package com.haier.stock.dao.stock;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.common.PagerInfo;
import com.haier.stock.model.InvReservedConfig;

public interface StockReservedConfigDao {
	 /**
     * 根据条件查询配置,分页
     * @param config
     * @return
     */
    List<InvReservedConfig> getReservedConfig(@Param("config") InvReservedConfig config,
                                              @Param("pager") PagerInfo pager);

    int getRowCnt();
}
