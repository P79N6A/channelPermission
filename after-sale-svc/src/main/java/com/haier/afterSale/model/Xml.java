package com.haier.afterSale.model;

import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.List;

public class Xml {
    public static void main(String[] args) {
        testBean2XML();
    }
    /**
     * 利用XStream在Java对象和XML之间相互转换
     */
    public static void testBean2XML() {
        System.out.println("将Java对象转换为xml！\n");
        Request request = getRequest();
        XStream xstream = new XStream();
        xstream.alias("request", Request.class);
        xstream.alias("deliveryOrder", DeliveryOrder.class);
        xstream.alias("relatedOrders",RelatedOrders.class);
        xstream.alias("relatedOrder",RelatedOrder.class);
        xstream.alias("pickerInfo",PickerInfo.class);
        xstream.alias("senderInfo",SenderInfo.class);
        xstream.alias("receiverInfo",ReceiverInfo.class);
        String xml = xstream.toXML(request);
        System.out.println(xml);
    }
    public static Request getRequest(){
        Request request=new Request();
        OrderLines orderLines=new OrderLines();
        orderLines.setOrderLine(getOrderLine());
        DeliveryOrder deliveryOrder=new DeliveryOrder();
        deliveryOrder.setOrderType("PTCK");
        deliveryOrder.setWarehouseCode("QDHEWL-0001");
        deliveryOrder.setCreateTime("2018-03-28 13:41:18");
        deliveryOrder.setScheduleDate("2018-04-27 13:41:18");
        deliveryOrder.setRemark("LBX0227541647045207；；机编：BH034009B00BAHA8DJ1D");
        deliveryOrder.setRelatedOrders(getrelatedOrders());
        deliveryOrder.setPickerInfo(getPickerInfo());
        deliveryOrder.setSenderInfo(getSenderInfo());
        deliveryOrder.setReceiverInfo(getReceiverInfo());
        request.setDeliveryOrder(deliveryOrder);
        request.setOrderLines(orderLines);
        return request;
    }
    public static RelatedOrders getrelatedOrders(){
        RelatedOrders relatedOrders=new RelatedOrders();
        relatedOrders.setRelatedOrder(getRelatedOrder());
        return relatedOrders;
    }
    public static RelatedOrder getRelatedOrder(){
        RelatedOrder relatedOrder=new RelatedOrder();
        relatedOrder.setOrderCode("");
        relatedOrder.setOrderType("");
        return relatedOrder;
    }
    public static PickerInfo getPickerInfo(){
        PickerInfo pickerInfo=new PickerInfo();
        pickerInfo.setCarNo("");
        pickerInfo.setCompany("");
        pickerInfo.setId("");
        return pickerInfo;
    }
    public static SenderInfo getSenderInfo(){
        SenderInfo senderInfo=new SenderInfo();
        senderInfo.setArea("");
        return senderInfo;
    }
    public static ReceiverInfo getReceiverInfo(){
        ReceiverInfo receiverInfo=new ReceiverInfo();
        receiverInfo.setArea("");
        return receiverInfo;
    }
    public static OrderLine getOrderLine(){
        OrderLine orderLine=new OrderLine();
        return orderLine;
    }
}
