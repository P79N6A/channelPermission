package com.haier.stock.util;

public class Constants {
    /**
     * // 所有渠道 0
     */
    public static final int    CHANNEL_ALL                                            = 0;
    /**
     * // 淘宝官方旗舰店渠道 1
     */
    public static final int    CHANNEL_TB                                             = 1;
    /**
     * // 商城 2
     */
    public static final int    CHANNEL_SC                                             = 2;
    /**
     * // 企业渠道 3
     */
    public static final int    CHANNEL_QY                                             = 3;
    /**
     * // 其他渠道 99
     */
    public static final int    CHANNEL_QT                                             = 99;

    /**
     * EIS接口编号-定时更新产品属性用
     * 对应EIS_INTERFACE_STATUS表的INTERFACE_CODE字段
     */
    public static final String EIS_INTERFACE_STATUS_INTERFACE_CODE_FOR_ITEM_ATTRIBUTE = "get_item_attribute";

    /**
     * EIS接口编号-定时更新物料基本信息用
     * 对应EIS_INTERFACE_STATUS表的INTERFACE_CODE字段
     */
    public static final String EIS_INTERFACE_STATUS_INTERFACE_CODE_FOR_ITEM_BASE      = "get_item_base";

    /**
     * EAI接口参数：DEPARTMENT-取得产品属性数据用
     */
    public static final String EAI_PARAM_DEP_FOR_ITEM_ATTRIBUTE                       = "table_hm_value_set";
    /**
     * EAI接口参数：TABLE_NAME-取得产品属性数据用
     */
    public static final String EAI_PARAM_TABLE_FOR_ITEM_ATTRIBUTE                     = "haiermdm.hm_value_set";

    /**
     * EAI接口参数：DEPARTMENT-取得物料基本信息数据用
     */
    public static final String EAI_PARAM_DEP_FOR_ITEM_BASE                        = "SC_table_view_mtl_base_all";
    /**
     * EAI接口参数：TABLE_NAME-取得物料基本信息数据用
     */
    public static final String EAI_PARAM_TABLE_FOR_ITEM_BASE                      = "haiermdm.view_mtl_base_all";
}
