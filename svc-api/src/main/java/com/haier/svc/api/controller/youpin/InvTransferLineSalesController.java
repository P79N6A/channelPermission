package com.haier.svc.api.controller.youpin;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.model.InvTransferLineSales;
import com.haier.shop.model.InvTransferLineSalesVo;
import com.haier.shop.service.InvTransferLineSalesService;
import com.haier.svc.api.controller.util.ExcelCallbackInterfaceUtil;
import com.haier.svc.api.controller.util.ExcelExportUtil;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import jxl.write.Number;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author xiaojian
 * 2018/08/20
 * 优品出库开票列表
 */


@Controller
@RequestMapping(value ="InvTransferLineSales")
public class InvTransferLineSalesController {

    @Autowired
    private InvTransferLineSalesService invTransferLineSalesService;

    /**
     * 页面跳转
     * @return 优品出库开票列表查询页面
     */
    @RequestMapping("/InvTransferLineSalesList")
    public String showCommissionList() {

        return "youpin/InvTransferLineSalesList";
    }

    /**
     * 分页查询 InvTransferLineSales
     * @param page
     * @param rows
     * @param vo
     * @return
     */

    @RequestMapping(value = "/searchList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject searchList(int page, int rows, InvTransferLineSalesVo vo){
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);
        List<InvTransferLineSales> listByVo = invTransferLineSalesService.findListByVo(vo, pager.getStart(), pager.getPageSize());
        int total = invTransferLineSalesService.getPagerCountS(vo);
        JSONArray res = new JSONArray();
        if (listByVo != null) {
            for (InvTransferLineSales o : listByVo) {
                InvTransferLineSales dto = o;
                JSONObject json = new JSONObject();
                json.put("id", dto.getId());
                json.put("lineNum", dto.getLineNum());
                json.put("soLineNum", dto.getSoLineNum());
                json.put("createTime", dto.getCreateTime());
                json.put("itemCode", dto.getItemCode());
                json.put("secTo", dto.getSecTo());
                json.put("transferQty", dto.getTransferQty());
                json.put("salesAmounts", dto.getSalesAmounts());
                json.put("invoiceState", dto.getInvoiceState());
                json.put("saleOutState", dto.getSaleOutState());
                res.add(json);
            }
        }
        return jsonResult(res,total);
    }

    /**
     * 导出excel InvTransferLineSales 前5000条
     * @param vo
     * @param request
     * @param response
     */
    @PostMapping("exportListByVo")
    public void  exportListByVo(InvTransferLineSalesVo vo, HttpServletRequest request, HttpServletResponse response){

        List<InvTransferLineSales> invTransferLineSales = invTransferLineSalesService.exportListByVo(vo);

        String fileName = "优品出库开票列表导出" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "优品出库开票列表";

        String[] sheetHead = new String[]{
                "序号",
                "调拨网单号",
                "销售网单号",
                "创建时间",
                "物料编码",
                "调入库位",
                "调货数量",
                "开票额",
                "开票状态",
                "sap队列状态",
        };
        try {
            ExcelExportUtil.exportEntity(null, request, response, fileName, sheetName, sheetHead,
                    new ExcelCallbackInterfaceUtil() {

                        @Override
                        public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp) throws Exception {
                            setExportinvTransferLineSalesList(sheet, temp, invTransferLineSales);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 内部方法用于导出excel
     * @param sheet
     * @param temp
     * @param list
     * @throws Exception
     */

    private void setExportinvTransferLineSalesList(WritableSheet sheet, int temp,
                                          List<InvTransferLineSales> list) throws Exception {
        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat textFormat = new WritableCellFormat(font);
        textFormat.setAlignment(Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        DisplayFormat displayFormat = NumberFormats.INTEGER;
        WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
        numberFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        //数字类型 addCell new Number
        NumberFormat nf = new NumberFormat("#0.00");
        WritableCellFormat numOutFormat = new WritableCellFormat(font, nf);
        numOutFormat.setAlignment(Alignment.CENTRE);
        numOutFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        NumberFormat nfint = new NumberFormat("#0");
        WritableCellFormat numOutFormatInt = new WritableCellFormat(font, nfint);
        numOutFormatInt.setAlignment(Alignment.CENTRE);
        numOutFormatInt.setBorder(Border.ALL, BorderLineStyle.THIN);

        int index = 0;
        for (InvTransferLineSales invTransferLineSales : list) {

            index++;
            int i = 0;
            //序号
            sheet.setColumnView(0, 10);
            sheet.addCell(new Number(0, temp, getIntValue(index), numOutFormatInt));
            //调拨网单号
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(invTransferLineSales.getLineNum()), textFormat));
            //销售网单号
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(invTransferLineSales.getSoLineNum()), textFormat));
            //创建时间
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getDateValue(invTransferLineSales.getCreateTime()), textFormat));
            //物料编码
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(invTransferLineSales.getItemCode()), textFormat));
            //调入库位
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(invTransferLineSales.getSecTo()), textFormat));
            //调货数量
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Number(i, temp, getIntValue(invTransferLineSales.getTransferQty()), numOutFormatInt));
            //开票额
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Number(i, temp, getDoubleValue(invTransferLineSales.getSalesAmounts()), numOutFormat));
            //开票状态
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringInvoiceState(invTransferLineSales.getInvoiceState()), textFormat));
            //sap队列状态
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringSaleOutState(invTransferLineSales.getSaleOutState()), textFormat));

            temp++;
        }
    }

    private static String getStringInvoiceState(int value){

        if (value == 0) {
            return "待开票";
        }else if (value == 1){
            return "开票中";
        }else if (value == 2){
            return "开票成功";
        }else {
            return getStringValue(value);
        }

    }

    private static String getStringSaleOutState(int value){
        if (value == 0) {
            return "待推送";
        }else if (value == 1){
            return "推送中";
        }else if (value == 2){
            return "推送成功";
        }else {
            return getStringValue(value);
        }
    }

    private static Double getDoubleValue(Object obj) {
        if (obj == null)
            return 0.00;

        return Double.parseDouble(String.valueOf(obj));

    }

    private static int getIntValue(Object obj) {
        if (obj == null)
            return 0;

        return Integer.parseInt(String.valueOf(obj));

    }

    private static String getStringValue(Object obj) {
        if (obj == null)
            return "";
        return String.valueOf(obj);
    }

    private static String getDateValue(Object obj){
        if (obj == null)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(obj);
    }

    private <T> JSONObject jsonResult(List<T> list, long total) {
        JSONObject json = new JSONObject();
        json.put("total", total);
        if (list == null || list.isEmpty()) {
            json.put("rows", new ArrayList<T>());
        } else {
            json.put("rows", list);
        }
        return json;
    }
}
