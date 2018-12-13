package com.haier.shop.dao.shopread;

import com.haier.shop.model.DepartMentCodeSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartMentCodeSkuReadDao {

    Integer selectKt(String sku); //查询sku是否是空调


    DepartMentCodeSku selectByPrimaryKey(Integer id);

    List<DepartMentCodeSku> selectdepartmentcodesku(String sku);
}