package com.haier.shop.model;

import java.io.Serializable;

public class LesStatusItem implements Serializable {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID    = 1L;

    /**
     * 状态 - 处理中
     */
    public static Integer     STATUS_WAIT         = 0;
    /**
     * 状态 - 已处理
     */
    public static Integer     STATUS_DONE         = 1;
    /**
     * 状态 - 出错
     */
    public static Integer     STATUS_ERROR        = -1;
    /**
     * 释放状态 - 待释放
     */
    public static Integer     RELEASE_STATUS_WAIT = 0;
    /**
     * 释放状态 - 已释放
     */
    public static Integer     RELEASE_STATUS_DONE = 1;

    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer lesStockSyncsId;

    public Integer getLesStockSyncsId() {
        return this.lesStockSyncsId;
    }

    public void setLesStockSyncsId(Integer value) {
        this.lesStockSyncsId = value;
    }

    private String corderSn;

    public String getCorderSn() {
        return this.corderSn;
    }

    public void setCorderSn(String value) {
        this.corderSn = value;
    }

    private String outping;

    public String getOutping() {
        return this.outping;
    }

    public void setOutping(String value) {
        this.outping = value;
    }

    private Long updTime;

    public Long getUpdTime() {
        return this.updTime;
    }

    public void setUpdTime(Long value) {
        this.updTime = value;
    }

    private String invoiceNumber;

    /**
     * 获取 类型。
     *
     * <p>
     * 1：库存变化对应stock<br />
     * 2：出库对应status
     */
    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    /**
     * 设置 类型。
     *
     * <p>
     * 1：库存变化对应stock<br />
     * 2：出库对应status
     *
     * @param value 属性值
     */
    public void setInvoiceNumber(String value) {
        this.invoiceNumber = value;
    }

    private String invoiceName;

    public String getInvoiceName() {
        return this.invoiceName;
    }

    public void setInvoiceName(String value) {
        this.invoiceName = value;
    }

    private Integer itype;

    public Integer getItype() {
        return this.itype;
    }

    public void setItype(Integer value) {
        this.itype = value;
    }

    private String data;

    /**
     * 获取 数据内容。
     */
    public String getData() {
        return this.data;
    }

    /**
     * 设置 数据内容。
     *
     * @param value 属性值
     */
    public void setData(String value) {
        this.data = value;
    }

    private Integer status;

    /**
     * 获取 状态。
     *
     * <p>
     * 0：待处理<br />
     * 1：已处理<br />
     * -1:出错
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 状态。
     *
     * <p>
     * 0：待处理<br />
     * 1：已处理<br />
     * -1:出错
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    private Integer releaseStatus;

    /**
     * 获取 库存释放状态。
     *
     * <p>
     * 0：未释放<br />
     * 1：已释放
     */
    public Integer getReleaseStatus() {
        return this.releaseStatus;
    }

    /**
     * 设置 库存释放状态。
     *
     * <p>
     * 0：未释放<br />
     * 1：已释放
     *
     * @param value 属性值
     */
    public void setReleaseStatus(Integer value) {
        this.releaseStatus = value;
    }

    private Integer processTime = 0;

    /**
     * 获取 处理时间。
     */
    public Integer getProcessTime() {
        return this.processTime;
    }

    /**
     * 设置 处理时间。
     *
     * @param value 属性值
     */
    public void setProcessTime(Integer value) {
        this.processTime = value;
    }

    private Integer addTime;

    /**
     * 获取 创建时间。
     */
    public Integer getAddTime() {
        return this.addTime;
    }

    /**
     * 设置 创建时间。
     *
     * @param value 属性值
     */
    public void setAddTime(Integer value) {
        this.addTime = value;
    }

    private String errorMessage;

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

    private String versionCode;

    /**
     * 获取 版本编号 - 作为唯一标识使用。
     */
    public String getVersionCode() {
        return this.versionCode;
    }

    /**
     * 设置 版本编号 - 作为唯一标识使用。
     *
     * @param value 属性值
     */
    public void setVersionCode(String value) {
        this.versionCode = value;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    private String channelCode;




}
