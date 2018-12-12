package com.haier.shop.model;

import java.io.Serializable;

/**
 * 电子发票日志
 *                       
 * @Filename: InvoiceElectricLogs.java
 * @Version: 1.0
 * @Author: weiyunjun
 * @Email: weiyunjun@ehaier.com
 *
 */
public class InvoiceElectricLogs implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    /*主键*/
    private Integer           id;
    /*添加日志时间*/
    private Integer           addTime;
    /*网单id*/
    private Integer           orderProductId;
    /*网单号*/
    private String            cOrderSn;
    /*发票id*/
    private Integer           invoiceId;
    /*发票类型      1：开票；2：作废/冲红；3：查询*/
    private Integer           type;
    /*请求数据  xml格式*/
    private String            pushData;
    /*接收数据*/
    private String            returnData;
    /*解析结果*/
    private Integer           analysisResult;
    /*校验结果*/
    private Integer           verifyResult;
    /*是否成功，0：失败；1：成功*/
    private Integer           success;
    /*推送次数*/
    private Integer           count;
    /*最后一次推送时间*/
    private Integer           lastTime;
    /*最后一次推送信息*/
    private String            lastMessage;
    /*电子发票发短信标志，如果false，通过电子发票系统发短信，true商城系统发短息*/
    private Integer           smsFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public Integer getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

    public String getCOrderSn() {
        return cOrderSn;
    }

    public void setCOrderSn(String cOrderSn) {
        this.cOrderSn = cOrderSn;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPushData() {
        return pushData;
    }

    public void setPushData(String pushData) {
        this.pushData = pushData;
    }

    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

    public Integer getAnalysisResult() {
        return analysisResult;
    }

    public void setAnalysisResult(Integer analysisResult) {
        this.analysisResult = analysisResult;
    }

    public Integer getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(Integer verifyResult) {
        this.verifyResult = verifyResult;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getLastTime() {
        return lastTime;
    }

    public void setLastTime(Integer lastTime) {
        this.lastTime = lastTime;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Integer getSmsFlag() {
        return smsFlag;
    }

    public void setSmsFlag(Integer smsFlag) {
        this.smsFlag = smsFlag;
    }

}
