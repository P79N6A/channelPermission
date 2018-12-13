package com.haier.shop.model;

import com.haier.shop.util.excel.Excel;
import com.haier.shop.util.excel.ExcelTitle;

import java.io.Serializable;
import java.math.BigDecimal;

@Excel(filename = "闸口价信息导出")
public class GatePrice implements Serializable {

    private static final long serialVersionUID = 1132873360654689659L;

    public String getBigChannel() {
        return bigChannel;
    }

    public void setBigChannel(String bigChannel) {
        this.bigChannel = bigChannel;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCateGory() {
        return cateGory;
    }

    public void setCateGory(String cateGory) {
        this.cateGory = cateGory;
    }

    public String getLogisticsModel() {
        return logisticsModel;
    }

    public void setLogisticsModel(String logisticsModel) {
        this.logisticsModel = logisticsModel;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFrozenFlag() {
        return frozenFlag;
    }

    public void setFrozenFlag(String frozenFlag) {
        this.frozenFlag = frozenFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditBy() {
        return auditBy;
    }

    public void setAuditBy(String auditBy) {
        this.auditBy = auditBy;
    }

    public String getAuditIp() {
        return auditIp;
    }

    public void setAuditIp(String auditIp) {
        this.auditIp = auditIp;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getTempBeginTime() {
        return tempBeginTime;
    }

    public void setTempBeginTime(String tempBeginTime) {
        this.tempBeginTime = tempBeginTime;
    }

    public String getTempEndTime() {
        return tempEndTime;
    }

    public void setTempEndTime(String tempEndTime) {
        this.tempEndTime = tempEndTime;
    }

    public BigDecimal getGatePrice() {
        return gatePrice;
    }

    public void setGatePrice(BigDecimal gatePrice) {
        this.gatePrice = gatePrice;
    }

    public BigDecimal getPurPrice() {
        return purPrice;
    }

    public void setPurPrice(BigDecimal purPrice) {
        this.purPrice = purPrice;
    }

    public BigDecimal getCut() {
        return cut;
    }

    public void setCut(BigDecimal cut) {
        this.cut = cut;
    }

    public BigDecimal getBack() {
        return back;
    }

    public void setBack(BigDecimal back) {
        this.back = back;
    }

    public BigDecimal getChannelRate() {
        return channelRate;
    }

    public void setChannelRate(BigDecimal channelRate) {
        this.channelRate = channelRate;
    }

    public BigDecimal getBarePrice() {
        return barePrice;
    }

    public BigDecimal getTempGatePrice() {
        return tempGatePrice;
    }

    public void setTempGatePrice(BigDecimal tempGatePrice) {
        this.tempGatePrice = tempGatePrice;
    }

    public BigDecimal getNormalGrossprofit() {
        return normalGrossprofit;
    }

    public void setNormalGrossprofit(BigDecimal normalGrossprofit) {
        this.normalGrossprofit = normalGrossprofit;
    }

    public String getLowerStatus() {
        return lowerStatus;
    }

    public void setLowerStatus(String lowerStatus) {
        this.lowerStatus = lowerStatus;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public BigDecimal getActualGrossprofit() {
        return actualGrossprofit;
    }
    public void setBarePrice(BigDecimal barePrice) {
        this.barePrice = barePrice;
    }

    public String getIsBigBarePrice() {
        return isBigBarePrice;
    }

    public void setIsBigBarePrice(String isBigBarePrice) {
        this.isBigBarePrice = isBigBarePrice;
    }
    public void setActualGrossprofit(BigDecimal actualGrossprofit) {
        this.actualGrossprofit = actualGrossprofit;
    }

    public String getExecDays() {
        return execDays;
    }

    public void setExecDays(String execDays) {
        this.execDays = execDays;
    }
    @ExcelTitle(titleName = "品类", importIndex = 1)
    private String cateGory;
    @ExcelTitle(titleName = "物流模式", importIndex = 8)
    private String logisticsModel;
    @ExcelTitle(titleName = "型号", importIndex = 3)
    private String version;
    @ExcelTitle(titleName = "冻结状态")
    private String frozenFlag;
    private String id;
    @ExcelTitle(titleName = "是否有效")
    private String isValid;
    @ExcelTitle(titleName = "sku", importIndex = 2)
    private String sku;
    @ExcelTitle(titleName = "创建人")
    private String createBy;
    @ExcelTitle(titleName = "修改人")
    private String updateBy;
    private String ip;
    @ExcelTitle(titleName = "审核状态")
    private String auditStatus;
    @ExcelTitle(titleName = "审核人")
    private String auditBy;
    private String auditIp;
    @ExcelTitle(titleName = "开始时间")
    private String beginTime;
    @ExcelTitle(titleName = "结束时间")
    private String endTime;
    private String createTime;
    private String updateTime;
    @ExcelTitle(titleName = "审核时间")
    private String auditTime;
    @ExcelTitle(titleName = "临时闸口价开始时间", importIndex = 12)
    private String tempBeginTime;
    @ExcelTitle(titleName = "临时闸口价结束时间", importIndex = 13)
    private String tempEndTime;
    @ExcelTitle(titleName = "闸口价", importIndex = 10)
    private BigDecimal gatePrice;
    @ExcelTitle(titleName = "采购价", importIndex = 4)
    private BigDecimal purPrice;
    @ExcelTitle(titleName = "直扣", importIndex = 5)
    private BigDecimal cut;
    @ExcelTitle(titleName = "后返", importIndex = 6)
    private BigDecimal back;
    @ExcelTitle(titleName = "价值链比率", importIndex = 9)
    private BigDecimal channelRate;
    @ExcelTitle(titleName = "裸价")
    private BigDecimal barePrice;
    @ExcelTitle(titleName = "临时闸口价", importIndex = 11)
    private BigDecimal tempGatePrice;
    @ExcelTitle(titleName = "标准毛利")
    private BigDecimal normalGrossprofit;
    @ExcelTitle(titleName = "大渠道", importIndex = 7)
    private String bigChannel;
    @ExcelTitle(titleName = "品牌", importIndex = 0)
    private String brand;
    @ExcelTitle(titleName = "下市状态")
    private String lowerStatus;
    private String fileId;
    @ExcelTitle(titleName = "实际毛利")
    private BigDecimal actualGrossprofit;
    @ExcelTitle(titleName = "是否小于裸价")
    private String isBigBarePrice;
    @ExcelTitle(titleName = "执行天数")
    private String execDays;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    private String brandName;
    private String productName;
    private String cateName;
}
