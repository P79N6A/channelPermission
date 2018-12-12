package com.haier.shop.dao.shopwrite;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExternalOrdersWriteDao {
    /**
     * 网单发货后，更新发货标志
     * @param orderId
     * @return
     */
    Integer updateAtferShipped(Integer orderId);

}
