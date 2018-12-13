package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.soap.Text;

public class ProductCates implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2147560518014353270L;

	private Integer id;

    private Integer siteId;

    private Integer parentId;
	private int rootId;

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
    
    private int count;
    
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	private String deliveryDesc;

	public int getRootId() {
		return rootId;
	}

	public void setRootId(int rootId) {
		this.rootId = rootId;
	}
}