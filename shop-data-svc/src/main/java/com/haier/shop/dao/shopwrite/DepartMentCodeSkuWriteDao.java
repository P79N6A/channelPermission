package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.DepartMentCodeSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartMentCodeSkuWriteDao {

    int insert(DepartMentCodeSku record);
    
    int deleteByPrimaryKey(Integer id);

    int insertSelective(DepartMentCodeSku record);

    int updateByPrimaryKeySelective(DepartMentCodeSku record);

    int updateByPrimaryKey(DepartMentCodeSku record);
}