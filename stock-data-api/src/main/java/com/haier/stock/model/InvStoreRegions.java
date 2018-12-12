package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Table: <strong>inv_store_regions</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>主键：自动增长列</td></tr>
 *   <tr><td>storeCode</td><td>{@link String}</td><td>store_code</td><td>varchar</td><td>店铺码</td></tr>
 *   <tr><td>cityId</td><td>{@link Integer}</td><td>city_id</td><td>int</td><td>城市编码</td></tr>
 *   <tr><td>regionsId</td><td>{@link Integer}</td><td>regions_id</td><td>int</td><td>区县编码</td></tr>
 *   <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>int</td><td>状态：0，禁用；1，启用</td></tr>
 *   <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>datetime</td><td>更新时间</td></tr>
 * </table>
 *
 * @author 
 * @date 2015-5-27
 * @email yudi@sina.com
 */
public class InvStoreRegions implements Serializable {

    public static final Integer STATUS_ON  = 1;
    public static final Integer STATUS_OFF = 0;
    private Integer             id;

    /**
     * 获取 主键：自动增长列。
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置 主键：自动增长列。
     *
     * @param value 属性值
     */
    public void setId(Integer value) {
        this.id = value;
    }

    private String storeCode;

    /**
     * 获取 店铺码。
     */
    public String getStoreCode() {
        return this.storeCode;
    }

    /**
     * 设置 店铺码。
     *
     * @param value 属性值
     */
    public void setStoreCode(String value) {
        this.storeCode = value;
    }

    private Integer cityId;

    /**
     * 获取 城市编码。
     */
    public Integer getCityId() {
        return this.cityId;
    }

    /**
     * 设置 城市编码。
     *
     * @param value 属性值
     */
    public void setCityId(Integer value) {
        this.cityId = value;
    }

    private Integer regionsId;

    /**
     * 获取 区县编码。
     */
    public Integer getRegionsId() {
        return this.regionsId;
    }

    /**
     * 设置 区县编码。
     *
     * @param value 属性值
     */
    public void setRegionsId(Integer value) {
        this.regionsId = value;
    }

    private Integer status;

    /**
     * 获取 状态：0，禁用；1，启用。
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 状态：0，禁用；1，启用。
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    private Date createTime;

    /**
     * 获取 创建时间。
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置 创建时间。
     *
     * @param value 属性值
     */
    public void setCreateTime(Date value) {
        this.createTime = value;
    }

    private Date updateTime;

    /**
     * 获取 更新时间。
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置 更新时间。
     *
     * @param value 属性值
     */
    public void setUpdateTime(Date value) {
        this.updateTime = value;
    }

    /**
     * HP派工时，启动标识为020单
     */
    public static final Integer HPREMARK_STATUS_ON  = 1;

    /**
     * HP派工时，停用标识为O2O单
     */
    public static final Integer HPREMARK_STATUS_OFF = 0;
    /**
     * HP派工标识
     */
    private int                 hpRemark;

    /**
     * 获取 O2O派工标识
     * @return
     */
    public int getHpRemark() {
        return hpRemark;
    }

    /**
     * 设置 O2O派工标识
     * @param hpRemark
     */
    public void setHpRemark(int hpRemark) {
        this.hpRemark = hpRemark;
    }

}