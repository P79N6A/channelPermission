package com.haier.shop.services;

import com.haier.shop.dao.shopread.ShopProductGatefoldDao;
import com.haier.shop.model.ProductGatefoldsDTO;
import com.haier.shop.service.ShopProductGatefoldDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopProductGatefoldDataServiceImpl implements ShopProductGatefoldDataService {
    @Autowired
    private ShopProductGatefoldDao shopProductGatefoldDao;
    @Override
    public List<ProductGatefoldsDTO> selectByProductId(Integer id) {
        return shopProductGatefoldDao.selectByProductId(id);
    }

    @Override
    public void insertBath(List<ProductGatefoldsDTO> productFeaturesDTOArrayList) {
        shopProductGatefoldDao.insertBath(productFeaturesDTOArrayList);
    }

    @Override
    public void deleteById(Integer id) {
        shopProductGatefoldDao.deleteById(id);
    }
}
