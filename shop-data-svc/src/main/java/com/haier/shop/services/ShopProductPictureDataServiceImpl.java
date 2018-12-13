package com.haier.shop.services;

import com.haier.shop.dao.shopread.ShopProductPictureDao;
import com.haier.shop.model.ProductPicturesDTO;
import com.haier.shop.service.ShopProductPictureDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopProductPictureDataServiceImpl implements ShopProductPictureDataService {
    @Autowired
    private ShopProductPictureDao shopProductPictureDao;
    @Override
    public List<ProductPicturesDTO> selectByProductId(Integer id) {
        return shopProductPictureDao.selectByProductId(id);
    }

    @Override
    public void insertBath(List<ProductPicturesDTO> productPicturesDTOList) {
        shopProductPictureDao.insertBath(productPicturesDTOList);
    }

    @Override
    public void deleteById(Integer id) {
        shopProductPictureDao.deleteById(id);

    }
}
