package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderOperateLogs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Mapper
public interface OrderOperateLogsWriteDao {

    Integer insert(OrderOperateLogs orderOperateLogs);

    void batchInsert(List<OrderOperateLogs> orderOperateLogsList);
}
