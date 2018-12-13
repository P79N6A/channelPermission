package com.haier.afterSale.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.haier.afterSale.webService.pushSAP.PushReturnInfoToGVS;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.haier.afterSale.service.InvoiceService;
import com.haier.afterSale.service.ItemService;
import com.haier.afterSale.service.OrderRepairService;
import com.haier.afterSale.service.OrderService;
import com.haier.afterSale.service.StockCommonService;
import com.haier.afterSale.services.VomOrderServiceImpl;
import com.haier.afterSale.util.HelpUtils;
import com.haier.afterSale.webService.pushSAP.InType;
import com.haier.afterSale.webService.pushSAP.OutType;
import com.haier.afterSale.webService.pushSAP.TMSGType;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.EisInterfaceFinance;
import com.haier.eis.model.VomwwwOutinstockAnalysis;
import com.haier.eis.service.EisInterfaceDataLogService;
import com.haier.eis.service.EisVomwwwOutinstockAnalysisService;
import com.haier.purchase.data.model.GoodsBackInfoResponse;
import com.haier.shop.model.Invoices;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrderRepairsNew;
import com.haier.shop.model.OrderType;
import com.haier.shop.model.OrdersNew;
import com.haier.shop.service.InvoicesService;
import com.haier.shop.service.ShopInvoiceService;
import com.haier.stock.model.InvChannel2OrderSource;
import com.haier.stock.model.InvStockChannel;

import net.sf.json.JSONObject;

import javax.xml.namespace.QName;

/**
 * 3W仓库退货入库，sap交互
 * @Filename: EISWWWStockModel.java
 * @Version: 1.0
 * @Author: liudun 刘盾
 * @Email: liudun@ehaier.com
 *
 */
@Service
public class CnPushReturnInfoToGVSModel {

    private Logger                           logger           = LoggerFactory.getLogger(CnPushReturnInfoToGVSModel.class);

    /**
     * wsdl文件名称
     */
    private String                           wsdlFile         = "wsdl_test/PushReturnInfoToGVS.wsdl";
    private String                           genuineGoodsWsdl = "TransQualityGoodsReturnFromGVS.wsdl";
    /**
     * webservice 接口名称
     */
    private String                           interfaceCode    = "PushReturnInfoToGVS";
    @Autowired
    private OrderService                     orderService;
    @Autowired
    private StockCommonService               stockCommonService;
    @Autowired
    private ItemService                      itemService;
    @Autowired
    private InvoiceService                   invoiceService;
    @Autowired
    private VomOrderServiceImpl                  vomOrderService;
    @Autowired
    private EisInterfaceDataLogService           eisInterfaceDataLogDao;
    @Autowired
    private EisVomwwwOutinstockAnalysisService      vomwwwOutinstockAnalysisDao;
    @Autowired
    private InvoicesService                    invoicesDao;
    @Autowired
    private HelpUtils                        help;
    @Autowired
    private OrderRepairService               orderRepairService;
    @Value("${wsdlPath}")
    private String wsdlPath;
    private static HashSet<String>           successBackNos   = new HashSet<String>();

    private static HashSet<String>           cOrderSns        = new HashSet<String>();

    private static Map<String, String>       REASONS          = new HashMap<String, String>();

    //从数据库捞取数据查询条件
    private static final Map<String, Object> queryMap         = new HashMap<String, Object>();

    //捞取数据量大小
    private static final int                 fetchSize        = 500;
    public final static QName SERVICE = new QName("http://www.example.org/PushReturnInfoToGVS/", "PushReturnInfoToGVS");

    public static void setSuccessBackNos(HashSet<String> successBackNos) {
        CnPushReturnInfoToGVSModel.successBackNos = successBackNos;
    }

    public static Map<String, String> getREASONS() {
        return REASONS;
    }

    public static void setREASONS(Map<String, String> REASONS) {
        CnPushReturnInfoToGVSModel.REASONS = REASONS;
    }

    public static Map<String, Object> getQueryMap() {
        return queryMap;
    }

    public static int getFetchSize() {
        return fetchSize;
    }

    public OrderRepairService getOrderRepairService() {
        return orderRepairService;
    }

    public void setOrderRepairService(OrderRepairService orderRepairService) {
        this.orderRepairService = orderRepairService;
    }

    static {
        queryMap.put("q_busType", 2);
        queryMap.put("q_sapStatus", 0);
        queryMap.put("q_ordeType", 3);
        queryMap.put("q_sapCount", 30);
        queryMap.put("q_handleStatus", 1);

        REASONS.put("价格问题", "156");
        REASONS.put("价格问题", "156");
        REASONS.put("大小尺寸", "157");
        REASONS.put("颜色款式", "158");
        REASONS.put("质量问题", "159");
        REASONS.put("安装问题", "160");
        REASONS.put("发票问题", "161");
        REASONS.put("配送问题", "162");
        REASONS.put("库存问题", "163");
        REASONS.put("地址问题", "164");
        REASONS.put("七天无理由", "165");
        REASONS.put("其他", "166");
    }

    /**
     * webService 返回bean
     */
    public class Result {
    	public int    status = EisInterfaceFinance.STATUS_ERROR;
      public   String eiaType;
      public  String message;
      public String system;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getEiaType() {
		return eiaType;
	}
	public void setEiaType(String eiaType) {
		this.eiaType = eiaType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
      
    }

    /**
     *
     * JOB：菜鸟3w仓退货入库，推送sap财务接口
     * 与之前退货流程相似EISStockService.processLesStockTransForFinance
     * 后端定时任务:vomwww_outinstock_analysis，推送sap webservice接口，
     * sap webservice接口文件（线上):wsdl/PushProSaleOrderToEhaierSAP.wsdl
     * sap webservice接口文件（测试）：wsdl_test/PushReturnInfoToGVS.wsdl
     * @return
     */
    public void cnPushStorageToSap() {

        //获取3w退货单信息，准备解析推送sap
        List<VomwwwOutinstockAnalysis> vomWWWInStockAnalysises = getVomWWWInStockAnalysisToDeal();
        //校验退货单信息并且推送sap接口
        foreachSend(vomWWWInStockAnalysises);
        cOrderSns.clear();
        logger.info("[eis-service][CnPushReturnInfoToGVSServiceImpl][cnPushStorageToSap] end");
    }

    /**
     * 推送sap接口
     * 正品退货流程
     * 定金订单处理流程
     * @param vomwwwOutinstockAnalysis
     * @return
     * @throws MalformedURLException 
     */
    public Result send(VomwwwOutinstockAnalysis vomwwwOutinstockAnalysis) throws MalformedURLException {
        Result result = new Result();

        String backNo = vomwwwOutinstockAnalysis.getBackNo();
        ServiceResult<OrderRepairsNew> byVomRepairSn = orderRepairService.getByVomRepairSn(backNo,
            vomwwwOutinstockAnalysis.getSku());
        if (null == byVomRepairSn || !byVomRepairSn.getSuccess()) {
            result.status = VomwwwOutinstockAnalysis.STATUS_ERROR;
            result.message = "获取退换货网单号信息失败:" + backNo;
            return result;
        }
        String corderSn = byVomRepairSn.getResult().getRepairSn();
        if (com.alibaba.dubbo.common.utils.StringUtils.isBlank(corderSn)) {
            result.status = VomwwwOutinstockAnalysis.STATUS_ERROR;
            result.message = "处理退货入库失败,退货单号为空:" + backNo;
            return result;
        }
        //套机情况， 对于第二条过来的拒收信息，生成新的逆向单 退货单号。 第一条逆向单退货单号 WD****TH2，第二条变成 WDW****TH2
        if (corderSn.trim().startsWith("WDW")) {
            result.status = VomwwwOutinstockAnalysis.STATUS_UNDO;
            result.message = "处理退货入库(套机repairSn:" + corderSn + "):第二条子件情况,不再推送SAP!";
            return result;
        }

        //处理正品退货
        String soou = this.isGenuineGoods(corderSn);
        if (!org.apache.commons.lang.StringUtils.isBlank(soou)) {
            //正品退货发送SAP
//            return this.reverseGenuineGoodsToGVS(corderSn, soou, vomwwwOutinstockAnalysis);
        }

        if (cOrderSns.contains(corderSn)) {
            result.status = EisInterfaceFinance.STATUS_WARN;
            result.message = "网单号：" + corderSn + "已经调用退货入库接口";
            return result;
        }
        cOrderSns.add(corderSn);

        //sap普通推送流程 组装数据
        com.haier.afterSale.webService.pushSAP.ObjectFactory objectFactory = new com.haier.afterSale.webService.pushSAP.ObjectFactory();
        InType request = objectFactory.createInType();
        	request.setZSYST("EHAI");//系统编码   
        	  

        request.setZWBDR(corderSn);//退货网单号
//        request.setZWBDR("WD182919358509TH3");
        OrderProductsNew orderProducts = getOrderProducts(corderSn.replaceAll("TH.*", ""));
        OrdersNew order = getOrderByWD(orderProducts);
        if (order == null) {
            result.message = "处理退货入库失败：未获取到退货单关联的订单,退货单号：" + corderSn;
            result.status = VomwwwOutinstockAnalysis.STATUS_ERROR;
            return result;
        }
        // 原始网单号
        if (orderProducts.getSplitFlag() == OrderProductsNew.SPLITFLAG_SPLIT_NEW) {
            request.setZWBDRO(orderProducts.getSplitRelateCOrderSn());// 原始网单号，拆单后新单需要原网单单号
        } else {
            request.setZWBDRO(orderProducts.getCOrderSn());// 原始网单号
        }
//        request.setZWBDRO("WD182919358509");
        //定金尾款订单，如销售出库推送了SAP，退货入库也推送SAP，如未推送，则都不推送

        if (OrderType.TYPE_GROUP_ADVANCE_TAIL.getValue().equals(order.getOrderType())
        //            && order.getTailPayTime() <= 0L
        //            && order.getTaobaoGroupId().intValue() > 0
        //            && orderProducts.getShippingOpporunity().intValue() == 0
        ) {
            if (dealDepositTailOrder(vomwwwOutinstockAnalysis, result, corderSn))
                return result;
        }
        //2016-11-02 时间改为系统当前时间
        request.setZWBDT(DateUtil.format(new Date(), "yyyy-MM-dd"));//网单创建日期
        //        request.setZWBDT(DateUtil.format(vomwwwOutinstockAnalysis.getOutInDate(), "yyyy-MM-dd"));//网单创建日期
        OrderRepairsNew orderRepairs = getOrderRepairs(orderProducts);
        if (orderRepairs == null) {
            result.status = VomwwwOutinstockAnalysis.STATUS_ERROR;
            result.message = "处理退货入库失败：网单关联的有效退货单已. 不存在，退货单号：" + corderSn;
            return result;
        }
        String reason = REASONS.get(orderRepairs.getReason());
        if (StringUtil.isEmpty(reason))
            reason = REASONS.get("其他");
        request.setAUGRU(reason);//退货原因

        request.setMATNR(orderProducts.getSku());//物料编码
        request.setKWMENG(new BigDecimal(orderProducts.getNumber()));//数量

        BigDecimal price = null;
        List<Invoices> invoices = invoicesDao.getByOrderProductId(orderProducts.getId());
        boolean isMakeReceipt = false;

        for (Invoices invoice : invoices) {
            if (invoice.getState() == 4) {
                isMakeReceipt = true;
                price = invoice.getPrice();
                break;
            }
        }

        if (!isMakeReceipt)
            price = computeInvoicePrice(order, orderProducts);

        request.setKBETR(price);//单价
        request.setKUNNRAG(getCustormCode(orderProducts, order, price));// 客户编码
        request.setKUNNRRG(HelpUtils.getKunnrRgNull(order.getSource()));//付款方
        request.setLGORT(vomwwwOutinstockAnalysis.getWWWStock());//库位
        setRequestZORDR(order, request);

        request.setZCHNL(getChannel(corderSn, order.getSource()));//渠道
        request.setZFGBL(isMakeReceipt ? "0" : "1");// 是否已开票
        
        List<InType> in = new ArrayList<InType>();
        in.add(request);
        
        URL url = this.getClass().getResource(wsdlPath + "/PushReturnInfoToGVS.wsdl");
//        logger.info(wsdlPath);
//        logger.info(url.toString());
        //        String path = "file:"+ this.getClass().getResource("/wsdl_test/PushReturnInfoToGVS.wsdl").getPath();
        com.haier.afterSale.webService.pushSAP.PushReturnInfoToGVS soap = new com.haier.afterSale.webService.pushSAP.PushReturnInfoToGVS_Service(
        		 url).getPushReturnInfoToGVSSOAP();

//        com.haier.afterSale.webService.pushSAP.PushReturnInfoToGVS soap = new com.haier.afterSale.webService.pushSAP.PushReturnInfoToGVS_Service(help.getWSDLURL(wsdlFile)).getPushReturnInfoToGVSSOAP();
//        String path = "file:"+ this.getClass().getResource("/wsdl_test/PushReturnInfoToGVS.wsdl").getPath();
//        com.haier.afterSale.webService.pushSAP.PushReturnInfoToGVS soap = new com.haier.afterSale.webService.pushSAP.PushReturnInfoToGVS_Service(
//        		  new URL(path)).getPushReturnInfoToGVSSOAP();
        // 要记录接口数据日志
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(corderSn);
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(in));
        Long startTime = System.currentTimeMillis();
        JSONObject.fromObject(request);
        try {

            OutType pushReturnInfoToGVS = soap.pushReturnInfoToGVS(in);
            List<TMSGType> results = pushReturnInfoToGVS.getTMSG();
            String msg = JsonUtil.toJson(results);

            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setResponseTime(DateUtil.currentDateTime());

            dataLog.setResponseData(msg);
            if (results == null || results.size() <= 0) {
                result.status = VomwwwOutinstockAnalysis.STATUS_FAILED;
                result.message = "调用EAI接口返回数据不合法";
            } else {
                boolean flag = true;
                for (TMSGType rs : results) {
                    if (!flag)
                        break;
                    if ("E".equals(rs.getTYPE()))
                        flag = false;
                }
                result.status = flag ? VomwwwOutinstockAnalysis.STATUS_SUCCESS
                    : VomwwwOutinstockAnalysis.STATUS_FAILED;
                result.message = msg;
            }
        } catch (Exception e) {
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData("");
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setErrorMessage(e.getMessage());
            result.status = VomwwwOutinstockAnalysis.STATUS_FAILED;
            result.message = "调用EAI接口失败";
            logger.error("调用EAI接口 pushReturnInfoToGVS 失败：", e);
        }

        dataLog.setCreateTime(DateUtil.currentDateTime());
        int eaiDataLogId = recordEisInterfaceDataLog(dataLog);
        vomwwwOutinstockAnalysis.setEaiDataLogId(eaiDataLogId);
        cOrderSns.clear();
        return result;

    }

    /**
     *
     * @param order
     * @param request
     */
    private void setRequestZORDR(OrdersNew order, InType request) {
        String orderSn = order.getOrderSn();
        String relationOrderSn = order.getRelationOrderSn();
        if (!StringUtil.isEmpty(relationOrderSn) && !"新单".equalsIgnoreCase(relationOrderSn)) {
            orderSn = relationOrderSn;
        } else if (!StringUtil.isEmpty(order.getSourceOrderSn())
                   && !"新单".equalsIgnoreCase(order.getSourceOrderSn())) {
            orderSn = order.getSourceOrderSn();
        } else {
            orderSn = order.getOrderSn();
        }
        // 如果是定金尾款订单，则取本身单号
        if (order.getOrderType().equals(OrderType.TYPE_GROUP_ADVANCE.getIntValue())) {
            orderSn = order.getOrderSn();
        }

        if (org.apache.commons.codec.binary.StringUtils.equals("BLPHH", order.getSource())) {//不良品换货业务，订单号不能截取
            if (orderSn != null && orderSn.contains("_")) {//网单号不含 “_” 的无需进行截取
                orderSn = orderSn.substring(0, orderSn.indexOf("_"));
            }
            request.setZORDR(orderSn);//订单号
        } else {//其他业务需要截掉订单号的前缀。如TOP_11111111 要截取为 11111111
            request.setZORDR(orderSn == null ? "" : orderSn.replaceAll(".*_", ""));//订单号
        }
    }

    /**
     * 处理定金尾款数据
     * @param vomwwwOutinstockAnalysis
     * @param result
     * @param corderSn
     * @return
     */
    private boolean dealDepositTailOrder(VomwwwOutinstockAnalysis vomwwwOutinstockAnalysis,
                                         Result result, String corderSn) {
        //定金尾款处理
        String tradeNo = vomwwwOutinstockAnalysis.getTradeNo();
        String subTradeNo = vomwwwOutinstockAnalysis.getSubTradeNo();

        List<VomwwwOutinstockAnalysis> deposits = vomwwwOutinstockAnalysisDao
            .getInAnalysisByTradeNo(tradeNo, subTradeNo, VomwwwOutinstockAnalysis.busTypeOut);
        if (CollectionUtils.isEmpty(deposits)) {
            result.status = VomwwwOutinstockAnalysis.STATUS_ERROR;
            result.message = "网单[" + corderSn + "]属于定金尾款退货，CBS未收到原单，退货单不推送SAP";
            logger.info("网单[" + corderSn + "]属于定金尾款退货，CBS未收到原单，退货单不推送SAP");
            return true;
        }
        Set<Integer> status = new HashSet<Integer>();
        for (VomwwwOutinstockAnalysis deposit : deposits) {
            status.add(deposit.getSapStatus());
        }
        if (status.isEmpty()) {
            result.status = VomwwwOutinstockAnalysis.STATUS_ERROR;
            result.message = "网单[" + corderSn + "]属于定金尾款退货，原单未推送SAP，退货单不推送SAP";
            logger.info("网单[" + corderSn + "]属于定金尾款退货，原单未推送SAP，退货单不推送SAP");
            return true;
        }
        if (status.contains(VomwwwOutinstockAnalysis.STATUS_ERROR)) {
            result.status = VomwwwOutinstockAnalysis.STATUS_ERROR;
            result.message = "网单[" + corderSn + "]属于定金尾款退货，原单未成功推送SAP，退货单不推送SAP";
            logger.info("网单[" + corderSn + "]属于定金尾款退货，原单未成功推送SAP，退货单不推送SAP");
            return true;
        }
        return false;
    }

    /**
     * 推送
     * @param vomwwwOutinstockAnalysises
     */
    private void foreachSend(List<VomwwwOutinstockAnalysis> vomwwwOutinstockAnalysises) {
        if (vomwwwOutinstockAnalysises == null || vomwwwOutinstockAnalysises.size() == 0) {
            logger.info("3W退换货入库信息推送SAP,没有需要处理的数据！");
            return;
        }
        //        logger.info("--------rk-----------------xinm-----" + vomwwwOutinstockAnalysises.size());
        //        int i = 0;
        for (VomwwwOutinstockAnalysis vomwwwOutinstockAnalysis : vomwwwOutinstockAnalysises) {
            //            logger.info("-------rk------------------xinm---iiiii--" + (i++));
            try {
                Result result = send(vomwwwOutinstockAnalysis);
                updateSapStatusAfterSend(vomwwwOutinstockAnalysis, result.status, result.message);
            } catch (BusinessException e) {
                updateSapStatusAfterSend(vomwwwOutinstockAnalysis,
                    VomwwwOutinstockAnalysis.STATUS_ERROR, e.getMessage() + "");
                logger.error("调用SAP财务接口时出现错误：" + e.getMessage());
            } catch (Exception e) {
                updateSapStatusAfterSend(
                    vomwwwOutinstockAnalysis,
                    vomwwwOutinstockAnalysis.getSapCount() + 1 >= 30 ? VomwwwOutinstockAnalysis.STATUS_ERROR
                        : VomwwwOutinstockAnalysis.STATUS_INIT, e.getMessage() + "");
                logger.error("调用SAP财务接口时出现错误：", e);
            }
        }
    }

    /**
     * 回写该队列数据推送状态
     * @param vomwwwOutinstockAnalysis
     * @param status
     * @param message
     */
    public void updateSapStatusAfterSend(VomwwwOutinstockAnalysis vomwwwOutinstockAnalysis,
                                         int status, String message) {
        vomwwwOutinstockAnalysis.setSapCount(vomwwwOutinstockAnalysis.getSapCount() + 1);
        vomwwwOutinstockAnalysis.setSapStatus(status);
        vomwwwOutinstockAnalysis.setMessage(message);
        vomwwwOutinstockAnalysisDao.updateSapStatusById(vomwwwOutinstockAnalysis);
    }

    /**
     * 获取要处理的数据
     * @return
     */
    private List<VomwwwOutinstockAnalysis> getVomWWWInStockAnalysisToDeal() {
        List<VomwwwOutinstockAnalysis> vomwwwOutinstockAnalysises = vomwwwOutinstockAnalysisDao
            .getByCondition(queryMap, fetchSize);
        return vomwwwOutinstockAnalysises;
    }

    /**
     * 判断单据号是否为正品退货<br/>
     * 是正品退货则返回so|ou号，不是正品退货则返回null
     * @return
     */
    private String isGenuineGoods(String corderSn) {

        try {
            if (org.apache.commons.lang.StringUtils.isBlank(corderSn) || !StringUtils.startsWithIgnoreCase(corderSn, "WD")) {
                return null;
            }
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("orderNo", corderSn);
            ServiceResult<GoodsBackInfoResponse> result = vomOrderService
                .getGoodsBackInfo(paramMap);
            if (!result.getSuccess()) {
                return null;
            }
            GoodsBackInfoResponse goodsBackInfoResponse = result.getResult();
            if (goodsBackInfoResponse == null) {
                return null;
            }
            //返回so|ou号
            return goodsBackInfoResponse.getSiOuSlipNo();
        } catch (Exception e) {
            logger.error("判断单据号是否为正品退货出现异常,corderSn:" + corderSn);
            throw new RuntimeException("判断单据号是否为正品退货出现异常,corderSn:" + corderSn);
        }
    }

//    /**
//     * 正品退货逆流程发送SAP
//     * @param corderSn
//     * @param soou
//     * @param vomwwwOutinstockAnalysis
//     * @return
//     */
//    private Result reverseGenuineGoodsToGVS(String corderSn, String soou,
//                                            VomwwwOutinstockAnalysis vomwwwOutinstockAnalysis) {
//
//        Result result = new Result();
//        String zfth = null;//退货标识（0-统帅正品退货；1-日日顺正品退货）
//        String zounb = null;//统帅ou单号
//        String vbeln = null;//日日顺退货销售单
//        if (soou.matches("4.+")) {//日日顺退货
//            zfth = "1";
//            zounb = null;
//            vbeln = soou;
//        }
//        if (soou.matches("OU.+") || soou.matches("ou.+")) {//统帅退货
//            zfth = "0";
//            zounb = soou;
//            vbeln = null;
//        } else {
//            result.status = EisInterfaceFinance.STATUS_ERROR;
//            result.message = "单据号无法识别，不是日日顺或统帅单，so|ou:" + soou;
//            return result;
//        }
//
//        //组装数据
//        ZMMINBOUND018IN in = new ZMMINBOUND018IN();
//        in.setZFTH(zfth);//退货标识
//        in.setZWBDR(corderSn);//CBS网单号
//        in.setZOUNB(zounb);//统帅ou单号
//        in.setVBELN(vbeln);//日日顺退回销售单
//        //TODO 行项目号确认：vomwwwOutinstockAnalysis.getItemNo()
//        in.setPOSNR("0001");//行项目号
//        in.setZSPDT(DateUtil.format(vomwwwOutinstockAnalysis.getOutInDate(), "yyyy-MM-dd"));//入库日期
//        in.setMATNR(vomwwwOutinstockAnalysis.getSku());//物料编码
//        in.setMENGE(new BigDecimal(vomwwwOutinstockAnalysis.getNum()));//订单数量
//        //        in.setLGORT(transQueue.getSecCode());//库存地点
//        in.setLGORT(vomwwwOutinstockAnalysis.getWWWStock());//库存地点
//        in.setZFGLG(vomwwwOutinstockAnalysis.getBatch());//批次标识
//        in.setZLSGI(vomwwwOutinstockAnalysis.getReceiptVoucher());//入库过账凭证（货入WA的les过账凭证）
//        List<ZMMINBOUND018IN> input = new ArrayList<ZMMINBOUND018IN>();
//        input.add(in);
//
//        //发送SAP
//        //记录接口数据日志
//        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
//        dataLog.setForeignKey(corderSn);
//        dataLog.setRequestTime(DateUtil.currentDateTime());
//        dataLog.setRequestData(JsonUtil.toJson(input));
//        Long startTime = System.currentTimeMillis();
//
//        TransQualityGoodsReturnFromGVS soap = new TransQualityGoodsReturnFromGVS_Service(
//            help.getWSDLURL(genuineGoodsWsdl)).getTransQualityGoodsReturnFromGVSSOAP();
//
//        try {
//            String sysName = "EHAIER";
//            List<ZMMINBOUND018OUT> out = soap.transQualityGoodsReturnFromGVS(sysName, input);
//            String message = JsonUtil.toJson(out);
//
//            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
//            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
//            dataLog.setResponseTime(DateUtil.currentDateTime());
//            dataLog.setResponseData(message);
//
//            if (CollectionUtils.isEmpty(out)) {
//                result.status = VomwwwOutinstockAnalysis.STATUS_FAILED;
//                result.message = "EAI返回空";
//                dataLog.setResponseData("");
//            } else {
//                boolean flag = true;
//                for (ZMMINBOUND018OUT res : out) {
//                    if (!flag)
//                        break;
//                    flag = !"E".equalsIgnoreCase(res.getTYPE());
//                }
//
//                result.status = flag ? VomwwwOutinstockAnalysis.STATUS_SUCCESS
//                    : VomwwwOutinstockAnalysis.STATUS_FAILED;
//                result.message = message;
//            }
//        } catch (Exception e) {
//            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
//            dataLog.setResponseTime(DateUtil.currentDateTime());
//            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
//            dataLog.setResponseData("");
//            dataLog.setErrorMessage(e.getMessage());
//            result.status = VomwwwOutinstockAnalysis.STATUS_FAILED;
//            result.message = "调用EAI接口失败";
//            logger.error("调用EAI接口 PushReturnInfoToGVSHandler 失败：", e);
//        }
//        dataLog.setCreateTime(DateUtil.currentDateTime());
//        int eaiDataLogId = recordEisInterfaceDataLog(dataLog);
//        vomwwwOutinstockAnalysis.setEaiDataLogId(eaiDataLogId);
//
//        return result;
//    }

    /**
     *
     * @return
     */
    public String getGenuineGoodswsdl() {
        return genuineGoodsWsdl;
    }

    /**
     *
     * @param genuineGoodsWsdl
     */
    public void setGenuineGoodswsdl(String genuineGoodsWsdl) {
        this.genuineGoodsWsdl = genuineGoodsWsdl;
    }

    /**
     *
     * @param log
     */
    private int recordEisInterfaceDataLog(EisInterfaceDataLog log) {
        try {
            if (com.alibaba.dubbo.common.utils.StringUtils.isBlank(log.getInterfaceCode())) {
            	log.setInterfaceCode(interfaceCode);
            }
            ServiceResult<EisInterfaceDataLog> serviceResult =eisInterfaceDataLogDao.insert(log);
            return serviceResult.getResult().getId();
        } catch (Exception e) {
            logger.error("记录EisInterfaceDataLog出错：", e);
            logger.error("EisInterfaceDataLog:" + JsonUtil.toJson(log));
            return 0;
        }
    }

    /**
     * 根据网单获取订单信息
     * @param orderProducts
     * @return
     */
    private OrdersNew getOrderByWD(OrderProductsNew orderProducts) {
        try {
            ServiceResult<OrdersNew> serviceResult = orderService.getOrderById(orderProducts
                .getOrderId());
            if (!serviceResult.getSuccess() || serviceResult.getResult() == null)
                throw new BusinessException("向订单服务请求订单信息出现错误:" + serviceResult.getMessage());
            return serviceResult.getResult();
        } catch (Exception e) {
            throw new RuntimeException("根据订单id获取订单信息出现服务异常：orderId:" + orderProducts.getOrderId());
        }

    }

    /**
     *
     * @param op
     * @return
     */
    private OrderRepairsNew getOrderRepairs(OrderProductsNew op) {
        ServiceResult<OrderRepairsNew> result = orderService.getValidByOrderProductId(op.getId());
        if (!result.getSuccess())
            throw new RuntimeException("orderService返回错误:" + result.getMessage());
        //        if (result.getResult() == null) {
        //            throw new RuntimeException(
        //                "orderService-getOrderRepairsByOrderProductId 错误:" + result.getMessage());
        //        }
        return result.getResult();
    }

    /**
     *  计算开票单价
     *
     * @param order
     * @param orderProduct
     */
    public BigDecimal computeInvoicePrice(OrdersNew order, OrderProductsNew orderProduct) {

        if (orderProduct == null || orderProduct.getCOrderSn() == null
            || orderProduct.getCOrderSn().equals("")) {
            throw new BusinessException("获取开票单价：网单为空，或网单号为空");
        }
        try {
            //获得开票含税总金额
            ServiceResult<BigDecimal> result = invoiceService.getInvoiceAmount(orderProduct
                .getCOrderSn());
            if (result == null || !result.getSuccess()) {
                String message = result != null ? result.getMessage() : "返回结果为null";
                throw new BusinessException("获取开票单价：调用发票价格接口时出错！错误信息：" + message);
            } else {
                //含税总金额
                BigDecimal amount = result.getResult();
                if (amount == null) {
                    throw new BusinessException("获取开票单价：调用发票价格接口返回金额为null");
                }
                if (orderProduct.getNumber() != null && orderProduct.getNumber().compareTo(0) > 0) {
                    //含税单价          =含税总金额除以网单数量
                    BigDecimal price = amount.divide(new BigDecimal(orderProduct.getNumber()), 2,
                        RoundingMode.HALF_UP);
                    return price;
                } else {
                    throw new BusinessException("获取开票单价：网单数量为0或为null");
                }
            }
        } catch (Exception e) {
            logger.error("获取开票单价异常," + e.getMessage() + "\n错误信息:", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private String getCustormCode(OrderProductsNew orderProducts, OrdersNew order, BigDecimal price) {
        return help.getCustormCode(orderProducts, order, price);
    }

    private String getChannel(String cOrderSn, String source) {
        if (cOrderSn.endsWith("J")) {//京东
            return "DS03";
        }
        if ("HPB2B".equalsIgnoreCase(source)) {//海朋归属大客户渠道
            return "DS03";
        }
        if ("TOPXB".equalsIgnoreCase(source) || "TOPFENXIAOXB".equalsIgnoreCase(source)
            || "TOPDHSC".equalsIgnoreCase(source)) {//新宝归属淘宝渠道
            return "DS02";
        }
        if ("RRS".equalsIgnoreCase(source) || "DXS".equalsIgnoreCase(source)) {//日日顺订单
            return "DS05";
        }
        if ("TONGSHUAI".equals(source) || "TONGSHUAIFX".equals(source)) {//统帅
            return "DS06";
        }
        if ("YIHAODIAN".equalsIgnoreCase(source) || "YHD".equalsIgnoreCase(source)) {//一号店订单
            return "DS11";
        }
        if ("CCBSC".equals(source) || "CCBSR".equals(source) || "95533".equals(source)) {//建行订单
            return "DS12";
        }
        if ("GMZX".equals(source)) {//国美在线订单
            return "DS10";
        }
        if ("YMX".equals(source)) {//亚马逊
            return "DS09";
        }
        if ("YHDZY".equals(source)) {//电商平台-1号店自营
            return "DS15";
        }
        if ("ICBC".equals(source)) {//平台大客户-工商银行
            return "DS13";
        }

        if ("SNYG".equals(source)) {//电商平台-苏宁易购旗舰店
            return "DS16";
        }
        if ("DALOU".equalsIgnoreCase(source)) {//企业订单_大楼订单,2016-08-30新增
            return "DS01";
        }
        //新加的sap渠道码走下面的流程    ---wyj-2015-04-10
        try {
            ServiceResult<InvChannel2OrderSource> result = stockCommonService
                .getSapChannelCodeAndCustomerCode(source);
            if (result == null || !result.getSuccess()) {
                String message = result != null ? result.getMessage() : "result返回结果为null";
                throw new BusinessException("获取sap渠道码：调用sap渠道码接口时出错！错误信息：" + message);
            } else {
                InvChannel2OrderSource invc2os = result.getResult();
                if (invc2os != null && invc2os.getSapChannelCode() != null
                    && !invc2os.getSapChannelCode().equals("")) {
                    return invc2os.getSapChannelCode();
                }
            }
        } catch (Exception e) {
            logger.error("获取sap渠道码异常," + e.getMessage() + "\n错误信息:", e);
            throw new RuntimeException(e.getMessage());
        }

        String channel = getInvStockChannelCodeBySource(source);
        if (StringUtil.isEmpty(channel)) {
            throw new BusinessException("订单来源对应的渠道不存在：" + source);
        }
        if (InvStockChannel.CHANNEL_DAKEHU.equals(channel)) {
            return "DS03";
        }
        if (InvStockChannel.CHANNEL_SHANGCHENG.equals(channel)) {
            return "DS01";
        }
        if (InvStockChannel.CHANNEL_TAOBAO.equals(channel)) {
            return "DS02";
        }
        if (InvStockChannel.JD.equals(channel)) {
            return "DS04";
        }
        if ("MK".equalsIgnoreCase(channel)) {
            return "DS07";
        }
        return "";
    }

    /**
     *
     * @param source
     * @return
     */
    private String getInvStockChannelCodeBySource(String source) {
        try {
            ServiceResult<String> serviceResult = stockCommonService
                .getChannelCodeByOrderSource(source);
            if (!serviceResult.getSuccess())
                throw new BusinessException("通过订单来源向库存服务请求渠道编码发生错误:" + serviceResult.getMessage());
            return serviceResult.getResult();
        } catch (Exception e) {
            throw new RuntimeException("根据来源source查找channelCode出现服务异常，source:" + source);
        }
    }

    /**
     *
     * @param file
     * @return
     */
    private URL getWSDLURL(String file) {
        try {
            return help.getWSDLURL(file);
        } catch (Exception e) {
            logger.error("WSDL文件路径配置错误或WSDL文件不存在：" + wsdlFile);
            throw new BusinessException("解析WSDL文件失败，配置错误");
        }
    }

    /**
     *
     * @param cOrderSn
     * @return
     */
    private OrderProductsNew getOrderProducts(String cOrderSn) {
        try {
            ServiceResult<OrderProductsNew> rs = orderService.getOrderProductByCOrderSn(cOrderSn);
            if (!rs.getSuccess()) {
                throw new RuntimeException("向订单服务请求网单信息出现错误：" + rs.getMessage());
            }
            if (rs.getResult() == null) {
                throw new BusinessException("向订单服务请求网单信息为空,corderSn：" + cOrderSns);
            }
            return rs.getResult();
        } catch (Exception e) {
            logger.error("向订单服务请求网单信息出现错误,corderSn:" + cOrderSn);
            throw new RuntimeException("向订单服务请求网单信息出现错误,corderSn:" + cOrderSn);
        }
    }

}
