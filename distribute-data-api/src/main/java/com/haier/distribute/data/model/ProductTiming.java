package com.haier.distribute.data.model;

import java.io.Serializable;
import java.math.BigDecimal;



public class ProductTiming   implements Serializable {
	  /**
	 * 
	 */
	private static final long serialVersionUID = -402088046547107512L;

	/**
     *Comment for <code>serialVersionUID</code>
     */
	  private Integer id;

	    private String sku;

	    private String productName;

	    private String productTitle;

	    private BigDecimal packagePrice;
	    
	    private String brandName;

	    private BigDecimal saleGuidePrice;

	    private byte onSale;

	    private String typeName;

	    private String productDetail;

	    private String productDetail2;

	    private String cateName;

	    private byte isDelete;

	    private int packageId;


	    private BigDecimal price;
	    
	    private String priceStartTime;
	    
	    private String priceEndTime;
	    
	    private BigDecimal salePrice;
	    
	    private String code;
	    
	    private String regionName;
	    
	    private byte regionType;
	    
	    private byte activeFlag;
	    

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getSku() {
			return sku;
		}

		public void setSku(String sku) {
			this.sku = sku;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public String getProductTitle() {
			return productTitle;
		}

		public void setProductTitle(String productTitle) {
			this.productTitle = productTitle;
		}

		public BigDecimal getPackagePrice() {
			return packagePrice;
		}

		public void setPackagePrice(BigDecimal packagePrice) {
			this.packagePrice = packagePrice;
		}

		public String getBrandName() {
			return brandName;
		}

		public void setBrandName(String brandName) {
			this.brandName = brandName;
		}

		public BigDecimal getSaleGuidePrice() {
			return saleGuidePrice;
		}

		public void setSaleGuidePrice(BigDecimal saleGuidePrice) {
			this.saleGuidePrice = saleGuidePrice;
		}

		public byte getOnSale() {
			return onSale;
		}

		public void setOnSale(byte onSale) {
			this.onSale = onSale;
		}

		public String getTypeName() {
			return typeName;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

		public String getProductDetail() {
			return productDetail;
		}

		public void setProductDetail(String productDetail) {
			this.productDetail = productDetail;
		}

		public String getProductDetail2() {
			return productDetail2;
		}

		public void setProductDetail2(String productDetail2) {
			this.productDetail2 = productDetail2;
		}

		public String getCateName() {
			return cateName;
		}

		public void setCateName(String cateName) {
			this.cateName = cateName;
		}

		public byte getIsDelete() {
			return isDelete;
		}

		public void setIsDelete(byte isDelete) {
			this.isDelete = isDelete;
		}

		public int getPackageId() {
			return packageId;
		}

		public void setPackageId(int packageId) {
			this.packageId = packageId;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}

		public String getPriceStartTime() {
			return priceStartTime;
		}

		public void setPriceStartTime(String priceStartTime) {
			this.priceStartTime = priceStartTime;
		}

		public BigDecimal getSalePrice() {
			return salePrice;
		}

		public void setSalePrice(BigDecimal salePrice) {
			this.salePrice = salePrice;
		}

		public String getPriceEndTime() {
			return priceEndTime;
		}

		public void setPriceEndTime(String priceEndTime) {
			this.priceEndTime = priceEndTime;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getRegionName() {
			return regionName;
		}

		public void setRegionName(String regionName) {
			this.regionName = regionName;
		}

		public byte getRegionType() {
			return regionType;
		}

		public void setRegionType(byte regionType) {
			this.regionType = regionType;
		}

		public byte getActiveFlag() {
			return activeFlag;
		}

		public void setActiveFlag(byte activeFlag) {
			this.activeFlag = activeFlag;
		}
	    
	   
	

    
}