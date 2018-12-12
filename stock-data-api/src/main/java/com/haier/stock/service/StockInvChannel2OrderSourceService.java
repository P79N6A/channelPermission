package com.haier.stock.service;

import java.util.List;
import java.util.Map;


import com.haier.stock.model.InvChannel2OrderSource;

public interface StockInvChannel2OrderSourceService {
	  public InvChannel2OrderSource getBySource(String source);

	    /**
	     * 取得所有订单渠道数据
	     * @return
	     */
	    public List<InvChannel2OrderSource> getAllOrder2ChannelSource();

	    public InvChannel2OrderSource getSapChannelCodeAndCustomerCode(String ordeSourceCode);

	/**
	 * 获取渠道代码与名称对应列表
	 */
	List<Map<String, Object>> getChannelNames();
}
