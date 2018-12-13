package com.haier.shop.dao.shopread;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.haier.shop.dto.ZfbOrderMatchingDto;
import com.haier.shop.model.QueryZFBOrderParameter;
import com.haier.shop.model.ZfbOrdersDetailsMatching;

@Mapper
public interface ZfbOrdersDetailsMatchingReadDao {
   
    ZfbOrdersDetailsMatching selectByPrimaryKey(Long id);


	List<ZfbOrderMatchingDto> getFindQueryOrderList(QueryZFBOrderParameter queryOrder);

	Integer getFindQueryOrderListCount(QueryZFBOrderParameter queryOrder);


	ZfbOrdersDetailsMatching queryZfbOrderDetailNegative(Integer orderId);


	ZfbOrdersDetailsMatching queryZfbOrderDetailPositive(Integer orderId);


}