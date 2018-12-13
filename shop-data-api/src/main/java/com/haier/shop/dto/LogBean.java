package com.haier.shop.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author zhangtianshuai
 *日志操作表
 */
public class LogBean implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5258184624544041392L;

	private String    id;//主键

    private String  username;//操作人

    private String  mkname;//模块名 1：人员信息管理  2：人员责任位配置 3：工单

    private Integer type;//操作类型1:人员添加 2:人员修改 3:人员删除 4:人员责任位配置添加 5:人员信息修改 6:人员责任位配置删除7:责任位添加 8:责任位修

    private String  content;//操作相关记录（如操作的数据内容、修改记录数量等）

    private Date    logtime;//日志添加时间

    private String  fkid;//外键id

    public LogBean() {
    }

    /**
     * @param username 操作人
     * @param mkname 模块名 1：人员信息管理  2：人员责任位配置 3：工单
     * @param type 操作类型1~17
     * @param content 操作相关记录（如操作的数据内容、修改记录数量等）
     * @param fkid 外键id
     */
    public LogBean(String username, String mkname, Integer type, String content, String fkid) {
        this.username = username;
        this.mkname = mkname;
        this.type = type;
        this.content = content;
        this.fkid = fkid;
        this.logtime = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getMkname() {
        return mkname;
    }

    public void setMkname(String mkname) {
        this.mkname = mkname == null ? null : mkname.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getLogtime() {
        return logtime;
    }

    public void setLogtime(Date logtime) {
        this.logtime = logtime;
    }

    public String getFkid() {
        return fkid;
    }

    public void setFkid(String fkid) {
        this.fkid = fkid == null ? null : fkid.trim();
    }

    @Override
    public String toString() {
        return "log [id=" + id + ", username=" + username + ", mkname=" + mkname + ", type=" + type
               + ", content=" + content + ", logtime=" + logtime + ", fkid=" + fkid + "]";
    }

}