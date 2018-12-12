package com.haier.stock.dao.stock;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.stock.model.PopInvWarehouse;

public interface PopInvWarehouseDao extends BaseDao<PopInvWarehouse> {
    int deleteByPrimaryKey(String whCode);

    int insert(PopInvWarehouse record);

    int insertSelective(PopInvWarehouse record);

    PopInvWarehouse selectByPrimaryKey(String whCode);

    int updateByPrimaryKeySelective(PopInvWarehouse record);

    int updateByPrimaryKey(PopInvWarehouse record);

    long checkCodeSame(String whCode);

    long checkNameSame(String whName);

    List<PopInvWarehouse> exportWarehouse(@Param("entity") PopInvWarehouse entity);
}