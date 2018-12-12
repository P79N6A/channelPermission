package com.haier.svc.api.form;

/**
 * Created by hanhaoyang@ehaier.com on 2017/11/27 0027.
 */
public enum LbxStatusEnum {
	NEW("NEW","未开始处理"),
	ACCEPT("ACCEPT","仓库接单"),
	PARTFULFILLED("PARTFULFILLED","部分收货完成"),
	FULFILLED("FULFILLED","收货完成"),
	EXCEPTION("EXCEPTION","异常"),
	CANCELED("CANCELED","取消"),
	CLOSED("CLOSED","关闭"),
	REJECT("REJECT","拒单"),
	CANCELEDFAIL("CANCELEDFAIL","取消失败")
    ;

    private LbxStatusEnum(String code, String name){
        this.code = code;
        this.name = name;
    };

    private String code;
    private String name;

    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
}
