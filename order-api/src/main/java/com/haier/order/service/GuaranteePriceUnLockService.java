package com.haier.order.service;

import com.haier.shop.model.OrderPriceGate;
import com.haier.shop.model.OrderPriceProductGroupIndustry;
import com.haier.shop.model.OrderPriceSourceChannel;
import com.haier.shop.model.OrderPriceSourceIndustry;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: liwei
 * @Description:
 * @Date: Create in 9:44 2018/6/20
 * @Modified By:
 */
public interface GuaranteePriceUnLockService {

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
   * 获取 渠道 下拉列表
   * @return
   */
  List<Map<String, String>> getGuaranteePriceChannel();

  /**
   * 获取 订单来源 下拉列表
   * @return
   */
  List<Map<String, String>> getGuaranteePriceSource(String channel);

  /**
   * 根据订单号获取锁定记录中的订单和网单号
   * @return
   */
  Map<String, Set<String>> getGateOrderSnList(List<String> list);

  /**
   * 查询保本价渠道订单来源配置列表总条数
   * @param params
   * @return
   */
  int getOrderPriceSourceChannelListCount(Map<String, Object> params);

  /**
   * 查询保本价渠道订单来源配置列表
   * @param params
   * @return
   */
  List<OrderPriceSourceChannel> getOrderPriceSourceChannelList(Map<String, Object> params);

  /**
   * 创建保本价渠道订单来源配置列表
   * @param orderPriceSourceChannel
   * @return
   */
  int createOrderPriceSourceChannel(OrderPriceSourceChannel orderPriceSourceChannel);
  /**
   * 更新保本价渠道订单来源配置列表
   * @param orderPriceSourceChannel
   * @return
   */
  int updateOrderPriceSourceChannel(OrderPriceSourceChannel orderPriceSourceChannel);

  /**
   * 根据id删除保本价渠道订单来源配置列表
   * @param id
   * @return
   */
  int deleteOrderPriceSourceChannelById(int id);

  /**
   * 获取stock库渠道下拉框
   * @return
   */
  List<Map<String, String>> getInvStockChannel();

  /**
   * 根据渠道获取stock库订单来源下拉框
   * @param channelCode
   * @return
   */
  List<Map<String, String>> getInvChannel2OrderSource(String channelCode);

  /**
   * 获取产业下拉框
   * @return
   */
  List<Map<String, String>> getOrderPriceIndustry();

  /**
   * 产品组下拉框
   * @return
   */
  List<Map<String, String>> getOrderPriceProductGroup(String industry);

  /**
   * 查询订单价格管控产品组产业配置列表总条数
   * @param params
   * @return
   */
  int getOrderPriceProductGroupIndustryListCount(Map<String, Object> params);

  /**
   * 查询订单价格管控产品组产业配置列表
   * @param params
   * @return
   */
  List<OrderPriceProductGroupIndustry> getOrderPriceProductGroupIndustryList(Map<String, Object> params);

  /**
   * 创建订单价格管控产品组产业配置列表
   * @param orderPriceProductGroupIndustry
   * @return
   */
  int createOrderPriceProductGroupIndustry(OrderPriceProductGroupIndustry orderPriceProductGroupIndustry);

  /**
   * 更新订单价格管控产品组产业配置列表
   * @param orderPriceProductGroupIndustry
   * @return
   */
  int updateOrderPriceProductGroupIndustry(OrderPriceProductGroupIndustry orderPriceProductGroupIndustry);

  /**
   * 根据id删除订单价格管控产品组产业配置列表
   * @param id
   * @return
   */
  int deleteOrderPriceProductGroupIndustryById(int id);

  /**
   * 产业下拉框
   * @return
   */
  List<Map<String, String>> getOrderPriceIndustryBySource(String source);

  /**
   * 查询订单价格管控来源产业配置列表总条数
   * @param params
   * @return
   */
  int getOrderPriceSourceIndustryListCount(Map<String, Object> params);

  /**
   * 查询订单价格管控来源产业配置列表
   * @param params
   * @return
   */
  List<OrderPriceSourceIndustry> getOrderPriceSourceIndustryList(Map<String, Object> params);

  /**
   * 创建订单价格管控来源产业配置列表
   * @param orderPriceSourceIndustry
   * @return
   */
  int createOrderPriceSourceIndustry(OrderPriceSourceIndustry orderPriceSourceIndustry);

  /**
   * 更新订单价格管控来源产业配置列表
   * @param orderPriceSourceIndustry
   * @return
   */
  int updateOrderPriceSourceIndustry(OrderPriceSourceIndustry orderPriceSourceIndustry);

  /**
   * 根据id删除订单价格管控来源产业配置列表
   * @param id
   * @return
   */
  int deleteOrderPriceSourceIndustryById(int id);
}
