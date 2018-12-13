package com.haier.shop.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class Merchandise implements Serializable {

    private Integer id;//商品id
    private String productName;//商品名字
    private Long addTime;//添加时间
    private BigDecimal limitedPrice;//限价
    private BigDecimal saleGuidePrice;//价格
    private BigDecimal unitPrice;
    private Integer  number;//数量
    private BigDecimal  freight;//运费
    private String  sku;//
    private BigDecimal couponCodeValue;//优惠总额
    private BigDecimal esAmount;//节能补贴金额

    /*默认值 orderproduct*/
    private Integer isTest;//是否是测试网点
    private Integer supportOneDayLimit;//是否支持24小时时限达
    private String cOrderSn;//child order sn 子订单编码 C0919293(网单号)
    private Integer cPaymentStatus;//子订单付款状态
    private Integer cPayTime;//子订单付款时间
    private Integer productType;//商品类型
    private Integer lockedNumber;//曾经锁定的库存数量
    private Integer unlockedNumber;//曾经解锁的库存数量
    private BigDecimal productAmount;//此字段专为同步外部订单而加，商品总金额=price*number+shippingFee-优惠金额，但优惠金额没在本系统存储
    private BigDecimal couponAmount;//优惠券抵扣金额
    private Integer cateId;//分类ID
    private Integer brandId;//品牌id、
    private Integer netPointId;//网点id、
    private Integer settlementStatus;//结算状态0 未结算 1已结算 、
    private Integer receiptOrRejectTime;//确认收货时间或拒绝收货时间
    private Integer isWmsSku;//是否淘宝小家电
    private String sCode;//库位编码
    private Integer status;//状态
    private String invoiceNumber;//运单号
    private String expressName;//快递公司
    private String invoiceExpressNumber;//发票快递单号
    private Integer shippingTime;//发货时间
    private String lessOrderSn;//less 订单号
    private Integer waitGetLesShippingInfo;//是否等待获取LES物流配送节点信息
    private Integer getLesShippingCount;//已获取LES配送节点信息的次数
    private String outping;//出库凭证
    private Integer lessShipTime;//less出库时间
    private Integer closeTime;//网单完成关闭或取消关闭时间
    private String receiptNum;//发票号
    private String receiptAddTime;//开票时间
    private Integer makeReceiptType;//开票类型
    private String shippingMode;//物流模式值为B2B2C或B2C
    private Integer lastTimeForShippingMode;//最后修改物流模式的时间
    private String lastEditorForShippingMode;//最后修改物流模式的管理员
    private String systemRemark;//系统备注
    private Integer externalSaleSettingId;//淘宝套装id、
    private Integer isNoLimitStockProduct;//是否是无限制库存量的商品、
    private Integer splitFlag;//拆单标志、
    private String splitRelateCOrderSn;//拆单关联单号、
    private String oid;


    /*orders*/
    private Integer orderId;//订单ID
    private String orderSn;//订单号
    private Integer isProduceDaily;//是否是日日单
    private String source;//订单来源
    private String sourceOrderSn;//来源订单号
    private String province;//省
    private String city;//市
    private String region;//区
    private String address;//详细地址
    private String consignee;//收货人
    private String zipcode;//邮编
    private String mobile;//手机
    private String phone;//固话
    private Integer isTogether;//货票同行
    private String remark1;//备注
    private String relationOrderSn;//关联订单单号
    private Integer isTest1;//是否是测试订单
    private Integer isCod;//是否是货到付款
    private Integer notAutoConfirm;//是否是非自动确定订单
    private Integer memberId;//会员id
    private String memberEmail;//会员邮件
    private Long syncTime;//同步到此表的时间
    private Integer orderStatus;//订单状态
    private Integer payTime;//在线付款时间
    private Integer paymentStatus;//付款状态 0未付 1 已付
    private String receiptConsignee;//发票收件人
    private String receiptAddress;//发票地址
    private String receiptZipcode;//发票邮编
    private String receiptMobile;//发票联系电话
    private BigDecimal productAmount1;//商品金额
    private BigDecimal orderAmount;//订单总金额
    private BigDecimal paidAmount;//已支付金额
    private BigDecimal shippingAmount;//淘宝运费
    private BigDecimal totalEsAmount;//网单中总的节能补贴金额之和
    private String paymentCode;//支付方式
    private String payBankCode;//网银代码
    private String paymentName;//支付方式名称
    private String originRegionName;//原淘宝收货地址信息
    private String originAddress;//原淘宝收货人详细收货信息
    private Integer street;//街道ID
    private Integer markBuilding;//标建标志（0-未标建 1-标建）
    private String poiId;//标建ID
    private String poiName;//标建名称
    private String regionName;//地区名称（如：北京 北京 昌平区 兴寿镇）
    private String receiptInfo;//发票信息，
    private Integer delayShipTime;//延迟发货日期，
    private Integer firstConfirmTime;//首次确认时间，
    private String firstConfirmPerson;//第一次确认人，
    private String signCode;//收货确认码，
    private Integer orderType;//订单类型 默认0 团购预付款 团购正式单 2，
    private Integer memberInvoiceId;//订单发票ID,MemberInvoices表的主键
    private Integer taobaoGroupId;//淘宝万人团活动ID
    private String tradeType;//交易类型,值参考淘宝
    private String stepTradeStatus;//分阶段付款的订单状态,值参考淘宝
    private BigDecimal stepPaidFee;//分阶段付款的已付金额
    private BigDecimal depositAmount;//定金应付金额
    private BigDecimal balanceAmount;//尾款应付金额
    private Integer autoCancelDays;//未付款过期的天数
    private Integer isNoLimitStockOrder;//是否是无库存限制订单
    private Integer autoConfirmNum;//已自动确认的次数
    private Integer smConfirmTime;//请求发送HP时间，格式为时间戳
    private Integer provinceid;//保存省id、
    private Integer cityid;//保存市id、
    private Integer regionid;//保存区id、
    private Integer idGift;//是赠品
    private String agent;//处理人



    /*MemberInvoices*/
    private String invoiceTitle;//发票抬头
    private Integer electricFlag;//电子发票标志，1：电子发票；0：纸质发票
    private Integer invoiceType;//发票类型1-增票,2-普票
    private String memberName;//会员名称
    private String taxPayerNumber;//纳税人识别号
    private String registerAddress;//注册地址
    private String registerPhone;//注册电话
    private String bankName;//开户银行
    private String bankCardNumber;//银行帐户
    private Integer state;//审核状态0-待审核,1-审核通过,2-拒绝
    private String auditor;//审核人
    private String remark;//审核备注信息
    private Integer isLock;//是否锁定,1-锁定,0-未锁定
    private Integer parentId;//已审核通过的增票记录
    private Long addTime1;//添加时间
    private Integer invoicing;//是否开票

    private String chanye; //产业

    public String getChanye() {
        return chanye;
    }

    public void setChanye(String chanye) {
        this.chanye = chanye;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public BigDecimal getLimitedPrice() {
        return limitedPrice;
    }

    public void setLimitedPrice(BigDecimal limitedPrice) {
        this.limitedPrice = limitedPrice;
    }

    public BigDecimal getSaleGuidePrice() {
        return saleGuidePrice;
    }

    public void setSaleGuidePrice(BigDecimal saleGuidePrice) {
        this.saleGuidePrice = saleGuidePrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getIsTest() {
        return isTest;
    }

    public void setIsTest(Integer isTest) {
        this.isTest = isTest;
    }

    public Integer getSupportOneDayLimit() {
        return supportOneDayLimit;
    }

    public void setSupportOneDayLimit(Integer supportOneDayLimit) {
        this.supportOneDayLimit = supportOneDayLimit;
    }

    public String getcOrderSn() {
        return cOrderSn;
    }

    public void setcOrderSn(String cOrderSn) {
        this.cOrderSn = cOrderSn;
    }

    public Integer getcPaymentStatus() {
        return cPaymentStatus;
    }

    public void setcPaymentStatus(Integer cPaymentStatus) {
        this.cPaymentStatus = cPaymentStatus;
    }

    public Integer getcPayTime() {
        return cPayTime;
    }

    public void setcPayTime(Integer cPayTime) {
        this.cPayTime = cPayTime;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
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

    public BigDecimal getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(BigDecimal productAmount) {
        this.productAmount = productAmount;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
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

    public Integer getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(Integer settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    public Integer getReceiptOrRejectTime() {
        return receiptOrRejectTime;
    }

    public void setReceiptOrRejectTime(Integer receiptOrRejectTime) {
        this.receiptOrRejectTime = receiptOrRejectTime;
    }

    public Integer getIsWmsSku() {
        return isWmsSku;
    }

    public void setIsWmsSku(Integer isWmsSku) {
        this.isWmsSku = isWmsSku;
    }

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getWaitGetLesShippingInfo() {
        return waitGetLesShippingInfo;
    }

    public void setWaitGetLesShippingInfo(Integer waitGetLesShippingInfo) {
        this.waitGetLesShippingInfo = waitGetLesShippingInfo;
    }

    public Integer getGetLesShippingCount() {
        return getLesShippingCount;
    }

    public void setGetLesShippingCount(Integer getLesShippingCount) {
        this.getLesShippingCount = getLesShippingCount;
    }

    public String getOutping() {
        return outping;
    }

    public void setOutping(String outping) {
        this.outping = outping;
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

    public String getReceiptNum() {
        return receiptNum;
    }

    public void setReceiptNum(String receiptNum) {
        this.receiptNum = receiptNum;
    }

    public String getReceiptAddTime() {
        return receiptAddTime;
    }

    public void setReceiptAddTime(String receiptAddTime) {
        this.receiptAddTime = receiptAddTime;
    }

    public Integer getMakeReceiptType() {
        return makeReceiptType;
    }

    public void setMakeReceiptType(Integer makeReceiptType) {
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

    public String getSystemRemark() {
        return systemRemark;
    }

    public void setSystemRemark(String systemRemark) {
        this.systemRemark = systemRemark;
    }

    public Integer getExternalSaleSettingId() {
        return externalSaleSettingId;
    }

    public void setExternalSaleSettingId(Integer externalSaleSettingId) {
        this.externalSaleSettingId = externalSaleSettingId;
    }

    public Integer getIsNoLimitStockProduct() {
        return isNoLimitStockProduct;
    }

    public void setIsNoLimitStockProduct(Integer isNoLimitStockProduct) {
        this.isNoLimitStockProduct = isNoLimitStockProduct;
    }

    public Integer getSplitFlag() {
        return splitFlag;
    }

    public void setSplitFlag(Integer splitFlag) {
        this.splitFlag = splitFlag;
    }

    public String getSplitRelateCOrderSn() {
        return splitRelateCOrderSn;
    }

    public void setSplitRelateCOrderSn(String splitRelateCOrderSn) {
        this.splitRelateCOrderSn = splitRelateCOrderSn;
    }

    public Integer getIsProduceDaily() {
        return isProduceDaily;
    }

    public void setIsProduceDaily(Integer isProduceDaily) {
        this.isProduceDaily = isProduceDaily;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceOrderSn() {
        return sourceOrderSn;
    }

    public void setSourceOrderSn(String sourceOrderSn) {
        this.sourceOrderSn = sourceOrderSn;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIsTogether() {
        return isTogether;
    }

    public void setIsTogether(Integer isTogether) {
        this.isTogether = isTogether;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRelationOrderSn() {
        return relationOrderSn;
    }

    public void setRelationOrderSn(String relationOrderSn) {
        this.relationOrderSn = relationOrderSn;
    }

    public Integer getIsTest1() {
        return isTest1;
    }

    public void setIsTest1(Integer isTest1) {
        this.isTest1 = isTest1;
    }

    public Integer getIsCod() {
        return isCod;
    }

    public void setIsCod(Integer isCod) {
        this.isCod = isCod;
    }

    public Integer getNotAutoConfirm() {
        return notAutoConfirm;
    }

    public void setNotAutoConfirm(Integer notAutoConfirm) {
        this.notAutoConfirm = notAutoConfirm;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public Long getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Long syncTime) {
        this.syncTime = syncTime;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getPayTime() {
        return payTime;
    }

    public void setPayTime(Integer payTime) {
        this.payTime = payTime;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getReceiptConsignee() {
        return receiptConsignee;
    }

    public void setReceiptConsignee(String receiptConsignee) {
        this.receiptConsignee = receiptConsignee;
    }

    public String getReceiptAddress() {
        return receiptAddress;
    }

    public void setReceiptAddress(String receiptAddress) {
        this.receiptAddress = receiptAddress;
    }

    public String getReceiptZipcode() {
        return receiptZipcode;
    }

    public void setReceiptZipcode(String receiptZipcode) {
        this.receiptZipcode = receiptZipcode;
    }

    public String getReceiptMobile() {
        return receiptMobile;
    }

    public void setReceiptMobile(String receiptMobile) {
        this.receiptMobile = receiptMobile;
    }

    public BigDecimal getProductAmount1() {
        return productAmount1;
    }

    public void setProductAmount1(BigDecimal productAmount1) {
        this.productAmount1 = productAmount1;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public BigDecimal getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount(BigDecimal shippingAmount) {
        this.shippingAmount = shippingAmount;
    }

    public BigDecimal getTotalEsAmount() {
        return totalEsAmount;
    }

    public void setTotalEsAmount(BigDecimal totalEsAmount) {
        this.totalEsAmount = totalEsAmount;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getPayBankCode() {
        return payBankCode;
    }

    public void setPayBankCode(String payBankCode) {
        this.payBankCode = payBankCode;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getOriginRegionName() {
        return originRegionName;
    }

    public void setOriginRegionName(String originRegionName) {
        this.originRegionName = originRegionName;
    }

    public String getOriginAddress() {
        return originAddress;
    }

    public void setOriginAddress(String originAddress) {
        this.originAddress = originAddress;
    }

    public Integer getStreet() {
        return street;
    }

    public void setStreet(Integer street) {
        this.street = street;
    }

    public Integer getMarkBuilding() {
        return markBuilding;
    }

    public void setMarkBuilding(Integer markBuilding) {
        this.markBuilding = markBuilding;
    }

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getReceiptInfo() {
        return receiptInfo;
    }

    public void setReceiptInfo(String receiptInfo) {
        this.receiptInfo = receiptInfo;
    }

    public Integer getDelayShipTime() {
        return delayShipTime;
    }

    public void setDelayShipTime(Integer delayShipTime) {
        this.delayShipTime = delayShipTime;
    }

    public Integer getFirstConfirmTime() {
        return firstConfirmTime;
    }

    public void setFirstConfirmTime(Integer firstConfirmTime) {
        this.firstConfirmTime = firstConfirmTime;
    }

    public String getFirstConfirmPerson() {
        return firstConfirmPerson;
    }

    public void setFirstConfirmPerson(String firstConfirmPerson) {
        this.firstConfirmPerson = firstConfirmPerson;
    }

    public String getSignCode() {
        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getMemberInvoiceId() {
        return memberInvoiceId;
    }

    public void setMemberInvoiceId(Integer memberInvoiceId) {
        this.memberInvoiceId = memberInvoiceId;
    }

    public Integer getTaobaoGroupId() {
        return taobaoGroupId;
    }

    public void setTaobaoGroupId(Integer taobaoGroupId) {
        this.taobaoGroupId = taobaoGroupId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getStepTradeStatus() {
        return stepTradeStatus;
    }

    public void setStepTradeStatus(String stepTradeStatus) {
        this.stepTradeStatus = stepTradeStatus;
    }

    public BigDecimal getStepPaidFee() {
        return stepPaidFee;
    }

    public void setStepPaidFee(BigDecimal stepPaidFee) {
        this.stepPaidFee = stepPaidFee;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Integer getAutoCancelDays() {
        return autoCancelDays;
    }

    public void setAutoCancelDays(Integer autoCancelDays) {
        this.autoCancelDays = autoCancelDays;
    }

    public Integer getIsNoLimitStockOrder() {
        return isNoLimitStockOrder;
    }

    public void setIsNoLimitStockOrder(Integer isNoLimitStockOrder) {
        this.isNoLimitStockOrder = isNoLimitStockOrder;
    }

    public Integer getAutoConfirmNum() {
        return autoConfirmNum;
    }

    public void setAutoConfirmNum(Integer autoConfirmNum) {
        this.autoConfirmNum = autoConfirmNum;
    }

    public Integer getSmConfirmTime() {
        return smConfirmTime;
    }

    public void setSmConfirmTime(Integer smConfirmTime) {
        this.smConfirmTime = smConfirmTime;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public Integer getElectricFlag() {
        return electricFlag;
    }

    public void setElectricFlag(Integer electricFlag) {
        this.electricFlag = electricFlag;
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getTaxPayerNumber() {
        return taxPayerNumber;
    }

    public void setTaxPayerNumber(String taxPayerNumber) {
        this.taxPayerNumber = taxPayerNumber;
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress;
    }

    public String getRegisterPhone() {
        return registerPhone;
    }

    public void setRegisterPhone(String registerPhone) {
        this.registerPhone = registerPhone;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Long getAddTime1() {
        return addTime1;
    }

    public void setAddTime1(Long addTime1) {
        this.addTime1 = addTime1;
    }

    public BigDecimal getCouponCodeValue() {
        return couponCodeValue;
    }

    public void setCouponCodeValue(BigDecimal couponCodeValue) {
        this.couponCodeValue = couponCodeValue;
    }

    public BigDecimal getEsAmount() {
        return esAmount;
    }

    public void setEsAmount(BigDecimal esAmount) {
        this.esAmount = esAmount;
    }

    public Integer getInvoicing() {
        return invoicing;
    }

    public void setInvoicing(Integer invoicing) {
        this.invoicing = invoicing;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(Integer provinceid) {
        this.provinceid = provinceid;
    }

    public Integer getCityid() {
        return cityid;
    }

    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }

    public Integer getRegionid() {
        return regionid;
    }

    public void setRegionid(Integer regionid) {
        this.regionid = regionid;
    }

    public Integer getOrderId() { return orderId; }

    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public Integer getIdGift() {
        return idGift;
    }

    public void setIdGift(Integer idGift) {
        this.idGift = idGift;
    }

    public String getAgent() { return agent; }

    public void setAgent(String agent) { this.agent = agent; }

}
