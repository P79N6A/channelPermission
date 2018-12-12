package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.WwwHpRecords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WwwHpRecordsWriteDao {


    int insert(WwwHpRecords wwwHpRecords);

    int update(WwwHpRecords wwwHpRecords);

}