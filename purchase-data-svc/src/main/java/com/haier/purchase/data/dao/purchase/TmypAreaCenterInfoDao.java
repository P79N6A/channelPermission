package com.haier.purchase.data.dao.purchase;

import com.haier.purchase.data.model.vehcile.TmypAreaCenterInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TmypAreaCenterInfoDao {
    List<TmypAreaCenterInfo> getTmypAreaCenterInfo(Map<String, Object> params);

    Integer getTmypAreaCenterInfoCount(Map<String, Object> params);

    TmypAreaCenterInfo getOneByDeliveryToCode(String deliveryToCode);

    void insertSelective(TmypAreaCenterInfo areaCenterInfoDTO);

    void updateSelectiveByDeliveryToCode(@Param("entity")TmypAreaCenterInfo areaCenterInfoDTO);

    void openStatusAreaCenterInfo(Map<String, Object> params);

    void closeStatusAreaCenterInfo(Map<String, Object> params);

    List<TmypAreaCenterInfo> getAreaCenterInfoExport(Map<String, Object> params);
}
