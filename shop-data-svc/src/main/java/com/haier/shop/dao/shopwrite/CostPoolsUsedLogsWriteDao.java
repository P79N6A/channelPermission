package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.CostPoolsUsedLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

@Mapper
public interface CostPoolsUsedLogsWriteDao {

    void insert(CostPoolsUsedLogs costPoolsUsedLogs);
}