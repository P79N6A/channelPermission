package com.haier.purchase.data.service.vechile;

import com.haier.purchase.data.model.vehcile.AreaCenterInfoDTO;

import java.util.List;
import java.util.Map;

public interface PurchaseAreaCenterInfoService extends BasService<AreaCenterInfoDTO> {

    List<AreaCenterInfoDTO> getAreaCenterInfo(Map<String, Object> params);

    Integer getAreaCenterInfoCount(Map<String, Object> params);

    void updateSelectiveByDeliveryToCode(AreaCenterInfoDTO areaCenterInfoDTO);

    void openStatusAreaCenterInfo(Map<String, Object> params);

    void closeStatusAreaCenterInfo(Map<String, Object> params);

    List<AreaCenterInfoDTO> getAreaCenterInfoExport(Map<String, Object> params);

    //List<AreaCenterInfoDTO> listByCondition(@Param("entity") AreaCenterInfoDTO entity);

}