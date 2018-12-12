package com.haier.shop.services;

import java.util.List;

import com.haier.shop.dao.shopread.BrandsReadDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.model.Brands;
import com.haier.shop.service.BrandsService;

@Service
public class BrandsServiceImpl implements BrandsService {
    @Autowired
    BrandsReadDao brandsReadDao;

    @Override
    public List<Brands> selectBrandsList() {
        return brandsReadDao.selectBrandsList();
    }

    @Override
    public List<Brands> selectBrandsIdList(int id) {
        return brandsReadDao.selectBrandsIdList(id);
    }

    @Override
    public List<Brands> getAllBrands() {
        return brandsReadDao.getAllBrands();
    }

    @Override
    public Brands get(Integer id) {
        return brandsReadDao.get(id);
    }
}