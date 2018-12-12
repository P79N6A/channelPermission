package com.haier.shop.model;

import java.io.Serializable;

/**
 * 库存商品表 同步表。
 *
 * <p>Table: <strong>StorageProducts</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>sCode</td><td>{@link String}</td><td>sCode</td><td>varchar</td><td>库房编码</td></tr>
 *   <tr><td>storageId</td><td>{@link Integer}</td><td>storageId</td><td>int</td><td>库存ID</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>商品sku</td></tr>
 *   <tr><td>stock</td><td>{@link Integer}</td><td>stock</td><td>int</td><td>实际库存量</td></tr>
 *   <tr><td>stockBak</td><td>{@link Integer}</td><td>stockBak</td><td>int</td><td>实际库存备份(LES有时需要让商城把实际库存清0,全部的再统一同步)</td></tr>
 *   <tr><td>usedStock</td><td>{@link Integer}</td><td>usedStock</td><td>int</td><td>占用库存量</td></tr>
 *   <tr><td>productType</td><td>{@link Integer}</td><td>productType</td><td>smallint</td><td>产品类型 10 为正常产品</td></tr>
 *   <tr><td>stockUpTime</td><td>{@link Long}</td><td>stockUpTime</td><td>int</td><td>实际库存最新更新时间</td></tr>
 *   <tr><td>lastAvailableModifyTime</td><td>{@link Long}</td><td>lastAvailableModifyTime</td><td>int</td><td>可用库存上次变化时间</td></tr>
 *   <tr><td>waitSyncToTaobao</td><td>{@link Integer}</td><td>waitSyncToTaobao</td><td>tinyint</td><td>是否等待同步到淘宝</td></tr>
 *   <tr><td>lastSyncToTaobaoTime</td><td>{@link Long}</td><td>lastSyncToTaobaoTime</td><td>int</td><td>最后一次同步到物流宝的时间</td></tr>
 *   <tr><td>addTime</td><td>{@link Long}</td><td>addTime</td><td>int</td><td>记录生成时间</td></tr>
 *   <tr><td>locksTB</td><td>{@link Integer}</td><td>locksTB</td><td>int</td><td>淘宝官方旗舰店预留商品</td></tr>
 *   <tr><td>locksSC</td><td>{@link Integer}</td><td>locksSC</td><td>int</td><td>商城预留商品</td></tr>
 *   <tr><td>locksQY</td><td>{@link Integer}</td><td>locksQY</td><td>int</td><td>企业预留商品</td></tr>
 *   <tr><td>shareStock</td><td>{@link Integer}</td><td>shareStock</td><td>int</td><td>共享库存</td></tr>
 *   <tr><td>waQty</td><td>{@link Integer}</td><td>waQty</td><td>int</td><td>WA库存</td></tr>
 *   <tr><td>availableQty</td><td>{@link Integer}</td><td>availableQty</td><td>int</td><td>可用库存（计算所得）</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-4-30
 * @email yudi@sina.com
 */
public class StorageProducts implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1134553119719219946L;
    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private String sCode;

    /**
     * 获取 库房编码。
     */
    public String getSCode() {
        return this.sCode;
    }

    /**
     * 设置 库房编码。
     *
     * @param value 属性值
     */
    public void setSCode(String value) {
        this.sCode = value;
    }

    private Integer storageId;

    /**
     * 获取 库存ID。
     */
    public Integer getStorageId() {
        return this.storageId;
    }

    /**
     * 设置 库存ID。
     *
     * @param value 属性值
     */
    public void setStorageId(Integer value) {
        this.storageId = value;
    }

    private String sku;

    /**
     * 获取 商品sku。
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * 设置 商品sku。
     *
     * @param value 属性值
     */
    public void setSku(String value) {
        this.sku = value;
    }

    private Integer stock;

    /**
     * 获取 实际库存量。
     */
    public Integer getStock() {
        return this.stock;
    }

    /**
     * 设置 实际库存量。
     *
     * @param value 属性值
     */
    public void setStock(Integer value) {
        this.stock = value;
    }

    private Integer stockBak;

    /**
     * 获取 实际库存备份(LES有时需要让商城把实际库存清0,全部的再统一同步)。
     */
    public Integer getStockBak() {
        return this.stockBak;
    }

    /**
     * 设置 实际库存备份(LES有时需要让商城把实际库存清0,全部的再统一同步)。
     *
     * @param value 属性值
     */
    public void setStockBak(Integer value) {
        this.stockBak = value;
    }

    private Integer usedStock;

    /**
     * 获取 占用库存量。
     */
    public Integer getUsedStock() {
        return this.usedStock;
    }

    /**
     * 设置 占用库存量。
     *
     * @param value 属性值
     */
    public void setUsedStock(Integer value) {
        this.usedStock = value;
    }

    private Integer productType;

    /**
     * 获取 产品类型 10 为正常产品。
     */
    public Integer getProductType() {
        return this.productType;
    }

    /**
     * 设置 产品类型 10 为正常产品。
     *
     * @param value 属性值
     */
    public void setProductType(Integer value) {
        this.productType = value;
    }

    private Long stockUpTime;

    /**
     * 获取 实际库存最新更新时间。
     */
    public Long getStockUpTime() {
        return this.stockUpTime;
    }

    /**
     * 设置 实际库存最新更新时间。
     *
     * @param value 属性值
     */
    public void setStockUpTime(Long value) {
        this.stockUpTime = value;
    }

    private Long lastAvailableModifyTime;

    /**
     * 获取 可用库存上次变化时间。
     */
    public Long getLastAvailableModifyTime() {
        return this.lastAvailableModifyTime;
    }

    /**
     * 设置 可用库存上次变化时间。
     *
     * @param value 属性值
     */
    public void setLastAvailableModifyTime(Long value) {
        this.lastAvailableModifyTime = value;
    }

    private Integer waitSyncToTaobao;

    /**
     * 获取 是否等待同步到淘宝。
     */
    public Integer getWaitSyncToTaobao() {
        return this.waitSyncToTaobao;
    }

    /**
     * 设置 是否等待同步到淘宝。
     *
     * @param value 属性值
     */
    public void setWaitSyncToTaobao(Integer value) {
        this.waitSyncToTaobao = value;
    }

    private Long lastSyncToTaobaoTime;

    /**
     * 获取 最后一次同步到物流宝的时间。
     */
    public Long getLastSyncToTaobaoTime() {
        return this.lastSyncToTaobaoTime;
    }

    /**
     * 设置 最后一次同步到物流宝的时间。
     *
     * @param value 属性值
     */
    public void setLastSyncToTaobaoTime(Long value) {
        this.lastSyncToTaobaoTime = value;
    }

    private Long addTime;

    /**
     * 获取 记录生成时间。
     */
    public Long getAddTime() {
        return this.addTime;
    }

    /**
     * 设置 记录生成时间。
     *
     * @param value 属性值
     */
    public void setAddTime(Long value) {
        this.addTime = value;
    }

    private Integer locksTB = 0;

    /**
     * 获取 淘宝官方旗舰店预留商品。
     */
    public Integer getLocksTB() {
        return this.locksTB;
    }

    /**
     * 设置 淘宝官方旗舰店预留商品。
     *
     * @param value 属性值
     */
    public void setLocksTB(Integer value) {
        this.locksTB = value;
    }

    private Integer locksSC = 0;

    /**
     * 获取 商城预留商品。
     */
    public Integer getLocksSC() {
        return this.locksSC;
    }

    /**
     * 设置 商城预留商品。
     *
     * @param value 属性值
     */
    public void setLocksSC(Integer value) {
        this.locksSC = value;
    }

    private Integer locksQY = 0;

    /**
     * 获取 企业预留商品。
     */
    public Integer getLocksQY() {
        return this.locksQY;
    }

    /**
     * 设置 企业预留商品。
     *
     * @param value 属性值
     */
    public void setLocksQY(Integer value) {
        this.locksQY = value;
    }

    private Integer shareStock = 0;

    /**
     * 获取 共享库存。
     */
    public Integer getShareStock() {
        return this.shareStock;
    }

    /**
     * 设置 共享库存。
     *
     * @param value 属性值
     */
    public void setShareStock(Integer value) {
        this.shareStock = value;
    }

    private Integer waQty = 0;

    /**
     * 获取 WA库存数
     * @return
     */
    public Integer getWaQty() {
        return waQty;
    }

    /**
     * 设置 WA库存数
     * @param waQty
     */
    public void setWaQty(Integer waQty) {
        this.waQty = waQty;
    }

    private String tsCode = "";

    /**
     * 获取转运库位
     * @return
     */
    public String getTsCode() {
        return tsCode;
    }

    /**
     * 设置转运库位
     * @param tsCode
     */
    public void setTsCode(String tsCode) {
        this.tsCode = tsCode;
    }

    private Integer tsShippingTime = 0;

    /**
     * 获取 转运时效（单位：小时）
     * @return
     */
    public Integer getTsShippingTime() {
        return tsShippingTime;
    }

    /**
     * 设置 转运时效（单位：小时）
     * @param tsShippingTime
     */
    public void setTsShippingTime(Integer tsShippingTime) {
        this.tsShippingTime = tsShippingTime;
    }

    private Integer availableQty = 0;

    /**
     * 获取 可用库存（计算所得）
     * @return
     */
    public Integer getAvailableQty() {
        return availableQty;
    }

    /**
     * 设置 可用库存（计算所得）
     * @param availableQty
     */
    public void setAvailableQty(Integer availableQty) {
        this.availableQty = availableQty;
    }

}