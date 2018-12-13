package com.haier.svc.api.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestHttp {
	public static void main(String[] args) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
		System.out.println(date);
		try {
			URL url = new URL("http://localhost:8081/hp/accept_time");

			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			/**
			 * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
			 * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
			 */
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestProperty("content-type", "text/html");
			connection.setRequestMethod("POST");
			connection.setConnectTimeout(50000);
			connection.setReadTimeout(50000);
			connection.connect();
			/**
			 * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...
			 */
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					connection.getOutputStream(), "UTF-8"));
			out.write("<ITEM><ORDER_NO>WD174825479755</ORDER_NO><STATUS_TIME>"+date+"</STATUS_TIME><STATUS>3</STATUS><ROWID>44444</ROWID><TBNO>555555</TBNO><WwwMark>66666</WwwMark><LBXNO>7777</LBXNO><SKU>8888</SKU><OPR_NAME>99999</OPR_NAME><OPR_TYPE>1010</OPR_TYPE></ITEM>");
			//out.write("");
			out.flush();

			String sCurrentLine;
			String sTotalString;
			sCurrentLine = "";
			sTotalString = "";
			InputStream l_urlStream;
			l_urlStream = connection.getInputStream();
			// 传说中的三层包装阿！
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(
					l_urlStream));
			while ((sCurrentLine = l_reader.readLine()) != null) {
				sTotalString += sCurrentLine + "/r/n";
			}
			System.out.println(sTotalString); // 获取返回的流
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
