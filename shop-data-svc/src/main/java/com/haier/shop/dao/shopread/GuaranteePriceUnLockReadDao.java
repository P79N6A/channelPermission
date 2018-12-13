package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderPriceGate;
import com.haier.shop.model.OrderPriceSourceChannel;
import java.util.List;
import java.util.Map;
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
public interface GuaranteePriceUnLockReadDao {

  /**
   * 获取 渠道 下拉列表
   * @return
   */
  List<Map<String, String>> getGuaranteePriceChannel();

  /**
   * 获取 订单来源 下拉列表
   * @return
   */
  List<Map<String, String>> getGuaranteePriceSource(@Param("channel") String channel);

  /**
   * 获取渠道订单来源
   * @return
   */
  List<Map<String, String>> getGuaranteePriceChannelSource();

  /**
   * 查询保本价锁定列表总条数
   * @param params
   * @return
   */
  int getGuaranteePriceListCount(Map<String, Object> params);

  /**
   * 查询保本价锁定列表
   * @param params
   * @return
   */
  List<OrderPriceGate> getGuaranteePriceList(Map<String, Object> params);

  /**
   * 根据订单号获取锁定记录中的订单和网单号
   * @param orderSns
   * @return
   */
  List<Map<String, String>> getGateOrderSnList(@Param("orderSns") List<String> orderSns);

  /**
   * 查询保本价渠道订单来源配置列表总条数总条数
   * @param params
   * @return
   */
  int getOrderPriceSourceChannelListCount(Map<String, Object> params);

  /**
   * 查询保本价渠道订单来源配置列表总条数
   * @param params
   * @return
   */
  List<OrderPriceSourceChannel> getOrderPriceSourceChannelList(Map<String, Object> params);

}
