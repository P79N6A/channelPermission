package com.haier.order.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * <p>Description: [AES-128-ECB加密模式，key需要为16位]</p>
 * Created on 2013-4-18
 * @author  <a href="mailto: zh-fan@neusoft.com">zh-fan</a>
 * @version 1.0
 */
public class AESUtil {
    private static final Log LOG = LogFactory.getLog(AESUtil.class);

    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            LOG.info("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            LOG.info("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        return new Base64().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                LOG.info("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                LOG.info("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "utf-8");
                return originalString;
            } catch (Exception e) {
                LOG.info(e.toString());
                return null;
            }
        } catch (Exception ex) {
            LOG.info(ex.toString());
            return null;
        }
    }

//    public static void main(String[] args) throws Exception {
//        /*
//         * 此处使用AES-128-ECB加密模式，key需要为16位
//         */
//        String cKey = "KeLy8g7qjmnbgWP0";
//        // 需要加密的字串
//        String cSrc = "cp2012c*0.150*215*WD1233768467*1800";
//        LOG.info("加密前的字串是：" + cSrc);
//        // 加密
//        String enString = AESUtil.Encrypt(cSrc, cKey);
//        LOG.info("加密后的字串是：" + enString);
//        // 解密pVAODDo8l3wEp3h8DhJ2bO7EJGTshjUN0ODz1QvPeFNblJuFz4aNMVLFTHK6sojo
//        String DeString = AESUtil.Decrypt(enString, cKey);
//        LOG.info("解密后的字串是：" + DeString);
//    }
    
    
    
}
