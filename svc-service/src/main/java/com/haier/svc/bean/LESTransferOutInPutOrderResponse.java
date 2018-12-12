/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.svc.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 中转出入库,入日日顺时间(LES)
 * @author xuelt
 *
 */
public class LESTransferOutInPutOrderResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private String FLAG;  //S成功 E失败
    private String MESSAGE;  //注释
    private String ZFLAG1;  //1为查询退货 2查询转运	
    private List<LESTransferOutInPutOrderSubResponse> SubRecords;  //一条记录中可能含有多条子记录,子记录内容
    private String faultDETAIL;
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
	public String getZFLAG1() {
		return ZFLAG1;
	}
	public void setZFLAG1(String zFLAG1) {
		ZFLAG1 = zFLAG1;
	}
	public List<LESTransferOutInPutOrderSubResponse> getSubRecords() {
		return SubRecords;
	}
	public void setSubRecords(List<LESTransferOutInPutOrderSubResponse> subRecords) {
		SubRecords = subRecords;
	}
	public String getFaultDETAIL() {
		return faultDETAIL;
	}
	public void setFaultDETAIL(String faultDETAIL) {
		this.faultDETAIL = faultDETAIL;
	}

}
