package com.haier.shop.model;

import java.io.Serializable;

public class COrderBean implements Serializable {

	private static final long serialVersionUID = 708087085114976373L;

	private Boolean success;

	/** 网单数据 */
	private Result result;

	private String message;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "COrderBean [success=" + success + ", result=" + result + ", message=" + message + "]";
	}

	/**
	 * @return the success
	 */
	public Boolean getSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	/**
	 * @return the result
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(Result result) {
		this.result = result;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public static class Result implements Serializable {
		private static final long serialVersionUID = -8531755876270005000L;
		private String id;
		private String siteId;
		private String isTest;
		private String hasRead;
		private String supportOneDayLimit;
		private String orderId;
		private String cOrderSn;
		private String isBook;
		private String cPaymentStatus;
		private String cPayTime;
		private String productType;
		private String productId;
		private String productName;
		private String sku;
		private String price;
		private String number;
		private String lockedNumber;
		private String unlockedNumber;
		private double productAmount;
		private double balanceAmount;
		private double couponAmount;
		private double esAmount;
		private String giftCardNumberId;
		private double usedGiftCardAmount;
		private String couponLogId;
		private double activityPrice;
		private String activityId;
		private String cateId;
		private String brandId;
		private String netPointId;
		private double shippingFee;
		private String settlementStatus;
		private String receiptOrRejectTime;
		private String isWmsSku;
		private String sCode;
		private String cbsSecCode;
		private String tsCode;
		private String status;
		private String productSn;
		private String invoiceNumber;
		private String expressName;
		private String invoiceExpressNumber;
		private String postMan;
		private String postManPhone;
		private String isNotice;
		private String noticeType;
		private String noticeRemark;
		private String noticeTime;
		private String shippingTime;
		private String lessOrderSn;
		private String waitGetLesShippingInfo;
		private String outping;
		private String lessShipTime;
		private String closeTime;
		private String isReceipt;
		private String isMakeReceipt;
		private String receiptNum;
		private String receiptAddTime;
		private String makeReceiptType;
		private String shippingMode;
		private String lastTimeForShippingMode;
		private String lastEditorForShippingMode;
		private String systemRemark;
		private String tongshuaiWorkId;
		private String orderPromotionId;
		private String orderPromotionAmount;
		private String externalSaleSettingId;
		private String recommendationId;
		private String hasSendAlertNum;
		private String isNoLimitStockProduct;
		private String shippingOpporunity;
		private String isTimeoutFree;
		private Orders orders;
		private String storeId;
		private String storeType;
		private String stockType;
		private String tbOrderSn;

		public String getTbOrderSn() {
			return tbOrderSn;
		}

		public void setTbOrderSn(String tbOrderSn) {
			this.tbOrderSn = tbOrderSn;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @Override public String toString() { return "Result [id=" + id +
		 * ", siteId=" + siteId + ", isTest=" + isTest + ", hasRead=" + hasRead
		 * + ", supportOneDayLimit=" + supportOneDayLimit + ", orderId=" +
		 * orderId + ", cOrderSn=" + cOrderSn + ", isBook=" + isBook +
		 * ", cPaymentStatus=" + cPaymentStatus + ", cPayTime=" + cPayTime +
		 * ", productType=" + productType + ", productId=" + productId +
		 * ", productName=" + productName + ", sku=" + sku + ", price=" + price
		 * + ", number=" + number + ", lockedNumber=" + lockedNumber +
		 * ", unlockedNumber=" + unlockedNumber + ", productAmount=" +
		 * productAmount + ", balanceAmount=" + balanceAmount +
		 * ", couponAmount=" + couponAmount + ", esAmount=" + esAmount +
		 * ", giftCardNumberId=" + giftCardNumberId + ", usedGiftCardAmount=" +
		 * usedGiftCardAmount + ", couponLogId=" + couponLogId +
		 * ", activityPrice=" + activityPrice + ", activityId=" + activityId +
		 * ", cateId=" + cateId + ", brandId=" + brandId + ", netPointId=" +
		 * netPointId + ", shippingFee=" + shippingFee + ", settlementStatus=" +
		 * settlementStatus + ", receiptOrRejectTime=" + receiptOrRejectTime +
		 * ", isWmsSku=" + isWmsSku + ", sCode=" + sCode + ", cbsSecCode=" +
		 * cbsSecCode + ", tsCode=" + tsCode + ", status=" + status +
		 * ", productSn=" + productSn + ", invoiceNumber=" + invoiceNumber +
		 * ", expressName=" + expressName + ", invoiceExpressNumber=" +
		 * invoiceExpressNumber + ", postMan=" + postMan + ", postManPhone=" +
		 * postManPhone + ", isNotice=" + isNotice + ", noticeType=" +
		 * noticeType + ", noticeRemark=" + noticeRemark + ", noticeTime=" +
		 * noticeTime + ", shippingTime=" + shippingTime + ", lessOrderSn=" +
		 * lessOrderSn + ", waitGetLesShippingInfo=" + waitGetLesShippingInfo +
		 * ", outping=" + outping + ", lessShipTime=" + lessShipTime +
		 * ", closeTime=" + closeTime + ", isReceipt=" + isReceipt +
		 * ", isMakeReceipt=" + isMakeReceipt + ", receiptNum=" + receiptNum +
		 * ", receiptAddTime=" + receiptAddTime + ", makeReceiptType=" +
		 * makeReceiptType + ", shippingMode=" + shippingMode +
		 * ", lastTimeForShippingMode=" + lastTimeForShippingMode +
		 * ", lastEditorForShippingMode=" + lastEditorForShippingMode +
		 * ", systemRemark=" + systemRemark + ", tongshuaiWorkId=" +
		 * tongshuaiWorkId + ", orderPromotionId=" + orderPromotionId +
		 * ", orderPromotionAmount=" + orderPromotionAmount +
		 * ", externalSaleSettingId=" + externalSaleSettingId +
		 * ", recommendationId=" + recommendationId + ", hasSendAlertNum=" +
		 * hasSendAlertNum + ", isNoLimitStockProduct=" + isNoLimitStockProduct
		 * + ", shippingOpporunity=" + shippingOpporunity + ", isTimeoutFree=" +
		 * isTimeoutFree + ", orders=" + orders + ", storeType=" + storeType +
		 * ", stockType=" + stockType + "]"; } /**
		 *
		 * @return the storeType
		 */
		public String getStoreType() {
			return storeType;
		}

		/**
		 *
		 * @param storeType
		 */
		public void setStoreType(String storeType) {
			this.storeType = storeType;
		}

		public String getStockType() {
			return stockType;
		}

		public void setStockType(String stockType) {
			this.stockType = stockType;
		}

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @param id
		 *            the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return the siteId
		 */
		public String getSiteId() {
			return siteId;
		}

		/**
		 * @param siteId
		 *            the siteId to set
		 */
		public void setSiteId(String siteId) {
			this.siteId = siteId;
		}

		/**
		 * @return the isTest
		 */
		public String getIsTest() {
			return isTest;
		}

		/**
		 * @param isTest
		 *            the isTest to set
		 */
		public void setIsTest(String isTest) {
			this.isTest = isTest;
		}

		/**
		 * @return the hasRead
		 */
		public String getHasRead() {
			return hasRead;
		}

		/**
		 * @param hasRead
		 *            the hasRead to set
		 */
		public void setHasRead(String hasRead) {
			this.hasRead = hasRead;
		}

		/**
		 * @return the supportOneDayLimit
		 */
		public String getSupportOneDayLimit() {
			return supportOneDayLimit;
		}

		/**
		 * @param supportOneDayLimit
		 *            the supportOneDayLimit to set
		 */
		public void setSupportOneDayLimit(String supportOneDayLimit) {
			this.supportOneDayLimit = supportOneDayLimit;
		}

		/**
		 * @return the orderId
		 */
		public String getOrderId() {
			return orderId;
		}

		/**
		 * @param orderId
		 *            the orderId to set
		 */
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		/**
		 * @return the cOrderSn
		 */
		public String getcOrderSn() {
			return cOrderSn;
		}

		/**
		 * @param cOrderSn
		 *            the cOrderSn to set
		 */
		public void setcOrderSn(String cOrderSn) {
			this.cOrderSn = cOrderSn;
		}

		/**
		 * @return the isBook
		 */
		public String getIsBook() {
			return isBook;
		}

		/**
		 * @param isBook
		 *            the isBook to set
		 */
		public void setIsBook(String isBook) {
			this.isBook = isBook;
		}

		/**
		 * @return the cPaymentStatus
		 */
		public String getcPaymentStatus() {
			return cPaymentStatus;
		}

		/**
		 * @param cPaymentStatus
		 *            the cPaymentStatus to set
		 */
		public void setcPaymentStatus(String cPaymentStatus) {
			this.cPaymentStatus = cPaymentStatus;
		}

		/**
		 * @return the cPayTime
		 */
		public String getcPayTime() {
			return cPayTime;
		}

		/**
		 * @param cPayTime
		 *            the cPayTime to set
		 */
		public void setcPayTime(String cPayTime) {
			this.cPayTime = cPayTime;
		}

		/**
		 * @return the productType
		 */
		public String getProductType() {
			return productType;
		}

		/**
		 * @param productType
		 *            the productType to set
		 */
		public void setProductType(String productType) {
			this.productType = productType;
		}

		/**
		 * @return the productId
		 */
		public String getProductId() {
			return productId;
		}

		/**
		 * @param productId
		 *            the productId to set
		 */
		public void setProductId(String productId) {
			this.productId = productId;
		}

		/**
		 * @return the productName
		 */
		public String getProductName() {
			return productName;
		}

		/**
		 * @param productName
		 *            the productName to set
		 */
		public void setProductName(String productName) {
			this.productName = productName;
		}

		/**
		 * @return the sku
		 */
		public String getSku() {
			return sku;
		}

		/**
		 * @param sku
		 *            the sku to set
		 */
		public void setSku(String sku) {
			this.sku = sku;
		}

		/**
		 * @return the price
		 */
		public String getPrice() {
			return price;
		}

		/**
		 * @param price
		 *            the price to set
		 */
		public void setPrice(String price) {
			this.price = price;
		}

		/**
		 * @return the number
		 */
		public String getNumber() {
			return number;
		}

		/**
		 * @param number
		 *            the number to set
		 */
		public void setNumber(String number) {
			this.number = number;
		}

		/**
		 * @return the lockedNumber
		 */
		public String getLockedNumber() {
			return lockedNumber;
		}

		/**
		 * @param lockedNumber
		 *            the lockedNumber to set
		 */
		public void setLockedNumber(String lockedNumber) {
			this.lockedNumber = lockedNumber;
		}

		/**
		 * @return the unlockedNumber
		 */
		public String getUnlockedNumber() {
			return unlockedNumber;
		}

		/**
		 * @param unlockedNumber
		 *            the unlockedNumber to set
		 */
		public void setUnlockedNumber(String unlockedNumber) {
			this.unlockedNumber = unlockedNumber;
		}

		/**
		 * @return the productAmount
		 */
		public double getProductAmount() {
			return productAmount;
		}

		/**
		 * @param productAmount
		 *            the productAmount to set
		 */
		public void setProductAmount(double productAmount) {
			this.productAmount = productAmount;
		}

		/**
		 * @return the balanceAmount
		 */
		public double getBalanceAmount() {
			return balanceAmount;
		}

		/**
		 * @param balanceAmount
		 *            the balanceAmount to set
		 */
		public void setBalanceAmount(double balanceAmount) {
			this.balanceAmount = balanceAmount;
		}

		/**
		 * @return the couponAmount
		 */
		public double getCouponAmount() {
			return couponAmount;
		}

		/**
		 * @param couponAmount
		 *            the couponAmount to set
		 */
		public void setCouponAmount(double couponAmount) {
			this.couponAmount = couponAmount;
		}

		/**
		 * @return the esAmount
		 */
		public double getEsAmount() {
			return esAmount;
		}

		/**
		 * @param esAmount
		 *            the esAmount to set
		 */
		public void setEsAmount(double esAmount) {
			this.esAmount = esAmount;
		}

		/**
		 * @return the giftCardNumberId
		 */
		public String getGiftCardNumberId() {
			return giftCardNumberId;
		}

		/**
		 * @param giftCardNumberId
		 *            the giftCardNumberId to set
		 */
		public void setGiftCardNumberId(String giftCardNumberId) {
			this.giftCardNumberId = giftCardNumberId;
		}

		/**
		 * @return the usedGiftCardAmount
		 */
		public double getUsedGiftCardAmount() {
			return usedGiftCardAmount;
		}

		/**
		 * @param usedGiftCardAmount
		 *            the usedGiftCardAmount to set
		 */
		public void setUsedGiftCardAmount(double usedGiftCardAmount) {
			this.usedGiftCardAmount = usedGiftCardAmount;
		}

		/**
		 * @return the couponLogId
		 */
		public String getCouponLogId() {
			return couponLogId;
		}

		/**
		 * @param couponLogId
		 *            the couponLogId to set
		 */
		public void setCouponLogId(String couponLogId) {
			this.couponLogId = couponLogId;
		}

		/**
		 * @return the activityPrice
		 */
		public double getActivityPrice() {
			return activityPrice;
		}

		/**
		 * @param activityPrice
		 *            the activityPrice to set
		 */
		public void setActivityPrice(double activityPrice) {
			this.activityPrice = activityPrice;
		}

		/**
		 * @return the activityId
		 */
		public String getActivityId() {
			return activityId;
		}

		/**
		 * @param activityId
		 *            the activityId to set
		 */
		public void setActivityId(String activityId) {
			this.activityId = activityId;
		}

		/**
		 * @return the cateId
		 */
		public String getCateId() {
			return cateId;
		}

		/**
		 * @param cateId
		 *            the cateId to set
		 */
		public void setCateId(String cateId) {
			this.cateId = cateId;
		}

		/**
		 * @return the brandId
		 */
		public String getBrandId() {
			return brandId;
		}

		/**
		 * @param brandId
		 *            the brandId to set
		 */
		public void setBrandId(String brandId) {
			this.brandId = brandId;
		}

		/**
		 * @return the netPointId
		 */
		public String getNetPointId() {
			return netPointId;
		}

		/**
		 * @param netPointId
		 *            the netPointId to set
		 */
		public void setNetPointId(String netPointId) {
			this.netPointId = netPointId;
		}

		/**
		 * @return the shippingFee
		 */
		public double getShippingFee() {
			return shippingFee;
		}

		/**
		 * @param shippingFee
		 *            the shippingFee to set
		 */
		public void setShippingFee(double shippingFee) {
			this.shippingFee = shippingFee;
		}

		/**
		 * @return the settlementStatus
		 */
		public String getSettlementStatus() {
			return settlementStatus;
		}

		/**
		 * @param settlementStatus
		 *            the settlementStatus to set
		 */
		public void setSettlementStatus(String settlementStatus) {
			this.settlementStatus = settlementStatus;
		}

		/**
		 * @return the receiptOrRejectTime
		 */
		public String getReceiptOrRejectTime() {
			return receiptOrRejectTime;
		}

		/**
		 * @param receiptOrRejectTime
		 *            the receiptOrRejectTime to set
		 */
		public void setReceiptOrRejectTime(String receiptOrRejectTime) {
			this.receiptOrRejectTime = receiptOrRejectTime;
		}

		/**
		 * @return the isWmsSku
		 */
		public String getIsWmsSku() {
			return isWmsSku;
		}

		/**
		 * @param isWmsSku
		 *            the isWmsSku to set
		 */
		public void setIsWmsSku(String isWmsSku) {
			this.isWmsSku = isWmsSku;
		}

		/**
		 * @return the sCode
		 */
		public String getsCode() {
			return sCode;
		}

		/**
		 * @param sCode
		 *            the sCode to set
		 */
		public void setsCode(String sCode) {
			this.sCode = sCode;
		}

		/**
		 * @return the cbsSecCode
		 */
		public String getCbsSecCode() {
			return cbsSecCode;
		}

		/**
		 * @param cbsSecCode
		 *            the cbsSecCode to set
		 */
		public void setCbsSecCode(String cbsSecCode) {
			this.cbsSecCode = cbsSecCode;
		}

		/**
		 * @return the tsCode
		 */
		public String getTsCode() {
			return tsCode;
		}

		/**
		 * @param tsCode
		 *            the tsCode to set
		 */
		public void setTsCode(String tsCode) {
			this.tsCode = tsCode;
		}

		/**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}

		/**
		 * @param status
		 *            the status to set
		 */
		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * @return the productSn
		 */
		public String getProductSn() {
			return productSn;
		}

		/**
		 * @param productSn
		 *            the productSn to set
		 */
		public void setProductSn(String productSn) {
			this.productSn = productSn;
		}

		/**
		 * @return the invoiceNumber
		 */
		public String getInvoiceNumber() {
			return invoiceNumber;
		}

		/**
		 * @param invoiceNumber
		 *            the invoiceNumber to set
		 */
		public void setInvoiceNumber(String invoiceNumber) {
			this.invoiceNumber = invoiceNumber;
		}

		/**
		 * @return the expressName
		 */
		public String getExpressName() {
			return expressName;
		}

		/**
		 * @param expressName
		 *            the expressName to set
		 */
		public void setExpressName(String expressName) {
			this.expressName = expressName;
		}

		/**
		 * @return the invoiceExpressNumber
		 */
		public String getInvoiceExpressNumber() {
			return invoiceExpressNumber;
		}

		/**
		 * @param invoiceExpressNumber
		 *            the invoiceExpressNumber to set
		 */
		public void setInvoiceExpressNumber(String invoiceExpressNumber) {
			this.invoiceExpressNumber = invoiceExpressNumber;
		}

		/**
		 * @return the postMan
		 */
		public String getPostMan() {
			return postMan;
		}

		/**
		 * @param postMan
		 *            the postMan to set
		 */
		public void setPostMan(String postMan) {
			this.postMan = postMan;
		}

		/**
		 * @return the postManPhone
		 */
		public String getPostManPhone() {
			return postManPhone;
		}

		/**
		 * @param postManPhone
		 *            the postManPhone to set
		 */
		public void setPostManPhone(String postManPhone) {
			this.postManPhone = postManPhone;
		}

		/**
		 * @return the isNotice
		 */
		public String getIsNotice() {
			return isNotice;
		}

		/**
		 * @param isNotice
		 *            the isNotice to set
		 */
		public void setIsNotice(String isNotice) {
			this.isNotice = isNotice;
		}

		/**
		 * @return the noticeType
		 */
		public String getNoticeType() {
			return noticeType;
		}

		/**
		 * @param noticeType
		 *            the noticeType to set
		 */
		public void setNoticeType(String noticeType) {
			this.noticeType = noticeType;
		}

		/**
		 * @return the noticeRemark
		 */
		public String getNoticeRemark() {
			return noticeRemark;
		}

		/**
		 * @param noticeRemark
		 *            the noticeRemark to set
		 */
		public void setNoticeRemark(String noticeRemark) {
			this.noticeRemark = noticeRemark;
		}

		/**
		 * @return the noticeTime
		 */
		public String getNoticeTime() {
			return noticeTime;
		}

		/**
		 * @param noticeTime
		 *            the noticeTime to set
		 */
		public void setNoticeTime(String noticeTime) {
			this.noticeTime = noticeTime;
		}

		/**
		 * @return the shippingTime
		 */
		public String getShippingTime() {
			return shippingTime;
		}

		/**
		 * @param shippingTime
		 *            the shippingTime to set
		 */
		public void setShippingTime(String shippingTime) {
			this.shippingTime = shippingTime;
		}

		/**
		 * @return the lessOrderSn
		 */
		public String getLessOrderSn() {
			return lessOrderSn;
		}

		/**
		 * @param lessOrderSn
		 *            the lessOrderSn to set
		 */
		public void setLessOrderSn(String lessOrderSn) {
			this.lessOrderSn = lessOrderSn;
		}

		/**
		 * @return the waitGetLesShippingInfo
		 */
		public String getWaitGetLesShippingInfo() {
			return waitGetLesShippingInfo;
		}

		/**
		 * @param waitGetLesShippingInfo
		 *            the waitGetLesShippingInfo to set
		 */
		public void setWaitGetLesShippingInfo(String waitGetLesShippingInfo) {
			this.waitGetLesShippingInfo = waitGetLesShippingInfo;
		}

		/**
		 * @return the outping
		 */
		public String getOutping() {
			return outping;
		}

		/**
		 * @param outping
		 *            the outping to set
		 */
		public void setOutping(String outping) {
			this.outping = outping;
		}

		/**
		 * @return the lessShipTime
		 */
		public String getLessShipTime() {
			return lessShipTime;
		}

		/**
		 * @param lessShipTime
		 *            the lessShipTime to set
		 */
		public void setLessShipTime(String lessShipTime) {
			this.lessShipTime = lessShipTime;
		}

		/**
		 * @return the closeTime
		 */
		public String getCloseTime() {
			return closeTime;
		}

		/**
		 * @param closeTime
		 *            the closeTime to set
		 */
		public void setCloseTime(String closeTime) {
			this.closeTime = closeTime;
		}

		/**
		 * @return the isReceipt
		 */
		public String getIsReceipt() {
			return isReceipt;
		}

		/**
		 * @param isReceipt
		 *            the isReceipt to set
		 */
		public void setIsReceipt(String isReceipt) {
			this.isReceipt = isReceipt;
		}

		/**
		 * @return the isMakeReceipt
		 */
		public String getIsMakeReceipt() {
			return isMakeReceipt;
		}

		/**
		 * @param isMakeReceipt
		 *            the isMakeReceipt to set
		 */
		public void setIsMakeReceipt(String isMakeReceipt) {
			this.isMakeReceipt = isMakeReceipt;
		}

		/**
		 * @return the receiptNum
		 */
		public String getReceiptNum() {
			return receiptNum;
		}

		/**
		 * @param receiptNum
		 *            the receiptNum to set
		 */
		public void setReceiptNum(String receiptNum) {
			this.receiptNum = receiptNum;
		}

		/**
		 * @return the receiptAddTime
		 */
		public String getReceiptAddTime() {
			return receiptAddTime;
		}

		/**
		 * @param receiptAddTime
		 *            the receiptAddTime to set
		 */
		public void setReceiptAddTime(String receiptAddTime) {
			this.receiptAddTime = receiptAddTime;
		}

		/**
		 * @return the makeReceiptType
		 */
		public String getMakeReceiptType() {
			return makeReceiptType;
		}

		/**
		 * @param makeReceiptType
		 *            the makeReceiptType to set
		 */
		public void setMakeReceiptType(String makeReceiptType) {
			this.makeReceiptType = makeReceiptType;
		}

		/**
		 * @return the shippingMode
		 */
		public String getShippingMode() {
			return shippingMode;
		}

		/**
		 * @param shippingMode
		 *            the shippingMode to set
		 */
		public void setShippingMode(String shippingMode) {
			this.shippingMode = shippingMode;
		}

		/**
		 * @return the lastTimeForShippingMode
		 */
		public String getLastTimeForShippingMode() {
			return lastTimeForShippingMode;
		}

		/**
		 * @param lastTimeForShippingMode
		 *            the lastTimeForShippingMode to set
		 */
		public void setLastTimeForShippingMode(String lastTimeForShippingMode) {
			this.lastTimeForShippingMode = lastTimeForShippingMode;
		}

		/**
		 * @return the lastEditorForShippingMode
		 */
		public String getLastEditorForShippingMode() {
			return lastEditorForShippingMode;
		}

		/**
		 * @param lastEditorForShippingMode
		 *            the lastEditorForShippingMode to set
		 */
		public void setLastEditorForShippingMode(String lastEditorForShippingMode) {
			this.lastEditorForShippingMode = lastEditorForShippingMode;
		}

		/**
		 * @return the systemRemark
		 */
		public String getSystemRemark() {
			return systemRemark;
		}

		/**
		 * @param systemRemark
		 *            the systemRemark to set
		 */
		public void setSystemRemark(String systemRemark) {
			this.systemRemark = systemRemark;
		}

		/**
		 * @return the tongshuaiWorkId
		 */
		public String getTongshuaiWorkId() {
			return tongshuaiWorkId;
		}

		/**
		 * @param tongshuaiWorkId
		 *            the tongshuaiWorkId to set
		 */
		public void setTongshuaiWorkId(String tongshuaiWorkId) {
			this.tongshuaiWorkId = tongshuaiWorkId;
		}

		/**
		 * @return the orderPromotionId
		 */
		public String getOrderPromotionId() {
			return orderPromotionId;
		}

		/**
		 * @param orderPromotionId
		 *            the orderPromotionId to set
		 */
		public void setOrderPromotionId(String orderPromotionId) {
			this.orderPromotionId = orderPromotionId;
		}

		/**
		 * @return the orderPromotionAmount
		 */
		public String getOrderPromotionAmount() {
			return orderPromotionAmount;
		}

		/**
		 * @param orderPromotionAmount
		 *            the orderPromotionAmount to set
		 */
		public void setOrderPromotionAmount(String orderPromotionAmount) {
			this.orderPromotionAmount = orderPromotionAmount;
		}

		/**
		 * @return the externalSaleSettingId
		 */
		public String getExternalSaleSettingId() {
			return externalSaleSettingId;
		}

		/**
		 * @param externalSaleSettingId
		 *            the externalSaleSettingId to set
		 */
		public void setExternalSaleSettingId(String externalSaleSettingId) {
			this.externalSaleSettingId = externalSaleSettingId;
		}

		/**
		 * @return the recommendationId
		 */
		public String getRecommendationId() {
			return recommendationId;
		}

		/**
		 * @param recommendationId
		 *            the recommendationId to set
		 */
		public void setRecommendationId(String recommendationId) {
			this.recommendationId = recommendationId;
		}

		/**
		 * @return the hasSendAlertNum
		 */
		public String getHasSendAlertNum() {
			return hasSendAlertNum;
		}

		/**
		 * @param hasSendAlertNum
		 *            the hasSendAlertNum to set
		 */
		public void setHasSendAlertNum(String hasSendAlertNum) {
			this.hasSendAlertNum = hasSendAlertNum;
		}

		/**
		 * @return the isNoLimitStockProduct
		 */
		public String getIsNoLimitStockProduct() {
			return isNoLimitStockProduct;
		}

		/**
		 * @param isNoLimitStockProduct
		 *            the isNoLimitStockProduct to set
		 */
		public void setIsNoLimitStockProduct(String isNoLimitStockProduct) {
			this.isNoLimitStockProduct = isNoLimitStockProduct;
		}

		/**
		 * @return the shippingOpporunity
		 */
		public String getShippingOpporunity() {
			return shippingOpporunity;
		}

		/**
		 * @param shippingOpporunity
		 *            the shippingOpporunity to set
		 */
		public void setShippingOpporunity(String shippingOpporunity) {
			this.shippingOpporunity = shippingOpporunity;
		}

		/**
		 * @return the isTimeoutFree
		 */
		public String getIsTimeoutFree() {
			return isTimeoutFree;
		}

		/**
		 * @param isTimeoutFree
		 *            the isTimeoutFree to set
		 */
		public void setIsTimeoutFree(String isTimeoutFree) {
			this.isTimeoutFree = isTimeoutFree;
		}

		/**
		 * @return the orders
		 */
		public Orders getOrders() {
			return orders;
		}

		/**
		 * @param orders
		 *            the orders to set
		 */
		public void setOrders(Orders orders) {
			this.orders = orders;
		}

		public String getStoreId() {
			return storeId;
		}

		public void setStoreId(String storeId) {
			this.storeId = storeId;
		}

		public static class Orders implements Serializable {
			private static final long serialVersionUID = -2895973338889340563L;
			private String id;
			private String siteId;
			private String isTest;
			private String hasSync;
			private String isBackend;
			private String isBook;
			private String isPackage;
			private double productAmount;
			private String onedayLimit;
			private double balanceAmount;
			private String addressLon;
			private String addressLat;
			private String smConfirmStatus;
			private String smConfirmTime;
			private String isTogether;
			private String isNotConfirm;
			private String smManualTime;
			private String tailPayTime;

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString() {
				return "Orders [id=" + id + ", siteId=" + siteId + ", isTest=" + isTest + ", hasSync=" + hasSync
						+ ", isBackend=" + isBackend + ", isBook=" + isBook + ", isPackage=" + isPackage
						+ ", productAmount=" + productAmount + ", onedayLimit=" + onedayLimit + ", balanceAmount="
						+ balanceAmount + ", addressLon=" + addressLon + ", addressLat=" + addressLat
						+ ", smConfirmStatus=" + smConfirmStatus + ", smConfirmTime=" + smConfirmTime + ", isTogether="
						+ isTogether + ", isNotConfirm=" + isNotConfirm + ", smManualTime=" + smManualTime
						+ ", tailPayTime=" + tailPayTime + "]";
			}

			/**
			 * @return the id
			 */
			public String getId() {
				return id;
			}

			/**
			 * @param id
			 *            the id to set
			 */
			public void setId(String id) {
				this.id = id;
			}

			/**
			 * @return the siteId
			 */
			public String getSiteId() {
				return siteId;
			}

			/**
			 * @param siteId
			 *            the siteId to set
			 */
			public void setSiteId(String siteId) {
				this.siteId = siteId;
			}

			/**
			 * @return the isTest
			 */
			public String getIsTest() {
				return isTest;
			}

			/**
			 * @param isTest
			 *            the isTest to set
			 */
			public void setIsTest(String isTest) {
				this.isTest = isTest;
			}

			/**
			 * @return the hasSync
			 */
			public String getHasSync() {
				return hasSync;
			}

			/**
			 * @param hasSync
			 *            the hasSync to set
			 */
			public void setHasSync(String hasSync) {
				this.hasSync = hasSync;
			}

			/**
			 * @return the isBackend
			 */
			public String getIsBackend() {
				return isBackend;
			}

			/**
			 * @param isBackend
			 *            the isBackend to set
			 */
			public void setIsBackend(String isBackend) {
				this.isBackend = isBackend;
			}

			/**
			 * @return the isBook
			 */
			public String getIsBook() {
				return isBook;
			}

			/**
			 * @param isBook
			 *            the isBook to set
			 */
			public void setIsBook(String isBook) {
				this.isBook = isBook;
			}

			/**
			 * @return the isPackage
			 */
			public String getIsPackage() {
				return isPackage;
			}

			/**
			 * @param isPackage
			 *            the isPackage to set
			 */
			public void setIsPackage(String isPackage) {
				this.isPackage = isPackage;
			}

			/**
			 * @return the productAmount
			 */
			public double getProductAmount() {
				return productAmount;
			}

			/**
			 * @param productAmount
			 *            the productAmount to set
			 */
			public void setProductAmount(double productAmount) {
				this.productAmount = productAmount;
			}

			/**
			 * @return the onedayLimit
			 */
			public String getOnedayLimit() {
				return onedayLimit;
			}

			/**
			 * @param onedayLimit
			 *            the onedayLimit to set
			 */
			public void setOnedayLimit(String onedayLimit) {
				this.onedayLimit = onedayLimit;
			}

			/**
			 * @return the balanceAmount
			 */
			public double getBalanceAmount() {
				return balanceAmount;
			}

			/**
			 * @param balanceAmount
			 *            the balanceAmount to set
			 */
			public void setBalanceAmount(double balanceAmount) {
				this.balanceAmount = balanceAmount;
			}

			/**
			 * @return the addressLon
			 */
			public String getAddressLon() {
				return addressLon;
			}

			/**
			 * @param addressLon
			 *            the addressLon to set
			 */
			public void setAddressLon(String addressLon) {
				this.addressLon = addressLon;
			}

			/**
			 * @return the addressLat
			 */
			public String getAddressLat() {
				return addressLat;
			}

			/**
			 * @param addressLat
			 *            the addressLat to set
			 */
			public void setAddressLat(String addressLat) {
				this.addressLat = addressLat;
			}

			/**
			 * @return the smConfirmStatus
			 */
			public String getSmConfirmStatus() {
				return smConfirmStatus;
			}

			/**
			 * @param smConfirmStatus
			 *            the smConfirmStatus to set
			 */
			public void setSmConfirmStatus(String smConfirmStatus) {
				this.smConfirmStatus = smConfirmStatus;
			}

			/**
			 * @return the smConfirmTime
			 */
			public String getSmConfirmTime() {
				return smConfirmTime;
			}

			/**
			 * @param smConfirmTime
			 *            the smConfirmTime to set
			 */
			public void setSmConfirmTime(String smConfirmTime) {
				this.smConfirmTime = smConfirmTime;
			}

			/**
			 * @return the isTogether
			 */
			public String getIsTogether() {
				return isTogether;
			}

			/**
			 * @param isTogether
			 *            the isTogether to set
			 */
			public void setIsTogether(String isTogether) {
				this.isTogether = isTogether;
			}

			/**
			 * @return the isNotConfirm
			 */
			public String getIsNotConfirm() {
				return isNotConfirm;
			}

			/**
			 * @param isNotConfirm
			 *            the isNotConfirm to set
			 */
			public void setIsNotConfirm(String isNotConfirm) {
				this.isNotConfirm = isNotConfirm;
			}

			/**
			 * @return the smManualTime
			 */
			public String getSmManualTime() {
				return smManualTime;
			}

			/**
			 * @param smManualTime
			 *            the smManualTime to set
			 */
			public void setSmManualTime(String smManualTime) {
				this.smManualTime = smManualTime;
			}

			/**
			 * @return the tailPayTime
			 */
			public String getTailPayTime() {
				return tailPayTime;
			}

			/**
			 * @param tailPayTime
			 *            the tailPayTime to set
			 */
			public void setTailPayTime(String tailPayTime) {
				this.tailPayTime = tailPayTime;
			}
		}

	}
}
