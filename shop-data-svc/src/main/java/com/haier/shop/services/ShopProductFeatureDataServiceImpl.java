package com.haier.shop.services;

import com.haier.shop.dao.shopread.ShopProductFeatureDao;
import com.haier.shop.model.ProductFeaturesDTO;
import com.haier.shop.service.ShopProductFeatureDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopProductFeatureDataServiceImpl implements ShopProductFeatureDataService {

    @Autowired
    private ShopProductFeatureDao shopProductFeatureDao;
    @Override
    public List<ProductFeaturesDTO> selectByProductId(Integer id) {
        return shopProductFeatureDao.selectByProductId(id);
    }

    @Override
    public void insertBath(List<ProductFeaturesDTO> productFeaturesDTOArrayList) {
        shopProductFeatureDao.insertBath(productFeaturesDTOArrayList);
    }

    @Override
    public void deleteById(Integer id) {
        shopProductFeatureDao.deleteById(id);

    }
}
