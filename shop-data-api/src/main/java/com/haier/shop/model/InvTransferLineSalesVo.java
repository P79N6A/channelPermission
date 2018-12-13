package com.haier.shop.model;


public class InvTransferLineSalesVo extends InvTransferLineSales {

    private String createTimeMin;

    private String createTimeMax;


    public String getCreateTimeMin() {
        return createTimeMin;
    }

    public void setCreateTimeMin(String createTimeMin) {
        this.createTimeMin = createTimeMin;
    }

    public String getCreateTimeMax() {
        return createTimeMax;
    }

    public void setCreateTimeMax(String createTimeMax) {
        this.createTimeMax = createTimeMax;
    }
}
