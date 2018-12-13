package com.haier.shop.service;

import com.haier.shop.model.ProductOuterPackingBoxesDTO;

import java.util.List;

public interface ShopProductOuterPackingBoxeDataService {
    List<ProductOuterPackingBoxesDTO> selectByProductId(Integer id);

    void insertBath(List<ProductOuterPackingBoxesDTO> productFeaturesDTOArrayList);

    void deleteById(Integer id);
}
