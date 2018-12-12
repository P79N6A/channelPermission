package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


//在table中前标‘---’为新增字段，前标‘//’为在原来基础上减去的字段

public class OrderProducts implements Serializable {
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
    private Integer isMakeReceipt;// 开票状态
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
    private String couponCode;
    private	String couponCodeValue;
    private int count;//用来取得分组数量的
    private int parentId;//用来比对产业
    
    
    
    public String getcOrderSn() {
		return cOrderSn;
	}

	public void setcOrderSn(String cOrderSn) {
		this.cOrderSn = cOrderSn;
	}

	public String getCouponCodeValue() {
		return couponCodeValue;
	}

	public void setCouponCodeValue(String couponCodeValue) {
		this.couponCodeValue = couponCodeValue;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

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
    public int getCount() {
		return count;
	}
    public void setCount(int count) {
		this.count = count;
	}
    public int getParentId() {
		return parentId;
	}
    public void setParentId(int parentId) {
		this.parentId = parentId;
	}
}