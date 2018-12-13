/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 *                       
 * @Filename: CrmOrderItem.java  crm_order_t实体类
 * @Version: 1.0
 * @Author: yanrp 燕如朋
 * @Email: yanrp110428@dhc.com.cn
 *
 */
public class CrmOrderItem implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 8758419113198914948L;
    private String            order_id;                               //单号              
    private String            bill_order_id;                          //销售单号               
    private Timestamp         bill_time;                              //提单日期        
    private String            bill_time_display;                      //提单日期    
    private String            so_id;                                  //SO单号               
    private String            dn_id;                                  //DN单号               
    private String            wp_order_id;                            //关联单号             
    private String            materials_id;                           //物料号              
    private float             amount;                                 //开票价格              
    private String            qty;                                    //开单数量             
    private float             total;                                  //价税合计               
    private float             bate_rate;                              //直扣             
    private Integer           status;                                 //状态                
    private Integer           flow_flag;                              //流程标识               
    private String            po_id;                                  //po订单号
    private Timestamp         rrs_out_time;                           //入日日顺库日期           
    private String            rrs_out_time_display;                   //入日日顺库日期  
    private Timestamp         wa_in_time;                             //入wa库日期    
    private String            wa_in_time_display;                     //入wa库日期    
    private String            source_order_id;                        //wp订单号 ，关联T+2表中的订单号
    private String            delete_flag;                            //删除标志位
    private String            storage_id;                             //库位码
    private String            storage_name;                           //库位名
    private String            product_group_id;                       //产品组
    private String            product_group_name;                     //产品组名称
    private String            flow_flag_name;                         //流程标识名称               
    private String            report_year_week;                       //上报年周
    private String            ed_channel_id;                          //渠道(ed_channel_id)
    private String            ed_channel_name;                        //渠道名称
    private String            brand_id;                               //品牌
    private String            materials_desc;                         //物料描述
    private String            category_id;                            //品类
    private BigDecimal        price;                                  //样表单价
    private BigDecimal        t2_amount;                              //样表金额
    private BigDecimal        t2_delivery_prediction;                 //数量
    private String            shipment_combination_id;                //一次运单号

    public Integer getWAqty() {
        return WAqty;
    }

    public void setWAqty(Integer WAqty) {
        this.WAqty = WAqty;
    }

    private Integer           WAqty;                                  // 已入WA库数量

    //3w订单信息
    private String subscribe_code;//预约码
	private String w3_local_code;//3w库位码
	private BigDecimal sign_num;//签收数量
	private String import_user;//导入时间
	private Timestamp import_date;//导入时间
	private Timestamp sign_date;//签收时间
	private String jcode;//3w中的J单号
	private String is_sap;//是否已经推送SAP

	private String push_sap_resoult;//sap推送结果
	private String custpodetailcode;//t2的J单号

    private int 			  sapStatus;							//推送sap状态
    private String 			  sapMessage;							//推送sap信息
    private String			  sapProcessTime;						//推送sap时间
    
    private String lbx;	
    private String lbxStatus;	//入库单状态
    private String inTime;		//3W入库时间
    private String pushStatus;	//推送物流状态
    
	
	
	
    public String getJcode() {
		return jcode;
	}

	public void setJcode(String jcode) {
		this.jcode = jcode;
	}

	public String getIs_sap() {
		return is_sap;
	}

	public void setIs_sap(String is_sap) {
		this.is_sap = is_sap;
	}

	public String getCustpodetailcode() {
		return custpodetailcode;
	}

	public void setCustpodetailcode(String custpodetailcode) {
		this.custpodetailcode = custpodetailcode;
	}

	public String getPush_sap_resoult() {
		return push_sap_resoult;
	}

	public void setPush_sap_resoult(String push_sap_resoult) {
		this.push_sap_resoult = push_sap_resoult;
	}

	public String getShipment_combination_id() {
		return shipment_combination_id;
	}

	public void setShipment_combination_id(String shipment_combination_id) {
		this.shipment_combination_id = shipment_combination_id;
	}

	public BigDecimal getT2_delivery_prediction() {
		return t2_delivery_prediction;
	}

	public void setT2_delivery_prediction(BigDecimal t2_delivery_prediction) {
		this.t2_delivery_prediction = t2_delivery_prediction;
	}

	public String getImport_user() {
		return import_user;
	}

	public void setImport_user(String import_user) {
		this.import_user = import_user;
	}

	public String getSubscribe_code() {
		return subscribe_code;
	}

	public void setSubscribe_code(String subscribe_code) {
		this.subscribe_code = subscribe_code;
	}

	public String getW3_local_code() {
		return w3_local_code;
	}

	public void setW3_local_code(String w3_local_code) {
		this.w3_local_code = w3_local_code;
	}

	public BigDecimal getSign_num() {
		return sign_num;
	}

	public void setSign_num(BigDecimal sign_num) {
		this.sign_num = sign_num;
	}

	public Timestamp getImport_date() {
		return import_date;
	}

	public void setImport_date(Timestamp import_date) {
		this.import_date = import_date;
	}

	public Timestamp getSign_date() {
		return sign_date;
	}

	public void setSign_date(Timestamp sign_date) {
		this.sign_date = sign_date;
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
     * @return Returns the bill_order_id
     */
    public String getBill_order_id() {
        return bill_order_id;
    }

    /**
     * @param bill_order_id
     * The bill_order_id to set.
     */
    public void setBill_order_id(String bill_order_id) {
        this.bill_order_id = bill_order_id;
    }

    /**
     * @return Returns the bill_time
     */
    public Timestamp getBill_time() {
        return bill_time;
    }

    /**
     * @param bill_time
     * The bill_time to set.
     */
    public void setBill_time(Timestamp bill_time) {
        this.bill_time = bill_time;
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
     * @return Returns the amount
     */
    public float getAmount() {
        return amount;
    }

    /**
     * @param amount
     * The amount to set.
     */
    public void setAmount(float amount) {
        this.amount = amount;
    }

    /**
     * @return Returns the qty
     */
    public String getQty() {
        return qty;
    }

    /**
     * @param qty
     * The qty to set.
     */
    public void setQty(String qty) {
        this.qty = qty;
    }

    /**
     * @return Returns the total
     */
    public float getTotal() {
        return total;
    }

    /**
     * @param total
     * The total to set.
     */
    public void setTotal(float total) {
        this.total = total;
    }

    /**
     * @return Returns the bate_rate
     */
    public float getBate_rate() {
        return bate_rate;
    }

    /**
     * @param bate_rate
     * The bate_rate to set.
     */
    public void setBate_rate(float bate_rate) {
        this.bate_rate = bate_rate;
    }

    /**
     * @return Returns the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     * The status to set.
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * @return Returns the po_id
     */
    public String getPo_id() {
        return po_id;
    }

    /**
     * @param po_id
     * The po_id to set.
     */
    public void setPo_id(String po_id) {
        this.po_id = po_id;
    }

    /**
     * @return Returns the rrs_out_time
     */
    public Timestamp getRrs_out_time() {
        return rrs_out_time;
    }

    /**
     * @param rrs_out_time
     * The rrs_out_time to set.
     */
    public void setRrs_out_time(Timestamp rrs_out_time) {
        this.rrs_out_time = rrs_out_time;
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
     * @return Returns the wa_in_time
     */
    public Timestamp getWa_in_time() {
        return wa_in_time;
    }

    /**
     * @param wa_in_time
     * The wa_in_time to set.
     */
    public void setWa_in_time(Timestamp wa_in_time) {
        this.wa_in_time = wa_in_time;
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
     * @return Returns the delete_flag
     */
    public String getDelete_flag() {
        return delete_flag;
    }

    /**
     * @param delete_flag
     * The delete_flag to set.
     */
    public void setDelete_flag(String delete_flag) {
        this.delete_flag = delete_flag;
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
     * @return Returns the storage_name
     */
    public String getStorage_name() {
        return storage_name;
    }

    /**
     * @param storage_name
     * The storage_name to set.
     */
    public void setStorage_name(String storage_name) {
        this.storage_name = storage_name;
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
     * @return Returns the report_year_week
     */
    public String getReport_year_week() {
        return report_year_week;
    }

    /**
     * @param report_year_week
     * The report_year_week to set.
     */
    public void setReport_year_week(String report_year_week) {
        this.report_year_week = report_year_week;
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

	public int getSapStatus() {
		return sapStatus;
	}

	public void setSapStatus(int sapStatus) {
		this.sapStatus = sapStatus;
	}

	public String getSapMessage() {
		return sapMessage;
	}

	public void setSapMessage(String sapMessage) {
		this.sapMessage = sapMessage;
	}

	public String getSapProcessTime() {
		return sapProcessTime;
	}

	public void setSapProcessTime(String sapProcessTime) {
		this.sapProcessTime = sapProcessTime;
	}

	public String getLbx() {
		return lbx;
	}

	public void setLbx(String lbx) {
		this.lbx = lbx;
	}

	public String getLbxStatus() {
		return lbxStatus;
	}

	public void setLbxStatus(String lbxStatus) {
		this.lbxStatus = lbxStatus;
	}

	public String getInTime() {
		return inTime;
	}

	public void setInTime(String inTime) {
		this.inTime = inTime;
	}

	public String getPushStatus() {
		return pushStatus;
	}

	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}
}
