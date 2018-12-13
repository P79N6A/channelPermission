package com.haier.svc.api.controller;

import com.haier.common.BusinessException;
import com.haier.common.util.JsonUtil;
import com.haier.svc.api.controller.invoice.InvoiceController;
import com.haier.svc.service.CnReplenishOrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 菜鸟补货单控制类
 **/
@Controller
@RequestMapping(value = "cnreplenishment/")
public class CnReplenishOrderController {

    private static final Logger logger = LogManager.getLogger(CnReplenishOrderController.class);

    @Autowired
    private CnReplenishOrderService cnReplenishOrderService;

    /**
     * 菜鸟补货单列表页面跳转
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"orderList"}, method = {RequestMethod.GET})
    String invoiceMakeOutList(HttpServletRequest request) {
        return "purchase/cnReplOrderList";
    }

    /**
     * 查询菜鸟补货单信息
     *
     * @param scItemId           货品id
     * @param scItemCode         货品code
     * @param scItemName         货品名称
     * @param replNo             补货单号
     * @param fromStoreCode      源仓
//     * @param transportType      运输方式
     * @param storeCode          仓编码
     * @param storeName          仓名称
     * @param state              补货流程状态
     * @param entryOrderCode     85单号
     * @param entryOrderId       LBX号
     * @param deadLineMax        最晚入库时间
     * @param deadLineMin
     * @param confirmDeadLineMax 确认最晚入库时间
     * @param confirmDeadLineMin
     */
    @RequestMapping(value = {"findOrderList"}, method = {RequestMethod.POST})
    void findInvoiceMakeOutList(@RequestParam(required = false) String gmtCreateMin,
                                @RequestParam(required = false) String gmtCreateMax,
                                @RequestParam(required = false) String gmtModifiedMin,
                                @RequestParam(required = false) String gmtModifiedMax,
                                @RequestParam(required = false) String scItemId,
                                @RequestParam(required = false) String scItemCode,
                                @RequestParam(required = false) String scItemName,
                                @RequestParam(required = false) String replNo,
                                @RequestParam(required = false) String fromStoreCode,
//                                @RequestParam(required = false) String transportType,
                                @RequestParam(required = false) String storeCode,
                                @RequestParam(required = false) String storeName,
                                @RequestParam(required = false) String state,
                                @RequestParam(required = false) String entryOrderCode,
                                @RequestParam(required = false) String entryOrderId,
                                @RequestParam(required = false) String deadLineMax,
                                @RequestParam(required = false) String deadLineMin,
                                @RequestParam(required = false) String confirmDeadLineMax,
                                @RequestParam(required = false) String confirmDeadLineMin,
                                @RequestParam(required = false) Integer rows,
                                @RequestParam(required = false) Integer page,
                                HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        try {
            if (rows == null)
                rows = 50;
            if (page == null)
                page = 1;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("m", (page - 1) * rows);
            params.put("n", rows);
            //参数加入params里
            params.put("gmtCreateMin", gmtCreateMin);
            params.put("gmtCreateMax", gmtCreateMax);
            params.put("gmtModifiedMin", gmtModifiedMin);
            params.put("gmtModifiedMax", gmtModifiedMax);
            params.put("scItemId", scItemId.trim());
            params.put("scItemCode", scItemCode.trim());
            params.put("scItemName", scItemName.trim());
            params.put("replNo", replNo.trim());
            params.put("fromStoreCode", fromStoreCode.trim());
//            params.put("transportType", transportType);
            params.put("storeCode", storeCode.trim());
            params.put("storeName", storeName.trim());
            params.put("state", state);
            params.put("entryOrderCode", entryOrderCode.trim());
            params.put("entryOrderId", entryOrderId.trim());
            params.put("deadLineMin", deadLineMin);
            params.put("deadLineMax", deadLineMax);
            params.put("confirmDeadLineMin", confirmDeadLineMin);
            params.put("confirmDeadLineMax", confirmDeadLineMax);

            Map<String, Object> retMap = cnReplenishOrderService.findOrderList(params);
            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    /**
     * 获取待确认补货单信息
     *
     * @param id
     * @param replNo
     * @param modelMap
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"replOrderDetail"}, method = {RequestMethod.GET})
    String getReplOrderDetail(@RequestParam(required = false) Integer id,
                                @RequestParam(required = false) String replNo,
                                Map<String, Object> modelMap, HttpServletRequest request,
                                HttpServletResponse response) {
        try {
            Map<String, Object> retMap = cnReplenishOrderService.queryReplOrderDetail(id, replNo);
            modelMap.putAll(retMap);
        } catch (Exception e) {
            logger.error("查询补货单信息失败", e);
            throw new BusinessException("异常信息:" + e.getMessage());
        }
        return "purchase/cnReplOrderConfirm";
    }

//    /**
//     * 确认补货单信息 (去掉不使用)
//     *
//     * @param request
//     * @return
//     */
//    @RequestMapping(value = {"confirmReplOrder"}, method = {RequestMethod.GET})
//    @ResponseBody
//    Map<String, Object> confirmReplOrder(@RequestParam(required = false) Integer id,
//                                           @RequestParam(required = false) String confirmReplQty,
//                                           @RequestParam(required = false) String confirmDeadLine,
//                                           @RequestParam(required = false) String contactName,
//                                           @RequestParam(required = false) String contactPhone,
//                                           HttpServletRequest request, HttpServletResponse response) {
//        String errorFlg = "1";
//        String message = "";
//        Map<String, Object> retMap = new HashMap<String, Object>();
//        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        try {
//            Integer confirmReplenishQty = Integer.parseInt(confirmReplQty);
//            Date date = sf.parse(confirmDeadLine);
//            //校验输入
//            if (confirmReplenishQty <= 0) {
//                retMap.put("message", "确认补货数量应当大于0");
//                retMap.put("errorFlg", errorFlg);
//                return retMap;
//            }
//            if (date.getTime() - (new Date()).getTime() <= 0) {
//                retMap.put("message", "确认最晚入库时间应大于当前时间");
//                retMap.put("errorFlg", errorFlg);
//                return retMap;
//            }
//            message = cnReplenishOrderService.confirmReplOrder(id, confirmReplenishQty, date, contactName, contactPhone);
//            if (message.trim().equals("")) {
//                message = "补货单确认成功，待出库";
//                errorFlg = "0";
//            }
//            retMap.put("message", message);
//            retMap.put("errorFlg", errorFlg);
//            return retMap;
//        } catch (Exception e) {
//            retMap.put("message", "补货单信息确认失败");
//            retMap.put("errorFlg", errorFlg);
//            logger.error("补货单信息确认失败", e);
//            throw new BusinessException("失败" + e.getMessage());
//        }
//    }
}
