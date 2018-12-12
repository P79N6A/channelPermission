package com.haier.distribute.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Products implements Serializable{
	 /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3431631072818009195L;
    private Integer id;

    private Integer siteId;

    private String productName;

    private Boolean stockToTaotao;

    private Integer lastSyncTime;

    private String taobaoId;

    private String wlbItemId;

    private String productActivityInfo;

    private String productTitle;

    private String productTags;

    private Integer brandId;

    private String defaultImageFileId;

    private String detailImageFileId;

    public String getSceneImageFileId() {
		return sceneImageFileId;
	}

	public void setSceneImageFileId(String sceneImageFileId) {
		this.sceneImageFileId = sceneImageFileId;
	}

	private String sceneImageFileId;

    private Integer productTypeId;

    private Integer addTime;

    private Integer lastModify;

    private Integer productCateId;

    private String productCatePath;

    private Byte onSale;

    private Boolean isStar;

    private Boolean isHot;

    private Boolean isBest;

    private Boolean isNew;

    private Boolean isSpecial;

    private Boolean isDelete;

    private Boolean bookable;

    private Integer bookDays;

    private Boolean isForbidArrivalNotice;

    private BigDecimal supplyPrice;

    private BigDecimal packagePrice;

    private BigDecimal saleGuidePrice;

    private BigDecimal energySubsidyAmount;

    private String energySubsidyProductName;
    			  
    private BigDecimal internalPrice;

    private BigDecimal rankPrice;

    private String rankGroups;

    private String sku;

    private Boolean storageType;

    private String memberRanks;

    private String similarProductIds;

    private String rejectReason;

    private Byte state;

    private Integer saleNum;

    private Integer virtualSaleNum;

    public Integer getVirtualSaleNum() {
		return virtualSaleNum;
	}

	public void setVirtualSaleNum(Integer virtualSaleNum) {
		this.virtualSaleNum = virtualSaleNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private Integer commentNum;

    private BigDecimal gradeAvg;

    private Integer heroitteCommentNum;

    private BigDecimal heroitteAvg;

    private String shippingMode;

    private Byte giftCardType;

    private Integer giftCardAmount;

    private Byte isVirtual;

    private Boolean isNoLimitStockProduct;

    private String sCode;

    private Boolean isNotConfirm;

    private Integer packageId;

    private BigDecimal specailPrice;

    private Boolean multiStorage;

    private Byte inspectType;

    private BigDecimal limitedPrice;

    private Boolean isNotPromotion;

    private Byte productO2OType;

    private Boolean isGift;

    private String conTaxCode;

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

	public Boolean getStorageType() {
		return storageType;
	}

	public void setStorageType(Boolean storageType) {
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

	

   
}