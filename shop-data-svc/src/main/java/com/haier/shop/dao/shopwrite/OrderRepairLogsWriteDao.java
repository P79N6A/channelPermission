package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderRepairLogs;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lichunsheng
 * @create 2018-01-04
 **/
@Mapper
public interface OrderRepairLogsWriteDao {

    int insert(OrderRepairLogs orderRepairLogs);

    int getNextValId(); //生成主键
}
