package com.haier.shop.dao.shopwrite;


import com.haier.shop.model.AllotNetPoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/12 0012.
 */
@Mapper
public interface AllotNetPointWriteDao {

    int insert(AllotNetPoint allotNetPoint);

    int updateById(@Param("id") Integer id, @Param("status") int status,
                   @Param("message") String message);
    int batchInsert(Map<String, Object> params);
}
