package com.haier.svc.api.controller.util;

/**
 * 常量类
 * 
 * @Filename: Constant.java
 * @Version: 1.0
 * @Author: 刘野
 * @Email: ye.liu@dhc.com.cn
 *
 */
public interface Constant {
    public static interface SYS_CONFIG {
        /** 连接超时时间 单位秒 */
        int    CONNECTION_TIMEOUT = 15 * 1000;

        /** 读取超时时间 单位秒 */
        int    SO_TIMEOUT         = 15 * 1000;

        /** 字符编码格式 **/
        String CHARACTER_SET_UTF  = "UTF-8";
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
        /** 24:区县工贸关系修改 */
        public static Integer LOG_24 = 24;
        /** 25:400工单列表导出 */
        public static Integer LOG_25 = 25;
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
    }
}
