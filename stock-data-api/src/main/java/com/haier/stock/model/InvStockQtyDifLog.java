package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Table: <strong>inv_stock_qty_dif_log</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>secCode</td><td>{@link String}</td><td>sec_code</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>&nbsp;</td></tr>
 *   <tr><td>lesStockQty</td><td>{@link Integer}</td><td>les_stock_qty</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>stockQty</td><td>{@link Integer}</td><td>stock_qty</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>timestamp/date</td><td>&nbsp;</td></tr>
 *   <tr><td>createUser</td><td>{@link String}</td><td>create_user</td><td>varchar</td><td>&nbsp;</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-12-16
 * @email yudi@sina.com
 */
public class InvStockQtyDifLog implements Serializable {
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private String secCode;

    public String getSecCode() {
        return this.secCode;
    }

    public void setSecCode(String value) {
        this.secCode = value;
    }

    private String sku;

    public String getSku() {
        return this.sku;
    }

    public void setSku(String value) {
        this.sku = value;
    }

    private Integer lesStockQty;

    public Integer getLesStockQty() {
        return this.lesStockQty;
    }

    public void setLesStockQty(Integer value) {
        this.lesStockQty = value;
    }

    private Integer stockQty;

    public Integer getStockQty() {
        return this.stockQty;
    }

    public void setStockQty(Integer value) {
        this.stockQty = value;
    }

    private Date createTime;

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date value) {
        this.createTime = value;
    }

    private String createUser;

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String value) {
        this.createUser = value;
    }

}