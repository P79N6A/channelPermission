package com.haier.svc.services;

import com.haier.common.util.StringUtil;
import com.haier.purchase.data.model.ReplenishOrder;
import com.haier.purchase.data.model.ReplenishOrderConfirmDisplayItem;
import com.haier.purchase.data.service.PurchaseCnReplOrderService;
import com.haier.svc.service.CnReplenishOrderService;
import com.taobao.pac.sdk.cp.PacClient;
import com.taobao.pac.sdk.cp.SendSysParams;
import com.taobao.pac.sdk.cp.dataobject.request.ASCP_PUSH_REPLENISHMENT_ORDER_CALLBACK.AscpPushReplenishmentOrderCallbackRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 菜鸟补货实现（预约计划入库）
 **/
@Service
public class CnReplenishOrderServiceImpl implements CnReplenishOrderService {

    @Autowired
    private PurchaseCnReplOrderService purchaseCnReplOrderService;

    @Override
    public Map<String, Object> findOrderList(Map<String, Object> params) {
        Map<String, Object> retMap = purchaseCnReplOrderService.getReplOrdersByPage(params);
        return retMap;
    }

    @Override
    public Map<String, Object> queryReplOrderDetail(Integer id, String replNo) {
        Map<String, Object> retMap = new HashMap<String, Object>();

        //获得MemberInvoices信息
        ReplenishOrderConfirmDisplayItem replOrder = purchaseCnReplOrderService.getById4Confirm(id);
        retMap.put("replOrder", replOrder);
        retMap.put("replNo", replNo);
        return retMap;
    }

    @Override
    public String confirmReplOrder(Integer id, Integer confirmReplQty, Date confirmDeadLine, String contactName, String contactPhone) {
        //查询补货单信息，确认状态是否符合要求
        ReplenishOrder order = purchaseCnReplOrderService.getById(id);
        if (null == order) {
            return "该补货单不存在";
        } else if (null != order && order.getState() != 0) {
            return ""; //成功
        }
        //保存补货确认信息
        order.setConfirmReplQty(confirmReplQty);
        order.setConfirmDeadLine(confirmDeadLine);
        order.setContactName(contactName);
        order.setContactPhone(contactPhone);
        order.setState(1);
        order.setModifiedTime(new Date());
        purchaseCnReplOrderService.confirmOrder(order);
        //85单号的生成和向天猫推送85单号 放到job中执行
        return "";
    }
}
