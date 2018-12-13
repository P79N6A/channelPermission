package com.haier.shop.model;

import java.io.Serializable;

public class OmsInStoreRecords implements Serializable {
    private static final long serialVersionUID = -5315128121933779296L;
    //主键
    private Integer id;
    //记账凭证号
    private String materialCertification;
    //行号
    private String certificationItem;
    //机编码
    private String snCode;
    //物流单号
    private String purchaseOrderCode;
    //来源单号
    private String sourceSn;
    //中心库位
    private String storageLocation;
    //批次
    private String batch;
    //物料号
    private String goodsCode;
    //订单数量
    private String billCnt;
    //订单类型
    private String orderType;
    //前续物流单号
    private String returnSourceOrder;

    private Integer rows; //分页用
    private Integer page;//分页

    public OmsInStoreRecords() {
    }

    //前序交易单号
    public  Integer getId() {
        return id;
    }public String  getMaterialCertification() {
        return materialCertification;
    }public String  getCertificationItem() {
        return certificationItem;
    }public String  getSnCode() {
        return snCode;
    }public String  getPurchaseOrderCode() {
        return purchaseOrderCode;
    }public String  getSourceSn() {
        return sourceSn;
    }public String  getStorageLocation() {
        return storageLocation;
    }public String  getBatch() {
        return batch;
    }public String  getGoodsCode() {
        return goodsCode;
    }public String  getBillCnt() {
        return billCnt;
    }public String  getOrderType() {
        return orderType;
    }public String  getReturnSourceOrder() {
        return returnSourceOrder;
    }public String  getReturnSourceSn() {
        return returnSourceSn;
    }public String  getOrderSourceCode() {
        return orderSourceCode;
    }public String  getOrderNo() {
        return orderNo;
    }public String  getRowId() {
        return rowId;
    }public String  getDb() {
        return db;
    }public String  getAddTime() {
        return addTime;
    }private String returnSourceSn;
    //前序来源单号
    private String orderSourceCode;
    //快递单号
    private String orderNo;

    private String orderCode;//菜鸟返回的lbx单号

    private String itemId;//货品Id

    private String ADD2;//二次质检结果,1机器完好换好包装箱； 2非正品需买单； 3机器完好无包装箱可换',

    private String ADD1;//'是否一次质检。1一次；2二次'

    private int vomStatus;//vom推送状态 0:未成功,1;成功
    private int sapStatus;//sap推送状态 0:未成功,1;成功

    private String vomSn;//vom订单号
    private int inSapStatus;//推送入库sap状态 0:未成功,1;成功
    private int outSapStatus;//推送出库sap状态 0:未成功,1;成功

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMaterialCertification(String materialCertification) {
        this.materialCertification = materialCertification;
    }

    public void setCertificationItem(String certificationItem) {
        this.certificationItem = certificationItem;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public void setPurchaseOrderCode(String purchaseOrderCode) {
        this.purchaseOrderCode = purchaseOrderCode;
    }

    public void setSourceSn(String sourceSn) {
        this.sourceSn = sourceSn;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public void setBillCnt(String billCnt) {
        this.billCnt = billCnt;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setReturnSourceOrder(String returnSourceOrder) {
        this.returnSourceOrder = returnSourceOrder;
    }

    public void setReturnSourceSn(String returnSourceSn) {
        this.returnSourceSn = returnSourceSn;
    }

    public void setOrderSourceCode(String orderSourceCode) {
        this.orderSourceCode = orderSourceCode;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    //报文唯一标识
    private String rowId;
    //DB号
    private String db;
    //创建时间
    private String addTime;

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    //是否已经推送HP鉴定
    private Integer status;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getADD2() {
        return ADD2;
    }

    public void setADD2(String ADD2) {
        this.ADD2 = ADD2;
    }

    public String getADD1() {
        return ADD1;
    }

    public void setADD1(String ADD1) {
        this.ADD1 = ADD1;
    }

    public int getVomStatus() {
        return vomStatus;
    }

    public void setVomStatus(int vomStatus) {
        this.vomStatus = vomStatus;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public int getSapStatus() {
        return sapStatus;
    }

    public void setSapStatus(int sapStatus) {
        this.sapStatus = sapStatus;
    }

    public String getVomSn() {
        return vomSn;
    }

    public void setVomSn(String vomSn) {
        this.vomSn = vomSn;
    }

    public int getInSapStatus() {
        return inSapStatus;
    }

    public void setInSapStatus(int inSapStatus) {
        this.inSapStatus = inSapStatus;
    }

    public int getOutSapStatus() {
        return outSapStatus;
    }

    public void setOutSapStatus(int outSapStatus) {
        this.outSapStatus = outSapStatus;
    }
}
