package com.haier.purchase.data.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜鸟补货单
 **/
public class ReplenishOrder implements Serializable{

    private static final long serialVersionUID = 2315246988920798058L;

    private Integer id;
    private Date gmtCreate; //创建时间
    private Date gmtModified; //补货单修改时间
    private Long scItemId; //货品id
    private String scItemCode; //货品code 海尔sku
    private String scItemName; //货品名称
    private String replNo; //补货单号
    private Integer planReplQty; //计划补货量
    private String fromStoreCode; //源仓 基地仓
    private String fromStoreName; //源仓名称
    private Date deadLine; //最晚入库时间
    private Integer replOrderType; //补货单据类型 1：补货，2：调拨，3：越库
    private Integer status; //单据状态 {0: "待小二审核",1: "审核通过",2: "审核不通过",3: "未确认导入",4: "未提交",8: "已提交",9: "已关闭"}
    private String xiaoerMemo; //小二备注
    private String systemMemo; //系统备注
    private Long supplier_id; //商家ID
    private String supplier_name; //商家昵称
    private Integer suggestQty; //建议补货量
    private Long marketType;  //所属行业
    private String transportType; //运输方式 {"direct"："直配"，"cross"："越库"，"union"："统配"，"direct_rep"："直入补货"，"transfer"："调拨"}
    private String storeCode; //仓code
    private String storeName; //仓name

    //补充字段
    private String messageId; //物流云上记录的主键
    private Date insertTime; //记录生成时间
    /**
     * 补货单状态 state
     * 0:初始状态（待确认） 1:补货单确认  2:申请85单号成功  3:已向TM推送85单号(查询85单号)  4:已获取LBX号
     * 5:已通知天猫基地仓出库(正在补货,对应入库单状态1-5) 100.接收天猫确认出库信息（完成补货） 101:取消补货
     * 102:无法生成85单号
     */
    private Integer state;
    private String entryOrderCode; //85单号
    private String entryOrderId; //菜鸟LBX号
    private String contactName; //联系人
    private String contactPhone; //联系电话,天猫支持一个
    private Date modifiedTime; //记录修改时间
    private Integer tryCount; //补货单操作尝试次数， 超过一定次数就停止job操作
    private Integer confirmReplQty; //确认补货量
    private Date confirmDeadLine; //确认最晚入库时间
    private String wpOrderId;  //WP单号 用于查询85单号
    private String errorCode; //请求错误码
    private String errorMsg; //请求错误信息


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getScItemId() {
        return scItemId;
    }

    public void setScItemId(Long scItemId) {
        this.scItemId = scItemId;
    }

    public String getScItemName() {
        return scItemName;
    }

    public void setScItemName(String scItemName) {
        this.scItemName = scItemName;
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

    public String getFromStoreName() {
        return fromStoreName;
    }

    public void setFromStoreName(String fromStoreName) {
        this.fromStoreName = fromStoreName;
    }

    public Long getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(Long supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getReplNo() {
        return replNo;
    }

    public void setReplNo(String replNo) {
        this.replNo = replNo;
    }

    public Integer getPlanReplQty() {
        return planReplQty;
    }

    public void setPlanReplQty(Integer planReplQty) {
        this.planReplQty = planReplQty;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public Integer getReplOrderType() {
        return replOrderType;
    }

    public void setReplOrderType(Integer replOrderType) {
        this.replOrderType = replOrderType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEntryOrderId() {
        return entryOrderId;
    }

    public void setEntryOrderId(String entryOrderId) {
        this.entryOrderId = entryOrderId;
    }

    public String getXiaoerMemo() {
        return xiaoerMemo;
    }

    public void setXiaoerMemo(String xiaoerMemo) {
        this.xiaoerMemo = xiaoerMemo;
    }

    public String getSystemMemo() {
        return systemMemo;
    }

    public void setSystemMemo(String systemMemo) {
        this.systemMemo = systemMemo;
    }

    public Integer getSuggestQty() {
        return suggestQty;
    }

    public void setSuggestQty(Integer suggestQty) {
        this.suggestQty = suggestQty;
    }

    public Long getMarketType() {
        return marketType;
    }

    public void setMarketType(Long marketType) {
        this.marketType = marketType;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getEntryOrderCode() {
        return entryOrderCode;
    }

    public void setEntryOrderCode(String entryOrderCode) {
        this.entryOrderCode = entryOrderCode;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Integer getConfirmReplQty() {
        return confirmReplQty;
    }

    public void setConfirmReplQty(Integer confirmReplQty) {
        this.confirmReplQty = confirmReplQty;
    }

    public Date getConfirmDeadLine() {
        return confirmDeadLine;
    }

    public void setConfirmDeadLine(Date confirmDeadLine) {
        this.confirmDeadLine = confirmDeadLine;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getTryCount() {
        return tryCount;
    }

    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }

    public String getWpOrderId() {
        return wpOrderId;
    }

    public void setWpOrderId(String wpOrderId) {
        this.wpOrderId = wpOrderId;
    }
}
