package com.haier.eis.dao.eis;

import org.apache.ibatis.annotations.Param;

import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.model.LesStockTransQueue3W;



public interface EisStockTrans2ExternalDao {

    LesStockTransQueue getByLesBillNo(@Param("lesBillNo") String billNo);

    LesStockTransQueue getByOrderSn(@Param("order_sn") String orderSn);

    Integer insert(LesStockTransQueue lesStockTransQueue);
    Integer insert2(LesStockTransQueue3W lesStockTransQueue);

}
