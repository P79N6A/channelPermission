package com.haier.distribute.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.haier.distribute.data.dao.distribute.BrandsDao;
import com.haier.distribute.data.model.Brands;
import com.haier.distribute.data.service.BrandsService;
import org.springframework.stereotype.Service;

@Service
public class BrandsServiceImpl implements BrandsService {
    @Autowired
    BrandsDao brandsDao;

    @Override
    public List<Brands> selectBrandsList() {
        return brandsDao.selectBrandsList();
    }

    @Override
    public List<Brands> selectBrandsIdList(int id) {
        return brandsDao.selectBrandsIdList(id);
    }

    @Override
    public List<Brands> getAllBrands() {
        return brandsDao.getAllBrands();
    }

    @Override
    public Brands get(Integer id) {
        return brandsDao.get(id);
    }
}