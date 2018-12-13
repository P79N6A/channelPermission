package com.haier.shop.service;

import com.haier.shop.model.BigStoragesRela;
import com.haier.shop.model.OrderWorkflowRegion;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/28.
 */
public interface OrderWorkFlowModelDataService {

    public Map<Integer, OrderWorkflowRegion> getRegionMap();
    public Map<String, String> getStorageName();
    public Map<String, List<BigStoragesRela>> getCodeBigStoragesRelaMap();
    public List<Map<String, Object>> getGaiyueInfo(Long orderProductId);
    public List<Map<String, Object>> getShippingTimeByRegionId(Long region);
    public List<OrderWorkflowRegion> getOwfRegion();
    List<Map<String, String>> getStorages();
    List<BigStoragesRela> getBigStoragesRelaList();
    List<Map<String, String>> getTradePersonCfg();
    double getDistances(@Param("regionId") Long regionId);
    public Integer formatShippingTime(Integer shippingTime, Long region, Integer type);

}
