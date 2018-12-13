package com.haier.distribute.data.dao.distribute;

import com.haier.distribute.data.model.ProductSpecificationsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductSpecificationDao {
    public List<ProductSpecificationsDTO> selectByProductId(@Param("id")Integer id);
    public void insertBath(List<ProductSpecificationsDTO> list);
    public void deleteById(@Param("id")Integer id);
    public  List<ProductSpecificationsDTO> selectAll();
}
