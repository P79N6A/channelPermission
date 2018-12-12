package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class VOMSynSubOrderRequire implements Serializable {

	// 行号 订单中有多行物料时，物料所在的行数
	private String itemNo = "";
	// 批次 产品状态
	private String storageType = "";
	// 客户产品编码
	private String productCode = "";
	// 客户产品编码
	private String hrCode = "";
	// 海尔产品编码 日日顺物流生成
	private String prodes = "";
	// 体积
	private String volume = "";
	// 重量
	private String weight = "";
	// 数量
	private Integer number = 0;
	// 单价
	private Double unprice = 0.0;
	// 前续订单行号
	private String reitem = "";
	
	
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getStorageType() {
		return storageType;
	}
	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getHrCode() {
		return hrCode;
	}
	public void setHrCode(String hrCode) {
		this.hrCode = hrCode;
	}
	public String getProdes() {
		return prodes;
	}
	public void setProdes(String prodes) {
		this.prodes = prodes;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Double getUnprice() {
		return unprice;
	}
	public void setUnprice(Double unprice) {
		this.unprice = unprice;
	}
	public String getReitem() {
		return reitem;
	}
	public void setReitem(String reitem) {
		this.reitem = reitem;
	}
	
	
	
	
}
