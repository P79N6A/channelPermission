package com.haier.shop.services;

import com.haier.shop.dao.shopread.GuaranteePriceUnLockReadDao;
import com.haier.shop.dao.shopwrite.GuaranteePriceUnLockWriteDao;
import com.haier.shop.model.OrderPriceGate;
import com.haier.shop.model.OrderPriceSourceChannel;
import com.haier.shop.service.GuaranteePriceUnLockShopService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 保本价渠道和订单来源配置 旧系统代码迁移
 * @Author: liwei
 * @Description:
 * @Date: Create in 10:09 2018/6/20
 * @Modified By:
 */
@Service
public class GuaranteePriceUnLockShopServiceImpl implements GuaranteePriceUnLockShopService {

  @Autowired
  private GuaranteePriceUnLockReadDao guaranteePriceUnLockReadDao;
  @Autowired
  private GuaranteePriceUnLockWriteDao guaranteePriceUnLockWriteDao;

  @Override
  public List<Map<String, String>> getGuaranteePriceChannel() {
    return guaranteePriceUnLockReadDao.getGuaranteePriceChannel();
  }

  @Override
  public List<Map<String, String>> getGuaranteePriceSource(String channel) {
    return guaranteePriceUnLockReadDao.getGuaranteePriceSource(channel);
  }

  @Override
  public List<Map<String, String>> getGuaranteePriceChannelSource() {
    return guaranteePriceUnLockReadDao.getGuaranteePriceChannelSource();
  }

  @Override
  public int getGuaranteePriceListCount(Map<String, Object> params) {
    return guaranteePriceUnLockReadDao.getGuaranteePriceListCount(params);
  }

  @Override
  public List<OrderPriceGate> getGuaranteePriceList(Map<String, Object> params) {
    return guaranteePriceUnLockReadDao.getGuaranteePriceList(params);
  }

  @Override
  public List<Map<String, String>> getGateOrderSnList(List<String> orderSns) {
    return guaranteePriceUnLockReadDao.getGateOrderSnList(orderSns);
  }

  @Override
  public int getOrderPriceSourceChannelListCount(Map<String, Object> params) {
    return guaranteePriceUnLockReadDao.getOrderPriceSourceChannelListCount(params);
  }

  @Override
  public List<OrderPriceSourceChannel> getOrderPriceSourceChannelList(Map<String, Object> params) {
    return guaranteePriceUnLockReadDao.getOrderPriceSourceChannelList(params);
  }

  @Override
  public int createOrderPriceSourceChannel(OrderPriceSourceChannel orderPriceSourceChannel) {
    return guaranteePriceUnLockWriteDao.createOrderPriceSourceChannel(orderPriceSourceChannel);
  }

  @Override
  public int updateOrderPriceSourceChannel(OrderPriceSourceChannel orderPriceSourceChannel) {
    return guaranteePriceUnLockWriteDao.updateOrderPriceSourceChannel(orderPriceSourceChannel);
  }

  @Override
  public int deleteOrderPriceSourceChannelById(Integer id) {
    return guaranteePriceUnLockWriteDao.deleteOrderPriceSourceChannelById(id);
  }
}
