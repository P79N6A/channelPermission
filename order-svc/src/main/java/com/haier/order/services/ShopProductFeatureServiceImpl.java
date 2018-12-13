package com.haier.order.services;

import com.haier.order.service.ShopProductFeatureService;
import com.haier.shop.model.ProductFeaturesDTO;
import com.haier.shop.service.ShopProductFeatureDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopProductFeatureServiceImpl implements ShopProductFeatureService{

    @Autowired
    private ShopProductFeatureDataService shopProductFeatureDataService;
    @Override
    public List<ProductFeaturesDTO> selectByProductId(Integer id) {
        return shopProductFeatureDataService.selectByProductId(id);
    }
}
