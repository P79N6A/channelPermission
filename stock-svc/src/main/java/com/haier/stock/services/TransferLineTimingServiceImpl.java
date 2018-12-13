package com.haier.stock.services;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.dbconfig.model.Result;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.service.EisInterfaceDataLogService;
import com.haier.eis.service.LesStockTransQueueService;
import com.haier.purchase.data.model.Cn3wReplenishOrders;
import com.haier.purchase.data.model.CnReplenishEntryOrder;
import com.haier.purchase.data.model.CnReplenishEntryOrderItem;
import com.haier.purchase.data.service.Cn3wReplenishOrdersService;
import com.haier.purchase.data.service.CnReplenishEntryOrderItemService;
import com.haier.purchase.data.service.CnReplenishEntryOrderService;
import com.haier.shop.model.InvTransferLineSales;
import com.haier.shop.service.InvTransferLineSalesService;
import com.haier.stock.eai.QueryTransferFeeFromHBDMToLES.*;
import com.haier.stock.eai.finance.transfer.*;
import com.haier.stock.eai.finance.transfer.ObjectFactory;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.service.job.TransferLineTimingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.ws.Holder;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TransferLineTimingServiceImpl implements TransferLineTimingService {
    @Value("${wsdlPath}")
    private String wsdlLocation;
    private static Logger log = LoggerFactory.getLogger(TransferLineTimingServiceImpl.class);
    @Autowired
    private TransferLineModel<InvTransferLine> transferLineModel;
    @Autowired
    private LesStockTransQueueService lesStockTransQueueService;
    @Autowired
    private CnReplenishEntryOrderService cnReplenishEntryOrderService;
    @Autowired
    private EisInterfaceDataLogService eisInterfaceDataLogService;
    @Autowired
    private CnReplenishEntryOrderItemService cnReplenishEntryOrderItemService;
    @Autowired
    private Cn3wReplenishOrdersService cn3wReplenishOrdersService;
    @Autowired
    private InvTransferLineSalesService invTransferLineSalesService;

    public void setTransferLineModel(TransferLineModel<InvTransferLine> transferLineModel) {
        this.transferLineModel = transferLineModel;
    }
    @Override
    public ServiceResult<Boolean> cancelTransferLineToLesJob() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            transferLineModel.cancelTransferLineToLesJob();
            result.setSuccess(true);
            result.setResult(true);
        } catch (Exception e) {
            log.error("3W调拨取消同步取消物流定时任务发生未知异常：", e);
            result.setSuccess(false);
            result.setResult(false);
            result.setMessage("3W调拨取消同步取消物流定时任务发生异常");
        }
        return result;
    }



    @Override
    public ServiceResult<Boolean> updateStatusFromLES() {
        Map<String, Object> params = new HashMap<>();
        // 找出待出库、待入库调拨单，去查找是否有新的状态
        params.put("lineStatus", new Integer[] { 40, 50 });
        int pageIndex = 1;
        while (true) {
            List<Map<String, Object>> list = new ArrayList<>();
            // 一次取500，直到结束
            PagerInfo page = new PagerInfo(500, pageIndex++);
            ServiceResult<List<InvTransferLine>> sr = getTransferLines(params, page);
            if (sr.getSuccess() == false || sr.getResult().size() == 0) {
                break;
            }
            log.info("获得" + sr.getResult().size() + "条待更新状态调拨单。");
            for (InvTransferLine itl : sr.getResult()) {
                Map<String, Object> map = new HashMap<>();
                map.put("lineNum", itl.getLineNum());
                map.put("oldStatus", itl.getLineStatus());
                list.add(map);
            }
            // 从les_stock_trans_queue获取新的状态，返回值参见
            // com.haier.stock.model.InventoryBusinessTypes。
            // 我们需要的状态是OUT_TRANSFER，IN_TRANSFER
            List<Map<String, Object>> result = lesStockTransQueueService.getDbStatusUpdated(list);
            for (Map<String, Object> map : result) {
                Object newStatusObj = map.get("newStatus");
                if (newStatusObj != null) {
                    String lineNum = map.get("lineNum").toString();
                    String newStatusStr = newStatusObj.toString();
                    Date billTime = null;
                    try {
                        if (map.get("billTime") != null) {
                            billTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                    .parse(map.get("billTime").toString());
                        }
                    } catch (ParseException e) {
                        log.error("更新调拨单状态时间转换错误", e);
                    }
                    String remark = map.get("remark") == null ? null : map.get("remark").toString();
                    Integer newStatus = null;
                    InvTransferLine invTransferLine = getInvTransferLineByLineNum(lineNum).getResult();
                    if ("ZGI6".equals(newStatusStr)||"YTI1".equals(newStatusStr)) {
                        // 调拨出库，状态更新为待入库50
                        log.info("调拨单状态更新，单号：" + lineNum + "，状态：" + newStatus);
                        newStatus = 50;
                        transferLineModel.updateTransferLineAfterLesOut(invTransferLine, billTime, remark);
                    } else if ("ZGR6".equals(newStatusStr)) {
                        // 调拨入库，状态更新为已完成100
                        log.info("调拨单状态更新，单号：" + lineNum + "，状态：" + newStatus);
                        newStatus = 100;
                        transferLineModel.updateTransferLineAfterLesInput(invTransferLine, billTime, remark);
                    }

                }
            }
        }
        return null;
    }

    /**
     * 3w-3w调拨单传vom，定时任务
     *
     * @return
     */
    @Override
    public ServiceResult<Boolean> syncInnerTransfersToVom() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            boolean isSuc = transferLineModel.syncInnerTransfersToVom();
            result.setMessage("更新状态成功");
            result.setSuccess(isSuc);
        } catch (Exception e) {
            log.error("更新调拨单状态发生异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新调拨单状态发生异常");
        }
        return result;
    }



    @Override
    public ServiceResult<Boolean> queryTransferFeeFromHBDMToLES() {
        // 查询条件，待录费用调拨单
        Map<String, Object> params = new HashMap<String, Object>() {
            {
                put("lineStatus", Arrays.asList(new Integer[] { InvTransferLine.LINE_STATUS_FEE_INPUT }));
            }
        };
        URL url = this.getClass().getResource(wsdlLocation + "/QueryTransferFeeFromHBDMToLES.wsdl");
        ZWDCBSINT01NEWPT service = new ZWDCBSINT01NEWPTBindingQSService(url).getZWDCBSINT01NEWPTBindingQSPort();
        int pageIndex = 1;
        while (true) {
            PagerInfo page = new PagerInfo(100, pageIndex++);
            ServiceResult<List<InvTransferLine>> sr = getTransferLines(params, page);
            if (sr.getSuccess() == false || sr.getResult().size() == 0) {
                break;
            }
            log.info("获得" + sr.getResult().size() + "条待录入费用调拨单。");

            // 请求参数
            ZWDCBSINT01NEW.INPUT input = new ZWDCBSINT01NEW.INPUT();
            for (InvTransferLine il : sr.getResult()) {
                ZCBSDIAOBOIN z = new ZCBSDIAOBOIN();
                z.setBSTNK(il.getLineNum());
                input.getItem().add(z);
            }
            ZWDCBSINT01NEW z = new ZWDCBSINT01NEW();
            z.setINPUT(input);
            // 调用
            ZWDCBSINT01NEWRESPONSE resp = service.zwdCBSINT01NEW(z);
            String flag = resp.getFLAG();
            if (org.apache.commons.lang.StringUtils.isNotBlank(flag) && "S".equalsIgnoreCase(flag)) {
                // 成功
                log.info("调用接口获取调拨费用成功");
                ZWDCBSINT01NEWRESPONSE.OUTPUT output = resp.getOUTPUT();
                // 获取返回值
                List<ZSTRDIAOHUO> result = output.getItem();
                // 用于存放组装后的transferline，最后会保存
                List<InvTransferLine> transfers = new ArrayList<>();
                for (ZSTRDIAOHUO z2 : result) {
                    InvTransferLine transfer = new InvTransferLine();
                    Map<String, String> errMap = new HashMap<>();
                    // 检查错误并组装，有错误的话errMap会有值
                    checkParam(errMap, transfer, z2);
                    if (errMap.keySet().size() > 0) {
                        // 有错误
                    } else {
                        transfers.add(transfer);
                    }
                }

                ServiceResult<Boolean> saveResult = saveTransferFee(transfers);
                if (!saveResult.getSuccess()) {
                    String errBstnk = saveResult.getCode();
                    log.error("提单号为" + errBstnk + "的调拨单LES传CBS调货费用接口调用失败:" + saveResult.getMessage());
                } else {
                    log.info("商城保存成功");
                }
            } else {
                log.error("调用接口获取调拨费用失败：" + resp.getMESSAGE());
            }
        }
        return null;
    }

    private void checkParam(Map<String, String> map, InvTransferLine transfer, ZSTRDIAOHUO z) {
        // check bstnk
        String bstnk = z.getBSTNK();
        if (StringUtil.isEmpty(z.getBSTNK(), true)) {
            map.put(bstnk, "提单号不能为空");
            return;
        }
        transfer.setLineNum(z.getBSTNK().trim());

        // check price
        if (z.getPRICE() == null) {
            map.put(bstnk, "运费不能为空");
            return;
        }
        BigDecimal pri = z.getPRICE();
        if (pri.compareTo(BigDecimal.ZERO) == -1) {
            map.put(bstnk, "运费不能为负数");
            return;
        }
        if (pri.compareTo(new BigDecimal("99999999.99")) == 1) {
            map.put(bstnk, "运费数额过大");
            return;
        }
        pri = pri.setScale(2, RoundingMode.HALF_UP);
        transfer.setTransferFee(pri);

        BigDecimal haulCycle = null;
        try {
            haulCycle = new BigDecimal(z.getYSZQ().trim());
        } catch (Exception e) {

        }
        if (haulCycle == null){
            map.put(bstnk, "运输周期不能为空");
            return;
        }
        else
            transfer.setHaulCycle(haulCycle.intValue());

        // check name
        // if (StringUtil.isEmpty(name, true)) {
        // return "操作人姓名不能为空";
        // }
        // if (name.length() > 45) {
        // return "操作人姓名过长";
        // }
        // transfer.setTransferFeeUser(name.trim());
        if (StringUtil.isEmpty(z.getLRNAME())) {
            z.setLRNAME("物流");
        }
        if (z.getLRNAME().length() > 45) {
            map.put(bstnk, "操作人姓名过长");
            return;
        }
        transfer.setTransferFeeUser(z.getLRNAME());

        // check 操作日期 yyyyMMdd
        if (StringUtil.isEmpty(z.getERDATLR(), true)) {
            map.put(bstnk, "操作日期不能为空");
            return;
        }
        if (z.getERDATLR().length() != 10) {
            map.put(bstnk, "操作日期格式不正确");
            return;
        }
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(z.getERDATLR());
        } catch (Exception e) {
            map.put(bstnk, "操作日期格式不正确");
            return;
        }

        // check 操作时间 HHmmss
        if (StringUtil.isEmpty(z.getERZETLR(), true)) {
            map.put(bstnk, "操作时间不能为空");
            return;
        }
        if (z.getERZETLR().length() != 8) {
            map.put(bstnk, "操作时间格式不正确");
            return;
        }
        try {
            new SimpleDateFormat("HH:mm:ss").parse(z.getERZETLR());
        } catch (Exception e) {
            map.put(bstnk, "操作时间格式不正确");
            return;
        }
        try {
            transfer.setTransferFeeTime(
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(z.getERDATLR() + " " + z.getERZETLR()));
        } catch (Exception e) {
            map.put(bstnk, "操作日期或操作时间格式不正确");
            return;
        }
    }

    /**
     * LES传CBS调货费用接口
     *
     * @param transfers
     * @return
     */
    public ServiceResult<Boolean> saveTransferFee(List<InvTransferLine> transfers) {
        ServiceResult<Boolean> result;
        try {
            result = transferLineModel.saveTransferFee(transfers);
        } catch (Exception e) {
            log.error("LES传CBS调货费用时发生未知异常：" + e);
            result = new ServiceResult<Boolean>();
            result.setResult(false);
            result.setError("", "LES传CBS调货费用时发生未知异常");
        }
        return result;
    }


    @Override
    public ServiceResult<Boolean> orderIn3WPushToSAP() {
        try {
            //获取订单将状态根据LBX更新为已完成
            updateInvTransferLine();

            Integer pageIndex = 1;

            while (true) {
                // 获得已入库调拨单。
                // 一次取100条，完成为止。条件写在cnReplenishEntryOrderDao.getToPushSAPOrder。
                PagerInfo pi = new PagerInfo(100, pageIndex++);
                List<CnReplenishEntryOrder> list = cnReplenishEntryOrderService.getToPushSAPOrders(pi);
                if (list == null || list.isEmpty()) {
                    break;
                }
                log.info("获得" + list.size() + "条已入库待推送SAP调拨单。");
                for (CnReplenishEntryOrder order : list) {
                    InvTransferLine itl = null;
                    // 先推出库再推入库。
                    // 查询出库数据
                    ServiceResult<InvTransferLine> sr = getInvTransferLineBySoLineNum(order.getStoreOrderCode());
                    if (sr.getResult()!=null) {
                        itl = sr.getResult();
                    } else {
                        log.error("通过单号【" + order.getStoreOrderCode() + "】未查询到相关订单：");
                        continue;
                    }

                    // 从les_stock_trans_queue表获得出库信息。有可能一个单号获得多条数据，套机。
                    List<LesStockTransQueue> result = lesStockTransQueueService
                            .getLesStockTransQueueByCorderSnBillType(itl.getLineNum());

                    // 从cn_replenish_entry_order_item表获得入库信息。
                    List<CnReplenishEntryOrderItem> itemList = cnReplenishEntryOrderItemService
                            .getByReplEntryOrderId(order.getId());
                    // 理想情况下两个结果条数一致
                    if (result != null && !result.isEmpty() && itemList != null && !itemList.isEmpty()
                            && result.size() == itemList.size()) {

                    } else {
                        log.error("单号【" + order.getOrderCode()
                                + "】在les_stock_trans_queue表的出库信息和cn_replenish_entry_order_item表的入库信息条数不一致。");
                        continue;
                    }

                    Cn3wReplenishOrders cn3wReplenishOrders = cn3wReplenishOrdersService.getByLBX(order.getStoreOrderCode());
                    for (int i = 0; i < itemList.size(); i++) {
                        CnReplenishEntryOrderItem item = itemList.get(i);
                        //出库入库都已推送成功
                        if (item.getOutSap() == 5&&item.getInSap() == 5) {
                            // 出库已推送成功
                        }
                        //只出库未入库
                        else if(item.getOutSap() == 5&&item.getInSap() != 5){
                            for (LesStockTransQueue lstq : result) {
                                if (item.getItemCode().equals(lstq.getSku())) {
                                    //判断出入库数量是否一致
                                    int inCount = item.getInCount() == null ? 0 : item.getInCount().intValue();
                                    int remnantNum = item.getRemnantNum() == null ? 0 : item.getRemnantNum().intValue();
                                    int qty = lstq.getQuantity() == null ? 0 : lstq.getQuantity().intValue();
                                    if (inCount + remnantNum != qty) {
                                        log.info("单号【" + order.getOrderCode() + "】出入库信息不一致，暂不推送SAP。");
                                        continue;
                                    }
                                    String itemNum = item.getItemNum();
                                    if (StringUtils.isBlank(itemNum)) {
                                        itemNum = cnReplenishEntryOrderService.genItemNum(i);
                                        item.setItemNum(itemNum);
                                        lstq.setZeile(itemNum);
                                    } else{
                                        lstq.setZeile(itemNum);
                                    }

                                    //入库推sap，入库传ture
                                    Result inSap = newPushToSap(itl, lstq, true);
                                    if (inSap.getStatus() == true) {
                                        // 推送成功
                                        item.setInSap(5);
                                        item.setInSapTime(new Date());
                                        item.setSapMsg(inSap.getMessage());
                                    } else {
                                        item.setInSap(3);
                                        item.setSapMsg(inSap.getMessage());
                                    }
                                    cnReplenishEntryOrderItemService.updateStatusAfterInPushToSAP(item);
                                }
                            }
                        }
                        //即未出库，也未入库，先出库，再入库。
                        else {
                            for (LesStockTransQueue lstq : result) {
                                if (item.getItemCode().equals(lstq.getSku())) {
                                    int inCount = item.getInCount() == null ? 0 : item.getInCount().intValue();
                                    int remnantNum = item.getRemnantNum() == null ? 0 : item.getRemnantNum().intValue();
                                    int qty = lstq.getQuantity() == null ? 0 : lstq.getQuantity().intValue();
                                    if (inCount + remnantNum != qty) {
                                        log.info("单号【" + order.getOrderCode() + "】出入库信息不一致，暂不推送SAP。");
                                        continue;
                                    }

                                    String itemNum = item.getItemNum();
                                    if (StringUtils.isBlank(itemNum)) {
                                        itemNum = cnReplenishEntryOrderService.genItemNum(i);
                                        item.setItemNum(itemNum);
                                        lstq.setZeile(itemNum);
                                    }else{
                                        lstq.setZeile(itemNum);
                                    }

                                    // 出库推sap,出库传false
                                    Result or = newPushToSap(itl, lstq, false);
                                    if (or.getStatus() == true) {
                                        // 推送成功，更新cn_replenish_entry_order_item表状态
                                        item.setOutSap(5);
                                        item.setOutSapTime(new Date());
                                        item.setOutSapMsg(or.getMessage());
                                        cnReplenishEntryOrderItemService.updateStatusAfterOutPushToSAP(item);

                                        if (item.getInSap() == 5) {
                                            // 入库已经推送成功
                                        } else {
                                            //入库推sap，入库传ture
                                            Result inSap =newPushToSap(itl, lstq,true);
                                            if (inSap.getStatus() == true) {
                                                // 推送成功
                                                item.setInSap(5);
                                                item.setInSapTime(new Date());
                                                item.setSapMsg(inSap.getMessage());
                                            } else {
                                                item.setInSap(3);
                                                item.setSapMsg(inSap.getMessage());
                                            }
                                            cnReplenishEntryOrderItemService.updateStatusAfterInPushToSAP(item);
                                        }

                                    } else {
                                        item.setOutSap(3);
                                        item.setOutSapMsg(or.getMessage());
                                        cnReplenishEntryOrderItemService.updateStatusAfterOutPushToSAP(item);
                                    }
                                }
                            }
                        }

                    }
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
    /*
            标志位：isOutStack，ture是入库，false是出库
         */
    private Result newPushToSap(InvTransferLine invTransferLine, LesStockTransQueue lesStockTransQueue,
                                boolean isOutStack) {
        Result rs = new Result();

        ObjectFactory objectFactory = new ObjectFactory();
        ZMMS0010 request = objectFactory.createZMMS0010();
        request.setZCBSN(lesStockTransQueue.getCorderSn());
        String outping = lesStockTransQueue.getOutping();
        String soLineNo = invTransferLine.getSoLineNum().substring(4);

        String zeile = lesStockTransQueue.getZeile();
        String seqNum = "";
        if (StringUtil.isEmpty(zeile) || zeile.length() <= 3)
            seqNum = zeile;
        else
            seqNum = zeile.substring(zeile.length() - 3, zeile.length());
        //入库
        if (isOutStack) {
            request.setZLGORTO("");// 发货库位
            request.setZLSOT("");// les出库单号
            request.setZLSIN(soLineNo);// les入库单号
            request.setZLSII(seqNum);
            request.setZLSOI("");
        } else{
            // 其它渠道直接调拨3W库，库位需要转化
            String secFrom = invTransferLine.getSecFrom();
            if (invTransferLine.getTransferReason().intValue() == InvTransferLine.TRANSFER_REASON_3W.intValue()
                    && !secFrom.endsWith("WA")) {
                secFrom = secFrom.substring(0, 2) + "WA";
            }
            request.setZLGORTO(secFrom);// 发货库位
            request.setZLSOT(outping);
            request.setZLSIN("");
            request.setZLSOI(seqNum);
            request.setZLSII("");
        }
        String date = DateUtil.format(lesStockTransQueue.getBillTime(), "yyyy-MM-dd");
        request.setBLDAT(date);// 凭证日期
        request.setBUDAT(date);// 过账日期

        String sku = lesStockTransQueue.getSku();

        request.setMATNR(sku);
        request.setMENGE(new BigDecimal(lesStockTransQueue.getQuantity()));

        request.setZLGORTI(invTransferLine.getSecTo());// 接收库位

        TransferGoodsInfoToEhaierSAP soap = new TransferGoodsInfoToEhaierSAP_Service(
                this.getClass().getResource(wsdlLocation + "/TransferGoodsInfoToEhaierSAP.WSDL"))
                .getTransferGoodsInfoToEhaierSAPSOAP();

        List<ZMMS0010> tZMMS0010 = new ArrayList<ZMMS0010>();
        tZMMS0010.add(request);
        // 要记录接口数据日志
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(lesStockTransQueue.getCorderSn());
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(tZMMS0010));
        String inMsg ="login"+JsonUtil.toJson(tZMMS0010);
        Long startTime = System.currentTimeMillis();
        try {
            Holder<List<ZSDS0002>> tMSG = new Holder<List<ZSDS0002>>();
            Holder<Integer> exSUBRC = new Holder<Integer>();
            soap.transferGoodsInfoToEhaierSAP(tZMMS0010, exSUBRC, tMSG);
            String outmsg = "logout"+JsonUtil.toJson(tMSG.value);
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData(outmsg);
            if (tMSG.value == null || tMSG.value.size() <= 0) {
                rs.setStatus(false);
                rs.setMessage("EAI 返回空");
                dataLog.setResponseData("");
            } else {
                boolean flag = true;
                for (ZSDS0002 zsds0002 : tMSG.value) {
                    if (!flag)
                        break;
                    flag = !"E".equalsIgnoreCase(zsds0002.getTYPE());
                }
                if (flag) {
                    // 更新状态为WA已推送SAP
                    updateLineStatusAndQtyByLineNum(invTransferLine.getLineNum(), InvTransferLine.LINE_STATUS_3W_TO_SAP,
                            lesStockTransQueue.getQuantity());
                }
                rs.setStatus(flag);
                rs.setMessage(inMsg+outmsg);
            }
        } catch (Exception e) {
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData("");
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setErrorMessage(e.getMessage());
            rs.setStatus(false);
            rs.setMessage("调用EAI接口失败");
            log.error("调用EAI接口 TransferGoodsInfoToEhaierSAP 失败：", e);
        }
        dataLog.setCreateTime(DateUtil.currentDateTime());
        recordEisInterfaceDataLog(dataLog);
        return rs;
    }
    /**
     * 根据销售网单号取得调拨网单
     *
     * @param soLineNum
     * @return
     */

    public ServiceResult<InvTransferLine> getInvTransferLineBySoLineNum(String soLineNum) {
        ServiceResult<InvTransferLine> result = new ServiceResult<InvTransferLine>();
        try {
            InvTransferLine line = transferLineModel.getInvTransferLineBySoLineNum(soLineNum);
            result.setResult(line);
            result.setSuccess(true);
        } catch (Exception e) {
            log.error("根据销售网单号取得调拨网单时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据销售网单号取得调拨网单时发生未知异常");
        }
        return result;
    }
    //优品
    @Override
    public ServiceResult<Boolean> sysorderInYPupdate() {
        Map<String, Object> params = new HashMap<>();
        params.put("lineStatus", new Integer[] { 50 });
        params.put("transferReason",5);
        // 一次取500，直到结束
        int pageIndex=1;
        while (true){
            List<Map<String, Object>> list = new ArrayList<>();
            PagerInfo page = new PagerInfo(500, pageIndex++);
            ServiceResult<List<InvTransferLine>> sr = getTransferLines(params, page);
            if(sr.getSuccess() == false ||sr.getResult().size()==0){
                break;
            }
            for (InvTransferLine itl : sr.getResult()) {
                Map<String, Object> map = new HashMap<>();
                map.put("soLineNum", itl.getSoLineNum());
                list.add(map);
            }
            List<CnReplenishEntryOrder> cnReplenishEntryOrderList = cnReplenishEntryOrderService.getEditStatusSAPOrders(list);
            for (CnReplenishEntryOrder it2 : cnReplenishEntryOrderList) {
                ServiceResult<InvTransferLine> invTransferLine = getInvTransferLineBySoLineNum(it2.getStoreOrderCode());
                transferLineModel.updateTransferLineToSap(invTransferLine.getResult());
                InvTransferLineSales invTransferLineSales=new InvTransferLineSales();
                invTransferLineSales.setCreateTime(new Date());
                invTransferLineSales.setItemCode(invTransferLine.getResult().getItemCode());
                invTransferLineSales.setLineNum(invTransferLine.getResult().getLineNum());
                invTransferLineSales.setSalesAmounts(invTransferLine.getResult().getSalesAmount());
                invTransferLineSales.setSoLineNum(invTransferLine.getResult().getSoLineNum());
                invTransferLineSales.setSecTo(invTransferLine.getResult().getSecTo());
                invTransferLineSales.setTransferQty(invTransferLine.getResult().getQty());
                invTransferLineSalesService.insertSalse(invTransferLineSales);
            }
        }
        return null;
    }

    //根据inSap更新数据库状态，改为已完成
    private void updateInvTransferLine(){
        Map<String, Object> params = new HashMap<>();
        params.put("lineStatus", new Integer[] { 50 });
        // 一次取500，直到结束
        int pageIndex=1;
        while (true){
            List<Map<String, Object>> list = new ArrayList<>();
            PagerInfo page = new PagerInfo(500, pageIndex++);
            ServiceResult<List<InvTransferLine>> sr = getTransferLines(params, page);
            if(sr.getSuccess() == false ||sr.getResult().size()==0){
                break;
            }
            for (InvTransferLine itl : sr.getResult()) {
                Map<String, Object> map = new HashMap<>();
                map.put("soLineNum", itl.getSoLineNum());
                list.add(map);
            }
            List<CnReplenishEntryOrder> cnReplenishEntryOrderList = cnReplenishEntryOrderService.getEditStatusSAPOrders(list);
            for (CnReplenishEntryOrder it2 : cnReplenishEntryOrderList) {
                ServiceResult<InvTransferLine> invTransferLine = getInvTransferLineBySoLineNum(it2.getStoreOrderCode());
                transferLineModel.updateTransferLineToSap(invTransferLine.getResult());
            }

        }
    }


    @SuppressWarnings("serial")
    @Override
    public ServiceResult<Boolean> orderFinishedPushToSAP() {
        /**
         * 获取待推送sap的调拨单，状态100（已完成）
         */
        Map<String, Object> params = new HashMap<String, Object>() {
            {
                put("lineStatus", Arrays.asList(new Integer[] { InvTransferLine.LINE_STATUS_COMPLETE }));
                put("transferReason", InvTransferLine.TRANSFER_REASON_PP);
            }
        };
        int pageIndex = 1;
        while (true) {
            // 一次取100，直到结束
            PagerInfo page = new PagerInfo(100, pageIndex++);
            ServiceResult<List<InvTransferLine>> sr = getTransferLines(params, page);
            if (sr.getSuccess() == false || sr.getResult().size() == 0) {
                break;
            }
            log.info("获得" + sr.getResult().size() + "条待推送SAP调拨单。");
            for (InvTransferLine itl : sr.getResult()) {
                // 从les_stock_trans_queue获取调拨单的出入库时间，正常应该返回两条数据，出库和入库
                List<LesStockTransQueue> result = lesStockTransQueueService
                        .getLesStockTransQueueByCorderSn(itl.getLineNum());
                if (result != null && result.size() > 0) {
                    for (LesStockTransQueue lstq : result) {
                        pushToSap(itl, lstq,
                                InventoryBusinessTypes.OUT_TRANSFER.getCode().equalsIgnoreCase(lstq.getBillType()));
                    }
                } else {

                }
            }
        }
        return null;
    }


    private Result pushToSap(InvTransferLine invTransferLine, LesStockTransQueue lesStockTransQueue,
                             boolean isOutStack) {
        Result rs = new Result();

        ObjectFactory objectFactory = new ObjectFactory();
        ZMMS0010 request = objectFactory.createZMMS0010();
        request.setZCBSN(lesStockTransQueue.getCorderSn());
        String outping = lesStockTransQueue.getOutping();

        String zeile = lesStockTransQueue.getZeile();
        String seqNum = "";
        if (StringUtil.isEmpty(zeile) || zeile.length() <= 3)
            seqNum = zeile;
        else
            seqNum = zeile.substring(zeile.length() - 3, zeile.length());

        if (InventoryBusinessTypes.IN_TRANSFER.getCode().equalsIgnoreCase(lesStockTransQueue.getBillType())) {
            request.setZLGORTO("");// 发货库位
            request.setZLSOT("");// les出库单号
            request.setZLSIN(outping);// les入库单号
            request.setZLSII(seqNum);
            request.setZLSOI("");
        } else if (InventoryBusinessTypes.OUT_TRANSFER.getCode().equalsIgnoreCase(lesStockTransQueue.getBillType())) {
            // 其它渠道直接调拨3W库，库位需要转化
            String secFrom = invTransferLine.getSecFrom();
            if (invTransferLine.getTransferReason().intValue() == InvTransferLine.TRANSFER_REASON_3W.intValue()
                    && !secFrom.endsWith("WA")) {
                secFrom = secFrom.substring(0, 2) + "WA";
            }
            request.setZLGORTO(secFrom);// 发货库位
            request.setZLSOT(outping);
            request.setZLSIN("");
            request.setZLSOI(seqNum);
            request.setZLSII("");
        }
        String date = DateUtil.format(lesStockTransQueue.getBillTime(), "yyyy-MM-dd");
        request.setBLDAT(date);// 凭证日期
        request.setBUDAT(date);// 过账日期

        String sku = lesStockTransQueue.getSku();

        request.setMATNR(sku);
        request.setMENGE(new BigDecimal(lesStockTransQueue.getQuantity()));

        request.setZLGORTI(invTransferLine.getSecTo());// 接收库位

        TransferGoodsInfoToEhaierSAP soap = new TransferGoodsInfoToEhaierSAP_Service(
                this.getClass().getResource(wsdlLocation + "/TransferGoodsInfoToEhaierSAP.WSDL"))
                .getTransferGoodsInfoToEhaierSAPSOAP();

        List<ZMMS0010> tZMMS0010 = new ArrayList<ZMMS0010>();
        tZMMS0010.add(request);
        // 要记录接口数据日志
        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
        dataLog.setForeignKey(lesStockTransQueue.getCorderSn());
        dataLog.setRequestTime(DateUtil.currentDateTime());
        dataLog.setRequestData(JsonUtil.toJson(tZMMS0010));
        String inMsg ="login"+JsonUtil.toJson(tZMMS0010);
        Long startTime = System.currentTimeMillis();
        try {
            Holder<List<ZSDS0002>> tMSG = new Holder<List<ZSDS0002>>();
            Holder<Integer> exSUBRC = new Holder<Integer>();
            soap.transferGoodsInfoToEhaierSAP(tZMMS0010, exSUBRC, tMSG);
            String outmsg = "logout"+JsonUtil.toJson(tMSG.value);
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData(outmsg);
            if (tMSG.value == null || tMSG.value.size() <= 0) {
                rs.setStatus(false);
                rs.setMessage("EAI 返回空");
                dataLog.setResponseData("");
            } else {
                boolean flag = true;
                for (ZSDS0002 zsds0002 : tMSG.value) {
                    if (!flag)
                        break;
                    flag = !"E".equalsIgnoreCase(zsds0002.getTYPE());
                }
                if (flag) {
                    // 更新状态为WA已推送SAP
                    updateLineStatusAndQtyByLineNum(invTransferLine.getLineNum(), InvTransferLine.LINE_STATUS_WA_TO_SAP,
                            lesStockTransQueue.getQuantity());
                }
                rs.setStatus(flag);
                rs.setMessage(inMsg+outmsg);
            }
        } catch (Exception e) {
            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
            dataLog.setResponseTime(DateUtil.currentDateTime());
            dataLog.setResponseData("");
            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
            dataLog.setErrorMessage(e.getMessage());
            rs.setStatus(false);
            rs.setMessage("调用EAI接口失败");
            log.error("调用EAI接口 TransferGoodsInfoToEhaierSAP 失败：", e);
        }
        dataLog.setCreateTime(DateUtil.currentDateTime());
        recordEisInterfaceDataLog(dataLog);
        return rs;
    }

    private void recordEisInterfaceDataLog(EisInterfaceDataLog dataLog) {
        try {
            if (StringUtil.isEmpty(dataLog.getInterfaceCode())) {
                dataLog.setInterfaceCode("transfer_order_from_hbdm_to_sap");
            }
            eisInterfaceDataLogService.insert(dataLog);
        } catch (Exception e) {
            log.error("记录EisInterfaceDataLog出错：", e);
            log.error("EisInterfaceDataLog:" + JsonUtil.toJson(log));
        }
    }

    public ServiceResult<Boolean> updateLineStatusAndQtyByLineNum(String orderNo, int status, Integer qty) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();

        try {
            boolean isSuc = transferLineModel.updateLineStatusAndQtyByLineNum(orderNo, status, qty);
            result.setMessage("更新状态[" + status + "]成功");
            result.setSuccess(isSuc);
        } catch (Exception e) {
            log.error("更新调拨单状态发生异常：" + e);
            result.setSuccess(false);
            result.setMessage("更新调拨单状态发生异常");
        }
        return result;
    }
    /**
     * 根据调拨网单号码取得调拨网单
     *
     * @param lineNum
     * @return
     * @see com.haier
     */
    public ServiceResult<InvTransferLine> getInvTransferLineByLineNum(String lineNum) {
        ServiceResult<InvTransferLine> result = new ServiceResult<InvTransferLine>();
        try {
            InvTransferLine line = transferLineModel.getInvTransferLineByLineNum(lineNum);
            result.setResult(line);
            result.setSuccess(true);
        } catch (Throwable e) {
            log.error("根据调拨网单号码取得调拨网单时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("根据调拨网单号码取得调拨网单时发生未知异常");
        }
        return result;
    }

    /**
     * 取得货物调拨列表
     *
     * @param params
     * @param pagerInfo
     * @return
     */
    public ServiceResult<List<InvTransferLine>> getTransferLines(Map<String, Object> params, PagerInfo pagerInfo) {
        ServiceResult<List<InvTransferLine>> result = new ServiceResult<List<InvTransferLine>>();
        try {
            int count = transferLineModel.getTransferLinesCount(params);
            result.setResult(transferLineModel.getTransferLines(params, pagerInfo));
            pagerInfo.setRowsCount(count);
            result.setPager(pagerInfo);
        } catch (BusinessException e) {
            result.setSuccess(false);
            result.setMessage("无法获取货物调拨列表:" + e.getMessage());
        } catch (Exception e) {
            log.error("获取货物调拨列表时发生未知异常：" + e);
            result.setSuccess(false);
            result.setMessage("获取货物调拨列表时发生未知异常");
        }
        return result;
    }
}
