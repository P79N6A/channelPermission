package com.haier.shop.model;

import java.io.Serializable;

/**
 * 退仓单表
 * @author wangp-c
 *
 */
public class OrderRepairTcs implements Serializable{

	private static final long serialVersionUID = -2036376350331167975L;

	private Integer id;
	
	/**添加时间 */
	private Integer addTime;
	
	/**不良品退货单id，正品默认0 */
	private Integer orderRepairId;
	
	/**菜鸟退仓单据ID*/
	private String caiNiaoTcId;
	
	/**菜鸟审核状态  1待审批，2审批通过，3驳回,4关闭，5已创建出库单 */
	private Integer caiNiaoTcHandle;
	
	/**菜鸟审核时间 */
	private Integer caiNiaoTcHandleTime;
	
	/**期望出库时间 */
	private Integer caiNiaoTcHopeTime;
	
	/**退仓类型 */
	private Integer caiNiaoTcExtType;
	
	/**出库单号 */
	private String caiNiaoTcExtSn;
	
	/**出库时间 */
	private Integer caiNiaoTcExtTime;

	/**1已接单 2已出库 3 订单已取消 */
	private Integer caiNiaoTcExtStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAddTime() {
		return addTime;
	}

	public void setAddTime(Integer addTime) {
		this.addTime = addTime;
	}

	public Integer getOrderRepairId() {
		return orderRepairId;
	}

	public void setOrderRepairId(Integer orderRepairId) {
		this.orderRepairId = orderRepairId;
	}

	public String getCaiNiaoTcId() {
		return caiNiaoTcId;
	}

	public void setCaiNiaoTcId(String caiNiaoTcId) {
		this.caiNiaoTcId = caiNiaoTcId;
	}

	public Integer getCaiNiaoTcHandle() {
		return caiNiaoTcHandle;
	}

	public void setCaiNiaoTcHandle(Integer caiNiaoTcHandle) {
		this.caiNiaoTcHandle = caiNiaoTcHandle;
	}

	public Integer getCaiNiaoTcHandleTime() {
		return caiNiaoTcHandleTime;
	}

	public void setCaiNiaoTcHandleTime(Integer caiNiaoTcHandleTime) {
		this.caiNiaoTcHandleTime = caiNiaoTcHandleTime;
	}

	public Integer getCaiNiaoTcHopeTime() {
		return caiNiaoTcHopeTime;
	}

	public void setCaiNiaoTcHopeTime(Integer caiNiaoTcHopeTime) {
		this.caiNiaoTcHopeTime = caiNiaoTcHopeTime;
	}

	public Integer getCaiNiaoTcExtType() {
		return caiNiaoTcExtType;
	}

	public void setCaiNiaoTcExtType(Integer caiNiaoTcExtType) {
		this.caiNiaoTcExtType = caiNiaoTcExtType;
	}

	public String getCaiNiaoTcExtSn() {
		return caiNiaoTcExtSn;
	}

	public void setCaiNiaoTcExtSn(String caiNiaoTcExtSn) {
		this.caiNiaoTcExtSn = caiNiaoTcExtSn;
	}

	public Integer getCaiNiaoTcExtTime() {
		return caiNiaoTcExtTime;
	}

	public void setCaiNiaoTcExtTime(Integer caiNiaoTcExtTime) {
		this.caiNiaoTcExtTime = caiNiaoTcExtTime;
	}

	public Integer getCaiNiaoTcExtStatus() {
		return caiNiaoTcExtStatus;
	}

	public void setCaiNiaoTcExtStatus(Integer caiNiaoTcExtStatus) {
		this.caiNiaoTcExtStatus = caiNiaoTcExtStatus;
	}
}
