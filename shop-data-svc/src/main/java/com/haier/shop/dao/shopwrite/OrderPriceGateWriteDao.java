package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderPriceGate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderPriceGateWriteDao {

	int batchInsert(List<OrderPriceGate> orderPriceGateList);
	int unLockOrderPriceGatebyOrderSn(@Param("orderSn") String orderSn,
									  @Param("operator") String operator,
									  @Param("responsiblePerson") String responsiblePerson,
									  @Param("unlockReason") String unlockReason);
}
