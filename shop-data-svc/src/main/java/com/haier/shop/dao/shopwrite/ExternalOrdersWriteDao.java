package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.ExternalOrders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExternalOrdersWriteDao {
    /**
     * 网单发货后，更新发货标志
     * @param orderId
     * @return
     */
    Integer updateAtferShipped(Integer orderId);

    /**
     * 添加
     * @param extOrder
     * @return
     */
    int insertExternalOrdersInfo(ExternalOrders extOrder);

    /**
     * 修改
     * @param sourceOrderSn
     * @param orderState
     * @param errorLog
     * @return
     */
    Integer updateExternalOrdersInfo(@Param("sourceOrderSn") String sourceOrderSn,
                                 @Param("orderState") Integer orderState,
                                 @Param("errorLog") String errorLog);
}
