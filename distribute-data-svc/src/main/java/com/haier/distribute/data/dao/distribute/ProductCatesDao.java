package com.haier.distribute.data.dao.distribute;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.distribute.data.model.ProductCates;

public interface ProductCatesDao extends BaseDao<ProductCates>{
	 List<ProductCates> selectByProducttypesId();
	    List<ProductCates> selectByProducttypesSku(int id);

	    ProductCates get(Integer id);
	    /**
	     * 获取所有品类
	     * @return
	     */
	    List<ProductCates> getAllProductCates();
	    

	    /**
	     * 获取parentId的所有子类的ID
	     * @param parentId
	     * @return
	     */
	    List<ProductCates> getAllChildren(@Param("parentId") Integer parentId);

}