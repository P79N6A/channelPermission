package com.haier.shop.dao.shopread;


import com.haier.shop.model.ProductActivities;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductActivitiesReadDao {
    ProductActivities get(Integer id);
}
