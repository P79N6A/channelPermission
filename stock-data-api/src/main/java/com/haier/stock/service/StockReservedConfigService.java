package com.haier.stock.service;

import java.util.List;


import com.haier.common.PagerInfo;
import com.haier.stock.model.InvReservedConfig;

public interface StockReservedConfigService {
	 /**
     * 根据条件查询配置,分页
     * @param config
     * @return
     */
    List<InvReservedConfig> getReservedConfig( InvReservedConfig config,PagerInfo pager);

    int getRowCnt();
}
