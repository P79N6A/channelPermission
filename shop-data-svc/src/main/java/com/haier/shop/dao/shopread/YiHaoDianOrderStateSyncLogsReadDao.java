package com.haier.shop.dao.shopread;

import com.haier.shop.model.YiHaoDianOrderStateSyncLogs;
import org.apache.ibatis.annotations.Mapper;

/*
* 作者:张波
* 2018/1/3
* */
@Mapper
public interface YiHaoDianOrderStateSyncLogsReadDao {
    /**
     * 根据订单编号，获取对象
     * @param orderSn 订单编号
     * @return
     */
    YiHaoDianOrderStateSyncLogs getByOrderSn(String orderSn);
}
