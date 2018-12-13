package com.haier.svc.api.controller.util.http;

public class OrderPageable {
	/**
	 *  页码
	 */
	private Integer pageNo=1;
	
	/**
	 *每页数量
	 */
	private Integer pageSize=10;
		
	/**
	 *开始时间
	 */
	private String startDate;
	
	/**
	 *结束时间
	 */
	private String endDate;
	/**
	 * 订单状态 
	 */
	private String orderStatus=""; 
	
	/**
	 * 订单日期类型 (1：订单生成日期，2：订单付款日期，3：订单发货日期，4：订单收货日期，5：订单更新日期) 
	 */
	private Integer dateType;
	
	/**
	 * 分页查询用的nextToken
	 */
	private String nextToken;

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getDateType() {
		return dateType;
	}

	public void setDateType(Integer dateType) {
		this.dateType = dateType;
	}

	public String getNextToken() {
		return nextToken;
	}

	public void setNextToken(String nextToken) {
		this.nextToken = nextToken;
	}
	
	
	
}
