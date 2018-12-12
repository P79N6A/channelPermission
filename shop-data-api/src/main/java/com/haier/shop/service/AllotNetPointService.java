package com.haier.shop.service;


import com.haier.shop.model.AllotNetPoint;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/12 0012.
 */
public interface AllotNetPointService {


    List<AllotNetPoint> getByStatus(Integer status, Integer rowNum);

    int insert(AllotNetPoint allotNetPoint);

    int updateById(Integer id, int status, String message);
    List<Map<String, Object>> getNetPoints();
    int batchInsert(Map<String, Object> params);
}
