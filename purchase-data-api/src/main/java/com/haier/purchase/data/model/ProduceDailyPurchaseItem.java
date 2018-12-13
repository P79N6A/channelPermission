package com.haier.purchase.data.model;

/**
 * Created by 钊 on 2014/6/4.
 */
public class ProduceDailyPurchaseItem extends PurchaseItem {
    private String receiver;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    private String deliveryToName;

    public String getDeliveryToName() {
        return deliveryToName;
    }

    public void setDeliveryToName(String deliveryToName) {
        this.deliveryToName = deliveryToName;
    }

    private String channel;

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    private String supplier;

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
