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

    List<Map<String, String>> getTrade(@Param("area") String area);

    List<Map<String, String>> getSCode(String trade);

    List<OrderWorkflowRegion> getOwfRegion();
}
