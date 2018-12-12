package com.haier.logistics.Helper;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.eis.model.VomShippingStatus;
import com.haier.logistics.services.OrderServiceImpl;
import com.haier.shop.model.OrderProductsNew;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/*
* 作者:张波
* 2017/12/22
* */
@Service("logisticsHandler")
public class ReturnOfStoreLogisticsHandler extends LogisticsHandler {
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private OutSaleLogisticsHandler outSaleLogisticsHandler;
    @Override
    public void process(VomShippingStatus shippingStatus) throws BusinessException {
        String refNo = shippingStatus.getOrderNo();
        Assert.notNull(refNo, "单据号不能为空");
        String status = shippingStatus.getStatus();

        boolean isReturnOfStore = refNo.indexOf("TH") == 14 || refNo.indexOf("RB") == 14
                || refNo.indexOf("IB") == 14 || refNo.indexOf("CX") == 14;
        if (!isReturnOfStore) {
            outSaleLogisticsHandler.process(shippingStatus);
            return;
        }

        String corderSn = refNo.replaceAll("TH.*", "");

        OrderProductsNew orderProducts = EisBuzHelper.getOrderProducts(orderService, corderSn);
        if (orderProducts == null) {
            setProcessSuccess(shippingStatus.getId(), "未获取到关联的网单，退货单号：" + refNo);
            return;
        }

        ServiceResult<Boolean> orderResult;
        //接单
        if ("WMS_ACCEPT".equals(status)) {
            orderResult = orderService.repairAfterVomAccepted(refNo, shippingStatus.getExpNo(),
                    shippingStatus.getOperDate());
        } else if ("WMS_FAILED".equals(status)) {//拒单
            orderResult = orderService.repairAfterVomRejected(refNo, shippingStatus.getContent(),
                    shippingStatus.getOperDate());
        } else {
            setProcessSuccess(shippingStatus.getId(), "退货入库只处理接单和据单节点");
            return;
        }

        if (!orderResult.getSuccess() || !orderResult.getResult()) {
            setProcessFailed(shippingStatus.getId(), orderResult.getMessage());
        } else {
            setProcessSuccess(shippingStatus.getId(), "");
        }
    }


    protected int setProcessSuccess(Integer id, String msg) {

        return setProcessStatus(id, VomShippingStatus.PROCESS_STATUS_DOWN,
                VomShippingStatus.PROCESS_STATUS_WAIT, msg);
    }
    protected int setProcessFailed(Integer id, String msg) {
        return setProcessStatus(id, VomShippingStatus.PROCESS_STATUS_WAIT,
                VomShippingStatus.PROCESS_STATUS_WAIT, msg);
    }
}

