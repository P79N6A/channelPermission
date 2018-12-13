package com.haier.shop.dto;

import java.io.Serializable;

/**
 * 
 *  通讯录表                     
 * @Filename: Reviewcontacts.java
 * @Version: 1.0
 * @Author: tianshuai.zhang 张天帅
 * @Email: tianshuai.zhang@dhc.com.cn
 *
 */
public class ReviewContacts implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3267307317899743682L;

    private String            id;                                     //

    private String            userid;                                 //人员id

    private String            question1level1;                        //责任位1

    private String            question1level2;                        //责任位2

    private String            ordercome;                              //订单来源

    private String            company;                                //工贸

    private String            manageruserid1;                         //一级领导

    private String            issend1;                                //是否发送通知（一级领导）（0不发送，1发送）

    private String            manageruserid2;                         //二级领导

    private String            issend2;                                //是否发送通知（二级领导）（0不发送，1发送）

    private String            delflag;                                //删除flag（0：未删除，1：删除）

    private String            addtime;                                 //

    private String            adduser;                                 //

    private String            updatetime;                              //

    private String            updateuser;                              //

    private String            remark1;                                 //

    private String            remark2;                                 //

    private String            remark3;                                 //

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getQuestion1level1() {
        return question1level1;
    }

    public void setQuestion1level1(String question1level1) {
        this.question1level1 = question1level1 == null ? null : question1level1.trim();
    }

    public String getQuestion1level2() {
        return question1level2;
    }

    public void setQuestion1level2(String question1level2) {
        this.question1level2 = question1level2 == null ? null : question1level2.trim();
    }

    public String getOrdercome() {
        return ordercome;
    }

    public void setOrdercome(String ordercome) {
        this.ordercome = ordercome == null ? null : ordercome.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getManageruserid1() {
        return manageruserid1;
    }

    public void setManageruserid1(String manageruserid1) {
        this.manageruserid1 = manageruserid1 == null ? null : manageruserid1.trim();
    }

    public String getIssend1() {
        return issend1;
    }

    public void setIssend1(String issend1) {
        this.issend1 = issend1 == null ? null : issend1.trim();
    }

    public String getManageruserid2() {
        return manageruserid2;
    }

    public void setManageruserid2(String manageruserid2) {
        this.manageruserid2 = manageruserid2 == null ? null : manageruserid2.trim();
    }

    public String getIssend2() {
        return issend2;
    }

    public void setIssend2(String issend2) {
        this.issend2 = issend2 == null ? null : issend2.trim();
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? null : addtime.trim();
    }

    public String getAdduser() {
        return adduser;
    }

    public void setAdduser(String adduser) {
        this.adduser = adduser == null ? null : adduser.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser == null ? null : updateuser.trim();
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3 == null ? null : remark3.trim();
    }

    @Override
    public String toString() {
        return "ReviewContacts [id=" + id + ", userid=" + userid + ", question1level1="
               + question1level1 + ", question1level2=" + question1level2 + ", ordercome="
               + ordercome + ", company=" + company + ", manageruserid1=" + manageruserid1
               + ", issend1=" + issend1 + ", manageruserid2=" + manageruserid2 + ", issend2="
               + issend2 + ", delflag=" + delflag + ", addtime=" + addtime + ", adduser=" + adduser
               + ", updatetime=" + updatetime + ", updateuser=" + updateuser + ", remark1="
               + remark1 + ", remark2=" + remark2 + ", remark3=" + remark3 + "]";
    }

}