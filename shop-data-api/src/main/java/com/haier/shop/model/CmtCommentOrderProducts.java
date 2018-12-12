package com.haier.shop.model;

import java.io.Serializable;
/**
 * Created by 张波 on 2017/12/26.
 */
public class CmtCommentOrderProducts implements Serializable{
    private Integer orderProductId;
    private Integer memberId;
    private Integer commentStatus;
    private String  corderSn;
    private Integer shareOrderNotify;
    private Integer commentNotify;
    private Integer expireStatus;
    private Integer orderId;
    private Long    closeTime;

    public Integer getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getCorderSn() {
        return corderSn;
    }

    public void setCorderSn(String corderSn) {
        this.corderSn = corderSn;
    }

    public Integer getShareOrderNotify() {
        return shareOrderNotify;
    }

    public void setShareOrderNotify(Integer shareOrderNotify) {
        this.shareOrderNotify = shareOrderNotify;
    }

    public Integer getCommentNotify() {
        return commentNotify;
    }

    public void setCommentNotify(Integer commentNotify) {
        this.commentNotify = commentNotify;
    }

    public Integer getExpireStatus() {
        return expireStatus;
    }

    public void setExpireStatus(Integer expireStatus) {
        this.expireStatus = expireStatus;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Long getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Long closeTime) {
        this.closeTime = closeTime;
    }
}
