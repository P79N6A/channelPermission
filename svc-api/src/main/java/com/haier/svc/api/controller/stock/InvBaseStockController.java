package com.haier.svc.api.controller.stock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.CodingErrorAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.Assert;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.order.model.Ustring;
import com.haier.purchase.data.model.CrmOrderItem;
import com.haier.stock.model.BaseStock;
import com.haier.stock.model.InvBaseStock;
import com.haier.stock.model.InvBaseStockExcel;
import com.haier.stock.model.InvStockLock;
import com.haier.stock.model.PopInvWarehouse;
import com.haier.svc.api.controller.stock.mode.BaseInventoryModel;
import com.haier.svc.api.controller.stock.mode.MemStockLockModel;
import com.haier.svc.api.controller.util.CrmExportData;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.controller.util.WebUtil;


@Controller
@RequestMapping("invBaseStock")
public class InvBaseStockController {

	private org.apache.log4j.Logger logger       = org.apache.log4j.LogManager.getLogger(this
            .getClass());
	
	/*当前页数*/
    private int                     pageIndex = 1;
    /*分页尺寸*/
    private int                     pageSize  = 10;
	
    @Autowired
    private BaseInventoryModel baseInventoryModel;
    
   @Autowired
    private MemStockLockModel memStockLockModel;
   
    private static final String CHECKSTR = "物料编码,库位编码,LES库位编码,总库位量,冻结库存数量,超卖待释放数量,创建时间,更新时间,操作";
    
    public void setBaseInventoryModel(BaseInventoryModel baseInventoryModel) {
		this.baseInventoryModel = baseInventoryModel;
	}
    public void setMemStockLockModel(MemStockLockModel memStockLockModel) {
    	this.memStockLockModel = memStockLockModel;
    }
	@RequestMapping(value = { "/loadInvBaseStockPage" }, method = { RequestMethod.GET })
    public String loadInvBaseStockPage() {
    	 
         return "stock/loadInvBaseStockPageList";
     }
	@RequestMapping(value = { "/getInvBaseStockList" }, method = { RequestMethod.POST })
	@ResponseBody
    public JSONObject getInvBaseStockList(@RequestParam(required = false) Integer pageIndex,
							          @RequestParam(required = false) String startDate,
	                                  @RequestParam(required = false) String endDate,
	                                  @RequestParam(required = false) String actualSelect,
	                                  @RequestParam(required = false) String stockQty,
	                                  @RequestParam(required = false) String frozenSelect,
	                                  @RequestParam(required = false) String frozenQty,
	                                  @RequestParam(required = false) String sku,
	                                  @RequestParam(required = false) String secCode,
	                                  @RequestParam(required = false) String selectType,
	                                  
							          Map<String, Object> modelMap,HttpServletRequest request,
							          HttpServletResponse response){
		JSONObject json = new JSONObject();
		  int size = Integer.parseInt(Ustring.getString0(request.getParameter("size")));
	      int start =Integer.parseInt(Ustring.getString0(request.getParameter("start")));
		try{
			Assert.notNull(baseInventoryModel, "Property 'baseInventoryModel' is required.");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap=handleParameters(paramMap,startDate,endDate,actualSelect,stockQty,frozenSelect, 
                                      frozenQty, sku,secCode,selectType);
			
			int pageIndexs =0;
			if (size <= 0) {
	        	size=20;
			}
	        if(start>0){
	        	pageIndexs=(start-1)  * size;

	        	if(pageIndexs==0){
	        		pageIndexs =1;
	        	}
			}else {
				pageIndexs =1;
			}
	        PagerInfo pager = new PagerInfo(size, pageIndexs);
	        if(pager.getPageIndex()==1) {
	        	 paramMap.put("start",0);
	        }else {
	        	 paramMap.put("start", pager.getPageIndex());
	        }
	        paramMap.put("size", pager.getPageSize());
			List<Map<String, Object>> lists=baseInventoryModel.queryByfrozenQtyGtStockQty(paramMap);
			pager.setRowsCount(baseInventoryModel.getfrozenQtyGtStockQtyCount(paramMap));
		json.put("total", pager.getRowsCount());
		json.put("rows", lists);
		}catch(Exception e){
			logger.error("[InvBaseStockController_getInvBaseStockList]查询冻结库存数量大于总库存数量时发生未知错误", e);
		}

	    return json;
	}
	
	
	@RequestMapping(value = { "/loadInvStockLockPage" }, method = { RequestMethod.GET })
    public ModelAndView  loadInvStockLockPage(@RequestParam(required = false) String secCode,
                                       @RequestParam(required = false) String sku,
                                       Map<String, Object> modelMap) {
		 ModelAndView mav = new ModelAndView();
		 mav.addObject("secCode",secCode);
		 mav.addObject("sku",sku);
		 mav.setViewName("stock/loadInvStockLockPageList");
		 return mav;
     }
	
	
	@RequestMapping(value = { "/getInvStockLockList" }, method = { RequestMethod.POST })
	@ResponseBody
    public JSONObject getInvStockLockList(@RequestParam(required = false) Integer pageIndex,
    		                          @RequestParam(required = false) String secCode,
    		                          @RequestParam(required = false) String sku,
    		                          @RequestParam(required = false) String refno,
    		                          @RequestParam(required = false) String lockQty,
							          @RequestParam(required = false) String startDate,
	                                  @RequestParam(required = false) String endDate,
							          Map<String, Object> modelMap,HttpServletRequest request,
							          HttpServletResponse response){
		Assert.notNull(baseInventoryModel, "Property 'baseInventoryModel' is required.");
        this.pageIndex = pageIndex == null || pageIndex <= 0 ? 1 : pageIndex;
        PagerInfo page = new PagerInfo(this.pageSize, this.pageIndex);
      //添加查询条件
        JSONObject json = new JSONObject();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        if(StringUtils.isNotEmpty(secCode)){
        	paramMap.put("secCode", secCode);
        }
        if(StringUtils.isNotEmpty(refno)){
        	paramMap.put("refno", refno);
        }
        if(StringUtils.isNotEmpty(sku)){
        	paramMap.put("sku", sku);
        }
        if(StringUtils.isNotEmpty(lockQty)){
        	paramMap.put("lockQty", lockQty);
        }
        if(StringUtils.isNotEmpty(startDate)){
     		paramMap.put("startDate",startDate+"00:00:00");
     	 }
     	 if(StringUtils.isNotEmpty(endDate)){
     		paramMap.put("endDate", endDate+"23:59:59");
     	 }
     	 paramMap.put("start", page.getStart());
         paramMap.put("size", page.getPageSize());
         List<Map<String, Object>> list = baseInventoryModel.getBySecCodeAndSku(paramMap);
     if (list != null && list.size() > 0) {
     	
			page.setRowsCount(baseInventoryModel.getBySecCodeAndSkuCount(paramMap));
			addPagerParam(modelMap, page);
			
			json.put("total", page.getRowsCount());
			json.put("rows", list);
     }	
		//json.put("pager", page);
		return json;

	}
	
	@RequestMapping(value = { "/batchRelease" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<String> batchReleaseLockStock(Map<String, Object> modelMap,
                                           HttpServletRequest request, HttpServletResponse response) {
		Assert.notNull(baseInventoryModel, "Property 'memStockLockModel' is required.");
		String jsonInvStockLock = request.getParameter("jsonInvStockLock");
        HttpJsonResult<String> jsonResult = new HttpJsonResult<String>();
        List<InvStockLock> lists;
		try {
			Gson gson = new Gson();
			Type type = new TypeToken<List<InvStockLock>>() {
            }.getType();
			lists = (List<InvStockLock>) gson.fromJson(jsonInvStockLock,type);
			if(!lists.isEmpty()){
	        	for(InvStockLock lock:lists){
	        		ServiceResult<Boolean> result = memStockLockModel.cancelLockStock(
	        				lock.getSku() == null ? "" : lock.getSku().trim(), lock.getSecCode() == null ? "" : lock.getSecCode().trim(),
	        						lock.getRefno() == null ? "" : lock.getRefno().trim(),
	        								lock.getLockQty()==null ? 0 :lock.getLockQty().intValue(),lock.getId().toString(), WebUtil
	        										.currentUserName(request));
	        		jsonResult.setData(result.getSuccess() ? "释放成功！" : "释放失败！");
	        		if (!result.getSuccess()) {
	        			jsonResult.setMessage(result.getMessage());
	        		}
	        	}
	        }
		} catch (Exception e) {
			logger.error("[InvBaseStockController_batchReleaseLockStock]批量释放全部锁定库存时发生未知错误", e);
			e.printStackTrace();
		}
        return jsonResult;
    }
	
	@RequestMapping(value = { "/exportInvBaseStockList.html" }, method = { RequestMethod.GET })
    public String exportInvBaseStockList(@RequestParam(required = false) Integer pageIndex,
							             @RequestParam(required = false) String startDate,
	                                     @RequestParam(required = false) String endDate,
	                                     @RequestParam(required = false) String actualSelect,
	                                     @RequestParam(required = false) String stockQty,
	                                     @RequestParam(required = false) String frozenSelect,
	                                     @RequestParam(required = false) String frozenQty,
	                                     @RequestParam(required = false) String sku,
	                                     @RequestParam(required = false) String secCode,
	                                     @RequestParam(required = false) String selectType,
							             Map<String, Object> modelMap,HttpServletRequest request,
							             HttpServletResponse response){
		try{
			Assert.notNull(baseInventoryModel, "Property 'baseInventoryModel' is required.");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap=handleParameters(paramMap,startDate,endDate,actualSelect,stockQty,frozenSelect, 
                    frozenQty, sku,secCode,selectType);
			List<Map<String, Object>> lists=baseInventoryModel.queryByfrozenQtyGtStockQty(paramMap);
			if (lists != null && lists.size() > 0) {
				modelMap.put("rowList", lists);
			}
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
	        String fileName = "inv_base_stock库存数据报表";
	        fileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
	        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".xls\"");
		}catch(Exception e){
			logger.error("[InvBaseStockController_exportInvBaseStockList]导出库存数据报表时发生未知错误", e);
		}
        return "stock/exportInvBaseStockPageGrid";
	}
	
	
	public Map<String, Object> handleParameters(Map<String, Object> paramMap,String startDate,String endDate,
			                     String actualSelect,String stockQty,String frozenSelect, 
			                     String frozenQty, String sku,String secCode,String selectType){
		if(StringUtils.isNotEmpty(secCode)){
			paramMap.put("secCode", secCode);
		}
		if(StringUtils.isNotEmpty(sku)){
			paramMap.put("sku", sku);
		}
		if(StringUtils.isNotEmpty(startDate)){
			paramMap.put("startDate", startDate);
		}
		if(StringUtils.isNotEmpty(endDate)){
			paramMap.put("endDate", endDate);
		}
		if(StringUtils.isNotEmpty(selectType)){
			paramMap.put("selectType", selectType);
		}
		if(StringUtils.isNotEmpty(actualSelect) && StringUtils.isNotEmpty(stockQty)){
			paramMap.put("actualSelect", actualSelect);
			paramMap.put("stockQty", stockQty);
		}
		if(StringUtils.isNotEmpty(frozenSelect) && StringUtils.isNotEmpty(frozenQty)){
			paramMap.put("frozenSelect", frozenSelect);
			paramMap.put("frozenQty", frozenQty); 
		}
        
        return paramMap;
	}
	
	/**
     * 分页处理
     * @param modelMap
     * @param pager
     */
    private void addPagerParam(Map<String, Object> modelMap, PagerInfo pager) {
        Integer totalPage = pager.getRowsCount() % pager.getPageSize() == 0
            ? pager.getRowsCount() / pager.getPageSize()
            : pager.getRowsCount() / pager.getPageSize() + 1;
        modelMap.put("totalCount", pager.getRowsCount());
        if (pager.getPageIndex() > 1)
            modelMap.put("hasFirst", true);
        else
            modelMap.put("hasFirst", false);
        if (pager.getPageIndex() - 1 > 0)
            modelMap.put("hasPrevious", true);
        else
            modelMap.put("hasPrevious", false);
        if (pager.getPageIndex() + 1 <= totalPage)
            modelMap.put("hasNext", true);
        else
            modelMap.put("hasNext", false);
        if (totalPage > 1 && pager.getPageIndex() != totalPage)
            modelMap.put("hasLast", true);
        else
            modelMap.put("hasLast", false);
        modelMap.put("curPage", totalPage > 0 ? pager.getPageIndex() : 0);
        modelMap.put("totalPage", totalPage);
        modelMap.put("startNum", pager.getStart() + 1);
        modelMap.put("endNum",
            (pager.getStart() + pager.getPageSize()) > pager.getRowsCount()
                ? (pager.getStart() + pager.getRowsCount() % pager.getPageSize())
                : pager.getStart() + pager.getPageSize());
    }
    
    
    
    /**
     * 导出超卖手动释放库存信息
     */
   @RequestMapping(value = "/ManualReleaseInventory.export", method = RequestMethod.GET)
    public void exportInvWarehouseList(@RequestParam(required = false) Integer pageIndex,
	          @RequestParam(required = false) String startDate,
	            @RequestParam(required = false) String endDate,
	            @RequestParam(required = false) String actualSelect,
	            @RequestParam(required = false) String stockQty,
	            @RequestParam(required = false) String frozenSelect,
	            @RequestParam(required = false) String frozenQty,
	            @RequestParam(required = false) String sku,
	            @RequestParam(required = false) String secCode,
	            @RequestParam(required = false) String selectType,
          
		Map<String, Object> modelMap,HttpServletRequest request,
		HttpServletResponse response) throws IOException {
		modelMap=handleParameters(modelMap,startDate,endDate,actualSelect,stockQty,frozenSelect, 
		frozenQty, sku,secCode,selectType);

        List<Map<String, Object>> predictstocklist=baseInventoryModel.queryByfrozenQtyGtStockQty(modelMap);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("超卖手动释放库存表");
        sheet.setColumnWidth(0, (int) (10.32 * 256));
        sheet.setColumnWidth(1, (int) 15.57 * 256);
        sheet.setColumnWidth(2, (int) 21.57 * 256);
        sheet.setColumnWidth(3, (int) 21.57 * 256);
        sheet.setColumnWidth(4, (int) 10.71 * 256);
        sheet.setColumnWidth(5, (int) 15.14 * 256);
        sheet.setColumnWidth(6, (int) 10.57 * 256);
        sheet.setColumnWidth(7, (int) 27.57 * 256);
        sheet.setColumnWidth(8, (int) 12.29 * 256);
        sheet.setColumnWidth(9, (int) 11.43 * 256);
        sheet.setColumnWidth(10, (int) 12.12 * 256);
        sheet.setColumnWidth(11, (int) 16.57 * 256);
        sheet.setColumnWidth(12, (int) 12.4 * 256);
        sheet.setColumnWidth(13, (int) 12.4 * 256);
        sheet.setColumnWidth(14, (int) 12.4 * 256);
        sheet.setColumnWidth(15, (int) 12.4 * 256);
        sheet.setColumnWidth(16, (int) 12.4 * 256);
        sheet.setColumnWidth(17, (int) 12.4 * 256);
        sheet.setColumnWidth(18, (int) 12.4 * 256);
        sheet.setColumnWidth(19, (int) 12.4 * 256);
        sheet.setColumnWidth(20, (int) 12.4 * 256);
        sheet.setColumnWidth(21, (int) 12.4 * 256);
        sheet.setColumnWidth(22, (int) 12.4 * 256);
        sheet.setColumnWidth(23, (int) 12.4 * 256);
        sheet.setColumnWidth(24, (int) 12.4 * 256);
        sheet.setColumnWidth(25, (int) 12.4 * 256);
        sheet.setColumnWidth(26, (int) 12.4 * 256);
        sheet.setColumnWidth(27, (int) 12.4 * 256);
        sheet.setColumnWidth(28, (int) 12.4 * 256);
        sheet.setColumnWidth(29, (int) 12.4 * 256);
        sheet.setColumnWidth(30, (int) 12.4 * 256);
        sheet.setColumnWidth(31, (int) 12.4 * 256);
        sheet.setColumnWidth(32, (int) 12.4 * 256);
        sheet.setColumnWidth(33, (int) 12.4 * 256);
        sheet.setColumnWidth(34, (int) 12.4 * 256);
        sheet.setColumnWidth(35, (int) 12.4 * 256);
        sheet.setColumnWidth(36, (int) 12.4 * 256);
        sheet.setColumnWidth(37, (int) 12.4 * 256);
        sheet.setColumnWidth(38, (int) 12.4 * 256);
        sheet.setColumnWidth(39, (int) 39.43 * 256);
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

        String[] totalHeaders = CHECKSTR.split(",");
        // 设置表头
        for (int i = 0; i < totalHeaders.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(totalHeaders[i]);
            cell.setCellStyle(style);
        }
        //向单元格里添加数据
        String STATUS = "";
        String supportCod = "";
        if (predictstocklist != null) {
            for (int i = 0; i < predictstocklist.size(); i++) {
               
            	for( int a = 1 ;a <= predictstocklist.get(i).size();a++) {
         		   Iterator<String> iter = predictstocklist.get(i).keySet().iterator();
         		   while(iter.hasNext()){
         		    String key=iter.next();
//         		    String value = (String) predictstocklist.get(i).get(key);
         		    System.out.println(key+" <==");
         		    row = sheet.createRow(i+1);  
         		    Gson gson = new Gson();
                     row.createCell(0).setCellValue(predictstocklist.get(i).get("sku").toString());
                     row.createCell(1).setCellValue(predictstocklist.get(i).get("secCode").toString());
                     row.createCell(2).setCellValue(predictstocklist.get(i).get("lesSecCode").toString());
                     row.createCell(3).setCellValue(predictstocklist.get(i).get("stockQty").toString());
                     row.createCell(4).setCellValue(predictstocklist.get(i).get("frozenQty").toString());
                     row.createCell(5).setCellValue(predictstocklist.get(i).get("overSold").toString());
                     row.createCell(6).setCellValue(predictstocklist.get(i).get("createTime").toString());
                     row.createCell(7).setCellValue(predictstocklist.get(i).get("updateTime").toString());
                     
         		   }
         	   }
                
            }
        }

        java.util.Date date = new java.util.Date();
        String str = sdf.format(date);
        String fileName = "超卖手动释放库存表" + str;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        ExportExcelUtil.exportCommon(is, fileName, response);
    }
}