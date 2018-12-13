package com.haier.shop.dao.shopread;

import com.haier.shop.model.ProductPicturesDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShopProductPictureDao {
    List<ProductPicturesDTO> selectByProductId(Integer id);

    void insertBath(List<ProductPicturesDTO> productPicturesDTOList);

    void deleteById(Integer id);
}
