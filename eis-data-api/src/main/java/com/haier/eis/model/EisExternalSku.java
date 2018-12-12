package com.haier.eis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 物料对照表。
 *
 * <p>Table: <strong>eis_external_sku</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>物料编码</td></tr>
 *   <tr><td>externalSku</td><td>{@link String}</td><td>externalSku</td><td>varchar</td><td>外部SKU</td></tr>
 *   <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>int</td><td>状态（1.启用0.弃用）</td></tr>
 *   <tr><td>type</td><td>{@link String}</td><td>type</td><td>varchar</td><td>类型</td></tr>
 *   <tr><td>addtime</td><td>{@link Date}</td><td>addtime</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>updatetime</td><td>{@link Date}</td><td>updatetime</td><td>datetime</td><td>更新时间</td></tr>
 *   <tr><td>createor</td><td>{@link String}</td><td>createor</td><td>varchar</td><td>创建人</td></tr>
 *   <tr><td>modifier</td><td>{@link String}</td><td>modifier</td><td>varchar</td><td>修改人</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-10-8
 * @email yudi@sina.com
 */
public class EisExternalSku implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private String sku;

    /**
     * 获取 物料编码。
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * 设置 物料编码。
     *
     * @param value 属性值
     */
    public void setSku(String value) {
        this.sku = value;
    }

    private String externalSku;

    /**
     * 获取 外部SKU。
     */
    public String getExternalSku() {
        return this.externalSku;
    }

    /**
     * 设置 外部SKU。
     *
     * @param value 属性值
     */
    public void setExternalSku(String value) {
        this.externalSku = value;
    }

    private Integer status;

    /**
     * 获取 状态（1.启用0.弃用）。
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 状态（1.启用0.弃用）。
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    /**
     * R码
     */
    public static final String TYPE_R = "R";

    private String             type;

    /**
     * 获取 类型。
     */
    public String getType() {
        return this.type;
    }

    /**
     * 设置 类型。
     *
     * @param value 属性值
     */
    public void setType(String value) {
        this.type = value;
    }

    private Date addtime;

    /**
     * 获取 创建时间。
     */
    public Date getAddtime() {
        return this.addtime;
    }

    /**
     * 设置 创建时间。
     *
     * @param value 属性值
     */
    public void setAddtime(Date value) {
        this.addtime = value;
    }

    private Date updatetime;

    /**
     * 获取 更新时间。
     */
    public Date getUpdatetime() {
        return this.updatetime;
    }

    /**
     * 设置 更新时间。
     *
     * @param value 属性值
     */
    public void setUpdatetime(Date value) {
        this.updatetime = value;
    }

    private String createor;

    /**
     * 获取 创建人。
     */
    public String getCreateor() {
        return this.createor;
    }

    /**
     * 设置 创建人。
     *
     * @param value 属性值
     */
    public void setCreateor(String value) {
        this.createor = value;
    }

    private String modifier;

    /**
     * 获取 修改人。
     */
    public String getModifier() {
        return this.modifier;
    }

    /**
     * 设置 修改人。
     *
     * @param value 属性值
     */
    public void setModifier(String value) {
        this.modifier = value;
    }

}