package com.haier.stock.model;

import java.io.Serializable;

/**
 * <p>Table: <strong>inv_section_2_oms</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>omsSec</td><td>{@link String}</td><td>oms_sec</td><td>varchar</td><td>OMS库位</td></tr>
 *   <tr><td>waSec</td><td>{@link String}</td><td>wa_sec</td><td>varchar</td><td>WA库位</td></tr>
 *   <tr><td>province</td><td>{@link String}</td><td>province</td><td>varchar</td><td>省略位</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-7-23
 * @email yudi@sina.com
 */
public class InvSection2Oms implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -6496682231244272827L;
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private String omsSec;

    /**
     * 获取 OMS库位。
     */
    public String getOmsSec() {
        return this.omsSec;
    }

    /**
     * 设置 OMS库位。
     *
     * @param value 属性值
     */
    public void setOmsSec(String value) {
        this.omsSec = value;
    }

    private String waSec;

    /**
     * 获取 WA库位。
     */
    public String getWaSec() {
        return this.waSec;
    }

    /**
     * 设置 WA库位。
     *
     * @param value 属性值
     */
    public void setWaSec(String value) {
        this.waSec = value;
    }

    private String province;

    /**
     * 获取 省略位。
     */
    public String getProvince() {
        return this.province;
    }

    /**
     * 设置 省略位。
     *
     * @param value 属性值
     */
    public void setProvince(String value) {
        this.province = value;
    }

}