package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderOperateLogs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderOperateLogsReadDao {
	List<OrderOperateLogs> getProductIdVdiew(String productId);//查询网单操作日志

	/**
	 * 根据网单id获取日志表最新一条日志
	 * @param orderProductId
	 * @return
	 */
	OrderOperateLogs getLogsNew(Integer orderProductId);
}
