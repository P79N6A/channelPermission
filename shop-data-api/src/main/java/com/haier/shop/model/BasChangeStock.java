package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Table: <strong>bas_change_stock</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>cityId</td><td>{@link Integer}</td><td>city_id</td><td>int</td><td>城市编码</td></tr>
 *   <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>物料编码</td></tr>
 *   <tr><td>hasStock</td><td>{@link Object}</td><td>has_stock</td><td>bit</td><td>是否有库存：1，有；0，无</td></tr>
 *   <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>date</td><td>更新时间</td></tr>
 * </table>
 *
 * @author 
 * @date 2015-7-13
 * @email 
 */
public class BasChangeStock implements Serializable {
    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer cityId;

    /**
     * 获取 城市编码。
     */
    public Integer getCityId() {
        return this.cityId;
    }

    /**
     * 设置 城市编码。
     *
     * @param value 属性值
     */
    public void setCityId(Integer value) {
        this.cityId = value;
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

    private Object hasStock;

    /**
     * 获取 是否有库存：1，有；0，无。
     */
    public Object getHasStock() {
        return this.hasStock;
    }

    /**
     * 设置 是否有库存：1，有；0，无。
     *
     * @param value 属性值
     */
    public void setHasStock(Object value) {
        this.hasStock = value;
    }

    private Date updateTime;

    /**
     * 获取 更新时间。
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置 更新时间。
     *
     * @param value 属性值
     */
    public void setUpdateTime(Date value) {
        this.updateTime = value;
    }

}