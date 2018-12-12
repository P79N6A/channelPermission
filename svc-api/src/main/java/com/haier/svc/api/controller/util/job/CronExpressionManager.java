package com.haier.svc.api.controller.util.job;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.haier.svc.api.controller.util.job.DateFormatUtil;

public class CronExpressionManager {

    //分钟
    private Map<Integer, String> jCB_MU_Map = new TreeMap<Integer, String>();
    //小时
    private Map<Integer, String> jCB_HH_Map = new TreeMap<Integer, String>();
    //日
    private Map<Integer, String> jCB_DD_Map = new TreeMap<Integer, String>();
    //月
    private Map<Integer, String> jCB_MM_Map = new TreeMap<Integer, String>();
    //周
    private Map<Integer, String> jCB_WK_Map = new TreeMap<Integer, String>();

    public void init() {

        for (int i = 0; i < 60; i++) {
            this.jCB_MU_Map.put(Integer.valueOf(i), "" + i + "");
        }
        for (int i = 10; i <= 31; i++) {
            this.jCB_DD_Map.put(Integer.valueOf(i), "" + i + "");
        }
        for (int i = 1; i <= 12; i++) {
            this.jCB_MM_Map.put(Integer.valueOf(i), "" + i + "");
        }
        for (int i = 1; i <= 7; i++) {
            this.jCB_WK_Map.put(Integer.valueOf(i), "" + i + "");
        }
        for (int i = 0; i <= 23; i++) {
            this.jCB_HH_Map.put(Integer.valueOf(i), "" + i + "");
        }
    }

    public String parseExpression(String expression) {
        String expresText = "";
        try {
            CronExpressionEx exp = new CronExpressionEx(expression.trim());
            Date dd = new Date();
            String startTime = DateFormatUtil.format("yyyy-MM-dd HH:mm:ss",dd);
            StringBuffer executePlan = new StringBuffer();
            executePlan.append("计划执行时间:---");
            for (int i = 1; i <= 8; i++) {
                dd = exp.getNextValidTimeAfter(dd);
                executePlan.append(i + ": " + DateFormatUtil.format("yyyy-MM-dd HH:mm:ss",dd)
                                   + "---");
                dd = new Date(dd.getTime() + 1000L);
            }
            expresText = "开始时间:---" + startTime + "---" + executePlan.toString();
        } catch (Exception e) {
            expresText = "表达式有误";
        }
        return expresText;
    }

    public static void main(String[] args) {
        CronExpressionManager manager = new CronExpressionManager();
        manager.init();
        System.out.println(manager.parseExpression("0 0/1 * * * ? *"));
    }
}
