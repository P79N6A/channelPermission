package com.haier.svc.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.GateItem;
import com.haier.purchase.data.model.GateOfLimitItem;
import com.haier.purchase.data.model.GateOfStockExceedCatchItem;
import com.haier.purchase.data.model.GateOfStockExceedItem;

public interface GateService {
	/**
     * 额度闸口检索
     * @param params
     * @return
     */
    public ServiceResult<List<GateOfLimitItem>> findGateOfLimit(Map<String, Object> params);

	/**
     * 时间闸口检索
     * @param params
     * @return
     */
    public ServiceResult<List<GateItem>> findGateItem(Map<String, Object> params);

    /**
     * 库存超期闸口检索
     * @param params
     * @return
     */
    public ServiceResult<List<GateOfStockExceedItem>> findGateOfStockExceed(Map<String, Object> params);

    /**
     * 库存超期闸口缓存表检索
     * @param params
     * @return
     */
    public ServiceResult<List<GateOfStockExceedCatchItem>> findGateOfStockExceedCatch(Map<String, Object> params);


}
