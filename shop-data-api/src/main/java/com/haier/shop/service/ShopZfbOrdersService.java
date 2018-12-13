package com.haier.shop.service;


import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.shop.dto.ZfbOrderMatchingDto;
import com.haier.shop.model.QueryZFBOrderParameter;
import com.haier.shop.model.ZfbOrdersDetails;
import com.haier.shop.model.ZfbOrdersDetailsMatching;


public interface ShopZfbOrdersService {
	//支付宝 流水匹配orders表
	List<ZfbOrderMatchingDto> getFindQueryOrderList(QueryZFBOrderParameter queryOrder);

	Integer getFindQueryOrderListCount(QueryZFBOrderParameter queryOrder);

	
	//支付宝 流水
	List<ZfbOrdersDetails> getFindQueryZfbOrderList(QueryZFBOrderParameter queryOrder);

	Integer getFindQueryZfbOrderListCount(QueryZFBOrderParameter queryOrder);
	
	//支付宝 报表
	List<ZfbOrdersDetails> getReportFormList(QueryZFBOrderParameter queryOrder);

	Integer getReportFormListCount(QueryZFBOrderParameter queryOrder);


}
