package com.haier.shop.model;

/**
 * @Author:JinXueqian
 * @Date: 2018/8/21 16:57
 */
public class TmypProductDiscountVo extends TmypProductDiscount {

   private String addTimeStr;
   private  String modifyTimeStr;

    public String getAddTimeStr() {
        return addTimeStr;
    }

    public void setAddTimeStr(String addTimeStr) {
        this.addTimeStr = addTimeStr;
    }

    public String getModifyTimeStr() {
        return modifyTimeStr;
    }

    public void setModifyTimeStr(String modifyTimeStr) {
        this.modifyTimeStr = modifyTimeStr;
    }
}
