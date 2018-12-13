package com.haier.purchase.data.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Table: <strong>purchase_order_queue</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>采购订单队列ID，自增长</td></tr>
 *   <tr><td>poItemId</td><td>{@link Integer}</td><td>po_item_id</td><td>int</td><td>订单明细ID</td></tr>
 *   <tr><td>poId</td><td>{@link Integer}</td><td>po_id</td><td>int</td><td>自增ID</td></tr>
 *   <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>smallint</td><td>处理状态，-1：出错，0：待处理，1：已处理<br /></td></tr>
 *   <tr><td>lesNo</td><td>{@link String}</td><td>les_no</td><td>varchar</td><td>Les返回凭证号</td></tr>
 *   <tr><td>lesMsg</td><td>{@link String}</td><td>les_msg</td><td>varchar</td><td>Les返回结果信息</td></tr>
 *   <tr><td>poTime</td><td>{@link Date}</td><td>po_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>createTime</td><td>{@link Date}</td><td>create_time</td><td>datetime</td><td>创建时间</td></tr>
 *   <tr><td>updateTime</td><td>{@link Date}</td><td>update_time</td><td>datetime</td><td>&nbsp;</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-6-8
 * @email yudi@sina.com
 */
public class PurchaseOrderQueue implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -4762042419279843201L;
    private Integer           id;

    /**
     * 获取 采购订单队列ID，自增长。
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置 采购订单队列ID，自增长。
     *
     * @param value 属性值
     */
    public void setId(Integer value) {
        this.id = value;
    }

    private Integer poItemId;

    /**
     * 获取 订单明细ID。
     */
    public Integer getPoItemId() {
        return this.poItemId;
    }

    /**
     * 设置 订单明细ID。
     *
     * @param value 属性值
     */
    public void setPoItemId(Integer value) {
        this.poItemId = value;
    }

    private Integer poId;

    /**
     * 获取 自增ID。
     */
    public Integer getPoId() {
        return this.poId;
    }

    /**
     * 设置 自增ID。
     *
     * @param value 属性值
     */
    public void setPoId(Integer value) {
        this.poId = value;
    }

    private Integer status;

    /**
     * 获取 处理状态，-1：出错，0：待处理，1：已处理。
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 处理状态，-1：出错，0：待处理，1：已处理。
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    private String lesNo;

    /**
     * 获取 Les返回凭证号。
     */
    public String getLesNo() {
        return this.lesNo;
    }

    /**
     * 设置 Les返回凭证号。
     *
     * @param value 属性值
     */
    public void setLesNo(String value) {
        this.lesNo = value;
    }

    private String lesMsg;

    /**
     * 获取 Les返回结果信息。
     */
    public String getLesMsg() {
        return lesMsg;
    }

    /**
     * 设置 Les返回结果信息。
     *
     * @param value 属性值
     */
    public void setLesMsg(String lesMsg) {
        this.lesMsg = lesMsg;
    }

    private Date poTime;

    public Date getPoTime() {
        return poTime;
    }

    public void setPoTime(Date poTime) {
        this.poTime = poTime;
    }

    private Date createTime;

    /**
     * 获取 创建时间。
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置 创建时间。
     *
     * @param value 属性值
     */
    public void setCreateTime(Date value) {
        this.createTime = value;
    }

    private Date updateTime;

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date value) {
        this.updateTime = value;
    }

    @Override
    public String toString() {
        return "PurchaseOrderQueue [id=" + id + ", poItemId=" + poItemId + ", poId=" + poId
               + ", status=" + status + ", lesNo=" + lesNo + ", createTime=" + createTime
               + ", updateTime=" + updateTime + "]";
    }

}