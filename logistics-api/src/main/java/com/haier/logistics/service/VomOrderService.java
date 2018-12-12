package com.haier.logistics.service;

import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.GoodsBackInfoResponse;
import com.haier.shop.model.VOMCancelOrderRequire;
import com.haier.shop.model.VOMOrderResponse;
//import com.haier.svc.bean.pop.VOMCancelOrderRequire;
//import com.haier.svc.bean.pop.VOMOrderResponse;
//import com.haier.svc.bean.pop.VOMSynOrderRequire;
import com.haier.shop.model.VOMSynOrderRequire;

public interface VomOrderService {

	ServiceResult<Boolean> synVomStatus(Map<String,Object> paramMap);
	
	ServiceResult<Boolean> returnVomStatus(Map<String,Object> paramMap);
	
	ServiceResult<GoodsBackInfoResponse> getGoodsBackInfo(Map<String,Object> paramMap);
	
	
	ServiceResult<Boolean> stopCgoGenuineRejectList(Map<String, Object> paramMap);
	
	
	ServiceResult<Boolean> stopCrmGenuineRejectList(Map<String, Object> paramMap);
}
