package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.Members;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MembersWriteDao {

    int update(Members members);
}
