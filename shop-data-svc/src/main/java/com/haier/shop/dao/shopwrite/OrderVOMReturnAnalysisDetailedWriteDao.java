package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderVOMReturnAnalysisDetailed;
import org.apache.ibatis.annotations.Mapper;

/**
 * 接收VOM推送到CBS数据之后解析出来的明细 
 * @author wukunyang
 *
 */
@Mapper
public interface OrderVOMReturnAnalysisDetailedWriteDao {
    int insert(OrderVOMReturnAnalysisDetailed record);
}