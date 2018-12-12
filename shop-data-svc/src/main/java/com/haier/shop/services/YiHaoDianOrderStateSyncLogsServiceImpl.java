package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.YiHaoDianOrderStateSyncLogsReadDao;
import com.haier.shop.dao.shopwrite.YiHaoDianOrderStateSyncLogsWriteDao;
import com.haier.shop.model.YiHaoDianOrderStateSyncLogs;
import com.haier.shop.service.YiHaoDianOrderStateSyncLogsService;

/*
* 作者:张波
* 2018/1/3
* */
@Service
public class YiHaoDianOrderStateSyncLogsServiceImpl implements YiHaoDianOrderStateSyncLogsService {
    @Autowired
    YiHaoDianOrderStateSyncLogsReadDao yiHaoDianOrderStateSyncLogsReadDao;
    @Autowired
    YiHaoDianOrderStateSyncLogsWriteDao yiHaoDianOrderStateSyncLogsWriteDao;

    /**
     * 根据订单编号，获取对象
     *
     * @param orderSn 订单编号
     * @return
     */
    @Override
    public YiHaoDianOrderStateSyncLogs getByOrderSn(String orderSn) {
        return yiHaoDianOrderStateSyncLogsReadDao.getByOrderSn(orderSn);
    }

    /**
     * 创建对象
     *
     * @param log
     * @return
     */
    @Override
    public Integer insert(YiHaoDianOrderStateSyncLogs log) {
        return yiHaoDianOrderStateSyncLogsWriteDao.insert(log);
    }
}
