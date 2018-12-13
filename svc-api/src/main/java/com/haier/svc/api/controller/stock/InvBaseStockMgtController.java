package com.haier.svc.api.controller.stock;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.util.StringUtil;
import com.haier.stock.model.*;
import com.haier.stock.service.InvBaseStockMgtService;
import com.haier.svc.api.controller.util.*;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.jsondoc.core.annotation.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 胡万来 on 2018/1/23 0023.
 */
@Controller
@Api(name = "WA库存", description = "invBaseStockMgtController")
@RequestMapping(value = "stock/")
public class InvBaseStockMgtController {

    @Autowired
    InvBaseStockMgtService invBaseStockMgtService;

    @RequestMapping(value = {"/getBaseStockPage"}, method = {RequestMethod.GET})
    String loadBaseStockPage() {
        return "stock/stock/baseStockList";
    }

    /**WA库存
     * @param page
     * @param rows
     * @param invBaseStock
     * @return
     */
    @RequestMapping(value = "/findBaseStockList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findBaseStockList(int page, int rows, InvBaseStock invBaseStock) {
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);

        return invBaseStockMgtService.getBaseStockList(pager, invBaseStock);
    }

    /**WA库存套机查询
     * @param page
     * @param rows
     * @param invBaseStock
     * @return
     */
    @RequestMapping(value = "/findMachineBaseStockList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findMachineBaseStockList(int page, int rows, InvBaseStock invBaseStock) {
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);

        return invBaseStockMgtService.getMachineBaseStockList(pager, invBaseStock);
    }

    /**
     * 查询所有品类
     *
     * @return
     */
    @RequestMapping("/getAllProductTypes")
    @ResponseBody
    public JSONArray getAllProductTypes() {
        return invBaseStockMgtService.getAllProductTypes();
    }

    //
    @RequestMapping(value = {"/getBaseStockLogPage"}, method = {RequestMethod.GET})
    String loadBaseStockLogPage() {
        return "stock/stock/baseStockLogList";
    }

    @RequestMapping(value = "/findBaseStockLogList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findBaseStockLogList(int page, int rows, InvBaseStockLog invBaseStockLog) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        if (StringUtils.isNotEmpty(invBaseStockLog.getStartCreateTime())) {
            Date date = sdf.parse(invBaseStockLog.getStartCreateTime()+" 00:00:00" );
            if (null != date) {
                invBaseStockLog.setStartCreateDate(date);
            }
        }
        if (StringUtils.isNotEmpty(invBaseStockLog.getEndCreateTime())) {
            Date date = sdf.parse(invBaseStockLog.getEndCreateTime()+" 23:59:59");
            if (null != date) {
                invBaseStockLog.setEndCreateDate(date);
            }
        }
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);
        JSONObject jsonObject = invBaseStockMgtService.getInvBaseStockLogList(pager, invBaseStockLog);
        return jsonObject;
    }

    //店铺库存
    @RequestMapping(value = {"/getStorePage"}, method = {RequestMethod.GET})
    String loadStorePage() {
        return "stock/stock/storeList";
    }

    /**
     * 店铺库存列表
     *
     * @param page
     * @param rows
     * @param invStore
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/findStoreList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findStoreList(int page, int rows, InvStore invStore) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);

        return invBaseStockMgtService.getStoreList(pager, invStore);
    }

    /**
     * 套机管理
     *
     * @return
     */
    @RequestMapping(value = {"/getMachineSetMgtPage"}, method = {RequestMethod.GET})
    String loadMachineSetMgtPage() {
        return "stock/stock/machineSetMgtList";
    }

    @RequestMapping(value = "/findMachineSetMgtList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findMachineSetMgtList(int page, int rows, InvMachineSet invMachineSet) throws ParseException {
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);

        return invBaseStockMgtService.getMachineSetMgtList(pager, invMachineSet);
    }

    @RequestMapping(value = "/findStockAreaList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findStockAreaList(int page, int rows, StockArea invMachineSet) throws ParseException {
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);

        return invBaseStockMgtService.getStockAreaList(pager, invMachineSet);
    }

    @RequestMapping(value = {"/enableSubSku"}, method = {RequestMethod.GET})
    public @ResponseBody
    HttpJsonResult<String> enableMachineSet(String status, String sku) {

        HttpJsonResult<String> jsonResult = new HttpJsonResult<String>();
        String message = invBaseStockMgtService.updateSubSku(sku, status,"");
        if (!StringUtil.isEmpty(message, true)) {
            jsonResult.setMessage(message);
        }
        return jsonResult;
    }

    /**
     * 跳转至县区库存管理页面
     *
     * @return
     */
    @RequestMapping(value = {"/getStockAreaList"}, method = {RequestMethod.GET})
    public String getStockAreaList() {
        return "stock/stock/stockAreaList";
    }

    /**
     * 按条件查询区县库存列表
     *
     * @return
     */
    @RequestMapping(value = {"/getStockAreaRowList"}, method = {RequestMethod.POST})
    @ResponseBody
    public JSONObject getStockAreaRowList(int page, int rows, StockArea stockArea) throws ParseException {
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);

        return invBaseStockMgtService.getStockAreaList(pager, stockArea);
    }

    /**
     * 查询所有省份
     *
     * @return
     */
    @RequestMapping("/getAllProvince")
    @ResponseBody
    public JSONArray getAllProvince() {
        return invBaseStockMgtService.queryAllProvince();
    }

    /**
     * 查询所有渠道列表
     *
     * @return
     */
    @RequestMapping("/getChannels")
    @ResponseBody
    public JSONArray getChannels() {
        return invBaseStockMgtService.getChannels();
    }

    /**
     * 查询省内所有城市
     *
     * @return
     */
    @RequestMapping(value = {"/getAllCityByProvId"}, method = {RequestMethod.POST})
    public @ResponseBody
    Map<Integer, String> getAllCityByProvId(Integer provinceCode) {
        HttpJsonResult<Map<Integer, String>> result = new HttpJsonResult<Map<Integer, String>>();
        Map<Integer, String> map = invBaseStockMgtService.queryAllCityByProvId(provinceCode);
        if (map == null) {
            map = new HashMap<Integer, String>();
        }
        return map;
    }

    /**
     * 查询市内所有区县
     *
     * @return
     */
    @RequestMapping(value = {"/getAllRegionByCityId"}, method = {RequestMethod.POST})
    public @ResponseBody
    Map<Integer, String> getAllRegionByCityId(Integer city_code) {
        HttpJsonResult<Map<Integer, String>> result = new HttpJsonResult<Map<Integer, String>>();
        Map<Integer, String> map = invBaseStockMgtService.getAllRegionByCityId(city_code);
        if (map == null) {
            map = new HashMap<Integer, String>();
        }
        return map;
    }

//    /**
//     * 查询所有渠道列表
//     *
//     * @return
//     */
//    @RequestMapping(value = {"/getChannels.html"}, method = {RequestMethod.GET})
//    public @ResponseBody
//    HttpJsonResult<List<InvStockChannel>> getChannels() {
//        HttpJsonResult<List<InvStockChannel>> result = new HttpJsonResult<List<InvStockChannel>>();
//        List<InvStockChannel> list = invBaseStockMgtService.getChannels();
//        result.setData(list);
//        return result;
//    }

    /**
     * @param secCodeData
     * @param skuData
     * @param stockQtyData
     * @param avaiableQtyData
     * @param itemPropertyData
     * @param productTypeNameData
     * @param productNameData
     * @param stockQtyCodeData
     * @param avaiableQtyCodeData
     * @param res
     * @param code
     * @throws IOException
     */
    @RequestMapping(value = "/exportBaseStockList", method = RequestMethod.GET)
    public void exportBaseStockList(String secCodeData, String skuData, String stockQtyData, String avaiableQtyData,
                                    String itemPropertyData, String productTypeNameData, String productNameData, String stockQtyCodeData,
                                    String avaiableQtyCodeData, HttpServletResponse res, String code) throws IOException {
        InvBaseStock condition = new InvBaseStock();

        condition.setSecCode(secCodeData);
        condition.setStockSku(skuData);
        if (!StringUtil.isEmpty(stockQtyData)) {
            condition.setStockStockQty(Integer.parseInt(stockQtyData));
        }
        if (!StringUtil.isEmpty(avaiableQtyData)) {
            condition.setAvaiableQty(Integer.parseInt(avaiableQtyData));
        }
        condition.setStockItemProperty(itemPropertyData);
        condition.setProductTypeName(productTypeNameData);
        condition.setProductName(productNameData);
        condition.setStockQtyCode(stockQtyCodeData);
        condition.setAvaiableQtyCode(avaiableQtyCodeData);
        List<InvBaseStock> invBaseStocks = new ArrayList<>();
        int pageIndex = 1;
        if ("yes".equals(code)) {
//            invBaseStocks = invBaseStockMgtService.exportBaseStockList(condition);
            List<InvBaseStock> invBaseStockList = new ArrayList<InvBaseStock>();
            do {
                invBaseStockList = invBaseStockMgtService.exportBaseStockListByCondition(condition,(pageIndex-1)*10000,10000);
                pageIndex++;
                invBaseStocks.addAll(invBaseStockList);
            }while (invBaseStockList.size() == 10000);
        }
        if ("mYes".equals(code)) {
//            invBaseStocks = invBaseStockMgtService.exportMachineBaseStockList(condition);
            List<InvBaseStock> invBaseStockList = new ArrayList<InvBaseStock>();
            do {
                invBaseStockList = invBaseStockMgtService.exportMachineBaseStockListByContion(condition,(pageIndex-1)*10000,10000);
                pageIndex++;
                invBaseStocks.addAll(invBaseStockList);
            }while (invBaseStockList.size() == 10000);
        }

        // 1.创建一个workbook，对应一个Excel文件
        SXSSFWorkbook wb = new SXSSFWorkbook(200);
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        Sheet sheet = wb.createSheet("WA库存列表");
        sheet.setColumnWidth(0, (int) (21.57 * 256));
        sheet.setColumnWidth(1, (int) 21.57 * 256);
        sheet.setColumnWidth(2, (int) 21.57 * 256);
        sheet.setColumnWidth(3, (int) 21.57 * 256);
        sheet.setColumnWidth(4, (int) 21.57 * 256);
        if ("yes".equals(code)) {
            sheet.setColumnWidth(5, (int) 11.14 * 256);
            sheet.setColumnWidth(6, (int) 11.14 * 256);
            sheet.setColumnWidth(7, (int) 8.57 * 256);
            sheet.setColumnWidth(8, (int) 21.57 * 256);
            sheet.setColumnWidth(9, (int) 21.57 * 256);
        }
        if ("mYes".equals(code)) {
            sheet.setColumnWidth(5, (int) 11.14 * 256);
            sheet.setColumnWidth(6, (int) 11.14 * 256);
            sheet.setColumnWidth(7, (int) 21.57 * 256);
            sheet.setColumnWidth(8, (int) 21.57 * 256);
        }

        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
        Row row = sheet.createRow(0);
        // 4.创建单元格，设置值表头，设置表头居中
        CellStyle style = wb.createCellStyle();
        // 居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        //查询表头
        if ("yes".equals(code)) {
            int length = ExportData.baseStockListTitle.length;
            // 设置表头
            for (int i = 0; length - 1 >= i; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(ExportData.baseStockListTitle[i]);
                cell.setCellStyle(style);
            }
        }
        //查询套机表头
        if ("mYes".equals(code)) {
            int length = ExportData.machineBaseStockListTitle.length;
            // 设置表头
            for (int i = 0; length - 1 >= i; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(ExportData.machineBaseStockListTitle[i]);
                cell.setCellStyle(style);
            }
        }

        //向单元格里添加数据
        if (invBaseStocks != null) {

            for (int i = 0; i < invBaseStocks.size(); i++) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                String createTime = sdf.format(invBaseStocks.get(i).getCreateTime());
                String updateTime = sdf.format(invBaseStocks.get(i).getUpdateTime());

                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(invBaseStocks.get(i).getStockSku());
                row.createCell(1).setCellValue(invBaseStocks.get(i).getCbsCategory());
                row.createCell(2).setCellValue(invBaseStocks.get(i).getProductName());
                row.createCell(3).setCellValue(invBaseStocks.get(i).getSecCode());
                row.createCell(4).setCellValue(invBaseStocks.get(i).getSecName());
                if ("yes".equals(code)) {
                    row.createCell(5).setCellValue(changeCodeToName(invBaseStocks.get(i).getStockItemProperty()));
                    row.createCell(6).setCellValue(invBaseStocks.get(i).getStockStockQty());
                    row.createCell(7).setCellValue(invBaseStocks.get(i).getStockFrozenQty());
                    row.createCell(8).setCellValue(createTime);
                    row.createCell(9).setCellValue(updateTime);
                }
                if ("mYes".equals(code)) {
                    row.createCell(5).setCellValue(changeCodeToName(invBaseStocks.get(i).getStockItemProperty()));
                    row.createCell(6).setCellValue(invBaseStocks.get(i).getAvaiableQty());
                    row.createCell(7).setCellValue(createTime);
                    row.createCell(8).setCellValue(updateTime);
                }

            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date = new java.util.Date();
        String str = sdf.format(date);
        String fileName = "WA库存" + str;
        if ("mYes".equals(code)) {
            fileName = "WA套机库存" + str;
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        ExportExcelUtil.exportCommon(is, fileName, res);
    }

    private String changeCodeToName(String value){
        if (StringUtils.isBlank(value)){
            return "";
        }
        if (value.equals("10")) {
            return "正品";
        }else if (value.equals("21")) {
            return "不良品";
        } else if (value.equals("22")) {
            return "开箱正品";
        } else if (value.equals("40")) {
            return "样品";
        } else if (value.equals("41")) {
            return "夺宝机";
        }else {
            return value;
        }
    }

    /**
     * @param secCodeData
     * @param skuData
     * @param refNoData
     * @param startCreateTimeData
     * @param endCreateTimeData
     * @param markData
     * @param billTypeData
     * @param res
     * @param code
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping(value = "/exportBaseStockLogList", method = RequestMethod.GET)
    public void exportBaseStockLogList(String secCodeData, String skuData, String refNoData, String startCreateTimeData,
                                       String endCreateTimeData, String markData, String billTypeData,
                                       HttpServletResponse res, String code) throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        InvBaseStockLog condition = new InvBaseStockLog();

        condition.setSecCode(secCodeData);
        condition.setSku(skuData);
        condition.setRefNo(refNoData);
        if (!StringUtil.isEmpty(startCreateTimeData)) {
            Date date = sdf.parse(startCreateTimeData+" 00:00:00");
            if (null != date) {
                condition.setStartCreateDate(date);
            }
        }
        if (!StringUtil.isEmpty(endCreateTimeData)) {
            Date date = sdf.parse(endCreateTimeData+" 23:59:59");
            if (null != date) {
                condition.setEndCreateDate(date);
            }
        }
        condition.setBillType(billTypeData);
        condition.setMark(markData);
        List<InvBaseStockLog> invBaseStockLogs = null;
        if ("yes".equals(code)) {
            invBaseStockLogs = invBaseStockMgtService.exportBaseStockLogList(condition);
        }

        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("库存日志列表");
        sheet.setColumnWidth(0, (int) (21.57 * 256));
        sheet.setColumnWidth(1, (int) 21.57 * 256);
        sheet.setColumnWidth(2, (int) 21.57 * 256);
        sheet.setColumnWidth(3, (int) 21.57 * 256);
        sheet.setColumnWidth(4, (int) 21.57 * 256);
        sheet.setColumnWidth(5, (int) 29.14 * 256);
        sheet.setColumnWidth(6, (int) 8.57 * 256);

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
        style.setWrapText(true);//自动换行

        //查询表头
        int length = ExportData.baseStockLogListTitle.length;
        // 设置表头
        for (int i = 0; length - 1 >= i; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(ExportData.baseStockLogListTitle[i]);
            cell.setCellStyle(style);
        }

        //向单元格里添加数据
        if (invBaseStockLogs != null) {
            for (short i = 0; i < invBaseStockLogs.size(); i++) {
                String createTime = sdf.format(invBaseStockLogs.get(i).getCreateTime());

                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(invBaseStockLogs.get(i).getRefNo());
                row.createCell(1).setCellValue(invBaseStockLogs.get(i).getSku());
                row.createCell(2).setCellValue(invBaseStockLogs.get(i).getProductName());
                row.createCell(3).setCellValue(invBaseStockLogs.get(i).getSecCode());
                row.createCell(4).setCellValue(invBaseStockLogs.get(i).getSecName());
                row.createCell(5).setCellValue(invBaseStockLogs.get(i).getContent());
                row.createCell(6).setCellValue(createTime);
            }
        }

        SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date = new java.util.Date();
        String str = s.format(date);
        String fileName = "库存日志" + str;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        ExportExcelUtil.exportCommon(is, fileName, res);
    }

    @RequestMapping(value = "/exportStoreList", method = RequestMethod.GET)
    public void exportStoreList(String storeCodeData, String skuData, String stockQtyData, String productTypeNameData,
                                String productNameData,
                                HttpServletResponse res, String code) throws IOException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        InvStore condition = new InvStore();

        condition.setStoreCode(storeCodeData);
        condition.setStoreSku(skuData);
        condition.setStockqty(stockQtyData);
        condition.setProductTypeName(productTypeNameData);
        condition.setProductName(productNameData);
        List<InvStore> invStores = null;
        if ("yes".equals(code)) {
            invStores = invBaseStockMgtService.exportStoreList(condition);
        }

        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("店铺库存列表");
        sheet.setColumnWidth(0, (int) (21.57 * 256));
        sheet.setColumnWidth(1, (int) 21.57 * 256);
        sheet.setColumnWidth(2, (int) 21.57 * 256);
        sheet.setColumnWidth(3, (int) 21.57 * 256);
        sheet.setColumnWidth(4, (int) 21.57 * 256);
        sheet.setColumnWidth(5, (int) 29.14 * 256);
        sheet.setColumnWidth(6, (int) 8.57 * 256);
        sheet.setColumnWidth(7, (int) 8.57 * 256);

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
        style.setWrapText(true);//自动换行

        //查询表头
        int length = ExportData.storeListTitle.length;
        // 设置表头
        for (int i = 0; length - 1 >= i; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(ExportData.storeListTitle[i]);
            cell.setCellStyle(style);
        }

        //向单元格里添加数据
        if (invStores != null) {
            for (short i = 0; i < invStores.size(); i++) {
                String updateTime = sdf.format(invStores.get(i).getUpdateTime());
                String storeTs = sdf.format(invStores.get(i).getStoreTs());

                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(invStores.get(i).getStoreCode());
                row.createCell(1).setCellValue(invStores.get(i).getStoreName());
                row.createCell(2).setCellValue(invStores.get(i).getStoreSku());
                row.createCell(3).setCellValue(invStores.get(i).getCbsCategory());
                row.createCell(4).setCellValue(invStores.get(i).getProductName());
                row.createCell(5).setCellValue(invStores.get(i).getStockqty());
                row.createCell(6).setCellValue(storeTs);
                row.createCell(7).setCellValue(updateTime);
            }
        }

        SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date = new java.util.Date();
        String str = s.format(date);
        String fileName = "店铺库存" + str;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        ExportExcelUtil.exportCommon(is, fileName, res);
    }
}
