package com.haier.order.model;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.StringUtil;
import com.haier.order.dateSource.ReadWriteRoutingDataSourceHolder;
import com.haier.order.multithread.IExcute;
import com.haier.order.multithread.MultiThreadTool;
import com.haier.order.multithread.ThreadHelper;
import com.haier.order.services.OrderCenterItemServiceImplByHwl;
import com.haier.order.services.OrderCenterOrderBizHelper;
import com.haier.order.services.OrderCenterStockCommonServiceImpl;
import com.haier.order.util.IProxy;
import com.haier.order.util.SmsTemplateConst;
import com.haier.order.util.SpringContextUtil;
import com.haier.order.util.TransactionProxy;
import com.haier.shop.model.*;
import com.haier.shop.service.*;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.OrderMsgQueue;
import com.haier.stock.service.OrderMsgQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.util.*;

@Service("confirmOrderModel")
public class ConfirmOrderModel {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(ConfirmOrderModel.class);

    @Autowired
    private OrdersNewService ordersNewService;
    @Autowired
    private ShopOrderWorkflowsService orderWorkflowsService;

    @Autowired
    private ShopMembersService membersService;

    @Autowired
    private OrderCenterItemServiceImplByHwl itemService;

    @Autowired
    private OrderProductsNewService orderProductsNewService;
    @Autowired
    private MemberInvoicesService memberInvoicesService;
    @Autowired
    private HPQueuesService hpQueuesService;
    @Autowired
    private ShopOrderOperateLogsService orderOperateLogsService;
    @Autowired
    private OrderCenterStockCommonServiceImpl orderThirdCenterStockCommonService;
    @Autowired
    private SmsLogsWriteService smsLogsWriteService;
    @Autowired
    private CountTimePubModel countTimePubModel;
    @Autowired
    private LesQueuesService lesQueuesService;
    @Autowired
    private EcQueuesService ecQueuesService;
    @Autowired
    private AccountCenterService accountCenterService;

    @Autowired
    private OrderPriceSourceChannelService orderPriceSourceChannelService;

    @Autowired
    private OrderGuaranteePriceInfoService orderGuaranteePriceInfoService;
    @Autowired
    private OrderPriceGateService orderPriceGateService;

    @Autowired
    private OrderCouponsService orderCouponsService;

    /*@Autowired
    private QueueMailsWriteService queueMailsWriteService;*/
    @Autowired
    private CostPoolsService costPoolsService;

    @Autowired
    private ChangeOrderConfigService changeOrderConfigService;

    @Autowired
    private OrderProductsAttributesService orderProductsAttributesService;

    @Autowired
    private OrderPriceCostPoolUseInfoService orderPriceCostPoolUseInfoService;

    @Autowired
    private OrderPriceProductGroupIndustryService orderPriceProductGroupIndustryService;

    @Autowired
    private OrderPriceSourceIndustryService orderPriceSourceIndustryService;

    @Autowired
    private ItemBaseNewService itemBaseNewService;

    @Autowired
    private HpNoticeQueuesService hpNoticeQueuesService;
//    @Autowired
//    private DataSourceTransactionManager transactionManager;
    @Autowired
    private OrderMsgQueueService orderMsgQueueService;

    //保本价渠道配置，和库存的渠道不一致，新的配置关系
    private final static Map<String, String> CHANNELMAP = new HashMap<String, String>();

    static {
        CHANNELMAP.put("SC", "商城");
        CHANNELMAP.put("TB", "天猫");
        CHANNELMAP.put("DSPT", "电商平台");
        CHANNELMAP.put("SCFX", "商城分销");
        CHANNELMAP.put("TMFX", "天猫分销");
        CHANNELMAP.put("SG", "顺逛");
    }

    private final static int GUARANTEEPRICE_TYPE = 1; //保本价类型
    private final static int COUPONCOSTPOOL_TYPE = 2; //费用池优惠券类型

    /**
     * 自动确认订单入口
     */
    public void autoConfirmOrder() {
        long startTime = System.currentTimeMillis();
        //查询冻结库存的网单对应订单
        List<OrdersNew> orderList = ordersNewService.getNotAutoConfirmOrders();
        if (null == orderList || orderList.isEmpty()) {
            log.info("确认订单,没有需要处理的数据");
            return;
        }
        execute(orderList);
        long endTime = System.currentTimeMillis();
        long time = endTime - startTime;
        log.info("订单确认条数:" + orderList.size() + ",总共执行时间:" + time + "毫秒,平均每条"
                + time / orderList.size() + "毫秒");
    }

    private void execute(List<OrdersNew> orderList) {
        ThreadHelper threadHelper = (ThreadHelper) SpringContextUtil.getBean("threadHelper");
        //加入多线程执行
        ExecuteConfirmStock executeConfirmStock = new ExecuteConfirmStock();
        //分组大小100条(如果大于10小于等于100,分两组),加入多线程失败重试3次,如果失败则本线程自己执行
        int splitSize = 100;
        int size = orderList.size();
        if (size > 10 && size <= 100) {
            splitSize = size / 2 + 1;
        }
        new MultiThreadTool<OrdersNew>().processJobs(executeConfirmStock, threadHelper, log, orderList,
                splitSize, 3);
    }

    private void confirmOrders(final List<OrdersNew> orderList, final String user) {
        //根据订单id数组查询网单列表
        final Map<Integer, List<OrderProductsNew>> orderIdOrderProductsMap = this
                .getOrderProducts(orderList);
        if (null == orderIdOrderProductsMap || orderIdOrderProductsMap.isEmpty()) {
            return;
        }
        //获取订单价格管控来源渠道对应表
        List<OrderPriceSourceChannel> guaranteePriceChannelSourceList = orderPriceSourceChannelService
                .getOrderPriceSourceChannelList();
        //渠道、订单来源对应关系Map,key=订单来源,value=渠道,保本价闸口用
        final Map<String, String> guaranteePriceChannelSourceMap = getGuaranteePriceChannelSourceList(
                guaranteePriceChannelSourceList, GUARANTEEPRICE_TYPE);
        //短信邮件Map,key=渠道,value=手机和邮件的Map,保本价闸口用
        final Map<String, Map<String, Set<String>>> guaranteePriceMsgMap = getGuaranteePriceMsgList(
                guaranteePriceChannelSourceList, GUARANTEEPRICE_TYPE);
        //渠道、订单来源对应关系Map,key=订单来源,value=渠道,费用池优惠券闸口用
        final Map<String, String> couponCostPoolChannelSourceMap = getGuaranteePriceChannelSourceList(
                guaranteePriceChannelSourceList, COUPONCOSTPOOL_TYPE);
        //短信邮件Map,key=渠道,value=手机和邮件的Map,费用池优惠券闸口用
        final Map<String, Map<String, Set<String>>> couponCostPoolMsgMap = getGuaranteePriceMsgList(
                guaranteePriceChannelSourceList, COUPONCOSTPOOL_TYPE);
        final int yearMonth = Integer.parseInt(DateUtil.format(new Date(), "yyyyMM"));
        final Map<String, Map<String, Object>> productMap = new HashMap<String, Map<String, Object>>();
        final Map<String, String> brandMap = new HashMap<String, String>();

        //获取订单价格管控来源产业对应表
        List<OrderPriceSourceIndustry> sourceIndustryList = orderPriceSourceIndustryService
                .getSourceIndustryList();
        //获取订单价格管控产品组产业对应表
        List<OrderPriceProductGroupIndustry> groupIndustryList = orderPriceProductGroupIndustryService
                .getProductGroupIndustryList();
        //产业、订单来源对应关系Map,key=订单来源,value=Set<产业>,费用池优惠券闸口用
        final Map<String, Set<String>> sourceIndustryMap = getSourceIndustryMap(sourceIndustryList);
        //产业、产品组对应关系Map,key=产品组,value=产业,费用池优惠券闸口用
        final Map<String, String> groupIndustryMap = getGroupIndustryMap(groupIndustryList);
        TransactionProxy transactionProxy = (TransactionProxy) SpringContextUtil.getBean("transactionProxy");

        for (final OrdersNew orders : orderList) {
            try {
                transactionProxy.doProxy(new IProxy() {
                    @Override
                    public Object doBusiness() {
                        List<OrderProductsNew> orderProductsList = orderIdOrderProductsMap
                                .get(Integer.parseInt(orders.getId()));
                        if (null == orderProductsList || orderProductsList.isEmpty()) {
                            return null;
                        }
                        //1.过滤网单列表中筛选WA、EC、O2O网单
                        List<OrderProductsNew> subFilterWAOrderProductsList = new ArrayList<OrderProductsNew>();
                        List<OrderProductsNew> subStoreOrderProductsList = new ArrayList<OrderProductsNew>();
                        List<OrderProductsNew> subO2OOrderProductsList = new ArrayList<OrderProductsNew>();
                        for (OrderProductsNew orderProducts : orderProductsList) {
                            if (OrderProductsNew.TYPE_WA
                                    .equalsIgnoreCase(orderProducts.getStockType())) {
                                subFilterWAOrderProductsList.add(orderProducts);
                            } else if (OrderProductsNew.TYPE_STORE
                                    .equalsIgnoreCase(orderProducts.getStockType())) {
                                subStoreOrderProductsList.add(orderProducts);
                            } else if (OrderProductsNew.TYPE_O2O
                                    .equalsIgnoreCase(orderProducts.getStockType())) {
                                //O2O占用库存则发短信
                                if (OrderProductsNew.STATUS_FROZEN_STOCK
                                        .equals(orderProducts.getStatus())) {
                                    subO2OOrderProductsList.add(orderProducts);
                                }
                            }
                        }
                        if (!subO2OOrderProductsList.isEmpty()) {
                            //O2O网单 发短信
                            sendSmsToStore(orders, subO2OOrderProductsList);
                        }

                        //订单保本价逻辑
                        if (!guaranteePriceGate(guaranteePriceChannelSourceMap,
                                guaranteePriceMsgMap, orders, subFilterWAOrderProductsList, user)) {
                            return null;
                        }
                        //订单费用池优惠券逻辑
                        if (!couponCostPoolGate(couponCostPoolChannelSourceMap,
                                couponCostPoolMsgMap, sourceIndustryMap, groupIndustryMap, orders,
                                subFilterWAOrderProductsList, productMap, brandMap, user, yearMonth)) {
                            return null;
                        }

                        //2.检查大家电类别是否全部占有库存，如果有一个未占有就去掉所有大家电，小家电只要占用库存的网单就加入队列
                        List<OrderProductsNew> subWAOrderProductsList = splitOrderProductList(
                                subFilterWAOrderProductsList);

                        Integer confirmTime = orders.getConfirmTime();
                        //3.确认订单
                        //如果订单状态已经是部分缺货,要更新的订单状态(部分缺货)未改变,直接返回
                        doConfirm(orders, user, orderProductsList);

                        //判断是否是自营转单,得到自营的网单id集合
                        Map<Integer, Integer> productsSelfMap = selfSellOrders(orders,
                                orderProductsList);

                        //判断是否商城分销,订单来源为SCFX,库存类型WA,获取网单id集合
                        Set<Integer> scfXorderProductId = getSCFXorderProductId(orders,
                                orderProductsList);

                        //首次确认订单(包括首次设置部分缺货)，订单下所有O2O网单插入到结算中心正向队列表，缺货网单也要插入
                        if ((confirmTime == null || confirmTime.equals(0))
                                && orders.getConfirmTime().intValue() > 0) {
                            List<OrderProductsNew> orderProductsListAccount = orderProductsNewService
                                    .getByOrderId(Integer.valueOf(orders.getId()));
                            insertO2OOrderConfirmQueues(orderProductsListAccount, productsSelfMap,
                                    scfXorderProductId);
                        }

                        //4.ec插入队列
                        if (!subStoreOrderProductsList.isEmpty()) {
                            //4.1排除没有占用库存的网单
                            for (Iterator<OrderProductsNew> it = subStoreOrderProductsList
                                    .iterator(); it.hasNext(); ) {
                                OrderProductsNew storeOp = it.next();
                                if (!OrderProductsNew.STATUS_FROZEN_STOCK
                                        .equals(storeOp.getStatus())) {
                                    it.remove();
                                }
                            }
                            //4.2插入队列
                            OrderCenterOrderBizHelper.saveEcQueues(subStoreOrderProductsList, ecQueuesService);
                            if (!subStoreOrderProductsList.isEmpty()) {
                                //发送APP消息
                                sendSmsToStore(orders, subStoreOrderProductsList);
                                //4.3记录订单操作日志表
                                insertOperateLog(orders, user, subStoreOrderProductsList);
                            }
                        }

                        //5.WA-如果大小家电列表都不为空
                        if (!subWAOrderProductsList.isEmpty()) {
                            //加入HP或LES队列
                            addHPOrLESQueues(orders, subWAOrderProductsList);

                            //把HpNoticeQueues推送次数大于等于30的定金尾款订单推送次数更新为0
                            updateHpNoticeQueues(orders, subWAOrderProductsList);

                            //如果没有任何记录插入HP和LES队列，返回null，返回的列表内容就是刚插入的记录
                            if (!subWAOrderProductsList.isEmpty()) {
                                //6.记录订单操作日志表
                                insertOperateLog(orders, user, subWAOrderProductsList);

                                //7.更新配送时效
                                for (OrderProductsNew op : subWAOrderProductsList) {
                                    countTimePubModel.setOrderWorkFlowShippingTime(orders, op);
                                }

                                //8.分配开票类型
                                addInvoiceType(orders, subWAOrderProductsList);

                                //9.发短信
                                //查询会员信息 方便发短信用
                                Members members = membersService.get(orders.getMemberId());
                                sendSms(orders, subWAOrderProductsList, members);

                                //10.天猫渠道确认订单后写入待同步快捷通队列 add 2014-10-29
                                insertKjtProofs(orders);

                                //11.设置超时免单
                                setIsTimeOutFreeInfo(orders, subWAOrderProductsList);
                            }
                        }

                        //6.顺逛自有库存单子
                        //过滤网单列表中筛选O2O网单，如果不是就移除
                        List<OrderProductsNew> sgO2OOrderProductsList = OrderCenterOrderBizHelper
                                .filterOrderProductList(orderProductsList, "O2O");
                        if (!sgO2OOrderProductsList.isEmpty()) {
                            //6.1排除没有占用库存的网单
                            for (Iterator<OrderProductsNew> it = sgO2OOrderProductsList.iterator(); it
                                    .hasNext(); ) {
                                OrderProductsNew storeOp = it.next();
                                if (!OrderProductsNew.STATUS_FROZEN_STOCK
                                        .equals(storeOp.getStatus())) {
                                    it.remove();
                                } else {
                                    //占用库存成功的改成待发货
                                    orderProductsNewService.updateStatus(storeOp.getId(),
                                            OrderProductsNew.STATUS_WAIT_DELIVERY);
                                }
                            }
                            if (!sgO2OOrderProductsList.isEmpty()) {
                                //6.2记录订单操作日志表
                                insertOperateLog(orders, user, sgO2OOrderProductsList);

                            }
                        }

                        return null;
                    }
                });
            } catch (Exception e) {
                log.error("自动确认订单,订单号:" + orders.getOrderSn() + ",发生异常：", e);
            }
        }
    }

    private Map<String, String> getGuaranteePriceChannelSourceList(List<OrderPriceSourceChannel> channelSourceList,
                                                                   int type) {
        if (channelSourceList == null || channelSourceList.size() == 0) {
            return null;
        }
        //key=订单来源,value=渠道
        Map<String, String> channelSourceMap = new HashMap<String, String>();
        for (OrderPriceSourceChannel channelSource : channelSourceList) {
            if (channelSource.getGateType().intValue() == type) {
                channelSourceMap.put(channelSource.getOrderSource(),
                        channelSource.getChannelCode());
            }
        }
        return channelSourceMap;
    }

    private Map<String, Map<String, Set<String>>> getGuaranteePriceMsgList(List<OrderPriceSourceChannel> channelSourceList,
                                                                           int type) {
        if (channelSourceList == null || channelSourceList.size() == 0) {
            return null;
        }

        //key=渠道,value=手机和邮件的Map,保本价闸口用
        Map<String, Map<String, Set<String>>> msgMap = new HashMap<String, Map<String, Set<String>>>();
        //key=渠道,value=手机Set
        Map<String, Set<String>> mobileMap = new HashMap<String, Set<String>>();
        //key=渠道,value=邮件Set
        Map<String, Set<String>> emailMap = new HashMap<String, Set<String>>();

        for (OrderPriceSourceChannel channelSource : channelSourceList) {
            if (channelSource.getGateType().intValue() == type) {
                int sendType = channelSource.getSendType().intValue();
                String channel = channelSource.getChannelCode();
                //短信
                if (sendType == 1) {
                    Set<String> set = null;
                    if (!mobileMap.containsKey(channel)) {
                        set = new HashSet<String>();
                        mobileMap.put(channel, set);
                    } else {
                        set = mobileMap.get(channel);
                    }
                    set.addAll(Arrays.asList(channelSource.getMobile().split(",")));
                }
                //邮件
                if (sendType == 2) {
                    Set<String> set = null;
                    if (!emailMap.containsKey(channel)) {
                        set = new HashSet<String>();
                        emailMap.put(channel, set);
                    } else {
                        set = emailMap.get(channel);
                    }
                    set.addAll(Arrays.asList(channelSource.getEmail().split(",")));
                }
                //短信和邮件
                if (sendType == 3) {
                    Set<String> set = null;
                    if (!mobileMap.containsKey(channel)) {
                        set = new HashSet<String>();
                        mobileMap.put(channel, set);
                    } else {
                        set = mobileMap.get(channel);
                    }
                    set.addAll(Arrays.asList(channelSource.getMobile().split(",")));

                    Set<String> set2 = null;
                    if (!emailMap.containsKey(channel)) {
                        set2 = new HashSet<String>();
                        emailMap.put(channel, set2);
                    } else {
                        set2 = emailMap.get(channel);
                    }
                    set2.addAll(Arrays.asList(channelSource.getEmail().split(",")));
                }
                Map<String, Set<String>> map = null;
                if (!msgMap.containsKey(channel)) {
                    map = new HashMap<String, Set<String>>();
                    msgMap.put(channel, map);
                } else {
                    map = msgMap.get(channel);
                }
                map.put("mobile", mobileMap.get(channel));
                map.put("email", emailMap.get(channel));
            }
        }

        return msgMap;
    }

    private Map<String, Set<String>> getSourceIndustryMap(List<OrderPriceSourceIndustry> sourceIndustryList) {
        if (sourceIndustryList == null || sourceIndustryList.size() == 0) {
            return null;
        }
        //key=订单来源,value=Set<产业>
        Map<String, Set<String>> sourceIndustryMap = new HashMap<String, Set<String>>();
        for (OrderPriceSourceIndustry temp : sourceIndustryList) {
            Set<String> set = null;
            if (!sourceIndustryMap.containsKey(temp.getOrderSource())) {
                set = new HashSet<String>();
                sourceIndustryMap.put(temp.getOrderSource(), set);
            } else {
                set = sourceIndustryMap.get(temp.getOrderSource());
            }
            set.add(temp.getIndustryCode());
        }
        return sourceIndustryMap;
    }

    private Map<String, String> getGroupIndustryMap(List<OrderPriceProductGroupIndustry> groupIndustryList) {
        if (groupIndustryList == null || groupIndustryList.size() == 0) {
            return null;
        }
        //key=产品组,value=产业
        Map<String, String> groupIndustryMap = new HashMap<String, String>();
        for (OrderPriceProductGroupIndustry temp : groupIndustryList) {
            groupIndustryMap.put(temp.getProductGroup(), temp.getIndustryCode());
        }
        return groupIndustryMap;
    }

    /**
     * 获取商品类型
     */
    private Map<String, Map<String, Object>> getProductCate() {
        Map<String, Map<String, Object>> productMap = new HashMap<String, Map<String, Object>>();
        List<Map<String, Object>> list = costPoolsService.getProductCate();
        if (list == null || list.isEmpty()) {
            return productMap;
        }

        for (Map<String, Object> map : list) {
            productMap.put(String.valueOf(map.get("id")), map);
        }
        return productMap;
    }

    /**
     * 获取顶级品类
     */
    private Map<String, Object> getSuperCateMap(String id, Map<String, Map<String, Object>> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> parentMap = map.get(id);
        if (parentMap == null) {
            return null;
        }
        if (Integer.parseInt(parentMap.get("parentId").toString()) == 0) {
            return parentMap;
        } else {
            return getSuperCateMap(parentMap.get("parentId").toString(), map);
        }
    }

    /**
     * 获取品牌
     */
    private Map<String, String> getBrand() {
        Map<String, String> productMap = new HashMap<String, String>();
        List<Map<String, Object>> list = costPoolsService.getBrand();
        if (list == null || list.isEmpty()) {
            return productMap;
        }

        for (Map<String, Object> map : list) {
            productMap.put(map.get("id").toString(), map.get("brandName").toString());
        }
        return productMap;
    }

    private boolean guaranteePriceGate(Map<String, String> guaranteePriceChannelSourceMap,
                                       Map<String, Map<String, Set<String>>> guaranteePriceMsgMap,
                                       OrdersNew orders,
                                       List<OrderProductsNew> subFilterWAOrderProductsList,
                                       String user) {
        //渠道与订单来源对照关系中订单来源是开启闸口，订单类型为普通订单，库存为WA
        //定金尾款取价格时要注意，会有问题，尾款金额取不到
        if (guaranteePriceChannelSourceMap != null && !guaranteePriceChannelSourceMap.isEmpty()
                && guaranteePriceChannelSourceMap.containsKey(orders.getSource())
                && orders.getOrderType().intValue() == OrderType.TYPE_NORMAL.getIntValue()) {
            OrderOperateLogs log = null;
            List<OrderOperateLogs> orderOperateLogsList = new ArrayList<OrderOperateLogs>();
            List<OrderPriceGate> orderPriceGateLockList = new ArrayList<OrderPriceGate>();
            String lockReason;
            //            boolean existPriceInfo;
            //循环库存为WA的网单
            for (OrderProductsNew op : subFilterWAOrderProductsList) {
                //没占库存的不闸
                if (op.getStatus().intValue() != OrderProductsNew.STATUS_FROZEN_STOCK.intValue()) {
                    continue;
                }
                lockReason = "网单销售价低于保本价，闸住，请联系专员放行订单！";
                //                existPriceInfo = true;
                //闸口表锁定信息
                OrderPriceGate orderPriceGate = orderPriceGateService
                        .getOrderPriceGatebyCOrderSn(op.getCOrderSn(), GUARANTEEPRICE_TYPE);
                //已存在的不再闸住
                if (orderPriceGate != null) {
                    continue;
                }

                //CBS闸口价处判断：订单SKU为赠品机SKU,订单金额为0.01元；订单为后台录单,不闸
                ServiceResult<ProductsNew> productResult = itemService
                        .getProductById(op.getProductId());
                if (productResult.getSuccess() && null != productResult.getResult()) {
                    if (productResult.getResult().getIsGift().intValue() == 1
                            && new BigDecimal("0.01").compareTo(op.getProductAmount()) == 0
                            && orders.getIsBackend().intValue() == 1) {
                        continue;
                    }
                }

                String shippingMode = StringUtil.isEmpty(op.getShippingMode(), true) ? "B2B2C"
                        : op.getShippingMode().trim();
                //保本价信息
                //                OrderGuaranteePriceInfo priceInfo = orderGuaranteePriceInfoDao.getConditions(
                //                    guaranteePriceChannelSourceMap.get(orders.getSource()), shippingMode,
                //                    op.getSku());
                List<OrderGuaranteePriceInfo> tempPriceInfoList = orderGuaranteePriceInfoService
                        .getNewConditions(guaranteePriceChannelSourceMap.get(orders.getSource()),
                                shippingMode, op.getSku());
                if (tempPriceInfoList == null || tempPriceInfoList.size() == 0) {
                    continue;
                }
                OrderGuaranteePriceInfo priceInfo = null;
                //有活动时间，取下单时间在活动时间内，最新的那条数据。没有活动，用保本价，取创建时间小于下单时间的最大的那条数据
                for (OrderGuaranteePriceInfo info : tempPriceInfoList) {
                    if (orders.getAddTime().intValue() >= info.getActivityGuaranteePriceStartTime()
                            .intValue()
                            && orders.getAddTime().intValue() <= info.getActivityGuaranteePriceEndTime()
                            .intValue()) {
                        priceInfo = info;
                        break;
                    }
                }
                if (priceInfo == null) {
                    for (OrderGuaranteePriceInfo info : tempPriceInfoList) {
                        if (DateUtil.parse(info.getCreateTime(), "yyyy-MM-dd HH:mm:ss").getTime()
                                / 1000 <= orders.getAddTime().intValue()) {
                            priceInfo = info;
                            break;
                        }
                    }
                }
                //                //如果没有维护保本价信息，闸住
                //                if (priceInfo == null) {
                //                    existPriceInfo = false;
                //                    priceInfo = new OrderGuaranteePriceInfo();
                //                    priceInfo.setGuaranteePrice(BigDecimal.ZERO);
                //                    String cateName = orderPriceGateService.getCateNameByCateId(op.getCateId());
                //                    priceInfo.setCateName(StringUtil.isEmpty(cateName) ? "" : cateName);
                //                    String brandName = orderPriceGateService.getBrandNameByBrandId(op.getBrandId());
                //                    priceInfo.setBrandName(StringUtil.isEmpty(brandName) ? "" : brandName);
                //                    lockReason = "渠道：" + guaranteePriceChannelSourceMap.get(orders.getSource())
                //                                 + "，品类：" + cateName + "，物流模式：" + shippingMode + "，SKU："
                //                                 + op.getSku() + "没有维护保本价信息，闸住，请联系专员放行订单！";
                //                }
                if (priceInfo == null) {
                    continue;
                }

                //保本价，如果有活动闸口价，优先用活动闸口价
                BigDecimal guaranteePrice = BigDecimal.ZERO;
                if (orders.getAddTime().intValue() >= priceInfo.getActivityGuaranteePriceStartTime()
                        .intValue()
                        && orders.getAddTime().intValue() <= priceInfo
                        .getActivityGuaranteePriceEndTime().intValue()) {
                    guaranteePrice = priceInfo.getActivityGuaranteePrice();
                } else {
                    guaranteePrice = priceInfo.getGuaranteePrice();
                }
                //优惠金额，各渠道不同，增加新渠道时需要注意
                //优惠券信息，比较保本价和计算差额用
                BigDecimal couponPrice = BigDecimal.ZERO;
                //商城优惠券金额，报表用
                BigDecimal scCouponPrice = BigDecimal.ZERO;
                //平台承担优惠券金额，报表用
                BigDecimal platformCouponPrice = BigDecimal.ZERO;
                String channel = guaranteePriceChannelSourceMap.get(orders.getSource());
                if ("SC".equalsIgnoreCase(channel) || "SG".equalsIgnoreCase(channel)) {
                    //商城渠道和顺逛渠道统一规则：
                    //balanceAmount（余额）+couponAmount（优惠券抵扣金额）+usedGiftCardAmount（礼品卡抵用的金额）+
                    //orderPromotionAmount（下单立减金额）+itemShareAmount（订单优惠价格分摊）+points（积分/金币）+
                    //couponCodeValue（优惠码优惠金额）+hbAmount（红包）
                    //seashell_amt(海贝积分金额)+insurance_amt(保险积分金额)+diamond_amt(钻金额)
                    couponPrice = op.getBalanceAmount().add(op.getCouponAmount())
                            .add(op.getUsedGiftCardAmount()).add(op.getOrderPromotionAmount())
                            .add(op.getItemShareAmount()).add(op.getCouponCodeValue())
                            .add(op.getHbAmount());
                    if (op != null && op.getPoints() != null && op.getPoints().longValue() > 0) {
                        couponPrice = couponPrice.add(new BigDecimal(op.getPoints())
                                .divide(new BigDecimal(100), 2, BigDecimal.ROUND_UNNECESSARY));
                    }
                    //2018-06-26 网单扩展属性表不用了 相关逻辑删除 start
                    /*OrderProductsAttributes attribute = orderProductsAttributesService
                            .getByOrderProductId(op.getId());
                    if (attribute != null) {
                        if (attribute.getSeashellAmt() != null) {
                            couponPrice = couponPrice.add(attribute.getSeashellAmt());
                        }
                        if (attribute.getInsuranceAmt() != null) {
                            couponPrice = couponPrice.add(attribute.getInsuranceAmt());
                        }
                        if (attribute.getDiamondAmt() != null) {
                            couponPrice = couponPrice.add(attribute.getDiamondAmt());
                        }
                    }*/
                    //2018-06-26 网单扩展属性表不用了 相关逻辑删除 end
                    //商城优惠券金额
                    scCouponPrice = couponPrice;
                    //平台承担优惠券金额为0,初值即0
                } else if ("DSPT".equalsIgnoreCase(channel)) {
                    //电商平台渠道用第三方除店铺外所有优惠券金额+店铺优惠券
                    OrderCoupons orderCoupons = orderCouponsService
                            .getOrderCouponsByCOrderSn(op.getCOrderSn());
                    if (orderCoupons != null) {
                        couponPrice = platFormCouponPrice(orderCoupons);
                    }
                    //商城优惠券金额(对应网单优惠券金额－平台承担优惠券金额)
                    scCouponPrice = op.getCouponAmount().subtract(couponPrice);
                    //平台承担优惠券金额
                    platformCouponPrice = couponPrice;
                } else if ("SCFX".equalsIgnoreCase(channel)) {
                    //商城分销渠道用礼品卡
                    couponPrice = op.getUsedGiftCardAmount();
                    //商城优惠券金额
                    scCouponPrice = couponPrice;
                    //平台承担优惠券金额为0,初值即0
                } else if ("TB".equalsIgnoreCase(channel)) {
                    //天猫渠道,积分金额+集分金额+点券金额+红包金额+店铺优惠券+优惠券
                    OrderCoupons orderCoupons = orderCouponsService
                            .getOrderCouponsByCOrderSn(op.getCOrderSn());
                    if (orderCoupons != null) {
                        couponPrice = tmCouponPrice(orderCoupons);
                    }
                    //商城优惠券金额
                    scCouponPrice = op.getCouponAmount();
                    //平台承担优惠券金额
                    platformCouponPrice = couponPrice;
                }
                //网单表productAmount字段＋优惠券金额 < 保本价*网单数量，则锁定
                if ((op.getProductAmount().add(couponPrice))
                        .compareTo(guaranteePrice.multiply(new BigDecimal(op.getNumber()))) == -1) {
                    OrderPriceGate orderPriceGateLock = setOrderPriceGateInfo(orders,
                            guaranteePriceChannelSourceMap, op, priceInfo, guaranteePrice, couponPrice,
                            scCouponPrice, platformCouponPrice, lockReason);

                    orderPriceGateLockList.add(orderPriceGateLock);
                    orderOperateLogsList
                            .add(constructOperateLog(orders, op, user, "保本价闸口闸住", lockReason, log));
                }
            } //for end

            if (!orderOperateLogsList.isEmpty()) {
                //插入闸口锁定表
                orderPriceGateService.batchInsert(orderPriceGateLockList);
                //插入操作日志表
                orderOperateLogsService.batchInsert(orderOperateLogsList);
                //更新订单表不自动确认
                ordersNewService.updateNotAutoConfirm(Integer.valueOf(orders.getId()), 0, 1);
                //发短信和邮件
                if (guaranteePriceMsgMap != null && !guaranteePriceMsgMap.isEmpty()) {
                    sendGateNotice(orderPriceGateLockList, guaranteePriceMsgMap);
                }
                return false;
            }
        } //if end
        return true;
    }

    private boolean couponCostPoolGate(Map<String, String> couponCostPoolChannelSourceMap,
                                       Map<String, Map<String, Set<String>>> couponCostPoolMsgMap,
                                       Map<String, Set<String>> sourceIndustryMap,
                                       Map<String, String> groupIndustryMap, OrdersNew orders,
                                       List<OrderProductsNew> subFilterWAOrderProductsList,
                                       Map<String, Map<String, Object>> productMap,
                                       Map<String, String> brandMap, String user, int yearMonth) {
        //渠道与订单来源对照关系中订单来源是开启闸口，订单类型为普通订单，库存为WA
        //定金尾款取价格时要注意，会有问题，尾款金额取不到
        if (couponCostPoolChannelSourceMap != null && !couponCostPoolChannelSourceMap.isEmpty()
                && couponCostPoolChannelSourceMap.containsKey(orders.getSource())//渠道下的订单来源已配置
                && groupIndustryMap != null && !groupIndustryMap.isEmpty() && sourceIndustryMap != null//产品组和产业已配置
                && !sourceIndustryMap.isEmpty() && sourceIndustryMap.containsKey(orders.getSource())//订单来源下的产业已配置
                && orders.getOrderType().intValue() == OrderType.TYPE_NORMAL.getIntValue()) {
            //渠道
            String channel = couponCostPoolChannelSourceMap.get(orders.getSource());
            //日志
            List<OrderOperateLogs> orderOperateLogsList = new ArrayList<OrderOperateLogs>();
            //锁定
            List<OrderPriceGate> orderPriceGateLockList = new ArrayList<OrderPriceGate>();
            //循环库存为WA的网单
            for (OrderProductsNew op : subFilterWAOrderProductsList) {
                //没占库存的不闸
                if (op.getStatus().intValue() != OrderProductsNew.STATUS_FROZEN_STOCK.intValue()) {
                    continue;
                }
                //产品组
                String department = itemBaseNewService.getByMaterialCode(op.getSku());
                if (StringUtil.isEmpty(department)) {
                    log.error("费用池闸口,网单号:" + op.getCOrderSn() + ",根据sku在基础信息表中找不到信息");
                    continue;
                }
                //产业
                String industry = groupIndustryMap.get(department);
                //不在产品组和产业对应关系表中的产品组不闸，订单来源下没有配置产业的不闸
                if (!groupIndustryMap.containsKey(department)
                        || !sourceIndustryMap.get(orders.getSource()).contains(industry)) {
                    continue;
                }
                //店铺优惠券
                BigDecimal dpCoupon = getDpCouponAmount(channel, op);
                //没使用店铺优惠券的不闸
                if (dpCoupon != null && dpCoupon.compareTo(BigDecimal.ZERO) < 1) {
                    continue;
                }

                String lockReason = "月度费用已占用完，不允许继续使用店铺优惠券！";
                List<CostPools> costPoolList = costPoolsService
                        .getCouponCostPoolByChannelAndChanYeAndYearMonth(
                                CostPools.getChannelValue(channel), groupIndustryMap.get(department),
                                yearMonth);
                if (costPoolList == null || costPoolList.isEmpty()) {
                    lockReason = "月度店铺优惠券费用未维护！";
                    //闸住代码组织
                    couponPoolGate(orders, op, productMap, brandMap, dpCoupon, channel, industry,
                            department, lockReason, user, orderPriceGateLockList, orderOperateLogsList);
                    continue;
                }

                BigDecimal batchCanUseCouponSum = BigDecimal.ZERO;
                for (CostPools costPools : costPoolList) {
                    batchCanUseCouponSum = batchCanUseCouponSum
                            .add(costPools.getAmount().subtract(costPools.getBalanceAmount()));
                }
                //费用池不够扣减
                if (dpCoupon.compareTo(batchCanUseCouponSum) == 1) {
                    //闸住代码组织
                    couponPoolGate(orders, op, productMap, brandMap, dpCoupon, channel, industry,
                            department, lockReason, user, orderPriceGateLockList, orderOperateLogsList);
                    continue;
                }
                //先查询扣减表中是否之前扣减过，扣过就不再扣减，直接过
                OrderPriceCostPoolUseInfo cpui = orderPriceCostPoolUseInfoService
                        .getByCorderSn(op.getCOrderSn());
                if (cpui != null) {
                    continue;
                }
                CostPools costPool = costPoolList.get(0);
                if (costPoolList.size() == 1) {
                    int result = costPoolsService.updateCouponCostPool(costPool.getId(), dpCoupon);
                    //update失败，说明并发
                    if (result == 0) {
                        couponPoolGate(orders, op, productMap, brandMap, dpCoupon, channel,
                                industry, department, lockReason, user, orderPriceGateLockList,
                                orderOperateLogsList);
                        continue;
                    } else {
                        //插入扣减费用新表
                        costPoolUseInfo(orders, op, productMap, brandMap, dpCoupon, channel,
                                industry, department);
                    }
                } else {
                    BigDecimal batchCanUseCoupon = BigDecimal.ZERO;
                    batchCanUseCoupon = costPool.getAmount().subtract(costPool.getBalanceAmount());
                    //1批次剩余>=网单优惠券
                    if (batchCanUseCoupon.compareTo(dpCoupon) >= 0) {
                        int result = costPoolsService.updateCouponCostPool(costPool.getId(), dpCoupon);
                        //update失败，说明并发，下次处理
                        if (result == 0) {
                            //下次处理
                            return false;
                        } else {
                            //插入扣减费用新表
                            costPoolUseInfo(orders, op, productMap, brandMap, dpCoupon, channel,
                                    industry, department);
                            continue;
                        }
                    } else {
                        //1批次剩余<网单优惠券
                        int result = costPoolsService.updateCouponCostPool(costPool.getId(),
                                batchCanUseCoupon);
                        //update失败，说明并发，下次处理
                        if (result == 0) {
                            //下次处理
                            return false;
                        } else {
                            //1批次update成功
                            //循环剩余批次，业务上，第一次成功，剩余扣减就可以了，不会失败。程序上，只要有失败说明不够用，递归
                            if (!subtractCoupon(dpCoupon.subtract(batchCanUseCoupon), costPoolList,
                                    null)) {
                                //内层事务开启，闸住代码组织，提交，抛异常，回滚外部事务
                                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                                def.setPropagationBehavior(
                                        TransactionDefinition.PROPAGATION_REQUIRES_NEW);
//                                TransactionStatus status = transactionManager.getTransaction(def);
                                try {
                                    //闸住代码组织
                                    couponPoolGate(orders, op, productMap, brandMap, dpCoupon,
                                            channel, industry, department, lockReason, user,
                                            orderPriceGateLockList, orderOperateLogsList);
                                    if (!orderOperateLogsList.isEmpty()) {
                                        //插入闸口锁定表
                                        orderPriceGateService.batchInsert(orderPriceGateLockList);
                                        //插入操作日志表
                                        orderOperateLogsService.batchInsert(orderOperateLogsList);
                                        //更新订单表不自动确认
                                        ordersNewService.updateNotAutoConfirm(Integer.valueOf(orders.getId()), 0, 1);
                                        //发短信和邮件
                                        if (couponCostPoolMsgMap != null
                                                && !couponCostPoolMsgMap.isEmpty()) {
                                            sendGateNotice(orderPriceGateLockList,
                                                    couponCostPoolMsgMap);
                                        }
                                    }
                                    //提交事务
//                                    transactionManager.commit(status);
                                } catch (Exception e) {
                                    //回滚事务
//                                    transactionManager.rollback(status);
                                    log.error("费用池优惠券扣减异常,订单ID:" + orders.getId(), e);
                                }
                                throw new BusinessException("费用池扣减业务，费用不够扣，回滚");
                            } else {
                                //插入扣减费用新表
                                costPoolUseInfo(orders, op, productMap, brandMap, dpCoupon, channel,
                                        industry, department);
                            }
                        }
                    }
                }
            } //for end
            if (!orderOperateLogsList.isEmpty()) {
                //插入闸口锁定表
                orderPriceGateService.batchInsert(orderPriceGateLockList);
                //插入操作日志表
                orderOperateLogsService.batchInsert(orderOperateLogsList);
                //更新订单表不自动确认
                ordersNewService.updateNotAutoConfirm(Integer.valueOf(orders.getId()), 0, 1);
                //发短信和邮件
                if (couponCostPoolMsgMap != null && !couponCostPoolMsgMap.isEmpty()) {
                    sendGateNotice(orderPriceGateLockList, couponCostPoolMsgMap);
                }
                return false;
            }
        } //if end
        return true;
    }

    private boolean subtractCoupon(BigDecimal dpCoupon, List<CostPools> costPoolList,
                                   Integer bacthNum) {
        bacthNum = bacthNum == null ? 1 : bacthNum;
        CostPools costPool = costPoolList.get(bacthNum);
        //当前批次可使用的金额
        BigDecimal batchCanUseCoupon = costPool.getAmount().subtract(costPool.getBalanceAmount());
        if (batchCanUseCoupon.compareTo(dpCoupon) >= 0) {
            int result = costPoolsService.updateCouponCostPool(costPool.getId(), dpCoupon);
            //update失败，说明不够用
            if (result == 0) {
                return false;
            } else {
                return true;
            }
        } else {
            int result = costPoolsService.updateCouponCostPool(costPool.getId(), batchCanUseCoupon);
            //update失败，说明不够用
            if (result == 0) {
                return false;
            } else {
                //成功继续
                //如果最后一次小于情况，还是成功，说明不够用
                if (bacthNum == costPoolList.size() - 1) {
                    return false;
                } else {
                    return subtractCoupon(dpCoupon.subtract(batchCanUseCoupon), costPoolList,
                            ++bacthNum);
                }
            }
        }
    }

    private void couponPoolGate(OrdersNew order, OrderProductsNew orderProduct,
                                Map<String, Map<String, Object>> productMap,
                                Map<String, String> brandMap, BigDecimal dpCoupon, String channel,
                                String industry, String department, String lockReason, String user,
                                List<OrderPriceGate> orderPriceGateLockList,
                                List<OrderOperateLogs> orderOperateLogsList) {
        OrderPriceGate orderPriceGateLock = setCouponCostPoolGateInfo(order, orderProduct,
                productMap, brandMap, dpCoupon, channel, industry, department, lockReason);
        orderPriceGateLockList.add(orderPriceGateLock);
        orderOperateLogsList
                .add(constructOperateLog(order, orderProduct, user, "费用池店铺优惠券闸口闸住", lockReason, null));
    }

    private void costPoolUseInfo(OrdersNew order, OrderProductsNew orderProduct,
                                 Map<String, Map<String, Object>> productMap,
                                 Map<String, String> brandMap, BigDecimal dpCoupon, String channel,
                                 String industry, String department) {
        OrderPriceCostPoolUseInfo useInfo = setCostPoolUseInfo(order, orderProduct, productMap,
                brandMap, dpCoupon, channel, industry, department);
        orderPriceCostPoolUseInfoService.insert(useInfo);
    }

    private BigDecimal getDpCouponAmount(String channel, OrderProductsNew op) {
        BigDecimal dpCoupon = BigDecimal.ZERO;
        if ("SC".equalsIgnoreCase(channel)) {
            dpCoupon = op.getCouponAmount();
        } else if ("SG".equalsIgnoreCase(channel)) {
            dpCoupon = op.getCouponAmount().add(op.getCouponCodeValue());
        } else if ("DSPT".equalsIgnoreCase(channel)) {
            OrderCoupons orderCoupons = orderCouponsService.getOrderCouponsByCOrderSn(op.getCOrderSn());
            if (orderCoupons != null && orderCoupons.getDpCouponAmount() != null) {
                dpCoupon = orderCoupons.getDpCouponAmount();
            }
        } else if ("TB".equalsIgnoreCase(channel)) {
            OrderCoupons orderCoupons = orderCouponsService.getOrderCouponsByCOrderSn(op.getCOrderSn());
            if (orderCoupons != null) {
                if (orderCoupons.getDpCouponAmount() != null) {
                    dpCoupon = dpCoupon.add(orderCoupons.getDpCouponAmount());
                }
                if (orderCoupons.getCouponAmount() != null) {
                    dpCoupon = dpCoupon.add(orderCoupons.getCouponAmount());
                }
            }
        }
        return dpCoupon;
    }

    private BigDecimal platFormCouponPrice(OrderCoupons orderCoupons) {
        BigDecimal price = BigDecimal.ZERO;
        if (orderCoupons.getJfbAmount() != null) {
            price = price.add(orderCoupons.getJfbAmount());
        }
        if (orderCoupons.getHbAmount() != null) {
            price = price.add(orderCoupons.getHbAmount());
        }
        if (orderCoupons.getDqAmount() != null) {
            price = price.add(orderCoupons.getDqAmount());
        }
        if (orderCoupons.getCouponAmount() != null) {
            price = price.add(orderCoupons.getCouponAmount());
        }
        if (orderCoupons.getPoints() != null) {
            price = price.add(orderCoupons.getPoints());
        }
        if (orderCoupons.getTyqAmount() != null) {
            price = price.add(orderCoupons.getTyqAmount());
        }
        if (orderCoupons.getDjqAmount() != null) {
            price = price.add(orderCoupons.getDjqAmount());
        }
        if (orderCoupons.getDjpzAmount() != null) {
            price = price.add(orderCoupons.getDjpzAmount());
        }
        if (orderCoupons.getDpCouponAmount() != null) {
            price = price.add(orderCoupons.getDpCouponAmount());
        }
        return price;
    }

    private BigDecimal tmCouponPrice(OrderCoupons orderCoupons) {
        BigDecimal price = BigDecimal.ZERO;
        if (orderCoupons.getJfbAmount() != null) {
            price = price.add(orderCoupons.getJfbAmount());
        }
        if (orderCoupons.getDqAmount() != null) {
            price = price.add(orderCoupons.getDqAmount());
        }
        if (orderCoupons.getHbAmount() != null) {
            price = price.add(orderCoupons.getHbAmount());
        }
        if (orderCoupons.getPoints() != null) {
            price = price.add(orderCoupons.getPoints());
        }
        if (orderCoupons.getDpCouponAmount() != null) {
            price = price.add(orderCoupons.getDpCouponAmount());
        }
        if (orderCoupons.getCouponAmount() != null) {
            price = price.add(orderCoupons.getCouponAmount());
        }
        return price;
    }

    private Map<Integer, List<OrderProductsNew>> getOrderProducts(List<OrdersNew> orderList) {
        Map<Integer, List<OrderProductsNew>> map = new HashMap<Integer, List<OrderProductsNew>>();
        Set<Integer> orderIdSet = new HashSet<Integer>();
        for (OrdersNew orders : orderList) {
            orderIdSet.add(Integer.valueOf(orders.getId()));
        }
        List<OrderProductsNew> orderProductsList = orderProductsNewService
                .getByOrderIdsForConfirm(new ArrayList<Integer>(orderIdSet));
        if (null == orderProductsList || orderProductsList.isEmpty()) {
            return map;
        }
        List<OrderProductsNew> orderProductList = null;
        for (OrderProductsNew orderProducts : orderProductsList) {
            if (map.containsKey(orderProducts.getOrderId())) {
                orderProductList = map.get(orderProducts.getOrderId());
            } else {
                orderProductList = new ArrayList<OrderProductsNew>();
                map.put(orderProducts.getOrderId(), orderProductList);
            }
            orderProductList.add(orderProducts);
        }
        return map;
    }

    /**
     * 检查大家电类别是否全部占有库存，如果有一个未占有就去掉所有大家电，小家电只要占用库存的网单就加入队列
     *
     * @param orderProductsList
     */
    private List<OrderProductsNew> splitOrderProductList(List<OrderProductsNew> orderProductsList) {
        List<OrderProductsNew> b2cOrderProductsList = new ArrayList<OrderProductsNew>();
        List<OrderProductsNew> b2b2cOrderProductsList = new ArrayList<OrderProductsNew>();
        List<OrderProductsNew> subOrderProductsList = new ArrayList<OrderProductsNew>();
        boolean b2b2cStockFlag = true;//如果所有大家电都占用库存就保存队列，否则不保存
        for (OrderProductsNew orderProducts : orderProductsList) {
            if ("B2C".equalsIgnoreCase(orderProducts.getShippingMode())) {//‘B2C’为小家电
                if (OrderProductsNew.STATUS_FROZEN_STOCK.equals(orderProducts.getStatus())) {
                    b2cOrderProductsList.add(orderProducts);
                }
            } else {//为空和‘B2B2C’是大家电
                if (OrderProductsNew.STATUS_FROZEN_STOCK.equals(orderProducts.getStatus())) {
                    b2b2cOrderProductsList.add(orderProducts);
                } else {
                    b2b2cStockFlag = false;
                }
            }
        }
        subOrderProductsList.addAll(b2cOrderProductsList);
        if (!b2b2cStockFlag) {
            b2b2cOrderProductsList.clear();
        } else {
            subOrderProductsList.addAll(b2b2cOrderProductsList);
        }
        return subOrderProductsList;
    }

    private boolean doConfirm(OrdersNew orders, String user, List<OrderProductsNew> orderProductsList) {
        Integer mysqlTime = Long.valueOf(System.currentTimeMillis() / 1000L).intValue();
        OrderWorkflows orderWorkflows = new OrderWorkflows();
        Integer status = OrderStatus.OS_CONFIRM.getCode();
        OrderProductsNew orderProducts = null;
        for (Iterator<OrderProductsNew> it = orderProductsList.iterator(); it.hasNext(); ) {
            orderProducts = it.next();
            if (orderProducts.getStatus().intValue() == 0) { // 如果存在网单未分配库存，订单状态需改为部分缺货。
                status = OrderStatus.OS_NOSTOCK.getCode();
                it.remove();
                break;
            }
            if (orderProducts.getStatus().intValue() != OrderProductsNew.STATUS_FROZEN_STOCK
                    .intValue()) {//只处理网单状态为已分配库位（1）的记录
                it.remove();
            }
        }

        //如果以前已经是部分缺货,订单状态未改变,什么都不做
        if (orderProductsList.isEmpty()
                || status.intValue() == orders.getOrderStatus().intValue()) {
            return false;
        }

        orders.setConfirmTime(mysqlTime);
        if (null == orders.getFirstConfirmPerson()) {
            orders.setFirstConfirmPerson(user);
        }
        if (orders.getFirstConfirmTime().intValue() == 0) {
            orders.setFirstConfirmTime(mysqlTime);
        }
        orders.setOrderStatus(status);
        //更新订单
        int updateNumber = ordersNewService.updateForConfirm(orders);
        //出现并发或取消订单
        if (updateNumber < 1) {
            orderProductsList.clear();
            log.info("出现并发或已取消订单,order id=" + orders.getId());
            return false;
        }
        //更新全流程表
        orderWorkflows.setOrderId(Integer.valueOf(orders.getId()));
        orderWorkflows.setConfirmPeople(user);
        orderWorkflows.setConfirmTime(mysqlTime.longValue());
        orderWorkflowsService.updateForConformOrder(orderWorkflows);
        return true;
    }

    private void sendGateNotice(List<OrderPriceGate> orderPriceGateLockList,
                                Map<String, Map<String, Set<String>>> guaranteePriceMsgMap) {
        String orderSn = null;
        try {
            for (OrderPriceGate lock : orderPriceGateLockList) {
                orderSn = lock.getOrderSn();
                Map<String, Set<String>> msgMap = guaranteePriceMsgMap.get(lock.getChannelCode());
                if (msgMap != null && !msgMap.isEmpty()) {
                    //内容
                    String msg = SmsTemplateConst.GUARANTEE_PRICE_NOTICE.replaceAll("#person#", "")
                            .replaceAll("#channel#", CHANNELMAP.get(lock.getChannelCode()))
                            .replaceAll("#orderSn#", lock.getOrderSn())
                            .replaceAll("#orderProductSn#", lock.getCorderSn())
                            .replaceAll("#lockReason#", lock.getLockReason());
                    Set<String> mobileSet = msgMap.get("mobile");
                    if (mobileSet != null && !mobileSet.isEmpty()) {
                        for (String mobile : mobileSet) {
                            //发短信
                            sendGuaranteePriceSms(mobile, msg, lock.getOrderSn());
                        }
                    }
                    Set<String> emailSet = msgMap.get("email");
                    if (emailSet != null && !emailSet.isEmpty()) {
                        for (String email : emailSet) {
                            //发邮件
                            sendGuaranteePriceEmail(email, msg, lock.getOrderSn());
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("确认订单闸口发通知失败【" + orderSn + "】:", e);
        }
    }

    private void sendSms(OrdersNew orders, List<OrderProductsNew> orderProductsList, Members members) {
        try {
            if (members == null) {
                return;
            }
            Integer mysqlTime = Long.valueOf(System.currentTimeMillis() / 1000L).intValue();
            SmsLogs smsLogs = new SmsLogs();
            //如果手机号为空,是会员的话取会员表里手机号
            if (StringUtils.isBlank(orders.getMobile()) && orders.getMemberId() > 0) {
                orders.setMobile(members.getMobile());
            }
            if (StringUtils.isBlank(orders.getMobile())) {
                return;
            }
            String mobile = orders.getMobile().replace(" ", "").replace("-", "");
            if (StringUtils.isEmpty(mobile) || mobile.length() > 11) {
                return;
            }
            mobile = mobile.trim();
            for (OrderProductsNew orderProducts : orderProductsList) {
                String smsMsg = this.constructSmsMsg(orders, orderProducts);
                if (StringUtils.isBlank(smsMsg)) {
                    continue;
                }
                smsLogs.setMobile(mobile);
                smsLogs.setName(mobile);
                smsLogs.setMessage(smsMsg);
                smsLogs.setAddTime(mysqlTime);
                smsLogs.setIsSuccess(0);//0-成功 1-失败
                smsLogs.setPriority(0);
                smsLogs.setLastTime(0);
                smsLogs.setTryNum(0);
                smsLogs.setSiteId(1);
                smsLogsWriteService.insertSmsLogs(smsLogs);
            }

        } catch (Exception e) {
            log.error("确认订单发短信失败【" + orders.getOrderSn() + "】:", e);
        }
    }

    /**
     * 发信息到店铺微店主
     *
     * @param orders
     * @param orderProductsList
     */
    private void sendSmsToStore(OrdersNew orders, List<OrderProductsNew> orderProductsList) {
        for (OrderProductsNew orderProducts : orderProductsList) {
            //查询会员信息 方便发短信用
            Members members = membersService.get(orderProducts.getStoreId());
            if (members == null || StringUtils.isBlank(members.getMobile())
                    || members.getMobile().length() > 11) {
                return;
            }
            Integer mysqlTime = Long.valueOf(System.currentTimeMillis() / 1000L).intValue();
            String mobile = members.getMobile().replace(" ", "").replace("-", "");
            if (StringUtils.isNotEmpty(mobile)) {
                mobile = mobile.trim();
            }
            try {
                SmsLogs smsLogs = new SmsLogs();
                String smsMsg = SmsTemplateConst.NEW_SALES_ORDER
                        .replaceAll("#orderSn#", orderProducts.getCOrderSn())
                        .replaceAll("#productName#", orderProducts.getProductName())
                        .replaceAll("#number#", orderProducts.getNumber().toString())
                        .replaceAll("#customerName#", orders.getConsignee())
                        .replaceAll("#customerPhoneNum#", orders.getMobile());
                smsLogs.setMobile(mobile);
                smsLogs.setName(mobile);
                smsLogs.setMessage(smsMsg);
                smsLogs.setAddTime(mysqlTime);
                smsLogs.setIsSuccess(0);//0-成功 1-失败
                smsLogs.setPriority(0);
                smsLogs.setLastTime(0);
                smsLogs.setTryNum(0);
                smsLogs.setSiteId(1);
                smsLogsWriteService.insertSmsLogs(smsLogs);
            } catch (Exception e) {
                log.error("确认订单发短信给店铺失败【" + orders.getOrderSn() + "】:", e);
            }
            this.sendAppMsg(orders, orderProducts, members);

        }
    }

    /**
     * 发信息到APP端
     *
     * @param orders
     * @param orderProducts
     * @param members
     */
    private void sendAppMsg(OrdersNew orders, OrderProductsNew orderProducts, Members members) {
        try {
            OrderMsgQueue orderMsgQueue = new OrderMsgQueue();
            orderMsgQueue.setCorderSn(orderProducts.getCOrderSn());
            orderMsgQueue.setOrderAmount(orderProducts.getProductAmount());
            if ("COD".equalsIgnoreCase(orders.getPaymentCode())) {
                orderMsgQueue.setIsCod(1);
            } else {
                orderMsgQueue.setIsCod(0);
            }
            orderMsgQueue.setReceiverName(orders.getConsignee());
            orderMsgQueue.setReceiverMobile(orders.getMobile());
            orderMsgQueue.setReceiverAddress(orders.getRegionName() + " " + orders.getAddress());
            orderMsgQueue.setStoreName(members.getNickName());
            orderMsgQueue.setMemberMobile(members.getMobile());
            orderMsgQueue.setMemberId(members.getId());
            orderMsgQueue.setSku(orderProducts.getSku());
            orderMsgQueue.setNum(orderProducts.getNumber());
            orderMsgQueue.setProductName(orderProducts.getProductName());
            orderMsgQueue.setOrderProductId(orderProducts.getId());
            orderMsgQueueService.insert(orderMsgQueue);
        } catch (Exception e) {
            log.error("确认订单保存APP消息失败【" + orders.getOrderSn() + "】:", e);
        }
    }

    private void sendGuaranteePriceSms(String mobile, String msg, String orderSn) {
        try {
            if (StringUtil.isEmpty(mobile, true)) {
                return;
            }
            if (StringUtil.isEmpty(msg, true)) {
                return;
            }
            mobile = mobile.replace(" ", "").replace("-", "");
            if (mobile.length() > 11) {
                return;
            }

            SmsLogs smsLogs = new SmsLogs();
            smsLogs.setMobile(mobile);
            smsLogs.setName(mobile);
            smsLogs.setMessage(msg);
            smsLogs.setAddTime(Long.valueOf(System.currentTimeMillis() / 1000L).intValue());
            smsLogs.setIsSuccess(0);//0-成功 1-失败
            smsLogs.setPriority(0);
            smsLogs.setLastTime(0);
            smsLogs.setTryNum(0);
            smsLogs.setSiteId(1);
            smsLogsWriteService.insertSmsLogs(smsLogs);
        } catch (Exception e) {
            log.error("确认订单保本价闸口发短信失败【" + orderSn + "】:", e);
        }
    }

    private void sendGuaranteePriceEmail(String email, String msg, String orderSn) {
        try {
            if (StringUtil.isEmpty(email, true)) {
                return;
            }
            if (StringUtil.isEmpty(msg, true)) {
                return;
            }
            email = email.trim();

            QueueMails queueMails = new QueueMails();
            queueMails.setAddTime(Long.valueOf(System.currentTimeMillis() / 1000L).intValue());
            queueMails.setBody(msg);
            queueMails.setDeadline(0);
            queueMails.setLastSentTime(0);
            queueMails.setSubject("订单号" + orderSn + "保本价闸口通知");
            queueMails.setTo(email);
            queueMails.setPriority(1);//高
            queueMails.setSentOk(0);
            queueMails.setSentTimes(0);
            queueMails.setLastErrorMessage("");
            queueMails.setSiteId(1);
            /*queueMailsWriteService.insertQueueMails(queueMails);*/
        } catch (Exception e) {
            log.error("确认订单保本价闸口发邮件失败【" + orderSn + "】:", e);
        }
    }

    private OrderPriceGate setOrderPriceGateInfo(OrdersNew orders,
                                                 Map<String, String> guaranteePriceChannelSourceMap,
                                                 OrderProductsNew op,
                                                 OrderGuaranteePriceInfo priceInfo,
                                                 BigDecimal guaranteePrice, BigDecimal couponPrice,
                                                 BigDecimal scCouponPrice,
                                                 BigDecimal platformCouponPrice,
                                                 String lockReason) {
        OrderPriceGate orderPriceGateLock = new OrderPriceGate();
        orderPriceGateLock.setOrderSource(orders.getSource());//订单来源
        orderPriceGateLock.setChannelCode(guaranteePriceChannelSourceMap.get(orders.getSource()));//渠道
        orderPriceGateLock.setIndustryCode("");//产业
        orderPriceGateLock.setProductGroup("");//产品组
        orderPriceGateLock.setCorderSn(op.getCOrderSn());//网单号
        orderPriceGateLock.setOrderSn(orders.getOrderSn());//订单号
        orderPriceGateLock.setSku(op.getSku());//物料编码
        orderPriceGateLock.setCateId(priceInfo.getCateId());//品类id
        orderPriceGateLock.setCateName(priceInfo.getCateName());//品类名称
        orderPriceGateLock.setBrandId(op.getBrandId());//品牌id
        orderPriceGateLock.setBrandName(priceInfo.getBrandName());//品牌名称
        orderPriceGateLock.setOrderProductPrice(op.getPrice());//网单单价
        orderPriceGateLock.setCouponAmount(scCouponPrice);//商城优惠券金额
        orderPriceGateLock.setPlatformCouponAmount(platformCouponPrice);//平台承担优惠券金额
        orderPriceGateLock.setOrderProductAmount(op.getProductAmount());//网单表productAmount字段
        orderPriceGateLock.setOrderProductNumber(op.getNumber());//网单数量
        orderPriceGateLock.setGuaranteePrice(guaranteePrice);//网单保本价(单价)
        orderPriceGateLock.setSubductionPrice(op.getProductAmount().add(couponPrice)
                .subtract(guaranteePrice.multiply(new BigDecimal(op.getNumber()))));//差额(网单表productAmount字段+优惠金额-保本价*网单数量)
        orderPriceGateLock.setOrderAddTime(orders.getAddTime().toString());//下单时间
        orderPriceGateLock.setCreateTime(((Long) (System.currentTimeMillis() / 1000)).toString());//被闸时间
        orderPriceGateLock.setLockReason(lockReason);//被闸原因
        orderPriceGateLock.setOperator("");//放行操作人
        orderPriceGateLock.setResponsiblePerson("");//责任人
        orderPriceGateLock.setUnlockReason("");//放行原因
        orderPriceGateLock.setUnlockTime(null);//放行时间
        orderPriceGateLock.setGateStatus(1);//闸口状态(1-闸住;0-不闸)
        orderPriceGateLock.setGateType(GUARANTEEPRICE_TYPE);
        orderPriceGateLock.setUpdateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));//更新时间
        return orderPriceGateLock;
    }

    private OrderPriceGate setCouponCostPoolGateInfo(OrdersNew orders, OrderProductsNew op,
                                                     Map<String, Map<String, Object>> productMap,
                                                     Map<String, String> brandMap,
                                                     BigDecimal dpCoupon, String channel,
                                                     String industryCode, String productGroup,
                                                     String lockReason) {
        OrderPriceGate orderPriceGateLock = new OrderPriceGate();
        if (productMap == null || productMap.isEmpty()) {
            productMap.putAll(getProductCate());
        }
        if (brandMap == null || brandMap.isEmpty()) {
            brandMap.putAll(getBrand());
        }
        orderPriceGateLock.setOrderSource(orders.getSource());//订单来源
        orderPriceGateLock.setChannelCode(channel);//渠道
        orderPriceGateLock.setIndustryCode(industryCode);//产业
        orderPriceGateLock.setProductGroup(productGroup);//产品组
        orderPriceGateLock.setCorderSn(op.getCOrderSn());//网单号
        orderPriceGateLock.setOrderSn(orders.getOrderSn());//订单号
        orderPriceGateLock.setSku(op.getSku());//物料编码
        if (productMap != null && !productMap.isEmpty()) {
            Map<String, Object> tempMap = getSuperCateMap(op.getCateId().toString(), productMap);
            if (tempMap != null) {
                orderPriceGateLock.setCateId(Integer.parseInt(tempMap.get("id").toString()));//品类id
                orderPriceGateLock.setCateName(tempMap.get("cateName").toString());//品类名称
            } else {
                orderPriceGateLock.setCateId(op.getCateId());//品类id
                orderPriceGateLock.setCateName("");//品类名称
            }
        } else {
            orderPriceGateLock.setCateId(op.getCateId());//品类id
            orderPriceGateLock.setCateName("");//品类名称
        }
        if (brandMap != null && !brandMap.isEmpty()) {
            orderPriceGateLock.setBrandId(op.getBrandId());//品牌id
            orderPriceGateLock.setBrandName(brandMap.get(op.getBrandId().toString()));//品牌名称
        } else {
            orderPriceGateLock.setBrandId(op.getBrandId());//品牌id
            orderPriceGateLock.setBrandName("");//品牌名称
        }
        orderPriceGateLock.setOrderProductPrice(op.getPrice());//网单单价
        orderPriceGateLock.setCouponAmount(BigDecimal.ZERO);//商城优惠券金额
        orderPriceGateLock.setPlatformCouponAmount(BigDecimal.ZERO);//平台承担优惠券金额
        orderPriceGateLock.setOrderProductAmount(op.getProductAmount());//网单表productAmount字段
        orderPriceGateLock.setOrderProductNumber(op.getNumber());//网单数量
        orderPriceGateLock.setGuaranteePrice(BigDecimal.ZERO);//网单保本价(单价)
        orderPriceGateLock.setSubductionPrice(dpCoupon);//差额(店铺优惠券)
        orderPriceGateLock.setOrderAddTime(orders.getAddTime().toString());//下单时间
        orderPriceGateLock.setCreateTime(((Long) (System.currentTimeMillis() / 1000)).toString());//被闸时间
        orderPriceGateLock.setLockReason(lockReason);//被闸原因
        orderPriceGateLock.setOperator("");//放行操作人
        orderPriceGateLock.setResponsiblePerson("");//责任人
        orderPriceGateLock.setUnlockReason("");//放行原因
        orderPriceGateLock.setUnlockTime(null);//放行时间
        orderPriceGateLock.setGateStatus(1);//闸口状态(1-闸住;0-不闸)
        orderPriceGateLock.setGateType(COUPONCOSTPOOL_TYPE);
        orderPriceGateLock.setUpdateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));//更新时间
        return orderPriceGateLock;
    }

    private OrderPriceCostPoolUseInfo setCostPoolUseInfo(OrdersNew orders, OrderProductsNew op,
                                                         Map<String, Map<String, Object>> productMap,
                                                         Map<String, String> brandMap,
                                                         BigDecimal dpCoupon, String channel,
                                                         String industryCode, String productGroup) {
        OrderPriceCostPoolUseInfo useInfo = new OrderPriceCostPoolUseInfo();
        if (productMap == null || productMap.isEmpty()) {
            productMap.putAll(getProductCate());
        }
        if (brandMap == null || brandMap.isEmpty()) {
            brandMap.putAll(getBrand());
        }
        useInfo.setOrderSource(orders.getSource());//订单来源
        useInfo.setChannelCode(channel);//渠道
        useInfo.setIndustryCode(industryCode);//产业
        useInfo.setProductGroup(productGroup);//产品组
        useInfo.setCorderSn(op.getCOrderSn());//网单号
        useInfo.setOrderSn(orders.getOrderSn());//订单号
        useInfo.setSku(op.getSku());//物料编码
        if (productMap != null && !productMap.isEmpty()) {
            Map<String, Object> tempMap = getSuperCateMap(op.getCateId().toString(), productMap);
            if (tempMap != null) {
                useInfo.setCateId(Integer.parseInt(tempMap.get("id").toString()));//品类id
                useInfo.setCateName(tempMap.get("cateName").toString());//品类名称
            } else {
                useInfo.setCateId(op.getCateId());//品类id
                useInfo.setCateName("");//品类名称
            }
        } else {
            useInfo.setCateId(op.getCateId());//品类id
            useInfo.setCateName("");//品类名称
        }
        if (brandMap != null && !brandMap.isEmpty()) {
            useInfo.setBrandId(op.getBrandId());//品牌id
            useInfo.setBrandName(brandMap.get(op.getBrandId().toString()));//品牌名称
        } else {
            useInfo.setBrandId(op.getBrandId());//品牌id
            useInfo.setBrandName("");//品牌名称
        }
        useInfo.setOrderProductPrice(op.getPrice());//网单单价
        useInfo.setCouponAmount(BigDecimal.ZERO);//商城优惠券金额
        useInfo.setPlatformCouponAmount(BigDecimal.ZERO);//平台承担优惠券金额
        useInfo.setOrderProductAmount(op.getProductAmount());//网单表productAmount字段
        useInfo.setOrderProductNumber(op.getNumber());//网单数量
        useInfo.setCostPoolUseAmount(dpCoupon);//占用的费用池金额
        useInfo.setCreateTime(((Long) (System.currentTimeMillis() / 1000)).intValue());//创建时间
        useInfo.setUpdateTime(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));//更新时间
        return useInfo;
    }

    private void addHPOrLESQueues(OrdersNew orders, List<OrderProductsNew> orderProductsList) {
        Integer mysqlTime = Long.valueOf(System.currentTimeMillis() / 1000L).intValue();
        HPQueues hpQueues = null;
        LesQueues lesQueues = null;
        List<HPQueues> hpQueuesList = new ArrayList<HPQueues>();
        List<LesQueues> lesQueuesList = new ArrayList<LesQueues>();
        OrderProductsNew orderProducts = null;
        for (Iterator<OrderProductsNew> it = orderProductsList.iterator(); it.hasNext(); ) {
            orderProducts = it.next();

            if (orders.getIsProduceDaily().intValue() == 1) {
                orderOperateLogsService.insert(this.constructOperateLog(orders, orderProducts, "系统",
                        "日日单", "日日单到货前，暂时不传HP或LES。", null));
                continue;
            }
            //2018/9/3 测试定金尾款 暂时注释掉 换成定金发VOM这款代码就需要注释掉
            if (orderProducts.getShippingOpporunity().intValue() == 1) {
                orderOperateLogsService.insert(this.constructOperateLog(orders, orderProducts, "系统",
                        "定金订单", "尾款发货模式，暂时不传HP或LES。", null));
                continue;
            }

            ServiceResult<InvSection> sectionResult = orderThirdCenterStockCommonService
                    .getSectionByCode(orderProducts.getSCode());
            if (sectionResult.getSuccess() && sectionResult.getResult() != null) {
                InvSection section = sectionResult.getResult();
                if (section.getChannelCode().equals(InvSection.CHANNEL_CODE_GD)
                        || section.getChannelCode().equals(InvSection.CHANNEL_CODE_HAIP)
                        //2017-05-02 净水库存--------START
                        || InvSection.NEW_CHANNEL_CODE.contains(section.getChannelCode())
                    //2017-05-02 净水库存--------END
                        ) {
                    String text = section.getChannelCode().equals(InvSection.CHANNEL_CODE_HAIP)
                            ? "海朋订单" : "";
                    text = section.getChannelCode().equals(InvSection.CHANNEL_CODE_GD) ? "基地库订单"
                            : text;
                    //2017-05-02 净水库存--------START
                    if (!StringUtil
                            .isEmpty(InvSection.NEW_CHANNEL_CODE_MAP.get(section.getChannelCode()))) {
                        text = InvSection.NEW_CHANNEL_CODE_MAP.get(section.getChannelCode()) + "订单";
                    }
                    //2017-05-02 净水库存--------END
                    orderOperateLogsService.insert(this.constructOperateLog(orders, orderProducts, "系统",
                            text, text + "，不传HP或LES。", null));
                    continue;
                }
            } else {
                log.error("判断是否为基地库直发订单失败(" + orderProducts.getCOrderSn() + ")："
                        + sectionResult.getMessage());
                throw new BusinessException("调用判断是否为基地库直发订单失败，回滚事务");
            }

            //****2018/8/29需求修改，大家电派工改为OMS进行派工，只需要给OMS下发订单，然后从回传订单状态数据中，解析网单数据***start
            //加入LES队列
            /*LesQueues lesResult = lesQueuesService.getLesQueueByOpId(orderProducts.getId());
            if (null != lesResult && lesResult.getId() > 0) {
                it.remove();
                continue;
            }
            lesQueuesList = addListLes(orderProducts, lesQueues, mysqlTime, lesQueuesList);*/
            /*if (lesQueuesService.getCountByOpId(orderProducts.getId()) > 0) {
                it.remove();
                continue;
            }
            lesQueuesList = addListLes(orderProducts, lesQueues, mysqlTime, lesQueuesList);*/
            if ("B2C".equalsIgnoreCase(orderProducts.getShippingMode())) {
                //加入LES队列
                if (lesQueuesService.getCountByOpId(orderProducts.getId()) > 0) {
                    it.remove();
                    continue;
                }
                lesQueuesList = addListLes(orderProducts, lesQueues, mysqlTime, lesQueuesList);
            } else {
                //加入HP队列
                if (hpQueuesService.getCountByOpId(orderProducts.getId()) > 0) {
                    it.remove();
                    continue;
                }
                hpQueuesList = addListHP(orderProducts, hpQueues, mysqlTime, hpQueuesList);
            }
        }
        if (!hpQueuesList.isEmpty()) {
            //保存HP队列
            hpQueuesService.insert(hpQueuesList);
        }
        if (!lesQueuesList.isEmpty()) {
            //保存LES队列
            lesQueuesService.insert(lesQueuesList);
        }
        //****2018/8/29需求修改，大家电派工改为OMS进行派工，只需要给OMS下发订单，然后从回传订单状态数据中，解析网单数据***end

    }

    /**
     * 加入les队列
     *
     * @param orderProducts
     * @param lesQueues
     * @param mysqlTime
     * @param lesQueuesList
     * @return
     */
    private List<LesQueues> addListLes(OrderProductsNew orderProducts, LesQueues lesQueues,
                                       Integer mysqlTime, List<LesQueues> lesQueuesList) {
        lesQueues = new LesQueues();
        lesQueues.setAddTime(mysqlTime);
        lesQueues.setAction("createOrder");
        lesQueues.setCount(0);
        lesQueues.setLastMessage("");
        lesQueues.setOrderProductId(orderProducts.getId());
        lesQueues.setPushData("");
        lesQueues.setSuccess(0);
        lesQueues.setIsLock(0);
        lesQueues.setIsStop(0);
        lesQueues.setSuccessTime(0L);
        lesQueues.setLastTryTime(0L);
        lesQueuesList.add(lesQueues);
        return lesQueuesList;
    }

    /**
     * 加入HP队列
     *
     * @param orderProducts
     * @param hpQueues
     * @param mysqlTime
     * @param hpQueuesList
     * @return
     */
    private List<HPQueues> addListHP(OrderProductsNew orderProducts, HPQueues hpQueues,
                                     Integer mysqlTime, List<HPQueues> hpQueuesList) {
        hpQueues = new HPQueues();
        hpQueues.setAddTime(mysqlTime);
        hpQueues.setCount(0);
        hpQueues.setLastMessage("");
        hpQueues.setOrderProductId(orderProducts.getId());
        hpQueues.setPushData("");
        hpQueues.setSuccess(0);
        hpQueues.setSuccessTime(0);
        hpQueuesList.add(hpQueues);
        return hpQueuesList;
    }

    private void addInvoiceType(OrdersNew orders, List<OrderProductsNew> orderProductsList) {
        MemberInvoices memberInvoices = memberInvoicesService.getByOrderId(Integer.valueOf(orders.getId()));
        if (null == memberInvoices) {
            return;
        }
        for (OrderProductsNew orderProducts : orderProductsList) {
            //分配开票方式:普票纸质发票是库房开票,其余的中心开票
            if (memberInvoices.getInvoiceType().intValue() == 2
                    && memberInvoices.getElectricFlag().intValue() == 0) {
                orderProducts.setMakeReceiptType(1);
            } else {
                orderProducts.setMakeReceiptType(2);
            }
            orderProducts.setIsMakeReceipt(9);//待开票
            if (orderProducts.getSystemRemark() == null
                    || (!orderProducts.getSystemRemark().contains("库房开票")
                    && !orderProducts.getSystemRemark().contains("共享开票"))) {
                orderProducts.setSystemRemark(orderProducts.getSystemRemark()
                        + (orderProducts.getMakeReceiptType().intValue() == 1
                        ? "库房开票;" : "共享开票;"));
            }

            orderProductsNewService.updateForMakeReceiptType(orderProducts);
        }
    }

    private void insertKjtProofs(OrdersNew orders) {
        try {
            if (OrderStatus.OS_CONFIRM.getCode().intValue() == orders.getOrderStatus().intValue()
                    && "TB".equals(orderThirdCenterStockCommonService.getChannelCodeByOrderSource(orders.getSource())
                    .getResult())) {
                ordersNewService.insertKjtProofs(Integer.valueOf(orders.getId()), System.currentTimeMillis() / 1000L, "");
            }
        } catch (Exception e) {
            log.error("插入快捷通消息队列失败【ordersn=" + orders.getOrderSn() + "】：", e);
        }
    }

    private void setIsTimeOutFreeInfo(OrdersNew orders, List<OrderProductsNew> subWAOrderProductsList) {
        //非货到付款；非预定订单；非活动订单； 物流模式为 b2b2c；库存类型为WA；
        //订单来源 商城订单、微店、移动商城、移动社交、海尔创客_PC端、海尔创客_移动端；
        //非转运
        //下单地区支持超时免单；支付时间和下单时间差小于30分钟
        String timeOutSource = orders.getSource();
        if (("1".equalsIgnoreCase(timeOutSource) || "MSTORE".equalsIgnoreCase(timeOutSource)
                || "MOBILE".equalsIgnoreCase(timeOutSource)
                || "S_MOBILE".equalsIgnoreCase(timeOutSource) || "CK".equalsIgnoreCase(timeOutSource)
                || "CK_MOBILE".equalsIgnoreCase(timeOutSource))
                && orders.getIsCod().intValue() == 0 && orders.getPayTime().intValue() > 0
                && orders.getPayTime().intValue() > orders.getAddTime().intValue()
                && (orders.getPayTime().intValue() - orders.getAddTime() < 1800)) {
            boolean judgeStreetOrder = OrderCenterOrderBizHelper.judgeStreetOrder(orders.getSource(),
                    orders.getAddTime());
            Integer stTimeOutFree;
            if (judgeStreetOrder) {//街道级
                stTimeOutFree = orderProductsNewService
                        .getStorageStreetsTimeoutFreeByStreetId(orders.getStreet());
            } else {//区县级
                stTimeOutFree = orderProductsNewService
                        .getStorageCitiesTimeoutFreeByRegionId(orders.getRegion());
            }
            //2017-05-24 StorageStreets表timeOutFree＝1 超时免单  ＝2主动超时免单
            if (stTimeOutFree != null
                    && (stTimeOutFree.intValue() == 1 || stTimeOutFree.intValue() == 2)) {
                for (OrderProductsNew timeOutOp : subWAOrderProductsList) {
                    if (timeOutOp.getIsTimeoutFree().intValue() == 0
                            && timeOutOp.getIsBook().intValue() == 0
                            && timeOutOp.getActivityId().intValue() == 0
                            && !"B2C".equalsIgnoreCase(timeOutOp.getShippingMode())
                            && "WA".equalsIgnoreCase(timeOutOp.getStockType())
                            && StringUtil.isEmpty(timeOutOp.getTsCode())) {
                        if (stTimeOutFree.intValue() == 1) {
                            timeOutOp.setIsTimeoutFree(1);
                        } else if (stTimeOutFree.intValue() == 2) {
                            timeOutOp.setIsTimeoutFree(3);
                        }
                        orderProductsNewService.updateIsTimeoutFree(timeOutOp.getId(),
                                timeOutOp.getIsTimeoutFree());
                        orderWorkflowsService.updateIsTimeoutFree(timeOutOp.getId(),
                                timeOutOp.getIsTimeoutFree());
                    }
                }
            }
        }
        for (OrderProductsNew timeOutOp : subWAOrderProductsList) {
            if (timeOutOp.getIsTimeoutFree().intValue() != 1
                    && timeOutOp.getIsTimeoutFree().intValue() != 3) {
                timeOutOp.setIsTimeoutFree(2);
                orderProductsNewService.updateIsTimeoutFree(timeOutOp.getId(),
                        timeOutOp.getIsTimeoutFree());
                orderWorkflowsService.updateIsTimeoutFree(timeOutOp.getId(),
                        timeOutOp.getIsTimeoutFree());
            }
        }
    }

    private void insertOperateLog(OrdersNew orders, String user,
                                  List<OrderProductsNew> orderProductsList) {
        OrderOperateLogs log = null;
        List<OrderOperateLogs> orderOperateLogsList = new ArrayList<OrderOperateLogs>();
        String remark = "订单状态从“未确认”改为“已确认”。";
        if (OrderStatus.OS_NOSTOCK.getCode().intValue() == orders.getOrderStatus().intValue()) {
            remark = "订单状态从“未确认”改为“部分缺货”。";
        }
        for (OrderProductsNew orderProducts : orderProductsList) {
            orderOperateLogsList
                    .add(this.constructOperateLog(orders, orderProducts, user, "确认订单成功", remark, log));
        }
        if (!orderOperateLogsList.isEmpty()) {
            orderOperateLogsService.batchInsert(orderOperateLogsList);
        }
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

    private String constructSmsMsg(OrdersNew orders, OrderProductsNew orderProducts) {
        String str = "";
        //        StringBuilder sb = new StringBuilder();
        //        if (orders.getSource() != null && orders.getSource().equalsIgnoreCase("MSTORE")) {
        //            sb.append("【顺逛微店】");
        //        } else {
        //            sb.append("【海尔商城】");
        //        }
        //        sb.append(orders.getConsignee());
        Integer typeId = orderProducts.getProductType();
        String typeName = "";
        if (typeId.intValue() > 0) {
            ServiceResult<ProductTypesNew> result = itemService.getProductType(typeId);
            if (result.getSuccess() && null != result.getResult()) {
                typeName = result.getResult().getTypeName();
            }
        }
        if (StringUtils.isBlank(typeName)) {
            typeName = orderProducts.getSku();
        }
        if ("TSMOBILE".equals(orders.getSource()) || "TSPC".equals(orders.getSource())) {
            OrderWorkflows orderWorkflows = orderWorkflowsService
                    .getByOrderProductId(orderProducts.getId());
            //计算应送达用户时间
            Long mustUserAcceptTime = countTimePubModel.countMustUserAcceptTime(orders,
                    orderProducts, orderWorkflows.getMustUserAcceptTime());
            if (null != orderWorkflows && ("B2B2C".equals(orderProducts.getShippingMode())
                    || "".equals(orderProducts.getShippingMode()))
                    && mustUserAcceptTime > 0) {
                int day = Math
                        .round((mustUserAcceptTime - System.currentTimeMillis() / 1000L) / 86400L);
                //                sb.append("您好，您购买的");
                //                sb.append(typeName);
                //                sb.append("已下单成功");
                MemberInvoices memberInvoices = memberInvoicesService.getByOrderId(Integer.valueOf(orders.getId()));
                if (null != memberInvoices && memberInvoices.getElectricFlag().intValue() == 1) {
                    //                    sb.append("，发票将以电子发.票（短信和邮件）的形式发送，请您注意查收！电子发.票同样保障您的法律权益和售后维修权益，关于电子发.票具体详情可到青岛税务局官网和统帅商城官网查询，也可联系在线客服进行咨询！统帅商城");
                    str = SmsTemplateConst.ConfirmOrder_TSSC_B2B2C_1
                            .replaceAll("#consignee#", orders.getConsignee())
                            .replaceAll("#typeName#", typeName);
                } else {
                    //                    sb.append("，您可在用户中心查看，如有需要请联系客服！统帅商城");
                    str = SmsTemplateConst.ConfirmOrder_TSSC_B2B2C_0
                            .replaceAll("#consignee#", orders.getConsignee())
                            .replaceAll("#typeName#", typeName);
                }
            } else {
                return "";
            }
        } else if (OrderType.TYPE_GROUP_ADVANCE.getIntValue() == orders.getOrderType().intValue()
                || OrderType.TYPE_GROUP_ADVANCE_TAIL.getIntValue() == orders.getOrderType()
                .intValue()) {
            //            sb.append("您好，您购买的");
            //            sb.append(typeName);
            //            sb.append("已成功支付定金。如有需要请联系客服或拨打4008281919！海尔商城");

            //寇瑞茂 2015-10-13去掉商城和天猫定金发短信业务，电话已不存在
            return "";
        } else if (orders.getIsProduceDaily().intValue() == 1 && "TB".equals(
                orderThirdCenterStockCommonService.getChannelCodeByOrderSource(orders.getSource()).getResult())) {
            //            sb.append("您好，您定制的");
            //            sb.append(typeName);
            //            sb.append("已下单成功，我们会尽快安排工厂生产和发货，到货后将会及时通知您，如有需要请联系天猫客服。");
            str = SmsTemplateConst.ConfirmOrder_TBSC
                    .replaceAll("#consignee#", orders.getConsignee())
                    .replaceAll("#typeName#", typeName);
        } else {
            OrderWorkflows orderWorkflows = orderWorkflowsService
                    .getByOrderProductId(orderProducts.getId());
            //计算应送达用户时间
            Long mustUserAcceptTime = countTimePubModel.countMustUserAcceptTime(orders,
                    orderProducts, orderWorkflows.getMustUserAcceptTime());
            if (null != orderWorkflows && ("B2B2C".equals(orderProducts.getShippingMode())
                    || "".equals(orderProducts.getShippingMode()))
                    && mustUserAcceptTime > 0) {
                int day = Math
                        .round((mustUserAcceptTime - System.currentTimeMillis() / 1000L) / 86400L);
                //                sb.append("您好，您购买的");
                //                sb.append(typeName);
                //                sb.append("已下单成功，预计");
                //                sb.append(day);
                //                MemberInvoices memberInvoices = memberInvoicesService.getByOrderId(orders.getId());
                //                if (null != memberInvoices && memberInvoices.getElectricFlag().intValue() == 1) {
                //                    sb.append("天送达，发票将以电子发.票（短信和邮件）的形式发送，请您注意查收！电子发.票同样保障您的法律权益和售后维修权益，关于电子发.票具体详情可到青岛税务局官网和海尔商城官网查询，也可联系在线客服进行咨询！海尔商城");
                //                } else {
                //                    sb.append("天送达，您可在用户中心查看，如有需要请联系客服或拨打4008281919！海尔商城");
                //                }
                //                sb.append("已下单成功");
                MemberInvoices memberInvoices = memberInvoicesService.getByOrderId(Integer.valueOf(orders.getId()));
                if (null != memberInvoices && memberInvoices.getElectricFlag().intValue() == 1) {
                    if (orders.getSource() != null
                            && orders.getSource().equalsIgnoreCase("MSTORE")) {
                        //                        sb.append("，发票将以电子发.票（短信和邮件）的形式发送，请注意查收！电子发.票同样保障您的法律权益和售后维修权益，详情可到青岛税务局官网和顺逛微店公众号查询，也可联系在线客服！");
                        str = SmsTemplateConst.ConfirmOrder_MSTORE_1
                                .replaceAll("#consignee#", orders.getConsignee())
                                .replaceAll("#typeName#", typeName);
                    } else {
                        //                        sb.append("，发票将以电子发.票（短信和邮件）的形式发送，请您注意查收！电子发.票同样保障您的法律权益和售后维修权益，关于电子发.票具体详情可到青岛税务局官网和海尔商城官网查询，也可联系在线客服进行咨询！海尔商城");
                        str = SmsTemplateConst.ConfirmOrder_HESC_1
                                .replaceAll("#consignee#", orders.getConsignee())
                                .replaceAll("#typeName#", typeName);
                    }
                } else {
                    if (orders.getSource() != null
                            && orders.getSource().equalsIgnoreCase("MSTORE")) {
                        //                        sb.append("，您可在订单管理或顺逛微店公众号查看，如有需要请联系在线客服！");
                        str = SmsTemplateConst.ConfirmOrder_MSTORE_0
                                .replaceAll("#consignee#", orders.getConsignee())
                                .replaceAll("#typeName#", typeName);
                    } else {
                        //                        sb.append("，您可在用户中心查看，如有需要请联系客服或拨打4008281919！海尔商城");
                        str = SmsTemplateConst.ConfirmOrder_HESC_0
                                .replaceAll("#consignee#", orders.getConsignee())
                                .replaceAll("#typeName#", typeName);
                    }
                }
            } else {
                return "";
            }
        }
        //        return sb.toString();
        return str;
    }

    /**
     * 插入 o2o确认订单成功队列表
     *
     * @author XinM
     */
    private void insertO2OOrderConfirmQueues(List<OrderProductsNew> list,
                                             Map<Integer, Integer> productsSelfMap,
                                             Set<Integer> scfXorderProductId) {
        if (list == null || list.size() == 0) {
            log.info("[正向单订单确认后推送结算中心]订单确认后推送队列：没有需要处理的记录。");
            return;
        }
        O2OOrderConfirmQueues o2oOrderConfirmQueues;
        for (OrderProductsNew orderProducts : list) {
            try {
                if (orderProducts.getStatus().equals(OrderProductsNew.STATUS_CANCEL_CLOSE)) {
                    log.info("网单号[" + orderProducts.getCOrderSn() + "]在写o2o确认订单成功队列表时，此网单已取消");
                    continue;
                }
                o2oOrderConfirmQueues = accountCenterService
                        .getForwardConfirmToAccountCenterByOrderProductId(orderProducts.getId());
                if (o2oOrderConfirmQueues != null) {
                    log.info("网单号[" + orderProducts.getCOrderSn() + "]在o2o确认订单成功队列表中已存在");
                    continue;
                }
                if (productsSelfMap != null && !productsSelfMap.isEmpty()
                        && productsSelfMap.containsKey(orderProducts.getId())) {//自营转单
                    insertoConfirmQueues(orderProducts, o2oOrderConfirmQueues);
                } else if (scfXorderProductId != null
                        && scfXorderProductId.contains(orderProducts.getId())) {//商城分销
                    insertoConfirmQueues(orderProducts, o2oOrderConfirmQueues);
                } else {
                    if (orderProducts.getO2oType().intValue() == 2
                            || orderProducts.getO2oType().intValue() == 3
                            || orderProducts.getO2oType().intValue() == 4
                            || orderProducts.getO2oType().intValue() == 5
                            || orderProducts.getO2oType().intValue() == 50
                            || orderProducts.getO2oType().intValue() == 60) {
                        insertoConfirmQueues(orderProducts, o2oOrderConfirmQueues);
                    }
                }
            } catch (Exception e) {
                log.error("O2O网单号[" + orderProducts.getCOrderSn() + "]异常：", e);
            }
        }
    }

    /**
     * 往结算中心推送数据
     *
     * @param orderProducts
     * @param o2oOrderConfirmQueues
     */
    private void insertoConfirmQueues(OrderProductsNew orderProducts,
                                      O2OOrderConfirmQueues o2oOrderConfirmQueues) {
        o2oOrderConfirmQueues = new O2OOrderConfirmQueues();

        o2oOrderConfirmQueues.setOrderId(orderProducts.getOrderId());
        o2oOrderConfirmQueues.setOrderProductId(orderProducts.getId());
        o2oOrderConfirmQueues.setAddTime((new Date().getTime() / 1000));
        o2oOrderConfirmQueues.setSuccess(0);
        o2oOrderConfirmQueues.setCount(0);
        o2oOrderConfirmQueues.setPushData("");
        o2oOrderConfirmQueues.setReturnData("");
        o2oOrderConfirmQueues.setLastTryTime(0L);
        o2oOrderConfirmQueues.setSuccessTime(0L);
        o2oOrderConfirmQueues.setLastMessage("");

        int t = accountCenterService.insertForwardConfirmToAccountCenterList(o2oOrderConfirmQueues);
        if (t <= 0) {
            log.error("O2O网单号[" + orderProducts.getCOrderSn() + "]异常：插入正向队列表失败");
        }
    }

    private class ExecuteConfirmStock implements IExcute {

        @SuppressWarnings("unchecked")
        @Override
        public void execute(Object obj) {
            ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
            try {
                List<OrdersNew> unConfirmOrderList = (List<OrdersNew>) obj;
                if (null == unConfirmOrderList || unConfirmOrderList.isEmpty()) {
                    return;
                }
                confirmOrders(unConfirmOrderList, "系统");
            } finally {
                ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
            }
        }
    }

    /**
     * 判断是否是自营网单，是则进行转单
     * 标记转单条件： ①符合区县+品牌+品类+客户的转单关系；
     * ②非B2C订单、库存类型为WA；
     * ③一个订单有多个网单时，有多少网单符合条件就标记多少网单
     *
     * @param orders
     * @param orderProductsList
     */
    private Map<Integer, Integer> selfSellOrders(OrdersNew orders,
                                                 List<OrderProductsNew> orderProductsList) {
        Map<Integer, Integer> productsMap = new HashMap<Integer, Integer>();
        if (orderProductsList != null && !orderProductsList.isEmpty()) {
            for (OrderProductsNew orderProducts : orderProductsList) {
                try {
                    ChangeOrderConfig config = null;
                    if (StringUtils.isNotEmpty(orders.getSource()) && orders.getRegion() != null
                            && orderProducts.getBrandId() != null
                            && orderProducts.getCateId() != null) {
                        config = changeOrderConfigService.getBySourceAndBrandAndCateAndregion(
                                orders.getSource(), orders.getRegion(), orderProducts.getBrandId(),
                                orderProducts.getCateId());
                        //判断条件1：订单中的条件符合转单关系表的对应关系//判断条件2：非B2C订单、库存类型为WA的订单
                        if (config != null && config.getCustomerId() != null
                                && !"B2C".equalsIgnoreCase(orderProducts.getShippingMode())
                                && OrderProductsNew.TYPE_WA
                                .equalsIgnoreCase(orderProducts.getStockType())) {

                            //2018-06-26 网单扩展属性表不用了 相关逻辑删除 start
                            /*OrderProductsAttributes attribute = orderProductsAttributesService
                                    .getByOrderProductId(orderProducts.getId());
                            if (attribute != null) {
                                productsMap.put(orderProducts.getId(), config.getCustomerId());
                                attribute.setIsSelfSell(1);//自营
                                attribute.setCustomerId(config.getCustomerId());//客户id
                                attribute.setIsDispatching(config.getIsDispatching());//是否指定派工  0不指定  1指定 默认0
                                orderProductsAttributesService.update(attribute);
                            }*/
                            //2018-06-26 网单扩展属性表不用了 相关逻辑删除 end
                        }
                    }
                } catch (Exception e) {
                    log.error("网单号[" + orderProducts.getId() + "]异常：", e);
                    throw new BusinessException("网单号[" + orderProducts.getId() + "]判断是否是自营转单出现异常");
                }
            }
        }
        return productsMap;
    }

    private Set<Integer> getSCFXorderProductId(OrdersNew orders,
                                               List<OrderProductsNew> orderProductsList) {
        Set<Integer> set = new HashSet<Integer>();
        if (orders != null && orderProductsList != null && !orderProductsList.isEmpty()) {
            if (orders.getSource() == null || !orders.getSource().equals("SCFX")) {
                return null;
            }
            for (OrderProductsNew orderProducts : orderProductsList) {
                if (orderProducts.getStockType() != null
                        && orderProducts.getStockType().equals("WA")) {
                    set.add(orderProducts.getId());
                }
            }
        }
        return set;
    }

    private void updateHpNoticeQueues(OrdersNew orders, List<OrderProductsNew> orderProductsList) {
        //定金尾款订单，并且已支付
        try {
            if ((orders.getOrderType().intValue() == 1 || orders.getOrderType().intValue() == 4)
                    && orders.getTailPayTime() > 0) {
                for (OrderProductsNew op : orderProductsList) {
                    hpNoticeQueuesService.updateQueuesBySuccessAndOrderProId(op.getId());
                }
            }
        } catch (Exception e) {
            log.error("O2O订单号[" + orders.getId() + "]异常：", e);
            throw new BusinessException("把HpNoticeQueues推送次数大于等于30的定金尾款订单推送次数更新为0时出现异常");
        }
    }

   /* public void setordersNewService(OrdersNewDao ordersNewService) {
        this.ordersNewService = ordersNewService;
    }*/

    /*public void setorderWorkflowsService(orderWorkflowsService orderWorkflowsService) {
        this.orderWorkflowsService = orderWorkflowsService;
    }

    public void setmembersService(membersService membersService) {
        this.membersService = membersService;
    }

    public void setOrderThirdCenterItemService(ItemServiceImplByHwl itemService) {
        this.itemService = itemService;
    }*/

   /* public void setorderProductsNewService(OrderProductsNewDao orderProductsNewService) {
        this.orderProductsNewService = orderProductsNewService;
    }

    public void setmemberInvoicesService(memberInvoicesService memberInvoicesService) {
        this.memberInvoicesService = memberInvoicesService;
    }

    public void sethpQueuesService(hpQueuesService hpQueuesService) {
        this.hpQueuesService = hpQueuesService;
    }*/

    /*public void setorderOperateLogsService(orderOperateLogsService orderOperateLogsService) {
        this.orderOperateLogsService = orderOperateLogsService;
    }

    public void setStockCommonService(OrderThirdCenterStockCommonService orderThirdCenterStockCommonService) {
        this.orderThirdCenterStockCommonService = orderThirdCenterStockCommonService;
    }

    public void setsmsLogsWriteService(smsLogsWriteService smsLogsWriteService) {
        this.smsLogsWriteService = smsLogsWriteService;
    }*/

   /* public void setTransactionProxy(TransactionProxy transactionProxy) {
        this.transactionProxy = transactionProxy;
    }

    public void setThreadHelper(ThreadHelper threadHelper) {
        this.threadHelper = threadHelper;
    }

    public void setCountTimePubModel(CountTimePubModel countTimePubModel) {
        this.countTimePubModel = countTimePubModel;
    }

    public void setlesQueuesService(lesQueuesService lesQueuesService) {
        this.lesQueuesService = lesQueuesService;
    }*/

    /*public void setEcQueuesDao(EcQueuesDao ecQueuesDao) {
        this.ecQueuesDao = ecQueuesDao;
    }

    public void setaccountCenterService(accountCenterService accountCenterService) {
        this.accountCenterService = accountCenterService;
    }

    public void setOrderPriceSourceChannelDao(OrderPriceSourceChannelDao orderPriceSourceChannelDao) {
        this.orderPriceSourceChannelDao = orderPriceSourceChannelDao;
    }

    public void setOrderGuaranteePriceInfoDao(OrderGuaranteePriceInfoDao orderGuaranteePriceInfoDao) {
        this.orderGuaranteePriceInfoDao = orderGuaranteePriceInfoDao;
    }*/

   /* public void setorderPriceGateService(orderPriceGateService orderPriceGateService) {
        this.orderPriceGateService = orderPriceGateService;
    }

    public void setorderCouponsService(orderCouponsService orderCouponsService) {
        this.orderCouponsService = orderCouponsService;
    }*/

   /* public void setqueueMailsWriteService(queueMailsWriteService queueMailsWriteService) {
        this.queueMailsWriteService = queueMailsWriteService;
    }*/

   /* public void setcostPoolsService(costPoolsService costPoolsService) {
        this.costPoolsService = costPoolsService;
    }*/

   /* public void setorderPriceCostPoolUseInfoService(orderPriceCostPoolUseInfoService orderPriceCostPoolUseInfoService) {
        this.orderPriceCostPoolUseInfoService = orderPriceCostPoolUseInfoService;
    }

    public void setOrderPriceProductGroupIndustryDao(OrderPriceProductGroupIndustryDao orderPriceProductGroupIndustryDao) {
        this.orderPriceProductGroupIndustryDao = orderPriceProductGroupIndustryDao;
    }*/

    /*public void setOrderPriceSourceIndustryDao(OrderPriceSourceIndustryDao orderPriceSourceIndustryDao) {
        this.orderPriceSourceIndustryDao = orderPriceSourceIndustryDao;
    }*/

   /* public void setitemBaseNewService(ItemBaseNewDao itemBaseNewService) {
        this.itemBaseNewService = itemBaseNewService;
    }*/

    /*public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setchangeOrderConfigService(changeOrderConfigService changeOrderConfigService) {
        this.changeOrderConfigService = changeOrderConfigService;
    }

    public void setorderProductsAttributesService(orderProductsAttributesService orderProductsAttributesService) {
        this.orderProductsAttributesService = orderProductsAttributesService;
    }

    public void sethpNoticeQueuesService(hpNoticeQueuesService hpNoticeQueuesService) {
        this.hpNoticeQueuesService = hpNoticeQueuesService;
    }*/

    /*public OrderMsgQueueDao getOrderMsgQueueDao() {
        return orderMsgQueueDao;
    }

    public void setOrderMsgQueueDao(OrderMsgQueueDao orderMsgQueueDao) {
        this.orderMsgQueueDao = orderMsgQueueDao;
    }
*/
}