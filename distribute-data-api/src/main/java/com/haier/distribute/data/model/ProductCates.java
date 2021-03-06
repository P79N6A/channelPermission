package com.haier.distribute.data.model;

import java.util.Date;

public class ProductCates {
    private Integer id;

    private Integer siteId;

    private Integer parentId;

    private String parentPath;

    private String cateName;

    private Byte sortNum;

    private Byte isHidden;
 
    private Integer lastModifyTime;

    private Date modified;

    private String cateFilters;

    private String extendCateFilters;

    private String priceRange;

    private String packDesc;

    private String afterService;
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public Byte getSortNum() {
		return sortNum;
	}

	public void setSortNum(Byte sortNum) {
		this.sortNum = sortNum;
	}

	public Byte getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(Byte isHidden) {
		this.isHidden = isHidden;
	}

	public Integer getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Integer lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getCateFilters() {
		return cateFilters;
	}

	public void setCateFilters(String cateFilters) {
		this.cateFilters = cateFilters;
	}

	public String getExtendCateFilters() {
		return extendCateFilters;
	}

	public void setExtendCateFilters(String extendCateFilters) {
		this.extendCateFilters = extendCateFilters;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public String getPackDesc() {
		return packDesc;
	}

	public void setPackDesc(String packDesc) {
		this.packDesc = packDesc;
	}

	public String getAfterService() {
		return afterService;
	}

	public void setAfterService(String afterService) {
		this.afterService = afterService;
	}

	public String getDeliveryDesc() {
		return deliveryDesc;
	}

	public void setDeliveryDesc(String deliveryDesc) {
		this.deliveryDesc = deliveryDesc;
	}

	private String deliveryDesc;
    
}