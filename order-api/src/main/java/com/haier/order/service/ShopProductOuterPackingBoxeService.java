package com.haier.order.service;

import com.haier.shop.model.ProductOuterPackingBoxesDTO;

import java.util.List;

public interface ShopProductOuterPackingBoxeService {
    List<ProductOuterPackingBoxesDTO> selectByProductId(Integer id);
}
