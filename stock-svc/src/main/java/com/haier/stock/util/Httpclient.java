package com.haier.stock.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * http请求工具类
 * @author tie.liu
 * 
 */
public class Httpclient {

	/**
	 * POST请求数据
	 * @param destUrl
	 * @param postData
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static String send(String destUrl, String postData, String type)
			throws Exception {
		String responseString = "";
		URL url = null;
		HttpURLConnection urlconn = null;
		OutputStream out = null;
		BufferedReader bufferedReader = null;
		try {
			url = new URL(destUrl);
			urlconn = (HttpURLConnection) url.openConnection();
			urlconn.setRequestProperty("content-type", type);
			urlconn.setRequestMethod("POST");
			urlconn.setDoInput(true);
			urlconn.setDoOutput(true);
			out = urlconn.getOutputStream();
			out.write(postData.getBytes("UTF-8"));
			out.flush();
			bufferedReader = new BufferedReader(new InputStreamReader(
					urlconn.getInputStream(), "UTF-8"));
			StringBuffer sb = new StringBuffer();
			int ch;
			while ((ch = bufferedReader.read()) > -1) {
				sb.append((char) ch);
			}
			responseString = sb.toString();
		} finally {
			if (out != null) {
				out.close();
			}
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (urlconn != null) {
				urlconn.disconnect();
			}
		}
		return responseString;
	}
}
