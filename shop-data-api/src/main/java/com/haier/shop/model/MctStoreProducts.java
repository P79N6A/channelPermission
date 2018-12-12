package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

public class MctStoreProducts implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 2661045664406388954L;

    /**
     * 下架
     */
    public static Integer     OFF_SALE         = 0;

    /**
     * 上架
     */
    public static Integer     ON_SALE          = 1;

    /**
     * 是否自动同步-初始
     */
    public static Integer     IS_AUTO_INIT     = 0;

    /**
     * 是否自动同步-同步
     */
    public static Integer     IS_AUTO_SYNC     = 1;

    private Integer           storeProductId;

    public Integer getStoreProductId() {
        return storeProductId;
    }

    public void setStoreProductId(Integer storeProductId) {
        this.storeProductId = storeProductId;
    }

    private Integer storeId;

    /**
    *获取 店铺id
    */
    public Integer getStoreId() {
        return storeId;
    }

    /**
    *设置 店铺id
    *@param storeId 店铺id
    */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    private String storeCode;

    /**
    *获取 店铺编码
    */
    public String getStoreCode() {
        return storeCode;
    }

    /**
    *设置 店铺编码
    *@param storeCode 店铺编码
    */
    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    private String sku;

    /**
    *获取 物料号
    */
    public String getSku() {
        return sku;
    }

    /**
    *设置 物料号
    *@param sku 物料号
    */
    public void setSku(String sku) {
        this.sku = sku;
    }

    private Integer stockNum;

    /**
    *获取 非实时的库存数
    */
    public Integer getStockNum() {
        return stockNum;
    }

    /**
    *设置 非实时的库存数
    *@param stockNum 非实时的库存数
    */
    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    private Integer onSale;

    /**
    *获取 是否上架
    */
    public Integer getOnSale() {
        return onSale;
    }

    /**
    *设置 是否上架
    *@param onSale 是否上架
    */
    public void setOnSale(Integer onSale) {
        this.onSale = onSale;
    }

    private Integer createUserId;

    /**
    *获取 创建用户ID
    */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
    *设置 创建用户ID
    *@param createUserId 创建用户ID
    */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    private Date createTime;

    /**
     *获取 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     *设置 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private Integer lastUpdateUserId;

    /**
    *获取 最后更新userId
    */
    public Integer getLastUpdateUserId() {
        return lastUpdateUserId;
    }

    /**
    *设置 最后更新userId
    *@param lastUpdateUserId 最后更新userId
    */
    public void setLastUpdateUserId(Integer lastUpdateUserId) {
        this.lastUpdateUserId = lastUpdateUserId;
    }

    private Date lastUpdateTime;

    /**
     *获取 最后更新时间
     */
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     *设置 最后更新时间
     *@param lastUpdateTime 最后更新时间
     */
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    private Integer isAuto;

    /**
     *获取 是否自动同步（0-初始；1-自动同步）
     */
    public Integer getIsAuto() {
        return isAuto;
    }

    /**
     *设置 是否自动同步（0-初始；1-自动同步）
     *@param isAuto 是否自动同步标识
     */
    public void setIsAuto(Integer isAuto) {
        this.isAuto = isAuto;
    }

}