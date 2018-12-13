package com.haier.distribute.data.dao.distribute;

import com.haier.distribute.data.model.ProductGatefoldsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductGatefoldDao {
    public List<ProductGatefoldsDTO> selectByProductId(@Param("id")Integer id);
    public void insertBath(List<ProductGatefoldsDTO> list);
    public void  deleteById(@Param("id")Integer id);
    public List<ProductGatefoldsDTO> selectAll();
}
