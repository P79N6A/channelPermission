package com.haier.svc.api.controller.vehicle;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.ConvertUtil;
import com.haier.common.util.StringUtil;
import com.haier.purchase.data.model.BdmOrder;
import com.haier.purchase.data.model.DataDictionary;
import com.haier.purchase.data.model.PredictingStockItem;
import com.haier.purchase.data.model.RuntimeParametersVO;
import com.haier.purchase.data.model.vehcile.AreaCenterInfoDTO;
import com.haier.purchase.data.model.vehcile.Cn3wPurchaseStock;
import com.haier.purchase.data.model.vehcile.ExportVehicleDTO;
import com.haier.purchase.data.model.vehcile.InterfaceLog;
import com.haier.purchase.data.model.vehcile.MaterielInfoDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderHistoryDTO;
import com.haier.purchase.data.model.vehcile.VehiclePushToSAP;
import com.haier.stock.model.InvStockChannel;
import com.haier.svc.api.controller.util.CommUtil;
import com.haier.svc.api.controller.util.ExcelReader;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import com.haier.svc.api.controller.util.ExportPushToSAP;
import com.haier.svc.api.controller.util.ExportVehicle;
import com.haier.svc.api.controller.util.ExportVehicleInterfaceLog;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.controller.util.WebUtil;
import com.haier.svc.api.form.LbxStatusEnum;
import com.haier.svc.api.service.vehicle.ApiVehicleOrderService;
import com.haier.svc.service.T2OrderService;
import com.haier.vehicle.service.VehicleInterfaceLogService;

/**
 * <p>
 * Description:
 * </p>
 * ClassName:mainController Created on 2017/9/4
 *
 * @author wsh
 * @version 1.0 Copyright (c) 2015 北京柯莱特科技有限公司 交付部
 */
@Controller
@RequestMapping("/vehicle")
public class VehicleController {

	private final static org.apache.log4j.Logger logger = LogManager
			.getLogger(VehicleController.class);

	@Autowired
	private ApiVehicleOrderService apiVehicleOrderService;
	@Autowired
	T2OrderService t2OrderService;

	@Autowired
	private VehicleInterfaceLogService vehicleInterfaceLogService;

	private static final String ORDER_STATUS = "order_status";

	@RequestMapping("/order")
	public String deliveryOrder(String orderId, Model model) {
		if (StringUtils.isNotEmpty(orderId)) {
			model.addAttribute("orderId", orderId);
			model.addAttribute("tempFlag", true);
		}
		return "deliveryOrder/deliveryOrder";
	}

	@RequestMapping("/confirmOrder")
	public String confirmOrder() {

		return "deliveryOrder/confirmOrder";
	}

	@RequestMapping("/history")
	public String history() {
		return "deliveryOrder/historyOrder";
	}

	@RequestMapping("/changDn")
	public String changDn() {
		return "deliveryOrder/changDn";
	}

	@RequestMapping("/pushToSAP")
	public String pushToSAPList() {
		return "vehicle/pushToSAP";
	}

	@RequestMapping(value = { "saveChang" }, method = { RequestMethod.POST })
	@ResponseBody
	public HttpJsonResult<String> saveChang(String itemNo,String vbelnSpare){
		HttpJsonResult<String> result = new HttpJsonResult<String>();
		boolean exist = apiVehicleOrderService.vbelnExists(itemNo, vbelnSpare);
		if(exist == true){
			result.setMessage("已存在相同的DN号。");
			return result;
		}
		int count = apiVehicleOrderService.updateVbelnSpareByItemNo(itemNo,vbelnSpare);
		if(count == 1){
			result.setMessage("success");
		} else {
			result.setMessage("error");
		}
		return result;
	}

	@RequestMapping("/historyDetail")
	public String historyDetail() {
		return "deliveryOrder/historyOrderDetail";
	}


	@RequestMapping("/tempList")
	public String tempList() {
		return "deliveryOrder/tempDeliveryOrderList";
	}

	@RequestMapping(value = {"/InterfaceLog"})
	public String InterfaceLog(){
		return "job/interfaceLog";
	}

	@RequestMapping("/orderDetail")
	@ResponseBody
	public JSONObject orderDetail(String orderNo) {
		return apiVehicleOrderService.checkOrder(orderNo);
	}
	
	@RequestMapping("/changeDnOrderDetail")
	@ResponseBody
	public JSONObject changeDnOrderDetail(String orderNo, String vbelnDn1, String startDate, String endDate, Integer page, Integer rows) {
		try {
			Date sDate = null, eDate = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (startDate != null && !"".equals(startDate)) {
				sDate = sdf.parse(startDate);
			}
			if (endDate != null && !"".equals(endDate)) {
				eDate = sdf.parse(endDate);
				Calendar rightNow = Calendar.getInstance();
				rightNow.setTime(eDate);
				rightNow.add(Calendar.DAY_OF_YEAR, 1);
				eDate = rightNow.getTime();
			}
			if(page == null){
				page = 1;
			}
			if(rows == null){
				rows = 1000;
			}
			int start = (page - 1) * rows;
			
			return apiVehicleOrderService.checkOrder(orderNo, vbelnDn1, sDate, eDate, start, rows);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/exportChangeDNTotal")
	@ResponseBody
	public Long exportChangeDNTotal(String orderNo, String vbelnDn1, String startDate, String endDate){
		try {
			Date sDate = null, eDate = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (startDate != null && !"".equals(startDate)) {
				sDate = sdf.parse(startDate);
			}
			if (endDate != null && !"".equals(endDate)) {
				eDate = sdf.parse(endDate);
				Calendar rightNow = Calendar.getInstance();
				rightNow.setTime(eDate);
				rightNow.add(Calendar.DAY_OF_YEAR, 1);
				eDate = rightNow.getTime();
			}
			Long total = apiVehicleOrderService.getChangeDNTotal(orderNo, vbelnDn1, sDate, eDate);
			return total;
		} catch (ParseException e) {
			e.printStackTrace();
			return -1L;
		}
	}
	
	@RequestMapping("/exportChangeDN")
	@ResponseBody
	public void exportChangeDN(HttpServletResponse resp, String orderNo, String vbelnDn1, String startDate, String endDate){
		List<String> titles =  Arrays.asList(new String[]{"主单号", "采购单号", "一次DN1", "二次DN5", "预约LBX备用DN", "开单时间"});
		try {
			Date sDate = null, eDate = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (startDate != null && !"".equals(startDate)) {
				sDate = sdf.parse(startDate);
			}
			if (endDate != null && !"".equals(endDate)) {
				eDate = sdf.parse(endDate);
				Calendar rightNow = Calendar.getInstance();
				rightNow.setTime(eDate);
				rightNow.add(Calendar.DAY_OF_YEAR, 1);
				eDate = rightNow.getTime();
			}
			List<VehicleOrderHistoryDTO> list = apiVehicleOrderService.getChangeDNList(orderNo, vbelnDn1, sDate, eDate, 0, 1000);
			
			
			// 1.创建一个workbook，对应一个Excel文件
	        HSSFWorkbook wb = new HSSFWorkbook();
	        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
	        HSSFSheet sheet = wb.createSheet("重置菜鸟预约DN");
	        for (int i = 0; i < titles.size(); i++) {
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
	        for(int i = 0; i < titles.size(); i++){
	            HSSFCell cell = row.createCell(i);
	            cell.setCellValue(titles.get(i));
	            cell.setCellStyle(style);
	        }

	        //向单元格里添加数据
	        for(short i=0;i<list.size();i++){
	            row = sheet.createRow(i+1);
	            if (list.get(i).getOrderNo() == null){
	                row.createCell(0).setCellValue("");
	            }else {
	                row.createCell(0).setCellValue(list.get(i).getOrderNo());
	            }

	            if (list.get(i).getItemNo() == null){
	                row.createCell(1).setCellValue("");
	            }else {
	                row.createCell(1).setCellValue(list.get(i).getItemNo());
	            }

	            if (list.get(i).getVbelnDn1() == null){
	                row.createCell(2).setCellValue("");
	            }else {
	                row.createCell(2).setCellValue(list.get(i).getVbelnDn1());
	            }
	            
	            if (list.get(i).getVbelnDn5() == null){
	            	row.createCell(3).setCellValue("");
	            }else {
	            	row.createCell(3).setCellValue(list.get(i).getVbelnDn5());
	            }
	            
	            if (list.get(i).getVbelnSpare() == null){
	            	row.createCell(4).setCellValue("");
	            }else {
	            	row.createCell(4).setCellValue(list.get(i).getVbelnSpare());
	            }
	            
	            if (list.get(i).getCreateTime() == null){
	            	row.createCell(5).setCellValue("");
	            }else {
	            	row.createCell(5).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(list.get(i).getCreateTime()));
	            }
	        }
	        
	        ByteArrayOutputStream os = new ByteArrayOutputStream();
	        try {
	            wb.write(os);
	            byte[] content = os.toByteArray();
	            InputStream is = new ByteArrayInputStream(content);

	            ExportExcelUtil.exportCommon(is, "重置菜鸟预约DN导出", resp);
	        } catch (IOException e) {
	            logger.error("错误", e);
	        }
	        
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/importChangeDN")
	@ResponseBody
	public HttpJsonResult<Map<String, Object>> importChangeDN(MultipartHttpServletRequest req) {
		HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
		try {
			MultipartFile file = req.getFile("file");

			String fileName = file.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			String fileExtName = fileName.substring(index + 1);
			if (!fileExtName.equalsIgnoreCase("xls")) {
				result.setMessage("选择导入文件扩展名必须为xls!");
				return result;
			}

			List<String[]> list = ExcelReader.readExcel(file.getInputStream(), 1);
			if (list.size() <= 1) {
				result.setMessage("很抱歉！你导入的Excel没有数据记录，请查看重新整理导入！");
				return result;
			}
			
			//判断新的dn是否有重复的
			StringBuilder msg = new StringBuilder("");
			for(int i = 1; i < list.size(); i++){
				String[] s = list.get(i);
				if(StringUtils.isNotBlank(s[4])){
					for(int ii = 1; ii < list.size(); ii++){
						String[] ss = list.get(ii);
						if(StringUtils.isNotBlank(ss[4]) && ss[4].equals(s[4]) && i != ii){
							msg.append("第" + i + "行DN【" + s[4] + "】重复<br>");
						}
					}
				}
			}
			if(StringUtils.isNotBlank(msg.toString())){
				result.setMessage(msg.toString() + "请检查后重新导入。");
				return result;
			}
			
			for(int i = 1; i < list.size(); i++){
				String[] s = list.get(i);
				if(StringUtils.isNotBlank(s[1])){
					apiVehicleOrderService.updateVblenSpare(s[1], s[4]);
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("菜鸟DN重置导入时失败：", e);
			result.setMessage("菜鸟DN重置导入时失败");
		}
		return result;
	}
	
	@RequestMapping("/updateStatus")
	@ResponseBody
	public JSONObject updateStatus(String orderNo, String status) {
		return apiVehicleOrderService.updateStatus(orderNo, status);
	}

	@RequestMapping("/historyOrder")
	@ResponseBody
	public JSONObject historyOrder(String startDate, String endDate,
								   String status, String orderNo, String order, String sort, int page, int rows)
			throws ParseException {
		VehicleOrderDTO condition = new VehicleOrderDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null && !"".equals(startDate)) {
			Date sDate = sdf.parse(startDate);
			condition.setStartOrderTime(sDate);
		}
		if (endDate != null && !"".equals(endDate)) {
			Date eDate = sdf.parse(endDate);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(eDate);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			condition.setEndOrderTime(rightNow.getTime());
		}
		if (orderNo != null && !"".equals(orderNo)) {
			condition.setOrderNo(orderNo);
		}
		page = page == 0 ? 1 : page;
		PagerInfo pager = new PagerInfo(rows, page);
		condition.setCreateBy(apiVehicleOrderService.getUserId());
		condition.setStatus(status);
		String[] exportArray = new String[2];
		exportArray[0] = "00";
		exportArray[1] = "01";
		condition.setNoStatus(exportArray);
		JSONObject result = apiVehicleOrderService.historyOrder(condition,
				pager);
		return result;
	}

	@RequestMapping("/tempOrder")
	@ResponseBody
	public JSONObject tempOrder(String startDate, String endDate, int page,
								int rows) throws ParseException {
		VehicleOrderDTO condition = new VehicleOrderDTO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (startDate != null && !"".equals(startDate)) {
			Date sDate = sdf.parse(startDate);
			condition.setStartOrderTime(sDate);
		}
		if (endDate != null && !"".equals(endDate)) {
			Date eDate = sdf.parse(endDate);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(eDate);
			rightNow.add(Calendar.DAY_OF_YEAR, 1);
			condition.setEndOrderTime(rightNow.getTime());
		}
		condition.setStatus("01");
		page = page == 0 ? 1 : page;
		PagerInfo pager = new PagerInfo(rows, page);
		JSONObject result = apiVehicleOrderService.historyOrder(condition,
				pager);
		return result;
	}

	@RequestMapping("/getPager")
	@ResponseBody
	public JSONObject getPager(VehicleOrderDTO condition, PagerInfo pager) {
		return apiVehicleOrderService.getPagerByCondition(condition, pager);
	}

	@RequestMapping("/sendTo")
	@ResponseBody
	public JSONArray getSendTo() {
		return apiVehicleOrderService.getSendTo();
	}

	@RequestMapping("/productGroup")
	@ResponseBody
	public JSONObject productGroup() {
		return apiVehicleOrderService.productGroup();
	}

	@RequestMapping("/baseCode")
	@ResponseBody
	public JSONArray baseCode() {
		return apiVehicleOrderService.baseCode();
	}

	@RequestMapping("/whCode")
	@ResponseBody
	public JSONArray whCode() {
		return apiVehicleOrderService.whCode();
	}

	@RequestMapping("/carCode")
	@ResponseBody
	public JSONObject carCode(String base, String sendTo) {
		return apiVehicleOrderService.carCode(base, sendTo);
	}

	@RequestMapping("/productList")
	@ResponseBody
	public JSONObject productList(int page, int rows, MaterielInfoDTO condition) {
		page = page == 0 ? 1 : page;
		PagerInfo pager = new PagerInfo(rows, page);
		condition.setMaterielName(condition.getMaterielCode());
		return apiVehicleOrderService.productList(pager, condition);
	}

	@RequestMapping("/center")
	@ResponseBody
	public JSONObject center(AreaCenterInfoDTO condition) {
		return apiVehicleOrderService.center(condition);
	}

	/**
	 * 装车检查
	 *
	 * @param dto
	 * @param materielIds
	 * @return
	 */
	@RequestMapping("/carCheck")
	@ResponseBody
	public JSONObject carCheck(VehicleOrderDTO dto, String materielIds) {
		JSONObject result =  apiVehicleOrderService.normalSave(dto, materielIds);
		return result;
	}

	/**
	 * 暂存
	 *
	 * @param dto
	 * @param materielIds
	 * @param minVolumeStr
	 * @param maxVolumeStr
	 * @return
	 */
	@RequestMapping("/tempSave")
	@ResponseBody
	public JSONObject tempSave(VehicleOrderDTO dto, String materielIds,
							   String minVolumeStr, String maxVolumeStr) {
		if (minVolumeStr != null && !"".equals(minVolumeStr)) {
			dto.setMinVolume(Double.parseDouble(minVolumeStr));
		}
		if (maxVolumeStr != null && !"".equals(maxVolumeStr)) {
			dto.setMaxVolume(Double.parseDouble(maxVolumeStr));
		}
		return apiVehicleOrderService.tempSave(dto, materielIds);
	}

	@RequestMapping("/checkOrder")
	@ResponseBody
	public JSONObject checkOrder(String orderNo) {
		return apiVehicleOrderService.checkOrder(orderNo);
	}

	@RequestMapping("/itemCheck")
	@ResponseBody
	public JSONObject itemCheck(String materielCode, String deliveryCode,
								String baseCode, int count) {
		return apiVehicleOrderService.itemCheck(materielCode, deliveryCode,
				baseCode, count);
	}

	@RequestMapping("/submitOrder")
	@ResponseBody
	public JSONObject submitOrder(String orderNo) {
		return apiVehicleOrderService.submitOrder(orderNo);
	}

	@RequestMapping("/orderStatus")
	@ResponseBody
	public JSONArray orderStatus() {
		return apiVehicleOrderService.orderStatus();
	}

	@RequestMapping("/tempLoad")
	@ResponseBody
	public JSONObject tempLoad(String orderId) {
		return apiVehicleOrderService.tempLoad(orderId);
	}

	/**
	 * 导出
	 */
	@RequestMapping(value = { "/exportVehicle.export" })
	void exportVehicle(@RequestParam(required = false) String orderNoList,
					   Map<String, Object> modelMap, HttpServletRequest request,
					   HttpServletResponse response) {
		try {
			com.alibaba.dubbo.common.json.JSONArray exportJson = new com.alibaba.dubbo.common.json.JSONArray();
			String[] exportArray = null;
			try {
				if (orderNoList != null && !orderNoList.equals("")) {
					exportJson = (com.alibaba.dubbo.common.json.JSONArray) JSON
							.parse(orderNoList);
					exportArray = new String[exportJson.length()];
					// JSONArray转化为list
					for (int i = 0; i < exportJson.length(); i++) {
						exportArray[i] = (String) exportJson.get(i);
					}
				}
			} catch (com.alibaba.dubbo.common.json.ParseException e1) {
				e1.printStackTrace();
				logger.error("JSON转换失败！ 错误：" + e1.getMessage());
			}
			Map<String, Object> params = new HashMap<String, Object>();
			// List<String> reviewList = new ArrayList<String>();
			params.put("orderNoList", exportArray);
			List<ExportVehicleDTO> vehicleList = apiVehicleOrderService
					.selectVehicleExport(params);
			// 1.创建一个workbook，对应一个Excel文件
			HSSFWorkbook wb = new HSSFWorkbook();
			// 2.在workbook中添加一个sheet，对应Excel中的一个sheet
			HSSFSheet sheet = wb.createSheet("整车直发历史订单列表");
			sheet.setColumnWidth(0, (int) (21.57 * 256));
			sheet.setColumnWidth(1, (int) 21.57 * 256);
			sheet.setColumnWidth(2, (int) 21.57 * 256);
			sheet.setColumnWidth(3, (int) 8.57 * 256);
			sheet.setColumnWidth(4, (int) 15 * 256);
			sheet.setColumnWidth(5, (int) 15 * 256);
			sheet.setColumnWidth(6, (int) 15 * 256);
			sheet.setColumnWidth(7, (int) 23 * 256);
			sheet.setColumnWidth(8, (int) 15 * 256);
			
			sheet.setColumnWidth(9, (int) 15 * 256);
			
			sheet.setColumnWidth(10, (int) 15 * 256);
			sheet.setColumnWidth(11, (int) 16 * 256);
			sheet.setColumnWidth(12, (int) 16 * 256);
			sheet.setColumnWidth(13, (int) 16 * 256);
			sheet.setColumnWidth(14, (int) 21.57 * 256);
			sheet.setColumnWidth(15, (int) 21.57 * 256);
			sheet.setColumnWidth(16, (int) 21.57 * 256);
			sheet.setColumnWidth(17, (int) 21.57 * 256);
			sheet.setColumnWidth(18, (int) 11.14 * 256);
			sheet.setColumnWidth(19, (int) 15 * 256);
			sheet.setColumnWidth(20, (int) 21.57 * 256);
			sheet.setColumnWidth(21, (int) 8 * 256);
			sheet.setColumnWidth(22, (int) 12 * 256);
			sheet.setColumnWidth(23, (int) 8 * 256);
			sheet.setColumnWidth(24, (int) 8 * 256);
			sheet.setColumnWidth(25, (int) 8 * 256);
			sheet.setColumnWidth(26, (int) 23 * 256);
			sheet.setColumnWidth(27, (int) 35 * 256);
			sheet.setColumnWidth(28, (int) 21.57 * 256);
			sheet.setColumnWidth(29, (int) 21.57 * 256);
			sheet.setColumnWidth(30, (int) 40 * 256);
			sheet.setColumnWidth(31, (int) 15 * 256);
			sheet.setColumnWidth(32, (int) 15 * 256);
			sheet.setColumnWidth(33, (int) 15 * 256);
			sheet.setColumnWidth(34, (int) 15 * 256);
			sheet.setColumnWidth(35, (int) 15 * 256);
	
			// 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
			HSSFRow row = sheet.createRow(0);
			// 4.创建单元格，设置值表头，设置表头居中
			HSSFCellStyle style = wb.createCellStyle();
			// 居中格式
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
	
			int length = ExportVehicle.orderListTitle.length;
			// 设置表头
			for (int i = 0; length - 1 >= i; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellValue(ExportVehicle.orderListTitle[i]);
				cell.setCellStyle(style);
			}
			Map<String, String> orderStatusMap = t2OrderService
					.getValueMeaningMap(ORDER_STATUS);
			// 向单元格里添加数据
			for (short i = 0; i < vehicleList.size(); i++) {
				ExportVehicleDTO dto = vehicleList.get(i);
				row = sheet.createRow(i + 1);
				row.createCell(0).setCellValue(dto.getOrderNo());
				row.createCell(1).setCellValue(dto.getItemNo());
				row.createCell(2).setCellValue(dto.getZqItemNo());
				row.createCell(3).setCellValue(String.valueOf(dto.getRows()));
				row.createCell(4).setCellValue(dto.getVbelnDn1());
				row.createCell(5).setCellValue(dto.getVbelnDn5());
				row.createCell(6).setCellValue(dto.getVbelnSpare());
				row.createCell(7).setCellValue(dto.getLbx());
				if (dto.getZspdt() != null) {
					row.createCell(8).setCellValue(
							com.haier.svc.api.controller.util.date.DateUtil
									.getFormatDateTime(dto.getZspdt()));
				}
				if(dto.getActualQty() != null){
					row.createCell(9).setCellValue(dto.getActualQty());
				}
				if (dto.getLbxStatus() != null && !"".equals(dto.getLbxStatus())) {
					if (dto.getLbxStatus().equals(LbxStatusEnum.NEW.getCode())) {
						row.createCell(10).setCellValue(LbxStatusEnum.NEW.getName());
					} else if (dto.getLbxStatus().equals(
							LbxStatusEnum.ACCEPT.getCode())) {
						row.createCell(10).setCellValue(
								LbxStatusEnum.ACCEPT.getName());
					} else if (dto.getLbxStatus().equals(
							LbxStatusEnum.PARTFULFILLED.getCode())) {
						row.createCell(10).setCellValue(
								LbxStatusEnum.PARTFULFILLED.getName());
					} else if (dto.getLbxStatus().equals(
							LbxStatusEnum.FULFILLED.getCode())) {
						row.createCell(10).setCellValue(
								LbxStatusEnum.FULFILLED.getName());
					} else if (dto.getLbxStatus().equals(
							LbxStatusEnum.EXCEPTION.getCode())) {
						row.createCell(10).setCellValue(
								LbxStatusEnum.EXCEPTION.getName());
					} else if (dto.getLbxStatus().equals(
							LbxStatusEnum.CANCELED.getCode())) {
						row.createCell(10).setCellValue(
								LbxStatusEnum.CANCELED.getName());
					} else if (dto.getLbxStatus().equals(
							LbxStatusEnum.CLOSED.getCode())) {
						row.createCell(10).setCellValue(
								LbxStatusEnum.CLOSED.getName());
					} else if (dto.getLbxStatus().equals(
							LbxStatusEnum.REJECT.getCode())) {
						row.createCell(10).setCellValue(
								LbxStatusEnum.REJECT.getName());
					} else if (dto.getLbxStatus().equals(
							LbxStatusEnum.CANCELEDFAIL.getCode())) {
						row.createCell(10).setCellValue(
								LbxStatusEnum.CANCELEDFAIL.getName());
					} else {
						row.createCell(10).setCellValue("");
					}
	
				}
	
				row.createCell(11).setCellValue(dto.getLbxErrMsg());
	
				if ("1".equals(dto.getSapStatus())) {
					row.createCell(12).setCellValue("推送SAP成功");
				} else if ("2".equals(dto.getSapStatus())) {
					row.createCell(12).setCellValue("推送SAP失败");
				} else if ("0".equals(dto.getSapStatus())) {
					row.createCell(12).setCellValue("未推送SAP");
				} else {
					row.createCell(12).setCellValue("未推送SAP");
				}
	
				if (orderStatusMap != null && StringUtils.isNotBlank(dto.getZkStatus())) {
					row.createCell(13).setCellValue(
							String.valueOf(orderStatusMap.get(String.valueOf(dto.getZkStatus()))));
				}
	
				if (orderStatusMap != null && StringUtils.isNotBlank(dto.getZqStatus())) {
					row.createCell(14).setCellValue(
							String.valueOf(orderStatusMap.get(String.valueOf(dto.getZqStatus()))));
				}
	
				row.createCell(15).setCellValue(dto.getZkRemark());
				row.createCell(16).setCellValue(dto.getZqRemark());
				row.createCell(17).setCellValue(dto.getMaterielCode());
				row.createCell(18).setCellValue(dto.getMaterielName());
				row.createCell(19).setCellValue(dto.getProductGroup());
				row.createCell(20).setCellValue(dto.getProductGroupName());
				row.createCell(21).setCellValue(dto.getBrand());
				row.createCell(22).setCellValue(String.valueOf(dto.getQty()));
				row.createCell(23).setCellValue(String.valueOf(dto.getUnitPrice()));
				row.createCell(24).setCellValue(String.valueOf(dto.getAmount()));
				row.createCell(25).setCellValue(String.valueOf(dto.getVolume()));
				row.createCell(26).setCellValue(
						String.valueOf(dto.getTotalVolume()));
				row.createCell(27).setCellValue(dto.getPaymentCode());
				row.createCell(28).setCellValue(dto.getPaymentName());
				row.createCell(29).setCellValue(dto.getJdName());
				row.createCell(30).setCellValue(dto.getDeliveryCode());
				row.createCell(31).setCellValue(dto.getDeliveryName());
				row.createCell(32).setCellValue(dto.getDistributionCentre());
				row.createCell(33).setCellValue(dto.getDistributionCentreName());
				row.createCell(34).setCellValue(dto.getWhCode());
	
				if (dto.getOrderTime() != null) {
					row.createCell(35).setCellValue(
							com.haier.svc.api.controller.util.date.DateUtil
									.getFormatDate(dto.getOrderTime()));
				}
				if (dto.getDateOfArrival() != null) {
					row.createCell(36).setCellValue(
							com.haier.svc.api.controller.util.date.DateUtil
									.getFormatDate(dto.getDateOfArrival()));
				}
			}
	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			java.util.Date date = new java.util.Date();
			String str = sdf.format(date);
			String fileName = "整车直发历史订单列表" + str;
	
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			wb.write(os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);

			ExportExcelUtil.exportCommon(is, fileName, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("错误", e);
		}

	}


	@RequestMapping(value = {"findInterfaceLog"})
	public void findInterfaceLog(
			@RequestParam(required = false) String logName,
			@RequestParam(required = false) String logMessage,
			@RequestParam(required = false) String startTime,
			@RequestParam(required = false) String endTime,
			@RequestParam(required = false) Integer rows,
			@RequestParam(required = false) Integer page,
			HttpServletRequest request,HttpServletResponse response){
		try {
			if (rows == null)
				rows = 20;
			if (page == null)
				page = 1;
			Map<String, Object> params = new HashMap<String, Object>();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (endTime != null && !"".equals(endTime)) {
				Date eDate = sdf.parse(endTime);
				Calendar rightNow = Calendar.getInstance();
				rightNow.setTime(eDate);
				rightNow.add(Calendar.DAY_OF_YEAR, 1);
				String str = sdf.format(rightNow.getTime());
				params.put("endTime", str);
			}

			params.put("logName", logName);
			params.put("logMessage", logMessage);
			params.put("startTime", startTime);
//			params.put("endTime", endTime);
			params.put("m", (page - 1) * rows);
			params.put("n", rows);

			ServiceResult<List<InterfaceLog>> result = vehicleInterfaceLogService.findInterfaceLog(params);

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

    @ResponseBody
    @RequestMapping(value = { "/getStatus" }, method = { RequestMethod.GET })
    HttpJsonResult<List<DataDictionary>> getStatus(HttpServletRequest request) {

        HttpJsonResult<List<DataDictionary>> result = new HttpJsonResult<List<DataDictionary>>();

        List<DataDictionary> list = new ArrayList<DataDictionary>();
        DataDictionary dict = new DataDictionary();
        dict.setValue("0");
        dict.setValue_meaning("未推送");
        list.add(dict);
        dict = new DataDictionary();
        dict.setValue("1");
        dict.setValue_meaning("推送成功");
        list.add(dict);
        dict = new DataDictionary();
        dict.setValue("2");
        dict.setValue_meaning("推送失败");
        list.add(dict);
        result.setData(list);
        return result;
    }

    @ResponseBody
    @RequestMapping(value = { "/findPushToSAPList" }, method = { RequestMethod.POST })
    public void findPushToSAPList(@RequestParam(required = false) String orderId,
                         @RequestParam(required = false) String dnOrderId,
                         @RequestParam(required = false) Integer status,
                         @RequestParam(required = false) String startDate,
                         @RequestParam(required = false) String endDate,
                         @RequestParam(required = false) Integer rows,
                         @RequestParam(required = false) Integer page,
                         HttpServletRequest request, HttpServletResponse response){
        PagerInfo pager = new PagerInfo(rows, page);

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("orderId",orderId);
        map.put("dnOrderId",dnOrderId);
        map.put("status",status);
        map.put("start",pager.getStart());
        map.put("size",pager.getPageSize());
        if(org.apache.commons.lang3.StringUtils.isNotBlank(startDate)){
        	try {
				map.put("startDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate + " 00:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
        if(org.apache.commons.lang3.StringUtils.isNotBlank(endDate)){
        	try {
        		map.put("endDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate + " 23:59:59"));
        	} catch (ParseException e) {
        		e.printStackTrace();
        	}
        }

        ServiceResult<List<VehiclePushToSAP>> result = apiVehicleOrderService.findPushToSAPList2(map);

        if (result.getSuccess() && result.getResult() != null) {

            List<VehiclePushToSAP> predictstocklist = result.getResult();
            Map<String, Object> retMap = new HashMap<String, Object>();
            retMap.put("total", result.getPager().getRowsCount());
            retMap.put("rows", predictstocklist);
            Gson gson = new Gson();
            response.addHeader("Content-type", "text/html;charset=utf-8");
            try {
                response.getWriter().write(gson.toJson(retMap));
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = {"/exportPushToSAPList.export"}, method = { RequestMethod.POST })
    public void exportPushToSAP(@RequestParam(required = false) String orderNoList,@RequestParam(required = false) String queryParam,
                        HttpServletRequest request, HttpServletResponse response) {

    	//如果导出时选中了记录，则orderNoList有值，可转换为List<VehiclePushToSAP>
    	//如果orderNoList为空，则导出全部，根据queryParam检索数据
    	List<VehiclePushToSAP> list = new ArrayList<>();
        Gson gson = new Gson();
        if(org.apache.commons.lang3.StringUtils.isNotBlank(orderNoList)){
        	list = gson.fromJson(orderNoList, new TypeToken<List<VehiclePushToSAP>>() {}.getType());
        }else if(org.apache.commons.lang3.StringUtils.isNotBlank(queryParam)){
        	Map<String, Object> map = (Map) JSONObject.parse(queryParam);
        	//把值为""的设置为null，否则查询的时候数据不准确。
        	for(String key : map.keySet()){
        		if(map.get(key) != null && org.apache.commons.lang3.StringUtils.isBlank(map.get(key).toString())){
        			map.put(key, null);
        		}
        	}
        	if(map.get("startDate") != null && org.apache.commons.lang3.StringUtils.isNotBlank(map.get("startDate").toString())){
        		try {
    				map.put("startDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(map.get("startDate").toString() + " 00:00:00"));
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
        	}
        	if(map.get("endDate") != null && org.apache.commons.lang3.StringUtils.isNotBlank(map.get("endDate").toString())){
        		try {
        			map.put("endDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(map.get("endDate").toString() + " 00:00:00"));
        		} catch (ParseException e) {
        			e.printStackTrace();
        		}
        	}
        	map.put("start",0);
            map.put("size",2000);
            ServiceResult<List<VehiclePushToSAP>> result = apiVehicleOrderService.findPushToSAPList2(map);

            if (result.getSuccess() && result.getResult() != null) {
            	list = result.getResult();
            }
        }

//        try {
//            if (orderNoList != null && !orderNoList.equals("")) {
//                exportJson = (com.alibaba.dubbo.common.json.JSONArray) JSON
//                        .parse(orderNoList);
//                exportArray = new ArrayList<String>(exportJson.length());
//                // JSONArray转化为list
//                for (int i = 0; i < exportJson.length(); i++) {
//                    exportArray.add((String) exportJson.get(i));
//                }
//            }
//        } catch (com.alibaba.dubbo.common.json.ParseException e1) {
//            e1.printStackTrace();
//            logger.error("JSON转换失败！ 错误：" + e1.getMessage());
//        }
//
//
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("orderList",exportArray);

        HSSFWorkbook  wb = getDetailsData(list);

        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
        Date date=new Date();
        String str=sdf.format(date);
        String fileName = "推送SAP列表"+str;

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

    public HSSFWorkbook getDetailsData(List<VehiclePushToSAP> list) {

//        ServiceResult<List<Cn3wPurchaseStock>> result = apiVehicleOrderService.findPushToSAPList(map);
//        List<Cn3wPurchaseStock> list = new ArrayList<Cn3wPurchaseStock>();
//        list = (List<Cn3wPurchaseStock>) map.get("orderList");
//        if (result.getSuccess() && result.getResult() != null) {
//            List = result.getResult();
//        }
        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("推送SAP列表");
        int length = ExportPushToSAP.pushToSAPListTitle2.length;
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
            cell.setCellValue(ExportPushToSAP.pushToSAPListTitle2[i]);
            cell.setCellStyle(style);
        }

        //向单元格里添加数据
        for(short i=0;i<list.size();i++){
        	int t = 0;
            row = sheet.createRow(i+1);
            VehiclePushToSAP vp = list.get(i);
            row.createCell(t++).setCellValue(toCellString(vp.getCnStockSyncsId()));
            row.createCell(t++).setCellValue(toCellString(vp.getVbelnDn1()));
            row.createCell(t++).setCellValue(toCellString(vp.getVbelnDn5()));
            row.createCell(t++).setCellValue(toCellString(vp.getVbelnSpare()));
            row.createCell(t++).setCellValue(toCellString(vp.getLbx()));
            row.createCell(t++).setCellValue(toCellString(vp.getActualQty()));
            row.createCell(t++).setCellValue(toCellString(vp.getQty()));
            row.createCell(t++).setCellValue(toCellString(vp.getPushData()));
            row.createCell(t++).setCellValue(toCellString(vp.getReturnData()));

            String status = "";
            String cell = toCellString(vp.getStatus());
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
            row.createCell(t++).setCellValue(status);
            row.createCell(t++).setCellValue(toCellString(vp.getMessage()));


            SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (vp.getAddTime() == null){
                row.createCell(t++).setCellValue("");
            }else {
                row.createCell(t++).setCellValue(sdf2.format(vp.getAddTime()));
            }

            if (vp.getProcessTime() == null){
                row.createCell(t++).setCellValue("");
            }else {
                row.createCell(t++).setCellValue(sdf2.format(vp.getProcessTime()));
            }

        }
        return wb;
    }
    
    private String toCellString(Object obj){
    	if(obj != null){
    		return obj.toString();
    	}
    	return "";
    }


	@RequestMapping(value = {"/exportInterfaceLogList.export"})
	public void exportInterfaceLog(@RequestParam(required = false) String interfaceLogList,
								HttpServletRequest request, HttpServletResponse response) {

		com.alibaba.dubbo.common.json.JSONArray exportJson = new com.alibaba.dubbo.common.json.JSONArray();
		ArrayList<Long> exportArray = null;
		try {
			if (interfaceLogList != null && !interfaceLogList.equals("")) {
				exportJson = (com.alibaba.dubbo.common.json.JSONArray) JSON
						.parse(interfaceLogList);
				exportArray = new ArrayList<Long>(exportJson.length());
				// JSONArray转化为list
				for (int i = 0; i < exportJson.length(); i++) {
					exportArray.add((Long) exportJson.get(i));
				}
			}
		} catch (com.alibaba.dubbo.common.json.ParseException e1) {
			e1.printStackTrace();
			logger.error("JSON转换失败！ 错误：" + e1.getMessage());
		}

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("interfaceLogList",exportArray);

		HSSFWorkbook  wb = getInterfaceLogData(map);

		SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
		Date date=new Date();
		String str=sdf.format(date);
		String fileName = "整车接口日志列表"+str;

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

    public HSSFWorkbook getInterfaceLogData(Map<String,Object> map) {

        ServiceResult<List<InterfaceLog>> result = vehicleInterfaceLogService.findInterfaceLog(map);
        List<InterfaceLog> List = new ArrayList<InterfaceLog>();
        if (result.getSuccess() && result.getResult() != null) {
            List = result.getResult();
        }
        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("整车接口日志列表");
        int length = ExportVehicleInterfaceLog.vehicleInterfaceLogListTitle.length;
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
            cell.setCellValue(ExportVehicleInterfaceLog.vehicleInterfaceLogListTitle[i]);
            cell.setCellStyle(style);
        }

        //向单元格里添加数据
        for(short i=0;i<List.size();i++){
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(List.get(i).getRowId());
            SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                row.createCell(1).setCellValue(sdf2.format(sdf1.parse(List.get(i).getInterfaceDate().toString())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            row.createCell(2).setCellValue(List.get(i).getInterfaceName());
            row.createCell(3).setCellValue(List.get(i).getInterfaceMessage());
        }
        return wb;
    }
}
