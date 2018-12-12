package com.haier.purchase.data.service;

import java.util.Map;

import com.haier.purchase.data.model.GoodsBackInfoResponse;


public interface PurchaseVomOrderService {

	public Integer updateLeaderReturnStatus(Map<String, Object> paramMap);
	
	public GoodsBackInfoResponse findGoodsBackInfo(Map<String, Object> paramMap);
	
	public Integer updateCrmReturnInfo(Map<String, Object> paramMap);
	
	public GoodsBackInfoResponse findCrmGoodsBackInfo(Map<String, Object> paramMap);
	
	public void updateCgoGenuineRejectStatusVom(Map<String, Object> paramMap);
	
	public void updateCrmGenuineRejectStatus(Map<String, Object> paramMap);
	
	public String getFlowFlagBySiOuInfo(Map<String, Object> paramMap);
	
	public String getFlowFlagByCrmReturnInfo(Map<String, Object> paramMap);
}
