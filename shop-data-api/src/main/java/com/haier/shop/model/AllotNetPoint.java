package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/12 0012.
 */
public class AllotNetPoint implements Serializable {


    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -4759070375956859590L;

    private String            CUSTOMER_CODE;

    private String            ORDER_NO;

    private String            CREATED_DATE;

    private String            PROC_REMARK;

    private String            ENTER_TIME;

    private String            SB_DATE;

    private String            ASSIGN_DATE;

    public static Integer     STATUS_INITIAL   = 0;
    public static Integer     STATUS_SUCCESS   = 1;
    public static Integer     STATUS_FAIL      = 2;

    private Integer           id;

    private Integer           status;

    private String            message;

    private Integer           apiLogsId;

    private Date createTime;

    private Date              updateTime;

  /*  public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getProcRemark() {
        return procRemark;
    }

    public void setProcRemark(String procRemark) {
        this.procRemark = procRemark;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public String getSbDate() {
        return sbDate;
    }

    public void setSbDate(String sbDate) {
        this.sbDate = sbDate;
    }

    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }*/

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getApiLogsId() {
        return apiLogsId;
    }

    public void setApiLogsId(Integer apiLogsId) {
        this.apiLogsId = apiLogsId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getCUSTOMER_CODE() {
		return CUSTOMER_CODE;
	}

	public void setCUSTOMER_CODE(String cUSTOMER_CODE) {
		CUSTOMER_CODE = cUSTOMER_CODE;
	}

	public String getORDER_NO() {
		return ORDER_NO;
	}

	public void setORDER_NO(String oRDER_NO) {
		ORDER_NO = oRDER_NO;
	}

	public String getCREATED_DATE() {
		return CREATED_DATE;
	}

	public void setCREATED_DATE(String cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	public String getPROC_REMARK() {
		return PROC_REMARK;
	}

	public void setPROC_REMARK(String pROC_REMARK) {
		PROC_REMARK = pROC_REMARK;
	}

	public String getENTER_TIME() {
		return ENTER_TIME;
	}

	public void setENTER_TIME(String eNTER_TIME) {
		ENTER_TIME = eNTER_TIME;
	}

	public String getSB_DATE() {
		return SB_DATE;
	}

	public void setSB_DATE(String sB_DATE) {
		SB_DATE = sB_DATE;
	}

	public String getASSIGN_DATE() {
		return ASSIGN_DATE;
	}

	public void setASSIGN_DATE(String aSSIGN_DATE) {
		ASSIGN_DATE = aSSIGN_DATE;
	}
    
}
