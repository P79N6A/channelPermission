package com.haier.purchase.data.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PurchaseOrderQueueStatus {
    //队列处理状态，-1：出错，0：待处理，1：已处理
    /** 出错 */
    POQ_ERROR("出错", -1),

    /** 待处理 */
    POQ_WAITING("待处理", 0),

    /** 已处理 */
    POQ_FINISH("已处理", 1);

    /** 枚举值 */
    private final String text;

    /** 枚举描述 */
    private final int    value;

    /**
     * 
     * 构建一个<code>OrderStatus.java</code>
     * @param code
     * @param value
     */
    private PurchaseOrderQueueStatus(String text, int value) {
        this.text = text;
        this.value = value;
    }

    private static final Map<Integer, PurchaseOrderQueueStatus> lookup = new HashMap<Integer, PurchaseOrderQueueStatus>();
    static {
        for (PurchaseOrderQueueStatus s : EnumSet.allOf(PurchaseOrderQueueStatus.class))
            lookup.put(s.toValue(), s);
    }

    /**
     * 获取枚举的值（整数值、字符串值等）
     * @return
     */
    public int toValue() {
        return this.value;
    }

    /**
     * 根据值（整数值、字符串值等）获取相应的枚举类型
     * @param value
     * @return
     */
    public static PurchaseOrderQueueStatus fromValue(int value) {
        return lookup.get(value);
    }

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }

}
