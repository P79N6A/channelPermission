package com.haier.purchase.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrderOperationLog  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  int Id;                 
	private  String order_id;             //订单号
	private  String sub_order_id;         //子单号
	private  int type;                    //原状态 
	private  String type_name;               //原状态名称 
	private  String is_sucess;            //1成功 0失败
	private  String content;              //更改内容
	private  String remark;               //备注
	private  Timestamp log_time;          //操作时间
	private  String operate_user;         //操作人
	private  String system;               //操作系统
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getSub_order_id() {
		return sub_order_id;
	}
	public void setSub_order_id(String sub_order_id) {
		this.sub_order_id = sub_order_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getIs_sucess() {
		return is_sucess;
	}
	public void setIs_sucess(String is_sucess) {
		this.is_sucess = is_sucess;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Timestamp getLog_time() {
		return log_time;
	}
	public void setLog_time(Timestamp log_time) {
		this.log_time = log_time;
	}
	public String getOperate_user() {
		return operate_user;
	}
	public void setOperate_user(String operate_user) {
		this.operate_user = operate_user;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

}
