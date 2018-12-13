package com.haier.order.model;

import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.EisExternalSku;
import com.haier.order.dateSource.ReadWriteRoutingDataSourceHolder;
import com.haier.order.multithread.IExcute;
import com.haier.order.multithread.MultiThreadTool;
import com.haier.order.multithread.ThreadHelper;
import com.haier.order.services.OrderCenterItemServiceImplByHwl;
import com.haier.order.services.OrderCenterLESServiceImpl;
import com.haier.order.services.OrderCenterStockCommonServiceImpl;
import com.haier.order.services.OrderCenterStockServiceImpl;
import com.haier.order.util.*;
import com.haier.shop.model.LesFiveYardInfo;
import com.haier.shop.service.PurchaseLesFiveYardsService;
import com.haier.shop.model.*;
import com.haier.shop.service.*;
import com.haier.stock.model.InvChannel2OrderSource;
import com.haier.stock.model.InvMachineSet;
import org.lorecraft.phparser.SerializedPhpParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SycnOrderToLesModel {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(SycnOrderToLesModel.class);
    @Autowired
    private ThreadHelper threadHelper;
    @Autowired
    private OrderCenterStockServiceImpl stockServiceImpl;
    @Autowired
    private OrderCenterStockCommonServiceImpl stockCommonService;
    @Autowired
    private OrderCenterItemServiceImplByHwl itemService;
    @Autowired
    private OrderCenterLESServiceImpl lesService;
    @Autowired
    private LesQueuesService lesQueuesService;
    @Autowired
    private OrderProductsNewService orderProductsNewService;
    @Autowired
    private OrdersNewService ordersNewService;
    @Autowired
    private MemberInvoicesService memberInvoicesService;
    @Autowired
    private PurchaseLesFiveYardsService purchaseLesFiveYardsService;
    @Autowired
    private RegionsService regionsService;
    @Autowired
    private ShopMembersService shopMembersService;
    @Autowired
    private ReservationShippingService reservationShippingService;
    @Autowired
    private ShopOrderOperateLogsService orderOperateLogsShopService;
    @Autowired
    private AccessExternalInterface accessExternalInterface;
    @Autowired
    private Item2OrderSourceService item2OrderSourceService;
    @Autowired
    private ProductActivitiesService productActivitiesService;
    @Autowired
    private ShopTaoBaoGroupsService shopTaoBaoGroupsService;

    Map<String, String> mapSource = null;

    private String logPrefix(String lesQueueId) {
        return "[ord-to-vom] [" + lesQueueId + "]";
    }

    private String logPrefix(String type, String lesQueueId) {
        return "[" + type + "] [" + lesQueueId + "]";
    }

    //1
    public void syncOrderToLes() {
        try {
            long startTime = System.currentTimeMillis();
            //获取待同步的列表，每次1000条
            List<LesQueues> lesQueuesList = getSendQueues(1000);
            if (lesQueuesList == null || lesQueuesList.size() == 0) {
                log.info("没有需要同步到VOM的订单。");
                return;
            }
            //加入多线程执行
            ExecuteOrderToLes executeOrderToLes = new ExecuteOrderToLes();
            //分组大小100条(如果大于10小于等于100,分两组),加入多线程失败重试3次,如果失败则本线程自己执行
            int splitSize = 100;
            int size = lesQueuesList.size();
            if (size > 10 && size <= splitSize) {
                splitSize = size / 2 + 1;
            }
            ThreadHelper threadHelper = (ThreadHelper) SpringContextUtil.getBean("threadHelper");

            new MultiThreadTool<LesQueues>().processJobs(executeOrderToLes, threadHelper, log,
                    lesQueuesList, splitSize, 3);
            long endTime = System.currentTimeMillis();
            long time = endTime - startTime;
            log.info("推送VOM条数:" + lesQueuesList.size() + ",总共执行时间:" + time + "毫秒,平均每条"
                    + time / lesQueuesList.size() + "毫秒");
        } catch (Exception e) {
            log.error("同步订单到VOM出现异常：", e);
        }

    }

    //4
    public void syncOrderToLesThread(List<LesQueues> lesQueuesList) {
        if (lesQueuesList != null && lesQueuesList.size() > 0) {
            for (LesQueues lesQueue : lesQueuesList) {
                if (lesQueue == null) {
                    continue;
                }
                try {
                    ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
                    int result = this.createOrderToLes(lesQueue);

                    //处理结果
                    //                    String logMessage = "";
                    //                    switch (result) {
                    //                        case 0: //创建成功
                    //                            break;
                    //                        case -101:
                    //                            lesQueue.setIsStop(1);//不再同步
                    //                            logMessage = "网单不存在，关闭推送";
                    //                            break;
                    //                        case -102:
                    //                            lesQueue.setIsStop(1);//不再同步
                    //                            logMessage = "订单不存在，关闭推送";
                    //                            break;
                    //                        default:
                    //                            break;
                    //                    }
                    //                    if (logMessage != null && !logMessage.equals("")) {
                    //                        lesQueue.setCount(lesQueue.getCount() + 1);//累加同步次数
                    //                        lesQueue.setLastMessage("【" + logMessage + "】");
                    //                        lesQueue.setLastTryTime(new Date().getTime() / 1000);
                    //                        lesQueuesService.updateAfterSyncLes(lesQueue);
                    //                    }
                } catch (Exception e) {
                    log.error(
                            this.logPrefix(lesQueue.getOrderProductId().toString()) + "网单同步到VOM出错", e);
                } finally {
                    ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
                }
            }
        } else {
            log.info("接收同步到VOM的订单列表数据为空。");
        }
    }

    /**
     * 往Les创建订单
     *
     * @param lesQueue
     */
    //5
    private int createOrderToLes(LesQueues lesQueue) {
        //参数检测
        if (lesQueue == null) {
            return -1;
        }
        Integer orderProductId = lesQueue.getOrderProductId();//网单id
        OrderProductsNew orderProduct = orderProductsNewService.get(orderProductId);
        if (orderProduct == null) {
            if (lesQueue.getCount() >= 10) {//处理由于主从库延时同步导致订单不存在的个别异常情况，超过10分钟还未找到网单则停止同步
                lesQueue.setIsStop(1);//不再同步
            }
            lesQueue.setLastMessage("网单不存在");
            lesQueue.setCount(lesQueue.getCount() + 1);
            lesQueue.setLastTryTime(new Date().getTime() / 1000);
            lesQueuesService.updateAfterSyncLes(lesQueue);
            log.error(this.logPrefix(lesQueue.getId() + "") + "网单" + orderProductId + "不存在");
            return -101;//网单不存在
        }
        //**********若网单关闭则不再同步VOM数据start*********
        if (null != orderProduct.getStatus() &&
            OrderProductStatus.CANCEL_CLOSE.getCode().intValue() ==
                orderProduct.getStatus().intValue()
            ){
            lesQueue.setIsStop(1);//不再同步
            lesQueue.setLastMessage("网单已关闭");
            lesQueue.setCount(lesQueue.getCount() + 1);
            lesQueue.setLastTryTime(new Date().getTime() / 1000);
            lesQueuesService.updateAfterSyncLes(lesQueue);
            log.error(this.logPrefix(lesQueue.getId() + "") + "网单" + orderProductId + "已经关闭");
            return -102;//网单已关闭
        }
        //**********若网单关闭则不再同步VOM数据end***********
        OrdersNew order = ordersNewService.get(orderProduct.getOrderId());
        if (order == null) {
            if (lesQueue.getCount() >= 10) {//处理由于主从库延时同步导致订单不存在的个别异常情况，超过10分钟还未找到订单则停止同步
                lesQueue.setIsStop(1);//不再同步
            }
            lesQueue.setLastMessage("订单不存在");
            lesQueue.setCount(lesQueue.getCount() + 1);
            lesQueue.setLastTryTime(new Date().getTime() / 1000);
            lesQueuesService.updateAfterSyncLes(lesQueue);
            log.error(
                    this.logPrefix(lesQueue.getId() + "") + "订单" + orderProduct.getOrderId() + "不存在");
            return -102;//订单不存在
        }

        MemberInvoices memberInvoice = memberInvoicesService.get(order.getMemberInvoiceId());
        String[] message = new String[1];

        //同步订单到vom
        int result = this.createOrderToLes(order, orderProduct, lesQueue, memberInvoice, message);

        //处理result的结果
        this.processResultForCreateOrderToLes(order, orderProduct, lesQueue, result, message);
        //记录日志
        if (log.isInfoEnabled())
            log.info(this.logPrefix(lesQueue.getId() + "") + message[0]);
        return result;
    }

    /**
     * 新增订单到VOM（开提单）
     *
     * @param lesQueue
     * @return
     */
    //6
    private Integer createOrderToLes(OrdersNew order, OrderProductsNew orderProduct, LesQueues lesQueue,
                                     MemberInvoices memberInvoice, String[] message) {
        //参数检测
        if (lesQueue == null) {
            return -1;
        }
        //检测订单信息是否符合开提单要求
        if (OrderStatus.OS_CANCEL.getCode().equals(order.getOrderStatus())) {
            return -2;//订单取消，关闭推送
        }

        //检测网单信息是否符合开提单要求
        if (orderProduct.getStatus().equals(OrderProductStatus.CANCEL_CLOSE.getCode())) {
            return -16;//网单取消，关闭推送
        }

        if (orderProduct.getStatus().equals(OrderProductStatus.COMPLETED_CLOSE)) {
            return -3;//网单已是完成关闭，取消推送
        }
        if (orderProduct.getLessOrderSn() != null && !orderProduct.getLessOrderSn().isEmpty()) {
            return -4;//已开过提单
        }
        if (orderProduct.getStatus() != null
                && orderProduct.getStatus() >= OrderProductStatus.LES_SHIPPING.getCode()) {
            return -4;//已开过提单
        }

        //
        //        //客户信息
        //        ChangeOrderCustomerInfo changeOrderCustomerInfo = null;
        //        OrderProductsAttributes orderProductsAttributes = getOrderProductsAttributes(lesQueue);
        //        if(orderProductsAttributes != null){
        //        	//是否自营
        //        	int is_self_sell = orderProductsAttributes.getIsSelfSell();
        //        	if(is_self_sell ==1){//自营转单
        //        		//获取客户id
        //        		int customer_id = orderProductsAttributes.getCustomerId();
        //        		//根据客户id查询客户信息(自营转单的客户信息)
        //        		changeOrderCustomerInfo = changeOrderCustomerInfoDao.get(customer_id);
        //        	}
        //        }

        //处理网单的库位
        String sCode = orderProduct.getSCode();
        //检查处理后的库位
        if (sCode == null || sCode.isEmpty()) {
            return -6;//网单中的库位编码不应为空
        }

        Storages storage = null;
        try {
            //检查库位（仓库）
            ServiceResult<Storages> result = stockServiceImpl.getStoragesBySCode(sCode);
            if (result == null || !result.getSuccess()) {
                message[0] = result != null ? result.getMessage() : "返回结果为null";
                log.error("开提单，检查库位（仓库）时(sCode:" + sCode + ",opid:" + orderProduct.getId()
                        + ")，调用服务失败:" + result.getMessage());
                return -13;//调用服务异常
            }
            storage = result.getResult();
            if (storage == null) {
                return -7;//库位不存在
            }
        } catch (Exception ex) {
            log.error("开提单，检查库位（仓库）时(sCode:" + sCode + ",opid:" + orderProduct.getId() + ")，发生未知异常:"
                            + ex.getMessage(),
                    ex);
            message[0] = ex.getMessage();
            return -13;
        }
        //物流模式
        Integer sdabw = 2; //2 网点 70 直配
        if (storage != null) {
            if ((new Integer(1).equals(storage.getType())
                    && "B2C".equalsIgnoreCase(orderProduct.getShippingMode()))
                    || new Integer(2).equals(storage.getType())) {//大家电库1，小家电库2
                sdabw = 70;
            }
        }
        //        TODO 自营转单二期，去掉限制
        //        if(orderProductsAttributes != null){
        //        	//是否自营
        //        	int is_self_sell = orderProductsAttributes.getIsSelfSell();
        //        	if(is_self_sell == 1)  //自营物流模式
        //            	sdabw = 70; //70直配
        //        }
        //中心代码-日日顺C码
        String centerCode = storage.getCenterCode();

        //套机列表查询
        List<InvMachineSet> imsList = null;
        try {
            InvMachineSet machineSet = new InvMachineSet();
            machineSet.setMainSku(orderProduct.getSku());
            ServiceResult<List<InvMachineSet>> stockcommresult = stockCommonService.getSubMachinesByMainSku(machineSet);
            if (stockcommresult != null && stockcommresult.getSuccess()) {
                imsList = stockcommresult.getResult();
                if (imsList == null || imsList.size() == 0) {//判断如果不是套机就做一个常规处理
                    imsList = new ArrayList<InvMachineSet>();
                    InvMachineSet ims = new InvMachineSet();
                    ims.setPosnr("10");
                    ims.setSubSku(convertToExternalSku(orderProduct.getSku()));//转换为R码
                    if (ims.getSubSku() == null || ims.getSubSku().equals("")) {
                        message[0] = "套机产品编码不能为空";
                        return -15;
                    }
                    ims.setMenge(new BigDecimal(1));
                    imsList.add(ims);
                } else {
                    for (int i = 0; i < imsList.size(); i++) {
                        //由于行号用索引号，所以不对BOM项目号为空判断
                        //                    if (imsList.get(i).getPosnr() == null || imsList.get(i).getPosnr().equals("")) {
                        //                        message[0] = "套机BOM项目号(行号)不能为空";
                        //                        return -15;
                        //                    }
                        imsList.get(i).setSubSku(convertToExternalSku(imsList.get(i).getSubSku()));//转换为R码
                        if (imsList.get(i).getSubSku() == null
                                || imsList.get(i).getSubSku().equals("")) {
                            message[0] = "套机产品编码不能为空";
                            return -15;
                        }
                        if (imsList.get(i).getMenge() == null
                                || imsList.get(i).getMenge().compareTo(new BigDecimal(0)) == 0) {
                            message[0] = "套机组件数量不能为空或为0";
                            return -15;
                        }
                    }
                }
            } else {
                message[0] = stockcommresult != null ? stockcommresult.getMessage() : "返回结果为null";
                log.error("开提单，查询套机时(sku:" + orderProduct.getSku() + ",opid:" + orderProduct.getId()
                        + ")，调用接口服务返回失败:" + message[0]);
                return -13;
            }
        } catch (Exception ex) {
            log.error("开提单，查询套机时(sku:" + orderProduct.getSku() + ",opid:" + orderProduct.getId()
                            + ")，发生未知异常:" + ex.getMessage(),
                    ex);
            message[0] = ex.getMessage();
            return -13;
        }
        //网点编码
        String accepter = "";
        try {
            Integer netPointId = orderProduct.getNetPointId();//检查网点
            if (netPointId > 0) {
                NetPoints np = null;
                ServiceResult<NetPoints> npresult = itemService.getNetPoint(netPointId);
                if (npresult == null || !npresult.getSuccess()) {
                    message[0] = npresult != null ? npresult.getMessage() : "返回结果为null";
                    log.error("开提单，获取网点编码时(netPointId:" + orderProduct.getNetPointId() + ",opid:"
                            + orderProduct.getId() + ")，调用接口服务返回失败:" + message[0]);
                    return -13;
                }
                np = npresult.getResult();
                if (np == null) {
                    return -8;//网点不存在
                }
                accepter = np.getNetPointN8();//网点8码
            }
            //    		TODO 自营转单二期，去掉限制
            //            else if(changeOrderCustomerInfo != null){ //自营转单
            //            	accepter = changeOrderCustomerInfo.getCustomerStoreCode();//客户88码
            //            }
            else {//非派工的传5码
                if (sdabw == 70) {//直配
                    LesFiveYardInfo yard = purchaseLesFiveYardsService.getBySCode(sCode);
                    if (yard != null) {
                        accepter = yard.getFiveYard(); //Les 送达方代码
                    }
                }
            }
        } catch (Exception ex) {
            log.error("开提单，获取网点编码时(netPointId:" + orderProduct.getNetPointId() + ",opid:"
                            + orderProduct.getId() + ")，发生未知异常:" + ex.getMessage(),
                    ex);
            message[0] = ex.getMessage();
            return -13;
        }

        //品牌名称
        String brandName = "";
        try {
            ServiceResult<Brands> result = itemService.getBrands(orderProduct.getBrandId());
            if (result == null || !result.getSuccess()) {
                message[0] = result != null ? result.getMessage() : "返回结果为null";
                log.error("开提单，获取品牌名称时(BrandId:" + orderProduct.getBrandId() + ",opid:"
                        + orderProduct.getId() + ")，调用接口服务返回失败:" + message[0]);
                return -13;
            }
            Brands brand = result.getResult();
            if (brand != null) {
                brandName = brand.getBrandName();
            }
        } catch (Exception ex) {
            log.error("开提单，获取品牌名称时(BrandId:" + orderProduct.getBrandId() + ",opid:"
                            + orderProduct.getId() + ")，发生未知异常:" + ex.getMessage(),
                    ex);
            message[0] = ex.getMessage();
            return -13;
        }
        //支付名称
        String paymentName = "";
        try {
            if (!StringUtil.isEmpty(order.getPaymentCode())) {
                Payments payment = null;
                ServiceResult<Payments> paymentresult = itemService
                        .getPaymentByCode(order.getPaymentCode());
                if (paymentresult == null || !paymentresult.getSuccess()) {
                    message[0] = paymentresult != null ? paymentresult.getMessage() : "返回结果为null";
                    log.error("开提单，获取支付名称时(PaymentCode:" + order.getPaymentCode() + ",opid:"
                            + orderProduct.getId() + ")，调用接口服务返回失败:" + message[0]);
                    return -13;
                }
                payment = paymentresult.getResult();
                if (payment != null) {
                    paymentName = payment.getPaymentName();
                }
            }
        } catch (Exception ex) {
            log.error("开提单，获取支付名称时(PaymentCode:" + order.getPaymentCode() + ",opid:"
                            + orderProduct.getId() + ")，发生未知异常:" + ex.getMessage(),
                    ex);
            message[0] = ex.getMessage();
            return -13;
        }

        //省市区
        List<Integer> idList = new ArrayList<Integer>();
        idList.add(order.getProvince());
        idList.add(order.getCity());
        idList.add(order.getRegion());
        Map<Integer, Regions> regionMap = this.getObjectByIds(idList);
        if (regionMap == null) {
            message[0] = "找不到对应的省市区：" + order.getProvince() + "," + order.getCity() + ","
                    + order.getRegion();
            return -12;
        }
        Regions province = regionMap.get(order.getProvince());
        Regions city = regionMap.get(order.getCity());
        Regions region = regionMap.get(order.getRegion());
        if (province == null || city == null || region == null) {
            message[0] = "找不到对应的省市区：" + order.getProvince() + "," + order.getCity() + ","
                    + order.getRegion();
            return -12;
        }

        //组装参数
        String pname = orderProduct.getProductName();//产品描述
        Integer orderType = order.getOrderType();//订单类型    普通订单-0,团购预付款订单-1,团购尾款订单-2,普通团购订单-3,单订单模式的订金-尾款订单-4
        String ddlx = "F1";//F1全款订单，F2尾款订单
        if (orderType != null && !orderType.equals(0) && !orderType.equals(3)) {//除了普通订单和普通团购订单，其他都认为是尾款订单
            ddlx = "F2";
        }
        String source = order.getSource();//订单来源
        String sourceName = "";//来源名称
        try {
            sourceName = getSourceName(source);
            if (sourceName == null || sourceName.equals("")) {
                message[0] = "未找到匹配的订单来源名称，source:" + source;
                return -13;
            }
        } catch (Exception ex) {
            log.error("开提单，获取订单来源名称时(source:" + source + ",opid:" + orderProduct.getId()
                            + ")，发生未知异常:" + ex.getMessage(),
                    ex);
            message[0] = ex.getMessage();
            return -13;
        }
        //        String sourceSn = source.equals(1) ? order.getOrderSn() : order.getSourceOrderSn();//来源订单编号，比较订单来源是否是商城订单，如果是商城订单，取订单号，如果不是取订单来源号
        String corderSn = orderProduct.getCOrderSn();//网单编号
        String orderSn = order.getOrderSn();//订单号
        //        Integer posex = 10;
        Date addDate = this.getCreateTimeForLes(order);//订单创建时间
        String payTime = DateFormatUtil.formatTime(order.getPayTime());//付款时间或付定金时间
        //        String tailPayTime = "";//尾款付款时间
        //        if (ddlx.equals("F2")) {//如果是尾款订单，就设置尾款时间
        //            tailPayTime = DateFormatUtil.formatTime(order.getTailPayTime());
        //        }

        //        String auart = "ZBCC";
        //        String kunnr = storage.getCenterCode();//售达方
        String sku = orderProduct.getSku();//sku
        sku = convertToExternalSku(sku);//转换为R码

        Integer quantity = orderProduct.getNumber();//数量
        //        String meins = "TAI";
        Integer charg = 10;//良品库-正品
        if ("AA8YE5U11".equalsIgnoreCase(sku)) {
            charg = 90;//赠品库
        }
        //订单来源为“COS”的订单，指定开单批次为40(样品机)
        if ("COS".equalsIgnoreCase(source)) {
            charg = 40;
        }
        //获取其他指定订单来源批次关系的信息  DBJ-41批次
        Item2OrderSource i2os = item2OrderSourceService.getByOrderSource(source);
        if (i2os != null && i2os.getItemProperty() != null) {
            charg = i2os.getItemProperty();
        }

        //        String augru = "";
        //        String bstkd_e = "";
        //        String posnr_e = "";
        BigDecimal unitPrice = orderProduct.getPrice();//单价
        BigDecimal productAmount = orderProduct.getProductAmount();//订单总金额    该字段算法和total的算法一样，但是优惠金额是否存储不清楚，所以总金额不计算直接取这个字段
        //2017-02-22 B2C使用Products的单价
        if ("B2C".equalsIgnoreCase(orderProduct.getShippingMode())) {
            try {
                ServiceResult<ProductsNew> result = itemService.getProductBySku(orderProduct.getSku());
                if (result == null || !result.getSuccess()) {
                    message[0] = result != null ? result.getMessage() : "返回结果为null";
                    log.error("开提单，获取产品表时(sku:" + orderProduct.getSku() + ",opid:"
                            + orderProduct.getId() + ")，调用接口服务返回失败:" + message[0]);
                    return -13;
                }
                ProductsNew product = result.getResult();
                if (product != null) {
                    unitPrice = product.getSaleGuidePrice();
                }
            } catch (Exception ex) {
                log.error("开提单，获取产品表时(sku:" + orderProduct.getSku() + ",opid:"
                                + orderProduct.getId() + ")，发生未知异常:" + ex.getMessage(),
                        ex);
                message[0] = ex.getMessage();
                return -13;
            }
            productAmount = unitPrice.multiply(new BigDecimal(orderProduct.getNumber()));
        } else {
            if (OrderType.TYPE_GROUP_ADVANCE.getValue().intValue() == orderType.intValue()
                    && orderProduct.getShippingOpporunity().intValue() == 0
                    && orderProduct.getActivityId().intValue() > 0) {
                //传金额取活动设置的商品金额
                //从促销活动表中获取价格
                ProductActivities pa = productActivitiesService.get(orderProduct.getActivityId());
                BigDecimal paPrice = this.getPriceInActivities(pa);
                if (paPrice != null) {
                    unitPrice = paPrice;
                    productAmount = unitPrice.multiply(new BigDecimal(orderProduct.getNumber()));
                }
            }
            if (OrderType.TYPE_GROUP_ADVANCE_TAIL.getValue().intValue() == orderType.intValue()
                    && orderProduct.getShippingOpporunity().intValue() == 0
                    && order.getTaobaoGroupId().intValue() > 0) {
                TaoBaoGroups taoBaoGroups = shopTaoBaoGroupsService.get(order.getTaobaoGroupId());
                if (taoBaoGroups != null) {
                    unitPrice = taoBaoGroups.getDepositAmount()
                            .add(taoBaoGroups.getBalanceAmount());
                    productAmount = unitPrice.multiply(new BigDecimal(orderProduct.getNumber()));
                }
            }
        }
        //        BigDecimal subtotal = unitPrice.multiply(new BigDecimal(quantity));//小计
        BigDecimal shipFee = orderProduct.getShippingFee();//运费
        //        BigDecimal total = subtotal.add(shipFee).subtract(orderProduct.getCouponAmount());//总计
        String consignee = order.getConsignee();//订货人
        //去掉特殊字符
        if (consignee != null) {
            consignee = consignee.replaceAll("[\\x00-\\x1f]+", "").replaceAll("[\\x7f-\\xff]+", "");
        }

        //订货人手机
        String mobile = order.getMobile();
        if (StringUtil.isEmpty(mobile) && order.getMemberId() > 0) {
            Members member = shopMembersService.get(order.getMemberId());
            if (member != null && !StringUtil.isEmpty(member.getMobile())) {
                mobile = member.getMobile();
            }
        }
        String phone = order.getPhone();//订货人电话

        //省市区名称
        String provinceName = province.getRegionName();//省
        String cityName = city.getRegionName();//市
        String regionName = region.getRegionName();//区
        String gbcode = region.getCode();//国标编码

        //--------------------- DTD 开始 ----------------
        //特殊sku（BA0A7209H）不传手机号，在时间范围内（2015-01-23~2015-02-02）
        //第二轮活动，定金订单，sku：BA0A7209H、600000MR5、CE0JW200G、CE0JC001N、GA0RQF01C；时间范围（2015-03-05 00:00:00~2015-03-28 23:59:59）
        //        String active_sku[] = new String[] { "F708V1000" };
        String h3 = "";//3H限时达标志
        //所有定金尾款订单，如果是定金发货模式，都不给物流传电话号码，默认为11个1
        //2015-04-08修改-商城订单、移动商城订单   物料号 F708V1000  型号HRO1008-5E   DTD订单标示传给HP，定金发货，尾款支付后传HP送用户  设置的时间13日00:00~20日 24:00
        if (orderType.equals(1) && orderProduct.getShippingOpporunity().equals(0)) {
            //            if (DateFormatUtil.parseByType("yyyy-MM-dd HH:mm:ss", "2015-04-13 00:00:00").before(
            //                addDate)
            //                && DateFormatUtil.parseByType("yyyy-MM-dd HH:mm:ss", "2015-04-20 23:59:59").after(
            //                    addDate)) {
            //                for (int i = 0; i < active_sku.length; i++) {
            //                    if (orderProduct.getSku().equals(active_sku[i]) || sku.equals(active_sku[i])) {
            //                        //                        mobile = "11111111111";
            //                        //                        phone = "";
            //
            //                        if (OrderSnUtil.guobiaoSet.contains(gbcode)) {//3H限时达标志-判断
            //                            h3 = "3H";
            //                        }
            //                    }
            //                }
            //            }
            //            //600000MRP	K49H定制  活动时间4月20日  00:00至5月1日 00:00
            //            if (DateFormatUtil.parseByType("yyyy-MM-dd HH:mm:ss", "2015-04-20 00:00:00").before(
            //                addDate)
            //                && DateFormatUtil.parseByType("yyyy-MM-dd HH:mm:ss", "2015-05-01 00:00:00").after(
            //                    addDate)) {
            //                if (orderProduct.getSku().equals("600000MRP") || sku.equals("600000MRP")) {
            //                    if (OrderSnUtil.guobiaoSet.contains(gbcode)) {//3H限时达标志-判断
            //                        h3 = "3H";
            //                    }
            //                }
            //            }

            //物料号F709W1000    这个物料商城渠道（ehaier、移动商城）下单时间在2015.5.22日-5月26日17:00全部默认为DTD活动
            //            if (DateFormatUtil.parseByType("yyyy-MM-dd HH:mm:ss", "2015-5-22 00:00:00").before(
            //                addDate)
            //                && DateFormatUtil.parseByType("yyyy-MM-dd HH:mm:ss", "2015-05-26 17:00:00").after(
            //                    addDate)) {
            //                if (orderProduct.getSku().equals("F709W1000") || sku.equals("F709W1000")) {
            //                    if (OrderSnUtil.guobiaoSet.contains(gbcode)) {//3H限时达标志-判断
            //                        h3 = "3H";
            //                    }
            //                }
            //            }

            mobile = "11111111111";
            phone = "";
        }
        //--------------------- DTD 结束 ----------------

        //        //2016-08-30,新增920限时达
        //        //订单来源 （商城订单、移动商城、微店、移动社交、优家移动）+地区 （三小时急速达区县表）+sku （3小时极速达选品.xlsx）+
        //        //订金付款时间 （付订金时间在 9月8日  0:00——9月17日17:00） 符合这四个条件的订金尾款订单
        //        //在 vom订单接口字段 remark1 打标 3H, HP派工接口字段 attribute2 打标 3H
        //        Long tempTime = orderProduct.getCPayTime();
        //        if ((OrderType.TYPE_GROUP_ADVANCE.getValue().intValue() == orderType.intValue())
        //            && tempTime >= 1473264000l && tempTime <= 1474102800l) {
        //            if (OrderSnUtil.get920Mark(order.getSource(), orderProduct.getSku(), gbcode)) {
        //                h3 = "3H";
        //            }
        //        }

        //        //2017-05-23,3H
        //        //        商品：CE0JK300M
        //        //        3小时急速达时间：6月1日 00:00-6月9日12:00 的定金尾款订单
        //        //        订单来源：顺逛APP、顺逛WAP、顺逛PC、商城PC、商城移动
        //        //SELECT `id` FROM `Regions` WHERE `regionType`=4 and `activeFlag`=1 and `shippingTime` in (0,1,2)
        //        long tempTime = orderProduct.getCPayTime();
        //        if ((OrderType.TYPE_GROUP_ADVANCE.getValue().intValue() == orderType.intValue())
        //            && tempTime >= 1496246400l && tempTime <= 1496980800l) {
        //            if (OrderSnUtil.get3HMark(order.getSource(), orderProduct.getSku())) {
        //                Regions streetRegion = regionsService.get(order.getStreet());
        //                if (streetRegion != null && streetRegion.getActiveFlag()
        //                    && streetRegion.getRegionType().intValue() == 4
        //                    && ("0".equals(streetRegion.getShippingTime())
        //                        || "1".equals(streetRegion.getShippingTime())
        //                        || "2".equals(streetRegion.getShippingTime()))) {
        //                    h3 = "3H";
        //                }
        //            }
        //        }

        //2017-07-03,3H
        //定金支付时间为：6月30日9:00-7月9日9:00
        long tempTime = orderProduct.getCPayTime();
        if ((OrderType.TYPE_GROUP_ADVANCE.getValue().intValue() == orderType.intValue())
                && tempTime >= 1498784400l && tempTime <= 1499562000l) {
            if (OrderSnUtil.get3HMark(order.getSource(), orderProduct.getSku(),
                    order.getRegion())) {
                h3 = "3H";
            }
        }
        //地址
        String address = order.getAddress();//地址
        //去掉特殊字符
        if (address != null) {
            address = address.replaceAll("[\\x00-\\x1f]+", "").replaceAll("[\\x7f-\\xff]+", "");
        }
        String zip = order.getZipcode();//邮编
        //付款状态
        //P1:已付款（非COD情况）
        //P2:代收货款（COD情况）
        String payStatus = order.getPaymentCode().toLowerCase().equals("cod") ? "P2" : "P1";
        //                Boolean isPaidTailAmount = this.IsPaidTailAmount(order);
        //                if (!isPaidTailAmount) {
        //                    payStatus = "P1";//如果订单没有付尾款，付款状态为P3---旧版；        新版写P1。因为钱是在线支付，不需要售后收款
        //                }
        //****************2018/8/30尾款订单需要结清尾款*************start***
        //除了普通订单和普通团购订单，其他都认为是尾款订单
        //***经跟业务确认定金尾款订单，付完尾款后再按照普通网单下发订单
        /*if (orderType != null && !orderType.equals(0) && !orderType.equals(3)&&orderProduct.getShippingOpporunity().intValue() == 2) {
            payStatus = "P3";
        }*/
        //****************2018/8/30尾款订单需要结清尾款*************end*****
        String payType = order.getPaymentCode().toLowerCase().equals("cod") ? "POS" : paymentName;// 付款方式
        String remark = order.getRemark();//客户备注
        remark = remark != null ? remark.replace("=", "~") : remark;
        //        String kunem = "";
        //        String kunz1 = "";
        //        String sdaem = "";
        //        if (!orderProduct.getSCode().equalsIgnoreCase(orderProduct.getTsCode())) {
        //            //多级库位的情况
        //            kunz1 = orderProduct.getTsCode();
        //            sdaem = "YDFH";
        //        }
        //超期免单标识
        String urlab = "";
        //        //2016-8-30 XinM 网单表是否超时免单字段获取异常，数据库是1，但走的是0的逻辑
        //        Integer isTimeoutFree = orderProductsNewService
        //            .getIsTimeoutFreeByCOrderSn(orderProduct.getCOrderSn());
        //2017-05-24 isTimeoutFree＝1 超时免单  ＝3主动超时免单
        if (orderProduct.getIsTimeoutFree().equals(1)
                || orderProduct.getIsTimeoutFree().equals(3)) {
            //        if (isTimeoutFree.intValue() == 1) {
            urlab = "F3";
            urlab = urlab + "," + h3;
        } else {
            urlab = h3;
        }
        //        String urmemo = "";
        //发票信息
        String isNeedInvoice = "2";//不需要开发票
        String invoiceType = "";
        String invoiceAcc = "";
        String invoiceAddress = "";
        String invoiceBank = "";
        String taxSpotNum = "";
        String bankCardNumber = "";//银行帐户
        //     TODO 自营转单二期，去掉限制
        //        if(orderProductsAttributes != null){
        //        	int is_self_sell = orderProductsAttributes.getIsSelfSell();
        //        	if(is_self_sell ==0){//非自营转单
        //        		if (memberInvoice != null && orderProduct.getIsReceipt().equals(1)) {//网单需要开发票
        //        			isNeedInvoice = "1";//需要开发票
        //        			invoiceType = memberInvoice.getInvoiceType() == null ? ""
        //        					: memberInvoice.getInvoiceType().toString();
        //        			invoiceAcc = memberInvoice.getInvoiceTitle();
        //        			invoiceBank = memberInvoice.getBankName();
        //        			taxSpotNum = memberInvoice.getTaxPayerNumber();
        //        			bankCardNumber = memberInvoice.getBankCardNumber();
        //        		}
        //        	}
        //        }else{
        //发票信息
        if (memberInvoice != null && orderProduct.getIsReceipt().equals(1)) {//网单需要开发票
            isNeedInvoice = "1";//需要开发票
            invoiceType = memberInvoice.getInvoiceType() == null ? ""
                    : memberInvoice.getInvoiceType().toString();
            invoiceAcc = memberInvoice.getInvoiceTitle();
            invoiceBank = memberInvoice.getBankName();
            taxSpotNum = memberInvoice.getTaxPayerNumber();
            bankCardNumber = memberInvoice.getBankCardNumber();
        }
        //        }
        Date reqDate = new Date();//调用les接口时间
        //        String add1 = "";

        //        //是否货票同行标识
        //        String add2 = orderProduct.getMakeReceiptType().equals(1) ? "货票同行" : "";
        //        if ("货票同行".equals(add2)) {
        //            //如果网单不需要开票，则不货票同行
        //            if (orderProduct.getIsReceipt().equals(0)) {
        //                add2 = "";
        //            }
        //            //如果网单金额为零，且不是使用礼品卡支付的，则不需要开票，不货票同行
        //            if (orderProduct.getProductAmount().compareTo(BigDecimal.ZERO) == 0
        //                && orderProduct.getUsedGiftCardAmount().compareTo(BigDecimal.ZERO) == 0) {
        //                add2 = "";
        //            }
        //        }

        //        String add3 = "";

        //调用接口
        try {
            //有中文的字符串长度计算方法统一按照规定长度除以3，因为vom的长度规定是按照UTF-8编码后的字节码长度计算的，一个汉字长度是3，所以按长度除以3计算
            if (log.isDebugEnabled())
                log.debug(
                        this.logPrefix(lesQueue.getId() + "") + "调用les接口参数：订单编号:" + order.getOrderSn());

            List<InputTypes> inputList = new ArrayList<InputTypes>();
            InputTypes input = new InputTypes();
            //产品名称 - 32
            if (pname == null || pname.trim().equals("")) {
                message[0] = "产品描述不能为空";
                return -15;
            }
            if (pname.length() > 18) {//vom要求最长18个中文字符
                pname = pname.substring(0, 18);
            }
            input.setPNAME(pname);
            //            //商城订单来源代码 - 30
            //            if (source.length() > 30) {
            //                source = source.substring(0, 30);
            //            }
            //            input.setSOURCE(source);
            //商城订单来源文字描述 - 1000
            if (sourceName.length() > 1000) {
                sourceName = sourceName.substring(0, 1000);
            }
            input.setSOURCEEXT(sourceName);
            //            //商城来源订单号 - 30
            //            if (sourceSn.length() > 30) {
            //                sourceSn = sourceSn.substring(0, 30);
            //            }
            //            input.setSOURCESN(sourceSn);//被订单号占用
            //商城网单编号 - 64
            if (corderSn == null || corderSn.trim().equals("")) {
                message[0] = "网单号不能为空";
                return -15;
            }
            if (corderSn.length() > 64) {
                corderSn = corderSn.substring(0, 64);
            }
            input.setBSTKD(corderSn);
            //商城订单号 - 64
            if (orderSn == null || orderSn.trim().equals("")) {
                message[0] = "订单号不能为空";
                return -15;
            }
            if (orderSn.length() > 64) {
                orderSn = orderSn.substring(0, 64);
            }
            input.setSOURCESN(orderSn);
            //            //商城订单行项目 - 6
            //            input.setPOSEX(posex.toString());

            //订单创建日期和时间
            if (addDate == null) {
                message[0] = "订单日期不能为空";
                return -15;
            }
            Calendar cl = Calendar.getInstance();
            cl.setTime(addDate);
            XMLGregorianCalendar xgcDate = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendarDate(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH) + 1,
                            cl.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
            XMLGregorianCalendar xgcTime = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendarTime(cl.get(Calendar.HOUR_OF_DAY), cl.get(Calendar.MINUTE),
                            cl.get(Calendar.SECOND), DatatypeConstants.FIELD_UNDEFINED);
            input.setAUDAT(xgcDate);//订单创建日期
            input.setAUTIM(xgcTime);//订单创建时间
            //            //单据类型 - 4
            //            if (auart.length() > 4) {
            //                auart = auart.substring(0, 4);
            //            }
            //            input.setAUART(auart);
            //            //售达方 - 10
            //            if (kunnr.length() > 10) {
            //                kunnr = kunnr.substring(0, 10);
            //            }
            //            input.setKUNNR(kunnr);
            //送达方编码 - 10
            if (accepter.length() > 10) {
                accepter = accepter.substring(0, 10);
            }
            input.setKUNWE(accepter);
            //            //物料号 - 18
            //            if (sku.length() > 18) {
            //                sku = sku.substring(0, 18);
            //            }
            //            input.setMATNR(sku); --被订单类型占用
            //2017-10-10 B2C模式传小件打标传递“XJ”
            if ("B2C".equalsIgnoreCase(orderProduct.getShippingMode())) {
                if (!StringUtil.isEmpty(ddlx)) {
                    if (ddlx.endsWith(",")) {
                        ddlx = ddlx + "XJ";
                    } else {
                        ddlx = ddlx + ",XJ";
                    }
                } else {
                    ddlx = "XJ";
                }
            }

            //订单类型 - 10
            if (ddlx.length() > 10) {
                ddlx = ddlx.substring(0, 10);
            }
            input.setMATNR(ddlx);
            //商品品牌 - 1000，可为空
            if (brandName.length() > 1000) {
                brandName = brandName.substring(0, 1000);
            }
            input.setCOMTYP(brandName);
            //订单数量 -15
            if (quantity == null || quantity == 0) {
                message[0] = "网单数量不能为空或为0";
                return -15;
            }
            input.setKWMENG(new BigDecimal(quantity));
            //            //基本单位 - 3, 非空， EA或TAI，商城不能空，
            //            if (meins.length() > 3) {
            //                meins = meins.substring(0, 3);
            //            }
            //            input.setMEINS(meins);
            //            //库存地点 - 4
            //            Boolean needFrozenCrmQty = false;
            //            if (!sCode.equalsIgnoreCase(cbsSecCode)) {
            //                needFrozenCrmQty = true;
            //            }
            //            sCode = cbsSecCode;//2013-7-22 Benio
            //            if (sCode.length() > 4) {
            //                sCode = sCode.substring(0, 4);
            //            }
            //            input.setLGORT(sCode);
            //批次 - 10, 非空（商城默认为 10）
            input.setCHARG(charg.toString());
            //特殊处理标记 - 4,(1：自提，2：配送,70:直发客户)
            input.setSDABW(sdabw.toString());
            //日日顺C码-scode转  中心代码
            if (centerCode == null || centerCode.trim().equals("")) {
                message[0] = "仓库编码不能为空";
                return -15;
            }
            input.setCENTERCODE(centerCode);
            //            //定单原因 - 3,可为空
            //            if (augru.length() > 3) {
            //                augru = augru.substring(0, 3);
            //            }
            //            input.setAUGRU(augru);
            //            //运达方的采购订单编号 - 35
            //            if (bstkd_e.length() > 35) {
            //                bstkd_e = bstkd_e.substring(0, 35);
            //            }
            //            input.setBSTKDE(bstkd_e);
            //            //优先采购订单的项目数 - 6
            //            if (posnr_e.length() > 6) {
            //                posnr_e = posnr_e.substring(0, 6);
            //            }
            //            input.setPOSNRE(posnr_e);
            input.setKBETR(unitPrice);//单价 - 12
            //            input.setKWERT(subtotal);//商品金额小计
            input.setSHIPCO(shipFee);//运费 - 12
            //            input.setKWERZ(total);//订单总金额----旧版
            input.setKWERZ(productAmount);//订单总金额----新版
            //       TODO 自营转单二期，去掉限制
            //            //自营转单
            //            if(changeOrderCustomerInfo != null){
            //            	consignee = changeOrderCustomerInfo.getContactsName();//联系人
            //            	mobile = changeOrderCustomerInfo.getContactsPhone();//联系手机
            //                phone = changeOrderCustomerInfo.getContactsMobile(); //联系电话
            //                provinceName = changeOrderCustomerInfo.getProvince();//省
            //                cityName = changeOrderCustomerInfo.getCity();//市
            //                regionName = changeOrderCustomerInfo.getRegion();//区
            //                address = changeOrderCustomerInfo.getDetailAddress(); //详细地址
            //            }
            //订货人姓名  ，收货人姓名- 64
            if (consignee == null || consignee.trim().equals("")) {
                message[0] = "收货人姓名不能为空";
                return -15;
            }
            if (consignee.length() > 21) {
                consignee = consignee.substring(0, 21);
            }
            input.setDHRXM(consignee);
            //订货人联系电话 - 32  联系电话和固定电话不能都为空，如果联系电话为空要赋值固定电话的值
            mobile = mobile == null || mobile.trim().equals("") ? phone : mobile;
            if (mobile == null || mobile.trim().equals("")) {
                message[0] = "联系电话和固定电话不能都为空";
                return -15;
            }
            if (mobile.length() > 32) {
                mobile = mobile.substring(0, 32);
            }
            input.setDHRPH(mobile);
            input.setSHRMOB(mobile);//收货人移动电话 - 32
            input.setSHRXM(consignee);//收货人姓名 - 64
            //收货人固定电话 - 32 ，可为空
            if (phone.length() > 32) {
                phone = phone.substring(0, 32);
            }
            input.setSHRTEL(phone);
            //收货人所在省 - 64
            if (provinceName.length() > 21) {
                provinceName = provinceName.substring(0, 21);
            }
            input.setPROV(provinceName);
            //收货人所在市 - 64
            if (cityName.length() > 21) {
                cityName = cityName.substring(0, 21);
            }
            input.setCITY(cityName);
            //收货人所在县/区 - 128
            if (regionName.length() > 42) {
                regionName = regionName.substring(0, 42);
            }
            input.setCOUNTY(regionName);
            //国标码 - 6
            if (gbcode.length() > 6) {
                gbcode = gbcode.substring(0, 6);
            }
            input.setGBCODE(gbcode);
            //收货地址 - 256
            if (address == null || address.trim().equals("")) {
                message[0] = "收货地址不能为空";
                return -15;
            }
            if (address.length() > 85) {
                address = address.substring(0, 85);
            }
            input.setADDRESS(address);
            //邮编 - 16
            if (zip.length() > 16) {
                zip = zip.substring(0, 16);
            }
            input.setPSTLZ(zip);
            //付款状态 - 16
            if (payStatus.length() > 16) {
                payStatus = payStatus.substring(0, 16);
            }
            input.setPAYSTE(payStatus);
            //支付类别 - 32
            if (payType.length() > 10) {
                payType = payType.substring(0, 10);
            }
            input.setPAYTYP(payType);
            //预约发货时间
            ReservationShipping rs = reservationShippingService.get(Integer.valueOf(order.getId()));//预约发货信息
            if (rs == null) {
                input.setYDDAT(null);//客户要求送货日期，可为空
                input.setYDTIME(null);//客户要求送货时间，可为空
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                cl.setTime(sdf.parse(rs.getDate()));
                xgcDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
                        cl.get(Calendar.YEAR), cl.get(Calendar.MONTH) + 1,
                        cl.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
                sdf = new SimpleDateFormat("HHmmss");
                cl.setTime(sdf.parse(rs.getTime()));
                xgcTime = DatatypeFactory.newInstance().newXMLGregorianCalendarTime(
                        cl.get(Calendar.HOUR_OF_DAY), cl.get(Calendar.MINUTE), cl.get(Calendar.SECOND),
                        DatatypeConstants.FIELD_UNDEFINED);
                input.setYDDAT(xgcDate);//客户要求送货日期，可为空
                input.setYDTIME(xgcTime);//客户要求送货时间，可为空
            }
            //客户备注 - 4000，可为空
            if (remark != null && remark.length() > 1333) {
                remark = remark.substring(0, 1333);
            }
            input.setSDMEMO(remark);
            //            //最终客户 - 10，可为空
            //            if (kunem.length() > 10) {
            //                kunem = kunem.substring(0, 10);
            //            }
            //            input.setKUNEM(kunem);
            //            //产品基地代码  - 10，可为空
            //            if (kunz1.length() > 10) {
            //                kunz1 = kunz1.substring(0, 10);
            //            }
            //            input.setKUNZ1(kunz1);
            //            //基地直发客户标记 - 10，可为空
            //            if (sdaem.length() > 10) {
            //                sdaem = sdaem.substring(0, 10);
            //            }
            //            input.setSDAEM(sdaem);
            //转运标识
            if (!StringUtil.isEmpty(orderProduct.getTsCode())) {
                if (!StringUtil.isEmpty(urlab)) {
                    if (urlab.endsWith(",")) {
                        urlab = urlab + "ZY";
                    } else {
                        urlab = urlab + ",ZY";
                    }
                } else {
                    urlab = "ZY";
                }
            }

            //超期免单标识 - 1，可为空
            if (urlab.length() > 10) {
                urlab = urlab.substring(0, 10);
            }
            input.setURLAB(urlab);
            //            //加急配送标释 - 10，可为空
            //            if (urmemo.length() > 10) {
            //                urmemo = urmemo.substring(0, 10);
            //            }
            //            input.setURMEMO(urmemo);
            //是否需要发票 - 32，可为空
            input.setINVO(isNeedInvoice);
            //发票类型 - 128，可为空
            if (invoiceType != null && invoiceType.length() > 128) {
                invoiceType = invoiceType.substring(0, 128);
            }
            input.setINVTYP(invoiceType);
            //发票抬头 - 128，可为空
            if (invoiceAcc != null) {
                invoiceAcc = invoiceAcc.trim();
            }
            if (invoiceAcc != null && invoiceAcc.length() > 42) {
                invoiceAcc = invoiceAcc.substring(0, 42);
            }
            input.setINVACC(invoiceAcc);
            //发票地址 - 128，可为空
            if (invoiceAddress != null) {
                invoiceAddress = invoiceAddress.trim();
            }
            if (invoiceAddress != null && invoiceAddress.length() > 42) {
                invoiceAddress = invoiceAddress.substring(0, 42);
            }
            input.setINVADD(invoiceAddress);
            //发票开户行 - 128，可为空
            if (invoiceBank != null && invoiceBank.length() > 42) {
                invoiceBank = invoiceBank.substring(0, 42);
            }
            input.setINVBANK(invoiceBank);
            //发票开户银行账号- 128，可为空
            if (bankCardNumber != null && bankCardNumber.length() > 128) {
                bankCardNumber = bankCardNumber.substring(0, 128);
            }
            input.setINVCARDNUMBER(bankCardNumber);
            //纳税人登记号 - 128，可为空
            if (taxSpotNum != null && taxSpotNum.length() > 128) {
                taxSpotNum = taxSpotNum.substring(0, 128);
            }
            input.setINVNUM(taxSpotNum);
            //请求时间
            cl.setTime(reqDate);
            xgcDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
                    cl.get(Calendar.YEAR), cl.get(Calendar.MONTH) + 1, cl.get(Calendar.DAY_OF_MONTH),
                    DatatypeConstants.FIELD_UNDEFINED);
            xgcTime = DatatypeFactory.newInstance().newXMLGregorianCalendarTime(
                    cl.get(Calendar.HOUR_OF_DAY), cl.get(Calendar.MINUTE), cl.get(Calendar.SECOND),
                    DatatypeConstants.FIELD_UNDEFINED);
            input.setERDAT(xgcDate);//商城订单创建日期
            input.setERZET(xgcTime);//商城订单创建时间
            //            //备用字段1 - 20，可为空
            //            if (add1.length() > 20) {
            //                add1 = add1.substring(0, 20);
            //            }
            //            input.setADD1(add1);//被付款时间占用
            //付款时间 - 20，可为空
            if (payTime.length() > 20) {
                payTime = payTime.substring(0, 20);
            }
            input.setADD1(payTime);
            //            //是否货票同行 - 30，可为空
            //            if (add2.length() > 30) {
            //                add2 = add2.substring(0, 30);
            //            }
            //            input.setADD2(add2);//被尾款付款时间占用
            //            //尾款付款时间 - 20，可为空
            //            if (tailPayTime.length() > 20) {
            //                tailPayTime = tailPayTime.substring(0, 20);
            //            }
            //            input.setADD2(tailPayTime);
            //            //备用字段3 - 50，可为空
            //            if (add3.length() > 50) {
            //                add3 = add3.substring(0, 50);
            //            }
            //            input.setADD3(add3);

            //套机列表
            input.setIMSLIST(imsList);
            inputList.add(input);

            //生成业务数据xml
            String content = accessExternalInterface.getContentXml(input);//生成xml格式的content参数
            VomInterData vomInterData = new VomInterData();
            vomInterData.setNotifyid(orderProduct.getId() + "");
            vomInterData.setNotifytime(DateFormatUtil.format(new Date()));
            vomInterData.setContent(content);

            //*********2018-06-07 添加log记录————start
            log.warn("!!网单" + corderSn + "即将调用VOM接口开提单，内容数据为：" + content+"!!");
            //*********2018-06-07 添加log记录————end

            //VOM开提单，生成开提单加密参数
            String paramLes_tem = accessExternalInterface.orderToLesParam(content, vomInterData);
            String resultXml = "";
            try {
                if (paramLes_tem == null || paramLes_tem.equals("")) {
                    message[0] = "生成VOM参数为空";
                    log.error(message[0]);
                    return -9;
                }
                //VOM新接口，调用VOM开提单    --vom
                ServiceResult<String> result = lesService.orderToLes(corderSn, paramLes_tem);
                if (result == null || !result.getSuccess()) {//调用les出异常
                    message[0] = "VOM调用返回失败，VOM错误信息:" + result != null ? result.getMessage()
                            : "调用接口返回为null";
                    log.error(message[0]);
                    return -9;
                }
                resultXml = result.getResult();
                if (resultXml == null) {
                    message[0] = "VOM调用返回结果为空";
                    log.error(message[0]);
                    return -9;
                }
            } catch (Exception ex) {
                log.error(
                        "开提单，调用VOM接口时，opid:" + orderProduct.getId() + "，发生未知异常:" + ex.getMessage(), ex);
                message[0] = "开提单，调用VOM接口时，opid:" + orderProduct.getId() + "，发生未知异常:"
                        + ex.getMessage();
                return -9;
            }
            //处理les返回结果，解析xml
            HttpResult<String> httpresult = accessExternalInterface.getLesToOrderResult(resultXml);

            //les返回结果
            if (httpresult == null || httpresult.getSuccess() == null) {//调用接口发生异常，说明resultXml=""
                log.error(this.logPrefix(lesQueue.getId() + "") + "调用VOM接口异常, 订单号id："
                        + order.getId() + ", HttpResult is null");
                message[0] = "调用VOM接口异常, 订单号id：" + order.getId() + ", 接口返回为空，HttpResult is null";
                return -9;
            }

            // false:失败
            if (!httpresult.getSuccess()) { //调用接口返回false
                message[0] = "VOM调用结果：" + httpresult.getMessage();
                return -11;
            } else {// true：成功    新的开提单不返回开提单号，开提单号直接更新网单号，在发送成功以后就更新
                try {
                    OrderProductsNew synOrderProduct = new OrderProductsNew();
                    synOrderProduct.setLessOrderSn(orderProduct.getCOrderSn());
                    synOrderProduct.setId(orderProduct.getId());
                    orderProductsNewService.updateSyncLes(synOrderProduct);
                    //--调用接口成功
                    return 0;
                } catch (Exception ex) {
                    log.error("VOM调用后，更新开提单号异常, 网单id:" + orderProduct.getId() + ",订单号id："
                                    + order.getId() + "，发生未知异常:" + ex.getMessage(),
                            ex);
                    message[0] = "VOM调用后，更新开提单号异常, 网单id:" + orderProduct.getId() + ",订单号id："
                            + order.getId() + "，发生未知异常:" + ex.getMessage();
                    return -18;
                }
            }
        } catch (Exception e) {
            log.error(this.logPrefix(lesQueue.getId() + "") + "VOM调用异常, 订单号id：" + order.getId(), e);
            message[0] = "VOM调用异常, 订单号id：" + order.getId() + e.getMessage();

            return -10;
        }
    }

    //10
    private String getSourceName(String source) {
        String sourceName = OrderSnUtil.getSourceName(source);//常用订单来源，不查数据库
        if (sourceName != null && sourceName.length() > 0) {
            return sourceName;
        }
        if (mapSource == null || mapSource.isEmpty()) {
            ServiceResult<List<InvChannel2OrderSource>> sourceListResult = stockCommonService.getAllOrder2ChannelSource();
            if (sourceListResult != null && sourceListResult.getSuccess()
                    && sourceListResult.getResult() != null) {
                List<InvChannel2OrderSource> channels = sourceListResult.getResult();
                if (channels != null && channels.size() > 0) {
                    mapSource = new HashMap<String, String>();
                    for (int k = 0; k < channels.size(); k++) {
                        mapSource.put(channels.get(k).getOrderSource(), channels.get(k).getNote());
                    }
                    return mapSource.get(source);
                }
            }
            return "";
        } else {
            return mapSource.get(source);
        }
    }

    /**
     * 转换为外部系统使用的物料编码
     *
     * @param sku 内部的物料编码
     * @return 外部的物料编码
     */
    //12
    private String convertToExternalSku(String sku) {
        if (sku == null || sku.equals("")) {
            return null;
        }
        ServiceResult<EisExternalSku> result = itemService.getBySkuType(sku, EisExternalSku.TYPE_R);
        EisExternalSku es = result.getResult();

        if (!result.getSuccess()) {
            log.error("通过itemService转换物料编码发生未知异常：" + result.getMessage());
            //throw new BusinessException("通过itemService转换物料编码发生未知异常：" + result.getMessage());
            return sku;
        } else {
            if (es != null && es.getExternalSku() != null && !es.getExternalSku().equals("")) {
                return es.getExternalSku();
            } else {
                return sku;
            }
        }
    }

    /**
     * 处理新增订单到les的结果
     *
     * @param lq
     * @param result
     * @param message
     */
    //7
    private void processResultForCreateOrderToLes(OrdersNew order, OrderProductsNew orderProduct,
                                                  LesQueues lq, Integer result, String[] message) {
        String logMessage = "";
        switch (result) {
            case 0: //创建成功
                lq.setSuccess(1);
                lq.setSuccessTime(new Date().getTime() / 1000);
                lq.setIsLock(0);
                message[0] = "同步订单到VOM成功！notifyid=" + orderProduct.getId();
                logMessage = message[0];
                break;
            case -1:
                lq.setIsStop(1);//不再同步
                message[0] = "队列对象为null";
                logMessage = message[0];
                break;
            case -2:
                lq.setIsStop(1);//不再同步
                message[0] = "订单取消，关闭推送";
                logMessage = message[0];
                break;
            case -16:
                lq.setIsStop(1);//不再同步
                message[0] = "网单取消，关闭推送";
                logMessage = message[0];
                break;
            case -3:
                lq.setIsStop(1);//不再同步
                message[0] = "网单已是完成关闭，取消推送";
                logMessage = message[0];
                break;
            case -4:
                lq.setSuccess(1);
                message[0] = "已开过提单";
                logMessage = message[0];
                break;
            case -5:
                //                lq.setIsLock(1);//留给1小时同步一次的job，下次同步
                message[0] = "订单region或者sku有误，或者找不到对应的库存";
                logMessage = message[0];
                break;
            case -6:
                //                lq.setIsLock(1);//留给1小时同步一次的job，下次同步
                message[0] = "网单中的库位编码不应为空";
                logMessage = "有异常,网单中的库位编码不应为空";
                break;
            case -7:
                //                lq.setIsLock(1);//留给1小时同步一次的job，下次同步
                message[0] = "库位不存在";
                logMessage = "storage not exists";
                break;
            case -8:
                //                lq.setIsLock(1);//留给1小时同步一次的job，下次同步
                message[0] = "网点不存在";
                logMessage = "netPoint not exsits";
                break;
            case -9://les返回信息为空,下次重新同步，如果重试5次还不行，则留给1小时同步一次的job
                //                if (lq.getCount().compareTo(5) >= 0) {
                //                    lq.setIsLock(1);//留给1小时同步一次的job，下次同步
                //                }
                logMessage = message[0];
                break;
            case -10://les调用异常,下次重新同步，如果重试5次还不行，则留给1小时同步一次的job
                //                if (lq.getCount().compareTo(5) >= 0) {
                //                    lq.setIsLock(1);//留给1小时同步一次的job，下次同步
                //                }
                logMessage = message[0];
                break;
            case -11://les调用结果不正确，如没有库存等
                //                lq.setIsLock(1);//留给1小时同步一次的job，下次同步
                logMessage = message[0];
                break;
            case -12://处理cbs库位时，发生异常
                //                lq.setIsLock(1);//留给1小时同步一次的job，下次同步
                lq.setIsStop(1);//没有行政区不在同步
                logMessage = message[0];
                break;
            case -13://查询相关service服务时发生异常
                //                lq.setIsLock(1);//留给1小时同步一次的job，下次同步
                logMessage = message[0];
                break;
            case -15://查询相关service服务时发生异常
                //                lq.setIsLock(1);//留给1小时同步一次的job，下次同步
                logMessage = message[0];
                break;
            case -18://更新lesordersn异常
                //                lq.setIsLock(1);//留给1小时同步一次的job，下次同步
                logMessage = message[0];
                break;
            default:
                break;
        }
        try {
            lq.setCount(lq.getCount() + 1);//累加同步次数
            lq.setLastMessage(message[0]);
            lq.setLastTryTime(new Date().getTime() / 1000);
            lesQueuesService.updateAfterSyncLes(lq);
        } catch (Exception ex) {
            log.error("VOM调用后更新lesqueue异常, 网单id:" + orderProduct.getId() + ",订单号id：" + order.getId()
                            + "，发生未知异常:" + ex.getMessage(),
                    ex);
            logMessage = "VOM调用后更新lesqueue异常, 网单id:" + orderProduct.getId() + ",订单号id："
                    + order.getId() + "，发生未知异常:" + ex.getMessage();
        }

        //记录订单操作日志
        this.insertOrderProductLog(order, orderProduct, logMessage, "同步到VOM");
    }

    /**
     * 新增 订单明细操作日志
     *
     * @param order
     * @param orderProduct
     * @param remark
     * @param changeLog
     */
    public void insertOrderProductLog(OrdersNew order, OrderProductsNew orderProduct, String remark,
                                      String changeLog) {
        if (remark != null && remark.length() > 250) {
            remark = remark.substring(0, 250) + "...";
        }
        OrderOperateLogs oplog = new OrderOperateLogs();
        oplog.setChangeLog(StringUtil.isEmpty(changeLog) ? "" : changeLog);
        oplog.setLogTime(((Long) (new Date().getTime() / 1000)).intValue());
        oplog.setNetPointId(orderProduct.getNetPointId());
        oplog.setOperator("系统");
        oplog.setOrderId(orderProduct.getOrderId());
        oplog.setOrderProductId(orderProduct.getId());
        oplog.setPaymentStatus(order.getPaymentStatus());
        oplog.setRemark(StringUtil.isEmpty(remark) ? "" : remark);
        oplog.setSiteId(1);
        oplog.setStatus(orderProduct.getStatus());

        try {
            orderOperateLogsShopService.insert(oplog);
        } catch (Exception e) {
            log.error("插入操作日志时出现异常：", e);
        }
    }

    /**
     * 获取LES要用的订单创建时间
     *
     * @param order
     * @return
     */
    //11
    private Date getCreateTimeForLes(OrdersNew order) {
        if (order.getIsCod().equals(1)) {
            //COD订单，使用COD确认时间
            if (order.getCodConfirmTime() > 0) {
                return new Date((long) order.getCodConfirmTime() * 1000);
            }
        } else {
            //非COD订单，使用付款时间
            if (order.getPayTime() > 0) {
                return new Date((long) order.getPayTime() * 1000);
            }
        }

        return new Date((long) order.getAddTime() * 1000);
    }

    /**
     * 返回待发送到les的队列
     *
     * @param topX
     * @return
     */
    //2
    public List<LesQueues> getSendQueues(Integer topX) {
        List<LesQueues> lq = null;
        try {
            lq = lesQueuesService.getSendQueues(topX);
        } catch (Exception e) {
            log.error("获取开提单队列数据出现异常：", e);
        }
        return lq;
    }

//    /**
//     * 同步到vom后，更新相关信息
//     * @param lesQueue
//     * @return
//     */
//    public int updateAfterSyncLes(LesQueues lesQueue) {
//        int i = 0;
//        try {
//            i = lesQueuesService.updateAfterSyncLes(lesQueue);
//        } catch (Exception e) {
//            log.error("发送vom后，更新信息出现异常：", e);
//        }
//        return i;
//    }

    /**
     * 根据id list 获取对象列表
     *
     * @param idList
     * @return
     */
    public List<Regions> getByIds(List<Integer> idList) {
        String ids = "";
        if (idList == null) {
            return null;
        }
        for (Integer id : idList) {
            if (!ids.isEmpty()) {
                ids += ",";
            }
            ids += id.toString();
        }
        return regionsService.getByIds(ids);
    }

    /**
     * 根据region id列表获取 对象列表
     *
     * @param idList
     * @return
     */
    //9
    public Map<Integer, Regions> getObjectByIds(List<Integer> idList) {
        List<Regions> list = this.getByIds(idList);
        if (list == null || list.size() == 0) {
            return null;
        }

        Map<Integer, Regions> map = new HashMap<Integer, Regions>();
        for (Regions r : list) {
            map.put(r.getId(), r);
        }
        return map;
    }

    /**
     * 判断指定的订单是否已付尾款
     * @param order
     * @return
     */
    //?
//    public Boolean IsPaidTailAmount(OrdersNew order) {
//        if (order == null) {
//            return false;
//        }
//
//        if (!order.getOrderType().equals(OrderType.TYPE_GROUP_ADVANCE.getValue())
//            && !order.getOrderType().equals(OrderType.TYPE_GROUP_ADVANCE_TAIL.getValue())) {
//            return true;//如果订单类型既不是订金订单，也不是一个订单模式的订金尾款订单，则认为已付尾款
//        }
//
//        GroupOrders gOrder = this.getByDepositOrderId(order.getId());
//        return this.IsPaidTailAmount(gOrder, order);
//    }

    /**
     * 根据定金订单Id获取团购订单
     * @param depositOrderId
     * @return
     */
    //?
//    public GroupOrders getByDepositOrderId(Integer depositOrderId) {
//        GroupOrders groupOrders = null;
//        try {
//            groupOrders = groupordersNewService.getByDepositOrderId(depositOrderId);
//        } catch (Exception e) {
//            log.error("获取团购订单出现异常：", e);
//        }
//        return groupOrders;
//    }

    /**
     * 判断指定的订单是否已付尾款
     * @param gOrder
     * @param order
     * @return
     */
    //?
//    public Boolean IsPaidTailAmount(GroupOrders gOrder, Orders order) {
//        if (order == null || gOrder == null) {
//            return false;
//        }
//
//        if (!order.getOrderType().equals(OrderType.TYPE_GROUP_ADVANCE.getValue())
//            && !order.getOrderType().equals(OrderType.TYPE_GROUP_ADVANCE_TAIL.getValue())) {
//            return true;//如果订单类型既不是订金订单，也不是一个订单模式的订金尾款订单，则认为已付尾款
//        }
//
//        //尾款订单已匹配，且订单已付款
//        if (gOrder.getStatus().equals(20)
//            && order.getPaymentStatus().equals(PaymentStatus.PS_PAID.getValue())) {
//            return true;
//        }
//
//        return false;
//    }
    //?
//    public void paytimeToLes() {
//        try {
//            //获取待同步的列表，每次1000条
//            List<OrderQueues> orderQueuesList = getOrderQueues(1000);
//            if (orderQueuesList == null || orderQueuesList.size() == 0) {
//                log.info("[ord-les-paytime] 没有需要同步到Les的订单付款时间。");
//                return;
//            }
//            //加入多线程执行
//            ExecutePaytimeToLes executePaytimeToLes = new ExecutePaytimeToLes();
//            //分组大小100条(如果大于10小于等于100,分两组),加入多线程失败重试3次,如果失败则本线程自己执行
//            int splitSize = 100;
//            int size = orderQueuesList.size();
//            if (size > 10 && size <= splitSize) {
//                splitSize = size / 2 + 1;
//            }
//            new MultiThreadTool<OrderQueues>().processJobs(executePaytimeToLes, threadHelper, log,
//                orderQueuesList, splitSize, 3);
//        } catch (Exception e) {
//            log.error("同步付款时间出现异常：", e);
//        }
//    }

//    public void tailPaytimeToLes() {
//        try {
//            //获取待同步的列表，每次1000条
//            List<GroupOrders> groupOrdersList = getGroupOrdersQueues(1000);
//            if (groupOrdersList == null || groupOrdersList.size() == 0) {
//                log.info("[tailpaytime-to-les] 没有需要同步到Les的订单付尾款时间。");
//                return;
//            }
//            //加入多线程执行
//            ExecuteTailPaytimeToLes executeTailPaytimeToLes = new ExecuteTailPaytimeToLes();
//            //分组大小100条(如果大于10小于等于100,分两组),加入多线程失败重试3次,如果失败则本线程自己执行
//            int splitSize = 100;
//            int size = groupOrdersList.size();
//            if (size > 10 && size <= splitSize) {
//                splitSize = size / 2 + 1;
//            }
//            new MultiThreadTool<GroupOrders>().processJobs(executeTailPaytimeToLes, threadHelper,
//                log, groupOrdersList, splitSize, 3);
//        } catch (Exception e) {
//            log.error("同步尾款时间出现异常：", e);
//        }
//    }

//    public void corderStatusToLes() {
//        try {
//            //获取待同步的列表，每次1000条
//            List<CorderStatusToLes> list = corderStatusToLesDao.findNeedSendToLes(1000);
//            if (list == null || list.size() == 0) {
//                log.info("[tailpaytime-to-les] 没有需要同步商城状态给LES");
//                return;
//            }
//            //加入多线程执行
//            ExecuteCorderStatusToLes executeTailPaytimeToLes = new ExecuteCorderStatusToLes();
//            //分组大小100条(如果大于10小于等于100,分两组),加入多线程失败重试3次,如果失败则本线程自己执行
//            int splitSize = 100;
//            int size = list.size();
//            if (size > 10 && size <= splitSize) {
//                splitSize = size / 2 + 1;
//            }
//            new MultiThreadTool<CorderStatusToLes>().processJobs(executeTailPaytimeToLes,
//                threadHelper, log, list, splitSize, 3);
//        } catch (Exception e) {
//            log.error("商城推送状态给LES出现异常：", e);
//        }
//    }

//    public void syncPaytimeToLesThread(List<OrderQueues> orderQueuesList) {
//        if (orderQueuesList != null && orderQueuesList.size() > 0) {
//            for (OrderQueues orderQueues : orderQueuesList) {
//                if (orderQueues == null) {
//                    continue;
//                }
//                try {
//                    ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
//                    this.syncPaytimeToLes(orderQueues);
//                } catch (Exception e) {
//                    log.error("[paytime-to-les] [" + orderQueues.getOrderProductId().toString()
//                              + "]" + "orderQueues同步到物流出错",
//                        e);
//                } finally {
//                    ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
//                }
//            }
//        } else {
//            log.info("接收同步到les的定金尾款订单列表数据为空。");
//        }
//    }

//    public void syncTailPaytimeToLesThread(List<GroupOrders> groupOrdersList) {
//        if (groupOrdersList != null && groupOrdersList.size() > 0) {
//            for (GroupOrders groupOrders : groupOrdersList) {
//                if (groupOrders == null) {
//                    continue;
//                }
//                try {
//                    ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
//                    this.syncTailPaytimeToLes(groupOrders);
//                } catch (Exception e) {
//                    log.error(
//                        "[tailpaytime-to-les] [" + groupOrders.getDepositOrderProductId().toString()
//                              + "]" + "orderQueues同步到物流出错",
//                        e);
//                } finally {
//                    ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
//                }
//            }
//        } else {
//            log.info("接收同步到les的定金尾款订单列表数据为空。");
//        }
//    }

//    public void syncCorderStatusToLesThread(List<CorderStatusToLes> groupOrdersList) {
//        if (groupOrdersList != null && groupOrdersList.size() > 0) {
//            for (CorderStatusToLes groupOrders : groupOrdersList) {
//                if (groupOrders == null) {
//                    continue;
//                }
//                try {
//                    ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
//                    this.syncCorderStatusToLes(groupOrders);
//                } catch (Exception e) {
//                    log.error("[CorderStatusToLes-to-les] [" + groupOrders.getId().toString() + "]"
//                              + "groupOrders同步商城状态到LES出错",
//                        e);
//                } finally {
//                    ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
//                }
//            }
//        } else {
//            log.info("接收同步商城状态到LES列表数据为空。");
//        }
//    }

    /**
     * 物流发送付款时间
     *
     * @param groupOrder
     * @return
     */
//    private void syncPaytimeToLes(OrderQueues orderQueues) {
//
//        Integer orderProductId = orderQueues.getOrderProductId();//网单id
//        OrderProducts orderProduct = orderProductsNewService.get(orderProductId);
//        if (orderProduct == null) {
//            orderQueues.setLastMessage("网单不存在");
//            orderQueues.setCount(orderQueues.getCount() + 1);
//            orderQueues.setIsStop(1);
//            orderQueues.setLastTryTime((new Date().getTime() / 1000));
//            orderQueuesDao.update(orderQueues);
//            log.error(this.logPrefix("ord-les-paytime", orderQueues.getId() + "") + "网单"
//                      + orderProductId + "不存在");
//            return;//网单不存在
//        }
//        Orders order = ordersNewService.get(orderProduct.getOrderId());
//        if (order == null) {
//            orderQueues.setLastMessage("订单不存在");
//            orderQueues.setCount(orderQueues.getCount() + 1);
//            orderQueues.setIsStop(1);
//            orderQueues.setLastTryTime((new Date().getTime() / 1000));
//            orderQueuesDao.update(orderQueues);
//            log.error(this.logPrefix("ord-les-paytime", orderQueues.getId() + "") + "订单"
//                      + orderProduct.getOrderId() + "不存在");
//            return;//订单不存在
//        }
//        if (OrderStatus.OS_CANCEL.getCode().equals(order.getOrderStatus())) {
//            orderQueues.setLastMessage("订单取消");
//            orderQueues.setCount(orderQueues.getCount() + 1);
//            orderQueues.setIsStop(1);
//            orderQueues.setLastTryTime((new Date().getTime() / 1000));
//            orderQueuesDao.update(orderQueues);
//            return;//订单取消
//        }
//
//        if (orderProduct.getStatus().equals(OrderProductStatus.CANCEL_CLOSE.getCode())) {
//            orderQueues.setLastMessage("网单取消");
//            orderQueues.setCount(orderQueues.getCount() + 1);
//            orderQueues.setIsStop(1);
//            orderQueues.setLastTryTime((new Date().getTime() / 1000));
//            orderQueuesDao.update(orderQueues);
//            return;//网单取消
//        }
//
//        if (order.getIsCod().equals(1)) {
//            orderQueues.setLastMessage("货到付款订单没有付款时间");
//            orderQueues.setCount(orderQueues.getCount() + 1);
//            orderQueues.setIsLock(1);
//            orderQueues.setLastTryTime((new Date().getTime() / 1000));
//            orderQueuesDao.update(orderQueues);
//            return;//货到付款订单没有付款时间
//        }
//
//        if (orderProduct.getStatus() < OrderProductStatus.LES_SHIPPING.getCode()) {
//            orderQueues.setLastMessage("开提单后才能传LES");//这种情况不计数
//            orderQueues.setLastTryTime((new Date().getTime() / 1000));
//            orderQueuesDao.update(orderQueues);
//            return;//开提单后才能传LES
//        }
//
//        if (!(order.getPayTime() != null && order.getPayTime().intValue() > 0)) {
//            orderQueues.setLastMessage("付款时间为空");
//            orderQueues.setCount(orderQueues.getCount() + 1);
//            orderQueues.setLastTryTime((new Date().getTime() / 1000));
//            orderQueuesDao.update(orderQueues);
//            log.error(this.logPrefix("ord-les-paytime", orderQueues.getId() + "") + "网单"
//                      + orderProductId + "付款时间为空");
//            return;//付款时间为空
//        }
//        Integer orderType = order.getOrderType();//订单类型    普通订单-0,团购预付款订单-1,团购尾款订单-2,普通团购订单-3,单订单模式的订金-尾款订单-4
//        String ddlx = "F1";//F1全款订单，F2尾款订单
//        if (orderType != null && !orderType.equals(0) && !orderType.equals(3)) {//除了普通订单和普通团购订单，其他都认为是尾款订单
//            ddlx = "F2";
//        }
//        String add1 = "1";//1 代表全款;2 代表定金全款;3 代表定金部分款
//        if (ddlx.equals("F2")) {
//            if (orderProduct.getShippingOpporunity().equals(1)) {
//                add1 = "2";//尾款发货
//            } else {
//                add1 = "3";//定金发货
//            }
//        }
//        //中心代码-日日顺C码
//        String centerCode = "";
//        //处理网单的库位
//        String sCode = orderProduct.getSCode();
//        //检查处理后的库位
//        Storages storage = null;
//        if (sCode != null && !sCode.isEmpty()) {
//            try {
//                //检查库位（仓库）
//                ServiceResult<Storages> result = stockService.getStoragesBySCode(sCode);
//                if (result == null || !result.getSuccess()) {
//                    String message = result != null ? result.getMessage() : "检查库位服务返回结果为null";
//                    orderQueues.setLastMessage(message);
//                    orderQueues.setCount(orderQueues.getCount() + 1);
//                    orderQueues.setLastTryTime((new Date().getTime() / 1000));
//                    orderQueuesDao.update(orderQueues);
//                    log.error("给les发送付款时间时，检查库位（仓库）时(sCode:" + sCode + ",opid:"
//                              + orderProduct.getId() + ")，调用服务失败:" + message);
//                    return;//调用服务异常
//                }
//                storage = result.getResult();
//                if (storage != null) {
//                    centerCode = storage.getCenterCode();
//                }
//            } catch (Exception ex) {
//                String message = ex.getMessage();
//                orderQueues.setLastMessage(message);
//                orderQueues.setCount(orderQueues.getCount() + 1);
//                orderQueues.setLastTryTime((new Date().getTime() / 1000));
//                orderQueuesDao.update(orderQueues);
//                log.error("给les发送付款时间时，检查库位（仓库）时(sCode:" + sCode + ",opid:" + orderProduct.getId()
//                          + ")，发生未知异常:" + ex.getMessage(),
//                    ex);
//                return;
//            }
//        }
//        //物流模式
//        Integer sdabw = 2;//2 网点 70 直配
//        if (storage != null) {
//            if ((new Integer(1).equals(storage.getType())
//                 && "B2C".equalsIgnoreCase(orderProduct.getShippingMode()))
//                || new Integer(2).equals(storage.getType())) {//大家电库1，小家电库2
//                sdabw = 70;
//            }
//        }
//        //送达方代码
//        String accepter = "";
//        try {
//            Integer netPointId = orderProduct.getNetPointId();//检查网点
//            if (netPointId > 0) {
//                NetPoints np = null;
//                ServiceResult<NetPoints> npresult = itemService.getNetPoint(netPointId);
//                if (npresult == null || !npresult.getSuccess()) {
//                    String message = npresult != null ? npresult.getMessage() : "检查网点服务返回结果为null";
//                    orderQueues.setLastMessage(message);
//                    orderQueues.setCount(orderQueues.getCount() + 1);
//                    orderQueues.setLastTryTime((new Date().getTime() / 1000));
//                    orderQueuesDao.update(orderQueues);
//                    log.error("给les发送付款时间时，获取网点编码时(netPointId:" + orderProduct.getNetPointId()
//                              + ",opid:" + orderProduct.getId() + ")，调用接口服务返回失败:" + message);
//                    return;
//                }
//                np = npresult.getResult();
//                if (np != null) {
//                    accepter = np.getNetPointN8();//网点8码
//                }
//            } else {//非派工的传5码
//                if (sdabw == 70) {//直配
//                    LesFiveYards yard = purchaseLesFiveYardsService.getBySCode(sCode);
//                    if (yard != null) {
//                        accepter = yard.getFiveYard(); //Les 送达方代码
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            String message = ex.getMessage();
//            orderQueues.setLastMessage(message);
//            orderQueues.setCount(orderQueues.getCount() + 1);
//            orderQueues.setLastTryTime((new Date().getTime() / 1000));
//            orderQueuesDao.update(orderQueues);
//            log.error("给les发送付款时间时，获取网点编码时(netPointId:" + orderProduct.getNetPointId() + ",opid:"
//                      + orderProduct.getId() + ")，发生未知异常:" + ex.getMessage(),
//                ex);
//            return;
//        }
//
//        try {
//            List<QueryPayTimeToLes> param = new ArrayList<QueryPayTimeToLes>();
//            QueryPayTimeToLes qptl = new QueryPayTimeToLes();
//            qptl.setBSTNK(orderProduct.getCOrderSn());//网单号
//            qptl.setBSTKD(orderProduct.getCOrderSn());//网单号
//            qptl.setPOSNR("PY");//W4=尾款/PY=订金
//            qptl.setSOURCE("HAIERSC");
//            qptl.setSOURCESN(order.getOrderSn());//订单号
//            String orderpaytime = DateFormatUtil.formatTime(order.getPayTime());
//            String paydate = orderpaytime != null && orderpaytime.length() > 10
//                ? orderpaytime.substring(0, 10) : "";
//            String paytime = orderpaytime != null && orderpaytime.length() > 11
//                ? orderpaytime.substring(11) : "";
//            qptl.setCRDAT(paydate);//付尾款日期
//            qptl.setCRZET(paytime);//付尾款时间
//            qptl.setKUNNR(centerCode);//中心
//            qptl.setKUNWE(accepter);//送达方
//            qptl.setNAME1("HAIERSC");
//            qptl.setMESSAGE("订单已付款");
//            qptl.setADD1(add1);//1 代表全款;2 代表定金全款;3 代表定金部分款
//            //订单编号  网单编号 订单类型
//            param.add(qptl);
//
//            ServiceResult<Boolean> result = lesService.paytimeToLes(order.getOrderSn(), param);
//            if (result == null || !result.getSuccess()) {
//                String message = "给les发送付款时间时，接口错误信息:" + result != null ? result.getMessage()
//                    : "调用接口返回为null";
//                orderQueues.setLastMessage(message);
//                orderQueues.setCount(orderQueues.getCount() + 1);
//                orderQueues.setLastTryTime((new Date().getTime() / 1000));
//                orderQueuesDao.update(orderQueues);
//                log.error(message);
//                return;
//            }
//            if (result.getSuccess()) {
//                orderQueues.setLastMessage("操作成功");
//                orderQueues.setSendLesSuccess(1);
//                orderQueues.setCount(orderQueues.getCount() + 1);
//                orderQueues.setSuccessTime((new Date().getTime() / 1000));
//                orderQueues.setLastTryTime((new Date().getTime() / 1000));
//                orderQueuesDao.update(orderQueues);
//            }
//        } catch (Exception ex) {
//            String message = ex.getMessage();
//            orderQueues.setLastMessage(message);
//            orderQueues.setCount(orderQueues.getCount() + 1);
//            orderQueues.setLastTryTime((new Date().getTime() / 1000));
//            orderQueuesDao.update(orderQueues);
//            log.error(
//                "给les发送付款时间时，调用Les接口时，opid:" + orderProduct.getId() + "，发生未知异常:" + ex.getMessage(),
//                ex);
//        }
//    }

    /**
     * 商城推送状态给LES
     * @param corderStatusToLes
     * @return
     */
//    private void syncCorderStatusToLes(CorderStatusToLes corderStatusToLes) {
//        Integer orderProductId = corderStatusToLes.getOrderproductid();//网单id
//        OrderProducts orderProduct = orderProductsNewService.get(orderProductId);
//        if (orderProduct == null) {
//            corderStatusToLes.setLastmessage("网单不存在");
//            corderStatusToLes.setCount(
//                corderStatusToLes.getCount() == null ? 1 : corderStatusToLes.getCount() + 1);
//            corderStatusToLes.setSuccess(2);
//            corderStatusToLesDao.updateByPrimaryKey(corderStatusToLes);
//            log.error(this.logPrefix("ord-les-paytime", corderStatusToLes.getId() + "") + "网单"
//                      + orderProductId + "不存在");
//            return;//网单不存在
//        }
//        //        if("3W".equalsIgnoreCase(orderProduct.getStockType())){
//        //        	 corderStatusToLes.setLastmessage("3W网单不推送");
//        //             corderStatusToLes.setCount(
//        //                 corderStatusToLes.getCount() == null ? 1 : corderStatusToLes.getCount() + 1);
//        //             corderStatusToLes.setSuccess(2);
//        //             corderStatusToLesDao.updateByPrimaryKey(corderStatusToLes);
//        //             log.error(this.logPrefix("ord-les-paytime", corderStatusToLes.getId() + "") + "网单"
//        //                       + orderProductId + "3W网单不推送");
//        //             return;
//        //        }
//        //2017-02-04 如果3W单子，并且OrderRepairs表typeFlag字段不等于 4 （逆向的），需要推送
//        if ("3W".equalsIgnoreCase(orderProduct.getStockType())
//            && "TS".equalsIgnoreCase(corderStatusToLes.getCorderflag())) {
//            OrderRepairs ors = orderRepairsDao
//                .getOrderRepairByRepairSn(corderStatusToLes.getCordersn());
//            if (ors != null && ors.getTypeFlag() != null && ors.getTypeFlag().intValue() == 4) {
//                corderStatusToLes.setLastmessage("3W网单逆向拒收不推送");
//                corderStatusToLes.setCount(
//                    corderStatusToLes.getCount() == null ? 1 : corderStatusToLes.getCount() + 1);
//                corderStatusToLes.setSuccess(2);
//                corderStatusToLesDao.updateByPrimaryKey(corderStatusToLes);
//                log.error(this.logPrefix("ord-les-paytime", corderStatusToLes.getId() + "") + "网单"
//                          + orderProductId + "3W网单逆向拒收不推送");
//                return;
//            }
//        }
//        Orders order = ordersNewService.get(orderProduct.getOrderId());
//        if (order == null) {
//            corderStatusToLes.setLastmessage("订单不存在");
//            corderStatusToLes.setCount(
//                corderStatusToLes.getCount() == null ? 1 : corderStatusToLes.getCount() + 1);
//            corderStatusToLes.setSuccess(2);
//            corderStatusToLesDao.updateByPrimaryKey(corderStatusToLes);
//            log.error(this.logPrefix("ord-les-paytime", corderStatusToLes.getId() + "") + "订单"
//                      + orderProduct.getOrderId() + "不存在");
//            return;//订单不存在
//        }
//
//        Integer orderType = order.getOrderType();//订单类型    普通订单-0,团购预付款订单-1,团购尾款订单-2,普通团购订单-3,单订单模式的订金-尾款订单-4
//        String ddlx = "F1";//F1全款订单，F2尾款订单
//        if (orderType != null && !orderType.equals(0) && !orderType.equals(3)) {//除了普通订单和普通团购订单，其他都认为是尾款订单
//            ddlx = "F2";
//        }
//        String add1 = "1";//1 代表全款;2 代表定金全款;3 代表定金部分款
//        if (ddlx.equals("F2")) {
//            if (orderProduct.getShippingOpporunity().equals(1)) {
//                add1 = "2";//尾款发货
//            } else {
//                add1 = "3";//定金发货
//            }
//        }
//        //中心代码-日日顺C码
//        String centerCode = "";
//        //处理网单的库位
//        String sCode = orderProduct.getSCode();
//        //检查处理后的库位
//        Storages storage = null;
//        if (sCode != null && !sCode.isEmpty()) {
//            try {
//                //检查库位（仓库）
//                ServiceResult<Storages> result = stockService.getStoragesBySCode(sCode);
//                if (result == null || !result.getSuccess()) {
//                    String message = result != null ? result.getMessage() : "检查库位服务返回结果为null";
//                    corderStatusToLes.setLastmessage(message);
//                    corderStatusToLes.setCount(corderStatusToLes.getCount() == null ? 1
//                        : corderStatusToLes.getCount() + 1);
//                    corderStatusToLesDao.updateByPrimaryKey(corderStatusToLes);
//                    log.error("给les发送付款时间时，检查库位（仓库）时(sCode:" + sCode + ",opid:"
//                              + orderProduct.getId() + ")，调用服务失败:" + message);
//                    return;//调用服务异常
//                }
//                storage = result.getResult();
//                if (storage != null) {
//                    centerCode = storage.getCenterCode();
//                }
//            } catch (Exception ex) {
//                String message = ex.getMessage();
//                corderStatusToLes.setLastmessage(message);
//                corderStatusToLes.setCount(
//                    corderStatusToLes.getCount() == null ? 1 : corderStatusToLes.getCount() + 1);
//                corderStatusToLesDao.updateByPrimaryKey(corderStatusToLes);
//                log.error("给les发送付款时间时，检查库位（仓库）时(sCode:" + sCode + ",opid:" + orderProduct.getId()
//                          + ")，发生未知异常:" + ex.getMessage(),
//                    ex);
//                return;
//            }
//        } else {
//            corderStatusToLes.setLastmessage("网点id：" + orderProduct.getId() + " 库位编码为空");
//            corderStatusToLes.setCount(
//                corderStatusToLes.getCount() == null ? 1 : corderStatusToLes.getCount() + 1);
//            corderStatusToLesDao.updateByPrimaryKey(corderStatusToLes);
//            return;
//        }
//        //物流模式
//        Integer sdabw = 2;//2 网点 70 直配
//        if (storage != null) {
//            if ((new Integer(1).equals(storage.getType())
//                 && "B2C".equalsIgnoreCase(orderProduct.getShippingMode()))
//                || new Integer(2).equals(storage.getType())) {//大家电库1，小家电库2
//                sdabw = 70;
//            }
//        }
//
//        //送达方代码
//        String accepter = "";
//        try {
//            Integer netPointId = orderProduct.getNetPointId();//检查网点
//            if (netPointId > 0) {
//                NetPoints np = null;
//                ServiceResult<NetPoints> npresult = itemService.getNetPoint(netPointId);
//                if (npresult == null || !npresult.getSuccess()) {
//                    String message = npresult != null ? npresult.getMessage() : "检查网点服务返回结果为null";
//                    corderStatusToLes.setLastmessage(message);
//                    corderStatusToLes.setCount(corderStatusToLes.getCount() == null ? 1
//                        : corderStatusToLes.getCount() + 1);
//                    corderStatusToLesDao.updateByPrimaryKey(corderStatusToLes);
//                    log.error("给les发送付款时间时，获取网点编码时(netPointId:" + orderProduct.getNetPointId()
//                              + ",opid:" + orderProduct.getId() + ")，调用接口服务返回失败:" + message);
//                    return;
//                }
//                np = npresult.getResult();
//                if (np != null) {
//                    accepter = np.getNetPointN8();//网点8码
//                }
//            } else {//非派工的传5码
//                if (sdabw == 70) {//直配
//                    LesFiveYards yard = purchaseLesFiveYardsService.getBySCode(sCode);
//                    if (yard != null) {
//                        accepter = yard.getFiveYard(); //Les 送达方代码
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            String message = ex.getMessage();
//            corderStatusToLes.setLastmessage(message);
//            corderStatusToLes.setCount(
//                corderStatusToLes.getCount() == null ? 1 : corderStatusToLes.getCount() + 1);
//            corderStatusToLesDao.updateByPrimaryKey(corderStatusToLes);
//            log.error("给les发送付款时间时，获取网点编码时(netPointId:" + orderProduct.getNetPointId() + ",opid:"
//                      + orderProduct.getId() + ")，发生未知异常:" + ex.getMessage(),
//                ex);
//            return;
//        }
//
//        try {
//            List<QueryPayTimeToLes> param = new ArrayList<QueryPayTimeToLes>();
//            QueryPayTimeToLes qptl = new QueryPayTimeToLes();
//            qptl.setBSTNK(corderStatusToLes.getCordersn());//网单号
//            qptl.setBSTKD(corderStatusToLes.getCordersn());//网单号
//            qptl.setPOSNR(corderStatusToLes.getCorderflag());//W4=尾款/PY=订金
//            qptl.setSOURCE("HAIERSC");
//            qptl.setSOURCESN(order.getOrderSn());//订单号
//            qptl.setCRDAT(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));//付尾款日期
//            qptl.setCRZET(new SimpleDateFormat("HH:mm:ss").format(new Date()));//付尾款时间
//            qptl.setKUNNR(centerCode);//中心
//            qptl.setKUNWE(accepter);//送达方
//            qptl.setNAME1("HAIERSC");
//            qptl.setMESSAGE("订单已闭环");
//            qptl.setADD1(add1);//1 代表全款;2 代表定金全款;3 代表定金部分款
//            //订单编号  网单编号 订单类型
//            param.add(qptl);
//            corderStatusToLes.setPushdata(JsonUtil.toJson(param));
//            ServiceResult<Boolean> result = lesService.paytimeToLes(order.getOrderSn(), param);
//            if (result == null || !result.getSuccess()) {
//                String message = "给les发送付尾款时间时，接口错误信息:" + result != null ? result.getMessage()
//                    : "调用接口返回为null";
//                corderStatusToLes.setLastmessage(message);
//                corderStatusToLes.setCount(
//                    corderStatusToLes.getCount() == null ? 1 : corderStatusToLes.getCount() + 1);
//                corderStatusToLesDao.updateByPrimaryKey(corderStatusToLes);
//                log.error(message);
//                return;
//            }
//            if (result.getSuccess()) {
//                corderStatusToLes.setLastmessage("操作成功");
//                corderStatusToLes.setCount(
//                    corderStatusToLes.getCount() == null ? 1 : corderStatusToLes.getCount() + 1);
//                corderStatusToLes.setSuccess(1);
//                corderStatusToLes.setSuccesstime((int) (new Date().getTime() / 1000));
//                corderStatusToLesDao.updateByPrimaryKey(corderStatusToLes);
//            }
//        } catch (Exception ex) {
//            String message = ex.getMessage();
//            corderStatusToLes.setLastmessage(message);
//            corderStatusToLes.setCount(
//                corderStatusToLes.getCount() == null ? 1 : corderStatusToLes.getCount() + 1);
//            corderStatusToLesDao.updateByPrimaryKey(corderStatusToLes);
//            log.error(
//                "给les发送付尾款时间时，调用Les接口时，opid:" + orderProduct.getId() + "，发生未知异常:" + ex.getMessage(),
//                ex);
//        }
//    }

    /**
     * 物流发送尾款付款时间
     *
     * @return
     */
//    private void syncTailPaytimeToLes(GroupOrders groupOrders) {
//
//        Integer orderProductId = groupOrders.getDepositOrderProductId();//网单id
//        OrderProducts orderProduct = orderProductsNewService.get(orderProductId);
//        if (orderProduct == null) {
//            groupOrders.setLesMessage("网单不存在");
//            groupOrders.setLesStatus(3);//停止发送
//            groupOrders.setLesLastTime((new Date().getTime() / 1000));
//            groupordersNewService.updateLesStatus(groupOrders);
//            log.error(this.logPrefix("ord-les-paytime", groupOrders.getDepositCOrderSn() + "")
//                      + "网单" + orderProductId + "不存在");
//            return;//网单不存在
//        }
//        Orders order = ordersNewService.get(orderProduct.getOrderId());
//        if (order == null) {
//            groupOrders.setLesMessage("订单不存在");
//            groupOrders.setLesStatus(3);//停止发送
//            groupOrders.setLesLastTime((new Date().getTime() / 1000));
//            groupordersNewService.updateLesStatus(groupOrders);
//            log.error(this.logPrefix("ord-les-paytime", groupOrders.getDepositCOrderSn() + "")
//                      + "订单" + orderProduct.getOrderId() + "不存在");
//            return;//订单不存在
//        }
//        if (OrderStatus.OS_CANCEL.getCode().equals(order.getOrderStatus())) {
//            groupOrders.setLesMessage("订单取消");
//            groupOrders.setLesStatus(3);//停止发送
//            groupOrders.setLesLastTime((new Date().getTime() / 1000));
//            groupordersNewService.updateLesStatus(groupOrders);
//            log.error(this.logPrefix("ord-les-paytime", groupOrders.getDepositCOrderSn() + "")
//                      + "订单" + orderProduct.getOrderId() + "取消");
//            return;//订单取消
//        }
//
//        if (orderProduct.getStatus().equals(OrderProductStatus.CANCEL_CLOSE.getCode())) {
//            groupOrders.setLesMessage("网单取消");
//            groupOrders.setLesStatus(3);//停止发送
//            groupOrders.setLesLastTime((new Date().getTime() / 1000));
//            groupordersNewService.updateLesStatus(groupOrders);
//            log.error(this.logPrefix("ord-les-paytime", groupOrders.getDepositCOrderSn() + "")
//                      + "网单" + orderProduct.getId() + "取消");
//            return;//网单取消
//        }
//
//        if (orderProduct.getStatus() < OrderProductStatus.LES_SHIPPING.getCode()) {
//            groupOrders.setLesMessage("开提单后才能传LES");
//            groupOrders.setLesStatus(2);//发送失败
//            groupOrders.setLesLastTime((new Date().getTime() / 1000));
//            groupordersNewService.updateLesStatus(groupOrders);
//            return;//开提单后才能传LES
//        }
//
//        if (!(order.getTailPayTime() > 0)) {
//            groupOrders.setLesMessage("尾款时间为空");
//            groupOrders.setLesStatus(2);//发送失败
//            groupOrders.setLesLastTime((new Date().getTime() / 1000));
//            groupordersNewService.updateLesStatus(groupOrders);
//            log.error(this.logPrefix("ord-les-paytime", groupOrders.getDepositCOrderSn() + "")
//                      + "网单" + orderProduct.getId() + "尾款时间为空");
//            return;//尾款时间为空
//        }
//
//        Integer orderType = order.getOrderType();//订单类型    普通订单-0,团购预付款订单-1,团购尾款订单-2,普通团购订单-3,单订单模式的订金-尾款订单-4
//        String ddlx = "F1";//F1全款订单，F2尾款订单
//        if (orderType != null && !orderType.equals(0) && !orderType.equals(3)) {//除了普通订单和普通团购订单，其他都认为是尾款订单
//            ddlx = "F2";
//        }
//        String add1 = "1";//1 代表全款;2 代表定金全款;3 代表定金部分款
//        if (ddlx.equals("F2")) {
//            if (orderProduct.getShippingOpporunity().equals(1)) {
//                add1 = "2";//尾款发货
//            } else {
//                add1 = "3";//定金发货
//            }
//        }
//        //中心代码-日日顺C码
//        String centerCode = "";
//        //处理网单的库位
//        String sCode = orderProduct.getSCode();
//        //检查处理后的库位
//        Storages storage = null;
//        if (sCode != null && !sCode.isEmpty()) {
//            try {
//                //检查库位（仓库）
//                ServiceResult<Storages> result = stockService.getStoragesBySCode(sCode);
//                if (result == null || !result.getSuccess()) {
//                    String message = result != null ? result.getMessage() : "检查库位服务返回结果为null";
//                    groupOrders.setLesMessage(message);
//                    groupOrders.setLesStatus(2);//发送失败
//                    groupOrders.setLesLastTime((new Date().getTime() / 1000));
//                    groupordersNewService.updateLesStatus(groupOrders);
//                    log.error("给les发送付尾款时间时，检查库位（仓库）时(sCode:" + sCode + ",opid:"
//                              + orderProduct.getId() + ")，调用服务失败:" + message);
//                    return;//调用服务异常
//                }
//                storage = result.getResult();
//                if (storage != null) {
//                    centerCode = storage.getCenterCode();
//                }
//            } catch (Exception ex) {
//                String message = ex.getMessage();
//                groupOrders.setLesMessage(message);
//                groupOrders.setLesStatus(2);//发送失败
//                groupOrders.setLesLastTime((new Date().getTime() / 1000));
//                groupordersNewService.updateLesStatus(groupOrders);
//                log.error("给les发送付尾款时间时，检查库位（仓库）时(sCode:" + sCode + ",opid:" + orderProduct.getId()
//                          + ")，发生未知异常:" + ex.getMessage(),
//                    ex);
//                return;
//            }
//        }
//        //物流模式
//        Integer sdabw = 2;//2 网点 70 直配
//        if (storage != null) {
//            if ((new Integer(1).equals(storage.getType())
//                 && "B2C".equalsIgnoreCase(orderProduct.getShippingMode()))
//                || new Integer(2).equals(storage.getType())) {//大家电库1，小家电库2
//                sdabw = 70;
//            }
//        }
//        //送达方代码
//        String accepter = "";
//        try {
//            Integer netPointId = orderProduct.getNetPointId();//检查网点
//            if (netPointId > 0) {
//                NetPoints np = null;
//                ServiceResult<NetPoints> npresult = itemService.getNetPoint(netPointId);
//                if (npresult == null || !npresult.getSuccess()) {
//                    String message = npresult != null ? npresult.getMessage() : "检查网点服务返回结果为null";
//                    groupOrders.setLesMessage(message);
//                    groupOrders.setLesStatus(2);//发送失败
//                    groupOrders.setLesLastTime((new Date().getTime() / 1000));
//                    groupordersNewService.updateLesStatus(groupOrders);
//                    log.error("给les发送付尾款时间时，获取网点编码时(netPointId:" + orderProduct.getNetPointId()
//                              + ",opid:" + orderProduct.getId() + ")，调用接口服务返回失败:" + message);
//                    return;
//                }
//                np = npresult.getResult();
//                if (np != null) {
//                    accepter = np.getNetPointN8();//网点8码
//                }
//            } else {//非派工的传5码
//                if (sdabw == 70) {//直配
//                    LesFiveYards yard = purchaseLesFiveYardsService.getBySCode(sCode);
//                    if (yard != null) {
//                        accepter = yard.getFiveYard(); //Les 送达方代码
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            String message = ex.getMessage();
//            groupOrders.setLesMessage(message);
//            groupOrders.setLesStatus(2);//发送失败
//            groupOrders.setLesLastTime((new Date().getTime() / 1000));
//            groupordersNewService.updateLesStatus(groupOrders);
//            log.error("给les发送付尾款时间时，获取网点编码时(netPointId:" + orderProduct.getNetPointId() + ",opid:"
//                      + orderProduct.getId() + ")，发生未知异常:" + ex.getMessage(),
//                ex);
//            return;
//        }
//
//        try {
//            List<QueryPayTimeToLes> param = new ArrayList<QueryPayTimeToLes>();
//            QueryPayTimeToLes qptl = new QueryPayTimeToLes();
//            qptl.setBSTNK(orderProduct.getCOrderSn());//网单号
//            qptl.setBSTKD(orderProduct.getCOrderSn());//网单号
//            qptl.setPOSNR("W4");//W4=尾款/PY=订金
//            qptl.setSOURCE("HAIERSC");
//            qptl.setSOURCESN(order.getOrderSn());//订单号
//            String orderpaytime = DateFormatUtil.formatTime(order.getTailPayTime());
//            String paydate = orderpaytime != null && orderpaytime.length() > 10
//                ? orderpaytime.substring(0, 10) : "";
//            String paytime = orderpaytime != null && orderpaytime.length() > 11
//                ? orderpaytime.substring(11) : "";
//            qptl.setCRDAT(paydate);//付尾款日期
//            qptl.setCRZET(paytime);//付尾款时间
//            qptl.setKUNNR(centerCode);//中心
//            qptl.setKUNWE(accepter);//送达方
//            qptl.setNAME1("HAIERSC");
//            qptl.setMESSAGE("订单已付尾款");
//            qptl.setADD1(add1);//1 代表全款;2 代表定金全款;3 代表定金部分款
//            //订单编号  网单编号 订单类型
//            param.add(qptl);
//
//            ServiceResult<Boolean> result = lesService.paytimeToLes(order.getOrderSn(), param);
//            if (result == null || !result.getSuccess()) {
//                String message = "给les发送付尾款时间时，接口错误信息:" + result != null ? result.getMessage()
//                    : "调用接口返回为null";
//                groupOrders.setLesMessage(message);
//                groupOrders.setLesStatus(2);//发送失败
//                groupOrders.setLesLastTime((new Date().getTime() / 1000));
//                groupordersNewService.updateLesStatus(groupOrders);
//                log.error(message);
//                return;
//            }
//            if (result.getSuccess()) {
//                groupOrders.setLesMessage("操作成功");
//                groupOrders.setLesStatus(1);//发送成功
//                groupOrders.setLesLastTime((new Date().getTime() / 1000));
//                groupordersNewService.updateLesStatus(groupOrders);
//            }
//        } catch (Exception ex) {
//            String message = ex.getMessage();
//            groupOrders.setLesMessage(message);
//            groupOrders.setLesStatus(2);//发送失败
//            groupOrders.setLesLastTime((new Date().getTime() / 1000));
//            groupordersNewService.updateLesStatus(groupOrders);
//            log.error(
//                "给les发送付尾款时间时，调用Les接口时，opid:" + orderProduct.getId() + "，发生未知异常:" + ex.getMessage(),
//                ex);
//        }
//    }
    //3
    private class ExecuteOrderToLes implements IExcute {

        @SuppressWarnings("unchecked")
        @Override
        public void execute(Object obj) {
            try {
                List<LesQueues> list = (List<LesQueues>) obj;
                syncOrderToLesThread(list);
            } catch (Exception e) {
                log.error("调VOM开提单,发生异常：", e);
            }
        }

    }

//    private class ExecutePaytimeToLes implements IExcute {
//
//        @SuppressWarnings("unchecked")
//        @Override
//        public void execute(Object obj) {
//            try {
//                List<OrderQueues> list = (List<OrderQueues>) obj;
//                syncPaytimeToLesThread(list);
//            } catch (Exception e) {
//                log.error("传输付款时间,发生异常：", e);
//            }
//        }
//    }

//    private class ExecuteTailPaytimeToLes implements IExcute {
//
//        @SuppressWarnings("unchecked")
//        @Override
//        public void execute(Object obj) {
//            try {
//                List<GroupOrders> list = (List<GroupOrders>) obj;
//                syncTailPaytimeToLesThread(list);
//            } catch (Exception e) {
//                log.error("传输尾款时间,发生异常：", e);
//            }
//        }
//    }

//    private class ExecuteCorderStatusToLes implements IExcute {
//
//        @SuppressWarnings("unchecked")
//        @Override
//        public void execute(Object obj) {
//            try {
//                List<CorderStatusToLes> list = (List<CorderStatusToLes>) obj;
//                syncCorderStatusToLesThread(list);
//            } catch (Exception e) {
//                log.error("传商城状态至LES,发生异常：", e);
//            }
//        }
//    }

    /**
     * 返回待发送付款时间到物流的队列
     * @param topX
     * @return
     */
//    public List<OrderQueues> getOrderQueues(Integer topX) {
//        List<OrderQueues> oq = null;
//        try {
//            oq = orderQueuesDao.getOrderQueuesList(topX);
//        } catch (Exception e) {
//            log.error("获取待发送付款时间队列数据出现异常：", e);
//        }
//        return oq;
//    }

    /**
     * 返回待发送尾款时间到物流的队列
     * @param topX
     * @return
     */
//    public List<GroupOrders> getGroupOrdersQueues(Integer topX) {
//        List<GroupOrders> oq = null;
//        try {
//            oq = groupordersNewService.getGroupOrdersQueues(topX);
//        } catch (Exception e) {
//            log.error("获取待发送尾款时间队列数据出现异常：", e);
//        }
//        return oq;
//    }

    /**
     * 从商城万人团活动中，计算出活动金额
     *
     * @param pa
     * @return
     */
    private BigDecimal getPriceInActivities(ProductActivities pa) {
        if (pa != null && pa.getActivityType().equals(1)) {//商城万人团
            String extensions = pa.getExtensions();
            @SuppressWarnings("rawtypes")
            Map results = (Map) new SerializedPhpParser(extensions).parse();
            @SuppressWarnings("rawtypes")
            Map priceList = (Map) results.get("priceList");
            if (priceList != null) {
                BigDecimal minPrice = BigDecimal.ZERO;
                for (int i = 0; i < priceList.size(); i++) {
                    @SuppressWarnings("rawtypes")
                    BigDecimal p = new BigDecimal(((Map) priceList.get(i)).get("price").toString());
                    if (i == 0) {
                        minPrice = p;
                    } else {
                        if (minPrice.compareTo(p) > 0) {
                            minPrice = p;
                        }
                    }
                }
                if (minPrice.compareTo(BigDecimal.ZERO) > 0) {
                    return minPrice;
                }
            }
        }
        return null;
    }

//    /**
//     * 查询OrderProductsAttributes属性
//     * @param lesQueue
//     * @return
//     */
//    public OrderProductsAttributes getOrderProductsAttributes(LesQueues lesQueue) {
//        OrderProductsAttributes orderProductsAttributes = null;
//        Integer orderProductId = lesQueue.getOrderProductId(); //网单id
//        //根据网单id获取网单扩展属性表
//        orderProductsAttributes = orderProductsAttributesService.getByOrderProductId(orderProductId);
//        return orderProductsAttributes;
//    }

    //-------------------------------IOC 注入--------------------------------------
    public void setThreadHelper(ThreadHelper threadHelper) {
        this.threadHelper = threadHelper;
    }

    public void setStockService(OrderCenterStockServiceImpl stockServiceImpl) {
        this.stockServiceImpl = stockServiceImpl;
    }

    public void setStockCommonService(OrderCenterStockCommonServiceImpl stockCommonService) {
        this.stockCommonService = stockCommonService;
    }

    public void setItemService(OrderCenterItemServiceImplByHwl itemService) {
        this.itemService = itemService;
    }



}
