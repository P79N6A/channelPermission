package com.haier.distribute.data.dao.distribute;

import org.apache.ibatis.annotations.Param;

public interface OrderDifferencePriceRefundDao {

    /**
     * 根据网单号获取有效申请差价单数量
     * @param cOrderSn
     * @return
     */
    int getValidCountByCorderSn(@Param("cOrderSn") String cOrderSn);

}