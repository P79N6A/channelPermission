package com.haier.shop.service;

import com.haier.shop.model.NetPoints;

import java.util.Map;

public interface NetPointsService {
    int deleteByPrimaryKey(Integer id);

    int insert(NetPoints record);

    int insertSelective(NetPoints record);

    NetPoints selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NetPoints record);

    int updateByPrimaryKey(NetPoints record);

    /**
     * 由网点8码查询网点信息
     * @param netPointN8
     * @return
     */
    NetPoints getByNetPointN8(String netPointN8);

    NetPoints get(Integer id);
    
    NetPoints getByNetPointByCode(String netPointCode);

    /**
     * 网点列表(分页查询)
     * @param params
     * @return
     */
    Map<String, Object> getNetPointsList(Map<String,Object> params);
}