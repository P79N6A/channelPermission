/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *                       
 * @Filename: CrmReturnItem.java
 * @Version: 1.0
 * @Author: yanrp 燕如朋
 * @Email: yanrp110428@dhc.com.cn
 *
 */

public class CrmGenuineRejectItem implements Serializable {

	/**
	 *Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -3808227459098347237L;
	private String			  wp_order_id;							   //单号
	private String			  so_id;								   //SO单号
	private String			  dn_id;								   //DN单号
	private String			  request_user;							   //申请人
	private String			  address;								   //地址
	private String			  concat_person;						   //联系人
	private String			  concat_phone;							   //联系电话
	private String			  brand_id;								   //品牌
	private String			  brand_name;							   //品牌名
	private String			  category_id;							   //品类
	private String			  product_group_id;						   //产品组
	private String			  product_group_name;					   //产品组名称
	private String			  materials_id;							   //物料号
	private String			  model_id;								   //型号
	private String			  ed_channel_id;						   //渠道
	private String			  ed_channel_name;						   //渠道名
	private String			  storage_id;							   //库位
	private Integer			  quantity		   = 0;					   //数量
	private Double			  tax_in_price	   = 0d;				   //含税价格
	private Timestamp		  warehouse_out_time;					   //出库时间
	private Timestamp		  warehouse_in_time;					   //入库时间
	private Timestamp		  commit_time;							   //提交日
	private Timestamp		  delivery_time;						   //提单日
	private String			  warehouse_out_time_display;			   //出库时间显示
	private String			  warehouse_in_time_display;			   //入库时间显示
	private String			  commit_time_display;					   //提交日显示
	private String			  delivery_time_display;				   //提单日显示
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
	private String			  flow_flag_name;						   //流程状态名称
	private String			  vbeln;								   //提单号
	private String			  wa_in_time_display;					   //入WA库时间

	/**
	 * @return Returns the wp_order_id
	 */
	public String getWp_order_id() {
		return wp_order_id;
	}
    private String            vom_reverse_in_wa_no;                    //vom逆向入wa单号
    private Timestamp         reverse_syn_vom_time;                    //逆向同步到vom时间
    private String            reverse_syn_vom_time_display;            //逆向同步到vom时间显示
    private Timestamp         wa_in_time;                              //终止退货入WA库时间
    private String            wa_in_time_displays;                     //终止退货入WA库时间显示

	/**
	 * @param wp_order_id
	 * The wp_order_id to set.
	 */
	public void setWp_order_id(String wp_order_id) {
		this.wp_order_id = wp_order_id;
	}

	/**
	 * @return Returns the so_id
	 */
	public String getSo_id() {
		return so_id;
	}

	/**
	 * @param so_id
	 * The so_id to set.
	 */
	public void setSo_id(String so_id) {
		this.so_id = so_id;
	}

	/**
	 * @return Returns the dn_id
	 */
	public String getDn_id() {
		return dn_id;
	}

	/**
	 * @param dn_id
	 * The dn_id to set.
	 */
	public void setDn_id(String dn_id) {
		this.dn_id = dn_id;
	}

	/**
	 * @return Returns the request_user
	 */
	public String getRequest_user() {
		return request_user;
	}

	/**
	 * @param request_user
	 * The request_user to set.
	 */
	public void setRequest_user(String request_user) {
		this.request_user = request_user;
	}

	/**
	 * @return Returns the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 * The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return Returns the concat_person
	 */
	public String getConcat_person() {
		return concat_person;
	}

	/**
	 * @param concat_person
	 * The concat_person to set.
	 */
	public void setConcat_person(String concat_person) {
		this.concat_person = concat_person;
	}

	/**
	 * @return Returns the concat_phone
	 */
	public String getConcat_phone() {
		return concat_phone;
	}

	/**
	 * @param concat_phone
	 * The concat_phone to set.
	 */
	public void setConcat_phone(String concat_phone) {
		this.concat_phone = concat_phone;
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
	 * @return Returns the brand_name
	 */
	public String getBrand_name() {
		return brand_name;
	}

	/**
	 * @param brand_name
	 * The brand_name to set.
	 */
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
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
	 * @return Returns the model_id
	 */
	public String getModel_id() {
		return model_id;
	}

	/**
	 * @param model_id
	 * The model_id to set.
	 */
	public void setModel_id(String model_id) {
		this.model_id = model_id;
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
	 * @return Returns the storage_id
	 */
	public String getStorage_id() {
		return storage_id;
	}

	/**
	 * @param storage_id
	 * The storage_id to set.
	 */
	public void setStorage_id(String storage_id) {
		this.storage_id = storage_id;
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
	 * @return Returns the tax_in_price
	 */
	public Double getTax_in_price() {
		return tax_in_price;
	}

	/**
	 * @param tax_in_price
	 * The tax_in_price to set.
	 */
	public void setTax_in_price(Double tax_in_price) {
		this.tax_in_price = tax_in_price;
	}

	/**
	 * @return Returns the warehouse_out_time
	 */
	public Timestamp getWarehouse_out_time() {
		return warehouse_out_time;
	}

	/**
	 * @param warehouse_out_time
	 * The warehouse_out_time to set.
	 */
	public void setWarehouse_out_time(Timestamp warehouse_out_time) {
		this.warehouse_out_time = warehouse_out_time;
	}

	/**
	 * @return Returns the warehouse_in_time
	 */
	public Timestamp getWarehouse_in_time() {
		return warehouse_in_time;
	}

	/**
	 * @param warehouse_in_time
	 * The warehouse_in_time to set.
	 */
	public void setWarehouse_in_time(Timestamp warehouse_in_time) {
		this.warehouse_in_time = warehouse_in_time;
	}

	/**
	 * @return Returns the commit_time
	 */
	public Timestamp getCommit_time() {
		return commit_time;
	}

	/**
	 * @param commit_time
	 * The commit_time to set.
	 */
	public void setCommit_time(Timestamp commit_time) {
		this.commit_time = commit_time;
	}

	/**
	 * @return Returns the delivery_time
	 */
	public Timestamp getDelivery_time() {
		return delivery_time;
	}

	/**
	 * @param delivery_time
	 * The delivery_time to set.
	 */
	public void setDelivery_time(Timestamp delivery_time) {
		this.delivery_time = delivery_time;
	}

	/**
	 * @return Returns the warehouse_out_time_display
	 */
	public String getWarehouse_out_time_display() {
		return warehouse_out_time_display;
	}

	/**
	 * @param warehouse_out_time_display
	 * The warehouse_out_time_display to set.
	 */
	public void setWarehouse_out_time_display(String warehouse_out_time_display) {
		this.warehouse_out_time_display = warehouse_out_time_display;
	}

	/**
	 * @return Returns the warehouse_in_time_display
	 */
	public String getWarehouse_in_time_display() {
		return warehouse_in_time_display;
	}

	/**
	 * @param warehouse_in_time_display
	 * The warehouse_in_time_display to set.
	 */
	public void setWarehouse_in_time_display(String warehouse_in_time_display) {
		this.warehouse_in_time_display = warehouse_in_time_display;
	}

	/**
	 * @return Returns the commit_time_display
	 */
	public String getCommit_time_display() {
		return commit_time_display;
	}

	/**
	 * @param commit_time_display
	 * The commit_time_display to set.
	 */
	public void setCommit_time_display(String commit_time_display) {
		this.commit_time_display = commit_time_display;
	}

	/**
	 * @return Returns the delivery_time_display
	 */
	public String getDelivery_time_display() {
		return delivery_time_display;
	}

	/**
	 * @param delivery_time_display
	 * The delivery_time_display to set.
	 */
	public void setDelivery_time_display(String delivery_time_display) {
		this.delivery_time_display = delivery_time_display;
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
	 * @return Returns the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getVbeln() {
		return vbeln;
	}

	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}

	public String getWa_in_time_display() {
		return wa_in_time_display;
	}

	public void setWa_in_time_display(String wa_in_time_display) {
		this.wa_in_time_display = wa_in_time_display;
	}

    public String getVom_reverse_in_wa_no() {
        return vom_reverse_in_wa_no;
    }

    public void setVom_reverse_in_wa_no(String vom_reverse_in_wa_no) {
        this.vom_reverse_in_wa_no = vom_reverse_in_wa_no;
    }

    public Timestamp getReverse_syn_vom_time() {
        return reverse_syn_vom_time;
    }

    public void setReverse_syn_vom_time(Timestamp reverse_syn_vom_time) {
        this.reverse_syn_vom_time = reverse_syn_vom_time;
    }

    public String getReverse_syn_vom_time_display() {
        return reverse_syn_vom_time_display;
    }

    public void setReverse_syn_vom_time_display(String reverse_syn_vom_time_display) {
        this.reverse_syn_vom_time_display = reverse_syn_vom_time_display;
    }

    public Timestamp getWa_in_time() {
        return wa_in_time;
    }

    public void setWa_in_time(Timestamp wa_in_time) {
        this.wa_in_time = wa_in_time;
    }

    public String getWa_in_time_displays() {
        return wa_in_time_displays;
    }

    public void setWa_in_time_displays(String wa_in_time_displays) {
        this.wa_in_time_displays = wa_in_time_displays;
    }

}
