package com.haier.purchase.data.model;

import java.io.Serializable;

public class PurchaseOrderInfoItem implements Serializable {
    private String order_id;
    private String ed_channel_id;
    private String materials_id;
    private String storage_id;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getEd_channel_id() {
        return ed_channel_id;
    }

    public void setEd_channel_id(String ed_channel_id) {
        this.ed_channel_id = ed_channel_id;
    }

    public String getMaterials_id() {
        return materials_id;
    }

    public void setMaterials_id(String materials_id) {
        this.materials_id = materials_id;
    }

    public String getStorage_id() {
        return storage_id;
    }

    public void setStorage_id(String storage_id) {
        this.storage_id = storage_id;
    }

}
