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
     * 鑾峰彇 娴佹按鍙枫��
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 璁剧疆 娴佹按鍙枫��
     *
     * @param value 灞炴�у��
     */
    public void setId(Integer value) {
        this.id = value;
    }

    private String mainSku;

    /**
     * 鑾峰彇 涓籹ku銆�
     */
    public String getMainSku() {
        return this.mainSku;
    }

    /**
     * 璁剧疆 涓籹ku銆�
     *
     * @param value 灞炴�у��
     */
    public void setMainSku(String value) {
        this.mainSku = value;
    }

    private String subSku;

    /**
     * 鑾峰彇 瀛恠ku銆�
     */
    public String getSubSku() {
        return this.subSku;
    }

    /**
     * 璁剧疆 瀛恠ku銆�
     *
     * @param value 灞炴�у��
     */
    public void setSubSku(String value) {
        this.subSku = value;
    }

    private String factoryCode;

    /**
     * 鑾峰彇 宸ュ巶缂栫爜銆�
     */
    public String getFactoryCode() {
        return this.factoryCode;
    }

    /**
     * 璁剧疆 宸ュ巶缂栫爜銆�
     *
     * @param value 灞炴�у��
     */
    public void setFactoryCode(String value) {
        this.factoryCode = value;
    }

    private String stlnr;

    /**
     * 鑾峰彇 鐗╂枡鍗曘��
     */
    public String getStlnr() {
        return this.stlnr;
    }

    /**
     * 璁剧疆 鐗╂枡鍗曘��
     *
     * @param value 灞炴�у��
     */
    public void setStlnr(String value) {
        this.stlnr = value;
    }

    private String posnr;

    /**
     * 鑾峰彇 BOM椤圭洰鍙枫��
     */
    public String getPosnr() {
        return this.posnr;
    }

    /**
     * 璁剧疆 BOM椤圭洰鍙枫��
     *
     * @param value 灞炴�у��
     */
    public void setPosnr(String value) {
        this.posnr = value;
    }

    private BigDecimal menge;

    /**
     * 鑾峰彇 缁勪欢鏁伴噺銆�
     */
    public BigDecimal getMenge() {
        return this.menge;
    }

    /**
     * 璁剧疆 缁勪欢鏁伴噺銆�
     *
     * @param value 灞炴�у��
     */
    public void setMenge(BigDecimal value) {
        this.menge = value;
    }

    private Date createTime;

    /**
     * 鑾峰彇 鍒涘缓鏃堕棿銆�
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 璁剧疆 鍒涘缓鏃堕棿銆�
     *
     * @param value 灞炴�у��
     */
    public void setCreateTime(Date value) {
        this.createTime = value;
    }

    private String maktx1;

    /**
     * 鑾峰彇 鐗╂枡鎻忚堪1銆�
     */
    public String getMaktx1() {
        return this.maktx1;
    }

    /**
     * 璁剧疆 鐗╂枡鎻忚堪1銆�
     *
     * @param value 灞炴�у��
     */
    public void setMaktx1(String value) {
        this.maktx1 = value;
    }

    private String maktx2;

    /**
     * 鑾峰彇 鐗╂枡鎻忚堪2銆�
     */
    public String getMaktx2() {
        return this.maktx2;
    }

    /**
     * 璁剧疆 鐗╂枡鎻忚堪2銆�
     *
     * @param value 灞炴�у��
     */
    public void setMaktx2(String value) {
        this.maktx2 = value;
    }

    private Date updateTime;

    /**
     * 鑾峰彇 淇敼鏃堕棿銆�
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 璁剧疆 淇敼鏃堕棿銆�
     *
     * @param value 灞炴�у��
     */
    public void setUpdateTime(Date value) {
        this.updateTime = value;
    }

    /**
     * 濂楁満鐘舵��
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
