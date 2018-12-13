package com.haier.eis.dao.eis;

import java.util.List;

import java.util.Map;
import org.apache.ibatis.annotations.Param;

import com.haier.eis.model.EisInterfaceFinance;

public interface EisInterfaceFinanceDao {
	    EisInterfaceFinance getByTransQueueId(@Param("les_stock_trans_queue_id") Integer transQueueId);

	    List<EisInterfaceFinance> getByStatus(@Param("status") Integer status);

	    List<EisInterfaceFinance> getByParams(Map<String,Object> params);

	    Integer insert(EisInterfaceFinance interfaceFinance);

	    Integer update(EisInterfaceFinance interfaceFinance);

			List<Integer> getIdsByOrderSn(@Param("corder_sn") String cOrderSn);

			Integer updateEisInterfaceFinance(@Param("les_stock_trans_queue_ids") List<Integer> les_stock_trans_queue_ids);
}
