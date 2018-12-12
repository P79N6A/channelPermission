package com.haier.stock.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class Md5 {

    public static String getMd5(String content) {
        return Base64.encodeBase64String(encrypt(content).getBytes());
    }

    public static String encrypt(String password) {
        return DigestUtils.md5Hex(password);
    }
}
