package com.haier.order.services;

import com.haier.order.service.ShopProductGatefoldService;
import com.haier.shop.model.ProductGatefoldsDTO;
import com.haier.shop.service.ShopProductGatefoldDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopProductGatefoldServiceImpl implements ShopProductGatefoldService{
    @Autowired
    private ShopProductGatefoldDataService shopProductGatefoldDataService;
    @Override
    public List<ProductGatefoldsDTO> selectByProductId(Integer id) {
        return shopProductGatefoldDataService.selectByProductId(id);
    }
}
