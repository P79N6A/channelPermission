package com.haier.distribute.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PopOrderProducts implements Serializable {
	private static final long serialVersionUID = 5595622765093487568L;

	private Long id;

    private Integer siteId;

    private Boolean isTest;

    private Boolean hasRead;

    private Boolean supportOneDayLimit;

    private Integer orderId;

    private String cOrderSn;
    private String exportCorderSn;

    private Boolean isBook;

    private Short cPaymentStatus;

    private String cPayTime;
    private String payTimeEnd;

    private Integer productType;

    private Integer productId;

    private String productName;

    private String sku;

    private BigDecimal price;

    private Short number;

    private Integer lockedNumber;

    private Integer unlockedNumber;

    private BigDecimal productAmount;

    private BigDecimal balanceAmount;

    private BigDecimal couponAmount;

    private BigDecimal esAmount;

    private Integer giftCardNumberId;

    private BigDecimal usedGiftCardAmount;

    private Integer couponLogId;

    private BigDecimal activityPrice;

    private Integer activityId;

    private Integer cateId;

    private Integer brandId;
    private String brandName;

    private Integer netPointId;

    private BigDecimal shippingFee;

    private Boolean settlementStatus;

    private Integer receiptOrRejectTime;

    private Boolean isWmsSku;

    private String sCode;

    private String tsCode;

    private Integer tsShippingTime;
    
    private BigDecimal pAmount;
    private String orderStatus;
    private String consignee;
    private String mobile;
    private String originRegionName;
    private String originAddress;
    private String col01;
    private String col02;
    private String col03;
    private String col04;
    private String col05;
    private String col06;
    private String col07;
    private String col08;
    private String col09;
    private String col10;
    private String col11;
    private String col12;
    private String col13;
    private String col14;
    private String col15;
    private String col16;
    private String col17;
    private String col18;
    private String col19;
    private String col20;
    private String col21;
    public String getCol21() {
		return col21;
	}

	public void setCol21(String col21) {
		this.col21 = col21;
	}

	public String getCol11() {
		return col11;
	}

	public void setCol11(String col11) {
		this.col11 = col11;
	}

	public String getCol12() {
		return col12;
	}

	public void setCol12(String col12) {
		this.col12 = col12;
	}

	public String getCol13() {
		return col13;
	}

	public void setCol13(String col13) {
		this.col13 = col13;
	}

	public String getCol14() {
		return col14;
	}

	public void setCol14(String col14) {
		this.col14 = col14;
	}

	public String getCol15() {
		return col15;
	}

	public void setCol15(String col15) {
		this.col15 = col15;
	}

	public String getCol16() {
		return col16;
	}

	public void setCol16(String col16) {
		this.col16 = col16;
	}

	public String getCol17() {
		return col17;
	}

	public void setCol17(String col17) {
		this.col17 = col17;
	}

	public String getCol18() {
		return col18;
	}

	public void setCol18(String col18) {
		this.col18 = col18;
	}

	public String getCol19() {
		return col19;
	}

	public void setCol19(String col19) {
		this.col19 = col19;
	}

	public String getCol20() {
		return col20;
	}

	public void setCol20(String col20) {
		this.col20 = col20;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public String getCol01() {
		return col01;
	}

	public void setCol01(String col01) {
		this.col01 = col01;
	}

	public String getCol02() {
		return col02;
	}

	public void setCol02(String col02) {
		this.col02 = col02;
	}

	public String getCol03() {
		return col03;
	}

	public void setCol03(String col03) {
		this.col03 = col03;
	}

	public String getCol04() {
		return col04;
	}

	public void setCol04(String col04) {
		this.col04 = col04;
	}

	public String getCol05() {
		return col05;
	}

	public void setCol05(String col05) {
		this.col05 = col05;
	}

	public String getCol06() {
		return col06;
	}

	public void setCol06(String col06) {
		this.col06 = col06;
	}

	public String getCol07() {
		return col07;
	}

	public void setCol07(String col07) {
		this.col07 = col07;
	}

	public String getCol08() {
		return col08;
	}

	public void setCol08(String col08) {
		this.col08 = col08;
	}

	public String getCol09() {
		return col09;
	}

	public void setCol09(String col09) {
		this.col09 = col09;
	}

	public String getCol10() {
		return col10;
	}

	public void setCol10(String col10) {
		this.col10 = col10;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Boolean getIsTest() {
		return isTest;
	}

	public void setIsTest(Boolean isTest) {
		this.isTest = isTest;
	}

	public Boolean getHasRead() {
		return hasRead;
	}

	public void setHasRead(Boolean hasRead) {
		this.hasRead = hasRead;
	}

	public Boolean getSupportOneDayLimit() {
		return supportOneDayLimit;
	}

	public void setSupportOneDayLimit(Boolean supportOneDayLimit) {
		this.supportOneDayLimit = supportOneDayLimit;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getcOrderSn() {
		return cOrderSn;
	}

	public void setcOrderSn(String cOrderSn) {
		this.cOrderSn = cOrderSn;
	}

	public Boolean getIsBook() {
		return isBook;
	}

	public void setIsBook(Boolean isBook) {
		this.isBook = isBook;
	}

	public Short getcPaymentStatus() {
		return cPaymentStatus;
	}

	public void setcPaymentStatus(Short cPaymentStatus) {
		this.cPaymentStatus = cPaymentStatus;
	}

	public String getcPayTime() {
		return cPayTime;
	}

	public void setcPayTime(String cPayTime) {
		this.cPayTime = cPayTime;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getLockedNumber() {
		return lockedNumber;
	}

	public void setLockedNumber(Integer lockedNumber) {
		this.lockedNumber = lockedNumber;
	}

	public Integer getUnlockedNumber() {
		return unlockedNumber;
	}

	public void setUnlockedNumber(Integer unlockedNumber) {
		this.unlockedNumber = unlockedNumber;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	public BigDecimal getEsAmount() {
		return esAmount;
	}

	public void setEsAmount(BigDecimal esAmount) {
		this.esAmount = esAmount;
	}

	public Integer getGiftCardNumberId() {
		return giftCardNumberId;
	}

	public void setGiftCardNumberId(Integer giftCardNumberId) {
		this.giftCardNumberId = giftCardNumberId;
	}

	public BigDecimal getUsedGiftCardAmount() {
		return usedGiftCardAmount;
	}

	public void setUsedGiftCardAmount(BigDecimal usedGiftCardAmount) {
		this.usedGiftCardAmount = usedGiftCardAmount;
	}

	public Integer getCouponLogId() {
		return couponLogId;
	}

	public void setCouponLogId(Integer couponLogId) {
		this.couponLogId = couponLogId;
	}

	public BigDecimal getActivityPrice() {
		return activityPrice;
	}

	public void setActivityPrice(BigDecimal activityPrice) {
		this.activityPrice = activityPrice;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getNetPointId() {
		return netPointId;
	}

	public void setNetPointId(Integer netPointId) {
		this.netPointId = netPointId;
	}

	public BigDecimal getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(BigDecimal shippingFee) {
		this.shippingFee = shippingFee;
	}

	public Boolean getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(Boolean settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public Integer getReceiptOrRejectTime() {
		return receiptOrRejectTime;
	}

	public void setReceiptOrRejectTime(Integer receiptOrRejectTime) {
		this.receiptOrRejectTime = receiptOrRejectTime;
	}

	public Boolean getIsWmsSku() {
		return isWmsSku;
	}

	public void setIsWmsSku(Boolean isWmsSku) {
		this.isWmsSku = isWmsSku;
	}

	public String getsCode() {
		return sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public String getTsCode() {
		return tsCode;
	}

	public void setTsCode(String tsCode) {
		this.tsCode = tsCode;
	}

	public Integer getTsShippingTime() {
		return tsShippingTime;
	}

	public void setTsShippingTime(Integer tsShippingTime) {
		this.tsShippingTime = tsShippingTime;
	}

	public String getProductSn() {
		return productSn;
	}

	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getInvoiceExpressNumber() {
		return invoiceExpressNumber;
	}

	public void setInvoiceExpressNumber(String invoiceExpressNumber) {
		this.invoiceExpressNumber = invoiceExpressNumber;
	}

	public String getPostMan() {
		return postMan;
	}

	public void setPostMan(String postMan) {
		this.postMan = postMan;
	}

	public String getPostManPhone() {
		return postManPhone;
	}

	public void setPostManPhone(String postManPhone) {
		this.postManPhone = postManPhone;
	}

	public Short getIsNotice() {
		return isNotice;
	}

	public void setIsNotice(Short isNotice) {
		this.isNotice = isNotice;
	}

	public Short getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(Short noticeType) {
		this.noticeType = noticeType;
	}

	public String getNoticeRemark() {
		return noticeRemark;
	}

	public void setNoticeRemark(String noticeRemark) {
		this.noticeRemark = noticeRemark;
	}

	public String getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(String noticeTime) {
		this.noticeTime = noticeTime;
	}

	public Integer getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(Integer shippingTime) {
		this.shippingTime = shippingTime;
	}

	public String getLessOrderSn() {
		return lessOrderSn;
	}

	public void setLessOrderSn(String lessOrderSn) {
		this.lessOrderSn = lessOrderSn;
	}

	public Boolean getWaitGetLesShippingInfo() {
		return waitGetLesShippingInfo;
	}

	public void setWaitGetLesShippingInfo(Boolean waitGetLesShippingInfo) {
		this.waitGetLesShippingInfo = waitGetLesShippingInfo;
	}

	public Integer getGetLesShippingCount() {
		return getLesShippingCount;
	}

	public void setGetLesShippingCount(Integer getLesShippingCount) {
		this.getLesShippingCount = getLesShippingCount;
	}

	public Integer getLessShipTime() {
		return lessShipTime;
	}

	public void setLessShipTime(Integer lessShipTime) {
		this.lessShipTime = lessShipTime;
	}

	public Integer getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Integer closeTime) {
		this.closeTime = closeTime;
	}

	public Integer getIsReceipt() {
		return isReceipt;
	}

	public void setIsReceipt(Integer isReceipt) {
		this.isReceipt = isReceipt;
	}

	public Integer getIsMakeReceipt() {
		return isMakeReceipt;
	}

	public void setIsMakeReceipt(Integer isMakeReceipt) {
		this.isMakeReceipt = isMakeReceipt;
	}

	public String getReceiptAddTime() {
		return receiptAddTime;
	}

	public void setReceiptAddTime(String receiptAddTime) {
		this.receiptAddTime = receiptAddTime;
	}

	public Byte getMakeReceiptType() {
		return makeReceiptType;
	}

	public void setMakeReceiptType(Byte makeReceiptType) {
		this.makeReceiptType = makeReceiptType;
	}

	public String getShippingMode() {
		return shippingMode;
	}

	public void setShippingMode(String shippingMode) {
		this.shippingMode = shippingMode;
	}

	public Integer getLastTimeForShippingMode() {
		return lastTimeForShippingMode;
	}

	public void setLastTimeForShippingMode(Integer lastTimeForShippingMode) {
		this.lastTimeForShippingMode = lastTimeForShippingMode;
	}

	public String getLastEditorForShippingMode() {
		return lastEditorForShippingMode;
	}

	public void setLastEditorForShippingMode(String lastEditorForShippingMode) {
		this.lastEditorForShippingMode = lastEditorForShippingMode;
	}

	public Integer getTongshuaiWorkId() {
		return tongshuaiWorkId;
	}

	public void setTongshuaiWorkId(Integer tongshuaiWorkId) {
		this.tongshuaiWorkId = tongshuaiWorkId;
	}

	public Integer getOrderPromotionId() {
		return orderPromotionId;
	}

	public void setOrderPromotionId(Integer orderPromotionId) {
		this.orderPromotionId = orderPromotionId;
	}

	public BigDecimal getOrderPromotionAmount() {
		return orderPromotionAmount;
	}

	public void setOrderPromotionAmount(BigDecimal orderPromotionAmount) {
		this.orderPromotionAmount = orderPromotionAmount;
	}

	public Integer getExternalSaleSettingId() {
		return externalSaleSettingId;
	}

	public void setExternalSaleSettingId(Integer externalSaleSettingId) {
		this.externalSaleSettingId = externalSaleSettingId;
	}

	public Integer getRecommendationId() {
		return recommendationId;
	}

	public void setRecommendationId(Integer recommendationId) {
		this.recommendationId = recommendationId;
	}

	public Boolean getHasSendAlertNum() {
		return hasSendAlertNum;
	}

	public void setHasSendAlertNum(Boolean hasSendAlertNum) {
		this.hasSendAlertNum = hasSendAlertNum;
	}

	public Boolean getIsNoLimitStockProduct() {
		return isNoLimitStockProduct;
	}

	public void setIsNoLimitStockProduct(Boolean isNoLimitStockProduct) {
		this.isNoLimitStockProduct = isNoLimitStockProduct;
	}

	public Integer getHpRegisterDate() {
		return hpRegisterDate;
	}

	public void setHpRegisterDate(Integer hpRegisterDate) {
		this.hpRegisterDate = hpRegisterDate;
	}

	public Integer getHpFailDate() {
		return hpFailDate;
	}

	public void setHpFailDate(Integer hpFailDate) {
		this.hpFailDate = hpFailDate;
	}

	public Integer getHpFinishDate() {
		return hpFinishDate;
	}

	public void setHpFinishDate(Integer hpFinishDate) {
		this.hpFinishDate = hpFinishDate;
	}

	public Integer getHpReservationDate() {
		return hpReservationDate;
	}

	public void setHpReservationDate(Integer hpReservationDate) {
		this.hpReservationDate = hpReservationDate;
	}

	public Byte getShippingOpporunity() {
		return shippingOpporunity;
	}

	public void setShippingOpporunity(Byte shippingOpporunity) {
		this.shippingOpporunity = shippingOpporunity;
	}

	public Byte getIsTimeoutFree() {
		return isTimeoutFree;
	}

	public void setIsTimeoutFree(Byte isTimeoutFree) {
		this.isTimeoutFree = isTimeoutFree;
	}

	public BigDecimal getItemShareAmount() {
		return itemShareAmount;
	}

	public void setItemShareAmount(BigDecimal itemShareAmount) {
		this.itemShareAmount = itemShareAmount;
	}

	public Integer getLessShipTInTime() {
		return lessShipTInTime;
	}

	public void setLessShipTInTime(Integer lessShipTInTime) {
		this.lessShipTInTime = lessShipTInTime;
	}

	public String getCbsSecCode() {
		return cbsSecCode;
	}

	public void setCbsSecCode(String cbsSecCode) {
		this.cbsSecCode = cbsSecCode;
	}

	public Byte getSplitFlag() {
		return splitFlag;
	}

	public void setSplitFlag(Byte splitFlag) {
		this.splitFlag = splitFlag;
	}

	public String getSplitRelateCOrderSn() {
		return splitRelateCOrderSn;
	}

	public void setSplitRelateCOrderSn(String splitRelateCOrderSn) {
		this.splitRelateCOrderSn = splitRelateCOrderSn;
	}

	public Byte getChannelId() {
		return channelId;
	}

	public void setChannelId(Byte channelId) {
		this.channelId = channelId;
	}

	public Integer getActivityId2() {
		return activityId2;
	}

	public void setActivityId2(Integer activityId2) {
		this.activityId2 = activityId2;
	}

	public Integer getPdOrderStatus() {
		return pdOrderStatus;
	}

	public void setPdOrderStatus(Integer pdOrderStatus) {
		this.pdOrderStatus = pdOrderStatus;
	}

	public String getOmsOrderSn() {
		return omsOrderSn;
	}

	public void setOmsOrderSn(String omsOrderSn) {
		this.omsOrderSn = omsOrderSn;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public BigDecimal getCouponCodeValue() {
		return couponCodeValue;
	}

	public void setCouponCodeValue(BigDecimal couponCodeValue) {
		this.couponCodeValue = couponCodeValue;
	}

	public BigDecimal getJfbAmoun() {
		return jfbAmoun;
	}

	public void setJfbAmoun(BigDecimal jfbAmoun) {
		this.jfbAmoun = jfbAmoun;
	}

	public BigDecimal getDjAmount() {
		return djAmount;
	}

	public void setDjAmount(BigDecimal djAmount) {
		this.djAmount = djAmount;
	}

	public BigDecimal getHbAmount() {
		return hbAmount;
	}

	public void setHbAmount(BigDecimal hbAmount) {
		this.hbAmount = hbAmount;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public Byte getStoreType() {
		return storeType;
	}

	public void setStoreType(Byte storeType) {
		this.storeType = storeType;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public Byte getO2oType() {
		return o2oType;
	}

	public void setO2oType(Byte o2oType) {
		this.o2oType = o2oType;
	}

	public String getBrokerageType() {
		return brokerageType;
	}

	public void setBrokerageType(String brokerageType) {
		this.brokerageType = brokerageType;
	}

	public String getReceiptNum() {
		return receiptNum;
	}

	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}

	public String getSystemRemark() {
		return systemRemark;
	}

	public void setSystemRemark(String systemRemark) {
		this.systemRemark = systemRemark;
	}

	private Short status;

    private String productSn;

    private String invoiceNumber;

    private String expressName;

    private String invoiceExpressNumber;

    private String postMan;

    private String postManPhone;

    private Short isNotice;

    private Short noticeType;

    private String noticeRemark;

    private String noticeTime;

    private Integer shippingTime;

    private String lessOrderSn;

    private Boolean waitGetLesShippingInfo;

    private Integer getLesShippingCount;

    private String outping;

    private Integer lessShipTime;

    private Integer closeTime;

    private Integer isReceipt;

    private Integer isMakeReceipt;
    //TODO
    private String receiptAddTime;

    private Byte makeReceiptType;

    private String shippingMode;

    private Integer lastTimeForShippingMode;

    private String lastEditorForShippingMode;
    //TODO
    private Integer tongshuaiWorkId;

    private Integer orderPromotionId;

    private BigDecimal orderPromotionAmount;

    private Integer externalSaleSettingId;

    private Integer recommendationId;

    private Boolean hasSendAlertNum;

    private Boolean isNoLimitStockProduct;

    private Integer hpRegisterDate;

    private Integer hpFailDate;

    private Integer hpFinishDate;

    private Integer hpReservationDate;

    private Byte shippingOpporunity;

    private Byte isTimeoutFree;

    private BigDecimal itemShareAmount;

    private Integer lessShipTInTime;

    private Integer lessShipTOutTime;
    //TODO
    private String cbsSecCode;

    private Integer points;

    private Date modified;

    private Byte splitFlag;

    private String splitRelateCOrderSn;

    private Byte channelId;

    private Integer activityId2;

    private Integer pdOrderStatus;

    private String omsOrderSn;

    private String couponCode;

    private BigDecimal couponCodeValue;

    private BigDecimal jfbAmoun;

    private BigDecimal djAmount;

    private BigDecimal hbAmount;

    private Integer storeId;

    private Byte storeType;

    private String stockType;

    private Byte o2oType;

    private String brokerageType;   

    private String oid;
    private String receiptNum;

    private String systemRemark;
    /**
     * Long型的子订单付款时间
     */
    private Long cPayTimeAdd;
    private Long cPayTimeEnd;
//    /**
//     * order表实体类
//     */
//    private OrderDTO orderDTO;
    
    private String orderSn;
    private String exportOrderSn;
    private String sourceOrderSn;
    private String iscoddisplay;
    private Integer iscod;
    public String invoicetype = "普通发票";
    private String channelName;
    private String sellPeople;
    public String getIscoddisplay() {
		return iscoddisplay;
	}

	public void setIscoddisplay(String iscoddisplay) {
		this.iscoddisplay = iscoddisplay;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getOrderamount() {
		return orderamount;
	}

	public void setOrderamount(String orderamount) {
		this.orderamount = orderamount;
	}

	public String getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}

	private String source;
//    private String invoicetype;
    private String orderamount;
    private String ordertype;
    private String shipTime;
//    private String noticeTime;
    private String typeName;

    

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Short getNumber() {
        return number;
    }

    public void setNumber(Short number) {
        this.number = number;
    }

    
    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    

    public String getOutping() {
        return outping;
    }

    public void setOutping(String outping) {
        this.outping = outping == null ? null : outping.trim();
    }

    

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

	public String operation = null;

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Long getcPayTimeAdd() {
		return cPayTimeAdd;
	}

	public void setcPayTimeAdd(Long cPayTimeAdd) {
		this.cPayTimeAdd = cPayTimeAdd;
	}

	public Integer getIscod() {
		return iscod;
	}

	public void setIscod(Integer iscod) {
		this.iscod = iscod;
	}

	public String getShipTime() {
		return shipTime;
	}

	public void setShipTime(String shipTime) {
		this.shipTime = shipTime;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getExportOrderSn() {
		return exportOrderSn;
	}

	public void setExportOrderSn(String exportOrderSn) {
		this.exportOrderSn = exportOrderSn;
	}

	public String getExportCorderSn() {
		return exportCorderSn;
	}

	public void setExportCorderSn(String exportCorderSn) {
		this.exportCorderSn = exportCorderSn;
	}

	public String getPayTimeEnd() {
		return payTimeEnd;
	}

	public void setPayTimeEnd(String payTimeEnd) {
		this.payTimeEnd = payTimeEnd;
	}

	public Long getcPayTimeEnd() {
		return cPayTimeEnd;
	}

	public void setcPayTimeEnd(Long cPayTimeEnd) {
		this.cPayTimeEnd = cPayTimeEnd;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public BigDecimal getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(BigDecimal productAmount) {
		this.productAmount = productAmount;
	}

	public Integer getLessShipTOutTime() {
		return lessShipTOutTime;
	}

	public void setLessShipTOutTime(Integer lessShipTOutTime) {
		this.lessShipTOutTime = lessShipTOutTime;
	}

	public String getSourceOrderSn() {
		return sourceOrderSn;
	}

	public void setSourceOrderSn(String sourceOrderSn) {
		this.sourceOrderSn = sourceOrderSn;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSellPeople() {
		return sellPeople;
	}

	public void setSellPeople(String sellPeople) {
		this.sellPeople = sellPeople;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public BigDecimal getpAmount() {
		return pAmount;
	}

	public void setpAmount(BigDecimal pAmount) {
		this.pAmount = pAmount;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOriginAddress() {
		return originAddress;
	}

	public void setOriginAddress(String originAddress) {
		this.originAddress = originAddress;
	}

	public String getOriginRegionName() {
		return originRegionName;
	}

	public void setOriginRegionName(String originRegionName) {
		this.originRegionName = originRegionName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}