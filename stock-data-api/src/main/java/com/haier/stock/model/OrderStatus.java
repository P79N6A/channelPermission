package com.haier.stock.model;

public enum OrderStatus {

    /** 订单未确认 */
    OS_UN_CONFIRM(200, "未确认"),
    /** 订单已确认 */
    OS_CONFIRM(201, "已确认"),
    /** 订单取消 */
    OS_CANCEL(202, "已取消"),
    /** 订单完成 */
    OS_COMPLETE(203, "已完成"),
    /** 部分缺货 */
    OS_NOSTOCK(204, "部分缺货"),
    /** 未定义 */
    UNDEFINED(-100, "未定义");

    private final Integer code;

    private final String  name;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    private OrderStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static OrderStatus getByCode(Integer code) {
        for (OrderStatus orderProductStatus : values()) {
            if (orderProductStatus.getCode().intValue() == code.intValue()) {
                return orderProductStatus;
            }
        }
        return UNDEFINED;
    }
}
