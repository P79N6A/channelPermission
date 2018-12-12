package com.haier.svc.api.controller.pop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.haier.distribute.service.DistributeCenterAdjustService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.jsondoc.core.annotation.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.util.JsonUtil;
import com.haier.distribute.data.model.TAdjustData;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import com.haier.svc.api.controller.util.PopExportData;

@Controller
@Api(name = "分销订单", description = "AdjustQQController")
@RequestMapping(value = "adjust/")
public class AdjustQQController {

    @Autowired
    private DistributeCenterAdjustService distributeCenterAdjustService;

    /**
     * 跳转数据核算页面
     *
     * @param orderId
     * @param tempFlag
     * @param model
     * @return
     */
    @RequestMapping("/orderList")
    public String deliveryOrder(String orderId, String tempFlag, Model model) {
        if (StringUtils.isNotBlank(tempFlag)) {
            model.addAttribute("tempFlag", tempFlag);
        }
        return "pop/adjust_qq/checkList";
    }

    /**
     * 加载数据核算列表
     *
     * @param page
     * @param rows
     * @param condition
     * @return
     */
    @RequestMapping("/findDistributeOrderList")
    @ResponseBody
    public JSONObject findDistributeOrderList(int page, int rows, TAdjustData condition) {
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);

        String attYearMonth = condition.getAttYearMonth();
        if (StringUtils.isNotEmpty(attYearMonth)) {

            condition.setPeriod(Integer.parseInt(attYearMonth.substring(5)));//期间
            condition.setYear(Integer.parseInt(attYearMonth.substring(0, 4)));//年度
        }

        //时间条件参数string转date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isNotEmpty(condition.getcPayTimeStart())) {
            Date date1;
            try {
                date1 = sdf.parse(condition.getcPayTimeStart() + " 00:00:00");
                if (null != date1) {
                    condition.setPayTimeStart(date1);//付款时间开始
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        if (StringUtils.isNotEmpty(condition.getcPayTimeEnd())) {
            Date date2;
            try {
                date2 = sdf.parse(condition.getcPayTimeEnd() + " 23:59:59");
                if (null != date2) {
                    condition.setPayTimeEnd(date2);//付款时间结束
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        if (StringUtils.isNotEmpty(condition.getInvoiceTimeStart())) {
            Date date3;
            try {
                date3 = sdf.parse(condition.getInvoiceTimeStart() + " 00:00:00");
                if (null != date3) {
                    condition.setFpTimeStart(date3);
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        if (StringUtils.isNotEmpty(condition.getInvoiceTimeEnd())) {
            Date date4;
            try {
                date4 = sdf.parse(condition.getInvoiceTimeEnd() + " 23:59:59");
                if (null != date4) {
                    condition.setFpTimeEnd(date4);
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return distributeCenterAdjustService.productList(pager, condition);
    }

    /**
     * 审核数据
     *
     * @param id
     * @param type
     * @return
     */
    @RequestMapping(value = "update", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String update(String id, String type) {

        return JsonUtil.toJson(distributeCenterAdjustService.update(id, type));
    }

    /**
     * 导出
     *
     * @param att_YearMonth
     * @param c_PayTimeStart
     * @param c_PayTimeEnd
     * @param settle_Type
     * @param sell_People
     * @param data_Status
     * @param invoice_TimeStart
     * @param invoice_TimeEnd
     * @param res
     * @throws IOException
     */
    @RequestMapping(value = "/exportCheckList", method = RequestMethod.GET)
    public void exportOrderList(String att_YearMonth, String c_PayTimeStart, String c_PayTimeEnd, String settle_Type,
                                String sell_People, String data_Status, String invoice_TimeStart, String invoice_TimeEnd, String code, HttpServletResponse res) throws IOException {
        TAdjustData condition = new TAdjustData();
        if (StringUtils.isNotEmpty(att_YearMonth)) {
            condition.setPeriod(Integer.parseInt(att_YearMonth.substring(5)));//期间
            condition.setYear(Integer.parseInt(att_YearMonth.substring(0, 4)));//年度
        }

        //时间条件参数string转date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isNotEmpty(c_PayTimeStart)) {
            Date date1;
            try {
                date1 = sdf.parse(c_PayTimeStart + " 00:00:00");
                if (null != date1) {
                    condition.setPayTimeStart(date1);//付款时间开始
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (StringUtils.isNotEmpty(c_PayTimeEnd)) {
            Date date2;
            try {
                date2 = sdf.parse(c_PayTimeEnd + " 23:59:59");
                if (null != date2) {
                    condition.setPayTimeEnd(date2);//付款时间结束
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (StringUtils.isNotEmpty(invoice_TimeStart)) {
            Date date3;
            try {
                date3 = sdf.parse(invoice_TimeStart + " 00:00:00");
                if (null != date3) {
                    condition.setFpTimeStart(date3);
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (StringUtils.isNotEmpty(invoice_TimeEnd)) {
            Date date4;
            try {
                date4 = sdf.parse(invoice_TimeEnd + " 23:59:59");
                if (null != date4) {
                    condition.setFpTimeEnd(date4);
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        condition.setSettleType(settle_Type);
        condition.setSellPeople(sell_People);
        condition.setDataStatus(data_Status);
        List<TAdjustData> checkList = null;
        if ("yes".equals(code)) {
            checkList = distributeCenterAdjustService.exportAdjustList(condition);
        }

        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("核算表");
        sheet.setColumnWidth(0, (int) (5.57 * 256));
        sheet.setColumnWidth(1, (int) 21.57 * 256);
        sheet.setColumnWidth(2, (int) 21.57 * 256);
        sheet.setColumnWidth(3, (int) 21.57 * 256);
        sheet.setColumnWidth(4, (int) 15.57 * 256);
        sheet.setColumnWidth(5, (int) 15.14 * 256);
        sheet.setColumnWidth(6, (int) 10.57 * 256);
        sheet.setColumnWidth(7, (int) 27.57 * 256);
        sheet.setColumnWidth(8, (int) 9.57 * 256);
        sheet.setColumnWidth(9, 9 * 256);
        sheet.setColumnWidth(10, 9 * 256);
        sheet.setColumnWidth(11, (int) 16.57 * 256);
        sheet.setColumnWidth(12, 9 * 256);
        sheet.setColumnWidth(13, 17 * 256);
        sheet.setColumnWidth(14, 9 * 256);
        sheet.setColumnWidth(15, 9 * 256);
        sheet.setColumnWidth(16, 9 * 256);
        sheet.setColumnWidth(17, 19 * 256);
        sheet.setColumnWidth(18, 9 * 256);
        sheet.setColumnWidth(19, 9 * 256);
        sheet.setColumnWidth(20, 9 * 256);
        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        // 4.创建单元格，设置值表头，设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        int length = PopExportData.checkListTitle.length;
        // 设置表头
        for (int i = 0; length - 1 >= i; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(PopExportData.checkListTitle[i]);
            cell.setCellStyle(style);
        }
        //向单元格里添加数据
        String field1 = "";
        String field2 = "";
        if (checkList != null) {
            for (short i = 0; i < checkList.size(); i++) {
                if ("1".equals(checkList.get(i).getInvoiceStatus())) {
                    field1 = "已开票";
                } else if ("2".equals(checkList.get(i).getInvoiceStatus())) {
                    field1 = "当月作废";
                } else if ("3".equals(checkList.get(i).getInvoiceStatus())) {
                    field1 = "跨月冲红";
                }
                //状态dataStatus
                if ("1".equals(checkList.get(i).getDataStatus())) {
                    field2 = "已开票";
                } else if ("2".equals(checkList.get(i).getDataStatus())) {
                    field2 = "作废/冲红";
                }

                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue((i + 1));  //序号
                row.createCell(1).setCellValue(checkList.get(i).getcOrderSn());  //网单号
                row.createCell(2).setCellValue(checkList.get(i).getcOrderSnOld());  //原网单号
                row.createCell(3).setCellValue(checkList.get(i).getSourceOrderSn());  //来源订单编号
                row.createCell(4).setCellValue(checkList.get(i).getChannelName());//  订单来源
                row.createCell(5).setCellValue(checkList.get(i).getSellPeople());  //销售代表
                row.createCell(6).setCellValue(checkList.get(i).getCategory());  //品类
                row.createCell(7).setCellValue(checkList.get(i).getBrand());  //品牌
                row.createCell(8).setCellValue(checkList.get(i).getSku());  //sku
                row.createCell(9).setCellValue(checkList.get(i).getProductName());  //宝贝型号

                row.createCell(10).setCellValue(checkList.get(i).getConsignee());//收货人姓名
                row.createCell(11).setCellValue(sdf.format(checkList.get(i).getcPayTime()));  //订单付款时间
                row.createCell(12).setCellValue(checkList.get(i).getNumber());  //销售数量
                row.createCell(13).setCellValue(checkList.get(i).getProductAmount().doubleValue());  //总价（发票金额）
                row.createCell(14).setCellValue((checkList.get(i).getProductAmount().multiply(new BigDecimal(checkList.get(i).getNumber()))).doubleValue());  //网单金额
                row.createCell(15).setCellValue(checkList.get(i).getcOrderStatus());  //网单状态
                row.createCell(16).setCellValue(checkList.get(i).getPeriod());  //期间
                row.createCell(17).setCellValue(checkList.get(i).getYear());// 年度
                row.createCell(18).setCellValue(sdf.format(checkList.get(i).getInvoiceTime()));  //发票时间
                row.createCell(19).setCellValue(field1);  //开票状态
                row.createCell(20).setCellValue(field2);  //状态

            }
        }

        java.util.Date date = new java.util.Date();
        String str = sdf.format(date);
        String fileName = "核算表" + str;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        ExportExcelUtil.exportCommon(is, fileName, res);
    }

    @SuppressWarnings("unused")
    private Long convertStringDateToInteger(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = formatter.parse(strDate);
            return date.getTime() / 1000;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
