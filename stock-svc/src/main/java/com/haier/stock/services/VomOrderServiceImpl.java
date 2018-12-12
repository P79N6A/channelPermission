package com.haier.stock.services;

import java.util.Map;

import com.haier.purchase.data.model.GoodsBackInfoResponse;
import com.haier.shop.model.VOMCancelOrderRequire;
import com.haier.shop.model.VOMOrderResponse;
import com.haier.shop.model.VOMSynOrderRequire;
import com.haier.stock.service.VomOrderService;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
@Service
public class VomOrderServiceImpl implements VomOrderService {

    private static Logger          logger = LogManager.getLogger(VomOrderServiceImpl.class);
    @Autowired
    private VOMOrderModel          vomOrderModel;


    public void setVomOrderModel(VOMOrderModel vomOrderModel) {
        this.vomOrderModel = vomOrderModel;
    }

    public ServiceResult<Boolean> synVomStatus(Map<String, Object> paramMap) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        serviceResult = vomOrderModel.synVomStatus(paramMap);
        return serviceResult;
    }

    public ServiceResult<Boolean> returnVomStatus(Map<String, Object> paramMap) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        serviceResult = vomOrderModel.returnVomStatus(paramMap);
        return serviceResult;
    }

    public ServiceResult<GoodsBackInfoResponse> getGoodsBackInfo(Map<String, Object> paramMap) {
        ServiceResult<GoodsBackInfoResponse> serviceResult = new ServiceResult<GoodsBackInfoResponse>();
        serviceResult = vomOrderModel.getGoodsBackInfo(paramMap);
        return serviceResult;
    }

    public ServiceResult<VOMOrderResponse> synOrderInfo(VOMSynOrderRequire synOrderRequire) {
        ServiceResult<VOMOrderResponse> serviceResult = new ServiceResult<VOMOrderResponse>();
        VOMOrderResponse vomOrderResponse = vomOrderModel.synOrderInfo(synOrderRequire);
        serviceResult.setResult(vomOrderResponse);
        return serviceResult;
    }

    public ServiceResult<Boolean> stopCgoGenuineRejectList(Map<String, Object> paramMap) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();

        try {

            Boolean flag = vomOrderModel.stopCgoGenuineRejectList(paramMap);
            result.setResult(flag);
            /*//TODO 2QILOG 终止退货日志
            try {
                //logger.info("入参开始"+JsonUtil.toJson(paramMap));
                List<Map<String, Object>> siouList = new ArrayList<Map<String, Object>>();
                siouList = (List<Map<String, Object>>) paramMap.get("successMap");
                //logger.info("取出OU单LIST+MAP"+JsonUtil.toJson(siouList));
                List<SiOuInfoVO> siOuInfoVO = new ArrayList<SiOuInfoVO>();
                List<String> siouListTemp = new ArrayList<String>();
                Map<String, Object> siouMap = new HashMap<String, Object>();
                for (Map<String, Object> map : siouList) {
                    siouListTemp.add((String) map.get("siOuNo"));
                }
                siouMap.put("si_ou_slipNo_list", siouListTemp);
                //logger.info("重新整合MAP"+JsonUtil.toJson(siouMap));
                List<SIOUInfoItem> resuSI = cgoModel.getCgoOrderListBySiou(siouMap);
                //logger.info("根据OU单查出所需数据"+JsonUtil.toJson(resuSI));
                List<OrderOperationLog> insertList = new ArrayList<OrderOperationLog>();
                if (flag) {
                    for (SIOUInfoItem sIOUInfoItem : resuSI) {
                        OrderOperationLog orderOperationLog = new OrderOperationLog();
                        orderOperationLog.setContent("终止退货成功");
                        orderOperationLog.setOrder_id(sIOUInfoItem.getSource_order_id());
                        orderOperationLog.setSub_order_id(sIOUInfoItem.getOrder_id());
                        orderOperationLog.setIs_sucess("1");
                        orderOperationLog.setType(sIOUInfoItem.getFlow_flag());
                        orderOperationLog.setSystem("采购平台");
                        insertList.add(orderOperationLog);
                    }
                } else {
                    for (SIOUInfoItem sIOUInfoItem : resuSI) {
                        OrderOperationLog orderOperationLog = new OrderOperationLog();
                        orderOperationLog.setContent("终止退货失败");
                        orderOperationLog.setOrder_id(sIOUInfoItem.getSource_order_id());
                        orderOperationLog.setSub_order_id(sIOUInfoItem.getOrder_id());
                        orderOperationLog.setIs_sucess("0");
                        orderOperationLog.setType(sIOUInfoItem.getFlow_flag());
                        orderOperationLog.setSystem("采购平台");
                        insertList.add(orderOperationLog);
                    }
                }
                //logger.info("插入日志参数"+JsonUtil.toJson(insertList));
                orderOperationLogModel.insertOrderOperationLog(insertList);
            } catch (Exception e) {
                logger.error("插入日志失败：", e);
            }*/
            //end

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            result.setResult(false);
            logger.error("提交订单失败：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<VOMOrderResponse> cancelVomOrderInfo(VOMCancelOrderRequire vOMCancelOrderRequire) {
        ServiceResult<VOMOrderResponse> serviceResult = new ServiceResult<VOMOrderResponse>();
        VOMOrderResponse vomOrderResponse = vomOrderModel.cancelOrderInfo(vOMCancelOrderRequire);
        serviceResult.setResult(vomOrderResponse);
        return serviceResult;
    }

    public ServiceResult<Boolean> stopCrmGenuineRejectList(Map<String, Object> paramMap) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();

        try {
            Boolean flag = vomOrderModel.stopCrmGenuineRejectList(paramMap);
            result.setResult(flag);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            result.setResult(false);
            logger.error("终止退货订单失败：", e);
        }
        return result;
    }

}
