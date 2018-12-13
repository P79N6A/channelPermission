package com.haier.shop.model;

import java.io.Serializable;

/**
 * 责任位统计对象
 *                       
 * @Filename: Statistic.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 *
 */
public class DutyStatistic implements Serializable {
    private static final long serialVersionUID = -1962964103688407055L;
    private String            question1Level1;                         //责任位一
    private String            question1Level2;                         //责任位二
    private String            unsettledNum;                            //未处理数量
    private String            middleNum;                               //中间结果数量    
    private String            finallyNum;                              //最终结果数量
    private String            finishPercent;                           //完成率

    public String getQuestion1Level1() {
        return question1Level1;
    }

    public void setQuestion1Level1(String question1Level1) {
        this.question1Level1 = question1Level1;
    }

    public String getQuestion1Level2() {
        return question1Level2;
    }

    public void setQuestion1Level2(String question1Level2) {
        this.question1Level2 = question1Level2;
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

    @Override
    public String toString() {
        return "DutyStatistic [question1Level1=" + question1Level1 + ", unsettledNum="
               + unsettledNum + ", middleNum=" + middleNum + ", finallyNum=" + finallyNum
               + ", finishPercent=" + finishPercent + "]";
    }

    public DutyStatistic(String question1Level1,String question1Level2) {
        this.question1Level1 = question1Level1;
        this.question1Level2 = question1Level2;
    }

}
