package com.haier.shop.service;


import java.util.List;
import java.util.Map;

import com.haier.shop.model.OrderWorkflowRegion;

/*
* 作者:张波
* 2017/12/19
* */
public interface OrderWorkflowRegionService {
    OrderWorkflowRegion get(  Integer regionId);

    List<Map<String, String>> getFdArea(String area);

    List<Map<String, String>> getSCodeTradeWl();

    List<Map<String, String>> getSCodeByLes(String les);

    List<Map<String, String>> getTrade( String area);

    List<Map<String, String>> getSCode(String trade);

    List<OrderWorkflowRegion> getOwfRegion();

    List<Map<String, String>> getCommissioner(String area);

    List<Map<String, String>> getCommissionerTrade(String areaCommissioner);

    List<Map<String, String>> getSmallChannelPeopleTrade(String areaCommissioner);

    List<Map<String, Object>> getCateInfo();

    List<Map<String, Object>> getStoreIdAndName();

    String getLoginPersonChannel(String userName);
}
