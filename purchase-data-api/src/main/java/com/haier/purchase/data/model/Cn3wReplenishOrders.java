package com.haier.purchase.data.model;

import java.io.Serializable;
import java.util.Date;

public class Cn3wReplenishOrders implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1923399912738026168L;
	
	private Long id;
	/**
	 * 物流云上记录的主键
	 */
	private String messageId;
	/**
	 * 菜鸟货品id
	 */
	private String sc_item_id;
	/**
	 * 海尔货品编码
	 */
	private String sc_item_code;
	/**
	 * 货品名称
	 */
	private String sc_item_name;
	/**
	 * 补货单号
	 */
	private String repl_no;
	/**
	 * wp单号
	 */
	private String wpOrderId;
	/**
	 * 85单号
	 */
	private String entryOrderCode;
	/**
	 * lbx号
	 */
	private String entryOrderId;
	/**
	 * 天猫单据状态 0: 待小二审核,1审核通过,2:审核不通过,3:未确认导入,4:未提交,8:已提交,9:已关闭
	 */
	private Integer status;
	/**
	 * 计划补货数量
	 */
	private Integer plan_repl_qty;
	/**
	 * 建议补货数量
	 */
	private Integer suggest_qty;
	/**
	 * 确认补货数量
	 */
	private Integer confirm_repl_qty;
	/**
	 * 最晚入库时间
	 */
	private Date deadline;
	/**
	 * 确认最晚入库时间
	 */
	private Date confirm_deadline;
	/**
	 * 基地仓编码
	 */
	private String from_store_code;
	/**
	 * 基地仓名称
	 */
	private String from_store_name;
	/**
	 * 仓库编码
	 */
	private String store_code;
	/**
	 * 仓库名称
	 */
	private String store_name;
	/**
	 * 补货单状态
	 */
	private Integer state;
	/**
	 * 创建时间
	 */
	private Date gmt_create;
	/**
	 * 修改时间
	 */
	private Date gmt_modified;
	/**
	 * 补货单据类型 1:补货,2:调拨,3:越库
	 */
	private Integer repl_order_type;
	/**
	 * 运输方式 direct:直配,cross:越库,union:统配,direct_rep:直入补货,transfer:调拨
	 */
	private String transport_type;
	/**
	 * 联系人
	 */
	private String contact_name;
	/**
	 * 联系电话
	 */
	private String contact_phone;
	/**
	 * 错误码
	 */
	private String errorCode;
	/**
	 * 错误信息
	 */
	private String errorMsg;
	/**
	 * 行业
	 */
	private Integer market_type;
	/**
	 * 小二备注
	 */
	private String xiaoer_memo;
	/**
	 * 系统备注
	 */
	private String system_memo;
	/**
	 * 记录生成时间
	 */
	private Date insertTime;
	/**
	 * 记录修改时间
	 */
	private Date modifyTime;
	/**
	 * 尝试次数
	 */
	private Integer trycount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getSc_item_id() {
		return sc_item_id;
	}
	public void setSc_item_id(String sc_item_id) {
		this.sc_item_id = sc_item_id;
	}
	public String getSc_item_code() {
		return sc_item_code;
	}
	public void setSc_item_code(String sc_item_code) {
		this.sc_item_code = sc_item_code;
	}
	public String getSc_item_name() {
		return sc_item_name;
	}
	public void setSc_item_name(String sc_item_name) {
		this.sc_item_name = sc_item_name;
	}
	public String getRepl_no() {
		return repl_no;
	}
	public void setRepl_no(String repl_no) {
		this.repl_no = repl_no;
	}
	public String getWpOrderId() {
		return wpOrderId;
	}
	public void setWpOrderId(String wpOrderId) {
		this.wpOrderId = wpOrderId;
	}
	public String getEntryOrderCode() {
		return entryOrderCode;
	}
	public void setEntryOrderCode(String entryOrderCode) {
		this.entryOrderCode = entryOrderCode;
	}
	public String getEntryOrderId() {
		return entryOrderId;
	}
	public void setEntryOrderId(String entryOrderId) {
		this.entryOrderId = entryOrderId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getPlan_repl_qty() {
		return plan_repl_qty;
	}
	public void setPlan_repl_qty(Integer plan_repl_qty) {
		this.plan_repl_qty = plan_repl_qty;
	}
	public Integer getSuggest_qty() {
		return suggest_qty;
	}
	public void setSuggest_qty(Integer suggest_qty) {
		this.suggest_qty = suggest_qty;
	}
	public Integer getConfirm_repl_qty() {
		return confirm_repl_qty;
	}
	public void setConfirm_repl_qty(Integer confirm_repl_qty) {
		this.confirm_repl_qty = confirm_repl_qty;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public Date getConfirm_deadline() {
		return confirm_deadline;
	}
	public void setConfirm_deadline(Date confirm_deadline) {
		this.confirm_deadline = confirm_deadline;
	}
	public String getFrom_store_code() {
		return from_store_code;
	}
	public void setFrom_store_code(String from_store_code) {
		this.from_store_code = from_store_code;
	}
	public String getFrom_store_name() {
		return from_store_name;
	}
	public void setFrom_store_name(String from_store_name) {
		this.from_store_name = from_store_name;
	}
	public String getStore_code() {
		return store_code;
	}
	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getGmt_create() {
		return gmt_create;
	}
	public void setGmt_create(Date gmt_create) {
		this.gmt_create = gmt_create;
	}
	public Date getGmt_modified() {
		return gmt_modified;
	}
	public void setGmt_modified(Date gmt_modified) {
		this.gmt_modified = gmt_modified;
	}
	public Integer getRepl_order_type() {
		return repl_order_type;
	}
	public void setRepl_order_type(Integer repl_order_type) {
		this.repl_order_type = repl_order_type;
	}
	public String getTransport_type() {
		return transport_type;
	}
	public void setTransport_type(String transport_type) {
		this.transport_type = transport_type;
	}
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getContact_phone() {
		return contact_phone;
	}
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Integer getMarket_type() {
		return market_type;
	}
	public void setMarket_type(Integer market_type) {
		this.market_type = market_type;
	}
	public String getXiaoer_memo() {
		return xiaoer_memo;
	}
	public void setXiaoer_memo(String xiaoer_memo) {
		this.xiaoer_memo = xiaoer_memo;
	}
	public String getSystem_memo() {
		return system_memo;
	}
	public void setSystem_memo(String system_memo) {
		this.system_memo = system_memo;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getTrycount() {
		return trycount;
	}
	public void setTrycount(Integer trycount) {
		this.trycount = trycount;
	}
}
