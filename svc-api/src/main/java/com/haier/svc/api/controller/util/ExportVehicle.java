package com.haier.svc.api.controller.util;


public final class ExportVehicle {
	
	
	public static final String ED_CHANNEL_NAME="整车订单编号";
	public static final String CATEGORY_ID="小单号";
	public static final String PRODUCT_GROUP_NAME="扣款单号";
	public static final String VBELNDN1="一次DN1";
	public static final String VBELNDN5="二次DN5";
	public static final String VBELNSPARE="预约LBX备用DN";
	public static final String MATERIALS_ID="LBX单号";
	public static final String STORAGE_ID="LBX入库时间";
	public static final String LBX_STATUS="LBX入库状态";
	public static final String SAP_STATUS="推送SAP状态";
	public static final String T2_DELIVERY_PREDICTION_ZK="订单错误信息";
	public static final String T2_DELIVERY_PREDICTION_ZQ="扣款错误信息";
	public static final String PRICE="明细行数";
	public static final String AMOUNT_ZK="ZK订单状态";
	public static final String AMOUNT_ZQ="ZQ扣款状态";
	public static final String MATERIALS_DESC="物料编号";
	public static final String FLOW_FLAG_NAME="物料名称";
	public static final String ORDER_TYPE_NAME="产品组";
	public static final String CHANNEL_COMMIT_USER="产品组名称";
	public static final String CHANNEL_COMMIT_TIME_DISPLAY="品牌";
	public static final String AUDIT_DEPART_USER="数量";
	public static final String AUDIT_DEPART_TIME_DISPLAY="含税开票价";
	public static final String AUDIT_USER="总金额";
	public static final String AUDIT_TIME_DISPLAY="体积";
	public static final String ERROR_MSG="总体积";
	public static final String ORDER_CATEGORY_NAME="付款方编码";
	public static final String ORDER_CLOSE_TIME_DISPLAY="付款方名称";
	public static final String JD_NAME="生产基地";
	public static final String DELIVERY_CODE="送达方编码";
	public static final String DELIVERY_NAME="送达方名称";
	public static final String DISTRIBUTION_CENTRE="配送中心编码";
	public static final String DISTRIBUTION_CENTRE_NAME="配送中心名称";
	public static final String WH_CODE="电商库位码";
	public static final String ORDER_TIME="开单日期";
	public static final String DATE_OF_ARRIVAL="到货日期";

	public static String[] orderListQuery = {ORDER_CATEGORY_NAME,FLOW_FLAG_NAME,VBELNDN1,CHANNEL_COMMIT_TIME_DISPLAY,
		CHANNEL_COMMIT_USER,AUDIT_TIME_DISPLAY,AUDIT_USER,ORDER_CLOSE_TIME_DISPLAY
		};
	/**	 * 整车直发列表导出的表头	 */
	public static String[] orderListTitle = {ED_CHANNEL_NAME,CATEGORY_ID,PRODUCT_GROUP_NAME,PRICE,VBELNDN1,VBELNDN5,VBELNSPARE,MATERIALS_ID,STORAGE_ID,LBX_STATUS,SAP_STATUS,AMOUNT_ZK,AMOUNT_ZQ,T2_DELIVERY_PREDICTION_ZK,T2_DELIVERY_PREDICTION_ZQ,MATERIALS_DESC,FLOW_FLAG_NAME
		,ORDER_TYPE_NAME,CHANNEL_COMMIT_USER,CHANNEL_COMMIT_TIME_DISPLAY,AUDIT_DEPART_USER,AUDIT_DEPART_TIME_DISPLAY,AUDIT_USER,AUDIT_TIME_DISPLAY,ERROR_MSG,ORDER_CATEGORY_NAME,ORDER_CLOSE_TIME_DISPLAY,
			JD_NAME,DELIVERY_CODE,DELIVERY_NAME,DISTRIBUTION_CENTRE,DISTRIBUTION_CENTRE_NAME,WH_CODE,ORDER_TIME,DATE_OF_ARRIVAL
	};
	
}