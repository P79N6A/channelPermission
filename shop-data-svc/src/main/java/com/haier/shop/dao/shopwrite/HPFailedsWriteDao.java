package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.HPFaileds;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HPFailedsWriteDao {

    public Integer insert(HPFaileds hpFailed);

    public Integer updateHpFailed(HPFaileds hpFailed);

}
