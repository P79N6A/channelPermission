package com.haier.purchase.data.model;

import java.io.Serializable;
import java.util.List;

public class PurchaseT2AuditResultFromCGO implements Serializable {

	/**
	 *Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	private Integer			  quantity;
	private double			  price;
	private String			  storage_id;
	private String			  source_order_id;
	private String			  splitted_order_id;
	private Integer			  audit_result;
	private String			  audit_time;
	private String			  remark;
	private String			  materials_id;

	private String			  supplier;
	private String			  factory_id;
	private String			  factory_name;
	private String			  latest_leavebase_time;

	private String			  prescription;
	private String			  request_arrive_date;
	private String			  order_type;
	private List<String>	  order_types;

	public String getMaterials_id() {
		return materials_id;
	}

	public void setMaterials_id(String materials_id) {
		this.materials_id = materials_id;
	}

	public String getSource_order_id() {
		return source_order_id;
	}

	public void setSource_order_id(String source_order_id) {
		this.source_order_id = source_order_id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStorage_id() {
		return storage_id;
	}

	public void setStorage_id(String storage_id) {
		this.storage_id = storage_id;
	}

	public String getSplitted_order_id() {
		return splitted_order_id;
	}

	public void setSplitted_order_id(String splitted_order_id) {
		this.splitted_order_id = splitted_order_id;
	}

	public Integer getAudit_result() {
		return audit_result;
	}

	public void setAudit_result(Integer audit_result) {
		this.audit_result = audit_result;
	}

	public String getAudit_time() {
		return audit_time;
	}

	public void setAudit_time(String audit_time) {
		this.audit_time = audit_time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getFactory_id() {
		return factory_id;
	}

	public void setFactory_id(String factory_id) {
		this.factory_id = factory_id;
	}

	public String getFactory_name() {
		return factory_name;
	}

	public void setFactory_name(String factory_name) {
		this.factory_name = factory_name;
	}

	public String getLatest_leavebase_time() {
		return latest_leavebase_time;
	}

	public void setLatest_leavebase_time(String latest_leavebase_time) {
		this.latest_leavebase_time = latest_leavebase_time;
	}

	public String getPrescription() {
		return prescription;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	public String getRequest_arrive_date() {
		return request_arrive_date;
	}

	public void setRequest_arrive_date(String request_arrive_date) {
		this.request_arrive_date = request_arrive_date;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public List<String> getOrder_types() {
		return order_types;
	}

	public void setOrder_types(List<String> order_types) {
		this.order_types = order_types;
	}
}
