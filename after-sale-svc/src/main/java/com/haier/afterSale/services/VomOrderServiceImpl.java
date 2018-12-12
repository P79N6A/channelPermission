package com.haier.afterSale.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.afterSale.model.VOMOrderModel;
import com.haier.afterSale.service.VomOrderService;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.GoodsBackInfoResponse;
@Service
public class VomOrderServiceImpl implements VomOrderService{
	@Autowired
	private VOMOrderModel          vomOrderModel;
	
	
    public ServiceResult<GoodsBackInfoResponse> getGoodsBackInfo(Map<String, Object> paramMap) {
        ServiceResult<GoodsBackInfoResponse> serviceResult = new ServiceResult<GoodsBackInfoResponse>();
        serviceResult = vomOrderModel.getGoodsBackInfo(paramMap);
        return serviceResult;
    }
}
