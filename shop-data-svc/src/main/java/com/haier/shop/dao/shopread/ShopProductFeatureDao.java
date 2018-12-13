package com.haier.shop.dao.shopread;

import com.haier.shop.model.ProductFeaturesDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopProductFeatureDao {
    List<ProductFeaturesDTO> selectByProductId(Integer id);

    void insertBath(List<ProductFeaturesDTO> productFeaturesDTOArrayList);

    void deleteById(Integer id);
}
