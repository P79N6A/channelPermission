package com.haier.distribute.data.dao.distribute;

import com.haier.distribute.data.model.ProductFeaturesDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductFeatureDao {
    public List<ProductFeaturesDTO> selectByProductId(@Param("id")Integer id);
    public void insertBath(List<ProductFeaturesDTO> list);
    public void deleteById(@Param("id")Integer id);
    public List<ProductFeaturesDTO> selectAll();
}
