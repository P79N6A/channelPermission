package com.haier.eop.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>Table: <strong>Orders</strong>
 * <p><table class="er-mapping" cellspacing=0 cellpadding=0 style="border:solid 1 #666;padding:3px;">
 * <tr style="background-color:#ddd;Text-align:Left;">
 * <th nowrap>属性名</th><th nowrap>属性类型</th><th nowrap>字段名</th><th nowrap>字段类型</th><th nowrap>说明</th>
 * </tr>
 * <tr><td>id</td><td>{@link Integer}</td><td>id</td><td>int</td><td>&nbsp;</td></tr>
 * <tr><td>siteId</td><td>{@link Integer}</td><td>siteId</td><td>int</td><td>&nbsp;</td></tr>
 * <tr><td>isTest</td><td>{@link Integer}</td><td>isTest</td><td>tinyint</td><td>是否是测试订单</td></tr>
 * <tr><td>hasSync</td><td>{@link Integer}</td><td>hasSync</td><td>tinyint</td><td>是否已同步(临时添加)</td></tr>
 * <tr><td>isBackend</td><td>{@link Integer}</td><td>isBackend</td><td>tinyint</td><td>是否为后台添加的订单</td></tr>
 * <tr><td>isBook</td><td>{@link Integer}</td><td>isBook</td><td>tinyint</td><td>是否为预定订单</td></tr>
 * <tr><td>isCod</td><td>{@link Integer}</td><td>isCod</td><td>tinyint</td><td>是否是货到付款订单</td></tr>
 * <tr><td>notAutoConfirm</td><td>{@link Integer}</td><td>notAutoConfirm</td><td>tinyint</td><td>是否是非自动确认订单</td></tr>
 * <tr><td>isPackage</td><td>{@link Integer}</td><td>isPackage</td><td>tinyint</td><td>是否为套装订单</td></tr>
 * <tr><td>packageId</td><td>{@link Integer}</td><td>packageId</td><td>int</td><td>套装ID</td></tr>
 * <tr><td>orderSn</td><td>{@link String}</td><td>orderSn</td><td>varchar</td><td>订单号</td></tr>
 * <tr><td>relationOrderSn</td><td>{@link String}</td><td>relationOrderSn</td><td>varchar</td><td>关联订单编号</td></tr>
 * <tr><td>memberId</td><td>{@link Integer}</td><td>memberId</td><td>int</td><td>会员id</td></tr>
 * <tr><td>predictId</td><td>{@link Integer}</td><td>predictId</td><td>int</td><td>会员购买预测ID</td></tr>
 * <tr><td>memberEmail</td><td>{@link String}</td><td>memberEmail</td><td>varchar</td><td>会员邮件</td></tr>
 * <tr><td>addTime</td><td>{@link Integer}</td><td>addTime</td><td>int</td><td>&nbsp;</td></tr>
 * <tr><td>syncTime</td><td>{@link Integer}</td><td>syncTime</td><td>int</td><td>同步到此表中的时间</td></tr>
 * <tr><td>orderStatus</td><td>{@link Integer}</td><td>orderStatus</td><td>smallint</td><td>订单状态</td></tr>
 * <tr><td>payTime</td><td>{@link Integer}</td><td>payTime</td><td>int</td><td>在线付款时间</td></tr>
 * <tr><td>paymentStatus</td><td>{@link Integer}</td><td>paymentStatus</td><td>smallint</td><td>付款状态：0 买家未付款 1 买家已付款 </td></tr>
 * <tr><td>receiptConsignee</td><td>{@link String}</td><td>receiptConsignee</td><td>varchar</td><td>发票收件人</td></tr>
 * <tr><td>receiptAddress</td><td>{@link String}</td><td>receiptAddress</td><td>varchar</td><td>发票地址</td></tr>
 * <tr><td>receiptZipcode</td><td>{@link String}</td><td>receiptZipcode</td><td>varchar</td><td>发票邮编</td></tr>
 * <tr><td>receiptMobile</td><td>{@link String}</td><td>receiptMobile</td><td>varchar</td><td>发票联系电话</td></tr>
 * <tr><td>productAmount</td><td>{@link BigDecimal}</td><td>productAmount</td><td>decimal</td><td>商品金额，等于订单中所有的商品的单价乘以数量之和</td></tr>
 * <tr><td>orderAmount</td><td>{@link BigDecimal}</td><td>orderAmount</td><td>decimal</td><td>订单总金额，等于商品总金额＋运费</td></tr>
 * <tr><td>paidBalance</td><td>{@link BigDecimal}</td><td>paidBalance</td><td>decimal</td><td>余额账户支付总金额</td></tr>
 * <tr><td>giftCardAmount</td><td>{@link BigDecimal}</td><td>giftCardAmount</td><td>decimal</td><td>礼品卡抵用金额</td></tr>
 * <tr><td>paidAmount</td><td>{@link BigDecimal}</td><td>paidAmount</td><td>decimal</td><td>已支付金额</td></tr>
 * <tr><td>shippingAmount</td><td>{@link BigDecimal}</td><td>shippingAmount</td><td>decimal</td><td>淘宝运费</td></tr>
 * <tr><td>totalEsAmount</td><td>{@link BigDecimal}</td><td>totalEsAmount</td><td>decimal</td><td>网单中总的节能补贴金额之和</td></tr>
 * <tr><td>usedCustomerBalanceAmount</td><td>{@link BigDecimal}</td><td>usedCustomerBalanceAmount</td><td>decimal</td><td>使用的客户的余额支付金额</td></tr>
 * <tr><td>customerId</td><td>{@link Integer}</td><td>customerId</td><td>int</td><td>用余额支付的客户ID</td></tr>
 * <tr><td>bestShippingTime</td><td>{@link String}</td><td>bestShippingTime</td><td>varchar</td><td>最佳配送时间描述</td></tr>
 * <tr><td>paymentCode</td><td>{@link String}</td><td>paymentCode</td><td>varchar</td><td>支付方式code</td></tr>
 * <tr><td>payBankCode</td><td>{@link String}</td><td>payBankCode</td><td>varchar</td><td>网银代码</td></tr>
 * <tr><td>paymentName</td><td>{@link String}</td><td>paymentName</td><td>varchar</td><td>支付方式名称</td></tr>
 * <tr><td>consignee</td><td>{@link String}</td><td>consignee</td><td>varchar</td><td>收货人</td></tr>
 * <tr><td>originRegionName</td><td>{@link String}</td><td>originRegionName</td><td>varchar</td><td>原淘宝收货地址信息</td></tr>
 * <tr><td>originAddress</td><td>{@link String}</td><td>originAddress</td><td>varchar</td><td>原淘宝收货人详细收货信息</td></tr>
 * <tr><td>province</td><td>{@link Integer}</td><td>province</td><td>int</td><td>收货地址中国省份</td></tr>
 * <tr><td>city</td><td>{@link Integer}</td><td>city</td><td>int</td><td>收货地址中的城市</td></tr>
 * <tr><td>region</td><td>{@link Integer}</td><td>region</td><td>int</td><td>收货地址中城市中的区</td></tr>
 * <tr><td>street</td><td>{@link Integer}</td><td>street</td><td>int</td><td>街道ID</td></tr>
 * <tr><td>markBuilding</td><td>{@link Integer}</td><td>markBuilding</td><td>int</td><td>标志建筑物</td></tr>
 * <tr><td>regionName</td><td>{@link String}</td><td>regionName</td><td>varchar</td><td>地区名称（如：北京 北京 昌平区 兴寿镇）</td></tr>
 * <tr><td>address</td><td>{@link String}</td><td>address</td><td>varchar</td><td>收货地址中用户输入的地址，一般是区以下的详细地址</td></tr>
 * <tr><td>zipcode</td><td>{@link String}</td><td>zipcode</td><td>varchar</td><td>收货地址中的邮编</td></tr>
 * <tr><td>mobile</td><td>{@link String}</td><td>mobile</td><td>varchar</td><td>收货人手机号</td></tr>
 * <tr><td>phone</td><td>{@link String}</td><td>phone</td><td>varchar</td><td>收货人固定电话号</td></tr>
 * <tr><td>receiptInfo</td><td>{@link String}</td><td>receiptInfo</td><td>text</td><td>发票信息，序列化数组array('title' =>.., 'receiptType' =>..,'needReceipt' => ..,'companyName' =>..,'taxSpotNum' =>..,'regAddress'=>..,'regPhone'=>..,'bank'=>..,'bankAccount'=>..)</td></tr>
 * <tr><td>delayShipTime</td><td>{@link Integer}</td><td>delayShipTime</td><td>int</td><td>延迟发货日期</td></tr>
 * <tr><td>remark</td><td>{@link String}</td><td>remark</td><td>text</td><td>订单备注</td></tr>
 * <tr><td>bankCode</td><td>{@link String}</td><td>bankCode</td><td>varchar</td><td>银行代码,用于银行直链支付</td></tr>
 * <tr><td>agent</td><td>{@link String}</td><td>agent</td><td>varchar</td><td>处理人</td></tr>
 * <tr><td>confirmTime</td><td>{@link Integer}</td><td>confirmTime</td><td>int</td><td>确认时间</td></tr>
 * <tr><td>firstConfirmTime</td><td>{@link Integer}</td><td>firstConfirmTime</td><td>int</td><td>首次确认时间</td></tr>
 * <tr><td>firstConfirmPerson</td><td>{@link String}</td><td>firstConfirmPerson</td><td>varchar</td><td>第一次确认订单操作人</td></tr>
 * <tr><td>finishTime</td><td>{@link Integer}</td><td>finishTime</td><td>int</td><td>订单完成时间</td></tr>
 * <tr><td>tradeSn</td><td>{@link String}</td><td>tradeSn</td><td>varchar</td><td>在线支付交易流水号</td></tr>
 * <tr><td>signCode</td><td>{@link String}</td><td>signCode</td><td>varchar</td><td>收货确认码</td></tr>
 * <tr><td>source</td><td>{@link String}</td><td>source</td><td>varchar</td><td>订单来源</td></tr>
 * <tr><td>sourceOrderSn</td><td>{@link String}</td><td>sourceOrderSn</td><td>varchar</td><td>外部订单号</td></tr>
 * <tr><td>onedayLimit</td><td>{@link Integer}</td><td>onedayLimit</td><td>tinyint</td><td>是否支持24小时限时达</td></tr>
 * <tr><td>logisticsManner</td><td>{@link Integer}</td><td>logisticsManner</td><td>int</td><td>物流评价</td></tr>
 * <tr><td>afterSaleManner</td><td>{@link Integer}</td><td>afterSaleManner</td><td>int</td><td>售后评价</td></tr>
 * <tr><td>personManner</td><td>{@link Integer}</td><td>personManner</td><td>int</td><td>人员态度</td></tr>
 * <tr><td>visitRemark</td><td>{@link String}</td><td>visitRemark</td><td>varchar</td><td>回访备注</td></tr>
 * <tr><td>visitTime</td><td>{@link Integer}</td><td>visitTime</td><td>int</td><td>回访时间</td></tr>
 * <tr><td>visitPerson</td><td>{@link String}</td><td>visitPerson</td><td>varchar</td><td>回访人</td></tr>
 * <tr><td>sellPeople</td><td>{@link String}</td><td>sellPeople</td><td>varchar</td><td>销售代表</td></tr>
 * <tr><td>sellPeopleManner</td><td>{@link Integer}</td><td>sellPeopleManner</td><td>int</td><td>销售代表服务态度</td></tr>
 * <tr><td>orderType</td><td>{@link Integer}</td><td>orderType</td><td>tinyint</td><td>订单类型 默认0 团购预付款 团购正式单 2</td></tr>
 * <tr><td>hasReadTaobaoOrderComment</td><td>{@link Integer}</td><td>hasReadTaobaoOrderComment</td><td>tinyint</td><td>是否已读取过淘宝订单评论</td></tr>
 * <tr><td>memberInvoiceId</td><td>{@link Integer}</td><td>memberInvoiceId</td><td>int</td><td>订单发票ID,MemberInvoices表的主键</td></tr>
 * <tr><td>taobaoGroupId</td><td>{@link Integer}</td><td>taobaoGroupId</td><td>int</td><td>淘宝万人团活动ID</td></tr>
 * <tr><td>tradeType</td><td>{@link String}</td><td>tradeType</td><td>varchar</td><td>交易类型,值参考淘宝</td></tr>
 * <tr><td>stepTradeStatus</td><td>{@link String}</td><td>stepTradeStatus</td><td>varchar</td><td>分阶段付款的订单状态,值参考淘宝</td></tr>
 * <tr><td>stepPaidFee</td><td>{@link BigDecimal}</td><td>stepPaidFee</td><td>decimal</td><td>分阶段付款的已付金额</td></tr>
 * <tr><td>depositAmount</td><td>{@link BigDecimal}</td><td>depositAmount</td><td>decimal</td><td>定金应付金额</td></tr>
 * <tr><td>balanceAmount</td><td>{@link BigDecimal}</td><td>balanceAmount</td><td>decimal</td><td>尾款应付金额</td></tr>
 * <tr><td>autoCancelDays</td><td>{@link Integer}</td><td>autoCancelDays</td><td>int</td><td>未付款过期的天数</td></tr>
 * <tr><td>isNoLimitStockOrder</td><td>{@link Integer}</td><td>isNoLimitStockOrder</td><td>tinyint</td><td>是否是无库存限制订单</td></tr>
 * <tr><td>ccbOrderReceivedLogId</td><td>{@link Integer}</td><td>ccbOrderReceivedLogId</td><td>int</td><td>建行订单接收日志ID</td></tr>
 * <tr><td>ip</td><td>{@link String}</td><td>ip</td><td>varchar</td><td>订单来源IP,针对商城前台订单</td></tr>
 * <tr><td>isGiftCardOrder</td><td>{@link Integer}</td><td>isGiftCardOrder</td><td>tinyint</td><td>是否为礼品卡订单</td></tr>
 * <tr><td>giftCardDownloadPassword</td><td>{@link String}</td><td>giftCardDownloadPassword</td><td>varchar</td><td>礼品卡下载密码</td></tr>
 * <tr><td>giftCardFindMobile</td><td>{@link String}</td><td>giftCardFindMobile</td><td>varchar</td><td>礼品卡密码找回手机号</td></tr>
 * <tr><td>autoConfirmNum</td><td>{@link Integer}</td><td>autoConfirmNum</td><td>int</td><td>已自动确认的次数</td></tr>
 * <tr><td>codConfirmPerson</td><td>{@link String}</td><td>codConfirmPerson</td><td>varchar</td><td>货到付款确认人</td></tr>
 * <tr><td>codConfirmTime</td><td>{@link Integer}</td><td>codConfirmTime</td><td>int</td><td>货到付款确认时间</td></tr>
 * <tr><td>codConfirmRemark</td><td>{@link String}</td><td>codConfirmRemark</td><td>varchar</td><td>货到付款确认备注</td></tr>
 * <tr><td>codConfirmState</td><td>{@link Integer}</td><td>codConfirmState</td><td>tinyint</td><td>货到侍确认状态0无需未确认,1待确认,2确认通过可以发货,3确认无效,订单可以取消</td></tr>
 * <tr><td>paymentNoticeUrl</td><td>{@link String}</td><td>paymentNoticeUrl</td><td>text</td><td>付款结果通知URL</td></tr>
 * <tr><td>addressLon</td><td>{@link BigDecimal}</td><td>addressLon</td><td>decimal</td><td>地址经度</td></tr>
 * <tr><td>addressLat</td><td>{@link BigDecimal}</td><td>addressLat</td><td>decimal</td><td>地址纬度</td></tr>
 * <tr><td>smConfirmStatus</td><td>{@link Integer}</td><td>smConfirmStatus</td><td>tinyint</td><td>标建确认状态</td></tr>
 * <tr><td>smConfirmTime</td><td>{@link Integer}</td><td>smConfirmTime</td><td>int</td><td>请求发送HP时间，格式为时间戳</td></tr>
 * <tr><td>isTogether</td><td>{@link Integer}</td><td>isTogether</td><td>int</td><td>货票同行</td></tr>
 * <tr><td>isNotConfirm</td><td>{@link Integer}</td><td>isNotConfirm</td><td>int</td><td>是否是无需确认的订单</td></tr>
 * <tr><td>smManualTime</td><td>{@link Integer}</td><td>smManualTime</td><td>int</td><td>转人工处理时间</td></tr>
 * <tr><td>smManualRemark</td><td>{@link Integer}</td><td>smManualRemark</td><td>int</td><td>转人工处理备注</td></tr>
 * </table>
 *
 * @author rongmai
 */
public class Orders implements Serializable {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 6282903735652163900L;
    /**
     * ----临时字段，不入库----start------
     */
    private String deliverTime;
    private String provinceStr;
    private String cityStr;
    private String regionStr;
    private String streetStr;
    /**----临时字段，不入库----end------*/

    /**
     * 订单来源-统帅日日顺乐家专卖店
     */
    public static final String ORIGIN_TONGSHUAI = "TONGSHUAI";

    /**
     * 订单来源-统帅分销
     */
    public static final String ORIGIN_TSFX = "TONGSHUAIFX";

    /**
     * 订单来源-1号店订单
     */
    public static final String ORIGIN_YIHAODIAN = "YIHAODIAN";
    /**
     * 订单来源-平台大客户-1号店订单
     */
    public static final String ORIGIN_YHD = "YHD";

    private Integer id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer isTest;

    /**
     * 获取 是否是测试订单
     */
    public Integer getIsTest() {
        return this.isTest;
    }

    /**
     * 设置 是否是测试订单
     *
     * @param value 属性值
     */
    public void setIsTest(Integer value) {
        this.isTest = value;
    }

    private Integer hasSync = 0;

    /**
     * 获取 是否已同步(临时添加)
     */
    public Integer getHasSync() {
        return this.hasSync;
    }


    private String memberEmail;

    private Long points;

    public Long getPoints() {
        return points;
    }

    private String tmallName;
    private String aliPayName;


    public String getTmallName() {
        return tmallName;
    }

    public void setTmallName(String tmallName) {
        this.tmallName = tmallName;
    }

    public String getAliPayName() {
        return aliPayName;
    }

    public void setAliPayName(String aliPayName) {
        this.aliPayName = aliPayName;
    }

    private BigDecimal paidAmount;// 已支付金额

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    /**
     * 设置 是否已同步(临时添加)
     *
     * @param value 属性值
     */
    public void setHasSync(Integer value) {
        this.hasSync = value;
    }

    private Integer isBackend = 0;

    /**
     * 获取 是否为后台添加的订单
     */
    public Integer getIsBackend() {
        return this.isBackend;
    }

    /**
     * 设置 是否为后台添加的订单
     *
     * @param value 属性值
     */
    public void setIsBackend(Integer value) {
        this.isBackend = value;
    }

    private Integer isBook = 0;

    /**
     * 获取 是否为后台添加的订单
     */
    public Integer getIsBook() {
        return this.isBook;
    }

    /**
     * 设置 是否为后台添加的订单
     *
     * @param value 属性值
     */
    public void setIsBook(Integer value) {
        this.isBook = value;
    }

    private Integer isCod;

    /**
     * 获取 是否是货到付款订单
     */
    public Integer getIsCod() {
        return this.isCod;
    }

    /**
     * 设置 是否是货到付款订单
     *
     * @param value 属性值
     */
    public void setIsCod(Integer value) {
        this.isCod = value;
    }

    private Integer notAutoConfirm;

    /**
     * 获取 是否是非自动确认订单
     */
    public Integer getNotAutoConfirm() {
        return this.notAutoConfirm;
    }

    /**
     * 设置 是否是非自动确认订单
     *
     * @param value 属性值
     */
    public void setNotAutoConfirm(Integer value) {
        this.notAutoConfirm = value;
    }

    private String orderSn;

    /**
     * 获取 订单号
     */
    public String getOrderSn() {
        return this.orderSn;
    }

    /**
     * 设置 订单号
     *
     * @param value 属性值
     */
    public void setOrderSn(String value) {
        this.orderSn = value;
    }

    private String relationOrderSn;

    /**
     * 获取 关联订单编号
     */
    public String getRelationOrderSn() {
        return this.relationOrderSn;
    }

    /**
     * 设置 关联订单编号
     *
     * @param value 属性值
     */
    public void setRelationOrderSn(String value) {
        this.relationOrderSn = value;
    }

    private Integer memberId;

    /**
     * 获取 会员id
     */
    public Integer getMemberId() {
        return this.memberId;
    }

    /**
     * 设置 会员id
     *
     * @param value 属性值
     */
    public void setMemberId(Integer value) {
        this.memberId = value;
    }

    private Long addTime;

    public Long getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Long value) {
        this.addTime = value;
    }

    private Long syncTime;

    /**
     * 获取 同步到此表中的时间
     */
    public Long getSyncTime() {
        return this.syncTime;
    }

    /**
     * 设置 同步到此表中的时间
     *
     * @param value 属性值
     */
    public void setSyncTime(Long value) {
        this.syncTime = value;
    }

    private Integer orderStatus;

    /**
     * 获取 订单状态
     */
    public Integer getOrderStatus() {
        return this.orderStatus;
    }

    /**
     * 设置 订单状态
     *
     * @param value 属性值
     */
    public void setOrderStatus(Integer value) {
        this.orderStatus = value;
    }

    private Long payTime;

    /**
     * 获取 在线付款时间
     */
    public Long getPayTime() {
        return this.payTime;
    }

    /**
     * 设置 在线付款时间
     *
     * @param value 属性值
     */
    public void setPayTime(Long value) {
        this.payTime = value;
    }

    private Integer paymentStatus;

    /**
     * 获取 付款状态：0 买家未付款 1 买家已付款
     */
    public Integer getPaymentStatus() {
        return this.paymentStatus;
    }

    /**
     * 设置 付款状态：0 买家未付款 1 买家已付款
     *
     * @param value 属性值
     */
    public void setPaymentStatus(Integer value) {
        this.paymentStatus = value;
    }

    private String receiptConsignee;

    /**
     * 获取 发票收件人
     */
    public String getReceiptConsignee() {
        return this.receiptConsignee;
    }

    /**
     * 设置 发票收件人
     *
     * @param value 属性值
     */
    public void setReceiptConsignee(String value) {
        this.receiptConsignee = value;
    }

    private String receiptAddress;

    /**
     * 获取 发票地址
     */
    public String getReceiptAddress() {
        return this.receiptAddress;
    }

    /**
     * 设置 发票地址
     *
     * @param value 属性值
     */
    public void setReceiptAddress(String value) {
        this.receiptAddress = value;
    }

    private String receiptZipcode;

    /**
     * 获取 发票邮编
     */
    public String getReceiptZipcode() {
        return this.receiptZipcode;
    }

    /**
     * 设置 发票邮编
     *
     * @param value 属性值
     */
    public void setReceiptZipcode(String value) {
        this.receiptZipcode = value;
    }

    private String receiptMobile;

    /**
     * 获取 发票联系电话
     */
    public String getReceiptMobile() {
        return this.receiptMobile;
    }

    /**
     * 设置 发票联系电话
     *
     * @param value 属性值
     */
    public void setReceiptMobile(String value) {
        this.receiptMobile = value;
    }

    private BigDecimal productAmount;

    /**
     * 获取 商品金额，等于订单中所有的商品的单价乘以数量之和
     */
    public BigDecimal getProductAmount() {
        return this.productAmount;
    }

    /**
     * 设置 商品金额，等于订单中所有的商品的单价乘以数量之和
     *
     * @param value 属性值
     */
    public void setProductAmount(BigDecimal value) {
        this.productAmount = value;
    }

    private BigDecimal orderAmount;

    /**
     * 获取 订单总金额，等于商品总金额＋运费
     */
    public BigDecimal getOrderAmount() {
        return this.orderAmount;
    }

    /**
     * 设置 订单总金额，等于商品总金额＋运费
     *
     * @param value 属性值
     */
    public void setOrderAmount(BigDecimal value) {
        this.orderAmount = value;
    }

    private BigDecimal shippingAmount;

    /**
     * 获取 淘宝运费
     */
    public BigDecimal getShippingAmount() {
        return this.shippingAmount;
    }

    /**
     * 设置 淘宝运费
     *
     * @param value 属性值
     */
    public void setShippingAmount(BigDecimal value) {
        this.shippingAmount = value;
    }

    private BigDecimal totalEsAmount;

    /**
     * 获取 网单中总的节能补贴金额之和
     */
    public BigDecimal getTotalEsAmount() {
        return this.totalEsAmount;
    }

    /**
     * 设置 网单中总的节能补贴金额之和
     *
     * @param value 属性值
     */
    public void setTotalEsAmount(BigDecimal value) {
        this.totalEsAmount = value;
    }

    private String paymentCode;

    /**
     * 获取 支付方式code
     */
    public String getPaymentCode() {
        return this.paymentCode;
    }

    /**
     * 设置 支付方式code
     *
     * @param value 属性值
     */
    public void setPaymentCode(String value) {
        this.paymentCode = value;
    }

    private String payBankCode;

    /**
     * 获取 网银代码
     */
    public String getPayBankCode() {
        return this.payBankCode;
    }

    /**
     * 设置 网银代码
     *
     * @param value 属性值
     */
    public void setPayBankCode(String value) {
        this.payBankCode = value;
    }

    private String paymentName;

    /**
     * 获取 支付方式名称
     */
    public String getPaymentName() {
        return this.paymentName;
    }

    /**
     * 设置 支付方式名称
     *
     * @param value 属性值
     */
    public void setPaymentName(String value) {
        this.paymentName = value;
    }

    private String consignee;

    /**
     * 获取 收货人
     */
    public String getConsignee() {
        return this.consignee;
    }

    /**
     * 设置 收货人
     *
     * @param value 属性值
     */
    public void setConsignee(String value) {
        this.consignee = value;
    }

    private String originRegionName;

    /**
     * 获取 原淘宝收货地址信息
     */
    public String getOriginRegionName() {
        return this.originRegionName;
    }

    /**
     * 设置 原淘宝收货地址信息
     *
     * @param value 属性值
     */
    public void setOriginRegionName(String value) {
        this.originRegionName = value;
    }

    private String originAddress;

    /**
     * 获取 原淘宝收货人详细收货信息
     */
    public String getOriginAddress() {
        return this.originAddress;
    }

    /**
     * 设置 原淘宝收货人详细收货信息
     *
     * @param value 属性值
     */
    public void setOriginAddress(String value) {
        this.originAddress = value;
    }

    private Integer province;

    /**
     * 获取 收货地址中国省份
     */
    public Integer getProvince() {
        return this.province;
    }

    /**
     * 设置 收货地址中国省份
     *
     * @param value 属性值
     */
    public void setProvince(Integer value) {
        this.province = value;
    }

    private Integer city;

    /**
     * 获取 收货地址中的城市
     */
    public Integer getCity() {
        return this.city;
    }

    /**
     * 设置 收货地址中的城市
     *
     * @param value 属性值
     */
    public void setCity(Integer value) {
        this.city = value;
    }

    private Integer region;

    /**
     * 获取 收货地址中城市中的区
     */
    public Integer getRegion() {
        return this.region;
    }

    /**
     * 设置 收货地址中城市中的区
     *
     * @param value 属性值
     */
    public void setRegion(Integer value) {
        this.region = value;
    }

    private Integer street;

    /**
     * 获取 街道ID
     */
    public Integer getStreet() {
        return this.street;
    }

    /**
     * 设置 街道ID
     *
     * @param value 属性值
     */
    public void setStreet(Integer value) {
        this.street = value;
    }

    /**
     * 获取 标志建筑物
     */
    private String regionName;

    /**
     * 获取 地区名称（如：北京 北京 昌平区 兴寿镇）
     */
    public String getRegionName() {
        return this.regionName;
    }

    /**
     * 设置 地区名称（如：北京 北京 昌平区 兴寿镇）
     *
     * @param value 属性值
     */
    public void setRegionName(String value) {
        this.regionName = value;
    }

    private String address;

    /**
     * 获取 收货地址中用户输入的地址，一般是区以下的详细地址
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 设置 收货地址中用户输入的地址，一般是区以下的详细地址
     *
     * @param value 属性值
     */
    public void setAddress(String value) {
        this.address = value;
    }

    private String zipcode;

    /**
     * 获取 收货地址中的邮编
     */
    public String getZipcode() {
        return this.zipcode;
    }

    /**
     * 设置 收货地址中的邮编
     *
     * @param value 属性值
     */
    public void setZipcode(String value) {
        this.zipcode = value;
    }

    private String mobile;

    /**
     * 获取 收货人手机号
     */
    public String getMobile() {
        return this.mobile;
    }

    /**
     * 设置 收货人手机号
     *
     * @param value 属性值
     */
    public void setMobile(String value) {
        this.mobile = value;
    }

    private String phone;

    /**
     * 获取 收货人固定电话号
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * 设置 收货人固定电话号
     *
     * @param value 属性值
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    private String receiptInfo;

    /**
     * 获取 发票信息，序列化数组array('title' =>.., 'receiptType' =>..,'needReceipt' => ..,'companyName' =>..,'taxSpotNum' =>..,'regAddress'=>..,'regPhone'=>..,'bank'=>..,'bankAccount'=>..)
     */
    public String getReceiptInfo() {
        return this.receiptInfo;
    }

    /**
     * 设置 发票信息，序列化数组array('title' =>.., 'receiptType' =>..,'needReceipt' => ..,'companyName' =>..,'taxSpotNum' =>..,'regAddress'=>..,'regPhone'=>..,'bank'=>..,'bankAccount'=>..)
     *
     * @param value 属性值
     */
    public void setReceiptInfo(String value) {
        this.receiptInfo = value;
    }

    private Integer delayShipTime;

    /**
     * 获取 延迟发货日期
     */
    public Integer getDelayShipTime() {
        return this.delayShipTime;
    }

    /**
     * 设置 延迟发货日期
     *
     * @param value 属性值
     */
    public void setDelayShipTime(Integer value) {
        this.delayShipTime = value;
    }

    private String remark;

    /**
     * 获取 订单备注
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置 订单备注
     *
     * @param value 属性值
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    private String bankCode;

    /**
     * 获取 银行代码,用于银行直链支付
     */
    public String getBankCode() {
        return this.bankCode;
    }

    /**
     * 设置 银行代码,用于银行直链支付
     *
     * @param value 属性值
     */
    public void setBankCode(String value) {
        this.bankCode = value;
    }

    private String agent;

    /**
     * 获取 处理人
     */
    public String getAgent() {
        return this.agent;
    }

    /**
     * 设置 处理人
     *
     * @param value 属性值
     */
    public void setAgent(String value) {
        this.agent = value;
    }

    private Integer confirmTime;

    /**
     * 获取 确认时间
     */
    public Integer getConfirmTime() {
        return this.confirmTime;
    }

    /**
     * 设置 确认时间
     *
     * @param value 属性值
     */
    public void setConfirmTime(Integer value) {
        this.confirmTime = value;
    }

    private Integer firstConfirmTime;

    /**
     * 获取 首次确认时间
     */
    public Integer getFirstConfirmTime() {
        return this.firstConfirmTime;
    }

    /**
     * 设置 首次确认时间
     *
     * @param value 属性值
     */
    public void setFirstConfirmTime(Integer value) {
        this.firstConfirmTime = value;
    }

    private String firstConfirmPerson;

    /**
     * 获取 第一次确认订单操作人
     */
    public String getFirstConfirmPerson() {
        return this.firstConfirmPerson;
    }

    /**
     * 设置 第一次确认订单操作人
     *
     * @param value 属性值
     */
    public void setFirstConfirmPerson(String value) {
        this.firstConfirmPerson = value;
    }

    private Integer finishTime;

    /**
     * 获取 订单完成时间
     */
    public Integer getFinishTime() {
        return this.finishTime;
    }

    /**
     * 设置 订单完成时间
     *
     * @param value 属性值
     */
    public void setFinishTime(Integer value) {
        this.finishTime = value;
    }

    private String tradeSn;

    /**
     * 获取 在线支付交易流水号
     */
    public String getTradeSn() {
        return this.tradeSn;
    }

    /**
     * 设置 在线支付交易流水号
     *
     * @param value 属性值
     */
    public void setTradeSn(String value) {
        this.tradeSn = value;
    }

    private String signCode;

    /**
     * 获取 收货确认码
     */
    public String getSignCode() {
        return this.signCode;
    }

    /**
     * 设置 收货确认码
     *
     * @param value 属性值
     */
    public void setSignCode(String value) {
        this.signCode = value;
    }

    private String source;

    /**
     * 获取 订单来源
     */
    public String getSource() {
        return this.source;
    }

    /**
     * 设置 订单来源
     *
     * @param value 属性值
     */
    public void setSource(String value) {
        this.source = value;
    }

    private String sourceOrderSn;

    /**
     * 获取 外部订单号
     */
    public String getSourceOrderSn() {
        return this.sourceOrderSn;
    }

    /**
     * 设置 外部订单号
     *
     * @param value 属性值
     */
    public void setSourceOrderSn(String value) {
        this.sourceOrderSn = value;
    }

    private Integer onedayLimit = 0;

    /**
     * 获取 是否支持24小时限时达
     */
    public Integer getOnedayLimit() {
        return this.onedayLimit;
    }

    /**
     * 设置 是否支持24小时限时达
     *
     * @param value 属性值
     */
    public void setOnedayLimit(Integer value) {
        this.onedayLimit = value;
    }

    private Integer orderType;

    /**
     * 获取 订单类型 默认0 团购预付款 团购正式单 2
     */
    public Integer getOrderType() {
        return this.orderType;
    }

    /**
     * 设置 订单类型 默认0 团购预付款 团购正式单 2
     *
     * @param value 属性值
     */
    public void setOrderType(Integer value) {
        this.orderType = value;
    }


    private Integer memberInvoiceId;

    /**
     * 获取 订单发票ID,MemberInvoices表的主键
     */
    public Integer getMemberInvoiceId() {
        return this.memberInvoiceId;
    }

    /**
     * 设置 订单发票ID,MemberInvoices表的主键
     *
     * @param value 属性值
     */
    public void setMemberInvoiceId(Integer value) {
        this.memberInvoiceId = value;
    }

    private Integer taobaoGroupId;

    /**
     * 获取 淘宝万人团活动ID
     */
    public Integer getTaobaoGroupId() {
        return this.taobaoGroupId;
    }

    /**
     * 设置 淘宝万人团活动ID
     *
     * @param value 属性值
     */
    public void setTaobaoGroupId(Integer value) {
        this.taobaoGroupId = value;
    }

    private String tradeType;

    /**
     * 获取 交易类型,值参考淘宝
     */
    public String getTradeType() {
        return this.tradeType;
    }

    /**
     * 设置 交易类型,值参考淘宝
     *
     * @param value 属性值
     */
    public void setTradeType(String value) {
        this.tradeType = value;
    }

    private String stepTradeStatus;

    /**
     * 获取 分阶段付款的订单状态,值参考淘宝
     */
    public String getStepTradeStatus() {
        return this.stepTradeStatus;
    }

    /**
     * 设置 分阶段付款的订单状态,值参考淘宝
     *
     * @param value 属性值
     */
    public void setStepTradeStatus(String value) {
        this.stepTradeStatus = value;
    }

    private BigDecimal stepPaidFee;

    /**
     * 获取 分阶段付款的已付金额
     */
    public BigDecimal getStepPaidFee() {
        return this.stepPaidFee;
    }

    /**
     * 设置 分阶段付款的已付金额
     *
     * @param value 属性值
     */
    public void setStepPaidFee(BigDecimal value) {
        this.stepPaidFee = value;
    }

    private BigDecimal depositAmount;

    /**
     * 获取 定金应付金额
     */
    public BigDecimal getDepositAmount() {
        return this.depositAmount;
    }

    /**
     * 设置 定金应付金额
     *
     * @param value 属性值
     */
    public void setDepositAmount(BigDecimal value) {
        this.depositAmount = value;
    }

    private BigDecimal balanceAmount;

    /**
     * 获取 尾款应付金额
     */
    public BigDecimal getBalanceAmount() {
        return this.balanceAmount;
    }

    /**
     * 设置 尾款应付金额
     *
     * @param value 属性值
     */
    public void setBalanceAmount(BigDecimal value) {
        this.balanceAmount = value;
    }

    private Integer autoCancelDays;

    /**
     * 获取 未付款过期的天数
     */
    public Integer getAutoCancelDays() {
        return this.autoCancelDays;
    }

    /**
     * 设置 未付款过期的天数
     *
     * @param value 属性值
     */
    public void setAutoCancelDays(Integer value) {
        this.autoCancelDays = value;
    }

    private Integer isNoLimitStockOrder;

    /**
     * 获取 是否是无库存限制订单
     */
    public Integer getIsNoLimitStockOrder() {
        return this.isNoLimitStockOrder;
    }

    /**
     * 设置 是否是无库存限制订单
     *
     * @param value 属性值
     */
    public void setIsNoLimitStockOrder(Integer value) {
        this.isNoLimitStockOrder = value;
    }

    private Integer autoConfirmNum;

    /**
     * 获取 已自动确认的次数
     */
    public Integer getAutoConfirmNum() {
        return this.autoConfirmNum;
    }

    /**
     * 设置 已自动确认的次数
     *
     * @param value 属性值
     */
    public void setAutoConfirmNum(Integer value) {
        this.autoConfirmNum = value;
    }

    private Long smConfirmTime = 0L;

    /**
     * 获取 请求发送HP时间，格式为时间戳
     */
    public Long getSmConfirmTime() {
        return this.smConfirmTime;
    }

    /**
     * 设置 请求发送HP时间，格式为时间戳
     *
     * @param value 属性值
     */
    public void setSmConfirmTime(Long value) {
        this.smConfirmTime = value;
    }

    private Integer isTogether = 0;

    /**
     * 获取 货票同行
     */
    public Integer getIsTogether() {
        return this.isTogether;
    }

    /**
     * 设置 货票同行
     *
     * @param value 属性值
     */
    public void setIsTogether(Integer value) {
        this.isTogether = value;
    }

    private Integer isNotConfirm = 0;

    /**
     * 获取 是否是无需确认的订单
     */
    public Integer getIsNotConfirm() {
        return this.isNotConfirm;
    }

    /**
     * 设置 是否是无需确认的订单
     *
     * @param value 属性值
     */
    public void setIsNotConfirm(Integer value) {
        this.isNotConfirm = value;
    }

    private Long smManualTime = 0L;

    /**
     * 获取 转人工处理时间
     */
    public Long getSmManualTime() {
        return this.smManualTime;
    }

    /**
     * 设置 转人工处理时间
     *
     * @param value 属性值
     */
    public void setSmManualTime(Long value) {
        this.smManualTime = value;
    }

    private String smManualRemark;

    /**
     * 获取 转人工处理备注
     *
     * @return
     */
    public String getSmManualRemark() {
        return this.smManualRemark;
    }

    /**
     * 设置 转人工处理备注
     *
     * @param value
     */
    public void setSmManualRemark(String value) {
        this.smManualRemark = value;
    }

    private List<OrderProducts> orderProducts;

    public List<OrderProducts> getOrderProducts() {
        return this.orderProducts;
    }

    public void setOrderProducts(List<OrderProducts> value) {
        this.orderProducts = value;
    }

    private String invoiceName;

    /**
     * 获取 淘宝发票抬头
     *
     * @return
     */
    public String getInvoiceName() {
        return this.invoiceName;
    }

    /**
     * 设置 淘宝发票抬头
     *
     * @param value
     */
    public void setInvoiceName(String value) {
        this.invoiceName = value;
    }

    private Long tailPayTime;

    /**
     * 获取 付款时间
     *
     * @return
     */
    public long getTailPayTime() {
        return tailPayTime;
    }

    /**
     * 设置 付款时间
     *
     * @param tailPayTime
     */
    public void setTailPayTime(Long tailPayTime) {
        this.tailPayTime = tailPayTime;
    }

    private Integer isProduceDaily; //是否日日单(1:是，0:否)

    /**
     * 获取 是否日日顺单
     *
     * @return
     */
    public Integer getIsProduceDaily() {
        return isProduceDaily;
    }

    /**
     * 设置 是否日日顺单
     *
     * @param isProduceDaily
     */
    public void setIsProduceDaily(Integer isProduceDaily) {
        this.isProduceDaily = isProduceDaily;
    }

    private String ckCode;

    /**
     * 获取 创客码
     *
     * @return
     */
    public String getCkCode() {
        return ckCode;
    }

    /**
     * 设置 创客码
     *
     * @param ckCode
     */
    public void setCkCode(String ckCode) {
        this.ckCode = ckCode;
    }

    private Integer iswww;

    /**
     * 获取 是否3W单
     *
     * @return
     */
    public Integer getIswww() {
        return iswww;
    }

    /**
     * 设置 是否3W单
     *
     * @param iswww
     */
    public void setIswww(Integer iswww) {
        this.iswww = iswww;
    }

    private Integer smConfirmStatus = 0;

    public Integer getSmConfirmStatus() {
        return smConfirmStatus;
    }

    public void setSmConfirmStatus(Integer smConfirmStatus) {
        this.smConfirmStatus = smConfirmStatus;
    }

    public String getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getProvinceStr() {
        return provinceStr;
    }

    public void setProvinceStr(String provinceStr) {
        this.provinceStr = provinceStr;
    }

    public String getCityStr() {
        return cityStr;
    }

    public void setCityStr(String cityStr) {
        this.cityStr = cityStr;
    }

    public String getRegionStr() {
        return regionStr;
    }

    public void setRegionStr(String regionStr) {
        this.regionStr = regionStr;
    }

    public String getStreetStr() {
        return streetStr;
    }

    public void setStreetStr(String streetStr) {
        this.streetStr = streetStr;
    }

    private String lbxSn;

    public String getLbxSn() {
        return lbxSn;
    }

    public void setLbxSn(String lbxSn) {
        this.lbxSn = lbxSn;
    }


    private String sellPeople;

    public String getSellPeople() {
        return sellPeople;
    }

    public void setSellPeople(String sellPeople) {
        this.sellPeople = sellPeople;
    }

}