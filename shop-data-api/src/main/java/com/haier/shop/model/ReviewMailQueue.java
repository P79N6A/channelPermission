package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xupeng on 18/4/27.
 */
public class ReviewMailQueue implements Serializable {

    private static final long serialVersionUID = -1723208220661233818L;
    private Long              id;

    private String            sender;                                  //发送者

    private String            receiver;                                //接收者

    private Integer           issend;                                  //是否已经发送0未发送，1发送 默认0

    private Date              addtime;                                 //加入队列时间 默认添加时间

    private Date sendtime;                                //成功发送时间 默认0000-00-00 00:00:00

    private Integer           trytime;                                 //尝试发送次数  默认0

    private String            back1;                                   //备用

    private Integer           back2;                                   //备用

    private String            information;                             //发送内容

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender == null ? null : sender.trim();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public Integer getIssend() {
        return issend;
    }

    public void setIssend(Integer issend) {
        this.issend = issend;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    public Integer getTrytime() {
        return trytime;
    }

    public void setTrytime(Integer trytime) {
        this.trytime = trytime;
    }

    public String getBack1() {
        return back1;
    }

    public void setBack1(String back1) {
        this.back1 = back1 == null ? null : back1.trim();
    }

    public Integer getBack2() {
        return back2;
    }

    public void setBack2(Integer back2) {
        this.back2 = back2;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information == null ? null : information.trim();
    }
}
