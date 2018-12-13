package com.haier.purchase.data.model.vehcile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 下单接口入参
 *
 * @author
 * @create 2017-09-05 9:11
 **/
public class Entry3wOrder implements Serializable {
	private static final long serialVersionUID = -3277446934783709400L;
	private int id;
	private String entryOrderCode;	//入库单编码
	private String ownerCode;		//货主编码
	private String warehouseCode;	//仓库编码
	private String entryOrderId;	//仓储系统入库单
	private String entryOrderType;	//CGRK=采购入库, QTRK=其他入库，B2BRK=B2B入库
	private String status;			//入库单状态 NEW-未开始处理,  ACCEPT-仓库接单 , PARTFULFILLED-部分收货完成,  FULFILLED-收货完成,  EXCEPTION-异常,  CANCELED-取消,  CLOSED-关闭,  REJECT-拒单,  CANCELEDFAIL-取消失败
	private String operateTime;		//操作时间
	private String flag;			//success|failure
	private String code;			//响应码
	private String message;			//响应信息
	private int totalLines;			//总条数
	private String pushStatus;			//推送状态（1成功，0未推送或失败）
	private String itemCode;
	private Integer planQty;
	private Integer actualQty;
	
	private List<OrderLines> orderLines = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEntryOrderCode() {
		return entryOrderCode;
	}

	public void setEntryOrderCode(String entryOrderCode) {
		this.entryOrderCode = entryOrderCode;
	}

	public String getOwnerCode() {
		return ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getEntryOrderId() {
		return entryOrderId;
	}

	public void setEntryOrderId(String entryOrderId) {
		this.entryOrderId = entryOrderId;
	}

	public String getEntryOrderType() {
		return entryOrderType;
	}

	public void setEntryOrderType(String entryOrderType) {
		this.entryOrderType = entryOrderType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getTotalLines() {
		return totalLines;
	}

	public void setTotalLines(int totalLines) {
		this.totalLines = totalLines;
	}

	public String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getPlanQty() {
        return planQty;
    }

    public void setPlanQty(Integer planQty) {
        this.planQty = planQty;
    }

	public List<OrderLines> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLines> orderLines) {
		this.orderLines = orderLines;
	}

	public Integer getActualQty() {
		return actualQty;
	}

	public void setActualQty(Integer actualQty) {
		this.actualQty = actualQty;
	}
}
