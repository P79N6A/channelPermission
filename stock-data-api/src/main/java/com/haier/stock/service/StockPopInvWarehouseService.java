package com.haier.stock.service;

import java.util.List;

import com.haier.stock.model.InvWarehouse;
import com.haier.stock.model.InvWarehouseInfo;
import com.haier.stock.model.PopInvWarehouse;

public interface StockPopInvWarehouseService<T> {
	public 	int deleteByPrimaryKey(String whCode);

	public   int insert(PopInvWarehouse record);

	public   int insertSelective(PopInvWarehouse record);

	public   PopInvWarehouse selectByPrimaryKey(String whCode);

	public  int updateByPrimaryKeySelective(PopInvWarehouse record);

	public   int updateByPrimaryKey(PopInvWarehouse record);

	public   long checkCodeSame(String whCode);

	public   long checkNameSame(String whName);

	public  List<PopInvWarehouse> exportWarehouse( PopInvWarehouse entity);
	
	List<T> getPageByCondition(T entity,int start,int rows);
	
	 long getPagerCount(T entity);

	public List<InvWarehouse> findCenter();
}
