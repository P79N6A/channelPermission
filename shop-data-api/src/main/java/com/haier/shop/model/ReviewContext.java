package com.haier.shop.model;

import java.io.Serializable;

/**
 * Created by xupeng on 18/4/26.
 * 工单用户反馈信息表
 *
 */
public class ReviewContext implements Serializable {

    private static final long serialVersionUID = 1277897457204785477L;
    private String id;                                     // 主键id
    private String reviewid;                               // 评论表主键id
    private String context;                                //评论信息内容
    private String addtime;                                //添加时间
    private String adduser;                                //添加人


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReviewid() {
        return reviewid;
    }

    public void setReviewid(String reviewid) {
        this.reviewid = reviewid;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getAdduser() {
        return adduser;
    }

    public void setAdduser(String adduser) {
        this.adduser = adduser;
    }
}
