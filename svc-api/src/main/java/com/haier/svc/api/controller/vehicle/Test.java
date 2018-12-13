package com.haier.svc.api.controller.vehicle;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.ArrayList;
public class Test {
    public static void main(String[] args) throws Exception{
        //请求地址
        //String url="http://qimen.api.taobao.com/router/qimen/service?app_key=12116339&customerId=2998123754&format=xml&method=taobao.qimen.stockout.create&sign_method=md5&timestamp=2018-02-09 11:25:41&v=2.0&sign=05110AFA50FC195AE55724A62FC4608E";
        //String url="http://qimen.api.taobao.com/router/qimen/service?app_key=12116339&customerId=2998123754&format=xml&method=taobao.qimen.stockout.create&sign_method=md5&timestamp=2018-02-09%2011:25:41&v=2.0&sign=05110AFA50FC195AE55724A62FC4608E";
        String url="http://qimenapi.tbsandbox.com/router/qimen/service?app_key=12116339&customerId=2998123754&format=xml&method=taobao.qimen.stockout.create&sign_method=md5&timestamp=2018-04-03+11:00:15&v=2.0&sign=BA00B6CF25A3B11DECAD319FCAAB19BF";
        //String url="https://qimenapi.tbsandbox.com/top/router/qimen/service";
        //请求参数
        String xmlString="<request><deliveryOrder><totalOrderLines></totalOrderLines><deliveryOrderCode>WD170812076579TC2</deliveryOrderCode><orderType>PTCK</orderType><orderSourceType>TM</orderSourceType><warehouseCode>QDHEWL-0081</warehouseCode><createTime>2018-02-08 14:00:36</createTime><scheduleDate>2018-03-10 14:00:36</scheduleDate><logisticsCode></logisticsCode><logisticsName></logisticsName><supplierCode></supplierCode><supplierName></supplierName><transportMode></transportMode><remark>3800000008054685；产业责任；机编：DH1VF0D3201D9H3L2583</remark><relatedOrders><relatedOrder><orderType></orderType><orderCode></orderCode></relatedOrder></relatedOrders><pickerInfo><company></company><name></name><tel></tel><mobile></mobile><id></id><carNo></carNo></pickerInfo><senderInfo><company></company><name></name><tel></tel><mobile></mobile><email></email><countryCode></countryCode><province></province><city></city><area></area><town></town><detailAddress></detailAddress><id></id></senderInfo><receiverInfo><company></company><name>郭辉辉</name><zipCode></zipCode><tel></tel><mobile>15805163964</mobile><email></email><countryCode></countryCode><province>山东</province><city>青岛市</city><area>崂山区</area><town></town><detailAddress>详细地址</detailAddress><id></id></receiverInfo></deliveryOrder><orderLines><orderLine><outBizCode></outBizCode><orderLineNo>1</orderLineNo><ownerCode>2998123754</ownerCode><itemCode>DH1VF0D32</itemCode><itemId>548480740844</itemId><itemName></itemName><inventoryType>JSDJD</inventoryType><planQty>1</planQty><batchCode></batchCode><productDate></productDate><expireDate></expireDate><produceCode></produceCode></orderLine></orderLines></request>";
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type","text/html;charset=UTF-8");
        StringEntity stringEntity=new StringEntity(xmlString,"utf-8");
        httpPost.setEntity(stringEntity);
        HttpResponse response= client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity!=null){
            String xml=EntityUtils.toString(entity);
            System.out.println("返回的xml:"+xml);
            System.out.println("");
        }
    }
}
