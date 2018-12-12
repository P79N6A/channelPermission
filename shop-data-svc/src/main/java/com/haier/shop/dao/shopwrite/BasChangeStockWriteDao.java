package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.BasChangeStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BasChangeStockWriteDao {

    Integer insert(@Param("changeList") List<BasChangeStock> insertList);

    Integer update(BasChangeStock changeStock);

    Integer insert2(@Param("changeList") List<BasChangeStock> insertList);

    Integer update2(BasChangeStock changeStock);

    Integer deleteB2CInfoBySku(@Param("sku") String sku);

}
