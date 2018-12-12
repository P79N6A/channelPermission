package com.haier.shop.model;

import java.io.Serializable;

/**
 * 库存变化队列。
 *
 * <p>Table: <strong>stock_change_queue</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>流水号</td></tr>
 *   <tr><td>scode</td><td>{@link String}</td><td>scode</td><td>varchar</td><td>库位</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>物料编码</td></tr>
 *   <tr><td>isArrivalNotice</td><td>{@link String}</td><td>is_arrival_notice</td><td>varchar</td><td>是否需要到货通知</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-5-2
 * @email yudi@sina.com
 */
public class StockChangeQueue implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 5438261721538710619L;
    private Integer           id;

    /**
     * 获取 流水号。
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置 流水号。
     *
     * @param value 属性值
     */
    public void setId(Integer value) {
        this.id = value;
    }

    private String scode;

    /**
     * 获取 库位。
     */
    public String getScode() {
        return this.scode;
    }

    /**
     * 设置 库位。
     *
     * @param value 属性值
     */
    public void setScode(String value) {
        this.scode = value;
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

    private Integer isArrivalNotice = 0;

    /**
     * 获取 是否需要到货通知。
     * 1、是
     * 0、否
     */
    public Integer getIsArrivalNotice() {
        return this.isArrivalNotice;
    }

    /**
     * 设置 是否需要到货通知。
     * 1、是
     * 0、否
     * @param value 属性值
     */
    public void setIsArrivalNotice(Integer value) {
        this.isArrivalNotice = value;
    }

}