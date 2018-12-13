package com.haier.shop.util;

public interface ReviewConstants {

    static final String SERVICE_RESULT_CODE_SYSERROR        = "service_result_code_syserror";
    static final String SERVICE_RESULT_CODE_PARAMETER_ERROR = "service_result_code_parameter_error";

    /** 工单状态  */
    public static interface WORKSTATUS {
        /** 未处理 */
        public static String UNTREATED    = "0";
        /** 已确认 */
        public static String CONFIRMED    = "1";
        /** 中间结果 */
        public static String MIDDLERESULT = "2";
        /** 最终结果 */
        public static String FINALRESULT  = "3";
    }
    /** 工单插入到SQM状态  */
    public static interface SQM_STATUS {
        /** 等待发送给SQM */
        public static String WAITING    = "0";
        /** 已发送SQM，等待插入结果 */
        public static String SENDING    = "1";
        /** 插入SQM成功 */
        public static String COMPLETE = "2";
        /** 已保存到SQM，且判定通过 */
        String JUDGE_PASS = "PDPASS";
        /** 已保存到SQM，且判定不通过 */
        String JUDGE_NOT_PASS = "PDNOTPASS";
        /** 判定结果已提交到SQM */
        String JUDGE_SENT = "PDSENT";
    }
    /** 工单在SQM中的类型  */
    public static interface SQM_TYPE {
        /** 咨询 */
        String  CONSULT   = "ZX";
        /** 投诉 */
        String COMPLAIN    = "TS";
    }
    /** 判定结果类型  */
    public static interface JUDGE_STATUS {

        String  PASS   = "0";

        String NOT_PASS    = "1";
        //判定结果已发送到SQM
        String SENT_SQM    = "2";
    }

    /** 责任位1  */
    public static interface QUESTION1LEVEL1 {
        /** 物流类 */
        public static String DUTY_1_1 = "物流类";
        /** 发票类 */
        public static String DUTY_1_2 = "发票类";
        /** 退款类 */
        public static String DUTY_1_3 = "退款类";
        /** 缺货类 */
        public static String DUTY_1_4 = "缺货类";
        /** 活动类 */
        public static String DUTY_1_5 = "活动类";
        /** 支付类 */
        public static String DUTY_1_6 = "支付类 ";
        /** 用户体验类 */
        public static String DUTY_1_7 = "用户体验类";
        /** 用户体验类 */
        public static String DUTY_1_8 = "用户体验类";
        /** 售后类 */
        public static String DUTY_1_9 = "售后类";
        /** 社会化业务 */
        public static String DUTY_2_0 = "社会化业务";
        /** 退换机 */
        public static String DUTY_2_1 = "退换机";
        /** OTO类 */
        public static String DUTY_2_2 = "OTO类";
        /** 小电库类 */
        public static String DUTY_2_3 = "小电库类";
        /** 服务类 */
        public static String DUTY_2_4 = "服务类";
        /** 送装类 */
        public static String DUTY_2_5 = "送装类";
    }

    /** 责任位2  */
    public static interface QUESTION1LEVEL2 {
        /** 物流类-退换货 */
        public static String DUTY_2_1 = "退换货";
        /** 物流类-虚假签收 */
        public static String DUTY_2_2 = "虚假签收";
        /** 物流类-送错货 */
        public static String DUTY_2_3 = "送错货";
        /** 物流类-送错货 */
        public static String DUTY_2_4 = "超时免单";
    }

    /** 订单关闭原因  */
    public static interface CLOSETYPE {
        /** 正常关闭 */
        public static String CLOSETYPE_1 = "1";
        /** 强制关闭 */
        public static String CLOSETYPE_2 = "2";
        /** 虚假封单 */
        public static String CLOSETYPE_3 = "3";
        /** 客服原因 */
        public static String CLOSETYPE_4 = "4";
    }

    /** 网单状态  */
    public static interface STATUS {
        /** 完成关闭 */
        public static String COMPLETED_CLOSE = "130";
        /** 取消关闭 */
        public static String CANCEL_CLOSE    = "110";
    }

    /** 订单状态  */
    public static interface ORDERSTATUS {
        /** 订单完成 */
        public static String OS_COMPLETE = "203";
    }

    /** 订单来源  */
    public static interface REMARK5 {
        /** 商城订单 */
        public static String REMARK5_1 = "商城订单";
    }

    /**时间常量*/
    public static interface TIME {
        /** 时间字符串 */
        public static String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";
    }

    /**短信模板类型*/
    public static interface SMSTYPE {

        /** 物流类类型 */
        public static String TYPE_1 = "(物流类)";
        /** 客服距离第一次反馈大于2小时 */
        public static String TYPE_2 = "(新增反馈)";
        /** 责任人大于2小时还未处理 */
        public static String TYPE_3 = "(上诉)";
        /** 责任人大于48小时还未处理中间结果 */
        public static String TYPE_4 = "(中间结果上诉)";
        /** 虚假封单 */
        public static String TYPE_5 = "(虚假封单)";
        /** 咨询次数提醒 */
        public static String TYPE_6 = "(咨询次数提醒)";
        /** 发票类类型 */
        String TYPE_7 = "(发票类)";
        /** 售后类类型 */
        String TYPE_8 = "(售后类)";
    }

    /**1:人员添加 2:人员修改 3:人员删除 4:人员责任位配置添加 5:人员信息修改 6:人员责任位配置删除 
     * 7:责任位添加 8:责任位修改 9:责任位删除 10:工单添加 11:工单修改 12:反馈内容添加 
     * 13:中间结果添加 14:最终结果添加 15:更改责任位 16:工单导出 17:责任位统计导出18:人员统计导出 
     * */
    public static interface LOG {
        /** 1:人员添加  */
        public static Integer LOG_1  = 1;
        /** 2:人员修改 */
        public static Integer LOG_2  = 2;
        /** 3人员删除 */
        public static Integer LOG_3  = 3;
        /** 4:人员责任位配置添加 */
        public static Integer LOG_4  = 4;
        /** 5:人员信息修改 */
        public static Integer LOG_5  = 5;
        /** 6:人员责任位配置删除  */
        public static Integer LOG_6  = 6;
        /** 7:责任位添加 */
        public static Integer LOG_7  = 7;
        /** 8:责任位修改  */
        public static Integer LOG_8  = 8;
        /** 9:责任位删除 */
        public static Integer LOG_9  = 9;
        /** 10:工单添加 */
        public static Integer LOG_10 = 10;
        /** 11:工单修改 */
        public static Integer LOG_11 = 11;
        /** 12:反馈内容添加 */
        public static Integer LOG_12 = 12;
        /** 13:中间结果添加 */
        public static Integer LOG_13 = 13;
        /** 14:最终结果添加 */
        public static Integer LOG_14 = 14;
        /** 15:更改责任位 */
        public static Integer LOG_15 = 15;
        /** 16:工单导出 */
        public static Integer LOG_16 = 16;
        /** 17:责任位统计导出 */
        public static Integer LOG_17 = 17;
        /** 18:人员统计导出 */
        public static Integer LOG_18 = 18;
        /** 19:人员信息管理导出 */
        public static Integer LOG_19 = 19;
        /** 20:人员责任配置导出 */
        public static Integer LOG_20 = 20;
        /** 21:订单来源管理添加 */
        public static Integer LOG_21 = 21;
        /** 22:订单来源管理修改 */
        public static Integer LOG_22 = 22;
        /** 23:区县添加 */
        public static Integer LOG_23 = 23;
        /** 22:区县工贸关系修改 */
        public static Integer LOG_24 = 24;

    }

    /**模块名称
     * 1：人员信息管理  2：人员责任位配置 3：工单'
     * */
    public static interface MKNAME {

        /** 人员信息管理*/
        public static String MKNAME_1 = "1";
        /** 人员责任位配置 */
        public static String MKNAME_2 = "2";
        /** 工单*/
        public static String MKNAME_3 = "3";
        /** 订单来源管理 */
        public static String MKNAME_4 = "4";
        /** 区县工贸管理 */
        public static String MKNAME_5 = "5";
    }

    /**
     * 系统常量
     *
     */
    public static interface SYS {

        /** 系统自动处理人 */
        public static String SYSTEM = "[系统]";
    }

    /** 信息指定发送的指定人 */
    public static interface SEND_PEOPLE {
        /** 责任人 */
        public static Integer DUTY_PEOPLE        = 0;
        /** 一级领导 */
        public static Integer MANAGERUSER_1      = 1;
        /** 一级领导与二级领导 */
        public static Integer MANAGERUSER_1_OR_2 = 2;

    }

    /** 字典表value_set_id 类别值 */
    public static interface VALUE_SET_ID {
        /** 订单来源 */
        public static String ORDER_SOURCE = "order_source";
    }

    /** 订单来源管理是否启用 */
    public static interface IS_FLG {
        /** 启用 */
        public static Short OPEN  = 0;
        /** 不启用 */
        public static Short CLOSE = 1;
    }

    /** 订单来源管理是否启用对应字符 */
    public static interface IS_FLG_STR {
        /** 启用 */
        public static String OPEN  = "启用";
        /** 不启用 */
        public static String CLOSE = "不启用";
    }

    /** 物流模式 */
    public static interface SHIPPINGMODE {
        /** B2C */
        public static String B2C = "B2C";
        /** B2B */
        public static String B2B = "B2B";
    }
}
