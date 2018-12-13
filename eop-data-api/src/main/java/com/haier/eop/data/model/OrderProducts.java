package com.haier.eop.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


//在table中前标‘---’为新增字段，前标‘//’为在原来基础上减去的字段

/**
 * <p>Table: <strong>OrderProducts</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 * <tr style="background-color:#ddd;Text-align:Left;">
 * <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 * </tr>
 * <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 * // *   <tr><td>siteId</td><td>{@link Integer}</td><td>siteId</td><td>int</td><td>&nbsp;</td></tr>
 * <tr><td>isTest</td><td>{@link Integer}</td><td>isTest</td><td>tinyint</td><td>是否是测试网单</td></tr>
 * // *   <tr><td>hasRead</td><td>{@link Integer}</td><td>hasRead</td><td>tinyint</td><td>是否已读,测试字段</td></tr>
 * <tr><td>supportOneDayLimit</td><td>{@link Integer}</td><td>supportOneDayLimit</td><td>tinyint</td><td>是否支持24小时限时达</td></tr>
 * <tr><td>orderId</td><td>{@link Integer}</td><td>orderId</td><td>int</td><td>&nbsp;</td></tr>
 * <tr><td>cOrderSn</td><td>{@link String}</td><td>cOrderSn</td><td>varchar</td><td>child order sn 子订单编码 C0919293</td></tr>
 * <tr><td>isBook</td><td>{@link Integer}</td><td>isBook</td><td>tinyint</td><td>是否是预订网单</td></tr>
 * <tr><td>cPaymentStatus</td><td>{@link Integer}</td><td>cPaymentStatus</td><td>smallint</td><td>子订单付款状态</td></tr>
 * <tr><td>cPayTime</td><td>{@link Integer}</td><td>cPayTime</td><td>int</td><td>子订单付款时间</td></tr>
 * <tr><td>productType</td><td>{@link Integer}</td><td>productType</td><td>int</td><td>商品类型</td></tr>
 * <tr><td>productId</td><td>{@link Integer}</td><td>productId</td><td>int</td><td>抽象商品id（可能是商品规格，也可能是套装，由商品类型决定）</td></tr>
 * <tr><td>productName</td><td>{@link String}</td><td>productName</td><td>varchar</td><td>商品名称：可能是商品名称加颜色规格，也可能是套装名称</td></tr>
 * <tr><td>sku</td><td>{@link String}</td><td>sku</td><td>varchar</td><td>货号</td></tr>
 * <tr><td>price</td><td>{@link BigDecimal}</td><td>price</td><td>decimal</td><td>商品单价</td></tr>
 * <tr><td>number</td><td>{@link Integer}</td><td>number</td><td>smallint</td><td>数量</td></tr>
 * <tr><td>lockedNumber</td><td>{@link Integer}</td><td>lockedNumber</td><td>int</td><td>曾经锁定的库存数量</td></tr>
 * <tr><td>unlockedNumber</td><td>{@link Integer}</td><td>unlockedNumber</td><td>int</td><td>曾经解锁的库存数量</td></tr>
 * <tr><td>productAmount</td><td>{@link BigDecimal}</td><td>productAmount</td><td>decimal</td><td>此字段专为同步外部订单而加，商品总金额=price*number+shippingFee-优惠金额，但优惠金额没在本系统存储</td></tr>
 * // *   <tr><td>balanceAmount</td><td>{@link BigDecimal}</td><td>balanceAmount</td><td>decimal</td><td>余额扣减</td></tr>
 * <tr><td>couponAmount</td><td>{@link BigDecimal}</td><td>couponAmount</td><td>decimal</td><td>优惠券抵扣金额</td></tr>
 * <tr><td>jfbAmount</td><td>{@link BigDecimal}</td><td>jfbAmount</td><td>decimal</td><td>天猫集分宝</td></tr>
 * <tr><td>djAmount</td><td>{@link BigDecimal}</td><td>djAmount</td><td>decimal</td><td>天猫点券</td></tr>
 * <tr><td>hbAmount</td><td>{@link BigDecimal}</td><td>hbAmount</td><td>decimal</td><td>天猫红包</td></tr>
 * <tr><td>esAmount</td><td>{@link BigDecimal}</td><td>esAmount</td><td>decimal</td><td>节能补贴金额</td></tr>
 * // *   <tr><td>giftCardNumberId</td><td>{@link Integer}</td><td>giftCardNumberId</td><td>int</td><td>礼品卡号ID,关联GiftCardNumbers表的主键</td></tr>
 * // *   <tr><td>usedGiftCardAmount</td><td>{@link BigDecimal}</td><td>usedGiftCardAmount</td><td>decimal</td><td>礼品卡抵用的金额</td></tr>
 * // *   <tr><td>couponLogId</td><td>{@link Integer}</td><td>couponLogId</td><td>int</td><td>使用的优惠券记录ID</td></tr>
 * // *   <tr><td>activityPrice</td><td>{@link BigDecimal}</td><td>activityPrice</td><td>decimal</td><td>活动价，当有活动价时price的值来源于activityPrice</td></tr>
 * // *   <tr><td>activityId</td><td>{@link Integer}</td><td>activityId</td><td>int</td><td>活动ID</td></tr>
 * <tr><td>cateId</td><td>{@link Integer}</td><td>cateId</td><td>int</td><td>分类ID</td></tr>
 * <tr><td>brandId</td><td>{@link Integer}</td><td>brandId</td><td>int</td><td>品牌ID</td></tr>
 * <tr><td>netPointId</td><td>{@link Integer}</td><td>netPointId</td><td>int</td><td>网点id</td></tr>
 * <tr><td>shippingFee</td><td>{@link BigDecimal}</td><td>shippingFee</td><td>decimal</td><td>配送费用</td></tr>
 * <tr><td>settlementStatus</td><td>{@link Integer}</td><td>settlementStatus</td><td>tinyint</td><td>结算状态0 未结算 1已结算 </td></tr>
 * <tr><td>receiptOrRejectTime</td><td>{@link Integer}</td><td>receiptOrRejectTime</td><td>int</td><td>确认收货时间或拒绝收货时间</td></tr>
 * <tr><td>isWmsSku</td><td>{@link Integer}</td><td>isWmsSku</td><td>tinyint</td><td>是否淘宝小家电</td></tr>
 * <tr><td>sCode</td><td>{@link String}</td><td>sCode</td><td>varchar</td><td>库位编码</td></tr>
 * <tr><td>tsCode</td><td>{@link String}</td><td>tsCode</td><td>varchar</td><td>转运库位编码</td></tr>
 * <tr><td>status</td><td>{@link Integer}</td><td>status</td><td>smallint</td><td>状态</td></tr>
 * // *   <tr><td>productSn</td><td>{@link String}</td><td>productSn</td><td>varchar</td><td>商品条形码</td></tr>
 * <tr><td>invoiceNumber</td><td>{@link String}</td><td>invoiceNumber</td><td>varchar</td><td>运单号</td></tr>
 * <tr><td>expressName</td><td>{@link String}</td><td>expressName</td><td>varchar</td><td>快递公司</td></tr>
 * <tr><td>invoiceExpressNumber</td><td>{@link String}</td><td>invoiceExpressNumber</td><td>varchar</td><td>发票快递单号</td></tr>
 * // *   <tr><td>postMan</td><td>{@link String}</td><td>postMan</td><td>varchar</td><td>送货人</td></tr>
 * // *   <tr><td>postManPhone</td><td>{@link String}</td><td>postManPhone</td><td>varchar</td><td>送货人电话</td></tr>
 * // *   <tr><td>isNotice</td><td>{@link Integer}</td><td>isNotice</td><td>smallint</td><td>是否开启预警</td></tr>
 * // *   <tr><td>noticeType</td><td>{@link Integer}</td><td>noticeType</td><td>smallint</td><td>&nbsp;</td></tr>
 * // *   <tr><td>noticeRemark</td><td>{@link String}</td><td>noticeRemark</td><td>varchar</td><td>&nbsp;</td></tr>
 * // *   <tr><td>noticeTime</td><td>{@link String}</td><td>noticeTime</td><td>varchar</td><td>预警时间</td></tr>
 * <tr><td>shippingTime</td><td>{@link Integer}</td><td>shippingTime</td><td>int</td><td>发货时间</td></tr>
 * <tr><td>lessOrderSn</td><td>{@link String}</td><td>lessOrderSn</td><td>varchar</td><td>less 订单号</td></tr>
 * <tr><td>waitGetLesShippingInfo</td><td>{@link Integer}</td><td>waitGetLesShippingInfo</td><td>tinyint</td><td>是否等待获取LES物流配送节点信息</td></tr>
 * <tr><td>getLesShippingCount</td><td>{@link Integer}</td><td>getLesShippingCount</td><td>int</td><td>已获取LES配送节点信息的次数</td></tr>
 * <tr><td>outping</td><td>{@link String}</td><td>outping</td><td>varchar</td><td>出库凭证</td></tr>
 * <tr><td>lessShipTime</td><td>{@link Integer}</td><td>lessShipTime</td><td>int</td><td>less出库时间</td></tr>
 * <tr><td>closeTime</td><td>{@link Integer}</td><td>closeTime</td><td>int</td><td>网单完成关闭或取消关闭时间</td></tr>
 * <tr><td>isReceipt</td><td>{@link Integer}</td><td>isReceipt</td><td>int</td><td>是否需要发票</td></tr>
 * <tr><td>isMakeReceipt</td><td>{@link Integer}</td><td>isMakeReceipt</td><td>int</td><td>开票状态</td></tr>
 * <tr><td>receiptNum</td><td>{@link String}</td><td>receiptNum</td><td>varchar</td><td>发票号</td></tr>
 * <tr><td>receiptAddTime</td><td>{@link String}</td><td>receiptAddTime</td><td>varchar</td><td>开票时间</td></tr>
 * <tr><td>makeReceiptType</td><td>{@link Integer}</td><td>makeReceiptType</td><td>tinyint</td><td>开票类型 0 初始值 1 库房开票  2 共享开票</td></tr>
 * <tr><td>shippingMode</td><td>{@link String}</td><td>shippingMode</td><td>varchar</td><td>物流模式,值为B2B2C或B2C</td></tr>
 * <tr><td>lastTimeForShippingMode</td><td>{@link Integer}</td><td>lastTimeForShippingMode</td><td>int</td><td>最后修改物流模式的时间</td></tr>
 * <tr><td>lastEditorForShippingMode</td><td>{@link String}</td><td>lastEditorForShippingMode</td><td>varchar</td><td>最后修改物流模式的管理员</td></tr>
 * <tr><td>systemRemark</td><td>{@link String}</td><td>systemRemark</td><td>text</td><td>系统备注，不给用户显示</td></tr>
 * // *   <tr><td>tongshuaiWorkId</td><td>{@link Integer}</td><td>tongshuaiWorkId</td><td>int</td><td>统帅定制作品ID</td></tr>
 * // *   <tr><td>orderPromotionId</td><td>{@link Integer}</td><td>orderPromotionId</td><td>int</td><td>下单立减活动ID</td></tr>
 * // *   <tr><td>orderPromotionAmount</td><td>{@link BigDecimal}</td><td>orderPromotionAmount</td><td>decimal</td><td>下单立减金额</td></tr>
 * <tr><td>externalSaleSettingId</td><td>{@link Integer}</td><td>externalSaleSettingId</td><td>int</td><td>淘宝套装设置ID（数据库默认为null）</td></tr>
 * // *   <tr><td>recommendationId</td><td>{@link Integer}</td><td>recommendationId</td><td>int</td><td>推荐购买ID</td></tr>
 * // *   <tr><td>hasSendAlertNum</td><td>{@link Integer}</td><td>hasSendAlertNum</td><td>tinyint</td><td>是否已发送了购买数据报警邮件(短信)</td></tr>
 * <tr><td>isNoLimitStockProduct</td><td>{@link Integer}</td><td>isNoLimitStockProduct</td><td>tinyint</td><td>是否是无限制库存量的商品</td></tr>
 * <tr><td>shippingOpporunity</td><td>{@link Integer}</td><td>shippingOpporunity</td><td>tinyint</td><td>活动订单发货时机，0 可发货 1 已付订金，不发货 2 已付尾款，可发货</td></tr>
 * <tr><td>isTimeoutFree</td><td>{@link Integer}</td><td>isTimeoutFree</td><td>tinyint</td><td>是否超时免单 0未设置 1是 2否</td></tr>
 * --- *   <tr><td>addTime</td><td>{@link Date}</td><td>addTime</td><td>tinyint</td><td>网单创建时间</td></tr>
 * --- *   <tr><td>modified</td><td>{@link Date}</td><td>modified</td><td>tinyint</td><td>网单最后更新时间</td></tr>
 * --- *   <tr><td>oid</td><td>{@link String}</td><td>oid</td><td>varchar</td><td>天猫子订单号</td></tr>
 * --- *   <tr><td>tzSku</td><td>{@link String}</td><td>tzSku</td><td>varchar</td><td>套装sku（数据库默认为NULL）</td></tr>
 * <tr><td>isTz</td><td>{@link String}</td><td>isTz</td><td>varchar</td><td>是否是套装商品</td></tr>
 * </table>
 *
 * @author rongmai
 */
public class OrderProducts implements Serializable {
	
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
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -8582599429762817975L;
    private Integer id;
    private Integer isTest;    //是否是测试网单
    private Integer supportOneDayLimit;//是否支持24小时限时达
    private Integer orderId;// 订单id
    private String cOrderSn;//child order sn 子订单编码 WD09192932323
    private Integer isBook = 0;// 是否是预订网单
    private Integer cPaymentStatus;//子订单付款状态
    private Long cPayTime;//子订单付款时间
    private Integer productType;//商品类型
    private Integer productId;//抽象商品id（可能是商品规格，也可能是套装，由商品类型决定）
    private String productName;//商品名称：可能是商品名称加颜色规格，也可能是套装名称
    private String sku;// 货号
    private BigDecimal price;// 商品单价
    private Long number;// 数量
    private Integer lockedNumber;//曾经锁定的库存数量
    private Integer unlockedNumber;//曾经解锁的库存数量
    private BigDecimal productAmount;//此字段专为同步外部订单而加，商品总金额=price*number+shippingFee-优惠金额，但优惠金额没在本系统存储
    //    private BigDecimal  balanceAmount;//余额扣减
    private BigDecimal couponAmount;//优惠券抵扣金额
    private BigDecimal jfbAmount = new BigDecimal(0);//集分宝抵扣金额
    private BigDecimal djAmount = new BigDecimal(0);//点券抵扣金额
    private BigDecimal hbAmount = new BigDecimal(0);//红包抵扣金额
    private BigDecimal esAmount = new BigDecimal(0);//节能补贴金额
    //    private BigDecimal  activityPrice;//活动价，当有活动价时price的值来源于activityPrice
    private Integer cateId;//分类ID
    private Integer brandId;// 品牌ID
    private Integer netPointId;//网点id
    private BigDecimal shippingFee;// 配送费用
    private Integer settlementStatus;//结算状态0 未结算 1已结算
    private Integer receiptOrRejectTime;//确认收货时间或拒绝收货时间
    private Integer isWmsSku;//是否淘宝小家电
    private String sCode;// 库位编码
    private String cbsSecCode;// cbs库位
    private String tsCode;// 转运库位编码
    private Integer status;// 状态 (OrderProducgtStatus)
    private String invoiceNumber;// 运单号
    private String expressName;// 快递公司
    private String invoiceExpressNumber;// 发票快递单号
    private Integer shippingTime;//发货时间
    private String lessOrderSn;//less 订单号
    private Integer waitGetLesShippingInfo;//是否等待获取LES物流配送节点信息
    private Integer getLesShippingCount;// 已获取LES配送节点信息的次数
    private String outping;// 出库凭证
    private Integer lessShipTime;// less出库时间
    private Integer closeTime;// 网单完成关闭或取消关闭时间
    private Integer isReceipt = 0;//是否需要发票
    private Integer isMakeReceipt = 1;// 开票状态
    private String receiptNum;// 发票号
    private String receiptAddTime;//开票时间
    private Integer makeReceiptType;//开票类型 0 初始值 1 库房开票  2 共享开票
    private String shippingMode;//物流模式,值为B2B2C或B2C
    private Integer lastTimeForShippingMode;//最后修改物流模式的时间
    private String lastEditorForShippingMode;// 最后修改物流模式的管理员
    private String systemRemark;// 系统备注，不给用户显示
    private Integer externalSaleSettingId;// 淘宝套装设置ID
    private Integer isNoLimitStockProduct;//是否是无限制库存量的商品
    private Integer shippingOpporunity; //活动订单发货时机 0 可发货 1 已付订金，不发货 2 已付尾款，可发货
    private Integer isTimeoutFree; //是否超时免单 0未设置 1是  2否
    private Long points; // 积分
    private Integer splitFlag;// 拆单标志，0：未拆单；1：拆单后旧单；2：拆单后新单
    private String splitRelateCOrderSn;//拆单关联单号
    private Integer pdOrderStatus; //日日单状态
    private String scode;//为样品机接口单独添加属性
    private Orders orders;// 网单对应的订单
    private String stockType = "WA";//网单库存类型，WA和3W
    private Integer o2oType = 1;    //o2o类型。
    private Integer storeId = 0;//店铺ID。
    private Integer storeType = 0;//店铺类型
    private String oid; //天猫子订单号
    private Date addTime;//网单的创建时间
    private Date modified;// 网单的最后更新时间
    private String tzSku;//套装sku（数据库默认为NULL）
    //临时字段
    private OrderProductsItem orderProductsItem;


    /**
     * 拆单标志-未拆单
     */
    public static final Integer SPLITFLAG_SPLIT_NOT = 0;
    /**
     * 拆单标志-拆单后旧单
     */
    public static final Integer SPLITFLAG_SPLIT_OLD = 1;
    /**
     * 拆单标志-拆单后新单
     */
    public static final Integer SPLITFLAG_SPLIT_NEW = 2;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取 child order sn 子订单编码 C0919293
     */
    public String getCOrderSn() {
        return this.cOrderSn;
    }

    /**
     * 设置 child order sn 子订单编码 C0919293
     *
     * @param value 属性值
     */
    public void setCOrderSn(String value) {
        this.cOrderSn = value;
    }

    public Integer getIsBook() {
        return isBook;
    }

    public void setIsBook(Integer isBook) {
        this.isBook = isBook;
    }


    /**
     * 获取 子订单付款状态
     */
    public Integer getCPaymentStatus() {
        return this.cPaymentStatus;
    }

    /**
     * 设置 子订单付款状态
     *
     * @param value 属性值
     */
    public void setCPaymentStatus(Integer value) {
        this.cPaymentStatus = value;
    }


    /**
     * 获取 子订单付款时间
     */
    public Long getCPayTime() {
        return this.cPayTime;
    }

    /**
     * 设置 子订单付款时间
     *
     * @param value 属性值
     */
    public void setCPayTime(Long value) {
        this.cPayTime = value;
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

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
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

    public BigDecimal getJfbAmount() {
        return jfbAmount;
    }

    public void setJfbAmount(BigDecimal jfbAmount) {
        this.jfbAmount = jfbAmount;
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

    public BigDecimal getEsAmount() {
        return esAmount;
    }

    public void setEsAmount(BigDecimal esAmount) {
        this.esAmount = esAmount;
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

    public String getCbsSecCode() {
        return cbsSecCode;
    }

    public void setCbsSecCode(String cbsSecCode) {
        this.cbsSecCode = cbsSecCode;
    }

    public String getTsCode() {
        return tsCode;
    }

    public void setTsCode(String tsCode) {
        this.tsCode = tsCode;
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

    public Integer getShippingOpporunity() {
        return shippingOpporunity;
    }

    public void setShippingOpporunity(Integer shippingOpporunity) {
        this.shippingOpporunity = shippingOpporunity;
    }

    public Integer getIsTimeoutFree() {
        return isTimeoutFree;
    }

    public void setIsTimeoutFree(Integer isTimeoutFree) {
        this.isTimeoutFree = isTimeoutFree;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
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

    public Integer getPdOrderStatus() {
        return pdOrderStatus;
    }

    public void setPdOrderStatus(Integer pdOrderStatus) {
        this.pdOrderStatus = pdOrderStatus;
    }


    /**
     * 获取 库位编码
     */
    public String getSCode() {
        return this.sCode;
    }

    /**
     * 设置 库位编码
     *
     * @param value 属性值
     */
    public void setSCode(String value) {
        this.sCode = value;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public Integer getO2oType() {
        return o2oType;
    }

    public void setO2oType(Integer o2oType) {
        this.o2oType = o2oType;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getStoreType() {
        return storeType;
    }

    public void setStoreType(Integer storeType) {
        this.storeType = storeType;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getTzSku() {
        return tzSku;
    }

    public void setTzSku(String tzSku) {
        this.tzSku = tzSku;
    }


    private int isTz;//是否是套装

    public int getIsTz() {
        return isTz;
    }

    public void setIsTz(int isTz) {
        this.isTz = isTz;
    }

    public OrderProductsItem getOrderProductsItem() {
        return orderProductsItem;
    }

    public void setOrderProductsItem(OrderProductsItem orderProductsItem) {
        this.orderProductsItem = orderProductsItem;
    }

    private String tbOrderSn;
    private Integer isCd;

	public String getTbOrderSn() {
		return tbOrderSn;
	}

	public void setTbOrderSn(String tbOrderSn) {
		this.tbOrderSn = tbOrderSn;
	}

	public Integer getIsCd() {
		return isCd;
	}

	public void setIsCd(Integer isCd) {
		this.isCd = isCd;
	}
}