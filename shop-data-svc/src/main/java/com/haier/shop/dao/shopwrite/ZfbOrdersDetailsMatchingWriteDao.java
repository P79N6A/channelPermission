package com.haier.shop.dao.shopwrite;

import org.apache.ibatis.annotations.Mapper;

import com.haier.shop.model.ZfbOrdersDetailsMatching;

@Mapper
public interface ZfbOrdersDetailsMatchingWriteDao {

	int updateByPrimaryKeySelective(ZfbOrdersDetailsMatching detailsmatching);
  
}