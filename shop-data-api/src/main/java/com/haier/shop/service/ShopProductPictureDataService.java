package com.haier.shop.service;

import com.haier.shop.model.ProductPicturesDTO;

import java.util.List;

public interface ShopProductPictureDataService {
    List<ProductPicturesDTO> selectByProductId(Integer id);

    void insertBath(List<ProductPicturesDTO> productPicturesDTOList);

    void deleteById(Integer id);
}
