package com.haier.distribute.data.dao.distribute;

import com.haier.distribute.data.model.TSaleProductPrice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TSaleProductPriceDao extends BaseDao<TSaleProductPrice>{
    int deleteByPrimaryKey(Integer id);

    int insert(TSaleProductPrice record);

    int insertSelective(TSaleProductPrice record);

    TSaleProductPrice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TSaleProductPrice record);

    int updateByPrimaryKey(TSaleProductPrice record);

    List<TSaleProductPrice> selectBySaleId(Integer saleId);

    List<TSaleProductPrice> selectCount(@Param("startDateTime") String startDateTime, @Param("endDateTime") String endDateTime, @Param("saleId") Integer saleId);

    int deleteAuto(Integer saleid);

    long checkPriceTime(@Param("id") int id, @Param("saleId") int saleId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<TSaleProductPrice> getExportData(@Param("entity")TSaleProductPrice condition);
}