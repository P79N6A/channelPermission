package com.haier.eop.data.model;



import java.io.Serializable;
import java.util.List;

/**
 * 辅助订单帮助类
 * 
 * @FileName:QueueOrderHelper.java
 * @Version: 1.0
 * @Author: liulianghui
 * @Author: 641899873@qq.com
 * @CreateDate: 2014年11月10日 下午8:28:19
 */
public class QueueOrderHelper implements Serializable {

	private static final long serialVersionUID = 825076469404786811L;
	
	private boolean isSuccess;

	private String errorLog;

	private Orders order;

	private List<OrderProducts> opList;

	private MemberInvoices invoices;

	// 在一次性请求多个订单情况下使用。
	private String sourceOrderSn;

	private List<OrderCoupons> orderCoupons;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrorLog() {
		return errorLog;
	}

	public void setErrorLog(String errorLog) {
		this.errorLog = errorLog;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public List<OrderProducts> getOpList() {
		return opList;
	}

	public void setOpList(List<OrderProducts> opList) {
		this.opList = opList;
	}

	public MemberInvoices getInvoices() {
		return invoices;
	}

	public void setInvoices(MemberInvoices invoices) {
		this.invoices = invoices;
	}

	public String getSourceOrderSn() {
		return sourceOrderSn;
	}

	public void setSourceOrderSn(String sourceOrderSn) {
		this.sourceOrderSn = sourceOrderSn;
	}

	public List<OrderCoupons> getOrderCoupons() {
		return orderCoupons;
	}

	public void setOrderCoupons(List<OrderCoupons> orderCoupons) {
		this.orderCoupons = orderCoupons;
	}

}