package com.haier.shop.model;



public class OrderRepairsVo extends OrderRepairs{

    private static final long serialVersionUID = 2145777004809127705L;
    private String cOrderSnId;//用来接收网单号

	private Integer  province;//收货地址中国省份

	private Integer  city;//收货地址中的城市

	private Integer  region;//收货地址中城市中的区

	private String addTimeTs;//用来接收申请时间
	
	private String orderSn;//订单号
	
	private String price;//销售金额
	
	private String status;//用来接收
	
	private String paymentStatusTS;//用来接收当前付款状态
	
	private String receiptStatusTS;//用来接收当前发票状态
	
	private String storageStatusTS;//用来接收当前货物状态
	
	private String sku;//物料编码
	
	private String stocktype;//类型
	
	private String userAcceptTime;//用户签收时间
	
	private String tbOrderSn;//网单ID
	
	private String  menuflag;//用来标示从什么地方来调用的接口
	
	
	private String source;//来源编码
	
	private String sourceOrderSn;//	来源订单号
	
	private String productType;//商品类型
	
	
	private String requeStservieDateTS;//要求服务器时间 生命一个String类型来接收
	
	private String invoiceId;//发票主键
	
	private String storeCode;//发起二次鉴定时 需要vom返回的c码查到库位传给HP
	
	private String repairHPrecordsId;//用来接收HP返回数据表主键 用来发起三次鉴定工单时更改推送三次坚定状态
    /**
     * 物流模式
     */
	private String shippingMode;
	private String terminationReason;  //终止逆向单原因
    /**
     *
     */
	private Integer isCd;

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public Integer getRegion() {
		return region;
	}

	public void setRegion(Integer region) {
		this.region = region;
	}

	public String getRepairHPrecordsId() {
		return repairHPrecordsId;
	}

	public void setRepairHPrecordsId(String repairHPrecordsId) {
		this.repairHPrecordsId = repairHPrecordsId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}


	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getRequeStservieDateTS() {
		return requeStservieDateTS;
	}

	public void setRequeStservieDateTS(String requeStservieDateTS) {
		this.requeStservieDateTS = requeStservieDateTS;
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

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getMenuflag() {
		return menuflag;
	}

	public void setMenuflag(String menuflag) {
		this.menuflag = menuflag;
	}

	public String getTbOrderSn() {
		return tbOrderSn;
	}

	public void setTbOrderSn(String tbOrderSn) {
		this.tbOrderSn = tbOrderSn;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getStocktype() {
		return stocktype;
	}

	public void setStocktype(String stocktype) {
		this.stocktype = stocktype;
	}

	public String getUserAcceptTime() {
		return userAcceptTime;
	}

	public void setUserAcceptTime(String userAcceptTime) {
		this.userAcceptTime = userAcceptTime;
	}

	public String getPaymentStatusTS() {
		return paymentStatusTS;
	}

	public void setPaymentStatusTS(String paymentStatusTS) {
		this.paymentStatusTS = paymentStatusTS;
	}

	public String getReceiptStatusTS() {
		return receiptStatusTS;
	}

	public void setReceiptStatusTS(String receiptStatusTS) {
		this.receiptStatusTS = receiptStatusTS;
	}

	public String getStorageStatusTS() {
		return storageStatusTS;
	}

	public void setStorageStatusTS(String storageStatusTS) {
		this.storageStatusTS = storageStatusTS;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddTimeTs() {
		return addTimeTs;
	}

	public void setAddTimeTs(String addTimeTs) {
		this.addTimeTs = addTimeTs;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}


	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getcOrderSnId() {
		return cOrderSnId;
	}

	public void setcOrderSnId(String cOrderSnId) {
		this.cOrderSnId = cOrderSnId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String type;


    public String getShippingMode() {
        return shippingMode;
    }

    public void setShippingMode(String shippingMode) {
        this.shippingMode = shippingMode;
    }

	public String getTerminationReason() {
		return terminationReason;
	}

	public void setTerminationReason(String terminationReason) {
		this.terminationReason = terminationReason;
	}

    public Integer getIsCd() {
        return isCd;
    }

    public void setIsCd(Integer isCd) {
        this.isCd = isCd;
    }
}
