package com.haier.shop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xupeng on 18/4/26.
 */
public class ReviewPool implements Serializable {


    private static final long serialVersionUID = 3822375759909912969L;
    private String id; // 工单编号
    private String orderCome; // 订单来源，HR：海尔商城,TM:天猫商城
    private String orderId; // 订单编号
    private String netOrderId; // 网单编号
    private String wordkOrderId; //
    private String workOrderType; // 工单类型
    private String workStatus; // 工单状态：0未处理、1已确认、2已反馈、3已闭环；
    private String createTime; // 网单生成时间
    private String context; // 最后评论内容
    private String buyer; // 收货人
    private String buyerMobile; // 收货人手机
    private String productName; // 宝贝名称
    private String number; // 数量
    private String sku; // sku
    private String productAmount; // 发票金额
    private String netPointId; // 配送网点
    private String productType; // 型号
    private String wangId; // 旺旺id
    private String phone; // 网单库
    private String payTime; // 付款时间
    private String regionName; // 所属区域
    private String company; // 所属单位
    private String address; //
    private String position1; // 无用
    private String backContext1; // 无用
    private String question1Level1; // 一级责任位
    private String question1Level2; // 二级责任位
    private String question1Level3; // 三级责任位
    private String position2; // 中间结果添加时间
    private String backContext2; // 中间结果
    private String question2Level1; // 无用
    private String question2Level2; // 无用
    private String position3; // 最终结果添加时间
    private String backContext3; // 最终结果内容
    private String question3Level1; // 无用
    private String question3level2; // 无用
    private String workOrderStatus; // 无用
    private String reViewType; // 无用
    private String insertTime; // 添加时间
    private String remark1; // 中间结果添加人
    private String remark2; // 最终结果添加人
    private String remark3; // 最新评论添加人
    private String remark4; // 人员id
    private String remark5; // 订单来源

    private Integer appealCount1; // 未处理上诉次数
    private Integer appealCount2; // 中间结果上诉次数
    private String lastAppealTime; // 最后上诉时间
    private Integer feedBackCount; // 咨询次数
    private String closeType; // 订单关闭原因

    private String oneProcessTime; // 一次处理时效
    private String orderProcessTime; // 工单处理时效
    private Integer complaintFlg; // 是否是投诉:0:不是投诉 1:是投诉
    private Integer askFlg; // 是否是咨询类:0:不是咨询 1:是咨询

    private String corderPrimary; // 网单号主键id
    private String orderPrimary; // 订单号主键id
    private String storeId; //
    private String orderFlag; // 排位标志，0按时间排序，1按用户咨询次数排序

    private String categoryLevelOne; // 工单处理类别，包括商城自身需要处理的三类及SQM,HP
    private String categoryLevelTwo; // 如果工单需要商城处理，责任位分为三级。
    private String categoryLevelThree; // 商城责任位分级，第三级
    private String centerType; // 中心类别
    private String desidePass; // 判定结果
    private String sqmStatus; // 插入到SQM状态。 默认为空，只有大电物流单用到该字段
    private String sqmType; // 工单在SQM中的类型，咨询次数到3次以上可以选择咨询转投诉工单
    private String desideText; // 什么情况
    private String sqmCount; // 同网单号同小类，在SQM中的工单数量
    private String sqmState; // 1 2
    private String workOrderTime; // 中间结果最新时间
    private String middleType; // 中间结果是否发送标识
    private String workOrderTo; // 工单去向
    private String workCreateTime; // 添加时间
    private String hpOrderId; // HP订单号
    private String productCode; // 产品大类
    private String complaintPhone; // 投诉电话
    private String storeType; // 库位类型
    private String channelCode; // 订单渠道
    private String channelName;
    private String nextUserName;
    private String automaticAppealTime;
    private String tbOrderSn;

    public String getTbOrderSn() {
        return tbOrderSn;
    }

    public void setTbOrderSn(String tbOrderSn) {
        this.tbOrderSn = tbOrderSn;
    }

    public String getAutomaticAppealTime() {
        return automaticAppealTime;
    }

    public void setAutomaticAppealTime(String automaticAppealTime) {
        this.automaticAppealTime = automaticAppealTime;
    }

    public String getNextUserName() {
        return nextUserName;
    }

    public void setNextUserName(String nextUserName) {
        this.nextUserName = nextUserName;
    }

    private List<ReviewContext> reviewContextList;
    private List<ReviewMiddle> reviewMiddleList;

    public List<ReviewMiddle> getReviewMiddleList() {
        return reviewMiddleList;
    }

    public void setReviewMiddleList(List<ReviewMiddle> reviewMiddleList) {
        this.reviewMiddleList = reviewMiddleList;
    }

    public List<ReviewContext> getReviewContextList() {
        return reviewContextList;
    }

    public void setReviewContextList(List<ReviewContext> reviewContextList) {
        this.reviewContextList = reviewContextList;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getWorkCreateTime() {
        return workCreateTime;
    }

    public void setWorkCreateTime(String workCreateTime) {
        this.workCreateTime = workCreateTime;
    }

    public String getWorkOrderTo() {
        return workOrderTo;
    }

    public void setWorkOrderTo(String workOrderTo) {
        this.workOrderTo = workOrderTo;
    }

    public String getMiddleType() {
        return middleType;
    }

    public void setMiddleType(String middleType) {
        this.middleType = middleType;
    }

    public String getWorkOrderTime() {
        return workOrderTime;
    }

    public void setWorkOrderTime(String workOrderTime) {
        this.workOrderTime = workOrderTime;
    }

    public String getSqmState() {
        return sqmState;
    }

    public void setSqmState(String sqmState) {
        this.sqmState = sqmState;
    }

    public String getDesideText() {
        return desideText;
    }

    public void setDesideText(String desideText) {
        this.desideText = desideText;
    }

    public Integer getComplaintFlg() {
        return complaintFlg;
    }

    public void setComplaintFlg(Integer complaintFlg) {
        this.complaintFlg = complaintFlg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderCome() {
        return orderCome;
    }

    public void setOrderCome(String orderCome) {
        this.orderCome = orderCome;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getNetOrderId() {
        return netOrderId;
    }

    public void setNetOrderId(String netOrderId) {
        this.netOrderId = netOrderId;
    }

    public String getWordkOrderId() {
        return wordkOrderId;
    }

    public void setWordkOrderId(String wordkOrderId) {
        this.wordkOrderId = wordkOrderId;
    }

    public String getWorkOrderType() {
        return workOrderType;
    }

    public void setWorkOrderType(String workOrderType) {
        this.workOrderType = workOrderType;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getBuyerMobile() {
        return buyerMobile;
    }

    public void setBuyerMobile(String buyerMobile) {
        this.buyerMobile = buyerMobile;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getNetPointId() {
        return netPointId;
    }

    public void setNetPointId(String netPointId) {
        this.netPointId = netPointId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getWangId() {
        return wangId;
    }

    public void setWangId(String wangId) {
        this.wangId = wangId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition1() {
        return position1;
    }

    public void setPosition1(String position1) {
        this.position1 = position1;
    }

    public String getBackContext1() {
        return backContext1;
    }

    public void setBackContext1(String backContext1) {
        this.backContext1 = backContext1;
    }

    public String getQuestion1Level1() {
        return question1Level1;
    }

    public void setQuestion1Level1(String question1Level1) {
        this.question1Level1 = question1Level1;
    }

    public String getQuestion1Level2() {
        return question1Level2;
    }

    public void setQuestion1Level2(String question1Level2) {
        this.question1Level2 = question1Level2;
    }

    public String getPosition2() {
        return position2;
    }

    public void setPosition2(String position2) {
        this.position2 = position2;
    }

    public String getBackContext2() {
        return backContext2;
    }

    public void setBackContext2(String backContext2) {
        this.backContext2 = backContext2;
    }

    public String getQuestion2Level1() {
        return question2Level1;
    }

    public void setQuestion2Level1(String question2Level1) {
        this.question2Level1 = question2Level1;
    }

    public String getQuestion2Level2() {
        return question2Level2;
    }

    public void setQuestion2Level2(String question2Level2) {
        this.question2Level2 = question2Level2;
    }

    public String getPosition3() {
        return position3;
    }

    public void setPosition3(String position3) {
        this.position3 = position3;
    }

    public String getBackContext3() {
        return backContext3;
    }

    public void setBackContext3(String backContext3) {
        this.backContext3 = backContext3;
    }

    public String getQuestion3Level1() {
        return question3Level1;
    }

    public void setQuestion3Level1(String question3Level1) {
        this.question3Level1 = question3Level1;
    }

    public String getQuestion3level2() {
        return question3level2;
    }

    public void setQuestion3level2(String question3level2) {
        this.question3level2 = question3level2;
    }

    public String getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(String workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public String getReViewType() {
        return reViewType;
    }

    public void setReViewType(String reViewType) {
        this.reViewType = reViewType;
    }

    public String getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(String insertTime) {
        this.insertTime = insertTime;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    public String getRemark4() {
        return remark4;
    }

    public void setRemark4(String remark4) {
        this.remark4 = remark4;
    }

    public String getRemark5() {
        return remark5;
    }

    public void setRemark5(String remark5) {
        this.remark5 = remark5;
    }

    public Integer getAppealCount1() {
        return appealCount1;
    }

    public void setAppealCount1(Integer appealCount1) {
        this.appealCount1 = appealCount1;
    }

    public Integer getAppealCount2() {
        return appealCount2;
    }

    public void setAppealCount2(Integer appealCount2) {
        this.appealCount2 = appealCount2;
    }

    public String getLastAppealTime() {
        return lastAppealTime;
    }

    public void setLastAppealTime(String lastAppealTime) {
        this.lastAppealTime = lastAppealTime;
    }

    public Integer getFeedBackCount() {
        return feedBackCount;
    }

    public void setFeedBackCount(Integer feedBackCount) {
        this.feedBackCount = feedBackCount;
    }

    public String getCloseType() {
        return closeType;
    }

    public void setCloseType(String closeType) {
        this.closeType = closeType;
    }

    public String getOneProcessTime() {
        return oneProcessTime;
    }

    public void setOneProcessTime(String oneProcessTime) {
        this.oneProcessTime = oneProcessTime;
    }

    public String getOrderProcessTime() {
        return orderProcessTime;
    }

    public void setOrderProcessTime(String orderProcessTime) {
        this.orderProcessTime = orderProcessTime;
    }

    public Integer getAskFlg() {
        return askFlg;
    }

    public void setAskFlg(Integer askFlg) {
        this.askFlg = askFlg;
    }

    public String getCorderPrimary() {
        return corderPrimary;
    }

    public void setCorderPrimary(String corderPrimary) {
        this.corderPrimary = corderPrimary;
    }

    public String getOrderPrimary() {
        return orderPrimary;
    }

    public void setOrderPrimary(String orderPrimary) {
        this.orderPrimary = orderPrimary;
    }

    @Override
    public String toString() {
        return "ReviewPool [id=" + id + ", orderCome=" + orderCome + ", orderId=" + orderId + ", netOrderId="
                + netOrderId + ", wordkOrderId=" + wordkOrderId + ", workOrderType=" + workOrderType + ", workStatus="
                + workStatus + ", createTime=" + createTime + ", context=" + context + ", buyer=" + buyer
                + ", buyerMobile=" + buyerMobile + ", productName=" + productName + ", number=" + number + ", sku="
                + sku + ", productAmount=" + productAmount + ", netPointId=" + netPointId + ", productType="
                + productType + ", wangId=" + wangId + ", phone=" + phone + ", payTime=" + payTime + ", regionName="
                + regionName + ", company=" + company + ", address=" + address + ", position1=" + position1
                + ", backContext1=" + backContext1 + ", question1Level1=" + question1Level1 + ", question1Level2="
                + question1Level2 + ", position2=" + position2 + ", backContext2=" + backContext2 + ", question2Level1="
                + question2Level1 + ", question2Level2=" + question2Level2 + ", position3=" + position3
                + ", backContext3=" + backContext3 + ", question3Level1=" + question3Level1 + ", question3level2="
                + question3level2 + ", workOrderStatus=" + workOrderStatus + ", reViewType=" + reViewType
                + ", insertTime=" + insertTime + ", remark1=" + remark1 + ", remark2=" + remark2 + ", remark3="
                + remark3 + ", remark4=" + remark4 + ", remark5=" + remark5 + ", appealCount1=" + appealCount1
                + ", appealCount2=" + appealCount2 + ", lastAppealTime=" + lastAppealTime + ", feedBackCount="
                + feedBackCount + ", closeType=" + closeType + ", oneProcessTime=" + oneProcessTime
                + ", orderProcessTime=" + orderProcessTime + ", complaintFlg=" + complaintFlg + ", askFlg=" + askFlg
                + ", corderPrimary=" + corderPrimary + ", orderPrimary=" + orderPrimary + "]";
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag;
    }

    public String getCategoryLevelOne() {
        return categoryLevelOne;
    }

    public void setCategoryLevelOne(String categoryLevelOne) {
        this.categoryLevelOne = categoryLevelOne;
    }

    public String getCategoryLevelTwo() {
        return categoryLevelTwo;
    }

    public void setCategoryLevelTwo(String categoryLevelTwo) {
        this.categoryLevelTwo = categoryLevelTwo;
    }

    public String getCategoryLevelThree() {
        return categoryLevelThree;
    }

    public void setCategoryLevelThree(String categoryLevelThree) {
        this.categoryLevelThree = categoryLevelThree;
    }

    public String getCenterType() {
        return centerType;
    }

    public void setCenterType(String centerType) {
        this.centerType = centerType;
    }

    public String getDesidePass() {
        return desidePass;
    }

    public void setDesidePass(String desidePass) {
        this.desidePass = desidePass;
    }

    public String getSqmStatus() {
        return sqmStatus;
    }

    public void setSqmStatus(String sqmStatus) {
        this.sqmStatus = sqmStatus;
    }

    public String getQuestion1Level3() {
        return question1Level3;
    }

    public void setQuestion1Level3(String question1Level3) {
        this.question1Level3 = question1Level3;
    }

    public String getSqmType() {
        return sqmType;
    }

    public void setSqmType(String sqmType) {
        this.sqmType = sqmType;
    }

    public String getSqmCount() {
        return sqmCount;
    }

    public void setSqmCount(String sqmCount) {
        this.sqmCount = sqmCount;
    }

    public String getHpOrderId() {
        return hpOrderId;
    }

    public void setHpOrderId(String hpOrderId) {
        this.hpOrderId = hpOrderId;
    }

    public String getComplaintPhone() {
        return complaintPhone;
    }

    public void setComplaintPhone(String complaintPhone) {
        this.complaintPhone = complaintPhone;
    }



}
