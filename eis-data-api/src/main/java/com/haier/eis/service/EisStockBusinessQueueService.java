package com.haier.eis.service;


import java.util.List;

import com.haier.eis.model.EisStockBusinessQueue;


/**
 * 2018-1-2
 * @author wukunyang
 *
 */
public interface EisStockBusinessQueueService {

    Integer insert(EisStockBusinessQueue stockBusinessQueue);

    List<EisStockBusinessQueue> getTops(int i);

    void delete(EisStockBusinessQueue queue);
}
