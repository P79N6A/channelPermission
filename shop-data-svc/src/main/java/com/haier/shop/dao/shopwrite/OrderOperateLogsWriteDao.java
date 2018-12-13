package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderOperateLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Mapper
public interface OrderOperateLogsWriteDao {

    Integer insert(OrderOperateLogs orderOperateLogs);

    void batchInsert(List<OrderOperateLogs> orderOperateLogsList);
    public void insertLog(@Param("map") Map<String,Object> map);

    /**
     * 根据id更新操作人
     * @param id
     * @param user
     * @return
     */
    Integer updateOperator(@Param("id")Integer id, @Param("user") String user);
}
