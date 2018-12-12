package com.haier.distribute.data.dao.distribute;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.distribute.data.model.ProductTypesNew;
import com.haier.distribute.data.model.Producttypes;


public interface ProductTypesDao extends BaseDao<Producttypes>{

	List<Producttypes> selectByProducttypesSku(int id);
	List<Producttypes> selectByProducttypes();
	ProductTypesNew getById(int typeId);
	ProductTypesNew getByIdNew(int typeId);
}
