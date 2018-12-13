package com.haier.shop.dto;

import java.io.Serializable;

public class ReviewRegionCompanyDto implements Serializable {
  private static final long serialVersionUID = -2107593759019401837L;
  private String            provinceId;                              //省ID
  private String            provinceName;                            //省名称
  private String            cityId;                                  //市ID
  private String            cityName;                                //市名称
  private String            companyId;                               //工贸ID
  private String            companyName;                             //工贸名称
  private String            regionId;                                //区县ID
  private String            regionName;                              //区县名称

  public String getProvinceId() {
    return provinceId;
  }

  public void setProvinceId(String provinceId) {
    this.provinceId = provinceId;
  }

  public String getProvinceName() {
    return provinceName;
  }

  public void setProvinceName(String provinceName) {
    this.provinceName = provinceName;
  }

  public String getCityId() {
    return cityId;
  }

  public void setCityId(String cityId) {
    this.cityId = cityId;
  }

  public String getCityName() {
    return cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getRegionId() {
    return regionId;
  }

  public void setRegionId(String regionId) {
    this.regionId = regionId;
  }

  public String getRegionName() {
    return regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

  @Override
  public String toString() {
    return "RegionCompanyDto [provinceId=" + provinceId + ", provinceName=" + provinceName
        + ", cityId=" + cityId + ", cityName=" + cityName + ", companyId=" + companyId
        + ", companyName=" + companyName + ", regionId=" + regionId + ", regionName="
        + regionName + "]";
  }

}
