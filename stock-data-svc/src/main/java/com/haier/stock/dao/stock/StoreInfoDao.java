package com.haier.stock.dao.stock;

import com.haier.stock.model.StoreInfo;
import org.apache.ibatis.annotations.Param;


public interface StoreInfoDao {

    StoreInfo getByOwerId(@Param("owerId") Integer owerId);

}