package com.haier.shop.dao.shopread;


import com.haier.shop.model.NetPoints;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface NetPointsReadDao {

    NetPoints selectByPrimaryKey(Integer id);

    /**
     * 由网点8码查询网点信息
     * @param netPointN8
     * @return
     */
    NetPoints getByNetPointN8(@Param("netPointN8") String netPointN8);

    NetPoints get(Integer id);
    
    NetPoints getByNetPointByCode(@Param("netPointCode") String netPointCode);

    /**
     * 查询总条数
     * @return
     */
    int getRowCnts();

    /**
     * 查询网点列表
     * @param params
     * @return
     */
    List<NetPoints> queryNetPointList(Map<String,Object> params);
}