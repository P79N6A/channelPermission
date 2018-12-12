package com.haier.eis.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 库存业务处理队列。(3W正品退仓)
 * @author wangp-c
 *
 */
public class EisStockBusinessQueue3W implements Serializable {
    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    private Integer           id;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    private Integer stockTransQueue3WId;

    
	/**
	 * 获取 les_stock_trans_queue_3w标识。
	 * @return
	 */
    public Integer getStockTransQueue3WId() {
		return stockTransQueue3WId;
	}
    
    /**
     * 设置 les_stock_trans_queue_3w标识
     * @param stockTransQueue3WId
     */
	public void setStockTransQueue3WId(Integer stockTransQueue3WId) {
		this.stockTransQueue3WId = stockTransQueue3WId;
	}

	private Date addTime;

    public Date getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Date value) {
        this.addTime = value;
    }

}