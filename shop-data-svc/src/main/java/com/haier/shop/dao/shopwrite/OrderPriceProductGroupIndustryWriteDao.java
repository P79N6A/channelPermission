package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderPriceProductGroupIndustry;
import org.apache.ibatis.annotations.Param;

public interface OrderPriceProductGroupIndustryWriteDao {

  /**
   * 创建订单价格管控产品组产业配置列表
   * @return
   */
  int createOrderPriceProductGroupIndustry(OrderPriceProductGroupIndustry orderPriceProductGroupIndustry);

  /**
   * 更新订单价格管控产品组产业配置列表
   * @return
   */
  int updateOrderPriceProductGroupIndustry(OrderPriceProductGroupIndustry orderPriceProductGroupIndustry);

  /**
   * 根据id删除订单价格管控产品组产业配置列表
   * @param id
   * @return
   */
  int deleteOrderPriceProductGroupIndustryById(@Param("id") Integer id);

}
