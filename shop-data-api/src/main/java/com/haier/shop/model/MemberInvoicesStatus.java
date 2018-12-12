package com.haier.shop.model;
/**
 * Created by 吴坤洋 on 2017/11/1
 * 发票 状态
 */
public enum MemberInvoicesStatus {
	    /** 待审核 */
	    START_PENDING_AUDIT(1, "已开票"),
	    /** 审核通过 */
	    STATUS_ADOPT(2, "未开票"),
	    /** 拒绝 */
	    SYNC_REFUSE(10, "待召回"),
	    
	    STASUS_RECALL(3,"已召回"),
	    
	    PUNCHED_TICKET(4,"已冲票"),
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
