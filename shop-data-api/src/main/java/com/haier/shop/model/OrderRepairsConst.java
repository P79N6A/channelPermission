package com.haier.shop.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 *                       
 * @Filename: SalesReturnConst.java
 * @Version: 1.0
 * @Author: SALLY LOU
 * @Email: loujing@ehaier.com
 *
 */
public class OrderRepairsConst {

    public static final int QUALITY_GOOD   = 1;
    public static final int QUALITY_OPENED = 2;
    public static final int QUALITY_BAD    = 3;
    public static final int QUALITY_BADCHANGE = 5;
    public static final int QUALITY_BADRETREAT = 6;

    public static Map<Integer, String> QUALITYMAP = new HashMap<Integer, String>();

    static {
        QUALITYMAP.put(QUALITY_GOOD, "未开箱正品");
        QUALITYMAP.put(QUALITY_OPENED, "已开箱正品");
        QUALITYMAP.put(QUALITY_BAD, "不良品");
        QUALITYMAP.put(QUALITY_BADCHANGE, "不良品换机");
        QUALITYMAP.put(QUALITY_BADRETREAT, "不良品退机");
    }

    public static final int CHECKRESULT_CONFORM   = 1;
    public static final int CHECKRESULT_UNCONFORM = 2;

    public static Map<Integer, String> CHECKRESULTMAP = new HashMap<Integer, String>();

    static {
        CHECKRESULTMAP.put(CHECKRESULT_CONFORM, "符合");
        CHECKRESULTMAP.put(CHECKRESULT_UNCONFORM, "不符合");
    }

    public static final int CHECKTYPE_FIRST  = 1;
    public static final int CHECKTYPE_SECOND = 2;
    public static final int CHECKTYPE_THIRD  = 3;

    public static Map<Integer, String> CHECKTYPEMAP = new HashMap<Integer, String>();

    static {
        CHECKTYPEMAP.put(CHECKTYPE_FIRST, "一次鉴定");
        CHECKTYPEMAP.put(CHECKTYPE_SECOND, "二次鉴定");
        CHECKTYPEMAP.put(CHECKTYPE_THIRD, "换箱");
    }

    public static final int            PACKRESULT_PACKED = 1;
    public static final int            PACKRESULT_BAD    = 2;
    public static final int            PACKRESULT_UNPACK = 3;
    public static Map<Integer, String> PACKRESULTMAP     = new HashMap<Integer, String>();

    static {
        PACKRESULTMAP.put(PACKRESULT_PACKED, "机器完好换好包装箱");
        PACKRESULTMAP.put(PACKRESULT_BAD, "非正品需买单");
        PACKRESULTMAP.put(PACKRESULT_UNPACK, "机器完好无包装箱可换");
    }

    public static final int            PACKRESULT_2_GOOD         = 1;
    public static final int            PACKRESULT_2_BAD_WULIU    = 2;
    public static final int            PACKRESULT_2_BAD_WANGDIAN = 3;
    public static Map<Integer, String> PACKRESULTMAP_2MAP        = new HashMap<Integer, String>();

    static {
        PACKRESULTMAP_2MAP.put(PACKRESULT_2_GOOD, "开箱正品需要换箱或整新");
        PACKRESULTMAP_2MAP.put(PACKRESULT_2_BAD_WULIU, "不良品需要买单:包装箱破损物流买单");
        PACKRESULTMAP_2MAP.put(PACKRESULT_2_BAD_WANGDIAN, "不良品需要买单:包装箱完好一次鉴定网点买单");
    }

    public static final int            PACKRESULT_3_IN10           = 1;
    public static final int            PACKRESULT_3_IN40           = 2;
    public static final int            PACKRESULT_3_IN10_DONOTNEED = 3;
    public static Map<Integer, String> PACKRESULT_3MAP             = new HashMap<Integer, String>();

    static {
        PACKRESULT_3MAP.put(PACKRESULT_3_IN10, "换箱完成");
        PACKRESULT_3MAP.put(PACKRESULT_3_IN40, "整新完成");
        PACKRESULT_3MAP.put(PACKRESULT_3_IN10_DONOTNEED, "无需换箱");

    }

    public static final int            RESPONSE_NETPOINT  = 1;
    public static final int            RESPONSE_TRANSPORT = 2;
    public static Map<Integer, String> RESPONSEMAP        = new HashMap<Integer, String>();

    static {
        RESPONSEMAP.put(RESPONSE_NETPOINT, "网点");
        RESPONSEMAP.put(RESPONSE_TRANSPORT, "物流");
    }

    public static final int            HPORDER_SUCCESS    = 0;
    public static final int            HPORDER_FAIL       = 1;
    public static final int            HPORDER_UNCHECK    = 2;
    public static final int            HPORDER_GENSUCCESS = 3;
    public static Map<Integer, String> HPORDERMAP         = new HashMap<Integer, String>();

    static {
        HPORDERMAP.put(HPORDER_SUCCESS, "鉴定结单");
        HPORDERMAP.put(HPORDER_FAIL, "工单生成不成功");
        HPORDERMAP.put(HPORDER_UNCHECK, "非鉴定单结单");
        HPORDERMAP.put(HPORDER_GENSUCCESS, "工单生成成功");
    }

    /****************************************OrderRepairLESRecords**********************************************/
    public static final int STORAGE_QUALITY_GOOD   = 10;
    public static final int STORAGE_QUALITY_OPENED = 22;
    public static final int STORAGE_QUALITY_BAD    = 21;

    public static final int OPERATE_PUTIN_DONOTINSPECT = 11;
    public static final int OPERATE_PUTIN_INSPECT      = 12;
    public static final int OPERATE_CHANGEOUT          = 21;
    public static final int OPERATE_CHANGEIN           = 13;

    public static final Map<Integer, String> OPERATEMAP = new HashMap<Integer, String>();

    static {
        OPERATEMAP.put(OPERATE_PUTIN_DONOTINSPECT, "入库-网点不检验");
        OPERATEMAP.put(OPERATE_PUTIN_INSPECT, "入库-网点检验");
        OPERATEMAP.put(OPERATE_CHANGEOUT, "存性变更出库");
        OPERATEMAP.put(OPERATE_CHANGEIN, "存性变更入库");
    }

    public static final int                  SUCCESS_WAITPUSH = 0;
    public static final int                  SUCCESS_PUSHED   = 1;
    public static final int                  SUCCESS_CANCEL   = 2;
    public static final Map<Integer, String> SUCCESSTIDANMAP  = new HashMap<Integer, String>();

    static {
        SUCCESSTIDANMAP.put(SUCCESS_WAITPUSH, "未开提单");
        SUCCESSTIDANMAP.put(SUCCESS_PUSHED, "已开提单");
        SUCCESSTIDANMAP.put(SUCCESS_CANCEL, "提单已取消");

    }

    /*******************************************OrderRepairs**************************************************/
    public static final boolean JDKU_FLOW_SWITCH = true;  // 基地库直发流程闸口，true=使用；false=不使用
    public static final boolean YBK_FLOW_SWITCH  = false; //延保卡流程闸口，true=使用；false=不使用

    public static final int            PS_PAID            = 1;
    public static final int            PS_REFUNDING       = 2;
    public static final int            PS_REFUNDED        = 3;
    public static final int            PS_REFUNDEDOFFLINE = 4;
    public static final int            PS_DONOTREFUND     = 5;
    public static Map<Integer, String> PSMAP              = new HashMap<Integer, String>();

    static {
        PSMAP.put(PS_PAID, "已付款");
        PSMAP.put(PS_REFUNDING, "待退款");
        PSMAP.put(PS_REFUNDED, "已退款");
        PSMAP.put(PS_REFUNDEDOFFLINE, "线下已退款");
        PSMAP.put(PS_DONOTREFUND, "无需退款");
    }

    public static final int            RS_MAKEED      = 1;
    public static final int            RS_UNMAKE      = 2;
    public static final int            RS_WAIT_CANCEL = 3;
    public static final int            RS_CANCELED    = 4;
    public static final int            RS_WAIT_RECALL = 10;
    public static Map<Integer, String> RSMAP          = new HashMap<Integer, String>();

    static {
        RSMAP.put(RS_MAKEED, "已开票");
        RSMAP.put(RS_UNMAKE, "未开票");
        RSMAP.put(RS_WAIT_RECALL, "待召回");
        RSMAP.put(RS_WAIT_CANCEL, "已召回");
        RSMAP.put(RS_CANCELED, "已冲票");
    }

    public static final int            SS_STOCKOUT    = 1;
    public static final int            SS_NOTSTOCKOUT = 2;
    public static final int            SS_WAIT_IN     = 3;
    public static final int            SS_STOCKIN     = 4;
    public static final int            SS_WAIT_RECALL = 10;
    public static final int            SS_IN22        = 122;
    public static final int            SS_IN21        = 121;
    public static final int            SS_IN10        = 110;
    public static final int            SS_IN40        = 140;
    public static final int            SS_IN41        = 141;
    public static final int            SS_INRRS21     = 221;
    public static final int            SS_INJL21      = 21;
    public static Map<Integer, String> SSMAP          = new HashMap<Integer, String>();

    static {
        SSMAP.put(SS_STOCKOUT, "已出库");
        SSMAP.put(SS_NOTSTOCKOUT, "未出库");
        SSMAP.put(SS_WAIT_RECALL, "待召回");
        SSMAP.put(SS_WAIT_IN, "已召回");
        SSMAP.put(SS_STOCKIN, "已入库");
        SSMAP.put(SS_IN22, "已入22库");
        SSMAP.put(SS_IN21, "已入21库");
        SSMAP.put(SS_IN10, "已入10库");
        SSMAP.put(SS_IN40, "已入40库");
        SSMAP.put(SS_IN41, "已入41库");
        SSMAP.put(SS_INRRS21, "已入日日顺21库");
        SSMAP.put(SS_INJL21, "已入金立库");
    }

    public static final int            HS_REVIEW          = 1;
    public static final int            HS_HANDLE          = 2;
    public static final int            HS_CONFIRM         = 3;
    public static final int            HS_CLOSE           = 4;
    public static final int            HS_CANCEL          = 5;
    public static final int            HS_STOP            = 6;
    public static final int            HS_REFUNDEDOFFLINE = 7;
    public static final int            HS_WAITSTOP        = 8;
    public static Map<Integer, String> HANDLESTATUSMAP    = new HashMap<Integer, String>();

    static {
        HANDLESTATUSMAP.put(HS_REVIEW, "审核中");
        HANDLESTATUSMAP.put(HS_HANDLE, "进行中");
        HANDLESTATUSMAP.put(HS_CONFIRM, "受理完成");
        HANDLESTATUSMAP.put(HS_CLOSE, "已完结");
        HANDLESTATUSMAP.put(HS_CANCEL, "已驳回");
        HANDLESTATUSMAP.put(HS_STOP, "已终止");
        HANDLESTATUSMAP.put(HS_REFUNDEDOFFLINE, "线下已退款");
        HANDLESTATUSMAP.put(HS_WAITSTOP, "等待确认终止");
    }

    public static Map<String, String> OPERATETEXTMAP = new HashMap<String, String>();

    static {
        OPERATETEXTMAP.put("ps_wait", "等待退款");
        OPERATETEXTMAP.put("ps_refund", "退款完成");
        OPERATETEXTMAP.put("rs_wait", "等待发票召回");
        OPERATETEXTMAP.put("rs_recall", "发票召回完成");
        OPERATETEXTMAP.put("rs_cancel", "冲票完成");
        OPERATETEXTMAP.put("ss_wait", "等待货物召回");
        OPERATETEXTMAP.put("ss_recall", "货物召回完成");
        OPERATETEXTMAP.put("ss_stockin", "入库完成");
        OPERATETEXTMAP.put("hs_begin", "审核通过");
        OPERATETEXTMAP.put("hs_cancel", "审核不通过");
        OPERATETEXTMAP.put("hs_end", "受理完成");
        OPERATETEXTMAP.put("hs_stop", "终止申请");
        OPERATETEXTMAP.put("hs_confirmstop", "确认终止");
        OPERATETEXTMAP.put("登记", "登记");
        OPERATETEXTMAP.put("edit", "修改信息");
        OPERATETEXTMAP.put("auto_audit", "自动审核");
        OPERATETEXTMAP.put("hp_send", "提交HP工单");
        OPERATETEXTMAP.put("hp_return", "HP回传检验结果");
        OPERATETEXTMAP.put("les_return", "LES回传检验结果");
        OPERATETEXTMAP.put("ss_changeout22", "存性变更出22库");
        OPERATETEXTMAP.put("ss_changein10", "存性变更入10库");
        OPERATETEXTMAP.put("ss_changein21", "存性变更入21库");
        OPERATETEXTMAP.put("ss_changein40", "存性变更入40库");
        OPERATETEXTMAP.put("ss_changein41", "存性变更入41库");
        OPERATETEXTMAP.put("ss_directin10", "未发网点入10库");
        OPERATETEXTMAP.put("ss_directin22", "未发网点入22库");
        OPERATETEXTMAP.put("ss_lesretry", "重推LES");
        OPERATETEXTMAP.put("ss_lescancel", "取消LES入库");
        OPERATETEXTMAP.put("ss_vomcancel", "取消VOM入库");
        OPERATETEXTMAP.put("ss_inspect22", "网点质检入22库");

    }

    public static final int            OFFLINEFLAG_YES = 1;
    public static final int            OFFLINEFLAG_NO  = 2;
    public static Map<Integer, String> OFFLINEFLAGMAP  = new HashMap<Integer, String>();

    static {
        OFFLINEFLAGMAP.put(OFFLINEFLAG_YES, "是");
        OFFLINEFLAGMAP.put(OFFLINEFLAG_NO, "否");
    }

    /**
     * typeActual 实际处理方式（预留字段）
     */
    public static final int            TYPE_RETURN   = 1;
    public static final int            TYPE_CHANGE   = 2;
    public static Map<Integer, String> TYPEACTUALMAP = new HashMap<Integer, String>();

    static {
        TYPEACTUALMAP.put(TYPE_RETURN, "退货退款");
        TYPEACTUALMAP.put(TYPE_CHANGE, "退货不退款");
    }

    public static Map<String, Integer> reasonTypeMap = new HashMap<String, Integer>();

    static {
        reasonTypeMap.put("价格问题", 1);
        reasonTypeMap.put("大小尺寸", 2);
        reasonTypeMap.put("颜色款式", 3);
        reasonTypeMap.put("质量问题", 4);
        reasonTypeMap.put("安装问题", 5);
        reasonTypeMap.put("发票问题", 6);
        reasonTypeMap.put("配送问题", 7);
        reasonTypeMap.put("库存问题", 8);
        reasonTypeMap.put("地址问题", 9);
        reasonTypeMap.put("七天无理由", 10);
        reasonTypeMap.put("其他", 11);
    }

    /**
    * 退货单类型
    */

    public static final int TYPE_NORMAL = 0; // 普通
    public static final int TYPE_JDK    = 1; // 基地库
    public static final int TYPE_YBK    = 2; // 延保卡

    /**
     * 商品表-商品退货检验方式
     */
    public static final int IT_NORMAL       = 1; // 网点上门质检
    public static final int IT_APPOINT      = 2; // 生活家电指定网点质检
    public static final int IT_DONOTINSPECT = 3; // 网点不质检

}
