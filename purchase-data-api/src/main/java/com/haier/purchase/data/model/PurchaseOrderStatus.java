package com.haier.purchase.data.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PurchaseOrderStatus {
    //采购订单状态 100：新建 ，110：确认，200: 完成，300：取消
    /** 新建 */
    PO_NEW("新建", 100),

    /** 确认 */
    CONFIRM("确认", 110),

    /** 完成 */
    PO_FINISH("完成", 200),

    /** 取消 */
    CANCEL("取消", 300);

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
    private PurchaseOrderStatus(String text, int value) {
        this.text = text;
        this.value = value;
    }

    private static final Map<Integer, PurchaseOrderStatus> lookup = new HashMap<Integer, PurchaseOrderStatus>();
    static {
        for (PurchaseOrderStatus s : EnumSet.allOf(PurchaseOrderStatus.class))
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
    public static PurchaseOrderStatus fromValue(int value) {
        return lookup.get(value);
    }

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }

}
