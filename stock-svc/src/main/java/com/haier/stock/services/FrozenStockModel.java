package com.haier.stock.services;

import com.haier.shop.model.ExternalOrders;
import com.haier.shop.service.ExternalOrdersService;
import com.haier.stock.util.DateFormatUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



import com.haier.shop.model.OrderOperateLogs;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrderRepairLogs;
import com.haier.shop.model.OrderWorkflows;
import com.haier.shop.model.OrderYBCards;
import com.haier.shop.model.OrdersNew;
import com.haier.shop.model.ProductTypesNew;
import com.haier.shop.model.ProductsNew;
import com.haier.shop.model.Regions;
import com.haier.stock.model.Stock;
import com.haier.shop.service.MsLinkageService;
import com.haier.shop.service.OrderProductsNewService;
import com.haier.shop.service.OrderYBCardsService;
import com.haier.shop.service.OrdersNewService;
import com.haier.shop.service.RegionsService;
import com.haier.shop.service.ShopOrderOperateLogsService;
import com.haier.shop.service.ShopOrderRepairLogsService;
import com.haier.shop.service.ShopOrderRepairsService;
import com.haier.shop.service.ShopOrderWorkflowsService;
import com.haier.stock.datasource.ReadWriteRoutingDataSourceHolder;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.service.StockCenterItemService;
import com.haier.stock.service.StockCommonService;
import com.haier.stock.services.Helper.ThreadHelper;
import com.haier.stock.util.IProxy;
import com.haier.stock.util.TransactionProxy;
import com.haier.stock.util.Ustring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;


/**
 * 分配库位和占用库存,更新销量
 *
 * @Filename: FrozenStockModel.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
@Service
public class FrozenStockModel {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(FrozenStockModel.class);
    @Autowired
    private OrdersNewService ordersNewService;
    @Autowired
    private StockCenterHopStockServiceImpl hopStockService;
    @Autowired
    private StockCommonService stockCommonService;
    @Autowired
    private ShopOrderOperateLogsService shopOrderOperateLogsService;
    @Autowired
    private OrderProductsNewService orderProductsNewService;
    @Autowired
    private StockCenterItemService itemService;
    @Autowired
    private ThreadHelper threadHelper;
    @Autowired
    private TransactionProxy transactionProxy;
    @Autowired
    private RegionsService regionsService;
    @Autowired
    private MsLinkageService msLinkageService;
    @Autowired
    private ExternalOrdersService externalOrdersService;

    @Autowired
    private OrderYBCardsService orderYBCardsService;
    @Autowired
    private StockCenterSgStoreServiceImpl sgStoreService;
    @Autowired
    private ShopOrderWorkflowsService shopOrderWorkflowsService;
    /**
     * 正常自动冻结库存入口
     */
    public void autoFrozenStock() {
        //1.查询待占用网单列表
        List<OrderProductsNew> unFrozenOpList = orderProductsNewService.getUnLockStockOpList();
        if (unFrozenOpList == null || unFrozenOpList.isEmpty()) {
            log.info("占用库存,没有需要处理的数据");
            return;
        } else {
            log.info(Thread.currentThread().getId() + "," + Thread.currentThread().getName()
                    + " - 订单占用库存，获取待处理数据完成，数量 " + unFrozenOpList.size());
        }

        long start = System.currentTimeMillis();
        execute(unFrozenOpList);
        log.info(Thread.currentThread().getId() + "," + Thread.currentThread().getName()
                + " - 订单占用库存，处理完成: " + (System.currentTimeMillis() - start));
    }

    /**
     * 异常自动冻结库存入口
     */
    public void autoFrozenStockForException() {
        //JOB改成2个小时一次，在高压力下有可能对订单正常流程有影响。
        //1.查询待占用网单列表
        List<OrderProductsNew> unFrozenOpList = orderProductsNewService.getLockStockExceptionOpList(null);
        if (null == unFrozenOpList || unFrozenOpList.isEmpty()) {
            log.info("异常占用库存,没有需要处理的数据");
            return;
        } else {
            log.info(Thread.currentThread().getId() + "," + Thread.currentThread().getName()
                    + " - 异常订单占用库存，获取待处理数据完成，数量 " + unFrozenOpList.size());
        }

        long start = System.currentTimeMillis();
        long mainStart = start;
        execute(unFrozenOpList);
        log.info(Thread.currentThread().getId() + "," + Thread.currentThread().getName()
                + " - 异常订单占用库存，处理记录数: " + unFrozenOpList.size() + ","
                + (System.currentTimeMillis() - start));

        while (unFrozenOpList.size() == 1000) {
            start = System.currentTimeMillis();
            unFrozenOpList = orderProductsNewService
                    .getLockStockExceptionOpList(unFrozenOpList.get(unFrozenOpList.size() - 1).getId());
            if (null == unFrozenOpList || unFrozenOpList.isEmpty()) {
                break;
            }
            execute(unFrozenOpList);
            log.info(Thread.currentThread().getId() + "," + Thread.currentThread().getName()
                    + " - 异常订单占用库存，处理记录数: " + unFrozenOpList.size() + ","
                    + (System.currentTimeMillis() - start));
        }
        log.info(Thread.currentThread().getId() + "," + Thread.currentThread().getName()
                + " - 异常订单占用库存，处理完成: " + (System.currentTimeMillis() - mainStart));

    }

    public void execute(List<OrderProductsNew> unFrozenOpList) {
        //加入多线程执行
        ExecuteFrozenStock executeFrozenStock = new ExecuteFrozenStock();
        //分组大小100条(如果大于10小于等于100,分两组),加入多线程失败重试3次,如果失败则本线程自己执行
        int splitSize = 100;
        int size = unFrozenOpList.size();
        if (size > 10 && size <= splitSize) {
            splitSize = size / 2 + 1;
        }
        new MultiThreadTool<OrderProductsNew>().processJobs(executeFrozenStock, threadHelper, log,
                unFrozenOpList, splitSize, 3);
    }

    private void frozenStockByOrder(final List<OrderProductsNew> unFrozenOpList) {
        if (null == unFrozenOpList || unFrozenOpList.isEmpty()) {
            return;
        } else {
            log.info(Thread.currentThread().getId() + "," + Thread.currentThread().getName()
                    + " - 订单占用库存，开始处理，数量 " + unFrozenOpList.size());
        }
        //查询Region Map<id,active>
        final Map<Integer, Boolean> regionActiveMap = new HashMap<Integer, Boolean>();
        //查询订单 Map<id,Orders>
        final Map<Integer, OrdersNew> orderIdOrdersMap = new HashMap<Integer, OrdersNew>();
        preQueryRegionAndOrders(unFrozenOpList, regionActiveMap, orderIdOrdersMap);
        if (orderIdOrdersMap.isEmpty() || regionActiveMap.isEmpty()) {
            //        if (orderIdOrdersMap.isEmpty()) {
            return;
        }

        final Set<OrderProductsNew> frozenStockOrderProductSet = new HashSet<OrderProductsNew>();
        for (final OrderProductsNew orderProducts : unFrozenOpList) {
            try {
                transactionProxy.doProxy(new IProxy() {
                    @Override
                    public Object doBusiness() {
                        //虚拟商品直接关闭完成订单
                        ServiceResult<ProductsNew> result = itemService.getProductBySku(orderProducts.getSku());
                        if (null != result && result.getSuccess() && null != result.getResult()) {
                            if (result.getResult().getIsVirtual().intValue() == 1) {
                                handleVirtualProduct(orderProducts, orderIdOrdersMap);
                                return null;
                            }
                        }

                        OrdersNew orders = orderIdOrdersMap.get(orderProducts.getOrderId());
                        if (null == orders) {
                            return null;
                        }
                        //***********小家电不检查activeflag=1***********2018/8/2**********start****
                        boolean isB2C =  "B2C".equalsIgnoreCase(orderProducts.getShippingMode());

                        //校验省、市、区县信息是否完整,区县是否合法
                        boolean isContinue = checkCanConfirm(orders, regionActiveMap, isB2C);
                        //***********小家电不检查activeflag=1***********2018/8/2**********end******
                        if (!isContinue) {
                            shopOrderOperateLogsService.insert(constructOperateLog(orders, orderProducts,
                                    "系统", "占用库存", "省市区或者街道校验不通过，转人工处理", null));
                            return null;
                        }

                        //***********小家电的订单，externalorders的addtime在 2018-7-24 12：30：00之后的才能占用库存****start**
                        if(null != orderProducts.getShippingMode()&& "B2C".equalsIgnoreCase(orderProducts.getShippingMode())){
                            if (null != orders.getSourceOrderSn()){
                                ExternalOrders externalOrders = externalOrdersService.getExternalOrdersBySourceOrderSn(orders.getSourceOrderSn());
                                Date addDate = DateFormatUtil.parseByType(null,externalOrders.getAddTime());
                                Date jugeDate = DateFormatUtil.parseByType(null,"2018-7-24 12:30:00");
                                if (addDate.before(jugeDate)){
                                    return null;
                                }
                            }
                        }
                        //***********小家电的订单，externalorders的addtime在 2018-7-24 12：30：00之后的才能占用库存****end****

                        OrderProductsNew productsNew = orderProductsNewService.get(orderProducts.getId());
                        if (null != productsNew.getStatus() &&
                                productsNew.getStatus().intValue() == OrderProductsNew.STATUS_CANCEL_CLOSE.intValue()){
                            return  null;
                        }
                        //分配库位和占用库存
                        boolean frozenOk = frozenStock(orderProducts, orders,
                                frozenStockOrderProductSet);
                        if (frozenOk) {
                            //更新销量
                            try {
                                updateSaleNum(orderProducts);
                            } catch (Exception e) {
//                                log.error("更新销量异常（frozenStockByOrder），网单号("
//                                          + orderProducts.getCOrderSn() + "):" + e.getMessage());
                                throw new BusinessException(
                                        "更新销量异常（frozenStockByOrder），网单号(" + orderProducts.getCOrderSn()
                                                + "):" + e.getMessage());
                            }
                        }
                        return null;
                    }
                });
            } catch (Exception e) {
                log.error("占用库存,发生异常：", e);
                if (frozenStockOrderProductSet.isEmpty()) {
                    return;
                }
                //释放库存
                ServiceResult<Boolean> result = hopStockService.releaseFrozenStockQty(
                        orderProducts.getSku(), orderProducts.getNumber(), orderProducts.getCOrderSn(),
                        InventoryBusinessTypes.RELEASE_BY_ZBCC);
                log.info(
                        "释放库存成功:opid==" + orderProducts.getId() + ",sCode==" + orderProducts.getSCode()
                                + ",tsCode==" + orderProducts.getTsCode() + ",结果:" + result.getResult());
            } finally {
                frozenStockOrderProductSet.clear();
            }
        }
    }

    private void handleVirtualProduct(OrderProductsNew orderProducts,
                                      Map<Integer, OrdersNew> orderIdOrdersMap) {
        List<OrderProductsNew> orderProductsList = orderProductsNewService
                .getByOrderId(orderProducts.getOrderId());
        boolean isCompleteOrder = true;
        for (OrderProductsNew orderProduct : orderProductsList) {
            if (orderProduct.getId().intValue() == orderProducts.getId().intValue()) {
                continue;
            }
            if (orderProduct.getStatus().intValue() != OrderProductsNew.STATUS_CANCEL_CLOSE.intValue()
                    && orderProduct.getStatus().intValue() != OrderProductsNew.STATUS_COMPLETED_CLOSE
                    .intValue()) {
                isCompleteOrder = false;
                break;
            }
        }
        //2017-07-13 更新完成关闭时间
        Date now = new Date();
        if (isCompleteOrder) {
            //            ordersNewService.updateOrderStatus(orderProducts.getOrderId(),
            //                OrderStatus.OS_COMPLETE.getCode());
            ordersNewService.completeClose(orderProducts.getOrderId(), now.getTime() / 1000);
            shopOrderOperateLogsService
                    .insert(constructOperateLog(orderIdOrdersMap.get(orderProducts.getOrderId()),
                            orderProducts, "系统", "虚拟商品订单", "虚拟商品网单关闭引起订单关闭", null));
            log.info("虚拟商品网单关闭引起订单关闭,orderId:" + orderProducts.getOrderId());
        }
        //        orderProductsNewService.updateStatus(orderProducts.getId(), OrderProducts.STATUS_COMPLETED_CLOSE);
        orderProductsNewService.completeClose(orderProducts.getId(), now.getTime() / 1000);
        shopOrderOperateLogsService
                .insert(constructOperateLog(orderIdOrdersMap.get(orderProducts.getOrderId()),
                        orderProducts, "系统", "虚拟商品", "虚拟商品网单直接关闭", null));
        log.info("虚拟商品网单关闭,opId:" + orderProducts.getId());

        //更新全流程表的网单关闭时间
        OrderWorkflows orderWorkflows = shopOrderWorkflowsService
                .getByOrderProductId(orderProducts.getId());
        if (orderWorkflows != null) {
            shopOrderWorkflowsService.updateFinishTime(orderWorkflows.getId(), now.getTime() / 1000);
        }

        //如果商品类型是延保卡,插入延保卡表
        ServiceResult<ProductTypesNew> productTypeResult = itemService
                .getProductType(orderProducts.getProductType());
        if (null != productTypeResult && productTypeResult.getSuccess()) {
            if (null != productTypeResult.getResult()) {
                if ("延保卡".equals(productTypeResult.getResult().getTypeName())) {
                    OrderYBCards orderYBCards = new OrderYBCards();
                    orderYBCards
                            .setAddTime(Long.valueOf(System.currentTimeMillis() / 1000L).intValue());
                    orderYBCards.setStatus(OrderYBCards.STATUS_INIT);
                    orderYBCards.setcOrderSn(orderProducts.getCOrderSn());
                    orderYBCardsService.insert(orderYBCards);
                }
            }
        }

    }

    private void preQueryRegionAndOrders(List<OrderProductsNew> unFrozenOpList,
                                         Map<Integer, Boolean> regionActiveMap,
                                         Map<Integer, OrdersNew> orderIdOrdersMap) {
        Set<Integer> orderIdSet = new HashSet<Integer>();
        for (OrderProductsNew orderProducts : unFrozenOpList) {
            orderIdSet.add(orderProducts.getOrderId());
        }

        //根据订单id查询订单
        List<OrdersNew> ordersList = ordersNewService.getByIdsForConfirm(new ArrayList<Integer>(orderIdSet));
        if (null == ordersList || ordersList.isEmpty()) {
            return;
        }
        //2016-11-12 
        StringBuilder sb = new StringBuilder();
        for (OrdersNew orders : ordersList) {
            orderIdOrdersMap.put(Integer.valueOf(orders.getId()), orders);
            boolean judgeStreetOrder = OrderBizHelper.judgeStreetOrder(orders.getSource(),
                    orders.getAddTime());
            if (judgeStreetOrder) {
                sb.append(orders.getStreet());
                sb.append(",");
            } else {
                sb.append(orders.getRegion());
                sb.append(",");
            }
        }

        sb.deleteCharAt(sb.length() - 1);
        //查询Regions表
        List<Regions> regionsList = regionsService.getByIds(sb.toString());
        if (null == regionsList || regionsList.isEmpty()) {
            return;
        }
        for (Regions regions : regionsList) {
            regionActiveMap.put(regions.getId(),regions.getActiveFlag());
        }
    }

    private boolean checkCanConfirm(OrdersNew orders, Map<Integer, Boolean> regionActiveMap,Boolean isB2C) {
        if (orders == null) {
            return false;
        }
        boolean judgeStreetOrder = OrderBizHelper.judgeStreetOrder(orders.getSource(),
                orders.getAddTime());
        if (judgeStreetOrder) {
            //省市区街道信息是否完整,Street activeFlag=1否则转人工
            if (orders.getProvince().intValue() == 0 || orders.getCity().intValue() == 0
                    || orders.getRegion().intValue() == 0 || orders.getStreet().intValue() == 0
                    || !regionActiveMap.containsKey(orders.getStreet())
                    || (!regionActiveMap.get(orders.getStreet())&&!isB2C)) {
                orders.setSmConfirmStatus(3);//待人工确认
                //更新订单
                ordersNewService.updateSmConfirmStatus(orders);
                log.error("订单" + orders.getOrderSn() + "省市区街道信息不全或区街道不存在,需要人工确认");
                return false;
            }
        } else {
            //省、市、区县信息是否完整,Regions activeFlag=1否则转人工
            if (orders.getProvince().intValue() == 0 || orders.getCity().intValue() == 0
                    || orders.getRegion().intValue() == 0
                    || !regionActiveMap.containsKey(orders.getRegion())
                    || (!regionActiveMap.get(orders.getRegion())&&!isB2C)) {
                orders.setSmConfirmStatus(3);//待人工确认
                //更新订单
                ordersNewService.updateSmConfirmStatus(orders);
                log.error("订单" + orders.getOrderSn() + "省市区不全或区不存在,需要人工确认");
                return false;
            }
        }
        return true;
    }

    private boolean frozenStock(OrderProductsNew orderProducts, OrdersNew orders,
                                Set<OrderProductsNew> frozenStockOrderProductSet) {
        if (orderProducts == null || orders == null) {
            return false;
        }

        ServiceResult<String> result = stockCommonService
                .getChannelCodeByOrderSource(orders.getSource());
        if (!result.getSuccess()) {
            return false;
        }
        String channelCode = result.getResult();
        if (com.alibaba.dubbo.common.utils.StringUtils.isBlank(channelCode)) {
            log.error("订单:" + orders.getId() + "对应的来源:" + orders.getSource() + "找不到渠道");
            shopOrderOperateLogsService.insert(this.constructOperateLog(orders, orderProducts, "系统", "占用库存",
                    "占用库存时分配库位失败:来源:" + orders.getSource() + "找不到对应的渠道", null));
            return false;
        }
        //是否需要占库存
        boolean isNeedLockStock = true;
        //是否支持多层级
        boolean isNeedMultipleSecCode = true;
        //  只有日日单和样品机不可以使用多层级,商城或别的渠道库存判断是否支持多层级,不需要传参数
        if (orders.getIsProduceDaily().intValue() == 1 || "COS".equals(orders.getSource())
                || "DBJ".equals(orders.getSource())) {
            isNeedMultipleSecCode = false;
        }
        //日日单暂不占库存
        if (orders.getIsProduceDaily().intValue() == 1) {
            isNeedLockStock = false;
        }

        //分配库位
        Stock stock = null;
        if (com.alibaba.dubbo.common.utils.StringUtils.isBlank(orderProducts.getSCode())) {
            ServiceResult<Stock> stockResult = getScodeByStockService(orderProducts, orders,
                    channelCode, isNeedMultipleSecCode);
            if (!stockResult.getSuccess() || null == stockResult.getResult()) {
                handleNoSCode(orderProducts, orders, stockResult);
                return false;
            }
            stock = stockResult.getResult();

            //            if (isNeedLockStock) {//如果不是日日单则判断库存是否满足
            //                if (stock.getSecCode() == null
            //                    || orderProducts.getNumber().intValue() > stock.getAvaibleQty()) { //如果库存数量不满足
            //                    shopOrderOperateLogsService.insert(
            //                        OrderBizHelper.constructOperateLog(orders, orderProducts, "系统", "占用WA库存",
            //                            "未找到满足库存的库位，WA分配库位失败" + "，返回库存信息：" + JsonUtil.toJson(stock), null));
            //                    log.error(
            //                        "网单ID：" + orderProducts.getId() + "，占用WA库存时分配库位失败，未找到对应顺逛WA满足的库位 占用数量："
            //                              + orderProducts.getNumber() + " 实际库存数量：" + stock.getAvaibleQty()
            //                              + "，返回库存信息：" + JsonUtil.toJson(stock));
            //                    //更新网单库位和占用库存数量,状态
            //                    orderProducts.setLockedNumber(123456789);
            //                    orderProducts.setStatus(OrderProducts.STATUS_STRAT_STATUS);
            //                    orderProductsNewService.updateForFrozenStock(orderProducts);
            //                    return false;
            //                }
            //            }

            log.info("分配库位成功:opid==" + orderProducts.getId() + ",sCode==" + stock.getSecCode()
                    + ",tsCode==" + stock.getTsSecCode());
            //货到付款订单，分配到海朋库位不占库存，返回
            if (orders.getIsCod() == 1) {
                ServiceResult<InvSection> sectionResult = stockCommonService
                        .getSectionByCode(stock.getSecCode());
                if (sectionResult.getSuccess()) {
                    InvSection section = sectionResult.getResult();
                    if (section != null
                            && (InvSection.CHANNEL_CODE_HAIP.equals(section.getChannelCode())
                            || InvSection.CHANNEL_CODE_GD.equals(section.getChannelCode())
                            //2017-05-02 净水库存--------START
                            || InvSection.NEW_CHANNEL_CODE.contains(section.getChannelCode())
                            //2017-05-02 净水库存--------END
                    )) {
                        handleNoSCode(orderProducts, orders, stockResult);
                        return false;
                    }
                }
            }
        }

        //不需要占库存的不再冻结库存
        if (!isNeedLockStock) {
            if (com.alibaba.dubbo.common.utils.StringUtils.isBlank(orderProducts.getSCode())) {
                //更新网单库位和占用库存数量,状态
                orderProducts.setSCode(stock.getSecCode());
            }
            orderProducts.setStatus(OrderProductsNew.STATUS_FROZEN_STOCK);
            orderProductsNewService.updateForFrozenStock(orderProducts);
            return true;
        }

        //冻结库存
        ServiceResult<String> frozenResult = frozenStock(stock, orderProducts, orders, channelCode);
        if (!frozenResult.getSuccess()) {
            //更新网单库位和占用库存数量,状态
            return updateOrderProductsForFail(frozenResult, orderProducts, orders);
        } else {
            //下单付款成功释放下单锁
            sgStoreService.paymentSuccessReleaseOrderLock(orderProducts.getCOrderSn());
            return updateOrderProductsForSuccess(stock, frozenResult, orderProducts, orders,
                    frozenStockOrderProductSet);
        }
    }

    private ServiceResult<Stock> getScodeByStockService(OrderProductsNew orderProducts, OrdersNew orders,
                                                        String channelCode,
                                                        boolean isNeedMultipleSecCode) {
        //        ServiceResult<Stock> stockResult = hopStockService.getStockBySkuAndRegion(
        //            orderProducts.getSku(), orders.getRegion(), channelCode, orderProducts.getNumber(),
        //            isNeedMultipleSecCode);
        boolean judgeStreetOrder = OrderBizHelper.judgeStreetOrder(orders.getSource(),
                orders.getAddTime());
        ServiceResult<Stock> stockResult = hopStockService
                .getStockBySkuAndRegionWithOutLockForLevel(orderProducts.getSku(),
                        judgeStreetOrder ? orders.getStreet() : orders.getRegion(), channelCode,
                        orderProducts.getNumber(), isNeedMultipleSecCode, false, judgeStreetOrder ? 4 : 3);
        return stockResult;
    }

    private void handleNoSCode(OrderProductsNew orderProducts, OrdersNew orders,
                               ServiceResult<Stock> stockResult) {
        shopOrderOperateLogsService.insert(this.constructOperateLog(orders, orderProducts, "系统", "占用库存",
                "占用库存时分配库位失败,来源:" + orders.getSource(), null));
        log.info(orderProducts.getCOrderSn() + ",占用库存时分配库位失败:" + stockResult.getMessage() + ",来源:"
                + orders.getSource() + ",返回调用库存信息：" + JsonUtil.toJson(stockResult));
        //更新网单库位和占用库存数量,状态
        orderProducts.setLockedNumber(123456789);
        orderProducts.setStatus(OrderProductsNew.STATUS_STRAT_STATUS);
        orderProductsNewService.updateForFrozenStock(orderProducts);
    }

    private ServiceResult<String> frozenStock(Stock stock, OrderProductsNew orderProducts,
                                              OrdersNew orders, String channelCode) {
        ServiceResult<String> frozenResult = null;
        if ("COS".equals(orders.getSource())) {
            //冻结样品机
            frozenResult = hopStockService.frozeStockQtyByProperty(orderProducts.getSku(),
                    null != stock ? stock.getSecCode() : orderProducts.getSCode(), 40,
                    orderProducts.getNumber(), orderProducts.getCOrderSn(),
                    InventoryBusinessTypes.FROZEN_BY_ZBCC);
        } else if ("DBJ".equals(orders.getSource())) {
            //冻结夺宝机
            frozenResult = hopStockService.frozeStockQtyByProperty(orderProducts.getSku(),
                    null != stock ? stock.getSecCode() : orderProducts.getSCode(), 41,
                    orderProducts.getNumber(), orderProducts.getCOrderSn(),
                    InventoryBusinessTypes.FROZEN_BY_ZBCC);
        } else {
            //boolean useRRS = msLinkageService.getMsLinkage(orderProducts.getSku(), orders.getSource()) != null;
            frozenResult = hopStockService.frozeStockQty(orderProducts.getSku(),
                    null != stock ? stock.getSecCode() : orderProducts.getSCode(),
                    orderProducts.getNumber(), orderProducts.getCOrderSn(), channelCode,
                    InventoryBusinessTypes.FROZEN_BY_ZBCC, false);
        }
        return frozenResult;
    }

    private boolean updateOrderProductsForFail(ServiceResult<String> frozenResult,
                                               OrderProductsNew orderProducts, OrdersNew orders) {
        //更新网单库位和占用库存数量,状态
        orderProducts.setLockedNumber(123456789);
        orderProductsNewService.updateForFrozenStock(orderProducts);
        String message = frozenResult.getMessage();
        if (message.length() > 50) {
            message = message.substring(0, 50);
        }
        shopOrderOperateLogsService.insert(this.constructOperateLog(orders, orderProducts, "系统", "占用库存",
                "占用库存失败:" + message, null));
        log.info(orderProducts.getCOrderSn() + ",占用库存失败:" + message);
        return false;
    }

    private boolean updateOrderProductsForSuccess(Stock stock, ServiceResult<String> frozenResult,
                                                  OrderProductsNew orderProducts, OrdersNew orders,
                                                  Set<OrderProductsNew> frozenStockOrderProductSet) {
        //更新网单库位和占用库存数量,状态
        if (null != stock) {
            orderProducts.setSCode(stock.getSecCode());
            String tsCode = com.alibaba.dubbo.common.utils.StringUtils.isBlank(stock.getTsSecCode()) ? "" : stock.getTsSecCode();
            if (tsCode.equalsIgnoreCase(stock.getSecCode())) {
                tsCode = "";
            }
            orderProducts.setTsCode(tsCode);
            orderProducts.setTsShippingTime(
                    stock.getTsShippingTime() == null ? 0 : stock.getTsShippingTime());
        }
        orderProducts.setLockedNumber(orderProducts.getNumber());
        orderProducts.setStatus(OrderProductsNew.STATUS_FROZEN_STOCK);
        int updateNumber = orderProductsNewService.updateForFrozenStock(orderProducts);
        //并发或取消订单网单
        if (updateNumber < 1) {
            log.info("占用库存失败:opid==" + orderProducts.getId() + ",已经取消或出现并发");
            //释放库存
            //            ServiceResult<Boolean> releaseResult = hopStockService.releaseFrozenStockQty(
            //                orderProducts.getSku(), orderProducts.getNumber(), orderProducts.getCOrderSn(),
            //                InventoryBusinessTypes.RELEASE_BY_ZBCC);
            //            log.info("释放库存:opid==" + orderProducts.getId() + ",sCode==" + orderProducts.getSCode()
            //                     + ",tsCode==" + orderProducts.getTsCode() + ",结果:"
            //                     + releaseResult.getMessage());
            return false;
        }
        frozenStockOrderProductSet.add(orderProducts);
        shopOrderOperateLogsService
                .insert(this.constructOperateLog(orders, orderProducts, "系统", "占用库存", "占用库存成功", null));
        log.info("占用库存成功:opid==" + orderProducts.getId() + ",sCode==" + orderProducts.getSCode()
                + ",tsCode==" + orderProducts.getTsCode() + ",结果:" + frozenResult.getResult());
        //根据订单ID判断订单下所有网单是否全部占用库存成功
        int num = orderProductsNewService.getSuccessNum(Integer.valueOf(orders.getId()));
        //如果全部成功，则将订单状态由部分缺货更新为未确认
        if (num == 0) {
            ordersNewService.updateStatus(Integer.valueOf(orders.getId()));
        }
        return true;
    }

    private OrderOperateLogs constructOperateLog(OrdersNew orders, OrderProductsNew orderProducts,
                                                 String operator, String changeLog, String remark,
                                                 OrderOperateLogs log) {
        log = new OrderOperateLogs();
        log.setChangeLog(StringUtil.isEmpty(changeLog) ? "" : changeLog);
        log.setNetPointId(orderProducts.getNetPointId());
        log.setOperator(StringUtil.isEmpty(operator) ? "系统" : operator);
        log.setOrderId(orderProducts.getOrderId());
        log.setOrderProductId(orderProducts.getId());
        log.setPaymentStatus(null == orders ? 0 : orders.getPaymentStatus());
        log.setRemark(StringUtil.isEmpty(remark) ? "" : remark);
        log.setSiteId(1);
        log.setStatus(orderProducts.getStatus());
        return log;
    }

    private void updateSaleNum(OrderProductsNew orderProducts) throws Exception {
        ProductsNew products = new ProductsNew();
        products.setSku(orderProducts.getSku());
        products.setSaleNum(orderProducts.getNumber() == null ? 0 : orderProducts.getNumber());
        try {
            ServiceResult<Boolean> result = itemService.updateSaleNumBySku(products);
            if (result == null) {
                log.error("占用库存时，更新销量失败（itemService.updateSaleNumBySku）,返回对象为空！");
                throw new BusinessException("占用库存时，更新销量失败（itemService.updateSaleNumBySku）,返回对象为空！");
            }
            if (!(result.getResult() && result.getSuccess())) {
                log.error("占用库存时，更新销量未成功（itemService.updateSaleNumBySku）" + result.getMessage());
                throw new BusinessException(
                        "占用库存时，更新销量未成功（itemService.updateSaleNumBySku）" + result.getMessage());
            }
        } catch (Exception e) {
            log.error("占用库存时，更新销量错误（FrozenStockModel.updateSaleNum）" + e.getMessage());
            throw new BusinessException(
                    "占用库存时，更新销量错误（FrozenStockModel.updateSaleNum）" + e.getMessage());
        }
    }

    private class ExecuteFrozenStock implements IExcute {

        @SuppressWarnings("unchecked")
        @Override
        public void execute(Object obj) {
            ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
            try {
                List<OrderProductsNew> unFrozenOpList = (List<OrderProductsNew>) obj;
                if (null == unFrozenOpList || unFrozenOpList.isEmpty()) {
                    return;
                }
                frozenStockByOrder(unFrozenOpList);
            } finally {
                ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
            }
        }
    }

   /* public void setOrdersDao(OrdersNewDao ordersNewService) {
        this.ordersNewService = ordersNewService;
    }

    public void setHopStockService(HopStockServiceImpl hopStockService) {
        this.hopStockService = hopStockService;
    }

    public void setStockCommonService(StockCommonService stockCommonService) {
        this.stockCommonService = stockCommonService;
    }

    public void setOrderOperateLogsDao(OrderOperateLogsDao shopOrderOperateLogsService) {
        this.shopOrderOperateLogsService = shopOrderOperateLogsService;
    }
*/
   /* public void setOrderProductsDao(OrderProductsNewDao orderProductsNewService) {
        this.orderProductsNewService = orderProductsNewService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public void setThreadHelper(ThreadHelper threadHelper) {
        this.threadHelper = threadHelper;
    }

    public void setTransactionProxy(TransactionProxy transactionProxy) {
        this.transactionProxy = transactionProxy;
    }

    public void setRegionsDao(RegionsDao regionsService) {
        this.regionsService = regionsService;
    }

    public void setMsLinkageDao(MsLinkageDao msLinkageService) {
        this.msLinkageService = msLinkageService;
    }

    public void setOrderYBCardsDao(OrderYBCardsDao orderYBCardsService) {
        this.orderYBCardsService = orderYBCardsService;
    }

    public void setSgStoreService(SgStoreServiceImpl sgStoreService) {
        this.sgStoreService = sgStoreService;
    }

    public void setOrderWorkflowsDao(OrderWorkflowsDao shopOrderWorkflowsService) {
        this.shopOrderWorkflowsService = shopOrderWorkflowsService;
    }*/

    public void frozenStockByOrderBysCode(final List<OrderProductsNew> unFrozenOpList) {
        if (null == unFrozenOpList || unFrozenOpList.isEmpty()) {
            return;
        } else {
            log.info(Thread.currentThread().getId() + "," + Thread.currentThread().getName()
                    + " - 订单占用库存，开始处理，数量 " + unFrozenOpList.size());
        }
        //查询Region Map<id,active>
        final Map<Integer, Boolean> regionActiveMap = new HashMap<Integer, Boolean>();
        //查询订单 Map<id,Orders>
        final Map<Integer, OrdersNew> orderIdOrdersMap = new HashMap<Integer, OrdersNew>();
        preQueryRegionAndOrders(unFrozenOpList, regionActiveMap, orderIdOrdersMap);
        if (orderIdOrdersMap.isEmpty()){
            return;
        }

        final Set<OrderProductsNew> frozenStockOrderProductSet = new HashSet<OrderProductsNew>();
        for (final OrderProductsNew orderProducts : unFrozenOpList) {
            try {
                transactionProxy.doProxy(new IProxy() {
                    @Override
                    public Object doBusiness() {
                        //虚拟商品直接关闭完成订单
                        ServiceResult<ProductsNew> result = itemService.getProductBySku(orderProducts.getSku());
                        if (null != result && result.getSuccess() && null != result.getResult()) {
                            if (result.getResult().getIsVirtual().intValue() == 1) {
                                handleVirtualProduct(orderProducts, orderIdOrdersMap);
                                return null;
                            }
                        }

                        OrdersNew orders = orderIdOrdersMap.get(orderProducts.getOrderId());
                        if (null == orders) {
                            return null;
                        }
                        //***********小家电不检查activeflag=1***********2018/8/2**********start****
                        boolean isB2C =  "B2C".equalsIgnoreCase(orderProducts.getShippingMode());

                        //校验省、市、区县信息是否完整,区县是否合法
                        boolean isContinue = checkCanConfirm(orders, regionActiveMap, isB2C);
                        //***********小家电不检查activeflag=1***********2018/8/2**********end******
                        if (!isContinue) {
                            shopOrderOperateLogsService.insert(constructOperateLog(orders, orderProducts,
                                    "系统", "占用库存", "省市区或者街道校验不通过，转人工处理", null));
                            return null;
                        }

                        //***********小家电的订单，externalorders的addtime在 2018-7-24 12：30：00之后的才能占用库存****start**
                        if(null != orderProducts.getShippingMode()&& "B2C".equalsIgnoreCase(orderProducts.getShippingMode())){
                            if (null != orders.getSourceOrderSn()){
                                ExternalOrders externalOrders = externalOrdersService.getExternalOrdersBySourceOrderSn(orders.getSourceOrderSn());
                                Date addDate = DateFormatUtil.parseByType(null,externalOrders.getAddTime());
                                Date jugeDate = DateFormatUtil.parseByType(null,"2018-7-24 12:30:00");
                                if (addDate.before(jugeDate)){
                                    return null;
                                }
                            }
                        }
                        //***********小家电的订单，externalorders的addtime在 2018-7-24 12：30：00之后的才能占用库存****end****

                        OrderProductsNew productsNew = orderProductsNewService.get(orderProducts.getId());
                        if (null != productsNew.getStatus() &&
                                productsNew.getStatus().intValue() == OrderProductsNew.STATUS_CANCEL_CLOSE.intValue()){
                            return  null;
                        }
                        //分配库位和占用库存
                        boolean frozenOk = frozenStockByNewsCode(orderProducts, orders,
                                frozenStockOrderProductSet);
                        if (frozenOk) {
                            //更新销量
                            try {
                                updateSaleNum(orderProducts);
                            } catch (Exception e) {
//                                log.error("更新销量异常（frozenStockByOrder），网单号("
//                                          + orderProducts.getCOrderSn() + "):" + e.getMessage());
                                throw new BusinessException(
                                        "更新销量异常（frozenStockByOrder），网单号(" + orderProducts.getCOrderSn()
                                                + "):" + e.getMessage());
                            }
                        }
                        return null;
                    }
                });
            } catch (Exception e) {
                log.error("占用库存,发生异常：", e);
                if (frozenStockOrderProductSet.isEmpty()) {
                    return;
                }
                //释放库存
                ServiceResult<Boolean> result = hopStockService.releaseFrozenStockQty(
                        orderProducts.getSku(), orderProducts.getNumber(), orderProducts.getCOrderSn(),
                        InventoryBusinessTypes.RELEASE_BY_ZBCC);
                log.info(
                        "释放库存成功:opid==" + orderProducts.getId() + ",sCode==" + orderProducts.getSCode()
                                + ",tsCode==" + orderProducts.getTsCode() + ",结果:" + result.getResult());
            } finally {
                frozenStockOrderProductSet.clear();
            }
        }
    }

    private boolean frozenStockByNewsCode(OrderProductsNew orderProducts, OrdersNew orders,
                                          Set<OrderProductsNew> frozenStockOrderProductSet) {
        if (orderProducts == null || orders == null) {
            return false;
        }

        ServiceResult<String> result = stockCommonService
                .getChannelCodeByOrderSource(orders.getSource());
        if (!result.getSuccess()) {
            return false;
        }
        String channelCode = result.getResult();
        if (com.alibaba.dubbo.common.utils.StringUtils.isBlank(channelCode)) {
            log.error("订单:" + orders.getId() + "对应的来源:" + orders.getSource() + "找不到渠道");
            shopOrderOperateLogsService.insert(this.constructOperateLog(orders, orderProducts, "系统", "占用库存",
                    "占用库存时分配库位失败:来源:" + orders.getSource() + "找不到对应的渠道", null));
            return false;
        }
        //是否需要占库存
        boolean isNeedLockStock = true;
        //是否支持多层级
        boolean isNeedMultipleSecCode = true;
        //  只有日日单和样品机不可以使用多层级,商城或别的渠道库存判断是否支持多层级,不需要传参数
        if (orders.getIsProduceDaily().intValue() == 1 || "COS".equals(orders.getSource())
                || "DBJ".equals(orders.getSource())) {
            isNeedMultipleSecCode = false;
        }
        //日日单暂不占库存
        if (orders.getIsProduceDaily().intValue() == 1) {
            isNeedLockStock = false;
        }

        //分配库位
        Stock stock = null;
        if (com.alibaba.dubbo.common.utils.StringUtils.isBlank(orderProducts.getSCode())) {
            ServiceResult<Stock> stockResult = getScodeByStockService(orderProducts, orders,
                    channelCode, isNeedMultipleSecCode);
            if (!stockResult.getSuccess() || null == stockResult.getResult()) {
                handleNoSCode(orderProducts, orders, stockResult);
                return false;
            }
            stock = stockResult.getResult();

            //            if (isNeedLockStock) {//如果不是日日单则判断库存是否满足
            //                if (stock.getSecCode() == null
            //                    || orderProducts.getNumber().intValue() > stock.getAvaibleQty()) { //如果库存数量不满足
            //            }

            log.info("分配库位成功:opid==" + orderProducts.getId() + ",sCode==" + stock.getSecCode()
                    + ",tsCode==" + stock.getTsSecCode());
            //货到付款订单，分配到海朋库位不占库存，返回
            if (orders.getIsCod() == 1) {
                ServiceResult<InvSection> sectionResult = stockCommonService
                        .getSectionByCode(stock.getSecCode());
                if (sectionResult.getSuccess()) {
                    InvSection section = sectionResult.getResult();
                    if (section != null
                            && (InvSection.CHANNEL_CODE_HAIP.equals(section.getChannelCode())
                            || InvSection.CHANNEL_CODE_GD.equals(section.getChannelCode())
                            //2017-05-02 净水库存--------START
                            || InvSection.NEW_CHANNEL_CODE.contains(section.getChannelCode())
                            //2017-05-02 净水库存--------END
                    )) {
                        handleNoSCode(orderProducts, orders, stockResult);
                        return false;
                    }
                }
            }
        }

        //不需要占库存的不再冻结库存
        if (!isNeedLockStock) {
            if (com.alibaba.dubbo.common.utils.StringUtils.isBlank(orderProducts.getSCode())) {
                //更新网单库位和占用库存数量,状态
                orderProducts.setSCode(stock.getSecCode());
            }
            orderProducts.setStatus(OrderProductsNew.STATUS_FROZEN_STOCK);
            orderProductsNewService.updateForFrozenStock(orderProducts);
            return true;
        }

        //冻结库存
        ServiceResult<String> frozenResult = frozenStockByNewsCode(stock, orderProducts, orders, channelCode);
        if (!frozenResult.getSuccess()) {
            //更新网单库位和占用库存数量,状态
            return updateOrderProductsForFail(frozenResult, orderProducts, orders);
        } else {
            //下单付款成功释放下单锁
            sgStoreService.paymentSuccessReleaseOrderLock(orderProducts.getCOrderSn());
            return updateOrderProductsForSuccess(stock, frozenResult, orderProducts, orders,
                    frozenStockOrderProductSet);
        }
    }

    private ServiceResult<String> frozenStockByNewsCode(Stock stock, OrderProductsNew orderProducts,
                                                        OrdersNew orders, String channelCode) {
        ServiceResult<String> frozenResult = null;
        if ("COS".equals(orders.getSource())) {
            //冻结样品机
            frozenResult = hopStockService.frozeStockQtyByProperty(orderProducts.getSku(),
                    null != stock ? stock.getSecCode() : orderProducts.getSCode(), 40,
                    orderProducts.getNumber(), orderProducts.getCOrderSn(),
                    InventoryBusinessTypes.FROZEN_BY_ZBCC);
        } else if ("DBJ".equals(orders.getSource())) {
            //冻结夺宝机
            frozenResult = hopStockService.frozeStockQtyByProperty(orderProducts.getSku(),
                    null != stock ? stock.getSecCode() : orderProducts.getSCode(), 41,
                    orderProducts.getNumber(), orderProducts.getCOrderSn(),
                    InventoryBusinessTypes.FROZEN_BY_ZBCC);
        } else {
            //boolean useRRS = msLinkageService.getMsLinkage(orderProducts.getSku(), orders.getSource()) != null;
            frozenResult = hopStockService.frozeStockQtyByNewsCode(orderProducts.getSku(),
                    null != stock ? stock.getSecCode() : orderProducts.getSCode(),
                    orderProducts.getNumber(), orderProducts.getCOrderSn(), channelCode,
                    InventoryBusinessTypes.FROZEN_BY_ZBCC, false);
        }
        return frozenResult;
    }

}