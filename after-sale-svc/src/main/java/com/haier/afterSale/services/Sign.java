package com.haier.afterSale.services;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

public class Sign {
    public static void main(String[] args) {
        //String xml="<request><deliveryOrder><totalOrderLines></totalOrderLines><deliveryOrderCode>WD170724408403TC3</deliveryOrderCode><orderType>PTCK</orderType><orderSourceType>TM</orderSourceType><warehouseCode>QDHEWL-0002</warehouseCode><createTime>2018-04-26 15:41:32</createTime><scheduleDate>2018-05-26 15:41:32</scheduleDate><logisticsCode></logisticsCode><logisticsName></logisticsName><supplierCode></supplierCode><supplierName></supplierName><transportMode></transportMode><remark>3800000008457878；产业责任；机编：BH02W10AE00BAH55PH85</remark><relatedOrders><relatedOrder><orderType></orderType><orderCode></orderCode></relatedOrder></relatedOrders><pickerInfo><company></company><name></name><tel></tel><mobile></mobile><id></id><carNo></carNo></pickerInfo><senderInfo><company></company><name></name><tel></tel><mobile></mobile><email></email><countryCode></countryCode><province></province><city></city><area></area><town></town><detailAddress></detailAddress><id></id></senderInfo><receiverInfo><company></company><name>郭辉辉</name><zipCode></zipCode><tel></tel><mobile>15805163964</mobile><email></email><countryCode></countryCode><province>山东</province><city>青岛市</city><area>崂山区</area><town></town><detailAddress>详细地址</detailAddress><id></id></receiverInfo></deliveryOrder><orderLines><orderLine><outBizCode></outBizCode><orderLineNo>1</orderLineNo><ownerCode>2998123754</ownerCode><itemCode>BH02W10AE</itemCode><itemId>539190512538</itemId><itemName></itemName><inventoryType>JSDJD</inventoryType><planQty>1</planQty><batchCode></batchCode><productDate></productDate><expireDate></expireDate><produceCode></produceCode></orderLine></orderLines></request>";
        String xml="hello1234";
        String charset="utf-8";
        String keys="key123";
        String XX=doSign(xml,charset,keys);
        System.out.println(XX);
    }
    public static String doSign(String content, String charset, String keys) {
        String sign = "";
        content = content + keys;
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes(charset));
            sign = new String(Base64.encodeBase64(md.digest()), charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sign;
    }
}
