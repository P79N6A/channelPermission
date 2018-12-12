package com.haier.order.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA {

    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    /**
    * RSA签名
    * @param content 待签名数据
    * @param privateKey 商户私钥
    * @param input_charset 编码格式
    * @return 签名值
    */
    public static String sign(String content, String privateKey, String input_charset) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(input_charset));

            byte[] signed = signature.sign();

            return Base64.encodeBase64String(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
    * RSA验签名检查
    * @param content 待签名数据
    * @param sign 签名值
    * @param ali_public_key 支付宝公钥
    * @param input_charset 编码格式
    * @return 布尔值
    */
    public static boolean verify(String content, String sign, String ali_public_key,
                                 String input_charset) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decodeBase64(ali_public_key);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            java.security.Signature signature = java.security.Signature
                .getInstance(SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update(content.getBytes(input_charset));

            boolean bverify = signature.verify(Base64.decodeBase64(sign));
            return bverify;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
    * 解密
    * @param content 密文
    * @param private_key 商户私钥
    * @param input_charset 编码格式
    * @return 解密后的字符串
    */
    public static String decrypt(String content, String private_key,
                                 String input_charset) throws Exception {
        PrivateKey prikey = getPrivateKey(private_key);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(Base64.decodeBase64(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        //rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), input_charset);
    }

    /**
    * 得到私钥
    * @param key 密钥字符串（经过base64编码）
    * @throws Exception
    */
    public static PrivateKey getPrivateKey(String key) throws Exception {

        byte[] keyBytes;

        keyBytes = Base64.decodeBase64(key);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        return privateKey;
    }

    public static void main(String[] args) {
        String content = "我是原文内容";
        String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnvNDmVwuOaaPDDNqQ/fUcaYFInYBEwW69Esl3EFV7eItK3ogwLDs5gjV+LtxBt1jNDYvhQw4n2XCWMgtk77DaGgnCyGplLtivzgrBQqEfJJ0TJHW6snhVG5o2lfn+wf6H6/4WCWo/JBOVlXNIK1jbSngAikq6gkqcZWq98S+/3QIDAQAB";
        String pri = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKe80OZXC45po8MM2pD99RxpgUidgETBbr0SyXcQVXt4i0reiDAsOzmCNX4u3EG3WM0Ni+FDDifZcJYyC2TvsNoaCcLIamUu2K/OCsFCoR8knRMkdbqyeFUbmjaV+f7B/ofr/hYJaj8kE5WVc0grWNtKeACKSrqCSpxlar3xL7/dAgMBAAECgYBkAI4KxZp+LT0U7HTiPIYFimEpib0PQMNkte6mrXZJRnTFDibjkXs9rgN2Vzm5dDXdClZ61h96YohdAzkH1F76sEbxhydNfjfIwmvN1SMnlNFqH+QBQCbR5zv2NAOATo5pkJyy76NM3amlwIfcdFfenVOB0AfORfu0/GKDUaXtgQJBAOa+3mrlKWgCcWH4ZfZs85jf6YXuy25mMj1B5aoL6xqVKUdcDKzT2xVJPKldg75Q0SEe+2UxpUcptjKVyXByvtMCQQC6GI+RkGuLQfcsnk5K1aIPCNu4btDWXRPj6kJZoCiAhvvlzCfkTd60fL/M9y0Svh0aIhf90+eku9/AM4P9oziPAkEAoQW1ZNdCi+iYgwu667qgS6CF8AbyQ7pheMoRoRbsHALXaYrc9YZqLnKLOI2fsCHprrlWKgn5Eq4TDT3l8ujALQJBAK3k9YNYKKZgZdBTvWVAa8cQyAB3zL4DC+sVpBqMBkzT1d7N/xjfixOs3q1w67Vv4VHiOfj+Yp8Uz+iSOuxv8RECQQCVEDkRu1/DuLozA2Ca5zfKpvjNtZ7HT7pDjWcP1Q4UpO3t//vjNZNwmJBiUzNFMEUblQTMn4boot8JUdeHNkAG";
        String sign = sign(content, pri, "UTF-8");
        System.out.println(sign);
        boolean verify = verify(content, sign, pub, "UTF-8");
        System.out.println(verify);
    }
}
