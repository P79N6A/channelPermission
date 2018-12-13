package com.haier.eop.services;

import com.haier.eop.data.model.TransferOrder;
import com.haier.eop.data.service.TransferOrder3wService;
import com.haier.eop.service.EopTransferOrder3wService;
import com.haier.eop.util.ArraySplitUtil;
import com.taobao.pac.sdk.cp.PacClient;
import com.taobao.pac.sdk.cp.SendSysParams;
import com.taobao.pac.sdk.cp.dataobject.request.TAOBAO_QIMEN_TRANSFERORDER_QUERY.TaobaoQimenTransferorderQueryRequest;
import com.taobao.pac.sdk.cp.dataobject.response.TAOBAO_QIMEN_TRANSFERORDER_QUERY.TaobaoQimenTransferorderQueryResponse;
import com.taobao.pac.sdk.cp.dataobject.response.TAOBAO_QIMEN_TRANSFERORDER_QUERY.TransferOrderDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 3W间调拨处理类
 */
@Service
public class EopTransferOrder3wServiceImpl implements EopTransferOrder3wService {

    private static final Logger logger = LoggerFactory.getLogger(EopTransferOrder3wServiceImpl.class);

    private static final String LINK_URL = "http://link.cainiao.com/gateway/link.do";
    private static final String LINK_APPKEY = "009658";
    private static final String LINK_SECRECTKEY = "Vi3z88ZEy5j9W28s4JA0O3X29r159S06";
    private static final String LINK_CPCODE = "2998123754";

    @Autowired
    private TransferOrder3wService transferOrder3wService;

    @Override
    public Map<String, Object> findOrderList(Map<String, Object> params) throws ParseException {
        Map<String, Object> retMap = transferOrder3wService.getReplOrdersByPage(params);
        return retMap;
    }

    @Override
    public void syncOrderFromCn(String transferOrderCode) {
        logger.info("开始从菜鸟同步调拨单信息，同步参数：" + transferOrderCode);
        PacClient pacClient = new PacClient(LINK_APPKEY, LINK_SECRECTKEY, LINK_URL);
        SendSysParams sysParams = new SendSysParams();
        sysParams.setFromCode(LINK_CPCODE);

        TaobaoQimenTransferorderQueryRequest linkRequest = new TaobaoQimenTransferorderQueryRequest();
        linkRequest.setTransferOrderCode(transferOrderCode);

        TaobaoQimenTransferorderQueryResponse linkResponse = new TaobaoQimenTransferorderQueryResponse();
        StringBuilder sb = new StringBuilder();
        try {
            linkResponse = pacClient.send(linkRequest, sysParams);
        } catch (Exception e) {
            sb.append("同步调拨单信息执行异常，异常原因：").append(e.getMessage());
            sb.append("，调拨参数：" + transferOrderCode);
            logger.error(sb.toString());
            return;
        } finally {
            logger.info("同步调拨单信息：" + linkResponse.toString());
        }
        if (linkResponse == null) {
            sb.append("同步调拨单信息执行异常，响应为空");
            sb.append("，调拨参数：" + transferOrderCode);
            logger.error(sb.toString());
            return;
        } else if (linkResponse.getFlag().equals("failure")) {
            sb.append("失败代码：").append(linkResponse.getCode()).append("；失败说明：").append(linkResponse.getMessage());
            sb.append("；调拨参数：" + transferOrderCode);
            logger.error(sb.toString());
            return;
        } else if (linkResponse.getFlag().equals("success")) {
            TransferOrderDetail detail = linkResponse.getTransferOrderDetail();
            if (!"ALLTRANSFEROUT".equals(detail.getOrderStatus()) && !"ALLTRANSFEROUTANDPARTIN".equals(detail.getOrderStatus())
                    && !"TRANSFERCONFIRMED".equals(detail.getOrderStatus())) {
                sb.append("同步调拨单信息，调拨单状态不符合条件，调用参数：").append(transferOrderCode);
                sb.append(",调拨单状态为：").append(detail.getOrderStatus());
                logger.warn(sb.toString());
                return;
            }
            if (null == detail.getItems() || detail.getItems().size() == 0) {
                logger.error("同步调拨单返回详细信息为空，调用参数：" + transferOrderCode);
                return;
            }
            List<TransferOrder> oldTransferOrders = transferOrder3wService.getByTransferOrderCode(transferOrderCode);
            if (null == oldTransferOrders || oldTransferOrders.size() == 0) {
                logger.error("同步调拨单信息失败，transferorder表中不存在相应调拨单，调用参数：" + transferOrderCode);
                return;
            }
            //记录报文
            int num = transferOrder3wService.getTransferOrderLogByName(transferOrderCode);
            if (num > 0) {
                transferOrder3wService.updateTransferLog(transferOrderCode, null,
                        null, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                        null, null, linkResponse.toString().replaceAll("[\r\n]", ""));
            } else {
                transferOrder3wService.insertTransferLog(transferOrderCode, null,
                        null, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                        null, null, linkResponse.toString().replaceAll("[\r\n]", ""));
            }

            transferOrder3wService.handleSyncUpdate(oldTransferOrders, detail, false);
        }
        return;
    }

    @Override
    public void syncOrdersFromCn() {
        logger.info("开始从菜鸟同步所有异常调拨单信息");
        //查询人工介入的调拨单数量
        Integer totalCount = transferOrder3wService.getManualOrderCount();
        if (null == totalCount || totalCount <= 0) {
            logger.info("没有需要同步的调拨单");
            return;
        }
        Integer pageSize = 30;
        //分页查询调拨单（去重），并向菜鸟请求查询数据
        int page = 0;
        if (totalCount % pageSize == 0) {
            page = totalCount / pageSize - 1;
        } else {
            page = totalCount / pageSize;
        }
        while (page >= 0) {
            try {
                //分页查询调拨单号
                List<String> transferOrderCodeList = transferOrder3wService.getManualOrderCodes(page--, pageSize);
                Set<String> codeSet = new HashSet<String>();
                for (String code : transferOrderCodeList) {
                    codeSet.add(code);
                }
                for (String transferOrderCode : codeSet) {
                    syncOrderFromCn(transferOrderCode);
                }
            } catch (Exception e) {
                logger.error("同步调拨单信息发生异常：" + e.getMessage());
            }
        }
        logger.info("从菜鸟同步所有异常调拨单信息执行结束");
    }

    @Override
    public String readdRecords(String[] totalArray) {
        List<String[]> list = new ArraySplitUtil<String>().splistArray(totalArray, 50);
        int totalCount = 0;
        Map<String, Object> tempMap = null;
        int existCount = 0;
        int insertCount = 0;
        List<String> errorCodes = new ArrayList<>();

        PacClient pacClient = new PacClient(LINK_APPKEY, LINK_SECRECTKEY, LINK_URL);
        SendSysParams sysParams = new SendSysParams();
        sysParams.setFromCode(LINK_CPCODE);

        TaobaoQimenTransferorderQueryRequest linkRequest = new TaobaoQimenTransferorderQueryRequest();
        TaobaoQimenTransferorderQueryResponse linkResponse = new TaobaoQimenTransferorderQueryResponse();

        for (String[] subArray : list) {
            tempMap = new HashMap<String, Object>();
            tempMap.put("orderCodes", subArray);
            boolean containCodes = false;
            List<String> existCodeList = transferOrder3wService.getExistTransferOrderCodes(tempMap);
            Set<String> existCodes = new HashSet<>();
            existCodes.addAll(existCodeList);
            if (null != existCodes && existCodes.size() != 0) { //去重
                containCodes = true;
            }
            for (String transferOrderCode : subArray) {
                try {

                    if (containCodes && existCodes.contains(transferOrderCode)) { //已经存在的调拨单，不再处理只做数量统计
                        existCount++;
                        continue;
                    }
                    //从菜鸟查询调拨单信息
                    linkRequest.setTransferOrderCode(transferOrderCode);

                    StringBuilder sb = new StringBuilder();
                    try {
                        linkResponse = pacClient.send(linkRequest, sysParams);
                    } catch (Exception e) {
                        sb.append("同步调拨单信息执行异常，异常原因：").append(e.getMessage());
                        sb.append("，调拨参数：" + transferOrderCode);
                        logger.error(sb.toString());
                        errorCodes.add(transferOrderCode);
                        continue;
                    } finally {
                        logger.info("同步调拨单信息：" + linkResponse.toString());
                    }
                    if (linkResponse == null) {
                        sb.append("同步调拨单信息执行异常，响应为空");
                        sb.append("，调拨参数：" + transferOrderCode);
                        errorCodes.add(transferOrderCode);
                        logger.error(sb.toString());
                        continue;
                    } else if (linkResponse.getFlag().equals("failure")) {
                        sb.append("失败代码：").append(linkResponse.getCode()).append("；失败说明：").append(linkResponse.getMessage());
                        sb.append("；调拨参数：" + transferOrderCode);
                        errorCodes.add(transferOrderCode);
                        logger.error(sb.toString());
                        continue;
                    } else if (linkResponse.getFlag().equals("success")) {
                        TransferOrderDetail detail = linkResponse.getTransferOrderDetail();
                        if (!"ALLTRANSFEROUT".equals(detail.getOrderStatus()) && !"ALLTRANSFEROUTANDPARTIN".equals(detail.getOrderStatus())
                                && !"TRANSFERCONFIRMED".equals(detail.getOrderStatus())) {
                            sb.append("同步调拨单信息，调拨单状态不符合条件，调用参数：").append(transferOrderCode);
                            sb.append(",调拨单状态为：").append(detail.getOrderStatus());
                            logger.warn(sb.toString());
                            errorCodes.add(transferOrderCode);
                            continue;
                        }
                        if (null == detail.getItems() || detail.getItems().size() == 0) {
                            logger.error("同步调拨单返回详细信息为空，调用参数：" + transferOrderCode);
                            errorCodes.add(transferOrderCode);
                            continue;
                        }
                        //记录报文
                        int num = transferOrder3wService.getTransferOrderLogByName(transferOrderCode);
                        if (num > 0) {
                            transferOrder3wService.updateTransferLog(transferOrderCode, null,
                                    null, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                                    null, null, linkResponse.toString().replaceAll("[\r\n]", ""));
                        } else {
                            transferOrder3wService.insertTransferLog(transferOrderCode, null,
                                    null, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                                    null, null, linkResponse.toString().replaceAll("[\r\n]", ""));
                        }
                        String createResult = transferOrder3wService.createTransferOrder(detail);
                        if (null == createResult) {
                            errorCodes.add(transferOrderCode);
                            logger.error("补录调拨单保存调拨信息时失败，调用参数：" + transferOrderCode);
                        } else if (createResult.equals("")) {
                            insertCount++;
                        } else {
                            logger.error(createResult + "，调用参数：" + transferOrderCode);
                            errorCodes.add(transferOrderCode);
                        }
                    }
                } catch (Exception e) {
                    errorCodes.add(transferOrderCode);
                    logger.error("补录调拨单发生异常，调用参数：" + transferOrderCode + "，异常信息：" + e.getMessage());
                }
            }
        }
        StringBuffer sb = new StringBuffer();
        if (errorCodes.size() != 0) {
            sb.append("共补录").append(insertCount).append("条记录，已存在").append(existCount);
            sb.append("条记录，补录失败").append(errorCodes.size()).append("条记录，失败单建议再执行一次：").append(errorCodes.toString());

        } else {
            sb.append("补录成功！共补录").append(insertCount).append("条记录，已存在").append(existCount).append("条记录。");
        }
        logger.info(sb.toString());
        return sb.toString();
    }

    @Override
    public List<Map<String, Object>> getExportTransferOrderOutList(Map<String, Object> paramMap) {
        List<Map<String, Object>> retMap = transferOrder3wService.getExportTransferOrderOutList(paramMap);
        return retMap;
    }
}
