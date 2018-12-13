package com.haier.shop.model;

import java.io.Serializable;

/**
 * 生活家电20库位关系。
 *
 * <p>Table: <strong>StoragesRela</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>siteId</td><td>{@link Integer}</td><td>siteId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>code</td><td>{@link String}</td><td>Code</td><td>varchar</td><td>库位编码</td></tr>
 *   <tr><td>masterCode</td><td>{@link String}</td><td>masterCode</td><td>varchar</td><td>主库位编码</td></tr>
 *   <tr><td>centerCode</td><td>{@link String}</td><td>centerCode</td><td>varchar</td><td>中心库位</td></tr>
 *   <tr><td>mulStoreCode</td><td>{@link String}</td><td>mulStoreCode</td><td>varchar</td><td>多级库位</td></tr>
 *   <tr><td>isMaster</td><td>{@link Integer}</td><td>isMaster</td><td>tinyint</td><td>是否主库，默认1，0=主库；1=从库</td></tr>
 * </table>
 *
 */
public class StoragesRela implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -8593572970113181305L;

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

    private String mulStoreCode;

    /**
     * 获取 多级库位。
     */
    public String getMulStoreCode() {
        return this.mulStoreCode;
    }

    /**
     * 设置 多级库位。
     *
     * @param value 属性值
     */
    public void setMulStoreCode(String value) {
        this.mulStoreCode = value;
    }

    private Integer isMaster = 1;

    /**
     * 获取 是否主库，默认1，0=主库；1=从库。
     */
    public Integer getIsMaster() {
        return this.isMaster;
    }

    /**
     * 设置 是否主库，默认1，0=主库；1=从库。
     *
     * @param value 属性值
     */
    public void setIsMaster(Integer value) {
        this.isMaster = value;
    }

    private String mulStoreName;

    public String getMulStoreName() {
        return mulStoreName;
    }

    public void setMulStoreName(String mulStoreName) {
        this.mulStoreName = mulStoreName;
    }

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
}