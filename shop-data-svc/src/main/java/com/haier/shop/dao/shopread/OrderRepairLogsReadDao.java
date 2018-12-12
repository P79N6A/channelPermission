package com.haier.shop.dao.shopread;



import com.haier.shop.model.OrderRepairLogs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 退货日志 记录
 * 吴坤洋 2017-11-3
 * @author wukunyang
 *
 */
@Mapper
public interface OrderRepairLogsReadDao {
	List<OrderRepairLogs> queryLogs(String id);//查看退货操作日志

	int getNextValId(); //生成主键
}
