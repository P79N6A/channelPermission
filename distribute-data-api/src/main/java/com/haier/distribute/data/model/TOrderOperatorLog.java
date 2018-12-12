package com.haier.distribute.data.model;

import java.io.Serializable;
import java.util.Date;

public class TOrderOperatorLog implements Serializable {

    private static final long serialVersionUID = 5595622765093487568L;

    private Integer id;

    private String ordersn;

    private String operator;

    private String changeitem;

    private String changecontent;

    private Date logtime;

    private String remark;
    
    private String operateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn == null ? null : ordersn.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getChangeitem() {
        return changeitem;
    }

    public void setChangeitem(String changeitem) {
        this.changeitem = changeitem == null ? null : changeitem.trim();
    }

    public String getChangecontent() {
        return changecontent;
    }

    public void setChangecontent(String changecontent) {
        this.changecontent = changecontent == null ? null : changecontent.trim();
    }

    public Date getLogtime() {
        return logtime;
    }

    public void setLogtime(Date logtime) {
        this.logtime = logtime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
}