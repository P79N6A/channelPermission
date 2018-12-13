package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.Storages;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:JinXueqian
 * @Date: 2018/7/27 16:57
 */
@Mapper
public interface StoragesWriteDao {

     int deleteByPrimaryKey(Integer id);

     int insert(Storages storages);
}
