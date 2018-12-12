package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * COLLECTOR表结构    LES库存明细---财务SAP使用
 * @author wangp-c
 *
 */
public class COLLECTORType implements Serializable{

	private static final long serialVersionUID = -1528001680405297768L;
	
	/**
	 * 物料号码
	 */
    private String matnr;
    
    /**
     * 工厂
     */
    private String werks;
    
    /**
     * 库存地点
     */
    private String lgort;
    
    /**
     * 批次编号
     */
    private String charg;
    
    /**
     * 物料组
     */
    private String matkl;
    
    /**
     * 非限制使用的估价的库存
     */
    private BigDecimal clabs;
    
    /**
     * 基本计量单位
     */
    private String meins;

	public String getMATNR() {
		return matnr;
	}

	public void setMATNR(String matnr) {
		this.matnr = matnr;
	}

	public String getWERKS() {
		return werks;
	}

	public void setWERKS(String werks) {
		this.werks = werks;
	}

	public String getLGORT() {
		return lgort;
	}

	public void setLGORT(String lgort) {
		this.lgort = lgort;
	}

	public String getCHARG() {
		return charg;
	}

	public void setCHARG(String charg) {
		this.charg = charg;
	}

	public String getMATKL() {
		return matkl;
	}

	public void setMATKL(String matkl) {
		this.matkl = matkl;
	}

	public BigDecimal getCLABS() {
		return clabs;
	}

	public void setCLABS(BigDecimal clabs) {
		this.clabs = clabs;
	}

	public String getMEINS() {
		return meins;
	}

	public void setMEINS(String meins) {
		this.meins = meins;
	}
	
}
