package com.haier.shop.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CostPools implements Serializable {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -8121679337147158379L;

    public static final int COSTPOOL_SC_CHANNEL   = 1;//商城
    public static final int COSTPOOL_TB_CHANNEL   = 2;//天猫
    public static final int COSTPOOL_DSPT_CHANNEL = 3;//电商平台
    public static final int COSTPOOL_SG_CHANNEL   = 4;//微店

    public static Integer getChannelValue(String channel) {
        if ("SC".equalsIgnoreCase(channel)) {
            return COSTPOOL_SC_CHANNEL;
        } else if ("TB".equalsIgnoreCase(channel)) {
            return COSTPOOL_TB_CHANNEL;
        } else if ("DSPT".equalsIgnoreCase(channel)) {
            return COSTPOOL_DSPT_CHANNEL;
        } else if ("SG".equalsIgnoreCase(channel)) {
            return COSTPOOL_SG_CHANNEL;
        }
        return null;
    }

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer siteId;

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    private Integer addTime;

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
    }

    private String masterName;

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    private Integer yearMonth;

    public Integer getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
    }

    private Integer batch;

    public Integer getBatch() {
        return batch;
    }

    public void setBatch(Integer batch) {
        this.batch = batch;
    }

    private Integer channel;

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    private String chanYe;

    public String getChanYe() {
        return chanYe;
    }

    public void setChanYe(String chanYe) {
        this.chanYe = chanYe;
    }

    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    private BigDecimal balanceAmount;

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    private Integer editTime;

    public Integer getEditTime() {
        return editTime;
    }

    public void setEditTime(Integer editTime) {
        this.editTime = editTime;
    }

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}