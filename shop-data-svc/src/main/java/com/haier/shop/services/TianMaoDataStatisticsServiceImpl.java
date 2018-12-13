package com.haier.shop.services;

import com.haier.shop.dao.shopread.TianMaoDataStatisticsReadDao;
import com.haier.shop.service.TianMaoDataStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author:JinXueqian
 * @Date: 2018/8/14 16:37
 */
@Service
public class TianMaoDataStatisticsServiceImpl implements TianMaoDataStatisticsService {

    @Autowired
    TianMaoDataStatisticsReadDao tianMaoDataStatisticsReadDao;
    @Override
    public List<Map<String,Object>> getTianMaoDataStatistics(Map<String, Object> params) {
        return tianMaoDataStatisticsReadDao.getTianMaoDataStatistics(params);
    }
}
