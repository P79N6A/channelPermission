package com.haier.shop.model;

/**
 * @Author:JinXueqian
 * @Date: 2018/8/8 17:31
 */
public class ExternalOrdersVo extends ExternalOrders {

    private String shopName;
    private String syncTimeStr;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getSyncTimeStr() {
        return syncTimeStr;
    }

    public void setSyncTimeStr(String syncTimeStr) {
        this.syncTimeStr = syncTimeStr;
    }
}
