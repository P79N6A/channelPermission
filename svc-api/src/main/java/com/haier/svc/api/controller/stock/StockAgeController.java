package com.haier.svc.api.controller.stock;

import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.stock.model.InvStockAge;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.StockAgeHandler;
import com.haier.stock.model.StockAgeWapped;
import com.haier.svc.api.controller.stock.mode.StockAgeModel;
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
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
            if (clearDate == null)
                clearDate = DateUtil.currentDate();
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

        if (clearDate == null)
            clearDate = DateUtil.currentDate();

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
                if (os != null)
                    os.close();
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
            if (m == 0)
                channelName = "合计";
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
                } else
                    sheet.addCell(new Number(j++, temp, stockQuantity, format1));

                sheet.setColumnView(j, 8);
                if (n == 5)
                    sheet.addCell(new Number(j++, temp, value, format3));
                else {
                    if (ageData.getAge() == -1004)
                        sheet.addCell(new Number(j++, temp, value, formatP));
                    else
                        sheet.addCell(new Number(j++, temp, value, _format1));

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

        if (clearDate == null)
            clearDate = DateUtil.currentDate();

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

        if (clearDate == null)
            clearDate = DateUtil.currentDate();

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
                if (os != null)
                    os.close();
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
            } else
                sheet.addCell(new Number(j++, temp, stockQuantity, format1));
            sheet.setColumnView(j, 8);
            if (_n == 5)
                sheet.addCell(new Number(j++, temp, _value, format3));
            else {
                if (ageData.getAge() == -1004)
                    sheet.addCell(new Number(j++, temp, _value, formatP));
                else
                    sheet.addCell(new Number(j++, temp, _value, _format1));
            }
            _n++;
        }
        temp++;
        for (Map.Entry<String, List<StockAgeWapped>> entry : datas.entrySet()) {
            String key = entry.getKey();
            List<StockAgeWapped> value = entry.getValue();
            sheet.setColumnView(0, 12);
            sheet.addCell(new Label(0, temp, key, format2));
            if (value.size() > 1)
                sheet.mergeCells(0, temp, 0, temp + value.size() - 1);

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
                    } else
                        sheet.addCell(new Number(j++, temp, stockQuantity, format1));
                    sheet.setColumnView(j, 8);
                    if (n == 5)
                        sheet.addCell(new Number(j++, temp, _value, format3));
                    else {
                        if (ageData.getAge() == -1004)
                            sheet.addCell(new Number(j++, temp, _value, formatP));
                        else
                            sheet.addCell(new Number(j++, temp, _value, _format1));
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

        if (clearDate == null)
            clearDate = DateUtil.currentDate();

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

        if (clearDate == null)
            clearDate = DateUtil.currentDate();

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

            if (clearDate == null)
                clearDate = DateUtil.currentDate();

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

            if (clearDate == null)
                clearDate = DateUtil.currentDate();

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
        mv.addObject("productGroupName", param);
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

        if (clearDate == null)
            clearDate = DateUtil.currentDate();

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

        if (clearDate == null)
            clearDate = DateUtil.currentDate();

        Map<String, List<StockAgeWapped>> datas = stockAgeModel.countStockGroupBySku(clearDate);

        ServletOutputStream os = null;
        try {
            try {
                os = response.getOutputStream();
                response.reset();

                String fileName = "WA库存-到产品（" + DateUtil.format(clearDate, "yyyy-MM-dd") + ")";

                response.setHeader("Content-Disposition", "attachment; filename="
                        + new String(fileName.getBytes(),
                        "iso-8859-1") + ".xls");
                response.setContentType("application/octet-stream; charset=utf-8");
                exportStockAgeForProducts("WA库存-到产品", clearDate, datas, os);
            } catch (Exception e) {
                response.reset();
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print("导出excel失败，" + e);
                response.getWriter().flush();
                logger.error("导出excel失败:", e);
            } finally {
                if (os != null)
                    os.close();
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
        if (clearDate == null)
            clearDate = DateUtil.currentDate();

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
                if (os != null)
                    os.close();
            }
        } catch (IOException e) {
            logger.error("导出excel失败:", e);
        }

    }
}
