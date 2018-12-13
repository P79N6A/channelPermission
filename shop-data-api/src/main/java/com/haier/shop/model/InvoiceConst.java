package com.haier.shop.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 发票常量
 *
 * @Filename: InvoiceConst.java
 * @Version: 1.0
 * @Author: weiyunjun
 * @Email: weiyunjun@ehaier.com
 */
public class InvoiceConst {

    /**
     * 电子发票----平台纳税人身份标识 (由发票平台提供)  php原值ehaier   测试值testcn   正式ehaier
     */
    public static String SHOPCODE = "ehaier";
    /**
     * 电子发票----开票员
     */
    public static String DRAWER = "海尔商城";
    /**
     * 电子发票----平台编码    测试PT0000014   正式PT000002
     */
    public static String PLATFORMCODE = "PT000002";
    /**
     * 电子发票----sessionID    测试8abc9c962490359c0124949b987b0058   正式8abc9c962490359c0124949b987b0058
     */
    public static String SESSIONID = "8abc9c962490359c0124949b987b0058";
    /**
     * 电子发票----平台版本   原php=V1.0   测试V1.1   正式V1.1   升级为2.0
     */
    public static String VERSION = "V2.0";
    /**
     * 发票相关的wsdl文件存放路径
     */
    public static String WSDL_LOCATION = "/wsdl";

    //成功标记
    public static Integer SUCCESS = 1;
    //失败标记
    public static Integer FAILED = 0;
  //状态类型   对应 successType
    public static Integer                    one_type   =1;
    public static Integer                    four_type =4;
    //对应返回结果名称
    public static Map<Integer, String> RESULTSTATUS = new HashMap<Integer, String>();

    static {
        RESULTSTATUS.put(SUCCESS, "成功");
        RESULTSTATUS.put(FAILED, "失败");
    }

    /**
     * 发票信息
     */
    /*电子发票系统发短信标志，现在设置为商城发短信，电子发票系统暂时不发短信，设置为false，以后要开通电子发票系统发短信可设置为true */
    public static final boolean SMSFLAG = false;
    /*开票类型:推送开票*/
    public static final Integer TYPE_GENERATE = 1;
    /*开票类型:作废/冲红*/
    public static final Integer TYPE_INVALID = 2;
    /*开票类型:查询*/
    public static final Integer TYPE_GET = 3;
    /*开票类型名称*/
    public static final Map<String, String> TYPENAMES = new HashMap<String, String>();

    static {
        TYPENAMES.put(TYPE_GENERATE + "", "推送开票");
        TYPENAMES.put(TYPE_INVALID + "", "作废/冲红");
        TYPENAMES.put(TYPE_GET + "", "查询");
    }

    /*网单类型:普通网单*/
    public static final Integer COMMON_CORDER_TYPE = 1;
    /*网单类型:差异网单*/
    public static final Integer DIFF_CORDER_TYPE = 2;
    /*网单类型:专项开票*/
    public static final Integer SPECIAL_CORDER_TYPE = 3;
    /*网单类型名称*/
    public static final Map<String, String> C_ORDER_TYPE = new HashMap<String, String>();

    static {
        C_ORDER_TYPE.put(COMMON_CORDER_TYPE + "", "普通网单");
        C_ORDER_TYPE.put(DIFF_CORDER_TYPE + "", "差异网单");
        C_ORDER_TYPE.put(SPECIAL_CORDER_TYPE + "", "专项开票");
    }

    /*电子发票切换标志，为true时代表所有普票强制改电子发票*/
    public static final boolean ELECTRIC_SWITCH = true;

    /*开票类型:电子发票*/
    public static final Integer ELECTRIC_INVOICE = 1;
    /*开票类型:纸质发票*/
    public static final Integer NOT_ELECTRIC_INVOICE = 0;
    /*开票类型名称*/
    public static final Map<String, String> ELECTRIC_FLAG = new HashMap<String, String>();

    static {
        ELECTRIC_FLAG.put(ELECTRIC_INVOICE + "", "电子发票");
        ELECTRIC_FLAG.put(NOT_ELECTRIC_INVOICE + "", "纸质发票");
    }

    /**
     * 会员发票
     */
    /*发票类型:增值税发票*/
    public static final Integer VAT_INVOICE = 1;
    /*发票类型:普通发票*/
    public static final Integer GENERAL_INVOICE = 2;
    /*发票类型名称*/
    public static final Map<String, String> INVOICE_TYPE = new HashMap<String, String>();

    static {
        INVOICE_TYPE.put(VAT_INVOICE + "", "增值税发票");
        INVOICE_TYPE.put(GENERAL_INVOICE + "", "普通发票");
    }

    /**
     * 发票日志
     */
    /*开票类型:首次推送开票*/
    public static final Integer ADD_KIND = 1;
    /*开票类型:修改发票信息*/
    public static final Integer EDIT_KIND = 2;
    /*开票类型:取消开票*/
    public static final Integer CANCEL_KIND = 3;
    /*开票类型:作废发票*/
    public static final Integer INVALID_KIND = 4;
    /*开票类型:商城接收EAI的推送的发票信息*/
    public static final Integer RECEIVE_KIND = 5;
    /*开票类型名称*/
    public static final Map<String, String> KIND_NAMES = new HashMap<String, String>();

    static {
        KIND_NAMES.put(ADD_KIND + "", "首次推送开票");
        KIND_NAMES.put(EDIT_KIND + "", "修改发票信息");
        KIND_NAMES.put(CANCEL_KIND + "", "取消开票");
        KIND_NAMES.put(INVALID_KIND + "", "作废发票");
        KIND_NAMES.put(RECEIVE_KIND + "", "商城接收EAI的推送的发票信息");
    }

    /**
     * 写LOG时，日志类型：1.取消，2.编辑
     */
    /*日志类型：取消*/
    public static Integer INVOICE_API_LOG_CANCEL_TYPE = 1;
    /*日志类型：编辑*/
    public static Integer INVOICE_API_LOG_EDIT_TYPE = 2;

    /**
     * 网单属性
     */
    /*开票类型：库房开票*/
    public static Integer MR_TYPE_HOUSE = 1;
    /*开票类型：共享开票*/
    public static Integer MR_TYPE_SHARE = 2;
    /*开票类型名称*/
    public static final Map<Integer, String> MAKE_RECEIPT_TYPE_NAMES = new HashMap<Integer, String>();

    static {
        MAKE_RECEIPT_TYPE_NAMES.put(MR_TYPE_HOUSE , "库房开票");
        MAKE_RECEIPT_TYPE_NAMES.put(MR_TYPE_SHARE , "共享开票");
    }

    /**
     * 网单开票状态选项
     */
    public static Integer MR_STATE_UNMAKE = 1;                                 // 未开票no use
    public static Integer MR_STATE_MAKED = 2;                                 // 已开票
    public static Integer MR_STATE_CANCEL = 10;                                // 取消开票
    public static Integer MR_STATE_RED = 4;                                 // 跨月冲红
    public static Integer MR_STATE_NULLIFY = 3;                                 // 当月作废
    public static Integer MR_STATE_NEEDMAKE = 9;                                 // 待开票
    public static Integer MR_STATE_MAKING = 5;                                 // 开票中 只商城用
    public static Integer MR_STATE_FAILD = 6;                                 // 开票失败no use
    public static Integer MR_STATE_CLOSE = 20;                                // SAP期初封存
    public static final Map<String, String> MAKE_RECEIPT_STATE_OPTIONS = new HashMap<String, String>();

    static {
        MAKE_RECEIPT_STATE_OPTIONS.put(MR_STATE_UNMAKE + "", "未开票");
        MAKE_RECEIPT_STATE_OPTIONS.put(MR_STATE_NEEDMAKE + "", "待开票");
        MAKE_RECEIPT_STATE_OPTIONS.put(MR_STATE_MAKING + "", "开票中");
        MAKE_RECEIPT_STATE_OPTIONS.put(MR_STATE_FAILD + "", "开票失败");
        MAKE_RECEIPT_STATE_OPTIONS.put(MR_STATE_MAKED + "", "已开票");
        MAKE_RECEIPT_STATE_OPTIONS.put(MR_STATE_NULLIFY + "", "作废发票");
        MAKE_RECEIPT_STATE_OPTIONS.put(MR_STATE_RED + "", "冲红发票");
        MAKE_RECEIPT_STATE_OPTIONS.put(MR_STATE_CANCEL + "", "取消开票");
        MAKE_RECEIPT_STATE_OPTIONS.put(MR_STATE_CLOSE + "", "期初数据封存");
    }

    /**
     * 差异订单
     */
    //开票状态
    public static Integer R_UNRECEIPTED = 1;                                 //未开票
    public static Integer R_RECEIPTED = 2;                                 //已开票
    public static Integer R_CANCEL = 10;                                //取消开票
    public static Integer R_RED = 4;                                 //跨月冲红
    public static Integer R_NULLIFY = 3;                                 //当月作废
    public static Integer R_RECEIPTING = 5;                                 //开票中 只商城用
    public static Integer R_FAILRECEIPTED = 6;                                 //开票失败no use

    /**
     * 发票状态Invoices - > status
     */
    /*
    public static Integer                    INVOICE_STATUS_WAIT_STATUS     = 0;
    public static Integer                    INVOICE_STATUS_IN_STATUS       = 1;
    public static Integer                    INVOICE_STATUS_COMPLETE_STATUS = 4;
    public static Integer                    INVOICE_STATUS_CANCEL_STATUS   = 5;
    public static final Map<Integer, String> INVOICE_STATUS_TYPES           = new HashMap<Integer, String>();
    static {
     INVOICE_STATUS_TYPES.put(INVOICE_STATUS_WAIT_STATUS, "待开票");
     INVOICE_STATUS_TYPES.put(INVOICE_STATUS_IN_STATUS, "开票中");
     INVOICE_STATUS_TYPES.put(INVOICE_STATUS_COMPLETE_STATUS, "已开票");
     INVOICE_STATUS_TYPES.put(INVOICE_STATUS_CANCEL_STATUS, "已取消开票");
    }*/

    /**
     * 发票状态 针对state字段     Invoices - > state
     */
    public static Integer WAIT_STATE = 0;                                 //待开票
    public static Integer IN_STATE = 1;                                 //开票中,商城初次开票推送给EAI的值也为此值
    public static Integer COMPLETE_STATE = 4;                                 //已开票,金税开完税后，回写EAI,EAI回写商城这边的值也为此值
    public static Integer CANCEL_STATE = 5;                                 //已取消开票
    public static final Map<Integer, String> INVOICE_STATE = new HashMap<Integer, String>();

    static {
        INVOICE_STATE.put(WAIT_STATE, "待开票");
        INVOICE_STATE.put(IN_STATE, "开票中");
        INVOICE_STATE.put(COMPLETE_STATE, "已开票");
        INVOICE_STATE.put(CANCEL_STATE, "已取消开票");
    }

    /**
     * 退换货开票状态
     */
    public static Integer RS_MAKEED = 1;
    public static Integer RS_UNMAKE = 2;
    public static Integer RS_WAIT_CANCEL = 3;
    public static Integer RS_CANCELED = 4;
    public static Integer RS_WAIT_RECALL = 10;
    public static final Map<Integer, String> RECEIPT_STATUS = new HashMap<Integer, String>();

    static {
        RECEIPT_STATUS.put(RS_MAKEED, "已开票");
        RECEIPT_STATUS.put(RS_UNMAKE, "未开票");
        RECEIPT_STATUS.put(RS_WAIT_RECALL, "待召回");
        RECEIPT_STATUS.put(RS_WAIT_CANCEL, "已召回");
        RECEIPT_STATUS.put(RS_CANCELED, "已冲票");
    }

    /**
     * 开票状态 针对eaiWriteState字段
     */
    public static String NORMAL_KP_STATE = "";                                //空为正常
    public static String INVALID_KP_STATE = "3";                               //当月作废，由EAI回写商城，商城这边无权私自修改
    public static String MONTH_INVALID_KP_STATE = "4";                               //跨月冲红，也是作废发票中的一种
    public static final Map<String, String> EAI_WRITE_STATE = new HashMap<String, String>();

    static {
        EAI_WRITE_STATE.put(NORMAL_KP_STATE, "正常");
        EAI_WRITE_STATE.put(INVALID_KP_STATE, "当月作废");
        EAI_WRITE_STATE.put(MONTH_INVALID_KP_STATE, "跨月冲红");
    }

    /**
     * 网单拆单
     */
    public static Integer SPLIT_NOT = 0;                                 //未拆单
    public static Integer SPLIT_OLD = 1;                                 //拆单后旧单
    public static Integer SPLIT_NEW = 2;                                 //拆单后新单
    public static final Map<Integer, String> SPLIT_FLAG = new HashMap<Integer, String>();

    static {
        SPLIT_FLAG.put(SPLIT_NOT, "未拆单");
        SPLIT_FLAG.put(SPLIT_OLD, "拆单后旧单");
        SPLIT_FLAG.put(SPLIT_NEW, "拆单后新单");
    }

    /**
     * SAP 开票类型
     */
    public static Integer SAP_TYPE_NORMAL = 1;                                 //普通开票
    public static Integer SAP_TYPE_INVALID = 2;                                 //作废/冲红
    public static Integer SAP_TYPE_VOUCHER = 3;                                 //二次开票
    public static final Map<Integer, String> SAP_PUSH_TYPE = new HashMap<Integer, String>();

    static {
        SAP_PUSH_TYPE.put(SAP_TYPE_NORMAL, "普通开票");
        SAP_PUSH_TYPE.put(SAP_TYPE_INVALID, "作废/冲红");
        SAP_PUSH_TYPE.put(SAP_TYPE_VOUCHER, "二次开票");
    }
}
