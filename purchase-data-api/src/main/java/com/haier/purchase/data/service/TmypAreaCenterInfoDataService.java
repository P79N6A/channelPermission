package com.haier.purchase.data.service;

import com.haier.purchase.data.model.vehcile.TmypAreaCenterInfo;

import java.util.List;
import java.util.Map;

public interface TmypAreaCenterInfoDataService {
    List<TmypAreaCenterInfo> getTmypAreaCenterInfo(Map<String, Object> params);

    Integer getTmypAreaCenterInfoCount(Map<String, Object> params);

    TmypAreaCenterInfo getOneByDeliveryToCode(String deliveryToCode);

    void insertSelective(TmypAreaCenterInfo areaCenterInfoDTO);

    void updateSelectiveByDeliveryToCode(TmypAreaCenterInfo areaCenterInfoDTO);

    void openStatusAreaCenterInfo(Map<String, Object> params);

    void closeStatusAreaCenterInfo(Map<String, Object> params);

    List<TmypAreaCenterInfo> getAreaCenterInfoExport(Map<String, Object> params);
}
