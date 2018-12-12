package com.haier.shop.model;

/**
 * 标建确认状态枚举
 * 
 * @author jun.panhj
 */
public enum SmConfirmStatusEnum {
	/**
	 * 1：初始状态
	 */
	INIT(1, "初始状态"),
	/**
	 * 2:已发送惠普
	 */
	SENG_HP(2, "已发送惠普"),
	/**
	 * 3:待人工处理
	 */
	W_MANUAL_HANDLE(3, "待人工处理"),
	/**
	 * 4:等待自动处理
	 */
	W_AUTO_HANDLE(4, "等待自动处理"),
	/**
	 * 5:已确认
	 */
	CONFIRM(5, "已确认");
	private int code;
	private String desc;

	SmConfirmStatusEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public SmConfirmStatusEnum getEnumByCode(int code) {
		SmConfirmStatusEnum[] list = SmConfirmStatusEnum.values();

		for (SmConfirmStatusEnum status : list) {
			if (status.getCode() == code) {
				return status;
			}
		}
		return null;
	}

}
