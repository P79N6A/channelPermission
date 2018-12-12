package com.haier.svc.api.controller.util;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/16 0016
 * \* Time: 11:20
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public final class PopExportData {

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
    public static final String PAMOUNT= "网单金额";
    public static final String SHR= "收货人";
    public static final String LXDH= "联系电话";
    public static final String SZD= "所在地";
    public static final String XXXX= "详细信息";
    
    public static final String CATEGORYID = "品类ID";
    public static final String CATEGORYNAME = "品类";
    public static final String COMSKU = "物料编码";
    public static final String COMPRODUCTNAME = "型号名称";
    public static final String BRANDID= "品牌ID";
    public static final String BRANDNAME = "品牌名称";
    public static final String MONTHPOLICY = "基础政策";
    public static final String CHANNENAME ="渠道";

    /**	 * 订单列表导出的表头	 */
    public static String[] orderListTitle = {ORDERSN,SOURCE,SOURCEORDERSN,ADDTIMESTART,FIRSTTURETIME,
            SURETIME,ORDERAMOUNT,ORDERSTATUS,OID,EXPRESSNO,CANCELSTATUS
    };
    /**	 * 网单列表表头	 */
    public static String[] productListTitle = {CORDERSN,ORDERSN,PRODUCTNAME,SKU,TYPENAME,PRICE,NUMBER,PAMOUNT,
            SOURCEORDERSN,SOURCE,CPAYTIME,ORDERSTATUS,SHR,LXDH,SZD,XXXX
    };
    /**	 * 核算表表头	 */
    public static String[] checkListTitle = {INDEX,WANGDAN,YUANWANGDAN,SOURCEORDERSN,SOURCE,SALER,CATEGORY,
            BRAND,SKUCODE,BABYMODEL,CONSIGNEENAME,CPAYTIME,SALENUMBER,TOTALPRICES,PAMOUNT,PRODUCTSTATUS,PERIOD,YEAR,
            INVOICETIME,INVOICESTATUS,STATUS
    };
    /**	 * 空调表表头	 */
    public static String[] commission_productListTitle = {CATEGORYID,CATEGORYNAME,CHANNENAME,COMSKU,COMPRODUCTNAME,BRANDID,BRANDNAME,MONTHPOLICY
    };

    public static String[] saleProductPriceDemoTitle = {"序号","渠道","物料编码","结算价格","销售价格","限制价格","价格适用开始时间","价格适用结束时间"
    };
}