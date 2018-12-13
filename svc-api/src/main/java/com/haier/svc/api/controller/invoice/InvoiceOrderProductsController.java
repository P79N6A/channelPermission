package com.haier.svc.api.controller.invoice;

import com.haier.common.util.JsonUtil;
import com.haier.invoice.util.CommUtil;
import com.haier.invoice.util.ExcelCallbackInterfaceUtil;
import com.haier.invoice.util.ExcelExportUtil;
import com.haier.shop.service.InvoiceOrderProductsService;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:Pineapple
 * @Date: 2018/5/15 11:37
 */
@Controller
@RequestMapping(value = "invoice/")
public class InvoiceOrderProductsController {

    private static final Logger logger = LogManager.getLogger(InvoiceController.class);

    /**
     * 专用网单页面跳转
     *
     * @return
     */

    @Autowired
    private InvoiceOrderProductsService        invoiceOrderProductsService;

    @RequestMapping(value = {"loadInvoiceOrderProductsListPage.html"}, method = {RequestMethod.GET})
    public String loadInvoiceOrderProductsListPage() {
        return "invoice/invoiceOrderProductsList";
    }

    /**
     * 专用网单列表
     *
     * @param
     * @param response
     * @return
     */
    @RequestMapping(value = {"getInvoiceOrderProductsList"}, method = {RequestMethod.POST})
    public void getInvoiceOrderProductsList(@RequestParam(required = false) String startDate,
                                            @RequestParam(required = false) String endDate,
                                            @RequestParam(required = false) String cPaymentStatus,
                                            @RequestParam(required = false) String status,
                                            @RequestParam(required = false) String makeReceiptType,
                                            @RequestParam(required = false) String isMakeReceipt,
                                            @RequestParam(required = false) String invoiceType,
                                            @RequestParam(required = false) String invoiceState,
                                            @RequestParam(required = false) String optype,
                                            @RequestParam(required = false) String source,
                                            @RequestParam(required = false) Integer rows,
                                            @RequestParam(required = false) Integer page,
                                            HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        try {
            if (rows == null) {
                rows = 50;
            }
            if (page == null) {
                page = 1;
            }
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("m", (page - 1) * rows);
            paramMap.put("n", rows);
            paramMap.put("startDate", startDate);
            paramMap.put("endDate", endDate);
            paramMap.put("cPaymentStatus", cPaymentStatus);
            paramMap.put("status", status);
            paramMap.put("makeReceiptType", makeReceiptType);
            paramMap.put("isMakeReceipt", isMakeReceipt);
            paramMap.put("invoiceType", invoiceType);
            paramMap.put("isMakeReceipt", isMakeReceipt);
            paramMap.put("invoiceState", invoiceState);
            paramMap.put("optype", optype);
            paramMap.put("source",source);

            List<Map<String, Object>> list = invoiceOrderProductsService.getListByParams(paramMap);
            int listCount = invoiceOrderProductsService.getCountByParams(paramMap);
            //将订单来源和发票状态转换
            if(list != null && list.size() > 0){
                for (Map<String, Object> map : list) {
                    Object sourceObj = map.get("source");
                    String sourceName = this.getSourceName((String) sourceObj);
                    map.put("sourceName",sourceName);
                    Object isMakeReceiptObj = map.get("isMakeReceipt");
                    String isMakeReceiptStutas = this.formatInvoiceStatus((Integer) isMakeReceiptObj);
                    map.put("isMakeReceiptStutas",isMakeReceiptStutas);
                }
            }
            Map<String, Object> retMap = new HashMap<String, Object>(64);
            retMap.put("total", listCount);
            retMap.put("rows", list);
            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.error("[InvoiceOrderProductsController]关联查询时发生未知错误", e);
        }
    }

    /**
     * 导出专用网单列表
     *
     * @param exportData
     * @param ids
     * @param request
     * @param response
     */
    @RequestMapping(value = {"/exportInvoiceOrderProductsList"})
    public void exportInvoiceOrderProductsList(@RequestParam(required = false) String exportData,
                                               @RequestParam(required = false) String ids,
                                               HttpServletRequest request,
                                               HttpServletResponse response) {

        Map<String, Object> params = new HashMap<>();
        Map<String, String> map = JsonUtil.fromJson(exportData);
        params.put("m", 0);
        params.put("n", 5000);
        params.put("startDate", map.get("startDate").trim());
        params.put("endDate", map.get("endDate").trim());
        params.put("cPaymentStatus", map.get("cPaymentStatus").trim());
        params.put("status", map.get("status").trim());
        params.put("makeReceiptType", map.get("makeReceiptType").trim());
        params.put("isMakeReceipt", map.get("isMakeReceipt").trim());
        params.put("invoiceType", map.get("invoiceType").trim());
        params.put("invoiceState", map.get("invoiceState").trim());
        params.put("optype", map.get("optype").trim());

        if (StringUtils.isNotBlank(ids)) {
            ids = ids.replace("[", "").replace("]", "");
            params.put("ids", ids);
        }

        //获取网单列表List
        final List<Map<String, Object>> resultList = invoiceOrderProductsService.getListByParams(params);

        String fileName = "专用网单列表报表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "数据导出";

        String[] sheetHead = new String[]{"下单时间", "订单付款状态", "开票状态","订单号", "网单库存类型", "网单编号", "商品名称",
                "订单付款方式", "订单来源", "总价", "发票类型", "发票户头", "纳税人登记号",
                "电话", "地址", "开户行名称", "开户银行账号", "发票审核状态", "销售代表"};

        try {
            ExcelExportUtil.exportEntity(request, response, fileName, sheetName, sheetHead, new ExcelCallbackInterfaceUtil() {
                @Override
                public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp) throws Exception {
                    setExcelBodyTotalForInvoiceOrderProductsList(sheet, temp, resultList);
                }
            });
        } catch (Exception e) {
            logger.error("专项网单列表导出失败", e);
            e.printStackTrace();
        }
    }

    /**
     * 导出专用网单具体数据，实现回调函数
     *
     * @param sheet
     * @param temp
     * @param list
     * @throws Exception
     */
    private void setExcelBodyTotalForInvoiceOrderProductsList(WritableSheet sheet, int temp, List<Map<String, Object>> list) throws Exception {
        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat textFormat = new WritableCellFormat(font);
        textFormat.setAlignment(Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        DisplayFormat displayFormat = NumberFormats.INTEGER;
        WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
        numberFormat.setAlignment(jxl.format.Alignment.RIGHT);
        numberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        int index = 0;
        for (Map<String, Object> map : list) {
            index++;
            /* "下单时间", "订单付款状态", "订单号", "网单库存类型", "网单编号", "商品名称",
                "订单付款方式", "订单来源", "总价", "发票类型", "发票户头", "纳税人登记号",
                "电话", "地址", "开户行名称", "开户银行账号", "发票审核状态", "销售代表"*/
            //下单时间
            sheet.setColumnView(0, 25);
            //优化格式后化时间,去掉.0
            String addTime = CommUtil.getStringValue(map.get("addTime"));
            String substringAddTime = addTime.substring(0,addTime.lastIndexOf("."));
            sheet.addCell(new Label(0, temp,substringAddTime, textFormat));
            //订单付款状态类型转换
            String cPaymentStatus = CommUtil.getStringValue(map.get("cPaymentStatus"));
            if ("200".equals(cPaymentStatus)) {
                cPaymentStatus = "未付款";
            } else if ("201".equals(cPaymentStatus)) {
                cPaymentStatus = "已付款";
            } else if ("206".equals(cPaymentStatus)) {
                cPaymentStatus = "待退款";
            } else if ("207".equals(cPaymentStatus)) {
                cPaymentStatus = "已退款";
            }
            sheet.setColumnView(1, 15);
            sheet.addCell(new Label(1, temp, cPaymentStatus, textFormat));

            //开票状态
            String isMakeReceipt = CommUtil.getStringValue(map.get("isMakeReceipt"));
            String isMakeReceiptStatus = this.formatInvoiceStatus(Integer.parseInt(isMakeReceipt));

            sheet.setColumnView(2, 15);
            sheet.addCell(new Label(2, temp, isMakeReceiptStatus, textFormat));
            //订单号
            sheet.setColumnView(3, 25);
            sheet.addCell(new Label(3, temp, CommUtil.getStringValue(map.get("orderSn")), textFormat));
            //网单库存类型
            String stockType = CommUtil.getStringValue(map.get("stockType"));
            if ("WA".equals(stockType)) {
                stockType = "WA";
            } else if ("STORE".equals(stockType)) {
                stockType = "STORE";
            } else if ("3W".equals(stockType)) {
                stockType = "3W";
            }
            sheet.setColumnView(4, 15);
            sheet.addCell(new Label(4, temp, stockType, textFormat));
            //网单编号
            sheet.setColumnView(5, 20);
            sheet.addCell(new Label(5, temp, CommUtil.getStringValue(map.get("cOrderSn")), textFormat));
            //商品名称
            sheet.setColumnView(6, 30);
            sheet.addCell(new Label(6, temp, CommUtil.getStringValue(map.get("productName")), textFormat));
            //订单付款方式
            sheet.setColumnView(7, 15);
            sheet.addCell(new Label(7, temp, CommUtil.getStringValue(map.get("paymentName")), textFormat));
            //订单来源
            String source = CommUtil.getStringValue(map.get("source"));

            String sourceName = this.getSourceName(source);
            sheet.setColumnView(8, 20);
            sheet.addCell(new Label(8, temp, sourceName, textFormat));
            //总价
            sheet.setColumnView(9, 15);
            sheet.addCell(new Label(9, temp, CommUtil.getStringValue(map.get("productAmount")), textFormat));
            //发票类型
            String type = CommUtil.getStringValue(map.get("type"));
            if ("1".equals(type)) {
                type = "增值税发票";
            } else if ("2".equals(type)) {
                type = "普通发票";
            } else if ("3".equals(type)) {
                type = "增值税发票（普）";
            }
            sheet.setColumnView(10, 15);
            sheet.addCell(new Label(10, temp, type, textFormat));
            //发票抬头
            sheet.setColumnView(11, 25);
            sheet.addCell(new Label(11, temp, CommUtil.getStringValue(map.get("invoiceTitle")), textFormat));
            //纳税人识别号
            sheet.setColumnView(12, 20);
            sheet.addCell(new Label(12, temp, CommUtil.getStringValue(map.get("taxPayerNumber")), textFormat));
            //电话
            sheet.setColumnView(13, 20);
            sheet.addCell(new Label(13, temp, CommUtil.getStringValue(map.get("registerPhone")), textFormat));
            //地址
            sheet.setColumnView(14, 20);
            sheet.addCell(new Label(14, temp, CommUtil.getStringValue(map.get("registerAddress")), textFormat));
            //开户行
            sheet.setColumnView(15, 20);
            sheet.addCell(new Label(15, temp, CommUtil.getStringValue(map.get("bankName")), textFormat));
            //银行卡号
            sheet.setColumnView(16, 20);
            sheet.addCell(new Label(16, temp, CommUtil.getStringValue(map.get("bankCardNumber")), textFormat));
            //发票审核状态
            String state = CommUtil.getStringValue(map.get("state"));
            if ("0".equals(state)) {
                state = "待审核";
            } else if ("1".equals(state)) {
                state = "审核通过";
            } else if ("2".equals(state)) {
                state = "拒绝";
            }
            sheet.setColumnView(17, 15);
            sheet.addCell(new Label(17, temp, state, textFormat));
            //销售代表
            sheet.setColumnView(18, 15);
            sheet.addCell(new Label(18, temp, CommUtil.getStringValue(map.get("sellPeople")), textFormat));
            temp++;

        }
    }

    /**
     * 获取订单来源店铺名称
     * @param source
     * @return
     */
    private String getSourceName(String source){
        if(source == null){
            return "";
        }
        String sourceName = "";
        if ("TBSC".equals(source)) {
            sourceName = "淘宝海尔官方旗舰店";
        } else if ("TOPDHSC".equals(source)) {
            sourceName = "海尔生活家电旗舰店";
        } else if ("TOPFENXIAO".equals(source)) {
            sourceName = "淘宝海尔官方旗舰店分销平台";
        } else if ("TOPFENXIAODHSC".equals(source)) {
            sourceName = "淘宝海尔生活家电旗舰店分销平台";
        } else if ("TOPBUYBANG".equals(source)) {
            sourceName = "淘宝海尔买帮专卖店";
        } else if ("TOPBOILER".equals(source)) {
            sourceName = "淘宝海尔热水器专卖店";
        } else if ("TOPSHJD".equals(source)) {
            sourceName = "淘宝海尔生活电器专卖店";
        } else if ("TOPMOBILE".equals(source)) {
            sourceName = "淘宝海尔手机专卖店";
        } else if ("TONGSHUAI".equals(source)) {
            sourceName = "统帅日日顺乐家专卖店";
        } else if ("TONGSHUAIFX".equals(source)) {
            sourceName = "统帅日日顺分销平台";
        } else if ("TOPXB".equals(source)) {
            sourceName = "海尔新宝旗舰店";
        } else if ("TOPFENXIAOXB".equals(source)) {
            sourceName = "淘宝海尔新宝旗舰店分销平台";
        } else if ("WASHER".equals(source)) {
            sourceName = "海尔洗衣机官方旗舰店";
        } else if ("FRIDGE".equals(source)) {
            sourceName = "海尔冰冷官方旗舰店";
        } else if ("AIR".equals(source)) {
            sourceName = "淘宝空调旗舰店";
        } else if ("TMMKFX".equals(source)) {
            sourceName = "天猫模卡分销";
        } else if ("GQGYS".equals(source)) {
            sourceName = "生态授权店";
        } else if ("TBCT".equals(source)) {
            sourceName = "淘宝村淘";
        } else if ("TBQYG".equals(source)) {
            sourceName = "天猫企业购";
        } else if ("TMST".equals(source)) {
            sourceName = "天猫生态";
        } else if ("FLW".equals(source)) {
            sourceName = "商城PC-返利网";
        } else if ("YHDQWZY".equals(source)) {
            sourceName = "电商平台-1号店全网自营";
        } else if ("TMKSD".equals(source)) {
            sourceName = "卡萨帝官方旗舰店";
        } else if ("TMTV".equals(source)) {
            sourceName = "天猫海尔电视";
        } else if ("TBCFDD".equals(source)) {
            sourceName = "淘宝海尔厨房大电旗舰店";
        } else if ("TBXCR".equals(source)) {
            sourceName = "天猫小超人旗舰店";
        } else if ("TMMK".equals(source)) {
            sourceName = "mooka模卡官方旗舰店";
        }
        return sourceName;
    }


    /**
     * 翻译发票状态
     */
    private String formatInvoiceStatus(Integer value) {
        if (value == null){
            return "";
        } else if (value == 1){
            return "未开票";
        } else if (value == 9){
            return "待开票";
        } else if (value == 5){
            return "开票中";
        } else if (value == 6){
            return "开票失败";
        } else if (value == 2){
            return "已开票";
        } else if (value == 3){
            return "作废发票";
        } else if (value == 4){
            return "冲红发票";
        }else if (value == 10){
            return "取消开票";
        }else if (value == 20){
            return "期初数据封存";
        }
        return value.toString();
    }


}
