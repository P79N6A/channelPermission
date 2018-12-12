package com.haier.eis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存业务处理队列。
 *
 * <p>Table: <strong>eis_stock_business_queue</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>stockTransQueueId</td><td>{@link Integer}</td><td>stock_trans_queue_id</td><td>int</td><td>les_stock_trans_queue标识</td></tr>
 *   <tr><td>addTime</td><td>{@link Date}</td><td>add_time</td><td>datetime</td><td>&nbsp;</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-11-11
 * @email yudi@sina.com
 */
public class EisStockBusinessQueue implements Serializable {
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

    private Integer stockTransQueueId;

    /**
     * 获取 les_stock_trans_queue标识。
     */
    public Integer getStockTransQueueId() {
        return this.stockTransQueueId;
    }

    /**
     * 设置 les_stock_trans_queue标识。
     *
     * @param value 属性值
     */
    public void setStockTransQueueId(Integer value) {
        this.stockTransQueueId = value;
    }

    private Date addTime;

    public Date getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Date value) {
        this.addTime = value;
    }

}