package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

public class PopInvWarehouse implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3431631072818009195L;
    private String id;

    private String whCode;

    private String whName;

    private Integer status;

    private String centerCode;

    private Byte supportCod;

    private String supportedDeliveryMode;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String lesFiveYard;

    private String lesWhCode;

    private String lesAccepter;

    private String rrsDeliverCode;

    private String crmAreaCode;

    private String ehaierPayer;

    private String itcCode;

    private String moCode;

    private String estorgeId;

    private String estorgeName;

    private String transmitId;

    private String transmitDesc;

    private String lesOeId;

    private String customId;

    private String customDesc;

    private String industryTradeId;

    private String industryTradeDesc;

    private String graininessId;

    private String netManagementId;

    private String esaleId;

    private String esaleName;

    private String saleOrgId;

    private String branch;

    private String paymentId;

    private String paymentName;

    private String areaId;

    private String rrsDistributionId;

    private String rrsDistributionName;

    private String rrsSaleId;

    private String rrsSaleName;

    private String omsRrsPaymentId;

    private String omsRrsPaymentName;

    private String sapCustomerCode;

    private String sapCustomerName;

    public String getWhCode() {
        return whCode;
    }

    public void setWhCode(String whCode) {
        this.whCode = whCode == null ? null : whCode.trim();
    }

    public String getWhName() {
        return whName;
    }

    public void setWhName(String whName) {
        this.whName = whName == null ? null : whName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCenterCode() {
        return centerCode;
    }

    public void setCenterCode(String centerCode) {
        this.centerCode = centerCode == null ? null : centerCode.trim();
    }

    public Byte getSupportCod() {
        return supportCod;
    }

    public void setSupportCod(Byte supportCod) {
        this.supportCod = supportCod;
    }

    public String getSupportedDeliveryMode() {
        return supportedDeliveryMode;
    }

    public void setSupportedDeliveryMode(String supportedDeliveryMode) {
        this.supportedDeliveryMode = supportedDeliveryMode == null ? null : supportedDeliveryMode.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getLesFiveYard() {
        return lesFiveYard;
    }

    public void setLesFiveYard(String lesFiveYard) {
        this.lesFiveYard = lesFiveYard == null ? null : lesFiveYard.trim();
    }

    public String getLesWhCode() {
        return lesWhCode;
    }

    public void setLesWhCode(String lesWhCode) {
        this.lesWhCode = lesWhCode == null ? null : lesWhCode.trim();
    }

    public String getLesAccepter() {
        return lesAccepter;
    }

    public void setLesAccepter(String lesAccepter) {
        this.lesAccepter = lesAccepter == null ? null : lesAccepter.trim();
    }

    public String getRrsDeliverCode() {
        return rrsDeliverCode;
    }

    public void setRrsDeliverCode(String rrsDeliverCode) {
        this.rrsDeliverCode = rrsDeliverCode == null ? null : rrsDeliverCode.trim();
    }

    public String getCrmAreaCode() {
        return crmAreaCode;
    }

    public void setCrmAreaCode(String crmAreaCode) {
        this.crmAreaCode = crmAreaCode == null ? null : crmAreaCode.trim();
    }

    public String getEhaierPayer() {
        return ehaierPayer;
    }

    public void setEhaierPayer(String ehaierPayer) {
        this.ehaierPayer = ehaierPayer == null ? null : ehaierPayer.trim();
    }

    public String getItcCode() {
        return itcCode;
    }

    public void setItcCode(String itcCode) {
        this.itcCode = itcCode == null ? null : itcCode.trim();
    }

    public String getMoCode() {
        return moCode;
    }

    public void setMoCode(String moCode) {
        this.moCode = moCode == null ? null : moCode.trim();
    }

    public String getEstorgeId() {
        return estorgeId;
    }

    public void setEstorgeId(String estorgeId) {
        this.estorgeId = estorgeId == null ? null : estorgeId.trim();
    }

    public String getEstorgeName() {
        return estorgeName;
    }

    public void setEstorgeName(String estorgeName) {
        this.estorgeName = estorgeName == null ? null : estorgeName.trim();
    }

    public String getTransmitId() {
        return transmitId;
    }

    public void setTransmitId(String transmitId) {
        this.transmitId = transmitId == null ? null : transmitId.trim();
    }

    public String getTransmitDesc() {
        return transmitDesc;
    }

    public void setTransmitDesc(String transmitDesc) {
        this.transmitDesc = transmitDesc == null ? null : transmitDesc.trim();
    }

    public String getLesOeId() {
        return lesOeId;
    }

    public void setLesOeId(String lesOeId) {
        this.lesOeId = lesOeId == null ? null : lesOeId.trim();
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId == null ? null : customId.trim();
    }

    public String getCustomDesc() {
        return customDesc;
    }

    public void setCustomDesc(String customDesc) {
        this.customDesc = customDesc == null ? null : customDesc.trim();
    }

    public String getIndustryTradeId() {
        return industryTradeId;
    }

    public void setIndustryTradeId(String industryTradeId) {
        this.industryTradeId = industryTradeId == null ? null : industryTradeId.trim();
    }

    public String getIndustryTradeDesc() {
        return industryTradeDesc;
    }

    public void setIndustryTradeDesc(String industryTradeDesc) {
        this.industryTradeDesc = industryTradeDesc == null ? null : industryTradeDesc.trim();
    }

    public String getGraininessId() {
        return graininessId;
    }

    public void setGraininessId(String graininessId) {
        this.graininessId = graininessId == null ? null : graininessId.trim();
    }

    public String getNetManagementId() {
        return netManagementId;
    }

    public void setNetManagementId(String netManagementId) {
        this.netManagementId = netManagementId == null ? null : netManagementId.trim();
    }

    public String getEsaleId() {
        return esaleId;
    }

    public void setEsaleId(String esaleId) {
        this.esaleId = esaleId == null ? null : esaleId.trim();
    }

    public String getEsaleName() {
        return esaleName;
    }

    public void setEsaleName(String esaleName) {
        this.esaleName = esaleName == null ? null : esaleName.trim();
    }

    public String getSaleOrgId() {
        return saleOrgId;
    }

    public void setSaleOrgId(String saleOrgId) {
        this.saleOrgId = saleOrgId == null ? null : saleOrgId.trim();
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch == null ? null : branch.trim();
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId == null ? null : paymentId.trim();
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName == null ? null : paymentName.trim();
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId == null ? null : areaId.trim();
    }

    public String getRrsDistributionId() {
        return rrsDistributionId;
    }

    public void setRrsDistributionId(String rrsDistributionId) {
        this.rrsDistributionId = rrsDistributionId == null ? null : rrsDistributionId.trim();
    }

    public String getRrsDistributionName() {
        return rrsDistributionName;
    }

    public void setRrsDistributionName(String rrsDistributionName) {
        this.rrsDistributionName = rrsDistributionName == null ? null : rrsDistributionName.trim();
    }

    public String getRrsSaleId() {
        return rrsSaleId;
    }

    public void setRrsSaleId(String rrsSaleId) {
        this.rrsSaleId = rrsSaleId == null ? null : rrsSaleId.trim();
    }

    public String getRrsSaleName() {
        return rrsSaleName;
    }

    public void setRrsSaleName(String rrsSaleName) {
        this.rrsSaleName = rrsSaleName == null ? null : rrsSaleName.trim();
    }

    public String getOmsRrsPaymentId() {
        return omsRrsPaymentId;
    }

    public void setOmsRrsPaymentId(String omsRrsPaymentId) {
        this.omsRrsPaymentId = omsRrsPaymentId == null ? null : omsRrsPaymentId.trim();
    }

    public String getOmsRrsPaymentName() {
        return omsRrsPaymentName;
    }

    public void setOmsRrsPaymentName(String omsRrsPaymentName) {
        this.omsRrsPaymentName = omsRrsPaymentName == null ? null : omsRrsPaymentName.trim();
    }

    public String getSapCustomerCode() {
        return sapCustomerCode;
    }

    public void setSapCustomerCode(String sapCustomerCode) {
        this.sapCustomerCode = sapCustomerCode == null ? null : sapCustomerCode.trim();
    }

    public String getSapCustomerName() {
        return sapCustomerName;
    }

    public void setSapCustomerName(String sapCustomerName) {
        this.sapCustomerName = sapCustomerName == null ? null : sapCustomerName.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}