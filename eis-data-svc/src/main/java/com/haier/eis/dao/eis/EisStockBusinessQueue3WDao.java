package com.haier.eis.dao.eis;

import java.util.List;

import com.haier.eis.model.EisStockBusinessQueue3W;


public interface EisStockBusinessQueue3WDao {

    Integer insert(EisStockBusinessQueue3W	 stockBusinessQueue3W);

    Integer delete(EisStockBusinessQueue3W stockBusinessQueue3W);

    List<EisStockBusinessQueue3W> getTops(Integer topx);
}
