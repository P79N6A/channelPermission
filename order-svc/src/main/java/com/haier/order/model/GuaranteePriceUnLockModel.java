package com.haier.order.model;

import com.haier.shop.model.OrderPriceGate;
import com.haier.shop.model.OrderPriceProductGroupIndustry;
import com.haier.shop.model.OrderPriceSourceChannel;
import com.haier.shop.model.OrderPriceSourceIndustry;
import com.haier.shop.service.GuaranteePriceUnLockShopService;
import com.haier.shop.service.OrderPriceProductGroupIndustryService;
import com.haier.shop.service.OrderPriceSourceIndustryService;
import com.haier.stock.service.StockInvChannel2OrderSourceService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 保本价渠道和订单来源配置 旧系统代码迁移
 * @Author: liwei
 * @Description:
 * @Date: Create in 9:44 2018/6/20
 * @Modified By:
 */
@Service("guaranteePriceUnLockModel")
public class GuaranteePriceUnLockModel {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
        .getLogger(GuaranteePriceUnLockModel.class);

    @Autowired
    private GuaranteePriceUnLockShopService guaranteePriceUnLockShopService;
    @Autowired
    private StockInvChannel2OrderSourceService stockInvChannel2OrderSourceService;
    @Autowired
    private OrderPriceProductGroupIndustryService orderPriceProductGroupIndustryService;
    @Autowired
    private OrderPriceSourceIndustryService orderPriceSourceIndustryService;

    /**
     * 查询保本价锁定列表总条数
     * @param params
     * @return
     */
    public int getGuaranteePriceListCount(Map<String, Object> params) {
        return guaranteePriceUnLockShopService.getGuaranteePriceListCount(params);
    }

    /**
     * 查询保本价锁定列表
     * @param params
     * @return
     */
    public List<OrderPriceGate> getGuaranteePriceList(Map<String, Object> params) {
        List<OrderPriceGate> list = guaranteePriceUnLockShopService.getGuaranteePriceList(params);
        if (list != null && list.size() > 0) {
            List<Map<String, String>> sourceList = guaranteePriceUnLockShopService
                .getGuaranteePriceChannelSource();
            if (sourceList != null && sourceList.size() > 0) {
                Map<String, String> sourceMap = new HashMap<String, String>();
                Map<String, String> channelMap = new HashMap<String, String>();
                for (Map<String, String> map : sourceList) {
                    sourceMap.put(map.get("order_source"), map.get("order_source_name"));
                    channelMap.put(map.get("channel_code"), map.get("channel_name"));
                }
                //                Map<String, String> payMap = new HashMap<String, String>();
                //                payMap.put("200", "未付款");
                //                payMap.put("201", "已付款");
                //                payMap.put("206", "待退款");
                //                payMap.put("207", "已退款");
                for (OrderPriceGate orderPriceGate : list) {
                    orderPriceGate.setOrderSource(sourceMap.get(orderPriceGate.getOrderSource()));
                    orderPriceGate.setChannelCode(channelMap.get(orderPriceGate.getChannelCode()));
                    orderPriceGate.setOrderStatus(OrderStatus
                        .getByCode(Integer.parseInt(orderPriceGate.getOrderStatus())).getName());
                    orderPriceGate.setOrderProductStatus(OrderProductStatus
                        .getByCode(Integer.parseInt(orderPriceGate.getOrderProductStatus()))
                        .getName());
                    //                    orderPriceGate.setPayStatus(payMap.get(orderPriceGate.getPayStatus()));
                }
            }
            List<OrderPriceProductGroupIndustry> industryList = orderPriceProductGroupIndustryService
                .getOrderPriceProductGroupIndustryList(null);
            if (industryList != null && industryList.size() > 0) {
                Map<String, String> groupMap = new HashMap<String, String>();
                Map<String, String> industryMap = new HashMap<String, String>();
                for (OrderPriceProductGroupIndustry temp : industryList) {
                    groupMap.put(temp.getProductGroup(), temp.getProductGroupName());
                    industryMap.put(temp.getIndustryCode(), temp.getIndustryName());
                }
                for (OrderPriceGate orderPriceGate : list) {
                    orderPriceGate.setProductGroup(groupMap.get(orderPriceGate.getProductGroup()));
                    orderPriceGate
                        .setIndustryCode(industryMap.get(orderPriceGate.getIndustryCode()));
                }
            }
        }
        return list;
    }

    /**
     * 获取 渠道 下拉列表
     * @return
     */
    public List<Map<String, String>> getGuaranteePriceChannel() {
        return guaranteePriceUnLockShopService.getGuaranteePriceChannel();
    }

    /**
     * 获取 订单来源 下拉列表
     * @return
     */
    public List<Map<String, String>> getGuaranteePriceSource(String channel) {
        return guaranteePriceUnLockShopService.getGuaranteePriceSource(channel);
    }

    /**
     * 根据订单号获取锁定记录中的订单和网单号
     * @return
     */
    public Map<String, Set<String>> getGateOrderSnList(List<String> list) {
        List<Map<String, String>> snList = guaranteePriceUnLockShopService.getGateOrderSnList(list);
        if (snList == null || snList.size() == 0) {
            return null;
        }
        //key=订单号,value=网单号Set
        Map<String, Set<String>> osAndOpMap = new HashMap<String, Set<String>>();
        //网单号
        Set<String> cOrderSnSet = new HashSet<String>();
        for (Map<String, String> map : snList) {
            if (osAndOpMap.containsKey(map.get("order_sn"))) {
                cOrderSnSet = osAndOpMap.get(map.get("order_sn"));
            } else {
                cOrderSnSet = new HashSet<String>();
                osAndOpMap.put(map.get("order_sn"), cOrderSnSet);
            }
            cOrderSnSet.add(map.get("corder_sn"));
        }
        return osAndOpMap;
    }

    /**
     * 查询保本价渠道订单来源配置列表总条数
     * @param params
     * @return
     */
    public int getOrderPriceSourceChannelListCount(Map<String, Object> params) {
        return guaranteePriceUnLockShopService.getOrderPriceSourceChannelListCount(params);
    }

    /**
     * 查询保本价渠道订单来源配置列表
     * @param params
     * @return
     */
    public List<OrderPriceSourceChannel> getOrderPriceSourceChannelList(Map<String, Object> params) {
        return guaranteePriceUnLockShopService.getOrderPriceSourceChannelList(params);
    }

    /**
     * 创建保本价渠道订单来源配置列表
     * @param orderPriceSourceChannel
     * @return
     */
    public int createOrderPriceSourceChannel(OrderPriceSourceChannel orderPriceSourceChannel) {
        return guaranteePriceUnLockShopService.createOrderPriceSourceChannel(orderPriceSourceChannel);
    }

    /**
     * 更新保本价渠道订单来源配置列表
     * @param orderPriceSourceChannel
     * @return
     */
    public int updateOrderPriceSourceChannel(OrderPriceSourceChannel orderPriceSourceChannel) {
        return guaranteePriceUnLockShopService.updateOrderPriceSourceChannel(orderPriceSourceChannel);
    }

    /**
     * 根据id删除保本价渠道订单来源配置列表
     * @param id
     * @return
     */
    public int deleteOrderPriceSourceChannelById(int id) {
        return guaranteePriceUnLockShopService.deleteOrderPriceSourceChannelById(id);
    }

    /**
     * 获取stock库渠道下拉框
     * @return
     */
    public List<Map<String, String>> getInvStockChannel() {
        return stockInvChannel2OrderSourceService.getInvStockChannel();
    }

    /**
     * 根据渠道获取stock库订单来源下拉框
     * @param channelCode
     * @return
     */
    public List<Map<String, String>> getInvChannel2OrderSource(String channelCode) {
        return stockInvChannel2OrderSourceService.getInvChannel2OrderSource(channelCode);
    }

    /**
     * 获取产业下拉框
     * @return
     */
    public List<Map<String, String>> getOrderPriceIndustry() {
        return orderPriceProductGroupIndustryService.getOrderPriceIndustry();
    }

    /**
     * 产品组下拉框
     * @return
     */
    public List<Map<String, String>> getOrderPriceProductGroup(String industry) {
        return orderPriceProductGroupIndustryService.getOrderPriceProductGroup(industry);
    }

    /**
     * 查询订单价格管控产品组产业配置列表总条数
     * @param params
     * @return
     */
    public int getOrderPriceProductGroupIndustryListCount(Map<String, Object> params) {
        return orderPriceProductGroupIndustryService.getOrderPriceProductGroupIndustryListCount(params);
    }

    /**
     * 查询订单价格管控产品组产业配置列表
     * @param params
     * @return
     */
    public List<OrderPriceProductGroupIndustry> getOrderPriceProductGroupIndustryList(Map<String, Object> params) {
        return orderPriceProductGroupIndustryService.getOrderPriceProductGroupIndustryList(params);
    }

    /**
     * 创建订单价格管控产品组产业配置列表
     * @param orderPriceProductGroupIndustry
     * @return
     */
    public int createOrderPriceProductGroupIndustry(OrderPriceProductGroupIndustry orderPriceProductGroupIndustry) {
        return orderPriceProductGroupIndustryService
            .createOrderPriceProductGroupIndustry(orderPriceProductGroupIndustry);
    }

    /**
     * 更新订单价格管控产品组产业配置列表
     * @param orderPriceProductGroupIndustry
     * @return
     */
    public int updateOrderPriceProductGroupIndustry(OrderPriceProductGroupIndustry orderPriceProductGroupIndustry) {
        return orderPriceProductGroupIndustryService
            .updateOrderPriceProductGroupIndustry(orderPriceProductGroupIndustry);
    }

    /**
     * 根据id删除订单价格管控产品组产业配置列表
     * @param id
     * @return
     */
    public int deleteOrderPriceProductGroupIndustryById(int id) {
        return orderPriceProductGroupIndustryService.deleteOrderPriceProductGroupIndustryById(id);
    }

    /**
     * 产业下拉框
     * @return
     */
    public List<Map<String, String>> getOrderPriceIndustryBySource(String source) {
        return orderPriceSourceIndustryService.getOrderPriceIndustryBySource(source);
    }

    /**
     * 查询订单价格管控来源产业配置列表总条数
     * @param params
     * @return
     */
    public int getOrderPriceSourceIndustryListCount(Map<String, Object> params) {
        return orderPriceSourceIndustryService.getOrderPriceSourceIndustryListCount(params);
    }

    /**
     * 查询订单价格管控来源产业配置列表
     * @param params
     * @return
     */
    public List<OrderPriceSourceIndustry> getOrderPriceSourceIndustryList(Map<String, Object> params) {
        return orderPriceSourceIndustryService.getOrderPriceSourceIndustryList(params);
    }

    /**
     * 创建订单价格管控来源产业配置列表
     * @param orderPriceSourceIndustry
     * @return
     */
    public int createOrderPriceSourceIndustry(OrderPriceSourceIndustry orderPriceSourceIndustry) {
        return orderPriceSourceIndustryService.createOrderPriceSourceIndustry(orderPriceSourceIndustry);
    }

    /**
     * 更新订单价格管控来源产业配置列表
     * @param orderPriceSourceIndustry
     * @return
     */
    public int updateOrderPriceSourceIndustry(OrderPriceSourceIndustry orderPriceSourceIndustry) {
        return orderPriceSourceIndustryService.updateOrderPriceSourceIndustry(orderPriceSourceIndustry);
    }

    /**
     * 根据id删除订单价格管控来源产业配置列表
     * @param id
     * @return
     */
    public int deleteOrderPriceSourceIndustryById(int id) {
        return orderPriceSourceIndustryService.deleteOrderPriceSourceIndustryById(id);
    }

}
