package com.haier.shop.dao.shopwrite;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.haier.shop.model.LesStockSyncs;

import java.util.Date;
import java.util.List;

@Mapper
public interface LesStockSyncsDao {
    List<LesStockSyncs> getUnDoneLesStockSyncs();

    Integer setLesStockSyncsDone(@Param("id") Integer id);

    Integer setErrMsg(@Param("id") Integer id, @Param("errorMsg") String errmsg);

    Integer update(@Param("id") Integer id, @Param("isDone") Integer isDone,
                   @Param("errMsg") String errMsg, @Param("doneTime") String doneTime);

    Integer insert(LesStockSyncs stockSyncs);
    
    Integer getIdbyDonetime(String donetime);
}
