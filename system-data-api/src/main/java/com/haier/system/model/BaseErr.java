package com.haier.system.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class BaseErr implements Serializable{
	private Integer log_id;              //主键ID
	private String order_id;             //订单ID
	private String menu_path;            //业务模块
	private String interface_path;       //接口地址
	private String message;              //审核错误信息
	private Timestamp log_time;          //日志时间
	private Integer user_id;             //用户名
	private Integer role_id;             //角色
	
	public Integer getLog_id() {
		return log_id;
	}
	public void setLog_id(Integer log_id) {
		this.log_id = log_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getMenu_path() {
		return menu_path;
	}
	public void setMenu_path(String menu_path) {
		this.menu_path = menu_path;
	}
	public String getInterface_path() {
		return interface_path;
	}
	public void setInterface_path(String interface_path) {
		this.interface_path = interface_path;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Timestamp getLog_time() {
		return log_time;
	}
	public void setLog_time(Timestamp log_time) {
		this.log_time = log_time;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getRole_id() {
		return role_id;
	}
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
}