package com.haier.distribute.data.model;

import java.io.Serializable;
import java.util.Date;

public class TOrderLogistics implements Serializable {

    private static final long serialVersionUID = 5595622765093487568L;

    private Integer id;

    private String ordersn;

    private String expressno;

	private Integer expressid;

    private String expressname;

    private String createby;

    private Date createtime;

    private String updateby;

    private Date updatetime;

    private String remark;
    
    public String getExpressno() {
    	return expressno;
    }
    
    public void setExpressno(String expressno) {
    	this.expressno = expressno;
    }
    
    public Integer getExpressid() {
    	return expressid;
    }
    
    public void setExpressid(Integer expressid) {
    	this.expressid = expressid;
    }
    
    public String getExpressname() {
    	return expressname;
    }
    
    public void setExpressname(String expressname) {
    	this.expressname = expressname;
    }

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


    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby == null ? null : createby.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getUpdateby() {
        return updateby;
    }

    public void setUpdateby(String updateby) {
        this.updateby = updateby == null ? null : updateby.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}