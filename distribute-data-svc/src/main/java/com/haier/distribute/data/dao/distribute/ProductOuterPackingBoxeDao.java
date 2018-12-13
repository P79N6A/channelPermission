package com.haier.distribute.data.dao.distribute;

import com.haier.distribute.data.model.ProductOuterPackingBoxesDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductOuterPackingBoxeDao {
    public List<ProductOuterPackingBoxesDTO> selectByProductId(@Param("id")Integer id);
    public void insertBath(List<ProductOuterPackingBoxesDTO> list);
    public void deleteById(@Param("id")Integer id);
    public List<ProductOuterPackingBoxesDTO> selectAll();
}
