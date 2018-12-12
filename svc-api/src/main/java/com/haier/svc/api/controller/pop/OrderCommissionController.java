package com.haier.svc.api.controller.pop;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.distribute.data.model.TSaleProductPrice;
import com.haier.distribute.service.DistributeCenterOrderCommissionService;
import com.haier.svc.api.controller.util.ExportData;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Hwl on 2017/12/6 0006.
 */
@Controller
@RequestMapping("pop/")
public class OrderCommissionController {

    @Resource
    private DistributeCenterOrderCommissionService distributeCenterOrderCommissionService;

    /**
     * 跳转订单佣金信息
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "orderCommissionInfo", method = RequestMethod.GET)
    public String orderCommissionInfoList(Model model) {

        return "pop/distribute/commissionInfo";
    }

    @RequestMapping(value = "/findOrderCommissionInfoList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findOrderCommissionInfoList(int page, int rows, TSaleProductPrice tSaleProductPrice, HttpSession session) {
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);
        if (StringUtils.isNotEmpty(tSaleProductPrice.getFirstSureTimeS())) {
            Long date = convertStringDateToInteger(tSaleProductPrice.getFirstSureTimeS() + " 00:00:00");
            if (null != date) {
                tSaleProductPrice.setFirstConfirmTimeS(date);
            }
        }
        if (StringUtils.isNotEmpty(tSaleProductPrice.getFirstSureTimeE())) {
            Long date = convertStringDateToInteger(tSaleProductPrice.getFirstSureTimeE() + " 23:59:59");
            if (null != date) {
                tSaleProductPrice.setFirstConfirmTimeE(date);
            }
        }
        return distributeCenterOrderCommissionService.orderCommissionInfoList(pager, tSaleProductPrice);
    }

    /**
     * 导出
     *
     * @param res
     * @throws IOException
     */
    @RequestMapping(value = "/exportOrderCommissionList", method = RequestMethod.GET)
    public void exportOrderCommissionList(String sureYearMonth_e, String source_e, String ccategoryId_e,
                                          String orderNo_e, String sourceOrderNo_e,
                                          String itemName_e, String itemSku_e, String payTime_e, String payTimeEnd_e,
                                          HttpServletResponse res, String code) throws IOException {
        TSaleProductPrice condition = new TSaleProductPrice();

        if (StringUtils.isNotEmpty(payTime_e)) {
            Long date = convertStringDateToInteger(payTime_e + " 00:00:00");
            if (null != date) {
                condition.setFirstConfirmTimeS(date);
            }
        }
        if (StringUtils.isNotEmpty(payTimeEnd_e)) {
            Long date = convertStringDateToInteger(payTimeEnd_e + " 23:59:59");
            if (null != date) {
                condition.setFirstConfirmTimeE(date);
            }
        }
        condition.setSureYearMonth(sureYearMonth_e);
        condition.setSource(source_e);
        condition.setProductType(ccategoryId_e);
        condition.setOrderSn(orderNo_e);
        condition.setSourceOrderSn(sourceOrderNo_e);
        condition.setProductName(itemName_e);
        condition.setSku(itemSku_e);

        List<TSaleProductPrice> commissionList = null;
        if ("yes".equals(code)) {
            commissionList = distributeCenterOrderCommissionService.getExportData(condition);
        }

        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("订单佣金信息表");
        sheet.setColumnWidth(0, (int) (21.57 * 256));
        sheet.setColumnWidth(1, (int) 21.57 * 256);
        sheet.setColumnWidth(2, (int) 13.43 * 256);
        sheet.setColumnWidth(3, (int) 21.57 * 256);
        sheet.setColumnWidth(4, (int) 21.57 * 256);
        sheet.setColumnWidth(5, (int) 11.14 * 256);
        sheet.setColumnWidth(6, (int) 8.57 * 256);
        sheet.setColumnWidth(7, (int) 8.57 * 256);
        sheet.setColumnWidth(8, (int) 21.57 * 256);
        sheet.setColumnWidth(9, (int) 21.57 * 256);
        sheet.setColumnWidth(10, (int) 21.57 * 256);
        sheet.setColumnWidth(11, (int) 21.57 * 256);
        sheet.setColumnWidth(12, (int) 21.57 * 256);

        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        // 4.创建单元格，设置值表头，设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 居中格式
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框

        int length = ExportData.commissionListTitle.length;
        // 设置表头
        for (int i = 0; length - 1 >= i; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(ExportData.commissionListTitle[i]);
            cell.setCellStyle(style);
        }
        //向单元格里添加数据
        if (commissionList != null) {
            for (short i = 0; i < commissionList.size(); i++) {
                BigDecimal policy;
                BigDecimal number;
                BigDecimal price;
                String n;

                policy = commissionList.get(i).getMonthPolicy() == null ? new BigDecimal(0) : commissionList.get(i).getMonthPolicy();
                price = commissionList.get(i).getPrice() == null ? new BigDecimal(0) : commissionList.get(i).getPrice();
                n = commissionList.get(i).getNumber() == null ? "0" : commissionList.get(i).getNumber();
                number = new BigDecimal(n) == null ? new BigDecimal(0) : new BigDecimal(n);
                commissionList.get(i).setpAmount(price.multiply(number));

                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(commissionList.get(i).getOrderSn());
                row.createCell(1).setCellValue(commissionList.get(i).getSourceOrderSn());
                row.createCell(2).setCellValue(commissionList.get(i).getSource());
                row.createCell(3).setCellValue(commissionList.get(i).getProductName());
                row.createCell(4).setCellValue(commissionList.get(i).getSku());
                row.createCell(5).setCellValue(commissionList.get(i).getTypeName());
                row.createCell(6).setCellValue(commissionList.get(i).getPrice().doubleValue());
                row.createCell(7).setCellValue(commissionList.get(i).getNumber());
                row.createCell(8).setCellValue(commissionList.get(i).getpAmount().doubleValue());
                BigDecimal a;
                BigDecimal b;
                BigDecimal c;
                a = commissionList.get(i).getSupplyPrice() == null ? new BigDecimal(0) : commissionList.get(i).getSupplyPrice();
                b = commissionList.get(i).getSupplyAmount() == null ? new BigDecimal(0) : commissionList.get(i).getSupplyAmount();
                c = commissionList.get(i).getCommission() == null ? new BigDecimal(0) : commissionList.get(i).getCommission();

                row.createCell(9).setCellValue(String.valueOf(a.doubleValue() == 0 ? "" : a.doubleValue()));
                row.createCell(10).setCellValue(String.valueOf(policy.doubleValue() == 0 ? "" : policy.doubleValue()));
                row.createCell(11).setCellValue(String.valueOf(b.doubleValue() == 0 ? "" : b.doubleValue()));
                row.createCell(12).setCellValue(String.valueOf(c.doubleValue() == 0 ? "" : c.doubleValue()));
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String str = sdf.format(date);
        String fileName = "订单佣金信息" + str;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        ExportExcelUtil.exportCommon(is, fileName, res);
    }

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
