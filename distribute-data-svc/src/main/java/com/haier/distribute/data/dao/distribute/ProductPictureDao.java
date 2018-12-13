package com.haier.distribute.data.dao.distribute;

import com.haier.distribute.data.model.ProductPicturesDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductPictureDao {
    public List<ProductPicturesDTO> selectByProductId(@Param("id")Integer id);
    public void insertBath(List<ProductPicturesDTO> list);
    public void deleteById(@Param("id")Integer id);
    public List<ProductPicturesDTO> selectAll();
}
