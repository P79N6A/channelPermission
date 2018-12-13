package com.haier.stock.services.finance;

import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.EisInterfaceFinance;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.purchase.data.model.PurchaseItem;
import com.haier.purchase.data.model.PurchaseOrder;
import com.haier.stock.eai.finance.plpurchase.*;
import com.haier.stock.eai.finance.plpurchase.ObjectFactory;
import com.haier.stock.eai.finance.plpurchase.push.*;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.service.PurchaseOrderService;
import com.haier.stock.services.PurchaseOrderServiceImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.ws.Holder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 3PL采购入库
 *                       
 * @Filename: CreatePurchaseOrderInfoFromEhaierHandler.java
 * @Version: 1.0
 * @Author: yanzhao
 * @Email: yan01250428@126.com
 *
 */
@Service
public class CreatePurchaseOrderInfoFromEhaierHandler extends SFHandler {
    private static final Logger      logger          = LogManager
                                                         .getLogger(CreatePurchaseOrderInfoFromEhaierHandler.class);
    @Autowired
    private PurchaseOrderServiceImpl purchaseOrderService;
    private PurchaseItem purchaseItem    = null;


    @Autowired
    public void setInterfaceCode(){
        super.interfaceCode = "CreatePurchaseOrderInfoFromEhaier";
    }
    @Autowired
    public void setWsdlFile(){
        super.wsdlFile = "CreatePurchaseOrderInfoFromEhaier.WSDL";
    }
    @Autowired
    public void setWsdlFileForQuery(){
        super.wsdlFileForQuery = "Trans3PLReceiveInfoFromEhaierToGVS.wsdl";
    }

    @Autowired
    public void setNextHandler(TransDNInfoFromEHAIERToGVSHandler nextHandler){
        super.nextHandler=nextHandler;//父类属性注入
    }

    public static final List<String> NO_PO_SUPPLIERS = Arrays.asList("V98643", "V98726", "V98830", "V98260","V98951","V98968");                //直接推收货库存

    @Override
    protected boolean isJob(LesStockTransQueue transQueue) {
        purchaseItem = null;

        if (InventoryBusinessTypes.IN_PURCHASE.getCode().equalsIgnoreCase(transQueue.getBillType())) {
            String cOrderSn = transQueue.getCorderSn();
            purchaseItem = getPurchaseItem(cOrderSn);
            return purchaseItem != null;
        }

        return false;
    }

    private Result processTrans3PLReceive(LesStockTransQueue transQueue) {
        Result rs = new Result();

        if (!"S".equals(transQueue.getMark())) {
            rs.status = EisInterfaceFinance.STATUS_SUCCESS;
            rs.message = "冲销记录，不推送SAP";
            return rs;
        }

        //组装数据
        com.haier.stock.eai.finance.plpurchase.push.ObjectFactory objectFactory = new com.haier.stock.eai.finance.plpurchase.push.ObjectFactory();
        ZMMS0013 request = objectFactory.createZMMS0013();
        request.setZWBDR(transQueue.getCorderSn());//网单号

        String no = transQueue.getZeile();
        no = no == null ? "" : no.substring(1, 4);
        request.setPOSNR(no);//行项目号

        request.setZSPDT(DateUtil.format(transQueue.getBillTime(), "yyyy-MM-dd"));//入库日期
        request.setMATNR(transQueue.getSku());//物料
        request.setMENGE(new BigDecimal(transQueue.getQuantity()));
        request.setLGORT(transQueue.getSecCode());//库位
        request.setZFGLG(transQueue.getCharg());
        request.setZLSGI(transQueue.getOutping());

        Trans3PLReceiveInfoFromEhaierToGVS soap = new Trans3PLReceiveInfoFromEhaierToGVS_Service(
            getWSDLURLForQuery()).getTrans3PLReceiveInfoFromEhaierToGVSSOAP();

        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(transQueue.getCorderSn());
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(request));
        Long startTime = System.currentTimeMillis();
        try {
            List<ZMMINBOUND007OUT> result = soap.trans3PLReceiveInfoFromEhaierToGVS(
                Arrays.asList(request), SYSNAME);

            String msg = JsonUtil.toJson(result);

            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData(msg);

            if (result == null || result.size() <= 0) {
                rs.status = EisInterfaceFinance.STATUS_FAILED;
                rs.message = "EAI 返回空";
                dataLog.setResponseData("");
            } else {
                boolean success = true;
                for (ZMMINBOUND007OUT zmminbound007OUT : result) {
                    if (!"S".equalsIgnoreCase(zmminbound007OUT.getTYPE())) {
                        success = false;
                        break;
                    }
                }
                rs.status = success ? EisInterfaceFinance.STATUS_SUCCESS
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
            logger.error("调用EAI接口 transReBackInfoFromEHAIERToGVS 失败：", e);
        }
        dataLog.setInterfaceCode("Trans3PLReceiveInfoFromEhaierTOGV");
        dataLog.setCreateTime(DateUtil.currentDateTime());
        recordEisInterfaceDataLog(dataLog);
        return rs;
    }

    @Override
    protected Result process(LesStockTransQueue transQueue) {

        Result rs = new Result();
        purchaseItem = getPurchaseItem(transQueue.getCorderSn());
        if (purchaseItem == null) {
            rs.status = EisInterfaceFinance.STATUS_ERROR;
            rs.message = "采购单不存在，Poid：" + transQueue.getCorderSn();
            return rs;
        }
        PurchaseOrder purchaseOrder = getPurchaseOrder(purchaseItem.getPoId());
        if (purchaseOrder == null) {
            rs.status = EisInterfaceFinance.STATUS_ERROR;
            rs.message = "采购订单不存在，Poid：" + purchaseItem.getPoId();
            return rs;
        }

        for (String noPoSupplier : NO_PO_SUPPLIERS) {
            if (noPoSupplier.equalsIgnoreCase(purchaseOrder.getSuplier())) {
                return processTrans3PLReceive(transQueue);
            }
        }

        com.haier.stock.eai.finance.plpurchase.ObjectFactory objectFactory = new ObjectFactory();
        ZMMS0006 request = objectFactory.createZMMS0006();
        request.setZSPNB(transQueue.getCorderSn());//CBS订单号
        request.setZLSGI(transQueue.getOutping());//LES入库单号
        request.setMATNR(transQueue.getSku());
        request.setMENGE(new BigDecimal(purchaseItem.getPoQty()));//采购数量
        request.setMENGEL(new BigDecimal(transQueue.getQuantity()));//本次入库数量
        request.setNETPR(purchaseItem.getPrice());//采购价格
        request.setLGORT(transQueue.getSecCode());
        request.setLIFNR(purchaseOrder.getSuplier());//供应商
        request.setZSPDT(DateUtil.format(transQueue.getBillTime(), "yyyy-MM-dd"));

        String no = transQueue.getZeile();
        no = no == null ? "" : no.substring(1, 4);
        request.setZSPIT(no);

        CreatePurchaseOrderInfoFromEhaier soap = new CreatePurchaseOrderInfoFromEhaier_Service(
            getWSDLURL()).getCreatePurchaseOrderInfoFromEhaierSOAP();
        List<ZMMS0006> tZMMS0006 = new ArrayList<ZMMS0006>();
        tZMMS0006.add(request);
        // 要记录接口数据日志
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(transQueue.getCorderSn());
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(tZMMS0006));
        Long startTime = System.currentTimeMillis();

        try {
            Holder<Integer> exSUBRC = new Holder<Integer>();
            Holder<List<ZSDS0002>> tMSG = new Holder<List<ZSDS0002>>();

            soap.createPurchaseOrderInfoFromEhaier(tZMMS0006, exSUBRC, tMSG);
            String msg = JsonUtil.toJson(tMSG.value);
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData(msg);
            if (tMSG.value == null || tMSG.value.size() <= 0) {
                rs.status = EisInterfaceFinance.STATUS_FAILED;
                rs.message = "EAI 返回空";
                dataLog.setResponseData("");
            } else {
                boolean flag = true;
                for (ZSDS0002 zsds0002 : tMSG.value) {
                    if (!flag)
                        break;
                    flag = !"E".equalsIgnoreCase(zsds0002.getTYPE());
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
            logger.error("调用EAI接口 transReBackInfoFromEHAIERToGVS 失败：", e);
        }
        dataLog.setCreateTime(DateUtil.currentDateTime());
        recordEisInterfaceDataLog(dataLog);
        return rs;
    }

    @Override
    protected Result getInterfaceStatus(LesStockTransQueue transQueue) {
        throw new BusinessException("不可预估的错误，此接口不支持状态查询");
    }


    private PurchaseOrder getPurchaseOrder(int poId) {
        ServiceResult<PurchaseOrder> rs = purchaseOrderService.getPurchaseOrder(poId);
        if (!rs.getSuccess())
            throw new RuntimeException("purchaseOrderService异常：" + rs.getMessage());
        return rs.getResult();
    }

    private PurchaseItem getPurchaseItem(String cOrderSn) {
        ServiceResult<PurchaseItem> pi_result = purchaseOrderService
            .getPurchaseItemByPoItemNo(cOrderSn);
        if (!pi_result.getSuccess())
            throw new RuntimeException("purchaseOrderService异常：" + pi_result.getMessage());
        return pi_result.getResult();
    }

//    public static void main(String[] args) {
//        System.out.println("0001".substring(1, 4));
//    }

}
