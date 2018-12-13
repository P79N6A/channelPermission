package com.haier.shop.model;

import java.io.Serializable;

/**
 * Created by xupeng on 18/4/26.
 * 工单最终结果
 */
public class ReviewFinalResult implements Serializable {

    private static final long serialVersionUID = -4258709131522751448L;
    private String            id;                                      // 主键id
    private String            reviewid;                                // 评论表主键id
    private String            middleContext;                           //评论中间表信息内容
    private String            addtime;                                 //添加时间
    private String            adduser;                                 //添加人...


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

    public String getMiddleContext() {
        return middleContext;
    }

    public void setMiddleContext(String middleContext) {
        this.middleContext = middleContext;
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
