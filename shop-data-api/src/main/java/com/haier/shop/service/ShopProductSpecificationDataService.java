package com.haier.shop.service;

import com.haier.shop.model.ProductSpecificationsDTO;

import java.util.List;

public interface ShopProductSpecificationDataService {
    List<ProductSpecificationsDTO> selectByProductId(Integer id);

    void insertBath(List<ProductSpecificationsDTO> productFeaturesDTOArrayList);

    void deleteById(Integer id);
}
