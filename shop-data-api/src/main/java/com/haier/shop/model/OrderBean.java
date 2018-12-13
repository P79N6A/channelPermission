package com.haier.shop.model;

import java.io.Serializable;

public class OrderBean implements Serializable {
    private static final long serialVersionUID = -7301374123115407231L;

    private Boolean           success;

    /** 订单数据*/
    private Result            result;

    private String            message;

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "OrderBean [success=" + success + ", result=" + result + ", message=" + message
               + "]";
    }

    /**
     * @return the success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * @param success the success to set
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
     * @param result the result to set
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
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    public static class Result implements Serializable {
        private static final long serialVersionUID = -7810748825582395961L;
        private String            id;
        private String            siteId;
        private String            isTest;
        private String            hasSync;
        private String            isBackend;
        private String            isBook;
        private String            isCod;
        private String            notAutoConfirm;
        private String            isPackage;
        private String            packageId;
        private String            orderSn;
        private String            relationOrderSn;
        private String            memberId;
        private String            predictId;
        private String            memberEmail;
        private String            addTime;
        private String            syncTime;
        private String            orderStatus;
        private String            payTime;
        private String            paymentStatus;
        private String            receiptConsignee;
        private String            receiptAddress;
        private String            receiptZipcode;
        private String            receiptMobile;
        private double            productAmount;
        private double            orderAmount;
        private double            paidBalance;
        private double            giftCardAmount;
        private double            paidAmount;
        private double            shippingAmount;
        private double            totalEsAmount;
        private double            usedCustomerBalanceAmount;
        private String            customerId;
        private String            bestShippingTime;
        private String            paymentCode;
        private String            payBankCode;
        private String            paymentName;
        private String            consignee;
        private String            originRegionName;
        private String            originAddress;
        private String            province;
        private String            city;
        private String            region;
        private String            street;
        private String            markBuilding;
        private String            regionName;
        private String            address;
        private String            zipcode;
        private String            mobile;
        private String            phone;
        private String            receiptInfo;
        //        "receiptInfo": "a:3:{s:5:\"title\";s:6:\"呵呵\";s:11:\"receiptType\";s:12:\"普通发票\";s:11:\"needReceipt\";b:1;};",
        private String            delayShipTime;
        private String            remark;
        private String            bankCode;
        private String            agent;
        private String            confirmTime;
        private String            firstConfirmTime;
        private String            firstConfirmPerson;
        private String            finishTime;
        private String            tradeSn;
        private String            signCode;
        private String            source;
        private String            sourceOrderSn;
        private String            onedayLimit;
        private String            logisticsManner;
        private String            afterSaleManner;
        private String            personManner;
        private String            visitRemark;
        private String            visitTime;
        private String            visitPerson;
        private String            sellPeople;
        private String            sellPeopleManner;
        private String            orderType;
        private String            hasReadTaobaoOrderComment;
        private String            memberInvoiceId;
        private String            taobaoGroupId;
        private String            tradeType;
        private String            stepTradeStatus;
        private double            stepPaidFee;
        private double            depositAmount;
        private double            balanceAmount;
        private String            autoCancelDays;
        private String            isNoLimitStockOrder;
        private String            ccbOrderReceivedLogId;
        private String            ip;
        private String            isGiftCardOrder;
        private String            giftCardDownloadPassword;
        private String            giftCardFindMobile;
        private String            autoConfirmNum;
        private String            codConfirmPerson;
        private String            codConfirmTime;
        private String            codConfirmRemark;
        private String            codConfirmState;
        private String            paymentNoticeUrl;
        private String            addressLon;
        private String            addressLat;
        private String            smConfirmStatus;
        private String            smConfirmTime;
        private String            isTogether;
        private String            isNotConfirm;
        private String            smManualTime;
        private String            smManualRemark;
        private String            points;
        private String            tailPayTime;
        private String 			  isProduceDaily;



        @Override
		public String toString() {
			return "Result [id=" + id + ", siteId=" + siteId + ", isTest="
					+ isTest + ", hasSync=" + hasSync + ", isBackend="
					+ isBackend + ", isBook=" + isBook + ", isCod=" + isCod
					+ ", notAutoConfirm=" + notAutoConfirm + ", isPackage="
					+ isPackage + ", packageId=" + packageId + ", orderSn="
					+ orderSn + ", relationOrderSn=" + relationOrderSn
					+ ", memberId=" + memberId + ", predictId=" + predictId
					+ ", memberEmail=" + memberEmail + ", addTime=" + addTime
					+ ", syncTime=" + syncTime + ", orderStatus=" + orderStatus
					+ ", payTime=" + payTime + ", paymentStatus="
					+ paymentStatus + ", receiptConsignee=" + receiptConsignee
					+ ", receiptAddress=" + receiptAddress
					+ ", receiptZipcode=" + receiptZipcode + ", receiptMobile="
					+ receiptMobile + ", productAmount=" + productAmount
					+ ", orderAmount=" + orderAmount + ", paidBalance="
					+ paidBalance + ", giftCardAmount=" + giftCardAmount
					+ ", paidAmount=" + paidAmount + ", shippingAmount="
					+ shippingAmount + ", totalEsAmount=" + totalEsAmount
					+ ", usedCustomerBalanceAmount="
					+ usedCustomerBalanceAmount + ", customerId=" + customerId
					+ ", bestShippingTime=" + bestShippingTime
					+ ", paymentCode=" + paymentCode + ", payBankCode="
					+ payBankCode + ", paymentName=" + paymentName
					+ ", consignee=" + consignee + ", originRegionName="
					+ originRegionName + ", originAddress=" + originAddress
					+ ", province=" + province + ", city=" + city + ", region="
					+ region + ", street=" + street + ", markBuilding="
					+ markBuilding + ", regionName=" + regionName
					+ ", address=" + address + ", zipcode=" + zipcode
					+ ", mobile=" + mobile + ", phone=" + phone
					+ ", receiptInfo=" + receiptInfo + ", delayShipTime="
					+ delayShipTime + ", remark=" + remark + ", bankCode="
					+ bankCode + ", agent=" + agent + ", confirmTime="
					+ confirmTime + ", firstConfirmTime=" + firstConfirmTime
					+ ", firstConfirmPerson=" + firstConfirmPerson
					+ ", finishTime=" + finishTime + ", tradeSn=" + tradeSn
					+ ", signCode=" + signCode + ", source=" + source
					+ ", sourceOrderSn=" + sourceOrderSn + ", onedayLimit="
					+ onedayLimit + ", logisticsManner=" + logisticsManner
					+ ", afterSaleManner=" + afterSaleManner
					+ ", personManner=" + personManner + ", visitRemark="
					+ visitRemark + ", visitTime=" + visitTime
					+ ", visitPerson=" + visitPerson + ", sellPeople="
					+ sellPeople + ", sellPeopleManner=" + sellPeopleManner
					+ ", orderType=" + orderType
					+ ", hasReadTaobaoOrderComment="
					+ hasReadTaobaoOrderComment + ", memberInvoiceId="
					+ memberInvoiceId + ", taobaoGroupId=" + taobaoGroupId
					+ ", tradeType=" + tradeType + ", stepTradeStatus="
					+ stepTradeStatus + ", stepPaidFee=" + stepPaidFee
					+ ", depositAmount=" + depositAmount + ", balanceAmount="
					+ balanceAmount + ", autoCancelDays=" + autoCancelDays
					+ ", isNoLimitStockOrder=" + isNoLimitStockOrder
					+ ", ccbOrderReceivedLogId=" + ccbOrderReceivedLogId
					+ ", ip=" + ip + ", isGiftCardOrder=" + isGiftCardOrder
					+ ", giftCardDownloadPassword=" + giftCardDownloadPassword
					+ ", giftCardFindMobile=" + giftCardFindMobile
					+ ", autoConfirmNum=" + autoConfirmNum
					+ ", codConfirmPerson=" + codConfirmPerson
					+ ", codConfirmTime=" + codConfirmTime
					+ ", codConfirmRemark=" + codConfirmRemark
					+ ", codConfirmState=" + codConfirmState
					+ ", paymentNoticeUrl=" + paymentNoticeUrl
					+ ", addressLon=" + addressLon + ", addressLat="
					+ addressLat + ", smConfirmStatus=" + smConfirmStatus
					+ ", smConfirmTime=" + smConfirmTime + ", isTogether="
					+ isTogether + ", isNotConfirm=" + isNotConfirm
					+ ", smManualTime=" + smManualTime + ", smManualRemark="
					+ smManualRemark + ", points=" + points + ", tailPayTime="
					+ tailPayTime + ", isProduceDaily=" + isProduceDaily + "]";
		}

		public String getIsProduceDaily() {
			return isProduceDaily;
		}

		public void setIsProduceDaily(String isProduceDaily) {
			this.isProduceDaily = isProduceDaily;
		}

		/**
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id the id to set
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
         * @param siteId the siteId to set
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
         * @param isTest the isTest to set
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
         * @param hasSync the hasSync to set
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
         * @param isBackend the isBackend to set
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
         * @param isBook the isBook to set
         */
        public void setIsBook(String isBook) {
            this.isBook = isBook;
        }

        /**
         * @return the isCod
         */
        public String getIsCod() {
            return isCod;
        }

        /**
         * @param isCod the isCod to set
         */
        public void setIsCod(String isCod) {
            this.isCod = isCod;
        }

        /**
         * @return the notAutoConfirm
         */
        public String getNotAutoConfirm() {
            return notAutoConfirm;
        }

        /**
         * @param notAutoConfirm the notAutoConfirm to set
         */
        public void setNotAutoConfirm(String notAutoConfirm) {
            this.notAutoConfirm = notAutoConfirm;
        }

        /**
         * @return the isPackage
         */
        public String getIsPackage() {
            return isPackage;
        }

        /**
         * @param isPackage the isPackage to set
         */
        public void setIsPackage(String isPackage) {
            this.isPackage = isPackage;
        }

        /**
         * @return the packageId
         */
        public String getPackageId() {
            return packageId;
        }

        /**
         * @param packageId the packageId to set
         */
        public void setPackageId(String packageId) {
            this.packageId = packageId;
        }

        /**
         * @return the orderSn
         */
        public String getOrderSn() {
            return orderSn;
        }

        /**
         * @param orderSn the orderSn to set
         */
        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        /**
         * @return the relationOrderSn
         */
        public String getRelationOrderSn() {
            return relationOrderSn;
        }

        /**
         * @param relationOrderSn the relationOrderSn to set
         */
        public void setRelationOrderSn(String relationOrderSn) {
            this.relationOrderSn = relationOrderSn;
        }

        /**
         * @return the memberId
         */
        public String getMemberId() {
            return memberId;
        }

        /**
         * @param memberId the memberId to set
         */
        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        /**
         * @return the predictId
         */
        public String getPredictId() {
            return predictId;
        }

        /**
         * @param predictId the predictId to set
         */
        public void setPredictId(String predictId) {
            this.predictId = predictId;
        }

        /**
         * @return the memberEmail
         */
        public String getMemberEmail() {
            return memberEmail;
        }

        /**
         * @param memberEmail the memberEmail to set
         */
        public void setMemberEmail(String memberEmail) {
            this.memberEmail = memberEmail;
        }

        /**
         * @return the addTime
         */
        public String getAddTime() {
            return addTime;
        }

        /**
         * @param addTime the addTime to set
         */
        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        /**
         * @return the syncTime
         */
        public String getSyncTime() {
            return syncTime;
        }

        /**
         * @param syncTime the syncTime to set
         */
        public void setSyncTime(String syncTime) {
            this.syncTime = syncTime;
        }

        /**
         * @return the orderStatus
         */
        public String getOrderStatus() {
            return orderStatus;
        }

        /**
         * @param orderStatus the orderStatus to set
         */
        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        /**
         * @return the payTime
         */
        public String getPayTime() {
            return payTime;
        }

        /**
         * @param payTime the payTime to set
         */
        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        /**
         * @return the paymentStatus
         */
        public String getPaymentStatus() {
            return paymentStatus;
        }

        /**
         * @param paymentStatus the paymentStatus to set
         */
        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        /**
         * @return the receiptConsignee
         */
        public String getReceiptConsignee() {
            return receiptConsignee;
        }

        /**
         * @param receiptConsignee the receiptConsignee to set
         */
        public void setReceiptConsignee(String receiptConsignee) {
            this.receiptConsignee = receiptConsignee;
        }

        /**
         * @return the receiptAddress
         */
        public String getReceiptAddress() {
            return receiptAddress;
        }

        /**
         * @param receiptAddress the receiptAddress to set
         */
        public void setReceiptAddress(String receiptAddress) {
            this.receiptAddress = receiptAddress;
        }

        /**
         * @return the receiptZipcode
         */
        public String getReceiptZipcode() {
            return receiptZipcode;
        }

        /**
         * @param receiptZipcode the receiptZipcode to set
         */
        public void setReceiptZipcode(String receiptZipcode) {
            this.receiptZipcode = receiptZipcode;
        }

        /**
         * @return the receiptMobile
         */
        public String getReceiptMobile() {
            return receiptMobile;
        }

        /**
         * @param receiptMobile the receiptMobile to set
         */
        public void setReceiptMobile(String receiptMobile) {
            this.receiptMobile = receiptMobile;
        }

        /**
         * @return the productAmount
         */
        public double getProductAmount() {
            return productAmount;
        }

        /**
         * @param productAmount the productAmount to set
         */
        public void setProductAmount(double productAmount) {
            this.productAmount = productAmount;
        }

        /**
         * @return the orderAmount
         */
        public double getOrderAmount() {
            return orderAmount;
        }

        /**
         * @param orderAmount the orderAmount to set
         */
        public void setOrderAmount(double orderAmount) {
            this.orderAmount = orderAmount;
        }

        /**
         * @return the paidBalance
         */
        public double getPaidBalance() {
            return paidBalance;
        }

        /**
         * @param paidBalance the paidBalance to set
         */
        public void setPaidBalance(double paidBalance) {
            this.paidBalance = paidBalance;
        }

        /**
         * @return the giftCardAmount
         */
        public double getGiftCardAmount() {
            return giftCardAmount;
        }

        /**
         * @param giftCardAmount the giftCardAmount to set
         */
        public void setGiftCardAmount(double giftCardAmount) {
            this.giftCardAmount = giftCardAmount;
        }

        /**
         * @return the paidAmount
         */
        public double getPaidAmount() {
            return paidAmount;
        }

        /**
         * @param paidAmount the paidAmount to set
         */
        public void setPaidAmount(double paidAmount) {
            this.paidAmount = paidAmount;
        }

        /**
         * @return the shippingAmount
         */
        public double getShippingAmount() {
            return shippingAmount;
        }

        /**
         * @param shippingAmount the shippingAmount to set
         */
        public void setShippingAmount(double shippingAmount) {
            this.shippingAmount = shippingAmount;
        }

        /**
         * @return the totalEsAmount
         */
        public double getTotalEsAmount() {
            return totalEsAmount;
        }

        /**
         * @param totalEsAmount the totalEsAmount to set
         */
        public void setTotalEsAmount(double totalEsAmount) {
            this.totalEsAmount = totalEsAmount;
        }

        /**
         * @return the usedCustomerBalanceAmount
         */
        public double getUsedCustomerBalanceAmount() {
            return usedCustomerBalanceAmount;
        }

        /**
         * @param usedCustomerBalanceAmount the usedCustomerBalanceAmount to set
         */
        public void setUsedCustomerBalanceAmount(double usedCustomerBalanceAmount) {
            this.usedCustomerBalanceAmount = usedCustomerBalanceAmount;
        }

        /**
         * @return the customerId
         */
        public String getCustomerId() {
            return customerId;
        }

        /**
         * @param customerId the customerId to set
         */
        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        /**
         * @return the bestShippingTime
         */
        public String getBestShippingTime() {
            return bestShippingTime;
        }

        /**
         * @param bestShippingTime the bestShippingTime to set
         */
        public void setBestShippingTime(String bestShippingTime) {
            this.bestShippingTime = bestShippingTime;
        }

        /**
         * @return the paymentCode
         */
        public String getPaymentCode() {
            return paymentCode;
        }

        /**
         * @param paymentCode the paymentCode to set
         */
        public void setPaymentCode(String paymentCode) {
            this.paymentCode = paymentCode;
        }

        /**
         * @return the payBankCode
         */
        public String getPayBankCode() {
            return payBankCode;
        }

        /**
         * @param payBankCode the payBankCode to set
         */
        public void setPayBankCode(String payBankCode) {
            this.payBankCode = payBankCode;
        }

        /**
         * @return the paymentName
         */
        public String getPaymentName() {
            return paymentName;
        }

        /**
         * @param paymentName the paymentName to set
         */
        public void setPaymentName(String paymentName) {
            this.paymentName = paymentName;
        }

        /**
         * @return the consignee
         */
        public String getConsignee() {
            return consignee;
        }

        /**
         * @param consignee the consignee to set
         */
        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        /**
         * @return the originRegionName
         */
        public String getOriginRegionName() {
            return originRegionName;
        }

        /**
         * @param originRegionName the originRegionName to set
         */
        public void setOriginRegionName(String originRegionName) {
            this.originRegionName = originRegionName;
        }

        /**
         * @return the originAddress
         */
        public String getOriginAddress() {
            return originAddress;
        }

        /**
         * @param originAddress the originAddress to set
         */
        public void setOriginAddress(String originAddress) {
            this.originAddress = originAddress;
        }

        /**
         * @return the province
         */
        public String getProvince() {
            return province;
        }

        /**
         * @param province the province to set
         */
        public void setProvince(String province) {
            this.province = province;
        }

        /**
         * @return the city
         */
        public String getCity() {
            return city;
        }

        /**
         * @param city the city to set
         */
        public void setCity(String city) {
            this.city = city;
        }

        /**
         * @return the region
         */
        public String getRegion() {
            return region;
        }

        /**
         * @param region the region to set
         */
        public void setRegion(String region) {
            this.region = region;
        }

        /**
         * @return the street
         */
        public String getStreet() {
            return street;
        }

        /**
         * @param street the street to set
         */
        public void setStreet(String street) {
            this.street = street;
        }

        /**
         * @return the markBuilding
         */
        public String getMarkBuilding() {
            return markBuilding;
        }

        /**
         * @param markBuilding the markBuilding to set
         */
        public void setMarkBuilding(String markBuilding) {
            this.markBuilding = markBuilding;
        }

        /**
         * @return the regionName
         */
        public String getRegionName() {
            return regionName;
        }

        /**
         * @param regionName the regionName to set
         */
        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        /**
         * @return the address
         */
        public String getAddress() {
            return address;
        }

        /**
         * @param address the address to set
         */
        public void setAddress(String address) {
            this.address = address;
        }

        /**
         * @return the zipcode
         */
        public String getZipcode() {
            return zipcode;
        }

        /**
         * @param zipcode the zipcode to set
         */
        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        /**
         * @return the mobile
         */
        public String getMobile() {
            return mobile;
        }

        /**
         * @param mobile the mobile to set
         */
        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        /**
         * @return the phone
         */
        public String getPhone() {
            return phone;
        }

        /**
         * @param phone the phone to set
         */
        public void setPhone(String phone) {
            this.phone = phone;
        }

        /**
         * @return the receiptInfo
         */
        public String getReceiptInfo() {
            return receiptInfo;
        }

        /**
         * @param receiptInfo the receiptInfo to set
         */
        public void setReceiptInfo(String receiptInfo) {
            this.receiptInfo = receiptInfo;
        }

        /**
         * @return the delayShipTime
         */
        public String getDelayShipTime() {
            return delayShipTime;
        }

        /**
         * @param delayShipTime the delayShipTime to set
         */
        public void setDelayShipTime(String delayShipTime) {
            this.delayShipTime = delayShipTime;
        }

        /**
         * @return the remark
         */
        public String getRemark() {
            return remark;
        }

        /**
         * @param remark the remark to set
         */
        public void setRemark(String remark) {
            this.remark = remark;
        }

        /**
         * @return the bankCode
         */
        public String getBankCode() {
            return bankCode;
        }

        /**
         * @param bankCode the bankCode to set
         */
        public void setBankCode(String bankCode) {
            this.bankCode = bankCode;
        }

        /**
         * @return the agent
         */
        public String getAgent() {
            return agent;
        }

        /**
         * @param agent the agent to set
         */
        public void setAgent(String agent) {
            this.agent = agent;
        }

        /**
         * @return the confirmTime
         */
        public String getConfirmTime() {
            return confirmTime;
        }

        /**
         * @param confirmTime the confirmTime to set
         */
        public void setConfirmTime(String confirmTime) {
            this.confirmTime = confirmTime;
        }

        /**
         * @return the firstConfirmTime
         */
        public String getFirstConfirmTime() {
            return firstConfirmTime;
        }

        /**
         * @param firstConfirmTime the firstConfirmTime to set
         */
        public void setFirstConfirmTime(String firstConfirmTime) {
            this.firstConfirmTime = firstConfirmTime;
        }

        /**
         * @return the firstConfirmPerson
         */
        public String getFirstConfirmPerson() {
            return firstConfirmPerson;
        }

        /**
         * @param firstConfirmPerson the firstConfirmPerson to set
         */
        public void setFirstConfirmPerson(String firstConfirmPerson) {
            this.firstConfirmPerson = firstConfirmPerson;
        }

        /**
         * @return the finishTime
         */
        public String getFinishTime() {
            return finishTime;
        }

        /**
         * @param finishTime the finishTime to set
         */
        public void setFinishTime(String finishTime) {
            this.finishTime = finishTime;
        }

        /**
         * @return the tradeSn
         */
        public String getTradeSn() {
            return tradeSn;
        }

        /**
         * @param tradeSn the tradeSn to set
         */
        public void setTradeSn(String tradeSn) {
            this.tradeSn = tradeSn;
        }

        /**
         * @return the signCode
         */
        public String getSignCode() {
            return signCode;
        }

        /**
         * @param signCode the signCode to set
         */
        public void setSignCode(String signCode) {
            this.signCode = signCode;
        }

        /**
         * @return the source
         */
        public String getSource() {
            return source;
        }

        /**
         * @param source the source to set
         */
        public void setSource(String source) {
            this.source = source;
        }

        /**
         * @return the sourceOrderSn
         */
        public String getSourceOrderSn() {
            return sourceOrderSn;
        }

        /**
         * @param sourceOrderSn the sourceOrderSn to set
         */
        public void setSourceOrderSn(String sourceOrderSn) {
            this.sourceOrderSn = sourceOrderSn;
        }

        /**
         * @return the onedayLimit
         */
        public String getOnedayLimit() {
            return onedayLimit;
        }

        /**
         * @param onedayLimit the onedayLimit to set
         */
        public void setOnedayLimit(String onedayLimit) {
            this.onedayLimit = onedayLimit;
        }

        /**
         * @return the logisticsManner
         */
        public String getLogisticsManner() {
            return logisticsManner;
        }

        /**
         * @param logisticsManner the logisticsManner to set
         */
        public void setLogisticsManner(String logisticsManner) {
            this.logisticsManner = logisticsManner;
        }

        /**
         * @return the afterSaleManner
         */
        public String getAfterSaleManner() {
            return afterSaleManner;
        }

        /**
         * @param afterSaleManner the afterSaleManner to set
         */
        public void setAfterSaleManner(String afterSaleManner) {
            this.afterSaleManner = afterSaleManner;
        }

        /**
         * @return the personManner
         */
        public String getPersonManner() {
            return personManner;
        }

        /**
         * @param personManner the personManner to set
         */
        public void setPersonManner(String personManner) {
            this.personManner = personManner;
        }

        /**
         * @return the visitRemark
         */
        public String getVisitRemark() {
            return visitRemark;
        }

        /**
         * @param visitRemark the visitRemark to set
         */
        public void setVisitRemark(String visitRemark) {
            this.visitRemark = visitRemark;
        }

        /**
         * @return the visitTime
         */
        public String getVisitTime() {
            return visitTime;
        }

        /**
         * @param visitTime the visitTime to set
         */
        public void setVisitTime(String visitTime) {
            this.visitTime = visitTime;
        }

        /**
         * @return the visitPerson
         */
        public String getVisitPerson() {
            return visitPerson;
        }

        /**
         * @param visitPerson the visitPerson to set
         */
        public void setVisitPerson(String visitPerson) {
            this.visitPerson = visitPerson;
        }

        /**
         * @return the sellPeople
         */
        public String getSellPeople() {
            return sellPeople;
        }

        /**
         * @param sellPeople the sellPeople to set
         */
        public void setSellPeople(String sellPeople) {
            this.sellPeople = sellPeople;
        }

        /**
         * @return the sellPeopleManner
         */
        public String getSellPeopleManner() {
            return sellPeopleManner;
        }

        /**
         * @param sellPeopleManner the sellPeopleManner to set
         */
        public void setSellPeopleManner(String sellPeopleManner) {
            this.sellPeopleManner = sellPeopleManner;
        }

        /**
         * @return the orderType
         */
        public String getOrderType() {
            return orderType;
        }

        /**
         * @param orderType the orderType to set
         */
        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        /**
         * @return the hasReadTaobaoOrderComment
         */
        public String getHasReadTaobaoOrderComment() {
            return hasReadTaobaoOrderComment;
        }

        /**
         * @param hasReadTaobaoOrderComment the hasReadTaobaoOrderComment to set
         */
        public void setHasReadTaobaoOrderComment(String hasReadTaobaoOrderComment) {
            this.hasReadTaobaoOrderComment = hasReadTaobaoOrderComment;
        }

        /**
         * @return the memberInvoiceId
         */
        public String getMemberInvoiceId() {
            return memberInvoiceId;
        }

        /**
         * @param memberInvoiceId the memberInvoiceId to set
         */
        public void setMemberInvoiceId(String memberInvoiceId) {
            this.memberInvoiceId = memberInvoiceId;
        }

        /**
         * @return the taobaoGroupId
         */
        public String getTaobaoGroupId() {
            return taobaoGroupId;
        }

        /**
         * @param taobaoGroupId the taobaoGroupId to set
         */
        public void setTaobaoGroupId(String taobaoGroupId) {
            this.taobaoGroupId = taobaoGroupId;
        }

        /**
         * @return the tradeType
         */
        public String getTradeType() {
            return tradeType;
        }

        /**
         * @param tradeType the tradeType to set
         */
        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }

        /**
         * @return the stepTradeStatus
         */
        public String getStepTradeStatus() {
            return stepTradeStatus;
        }

        /**
         * @param stepTradeStatus the stepTradeStatus to set
         */
        public void setStepTradeStatus(String stepTradeStatus) {
            this.stepTradeStatus = stepTradeStatus;
        }

        /**
         * @return the stepPaidFee
         */
        public double getStepPaidFee() {
            return stepPaidFee;
        }

        /**
         * @param stepPaidFee the stepPaidFee to set
         */
        public void setStepPaidFee(double stepPaidFee) {
            this.stepPaidFee = stepPaidFee;
        }

        /**
         * @return the depositAmount
         */
        public double getDepositAmount() {
            return depositAmount;
        }

        /**
         * @param depositAmount the depositAmount to set
         */
        public void setDepositAmount(double depositAmount) {
            this.depositAmount = depositAmount;
        }

        /**
         * @return the balanceAmount
         */
        public double getBalanceAmount() {
            return balanceAmount;
        }

        /**
         * @param balanceAmount the balanceAmount to set
         */
        public void setBalanceAmount(double balanceAmount) {
            this.balanceAmount = balanceAmount;
        }

        /**
         * @return the autoCancelDays
         */
        public String getAutoCancelDays() {
            return autoCancelDays;
        }

        /**
         * @param autoCancelDays the autoCancelDays to set
         */
        public void setAutoCancelDays(String autoCancelDays) {
            this.autoCancelDays = autoCancelDays;
        }

        /**
         * @return the isNoLimitStockOrder
         */
        public String getIsNoLimitStockOrder() {
            return isNoLimitStockOrder;
        }

        /**
         * @param isNoLimitStockOrder the isNoLimitStockOrder to set
         */
        public void setIsNoLimitStockOrder(String isNoLimitStockOrder) {
            this.isNoLimitStockOrder = isNoLimitStockOrder;
        }

        /**
         * @return the ccbOrderReceivedLogId
         */
        public String getCcbOrderReceivedLogId() {
            return ccbOrderReceivedLogId;
        }

        /**
         * @param ccbOrderReceivedLogId the ccbOrderReceivedLogId to set
         */
        public void setCcbOrderReceivedLogId(String ccbOrderReceivedLogId) {
            this.ccbOrderReceivedLogId = ccbOrderReceivedLogId;
        }

        /**
         * @return the ip
         */
        public String getIp() {
            return ip;
        }

        /**
         * @param ip the ip to set
         */
        public void setIp(String ip) {
            this.ip = ip;
        }

        /**
         * @return the isGiftCardOrder
         */
        public String getIsGiftCardOrder() {
            return isGiftCardOrder;
        }

        /**
         * @param isGiftCardOrder the isGiftCardOrder to set
         */
        public void setIsGiftCardOrder(String isGiftCardOrder) {
            this.isGiftCardOrder = isGiftCardOrder;
        }

        /**
         * @return the giftCardDownloadPassword
         */
        public String getGiftCardDownloadPassword() {
            return giftCardDownloadPassword;
        }

        /**
         * @param giftCardDownloadPassword the giftCardDownloadPassword to set
         */
        public void setGiftCardDownloadPassword(String giftCardDownloadPassword) {
            this.giftCardDownloadPassword = giftCardDownloadPassword;
        }

        /**
         * @return the giftCardFindMobile
         */
        public String getGiftCardFindMobile() {
            return giftCardFindMobile;
        }

        /**
         * @param giftCardFindMobile the giftCardFindMobile to set
         */
        public void setGiftCardFindMobile(String giftCardFindMobile) {
            this.giftCardFindMobile = giftCardFindMobile;
        }

        /**
         * @return the autoConfirmNum
         */
        public String getAutoConfirmNum() {
            return autoConfirmNum;
        }

        /**
         * @param autoConfirmNum the autoConfirmNum to set
         */
        public void setAutoConfirmNum(String autoConfirmNum) {
            this.autoConfirmNum = autoConfirmNum;
        }

        /**
         * @return the codConfirmPerson
         */
        public String getCodConfirmPerson() {
            return codConfirmPerson;
        }

        /**
         * @param codConfirmPerson the codConfirmPerson to set
         */
        public void setCodConfirmPerson(String codConfirmPerson) {
            this.codConfirmPerson = codConfirmPerson;
        }

        /**
         * @return the codConfirmTime
         */
        public String getCodConfirmTime() {
            return codConfirmTime;
        }

        /**
         * @param codConfirmTime the codConfirmTime to set
         */
        public void setCodConfirmTime(String codConfirmTime) {
            this.codConfirmTime = codConfirmTime;
        }

        /**
         * @return the codConfirmRemark
         */
        public String getCodConfirmRemark() {
            return codConfirmRemark;
        }

        /**
         * @param codConfirmRemark the codConfirmRemark to set
         */
        public void setCodConfirmRemark(String codConfirmRemark) {
            this.codConfirmRemark = codConfirmRemark;
        }

        /**
         * @return the codConfirmState
         */
        public String getCodConfirmState() {
            return codConfirmState;
        }

        /**
         * @param codConfirmState the codConfirmState to set
         */
        public void setCodConfirmState(String codConfirmState) {
            this.codConfirmState = codConfirmState;
        }

        /**
         * @return the paymentNoticeUrl
         */
        public String getPaymentNoticeUrl() {
            return paymentNoticeUrl;
        }

        /**
         * @param paymentNoticeUrl the paymentNoticeUrl to set
         */
        public void setPaymentNoticeUrl(String paymentNoticeUrl) {
            this.paymentNoticeUrl = paymentNoticeUrl;
        }

        /**
         * @return the addressLon
         */
        public String getAddressLon() {
            return addressLon;
        }

        /**
         * @param addressLon the addressLon to set
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
         * @param addressLat the addressLat to set
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
         * @param smConfirmStatus the smConfirmStatus to set
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
         * @param smConfirmTime the smConfirmTime to set
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
         * @param isTogether the isTogether to set
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
         * @param isNotConfirm the isNotConfirm to set
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
         * @param smManualTime the smManualTime to set
         */
        public void setSmManualTime(String smManualTime) {
            this.smManualTime = smManualTime;
        }

        /**
         * @return the smManualRemark
         */
        public String getSmManualRemark() {
            return smManualRemark;
        }

        /**
         * @param smManualRemark the smManualRemark to set
         */
        public void setSmManualRemark(String smManualRemark) {
            this.smManualRemark = smManualRemark;
        }

        /**
         * @return the points
         */
        public String getPoints() {
            return points;
        }

        /**
         * @param points the points to set
         */
        public void setPoints(String points) {
            this.points = points;
        }

        /**
         * @return the tailPayTime
         */
        public String getTailPayTime() {
            return tailPayTime;
        }

        /**
         * @param tailPayTime the tailPayTime to set
         */
        public void setTailPayTime(String tailPayTime) {
            this.tailPayTime = tailPayTime;
        }
    }
}
