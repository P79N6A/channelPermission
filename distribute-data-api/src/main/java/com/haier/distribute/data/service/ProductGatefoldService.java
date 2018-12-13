package com.haier.distribute.data.service;

import com.haier.distribute.data.model.ProductGatefoldsDTO;

import java.util.List;

public interface ProductGatefoldService {
    public List<ProductGatefoldsDTO> selectByProductId(Integer id);
    public void insertBath(List<ProductGatefoldsDTO> list);
    public void  deleteById(Integer id);
    public List<ProductGatefoldsDTO> selectAll();
}
