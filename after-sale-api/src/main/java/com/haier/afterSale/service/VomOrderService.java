package com.haier.afterSale.service;

import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.GoodsBackInfoResponse;

public interface VomOrderService {
	ServiceResult<GoodsBackInfoResponse> getGoodsBackInfo(Map<String,Object> paramMap);
}
