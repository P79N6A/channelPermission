package com.haier.distribute.data.service;

import com.haier.distribute.data.model.ProductOuterPackingBoxesDTO;

import java.util.List;

public interface ProductOuterPackingBoxeService {
    public List<ProductOuterPackingBoxesDTO> selectByProductId(Integer id);
    public void insertBath(List<ProductOuterPackingBoxesDTO> list);
    public void deleteById(Integer id);
    public List<ProductOuterPackingBoxesDTO> selectAll();
}
