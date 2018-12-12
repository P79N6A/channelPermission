package com.haier.distribute.data.model;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/7 0007
 * \* Time: 9:58
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public enum OrderTypeDisplay {


    /** 普通订单 */
    TYPE_NORMAL(0, "普通订单"),

    /** 团购预付款订单 */
    TYPE_GROUP_ADVANCE(1, "团购预付款订单"),

    /** 团购尾款订单 */
    TYPE_GROUP_TAIL(2, "团购尾款订单"),

    /** 普通团购订单 */
    TYPE_GROUP(3, "普通团购订单"),

    /** 单订单模式的定金-尾款订单 */
    TYPE_GROUP_ADVANCE_TAIL(4, "单订单模式的定金-尾款订单"),

    /** 秒杀 */
    TYPE_SPIKE(5, "秒杀"),

    /** 限时抢购 */
    TYPE_LIMIT_BUY(6, "限时抢购");

    /** 枚举值 */
    private final Integer  code;

    /** 枚举描述 */
    private final String value;

    /**
     *
     * 构建一个<code>PaymentStatus.java</code>
     * @param code
     * @param value
     */
    private OrderTypeDisplay(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    /**
     * @return Returns the code.
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @return Returns the value.
     */
    public String getValue() {
        return value;
    }

    /**
     * @return Returns the code.
     */
    public Integer code() {
        return code;
    }

    /**
     * @return Returns the value.
     */
    public String value() {
        return value;
    }

    public static OrderTypeDisplay getByCode(Integer code) {
        for (OrderTypeDisplay orderTypeStatus : values()) {
            if (orderTypeStatus.getCode().intValue() == code.intValue()) {
                return orderTypeStatus;
            }
        }
        return TYPE_NORMAL;
    }

}