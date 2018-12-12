package com.haier.eis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * vomwww 出库数据解析表
 *                       
 * @Filename: VomwwwOutstockInfo.java
 * @Version: 1.0
 * @Author:  于善涛
 * @Email: yushantao@ehaier.com
 *
 */
public class VomwwwOutinstockAnalysis implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID      = 1L;
    private Integer           id;
    private String            tradeNo;                   //交易单号
    private String            subTradeNo;                //交易子单号
    private String            scOrderNo;                 //订单号－－商城
    private String            itemNo;                    //订单行号
    private Integer           orderStatus;               //完成状态：订单是否全部完成 1.已完成 2.未完成
    private String            certification;             //单据号
    private String            LBXNo;                     //LBX单号
    private String            sku;                       //物料
    private Integer           type;                      //类型：  出库  退货入库-批次 
    private Integer           num;                       //数量
    private String            receiptVoucher;            //出入库凭证：  出库时为les系统的销售出库凭证，退货时为退货入库凭证；
    private Date              outInDate;                 //出入库时间   
    private String            TBNo;                      //TB单号
    private String            WWWStock;                  //3W库位
    private String            hpInfo;                    //网点信息
    private String            backNo;                    //退货单号
    private String            batch;                     //批次  10  22  21  Z3/Z4  需要提供批次的含义
    private String            expressNum;                //运单号  快递单号
    private String            storeCode;                 //仓库C码－中心代码
    private Date              addTime;                   //加入时间
    private Integer           sapStatus;                 //财务状态：0未处理；1处理成功；2处理失败
    private Integer           sapCount;                  //推送sap次数
    private Integer           stockStatus;               //库存状态
    private String            markMessage;               //备注
    private Integer           busType;                   //业务类型：1、出库；2、入库
    private Integer           orderType;                 //订单类型：1.采购入库 2.销售出库 3.退货入库 4.取件 5.普通出库（自提）
    private Integer           delay;                     //0：不延后 1：延后处理 2：延后处理完成
    private Date              processTime;               //处理时间
    private String            message;                   //处理信息
    private Integer           vomwwwLogsId;              //数据来源id
    private String            markMessage1;
    private String            markMessage2;
    private String            markMessage3;
    private Integer           handleStatus;              //出入库处理状态：0、未处理；1、成功；2不再处理；3失败；4未知异常
    private Integer           eaiDataLogId          = 0; //EAI接口日志标识

    /**
     * 成功
     */
    public final static int   STATUS_SUCCESS        = 1;
    /**
     * 未处理（需要调用状态查询接口）
     */
    public final static int   STATUS_INIT           = 0;
    /**
     * 失败（需要重新处理）
     */
    public final static int   STATUS_FAILED         = 2;
    /**
     * 错误（无法处理）
     */
    public final static int   STATUS_ERROR          = 3;

    /**
     * 未知错误
     */
    public final static int   STATUS_UNKNOWN        = 4;

    /**
     * 出现特殊处理
     */
    public final static int   STATUS_WARN           = -1;

    /**
     * 不再处理
     */
    public final static int   STATUS_UNDO           = 5;

    /**
     * 入库
     */
    public final static int   busTypeIn             = 1;
    /**
     * 出库
     */
    public final static int   busTypeOut            = 2;

    /**
     * 出入库处理状态：未处理
     */
    public final static int   HANDLE_STATUS_INIT    = 0;
    /**
     * 出入库处理状态：成功
     */
    public final static int   HANDLE_STATUS_SUCCESS = 1;
    /**
     * 出入库处理状态：不再处理
     */
    public final static int   HANDLE_STATUS_UNDO    = 2;
    /**
     * 出入库处理状态：失败
     */
    public final static int   HANDLE_STATUS_FAILED  = 3;

    /**
     * 出入库处理状态：未知异常
     */
    public final static int   HANDLE_STATUS_ERROR   = 4;

    public Integer getSapCount() {
        return sapCount;
    }

    public void setSapCount(Integer sapCount) {
        this.sapCount = sapCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getSubTradeNo() {
        return subTradeNo;
    }

    public void setSubTradeNo(String subTradeNo) {
        this.subTradeNo = subTradeNo;
    }

    public String getScOrderNo() {
        return scOrderNo;
    }

    public void setScOrderNo(String scOrderNo) {
        this.scOrderNo = scOrderNo;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getLBXNo() {
        return LBXNo;
    }

    public void setLBXNo(String lBXNo) {
        LBXNo = lBXNo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getReceiptVoucher() {
        return receiptVoucher;
    }

    public void setReceiptVoucher(String receiptVoucher) {
        this.receiptVoucher = receiptVoucher;
    }

    public Date getOutInDate() {
        return outInDate;
    }

    public void setOutInDate(Date outInDate) {
        this.outInDate = outInDate;
    }

    public String getTBNo() {
        return TBNo;
    }

    public void setTBNo(String tBNo) {
        TBNo = tBNo;
    }

    public String getWWWStock() {
        return WWWStock;
    }

    public void setWWWStock(String wWWStock) {
        WWWStock = wWWStock;
    }

    public String getHpInfo() {
        return hpInfo;
    }

    public void setHpInfo(String hpInfo) {
        this.hpInfo = hpInfo;
    }

    public String getBackNo() {
        return backNo;
    }

    public void setBackNo(String backNo) {
        this.backNo = backNo;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Integer getSapStatus() {
        return sapStatus;
    }

    public void setSapStatus(Integer sapStatus) {
        this.sapStatus = sapStatus;
    }

    public Integer getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(Integer stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getMarkMessage() {
        return markMessage;
    }

    public void setMarkMessage(String markMessage) {
        this.markMessage = markMessage;
    }

    public Integer getBusType() {
        return busType;
    }

    public void setBusType(Integer busType) {
        this.busType = busType;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }

    public Date getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getVomwwwLogsId() {
        return vomwwwLogsId;
    }

    public void setVomwwwLogsId(Integer vomwwwLogsId) {
        this.vomwwwLogsId = vomwwwLogsId;
    }

    public String getMarkMessage1() {
        return markMessage1;
    }

    public void setMarkMessage1(String markMessage1) {
        this.markMessage1 = markMessage1;
    }

    public String getMarkMessage2() {
        return markMessage2;
    }

    public void setMarkMessage2(String markMessage2) {
        this.markMessage2 = markMessage2;
    }

    public String getMarkMessage3() {
        return markMessage3;
    }

    public void setMarkMessage3(String markMessage3) {
        this.markMessage3 = markMessage3;
    }

    public Integer getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(Integer handleStatus) {
        this.handleStatus = handleStatus;
    }

    public Integer getEaiDataLogId() {
        return eaiDataLogId;
    }

    public void setEaiDataLogId(Integer eaiDataLogId) {
        this.eaiDataLogId = eaiDataLogId;
    }

    @Override
    public String toString() {
        return "VomwwwOutinstockAnalysis{" + "id=" + id + ", tradeNo='" + tradeNo + '\''
               + ", subTradeNo='" + subTradeNo + '\'' + ", scOrderNo='" + scOrderNo + '\''
               + ", itemNo='" + itemNo + '\'' + ", orderStatus=" + orderStatus
               + ", certification='" + certification + '\'' + ", LBXNo='" + LBXNo + '\''
               + ", sku='" + sku + '\'' + ", type=" + type + ", num=" + num + ", receiptVoucher='"
               + receiptVoucher + '\'' + ", outInDate=" + outInDate + ", TBNo='" + TBNo + '\''
               + ", WWWStock='" + WWWStock + '\'' + ", hpInfo='" + hpInfo + '\'' + ", backNo='"
               + backNo + '\'' + ", batch='" + batch + '\'' + ", expressNum='" + expressNum + '\''
               + ", storeCode='" + storeCode + '\'' + ", addTime=" + addTime + ", sapStatus="
               + sapStatus + ", stockStatus=" + stockStatus + ", markMessage='" + markMessage
               + '\'' + ", busType=" + busType + ", orderType=" + orderType + ", delay=" + delay
               + ", processTime=" + processTime + ", message='" + message + '\''
               + ", vomwwwLogsId=" + vomwwwLogsId + ", markMessage1='" + markMessage1 + '\''
               + ", markMessage2='" + markMessage2 + '\'' + ", markMessage3='" + markMessage3
               + '\'' + ", sapCount=" + sapCount + ", eaiDataLogId=" + eaiDataLogId + '}';
    }
}