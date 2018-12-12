package com.haier.shop.dao.shopread;

import com.haier.shop.model.SgRealtimeStock;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SgRealtimeStockReadDao {
	SgRealtimeStock selectByParams(SgRealtimeStock record);

    String findStoreCodeByStoreId(Integer id);
}