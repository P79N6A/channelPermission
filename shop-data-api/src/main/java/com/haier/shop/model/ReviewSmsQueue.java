package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xupeng on 18/4/27.
 * 短信发送队列
 */
public class ReviewSmsQueue implements Serializable {

    private static final long serialVersionUID = -6671988546339236964L;
    private Long              id;                                      //主键自增长

    private String            receiver;                                //接收者

    private Integer           issend;                                  //是否已经发送0未发送，1发送  默认0

    private Date              addtime;                                 //加入队列时间  默认当前系统时间

    private Date sendtime;                                //成功发送时间  默认 0000-00-00 00:00:00

    private Integer           trytime;                                 //尝试发送次数   默认0

    private String            back1;                                   //备用

    private Integer           back2;                                   //备用

    private String            information;                             //发送内容

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
