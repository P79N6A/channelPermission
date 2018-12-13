package com.haier.distribute.data.service;

import com.haier.distribute.data.model.ProductCenterDTO;

import java.util.List;

public interface ProductCenterService {
    public ProductCenterDTO selectByCode(String code);
    public ProductCenterDTO insertProductInfo(ProductCenterDTO productCenterDTO);
    public void updateById(ProductCenterDTO productCenterDTO);
    public List<ProductCenterDTO> selectAll();
    public List<ProductCenterDTO> selectBySku(List<String> list);
    public List<ProductCenterDTO> selectByProductCode(List<String> list);
}
