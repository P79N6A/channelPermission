/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.svc.bean;

import java.io.Serializable;

/**
 * 出日日顺时间、提单时间查询接口(LES)
 * @author xuelt
 *
 */
public class LESOutRRSLedingBillTimeSubResponse implements Serializable {

	private static final long serialVersionUID = 1L;
    
	private String BSTKD;  //	85提单
	private String GVS_SO;  //	GVS SO
	private String KUNNR;  //	中心代码
	private String KUNWE;  //	送达方代码
	private String ADD4;  //	D，P单号
	private String ERDAT;  //	订单日期
	private String ERZET;  //	订单时间
	private String AD1;  //	出库日期
	private String AD2;  //	出库时间
	private String AD3;  //	预留
	public String getBSTKD() {
		return BSTKD;
	}
	public void setBSTKD(String bSTKD) {
		BSTKD = bSTKD;
	}
	public String getGVS_SO() {
		return GVS_SO;
	}
	public void setGVS_SO(String gVS_SO) {
		GVS_SO = gVS_SO;
	}
	public String getKUNNR() {
		return KUNNR;
	}
	public void setKUNNR(String kUNNR) {
		KUNNR = kUNNR;
	}
	public String getKUNWE() {
		return KUNWE;
	}
	public void setKUNWE(String kUNWE) {
		KUNWE = kUNWE;
	}
	public String getADD4() {
		return ADD4;
	}
	public void setADD4(String aDD4) {
		ADD4 = aDD4;
	}
	public String getERDAT() {
		return ERDAT;
	}
	public void setERDAT(String eRDAT) {
		ERDAT = eRDAT;
	}
	public String getERZET() {
		return ERZET;
	}
	public void setERZET(String eRZET) {
		ERZET = eRZET;
	}
	public String getAD1() {
		return AD1;
	}
	public void setAD1(String aD1) {
		AD1 = aD1;
	}
	public String getAD2() {
		return AD2;
	}
	public void setAD2(String aD2) {
		AD2 = aD2;
	}
	public String getAD3() {
		return AD3;
	}
	public void setAD3(String aD3) {
		AD3 = aD3;
	}


}
