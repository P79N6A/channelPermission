package com.haier.shop.model;

public enum OrderType {

    /** 普通订单 */
    TYPE_NORMAL("TYPE_NORMAL", 0),

    /** 团购预付款订单 */
    TYPE_GROUP_ADVANCE("TYPE_GROUP_ADVANCE", 1),

    /** 团购尾款订单 */
    TYPE_GROUP_TAIL("TYPE_GROUP_TAIL", 2),

    /** 普通团购订单 */
    TYPE_GROUP("TYPE_GROUP", 3),

    /** 单订单模式的定金-尾款订单 */
    TYPE_GROUP_ADVANCE_TAIL("TYPE_GROUP_ADVANCE_TAIL", 4),

    /** 秒杀 */
    TYPE_SPIKE("TYPE_SPIKE", 5),

    /** 限时抢购 */
    TYPE_LIMIT_BUY("TYPE_LIMIT_BUY", 6);

    /** 枚举值 */
    private final String  code;

    /** 枚举描述 */
    private final Integer value;

    /**
     * 
     * 构建一个<code>PaymentStatus.java</code>
     * @param code
     * @param value
     */
    private OrderType(String code, Integer value) {
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
    public Integer getValue() {
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
    public Integer value() {
        return value;
    }

    public int getIntValue() {
        return value.intValue();
    }

}
