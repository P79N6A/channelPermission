package com.haier.shop.service;



import com.haier.shop.model.OrderRepairLogs;

import java.util.List;

/**
 * 退货日志 记录
 * 吴坤洋 2017-11-3
 * @author wukunyang
 *
 */
public interface ShopOrderRepairLogsService {
	int insert(OrderRepairLogs record); //插入
	int getNextValId(); //生成主键
	List<OrderRepairLogs> queryLogs(String id);//查看退货操作日志
}
