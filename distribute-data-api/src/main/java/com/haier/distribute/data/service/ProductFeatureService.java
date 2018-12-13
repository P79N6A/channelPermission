package com.haier.distribute.data.service;

import com.haier.distribute.data.model.ProductFeaturesDTO;

import java.util.List;

public interface ProductFeatureService {
    public List<ProductFeaturesDTO> selectByProductId(Integer id);
    public void insertBath(List<ProductFeaturesDTO> list);
    public void deleteById(Integer id);
    public List<ProductFeaturesDTO> selectAll();
}
