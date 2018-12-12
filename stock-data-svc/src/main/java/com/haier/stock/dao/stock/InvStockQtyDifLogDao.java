package com.haier.stock.dao.stock;


import com.haier.stock.model.InvStockQtyDifLog;

import java.util.List;

public interface InvStockQtyDifLogDao {
	
	List<InvStockQtyDifLog> queryDifStockQty ();

	Integer insert ( InvStockQtyDifLog difLog );

}
