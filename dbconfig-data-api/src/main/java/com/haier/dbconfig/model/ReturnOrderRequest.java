package com.haier.dbconfig.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="request")
public class ReturnOrderRequest {

	/**
	 * 参数说明：此处的参数适用于所有接口中的参数，属于共同使用，如果需要添加新的属性
	 *           请将下面的XML中添加注释
	 * <?xml version="1.0" encoding="utf-8"?>
		<request>
		<ownerCode>货主编码,  string (50) </ownerCode>
		<warehouseCode>仓库编码, string (50)</warehouseCode>
		<returnOrderCode>退货单编码,  string (50) ,  必填</returnOrderCode>
		<returnOrderId>仓库系统订单编码, string (50) ,条件必填</returnOrderId>
		<deliveryOrderId>仓储系统出库单号, string (50) ，条件必填</deliveryOrderId>
		<startTime>订单最后操作时间（查询起始时间点），string (50)，YYYY-MM-DD hh:mm:ss，必填</startTime>
		<endTime>订单最后操作时间（查询截止时间点），默认当前时间，string (50)，YYYY-MM-DD hh:mm:ss</endTime>
		<orderType>单据类型, string (50) ，JYCK= 一般交易出库单，HHCK= 换货出库 ，BFCK= 补发出库，PTCK=普通出库单，DBCK=调拨出库 ，QTCK=其他出库，B2BRK=B2B入库，B2BCK=B2B出库，CGRK=采购入库 ，DBRK= 调拨入库 ，QTRK= 其他入库 ，XTRK= 销退入库，HHRK= 换货入库，CNJG= 仓内加工单</orderType>
		<checkOrderCode>盘点单编码, string（50），必填</checkOrderCode>
		<checkOrderId>仓储系统的盘点单编码, string（50），条件必填</checkOrderId>
		<currentPage>当前第几页，从1开始，int，必填</currentPage>
		<page>当前页，从1开始，必填</page>
		<pageSize>每页orderLine条数（最多100条），必填</pageSize>
	    <deliveryOrderCode>出库单号, string (50) , 必填</deliveryOrderCode>
		</request>
	 */
	private String ownerCode;
	private String orderCode;
	private String orderId;
	private String warehouseCode;
	private String returnOrderCode;
	private String returnOrderId;
	private String currentPage;
	private String orderSourceCode;
	private String checkOrderCode;
	private String checkOrderId;
	private String page;
	private String pageSize;
	private String deliveryOrderId;
	private String deliveryOrderCode;
	private String startTime;
	private String endTime;
	private String orderType;
    private String entryOrderId;
    private String entryOrderCode;
    private WarehouseCodes warehouseCodes;
    private ItemCodes itemCodes;
    private ChannelCodes channelCodes;
    private String transferOrderCode;

    public static class WarehouseCodes{
    	private List<String> warehouseCode;

		public List<String> getWarehouseCode() {
			return warehouseCode;
		}
		public void setWarehouseCode(List<String> warehouseCode) {
			this.warehouseCode = warehouseCode;
		}
    }

    public static class ItemCodes{
    	private List<String> itemCode;

		public List<String> getItemCode() {
			return itemCode;
		}
		public void setItemCode(List<String> itemCode) {
			this.itemCode = itemCode;
		}
    }

    public static class ChannelCodes{
    	private List<String> channelCode;

		public List<String> getChannelCode() {
			return channelCode;
		}
		public void setChannelCode(List<String> channelCode) {
			this.channelCode = channelCode;
		}
    }

	public WarehouseCodes getWarehouseCodes() {
		return warehouseCodes;
	}
	public void setWarehouseCodes(WarehouseCodes warehouseCodes) {
		this.warehouseCodes = warehouseCodes;
	}
	public ItemCodes getItemCodes() {
		return itemCodes;
	}
	public void setItemCodes(ItemCodes itemCodes) {
		this.itemCodes = itemCodes;
	}
	public ChannelCodes getChannelCodes() {
		return channelCodes;
	}
	public void setChannelCodes(ChannelCodes channelCodes) {
		this.channelCodes = channelCodes;
	}
	public String getEntryOrderId() {
		return entryOrderId;
	}
	public void setEntryOrderId(String entryOrderId) {
		this.entryOrderId = entryOrderId;
	}
	public String getEntryOrderCode() {
		return entryOrderCode;
	}
	public void setEntryOrderCode(String entryOrderCode) {
		this.entryOrderCode = entryOrderCode;
	}
	
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getOrderSourceCode() {
		return orderSourceCode;
	}
	public void setOrderSourceCode(String orderSourceCode) {
		this.orderSourceCode = orderSourceCode;
	}
	public String getCheckOrderCode() {
		return checkOrderCode;
	}
	public void setCheckOrderCode(String checkOrderCode) {
		this.checkOrderCode = checkOrderCode;
	}
	public String getCheckOrderId() {
		return checkOrderId;
	}
	public void setCheckOrderId(String checkOrderId) {
		this.checkOrderId = checkOrderId;
	}
	public String getDeliveryOrderId() {
		return deliveryOrderId;
	}
	public void setDeliveryOrderId(String deliveryOrderId) {
		this.deliveryOrderId = deliveryOrderId;
	}
	public String getDeliveryOrderCode() {
		return deliveryOrderCode;
	}
	public void setDeliveryOrderCode(String deliveryOrderCode) {
		this.deliveryOrderCode = deliveryOrderCode;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
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
	public String getReturnOrderCode() {
		return returnOrderCode;
	}
	public void setReturnOrderCode(String returnOrderCode) {
		this.returnOrderCode = returnOrderCode;
	}
	public String getReturnOrderId() {
		return returnOrderId;
	}
	public void setReturnOrderId(String returnOrderId) {
		this.returnOrderId = returnOrderId;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getTransferOrderCode() {
		return transferOrderCode;
	}
	public void setTransferOrderCode(String transferOrderCode) {
		this.transferOrderCode = transferOrderCode;
	}
	
}
