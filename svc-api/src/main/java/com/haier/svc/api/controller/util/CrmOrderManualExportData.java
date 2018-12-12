package com.haier.svc.api.controller.util;

/**
 * Created by 李超 on 2018/3/15.
 */
public class CrmOrderManualExportData {

    public static final String WP_ORDER_ID="订单号";
    public static final String SOURCE_ORDER_ID="来源单号";
    public static final String PO_ID="PO号";
    public static final String SO_ID="SO号";
    public static final String DN_ID="DN号";
    public static final String ED_CHANNEL_NAME="渠道";
    public static final String FLOW_FLAG_NAME="状态";
    public static final String CORPNAME="分公司";
    public static final String INDUSTRY_TRADE_DESC="工贸";
    public static final String BILLTYPENAME="订单类型";
    public static final String SAP_FLOW_NUM_NAME="配送方式";
    public static final String PAYMENT_NAME="付款方";
    public static final String ESALE_NAME="送达方";
    public static final String ESTORGE_ID="仓库编码";
    public static final String ESTORGE_NAME="仓库名称";
    public static final String WHCODE="日日顺库位码";
    public static final String CUSTMGR="客户经理";
    public static final String PORMGR="产品经理";
    public static final String PRODPUTY="产品代表";
    public static final String ISKPONAME="是否商用空调";
    public static final String PLANINDATE_DISPLAY="预计到货日期";
    public static final String BUDGETORGNAME="预算体";
    public static final String GGG="预算种类";
    public static final String CORPNAME2="销售组织";
    public static final String MAKER="开单人";
    public static final String RELEBILLCODE="关联单号";
    public static final String RRS_DISTRIBUTION_NAME="配送中心";
    public static final String MATERIALS_ID="物料编号";
    public static final String MATERIALS_DESC="型号";
    public static final String BRAND_NAME="品牌";
    public static final String PRODUCT_GROUP_NAME="产品组";
    public static final String INVSTATE="批次";
    public static final String SUMMONEY="价税合计";
    public static final String QTY="数量";
    public static final String UNITPRICE="开票价格";
    public static final String RETAILPRICE="供价";
    public static final String ACTPRICE="执行价";
    public static final String BATERATE="直扣";
    public static final String BATEMONEY="台返金额";
    public static final String LOSSRATE="折扣";
    public static final String ISFLNAME="是否返利";
    public static final String BILL_TIME_DISPLAY="提交时间";
    public static final String RRS_OUT_TIME_DISPLAY="出日日顺库时间";
    public static final String WA_IN_TIME_DISPLAY="入WA库时间";
    public static final String ERROR_MSG="备注";




    public static String[] CrmOrderManualListQuery ={
            WP_ORDER_ID,SOURCE_ORDER_ID,PO_ID,SO_ID,DN_ID,ED_CHANNEL_NAME,FLOW_FLAG_NAME,CORPNAME,
            INDUSTRY_TRADE_DESC,BILLTYPENAME,SAP_FLOW_NUM_NAME,PAYMENT_NAME,ESALE_NAME,ESTORGE_ID,
            ESTORGE_NAME,WHCODE,CUSTMGR,PORMGR,PRODPUTY,ISKPONAME,PLANINDATE_DISPLAY,BUDGETORGNAME,
            GGG,CORPNAME2,MAKER,RELEBILLCODE,RRS_DISTRIBUTION_NAME,MATERIALS_ID,MATERIALS_DESC,BRAND_NAME,
            PRODUCT_GROUP_NAME,INVSTATE,SUMMONEY,QTY,UNITPRICE,RETAILPRICE,ACTPRICE,BATERATE,BATEMONEY,
            LOSSRATE,ISFLNAME,BILL_TIME_DISPLAY,RRS_OUT_TIME_DISPLAY,WA_IN_TIME_DISPLAY,ERROR_MSG
    };

}
