package com.haier.order.services;

import com.haier.order.model.GuaranteePriceUnLockModel;
import com.haier.order.service.GuaranteePriceUnLockService;
import com.haier.shop.model.OrderPriceGate;
import com.haier.shop.model.OrderPriceProductGroupIndustry;
import com.haier.shop.model.OrderPriceSourceChannel;
import com.haier.shop.model.OrderPriceSourceIndustry;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuaranteePriceUnLockServiceImpl implements GuaranteePriceUnLockService {

  @Autowired
  private GuaranteePriceUnLockModel guaranteePriceUnLockModel;

  @Override
  public int getGuaranteePriceListCount(Map<String, Object> params) {
    return guaranteePriceUnLockModel.getGuaranteePriceListCount(params);
  }

  @Override
  public List<OrderPriceGate> getGuaranteePriceList(Map<String, Object> params) {
    return guaranteePriceUnLockModel.getGuaranteePriceList(params);
  }

  @Override
  public List<Map<String, String>> getGuaranteePriceChannel() {
    return guaranteePriceUnLockModel.getGuaranteePriceChannel();
  }

  @Override
  public List<Map<String, String>> getGuaranteePriceSource(String channel) {
    return guaranteePriceUnLockModel.getGuaranteePriceSource(channel);
  }

  @Override
  public Map<String, Set<String>> getGateOrderSnList(List<String> list) {
    return guaranteePriceUnLockModel.getGateOrderSnList(list);
  }

  @Override
  public int getOrderPriceSourceChannelListCount(Map<String, Object> params) {
    return guaranteePriceUnLockModel.getOrderPriceSourceChannelListCount(params);
  }

  @Override
  public List<OrderPriceSourceChannel> getOrderPriceSourceChannelList(Map<String, Object> params) {
    return guaranteePriceUnLockModel.getOrderPriceSourceChannelList(params);
  }

  @Override
  public int createOrderPriceSourceChannel(OrderPriceSourceChannel orderPriceSourceChannel) {
    return guaranteePriceUnLockModel.createOrderPriceSourceChannel(orderPriceSourceChannel);
  }

  @Override
  public int updateOrderPriceSourceChannel(OrderPriceSourceChannel orderPriceSourceChannel) {
    return guaranteePriceUnLockModel.updateOrderPriceSourceChannel(orderPriceSourceChannel);
  }

  @Override
  public int deleteOrderPriceSourceChannelById(int id) {
    return guaranteePriceUnLockModel.deleteOrderPriceSourceChannelById(id);
  }

  @Override
  public List<Map<String, String>> getInvStockChannel() {
    return guaranteePriceUnLockModel.getInvStockChannel();
  }

  @Override
  public List<Map<String, String>> getInvChannel2OrderSource(String channelCode) {
    return guaranteePriceUnLockModel.getInvChannel2OrderSource(channelCode);
  }

  @Override
  public List<Map<String, String>> getOrderPriceIndustry() {
    return guaranteePriceUnLockModel.getOrderPriceIndustry();
  }

  @Override
  public List<Map<String, String>> getOrderPriceProductGroup(String industry) {
    return guaranteePriceUnLockModel.getOrderPriceProductGroup(industry);
  }

  @Override
  public int getOrderPriceProductGroupIndustryListCount(Map<String, Object> params) {
    return guaranteePriceUnLockModel.getOrderPriceProductGroupIndustryListCount(params);
  }

  @Override
  public List<OrderPriceProductGroupIndustry> getOrderPriceProductGroupIndustryList(
      Map<String, Object> params) {
    return guaranteePriceUnLockModel.getOrderPriceProductGroupIndustryList(params);
  }

  @Override
  public int createOrderPriceProductGroupIndustry(
      OrderPriceProductGroupIndustry orderPriceProductGroupIndustry) {
    return guaranteePriceUnLockModel.createOrderPriceProductGroupIndustry(orderPriceProductGroupIndustry);
  }

  @Override
  public int updateOrderPriceProductGroupIndustry(
      OrderPriceProductGroupIndustry orderPriceProductGroupIndustry) {
    return guaranteePriceUnLockModel.updateOrderPriceProductGroupIndustry(orderPriceProductGroupIndustry);
  }

  @Override
  public int deleteOrderPriceProductGroupIndustryById(int id) {
    return guaranteePriceUnLockModel.deleteOrderPriceProductGroupIndustryById(id);
  }

  @Override
  public List<Map<String, String>> getOrderPriceIndustryBySource(String source) {
    return guaranteePriceUnLockModel.getOrderPriceIndustryBySource(source);
  }

  @Override
  public int getOrderPriceSourceIndustryListCount(Map<String, Object> params) {
    return guaranteePriceUnLockModel.getOrderPriceSourceIndustryListCount(params);
  }

  @Override
  public List<OrderPriceSourceIndustry> getOrderPriceSourceIndustryList(
      Map<String, Object> params) {
    return guaranteePriceUnLockModel.getOrderPriceSourceIndustryList(params);
  }

  @Override
  public int createOrderPriceSourceIndustry(OrderPriceSourceIndustry orderPriceSourceIndustry) {
    return guaranteePriceUnLockModel.createOrderPriceSourceIndustry(orderPriceSourceIndustry);
  }

  @Override
  public int updateOrderPriceSourceIndustry(OrderPriceSourceIndustry orderPriceSourceIndustry) {
    return guaranteePriceUnLockModel.updateOrderPriceSourceIndustry(orderPriceSourceIndustry);
  }

  @Override
  public int deleteOrderPriceSourceIndustryById(int id) {
    return guaranteePriceUnLockModel.deleteOrderPriceSourceIndustryById(id);
  }
}
