package com.haier.shop.services;

import com.haier.shop.dao.shopread.ShopProductSpecificationDao;
import com.haier.shop.model.ProductSpecificationsDTO;
import com.haier.shop.service.ShopProductSpecificationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopProductSpecificationDataServiceImpl implements ShopProductSpecificationDataService {
    @Autowired
    private ShopProductSpecificationDao shopProductSpecificationDao;
    @Override
    public List<ProductSpecificationsDTO> selectByProductId(Integer id) {
        return shopProductSpecificationDao.selectByProductId(id);
    }

    @Override
    public void insertBath(List<ProductSpecificationsDTO> productFeaturesDTOArrayList) {
        shopProductSpecificationDao.insertBath(productFeaturesDTOArrayList);
    }

    @Override
    public void deleteById(Integer id) {
        shopProductSpecificationDao.deleteById(id);
    }
}
