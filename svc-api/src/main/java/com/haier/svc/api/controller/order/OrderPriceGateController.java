package com.haier.svc.api.controller.order;


import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.OrderPriceGate;
import com.haier.shop.model.OrderPriceProductGroupIndustry;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.Orders;
import com.haier.shop.model.OrdersNew;
import com.haier.shop.service.OrderPriceGateService;
import com.haier.shop.service.OrderProductsNewService;
import com.haier.shop.service.OrdersNewService;
import com.haier.svc.api.controller.util.CommUtil;
import com.haier.svc.api.controller.util.ExcelCallbackInterfaceUtil;
import com.haier.svc.api.controller.util.ExcelExportUtil;
import com.haier.svc.api.controller.util.HttpJsonResult;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "order")
public class OrderPriceGateController {
    private final static org.apache.log4j.Logger LOGGER = LogManager
                                                            .getLogger(OrderPriceGateController.class);

    @Autowired
    private OrderPriceGateService orderPriceGateModel;
    @Autowired
    private OrdersNewService ordersNewService;
    @Autowired
    private OrderProductsNewService orderProductsNewService;


    @RequestMapping(value = { "/orderPriceGateList.html" })
    String orderPriceGateList() {
        return "order/orderPriceGateList";
    }

    /**
     * 获取 订单来源 下拉列表
     * @return
     */
    @RequestMapping(value = { "/getOrderPriceSourceList" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<Map<String, String>>> getOrderPriceSourceList() {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
        Assert.notNull(orderPriceGateModel, "Property 'orderPriceGateModel' is required.");
        try {
            result.setData(orderPriceGateModel.getOrderPriceSourceList());
        } catch (Exception e) {
            LOGGER.error("[OrderPriceGateController]获取订单来源下拉框时发生未知错误", e);
            result.setMessage("获取订单来源下拉框时发生未知错误");
        }
        return result;
    }

    //获取 采购账户 列表[费用池]
    @RequestMapping(value = { "/getIndustryCode" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<Map<String, String>>> getIndustryCode() {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
        Assert.notNull(orderPriceGateModel, "Property 'orderPriceGateModel' is required.");
        try {
            result.setData(orderPriceGateModel.getIndustryCode());
        } catch (Exception e) {
            LOGGER.error("[OrderPriceGateController]获取采购账户下拉框时发生未知错误", e);
            result.setMessage("获取采购账户下拉框时发生未知错误");
        }
        return result;
    }

    //获取 产品组 列表[费用池]
    @RequestMapping(value = { "/getProductGroup" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<Map<String, String>>> getProductGroup(@RequestParam(required = true) String industryCode) {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
        Assert.notNull(orderPriceGateModel, "Property 'orderPriceGateModel' is required.");
        try {
            result.setData(orderPriceGateModel.getProductGroup(industryCode));
        } catch (Exception e) {
            LOGGER.error("[OrderPriceGateController]获取产品组下拉框时发生未知错误", e);
            result.setMessage("获取产品组下拉框时发生未知错误");
        }
        return result;
    }

    /**
     * 查询订单价格闸口列表
     * @param orderSn
     * @param cOrderSn
     * @param orderSource
     * @param createTimeMin
     * @param createTimeMax
     * @param rows
     * @param page
     * @param request
     * @param response
     */
    @RequestMapping(value = { "/getOrderPriceGateList.html" }, method = { RequestMethod.POST })
    @ResponseBody
    public String getOrderPriceGateList(@RequestParam(required = false) String orderSn,
                               @RequestParam(required = false) String cOrderSn,
                               @RequestParam(required = false) String channel,
                               @RequestParam(required = false) String orderSource,
                               @RequestParam(required = false) String orderStatusCode,
                               @RequestParam(required = false) String gateStatus,
                               @RequestParam(required = false) String gateType,
                               @RequestParam(required = false) String industryCode,
                               @RequestParam(required = false) String productGroup,
                               @RequestParam(required = false) String createTimeMin,
                               @RequestParam(required = false) String createTimeMax,
                               @RequestParam(required = false) Integer rows,
                               @RequestParam(required = false) Integer page,
                               HttpServletRequest request, HttpServletResponse response) {
        try {

            if (rows == null){
                rows = 20;
            }
            if (page == null){
                page = 1;
            }
            if (createTimeMin != null && !createTimeMin.equals("")) {
                createTimeMin = (timeStringToDate(createTimeMin).getTime() / 1000) + "";
            }
            if (createTimeMax != null && !createTimeMax.equals("")) {
                createTimeMax = (timeStringToDate(createTimeMax).getTime() / 1000) + "";
            }
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("m", (page - 1) * rows);
            paramMap.put("n", rows);
            paramMap.put("orderSn", orderSn);
            paramMap.put("cOrderSn", cOrderSn);
            paramMap.put("channel", channel);
            paramMap.put("orderSource", orderSource);
            if (orderStatusCode.contains("77")){
                orderStatusCode = null;
            }
            if (orderStatusCode != null && !orderStatusCode.trim().equals("")
                && orderStatusCode.split(",").length > 0) {
                paramMap.put("orderStatusCode", Arrays.asList(orderStatusCode.split(",")));//订单状态多选
            }else {
                paramMap.put("orderStatusCode", orderStatusCode);
            }
            paramMap.put("gateStatus", gateStatus);
            paramMap.put("gateType", gateType);
            if (gateType != null && gateType.equals("2")) {
                paramMap.put("industryCode", industryCode);
                paramMap.put("productGroup", productGroup);
            } else {
                paramMap.put("industryCode", "");
                paramMap.put("productGroup", "");
            }
            paramMap.put("createTimeMin", createTimeMin);
            paramMap.put("createTimeMax", createTimeMax);

            List<OrderPriceGate> list = orderPriceGateModel.getOrderPriceGateList(paramMap);
            int resultcount = orderPriceGateModel.getRows(paramMap);
            replaceSourceAndChannel(list);

            response.setContentType("application/json;charset=UTF-8");
            Map<String, Object> retMap = new HashMap<String, Object>();
            retMap.put("total", resultcount);
            if (null == list){
                list = new ArrayList<>();
            }
            retMap.put("rows", list);
            return JsonUtil.toJson(retMap);

        } catch (Exception e) {
            LOGGER.error("[OrderPriceGateController.getOrderPriceGateList]查询订单价格闸口列表时发生未知错误", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    /**
     * 导出订单价格闸口列表
     * @param exportData
     * @param request
     * @param response
     */
    @RequestMapping(value = { "exportOrderPriceGateList.html" })
    void exportAllMemberInvoiceList(@RequestParam(required = false) String exportData,
                                    HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("m", 0);
        params.put("n", 5000);
        //orderStatus 
        Map<String, String> map = JsonUtil.fromJson(exportData);
        params.put("orderSn", map.get("orderSn"));
        params.put("cOrderSn", map.get("cOrderSn"));
        params.put("channel", map.get("channel"));
        params.put("orderSource", map.get("orderSource"));
        String orderStatusCode = map.get("orderStatusCode");
        if (orderStatusCode.contains("77")){
            orderStatusCode = null;
        }
        if (orderStatusCode != null && !orderStatusCode.trim().equals("")
            && orderStatusCode.split(";").length > 0) {
            params.put("orderStatusCode", Arrays.asList(orderStatusCode.split(";")));//订单状态多选
        }else {
            params.put("orderStatusCode", orderStatusCode);
        }
        params.put("gateStatus", map.get("gateStatus"));
        params.put("gateType", map.get("gateType"));

        if (map.get("gateType") != null && map.get("gateType").equals("2")) {
            params.put("industryCode", map.get("industryCode"));
            params.put("productGroup", map.get("productGroup"));
        } else {
            params.put("industryCode", "");
            params.put("productGroup", "");
        }

        String createTimeMin = map.get("createTimeMin").trim();
        String createTimeMax = map.get("createTimeMax").trim();
        if (createTimeMin != null && !createTimeMin.equals("")) {
            createTimeMin = (timeStringToDate(createTimeMin).getTime() / 1000) + "";
        }
        if (createTimeMax != null && !createTimeMax.equals("")) {
            createTimeMax = (timeStringToDate(createTimeMax).getTime() / 1000) + "";
        }
        params.put("createTimeMin", createTimeMin);
        params.put("createTimeMax", createTimeMax);

        //获取开单列表List
        final List<OrderPriceGate> resultList = orderPriceGateModel.getOrderPriceGateList(params);
        replaceSourceAndChannel(resultList);
        String fileName = "订单价格闸口列表报表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "数据导出";

        String[] sheetHead = new String[] { "序号", "订单来源", "渠道", "产业", "产品组", "订单号", "订单状态", "网单号",
                "物料编码", "品类名称", "型号", "网单单价", "商城优惠券金额", "平台优惠券金额", "网单表productAmount", "网单数量",
                "保本价(单价)", "保本价差额(开票金额+平台承担的优惠金额-保本价)/费用池优惠券", "下单时间", "被闸时间", "被闸原因", "闸口状态",
                "闸口类型", "放行操作人", "责任人", "放行原因", "放行时间" };
        try {
            ExcelExportUtil.exportEntity(LOGGER, request, response, fileName, sheetName, sheetHead,
                new ExcelCallbackInterfaceUtil() {

                    @Override
                    public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp)
                                                                                                 throws Exception {
                        setExportMemberInvoiceList(sheet, temp, resultList);
                    }

                });
        } catch (Exception e) {
            LOGGER.error("订单价格闸口列表导出失败", e);
            e.printStackTrace();
        }
    }

    /**
     * 导出具体数据，实现回调函数
     * @param sheet
     * @param temp 行号
     * @param list 传入需要导出的 list
     * @throws WriteException 
     */
    private void setExportMemberInvoiceList(WritableSheet sheet, int temp, List<OrderPriceGate> list)
                                                                                                     throws Exception {
        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
            UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat textFormat = new WritableCellFormat(font);
        textFormat.setAlignment(Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        DisplayFormat displayFormat = NumberFormats.INTEGER;
        WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
        numberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        int index = 0;
        for (OrderPriceGate orderPriceGate : list) {

            index++;
            int i = 0;

            sheet.setColumnView(0, 10);
            sheet.addCell(new Label(0, temp, CommUtil.getStringValue(index), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                .getOrderSource()), textFormat));
            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                .getChannelCode()), textFormat));
            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                .getIndustryCode()), textFormat));
            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                .getProductGroup()), textFormat));
            sheet.setColumnView(++i, 20);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate.getOrderSn()),
                textFormat));
            sheet.setColumnView(++i, 20);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                .getOrderStatus()), textFormat));
            sheet.setColumnView(++i, 20);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate.getCorderSn()),
                textFormat));
            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate.getSku()),
                textFormat));

            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate.getCateName()),
                textFormat));

            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                .getProductName()), textFormat));
            sheet.setColumnView(++i, 10);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                .getOrderProductPrice()), textFormat));
            sheet.setColumnView(++i, 20);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                .getCouponAmount()), textFormat));
            sheet.setColumnView(++i, 20);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                .getPlatformCouponAmount()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                .getOrderProductAmount()), textFormat));
            sheet.setColumnView(++i, 10);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                .getOrderProductNumber()), textFormat));
            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                .getGuaranteePrice()), textFormat));
            sheet.setColumnView(++i, 50);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                .getSubductionPrice()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                .getOrderAddTime()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp,
                CommUtil.getStringValue(orderPriceGate.getCreateTime()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp,
                CommUtil.getStringValue(orderPriceGate.getLockReason()), textFormat));
            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil
                .getStringValue(getGateStatusName(orderPriceGate.getGateStatus())), textFormat));
            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(getGateTypeName(orderPriceGate
                .getGateType())), textFormat));
            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate.getOperator()),
                textFormat));
            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                .getResponsiblePerson()), textFormat));
            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                .getUnlockReason()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp,
                CommUtil.getStringValue(orderPriceGate.getUnlockTime()), textFormat));
            temp++;
        }
    }

    /**
     * 将订单来源、渠道编码 替换为 相应的名称
     * @param list
     * @return
     */
    private void replaceSourceAndChannel(List<OrderPriceGate> list) {
        if (list != null && list.size() > 0) {
            List<Map<String, String>> sourceList = orderPriceGateModel
                .getOrderPriceSourceChannelList();
            if (sourceList != null && sourceList.size() > 0) {
                Map<String, String> sourceMap = new HashMap<String, String>();
                Map<String, String> channelMap = new HashMap<String, String>();
                for (Map<String, String> map : sourceList) {
                    sourceMap.put(map.get("order_source"), map.get("order_source_name"));
                    channelMap.put(map.get("channel_code"), map.get("channel_name"));
                }

                List<OrderPriceProductGroupIndustry> industryList = orderPriceGateModel
                    .getOrderPriceProductGroupIndustryList();
                Map<String, String> industryCodeMap = new HashMap<String, String>();
                Map<String, String> productGroupMap = new HashMap<String, String>();
                if (industryList != null && industryList.size() > 0) {
                    for (OrderPriceProductGroupIndustry industry : industryList) {
                        industryCodeMap.put(industry.getIndustryCode(), industry.getIndustryName());
                        productGroupMap.put(industry.getProductGroup(),
                            industry.getProductGroupName());
                    }
                }

                for (OrderPriceGate orderPriceGate : list) {
                    orderPriceGate
                        .setOrderSource(sourceMap.get(orderPriceGate.getOrderSource()) == null ? orderPriceGate
                            .getOrderSource() : sourceMap.get(orderPriceGate.getOrderSource()));
                    orderPriceGate
                        .setChannelCode(channelMap.get(orderPriceGate.getChannelCode()) == null ? orderPriceGate
                            .getChannelCode() : channelMap.get(orderPriceGate.getChannelCode()));
                    orderPriceGate.setIndustryCode(industryCodeMap.get(orderPriceGate
                        .getIndustryCode()) == null ? orderPriceGate.getIndustryCode()
                        : industryCodeMap.get(orderPriceGate.getIndustryCode()));
                    orderPriceGate.setProductGroup(productGroupMap.get(orderPriceGate
                        .getProductGroup()) == null ? orderPriceGate.getProductGroup()
                        : productGroupMap.get(orderPriceGate.getProductGroup()));
                    //由于增加条件订单状态关联了订单表，直接能查出订单状态.同时关联了网单表查出商品名称
                    //                    orderPriceGate.setOrderStatus(getOrderStatusByOrderSn(orderPriceGate
                    //                        .getOrderSn()));
                    orderPriceGate.setOrderStatus(getOrderStatus(orderPriceGate.getOrderStatus()));
                    //                    orderPriceGate.setProductName(getOrderProductNameByCOrderSn(orderPriceGate
                    //                        .getCorderSn()));
                }
            }
        }
    }

    /**
     * 闸口状态
     * @param gateStatus
     * @return
     */
    private String getGateStatusName(Integer gateStatus) {
        if (gateStatus == null) {
            return "";
        }
        if (gateStatus.intValue() == 0){
            return "放行";
        }
        if (gateStatus.intValue() == 1){
            return "闸住";
        }
        return gateStatus.intValue() + "";
    }

    /**
     * 闸口类型
     * @param gateType
     * @return
     */
    private String getGateTypeName(Integer gateType) {
        if (gateType == null) {
            return "";
        }
        if (gateType.intValue() == 1){
            return "保本价";
        }
        if (gateType.intValue() == 2){
            return "费用池";
        }
        return gateType.intValue() + "";
    }

    /**
     * 根据订单号查询订单状态
     * @param orderSn
     * @return
     */
    @Deprecated
    private String getOrderStatusByOrderSn(String orderSn) {
        if (orderSn == null){
            return null;
        }

        try {
            OrdersNew ordersNew = ordersNewService.getByOrderSn(orderSn);


                if (ordersNew != null) {
                    return getOrderStatus(ordersNew.getOrderStatus());
                } else {
                    return "查询不到";
                }
        } catch (Exception e) {
            LOGGER.error("[OrderPriceGateController.getOrderStatusByOrderSn]查询订单" + orderSn
                         + "信息时发生未知错误", e);
            return "查询异常";
        }
    }

    /**
     * 根据网单号查询productName
     * @param cOrderSn
     * @return
     */
    @Deprecated
    private String getOrderProductNameByCOrderSn(String cOrderSn) {
        if (cOrderSn == null){
            return null;
        }

        try {
            OrderProductsNew orderProductsNew = orderProductsNewService
                .getByCOrderSn(cOrderSn);
                if (orderProductsNew != null) {
                    return orderProductsNew.getProductName();
                } else {
                    return "查询不到";
                }
        } catch (Exception e) {
            LOGGER.error("[OrderPriceGateController.getOrderProductNameByCOrderSn]查询网单" + cOrderSn
                         + "信息时发生未知错误", e);
            return "查询异常";
        }
    }

    /**
     * 转换网单状态
     * @param orderStatus
     * @return
     */
    @Deprecated
    private String getOrderStatus(int orderStatus) {
        String orderStatusName;
        switch (orderStatus) {
            case 200:
                orderStatusName = "未确认";
                break;
            case 201:
                orderStatusName = "已确认";
                break;
            case 202:
                orderStatusName = "已取消";
                break;
            case 203:
                orderStatusName = "已完成";
                break;
            case 204:
                orderStatusName = "部分缺货";
                break;
            case -100:
                orderStatusName = "未定义";
                break;
            default:
                orderStatusName = orderStatus + "";
                break;
        }
        return orderStatusName;
    }

    /**
     * 转换网单状态
     * @param orderStatus
     * @return
     */
    private String getOrderStatus(String orderStatus) {
        if (orderStatus == null) {
            return null;
        } else if (orderStatus.equals("200")) {
            return "未确认";
        } else if (orderStatus.equals("201")) {
            return "已确认";
        } else if (orderStatus.equals("202")) {
            return "已取消";
        } else if (orderStatus.equals("203")) {
            return "已完成";
        } else if (orderStatus.equals("204")) {
            return "部分缺货";
        } else if (orderStatus.equals("-100")) {
            return "未定义";
        } else {
            return orderStatus;
        }
    }

    /**
     * 处理时间
     * @param time
     * @return
     * @throws ParseException 
     */
    public Date timeStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtil.isEmpty(time)) {
            return null;
        }
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            return new Date();
        }
    }

    @RequestMapping(value = { "/getGuaranteePriceChannel" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<Map<String, String>>> getGuaranteePriceChannel() {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();

        try {
            result.setData(orderPriceGateModel.getGuaranteePriceChannel());
        } catch (Exception e) {
            LOGGER.error("[GuaranteePriceUnLockController_getChannel]获取渠道下拉框时发生未知错误", e);
            result.setMessage("获取渠道下拉框时发生未知错误");
        }
        return result;
    }

    @RequestMapping(value = { "/getGuaranteePriceSource" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<Map<String, String>>> getGuaranteePriceSource(@RequestParam(required = true) String channel) {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();

        try {
            result.setData(orderPriceGateModel.getGuaranteePriceSource(channel));
        } catch (Exception e) {
            LOGGER.error("[OrderPriceGateController_getSource]获取订单来源下拉框时发生未知错误", e);
            result.setMessage("获取订单来源下拉框时发生未知错误");
        }
        return result;
    }

}
