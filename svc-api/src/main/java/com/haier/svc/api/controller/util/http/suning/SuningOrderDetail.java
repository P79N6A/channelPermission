package com.haier.svc.api.controller.util.http.suning;

import java.util.List;

/**
 * 苏宁网单信息帮助类
 *
 * @FileName:SuningOrderDetail.java
 * @Version: 1.0
 * @Author: liulianghui
 * @Author: 641899873@qq.com
 * @CreateDate: 2015年3月18日 下午5:53:59
 */
public class SuningOrderDetail {
    private String productCode; // 苏宁商品编码
    private String productName; // 商品中文名称。
    private String unitPrice; // 商品单价。
    private String payAmount; // 付款金额。订单行项目金额
    private String saleNum; // 数量
    private String transportFee; // 运费。订单行项目对应的运费
    private String returnOrderFlag; // 退货订单标示位 0表示正常订单, 1表示退货订单
    private String coupontotalMoney; // 优惠劵金额
    private String vouchertotalMoney; // 优惠单金额
    private String invCode; // 仓库编码
    private String orderLineNumber; // 订单行项目号
    private String itemCode; // 商家商品编码
    private String receivezipCode; // 收货人邮政编码
    private String hwgFlag; // 海外购标识。01表示海外购订单，其他值为非海外购订单
    private String disType; // 发货方式。01 代表海外直邮发货；02代表商家保税区发货；03代表苏宁保税区发货;空代表国内海外购
    private String orderchannel; // 订单渠道。PC，手机MOBILE，WAP， 电话PHONE
    private String prmtcode; // 推广方式。取值为01时，表示网盟
    private String payTotalAmount;// 礼品卡支付金额
    private String reservedepositamount;//定金金额
    private String reservebalanceamount;//尾款金额
    private String reservestatus;//M-定金支付完成 、P-定金罚没 、R-定金退还
    private String orderLineStatus;//订单行项目状态。10=待发货；20=已发货；30=交易成功;
    private String activitytype;//业务类型(活动类型)02渠道专享、03免费试用、04付邮试用、05预定一单两付、06预定一单一付、07秒杀、08S码
    private List<SuningPayMentList> paymentList;// 易宝支付金额6903

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(String saleNum) {
        this.saleNum = saleNum;
    }

    public String getOrderLineStatus() {
        return orderLineStatus;
    }

    public void setOrderLineStatus(String orderLineStatus) {
        this.orderLineStatus = orderLineStatus;
    }

    public String getTransportFee() {
        return transportFee;
    }

    public void setTransportFee(String transportFee) {
        this.transportFee = transportFee;
    }

    public String getReturnOrderFlag() {
        return returnOrderFlag;
    }

    public void setReturnOrderFlag(String returnOrderFlag) {
        this.returnOrderFlag = returnOrderFlag;
    }

    public String getCoupontotalMoney() {
        return coupontotalMoney;
    }

    public void setCoupontotalMoney(String coupontotalMoney) {
        this.coupontotalMoney = coupontotalMoney;
    }

    public String getVouchertotalMoney() {
        return vouchertotalMoney;
    }

    public void setVouchertotalMoney(String vouchertotalMoney) {
        this.vouchertotalMoney = vouchertotalMoney;
    }

    public String getInvCode() {
        return invCode;
    }

    public void setInvCode(String invCode) {
        this.invCode = invCode;
    }

    public String getOrderLineNumber() {
        return orderLineNumber;
    }

    public void setOrderLineNumber(String orderLineNumber) {
        this.orderLineNumber = orderLineNumber;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getReceivezipCode() {
        return receivezipCode;
    }

    public void setReceivezipCode(String receivezipCode) {
        this.receivezipCode = receivezipCode;
    }

    public String getHwgFlag() {
        return hwgFlag;
    }

    public void setHwgFlag(String hwgFlag) {
        this.hwgFlag = hwgFlag;
    }

    public String getDisType() {
        return disType;
    }

    public void setDisType(String disType) {
        this.disType = disType;
    }

    public String getOrderchannel() {
        return orderchannel;
    }

    public void setOrderchannel(String orderchannel) {
        this.orderchannel = orderchannel;
    }

    public String getPrmtcode() {
        return prmtcode;
    }

    public void setPrmtcode(String prmtcode) {
        this.prmtcode = prmtcode;
    }

    public String getPayTotalAmount() {
        return payTotalAmount;
    }

    public void setPayTotalAmount(String payTotalAmount) {
        this.payTotalAmount = payTotalAmount;
    }

    public List<SuningPayMentList> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<SuningPayMentList> paymentList) {
        this.paymentList = paymentList;
    }

    public String getReservedepositamount() {
        return reservedepositamount;
    }

    public void setReservedepositamount(String reservedepositamount) {
        this.reservedepositamount = reservedepositamount;
    }

    public String getReservebalanceamount() {
        return reservebalanceamount;
    }

    public void setReservebalanceamount(String reservebalanceamount) {
        this.reservebalanceamount = reservebalanceamount;
    }

    public String getReservestatus() {
        return reservestatus;
    }

    public void setReservestatus(String reservestatus) {
        this.reservestatus = reservestatus;
    }

    public String getActivitytype() {
        return activitytype;
    }

    public void setActivitytype(String activitytype) {
        this.activitytype = activitytype;
    }
}
