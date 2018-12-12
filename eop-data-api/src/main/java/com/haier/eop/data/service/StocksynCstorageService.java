package com.haier.eop.data.service;

import java.util.List;

import com.haier.eop.data.model.StocksynCstorage;
import com.haier.eop.data.model.StocksynCstorage;


public interface StocksynCstorageService {
	/***
	 * 根据sCode ，source查询id
	 * @param entity
	 * @return
	 */
	StocksynCstorage getId(StocksynCstorage entity);
	List<StocksynCstorage> getsCode(String source);
	
	  int deleteByPrimaryKey(Integer id);
	    int insert(StocksynCstorage record);
	    int updateByPrimaryKeySelective(StocksynCstorage record);
	    List<StocksynCstorage> Listf(StocksynCstorage entity, int start, int rows);
	    int getPagerCountS(StocksynCstorage entity);
}