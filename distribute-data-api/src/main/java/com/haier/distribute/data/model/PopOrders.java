package com.haier.distribute.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/6 0006
 * \* Time: 17:22
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class PopOrders implements Serializable {

    private static final long serialVersionUID = -371668894870650117L;

    private Long id;
    /**
     * 订单来源-淘宝海尔新宝旗舰店分销平台
     */
    public static final String ORIGIN_FENXIAOXB = "TOPFENXIAOXB";

    /**
     * 订单来源-海尔新宝旗舰店
     */
    public static final String ORIGIN_TOPXB = "TOPXB";

    /**
     * 订单来源-统帅分销
     */
    public static final String ORIGIN_TSFX      = "TONGSHUAIFX";
    /**
     * 订单来源-1号店订单
     */
    public static final String ORIGIN_YIHAODIAN = "YIHAODIAN";
    /**
     * 订单来源-平台大客户-1号店订单
     */
    public static final String ORIGIN_YHD       = "YHD";
    /**
     * 订单来源-统帅日日顺乐家专卖店
     */
    public static final String ORIGIN_TONGSHUAI = "TONGSHUAI";

    public static final String ORIGIN_FRONT = "ORIGIN_FRONT";

    private Integer siteId;

    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer value) {
        this.siteId = value;
    }

    private Integer isTest;

    /**
     * 获取 是否是测试订单。
     */
    public Integer getIsTest() {
        return this.isTest;
    }

    /**
     * 设置 是否是测试订单。
     *
     * @param value 属性值
     */
    public void setIsTest(Integer value) {
        this.isTest = value;
    }

    private Integer hasSync = 0;

    /**
     * 获取 是否已同步(临时添加)。
     */
    public Integer getHasSync() {
        return this.hasSync;
    }

    /**
     * 设置 是否已同步(临时添加)。
     *
     * @param value 属性值
     */
    public void setHasSync(Integer value) {
        this.hasSync = value;
    }

    private Integer isBackend = 0;

    /**
     * 获取 是否为后台添加的订单。
     */
    public Integer getIsBackend() {
        return this.isBackend;
    }

    /**
     * 设置 是否为后台添加的订单。
     *
     * @param value 属性值
     */
    public void setIsBackend(Integer value) {
        this.isBackend = value;
    }

    private Integer isBook = 0;

    /**
     * 获取 是否为预定订单。
     */
    public Integer getIsBook() {
        return this.isBook;
    }

    /**
     * 设置 是否为预定订单。
     *
     * @param value 属性值
     */
    public void setIsBook(Integer value) {
        this.isBook = value;
    }

    private Integer isCod;
    private String isCodDisplay; // 显示用

    /**
     * 获取 是否是货到付款订单。
     */
    public Integer getIsCod() {
        return this.isCod;
    }

    /**
     * 设置 是否是货到付款订单。
     *
     * @param value 属性值
     */
    public void setIsCod(Integer value) {
        this.isCod = value;
    }

    /**
     * 获取 是否是货到付款订单。
     */
    public String getIsCodDisplay() {
        return isCodDisplay;
    }

    /**
     * 设置 是否是货到付款订单。
     *
     * @param value 属性值
     */
    public void setIsCodDisplay(String isCodDisplay) {
        this.isCodDisplay = isCodDisplay;
    }

    private Integer notAutoConfirm;

    /**
     * 获取 是否是非自动确认订单。
     */
    public Integer getNotAutoConfirm() {
        return this.notAutoConfirm;
    }

    /**
     * 设置 是否是非自动确认订单。
     *
     * @param value 属性值
     */
    public void setNotAutoConfirm(Integer value) {
        this.notAutoConfirm = value;
    }

    private Integer isPackage = 0;

    /**
     * 获取 是否为套装订单。
     */
    public Integer getIsPackage() {
        return this.isPackage;
    }

    /**
     * 设置 是否为套装订单。
     *
     * @param value 属性值
     */
    public void setIsPackage(Integer value) {
        this.isPackage = value;
    }

    private Integer packageId;

    /**
     * 获取 套装ID。
     */
    public Integer getPackageId() {
        return this.packageId;
    }

    /**
     * 设置 套装ID。
     *
     * @param value 属性值
     */
    public void setPackageId(Integer value) {
        this.packageId = value;
    }

    private String orderSn;
    private String exportOrderSn;

    /**
     * 获取 订单号。
     */
    public String getOrderSn() {
        return this.orderSn;
    }

    /**
     * 设置 订单号。
     *
     * @param value 属性值
     */
    public void setOrderSn(String value) {
        this.orderSn = value;
    }

    private String relationOrderSn;

    /**
     * 获取 关联订单编号。
     */
    public String getRelationOrderSn() {
        return this.relationOrderSn;
    }

    /**
     * 设置 关联订单编号。
     *
     * @param value 属性值
     */
    public void setRelationOrderSn(String value) {
        this.relationOrderSn = value;
    }

    private Integer memberId;

    /**
     * 获取 会员id。
     */
    public Integer getMemberId() {
        return this.memberId;
    }

    /**
     * 设置 会员id。
     *
     * @param value 属性值
     */
    public void setMemberId(Integer value) {
        this.memberId = value;
    }

    private Integer predictId;

    /**
     * 获取 会员购买预测ID。
     */
    public Integer getPredictId() {
        return this.predictId;
    }

    /**
     * 设置 会员购买预测ID。
     *
     * @param value 属性值
     */
    public void setPredictId(Integer value) {
        this.predictId = value;
    }

    private String memberEmail;

    /**
     * 获取 会员邮件。
     */
    public String getMemberEmail() {
        return this.memberEmail;
    }

    /**
     * 设置 会员邮件。
     *
     * @param value 属性值
     */
    public void setMemberEmail(String value) {
        this.memberEmail = value;
    }

    private Integer addTime;

    public Integer getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Integer value) {
        this.addTime = value;
    }

    private Integer syncTime;

    /**
     * 获取 同步到此表中的时间。
     */
    public Integer getSyncTime() {
        return this.syncTime;
    }

    /**
     * 设置 同步到此表中的时间。
     *
     * @param value 属性值
     */
    public void setSyncTime(Integer value) {
        this.syncTime = value;
    }

    private String orderStatus;

    /**
     * 获取 订单状态。
     */
    public String getOrderStatus() {
        return this.orderStatus;
    }

    /**
     * 设置 订单状态。
     *
     * @param value 属性值
     */
    public void setOrderStatus(String value) {
        this.orderStatus = value;
    }

    private String payTime;

    /**
     * 获取 在线付款时间。
     */
    public String getPayTime() {
        return this.payTime;
    }

    /**
     * 设置 在线付款时间。
     *
     * @param value 属性值
     */
    public void setPayTime(String value) {
        this.payTime = value;
    }

    private Integer paymentStatus;

    /**
     * 获取 付款状态：0 买家未付款 1 买家已付款 。
     */
    public Integer getPaymentStatus() {
        return this.paymentStatus;
    }

    /**
     * 设置 付款状态：0 买家未付款 1 买家已付款 。
     *
     * @param value 属性值
     */
    public void setPaymentStatus(Integer value) {
        this.paymentStatus = value;
    }

    private String receiptConsignee;

    /**
     * 获取 发票收件人。
     */
    public String getReceiptConsignee() {
        return this.receiptConsignee;
    }

    /**
     * 设置 发票收件人。
     *
     * @param value 属性值
     */
    public void setReceiptConsignee(String value) {
        this.receiptConsignee = value;
    }

    private String receiptAddress;

    /**
     * 获取 发票地址。
     */
    public String getReceiptAddress() {
        return this.receiptAddress;
    }

    /**
     * 设置 发票地址。
     *
     * @param value 属性值
     */
    public void setReceiptAddress(String value) {
        this.receiptAddress = value;
    }

    private String receiptZipcode;

    /**
     * 获取 发票邮编。
     */
    public String getReceiptZipcode() {
        return this.receiptZipcode;
    }

    /**
     * 设置 发票邮编。
     *
     * @param value 属性值
     */
    public void setReceiptZipcode(String value) {
        this.receiptZipcode = value;
    }

    private String receiptMobile;

    /**
     * 获取 发票联系电话。
     */
    public String getReceiptMobile() {
        return this.receiptMobile;
    }

    /**
     * 设置 发票联系电话。
     *
     * @param value 属性值
     */
    public void setReceiptMobile(String value) {
        this.receiptMobile = value;
    }

    private BigDecimal productAmount;

    /**
     * 获取 商品金额，等于订单中所有的商品的单价乘以数量之和。
     */
    public BigDecimal getProductAmount() {
        return this.productAmount;
    }

    /**
     * 设置 商品金额，等于订单中所有的商品的单价乘以数量之和。
     *
     * @param value 属性值
     */
    public void setProductAmount(BigDecimal value) {
        this.productAmount = value;
    }

    private BigDecimal orderAmount;

    /**
     * 获取 订单总金额，等于商品总金额＋运费。
     */
    public BigDecimal getOrderAmount() {
        return this.orderAmount;
    }

    /**
     * 设置 订单总金额，等于商品总金额＋运费。
     *
     * @param value 属性值
     */
    public void setOrderAmount(BigDecimal value) {
        this.orderAmount = value;
    }

    private BigDecimal paidBalance;

    /**
     * 获取 余额账户支付总金额。
     */
    public BigDecimal getPaidBalance() {
        return this.paidBalance;
    }

    /**
     * 设置 余额账户支付总金额。
     *
     * @param value 属性值
     */
    public void setPaidBalance(BigDecimal value) {
        this.paidBalance = value;
    }

    private BigDecimal giftCardAmount;

    /**
     * 获取 礼品卡抵用金额。
     */
    public BigDecimal getGiftCardAmount() {
        return this.giftCardAmount;
    }

    /**
     * 设置 礼品卡抵用金额。
     *
     * @param value 属性值
     */
    public void setGiftCardAmount(BigDecimal value) {
        this.giftCardAmount = value;
    }

    private BigDecimal paidAmount;

    /**
     * 获取 已支付金额。
     */
    public BigDecimal getPaidAmount() {
        return this.paidAmount;
    }

    /**
     * 设置 已支付金额。
     *
     * @param value 属性值
     */
    public void setPaidAmount(BigDecimal value) {
        this.paidAmount = value;
    }

    private BigDecimal shippingAmount;

    /**
     * 获取 淘宝运费。
     */
    public BigDecimal getShippingAmount() {
        return this.shippingAmount;
    }

    /**
     * 设置 淘宝运费。
     *
     * @param value 属性值
     */
    public void setShippingAmount(BigDecimal value) {
        this.shippingAmount = value;
    }

    private BigDecimal totalEsAmount;

    /**
     * 获取 网单中总的节能补贴金额之和。
     */
    public BigDecimal getTotalEsAmount() {
        return this.totalEsAmount;
    }

    /**
     * 设置 网单中总的节能补贴金额之和。
     *
     * @param value 属性值
     */
    public void setTotalEsAmount(BigDecimal value) {
        this.totalEsAmount = value;
    }

    private BigDecimal usedCustomerBalanceAmount;

    /**
     * 获取 使用的客户的余额支付金额。
     */
    public BigDecimal getUsedCustomerBalanceAmount() {
        return this.usedCustomerBalanceAmount;
    }

    /**
     * 设置 使用的客户的余额支付金额。
     *
     * @param value 属性值
     */
    public void setUsedCustomerBalanceAmount(BigDecimal value) {
        this.usedCustomerBalanceAmount = value;
    }

    private Integer customerId;

    /**
     * 获取 用余额支付的客户ID。
     */
    public Integer getCustomerId() {
        return this.customerId;
    }

    /**
     * 设置 用余额支付的客户ID。
     *
     * @param value 属性值
     */
    public void setCustomerId(Integer value) {
        this.customerId = value;
    }

    private String bestShippingTime;

    /**
     * 获取 最佳配送时间描述。
     */
    public String getBestShippingTime() {
        return this.bestShippingTime;
    }

    /**
     * 设置 最佳配送时间描述。
     *
     * @param value 属性值
     */
    public void setBestShippingTime(String value) {
        this.bestShippingTime = value;
    }

    private String paymentCode;

    /**
     * 获取 支付方式code。
     */
    public String getPaymentCode() {
        return this.paymentCode;
    }

    /**
     * 设置 支付方式code。
     *
     * @param value 属性值
     */
    public void setPaymentCode(String value) {
        this.paymentCode = value;
    }

    private String payBankCode;

    /**
     * 获取 网银代码。
     */
    public String getPayBankCode() {
        return this.payBankCode;
    }

    /**
     * 设置 网银代码。
     *
     * @param value 属性值
     */
    public void setPayBankCode(String value) {
        this.payBankCode = value;
    }

    private String paymentName;

    /**
     * 获取 支付方式名称。
     */
    public String getPaymentName() {
        return this.paymentName;
    }

    /**
     * 设置 支付方式名称。
     *
     * @param value 属性值
     */
    public void setPaymentName(String value) {
        this.paymentName = value;
    }

    private String consignee;

    /**
     * 获取 收货人。
     */
    public String getConsignee() {
        return this.consignee;
    }

    /**
     * 设置 收货人。
     *
     * @param value 属性值
     */
    public void setConsignee(String value) {
        this.consignee = value;
    }

    private String originRegionName;

    /**
     * 获取 原淘宝收货地址信息。
     */
    public String getOriginRegionName() {
        return this.originRegionName;
    }

    /**
     * 设置 原淘宝收货地址信息。
     *
     * @param value 属性值
     */
    public void setOriginRegionName(String value) {
        this.originRegionName = value;
    }

    private String originAddress;

    /**
     * 获取 原淘宝收货人详细收货信息。
     */
    public String getOriginAddress() {
        return this.originAddress;
    }

    /**
     * 设置 原淘宝收货人详细收货信息。
     *
     * @param value 属性值
     */
    public void setOriginAddress(String value) {
        this.originAddress = value;
    }

    private Integer province;

    /**
     * 获取 收货地址中国省份。
     */
    public Integer getProvince() {
        return this.province;
    }

    /**
     * 设置 收货地址中国省份。
     *
     * @param value 属性值
     */
    public void setProvince(Integer value) {
        this.province = value;
    }

    private Integer city;

    /**
     * 获取 收货地址中的城市。
     */
    public Integer getCity() {
        return this.city;
    }

    /**
     * 设置 收货地址中的城市。
     *
     * @param value 属性值
     */
    public void setCity(Integer value) {
        this.city = value;
    }

    private Integer region;

    /**
     * 获取 收货地址中城市中的区。
     */
    public Integer getRegion() {
        return this.region;
    }

    /**
     * 设置 收货地址中城市中的区。
     *
     * @param value 属性值
     */
    public void setRegion(Integer value) {
        this.region = value;
    }

    private Integer street;

    /**
     * 获取 街道ID。
     */
    public Integer getStreet() {
        return this.street;
    }

    /**
     * 设置 街道ID。
     *
     * @param value 属性值
     */
    public void setStreet(Integer value) {
        this.street = value;
    }

    private Integer markBuilding;

    /**
     * 获取 标志建筑物。
     */
    public Integer getMarkBuilding() {
        return this.markBuilding;
    }

    /**
     * 设置 标志建筑物。
     *
     * @param value 属性值
     */
    public void setMarkBuilding(Integer value) {
        this.markBuilding = value;
    }

    private String poiId;

    /**
     * 获取 标建ID。
     */
    public String getPoiId() {
        return this.poiId;
    }

    /**
     * 设置 标建ID。
     *
     * @param value 属性值
     */
    public void setPoiId(String value) {
        this.poiId = value;
    }

    private String poiName;

    /**
     * 获取 标建名称。
     */
    public String getPoiName() {
        return this.poiName;
    }

    /**
     * 设置 标建名称。
     *
     * @param value 属性值
     */
    public void setPoiName(String value) {
        this.poiName = value;
    }

    private String regionName;

    /**
     * 获取 地区名称（如：北京 北京 昌平区 兴寿镇）。
     */
    public String getRegionName() {
        return this.regionName;
    }

    /**
     * 设置 地区名称（如：北京 北京 昌平区 兴寿镇）。
     *
     * @param value 属性值
     */
    public void setRegionName(String value) {
        this.regionName = value;
    }

    private String address;

    /**
     * 获取 收货地址中用户输入的地址，一般是区以下的详细地址。
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 设置 收货地址中用户输入的地址，一般是区以下的详细地址。
     *
     * @param value 属性值
     */
    public void setAddress(String value) {
        this.address = value;
    }

    private String zipcode;

    /**
     * 获取 收货地址中的邮编。
     */
    public String getZipcode() {
        return this.zipcode;
    }

    /**
     * 设置 收货地址中的邮编。
     *
     * @param value 属性值
     */
    public void setZipcode(String value) {
        this.zipcode = value;
    }

    private String mobile;

    /**
     * 获取 收货人手机号。
     */
    public String getMobile() {
        return this.mobile;
    }

    /**
     * 设置 收货人手机号。
     *
     * @param value 属性值
     */
    public void setMobile(String value) {
        this.mobile = value;
    }

    private String phone;

    /**
     * 获取 收货人固定电话号。
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * 设置 收货人固定电话号。
     *
     * @param value 属性值
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    private String receiptInfo;

    /**
     * 获取 发票信息，序列化数组array('title' =>.., 'receiptType' =>..,'needReceipt' => ..,'companyName' =>..,'taxSpotNum' =>..,'regAddress'=>..,'regPhone'=>..,'bank'=>..,'bankAccount'=>..)。
     */
    public String getReceiptInfo() {
        return this.receiptInfo;
    }

    /**
     * 设置 发票信息，序列化数组array('title' =>.., 'receiptType' =>..,'needReceipt' => ..,'companyName' =>..,'taxSpotNum' =>..,'regAddress'=>..,'regPhone'=>..,'bank'=>..,'bankAccount'=>..)。
     *
     * @param value 属性值
     */
    public void setReceiptInfo(String value) {
        this.receiptInfo = value;
    }

    private Integer delayShipTime;

    /**
     * 获取 延迟发货日期。
     */
    public Integer getDelayShipTime() {
        return this.delayShipTime;
    }

    /**
     * 设置 延迟发货日期。
     *
     * @param value 属性值
     */
    public void setDelayShipTime(Integer value) {
        this.delayShipTime = value;
    }

    private String remark;

    /**
     * 获取 订单备注。
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置 订单备注。
     *
     * @param value 属性值
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    private String bankCode;

    /**
     * 获取 银行代码,用于银行直链支付。
     */
    public String getBankCode() {
        return this.bankCode;
    }

    /**
     * 设置 银行代码,用于银行直链支付。
     *
     * @param value 属性值
     */
    public void setBankCode(String value) {
        this.bankCode = value;
    }

    private String agent;

    /**
     * 获取 处理人。
     */
    public String getAgent() {
        return this.agent;
    }

    /**
     * 设置 处理人。
     *
     * @param value 属性值
     */
    public void setAgent(String value) {
        this.agent = value;
    }

    private Integer confirmTime;

    /**
     * 获取 确认时间。
     */
    public Integer getConfirmTime() {
        return this.confirmTime;
    }

    /**
     * 设置 确认时间。
     *
     * @param value 属性值
     */
    public void setConfirmTime(Integer value) {
        this.confirmTime = value;
    }

    private Integer firstConfirmTime;

    /**
     * 获取 首次确认时间。
     */
    public Integer getFirstConfirmTime() {
        return this.firstConfirmTime;
    }

    /**
     * 设置 首次确认时间。
     *
     * @param value 属性值
     */
    public void setFirstConfirmTime(Integer value) {
        this.firstConfirmTime = value;
    }

    private String firstConfirmPerson;

    /**
     * 获取 第一次确认人。
     */
    public String getFirstConfirmPerson() {
        return this.firstConfirmPerson;
    }

    /**
     * 设置 第一次确认人。
     *
     * @param value 属性值
     */
    public void setFirstConfirmPerson(String value) {
        this.firstConfirmPerson = value;
    }

    private Integer finishTime;

    /**
     * 获取 订单完成时间。
     */
    public Integer getFinishTime() {
        return this.finishTime;
    }

    /**
     * 设置 订单完成时间。
     *
     * @param value 属性值
     */
    public void setFinishTime(Integer value) {
        this.finishTime = value;
    }

    private String tradeSn;

    /**
     * 获取 在线支付交易流水号。
     */
    public String getTradeSn() {
        return this.tradeSn;
    }

    /**
     * 设置 在线支付交易流水号。
     *
     * @param value 属性值
     */
    public void setTradeSn(String value) {
        this.tradeSn = value;
    }

    private String signCode;

    /**
     * 获取 收货确认码。
     */
    public String getSignCode() {
        return this.signCode;
    }

    /**
     * 设置 收货确认码。
     *
     * @param value 属性值
     */
    public void setSignCode(String value) {
        this.signCode = value;
    }

    private String source;

    /**
     * 获取 订单来源。
     */
    public String getSource() {
        return this.source;
    }

    /**
     * 设置 订单来源。
     *
     * @param value 属性值
     */
    public void setSource(String value) {
        this.source = value;
    }

    private String sourceOrderSn;

    /**
     * 获取 外部订单号。
     */
    public String getSourceOrderSn() {
        return this.sourceOrderSn;
    }

    /**
     * 设置 外部订单号。
     *
     * @param value 属性值
     */
    public void setSourceOrderSn(String value) {
        this.sourceOrderSn = value;
    }

    private Integer onedayLimit = 0;

    /**
     * 获取 是否支持24小时限时达。
     */
    public Integer getOnedayLimit() {
        return this.onedayLimit;
    }

    /**
     * 设置 是否支持24小时限时达。
     *
     * @param value 属性值
     */
    public void setOnedayLimit(Integer value) {
        this.onedayLimit = value;
    }

    private Integer logisticsManner;

    /**
     * 获取 物流评价。
     */
    public Integer getLogisticsManner() {
        return this.logisticsManner;
    }

    /**
     * 设置 物流评价。
     *
     * @param value 属性值
     */
    public void setLogisticsManner(Integer value) {
        this.logisticsManner = value;
    }

    private Integer afterSaleManner;

    /**
     * 获取 售后评价。
     */
    public Integer getAfterSaleManner() {
        return this.afterSaleManner;
    }

    /**
     * 设置 售后评价。
     *
     * @param value 属性值
     */
    public void setAfterSaleManner(Integer value) {
        this.afterSaleManner = value;
    }

    private Integer personManner;

    /**
     * 获取 人员态度。
     */
    public Integer getPersonManner() {
        return this.personManner;
    }

    /**
     * 设置 人员态度。
     *
     * @param value 属性值
     */
    public void setPersonManner(Integer value) {
        this.personManner = value;
    }

    private String visitRemark;

    /**
     * 获取 回访备注。
     */
    public String getVisitRemark() {
        return this.visitRemark;
    }

    /**
     * 设置 回访备注。
     *
     * @param value 属性值
     */
    public void setVisitRemark(String value) {
        this.visitRemark = value;
    }

    private Integer visitTime;

    /**
     * 获取 回访时间。
     */
    public Integer getVisitTime() {
        return this.visitTime;
    }

    /**
     * 设置 回访时间。
     *
     * @param value 属性值
     */
    public void setVisitTime(Integer value) {
        this.visitTime = value;
    }

    private String visitPerson;

    /**
     * 获取 回访人。
     */
    public String getVisitPerson() {
        return this.visitPerson;
    }

    /**
     * 设置 回访人。
     *
     * @param value 属性值
     */
    public void setVisitPerson(String value) {
        this.visitPerson = value;
    }

    private String sellPeople;

    /**
     * 获取 销售代表。
     */
    public String getSellPeople() {
        return this.sellPeople;
    }

    /**
     * 设置 销售代表。
     *
     * @param value 属性值
     */
    public void setSellPeople(String value) {
        this.sellPeople = value;
    }

    private Integer sellPeopleManner;

    /**
     * 获取 销售代表服务态度。
     */
    public Integer getSellPeopleManner() {
        return this.sellPeopleManner;
    }

    /**
     * 设置 销售代表服务态度。
     *
     * @param value 属性值
     */
    public void setSellPeopleManner(Integer value) {
        this.sellPeopleManner = value;
    }

    private String orderType;

    /**
     * 获取 订单类型 默认0 团购预付款 团购正式单 2。
     */
    public String getOrderType() {
        return this.orderType;
    }

    /**
     * 设置 订单类型 默认0 团购预付款 团购正式单 2。
     *
     * @param value 属性值
     */
    public void setOrderType(String value) {
        this.orderType = value;
    }

    private Integer hasReadTaobaoOrderComment;

    /**
     * 获取 是否已读取过淘宝订单评论。
     */
    public Integer getHasReadTaobaoOrderComment() {
        return this.hasReadTaobaoOrderComment;
    }

    /**
     * 设置 是否已读取过淘宝订单评论。
     *
     * @param value 属性值
     */
    public void setHasReadTaobaoOrderComment(Integer value) {
        this.hasReadTaobaoOrderComment = value;
    }

    private Integer memberInvoiceId;

    /**
     * 获取 订单发票ID,MemberInvoices表的主键。
     */
    public Integer getMemberInvoiceId() {
        return this.memberInvoiceId;
    }

    /**
     * 设置 订单发票ID,MemberInvoices表的主键。
     *
     * @param value 属性值
     */
    public void setMemberInvoiceId(Integer value) {
        this.memberInvoiceId = value;
    }

    private Integer taobaoGroupId;

    /**
     * 获取 淘宝万人团活动ID。
     */
    public Integer getTaobaoGroupId() {
        return this.taobaoGroupId;
    }

    /**
     * 设置 淘宝万人团活动ID。
     *
     * @param value 属性值
     */
    public void setTaobaoGroupId(Integer value) {
        this.taobaoGroupId = value;
    }

    private String tradeType;

    /**
     * 获取 交易类型,值参考淘宝。
     */
    public String getTradeType() {
        return this.tradeType;
    }

    /**
     * 设置 交易类型,值参考淘宝。
     *
     * @param value 属性值
     */
    public void setTradeType(String value) {
        this.tradeType = value;
    }

    private String stepTradeStatus;

    /**
     * 获取 分阶段付款的订单状态,值参考淘宝。
     */
    public String getStepTradeStatus() {
        return this.stepTradeStatus;
    }

    /**
     * 设置 分阶段付款的订单状态,值参考淘宝。
     *
     * @param value 属性值
     */
    public void setStepTradeStatus(String value) {
        this.stepTradeStatus = value;
    }

    private BigDecimal stepPaidFee;

    /**
     * 获取 分阶段付款的已付金额。
     */
    public BigDecimal getStepPaidFee() {
        return this.stepPaidFee;
    }

    /**
     * 设置 分阶段付款的已付金额。
     *
     * @param value 属性值
     */
    public void setStepPaidFee(BigDecimal value) {
        this.stepPaidFee = value;
    }

    private BigDecimal depositAmount;

    /**
     * 获取 定金应付金额。
     */
    public BigDecimal getDepositAmount() {
        return this.depositAmount;
    }

    /**
     * 设置 定金应付金额。
     *
     * @param value 属性值
     */
    public void setDepositAmount(BigDecimal value) {
        this.depositAmount = value;
    }

    private BigDecimal balanceAmount;

    /**
     * 获取 尾款应付金额。
     */
    public BigDecimal getBalanceAmount() {
        return this.balanceAmount;
    }

    /**
     * 设置 尾款应付金额。
     *
     * @param value 属性值
     */
    public void setBalanceAmount(BigDecimal value) {
        this.balanceAmount = value;
    }

    private Integer autoCancelDays;

    /**
     * 获取 未付款过期的天数。
     */
    public Integer getAutoCancelDays() {
        return this.autoCancelDays;
    }

    /**
     * 设置 未付款过期的天数。
     *
     * @param value 属性值
     */
    public void setAutoCancelDays(Integer value) {
        this.autoCancelDays = value;
    }

    private Integer isNoLimitStockOrder;

    /**
     * 获取 是否是无库存限制订单。
     */
    public Integer getIsNoLimitStockOrder() {
        return this.isNoLimitStockOrder;
    }

    /**
     * 设置 是否是无库存限制订单。
     *
     * @param value 属性值
     */
    public void setIsNoLimitStockOrder(Integer value) {
        this.isNoLimitStockOrder = value;
    }

    private Integer ccbOrderReceivedLogId;

    /**
     * 获取 建行订单接收日志ID。
     */
    public Integer getCcbOrderReceivedLogId() {
        return this.ccbOrderReceivedLogId;
    }

    /**
     * 设置 建行订单接收日志ID。
     *
     * @param value 属性值
     */
    public void setCcbOrderReceivedLogId(Integer value) {
        this.ccbOrderReceivedLogId = value;
    }

    private String ip;

    /**
     * 获取 订单来源IP,针对商城前台订单。
     */
    public String getIp() {
        return this.ip;
    }

    /**
     * 设置 订单来源IP,针对商城前台订单。
     *
     * @param value 属性值
     */
    public void setIp(String value) {
        this.ip = value;
    }

    private Integer isGiftCardOrder;

    /**
     * 获取 是否为礼品卡订单。
     */
    public Integer getIsGiftCardOrder() {
        return this.isGiftCardOrder;
    }

    /**
     * 设置 是否为礼品卡订单。
     *
     * @param value 属性值
     */
    public void setIsGiftCardOrder(Integer value) {
        this.isGiftCardOrder = value;
    }

    private String giftCardDownloadPassword;

    /**
     * 获取 礼品卡下载密码。
     */
    public String getGiftCardDownloadPassword() {
        return this.giftCardDownloadPassword;
    }

    /**
     * 设置 礼品卡下载密码。
     *
     * @param value 属性值
     */
    public void setGiftCardDownloadPassword(String value) {
        this.giftCardDownloadPassword = value;
    }

    private String giftCardFindMobile;

    /**
     * 获取 礼品卡密码找回手机号。
     */
    public String getGiftCardFindMobile() {
        return this.giftCardFindMobile;
    }

    /**
     * 设置 礼品卡密码找回手机号。
     *
     * @param value 属性值
     */
    public void setGiftCardFindMobile(String value) {
        this.giftCardFindMobile = value;
    }

    private Integer autoConfirmNum;

    /**
     * 获取 已自动确认的次数。
     */
    public Integer getAutoConfirmNum() {
        return this.autoConfirmNum;
    }

    /**
     * 设置 已自动确认的次数。
     *
     * @param value 属性值
     */
    public void setAutoConfirmNum(Integer value) {
        this.autoConfirmNum = value;
    }

    private String codConfirmPerson;

    /**
     * 获取 货到付款确认人。
     */
    public String getCodConfirmPerson() {
        return this.codConfirmPerson;
    }

    /**
     * 设置 货到付款确认人。
     *
     * @param value 属性值
     */
    public void setCodConfirmPerson(String value) {
        this.codConfirmPerson = value;
    }

    private Integer codConfirmTime;

    private String sureTime;
    private String firstTureTime;

    /**
     * 获取 货到付款确认时间。
     */
    public Integer getCodConfirmTime() {
        return this.codConfirmTime;
    }

    /**
     * 设置 货到付款确认时间。
     *
     * @param value 属性值
     */
    public void setCodConfirmTime(Integer value) {
        this.codConfirmTime = value;
    }

    private String codConfirmRemark;

    /**
     * 获取 货到付款确认备注。
     */
    public String getCodConfirmRemark() {
        return this.codConfirmRemark;
    }

    /**
     * 设置 货到付款确认备注。
     *
     * @param value 属性值
     */
    public void setCodConfirmRemark(String value) {
        this.codConfirmRemark = value;
    }

    private Integer codConfirmState;

    /**
     * 获取 货到侍确认状态0无需未确认,1待确认,2确认通过可以发货,3确认无效,订单可以取消。
     */
    public Integer getCodConfirmState() {
        return this.codConfirmState;
    }

    /**
     * 设置 货到侍确认状态0无需未确认,1待确认,2确认通过可以发货,3确认无效,订单可以取消。
     *
     * @param value 属性值
     */
    public void setCodConfirmState(Integer value) {
        this.codConfirmState = value;
    }

    private String paymentNoticeUrl;

    /**
     * 获取 付款结果通知URL。
     */
    public String getPaymentNoticeUrl() {
        return this.paymentNoticeUrl;
    }

    /**
     * 设置 付款结果通知URL。
     *
     * @param value 属性值
     */
    public void setPaymentNoticeUrl(String value) {
        this.paymentNoticeUrl = value;
    }

    private BigDecimal addressLon;

    /**
     * 获取 地址经度。
     */
    public BigDecimal getAddressLon() {
        return this.addressLon;
    }

    /**
     * 设置 地址经度。
     *
     * @param value 属性值
     */
    public void setAddressLon(BigDecimal value) {
        this.addressLon = value;
    }

    private BigDecimal addressLat;

    /**
     * 获取 地址纬度。
     */
    public BigDecimal getAddressLat() {
        return this.addressLat;
    }

    /**
     * 设置 地址纬度。
     *
     * @param value 属性值
     */
    public void setAddressLat(BigDecimal value) {
        this.addressLat = value;
    }

    private Integer smConfirmStatus;

    /**
     * 获取 标建确认状态。1 = 初始状态；2 = 已发HP，等待确认；3 = 待人工处理；4 = 待自动处理；5 = 已确认。
     */
    public Integer getSmConfirmStatus() {
        return this.smConfirmStatus;
    }

    /**
     * 设置 标建确认状态。1 = 初始状态；2 = 已发HP，等待确认；3 = 待人工处理；4 = 待自动处理；5 = 已确认。
     *
     * @param value 属性值
     */
    public void setSmConfirmStatus(Integer value) {
        this.smConfirmStatus = value;
    }

    private Integer smConfirmTime;

    /**
     * 获取 请求发送HP时间，格式为时间戳。
     */
    public Integer getSmConfirmTime() {
        return this.smConfirmTime;
    }

    /**
     * 设置 请求发送HP时间，格式为时间戳。
     *
     * @param value 属性值
     */
    public void setSmConfirmTime(Integer value) {
        this.smConfirmTime = value;
    }

    private Integer smManualTime = 0;

    /**
     * 获取 转人工确认时间。
     */
    public Integer getSmManualTime() {
        return this.smManualTime;
    }

    /**
     * 设置 转人工确认时间。
     *
     * @param value 属性值
     */
    public void setSmManualTime(Integer value) {
        this.smManualTime = value;
    }

    private String smManualRemark;

    /**
     * 获取 转人工确认备注。
     */
    public String getSmManualRemark() {
        return this.smManualRemark;
    }

    /**
     * 设置 转人工确认备注。
     *
     * @param value 属性值
     */
    public void setSmManualRemark(String value) {
        this.smManualRemark = value;
    }

    private Integer isTogether;

    /**
     * 获取 货票通行。
     */
    public Integer getIsTogether() {
        return this.isTogether;
    }

    /**
     * 设置 货票通行。
     *
     * @param value 属性值
     */
    public void setIsTogether(Integer value) {
        this.isTogether = value;
    }

    private Integer isNotConfirm = 0;

    /**
     * 获取 是否是无需确认的订单。
     */
    public Integer getIsNotConfirm() {
        return this.isNotConfirm;
    }

    /**
     * 设置 是否是无需确认的订单。
     *
     * @param value 属性值
     */
    public void setIsNotConfirm(Integer value) {
        this.isNotConfirm = value;
    }

    //  积分
    private Long points;

    /**
     * 获取 积分
     */
    public Long getPoints() {
        return points;
    }

    /**
     * 设置 积分
     *
     * @param points 属性值
     */
    public void setPoints(Long points) {
        this.points = points;
    }

    //尾款付款时间
    private long tailPayTime;

    /**
     * 获取 尾款付款时间
     */
    public long getTailPayTime() {
        return tailPayTime;
    }

    /**
     * 设置 尾款付款时间
     *
     * @param tailPayTime 属性值
     */
    public void setTailPayTime(long tailPayTime) {
        this.tailPayTime = tailPayTime;
    }

    //是否日日单(1:是，0:否)
    private Integer isProduceDaily;

    /**
     * 获取 是否日日单
     */
    public Integer getIsProduceDaily() {
        return isProduceDaily;
    }

    /**
     * 设置 是否日日单
     *
     * @param isProduceDaily 属性值
     */
    public void setIsProduceDaily(Integer isProduceDaily) {
        this.isProduceDaily = isProduceDaily;
    }

    private String couponCode = "";

    /**
     * 获取 优惠码。
     */
    public String getCouponCode() {
        return this.couponCode;
    }

    /**
     * 设置 优惠码。
     *
     * @param value 属性值
     */
    public void setCouponCode(String value) {
        this.couponCode = value;
    }

    private BigDecimal couponCodeValue = BigDecimal.ZERO;

    /**
     * 获取 优惠码金额。
     */
    public BigDecimal getCouponCodeValue() {
        return this.couponCodeValue;
    }

    /**
     * 设置 优惠码金额。
     *
     * @param value 属性值
     */
    public void setCouponCodeValue(BigDecimal value) {
        this.couponCodeValue = value;
    }

    private String ckCode;

    /**
     * 获取 微店主id（记录memberId，标识微店主的订单）
     * @return
     */
    public String getCkCode() {
        return ckCode;
    }

    /**
     * 设置 微店主id（记录memberId，标识微店主的订单）
     * @param ckCode
     */
    public void setCkCode(String ckCode) {
        this.ckCode = ckCode;
    }

    private Date modified; //最后更新时间

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    private Integer channelId; //区分EP和商城

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    private Long addTimeStartS; // 订单生成时间开始（查询用）
    private Long addTimeEndS; // 订单生成时间结束（查询用）
    private String addTimeStart; // 订单生成时间开始（查询用）
    private String addTimeEnd; // 订单生成时间结束（查询用）

    public String getAddTimeStart() {
        return addTimeStart;
    }

    public void setAddTimeStart(String addTimeStart) {
        this.addTimeStart = addTimeStart;
    }

    public String getAddTimeEnd() {
        return addTimeEnd;
    }

    public void setAddTimeEnd(String addTimeEnd) {
        this.addTimeEnd = addTimeEnd;
    }

    public Long getAddTimeStartS() {
        return addTimeStartS;
    }

    public void setAddTimeStartS(Long addTimeStartS) {
        this.addTimeStartS = addTimeStartS;
    }

    public Long getAddTimeEndS() {
        return addTimeEndS;
    }

    public void setAddTimeEndS(Long addTimeEndS) {
        this.addTimeEndS = addTimeEndS;
    }

    public String invoiceType = "普通发票"; // 发票类型

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String operation = null;

    public void setOperation(String operation){
        this.operation = operation;
    }
    //天猫子订单号
    private String oid;
    //订单取消状态
    private int cancelStatus;
    //来源名
    private String channelName;
    //取消状态操作
    public String cancelOperation = null;
    //物流编号
    private String expressNo;

    public void setCancelOperation(String cancelOperation){
        this.cancelOperation = cancelOperation;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public int getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(int cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public String getSureTime() {
        return sureTime;
    }

    public void setSureTime(String sureTime) {
        this.sureTime = sureTime;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getFirstTureTime() {
        return firstTureTime;
    }

    public void setFirstTureTime(String firstTureTime) {
        this.firstTureTime = firstTureTime;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getExportOrderSn() {
        return exportOrderSn;
    }

    public void setExportOrderSn(String exportOrderSn) {
        this.exportOrderSn = exportOrderSn;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


}