package com.haier.eis.service;

import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.model.LesStockTransQueue3W;

public interface EisStockTrans2ExternalService {

    LesStockTransQueue getByLesBillNo( String billNo);

    LesStockTransQueue getByOrderSn(String orderSn);

    Integer insert(LesStockTransQueue lesStockTransQueue);
    Integer insert2(LesStockTransQueue3W lesStockTransQueue);

}
