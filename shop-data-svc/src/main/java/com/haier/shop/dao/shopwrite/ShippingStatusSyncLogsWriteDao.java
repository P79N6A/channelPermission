package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.ShippingStatusSyncLogs;
import org.apache.ibatis.annotations.Mapper;

/*
* 作者:张波
* 2018/1/3
* */
@Mapper
public interface ShippingStatusSyncLogsWriteDao {

    /**
     * 新增对象
     * @param log
     * @return 影响行数
     */
    Integer insert(ShippingStatusSyncLogs log);
}
