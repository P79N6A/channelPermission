/*     */ package com.haier.shop.model;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.math.BigDecimal;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class BasStores
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -264584636750741280L;
/*     */   private Integer storeId;
/*     */   private String storeCode;
/*     */   private String storeName;
/*     */   private Integer storeType;
/*     */   private String siteCode;
/*     */   private String storeImageFileId;
/*     */   private Integer provinceId;
/*     */   private Integer cityId;
/*     */   private Integer regionId;
/*     */   private String address;
/*     */   private BigDecimal longitude;
/*     */   private BigDecimal latitude;
/*     */   private String industryTrade;
/*     */   private String qrCodeUrl;
/*     */   private Integer isCod;
/*     */   private Integer shippingTimeLimit;
/*     */   private String contacts;
/*     */   private String contactsMobile;
/*     */   private Integer isSyncEc;
/*     */   private Integer status;
/*     */   private Integer createMuserId;
/*     */   private Date createTime;
/*     */   private Integer lastUpdateMuserId;
/*     */   private Integer lastUpdateUserId;
/*     */   private Date lastUpdateTime;
/*     */   private String createMuserName;
/*     */   private String lastUpdateMuserName;
/*     */   private String lastUpdateUserName;
/*     */   private String previewUrl;
/*     */   private String storeTypeName;
/*     */   private String isSyncEcName;
/*     */   private String statusName;
/*     */   private String bankName;
/*     */   private String bankAccount;
/*     */   private Integer isShunguang;
/*  92 */   public static final Integer STOR_TYPE_1 = Integer.valueOf(1);
/*  94 */   public static final Integer STOR_TYPE_2 = Integer.valueOf(2);
/*  96 */   public static final Integer STOR_TYPE_3 = Integer.valueOf(3);
/*  98 */   public static final Integer STOR_TYPE_4 = Integer.valueOf(4);
/* 101 */   public static final Integer IS_CODE_YES = Integer.valueOf(1);
/* 103 */   public static final Integer IS_CODE_NO = Integer.valueOf(0);
/* 106 */   public static final Integer IS_SYNC_EC_YES = Integer.valueOf(1);
/* 108 */   public static final Integer IS_SYNC_EC_NO = Integer.valueOf(0);
/* 111 */   public static final Integer STORE_STATUS_1 = Integer.valueOf(1);
/* 113 */   public static final Integer STORE_STATUS_2 = Integer.valueOf(2);
/* 115 */   public static final Integer STORE_STATUS_3 = Integer.valueOf(3);
/* 117 */   public static final Integer STORE_STATUS_4 = Integer.valueOf(4);
/* 119 */   public static final Integer STORE_STATUS_5 = Integer.valueOf(5);
/* 121 */   public static final Integer STORE_STATUS_6 = Integer.valueOf(6);
/* 123 */   public static final Integer STORE_STATUS_7 = Integer.valueOf(7);
/* 126 */   public static final Integer REGION_STATUS_0 = Integer.valueOf(0);
/* 128 */   public static final Integer REGION_STATUS_1 = Integer.valueOf(1);
/* 131 */   public static final Integer IS_SHUNGUANG_0 = Integer.valueOf(0);
/* 133 */   public static final Integer IS_SHUNGUANG_1 = Integer.valueOf(1);
/*     */   
/*     */   public Integer getStoreId()
/*     */   {
/* 139 */     return this.storeId;
/*     */   }
/*     */   
/*     */   public void setStoreId(Integer storeId)
/*     */   {
/* 146 */     this.storeId = storeId;
/*     */   }
/*     */   
/*     */   public String getStoreCode()
/*     */   {
/* 153 */     return this.storeCode;
/*     */   }
/*     */   
/*     */   public void setStoreCode(String storeCode)
/*     */   {
/* 160 */     this.storeCode = storeCode;
/*     */   }
/*     */   
/*     */   public String getStoreName()
/*     */   {
/* 167 */     return this.storeName;
/*     */   }
/*     */   
/*     */   public void setStoreName(String storeName)
/*     */   {
/* 174 */     this.storeName = storeName;
/*     */   }
/*     */   
/*     */   public Integer getStoreType()
/*     */   {
/* 181 */     return this.storeType;
/*     */   }
/*     */   
/*     */   public void setStoreType(Integer storeType)
/*     */   {
/* 188 */     this.storeType = storeType;
/*     */   }
/*     */   
/*     */   public String getSiteCode()
/*     */   {
/* 195 */     return this.siteCode;
/*     */   }
/*     */   
/*     */   public void setSiteCode(String siteCode)
/*     */   {
/* 202 */     this.siteCode = siteCode;
/*     */   }
/*     */   
/*     */   public String getStoreImageFileId()
/*     */   {
/* 209 */     return this.storeImageFileId;
/*     */   }
/*     */   
/*     */   public void setStoreImageFileId(String storeImageFileId)
/*     */   {
/* 216 */     this.storeImageFileId = storeImageFileId;
/*     */   }
/*     */   
/*     */   public Integer getProvinceId()
/*     */   {
/* 223 */     return this.provinceId;
/*     */   }
/*     */   
/*     */   public void setProvinceId(Integer provinceId)
/*     */   {
/* 230 */     this.provinceId = provinceId;
/*     */   }
/*     */   
/*     */   public Integer getCityId()
/*     */   {
/* 237 */     return this.cityId;
/*     */   }
/*     */   
/*     */   public void setCityId(Integer cityId)
/*     */   {
/* 244 */     this.cityId = cityId;
/*     */   }
/*     */   
/*     */   public Integer getRegionId()
/*     */   {
/* 251 */     return this.regionId;
/*     */   }
/*     */   
/*     */   public void setRegionId(Integer regionId)
/*     */   {
/* 258 */     this.regionId = regionId;
/*     */   }
/*     */   
/*     */   public String getAddress()
/*     */   {
/* 265 */     return this.address;
/*     */   }
/*     */   
/*     */   public void setAddress(String address)
/*     */   {
/* 272 */     this.address = address;
/*     */   }
/*     */   
/*     */   public BigDecimal getLongitude()
/*     */   {
/* 279 */     return this.longitude;
/*     */   }
/*     */   
/*     */   public void setLongitude(BigDecimal longitude)
/*     */   {
/* 286 */     this.longitude = longitude;
/*     */   }
/*     */   
/*     */   public BigDecimal getLatitude()
/*     */   {
/* 293 */     return this.latitude;
/*     */   }
/*     */   
/*     */   public void setLatitude(BigDecimal latitude)
/*     */   {
/* 300 */     this.latitude = latitude;
/*     */   }
/*     */   
/*     */   public String getIndustryTrade()
/*     */   {
/* 307 */     return this.industryTrade;
/*     */   }
/*     */   
/*     */   public void setIndustryTrade(String industryTrade)
/*     */   {
/* 314 */     this.industryTrade = industryTrade;
/*     */   }
/*     */   
/*     */   public String getQrCodeUrl()
/*     */   {
/* 321 */     return this.qrCodeUrl;
/*     */   }
/*     */   
/*     */   public void setQrCodeUrl(String qrCodeUrl)
/*     */   {
/* 328 */     this.qrCodeUrl = qrCodeUrl;
/*     */   }
/*     */   
/*     */   public Integer getIsCod()
/*     */   {
/* 335 */     return this.isCod;
/*     */   }
/*     */   
/*     */   public void setIsCod(Integer isCod)
/*     */   {
/* 342 */     this.isCod = isCod;
/*     */   }
/*     */   
/*     */   public Integer getShippingTimeLimit()
/*     */   {
/* 349 */     return this.shippingTimeLimit;
/*     */   }
/*     */   
/*     */   public void setShippingTimeLimit(Integer shippingTimeLimit)
/*     */   {
/* 356 */     this.shippingTimeLimit = shippingTimeLimit;
/*     */   }
/*     */   
/*     */   public String getContacts()
/*     */   {
/* 363 */     return this.contacts;
/*     */   }
/*     */   
/*     */   public void setContacts(String contacts)
/*     */   {
/* 370 */     this.contacts = contacts;
/*     */   }
/*     */   
/*     */   public String getContactsMobile()
/*     */   {
/* 377 */     return this.contactsMobile;
/*     */   }
/*     */   
/*     */   public void setContactsMobile(String contactsMobile)
/*     */   {
/* 384 */     this.contactsMobile = contactsMobile;
/*     */   }
/*     */   
/*     */   public Integer getIsSyncEc()
/*     */   {
/* 391 */     return this.isSyncEc;
/*     */   }
/*     */   
/*     */   public void setIsSyncEc(Integer isSyncEc)
/*     */   {
/* 398 */     this.isSyncEc = isSyncEc;
/*     */   }
/*     */   
/*     */   public Integer getStatus()
/*     */   {
/* 405 */     return this.status;
/*     */   }
/*     */   
/*     */   public void setStatus(Integer status)
/*     */   {
/* 412 */     this.status = status;
/*     */   }
/*     */   
/*     */   public Integer getCreateMuserId()
/*     */   {
/* 419 */     return this.createMuserId;
/*     */   }
/*     */   
/*     */   public void setCreateMuserId(Integer createMuserId)
/*     */   {
/* 426 */     this.createMuserId = createMuserId;
/*     */   }
/*     */   
/*     */   public Date getCreateTime()
/*     */   {
/* 433 */     return this.createTime;
/*     */   }
/*     */   
/*     */   public void setCreateTime(Date createTime)
/*     */   {
/* 440 */     this.createTime = createTime;
/*     */   }
/*     */   
/*     */   public Integer getLastUpdateMuserId()
/*     */   {
/* 447 */     return this.lastUpdateMuserId;
/*     */   }
/*     */   
/*     */   public void setLastUpdateMuserId(Integer lastUpdateMuserId)
/*     */   {
/* 454 */     this.lastUpdateMuserId = lastUpdateMuserId;
/*     */   }
/*     */   
/*     */   public Integer getLastUpdateUserId()
/*     */   {
/* 461 */     return this.lastUpdateUserId;
/*     */   }
/*     */   
/*     */   public void setLastUpdateUserId(Integer lastUpdateUserId)
/*     */   {
/* 468 */     this.lastUpdateUserId = lastUpdateUserId;
/*     */   }
/*     */   
/*     */   public Date getLastUpdateTime()
/*     */   {
/* 475 */     return this.lastUpdateTime;
/*     */   }
/*     */   
/*     */   public void setLastUpdateTime(Date lastUpdateTime)
/*     */   {
/* 482 */     this.lastUpdateTime = lastUpdateTime;
/*     */   }
/*     */   
/*     */   public String getCreateMuserName()
/*     */   {
/* 489 */     return this.createMuserName;
/*     */   }
/*     */   
/*     */   public void setCreateMuserName(String createMuserName)
/*     */   {
/* 496 */     this.createMuserName = createMuserName;
/*     */   }
/*     */   
/*     */   public String getLastUpdateMuserName()
/*     */   {
/* 503 */     return this.lastUpdateMuserName;
/*     */   }
/*     */   
/*     */   public void setLastUpdateMuserName(String lastUpdateMuserName)
/*     */   {
/* 510 */     this.lastUpdateMuserName = lastUpdateMuserName;
/*     */   }
/*     */   
/*     */   public String getLastUpdateUserName()
/*     */   {
/* 517 */     return this.lastUpdateUserName;
/*     */   }
/*     */   
/*     */   public void setLastUpdateUserName(String lastUpdateUserName)
/*     */   {
/* 524 */     this.lastUpdateUserName = lastUpdateUserName;
/*     */   }
/*     */   
/*     */   public String getPreviewUrl()
/*     */   {
/* 531 */     return this.previewUrl;
/*     */   }
/*     */   
/*     */   public void setPreviewUrl(String previewUrl)
/*     */   {
/* 538 */     this.previewUrl = previewUrl;
/*     */   }
/*     */   
/*     */   public String getBankAccount()
/*     */   {
/* 542 */     return this.bankAccount;
/*     */   }
/*     */   
/*     */   public void setBankAccount(String bankAccount)
/*     */   {
/* 546 */     this.bankAccount = bankAccount;
/*     */   }
/*     */   
/*     */   public String getBankName()
/*     */   {
/* 550 */     return this.bankName;
/*     */   }
/*     */   
/*     */   public void setBankName(String bankName)
/*     */   {
/* 554 */     this.bankName = bankName;
/*     */   }
/*     */   
/*     */   public String getStoreTypeName()
/*     */   {
/* 558 */     if (this.storeType == STOR_TYPE_1) {
/* 559 */       return "旗舰店";
/*     */     }
/* 560 */     if (this.storeType == STOR_TYPE_2) {
/* 561 */       return "销售服务店";
/*     */     }
/* 562 */     if (this.storeType == STOR_TYPE_3) {
/* 563 */       return "伞下店";
/*     */     }
/* 564 */     if (this.storeType == STOR_TYPE_4) {
/* 565 */       return "纯服务店";
/*     */     }
/* 567 */     return this.storeTypeName;
/*     */   }
/*     */   
/*     */   public void setStoreTypeName(String storeTypeName)
/*     */   {
/* 571 */     this.storeTypeName = storeTypeName;
/*     */   }
/*     */   
/*     */   public String getIsSyncEcName()
/*     */   {
/* 575 */     if (this.isSyncEc == IS_SYNC_EC_YES) {
/* 576 */       return "是";
/*     */     }
/* 577 */     if (this.isSyncEc == IS_SYNC_EC_NO) {
/* 578 */       return "否";
/*     */     }
/* 580 */     return this.isSyncEcName;
/*     */   }
/*     */   
/*     */   public void setIsSyncEcName(String isSyncEcName)
/*     */   {
/* 584 */     this.isSyncEcName = isSyncEcName;
/*     */   }
/*     */   
/*     */   public String getStatusName()
/*     */   {
/* 588 */     if (this.status == STORE_STATUS_1) {
/* 589 */       return "新建";
/*     */     }
/* 590 */     if (this.status == STORE_STATUS_2) {
/* 591 */       return "网签通过";
/*     */     }
/* 592 */     if (this.status == STORE_STATUS_3) {
/* 593 */       return "营业中";
/*     */     }
/* 594 */     if (this.status == STORE_STATUS_4) {
/* 595 */       return "暂停营业";
/*     */     }
/* 596 */     if (this.status == STORE_STATUS_5) {
/* 597 */       return "预取消";
/*     */     }
/* 598 */     if (this.status == STORE_STATUS_6) {
/* 599 */       return "取消合作";
/*     */     }
/* 600 */     if (this.status == STORE_STATUS_7) {
/* 601 */       return "临时状态";
/*     */     }
/* 603 */     return this.statusName;
/*     */   }
/*     */   
/*     */   public void setStatusName(String statusName)
/*     */   {
/* 607 */     this.statusName = statusName;
/*     */   }
/*     */   
/*     */   public Integer getIsShunguang()
/*     */   {
/* 614 */     return this.isShunguang;
/*     */   }
/*     */   
/*     */   public void setIsShunguang(Integer isShunguang)
/*     */   {
/* 621 */     this.isShunguang = isShunguang;
/*     */   }
/*     */ }


/* Location:           D:\panning\develop\M2_Repository\com\ehaier\shoppingmall\shoppingmall-store-service\1.0.11\shoppingmall-store-service-1.0.11.jar
 * Qualified Name:     com.ehaier.shoppingmall.store.entity.BasStores
 * JD-Core Version:    0.7.0.1
 */