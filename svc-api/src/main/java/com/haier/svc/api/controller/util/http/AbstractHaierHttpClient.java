package com.haier.svc.api.controller.util.http;

import com.haier.eop.data.model.OrdersQueue;
import com.haier.eop.data.model.QueueOrderHelper;
import com.haier.svc.api.controller.util.DateUtil;
import com.haier.svc.api.controller.util.StringUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaojian
 * @date 2018/05/29
 *
 */
public abstract class AbstractHaierHttpClient {
    private static final Logger logger = LogManager.getLogger(AbstractHaierHttpClient.class);
    public int connectTimeout = 30000;
    public int readTimeout = 60000;
    public final static String ORDER_QUEUE_STATA="1001";
    public final static String ORDER_FINAL_QUEUE_STATA="10051";
    public final static String CHARSET_UTF8="UTF-8";
    public DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public final String excute(String dataJson,String url) {
        String result = "";
        HttpClientProvider p = null;
        Map<String, String> map = new HashMap<String, String>();
        try {
            p = new HttpClientProvider();
            result = p.setUrl(url).setPostMethod().setRequestBody(dataJson)
                    .setHeadParams(map).setConnectTimeout(connectTimeout)
                    .setSocketTimeout(readTimeout).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public final String excute(String url,String xmlData,String reqType,Map<String,String> headMap) {
        String result = "";
        HttpClientProvider p = null;
        try {
            p = new HttpClientProvider();
            result = p.setUrl(url).setPostMethod().setRequestBody(xmlData)
                    .setHeadParams(headMap).setReqType(reqType).setConnectTimeout(connectTimeout)
                    .setSocketTimeout(readTimeout).send();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 执行与第三方接口交付操作(http请求)
     * @param formparams
     * @return
     */
    public final String excute(Map<String, String> formparams,String url) {
        String result = "";
        HttpClientProvider p=null;
        try {
            p=new HttpClientProvider();
            result =p.setUrl(url).setPostMethod()
                    .setParams(formparams).setConnectTimeout(connectTimeout)
                    .setSocketTimeout(readTimeout).send();
        } catch (Exception e) {
            result="error";
            logger.info("AbstractHaierHttpClient类excute异常(2个参数其中一个url)------->" + e.getMessage());
            e.getMessage();
        }
        return result;
    }

    /**
     * 执行与第三方接口交付操作(https请求)
     * @param formparams
     * @param url https请求URL
     * @param keystorePath https密钥文件地址
     * @param password https密钥文件密码
     * @return
     */
    public final String excuteHttps(Map<String, String> formparams,String url,String keystorePath,String password) {
        String result = "";
        try {
            HttpClientProvider p=new HttpClientProvider();
            result =p.setUrl(url).setPostMethod().setHttp(false).setKeystorePath(keystorePath)
                    .setPassword(password)
                    .setParams(formparams).setConnectTimeout(connectTimeout)
                    .setSocketTimeout(readTimeout).send();
        } catch (Exception e) {
            result="error";
            logger.info("AbstractHaierHttpClient类excuteHttps异常------->" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 执行与第三方接口交付操作(https请求)
     * @param formparams
     * @param url https请求URL
     * @return
     */
    public final String excuteAmazonHttps(Map<String, String> formparams,String url) {
        String result = "";
        try {
            HttpClientProvider p=new HttpClientProvider();
            Map<String, String> headParams = new HashMap<String, String>();
            result =p.setUrl(url).setPostMethod().setHttp(true).setHeadParams(headParams)
                    .setParams(formparams).setConnectTimeout(connectTimeout)
                    .setSocketTimeout(readTimeout).send();
        } catch (Exception e) {
            result="error";
            logger.info("AbstractHaierHttpClient类excuteAmazonHttps异常------->" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 执行与第三方接口交付操作(https请求)
     * @param formparams
     * @param url https请求URL
     * @return
     */
    public final String excute(Map<String, String> formparams,Map<String, String> headParams,String url) {
        String result = "";
        try {
            HttpClientProvider p=new HttpClientProvider();
            result =p.setUrl(url).setPostMethod().setHeadParams(headParams)
                    .setParams(formparams).setConnectTimeout(connectTimeout)
                    .setSocketTimeout(readTimeout).send();
        } catch (Exception e) {
            result="error";
            logger.info("AbstractHaierHttpClient类excute异常(url)------->" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    /**
     * @param requestBody //请求报文
     * @param headParams  //头部信息
     * @param url    //请求url
     * @param reqType  请求类型 json/xml
     * @return
     */
    public final String excute(String requestBody,Map<String, String> headParams,String url,String reqType) {
        String result = "";
        try {
            HttpClientProvider p=new HttpClientProvider();
            result =p.setUrl(url).setPostMethod().setHeadParams(headParams)
                    .setRequestBody(requestBody).setReqType(reqType)
                    .setConnectTimeout(connectTimeout)
                    .setSocketTimeout(readTimeout).send();
        } catch (Exception e) {
            result="error";
            logger.info("AbstractHaierHttpClient类excute异常(reqType)------->" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public final String excuteGet(String url) {
        String result = "";
        try {
            HttpClientProvider p=new HttpClientProvider();
            result =p.setUrl(url).setGetMethod()
                    .setConnectTimeout(connectTimeout)
                    .setSocketTimeout(readTimeout).send();
        } catch (Exception e) {
            result="error";
            logger.info("AbstractHaierHttpClient类excuteGet异常------->" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public final String excutePost(String url,String requestBody) {
        String result = "";
        HttpClientProvider p=null;
        try {
            p=new HttpClientProvider();
            result =p.setUrl(url).setPostMethod().setRequestBody(requestBody).
                    setConnectTimeout(connectTimeout)
                    .setSocketTimeout(readTimeout).send();
        } catch (Exception e) {
            result="error";
            logger.info("AbstractHaierHttpClient类excute异常(2个参数其中一个url)------->" + e.getMessage());
            e.getMessage();
        }
        return result;
    }

    public final String excuteUploadFile(String url,Map<String,Object> fileParams) {
        String result = "";
        try {
            HttpClientProvider p=new HttpClientProvider();
            result =p.setUrl(url).setPostMethod().setFile(true).setFileParams(fileParams)
                    .setConnectTimeout(connectTimeout)
                    .setSocketTimeout(readTimeout).send();
        } catch (Exception e) {
            result="error";
            logger.info("AbstractHaierHttpClient类excuteUploadFile异常------->" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    //校验下单时间
    public Long checkCreateTime(String orderSaleTime){
        try {
            if (StringUtil.isEmpty(orderSaleTime)) {
                throw new JobException("组装商城订单异常：下单时间不存在");
            }
            return DateUtil.toParse(orderSaleTime,DateUtil.DATE_FORMAT_YYYY_MM_DD_HMS).getTime()/1000;
        } catch (Exception e) {
            throw new JobException("组装商城订单异常：下单时间时间格式异常");
        }
    }
    //校验付款时间
    public Long checkPayTime(String orderPayTime){
        try {
            if (StringUtil.isEmpty(orderPayTime)) {
                return 0L;
            }
            return DateUtil.toParse(orderPayTime,DateUtil.DATE_FORMAT_YYYY_MM_DD_HMS).getTime()/1000;
        } catch (Exception e) {
            throw new JobException("组装商城订单异常：订单付款时间格式异常");
        }
    }

    //校验付款金额
    public BigDecimal checkPayAmount(String orderPayAmount){
        try {
            if(StringUtil.isEmpty(orderPayAmount)){
                throw new JobException("组装商城订单异常：付款金额不存在");
            }
            return new BigDecimal(orderPayAmount).setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            throw new JobException("组装商城订单异常：付款金额格式异常");
        }
    }
    //校验商品价格
    public BigDecimal checkProductPrice(String productPrice){
        try {
            if (StringUtil.isEmpty(productPrice)) {
                throw new JobException("组装商城网单异常：商品价格不存在");
            }
            return new BigDecimal(productPrice).setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            throw new JobException("组装商城网单异常：商品价格格式异常"+productPrice);
        }
    }

    //校验商品数量
    public Long checkProductNum(String productNum){
        try {
            if (StringUtil.isEmpty(productNum)) {
                throw new JobException("组装商城网单异常：商品数量不存在");
            }
            if(productNum.lastIndexOf(".")>0){
                productNum=productNum.substring(0,productNum.lastIndexOf("."));
            }
            return Long.parseLong(productNum);
        } catch (Exception e) {
            throw new JobException("组装商城网单异常：商品数量异常:"+e.getMessage());
        }
    }


    public String getOrderByOrderIds(List list) {
        return null;
    }

    public String getOrderByOrderId(String orderId) {
        return null;
    }


    public List<QueueOrderHelper> conventOrdersList(String xmlStr){
        return null;
    }
    public OrdersQueue conventOrdersQueue(String xmlStr){
        return null;
    }
}
