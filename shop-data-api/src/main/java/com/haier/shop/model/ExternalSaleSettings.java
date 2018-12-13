package com.haier.shop.model;

import java.io.Serializable;

/**
 * @author hanhaoyang@ehaier.com
 * @date 2018/7/12 16:10
 */
public class ExternalSaleSettings implements Serializable{
    private Integer id;

    private Integer siteId;

    private String settingName;

    private String externalSkus;

    private String productSpecs;

    private String configIds;

    private Integer addTime;

    private Integer type;

    private Integer	startTime;

    private Integer	endTime;

    private Integer	effect;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public String getExternalSkus() {
        return externalSkus;
    }

    public void setExternalSkus(String externalSkus) {
        this.externalSkus = externalSkus;
    }

    public String getProductSpecs() {
        return productSpecs;
    }

    public void setProductSpecs(String productSpecs) {
        this.productSpecs = productSpecs;
    }

    public String getConfigIds() {
        return configIds;
    }

    public void setConfigIds(String configIds) {
        this.configIds = configIds;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getEffect() {
        return effect;
    }

    public void setEffect(Integer effect) {
        this.effect = effect;
    }
}
