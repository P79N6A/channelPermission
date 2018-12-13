package com.haier.shop.model;

/**
 * 商品中心数据类
 * @author panning
 * @Date 2018-03-29
 * @version 1.0.0
 */

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductCenterDTO  implements Serializable{
	private Integer id;
	private String code;

	public ProductCenterDTO() {

	}

	private String name;//商品名字
	private String model;//商品型号
	private String series;
	private Integer brandId;
	private String brandName;
	private String productGroupCode;
	private String productGroupName;
	private String internationalCode;
	private Long dateToMarket;
	private Long dateOffMarket;
	private Integer status;

	public Integer getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getModel() {
		return model;
	}

	public String getSeries() {
		return series;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public String getProductGroupCode() {
		return productGroupCode;
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public String getInternationalCode() {
		return internationalCode;
	}

	public Long getDateToMarket() {
		return dateToMarket;
	}

	public Long getDateOffMarket() {
		return dateOffMarket;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	public void setInternationalCode(String internationalCode) {
		this.internationalCode = internationalCode;
	}

	public void setDateToMarket(Long dateToMarket) {
		this.dateToMarket = dateToMarket;
	}

	public void setDateOffMarket(Long dateOffMarket) {
		this.dateOffMarket = dateOffMarket;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSalePoint(String salePoint) {
		this.salePoint = salePoint;
	}

	public Integer getStatus() {
		return status;
	}
	public String getRemark() {
		return remark;
	}
	public String getSalePoint() {
		return salePoint;
	}
	private String remark;
	private String salePoint;

}