package com.haier.afterSale.model;

public enum CargoStatus {
	OUT_OF_WAREHOUSE(1, "已出库"),
	NOT_OF_WAREHOUSE(2, "未出库"),
	RECALL(3,"已召回"),
	WAREHOUSING(4,"已入库"),
	PENDING_RECALL(10, "待召回"),
	WAREHOUSING22(122,"已入22库"),
	WAREHOUSING21(121,"已入21库"),
	WAREHOUSING10(110,"已入10库"),
	WAREHOUSING40(140,"已入40库"),
	WAREHOUSING41(141,"已入41库"),
	WAREHOUSINGDAY21(221,"已入日日顺21库"),
	WAREHOUSINGJINLI(21,"已入金立库"),
	CAINIAOZP(100,"菜鸟正品"),
	CNJS_PENDING_IDENTIFICATION(104,"菜鸟机损待鉴定"),
	CNXS_PENDING_IDENTIFICATION(105,"菜鸟箱损待鉴定"),

    
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

    private CargoStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public static CargoStatus getByCode(Integer code) {
        for (CargoStatus cargoStatus : values()) {
            if (cargoStatus.getCode().intValue() == code.intValue()) {
                return cargoStatus;
            }
        }
		return UNDEFINED;
    }

}
