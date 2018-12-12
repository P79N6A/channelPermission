package com.haier.stock.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.alibaba.dubbo.common.utils.StringUtils;

/**
 * HTTPS加密专用调用服务工具类
 *                       
 * @Filename: HttpSecInvokeUtil.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public class HttpSecInvokeUtil { 

    public static final String METHOD_POST       = "POST";
    public static final String METHOD_GET        = "GET";
    public static final String CONTENT_TYPE_TEXT = "text/xml";
    public static final String CONTENT_TYPE_JSON = "application/json";

    /**
     * 调用HTTP服务
     * @param content 调用提交数据
     * @param urlStr http服务地址
     * @return 结果字符串
     * @throws Exception
     */
    public static String invoke(String content, String urlStr) throws Exception {
        return invokePost(content, urlStr);
    }

    public static String invokeGet(String content, String urlStr) throws Exception {
        return invokeGet(content, urlStr, CONTENT_TYPE_TEXT);
    }

    public static String invokeGet(String content, String urlStr,
                                   String contentType) throws Exception {
        return invokeByType(METHOD_GET, content, urlStr, contentType);
    }

    public static String invokePost(String content, String urlStr) throws Exception {
        return invokePost(content, urlStr, CONTENT_TYPE_TEXT);
    }

    public static String invokePost(String content, String urlStr,
                                    String contentType) throws Exception {
        return invokeByType(METHOD_POST, content, urlStr, contentType);
    }

    public static String invokeByType(String type, String content, String urlStr,
                                      String contentType) throws Exception {
        TrustManager[] tm = { new X509TrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        } };
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(null, tm, new java.security.SecureRandom());
        // 从上述SSLContext对象中得到SSLSocketFactory对象    
        SSLSocketFactory ssf = sslContext.getSocketFactory();

        URL url = new URL(urlStr);
        HttpsURLConnection httpConn = (HttpsURLConnection) url.openConnection();

        httpConn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        httpConn.setSSLSocketFactory(ssf);

        byte[] data = null;
        if (!StringUtils.isBlank(content)) {
            data = content.getBytes("utf-8");
            httpConn.setRequestProperty("Content-Length", String.valueOf(data.length));
        }
        httpConn.setRequestProperty("Content-Type", contentType + ";charset=utf-8");
        httpConn.setRequestMethod(type);
        httpConn.setConnectTimeout(5000);//连接超时5秒
        httpConn.setReadTimeout(10000);//读取数据超时10秒
        httpConn.setDoOutput(true);
        if ("POST".equals(type) && null != data) {
            OutputStream out = null;
            try {
                httpConn.setDoInput(true);
                out = httpConn.getOutputStream();
                out.write(data);
            } finally {
                if (null != out) {
                    out.close();
                }
            }
        }
        byte[] datas = readInputStream(httpConn.getInputStream());
        return new String(datas, "utf-8");
    }

    private static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            byte[] data = outStream.toByteArray();//二进制数据
            return data;
        } finally {
            outStream.close();
            inStream.close();
        }
    }

}
