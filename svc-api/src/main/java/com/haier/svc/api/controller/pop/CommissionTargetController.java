package com.haier.svc.api.controller.pop;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.distribute.data.model.CommissionTarget;
import com.haier.distribute.service.DistributeCenterCommissionTargetService;
import com.haier.svc.api.controller.util.ExportData;
import com.haier.svc.api.controller.util.ExportExcelUtil;

import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;


@Controller
@RequestMapping("pop/commission_target")
public class CommissionTargetController {

    @Resource
    private DistributeCenterCommissionTargetService commissionTargetService;


    //菜单配置显示佣金目标页面
    @RequestMapping("/commission_targetList")
    public String showCommissionList() {

        return "pop/commission_target/commission_targetList";
    }
  //菜单配置显示佣金结算页面
  	 @RequestMapping("/commission_invoiceList")
  	    public String showProduct1() {

  	        return "pop/commission_target/commission_invoiceList";
  	    }
  		//菜单配置显示佣金结算页面
  	 @RequestMapping("/commission_invoiceList1")
  	    public String showProduct2() {

  	        return "pop/commission_target/commission_invoiceList1";
  	    }
  	 
	 @RequestMapping("/test1")
	    public String test1() {

	        return "pop/commission_target/test1";
	    }
	 @RequestMapping("/test2")
	    public String test2() {

	        return "pop/commission_target/test2";
	    }
	 @RequestMapping("/test3")
	    public String test3() {

	        return "pop/commission_target/test3";
	    }
	 @RequestMapping("/test4")
	    public String test4() {

	        return "pop/commission_target/test4";
	    }
	 @RequestMapping("/test5")
	    public String test5() {

	        return "pop/commission_target/test5";
	    }
    /**
     * 佣金目标分页查询
     *
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/commissionListF", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findOrderProductList(int page, int rows, CommissionTarget condition) {
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);

        return commissionTargetService.findCommission_targetList(pager, condition);
    }
    
  //查询季度佣金结算
  		@RequestMapping(value="/commission_product_invoice", method = RequestMethod.POST)
  		@ResponseBody
  		public JSONObject Commission_product_invoice(int page, int rows,String categoryId ,String startTime ,String endTime,String policyYear,int channelId,int channelType,int brandId) {
  			 page = page == 0 ? 1 : page;
  		        PagerInfo pager = new PagerInfo(rows, page);
  			return commissionTargetService.commission_product_invoice(pager,categoryId , startTime , endTime, policyYear, channelId, channelType, brandId);
  		}
    		//查询月度佣金结算
  		@RequestMapping(value="/commission_product_invoice1", method = RequestMethod.POST)
  		@ResponseBody
  		public JSONObject Commission_product_invoice1(int page, int rows,String categoryId ,String startTime ,String endTime,String policyYear,int channelId,int channelType,int brandId) {
  			 page = page == 0 ? 1 : page;
  		        PagerInfo pager = new PagerInfo(rows, page);
  			return commissionTargetService.commission_product_invoice1(pager,categoryId , startTime , endTime, policyYear, channelId, channelType, brandId);
  		}
    	//佣金目标添加
  		@RequestMapping(value="/addcommission", method = RequestMethod.POST)
  	    @ResponseBody

  	    public int addCommission( CommissionTarget commission ){


  			return commissionTargetService.addCommission(commission);
  	    }

  		//佣金目标修改
  		@RequestMapping(value="/updatecommission", method = RequestMethod.POST)
  	    @ResponseBody
  	    public int updateCommission(CommissionTarget commission) {

  			return commissionTargetService.updateCommission(commission);
  	    }
  		//佣金目标删除
  		@RequestMapping(value="/deletecommission", method = RequestMethod.POST)
  	    @ResponseBody
  	    public int deleteCommission(int id) {

  			return commissionTargetService.deleteCommission(id);
  	    }
  		//佣金目标校验
  		@RequestMapping(value="/jiaoyancommission", method = RequestMethod.POST)
  	    @ResponseBody
  	    public Boolean jiaoyan(CommissionTarget commission ) {

  			return commissionTargetService.jiaoyanCommission(commission);
  	    }
  	//季度佣金结算导出
  		@RequestMapping(value="/exportCommission_invoice", method = RequestMethod.GET)
  	    @ResponseBody
  	    public void exportProductList(String categoryId ,String policyYear,int channelId,int channelType,int brandId
  	    		,HttpServletResponse res) throws IOException {
  			JSONArray value=commissionTargetService.commission_product_invoice(categoryId , policyYear, channelId, channelType, brandId);

  	        // 1.创建一个workbook，对应一个Excel文件
  	        XSSFWorkbook wb = new XSSFWorkbook();
  	        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
  	        XSSFSheet sheet = wb.createSheet("佣金结算");
  	        sheet.setColumnWidth(0, (int) (10 * 256));
  	        sheet.setColumnWidth(1, (int) 10 * 256);
  	        sheet.setColumnWidth(2, (int) 10 * 256);
  	        sheet.setColumnWidth(3, (int) 12 * 256);
  	        sheet.setColumnWidth(4, (int) 10 * 256);
  	        sheet.setColumnWidth(5, (int) 7 * 256);
	        sheet.setColumnWidth(6, (int) 12 * 256);
	        sheet.setColumnWidth(7, (int) 12 * 256);
  	        sheet.setColumnWidth(8, (int) 10 * 256);
  	        sheet.setColumnWidth(9, (int) 7 * 256);
	        sheet.setColumnWidth(10, (int) 12 * 256);
	        sheet.setColumnWidth(11, (int) 12 * 256);
  	        sheet.setColumnWidth(12, (int) 10 * 256);
  	        sheet.setColumnWidth(13, (int) 7 * 256);
	        sheet.setColumnWidth(14, (int) 12 * 256);
	        sheet.setColumnWidth(15, (int) 12 * 256);
  	        sheet.setColumnWidth(16, (int) 10 * 256);
  	        sheet.setColumnWidth(17, (int) 7 * 256);
	        sheet.setColumnWidth(18, (int) 12 * 256);
	        sheet.setColumnWidth(19, (int) 12 * 256);
  	        sheet.setColumnWidth(20, (int) 10 * 256);
  	        sheet.setColumnWidth(21, (int) 7 * 256);
	        sheet.setColumnWidth(22, (int) 12 * 256);
//  	      sheet.setColumnWidth(0, "1".getBytes().length*21*256);
  	    sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
  	  sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
  	sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
  	sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 6));
  	sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 10));
  	sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 14));
  	sheet.addMergedRegion(new CellRangeAddress(0, 0, 15, 18));
  	sheet.addMergedRegion(new CellRangeAddress(0, 0, 19, 22));
  	        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
  	        XSSFRow row = sheet.createRow((int) 0);
  	        XSSFRow row1 = sheet.createRow((int) 1);
  	        
  	        // 4.创建单元格，设置值表头，设置表头居中
  	        XSSFCellStyle style = wb.createCellStyle();
  	        // 居中格式
  	        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
  	        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
  	        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
  	        style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
  	        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
  	        style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
  	        style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
  	        int length = ExportData.commission_invoice1.length;
  	        int length2 = ExportData.commission_invoice2.length;
  	        // 设置表头
  	        for (int i= 0; length - 1 >= i; i++) {
  	            XSSFCell cell = row.createCell(i);
  	            XSSFCell cell1 = row1.createCell(i);
  	            cell.setCellValue(ExportData.commission_invoice1[i]);
  	            cell1.setCellValue(ExportData.commission_invoice2[i]);
  	            cell.setCellStyle(style);
  	            cell1.setCellStyle(style);
  	            
  	        }
  	        
  	      
  	        //向单元格里添加数据  
  	        for (short i = 0; i < value.size(); i++) {
  	        	JSONObject object = (JSONObject) value.get(i);
  	            row = sheet.createRow(i + 2);
  	            row.createCell(0).setCellValue(object.getString("col04"));
  	            row.createCell(1).setCellValue(object.getString("col05"));
  	            row.createCell(2).setCellValue(object.getString("col06"));
  	            row.createCell(3).setCellValue(object.getString("col07"));
	            row.createCell(4).setCellValue(object.getString("col08"));
	            row.createCell(5).setCellValue(object.getString("col09"));
	            row.createCell(6).setCellValue(object.getString("col10"));
	            row.createCell(7).setCellValue(object.getString("col11"));
	            row.createCell(8).setCellValue(object.getString("col12"));
	            row.createCell(9).setCellValue(object.getString("col13"));
	            row.createCell(10).setCellValue(object.getString("col14"));
	            row.createCell(11).setCellValue(object.getString("col15"));
	            row.createCell(12).setCellValue(object.getString("col16"));
	            row.createCell(13).setCellValue(object.getString("col17"));
	            row.createCell(14).setCellValue(object.getString("col18"));
	            row.createCell(15).setCellValue(object.getString("col19"));
	            row.createCell(16).setCellValue(object.getString("col20"));
	            row.createCell(17).setCellValue(object.getString("col21"));
	            row.createCell(18).setCellValue(object.getString("col22"));
	            row.createCell(19).setCellValue(object.getString("col23"));
	            row.createCell(20).setCellValue(object.getString("col24"));
	            row.createCell(21).setCellValue(object.getString("col25"));
	            row.createCell(22).setCellValue(object.getString("col26"));
  	        }

  	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
  	        java.util.Date date = new java.util.Date();
  	        String str = sdf.format(date);
  	        String fileName = "佣金结算" + str;

  	        ByteArrayOutputStream os = new ByteArrayOutputStream();
  	        wb.write(os);
  	        byte[] content = os.toByteArray();
  	        InputStream is = new ByteArrayInputStream(content);
  	        ExportExcelUtil.exportCommon(is, fileName, res);
  	    }
  		
  		//月度导出
  		@RequestMapping(value="/exportCommission_invoice1", method = RequestMethod.GET)
  	    @ResponseBody
  	    public void exportProductList1(String categoryId ,String policyYear,int channelId,int channelType,int brandId
  	    		,HttpServletResponse res) throws IOException {
  			JSONArray value=commissionTargetService.commission_product_invoice1(categoryId , policyYear, channelId, channelType, brandId);
  	        // 1.创建一个workbook，对应一个Excel文件
  	        XSSFWorkbook wb = new XSSFWorkbook();
  	        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
  	        XSSFSheet sheet = wb.createSheet("月度佣金结算");
  	        sheet.setColumnWidth(0, (int) (10 * 256));
  	        sheet.setColumnWidth(1, (int) 10 * 256);
  	        sheet.setColumnWidth(2, (int) 10 * 256);
  	        sheet.setColumnWidth(3, (int) 12 * 256);
  	        sheet.setColumnWidth(4, (int) 10 * 256);
  	        sheet.setColumnWidth(5, (int) 7 * 256);
	        sheet.setColumnWidth(6, (int) 12 * 256);
	        sheet.setColumnWidth(7, (int) 12 * 256);
  	        sheet.setColumnWidth(8, (int) 10 * 256);
  	        sheet.setColumnWidth(9, (int) 7 * 256);
	        sheet.setColumnWidth(10, (int) 12 * 256);
	        sheet.setColumnWidth(11, (int) 12 * 256);
  	        sheet.setColumnWidth(12, (int) 10 * 256);
  	        sheet.setColumnWidth(13, (int) 7 * 256);
	        sheet.setColumnWidth(14, (int) 12 * 256);
	        sheet.setColumnWidth(15, (int) 12 * 256);
  	        sheet.setColumnWidth(16, (int) 10 * 256);
  	        sheet.setColumnWidth(17, (int) 7 * 256);
	        sheet.setColumnWidth(18, (int) 12 * 256);
	        sheet.setColumnWidth(19, (int) 12 * 256);
  	        sheet.setColumnWidth(20, (int) 10 * 256);
  	        sheet.setColumnWidth(21, (int) 7 * 256);
	        sheet.setColumnWidth(22, (int) 12 * 256);
	        sheet.setColumnWidth(23, (int) 12 * 256);
  	        sheet.setColumnWidth(24, (int) 10 * 256);
  	        sheet.setColumnWidth(25, (int) 7 * 256);
	        sheet.setColumnWidth(26, (int) 12 * 256);
	        sheet.setColumnWidth(27, (int) 12 * 256);
  	        sheet.setColumnWidth(28, (int) 10 * 256);
  	        sheet.setColumnWidth(29, (int) 7 * 256);
	        sheet.setColumnWidth(30, (int) 12 * 256);
	        sheet.setColumnWidth(31, (int) 12 * 256);
  	        sheet.setColumnWidth(32, (int) 10 * 256);
  	        sheet.setColumnWidth(33, (int) 7 * 256);
	        sheet.setColumnWidth(34, (int) 12 * 256);
	        sheet.setColumnWidth(35, (int) 12 * 256);
  	        sheet.setColumnWidth(36, (int) 10 * 256);
  	        sheet.setColumnWidth(37, (int) 7 * 256);
	        sheet.setColumnWidth(38, (int) 12 * 256);
	        sheet.setColumnWidth(39, (int) 12 * 256);
  	        sheet.setColumnWidth(40, (int) 10 * 256);
  	        sheet.setColumnWidth(41, (int) 7 * 256);
	        sheet.setColumnWidth(42, (int) 12 * 256);
	        sheet.setColumnWidth(43, (int) 12 * 256);
  	        sheet.setColumnWidth(44, (int) 10 * 256);
  	        sheet.setColumnWidth(45, (int) 7 * 256);
	        sheet.setColumnWidth(46, (int) 12 * 256);
	        sheet.setColumnWidth(47, (int) 12 * 256);
  	        sheet.setColumnWidth(48, (int) 10 * 256);
  	        sheet.setColumnWidth(49, (int) 7 * 256);
	        sheet.setColumnWidth(50, (int) 12 * 256);
	        sheet.setColumnWidth(51, (int) 12 * 256);
  	        sheet.setColumnWidth(52, (int) 10 * 256);
  	        sheet.setColumnWidth(53, (int) 7 * 256);
	        sheet.setColumnWidth(54, (int) 12 * 256);
//  	      sheet.setColumnWidth(0, "1".getBytes().length*21*256);
  	    sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
  	  sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
  	sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
  	sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 6));
  	sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 10));
  	sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 14));
  	sheet.addMergedRegion(new CellRangeAddress(0, 0, 15, 18));
  	sheet.addMergedRegion(new CellRangeAddress(0, 0, 19, 22));
  	
  	sheet.addMergedRegion(new CellRangeAddress(0, 0, 23, 26));
  	sheet.addMergedRegion(new CellRangeAddress(0, 0, 27, 30));
  	sheet.addMergedRegion(new CellRangeAddress(0, 0, 31, 34));
  	sheet.addMergedRegion(new CellRangeAddress(0, 0, 35, 38));
  	sheet.addMergedRegion(new CellRangeAddress(0, 0, 39, 42));
  	sheet.addMergedRegion(new CellRangeAddress(0, 0, 43, 46));
  	sheet.addMergedRegion(new CellRangeAddress(0, 0, 47, 50));
  	sheet.addMergedRegion(new CellRangeAddress(0, 0, 51, 54));
  	        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
  	        XSSFRow row = sheet.createRow((int) 0);
  	        XSSFRow row1 = sheet.createRow((int) 1);
  	        
  	        // 4.创建单元格，设置值表头，设置表头居中
  	        XSSFCellStyle style = wb.createCellStyle();
  	        // 居中格式
  	        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
  	        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
  	        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
  	        style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
  	        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
  	        style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
  	        style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
  	        int length = ExportData.commission_invoice3.length;
  	        int length2 = ExportData.commission_invoice4.length;
  	        // 设置表头
  	        for (int i= 0; length - 1 >= i; i++) {
  	            XSSFCell cell = row.createCell(i);
  	            XSSFCell cell1 = row1.createCell(i);
  	            cell.setCellValue(ExportData.commission_invoice3[i]);
  	            cell1.setCellValue(ExportData.commission_invoice4[i]);
  	            cell.setCellStyle(style);
  	            cell1.setCellStyle(style);
  	            
  	        }
  	        
  	      
  	        //向单元格里添加数据  
  	        for (short i = 0; i < value.size(); i++) {
  	        	JSONObject object = (JSONObject) value.get(i);
  	            row = sheet.createRow(i + 2);
  	            row.createCell(0).setCellValue(object.getString("col04"));
  	            row.createCell(1).setCellValue(object.getString("col05"));
  	            row.createCell(2).setCellValue(object.getString("col06"));
  	            row.createCell(3).setCellValue(object.getString("col07"));
	            row.createCell(4).setCellValue(object.getString("col08"));
	            row.createCell(5).setCellValue(object.getString("col09"));
	            row.createCell(6).setCellValue(object.getString("col10"));
	            row.createCell(7).setCellValue(object.getString("col11"));
	            row.createCell(8).setCellValue(object.getString("col12"));
	            row.createCell(9).setCellValue(object.getString("col13"));
	            row.createCell(10).setCellValue(object.getString("col14"));
	            row.createCell(11).setCellValue(object.getString("col15"));
	            row.createCell(12).setCellValue(object.getString("col16"));
	            row.createCell(13).setCellValue(object.getString("col17"));
	            row.createCell(14).setCellValue(object.getString("col18"));
	            row.createCell(15).setCellValue(object.getString("col19"));
	            row.createCell(16).setCellValue(object.getString("col20"));
	            row.createCell(17).setCellValue(object.getString("col21"));
	            row.createCell(18).setCellValue(object.getString("col22"));
	            row.createCell(19).setCellValue(object.getString("col23"));
	            row.createCell(20).setCellValue(object.getString("col24"));
	            row.createCell(21).setCellValue(object.getString("col25"));
	            row.createCell(22).setCellValue(object.getString("col26"));

	            row.createCell(23).setCellValue(object.getString("col27"));
	            row.createCell(24).setCellValue(object.getString("col28"));
	            row.createCell(25).setCellValue(object.getString("col29"));
	            row.createCell(26).setCellValue(object.getString("col30"));
	            row.createCell(27).setCellValue(object.getString("col31"));
	            row.createCell(28).setCellValue(object.getString("col32"));
	            row.createCell(29).setCellValue(object.getString("col33"));
	            row.createCell(30).setCellValue(object.getString("col34"));
	            row.createCell(31).setCellValue(object.getString("col35"));
	            row.createCell(32).setCellValue(object.getString("col36"));
	            row.createCell(33).setCellValue(object.getString("col37"));
	            row.createCell(34).setCellValue(object.getString("col38"));
	            row.createCell(35).setCellValue(object.getString("col39"));
	            row.createCell(36).setCellValue(object.getString("col40"));
	            row.createCell(37).setCellValue(object.getString("col41"));
	            row.createCell(38).setCellValue(object.getString("col42"));
	            row.createCell(39).setCellValue(object.getString("col43"));
	            
	            row.createCell(40).setCellValue(object.getString("col44"));
	            row.createCell(41).setCellValue(object.getString("col45"));
	            row.createCell(42).setCellValue(object.getString("col46"));
	            row.createCell(43).setCellValue(object.getString("col47"));
	            row.createCell(44).setCellValue(object.getString("col48"));
	            row.createCell(45).setCellValue(object.getString("col49"));
	            row.createCell(46).setCellValue(object.getString("col50"));
	            row.createCell(47).setCellValue(object.getString("col51"));
	            row.createCell(48).setCellValue(object.getString("col52"));
	            row.createCell(49).setCellValue(object.getString("col53"));
	            row.createCell(50).setCellValue(object.getString("col54"));
	            row.createCell(51).setCellValue(object.getString("col55"));
	            row.createCell(52).setCellValue(object.getString("col56"));
	            row.createCell(53).setCellValue(object.getString("col57"));
	            row.createCell(54).setCellValue(object.getString("col58"));
  	        }

  	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
  	        java.util.Date date = new java.util.Date();
  	        String str = sdf.format(date);
  	        String fileName = "月度佣金结算" + str;

  	        ByteArrayOutputStream os = new ByteArrayOutputStream();
  	        wb.write(os);
  	        byte[] content = os.toByteArray();
  	        InputStream is = new ByteArrayInputStream(content);
  	        ExportExcelUtil.exportCommon(is, fileName, res);
  	    }
}