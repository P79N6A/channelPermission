package com.haier.shop.services;

import com.haier.shop.dao.shopread.ReverseReportReadDao;
import com.haier.shop.service.ReverseReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class ReverseReportServiceImpl implements ReverseReportService {
    @Autowired
    private ReverseReportReadDao reverseReportReadDao;

    @Override
    public List<Map<String, Object>> getOntimeRateReverse(Map<String, Object> paramMap) {
        return reverseReportReadDao.getOntimeRateReverse(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverseByOrderSn(Map<String, Object> paramMap) {
        return reverseReportReadDao.getOntimeRateReverseByOrderSn(paramMap);
    }

    @Override
    public Integer getOntimeRateReverseDetailCount(Map<String, Object> paramMap) {
        return reverseReportReadDao.getOntimeRateReverseDetailCount(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOntimeRateReverseDetail(Map<String, Object> paramMap) {
        return reverseReportReadDao.getOntimeRateReverseDetail(paramMap);
    }
}
