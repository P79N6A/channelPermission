package com.haier.eop.data.model;

import java.io.Serializable;

/**
 * 调拨单显示实体类
 */
public class TransferOrderDisplayItem implements Serializable{

    private Integer id;                     //id值
    private String transferOrderCode;       //调拨单号
    private String scItemCode;              //物料编码
    private String fromStoreCode;           //出仓库编码
    private String toStoreCode;             //入仓库编码
    private Integer outCount;               //实际出库数量
    private Integer inCount;                //入库数量
    private Integer remnantNum;             //残品数量
    private Integer orderStatus;            //状态
    private String orderCode;               //外部ERP单号
    private String transferOutOrderCode;    //出库单LBX号
    private String transferInOrderCode;     //入库单LBX号
    private String confirmOutTime;          //出库确认时间
    private String confirmInTime;           //入库确认时间
    private String creater;                 //创建人
    private String createTime;              //创建时间
    private String sapErrorMessage;         //sap错误信息

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRemnantNum() {
        return remnantNum;
    }

    public void setRemnantNum(Integer remnantNum) {
        this.remnantNum = remnantNum;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getTransferOrderCode() {
        return transferOrderCode;
    }

    public void setTransferOrderCode(String transferOrderCode) {
        this.transferOrderCode = transferOrderCode;
    }

    public String getScItemCode() {
        return scItemCode;
    }

    public void setScItemCode(String scItemCode) {
        this.scItemCode = scItemCode;
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

    public Integer getOutCount() {
        return outCount;
    }

    public void setOutCount(Integer outCount) {
        this.outCount = outCount;
    }

    public Integer getInCount() {
        return inCount;
    }

    public void setInCount(Integer inCount) {
        this.inCount = inCount;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
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

    public String getSapErrorMessage() {
        return sapErrorMessage;
    }

    public void setSapErrorMessage(String sapErrorMessage) {
        this.sapErrorMessage = sapErrorMessage;
    }
}
