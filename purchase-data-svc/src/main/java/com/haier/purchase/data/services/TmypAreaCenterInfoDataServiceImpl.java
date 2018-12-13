package com.haier.purchase.data.services;

import com.haier.purchase.data.dao.purchase.TmypAreaCenterInfoDao;
import com.haier.purchase.data.model.vehcile.TmypAreaCenterInfo;
import com.haier.purchase.data.service.TmypAreaCenterInfoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TmypAreaCenterInfoDataServiceImpl implements TmypAreaCenterInfoDataService{

    @Autowired
    private TmypAreaCenterInfoDao tmypAreaCenterInfoDao;
    @Override
    public List<TmypAreaCenterInfo> getTmypAreaCenterInfo(Map<String, Object> params) {
        return tmypAreaCenterInfoDao.getTmypAreaCenterInfo(params);
    }

    @Override
    public Integer getTmypAreaCenterInfoCount(Map<String, Object> params) {
        return tmypAreaCenterInfoDao.getTmypAreaCenterInfoCount(params);
    }

    @Override
    public TmypAreaCenterInfo getOneByDeliveryToCode(String deliveryToCode) {
        return tmypAreaCenterInfoDao.getOneByDeliveryToCode(deliveryToCode);
    }

    @Override
    public void insertSelective(TmypAreaCenterInfo areaCenterInfoDTO) {
        tmypAreaCenterInfoDao.insertSelective(areaCenterInfoDTO);
    }

    @Override
    public void updateSelectiveByDeliveryToCode(TmypAreaCenterInfo areaCenterInfoDTO) {
        tmypAreaCenterInfoDao.updateSelectiveByDeliveryToCode(areaCenterInfoDTO);

    }

    @Override
    public void openStatusAreaCenterInfo(Map<String, Object> params) {
        tmypAreaCenterInfoDao.openStatusAreaCenterInfo(params);

    }

    @Override
    public void closeStatusAreaCenterInfo(Map<String, Object> params) {
        tmypAreaCenterInfoDao.closeStatusAreaCenterInfo(params);
    }

    @Override
    public List<TmypAreaCenterInfo> getAreaCenterInfoExport(Map<String, Object> params) {
        return tmypAreaCenterInfoDao.getAreaCenterInfoExport(params);
    }
}
