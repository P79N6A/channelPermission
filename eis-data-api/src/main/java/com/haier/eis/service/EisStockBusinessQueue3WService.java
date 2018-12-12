package com.haier.eis.service;

import java.util.List;

import com.haier.eis.model.EisStockBusinessQueue3W;



public interface EisStockBusinessQueue3WService {

    Integer insert(EisStockBusinessQueue3W	 stockBusinessQueue3W);

    Integer delete(EisStockBusinessQueue3W stockBusinessQueue3W);

    List<EisStockBusinessQueue3W> getTops(Integer topx);
}
