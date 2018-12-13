package com.haier.shop.service;

import com.haier.shop.model.ProductFeaturesDTO;

import java.util.List;

public interface ShopProductFeatureDataService {
    List<ProductFeaturesDTO> selectByProductId(Integer id);

    void insertBath(List<ProductFeaturesDTO> productFeaturesDTOArrayList);

    void deleteById(Integer id);
}
