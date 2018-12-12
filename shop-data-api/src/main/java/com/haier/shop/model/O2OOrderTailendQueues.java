package com.haier.shop.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * o2o已付尾款订单列表
 * @author XinM
 *
 */

public class O2OOrderTailendQueues implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer           id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 定金订单id
     */
    private Integer depositOrderId;

    public Integer getDepositOrderId() {
        return depositOrderId;
    }

    public void setDepositOrderId(Integer depositOrderId) {
        this.depositOrderId = depositOrderId;
    }

    /**
     * 定金网单id
     */
    private Integer depositOrderProductId;

    public Integer getDepositOrderProductId() {
        return depositOrderProductId;
    }

    public void setDepositOrderProductId(Integer depositOrderProductId) {
        this.depositOrderProductId = depositOrderProductId;
    }

    /**
     * 尾款订单id
     */
    private Integer tailOrderId;

    public Integer getTailOrderId() {
        return tailOrderId;
    }

    public void setTailOrderId(Integer tailOrderId) {
        this.tailOrderId = tailOrderId;
    }

    /**
     * 尾款网单ID
     */
    private Integer tailOrderProductId;

    public Integer getTailOrderProductId() {
        return tailOrderProductId;
    }

    public void setTailOrderProductId(Integer tailOrderProductId) {
        this.tailOrderProductId = tailOrderProductId;
    }

    /**
     * 添加时间
     */
    private Long addTime;

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    /**
     * 推送数据
     */
    private String pushData;

    public String getPushData() {
        return pushData;
    }

    public void setPushData(String pushData) {
        this.pushData = pushData;
    }

    /**
     * 反馈数据
     */
    private String returnData;

    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

    /**
     * 是否成功
     */
    private Integer success;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    /**
     * 推送次数，大于30就不在推送
     */
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 推送成功时间
     */
    private Long successTime;

    public Long getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(Long successTime) {
        this.successTime = successTime;
    }

    /**
     * 最晚推送时间
     */
    private Long lastTryTime;

    public Long getLastTryTime() {
        return lastTryTime;
    }

    public void setLastTryTime(Long lastTryTime) {
        this.lastTryTime = lastTryTime;
    }

    /**
     * 返回信息
     */
    private String lastMessage;

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    /**
     * 数据更新时间
     */
    private Timestamp modifyTime;

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

}
