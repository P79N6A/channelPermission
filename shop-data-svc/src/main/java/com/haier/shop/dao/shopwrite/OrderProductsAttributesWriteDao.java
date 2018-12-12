package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderProductsAttributes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderProductsAttributesWriteDao {

    int insert(OrderProductsAttributes orderProductsAttributes);

    int update(OrderProductsAttributes orderProductsAttributes);

}