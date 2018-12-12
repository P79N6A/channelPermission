package com.haier.distribute.data.service;

import java.util.List;

import com.haier.distribute.data.model.ProductCates;


public interface ProductCatesService {
    List<ProductCates> selectByProducttypesId();

    List<ProductCates> selectByProducttypesSku(int id);

    ProductCates get(Integer id);

    /**
     * 获取所有品类
     *
     * @return
     */
    List<ProductCates> getAllProductCates();


    /**
     * 获取parentId的所有子类的ID
     *
     * @param parentId
     * @return
     */
    List<ProductCates> getAllChildren(Integer parentId);

}