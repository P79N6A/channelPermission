package com.haier.stock.services.finance;

import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.EisInterfaceFinance;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.service.LesStockTransQueueService;
import com.haier.shop.model.ChangeOrderConfig;
import com.haier.shop.model.Invoices;
import com.haier.shop.model.OrderProductsAttributes;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrderType;
import com.haier.shop.model.OrdersNew;
import com.haier.stock.eai.finance.order.ObjectFactory;
import com.haier.stock.eai.finance.order.TransOrderInfoFromEhaierToGVS;
import com.haier.stock.eai.finance.order.TransOrderInfoFromEhaierToGVS_Service;
import com.haier.stock.eai.finance.order.ZSDS0001;
import com.haier.stock.eai.finance.order.ZSDS0002;
import com.haier.stock.eai.finance.order.query.QueryOrderHandleInfoFromGVS;
import com.haier.stock.eai.finance.order.query.QueryOrderHandleInfoFromGVS_Service;
import com.haier.stock.eai.finance.order.query.ZSDS0003;
import com.haier.stock.eai.finance.order.query.ZSDS0006;

import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.util.HelpUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.ws.Holder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 标准销售出库接口（需要在正品退货Handler后触发）
 *                       
 * @Filename: TransOrderInfoFromEhaierToGVSHandler.java
 * @Version: 1.0
 * @Author: yanzhao
 * @Email: yan01250428@126.com
 *
 */
@Service
public class TransOrderInfoFromEhaierToGVSHandler extends SFHandler {

    private Logger logger = LogManager.getLogger(TransOrderInfoFromEhaierToGVSHandler.class);


    @Autowired
    public void setInterfaceCode(){
        super.interfaceCode = "TransOrderInfoFromEhaierToGVSHandler";
    }
    @Autowired
    public void setWsdlFile(){
        super.wsdlFile = "TransOrderInfoFromEhaierToGVS.wsdl";
    }
    @Autowired
    public void setWsdlFileForQuery(){
        super.wsdlFileForQuery = "QueryOrderHandleInfoFromGVS.wsdl";
    }

    @Autowired
    public void setNextHandler(PushProSaleOrderToEhaierSAPHandler nextHandler){
        super.nextHandler=nextHandler;//父类属性注入
    }

    @Autowired
    private LesStockTransQueueService lesStockTransQueueService;
    /**
     * 从自有库存出库且订单来源不等于正品退货（由于本handler再正品退货的handler后处理，所以不判断正品退货环节）
     * @param transQueue
     * @return
     * @see com.haier.stock.services.finance.SFHandler#isJob(com.haier.eis.model.LesStockTransQueue)
     */
    @Override
    protected boolean isJob(LesStockTransQueue transQueue) {
        return LesStockTransQueue.CHANNEL_WA.equals(transQueue.getChannelCode())
               && (InventoryBusinessTypes.OUT_SALE.getCode().equals(transQueue.getBillType()) && !transQueue
                   .getCorderSn().endsWith("J"));
    }

    @Override
    protected Result process(LesStockTransQueue transQueue) {

        Result rs = new Result();

        if (cOrderSns.contains(transQueue.getCorderSn())) {
            rs.status = EisInterfaceFinance.STATUS_WARN;
            rs.message = "网单号：" + transQueue.getCorderSn() + "已经调用销售出库接口";
            return rs;
        }
        cOrderSns.add(transQueue.getCorderSn());

        com.haier.stock.eai.finance.order.ObjectFactory objectFactory = new ObjectFactory();
        ZSDS0001 request = objectFactory.createZSDS0001();

        request.setZSYST("EHAI");//系统编码

        String cOrderSn = transQueue.getCorderSn();
        request.setZWBDR(cOrderSn);//网单号

        OrderProductsNew orderProduct = getOrderProducts(cOrderSn);
        if (orderProduct == null) {
            rs.status = EisInterfaceFinance.STATUS_ERROR;
            rs.message = "关联的网单不存在，网单号：" + cOrderSn;
            return rs;
        }

        OrdersNew order = getOrderByWD(orderProduct);
        if (order == null) {
            rs.status = EisInterfaceFinance.STATUS_ERROR;
            rs.message = "关联的订单不存在，网单号：" + cOrderSn;
            return rs;
        }

        //定金尾款订单未付尾款不推送SAP
        if (OrderType.TYPE_GROUP_ADVANCE_TAIL.getValue().equals(order.getOrderType())
            && order.getTailPayTime() <= 0L
        //            && order.getTaobaoGroupId() > 0 && orderProduct.getShippingOpporunity().equals(0)
        ) {
            //            if (order.getTailPayTime() <= 0) {//未付尾款
            rs.status = EisInterfaceFinance.STATUS_ERROR;
            rs.message = "网单[" + transQueue.getCorderSn() + "]属于定金尾款，未支付尾款前不推送SAP";
            logger.info("网单[" + transQueue.getCorderSn() + "]属于定金尾款，未支付尾款前不推送SAP");
            return rs;
            //            }
        }

        //        request.setKUNNRRG("");//付款方
        request.setKUNNRRG(HelpUtils.getKunnrRg(order.getSource()));//付款方
        request.setZWBDT(DateUtil.format(transQueue.getBillTime(), "yyyy-MM-dd"));//网单创建日期
        request.setAUGRU("");//订单原因
        request.setMATNR(orderProduct.getSku());//物料编码
        request.setKWMENG(new BigDecimal(orderProduct.getNumber()));//数量

        BigDecimal price = null;
        List<Invoices> invoices = invoicesDao.getByOrderProductId(orderProduct.getId());

        boolean isMakeReceipt = false;
        Date createReceiptTime = null;

        for (Invoices invoice : invoices) {
            if (invoice.getState() == 4) {
                isMakeReceipt = true;
                price = invoice.getPrice();
                createReceiptTime = new Date((long) invoice.getBillingTime() * 1000);
                break;
            }
        }

        if (createReceiptTime != null
            && createReceiptTime.before(DateUtil
                .parse("2013-09-01 00:00:00", "yyyy-MM-dd HH:mm:ss"))) {
            rs.status = EisInterfaceFinance.STATUS_SUCCESS;
            rs.message = "此网单的开票时间早于2013-08-31 24：00：00，不用传给SAP，网单号：" + cOrderSn;
            return rs;
        }

        if (!isMakeReceipt){
            logger.error("");
            price = computeInvoicePrice(order, orderProduct);
        }

        try {
            request.setKBETR(price);//单价
        } catch (Exception e) {
            logger.error("网单信息中的单价计算出现错误：", e);
            request.setKBETR(null);
        }

        request.setKUNNRAG(getCustormCode(orderProduct, order, price));//客户编码
        request.setLGORT(transQueue.getSecCode());//库位

        request.setKUNNRZYZD("");//自营转单店铺88码
        OrderProductsAttributes orderProductsAttributes = getOrderProductsAttributes(orderProduct
            .getId());
        if (orderProductsAttributes != null && orderProductsAttributes.getIsSelfSell() == 1) {//自营转单的单子
            ChangeOrderConfig changeOrderConfig = getChangeOrderConfig(orderProductsAttributes
                .getCustomerId());
            request.setKUNNRZYZD(changeOrderConfig != null ? changeOrderConfig
                .getCustomerStoreCode() : "");//自营转单店铺88码
        }
        //备用字段
        request.setADD1("");
        request.setADD2("");
        request.setADD3("");

        String orderSn = order.getOrderSn();
        String relationOrderSn = order.getRelationOrderSn();
        if (!StringUtil.isEmpty(relationOrderSn) && !"新单".equalsIgnoreCase(relationOrderSn)) {
            orderSn = relationOrderSn;
        } else if (!StringUtil.isEmpty(order.getSourceOrderSn())
                   && !"新单".equalsIgnoreCase(order.getSourceOrderSn())) {
            orderSn = order.getSourceOrderSn().trim();
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

        request.setZCHNL(getChannel(cOrderSn, order.getSource()));//渠道

        List<ZSDS0001> tZSDS0001 = new ArrayList<ZSDS0001>();
        tZSDS0001.add(request);

        TransOrderInfoFromEhaierToGVS soap = new TransOrderInfoFromEhaierToGVS_Service(getWSDLURL())
            .getTransOrderInfoFromEhaierToGVSSOAP();

        Holder<Integer> exSUBRC = new Holder<Integer>();
        Holder<List<ZSDS0002>> tMSG = new Holder<List<ZSDS0002>>();

        // 要记录接口数据日志
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(transQueue.getCorderSn());
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(tZSDS0001));
        Long startTime = System.currentTimeMillis();

        try {
            soap.transOrderInfoFromEhaierToGVS(tZSDS0001, exSUBRC, tMSG);
            String msg = JsonUtil.toJson(tMSG.value);

            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData(msg);

            rs.status = EisInterfaceFinance.STATUS_UNKNOWN;
            if (tMSG.value == null || tMSG.value.size() <= 0) {
                rs.status = EisInterfaceFinance.STATUS_FAILED;
                rs.message = "EAI返回空";
                dataLog.setResponseData("");
            } else {
                boolean isSuccess = true;
                for (ZSDS0002 zsds0002 : tMSG.value) {
                    /*if (EisInterfaceFinance.STATUS_FAILED == rs.status)
                        break;
                    rs.status = "E".equalsIgnoreCase(zsds0002.getTYPE()) ? EisInterfaceFinance.STATUS_FAILED
                        : EisInterfaceFinance.STATUS_UNKNOWN;      */
                    //******** 2018-06-14 优化返回参数判断******************
                    if (!"S".equalsIgnoreCase(zsds0002.getTYPE())) {
                        isSuccess = false;
                        break;
                    }
                }
                rs.status = isSuccess ? EisInterfaceFinance.STATUS_SUCCESS
                    : EisInterfaceFinance.STATUS_FAILED;
                rs.message = msg;
            }
        } catch (Exception e) {
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData("");
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setErrorMessage(e.getMessage());
            rs.status = EisInterfaceFinance.STATUS_FAILED;
            rs.message = "调用EAI接口失败";
            logger.error("调用EAI接口 TransOrderInfoFromEhaierToGVS 失败：", e);
        }

        dataLog.setCreateTime(DateUtil.currentDateTime());
        recordEisInterfaceDataLog(dataLog);

        return rs;
    }

    @Override
    protected Result getInterfaceStatus(LesStockTransQueue transQueue) {
        Result rs = new Result();
        com.haier.stock.eai.finance.order.query.ObjectFactory objectFactory = new com.haier.stock.eai.finance.order.query.ObjectFactory();
        ZSDS0006 request = objectFactory.createZSDS0006();
        request.setZSYST("EHAI");//系统编码
        String cOrderSn = transQueue.getCorderSn();
        request.setZWBDR(cOrderSn);//网单号

        QueryOrderHandleInfoFromGVS soap = new QueryOrderHandleInfoFromGVS_Service(
            getWSDLURLForQuery()).getQueryOrderHandleInfoFromGVSSOAP();

        List<ZSDS0006> tZSDS0006 = new ArrayList<ZSDS0006>();
        tZSDS0006.add(request);

        // 要记录接口数据日志
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(transQueue.getCorderSn());
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(tZSDS0006));
        Long startTime = System.currentTimeMillis();

        try {
            List<ZSDS0003> results = soap.queryOrderHandleInfoFromGVS(tZSDS0006);
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
                rs.mark = result.getZSTTS();
                rs.message = result.getZMSG();
                rs.system = result.getZSYST();
            }
        } catch (Exception e) {
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData("");
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setErrorMessage(e.getMessage());
            rs.status = EisInterfaceFinance.STATUS_UNKNOWN;
            rs.message = "调用EAI接口失败";
            logger.error("调用EAI接口 QueryOrderHandleInfoFromGVS 失败：", e);
        }
        dataLog.setCreateTime(DateUtil.currentDateTime());
        dataLog.setInterfaceCode(interfaceCode + "_query");
        recordEisInterfaceDataLog(dataLog);
        return rs;
    }
}
