package com.haier.purchase.data.model;

import java.math.BigDecimal;

public class HaierLimitHistoryItem {
    private String     id;
    private String     category_id;
    private String     year_week;
    private String     ed_channel_id;
    private BigDecimal target_num;
    private BigDecimal limit_num;
    private BigDecimal loan_num;
    private BigDecimal total_num;
    private BigDecimal on_way_num;
    private BigDecimal used_num;
    private BigDecimal available_num;
    private String     create_time;
    private int        month;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getYear_week() {
        return year_week;
    }

    public void setYear_week(String year_week) {
        this.year_week = year_week;
    }

    public String getEd_channel_id() {
        return ed_channel_id;
    }

    public void setEd_channel_id(String ed_channel_id) {
        this.ed_channel_id = ed_channel_id;
    }

    public BigDecimal getTarget_num() {
        return target_num;
    }

    public void setTarget_num(BigDecimal target_num) {
        this.target_num = target_num;
    }

    public BigDecimal getLimit_num() {
        return limit_num;
    }

    public void setLimit_num(BigDecimal limit_num) {
        this.limit_num = limit_num;
    }

    public BigDecimal getLoan_num() {
        return loan_num;
    }

    public void setLoan_num(BigDecimal loan_num) {
        this.loan_num = loan_num;
    }

    public BigDecimal getTotal_num() {
        return total_num;
    }

    public void setTotal_num(BigDecimal total_num) {
        this.total_num = total_num;
    }

    public BigDecimal getOn_way_num() {
        return on_way_num;
    }

    public void setOn_way_num(BigDecimal on_way_num) {
        this.on_way_num = on_way_num;
    }

    public BigDecimal getUsed_num() {
        return used_num;
    }

    public void setUsed_num(BigDecimal used_num) {
        this.used_num = used_num;
    }

    public BigDecimal getAvailable_num() {
        return available_num;
    }

    public void setAvailable_num(BigDecimal available_num) {
        this.available_num = available_num;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

}
