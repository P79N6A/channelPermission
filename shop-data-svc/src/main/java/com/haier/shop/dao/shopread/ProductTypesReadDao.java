package com.haier.shop.dao.shopread;

import com.haier.shop.model.ProductTypesNew;
import com.haier.shop.model.Producttypes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductTypesReadDao {


	List<Producttypes> selectByProducttypesSku(int id);
	List<Producttypes> selectByProducttypes();
	ProductTypesNew getById(int typeId);
	ProductTypesNew getByIdNew(int typeId);
	List<ProductTypesNew> findsort();
}
