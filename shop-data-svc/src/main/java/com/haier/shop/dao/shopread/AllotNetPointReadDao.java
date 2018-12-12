package com.haier.shop.dao.shopread;


import com.haier.shop.model.AllotNetPoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AllotNetPointReadDao {


    List<AllotNetPoint> getByStatus(@Param("status") Integer status, @Param("rowNum") Integer rowNum);
    public List<Map<String, Object>> getNetPoints();
}
