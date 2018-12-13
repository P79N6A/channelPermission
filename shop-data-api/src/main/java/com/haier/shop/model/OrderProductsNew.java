package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/*
* 作者:张波
* 2017/12/19
* */
public class OrderProductsNew implements Serializable{
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -2000204085719845292L;

    /**
     * 网单状态 -  初始状态
     */
    public static Integer STATUS_STRAT_STATUS      = 0;
    /**
     * 网单状态 -  已占用库存
     */
    public static Integer STATUS_FROZEN_STOCK      = 1;
    /**
     * 网单状态 -  同步到HP
     */
    public static Integer STATUS_SYNC_HP           = 2;
    /**
     * 网单状态 -  同步到EC
     */
    public static Integer STATUS_SYNC_EC           = 3;
    /**
     * 网单状态 -  已分配网点
     */
    public static Integer STATUS_DISPATCH_NETPOINT = 4;
    /**
     * 网单状态 -  LES 开提单, 待出库
     */
    public static Integer STATUS_LES_SHIPPING      = 8;
    /**
     * 网单状态 -  待审核
     */
    public static Integer STATUS_WAIT_VERIFY       = 10;
    /**
     * 网单状态 -  待转运入库
     */
    public static Integer STATUS_WAIT_TRANSSHIPIN  = 11;
    /**
     * 网单状态 -  待转运出库
     */
    public static Integer STATUS_WAIT_TRANSSHIPOUT = 12;
    /**
     * 网单状态 -  待发货
     */
    public static Integer STATUS_WAIT_DELIVERY     = 40;
    /**
     * 网单状态 -  网点拒单
     */
    public static Integer STATUS_NETPOINT_REFUSE   = 150;
    /**
     * 网单状态 -  待交付
     */
    public static Integer STATUS_WAIT_DELIVER      = 70;
    /**
     * 网单状态 -  完成关闭
     */
    public static Integer STATUS_COMPLETED_CLOSE   = 130;
    /**
     * 网单状态 -  用户签收
     */
    public static Integer STATUS_USER_SIGN         = 140;
    /**
     * 网单状态 -  用户拒收
     */
    public static Integer STATUS_USER_REJECTION    = 160;
    /**
     * 网单状态 -  取消关闭
     */
    public static Integer STATUS_CANCEL_CLOSE      = 110;
    /**
     * 日日单状态 -  普通单初始值
     */
    public static Integer RRSSTATUS_DEFAULT        = 0;

    /**
     * 日日单状态 -  日日单初始值
     */
    public static Integer RRSSTATUS_INIT = 1;

    /**
     * 日日单状态 -  生产中
     */
    public static Integer RRSSTATUS_PRODUCTION = 10;

    /**
     * 日日单状态 - 工厂已发货
     */
    public static Integer getRRSSTATUS_FACTORY_HAS_DELIVERY = 12;

    /**
     * 日日单状态 -  已入库
     */
    public static Integer RRSSTATUS_STOCK_IN = 50;

    /**
     * 日日单状态-  已通知用户
     */
    public static Integer RRSSTATUS_NOTICE_USER = 60;

    /**
     * 日日单状态 -  完成关闭
     */
    public static Integer RRSSTATUS_FINISH_CLOSE = 100;

    /**
     * 日日单状态 -  取消关闭
     */
    public static Integer       RRSSTATUS_CANCEL_CLOSE = 110;
    /**
     * 拆单标志-未拆单
     */
    public static final Integer SPLITFLAG_SPLIT_NOT    = 0;
    /**
     * 拆单标志-拆单后旧单
     */
    public static final Integer SPLITFLAG_SPLIT_OLD    = 1;
    /**
     * 拆单标志-拆单后新单
     */
    public static final Integer SPLITFLAG_SPLIT_NEW    = 2;
    /**
     * 网单类型-(默认)WA:自由库存网单；
     */
    public static final String  TYPE_WA                = "WA";
    /**
     * 网单类型-STORE:店铺网单；
     */
    public static final String  TYPE_STORE             = "STORE";
    /**
     * 网单类型-O2O网单；
     */
    public static final String  TYPE_O2O               = "O2O";

    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer siteId;

    public Integer getSiteId() {
        return this.siteId;
    }

    public void setSiteId(Integer value) {
        this.siteId = value;
    }

    private Integer isTest;

    /**
     * 获取 是否是测试网单。
     */
    public Integer getIsTest() {
        return this.isTest;
    }

    /**
     * 设置 是否是测试网单。
     *
     * @param value 属性值
     */
    public void setIsTest(Integer value) {
        this.isTest = value;
    }

    private Integer hasRead = 0;

    /**
     * 获取 是否已读,测试字段。
     */
    public Integer getHasRead() {
        return this.hasRead;
    }

    /**
     * 设置 是否已读,测试字段。
     *
     * @param value 属性值
     */
    public void setHasRead(Integer value) {
        this.hasRead = value;
    }

    private Integer supportOneDayLimit;

    /**
     * 获取 是否支持24小时限时达。
     */
    public Integer getSupportOneDayLimit() {
        return this.supportOneDayLimit;
    }

    /**
     * 设置 是否支持24小时限时达。
     *
     * @param value 属性值
     */
    public void setSupportOneDayLimit(Integer value) {
        this.supportOneDayLimit = value;
    }

    private Integer orderId;

    public Integer getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Integer value) {
        this.orderId = value;
    }

    private String cOrderSn;

    /**
     * 获取 child order sn 子订单编码 C0919293。
     */
    public String getCOrderSn() {
        return this.cOrderSn;
    }

    /**
     * 设置 child order sn 子订单编码 C0919293。
     *
     * @param value 属性值
     */
    public void setCOrderSn(String value) {
        this.cOrderSn = value;
    }

    private Integer isBook = 0;

    /**
     * 获取 是否是预订网单。
     */
    public Integer getIsBook() {
        return this.isBook;
    }

    /**
     * 设置 是否是预订网单。
     *
     * @param value 属性值
     */
    public void setIsBook(Integer value) {
        this.isBook = value;
    }

    private Integer cPaymentStatus;

    /**
     * 获取 子订单付款状态。
     */
    public Integer getCPaymentStatus() {
        return this.cPaymentStatus;
    }

    /**
     * 设置 子订单付款状态。
     *
     * @param value 属性值
     */
    public void setCPaymentStatus(Integer value) {
        this.cPaymentStatus = value;
    }

    private Long cPayTime;

    /**
     * 获取 子订单付款时间。
     */
    public Long getCPayTime() {
        return this.cPayTime;
    }

    /**
     * 设置 子订单付款时间。
     *
     * @param value 属性值
     */
    public void setCPayTime(Long value) {
        this.cPayTime = value;
    }

    private Integer productType;

    /**
     * 获取 商品类型。
     */
    public Integer getProductType() {
        return this.productType;
    }

    /**
     * 设置 商品类型。
     *
     * @param value 属性值
     */
    public void setProductType(Integer value) {
        this.productType = value;
    }

    private Integer productId;

    /**
     * 获取 抽象商品id（可能是商品规格，也可能是套装，由商品类型决定）。
     */
    public Integer getProductId() {
        return this.productId;
    }

    /**
     * 设置 抽象商品id（可能是商品规格，也可能是套装，由商品类型决定）。
     *
     * @param value 属性值
     */
    public void setProductId(Integer value) {
        this.productId = value;
    }

    private String productName;

    /**
     * 获取 商品名称：可能是商品名称加颜色规格，也可能是套装名称。
     */
    public String getProductName() {
        return this.productName;
    }

    /**
     * 设置 商品名称：可能是商品名称加颜色规格，也可能是套装名称。
     *
     * @param value 属性值
     */
    public void setProductName(String value) {
        this.productName = value;
    }

    private String sku;

    /**
     * 获取 货号。
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * 设置 货号。
     *
     * @param value 属性值
     */
    public void setSku(String value) {
        this.sku = value;
    }

    private BigDecimal price;

    /**
     * 获取 商品单价。
     */
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * 设置 商品单价。
     *
     * @param value 属性值
     */
    public void setPrice(BigDecimal value) {
        this.price = value;
    }

    private Integer number;

    /**
     * 获取 数量。
     */
    public Integer getNumber() {
        return this.number;
    }

    /**
     * 设置 数量。
     *
     * @param value 属性值
     */
    public void setNumber(Integer value) {
        this.number = value;
    }

    private Integer lockedNumber;

    /**
     * 获取 曾经锁定的库存数量。
     */
    public Integer getLockedNumber() {
        return this.lockedNumber;
    }

    /**
     * 设置 曾经锁定的库存数量。
     *
     * @param value 属性值
     */
    public void setLockedNumber(Integer value) {
        this.lockedNumber = value;
    }

    private Integer unlockedNumber;

    /**
     * 获取 曾经解锁的库存数量。
     */
    public Integer getUnlockedNumber() {
        return this.unlockedNumber;
    }

    /**
     * 设置 曾经解锁的库存数量。
     *
     * @param value 属性值
     */
    public void setUnlockedNumber(Integer value) {
        this.unlockedNumber = value;
    }

    private BigDecimal productAmount;

    /**
     * 获取 此字段专为同步外部订单而加，商品总金额=price*number+shippingFee-优惠金额，但优惠金额没在本系统存储。
     */
    public BigDecimal getProductAmount() {
        return this.productAmount;
    }

    /**
     * 设置 此字段专为同步外部订单而加，商品总金额=price*number+shippingFee-优惠金额，但优惠金额没在本系统存储。
     *
     * @param value 属性值
     */
    public void setProductAmount(BigDecimal value) {
        this.productAmount = value;
    }

    private BigDecimal balanceAmount;

    /**
     * 获取 余额扣减。
     */
    public BigDecimal getBalanceAmount() {
        return this.balanceAmount;
    }

    /**
     * 设置 余额扣减。
     *
     * @param value 属性值
     */
    public void setBalanceAmount(BigDecimal value) {
        this.balanceAmount = value;
    }

    private BigDecimal couponAmount;

    /**
     * 获取 优惠券抵扣金额。
     */
    public BigDecimal getCouponAmount() {
        return this.couponAmount;
    }

    /**
     * 设置 优惠券抵扣金额。
     *
     * @param value 属性值
     */
    public void setCouponAmount(BigDecimal value) {
        this.couponAmount = value;
    }

    private BigDecimal esAmount;

    /**
     * 获取 节能补贴金额。
     */
    public BigDecimal getEsAmount() {
        return this.esAmount;
    }

    /**
     * 设置 节能补贴金额。
     *
     * @param value 属性值
     */
    public void setEsAmount(BigDecimal value) {
        this.esAmount = value;
    }

    private Integer giftCardNumberId;

    /**
     * 获取 礼品卡号ID,关联GiftCardNumbers表的主键。
     */
    public Integer getGiftCardNumberId() {
        return this.giftCardNumberId;
    }

    /**
     * 设置 礼品卡号ID,关联GiftCardNumbers表的主键。
     *
     * @param value 属性值
     */
    public void setGiftCardNumberId(Integer value) {
        this.giftCardNumberId = value;
    }

    private BigDecimal usedGiftCardAmount;

    /**
     * 获取 礼品卡抵用的金额。
     */
    public BigDecimal getUsedGiftCardAmount() {
        return this.usedGiftCardAmount;
    }

    /**
     * 设置 礼品卡抵用的金额。
     *
     * @param value 属性值
     */
    public void setUsedGiftCardAmount(BigDecimal value) {
        this.usedGiftCardAmount = value;
    }

    private Integer couponLogId;

    /**
     * 获取 使用的优惠券记录ID。
     */
    public Integer getCouponLogId() {
        return this.couponLogId;
    }

    /**
     * 设置 使用的优惠券记录ID。
     *
     * @param value 属性值
     */
    public void setCouponLogId(Integer value) {
        this.couponLogId = value;
    }

    private BigDecimal activityPrice;

    /**
     * 获取 活动价，当有活动价时price的值来源于activityPrice。
     */
    public BigDecimal getActivityPrice() {
        return this.activityPrice;
    }

    /**
     * 设置 活动价，当有活动价时price的值来源于activityPrice。
     *
     * @param value 属性值
     */
    public void setActivityPrice(BigDecimal value) {
        this.activityPrice = value;
    }

    private Integer activityId;

    /**
     * 获取 活动ID。
     */
    public Integer getActivityId() {
        return this.activityId;
    }

    /**
     * 设置 活动ID。
     *
     * @param value 属性值
     */
    public void setActivityId(Integer value) {
        this.activityId = value;
    }

    private Integer cateId;

    /**
     * 获取 分类ID。
     */
    public Integer getCateId() {
        return this.cateId;
    }

    /**
     * 设置 分类ID。
     *
     * @param value 属性值
     */
    public void setCateId(Integer value) {
        this.cateId = value;
    }

    private Integer brandId;

    /**
     * 获取 品牌ID。
     */
    public Integer getBrandId() {
        return this.brandId;
    }

    /**
     * 设置 品牌ID。
     *
     * @param value 属性值
     */
    public void setBrandId(Integer value) {
        this.brandId = value;
    }

    private Integer netPointId;

    /**
     * 获取 网点id。
     */
    public Integer getNetPointId() {
        return this.netPointId;
    }

    /**
     * 设置 网点id。
     *
     * @param value 属性值
     */
    public void setNetPointId(Integer value) {
        this.netPointId = value;
    }

    private BigDecimal shippingFee;

    /**
     * 获取 配送费用。
     */
    public BigDecimal getShippingFee() {
        return this.shippingFee;
    }

    /**
     * 设置 配送费用。
     *
     * @param value 属性值
     */
    public void setShippingFee(BigDecimal value) {
        this.shippingFee = value;
    }

    private Integer settlementStatus;

    /**
     * 获取 结算状态0 未结算 1已结算 。
     */
    public Integer getSettlementStatus() {
        return this.settlementStatus;
    }

    /**
     * 设置 结算状态0 未结算 1已结算 。
     *
     * @param value 属性值
     */
    public void setSettlementStatus(Integer value) {
        this.settlementStatus = value;
    }

    private Long receiptOrRejectTime;

    /**
     * 获取 确认收货时间或拒绝收货时间。
     */
    public Long getReceiptOrRejectTime() {
        return this.receiptOrRejectTime;
    }

    /**
     * 设置 确认收货时间或拒绝收货时间。
     *
     * @param value 属性值
     */
    public void setReceiptOrRejectTime(Long value) {
        this.receiptOrRejectTime = value;
    }

    private Integer isWmsSku;

    /**
     * 获取 是否淘宝小家电。
     */
    public Integer getIsWmsSku() {
        return this.isWmsSku;
    }

    /**
     * 设置 是否淘宝小家电。
     *
     * @param value 属性值
     */
    public void setIsWmsSku(Integer value) {
        this.isWmsSku = value;
    }

    private String sCode;

    /**
     * 获取 库位编码。
     */
    public String getSCode() {
        return this.sCode;
    }

    /**
     * 设置 库位编码。
     *
     * @param value 属性值
     */
    public void setSCode(String value) {
        this.sCode = value;
    }

    private String tsCode;

    /**
     * 获取 转运库房编码。
     */
    public String getTsCode() {
        return this.tsCode;
    }

    /**
     * 设置 转运库房编码。
     *
     * @param value 属性值
     */
    public void setTsCode(String value) {
        this.tsCode = value;
    }

    private Integer tsShippingTime = 0;

    /**
     * 获取 转运时效（小时）。
     */
    public Integer getTsShippingTime() {
        return this.tsShippingTime;
    }

    /**
     * 设置 转运时效（小时）。
     *
     * @param value 属性值
     */
    public void setTsShippingTime(Integer value) {
        this.tsShippingTime = value;
    }

    private Integer status;

    /**
     * 获取 状态。
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置 状态。
     *
     * @param value 属性值
     */
    public void setStatus(Integer value) {
        this.status = value;
    }

    private String productSn;

    /**
     * 获取 商品条形码。
     */
    public String getProductSn() {
        return this.productSn;
    }

    /**
     * 设置 商品条形码。
     *
     * @param value 属性值
     */
    public void setProductSn(String value) {
        this.productSn = value;
    }

    private String invoiceNumber;

    /**
     * 获取 运单号。
     */
    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    /**
     * 设置 运单号。
     *
     * @param value 属性值
     */
    public void setInvoiceNumber(String value) {
        this.invoiceNumber = value;
    }

    private String expressName;

    /**
     * 获取 快递公司。
     */
    public String getExpressName() {
        return this.expressName;
    }

    /**
     * 设置 快递公司。
     *
     * @param value 属性值
     */
    public void setExpressName(String value) {
        this.expressName = value;
    }

    private String invoiceExpressNumber;

    /**
     * 获取 发票快递单号。
     */
    public String getInvoiceExpressNumber() {
        return this.invoiceExpressNumber;
    }

    /**
     * 设置 发票快递单号。
     *
     * @param value 属性值
     */
    public void setInvoiceExpressNumber(String value) {
        this.invoiceExpressNumber = value;
    }

    private String postMan;

    /**
     * 获取 送货人。
     */
    public String getPostMan() {
        return this.postMan;
    }

    /**
     * 设置 送货人。
     *
     * @param value 属性值
     */
    public void setPostMan(String value) {
        this.postMan = value;
    }

    private String postManPhone;

    /**
     * 获取 送货人电话。
     */
    public String getPostManPhone() {
        return this.postManPhone;
    }

    /**
     * 设置 送货人电话。
     *
     * @param value 属性值
     */
    public void setPostManPhone(String value) {
        this.postManPhone = value;
    }

    private Integer isNotice;

    /**
     * 获取 是否开启预警。
     */
    public Integer getIsNotice() {
        return this.isNotice;
    }

    /**
     * 设置 是否开启预警。
     *
     * @param value 属性值
     */
    public void setIsNotice(Integer value) {
        this.isNotice = value;
    }

    private Integer noticeType;

    public Integer getNoticeType() {
        return this.noticeType;
    }

    public void setNoticeType(Integer value) {
        this.noticeType = value;
    }

    private String noticeRemark;

    public String getNoticeRemark() {
        return this.noticeRemark;
    }

    public void setNoticeRemark(String value) {
        this.noticeRemark = value;
    }

    private String noticeTime;

    /**
     * 获取 预警时间。
     */
    public String getNoticeTime() {
        return this.noticeTime;
    }

    /**
     * 设置 预警时间。
     *
     * @param value 属性值
     */
    public void setNoticeTime(String value) {
        this.noticeTime = value;
    }

    private Long shippingTime;

    /**
     * 获取 发货时间。
     */
    public Long getShippingTime() {
        return this.shippingTime;
    }

    /**
     * 设置 发货时间。
     *
     * @param value 属性值
     */
    public void setShippingTime(Long value) {
        this.shippingTime = value;
    }

    private String lessOrderSn;

    /**
     * 获取 less 订单号。
     */
    public String getLessOrderSn() {
        return this.lessOrderSn;
    }

    /**
     * 设置 less 订单号。
     *
     * @param value 属性值
     */
    public void setLessOrderSn(String value) {
        this.lessOrderSn = value;
    }

    private Integer waitGetLesShippingInfo;

    /**
     * 获取 是否等待获取LES物流配送节点信息。
     */
    public Integer getWaitGetLesShippingInfo() {
        return this.waitGetLesShippingInfo;
    }

    /**
     * 设置 是否等待获取LES物流配送节点信息。
     *
     * @param value 属性值
     */
    public void setWaitGetLesShippingInfo(Integer value) {
        this.waitGetLesShippingInfo = value;
    }

    private Integer getLesShippingCount;

    /**
     * 获取 已获取LES配送节点信息的次数。
     */
    public Integer getGetLesShippingCount() {
        return this.getLesShippingCount;
    }

    /**
     * 设置 已获取LES配送节点信息的次数。
     *
     * @param value 属性值
     */
    public void setGetLesShippingCount(Integer value) {
        this.getLesShippingCount = value;
    }

    private String outping;

    /**
     * 获取 出库凭证。
     */
    public String getOutping() {
        return this.outping;
    }

    /**
     * 设置 出库凭证。
     *
     * @param value 属性值
     */
    public void setOutping(String value) {
        this.outping = value;
    }

    private Long lessShipTime;

    /**
     * 获取 less出库时间。
     */
    public Long getLessShipTime() {
        return this.lessShipTime;
    }

    /**
     * 设置 less出库时间。
     *
     * @param value 属性值
     */
    public void setLessShipTime(Long value) {
        this.lessShipTime = value;
    }

    private Long closeTime;

    /**
     * 获取 网单完成关闭或取消关闭时间。
     */
    public Long getCloseTime() {
        return this.closeTime;
    }

    /**
     * 设置 网单完成关闭或取消关闭时间。
     *
     * @param value 属性值
     */
    public void setCloseTime(Long value) {
        this.closeTime = value;
    }

    private Integer isReceipt = 0;

    /**
     * 获取 是否需要发票。
     */
    public Integer getIsReceipt() {
        return this.isReceipt;
    }

    /**
     * 设置 是否需要发票。
     *
     * @param value 属性值
     */
    public void setIsReceipt(Integer value) {
        this.isReceipt = value;
    }

    private Integer isMakeReceipt = 1;

    /**
     * 获取 开票状态。
     */
    public Integer getIsMakeReceipt() {
        return this.isMakeReceipt;
    }

    /**
     * 设置 开票状态。
     *
     * @param value 属性值
     */
    public void setIsMakeReceipt(Integer value) {
        this.isMakeReceipt = value;
    }

    private String receiptNum;

    /**
     * 获取 发票号。
     */
    public String getReceiptNum() {
        return this.receiptNum;
    }

    /**
     * 设置 发票号。
     *
     * @param value 属性值
     */
    public void setReceiptNum(String value) {
        this.receiptNum = value;
    }

    private String receiptAddTime;

    /**
     * 获取 开票时间。
     */
    public String getReceiptAddTime() {
        return this.receiptAddTime;
    }

    /**
     * 设置 开票时间。
     *
     * @param value 属性值
     */
    public void setReceiptAddTime(String value) {
        this.receiptAddTime = value;
    }

    private Integer makeReceiptType;

    /**
     * 获取 开票类型 0 初始值 1 库房开票  2 共享开票。
     */
    public Integer getMakeReceiptType() {
        return this.makeReceiptType;
    }

    /**
     * 设置 开票类型 0 初始值 1 库房开票  2 共享开票。
     *
     * @param value 属性值
     */
    public void setMakeReceiptType(Integer value) {
        this.makeReceiptType = value;
    }

    private String shippingMode;

    /**
     * 获取 物流模式,值为B2B2C或B2C。
     */
    public String getShippingMode() {
        return this.shippingMode;
    }

    /**
     * 设置 物流模式,值为B2B2C或B2C。
     *
     * @param value 属性值
     */
    public void setShippingMode(String value) {
        this.shippingMode = value;
    }

    private Long lastTimeForShippingMode;

    /**
     * 获取 最后修改物流模式的时间。
     */
    public Long getLastTimeForShippingMode() {
        return this.lastTimeForShippingMode;
    }

    /**
     * 设置 最后修改物流模式的时间。
     *
     * @param value 属性值
     */
    public void setLastTimeForShippingMode(Long value) {
        this.lastTimeForShippingMode = value;
    }

    private String lastEditorForShippingMode;

    /**
     * 获取 最后修改物流模式的管理员。
     */
    public String getLastEditorForShippingMode() {
        return this.lastEditorForShippingMode;
    }

    /**
     * 设置 最后修改物流模式的管理员。
     *
     * @param value 属性值
     */
    public void setLastEditorForShippingMode(String value) {
        this.lastEditorForShippingMode = value;
    }

    private String systemRemark;

    /**
     * 获取 系统备注，不给用户显示。
     */
    public String getSystemRemark() {
        return this.systemRemark;
    }

    /**
     * 设置 系统备注，不给用户显示。
     *
     * @param value 属性值
     */
    public void setSystemRemark(String value) {
        this.systemRemark = value;
    }

    private Integer tongshuaiWorkId = -1;

    /**
     * 获取 统帅定制作品ID。
     */
    public Integer getTongshuaiWorkId() {
        return this.tongshuaiWorkId;
    }

    /**
     * 设置 统帅定制作品ID。
     *
     * @param value 属性值
     */
    public void setTongshuaiWorkId(Integer value) {
        this.tongshuaiWorkId = value;
    }

    private Integer orderPromotionId;

    /**
     * 获取 下单立减活动ID。
     */
    public Integer getOrderPromotionId() {
        return this.orderPromotionId;
    }

    /**
     * 设置 下单立减活动ID。
     *
     * @param value 属性值
     */
    public void setOrderPromotionId(Integer value) {
        this.orderPromotionId = value;
    }

    private BigDecimal orderPromotionAmount;

    /**
     * 获取 下单立减金额。
     */
    public BigDecimal getOrderPromotionAmount() {
        return this.orderPromotionAmount;
    }

    /**
     * 设置 下单立减金额。
     *
     * @param value 属性值
     */
    public void setOrderPromotionAmount(BigDecimal value) {
        this.orderPromotionAmount = value;
    }

    private Integer externalSaleSettingId;

    /**
     * 获取 淘宝套装设置ID。
     */
    public Integer getExternalSaleSettingId() {
        return this.externalSaleSettingId;
    }

    /**
     * 设置 淘宝套装设置ID。
     *
     * @param value 属性值
     */
    public void setExternalSaleSettingId(Integer value) {
        this.externalSaleSettingId = value;
    }

    private Integer recommendationId;

    /**
     * 获取 推荐购买ID。
     */
    public Integer getRecommendationId() {
        return this.recommendationId;
    }

    /**
     * 设置 推荐购买ID。
     *
     * @param value 属性值
     */
    public void setRecommendationId(Integer value) {
        this.recommendationId = value;
    }

    private Integer hasSendAlertNum;

    /**
     * 获取 是否已发送了购买数据报警邮件(短信)。
     */
    public Integer getHasSendAlertNum() {
        return this.hasSendAlertNum;
    }

    /**
     * 设置 是否已发送了购买数据报警邮件(短信)。
     *
     * @param value 属性值
     */
    public void setHasSendAlertNum(Integer value) {
        this.hasSendAlertNum = value;
    }

    private Integer isNoLimitStockProduct;

    /**
     * 获取 是否是无限制库存量的商品。
     */
    public Integer getIsNoLimitStockProduct() {
        return this.isNoLimitStockProduct;
    }

    /**
     * 设置 是否是无限制库存量的商品。
     *
     * @param value 属性值
     */
    public void setIsNoLimitStockProduct(Integer value) {
        this.isNoLimitStockProduct = value;
    }

    private Integer hpRegisterDate = 0;

    /**
     * 获取 HP注册时间。
     */
    public Integer getHpRegisterDate() {
        return this.hpRegisterDate;
    }

    /**
     * 设置 HP注册时间。
     *
     * @param value 属性值
     */
    public void setHpRegisterDate(Integer value) {
        this.hpRegisterDate = value;
    }

    private Integer hpFailDate = 0;

    /**
     * 获取 HP派工失败时间。
     */
    public Integer getHpFailDate() {
        return this.hpFailDate;
    }

    /**
     * 设置 HP派工失败时间。
     *
     * @param value 属性值
     */
    public void setHpFailDate(Integer value) {
        this.hpFailDate = value;
    }

    private Integer hpFinishDate = 0;

    /**
     * 获取 HP派工成功时间。
     */
    public Integer getHpFinishDate() {
        return this.hpFinishDate;
    }

    /**
     * 设置 HP派工成功时间。
     *
     * @param value 属性值
     */
    public void setHpFinishDate(Integer value) {
        this.hpFinishDate = value;
    }

    private Integer hpReservationDate = 0;

    /**
     * 获取 HP回传预约送货时间。
     */
    public Integer getHpReservationDate() {
        return this.hpReservationDate;
    }

    /**
     * 设置 HP回传预约送货时间。
     *
     * @param value 属性值
     */
    public void setHpReservationDate(Integer value) {
        this.hpReservationDate = value;
    }

    private Integer shippingOpporunity = 0;

    /**
     * 获取 活动订单发货时机，0定金发货 1尾款发货。
     */
    public Integer getShippingOpporunity() {
        return this.shippingOpporunity;
    }

    /**
     * 设置 活动订单发货时机，0定金发货 1尾款发货。
     *
     * @param value 属性值
     */
    public void setShippingOpporunity(Integer value) {
        this.shippingOpporunity = value;
    }

    private Integer isTimeoutFree = 0;

    /**
     * 获取 是否超时免单 0未设置 1是 2否。
     */
    public Integer getIsTimeoutFree() {
        return this.isTimeoutFree;
    }

    /**
     * 设置 是否超时免单 0未设置 1是 2否。
     *
     * @param value 属性值
     */
    public void setIsTimeoutFree(Integer value) {
        this.isTimeoutFree = value;
    }

    private BigDecimal itemShareAmount = new BigDecimal("0.00");

    /**
     * 获取 订单优惠价格分摊。
     */
    public BigDecimal getItemShareAmount() {
        return this.itemShareAmount;
    }

    /**
     * 设置 订单优惠价格分摊。
     *
     * @param value 属性值
     */
    public void setItemShareAmount(BigDecimal value) {
        this.itemShareAmount = value;
    }

    private Long lessShipTInTime = 0L;

    /**
     * 获取 less转运入库时间。
     */
    public Long getLessShipTInTime() {
        return this.lessShipTInTime;
    }

    /**
     * 设置 less转运入库时间。
     *
     * @param value 属性值
     */
    public void setLessShipTInTime(Long value) {
        this.lessShipTInTime = value;
    }

    private Long lessShipTOutTime = 0L;

    /**
     * 获取 less转运出库时间。
     */
    public Long getLessShipTOutTime() {
        return this.lessShipTOutTime;
    }

    /**
     * 设置 less转运出库时间。
     *
     * @param value 属性值
     */
    public void setLessShipTOutTime(Long value) {
        this.lessShipTOutTime = value;
    }

    private String cbsSecCode;

    /**
     * 获取 cbs库位
     * @return
     */
    public String getCbsSecCode() {
        return cbsSecCode;
    }

    /**
     * 设置cbs 库位
     * @param cbsSecCode
     */
    public void setCbsSecCode(String cbsSecCode) {
        this.cbsSecCode = cbsSecCode;
    }

    private Integer activityId2;

    /**
     * 获取 活动ID2。
     */
    public Integer getActivityId2() {
        return this.activityId2;
    }

    /**
     * 设置 活动ID2。
     *
     * @param value 属性值
     */
    public void setActivityId2(Integer activityId2) {
        this.activityId2 = activityId2;
    }

    /**
     * 日日单状态
     */
    private Integer pdOrderStatus;

    /**
     * 获取 日日单状态
     * @return 日日单状态
     */
    public Integer getPdOrderStatus() {
        return pdOrderStatus;
    }

    /**
     * 设置 日日单状态
     * @param pdOrderStatus 日日单状态
     */
    public void setPdOrderStatus(Integer pdOrderStatus) {
        this.pdOrderStatus = pdOrderStatus;
    }

    /**
     * 集团OMS单号
     */
    private String omsOrderSn;

    /**
     * 获取 集团OMS单号
     * @return 集团OMS单号
     */
    public String getOmsOrderSn() {
        return omsOrderSn;
    }

    /**
     * 设置 集团OMS单号
     * @param omsOrderSn 集团OMS单号
     */
    public void setOmsOrderSn(String omsOrderSn) {
        this.omsOrderSn = omsOrderSn;
    }

    //拆单标志，0：未拆单；1：拆单后旧单；2：拆单后新单
    private Integer splitFlag;

    /**
     * 获取 拆单标志
     * @return 拆单标志
     */
    public Integer getSplitFlag() {
        return splitFlag;
    }

    /**
     * 设置 拆单标志
     * @param splitFlag 拆单标志
     */
    public void setSplitFlag(Integer splitFlag) {
        this.splitFlag = splitFlag;
    }

    //拆单关联单号
    private String splitRelateCOrderSn;

    /**
     * 获取 拆单关联单号
     * @return 拆单关联单号
     */
    public String getSplitRelateCOrderSn() {
        return splitRelateCOrderSn;
    }

    /**
     * 设置 拆单关联单号
     * @param splitRelateCOrderSn 拆单关联单号
     */
    public void setSplitRelateCOrderSn(String splitRelateCOrderSn) {
        this.splitRelateCOrderSn = splitRelateCOrderSn;
    }

    // 网单使用积分
    private Long points;

    /**
     * 获取 网单使用积分
     * @return 网单使用积分
     */
    public Long getPoints() {
        return points;
    }

    /**
     * 设置 网单使用积分
     * @param points 网单使用积分
     */
    public void setPoints(Long points) {
        this.points = points;
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

    private String stockType = "WA";

    /**
     * 获取网单类型。
     */
    public String getStockType() {
        return this.stockType;
    }

    /**
     * 设置 网单类型。
     *
     * @param value 属性值
     */
    public void setStockType(String value) {
        this.stockType = value;
    }

    private Integer o2oType = 1;

    /**
     * 获取o2o类型。
     * o2oType：1非O2O网单，2线下用户分销商城，3商城分销旗舰店，4创客，5线下销售旗舰店，50微店，60天猫分销旗舰店
     */
    public Integer getO2oType() {
        return this.o2oType;
    }

    /**
     * 设置 o2o类型。
     *
     * @param value 属性值
     */
    public void setO2oType(Integer value) {
        this.o2oType = value;
    }

    private Integer storeId = 0;

    /**
     * 获取店铺ID。
     */
    public Integer getStoreId() {
        return this.storeId;
    }

    /**
     * 设置店铺ID。
     *
     * @param value 属性值
     */
    public void setStoreId(Integer value) {
        this.storeId = value;
    }

    private Integer storeType = 0;

    /**
     * 获取店铺类型。
     */
    public Integer getStoreType() {
        return this.storeType;
    }

    /**
     * 设置店铺类型。
     *
     * @param value 属性值
     */
    public void setStoreType(Integer value) {
        this.storeType = value;
    }

    private String brokerageType;

    /**
     * 获取 佣金类型[微店订单]
     * @return
     */
    public String getBrokerageType() {
        return brokerageType;
    }

    /**
     * 设置 佣金类型[微店订单]
     * @param brokerageType
     */
    public void setBrokerageType(String brokerageType) {
        this.brokerageType = brokerageType;
    }

    /**
     * 红包
     */
    private BigDecimal hbAmount = BigDecimal.ZERO;

    public BigDecimal getHbAmount() {
        return hbAmount;
    }

    public void setHbAmount(BigDecimal hbAmount) {
        this.hbAmount = hbAmount;
    }

    /**
     * 集分宝
     */
    private BigDecimal jfbAmount = BigDecimal.ZERO;

    public BigDecimal getJfbAmount() {
        return jfbAmount;
    }

    public void setJfbAmount(BigDecimal jfbAmount) {
        this.jfbAmount = jfbAmount;
    }

    /**
     * 点卷
     */
    private BigDecimal djAmount = BigDecimal.ZERO;

    public BigDecimal getDjAmount() {
        return djAmount;
    }

    public void setDjAmount(BigDecimal djAmount) {
        this.djAmount = djAmount;
    }

    /**
     * 订单号
     */
    private String orderSn;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    /**
     * 类型名称
     */
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * 拆单标识
     */
    private Integer isCd;

    public Integer getIsCd() {
        return isCd;
    }

    public void setIsCd(Integer isCd) {
        this.isCd = isCd;
    }

    /**
     * tb单号
     */
    private String tbOrderSn;

    public String getTbOrderSn() {
        return tbOrderSn;
    }

    public void setTbOrderSn(String tbOrderSn) {
        this.tbOrderSn = tbOrderSn;
    }

    private String oid;

    private int isSelfSell;

    public int isSelfSell() {
        return isSelfSell;
    }

    public void setSelfSell(int selfSell) {
        isSelfSell = selfSell;
    }

    /**
     * 天猫子订单号
     * @return
     */
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}
