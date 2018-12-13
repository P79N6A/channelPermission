package com.haier.shop.model;

import java.io.Serializable;

/**
 * @author hanhaoyang@ehaier.com
 * @date 2018/7/25 9:54
 */
public class EvaluationParameter implements Serializable{
    private int id;
    private String sourceOrderSn;           //外部订单号
    private String commentCreateTime;              //评论创建时间
    private String commentEndTime;                 //评论结束时间
    private String productName;
    private String productCode;             //外部商品编码
    private String custStar;    //商品评分
    private Integer rows;                    //行数
    private Integer page;                    //页数
    private String source;                 //订单来源

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSourceOrderSn() {
        return sourceOrderSn;
    }

    public void setSourceOrderSn(String sourceOrderSn) {
        this.sourceOrderSn = sourceOrderSn;
    }

    public String getCommentCreateTime() {
        return commentCreateTime;
    }

    public void setCommentCreateTime(String commentCreateTime) {
        this.commentCreateTime = commentCreateTime;
    }

    public String getCommentEndTime() {
        return commentEndTime;
    }

    public void setCommentEndTime(String commentEndTime) {
        this.commentEndTime = commentEndTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCustStar() {
        return custStar;
    }

    public void setCustStar(String custStar) {
        this.custStar = custStar;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
