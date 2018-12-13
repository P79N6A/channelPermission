package com.haier.stock.services.finance;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.EisInterfaceFinance;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.purchase.data.model.GoodsBackInfoResponse;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrdersNew;
import com.haier.stock.eai.finance.rebacktocvs.ObjectFactory;
import com.haier.stock.eai.finance.rebacktocvs.TransReBackInfoFromEHAIERToGVS;
import com.haier.stock.eai.finance.rebacktocvs.TransReBackInfoFromEHAIERToGVS_Service;
import com.haier.stock.eai.finance.rebacktocvs.ZMMS0008;
import com.haier.stock.eai.finance.rebacktocvs.ZSDS0002;
import com.haier.stock.eai.finance.transcancelorderinfofromehaiersap.TransCancelOrderInfoFromEhaierSAP;
import com.haier.stock.eai.finance.transcancelorderinfofromehaiersap.TransCancelOrderInfoFromEhaierSAP_Service;
import com.haier.stock.eai.finance.transcancelorderinfofromehaiersap.ZMMS0104;
import com.haier.stock.eai.finance.transcancelorderinfofromehaiersap.ZMMS0110;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.services.VomOrderServiceImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.ws.Holder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 日日顺&&统帅正品退货
 *
 * @Filename: TransReBackInfoFromEHAIERToGVSHandler.java
 * @Version: 1.0
 * @Author: yanzhao
 * @Email: yan01250428@126.com
 */
@Service
public class TransReBackInfoFromEHAIERToGVSHandler extends SFHandler {

    private Logger              logger                 = LogManager
                                                           .getLogger(TransReBackInfoFromEHAIERToGVSHandler.class);

    private final static String SOURCE_GENUINE_RETURNS = "ZPTH";
    private String              sourceOrderSn          = null;
    private Integer             processId;
    @Autowired
    private VomOrderServiceImpl vomOrderService;

    @Autowired
    public void setInterfaceCode(){
        super.interfaceCode = "TransReBackInfoFromEHAIERToGVS";
    }
    @Autowired
    public void setWsdlFile(){
        super.wsdlFile = "TransReBackInfoFromEHAIERToGVS.wsdl";
    }
    @Autowired
    public void setWsdlFileForQuery(){
        super.wsdlFileForQuery = "TransCancelOrderInfoFromEhaierSAP.wsdl";
    }

    @Autowired
    public void setNextHandler(TransOrderInfoFromEhaierToGVSHandler nextHandler){
        super.nextHandler=nextHandler;//父类属性注入
    }

    /**
     * 获取正品退货外部订单号
     *
     * @return
     */
    private String getSourceOrderNo(String orderNo) {

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
            //throw new BusinessException("处理正品退货记录[" + orderNo + "]失败：" + result.getMessage());
        }
        GoodsBackInfoResponse goodsBackInfoResponse = result.getResult();
        if (goodsBackInfoResponse == null) {
            return null;
        }
        //返回so|ou号
        return goodsBackInfoResponse.getSiOuSlipNo();
    }

    /**
     * 类型为销售出库ZBCC且订单来源是正品退货ZPTH的
     *
     * @param transQueue
     * @return
     * @see SFHandler#isJob(com.haier.eis.model.LesStockTransQueue)
     */
    @Override
    protected boolean isJob(LesStockTransQueue transQueue) {

        processId = transQueue.getId();
        sourceOrderSn = null;

        String billType = transQueue.getBillType();

        String cbsBillNo = transQueue.getCorderSn();
        if (InventoryBusinessTypes.OUT_RETURNED_JD.getCode().equals(billType)) {//京东退货
            sourceOrderSn = transQueue.getCorderSn();
            return true;
        }

        if (!InventoryBusinessTypes.OUT_SALE.getCode().equals(billType)) {
            return false;
        }

        //判断是否是正品退货
        String sourceOrderNo = getSourceOrderNo(transQueue.getCorderSn());
        if (!StringUtil.isEmpty(sourceOrderNo)) {
            sourceOrderSn = sourceOrderNo;
            return true;
        }

        if (cbsBillNo.endsWith("J")) {//京东
            return false;
        } else {
            OrderProductsNew orderProducts = getOrderProducts(cbsBillNo);
            if (orderProducts == null)
                throw new BusinessException("网单号关联的网单已不存在，WD:" + cbsBillNo);
            OrdersNew order = getOrderByWD(orderProducts);
            if (order == null) {
                throw new BusinessException("网单号关联的订单已不存在,WD:" + cbsBillNo);
            }
            String source = order.getSource();
            if (SOURCE_GENUINE_RETURNS.equalsIgnoreCase(source)) {
                sourceOrderSn = order.getSourceOrderSn();
                return true;
            }
        }
        return false;
    }

    @Override
    protected Result process(LesStockTransQueue transQueue) {
        if (InventoryBusinessTypes.OUT_RETURNED_JD.getCode().equals(transQueue.getBillType())) {//京东退货
            sourceOrderSn = transQueue.getCorderSn();
            processId = transQueue.getId();
        } else {
            //判断是否是正品退货
            String sourceOrderNo = getSourceOrderNo(transQueue.getCorderSn());
            if (!StringUtil.isEmpty(sourceOrderNo)
                && InventoryBusinessTypes.OUT_SALE.getCode().equals(transQueue.getBillType())) {
                sourceOrderSn = sourceOrderNo;
                processId = transQueue.getId();
            }
        }
        Result rs;
        if (StringUtil.isEmpty(sourceOrderSn) || !transQueue.getId().equals(processId)) {
            String cbsBillNo = transQueue.getCorderSn();
            OrderProductsNew orderProducts = getOrderProducts(cbsBillNo);
            if (orderProducts == null) {
                rs = new Result();
                rs.setStatus(EisInterfaceFinance.STATUS_ERROR);
                rs.setMessage("网单号关联的网单已不存在，WD:" + cbsBillNo);
                return rs;
            }
            OrdersNew order = getOrderByWD(orderProducts);
            if (order == null) {
                rs = new Result();
                rs.setStatus(EisInterfaceFinance.STATUS_ERROR);
                rs.setMessage("网单号关联的订单已不存在,WD:" + cbsBillNo);
                return rs;
            }
            sourceOrderSn = order.getSourceOrderSn();
        }
        if (StringUtil.isEmpty(sourceOrderSn)) {
            rs = new Result();
            rs.setStatus(EisInterfaceFinance.STATUS_ERROR);
            rs.setMessage("来源订单为空");
            return rs;
        }
        if (sourceOrderSn.matches("4.+")) {
            return dealRRS(transQueue);
        } else if (sourceOrderSn.matches("OU.+") || sourceOrderSn.matches("ou.+")) {
            return dealTongShuai(transQueue);
        } else {
            rs = new Result();
            rs.setStatus(EisInterfaceFinance.STATUS_ERROR);
            rs.setMessage("来源订单规则无法识别,sourceOrderSn:" + sourceOrderSn);
            return rs;
        }
    }

    private Result dealRRS(LesStockTransQueue transQueue) {
        Result rs = new Result();
        ObjectFactory factory = new ObjectFactory();
        ZMMS0008 zmms0008 = factory.createZMMS0008();
        zmms0008.setZSPDT(DateUtil.format(transQueue.getBillTime(), "yyyy-MM-dd"));//订单出库日期
        zmms0008.setMATNR(transQueue.getSku());//物料编号
        zmms0008.setMENGE(new BigDecimal(transQueue.getQuantity()));//交货数量
        zmms0008.setLGORT(transQueue.getSecCode());//库位
        zmms0008.setZFGLG(transQueue.getCharg());//批次编号：10/90
        zmms0008.setZLSGI(transQueue.getOutping());//LES出库单号
        zmms0008.setLIFNR("01");// 供应商,测试供应商：V98715， V98668,正式：01
        zmms0008.setVBELN(sourceOrderSn);//  日日顺对电商的退货SO号
        zmms0008.setPOSNR(transQueue.getZeile());

        List<ZMMS0008> list = new ArrayList<ZMMS0008>();
        list.add(zmms0008);

        // 要记录接口数据日志
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(transQueue.getCorderSn());
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(list));
        Long startTime = System.currentTimeMillis();

        TransReBackInfoFromEHAIERToGVS soap = new TransReBackInfoFromEHAIERToGVS_Service(
            getWSDLURL()).getTransReBackInfoFromEHAIERToGVSSOAP();

        try {
            Holder<List<ZSDS0002>> tMSG = new Holder<List<ZSDS0002>>();
            Holder<Integer> exSUBRC = new Holder<Integer>();
            soap.transReBackInfoFromEHAIERToGVS(list, exSUBRC, tMSG);
            String message = JsonUtil.toJson(tMSG.value);

            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData(message);

            if (tMSG.value == null || tMSG.value.size() <= 0) {
                rs.setStatus(EisInterfaceFinance.STATUS_FAILED);
                rs.setMessage("EAI返回空");
                dataLog.setResponseData("");
            } else {
                List<ZSDS0002> results = tMSG.value;
                boolean flag = true;
                for (ZSDS0002 result : results) {
                    //                    if (!flag)
                    //                        break;
                    //                    flag = !"E".equalsIgnoreCase(result.getTYPE());

                    if (!"S".equalsIgnoreCase(result.getTYPE())) {
                        flag = false;
                        break;
                    }
                }

                rs.setStatus(flag ? EisInterfaceFinance.STATUS_SUCCESS
                    : EisInterfaceFinance.STATUS_FAILED);
                rs.setMessage(message);
            }
        } catch (Exception e) {
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData("");
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setErrorMessage(e.getMessage());
            rs.setStatus(EisInterfaceFinance.STATUS_FAILED);
            rs.setMessage("调用EAI接口失败");
            logger.error("调用EAI接口 transReBackInfoFromEHAIERToGVS 失败：", e);
        }
        dataLog.setCreateTime(DateUtil.currentDateTime());
        recordEisInterfaceDataLog(dataLog);
        return rs;
    }

    private Result dealTongShuai(LesStockTransQueue transQueue) {
        Result rs = new Result();

        com.haier.stock.eai.finance.transcancelorderinfofromehaiersap.ObjectFactory objectFactory = new com.haier.stock.eai.finance.transcancelorderinfofromehaiersap.ObjectFactory();
        ZMMS0104 request = objectFactory.createZMMS0104();
        request.setZOUNB(this.sourceOrderSn);//OU号

        String no = StringUtil.isEmpty(transQueue.getReserve1()) ? "001" : transQueue.getReserve1()
            .substring(1, 3);

        request.setZOUIT(no);//行项目号
        request.setZSPDT(DateUtil.format(transQueue.getBillTime(), "yyyy-MM-dd"));//出库日期
        request.setBUKRS("5000");//公司代码

        String sku = transQueue.getSku();
        request.setMATNR(sku);

        request.setMENGE(new BigDecimal(transQueue.getQuantity()));//数量
        request.setLGORT(transQueue.getSecCode());
        request.setZFGLG(transQueue.getCharg());//批次 10/90

        List<ZMMS0104> tZMMS0104 = new ArrayList<ZMMS0104>();
        tZMMS0104.add(request);

        // 要记录接口数据日志
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(transQueue.getCorderSn());
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(tZMMS0104));
        Long startTime = System.currentTimeMillis();

        TransCancelOrderInfoFromEhaierSAP soap = new TransCancelOrderInfoFromEhaierSAP_Service(
            getWSDLURLForQuery()).getTransCancelOrderInfoFromEhaierSAPSOAP();

        try {

            List<ZMMS0110> rets = soap.transCancelOrderInfoFromEhaierSAP(tZMMS0104);
            String msg = JsonUtil.toJson(rets);

            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData(msg);

            if (rets == null || rets.size() <= 0) {
                rs.setStatus(EisInterfaceFinance.STATUS_FAILED);
                rs.setMessage("EAI返回空");
                dataLog.setResponseData("");
            } else {
                boolean flag = true;
                for (ZMMS0110 result : rets) {
                    //                    if (!flag)
                    //                        break;
                    //                    flag = !"E".equalsIgnoreCase(result.getZTYPE());

                    if (!"S".equalsIgnoreCase(result.getZTYPE())) {
                        flag = false;
                        break;
                    }
                }

                rs.setStatus(flag ? EisInterfaceFinance.STATUS_SUCCESS
                    : EisInterfaceFinance.STATUS_FAILED);
                rs.setMessage(msg);
            }
        } catch (Exception e) {
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData("");
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setErrorMessage(e.getMessage());
            rs.setStatus(EisInterfaceFinance.STATUS_FAILED);
            rs.setMessage("调用EAI接口失败");
            logger.error("调用EAI接口 TransCancelOrderInfoFromEhaierSAP 失败：", e);
        }

        dataLog.setInterfaceCode("TransCancelOrderInfoFromEhaierSAP");
        dataLog.setCreateTime(DateUtil.currentDateTime());
        recordEisInterfaceDataLog(dataLog);

        return rs;
    }

    @Override
    protected Result getInterfaceStatus(LesStockTransQueue transQueue) {
        throw new BusinessException("不可预估的错误，此接口不支持状态查询");
    }

}
