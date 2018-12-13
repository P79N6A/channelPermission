package com.haier.purchase.data.model;

import java.io.Serializable;

/**
 * 
 *                       
 * @Filename: BdmOrder.java
 * @Version: 1.0
 * @Author: lizhen
 * @Email: zhen1.li@dhc.com.cn
 *
 */
public class BdmOrder implements Serializable {
    private String salesChanManagerID;  //渠道id
    private String salesChanManagerName; //  渠道名称    
    private String pltype;              //产品线编码
    private String productLineId;       //产品线id
    private String plname;              //产品线名称
    private String productSeriesId;     //产品系列id
    private String itemcode;            //型号编码
    private String itemname;            //型号名称
    private String categorycode;        //产品系列编码
    private String catn;                //产品线系列名称
    private String tradeCode;           //工贸
    private String smbCcode;            //经营体编码
    private String smbName;             //经营体名称
    private String message;             //接口提示信息（S成功；F失败）
    private String customerCode;        //客户
    private String customerName;        //客户名称
    private String create_user;         //创建人
    private String create_time;         //创建时间
    private String update_user;         //更新人
    private String update_time;         //更新时间
    private String delete_user;         //删除人
    private String delete_time;         //删除时间
    private String status;              //信息同步状态
    private String syn_time;            //数据同步时间

    public String getSalesChanManagerID() {
        return salesChanManagerID;
    }

    public void setSalesChanManagerID(String salesChanManagerID) {
        this.salesChanManagerID = salesChanManagerID;
    }

    public String getSalesChanManagerName() {
        return salesChanManagerName;
    }

    public void setSalesChanManagerName(String salesChanManagerName) {
        this.salesChanManagerName = salesChanManagerName;
    }

    public String getPltype() {
        return pltype;
    }

    public void setPltype(String pltype) {
        this.pltype = pltype;
    }

    public String getProductLineId() {
        return productLineId;
    }

    public void setProductLineId(String productLineId) {
        this.productLineId = productLineId;
    }

    public String getPlname() {
        return plname;
    }

    public void setPlname(String plname) {
        this.plname = plname;
    }

    public String getProductSeriesId() {
        return productSeriesId;
    }

    public void setProductSeriesId(String productSeriesId) {
        this.productSeriesId = productSeriesId;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getCategorycode() {
        return categorycode;
    }

    public void setCategorycode(String categorycode) {
        this.categorycode = categorycode;
    }

    public String getCatn() {
        return catn;
    }

    public void setCatn(String catn) {
        this.catn = catn;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public String getSmbCcode() {
        return smbCcode;
    }

    public void setSmbCcode(String smbCcode) {
        this.smbCcode = smbCcode;
    }

    public String getSmbName() {
        return smbName;
    }

    public void setSmbName(String smbName) {
        this.smbName = smbName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getDelete_user() {
        return delete_user;
    }

    public void setDelete_user(String delete_user) {
        this.delete_user = delete_user;
    }

    public String getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(String delete_time) {
        this.delete_time = delete_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSyn_time() {
        return syn_time;
    }

    public void setSyn_time(String syn_time) {
        this.syn_time = syn_time;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

}
