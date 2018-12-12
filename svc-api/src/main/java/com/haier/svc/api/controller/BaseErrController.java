package com.haier.svc.api.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haier.system.model.BaseErr;
import com.haier.system.service.SystemCenterBaseErrService;
import jxl.write.DateTime;

import org.apache.log4j.LogManager;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.svc.api.controller.util.ExportBaseErr;
import com.haier.svc.api.controller.util.ExportExcelUtil;

@Controller
@RequestMapping(value="BaseErr")
public class BaseErrController {
	
    private final static org.apache.log4j.Logger logger = LogManager
            .getLogger(T2OrderQueryController.class);
	
//    @Reference(version="1.0.0")
//	private BaseErrService baseErrService;
    @Autowired
    private SystemCenterBaseErrService systemCenterBaseErrService;

	@RequestMapping("/toBaseError")
	public String toBaseError(){
		return  "purchase/baseError";
	}
	
    /**
     * 查询错误信息
     * @param start_week
     * @param end_week
     * @param order_id
     * @param menu_path
     * @param interface_path
     * @param rows
     * @param page
     * @param request
     * @param response
     */
    @RequestMapping(value = {"/queryBaseErr"}, method = {RequestMethod.POST})
    void queryBaseErr(@RequestParam(required = false) String start_week,
                      @RequestParam(required = false) String end_week,
                      @RequestParam(required = false) String order_id,
                      @RequestParam(required = false) String menu_path,
                      @RequestParam(required = false) String interface_path,
                      @RequestParam(required = false) Integer rows,
                      @RequestParam(required = false) Integer page,
                      HttpServletRequest request, HttpServletResponse response) {
    	
        try {
            String startDay = "";
            String endDay = "";
            if (rows == null)
                rows = 20;
            if (page == null)
                page = 1;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("start_week", start_week);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (end_week != null && !"".equals(end_week)) {
                Date eDate = sdf.parse(end_week);
                Calendar rightNow = Calendar.getInstance();
                rightNow.setTime(eDate);
                rightNow.add(Calendar.DAY_OF_YEAR, 1);
                String str = sdf.format(rightNow.getTime());
                params.put("end_week", str);
            }
            params.put("order_id", order_id);
            params.put("menu_path", menu_path);
            params.put("interface_path", interface_path);
            params.put("m", (page - 1) * rows);
            params.put("n", rows);
            
            ServiceResult<List<BaseErr>> result = systemCenterBaseErrService.queryErr(params);
            
            Map<String, Object> retMap = new HashMap<String, Object>();
            Gson gson = new Gson();
            retMap.put("rows", result.getResult());
            retMap.put("total", result.getPager().getRowsCount());
            
            response.addHeader("Content-type", "text/html;charset=utf-8");
			response.getWriter().write(gson.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
            
        } catch (IOException e) {
            logger.error("", e);
            throw new BusinessException("失败" + e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 错误信息导出
     * @param exportData
     * @param res
     */
    @RequestMapping(value = {"/exportT2OrderList.export"})
    void exportErrExcel(@RequestParam(required = false) String order_id,
            @RequestParam(required = false) String menu_path,
            @RequestParam(required = false) String interface_path,
            @RequestParam(required = false) String start_week,
            @RequestParam(required = false) String end_week,
            HttpServletRequest request,HttpServletResponse response) {
    	
        Map<String, Object> params = new HashMap<String, Object>();

        
/*        JSONArray exportJson = new JSONArray();
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

        params.put("order_list", exportArray);*/
        
        params.put("order_id", order_id);
        params.put("menu_path", menu_path);
        params.put("interface_path", interface_path);
        params.put("start_week", start_week);
        params.put("end_week", end_week);
        
        HSSFWorkbook  wb= getDetailsData(params);
   
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");  
        Date date=new Date();  
        String str=sdf.format(date); 
        String fileName = "错误信息"+str;
          
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
    
    public HSSFWorkbook getDetailsData(Map<String, Object> params) {
        ServiceResult<List<BaseErr>> result = systemCenterBaseErrService.queryErr(params);
       // 获得条数
       List<BaseErr> errList = new ArrayList<BaseErr>();
       if (result.getSuccess() && result.getResult() != null) {
           errList = result.getResult();
       }
      // 1.创建一个workbook，对应一个Excel文件
         HSSFWorkbook wb = new HSSFWorkbook();
         // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
         HSSFSheet sheet = wb.createSheet("错误信息");
         int length = ExportBaseErr.errListQuery.length;
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
           cell.setCellValue(ExportBaseErr.errListQuery[i]);
           cell.setCellStyle(style);          
         }

         //向单元格里添加数据  
         for(short i=0;i<errList.size();i++){              
             row = sheet.createRow(i+1);  
             row.createCell(0).setCellValue(errList.get(i).getOrder_id());
             row.createCell(1).setCellValue(errList.get(i).getMenu_path());
             row.createCell(2).setCellValue(errList.get(i).getInterface_path());
             row.createCell(3).setCellValue(errList.get(i).getMessage());
             //row.createCell(4).setCellValue(errList.get(i).getLog_time());
             
             Timestamp log_time = errList.get(i).getLog_time();
             if(!"".equals(log_time)){
            	 
                 SimpleDateFormat ltime =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
                 String str=ltime.format(log_time);
                 row.createCell(4).setCellValue(str);
             }
             //row.createCell(5).setCellValue(errList.get(i).getUser_id());
             //row.createCell(6).setCellValue(errList.get(i).getRole_id());
           }
		return wb;
   }
    
}
