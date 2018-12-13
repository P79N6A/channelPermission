package com.haier.purchase.data.model;

import java.io.Serializable;

/**
 * 菜鸟补货单列表
 **/
public class ReplenishOrderDisplayItem implements Serializable{

    private static final long serialVersionUID = 4252468049226101485L;

    private Integer id;
    private String gmtCreate; //创建时间
    private String gmtModified; //补货单修改时间
    private Long scItemId; //货品id
    private String scItemCode; //货品编码
    private String scItemName; //货品名称
    private String replNo; //补货单号
    private Integer planReplQty; //计划补货量
    private String deadLine; //最晚入库时间
    private Integer suggestQty; //建议补货量
    private String transportType; //运输方式 {"direct"："直配"，"cross"："越库"，"union"："统配"，"direct_rep"："直入补货"，"transfer"："调拨"}
    private String fromStoreCode; //基地仓code
    private String fromStoreName; //基地仓名称
    private String storeCode; //仓code
    private String storeName; //仓name

    /**
     * 补货单状态 state
     * 0:初始状态（待确认） 1:已生成85单号（待补货）2:已向TM推送85单号 3:已获取LBX号
     * 4:已通知天猫基地仓出库(正在补货,对应入库单状态0-5) 5.收到出库单 100.已推送SAP（完成补货） 101.过期关闭
     */
    private Integer state;
    private String entryOrderCode; //85单号
    private String entryOrderId; //菜鸟LBX号
    private String contactName; //联系人
    private String contactPhone; //联系电话,天猫支持一个
    private Integer confirmReplQty; //确认补货量
    private String confirmDeadLine; //确认最晚入库时间
    private String errorMsg; //请求错误信息


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Long getScItemId() {
        return scItemId;
    }

    public void setScItemId(Long scItemId) {
        this.scItemId = scItemId;
    }

    public String getScItemCode() {
        return scItemCode;
    }

    public void setScItemCode(String scItemCode) {
        this.scItemCode = scItemCode;
    }

    public String getScItemName() {
        return scItemName;
    }

    public void setScItemName(String scItemName) {
        this.scItemName = scItemName;
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

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public String getConfirmDeadLine() {
        return confirmDeadLine;
    }

    public void setConfirmDeadLine(String confirmDeadLine) {
        this.confirmDeadLine = confirmDeadLine;
    }

    public String getEntryOrderId() {
        return entryOrderId;
    }

    public void setEntryOrderId(String entryOrderId) {
        this.entryOrderId = entryOrderId;
    }

    public Integer getSuggestQty() {
        return suggestQty;
    }

    public void setSuggestQty(Integer suggestQty) {
        this.suggestQty = suggestQty;
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

    public Integer getConfirmReplQty() {
        return confirmReplQty;
    }

    public void setConfirmReplQty(Integer confirmReplQty) {
        this.confirmReplQty = confirmReplQty;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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
}
