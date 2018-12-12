package com.haier.shop.model;

import java.io.Serializable;

/**
 * 库存覆盖城市表。
 *
 * <p>Table: <strong>StorageCities</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>provinceId</td><td>{@link Integer}</td><td>provinceId</td><td>int</td><td>省ID</td></tr>
 *   <tr><td>cityId</td><td>{@link Integer}</td><td>cityId</td><td>int</td><td>市ID</td></tr>
 *   <tr><td>regionId</td><td>{@link Integer}</td><td>regionId</td><td>int</td><td>地区ID</td></tr>
 *   <tr><td>provinceName</td><td>{@link String}</td><td>provinceName</td><td>varchar</td><td>省名称</td></tr>
 *   <tr><td>cityName</td><td>{@link String}</td><td>cityName</td><td>varchar</td><td>市名称</td></tr>
 *   <tr><td>regionName</td><td>{@link String}</td><td>regionName</td><td>varchar</td><td>区县名称</td></tr>
 *   <tr><td>sCodeA</td><td>{@link String}</td><td>sCodeA</td><td>varchar</td><td>大家电库房编码</td></tr>
 *   <tr><td>sCodeB</td><td>{@link String}</td><td>sCodeB</td><td>varchar</td><td>小家电库房编码</td></tr>
 *   <tr><td>onedayLimit</td><td>{@link Integer}</td><td>onedayLimit</td><td>tinyint</td><td>24小时限时达</td></tr>
 *   <tr><td>tcCover</td><td>{@link Integer}</td><td>tcCover</td><td>tinyint</td><td>TC覆盖能力</td></tr>
 *   <tr><td>tcLimit</td><td>{@link String}</td><td>tcLimit</td><td>varchar</td><td>TC速度</td></tr>
 *   <tr><td>pointCover</td><td>{@link Integer}</td><td>pointCover</td><td>tinyint</td><td>网点覆盖能力</td></tr>
 *   <tr><td>pointLimit</td><td>{@link String}</td><td>pointLimit</td><td>varchar</td><td>网点速度</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-8-26
 * @email yudi@sina.com
 */
public class StorageCities implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -3002959605862581419L;

    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer provinceId;

    /**
     * 获取 省ID。
     */
    public Integer getProvinceId() {
        return this.provinceId;
    }

    /**
     * 设置 省ID。
     *
     * @param value 属性值
     */
    public void setProvinceId(Integer value) {
        this.provinceId = value;
    }

    private Integer cityId;

    /**
     * 获取 市ID。
     */
    public Integer getCityId() {
        return this.cityId;
    }

    /**
     * 设置 市ID。
     *
     * @param value 属性值
     */
    public void setCityId(Integer value) {
        this.cityId = value;
    }

    private Integer regionId;

    /**
     * 获取 地区ID。
     */
    public Integer getRegionId() {
        return this.regionId;
    }

    /**
     * 设置 地区ID。
     *
     * @param value 属性值
     */
    public void setRegionId(Integer value) {
        this.regionId = value;
    }

    private String provinceName;

    /**
     * 获取 省名称。
     */
    public String getProvinceName() {
        return this.provinceName;
    }

    /**
     * 设置 省名称。
     *
     * @param value 属性值
     */
    public void setProvinceName(String value) {
        this.provinceName = value;
    }

    private String cityName;

    /**
     * 获取 市名称。
     */
    public String getCityName() {
        return this.cityName;
    }

    /**
     * 设置 市名称。
     *
     * @param value 属性值
     */
    public void setCityName(String value) {
        this.cityName = value;
    }

    private String regionName;

    /**
     * 获取 区县名称。
     */
    public String getRegionName() {
        return this.regionName;
    }

    /**
     * 设置 区县名称。
     *
     * @param value 属性值
     */
    public void setRegionName(String value) {
        this.regionName = value;
    }

    private String sCodeA;

    /**
     * 获取 大家电库房编码。
     */
    public String getSCodeA() {
        return this.sCodeA;
    }

    /**
     * 设置 大家电库房编码。
     *
     * @param value 属性值
     */
    public void setSCodeA(String value) {
        this.sCodeA = value;
    }

    private String sCodeB;

    /**
     * 获取 小家电库房编码。
     */
    public String getSCodeB() {
        return this.sCodeB;
    }

    /**
     * 设置 小家电库房编码。
     *
     * @param value 属性值
     */
    public void setSCodeB(String value) {
        this.sCodeB = value;
    }

    private Integer onedayLimit;

    /**
     * 获取 24小时限时达。
     */
    public Integer getOnedayLimit() {
        return this.onedayLimit;
    }

    /**
     * 设置 24小时限时达。
     *
     * @param value 属性值
     */
    public void setOnedayLimit(Integer value) {
        this.onedayLimit = value;
    }

    private Integer tcCover;

    /**
     * 获取 TC覆盖能力。
     */
    public Integer getTcCover() {
        return this.tcCover;
    }

    /**
     * 设置 TC覆盖能力。
     *
     * @param value 属性值
     */
    public void setTcCover(Integer value) {
        this.tcCover = value;
    }

    private String tcLimit;

    /**
     * 获取 TC速度。
     */
    public String getTcLimit() {
        return this.tcLimit;
    }

    /**
     * 设置 TC速度。
     *
     * @param value 属性值
     */
    public void setTcLimit(String value) {
        this.tcLimit = value;
    }

    private Integer pointCover;

    /**
     * 获取 网点覆盖能力。
     */
    public Integer getPointCover() {
        return this.pointCover;
    }

    /**
     * 设置 网点覆盖能力。
     *
     * @param value 属性值
     */
    public void setPointCover(Integer value) {
        this.pointCover = value;
    }

    private String pointLimit;

    /**
     * 获取 网点速度。
     */
    public String getPointLimit() {
        return this.pointLimit;
    }

    /**
     * 设置 网点速度。
     *
     * @param value 属性值
     */
    public void setPointLimit(String value) {
        this.pointLimit = value;
    }

}