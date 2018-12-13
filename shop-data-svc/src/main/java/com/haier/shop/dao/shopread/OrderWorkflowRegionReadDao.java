package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderWorkflowRegion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/*
* 作者:张波
* 2017/12/19
* */
@Mapper
public interface OrderWorkflowRegionReadDao {
    OrderWorkflowRegion get(@Param("regionId") Integer regionId);

    List<Map<String, String>> getFdArea(String area);

    List<Map<String, String>> getSCodeTradeWl();

    List<Map<String, String>> getSCodeByLes(@Param("les") String les);

    List<Map<String, String>> getTrade(@Param("area") String area);

    List<Map<String, String>> getSCode(String trade);

    List<OrderWorkflowRegion> getOwfRegion();

    List<Map<String, String>> getCommissioner(@Param("area") String area);

    List<Map<String, String>> getCommissionerTrade(@Param("areaCommissioner") String areaCommissioner);

    List<Map<String, String>> getSmallChannelPeopleTrade(@Param("areaCommissioner") String areaCommissioner);

    List<Map<String, Object>> getCateInfo();
    List<Map<String, Object>> getStoreIdAndName();

    String getLoginPersonChannel(@Param("userName") String userName);

}
