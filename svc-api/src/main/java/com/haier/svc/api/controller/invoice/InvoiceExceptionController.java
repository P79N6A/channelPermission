package com.haier.svc.api.controller.invoice;

import com.haier.common.BusinessException;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.invoice.service.InvoiceExceptionService;
import com.haier.shop.model.InvoiceExceptionDispItem;
import com.haier.stock.service.StockInvChannel2OrderSourceService;
import com.haier.svc.api.controller.util.CommUtil;
import com.haier.svc.api.controller.util.ExcelCallbackInterfaceUtil;
import com.haier.svc.api.controller.util.ExcelExportUtil;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "invoiceException/")
public class InvoiceExceptionController {
    private final static org.apache.log4j.Logger logger     = LogManager
                                                                .getLogger(InvoiceExceptionController.class);
    @Autowired
    private InvoiceExceptionService invoiceExceptionService;

    @Autowired
    private StockInvChannel2OrderSourceService stockInvChannel2OrderSourceService;


    private static long                          THREE_DAYS = 259200;
    private static long                          SEVEN_DAYS = 604800;

    @RequestMapping(value = { "/loadInvoiceExceptionList.html" }, method = { RequestMethod.GET })
    String loadInvoiceExceptionList(HttpServletRequest request) {
        return "invoice/invoiceExceptionList";
    }

    @RequestMapping(value = { "/getInvoiceExceptionList.html" },method = { RequestMethod.POST })
    void getInvoiceExceptionList(@RequestParam(required = false) String orderSn,
                                 @RequestParam(required = false) String cOrderSn,
                                 @RequestParam(required = false) String cPaymentStatus,
                                 @RequestParam(required = false) String status,
                                 @RequestParam(required = false) String makeReceiptType,
                                 @RequestParam(required = false) String isMakeReceipt,
                                 @RequestParam(required = false) String invoiceType,
                                 @RequestParam(required = false) String state,
                                 @RequestParam(required = false) String addTimeMin,
                                 @RequestParam(required = false) String addTimeMax,
                                 @RequestParam(required = false) String nowDate,
                                 @RequestParam(required = false) Integer rows,
                                 @RequestParam(required = false) Integer page,
                                 HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        try {

            if (rows == null)
                rows = 100;
            if (page == null)
                page = 1;
            if (addTimeMin != null && !addTimeMin.equals("")) {
                addTimeMin = (timeStringToDate(addTimeMin).getTime() / 1000) + "";
            }
            if (addTimeMax != null && !addTimeMax.equals("")) {
                addTimeMax = (timeStringToDate(addTimeMax).getTime() / 1000) + "";
            }

            if (nowDate == null || nowDate.equals("")) {
                nowDate = new Date().toString();
            }

            if (invoiceType.equals("1")) {//增值税发票
                nowDate = ((timeStringToDate(nowDate).getTime() / 1000) - SEVEN_DAYS) + "";
            } else {//电子发票
                nowDate = ((timeStringToDate(nowDate).getTime() / 1000) - THREE_DAYS) + "";
            }

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("m", (page - 1) * rows);
            paramMap.put("n", rows);
            paramMap.put("orderSn", orderSn);
            paramMap.put("cOrderSn", cOrderSn);
            paramMap.put("cPaymentStatus", cPaymentStatus);
            paramMap.put("status", status);
            paramMap.put("makeReceiptType", makeReceiptType);
            paramMap.put("isMakeReceipt", isMakeReceipt);
            paramMap.put("invoiceType", invoiceType);
            paramMap.put("state", state);
            paramMap.put("addTimeMin", addTimeMin);
            paramMap.put("addTimeMax", addTimeMax);
            paramMap.put("nowDate", nowDate);

            List<InvoiceExceptionDispItem> list = invoiceExceptionService
                .getInvoiceExceptionList(paramMap);
            int resultcount = invoiceExceptionService.getCount();


            Map<String, Object> retMap = new HashMap<String, Object>();
            retMap.put("total", resultcount);
            retMap.put("rows", list);
            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();

        } catch (Exception e) {
            logger.error("[InvoiceExceptionController.getInvoiceExceptionList]查询发票异常列表时发生未知错误", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    /**
     * 导出发票异常列表
     * @param exportData
     * @param request
     * @param response
     */
    @RequestMapping(value = { "/exportInvoiceExceptionList.html" })
    void exportInvoiceExceptionList(@RequestParam(required = false) String exportData,
                                    HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<String, Object>();
        //        params.put("m", 0);
        //        params.put("n", 5000);

        Map<String, String> map = JsonUtil.fromJson(exportData);

        params.put("orderSn", map.get("orderSn").trim());
        params.put("cOrderSn", map.get("cOrderSn").trim());
        params.put("cPaymentStatus", map.get("cPaymentStatus").trim());
        params.put("status", map.get("status").trim());
        params.put("makeReceiptType", map.get("makeReceiptType").trim());
        params.put("isMakeReceipt", map.get("isMakeReceipt").trim());
        params.put("invoiceType", map.get("invoiceType").trim());
        params.put("state", map.get("state").trim());

        String addTimeMin = map.get("addTimeMin").trim();
        String addTimeMax = map.get("addTimeMax").trim();
        if (addTimeMin != null && !addTimeMin.equals("")) {
            addTimeMin = (timeStringToDate(addTimeMin).getTime() / 1000) + "";
        }
        if (addTimeMax != null && !addTimeMax.equals("")) {
            addTimeMax = (timeStringToDate(addTimeMax).getTime() / 1000) + "";
        }
        params.put("addTimeMin", addTimeMin);
        params.put("addTimeMax", addTimeMax);

        String nowDate = map.get("nowDate").trim();
        if (nowDate == null || nowDate.equals("")) {
            nowDate = new Date().toString();
        }

        if (map.get("invoiceType").trim().equals("1")) {//增值税发票
            nowDate = ((timeStringToDate(nowDate).getTime() / 1000) - SEVEN_DAYS) + "";
        } else {//电子发票
            nowDate = ((timeStringToDate(nowDate).getTime() / 1000) - THREE_DAYS) + "";
        }
        params.put("nowDate", nowDate);

        //获取发票异常列表List
        final List<InvoiceExceptionDispItem> list = invoiceExceptionService
            .getInvoiceExceptionList(params);

        //replaceData(list);

        String fileName = "发票异常报表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "数据导出";

        String[] sheetHead = new String[] { "序号", "订单号", "来源订单号", "关联订单号", "是否为后台下单", "o2oType",
                "网单库存类型", "网单号", "是否需要开具发票", "网单状态", "网单付款状态", "订单付款状态", "订单付款方式", "订单付款金额",
                "开票类型", "开票状态", "发票收件人", "发票收件地址", "邮政编码", "收件人电话", "下单时间", "订单状态", "订单来源", "品牌",
                "类型", "型号", "物料编码", "售价", "数量", "运费", "总价", "订单备注", "下单人(会员用户名)", "淘宝收货人地址",
                "淘宝收货人详细地址", "收货人", "收货人电话", "省", "市", "区县", "街道", "联系地址", "邮编", "订单模式", "所属库房",
                "转运库房", "转运时效（小时）", "交易流水号", "订单使用积分", "付款时间", "首次确认时间", "首次确认人", "确认人", "确认时间",
                "分配网点", "分配时间", "LES出库号", "发票类型", "发票户头", "纳税人登记号", "电话", "地址", "开户行名称", "开户银行账号",
                "发票审核状态" };
        try {
            ExcelExportUtil.exportEntity(logger, request, response, fileName, sheetName, sheetHead,
                new ExcelCallbackInterfaceUtil() {

                    @Override
                    public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp)
                                                                                                 throws Exception {
                        setExportMemberInvoiceList(sheet, temp, list);
                    }

                });
        } catch (Exception e) {
            logger.error("发票异常报表导出失败", e);
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
    private void setExportMemberInvoiceList(WritableSheet sheet, int temp,
                                            List<InvoiceExceptionDispItem> list) throws Exception {
        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
            UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat textFormat = new WritableCellFormat(font);
        textFormat.setAlignment(Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        DisplayFormat displayFormat = NumberFormats.INTEGER;
        WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
        numberFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        int index = 0;
        for (InvoiceExceptionDispItem lt : list) {

            index++;
            int i = 0;

            sheet.setColumnView(0, 10);
            sheet.addCell(new Label(0, temp, CommUtil.getStringValue(index), textFormat));

            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getOrderSn()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getSourceOrderSn()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getRelationOrderSn()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet
                .addCell(new Label(i, temp, CommUtil.getStringValue(lt.getIsBackend()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getO2oType()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet
                .addCell(new Label(i, temp, CommUtil.getStringValue(lt.getStockType()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet
                .addCell(new Label(i, temp, CommUtil.getStringValue(lt.getcOrderSn()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet
                .addCell(new Label(i, temp, CommUtil.getStringValue(lt.getIsReceipt()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getStatus()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getcPaymentStatus()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getPaymentStatus()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getPaymentCode()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getPaidAmount()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getMakeReceiptType()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getIsMakeReceipt()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getReceiptConsignee()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getReceiptAddress()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getReceiptZipcode()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getReceiptMobile()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getAddTime()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getOrderStatus()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getSource()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet
                .addCell(new Label(i, temp, CommUtil.getStringValue(lt.getBrandName()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet
                .addCell(new Label(i, temp, CommUtil.getStringValue(lt.getCateName()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getProductName()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getSku()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getPrice()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getNumber()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getShippingFee()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getProductAmount()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getRemark()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet
                .addCell(new Label(i, temp, CommUtil.getStringValue(lt.getUsername()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getOriginRegionName()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getOriginAddress()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet
                .addCell(new Label(i, temp, CommUtil.getStringValue(lt.getConsignee()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getMobile()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet
                .addCell(new Label(i, temp, CommUtil.getStringValue(lt.getProvince()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getCity()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getRegion()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getStreet()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getAddress()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getZipcode()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getShippingMode()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getsCode()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getTsCode()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getTsShippingTime()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getTradeSn()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getPoints()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getPayTime()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getFirstConfirmTime()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getFirstConfirmPerson()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getAgent()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getConfirmTime()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getNetPointName()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getHpFinishDate()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getOutping()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getInvoiceType()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getInvoiceTitle()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getTaxPayerNumber()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getRegisterPhone()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getRegisterAddress()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet
                .addCell(new Label(i, temp, CommUtil.getStringValue(lt.getBankName()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getBankCardNumber()),
                textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(lt.getState()), textFormat));

            temp++;
        }
    }

    /**
     * 替换数据
     * @param list
     */
    private void replaceData(List<InvoiceExceptionDispItem> list) {
        if (list != null && list.size() > 0) {
            List<Map<String, String>> sourceList = stockInvChannel2OrderSourceService
                .getInvChannel2OrderSource(null);
            Map<String, String> sourceMap = new HashMap<String, String>();
            if (sourceList != null && sourceList.size() > 0) {
                for (Map<String, String> map : sourceList) {
                    sourceMap.put(map.get("order_source"), map.get("note"));
                }
            }

            for (InvoiceExceptionDispItem lt : list) {
                lt.setIsBackend(getIsBackend(lt.getIsBackend()));//是否后台下单
                lt.setStockType(getStockType(lt.getStockType()));//库存类型
                lt.setIsReceipt(getIsReceipt(lt.getIsReceipt()));//是否需要开具发票
                lt.setStatus(getStatus(lt.getStatus()));//网单状态
                lt.setcPaymentStatus(getcPaymentStatus(lt.getcPaymentStatus())); //网单付款状态
                lt.setPaymentStatus(getPaymentStatus(lt.getPaymentStatus()));//订单付款状态
                lt.setPaymentCode(getPaymentCode(lt.getPaymentCode()));//支付方式
                lt.setMakeReceiptType(getMakeReceiptType(lt.getMakeReceiptType())); //开票类型
                lt.setIsMakeReceipt(getIsMakeReceipt(lt.getIsMakeReceipt()));//开票状态
                lt.setAddTime(timeStamp2Date(lt.getAddTime(), null));//下单时间
                lt.setOrderStatus(getOrderStatus(lt.getOrderStatus()));//订单状态
                lt.setSource(getSource(sourceMap, lt.getSource()));//订单来源
                dealRegionName(lt);//省、//市、//区县、//街道
                lt.setPayTime(timeStamp2Date(lt.getPayTime(), null));//付款时间
                lt.setFirstConfirmTime(timeStamp2Date(lt.getFirstConfirmTime(), null));//首次确认时间
                lt.setConfirmTime(timeStamp2Date(lt.getConfirmTime(), null));//确认时间
                lt.setHpFinishDate(timeStamp2Date(lt.getHpFinishDate(), null));//分配时间
                lt.setInvoiceType(getInvoiceType(lt.getInvoiceType()));//发票类型            
                lt.setState(getState(lt.getState()));//发票审核状态
            }
        }
    }

    /**
     * 是否后台下单
     */
    private String getIsBackend(String isBackend) {
        if (isBackend == null)
            return "null";
        if ("0".equals(isBackend))
            return "否";
        if ("1".equals(isBackend))
            return "是";
        return isBackend;
    }

    /**
     * 库存类型
     */
    private String getStockType(String stockType) {
        if (stockType == null)
            return "null";
        if ("WA".equalsIgnoreCase(stockType))
            return "自有库存网单";
        return stockType;
    }

    /**
     * 是否需要开具发票
     */
    private String getIsReceipt(String isReceipt) {
        if (isReceipt == null || "".equals(isReceipt) || "1".equals(isReceipt))
            return "是";
        return "否";
    }

    /**
     * 网单状态
     */
    private String getStatus(String status) {
        if (status == null)
            return "null";
        if (status.equals("0"))
            return "处理中";
        if (status.equals("1"))
            return "已占用库存";
        if (status.equals("2"))
            return "同步到HP";
        if (status.equals("3"))
            return "同步到EC";
        if (status.equals("4"))
            return "分配网点";
        if (status.equals("8"))
            return "待出库";
        if (status.equals("10"))
            return "待审核";
        if (status.equals("11"))
            return "待转运入库";
        if (status.equals("12"))
            return "待转运出库";
        if (status.equals("40"))
            return "待发货";
        if (status.equals("150"))
            return "网点拒绝";
        if (status.equals("70"))
            return "待交付";
        if (status.equals("140"))
            return "用户签收";
        if (status.equals("130"))
            return "完成关闭";
        if (status.equals("160"))
            return "用户拒收";
        if (status.equals("110"))
            return "取消关闭";
        return status;
    }

    /**
     * 网单付款状态
     */
    private String getcPaymentStatus(String cPaymentStatus) {
        if (cPaymentStatus == null)
            return "null";
        if (cPaymentStatus.equals("200"))
            return "未付款";
        if (cPaymentStatus.equals("201"))
            return "已付款";
        if (cPaymentStatus.equals("206"))
            return "待退款";
        if (cPaymentStatus.equals("207"))
            return "已退款";
        return cPaymentStatus;
    }

    /**
     * 订单付款状态
     */
    private String getPaymentStatus(String paymentStatus) {
        if (paymentStatus == null)
            return "null";
        if (paymentStatus.equals("100"))
            return "未付款";

        if (paymentStatus.equals("101"))
            return "已付款";

        if (paymentStatus.equals("102"))
            return "待退款";
        if (paymentStatus.equals("103"))
            return "已退款";
        return paymentStatus;
    }

    /**
     * 订单付款方式
     */
    private String getPaymentCode(String paymentCode) {
        if (paymentCode == null)
            return "null";
        if (paymentCode.equals("chinapay"))
            return "银联支付";
        if (paymentCode.equals("alipay"))
            return "支付宝";
        if (paymentCode.equals("wxpay"))
            return "微信支付";
        if (paymentCode.equals("tenpay"))
            return "财付通";
        if (paymentCode.equals("cod"))
            return "货到付款";
        if (paymentCode.equals("insidestatement"))
            return "内部结算单";
        if (paymentCode.equals("lejia"))
            return "电子钱包";
        if (paymentCode.equals("giftCard"))
            return "礼品卡支付";
        if (paymentCode.equals("offline"))
            return "线下付款";
        if (paymentCode.equals("prepaid"))
            return "客户预付货款";
        if (paymentCode.equals("ccb"))
            return "建行网银支付";
        if (paymentCode.equals("balance"))
            return "余额支付";
        if (paymentCode.equals("alipaymobile"))
            return "支付宝移动商城";
        if (paymentCode.equals("chinaecpay"))
            return "联行支付";
        if (paymentCode.equals("kjtpay"))
            return "快捷通";
        if (paymentCode.equals("ccbfenqi"))
            return "建行信用卡分期";
        if (paymentCode.equals("weixinpc"))
            return "商城微信";

        return paymentCode;
    }

    /**
     * 开票类型
     */
    private String getMakeReceiptType(String makeReceiptType) {
        if (makeReceiptType == null)
            return "null";
        if (makeReceiptType.equals("1"))
            return "库房开票";
        if (makeReceiptType.equals("2"))
            return "共享开票";
        return makeReceiptType;
    }

    /**
     * 开票状态
     */
    private String getIsMakeReceipt(String isMakeReceipt) {
        if (isMakeReceipt == null)
            return "null";
        if (isMakeReceipt.equals("1"))
            return "未开票";
        if (isMakeReceipt.equals("9"))
            return "待开票";
        if (isMakeReceipt.equals("5"))
            return "开票中";
        if (isMakeReceipt.equals("6"))
            return "开票失败";
        if (isMakeReceipt.equals("2"))
            return "已开票";
        if (isMakeReceipt.equals("3"))
            return "作废发票";
        if (isMakeReceipt.equals("4"))
            return "冲红发票";
        if (isMakeReceipt.equals("10"))
            return "取消开票";
        if (isMakeReceipt.equals("20"))
            return "期初数据封存";
        return isMakeReceipt;
    }

    /**
     * 订单状态
     */
    private String getOrderStatus(String orderStatus) {
        if (orderStatus == null)
            return "null";
        if (orderStatus.equals("200"))
            return "未确认";
        if (orderStatus.equals("201"))
            return "已确认";
        if (orderStatus.equals("202"))
            return "已取消";
        if (orderStatus.equals("203"))
            return "已完成";
        if (orderStatus.equals("204"))
            return "部分缺货";
        if (orderStatus.equals("-100"))
            return "未定义";
        return orderStatus;
    }

    /**
     * 订单来源
     */
    private String getSource(Map<String, String> sourceMap, String source) {
        if (sourceMap == null || source == null)
            return source;
        return sourceMap.get(source) == null ? source : sourceMap.get(source);
    }

    /**
     * 处理省、市、区县、街道
     * 地区名称（如：北京 北京 昌平区 兴寿镇）
     */
    private void dealRegionName(InvoiceExceptionDispItem lt) {
        if (lt.getRegionName() == null || "".equals(lt.getRegionName()))
            return;
        String[] regionName = lt.getRegionName().split(" ");
        for (int i = 0; i < regionName.length; i++) {
            if (i == 0) {
                lt.setProvince(regionName[i]);
            } else if (i == 1) {
                lt.setCity(regionName[i]);
            } else if (i == 2) {
                lt.setRegion(regionName[i]);
            } else if (i == 3) {
                lt.setStreet(regionName[i]);
            }
        }

    }

    /**
     * 发票类型
     */
    private String getInvoiceType(String invoiceType) {
        if (invoiceType == null)
            return "null";
        if (invoiceType.equals("1"))
            return "增值税发票";
        if (invoiceType.equals("2"))
            return "普通发票";
        return invoiceType;
    }

    /**
     * 发票审核状态
     */
    private String getState(String state) {
        if (state == null)
            return "null";
        if (state.equals("0"))
            return "待审核";
        if (state.equals("1"))
            return "审核通过";
        if (state.equals("2"))
            return "拒绝";
        return state;
    }

    /**
     * 处理时间变成时间戳
     * @param time
     * @return
     * @throws ParseException 
     */
    public static Date timeStringToDate(String time) {
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

    /** 
     * 时间戳转换成日期格式字符串 
     * @param seconds 精确到秒的字符串 
     * @param format
     * @return 
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
//        return sdf.format(new Date(Long.valueOf(seconds + "000")));
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    //    public static void main(String[] args) {
    //        String nowDate = new Date().toString();
    //        System.out.println("InvoiceExceptionController.main()1=" + nowDate);
    //        nowDate = (timeStringToDate(nowDate).getTime() / 1000) + "";
    //        System.out.println("InvoiceExceptionController.main(2)=" + nowDate);
    //        nowDate = ((timeStringToDate(nowDate).getTime() / 1000) - SEVEN_DAYS) + "";
    //        System.out.println("InvoiceExceptionController.main(2)=" + nowDate);
    //    }

}
