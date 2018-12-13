package com.haier.svc.api.controller.util.http.jingdong;

import com.haier.svc.api.controller.util.JsonUtils;
import com.haier.svc.api.controller.util.StringUtil;
import org.apache.commons.codec.digest.DigestUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.*;

public class JingdongApiClient {

	public static final String CHARSET_UTF8 = "UTF-8";
	private static final String JSON_PARAM_KEY = "360buy_param_json";
	private static final String OTHER_PARAM_KEY = "other";
	private static String serverUrl;
	private static String accessToken;
	private static int connectTimeout;
	private static int readTimeout;
	private static String appKey;
	private static String appSecret;
	private static String appMethod;

	public JingdongApiClient(String serverUrl, String accessToken, String appKey, String appSecret, String appMethod) {
		this.connectTimeout = 30000;
		this.readTimeout = 30000;

		this.serverUrl = serverUrl;
		this.accessToken = accessToken;
		this.appKey = appKey;
		this.appSecret = appSecret;
		this.appMethod = appMethod;
	}

	public String execute(Map<String, String> formparams) throws Exception {
		try {
			String url = buildUrl(formparams);
			Map<String,String> params = new HashMap<String,String>();
			String json = JsonUtils.mapToJson(formparams);
			params.put("360buy_param_json", json);
			String rsp = doPost(url,params);
			return rsp;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("出现异常，请重试");
		}
	}

	public static String doPost(String url,Map<String, String> params)
			throws Exception {
		String ctype = "application/x-www-form-urlencoded;charset=UTF-8";
		String query = buildQuery(params, "UTF-8");
		byte[] content = new byte[0];
		if (query != null) {
			content = query.getBytes("UTF-8");
		}
		return doPost(url, ctype, content);
	}

	public static String doPost(String url, String ctype, byte[] content)
			throws IOException {
		HttpURLConnection conn = null;
		OutputStream out = null;
		String rsp = null;
		try {
			conn = getConnection(new URL(url), "POST", ctype);

			conn.setConnectTimeout(connectTimeout);
			conn.setReadTimeout(readTimeout);

			out = conn.getOutputStream();
			out.write(content);
			rsp = resovlerResponse(conn);
		} finally {
			if (out != null) {
				out.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}

		return rsp;
	}

	public static String resovlerResponse(HttpURLConnection con) {
		StringBuffer responseContent = new StringBuffer();
		try {
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream in = con.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				String lineStr = "";
				while ((lineStr = br.readLine()) != null) {
					responseContent.append(lineStr);
				}
				br.close();
				in.close();
			} else {
				System.out.println("返回状态为：" + responseCode);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseContent.toString();
	}

	private static HttpURLConnection getConnection(URL url, String method, String ctype) throws IOException {
		HttpURLConnection conn = null;
		if ("https".equals(url.getProtocol())) {
			SSLContext ctx = null;
			try {
				ctx = SSLContext.getInstance("TLS");
				ctx.init(new KeyManager[0], new DefaultTrustManager[] { new DefaultTrustManager() },
						new SecureRandom());
			} catch (Exception e) {
				throw new IOException(e);
			}
			HttpsURLConnection connHttps = (HttpsURLConnection) url.openConnection();
			connHttps.setSSLSocketFactory(ctx.getSocketFactory());
			connHttps.setHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			conn = connHttps;
		} else {
			conn = (HttpURLConnection) url.openConnection();
		}
		conn.setRequestMethod(method);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
		conn.setRequestProperty("User-Agent", "360buy-sdk-java");
		conn.setRequestProperty("Content-Type", ctype);
		return conn;
	}

	private String buildUrl(Map<String, String> formparams) throws Exception {
		String timestamp = DateUtils.formatDate("yyyy-MM-dd HH:mm:ss", new Date());
		Map<String,String> sysParams = new HashMap<String,String>();
		sysParams.put("method", this.appMethod);
		sysParams.put("timestamp", timestamp);
		sysParams.put("v", "2.0");

		Map<String,String> pmap = new TreeMap<String,String>();
		pmap.put("360buy_param_json", JsonUtils.mapToJson(formparams));
		sysParams.put("access_token", this.accessToken);
		sysParams.put("app_key", this.appKey);
		pmap.putAll(sysParams);
		String sign = sign(pmap, this.appSecret);

		sysParams.put("sign", sign);
		StringBuilder sb = new StringBuilder(this.serverUrl);
		sb.append("?");
		sb.append(buildQuery(sysParams, "UTF-8"));
		return sb.toString();
	}

	public static String buildQuery(Map<String, String> params, String charset) throws Exception {
		if ((params == null) || (params.isEmpty())) {
			return null;
		}

		StringBuilder query = new StringBuilder();
		Set<Map.Entry<String, String>> entries = params.entrySet();
		boolean hasParam = false;
		for (Map.Entry<String, String> entry : entries) {
			String name = (String) entry.getKey();
			String value = (String) entry.getValue();

			if (StringUtil.areNotEmpty(new String[] { name, value })) {
				if (hasParam) {
					query.append("&");
				} else {
					hasParam = true;
				}

				query.append(name).append("=").append(URLEncoder.encode(value, charset));
			}

		}

		return query.toString();
	}

	private String sign(Map<String, String> pmap, String appSecret) throws Exception {
		StringBuilder sb = new StringBuilder(appSecret);

		for (Map.Entry entry : pmap.entrySet()) {
			String name = (String) entry.getKey();
			String value = (String) entry.getValue();

			if (StringUtil.areNotEmpty(new String[] { name, value })) {
				sb.append(name).append(value);
			}
		}

		sb.append(appSecret);
		String result = DigestUtils.md5Hex(sb.toString()).toUpperCase();

		return result;
	}
}
