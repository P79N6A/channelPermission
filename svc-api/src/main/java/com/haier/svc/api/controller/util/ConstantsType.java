package com.haier.svc.api.controller.util;

public class ConstantsType {

    //-----------------------------------static final property --------------------------------------------

    /******* 数据库字段别名 -- 常量 *******/
    /** 数据库字段 -- 活动id */
    public static final String DB_ACTIVITY_ID                         = "activity_id";
    /** 数据库字段 -- 活动名称 */
    public static final String DB_ACTIVITY_NAME                       = "activity_name";
    /** 订单台数  */
    public static final String NUMBER                                 = "num";
    /** 下单时间 */
    public static final String ADDTIME                                = "addTime";
    /** 活动主键id */
    public static final String ACTIVITYID2                            = "activityId2";
    /** 渠道代码 */
    public static final String SOURCE                                 = "source";
    /** 统计下单量 */
    public static final String ORDERTYPE                              = "orderType";
    /** 统计时间 */
    public static final String CONFIRMTIME                            = "confirmTime";
    /** 付款尾款时间 */
    public static final String TAILPAYTIME                            = "tailPayTime";
    /** 付款状态 */
    public static final String PAYMENTSTATUS                          = "paymentStatus";
    /** 网单状态 */
    public static final String ORDERPRODUCTSTATUS                     = "cancelCount";
    /** 派工时间 */
    public static final String HPALLOTNETPOINTTIME                    = "allotCount";
    /** 开单时间 */
    public static final String LESSHIPPING                            = "openOrderCount";
    /** 出库时间 */
    public static final String LESSHIPTIME                            = "outStoragCount";
    /** hp回传网点时间 */
    public static final String NETPOINTACCEPTTIME                     = "netPointCount";
    /** LES回传网点时间 */
    public static final String NETPOINTARRIVETIME                     = "netPointArriveTime";
    /** HP回传网点出库时间 */
    public static final String NETPOINTSHIPTIME                       = "natePointOutCount";
    /** HP回传用户签收时间 */
    public static final String USERACCEPTTIME                         = "userAcceptCount";
    /** 区县id */
    public static final String REGIONID                               = "areaCode";

    /******* rest请求mapkey值 -- 常量  *******/
    /** 【订单量】树状统计数据  -- key */
    public static final String ORDER_KEY                              = "order";
    /** 【订单量】树状列表统计数据  -- key */
    public static final String ORDER_LIST_KEY                         = "order_list";
    /** 【订单台数量】树状统计数据 -- key */
    public static final String NUMBER_KEY                             = "number";
    /** 【订单台数量】树状列表统计数据 -- key */
    public static final String NUMBER_LIST_KEY                        = "number_list";
    /** 【型号-品类】统计数据 -- key */
    public static final String TYPE_KEY                               = "type";
    /** 【库位-区域】统计数据 -- key */
    public static final String LOCATION_KEY                           = "location";
    /** 【网点】统计数据 -- key */
    public static final String NETPOINT_KEY                           = "netpoint";
    /** 【渠道】统计数据 -- key */
    public static final String CHANNEL_KEY                            = "channel";
    /** 【订单台数】列表统计数据 -- key */
    public static final String ALL_ACTIVITY_ORDER                     = "all_activity_order";
    /** 【订单台数】列表统计数据 -- key */
    public static final String ALL_ACTIVITY_NUMBER                    = "all_activity_number";

    /****** 设置查询参数的  -- 常量  ******/
    /** 活动id号 */
    public static final String ACTIVITY_ID                            = "activity_id";
    /** 时间区间 - 开始日期 */
    public static final String START_DATE                             = "start_date";
    /** 时间区间 - 结束日期 */
    public static final String END_DATE                               = "end_date";
    /** 所有活动id串, xxx,xxx,xxx */
    public static final String ACTIVITYID_STR                         = "activityid_str";
    /** 订单状态 */
    public static final String ORDER_STATUS                           = "order_status";
    /** 检查的日期点 */
    public static final String CHECK_DATE_BEGIN                       = "check_date_begin";
    /** 检查的日期点加一天 */
    public static final String CHECK_DATE_END                         = "check_date_end";
    /** 配送时效  */
    public static final String SHIPPING_TIME                          = "shipping_time";
    /** 省  */
    public static final String PROVINCE                               = "province";
    /** 市   */
    public static final String CITY                                   = "city";
    /** 区  */
    public static final String REGION                                 = "region";
    /** 省市区县筛选维度   */
    public static final String PCR                                    = "PCR";
    /** 区域筛选维度   */
    public static final String A                                      = "A";
    /** 物流区域筛选维度   */
    public static final String FDA                                    = "FDA";
    /** 筛选维度的key */
    public static final String QUERY_GROUP                            = "query_group";

    /********* 统计每个日期  -- 常量  ******/
    /** tree订单统计key */
    public static final String TREE_ORDER_KEY                         = "tree_order_key";
    /** tree订单台数统计key */
    public static final String TREE_NUMBER_KEY                        = "tree_number_key";
    /** x轴的时间数组 */
    public static final String DATE_INTERVAL                          = "date_interval";
    /** 订单总量  */
    public static final String ORDERS_COUNT                           = "orders_count";
    /** 未闭环的总量  */
    public static final String ORDERS_UNCLOSED_COUNT                  = "orders_unclosed_count";
    /** 未闭环-->待确认总量  */
    public static final String ORDERS_UNCLOSED_COUNT_CONFIRM          = "orders_unclosed_count_confirm";
    /** 未闭环-->待派工总量  */
    public static final String ORDERS_UNCLOSED_COUNT_SEND             = "orders_unclosed_count_send";
    /** 未闭环-->待开提单总量  */
    public static final String ORDERS_UNCLOSED_COUNT_BILLING          = "orders_unclosed_count_billing";
    /** 未闭环-->待到网点总量  */
    public static final String ORDERS_UNCLOSEDNO_COUNT_WEBSITE        = "orders_unclosedno_count_website";
    /** 未闭环-->待出库总量  */
    public static final String ORDERS_UNCLOSED_COUNT_OUTBOUND         = "orders_unclosed_count_outbound";
    /** 未闭环-->待网点出库总量  */
    public static final String ORDERS_UNCLOSED_COUNT_WEBSITE_OUTBOUND = "orders_unclosed_count_website_outbound";
    /** 未闭环-->待送达用户总量  */
    public static final String ORDERS_UNCLOSED_COUNT_SENDUSER         = "orders_unclosed_count_senduser";
    /** 各个日期的秒数组key */
    public static final String DATE2SECONDARR                         = "date2SecondArr";
    /** 追加一天的各个日期的秒数组key */
    public static final String ADDONEDATE2SECONDARR                   = "addOneDate2SecondArr";

    /******* 所有活动下的订单各状态的统计数据 -- 常量  ********/
    /** 活动主键  */
    public static final String ALL_ACTIVITY_ID                        = "all_activity_id";
    /** 活动名称  */
    public static final String ALL_ACTIVITY_NAME                      = "all_activity_name";
    /** 下单数量 */
    public static final String ORDERTYPECOUNT                         = "orderTypeCount";
    /** 付款数量  */
    public static final String PAYCOUNT                               = "payCount";
    /** 取消关闭  */
    public static final String CANCELCOUNT                            = "cancelCount";
    /** 付款率 */
    public static final String PAYRATE                                = "payRate";
    /** 确认数 */
    public static final String CONFIRMCOUNT                           = "confirmCount";
    /** 未确认数 */
    public static final String UNCONFIRMCOUNT                         = "unConfirmCOunt";
    /** 确认率 */
    public static final String CONFIRMRATE                            = "confirmRate";
    /** 派工数 */
    public static final String ALLOTCOUNT                             = "allotCount";
    /** 付款派工率 */
    public static final String ALLOTRATE                              = "allotRate";
    /** 开单数 */
    public static final String OPENORDERCOUNT                         = "openOrderCount";
    /** 付款开单率 */
    public static final String OPENORDERRATE                          = "openOrderRate";
    /** 出库数 */
    public static final String OUTSTORAGCOUNT                         = "outStoragCount";
    /** 付款出库率 */
    public static final String OUTSTORAGERATE                         = "outStorageRate";
    /** 已到网点数 */
    public static final String NETPOINTCOUNT                          = "netPointCount";
    /** 付款到网点率 */
    public static final String NETPOINTRATE                           = "netPointRate";
    /** 网点出库数 */
    public static final String NATEPOINTOUTCOUNT                      = "natePointOutCount";
    /** 付款网点出库率 */
    public static final String NETPOINTOUTRATE                        = "netPointOUtRate";
    /** 已到用户数 */
    public static final String USERACCEPTCOUNT                        = "userAcceptCount";
    /** 付款到用户率 */
    public static final String USERACCEPTRATE                         = "userAcceptRate";
    /** 确认派工率(派工数/确认数)*/
    public static final String CONFIRMALLOTRATE                       = "confirmAllotRate";
    /** 派工开单率(开单数/派工数)*/
    public static final String ALLOTOPENORDERRATE                     = "allotOpenOrderRate";
    /** 开单出库率(出库数/开单数)*/
    public static final String OPENORDEROUTSTORAGERATE                = "openOrderOutStorageRate";
    /** 出库到网点率(已到网点数/出库数)*/
    public static final String OUTSTORAGENATEPOINTRATE                = "outStorageNatePointRate";
    /** 到网点出库率(网点出库数/已到网点数)*/
    public static final String NATEPOINTOUTSTORAGERATE                = "natePointOutStorageRate";
    /** 网点出库到用户率(已到用户数/网点出库数)*/
    public static final String NATEPOINTOUTUSERACCEPTRATE             = "natePointOutuserAcceptRate";
    
    public static enum SYS_TYPE {
    	POP_ORDER_PAY_TYPE, // 订单付款状态
    	POP_ORDER_TYPE, // 订单状态
    	INVOICE_TYPE // 发票状态
    }


}
