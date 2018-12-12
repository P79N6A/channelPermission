package com.haier.stock.model;

import java.io.Serializable;

/**
 * <p>Table: <strong>Inv_gd_factory</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>factory</td><td>{@link String}</td><td>factory</td><td>varchar</td><td>&nbsp;</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2014-8-29
 * @email yudi@sina.com
 */
public class InvGdFactory implements Serializable {
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private String factory;

    public String getFactory() {
        return this.factory;
    }

    public void setFactory(String value) {
        this.factory = value;
    }

    private String department;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    private String secCode;

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }

}