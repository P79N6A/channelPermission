/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.afterSale.model;

import com.haier.dbconfig.model.Result;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

//import javax.servlet.http.HttpServletResponse;

/**
 *
 * @Filename: CainiaoInterfaceSender.java
 * @Version: 1.0
 * @Author: 穆占强
 * @Email: muzhanqiang@ehaier.com
 *
 */
public class CainiaoInterfaceSender {

	/**
	 * 通过http请求菜鸟的接口并返回菜鸟的返回数据，如果失败返回fail，如果成功返回xml数据
	 * @param targetUrl	菜鸟的请求地址
	 * @param msgMap	当前请求接口的参数
	 * @return
	 * @throws Exception
	 */
	public static Result sendToCainiao(CloseableHttpClient httpClient, String targetUrl, Map<String, String> msgMap, String body)
			throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(targetUrl).append("?");
		int i = 1;
		for(Map.Entry<String, String> entry: msgMap.entrySet()){
			if(entry.getKey().equals("timestamp")){
				sb.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			}else{
				sb.append(entry.getKey()).append("=").append(entry.getValue());
			}
			if(msgMap.size() > i++){
				sb.append("&");
			}
		}
		HttpPost httpPost = new HttpPost(sb.toString());
//		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//		if (msgMap != null && msgMap.size() > 0) {
//			for (String key : msgMap.keySet()) {
//				nvps.add(new BasicNameValuePair(key, msgMap.get(key)));
//			}
//		}
//		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        HttpEntity rqentity = new StringEntity(body, "utf-8");
        httpPost.setEntity(rqentity);
        RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(60000)
				.setConnectionRequestTimeout(60000)
				.setSocketTimeout(60000).build();
		httpPost.setConfig(requestConfig);

		int statusCode;
		CloseableHttpResponse response = httpClient.execute(httpPost);
		Result result = new Result();
		try {
			statusCode = response.getStatusLine().getStatusCode();
			if (statusCode == 200) {
				result.setStatus(true);
				HttpEntity rpEntity = response.getEntity();
				System.out.println(EntityUtils.toString(rpEntity, "UTF-8"));
				//result.setMessage(EntityUtils.toString(rpEntity, "UTF-8"));
				//result.setMessage("1");
			}else{
				result.setStatus(false);
				result.setMessage("HTTP返回状态错误，状态码为：" + statusCode);
			}
		}catch(Exception e){
			result.setStatus(false);
			result.setMessage(e.getMessage());
		} finally {
			response.close();
		}
		return result;
	}

	public static void main(String[] args) throws Exception {

		CainiaoInterfaceSender sender = new CainiaoInterfaceSender();
		String targetUrl = "http://gw.api.taobao.com/router/rest";
		targetUrl = "http://gw.api.taobao.com/router/rest?sign=AFE18055C9F77670510990BAF52C1818&timestamp=2016-09-26+19%3A27%3A01&v=2.0&app_key=12116339&method=taobao.logistics.companies.get&partner_id=top-apitools&session=610172696cd49995c037ef34964d77d24217774a389a7a6470168984&format=xml&force_sensitive_param_fuzzy=true&fields=id%2Ccode%2Cname%2Creg_mail_no";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String body = "";
		Map map = new HashMap();
		Result result = sendToCainiao(httpClient, targetUrl, map, body);
		System.out.println(result.getMessage());
	}
}
