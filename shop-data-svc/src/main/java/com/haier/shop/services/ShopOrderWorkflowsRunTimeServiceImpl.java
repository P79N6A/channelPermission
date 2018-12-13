package com.haier.shop.services;

import com.haier.shop.dao.shopread.OrderWorkflowsRunTimeReadDao;
import com.haier.shop.service.ShopOrderWorkflowsRunTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class ShopOrderWorkflowsRunTimeServiceImpl implements ShopOrderWorkflowsRunTimeService {
    @Autowired
    private OrderWorkflowsRunTimeReadDao orderWorkflowsRunTimeReadDao;
    @Override
    public List<Map<String, Object>> getOntimeRateLes(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateLes(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateNetpoint(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateNetpoint(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateUser(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateUser(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateOwf(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateOwf(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateOwfSecond(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateOwfSecond(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateTransport(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateTransport(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateCod(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateCod(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRate(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRate(paramMap);
    }

    @Override
    public List<Map<String, Object>> getO2oStoreOntimeRateList(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getO2oStoreOntimeRateList(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateByOrderSn(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateByOrderSn(paramMap);
    }

    @Override
    public List<Map<String, String>> getRegions(Integer parentId) {
        return orderWorkflowsRunTimeReadDao.getRegions(parentId);
    }

    @Override
    public List<Map<String, String>> getStorages() {
        return orderWorkflowsRunTimeReadDao.getStorages();
    }

    @Override
    public Integer getOntimeRateDetailCount(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateDetailCount(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateDetail(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateDetail(paramMap);
    }

    @Override
    public List<Map<String, Object>> getShippingTimeByRegionId(Integer id) {
        return orderWorkflowsRunTimeReadDao.getShippingTimeByRegionId(id);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverseAudit(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateReverseAudit(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverseHp(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateReverseHp(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverseLes(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateReverseLes(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverseInvoice(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateReverseInvoice(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverseOrderclose(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateReverseOrderclose(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverseRefund(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateReverseRefund(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverseBlp(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateReverseBlp(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverse(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateReverse(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverseByOrderSn(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateReverseByOrderSn(paramMap);
    }

    @Override
    public Integer getOntimeRateReverseDetailCount(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateReverseDetailCount(paramMap);

    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverseDetail(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateReverseDetail(paramMap);
    }

    @Override
    public List<Map<String, Object>> getDisplayOutTimeList(Integer startDate, Integer endDate) {
        return orderWorkflowsRunTimeReadDao.getDisplayOutTimeList(startDate,endDate);
    }

    @Override
    public List<Map<String, Object>> getDisplayOutTimeReverseList(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getDisplayOutTimeReverseList(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverse22StoreHouse(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateReverse22StoreHouse(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverseReCheckQuality(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateReverseReCheckQuality(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverseTransmitBox(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateReverseTransmitBox(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverseTransmitStock(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateReverseTransmitStock(paramMap);
    }

    @Override
    public Map<String, Object> get1041StoreExistInfo(Integer orderRepairId) {
        return orderWorkflowsRunTimeReadDao.get1041StoreExistInfo(orderRepairId);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverseByOrderSn22Store(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getOntimeRateReverseByOrderSn22Store(paramMap);
    }

    @Override
    public List<Map<String, Object>> getGaiyueInfo(Map<String, Object> paramMap) {
        return orderWorkflowsRunTimeReadDao.getGaiyueInfo(paramMap);
    }

    @Override
    public List<Map<String, Object>> getDistances() {
        return orderWorkflowsRunTimeReadDao.getDistances();
    }

    @Override
    public List<Map<String, Object>> getProductCate() {
        return orderWorkflowsRunTimeReadDao.getProductCate();
    }
}
