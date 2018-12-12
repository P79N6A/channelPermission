/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.stock.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 
 * @Filename: InvRrsWarehouseItem.java inv_rrs_warehouse实体类
 * @Version: 1.0
 * @Author: yanrp 燕如朋
 * @Email: yanrp110428@dhc.com.cn
 * 
 */
public class InvRrsWarehouse implements Serializable {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 7251444158784299582L;
	private String rrs_wh_code; // 日日顺库位码
	private String rrs_wh_name; // 日日顺仓库名称
	private String estorge_id; // 电商库位码
	private Integer t2_default; // T+2上单默认
	private Integer transport_prescription; // 运输时效（天）
	private String create_user; // 创建者
	private Date create_time; // 创建时间
	private String update_user; // 最后更新人
	private Timestamp update_time; // 最后更新时间
	private Integer is_enabled;//状态标志位
	private String create_time_web;//此字段用于前台显示日期格式的修正  数据库中无此字段
	private String update_time_web;//此字段用于前台显示日期格式的修正  数据库中无此字段
	public String getCreate_time_web() {
        return create_time_web;
    }

    public String getUpdate_time_web() {
        return update_time_web;
    }

    public void setUpdate_time_web(String update_time_web) {
        this.update_time_web = update_time_web;
    }

    public void setCreate_time_web(String create_time_web) {
        this.create_time_web = create_time_web;
    }

    //
	private String is_enabled_name;  //状态标志位对应汉字解释，数据库中无此字段，只用于前台显示
	/**
	 * @return Returns the rrs_wh_code
	 */
	public String getRrs_wh_code() {
		return rrs_wh_code;
	}

	/**
	 * @param rrs_wh_code
	 *            The rrs_wh_code to set.
	 */
	public void setRrs_wh_code(String rrs_wh_code) {
		this.rrs_wh_code = rrs_wh_code;
	}

	/**
	 * @return Returns the rrs_wh_name
	 */
	public String getRrs_wh_name() {
		return rrs_wh_name;
	}

	/**
	 * @param rrs_wh_name
	 *            The rrs_wh_name to set.
	 */
	public void setRrs_wh_name(String rrs_wh_name) {
		this.rrs_wh_name = rrs_wh_name;
	}

	/**
	 * @return Returns the estorge_id
	 */
	public String getEstorge_id() {
		return estorge_id;
	}

	/**
	 * @param estorge_id
	 *            The estorge_id to set.
	 */
	public void setEstorge_id(String estorge_id) {
		this.estorge_id = estorge_id;
	}

	/**
	 * @return Returns the t2_default
	 */
	public Integer getT2_default() {
		return t2_default;
	}

	/**
	 * @param t2_default
	 *            The t2_default to set.
	 */
	public void setT2_default(Integer t2_default) {
		this.t2_default = t2_default;
	}

	/**
	 * @return Returns the transport_prescription
	 */
	public Integer getTransport_prescription() {
		return transport_prescription;
	}

	/**
	 * @param transport_prescription
	 *            The transport_prescription to set.
	 */
	public void setTransport_prescription(Integer transport_prescription) {
		this.transport_prescription = transport_prescription;
	}

	/**
	 * @return Returns the create_user
	 */
	public String getCreate_user() {
		return create_user;
	}

	/**
	 * @param create_user
	 *            The create_user to set.
	 */
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	/**
	 * @return Returns the create_time
	 */
	public Date getCreate_time() {
		return create_time;
	}

	/**
	 * @param create_time
	 *            The create_time to set.
	 */
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	/**
	 * @return Returns the update_user
	 */
	public String getUpdate_user() {
		return update_user;
	}

	/**
	 * @param update_user
	 *            The update_user to set.
	 */
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	/**
	 * @return Returns the update_time
	 */
	public Timestamp getUpdate_time() {
		return update_time;
	}

	/**
	 * @param update_time
	 *            The update_time to set.
	 */
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}

	/**
	 * @return Returns the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getIs_enabled() {
		return is_enabled;
	}

	public void setIs_enabled(Integer is_enabled) {
		this.is_enabled = is_enabled;
	}

	public String getIs_enabled_name() {
		return is_enabled_name;
	}

	public void setIs_enabled_name(String is_enabled_name) {
		this.is_enabled_name = is_enabled_name;
	}

    @Override
    public String toString() {
        return "InvRrsWarehouse [rrs_wh_code=" + rrs_wh_code + ", rrs_wh_name=" + rrs_wh_name
               + ", estorge_id=" + estorge_id + ", t2_default=" + t2_default
               + ", transport_prescription=" + transport_prescription + ", create_user="
               + create_user + ", create_time=" + create_time + ", update_user=" + update_user
               + ", update_time=" + update_time + ", is_enabled=" + is_enabled
               + ", create_time_web=" + create_time_web + ", update_time_web=" + update_time_web
               + ", is_enabled_name=" + is_enabled_name + "]";
    }

	
}
