package com.haier.shop.model;

import java.io.Serializable;

/**
 * 延保卡实体bean
 *                       
 * @Filename: OrderYBCards.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public class OrderYBCards implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long   serialVersionUID = -5619199768491528149L;

    /**
     * 0：初始状态
     */
    public static final Integer STATUS_INIT      = 0;
    /**
     * 1：已推送HP
     */
    public static final Integer STATUS_TO_HP     = 1;
    /**
     * 2：已获取卡号
     */
    public static final Integer STATUS_GET_CARD  = 2;
    /**
     * 3：已退保
     */
    public static final Integer STATUS_RETURN    = 3;
    private Integer             id;
    private Integer             siteId;
    /**
     * 添加时间
     */
    private Integer             addTime;
    /**
     * 网单号
     */
    private String              cOrderSn;
    /**
     * 延保卡号
     */
    private String              ybCardNum;
    /**
     * 产品序列号
     */
    private String              productNo;
    /**
     * 延保开始时间
     */
    private String              ybBeginDate;
    /**
     * 延保结束时间
     */
    private String              ybEndDate;
    /**
     * 服务完成时间
     */
    private String              serviceCloseDate;
    /**
     * 写入时间
     */
    private String              createdDate;
    /**
     * 延保卡状态，0：初始状态；1：已推送HP；2：已获取卡号；3：已退保
     */
    private Integer             status;
    /**
     * 推送次数
     */
    private Integer             count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    public String getcOrderSn() {
        return cOrderSn;
    }

    public void setcOrderSn(String cOrderSn) {
        this.cOrderSn = cOrderSn;
    }

    public String getYbCardNum() {
        return ybCardNum;
    }

    public void setYbCardNum(String ybCardNum) {
        this.ybCardNum = ybCardNum;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getYbBeginDate() {
        return ybBeginDate;
    }

    public void setYbBeginDate(String ybBeginDate) {
        this.ybBeginDate = ybBeginDate;
    }

    public String getYbEndDate() {
        return ybEndDate;
    }

    public void setYbEndDate(String ybEndDate) {
        this.ybEndDate = ybEndDate;
    }

    public String getServiceCloseDate() {
        return serviceCloseDate;
    }

    public void setServiceCloseDate(String serviceCloseDate) {
        this.serviceCloseDate = serviceCloseDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}