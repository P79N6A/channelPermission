package com.haier.order.service;


import com.haier.shop.model.ProductPicturesDTO;

import java.util.List;

public interface ShopProductPictureService {
    List<ProductPicturesDTO> selectByProductId(Integer id);
}
