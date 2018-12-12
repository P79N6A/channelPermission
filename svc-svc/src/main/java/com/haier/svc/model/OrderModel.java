//package com.haier.svc.model;
//
//import com.haier.common.BusinessException;
//import com.haier.common.ServiceResult;
//import com.haier.common.util.DateUtil;
//import com.haier.common.util.JsonUtil;
//import com.haier.common.util.StringUtil;
//import com.haier.distribute.data.model.ProductCates;
//import com.haier.shop.model.BenefitTypeReqVO;
//import com.haier.shop.model.CmtCommentOrderProducts;
//import com.haier.shop.model.HpReservationDateLogs;
//import com.haier.shop.model.HpSignTimeInterface;
//import com.haier.shop.model.InvoiceQueue;
//import com.haier.shop.model.InvoicesReady;
//import com.haier.shop.model.NetPoints;
//import com.haier.shop.model.OrderOperateLogs;
//import com.haier.shop.model.OrderProductsAttributes;
//import com.haier.shop.model.OrderProductsNew;
//import com.haier.shop.model.OrderRepairHPRecordsNew;
//import com.haier.shop.model.OrderRepairsNew;
//import com.haier.shop.model.OrderShippedQueue;
//import com.haier.shop.model.OrderType;
//import com.haier.shop.model.OrderWorkflowRegion;
//import com.haier.shop.model.OrderWorkflows;
//import com.haier.shop.model.OrdersAttributes;
//import com.haier.shop.model.OrdersNew;
//import com.haier.shop.model.ProductsNew;
//import com.haier.shop.service.*;
//import com.haier.stock.model.OrderProductStatus;
//import com.haier.stock.model.OrderStatus;
//import com.haier.svc.helper.ReadWriteRoutingDataSourceHolder;
//import com.haier.svc.service.ItemService;
//import com.haier.svc.service.PayCenterFallBackService;
//import com.haier.svc.util.OrderSnUtil;
//import com.haier.svc.util.PayCenterJsonUtils;
//import com.haier.svc.util.SignUtil;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.support.DefaultTransactionDefinition;
//import org.springframework.util.Assert;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import java.math.BigDecimal;
//import java.net.URLDecoder;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//@Configuration
//public class OrderModel {
//    @Autowired(required=false)
//    private OrdersNewService ordersNewDao;
//    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
//            .getLogger(OrderModel.class);
//    @Autowired(required=false)
//    private OrderProductsNewService orderProductsNewDao;
//    @Autowired(required=false)
//    private OrderProductsAttributesService orderProductsAttributesDao;
//    @Autowired(required=false)
//    private OrdersAttributesService ordersAttributesDao;
//    @Autowired(required=false)
//    private ItemService itemService;
//    @Autowired(required=false)
//    private OrderWorkflowRegionService orderWorkflowRegionDao;
////    @Autowired(required=false)
////    private DataSourceTransactionManager transactionManager;
//    @Autowired(required=false)
//    private ShopOrderOperateLogsService orderOperateLogsDao;
//    @Autowired(required=false)
//    private ShopOrderWorkflowsService orderWorkflowsDao;
//    @Autowired(required=false)
//    private OrderShippedQueueService orderShippedQueueDao;
//    @Autowired(required=false)
//    private InvoiceQueueService invoiceQueueDao;
//    @Autowired(required=false)
//    private InvoicesReadyService invoicesReadyDao;
//    @Autowired(required=false)
//    private OrderRepairsNewService orderRepairsNewDao;
//    @Autowired(required=false)
//    private HpReservationDateLogsService hpReservationDateLogsDao;
//    @Autowired(required=false)
//    private HpSignTimeInterfaceService hpSignTimeInterfaceDao;
//    @Autowired(required=false)
//    private CmtCommentOrderProductsService cmtCommentOrderProductsDao;
//    private PayCenterFallBackService payCenterFallBackService;
//    @Autowired(required=false)
//    private OrderRepairHPRecordsnNewService orderRepairHPRecordsDao;
//    private static final String RAS_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALEOFaUEqEG7cfWcv0SLpHlQmr3qIiFH3ttwBVmnffwF/9xVp2zAZCbgOFDPu+tCedXgEWD13d2W6xG/qp8vhe+Oqo93+J19LnajRyjyGkmDH+pzCn5aS7D7SZ/j1cIu2CuNQNwj9IFmGEEASgimRhHeFnGGfY7PzZqI3XRlzbr/AgMBAAECgYBKTKzMmQ26t9x0w5iIPUmCF084jz5PVQeycmnsW5tE3YengNJHktz0a3d2ghZL/ZN/Kw5f8A1w5dozkokZUCoV0Mpd63JE0BZF9LQH7vJpHSC8MaPHntXB4di9O2//A8KrWDzz8f0lNcnw9vxvDzL3VnAUIiIaFkCwEhcToOJwsQJBANZR47SMc+KziSmg+F8d7dR6mFCczmrB1BEMcASEf45Ij6JFwuU67s1Z5ZmTSH2sFWGJKUEUy20v3oXVyBwCkaMCQQDTfO4PRAWwMidwXAhyH7X/KjuRQTMtuXpl/3ZRF5AAQSD7h0gYRV+1SoQgT4UXQBlnbV4k2KEzkHBiDuYhj971AkAaPykdwV2n08jmejowm9+2d9UTekClPluUQuutAfUFHcnJW7XEkPUR3QKLTkhAa8SqjzuoJr3K/2PHDClXlND1AkB3uZjXYY3K0onLLP7HBLa2TkVMlNmRQBhPl9B2Vd16l2RBoLMqslNdQWMNG5dfszTufVa3iz+u/kzw5jhXtaflAkAb/IPuTy3afvI7f04alHqkSDRQuDoO0QP0dTCQbih0kFPItNGB/l0J86fbGYiF4Uiugdm1aGUJ9dwKR8uO2aMx";
//    @SuppressWarnings("serial")
//    private static Map<Integer, String> orderStatusMap = new HashMap<Integer, String>() {
//
//        {
//            put(OrderProductsNew.STATUS_STRAT_STATUS, "初始状态");
//            put(OrderProductsNew.STATUS_SYNC_HP, "同步到HP");
//            put(OrderProductsNew.STATUS_DISPATCH_NETPOINT, "已分配网点");
//
//            put(OrderProductsNew.STATUS_LES_SHIPPING, "LES 开提单, 待出库");
//            put(OrderProductsNew.STATUS_WAIT_VERIFY, "待审核");
//            put(OrderProductsNew.STATUS_WAIT_TRANSSHIPIN, "待转运入库");
//            put(OrderProductsNew.STATUS_WAIT_TRANSSHIPOUT, "待转运出库");
//            put(OrderProductsNew.STATUS_WAIT_DELIVERY, "待发货");
//            put(OrderProductsNew.STATUS_NETPOINT_REFUSE, "网点拒单");
//            put(OrderProductsNew.STATUS_WAIT_DELIVER, "待交付");
//            put(OrderProductsNew.STATUS_COMPLETED_CLOSE, "完成关闭");
//            put(OrderProductsNew.STATUS_USER_SIGN, "用户签收");
//            put(OrderProductsNew.STATUS_USER_REJECTION, "用户拒收");
//            put(170, "用户取消");
//            put(180, "无法执行");
//            put(OrderProductsNew.STATUS_CANCEL_CLOSE, "取消关闭");
//        }
//    };
//    //区分签收人 用于日志的展示
//    static Map<String, String> oprMap = new HashMap<String, String>();
//
//    static {
//        oprMap.put("SG-WDZ", "顺逛微店主");
//        oprMap.put("WDZ", "顺逛微店主");
//        oprMap.put("YD-WDZ", "移动微店主");
//        oprMap.put("YD-YH", "移动用户");
//        oprMap.put("PC-YH", "PC用户");
//    }
//    @SuppressWarnings("serial")
//    /**
//     * 根据编号列表，获取网单列表
//     * @param snList 网单编号列表
//     * @return
//     */
//    public ServiceResult<List<OrderProductsNew>> getOrderLineBySnList(List<String> snList){
//        //7
//        ServiceResult<List<OrderProductsNew>> result = new ServiceResult<List<OrderProductsNew>>();
//        //验证数据
//        if (snList == null || snList.size() == 0 || snList.size() > 1000) {
//            result.setMessage("网单编号列表不能为空，且不能多于1000个");
//            result.setSuccess(false);
//            return result;
//        }
//        //获取列表
//        try {
//            result.setResult(orderProductsNewDao.getByCOrderSnList(snList));
//            result.setSuccess(true);
//            return result;
//        } catch (Exception ex) {
//            String sns = "";
//            if (snList != null) {
//                for (String s : snList) {
//                    if (!StringUtil.isEmpty(sns)) {
//                        sns += ";";
//                    }
//                    sns += s;
//                }
//            }
//            log.error("根据网单编号列表(" + sns + ")获取网单列表时，出现未知异常：", ex);
//            result.setMessage("出现未知异常：" + ex.getMessage());
//            result.setSuccess(false);
//            return result;
//        }
//    }
//    /**
//     * 根据id列表，获取订单列表
//     * @param ids id列表
//     * @return
//     */
//    public List<OrdersNew> getOrderByIds(List<Integer> ids) {
//        return ordersNewDao.getByIds(ids);
//    }
//    /**
//     * LES出入库后，商城相关处理
//     * @param order 订单
//     * @param orderProduct 网单
//     * @param iType 出入库类型[0:第一次出库,1:转运入库,2:转运第二次出库]
//     * @param lesNo 出入库凭证号
//     * @param lesShipTime 出入库时间
//     * @param expressNo 快递单号
//     * @param expressCompony 快递公司
//     * @param cainiaoMap Map<String, String>类型，菜鸟出库使用。必传：<"lbxSn",菜鸟物流单号>,<"sCode",库位编码>,<"netPointN8",网点8码>
//     *        cainiaoMap新增 <"sourceOrderSn",来源订单号（外部订单号）>,<"oid",天猫子订单号>,<"tbOrderSn",外部TB单号（VOM单号）><"sku",物料编码>
//     *        [XinM 2016-9-27 仅菜鸟出库使用]
//     * @return
//     */
//    public ServiceResult<Boolean> processAfterLesShipped(OrdersNew order, OrderProductsNew orderProduct,
//                                                         Integer iType, String lesNo,
//                                                         Date lesShipTime, String expressNo,
//                                                         String expressCompony,
//                                                         Map<String, String> cainiaoMap) {
//        String outstrType = "LES";
//        if (orderProduct.getStockType() != null
//                && orderProduct.getStockType().equalsIgnoreCase("3W")) {
//            outstrType = "CAINIAO";
//        }
//        if (iType == 0) {//3W单子都走普通出库，不走转运出库
//            if (StringUtil.isEmpty(orderProduct.getTsCode())
//                    || orderProduct.getStockType().equalsIgnoreCase("3W")) {//普通出库
//                return this.deliveryOrderLine(order, orderProduct, lesNo, lesShipTime, expressNo,
//                        expressCompony, outstrType, cainiaoMap);
//            } else {//转运第一次出库
//                return this.transferOrderLineFirstOut(order, orderProduct, lesNo, lesShipTime);
//            }
//        }
//        if (iType == 1) {//转运入库
//            return this.transferOrderLineIn(order, orderProduct, lesNo, lesShipTime);
//        }
//        if (iType == 2) {//转运第二次出库
//            return this.transferOrderLineSecondOut(order, orderProduct, lesNo, lesShipTime,
//                    expressNo, expressCompony);
//        }
//
//        //类型超出范围
//        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
//        result.setResult(false);
//        result.setMessage("iType类型(" + iType + ")超出本程序的IQ范围，请升级系统或者提交更低级的任务");
//        return result;
//    }
//    /**
//     * 网单出库（默认开发票）
//     * @param order 订单
//     * @param orderProduct 网单
//     * @param lesDoNo 出库凭证
//     * @param lesShipTime 出库时间
//     * @param expressNo 快递单号
//     * @param expressCompony 快递公司
//     * @param cainiaoMap Map<String, String>类型，菜鸟出库使用。必传：<"lbxSn",菜鸟物流单号>,<"sCode",库位编码>,<"netPointN8",网点8码>
//     *        cainiaoMap新增 <"sourceOrderSn",来源订单号（外部订单号）>,<"oid",天猫子订单号>,<"tbOrderSn",外部TB单号（VOM单号）><"sku",物料编码>
//     *        [XinM 2016-9-27 仅菜鸟出库使用]
//     * @return
//     */
//    public ServiceResult<Boolean> deliveryOrderLine(OrdersNew order, OrderProductsNew orderProduct,
//                                                    String lesDoNo, Date lesShipTime,
//                                                    String expressNo, String expressCompony,
//                                                    String outstrType,
//                                                    Map<String, String> cainiaoMap) {
//        return deliveryOrderLine(order, orderProduct, lesDoNo, lesShipTime, expressNo, expressCompony, outstrType, cainiaoMap,true, false);
//    }
//    /**
//     * 网单出库
//     * @param order 订单
//     * @param orderProduct 网单
//     * @param lesDoNo 出库凭证
//     * @param lesShipTime 出库时间
//     * @param expressNo 快递单号
//     * @param expressCompony 快递公司
//     * @param cainiaoMap Map<String, String>类型，菜鸟出库使用。必传：<"lbxSn",菜鸟物流单号>,<"sCode",库位编码>,<"netPointN8",网点8码>
//     *        cainiaoMap新增 <"sourceOrderSn",来源订单号（外部订单号）>,<"oid",天猫子订单号>,<"tbOrderSn",外部TB单号（VOM单号）><"sku",物料编码>
//     *        [XinM 2016-9-27 仅菜鸟出库使用]
//     *
//     * @return
//     */
//    public ServiceResult<Boolean> deliveryOrderLine(OrdersNew order, OrderProductsNew orderProduct,
//                                                    String lesDoNo, Date lesShipTime,
//                                                    String expressNo, String expressCompony,
//                                                    String outstrType,
//                                                    Map<String, String> cainiaoMap,
//                                                    boolean invoicing, boolean toOutping) {
//        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
//        //曾经解锁的库存数量 - 没有出库过，且曾经解锁的库存数量为零时才更新
//        if (StringUtil.isEmpty(orderProduct.getOutping()) && orderProduct.getUnlockedNumber() == 0
//                && !orderProduct.getStockType().equalsIgnoreCase("3W")) {
//            orderProduct.setUnlockedNumber(orderProduct.getNumber());
//        } else {
//            orderProduct.setUnlockedNumber(0);//由于sql中是增量的，所以要赋值
//        }
//        //出库单
//        orderProduct.setOutping(lesDoNo);
//        //出库时间
//        orderProduct.setLessShipTime(lesShipTime.getTime() / 1000);
//        //运单号
//        orderProduct.setInvoiceNumber(expressNo);
//        //快递公司
//        orderProduct.setExpressName(expressCompony);
//        //网单状态 - 未关闭时才更新
//        if (orderProduct.getStatus() < OrderProductsNew.STATUS_WAIT_VERIFY) {
//            orderProduct.setStatus(OrderProductsNew.STATUS_WAIT_VERIFY);
//        } else {
//            //            orderProduct.setSystemRemark(null);//由于sql中是增量的，故要赋值
//        }
//        orderProduct.setSystemRemark(null);
//
//        //3W-更新'订单扩展属性表'，仅3W菜鸟出库时候 XinM 2016-9-27
//        //3W-更新'网单扩展属性表'，仅3W菜鸟出库时候 XinM 2016-9-28
//        OrdersAttributes ordersAttributes = null;
//        //'网单扩展属性表'
//        OrderProductsAttributes orderProductsAttributes = orderProductsAttributesDao
//                .getByOrderProductId(orderProduct.getId());
//        if (orderProduct.getStockType() != null
//                && orderProduct.getStockType().equalsIgnoreCase("3W")) {
//            //'订单扩展属性表'
//            ordersAttributes = ordersAttributesDao.getByOrderId(Integer.valueOf(order.getId()));
//            if (ordersAttributes == null) {
//                log.error("CaiNiao网单出库：lbxSn[" + cainiaoMap.get("lbxSn") + "],order.id["
//                        + order.getId() + "]没有查到[订单扩展属性信息]");
//                result.setResult(false);
//                result.setMessage("CaiNiao网单出库：order.id[" + order.getId() + "]在[订单扩展属性表]没有查到");
//                return result;
//            }
//            ordersAttributes.setLbx(cainiaoMap.get("lbxSn"));
//
//            if (orderProductsAttributes == null) {
//                log.error("CaiNiao网单出库：lbxSn[" + cainiaoMap.get("lbxSn") + "],orderProduct.id["
//                        + orderProduct.getId() + "]没有查到[网单扩展属性信息]");
//                result.setResult(false);
//                result.setMessage(
//                        "CaiNiao网单出库：orderProduct.id[" + orderProduct.getId() + "]在[网单扩展属性表]没有查到");
//                return result;
//            }
//            orderProductsAttributes.setTbOrderSn(cainiaoMap.get("tbOrderSn"));
//
//            //更新网单相关数据（3W订单没有HP相关业务逻辑，导致网单有的没有数据，但后续业务需要）
//            orderProduct.setSCode(cainiaoMap.get("sCode"));
//            if (cainiaoMap.get("netPointN8") != null && !"".equals(cainiaoMap.get("netPointN8"))) {
//                //3W直配商品，不经过网点。经过网点的netPointN8，是 8开头的一串数字。
//                if (cainiaoMap.get("netPointN8").startsWith("8")) {
//                    ServiceResult<NetPoints> resultNetPoints = itemService
//                            .getByNetPointN8(cainiaoMap.get("netPointN8"));
//                    if (resultNetPoints == null) {
//                        log.error("CaiNiao网单出库：lbxSn[" + cainiaoMap.get("lbxSn") + "],netPointN8["
//                                + cainiaoMap.get("netPointN8") + "]调用itemService查询网点表返回为null");
//                        throw new BusinessException("CaiNiao网单出库：lbxSn[" + cainiaoMap.get("lbxSn")
//                                + "],netPointN8[" + cainiaoMap.get("netPointN8")
//                                + "]调用itemService查询网点表返回为null");
//                    } else if (!resultNetPoints.getSuccess()) {
//                        log.error("CaiNiao网单出库：lbxSn[" + cainiaoMap.get("lbxSn") + "],netPointN8["
//                                + cainiaoMap.get("netPointN8")
//                                + "]调用itemService查询网点表返回false!errorMessage："
//                                + resultNetPoints.getMessage());
//                        throw new BusinessException("CaiNiao网单出库：lbxSn[" + cainiaoMap.get("lbxSn")
//                                + "],netPointN8[" + cainiaoMap.get("netPointN8")
//                                + "]调用itemService查询网点表返回false!errorMessage："
//                                + resultNetPoints.getMessage());
//                    } else if (resultNetPoints.getResult() == null) {
//                        log.error("CaiNiao网单出库：lbxSn[" + cainiaoMap.get("lbxSn") + "],netPointN8["
//                                + cainiaoMap.get("netPointN8")
//                                + "]调用itemService查询网点表,返回result结果为null");
//                        throw new BusinessException("CaiNiao网单出库：lbxSn[" + cainiaoMap.get("lbxSn")
//                                + "],netPointN8[" + cainiaoMap.get("netPointN8")
//                                + "]调用itemService查询网点表,返回result结果为null");
//                    } else {
//                        orderProduct.setNetPointId(resultNetPoints.getResult().getId());
//                        //2017-05-31 3W物流不回传转运库位，导致报表不准确，增加按照网点8码匹配转运库位逻辑
//                        if (StringUtil.isEmpty(orderProduct.getTsCode())) {
//                            NetPoints np = resultNetPoints.getResult();
//                            if (!StringUtil.isEmpty(orderProduct.getSCode())
//                                    && !StringUtil.isEmpty(np.getTCCode())
//                                    && !np.getTCCode().substring(0, 2)
//                                    .equals(orderProduct.getSCode().substring(0, 2))) {
//                                if (OrderSnUtil.SCODE_MAP.containsKey(np.getTCCode())) {
//                                    orderProduct
//                                            .setTsCode(OrderSnUtil.SCODE_MAP.get(np.getTCCode()));
//                                } else {
//                                    OrderWorkflowRegion region = orderWorkflowRegionDao
//                                            .get(order.getRegion());
//                                    if (region != null && !region.getSecCode().substring(0, 2)
//                                            .equals(orderProduct.getSCode().substring(0, 2))) {
//                                        if (OrderSnUtil.SCODE_MAP
//                                                .containsKey(region.getSecCode())) {
//                                            orderProduct.setTsCode(
//                                                    OrderSnUtil.SCODE_MAP.get(region.getSecCode()));
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            } else {
//                throw new BusinessException("CaiNiao网单出库：网点8码不能空");
//            }
//            //3W－菜鸟出库时更新开提单节点相关信息（因3W网单没有开提单节点）
//            orderProduct.setWaitGetLesShippingInfo(1);
//            //3W－菜鸟出库时更新开提单节点相关信息（因3W网单没有开提单节点）
//            orderProduct.setLessOrderSn(orderProduct.getCOrderSn());
//
//            //            //2017-04-06 3W物流不回传转运库位，导致报表不准确，增加按照区县匹配转运库位逻辑
//            //            if (StringUtil.isEmpty(orderProduct.getTsCode())) {
//            //                OrderWorkflowRegion region = orderWorkflowRegionDao.get(order.getRegion());
//            //                if (region != null && !StringUtil.isEmpty(orderProduct.getSCode()) && !region
//            //                    .getSecCode().substring(0, 2).equals(orderProduct.getSCode().substring(0, 2))) {
//            //                    if (OrderSnUtil.SCODE_MAP
//            //                        .containsKey(region.getSecCode().substring(0, 2) + "WA")) {
//            //                        orderProduct.setTsCode(
//            //                            OrderSnUtil.SCODE_MAP.get(region.getSecCode().substring(0, 2) + "WA"));
//            //                    }
//            //                }
//            //            }
//        }
//
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);
//        try {
//            //3W-更新'订单扩展属性表'，仅3W菜鸟出库时候 XinM 2016-9-27
//            if (orderProduct.getStockType() != null
//                    && orderProduct.getStockType().equalsIgnoreCase("3W")) {
//                int n = ordersAttributesDao.update(ordersAttributes);
//                if (n < 1) {
//                    log.error("CaiNiao网单出库：lbxSn[" + cainiaoMap.get("lbxSn") + "]cOrderSn["
//                            + orderProduct.getCOrderSn() + "]跟新'订单扩展属性表'失败！");
//                }
//                n = orderProductsAttributesDao.update(orderProductsAttributes);
//                if (n < 1) {
//                    log.error("CaiNiao网单出库：lbxSn[" + cainiaoMap.get("lbxSn") + "]cOrderSn["
//                            + orderProduct.getCOrderSn() + "]跟新'网单扩展属性表'失败！");
//                }
//                orderProductsNewDao.updateAfterDelivery3W(orderProduct);
//            } else {
//                //更新网单
//                orderProductsNewDao.updateAfterDelivery(orderProduct);
//            }
//            //添加日志
//            if (StringUtil.isEmpty(lesDoNo)) {//出库凭证号为空的异常
//                OrderOperateLogs log = this.getOrderOperateLog(order, orderProduct, "系统",
//                        outstrType + "出库", outstrType + "出库成功,但出库凭证号为空");
//                orderOperateLogsDao.insert(log);
//            }
//
//            String text = "出库成功,出库凭证号:" + lesDoNo;
//            if (toOutping) {
//                text = "出库成功,出库凭证号为空," + outstrType + "出库凭证号在回传网点时回传";
//            }
//
//            // 订单状态为订单已有状态 2013-10-29 娄静
//            String logmsg = orderProduct.getStatus() > OrderProductsNew.STATUS_WAIT_VERIFY
//                    ? outstrType + text
//                    + (StringUtil.isEmpty(orderProduct.getExpressName()) ? ""
//                    : ";快递公司:" + orderProduct.getExpressName())
//                    + ",但此时网单状态为" + orderStatusMap.get(orderProduct.getStatus()) + ",不再修改网单状态"
//                    : outstrType + text + (StringUtil.isEmpty(orderProduct.getExpressName()) ? ""
//                    : ";快递公司:" + orderProduct.getExpressName());
//            orderOperateLogsDao
//                    .insert(getOrderOperateLog(order, orderProduct, "系统", outstrType + "出库", logmsg));
//
//            //1、更新订单全流程监控信息 - 出库时间 2、添加到出库队列
//            //            if (OrderProducts.STATUS_WAIT_VERIFY.equals(orderProduct.getStatus())) {//网单出库        2014-03-21注释掉该条件 -wyj
//            //更新订单全流程监控信息 - 出库时间
//            OrderWorkflows wf = orderWorkflowsDao.getByOrderProductId(orderProduct.getId());
//            if (wf != null) {
//                wf.setLesShipTime(orderProduct.getLessShipTime());
//
//                //3W－3W菜鸟出库时更新开提单节点相关信息（因3W网单没有开提单节点），故收到出库信息时模拟写入全流程表开提单时间 仅3W菜鸟出库时候 wyj 2016-10-03
//                if (orderProduct.getStockType() != null
//                        && orderProduct.getStockType().equalsIgnoreCase("3W")) {
//                    wf.setLesShipping(new Date().getTime() / 1000);
//                    orderWorkflowsDao.updateAfterCnShipped(wf);
//                } else {
//                    orderWorkflowsDao.updateAfterLesShipped(wf);
//                }
//            } else {
//                log.warn(
//                        outstrType + "出库，同步到CBS时，找不到网单(" + orderProduct.getCOrderSn() + ")对应的全流程监控信息。");
//            }
//            //添加到出库队列
//            OrderShippedQueue queue = new OrderShippedQueue();
//            queue.setOrderLineId(orderProduct.getId());
//            orderShippedQueueDao.insert(queue);
//
//            if (invoicing) {
//                //出库后创建开发票，添加到Invoice表
//                int n = insertInvoice(order, orderProduct);
//                if (n > 0) {
//                    OrderOperateLogs invlog = this.getOrderOperateLog(order, orderProduct, "系统",
//                            "创建发票队列", "添加网单到发票队列完成");
//                    orderOperateLogsDao.insert(invlog);
//                }
//            }
//
//            //不良品换货出库传HP
//            // this.insertBlphhInfo(order, orderProduct);
//
//            //提交事务
//            transactionManager.commit(status);
//            //返回执行成功
//            result.setResult(true);
//            return result;
//        } catch (Exception ex) {
//            //回滚事务
//            transactionManager.rollback(status);
//            //记录日志
//            log.error("网单出库(expressNo:" + expressNo + ",lesDoNo:" + lesDoNo + ",orderProductId:"
//                            + orderProduct.getId() + ")，出现未知异常:",
//                    ex);
//            result.setResult(false);
//            result.setMessage("发生未知异常：" + ex.getMessage());
//            return result;
//        }
//    }
//    /**
//     * 根据网单信息，返回网单操作日志
//     * @param order 订单
//     * @param orderProduct 网单
//     * @param operator 操作人，可为null
//     * @param changeLog 变化日志
//     * @param remark 备注
//     * @return 如果为null，应为传入参数不对
//     */
//    public OrderOperateLogs getOrderOperateLog(OrdersNew order, OrderProductsNew orderProduct,
//                                               String operator, String changeLog, String remark) {
//        Assert.notNull(order, "订单不能为空");
//
//        OrderOperateLogs log = new OrderOperateLogs();
//        log.setChangeLog(StringUtil.isEmpty(changeLog) ? "" : changeLog);
//        log.setNetPointId(orderProduct == null ? 0 : orderProduct.getNetPointId());
//        log.setOperator(StringUtil.isEmpty(operator) ? "系统" : operator);
//        log.setOrderId(Integer.valueOf(order.getId()));
//        log.setOrderProductId(orderProduct == null ? 0 : orderProduct.getId());
//        log.setPaymentStatus(order.getPaymentStatus());
//        if (StringUtil.isEmpty(remark)) {
//            log.setRemark("");
//        } else {
//            if (remark.length() > 255) {
//                log.setRemark(remark.substring(0, 255));
//            } else {
//                log.setRemark(remark);
//            }
//        }
//        log.setSiteId(1);
//        log.setStatus(orderProduct == null ? order.getOrderStatus() : orderProduct.getStatus());
//
//        return log;
//    }
//    /**
//     * 创建开发票
//     * @param
//     * @return
//     */
//    public int insertInvoice(OrdersNew order, OrderProductsNew orderProduct){
//        //订单取消，关闭推送
//        if (order.getOrderStatus().equals(OrderStatus.OS_CANCEL.getCode())) {
//            log.error("订单取消，关闭推送发票队列，订单id：" + order.getId());
//            return 0;
//        }
//        //网单取消，关闭推送
//        if (orderProduct.getStatus().equals(OrderProductStatus.CANCEL_CLOSE.getCode())) {
//            log.error("网单取消，关闭推送发票队列，网单id：" + orderProduct.getId());
//            return 0;
//        }
//
//        //天猫定金尾款订单，定金发货的网单不再开票。待HP收到尾款回传时再插入开票队列
//        //不区分定金发货还是尾款发货模式，只有未付尾款就不开票
//        if (OrderType.TYPE_GROUP_ADVANCE_TAIL.getValue().equals(order.getOrderType())
//                && order.getTaobaoGroupId() > 0 && order.getTailPayTime() == 0l) {
//            log.info("天猫定金尾款订单，定金发货的网单不再开票。待HP收到尾款回传时再插入开票队列。订单id：" + order.getId());
//            return 0;
//        }
//
//        //2016-10-18 3W网单发票信息特殊处理
//        if ("3W".equalsIgnoreCase(orderProduct.getStockType())
//                && orderProduct.getMakeReceiptType().intValue() == 0) {
//            OrderOperateLogs invlog = this.getOrderOperateLog(order, orderProduct, "系统", "创建发票队列",
//                    "3W特殊网单人工客服没有处理，不开票！");
//            orderOperateLogsDao.insert(invlog);
//            return 0;
//        }
//
//        //添加发票队列，开提单时开发票-----后面修改成出库时开发票，有两部分出库代码修改，采销联动出库和VOM出库，等VOM开提单上线在修改这个上线
//        InvoiceQueue invoiceQueue = new InvoiceQueue();
//        invoiceQueue.setOrderProductId(orderProduct.getId());
//        invoiceQueue.setSuccess(0);
//        invoiceQueue.setAddTime(new Date());
//        invoiceQueue.setProcessTime(new Date());
//        List<InvoiceQueue> inv_queue_list = invoiceQueueDao
//                .getByOrderProductId(orderProduct.getId());
//        if (inv_queue_list == null || inv_queue_list.size() == 0) {//如果已经存在就不在插入
//            return invoiceQueueDao.insert(invoiceQueue);
//        }
//
//        if (inv_queue_list != null && inv_queue_list.size() > 0) {
//            return inv_queue_list.size();
//        }
//
//        return 0;
//    }
//    /**
//     * 转运网单第一次出库
//     * @param order 订单
//     * @param orderProduct 网单
//     * @param lesDoNo 出库凭证
//     * @param lesShipTime 出库时间
//     * @return
//     */
//    public ServiceResult<Boolean> transferOrderLineFirstOut(OrdersNew order,
//                                                            OrderProductsNew orderProduct,
//                                                            String lesDoNo, Date lesShipTime){
//        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
//        //曾经解锁的库存数量 - 曾经解锁的库存数量为零时才更新
//        if (orderProduct.getUnlockedNumber() == 0) {
//            orderProduct.setUnlockedNumber(orderProduct.getNumber());
//        } else {
//            orderProduct.setUnlockedNumber(0);//由于sql是动态更新，需要赋值为零，让sql不更新
//        }
//        //网单状态 - 未关闭时才更新
//        if (!OrderProductsNew.STATUS_CANCEL_CLOSE.equals(orderProduct.getStatus())
//                && !OrderProductsNew.STATUS_COMPLETED_CLOSE.equals(orderProduct.getStatus())) {
//            orderProduct.setStatus(OrderProductsNew.STATUS_WAIT_TRANSSHIPIN);
//            //            orderProduct.setSystemRemark("LES转运第一次出库成功-把网单状态改为待转运入库;");
//        } else {
//            //            orderProduct.setSystemRemark(null);//由于sql是动态更新，需要赋值为null，让sql不更新
//        }
//        orderProduct.setSystemRemark(null);
//        //LES转运出库时间
//        orderProduct.setLessShipTOutTime(lesShipTime.getTime() / 1000);
//        orderProduct.setOutping(lesDoNo);//为了在这个节点也能退货，也更新出库凭证
//
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);
//        try {
//            //更新网单
//            orderProductsNewDao.updateAfterTransferFirstOut(orderProduct);
//            //添加日志
//            if (StringUtil.isEmpty(lesDoNo)) {
//                OrderOperateLogs log = this.getOrderOperateLog(order, orderProduct, "系统", "LES出库",
//                        "LES转运第一次出库成功,但出库凭证号为空");
//                orderOperateLogsDao.insert(log);
//            }
//            if (OrderProductsNew.STATUS_CANCEL_CLOSE.equals(orderProduct.getStatus())) {
//                OrderOperateLogs log = this.getOrderOperateLog(order, orderProduct, "系统", "LES出库",
//                        "LES转运第一次出库成功,出库凭证号:" + lesDoNo + ",但此时网单状态为取消关闭,不再修改网单状态");
//                orderOperateLogsDao.insert(log);
//            } else if (OrderProductsNew.STATUS_COMPLETED_CLOSE.equals(orderProduct.getStatus())) {
//                OrderOperateLogs log = this.getOrderOperateLog(order, orderProduct, "系统", "LES出库",
//                        "LES转运第一次出库成功,出库凭证号:" + lesDoNo + ",但此时网单状态已为完成关闭,不再修改网单状态");
//                orderOperateLogsDao.insert(log);
//            } else {
//                OrderOperateLogs log = this.getOrderOperateLog(order, orderProduct, "系统", "LES出库",
//                        "LES转运第一次出库成功,出库凭证号:" + lesDoNo);
//                orderOperateLogsDao.insert(log);
//            }
//            //提交事务
//            transactionManager.commit(status);
//            //返回执行成功
//            result.setResult(true);
//            return result;
//        } catch (Exception ex) {
//            //回滚事务
//            transactionManager.rollback(status);
//            //记录日志
//            log.error("转运网单第一次出库(lesDoNo:" + lesDoNo + ",orderProductId:" + orderProduct.getId()
//                            + ")，出现未知异常:",
//                    ex);
//            result.setResult(false);
//            result.setMessage("发生未知异常：" + ex.getMessage());
//            return result;
//        }
//    }
//    /**
//     * 转运网单入库
//     * @param order 订单
//     * @param orderProduct 网单对象
//     * @param lesIoNo 转运单编号
//     * @param lesInputTime 入库时间
//     * @return
//     */
//    public ServiceResult<Boolean> transferOrderLineIn(OrdersNew order, OrderProductsNew orderProduct,
//                                                      String lesIoNo, Date lesInputTime){
//        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
//        //检查网单状态
//        if (OrderProductsNew.STATUS_CANCEL_CLOSE.equals(orderProduct.getStatus())) {
//            OrderOperateLogs log = this.getOrderOperateLog(order, orderProduct, "系统", "LES出库",
//                    "本地转运库入库成功,但此时网单状态为取消关闭,不再修改网单状态");
//            orderOperateLogsDao.insert(log);
//            result.setResult(true);
//            return result;
//        }
//        if (OrderProductsNew.STATUS_COMPLETED_CLOSE.equals(orderProduct.getStatus())) {
//            OrderOperateLogs log = this.getOrderOperateLog(order, orderProduct, "系统", "LES出库",
//                    "本地转运库入库成功,但此时网单状态已为完成关闭,不再修改网单状态");
//            orderOperateLogsDao.insert(log);
//            result.setResult(true);
//            return result;
//        }
//        //检查是否开票
//        InvoicesReady ir = invoicesReadyDao.getByOrderProductId(orderProduct.getId());
//        if (ir != null) {
//            OrderOperateLogs log = this.getOrderOperateLog(order, orderProduct, "系统", "LES出库",
//                    "转运开票失败：已经存在开票队列记录");
//            orderOperateLogsDao.insert(log);
//            result.setResult(true);
//            return result;
//        }
//        //获取退货单
//        OrderRepairsNew repair = orderRepairsNewDao.getValidByOrderProductId(orderProduct.getId());
//
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);
//        try {
//            //开票
//            ir = new InvoicesReady();
//            ir.setDoTime(0);
//            ir.setOrderProductId(orderProduct.getId());
//            if (repair == null) {
//                ir.setStatus(0);
//                ir.setMessage("");
//            } else {//存在退货单，不再开票
//                ir.setStatus(3);
//                ir.setMessage("已有退货单、不开发票");
//            }
//            invoicesReadyDao.insert(ir);
//            //开票日志
//            String logRemark = "创建转运库开票队列";
//            if (repair != null) {
//                logRemark = "已有退货单、不开发票";
//            }
//            OrderOperateLogs log = this.getOrderOperateLog(order, orderProduct, "系统", "LES出库",
//                    logRemark);
//            orderOperateLogsDao.insert(log);
//            //更新网单信息
//            orderProduct.setStatus(OrderProductsNew.STATUS_WAIT_TRANSSHIPOUT);
//            orderProduct.setLessShipTInTime(lesInputTime.getTime() / 1000);
//            //            orderProduct.setSystemRemark("本地转运库入库成功-把网单状态改为待转运出库;");
//            orderProduct.setSystemRemark(null);
//            orderProductsNewDao.updateAfterTransferIn(orderProduct);
//            //更新网单日志
//            log = this.getOrderOperateLog(order, orderProduct, "系统", "LES出库",
//                    "本地转运库入库，单号：" + lesIoNo + ".'，时间：" + lesInputTime + "。");
//            orderOperateLogsDao.insert(log);
//            //提交事务
//            transactionManager.commit(status);
//            //返回执行成功
//            result.setResult(true);
//            return result;
//        } catch (Exception ex) {
//            //回滚事务
//            transactionManager.rollback(status);
//            //记录日志
//            log.error("网单转运入库(lesInputTime:" + lesInputTime + ",lesIoNo:" + lesIoNo
//                            + ",orderProductId:" + orderProduct.getId() + ")，出现未知异常:",
//                    ex);
//            result.setResult(false);
//            result.setMessage("发生未知异常：" + ex.getMessage());
//            return result;
//        }
//    }
//
//    /**
//     * 转运网单第二次出库
//     * @param order 订单
//     * @param orderProduct 网单
//     * @param lesDoNo 出库凭证
//     * @param lesShipTime 出库时间
//     * @param expressNo 快递单号
//     * @param expressCompony 快递公司
//     * @return
//     */
//    public ServiceResult<Boolean> transferOrderLineSecondOut(OrdersNew order,
//                                                             OrderProductsNew orderProduct,
//                                                             String lesDoNo, Date lesShipTime,
//                                                             String expressNo,
//                                                             String expressCompony){
//        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
//        //出库单
//        orderProduct.setOutping(lesDoNo);
//        //出库时间
//        orderProduct.setLessShipTime(lesShipTime.getTime() / 1000);
//        //运单号
//        orderProduct.setInvoiceNumber(expressNo);
//        //快递公司
//        orderProduct.setExpressName(expressCompony);
//        //网单状态 - 未关闭时才更新
//        if (!OrderProductsNew.STATUS_CANCEL_CLOSE.equals(orderProduct.getStatus())
//                && !OrderProductsNew.STATUS_COMPLETED_CLOSE.equals(orderProduct.getStatus())) {
//            orderProduct.setStatus(OrderProductsNew.STATUS_WAIT_VERIFY);
//            //            orderProduct.setSystemRemark("LES转运第二次出库成功-把网单状态改为待审核;");
//        } else {
//            //            orderProduct.setSystemRemark(null);//由于sql动态更新，不需要更新该字段时，需要赋值为null
//        }
//        orderProduct.setSystemRemark(null);
//
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);
//        try {
//            //更新网单
//            orderProductsNewDao.updateAfterDelivery(orderProduct);
//            //添加日志
//            if (StringUtil.isEmpty(lesDoNo)) {
//                OrderOperateLogs log = this.getOrderOperateLog(order, orderProduct, "系统", "LES出库",
//                        "LES转运第二次出库成功,但出库凭证号为空");
//                orderOperateLogsDao.insert(log);
//            }
//            if (OrderProductsNew.STATUS_CANCEL_CLOSE.equals(orderProduct.getStatus())) {
//                OrderOperateLogs log = this.getOrderOperateLog(order, orderProduct, "系统", "LES出库",
//                        "LES转运第二次出库成功,出库凭证号:" + lesDoNo + ",但此时网单状态为取消关闭,不再修改网单状态");
//                orderOperateLogsDao.insert(log);
//            } else if (OrderProductsNew.STATUS_COMPLETED_CLOSE.equals(orderProduct.getStatus())) {
//                OrderOperateLogs log = this.getOrderOperateLog(order, orderProduct, "系统", "LES出库",
//                        "LES转运第二次出库成功,出库凭证号:" + lesDoNo + ",但此时网单状态已为完成关闭,不再修改网单状态");
//                orderOperateLogsDao.insert(log);
//            } else {
//                OrderOperateLogs log = this.getOrderOperateLog(order, orderProduct, "系统", "LES出库",
//                        "LES转运第二次出库成功,出库凭证号:" + lesDoNo);
//                orderOperateLogsDao.insert(log);
//            }
//            //1、更新订单全流程监控信息 - 出库时间 2、添加到出库队列
//            //            if (OrderProducts.STATUS_WAIT_VERIFY.equals(orderProduct.getStatus())) {//网单出库        2014-03-21注释掉该条件 -wyj
//            //更新订单全流程监控信息 - 出库时间
//            OrderWorkflows wf = orderWorkflowsDao.getByOrderProductId(orderProduct.getId());
//            if (wf != null) {
//                wf.setLesShipTime(orderProduct.getLessShipTime());
//                orderWorkflowsDao.updateAfterLesShipped(wf);
//            } else {
//                log.warn("LES出库，同步到CBS时，找不到网单(" + orderProduct.getCOrderSn() + ")对应的全流程监控信息。");
//            }
//            //添加到出库队列
//            OrderShippedQueue queue = new OrderShippedQueue();
//            queue.setOrderLineId(orderProduct.getId());
//            orderShippedQueueDao.insert(queue);
//
//            //
//            //            OrderProductsAttributes orderProductsAttributes = orderProductsAttributesDao
//            //                .getByOrderProductId(orderProduct.getId());
//            //            int n = insertInvoice(order, orderProduct, orderProductsAttributes);
//
//            //出库后创建开发票，添加到Invoice表
//            int n = insertInvoice(order, orderProduct);
//            if (n > 0) {
//                OrderOperateLogs invlog = this.getOrderOperateLog(order, orderProduct, "系统",
//                        "创建发票队列", "添加网单到发票队列完成");
//                orderOperateLogsDao.insert(invlog);
//            }
//
//            //            }
//
//            //不良品换货出库传HP
//            // this.insertBlphhInfo(order, orderProduct);
//
//            //提交事务
//            transactionManager.commit(status);
//            //返回执行成功
//            result.setResult(true);
//            return result;
//        } catch (Exception ex) {
//            //回滚事务
//            transactionManager.rollback(status);
//            //记录日志
//            log.error("转运网单第二次出库(expressNo:" + expressNo + ",lesDoNo:" + lesDoNo
//                            + ",orderProductId:" + orderProduct.getId() + ")，出现未知异常:",
//                    ex);
//            result.setResult(false);
//            result.setMessage("发生未知异常：" + ex.getMessage());
//            return result;
//        }
//    }
//    
//    /**
//     * 根据id获取订单
//     * @param id
//     * @return
//     */
//    public OrdersNew getOrderById(Integer id) {
//        return ordersNewDao.get(id);
//    }
//    
//    /**
//     * 根据网单编号，获取网单信息
//     * @param cOrderSn
//     * @return
//     */
//    public OrderProductsNew getOrderProductByCOrderSn(String cOrderSn) {
//        return orderProductsNewDao.getByCOrderSn(cOrderSn);
//    }
//    /**
//     * 保存saveHpReservationDateRelation信息
//     * @param orderProduct
//     * @return
//     */
//    public Boolean saveHpReservationDateRelation(OrderProductsNew orderProduct, String remark,
//    		String changeLog) {
//    	DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//    	def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//    	TransactionStatus status = transactionManager.getTransaction(def);
//    	try {
//    		//更新预约时间
//    		orderProductsNewDao.updateOpHpReservationDate(orderProduct);
//    		//保存送货时间日记信息
//    		HpReservationDateLogs hpReservationDateLogs = new HpReservationDateLogs();
//    		hpReservationDateLogs.setOrderId(orderProduct.getOrderId());
//    		hpReservationDateLogs.setOrderProductId(orderProduct.getId());
//    		hpReservationDateLogs.setCOrderSn(orderProduct.getCOrderSn());
//    		hpReservationDateLogs.setHpReservationDate(orderProduct.getHpReservationDate());
//    		hpReservationDateLogs
//    		.setAddTime(((Long) (System.currentTimeMillis() / 1000)).intValue());
//    		hpReservationDateLogsDao.insert(hpReservationDateLogs);
//    		//订单操作日志
//    		if (remark != null && remark.length() > 250) {
//    			remark = remark.substring(0, 250) + "...";
//    		}
//    		OrderOperateLogs log = new OrderOperateLogs();
//    		log.setChangeLog(StringUtil.isEmpty(changeLog) ? "" : changeLog);
//    		log.setLogTime(((Long) (System.currentTimeMillis() / 1000)).intValue());
//    		log.setNetPointId(orderProduct == null ? 0 : orderProduct.getNetPointId());
//    		log.setOperator("CBS系统");
//    		log.setOrderId(orderProduct.getOrderId());
//    		log.setOrderProductId(orderProduct == null ? 0 : orderProduct.getId());
//    		log.setPaymentStatus(orderProduct.getCPaymentStatus());
//    		log.setRemark(StringUtil.isEmpty(remark) ? "" : remark);
//    		log.setSiteId(1);
//    		log.setStatus(orderProduct.getStatus());
//    		orderOperateLogsDao.insert(log);
//
//    		//            int orderId = orderProduct.getOrderId();
//    		//            String mobileNumber = "";
//    		//            if (orderId > 0) {
//    		//                Orders orders = ordersDao.get(orderId);
//    		//                if (orders != null) {
//    		//                    //获取手机号码
//    		//                    if (StringUtil.isEmpty(orders.getMobile()) && orders.getMemberId() > 0) {
//    		//                        Members members = membersDao.get(orders.getMemberId());
//    		//                        mobileNumber = members.getMobile();
//    		//                    } else {
//    		//                        mobileNumber = orders.getMobile();
//    		//                    }
//    		//                    if (!StringUtil.isEmpty(mobileNumber)
//    		//                        && orderProduct.getHpReservationDate() > 0) {
//    		//                        StringBuffer sb = new StringBuffer();
//    		//                        sb.append(orders.getConsignee()).append("您好，您购买的");
//    		//                        List<Map<String, Object>> list = orderProductsDao.getProductTypeById
//    		//
//    		//                        (orderProduct.getProductType());
//    		//                        for (Map<String, Object> map : list) {
//    		//                            sb.append(map.get("name"));
//    		//                        }
//    		//                        SimpleDateFormat dd = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
//    		//                        sb.append("预约送货时间修改为").append(dd.format
//    		//
//    		//                        (orderProduct.getHpReservationDate())).append("。如有需要请联系在线客服！");
//    		//                        SmsLogs smsLogs = new SmsLogs();
//    		//                        smsLogs.setMobile(mobileNumber);
//    		//                        smsLogs.setName(mobileNumber);
//    		//                        smsLogs.setMessage(sb.toString());
//    		//                        smsLogs.setAddTime(Long.valueOf(System.currentTimeMillis() / 1000L)
//    		//                            .intValue());
//    		//                        smsLogs.setIsSuccess(0);//0-成功 1-失败
//    		//                        smsLogs.setPriority(0);
//    		//                        smsLogs.setLastTime(0);
//    		//                        smsLogs.setTryNum(0);
//    		//                        smsLogs.setSiteId(1);
//    		//                        smsLogsWriteDao.insertSmsLogs(smsLogs);
//    		//
//    		//                    }
//    		//                }
//    		//            }
//    		transactionManager.commit(status);
//    		return true;
//    	} catch (Exception e) {
//    		transactionManager.rollback(status);
//    		log.error("[OrderModel]网单号：" + orderProduct.getCOrderSn() + "更新预约时间发生未知异常：", e);
//    		return false;
//    	}
//    }
//    public OrderProductsAttributes getByOrderProductId(Integer orderProductId) {
//        return orderProductsAttributesDao.getByOrderProductId(orderProductId);
//    }
//    /**
//     * 根据网单号获取订单
//     * @param orderSn
//     * @return
//     */
//    public OrdersNew getByOrderSn(String orderSn) {
//        return ordersNewDao.getByOrderSn(orderSn);
//    }
//    
//
//    public OrderRepairsNew getValidByOrderProductId(Integer orderProductId) {
//        return orderRepairsNewDao.getValidByOrderProductId(orderProductId);
//    }
//    
//    public List<OrderRepairsNew> getOrderRepairsByOrderProductId(Integer orderProductId) {
//        return orderRepairsNewDao.getByOrderProductId(orderProductId);
//    }
//    
//    /**
//     * 取消关闭网单和订单,
//     * 支持强制 变更 完成关闭 为 取消关闭
//     * @param orderProducts
//     * @param orders
//     * @return
//     */
//    public Boolean forceCancelClose(OrderProductsNew orderProducts, OrdersNew orders) {
//
//        if (orders.getOrderStatus().equals(OrderStatus.OS_CANCEL.getCode())
//        /*|| orders.getOrderStatus().equals(OrderStatus.OS_COMPLETE.getCode())*/) {
//            // throw new BusinessException("订单【完成关闭】或【取消关闭】后将不能被取消！");
//            //【完成关闭】或
//            log.info("订单【取消关闭】后将不能被取消！");
//            return true;
//        }
//        OrderOperateLogs orderProductOperateLogs;
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);
//        try {
//            //更新网单信息
//            orderProductsNewDao.forceCancelClose(orderProducts.getId(), new Date().getTime() / 1000);
//            //更新订单状态
//            List<OrderProductsNew> opList = orderProductsNewDao.getByOrderId(orderProducts.getOrderId());
//            //查询所有网单
//            int n = 0;
//            if (opList != null && opList.size() > 0) {
//                for (Iterator<OrderProductsNew> it = opList.iterator(); it.hasNext();) {
//                    OrderProductsNew op = it.next();
//                    if (op.getId().equals(orderProducts.getId())) {
//                        it.remove();//移除当前要更新为取消关闭但还未取消关闭的网单
//                    } else {
//                        if (/*op.getStatus().equals(OrderProductStatus.COMPLETED_CLOSE.getCode())
//                            ||*/op.getStatus().equals(OrderProductStatus.CANCEL_CLOSE.getCode())) {
//                            n++;
//                        }
//
//                    }
//                }
//                if (opList.size() == n) {
//                    ordersNewDao.updateOrderStatus(Integer.parseInt(orders.getId()), OrderStatus.OS_CANCEL.getCode());
//                }
//            }
//            Date now = new Date();
//            orderProductOperateLogs = getOrderOperateLog(orders, orderProducts, "系统", "网单状态由 ”"
//                                                                                      + OrderProductStatus
//                                                                                          .getByCode(
//                                                                                              orderProducts
//                                                                                                  .getStatus())
//                                                                                          .getName()
//                                                                                      + "“变成 ”"
//                                                                                      + OrderProductStatus.CANCEL_CLOSE
//                                                                                          .getName()
//                                                                                      + "“",
//                "网单状态" + OrderProductStatus.CANCEL_CLOSE.getName() + "时间为：" + DateUtil.format(now,
//                    "yyyy-MM-dd HH:mm:ss"));
//            orderOperateLogsDao.insert(orderProductOperateLogs);
//
//            //更改网单全流程表
//            OrderWorkflows orderWorkflows = new OrderWorkflows();
//            orderWorkflows.setOrderId(Integer.parseInt(orders.getId()));
//            orderWorkflows.setCancelTime(System.currentTimeMillis() / 1000);
//            orderWorkflows.setCancelPeople("CBS退货单");
//            orderWorkflowsDao.updateForCancelOrder(orderWorkflows);
//            //提交事务
//            transactionManager.commit(status);
//            return true;
//        } catch (Exception e) {
//            transactionManager.rollback(status);
//            return false;
//        }
//
//    }
//    public boolean updateOrderWorkflowNetPointAcceptTime(String orderSn, Date acceptTime,
//                                                         String mes[]){
//        OrderProductsNew orderProducts = orderProductsNewDao.getByCOrderSn(orderSn);
//        if (orderProducts == null) {
//            mes[0] = "网点接单:网单没有记录，orderSn=" + orderSn;
//            log.error(mes[0]);
//            return false;
//        }
//        OrdersNew orders = ordersNewDao.get(orderProducts.getOrderId());
//        if (orders == null) {
//            mes[0] = "网点接单:订单没有记录，orderSn=" + orderProducts.getCOrderSn();
//            log.error(mes[0]);
//            return false;
//        }
//        if (acceptTime == null) {
//            mes[0] = "网点接单:网点接单时间为空，orderSn=" + orderProducts.getCOrderSn();
//            log.error(mes[0]);
//            return false;
//        }
//        OrderWorkflows orderWorkflow = orderWorkflowsDao.getByOrderProductId(orderProducts.getId());
//        if (orderWorkflow == null) {
//            mes[0] = "网点接单:网单没有全流程记录，orderSn=" + orderProducts.getCOrderSn();
//            log.error(mes[0]);
//            return false;
//        }
//        Long netPointAcceptTime = orderWorkflow.getNetPointAcceptTime();
//        OrderOperateLogs orderProductOperateLogs;
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);
//        try {
//            //更新网单信息
//            if (orderProducts.getStatus() < OrderProductStatus.WAIT_DELIVERY.getCode()) {
//                orderProducts.setStatus(OrderProductStatus.WAIT_DELIVERY.getCode());
//                orderProductsNewDao.updateStatus(orderProducts.getId(), orderProducts.getStatus());
//            }
//            //更新网点接单时间
//            if (!(netPointAcceptTime != null && netPointAcceptTime > 0)) {
//                orderWorkflowsDao.updateNetPointAcceptTime(orderWorkflow.getId(),
//                        acceptTime.getTime() / 1000);
//            }
//            //记录网单日志
//            orderProductOperateLogs = getOrderOperateLog(orders, orderProducts, "CBS系统", "网点接单",
//                    "网点接单时间为：" + DateUtil.format(acceptTime, "yyyy-MM-dd HH:mm:ss"));
//            orderOperateLogsDao.insert(orderProductOperateLogs);
//
//            transactionManager.commit(status);
//            return true;
//        } catch (Exception e) {
//            transactionManager.rollback(status);
//            mes[0] = "记录网点接单发生异常，orderSn=" + orderProducts.getCOrderSn();
//            log.error(mes[0], e);
//            return false;
//        }
//    }
//    public boolean updateOrderWorkflowNetPointShipTime(String orderSn, Date shipTime,
//                                                       String mes[]) {
//        OrderProductsNew orderProducts = orderProductsNewDao.getByCOrderSn(orderSn);
//        if (orderProducts == null) {
//            mes[0] = "网点出库:网单没有记录，orderSn=" + orderSn;
//            log.error(mes[0]);
//            return false;
//        }
//        OrdersNew orders = ordersNewDao.get(orderProducts.getOrderId());
//        if (orders == null) {
//            mes[0] = "网点出库:订单没有记录，orderSn=" + orderProducts.getCOrderSn();
//            log.error(mes[0]);
//            return false;
//        }
//        if (shipTime == null) {
//            mes[0] = "网点出库:网点出库时间为空，orderSn=" + orderProducts.getCOrderSn();
//            log.error(mes[0]);
//            return false;
//        }
//        OrderWorkflows orderWorkflow = orderWorkflowsDao.getByOrderProductId(orderProducts.getId());
//        if (orderWorkflow == null) {
//            mes[0] = "网点出库:网单没有全流程记录，orderSn=" + orderProducts.getCOrderSn();
//            log.error(mes[0]);
//            return false;
//        }
//        Long netPointShipTime = orderWorkflow.getNetPointShipTime();
//        OrderOperateLogs orderProductOperateLogs;
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);
//        try {
//            //更新网单信息
//            if (orderProducts.getStatus() < OrderProductStatus.WAIT_DELIVER.getCode()) {
//                orderProducts.setStatus(OrderProductStatus.WAIT_DELIVER.getCode());
//                orderProductsNewDao.updateStatus(orderProducts.getId(), orderProducts.getStatus());
//            }
//            //更新网点出库时间
//            if (!(netPointShipTime != null && netPointShipTime > 0)) {
//                orderWorkflowsDao.updateNetPointShipTime(orderWorkflow.getId(),
//                        shipTime.getTime() / 1000);
//            }
//            //记录网单日志
//            orderProductOperateLogs = getOrderOperateLog(orders, orderProducts, "CBS系统", "网点出库",
//                    "网点出库时间为：" + DateUtil.format(shipTime, "yyyy-MM-dd HH:mm:ss"));
//            orderOperateLogsDao.insert(orderProductOperateLogs);
//
//            transactionManager.commit(status);
//            return true;
//        } catch (Exception e) {
//            transactionManager.rollback(status);
//            mes[0] = "记录网点出库发生异常，orderSn=" + orderProducts.getCOrderSn();
//            log.error(mes[0], e);
//            return false;
//        }
//    }
//    /**
//     * 用户签收CBS专用
//     * @param
//     * @param
//     * @param mes
//     * @return
//     * @throws ParseException
//     */
//    public boolean updateOrderWorkflowUserAcceptTime(HpSignTimeInterface hpSignTimeInterface,
//                                                     String mes[]) throws ParseException{
//
//        if (mes == null) {
//            mes = new String[1];
//        }
//        //判断是否为微店主签收
//        if (!StringUtil.isEmpty(hpSignTimeInterface.getOprName())
//                && !StringUtil.isEmpty(hpSignTimeInterface.getOprType())) {
//            mes[0] = oprMap.get(hpSignTimeInterface.getOprType()) + "："
//                    + hpSignTimeInterface.getOprName() + ",已签收： ";
//        }
//        OrderProductsNew orderProductsNew = null;
//        HpSignTimeInterface hasInfo = null;
//        if ("HR".equalsIgnoreCase(hpSignTimeInterface.getWwwMark())) {
//            if (StringUtil.isEmpty(hpSignTimeInterface.getTbNo())) {
//                mes[0] = hpSignTimeInterface.getOrderSn() + "3w客户签收，TbNo为空";
//                log.error(mes[0]);
//                return false;
//            }
//            if (StringUtil.isEmpty(hpSignTimeInterface.getLbxNo())) {
//                mes[0] = hpSignTimeInterface.getOrderSn() + "3w客户签收，LBX为空";
//                log.error(mes[0]);
//                return false;
//            }
//            if (StringUtil.isEmpty(hpSignTimeInterface.getSku())) {
//                mes[0] = hpSignTimeInterface.getOrderSn() + "3w客户签收，sku为空";
//                log.error(mes[0]);
//                return false;
//            }
//            //判断表里是否有数据 有数据更新次数+1 return,没有则继续往下走
//            hasInfo = hpSignTimeInterfaceDao.selectByTbNoAndLbx(hpSignTimeInterface);
//            if (hasInfo != null && hasInfo.getDataStatus() != null
//                    && (hasInfo.getDataStatus() == 1 || hasInfo.getDataStatus() == 4)) {
//                hpSignTimeInterface
//                        .setCount(hasInfo.getCount() == null ? 1 : hasInfo.getCount() + 1);
//                hpSignTimeInterface.setLastTime(String.valueOf(new Date().getTime() / 1000));
//                hpSignTimeInterfaceDao.addCountBySkuAndLbx(hpSignTimeInterface);
//                return true;
//            } else {
//                List<OrderProductsNew> opList = orderProductsNewDao
//                        .getByTbNo(hpSignTimeInterface.getTbNo());
//                if (opList == null || opList.size() == 0) {
//                    mes[0] = "tbNo:" + hpSignTimeInterface.getTbNo() + "关联的网单不存在";
//                    log.error(mes[0]);
//                    return false;
//                } else {
//                    if (opList.size() == 1) {
//                        orderProductsNew = opList.get(0);
//                    } else {
//                        boolean flag = false;
//                        for (OrderProductsNew op : opList) {
//                            if (hpSignTimeInterface.getSku().equals(op.getSku())) {
//                                orderProductsNew = op;
//                                flag = true;
//                                break;
//                            }
//                        }
//                        if (!flag) {
//                            mes[0] = "tbNo:" + hpSignTimeInterface.getTbNo() + ",sku:"
//                                    + hpSignTimeInterface.getSku() + ",未匹配到相关网单信息";
//                            log.error(mes[0]);
//                            return false;
//                        }
//                    }
//                    hpSignTimeInterface
//                            .setOrderSn(orderProductsNew == null ? "" : orderProductsNew.getCOrderSn());
//                }
//            }
//        }
//
//        String corderSn = hpSignTimeInterface.getOrderSn();
//        Date signTime = null;
//        try {
//            signTime = hpSignTimeInterface.getStatusTime() == null ? new Date()
//                    : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//                    .parse(hpSignTimeInterface.getStatusTime());
//        } catch (ParseException e) {
//            mes[0] = corderSn + "3w客户签收，签收时间格式错误";
//            log.error(mes[0]);
//            return false;
//        }
//        if (orderProductsNew == null) {
//            orderProductsNew = orderProductsNewDao.getByCOrderSn(corderSn);
//        }
//        if (orderProductsNew == null) {
//            mes[0] = corderSn + "关联的网单不存在";
//            log.error(mes[0]);
//            return false;
//        }
//
//        //网单已签收或取消
//        if(orderProductsNew.getStatus() != null && (orderProductsNew.getStatus().equals(110) || orderProductsNew.getStatus().equals(130))){
//            mes[0] = corderSn + "网单已签收或取消";
//            log.error(mes[0]);
//            return true;
//        }
//
//        //更新网单状态为完成关闭
//        if (!orderProductsNew.getCPaymentStatus().equals(200)) {//未付款的等待付款后自动签收
//            if (!completeCloseOrderProductOper(corderSn, signTime, "", mes)) {
//                return false;
//            }
//        } else {
//            if (signTime == null) {
//                mes[0] = corderSn + "用户签收时间不能为空";
//                log.error(mes[0]);
//                return false;
//            }
//            OrdersNew orders = ordersNewDao.get(orderProductsNew.getOrderId());
//            if (orders == null) {
//                mes[0] = "网单" + corderSn + "关联的订单不存在";
//                log.error(mes[0]);
//                return false;
//            }
//            OrderWorkflows orderWorkflows = orderWorkflowsDao
//                    .getByOrderProductId(orderProductsNew.getId());
//            if (orderWorkflows != null) {
//                if (orderWorkflows.getUserAcceptTime().intValue() == 0) {
//                    orderWorkflowsDao.updateUserAcceptTime(orderWorkflows.getId(),
//                            signTime.getTime() / 1000);
//                    orderOperateLogsDao.insert(getOrderOperateLog(orders, orderProductsNew, "CBS系统",
//                            "用户签收时间同步", (StringUtil.isEmpty(mes[0]) ? "HP同步用户签收时间:" : mes[0])
//                                    + DateUtil.format(signTime, "yyyy-MM-dd HH:mm:ss")));
//                    //报表增加更新网单表modified字段，报表使用全流程NetPointArriveTime，但是订单网单没有及时更新
//                    orderProductsNewDao.updateOpModify(orderProductsNew.getId());
//                } else {
//                    log.error(corderSn + "用户签收时间已存在，新接收时间："
//                            + DateUtil.format(signTime, "yyyy-MM-dd HH:mm:ss"));
//                }
//            } else {
//                mes[0] = corderSn + " 关联的全流程信息不存在，不更新全流程的用户签收时间";
//                log.error(mes[0]);
//                return false;
//            }
//        }
//        //插入到
//        if ("HR".equalsIgnoreCase(hpSignTimeInterface.getWwwMark())) {
//            if (hasInfo == null) {
//                hpSignTimeInterface.setCount(1);
//                hpSignTimeInterface.setLastTime(String.valueOf(new Date().getTime() / 1000));
//                hpSignTimeInterface.setDataStatus(1);
//                hpSignTimeInterfaceDao.insert(hpSignTimeInterface);
//            }
//        }
//        return true;
//    }
//    public boolean completeCloseOrderProductOper(String corderSn, Date signTime,
//                                                 String completeType, String mes[]){
//        if (mes == null) {
//            mes = new String[1];
//        }
//
//        Date now = new Date();
//        if (signTime == null) {
//            mes[0] = corderSn + "用户签收时间不能为空";
//            log.error(mes[0]);
//            return false;
//        }
//        OrderProductsNew orderProductsNew = orderProductsNewDao.getByCOrderSn(corderSn);
//        if (orderProductsNew == null) {
//            mes[0] = corderSn + "关联的网单不存在";
//            log.error(mes[0]);
//            return false;
//        }
//        OrdersNew orders = ordersNewDao.get(orderProductsNew.getOrderId());
//        if (orders == null) {
//            mes[0] = "网单" + corderSn + "关联的订单不存在";
//            log.error(mes[0]);
//            return false;
//        }
//        OrderWorkflows orderWorkflows = orderWorkflowsDao
//                .getByOrderProductId(orderProductsNew.getId());
//        //更新全流程用户签收时间
//
//        boolean noticeFlag = false;
//        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(def);
//        try {
//            ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
//            OrderOperateLogs orderOperateLogs;
//            OrderOperateLogs orderProductOperateLogs;
//            //更新网单状态为完成关闭
//            if (orderProductsNew.getCPaymentStatus() != 200) {//未付款的等待付款后自动签收
//                //更新订单状态
//                List<OrderProductsNew> opList = orderProductsNewDao
//                        .getByOrderId(orderProductsNew.getOrderId());
//                int n = 0;
//                if (opList != null && opList.size() > 0) {
//                    for (Iterator<OrderProductsNew> it = opList.iterator(); it.hasNext();) {
//                        OrderProductsNew op = it.next();
//                        if (op.getId().equals(orderProductsNew.getId())) {
//                            it.remove();//移除当前要更新为完成关闭但还未关闭的网单
//                        } else {
//                            if (op.getStatus().equals(OrderProductStatus.COMPLETED_CLOSE.getCode())
//                                    || op.getStatus()
//                                    .equals(OrderProductStatus.CANCEL_CLOSE.getCode())) {
//                                n++;
//                            }
//                        }
//                    }
//                    if (n == opList.size()) {
//                        ordersNewDao.completeClose(Integer.parseInt(orders.getId()), now.getTime() / 1000);
//                        orderOperateLogs = getOrderOperateLog(orders, null,
//                                "CBS系统", "订单状态由“"
//                                        + OrderStatus.getByCode(orders.getOrderStatus()).getName()
//                                        + "”变成“" + OrderStatus.OS_COMPLETE.getName()
//                                        + "”",
//                                "订单状态" + OrderStatus.OS_COMPLETE.getName() + (completeType == null ? ""
//                                        : "“" + completeType + "”") + "时间为："
//                                        + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
//                        orderOperateLogsDao.insert(orderOperateLogs);
//                        orders.setOrderStatus(OrderStatus.OS_COMPLETE.getCode());
//                    }
//                }
//
//                orderProductsNewDao.completeClose(orderProductsNew.getId(), now.getTime() / 1000);
//                orderProductOperateLogs = getOrderOperateLog(orders, orderProductsNew,
//                        "CBS系统", "网单状态：由 ”"
//                                + OrderProductStatus.getByCode(orderProductsNew.getStatus()).getName()
//                                + "“变成 ”" + OrderProductStatus.COMPLETED_CLOSE.getName()
//                                + "“",
//                        "网单状态" + OrderProductStatus.COMPLETED_CLOSE.getName() + "时间为：" + DateUtil
//                                .format(now, "yyyy-MM-dd HH:mm:ss"));
//                orderOperateLogsDao.insert(orderProductOperateLogs);
//
//                noticeFlag = true;
//
//                orderProductsNew.setStatus(
//                        orderProductsNew.getStatus() == OrderProductStatus.CANCEL_CLOSE.getCode()
//                                ? OrderProductStatus.CANCEL_CLOSE.getCode()
//                                : OrderProductStatus.COMPLETED_CLOSE.getCode());
//
//                //更新全流程表的网单关闭时间
//                if (orderWorkflows != null) {
//                    orderWorkflowsDao.updateFinishTime(orderWorkflows.getId(),
//                            now.getTime() / 1000);
//                }
//
//                ServiceResult<ProductsNew> productResult = itemService
//                        .getProductById(orderProductsNew.getProductId());
//                if (!productResult.getSuccess()) {
//                    throw new BusinessException(
//                            "完成关闭网单失败：获取商品信息出现未知错误：" + productResult.getResult());
//                }
//                ProductsNew productsNew = productResult.getResult();
//                //网单完成关闭时，同步写入外部网单评论状态表
//                //存在会员ID，且不失团购尾款订单和四季沐歌订单,2014年10月21日需求，当虚拟商品时不插入评论表
//                boolean isVirtualProducts = productsNew != null && productsNew.getIsVirtual().equals(1);
//                if (orders.getMemberId() > 0
//                        && !orders.getOrderType().equals(OrderType.TYPE_GROUP_TAIL.getValue())
//                        && !orders.getSource().equals("CORPORATE_SJMG") && !isVirtualProducts) {
//                    ServiceResult<List<ProductCates>> result = itemService
//                            .getChildrenProductCates(2737);
//                    if (!result.getSuccess()) {
//                        throw new BusinessException(
//                                "更新外部网单评论状态失败：获取生活家电的子类目出现错误：" + result.getMessage());
//                    }
//                    boolean isLifeHomeAppliance = false;
//                    for (ProductCates productCates : result.getResult()) {
//                        if (productCates.getId().equals(orderProductsNew.getCateId())) {
//                            isLifeHomeAppliance = true;
//                            break;
//                        }
//                    }
//
//                    int shareOrderNotify = 0;
//                    int commentNotify = 0;
//
//                    if (isLifeHomeAppliance && (("1".equals(orders.getSource())
//                            || "MOBILE".equals(orders.getSource())))) {
//                        shareOrderNotify = 3;
//                        commentNotify = 3;
//                    }
//
//                    CmtCommentOrderProducts cmtCommentOrderProducts = new CmtCommentOrderProducts();
//                    cmtCommentOrderProducts.setOrderProductId(orderProductsNew.getId());
//                    cmtCommentOrderProducts.setOrderId(Integer.parseInt(orders.getId()));
//                    cmtCommentOrderProducts.setMemberId(orders.getMemberId());
//                    cmtCommentOrderProducts.setCommentStatus(0);
//                    cmtCommentOrderProducts.setCorderSn(orderProductsNew.getCOrderSn());
//                    cmtCommentOrderProducts.setCloseTime(now.getTime() / 1000);
//                    cmtCommentOrderProducts.setShareOrderNotify(shareOrderNotify);
//                    cmtCommentOrderProducts.setCommentNotify(commentNotify);
//                    cmtCommentOrderProductsDao.insert(cmtCommentOrderProducts);
//                }
//            } else {
//                orderProductOperateLogs = getOrderOperateLog(orders, orderProductsNew, "系统",
//                        "未付款的等待付款后自动签收", "");
//                orderOperateLogsDao.insert(orderProductOperateLogs);
//            }
//            //更新用户签收时间
//            if (orderWorkflows != null) {
//                if (orderWorkflows.getUserAcceptTime().intValue() == 0) {
//                    orderWorkflowsDao.updateUserAcceptTime(orderWorkflows.getId(),
//                            signTime.getTime() / 1000);
//                    orderProductOperateLogs = getOrderOperateLog(orders, orderProductsNew, "CBS系统",
//                            "用户签收时间同步",
//                            //根据mes[0]区分是否为快递100回传签收
//                            (StringUtil.isEmpty(mes[0]) ? "HP同步用户签收时间:" : mes[0])
//                                    + DateUtil.format(signTime, "yyyy-MM-dd HH:mm:ss"));
//                    orderOperateLogsDao.insert(orderProductOperateLogs);
//                    //报表增加更新网单表modified字段，报表使用全流程NetPointArriveTime，但是订单网单没有及时更新
//                    orderProductsNewDao.updateOpModify(orderProductsNew.getId());
//                } else {
//                    log.error(corderSn + "用户签收时间已存在，新接收时间："
//                            + DateUtil.format(signTime, "yyyy-MM-dd HH:mm:ss"));
//                }
//            } else {
//                log.error(corderSn + " 关联的全流程信息不存在，不再更新全流程的用户签收时间");
//            }
//            transactionManager.commit(status);
//        } catch (Exception e) {
//            log.error("完成关闭网单[" + corderSn + "]失败：", e);
//            transactionManager.rollback(status);
//            throw new BusinessException(e.getMessage());
//        } finally {
//            ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
//        }
//        return true;
//    }
//    /**
//     * 插入签收处理异常数据
//     * @param hpSignTimeInterface
//     */
//    public void insertErrorHpSignTimeInterface(HpSignTimeInterface hpSignTimeInterface) {
//        //判断是3w签收的单子
//        if (hpSignTimeInterface.getStatus() != null && hpSignTimeInterface.getStatus() == 3
//                && "HR".equalsIgnoreCase(hpSignTimeInterface.getWwwMark())) {
//            HpSignTimeInterface hasInfo = hpSignTimeInterfaceDao
//                    .selectByTbNoAndLbx(hpSignTimeInterface);
//            if (hasInfo == null) {
//                hpSignTimeInterface.setDataStatus(0);
//                hpSignTimeInterface.setLastTime(String.valueOf(new Date().getTime() / 1000));
//                hpSignTimeInterfaceDao.insert(hpSignTimeInterface);
//            }
//        }
//    }
//    
//    public boolean payCenterFallBack(OrderProductsNew orderProduct, OrdersNew order, String repairSn,
//    		String[] message) {
//    	try {
//    		OrderRepairsNew ors = orderRepairsNewDao.getByRepairSn(repairSn);
//    		if (ors == null) {
//    			message[0] = "根据退换货单号查询不到信息,单号:" + repairSn;
//    			return false;
//    		}
//    		List<OrderRepairHPRecordsNew> hpList = orderRepairHPRecordsDao.getByRepairId(ors.getId());
//    		if (hpList == null || hpList.isEmpty()) {
//    			log.error("根据退换货ID查询不到HPRecords信息,单号:" + repairSn + ",退换货ID:" + ors.getId());
//    			return true;
//    		}
//    		boolean hpFlag = false;
//    		for (OrderRepairHPRecordsNew orderRepairHPRecords : hpList) {
//    			if (orderRepairHPRecords.getQuality() != null
//    					&& orderRepairHPRecords.getQuality().intValue() == 6) {
//    				hpFlag = true;
//    				break;
//    			}
//    		}
//    		if (!hpFlag) {
//    			log.error("HPRecords没有不良品退机信息,单号:" + repairSn + ",退换货ID:" + ors.getId());
//    			return true;
//    		}
//    		OrderProductsAttributes opAttribute = orderProductsAttributesDao
//    				.getByOrderProductId(orderProduct.getId());
//    		if (opAttribute == null) {
//    			return true;
//    		}
//    		if ((opAttribute.getSeashellAmt() != null
//    				&& opAttribute.getSeashellAmt().compareTo(BigDecimal.ZERO) > 0)
//    				|| (opAttribute.getInsuranceAmt() != null
//    				&& opAttribute.getInsuranceAmt().compareTo(BigDecimal.ZERO) > 0)
//    				|| (opAttribute.getDiamondAmt() != null
//    				&& opAttribute.getDiamondAmt().compareTo(BigDecimal.ZERO) > 0)) {
//    			int splitFlag = orderProduct.getSplitFlag().intValue();
//    			BenefitTypeReqVO reqVo = new BenefitTypeReqVO();
//    			reqVo.setMemberId(order.getMemberId());//会员Id
//    			reqVo.setOrderSn(order.getOrderSn());//订单号
//    			String backType = "";//10:退订单，11:退网单,13:拆单退
//    			//退网单
//    			if (splitFlag == 0) {
//    				backType = "11";
//    				reqVo.setOrderProductId(orderProduct.getCOrderSn());//网单号
//    			}
//    			//拆单退网单
//    			if (splitFlag == 1 || splitFlag == 2) {
//    				backType = "13";
//    				if (StringUtil.isEmpty(orderProduct.getSplitRelateCOrderSn())) {
//    					reqVo.setOrderProductId(orderProduct.getCOrderSn());
//    				} else {
//    					reqVo.setOrderProductId(orderProduct.getSplitRelateCOrderSn());//网单号
//    				}
//    				reqVo.setOrderProductIdN(orderProduct.getCOrderSn());//新网单号backType 13时必填
//    				reqVo.setNum(orderProduct.getNumber());//数量backType 13时必填
//    				List<Map<String, String>> benefitList = new ArrayList<Map<String, String>>();//该节点backType为13时 必填
//
//    				if (opAttribute.getDiamondAmt() != null
//    						&& opAttribute.getDiamondAmt().compareTo(BigDecimal.ZERO) > 0) {
//    					Map<String, String> benefitMap = new HashMap<String, String>();
//    					benefitMap.put("amt", opAttribute.getDiamondAmt().toString());
//    					benefitMap.put("benefitType", "diamond");
//    					benefitMap.put("count", opAttribute.getDiamondCount().toString());
//    					benefitList.add(benefitMap);
//    				}
//    				if (opAttribute.getSeashellAmt() != null
//    						&& opAttribute.getSeashellAmt().compareTo(BigDecimal.ZERO) > 0) {
//    					Map<String, String> benefitMap = new HashMap<String, String>();
//    					benefitMap.put("amt", opAttribute.getSeashellAmt().toString());
//    					benefitMap.put("benefitType", "seashell");
//    					benefitMap.put("count", opAttribute.getSeashellCount().toString());
//    					benefitList.add(benefitMap);
//    				}
//    				if (opAttribute.getInsuranceAmt() != null
//    						&& opAttribute.getInsuranceAmt().compareTo(BigDecimal.ZERO) > 0) {
//    					Map<String, String> benefitMap = new HashMap<String, String>();
//    					benefitMap.put("amt", opAttribute.getInsuranceAmt().toString());
//    					benefitMap.put("benefitType", "insurance");
//    					benefitMap.put("count", opAttribute.getInsuranceCount().toString());
//    					benefitList.add(benefitMap);
//    				}
//    				reqVo.setBenefitList(benefitList);
//    				reqVo.setPayAmt(orderProduct.getProductAmount());//网单实付金额backType 13时必填
//    			}
//    			reqVo.setBackType(backType);
//    			reqVo.setAppId("10006");
//    			reqVo.setCharset("UTF-8");
//    			reqVo.setSignType(SignUtil.SIGNTYPE_RSA);
//    			reqVo.setTs(Long.valueOf(System.currentTimeMillis() / 1000).toString());
//    			reqVo.setSign(SignUtil.sign(reqVo, SignUtil.SIGNTYPE_RSA, RAS_PRIVATE_KEY));
//
//    			String strPost = PayCenterJsonUtils.obj2JsonString(reqVo);
//    			ServiceResult<String> result = payCenterFallBackService.fallBack(repairSn, strPost);
//    			if (result == null) {
//    				message[0] = "调用支付中心回退接口失败！result=null";
//    				return false;
//    			}
//    			if (result.getSuccess()) {
//    				String json = result.getResult();
//    				json = URLDecoder.decode(json, "utf-8");
//    				if (StringUtil.isEmpty(json)) {
//    					message[0] = "调用支付中心回退接口失败！result.getResult()为空";
//    					return false;
//    				}
//    				Map<String, Object> returnMap = PayCenterJsonUtils.jsonString2Object(json,
//    						Map.class);
//    				if (returnMap == null || returnMap.isEmpty()) {
//    					message[0] = "调用支付中心回退接口返回结果转化map为空！返回内容:" + json;
//    					return false;
//    				}
//    				if (returnMap.get("resultCode") == null
//    						|| "".equals(returnMap.get("resultCode").toString())) {
//    					message[0] = "调用支付中心回退接口返回结果resultCode字段为空！";
//    					return false;
//    				}
//    				if ("10".equals(returnMap.get("resultCode").toString())) {
//    					return true;
//    				} else {
//    					message[0] = "调用支付中心回退接口返回结果resultCode != 10," + JsonUtil.toJson(result);
//    					return false;
//    				}
//    			} else {
//    				message[0] = "调用支付中心回退接口失败！Success=false," + JsonUtil.toJson(result);
//    				return false;
//    			}
//    		}
//    		return true;
//    	} catch (Exception e) {
//    		message[0] = "不良品调用支付中心回退接口异常" + e.getMessage();
//    		log.error("不良品调用支付中心回退接口异常,单号:" + repairSn, e);
//    		return false;
//    	}
//    }
//    /**
//     * 获取待处理的 出库队列
//     * @param topX 前X条
//     * @return 出库队列
//     */
//    public List<OrderShippedQueue> getOrderShippedQueueForProcess(Integer topX) {
//        return orderShippedQueueDao.getListToProcess(topX);
//    }
//    /**
//     * 根据id列表，获取网单列表
//     * @param ids
//     * @return
//     */
//    public List<OrderProductsNew> getOrderProductsByIds(List<Integer> ids) {
//        return orderProductsNewDao.getByIds(ids);
//    }
//    /**
//     * 删除指定的出库队列
//     * @param id
//     * @return 影响行数
//     */
//    public Integer deleteOrderShippedQueue(Integer id) {
//        return orderShippedQueueDao.delete(id);
//    }
//}
