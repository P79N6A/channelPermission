package com.haier.distribute.data.dao.distribute;

import com.haier.distribute.data.model.TOrderLogistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TOrderLogisticsDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TOrderLogistics record);

    int insertSelective(TOrderLogistics record);

    TOrderLogistics selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TOrderLogistics record);

    int updateByPrimaryKey(TOrderLogistics record);

	List<TOrderLogistics> checkExpressNo(@Param("expressNo") String expressNo, @Param("orderSn") String orderSn);

    int editExpressNo(@Param("expressNo") String expressNo, @Param("orderSn") String orderSn);
}