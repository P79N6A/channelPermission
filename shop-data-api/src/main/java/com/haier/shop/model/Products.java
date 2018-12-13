package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Products implements Serializable{
	 /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3431631072818009195L;
    private Integer id;

    private Integer siteId;

    private String productName = "";

    private Boolean stockToTaotao = false;

    private Integer lastSyncTime = 0;

    private String taobaoId = "";

    private String wlbItemId = "";

    private String productActivityInfo = "";

    private String productTitle = "";

    private String productTags = "";

    private Integer brandId = 0;

    private String defaultImageFileId = "";

    private String detailImageFileId = "";

    private BigDecimal externalSalePrice = BigDecimal.ZERO;

    private String sellingPoint = "";

    private String productDetail = "";

    private String productBlockDetail = "";

    private String productDetail2 = "";

    /**
     * 获取 外部套装促销价格
     * @return
     */
    public BigDecimal getExternalSalePrice() {
        return this.externalSalePrice;
    }

    /**
     * 设置 外部套装促销价格
     * @param value
     */
    public void setExternalSalePrice(BigDecimal value) {
        this.externalSalePrice = value;
    }
    public String getSceneImageFileId() {
		return sceneImageFileId;
	}

	public void setSceneImageFileId(String sceneImageFileId) {
		this.sceneImageFileId = sceneImageFileId;
	}

	private String sceneImageFileId = "";

    private Integer productTypeId = 0;

    private Integer addTime;

    private Integer lastModify = 0 ;

    private Integer productCateId = 0;

    private String productCatePath = "";

    private Byte onSale = 0;

    private Boolean isStar = false;

    private Boolean isHot = false;

    private Boolean isBest= false;

    private Boolean isNew = false;

    private Boolean isSpecial = false;

    private Boolean isDelete = false;

    private Boolean bookable = false;

    private Integer bookDays = 0 ;

    private Boolean isForbidArrivalNotice = false;

    private BigDecimal supplyPrice = BigDecimal.ZERO;

    private BigDecimal packagePrice = BigDecimal.ZERO;

    private BigDecimal saleGuidePrice = BigDecimal.ZERO;

    private BigDecimal energySubsidyAmount = BigDecimal.ZERO;

    private String energySubsidyProductName = "";
    			  
    private BigDecimal internalPrice = BigDecimal.ZERO;

    private BigDecimal rankPrice = BigDecimal.ZERO;

    private String rankGroups = "";

    private String sku;

    private Integer storageType = 0;

    private String memberRanks = "";

    private String similarProductIds = "";

    private String modifiedFields = "";

    private String rejectReason = "";

    private String keywords = "";

    private String cities = "";

    private Byte state = 0;

    private Integer saleNum = 0;

    private Integer virtualSaleNum = 0;

    public Integer getVirtualSaleNum() {
		return virtualSaleNum;
	}

	public void setVirtualSaleNum(Integer virtualSaleNum) {
		this.virtualSaleNum = virtualSaleNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Integer commentNum = 0;

    private BigDecimal gradeAvg = BigDecimal.ZERO;

    private Integer heroitteCommentNum = 0;

    private BigDecimal heroitteAvg = BigDecimal.ZERO;

    private String shippingMode = "";

    private Byte giftCardType = 0;

    private Integer giftCardAmount = 0;

    private Byte isVirtual = 0;

    private Boolean isNoLimitStockProduct = false;

    private String sCode = "";

    private Boolean isNotConfirm = false;

	public Integer getIsSynch() {
		return isSynch;
	}

	public void setIsSynch(Integer isSynch) {
		this.isSynch = isSynch;
	}

	private Integer isSynch;
    private Integer packageId = 0;

    private BigDecimal specailPrice = BigDecimal.ZERO;

    private Boolean multiStorage = false;

    private Byte inspectType = 0;

    private BigDecimal limitedPrice = BigDecimal.ZERO;

    private Boolean isNotPromotion = false;

    private Byte productO2OType = 0;

    private Boolean isGift = false;

    private String conTaxCode = "";

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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Boolean getStockToTaotao() {
		return stockToTaotao;
	}

	public void setStockToTaotao(Boolean stockToTaotao) {
		this.stockToTaotao = stockToTaotao;
	}

	public Integer getLastSyncTime() {
		return lastSyncTime;
	}

	public void setLastSyncTime(Integer lastSyncTime) {
		this.lastSyncTime = lastSyncTime;
	}

	public String getTaobaoId() {
		return taobaoId;
	}

	public void setTaobaoId(String taobaoId) {
		this.taobaoId = taobaoId;
	}

	public String getWlbItemId() {
		return wlbItemId;
	}

	public void setWlbItemId(String wlbItemId) {
		this.wlbItemId = wlbItemId;
	}

	public String getProductActivityInfo() {
		return productActivityInfo;
	}

	public void setProductActivityInfo(String productActivityInfo) {
		this.productActivityInfo = productActivityInfo;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public String getProductTags() {
		return productTags;
	}

	public void setProductTags(String productTags) {
		this.productTags = productTags;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getDefaultImageFileId() {
		return defaultImageFileId;
	}

	public void setDefaultImageFileId(String defaultImageFileId) {
		this.defaultImageFileId = defaultImageFileId;
	}

	public String getDetailImageFileId() {
		return detailImageFileId;
	}

	public void setDetailImageFileId(String detailImageFileId) {
		this.detailImageFileId = detailImageFileId;
	}

	public Integer getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Integer productTypeId) {
		this.productTypeId = productTypeId;
	}

	public Integer getAddTime() {
		return addTime;
	}

	public void setAddTime(Integer addTime) {
		this.addTime = addTime;
	}

	public Integer getLastModify() {
		return lastModify;
	}

	public void setLastModify(Integer lastModify) {
		this.lastModify = lastModify;
	}

	public Integer getProductCateId() {
		return productCateId;
	}

	public void setProductCateId(Integer productCateId) {
		this.productCateId = productCateId;
	}

	public String getProductCatePath() {
		return productCatePath;
	}

	public void setProductCatePath(String productCatePath) {
		this.productCatePath = productCatePath;
	}

	public Byte getOnSale() {
		return onSale;
	}

	public void setOnSale(Byte onSale) {
		this.onSale = onSale;
	}

	public Boolean getIsStar() {
		return isStar;
	}

	public void setIsStar(Boolean isStar) {
		this.isStar = isStar;
	}

	public Boolean getIsHot() {
		return isHot;
	}

	public void setIsHot(Boolean isHot) {
		this.isHot = isHot;
	}

	public Boolean getIsBest() {
		return isBest;
	}

	public void setIsBest(Boolean isBest) {
		this.isBest = isBest;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public void setIsNew(Boolean isNew) {
		this.isNew = isNew;
	}

	public Boolean getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(Boolean isSpecial) {
		this.isSpecial = isSpecial;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Boolean getBookable() {
		return bookable;
	}

	public void setBookable(Boolean bookable) {
		this.bookable = bookable;
	}

	public Integer getBookDays() {
		return bookDays;
	}

	public void setBookDays(Integer bookDays) {
		this.bookDays = bookDays;
	}

	public Boolean getIsForbidArrivalNotice() {
		return isForbidArrivalNotice;
	}

	public void setIsForbidArrivalNotice(Boolean isForbidArrivalNotice) {
		this.isForbidArrivalNotice = isForbidArrivalNotice;
	}

	public BigDecimal getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(BigDecimal supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public BigDecimal getPackagePrice() {
		return packagePrice;
	}

	public void setPackagePrice(BigDecimal packagePrice) {
		this.packagePrice = packagePrice;
	}

	public BigDecimal getSaleGuidePrice() {
		return saleGuidePrice;
	}

	public void setSaleGuidePrice(BigDecimal saleGuidePrice) {
		this.saleGuidePrice = saleGuidePrice;
	}

	public BigDecimal getEnergySubsidyAmount() {
		return energySubsidyAmount;
	}

	public void setEnergySubsidyAmount(BigDecimal energySubsidyAmount) {
		this.energySubsidyAmount = energySubsidyAmount;
	}

	public String getEnergySubsidyProductName() {
		return energySubsidyProductName;
	}

	public void setEnergySubsidyProductName(String energySubsidyProductName) {
		this.energySubsidyProductName = energySubsidyProductName;
	}

	public BigDecimal getInternalPrice() {
		return internalPrice;
	}

	public void setInternalPrice(BigDecimal internalPrice) {
		this.internalPrice = internalPrice;
	}

	public BigDecimal getRankPrice() {
		return rankPrice;
	}

	public void setRankPrice(BigDecimal rankPrice) {
		this.rankPrice = rankPrice;
	}

	public String getRankGroups() {
		return rankGroups;
	}

	public void setRankGroups(String rankGroups) {
		this.rankGroups = rankGroups;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getStorageType() {
		return storageType;
	}

	public void setStorageType(Integer storageType) {
		this.storageType = storageType;
	}

	public String getMemberRanks() {
		return memberRanks;
	}

	public void setMemberRanks(String memberRanks) {
		this.memberRanks = memberRanks;
	}

	public String getSimilarProductIds() {
		return similarProductIds;
	}

	public void setSimilarProductIds(String similarProductIds) {
		this.similarProductIds = similarProductIds;
	}

	

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public Integer getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Integer saleNum) {
		this.saleNum = saleNum;
	}



	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public BigDecimal getGradeAvg() {
		return gradeAvg;
	}

	public void setGradeAvg(BigDecimal gradeAvg) {
		this.gradeAvg = gradeAvg;
	}

	public Integer getHeroitteCommentNum() {
		return heroitteCommentNum;
	}

	public void setHeroitteCommentNum(Integer heroitteCommentNum) {
		this.heroitteCommentNum = heroitteCommentNum;
	}

	public BigDecimal getHeroitteAvg() {
		return heroitteAvg;
	}

	public void setHeroitteAvg(BigDecimal heroitteAvg) {
		this.heroitteAvg = heroitteAvg;
	}

	public String getShippingMode() {
		return shippingMode;
	}

	public void setShippingMode(String shippingMode) {
		this.shippingMode = shippingMode;
	}

	public Byte getGiftCardType() {
		return giftCardType;
	}

	public void setGiftCardType(Byte giftCardType) {
		this.giftCardType = giftCardType;
	}

	public Integer getGiftCardAmount() {
		return giftCardAmount;
	}

	public void setGiftCardAmount(Integer giftCardAmount) {
		this.giftCardAmount = giftCardAmount;
	}

	public Byte getIsVirtual() {
		return isVirtual;
	}

	public void setIsVirtual(Byte isVirtual) {
		this.isVirtual = isVirtual;
	}

	public Boolean getIsNoLimitStockProduct() {
		return isNoLimitStockProduct;
	}

	public void setIsNoLimitStockProduct(Boolean isNoLimitStockProduct) {
		this.isNoLimitStockProduct = isNoLimitStockProduct;
	}

	public String getsCode() {
		return sCode;
	}

	public void setsCode(String sCode) {
		this.sCode = sCode;
	}

	public Boolean getIsNotConfirm() {
		return isNotConfirm;
	}

	public void setIsNotConfirm(Boolean isNotConfirm) {
		this.isNotConfirm = isNotConfirm;
	}

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
		this.packageId = packageId;
	}

	public BigDecimal getSpecailPrice() {
		return specailPrice;
	}

	public void setSpecailPrice(BigDecimal specailPrice) {
		this.specailPrice = specailPrice;
	}

	public Boolean getMultiStorage() {
		return multiStorage;
	}

	public void setMultiStorage(Boolean multiStorage) {
		this.multiStorage = multiStorage;
	}

	public Byte getInspectType() {
		return inspectType;
	}

	public void setInspectType(Byte inspectType) {
		this.inspectType = inspectType;
	}

	public BigDecimal getLimitedPrice() {
		return limitedPrice;
	}

	public void setLimitedPrice(BigDecimal limitedPrice) {
		this.limitedPrice = limitedPrice;
	}

	public Boolean getIsNotPromotion() {
		return isNotPromotion;
	}

	public void setIsNotPromotion(Boolean isNotPromotion) {
		this.isNotPromotion = isNotPromotion;
	}

	public Byte getProductO2OType() {
		return productO2OType;
	}

	public void setProductO2OType(Byte productO2OType) {
		this.productO2OType = productO2OType;
	}

	public Boolean getIsGift() {
		return isGift;
	}

	public void setIsGift(Boolean isGift) {
		this.isGift = isGift;
	}

	public String getConTaxCode() {
		return conTaxCode;
	}

	public void setConTaxCode(String conTaxCode) {
		this.conTaxCode = conTaxCode;
	}
	private String limitCityIds;

	public String getLimitCityIds() {
		return limitCityIds;
	}

	public void setLimitCityIds(String limitCityIds) {
		this.limitCityIds = limitCityIds;
	}

	public String getSellingPoint() {
		return sellingPoint;
	}

	public void setSellingPoint(String sellingPoint) {
		this.sellingPoint = sellingPoint;
	}

	public String getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}

    public String getProductBlockDetail() {
        return productBlockDetail;
    }

    public void setProductBlockDetail(String productBlockDetail) {
        this.productBlockDetail = productBlockDetail;
    }

    public String getProductDetail2() {
        return productDetail2;
    }

    public void setProductDetail2(String productDetail2) {
        this.productDetail2 = productDetail2;
    }

    public String getModifiedFields() {
        return modifiedFields;
    }

    public void setModifiedFields(String modifiedFields) {
        this.modifiedFields = modifiedFields;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getCities() {
        return cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }

    public Boolean getStar() {
        return isStar;
    }

    public void setStar(Boolean star) {
        isStar = star;
    }

    public Boolean getHot() {
        return isHot;
    }

    public void setHot(Boolean hot) {
        isHot = hot;
    }

    public Boolean getBest() {
        return isBest;
    }

    public void setBest(Boolean best) {
        isBest = best;
    }

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean aNew) {
        isNew = aNew;
    }

    public Boolean getSpecial() {
        return isSpecial;
    }

    public void setSpecial(Boolean special) {
        isSpecial = special;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public Boolean getForbidArrivalNotice() {
        return isForbidArrivalNotice;
    }

    public void setForbidArrivalNotice(Boolean forbidArrivalNotice) {
        isForbidArrivalNotice = forbidArrivalNotice;
    }

    public Boolean getNoLimitStockProduct() {
        return isNoLimitStockProduct;
    }

    public void setNoLimitStockProduct(Boolean noLimitStockProduct) {
        isNoLimitStockProduct = noLimitStockProduct;
    }

    public Boolean getNotConfirm() {
        return isNotConfirm;
    }

    public void setNotConfirm(Boolean notConfirm) {
        isNotConfirm = notConfirm;
    }

    public Boolean getNotPromotion() {
        return isNotPromotion;
    }

    public void setNotPromotion(Boolean notPromotion) {
        isNotPromotion = notPromotion;
    }

    public Boolean getGift() {
        return isGift;
    }

    public void setGift(Boolean gift) {
        isGift = gift;
    }
}