package com.haier.distribute.data.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/7 0007
 * \* Time: 10:35
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
public class TChannelsInfo implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3431631072818009195L;
    private Long id;
    
    private String channelCode;

    private String channelName;

    private BigDecimal moneyLimit;

    private Integer sort;

    private String remark;

    private BigDecimal moneyAlert;

    private BigDecimal moneyLock;

    private String createBy;

    private Date createTime;

    private Date updateTime;

    private String updateBy;
    /**
     * 操作
     */
    public String operation = null;

    public void setOperation(String operation) {
        this.operation = operation;
    }
    private BigDecimal moneyMin;
    private BigDecimal moneyMax;


    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public BigDecimal getMoneyLimit() {
        return moneyLimit;
    }

    public void setMoneyLimit(BigDecimal moneyLimit) {
        this.moneyLimit = moneyLimit;
    }

    public BigDecimal getMoneyAlert() {
        return moneyAlert;
    }

    public void setMoneyAlert(BigDecimal moneyAlert) {
        this.moneyAlert = moneyAlert;
    }

    public BigDecimal getMoneyLock() {
        return moneyLock;
    }

    public void setMoneyLock(BigDecimal moneyLock) {
        this.moneyLock = moneyLock;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public String getuTime() {
        return uTime;
    }

    public void setuTime(String uTime) {
        this.uTime = uTime;
    }

    public BigDecimal getMoneyMin() {
        return moneyMin;
    }

    public void setMoneyMin(BigDecimal moneyMin) {
        this.moneyMin = moneyMin;
    }
    public BigDecimal getMoneyMax() {
        return moneyMax;
    }

    public void setMoneyMax(BigDecimal moneyMax) {
        this.moneyMax = moneyMax;
    }
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	private String cTime;
    private String uTime;

}