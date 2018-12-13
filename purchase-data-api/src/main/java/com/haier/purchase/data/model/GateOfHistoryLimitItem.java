package com.haier.purchase.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author 刘骥飞
 * @date 2014-8-18
 * @email jifei.liu@dhc.com.cn
 */
public class GateOfHistoryLimitItem implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7327783337319341946L;
    private String            id;
    private String            year_week;
    private String            category_id;
    private String            ed_channel_id;
    private String            ed_channel_name;
    private BigDecimal        target_num;
    private BigDecimal        limit_num;
    private BigDecimal        loan_num;
    private BigDecimal        total_num;
    private BigDecimal        on_way_num;
    private BigDecimal        used_num;
    private BigDecimal        available_num;
    private String            create_time;

    /**
     * @return Returns the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     * The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Returns the year_week
     */
    public String getYear_week() {
        return year_week;
    }

    /**
     * @param year_week
     * The year_week to set.
     */
    public void setYear_week(String year_week) {
        this.year_week = year_week;
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
     * @return Returns the target_num
     */
    public BigDecimal getTarget_num() {
        return target_num;
    }

    /**
     * @param target_num
     * The target_num to set.
     */
    public void setTarget_num(BigDecimal target_num) {
        this.target_num = target_num;
    }

    /**
     * @return Returns the limit_num
     */
    public BigDecimal getLimit_num() {
        return limit_num;
    }

    /**
     * @param limit_num
     * The limit_num to set.
     */
    public void setLimit_num(BigDecimal limit_num) {
        this.limit_num = limit_num;
    }

    /**
     * @return Returns the loan_num
     */
    public BigDecimal getLoan_num() {
        return loan_num;
    }

    /**
     * @param loan_num
     * The loan_num to set.
     */
    public void setLoan_num(BigDecimal loan_num) {
        this.loan_num = loan_num;
    }

    /**
     * @return Returns the total_num
     */
    public BigDecimal getTotal_num() {
        return total_num;
    }

    /**
     * @param total_num
     * The total_num to set.
     */
    public void setTotal_num(BigDecimal total_num) {
        this.total_num = total_num;
    }

    /**
     * @return Returns the on_way_num
     */
    public BigDecimal getOn_way_num() {
        return on_way_num;
    }

    /**
     * @param on_way_num
     * The on_way_num to set.
     */
    public void setOn_way_num(BigDecimal on_way_num) {
        this.on_way_num = on_way_num;
    }

    /**
     * @return Returns the used_num
     */
    public BigDecimal getUsed_num() {
        return used_num;
    }

    /**
     * @param used_num
     * The used_num to set.
     */
    public void setUsed_num(BigDecimal used_num) {
        this.used_num = used_num;
    }

    /**
     * @return Returns the available_num
     */
    public BigDecimal getAvailable_num() {
        return available_num;
    }

    /**
     * @param available_num
     * The available_num to set.
     */
    public void setAvailable_num(BigDecimal available_num) {
        this.available_num = available_num;
    }

    /**
     * @return Returns the create_time
     */
    public String getCreate_time() {
        return create_time;
    }

    /**
     * @param create_time
     * The create_time to set.
     */
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

}