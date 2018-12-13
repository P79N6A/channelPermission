package com.haier.shop.model;

public enum ExternalOrderState {

    /** 未同步 */
    OS_UNSYNCED("OS_UNSYNCED", 1001),

    /** 已同步到系统 */
    OS_SYNCEDSYS("OS_SYNCEDSYS", 1002),

    /** 已完成同步 */
    OS_SYNCED("OS_SYNCED", 1003),

    /** 同步失败 */
    OS_SYNCFAILED("OS_SYNCFAILED", 1004),
    
    /** 同步异常-需要开发人员介入解决 */
    OS_SYNCException("OS_SYNCException", 110),

    /** 回传发货状态失败 */
    OS_SHIPFAILED("OS_SHIPFAILED", 1009),
    
    /** 不应同步 */
    OS_NOT_NEED_SYNC("OS_NOT_NEED_SYNC", 1005),

    /** 等待继续同步(例如淘宝万人团订单) */
    OS_WAIT_CONTINUE_SYNC("OS_WAIT_CONTINUE_SYNC", 1006),
    
    /** 
     * 海尔官方旗舰店9.5至10.8活动 
     */
    OS_WAIT_CONTINUE_SYNC_1008("OS_WAIT_CONTINUE_SYNC_1008", 1008),

    /**
     * 套装payamount价格和系统不一致
     */
    OS_EXTERNAL_PRICE_ERROR("OS_EXTERNAL_PRICE_ERROR",1010),

    /**
     * 3W订单发货状态
     */
    OS_EXTERNAL_3W_SHIPPING_STATUS("OS_EXTERNAL_3W_SHIPPING_STATUS",1013),
    /**
     * 海尔官方旗舰店10月28日规则
     */
    OS_WAIT_CONTINUE_SYNC_1028("OS_WAIT_CONTINUE_SYNC_1028", 1028),

    /**
     * 双11大促节点状态
     */
    OS_EXTERNAL_DUBBO_ELEVEN("OS_EXTERNAL_DUBBO_ELEVEN", 1111),

    /**
     * 统帅日日顺乐家专卖店2014年1月11日规则
     */
    OS_WAIT_CONTINUE_SYNC_140111("OS_WAIT_CONTINUE_SYNC_140111", 1401);

    /** 枚举值 */
    private final String code;

    /** 枚举描述 */
    private final int    value;

    /**
     * 
     * 构建一个<code>ExternalOrderState.java</code>
     * @param code
     * @param value
     */
    private ExternalOrderState(String code, int value) {
        this.code = code;
        this.value = value;
    }

    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Returns the value.
     */
    public int getValue() {
        return value;
    }

    /**
     * @return Returns the code.
     */
    public String code() {
        return code;
    }

    /**
     * @return Returns the value.
     */
    public int value() {
        return value;
    }

}
