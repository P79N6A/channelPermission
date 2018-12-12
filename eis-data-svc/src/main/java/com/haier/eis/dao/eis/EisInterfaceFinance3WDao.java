package com.haier.eis.dao.eis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.eis.model.EisInterfaceFinance3w;


public interface EisInterfaceFinance3WDao {
    EisInterfaceFinance3w getByTransQueue3WId(@Param("les_stock_trans_queue_3w_id") Integer transQueue3WId,@Param("interfaceCode")String interfaceCode);

    List<EisInterfaceFinance3w> getByStatus(@Param("status") Integer status);

    Integer insert(EisInterfaceFinance3w interfaceFinance);

    Integer update(EisInterfaceFinance3w interfaceFinance);
}
