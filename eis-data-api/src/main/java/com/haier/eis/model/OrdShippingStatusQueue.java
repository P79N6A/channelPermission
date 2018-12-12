package com.haier.eis.model;

import java.io.Serializable;
import java.util.Date;

/*
* 作者:张波
* 2017/12/19
* */
public class OrdShippingStatusQueue implements Serializable{
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID    = -374149657794312839L;

    private Integer           id;
    private Date addTime;
    private Integer           orderId;
    private Integer           orderProductId;
    private String            cOrderSn;
    private String            orderSource;
    private String            outping;
    private Date              lesShipTime;
    private String            expressName;
    private String            expressNumber;
    private Integer           status;
    private Integer           count;

    /**
     * 同步状态 - 未处理
     */
    public static Integer     STATUS_UN_PROCESSED = 0;
    /**
     * 同步状态 - 成功
     */
    public static Integer     STATUS_SUCCESS      = 1;
    /**
     * 同步状态 - 失败
     */
    public static Integer     STATUS_FAILURE      = 2;

    /**
     * 获取 流水号Id
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置 流水号Id
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取 添加时间
     * @return
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * 设置 添加时间
     * @param addTime
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * 获取 订单Id
     * @return
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * 设置 订单Id
     * @param orderId
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取 网单Id
     * @return
     */
    public Integer getOrderProductId() {
        return orderProductId;
    }

    /**
     * 设置 网单Id
     * @param orderProductId
     */
    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

    /**
     * 获取 网单号
     * @return
     */
    public String getcOrderSn() {
        return cOrderSn;
    }

    /**
     * 获取 网单号
     * @return
     */
    public void setcOrderSn(String cOrderSn) {
        this.cOrderSn = cOrderSn;
    }

    /**
     * 获取 订单来源
     * @return
     */
    public String getOrderSource() {
        return orderSource;
    }

    /**
     * 设置 订单来源
     * @param orderSource
     */
    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    /**
     * 获取 出库单号
     * @return
     */
    public String getOutping() {
        return outping;
    }

    /**
     * 设置 出库单号
     * @param outping
     */
    public void setOutping(String outping) {
        this.outping = outping;
    }

    /**
     * 获取 LES出库时间
     * @return
     */
    public Date getLesShipTime() {
        return lesShipTime;
    }

    /**
     * 设置 LES出库时间
     * @param lesShippingTime
     */
    public void setLesShipTime(Date lesShipTime) {
        this.lesShipTime = lesShipTime;
    }

    /**
     * 获取 物流公司
     * @return
     */
    public String getExpressName() {
        return expressName;
    }

    /**
     * 设置 物流公司
     * @param expressName
     */
    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    /**
     * 获取 物流单号
     * @return
     */
    public String getExpressNumber() {
        return expressNumber;
    }

    /**
     * 设置 物流单号
     * @param expressNumber
     */
    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    /**
     * 获取 同步状态
     * 0：未同步
     * 1：同步成功
     * 2：同步失败
     * @return
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置 同步状态
     * 0：未同步
     * 1：同步成功
     * 2：同步失败
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取 同步次数
     * @return
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 设置 同步次数
     * @param count
     */
    public void setCount(Integer count) {
        this.count = count;
    }
}
