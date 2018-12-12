package com.haier.stock.service;


import com.haier.stock.model.InvStockQtyDifLog;

import java.util.List;

public interface InvStockQtyDifLogService {
	
	List<InvStockQtyDifLog> queryDifStockQty ();

	Integer insert ( InvStockQtyDifLog difLog );

}
