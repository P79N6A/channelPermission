package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * LTMX表结构  LES出入库流水---财务SAP使用
 * @author wangp-c
 *
 */
public class LTMXType implements Serializable{

	private static final long serialVersionUID = -8670814065684705829L;

	/**
	 * 物料凭证编号
	 */
    private String mblnr;
    
    /**
     * 物料凭证的年份
     */
    private String mjahr;
    
    /**
     * 会计凭证输入日期
     */
    private String cpudt;
    
    
    /**
     * 物料凭证中的项目
     */
    private String zeile;
    
    /**
     * 物料号码
     */
    private String matnr;
    
    /**
     * 基本计量单位
     */
    private String meins;
    
    /**
     * 数量
     */
    private BigDecimal menge;
    
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
     * 移动类型(库存管理)
     */
    private String bwart;
    
    /**
     * 交货
     */
    private String vbelndn;
    
    /**
     * 交货项目
     */
    private String posnrdn;
    
    /**
     * 销售凭证
     */
    private String vbelnso;
    
    /**
     * 销售单据项目
     */
    private String posnrso;
    
    /**
     * 销售单据类型
     */
    private String auart;
    
    /**
     * 客户采购订单编号
     */
    private String bstnk;
    
    /**
     * 客户采购订单编号
     */
    private String bstnk100;
    
    /**
     * 创建对象的人员名称
     */
    private String ernam;
    
    /**
     * 记录建立日期
     */
    private String erdat;
    
    /**
     * 借/贷标志
     */
    private String shkzg;

	public String getMBLNR() {
		return mblnr;
	}

	public void setMBLNR(String mblnr) {
		this.mblnr = mblnr;
	}

	public String getMJAHR() {
		return mjahr;
	}

	public void setMJAHR(String mjahr) {
		this.mjahr = mjahr;
	}

	public String getCPUDT() {
		return cpudt;
	}

	public void setCPUDT(String cpudt) {
		this.cpudt = cpudt;
	}

	public String getZEILE() {
		return zeile;
	}

	public void setZEILE(String zeile) {
		this.zeile = zeile;
	}

	public String getMATNR() {
		return matnr;
	}

	public void setMATNR(String matnr) {
		this.matnr = matnr;
	}

	public String getMEINS() {
		return meins;
	}

	public void setMEINS(String meins) {
		this.meins = meins;
	}

	public BigDecimal getMENGE() {
		return menge;
	}

	public void setMENGE(BigDecimal menge) {
		this.menge = menge;
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

	public String getBWART() {
		return bwart;
	}

	public void setBWART(String bwart) {
		this.bwart = bwart;
	}

	public String getVBELNDN() {
		return vbelndn;
	}

	public void setVBELNDN(String vbelndn) {
		this.vbelndn = vbelndn;
	}

	public String getPOSNRDN() {
		return posnrdn;
	}

	public void setPOSNRDN(String posnrdn) {
		this.posnrdn = posnrdn;
	}

	public String getVBELNSO() {
		return vbelnso;
	}

	public void setVBELNSO(String vbelnso) {
		this.vbelnso = vbelnso;
	}

	public String getPOSNRSO() {
		return posnrso;
	}

	public void setPOSNRSO(String posnrso) {
		this.posnrso = posnrso;
	}

	public String getAUART() {
		return auart;
	}

	public void setAUART(String auart) {
		this.auart = auart;
	}

	public String getBSTNK() {
		return bstnk;
	}

	public void setBSTNK(String bstnk) {
		this.bstnk = bstnk;
	}

	public String getBSTNK100() {
		return bstnk100;
	}

	public void setBSTNK100(String bstnk100) {
		this.bstnk100 = bstnk100;
	}

	public String getERNAM() {
		return ernam;
	}

	public void setERNAM(String ernam) {
		this.ernam = ernam;
	}

	public String getERDAT() {
		return erdat;
	}

	public void setERDAT(String erdat) {
		this.erdat = erdat;
	}

	public String getSHKZG() {
		return shkzg;
	}

	public void setSHKZG(String shkzg) {
		this.shkzg = shkzg;
	}
    

}
