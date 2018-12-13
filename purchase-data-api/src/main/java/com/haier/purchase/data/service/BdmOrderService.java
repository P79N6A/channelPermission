package com.haier.purchase.data.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.BdmOrder;

public interface BdmOrderService {
    public ServiceResult<List<BdmOrder>> findBdmOrder(Map<String, Object> params);

    public int insertBdmOrder(Map<String, Object> params);

    public int deleteBdmOrder(Map<String, Object> params);

}
