package com.haier.purchase.data.model.vehcile;

import java.util.Date;

public class VehicleOrderZqDTO extends BaseDTO {

    private static final long serialVersionUID = -535741395084298169L;
    private Long zqOrderId; //主键自增ID
    private String zqOrderNo; //整车订单扣款编号
    private String orderNo; //整车订单采购编号关联
    private String type; //订单类型
    private String status; //订单状态
    private String corpCode; //分公司销售组织编码
    private String corpName; //分公司销售组织名称
    private String areaCode; //所属区域编码
    private String areaName; //所属区域名称
    private String jdCode; //生产基地
    private String jdName; //基地名称
    private String distributionCentre; //配送中心
    private String distributionCentreName; //配送中心名称
    private String soldToCode; //售达方
    private String soldToName; //送达方名称
    private String mgCustCode; //管理客户编码  送达方对应的B码
    private String deliveryCode; //送达方编码
    private String deliveryName; //送达方名称
    private String budgetOrg; //预算体
    private Long totalRebate; //总返利
    private Long amount; //总金额
    private Integer rows; //明细行数
    private double loadingVolume; //装车体积
    private String carType; //车型
    private String carTypeName; //车型名称
    private String isxg; //是否限高
    private String ispingcar; //是否拼车
    private double minVolume; //最小体积
    private double maxVolume; //最大体积
    private Date orderTime; //订单（开单）日期
    private Date dateOfArrival; //到货日期
    private String createBy; //创建人
    private Date createTime; //创建时间
    private String lastUpdateBy; //最后修改人
    private Date lastUpdateTime; //最后修改时间


    public Long getZqOrderId() {
        return zqOrderId;
    }

    public void setZqOrderId(Long zqOrderId) {
        this.zqOrderId = zqOrderId;
    }

    public String getZqOrderNo() {
        return zqOrderNo;
    }

    public void setZqOrderNo(String zqOrderNo) {
        this.zqOrderNo = zqOrderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
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

    public String getJdCode() {
        return jdCode;
    }

    public void setJdCode(String jdCode) {
        this.jdCode = jdCode;
    }

    public String getJdName() {
        return jdName;
    }

    public void setJdName(String jdName) {
        this.jdName = jdName;
    }

    public String getDistributionCentre() {
        return distributionCentre;
    }

    public void setDistributionCentre(String distributionCentre) {
        this.distributionCentre = distributionCentre;
    }

    public String getDistributionCentreName() {
        return distributionCentreName;
    }

    public void setDistributionCentreName(String distributionCentreName) {
        this.distributionCentreName = distributionCentreName;
    }

    public String getSoldToCode() {
        return soldToCode;
    }

    public void setSoldToCode(String soldToCode) {
        this.soldToCode = soldToCode;
    }

    public String getSoldToName() {
        return soldToName;
    }

    public void setSoldToName(String soldToName) {
        this.soldToName = soldToName;
    }

    public String getMgCustCode() {
        return mgCustCode;
    }

    public void setMgCustCode(String mgCustCode) {
        this.mgCustCode = mgCustCode;
    }

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(String deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getBudgetOrg() {
        return budgetOrg;
    }

    public void setBudgetOrg(String budgetOrg) {
        this.budgetOrg = budgetOrg;
    }

    public Long getTotalRebate() {
        return totalRebate;
    }

    public void setTotalRebate(Long totalRebate) {
        this.totalRebate = totalRebate;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public double getLoadingVolume() {
        return loadingVolume;
    }

    public void setLoadingVolume(double loadingVolume) {
        this.loadingVolume = loadingVolume;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getIsxg() {
        return isxg;
    }

    public void setIsxg(String isxg) {
        this.isxg = isxg;
    }

    public String getIspingcar() {
        return ispingcar;
    }

    public void setIspingcar(String ispingcar) {
        this.ispingcar = ispingcar;
    }

    public double getMinVolume() {
        return minVolume;
    }

    public void setMinVolume(double minVolume) {
        this.minVolume = minVolume;
    }

    public double getMaxVolume() {
        return maxVolume;
    }

    public void setMaxVolume(double maxVolume) {
        this.maxVolume = maxVolume;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Date getDateOfArrival() {
        return dateOfArrival;
    }

    public void setDateOfArrival(Date dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
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


}


