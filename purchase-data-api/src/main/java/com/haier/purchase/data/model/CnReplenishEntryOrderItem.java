package com.haier.purchase.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CnReplenishEntryOrderItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9040290024757087169L;
	
	private Long id;
	/**
	 * 补货入库单id
	 */
	private Long replEntryOrderId;
	/**
	 * 85单号
	 */
	private String orderItemId;
	/**
	 * 货主ID
	 */
	private String userId;
	/**
	 * 卖家名称
	 */
	private String userName;
	/**
	 * 菜鸟商品ID
	 */
	private String itemId;
	/**
	 * 菜鸟商品名称
	 */
	private String itemName;
	/**
	 * ERP商品编码
	 */
	private String itemCode;
	/**
	 * 套装 0:否 1:是
	 */
	private Boolean isMachineSet;
	/**
	 * 套装sku
	 */
	private String mainSku;
	/**
	 * 套装内数量
	 */
	private Integer menge;
	/**
	 * 库存类型 1:可销售库存正品 101:残次 102:机损 103:箱损 201:冻结库存 301:在途库存
	 */
	private Integer inventoryType;
	/**
	 * 销售价格
	 */
	private BigDecimal itemPrice;
	/**
	 * 商品数量
	 */
	private Integer itemQuantity;
	/**
	 * 出库确认数量
	 */
	private BigDecimal confirmQty;
	/**
	 * 商品版本号
	 */
	private Integer itemVersion;
	/**
	 * 批次备注
	 */
	private String batchRemark;
	/**
	 * 记录生成时间
	 */
	private Date insertTime;
	/**
	 * 记录修改时间
	 */
	private Date modityTime;
	/**
	 * //0 初始值
		//1 出入库数量不一致的
		//2 出入库数量一致，可以推送SAP  （出库数量=入库正品+入库残品）
		//3 推送SAP失败的
		//5 推送成功的
	 */
	private Integer inSap;
	/**
	 * 行项目号
	 */
	private String itemNum;
	/**
	 * 入库推送SAP时间
	 */
	private Date inSapTime;
	/**
	 * sap返回信息
	 */
	private String sapMsg;
	/**
	 * 类型
	 */
	private Integer type;
	/**
	 * 入库正品数量
	 */
	private Integer inCount;
	/**
	 * 入库残品数量
	 */
	private Integer remnantNum;
	private String outSapMsg;

	public String getOutSapMsg() {
		return outSapMsg;
	}

	public void setOutSapMsg(String outSapMsg) {
		this.outSapMsg = outSapMsg;
	}

	/**
	 * //0 初始值
		//1 出入库数量不一致的
		//2 出入库数量一致，可以推送SAP  （出库数量=入库正品+入库残品）
		//3 推送SAP失败的
		//5 推送成功的
	 */
	private Integer outSap;
	/**
	 * 出库推送SAP时间
	 */
	private Date outSapTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getReplEntryOrderId() {
		return replEntryOrderId;
	}

	public void setReplEntryOrderId(Long replEntryOrderId) {
		this.replEntryOrderId = replEntryOrderId;
	}

	public String getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Boolean getIsMachineSet() {
		return isMachineSet;
	}

	public void setIsMachineSet(Boolean isMachineSet) {
		this.isMachineSet = isMachineSet;
	}

	public String getMainSku() {
		return mainSku;
	}

	public void setMainSku(String mainSku) {
		this.mainSku = mainSku;
	}

	public Integer getMenge() {
		return menge;
	}

	public void setMenge(Integer menge) {
		this.menge = menge;
	}

	public Integer getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(Integer inventoryType) {
		this.inventoryType = inventoryType;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Integer getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Integer itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public BigDecimal getConfirmQty() {
		return confirmQty;
	}

	public void setConfirmQty(BigDecimal confirmQty) {
		this.confirmQty = confirmQty;
	}

	public Integer getItemVersion() {
		return itemVersion;
	}

	public void setItemVersion(Integer itemVersion) {
		this.itemVersion = itemVersion;
	}

	public String getBatchRemark() {
		return batchRemark;
	}

	public void setBatchRemark(String batchRemark) {
		this.batchRemark = batchRemark;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getModityTime() {
		return modityTime;
	}

	public void setModityTime(Date modityTime) {
		this.modityTime = modityTime;
	}

	public String getItemNum() {
		return itemNum;
	}

	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}

	public Date getInSapTime() {
		return inSapTime;
	}

	public void setInSapTime(Date inSapTime) {
		this.inSapTime = inSapTime;
	}

	public String getSapMsg() {
		return sapMsg;
	}

	public void setSapMsg(String sapMsg) {
		this.sapMsg = sapMsg;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getInCount() {
		return inCount;
	}

	public void setInCount(Integer inCount) {
		this.inCount = inCount;
	}

	public Integer getRemnantNum() {
		return remnantNum;
	}

	public void setRemnantNum(Integer remnantNum) {
		this.remnantNum = remnantNum;
	}

	public Date getOutSapTime() {
		return outSapTime;
	}

	public void setOutSapTime(Date outSapTime) {
		this.outSapTime = outSapTime;
	}

	public Integer getInSap() {
		return inSap;
	}

	public void setInSap(Integer inSap) {
		this.inSap = inSap;
	}

	public Integer getOutSap() {
		return outSap;
	}

	public void setOutSap(Integer outSap) {
		this.outSap = outSap;
	}
}
