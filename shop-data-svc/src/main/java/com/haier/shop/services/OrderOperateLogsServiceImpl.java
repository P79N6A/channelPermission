package com.haier.shop.services;

import java.util.List;

import com.haier.shop.dao.shopwrite.OrderOperateLogsWriteDao;
import com.haier.shop.model.OrderOperateLogs;
import com.haier.shop.service.ShopOrderOperateLogsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Service
public class OrderOperateLogsServiceImpl implements ShopOrderOperateLogsService {

    @Autowired
    OrderOperateLogsWriteDao orderOperateLogsWriteDao;

    @Override
    public Integer insert(OrderOperateLogs orderOperateLogs) {
        return orderOperateLogsWriteDao.insert(orderOperateLogs);
    }

	@Override
	public void batchInsert(List<OrderOperateLogs> orderOperateLogsList) {
		orderOperateLogsWriteDao.batchInsert(orderOperateLogsList);
	}
}
