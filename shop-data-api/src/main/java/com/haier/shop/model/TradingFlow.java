package com.haier.shop.model;

import java.io.Serializable;
import java.util.Date;

public class TradingFlow implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2379850144151337206L;

	private Integer id;

    private Date createTime;

    private String shopName;

    private Integer startMoney;

    private Integer endMoney;
    
    private String time;
    
    private Integer incomeMoney;
    
    private Integer expenditureMoney;
    
    
    
    public Integer getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(Integer incomeMoney) {
		this.incomeMoney = incomeMoney;
	}

	public Integer getExpenditureMoney() {
		return expenditureMoney;
	}

	public void setExpenditureMoney(Integer expenditureMoney) {
		this.expenditureMoney = expenditureMoney;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public Integer getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(Integer startMoney) {
        this.startMoney = startMoney;
    }

    public Integer getEndMoney() {
        return endMoney;
    }

    public void setEndMoney(Integer endMoney) {
        this.endMoney = endMoney;
    }
}