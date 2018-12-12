package com.haier.svc.api.controller.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

public final class ExportData {
	
	
	public static final String ED_CHANNEL_NAME="渠道";
	public static final String CATEGORY_ID="品类";
	public static final String PRODUCT_GROUP_NAME="产品组";
	public static final String ORDER_ID="订单号";
	public static final String MATERIALS_ID="物料号";
	public static final String STORAGE_ID="库位";
	public static final String T2_DELIVERY_PREDICTION="数量";
	public static final String PRICE="样表价格";
	public static final String AMOUNT="金额";
	public static final String MATERIALS_DESC="型号";
	public static final String MATERIALS_TYPE="类型";
	public static final String FLOW_FLAG_NAME="状态";
	public static final String ORDER_TYPE_NAME="订单类型";
	public static final String CHANNEL_COMMIT_USER="渠道提交人";
	public static final String CHANNEL_COMMIT_TIME_DISPLAY="渠道提交时间";
	public static final String AUDIT_DEPART_USER="产品部审核人";
	public static final String AUDIT_DEPART_TIME_DISPLAY="产品部审核时间";
	public static final String AUDIT_USER="内部审核人";
	public static final String AUDIT_TIME_DISPLAY="内部审核时间";
	public static final String ERROR_MSG="备注";
	public static final String ORDER_CATEGORY_NAME="订单类别";
	public static final String ORDER_CLOSE_TIME_DISPLAY="手工关单时间";
	public static final String ORDER_CLOSE_USER="手工关单人";
	public static final String	 OMS_ORDER_ID="OMS订单号";
	public static final String CUSTOM_DESC="客户名称";
	public static final String GGG="售达方";
	public static final String TRANSMIT_DESC="送达方";
	public static final String REPORT_YEAR_WEEK="提报年";
	public static final String REPORT_YEAR_WEEK_DISPLAY="提报周";
	public static final String ARRIVE_TEAR_WEEK="到货年";
	public static final String ARRIVE_TEAR_WEEK_DISPLAY="到货周";
	public static final String INDUSTRT_TRADE_DESC="工贸";
	public static final String BRAND_ID="品牌";
	public static final String STORAGE_NAME="WA库位名称";
	public static final String ARRIAL_STORAGE_DESC="日日顺库位名称";
	public static final String SERIES_ID="系列";
	public static final String STATUS="OMS状态";
	public static final String LATEST_ARRIVE_DATE="最晚到货日期";
	public static final String PLAN_DELIVER_DATE_DISPLAY="最晚到货日期";
	public static final String PROMISE_ARRIVE_DATE_DISPLAY="承诺到货日期";
	public static final String ACTUAL_DELIVER_DATE_DISPLAY="实际发货日期";
	public static final String PREDICT_ARRIVE_DATE_DISPLAY="预计到货日期";
	public static final String INDUSTRY_TRADE_TAKE_DATE_DISPLAY="工贸收货日期";
	public static final String CUSTOM_SIGN_DATE_DISPLAY="工贸签收日期";
	public static final String RETURN_ORDER_DATE_DISPLAY="工贸返单日期";
	public static final String LATEST_LEAVE_BASE_DATE_DISPLAY="最晚离基地日期";
	public static final String FACTORY_ID="发运工厂编码";
	public static final String FACTORY_NAME="发运工厂名称";
	public static final String SHIPMENT_COMBINATION_ID="一次发运单号";
	public static final String SIGN_NUM="工贸签收数量";
	public static final String NO_PASS_REASON="OMS拒单原因";
	public static final String GVS_ORDER_ID="GVS订单号";
	public static final String DN_ID="日日顺DN号";
	public static final String CUSTPODETAILCODE="客户订单号";
	public static final String COMMIT_TIME_DISPLAY="提交日期";
	public static final String CUSTOMIZATION_NAME="定制";
	public static final String SATISFY_TYPE_NAME="满足方式";
	public static final String WAQTY="已入WA库数量";
	public static final String PASS_REASON="冻结推送意见";
	public static final String CATEGORY = "品类";

	public static final String ORDERSN = "订单号";
	public static final String SOURCE = "订单来源";
	public static final String SOURCEORDERSN = "来源订单号";
	public static final String RELATIONORDERSN = "关联订单号";
	public static final String ADDTIMESTART = "下单时间";
	public static final String FIRSTTURETIME = "首次确认时间";
	public static final String SURETIME = "确认时间";
	public static final String ORDERAMOUNT = "订单金额";
	public static final String ORDERSTATUS = "订单状态";
	public static final String OID = "关联订单号";
	public static final String EXPRESSNO = "物流编号";
	public static final String CANCELSTATUS = "订单取消状态";
	
	public static final String CORDERSN = "网单编号";
	public static final String PRODUCTNAME = "商品名称";
	public static final String SKU = "商品编号";
	public static final String TYPENAME = "商品分类";
	public static final String NUMBER = "数量";
	
	public static final String CPAYTIME = "付款时间";
	
	public static final String INDEX = "序号";
	public static final String WANGDAN = "网单号";
	public static final String YUANWANGDAN = "原网单号";
	public static final String SALER = "销售代表";
	public static final String BRAND = "品牌";
	public static final String SKUCODE ="SKU";
	public static final String BABYMODEL = "宝贝型号";
	public static final String CONSIGNEENAME = "收件人姓名";
	public static final String SALENUMBER = "销售数量";
	public static final String TOTALPRICES = "总价（发票金额）";
	public static final String PRODUCTSTATUS = "网单状态";
	public static final String PERIOD = "期间";
	public static final String YEAR = "年度";
	public static final String  INVOICETIME = "发票时间";
	public static final String INVOICESTATUS = "开票状态";
	public static final String PAMOUNT= "网单金额";
	public static final String OAMOUNT= "订单金额";
	public static final String SHR= "收货人";
	public static final String LXDH= "联系电话";
	public static final String SZD= "所在地";
	public static final String XXXX= "详细信息";
	public static final String SUPPLYPRICE = "结算价格";
	public static final String SUPPLYAMOUNT = "结算金额（元）";
	public static final String COMMISSION = "佣金（元）";
	public static final String MONTHPOLICY = "返点率";

    public static final String CATEGORYID = "品类ID";
    public static final String CATEGORYNAME = "品类";
    public static final String COMSKU = "物料编码";
    public static final String COMPRODUCTNAME = "型号名称";
    public static final String BRANDID= "品牌ID";
    public static final String BRANDNAME = "品牌名称";
    public static final String CHANNENAME ="渠道";
    public static final String CHANNUBER ="渠道编码";
    public static final String REWARD ="政策";
    public static final String YEAR1 ="一季度";
    public static final String YEAR2 ="二季度";
    public static final String YEAR3 ="三季度";
    public static final String YEAR4 ="四季度";
    public static final String YEAR5 ="年度";
    public static final String INVOICE1 ="结算金额(万)";
    public static final String INVOICE2 ="目标值(万)";
    public static final String INVOICE3 ="政策(%)";
    public static final String INVOICE4 ="返佣金额(元)";
    public static final String POLICYYear ="政策年度";
    public static final String YUE1 ="1月";
    public static final String YUE2 ="2月";
    public static final String YUE3 ="3月";
    public static final String YUE4 ="4月";
    public static final String YUE5 ="5月";
    public static final String YUE6 ="6月";
    public static final String YUE7 ="7月";
    public static final String YUE8 ="8月";
    public static final String YUE9 ="9月";
    public static final String YUE10 ="10月";
    public static final String YUE11 ="11月";
    public static final String YUE12 ="12月";
    public static final String STOCKPRODUCTNAME ="产品型号";
    public static final String SECCODE ="库位编码";
    public static final String SECNAME ="库位名称";
    public static final String STOCKSTOCKQTY ="实际库存";
    public static final String STOCKFROZENQTY ="占有库存";
    public static final String AVAIABLEQTY ="可用库存";
    public static final String MODIFIER = "最后更新人";
    public static final String product_type ="产品类型";
    public static final String LENGTH = "长度";
    public static final String HEIGHT = "高度";
    public static final String WIDTH = "宽度";
    public static final String WEIGHT ="总重量";
    public static final String weight_unit = "单位";
    public static final String is_auto_update = "是否自动同步";
    public static final String pro_band = "品牌";
    public static final String delete_flag = "删除标志";
    public static final String CREATETIME ="创建时间";
    public static final String UPDATETIME ="更新时间";
    public static final String REFNO ="单据号";
    public static final String PRODUCTNAMES ="物料型号";
    public static final String LOG ="日志内容";
	public static final String STORECODE ="店铺码";
	public static final String STORENAME ="店铺名称";
	public static final String STORKQTY ="总库存量";
	public static final String ECTIME ="EC库存更新时间";
	public static String[] orderListQuery = {ORDER_CATEGORY_NAME,FLOW_FLAG_NAME,ORDER_ID,CHANNEL_COMMIT_TIME_DISPLAY,
		CHANNEL_COMMIT_USER,AUDIT_TIME_DISPLAY,AUDIT_USER,ORDER_CLOSE_TIME_DISPLAY,ORDER_CLOSE_USER,OMS_ORDER_ID,
		CUSTOM_DESC,GGG,TRANSMIT_DESC,REPORT_YEAR_WEEK,REPORT_YEAR_WEEK_DISPLAY,ARRIVE_TEAR_WEEK,ARRIVE_TEAR_WEEK_DISPLAY,
		INDUSTRT_TRADE_DESC,ED_CHANNEL_NAME,CATEGORY_ID,PRODUCT_GROUP_NAME,BRAND_ID,MATERIALS_ID,
		MATERIALS_DESC,ORDER_TYPE_NAME,T2_DELIVERY_PREDICTION,PRICE,AMOUNT,STORAGE_ID,STORAGE_NAME,ARRIAL_STORAGE_DESC,
		SERIES_ID,STATUS,LATEST_ARRIVE_DATE,PLAN_DELIVER_DATE_DISPLAY,PROMISE_ARRIVE_DATE_DISPLAY,ACTUAL_DELIVER_DATE_DISPLAY,
		PREDICT_ARRIVE_DATE_DISPLAY,INDUSTRY_TRADE_TAKE_DATE_DISPLAY,CUSTOM_SIGN_DATE_DISPLAY,RETURN_ORDER_DATE_DISPLAY,
		LATEST_LEAVE_BASE_DATE_DISPLAY,FACTORY_ID,FACTORY_NAME,SHIPMENT_COMBINATION_ID,SIGN_NUM,NO_PASS_REASON,GVS_ORDER_ID,
		DN_ID,CUSTPODETAILCODE,COMMIT_TIME_DISPLAY,CUSTOMIZATION_NAME,SATISFY_TYPE_NAME,WAQTY,ERROR_MSG,PASS_REASON
		};
	
	
	/*
	public static final String ORDERSN = "订单号";
	public static final String SOURCE = "订单来源";
	public static final String SOURCEORDERSN = "来源订单号";
	public static final String ADDTIMESTART = "下单时间";
	public static final String FIRSTTURETIME = "首次确认时间";
	public static final String SURETIME = "确认时间";
	public static final String ORDERAMOUNT = "订单金额";
	public static final String ORDERSTATUS = "订单状态";
	public static final String OID = "关联订单号";
	public static final String EXPRESSNO = "物流编号";
	public static final String CANCELSTATUS = "订单取消状态";
	
	public static final String CORDERSN = "网单编号";
	public static final String PRODUCTNAME = "商品名称";
	public static final String SKU = "商品编号";
	public static final String TYPENAME = "商品类型";
	public static final String PRICE = "价格";
	public static final String NUMBER = "数量";
	
	public static final String CPAYTIME = "付款时间";
	
	public static final String INDEX = "序号";
	public static final String WANGDAN = "网单号";
	public static final String YUANWANGDAN = "原网单号";
	public static final String SALER = "销售代表";
	public static final String CATEGORY = "品类";
	public static final String BRAND = "品牌";
	public static final String SKUCODE ="SKU";
	public static final String BABYMODEL = "宝贝型号";
	public static final String CONSIGNEENAME = "收件人姓名";
	public static final String SALENUMBER = "销售数量";
	public static final String TOTALPRICES = "总价（发票金额）";
	public static final String PRODUCTSTATUS = "网单状态";
	public static final String PERIOD = "期间";
	public static final String YEAR = "年度";
	public static final String  INVOICETIME = "发票时间";
	public static final String INVOICESTATUS = "开票状态";
	public static final String STATUS = "状态";
	*/
	/**	 * 订单列表导出的表头	 */
	public static String[] orderListTitle = {ED_CHANNEL_NAME,CATEGORY_ID,PRODUCT_GROUP_NAME,ORDER_ID,MATERIALS_ID,STORAGE_ID,T2_DELIVERY_PREDICTION,PRICE,AMOUNT,MATERIALS_DESC,FLOW_FLAG_NAME
		,ORDER_TYPE_NAME,CHANNEL_COMMIT_USER,CHANNEL_COMMIT_TIME_DISPLAY,AUDIT_DEPART_USER,AUDIT_DEPART_TIME_DISPLAY,AUDIT_USER,AUDIT_TIME_DISPLAY,ERROR_MSG
	};
	/**订单佣金信息*/
	public static String[] commissionListTitle = {ORDERSN,SOURCEORDERSN,SOURCE,PRODUCTNAME,SKU,TYPENAME,PRICE,NUMBER,OAMOUNT,
			SUPPLYPRICE,MONTHPOLICY,SUPPLYAMOUNT,COMMISSION};
	

	/**	 * 订单列表导出的表头	 */
	/*public static String[] orderListTitle = {ORDERSN,SOURCE,SOURCEORDERSN,ADDTIMESTART,FIRSTTURETIME,
		SURETIME,ORDERAMOUNT,ORDERSTATUS,OID,EXPRESSNO,CANCELSTATUS
	};*/
	/**	 * 网单列表表头	 */
	/*public static String[] productListTitle = {CORDERSN,ORDERSN,PRODUCTNAME,SKU,TYPENAME,PRICE,NUMBER,
		SOURCEORDERSN,SOURCE,CPAYTIME
	};*/
	/**	 * 核算表表头	 */
	/*public static String[] checkListTitle = {INDEX,WANGDAN,YUANWANGDAN,SOURCEORDERSN,SOURCE,SALER,CATEGORY,
		BRAND,SKUCODE,BABYMODEL,CONSIGNEENAME,CPAYTIME,SALENUMBER,TOTALPRICES,PRODUCTSTATUS,PERIOD,YEAR,
		INVOICETIME,INVOICESTATUS,STATUS
	};*/
	
    /**	 * 空调表表头	 */
    public static String[] commission_productListTitle = {CATEGORYID,CATEGORYNAME,CHANNENAME,COMSKU,COMPRODUCTNAME,BRANDID,BRANDNAME,MONTHPOLICY
    };
    public static String[] commission_productdemoTitle = {INDEX,COMSKU,CHANNUBER,REWARD,POLICYYear
    };
    public static String[] commission_invoice1 = {CATEGORY,BRAND,CHANNENAME,YEAR1,INDEX,INDEX,INDEX,YEAR2,INDEX,INDEX,INDEX,YEAR3,INDEX,INDEX,INDEX,YEAR4,INDEX,INDEX,INDEX,YEAR5,INDEX,INDEX,INDEX
    };
    public static String[] commission_invoice2 = {INDEX,INDEX,INDEX,INVOICE1,INVOICE2,INVOICE3,INVOICE4,INVOICE1,INVOICE2,INVOICE3,INVOICE4,INVOICE1,INVOICE2,INVOICE3,INVOICE4,INVOICE1,INVOICE2,INVOICE3,INVOICE4,INVOICE1,INVOICE2,INVOICE3,INVOICE4,
    };
    public static String[] commission_invoice3 = {CATEGORY,BRAND,CHANNENAME,YUE1,INDEX,INDEX,INDEX,YUE2,INDEX,INDEX,INDEX,YUE3,INDEX,INDEX,INDEX,YUE4,INDEX,INDEX,INDEX,YUE5,INDEX,INDEX,INDEX,YUE6,INDEX,INDEX,INDEX,YUE7,INDEX,INDEX,INDEX,YUE8,INDEX,INDEX,INDEX,YUE9,INDEX,INDEX,INDEX,YUE10,INDEX,INDEX,INDEX,YUE11,INDEX,INDEX,INDEX,YUE12,INDEX,INDEX,INDEX,YEAR5,INDEX,INDEX,INDEX
    };
    public static String[] commission_invoice4 = {INDEX,INDEX,INDEX,INVOICE1,INVOICE2,INVOICE3,INVOICE4,INVOICE1,INVOICE2,INVOICE3,INVOICE4,INVOICE1,INVOICE2,INVOICE3,INVOICE4,INVOICE1,INVOICE2,INVOICE3,INVOICE4,INVOICE1,INVOICE2,INVOICE3,INVOICE4,INVOICE1,INVOICE2,INVOICE3,INVOICE4,INVOICE1,INVOICE2,INVOICE3,INVOICE4,INVOICE1,INVOICE2,INVOICE3,INVOICE4,INVOICE1,INVOICE2,INVOICE3,INVOICE4,INVOICE1,INVOICE2,INVOICE3,INVOICE4,INVOICE1,INVOICE2,INVOICE3,INVOICE4,INVOICE1,INVOICE2,INVOICE3,INVOICE4,INVOICE1,INVOICE2,INVOICE3,INVOICE4,
    };

    public static String[] baseStockListTitle = {COMSKU,CATEGORY,STOCKPRODUCTNAME,SECCODE,SECNAME,STOCKSTOCKQTY,STOCKFROZENQTY,CREATETIME,UPDATETIME};
    public static String[] baseStockLogListTitle = {REFNO,COMSKU,PRODUCTNAMES,SECCODE,SECNAME,LOG,CREATETIME};
    public static String[] machineBaseStockListTitle = {COMSKU,CATEGORY,STOCKPRODUCTNAME,SECCODE,SECNAME,AVAIABLEQTY,CREATETIME,UPDATETIME};
    public static String[] storeListTitle = {STORECODE,STORENAME,COMSKU,CATEGORY,STOCKPRODUCTNAME,STORKQTY,ECTIME,CREATETIME};
    public static String[] itemBaseListTitle = {COMSKU,MATERIALS_DESC,MATERIALS_TYPE,PRODUCT_GROUP_NAME,FLOW_FLAG_NAME,MODIFIER,product_type,LENGTH,HEIGHT,WIDTH,WEIGHT,weight_unit,delete_flag,CREATETIME,UPDATETIME,is_auto_update,pro_band};
}