package com.haier.svc.bean;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;

/**
 * Created by 黄俊 on 2014/7/8.
 */
public class PredictingStockItem implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -390962562551039079L;
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private Integer           id;                                     //编号
    private String            product_group_id;                       //产品组
    private String            category_id;                            //品类
    private String            product_group_name;                     //产品组名称
    private String            materials_id;                           //物料号）
    private String            report_year_week;                       //上报年周
    private String            report_year_week_display;               //上报年周表示值
    private String            ed_channel_id;                          //渠道
    private String            ed_channel_name;                        //渠道名称
    private String            brand_name;                             //品牌
    private String            material_description;                   //型号
    private Date              t3_date;                                //T+3周时间
    private Integer           t3_require_prediction;                  //T+3周需求预测
    private Date              t4_date;                                //T+4周时间
    private Integer           t4_require_prediction;                  //T+4周需求预测
    private Date              t5_date;                                //T+5周时间
    private Integer           t5_require_prediction;                  //T+5周需求预测
    private Date              t6_date;                                //T+6周时间
    private Integer           t6_require_prediction;                  //T+6周需求预测
    private Date              t7_date;                                //T+7周时间
    private Integer           t7_require_prediction;                  //T+7周需求预测
    private Date              t8_date;                                //T+8周时间
    private Integer           t8_require_prediction;                  //T+8周需求预测
    private Date              t9_date;                                //T+9周时间
    private Integer           t9_require_prediction;                  //T+9周需求预测
    private Date              t10_date;                               //T+10周时间
    private Integer           t10_require_prediction;                 //T+10周需求预测
    private Date              t11_date;                               //T+11周时间
    private Integer           t11_require_prediction;                 //T+11周需求预测
    private Date              t12_date;                               //T+12周时间
    private Integer           t12_require_prediction;                 //T+12周需求预测
    private Date              t13_date;                               //T+13周时间
    private Integer           t13_require_prediction;                 //T+13周需求预测
    private String            create_user;                            //创建人
    private Timestamp         create_time;                            //创建时间
    private String            create_time_display;  		      //创建时间显示
    private String            modify_user;                            //修改人
    private Timestamp         modify_time;                            //修改时间
    private String            delete_user;                            //删除人
    private Timestamp         delete_time;                            //删除时间
    private Integer           delete_flag;                            //删除标志
    private String            audit_user;                             //审核人
    private Timestamp         audit_time;                             //审核时间
    private String            audit_time_display;  		      //审核时间显示
    private String            audit_remark;                           //审核意见
    private Integer           flow_flag;                              //流程标志code
    private String            error_msg;                              //同步错误
    private Integer           not_commit_count;                       //未提交数
    private Integer           commit_count;                           //已提交数
    private Integer           failure_count;                          //提交失败数
    private String            flow_flag_name;                         //流程标志name

public String getCreate_time_display() {
		return create_time_display;
	}

	public void setCreate_time_display(String create_time_display) {
		this.create_time_display = create_time_display;
	}

	public String getAudit_time_display() {
		return audit_time_display;
	}

	public void setAudit_time_display(String audit_time_display) {
		this.audit_time_display = audit_time_display;
	}

    /**
     * @return Returns the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     * The id to set.
     */
    public void setId(Integer id) {
        this.id = id;
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
     * @return Returns the material_description
     */
    public String getMaterial_description() {
        return material_description;
    }

    /**
     * @param material_description
     * The material_description to set.
     */
    public void setMaterial_description(String material_description) {
        this.material_description = material_description;
    }

    /**
     * @return Returns the t3_date
     */
    public Date getT3_date() {
        return t3_date;
    }

    /**
     * @param t3_date
     * The t3_date to set.
     */
    public void setT3_date(Date t3_date) {
        this.t3_date = t3_date;
    }

    /**
     * @return Returns the t3_require_prediction
     */
    public Integer getT3_require_prediction() {
        return t3_require_prediction;
    }

    /**
     * @param t3_require_prediction
     * The t3_require_prediction to set.
     */
    public void setT3_require_prediction(Integer t3_require_prediction) {
        this.t3_require_prediction = t3_require_prediction;
    }

    /**
     * @return Returns the t4_date
     */
    public Date getT4_date() {
        return t4_date;
    }

    /**
     * @param t4_date
     * The t4_date to set.
     */
    public void setT4_date(Date t4_date) {
        this.t4_date = t4_date;
    }

    /**
     * @return Returns the t4_require_prediction
     */
    public Integer getT4_require_prediction() {
        return t4_require_prediction;
    }

    /**
     * @param t4_require_prediction
     * The t4_require_prediction to set.
     */
    public void setT4_require_prediction(Integer t4_require_prediction) {
        this.t4_require_prediction = t4_require_prediction;
    }

    /**
     * @return Returns the t5_date
     */
    public Date getT5_date() {
        return t5_date;
    }

    /**
     * @param t5_date
     * The t5_date to set.
     */
    public void setT5_date(Date t5_date) {
        this.t5_date = t5_date;
    }

    /**
     * @return Returns the t5_require_prediction
     */
    public Integer getT5_require_prediction() {
        return t5_require_prediction;
    }

    /**
     * @param t5_require_prediction
     * The t5_require_prediction to set.
     */
    public void setT5_require_prediction(Integer t5_require_prediction) {
        this.t5_require_prediction = t5_require_prediction;
    }

    /**
     * @return Returns the t6_date
     */
    public Date getT6_date() {
        return t6_date;
    }

    /**
     * @param t6_date
     * The t6_date to set.
     */
    public void setT6_date(Date t6_date) {
        this.t6_date = t6_date;
    }

    /**
     * @return Returns the t6_require_prediction
     */
    public Integer getT6_require_prediction() {
        return t6_require_prediction;
    }

    /**
     * @param t6_require_prediction
     * The t6_require_prediction to set.
     */
    public void setT6_require_prediction(Integer t6_require_prediction) {
        this.t6_require_prediction = t6_require_prediction;
    }

    /**
     * @return Returns the t7_date
     */
    public Date getT7_date() {
        return t7_date;
    }

    /**
     * @param t7_date
     * The t7_date to set.
     */
    public void setT7_date(Date t7_date) {
        this.t7_date = t7_date;
    }

    /**
     * @return Returns the t7_require_prediction
     */
    public Integer getT7_require_prediction() {
        return t7_require_prediction;
    }

    /**
     * @param t7_require_prediction
     * The t7_require_prediction to set.
     */
    public void setT7_require_prediction(Integer t7_require_prediction) {
        this.t7_require_prediction = t7_require_prediction;
    }

    /**
     * @return Returns the t8_date
     */
    public Date getT8_date() {
        return t8_date;
    }

    /**
     * @param t8_date
     * The t8_date to set.
     */
    public void setT8_date(Date t8_date) {
        this.t8_date = t8_date;
    }

    /**
     * @return Returns the t8_require_prediction
     */
    public Integer getT8_require_prediction() {
        return t8_require_prediction;
    }

    /**
     * @param t8_require_prediction
     * The t8_require_prediction to set.
     */
    public void setT8_require_prediction(Integer t8_require_prediction) {
        this.t8_require_prediction = t8_require_prediction;
    }

    /**
     * @return Returns the t9_date
     */
    public Date getT9_date() {
        return t9_date;
    }

    /**
     * @param t9_date
     * The t9_date to set.
     */
    public void setT9_date(Date t9_date) {
        this.t9_date = t9_date;
    }

    /**
     * @return Returns the t9_require_prediction
     */
    public Integer getT9_require_prediction() {
        return t9_require_prediction;
    }

    /**
     * @param t9_require_prediction
     * The t9_require_prediction to set.
     */
    public void setT9_require_prediction(Integer t9_require_prediction) {
        this.t9_require_prediction = t9_require_prediction;
    }

    /**
     * @return Returns the t10_date
     */
    public Date getT10_date() {
        return t10_date;
    }

    /**
     * @param t10_date
     * The t10_date to set.
     */
    public void setT10_date(Date t10_date) {
        this.t10_date = t10_date;
    }

    /**
     * @return Returns the t10_require_prediction
     */
    public Integer getT10_require_prediction() {
        return t10_require_prediction;
    }

    /**
     * @param t10_require_prediction
     * The t10_require_prediction to set.
     */
    public void setT10_require_prediction(Integer t10_require_prediction) {
        this.t10_require_prediction = t10_require_prediction;
    }

    /**
     * @return Returns the t11_date
     */
    public Date getT11_date() {
        return t11_date;
    }

    /**
     * @param t11_date
     * The t11_date to set.
     */
    public void setT11_date(Date t11_date) {
        this.t11_date = t11_date;
    }

    /**
     * @return Returns the t11_require_prediction
     */
    public Integer getT11_require_prediction() {
        return t11_require_prediction;
    }

    /**
     * @param t11_require_prediction
     * The t11_require_prediction to set.
     */
    public void setT11_require_prediction(Integer t11_require_prediction) {
        this.t11_require_prediction = t11_require_prediction;
    }

    /**
     * @return Returns the t12_date
     */
    public Date getT12_date() {
        return t12_date;
    }

    /**
     * @param t12_date
     * The t12_date to set.
     */
    public void setT12_date(Date t12_date) {
        this.t12_date = t12_date;
    }

    /**
     * @return Returns the t12_require_prediction
     */
    public Integer getT12_require_prediction() {
        return t12_require_prediction;
    }

    /**
     * @param t12_require_prediction
     * The t12_require_prediction to set.
     */
    public void setT12_require_prediction(Integer t12_require_prediction) {
        this.t12_require_prediction = t12_require_prediction;
    }

    /**
     * @return Returns the t13_date
     */
    public Date getT13_date() {
        return t13_date;
    }

    /**
     * @param t13_date
     * The t13_date to set.
     */
    public void setT13_date(Date t13_date) {
        this.t13_date = t13_date;
    }

    /**
     * @return Returns the t13_require_prediction
     */
    public Integer getT13_require_prediction() {
        return t13_require_prediction;
    }

    /**
     * @param t13_require_prediction
     * The t13_require_prediction to set.
     */
    public void setT13_require_prediction(Integer t13_require_prediction) {
        this.t13_require_prediction = t13_require_prediction;
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
     * @return Returns the not_commit_count
     */
    public Integer getReport_count() {
        return not_commit_count;
    }

    /**
     * @param not_commit_count
     * The not_commit_count to set.
     */
    public void setNot_commit_count(Integer not_commit_count) {
        this.not_commit_count = not_commit_count;
    }

    /**
     * @return Returns the commit_count
     */
    public Integer getCommit_count() {
        return commit_count;
    }

    /**
     * @param commit_count
     * The commit_count to set.
     */
    public void setCommit_count(Integer commit_count) {
        this.commit_count = commit_count;
    }

    /**
     * @return Returns the failure_count
     */
    public Integer getFailure_count() {
        return failure_count;
    }

    /**
     * @param failure_count
     * The failure_count to set.
     */
    public void setFailure_count(Integer failure_count) {
        this.failure_count = failure_count;
    }

    /**
     * @return Returns the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
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
}
