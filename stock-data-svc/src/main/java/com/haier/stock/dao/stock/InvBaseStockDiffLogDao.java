package com.haier.stock.dao.stock;

import com.haier.stock.model.InvBaseStockDiffLog;
import org.apache.ibatis.annotations.Param;



public interface InvBaseStockDiffLogDao{

	/**
	 * 插入操作
	 * @param invBaseStockDiffLog
	 * @return 
	 */
	Object insert(InvBaseStockDiffLog invBaseStockDiffLog);
	
	/**
	 * 批量删除某几天之前的数据
	 * @param date
	 * @param day
	 * @return
	 */
	int batchDelete(@Param("date") String date,@Param("day") Integer day); 
	
	/**
	 * 获取最大修改时间
	 * @return
	 */
	String getMaxTime();
	
}
