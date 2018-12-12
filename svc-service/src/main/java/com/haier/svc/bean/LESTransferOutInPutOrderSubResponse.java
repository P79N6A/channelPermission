/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.svc.bean;

import java.io.Serializable;

/**
 * 中转出入库,入日日顺时间(LES)
 * @author xuelt
 *
 */
public class LESTransferOutInPutOrderSubResponse implements Serializable {

	private static final long serialVersionUID = 1L;
    
    private String BSTKD;  //客户采购订单编号
    private String FLAG_RK;  //
    private String ERDAT_RK;  //记录建立日期
    private String ERZET_RK;  //输入时间
    private String FLAG_CK;  //
    private String ERDAT_CK;  //记录建立日期
    private String ERZET_CK;  //输入时间
    private String AD1;  //预留
    private String AD2;  //预留
    private String AD3;  //预留
    private String AD4;  //预留
	public String getBSTKD() {
		return BSTKD;
	}
	public void setBSTKD(String bSTKD) {
		BSTKD = bSTKD;
	}
	public String getFLAG_RK() {
		return FLAG_RK;
	}
	public void setFLAG_RK(String fLAG_RK) {
		FLAG_RK = fLAG_RK;
	}
	public String getERDAT_RK() {
		return ERDAT_RK;
	}
	public void setERDAT_RK(String eRDAT_RK) {
		ERDAT_RK = eRDAT_RK;
	}
	public String getERZET_RK() {
		return ERZET_RK;
	}
	public void setERZET_RK(String eRZET_RK) {
		ERZET_RK = eRZET_RK;
	}
	public String getFLAG_CK() {
		return FLAG_CK;
	}
	public void setFLAG_CK(String fLAG_CK) {
		FLAG_CK = fLAG_CK;
	}
	public String getERDAT_CK() {
		return ERDAT_CK;
	}
	public void setERDAT_CK(String eRDAT_CK) {
		ERDAT_CK = eRDAT_CK;
	}
	public String getERZET_CK() {
		return ERZET_CK;
	}
	public void setERZET_CK(String eRZET_CK) {
		ERZET_CK = eRZET_CK;
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
	public String getAD4() {
		return AD4;
	}
	public void setAD4(String aD4) {
		AD4 = aD4;
	}


}
