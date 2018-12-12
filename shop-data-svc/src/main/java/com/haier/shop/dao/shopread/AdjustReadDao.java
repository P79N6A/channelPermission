package com.haier.shop.dao.shopread;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.haier.shop.model.Adjust;
import com.haier.shop.model.Invoices;
@Mapper
public interface AdjustReadDao extends BaseReadDao<Adjust> {

    String getVehicleAdjustNo(@Param("begin") String begin);

	List<Invoices> getAdjustByTiming(@Param("startTime") long startTime, @Param("endTime") long endTime);
}
