package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderPriceGate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface OrderPriceGateReadDao {
	 OrderPriceGate getOrderPriceGatebyCOrderSn(@Param("cOrderSn") String cOrderSn,
                                                @Param("gateType") Integer gateType);

	int unLockOrderPriceGatebyOrderSn(@Param("orderSn") String orderSn,
                                      @Param("operator") String operator,
                                      @Param("responsiblePerson") String responsiblePerson,
                                      @Param("unlockReason") String unlockReason);
	
	List<Map<String, Object>> getUnLockbyOrderSn(@Param("orderSn") String orderSn);
	
	String getBrandNameByBrandId(@Param("id") Integer id);
	
	String getCateNameByCateId(@Param("id") Integer id);
}
