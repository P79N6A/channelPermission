package com.haier.shop.dao.settleCenter;

import com.haier.shop.model.OdsUserCategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OdsUserCategoryBrandDao {

    /**
     * 查询
     * @param params
     */
    List<OdsUserCategoryBrand> queryUserCategoryBrandList(Map<String, Object> params);
}
