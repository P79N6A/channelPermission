package com.haier.svc.api.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.svc.service.*;
import org.apache.log4j.LogManager;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.haier.afterSale.model.BadRate;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.model.CrmOrderItem;
import com.haier.purchase.data.model.DataDictionary;
import com.haier.purchase.data.model.T2OrderItem;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import com.haier.svc.api.controller.util.CommUtil;
import com.haier.svc.api.controller.util.CrmExportData;
import com.haier.svc.api.controller.util.ExportData;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.controller.util.WebUtil;
import com.haier.svc.api.controller.util.date.DateCal;
import com.haier.svc.api.service.CommPurchase;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author zsx
 *
 */
@Controller
@RequestMapping(value="purchaseAllocation")
public class PurchaseAllocationManagementController {
	
    private final static org.apache.log4j.Logger logger = LogManager
            .getLogger(T2OrderQueryController.class);
	
    @Autowired
    ItemService itemService;
    @Autowired
    EisEaiMdmService eisEaiMdmSerivce;
	 @RequestMapping("/PageJump")
	 public String PageJump(){
		 return  "purchase/productBaseDataManagement";
	 }
	 /**
	  * 查询所有类型
	  * 
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = { "/getType" }, method = { RequestMethod.GET })
		@ResponseBody
		 public void getType(HttpServletResponse response) throws IOException {

			ServiceResult<List<ItemBase>> queryResult = itemService
					.getType();
		      response.setContentType("text/html;charset=utf-8");
		
		      ObjectMapper mapper = new ObjectMapper();    //提供java-json相互转换功能的类
		      
		      String json = mapper.writeValueAsString(queryResult.getResult());    //将list中的对象转换为Json格式的数组
		      response.getWriter().write(json);
		      
		  }
	
   
	 /**
	  * 产品基础信息查询  
	  * 
	  * @param type
	  * @param product_group_id
	  * @param status
	  * @param update
	  * @param materials_id
	  * @param materials_desc
	  * @param category_id
	  * @param rows
	  * @param page
	  * @param request
	  * @param response
	  */
    @RequestMapping(value = {"/findProductBaseData"}, method = {RequestMethod.POST})
    void findMultipleOrder(
                           @RequestParam(required = false) String type,
                           @RequestParam(required = false) String product_group_id,
                           @RequestParam(required = false) String status,
                           @RequestParam(required = false) String update,
                           @RequestParam(required = false) String materials_id,
                           @RequestParam(required = false) String materials_desc,
                           @RequestParam(required = false) String category_id,
                           @RequestParam(required = false) Integer rows,
                           @RequestParam(required = false) Integer page,
                           HttpServletRequest request, HttpServletResponse response) {
        try {
            if (rows == null)
                rows = 20;
            if (page == null)
                page = 1;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("type", type);
            params.put("product_group_id", product_group_id);
            params.put("status", status);
            params.put("update", update);
            params.put("materials_id", materials_id);
            params.put("materials_desc", materials_desc);
            params.put("category_id", category_id);
            params.put("m", (page - 1) * rows);
            params.put("n", rows);
            
            ServiceResult<List<ItemBase>> result = itemService
                    .getProductBaseData(params);
            
            if(result == null)
            	return ;
            
  
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
        }
    }
    

    
    
    //全部导出
    @RequestMapping(value = {"/exportProductBaseList.export"})
    void exportProductBaseList(
                           @RequestParam(required = false) String type,
                           @RequestParam(required = false) String product_group_id,
                           @RequestParam(required = false) String status,
                           @RequestParam(required = false) String update,
                           @RequestParam(required = false) String materials_id,
                           @RequestParam(required = false) String materials_desc,
                           @RequestParam(required = false) String category_id,
                           HttpServletRequest request, HttpServletResponse response) {
    	    //如果是ALL，渠道设为空
    	    if (type.equals("全部") ) {
    	        type = "";
    	    }
    	 
    	    //如果是ALL，订单类型设为空
    	    if (status .equals("3") ) {
    	    	status = "";
    	    }
    	    //如果是ALL，订单类型设为空
    	    if (update .equals("2")) {
    	    	update = "";
    	    }
    	    //如果是ALL，品类设为空
    	    if (category_id.equals("ALL")) {
    	    	category_id = "";
    	    }
    	    //如果是ALL，产品组设为空
    	    if (product_group_id .equals("ALL")) {
    	    	product_group_id = "";
    	    }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("type", type);
            params.put("product_group_id", product_group_id);
            params.put("status", status);
            params.put("update", update);
            params.put("materials_id", materials_id);
            params.put("materials_desc", materials_desc);
            params.put("category_id", category_id);
            params.put("m",0);
            params.put("n",10000);
    HSSFWorkbook  wb= getDetailsData(params);
    
    SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");  
    java.util.Date date=new java.util.Date();  
    String str=sdf.format(date); 
    String fileName = "产品基础数据列表"+str;
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

    /**
     * 手动同步物料信息
     * @param materialCode
     * @param modelMap
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "/manualSyncMdm" }, method = { RequestMethod.GET })
    @ResponseBody
    public HttpJsonResult<Map<String, Object>> manualSyncMdm(@RequestParam(required = false) String materialCode,
                                                             Map<String, Object> modelMap,
                                                             HttpServletRequest request,
                                                             HttpServletResponse response) {
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();

        try {
            ServiceResult<Boolean> syncResult = eisEaiMdmSerivce.manualSyncMdmBySku(materialCode,
                    this.getUserName());
            if (syncResult != null && syncResult.getSuccess() && syncResult.getResult()) {
                result.setMessage("物料编码：" + materialCode + "手动同步成功！");
            } else if (syncResult != null && syncResult.getSuccess() && !syncResult.getResult()) {
                result.setMessage("物料编码：" + materialCode + "," + syncResult.getMessage());
            } else {
                result.setMessage("物料编码：" + materialCode + "手动同步时,item服务发生异常,失败！");
                logger.error("物料编码：" + materialCode + "手动同步时,item服务发生异常,失败:"
                        + syncResult.getMessage());
            }
        } catch (Exception e) {
            logger.error("[item][ItemBaseController]物料编码：" + materialCode + "手动同步信息时web发生未知错误", e);
            result.setMessage("物料编码：" + materialCode + "手动同步信息时web发生异常，失败！");
        }
        return result;
    }

public HSSFWorkbook getDetailsData(Map<String, Object> params) {
    
    ServiceResult<List<ItemBase>> result = itemService
            .getProductBaseData(params); 

   // 获得条数
   List<ItemBase> list = new ArrayList<ItemBase>();
   if (result.getSuccess() && result.getResult() != null) {
       list = result.getResult();
   }
  // 1.创建一个workbook，对应一个Excel文件
     HSSFWorkbook wb = new HSSFWorkbook();
     // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
     HSSFSheet sheet = wb.createSheet("产品基本数据列表");
     int length = ExportData.itemBaseListTitle.length;
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
       cell.setCellValue(ExportData.itemBaseListTitle[i]);
       cell.setCellStyle(style);          
     }
   

     //向单元格里添加数据  
     for(short i=0;i<list.size();i++){              
         row = sheet.createRow(i+1);
         row.createCell(0).setCellValue(list.get(i).getMaterialCode());  
         row.createCell(1).setCellValue(list.get(i).getMaterialDescription());  
         row.createCell(2).setCellValue(list.get(i).getMaterialType());  
         row.createCell(3).setCellValue(list.get(i).getDepartment());  
         row.createCell(4).setCellValue(list.get(i).getStatus());  
         row.createCell(5).setCellValue(list.get(i).getModifier());  
         row.createCell(6).setCellValue(list.get(i).getProductType());  
         row.createCell(7).setCellValue(list.get(i).getLengthNumber().toString());  
         row.createCell(8).setCellValue(list.get(i).getWidthNumber().toString()); 
         row.createCell(9).setCellValue(list.get(i).getHighNumber().toString());  
         row.createCell(10).setCellValue(list.get(i).getGrossWeightNumber().toString());  
         row.createCell(11).setCellValue(list.get(i).getWeightUnit());
         row.createCell(12).setCellValue(list.get(i).getDeleteFlag());             
         row.createCell(13).setCellValue(list.get(i).getCreated());
         row.createCell(14).setCellValue(list.get(i).getLastUpd());
         row.createCell(15).setCellValue(list.get(i).getProBand());
         row.createCell(16).setCellValue(list.get(i).getIsAutoUpdate());
         if(list.get(i).getPrice()==null){
             row.createCell(17).setCellValue("");
         }else{
             row.createCell(17).setCellValue(String.valueOf(list.get(i).getPrice()));
         }
     }
	return wb;

}

    /**
     * 手动添加产品基础数据
     * @param materialCodeLabelInsert
     * @param materialDescriptionInsert
     * @param productTypeInsert
     * @param departmentInsert
     * @param priceInsert
     * @param modelMap
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "/insertItemBase" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Map<String, Object>> insertItemBase(@RequestParam(required = false) String materialCodeLabelInsert,
                                                              @RequestParam(required = false) String materialDescriptionInsert,
                                                              @RequestParam(required = false) String productTypeInsert,
                                                              @RequestParam(required = false) String departmentInsert,
                                                              @RequestParam(required = false) String priceInsert,
                                                              Map<String, Object> modelMap,
                                                              HttpServletRequest request,
                                                              HttpServletResponse response) {
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        try {
            materialCodeLabelInsert = materialCodeLabelInsert == null ? ""
                    : materialCodeLabelInsert.trim();
            materialDescriptionInsert = materialDescriptionInsert == null ? ""
                    : materialDescriptionInsert.trim();
            productTypeInsert = productTypeInsert == null ? "" : productTypeInsert.trim();
            departmentInsert = departmentInsert == null ? "" : departmentInsert.trim();
            if ("".equals(materialCodeLabelInsert)) {
                result.setMessage("物料编码不能为空! ");
                return result;
            }
            if (materialCodeLabelInsert.length() > 60) {
                result.setMessage("物料编码长度不能超过60位! ");
                return result;
            }
            if (materialDescriptionInsert.length() > 400) {
                result.setMessage("物料名称长度不能超过400位! ");
                return result;
            }

            if (priceInsert == null || "".equals(priceInsert)) {
                priceInsert = "0";
            } else if (!isDecimal(priceInsert.trim())) {
                result.setMessage("价格输入格式有误! ");
                return result;
            } else {
                priceInsert = priceInsert.trim();
            }

            ServiceResult<ItemBase> existResult = itemService
                    .getItemBaseBySku(materialCodeLabelInsert);
            if (existResult != null && existResult.getResult() != null) {
                result.setMessage("物料编码已存在! ");
                return result;
            }

            ItemBase itemBase = new ItemBase();
            itemBase.setMaterialCode(materialCodeLabelInsert);
            itemBase.setMaterialDescription(materialDescriptionInsert);
            itemBase.setProductType(productTypeInsert);
            itemBase.setDepartment(departmentInsert);
            itemBase.setPrice(new BigDecimal(priceInsert));
            itemBase.setDeleteFlag(0);
            itemBase.setCreated(new Date());
            itemBase.setLastUpd(new Date());
            itemBase.setModifier(WebUtil.currentUserName(request));

            ServiceResult<Boolean> insertResult = itemService.insertItemBaseInfo(itemBase);
            if (null != insertResult && (!insertResult.getSuccess() || !insertResult.getResult())) {
                result.setMessage("添加失败！");
            }
        } catch (Exception e) {
            logger.error("[item][ItemBaseController]添加产品基础数据时发生未知错误", e);
            result.setMessage("添加失败！");
        }
        return result;
    }
    private boolean isDecimal(String str) {
        return Pattern
                .compile(
                        "(^[1-9]{1}[0-9]{0,5}$)|((^[1-9]{1}[0-9]{0,5})(\\.[\\d]{1,2}$))|((^0{1})(\\.[\\d]{1,2})$)|^0{1}$")
                .matcher(str).matches();
    }

    /**
     * 根据id修改产品基础数据
     * @param id
     * @param materialDescription
     * @param department
     * @param status
     * @param isAutoUpdate
     * @param modelMap
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "/saveItemBase" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Map<String, Object>> saveItemBase(@RequestParam(required = false) Integer id,
                                                            @RequestParam(required = false) String materialDescription,
                                                            @RequestParam(required = false) String department,
                                                            @RequestParam(required = false) Integer status,
                                                            @RequestParam(required = false) Integer isAutoUpdate,
                                                            @RequestParam(required = false) String price,
                                                            Map<String, Object> modelMap,
                                                            HttpServletRequest request,
                                                            HttpServletResponse response) {
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        try {
            if (StringUtils.isBlank(materialDescription.trim())) {
                result.setMessage("型号不能为空! ");
                return result;
            }

            if (price == null || "".equals(price)) {
                price = "0";
            } else if (!isDecimal(price.trim())) {
                result.setMessage("价格输入格式有误! ");
                return result;
            } else {
                price = price.trim();
            }

            ItemBase itemBase = new ItemBase();
            itemBase.setNumberNull();
            itemBase.setId(id);
            itemBase.setMaterialDescription(materialDescription.trim());
            itemBase.setDepartment(department);
            itemBase.setStatus(status);
            itemBase.setIsAutoUpdate(isAutoUpdate);
            itemBase.setModifier(getUserName());//取登陆用户名为修改人
            itemBase.setPrice(new BigDecimal(price));
            ServiceResult<Boolean> updateResult = itemService.updateItemBaseById(itemBase);
            if (null != updateResult && (!updateResult.getSuccess() || !updateResult.getResult())) {
                result.setMessage("保存失败！");
            }
        } catch (Exception e) {
            logger.error("[item][ItemBaseController]保存产品基础数据时发生未知错误", e);
            result.setMessage("保存失败！");
        }
        return result;
    }

    /**
     * 获取当前登录的用户
     */
    private String getUserName() {
        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        return (String) attr.getRequest().getSession().getAttribute("userName");
    }
}
