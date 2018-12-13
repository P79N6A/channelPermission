package com.haier.shop.model;

import java.io.Serializable;

/**
 * 人员统计
 *                       
 * @Filename: PersonnelStatistic.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 *
 */
public class PersonnelStatistic implements Serializable {
    private static final long serialVersionUID = -5094380385790537645L;
    private String            wangId;                                  //人员
    private String            company;                                 //工贸
    private String            appealCount2;                            //中间结果上诉次数  
    private String            unsettledNum;                            //未处理数量
    private String            middleNum;                               //中间结果数量    
    private String            finallyNum;                              //最终结果数量
    private String            finishPercent;                           //完成率

    public String getWangId() {
        return wangId;
    }

    public void setWangId(String wangId) {
        this.wangId = wangId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAppealCount2() {
        return appealCount2;
    }

    public void setAppealCount2(String appealCount2) {
        this.appealCount2 = appealCount2;
    }

    public String getUnsettledNum() {
        return unsettledNum;
    }

    public void setUnsettledNum(String unsettledNum) {
        this.unsettledNum = unsettledNum;
    }

    public String getMiddleNum() {
        return middleNum;
    }

    public void setMiddleNum(String middleNum) {
        this.middleNum = middleNum;
    }

    public String getFinallyNum() {
        return finallyNum;
    }

    public void setFinallyNum(String finallyNum) {
        this.finallyNum = finallyNum;
    }

    public String getFinishPercent() {
        return finishPercent;
    }

    public void setFinishPercent(String finishPercent) {
        this.finishPercent = finishPercent;
    }

    public PersonnelStatistic(String wangId, String company, String appealCount2) {
        this.wangId = wangId;
        this.company = company;
        this.appealCount2 = appealCount2;
    }

}
