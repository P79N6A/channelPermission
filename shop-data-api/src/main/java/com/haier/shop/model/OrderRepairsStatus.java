package com.haier.shop.model;

public enum OrderRepairsStatus {
	 /** 审核中 */
    START_REPAI_AUDIT(1, "审核中"),
    /** 进行中 */
    STATUS_ADOPT(2, "进行中"),
    /** 受理完成 */
    SYNC_REFUSE(3, "受理完成"),
    
    STATUS_IS_ORVER(4,"已完结"),
    
    STATUS_REJWCTED(5,"已驳回"),
    
    STATUS_TERMINATED(6,"已终止"),

    OFFLINE_REFUND(7,"线下已退款"),
    
    WAITING_FOR_CONFIRMATION_TO_TERMINAT(8,"等待确认终止"),

    WAITING_FOR_HP_CONFIRMATION_REJECT(9,"等待HP确认拒收"),

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

    private OrderRepairsStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public static OrderRepairsStatus getByCode(Integer code) {
        for (OrderRepairsStatus orderRepairsStatus : values()) {
            if (orderRepairsStatus.getCode().intValue() == code.intValue()) {
                return orderRepairsStatus;
            }
        }
		return UNDEFINED;
    }
}
