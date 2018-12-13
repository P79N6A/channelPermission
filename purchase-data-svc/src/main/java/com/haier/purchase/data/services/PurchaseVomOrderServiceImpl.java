package com.haier.purchase.data.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.purchase.VomOrderDao;
import com.haier.purchase.data.model.GoodsBackInfoResponse;
import com.haier.purchase.data.service.PurchaseVomOrderService;

@Service
public class PurchaseVomOrderServiceImpl implements PurchaseVomOrderService{

	@Autowired
	VomOrderDao vomOrderDao;
	
	@Override
	public Integer updateLeaderReturnStatus(Map<String, Object> paramMap){
		return vomOrderDao.updateLeaderReturnStatus(paramMap);
	}

	@Override
	public GoodsBackInfoResponse findGoodsBackInfo(Map<String, Object> paramMap){
		return vomOrderDao.findGoodsBackInfo(paramMap);
	}

	@Override
	public Integer updateCrmReturnInfo(Map<String, Object> paramMap){
		return vomOrderDao.updateCrmReturnInfo(paramMap);
	}

	@Override
	public GoodsBackInfoResponse findCrmGoodsBackInfo(Map<String, Object> paramMap){
		return vomOrderDao.findCrmGoodsBackInfo(paramMap);
	}

	@Override
	public void updateCgoGenuineRejectStatusVom(Map<String, Object> paramMap){
		vomOrderDao.updateCgoGenuineRejectStatusVom(paramMap);
	}

	@Override
	public void updateCrmGenuineRejectStatus(Map<String, Object> paramMap){
		vomOrderDao.updateCrmGenuineRejectStatus(paramMap);
	}

	@Override
	public String getFlowFlagBySiOuInfo(Map<String, Object> paramMap){
		return vomOrderDao.getFlowFlagBySiOuInfo(paramMap);
	}

	@Override
	public String getFlowFlagByCrmReturnInfo(Map<String, Object> paramMap){
		return vomOrderDao.getFlowFlagByCrmReturnInfo(paramMap);
	}

	@Override
	public String getStorageIdByCrmReturnInfo(Map<String, Object> paramMap) {
		return vomOrderDao.getStorageIdByCrmReturnInfo(paramMap);
	}
}
