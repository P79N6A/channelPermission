package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;


public class PutAway implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -2352581054105228287L;
	private String centerName;
	private String warehouseLocation;
	private String netListNum;
	private String category;
	private String materielNum;
	private String machineNum;
	private String negativeLadingListNum;
	private Date negativeProduceDate;
	private String pushWarehouseNum;
	private Date pushWarehouseProduceDate;
	private Date pushCompleteDate;
	private Date addtimeTs;
	private Date storageDate;
	private String remark;
	private String secCode;
    private long period;
    private Date returnResultTime;
    private String OnePushTime;
    private Date secondPushTime;
    private String pushTime;
    private byte storageType;
    private int istz ;
    private String industry;
    private String outNum; //22出库单号
    private String GenuineWarehouseNum;//正品入库单号
    private String UndesirableWarehouseNum;//不良品入库单号
    private int rootId;//用来判断产业的
    private String oneInspectorNum;
    private String rejectOneResult;
    private String twoInspectorResult;//二次质检结果
    private String NetPointTime;
    private Date repairsDate;//逆向产生时间
    private String orderNum;
    private String woId;
    private String repairsNum;
    private String reversePush;//重推
    private byte checkType;  //判断第几次质检类型
    private byte quality;  //判断质检结果
    private int handleStatus;//处理状态
    private String rejectOrSign;
    private String productName;
    private String refundAmount;
    private String reason;
    private String description;
    private String netPointCode;
    private Date rejectOrSignDate;
	public String getCenterName() {
		return centerName;
	}
	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}
	public String getWarehouseLocation() {
		return warehouseLocation;
	}
	public void setWarehouseLocation(String warehouseLocation) {
		this.warehouseLocation = warehouseLocation;
	}
	public String getNetListNum() {
		return netListNum;
	}
	public void setNetListNum(String netListNum) {
		this.netListNum = netListNum;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMaterielNum() {
		return materielNum;
	}
	public void setMaterielNum(String materielNum) {
		this.materielNum = materielNum;
	}
	public String getMachineNum() {
		return machineNum;
	}
	public void setMachineNum(String machineNum) {
		this.machineNum = machineNum;
	}
	public String getNegativeLadingListNum() {
		return negativeLadingListNum;
	}
	public void setNegativeLadingListNum(String negativeLadingListNum) {
		this.negativeLadingListNum = negativeLadingListNum;
	}
	public Date getNegativeProduceDate() {
		return negativeProduceDate;
	}
	public void setNegativeProduceDate(Date date) {
		this.negativeProduceDate = date;
	}
	public String getPushWarehouseNum() {
		return pushWarehouseNum;
	}
	public void setPushWarehouseNum(String pushWarehouseNum) {
		this.pushWarehouseNum = pushWarehouseNum;
	}
	public Date getPushWarehouseProduceDate() {
		return pushWarehouseProduceDate;
	}
	public void setPushWarehouseProduceDate(Date pushWarehouseProduceDate) {
		this.pushWarehouseProduceDate = pushWarehouseProduceDate;
	}
	public Date getPushCompleteDate() {
		return pushCompleteDate;
	}
	public void setPushCompleteDate(Date date) {
		this.pushCompleteDate = date;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public long getPeriod() {
		return period;
	}
	public void setPeriod(long day) {
		this.period = day;
	}
    public String getSecCode() {
		return secCode;
	}
    public void setSecCode(String secCode) {
		this.secCode = secCode;
	}
    public void setAddtimeTs(Date addtimeTs) {
		this.addtimeTs = addtimeTs;
	}
    public Date getAddtimeTs() {
		return addtimeTs;
	}
	public Date getReturnResultTime() {
		return returnResultTime;
	}
	public void setReturnResultTime(Date returnResultTime) {
		this.returnResultTime = returnResultTime;
	}
	public Date getSecondPushTime() {
		return secondPushTime;
	}
	public void setSecondPushTime(Date secondPushTime) {
		this.secondPushTime = secondPushTime;
	}
	public String getPushTime() {
		return pushTime;
	}
	public void setPushTime(String pushTime) {
		this.pushTime = pushTime;
	}
    public byte getStorageType() {
		return storageType;
	}
    public void setStorageType(byte storageType) {
		this.storageType = storageType;
	}
    public void setStorageDate(Date storageDate) {
		this.storageDate = storageDate;
	}
    public Date getStorageDate() {
		return storageDate;
	}
	public int getIstz() {
		return istz;
	}
	public void setIstz(int istz) {
		this.istz = istz;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getOutNum() {
		return outNum;
	}
	public void setOutNum(String outNum) {
		this.outNum = outNum;
	}
	public String getGenuineWarehouseNum() {
		return GenuineWarehouseNum;
	}
	public void setGenuineWarehouseNum(String genuineWarehouseNum) {
		GenuineWarehouseNum = genuineWarehouseNum;
	}
	public String getUndesirableWarehouseNum() {
		return UndesirableWarehouseNum;
	}
	public void setUndesirableWarehouseNum(String undesirableWarehouseNum) {
		UndesirableWarehouseNum = undesirableWarehouseNum;
	}

	public String getOneInspectorNum() {
		return oneInspectorNum;
	}
	public void setOneInspectorNum(String oneInspectorNum) {
		this.oneInspectorNum = oneInspectorNum;
	}
	public String getRejectOneResult() {
		return rejectOneResult;
	}
	public void setRejectOneResult(String rejectOneResult) {
		this.rejectOneResult = rejectOneResult;
	}
	public String getNetPointTime() {
		return NetPointTime;
	}
	public void setNetPointTime(String string) {
		NetPointTime = string;
	}
	public Date getRepairsDate() {
		return repairsDate;
	}
	public void setRepairsDate(Date repairsDate) {
		this.repairsDate = repairsDate;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getRepairsNum() {
		return repairsNum;
	}
	public void setRepairsNum(String repairsNum) {
		this.repairsNum = repairsNum;
	}
	public String getReversePush() {
		return reversePush;
	}
	public void setReversePush(String reversePush) {
		this.reversePush = reversePush;
	}
	public String getWoId() {
		return woId;
	}
	public void setWoId(String woId) {
		this.woId = woId;
	}
	public byte getCheckType() {
		return checkType;
	}
	public void setCheckType(byte checkType) {
		this.checkType = checkType;
	}
	public void setQuality(byte quality) {
		this.quality = quality;
	}
	public byte getQuality() {
		return quality;
	}
	 public void setHandleStatus(int handleStatus) {
		this.handleStatus = handleStatus;
	}
	 public int getHandleStatus() {
		return handleStatus;
	}
	 public void setTwoInspectorResult(String twoInspectorResult) {
		this.twoInspectorResult = twoInspectorResult;
	}
	 public String getTwoInspectorResult() {
		return twoInspectorResult;
	}
	public String getRejectOrSign() {
		return rejectOrSign;
	}
	public void setRejectOrSign(String rejectOrSign) {
		this.rejectOrSign = rejectOrSign;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNetPointCode() {
		return netPointCode;
	}
	public void setNetPointCode(String netPointCode) {
		this.netPointCode = netPointCode;
	}
	public Date getRejectOrSignDate() {
		return rejectOrSignDate;
	}
	public void setRejectOrSignDate(Date rejectOrSignDate) {
		this.rejectOrSignDate = rejectOrSignDate;
	}
	 public void setOnePushTime(String string) {
		OnePushTime = string;
	}
	 public String getOnePushTime() {
		return OnePushTime;
	}

	public int getRootId() {
		return rootId;
	}

	public void setRootId(int rootId) {
		this.rootId = rootId;
	}
}
