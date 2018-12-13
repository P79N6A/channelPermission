package com.haier.order.services;

import com.haier.order.service.ShopProductSpecificationService;
import com.haier.shop.model.ProductSpecificationsDTO;
import com.haier.shop.service.ShopProductSpecificationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopProductSpecificationServiceImpl implements ShopProductSpecificationService{

    @Autowired
    private ShopProductSpecificationDataService shopProductSpecificationDataService;
    @Override
    public List<ProductSpecificationsDTO> selectByProductId(Integer id) {
        return shopProductSpecificationDataService.selectByProductId(id);
    }
}
