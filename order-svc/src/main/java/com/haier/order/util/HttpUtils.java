package com.haier.order.util;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.gson.Gson;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

public class HttpUtils {
    public static final String             HTTP_METHOD_GET  = "GET";
    public static final String             HTTP_METHOD_POST = "POST";
    private static org.apache.log4j.Logger log              = org.apache.log4j.LogManager
                                                                .getLogger(HttpUtils.class);

    private static class HttpRequestJob implements Runnable {
        private IHttpResponseCallback cb           = null;

        private HttpGet               get_request  = null;
        private HttpPost              post_request = null;

        private String                content      = null;
        private boolean               isSSL        = false;

        public HttpRequestJob(HttpGet get_request, HttpPost post_request, boolean isSSL,
                              IHttpResponseCallback cb) {
            this.get_request = get_request;
            this.post_request = post_request;
            this.cb = cb;
            this.isSSL = isSSL;
        }

        public String getContent() {
            return content;
        }

        @Override
        public void run() {
            HttpClient client = new DefaultHttpClient();
            
            if (isSSL) {
                client = wrapClient(client);
            }
            BufferedReader in = null;
            HttpResponse response = null;
            try {
                if (get_request != null) {
                    response = client.execute(get_request);
                } else if (post_request != null) {
                    response = client.execute(post_request);
                }

                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(),
                    "UTF-8"));
                StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }
                in.close();
                content = sb.toString();
                log.info("[HTTP][RESPONSE]" + content);
            } catch (Exception e) {
                log.error("", e);
                content = null;
            } finally {
                if (in != null) {
                    try {
                        in.close();// 最后要关闭BufferedReader  
                    } catch (Exception e) {
                        e.printStackTrace();
                        content = null;

                    }
                }

                //System.out.println(response.getStatusLine().getStatusCode() + ":" + content);
                if (cb != null) {
                    cb.response(response.getStatusLine().getStatusCode(), content);
                }
            }
        }
    }

    public static interface IHttpResponseCallback {
        public void response(int http_code, String response);
    }

    public class CharacterMethod extends PostMethod {
        public CharacterMethod(String url) {
            super(url);
        }

        @Override
        public String getRequestCharSet() {
            //return super.getRequestCharSet();   
            return "UTF-8";
        }
    }

    public static String sendRequest(String url, Map<String, String> header, String data,
                                     String method, boolean isSSL, IHttpResponseCallback cb) {
        try {
            HttpRequestJob job = null;
            if (method.equalsIgnoreCase(HTTP_METHOD_GET)) {
                HttpGet request = new HttpGet();
                if (StringUtils.isNotEmpty(data)) {
                    request.setURI(new URI(url + "?" + data));
                } else {
                    request.setURI(new URI(url));
                }
                if (header != null) {
                    for (Map.Entry<String, String> s : header.entrySet()) {
                        Header h = new BasicHeader(s.getKey(), s.getValue());
                        request.addHeader(h);
                    }
                }

                log.info("[HTTP][GET]url:" + request.getURI().toString());
                job = new HttpRequestJob(request, null, isSSL, cb);
            } else if (method.equalsIgnoreCase(HTTP_METHOD_POST)) {
                HttpPost request = new HttpPost();
                request.setURI(new URI(url));

                if (header != null) {
                    for (Map.Entry<String, String> s : header.entrySet()) {
                        Header h = new BasicHeader(s.getKey(), s.getValue());
                        request.addHeader(h);
                    }
                }

                HttpEntity entity = new StringEntity(data, "utf-8");
                request.setEntity(entity);

                log.info("[HTTP][POST]url:" + request.getURI().toString());
                log.info("[HTTP][POST]data:" + data);
                job = new HttpRequestJob(null, request, isSSL, cb);
            }

            Thread t = new Thread(job);
            t.start();

            if (cb == null) {
                t.join();
                return job.getContent();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    private static DefaultHttpClient wrapClient(HttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {

                //public X509Certificate[] getAcceptedIssuers() { 
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                                                                                   throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                                                                                   throws CertificateException {
                }

            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = base.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
            return new DefaultHttpClient(ccm, base.getParams());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String resultBuildJson(Object result, OutputStream os) throws IOException {
        Gson gson = new Gson();
        String s = gson.toJson(result);
        if (os != null)
            os.write(s.getBytes());
        return s;
    }
}
