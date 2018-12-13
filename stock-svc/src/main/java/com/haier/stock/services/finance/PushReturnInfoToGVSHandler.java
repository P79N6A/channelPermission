package com.haier.stock.services.finance;

import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.EisInterfaceFinance;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.service.LesStockTransQueueService;
import com.haier.purchase.data.model.GoodsBackInfoResponse;
import com.haier.shop.model.Invoices;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrderRepairsNew;
import com.haier.shop.model.OrderType;
import com.haier.shop.model.OrdersNew;
import com.haier.stock.eai.finance.pushreturn.query.QueryHandleResultFromGVS;
import com.haier.stock.eai.finance.pushreturn.query.QueryHandleResultFromGVS_Service;
import com.haier.stock.eai.finance.pushreturn.query.ZSDS0003;
import com.haier.stock.eai.finance.pushreturn.query.ZSDS0006;
import com.haier.stock.eai.finance.pushReturnInfoToGVS.InType;
import com.haier.stock.eai.finance.pushReturnInfoToGVS.OutType;
import com.haier.stock.eai.finance.pushReturnInfoToGVS.TMSGType;
import com.haier.stock.eai.finance.transqualitygoodsreturnfromgvs.TransQualityGoodsReturnFromGVS;
import com.haier.stock.eai.finance.transqualitygoodsreturnfromgvs.TransQualityGoodsReturnFromGVS_Service;
import com.haier.stock.eai.finance.transqualitygoodsreturnfromgvs.ZMMINBOUND018IN;
import com.haier.stock.eai.finance.transqualitygoodsreturnfromgvs.ZMMINBOUND018OUT;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.service.VomOrderService;
import com.haier.stock.services.VomOrderServiceImpl;
import com.haier.stock.util.HelpUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 退货入库
 *
 * @Filename: PushReturnInfoToGVSHandler.java
 * @Version: 1.0
 * @Author: yanzhao
 * @Email: yan01250428@126.com
 *
 */
@Service
public class PushReturnInfoToGVSHandler extends SFHandler {
    private Logger                     logger = LogManager
                                                  .getLogger(PushReturnInfoToGVSHandler.class);

    private static Map<String, String> REASONS = new HashMap<String, String>();
    static {
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
    @Autowired
    private VomOrderServiceImpl vomOrderService;

    private String genuineGoodswsdl ="PushReturnInfoToGVS.wsdl";

    @Autowired
    public void setInterfaceCode(){
      super.interfaceCode = "PushReturnInfoToGVS";
    }
    @Autowired
    public void setWsdlFile(){
        super.wsdlFile = "PushReturnInfoToGVS.wsdl";
    }
    @Autowired
    public void setWsdlFileForQuery(){
        super.wsdlFileForQuery = "QueryHandleResultFromGVS.wsdl";
    }

    @Autowired
    public void setNextHandler(TransReBackInfoFromEHAIERToGVSHandler nextHandler){
      super.nextHandler=nextHandler;//父类属性注入
    }

    @Autowired
    private LesStockTransQueueService lesStockTransQueueService;


    /**
     * 判断单据号是否为正品退货<br/>
     * 是正品退货则返回so|ou号，不是正品退货则返回null
     * @return
     */
    private String isGenuineGoods(String orderNo) {

        if (StringUtil.isEmpty(orderNo)) {
            return null;
        }

        if (!orderNo.startsWith("WD")) {
            return null;
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNo", orderNo);
        ServiceResult<GoodsBackInfoResponse> result = vomOrderService.getGoodsBackInfo(paramMap);
        if (!result.getSuccess()) {
            return null;
            //            throw new BusinessException("处理正品退货记录[" + orderNo + "]失败：" + result.getMessage());
        }
        GoodsBackInfoResponse goodsBackInfoResponse = result.getResult();
        if (goodsBackInfoResponse == null) {
            return null;
        }
        //返回so|ou号
        return goodsBackInfoResponse.getSiOuSlipNo();
    }

    /**
     * 正品退货逆流程发送SAP
     * @param transQueue
     * @return
     */
    private Result reverseGenuineGoodsToGVS(LesStockTransQueue transQueue, String soou) {

        Result result = new Result();
        String zfth = null;//退货标识（0-统帅正品退货；1-日日顺正品退货）
        String zounb = null;//统帅ou单号
        String vbeln = null;//日日顺退货销售单
        if (soou.matches("4.+")) {//日日顺退货
            zfth = "1";
            zounb = null;
            vbeln = soou;
        } else if (soou.matches("OU.+") || soou.matches("ou.+")) {//统帅退货
            zfth = "0";
            zounb = soou;
            vbeln = null;
        } else {
            result.status = EisInterfaceFinance.STATUS_ERROR;
            result.message = "单据号无法识别，不是日日顺或统帅单，so|ou:" + soou;
            return result;
        }

        //组装数据
        ZMMINBOUND018IN in = new ZMMINBOUND018IN();
        in.setZFTH(zfth);//退货标识
        in.setZWBDR(transQueue.getCorderSn());//CBS网单号
        in.setZOUNB(zounb);//统帅ou单号
        in.setVBELN(vbeln);//日日顺退回销售单
        in.setPOSNR(transQueue.getZeile());//行项目号
        in.setZSPDT(DateUtil.format(transQueue.getBillTime(), "yyyy-MM-dd"));//入库日期
        in.setMATNR(transQueue.getSku());//物料编码
        in.setMENGE(new BigDecimal(transQueue.getQuantity()));//订单数量
        in.setLGORT(transQueue.getSecCode());//库存地点
        in.setZFGLG(transQueue.getCharg());//批次标识
        in.setZLSGI(transQueue.getOutping());//入库过账凭证（货入WA的les过账凭证）
        List<ZMMINBOUND018IN> input = new ArrayList<ZMMINBOUND018IN>();
        input.add(in);

        //发送SAP
        //记录接口数据日志
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(transQueue.getCorderSn());
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(input));
        Long startTime = System.currentTimeMillis();

        TransQualityGoodsReturnFromGVS soap = new TransQualityGoodsReturnFromGVS_Service(this
            .help.getWSDLURL(genuineGoodswsdl))
            .getTransQualityGoodsReturnFromGVSSOAP();

        try {
            String sysName = "EHAIER";
            List<ZMMINBOUND018OUT> out = soap.transQualityGoodsReturnFromGVS(sysName, input);
            String message = JsonUtil.toJson(out);

            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData(message);

            if (out == null || out.size() <= 0) {
                result.status = EisInterfaceFinance.STATUS_FAILED;
                result.message = "EAI返回空";
                dataLog.setResponseData("");
            } else {
                boolean flag = true;
                for (ZMMINBOUND018OUT res : out) {
                    if (!"S".equalsIgnoreCase(res.getTYPE())){
                        flag = false;
                        break;
                    }
                }

                result.status = flag ? EisInterfaceFinance.STATUS_SUCCESS
                    : EisInterfaceFinance.STATUS_FAILED;
                result.message = message;
            }
        } catch (Exception e) {
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData("");
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setErrorMessage(e.getMessage());
            result.status = EisInterfaceFinance.STATUS_FAILED;
            result.message = "调用EAI接口失败";
            logger.error("调用EAI接口 PushReturnInfoToGVSHandler 失败：", e);
        }
        dataLog.setCreateTime(DateUtil.currentDateTime());
        recordEisInterfaceDataLog(dataLog);

        return result;
    }


    @Override
    protected boolean isJob(LesStockTransQueue transQueue) {
        return InventoryBusinessTypes.IN_RETURNED.getCode().equalsIgnoreCase(
            transQueue.getBillType());
    }

    @Override
    protected Result process(LesStockTransQueue transQueue) {
        Result result = new Result();

        String corderSn = transQueue.getCorderSn();
        if (StringUtil.isEmpty(corderSn)) {
            result.status = EisInterfaceFinance.STATUS_ERROR;
            result.message = "处理退货入库失败：退货单号不正确，" + corderSn;
            return result;
        }

        //处理正品退货
        String soou = this.isGenuineGoods(corderSn);
        if (!StringUtil.isEmpty(soou)
            && InventoryBusinessTypes.IN_RETURNED.getCode().equals(transQueue.getBillType())) {
            //正品退货发送SAP
            return this.reverseGenuineGoodsToGVS(transQueue, soou);
        }

        if (cOrderSns.contains(corderSn)) {
            result.status = EisInterfaceFinance.STATUS_WARN;
            result.message = "网单号：" + corderSn + "已经调用退货入库接口";
            return result;
        }
        cOrderSns.add(corderSn);

        //组装数据
        com.haier.stock.eai.finance.pushReturnInfoToGVS.ObjectFactory objectFactory = new com.haier.stock.eai.finance.pushReturnInfoToGVS.ObjectFactory();
        InType request = objectFactory.createInType();

        request.setZSYST("EHAI");//系统编码

        request.setZWBDR(corderSn);//退货网单号

        OrderProductsNew orderProducts = getOrderProducts(corderSn.replaceAll("TH.*", ""));
        OrdersNew order = getOrderByWD(orderProducts);

        if (order == null) {
            result.message = "处理退货入库失败：未获取到退货单关联的订单,退货单号：" + corderSn;
            result.status = EisInterfaceFinance.STATUS_ERROR;
            return result;
        }
        // 原始网单号
        if (orderProducts.getSplitFlag() == OrderProductsNew.SPLITFLAG_SPLIT_NEW) {
            request.setZWBDRO(orderProducts.getSplitRelateCOrderSn());// 原始网单号，拆单后新单需要原网单单号
        } else {
            request.setZWBDRO(orderProducts.getCOrderSn());// 原始网单号
        }
        //定金尾款订单，如销售出库推送了SAP，退货入库也推送SAP，如未推送，则都不推送
        if (OrderType.TYPE_GROUP_ADVANCE_TAIL.getValue().equals(order.getOrderType())
            && order.getTaobaoGroupId() > 0 && orderProducts.getShippingOpporunity().equals(0)) {

            //3W的单子特殊处理:因为3W的单子，出库走的单独的逻辑
            if (orderProducts.getStockType() != null
                && orderProducts.getStockType().equalsIgnoreCase("3W")) {
                if (orderProducts.getLessShipTime().equals(0L)
                    || orderProducts.getLessShipTime().intValue() == 0) {
                    result.status = EisInterfaceFinance.STATUS_ERROR;
                    result.message = "3W网单[" + transQueue.getCorderSn()
                        + "]属于定金尾款退货，CBS未收到原单，退货单不推送SAP";
                    logger
                        .info("3W网单[" + transQueue.getCorderSn() + "]属于定金尾款退货，CBS未收到原单，退货单不推送SAP");
                    return result;
                }
            } else {

                List<LesStockTransQueue> depositList = lesStockTransQueueService
                    .getLesStockTransQueueByCorderSn(corderSn.replaceAll("TH.*", ""));
                if ((depositList == null || depositList.isEmpty())
                    && orderProducts.getSplitFlag().intValue() == 2
                    && !StringUtil.isEmpty(orderProducts.getSplitRelateCOrderSn())) {//拆单的情况
                    depositList = lesStockTransQueueService.getLesStockTransQueueByCorderSn(
                        orderProducts.getSplitRelateCOrderSn());
                }
                if (depositList == null || depositList.isEmpty()) {
                    result.status = EisInterfaceFinance.STATUS_ERROR;
                    result.message = "网单[" + transQueue.getCorderSn()
                        + "]属于定金尾款退货，CBS未收到原单，退货单不推送SAP";
                    logger.info("网单[" + transQueue.getCorderSn() + "]属于定金尾款退货，CBS未收到原单，退货单不推送SAP");
                    return result;
                }
                Set<Integer> status = new HashSet<Integer>();
                for (LesStockTransQueue deposit : depositList) {
                    Integer transQueueId = deposit.getId();
                    EisInterfaceFinance eisInterfaceFinance = getInterfaceFinance(transQueueId);
                    if (eisInterfaceFinance == null) {
                        continue;
                    }
                    status.add(eisInterfaceFinance.getStatus());
                }
                if (status.isEmpty()) {
                    result.status = EisInterfaceFinance.STATUS_ERROR;
                    result.message = "网单[" + transQueue.getCorderSn()
                        + "]属于定金尾款退货，原单未推送SAP，退货单不推送SAP";
                    logger.info("网单[" + transQueue.getCorderSn() + "]属于定金尾款退货，原单未推送SAP，退货单不推送SAP");
                    return result;
                }
                if (status.contains(EisInterfaceFinance.STATUS_ERROR)) {
                    result.status = EisInterfaceFinance.STATUS_ERROR;
                    result.message = "网单[" + transQueue.getCorderSn()
                        + "]属于定金尾款退货，原单未成功推送SAP，退货单不推送SAP";
                    logger
                        .info("网单[" + transQueue.getCorderSn() + "]属于定金尾款退货，原单未成功推送SAP，退货单不推送SAP");
                    return result;
                }
            }
        }

        /*if (new Date((long) order.getAddTime() * 1000).before(DateUtil.parse("2013-08-01 00:00:00",
            "yyyy-MM-dd HH:mm:ss"))) {
            result.status = EisInterfaceFinance.STATUS_SUCCESS;
            result.message = "此退货的网单早于 2013-08-01 00:00:00 ，" + corderSn;
            return result;
        }*/

        request.setZWBDT(DateUtil.format(transQueue.getBillTime(), "yyyy-MM-dd"));//网单创建日期

        OrderRepairsNew orderRepairs = getOrderRepairs(orderProducts);
        if (orderRepairs == null) {
            result.status = EisInterfaceFinance.STATUS_ERROR;
            result.message = "处理退货入库失败：网单关联的有效退货单已不存在，退货单号：" + corderSn;
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
        request.setLGORT(transQueue.getSecCode());//库位
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

        if ("BLPHH".equals(order.getSource())) {//不良品换货业务，订单号不能截取
            if (orderSn != null && orderSn.contains("_")) {//网单号不含 “_” 的无需进行截取
                orderSn = orderSn.substring(0, orderSn.indexOf("_"));
            }
            request.setZORDR(orderSn);//订单号
        } else {//其他业务需要截掉订单号的前缀。如TOP_11111111 要截取为 11111111
            request.setZORDR(orderSn == null ? "" : orderSn.replaceAll(".*_", ""));//订单号
        }

        request.setZCHNL(getChannel(corderSn, order.getSource()));//渠道
        request.setZFGBL(isMakeReceipt ? "0" : "1");// 是否已开票

        List<InType> in = new ArrayList<InType>();
        in.add(request);
        com.haier.stock.eai.finance.pushReturnInfoToGVS.PushReturnInfoToGVS soap = new
            com.haier.stock.eai.finance.pushReturnInfoToGVS.PushReturnInfoToGVS_Service(getWSDLURL()).getPushReturnInfoToGVSSOAP();

        // 要记录接口数据日志
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(transQueue.getCorderSn());
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(in));
        Long startTime = System.currentTimeMillis();

        try {
            OutType pushReturnInfoToGVS = soap.pushReturnInfoToGVS(in);
            List<TMSGType> results = pushReturnInfoToGVS.getTMSG();
            String msg = JsonUtil.toJson(results);

            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setResponseTime(DateUtil.currentDateTime());

            dataLog.setResponseData(msg);
            if (results == null || results.size() <= 0) {
                result.status = EisInterfaceFinance.STATUS_FAILED;
                result.message = "调用EAI接口返回数据不合法";
            } else {
                boolean flag = true;
                for (TMSGType rs : results) {
                    if (!flag)
                        break;
                    if ("E".equals(rs.getTYPE()))
                        flag = false;
                }
                result.status = flag ? EisInterfaceFinance.STATUS_UNKNOWN
                    : EisInterfaceFinance.STATUS_FAILED;
                result.message = msg;
            }
        } catch (Exception e) {
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData("");
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setErrorMessage(e.getMessage());
            result.status = EisInterfaceFinance.STATUS_FAILED;
            result.message = "调用EAI接口失败";
            logger.error("调用EAI接口 pushReturnInfoToGVS 失败：", e);
        }

        dataLog.setCreateTime(DateUtil.currentDateTime());
        recordEisInterfaceDataLog(dataLog);

        return result;
    }

    @Override
    protected Result getInterfaceStatus(LesStockTransQueue transQueue) {
        Result rs = new Result();

        com.haier.stock.eai.finance.pushreturn.query.ObjectFactory objectFactory = new com.haier.stock.eai.finance.pushreturn.query.ObjectFactory();
        ZSDS0006 request = objectFactory.createZSDS0006();
        request.setZSYST("EHAI");//系统编码
        String cOrderSn = transQueue.getCorderSn();
        request.setZWBDR(cOrderSn);//网单号

        QueryHandleResultFromGVS soap = new QueryHandleResultFromGVS_Service(getWSDLURLForQuery())
            .getQueryHandleResultFromGVSSOAP();
        List<ZSDS0006> tZSDS0006 = new ArrayList<ZSDS0006>();
        tZSDS0006.add(request);

        // 要记录接口数据日志
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(transQueue.getCorderSn());
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(tZSDS0006));
        Long startTime = System.currentTimeMillis();

        try {
            List<ZSDS0003> results = soap
                .queryHandleResultFromGVS(tZSDS0006);
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData(JsonUtil.toJson(results));

            if (results == null || results.size() <= 0) {
                rs.status = EisInterfaceFinance.STATUS_UNKNOWN;
                rs.message = "EAI查询接口返回空";
                dataLog.setResponseData("");
            } else {
                ZSDS0003 result = results.get(0);
                if ("S".equals(result.getZTYPE()))
                    rs.status = EisInterfaceFinance.STATUS_SUCCESS;
                else
                    rs.status = EisInterfaceFinance.STATUS_UNKNOWN;
                rs.eiaType = result.getZTYPE();
                rs.message = result.getZMSG();
                rs.mark = result.getZSTTS();
                rs.system = result.getZSYST();
            }
        } catch (Exception e) {
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData("");
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setErrorMessage(e.getMessage());
            rs.status = EisInterfaceFinance.STATUS_UNKNOWN;
            rs.message = "调用EAI接口失败：" + e.getMessage();
            logger.error("调用EAI接口 QueryOrderHandleInfoFromGVS 失败：", e);
        }
        dataLog.setCreateTime(DateUtil.currentDateTime());
        dataLog.setInterfaceCode(interfaceCode + "_query");
        recordEisInterfaceDataLog(dataLog);
        return rs;
    }

    private OrderRepairsNew getOrderRepairs(OrderProductsNew op) {
        ServiceResult<OrderRepairsNew> result = orderService.getValidByOrderProductId(op.getId());
        if (!result.getSuccess())
            throw new RuntimeException("orderService返回错误:" + result.getMessage());
        if (result.getResult() == null) {
            ServiceResult<List<OrderRepairsNew>> rs = orderService.getOrderRepairsByOrderProductId(op
                .getId());
            if (rs==null || !rs.getSuccess())
                throw new RuntimeException("orderService-getOrderRepairsByOrderProductId 错误:"
                    + rs.getMessage());
            if (rs.getResult()!=null && rs.getResult().size() > 0)
                return rs.getResult().get(0);
        }
        return result.getResult();
    }
}
