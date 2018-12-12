package com.haier.shop.dao.shopread;


import com.haier.shop.model.Members;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MembersReadDao {

    Members get(Integer id);

    String getMemberMobile(Integer id);
}
