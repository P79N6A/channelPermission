package com.haier.shop.service;

import com.haier.shop.model.ProductGatefoldsDTO;

import java.util.List;

public interface ShopProductGatefoldDataService {
    List<ProductGatefoldsDTO> selectByProductId(Integer id);

    void insertBath(List<ProductGatefoldsDTO> productFeaturesDTOArrayList);

    void deleteById(Integer id);
}
