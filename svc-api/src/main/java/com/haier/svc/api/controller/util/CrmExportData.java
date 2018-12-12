package com.haier.svc.api.controller.util;

public class CrmExportData {
	
	public static final String ORDER_ID="WP订单号";
	public static final String PO_ID="PO编号";
	public static final String BILL_ORDER_ID="销售单号";
	public static final String BILL_TIME_DISPLAY="提单时间";
	public static final String SO_ID="SO";
	public static final String DN_ID="DN";
	public static final String ED_CHANNEL_NAME="渠道";
	public static final String CATEGORY_ID="品类";
	public static final String PRODUCT_GROUP_NAME="产品组";
	public static final String MATERIALS_ID="物料编号";
	public static final String MATERIALS_DESC="型号";
	public static final String QTY="数量";
	public static final String PRICE="样表单价";
	public static final String T2_AMOUNT="样表金额";
	public static final String AMOUNT="采购价格";
	public static final String TOTAL="采购金额";
	public static final String STORAGE_ID="WA库位码";
	public static final String STORAGE_NAME="WA库位名称";
	public static final String FLOW_FLAG_NAME="状态";
	public static final String RRS_OUT_TIME_DISPLAY="出日日顺库时间";
	public static final String WA_IN_TIME_DISPLAY="入库时间";
	
	
	/**
	* PO导出excel字段 赵环宇
	*/
	public static String[] poListQuery ={ORDER_ID,PO_ID,BILL_ORDER_ID,BILL_TIME_DISPLAY,SO_ID,DN_ID,ED_CHANNEL_NAME,
		CATEGORY_ID,PRODUCT_GROUP_NAME,MATERIALS_ID,MATERIALS_DESC,QTY,PRICE,T2_AMOUNT,AMOUNT,TOTAL,
		STORAGE_ID,STORAGE_NAME,FLOW_FLAG_NAME,RRS_OUT_TIME_DISPLAY,WA_IN_TIME_DISPLAY
	};
	
	
	

}
