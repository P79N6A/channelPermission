package com.haier.shop.dao.shopread;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.ProductCates;

@Mapper
public interface ProductCatesReadDao extends BaseReadDao<ProductCates> {
	 List<ProductCates> selectByProducttypesId();
	List<ProductCates> selectCateName();

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
	    List<ProductCates> findIndustry(String typeName);
	    List<ProductCates> findindustry();
		List<ProductCates> findSortCount();
}