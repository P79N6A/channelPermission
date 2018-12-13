package com.haier.order.service;

import com.haier.shop.model.ProductGatefoldsDTO;

import java.util.List;

public interface ShopProductGatefoldService {
    List<ProductGatefoldsDTO> selectByProductId(Integer id);
}
