/**
 * Copyright (c) mbaobao.com 2011 All Rights Reserved.
 */
package com.haier.svc.bean;

import java.io.Serializable;

public class GVSOrderPriceRequire implements Serializable {

    private String INT_CODE      = "temp_service_55"; //默认值
    private String INT_SYSDOMAIN = "his";            //默认值
    private String CustCode;                         //付款方编码
    private String RegionCode;                       //工贸编码
    private String InvCode;                          //物料编码
    private String CorpCode;                         //分公司编码

    public String getINT_CODE() {
        return INT_CODE;
    }

    public String getINT_SYSDOMAIN() {
        return INT_SYSDOMAIN;
    }

    public String getRegionCode() {
        return RegionCode;
    }

    public void setRegionCode(String regionCode) {
        RegionCode = regionCode;
    }

    public String getInvCode() {
        return InvCode;
    }

    public void setInvCode(String invCode) {
        InvCode = invCode;
    }

    public void setCustCode(String custCode) {
        CustCode = custCode;
    }

    public void setCorpCode(String corpCode) {
        CorpCode = corpCode;
    }

    public String getCustCode() {
        return CustCode;
    }

    public String getCorpCode() {
        return CorpCode;
    }

    public String getPostMessage() {
        return "INT_CODE=" + INT_CODE + "&INT_SYSDOMAIN=" + INT_SYSDOMAIN + "&CustCode=" + CustCode
               + "&RegionCode=" + RegionCode + "&InvCode=" + InvCode + "&CorpCode=" + CorpCode;
    }
}
