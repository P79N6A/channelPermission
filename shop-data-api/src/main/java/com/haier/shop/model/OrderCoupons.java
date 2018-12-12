package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderCoupons implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 7814543457823576185L;
    private Integer           id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer orderId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    private Integer orderProductId;

    public Integer getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

    private String cOrderSn;

    public String getCOrderSn() {
        return cOrderSn;
    }

    public void setCOrderSn(String cOrderSn) {
        this.cOrderSn = cOrderSn;
    }

    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    private BigDecimal productAmount;

    public BigDecimal getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(BigDecimal productAmount) {
        this.productAmount = productAmount;
    }

    private BigDecimal jfbAmount;

    public BigDecimal getJfbAmount() {
        return jfbAmount;
    }

    public void setJfbAmount(BigDecimal jfbAmount) {
        this.jfbAmount = jfbAmount;
    }

    private BigDecimal hbAmount;

    public BigDecimal getHbAmount() {
        return hbAmount;
    }

    public void setHbAmount(BigDecimal hbAmount) {
        this.hbAmount = hbAmount;
    }

    private BigDecimal dqAmount;

    public BigDecimal getDqAmount() {
        return dqAmount;
    }

    public void setDqAmount(BigDecimal dqAmount) {
        this.dqAmount = dqAmount;
    }

    private BigDecimal couponAmount;

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    private BigDecimal points;

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    private BigDecimal tyqAmount;

    public BigDecimal getTyqAmount() {
        return tyqAmount;
    }

    public void setTyqAmount(BigDecimal tyqAmount) {
        this.tyqAmount = tyqAmount;
    }

    private BigDecimal djqAmount;

    public BigDecimal getDjqAmount() {
        return djqAmount;
    }

    public void setDjqAmount(BigDecimal djqAmount) {
        this.djqAmount = djqAmount;
    }

    private BigDecimal djpzAmount;

    public BigDecimal getDjpzAmount() {
        return djpzAmount;
    }

    public void setDjpzAmount(BigDecimal djpzAmount) {
        this.djpzAmount = djpzAmount;
    }

    private BigDecimal dpxdljAmount;

    public BigDecimal getDpxdljAmount() {
        return dpxdljAmount;
    }

    public void setDpxdljAmount(BigDecimal dpxdljAmount) {
        this.dpxdljAmount = dpxdljAmount;
    }

    private BigDecimal dpCouponAmount;

    public BigDecimal getDpCouponAmount() {
        return dpCouponAmount;
    }

    public void setDpCouponAmount(BigDecimal dpCouponAmount) {
        this.dpCouponAmount = dpCouponAmount;
    }

    private BigDecimal dphbAmount;

    public BigDecimal getDphbAmount() {
        return dphbAmount;
    }

    public void setDphbAmount(BigDecimal dphbAmount) {
        this.dphbAmount = dphbAmount;
    }

    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private String updateTime;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    private String kjCode;

    public String getKjCode() {
        return kjCode;
    }

    public void setKjCode(String kjCode) {
        this.kjCode = kjCode;
    }

    private String lpjCode;

    public String getLpjCode() {
        return lpjCode;
    }

    public void setLpjCode(String lpjCode) {
        this.lpjCode = lpjCode;
    }

}