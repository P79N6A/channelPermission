package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单
 */
public class Orders implements Serializable {
    private static final long serialVersionUID = -1870356948297769645L;


    /**
     * Comment for <code>serialVersionUID</code>
     */

    /**----临时字段，不入库----start------*/
    private String deliverTime;
    private String provinceStr;
    private String regionStr;
    private String cityStr;
    private String streetStr;
    /**----临时字段，不入库----end------*/

    /**
     * 订单来源-统帅日日顺乐家专卖店
     */
    public static final String ORIGIN_TONGSHUAI = "TONGSHUAI";

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
    private Date modified;// 订单的最后更新时间
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

    public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	private String memberEmail;

    private Long points;

    public Long getPoints() {
        return points;
    }
    private int markBuilding;//标建标志（0-未标建 1-标建）
    private String poiId;//标建ID
    private String poiName;
    private String tmallName;
    private String aliPayName;
    private String lbxSn;
    private String sellpeople;
    private String orderYwType;
//    private String modified;//最后更新时间
    private String channelId;//区分EP和商城
    private String couponCode;//优惠码编码
    private String couponCodeValue;//优惠码优惠金额
    /**
     * 复制订单标志：1-手动复制 2-自动复制
     */
    private Integer isCopy;

    public Integer getIsCopy() {
        return isCopy;
    }

    public void setIsCopy(Integer isCopy) {
        this.isCopy = isCopy;
    }

    public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getCouponCodeValue() {
		return couponCodeValue;
	}

	public void setCouponCodeValue(String couponCodeValue) {
		this.couponCodeValue = couponCodeValue;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}


	public String getLbxSn() {
		return lbxSn;
	}

	public void setLbxSn(String lbxSn) {
		this.lbxSn = lbxSn;
	}

	public String getSellpeople() {
		return sellpeople;
	}

	public void setSellpeople(String sellpeople) {
		this.sellpeople = sellpeople;
	}

	public String getOrderYwType() {
		return orderYwType;
	}

	public void setOrderYwType(String orderYwType) {
		this.orderYwType = orderYwType;
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

	public int getMarkBuilding() {
		return markBuilding;
	}

	public void setMarkBuilding(int markBuilding) {
		this.markBuilding = markBuilding;
	}

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
     * 获取 是否为预定订单
     */
    public Integer getIsBook() {
        return this.isBook;
    }

    /**
     * 设置 是否为预定订单
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
	private String codConfirmPerson;//货到付款确认人
	private String codConfirmTime;//货到付款确认时间
	private String codConfirmRemark;//货到付款确认备注
	private String codConfirmState;//货到侍确认状态0无需未确认,1待确认,2确认通过可以发货,3确认无效,订单可以取消
	private String paymentNoticeUrl;//付款结果通知URL
	private String addressLon;//地址经度
	private String addressLat;//地址纬度
	
    public String getCodConfirmPerson() {
		return codConfirmPerson;
	}

	public void setCodConfirmPerson(String codConfirmPerson) {
		this.codConfirmPerson = codConfirmPerson;
	}

	public String getCodConfirmTime() {
		return codConfirmTime;
	}

	public void setCodConfirmTime(String codConfirmTime) {
		this.codConfirmTime = codConfirmTime;
	}

	public String getCodConfirmRemark() {
		return codConfirmRemark;
	}

	public void setCodConfirmRemark(String codConfirmRemark) {
		this.codConfirmRemark = codConfirmRemark;
	}

	public String getCodConfirmState() {
		return codConfirmState;
	}

	public void setCodConfirmState(String codConfirmState) {
		this.codConfirmState = codConfirmState;
	}

	public String getPaymentNoticeUrl() {
		return paymentNoticeUrl;
	}

	public void setPaymentNoticeUrl(String paymentNoticeUrl) {
		this.paymentNoticeUrl = paymentNoticeUrl;
	}

	public String getAddressLon() {
		return addressLon;
	}

	public void setAddressLon(String addressLon) {
		this.addressLon = addressLon;
	}

	public String getAddressLat() {
		return addressLat;
	}

	public void setAddressLat(String addressLat) {
		this.addressLat = addressLat;
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

    private Integer isTogether ;

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
    
}