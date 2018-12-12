package com.haier.order.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class WebCommonUtil {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
                                                   .getLogger(WebCommonUtil.class);

    public static String PostMessage(String url, String message) throws Exception {
        URL urlPost = new URL(url);
        //System.out.println("Post to " + url + " message : " + message);
        log.info("Post to " + url + " message : " + message);
        byte[] data = message.getBytes();
        HttpURLConnection httpConn = (HttpURLConnection) urlPost.openConnection();

        httpConn.setRequestProperty("Content-Length", String.valueOf(message.length()));
        //httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");

        try {
            httpConn.setRequestMethod("POST");
        } catch (ProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        OutputStream out = httpConn.getOutputStream();
        out.write(data);
        out.close();
        int len = 0;
        InputStream inStream = httpConn.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        String resultMsg = buffer.toString();
        inStream.close();
        //System.out.println("message response from " + url + ": " + resultMsg);
        log.info("message response from " + url + ": " + resultMsg);
        return resultMsg;

    }

    public static String GetMessage(String url, String message) throws Exception {
        URL urlPost = new URL(url + "?" + message);
        log.info("get from " + url + "?" + message);
        HttpURLConnection httpConn = (HttpURLConnection) urlPost.openConnection();

        //httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");

        try {
            httpConn.setRequestMethod("GET");
        } catch (ProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);
        int len = 0;
        InputStream inStream = httpConn.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        String resultMsg = buffer.toString();
        inStream.close();
        //System.out.println("message response from " + url + ": " + resultMsg);
        log.debug("message response from " + url + ": " + resultMsg);
        return resultMsg;

    }

}
