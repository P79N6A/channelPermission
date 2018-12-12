/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *                       
 * @Filename: CrmOrderManualItem.java
 * @Version: 1.0
 * @Author: yanrp 燕如朋
 * @Email: yanrp110428@dhc.com.cn
 *
 */
public class CrmOrderManualItem implements Serializable {

	/**
	 *Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -1712031528331516822L;
	private String			  wp_order_id;							   //销售单号
	private String			  corpCode;								   //分公司编码
	private String			  regionId;								   //工贸编码
	private String			  billType;								   //订单类型
	private Integer			  sap_flow_num;							   //配送方式
	private String			  custCode;								   //付款方编码
	private String			  destCode;								   //送达方编码
	private String			  whCode;								   //日日顺仓库编码
	private String			  custMgr;								   //客户经理
	private String			  porMgr;								   //产品经理
	private String			  proDputy;								   //产品代表
	private Timestamp		  planInDate;							   //预计到货日期
	private String			  budgetSort;							   //预算种类
	private String			  budgetOrg;							   //预算体
	private String			  budgetOrgName;						   //预算体名称
	private String			  saleOrgCode;							   //销售组织编码
	private String			  maker;								   //开单人
	private String			  releBillCode;							   //关联单号
	private Integer			  sysFlag;								   //系统标识码
	private String			  destCenter;							   //配送中心编码
	private Integer			  stock_type;							   //库存类型
	private String			  les_regionId;							   //LES工贸编码
	private String			  create_user;							   //创建人
	private Timestamp		  create_time;							   //创建时间
	private String			  modify_user;							   //修改人
	private Timestamp		  modify_time;							   //修改时间
	private String			  delete_user;							   //删除人
	private Timestamp		  delete_time;							   //删除时间
	private Integer			  delete_flag;							   //删除状态
	private String			  audit_user;							   //审核人
	private Timestamp		  audit_time;							   //审核时间
	private String			  audit_remark;							   //审核意见
	private Integer			  flow_flag;							   //流程标志
	private String			  error_msg;							   //同步错误
	private String			  planInDateStr;						   //预计到货日期
	private String			  estorge_id;							   //WA库位码
	private String			  estorge_name;							   //WA库位名
	private String			  corpName;								   //分公司名称
	private String			  industry_trade_desc;					   //工贸描述
	private String			  payment_name;							   //付款方名称
	private String			  esale_name;							   //送达方名称
	private String			  rrs_distribution_name;				   //配送中心名称
	private String			  source_order_id;						   //来源单号

	private String			  mustHaveDNorSO;						   //更新状态使用，如果不为空，则只有SO或者DN有值时才更新

	/**
	 * @return Returns the wp_order_id
	 */
	public String getWp_order_id() {
		return wp_order_id;
	}

	/**
	 * @param wp_order_id
	 * The wp_order_id to set.
	 */
	public void setWp_order_id(String wp_order_id) {
		this.wp_order_id = wp_order_id;
	}

	/**
	 * @return Returns the corpCode
	 */
	public String getCorpCode() {
		return corpCode;
	}

	/**
	 * @param corpCode
	 * The corpCode to set.
	 */
	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	/**
	 * @return Returns the regionId
	 */
	public String getRegionId() {
		return regionId;
	}

	/**
	 * @param regionId
	 * The regionId to set.
	 */
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	/**
	 * @return Returns the billType
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @param billType
	 * The billType to set.
	 */
	public void setBillType(String billType) {
		this.billType = billType;
	}

	/**
	 * @return Returns the sap_flow_num
	 */
	public Integer getSap_flow_num() {
		return sap_flow_num;
	}

	/**
	 * @param sap_flow_num
	 * The sap_flow_num to set.
	 */
	public void setSap_flow_num(Integer sap_flow_num) {
		this.sap_flow_num = sap_flow_num;
	}

	/**
	 * @return Returns the custCode
	 */
	public String getCustCode() {
		return custCode;
	}

	/**
	 * @param custCode
	 * The custCode to set.
	 */
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}

	/**
	 * @return Returns the destCode
	 */
	public String getDestCode() {
		return destCode;
	}

	/**
	 * @param destCode
	 * The destCode to set.
	 */
	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}

	/**
	 * @return Returns the whCode
	 */
	public String getWhCode() {
		return whCode;
	}

	/**
	 * @param whCode
	 * The whCode to set.
	 */
	public void setWhCode(String whCode) {
		this.whCode = whCode;
	}

	/**
	 * @return Returns the custMgr
	 */
	public String getCustMgr() {
		return custMgr;
	}

	/**
	 * @param custMgr
	 * The custMgr to set.
	 */
	public void setCustMgr(String custMgr) {
		this.custMgr = custMgr;
	}

	/**
	 * @return Returns the porMgr
	 */
	public String getPorMgr() {
		return porMgr;
	}

	/**
	 * @param porMgr
	 * The porMgr to set.
	 */
	public void setPorMgr(String porMgr) {
		this.porMgr = porMgr;
	}

	/**
	 * @return Returns the proDputy
	 */
	public String getProDputy() {
		return proDputy;
	}

	/**
	 * @param proDputy
	 * The proDputy to set.
	 */
	public void setProDputy(String proDputy) {
		this.proDputy = proDputy;
	}

	/**
	 * @return Returns the planInDate
	 */
	public Timestamp getPlanInDate() {
		return planInDate;
	}

	/**
	 * @param planInDate
	 * The planInDate to set.
	 */
	public void setPlanInDate(Timestamp planInDate) {
		this.planInDate = planInDate;
	}

	/**
	 * @return Returns the budgetSort
	 */
	public String getBudgetSort() {
		return budgetSort;
	}

	/**
	 * @param budgetSort
	 * The budgetSort to set.
	 */
	public void setBudgetSort(String budgetSort) {
		this.budgetSort = budgetSort;
	}

	/**
	 * @return Returns the budgetOrg
	 */
	public String getBudgetOrg() {
		return budgetOrg;
	}

	/**
	 * @param budgetOrg
	 * The budgetOrg to set.
	 */
	public void setBudgetOrg(String budgetOrg) {
		this.budgetOrg = budgetOrg;
	}

	/**
	 * @return Returns the budgetOrgName
	 */
	public String getBudgetOrgName() {
		return budgetOrgName;
	}

	/**
	 * @param budgetOrgName
	 * The budgetOrgName to set.
	 */
	public void setBudgetOrgName(String budgetOrgName) {
		this.budgetOrgName = budgetOrgName;
	}

	/**
	 * @return Returns the saleOrgCode
	 */
	public String getSaleOrgCode() {
		return saleOrgCode;
	}

	/**
	 * @param saleOrgCode
	 * The saleOrgCode to set.
	 */
	public void setSaleOrgCode(String saleOrgCode) {
		this.saleOrgCode = saleOrgCode;
	}

	/**
	 * @return Returns the maker
	 */
	public String getMaker() {
		return maker;
	}

	/**
	 * @param maker
	 * The maker to set.
	 */
	public void setMaker(String maker) {
		this.maker = maker;
	}

	/**
	 * @return Returns the releBillCode
	 */
	public String getReleBillCode() {
		return releBillCode;
	}

	/**
	 * @param releBillCode
	 * The releBillCode to set.
	 */
	public void setReleBillCode(String releBillCode) {
		this.releBillCode = releBillCode;
	}

	/**
	 * @return Returns the sysFlag
	 */
	public Integer getSysFlag() {
		return sysFlag;
	}

	/**
	 * @param sysFlag
	 * The sysFlag to set.
	 */
	public void setSysFlag(Integer sysFlag) {
		this.sysFlag = sysFlag;
	}

	/**
	 * @return Returns the destCenter
	 */
	public String getDestCenter() {
		return destCenter;
	}

	/**
	 * @param destCenter
	 * The destCenter to set.
	 */
	public void setDestCenter(String destCenter) {
		this.destCenter = destCenter;
	}

	/**
	 * @return Returns the stock_type
	 */
	public Integer getStock_type() {
		return stock_type;
	}

	/**
	 * @param stock_type
	 * The stock_type to set.
	 */
	public void setStock_type(Integer stock_type) {
		this.stock_type = stock_type;
	}

	/**
	 * @return Returns the les_regionId
	 */
	public String getLes_regionId() {
		return les_regionId;
	}

	/**
	 * @param les_regionId
	 * The les_regionId to set.
	 */
	public void setLes_regionId(String les_regionId) {
		this.les_regionId = les_regionId;
	}

	/**
	 * @return Returns the create_user
	 */
	public String getCreate_user() {
		return create_user;
	}

	/**
	 * @param create_user
	 * The create_user to set.
	 */
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	/**
	 * @return Returns the create_time
	 */
	public Timestamp getCreate_time() {
		return create_time;
	}

	/**
	 * @param create_time
	 * The create_time to set.
	 */
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	/**
	 * @return Returns the modify_user
	 */
	public String getModify_user() {
		return modify_user;
	}

	/**
	 * @param modify_user
	 * The modify_user to set.
	 */
	public void setModify_user(String modify_user) {
		this.modify_user = modify_user;
	}

	/**
	 * @return Returns the modify_time
	 */
	public Timestamp getModify_time() {
		return modify_time;
	}

	/**
	 * @param modify_time
	 * The modify_time to set.
	 */
	public void setModify_time(Timestamp modify_time) {
		this.modify_time = modify_time;
	}

	/**
	 * @return Returns the delete_user
	 */
	public String getDelete_user() {
		return delete_user;
	}

	/**
	 * @param delete_user
	 * The delete_user to set.
	 */
	public void setDelete_user(String delete_user) {
		this.delete_user = delete_user;
	}

	/**
	 * @return Returns the delete_time
	 */
	public Timestamp getDelete_time() {
		return delete_time;
	}

	/**
	 * @param delete_time
	 * The delete_time to set.
	 */
	public void setDelete_time(Timestamp delete_time) {
		this.delete_time = delete_time;
	}

	/**
	 * @return Returns the delete_flag
	 */
	public Integer getDelete_flag() {
		return delete_flag;
	}

	/**
	 * @param delete_flag
	 * The delete_flag to set.
	 */
	public void setDelete_flag(Integer delete_flag) {
		this.delete_flag = delete_flag;
	}

	/**
	 * @return Returns the audit_user
	 */
	public String getAudit_user() {
		return audit_user;
	}

	/**
	 * @param audit_user
	 * The audit_user to set.
	 */
	public void setAudit_user(String audit_user) {
		this.audit_user = audit_user;
	}

	/**
	 * @return Returns the audit_time
	 */
	public Timestamp getAudit_time() {
		return audit_time;
	}

	/**
	 * @param audit_time
	 * The audit_time to set.
	 */
	public void setAudit_time(Timestamp audit_time) {
		this.audit_time = audit_time;
	}

	/**
	 * @return Returns the audit_remark
	 */
	public String getAudit_remark() {
		return audit_remark;
	}

	/**
	 * @param audit_remark
	 * The audit_remark to set.
	 */
	public void setAudit_remark(String audit_remark) {
		this.audit_remark = audit_remark;
	}

	/**
	 * @return Returns the flow_flag
	 */
	public Integer getFlow_flag() {
		return flow_flag;
	}

	/**
	 * @param flow_flag
	 * The flow_flag to set.
	 */
	public void setFlow_flag(Integer flow_flag) {
		this.flow_flag = flow_flag;
	}

	/**
	 * @return Returns the error_msg
	 */
	public String getError_msg() {
		return error_msg;
	}

	/**
	 * @param error_msg
	 * The error_msg to set.
	 */
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	/**
	 * @return Returns the planInDateStr
	 */
	public String getPlanInDateStr() {
		return planInDateStr;
	}

	/**
	 * @param planInDateStr
	 * The planInDateStr to set.
	 */
	public void setPlanInDateStr(String planInDateStr) {
		this.planInDateStr = planInDateStr;
	}

	/**
	 * @return Returns the estorge_id
	 */
	public String getEstorge_id() {
		return estorge_id;
	}

	/**
	 * @param estorge_id
	 * The estorge_id to set.
	 */
	public void setEstorge_id(String estorge_id) {
		this.estorge_id = estorge_id;
	}

	/**
	 * @return Returns the estorge_name
	 */
	public String getEstorge_name() {
		return estorge_name;
	}

	/**
	 * @param estorge_name
	 * The estorge_name to set.
	 */
	public void setEstorge_name(String estorge_name) {
		this.estorge_name = estorge_name;
	}

	/**
	 * @return Returns the corpName
	 */
	public String getCorpName() {
		return corpName;
	}

	/**
	 * @param corpName
	 * The corpName to set.
	 */
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	/**
	 * @return Returns the industry_trade_desc
	 */
	public String getIndustry_trade_desc() {
		return industry_trade_desc;
	}

	/**
	 * @param industry_trade_desc
	 * The industry_trade_desc to set.
	 */
	public void setIndustry_trade_desc(String industry_trade_desc) {
		this.industry_trade_desc = industry_trade_desc;
	}

	/**
	 * @return Returns the payment_name
	 */
	public String getPayment_name() {
		return payment_name;
	}

	/**
	 * @param payment_name
	 * The payment_name to set.
	 */
	public void setPayment_name(String payment_name) {
		this.payment_name = payment_name;
	}

	/**
	 * @return Returns the esale_name
	 */
	public String getEsale_name() {
		return esale_name;
	}

	/**
	 * @param esale_name
	 * The esale_name to set.
	 */
	public void setEsale_name(String esale_name) {
		this.esale_name = esale_name;
	}

	/**
	 * @return Returns the rrs_distribution_name
	 */
	public String getRrs_distribution_name() {
		return rrs_distribution_name;
	}

	/**
	 * @param rrs_distribution_name
	 * The rrs_distribution_name to set.
	 */
	public void setRrs_distribution_name(String rrs_distribution_name) {
		this.rrs_distribution_name = rrs_distribution_name;
	}

	public String getSource_order_id() {
		return source_order_id;
	}

	public void setSource_order_id(String source_order_id) {
		this.source_order_id = source_order_id;
	}

	public String getMustHaveDNorSO() {
		return mustHaveDNorSO;
	}

	public void setMustHaveDNorSO(String mustHaveDNorSO) {
		this.mustHaveDNorSO = mustHaveDNorSO;
	}

}
