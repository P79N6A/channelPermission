package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.SgRealtimeStock;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SgRealtimeStockWriteDao {

    int insert(SgRealtimeStock record);

    int updateByParams(SgRealtimeStock record);
}