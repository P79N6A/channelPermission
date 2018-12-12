package com.haier.order.model;

/**
 * Created by 吴坤洋 on 2017/10/27
 */
public enum OrderProductStatus {

    /** 处理中 */
    START_STATUS(0, "处理中"),
    /** 已占用库存 */
    STATUS_FROZEN_STOCK(1, "已占用库存"),
    /** 同步到HP */
    SYNC_HP(2, "同步到HP"),
    /** 同步到EC */
    STATUS_SYNC_EC(3, "同步到EC"),
    /** 分配网点 */
    DISPATCH_NET_POINT(4, "已分配到网点"),
    /** 已开提单，待出库 */
    LES_SHIPPING(8, "待出库"),
    /** 待审核 */
    WAIT_VERIFY(10, "待审核"),
    /** 待转运入库 */
    WAIT_TRANS_SHIP_IN(11, "待转运入库"),
    /** 待转运出库 */
    WAIT_TRANS_SHIP_OUT(12, "待转运出库"),
    /** 待发货 */
    WAIT_DELIVERY(40, "待发货"),
    /** 网单拒单 */
    NET_POINT_REFUSE(150, "网点拒单"),
    /** 待交付 */
    WAIT_DELIVER(70, "待交付"),
    /** 完成关闭 */
    COMPLETED_CLOSE(130, "完成关闭"),
    /** 用户已签收 */
    USER_SIGN(140, "用户签收"),
    /** 用户拒收 */
    USER_REJECTION(160, "用户拒收"),
    /** 取消关闭 */
    CANCEL_CLOSE(110, "取消关闭"),
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

    private OrderProductStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    

    public static OrderProductStatus getByCode(Integer code) {
        for (OrderProductStatus orderProductStatus : values()) {
            if (orderProductStatus.getCode().intValue() == code.intValue()) {
                return orderProductStatus;
            }
        }
        return UNDEFINED;
    }
}
