package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CostPoolsUsedLogs implements Serializable{

    public Integer id; //ID
    public Integer siteId;  //站点ID
    public Integer type;    //费用类型 0赠品机 1优惠券
    public Integer channel; //渠道
    public String chanye;   //产业
    public Integer yearMonth;   //年月
    public Integer corderId;    //网单ID
    public Integer orderId;     //订单ID
    public String orderSn;      //订单号
    public String cOrderSn;     //网单号
    public String relationOrderSn;  //关联订单号
    public String source;   //订单来源
    public Integer usedType;    //使用类型 1减少费用 2增加费用
    public BigDecimal amount;   //发生金额
    public Integer addTime; //添加时间
    public String remark;   //备注
    public Integer number;  //商品数量

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getChanye() {
        return chanye;
    }

    public void setChanye(String chanye) {
        this.chanye = chanye;
    }

    public Integer getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Integer getCorderId() {
        return corderId;
    }

    public void setCorderId(Integer corderId) {
        this.corderId = corderId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getcOrderSn() {
        return cOrderSn;
    }

    public void setcOrderSn(String cOrderSn) {
        this.cOrderSn = cOrderSn;
    }

    public String getRelationOrderSn() {
        return relationOrderSn;
    }

    public void setRelationOrderSn(String relationOrderSn) {
        this.relationOrderSn = relationOrderSn;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getUsedType() {
        return usedType;
    }

    public void setUsedType(Integer usedType) {
        this.usedType = usedType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }


}
