package com.haier.svc.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    public static String md5(String msg) throws NoSuchAlgorithmException {
        byte[] buf = msg.getBytes();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(buf);
        byte[] tmp = md5.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : tmp) {
            sb.append(Integer.toHexString(b & 0xff));
        }
        //System.out.println(sb);
        return sb.toString();
    }
}
