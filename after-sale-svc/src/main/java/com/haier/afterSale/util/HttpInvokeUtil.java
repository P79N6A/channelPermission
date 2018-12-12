package com.haier.afterSale.util;

import com.alibaba.dubbo.common.utils.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

/**
 * HTTP调用服务工具类
 *                       
 * @Filename: HttpInvokeUtil.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public class HttpInvokeUtil {

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

    //    public static String invokeByType(String type, String content, String urlStr) throws Exception {
    //        return invokeByType(type, content, urlStr);
    //    }

    public static String invokeByType(String type, String content, String urlStr,
                                      String contentType) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
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

    public static String paramInvoke(Map<String, Object> paramaMap,
                                     String urlStr) throws Exception {
        if (paramaMap == null || urlStr == null) {
            return null;
        }
        Iterator<Entry<String, Object>> iterator = paramaMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, Object> next = iterator.next();
            urlStr += "&" + next.getKey() + "="
                      + URLEncoder.encode((String) next.getValue(), "utf-8");
        }
        URL url = new URL(urlStr);//创建一个URL  
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();//通过该url获得与服务器的连接  
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestMethod("POST");//设置请求方式为post  
        connection.setConnectTimeout(5000);//连接超时5秒
        connection.setReadTimeout(10000);//读取数据超时10秒
        connection.setDoOutput(true);

        //post方法要传送的键值对  
        connection.connect();
        byte[] datas = readInputStream(connection.getInputStream());
        String result = new String(datas, "utf-8");
        return result;
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

    public static String sendByPost(Map<String, String> paramMap, String url) throws Exception {
        if (paramMap == null || paramMap.size() == 0) {
            return null;
        }
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        Iterator<String> iterator = paramMap.keySet().iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            urlParameters.add(new BasicNameValuePair(next, paramMap.get(next)));
        }
        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(
            new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }
}
