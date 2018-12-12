package com.haier.purchase.data.model.vehcile;


public class VehicleOrderHistoryDTO extends VehicleOrderDetailsDTO {

	private static final long serialVersionUID = -6949617230020591671L;
	
	private String zqItemNo;	//扣款单号
	private String zqStatus;	//扣款状态
	private String remark;		//闸口信息
	private String sapStatus;	//推送sap状态
	private String lbxStatus;	//lxb状态
	public String getZqItemNo() {
		return zqItemNo;
	}
	public void setZqItemNo(String zqItemNo) {
		this.zqItemNo = zqItemNo;
	}
	public String getZqStatus() {
		return zqStatus;
	}
	public void setZqStatus(String zqStatus) {
		this.zqStatus = zqStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSapStatus() {
		return sapStatus;
	}
	public void setSapStatus(String sapStatus) {
		this.sapStatus = sapStatus;
	}
	public String getLbxStatus() {
		return lbxStatus;
	}
	public void setLbxStatus(String lbxStatus) {
		this.lbxStatus = lbxStatus;
	}

}


