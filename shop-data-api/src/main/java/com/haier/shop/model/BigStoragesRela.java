package com.haier.shop.model;

import java.io.Serializable;

/**
 * 大家电多级库位关系。
 *
 * <p>Table: <strong>BigStoragesRela</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>siteId</td><td>{@link Integer}</td><td>siteId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>code</td><td>{@link String}</td><td>Code</td><td>varchar</td><td>库位编码</td></tr>
 *   <tr><td>masterCode</td><td>{@link String}</td><td>masterCode</td><td>varchar</td><td>主库位编码</td></tr>
 *   <tr><td>centerCode</td><td>{@link String}</td><td>centerCode</td><td>varchar</td><td>中心库位</td></tr>
 *   <tr><td>masterShippingTime</td><td>{@link Integer}</td><td>masterShippingTime</td><td>int</td><td>主库转运时效（小时）</td></tr>
 *   <tr><td>centerShippingTime</td><td>{@link Integer}</td><td>centerShippingTime</td><td>int</td><td>中心库转运时效（小时）</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-8-26
 * @email yudi@sina.com
 */
public class BigStoragesRela implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -8315885331742849027L;

    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer siteId;

    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer value) {
        this.siteId = value;
    }

    private String code;

    /**
     * 获取 库位编码。
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 设置 库位编码。
     *
     * @param value 属性值
     */
    public void setCode(String value) {
        this.code = value;
    }

    private String masterCode;

    /**
     * 获取 主库位编码。
     */
    public String getMasterCode() {
        return this.masterCode;
    }

    /**
     * 设置 主库位编码。
     *
     * @param value 属性值
     */
    public void setMasterCode(String value) {
        this.masterCode = value;
    }

    private String centerCode;

    /**
     * 获取 中心库位。
     */
    public String getCenterCode() {
        return this.centerCode;
    }

    /**
     * 设置 中心库位。
     *
     * @param value 属性值
     */
    public void setCenterCode(String value) {
        this.centerCode = value;
    }

    private Integer masterShippingTime = 0;

    /**
     * 获取 主库转运时效（小时）。
     */
    public Integer getMasterShippingTime() {
        return this.masterShippingTime;
    }

    /**
     * 设置 主库转运时效（小时）。
     *
     * @param value 属性值
     */
    public void setMasterShippingTime(Integer value) {
        this.masterShippingTime = value;
    }

    private Integer centerShippingTime = 0;

    /**
     * 获取 中心库转运时效（小时）。
     */
    public Integer getCenterShippingTime() {
        return this.centerShippingTime;
    }

    /**
     * 设置 中心库转运时效（小时）。
     *
     * @param value 属性值
     */
    public void setCenterShippingTime(Integer value) {
        this.centerShippingTime = value;
    }

    /**
     * 主库运输距离
     */
    private String masterDistance;

    /**
     * 中心库运输距离
     */
    private String centerDistance;

    /**
     * 库位名称
     */
    private String name;

    /**
     * 主库位名称
     */
    private String masterName;

    /**
     * 中心库位名称
     */
    private String centerName;

    /**
     * 是否开通
     */
    private int flag;

    public String getMasterDistance() {
        return masterDistance;
    }

    public void setMasterDistance(String masterDistance) {
        this.masterDistance = masterDistance;
    }

    public String getCenterDistance() {
        return centerDistance;
    }

    public void setCenterDistance(String centerDistance) {
        this.centerDistance = centerDistance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}