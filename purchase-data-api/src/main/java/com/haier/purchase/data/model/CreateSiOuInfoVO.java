package com.haier.purchase.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CreateSiOuInfoVO implements Serializable {

	private String	   siOuSlipNo;
	private String	   siOuSlipLineNo;
	private String	   siSlipNo;
	private String	   siSlipLineNo;
	private String	   ouSlipNo;
	private String	   ouSlipLineNo;
	private String	   slip_date;
	private String	   companyId;
	private String	   mdm_partsCode;
	private String	   quantity;
	private BigDecimal taxIncludeAmount;
	private BigDecimal taxExcludeAmount;
	private String	   rejectFlag;
	private String	   custom_purchase_id;
	private int		   billType;
	private String	   source_order_id;

	//private String order_id;
	//private String si_ou_slipNo;
	//private String si_ou_slipLineNo;
	//private String bill_type;
	private String	   company_id;
	private String	   busi_company_id;
	private String	   leave_warehouse_time;
	private String	   location_code;
	private String	   si_ou_status;
	//private String tax_include_amount;
	private String	   tax_exclude_amount;
	private String	   receipt_quantity;
	private String	   invoice_no;
	private String	   sys_invoice_date;
	private String	   invoice_status;
	private String	   delete_date;
	private int		   flow_flag;

	private String	   cust_unit_code;
	private String	   address_id;
	private String	   supplier;

	public String getSiOuSlipNo() {
		return siOuSlipNo;
	}

	public void setSiOuSlipNo(String siOuSlipNo) {
		this.siOuSlipNo = siOuSlipNo;
	}

	public String getSiOuSlipLineNo() {
		return siOuSlipLineNo;
	}

	public void setSiOuSlipLineNo(String siOuSlipLineNo) {
		this.siOuSlipLineNo = siOuSlipLineNo;
	}

	public String getSiSlipNo() {
		return siSlipNo;
	}

	public void setSiSlipNo(String siSlipNo) {
		this.siSlipNo = siSlipNo;
	}

	public String getSiSlipLineNo() {
		return siSlipLineNo;
	}

	public void setSiSlipLineNo(String siSlipLineNo) {
		this.siSlipLineNo = siSlipLineNo;
	}

	public String getOuSlipNo() {
		return ouSlipNo;
	}

	public void setOuSlipNo(String ouSlipNo) {
		this.ouSlipNo = ouSlipNo;
	}

	public String getOuSlipLineNo() {
		return ouSlipLineNo;
	}

	public void setOuSlipLineNo(String ouSlipLineNo) {
		this.ouSlipLineNo = ouSlipLineNo;
	}

	public String getSlip_date() {
		return slip_date;
	}

	public void setSlip_date(String slip_date) {
		this.slip_date = slip_date;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getMdm_partsCode() {
		return mdm_partsCode;
	}

	public void setMdm_partsCode(String mdm_partsCode) {
		this.mdm_partsCode = mdm_partsCode;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getRejectFlag() {
		return rejectFlag;
	}

	public void setRejectFlag(String rejectFlag) {
		this.rejectFlag = rejectFlag;
	}

	public String getCustom_purchase_id() {
		return custom_purchase_id;
	}

	public void setCustom_purchase_id(String custom_purchase_id) {
		this.custom_purchase_id = custom_purchase_id;
	}

	public int getBillType() {
		return billType;
	}

	public void setBillType(int billType) {
		this.billType = billType;
	}

	public String getSource_order_id() {
		return source_order_id;
	}

	public void setSource_order_id(String source_order_id) {
		this.source_order_id = source_order_id;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getBusi_company_id() {
		return busi_company_id;
	}

	public void setBusi_company_id(String busi_company_id) {
		this.busi_company_id = busi_company_id;
	}

	public String getLeave_warehouse_time() {
		return leave_warehouse_time;
	}

	public void setLeave_warehouse_time(String leave_warehouse_time) {
		this.leave_warehouse_time = leave_warehouse_time;
	}

	public String getLocation_code() {
		return location_code;
	}

	public void setLocation_code(String location_code) {
		this.location_code = location_code;
	}

	public String getSi_ou_status() {
		return si_ou_status;
	}

	public void setSi_ou_status(String si_ou_status) {
		this.si_ou_status = si_ou_status;
	}

	public String getTax_exclude_amount() {
		return tax_exclude_amount;
	}

	public void setTax_exclude_amount(String tax_exclude_amount) {
		this.tax_exclude_amount = tax_exclude_amount;
	}

	public String getReceipt_quantity() {
		return receipt_quantity;
	}

	public void setReceipt_quantity(String receipt_quantity) {
		this.receipt_quantity = receipt_quantity;
	}

	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

	public String getSys_invoice_date() {
		return sys_invoice_date;
	}

	public void setSys_invoice_date(String sys_invoice_date) {
		this.sys_invoice_date = sys_invoice_date;
	}

	public String getInvoice_status() {
		return invoice_status;
	}

	public void setInvoice_status(String invoice_status) {
		this.invoice_status = invoice_status;
	}

	public String getDelete_date() {
		return delete_date;
	}

	public void setDelete_date(String delete_date) {
		this.delete_date = delete_date;
	}

	public int getFlow_flag() {
		return flow_flag;
	}

	public void setFlow_flag(int flow_flag) {
		this.flow_flag = flow_flag;
	}

	public String getCust_unit_code() {
		return cust_unit_code;
	}

	public void setCust_unit_code(String cust_unit_code) {
		this.cust_unit_code = cust_unit_code;
	}

	public String getAddress_id() {
		return address_id;
	}

	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}

	public BigDecimal getTaxIncludeAmount() {
		return taxIncludeAmount;
	}

	public void setTaxIncludeAmount(BigDecimal taxIncludeAmount) {
		this.taxIncludeAmount = taxIncludeAmount;
	}

	public BigDecimal getTaxExcludeAmount() {
		return taxExcludeAmount;
	}

	public void setTaxExcludeAmount(BigDecimal taxExcludeAmount) {
		this.taxExcludeAmount = taxExcludeAmount;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public static class Result {
		private String siSlipNo;
		private String siSlipLineNo;
		private String ouSlipNo;
		private String ouSlipLineNo;
		private String transmitFlag;
		private String message;

		public String getSiSlipNo() {
			return siSlipNo;
		}

		public void setSiSlipNo(String siSlipNo) {
			this.siSlipNo = siSlipNo;
		}

		public String getSiSlipLineNo() {
			return siSlipLineNo;
		}

		public void setSiSlipLineNo(String siSlipLineNo) {
			this.siSlipLineNo = siSlipLineNo;
		}

		public String getOuSlipNo() {
			return ouSlipNo;
		}

		public void setOuSlipNo(String ouSlipNo) {
			this.ouSlipNo = ouSlipNo;
		}

		public String getOuSlipLineNo() {
			return ouSlipLineNo;
		}

		public void setOuSlipLineNo(String ouSlipLineNo) {
			this.ouSlipLineNo = ouSlipLineNo;
		}

		public String getTransmitFlag() {
			return transmitFlag;
		}

		public void setTransmitFlag(String transmitFlag) {
			this.transmitFlag = transmitFlag;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

	}
}
