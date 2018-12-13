package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

/**
 * kpi报表数据
 *                       
 * @Filename: OrdWfKpiData.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public class OrdWfKpiData implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 7747959981217665284L;

    /**
     * 主键、自增长
     */
    private Long              id;
    /**
     * 网单编号
     */
    private String            cOrderSn;
    /**
     * 订单id，索引
     */
    private Long              orderId;
    /**
     * 网单id，索引
     */
    private Long              orderProductId;

    /**
     * 退换货单id
     */
    private Long              orsId;

    /**
     * 来源(渠道)名称
     */
    private String            sourceName;
    /**
     * 区域名称
     */
    private String            areaName;
    /**
     * 工贸名称
     */
    private String            tradeName;
    /**
     * 中心(仓库)名称
     */
    private String            centerName;
    /**
     * 节点
     */
    private String            nodeType;
    /**
     * 是否超时免单 0未设置 1是 2否
     */
    private Integer           isTimeoutFree;
    /**
     * 订单类型
     */
    private Integer           orderType;
    /**
     * 日期,索引
     */
    private Date              date;
    /**
     * 是否完成:0:未完成;1:已完成
     */
    private Integer           isFinish;
    /**
     * 超时类型: 0:已完成未超期;1:已完成超期;2：未完成超期; 3:未完成未超期
     */
    private Integer           timeoutType;
    /**
     * 应完成时间
     */
    private Date              mustFinishTime;
    /**
     * 实际完成时间
     */
    private Date              realFinishTime;
    /**
     * 超时天数
     */
    private Integer           timeoutDay;
    /**
     * 店铺ID
     */
    private Integer           storeId;

    /**
     * 获取 主键、自增长
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置 主键、自增长
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取 网单编号
     * @return
     */
    public String getcOrderSn() {
        return cOrderSn;
    }

    /**
     * 设置 网单编号
     * @param cOrderSn
     */
    public void setcOrderSn(String cOrderSn) {
        this.cOrderSn = cOrderSn;
    }

    /**
     * 获取 订单id，索引
     * @return
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置 订单id，索引
     * @param orderId
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取 网单id，索引
     * @return
     */
    public Long getOrderProductId() {
        return orderProductId;
    }

    /**
     * 设置 网单id，索引
     * @param orderProductId
     */
    public void setOrderProductId(Long orderProductId) {
        this.orderProductId = orderProductId;
    }

    /**
     * 获取 退换货单id
     * @return
     */
    public Long getOrsId() {
        return orsId;
    }

    /**
     * 设置 退换货单id
     * @param orderProductId
     */
    public void setOrsId(Long orsId) {
        this.orsId = orsId;
    }

    /**
     * 获取 来源(渠道)名称
     * @return
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * 设置 来源(渠道)名称
     * @param sourceName
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    /**
     * 获取 区域名称
     * @return
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * 设置 区域名称
     * @param areaName
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * 获取 工贸名称
     * @return
     */
    public String getTradeName() {
        return tradeName;
    }

    /**
     * 设置 工贸名称
     * @param tradeName
     */
    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    /**
     * 获取 中心(仓库)名称
     * @return
     */
    public String getCenterName() {
        return centerName;
    }

    /**
     * 设置 中心(仓库)名称
     * @param centerName
     */
    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    /**
     * 获取 节点
     * @return
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * 设置 节点
     * @param nodeType
     */
    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * 获取 是否超时免单 0未设置 1是 2否
     * @return
     */
    public Integer getIsTimeoutFree() {
        return isTimeoutFree;
    }

    /**
     * 设置 是否超时免单 0未设置 1是 2否
     * @param isTimeoutFree
     */
    public void setIsTimeoutFree(Integer isTimeoutFree) {
        this.isTimeoutFree = isTimeoutFree;
    }

    /**
     * 获取 订单类型
     * @return
     */
    public Integer getOrderType() {
        return orderType;
    }

    /**
     * 设置 订单类型
     * @param orderType
     */
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    /**
     * 获取 日期,索引
     * @return
     */
    public Date getDate() {
        return date;
    }

    /**
     * 设置 日期,索引
     * @param date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 获取 是否完成:0:未完成;1:已完成
     * @return
     */
    public Integer getIsFinish() {
        return isFinish;
    }

    /**
     * 设置 是否完成:0:未完成;1:已完成
     * @param isFinish
     */
    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }

    /**
     * 获取 超时类型: 0:已完成未超期;1:已完成超期;2：未完成超期; 3:未完成未超期
     * @return
     */
    public Integer getTimeoutType() {
        return timeoutType;
    }

    /**
     * 设置 超时类型: 0:已完成未超期;1:已完成超期;2：未完成超期; 3:未完成未超期
     * @param timeoutType
     */
    public void setTimeoutType(Integer timeoutType) {
        this.timeoutType = timeoutType;
    }

    /**
     * 获取 应完成时间
     * @return
     */
    public Date getMustFinishTime() {
        return mustFinishTime;
    }

    /**
     * 设置 应完成时间
     * @param mustFinishTime
     */
    public void setMustFinishTime(Date mustFinishTime) {
        this.mustFinishTime = mustFinishTime;
    }

    /**
     * 获取 实际完成时间
     * @return
     */
    public Date getRealFinishTime() {
        return realFinishTime;
    }

    /**
     * 设置 实际完成时间
     * @param realFinishTime
     */
    public void setRealFinishTime(Date realFinishTime) {
        this.realFinishTime = realFinishTime;
    }

    /**
     * 获取 超时天数
     * @return
     */
    public Integer getTimeoutDay() {
        return timeoutDay;
    }

    /**
     * 设置 超时天数
     * @param timeoutDay
     */
    public void setTimeoutDay(Integer timeoutDay) {
        this.timeoutDay = timeoutDay;
    }

    /**
     * 获取 店铺ID
     * @return
     */
    public Integer getStoreId() {
        return storeId;
    }

    /**
     * 设置 店铺ID
     * @param storeId
     */
    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

}