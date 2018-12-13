package com.haier.stock.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InvMachineSet implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID               = 1L;

    public static final int   STATUS_SALE_SUB_MACHINE        = 1;

    public static final int   STATUS_CANCEL_SALE_SUB_MACHINE = 0;

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

    private String mainSku;

    /**
     * 获取 主sku。
     */
    public String getMainSku() {
        return this.mainSku;
    }

    /**
     * 设置 主sku。
     *
     * @param value 属性值
     */
    public void setMainSku(String value) {
        this.mainSku = value;
    }

    private String subSku;

    /**
     * 获取 子sku。
     */
    public String getSubSku() {
        return this.subSku;
    }

    /**
     * 设置 子sku。
     *
     * @param value 属性值
     */
    public void setSubSku(String value) {
        this.subSku = value;
    }

    private String factoryCode;

    /**
     * 获取 工厂编码。
     */
    public String getFactoryCode() {
        return this.factoryCode;
    }

    /**
     * 设置 工厂编码。
     *
     * @param value 属性值
     */
    public void setFactoryCode(String value) {
        this.factoryCode = value;
    }

    private String stlnr;

    /**
     * 获取 物料单。
     */
    public String getStlnr() {
        return this.stlnr;
    }

    /**
     * 设置 物料单。
     *
     * @param value 属性值
     */
    public void setStlnr(String value) {
        this.stlnr = value;
    }

    private String posnr;

    /**
     * 获取 BOM项目号。
     */
    public String getPosnr() {
        return this.posnr;
    }

    /**
     * 设置 BOM项目号。
     *
     * @param value 属性值
     */
    public void setPosnr(String value) {
        this.posnr = value;
    }

    private BigDecimal menge;

    /**
     * 获取 组件数量。
     */
    public BigDecimal getMenge() {
        return this.menge;
    }

    /**
     * 设置 组件数量。
     *
     * @param value 属性值
     */
    public void setMenge(BigDecimal value) {
        this.menge = value;
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

    private String maktx1;

    /**
     * 获取 物料描述1。
     */
    public String getMaktx1() {
        return this.maktx1;
    }

    /**
     * 设置 物料描述1。
     *
     * @param value 属性值
     */
    public void setMaktx1(String value) {
        this.maktx1 = value;
    }

    private String maktx2;

    /**
     * 获取 物料描述2。
     */
    public String getMaktx2() {
        return this.maktx2;
    }

    /**
     * 设置 物料描述2。
     *
     * @param value 属性值
     */
    public void setMaktx2(String value) {
        this.maktx2 = value;
    }

    private Date updateTime;

    /**
     * 获取 修改时间。
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置 修改时间。
     *
     * @param value 属性值
     */
    public void setUpdateTime(Date value) {
        this.updateTime = value;
    }

    /**
     * 套机状态
     */
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private Integer isSaleSub;

    public Integer getIsSaleSub() {
        return isSaleSub;
    }

    public void setIsSaleSub(Integer isSaleSub) {
        this.isSaleSub = isSaleSub;
    }

    private String optUser;

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }

    private String operation;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
