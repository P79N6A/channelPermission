package com.haier.eis.service;

import java.util.List;


import com.haier.eis.model.EisInterfaceFinance;
import java.util.Map;

public interface EisInterfaceFinanceService {
	  EisInterfaceFinance getByTransQueueId(Integer transQueueId);

	    List<EisInterfaceFinance> getByStatus(Integer status);

	    List<EisInterfaceFinance> getByParams(Map<String,Object> params);

	    Integer insert(EisInterfaceFinance interfaceFinance);

	    Integer update(EisInterfaceFinance interfaceFinance);

	List<Integer> getIdsByOrderSn(String cOrderSn);

	Integer updateEisInterfaceFinance(List<Integer> les_stock_trans_queue_ids);
}
