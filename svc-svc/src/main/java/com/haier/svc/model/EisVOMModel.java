package com.haier.svc.model;


import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.EisInterfaceStatus;
import com.haier.eis.model.VomReceivedQueue;
import com.haier.eis.model.VomShippingStatus;
import com.haier.eis.service.EisInterfaceStatusService;
import com.haier.eis.service.EisVomReceivedQueueService;
import com.haier.purchase.data.model.GoodsBackInfoResponse;
import com.haier.stock.service.VomOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model of EisVOMService
 * Created by 钊 on 2014/7/21.
 */
@Service("eisVOMModel")
public class EisVOMModel {
    public static final Logger LOGGER = LoggerFactory.getLogger(EisVOMModel.class);

    @Autowired
    private EisInterfaceStatusService eisInterfaceStatusService;
    @Autowired
    private EisVomReceivedQueueService eisVomReceivedQueueService;
    @Autowired
    private VomOrderService vomOrderService;
    /**
     * 处理正品退货拒单情况下验证失败的数据
     * 处理逻辑：将数据状态同步给采购系统，同步完成后修改vom_received_queue.status的值为3-已同步采购系统
     */
    public void processQualityGoods() {

        //查询上次执行后最大的id值
        EisInterfaceStatus eisInterfaceStatus = eisInterfaceStatusService
                .getByInterfaceCode(EisInterfaceStatus.NTERFACE_CODE_VOM_RECEIVED_QUEUE_ID);
        if (eisInterfaceStatus == null) {
            LOGGER.info("[processQualityGoods]没有vom_received_queue_id这个接口变量");
            return;
        }
        List<VomReceivedQueue> vomReceivedQueueList = eisVomReceivedQueueService
                .getByIdStatus(eisInterfaceStatus.getLastId(), VomReceivedQueue.STATUS_FAIL);
        if (vomReceivedQueueList == null || vomReceivedQueueList.isEmpty()) {
            LOGGER.info("[processQualityGoods]没有需要处理的数据");
            return;
        }

        try {
            Integer lastId = 0;
            for (VomReceivedQueue vomReceivedQueue : vomReceivedQueueList) {
                lastId = vomReceivedQueue.getId();//记录每次处理结束后的id，循环处理结束后的id为本次job运行处理的最大id
                if (VomReceivedQueue.STATUS_FAIL != vomReceivedQueue.getStatus().intValue()) {
                    LOGGER.info(
                            "[processQualityGoods]状态[" + vomReceivedQueue.getStatus() + "]，已不是处理失败的状态");
                    continue;
                }
                String type = vomReceivedQueue.getType();
                if (StringUtil.isEmpty(type) || !"xml".equalsIgnoreCase(type)) {
                    LOGGER.info("[processQualityGoods]暂时只支持xml格式的报文");
                    continue;
                }
                Document document = DocumentHelper.parseText(vomReceivedQueue.getContent());
                document.setXMLEncoding("utf-8");
                Element rootElement = document.getRootElement();
                VomShippingStatus vomShippingStatus = parseContent(rootElement);
                if (!"WMS_FAILED".equalsIgnoreCase(vomShippingStatus.getStatus())) {//不是拒单不处理
                    continue;
                }
                String orderNo = vomShippingStatus.getOrderNo();
                if (StringUtil.isEmpty(orderNo, true)) {
                    LOGGER.info("[processQualityGoods]单号为空");
                    continue;
                }
                //判断是否是正品退货单子
                Map<String, Object> queryParamMap = new HashMap<String, Object>();
                queryParamMap.put("orderNo", orderNo);
                ServiceResult<GoodsBackInfoResponse> queryResult = vomOrderService
                        .getGoodsBackInfo(queryParamMap);
                if (queryResult == null || !queryResult.getSuccess()) {//判断是否是正品退货单子，判断失败，停止本次处理，下一次运行时重新处理
                    throw new BusinessException(
                            "单号" + orderNo + "，查询正品退货信息失败：" + (queryResult == null ? ""
                                    : queryResult.getMessage()));
                }
                if (queryResult.getResult() == null) {//不是正品退货不处理
                    continue;
                }
                String errorMsg = vomShippingStatus.getContent();
                if (!StringUtil.isEmpty(vomReceivedQueue.getErrorMessage(), true)) {
                    errorMsg = errorMsg + "|" + vomReceivedQueue.getErrorMessage();
                }
                Map<String, Object> updateParamMap = new HashMap<String, Object>();
                updateParamMap.put("orderNo", orderNo);
                updateParamMap.put("status", "-120");//-120-VOM拒单
                updateParamMap.put("errorMsg", errorMsg);

                ServiceResult<Boolean> result = vomOrderService.synVomStatus(updateParamMap);
                if (result == null || !result.getSuccess()) {//处理失败，停止本次处理，下一次运行时重新处理
                    throw new BusinessException("单号" + orderNo + "，处理正品退货拒单失败："
                            + (result == null ? "" : result.getMessage()));
                }
                eisVomReceivedQueueService.updateStatusById(lastId, VomReceivedQueue.STATUS_SYNCH);
            }

            eisInterfaceStatus.setLastId(lastId);
            eisInterfaceStatus.setLastTime(new Date());
            eisInterfaceStatus.setUpdateTime(new Date());
            eisInterfaceStatusService.update(eisInterfaceStatus);

        } catch (Exception e) {
            LOGGER.error("[processQualityGoods]处理正品退货拒单情况出现异常", e);
        }

    }


    private VomShippingStatus parseContent(Element rootElement) {
        VomShippingStatus vomShippingStatus = new VomShippingStatus();
        vomShippingStatus.setStoreCode(rootElement.elementTextTrim("storecode"));
        vomShippingStatus.setOrderNo(rootElement.elementTextTrim("orderno"));
        vomShippingStatus.setExpNo(rootElement.elementTextTrim("expno"));
        vomShippingStatus.setOperator(rootElement.elementTextTrim("operator"));
        vomShippingStatus.setOperContact(rootElement.elementTextTrim("opercontact"));
        vomShippingStatus.setOperDate(
                DateUtil.parse(rootElement.elementTextTrim("operdate"), "yyyy-MM-dd HH:mm:ss"));
        vomShippingStatus.setStatus(rootElement.elementTextTrim("status"));
        vomShippingStatus.setContent(rootElement.elementTextTrim("content"));
        vomShippingStatus.setRemark(rootElement.elementTextTrim("remark"));
        vomShippingStatus.setProcessStatus(0);
        return vomShippingStatus;
    }
}
