package com.haier.purchase.data.model;

public class CrmOrderVO {
    private String order_id;
    private String po_id;
    private String source_order_id;
    private String bill_order_id;
    private String bill_time;
    private String so_id;
    private String dn_id;
    private String wp_order_id;
    private String material_id;
    private Double amount;
    private int    qty;
    private Double total;
    private Double bate_rate;
    private int    status;
    private int    flow_flag;
    private String rrs_out_time;
    private String rrs_in_time;
    private String wa_in_order_id;
    private String wa_in_time;
    private String bill_back_time;
    private String detail;
    private String message;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPo_id() {
        return po_id;
    }

    public void setPo_id(String po_id) {
        this.po_id = po_id;
    }

    public String getSource_order_id() {
        return source_order_id;
    }

    public void setSource_order_id(String source_order_id) {
        this.source_order_id = source_order_id;
    }

    public String getBill_order_id() {
        return bill_order_id;
    }

    public void setBill_order_id(String bill_order_id) {
        this.bill_order_id = bill_order_id;
    }

    public String getBill_time() {
        return bill_time;
    }

    public void setBill_time(String bill_time) {
        this.bill_time = bill_time;
    }

    public String getSo_id() {
        return so_id;
    }

    public void setSo_id(String so_id) {
        this.so_id = so_id;
    }

    public String getDn_id() {
        return dn_id;
    }

    public void setDn_id(String dn_id) {
        this.dn_id = dn_id;
    }

    public String getWp_order_id() {
        return wp_order_id;
    }

    public void setWp_order_id(String wp_order_id) {
        this.wp_order_id = wp_order_id;
    }

    public String getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getBate_rate() {
        return bate_rate;
    }

    public void setBate_rate(Double bate_rate) {
        this.bate_rate = bate_rate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFlow_flag() {
        return flow_flag;
    }

    public void setFlow_flag(int flow_flag) {
        this.flow_flag = flow_flag;
    }

    public String getRrs_out_time() {
        return rrs_out_time;
    }

    public void setRrs_out_time(String rrs_out_time) {
        this.rrs_out_time = rrs_out_time;
    }

    public String getRrs_in_time() {
        return rrs_in_time;
    }

    public void setRrs_in_time(String rrs_in_time) {
        this.rrs_in_time = rrs_in_time;
    }

    public String getWa_in_order_id() {
        return wa_in_order_id;
    }

    public void setWa_in_order_id(String wa_in_order_id) {
        this.wa_in_order_id = wa_in_order_id;
    }

    public String getWa_in_time() {
        return wa_in_time;
    }

    public void setWa_in_time(String wa_in_time) {
        this.wa_in_time = wa_in_time;
    }

    public String getBill_back_time() {
        return bill_back_time;
    }

    public void setBill_back_time(String bill_back_time) {
        this.bill_back_time = bill_back_time;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
