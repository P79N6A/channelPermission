package com.haier.shop.services;

import com.haier.shop.dao.shopread.ShopProductOuterPackingBoxeDao;
import com.haier.shop.model.ProductOuterPackingBoxesDTO;
import com.haier.shop.service.ShopProductOuterPackingBoxeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopProductOuterPackingBoxeDataServiceImpl implements ShopProductOuterPackingBoxeDataService {
    @Autowired
    private ShopProductOuterPackingBoxeDao shopProductOuterPackingBoxeDao;
    @Override
    public List<ProductOuterPackingBoxesDTO> selectByProductId(Integer id) {
        return shopProductOuterPackingBoxeDao.selectByProductId(id);
    }

    @Override
    public void insertBath(List<ProductOuterPackingBoxesDTO> productFeaturesDTOArrayList) {
         shopProductOuterPackingBoxeDao.insertBath(productFeaturesDTOArrayList);
    }

    @Override
    public void deleteById(Integer id) {
        shopProductOuterPackingBoxeDao.deleteById(id);

    }
}
