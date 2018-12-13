package com.haier.shop.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderOperateLogsReadDao;
import com.haier.shop.dao.shopwrite.OrderOperateLogsWriteDao;
import com.haier.shop.model.OrderOperateLogs;
import com.haier.shop.service.ShopOrderOperateLogsService;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Service
public class OrderOperateLogsServiceImpl implements ShopOrderOperateLogsService {
	@Autowired
	OrderOperateLogsReadDao orderOperateLogsReadDao;
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

	@Override
	public List<OrderOperateLogs> getProductIdVdiew(String productId) {
		// TODO Auto-generated method stub
		return orderOperateLogsReadDao.getProductIdVdiew(productId);
	}

	@Override
	public OrderOperateLogs getLogsNew(Integer orderProductId) {
		return orderOperateLogsReadDao.getLogsNew(orderProductId);
	}
	public void insertLog(Map<String,Object> map){
		orderOperateLogsWriteDao.insertLog(map);
	}

	@Override
	public Integer updateOperator(Integer id, String user) {
		return orderOperateLogsWriteDao.updateOperator(id, user);
	}
}
