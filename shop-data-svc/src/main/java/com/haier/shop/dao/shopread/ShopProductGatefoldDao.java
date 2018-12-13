package com.haier.shop.dao.shopread;

import com.haier.shop.model.ProductGatefoldsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopProductGatefoldDao {
    List<ProductGatefoldsDTO> selectByProductId(Integer id);

    void insertBath(List<ProductGatefoldsDTO> productFeaturesDTOArrayList);

    void deleteById(Integer id);
}
