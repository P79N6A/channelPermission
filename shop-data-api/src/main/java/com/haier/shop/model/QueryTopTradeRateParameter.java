package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;


public class QueryTopTradeRateParameter implements Serializable {

    private static final long serialVersionUID = 9L;

    private int id;
    private String sourceOrderSn;           //外部订单号
    private String commentCreateTime;              //评论创建时间
    private String commentEndTime;                 //评论结束时间
    private int isGiveMark;                 //是否评分
    private String markResult;              //评价结果
    private String productName;         //商品类型名称
    private Integer rows;                    //行数
    private Integer page;                    //页数
    private String source;                 //订单来源
    private String lbxNo;                   //lbx单号
    private String cOrderSn;                //网单号
    private String productInfos;            //宝贝信息
    private String productType;             //型号
    private int number;                     //数量
    private String sku;                     //物料编码
    private String nicker;                  //旺旺ID
    private String consignee;                //收货人
    private String phone;                      //电话
    private String mobile;                   //手机
    private String region;                    //所属区域
    private String sCode;                     //所属库位
    private String createTime;                     //下单时间
    private String payTime;                     //付款时间
    private String commentInfos;                //评价信息
    private Date commentCreateTimeShow;         //评论创建时间展示
    private Date createTimeShow;                //展示下单时间


    public Date getCreateTimeShow() {
        return createTimeShow;
    }

    public void setCreateTimeShow(Date createTimeShow) {
        this.createTimeShow = createTimeShow;
    }

    public Date getCommentCreateTimeShow() {
        return commentCreateTimeShow;
    }

    public void setCommentCreateTimeShow(Date commentCreateTimeShow) {
        this.commentCreateTimeShow = commentCreateTimeShow;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public int getIsGiveMark() {
        return isGiveMark;
    }

    public void setIsGiveMark(int isGiveMark) {
        this.isGiveMark = isGiveMark;
    }

    public String getMarkResult() {
        return markResult;
    }

    public void setMarkResult(String markResult) {
        this.markResult = markResult;
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

    public String getLbxNo() {
        return lbxNo;
    }

    public void setLbxNo(String lbxNo) {
        this.lbxNo = lbxNo;
    }

    public String getcOrderSn() {
        return cOrderSn;
    }

    public void setcOrderSn(String cOrderSn) {
        this.cOrderSn = cOrderSn;
    }

    public String getProductInfos() {
        return productInfos;
    }

    public void setProductInfos(String productInfos) {
        this.productInfos = productInfos;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getNicker() {
        return nicker;
    }

    public void setNicker(String nicker) {
        this.nicker = nicker;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getsCode() {
        return sCode;
    }

    public void setsCode(String sCode) {
        this.sCode = sCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getCommentInfos() {
        return commentInfos;
    }

    public void setCommentInfos(String commentInfos) {
        this.commentInfos = commentInfos;
    }
}
