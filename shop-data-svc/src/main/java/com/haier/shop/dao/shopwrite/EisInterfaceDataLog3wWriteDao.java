package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.EisInterfaceDataLog3W;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EisInterfaceDataLog3wWriteDao {

    Integer insert(EisInterfaceDataLog3W log);
}
