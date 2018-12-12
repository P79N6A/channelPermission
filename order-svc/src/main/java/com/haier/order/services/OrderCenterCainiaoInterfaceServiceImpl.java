package com.haier.order.services;

import com.haier.dbconfig.model.*;
import com.haier.order.util.CainiaoInterfaceSender;
import com.haier.order.util.SerializedPhpParser;
import com.haier.order.util.XMLUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.haier.dbconfig.model.InterfaceEnum.STOCK_QUERY;

@Service
public class OrderCenterCainiaoInterfaceServiceImpl {

    private static final Logger logger = LogManager.getLogger(OrderCenterCainiaoInterfaceServiceImpl.class);

    // 奇门url请求地址,系统注入
//    public String targetUrl;
//	// 测试环境地址
//	public String targetUrl = "http://qimenapi.tbsandbox.com/router/qimen/service";
//	// 生产环境地址
	public String targetUrl = "http://qimen.api.taobao.com/router/qimen/service";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    /**
     * 通过枚举获取访问奇门的接口，返回为对应接口的对象
     *
     * @param iEnum  枚举类型
     * @param config 认证信息
     * @param request   入参对象
     * @return
     */
    public Object getMethodInfo(InterfaceEnum iEnum, SyncOrderConfigs config, Object request) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        Map<String, String> map = new HashMap<String, String>();
        String appKey = "";
        String session = "";
        String secret = "";
        if (config == null) {
			// 测试环境认证信息，发生产需要重新配置
//	        appKey = "1012116339";
//	        session = "610051337844017312dff1034cd53c113b1badb7cee05582074082787";
//	        secret = "sandbox6cd58b11d33b8e8df540666f2";
////			map.put("customerId", "4704173966");
//	        map.put("customerId", "3629369251");
            // 生产环境认证信息，发生产需要重新配置,customerId待生成
            appKey = "12116339";
            session = "610261676856f4e42f6cdd0114720e97b61fcf877d31f0b470168984";
            secret = "9274e906cd58b11d33b8e8df540666f2";
            map.put("customerId", "2998123754");
        } else {
            appKey = getValueByKey(config.getConfigValue(), "appKey").toString();
            session = getValueByKey(config.getConfigValue(), "session").toString();
            secret = getValueByKey(config.getConfigValue(), "secret").toString();
            map.put("customerId", config.getCustomerid());
        }
        map.put("session", session);
        map.put("method", iEnum.getInterfaceName());
        String time = sdf.format(new Date());
        map.put("timestamp", time);
        map.put("format", "xml");
        map.put("app_key", appKey);
        map.put("v", "2.0");
        map.put("sign_method", "md5");
        XMLUtils<ReturnOrderRequest> xmlRequest = new XMLUtils<ReturnOrderRequest>();
        // 通过接口类型确定传入的对象类型
        String xml = xmlRequest.beanToXML((ReturnOrderRequest) request, ReturnOrderRequest.class);
        System.out.println(xml);
        logger.info("时间:" + time + ";奇门接口请求方法：" + iEnum.getInterfaceName() + ";奇门接口请求数据：" + xml);
        String sign = getSign(map, secret, xml);
        map.put("sign", sign);
        Object o = new Object();
        Result result = new Result();
        logger.info("奇门接口请求方法map=" + map);
        try {
            result = CainiaoInterfaceSender.sendToCainiao(httpClient, targetUrl, map, xml);
            System.out.println(result.getMessage());
        } catch (Exception e) {
            result.setStatus(false);
            result.setMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            logger.info("时间:" + time + ";奇门接口返回数据：" + result.getMessage());
        }
        switch (iEnum) {
            case STOCK_QUERY:
                if (result != null) {
                    if (result.getStatus()) {
                        XMLUtils<ChanneLinventoryResponse> xmlResponse = new XMLUtils<ChanneLinventoryResponse>();
                        o = xmlResponse.XMLStringToBean(result.getMessage(), ChanneLinventoryResponse.class);
                    }
                }
                break;
            default:
                break;
        }

        return o;
    }


    /**
     * 通过枚举获取访问奇门的接口，返回为对应接口的对象
     *
     * @param iEnum  枚举类型
     * @param config 认证信息
     * @param request   入参对象
     * @return
     */
//    public Object getMethodInfo(InterfaceEnum iEnum, SyncOrderConfigs config, Object request, Class<T> cla) {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        Map<String, String> map = new HashMap<String, String>();
//        String appKey = "";
//        String session = "";
//        String secret = "";
//        if (config == null) {
////			// 测试环境认证信息，发生产需要重新配置
////	        appKey = "1012116339";
////	        session = "610051337844017312dff1034cd53c113b1badb7cee05582074082787";
////	        secret = "sandbox6cd58b11d33b8e8df540666f2";
//
//	        appKey = "1012116339";
//	        session = "610051337844017312dff1034cd53c113b1badb7cee05582074082787";
//	        secret = "sandbox6cd58b11d33b8e8df540666f2";
//          map.put("customerId", "3629369251");
//
////			// 生产环境认证信息，发生产需要重新配置,customerId待生成
////	        appKey = "12116339";
////	        session = "610261676856f4e42f6cdd0114720e97b61fcf877d31f0b470168984";
////	        secret = "9274e906cd58b11d33b8e8df540666f2";
////			map.put("customerId", "2998123754");
//        } else {
//            appKey = getValueByKey(config.getConfigValue(), "appKey").toString();
//            session = getValueByKey(config.getConfigValue(), "session").toString();
//            secret = getValueByKey(config.getConfigValue(), "secret").toString();
//            map.put("customerId", config.getCustomerid());
//        }
//        map.put("session", session);
//        map.put("method", iEnum.getInterfaceName());
//        String time = sdf.format(new Date());
//        map.put("timestamp", time);
//        map.put("format", "xml");
//        map.put("app_key", appKey);
//        map.put("v", "2.0");
//        map.put("sign_method", "md5");
//        XMLUtils<T> xmlRequest = new XMLUtils<T>();
//        // 通过接口类型确定传入的对象类型
//        String xml = xmlRequest.beanToXML((T) request, cla);
//        System.out.println(xml);
//        logger.info("时间:" + time + ";奇门接口请求方法：" + iEnum.getInterfaceName() + ";奇门接口请求数据：" + xml);
//        String sign = getSign(map, secret, xml);
//        map.put("sign", sign);
//        Object o = new Object();
//        Result result = new Result();
//        logger.info("奇门接口请求方法map=" + map);
//        try {
//            result = CainiaoInterfaceSender.sendToCainiao(httpClient, targetUrl, map, xml);
//            System.out.println(result.getMessage());
//        } catch (Exception e) {
//            result.setStatus(false);
//            result.setMessage(e.getMessage());
//            e.printStackTrace();
//        } finally {
//            logger.info("时间:" + time + ";奇门接口返回数据：" + result.getMessage());
//        }
//        switch (iEnum) {
//            case STOCK_QUERY:
//                if (result != null) {
//                    if (result.getStatus()) {
//                        XMLUtils<ChanneLinventoryResponse> xmlResponse = new XMLUtils<ChanneLinventoryResponse>();
//                        o = xmlResponse.XMLStringToBean(result.getMessage(), ChanneLinventoryResponse.class);
//                    }
//                }
//                break;
//            default:
//                break;
//        }
//
//        return o;
//    }


    /**
     * 通过入参获取认证的密文，通过md5加密
     * 加密逻辑是对所有参数的参数名进行升序排序，然后合并再进行md5处理
     *
     * @param map
     * @return
     */
    private String getSign(Map<String, String> map, String secret, String body) {
        List<Map.Entry<String, String>> keys =
                new ArrayList<Map.Entry<String, String>>(map.entrySet());
        // 对象内容进行排序
        Collections.sort(keys, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });
        StringBuffer sb = new StringBuffer();
        sb.append(secret);
        for (Map.Entry<String, String> key : keys) {
            sb.append(key.getKey()).append(key.getValue());
        }
        sb.append(body).append(secret);
        byte[] abstractMesaage = digest(sb.toString());
        // 4. 把二进制转换成大写的十六进制
        String sign = byte2Hex(abstractMesaage);
        return sign;
    }

    private byte[] digest(String message) {
        try {
            MessageDigest md5Instance = MessageDigest.getInstance("MD5");
            md5Instance.update(message.getBytes("UTF-8"));
            return md5Instance.digest();
        } catch (UnsupportedEncodingException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        return null;
    }


    /**
     * md5生成
     *
     * @param bytes
     * @return
     */
    private String byte2Hex(byte[] bytes) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        int j = bytes.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (byte byte0 : bytes) {
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }

    /**
     * 根据key值获取对应的值
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unused")
    public Object getValueByKey(String input, String key) {
        SerializedPhpParser serializedPhpParser = new SerializedPhpParser(input);
        Object result = serializedPhpParser.parse();

        if (result == null) {
            throw new RuntimeException("php serialized 字符串无法解析：" + input);
        }

        if (result != null) {
            @SuppressWarnings("rawtypes")
            Map myType = (Map) result;
            if (myType == null) {
                throw new RuntimeException("php serialized 字符串不是数组：" + result.toString());
            } else {
                return myType.get(key);
            }
        }
        return null;
    }

}
