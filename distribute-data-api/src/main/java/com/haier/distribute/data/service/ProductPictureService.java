package com.haier.distribute.data.service;

import com.haier.distribute.data.model.ProductPicturesDTO;

import java.util.List;

public interface ProductPictureService {
    public List<ProductPicturesDTO> selectByProductId(Integer id);
    public void insertBath(List<ProductPicturesDTO> list);
    public void deleteById(Integer id);
    public List<ProductPicturesDTO> selectAll();
}
