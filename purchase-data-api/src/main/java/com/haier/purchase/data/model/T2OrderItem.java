/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.purchase.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.Date;

/**
 * 
 * @Filename: T2OrderItem.java haier_t2_order_t实体类
 * @Version: 1.0
 * @Author: yanrp 燕如朋
 * @Email: yanrp110428@dhc.com.cn
 * 
 */
public class T2OrderItem implements Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 2311810511857756498L;
    private String            storage_id;                             // 库位码
    private String            storage_name;                           // 库位名称
    private String            product_group_id;                       // 产品组
    private String            product_group_name;                     // 产品组名称
    private String            report_year_week;                       // 上报年周
    private String            report_year_week_display;               // 上报年周
    private String            arrive_year_week;                       // 到货年周
    private String            arrive_year_week_display;               // 到货年周
    private String            ed_channel_id;                          // 渠道(ed_channel_id)
    private String            ed_channel_name;                        // 渠道名称
    private String            industry_trade_id;                      // 工贸编码
    private String            industry_trade_desc;                    // 工贸描述
    private String            custom_id;                              // 管理客户编码
    private String            custom_desc;                            // 管理客户描述
    private String            transmit_id;                            // 送达方编码
    private String            transmit_desc;                          // 送达方描述
    private String            distribution_center_id;                 // 配送中心编码
    private String            distribution_center_desc;               // 配送中心描述
    private String            arrival_storage_id;                     // 到货库位编码
    private String            arrival_storage_desc;                   // 到货库位描述
    private String            materials_desc;                         // 物料描述
    private BigDecimal        t2_delivery_prediction;                 // T+2周提货预测
    private Integer           customization;                          // 是否定制
    private String            oms_order_id;                           // OMS订单号
    private String            order_id;                               // 订单号
    private Integer           order_num;                              // 订单行号
    private String            status;                                 // 状态
    private Integer           count;                                  // 数量
    private BigDecimal        amount;                                 // 金额
    private Date              latest_arrive_date;                     // 最晚到货时间
    private String            latest_arrive_date_display;             // 最晚到货时间
    private Date              plan_deliver_date;                      // 计划发货日期
    private String            plan_deliver_date_display;              // 计划发货日期
    private Date              promise_arrive_date;                    // 承诺到货日期
    private String            promise_arrive_date_display;            // 承诺到货日期
    private Date              actual_deliver_date;                    // 实际发货日期
    private String            actual_deliver_date_display;            // 实际发货日期
    private Date              predict_arrive_date;                    // 预计到货日期
    private String            predict_arrive_date_display;            // 预计到货日期
    private Date              dn_create_date;                         // DN创建日期
    private String            dn_create_date_display;                 // DN创建日期
    private Date              industry_trade_take_date;               // 工贸收货日期
    private String            industry_trade_take_date_display;       // 工贸收货日期
    private Date              industry_trade_deliver_date;            // 工贸发货日期
    private String            industry_trade_deliver_date_display;    // 工贸发货日期
    private Date              custom_sign_date;                       // 客户签收日期
    private String            custom_sign_date_display;               // 客户签收日期
    private Date              return_order_date;                      // 返单日期
    private String            return_order_date_display;              // 返单日期
    private Integer           satisfy_type_id;                        // 满足方式
    private String            satisfy_type_name;                      // 满足方式名称
    private String            shipment_combination_id;                // 一次发运组合号
    private Integer           sign_num;                               // 签收数量
    private Integer           arrive_delay_days;                      // 到货延误天数
    private Integer           trans_delay_days;                       // 运输延误天数
    private String            no_pass_reason;                         // 不通过原因
    private Integer           order_type_id;                          // 订单类型编码
    private Integer           take_type_id;                           // 提货方式编码
    private String            gvs_order_id;                           // GVS订单号
    private String            dn_id;                                  // DN号
    private Timestamp         confirm_order_time;                     // 确认订购时间
    private String            confirm_order_time_display;             // 确认订购时间
    private String            order_close_user;                       // 订单关闭人
    private Timestamp         order_close_time;                       // 订单关闭日期
    private String            order_close_time_display;               // 订单关闭日期
    private Timestamp         commit_time;                            // 提交时间
    private String            commit_time_display;                    // 提交时间
    private Integer           rrs_order_flag;                         // 是否为日日顺订单
    private BigDecimal        channel_price;                          // 渠道供价
    private Date              latest_leave_base_date;                 // 最晚离基地日期
    private String            latest_leave_base_date_display;         // 最晚离基地日期
    private String            create_user;                            // 创建人
    private Timestamp         create_time;                            // 创建时间
    private String            create_time_display;                    // 创建时间
    private String            modify_user;                            // 修改人
    private Timestamp         modify_time;                            // 修改时间
    private String            modify_time_display;                    // 修改时间
    private String            delete_user;                            // 删除人
    private Timestamp         delete_time;                            // 删除时间
    private String            delete_time_display;                    // 删除时间
    private String            delete_flag;                            // 删除标志位
    private Integer           flow_flag;                              // 流程状态标志
    private String            channel_commit_user;                    // 提交人
    private Timestamp         channel_commit_time;                    // 提交时间
    private String            channel_commit_time_display;            // 提交时间显示
    private Integer           revoke_flag;                            // 撤销标志
    private String            error_msg;                              // 同步错误
    private String            audit_user;                             // 审核人
    private Timestamp         audit_time;                             // 审核时间
    private String            audit_time_display;                     // 审核时间
    private String            audit_remark;                           // 审核意见
    private String            materials_id;                           // 物料号
    private String            oms_order_type;                         // 订单类型
    private String            factory_id;                             // 发运工厂编码
    private String            factory_name;                           // 发运工厂名称
    private String            series_id;                              // 系列
    private BigDecimal        price;                                  // 单价
    private String            brand_id;                               // 品牌
    private BigDecimal        WAamount;                               // 已入WA库单价
    private Integer           WAqty;                                  // 已入WA库数量
    private BigDecimal        WAtotal;                                // 已入WA库金额
    private String            order_type_name;                        // 订单类型名称
    private String            flow_flag_name;                         // 流程状态名称
    private String            rrs_order_flag_name;                    // 日日顺订单标记名称
    private String            customization_name;                     // 定制名称
    private String            status_name;                            // OMS状态名称
    private String            category_id;                            // 品类
    private Integer           sync_status;                            // 传输状态
    private String            custpodetailcode;                       // 客户订单号
    private String            payment_id;                             // 电商付款方编码
    private String            payment_name;                           // 电商付款方名称
    private Integer           order_category;                         // 订单类别
    private String            order_category_name;                    // 订单类别名称
    //private String            volume;                                 //体积

    private String            audit_depart_user;                      // 产品部审核人
    private Timestamp         audit_depart_time;                      // 产品部审核时间
    private String            audit_depart_time_display;              // 产品部审核时间
    private String            audit_depart_remark;                    // 产品部审核意见
    private String            pass_reason;                            // 已冻结推送意见
    private BigDecimal        importCount;                             //订单总数量
    private String            relevanceId;                            // 关联crm_order_manual_t
    private String            cancelOrderId;
    private String            cancelFlag;
    private Date			  waInTime;								//WA入库时间（crm_order_t）
    private String 	          mblnr;								//物料凭证编号(推送sap用)
    private int 			  sapStatus;							//推送sap状态
    private String 			  sapMessage;							//推送sap信息
    private String			  sapProcessTime;						//推送sap时间


	public String getOrder_close_user() {
        return order_close_user;
    }

    public void setOrder_close_user(String order_close_user) {
        this.order_close_user = order_close_user;
    }

    /**
     * @return Returns the storage_id
     */
    public String getStorage_id() {
        return storage_id;
    }

    /**
     * @param storage_id
     *            The storage_id to set.
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
     *            The storage_name to set.
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
     *            The product_group_id to set.
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
     *            The product_group_name to set.
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
     *            The report_year_week to set.
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
     *            The report_year_week_display to set.
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
     *            The arrive_year_week to set.
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
     *            The arrive_year_week_display to set.
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
     *            The ed_channel_id to set.
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
     *            The ed_channel_name to set.
     */
    public void setEd_channel_name(String ed_channel_name) {
        this.ed_channel_name = ed_channel_name;
    }

    /**
     * @return Returns the industry_trade_id
     */
    public String getIndustry_trade_id() {
        return industry_trade_id;
    }

    /**
     * @param industry_trade_id
     *            The industry_trade_id to set.
     */
    public void setIndustry_trade_id(String industry_trade_id) {
        this.industry_trade_id = industry_trade_id;
    }

    /**
     * @return Returns the industry_trade_desc
     */
    public String getIndustry_trade_desc() {
        return industry_trade_desc;
    }

    /**
     * @param industry_trade_desc
     *            The industry_trade_desc to set.
     */
    public void setIndustry_trade_desc(String industry_trade_desc) {
        this.industry_trade_desc = industry_trade_desc;
    }

    /**
     * @return Returns the custom_id
     */
    public String getCustom_id() {
        return custom_id;
    }

    /**
     * @param custom_id
     *            The custom_id to set.
     */
    public void setCustom_id(String custom_id) {
        this.custom_id = custom_id;
    }

    /**
     * @return Returns the custom_desc
     */
    public String getCustom_desc() {
        return custom_desc;
    }

    /**
     * @param custom_desc
     *            The custom_desc to set.
     */
    public void setCustom_desc(String custom_desc) {
        this.custom_desc = custom_desc;
    }

    /**
     * @return Returns the transmit_id
     */
    public String getTransmit_id() {
        return transmit_id;
    }

    /**
     * @param transmit_id
     *            The transmit_id to set.
     */
    public void setTransmit_id(String transmit_id) {
        this.transmit_id = transmit_id;
    }

    /**
     * @return Returns the transmit_desc
     */
    public String getTransmit_desc() {
        return transmit_desc;
    }

    /**
     * @param transmit_desc
     *            The transmit_desc to set.
     */
    public void setTransmit_desc(String transmit_desc) {
        this.transmit_desc = transmit_desc;
    }

    /**
     * @return Returns the distribution_center_id
     */
    public String getDistribution_center_id() {
        return distribution_center_id;
    }

    /**
     * @param distribution_center_id
     *            The distribution_center_id to set.
     */
    public void setDistribution_center_id(String distribution_center_id) {
        this.distribution_center_id = distribution_center_id;
    }

    /**
     * @return Returns the distribution_center_desc
     */
    public String getDistribution_center_desc() {
        return distribution_center_desc;
    }

    /**
     * @param distribution_center_desc
     *            The distribution_center_desc to set.
     */
    public void setDistribution_center_desc(String distribution_center_desc) {
        this.distribution_center_desc = distribution_center_desc;
    }

    /**
     * @return Returns the arrival_storage_id
     */
    public String getArrival_storage_id() {
        return arrival_storage_id;
    }

    /**
     * @param arrival_storage_id
     *            The arrival_storage_id to set.
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
     *            The arrival_storage_desc to set.
     */
    public void setArrival_storage_desc(String arrival_storage_desc) {
        this.arrival_storage_desc = arrival_storage_desc;
    }

    /**
     * @return Returns the materials_desc
     */
    public String getMaterials_desc() {
        return materials_desc;
    }

    /**
     * @param materials_desc
     *            The materials_desc to set.
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
     *            The t2_delivery_prediction to set.
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
     *            The customization to set.
     */
    public void setCustomization(Integer customization) {
        this.customization = customization;
    }

    /**
     * @return Returns the oms_order_id
     */
    public String getOms_order_id() {
        return oms_order_id;
    }

    /**
     * @param oms_order_id
     *            The oms_order_id to set.
     */
    public void setOms_order_id(String oms_order_id) {
        this.oms_order_id = oms_order_id;
    }

    /**
     * @return Returns the order_id
     */
    public String getOrder_id() {
        return order_id;
    }

    /**
     * @param order_id
     *            The order_id to set.
     */
    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    /**
     * @return Returns the order_num
     */
    public Integer getOrder_num() {
        return order_num;
    }

    /**
     * @param order_num
     *            The order_num to set.
     */
    public void setOrder_num(Integer order_num) {
        this.order_num = order_num;
    }

    /**
     * @return Returns the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return Returns the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @param count
     *            The count to set.
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return Returns the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount
     *            The amount to set.
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return Returns the latest_arrive_date
     */
    public Date getLatest_arrive_date() {
        return latest_arrive_date;
    }

    /**
     * @param latest_arrive_date
     *            The latest_arrive_date to set.
     */
    public void setLatest_arrive_date(Date latest_arrive_date) {
        this.latest_arrive_date = latest_arrive_date;
    }

    /**
     * @return Returns the latest_arrive_date_display
     */
    public String getLatest_arrive_date_display() {
        return latest_arrive_date_display;
    }

    /**
     * @param latest_arrive_date_display
     *            The latest_arrive_date_display to set.
     */
    public void setLatest_arrive_date_display(String latest_arrive_date_display) {
        this.latest_arrive_date_display = latest_arrive_date_display;
    }

    /**
     * @return Returns the plan_deliver_date
     */
    public Date getPlan_deliver_date() {
        return plan_deliver_date;
    }

    /**
     * @param plan_deliver_date
     *            The plan_deliver_date to set.
     */
    public void setPlan_deliver_date(Date plan_deliver_date) {
        this.plan_deliver_date = plan_deliver_date;
    }

    /**
     * @return Returns the plan_deliver_date_display
     */
    public String getPlan_deliver_date_display() {
        return plan_deliver_date_display;
    }

    /**
     * @param plan_deliver_date_display
     *            The plan_deliver_date_display to set.
     */
    public void setPlan_deliver_date_display(String plan_deliver_date_display) {
        this.plan_deliver_date_display = plan_deliver_date_display;
    }

    /**
     * @return Returns the promise_arrive_date
     */
    public Date getPromise_arrive_date() {
        return promise_arrive_date;
    }

    /**
     * @param promise_arrive_date
     *            The promise_arrive_date to set.
     */
    public void setPromise_arrive_date(Date promise_arrive_date) {
        this.promise_arrive_date = promise_arrive_date;
    }

    /**
     * @return Returns the promise_arrive_date_display
     */
    public String getPromise_arrive_date_display() {
        return promise_arrive_date_display;
    }

    /**
     * @param promise_arrive_date_display
     *            The promise_arrive_date_display to set.
     */
    public void setPromise_arrive_date_display(String promise_arrive_date_display) {
        this.promise_arrive_date_display = promise_arrive_date_display;
    }

    /**
     * @return Returns the actual_deliver_date
     */
    public Date getActual_deliver_date() {
        return actual_deliver_date;
    }

    /**
     * @param actual_deliver_date
     *            The actual_deliver_date to set.
     */
    public void setActual_deliver_date(Date actual_deliver_date) {
        this.actual_deliver_date = actual_deliver_date;
    }

    /**
     * @return Returns the actual_deliver_date_display
     */
    public String getActual_deliver_date_display() {
        return actual_deliver_date_display;
    }

    /**
     * @param actual_deliver_date_display
     *            The actual_deliver_date_display to set.
     */
    public void setActual_deliver_date_display(String actual_deliver_date_display) {
        this.actual_deliver_date_display = actual_deliver_date_display;
    }

    /**
     * @return Returns the predict_arrive_date
     */
    public Date getPredict_arrive_date() {
        return predict_arrive_date;
    }

    /**
     * @param predict_arrive_date
     *            The predict_arrive_date to set.
     */
    public void setPredict_arrive_date(Date predict_arrive_date) {
        this.predict_arrive_date = predict_arrive_date;
    }

    /**
     * @return Returns the predict_arrive_date_display
     */
    public String getPredict_arrive_date_display() {
        return predict_arrive_date_display;
    }

    /**
     * @param predict_arrive_date_display
     *            The predict_arrive_date_display to set.
     */
    public void setPredict_arrive_date_display(String predict_arrive_date_display) {
        this.predict_arrive_date_display = predict_arrive_date_display;
    }

    /**
     * @return Returns the dn_create_date
     */
    public Date getDn_create_date() {
        return dn_create_date;
    }

    /**
     * @param dn_create_date
     *            The dn_create_date to set.
     */
    public void setDn_create_date(Date dn_create_date) {
        this.dn_create_date = dn_create_date;
    }

    /**
     * @return Returns the dn_create_date_display
     */
    public String getDn_create_date_display() {
        return dn_create_date_display;
    }

    /**
     * @param dn_create_date_display
     *            The dn_create_date_display to set.
     */
    public void setDn_create_date_display(String dn_create_date_display) {
        this.dn_create_date_display = dn_create_date_display;
    }

    /**
     * @return Returns the industry_trade_take_date
     */
    public Date getIndustry_trade_take_date() {
        return industry_trade_take_date;
    }

    /**
     * @param industry_trade_take_date
     *            The industry_trade_take_date to set.
     */
    public void setIndustry_trade_take_date(Date industry_trade_take_date) {
        this.industry_trade_take_date = industry_trade_take_date;
    }

    /**
     * @return Returns the industry_trade_take_date_display
     */
    public String getIndustry_trade_take_date_display() {
        return industry_trade_take_date_display;
    }

    /**
     * @param industry_trade_take_date_display
     *            The industry_trade_take_date_display to set.
     */
    public void setIndustry_trade_take_date_display(String industry_trade_take_date_display) {
        this.industry_trade_take_date_display = industry_trade_take_date_display;
    }

    /**
     * @return Returns the industry_trade_deliver_date
     */
    public Date getIndustry_trade_deliver_date() {
        return industry_trade_deliver_date;
    }

    /**
     * @param industry_trade_deliver_date
     *            The industry_trade_deliver_date to set.
     */
    public void setIndustry_trade_deliver_date(Date industry_trade_deliver_date) {
        this.industry_trade_deliver_date = industry_trade_deliver_date;
    }

    /**
     * @return Returns the industry_trade_deliver_date_display
     */
    public String getIndustry_trade_deliver_date_display() {
        return industry_trade_deliver_date_display;
    }

    /**
     * @param industry_trade_deliver_date_display
     *            The industry_trade_deliver_date_display to set.
     */
    public void setIndustry_trade_deliver_date_display(String industry_trade_deliver_date_display) {
        this.industry_trade_deliver_date_display = industry_trade_deliver_date_display;
    }

    /**
     * @return Returns the custom_sign_date
     */
    public Date getCustom_sign_date() {
        return custom_sign_date;
    }

    /**
     * @param custom_sign_date
     *            The custom_sign_date to set.
     */
    public void setCustom_sign_date(Date custom_sign_date) {
        this.custom_sign_date = custom_sign_date;
    }

    /**
     * @return Returns the custom_sign_date_display
     */
    public String getCustom_sign_date_display() {
        return custom_sign_date_display;
    }

    /**
     * @param custom_sign_date_display
     *            The custom_sign_date_display to set.
     */
    public void setCustom_sign_date_display(String custom_sign_date_display) {
        this.custom_sign_date_display = custom_sign_date_display;
    }

    /**
     * @return Returns the return_order_date
     */
    public Date getReturn_order_date() {
        return return_order_date;
    }

    /**
     * @param return_order_date
     *            The return_order_date to set.
     */
    public void setReturn_order_date(Date return_order_date) {
        this.return_order_date = return_order_date;
    }

    /**
     * @return Returns the return_order_date_display
     */
    public String getReturn_order_date_display() {
        return return_order_date_display;
    }

    /**
     * @param return_order_date_display
     *            The return_order_date_display to set.
     */
    public void setReturn_order_date_display(String return_order_date_display) {
        this.return_order_date_display = return_order_date_display;
    }

    /**
     * @return Returns the satisfy_type_id
     */
    public Integer getSatisfy_type_id() {
        return satisfy_type_id;
    }

    /**
     * @param satisfy_type_id
     *            The satisfy_type_id to set.
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
     *            The satisfy_type_name to set.
     */
    public void setSatisfy_type_name(String satisfy_type_name) {
        this.satisfy_type_name = satisfy_type_name;
    }

    /**
     * @return Returns the shipment_combination_id
     */
    public String getShipment_combination_id() {
        return shipment_combination_id;
    }

    /**
     * @param shipment_combination_id
     *            The shipment_combination_id to set.
     */
    public void setShipment_combination_id(String shipment_combination_id) {
        this.shipment_combination_id = shipment_combination_id;
    }

    /**
     * @return Returns the sign_num
     */
    public Integer getSign_num() {
        return sign_num;
    }

    /**
     * @param sign_num
     *            The sign_num to set.
     */
    public void setSign_num(Integer sign_num) {
        this.sign_num = sign_num;
    }

    /**
     * @return Returns the arrive_delay_days
     */
    public Integer getArrive_delay_days() {
        return arrive_delay_days;
    }

    /**
     * @param arrive_delay_days
     *            The arrive_delay_days to set.
     */
    public void setArrive_delay_days(Integer arrive_delay_days) {
        this.arrive_delay_days = arrive_delay_days;
    }

    /**
     * @return Returns the trans_delay_days
     */
    public Integer getTrans_delay_days() {
        return trans_delay_days;
    }

    /**
     * @param trans_delay_days
     *            The trans_delay_days to set.
     */
    public void setTrans_delay_days(Integer trans_delay_days) {
        this.trans_delay_days = trans_delay_days;
    }

    /**
     * @return Returns the no_pass_reason
     */
    public String getNo_pass_reason() {
        return no_pass_reason;
    }

    /**
     * @param no_pass_reason
     *            The no_pass_reason to set.
     */
    public void setNo_pass_reason(String no_pass_reason) {
        this.no_pass_reason = no_pass_reason;
    }

    /**
     * @return Returns the order_type_id
     */
    public Integer getOrder_type_id() {
        return order_type_id;
    }

    /**
     * @param order_type_id
     *            The order_type_id to set.
     */
    public void setOrder_type_id(Integer order_type_id) {
        this.order_type_id = order_type_id;
    }

    /**
     * @return Returns the take_type_id
     */
    public Integer getTake_type_id() {
        return take_type_id;
    }

    /**
     * @param take_type_id
     *            The take_type_id to set.
     */
    public void setTake_type_id(Integer take_type_id) {
        this.take_type_id = take_type_id;
    }

    /**
     * @return Returns the gvs_order_id
     */
    public String getGvs_order_id() {
        return gvs_order_id;
    }

    /**
     * @param gvs_order_id
     *            The gvs_order_id to set.
     */
    public void setGvs_order_id(String gvs_order_id) {
        this.gvs_order_id = gvs_order_id;
    }

    /**
     * @return Returns the dn_id
     */
    public String getDn_id() {
        return dn_id;
    }

    /**
     * @param dn_id
     *            The dn_id to set.
     */
    public void setDn_id(String dn_id) {
        this.dn_id = dn_id;
    }

    /**
     * @return Returns the confirm_order_time
     */
    public Timestamp getConfirm_order_time() {
        return confirm_order_time;
    }

    /**
     * @param confirm_order_time
     *            The confirm_order_time to set.
     */
    public void setConfirm_order_time(Timestamp confirm_order_time) {
        this.confirm_order_time = confirm_order_time;
    }

    /**
     * @return Returns the confirm_order_time_display
     */
    public String getConfirm_order_time_display() {
        return confirm_order_time_display;
    }

    /**
     * @param confirm_order_time_display
     *            The confirm_order_time_display to set.
     */
    public void setConfirm_order_time_display(String confirm_order_time_display) {
        this.confirm_order_time_display = confirm_order_time_display;
    }

    /**
     * @return Returns the order_close_time
     */
    public Timestamp getOrder_close_time() {
        return order_close_time;
    }

    /**
     * @param order_close_time
     *            The order_close_time to set.
     */
    public void setOrder_close_time(Timestamp order_close_time) {
        this.order_close_time = order_close_time;
    }

    /**
     * @return Returns the order_close_time_display
     */
    public String getOrder_close_time_display() {
        return order_close_time_display;
    }

    /**
     * @param order_close_time_display
     *            The order_close_time_display to set.
     */
    public void setOrder_close_time_display(String order_close_time_display) {
        this.order_close_time_display = order_close_time_display;
    }

    /**
     * @return Returns the commit_time
     */
    public Timestamp getCommit_time() {
        return commit_time;
    }

    /**
     * @param commit_time
     *            The commit_time to set.
     */
    public void setCommit_time(Timestamp commit_time) {
        this.commit_time = commit_time;
    }

    /**
     * @return Returns the commit_time_display
     */
    public String getCommit_time_display() {
        return commit_time_display;
    }

    /**
     * @param commit_time_display
     *            The commit_time_display to set.
     */
    public void setCommit_time_display(String commit_time_display) {
        this.commit_time_display = commit_time_display;
    }

    /**
     * @return Returns the rrs_order_flag
     */
    public Integer getRrs_order_flag() {
        return rrs_order_flag;
    }

    /**
     * @param rrs_order_flag
     *            The rrs_order_flag to set.
     */
    public void setRrs_order_flag(Integer rrs_order_flag) {
        this.rrs_order_flag = rrs_order_flag;
    }

    /**
     * @return Returns the channel_price
     */
    public BigDecimal getChannel_price() {
        return channel_price;
    }

    /**
     * @param channel_price
     *            The channel_price to set.
     */
    public void setChannel_price(BigDecimal channel_price) {
        this.channel_price = channel_price;
    }

    /**
     * @return Returns the latest_leave_base_date
     */
    public Date getLatest_leave_base_date() {
        return latest_leave_base_date;
    }

    /**
     * @param latest_leave_base_date
     *            The latest_leave_base_date to set.
     */
    public void setLatest_leave_base_date(Date latest_leave_base_date) {
        this.latest_leave_base_date = latest_leave_base_date;
    }

    /**
     * @return Returns the latest_leave_base_date_display
     */
    public String getLatest_leave_base_date_display() {
        return latest_leave_base_date_display;
    }

    /**
     * @param latest_leave_base_date_display
     *            The latest_leave_base_date_display to set.
     */
    public void setLatest_leave_base_date_display(String latest_leave_base_date_display) {
        this.latest_leave_base_date_display = latest_leave_base_date_display;
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
    public Timestamp getCreate_time() {
        return create_time;
    }

    /**
     * @param create_time
     *            The create_time to set.
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
     *            The create_time_display to set.
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
     *            The modify_user to set.
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
     *            The modify_time to set.
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
     *            The modify_time_display to set.
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
     *            The delete_user to set.
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
     *            The delete_time to set.
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
     *            The delete_time_display to set.
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
     *            The delete_flag to set.
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
     *            The flow_flag to set.
     */
    public void setFlow_flag(Integer flow_flag) {
        this.flow_flag = flow_flag;
    }

    /**
     * @return Returns the channel_commit_user
     */
    public String getChannel_commit_user() {
        return channel_commit_user;
    }

    /**
     * @param channel_commit_user
     *            The channel_commit_user to set.
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
     *            The channel_commit_time to set.
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
     *            The channel_commit_time_display to set.
     */
    public void setChannel_commit_time_display(String channel_commit_time_display) {
        this.channel_commit_time_display = channel_commit_time_display;
    }

    /**
     * @return Returns the revoke_flag
     */
    public Integer getRevoke_flag() {
        return revoke_flag;
    }

    /**
     * @param revoke_flag
     *            The revoke_flag to set.
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
     *            The error_msg to set.
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
     *            The audit_user to set.
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
     *            The audit_time to set.
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
     *            The audit_time_display to set.
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
     *            The audit_remark to set.
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
     *            The materials_id to set.
     */
    public void setMaterials_id(String materials_id) {
        this.materials_id = materials_id;
    }

    /**
     * @return Returns the oms_order_type
     */
    public String getOms_order_type() {
        return oms_order_type;
    }

    /**
     * @param oms_order_type
     *            The oms_order_type to set.
     */
    public void setOms_order_type(String oms_order_type) {
        this.oms_order_type = oms_order_type;
    }

    /**
     * @return Returns the factory_id
     */
    public String getFactory_id() {
        return factory_id;
    }

    /**
     * @param factory_id
     *            The factory_id to set.
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
     *            The factory_name to set.
     */
    public void setFactory_name(String factory_name) {
        this.factory_name = factory_name;
    }

    /**
     * @return Returns the series_id
     */
    public String getSeries_id() {
        return series_id;
    }

    /**
     * @param series_id
     *            The series_id to set.
     */
    public void setSeries_id(String series_id) {
        this.series_id = series_id;
    }

    /**
     * @return Returns the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price
     *            The price to set.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return Returns the brand_id
     */
    public String getBrand_id() {
        return brand_id;
    }

    /**
     * @param brand_id
     *            The brand_id to set.
     */
    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    /**
     * @return Returns the wAamount
     */
    public BigDecimal getWAamount() {
        return WAamount;
    }

    /**
     * @param wAamount
     *            The wAamount to set.
     */
    public void setWAamount(BigDecimal wAamount) {
        WAamount = wAamount;
    }

    /**
     * @return Returns the wAqty
     */
    public Integer getWAqty() {
        return WAqty;
    }

    /**
     * @param wAqty
     *            The wAqty to set.
     */
    public void setWAqty(Integer wAqty) {
        WAqty = wAqty;
    }

    /**
     * @return Returns the wAtotal
     */
    public BigDecimal getWAtotal() {
        return WAtotal;
    }

    /**
     * @param wAtotal
     *            The wAtotal to set.
     */
    public void setWAtotal(BigDecimal wAtotal) {
        WAtotal = wAtotal;
    }

    /**
     * @return Returns the order_type_name
     */
    public String getOrder_type_name() {
        return order_type_name;
    }

    /**
     * @param order_type_name
     *            The order_type_name to set.
     */
    public void setOrder_type_name(String order_type_name) {
        this.order_type_name = order_type_name;
    }

    /**
     * @return Returns the flow_flag_name
     */
    public String getFlow_flag_name() {
        return flow_flag_name;
    }

    /**
     * @param flow_flag_name
     *            The flow_flag_name to set.
     */
    public void setFlow_flag_name(String flow_flag_name) {
        this.flow_flag_name = flow_flag_name;
    }

    /**
     * @return Returns the rrs_order_flag_name
     */
    public String getRrs_order_flag_name() {
        return rrs_order_flag_name;
    }

    /**
     * @param rrs_order_flag_name
     *            The rrs_order_flag_name to set.
     */
    public void setRrs_order_flag_name(String rrs_order_flag_name) {
        this.rrs_order_flag_name = rrs_order_flag_name;
    }

    /**
     * @return Returns the customization_name
     */
    public String getCustomization_name() {
        return customization_name;
    }

    /**
     * @param customization_name
     *            The customization_name to set.
     */
    public void setCustomization_name(String customization_name) {
        this.customization_name = customization_name;
    }

    /**
     * @return Returns the status_name
     */
    public String getStatus_name() {
        return status_name;
    }

    /**
     * @param status_name
     *            The status_name to set.
     */
    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    /**
     * @return Returns the category_id
     */
    public String getCategory_id() {
        return category_id;
    }

    /**
     * @param category_id
     *            The category_id to set.
     */
    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    /**
     * @return Returns the custpodetailcode
     */
    public String getCustpodetailcode() {
        return custpodetailcode;
    }

    /**
     * @param custpodetailcode
     *            The custpodetailcode to set.
     */
    public void setCustodetailcode(String custpodetailcode) {
        this.custpodetailcode = custpodetailcode;
    }

    /**
     * @return Returns the payment_id
     */
    public String getPayment_id() {
        return payment_id;
    }

    /**
     * @param payment_id
     *            The payment_id to set.
     */
    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    /**
     * @return Returns the payment_name
     */
    public String getPayment_name() {
        return payment_name;
    }

    /**
     * @param payment_name
     *            The payment_name to set.
     */
    public void setPayment_name(String payment_name) {
        this.payment_name = payment_name;
    }

    /**
     * @return Returns the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getSync_status() {
        return sync_status;
    }

    public void setSync_status(Integer sync_status) {
        this.sync_status = sync_status;
    }

    public Integer getOrder_category() {
        return order_category;
    }

    public void setOrder_category(Integer order_category) {
        this.order_category = order_category;
    }

    public String getOrder_category_name() {
        return order_category_name;
    }

    public void setOrder_category_name(String order_category_name) {
        this.order_category_name = order_category_name;
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

    public String getPass_reason() {
        return pass_reason;
    }

    public void setPass_reason(String pass_reason) {
        this.pass_reason = pass_reason;
    }

    public BigDecimal getImportCount() {
        return importCount;
    }

    public void setImportCount(BigDecimal importCount) {
        this.importCount = importCount;
    }

    public String getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(String relevanceId) {
        this.relevanceId = relevanceId;
    }

    public String getCancelOrderId() {
        return cancelOrderId;
    }

    public void setCancelOrderId(String cancelOrderId) {
        this.cancelOrderId = cancelOrderId;
    }

    public String getCancelFlag() {
        return cancelFlag;
    }

    public void setCancelFlag(String cancelFlag) {
        this.cancelFlag = cancelFlag;
    }
    

	public Date getWaInTime() {
		return waInTime;
	}

	public void setWaInTime(Date waInTime) {
		this.waInTime = waInTime;
	}

	public String getMblnr() {
		return mblnr;
	}

	public void setMblnr(String mblnr) {
		this.mblnr = mblnr;
	}

	public void setCustpodetailcode(String custpodetailcode) {
		this.custpodetailcode = custpodetailcode;
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

	@Override
    public String toString() {
        return "T2OrderItem [storage_id=" + storage_id + ", storage_name=" + storage_name
               + ", product_group_id=" + product_group_id + ", product_group_name="
               + product_group_name + ", report_year_week=" + report_year_week
               + ", report_year_week_display=" + report_year_week_display + ", arrive_year_week="
               + arrive_year_week + ", arrive_year_week_display=" + arrive_year_week_display
               + ", ed_channel_id=" + ed_channel_id + ", ed_channel_name=" + ed_channel_name
               + ", industry_trade_id=" + industry_trade_id + ", industry_trade_desc="
               + industry_trade_desc + ", custom_id=" + custom_id + ", custom_desc=" + custom_desc
               + ", transmit_id=" + transmit_id + ", transmit_desc=" + transmit_desc
               + ", distribution_center_id=" + distribution_center_id
               + ", distribution_center_desc=" + distribution_center_desc + ", arrival_storage_id="
               + arrival_storage_id + ", arrival_storage_desc=" + arrival_storage_desc
               + ", materials_desc=" + materials_desc + ", t2_delivery_prediction="
               + t2_delivery_prediction + ", customization=" + customization + ", oms_order_id="
               + oms_order_id + ", order_id=" + order_id + ", order_num=" + order_num + ", status="
               + status + ", count=" + count + ", amount=" + amount + ", latest_arrive_date="
               + latest_arrive_date + ", latest_arrive_date_display=" + latest_arrive_date_display
               + ", plan_deliver_date=" + plan_deliver_date + ", plan_deliver_date_display="
               + plan_deliver_date_display + ", promise_arrive_date=" + promise_arrive_date
               + ", promise_arrive_date_display=" + promise_arrive_date_display
               + ", actual_deliver_date=" + actual_deliver_date + ", actual_deliver_date_display="
               + actual_deliver_date_display + ", predict_arrive_date=" + predict_arrive_date
               + ", predict_arrive_date_display=" + predict_arrive_date_display
               + ", dn_create_date=" + dn_create_date + ", dn_create_date_display="
               + dn_create_date_display + ", industry_trade_take_date=" + industry_trade_take_date
               + ", industry_trade_take_date_display=" + industry_trade_take_date_display
               + ", industry_trade_deliver_date=" + industry_trade_deliver_date
               + ", industry_trade_deliver_date_display=" + industry_trade_deliver_date_display
               + ", custom_sign_date=" + custom_sign_date + ", custom_sign_date_display="
               + custom_sign_date_display + ", return_order_date=" + return_order_date
               + ", return_order_date_display=" + return_order_date_display + ", satisfy_type_id="
               + satisfy_type_id + ", satisfy_type_name=" + satisfy_type_name
               + ", shipment_combination_id=" + shipment_combination_id + ", sign_num=" + sign_num
               + ", arrive_delay_days=" + arrive_delay_days + ", trans_delay_days="
               + trans_delay_days + ", no_pass_reason=" + no_pass_reason + ", order_type_id="
               + order_type_id + ", take_type_id=" + take_type_id + ", gvs_order_id="
               + gvs_order_id + ", dn_id=" + dn_id + ", confirm_order_time=" + confirm_order_time
               + ", confirm_order_time_display=" + confirm_order_time_display
               + ", order_close_user=" + order_close_user + ", order_close_time="
               + order_close_time + ", order_close_time_display=" + order_close_time_display
               + ", commit_time=" + commit_time + ", commit_time_display=" + commit_time_display
               + ", rrs_order_flag=" + rrs_order_flag + ", channel_price=" + channel_price
               + ", latest_leave_base_date=" + latest_leave_base_date
               + ", latest_leave_base_date_display=" + latest_leave_base_date_display
               + ", create_user=" + create_user + ", create_time=" + create_time
               + ", create_time_display=" + create_time_display + ", modify_user=" + modify_user
               + ", modify_time=" + modify_time + ", modify_time_display=" + modify_time_display
               + ", delete_user=" + delete_user + ", delete_time=" + delete_time
               + ", delete_time_display=" + delete_time_display + ", delete_flag=" + delete_flag
               + ", flow_flag=" + flow_flag + ", channel_commit_user=" + channel_commit_user
               + ", channel_commit_time=" + channel_commit_time + ", channel_commit_time_display="
               + channel_commit_time_display + ", revoke_flag=" + revoke_flag + ", error_msg="
               + error_msg + ", audit_user=" + audit_user + ", audit_time=" + audit_time
               + ", audit_time_display=" + audit_time_display + ", audit_remark=" + audit_remark
               + ", materials_id=" + materials_id + ", oms_order_type=" + oms_order_type
               + ", factory_id=" + factory_id + ", factory_name=" + factory_name + ", series_id="
               + series_id + ", price=" + price + ", brand_id=" + brand_id + ", WAamount="
               + WAamount + ", WAqty=" + WAqty + ", WAtotal=" + WAtotal + ", order_type_name="
               + order_type_name + ", flow_flag_name=" + flow_flag_name + ", rrs_order_flag_name="
               + rrs_order_flag_name + ", customization_name=" + customization_name
               + ", status_name=" + status_name + ", category_id=" + category_id + ", sync_status="
               + sync_status + ", custpodetailcode=" + custpodetailcode + ", payment_id="
               + payment_id + ", payment_name=" + payment_name + ", order_category="
               + order_category + ", order_category_name=" + order_category_name + "]";
    }

}
