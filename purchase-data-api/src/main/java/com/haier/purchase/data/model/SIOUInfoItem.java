/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *                       
 * @Filename: SIOUInfoItem.java
 * @Version: 1.0
 * @Author: yanrp 燕如朋
 * @Email: yanrp110428@dhc.com.cn
 *
 */
public class SIOUInfoItem implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1787425179000548490L;
    private String            order_id;                               //拆分订单号
    private String            source_order_id;                        //来源订单号
    private String            si_ou_slipNo;                           //SI/OU单号
    private String            si_ou_slipLineNo;                       //SI/OU单项次
    private Integer           bill_type;                              //类型
    private Date              slip_date;                              //单据日期  
    private String            slip_date_display;                      //单据日期显示
    private String            company_id;                             //单据公司
    private String            mdm_partsCode;                          //MDM物料编码
    private Integer           quantity;                               //数量
    private Float             taxIncludeAmount;                       //行项目未税金额
    private Integer           rejectFlag;                             //拒收标识
    private String            busi_company_id;                        //业务公司
    private Date              leave_warehouse_time;                   //出库时间
    private String            location_code;                          //发运库
    private String            si_ou_status;                           //单据状态
    private Float             tax_include_amount;                     //含税金额
    private Float             tax_exclude_amount;                     //未税金额
    private Integer           receipt_quantity;                       //发票数量
    private String            invoice_no;                             //金税发票号
    private Date              sys_invoice_date;                       //系统发票日期
    private Integer           invoice_status;                         //发票状态
    private Date              delete_date;                            //发票作废日期
    private Integer           flow_flag;                              //流程标识
    private Date              wa_in_time;                             //入WA库时间
    private String            audit_result;                           //评审结果
    private String            audit_result_name;                      //评审结果名称
    private String            audit_time_display;                     //评审时间
    private String            remark;                                 //备注

    private String            rrs_out_time_display;                   //入日日顺库日期  
    private String            wa_in_time_display;                     //入wa库日期    
    private String            bill_time_display;                      //提单日期    
    private String            product_group_id;                       //产品组
    private String            product_group_name;                     //产品组名称
    private String            flow_flag_name;                         //流程标识名称               
    private String            ed_channel_id;                          //渠道(ed_channel_id)
    private String            ed_channel_name;                        //渠道名称
    private String            brand_id;                               //品牌
    private String            materials_desc;                         //物料描述
    private String            category_id;                            //品类
    private String            materials_id;                           //物料号              
    private BigDecimal        price;                                  //样表单价
    private BigDecimal        t2_amount;                              //样表金额
    private BigDecimal        amount;                                 //采购金额
    private String 			  error_msg;							  //错误信息
    
    //xuelin.zhao start
    private Integer           release_flag;                           //0:未释放1:已释放
    private Date              syn_vom_time;                           //同步到VOM时间
    private String            syn_vom_time_display;                   //同步到VOM时间显示
    private Date              reverse_syn_vom_time;                   //逆向同步到VOM时间
    private String            reverse_syn_vom_time_display;           //逆向同步到VOM时间显示
    private Date              cancel_in_wa_time;                      //取消入WA库提单时间
    private String            cancel_in_wa_time_display;              //取消入WA库提单时间显示
    private Date              cancel_out_wa_time;                     //取消出WA库提单时间
    private String            cancel_out_wa_time_display;             //取消出WA库提单时间显示
    private String            vom_out_wa_no;                   		  //vom出wa单号
    private String            vom_reverse_in_wa_no;                   //vom逆向入wa单号
    //end
    
    public Integer getRelease_flag() {
		return release_flag;
	}

	public void setRelease_flag(Integer release_flag) {
		this.release_flag = release_flag;
	}

	public Date getSyn_vom_time() {
		return syn_vom_time;
	}

	public void setSyn_vom_time(Date syn_vom_time) {
		this.syn_vom_time = syn_vom_time;
	}

	public String getSyn_vom_time_display() {
		return syn_vom_time_display;
	}

	public void setSyn_vom_time_display(String syn_vom_time_display) {
		this.syn_vom_time_display = syn_vom_time_display;
	}

	public Date getReverse_syn_vom_time() {
		return reverse_syn_vom_time;
	}

	public void setReverse_syn_vom_time(Date reverse_syn_vom_time) {
		this.reverse_syn_vom_time = reverse_syn_vom_time;
	}

	public String getReverse_syn_vom_time_display() {
		return reverse_syn_vom_time_display;
	}

	public void setReverse_syn_vom_time_display(String reverse_syn_vom_time_display) {
		this.reverse_syn_vom_time_display = reverse_syn_vom_time_display;
	}

	public Date getCancel_in_wa_time() {
		return cancel_in_wa_time;
	}

	public void setCancel_in_wa_time(Date cancel_in_wa_time) {
		this.cancel_in_wa_time = cancel_in_wa_time;
	}

	public String getCancel_in_wa_time_display() {
		return cancel_in_wa_time_display;
	}

	public void setCancel_in_wa_time_display(String cancel_in_wa_time_display) {
		this.cancel_in_wa_time_display = cancel_in_wa_time_display;
	}

	public Date getCancel_out_wa_time() {
		return cancel_out_wa_time;
	}

	public void setCancel_out_wa_time(Date cancel_out_wa_time) {
		this.cancel_out_wa_time = cancel_out_wa_time;
	}

	public String getCancel_out_wa_time_display() {
		return cancel_out_wa_time_display;
	}

	public void setCancel_out_wa_time_display(String cancel_out_wa_time_display) {
		this.cancel_out_wa_time_display = cancel_out_wa_time_display;
	}

	public String getVom_out_wa_no() {
		return vom_out_wa_no;
	}

	public void setVom_out_wa_no(String vom_out_wa_no) {
		this.vom_out_wa_no = vom_out_wa_no;
	}

	public String getVom_reverse_in_wa_no() {
		return vom_reverse_in_wa_no;
	}

	public void setVom_reverse_in_wa_no(String vom_reverse_in_wa_no) {
		this.vom_reverse_in_wa_no = vom_reverse_in_wa_no;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	/**
     * @return Returns the order_id
     */
    public String getOrder_id() {
        return order_id;
    }

    /**
     * @param order_id
     * The order_id to set.
     */
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    /**
     * @return Returns the source_order_id
     */
    public String getSource_order_id() {
        return source_order_id;
    }

    /**
     * @param source_order_id
     * The source_order_id to set.
     */
    public void setSource_order_id(String source_order_id) {
        this.source_order_id = source_order_id;
    }

    /**
     * @return Returns the si_ou_slipNo
     */
    public String getSi_ou_slipNo() {
        return si_ou_slipNo;
    }

    /**
     * @param si_ou_slipNo
     * The si_ou_slipNo to set.
     */
    public void setSi_ou_slipNo(String si_ou_slipNo) {
        this.si_ou_slipNo = si_ou_slipNo;
    }

    /**
     * @return Returns the si_ou_slipLineNo
     */
    public String getSi_ou_slipLineNo() {
        return si_ou_slipLineNo;
    }

    /**
     * @param si_ou_slipLineNo
     * The si_ou_slipLineNo to set.
     */
    public void setSi_ou_slipLineNo(String si_ou_slipLineNo) {
        this.si_ou_slipLineNo = si_ou_slipLineNo;
    }

    /**
     * @return Returns the bill_type
     */
    public Integer getBill_type() {
        return bill_type;
    }

    /**
     * @param bill_type
     * The bill_type to set.
     */
    public void setBill_type(Integer bill_type) {
        this.bill_type = bill_type;
    }

    /**
     * @return Returns the slip_date
     */
    public Date getSlip_date() {
        return slip_date;
    }

    /**
     * @param slip_date
     * The slip_date to set.
     */
    public void setSlip_date(Date slip_date) {
        this.slip_date = slip_date;
    }

    /**
     * @return Returns the slip_date_display
     */
    public String getSlip_date_display() {
        return slip_date_display;
    }

    /**
     * @param slip_date_display
     * The slip_date_display to set.
     */
    public void setSlip_date_display(String slip_date_display) {
        this.slip_date_display = slip_date_display;
    }

    /**
     * @return Returns the company_id
     */
    public String getCompany_id() {
        return company_id;
    }

    /**
     * @param company_id
     * The company_id to set.
     */
    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    /**
     * @return Returns the mdm_partsCode
     */
    public String getMdm_partsCode() {
        return mdm_partsCode;
    }

    /**
     * @param mdm_partsCode
     * The mdm_partsCode to set.
     */
    public void setMdm_partsCode(String mdm_partsCode) {
        this.mdm_partsCode = mdm_partsCode;
    }

    /**
     * @return Returns the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     * The quantity to set.
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return Returns the taxIncludeAmount
     */
    public Float getTaxIncludeAmount() {
        return taxIncludeAmount;
    }

    /**
     * @param taxIncludeAmount
     * The taxIncludeAmount to set.
     */
    public void setTaxIncludeAmount(Float taxIncludeAmount) {
        this.taxIncludeAmount = taxIncludeAmount;
    }

    /**
     * @return Returns the rejectFlag
     */
    public Integer getRejectFlag() {
        return rejectFlag;
    }

    /**
     * @param rejectFlag
     * The rejectFlag to set.
     */
    public void setRejectFlag(Integer rejectFlag) {
        this.rejectFlag = rejectFlag;
    }

    /**
     * @return Returns the busi_company_id
     */
    public String getBusi_company_id() {
        return busi_company_id;
    }

    /**
     * @param busi_company_id
     * The busi_company_id to set.
     */
    public void setBusi_company_id(String busi_company_id) {
        this.busi_company_id = busi_company_id;
    }

    /**
     * @return Returns the leave_warehouse_time
     */
    public Date getLeave_warehouse_time() {
        return leave_warehouse_time;
    }

    /**
     * @param leave_warehouse_time
     * The leave_warehouse_time to set.
     */
    public void setLeave_warehouse_time(Date leave_warehouse_time) {
        this.leave_warehouse_time = leave_warehouse_time;
    }

    /**
     * @return Returns the location_code
     */
    public String getLocation_code() {
        return location_code;
    }

    /**
     * @param location_code
     * The location_code to set.
     */
    public void setLocation_code(String location_code) {
        this.location_code = location_code;
    }

    /**
     * @return Returns the si_ou_status
     */
    public String getSi_ou_status() {
        return si_ou_status;
    }

    /**
     * @param si_ou_status
     * The si_ou_status to set.
     */
    public void setSi_ou_status(String si_ou_status) {
        this.si_ou_status = si_ou_status;
    }

    /**
     * @return Returns the tax_include_amount
     */
    public Float getTax_include_amount() {
        return tax_include_amount;
    }

    /**
     * @param tax_include_amount
     * The tax_include_amount to set.
     */
    public void setTax_include_amount(Float tax_include_amount) {
        this.tax_include_amount = tax_include_amount;
    }

    /**
     * @return Returns the tax_exclude_amount
     */
    public Float getTax_exclude_amount() {
        return tax_exclude_amount;
    }

    /**
     * @param tax_exclude_amount
     * The tax_exclude_amount to set.
     */
    public void setTax_exclude_amount(Float tax_exclude_amount) {
        this.tax_exclude_amount = tax_exclude_amount;
    }

    /**
     * @return Returns the receipt_quantity
     */
    public Integer getReceipt_quantity() {
        return receipt_quantity;
    }

    /**
     * @param receipt_quantity
     * The receipt_quantity to set.
     */
    public void setReceipt_quantity(Integer receipt_quantity) {
        this.receipt_quantity = receipt_quantity;
    }

    /**
     * @return Returns the invoice_no
     */
    public String getInvoice_no() {
        return invoice_no;
    }

    /**
     * @param invoice_no
     * The invoice_no to set.
     */
    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    /**
     * @return Returns the sys_invoice_date
     */
    public Date getSys_invoice_date() {
        return sys_invoice_date;
    }

    /**
     * @param sys_invoice_date
     * The sys_invoice_date to set.
     */
    public void setSys_invoice_date(Date sys_invoice_date) {
        this.sys_invoice_date = sys_invoice_date;
    }

    /**
     * @return Returns the invoice_status
     */
    public Integer getInvoice_status() {
        return invoice_status;
    }

    /**
     * @param invoice_status
     * The invoice_status to set.
     */
    public void setInvoice_status(Integer invoice_status) {
        this.invoice_status = invoice_status;
    }

    /**
     * @return Returns the delete_date
     */
    public Date getDelete_date() {
        return delete_date;
    }

    /**
     * @param delete_date
     * The delete_date to set.
     */
    public void setDelete_date(Date delete_date) {
        this.delete_date = delete_date;
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
     * @return Returns the wa_in_time
     */
    public Date getWa_in_time() {
        return wa_in_time;
    }

    /**
     * @param wa_in_time
     * The wa_in_time to set.
     */
    public void setWa_in_time(Date wa_in_time) {
        this.wa_in_time = wa_in_time;
    }

    /**
     * @return Returns the audit_result
     */
    public String getAudit_result() {
        return audit_result;
    }

    /**
     * @param audit_result
     * The audit_result to set.
     */
    public void setAudit_result(String audit_result) {
        this.audit_result = audit_result;
    }

    /**
     * @return Returns the audit_result_name
     */
    public String getAudit_result_name() {
        return audit_result_name;
    }

    /**
     * @param audit_result_name
     * The audit_result_name to set.
     */
    public void setAudit_result_name(String audit_result_name) {
        this.audit_result_name = audit_result_name;
    }

    /**
     * @return Returns the audit_time_display
     */
    public String getAudit_time_display() {
        return audit_time_display;
    }

    /**
     * @param audit_time_display
     * The audit_time_display to set.
     */
    public void setAudit_time_display(String audit_time_display) {
        this.audit_time_display = audit_time_display;
    }

    /**
     * @return Returns the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     * The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return Returns the rrs_out_time_display
     */
    public String getRrs_out_time_display() {
        return rrs_out_time_display;
    }

    /**
     * @param rrs_out_time_display
     * The rrs_out_time_display to set.
     */
    public void setRrs_out_time_display(String rrs_out_time_display) {
        this.rrs_out_time_display = rrs_out_time_display;
    }

    /**
     * @return Returns the wa_in_time_display
     */
    public String getWa_in_time_display() {
        return wa_in_time_display;
    }

    /**
     * @param wa_in_time_display
     * The wa_in_time_display to set.
     */
    public void setWa_in_time_display(String wa_in_time_display) {
        this.wa_in_time_display = wa_in_time_display;
    }

    /**
     * @return Returns the bill_time_display
     */
    public String getBill_time_display() {
        return bill_time_display;
    }

    /**
     * @param bill_time_display
     * The bill_time_display to set.
     */
    public void setBill_time_display(String bill_time_display) {
        this.bill_time_display = bill_time_display;
    }

    /**
     * @return Returns the product_group_id
     */
    public String getProduct_group_id() {
        return product_group_id;
    }

    /**
     * @param product_group_id
     * The product_group_id to set.
     */
    public void setProduct_group_id(String product_group_id) {
        this.product_group_id = product_group_id;
    }

    /**
     * @return Returns the product_group_name
     */
    public String getProduct_group_name() {
        return product_group_name;
    }

    /**
     * @param product_group_name
     * The product_group_name to set.
     */
    public void setProduct_group_name(String product_group_name) {
        this.product_group_name = product_group_name;
    }

    /**
     * @return Returns the flow_flag_name
     */
    public String getFlow_flag_name() {
        return flow_flag_name;
    }

    /**
     * @param flow_flag_name
     * The flow_flag_name to set.
     */
    public void setFlow_flag_name(String flow_flag_name) {
        this.flow_flag_name = flow_flag_name;
    }

    /**
     * @return Returns the ed_channel_id
     */
    public String getEd_channel_id() {
        return ed_channel_id;
    }

    /**
     * @param ed_channel_id
     * The ed_channel_id to set.
     */
    public void setEd_channel_id(String ed_channel_id) {
        this.ed_channel_id = ed_channel_id;
    }

    /**
     * @return Returns the ed_channel_name
     */
    public String getEd_channel_name() {
        return ed_channel_name;
    }

    /**
     * @param ed_channel_name
     * The ed_channel_name to set.
     */
    public void setEd_channel_name(String ed_channel_name) {
        this.ed_channel_name = ed_channel_name;
    }

    /**
     * @return Returns the brand_id
     */
    public String getBrand_id() {
        return brand_id;
    }

    /**
     * @param brand_id
     * The brand_id to set.
     */
    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    /**
     * @return Returns the materials_desc
     */
    public String getMaterials_desc() {
        return materials_desc;
    }

    /**
     * @param materials_desc
     * The materials_desc to set.
     */
    public void setMaterials_desc(String materials_desc) {
        this.materials_desc = materials_desc;
    }

    /**
     * @return Returns the category_id
     */
    public String getCategory_id() {
        return category_id;
    }

    /**
     * @param category_id
     * The category_id to set.
     */
    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    /**
     * @return Returns the materials_id
     */
    public String getMaterials_id() {
        return materials_id;
    }

    /**
     * @param materials_id
     * The materials_id to set.
     */
    public void setMaterials_id(String materials_id) {
        this.materials_id = materials_id;
    }

    /**
     * @return Returns the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return Returns the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price
     * The price to set.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return Returns the t2_amount
     */
    public BigDecimal getT2_amount() {
        return t2_amount;
    }

    /**
     * @param t2_amount
     * The t2_amount to set.
     */
    public void setT2_amount(BigDecimal t2_amount) {
        this.t2_amount = t2_amount;
    }

    /**
     * @return Returns the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount
     * The amount to set.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
