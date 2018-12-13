package com.haier.order.services;

import com.haier.order.service.ShopProductOuterPackingBoxeService;
import com.haier.shop.model.ProductOuterPackingBoxesDTO;
import com.haier.shop.service.ShopProductOuterPackingBoxeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopProductOuterPackingBoxeServiceImpl implements ShopProductOuterPackingBoxeService {
    @Autowired
    private ShopProductOuterPackingBoxeDataService shopProductOuterPackingBoxeDataService;
    @Override
    public List<ProductOuterPackingBoxesDTO> selectByProductId(Integer id) {
        return shopProductOuterPackingBoxeDataService.selectByProductId(id);
    }
}
