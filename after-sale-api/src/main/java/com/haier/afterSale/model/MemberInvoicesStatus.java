package com.haier.afterSale.model;
/**
 * Created by 吴坤洋 on 2017/11/1
 * 发票 状态
 */
public enum MemberInvoicesStatus {

		STASUS_INITIAL(0,"待审核"),

	    START_PENDING_AUDIT(1, "审核通过"),

	    STATUS_ADOPT(2, "审核拒绝"),

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

	    private MemberInvoicesStatus(Integer code, String name) {
	        this.code = code;
	        this.name = name;
	    }
	    
	    public static MemberInvoicesStatus getByCode(Integer code) {
	        for (MemberInvoicesStatus memberInvoicesStatus : values()) {
	            if (memberInvoicesStatus.getCode().intValue() == code.intValue()) {
	                return memberInvoicesStatus;
	            }
	        }
			return UNDEFINED;
	    }

}
