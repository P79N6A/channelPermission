package com.haier.shop.model;

/**
 * @Author:JinXueqian
 * @Date: 2018/7/25 14:30
 */
public class StoragesVo extends Storages {

    /**
     * 所属中心(城市ID)对应的城市名称
     */
    private String cityName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
