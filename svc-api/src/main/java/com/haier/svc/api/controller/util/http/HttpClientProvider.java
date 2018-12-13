package com.haier.svc.api.controller.util.http;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.*;

public class HttpClientProvider {

	private static final Logger logger = LogManager
			.getLogger(HttpClientProvider.class);
	// 请求方式(GET, POST, PUT, DELETE, HEAD, OPTIONS, and TRACE),默认为GET
	private String methodType = "GET";
	// 请求编码
	private String encoding = "UTF-8";
	// 请求参数
	private Map<String, String> params = new HashMap<String, String>();
	private Map<String, String> headParams = new HashMap<String, String>();
	private Map<String, Object> fileParams = new HashMap<String, Object>();
	// 主体内容，自动转化为params参数
	private String requestBody;
	// 请求的URL
	private String url;
	// 返回的响应的CODE
	private int responseCode = -1;

	private boolean isHttp = true;
	// htts请求的key文件路径
	private String keystorePath;
	private String password;
	private String reqType = "json"; // 请求类型 xml/json
	private boolean isFile = false; // 是否提交附件

	// 返回字段
	protected String responseContent;
	private List<NameValuePair> pairs;
	private int connectionRequestTimeout = 30000;
	private int connectTimeout = 30000;
	private int socketTimeout = 60000;

	public String send() {
		if ("POST".equalsIgnoreCase(this.methodType)) {
			responseContent = post();
		} else if ("GET".equalsIgnoreCase(this.methodType)) {
			responseContent = get();
		}
		return responseContent;
	}

	public String post() {
		try {
			HttpPost httppost = new HttpPost(url);
			httppost.setConfig(this.getRequestConfig());
			addHeadParams(httppost);
			if ((pairs == null || pairs.size() == 0) && this.params.isEmpty()
					&& !isFile) {
				StringEntity jsonStr = null;
				if (reqType.equals("xml")) {
					httppost.addHeader(HTTP.CONTENT_TYPE, "application/xml");
					jsonStr = this.getXml();

				} else {
					httppost.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
					jsonStr = this.getJson();
				}
				jsonStr.setContentEncoding("UTF-8");
				httppost.setEntity(jsonStr);
			} else if (isFile) {
				MultipartEntityBuilder b = MultipartEntityBuilder.create();
				for (Map.Entry<String, Object> entity : fileParams.entrySet()) {
					if (entity.getValue() instanceof File) {
						b.addBinaryBody(entity.getKey(),
								(File) entity.getValue());
					} else {
						b.addTextBody(entity.getKey(),
								(String) entity.getValue());
					}
				}
				// 生成 HTTP 实体
				HttpEntity httpEntity = b.build();
				httppost.setEntity(httpEntity);
			} else {
				UrlEncodedFormEntity uefEntity;
				uefEntity = new UrlEncodedFormEntity(this.getPairs(), encoding);
				httppost.setEntity(uefEntity);
			}
			if (isHttp) {
				return executeMethod(httppost);
			} else {
				return executeHTTPSMethod(httppost);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("执行post请求异常信息------->" + e.getMessage());
			return "error";
		}
	}

	public StringEntity getJson() throws UnsupportedEncodingException {
		logger.info("request requestBody [ JSON:" + this.requestBody + ";]");
		StringEntity se = new StringEntity(this.requestBody, encoding);
		se.setContentType("text/json");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
				"application/json"));
		return se;
	}

	public StringEntity getXml() throws UnsupportedEncodingException {
		logger.info("request requestBody [ XML:" + this.requestBody + ";]");
		StringEntity se = new StringEntity(this.requestBody, encoding);
		se.setContentType("text/xml");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
				"application/json"));
		return se;
	}

	// 设置头信息
	protected void addHeadParams(HttpPost httppost) {
		if (null != headParams && headParams.size() >= 1) {
			Iterator<String> headParamIT = headParams.keySet().iterator();
			while (headParamIT.hasNext()) {
				String key = headParamIT.next();
				String value = headParams.get(key);
				httppost.addHeader(key, value);
			}
		}
	}

	/**
	 * 发送 get请求
	 */
	private String get() {
		try {
			HttpGet httpget = new HttpGet(url);
			httpget.setConfig(this.getRequestConfig());
			if (isHttp) {
				return executeMethod(httpget);
			} else {
				return executeHTTPSMethod(httpget);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("执行get请求异常信息------->" + e.getMessage());
			return "error";
		}
	}

	// 设置POST的请求参数,该方法可以用来被重写
	public List<NameValuePair> getPairs() {
		if (pairs == null || pairs.size() == 0) {
			pairs = new ArrayList<NameValuePair>();
			if (null != params && params.size() > 1) {
				Iterator<String> paramsIT = params.keySet().iterator();
				while (paramsIT.hasNext()) {
					String key = paramsIT.next();
					String value = params.get(key);
					pairs.add(new BasicNameValuePair(key, value));
				}
			}
		}
		return pairs;
	}

	public String executeMethod(HttpUriRequest request) {
		// 创建默认的httpClient实例.
		String result = "";
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			/*
			 * if(request instanceof HttpPost){
			 * logger.info("executing POST request: " + request.getURI()); }else
			 * if(request instanceof HttpGet){
			 * logger.info("executing GET request: " + request.getURI()); }
			 */
			CloseableHttpResponse response = httpclient.execute(request);
			try {
				this.responseCode = response.getStatusLine().getStatusCode();
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, encoding);
					// logger.info("Response content: " +result);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 执行HTTS请求
	 */
	public String executeHTTPSMethod(HttpUriRequest request) throws Exception {
		KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
		FileInputStream instream = new FileInputStream(new File(
				this.keystorePath));
		try {
			trustStore.load(instream, this.password.toCharArray());
		} catch (Exception e) {
			throw new JobException("执行查询请求异常：密钥错误;\n" + e.getMessage());
		} finally {
			instream.close();
		}
		String result = "";
		SSLContext sslcontext = SSLContexts.custom()
				.loadTrustMaterial(trustStore, new TrustSelfSignedStrategy())
				.build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom()
				.setSSLSocketFactory(sslsf).build();
		try {
			CloseableHttpResponse response = httpclient.execute(request);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, encoding);
					// logger.info("Response https content: " +result);
				}
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
		return result;
	}

	private RequestConfig getRequestConfig() {
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(this.connectionRequestTimeout)
				.setConnectTimeout(this.connectTimeout)
				.setSocketTimeout(this.socketTimeout).build();
		return requestConfig;
	}

	// **********************************************************************************/
	public HttpClientProvider setPostMethod() {
		this.methodType = "POST";
		return this;
	}

	public HttpClientProvider setGetMethod() {
		this.methodType = "GET";
		return this;
	}

	public HttpClientProvider setHttp(boolean isHttp) {
		this.isHttp = isHttp;
		return this;
	}

	public HttpClientProvider setKeystorePath(String keystorePath) {
		this.keystorePath = keystorePath;
		return this;
	}

	public HttpClientProvider setPassword(String password) {
		this.password = password;
		return this;
	}

	public HttpClientProvider setParams(Map<String, String> params) {
		this.params = params;
		return this;
	}

	public HttpClientProvider setHeadParams(Map<String, String> headParams) {
		this.headParams = headParams;
		return this;
	}

	public HttpClientProvider setFileParams(Map<String, Object> fileParams) {
		this.fileParams = fileParams;
		return this;
	}

	public HttpClientProvider setEncoding(String encoding) {
		this.encoding = encoding;
		return this;
	}

	public HttpClientProvider setRequestBody(String requestBody) {
		this.requestBody = requestBody;
		return this;
	}

	public HttpClientProvider setUrl(String url) {
		this.url = url;
		return this;
	}

	public HttpClientProvider setPairs(List<NameValuePair> pairs) {
		this.pairs = pairs;
		return this;
	}

	public HttpClientProvider setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
		return this;
	}

	public HttpClientProvider setReqType(String reqType) {
		this.reqType = reqType;
		return this;
	}

	public HttpClientProvider setFile(boolean isFile) {
		this.isFile = isFile;
		return this;
	}

	public HttpClientProvider setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
		return this;
	}

	public HttpClientProvider setConnectionRequestTimeout(
			int connectionRequestTimeout) {
		this.connectionRequestTimeout = connectionRequestTimeout;
		return this;
	}

	public String getMethodType() {
		return methodType;
	}

	public String getEncoding() {
		return encoding;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public int getConnectionRequestTimeout() {
		return connectionRequestTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public String getUrl() {
		return url;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseContent() {
		return responseContent;
	}

	public void setResponseContent(String responseContent) {
		this.responseContent = responseContent;
	}

	public Map<String, String> getHeadParams() {
		return headParams;
	}

	public String getReqType() {
		return reqType;
	}

	public boolean isFile() {
		return isFile;
	}

}
