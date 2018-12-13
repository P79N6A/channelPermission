package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderPriceSourceIndustry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 保本价渠道和订单来源配置 旧系统代码迁移
 * @Author: liwei
 * @Description:
 * @Date: Create in 10:09 2018/6/20
 * @Modified By:
 */
@Mapper
public interface OrderPriceSourceIndustryWriteDao {

  /**
   * 创建订单价格管控来源产业配置列表
   * @return
   */
  int createOrderPriceSourceIndustry(OrderPriceSourceIndustry orderPriceSourceIndustry);

  /**
   * 更新订单价格管控来源产业配置列表
   * @return
   */
  int updateOrderPriceSourceIndustry(OrderPriceSourceIndustry orderPriceSourceIndustry);

  /**
   * 根据id删除订单价格管控来源产业配置列表
   * @param id
   * @return
   */
  int deleteOrderPriceSourceIndustryById(@Param("id") Integer id);

}
