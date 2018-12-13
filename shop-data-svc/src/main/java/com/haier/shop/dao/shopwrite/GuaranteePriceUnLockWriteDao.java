package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderPriceSourceChannel;
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
public interface GuaranteePriceUnLockWriteDao {

  /**
   * 创建保本价渠道订单来源配置列表
   * @param id
   * @return
   */
  int createOrderPriceSourceChannel(OrderPriceSourceChannel orderPriceSourceChannel);

  /**
   * 更新保本价渠道订单来源配置列表
   * @param id
   * @return
   */
  int updateOrderPriceSourceChannel(OrderPriceSourceChannel orderPriceSourceChannel);

  /**
   * 根据id删除保本价渠道订单来源配置列表
   * @param id
   * @return
   */
  int deleteOrderPriceSourceChannelById(@Param("id") Integer id);

}
