package com.haier.svc.service;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.BdmOrder;
import java.util.List;
import java.util.Map;

public interface BdmOrderService {
    public ServiceResult<List<BdmOrder>> findBdmOrder(Map<String, Object> params);

    public int insertBdmOrder(Map<String, Object> params);

    public int deleteBdmOrder(Map<String, Object> params);

}
