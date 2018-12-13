package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hanhaoyang@ehaier.com
 * @date 2018/7/24 11:19
 */
public class Evaluation implements Serializable{
    private Integer id;
    private String sourceOrderSn;   //来源订单号
    private String productName; //商品名称
    private String productCode; //商品编码
    private String custStar;    //商品评分
    private String attitudeStar;    //服务评分
    private String deliverySpeedStar;   //物流评分
    private String userName;    //买家名称
    private String custEvaluateContent; //顾客评价内容
    private Date custEvaluateTime;  //顾客评价时间
    private String custAddEvaluateContent;  //顾客追评
    private Date custAddEvaluateTime;   //顾客追评时间
    private String shopReplyContent;    //商家回复
    private Date shopReplyTime; //商家回复时间
    private String source;  //订单来源

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSourceOrderSn() {
        return sourceOrderSn;
    }

    public void setSourceOrderSn(String sourceOrderSn) {
        this.sourceOrderSn = sourceOrderSn;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCustStar() {
        return custStar;
    }

    public void setCustStar(String custStar) {
        this.custStar = custStar;
    }

    public String getAttitudeStar() {
        return attitudeStar;
    }

    public void setAttitudeStar(String attitudeStar) {
        this.attitudeStar = attitudeStar;
    }

    public String getDeliverySpeedStar() {
        return deliverySpeedStar;
    }

    public void setDeliverySpeedStar(String deliverySpeedStar) {
        this.deliverySpeedStar = deliverySpeedStar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustEvaluateContent() {
        return custEvaluateContent;
    }

    public void setCustEvaluateContent(String custEvaluateContent) {
        this.custEvaluateContent = custEvaluateContent;
    }

    public Date getCustEvaluateTime() {
        return custEvaluateTime;
    }

    public void setCustEvaluateTime(Date custEvaluateTime) {
        this.custEvaluateTime = custEvaluateTime;
    }

    public String getCustAddEvaluateContent() {
        return custAddEvaluateContent;
    }

    public void setCustAddEvaluateContent(String custAddEvaluateContent) {
        this.custAddEvaluateContent = custAddEvaluateContent;
    }

    public Date getCustAddEvaluateTime() {
        return custAddEvaluateTime;
    }

    public void setCustAddEvaluateTime(Date custAddEvaluateTime) {
        this.custAddEvaluateTime = custAddEvaluateTime;
    }

    public String getShopReplyContent() {
        return shopReplyContent;
    }

    public void setShopReplyContent(String shopReplyContent) {
        this.shopReplyContent = shopReplyContent;
    }

    public Date getShopReplyTime() {
        return shopReplyTime;
    }

    public void setShopReplyTime(Date shopReplyTime) {
        this.shopReplyTime = shopReplyTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
