package com.haier.svc.services;

import com.haier.purchase.data.model.vehcile.TmypAreaCenterInfo;
import com.haier.purchase.data.service.TmypAreaCenterInfoDataService;
import com.haier.svc.service.TmypAreaCenterInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TmypAreaCenterInfoServiceImpl implements TmypAreaCenterInfoService{

    @Autowired
    private TmypAreaCenterInfoDataService tmypAreaCenterInfoDataService;

    @Override
    public List<TmypAreaCenterInfo> getTmypAreaCenterInfo(Map<String, Object> params) {
        return tmypAreaCenterInfoDataService.getTmypAreaCenterInfo(params);
    }

    @Override
    public Integer getTmypAreaCenterInfoCount(Map<String, Object> params) {
        return tmypAreaCenterInfoDataService.getTmypAreaCenterInfoCount(params);
    }

    @Override
    public TmypAreaCenterInfo getOneByDeliveryToCode(String deliveryToCode) {
        return tmypAreaCenterInfoDataService.getOneByDeliveryToCode(deliveryToCode);
    }

    @Override
    public void insertSelective(TmypAreaCenterInfo areaCenterInfoDTO) {
        tmypAreaCenterInfoDataService.insertSelective(areaCenterInfoDTO);
    }

    @Override
    public void updateSelectiveByDeliveryToCode(TmypAreaCenterInfo areaCenterInfoDTO) {
        tmypAreaCenterInfoDataService.updateSelectiveByDeliveryToCode(areaCenterInfoDTO);
    }

    @Override
    public void openStatusAreaCenterInfo(Map<String, Object> params) {
        tmypAreaCenterInfoDataService.openStatusAreaCenterInfo(params);

    }

    @Override
    public void closeStatusAreaCenterInfo(Map<String, Object> params) {
        tmypAreaCenterInfoDataService.closeStatusAreaCenterInfo(params);

    }

    @Override
    public List<TmypAreaCenterInfo> getAreaCenterInfoExport(Map<String, Object> params) {
        return tmypAreaCenterInfoDataService.getAreaCenterInfoExport(params);
    }
}
