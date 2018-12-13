package com.haier.distribute.data.service;

import com.haier.distribute.data.model.ProductSpecificationsDTO;

import java.util.List;

public interface ProductSpecificationService {
    public List<ProductSpecificationsDTO> selectByProductId(Integer id);
    public void insertBath(List<ProductSpecificationsDTO> list);
    public void deleteById(Integer id);
    public  List<ProductSpecificationsDTO> selectAll();
}
