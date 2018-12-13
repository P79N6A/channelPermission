package com.haier.afterSale.services;

import com.taobao.pac.sdk.cp.PacClient;
import com.taobao.pac.sdk.cp.SendSysParams;
import com.taobao.pac.sdk.cp.dataobject.request.ERP_ORDER_CANCEL_NOTIFY.ErpOrderCancelNotifyRequest;
import com.taobao.pac.sdk.cp.dataobject.request.order_cancel.OrderCancelRequest;
import com.taobao.pac.sdk.cp.dataobject.response.ERP_ORDER_CANCEL_NOTIFY.ErpOrderCancelNotifyResponse;
import com.taobao.pac.sdk.cp.dataobject.response.order_cancel.OrderCancelResponse;

public class Test2 {
    public static void main(String[] args) throws Exception{
    	ErpOrderCancelNotifyRequest request = new ErpOrderCancelNotifyRequest();
    	request.setOwnerUserId("2998123754");
    	request.setOrderCode("LBX0127541814747181");
    	request.setOrderType("901");
    	request.setOutOrderCode("DB1523259702050821666");
    	 //LINK 方式接入 生产环境
        String appKey = "009658";
        String fromCode = "2998123754";
        String secretKey = "Vi3z88ZEy5j9W28s4JA0O3X29r159S06";
        String pacUrl = "http://link.cainiao.com/gateway/link.do";

        PacClient pacClient = new PacClient(appKey, secretKey, pacUrl);
        SendSysParams sysParams = new SendSysParams();
        sysParams.setFromCode(fromCode);
        
        ErpOrderCancelNotifyResponse linkResponse = pacClient.send(request, sysParams);
        System.out.println(linkResponse);

    }
   
}
