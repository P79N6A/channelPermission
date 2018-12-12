package com.haier.distribute.data.service;

import com.haier.distribute.data.model.Brands;

import java.util.List;


public interface BrandsService {
    List<Brands> selectBrandsList();

    List<Brands> selectBrandsIdList(int id);

    List<Brands> getAllBrands();

    Brands get(Integer id);
}