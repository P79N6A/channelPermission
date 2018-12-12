package com.haier.shop.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class QueryOrderParameter implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 省份
	 */
	public static final Map<Integer,String> PROVINCE_MAP = new HashMap<Integer,String>();
	
	/**
	 * 订单来源
	 */
	public static final Map<String,String> SOURCE_MAP = new HashMap<String,String>();
	
	static{
		PROVINCE_MAP.put(13,"安徽");
		PROVINCE_MAP.put(2,"北京");
		PROVINCE_MAP.put(23,"重庆");
		PROVINCE_MAP.put(14,"福建");
		PROVINCE_MAP.put(25,"贵州");
		PROVINCE_MAP.put(20,"广东");
		PROVINCE_MAP.put(29,"甘肃");
		PROVINCE_MAP.put(21,"广西");
		PROVINCE_MAP.put(18,"湖北");
		PROVINCE_MAP.put(17,"河南");
		PROVINCE_MAP.put(22,"海南");
		PROVINCE_MAP.put(19,"湖南");
		PROVINCE_MAP.put(9,"黑龙江");
		PROVINCE_MAP.put(4,"河北");
		PROVINCE_MAP.put(15,"江西");
		PROVINCE_MAP.put(8,"吉林");
		PROVINCE_MAP.put(11,"江苏");
		PROVINCE_MAP.put(7,"辽宁");
		PROVINCE_MAP.put(6,"内蒙古");
		PROVINCE_MAP.put(31,"宁夏");
		PROVINCE_MAP.put(30,"青海");
		PROVINCE_MAP.put(24,"四川");
		PROVINCE_MAP.put(5,"山西");
		PROVINCE_MAP.put(10,"上海");
		PROVINCE_MAP.put(16,"山东");
		PROVINCE_MAP.put(28,"陕西");
		PROVINCE_MAP.put(3,"天津");
		PROVINCE_MAP.put(27,"西藏");
		PROVINCE_MAP.put(32,"新疆");
		PROVINCE_MAP.put(26,"云南");
		PROVINCE_MAP.put(12,"浙江");
		SOURCE_MAP.put("TBSC", "海尔官方淘宝旗舰店");
		SOURCE_MAP.put("TOPBOILER", "海尔热水器专卖店");
		SOURCE_MAP.put("TONGSHUAI", "淘宝网统帅日日顺乐家专卖店");
		SOURCE_MAP.put("TONGSHUAIFX", "统帅品牌商");
		SOURCE_MAP.put("TOPXB", "海尔新宝旗舰店");
		SOURCE_MAP.put("MOOKA", "mooka模卡官方旗舰店");
		SOURCE_MAP.put("WASHER", "海尔洗衣机旗舰店");
		SOURCE_MAP.put("FRIDGE", "海尔冰冷旗舰店");
		SOURCE_MAP.put("AIR", "海尔空调旗舰店");
		SOURCE_MAP.put("TBCT", "村淘海尔商家");
		SOURCE_MAP.put("GQGYS", "海尔官方旗舰店供应商");
		SOURCE_MAP.put("TMKSD", "天猫卡萨帝旗舰店");
		SOURCE_MAP.put("TMTV", "海尔电视旗舰店");
		SOURCE_MAP.put("TBCFDD", "海尔厨房大电旗舰店");
		SOURCE_MAP.put("TBXCR", "天猫小超人旗舰店");
		SOURCE_MAP.put("TOPSHJD", "海尔生活电器专卖店");
		SOURCE_MAP.put("TOPDHSC", "海尔生活家电旗舰店");
		SOURCE_MAP.put("GMZX", "统帅国美旗舰店");
		SOURCE_MAP.put("GMZXTS", "统帅国美官方旗舰店");
		SOURCE_MAP.put("GMTSZYCW", "统帅国美旗舰店(厨卫)");
		SOURCE_MAP.put("GMTSZYKT", "统帅国美旗舰店(空调)");
		SOURCE_MAP.put("GMTSZYBX", "统帅国美旗舰店(冰箱)");
		SOURCE_MAP.put("GMTSZYXYJ", "统帅国美旗舰店(洗衣机)");
		SOURCE_MAP.put("yihaodian", "一号店");
		SOURCE_MAP.put("yihaodiants", "一号店统帅");
		SOURCE_MAP.put("YHDZY", "一号店自营");
		SOURCE_MAP.put("YHDQWZY", "一号店全网自营");
		SOURCE_MAP.put("jianhang", "建行");
		SOURCE_MAP.put("ICBC", "工商");
		SOURCE_MAP.put("YMX", "亚马逊");
		SOURCE_MAP.put("SNYG", "苏宁");
		SOURCE_MAP.put("SNHEGQ", "苏宁海尔集团官方旗舰店");
		SOURCE_MAP.put("SNQDZX", "苏宁渠道中心");
		SOURCE_MAP.put("DDW", "当当");
		SOURCE_MAP.put("YLW", "邮乐");
		SOURCE_MAP.put("YDYZ", "有赞微商城");
		SOURCE_MAP.put("KDGW", "微店");
		SOURCE_MAP.put("MD", "萌店");
		SOURCE_MAP.put("JDXYJ", "京东");
	};
	
	
	private Integer smConfirmStatus;		//是否转人工处理   1是，2否
	private Integer isCod;					//货到付款订单   1是，2否
	private Integer codConfirmState;		//货到付款确认状态   0无需审核，1待审核，2审核确认通过，3审核确认无效
	private String orderSn;					//请输入订单号
	private String sourceOrderNo;			//来源订单号
	private String mobile;					//手机号码
	private String consignee;				//收货人
	private String productName;				//商品名称
	private Integer isGiftCardOrder;		//礼品卡订单  1是，2否
	private String source;					//订单来源
	private String orderMoney;				//订单金额
	private Integer orderStatus;			//订单状态   200未确认，201已确认，202已取消，203已完成
	private String paymentName;				//支付方式
	private String paymentCode;				//支付码
	private Integer paymentStatus;			//支付状态  100未付款,101已付款,102待退款,103已退款
	private Integer timesBegine;			//已确认次数开始
	private Integer timesEnd;				//已确认次数结束
	private String addTimeBegine;			//订单生成时间开始
	private String addTimeEnd;				//订单生成时间结束
	private String modifiedBegine;			//订单最后更新时间开始
	private String modifiedEnd;				//订单最后更新时间结束
	private String orderAddTime;			//订单时间开始
	private String orderModified;			//订单更新时间结束
	private Integer orderType;				//订单类型    0普通订单，2定金尾款订单
	private Integer invoiceType;			//发票类型    1增票，2普票
	private Integer provinceId;				//省份id
	private String payTimeSort;				//asc按付款时间升序显示,desc按付款时间降序显示
	private Integer isUsePoint;				//是否使用积分   1是，0否
	private Integer isProduceDaily;			//是否日日单   1是，0否
	private Integer rows;					//行数
	private Integer page;					//页数
	
	private String payTime;
	private String syncTime;
	private String firstConfirmTime;
	private Integer autoConfirmNum;
	public Integer getSmConfirmStatus() {
		return smConfirmStatus;
	}
	public void setSmConfirmStatus(Integer smConfirmStatus) {
		this.smConfirmStatus = smConfirmStatus;
	}
	public Integer getIsCod() {
		return isCod;
	}
	public void setIsCod(Integer isCod) {
		this.isCod = isCod;
	}
	public Integer getCodConfirmState() {
		return codConfirmState;
	}
	public void setCodConfirmState(Integer codConfirmState) {
		this.codConfirmState = codConfirmState;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public String getSourceOrderNo() {
		return sourceOrderNo;
	}
	public void setSourceOrderNo(String sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getIsGiftCardOrder() {
		return isGiftCardOrder;
	}
	public void setIsGiftCardOrder(Integer isGiftCardOrder) {
		this.isGiftCardOrder = isGiftCardOrder;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public Integer getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(Integer paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Integer getTimesBegine() {
		return timesBegine;
	}
	public void setTimesBegine(Integer timesBegine) {
		this.timesBegine = timesBegine;
	}
	public Integer getTimesEnd() {
		return timesEnd;
	}
	public void setTimesEnd(Integer timesEnd) {
		this.timesEnd = timesEnd;
	}
	public String getAddTimeBegine() {
		return addTimeBegine;
	}
	public void setAddTimeBegine(String addTimeBegine) {
		this.addTimeBegine = addTimeBegine;
	}
	public String getAddTimeEnd() {
		return addTimeEnd;
	}
	public void setAddTimeEnd(String addTimeEnd) {
		this.addTimeEnd = addTimeEnd;
	}
	public String getModifiedBegine() {
		return modifiedBegine;
	}
	public void setModifiedBegine(String modifiedBegine) {
		this.modifiedBegine = modifiedBegine;
	}
	public String getModifiedEnd() {
		return modifiedEnd;
	}
	public void setModifiedEnd(String modifiedEnd) {
		this.modifiedEnd = modifiedEnd;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Integer getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public String getPayTimeSort() {
		return payTimeSort;
	}
	public void setPayTimeSort(String payTimeSort) {
		this.payTimeSort = payTimeSort;
	}
	public Integer getIsUsePoint() {
		return isUsePoint;
	}
	public void setIsUsePoint(Integer isUsePoint) {
		this.isUsePoint = isUsePoint;
	}
	public Integer getIsProduceDaily() {
		return isProduceDaily;
	}
	public void setIsProduceDaily(Integer isProduceDaily) {
		this.isProduceDaily = isProduceDaily;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(String orderMoney) {
		this.orderMoney = orderMoney;
	}
	public String getOrderAddTime() {
		return orderAddTime;
	}
	public void setOrderAddTime(String orderAddTime) {
		this.orderAddTime = orderAddTime;
	}
	public String getOrderModified() {
		return orderModified;
	}
	public void setOrderModified(String orderModified) {
		this.orderModified = orderModified;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getSyncTime() {
		return syncTime;
	}
	public void setSyncTime(String syncTime) {
		this.syncTime = syncTime;
	}
	public String getFirstConfirmTime() {
		return firstConfirmTime;
	}
	public void setFirstConfirmTime(String firstConfirmTime) {
		this.firstConfirmTime = firstConfirmTime;
	}
	public Integer getAutoConfirmNum() {
		return autoConfirmNum;
	}
	public void setAutoConfirmNum(Integer autoConfirmNum) {
		this.autoConfirmNum = autoConfirmNum;
	}
	public String getPaymentCode() {
		return paymentCode;
	}
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}
}
