package com.haier.stock.services.finance;

import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.EisInterfaceFinance;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.stock.eai.finance.transfer.ObjectFactory;
import com.haier.stock.eai.finance.transfer.TransferGoodsInfoToEhaierSAP;
import com.haier.stock.eai.finance.transfer.TransferGoodsInfoToEhaierSAP_Service;
import com.haier.stock.eai.finance.transfer.ZMMS0010;
import com.haier.stock.eai.finance.transfer.ZSDS0002;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.service.TransferLineService;
import com.haier.stock.services.TransferLineServiceImpl;
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
 * 调拨入库、调拨出库
 */
@Service
public class TransferGoodsInfoToEhaierSAPHandler extends SFHandler {
    private Logger              logger = LogManager
        .getLogger(TransferGoodsInfoToEhaierSAPHandler.class);
    @Autowired
    private TransferLineServiceImpl transferLineService;


    @Autowired
    public void setInterfaceCode(){
        super.interfaceCode = "TransferGoodsInfoToEhaierSAP";
    }
    @Autowired
    public void setWsdlFile(){
        super.wsdlFile = "TransferGoodsInfoToEhaierSAP.WSDL";
    }
    @Autowired
    public void setWsdlFileForQuery(){
        super.wsdlFileForQuery = "";
    }

    @Autowired
    public void setNextHandler(DefaultSFHandler nextHandler){
        super.nextHandler=nextHandler;//父类属性注入
    }

    @Override
    protected boolean isJob(LesStockTransQueue transQueue) {
        String billType = transQueue.getBillType();
        return InventoryBusinessTypes.IN_TRANSFER.getCode().equalsIgnoreCase(billType)
               || InventoryBusinessTypes.OUT_TRANSFER.getCode().equalsIgnoreCase(billType);
    }

    @Override
    protected Result process(LesStockTransQueue transQueue) {
        Result rs = new Result();

        InvTransferLine transferLine = getTransferLine(transQueue.getCorderSn());
        if (transferLine == null) {
            rs.status = EisInterfaceFinance.STATUS_ERROR;
            rs.message = "关联的调拨单不存在,单号：" + transQueue.getCorderSn();
            return rs;
        }
        //虚拟调拨不需传财务
        if (transferLine.getTransferReason() == InvTransferLine.TRANSFER_REASON_XN) {
            rs.status = EisInterfaceFinance.STATUS_SUCCESS;
            rs.message = "关联的调拨单，单号：" + transQueue.getCorderSn() + "，属于虚拟调拨";
            return rs;
        }
        ObjectFactory objectFactory = new ObjectFactory();
        ZMMS0010 request = objectFactory.createZMMS0010();
        request.setZCBSN(transQueue.getCorderSn());
        String outping = transQueue.getOutping();

        String zeile = transQueue.getZeile();
        String seqNum = "";
        if (StringUtil.isEmpty(zeile) || zeile.length() <= 3)
            seqNum = zeile;
        else
            seqNum = zeile.substring(zeile.length() - 3, zeile.length());

        if (InventoryBusinessTypes.IN_TRANSFER.getCode()
            .equalsIgnoreCase(transQueue.getBillType())) {
            request.setZLGORTO("");//发货库位
            request.setZLSOT("");//les出库单号
            request.setZLSIN(outping);//les入库单号
            request.setZLSII(seqNum);
            request.setZLSOI("");
        } else if (InventoryBusinessTypes.OUT_TRANSFER.getCode()
            .equalsIgnoreCase(transQueue.getBillType())) {
            //其它渠道直接调拨3W库，库位需要转化
            String secFrom = transferLine.getSecFrom();
            if (transferLine.getTransferReason().intValue() == InvTransferLine.TRANSFER_REASON_3W
                .intValue() && !secFrom.endsWith("WA")) {
                secFrom = secFrom.substring(0, 2) + "WA";
            }
            request.setZLGORTO(secFrom);//发货库位
            request.setZLSOT(outping);
            request.setZLSIN("");
            request.setZLSOI(seqNum);
            request.setZLSII("");
        }
        String date = DateUtil.format(transQueue.getBillTime(), "yyyy-MM-dd");
        request.setBLDAT(date);//凭证日期
        request.setBUDAT(date);//过账日期

        String sku = transQueue.getSku();

        request.setMATNR(sku);
        request.setMENGE(new BigDecimal(transQueue.getQuantity()));

        request.setZLGORTI(transferLine.getSecTo());//接收库位

        TransferGoodsInfoToEhaierSAP soap = new TransferGoodsInfoToEhaierSAP_Service(getWSDLURL())
            .getTransferGoodsInfoToEhaierSAPSOAP();

        List<ZMMS0010> tZMMS0010 = new ArrayList<ZMMS0010>();
        tZMMS0010.add(request);
        // 要记录接口数据日志
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(transQueue.getCorderSn());
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(tZMMS0010));
        Long startTime = System.currentTimeMillis();
        try {
            Holder<List<ZSDS0002>> tMSG = new Holder<List<ZSDS0002>>();
            Holder<Integer> exSUBRC = new Holder<Integer>();
            soap.transferGoodsInfoToEhaierSAP(tZMMS0010, exSUBRC, tMSG);
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

    @Override
    protected Result getInterfaceStatus(LesStockTransQueue transQueue) {
        throw new BusinessException("不可预估的错误，此接口不支持状态查询");
    }

    private InvTransferLine getTransferLine(String lineNum) {
        ServiceResult<InvTransferLine> rs = transferLineService
            .getInvTransferLineByLineNum(lineNum);
        if (!rs.getSuccess())
            throw new RuntimeException("通过调拨服务获取调拨单出错：" + rs.getMessage());
        return rs.getResult();
    }

}
