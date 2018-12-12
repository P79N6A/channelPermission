/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.svc.bean;

import java.io.Serializable;

public class GVSOrderPriceResponse implements Serializable {

    private String ReInvCode;    //物料编码
    private String ReUnitPrice;  //开票单价
    private String ReStockPrice; //采购价
    private String ReRetailPrice; //供价
    private String ReActPrice;   //执行价
    private String ReBateRate;   //直扣
    private String ReBateMoney;  //台返金额
    private String ReLossRate;   //折扣
    private String ReIsFL;       //是否返利
    private String ReIsKPO;      //是否商用空调
    public String getReInvCode() {
        return ReInvCode;
    }
    public void setReInvCode(String reInvCode) {
        ReInvCode = reInvCode;
    }
    public String getReUnitPrice() {
        return ReUnitPrice;
    }
    public void setReUnitPrice(String reUnitPrice) {
        ReUnitPrice = reUnitPrice;
    }
    public String getReStockPrice() {
        return ReStockPrice;
    }
    public void setReStockPrice(String reStockPrice) {
        ReStockPrice = reStockPrice;
    }
    public String getReRetailPrice() {
        return ReRetailPrice;
    }
    public void setReRetailPrice(String reRetailPrice) {
        ReRetailPrice = reRetailPrice;
    }
    public String getReActPrice() {
        return ReActPrice;
    }
    public void setReActPrice(String reActPrice) {
        ReActPrice = reActPrice;
    }
    public String getReBateRate() {
        return ReBateRate;
    }
    public void setReBateRate(String reBateRate) {
        ReBateRate = reBateRate;
    }
    public String getReBateMoney() {
        return ReBateMoney;
    }
    public void setReBateMoney(String reBateMoney) {
        ReBateMoney = reBateMoney;
    }
    public String getReLossRate() {
        return ReLossRate;
    }
    public void setReLossRate(String reLossRate) {
        ReLossRate = reLossRate;
    }
    public String getReIsFL() {
        return ReIsFL;
    }
    public void setReIsFL(String reIsFL) {
        ReIsFL = reIsFL;
    }
    public String getReIsKPO() {
        return ReIsKPO;
    }
    public void setReIsKPO(String reIsKPO) {
        ReIsKPO = reIsKPO;
    }
    
    

}
