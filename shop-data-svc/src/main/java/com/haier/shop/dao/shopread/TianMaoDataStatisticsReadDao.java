package com.haier.shop.dao.shopread;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author:JinXueqian
 * @Date: 2018/8/14 16:39
 */
@Mapper
public interface TianMaoDataStatisticsReadDao {

    List<Map<String,Object>> getTianMaoDataStatistics(Map<String,Object> params);
}
