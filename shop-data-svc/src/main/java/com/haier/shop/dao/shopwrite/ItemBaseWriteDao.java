package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.ItemBase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItemBaseWriteDao {


	Integer update(ItemBase base);
	
	Integer insert(ItemBase base);
	
	Integer updateNotNull(ItemBase base);
	
}
