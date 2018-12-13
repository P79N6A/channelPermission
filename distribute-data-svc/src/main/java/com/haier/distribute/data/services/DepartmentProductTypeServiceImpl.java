package com.haier.distribute.data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.haier.distribute.data.dao.distribute.DepartmentProductTypeDao;
import com.haier.distribute.data.dao.shop.ProductsShopDao;
import com.haier.distribute.data.model.DepartmentProductType;
import com.haier.distribute.data.service.DepartmentProductTypeService;
import org.springframework.stereotype.Service;


@Service
public class DepartmentProductTypeServiceImpl implements DepartmentProductTypeService {
    @Autowired
    DepartmentProductTypeDao DepartmentProductTypeDao;
    
    @Autowired
	ProductsShopDao productsShopDao;

    @Override
    public List<DepartmentProductType> selectproduct() {
        // TODO Auto-generated method stub
        return DepartmentProductTypeDao.selectproduct();
    }

    @Override
    public String selectcode(int productTypeId) {
        // TODO Auto-generated method stub
        return DepartmentProductTypeDao.selectcode(productTypeId);
    }

    @Override
    public List<DepartmentProductType> selectname(int productTypeId) {
        // TODO Auto-generated method stub
        return DepartmentProductTypeDao.selectname(productTypeId);
    }

    @Override
    public DepartmentProductType getDepartment(Integer productTypeId) {
        return productsShopDao.getDepartment(productTypeId);
    }

}