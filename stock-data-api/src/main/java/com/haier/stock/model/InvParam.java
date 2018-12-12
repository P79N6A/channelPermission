package com.haier.stock.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Table: <strong>inv_param</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 *   <tr style="background-color:#ddd;Text-align:Left;">
 *     <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 *   </tr>
 *   <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>自增列</td></tr>
 *   <tr><td>group</td><td>{@link String}</td><td>group</td><td>varchar</td><td>所属组</td></tr>
 *   <tr><td>key</td><td>{@link String}</td><td>key</td><td>varchar</td><td>键</td></tr>
 *   <tr><td>name</td><td>{@link String}</td><td>name</td><td>varchar</td><td>名称</td></tr>
 *   <tr><td>sort</td><td>{@link Integer}</td><td>sort</td><td>int</td><td>排序</td></tr>
 *   <tr><td>lastTime</td><td>{@link Date}</td><td>last_time</td><td>datetime</td><td>最后更新时间</td></tr>
 *   <tr><td>lastUser</td><td>{@link String}</td><td>last_user</td><td>varchar</td><td>最后更新人</td></tr>
 * </table>
 *
 * @author 
 * @date 2014-12-22
 * @email 
 */
public class InvParam implements Serializable {
    private Integer id;
    private String  paramGroup;
    private String  paramKey;
    private String  paramName;
    private String  paramSort;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParamGroup() {
        return paramGroup;
    }

    public void setParamGroup(String paramGroup) {
        this.paramGroup = paramGroup;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamSort() {
        return paramSort;
    }

    public void setParamSort(String paramSort) {
        this.paramSort = paramSort;
    }

    private Date lastTime;

    /**
     * 获取 最后更新时间。
     */
    public Date getLastTime() {
        return this.lastTime;
    }

    /**
     * 设置 最后更新时间。
     *
     * @param value 属性值
     */
    public void setLastTime(Date value) {
        this.lastTime = value;
    }

    private String lastUser;

    /**
     * 获取 最后更新人。
     */
    public String getLastUser() {
        return this.lastUser;
    }

    /**
     * 设置 最后更新人。
     *
     * @param value 属性值
     */
    public void setLastUser(String value) {
        this.lastUser = value;
    }

}