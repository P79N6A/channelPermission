package com.haier.shop.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class ZfbOrderMatchingDto  implements Serializable{
    private Long id;

    private String accountsNo;

    private String businessNo;

    private String ordersn;

    private String productName;

    private Date createTime;

    private String memberLoginId;

    private BigDecimal incomeMoney;

    private BigDecimal expenditureMoney;

    private BigDecimal accountBalance;

    private String paymentCode;

    private String paymentName;

    private String businessTypeCode;

    private String businessTypeName;

    private String remake;

    private String taobaoBusinessType;

    private String source;

    private BigDecimal orderamount;
    private Integer differenceStatus;
    private Integer oId;
    private BigDecimal billAmount;
    
    private Integer number;
    private String couponAmount;//网单使用优惠券sum
    private String esAmount;//节能补贴金额sum
    private String itemShareAmount;//订单优惠价格分摊sum
    private String points;//网单使用积分sum
    private String jfbAmount;//'网单集分宝sum
    private String djAmount;//网单点券sum
    private String hbAmount;//网单红包sum
    
    private String productAmount;//网单金额sum
    private String shippingAmount;//淘宝运费
    private String totalEsAmount;//总节能补贴
    private String productAmounto;//优惠后金额
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDifferenceStatus() {
		return differenceStatus;
	}

	public void setDifferenceStatus(Integer differenceStatus) {
		this.differenceStatus = differenceStatus;
	}

	public String getAccountsNo() {
        return accountsNo;
    }

    public void setAccountsNo(String accountsNo) {
        this.accountsNo = accountsNo == null ? null : accountsNo.trim();
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo == null ? null : businessNo.trim();
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn == null ? null : ordersn.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMemberLoginId() {
        return memberLoginId;
    }

    public void setMemberLoginId(String memberLoginId) {
        this.memberLoginId = memberLoginId == null ? null : memberLoginId.trim();
    }

 

    public BigDecimal getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(BigDecimal incomeMoney) {
		this.incomeMoney = incomeMoney;
	}

	public BigDecimal getExpenditureMoney() {
		return expenditureMoney;
	}

	public void setExpenditureMoney(BigDecimal expenditureMoney) {
		this.expenditureMoney = expenditureMoney;
	}

	public BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}

	public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode == null ? null : paymentCode.trim();
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName == null ? null : paymentName.trim();
    }

    public String getBusinessTypeCode() {
        return businessTypeCode;
    }

    public void setBusinessTypeCode(String businessTypeCode) {
        this.businessTypeCode = businessTypeCode == null ? null : businessTypeCode.trim();
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName == null ? null : businessTypeName.trim();
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake == null ? null : remake.trim();
    }

    public String getTaobaoBusinessType() {
        return taobaoBusinessType;
    }

    public void setTaobaoBusinessType(String taobaoBusinessType) {
        this.taobaoBusinessType = taobaoBusinessType == null ? null : taobaoBusinessType.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public BigDecimal getOrderamount() {
        return orderamount;
    }

    public void setOrderamount(BigDecimal orderamount) {
        this.orderamount = orderamount;
    }

	public Integer getoId() {
		return oId;
	}

	public void setoId(Integer oId) {
		this.oId = oId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(String couponAmount) {
		this.couponAmount = couponAmount;
	}

	public String getEsAmount() {
		return esAmount;
	}

	public void setEsAmount(String esAmount) {
		this.esAmount = esAmount;
	}

	public String getItemShareAmount() {
		return itemShareAmount;
	}

	public void setItemShareAmount(String itemShareAmount) {
		this.itemShareAmount = itemShareAmount;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getJfbAmount() {
		return jfbAmount;
	}

	public void setJfbAmount(String jfbAmount) {
		this.jfbAmount = jfbAmount;
	}

	public String getDjAmount() {
		return djAmount;
	}

	public void setDjAmount(String djAmount) {
		this.djAmount = djAmount;
	}

	public String getHbAmount() {
		return hbAmount;
	}

	public void setHbAmount(String hbAmount) {
		this.hbAmount = hbAmount;
	}

	public String getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(String productAmount) {
		this.productAmount = productAmount;
	}

	public String getShippingAmount() {
		return shippingAmount;
	}

	public void setShippingAmount(String shippingAmount) {
		this.shippingAmount = shippingAmount;
	}

	public String getTotalEsAmount() {
		return totalEsAmount;
	}

	public BigDecimal getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}

	public void setTotalEsAmount(String totalEsAmount) {
		this.totalEsAmount = totalEsAmount;
	}

	public String getProductAmounto() {
		return productAmounto;
	}

	public void setProductAmounto(String productAmounto) {
		this.productAmounto = productAmounto;
	}
	
}
