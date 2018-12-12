package com.haier.svc.api.controller.vehicle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.vehcile.AreaCenterInfoDTO;
import com.haier.purchase.data.model.vehcile.ExportVehicleDTO;
import com.haier.purchase.data.model.vehcile.InterfaceLog;
import com.haier.purchase.data.model.vehcile.MaterielInfoDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDTO;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import com.haier.svc.api.controller.util.ExportVehicle;
import com.haier.svc.api.controller.util.HttpJsonResult;
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

	@RequestMapping(value = { "saveChang" }, method = { RequestMethod.POST })
	@ResponseBody
	public HttpJsonResult<String> saveChang(String itemNo,String vbelnSpare){
		HttpJsonResult<String> result = new HttpJsonResult<String>();
		int count = apiVehicleOrderService.updateVblenSpare(itemNo,vbelnSpare);
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

	@RequestMapping("/updateStatus")
	@ResponseBody
	public JSONObject updateStatus(String orderNo, String status) {
		return apiVehicleOrderService.updateStatus(orderNo, status);
	}

	@RequestMapping("/historyOrder")
	@ResponseBody
	public JSONObject historyOrder(String startDate, String endDate,
			String status, String order, int page, int rows)
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
		if (order != null && !"".equals(order)) {
			condition.setOrderNo(order);
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
		return apiVehicleOrderService.normalSave(dto, materielIds);
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
		System.out.println("11111111111111111");
		return apiVehicleOrderService.tempLoad(orderId);
	}

	/**
	 * 导出
	 */
	@RequestMapping(value = { "/exportVehicle.export" })
	void exportVehicle(@RequestParam(required = false) String orderNoList,
			Map<String, Object> modelMap, HttpServletRequest request,
			HttpServletResponse response) {
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
		sheet.setColumnWidth(10, (int) 16 * 256);
		sheet.setColumnWidth(11, (int) 16 * 256);
		sheet.setColumnWidth(12, (int) 16 * 256);
		sheet.setColumnWidth(13, (int) 21.57 * 256);
		sheet.setColumnWidth(14, (int) 21.57 * 256);
		sheet.setColumnWidth(15, (int) 21.57 * 256);
		sheet.setColumnWidth(16, (int) 21.57 * 256);
		sheet.setColumnWidth(17, (int) 11.14 * 256);
		sheet.setColumnWidth(18, (int) 15 * 256);
		sheet.setColumnWidth(19, (int) 21.57 * 256);
		sheet.setColumnWidth(20, (int) 8 * 256);
		sheet.setColumnWidth(21, (int) 12 * 256);
		sheet.setColumnWidth(22, (int) 8 * 256);
		sheet.setColumnWidth(23, (int) 8 * 256);
		sheet.setColumnWidth(24, (int) 8 * 256);
		sheet.setColumnWidth(25, (int) 23 * 256);
		sheet.setColumnWidth(26, (int) 35 * 256);
		sheet.setColumnWidth(27, (int) 21.57 * 256);
		sheet.setColumnWidth(28, (int) 21.57 * 256);
		sheet.setColumnWidth(29, (int) 40 * 256);
		sheet.setColumnWidth(30, (int) 15 * 256);
		sheet.setColumnWidth(31, (int) 15 * 256);
		sheet.setColumnWidth(32, (int) 15 * 256);
		sheet.setColumnWidth(33, (int) 15 * 256);
		sheet.setColumnWidth(34, (int) 15 * 256);

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
			if (dto.getLbxStatus() != null && !"".equals(dto.getLbxStatus())) {
				if (dto.getLbxStatus().equals(LbxStatusEnum.NEW.getCode())) {
					row.createCell(9).setCellValue(LbxStatusEnum.NEW.getName());
				} else if (dto.getLbxStatus().equals(
						LbxStatusEnum.ACCEPT.getCode())) {
					row.createCell(9).setCellValue(
							LbxStatusEnum.ACCEPT.getName());
				} else if (dto.getLbxStatus().equals(
						LbxStatusEnum.PARTFULFILLED.getCode())) {
					row.createCell(9).setCellValue(
							LbxStatusEnum.PARTFULFILLED.getName());
				} else if (dto.getLbxStatus().equals(
						LbxStatusEnum.FULFILLED.getCode())) {
					row.createCell(9).setCellValue(
							LbxStatusEnum.FULFILLED.getName());
				} else if (dto.getLbxStatus().equals(
						LbxStatusEnum.EXCEPTION.getCode())) {
					row.createCell(9).setCellValue(
							LbxStatusEnum.EXCEPTION.getName());
				} else if (dto.getLbxStatus().equals(
						LbxStatusEnum.CANCELED.getCode())) {
					row.createCell(9).setCellValue(
							LbxStatusEnum.CANCELED.getName());
				} else if (dto.getLbxStatus().equals(
						LbxStatusEnum.CLOSED.getCode())) {
					row.createCell(9).setCellValue(
							LbxStatusEnum.CLOSED.getName());
				} else if (dto.getLbxStatus().equals(
						LbxStatusEnum.REJECT.getCode())) {
					row.createCell(9).setCellValue(
							LbxStatusEnum.REJECT.getName());
				} else if (dto.getLbxStatus().equals(
						LbxStatusEnum.CANCELEDFAIL.getCode())) {
					row.createCell(9).setCellValue(
							LbxStatusEnum.CANCELEDFAIL.getName());
				} else {
					row.createCell(9).setCellValue("");
				}

			}

			if ("1".equals(dto.getSapStatus())) {
				row.createCell(10).setCellValue("推送SAP成功");
			} else if ("2".equals(dto.getSapStatus())) {
				row.createCell(10).setCellValue("推送SAP失败");
			} else if ("0".equals(dto.getSapStatus())) {
				row.createCell(10).setCellValue("未推送SAP");
			} else {
				row.createCell(10).setCellValue("未推送SAP");
			}
			
			if (orderStatusMap != null) {
				row.createCell(11).setCellValue(
						String.valueOf(orderStatusMap.get(String.valueOf(dto.getZkStatus()))));
			}

			if (orderStatusMap != null) {
				row.createCell(12).setCellValue(
						String.valueOf(orderStatusMap.get(String.valueOf(dto.getZqStatus()))));
			}

			row.createCell(13).setCellValue(dto.getZkRemark());
			row.createCell(14).setCellValue(dto.getZqRemark());
			row.createCell(15).setCellValue(dto.getMaterielCode());
			row.createCell(16).setCellValue(dto.getMaterielName());
			row.createCell(17).setCellValue(dto.getProductGroup());
			row.createCell(18).setCellValue(dto.getProductGroupName());
			row.createCell(19).setCellValue(dto.getBrand());
			row.createCell(20).setCellValue(String.valueOf(dto.getQty()));
			row.createCell(21).setCellValue(String.valueOf(dto.getUnitPrice()));
			row.createCell(22).setCellValue(String.valueOf(dto.getAmount()));
			row.createCell(23).setCellValue(String.valueOf(dto.getVolume()));
			row.createCell(24).setCellValue(
					String.valueOf(dto.getTotalVolume()));
			row.createCell(25).setCellValue(dto.getPaymentCode());
			row.createCell(26).setCellValue(dto.getPaymentName());
			row.createCell(27).setCellValue(dto.getJdName());
			row.createCell(28).setCellValue(dto.getDeliveryCode());
			row.createCell(29).setCellValue(dto.getDeliveryName());
			row.createCell(30).setCellValue(dto.getDistributionCentre());
			row.createCell(31).setCellValue(dto.getDistributionCentreName());
			row.createCell(32).setCellValue(dto.getWhCode());

			if (dto.getOrderTime() != null) {
				row.createCell(33).setCellValue(
						com.haier.svc.api.controller.util.date.DateUtil
								.getFormatDate(dto.getOrderTime()));
			}
			if (dto.getDateOfArrival() != null) {
				row.createCell(34).setCellValue(
						com.haier.svc.api.controller.util.date.DateUtil
								.getFormatDate(dto.getDateOfArrival()));
			}
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		java.util.Date date = new java.util.Date();
		String str = sdf.format(date);
		String fileName = "整车直发历史订单列表" + str;

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			wb.write(os);
			byte[] content = os.toByteArray();
			InputStream is = new ByteArrayInputStream(content);

			ExportExcelUtil.exportCommon(is, fileName, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
}
