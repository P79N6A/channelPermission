package com.haier.purchase.data.dao.vehcile;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.haier.purchase.data.model.vehcile.AreaCenterInfoDTO;

public interface AreaCenterInfoDao {

	int insertSelective(AreaCenterInfoDTO entity);

	int updateSelectiveById(@Param("entity")AreaCenterInfoDTO entity);

    AreaCenterInfoDTO getOneById(long id);

    AreaCenterInfoDTO getOneByCondition(@Param("entity")AreaCenterInfoDTO entity);

    List<AreaCenterInfoDTO> getListByCondition(@Param("entity")AreaCenterInfoDTO entity);

    List<AreaCenterInfoDTO> getPageByCondition(@Param("entity")AreaCenterInfoDTO entity, @Param("start") int start, @Param("rows") int rows);

    long getPagerCount(@Param("entity")AreaCenterInfoDTO entity);

    AreaCenterInfoDTO getOneByDeliveryToCode(@Param("deliveryToCode") String deliveryToCode);

    List<AreaCenterInfoDTO> getAreaCenterInfo(Map<String, Object> params);

    Integer getAreaCenterInfoCount(Map<String, Object> params);

    void updateSelectiveByDeliveryToCode(@Param("entity")AreaCenterInfoDTO entity);

    void openStatusAreaCenterInfo(Map<String, Object> params);

    void closeStatusAreaCenterInfo(Map<String, Object> params);

    List<AreaCenterInfoDTO> getAreaCenterInfoExport(Map<String, Object> params);
}