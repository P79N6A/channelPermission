package com.haier.stock.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class SgStore implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 4479954853459810649L;
    private Integer           id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer ownerId;

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    private String storeName;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    private String storeCode;

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    private Integer storeType;

    public Integer getStoreType() {
        return storeType;
    }

    public void setStoreType(Integer storeType) {
        this.storeType = storeType;
    }

    private String miniRoot;

    public String getMiniRoot() {
        return miniRoot;
    }

    public void setMiniRoot(String miniRoot) {
        this.miniRoot = miniRoot;
    }

    private String distributionTime;

    public String getDistributionTime() {
        return distributionTime;
    }

    public void setDistributionTime(String distributionTime) {
        this.distributionTime = distributionTime;
    }

    private String licenseId;

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    private String licensePhote;

    public String getLicensePhote() {
        return licensePhote;
    }

    public void setLicensePhote(String licensePhote) {
        this.licensePhote = licensePhote;
    }

    private String openAccountBank;

    public String getOpenAccountBank() {
        return openAccountBank;
    }

    public void setOpenAccountBank(String openAccountBank) {
        this.openAccountBank = openAccountBank;
    }

    private String bankAccount;

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    private Integer isCod;

    public Integer getIsCod() {
        return isCod;
    }

    public void setIsCod(Integer isCod) {
        this.isCod = isCod;
    }

    private Integer isEc;

    public Integer getIsEc() {
        return isEc;
    }

    public void setIsEc(Integer isEc) {
        this.isEc = isEc;
    }

    private Integer isPriceChange;

    public Integer getIsPriceChange() {
        return isPriceChange;
    }

    public void setIsPriceChange(Integer isPriceChange) {
        this.isPriceChange = isPriceChange;
    }

    private BigDecimal platformFeeScale;

    public BigDecimal getPlatformFeeScale() {
        return platformFeeScale;
    }

    public void setPlatformFeeScale(BigDecimal platformFeeScale) {
        this.platformFeeScale = platformFeeScale;
    }

    private String addUser;

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    private Integer addTime;

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    private String modifyUser;

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    private Integer modifyTime;

    public Integer getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Integer modifyTime) {
        this.modifyTime = modifyTime;
    }

    private String auditUser;

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    private Integer auditTime;

    public Integer getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Integer auditTime) {
        this.auditTime = auditTime;
    }

    private String auditOpinion;

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    private Integer storeState;

    public Integer getStoreState() {
        return storeState;
    }

    public void setStoreState(Integer storeState) {
        this.storeState = storeState;
    }

    private Integer isWa;

    public Integer getIsWa() {
        return isWa;
    }

    public void setIsWa(Integer isWa) {
        this.isWa = isWa;
    }
}