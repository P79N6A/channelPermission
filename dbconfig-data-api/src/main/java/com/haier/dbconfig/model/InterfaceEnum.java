package com.haier.dbconfig.model;

public enum InterfaceEnum {

	CHAXUN("查询单据列表", "taobao.qimen.orderstatus.batchquery", "OrderStatusResponse"),
    FAHUO("获取销售订单发货信息","taobao.qimen.deliveryorder.query", "DeliveryOrderResponse"),
    TUIHUORUKUDAN("退货入库单查询接口","taobao.qimen.returnorder.query", "ReturnOrderrResponse"),
    RUKUDAN("获取入库单信息","taobao.qimen.entryorder.query", "EntryOrderResponse"),
    CHUKIFAHUO("通过订单号获取单个出库单发货信息","taobao.qimen.stockout.query", "StockOutResponse"),
    CHUKUQUEREN("出库单确认接口","taobao.qimen.stockout.confirm", "StockOutConfirmResponse"),
    STOCK_QUERY("渠道库存查询接口","taobao.qimen.channelinventory.query", "ChanneLinventoryResponse"),
    SUNYIDAN("通过订单列表批量获取库存损益单信息","taobao.qimen.inventorycheck.query", "InventoryCheckResponse"),
    TRANSFER_CREATE("3w调拨单创建","taobao.qimen.transferorder.create", "TransferOrderCreateResponse"),
    TRANSFER_CANCEL("3w单据取消","taobao.qimen.order.cancel", "TransferOrderCancelResponse"),
    TRANSFER_QUERY("3w调拨单查询","taobao.qimen.transferorder.query", "TransferOrderQueryResponse"),
    ENTRY_QUERY("3w入库单查询","taobao.qimen.entryorder.query", "EntryOrderResponse"),
    DIAOBOCHECK("调拨单查询接口","taobao.qimen.transferorder.query","DiaoboInOutStockQueryResponse"),
    RKDZTHC("入库单状态回传","taobao.qimen.entryorder.callback","EntryOrderCallBackResponse");

    private InterfaceEnum(String interfaceInfo, String interfaceName, String resultObject){
        this.interfaceInfo = interfaceInfo;
        this.interfaceName = interfaceName;
        this.resultObject = resultObject;
    };
    
    private String interfaceInfo;
    private String interfaceName;
    private String resultObject;
    
	public String getInterfaceInfo() {
		return interfaceInfo;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public String getResultObject() {
		return resultObject;
	}
    
}
