package com.haier.shop.dao.shopread;

import com.haier.shop.model.CostPools;
import com.haier.shop.model.CostPoolsUsedLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CostPoolsUsedLogsReadDao {

    List<CostPools> findCostPoolsUsedLogsByPage(Map<String, Object> params);

    int getTotal();

    List<Map<String,Object>> getExportCostPoolsUsedLogsList(Map<String, Object> paramMap);

    CostPoolsUsedLogs findLogsByOidCid(@Param("oid") Integer id,@Param("opid") Integer id1);
}
