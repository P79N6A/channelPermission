package com.haier.stock.services.finance;

import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.EisInterfaceFinance;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.shop.model.Invoices;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrdersNew;
import com.haier.stock.eai.finance.pushprosale.*;
import com.haier.stock.eai.finance.pushprosale.query.QueryProSaleHandleFromEhaierSAP;
import com.haier.stock.eai.finance.pushprosale.query.QueryProSaleHandleFromEhaierSAP_Service;

import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.stock.model.InventoryBusinessTypes;
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

@Service
public class PushProSaleOrderToEhaierSAPHandler extends SFHandler {
    private Logger logger = LogManager.getLogger(PushProSaleOrderToEhaierSAPHandler.class);


    @Autowired
    public void setInterfaceCode(){
        super.interfaceCode = "PushProSaleOrderToEhaierSAP";
    }
    @Autowired
    public void setWsdlFile(){
        super.wsdlFile = "PushProSaleOrderToEhaierSAP.wsdl";
    }
    @Autowired
    public void setWsdlFileForQuery(){
        super.wsdlFileForQuery = "QueryProSaleHandleFromEhaierSAP.wsdl";
    }

    @Autowired
    public void setNextHandler(CreatePurchaseOrderInfoFromEhaierHandler nextHandler){
        super.nextHandler=nextHandler;//父类属性注入
    }

    @Override
    protected boolean isJob(LesStockTransQueue transQueue) {
        return InventoryBusinessTypes.OUT_SALE.getCode().equals(transQueue.getBillType()) && LesStockTransQueue.CHANNEL_RRS
                .equals(transQueue.getChannelCode());
    }

    @Override
    protected Result process(LesStockTransQueue transQueue) {
        Result rs = new Result();

        com.haier.stock.eai.finance.pushprosale.ObjectFactory objectFactory = new ObjectFactory();
        ZSDS0008 request = objectFactory.createZSDS0008();

        request.setZSYST("EHAI");

        String corderSn = transQueue.getCorderSn();
        request.setZWBDR(corderSn);//网单号
        OrderProductsNew orderProduct = getOrderProducts(corderSn);
        if (orderProduct == null) {
            rs.status = EisInterfaceFinance.STATUS_ERROR;
            rs.message = "关联的网单不存在，网单号：" + corderSn;
            return rs;
        }
        OrdersNew order = getOrderByWD(orderProduct);
        if (order == null) {
            rs.status = EisInterfaceFinance.STATUS_ERROR;
            rs.message = "关联的订单不存在，网单号：" + corderSn;
            return rs;
        }

        request.setKUNNRRG("");//付款方 空值

        request.setZWBDT(DateUtil.format(transQueue.getBillTime(), "yyyy-MM-dd"));
        request.setAUGRU("");//订单原因
        request.setMATNR(orderProduct.getSku());
        request.setKWMENG(new BigDecimal(orderProduct.getNumber()));

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
            rs.message = "此网单的开票时间早于2013-08-31 24：00：00，不用传给SAP，网单号：" + orderProduct.getCOrderSn();
            return rs;
        }

        if (!isMakeReceipt)
            price = computeInvoicePrice(order, orderProduct);

        request.setKBETR(price);//单价

        request.setKUNNRAG(getCustormCode(orderProduct, order, price));//客户编码
        request.setLGORT(orderProduct.getSCode());//WA库位

        String orderSn = order.getOrderSn();
        String relationOrderSn = order.getRelationOrderSn();
        if (!StringUtil.isEmpty(relationOrderSn) && !"新单".equalsIgnoreCase(relationOrderSn)) {
            orderSn = relationOrderSn;
        } else if (!StringUtil.isEmpty(order.getSourceOrderSn())
                   && !"新单".equalsIgnoreCase(order.getSourceOrderSn())) {
            orderSn = order.getSourceOrderSn();
        }

        request.setZORDR(orderSn == null ? "" : orderSn.replaceAll(".*_", ""));
        request.setZCHNL(getChannel(corderSn, order.getSource()));

        PushProSaleOrderToEhaierSAP soap = new PushProSaleOrderToEhaierSAP_Service(getWSDLURL())
            .getPushProSaleOrderToEhaierSAPSOAP();
        Holder<List<ZSDS0002>> tMSG = new Holder<List<ZSDS0002>>();
        Holder<Integer> exSUBRC = new Holder<Integer>();
        List<ZSDS0008> tZSDS0008 = new ArrayList<ZSDS0008>();
        tZSDS0008.add(request);

        // 要记录接口数据日志
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(transQueue.getCorderSn());
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(tZSDS0008));
        Long startTime = System.currentTimeMillis();

        try {
            soap.pushProSaleOrderToEhaierSAP(tZSDS0008, exSUBRC, tMSG);
            String msg = JsonUtil.toJson(tMSG.value);

            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData(msg);

            if (tMSG.value == null || tMSG.value.size() <= 0) {
                rs.status = EisInterfaceFinance.STATUS_FAILED;
                rs.message = "EAI返回空";
                dataLog.setResponseData("");
            } else {
                boolean flag = true;
                for (ZSDS0002 zsds0002 : tMSG.value) {
                    if (!"S".equalsIgnoreCase(zsds0002.getTYPE())){
                        flag = false;
                        break;
                    }
                }

                rs.status = flag ? EisInterfaceFinance.STATUS_SUCCESS
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
        com.haier.stock.eai.finance.pushprosale.query.ObjectFactory objectFactory = new com.haier.stock.eai.finance.pushprosale.query.ObjectFactory();
        com.haier.stock.eai.finance.pushprosale.query.ZSDS0006 request = objectFactory
            .createZSDS0006();
        request.setZSYST("EHAI");//系统编码
        String cOrderSn = transQueue.getCorderSn();
        request.setZWBDR(cOrderSn);//网单号

        QueryProSaleHandleFromEhaierSAP soap = new QueryProSaleHandleFromEhaierSAP_Service(
            getWSDLURLForQuery()).getQueryProSaleHandleFromEhaierSAPSOAP();

        List<com.haier.stock.eai.finance.pushprosale.query.ZSDS0006> tZSDS0006 = new ArrayList<com.haier.stock.eai.finance.pushprosale.query.ZSDS0006>();
        tZSDS0006.add(request);

        // 要记录接口数据日志
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(transQueue.getCorderSn());
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(tZSDS0006));
        Long startTime = System.currentTimeMillis();

        try {
            List<com.haier.stock.eai.finance.pushprosale.query.ZSDS0003> results = soap
                .queryProSaleHandleFromEhaierSAP(tZSDS0006);
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData(JsonUtil.toJson(results));

            if (results == null || results.size() <= 0) {
                rs.status = EisInterfaceFinance.STATUS_UNKNOWN;
                rs.message = "EAI查询接口返回空";
                dataLog.setResponseData("");
            } else {
                com.haier.stock.eai.finance.pushprosale.query.ZSDS0003 result = results.get(0);
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
