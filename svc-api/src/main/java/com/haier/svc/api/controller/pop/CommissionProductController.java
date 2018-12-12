package com.haier.svc.api.controller.pop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.haier.afterSale.model.Json;
import com.haier.distribute.data.model.Products;
import com.haier.distribute.service.DistributeCenterCommissionProductService;
import com.haier.order.model.Ustring;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsondoc.core.annotation.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.distribute.data.model.CommissionProduct;
import com.haier.svc.api.controller.util.ExportData;
import com.haier.svc.api.controller.util.ExportExcelUtil;

/***
 * 空调政策表
 * @author 孙玉凯
 *
 */
@Controller
@Api(name = "", description = "PopController")
@RequestMapping(value ="pop/commission_product")
public class CommissionProductController {

	@Autowired
	    private DistributeCenterCommissionProductService distributeCenterCommissionProductService;

  //菜单配置显示产品政策页面
  @RequestMapping("/commission_productList")
  public String showCommissionList() {

      return "pop/commission_product/commission_productList";
  }

  /**
   * 产品政策分页查询
   *
   * @param page
   * @param rows
   * @return
   */
  @RequestMapping(value = "/commission_productListF", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject findOrderProductList(int page, int rows, CommissionProduct condition) {
      page = page == 0 ? 1 : page;
      PagerInfo pager = new PagerInfo(rows, page);

      return distributeCenterCommissionProductService.CommissionListf(pager, condition);
  }
  	//产品政策添加
		@RequestMapping(value="/addcommission_product", method = RequestMethod.POST)
	    @ResponseBody

	    public int addCommission( CommissionProduct commission ){


			return distributeCenterCommissionProductService.addCommission(commission);
	    }

		//产品政策修改
		@RequestMapping(value="/updatecommission_product", method = RequestMethod.POST)
	    @ResponseBody
	    public int updateCommission(CommissionProduct commission) {

			return distributeCenterCommissionProductService.updateCommission(commission);
	    }
		//产品政策删除
		@RequestMapping(value="/deletecommission_product", method = RequestMethod.POST)
	    @ResponseBody
	    public int deleteCommission(int id) {

			return distributeCenterCommissionProductService.deleteCommission(id);
	    }
		//产品政策校验
		@RequestMapping(value="/jiaoyancommission", method = RequestMethod.POST)
	    @ResponseBody
	    public Boolean jiaoyanCommission(CommissionProduct commission) {

			return distributeCenterCommissionProductService.jiaoyanCommission(commission);
	    }
		//产品政策导出
		@RequestMapping(value="/exportCommission_product", method = RequestMethod.GET)
	    @ResponseBody
	    public void exportProductList(CommissionProduct commission_product,HttpServletResponse res) throws IOException {
//	    	PopOrderProducts orderProducts = new PopOrderProducts();
	    	
	    	List<CommissionProduct>productList =distributeCenterCommissionProductService.selectexportCommission_productListf(commission_product);
//	    	List<PopOrderProducts> productList = popOrderService.exportOrderProductsList(orderProducts);

	        // 1.创建一个workbook，对应一个Excel文件
	        XSSFWorkbook wb = new XSSFWorkbook();
	        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
	        XSSFSheet sheet = wb.createSheet("产品政策");
	        sheet.setColumnWidth(0, (int) (21.57 * 256));
	        sheet.setColumnWidth(1, (int) 21.57 * 256);
	        sheet.setColumnWidth(2, (int) 13.43 * 256);
	        sheet.setColumnWidth(3, (int) 21.57 * 256);
	        sheet.setColumnWidth(4, (int) 21.57 * 256);
	      

	        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
	        XSSFRow row = sheet.createRow(0);
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

	        int length = ExportData.commission_productListTitle.length;
	        // 设置表头
	        for (int i= 0; length - 1 >= i; i++) {
	            XSSFCell cell = row.createCell(i);
	            cell.setCellValue(ExportData.commission_productListTitle[i]);
	            cell.setCellStyle(style);
	        }
	      
	        //向单元格里添加数据  
	        for (short i = 0; i < productList.size(); i++) {
	          
	            row = sheet.createRow(i + 1);
	            row.createCell(0).setCellValue(productList.get(i).getCategoryId());
	            row.createCell(1).setCellValue(productList.get(i).getCategoryName());
	            if(Integer.parseInt( commission_product.getChannelType()) == 1){
	            	row.createCell(2).setCellValue(productList.get(i).getDistributionName());
	            }else{
	            	row.createCell(2).setCellValue(productList.get(i).getChannelName());
	            }
	            row.createCell(3).setCellValue(productList.get(i).getSku());
	            row.createCell(4).setCellValue(productList.get(i).getProductName());
	            row.createCell(5).setCellValue(productList.get(i).getBrandId());
	            row.createCell(6).setCellValue(productList.get(i).getBrandName());
	            row.createCell(7).setCellValue(productList.get(i).getMonthPolicy().toString()+"%");
	        
	        }

	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	        java.util.Date date = new java.util.Date();
	        String str = sdf.format(date);
	        String fileName = "产品政策" + str;

	        ByteArrayOutputStream os = new ByteArrayOutputStream();
	        wb.write(os);
	        byte[] content = os.toByteArray();
	        InputStream is = new ByteArrayInputStream(content);
	        ExportExcelUtil.exportCommon(is, fileName, res);
	    }
		
		/**
		 * 判断导入文档表头是否正确
		 *
		 * @param firstLineData
		 * @param checkStr
		 * @return
		 */
		public boolean checkDataTemplate(String[] firstLineData, String checkStr) {
			boolean flag = false;
			StringBuffer sb = new StringBuffer();
			for (String str : firstLineData) {
				if (sb.length() > 0)
					sb.append(",");
				sb.append(str.trim());
			}
			String nullStr = sb.toString().replace(checkStr, "").replace(",", "");
			if (nullStr == null || "".equals(nullStr)) {
				flag = true;
			}
			return flag;
		}
		/**
		 * 导出模板
		 */
		@RequestMapping(value="/exportdemo", method = RequestMethod.GET)
	    @ResponseBody
	    public void exportDemo(HttpServletResponse res) throws IOException {
//	    	PopOrderProducts orderProducts = new PopOrderProducts();
	    	

	        // 1.创建一个workbook，对应一个Excel文件
	        XSSFWorkbook wb = new XSSFWorkbook();
	        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
	        XSSFSheet sheet = wb.createSheet("产品政策模板");
	        sheet.setColumnWidth(0, (int) (21.57 * 256));
	        sheet.setColumnWidth(1, (int) 21.57 * 256);
	        sheet.setColumnWidth(2, (int) 13.43 * 256);
	        sheet.setColumnWidth(3, (int) 21.57 * 256);
	        sheet.setColumnWidth(4, (int) 21.57 * 256);

	        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
	        XSSFRow row = sheet.createRow(0);
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

	        int length = ExportData.commission_productdemoTitle.length;
	        // 设置表头
	        for (int i= 0; length - 1 >= i; i++) {
	            XSSFCell cell = row.createCell(i);
	            cell.setCellValue(ExportData.commission_productdemoTitle[i]);
	            cell.setCellStyle(style);
	        }
	      

	        String fileName = "产品政策模板";

	        ByteArrayOutputStream os = new ByteArrayOutputStream();
	        wb.write(os);
	        byte[] content = os.toByteArray();
	        InputStream is = new ByteArrayInputStream(content);
	        ExportExcelUtil.exportCommon(is, fileName, res);
	    }
		 /**
	     * 导入空调政策
	     *
	     * @param session
	     * @param res
	     * @throws IOException
	     */
		@RequestMapping(value="/importcommission_product", method = RequestMethod.POST)
	    @ResponseBody
		public Json importCommission_product(@RequestParam MultipartFile file) throws Exception {
		    Json json = new Json();
		  CommissionProduct commission_product =new CommissionProduct();
//		    List<SerachInvoiceManage> invoicelist = new ArrayList<SerachInvoiceManage>();
		    int rowsCount = 0;
		    // 接收校验字段
		    StringBuffer sc = new StringBuffer(2000);
		    // 导入文件名
		    String filePath = file.getOriginalFilename();
		    //得到 Excel 工作表对象
		    Sheet sheet = null;
		    Row row = null;
		    Map<Object, String> map = new HashMap();
		    if (filePath.endsWith(".xlsx")) {//判断是否是以  .xls 结尾     91-03版Excel
		    	 XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		       sheet = workbook.getSheetAt(0);
		    }else{
		    	  sc.append("请使用2007或以上版本excel文档导入<br/>");
		    	  json.setMsg("导入失败！<br/>" + sc.toString());
		    	  return json;

		    }
		    //返回 Excel 工作表中有多少行
		    rowsCount = sheet.getPhysicalNumberOfRows();

		    if (rowsCount <= 1) {
		      sc.append("报表内容不能为空!<br/>");
		      json.setSuccess(false);
		      json.setMsg("导入失败！<br/>" + sc.toString());
		      return json;

		    } else {
		    	boolean flage=true;
		      // 先检查
		    	
		      for (int rowindex = 1; rowindex < rowsCount; rowindex++) {
		        row = sheet.getRow(rowindex);
		        int j = rowindex + 1;
		        if (row == null) {
		          break;
		        }
		        String sku = Ustring.getString(row.getCell(1)).trim();//物料编码
		        String monthPolicy = Ustring.getString(row.getCell(3)).trim();//基础政策%
		        String channelsid = Ustring.getString(row.getCell(2)).trim();//渠道编码
		        Double policyYear = Double.parseDouble((((Ustring.getString(row.getCell(4)).trim()))));//政策年度
		        
		        //防止数据为空或者不符合条件的数据，进行验证
		         
		        if (sku == "") {
		          sc.append("第" + j + "行物料编码为空，请检查！");
		          json.setMsg("导入失败！<br/>" + sc.toString());
		          return json;
		        }
		        if(Ustring.isEmpty(map.get(sku))){
		        	 map.put(sku,sku);
		        	 
		         }else {
		        	 sc.append("第" + j + "行物料编码相同");
			          json.setMsg("导入失败！<br/>" + sc.toString());
			          return json;
		         }
		       
		        	
		        if (Double.parseDouble(Ustring.getString0(monthPolicy))<=0 || Double.parseDouble(Ustring.getString0(monthPolicy)) > 100 ) {
		          sc.append("第" + j + "行基础政策有误，请检查！");
		          json.setMsg("导入失败！<br/>" + sc.toString());
		          return json;

		        }
		        	String b=Ustring.getString(row.getCell(4)).trim();
		        if ((Ustring.getString(row.getCell(4)).trim()).length() !=6 ) {
		          sc.append("第" + j + "行政策年度有误，请检查！");
		          json.setMsg("导入失败！<br/>" + sc.toString());
		          return json;

		        }
		        Products products=new Products();
		        products.setSku(sku);
		        products.setProductName("%");
		        //根据sku查询品类id品牌id
		    	List<Products>productList =distributeCenterCommissionProductService.importCommission_productList(products.getSku(),products.getProductName());
		       
		    	
		    	  //根据渠道编码判断渠道
		     String	dis =distributeCenterCommissionProductService.Distribution_infoList(channelsid);
		     if(dis.equals("q")){
		    	 sc.append("第" + j + "行渠道编码表数据重复请联系管理员");
		          json.setMsg("导入失败！<br/>" + sc.toString());
		          return json;
		          	//返回错误信息渠道编码2张表重复
		 	}
		 	if(dis.equals("w")){
		 		 sc.append("第" + j + "行渠道编码不存在");
		          json.setMsg("导入失败！<br/>" + sc.toString());
		          return json;
		 	}
		 	if(dis.equals("e")){
		 		 sc.append("第" + j + "行渠道编码在数据库信息错误请联系管理员");
		          json.setMsg("导入失败！<br/>" + sc.toString());
		          return json;//返回错误信息数据库信息错误请联系管理员
		 	}
		 	int chantype=0;
		 	if(dis.substring(0, 1).equals("1")){
		 		 commission_product.setChannelType("1");
		 		  chantype=1;
		 		 int a=Integer.parseInt(dis.substring(1,dis.length()));
		 		commission_product.setChannelId(a);
		 	}
		 	if(dis.substring(0, 1).equals("2")){
		 		 commission_product.setChannelType("2");
		 		 chantype=2;
		 		 int a=Integer.parseInt(dis.substring(1,dis.length()));
		 		commission_product.setChannelId(a);
		 	}
		    	if(productList.size() ==0 )
		    	 {
			          sc.append("第" + j + "行物料编码有误，请检查！");
			          json.setMsg("导入失败！<br/>" + sc.toString());
			          return json;
			        }
		    	  int skuAll= distributeCenterCommissionProductService.skuAll(sku,chantype, (new   Double(policyYear)).intValue());
			         if(skuAll > 0){
			        	 json.setMsg("重复");
			        	 return json;
			         }
		    	   //根据品类id品牌id查询数据
		    	CommissionProduct commissionxinxi =distributeCenterCommissionProductService.AllName(productList.get(0).getBrandId(),productList.get(0).getProductTypeId());
		        	 commission_product.setBrandId(commissionxinxi.getBrandId());
		        	 commission_product.setBrandName(commissionxinxi.getBrandName());
		        	 commission_product.setCategoryId(commissionxinxi.getCategoryId());
		        	 commission_product.setCategoryName(commissionxinxi.getCategoryName());
			         commission_product.setSku(sku);
			         commission_product.setPolicyYear((new   Double(policyYear)).intValue());
			         commission_product.setMonthPolicy(new BigDecimal(monthPolicy));
			         commission_product.setProductName(productList.get(0).getProductName());
			         commission_product.setCreateTime(new Date());
				  distributeCenterCommissionProductService.addCommission_product(commission_product);
		      }
		    }
		      json.setSuccess(true);
		      json.setMsg("导入成功!");
		    
		    return json;

		  }						
	    
	    /**
	     * 重复调用导入
	     *
	     * @param session
	     * @param res
	     * @throws IOException
	     */
		@RequestMapping(value="/importcommission_product1", method = RequestMethod.POST)
	    @ResponseBody
		public Json importCommission_product1(@RequestParam MultipartFile file) throws Exception {
		    Json json = new Json();
		    CommissionProduct commission_product =new CommissionProduct();
//		    List<SerachInvoiceManage> invoicelist = new ArrayList<SerachInvoiceManage>();
		  
		    int rowsCount = 0;
		    // 接收校验字段
		    StringBuffer sc = new StringBuffer(2000);
		    // 导入文件名
		    String filePath = file.getOriginalFilename();
		    //得到 Excel 工作表对象
		    Sheet sheet = null;
		    Row row = null;
		    Map<Object, String> map = new HashMap();
		  if (filePath.endsWith(".xlsx")) {//判断是否是以  .xls 结尾     91-03版Excel
			 XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		      sheet = workbook.getSheetAt(0);
		    
		    }else{
		    	  sc.append("请使用2007或以上版本excel文档导入<br/>");
		    	  json.setMsg("导入失败！<br/>" + sc.toString());
		    	  return json;

		    }
		    //返回 Excel 工作表中有多少行
		    rowsCount = sheet.getPhysicalNumberOfRows();

		    if (rowsCount <= 1) {
		      sc.append("报表内容不能为空!<br/>");
		      json.setSuccess(false);
		      json.setMsg("导入失败！<br/>" + sc.toString());
		      return json;

		    } else {
		      // 先检查
		      for (int rowindex = 1; rowindex < rowsCount; rowindex++) {
		        row = sheet.getRow(rowindex);
		        int j = rowindex + 1;
		        if (row == null) {
		          break;
		        }
		      String channelsid = Ustring.getString(row.getCell(2)).trim();//渠道编码
		        String sku = Ustring.getString(row.getCell(1)).trim();//物料编码
		        String monthPolicy = Ustring.getString(row.getCell(3)).trim();//基础政策%
		        Double policyYear = Double.parseDouble((((Ustring.getString(row.getCell(4)).trim()))));//政策年度
		        //防止数据为空或者不符合条件的数据，进行验证
		         
		        if (sku == "") {
		          sc.append("第" + j + "行物料编码为空，请检查！");
		          json.setMsg("导入失败！<br/>" + sc.toString());
		          return json;
		        }
		        if(Ustring.isEmpty(map.get(sku))){
		        	 map.put(sku,sku);
		        	 
		         }else {
		        	 sc.append("第" + j + "行物料编码相同");
			          json.setMsg("导入失败！<br/>" + sc.toString());
			          return json;
		         }
		        
//		        ||  Integer.parseInt(monthPolicy) >100
		        if (Double.parseDouble(Ustring.getString0(monthPolicy))<=0 || Double.parseDouble(Ustring.getString0(monthPolicy)) > 100 ) {
		          sc.append("第" + j + "行基础政策有误，请检查！");
		          json.setMsg("导入失败！<br/>" + sc.toString());
		          return json;

		        }
		        Products products=new Products();
		        products.setSku(sku);
		        products.setProductName("%");
		        //根据sku查询品类id品牌id
		    	List<Products>productList =distributeCenterCommissionProductService.importCommission_productList(products.getSku(),products.getProductName());
		       
		    	if(productList.size() ==0 )
		    	 {
			          sc.append("第" + j + "行物料编码有误，请检查！");
			          json.setMsg("导入失败！<br/>" + sc.toString());
			          return json;
			        }
		    	 String	dis =distributeCenterCommissionProductService.Distribution_infoList(channelsid);
		    	   //根据品类id品牌id查询数据
		    	CommissionProduct commissionxinxi =distributeCenterCommissionProductService.AllName(productList.get(0).getBrandId(),productList.get(0).getProductTypeId());
		        	 commission_product.setBrandId(commissionxinxi.getBrandId());
		        	 commission_product.setBrandName(commissionxinxi.getBrandName());
		        	 commission_product.setCategoryId(commissionxinxi.getCategoryId());
		        	 commission_product.setCategoryName(commissionxinxi.getCategoryName());
			         commission_product.setSku(sku);
			         commission_product.setMonthPolicy(new BigDecimal(monthPolicy));
			         commission_product.setProductName(productList.get(0).getProductName());
			         commission_product.setPolicyYear((new   Double(policyYear)).intValue());
			      int skuAll=0;
			         if(dis.substring(0, 1).equals("1")){
			    	  skuAll= distributeCenterCommissionProductService.skuAll(sku,1,(new   Double(policyYear)).intValue());
			    	 commission_product.setChannelType("1");
	  		 		 int a=Integer.parseInt(dis.substring(1,dis.length()));
	  		 		commission_product.setChannelId(a);
			       }else{
			      skuAll= distributeCenterCommissionProductService.skuAll(sku,2,(new   Double(policyYear)).intValue());
			    commission_product.setChannelType("2");
		 		 int a=Integer.parseInt(dis.substring(1,dis.length()));
		 		commission_product.setChannelId(a);
			     }
			         if(skuAll >0){
			        	 commission_product.setId(skuAll);
						 distributeCenterCommissionProductService.updateCommission_product(commission_product);
			         }else{
			        	 commission_product.setId(0);
			        	 commission_product.setCreateTime(new Date());
						 distributeCenterCommissionProductService.addCommission_product(commission_product);
			         }
		      }
		    }
		      json.setSuccess(true);
		      json.setMsg("导入成功!");
		    
		    return json;

		  }		

}