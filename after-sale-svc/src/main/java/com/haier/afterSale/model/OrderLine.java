package com.haier.afterSale.model;

public class OrderLine {
    private String outBizCode="";
    private String orderLineNo="";
    private String ownerCode="";
    private String itemCode="";
    private String itemId="";
    private String itemName="";
    private String inventoryType="";
    private String planQty="";
    private String batchCode="";

    public void setOutBizCode(String outBizCode) {
        this.outBizCode = outBizCode;
    }

    public void setOrderLineNo(String orderLineNo) {
        this.orderLineNo = orderLineNo;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public void setPlanQty(String planQty) {
        this.planQty = planQty;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public void setProduceCode(String produceCode) {
        this.produceCode = produceCode;
    }

    public String getOutBizCode() {
        return outBizCode;

    }

    public String getOrderLineNo() {
        return orderLineNo;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public String getPlanQty() {
        return planQty;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public String getProductDate() {
        return productDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public String getProduceCode() {
        return produceCode;
    }

    private String productDate="";
    private String expireDate="";
    private String produceCode="";
}
