package com.haier.shop.model;

import java.io.Serializable;

/**
 * 页面网单回车Bean
 * 
 * @Filename: WorkOrderBean.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 *
 */
public class WorkOrderBean implements Serializable {
	private static final long serialVersionUID = -8237033607981507761L;
	private String corderSn; // 网单号
	private String orderSn; // 订单号
	private String productName; // 宝贝名称
	private String number; // 数量
	private String sku; // SKU编码
	private Double productAmount; // 发票金额(商品金额)
	private String buyerMobile; // 收货电话mobile
	private String buyer; // 收货联系人consignee
	private String netPointId; // 配送网点 //没有
	private String netPointName;//网点名字
	private String createTime; // 下单时间 //没有
	private String payTime; // 付款时间
	private String region; // 所属区域
	private String regionName; // 所属区域
	private String address; // 详细地址
	private String shippingTime; // 配送时效
	private String company; // 所属工贸 //没有
	private String phone; // 物流中心 //没有
	private String source; // 订单来源
	private String corderPrimary; // 订单ID
	private String orderPrimary; // 网单ID
	private String storeId; // 商铺ID
	private String centerType; // 物流中心类别
	private String sCode; // 存储从网单接口中获取的sCode，供判断是否发给SQM
	private String storeType; // 库存类别
	private String channelCode; // 订单渠道
	private String channelName;
	private String nextUserName;
	private String tbOrderSn;
	private String status;
	private String netOrderStatus;
	private String lessShipTime;
	private String price;
	private String agant;
	private String stockType;
	private String isProduceDaily;
	private String sourceOrderSn;
	private String finishTime;
	private String addtime;

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getIsProduceDaily() {
    return isProduceDaily;
  }

  public void setIsProduceDaily(String isProduceDaily) {
    this.isProduceDaily = isProduceDaily;
  }

  public String getSourceOrderSn() {
    return sourceOrderSn;
  }

  public void setSourceOrderSn(String sourceOrderSn) {
    this.sourceOrderSn = sourceOrderSn;
  }

  public String getFinishTime() {
    return finishTime;
  }

  public void setFinishTime(String finishTime) {
    this.finishTime = finishTime;
  }

  public String getAgant() {
    return agant;
  }

  public void setAgant(String agant) {
    this.agant = agant;
  }

  public String getNetOrderStatus() {
		return netOrderStatus;
	}

	public void setNetOrderStatus(String netOrderStatus) {
		this.netOrderStatus = netOrderStatus;
	}

	public String getLessShipTime() {
		return lessShipTime;
	}

	public void setLessShipTime(String lessShipTime) {
		this.lessShipTime = lessShipTime;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTbOrderSn() {
		return tbOrderSn;
	}

	public void setTbOrderSn(String tbOrderSn) {
		this.tbOrderSn = tbOrderSn;
	}

	public String getNextUserName() {
		return nextUserName;
	}

	public void setNextUserName(String nextUserName) {
		this.nextUserName = nextUserName;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

	public String getCorderSn() {
		return corderSn;
	}

	public void setCorderSn(String corderSn) {
		this.corderSn = corderSn;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Double getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(Double productAmount) {
		this.productAmount = productAmount;
	}

	public String getBuyerMobile() {
		return buyerMobile;
	}

	public void setBuyerMobile(String buyerMobile) {
		this.buyerMobile = buyerMobile;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getNetPointId() {
		return netPointId;
	}

	public void setNetPointId(String netPointId) {
		this.netPointId = netPointId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShippingTime() {
		return shippingTime;
	}

	public void setShippingTime(String shippingTime) {
		this.shippingTime = shippingTime;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCorderPrimary() {
		return corderPrimary;
	}

	public void setCorderPrimary(String corderPrimary) {
		this.corderPrimary = corderPrimary;
	}

	public String getOrderPrimary() {
		return orderPrimary;
	}

	public void setOrderPrimary(String orderPrimary) {
		this.orderPrimary = orderPrimary;
	}

	@Override
	public String toString() {
		return "WorkOrderBean [corderSn=" + corderSn + ", orderSn=" + orderSn + ", productName=" + productName
				+ ", number=" + number + ", sku=" + sku + ", productAmount=" + productAmount + ", buyerMobile="
				+ buyerMobile + ", buyer=" + buyer + ", netPointId=" + netPointId + ", createTime=" + createTime
				+ ", payTime=" + payTime + ", regionName=" + regionName + ", address=" + address + ", shippingTime="
				+ shippingTime + ", company=" + company + ", phone=" + phone + ", source=" + source + ", corderPrimary="
				+ corderPrimary + ", orderPrimary=" + orderPrimary + "]";
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getCenterType() {
		return centerType;
	}

	public void setCenterType(String centerType) {
		this.centerType = centerType;
	}

	public String getsCode() {
		return sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public String getNetPointName() {
		return netPointName;
	}

	public void setNetPointName(String netPointName) {
		this.netPointName = netPointName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
