package com.haier.shop.dao.shopwrite;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MdmDataWriteDao {
    public int insert(List<Map<String,Object>> maps);
    public void delete();
}
