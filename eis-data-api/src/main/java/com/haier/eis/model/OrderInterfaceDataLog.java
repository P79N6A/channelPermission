package com.haier.eis.model;

import java.io.Serializable;
import java.util.Date;

/*
* 作者:张波
* 2017/12/26
*/
public class OrderInterfaceDataLog implements Serializable{
    /**
     * 网单信息接口日志
     */
    private static final long serialVersionUID = 1L;
    /**
     * 响应状态 - 成功
     */
    public static String RESPONSE_STATUS_SUCCESS    = "S";
    /**
     * 响应状态 - 失败
     */
    public static String RESPONSE_STATUS_ERROR      = "F";

    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private String interfaceCode;

    /**
     * 获取 接口编号。
     */
    public String getInterfaceCode() {
        return this.interfaceCode;
    }

    /**
     * 设置 接口编号。
     *
     * @param value 属性值
     */
    public void setInterfaceCode(String value) {
        this.interfaceCode = value;
    }

    private String requestData;

    /**
     * 获取 请求数据（json格式）。
     */
    public String getRequestData() {
        return this.requestData;
    }

    /**
     * 设置 请求数据（json格式）。
     *
     * @param value 属性值
     */
    public void setRequestData(String value) {
        this.requestData = value;
    }

    private Object responseData;

    /**
     * 获取 响应数据。
     */
    public Object getResponseData() {
        return this.responseData;
    }

    /**
     * 设置 响应数据。
     *
     * @param value 属性值
     */
    public void setResponseData(Object value) {
        this.responseData = value;
    }

    private Date requestTime;

    /**
     * 获取 请求时间。
     */
    public Date getRequestTime() {
        return this.requestTime;
    }

    /**
     * 设置 请求时间。
     *
     * @param value 属性值
     */
    public void setRequestTime(Date value) {
        this.requestTime = value;
    }

    private Date responseTime;

    /**
     * 获取 响应时间。
     */
    public Date getResponseTime() {
        return this.responseTime;
    }

    /**
     * 设置 响应时间。
     *
     * @param value 属性值
     */
    public void setResponseTime(Date value) {
        this.responseTime = value;
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

    private String responseStatus;

    /**
     * 获取 响应状态。
     *
     * <p>
     * Success：成功<br />
     * Error：失败
     */
    public String getResponseStatus() {
        return this.responseStatus;
    }

    /**
     * 设置 响应状态。
     *
     * <p>
     * Success：成功<br />
     * Error：失败
     *
     * @param value 属性值
     */
    public void setResponseStatus(String value) {
        this.responseStatus = value;
    }

    private String errorMessage;

    /**
     * 获取 错误信息。
     */
    public String getErrorMessage() {
        return this.errorMessage;
    }

    /**
     * 设置 错误信息。
     *
     * @param value 属性值
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    private String foreignKey;

    /**
     * 获取 外关键字。
     *
     * <p>
     * 例如：跟订单相关，就填订单编号；<br />
     * 跟采购单相关，就填采购单号...
     */
    public String getForeignKey() {
        return this.foreignKey;
    }

    /**
     * 设置 外关键字。
     *
     * <p>
     * 例如：跟订单相关，就填订单编号；<br />
     * 跟采购单相关，就填采购单号...
     *
     * @param value 属性值
     */
    public void setForeignKey(String value) {
        this.foreignKey = value;
    }

    private Long elapsedTime = 0L;

    /**
     * 获取 耗时，毫秒。
     */
    public Long getElapsedTime() {
        return this.elapsedTime;
    }

    /**
     * 设置 耗时，毫秒。
     *
     * @param value 属性值
     */
    public void setElapsedTime(Long value) {
        this.elapsedTime = value;
    }
}
