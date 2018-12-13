package com.haier.shop.dao.shopread;

import com.haier.shop.model.ProductSpecificationsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopProductSpecificationDao {
    List<ProductSpecificationsDTO> selectByProductId(Integer id);

    void insertBath(List<ProductSpecificationsDTO> productFeaturesDTOArrayList);

    void deleteById(Integer id);
}
