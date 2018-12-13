package com.haier.stock.services.finance;

import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.EisInterfaceFinance;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.service.LesStockTransQueueService;
import com.haier.stock.eai.finance.postaccounttoehaiersap.PostAccountToEhaierSAP;
import com.haier.stock.eai.finance.postaccounttoehaiersap.PostAccountToEhaierSAP_Service;
import com.haier.stock.eai.finance.postaccounttoehaiersap.ZMMS0102;
import com.haier.stock.eai.finance.postaccounttoehaiersap.ZMMS0108;
import com.haier.stock.eai.finance.purchasefromgvs.ObjectFactory;
import com.haier.stock.eai.finance.purchasefromgvs.TransDNInfoFromEHAIERToGVS;
import com.haier.stock.eai.finance.purchasefromgvs.TransDNInfoFromEHAIERToGVS_Service;
import com.haier.stock.eai.finance.purchasefromgvs.ZMMS0003;
import com.haier.stock.eai.finance.purchasefromgvs.ZSDS0002;

import com.haier.common.BusinessException;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.stock.model.InventoryBusinessTypes;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.Holder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 电商日日顺&统帅PO收货过账接口（MM_01）
 *                       
 * @Filename: TransDNInfoFromEHAIERToGVSHandler.java
 * @Version: 1.0
 * @Author: yanzhao
 * @Email: yan01250428@126.com
 *
 */
@Service
public class TransDNInfoFromEHAIERToGVSHandler extends SFHandler {

    private Logger logger = LogManager.getLogger(TransDNInfoFromEHAIERToGVSHandler.class);

    @Autowired
    public void setInterfaceCode(){
        super.interfaceCode = "TransDNInfoFromEHAIERToGVS";
    }
    @Autowired
    public void setWsdlFile(){
        super.wsdlFile = "TransDNInfoFromEHAIERToGVS.wsdl";
    }
    @Autowired
    public void setWsdlFileForQuery(){
        super.wsdlFileForQuery = "PostAccountToEhaierSAP.wsdl";
    }

    @Autowired
    public void setNextHandler(TransferGoodsInfoToEhaierSAPHandler nextHandler){
        super.nextHandler=nextHandler;//父类属性注入
    }

    @Autowired
    private LesStockTransQueueService lesStockTransQueueService;

    @Override
    protected boolean isJob(LesStockTransQueue transQueue) {
        //  日日顺&与统帅的采购排定逻辑,在PL采购后处理，不是PL的则是日日顺&统帅的
        //2018-8-23 防止查询到的3PL采购的数据 没有在3PL采购定时任务里处理到了这里
        return InventoryBusinessTypes.IN_PURCHASE.getCode().equals(transQueue.getBillType()) && !transQueue.getCorderSn().startsWith("WD");
    }

    @Override
    protected Result process(LesStockTransQueue transQueue) {
        Result rs = new Result();

        String cOrderSn = transQueue.getCorderSn();
        if (StringUtil.isEmpty(cOrderSn)) {
            rs.status = EisInterfaceFinance.STATUS_ERROR;
            rs.message = "cOrderSn为空";
            return rs;
        }
        if (cOrderSn.matches("8.+(D.*)?")) {//85开头 D*结尾的
            return dealRRS(transQueue);
        } else if (cOrderSn.matches("(PC).+")) {//PC开头//金立单号 出库单号
            return dealJinLi(transQueue);
        } else if (cOrderSn.matches("(SI).+")) {//SO或者SI开头//DBM 出库单号
            return dealTongShuai(transQueue);
        } else {
            rs.status = EisInterfaceFinance.STATUS_ERROR;
            rs.message = "cOrderSn无法匹配DN或DBM规则";
            return rs;
        }

    }

    private Result dealRRS(LesStockTransQueue transQueue) {
        Result rs = new Result();

        com.haier.stock.eai.finance.purchasefromgvs.ObjectFactory objectFactory = new ObjectFactory();
        ZMMS0003 request = objectFactory.createZMMS0003();

        request.setZSPDT(DateUtil.format(transQueue.getBillTime(), "yyyy-MM-dd"));//订单入库日期(采购订单如何获取)
        request.setMATNR(transQueue.getSku());//物料编码
        request.setMENGE(new BigDecimal(transQueue.getQuantity()));//交货数量
        request.setLGORT(transQueue.getSecCode());//库位

        String cOrderSn = transQueue.getCorderSn();
        request.setVBELN(cOrderSn.replaceAll("D.*", ""));//客户采购订单编号（DN)
        request.setZSPNB("");
        request.setLIFNR("01");// 供应商

        request.setZLSGI(transQueue.getOutping());//les入库单号
        request.setZFGLG(transQueue.getCharg());//批次编号
        request.setPOSNR(transQueue.getZeile());

        TransDNInfoFromEHAIERToGVS soap = new TransDNInfoFromEHAIERToGVS_Service(getWSDLURL())
            .getTransDNInfoFromEHAIERToGVSSOAP();
        Holder<List<ZSDS0002>> tMSG = new Holder<List<ZSDS0002>>();
        Holder<Integer> exSUBRC = new Holder<Integer>();
        List<ZMMS0003> tZMMS0003 = new ArrayList<ZMMS0003>();
        tZMMS0003.add(request);

        // 要记录接口数据日志
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(transQueue.getCorderSn());
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(tZMMS0003));
        Long startTime = System.currentTimeMillis();

        try {
            soap.transDNInfoFromEHAIERToGVS(tZMMS0003, exSUBRC, tMSG);
            String msg = JsonUtil.toJson(tMSG.value);
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData(msg);

            List<ZSDS0002> results = tMSG.value;
            if (results == null || results.size() <= 0) {
                rs.status = EisInterfaceFinance.STATUS_FAILED;
                rs.message = "EAI 返回空";
                dataLog.setResponseData("");
            } else {
                boolean flag = true;
                for (ZSDS0002 zsds0002 : results) {
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
            logger.error("调用EAI接口 transReBackInfoFromEHAIERToGVS 失败：", e);
        }
        dataLog.setCreateTime(DateUtil.currentDateTime());
        recordEisInterfaceDataLog(dataLog);
        return rs;
    }

    private Result dealJinLi(LesStockTransQueue transQueue) {
        Result rs = new Result();

        com.haier.stock.eai.finance.postaccounttoehaiersap.ObjectFactory objectFactory = new com.haier.stock.eai.finance.postaccounttoehaiersap.ObjectFactory();
        ZMMS0102 request = objectFactory.createZMMS0102();
        request.setZSPNB(
            transQueue.getCorderSn() == null ? "" : transQueue.getCorderSn().substring(0, 15));//出库单号
        //LES回传的是空，推送SAP接口中行项目字段：ZSPIT 默认值为：01 推送        
        String no = StringUtil.isEmpty(transQueue.getReserve1()) ? "01" : transQueue.getReserve1();
        request.setZSPIT(no);
        request.setZLSGI(transQueue.getOutping());
        request.setZSPDT(DateUtil.format(transQueue.getBillTime(), "yyyy-MM-dd"));
        request.setBUKRS("0480");//公司代码

        String sku = transQueue.getSku();
        request.setMATNR(sku);

        request.setMENGE(new BigDecimal(transQueue.getQuantity()));
        request.setLGORT(transQueue.getSecCode());
        request.setZFGLG(transQueue.getCharg());//批次

        List<ZMMS0102> tZMMS0102 = new ArrayList<ZMMS0102>();
        tZMMS0102.add(request);

        // 要记录接口数据日志
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(transQueue.getCorderSn());
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(tZMMS0102));
        Long startTime = System.currentTimeMillis();

        PostAccountToEhaierSAP soap = new PostAccountToEhaierSAP_Service(getWSDLURLForQuery())
            .getPostAccountToEhaierSAPSOAP();

        try {
            List<ZMMS0108> results = soap.postAccountToEhaierSAP(tZMMS0102);

            String msg = JsonUtil.toJson(results);
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData(msg);

            if (results == null || results.size() <= 0) {
                rs.status = EisInterfaceFinance.STATUS_FAILED;
                rs.message = "EAI 返回空";
                dataLog.setResponseData("");
            } else {
                boolean flag = true;
                for (ZMMS0108 result : results) {
                    if (!flag)
                        break;
                    flag = !"E".equalsIgnoreCase(result.getZTYPE());
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
            logger.error("调用EAI接口 PostAccountToEhaierSAP 失败：", e);
        }
        dataLog.setCreateTime(DateUtil.currentDateTime());
        dataLog.setInterfaceCode("PostAccountToEhaierSAP");
        recordEisInterfaceDataLog(dataLog);
        return rs;
    }

    private Result dealTongShuai(LesStockTransQueue transQueue) {
        Result rs = new Result();

        com.haier.stock.eai.finance.postaccounttoehaiersap.ObjectFactory objectFactory = new com.haier.stock.eai.finance.postaccounttoehaiersap.ObjectFactory();
        ZMMS0102 request = objectFactory.createZMMS0102();
        request.setZSPNB(
            transQueue.getCorderSn() == null ? "" : transQueue.getCorderSn().substring(0, 15));//出库单号
        String no = StringUtil.isEmpty(transQueue.getReserve1()) ? ""
            : transQueue.getReserve1().substring(1, 3);
        request.setZSPIT(no);
        request.setZLSGI(transQueue.getOutping());
        request.setZSPDT(DateUtil.format(transQueue.getBillTime(), "yyyy-MM-dd"));
        request.setBUKRS("5000");//公司代码

        String sku = transQueue.getSku();
        request.setMATNR(sku);

        request.setMENGE(new BigDecimal(transQueue.getQuantity()));
        request.setLGORT(transQueue.getSecCode());
        request.setZFGLG(transQueue.getCharg());//批次

        List<ZMMS0102> tZMMS0102 = new ArrayList<ZMMS0102>();
        tZMMS0102.add(request);

        // 要记录接口数据日志
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(transQueue.getCorderSn());
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(tZMMS0102));
        Long startTime = System.currentTimeMillis();

        PostAccountToEhaierSAP soap = new PostAccountToEhaierSAP_Service(getWSDLURLForQuery())
            .getPostAccountToEhaierSAPSOAP();

        try {
            List<ZMMS0108> results = soap.postAccountToEhaierSAP(tZMMS0102);

            String msg = JsonUtil.toJson(results);
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData(msg);

            if (results == null || results.size() <= 0) {
                rs.status = EisInterfaceFinance.STATUS_FAILED;
                rs.message = "EAI 返回空";
                dataLog.setResponseData("");
            } else {
                boolean flag = true;
                for (ZMMS0108 result : results) {
                    if (!flag)
                        break;
                    flag = !"E".equalsIgnoreCase(result.getZTYPE());
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
            logger.error("调用EAI接口 PostAccountToEhaierSAP 失败：", e);
        }
        dataLog.setCreateTime(DateUtil.currentDateTime());
        dataLog.setInterfaceCode("PostAccountToEhaierSAP");
        recordEisInterfaceDataLog(dataLog);
        return rs;
    }

    @Override
    protected Result getInterfaceStatus(LesStockTransQueue transQueue) {
        throw new BusinessException("不可预估的错误，此接口不支持状态查询");
    }

//    public static void main(String[] args) {
//        String cOrderSn = "8527568302DsdsdsdfA";
//        System.out.println(cOrderSn.replaceAll("D.*", ""));//客户采购订单编号（DN));
//        if (cOrderSn.matches("8.+(D.*)?")) {//85开头 D*结尾的
//            System.out.println("a");
//        } else if (cOrderSn.matches("(SI).+")) {//SO或者SI开头//DBM 出库单号
//            System.out.println("b");
//        } else {
//            System.out.println("cOrderSn无法匹配DN或DBM规则");
//        }
//
//    }

}
