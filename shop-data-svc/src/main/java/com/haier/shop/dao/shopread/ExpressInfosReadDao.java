package com.haier.shop.dao.shopread;

import com.haier.shop.model.ExpressInfos;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExpressInfosReadDao {
    ExpressInfos findBycOrderSn(String cOrderSn);
}
