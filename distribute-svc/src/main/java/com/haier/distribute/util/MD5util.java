package com.haier.distribute.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SignatureException;

import org.apache.commons.codec.digest.DigestUtils;


/**
 * MD5加密工具类
 * 
 * @Filename MD5util.java
 * 
 * @version 1.0
 * @author changji.zhang
 */
public class MD5util {

	private static String key = "DFGHJKertyui456RTYUIOdfghjadfsa67dsfdsf";

	public static String encrypt(String message) throws Exception {
		return encrypt(message, "MD5");
	}

	// algorithm: MD5 or SHA-1
	// return string length: 32 if algorithm = MD5, or 40 if algorithm = SHA-1
	public static String encrypt(String message, String algorithm)
			throws Exception {
		if (message == null) {
			throw new Exception("message is null.");
		}
		if (!"MD5".equals(algorithm) && !"SHA-1".equals(algorithm)) {
			throw new Exception("algorithm must be MD5 or SHA-1.");
		}
		byte[] buffer = message.getBytes("utf-8");

		// The SHA algorithm results in a 20-byte digest, while MD5 is 16 bytes
		// long.
		MessageDigest md = MessageDigest.getInstance(algorithm);

		// Ensure the digest's buffer is empty. This isn't necessary the first
		// time used.
		// However, it is good practice to always empty the buffer out in case
		// you later reuse it.
		md.reset();

		// Fill the digest's buffer with data to compute a message digest from.
		md.update(buffer);

		// Generate the digest. This does any necessary padding required by the
		// algorithm.
		byte[] digest = md.digest();

		// Save or print digest bytes. Integer.toHexString() doesn't print
		// leading zeros.
		StringBuffer hexString = new StringBuffer();
		String sHexBit = null;
		for (int i = 0; i < digest.length; i++) {
			sHexBit = Integer.toHexString(0xFF & digest[i]);
			if (sHexBit.length() == 1) {
				sHexBit = "0" + sHexBit;
			}
			hexString.append(sHexBit);
		}
		return hexString.toString();
	}

	public static String Md5(String msg) {
		try {
			return encrypt(msg + PassWordKey());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// / <summary>
	// / MD5加密的混合代码
	// / </summary>
	// / <returns></returns>
	public static String PassWordKey() {
		return "@4!@#$%@";
	}

	/**
	 * 签名字符串
	 * 
	 * @param text
	 *            需要签名的字符串
	 * @param key
	 *            密钥
	 * @param input_charset
	 *            编码格式
	 * @return 签名结果
	 */
	public static String sign(String text, String key, String input_charset) {
		text = text + key;
		return DigestUtils.md5Hex(getContentBytes(text, input_charset));
	}

	public static String sign(String text) {
		return sign(text + PassWordKey(), key, "utf-8");
	}

	public static boolean verify(String text, String sign) {
		return verify(text + PassWordKey(), sign, key, "utf-8");
	}

	/**
	 * 签名字符串
	 * 
	 * @param text
	 *            需要签名的字符串
	 * @param sign
	 *            签名结果
	 * @param key
	 *            密钥
	 * @param input_charset
	 *            编码格式
	 * @return 签名结果
	 */
	public static boolean verify(String text, String sign, String key,
			String input_charset) {
		text = text + key;
		String mysign = DigestUtils
				.md5Hex(getContentBytes(text, input_charset));
		if (mysign.equals(sign)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param content
	 * @param charset
	 * @return
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:"
					+ charset);
		}
	}
}
