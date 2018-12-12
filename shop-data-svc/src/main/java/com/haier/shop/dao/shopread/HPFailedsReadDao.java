package com.haier.shop.dao.shopread;

import com.haier.shop.model.HPFaileds;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HPFailedsReadDao {

    public HPFaileds getByOrderProductId(@Param("orderProductId") Integer orderProductId);
}
