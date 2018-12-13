package com.haier.purchase.data.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PurchaseItemStatus {
    //采购网单状态，10：新建，15：确认，20：入库中，100：完成，200：取消
    /** 新建 */
    PI_NEW("新建", 10),

    /** 确认 */
    CONFIRM("确认", 15),

    /** 入库中 */
    PI_HANDLE("入库中", 20),

    /** 完成 */
    PI_FINISH("完成", 100),

    /** 取消 */
    PO_CANCEL("取消", 200),

    /** 日日单状态：订单未满足 */
    PD_UNMET("订单未满足", 1101),

    /** 日日单状态：订单已撤销 */
    PD_REVOCATION("已撤销", 1102),
    /** 日日单状态：待审核 */
    PD_TO_AUDIT("待审核", 1103),
    /** 日日单状态：已冻结 */
    PD_HAS_BEEN_FROZEN("已冻结", 1104),
    /** 日日单状态：待评审 */
    PD_TO_REVIEW("待评审", 1105),
    /** 日日单状态：等待发货 */
    PD_WAIT_FOR_DELIVERY("等待发货", 1106),
    /** 日日单状态：事业部已发货 */
    PD_BIZ_DEPt_HAS_SHIPPED("事业部已发货", 1107),
    /** 日日单状态：等待工贸记账 */
    PD_WAIT_INDUSTRY_TRADE_BOOK("等待工贸记账", 1108),
    /** 日日单状态：等待工贸记账 */
    PD_WAIT_INDUSTRY_TRADE_DELIVERY("等待工贸发货", 1109),
    /** 日日单状态：待入日日顺库 */
    PD_WAIT_INTO_RRS_WAREHOUSE("待入日日顺库", 1110),
    /** 日日单状态：已入日日顺库 */
    PD_HAS_ENTER_RRS_WAREHOUSE("已入日日顺库", 1111),
    /** 日日单状态：已开入WA提单 */
    PD_HAS_CREATE_ENTRY_WA_WAREHOUSE_PING("已开入WA库提单", 1112),
    /** 日日单状态：取消入WA库 */
    PD_HAS_CANCEL_ENTRY_WA_WAREHOUSE("取消入WA库", 1113);
    /** 枚举值 */
    private final String text;

    /** 枚举描述 */
    private final int    value;

    /**
     * 
     * 构建一个<code>OrderStatus.java</code>
     * @param text
     * @param value
     */
    private PurchaseItemStatus(String text, int value) {
        this.text = text;
        this.value = value;
    }

    private static final Map<Integer, PurchaseItemStatus> lookup = new HashMap<Integer, PurchaseItemStatus>();
    static {
        for (PurchaseItemStatus s : EnumSet.allOf(PurchaseItemStatus.class))
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
    public static PurchaseItemStatus fromValue(int value) {
        return lookup.get(value);
    }

    public static PurchaseItemStatus fromText(String text) {
        for (PurchaseItemStatus purchaseItemStatus : values()) {
            if (purchaseItemStatus.getText().equals(text))
                return purchaseItemStatus;
        }
        return null;
    }

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }

}
