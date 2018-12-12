package com.haier.shop.model;

import java.io.Serializable;

/**
 * <p>Table: <strong>CustomerCodes</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>siteId</td><td>{@link Integer}</td><td>siteId</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>addTime</td><td>{@link Integer}</td><td>addTime</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>code</td><td>{@link String}</td><td>code</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>title</td><td>{@link String}</td><td>title</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>updateTime</td><td>{@link Integer}</td><td>updateTime</td><td>int</td><td>&nbsp;</td></tr>
 * </table>
 *
 * @author 吴坤洋
 * @date 2017-12-23
 * @email yudi@sina.com
 */
public class CustomerCodes implements Serializable {
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

    private Integer siteId;

    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer value) {
        this.siteId = value;
    }

    private Integer addTime;

    public Integer getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Integer value) {
        this.addTime = value;
    }

    private String code;

    public String getCode() {
        return this.code;
    }

    public void setCode(String value) {
        this.code = value;
    }

    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    private Integer updateTime;

    public Integer getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Integer value) {
        this.updateTime = value;
    }

}