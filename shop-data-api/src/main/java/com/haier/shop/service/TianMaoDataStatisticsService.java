package com.haier.shop.service;

import java.util.List;
import java.util.Map;

/**
 * @Author:JinXueqian
 * @Date: 2018/8/14 16:37
 */
public interface TianMaoDataStatisticsService {
    List<Map<String,Object>> getTianMaoDataStatistics(Map<String,Object> params);
}
