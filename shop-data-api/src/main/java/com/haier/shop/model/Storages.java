package com.haier.shop.model;

import java.io.Serializable;

/**
 * 库位信息表。
 *
 * <p>Table: <strong>Storages</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>isFreightInvoice</td><td>{@link Integer}</td><td>isFreightInvoice</td><td>tinyint</td><td>是否开启货票同行</td></tr>
 *   <tr><td>isSupportCod</td><td>{@link Integer}</td><td>isSupportCod</td><td>tinyint</td><td>是否支持货到付款</td></tr>
 *   <tr><td>name</td><td>{@link String}</td><td>name</td><td>varchar</td><td>库位名称</td></tr>
 *   <tr><td>code</td><td>{@link String}</td><td>code</td><td>varchar</td><td>库位编码</td></tr>
 *   <tr><td>type</td><td>{@link Integer}</td><td>type</td><td>smallint</td><td>库位类型 1 大电类型 (83) 2 小电类型(5)</td></tr>
 *   <tr><td>centerCode</td><td>{@link String}</td><td>centerCode</td><td>varchar</td><td>网单中心代码</td></tr>
 *   <tr><td>taobaoStoreCode</td><td>{@link String}</td><td>taobaoStoreCode</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>centerCity</td><td>{@link Integer}</td><td>centerCity</td><td>int</td><td>所属中心(城市ID)</td></tr>
 *   <tr><td>industryCode</td><td>{@link String}</td><td>industryCode</td><td>varchar</td><td>工贸代码</td></tr>
 *   <tr><td>industryName</td><td>{@link String}</td><td>industryName</td><td>varchar</td><td>工贸名称</td></tr>
 *   <tr><td>area</td><td>{@link Integer}</td><td>area</td><td>tinyint</td><td>所属区域</td></tr>
 *   <tr><td>limitTime</td><td>{@link String}</td><td>limitTime</td><td>varchar</td><td>预计送达时间</td></tr>
 *   <tr><td>remark</td><td>{@link String}</td><td>remark</td><td>varchar</td><td>备注</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-4-30
 * @email yudi@sina.com
 */
public class Storages implements Serializable {
    /* 库位大小 */
    public static final Integer BIG_APPLIANCE    = 1;
    public static final Integer SMALL_APPLIANCE  = 2;

    /* 所属区域 */
    public static final Integer EAST_AREA        = 1;
    public static final Integer WEST_AREA        = 2;
    public static final Integer SOUTH_AREA       = 3;
    public static final Integer NORTH_AREA       = 4;

    /** Comment for <code>serialVersionUID</code> */
    private static final long   serialVersionUID = -8526933324251102194L;
    private Integer             id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer isFreightInvoice;

    /**
     * 获取 是否开启货票同行。
     */
    public Integer getIsFreightInvoice() {
        return this.isFreightInvoice;
    }

    /**
     * 设置 是否开启货票同行。
     *
     * @param value 属性值
     */
    public void setIsFreightInvoice(Integer value) {
        this.isFreightInvoice = value;
    }

    private Integer isSupportCod;

    /**
     * 获取 是否支持货到付款。
     */
    public Integer getIsSupportCod() {
        return this.isSupportCod;
    }

    /**
     * 设置 是否支持货到付款。
     *
     * @param value 属性值
     */
    public void setIsSupportCod(Integer value) {
        this.isSupportCod = value;
    }

    private String name;

    /**
     * 获取 库位名称。
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置 库位名称。
     *
     * @param value 属性值
     */
    public void setName(String value) {
        this.name = value;
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

    private Integer type;

    /**
     * 获取 库位类型 1 大电类型 (83) 2 小电类型(5)。
     */
    public Integer getType() {
        return this.type;
    }

    /**
     * 设置 库位类型 1 大电类型 (83) 2 小电类型(5)。
     *
     * @param value 属性值
     */
    public void setType(Integer value) {
        this.type = value;
    }

    private String centerCode;

    /**
     * 获取 网单中心代码。
     */
    public String getCenterCode() {
        return this.centerCode;
    }

    /**
     * 设置 网单中心代码。
     *
     * @param value 属性值
     */
    public void setCenterCode(String value) {
        this.centerCode = value;
    }

    private String taobaoStoreCode;

    public String getTaobaoStoreCode() {
        return this.taobaoStoreCode;
    }

    public void setTaobaoStoreCode(String value) {
        this.taobaoStoreCode = value;
    }

    private Integer centerCity;

    /**
     * 获取 所属中心(城市ID)。
     */
    public Integer getCenterCity() {
        return this.centerCity;
    }

    /**
     * 设置 所属中心(城市ID)。
     *
     * @param value 属性值
     */
    public void setCenterCity(Integer value) {
        this.centerCity = value;
    }

    private String industryCode;

    /**
     * 获取 工贸代码。
     */
    public String getIndustryCode() {
        return this.industryCode;
    }

    /**
     * 设置 工贸代码。
     *
     * @param value 属性值
     */
    public void setIndustryCode(String value) {
        this.industryCode = value;
    }

    private String industryName;

    /**
     * 获取 工贸名称。
     */
    public String getIndustryName() {
        return this.industryName;
    }

    /**
     * 设置 工贸名称。
     *
     * @param value 属性值
     */
    public void setIndustryName(String value) {
        this.industryName = value;
    }

    private Integer area;

    /**
     * 获取 所属区域。
     */
    public Integer getArea() {
        return this.area;
    }

    /**
     * 设置 所属区域。
     *
     * @param value 属性值
     */
    public void setArea(Integer value) {
        this.area = value;
    }

    private String limitTime;

    /**
     * 获取 预计送达时间。
     */
    public String getLimitTime() {
        return this.limitTime;
    }

    /**
     * 设置 预计送达时间。
     *
     * @param value 属性值
     */
    public void setLimitTime(String value) {
        this.limitTime = value;
    }

    private String remark;

    /**
     * 获取 备注。
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置 备注。
     *
     * @param value 属性值
     */
    public void setRemark(String value) {
        this.remark = value;
    }

}