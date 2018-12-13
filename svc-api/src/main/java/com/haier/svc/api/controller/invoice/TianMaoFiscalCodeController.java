package com.haier.svc.api.controller.invoice;

import com.haier.common.BusinessException;
import com.haier.common.util.JsonUtil;
import com.haier.invoice.service.InvoiceCommonService;
import com.haier.invoice.util.CommUtil;
import com.haier.invoice.util.ExcelCallbackInterfaceUtil;
import com.haier.invoice.util.ExcelExportUtil;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:JinXueqian
 * @Date: 2018/6/26 11:24
 */
@Controller
@RequestMapping("invoice/")
public class TianMaoFiscalCodeController {

    private static final Logger logger = LogManager.getLogger(TianMaoFiscalCodeController.class);

    @Autowired
    private InvoiceCommonService invoiceCommonService;

    @GetMapping(value = "loadTianMaoFiscalCondeListPage.html")
    public String loadTianMaoFiscalCondeListPage(){
        return "invoice/tianMaoFiscalCodeList";
    }

    @PostMapping(value = "getTianMaoFiscalCodeList.html")
    public void getTianMaoFiscalCodeList(@RequestParam(required = false) String corder_sn,
                                         @RequestParam(required = false) String sourceOrderSn,
                                         @RequestParam(required = false) String invoice_title,
                                         @RequestParam(required = false) String tax_payer_number,
                                         @RequestParam(required = false) String type,
                                         @RequestParam(required = false) String state,
                                         @RequestParam(required = false) String billing_time_start,
                                         @RequestParam(required = false) String billing_time_end,
                                         @RequestParam(required = false) String corder_add_time_start,
                                         @RequestParam(required = false) String corder_add_time_end,
                                         @RequestParam(required = false) Integer rows,
                                         @RequestParam(required = false) Integer page,
                                         HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        try {
            if (rows == null){
                rows = 50;
            }
            if (page == null){
                page = 1;
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("m", (page - 1) * rows);
            params.put("n", rows);
            //参数加入params里
            params.put("corder_sn", corder_sn.trim());
            params.put("sourceOrderSn", sourceOrderSn.trim());
            params.put("invoice_title", invoice_title.trim());
            params.put("tax_payer_number", tax_payer_number.trim());
            params.put("type", type);
            params.put("state", state);
            params.put("billing_time_start", billing_time_start);
            params.put("billing_time_end", billing_time_end);
            params.put("corder_add_time_start", corder_add_time_start);
            params.put("corder_add_time_end", corder_add_time_end);
            Map<String, Object> retMap = invoiceCommonService.getTianMaoFiscalCodeListByPage(params);
            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    /**
     * 开单列表导出Excel
     * @param exportData  导出数据检索条件
     * @param response
     * @return 方法执行完毕调用的画面
     *
     */
    @RequestMapping(value = { "exportTianMaoFiscalCodeList.html" })
    void exportTianMaoFiscalCodeList(@RequestParam(required = false) String exportData,
                                  HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<String, Object>();
        response.setCharacterEncoding("UTF-8");
        params.put("m", 0);
        params.put("n", 5000);
        Map<String, String> map = JsonUtil.fromJson(exportData);

        //参数加入params里
        params.put("corder_sn", map.get("corder_sn").trim());
        params.put("sourceOrderSn", map.get("sourceOrderSn").trim());
        params.put("invoice_title", map.get("invoice_title").trim());
        params.put("tax_payer_number", map.get("tax_payer_number").trim());
        params.put("type", map.get("type"));
        params.put("state", map.get("state"));
        params.put("billing_time_start", map.get("billing_time_start"));
        params.put("billing_time_end", map.get("billing_time_end"));
        params.put("corder_add_time_start", map.get("corder_add_time_start"));
        params.put("corder_add_time_end", map.get("corder_add_time_end"));

        String ids = request.getParameter("ids");
        if (StringUtils.isNotBlank(ids)) {
            ids = ids.replace("[", "").replace("]", "");
            params.put("ids", ids);
        }

        try {
            //获取开单列表List
            final List<Map<String, Object>> result = invoiceCommonService.getExportTianMaoFiscalCodeList(params);
            String fileName = "开票报表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String sheetName = "数据导出";
            String[] sheetHead = new String[] { "序号", "网单号", "客户编码", "发票抬头", "纳税人识别号", "注册地址和电话",
                    "开户银行", "发票备注", "发票收件人", "发票收件地址", "邮政编码", "收件人电话", "网单生成时间", "物料编码", "商品名称",
                    "商品分类", "计量单位", "数量", "含税价", "含税金额", "不含税单价", "不含税金额", "税额", "税率", "补贴金额", "发票类型",
                    "是否货票同行", "发票状态", "付款方式", "开票时间", "开票人", "开票状态", "发票作废时间", "首次推送开票时间",
                    "电商最后更新开票信息时间", "收货人", "地区名称", "收货地址", "手机号", "固定电话", "订单备注", "税控号码" };
            ExcelExportUtil.exportEntity(request, response, fileName, sheetName, sheetHead,
                    new ExcelCallbackInterfaceUtil() {

                        @Override
                        public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp)
                                throws Exception {
                            setExcelBodyTotalForTianMaoFiscalCode(sheet, temp, result);
                        }

                    });
        } catch (Exception e) {
            logger.error("开票列表导出失败", e);
            e.printStackTrace();
        }
    }

    @GetMapping(value = "loadFiscalCondeListPage.html")
    public String loadFiscalCondeListPage(){
        return "invoice/fiscalCodeList";
    }

    @PostMapping(value = "getFiscalCodeList.html")
    public void getFiscalCodeList(@RequestParam(required = false) String sourceOrderSn,
                                  @RequestParam(required = false) Integer rows,
                                  @RequestParam(required = false) Integer page,
                                  HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        try {
            if (rows == null){
                rows = 50;
            }
            if (page == null){
                page = 1;
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("m", (page - 1) * rows);
            params.put("n", rows);
            //参数加入params里
            params.put("sourceOrderSn", sourceOrderSn.trim());
            Map<String, Object> retMap = invoiceCommonService.getFiscalCodeListByPage(params);
            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("", e);
            throw new BusinessException("税控码查询失败" + e.getMessage());
        }
    }


    /**
     * 导出具体数据，实现回调函数
     * @param sheet
     * @param temp 行号
     * @param list 传入需要导出的 list
     */
    private void setExcelBodyTotalForTianMaoFiscalCode(WritableSheet sheet, int temp,
                                                     List<Map<String, Object>> list)
            throws Exception {

        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
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
            //jxl.write.Number(列号,行号 ,内容 )
            /* "网单号", "客户编码", "发票抬头", "纳税人识别号", "注册地址和电话", "开户银行",
             "发票备注", "发票收件人", "发票收件地址", "邮政编码", "收件人电话", "网单生成时间", "物料编码", "商品名称", "商品分类",
             "计量单位", "数量", "含税价", "含税金额", "不含税单价", "不含税金额", "税额", "税率", "补贴金额", "发票类型",
             "是否货票同行", "发票状态", "付款方式", "开票时间", "开票人", "开票状态", "发票作废时间", "首次推送开票时间",
             "电商最后更新开票信息时间", "收货人", "地区名称", "收货地址", "手机号", "固定电话", "订单备注", "税控号码"*/
            sheet.setColumnView(0, 10);
            sheet.addCell(new Label(0, temp, CommUtil.getStringValue(index), textFormat));
            sheet.setColumnView(1, 30);
            sheet.addCell(new Label(1, temp, CommUtil.getStringValue(map.get("cOrderSn")),
                    textFormat));
            sheet.setColumnView(2, 15);
            sheet.addCell(new Label(2, temp, CommUtil.getStringValue(map.get("customerCode")),
                    textFormat));
            sheet.setColumnView(3, 15);
            sheet.addCell(new Label(3, temp, CommUtil.getStringValue(map.get("invoiceTitle")),
                    textFormat));
            sheet.setColumnView(4, 15);
            sheet.addCell(new Label(4, temp, CommUtil.getStringValue(map.get("taxPayerNumber")),
                    textFormat));
            sheet.setColumnView(5, 15);
            sheet.addCell(new Label(5, temp, CommUtil.getStringValue(map
                    .get("registerAddressAndPhone")), textFormat));
            sheet.setColumnView(6, 15);
            sheet.addCell(new Label(6, temp,
                    CommUtil.getStringValue(map.get("bankNameAndAccount")), textFormat));
            sheet.setColumnView(7, 15);
            sheet
                    .addCell(new Label(7, temp, CommUtil.getStringValue(map.get("remark")), textFormat));
            sheet.setColumnView(8, 15);
            sheet.addCell(new Label(8, temp, CommUtil.getStringValue(map.get("receiptConsignee")),
                    textFormat));
            sheet.setColumnView(9, 15);
            sheet.addCell(new Label(9, temp, CommUtil.getStringValue(map.get("receiptAddress")),
                    textFormat));
            sheet.setColumnView(10, 15);
            sheet.addCell(new Label(10, temp, CommUtil.getStringValue(map.get("receiptZipcode")),
                    textFormat));
            sheet.setColumnView(11, 15);
            sheet.addCell(new Label(11, temp, CommUtil.getStringValue(map.get("receiptMobile")),
                    textFormat));
            sheet.setColumnView(12, 15);
            sheet.addCell(new Label(12, temp, CommUtil.getStringValue(map.get("cOrderAddTime")),
                    textFormat));
            sheet.setColumnView(13, 15);
            sheet.addCell(new Label(13, temp, CommUtil.getStringValue(map.get("sku")), textFormat));
            sheet.setColumnView(14, 15);
            sheet.addCell(new Label(14, temp, CommUtil.getStringValue(map.get("productName")),
                    textFormat));
            sheet.setColumnView(15, 15);
            sheet.addCell(new Label(15, temp, CommUtil.getStringValue(map.get("productCateName")),
                    textFormat));
            sheet.setColumnView(16, 15);
            sheet
                    .addCell(new Label(16, temp, CommUtil.getStringValue(map.get("unit")), textFormat));
            sheet.setColumnView(17, 15);
            sheet.addCell(new Label(17, temp, CommUtil.getStringValue(map.get("number")),
                    textFormat));
            sheet.setColumnView(18, 15);
            sheet
                    .addCell(new Label(18, temp, CommUtil.getStringValue(map.get("price")), textFormat));
            sheet.setColumnView(19, 15);
            sheet.addCell(new Label(19, temp, CommUtil.getStringValue(map.get("amount")),
                    textFormat));
            sheet.setColumnView(20, 15);
            sheet.addCell(new Label(20, temp, CommUtil.getStringValue(map.get("nonTaxPrice")),
                    textFormat));
            sheet.setColumnView(21, 15);
            sheet.addCell(new Label(21, temp, CommUtil.getStringValue(map.get("nonTaxAmount")),
                    textFormat));
            sheet.setColumnView(22, 15);
            sheet.addCell(new Label(22, temp, CommUtil.getStringValue(map.get("taxAmount")),
                    textFormat));
            sheet.setColumnView(23, 15);
            sheet.addCell(new Label(23, temp, CommUtil.getStringValue(map.get("taxRate")),
                    textFormat));
            sheet.setColumnView(24, 15);
            sheet.addCell(new Label(24, temp,
                    CommUtil.getStringValue(map.get("energySavingAmount")), textFormat));
            //发票类型转化为名称('1': '增值税发票';'2':'普通发票';)
            String type = CommUtil.getStringValue(map.get("type"));
            if ("1".equals(type)) {
                type = "增值税发票";
            } else if ("2".equals(type)) {
                type = "普通发票";
            }
            sheet.setColumnView(25, 15);
            sheet.addCell(new Label(25, temp, type, textFormat));
            //isTogether转化为名称（"是"，"否"）
            String isTogether = "1".equals(CommUtil.getStringValue(map.get("isTogether"))) ? "是"
                    : "否";
            sheet.setColumnView(26, 15);
            sheet.addCell(new Label(26, temp, isTogether, textFormat));
            //发票状态转化为名称（"0":'待开票',"1"'开票中',"4":'已开票',"5":'已取消开票'）
            String state = CommUtil.getStringValue(map.get("state"));
            if ("0".equals(state)) {
                state = "待开票";
            } else if ("1".equals(state)) {
                state = "开票中";
            } else if ("4".equals(state)) {
                state = "已开票";
            } else if ("5".equals(state)) {
                state = "已取消开票";
            }
            sheet.setColumnView(27, 15);
            sheet.addCell(new Label(27, temp, state, textFormat));
            sheet.setColumnView(28, 15);
            sheet.addCell(new Label(28, temp, CommUtil.getStringValue(map.get("paymentName")),
                    textFormat));
            sheet.setColumnView(29, 15);
            sheet.addCell(new Label(29, temp, CommUtil.getStringValue(map.get("billingTime")),
                    textFormat));
            sheet.setColumnView(30, 15);
            sheet.addCell(new Label(30, temp, CommUtil.getStringValue(map.get("drawer")),
                    textFormat));
            //开票状态转化为名称 '': '正常',  "3": '当月作废', "4":'跨月冲红'
            String eaiWriteState = CommUtil.getStringValue(map.get("eaiWriteState"));
            if ("".equals(eaiWriteState)) {
                eaiWriteState = "正常";
            } else if ("3".equals(eaiWriteState)) {
                eaiWriteState = "当月作废";
            } else if ("4".equals(eaiWriteState)) {
                eaiWriteState = "跨月冲红";
            }
            sheet.setColumnView(31, 15);
            sheet.addCell(new Label(31, temp, eaiWriteState, textFormat));
            sheet.setColumnView(32, 15);
            sheet.addCell(new Label(32, temp, CommUtil.getStringValue(map.get("invalidTime")),
                    textFormat));
            sheet.setColumnView(33, 15);
            sheet.addCell(new Label(33, temp, CommUtil.getStringValue(map.get("firstPushTime")),
                    textFormat));
            sheet.setColumnView(34, 15);
            sheet.addCell(new Label(34, temp, CommUtil.getStringValue(map.get("lastModifyTime")),
                    textFormat));
            sheet.setColumnView(35, 15);
            sheet.addCell(new Label(35, temp, CommUtil.getStringValue(map.get("consignee")),
                    textFormat));
            sheet.setColumnView(36, 15);
            sheet.addCell(new Label(36, temp, CommUtil.getStringValue(map.get("regionName")),
                    textFormat));
            sheet.setColumnView(37, 15);
            sheet.addCell(new Label(37, temp, CommUtil.getStringValue(map.get("address")),
                    textFormat));
            sheet.setColumnView(38, 15);
            sheet.addCell(new Label(38, temp, CommUtil.getStringValue(map.get("mobile")),
                    textFormat));
            sheet.setColumnView(39, 15);
            sheet
                    .addCell(new Label(39, temp, CommUtil.getStringValue(map.get("phone")), textFormat));
            sheet.setColumnView(40, 15);
            //订单备注处理
            String orderRemark = CommUtil.getStringValue(map.get("orderRemark")).replace("\n", "")
                    .replace("\t", "").replace("\r", "").replace("\r\n", "");
            sheet.addCell(new Label(40, temp, orderRemark, textFormat));
            //税控号码处理
            String[] invoiceNumbers = CommUtil.getStringValue(map.get("invoiceNumber")).split(" ");
            String invoiceNumber = "";
            for (String str : invoiceNumbers) {
                invoiceNumber += "'" + str + '\t';
            }
            sheet.setColumnView(41, 15);
            sheet.addCell(new Label(41, temp, invoiceNumber, textFormat));
            temp++;
        }
    }

}
