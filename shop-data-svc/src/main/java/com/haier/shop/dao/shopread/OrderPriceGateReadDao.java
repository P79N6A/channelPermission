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

	/**
	 * 查询订单价格闸口列表
	 * @param paramMap
	 * @return
	 */
	List<OrderPriceGate> getOrderPriceGateList(@Param("params") Map<String, Object> paramMap);

	/**
	 * 获得条数[订单价格闸口列表]
	 *
	 * @return
	 */
	public int getRows(@Param("params")Map<String,Object> param);


}
