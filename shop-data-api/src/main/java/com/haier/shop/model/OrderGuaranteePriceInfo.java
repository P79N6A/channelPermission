package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderGuaranteePriceInfo implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -3358288559909168251L;

    private Integer id;

    /**
     * 获取 主键
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置 主键
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    private String channelCode;

    /**
     * 获取 渠道
     * @return
     */
    public String getChannelCode() {
        return channelCode;
    }

    /**
     * 设置 渠道
     * @param channelCode
     */
    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    private String shippingMode;

    /**
     * 获取 物流模式
     * @return
     */
    public String getShippingMode() {
        return shippingMode;
    }

    /**
     * 设置 物流模式
     * @param shippingMode
     */
    public void setShippingMode(String shippingMode) {
        this.shippingMode = shippingMode;
    }

    private Integer brandId;

    /**
     * 获取 品牌id
     * @return
     */
    public Integer getBrandId() {
        return brandId;
    }

    /**
     * 设置 品牌id
     * @param brandId
     */
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    private String brandName;

    /**
     * 获取 品牌名称
     * @return
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * 设置 品牌名称
     * @param brandName
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    private Integer cateId;

    /**
     * 获取 品类id
     * @return
     */
    public Integer getCateId() {
        return cateId;
    }

    /**
     * 获取 品类id
     * @param cateId
     */
    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    private String cateName;

    /**
     * 获取 品类名称
     * @return
     */
    public String getCateName() {
        return cateName;
    }

    /**
     * 设置 品类名称
     * @param cateName
     */
    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    private String sku;

    /**
     * 获取 物料编码
     * @return
     */
    public String getSku() {
        return sku;
    }

    /**
     * 设置 物料编码
     * @param sku
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    public String productName;

    /**
     * 获取 商品名称
     * @return
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置 商品名称
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    private BigDecimal guaranteePrice;

    /**
     * 获取 保本价
     * @return
     */
    public BigDecimal getGuaranteePrice() {
        return guaranteePrice;
    }

    /**
     * 设置 保本价
     * @param guaranteePrice
     */
    public void setGuaranteePrice(BigDecimal guaranteePrice) {
        this.guaranteePrice = guaranteePrice;
    }

    private BigDecimal supplyPrice;

    /**
     * 获取 供价
     * @return
     */
    public BigDecimal getSupplyPrice() {
        return supplyPrice;
    }

    /**
     * 设置 供价
     * @param supplyPrice
     */
    public void setSupplyPrice(BigDecimal supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    private BigDecimal directDeductionPrice;

    /**
     * 获取 直扣
     * @return
     */
    public BigDecimal getDirectDeductionPrice() {
        return directDeductionPrice;
    }

    /**
     * 设置 直扣
     * @param directDeductionPrice
     */
    public void setDirectDeductionPrice(BigDecimal directDeductionPrice) {
        this.directDeductionPrice = directDeductionPrice;
    }

    private BigDecimal afterBackPrice;

    /**
     * 获取 后返
     * @return
     */
    public BigDecimal getAfterBackPrice() {
        return afterBackPrice;
    }

    /**
     * 设置 后返
     * @param afterBackPrice
     */
    public void setAfterBackPrice(BigDecimal afterBackPrice) {
        this.afterBackPrice = afterBackPrice;
    }

    private BigDecimal valueChainRatio;

    /**
     * 获取 价值链比率
     * @return
     */
    public BigDecimal getValueChainRatio() {
        return valueChainRatio;
    }

    /**
     * 设置 价值链比率
     * @param valueChainRatio
     */
    public void setValueChainRatio(BigDecimal valueChainRatio) {
        this.valueChainRatio = valueChainRatio;
    }

    private String createTime;

    /**
     * 获取 创建时间
     * @return
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置 创建时间
     * @param createTime
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private String updateTime;

    /**
     * 获取 更新时间
     * @return
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置 更新时间
     * @param updateTime
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    private BigDecimal activityGuaranteePrice;

    public BigDecimal getActivityGuaranteePrice() {
        return activityGuaranteePrice;
    }

    public void setActivityGuaranteePrice(BigDecimal activityGuaranteePrice) {
        this.activityGuaranteePrice = activityGuaranteePrice;
    }

    private Integer activityGuaranteePriceStartTime;

    public Integer getActivityGuaranteePriceStartTime() {
        return activityGuaranteePriceStartTime;
    }

    public void setActivityGuaranteePriceStartTime(Integer activityGuaranteePriceStartTime) {
        this.activityGuaranteePriceStartTime = activityGuaranteePriceStartTime;
    }

    private Integer activityGuaranteePriceEndTime;

    public Integer getActivityGuaranteePriceEndTime() {
        return activityGuaranteePriceEndTime;
    }

    public void setActivityGuaranteePriceEndTime(Integer activityGuaranteePriceEndTime) {
        this.activityGuaranteePriceEndTime = activityGuaranteePriceEndTime;
    }

}