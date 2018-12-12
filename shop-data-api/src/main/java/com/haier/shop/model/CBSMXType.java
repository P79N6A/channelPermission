package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * CBS_MX表结构  LES出入库流水---CBS使用
 * @author wangp-c
 *
 */
public class CBSMXType implements Serializable{
	
	private static final long serialVersionUID = 5839085372951958798L;

	/**
	 * 凭证抬头文本
	 */
    private String bktxt;
    
    /**
     * 凭证中的凭证日期
     */
    private String bldat;
    
    /**
     * 凭证记帐日期
     */
    private String budat;
    
    /**
     * 凭证记帐日期
     */
    private String bwart;
    
    /**
     * 批次编号
     */
    private String charg;
    
    /**
     * 输入时间
     */
    private String cputm;
    
    /**
     * 输入单位
     */
    private String erfme;
    
    /**
     * 以条目单位的数量
     */
    private BigDecimal erfmg;
    
    /**
     * 库存地点
     */
    private String lgort;
    
    /**
     * 供应商或债权人帐户号
     */
    private String lifnr;
    
    /**
     * 物料号码
     */
    private String matnr;
    
    /**
     * 物料凭证编号
     */
    private String mblnr;
    
    /**
     * 基本计量单位
     */
    private String meins;
    
    /**
     * 数量
     */
    private BigDecimal menge;
    
    /**
     * 物料凭证的年份
     */
    private String mjahr;
    
    /**
     * 借/贷标志
     */
    private String shkzg;
    
    /**
     * 用户名
     */
    private String usnam;
    
    /**
     * 工厂
     */
    private String werks;
    
    /**
     * 参考凭证号
     */
    private String xblnr;
    
    /**
     * 仓位
     */
    private String lgpla;
    
    /**
     * 仓储地点的描述
     */
    private String lgobe;
    
    /**
     * 单位体积
     */
    private BigDecimal volum;
    
    /**
     * 体积
     */
    private BigDecimal zvolum;
    
    /**
     * 起始凭证
     */
    private String vbelv;
    
    /**
     * 起始凭证
     */
    private String posnv;
    
    /**
     * 销售与分销单据号
     */
    private String vbeln;
    
    /**
     * 配车单号
     */
    private String peiche;
    
    /**
     * 运输计划点
     */
    private String tplst;
    
    /**
     * 起始凭证
     */
    private String vbelvo;
    
    /**
     * 起始项目
     */
    private String posnvo;
    
    
    /**
     * 客户采购订单编号
     */
    private String bstkd;
    
    /**
     * 名称
     */
    private String name1SDF;
    
    /**
     * 物料组
     */
    private String matkl;
    
    /**
     * 外部物料组
     */
    private String extwg;

	public String getBKTXT() {
		return bktxt;
	}

	public void setBKTXT(String bktxt) {
		this.bktxt = bktxt;
	}

	public String getBLDAT() {
		return bldat;
	}

	public void setBLDAT(String bldat) {
		this.bldat = bldat;
	}

	public String getBUDAT() {
		return budat;
	}

	public void setBUDAT(String budat) {
		this.budat = budat;
	}

	public String getBWART() {
		return bwart;
	}

	public void setBWART(String bwart) {
		this.bwart = bwart;
	}

	public String getCHARG() {
		return charg;
	}

	public void setCHARG(String charg) {
		this.charg = charg;
	}

	public String getCPUTM() {
		return cputm;
	}

	public void setCPUTM(String cputm) {
		this.cputm = cputm;
	}

	public String getERFME() {
		return erfme;
	}

	public void setERFME(String erfme) {
		this.erfme = erfme;
	}

	public BigDecimal getERFMG() {
		return erfmg;
	}

	public void setERFMG(BigDecimal erfmg) {
		this.erfmg = erfmg;
	}

	public String getLGORT() {
		return lgort;
	}

	public void setLGORT(String lgort) {
		this.lgort = lgort;
	}

	public String getLIFNR() {
		return lifnr;
	}

	public void setLIFNR(String lifnr) {
		this.lifnr = lifnr;
	}

	public String getMATNR() {
		return matnr;
	}

	public void setMATNR(String matnr) {
		this.matnr = matnr;
	}

	public String getMBLNR() {
		return mblnr;
	}

	public void setMBLNR(String mblnr) {
		this.mblnr = mblnr;
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

	public String getMJAHR() {
		return mjahr;
	}

	public void setMJAHR(String mjahr) {
		this.mjahr = mjahr;
	}

	public String getSHKZG() {
		return shkzg;
	}

	public void setSHKZG(String shkzg) {
		this.shkzg = shkzg;
	}

	public String getUSNAM() {
		return usnam;
	}

	public void setUSNAM(String usnam) {
		this.usnam = usnam;
	}

	public String getWERKS() {
		return werks;
	}

	public void setWERKS(String werks) {
		this.werks = werks;
	}

	public String getXBLNR() {
		return xblnr;
	}

	public void setXBLNR(String xblnr) {
		this.xblnr = xblnr;
	}

	public String getLGPLA() {
		return lgpla;
	}

	public void setLGPLA(String lgpla) {
		this.lgpla = lgpla;
	}

	public String getLGOBE() {
		return lgobe;
	}

	public void setLGOBE(String lgobe) {
		this.lgobe = lgobe;
	}

	public BigDecimal getVOLUM() {
		return volum;
	}

	public void setVOLUM(BigDecimal volum) {
		this.volum = volum;
	}

	public BigDecimal getZVOLUM() {
		return zvolum;
	}

	public void setZVOLUM(BigDecimal zvolum) {
		this.zvolum = zvolum;
	}

	public String getVBELV() {
		return vbelv;
	}

	public void setVBELV(String vbelv) {
		this.vbelv = vbelv;
	}

	public String getPOSNV() {
		return posnv;
	}

	public void setPOSNV(String posnv) {
		this.posnv = posnv;
	}

	public String getVBELN() {
		return vbeln;
	}

	public void setVBELN(String vbeln) {
		this.vbeln = vbeln;
	}

	public String getPEICHE() {
		return peiche;
	}

	public void setPEICHE(String peiche) {
		this.peiche = peiche;
	}

	public String getTPLST() {
		return tplst;
	}

	public void setTPLST(String tplst) {
		this.tplst = tplst;
	}

	public String getVBELVO() {
		return vbelvo;
	}

	public void setVBELVO(String vbelvo) {
		this.vbelvo = vbelvo;
	}

	public String getPOSNVO() {
		return posnvo;
	}

	public void setPOSNVO(String posnvo) {
		this.posnvo = posnvo;
	}

	public String getBSTKD() {
		return bstkd;
	}

	public void setBSTKD(String bstkd) {
		this.bstkd = bstkd;
	}

	public String getNAME1SDF() {
		return name1SDF;
	}

	public void setNAME1SDF(String name1sdf) {
		name1SDF = name1sdf;
	}

	public String getMATKL() {
		return matkl;
	}

	public void setMATKL(String matkl) {
		this.matkl = matkl;
	}

	public String getEXTWG() {
		return extwg;
	}

	public void setEXTWG(String extwg) {
		this.extwg = extwg;
	}
    
}
