package com.haier.order.service;

import com.haier.shop.model.ProductSpecificationsDTO;

import java.util.List;

public interface ShopProductSpecificationService {
    List<ProductSpecificationsDTO> selectByProductId(Integer id);
}
