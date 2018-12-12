package com.haier.shop.model;

import java.io.Serializable;
/*
* 作者:张波
* 2017/12/19
* */
public class OrderWorkflowRegion implements Serializable{
    private Integer regionId;

    /**
     * 获取 区县id。
     */
    public Integer getRegionId() {
        return this.regionId;
    }

    /**
     * 设置 区县id。
     *
     * @param value 属性值
     */
    public void setRegionId(Integer value) {
        this.regionId = value;
    }

    private String regionName;

    /**
     * 获取 区县名称。
     */
    public String getRegionName() {
        return this.regionName;
    }

    /**
     * 设置 区县名称。
     *
     * @param value 属性值
     */
    public void setRegionName(String value) {
        this.regionName = value;
    }

    private Integer cityId;

    /**
     * 获取 城市id。
     */
    public Integer getCityId() {
        return this.cityId;
    }

    /**
     * 设置 城市id。
     *
     * @param value 属性值
     */
    public void setCityId(Integer value) {
        this.cityId = value;
    }

    private String cityName;

    /**
     * 获取 城市名称。
     */
    public String getCityName() {
        return this.cityName;
    }

    /**
     * 设置 城市名称。
     *
     * @param value 属性值
     */
    public void setCityName(String value) {
        this.cityName = value;
    }

    private Integer provinceId;

    /**
     * 获取 省份id。
     */
    public Integer getProvinceId() {
        return this.provinceId;
    }

    /**
     * 设置 省份id。
     *
     * @param value 属性值
     */
    public void setProvinceId(Integer value) {
        this.provinceId = value;
    }

    private String provinceName;

    /**
     * 获取 省份名称。
     */
    public String getProvinceName() {
        return this.provinceName;
    }

    /**
     * 设置 省份名称。
     *
     * @param value 属性值
     */
    public void setProvinceName(String value) {
        this.provinceName = value;
    }

    private String secCode;

    /**
     * 获取 仓库编码。
     */
    public String getSecCode() {
        return this.secCode;
    }

    /**
     * 设置 仓库编码。
     *
     * @param value 属性值
     */
    public void setSecCode(String value) {
        this.secCode = value;
    }

    private String secName;

    /**
     * 获取 仓库名称。
     */
    public String getSecName() {
        return this.secName;
    }

    /**
     * 设置 仓库名称。
     *
     * @param value 属性值
     */
    public void setSecName(String value) {
        this.secName = value;
    }

    private String gmName;

    /**
     * 获取 工贸名称。
     */
    public String getGmName() {
        return this.gmName;
    }

    /**
     * 设置 工贸名称。
     *
     * @param value 属性值
     */
    public void setGmName(String value) {
        this.gmName = value;
    }

    private String wlqyName;

    /**
     * 获取 物流区域名称。
     */
    public String getWlqyName() {
        return this.wlqyName;
    }

    /**
     * 设置 物流区域名称。
     *
     * @param value 属性值
     */
    public void setWlqyName(String value) {
        this.wlqyName = value;
    }

    private String qyName;

    /**
     * 获取 订单区域名称。
     */
    public String getQyName() {
        return this.qyName;
    }

    /**
     * 设置 订单区域名称。
     *
     * @param value 属性值
     */
    public void setQyName(String value) {
        this.qyName = value;
    }
}
