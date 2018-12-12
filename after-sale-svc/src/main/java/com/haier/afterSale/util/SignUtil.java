package com.haier.afterSale.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

/**
 * 签名规则(MD5|RSA)
 * <p>第一步,将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），
 * 使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA</p>
 *  <ul>
 *  <li>参数名ASCII码从小到大排序（字典序）</li>
 *  <li>如果参数的值为空不参与签名</li>
 *  <li>如果参数的值为空不参与签名</li
 *  <li>>参数名区分大小写</li>
 * </ul>
 * <p>客户端使用私钥 对stringA进行RSA加密 得到sign值signValue</p>
 * @author duanshichao@ehaier.com
 *
 */
public class SignUtil {

    private static Logger logger = LoggerFactory.getLogger(SignUtil.class);

    public static final String SIGNTYPE_MD5 = "MD5";
    public static final String SIGNTYPE_RSA = "RSA";
    public static final String CHARSET_UTF8 = "UTF-8";

    public static final String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnvNDmVwuOaaPDDNqQ/fUcaYFInYBEwW69Esl3EFV7eItK3ogwLDs5gjV+LtxBt1jNDYvhQw4n2XCWMgtk77DaGgnCyGplLtivzgrBQqEfJJ0TJHW6snhVG5o2lfn+wf6H6/4WCWo/JBOVlXNIK1jbSngAikq6gkqcZWq98S+/3QIDAQAB";
    public static final String pri = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKe80OZXC45po8MM2pD99RxpgUidgETBbr0SyXcQVXt4i0reiDAsOzmCNX4u3EG3WM0Ni+FDDifZcJYyC2TvsNoaCcLIamUu2K/OCsFCoR8knRMkdbqyeFUbmjaV+f7B/ofr/hYJaj8kE5WVc0grWNtKeACKSrqCSpxlar3xL7/dAgMBAAECgYBkAI4KxZp+LT0U7HTiPIYFimEpib0PQMNkte6mrXZJRnTFDibjkXs9rgN2Vzm5dDXdClZ61h96YohdAzkH1F76sEbxhydNfjfIwmvN1SMnlNFqH+QBQCbR5zv2NAOATo5pkJyy76NM3amlwIfcdFfenVOB0AfORfu0/GKDUaXtgQJBAOa+3mrlKWgCcWH4ZfZs85jf6YXuy25mMj1B5aoL6xqVKUdcDKzT2xVJPKldg75Q0SEe+2UxpUcptjKVyXByvtMCQQC6GI+RkGuLQfcsnk5K1aIPCNu4btDWXRPj6kJZoCiAhvvlzCfkTd60fL/M9y0Svh0aIhf90+eku9/AM4P9oziPAkEAoQW1ZNdCi+iYgwu667qgS6CF8AbyQ7pheMoRoRbsHALXaYrc9YZqLnKLOI2fsCHprrlWKgn5Eq4TDT3l8ujALQJBAK3k9YNYKKZgZdBTvWVAa8cQyAB3zL4DC+sVpBqMBkzT1d7N/xjfixOs3q1w67Vv4VHiOfj+Yp8Uz+iSOuxv8RECQQCVEDkRu1/DuLozA2Ca5zfKpvjNtZ7HT7pDjWcP1Q4UpO3t//vjNZNwmJBiUzNFMEUblQTMn4boot8JUdeHNkAG";

    /**
     * 签名
     * @param bean
     * @param signType
     * @param key
     */
    public static String sign(Object bean, String signType, String key) {
        TreeMap<String, Object> treeMap;
        String signResult = null;
        try {
            treeMap = convert2Map(bean);
            String signTempStr = getSignTempStr(treeMap);

            if (StringUtils.equals(SIGNTYPE_RSA, signType)) {
                signResult = RSA.sign(signTempStr, key, CHARSET_UTF8);
            } else if (StringUtils.equals(SIGNTYPE_MD5, signType)) {
                signTempStr += "&key=" + key;
                signResult = DigestUtils.md5Hex(signTempStr.getBytes()).toUpperCase();
            }
            logger.info("【待签名的串】" + signTempStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signResult;
    }

    /**
     * 验签
     * @param bean
     * @param sign
     * @param signType
     * @param key
     */
    public static Boolean signVerify(Object bean, String sign, String signType, String key) {
        boolean flag = false;
        TreeMap<String, Object> treeMap;
        try {
            treeMap = convert2Map(bean);
            String signTempStr = getSignTempStr(treeMap);
            if (StringUtils.equals(SIGNTYPE_RSA, signType)) {
                flag = RSA.verify(signTempStr, sign, key, CHARSET_UTF8);
            } else if (StringUtils.equals(SIGNTYPE_MD5, signType)) {
                signTempStr += "&key=" + key;
                String genSign = DigestUtils.md5Hex(signTempStr.getBytes()).toUpperCase();
                flag = StringUtils.equals(sign, genSign);
            }
            logger.info("待签名的串" + signTempStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 获取 待签名串
     * @param treeMap
     */
    private static String getSignTempStr(TreeMap<String, Object> treeMap) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
            if (!StringUtils.equals("sign", entry.getKey())) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        String StringTemp = sb.toString();
        if (StringTemp.length() > 0) {
            StringTemp = StringTemp.substring(0, StringTemp.length() - 1);
        }
        return StringTemp;
    }

    /**
     *  obj to TreeMap
     * @param bean
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private static TreeMap<String, Object> convert2Map(Object bean) throws IntrospectionException,
                                                                    IllegalAccessException,
                                                                    InvocationTargetException {
        @SuppressWarnings("rawtypes")
        Class type = bean.getClass();
        TreeMap<String, Object> returnMap = new TreeMap<String, Object>();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        for (int i = 0; i < propertyDescriptors.length; i++) {

            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if (result == null)
                    continue;
                if (result instanceof String || result instanceof Integer) {
                    if (!com.alibaba.dubbo.common.utils.StringUtils.isBlank(String.valueOf(result))) {
                        returnMap.put(propertyName, result);
                    }
                } else if (result instanceof BigDecimal) {
                    returnMap.put(propertyName, result);
                } else {
                    if (result instanceof List) {
                        if (CollectionUtils.isEmpty((Collection<?>) result)) {
                            continue;
                        }
                    } else if (result instanceof Map) {
                        if (((Map<?, ?>) result).isEmpty()) {
                            continue;
                        }
                    }
                    returnMap.put(propertyName, PayCenterJsonUtils.obj2JsonString(result));
                }
            }
        }
        return returnMap;
    }

    //    public static void main(String[] args) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
    //        /*array(13) { ["appId"]=> string(5) "10005" 
    //         * ["backType"]=> int(13) 
    //         * ["benefitList"]=> string(166) "
    //         * [{"amt":"300.00","benefitType":"diamond","count":"30"},
    //         * {"amt":"200.00","benefitType":"seashell","count":"20"},
    //         * {"amt":"100.00","benefitType":"insurance","count":"10"}]" 
    //         * ["charset"]=> string(5) "UTF-8" 
    //         * ["memberId"]=> string(9) "138990962" 
    //         * ["num"]=> int(2) 
    //         * ["orderProductId"]=> string(14) "WD170717693343" 
    //         * ["orderProductIdN"]=> string(14) "WD170717693344" 
    //         * ["orderSn"]=> string(15) "170717152728092" 
    //         * ["payAmt"]=> float(300) 
    //         * ["sign"]=> string(32) "5B961585A348D96BC95A2440D8D1AF30"
    //         *  ["signType"]=> string(3) "MD5" 
    //         *  ["ts"]=> string(13) "1500366632145" } 
    //        */
    //        BenefitTypeReqVO req = new BenefitTypeReqVO();
    //        req.setAppId("10005");
    //        req.setBackType("11");
    //        List<Map<String, String>> benefitList = new ArrayList<Map<String,String>>();
    //        Map<String, String> map1 = new HashMap<String, String>();
    //        map1.put("amt", "300.00");
    //        map1.put("benefitType", "diamond");
    //        map1.put("count", "30");
    //        benefitList.add(map1);
    //        Map<String, String> map2 = new HashMap<String, String>();
    //        map2.put("benefitType", "seashell");
    //        map2.put("amt", "200.00");
    //        map2.put("count", "20");
    //        benefitList.add(map2);
    //        Map<String, String> map3 = new HashMap<String, String>();
    //        map3.put("amt", "200.00");
    //        map3.put("count", "20");
    //        map3.put("benefitType", "insurance");
    //        benefitList.add(map3);
    //        //req.setBenefitList(benefitList);
    //        req.setCharset("UTF-8");
    //        req.setMemberId(138990962);
    //        //req.setNum(2);
    //        req.setOrderProductId("WD170718693539");
    //        //req.setOrderProductIdN("WD170717693344");
    //        req.setOrderSn("D17071815420797456");
    //        //req.setPayAmt(new BigDecimal("300"));
    //        req.setSignType("MD5");;
    //        req.setTs("1500372757197");
    //        String signTempStr = "appId=10005&backType=11&charset=UTF-8&memberId=138990962&orderProductId=WD170718693539&orderSn=D17071815420797456&signType=MD5&ts=1500372757197&key=7B0A81D45D640E6830C12A4AA3D06A4C";
    //        //System.out.println(DigestUtils.md5Hex(signTempStr.getBytes()).toUpperCase());
    //        /*B4DD25064EAAE0FA964F28408B85B73A
    //        B4DD25064EAAE0FA964F28408B85B73A
    //        B4DD25064EAAE0FA964F28408B85B73A*/
    //        System.out.println(JsonUtils.obj2JsonString(req));
    //        System.out.println("原始json"+JSON.toJSONString(req));
    //        System.out.println(sign(req, SIGNTYPE_RSA, pri));
    //        System.out.println("【原始json】"+JSON.toJSONString(req));
    //        System.out.println("【key】"+"7B0A81D45D640E6830C12A4AA3D06A4C");
    //        String sign = sign(req, SIGNTYPE_MD5, "7B0A81D45D640E6830C12A4AA3D06A4C");
    //        System.out.println("【签名结果】"+sign);
    //        System.out.println(signVerify(req,sign, SIGNTYPE_MD5, "7B0A81D45D640E6830C12A4AA3D06A4C"));
    //    }

}
