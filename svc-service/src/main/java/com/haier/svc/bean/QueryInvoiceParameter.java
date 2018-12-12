package com.haier.svc.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by hanhaoyang@ehaier.com on 2017/11/15 0015.
 */
public class QueryInvoiceParameter implements Serializable {
    private static final long serialVersionUID = 9L;

    private int id;
    private String cOrderSn;
    private String invoiceTitle;
    private String taxPayerNumber;
    private String registerAddressAndPhone;
    private String bankNameAndAccount;
    private String isEnergySaving;
    private String isReInvoice;
    private String type;
    private String state;
    private String electricFlag;
    private Integer tryNum;
    private String startFirstPushTime;
    private String endFirstPushTime;
    private String startInvalidTime;
    private String endInvalidTime;
    private String success;
    private String startCOrderAddTime;
    private String endCOrderAddTime;
    private String isTogether;
    private String cOrderType;
    private String invoiceNumber;
    private Integer pushState;
    private String waitPushTime;            //未推送时间
    private String waitTakeTime;            //未开票时间
    private Integer rows;					//行数
    private Integer page;					//页数

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getcOrderSn() {
        return cOrderSn;
    }

    public void setcOrderSn(String cOrderSn) {
        this.cOrderSn = cOrderSn;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getTaxPayerNumber() {
        return taxPayerNumber;
    }

    public void setTaxPayerNumber(String taxPayerNumber) {
        this.taxPayerNumber = taxPayerNumber;
    }

    public String getRegisterAddressAndPhone() {
        return registerAddressAndPhone;
    }

    public void setRegisterAddressAndPhone(String registerAddressAndPhone) {
        this.registerAddressAndPhone = registerAddressAndPhone;
    }

    public String getBankNameAndAccount() {
        return bankNameAndAccount;
    }

    public void setBankNameAndAccount(String bankNameAndAccount) {
        this.bankNameAndAccount = bankNameAndAccount;
    }

    public String getIsReInvoice() {
        return isReInvoice;
    }

    public void setIsReInvoice(String isReInvoice) {
        this.isReInvoice = isReInvoice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getElectricFlag() {
        return electricFlag;
    }

    public void setElectricFlag(String electricFlag) {
        this.electricFlag = electricFlag;
    }

    public Integer getTryNum() {
        return tryNum;
    }

    public void setTryNum(Integer tryNum) {
        this.tryNum = tryNum;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getIsTogether() {
        return isTogether;
    }

    public void setIsTogether(String isTogether) {
        this.isTogether = isTogether;
    }

    public String getcOrderType() {
        return cOrderType;
    }

    public void setcOrderType(String cOrderType) {
        this.cOrderType = cOrderType;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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

    public String getIsEnergySaving() {
        return isEnergySaving;
    }

    public void setIsEnergySaving(String isEnergySaving) {
        this.isEnergySaving = isEnergySaving;
    }

    public String getEndFirstPushTime() {
        return endFirstPushTime;
    }

    public void setEndFirstPushTime(String endFirstPushTime) {
        this.endFirstPushTime = endFirstPushTime;
    }

    public String getStartFirstPushTime() {
        return startFirstPushTime;
    }

    public void setStartFirstPushTime(String startFirstPushTime) {
        this.startFirstPushTime = startFirstPushTime;
    }

    public String getEndInvalidTime() {
        return endInvalidTime;
    }

    public void setEndInvalidTime(String endInvalidTime) {
        this.endInvalidTime = endInvalidTime;
    }

    public String getStartInvalidTime() {
        return startInvalidTime;
    }

    public void setStartInvalidTime(String startInvalidTime) {
        this.startInvalidTime = startInvalidTime;
    }

    public String getEndCOrderAddTime() {
        return endCOrderAddTime;
    }

    public void setEndCOrderAddTime(String endCOrderAddTime) {
        this.endCOrderAddTime = endCOrderAddTime;
    }

    public String getStartCOrderAddTime() {
        return startCOrderAddTime;
    }

    public void setStartCOrderAddTime(String startCOrderAddTime) {
        this.startCOrderAddTime = startCOrderAddTime;
    }

    public Integer getPushState() {
        return pushState;
    }

    public void setPushState(Integer pushState) {
        this.pushState = pushState;
    }

    public String getWaitPushTime() {
        return waitPushTime;
    }

    public void setWaitPushTime(String waitPushTime) {
        this.waitPushTime = waitPushTime;
    }

    public String getWaitTakeTime() {
        return waitTakeTime;
    }

    public void setWaitTakeTime(String waitTakeTime) {
        this.waitTakeTime = waitTakeTime;
    }
}
