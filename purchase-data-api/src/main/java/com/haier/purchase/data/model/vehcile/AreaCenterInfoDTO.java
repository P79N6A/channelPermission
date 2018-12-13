package com.haier.purchase.data.model.vehcile;

import java.util.Date;

public class AreaCenterInfoDTO extends BaseDTO {

    private static final long serialVersionUID = -8230659678984052662L;
    private String deliveryToCode; //送达方编码
    private String distributionCentreCode; //配送中心编码
    private String distributionCentreName; //配送中心名称
    private String warehouseCode; //配送中心编码
    private String warehouseName; //配送中心名称
    private String areaCode; //区域编码
    private String areaName; //区域名称
    private String rrsCenterCode; //日日顺中心编码
    private String rrsCenterName; //日日顺中心编码
    private String createBy; //


    public String getActiveFlagName() {
        return activeFlagName;
    }

    public void setActiveFlagName(String activeFlagName) {
        this.activeFlagName = activeFlagName;
    }

    private String activeFlagName;

    private String createTimeShow;

    public String getCreateTimeShow() {
        return createTimeShow;
    }

    public void setCreateTimeShow(String createTimeShow) {
        this.createTimeShow = createTimeShow;
    }

    public String getLastUpdateTimeShow() {
        return lastUpdateTimeShow;
    }

    public void setLastUpdateTimeShow(String lastUpdateTimeShow) {
        this.lastUpdateTimeShow = lastUpdateTimeShow;
    }

    private String lastUpdateTimeShow;
    private Date createTime; //
    private String lastUpdateBy; //
    private Date lastUpdateTime; //

    private String whCode;//仓库编码
    public String getDeliveryToCode() {
        return deliveryToCode;
    }

    public void setDeliveryToCode(String deliveryToCode) {
        this.deliveryToCode = deliveryToCode;
    }

    public String getDistributionCentreCode() {
        return distributionCentreCode;
    }

    public void setDistributionCentreCode(String distributionCentreCode) {
        this.distributionCentreCode = distributionCentreCode;
    }

    public String getDistributionCentreName() {
        return distributionCentreName;
    }

    public void setDistributionCentreName(String distributionCentreName) {
        this.distributionCentreName = distributionCentreName;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getRrsCenterCode() {
        return rrsCenterCode;
    }

    public void setRrsCenterCode(String rrsCenterCode) {
        this.rrsCenterCode = rrsCenterCode;
    }

    public String getRrsCenterName() {
        return rrsCenterName;
    }

    public void setRrsCenterName(String rrsCenterName) {
        this.rrsCenterName = rrsCenterName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

	public String getWhCode() {
		return whCode;
	}

	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
}


