package com.haier.eop.data.model;

import java.io.Serializable;

/**
 * 3w间调拨单
 * 海尔处理流程  1. 全部出库/全部出库部分入库->全部入库->出库推SAP->入库推SAP(关闭)
 *             2. 全部入库->出库推SAP->入库推SAP(关闭)
 */
public class TransferOrder implements Serializable {

    private static final long serialVersionUID = 5753133742927385062L;

    private Integer id;
    private String messageIdStr;                 //菜鸟主键拼接
    private String fromStoreCode;                //出库仓编码
    private String toStoreCode;                    //入库仓编码
    private String expectStartTime;                //期望调拨开始时间
    private String attributes;                    //扩展属性,备注
    private String scItemCode;                    //货品编码
    private String inventoryType;                //库存类型(1:可销售库存.101:残次)
    private int count;                            //期望调拨数量
    private String transferOrderCode;            //调拨单号
    private String expectOutStoreTime;            //预计出库时间
    private String expectInStoreTime;            //预计入库时间
    private int orderStatus;                    //调拨单状态：
                                                // 0，为调拨单申请中状态；
                                                // 10，部分出库未入库；
                                                // 20，部分出库部分入库
                                                // 30，全部出库未入库；
                                                // 40，全部出库部分入库；
                                                // 100，已推送SAP，关闭;
                                                // 101，无需推送SAP，关闭;(同仓位调拨)
                                                // 110，人工介入状态
                                                // -100， 已取消
                                                // 120，创建未请求菜鸟接口
                                                // 140，调拨完成，出库入库未推送SAP
                                                // 150，调拨完成，入库未推送SAP

    private int outCount = 0;                        //实际出库数量
    private int inCount = 0;                        //实际入库数量
    private String orderCode;                    //外部ERP订单号
    private String productCate;                    //品类
    private String creater;                        //创建人
    private String createTime;                    //创建时间
    private int remnantNum = 0;                        //残次品数量
    private int machinelossNum = 0;                    //机损数量
    private int boxlossNum = 0;                        //箱损数量
    private int freeze = 0;                            //冻结数量
    private int pullon = 0;                            //调拨在途
    private String errorMessage;                //错误原因
    private String transferOutOrderCode;        //调拨出库单号
    private String transferInOrderCode;        //调拨入库单号
    private String confirmOutTime;                //出库确认时间
    private String confirmInTime;                //入库确认时间
    private String ownerCode;                    //货主编码
    private String itemNum;                        //行项目号，推送SAP使用，四位数字，中间两位-物料计数，后一位-调拨次数
    private String sapErrorMessage;                //推送SAP失败信息

    public String getMessageIdStr() {
        return messageIdStr;
    }

    public void setMessageIdStr(String messageIdStr) {
        this.messageIdStr = messageIdStr;
    }

    public int getMachinelossNum() {
        return machinelossNum;
    }

    public void setMachinelossNum(int machinelossNum) {
        this.machinelossNum = machinelossNum;
    }

    public int getBoxlossNum() {
        return boxlossNum;
    }

    public void setBoxlossNum(int boxlossNum) {
        this.boxlossNum = boxlossNum;
    }

    public int getFreeze() {
        return freeze;
    }

    public void setFreeze(int freeze) {
        this.freeze = freeze;
    }

    public int getPullon() {
        return pullon;
    }

    public void setPullon(int pullon) {
        this.pullon = pullon;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getRemnantNum() {
        return remnantNum;
    }

    public void setRemnantNum(int remnantNum) {
        this.remnantNum = remnantNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFromStoreCode() {
        return fromStoreCode;
    }

    public void setFromStoreCode(String fromStoreCode) {
        this.fromStoreCode = fromStoreCode;
    }

    public String getToStoreCode() {
        return toStoreCode;
    }

    public void setToStoreCode(String toStoreCode) {
        this.toStoreCode = toStoreCode;
    }

    public String getExpectStartTime() {
        return expectStartTime;
    }

    public void setExpectStartTime(String expectStartTime) {
        this.expectStartTime = expectStartTime;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getScItemCode() {
        return scItemCode;
    }

    public void setScItemCode(String scItemCode) {
        this.scItemCode = scItemCode;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTransferOrderCode() {
        return transferOrderCode;
    }

    public void setTransferOrderCode(String transferOrderCode) {
        this.transferOrderCode = transferOrderCode;
    }

    public String getExpectOutStoreTime() {
        return expectOutStoreTime;
    }

    public void setExpectOutStoreTime(String expectOutStoreTime) {
        this.expectOutStoreTime = expectOutStoreTime;
    }

    public String getExpectInStoreTime() {
        return expectInStoreTime;
    }

    public void setExpectInStoreTime(String expectInStoreTime) {
        this.expectInStoreTime = expectInStoreTime;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOutCount() {
        return outCount;
    }

    public void setOutCount(int outCount) {
        this.outCount = outCount;
    }

    public int getInCount() {
        return inCount;
    }

    public void setInCount(int inCount) {
        this.inCount = inCount;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getProductCate() {
        return productCate;
    }

    public void setProductCate(String productCate) {
        this.productCate = productCate;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTransferOutOrderCode() {
        return transferOutOrderCode;
    }

    public void setTransferOutOrderCode(String transferOutOrderCode) {
        this.transferOutOrderCode = transferOutOrderCode;
    }

    public String getTransferInOrderCode() {
        return transferInOrderCode;
    }

    public void setTransferInOrderCode(String transferInOrderCode) {
        this.transferInOrderCode = transferInOrderCode;
    }

    public String getConfirmOutTime() {
        return confirmOutTime;
    }

    public void setConfirmOutTime(String confirmOutTime) {
        this.confirmOutTime = confirmOutTime;
    }

    public String getConfirmInTime() {
        return confirmInTime;
    }

    public void setConfirmInTime(String confirmInTime) {
        this.confirmInTime = confirmInTime;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public String getSapErrorMessage() {
        return sapErrorMessage;
    }

    public void setSapErrorMessage(String sapErrorMessage) {
        this.sapErrorMessage = sapErrorMessage;
    }
}
