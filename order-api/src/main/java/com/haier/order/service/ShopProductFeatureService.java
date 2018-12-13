package com.haier.order.service;

import com.haier.shop.model.ProductFeaturesDTO;

import java.util.List;

public interface ShopProductFeatureService {
    List<ProductFeaturesDTO> selectByProductId(Integer id);
}
