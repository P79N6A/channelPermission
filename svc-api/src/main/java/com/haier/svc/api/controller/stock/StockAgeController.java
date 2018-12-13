package com.haier.svc.api.controller.stock;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.stock.model.InvStockAge;
import com.haier.stock.model.InvStockAge.StockAgeData;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.StockAgeHandler;
import com.haier.stock.model.StockAgeWapped;
import com.haier.svc.api.controller.stock.mode.StockAgeModel;
import com.haier.svc.api.controller.util.HttpJsonResult;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.PaperSize;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.BLUE;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "stock/")
public class StockAgeController {
    private Logger logger = LogManager.getLogger(StockAgeController.class);
    @Autowired
    private StockAgeModel stockAgeModel;
    @RequestMapping(value = { "/getStockAgeForChannel" }, method = { RequestMethod.GET })
    public String getStockAgeForChannel() {
        return "stock/stockAgeForChannels";
    }
    @RequestMapping(value = { "countStockAgesWithChannelsBySku.html" }, method = { RequestMethod.GET })
    ModelAndView countStockAgesWithChannelsBySku(HttpServletRequest request,
        @RequestParam(required = false) String date,
        @RequestParam(required = false) String param,
        @RequestParam(required = false) String title) {
        ModelAndView mv  = new ModelAndView();
        mv.addObject("shijian",date);
        mv.addObject("channel",param);
        mv.addObject("title",title + " WA库存-到渠道");
        mv.addObject("onQueryUrl","/stock/getStockAgeForProductsListWithChannel.html");
        mv.addObject("onExportUrl","/stock/exportStockAgeForProductsListWithChannel.html");
        mv.addObject("onQuery",true);
        mv.addObject("select",3);

        mv.setViewName("stock/stockAgeForCount");
        return mv;
    }
    @RequestMapping(value = { "findStockAgeGroupByChannel.html" }, method = { RequestMethod.POST })
    public void findGroupByChannel(@RequestParam(required = false) String date,String param,
        HttpServletResponse response, HttpServletRequest request){
        try {
            Date clearDate = DateUtil.parse(date, "yyyy-MM-dd");
            if (clearDate == null){
                clearDate = DateUtil.currentDate();
            }
            List<StockAgeWapped> countStockGroupByChannel = stockAgeModel
                .countStockGroupByChannel(clearDate);
            List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
            for (StockAgeWapped stockAgeWapped : countStockGroupByChannel) {
                Map<String, Object> retMap = new HashMap<String, Object>();
                retMap.put("channel", stockAgeWapped.getStockAge().getChannelCode());
                retMap.put("channelName", stockAgeWapped.getStockAge().getChannelName());
                for (InvStockAge.StockAgeData ageData : stockAgeWapped.getAgeDatas()) {
                    retMap.put("a" + String.valueOf(ageData.getAge()), ageData);
                    retMap.put("aa" + String.valueOf(ageData.getAge()), ageData);
                }
                retList.add(retMap);
            }

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("total", retList.size());
            map.put("rows", retList);
            response.setHeader("contentType", "text/html; charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JsonUtil.toJson(map));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = { "/exportStockAgeForChannelsList.html" }, method = { RequestMethod.GET })
    void exportStockAgeForChannels(HttpServletResponse response,
        @RequestParam(required = false) String date){

        Date clearDate = DateUtil.parse(date, "yyyy-MM-dd");

        if (clearDate == null) {
            clearDate = DateUtil.currentDate();
        }
        List<StockAgeWapped> countStockGroupByChannel = stockAgeModel
            .countStockGroupByChannel(clearDate);

        ServletOutputStream os = null;
        try {
            try {
                os = response.getOutputStream();
                response.reset();

                String fileName = "WA库存-到渠道（" + DateUtil.format(clearDate, "yyyy-MM-dd") + ")";
                response.setHeader("Content-Disposition", "attachment; filename="
                    + new String((fileName+ ".xls").getBytes("gb2312"),
                    "iso-8859-1") );
                response.setContentType("application/octet-stream; charset=utf-8");
                exportStockAgeForChannels("WA库存-到渠道", clearDate, countStockGroupByChannel, os);
            } catch (Exception e) {
                response.reset();
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print("导出excel失败，" + e);
                response.getWriter().flush();
                logger.error("导出excel失败:", e);
            } finally {
                if (os != null){
                    os.close();
                }
            }
        } catch (IOException e) {
            logger.error("导出excel失败:", e);
        }
    }
    private void exportStockAgeForChannels(String title, Date clearDate,
        List<StockAgeWapped> datas, OutputStream os)
        throws Exception{
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        WritableSheet sheet = workbook.createSheet("库龄统计", 0);
        sheet.getSettings().setPaperSize(PaperSize.A2);//设置纸张大小
        sheet.getSettings().setFitHeight(297);
        sheet.getSettings().setFitWidth(21);
        sheet.getSettings().setHorizontalCentre(true);

        //标题
        WritableFont font = new WritableFont(WritableFont.ARIAL, 13, WritableFont.BOLD, false,
            UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);
        WritableCellFormat format = new WritableCellFormat(font);
        format.setAlignment(Alignment.LEFT);
        Label l_title = new Label(0, 0, title, format);
        sheet.setColumnView(0, 25);
        sheet.addCell(l_title);
        sheet.mergeCells(0, 0, 24, 0);

        WritableFont fontParam = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
            UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);
        WritableCellFormat formatParam = new WritableCellFormat(fontParam);
        //参数
        Label params = new Label(0, 2, "报表日期：" + DateUtil.format(clearDate, "yyyy-MM-dd")
            + " 金额单位：（人民币）万元 数据类型：正品", formatParam);
        sheet.setColumnView(0, 25);
        sheet.addCell(params);

        int temp = 4;
        String[] headers = new String[] { "渠道", "1-7天", "8-14天", "15-21天", "22-30天", "31-44天",
            "45-60天", "61-75天", "75-90天", "3-4月", "4-6月", "6-12月", "1年以上", "正常库龄小计", "超期库龄小计",
            "合计", "超期占比" };
        WritableFont fontHeader = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD,
            false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);
        WritableCellFormat formatHeader = new WritableCellFormat(fontHeader);
        formatHeader.setAlignment(Alignment.CENTRE);
        formatHeader.setVerticalAlignment(VerticalAlignment.CENTRE);
        formatHeader.setBorder(Border.ALL, BorderLineStyle.THIN);
        int count = 0;
        Label label = new Label(count, temp, headers[0], formatHeader);
        sheet.setColumnView(count, 12);
        sheet.addCell(label);
        sheet.mergeCells(count, temp, count, temp + 1);
        count++;

        for (int col = 1; col < headers.length; col++) {
            Label _label = new Label(count, temp, headers[col], formatHeader);
            sheet.setColumnView(count, 16);
            sheet.addCell(_label);

            sheet.mergeCells(count, temp, count + 1, temp);

            Label label2 = new Label(count, temp + 1, "数量", formatHeader);
            sheet.setColumnView(count, 8);
            sheet.addCell(label2);

            count++;

            Label label3 = new Label(count, temp + 1, "金额", formatHeader);
            sheet.setColumnView(count, 8);
            sheet.addCell(label3);
            count++;
        }

        //数据
        temp = 6;
        WritableFont font1 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
            UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        NumberFormat nf = new NumberFormat("#,##0");
        NumberFormat _nf = new NumberFormat("#,##0.00");
        WritableCellFormat format1 = new WritableCellFormat(font1, nf);
        format1.setAlignment(Alignment.RIGHT);
        format1.setBorder(Border.ALL, BorderLineStyle.THIN);
        WritableCellFormat _format1 = new WritableCellFormat(font1, _nf);
        _format1.setAlignment(Alignment.RIGHT);
        _format1.setBorder(Border.ALL, BorderLineStyle.THIN);

        WritableCellFormat formatP = new WritableCellFormat(font1, NumberFormats.PERCENT_INTEGER);
        formatP.setAlignment(Alignment.RIGHT);
        formatP.setBorder(Border.ALL, BorderLineStyle.THIN);

        WritableCellFormat format2 = new WritableCellFormat(font1);
        format2.setAlignment(Alignment.CENTRE);
        format2.setBorder(Border.ALL, BorderLineStyle.THIN);

        WritableCellFormat format3 = new WritableCellFormat(font1, _nf);
        format3.setAlignment(Alignment.RIGHT);
        format3.setBorder(Border.RIGHT, BorderLineStyle.THICK, Colour.RED);
        format3.setBorder(Border.LEFT, BorderLineStyle.THIN);
        format3.setBorder(Border.TOP, BorderLineStyle.THIN);
        format3.setBorder(Border.BOTTOM, BorderLineStyle.THIN);

        int m = 0;
        for (StockAgeWapped ageWapped : datas) {
            int j = 0;

            sheet.setColumnView(j, 12);
            String channelName = ageWapped.getStockAge().getChannelName();
            if (m == 0){
                channelName = "合计";
            }
            m++;

            sheet.addCell(new Label(j++, temp, channelName, format2));

            List<InvStockAge.StockAgeData> agedatas = ageWapped.getAgeDatas();

            int n = 0;

            for (InvStockAge.StockAgeData ageData : agedatas) {
                sheet.setColumnView(j, 8);

                double stockQuantity = ageData.getStockQuantity();
                double value = ageData.getValue().doubleValue();
                if (ageData.getAge() == -1004) {
                    stockQuantity = stockQuantity / 100;
                    value = value / 100;
                    sheet.addCell(new Number(j++, temp, stockQuantity, formatP));
                } else {
                    sheet.addCell(new Number(j++, temp, stockQuantity, format1));
                }
                sheet.setColumnView(j, 8);
                if (n == 5) {
                    sheet.addCell(new Number(j++, temp, value, format3));
                }else {
                    if (ageData.getAge() == -1004) {
                        sheet.addCell(new Number(j++, temp, value, formatP));
                    }else {
                        sheet.addCell(new Number(j++, temp, value, _format1));
                    }
                }
                n++;
            }
            temp++;

        }

        workbook.write();
        workbook.close();
    }
    @RequestMapping(value = { "/getStockAgeForProductsListWithChannel.html" }, method = { RequestMethod.GET })
    void getStockAgeForProductWithChannel(Map<String, Object> modelMap,HttpServletResponse response,
        @RequestParam(required = false) String date,
        @RequestParam(required = false) String channel) throws Exception{
        Date clearDate = DateUtil.parse(date, "yyyy-MM-dd");

        if (clearDate == null) {
            clearDate = DateUtil.currentDate();
        }
        Map<String, List<StockAgeWapped>> datas = stockAgeModel.countStockGroupBySkuWithChannel(
            clearDate, channel);
        modelMap.put("total", datas.remove("total").get(0));
        modelMap.put("datas", datas);
        modelMap.put("show", "1");
        response.setHeader("contentType", "text/html; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JsonUtil.toJson(modelMap));
        response.getWriter().flush();
        response.getWriter().close();
    }
    @RequestMapping(value = { "/exportStockAgeForProductsListWithChannel.html" }, method = { RequestMethod.GET })
    void exportStockAgeForProductWithChannel(HttpServletResponse response, String date, String channel){

        Date clearDate = DateUtil.parse(date, "yyyy-MM-dd");

        if (clearDate == null) {
            clearDate = DateUtil.currentDate();
        }
        Map<String, List<StockAgeWapped>> datas = stockAgeModel.countStockGroupBySkuWithChannel(
            clearDate, channel);

        ServletOutputStream os = null;
        try {
            try {
                os = response.getOutputStream();
                response.reset();

                String channelName = "";
                List<InvStockChannel> channels = stockAgeModel.getChannels();
                for (InvStockChannel _channel : channels) {
                    if (channel.equalsIgnoreCase(_channel.getChannelCode())) {
                        channelName = _channel.getName();
                        break;
                    }
                }

                String fileName = channelName + "WA库存-到产品（"
                    + DateUtil.format(clearDate, "yyyy-MM-dd") + ")";

                response.setHeader("Content-Disposition", "attachment; filename="
                    + new String((fileName+".xls").getBytes("gb2312"),
                    "iso-8859-1"));

                response.setContentType("application/octet-stream; charset=utf-8");
                exportStockAgeForProducts(channelName + " WA库存-到产品", clearDate, datas, os);
            } catch (Exception e) {
                response.reset();
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print("导出excel失败，" + e);
                response.getWriter().flush();
                logger.error("导出excel失败:", e);
            } finally {
                if (os != null){
                    os.close();
                }
            }
        } catch (IOException e) {
            logger.error("导出excel失败:", e);
        }
    }
    private void exportStockAgeForProducts(String title, Date clearDate,
        Map<String, List<StockAgeWapped>> datas, OutputStream os)
        throws Exception{
        WritableWorkbook workbook = Workbook.createWorkbook(os);
        WritableSheet sheet = workbook.createSheet("库龄统计", 0);
        sheet.getSettings().setPaperSize(PaperSize.A2);//设置纸张大小
        sheet.getSettings().setFitHeight(297);
        sheet.getSettings().setFitWidth(21);
        sheet.getSettings().setHorizontalCentre(true);

        //标题
        WritableFont font = new WritableFont(WritableFont.ARIAL, 13, WritableFont.BOLD, false,
            UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);
        WritableCellFormat format = new WritableCellFormat(font);
        format.setAlignment(Alignment.LEFT);
        Label l_title = new Label(0, 0, title, format);
        sheet.setColumnView(0, 25);
        sheet.addCell(l_title);
        sheet.mergeCells(0, 0, 25, 0);

        WritableFont fontParam = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
            UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);
        WritableCellFormat formatParam = new WritableCellFormat(fontParam);
        //参数
        Label params = new Label(0, 2, "报表日期：" + DateUtil.format(clearDate, "yyyy-MM-dd")
            + " 金额单位：（人民币）万元 数据类型：正品", formatParam);
        sheet.setColumnView(0, 25);
        sheet.addCell(params);

        int temp = 4;
        String[] headers = new String[] { "品类", "产品组", "1-7天", "8-14天", "15-21天", "22-30天",
            "31-44天", "45-60天", "61-75天", "75-90天", "3-4月", "4-6月", "6-12月", "1年以上", "正常库龄小计",
            "超期库龄小计", "总计", "超期占比" };
        WritableFont fontHeader = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD,
            false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);
        WritableCellFormat formatHeader = new WritableCellFormat(fontHeader);
        formatHeader.setAlignment(Alignment.CENTRE);
        formatHeader.setVerticalAlignment(VerticalAlignment.CENTRE);
        formatHeader.setBorder(Border.ALL, BorderLineStyle.THIN);
        int count = 0;
        for (int i = 0; i < 2; i++) {
            Label label = new Label(count, temp, headers[i], formatHeader);
            sheet.setColumnView(count, 14);
            sheet.addCell(label);
            sheet.mergeCells(count, temp, count, temp + 1);
            count++;
        }

        for (int col = 2; col < headers.length; col++) {
            Label _label = new Label(count, temp, headers[col], formatHeader);
            sheet.setColumnView(count, 16);
            sheet.addCell(_label);

            sheet.mergeCells(count, temp, count + 1, temp);

            Label label2 = new Label(count, temp + 1, "数量", formatHeader);
            sheet.setColumnView(count, 8);
            sheet.addCell(label2);

            count++;

            Label label3 = new Label(count, temp + 1, "金额", formatHeader);
            sheet.setColumnView(count, 8);
            sheet.addCell(label3);
            count++;
        }

        //数据
        temp = 6;
        WritableFont font1 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
            UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        NumberFormat nf = new NumberFormat("#,##0");
        NumberFormat _nf = new NumberFormat("#,##0.00");
        WritableCellFormat format1 = new WritableCellFormat(font1, nf);
        format1.setAlignment(Alignment.RIGHT);
        format1.setBorder(Border.ALL, BorderLineStyle.THIN);
        WritableCellFormat _format1 = new WritableCellFormat(font1, _nf);
        _format1.setAlignment(Alignment.RIGHT);
        _format1.setBorder(Border.ALL, BorderLineStyle.THIN);

        WritableCellFormat formatP = new WritableCellFormat(font1, NumberFormats.PERCENT_INTEGER);
        formatP.setAlignment(Alignment.RIGHT);
        formatP.setBorder(Border.ALL, BorderLineStyle.THIN);

        WritableCellFormat format2 = new WritableCellFormat(font1);
        format2.setAlignment(Alignment.CENTRE);
        format2.setBorder(Border.ALL, BorderLineStyle.THIN);

        WritableCellFormat format3 = new WritableCellFormat(font1, _nf);
        format3.setAlignment(Alignment.RIGHT);
        format3.setBorder(Border.RIGHT, BorderLineStyle.THICK, Colour.RED);
        format3.setBorder(Border.LEFT, BorderLineStyle.THIN);
        format3.setBorder(Border.TOP, BorderLineStyle.THIN);
        format3.setBorder(Border.BOTTOM, BorderLineStyle.THIN);

        StockAgeWapped totalAgeWapped = datas.remove("total").get(0);
        sheet.setColumnView(0, 12);
        sheet.addCell(new Label(0, temp, "总计", format2));
        sheet.addCell(new Label(1, temp, "", format2));
        int _n = 0;
        int j = 2;
        for (InvStockAge.StockAgeData ageData : totalAgeWapped.getAgeDatas()) {

            sheet.setColumnView(j, 8);
            double stockQuantity = ageData.getStockQuantity();
            double _value = ageData.getValue().doubleValue();

            if (ageData.getAge() == -1004) {
                stockQuantity /= 100;
                _value /= 100;
                sheet.addCell(new Number(j++, temp, stockQuantity, formatP));
            } else{
                sheet.addCell(new Number(j++, temp, stockQuantity, format1));
            }
            sheet.setColumnView(j, 8);
            if (_n == 5) {
                sheet.addCell(new Number(j++, temp, _value, format3));
            }else {
                if (ageData.getAge() == -1004) {
                    sheet.addCell(new Number(j++, temp, _value, formatP));
                }else{
                    sheet.addCell(new Number(j++, temp, _value, _format1));
                }
            }
            _n++;
        }
        temp++;
        for (Map.Entry<String, List<StockAgeWapped>> entry : datas.entrySet()) {
            String key = entry.getKey();
            List<StockAgeWapped> value = entry.getValue();
            sheet.setColumnView(0, 12);
            sheet.addCell(new Label(0, temp, key, format2));
            if (value.size() > 1) {
                sheet.mergeCells(0, temp, 0, temp + value.size() - 1);
            }
            for (StockAgeWapped ageWapped : value) {
                j = 1;

                sheet.setColumnView(j, 8);
                sheet.addCell(new Label(j++, temp, ageWapped.getStockAge().getProductGroupName(),
                    format2));

                List<InvStockAge.StockAgeData> agedatas = ageWapped.getAgeDatas();
                int n = 0;
                for (InvStockAge.StockAgeData ageData : agedatas) {
                    sheet.setColumnView(j, 8);

                    double stockQuantity = ageData.getStockQuantity();
                    double _value = ageData.getValue().doubleValue();

                    if (ageData.getAge() == -1004) {
                        stockQuantity /= 100;
                        _value /= 100;
                        sheet.addCell(new Number(j++, temp, stockQuantity, formatP));
                    } else{
                        sheet.addCell(new Number(j++, temp, stockQuantity, format1));
                    }
                    sheet.setColumnView(j, 8);
                    if (n == 5) {
                        sheet.addCell(new Number(j++, temp, _value, format3));
                    }else {
                        if (ageData.getAge() == -1004) {
                            sheet.addCell(new Number(j++, temp, _value, formatP));
                        }else{
                            sheet.addCell(new Number(j++, temp, _value, _format1));
                        }
                    }
                    n++;
                }
                temp++;
            }
        }

        workbook.write();
        workbook.close();
    }
    /**
     * 预测准确率-到渠道到产品汇总-过滤器
     * @param request
     * @return
     */
    @RequestMapping(value = { "/countStockAgesToChannelsToSku.html" }, method = { RequestMethod.GET })
    ModelAndView countStockAgesToChannelsToSku(@RequestParam(required = false) String date,
        HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        if (date == null || date.trim().equals("")) {
            mv.addObject("date", DateUtil.format(new Date(), "yyyy-MM-dd"));
        } else {
            mv.addObject("date", date);
        }
        mv.addObject("title", "WA库存-到渠道到产品汇总");
        mv.addObject("onQueryUrl", "/stock/getStockAgeForToChannelsToProductsList.html");
        //        request.setAttribute("onShowDetailUrl", "/stock/countStockAgesWithChannelsBySku.html");
        mv.addObject("onExportUrl", "/stock/exportStockAgeForToChannelsToProductsList.html");
        mv.addObject("onQuery", false);
        mv.addObject("select", 1);
        mv.setViewName("/stock/stockAgeForCountForecast");
        return mv;
    }
    /**
     * 预测准确率-到渠道到产品汇总-查询列表
     * @param modelMap
     * @param date
     * @return
     */
    @RequestMapping(value = { "/getStockAgeForToChannelsToProductsList.html" }, method = { RequestMethod.GET })
    String getStockAgeForToChannelsToProductsList(Map<String, Object> modelMap,
        @RequestParam(required = false) String date){

        Date clearDate = DateUtil.parse(date, "yyyy-MM-dd");

        if (clearDate == null) {
            clearDate = DateUtil.currentDate();
        }
        try {
            Map<String, List<StockAgeHandler>> datas = stockAgeModel
                .countStockGroupToChannelsToProducts(clearDate);

            modelMap.put("datas", datas);
            modelMap.put("show", "0");

        } catch (Exception e) {
            logger
                .error(
                    "[stock][StockAgeController_getStockAgeForToChannelsToProductsList]获取预测准确率-到渠道到产品汇总-查询列表时发生未知错误",
                    e);
            e.printStackTrace();
        }
        return "stock/stockAgeForToChannelsToProductsList";
    }
    /**
     * 预测准确率-到产品到渠道汇总-过滤器
     * @param request
     * @return
     */
    @RequestMapping(value = { "/countStockAgesToSkuToChannels.html" }, method = { RequestMethod.GET })
    String countStockAgesToSkuToChannels(@RequestParam(required = false) String date,
        HttpServletRequest request){
        if (date == null || date.trim().equals("")) {
            request.setAttribute("date", DateUtil.format(new Date(), "yyyy-MM-dd"));
        } else {
            request.setAttribute("date", date);
        }
        request.setAttribute("title", "WA库存-到产品到渠道汇总");
        request.setAttribute("onQueryUrl", "/stock/getStockAgeForToProductsToChannelsList.html");
        request.setAttribute("onExportUrl", "/stock/exportStockAgeForToProductsToChannelsList.html");
        request.setAttribute("onQuery", false);
        request.setAttribute("select", 2);
        return "stock/stockAgeForCountForecast";
    }

    /**
     * 预测准确率-到产品到渠道汇总-查询列表导出
     * @param date
     * @param modelMap
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "/exportStockAgeForToProductsToChannelsList.html" }, method = { RequestMethod.GET })
    String exportStockAgeForToProductsToChannelsList(@RequestParam(required = false) String date,
        Map<String, Object> modelMap,
        HttpServletRequest request,
        HttpServletResponse response){

        String charset = "UTF-8";
        String fileName = "到产品到渠道汇总_" + date;
        try {
            //执行查询
            getStockAgeForToProductsToChannelsList(modelMap, date);
            //生成excel
            Enumeration<?> eunm = request.getHeaders("User-Agent");
            String browertype = "";
            while (eunm.hasMoreElements()) {
                browertype = eunm.nextElement().toString();
            }
            if (browertype != null && browertype.contains("Firefox")) { //判断浏览器类型
                fileName = new String(fileName.getBytes(charset), "ISO-8859-1");
            } else {
                fileName = URLEncoder.encode(fileName, charset);
            }

            response.setContentType("application/vnd.ms-excel;charset=" + charset);
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileName
                + ".xls\"");
            modelMap.put("exportFlag", "1");
        } catch (Exception e) {
            logger.error("[stock][StockAgeController_exportStockAgeForToProductsToChannelsList]:"
                + fileName + "文件下载失败!", e);
            e.printStackTrace();
        }
        return "stock/stockAgeForToProductsToChannelsList";
    }
    /**
     * 预测准确率-到产品到渠道汇总-查询列表
     * @param modelMap
     * @param date
     * @return
     */
    @RequestMapping(value = { "/getStockAgeForToProductsToChannelsList.html" }, method = { RequestMethod.GET })
    String getStockAgeForToProductsToChannelsList(Map<String, Object> modelMap,
        @RequestParam(required = false) String date){

        Date clearDate = DateUtil.parse(date, "yyyy-MM-dd");

        if (clearDate == null) {
            clearDate = DateUtil.currentDate();
        }
        try {
            Map<String, List<StockAgeHandler>> datas = stockAgeModel
                .countStockGroupToProductsToChannels(clearDate);

            modelMap.put("datas", datas);
            modelMap.put("show", "0");

        } catch (Exception e) {
            logger .error( "[stock][StockAgeController_getStockAgeForToProductsToChannelsList]获取预测准确率-到产品到渠道汇总-查询列表时发生未知错误",e);
            e.printStackTrace();
        }
        return "stock/stockAgeForToProductsToChannelsList";
    }
    /**
     * 预测准确率-到渠道到产品汇总-查询列表导出
     * @param date
     * @param modelMap
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "/exportStockAgeForToChannelsToProductsList.html" }, method = { RequestMethod.GET })
    String exportStockAgeForToChannelsToProductsList(@RequestParam(required = false) String date,
        Map<String, Object> modelMap,
        HttpServletRequest request,
        HttpServletResponse response){

        String charset = "UTF-8";
        String fileName = "到渠道到产品汇总_" + date;
        try {
            //执行查询
            getStockAgeForToChannelsToProductsList(modelMap, date);
            //生成excel
            Enumeration<?> eunm = request.getHeaders("User-Agent");
            String browertype = "";
            while (eunm.hasMoreElements()) {
                browertype = eunm.nextElement().toString();
            }
            if (browertype != null && browertype.contains("Firefox")) { //判断浏览器类型
                fileName = new String(fileName.getBytes(charset), "ISO-8859-1");
            } else {
                fileName = URLEncoder.encode(fileName, charset);
            }

            response.setContentType("application/vnd.ms-excel;charset=" + charset);
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileName
                + ".xls\"");
            modelMap.put("exportFlag", "1");
        } catch (Exception e) {
            logger.error("[stock][StockAgeController_exportStockAgeForToChannelsToProductsList]:"
                + fileName + "文件下载失败!", e);
            e.printStackTrace();
        }
        return "stock/stockAgeForToChannelsToProductsList";
    }
    @RequestMapping(value = { "/countStockAgesWithChannels.html" }, method = { RequestMethod.GET })
    ModelAndView countStockAgesWithChannels(@RequestParam(required = false) String date,
        HttpServletRequest request) {
        ModelAndView mv=new ModelAndView();
        if (date == null || date.trim().equals("")) {
            mv.addObject("shijian",DateUtil.format(new Date(), "yyyy-MM-dd"));
        } else {
            mv.addObject("shijian",date);
            request.setAttribute("date", date);
        }
        mv.addObject("title","WA库存-到渠道");
        mv.addObject("onQueryUrl","/stock/getStockAgeForChannelsList.html");
        mv.addObject("onExportUrl","/stock/exportStockAgeForChannelsList.html");
        mv.addObject("onQuery","false");
        mv.addObject("select",3);
        mv.setViewName("/stock/stockAgeForCount");
        return mv;
    }
    @RequestMapping(value = { "/countStockAgesWithSku.html" }, method = { RequestMethod.GET })
    String countStockAgesWithSku(@RequestParam(required = false) String date,
        HttpServletRequest request) {
        if (date == null || date.trim().equals("")) {
            request.setAttribute("date", DateUtil.format(new Date(), "yyyy-MM-dd"));
        } else {
            request.setAttribute("date1", date);
        }
        request.setAttribute("title", "WA库存-到产品");
        request.setAttribute("onQueryUrl", "/stock/getStockAgeForProductsList.html");
        request.setAttribute("onShowDetailUrl", "/stock/countStockAgesWithSkuByChannels.html");
        request.setAttribute("onExportUrl", "/stock/exportStockAgeForProductsList.html");
        request.setAttribute("onQuery", false);
        request.setAttribute("select", 4);
        return "stock/stockAgeForCount";
    }
    @ResponseBody
    @RequestMapping(value = { "findStockAgeGroupByProduct.html" }, method = { RequestMethod.POST })
    public void findGroupByProduct(@RequestParam(required = false) String date,String channel,
        HttpServletResponse response, HttpServletRequest request) {

        try {
            Date clearDate = DateUtil.parse(date, "yyyy-MM-dd");

            if (clearDate == null) {
                clearDate = DateUtil.currentDate();
            }
            Map<String, List<StockAgeWapped>> datas = stockAgeModel.countStockGroupBySkuWithChannel(clearDate,channel);
            List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
            for (Map.Entry<String, List<StockAgeWapped>> stringListEntry : datas.entrySet()) {
                String productType = stringListEntry.getKey();
                List<StockAgeWapped> list = stringListEntry.getValue();
                for (StockAgeWapped stockAgeWapped : list) {
                    Map<String, Object> retMap = new HashMap<String, Object>();
                    retMap.put("productType", productType);
                    retMap.put("productGroup", stockAgeWapped.getStockAge().getProductGroupName());
                    for (InvStockAge.StockAgeData ageData : stockAgeWapped.getAgeDatas()) {
                        retMap.put("a" + String.valueOf(ageData.getAge()), ageData);
                        retMap.put("aa" + String.valueOf(ageData.getAge()), ageData);
                    }
                    retList.add(retMap);
                }
            }
            response.setHeader("contentType", "text/html; charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            Map<String, Object> map = new HashMap<String, Object>();

            LinkedList<Map<String,Object>> list=new LinkedList<>();
            for (Map<String,Object> map1:retList){
                if (map1.get("productType").toString().equals("total")){
                    list.addFirst(map1);
                }else{
                    list.add(map1);
                }

            }
            map.put("total", list.size());
            map.put("rows", list);
            response.getWriter().write(JsonUtil.toJson(map));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = { "/getStockAgeForProducts" }, method = { RequestMethod.GET })
    String getStockAgeForProduct() {
        return "stock/stockAgeForProducts";
    }

    @RequestMapping(value = { "findStockAgeGroupByProductNew.html" }, method = { RequestMethod.POST })
    public void findGroupByProductNew(@RequestParam(required = false) String date,
        HttpServletResponse response, HttpServletRequest request){

        try {
            Date clearDate = DateUtil.parse(date, "yyyy-MM-dd");

            if (clearDate == null) {
                clearDate = DateUtil.currentDate();
            }
            Map<String, List<StockAgeWapped>> datas = stockAgeModel.countStockGroupBySku(clearDate);
            List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
            for (Map.Entry<String, List<StockAgeWapped>> stringListEntry : datas.entrySet()) {
                String productType = stringListEntry.getKey();
                List<StockAgeWapped> list = stringListEntry.getValue();
                for (StockAgeWapped stockAgeWapped : list) {
                    Map<String, Object> retMap = new HashMap<String, Object>();
                    retMap.put("productType", productType);
                    retMap.put("productGroup", stockAgeWapped.getStockAge().getProductGroupName());
                    for (InvStockAge.StockAgeData ageData : stockAgeWapped.getAgeDatas()) {
                        retMap.put("a" + String.valueOf(ageData.getAge()), ageData);
                        retMap.put("aa" + String.valueOf(ageData.getAge()), ageData);
                    }
                    retList.add(retMap);
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            LinkedList<Map<String,Object>> list=new LinkedList<>();
            for (Map<String,Object> map1:retList){
                if (map1.get("productType").toString().equals("total")){
                    list.addFirst(map1);
                }else{
                    list.add(map1);
                }

            }
            map.put("total", list.size());
            map.put("rows", list);
            response.setHeader("contentType", "text/html; charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JsonUtil.toJson(map));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = { "/countStockAgesWithSkuByChannels.html" }, method = { RequestMethod.GET })
    ModelAndView countStockAgesWithSkuByChannels(HttpServletRequest request,
        @RequestParam(required = false) String date,
        @RequestParam(required = false) String param,
        @RequestParam(required = false) String title){
        ModelAndView mv=new ModelAndView();
        mv.addObject("shijian", date);
        param = request.getParameter("param");
        mv.addObject("productGroupName", param);
        title = request.getParameter("title");
        mv.addObject("title", title + " WA库存-到产品");
        mv.addObject("onQueryUrl", "/stock/getStockAgeForChinnelWithProductGroup.html");
        mv.addObject("onExportUrl","/stock/exportStockAgeForChinnelWithProductGroup.html");
        mv.addObject("onQuery", true);
        mv.addObject("select", 4);
        mv.setViewName("stock/stockForProducts");
        return mv;
    }
    @RequestMapping(value = { "/getStockAgeForChinnelWithProductGroup.html" })
    void getStockAgeForChinnelWithProductGroup(Map<String, Object> modelMap,  HttpServletResponse response,
        @RequestParam(required = false) String date,
        @RequestParam(required = false) String productGroupName)throws Exception {

        Date clearDate = DateUtil.parse(date, "yyyy-MM-dd");

        if (clearDate == null) {
            clearDate = DateUtil.currentDate();
        }
        List<StockAgeWapped> datas = stockAgeModel.countStockGroupByChannelWithSku(clearDate,
            productGroupName);
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        for (StockAgeWapped stockAgeWapped : datas) {
            Map<String, Object> retMap = new HashMap<String, Object>();
            retMap.put("channel", stockAgeWapped.getStockAge().getChannelCode());
            retMap.put("channelName", stockAgeWapped.getStockAge().getChannelName());
            for (InvStockAge.StockAgeData ageData : stockAgeWapped.getAgeDatas()) {
                retMap.put("a" + String.valueOf(ageData.getAge()), ageData);
                retMap.put("aa" + String.valueOf(ageData.getAge()), ageData);
            }
            retList.add(retMap);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", retList.size());
        map.put("rows", retList);
        response.setHeader("contentType", "text/html; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JsonUtil.toJson(map));
        response.getWriter().flush();
        response.getWriter().close();
    }
    @RequestMapping(value = { "/exportStockAgeForProductsList.html" }, method = { RequestMethod.GET })
    void exportStockAgeForProduct(HttpServletResponse response,
        @RequestParam(required = false) String date){

        Date clearDate = DateUtil.parse(date, "yyyy-MM-dd");

        if (clearDate == null) {
            clearDate = DateUtil.currentDate();
        }
        Map<String, List<StockAgeWapped>> datas = stockAgeModel.countStockGroupBySku(clearDate);

        ServletOutputStream os = null;
        try {
            try {
                os = response.getOutputStream();
                response.reset();

                String fileName = "WA库存-到产品（" + DateUtil.format(clearDate, "yyyy-MM-dd") + ")";

                /*response.setHeader("Content-Disposition", "attachment; filename="
                        + new String(fileName.getBytes(),
                        "utf-8") + ".xls");
                response.setContentType("application/octet-stream; charset=utf-8");*/

                response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + URLEncoder.encode(fileName,"UTF-8")
                    + ".xls");
                response.setContentType("application/octet-stream");
                response.setCharacterEncoding("UTF-8");
                exportStockAgeForProducts("WA库存-到产品", clearDate, datas, os);
            } catch (Exception e) {
                response.reset();
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print("导出excel失败，" + e);
                response.getWriter().flush();
                logger.error("导出excel失败:", e);
            } finally {
                if (os != null){
                    os.close();
                }
            }
        } catch (IOException e) {
            logger.error("导出excel失败:", e);
        }
    }
    @RequestMapping(value = { "/exportStockAgeForChinnelWithProductGroup.html" })
    void exportStockAgeForChinnelWithProductGroup(HttpServletResponse response,HttpServletRequest request,
        @RequestParam(required = false) String date,
        @RequestParam(required = false) String productGroupName)throws Exception{
        Date clearDate = DateUtil.parse(date, "yyyy-MM-dd");
        if (clearDate == null) {
            clearDate = DateUtil.currentDate();
        }
        List<StockAgeWapped> datas = stockAgeModel.countStockGroupByChannelWithSku(clearDate,
            productGroupName);

        ServletOutputStream os = null;
        try {
            try {
                os = response.getOutputStream();
                response.reset();

                String fileName = productGroupName + "WA库存-到渠道（"
                    + DateUtil.format(clearDate, "yyyy-MM-dd") + ")";

                response.setHeader("Content-Disposition", "attachment; filename="
                    + new String(fileName.getBytes(),
                    "iso-8859-1") + ".xls");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/octet-stream; charset=utf-8");
                exportStockAgeForChannels(productGroupName + "WA库存-到渠道", clearDate, datas, os);
            } catch (Exception e) {
                response.reset();
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print("导出excel失败，" + e);
                response.getWriter().flush();
                logger.error("导出excel失败:", e);
            } finally {
                if (os != null){
                    os.close();
                }
            }
        } catch (IOException e) {
            logger.error("导出excel失败:", e);
        }

    }

    @RequestMapping(value = { "/getStockAge.html" }, method = { RequestMethod.GET })
    String getStockAge() {
        return "stock/stockAge";
    }

    @ResponseBody
    @RequestMapping(value = { "/getStockAgeList.html" }, method = { RequestMethod.POST })
    String getStockAgeList(Map<String, Object> modelMap,
        @RequestParam(required = false) String sec_code,
        @RequestParam(required = false) String sku,
        @RequestParam(required = false) String channel_code,
        @RequestParam(required = false) String product_group_name,
        @RequestParam(required = false) String product_type_name,
        @RequestParam(required = false) String product_name,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer rows) {

        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 20;
        }
        PagerInfo pagerInfo = new PagerInfo(rows, page);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sec_code", sec_code);
        params.put("sku", sku);
        params.put("product_name", StringUtil.isEmpty(product_name) ? null : product_name.trim());
        params.put("start",(page-1)*rows);
        params.put("size",rows);
        if (!"ALL".equals(channel_code)){
            params.put("channel_code", channel_code.trim());
        }
        if (!"全部".equals(product_group_name)){
            params.put("product_group_name", product_group_name);
        }
        if (!"全部".equals(product_type_name)) {
            params.put("product_type_name", product_type_name);
        }
        ServiceResult<List<StockAgeWapped>> result = stockAgeModel.getStockAgeList(params);
        if (!result.getSuccess()) {
            logger.warn("查询库龄报表数据失败：" + result.getMessage());
            result.setResult(new ArrayList<>());
        }
        modelMap.put("rows", result.getResult());
        modelMap.put("total", result.getPager().getRowsCount());
        return JsonUtil.toJson(modelMap);
    }

    @RequestMapping(value = { "/getProductGroups" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<String>> getProductGroups(@RequestParam(required = false) String productType) {

        HttpJsonResult<List<String>> result = new HttpJsonResult<List<String>>();
        List<String> groups = stockAgeModel.getProductGroups(productType);
        groups.add(0, "全部");
        result.setData(groups);
        return result;
    }

    @RequestMapping(value = { "/exportStockAge" }, method = { RequestMethod.GET })
    void exportStockAges(@RequestParam(required = false) String sec_code,
        @RequestParam(required = false) String sku,
        @RequestParam(required = false) String channel_code,
        @RequestParam(required = false) String product_group_name,
        @RequestParam(required = false) String product_type_name,
        @RequestParam(required = false) String product_name,
        HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sec_code", sec_code);
        params.put("sku", sku);
        params.put("channel_code", channel_code.trim());
        params.put("product_group_name", product_group_name);
        params.put("product_type_name", product_type_name.trim());
        params.put("product_name", StringUtil.isEmpty(product_name) ? null : product_name.trim());
        ServletOutputStream os = null;
        try {
            try {
                response.setContentType("application/octet-stream;charset=UTF-8");
                os = response.getOutputStream();
                response.reset();
                response.setHeader("Content-Disposition",
                    "attachment; filename="+ java.net.URLEncoder
                        .encode("库龄报表"+ DateUtil.format(new Date(), "yyyy-MM-dd"),"UTF-8")
                        + ".xls");
                response.addHeader("Pargam", "no-cache");
                response.addHeader("Cache-Control", "no-cache");
                export(os, params);
            } catch (Exception e) {
                response.reset();
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print("导出excel失败，" + e);
                response.getWriter().flush();
                logger.error("导出excel失败:", e);
            } finally {
                if (os != null){
                    os.close();
                }
            }
        } catch (IOException e) {
            logger.error("导出excel失败:", e);
        }
    }

    private void export(OutputStream os, Map<String, Object> params) throws Exception {
        HSSFWorkbook book = new HSSFWorkbook();
        HSSFSheet sheet = book.createSheet("库龄统计");
        setExcelTitle(sheet, book);
        setExcelParams(sheet, book, params);
        setExcelHeader(sheet, book, params);
        setExcelBody(sheet, book, params);
        try {
            book.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setExcelTitle(HSSFSheet sheet,HSSFWorkbook book) throws WriteException {
        HSSFRow row = sheet.createRow(0);
        for (int j = 0; j < 34; j++) {
            HSSFCell hssfCell = row.createCell(j);
        }
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0,0,0,33);
        HSSFCell cell = row.getCell(0);
        cell.setCellStyle(setTitleFontStyle(book,16, HSSFColor.BLUE.index,true));
        cell.setCellValue("库龄报表");
        sheet.addMergedRegion(cellRangeAddress);
        row.setHeight((short) 400);
    }

    public void setCellStyle(HSSFCellStyle cellStyle) {
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
    }

    private HSSFCellStyle setTitleFontStyle(HSSFWorkbook workbook,int fontSize,short color,boolean isBold) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        if(isBold){
            //粗体显示
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        }
        font.setFontHeightInPoints((short) fontSize);
        font.setColor(color);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
        //选择需要用到的字体格式
        cellStyle.setFont(font);
        return cellStyle;
    }

    private void setExcelParams(HSSFSheet sheet,HSSFWorkbook book, Map<String, Object> params) {
        HSSFRow row = sheet.createRow(1);
        HSSFCellStyle contentStyle = setTitleFontStyle(book,11, HSSFColor.BLUE_GREY.index,false);
        setCellStyle(contentStyle);
        String[] names = new String[]{"渠道",(String) params.get("channel_code"),"品类",(String) params.get("product_type_name"),
            "产品组",(String) params.get("product_group_name"),"库位",(String) params.get("sec_code"),
            "物料编码",(String) params.get("sku"),"产品型号",(String) params.get("product_name")};
        for (int i = 0;i < names.length; i++){
          HSSFCell hssfCell = row.createCell(i);
          hssfCell.setCellStyle(contentStyle);
          hssfCell.setCellValue(names[i]);
        }
    }

    private void setExcelHeader(HSSFSheet sheet,HSSFWorkbook workbook, Map<String, Object> map) throws Exception {
        String[] headers = new String[] { "渠道", "库位", "库位名称", "品类", "产品组", "品牌", "物料编码", "型号",
            "小计", "1-7天", "8-14天", "15-21天", "22-30天", "31-44天", "45-60天", "61-75天", "75-90天",
            "3-4月", "4-6月", "6-12月", "1年以上" };
          HSSFRow row = sheet.createRow(3);
          HSSFCellStyle contentStyle = setTitleFontStyle(workbook,11, HSSFColor.BLUE_GREY.index,true);
          setCellStyle(contentStyle);
          for (int i = 0;i < 34; i++){
            HSSFCell hssfCell = row.createCell(i);
            hssfCell.setCellStyle(contentStyle);
              if(i<8){
                  hssfCell.setCellValue(headers[i]);
                  /*CellRangeAddress cellRangeAddress = new CellRangeAddress(2,
                    3, i,i);
                sheet.addMergedRegion(cellRangeAddress);*/
            }else if (i % 2 == 0){
                /*CellRangeAddress cellRangeAddress = new CellRangeAddress(2,
                    2, i,i+1);
                sheet.addMergedRegion(cellRangeAddress);*/
                hssfCell.setCellValue(headers[8 + (i-8) / 2]);
            }
          }
        HSSFRow nextRow = sheet.createRow(4);

        for (int i = 0;i < 34; i++){
            HSSFCell hssfCell = nextRow.createCell(i);
            hssfCell.setCellStyle(contentStyle);
            if (i < 8){

            }else if (i % 2 == 0){
              hssfCell.setCellValue("數量");
            }else {
              hssfCell.setCellValue("金额");
            }
        }
        for (int i = 0;i < 34; i++){
            if(i < 8){
                CellRangeAddress cellRangeAddress = new CellRangeAddress(3,
                    4, i,i);
                sheet.addMergedRegion(cellRangeAddress);
            }else {
                CellRangeAddress cellRangeAddress = new CellRangeAddress(3,
                    3, i,i + 1);
                i++;
                sheet.addMergedRegion(cellRangeAddress);
            }
        }

    }

    private void setExcelBody(HSSFSheet sheet,HSSFWorkbook workbook,Map<String, Object> map) throws Exception {
        HSSFCellStyle contentStyle = setTitleFontStyle(workbook,9, HSSFColor.BLACK.index,false);
        setCellStyle(contentStyle);
        List<StockAgeWapped> stockAges = loadStockAgesForExprot(map);

        HSSFCellStyle cellStyle = setTitleFontStyle(workbook,9, HSSFColor.BLACK.index,false);
        setCellStyle(cellStyle);
        cellStyle.setBorderRight(CellStyle.BORDER_THICK);
        cellStyle.setRightBorderColor(HSSFColor.RED.index);

        for (int i = 0; i < stockAges.size(); i++) {
            int cellNum = 0;
            HSSFRow row = sheet.createRow(i + 5);
            HSSFCell hssfCell0 = row.createCell(cellNum);
            hssfCell0.setCellValue(stockAges.get(i).getStockAge().getChannelName());
            hssfCell0.setCellStyle(contentStyle);

            HSSFCell hssfCell1 = row.createCell(++cellNum);
            hssfCell1.setCellValue(stockAges.get(i).getStockAge().getSecCode());
            hssfCell1.setCellStyle(contentStyle);

            HSSFCell hssfCell2 = row.createCell(++cellNum);
            hssfCell2.setCellValue(stockAges.get(i).getStockAge().getSecName());
            hssfCell2.setCellStyle(contentStyle);

            HSSFCell hssfCell3 = row.createCell(++cellNum);
            hssfCell3.setCellValue(stockAges.get(i).getStockAge().getProductTypeName());
            hssfCell3.setCellStyle(contentStyle);

            HSSFCell hssfCell4 = row.createCell(++cellNum);
            hssfCell4.setCellValue(stockAges.get(i).getStockAge().getProductGroupName());
            hssfCell4.setCellStyle(contentStyle);

            HSSFCell hssfCell5 = row.createCell(++cellNum);
            hssfCell5.setCellValue(stockAges.get(i).getStockAge().getBrand());
            hssfCell5.setCellStyle(contentStyle);
            HSSFCell hssfCell6 = row.createCell(++cellNum);
            hssfCell6.setCellValue(stockAges.get(i).getStockAge().getSku());
            hssfCell6.setCellStyle(contentStyle);
            HSSFCell hssfCell7 = row.createCell(++cellNum);
            hssfCell7.setCellValue(stockAges.get(i).getStockAge().getProductName());
            hssfCell7.setCellStyle(contentStyle);

            List<StockAgeData> agedatas = stockAges.get(i).getAgeDatas();

            int n = 0;
            for (StockAgeData ageData : agedatas) {
                HSSFCell dataCell = row.createCell(++cellNum);
                dataCell.setCellValue(ageData.getStockQuantity());
                dataCell.setCellStyle(contentStyle);
                if (n == 6){
                    HSSFCell cell = row.createCell(++cellNum);
                    cell.setCellValue(ageData.getValue().doubleValue());

                    cell.setCellStyle(cellStyle);
                }else{
                    HSSFCell dataCell1 = row.createCell(++cellNum);
                    dataCell1.setCellValue(ageData.getValue().doubleValue());
                    dataCell1.setCellStyle(contentStyle);
                }
                n++;
            }
        }

    }

    private List<StockAgeWapped> loadStockAgesForExprot(Map<String, Object> params) {
        List<StockAgeWapped> stockAges = new ArrayList<StockAgeWapped>();
        int page = 1;
        boolean hasMore = true;
        int count = 0;
        while (hasMore) {
            PagerInfo pagerInfo = new PagerInfo(1000, page);
            if ("ALL".equals(params.get("channel_code"))) {
                params.put("channel_code", "");
            }
            if ("全部".equals(params.get("product_group_name"))) {
                params.put("product_group_name", "");
            }
            params.put("start",(page-1)*30000);
            params.put("rows",30000);
            ServiceResult<List<StockAgeWapped>> rs = stockAgeModel.getStockAgeList(params);
            if (!rs.getSuccess()){
                hasMore = false;
            }
            count = rs.getResult().size();
            if (count == 0 || rs.getResult().size() < 30000){
                hasMore = false;
            }
            stockAges.addAll(rs.getResult());
            page++;
        }
        return stockAges;
    }

    @RequestMapping(value = { "/getChannels" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<InvStockChannel>> getChannels() {
        HttpJsonResult<List<InvStockChannel>> result = new HttpJsonResult<List<InvStockChannel>>();
        List<InvStockChannel> channels = stockAgeModel.getChannels();
        result.setData(channels);
        return result;
    }

    /*@RequestMapping(value = { "/delhistory.html" }, method = { RequestMethod.GET })
    String calculateHistory() {
        return "stock/stockAgeHistory";
    }

    @RequestMapping(value = { "/calByDay/real/danger" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult calByDay(String startDate,String endDate) {
        Date start = DateUtil.parse(startDate,"yyyy-MM-dd");
        Date end = DateUtil.parse(endDate,"yyyy-MM-dd");

//        Date today = DateUtil.truncateTime();//当前日期
        HttpJsonResult<List<InvStockChannel>> result = new HttpJsonResult<List<InvStockChannel>>();
        List<Date> dates = getEveryday(start,end);
        if (dates.size() != 1){
            result.setMessage("按天统计最多只能统计一天");
            return result;
        }
        StringBuilder stringBuilder =new StringBuilder();
        for (Date today:dates){
            ServiceResult<Boolean> result1 = stockAgeModel.calculateStockAgeDayHistory(today);
            stringBuilder.append(result1.getMessage());
        }
//        result.setSuccess(result1.getSuccess());
        result.setMessage("处理结果:"+stringBuilder.toString());
        return result;
    }

    @RequestMapping(value = { "/calByTime/real/danger" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult calByTime(String startDate,String endDate) {
        Date start = DateUtil.parse(startDate,"yyyy-MM-dd");
        Date end = DateUtil.parse(endDate,"yyyy-MM-dd");
        HttpJsonResult<List<InvStockChannel>> result = new HttpJsonResult<List<InvStockChannel>>();
        List<Date> dates = getEveryday(start,end);
        if (dates.size() != 1){
            result.setMessage("实时统计最多只能统计一天");
            return result;
        }
        StringBuilder stringBuilder =new StringBuilder();
        for (Date today:dates){
            ServiceResult<Boolean> result1 = stockAgeModel.calculateStockAgeTimeHistory(today);
            stringBuilder.append(result1.getMessage());
        }
//        result.setSuccess(result1.getSuccess());
        result.setMessage("处理结果:"+stringBuilder.toString());
        return result;
    }

    public    List<Date> getEveryday(Date beginDate , Date endDate){
        List<Date> results = new ArrayList<>();
        Calendar startTime =  Calendar.getInstance();
        startTime.setTime(beginDate);
        do {
            results.add(startTime.getTime());
            startTime.add(Calendar.DATE,1);
        }while (startTime.getTime().before(endDate));
        return results;
    }

    @RequestMapping(value = { "/onToall/real/danger" }, method = { RequestMethod.GET })
    @ResponseBody
    String onToall(){
        logger.warn("开始生产库龄数据");
        stockAgeModel.processForGenerateStockAgeInOutHistory();
        logger.warn("生产库龄数据结束");
        Date start = DateUtil.parse("2018-06-22","yyyy-MM-dd");
        Date end =  DateUtil.parse("2018-08-15","yyyy-MM-dd");
        List<Date> dates = getEveryday(start ,end);
//        StockAgeModel model = (StockAgeModel)SpringContextUtil.getBean("stockAgeModel");
        for (Date today:dates){

            ServiceResult<Boolean> result1 = stockAgeModel.calculateStockAgeDayHistory(today);
            logger.warn(today+"按天统计的处理结果为"+result1.getMessage());
            ServiceResult<Boolean> result2 = stockAgeModel.calculateStockAgeTimeHistory(today);
            logger.warn(today+"实时统计的处理结果为"+result1.getMessage());
        }
        return "zhiximgwanl";
    }*/

}
