package com.haier.svc.util;

import com.alibaba.dubbo.common.utils.StringUtils;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.Key;
import java.security.Security;

public class DESPlus {
    private static org.apache.log4j.Logger log           = org.apache.log4j.LogManager
                                                             .getLogger(DESPlus.class);
    private static String                  strDefaultKey = "dfwobswqdvds";             //测试值，上线会修改
    private static String                  appId         = "CBS";
    private static Cipher                  encryptCipher = null;
    private static Cipher                  decryptCipher = null;

    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }

    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * 默认构造方法，使用默认密钥
     *
     * @throws Exception
     */
    public DESPlus() throws Exception {
        this(strDefaultKey);
    }

    /**
     * 指定密钥构造方法
     *
     * @param strKey 指定的密钥
     * @throws Exception
     */
    public DESPlus(String strKey) throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = getKey(strKey.getBytes());
        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    public static String getKey() {
        return strDefaultKey;
    }

    /**
     * 加密字节数组
     *
     * @param arrB 需加密的字节数组
     * @return 加密后的字节数组
     * @throws Exception
     */
    public static byte[] encrypt(byte[] arrB) throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, getKey(strDefaultKey.getBytes()));
        return encryptCipher.doFinal(arrB);
    }

    /**
     * 加密字符串
     *
     * @param strIn 需加密的字符串
     * @return 加密后的字符串
     * @throws Exception
     */
    public static String encrypt(String strIn) throws Exception {
        return byteArr2HexStr(encrypt(strIn.getBytes("UTF-8")));
    }

    /**
     * 解密字节数组
     *
     * @param arrB 需解密的字节数组
     * @return 解密后的字节数组
     * @throws Exception
     */
    public static byte[] decrypt(byte[] arrB) throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, getKey(strDefaultKey.getBytes()));
        return decryptCipher.doFinal(arrB);
    }

    /**
     * 解密字符串
     *
     * @param strIn 需解密的字符串
     * @return 解密后的字符串
     * @throws Exception
     */
    public static String decrypt(String strIn) throws Exception {
        return new String(decrypt(hexStr2ByteArr(strIn)), "UTF-8");
    }

    private static Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];
        /* int size = 0;
         if (arrBTmp.length<arrB.length){
             size = arrBTmp.length;
         }else{
             size = arrB.length;
         }*/
        System.arraycopy(arrBTmp, 0, arrB, 0, arrB.length);
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
        return key;

    }

    /**
     * 加密编码参数
     * @param interfaceName
     * @throws Exception
     */
    public static String encodeParam(String interfaceName, String jsonContent) throws Exception {
        return encodeParam(interfaceName, jsonContent, appId, strDefaultKey);
    }

    /**
     * 加密编码参数
     * @param interfaceName 接口名称
     * @param jsonContent 参数json串
     * @param o2oAppId 应用名
     * @param o2oKey 密钥key
     * @throws Exception
     */
    public static String encodeParam(String interfaceName, String jsonContent, String o2oAppId,
                                     String o2oKey) throws Exception {
        //        log.info("interfaceName = " + interfaceName + "\njsonContent = " + jsonContent
        //                 + "\no2oAppId = " + o2oAppId + "\no2oKey = " + o2oKey);
        String encontent = encrypt(jsonContent);
        String sign = Md5.getMd5(encontent + o2oKey);

        HttpApi httpApi = new HttpApi();
        httpApi.setAppId(o2oAppId);
        httpApi.setInterfaceName(interfaceName);
        httpApi.setContent(encontent);
        httpApi.setSign(sign);

        String enString = XmlUtil.jAXBmarshalString(httpApi);

        String encrypted = URLEncoder.encode(enString, "UTF-8");
        byte[] basestr = Base64.encodeBase64(encrypted.getBytes("UTF-8"));
        return new String(basestr, "UTF-8");
    }

    /**
     * 解密参数
     * @param encodeContent 转码前字符串
     * @throws Exception
     */
    public static String decodeParam(String encodeContent) throws Exception {
        byte[] decryptByte = Base64.decodeBase64(encodeContent);
        String decrypt = URLDecoder.decode(new String(decryptByte, "UTF-8"), "UTF-8");
        decrypt = CommonTool.cleanParm(decrypt);
        return decrypt;
    }

    /**
     * 解密参数
     * @param decryptContent 转码后的参数
     * @throws Exception
     */
    public static HttpResult<HttpApi> decryptParam(String decryptContent) throws Exception {
        return decryptParam(decryptContent, strDefaultKey);
    }

    /**
     * 解密参数
     * @param decryptContent 转码后的参数
     * @param o2oKey key值
     * @throws Exception
     */
    public static HttpResult<HttpApi> decryptParam(String decryptContent, String o2oKey)
                                                                                        throws Exception {
        //        log.info("decryptContent=" + decryptContent);
        HttpResult<HttpApi> httpResult = new HttpResult<HttpApi>();
        HttpApi output = (HttpApi) XmlUtil.jAXBunmarshalString(decryptContent, HttpApi.class);
        //        log.info("return-param-decode-decrypt:" + JsonUtil.toJson(output));
        if (output != null && StringUtils.isEmpty(output.getError())) {
            String signResult = CommonTool.cleanParm(Md5.getMd5(output.getContent() + o2oKey));
            String contentResult = "";
            if (signResult.equals(output.getSign())) {
                contentResult = decrypt(output.getContent());
                //                log.info("contentResult=" + contentResult);
                output.setContent(contentResult);
                httpResult.setData(output);
                httpResult.setSuccess(true);
                return httpResult;
            } else {
                httpResult.setData(output);
                httpResult.setMessage("签名校验错误！");
                httpResult.setSuccess(false);
                log.error(decryptContent + ",签名校验错误！");
            }
        } else {
            httpResult.setData(output);
            httpResult.setMessage(output != null ? output.getError() : "decryptContent="
                                                                       + decryptContent);
            httpResult.setSuccess(false);
            log.error(output != null ? output.getError() : "接口返回错误信息：decryptContent="
                                                           + decryptContent);
        }
        return httpResult;
    }

    /**
     * 解码并解密参数
     * @param encodeContent 解码解密前的参数
     * @param o2oKey key值
     * @throws Exception
     */
    public static HttpResult<HttpApi> decodeAndDecryptParam(String encodeContent, String o2oKey)
                                                                                                throws Exception {
        return decryptParam(decodeParam(encodeContent), o2oKey);
    }

//    public static void main(String[] args) throws Exception {
//        //        DESPlus des = new DESPlus();
//        String url = "http://58.56.128.84:9001/EAI/RoutingProxyService/EAI_REST_POST_ServiceRoot?INT_CODE=temp_service_558";
//
//        //设置参数
//        List<Map<String, String>> paramlist = new ArrayList<Map<String, String>>();
//        Map<String, String> pm = new HashMap<String, String>();
//        //        pm.put("netSn", "WD123123124");
//        //        pm.put("closeTime", "2015-06-02 17:22:21");
//        //        pm.put("closeCount", "5");
//        //        pm.put("oldNetSn", "");
//
//        pm.put("netSn", "WD123123125");
//        pm.put("cancelTime", "2015-06-02 17:22:21");
//        pm.put("cancelCount", "5");
//        pm.put("oldNetSn", "WD123123123");
//        paramlist.add(pm);
//
//        //将参数转成json格式:"[{\"netSn\":\"WD123123123\",\"cancelTime\":\"2015-06-02 17:22:21\",\"cancelCount\":\"5\",\"oldNetSn\":\"WD123123123\"}]";
//        String json_content = JsonUtil.toJson(paramlist);//这个字符串直接保存到pushData中
//        //        json_content = "[{\"createTime\":\"\",\"sourceType\":\"PC\",\"remark\":\"\",\"orderActualPrice\":0.00,\"netPrice\":1799.00,\"number\":1,\"type\":\"10\",\"orderSn\":\"121225173839549\",\"sku\":\"BA09X004Z\",\"createBy\":\"\",\"brandName\":\"\",\"brandId\":297,\"oldNetSn\":\"\",\"isTogether\":\"Y\",\"goodsOwnerId\":\"SYWA\",\"netActualPrice\":1799.00,\"price\":1799.00,\"netSn\":\"WD120209002667\",\"orderPrice\":1099.00,\"payOnDelivery\":\"Y\",\"netTime\":\"2015-01-13 16:36:36\",\"productName\":\"BCD-206SM\",\"cateId\":2723,\"cateName\":\"\"}]";
//        //参数加密并格式化成xml再转码
//        String encrypt_content = encodeParam("backNetInfo", json_content);//调用接口前加密后的字符串参数   // backNetInfo，odsNetInfo，closeNetInfo
//        //请求地址传值，并接收返回结果
//        String result = CommonTool.sendPost(url, encrypt_content, "text/html; charset=utf-8");
//        //把返回的结果逆向转码
//        String decode_result = decodeParam(result);
//        //对方现在返回的格式 <?xml version="1.0" encoding="UTF-8" standalone="yes"?><httpApi>    <content>dafe1e702fdf5c64</content>    <sign>YWMwYTAxODA5ZWNlMGI1N2M4NTNiZjJhYjY1M2Y1NWU=</sign></httpApi>
//        System.out.println("decrypt_content=" + decode_result);
//        //将加密的content内容解密并校验签名
//        HttpResult<HttpApi> httpResult = decryptParam(decode_result);
//        //解密后的字符串转成json串
//        String resultJson = JsonUtil.toJson(httpResult);//这个字符串直接保存到returnData中
//        System.out.println("resultJson=" + resultJson);
//
//        if (httpResult.getSuccess()) {//表示签名校验通过
//            HttpApi hp = httpResult.getData();
//            System.out.println("content=" + hp.getContent());//解密后的内容，实际应该是一个json串
//            System.out.println("sign=" + hp.getSign());
//        } else {
//            System.out.println(httpResult.getMessage());//"签名校验错误！"
//        }
//        //json串解析
//        String jsonstr = "[{\"netSn\":\"WD123123123\",\"cancelTime\":\"2015-06-02 17:22:21\",\"cancelCount\":\"5\",\"oldNetSn\":\"WD123123123\"}]";
//        List<Map<String, String>> pl = JsonUtil.fromJson(jsonstr);
//        System.out.println("pl=" + pl.get(0).get("netSn"));
//    }

}
