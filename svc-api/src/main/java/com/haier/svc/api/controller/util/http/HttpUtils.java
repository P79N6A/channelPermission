package com.haier.svc.api.controller.util.http;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.haier.svc.api.controller.util.StringUtil;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class HttpUtils {

	/**
	 * 根据条件计算获得国美的SIGN签名
	 * @param appkey  
	 * @param isHmac  
	 * @throws IOException
	 */
	public static String getGMSign(Map<String, String> params, String appkey,boolean isHmac)
			throws IOException { 
		Set<Entry<String, String>> paramSet = params.entrySet();
		StringBuilder query = new StringBuilder();
		if (!isHmac) {
			query.append(appkey);
		}
		for (Entry<String, String> param : paramSet) {
			if (StringUtil.areNotEmpty(param.getKey(), param.getValue())) {
				query.append(param.getKey()).append(param.getValue());
			}
		}
		byte[] bytes;
		if (isHmac) {
			bytes = encryptHMAC(query.toString(), appkey,"HmacMD5");
		} else {
			query.append(appkey);
			bytes = encryptMD5(query.toString());
		}
		 return byte2hex(bytes);
	}
	
	/**
	 * 根据条件计算获得国美的SIGN签名
	 * @param params
	 * @param appSecret
	 * @throws IOException
	 */
	public static String getDangDangMSign(Map<String, String> params, String appSecret)
			throws IOException { 
		Set<Entry<String, String>> paramSet = params.entrySet();
		StringBuilder query = new StringBuilder(); 
		query.append(appSecret); 
		for (Entry<String, String> param : paramSet) {
			if (StringUtil.areNotEmpty(param.getKey(), param.getValue())) {
				query.append(param.getKey()).append(param.getValue());
			}
		}
		query.append(appSecret);
		byte[] bytes= encryptMD5(query.toString());
		return bytes2Hex(bytes).toUpperCase();
	}

	/**
	 * 根据条件计算获得RRS->SIGN签名
	 * @param appkey
	 * @param params
	 * @throws IOException
	 */
	public static String getRrsSign(Map<String, String> params, String appkey){
		Set<Entry<String, String>> paramSet = params.entrySet();
		StringBuilder query = new StringBuilder();
		for (Entry<String, String> param : paramSet) {
			if (StringUtil.areNotEmpty(param.getKey(), param.getValue())) {
				query.append(param.getKey()).append("=").append(param.getValue()).append("&");
			}
		}
		if(query.length()>0){
			query.delete(query.lastIndexOf("&"),query.length());
		}
		query.append(appkey);
		return Hashing.md5().newHasher().putString(query,Charsets.UTF_8).hash().toString();
	}

	/**
	 * 根据条件计算获得RRS->SIGN签名
	 * @param appkey
	 * @param params
	 * @throws IOException
	 */
	public static String getSignOnMD5(Map<String, String> params, String appkey) throws IOException {
		Set<Entry<String, String>> paramSet = params.entrySet();
		StringBuilder query = new StringBuilder();
		for (Entry<String, String> param : paramSet) {
			if (StringUtil.areNotEmpty(param.getKey(), param.getValue())) {
				query.append(param.getKey()).append("=").append(param.getValue()).append("&");
			}
		}
		if(query.length()>0){
			query.delete(query.lastIndexOf("&"),query.length());
		}
		query.append(appkey);
		byte[] bytes= encryptMD5(query.toString());
		return bytes2Hex(bytes).toUpperCase();
	}

	/**
	 * 根据条件计算获得RRS->SIGN签名
	 * @param appSecret
	 * @param params
	 * @throws IOException
	 */
	public static String getICBCSign(Map<String, String> params,String appSecret,boolean isHmac) throws IOException{
		Set<Entry<String, String>> paramSet = params.entrySet();
		StringBuilder query = new StringBuilder();
		if (!isHmac) {
			query.append(appSecret);
		}
		for (Entry<String, String> param : paramSet) {
			//if (StringUtil.areNotEmpty(param.getKey(), param.getValue())) {
				query.append(param.getKey()).append("=").append(param.getValue()).append("&");
			//}
		}
		if(query.length()>0){
			query.delete(query.lastIndexOf("&"),query.length());
		} 
		byte[] bytes;
		if (isHmac) {
			 bytes = encryptHMAC(query.toString(), appSecret,"HmacSHA256");
			 BASE64Encoder be= new BASE64Encoder();
			 return be.encode(bytes);
		} else {
			query.append(appSecret);
			return encryptSHA256(query.toString());
		} 
	}
	
	/**
	 * 新的md5签名，首尾放secret。 
	 * @param params  传给服务器的参数 
	 * @param secret  分配给您的APP_SECRET
	 */
	public static String md5Signature(TreeMap<String, String> params,
			String secret) {
		String result = null;
		StringBuffer orgin = getBeforeSign(params, new StringBuffer(secret));
		if (orgin == null)
			return result;
		orgin.append(secret);
		try { 
			result = bytes2Hex(encryptMD5(orgin.toString()));
		} catch (Exception e) {
			throw new RuntimeException("sign error !");
		}
		return result;
	} 
	private static String encryptSHA256(String strSrc)  {
		MessageDigest md = null;
		String strDes = null; 
		try {
		   byte[] bt = strSrc.getBytes(Constants.CHARSET_UTF8);
			md = MessageDigest.getInstance("SHA-256");
			md.update(bt);
			strDes = byte2hex(md.digest()); // to HexString
		} catch (Exception e) {
			return null;
		}
		return strDes;
	} 
	
	private static byte[] encryptHMAC(String data, String secret,String SecretKey) throws IOException {
		byte[] bytes = null;
		try {
			SecretKey secretKey = new SecretKeySpec(secret.getBytes(Constants.CHARSET_UTF8),SecretKey);
			Mac mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
			bytes = mac.doFinal(data.getBytes(Constants.CHARSET_UTF8));
		} catch (GeneralSecurityException gse) {
			throw new IOException(gse);
		}
		return bytes;
	}
	
	private static byte[] encryptMD5(String data) throws IOException {
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			bytes = md.digest(data.getBytes(Constants.CHARSET_UTF8));
		} catch (GeneralSecurityException gse) {
			throw new IOException(gse);
		}
		return bytes;
	} 
	
	private static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex.toUpperCase());
		}
		return sign.toString();
	}
 
	
	private static String bytes2Hex(byte[] byteArray) {
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (byteArray[i] >= 0 && byteArray[i] < 16) {
				strBuf.append("0");
			}
			strBuf.append(Integer.toHexString(byteArray[i] & 0xFF));
		}
		return strBuf.toString();
	} 

	/**
	 * 添加参数的封装方法
	 * @param params
	 * @param orgin
	 * @return
	 */
	private static StringBuffer getBeforeSign(TreeMap<String, String> params,
			StringBuffer orgin) {
		if (params == null)
			return null;
		Map<String, String> treeMap = new TreeMap<String, String>();
		treeMap.putAll(params);
		Iterator<String> iter = treeMap.keySet().iterator();
		while (iter.hasNext()) {
			String name = (String) iter.next();
			orgin.append(name).append(params.get(name));
		}
		return orgin;
	}
	/**
	* 新的md5签名，首尾放secret。(一号店)
	*
	* @param params 传给服务器的参数
	*
	* @param secret 分配给您的APP_SECRET
	*/
	public static String md5Signature1(TreeMap<String, String> params, String secret) {
	     String result = null;
	     StringBuffer orgin = getBeforeSign1(params, new StringBuffer(secret));
	     if (orgin == null)
	         return result;
	 
	     // secret last
	     orgin.append(secret);
	     try {
	         MessageDigest md = MessageDigest.getInstance("MD5");
	         result = byte2hex1(md.digest(orgin.toString().getBytes("utf-8")));
	 
	     } catch (Exception e) {
	         throw new RuntimeException("sign error !");
	     }
	     return result;
	}
	 
	 
	/**
	* 二进制转字符串(一号店)
	*/
	private static String byte2hex1(byte[] b) {
	 
	     StringBuffer hs = new StringBuffer();
	     String stmp = "";
	     for (int n = 0; n < b.length; n++) {
	         stmp = (Integer.toHexString(b[n] & 0XFF));
	         if (stmp.length() == 1)
	         hs.append("0").append(stmp);
	         else
	         hs.append(stmp);
	     }
	     return hs.toString();
	}
	 
	/**
	* 添加参数的封装方法(一号店)
	* @param params
	* @param orgin
	* @return
	*/
	private static StringBuffer getBeforeSign1(TreeMap<String, String> params, StringBuffer orgin) {
	     if (params == null)
	         return null;
	     Map<String, String> treeMap = new TreeMap<String, String>();
	     treeMap.putAll(params);
	     Iterator<String> iter = treeMap.keySet().iterator();
	     while (iter.hasNext()) {
	         String name = (String) iter.next();
	         orgin.append(name).append(params.get(name));
	     }
	 
	     return orgin;
	}


}
