package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderVOMReturnAnalysis;
import org.apache.ibatis.annotations.Mapper;

/**
 * 接收VOM主动推送过来解析之后的数据。（主表单）
 * @author wukunyang
 *
 */
@Mapper
public interface OrderVOMReturnAnalysisWriteDao {
    int insert(OrderVOMReturnAnalysis record);
}