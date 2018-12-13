package com.haier.shop.dao.shopread;

import com.haier.shop.model.ProductOuterPackingBoxesDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopProductOuterPackingBoxeDao {
    List<ProductOuterPackingBoxesDTO> selectByProductId(Integer id);

    void insertBath(List<ProductOuterPackingBoxesDTO> productFeaturesDTOArrayList);

    void deleteById(Integer id);
}
