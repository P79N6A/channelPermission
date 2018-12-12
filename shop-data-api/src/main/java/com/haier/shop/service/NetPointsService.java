package com.haier.shop.service;

import com.haier.shop.model.NetPoints;

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
}