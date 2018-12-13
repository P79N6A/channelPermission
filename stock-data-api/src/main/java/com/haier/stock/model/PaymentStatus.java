package com.haier.stock.model;

public enum PaymentStatus {
	ALREADY_PAID(1, "已付款"),
	PENDING_REFUND(2, "待退款"),
	REFUNDED(3, "已退款"),
    
	OFFLINE_REFUND(4,"线下已退款"),
    
	NO_REFUND_REQUIRED(5,"无需退款"),

  /** 买家已付款 */
  PS_PAID( 101,"PS_PAID"),
	/** 未付款 */
	PS_UNPAID(100,"PS_UNPAID"),
    
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

    private PaymentStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    
    
    public static PaymentStatus getByCode(Integer code) {
        for (PaymentStatus paymentStatus : values()) {
            if (paymentStatus.getCode().intValue() == code.intValue()) {
                return paymentStatus;
            }
        }
		return UNDEFINED;
    }
}
