package com.haier.shop.model;

import java.io.Serializable;

/*
 * 转单关系配置表
 */
public class ChangeOrderConfig implements Serializable{

	private static final long serialVersionUID = 4835132446327437420L;
	
	private Integer id;//主键自增

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String orderSourceCode;//订单来源code


	public String getOrderSourceCode() {
		return orderSourceCode;
	}

	public void setOrderSourceCode(String orderSourceCode) {
		this.orderSourceCode = orderSourceCode;
	}

	private String orderSourceName;//订单来源名称

	public String getOrderSourceName() {
		return orderSourceName;
	}

	public void setOrderSourceName(String orderSourceName) {
		this.orderSourceName = orderSourceName;
	}
	
	private Integer regionId;//区县id

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	private String regionName;//区县名称

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
	private Integer brandId;//品牌id

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	private String brandName;//品牌名称

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	private Integer cateId;//品类id

	public Integer getCateId() {
		return cateId;
	}

	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}

	private String cateName;//品类名称

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	
	private Integer customerId;//客户id

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	private String customerName;//客户名称

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	private String customerStoreCode;//客户88码

	public String getCustomerStoreCode() {
		return customerStoreCode;
	}

	public void setCustomerStoreCode(String customerStoreCode) {
		this.customerStoreCode = customerStoreCode;
	}
	
	private Integer status;//'启用状态 0 不启用 1 启用'

	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	private String productGroupCode;//产品组code
	

	public String getProductGroupCode() {
		return productGroupCode;
	}

	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}
	
	private String productGroupName;//产品组名称
	
	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	private String createTime;//创建时间

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	private String updateTime;//更新时间

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	private Integer isDispatching;//是否指定派工  0不指定  1指定 默认0

	public Integer getIsDispatching() {
		return isDispatching;
	}

	public void setIsDispatching(Integer isDispatching) {
		this.isDispatching = isDispatching;
	}
}
