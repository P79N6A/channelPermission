package com.haier.stock.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * LES-CBS库存对比差异实体
 * @author wangp-c
 *
 */
public class InvBaseStockDiff implements Serializable{


	private static final long serialVersionUID = -8523669696977889560L;
	
	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 物料编码
	 */
	private String sku;

	/**
	 * les库存编码
	 */
	private String lesSecCode;
	
	/**
	 * 总库存量
	 */
	private Integer stockQty;
	
	/**
	 * 冻结数量
	 */
	private Integer frozenQty;
	
	/**
	 * 产品型号
	 */
	private String productName;
	
	/**
	 * 品类
	 */
	private String productTypeName;
	
	/**
	 * 产品组
	 */
	private String productGroupName;
	
	/**
	 * 库位名称
	 */
	private String secName;
	
	/**
	 * 批次
	 */
	private String itemProperty;
	
	/**
	 * LES-CBS库存差异对比：0：无差异 1：有差异
	 */
	private Integer diffType;
	
	/**
	 * 实物库存
	 */
	private BigDecimal lesClabs;
	
	/**
	 * 在运库存 (从一库存地到另一库存地)
	 */
	private BigDecimal lesCumlm;
	
	/**
	 * 数量
	 */
	private BigDecimal lesZmenge1;
	
	/**
	 * 开票未提
	 */
	private BigDecimal lesMenge1;
	
	/**
	 * 可用库存数
	 */
	private BigDecimal lesMenge2;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;

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

	public String getLesSecCode() {
		return lesSecCode;
	}

	public void setLesSecCode(String lesSecCode) {
		this.lesSecCode = lesSecCode;
	}

	public Integer getStockQty() {
		return stockQty;
	}

	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}

	public Integer getFrozenQty() {
		return frozenQty;
	}

	public void setFrozenQty(Integer frozenQty) {
		this.frozenQty = frozenQty;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getProductGroupName() {
		return productGroupName;
	}

	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}

	public String getSecName() {
		return secName;
	}

	public void setSecName(String secName) {
		this.secName = secName;
	}

	public String getItemProperty() {
		return itemProperty;
	}

	public void setItemProperty(String itemProperty) {
		this.itemProperty = itemProperty;
	}

	public Integer getDiffType() {
		return diffType;
	}

	public void setDiffType(Integer diffType) {
		this.diffType = diffType;
	}

	public BigDecimal getLesClabs() {
		return lesClabs;
	}

	public void setLesClabs(BigDecimal lesClabs) {
		this.lesClabs = lesClabs;
	}

	public BigDecimal getLesCumlm() {
		return lesCumlm;
	}

	public void setLesCumlm(BigDecimal lesCumlm) {
		this.lesCumlm = lesCumlm;
	}

	public BigDecimal getLesZmenge1() {
		return lesZmenge1;
	}

	public void setLesZmenge1(BigDecimal lesZmenge1) {
		this.lesZmenge1 = lesZmenge1;
	}

	public BigDecimal getLesMenge1() {
		return lesMenge1;
	}

	public void setLesMenge1(BigDecimal lesMenge1) {
		this.lesMenge1 = lesMenge1;
	}

	public BigDecimal getLesMenge2() {
		return lesMenge2;
	}

	public void setLesMenge2(BigDecimal lesMenge2) {
		this.lesMenge2 = lesMenge2;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
