package com.haier.svc.api.controller.stock.mode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.service.ShopItemAttributeService;

@Service
public class ItemAttributeModel {
	@Autowired
    private ShopItemAttributeService shopItemAttributeService;

    /**
     * 获得所有品类
     * @return
     */
    public List<String> getProductTypes() {
        return shopItemAttributeService.getProductTypesTo2();
    }

    public void setshopItemAttributeService(ShopItemAttributeService shopItemAttributeService) {
        this.shopItemAttributeService = shopItemAttributeService;
    }
}
