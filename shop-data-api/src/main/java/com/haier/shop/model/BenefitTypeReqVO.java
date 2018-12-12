package com.haier.shop.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BenefitTypeReqVO extends BaseReqVO {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -2031254762756649427L;

    private Integer memberId; //海尔商城用户

    private String orderSn; //订单号 

    private String backType; //退单类型 10:退订单，11:退网单,13:拆单退

    private String orderProductId; //网单号 退单为网单时必传

    private String orderProductIdN; //新网单号 拆单必传

    private Integer num; //新网单商品数量

    private BigDecimal payAmt; //新网单实付金额 

    private List<Map<String, String>> benefitList = new ArrayList<Map<String, String>>(); //回退优惠类型及数量、金额

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getBackType() {
        return backType;
    }

    public void setBackType(String backType) {
        this.backType = backType;
    }

    public String getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(String orderProductId) {
        this.orderProductId = orderProductId;
    }

    public String getOrderProductIdN() {
        return orderProductIdN;
    }

    public void setOrderProductIdN(String orderProductIdN) {
        this.orderProductIdN = orderProductIdN;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(BigDecimal payAmt) {
        this.payAmt = payAmt;
    }

    public List<Map<String, String>> getBenefitList() {
        return benefitList;
    }

    public void setBenefitList(List<Map<String, String>> benefitList) {
        this.benefitList = benefitList;
    }

    public String toString() {
        return "BenefitTypeReqVO [memberId=" + memberId + ", orderSn=" + orderSn + ", backType="
               + backType + ", orderProductId=" + orderProductId + ", orderProductIdN="
               + orderProductIdN + ", num=" + num + ", payAmt=" + payAmt + ", benefitList="
               + benefitList + "]";
    }

}
