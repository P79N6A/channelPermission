package com.haier.stock.service;


import com.haier.stock.model.InvBaseStockDiffLog;

public interface InvBaseStockDiffLogService{

	/**
	 * 插入操作
	 * @param invBaseStockDiffLog
	 */
	Object insert(InvBaseStockDiffLog invBaseStockDiffLog);
	
	/**
	 * 批量删除某几天之前的数据
	 * @param date
	 * @param day
	 * @return
	 */
	int batchDelete(String date, Integer day); 
	
	/**
	 * 获取最大修改时间
	 * @return
	 */
	String getMaxTime();
	
}
