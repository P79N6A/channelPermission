package com.haier.shop.dao.shopread;

import com.haier.shop.model.Order2ths;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Order2thsReadDao {
    /**
     * 根据id获取差异订单对象
     * @param id
     * @return
     */
    Order2ths get(Integer id);
}
