package com.haier.afterSale.model;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.haier.afterSale.service.ThTransactionService;
import com.haier.afterSale.util.HelpUtils;
import com.haier.afterSale.webService.pushSAP.InType;
import com.haier.afterSale.webService.pushSAP.OutType;
import com.haier.afterSale.webService.pushSAP.PushReturnInfoToGVS;
import com.haier.afterSale.webService.pushSAP.PushReturnInfoToGVS_Service;
import com.haier.afterSale.webService.pushSAP.TMSGType;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.EisInterfaceQueueData;
import com.haier.eis.service.EisInterfaceQueueDataService;
import com.haier.shop.model.Invoices;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrderRepairsNew;
import com.haier.shop.model.OrderType;
import com.haier.shop.model.OrderhpRejectionLogsVO;
import com.haier.shop.model.OrdersNew;
import com.haier.shop.service.InvoicesService;
import com.haier.stock.model.InvThTransaction;
@Service
public class TransferDefectiveProductsInToGVSModel {

    private Logger                   logger = LogManager
                                                .getLogger(TransferDefectiveProductsInToGVSModel.class);
    @Autowired
    private ThTransactionService     thTransactionService;
    @Autowired
    private HelpUtils                help;
    @Autowired
    private InvoicesService              invoicesDao;
    private String                   wsdlFile;
    @Autowired
    private EisInterfaceQueueDataService eisInterfaceQueueDataDao;
    @Value("${wsdlPath}")
    private String wsdlPath;

    /**
     * 查询不良品虚入数据
     * @param params
     * @return
     */
    private List<InvThTransaction> getTransferData(String channel) {
        ServiceResult<List<InvThTransaction>> result = thTransactionService.getInDataList(channel);
        if (result == null || !result.getSuccess()) {
            logger.info("查询不良品虚入信息失败");
            return null;
        }
        return result.getResult();
    }

    private InvThTransaction getInvThTransactionById(Integer id) {
        ServiceResult<InvThTransaction> result = thTransactionService.get(id);
        if (result == null || !result.getSuccess()) {
            logger.info("查询不良品虚入信息失败");
            return null;
        }
        return result.getResult();
    }

    /**
     * 查询发送失败的不良品虚入数据
     * @return
     */
    private List<InvThTransaction> getTransferFailureData() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("source", "0");//0-不良品
        params.put("billType", "1");//0-出；1-入
        params.put("status", "2");//处理状态(-1-出现特殊处理；0-未知；1-成功；2-失败；3-错误)
        List<EisInterfaceQueueData> queueDatas = eisInterfaceQueueDataDao
            .queryEisInterfaceQueueData(params);
        if (queueDatas == null || queueDatas.size() == 0) {
            return null;
        }
        List<InvThTransaction> transactions = new ArrayList<InvThTransaction>();
        for (EisInterfaceQueueData queueData : queueDatas) {
            Integer sourceId = queueData.getSourceId();
            InvThTransaction thTransaction = this.getInvThTransactionById(sourceId);
            if (thTransaction != null) {
                transactions.add(thTransaction);
            }
        }
        return transactions;
    }
//
//    //发送不良品虚入信息到SAP
//    public void transferDefectiveProductsInToGVS() {
//        //1、处理以往发送失败的数据
//        List<InvThTransaction> failureData = this.getTransferFailureData();
//        this.handler(failureData);
//        //2、处理新的数据
//        String channel = InvThTransaction.CHANNEL_SHANGCHENG;
//        List<InvThTransaction> invThTransactions = this.getTransferData(channel);
//        this.handler(invThTransactions);
//    }
//
//    /**
//     * 逐条发送数据到SAP
//     * @param invThTransactions
//     */
//    private void handler(List<InvThTransaction> invThTransactions) {
//
//        if (invThTransactions == null || invThTransactions.size() == 0) {
//            logger.info("不良品虚入信息不存在");
//            return;
//        }
//
//        for (InvThTransaction transaction : invThTransactions) {
//            try {
//                transfer(transaction);
//            } catch (Exception e) {
//                logger.error("New[To Sap]:发送不良品虚入信息到SAP发生异常InvThTransactionID：["
//                             + transaction.getId() + "]," + e.getMessage());
//            }
//        }
//    }

    /**
     * 发送数据到SAP
     * @param transaction
     * @throws MalformedURLException 
     */
    public String transfer(OrderhpRejectionLogsVO transaction) throws MalformedURLException {
    	String success ="";
        String channelOrderSn = transaction.getChannelOrderSn();
        if (StringUtil.isEmpty(channelOrderSn)) {
            throw new BusinessException("网单号不存在");
        }
        //组装数据
        com.haier.afterSale.webService.pushSAP.ObjectFactory objectFactory = new com.haier.afterSale.webService.pushSAP.ObjectFactory();
        InType request = objectFactory.createInType();
        request.setZSYST("BLP1");//系统编码
        request.setZWBDR(channelOrderSn);//退货网单号
        
//        request.setZWBDR("WD182922537787TH2");//退货网单号
        
        request.setZWBDT(DateUtil.format(new Date(), "yyyy-MM-dd"));//网单创建日期

        OrderProductsNew orderProducts = help.getOrderProducts(channelOrderSn.replaceAll("TH.*|TC.*", ""));
        if (orderProducts == null) {
            throw new BusinessException("网单号关联的网单已不存在，WD:" + channelOrderSn);
        }
        OrdersNew order = help.getOrderByWD(orderProducts);

        if (order == null) {
            throw new BusinessException("网单号关联的订单已不存在,WD:" + channelOrderSn);
        }

        OrderRepairsNew orderRepairs = help.getOrderRepairs(orderProducts);
        if (orderRepairs == null) {
            throw new BusinessException("网单号关联的退货单已不存在,WD:" + channelOrderSn); 
        }
        String reason = help.getReasonCode(orderRepairs.getReason());
        if (StringUtil.isEmpty(reason)) {
            reason = help.getReasonCode("其他");
        }
        request.setAUGRU(reason);//退货原因
        request.setMATNR(transaction.getSubCount() == null || transaction.getSubCount() <= 1 ? transaction.getSku() : transaction.getMainSku());//物料编码
        request.setKWMENG(new BigDecimal(transaction.getQuantity()));//数量

        BigDecimal price = null;
        List<Invoices> invoices = invoicesDao.getByOrderProductId(orderProducts.getId());
        /* if (invoices == null || invoices.size() == 0) {
             throw new BusinessException("网单号关联的发票信息已不存在,WD:" + channelOrderSn);
         }*/
        boolean isMakeReceipt = false;
        for (Invoices invoice : invoices) {
            if (invoice.getState() == 4) {
                isMakeReceipt = true;
                price = invoice.getPrice();
                break;
            }
        }

        if (!isMakeReceipt)
            price = help.computeInvoicePrice(order, orderProducts);

        request.setKBETR(price);//单价
        request.setKUNNRAG(help.getCustormCode(orderProducts, order, price));// 客户编码
        request.setLGORT(transaction.getSecCode());//库位
        // 原始网单号
        if (orderProducts.getSplitFlag() == OrderProductsNew.SPLITFLAG_SPLIT_NEW) {
       	 request.setZWBDRO(orderProducts.getSplitRelateCOrderSn());// 原始网单号，拆单后新单需要原网单单号
        } else {
       	 request.setZWBDRO(orderProducts.getCOrderSn());// 原始网单号
//       	 request.setZWBDRO("WD182922537787");// 原始网单号
        }
        String orderSn = order.getOrderSn();
        String relationOrderSn = order.getRelationOrderSn();
        if (!StringUtil.isEmpty(relationOrderSn) && !"新单".equalsIgnoreCase(relationOrderSn)) {
            orderSn = relationOrderSn;
        } else if (!StringUtil.isEmpty(order.getSourceOrderSn())
                   && !"新单".equalsIgnoreCase(order.getSourceOrderSn())) {
            orderSn = order.getSourceOrderSn();
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

        String channel = help.getChannel(channelOrderSn, order.getSource());
        request.setZCHNL(channel);//渠道
        request.setZFGBL(isMakeReceipt ? "0" : "1");// 是否已开票
        List<InType> in = new ArrayList<InType>();
        in.add(request);   
        URL url = this.getClass().getResource(wsdlPath + "/PushReturnInfoToGVS.wsdl");
        PushReturnInfoToGVS soap = new PushReturnInfoToGVS_Service(url).getPushReturnInfoToGVSSOAP();
        logger.info("不良品虚入发送SAP参数：" + JsonUtil.toJson(request));
        // 要记录接口数据日志
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(transaction.getChannelOrderSn());
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(request));
        Long startTime = System.currentTimeMillis();

        Integer status = null;
        String message = null;
        try {
			//发送数据
			
			OutType pushReturnInfoToGVS = soap.pushReturnInfoToGVS(in);
			List<TMSGType> results = pushReturnInfoToGVS.getTMSG();
            String msg = JsonUtil.toJson(results);
            success=results.get(0).getTYPE();
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setResponseTime(DateUtil.currentDateTime());

            dataLog.setResponseData(msg);
            if (results == null || results.size() <= 0) {
                status = EisInterfaceQueueData.STATUS_FAILED;
                message = "调用EAI接口返回数据不合法";
            } else {
                boolean flag = true;
                for (TMSGType rs : results) {
                    //                    flag = !"E".equalsIgnoreCase(rs.getTYPE());
                    //                    if (!flag)
                    //                        break;
                    if (!"S".equalsIgnoreCase(rs.getTYPE())) {
                        flag = false;
                        break;
                    }
                }
                status = flag ? EisInterfaceQueueData.STATUS_SUCCESS
                    : EisInterfaceQueueData.STATUS_FAILED;
                message = msg;
            }
        } catch (Exception e) {
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData("");
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setErrorMessage(e.getMessage());
            status = EisInterfaceQueueData.STATUS_FAILED;
            message = "调用EAI接口失败";
            logger.error("调用EAI接口 pushReturnInfoToGVS 失败：", e);
        }
        dataLog.setCreateTime(DateUtil.currentDateTime());
        dataLog.setInterfaceCode(EisInterfaceQueueData.INTERFACE_CODE_DEFECTIVEPRODUCTS_IN);
        //记录日志
        help.recordEisInterfaceDataLog(dataLog);
        //记录eis接口数据
        EisInterfaceQueueData queueData = new EisInterfaceQueueData();
        queueData.setSourceId(transaction.getId());
        queueData.setSource(EisInterfaceQueueData.SOURCE_DEFECTIVEPRODUCTS);
        queueData.setBillType(EisInterfaceQueueData.BILLTYPE_IN);
        queueData.setInterfaceCode(EisInterfaceQueueData.INTERFACE_CODE_DEFECTIVEPRODUCTS_IN);
        queueData.setStatus(status);
        queueData.setMessage(message);
        queueData.setDataLogId(dataLog.getId());
        Integer countRow = help.recordEisInterfaceQueueData(queueData);
        //发送成功且数据成功放入eis_interface_queue_data，更新源数据表inv_th_transaction状态
        if ((status != null && status == EisInterfaceQueueData.STATUS_SUCCESS)
            && (countRow != null && countRow > 0)) {
            /*    InvThTransaction thTransaction = new InvThTransaction();
                thTransaction.setId(transaction.getId());
                thTransaction.setInStatus(InvThTransaction.TRANS_IN_STATUS_ACCEPTED_BYSAP);//4:ICMS返回SAP已接收
                thTransactionService.updateById(thTransaction);*/

            List<String> orderSnList = new ArrayList<String>();
            orderSnList.add(transaction.getcOrderSn());
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("inStatus", InvThTransaction.TRANS_IN_STATUS_ACCEPTED_BYSAP);
            params.put("corder_sn", orderSnList);
            params.put("where_in_status", "1");//等于1的才更新
            thTransactionService.updateInStatusByOrderSns(params);
        }
        return success;
    }

    public void setThTransactionService(ThTransactionService thTransactionService) {
        this.thTransactionService = thTransactionService;
    }

    public void setHelp(HelpUtils help) {
        this.help = help;
    }

    public void setWsdlFile(String wsdlFile) {
        this.wsdlFile = wsdlFile;
    }


}
