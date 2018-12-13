package com.haier.order.services;

import com.haier.order.service.ShopProductPictureService;
import com.haier.shop.model.ProductPicturesDTO;
import com.haier.shop.service.ShopProductPictureDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopProductPictureServiceImpl implements ShopProductPictureService{

    @Autowired
    private ShopProductPictureDataService shopProductPictureDataService;
    @Override
    public List<ProductPicturesDTO> selectByProductId(Integer id) {
        return shopProductPictureDataService.selectByProductId(id);
    }
}
