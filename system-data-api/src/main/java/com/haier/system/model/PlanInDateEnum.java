package com.haier.system.model;

public enum PlanInDateEnum {

    ZCDH("整车直发到货周", 1),
    T2DH("T+2到货周", 2);
    // 业务码
    private int   code;
    // 描述
    private String desc;

    PlanInDateEnum(String desc,Integer code) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
    
}
