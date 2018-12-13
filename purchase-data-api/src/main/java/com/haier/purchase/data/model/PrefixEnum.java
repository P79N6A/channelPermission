package com.haier.purchase.data.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PrefixEnum {
    /** 采购订单编号前缀 */
    PREFIX_PO("PURCHASE_ORDER", "PO"),

    /** 采购订单明细编号前缀 */
    PREFIX_PI("PURCHASE_ITEM", "WD");

    /** 枚举值 */
    private final String text;

    /** 枚举描述 */
    private final String value;

    /**
     * 
     * 构建一个<code>OrderStatus.java</code>
     * @param code
     * @param value
     */
    private PrefixEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    private static final Map<String, PrefixEnum> lookup = new HashMap<String, PrefixEnum>();
    static {
        for (PrefixEnum s : EnumSet.allOf(PrefixEnum.class))
            lookup.put(s.toValue(), s);
    }

    /**
     * 获取枚举的值（整数值、字符串值等）
     * @return
     */
    public String toValue() {
        return this.value;
    }

    /**
     * 根据值（整数值、字符串值等）获取相应的枚举类型
     * @param value
     * @return
     */
    public static PrefixEnum fromValue(int value) {
        return lookup.get(value);
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }

}
