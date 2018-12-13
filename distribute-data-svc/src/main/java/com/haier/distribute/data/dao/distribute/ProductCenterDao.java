package com.haier.distribute.data.dao.distribute;

import com.haier.distribute.data.model.ProductCenterDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCenterDao {
    public ProductCenterDTO selectByCode(String code);
    public int insertProductInfo(@Param("entity")ProductCenterDTO simpleProduct);
    public void updateById(@Param("entity") ProductCenterDTO productCenterDTO);
    public List<ProductCenterDTO> selectAll();
    public List<ProductCenterDTO> selectBySku(@Param("list") List<String> list);
	public List<ProductCenterDTO> selectByProductCode(@Param("list") List<String> list);
}
