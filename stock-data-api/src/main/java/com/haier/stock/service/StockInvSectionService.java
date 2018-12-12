package com.haier.stock.service;

import java.util.List;

import com.haier.stock.model.InvSection;

public interface StockInvSectionService<T> {
	 public  int deleteByPrimaryKey(String secCode);

	 public    int insert(InvSection record);

	 public  int insertSelective(InvSection record);

	 public    InvSection selectByPrimaryKey(String secCode);

	 public   int updateByPrimaryKeySelective(InvSection record);

	 public  int updateByPrimaryKey(InvSection record);
	    
	 public  long checkSame(String secCode);

	 public List<InvSection> exportSection(InvSection entity);
	    
	 public  InvSection getBySecCode(String secCode);
	    
	    /**
	     * 通过渠道编码获取在用的库存列表
	     * @param whCode
	     * @param channelCode
	     * @return
	     */
	 public   List<InvSection> getByChannelCode(String whCode,
	                                      String channelCode);
	    
	    /**
	     * 根据状态获取库位列表
	     * @param status 状态，可为空
	     * @return
	     */
	 public  List<InvSection> getByStatus(Integer status);

	    /**
	     * 根据条件获取响应库位
	     * @param secCode
	     * @return
	     */
	 public  InvSection getInvSection(String secCode,
	                             String channelCode,
	                              String itemProperty);
	    


	 public  InvSection getByLesSecCodeAndChannelCode(String lesSecCode,
	                                             String channelCode);
	 List<T> getPageByCondition(T entity,int start,int rows);
		
	 long getPagerCount(T entity);
}
