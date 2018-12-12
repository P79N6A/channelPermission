package com.haier.stock.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.stock.model.InvReservedConfig;
import com.haier.stock.model.QueryTotalData;

public interface StockReservedService {
	  /**
     * 插入库存预留配置信息
     * @param config
     * @return
     */
    ServiceResult<Boolean> insertInvReservedConfig(InvReservedConfig config);
    
    /**
     * 根据条件查询预留渠道配置信息
     * @param config
     * @return
     */
    ServiceResult<List<InvReservedConfig>> queryInvReservedConfigs(InvReservedConfig config);
    /**
     * 根据条件查询预留渠道配置信息
     * @param
     * @return
     */
    public List<Map<String,String>> queryTotalPage(Map<String,Object> map);
    public JSONObject queryTotalData(PagerInfo pagerInfo,Map<String, Object> map);
    
    /**
     * 根据ID更新渠道预留配置
     * @param config
     * @return
     */
    ServiceResult<Boolean> updateReservedConfigById(InvReservedConfig config);
}
