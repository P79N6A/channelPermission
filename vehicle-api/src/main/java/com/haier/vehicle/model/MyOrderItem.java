/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.vehicle.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.Timestamp;

/**
 * 母嬰
 * @Filename: MyOrderItem.java  leader_nb_t2_order_t实体类
 * @Version: 1.0
 * @Author: zzb
 * @Email: Zhangzengbao32@camelotchina.com
 *
 */
public class MyOrderItem implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3431631072818009195L;
    private String            storage_id;                             //库位码
    private String            storage_name;                           //库位名称
    private String            product_group_id;                       //产品组
    private String            product_group_name;                     //产品组名称
    private String            report_year_week;                       //上报年周
    private String            report_year_week_display;               //上报年周
    private String            arrive_year_week;                       //到货年周
    private String            arrive_year_week_display;               //到货年周
    private String            ed_channel_id;                          //渠道
    private String            ed_channel_name;                        //渠道名称
    private String            materials_desc;                         //型号
    private BigDecimal        t2_delivery_prediction;                 //T+2周提货预测
    private Integer           customization;                          //是否定制
    private String            order_id;                               //订单号
    private Integer           satisfy_type_id;                        //满足方式
    private String            satisfy_type_name;                      //满足方式名称
    private String            create_user;                            //创建人
    private Timestamp         create_time;                            //创建时间
    private String            create_time_display;                    //创建时间
    private String            modify_user;                            //修改人
    private Timestamp         modify_time;                            //修改时间
    private String            modify_time_display;                    //修改时间
    private String            delete_user;                            //删除人
    private Timestamp         delete_time;                            //删除时间
    private String            delete_time_display;                    //删除时间
    private String            delete_flag;                            //删除标志位
    private Integer           flow_flag;                              //流程状态标志
    private Integer           revoke_flag;                            //撤销标志
    private String            error_msg;                              //同步错误
    private String            audit_user;                             //审核人
    private Timestamp         audit_time;                             //审核时间
    private String            audit_time_display;                     //审核时间
    private String            audit_remark;                           //审核意见
    private String            materials_id;                           //物料号
    private String            category_id;                            //品类
    private String            flow_flag_name;                         //流程状态名称
    private String            customization_name;                     //定制名称
    private Integer           order_type_id;                          //订单类型编码
    private String            order_type_name;                        //订单类型名称
    private String            brand_id;                               //品牌
    private String            type;                                   //类型
    private String            channel_commit_user;                    //提交人
    private Timestamp         channel_commit_time;                    //提交时间
    private String            channel_commit_time_display;            //提交时间
    private String            cgo_order_id;                           //CGO订单
    private String            address_id;                             //送达方编码
    private String            address_name;                           //送达方名称
    private String            arrival_storage_id;                     //日日顺库位编码
    private String            arrival_storage_desc;                   //日日顺库位名称
    private String            latest_leave_base_date_display;         //最晚离基地日期
    private String            request_arrive_date_display;            //要求到货时间
    private String            actual_deliver_date_display;            //实际到货时间
    private String            factory_id;                             //发运工厂编码
    private String            factory_name;                           //发运工厂名称
    private String            shipment_combination_id;                //一次发运组合号
    private String            cust_unit_code;                         //客户编码
    private String            cust_unit_name;                         //客户描述
    private Integer           WAqty;                                  //已入WA库数量
    private String            rrs_in_time_display;                    //已入日日顺库时间
    private String            rrs_out_time_display;                   //已出日日顺库时间
    private Integer           audit_quantity;                         //评审数量
    private BigDecimal        audit_amount;                           //评审金额
    private BigDecimal        price;                                  //样表单价
    private BigDecimal        amount;                                 //样表金额
    private String            materials_id_R;                         //物料号R码
    private String            po_no;                                  //PO单号
    private String            si_ou_slipNo;                           //SI单号
    private String            fold_slip_no;                           //KB单号
    private String            si_quantity;                            //数量
    private String            si_amount;                              //订单金额
    private String            wa_in_time_display;                     //WA库时间
    private String            receipt_quantity;                       //开票数量
    private String            tax_price;                              //开票价格
    private String            tax_include_amount;                     //价税合计
    private String            wa_in_num;                              //已入WA库数
    private String            bill_time_display;                      //提单日期
    private String            order_close_user;                       //订单关闭人
    private Timestamp         order_close_time;                       //订单关闭日期
    private String            order_close_time_display;               //订单关闭日期
    private String            audit_depart_user;                      //产品部审核人
    private Timestamp         audit_depart_time;                      //产品部审核时间
    private String            audit_depart_time_display;              //产品部审核时间
    private String            audit_depart_remark;                    //产品部审核意见

    public String getOrder_close_user() {
        return order_close_user;
    }

    public void setOrder_close_user(String order_close_user) {
        this.order_close_user = order_close_user;
    }

    public Timestamp getOrder_close_time() {
        return order_close_time;
    }

    public void setOrder_close_time(Timestamp order_close_time) {
        this.order_close_time = order_close_time;
    }

    public String getOrder_close_time_display() {
        return order_close_time_display;
    }

    public void setOrder_close_time_display(String order_close_time_display) {
        this.order_close_time_display = order_close_time_display;
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
     * @return Returns the report_year_week_display
     */
    public String getReport_year_week_display() {
        return report_year_week_display;
    }

    /**
     * @param report_year_week_display
     * The report_year_week_display to set.
     */
    public void setReport_year_week_display(String report_year_week_display) {
        this.report_year_week_display = report_year_week_display;
    }

    /**
     * @return Returns the arrive_year_week
     */
    public String getArrive_year_week() {
        return arrive_year_week;
    }

    /**
     * @param arrive_year_week
     * The arrive_year_week to set.
     */
    public void setArrive_year_week(String arrive_year_week) {
        this.arrive_year_week = arrive_year_week;
    }

    /**
     * @return Returns the arrive_year_week_display
     */
    public String getArrive_year_week_display() {
        return arrive_year_week_display;
    }

    /**
     * @param arrive_year_week_display
     * The arrive_year_week_display to set.
     */
    public void setArrive_year_week_display(String arrive_year_week_display) {
        this.arrive_year_week_display = arrive_year_week_display;
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
     * @return Returns the t2_delivery_prediction
     */
    public BigDecimal getT2_delivery_prediction() {
        return t2_delivery_prediction;
    }

    /**
     * @param t2_delivery_prediction
     * The t2_delivery_prediction to set.
     */
    public void setT2_delivery_prediction(BigDecimal t2_delivery_prediction) {
        this.t2_delivery_prediction = t2_delivery_prediction;
    }

    /**
     * @return Returns the customization
     */
    public Integer getCustomization() {
        return customization;
    }

    /**
     * @param customization
     * The customization to set.
     */
    public void setCustomization(Integer customization) {
        this.customization = customization;
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
     * @return Returns the satisfy_type_id
     */
    public Integer getSatisfy_type_id() {
        return satisfy_type_id;
    }

    /**
     * @param satisfy_type_id
     * The satisfy_type_id to set.
     */
    public void setSatisfy_type_id(Integer satisfy_type_id) {
        this.satisfy_type_id = satisfy_type_id;
    }

    /**
     * @return Returns the satisfy_type_name
     */
    public String getSatisfy_type_name() {
        return satisfy_type_name;
    }

    /**
     * @param satisfy_type_name
     * The satisfy_type_name to set.
     */
    public void setSatisfy_type_name(String satisfy_type_name) {
        this.satisfy_type_name = satisfy_type_name;
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
     * @return Returns the create_time_display
     */
    public String getCreate_time_display() {
        return create_time_display;
    }

    /**
     * @param create_time_display
     * The create_time_display to set.
     */
    public void setCreate_time_display(String create_time_display) {
        this.create_time_display = create_time_display;
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
     * @return Returns the modify_time_display
     */
    public String getModify_time_display() {
        return modify_time_display;
    }

    /**
     * @param modify_time_display
     * The modify_time_display to set.
     */
    public void setModify_time_display(String modify_time_display) {
        this.modify_time_display = modify_time_display;
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
     * @return Returns the delete_time_display
     */
    public String getDelete_time_display() {
        return delete_time_display;
    }

    /**
     * @param delete_time_display
     * The delete_time_display to set.
     */
    public void setDelete_time_display(String delete_time_display) {
        this.delete_time_display = delete_time_display;
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
     * @return Returns the revoke_flag
     */
    public Integer getRevoke_flag() {
        return revoke_flag;
    }

    /**
     * @param revoke_flag
     * The revoke_flag to set.
     */
    public void setRevoke_flag(Integer revoke_flag) {
        this.revoke_flag = revoke_flag;
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
     * @return Returns the customization_name
     */
    public String getCustomization_name() {
        return customization_name;
    }

    /**
     * @param customization_name
     * The customization_name to set.
     */
    public void setCustomization_name(String customization_name) {
        this.customization_name = customization_name;
    }

    /**
     * @return Returns the order_type_id
     */
    public Integer getOrder_type_id() {
        return order_type_id;
    }

    /**
     * @param order_type_id
     * The order_type_id to set.
     */
    public void setOrder_type_id(Integer order_type_id) {
        this.order_type_id = order_type_id;
    }

    /**
     * @return Returns the order_type_name
     */
    public String getOrder_type_name() {
        return order_type_name;
    }

    /**
     * @param order_type_name
     * The order_type_name to set.
     */
    public void setOrder_type_name(String order_type_name) {
        this.order_type_name = order_type_name;
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
     * @return Returns the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     * The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return Returns the channel_commit_user
     */
    public String getChannel_commit_user() {
        return channel_commit_user;
    }

    /**
     * @param channel_commit_user
     * The channel_commit_user to set.
     */
    public void setChannel_commit_user(String channel_commit_user) {
        this.channel_commit_user = channel_commit_user;
    }

    /**
     * @return Returns the channel_commit_time
     */
    public Timestamp getChannel_commit_time() {
        return channel_commit_time;
    }

    /**
     * @param channel_commit_time
     * The channel_commit_time to set.
     */
    public void setChannel_commit_time(Timestamp channel_commit_time) {
        this.channel_commit_time = channel_commit_time;
    }

    /**
     * @return Returns the channel_commit_time_display
     */
    public String getChannel_commit_time_display() {
        return channel_commit_time_display;
    }

    /**
     * @param channel_commit_time_display
     * The channel_commit_time_display to set.
     */
    public void setChannel_commit_time_display(String channel_commit_time_display) {
        this.channel_commit_time_display = channel_commit_time_display;
    }

    /**
     * @return Returns the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * @return Returns the address_id
     */
    public String getAddress_id() {
        return address_id;
    }

    /**
     * @param address_id
     * The address_id to set.
     */
    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    /**
     * @return Returns the address_name
     */
    public String getAddress_name() {
        return address_name;
    }

    /**
     * @param address_name
     * The address_name to set.
     */
    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    /**
     * @return Returns the arrival_storage_id
     */
    public String getArrival_storage_id() {
        return arrival_storage_id;
    }

    /**
     * @param arrival_storage_id
     * The arrival_storage_id to set.
     */
    public void setArrival_storage_id(String arrival_storage_id) {
        this.arrival_storage_id = arrival_storage_id;
    }

    /**
     * @return Returns the arrival_storage_desc
     */
    public String getArrival_storage_desc() {
        return arrival_storage_desc;
    }

    /**
     * @param arrival_storage_desc
     * The arrival_storage_desc to set.
     */
    public void setArrival_storage_desc(String arrival_storage_desc) {
        this.arrival_storage_desc = arrival_storage_desc;
    }

    /**
     * @return Returns the latest_leave_base_date_display
     */
    public String getLatest_leave_base_date_display() {
        return latest_leave_base_date_display;
    }

    /**
     * @param latest_leave_base_date_display
     * The latest_leave_base_date_display to set.
     */
    public void setLatest_leave_base_date_display(String latest_leave_base_date_display) {
        this.latest_leave_base_date_display = latest_leave_base_date_display;
    }

    /**
     * @return Returns the cgo_order_id
     */
    public String getCgo_order_id() {
        return cgo_order_id;
    }

    /**
     * @param cgo_order_id
     * The cgo_order_id to set.
     */
    public void setCgo_order_id(String cgo_order_id) {
        this.cgo_order_id = cgo_order_id;
    }

    /**
     * @return Returns the request_arrive_date_display
     */
    public String getRequest_arrive_date_display() {
        return request_arrive_date_display;
    }

    /**
     * @param request_arrive_date_display
     * The request_arrive_date_display to set.
     */
    public void setRequest_arrive_date_display(String request_arrive_date_display) {
        this.request_arrive_date_display = request_arrive_date_display;
    }

    /**
     * @return Returns the actual_deliver_date_display
     */
    public String getActual_deliver_date_display() {
        return actual_deliver_date_display;
    }

    /**
     * @param actual_deliver_date_display
     * The actual_deliver_date_display to set.
     */
    public void setActual_deliver_date_display(String actual_deliver_date_display) {
        this.actual_deliver_date_display = actual_deliver_date_display;
    }

    /**
     * @return Returns the factory_id
     */
    public String getFactory_id() {
        return factory_id;
    }

    /**
     * @param factory_id
     * The factory_id to set.
     */
    public void setFactory_id(String factory_id) {
        this.factory_id = factory_id;
    }

    /**
     * @return Returns the factory_name
     */
    public String getFactory_name() {
        return factory_name;
    }

    /**
     * @param factory_name
     * The factory_name to set.
     */
    public void setFactory_name(String factory_name) {
        this.factory_name = factory_name;
    }

    /**
     * @return Returns the shipment_combination_id
     */
    public String getShipment_combination_id() {
        return shipment_combination_id;
    }

    /**
     * @param shipment_combination_id
     * The shipment_combination_id to set.
     */
    public void setShipment_combination_id(String shipment_combination_id) {
        this.shipment_combination_id = shipment_combination_id;
    }

    /**
     * @return Returns the cust_unit_code
     */
    public String getCust_unit_code() {
        return cust_unit_code;
    }

    /**
     * @param custom_id
     * The custom_id to set.
     */
    public void setCust_unit_code(String cust_unit_code) {
        this.cust_unit_code = cust_unit_code;
    }

    /**
     * @return Returns the cust_unit_name
     */
    public String getCust_unit_name() {
        return cust_unit_name;
    }

    /**
     * @param cust_unit_name
     * The cust_unit_name to set.
     */
    public void setCust_unit_name(String cust_unit_name) {
        this.cust_unit_name = cust_unit_name;
    }

    /**
     * @return Returns the wAqty
     */
    public Integer getWAqty() {
        return WAqty;
    }

    /**
     * @param wAqty
     * The wAqty to set.
     */
    public void setWAqty(Integer wAqty) {
        WAqty = wAqty;
    }

    /**
     * @return Returns the rrs_in_time_display
     */
    public String getRrs_in_time_display() {
        return rrs_in_time_display;
    }

    /**
     * @param rrs_in_time_display
     * The rrs_in_time_display to set.
     */
    public void setRrs_in_time_display(String rrs_in_time_display) {
        this.rrs_in_time_display = rrs_in_time_display;
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
     * @return Returns the audit_quantity
     */
    public Integer getAudit_quantity() {
        return audit_quantity;
    }

    /**
     * @param audit_quantity
     * The audit_quantity to set.
     */
    public void setAudit_quantity(Integer audit_quantity) {
        this.audit_quantity = audit_quantity;
    }

    /**
     * @return Returns the audit_amount
     */
    public BigDecimal getAudit_amount() {
        return audit_amount;
    }

    /**
     * @param audit_amount
     * The audit_amount to set.
     */
    public void setAudit_amount(BigDecimal audit_amount) {
        this.audit_amount = audit_amount;
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

    /**
     * @return Returns the materials_id_R
     */
    public String getMaterials_id_R() {
        return materials_id_R;
    }

    /**
     * @param materials_id_R
     * The materials_id_R to set.
     */
    public void setMaterials_id_R(String materials_id_R) {
        this.materials_id_R = materials_id_R;
    }

    /**
     * @return Returns the po_no
     */
    public String getPo_no() {
        return po_no;
    }

    /**
     * @param po_no
     * The po_no to set.
     */
    public void setPo_no(String po_no) {
        this.po_no = po_no;
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
     * @return Returns the fold_slip_no
     */
    public String getFold_slip_no() {
        return fold_slip_no;
    }

    /**
     * @param fold_slip_no
     * The fold_slip_no to set.
     */
    public void setFold_slip_no(String fold_slip_no) {
        this.fold_slip_no = fold_slip_no;
    }

    /**
     * @return Returns the si_quantity
     */
    public String getSi_quantity() {
        return si_quantity;
    }

    /**
     * @param si_quantity
     * The si_quantity to set.
     */
    public void setSi_quantity(String si_quantity) {
        this.si_quantity = si_quantity;
    }

    /**
     * @return Returns the si_amount
     */
    public String getSi_amount() {
        return si_amount;
    }

    /**
     * @param si_amount
     * The si_amount to set.
     */
    public void setSi_amount(String si_amount) {
        this.si_amount = si_amount;
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
     * @return Returns the tax_include_amount
     */
    public String getTax_include_amount() {
        return tax_include_amount;
    }

    /**
     * @param tax_include_amount
     * The tax_include_amount to set.
     */
    public void setTax_include_amount(String tax_include_amount) {
        this.tax_include_amount = tax_include_amount;
    }

    /**
     * @return Returns the receipt_quantity
     */
    public String getReceipt_quantity() {
        return receipt_quantity;
    }

    /**
     * @param receipt_quantity
     * The receipt_quantity to set.
     */
    public void setReceipt_quantity(String receipt_quantity) {
        this.receipt_quantity = receipt_quantity;
    }

    /**
     * @return Returns the tax_price
     */
    public String getTax_price() {
        return tax_price;
    }

    /**
     * @param tax_price
     * The tax_price to set.
     */
    public void setTax_price(String tax_price) {
        this.tax_price = tax_price;
    }

    /**
     * @return Returns the wa_in_num
     */
    public String getWa_in_num() {
        return wa_in_num;
    }

    /**
     * @param wa_in_num
     * The wa_in_num to set.
     */
    public void setWa_in_num(String wa_in_num) {
        this.wa_in_num = wa_in_num;
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

    public String getAudit_depart_user() {
        return audit_depart_user;
    }

    public void setAudit_depart_user(String audit_depart_user) {
        this.audit_depart_user = audit_depart_user;
    }

    public Timestamp getAudit_depart_time() {
        return audit_depart_time;
    }

    public void setAudit_depart_time(Timestamp audit_depart_time) {
        this.audit_depart_time = audit_depart_time;
    }

    public String getAudit_depart_time_display() {
        return audit_depart_time_display;
    }

    public void setAudit_depart_time_display(String audit_depart_time_display) {
        this.audit_depart_time_display = audit_depart_time_display;
    }

    public String getAudit_depart_remark() {
        return audit_depart_remark;
    }

    public void setAudit_depart_remark(String audit_depart_remark) {
        this.audit_depart_remark = audit_depart_remark;
    }
}
