package com.haier.svc.api.controller.purchase;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.ParseException;
import com.google.gson.Gson;
import com.haier.common.ServiceResult;
import com.haier.eis.model.BusinessException;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import com.haier.svc.api.controller.util.ExportPushToSAP;
import com.haier.svc.service.TransPushToSapService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/transPushToSap")
@Controller
public class TransPushToSapController {
    private final static org.apache.log4j.Logger logger = LogManager
            .getLogger(TransPushToSapController.class);
    @Autowired
    private TransPushToSapService transPushToSapService;

    /**
     * 推送sap跳转
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "transPushToSapList" }, method = { RequestMethod.GET })
    String purchaseStockList(HttpServletRequest request, Map<String, Object> modelMap) {

        return "purchase/transPushToSap";
    }




    /**
     * CRM正品退货查询
     *
     * @param rows
     * @param page
     * @param response
     */
    @RequestMapping(value = { "findTransPushToSapList" }, method = { RequestMethod.POST })
    void findCrmGenuineRejectList(@RequestParam(required = false) String foreignKey,
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
            params.put("foreignKey", foreignKey);
            params.put("status", status);
            params.put("startDate", startDate);
            params.put("endDate", endDate);
            params.put("m", (page - 1) * rows);
            params.put("n", rows);

            // 调用业务Service
            ServiceResult<List<EisInterfaceDataLog>> result = transPushToSapService
                    .getTransPushToSapList(params);
            /*
             * //获得条数 ServiceResult<Integer> resultcount =
             * crmGenuineRejectService.getRowCnts();
             */
            if (result.getSuccess() && result.getResult() != null) {
                List<EisInterfaceDataLog> eisInterfaceDataLogs = result.getResult();
                for(EisInterfaceDataLog eis : eisInterfaceDataLogs){
                    net.sf.json.JSONArray rpjsonArray = net.sf.json.JSONArray.fromObject(eis.getResponseDatas().replace("logout",""));
                    net.sf.json.JSONArray rqjsonArray = net.sf.json.JSONArray.fromObject(eis.getRequestData());
                    for(int i = 0 ;i < rqjsonArray.size() ; i++){
                        eis.setZlsin(rqjsonArray.getJSONObject(i).getString("zlsin"));
                        eis.setMatnr(rqjsonArray.getJSONObject(i).getString("matnr"));
                        eis.setMenge(rqjsonArray.getJSONObject(i).getString("menge"));
                        eis.setZlgorto(rqjsonArray.getJSONObject(i).getString("zlgorto"));
                        eis.setZlgorti(rqjsonArray.getJSONObject(i).getString("zlgorti"));
                    }
                    for(int i = 0 ;i < rpjsonArray.size() ; i++){
                        if("E".equals(rpjsonArray.getJSONObject(i).getString("type"))){
                            eis.setStatus("2");
                            break;
                        }else {
                            eis.setStatus("1");
                        }
                    }
                }
                Gson gson = new Gson();
                Map<String, Object> retMap = new HashMap<String, Object>();
                retMap.put("total", result.getPager().getRowsCount());
                retMap.put("rows", eisInterfaceDataLogs);
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
    @RequestMapping(value = { "/exportPushToSap" })
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
                    exportArray[i] = String.valueOf(exportJson.get(i));
                }
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
            logger.error("JSON转换失败！ 错误：" + e1.getMessage());
        }
        params.put("eisInterfaceDatalist", exportArray);
        List<EisInterfaceDataLog> eisInterfaceData = getPushToSapListData(params);
        for(EisInterfaceDataLog eisInterface : eisInterfaceData){
            net.sf.json.JSONArray rpjsonArray = net.sf.json.JSONArray.fromObject(eisInterface.getResponseDatas().replace("logout",""));
            net.sf.json.JSONArray rqjsonArray = net.sf.json.JSONArray.fromObject(eisInterface.getRequestData());
            for(int i = 0 ;i < rqjsonArray.size() ; i++){
                eisInterface.setZlsin(rqjsonArray.getJSONObject(i).getString("zlsin"));
                eisInterface.setMatnr(rqjsonArray.getJSONObject(i).getString("matnr"));
                eisInterface.setMenge(rqjsonArray.getJSONObject(i).getString("menge"));
                eisInterface.setZlgorto(rqjsonArray.getJSONObject(i).getString("zlgorto"));
                eisInterface.setZlgorti(rqjsonArray.getJSONObject(i).getString("zlgorti"));
            }
            for(int i = 0 ;i < rpjsonArray.size() ; i++){
                if("E".equals(rpjsonArray.getJSONObject(i).getString("type"))){
                    eisInterface.setStatus("推送失败");
                    break;
                }else {
                    eisInterface.setStatus("成功");
                }
            }
        }
        HSSFWorkbook wb= getDetailsData(eisInterfaceData);
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

    public HSSFWorkbook getDetailsData(List<EisInterfaceDataLog> list) {


        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("推送SAP");
        int length = ExportPushToSAP.transPushToSAPListTitle.length;
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
            cell.setCellValue(ExportPushToSAP.transPushToSAPListTitle[i]);
            cell.setCellStyle(style);
        }

        //向单元格里添加数据
        for(short i=0;i<list.size();i++){
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(list.get(i).getForeignKey());
            row.createCell(1).setCellValue(list.get(i).getRequestData());
            row.createCell(2).setCellValue(list.get(i).getResponseDatas());
            row.createCell(3).setCellValue(list.get(i).getStatus());
            SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (list.get(i).getRequestTime() == null){
                row.createCell(4).setCellValue("");
            }else {
                row.createCell(4).setCellValue(sdf2.format(list.get(i).getRequestTime()));
            }
            row.createCell(5).setCellValue(list.get(i).getZlsin());
            row.createCell(6).setCellValue(list.get(i).getMatnr());
            row.createCell(7).setCellValue(list.get(i).getMenge());
            row.createCell(8).setCellValue(list.get(i).getZlgorto());
            row.createCell(9).setCellValue(list.get(i).getZlgorti());

        }
        return wb;

    }

    private String toCellString(Object obj){
        if(obj != null){
            return obj.toString();
        }
        return "";
    }

    private List<EisInterfaceDataLog> getPushToSapListData(Map<String, Object> params) {
        ServiceResult<List<EisInterfaceDataLog>> result = transPushToSapService
                .getTransPushToSapList(params);
        return result.getResult();
    }

    @RequestMapping(value = {"/exportPushToSapList"})
    void exportPurchaseStockList(@RequestParam(required = false) String foreignKey,
                                 @RequestParam(required = false) String status,
                                 @RequestParam(required = false) String startDate,
                                 @RequestParam(required = false) String endDate,
                                 HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("cnStockSyncsId", foreignKey);
        params.put("status", status);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        // 调用业务Service
        Integer pageIndex = 0;
        List<EisInterfaceDataLog> eisInterfaceDataList= new ArrayList<>();

        while (true){
            params.put("m",1000*pageIndex);
            params.put("n",1000);
            pageIndex++;
            List<EisInterfaceDataLog> list = transPushToSapService.getEisInterfaceDataList(params);
            if(list.isEmpty()||list.size()==0){
                break;
            }
            eisInterfaceDataList.addAll(list);
        }
        for(EisInterfaceDataLog eisInterface : eisInterfaceDataList){
            net.sf.json.JSONArray rpjsonArray = net.sf.json.JSONArray.fromObject(eisInterface.getResponseDatas().replace("logout",""));
            net.sf.json.JSONArray rqjsonArray = net.sf.json.JSONArray.fromObject(eisInterface.getRequestData());
            for(int i = 0 ;i < rqjsonArray.size() ; i++){
                eisInterface.setZlsin(rqjsonArray.getJSONObject(i).getString("zlsin"));
                eisInterface.setMatnr(rqjsonArray.getJSONObject(i).getString("matnr"));
                eisInterface.setMenge(rqjsonArray.getJSONObject(i).getString("menge"));
                eisInterface.setZlgorto(rqjsonArray.getJSONObject(i).getString("zlgorto"));
                eisInterface.setZlgorti(rqjsonArray.getJSONObject(i).getString("zlgorti"));
            }
            for(int i = 0 ;i < rpjsonArray.size() ; i++){
                if("E".equals(rpjsonArray.getJSONObject(i).getString("type"))){
                    eisInterface.setStatus("推送失败");
                    break;
                }else {
                    eisInterface.setStatus("成功");
                }
            }
        }
        HSSFWorkbook wb= getDetailsData(eisInterfaceDataList);
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


