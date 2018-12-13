package com.haier.vehicle.services;

/**
 * 使用jdk自带的HttpURLConnection向URL发送POST请求并输出响应结果
 * 参数使用流传递，并且硬编码为字符串"name=XXX"的格式
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.google.gson.Gson;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.vehcile.Entry3wOrder;
import com.haier.vehicle.util.XmlUtils;

public class SendPostDemo {
	public static void main(String[] args) throws Exception {
		String urlPath = new String("http://144.123.47.137:81/cainiao/getEntryOrder.html");
		String param = "dnId=8569073365";
		// 建立连接
		URL url = new URL(urlPath);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		// 设置参数
		httpConn.setDoOutput(true); // 需要输出
		httpConn.setDoInput(true); // 需要输入
		httpConn.setUseCaches(false); // 不允许缓存
		httpConn.setRequestMethod("POST"); // 设置POST方式连接
		// 设置请求属性
		httpConn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
		httpConn.setRequestProperty("Charset", "UTF-8");
		// 连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
		httpConn.connect();
		// 建立输入流，向指向的URL传入参数
		DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
		dos.writeBytes(param);
		dos.flush();
		dos.close();
		// 获得响应状态
		int resultCode = httpConn.getResponseCode();
		if (HttpURLConnection.HTTP_OK == resultCode) {
			StringBuffer sb = new StringBuffer();
			String readLine = new String();
			BufferedReader responseReader = new BufferedReader(
					new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			while ((readLine = responseReader.readLine()) != null) {
				sb.append(readLine).append("\n");
			}
			responseReader.close();
			Gson json = new Gson();
			Map<String, Object> map = XmlUtils.xmlStrToMap(json.fromJson(sb.toString(),ServiceResult.class).getResult().toString());
			Entry3wOrder entry3wOrder = (Entry3wOrder) XmlUtils.mapToBean(XmlUtils.xmlStrToMap(map.get("entryOrder").toString()), Entry3wOrder.class);
			entry3wOrder.setFlag(map.get("flag").toString());
			entry3wOrder.setCode(json.fromJson(sb.toString(),ServiceResult.class).getCode());
			entry3wOrder.setMessage(json.fromJson(sb.toString(),ServiceResult.class).getMessage());
		}
		
	}
	
}