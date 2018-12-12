/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.svc.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 出日日顺时间、提单时间查询接口(LES)
 * @author xuelt
 *
 */
public class LESOutRRSLedingBillTimeResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private String FLAG;  //S成功 E失败
    private String MESSAGE;  //注释
    private List<LESOutRRSLedingBillTimeSubResponse> SubRecords;  //一条记录中可能含有多条子记录,子记录内容
    private String faultDetail;
	public String getFLAG() {
		return FLAG;
	}
	public void setFLAG(String fLAG) {
		FLAG = fLAG;
	}
	public String getMESSAGE() {
		return MESSAGE;
	}
	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}
	public List<LESOutRRSLedingBillTimeSubResponse> getSubRecords() {
		return SubRecords;
	}
	public void setSubRecords(List<LESOutRRSLedingBillTimeSubResponse> subRecords) {
		SubRecords = subRecords;
	}
	public String getFaultDetail() {
		return faultDetail;
	}
	public void setFaultDetail(String faultDetail) {
		this.faultDetail = faultDetail;
	}

}
