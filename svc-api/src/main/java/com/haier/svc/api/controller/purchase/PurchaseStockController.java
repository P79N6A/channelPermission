package com.haier.svc.api.controller.purchase;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.ParseException;
import com.google.gson.Gson;
import com.haier.common.ServiceResult;
import com.haier.eis.model.BusinessException;
import com.haier.purchase.data.model.CnT2PurchaseStock;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import com.haier.svc.api.controller.util.ExportPushToSAP;
import com.haier.svc.service.PurchaseStockService;
import org.apache.log4j.LogManager;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping(value = "/purchaseStock")
@Controller
public class PurchaseStockController {

    private final static org.apache.log4j.Logger logger = LogManager
            .getLogger(CrmGenuineRejectController.class);

    @Autowired
    private PurchaseStockService purchaseStockService;
    /**
     * 推送sap跳转
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "purchaseStockList" }, method = { RequestMethod.GET })
    String purchaseStockList(HttpServletRequest request, Map<String, Object> modelMap) {

        return "purchase/purchaseStock";
    }


    /**
     * CRM正品退货查询
     *
     * @param rows
     * @param page
     * @param response
     */
    @RequestMapping(value = { "findPurchaseStockList" }, method = { RequestMethod.POST })
    void findCrmGenuineRejectList(@RequestParam(required = false) String cnStockSyncsId,
                                  @RequestParam(required = false) String status,
                                  @RequestParam(required = false) String startDate,
                                  @RequestParam(required = false) String endDate,
                                  @RequestParam(required = false) Integer rows,
                                  @RequestParam(required = false) Integer page,
                                  HttpServletRequest request, HttpServletResponse response) {
        try {
            if (rows == null)
                rows = 20;
            if (page == null)
                page = 1;

            // 取得产品组权限List,渠道权限List和品类List

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("cnStockSyncsId", cnStockSyncsId);
            params.put("status", status);
            params.put("startDate", startDate);
            params.put("endDate", endDate);
            params.put("m", (page - 1) * rows);
            params.put("n", rows);

            // 调用业务Service
            ServiceResult<List<CnT2PurchaseStock>> result = purchaseStockService
                    .getPurchaseStockList(params);
            /*
             * //获得条数 ServiceResult<Integer> resultcount =
             * crmGenuineRejectService.getRowCnts();
             */
            if (result.getSuccess() && result.getResult() != null) {
                List<CnT2PurchaseStock> predictstocklist = result.getResult();
                for(CnT2PurchaseStock stock : predictstocklist){
                    net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(stock.getPushData());
                        for(int i = 0 ;i < jsonArray.size() ; i++){
                            stock.setVbeln(jsonArray.getJSONObject(i).getString("vbeln"));
                            stock.setMatnr(jsonArray.getJSONObject(i).getString("matnr"));
                            stock.setMenge(jsonArray.getJSONObject(i).getString("menge"));
                            stock.setLgort(jsonArray.getJSONObject(i).getString("lgort"));
                         }
                }
                Gson gson = new Gson();
                Map<String, Object> retMap = new HashMap<String, Object>();
                retMap.put("total", result.getPager().getRowsCount());
                retMap.put("rows", predictstocklist);
                response.addHeader("Content-type", "text/html;charset=utf-8");
                response.getWriter().write(gson.toJson(retMap));
                response.getWriter().flush();
                response.getWriter().close();
            }
        } catch (IOException e) {
            logger.error("", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }


    /**
     * sap点击部分导出、导出Excel
     *
     * @param exportData
     *            导出数据
     * @param response
     * @param request
     * @param modelMap
     *            状态
     * @return 方法执行完毕调用的画面
     */
    @RequestMapping(value = { "/exportPurchaseStock" })
    void exportCrmGenuineReject(@RequestParam(required = false) String exportData,
                                HttpServletResponse response, HttpServletRequest request,
                                Map<String, Object> modelMap) {
        Map<String, Object> params = new HashMap<String, Object>();
        JSONArray exportJson = new JSONArray();
        String[] exportArray = null;
        try {
            if (exportData != null && !exportData.equals("")) {
                exportJson = (JSONArray) JSON.parse(exportData);
                exportArray = new String[exportJson.length()];
                // JSONArray转化为list
                for (int i = 0; i < exportJson.length(); i++) {
                    exportArray[i] = (String) exportJson.get(i);
                }
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
            logger.error("JSON转换失败！ 错误：" + e1.getMessage());
        }
        params.put("cnStockSyncsIdlist", exportArray);
        List<CnT2PurchaseStock> cnT2PurchaseStockData = getPurchaseStockListData(params);
        for(CnT2PurchaseStock stock : cnT2PurchaseStockData){
            net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(stock.getPushData());
            for(int i = 0 ;i < jsonArray.size() ; i++){
                stock.setVbeln(jsonArray.getJSONObject(i).getString("vbeln"));
                stock.setMatnr(jsonArray.getJSONObject(i).getString("matnr"));
                stock.setMenge(jsonArray.getJSONObject(i).getString("menge"));
                stock.setLgort(jsonArray.getJSONObject(i).getString("lgort"));
            }
        }
        HSSFWorkbook wb= getDetailsData(cnT2PurchaseStockData);
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date=new java.util.Date();
        String str=sdf.format(date);
        String fileName = "推送SAP"+str;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            wb.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);

            ExportExcelUtil.exportCommon(is,fileName,response);
        } catch (IOException e) {
            logger.error("错误", e);
        }
    }

    public HSSFWorkbook getDetailsData(List<CnT2PurchaseStock> list) {


        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("推送SAP");
        int length = ExportPushToSAP.pushToSAPListTitle.length;
        for (int i = 0; i <length; i++) {
            sheet.setColumnWidth(i, (int)(21.57*256));
        }

        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 4.创建单元格，设置值表头，设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边
        // 设置表头
        for(int i=0;length-1>=i;i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(ExportPushToSAP.pushToSAPListTitle[i]);
            cell.setCellStyle(style);
        }

        //向单元格里添加数据
        for(short i=0;i<list.size();i++){
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(list.get(i).getCnStockSyncsId());
            row.createCell(1).setCellValue(list.get(i).getPushData());
            row.createCell(2).setCellValue(list.get(i).getReturnData());

            String status = "";
            String cell = toCellString(list.get(i).getStatus());
            switch (cell){

                case "1":
                    status = "推送成功";
                    break;
                case "2":
                    status = "推送失败";
                    break;
                case "0":
                    status = "未推送";
                    break;
            }
            row.createCell(3).setCellValue(status);
            SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            row.createCell(4).setCellValue(list.get(i).getMessage());
            if (list.get(i).getProcessTime() == null){
                row.createCell(5).setCellValue("");
            }else {
                row.createCell(5).setCellValue(sdf2.format(list.get(i).getProcessTime()));
            }
            row.createCell(6).setCellValue(list.get(i).getLgort());

            row.createCell(7).setCellValue(list.get(i).getMenge());

            row.createCell(8).setCellValue(list.get(i).getMatnr());

            row.createCell(9).setCellValue(list.get(i).getVbeln());


        }
        return wb;

    }

    private String toCellString(Object obj){
        if(obj != null){
            return obj.toString();
        }
        return "";
    }
    private List<CnT2PurchaseStock> getPurchaseStockListData(Map<String, Object> params) {
        ServiceResult<List<CnT2PurchaseStock>> result = purchaseStockService
                .getPurchaseStockList(params);
        return result.getResult();
    }

        @RequestMapping(value = {"/exportPurchaseStockList"})
        void exportPurchaseStockList(@RequestParam(required = false) String cnStockSyncsId,
                                       @RequestParam(required = false) String status,
                                       @RequestParam(required = false) String startDate,
                                       @RequestParam(required = false) String endDate,
                HttpServletRequest request, HttpServletResponse response) throws IOException {

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("cnStockSyncsId", cnStockSyncsId);
            params.put("status", status);
            params.put("startDate", startDate);
            params.put("endDate", endDate);
            // 调用业务Service
            Integer pageIndex = 0;
            List<CnT2PurchaseStock> purchaseStockList= new ArrayList<>();

            while (true){
                params.put("m",1000*pageIndex);
                params.put("n",1000);
                pageIndex++;
                List<CnT2PurchaseStock> list = purchaseStockService.getPurchaseStocDatakList(params);
                if(list.isEmpty()||list.size()==0){
                    break;
                }
                purchaseStockList.addAll(list);
            }
            for(CnT2PurchaseStock stock : purchaseStockList){
                net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(stock.getPushData());
                for(int i = 0 ;i < jsonArray.size() ; i++){
                    stock.setVbeln(jsonArray.getJSONObject(i).getString("vbeln"));
                    stock.setMatnr(jsonArray.getJSONObject(i).getString("matnr"));
                    stock.setMenge(jsonArray.getJSONObject(i).getString("menge"));
                    stock.setLgort(jsonArray.getJSONObject(i).getString("lgort"));
                }
            }
            HSSFWorkbook wb= getDetailsData(purchaseStockList);
            SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
            java.util.Date date=new java.util.Date();
            String str=sdf.format(date);
            String fileName = "推送SAP"+str;
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                wb.write(os);
                byte[] content = os.toByteArray();
                InputStream is = new ByteArrayInputStream(content);

                ExportExcelUtil.exportCommon(is,fileName,response);
            } catch (IOException e) {
                logger.error("错误", e);
            }

    }
}
