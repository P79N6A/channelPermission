package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * CBS_KC表结构  LES库存数---CBS使用
 * @author wangp-c
 *
 */
public class CBSKCType implements Serializable{

	private static final long serialVersionUID = 4044614277530064966L;

	/**
	 * 库存地点
	 */
	private String lgort1;
    
    /**
     * 库存地点
     */
    private String lgort;
    
    /**
     * 物料号码
     */
    private String matnr;
    
    /**
     * 物料号码
     */
    private String matnrnew;
    
    /**
     * 批次编号
     */
    private String atwart;
    
    /**
     * 实物库存
     */
    private BigDecimal clabs;
    
    /**
     * 在运库存
     */
    private BigDecimal cumlm;
    
    /**
     * 基本计量单位
     */
    private String meins;
    
    /**
     * 数量2
     */
    private BigDecimal zmenge1;
    
    /**
     * 单位2
     */
    private String vrkme;
    
    /**
     * 开票未提
     */
    private BigDecimal menge1;
    
    /**
     * 可用库存数
     */
    private BigDecimal menge2;
    
    /**
     * 物料组
     */
    private String matkl;
    
    /**
     * 实物库存体积
     */
    private BigDecimal volum;
    
    /**
     * 单位
     */
    private String zvoleh;

	public String getLGORT1() {
		return lgort1;
	}

	public void setLGORT1(String lgort1) {
		this.lgort1 = lgort1;
	}

	public String getLGORT() {
		return lgort;
	}

	public void setLGORT(String lgort) {
		this.lgort = lgort;
	}

	public String getMATNR() {
		return matnr;
	}

	public void setMATNR(String matnr) {
		this.matnr = matnr;
	}

	public String getMATNRNEW() {
		return matnrnew;
	}

	public void setMATNRNEW(String matnrnew) {
		this.matnrnew = matnrnew;
	}

	public String getATWART() {
		return atwart;
	}

	public void setATWART(String atwart) {
		this.atwart = atwart;
	}

	public BigDecimal getCLABS() {
		return clabs;
	}

	public void setCLABS(BigDecimal clabs) {
		this.clabs = clabs;
	}

	public BigDecimal getCUMLM() {
		return cumlm;
	}

	public void setCUMLM(BigDecimal cumlm) {
		this.cumlm = cumlm;
	}

	public String getMEINS() {
		return meins;
	}

	public void setMEINS(String meins) {
		this.meins = meins;
	}

	public BigDecimal getZMENGE1() {
		return zmenge1;
	}

	public void setZMENGE1(BigDecimal zmenge1) {
		this.zmenge1 = zmenge1;
	}

	public String getVRKME() {
		return vrkme;
	}

	public void setVRKME(String vrkme) {
		this.vrkme = vrkme;
	}

	public BigDecimal getMENGE1() {
		return menge1;
	}

	public void setMENGE1(BigDecimal menge1) {
		this.menge1 = menge1;
	}

	public BigDecimal getMENGE2() {
		return menge2;
	}

	public void setMENGE2(BigDecimal menge2) {
		this.menge2 = menge2;
	}

	public String getMATKL() {
		return matkl;
	}

	public void setMATKL(String matkl) {
		this.matkl = matkl;
	}

	public BigDecimal getVOLUM() {
		return volum;
	}

	public void setVOLUM(BigDecimal volum) {
		this.volum = volum;
	}

	public String getZVOLEH() {
		return zvoleh;
	}

	public void setZVOLEH(String zvoleh) {
		this.zvoleh = zvoleh;
	}
    
}
