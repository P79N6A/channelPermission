package com.haier.stock.model;

import java.io.Serializable;

/**
 * <p>Table: <strong>inv_reserved_to_release</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>batchId</td><td>{@link Integer}</td><td>batch_id</td><td>int</td><td>&nbsp;</td></tr>
 *   <tr><td>createTime</td><td>{@link Object}</td><td>crtime</td><td>datetimn</td><td>&nbsp;</td></tr>
 *   <tr><td>releaseTime</td><td>{@link Object}</td><td>release_time</td><td>datetimn</td><td>&nbsp;</td></tr>
 *   <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>int</td><td>&nbsp;</td></tr>
 * </table>
 *
 * @author 刘志斌
 * @date 2013-11-28
 * @email yudi@sina.com
 */
public class InvReservedToRelease implements Serializable {

    public static final int STATUS_DONE = 1;
    public static final int STATUS_UNDO = 0;

    private Integer         id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer batchId;

    public Integer getBatchId() {
        return this.batchId;
    }

    public void setBatchId(Integer value) {
        this.batchId = value;
    }

    private Object createTime;

    public Object getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Object value) {
        this.createTime = value;
    }

    private Object releaseTime;

    public Object getReleaseTime() {
        return this.releaseTime;
    }

    public void setReleaseTime(Object value) {
        this.releaseTime = value;
    }

    private Integer status;

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer value) {
        this.status = value;
    }

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}