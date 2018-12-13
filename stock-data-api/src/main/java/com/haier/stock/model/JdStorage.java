package com.haier.stock.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 
 * @Filename: JdStorage.java t_jd_storage实体类
 * @Version: 1.0
 * @Author: zhangming
 * 
 */
public class JdStorage implements Serializable {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 7251444158784299582L;
	
	/*
	 * 分公司编码 
	 */
	private String corpCode;
	/*
	 *  区域编码
	 */
	private String regionId;
	/*
	 * 区域名称
	 */
	private String regionName;
	/*
	 * 日日顺中心编码
	 */
	private String corpDest;
	/*
	 * 日日顺库位码
	 */
	private String whCode;
	/*
	 * 电商库位码
	 */
	private String estorageId;
	/*
	 * 客户送达方
	 */
	private String custDest;
	/*
	 * 付款方编码
	 */
	private String mgCustCode;
	/*
	 * 付款方名称
	 */
	private String custName;
	
	private Integer transportPrescription;
	
	private String create_user; // 创建者
	private Date create_time; // 创建时间
	private String update_user; // 最后更新人
	private Timestamp update_time; // 最后更新时间
	private Integer is_enabled;//状态标志位
	public String getCorpCode() {
		return corpCode;
	}
	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getCorpDest() {
		return corpDest;
	}
	public void setCorpDest(String corpDest) {
		this.corpDest = corpDest;
	}
	public String getWhCode() {
		return whCode;
	}
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}
	public String getEstorageId() {
		return estorageId;
	}
	public void setEstorageId(String estorageId) {
		this.estorageId = estorageId;
	}
	public String getCustDest() {
		return custDest;
	}
	public void setCustDest(String custDest) {
		this.custDest = custDest;
	}
	public String getMgCustCode() {
		return mgCustCode;
	}
	public void setMgCustCode(String mgCustCode) {
		this.mgCustCode = mgCustCode;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	public Integer getIs_enabled() {
		return is_enabled;
	}
	public void setIs_enabled(Integer is_enabled) {
		this.is_enabled = is_enabled;
	}
	public Integer getTransportPrescription() {
		return transportPrescription;
	}
	public void setTransportPrescription(Integer transportPrescription) {
		this.transportPrescription = transportPrescription;
	}
	
	
}
