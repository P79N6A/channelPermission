package com.haier.eop.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单同步列队
 * @FileName:BaseOrdersQueue.java
 * @Version: 1.0
 * @Author: liulianghui
 * @Author: 641899873@qq.com
 * @CreateDate: 2014年10月30日 上午9:52:39
 */ 
public class OrdersQueue implements Serializable{
	private static final long serialVersionUID = 496807402855218488L;

	private Integer id;
	//渠道来源
	private String source;
	//订单Id
	private String sourceOrderSn;

	//系统状态(1001:待同步状态,1002:同步成功,1005:其它状态,1003:les已出库,101:订单异常,1006:已支付订金订单,1007:待同步尾款订单)
	private String ordersState;

	//第三方系统的返回的订单全部数据（json或xml）
	private String orderInfo;

	//错误日志
	private String errorLog;

	//同步时间
	private Long addTime;

	//最后同步时间
	private Long syncTime;

	//同步次数
	private Integer syncCount;

	//创建人(系统，人)
	private String creator;

	//订单类型(PR：初始，PP：订单处理中，EX：已出库，DL：已妥投，CWS：客服取消-待商家确认，CWC：商家申请取消，DFC：发货失败取消 ，RCC：客服取消，RV：拒收，RT：拒收已退回库房)
	private String orderType;

	//订单总金额
	private BigDecimal orderTotalPrice;

	//订单更新时间
	private Date modifyTime;

	//下单时间
	private Date orderTime;

	//订单支付时间
	private Date payTime;

	//订单类型 fixed普通 step预售
	private String type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSourceOrderSn() {
		return sourceOrderSn;
	}
	public void setSourceOrderSn(String sourceOrderSn) {
		this.sourceOrderSn = sourceOrderSn;
	}
	 
	public String getOrdersState() {
		return ordersState;
	}
	public void setOrdersState(String ordersState) {
		this.ordersState = ordersState;
	}
	public String getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public String getErrorLog() {
		return errorLog;
	}
	public void setErrorLog(String errorLog) {
		this.errorLog = errorLog;
	}
	public Long getAddTime() {
		return addTime;
	}
	public void setAddTime(Long addTime) {
		this.addTime = addTime;
	}
	public Long getSyncTime() {
		return syncTime;
	}
	public void setSyncTime(Long syncTime) {
		this.syncTime = syncTime;
	}
	public Integer getSyncCount() {
		return syncCount;
	}
	public void setSyncCount(Integer syncCount) {
		this.syncCount = syncCount;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}
	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
	 
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	@Override
	public String toString() {
		return "OrdersQueue{" +
				"source='" + source + '\'' +
				", sourceOrderSn='" + sourceOrderSn + '\'' +
				", ordersState='" + ordersState + '\'' +
				", orderInfo='" + orderInfo + '\'' +
				", errorLog='" + errorLog + '\'' +
				", addTime=" + addTime +
				", syncTime=" + syncTime +
				", syncCount=" + syncCount +
				", creator='" + creator + '\'' +
				", orderType='" + orderType + '\'' +
				", orderTotalPrice=" + orderTotalPrice +
				", modifyTime=" + modifyTime +
				", orderTime=" + orderTime +
				", payTime=" + payTime +
				", type='" + type + '\'' +
				'}';
	}
}
