package com.haier.distribute.data.model;

import java.io.Serializable;
import java.util.Date;

public class TOperatorLog implements Serializable{
    private static final long serialVersionUID = 3431631072818009195L;

    private Long id;

    private String module;

    private Integer recordid;

    private String operator;

    private String changeitem;

    private String changecontent;

    private Date logtime;

    private String remark;


    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module == null ? null : module.trim();
    }

    public Integer getRecordid() {
        return recordid;
    }

    public void setRecordid(Integer recordid) {
        this.recordid = recordid;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}