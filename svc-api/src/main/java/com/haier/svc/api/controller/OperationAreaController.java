package com.haier.svc.api.controller;


import static com.haier.stock.model.InventoryBusinessTypes.RELEASE_BY_ZBCC;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.Boolean;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.haier.afterSale.service.*;
import com.haier.common.util.JsonUtil;
import com.haier.order.model.OrderProductStatus;
import com.haier.shop.model.*;
import com.haier.stock.service.VOMPropellingService;
import com.haier.svc.api.controller.util.ExcelCallbackInterfaceUtil;
import com.haier.svc.api.controller.util.ExcelExportUtil;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.haier.afterSale.model.Json;
import com.haier.afterSale.model.Ustring;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.invoice.util.HttpJsonResult;
import com.haier.order.service.MdmService;
import com.haier.shop.service.ShopOperationAreaService;
import com.haier.stock.service.StockCenterHopStockService;
import com.haier.stock.service.VomOrderService;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import com.haier.svc.bean.ShopEnum;
import jxl.write.*;
import jxl.write.Number;
import org.apache.log4j.LogManager;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.haier.afterSale.model.Json;
import com.haier.afterSale.model.Ustring;
import com.haier.afterSale.service.OperationAreaService;
import com.haier.afterSale.service.OrderService;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.order.service.MdmService;


import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;


/**
 * 网单
 * @author
 * Created by 吴坤洋 on 2017/10/24.
 *
 */
@Controller
@RequestMapping(value="operationArea/")
public class OperationAreaController {

	private static Logger LOGGER = LoggerFactory.getLogger(OperationAreaController.class);



/*	private final static org.apache.log4j.Logger LOGGER = LogManager
			.getLogger(OperationAreaController.class);*/

	@Autowired
	private ShopOperationAreaService shopOperationAreaService;
	@Autowired
	private MdmService mdmService;
	private final static org.apache.log4j.Logger logger = LogManager
			.getLogger(OperationAreaController.class);
	@Autowired
	private OperationAreaService operationAreaService;
	@Autowired
	private VomOrderService vomOrderService;
	@Autowired
	private StockCenterHopStockService stockCenterHopStockService;
	@Autowired
	private OrderService orderService;
    @Autowired
	private HPRejectionService hpRejectionService;
    @Autowired
    private VOMPropellingService vomPropellingService;

	/**
	 * 查询
	 * @param
	 * @return
	 */
	@RequestMapping("search")
	@ResponseBody
	public void search(OrderProductsVo vo,HttpServletRequest request, HttpServletResponse response){
		if(!"".equals(vo.getRegionAssign().trim())) {
			//区域查询由用省名查询改为用 o.province 查询
			if("east".equals(vo.getRegionAssign().trim())) {//东区
				//12 13 15 10 11 16
				//vo.setRegionAssign("浙江,安徽,江西,江苏,上海,山东");
				vo.setRegionAssign("east");
			}else 
			if("south".equals(vo.getRegionAssign().trim())) {//南区
				//18 19 20 21 14 22
				//vo.setRegionAssign("湖南,福建,广东,海南,广西,湖北");
				vo.setRegionAssign("south");
			}else
			if("west".equals(vo.getRegionAssign().trim())) {//西区
				// 5 26 25 29 27 24 30 32 31 23 28
				//vo.setRegionAssign("山西,云南,贵州,甘肃,西藏,四川,青海,新疆,宁夏,重庆，陕西");
				vo.setRegionAssign("west");
			}else
			if("north".equals(vo.getRegionAssign().trim())) {//北区
				// 2 3 4 5 6 7 8 9 17
				//vo.setRegionAssign("北京,河北,吉林,黑龙江,内蒙古,山西,天津,河南,辽宁");
				vo.setRegionAssign("north");
			}

		}
        //以下过程用于去source 中的 "" 以下代码 用来多来源查询 留下备用
//        String[] sourceList = null;
//        if (vo.getSource() != null && !"".equals(vo.getSource())) {
//            sourceList = vo.getSource().split(",");
//            StringBuffer sb = new StringBuffer();
//            for (int i = 0; i < sourceList.length; i++) {
//                if ("".equals(sourceList[i])) {
//                    continue;
//                }
//                sb.append(sourceList[i]);
//                if (i != sourceList.length - 1) {
//                    sb.append(",");
//                }
//            }
//            vo.setSource(sb.toString());
//        }

		if (vo.getRows() == null){
			vo.setRows(20);
		}
		if (vo.getPage() == null)
		{
			vo.setPage(1);
		}
		if (vo.getReceiptAddTimeMin() != null && !"".equals(vo.getReceiptAddTimeMin())){
			vo.setReceiptAddTimeMin(vo.getReceiptAddTimeMin().substring(0,10).replaceAll("-",""));
		}
		if (vo.getReceiptAddTimeMax() != null && !"".equals(vo.getReceiptAddTimeMax())){
			vo.setReceiptAddTimeMax(vo.getReceiptAddTimeMax().substring(0,10).replaceAll("-",""));
		}
		try {
			if(vo.getPage()>0){
				vo.setPage((vo.getPage() - 1) * vo.getRows());
			}else {
				vo.setPage(0);
			}
			ServiceResult<List<OrderProductsVo>> result =operationAreaService.searchList(vo);
			if (result.getSuccess() && result.getResult() != null) {
				List<OrderProductsVo> predictstocklist = result.getResult();
				Map<String, Object> retMap = new HashMap<String, Object>();
				retMap.put("total", result.getPager().getRowsCount());
				retMap.put("rows", predictstocklist);
				Gson gson=new Gson();
				response.addHeader("Content-type","text/html;charset=utf-8");
				response.getWriter().write(gson.toJson(retMap));
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (IOException e) {
			logger.error("错误", e);
		}
	}
	/**
	 * 页面跳转
	 * @return
	 */
	@RequestMapping("SinglePageJump")
	public ModelAndView SinglePageJump(String orderSn){
//		return  "order/netOrderList";
		ModelAndView mv  = new ModelAndView();
		if(StringUtil.isEmpty(orderSn)){
			mv.addObject("orderSn", "");
			mv.setViewName("order/netOrderList");
		}else{
			mv.addObject("orderSn", orderSn);
			mv.setViewName("order/netOrderList");
		}
		return mv;
	}

	/**
	 *  根据网单编号 查询明细 网单
	 * @param cOrderSn
	 * @return
	 */
	@RequestMapping("ProductView")
	public ModelAndView ProductView(String cOrderSn){
		ModelAndView mv  = new ModelAndView();
		if(cOrderSn.contains("R")){
			cOrderSn = cOrderSn.substring(0, cOrderSn.lastIndexOf("R"));
		}
		OrderProductsVo vo =operationAreaService.PrudectView(cOrderSn);
		OrderRepairs orderRepairs=operationAreaService.selectOrderSn(cOrderSn);//根据网单
		if(vo != null){
			String source = vo.getSource();
			vo.setSource(getSourceName(source));
			Map<String, String> map = operationAreaService.selectMemberInvoicesByorderSn(vo.getOrderSn());
			ExpressInfos expressInfos = operationAreaService.findBycOrderSn(cOrderSn);
			if (expressInfos != null){
                mv.addObject("expressCompany", expressInfos.getExpressCompany());
                mv.addObject("expressNumber", expressInfos.getExpressNumber());
            }else {
				mv.addObject("expressCompany","");
				mv.addObject("expressNumber", "");
			}
			mv.addObject("vo", vo);
			mv.addObject("bitMap", map);
		}

		String handleStatus ="";
		if(null==orderRepairs) {
			orderRepairs = new OrderRepairs();
		}else {
			handleStatus =orderRepairs.getHandleStatus().toString();
		}
		mv.addObject("handleStatus", handleStatus);
		mv.addObject("repairsId", orderRepairs.getId());
		mv.setViewName("order/norderDetail");
		return mv;
	}

	/**
	 * 前台easyui请求查询 明细 （网单）
	 * @throws ParseException
	 * @throws
	 */
	@RequestMapping("PrudevtDatail")
	@ResponseBody
	public String PrudevtDatail(String cOrderSn){
		Gson gosn = new Gson();
		OrderProductsVo vodata= operationAreaService.PrudectDetailed(cOrderSn);
		return gosn.toJson(vodata);
	}
	/**
	 * 查询网单操作日志
	 * @param productId
	 * @return
	 */
	@RequestMapping("OperationHistory")
	@ResponseBody
	public List<OrderOperateLogs> OperationHistory(String productId) {
		List<OrderOperateLogs> orderOperateLogs=	operationAreaService.getProductIdVdiew(productId);
		return orderOperateLogs;
	}

	/**
	 * 保存退货信息
	 */
	@RequestMapping(value="SaveRepairs",method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public Json SaveRepairs(@RequestBody OrderRepairsVo orderRepairs,HttpServletRequest request){
		HttpSession session = request.getSession();
		Json josn = new Json();
		String userName = Ustring.getString(session.getAttribute("userName"));
		josn = operationAreaService.SaveRepairs(orderRepairs,userName);
		return josn;
	}
	/**
	 * 跳转退货审核页面
	 * @return
	 */
	@RequestMapping("ReturnEdit")
	public ModelAndView ReturnEdit(String id){
		ModelAndView mv = new ModelAndView();
		Map<String, Object> map=  operationAreaService.ReturnEdit(id); //查询退货页面详情
		OrderRepairsVo vo = (OrderRepairsVo) map.get("vo");
		Object list =map.get("list");
		Object repairlist =map.get("repairLesreCords");
		Object hpRecordsList =map.get("hpRecords");
		mv.addObject("vo",vo);
		mv.addObject("list",list);
		mv.addObject("hpRecordsList",hpRecordsList);
		mv.addObject("repairlist",repairlist);
		mv.setViewName("order/returnChangeOrderDetail");
		return mv;
	}
	/*
   		* 优惠券信息
   		* */
	@ResponseBody
	@RequestMapping("selectCoupon")
	public Json selectCoupon(String orderSn) throws ParseException {
		Json json=new Json();
		List<Map<String,String>> couponList= operationAreaService.selectCoupon(orderSn);
		json.setObj(couponList);
		return  json;
	}
	/*
        * 订单操作日志
        * */
	@ResponseBody
	@RequestMapping("selectOrderOperateLogs")
	public Json selectOrderOperateLogs(String orderSn) throws ParseException {
		Json json=new Json();
		List<Map<String,String>>OrderOperateLogsList=  operationAreaService.selectOrderOperateLogs(orderSn);
		json.setObj(OrderOperateLogsList);
		return json;
	}
	/**
	 *  根据订单编号 查询明细 表单
	 * @param orderSn
	 * @return
	 * @author zhangbo
	 */
	@ResponseBody
	@RequestMapping("selectPrudevtDatail")
	public Json selectPrudevtDatail(String orderSn) throws ParseException {
		Json json=new Json();
		List<Map<String,String>> map=operationAreaService.selectPrudevtDatail(orderSn);
		json.setObj(map);

		return json;
	}
	/**
	 *  根据订单编号 查询明细 表单
	 * @param orderSn
	 * @return
	 * @author zhangbo
	 */
	@ResponseBody
	@RequestMapping("orderNumberSelect")
	public ModelAndView orderNumberSelect(String orderSn){
		ModelAndView mv  = new ModelAndView();
		Map<String,String> bitMap =operationAreaService.orderNumberSelect(orderSn);
		mv.addObject("bitMap", bitMap);
		mv.setViewName("order/orderDetails");
		return  mv;
	}

	/**
	 * 查询 Region表中的省市区街
	 * @param pid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getRegion")
	public JSONArray getRegion(int pid,String shippingMode){
	    if ("B2C".equals(shippingMode)){
	        return operationAreaService.getRegionB2C(pid);
        }else {
			return operationAreaService.getRegion(pid);
		}

	}

	/**
	 *  根据订单编号 查询是否网单是否开具成功
	 * @param orderSn
	 * @return
	 * @author zhangbo
	 */
	@ResponseBody
	@RequestMapping("netList")
	public Json netList(String orderSn){
		Map<String,String> bitMap =operationAreaService.orderNumberSelect(orderSn);
		Json json = new Json();
		String string = bitMap.get("invoiceOperation");
		if(!StringUtil.isEmpty(string)) {
			if(string.equals("1")) {
				json.setSuccess(false);
			}else {
				json.setSuccess(true);
			}
		}else {
			json.setSuccess(true);
		}
		return  json;
	}
	/**
	 * 更新 orders 表
	 * @param orderSn
	 * @param province
	 * @param city
	 * @param region
	 * @param street
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateRegion")
	public Json updateRegion(String orderSn,int province,int city,int region,int street,String oldRegionName,int orderId,HttpServletRequest request){
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		ServiceResult<Boolean> result = operationAreaService.updateRegion(orderSn,province,city,region,street,orderId,userName,oldRegionName);

		boolean success = result.getSuccess();
		String message = result.getMessage();
		Json json = new Json();
		json.setSuccess(success);
		json.setMsg(message);
		return json;
	}

	@ResponseBody
	@RequestMapping("getRegionName")
	public String getRegionName(String orderSn){
		return operationAreaService.getRegionName(orderSn);
	}

	/**
	 * 退货审核
	 * @param id
	 * @return
	 */
	@RequestMapping("Toexamine")
	@ResponseBody
	public Json Toexamine(String id,String status,String handleRemark,String cOrderSn,HttpServletRequest request){
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		return operationAreaService.Toexamine(id, status, handleRemark,userName,cOrderSn);
	}
	/*
	* hp拒收跳转页面
	* @author zhangbo
	* */
	@ResponseBody
	@RequestMapping("select_WwwHpRecords")
	public ModelAndView select_WwwHpRecords(String tj){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("order/hpRejectable");
		return mv;
	}
	/*
	* hp拒收表格显示
	* @author zhangbo
	* */
	@ResponseBody
	@RequestMapping("datagrid_WwwHpRecords")
	public Json datagrid_WwwHpRecords(@RequestBody Map<String,Object> map)throws Exception{
		if (Ustring.isEmpty(map.get("success").toString())){
			map.put("success",4);
		}
		if (map.get("rows")==null){
			map.put("rows",20);
		}
		if (map.get("page")==null) {
			map.put("page",1);
		}
		/*
		* 设置分页参数
		* */
		if (Integer.parseInt(map.get("page").toString()) > 0) {
			int pageNumber = (Integer.parseInt(map.get("page").toString()) - 1) * Integer.parseInt(map.get("rows").toString());
			map.put("page", pageNumber);
		} else {
			map.put("page", 0);
		}
		List<Map<String, String>> recordsList = null;
		List<Map<String, String>> countList = null;
		Json json = new Json();
		/*
		* 将"yyyy-MM-dd转换为时间戳"
		* */
		if (Ustring.isNotEmpty(map.get("addTimeMin").toString())){
			String time=map.get("addTimeMin").toString();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date=sdf.parse(time);
			// 除1000为了与数据库存储形式一致
			long addTimeMin=date.getTime()/1000;
			map.put("addTimeMin",addTimeMin);
		}
		//在页面显示的list
		recordsList = operationAreaService.datagrid_WwwHpRecords(map);
		//统计总页数的
		countList = operationAreaService.datagrid_WwwHpRecords1(map);
		json.setObj(recordsList);
		json.setTotal(countList.size());
		return json;
	}
	/*
	* hp拒收匹配
	* @author zhangbo
	* */
	@ResponseBody
	@RequestMapping("exceptional_point")
	public Json exceptional_point(@RequestBody List<Map<String,Object>> hpRecordsList,HttpServletRequest request)throws Exception{
		Json jsonInfo=new Json();
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		if (hpRecordsList.size()==0){
			logger.info("暂时没有需要重置异常的数据!");
			jsonInfo.setMsg("暂时没有需要重置异常的数据");
			return jsonInfo;
		}
		//hpRecordsList:查询出WwwHpRecords(HP拒收表的数据)
		for (Map map:hpRecordsList){
			String hpTbSn=map.get("hpTbSn")+"";
			//selectMap:根据TB单号查询网单
			List<Map<String,Object>> selectMapList=shopOperationAreaService.queryOrderProductByTB(hpTbSn);
			Map<String,Object> selectMap=new HashMap<>();
			int sign=0;
			for (Map thSnMap:selectMapList){
				String repairSn=thSnMap.get("repairSn")+"";
				if (StringUtil.isEmpty(repairSn)){
					continue;
				}
				if (!repairSn.contains("TH")){
					continue;
				}
				String num=repairSn.substring(repairSn.length()-1,repairSn.length());
				boolean boo=isNumeric(num);
				if (boo==false){
					continue;
				}
				int n=Integer.parseInt(num);
				if (n>sign){
					sign=n;
				}
			}
			if (sign!=0){
				String reSn="TH"+sign;
				for (Map map1:selectMapList){
					String repairSn=map1.get("repairSn")+"";
					if (repairSn.contains(reSn)){
						selectMap=map1;
					}
				}
			}
			//匹配网单失败
			if (selectMap==null){
				//更新WwwHpRecords表中的匹配次数
				map.put("remark","未匹配到网单号");
				shopOperationAreaService.updateHPjushouCount(map);
				continue;
			}
			//匹配网单成功
			if (selectMap!=null){
				//sku相同
				if (selectMap.get("sku").toString().equals(map.get("sku").toString())){
					//根据TB查询出的数据中没有退货单号
					if (selectMap.containsKey("th_id")==false){
						//生成退货单号
						JSONObject jsonObject=generateTHSn(selectMap,userName);
						//1:生成退货单号失败
						if (StringUtil.isEmpty(jsonObject.get("th_id")+"")){
							//更新WwwHpRecords表中的匹配次数
							map.put("remark","生成退货单号失败");
							jsonInfo.setMsg("生成退货单号失败");
							shopOperationAreaService.updateHPjushouCount(map);
							continue;
						}
						//2:生成退货单号成功
						if (!StringUtil.isEmpty(jsonObject.get("th_id")+"")){
							//生成退货单号成功,记录日志
							addOrderRepairLogs(userName,Integer.parseInt(jsonObject.get("th_id").toString()),"系统","拒收匹配生成退货单成功");
							selectMap.put("th_id",jsonObject.get("th_id"));
							map.put("th_id",jsonObject.get("th_id"));
							if (StringUtil.isEmpty(map.get("vomRepairSn").toString())){
								map.put("vomRepairSn","");
								//发起鉴定请求
								Json json=ModifyPushHPDate(jsonObject.get("th_id").toString());
								//1:发起一次质检失败
								if (json.getSuccess()==false){
									map.put("remark","发起一次质检失败");
									//更新WwwHpRecords表中的匹配次数
									shopOperationAreaService.updateHPjushouCount(map);
									jsonInfo.setMsg("发起一次质检失败");
									continue;
								}
								addOrderRepairLogs(userName,Integer.parseInt(jsonObject.get("th_id").toString()),"系统","发起一次质检");
							}
							if (!StringUtil.isEmpty(map.get("vomRepairSn").toString())){
								addOrderRepairLogs(userName,Integer.parseInt(jsonObject.get("th_id").toString()),"系统","等待HP确认拒收信息");
							}
							selectMap.put("th_id",jsonObject.get("th_id").toString());
							map.put("handleStatus",2);
							//更改退货表中的vomRepairSn和handleStatus字段值
							shopOperationAreaService.updateOrderRepairsStatus(map);
							addOrderRepairLogs(userName,Integer.parseInt(jsonObject.get("th_id").toString()),"系统","更改退货单的状态为进行中");
							List<Map<String,Object>> list=new ArrayList<>();
							list.add(selectMap);
							operationAreaService.update_WwwHpRecordsInfo(list);
							jsonInfo.setMsg("匹配成功");
							addOrderRepairLogs(userName,Integer.parseInt(jsonObject.get("th_id").toString()),"系统","HP拒收匹配成功");
							return jsonInfo;
						}
					}
					//根据TB查询出的数据中有退货单号
					if (selectMap.containsKey("th_id")==true){
						//如果退货单中的状态为以终止,重新生成退货单
						if (selectMap.get("handleStatus").toString().equals("6")){
							//生成退货单号
							JSONObject jsonObject=generateTHSn(selectMap,userName);
							if (StringUtil.isEmpty(jsonObject.get("th_id")+"")){
								//更新WwwHpRecords表中的匹配次数
								map.put("remark","生成退货单号失败");
								jsonInfo.setMsg("生成退货单号失败");
								shopOperationAreaService.updateHPjushouCount(map);
								continue;
							}
							selectMap.put("th_id",jsonObject.get("th_id"));
						}
						map.put("th_id",selectMap.get("th_id"));
						if (StringUtil.isEmpty(map.get("vomRepairSn").toString())){
							map.put("vomRepairSn","");
							//发起鉴定请求
							Json json=ModifyPushHPDate(selectMap.get("th_id").toString());
							//1:发起一次质检失败
							if (json.getSuccess()==false){
								map.put("remark","生成一次质检失败");
								//更新WwwHpRecords表中的匹配次数
								shopOperationAreaService.updateHPjushouCount(map);
								continue;
							}
							addOrderRepairLogs(userName,Integer.parseInt(selectMap.get("th_id").toString()),"系统","发起一次质检");
						}
						if (!StringUtil.isEmpty(map.get("vomRepairSn").toString())){
							addOrderRepairLogs(userName,Integer.parseInt(selectMap.get("th_id").toString()),"系统","等待HP确认拒收");

						}
						//如果退货单中的状态为以驳回,直接匹配异常
						if (selectMap.get("handleStatus").toString().equals("5")){
							map.put("remark","退货单中的状态为以驳回");
							//更新WwwHpRecords表中的匹配次数
							shopOperationAreaService.updateHPjushouCount(map);
							addOrderRepairLogs(userName,Integer.parseInt(selectMap.get("th_id").toString()),"系统","退货单中的状态为以驳回");
							continue;
						}
						map.put("handleStatus",2);
						//更改退货表中的vomRepairSn和handleStatus字段值
						shopOperationAreaService.updateOrderRepairsStatus(map);
						addOrderRepairLogs(userName,Integer.parseInt(selectMap.get("th_id").toString()),"系统","更改退货单的状态为进行中");
						List<Map<String,Object>> list=new ArrayList<>();
						list.add(selectMap);
						operationAreaService.update_WwwHpRecordsInfo(list);
						jsonInfo.setMsg("匹配成功");
						addOrderRepairLogs(userName,Integer.parseInt(selectMap.get("th_id").toString()),"系统","HP拒收匹配成功");
						return jsonInfo;
					}
				}
				//sku不同,有可能是套机,套机HP回传的是子物料,网单存的是主物料,需要去套机表中根据子物料
				//得到主物料,然后根据判断是不是和网单表里面的主物料是否一样
				if (!selectMap.get("sku").toString().equals(map.get("sku").toString())){
					String sku=map.get("sku")+"";
					List<Map<String,Object>> mapLis=new ArrayList<>();
					Map<String,Object> sub_skuMap=new HashMap<>();
					sub_skuMap.put("input_sku",sku);
					mapLis.add(selectMap);
					List<Map<String,Object>> mainAndSub_sku=operationAreaService.select_sku(mapLis);
					int count=0;
					for (Map skuMap:mainAndSub_sku){
						if (skuMap.get("main_sku").toString().equals(selectMap.get("sku").toString())){
							count=1;
							//根据TB查询出的数据中没有退货单号
							if (selectMap.containsKey("th_id")==false){
								//生成退货单号
								JSONObject jsonObject=generateTHSn(selectMap,userName);
								//1:生成退货单号失败
								if (StringUtil.isEmpty(jsonObject.get("th_id")+"")){
									//更新WwwHpRecords表中的匹配次数
									map.put("remark","生成退货单号失败");
									shopOperationAreaService.updateHPjushouCount(map);
									continue;
								}
								map.put("th_id",jsonObject.get("th_id").toString());
								//2:生成退货单号成功
								if (!StringUtil.isEmpty(jsonObject.get("th_id")+"")){
									selectMap.put("th_id",jsonObject.get("th_id").toString());
									map.put("handleStatus",2);
									if (StringUtil.isEmpty(map.get("vomRepairSn").toString())){
										map.put("vomRepairSn","");
										//发起鉴定请求
										Json json=ModifyPushHPDate(jsonObject.get("th_id").toString());
										//1:发起一次质检失败
										if (json.getSuccess()==false){
											map.put("remark","生成一次质检失败");
											//更新WwwHpRecords表中的匹配次数
											shopOperationAreaService.updateHPjushouCount(map);
											continue;
										}
										addOrderRepairLogs(userName,Integer.parseInt(jsonObject.get("th_id").toString()),"系统","发起一次质检");
									}
									if (!StringUtil.isEmpty(map.get("vomRepairSn").toString())){
										addOrderRepairLogs(userName,Integer.parseInt(jsonObject.get("th_id").toString()),"系统","等待HP确认拒收");

									}
									//如果退货单中的状态为以驳回,直接匹配异常
									if (selectMap.get("handleStatus").toString().equals("5")){
										map.put("remark","退货单中的状态为以驳回");
										//更新WwwHpRecords表中的匹配次数
										shopOperationAreaService.updateHPjushouCount(map);
										addOrderRepairLogs(userName,Integer.parseInt(jsonObject.get("th_id").toString()),"系统","退货单中的状态为以驳回");
										continue;
									}
									map.put("handleStatus",2);
									//更改退货表中的vomRepairSn和handleStatus字段值
									shopOperationAreaService.updateOrderRepairsStatus(map);
									addOrderRepairLogs(userName,Integer.parseInt(jsonObject.get("th_id").toString()),"系统","更改退货单状态为进行中");
									List<Map<String,Object>> list=new ArrayList<>();
									list.add(selectMap);
									operationAreaService.update_WwwHpRecordsInfo(list);
									jsonInfo.setMsg("匹配成功");
									addOrderRepairLogs(userName,Integer.parseInt(jsonObject.get("th_id").toString()),"系统","HP拒收匹配成功");
									return jsonInfo;
								}
							}
							//根据TB查询出的数据中有退货单号
							if (selectMap.containsKey("th_id")==true){
								if (selectMap.get("handleStatus").toString().equals("6")){
									//生成退货单号
									JSONObject jsonObject=generateTHSn(selectMap,userName);
									if (StringUtil.isEmpty(jsonObject.get("th_id")+"")){
										//更新WwwHpRecords表中的匹配次数
										map.put("remark","生成退货单号失败");
										jsonInfo.setMsg("生成退货单号失败");
										shopOperationAreaService.updateHPjushouCount(map);
										continue;
									}
									selectMap.put("th_id",jsonObject.get("th_id"));
								}
								map.put("th_id",selectMap.get("th_id"));
								if (StringUtil.isEmpty(map.get("vomRepairSn").toString())){
									map.put("vomRepairSn","");
									//发起鉴定请求
									Json json=ModifyPushHPDate(selectMap.get("th_id").toString());
									//1:发起一次质检失败
									if (json.getSuccess()==false){
										map.put("remark","生成一次质检失败");
										//更新WwwHpRecords表中的匹配次数
										shopOperationAreaService.updateHPjushouCount(map);
										continue;
									}
									addOrderRepairLogs(userName,Integer.parseInt(selectMap.get("th_id").toString()),"系统","发起一次质检");
								}
								if (!StringUtil.isEmpty(map.get("vomRepairSn").toString())){
									addOrderRepairLogs(userName,Integer.parseInt(selectMap.get("th_id").toString()),"系统","等待HP确认拒收");

								}
								//如果退货单中的状态为以驳回,直接匹配异常
								if (selectMap.get("handleStatus").toString().equals("5")){
									map.put("remark","退货单中的状态为以驳回");
									//更新WwwHpRecords表中的匹配次数
									shopOperationAreaService.updateHPjushouCount(map);
									addOrderRepairLogs(userName,Integer.parseInt(selectMap.get("th_id").toString()),"系统","退货单中的状态为以驳回");
									continue;
								}
								map.put("handleStatus",2);
								//更改退货表中的vomRepairSn和handleStatus字段值
								shopOperationAreaService.updateOrderRepairsStatus(map);
								addOrderRepairLogs(userName,Integer.parseInt(selectMap.get("th_id").toString()),"系统","更改退货单的状态为进行中");
								List<Map<String,Object>> list=new ArrayList<>();
								list.add(selectMap);
								operationAreaService.update_WwwHpRecordsInfo(list);
								jsonInfo.setMsg("匹配成功");
								addOrderRepairLogs(userName,Integer.parseInt(selectMap.get("th_id").toString()),"系统","HP拒收匹配成功");
								return jsonInfo;
							}
						}
					}
					if (count==0){
						map.put("remark","sku不存在,匹配失败");
						//更新WwwHpRecords表中的匹配次数
						jsonInfo.setMsg("sku不存在,匹配失败");
						shopOperationAreaService.updateHPjushouCount(map);
						return jsonInfo;
					}
				}
			}
		}
		jsonInfo.setMsg("操作完成");
		return jsonInfo;
	}
	/*
	* hp拒收报表导出
	* */
	@ResponseBody
	@RequestMapping("export_Excel")
	public Json export_Excel(@RequestParam Map<String,Object> map2, HttpServletResponse response)throws Exception{
		String strMap=map2.get("exportData").toString();
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<>();
		map = gson.fromJson(strMap, map.getClass());
		Json json=new Json();
		if (Ustring.isEmpty(map.get("success").toString())){
			map.put("success",4);
		}
		//查询需要导出的数据
		List<Map<String,Object>> export_ExcelData=operationAreaService.select_export_ExcelData(map);
		if (export_ExcelData.size()==0){
			json.setMsg("没有数据导出");
			return json;
		}


		//创建工作薄对象
		HSSFWorkbook wb = new HSSFWorkbook();
		//创建页
		HSSFSheet sheet_1 =  wb.createSheet("hp拒收报表");
		//创建标题行
		HSSFRow headRow = sheet_1.createRow(0);
		//创建标题行第一列
		HSSFCell head_cell_1 = headRow.createCell(0);
		//第一列内容
		head_cell_1.setCellValue("VOM退货单号");
		//创建标题行第2列
		HSSFCell head_cell_2 = headRow.createCell(1);
		//第2列内容
		head_cell_2.setCellValue("网单号");
		//创建标题行第一列
		HSSFCell head_cell_3 = headRow.createCell(2);
		//第3列内容
		head_cell_3.setCellValue("TB单号");
		//创建标题行第4列
		HSSFCell head_cell_4 = headRow.createCell(3);
		//第4列内容
		head_cell_4.setCellValue("匹配状态");
		//创建标题行第5列
		HSSFCell head_cell_5 = headRow.createCell(4);
		//第5列内容
		head_cell_5.setCellValue("备注");
		//创建标题行第6列
		HSSFCell head_cell_6 = headRow.createCell(5);
		//第6列内容
		head_cell_6.setCellValue("添加时间");
		//创建标题行第7列
		HSSFCell head_cell_7 = headRow.createCell(6);
		//第7列内容
		head_cell_7.setCellValue("最后更新时间");
		//创建标题行第8列
		HSSFCell head_cell_8 = headRow.createCell(7);
		//第8列内容
		head_cell_8.setCellValue("物料号");
		//为内容行添加数据和样式
		for (int i = 1; i <= export_ExcelData.size(); i++) {
			//拿取export_ExcelData坐标下的数据
			Map<String,Object> dataMap=export_ExcelData.get(i-1);
			//创建行
			HSSFRow contentRow = sheet_1.createRow(i);
			//创建每一个单元格
			HSSFCell content_cell_1 = contentRow.createCell(0);
			HSSFCell content_cell_2 = contentRow.createCell(1);
			HSSFCell content_cell_3 = contentRow.createCell(2);
			HSSFCell content_cell_4 = contentRow.createCell(3);
			HSSFCell content_cell_5 = contentRow.createCell(4);
			HSSFCell content_cell_6 = contentRow.createCell(5);
			HSSFCell content_cell_7 = contentRow.createCell(6);
			HSSFCell content_cell_8 = contentRow.createCell(7);
			content_cell_1.setCellValue(dataMap.get("vomRepairSn").toString());
			content_cell_2.setCellValue(dataMap.get("cOrderSn").toString());
			content_cell_3.setCellValue(dataMap.get("hpTbSn").toString());
			String xx=dataMap.get("success")+"";
			int success=Integer.parseInt(xx);
			if (success==0){
				dataMap.put("success","失败");
			}
			if (success==1){
				dataMap.put("success","成功");
			}
			if (success==2){
				dataMap.put("success","待匹配");
			}
			if (success==3){
				dataMap.put("success","带重新匹配");
			}
			content_cell_4.setCellValue(dataMap.get("success").toString());
			content_cell_5.setCellValue(dataMap.get("remark").toString());
			content_cell_6.setCellValue(dataMap.get("addTime").toString());
			content_cell_7.setCellValue(dataMap.get("modifytime").toString());
			content_cell_8.setCellValue(dataMap.get("sku").toString());
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String str = sdf.format(date);
		String fileName = "hp拒收报表" + str;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		wb.write(os);
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		ExportExcelUtil.exportCommon(is, fileName, response);
		return json;
	}

	@RequestMapping("Rejectionsinglereset")
	@ResponseBody
	public Object Rejectionsinglereset(String id) {
		ServiceResult<JSONObject> result = new ServiceResult<>();
		result=operationAreaService.Rejectionsinglereset(id);
		return JsonUtil.toJson(result);
	}


	/**
	 * 推送HP信息
	 * @throws java.text.ParseException
	 * @throws MalformedURLException
	 */
	@ResponseBody
	@RequestMapping(value="ModifyPushHP" ,method=RequestMethod.POST, consumes="application/json")
	public Json ModifyPushHP(@RequestBody OrderRepairsVo orderRepairsVo,HttpServletRequest request) throws MalformedURLException, java.text.ParseException{
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		List<OrderRepairsVo> vo  = new ArrayList<>();
		orderRepairsVo.setMenuflag("SD");//标示是手动来推送的
		vo.add(orderRepairsVo);
		return operationAreaService.ModifyPushHP(vo,userName);
	}
//	/**
//	 * 接收天猫退货信息存到数据库
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value="tmReturnData", method=RequestMethod.POST, consumes = "application/json")
//	@ResponseBody
//	public Json TMReturnData(@RequestBody JSONObject params){
//		Json json = new Json();
//		List<OrderTmallReturnLogs> orderTmallReturnLogs = (List<OrderTmallReturnLogs>) JSON.parseObject(JSON.toJSONString(params), OrderTmallReturnLogs.class);
//		operationAreaService.TMReturnData(orderTmallReturnLogs);
//		return json;
//	}
	//退换货列表
	@ResponseBody
	@RequestMapping("select_orderrepairtcs")
	public ModelAndView select_orderrepairtcs(String tj){
		ModelAndView mv=new ModelAndView();
		mv.setViewName("order/returnChangeList");
		return mv;
	}
	/*
	* 退换货表格显示
	* @author zhangbo
	* */
	@ResponseBody
	@RequestMapping("datagrid_orderForecastGoal")
	public Json datagrid_orderForecastGoal(@RequestBody Map<String,Object> map)throws Exception{
		Json json=new Json();
		/*
		* 设置分页参数
		* */
		if (Integer.parseInt(map.get("page").toString()) > 0) {
			int pageNumber = (Integer.parseInt(map.get("page").toString()) - 1) * Integer.parseInt(map.get("rows").toString());
			map.put("page", pageNumber);
		} else {
			map.put("page", 0);
		}
		//表格显示的数据
		List<Map<String, String>>Returnlist = operationAreaService.datagrid_orderForecastGoal(map);
		//分页使用的count
		List<Map<String, String>> countList = operationAreaService.datagrid_orderForecastGoalcount(map);
		List<String> east=new ArrayList<>();//东区
		List<String> south=new ArrayList<>();//南区
		List<String> west=new ArrayList<>();//西区
		List<String> north=new ArrayList<>();//北区
		east.add("浙江");east.add("安徽");east.add("江西");east.add("江苏");east.add("上海");east.add("山东");
		south.add("湖南");south.add("福建");south.add("广东");south.add("海南");south.add("广西");south.add("湖北");
		west.add("陕西");west.add("云南");west.add("贵州");west.add("甘肃");west.add("西藏");west.add("四川");west.add("青海");west.add("新疆");west.add("宁夏");west.add("重庆");
		north.add("北京");north.add("河北");north.add("吉林");north.add("黑龙江");north.add("内蒙古");north.add("山西");north.add("天津");north.add("河南");north.add("辽宁");
		//将Unix时间戳转化为日期时间
		for (Map map1:Returnlist){
			String addTime=map1.get("addTime").toString();
			String province = String.valueOf(map1.get("province"));
			if (!addTime.equals("0")){
			Long timestamp = Long.parseLong(addTime)*1000;
			String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
			map1.put("addTime",date);
			}else{
				map1.put("addTime","");
			}
			if (east.contains(province)){
				map1.put("region","东区");
			}
			if (south.contains(province)){
				map1.put("region","南区");
			}
			if (west.contains(province)){
				map1.put("region","西区");
			}
			if (north.contains(province)){
				map1.put("region","北区");
			}
		}
		json.setObj(Returnlist);
		json.setTotal(countList.size());
		return json;
	}
	/*
	* 退换货报表导出
	* */
	@ResponseBody
	@RequestMapping("export_ExcelOrderRepairs")
	public Json export_ExcelOrderRepairs(@RequestParam Map<String,Object> map2, HttpServletResponse response)throws Exception{
		String strMap = map2.get("exportData").toString();
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<>();
		map = gson.fromJson(strMap, map.getClass());
//		map.put("type",1);
		Json json=new Json();
		//查询需要导出的数据
		List<Map<String,Object>> export_ExcelData=operationAreaService.export_ExcelOrderRepairs(map);
		if (export_ExcelData.size()==0){
			json.setMsg("没有数据导出");
			return json;
		}
		List<String> east=new ArrayList<>();//东区
		List<String> south=new ArrayList<>();//南区
		List<String> west=new ArrayList<>();//西区
		List<String> north=new ArrayList<>();//北区
		east.add("浙江");east.add("安徽");east.add("江西");east.add("江苏");east.add("上海");east.add("山东");
		south.add("湖南");south.add("福建");south.add("广东");south.add("海南");south.add("广西");south.add("湖北");
		west.add("陕西");west.add("云南");west.add("贵州");west.add("甘肃");west.add("西藏");west.add("四川");west.add("青海");west.add("新疆");west.add("宁夏");west.add("重庆");
		north.add("北京");north.add("河北");north.add("吉林");north.add("黑龙江");north.add("内蒙古");north.add("山西");north.add("天津");north.add("河南");north.add("辽宁");
		for (Map map1:export_ExcelData){
			//将Unix时间戳转化为日期时间(addTime)
			String addTime=map1.get("addTime").toString();
			String province=map1.get("province").toString();
			if(map1.get("hpTime") != null) {
				String hpTime = map1.get("hpTime").toString();
				if (!hpTime.equals("0")){
					Long timestamp = Long.parseLong(hpTime)*1000;
					String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
					map1.put("hpTime",date);
				}else{
					map1.put("hpTime","");
				}
			}
			if(map1.get("paymentTime") != null) {
				String paymentTime = map1.get("paymentTime").toString();
				if (!paymentTime.equals("0")){
					Long timestamp = Long.parseLong(paymentTime)*1000;
					String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
					map1.put("paymentTime",date);
				}else{
					map1.put("paymentTime","");
				}
			}
            String hpFirstAddTime = map1.get("hpFirstAddTime").toString();
			if (!addTime.equals("0")){
				Long timestamp = Long.parseLong(addTime)*1000;
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
				map1.put("addTime",date);
			}else{
				map1.put("addTime","");
			}

            if (!hpFirstAddTime.equals("0")){
                Long timestamp = Long.parseLong(hpFirstAddTime)*1000;
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
                map1.put("hpFirstAddTime",date);
            }else{
                map1.put("hpFirstAddTime","");
            }

			if(map1.get("userAcceptTime") != null) {
				String userAcceptTime = map1.get("userAcceptTime").toString();
				if (!userAcceptTime.equals("0")){
					Long timestamp = Long.parseLong(userAcceptTime)*1000;
					String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
					map1.put("userAcceptTime",date);
				}else{
					map1.put("userAcceptTime","");
				}
			}
			if (east.contains(province)){
				map1.put("region","东区");
			}
			if (south.contains(province)){
				map1.put("region","南区");
			}
			if (west.contains(province)){
				map1.put("region","西区");
			}
			if (north.contains(province)){
				map1.put("region","北区");
			}
			//退换货类型处理
			Object typeActual=map1.get("typeActual");
			if (typeActual!=null){
				if (typeActual.equals(1)){
					map1.put("typeActual","退货退款");
				}
				if (typeActual.equals(2)){
					map1.put("typeActual","退货不退款");
				}
			}else{
				map1.put("typeActual","");
			}
			//处理状态
			Object handleStatus=map1.get("handleStatus");
			if (handleStatus!=null){
				int x=Integer.parseInt(handleStatus.toString());
				if (x==1){
					map1.put("handleStatus","审核中");
				}
				if (x==2){
					map1.put("handleStatus","进行中");
				}
				if (x==3){
					map1.put("handleStatus","受理完成");
				}
				if (x==4){
					map1.put("handleStatus","已完结");
				}
				if (x==5){
					map1.put("handleStatus","已驳回");
				}
				if (x==6){
					map1.put("handleStatus","已终止");
				}
				if (x==7){
					map1.put("handleStatus","线下已退款");
				}
				if (x==8){
					map1.put("handleStatus","等待确认终止");
				}
				if (x==9){
					map1.put("handleStatus","等待HP确认拒收");
				}
			}else{
				map1.put("handleStatus","");
			}
			//付款状态处理
			Object PaymentStatus=map1.get("PaymentStatus");
			if (PaymentStatus!=null){
				if (PaymentStatus.equals(1)){
					map1.put("PaymentStatus","已付款");
				}
				if (PaymentStatus.equals(2)){
					map1.put("PaymentStatus","待退款");
				}
				if (PaymentStatus.equals(3)){
					map1.put("PaymentStatus","已退款");
				}
				if (PaymentStatus.equals(4)){
					map1.put("PaymentStatus","线下已退款");
				}
				if (PaymentStatus.equals(5)){
					map1.put("PaymentStatus","无需退款");
				}
			}else{
				map1.put("PaymentStatus","");
			}
			//发票状态处理
			Object receiptStatus=map1.get("receiptStatus");
			if (receiptStatus!=null){
				if (receiptStatus.equals(1)){
					map1.put("receiptStatus","已开票");
				}
				if (receiptStatus.equals(2)){
					map1.put("receiptStatus","未开票");
				}
				if (receiptStatus.equals(3)){
					map1.put("receiptStatus","已召回");
				}
				if (receiptStatus.equals(4)){
					map1.put("receiptStatus","已冲票");
				}
				if (receiptStatus.equals(10)){
					map1.put("receiptStatus","待召回");
				}
			}else{
				map1.put("receiptStatus","");
			}
			//出入库状态处理
			Object storageStatus=map1.get("storageStatus");
			if (storageStatus!=null){
				if (storageStatus.equals(1)){
					map1.put("storageStatus","已出库");
				}
				if (storageStatus.equals(2)){
					map1.put("storageStatus","未出库");
				}
				if (storageStatus.equals(3)){
					map1.put("storageStatus","已召回");
				}
				if (storageStatus.equals(4)){
					map1.put("storageStatus","已入库");
				}
				if (storageStatus.equals(10)){
					map1.put("storageStatus","待召回");
				}
				if (storageStatus.equals(122)){
					map1.put("storageStatus","已入22库");
				}
				if (storageStatus.equals(121)){
					map1.put("storageStatus","已入21库");
				}
				if (storageStatus.equals(140)){
					map1.put("storageStatus","已入40库");
				}
				if (storageStatus.equals(141)){
					map1.put("storageStatus","已入41库");
				}
				if (storageStatus.equals(221)){
					map1.put("storageStatus","已入日日顺21库");
				}
				if (storageStatus.equals(21)){
					map1.put("storageStatus","已入金立库");
				}
				if (storageStatus.equals(100)){
					map1.put("storageStatus","菜鸟正品");
				}
				if (storageStatus.equals(104)){
					map1.put("storageStatus","菜鸟机损待鉴定");
				}
				if (storageStatus.equals(105)){
					map1.put("storageStatus","菜鸟箱损待鉴定");
				}
				if (storageStatus.equals(110)){
					map1.put("storageStatus","已入10库");
				}
			}else{
				map1.put("storageStatus","");
			}
			//订单来源处理
            Object source=map1.get("source");
			if (source!=null) {
				if (source.equals("TBSC")) {
					map1.put("source", "海尔官方淘宝旗舰店");
				}
				if (source.equals("TOPBOILER")) {
					map1.put("source", "海尔热水器专卖店");
				}
				if (source.equals("TONGSHUAI")) {
					map1.put("source", "淘宝网统帅日日顺乐家专卖店");
				}
				if (source.equals("TONGSHUAIFX")) {
					map1.put("source", "统帅品牌商");
				}
				if (source.equals("TOPXB")) {
					map1.put("source", "海尔新宝旗舰店");
				}
				if (source.equals("MOOKA")) {
					map1.put("source", "mooka模卡官方旗舰店");
				}
				if (source.equals("WASHER")) {
					map1.put("source", "海尔洗衣机旗舰店");
				}
				if (source.equals("FRIDGE")) {
					map1.put("source", "海尔冰冷旗舰店");
				}
				if (source.equals("AIR")) {
					map1.put("source", "海尔空调旗舰店");
				}
				if (source.equals("TBCT")) {
					map1.put("source", "村淘海尔商家");
				}
				if (source.equals("GQGYS")) {
					map1.put("source", "生态授权店");
				}
				if (source.equals("TMKSD")) {
					map1.put("source", "天猫卡萨帝旗舰店");
				}
				if (source.equals("TMTV")) {
					map1.put("source", "海尔电视旗舰店");
				}
				if (source.equals("TBCFDD")) {
					map1.put("source", "海尔厨房大电旗舰店");
				}
				if (source.equals("TBXCR")) {
					map1.put("source", "天猫小超人旗舰店");
				}
				if (source.equals("TOPSHJD")) {
					map1.put("source", "海尔生活电器专卖店");
				}
				if (source.equals("TOPDHSC")) {
					map1.put("source", "海尔生活家电旗舰店");
				}
				if (source.equals("GMZX")) {
					map1.put("source", "国美海尔官方旗舰店");
				}
				if (source.equals("GMZXTS")) {
					map1.put("source", "国美统帅官方旗舰店");
				}
				if (source.equals("SNYG")) {
					map1.put("source", "苏宁统帅官方旗舰店");
				}
				if (source.equals("SNHEGQ")) {
					map1.put("source", "苏宁海尔官方旗舰店");
				}
				if (source.equals("SNQDZX")) {
					map1.put("source", "苏宁渠道中心");
				}
				if (source.equals("DDW")) {
					map1.put("source", "当当");
				}
				if (source.equals("JDHEBXGQ")) {
					map1.put("source", "京东海尔集团冰箱官方旗舰店");
				}
				if (source.equals("JDHEGQ")) {
					map1.put("source", "京东海尔官方旗舰店");
				}
			}else{
				map1.put("source","");
			}
            //一次质检结果处理
			Object quality = map1.get("quality");
			if(quality != null) {
				if (quality.equals(1)) {
					map1.put("quality", "未开箱正品");
				}
				if (quality.equals(2)) {
					map1.put("quality", "已开箱正品");
				}
				if (quality.equals(3)) {
					map1.put("quality", "不良品");
				}
				if (quality.equals(5)) {
					map1.put("quality", "不良品换机");
				}
				if (quality.equals(6)) {
					map1.put("quality", "不良品退机");
				}
			}else{
				map1.put("quality","");
			}
			//处理检验结果
			Object checkResult = map1.get("checkResult");
			if(checkResult != null) {
				if (checkResult.equals(1)) {
					map1.put("checkResult", "符合");
				}
				if (checkResult.equals(2)) {
					map1.put("checkResult", "不符合");
				}

			}else{
				map1.put("checkResult","");
			}

		}
		//创建工作薄对象
		HSSFWorkbook wb = new HSSFWorkbook();
		//创建页
		HSSFSheet sheet_1 =  wb.createSheet("退换货报表");
		//创建标题行
		HSSFRow headRow = sheet_1.createRow(0);
		//创建标题行第一列
		HSSFCell head_cell_1 = headRow.createCell(0);
		//第一列内容
		head_cell_1.setCellValue("订单编号");
		//创建标题行第2列
		HSSFCell head_cell_2 = headRow.createCell(1);
		//第2列内容
		head_cell_2.setCellValue("网单编号");
		//创建标题行第一列
		HSSFCell head_cell_3 = headRow.createCell(2);
		//第3列内容
		head_cell_3.setCellValue("退换货单号");
		//创建标题行第4列
		HSSFCell head_cell_4 = headRow.createCell(3);
		//第4列内容
		head_cell_4.setCellValue("退换货类型");
		//创建标题行第5列
		HSSFCell head_cell_5 = headRow.createCell(4);
		//第5列内容
		head_cell_5.setCellValue("退换货数量");
		//创建标题行第6列
		HSSFCell head_cell_6 = headRow.createCell(5);
		//第6列内容
		head_cell_6.setCellValue("处理状态");
		//创建标题行第7列
		HSSFCell head_cell_7 = headRow.createCell(6);
		//第7列内容
		head_cell_7.setCellValue("提交时间");
		//创建标题行第8列
		HSSFCell head_cell_8 = headRow.createCell(7);
		//第8列内容
		head_cell_8.setCellValue("付款状态");
		//创建标题行第9列
		HSSFCell head_cell_9 = headRow.createCell(8);
		//第9列内容
		head_cell_9.setCellValue("发票状态");
		//创建标题行第10列
		HSSFCell head_cell_10 = headRow.createCell(9);
		//第10列内容
		head_cell_10.setCellValue("出入库状态");
		//创建标题行第11列
		HSSFCell head_cell_11 = headRow.createCell(10);
		//第11列内容
		head_cell_11.setCellValue("联系人姓名");
		//创建标题行第12列
		HSSFCell head_cell_12 = headRow.createCell(11);
		//第12列内容
		head_cell_12.setCellValue("联系人电话");
		//创建标题行第13列
		HSSFCell head_cell_13 = headRow.createCell(12);
		//第13列内容
		head_cell_13.setCellValue("订单地区分配");
		//创建标题行第14列
		HSSFCell head_cell_14 = headRow.createCell(13);
		//第14列内容
		head_cell_14.setCellValue("省份");
		//创建标题行第15列
		HSSFCell head_cell_15 = headRow.createCell(14);
		//第15列内容
		head_cell_15.setCellValue("商品类型");
		//创建标题行第16列
		HSSFCell head_cell_16 = headRow.createCell(15);
		//第16列内容
		head_cell_16.setCellValue("购买产品型号");
	/*	//创建标题行第17列
		HSSFCell head_cell_17 = headRow.createCell(16);
		//第17列内容
		head_cell_17.setCellValue("购买价格");
		//创建标题行第18列
		HSSFCell head_cell_18 = headRow.createCell(17);
		//第18列内容
		head_cell_18.setCellValue("实际支付金额");*/
		//创建标题行第17列
		HSSFCell head_cell_17 = headRow.createCell(16);
		//第17列内容
		head_cell_17.setCellValue("退款申请金额");
		//创建标题行第18列
		HSSFCell head_cell_18 = headRow.createCell(17);
		//第18列内容
		head_cell_18.setCellValue("订单来源");
/*		//创建标题行第21列
		HSSFCell head_cell_21 = headRow.createCell(20);
		//第21列内容
		head_cell_21.setCellValue("退款受理状态");*/
		//创建标题行第19列
		HSSFCell head_cell_19 = headRow.createCell(18);
		//第19列内容
		head_cell_19.setCellValue("退款原因");
		//创建标题行第20列
		HSSFCell head_cell_20 = headRow.createCell(19);
		//第20列内容
		head_cell_20.setCellValue("退款描述");
		//创建标题行第21列
		HSSFCell head_cell_21 = headRow.createCell(20);
		//第21列内容
		head_cell_21.setCellValue("一次质检推HP时间");
		//创建标题行第22列
		HSSFCell head_cell_22 = headRow.createCell(21);
		//第22列内容
		head_cell_22.setCellValue("一次质检工单号");
/*		//创建标题行第26列
		HSSFCell head_cell_26 = headRow.createCell(25);
		//第26列内容
		head_cell_26.setCellValue("一次质检工单生成时间");*/
		//创建标题行第23列
		HSSFCell head_cell_23 = headRow.createCell(22);
		//第23列内容
		head_cell_23.setCellValue("一次质检HP回传时间");
		//创建标题行第24列
		HSSFCell head_cell_24 = headRow.createCell(23);
		//第24列内容
		head_cell_24.setCellValue("一次质检网点");
		//创建标题行第25列
		HSSFCell head_cell_25 = headRow.createCell(24);
		//第25列内容
		head_cell_25.setCellValue("一次质检结果");
		//创建标题行第26列
		HSSFCell head_cell_26 = headRow.createCell(25);
		//第26列内容
		head_cell_26.setCellValue("一次检验结果");
		//创建标题行第27列
		HSSFCell head_cell_27 = headRow.createCell(26);
		//第27列内容
		head_cell_27.setCellValue("一次质检机编代码");
		//创建标题行第28列
		HSSFCell head_cell_28 = headRow.createCell(27);
		//第28列内容
		head_cell_28.setCellValue("支付方式");
		//创建标题行第29列
		HSSFCell head_cell_29 = headRow.createCell(28);
		//第29列内容
		head_cell_29.setCellValue("支付时间");
		//创建标题行第30列
		HSSFCell head_cell_30 = headRow.createCell(29);
		//第30列内容
		head_cell_30.setCellValue("交易流水号");
		//创建标题行第31列
		HSSFCell head_cell_31 = headRow.createCell(30);
		//第31列内容
		head_cell_31.setCellValue("用户签收时间");

		//为内容行添加数据和样式
		for (int i = 1; i <= export_ExcelData.size(); i++) {
			//拿取export_ExcelData坐标下的数据
			Map<String,Object> dataMap=export_ExcelData.get(i-1);
			//创建行
			HSSFRow contentRow = sheet_1.createRow(i);
			//创建每一个单元格
			HSSFCell content_cell_1 = contentRow.createCell(0);
			HSSFCell content_cell_2 = contentRow.createCell(1);
			HSSFCell content_cell_3 = contentRow.createCell(2);
			HSSFCell content_cell_4 = contentRow.createCell(3);
			HSSFCell content_cell_5 = contentRow.createCell(4);
			HSSFCell content_cell_6 = contentRow.createCell(5);
			HSSFCell content_cell_7 = contentRow.createCell(6);
			HSSFCell content_cell_8 = contentRow.createCell(7);
			HSSFCell content_cell_9 = contentRow.createCell(8);
			HSSFCell content_cell_10 = contentRow.createCell(9);
			HSSFCell content_cell_11 = contentRow.createCell(10);
			HSSFCell content_cell_12 = contentRow.createCell(11);
			HSSFCell content_cell_13 = contentRow.createCell(12);
			HSSFCell content_cell_14 = contentRow.createCell(13);
			HSSFCell content_cell_15 = contentRow.createCell(14);
			HSSFCell content_cell_16 = contentRow.createCell(15);
			HSSFCell content_cell_17 = contentRow.createCell(16);
			HSSFCell content_cell_18 = contentRow.createCell(17);
			HSSFCell content_cell_19 = contentRow.createCell(18);
			HSSFCell content_cell_20 = contentRow.createCell(19);
			HSSFCell content_cell_21 = contentRow.createCell(20);
			HSSFCell content_cell_22 = contentRow.createCell(21);
			HSSFCell content_cell_23 = contentRow.createCell(22);
			HSSFCell content_cell_24 = contentRow.createCell(23);
			HSSFCell content_cell_25 = contentRow.createCell(24);
			HSSFCell content_cell_26 = contentRow.createCell(25);
			HSSFCell content_cell_27 = contentRow.createCell(26);
			HSSFCell content_cell_28 = contentRow.createCell(27);
			HSSFCell content_cell_29 = contentRow.createCell(28);
			HSSFCell content_cell_30 = contentRow.createCell(29);
			HSSFCell content_cell_31 = contentRow.createCell(30);

/*
			HSSFCell content_cell_26 = contentRow.createCell(25);
			HSSFCell content_cell_27 = contentRow.createCell(26);
			HSSFCell content_cell_28 = contentRow.createCell(27);
			HSSFCell content_cell_29 = contentRow.createCell(28);
*/


			content_cell_1.setCellValue(dataMap.get("orderSn").toString());//订单编号
			content_cell_2.setCellValue(dataMap.get("cOrderSn").toString());//网单号
			content_cell_3.setCellValue(dataMap.get("repairSn").toString());//退货单号
			content_cell_4.setCellValue(dataMap.get("typeActual").toString());//退换货类型
			content_cell_5.setCellValue(dataMap.get("count").toString());//退换货数量
			content_cell_6.setCellValue(dataMap.get("handleStatus").toString());//处理状态
			content_cell_7.setCellValue(dataMap.get("addTime").toString());//提交时间
			content_cell_8.setCellValue(dataMap.get("PaymentStatus").toString());//付款状态
			content_cell_9.setCellValue(dataMap.get("receiptStatus").toString());//发票状态
			content_cell_10.setCellValue(dataMap.get("storageStatus").toString());//出入库状态
			content_cell_11.setCellValue(dataMap.get("contactName").toString());//联系人姓名
			content_cell_12.setCellValue(dataMap.get("contactMobile").toString());//联系人电话
			content_cell_13.setCellValue(dataMap.get("region").toString());//订单地区分配
			content_cell_14.setCellValue(dataMap.get("province").toString());//省份
			if(dataMap.get("typeName") != null) {
				content_cell_15.setCellValue(dataMap.get("typeName").toString());//商品类型
			}else{
				content_cell_15.setCellValue("");//商品类型

			}
			content_cell_16.setCellValue(dataMap.get("productName").toString());//购买产品型号
/*			content_cell_17.setCellValue(dataMap.get("province").toString());//购买价格
			content_cell_18.setCellValue(dataMap.get("province").toString());//实际支付金额*/
			content_cell_17.setCellValue(dataMap.get("offlineAmount").toString());//退款申请金额
			content_cell_18.setCellValue(dataMap.get("source").toString());//订单来源
//			content_cell_21.setCellValue(dataMap.get("province").toString());//退款受理状态
            if(dataMap.get("reason") != null) {
                content_cell_19.setCellValue(dataMap.get("reason").toString());//退款原因
            }else{
                content_cell_19.setCellValue("");//退款原因
            }
			content_cell_20.setCellValue(dataMap.get("description").toString());//退款描述
			content_cell_21.setCellValue(dataMap.get("hpFirstAddTime").toString());//一次质检推HP时间
            if(dataMap.get("woId") != null){
                content_cell_22.setCellValue(dataMap.get("woId").toString());//一次质检工单号
            }else{
                content_cell_22.setCellValue("");//一次质检工单号
            }

//			content_cell_26.setCellValue(dataMap.get("province").toString());//一次质检工单生成时间
			if(dataMap.get("hpTime") != null) {
				content_cell_23.setCellValue(dataMap.get("hpTime").toString());//一次质检HP回传时间
			}else{
				content_cell_23.setCellValue("");//一次质检HP回传时间
			}
            if(dataMap.get("netPointName") != null) {
                content_cell_24.setCellValue(dataMap.get("netPointName").toString());//一次质检网点
            }else{
                content_cell_24.setCellValue("");//一次质检网点
            }
			content_cell_25.setCellValue(dataMap.get("quality").toString());//一次质检结果
			content_cell_26.setCellValue(dataMap.get("checkResult").toString());//一次检验结果
			if(dataMap.get("machineNum") != null) {
				content_cell_27.setCellValue(dataMap.get("machineNum").toString());//一次质检机编代码
			}else{
				content_cell_27.setCellValue("");
			}
			content_cell_28.setCellValue(dataMap.get("paymentName").toString());//支付方式
			content_cell_29.setCellValue(dataMap.get("paymentTime").toString());//支付时间
			if(dataMap.get("tradeSn") != null) {
				content_cell_30.setCellValue(dataMap.get("tradeSn").toString());//交易流水号
			}else{
				content_cell_30.setCellValue("");//交易流水号

			}
			if(dataMap.get("userAcceptTime") != null) {
				content_cell_31.setCellValue(dataMap.get("userAcceptTime").toString());//用户签收时间
			}else{
				content_cell_31.setCellValue("");
			}




		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String str = sdf.format(date);
  		String fileName = "退换货报表" + str;
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		wb.write(os);
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		ExportExcelUtil.exportCommon(is, fileName, response);
		/*File file=new File("退换货报表.xls");
		OutputStream os = new FileOutputStream(file);
		wb.write(os);
		os.close();*/
		json.setMsg("导出报表成功");
		return json;
	}

	/**
	 * 接收不良品返回过来的信息   http接口
	 * @param
	 * @return
	 * @throws java.text.ParseException
	 * @throws IOException
	 */
	@RequestMapping(value="HPReturnUnhealthyImpl")
	@ResponseBody
	public String HPReturnUnhealthyImpl(HttpServletResponse response,HttpServletRequest request) throws java.text.ParseException, IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String jsonStr = null;
		StringBuilder result = new StringBuilder();
		try {
		while ((jsonStr = reader.readLine()) != null) {
		result.append(jsonStr);
		}
		} catch (IOException e) {
		e.printStackTrace();
		}
		reader.close();// 关闭输入流
		String obejecXml =result.toString();
		return operationAreaService.HPReturnUnhealthyImpl(obejecXml);
	}
	/**
	 * 接收HP拒收返回的信息   http接口
	 * @param
	 * @return
	 * @throws java.text.ParseException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="analysisXml")
	public String analysisXml(HttpServletRequest request)throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String jsonStr = null;
		String result;
		StringBuilder result1 = new StringBuilder();
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		try {
			while ((jsonStr = reader.readLine()) != null) {
				result1.append(jsonStr);
			}
		} catch (IOException e) {
			logger.info("读取报文失败");
			e.printStackTrace();
            return "<processResponse><receiveFlag>F</receiveFlag></processResponse>";
		}

		String XMLString =result1.toString();
		Document doc = null;
		reader.close();// 关闭输入流
		try{
			// 将字符串转为XML
			doc = DocumentHelper.parseText(XMLString);
			// 获取根节点
			Element rootElt = doc.getRootElement();
//			System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
			//判断根节点的名字是不是root
			if (!(rootElt.getName().equals("root"))){
				return "<processResponse><receiveFlag>F</receiveFlag></processResponse>";
			}
			//通过根元素得到一级子元素
			List<Element>firstChild=rootElt.elements();
			if (!(firstChild.get(0).getName().equals("orderJs"))||firstChild.size()!=1){
				return "<processResponse><receiveFlag>F</receiveFlag></processResponse>";
			}
			//通过一级子元素得到二级子元素
			List<Element> secondChild=firstChild.get(0).elements();
			if (!(secondChild.get(0).getName().equals("item"))||secondChild.size()!=1){
				return "<processResponse><receiveFlag>F</receiveFlag></processResponse>";
			}
			//取到item的元素
			Element itemElement=secondChild.get(0);
			//得到item的节点
			List<Element> itemChild=itemElement.elements();
			Map<String,Object> xmlMap=new HashMap();
			for (Element item:itemChild){
				//得到rowid的值(主键) Y
				if (item.getName().equals("rowid")){
					//得到指定节点的值
					String rowid=item.getStringValue();
					if (Ustring.isEmpty(rowid)){
						return "<processResponse><receiveFlag>F</receiveFlag></processResponse>";
					}
					xmlMap.put("rowid",rowid);
//					System.out.println(rowid);
				}
				//得到orderno的值(Tb单号) Y
				if (item.getName().equals("orderno")){
					//得到指定节点的值
					String orderno=item.getStringValue();
					if (Ustring.isEmpty(orderno)){
						return "<processResponse><receiveFlag>F</receiveFlag></processResponse>";
					}
					xmlMap.put("orderno",orderno);
//					System.out.println(orderno);
				}
				//得到sourceno的值(来源编号) N
				if (item.getName().equals("sourceno")){
					//得到指定节点的值
					String sourceno=item.getStringValue();
//					System.out.println(sourceno);
					xmlMap.put("sourceno",sourceno);
				}
				//得到prodtype的值(物料) Y
				if (item.getName().equals("prodtype")){
					//得到指定节点的值
					String prodtype=item.getStringValue();
					if (Ustring.isEmpty(prodtype)){
						return "物料不能为空";
					}
					xmlMap.put("prodtype",prodtype);
//					System.out.println(prodtype);
				}
				//得到remark的值(备注) N
				if (item.getName().equals("remark")){
					//得到指定节点的值
					String remark=item.getStringValue();
//					System.out.println(remark);
					xmlMap.put("remark",remark);
				}
				//得到netcode的值(网单86码) N
				if (item.getName().equals("netcode")){
					//得到指定节点的值
					String netcode=item.getStringValue();
//					System.out.println(netcode);
					xmlMap.put("netcode",netcode);
				}
				//得到status的值(标识) Y 默认为6
				if (item.getName().equals("status")){
					//得到指定节点的值
					String status=item.getStringValue();
					if (Ustring.isEmpty(status)){
						return "标识不能为空";
					}
					xmlMap.put("status",status);
//					System.out.println(status);
				}
				//得到types的值 N
				if (item.getName().equals("types")){
					//得到指定节点的值
					String types=item.getStringValue();
//					System.out.println(types);
					xmlMap.put("types",types);
				}
				//得到add10的值(备用字段)
				if (item.getName().equals("add10")){
					//得到指定节点的值
					String add10=item.getStringValue();
//					System.out.println(add10);
					xmlMap.put("add10",add10);
				}
				//得到add1的值(备用字段)
				if (item.getName().equals("add1")){
					//得到指定节点的值
					String add1=item.getStringValue();
//					System.out.println(add1);
					xmlMap.put("add1",add1);
				}
				//得到add2的值(lbx单号)
				if (item.getName().equals("add2")){
					//得到指定节点的值
					String add2=item.getStringValue();
//					System.out.println(add2);
					xmlMap.put("add2",add2);
				}
				//得到add3的值(备用字段)
				if (item.getName().equals("add3")){
					//得到指定节点的值
					String add3=item.getStringValue();
//					System.out.println(add3);
					xmlMap.put("add3",add3);
				}
			}
			List<String> list=operationAreaService.selectHPlogsRowid(xmlMap.get("rowid").toString());
			if (list.size()!=0){
				return "<processResponse><receiveFlag>F</receiveFlag></processResponse>";
			}
			xmlMap.put("receiveData",XMLString);
			//插入到拒收日志表
			operationAreaService.insertHPlogs(xmlMap);
			//插入到HP拒收表
			operationAreaService.insertWwwHpRecords(xmlMap);
			return "<processResponse><receiveFlag>S</receiveFlag></processResponse>";
		}catch (Exception e){
			e.printStackTrace();
			return "<processResponse><receiveFlag>F</receiveFlag></processResponse>";
		}

	}

    @ResponseBody
    @RequestMapping(value="checkhpreject")
	public String checkhpreject(){
        try {
            hpRejectionService.handleHPRejection();
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return "success";
    }

	@ResponseBody
	@RequestMapping(value="checkHPOrder")
	public List<String> checkHPOrder(HttpServletRequest request)throws Exception{
		String jsonStr = null;
		List<String> result = new ArrayList<String>();
		StringBuilder result1 = new StringBuilder();
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
        List<Map<String, Object>> mapList = operationAreaService.selectAllHPlogs();

        for (int i = 0; i < mapList.size(); i++){
            Map<String, Object> stringObjectMap = mapList.get(i);
            String receiveData = String.valueOf(stringObjectMap.get("receiveData"));
            String XMLString = receiveData;
            Document doc = null;
            try{
                // 将字符串转为XML
                doc = DocumentHelper.parseText(XMLString);
                // 获取根节点
                Element rootElt = doc.getRootElement();
//			System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
                //判断根节点的名字是不是root
                if (!(rootElt.getName().equals("root"))){
                    result.add("<processResponse><receiveFlag>F</receiveFlag></processResponse>");
                    continue;
                }
                //通过根元素得到一级子元素
                List<Element>firstChild=rootElt.elements();
                if (!(firstChild.get(0).getName().equals("orderJs"))||firstChild.size()!=1){
                    result.add("<processResponse><receiveFlag>F</receiveFlag></processResponse>");
                    continue;
                }
                //通过一级子元素得到二级子元素
                List<Element> secondChild=firstChild.get(0).elements();
                if (!(secondChild.get(0).getName().equals("item"))||secondChild.size()!=1){
                    result.add("<processResponse><receiveFlag>F</receiveFlag></processResponse>");
                    continue;
                }
                //取到item的元素
                Element itemElement=secondChild.get(0);
                //得到item的节点
                List<Element> itemChild=itemElement.elements();
                Map<String,Object> xmlMap=new HashMap();
                for (Element item:itemChild){
                    //得到rowid的值(主键) Y
                    if (item.getName().equals("rowid")){
                        //得到指定节点的值
                        String rowid=item.getStringValue();
                        if (Ustring.isEmpty(rowid)){
                            result.add("<processResponse><receiveFlag>F</receiveFlag></processResponse>");
                            continue;
                        }
                        xmlMap.put("rowid",rowid);
//					System.out.println(rowid);
                    }
                    //得到orderno的值(Tb单号) Y
                    if (item.getName().equals("orderno")){
                        //得到指定节点的值
                        String orderno=item.getStringValue();
                        if (Ustring.isEmpty(orderno)){
                            result.add("<processResponse><receiveFlag>F</receiveFlag></processResponse>");
                            continue;
                        }
                        xmlMap.put("orderno",orderno);
//					System.out.println(orderno);
                    }
                    //得到sourceno的值(来源编号) N
                    if (item.getName().equals("sourceno")){
                        //得到指定节点的值
                        String sourceno=item.getStringValue();
//					System.out.println(sourceno);
                        xmlMap.put("sourceno",sourceno);
                    }
                    //得到prodtype的值(物料) Y
                    if (item.getName().equals("prodtype")){
                        //得到指定节点的值
                        String prodtype=item.getStringValue();
                        if (Ustring.isEmpty(prodtype)){
                            result.add("物料不能为空");
                            continue;
                        }
                        xmlMap.put("prodtype",prodtype);
//					System.out.println(prodtype);
                    }
                    //得到remark的值(备注) N
                    if (item.getName().equals("remark")){
                        //得到指定节点的值
                        String remark=item.getStringValue();
//					System.out.println(remark);
                        xmlMap.put("remark",remark);
                    }
                    //得到netcode的值(网单86码) N
                    if (item.getName().equals("netcode")){
                        //得到指定节点的值
                        String netcode=item.getStringValue();
//					System.out.println(netcode);
                        xmlMap.put("netcode",netcode);
                    }
                    //得到status的值(标识) Y 默认为6
                    if (item.getName().equals("status")){
                        //得到指定节点的值
                        String status=item.getStringValue();
                        if (Ustring.isEmpty(status)){
                            result.add("标识不能为空");
                            continue;

                        }
                        xmlMap.put("status",status);
//					System.out.println(status);
                    }
                    //得到types的值 N
                    if (item.getName().equals("types")){
                        //得到指定节点的值
                        String types=item.getStringValue();
//					System.out.println(types);
                        xmlMap.put("types",types);
                    }
                    //得到add10的值(备用字段)
                    if (item.getName().equals("add10")){
                        //得到指定节点的值
                        String add10=item.getStringValue();
//					System.out.println(add10);
                        xmlMap.put("add10",add10);
                    }
                    //得到add1的值(备用字段)
                    if (item.getName().equals("add1")){
                        //得到指定节点的值
                        String add1=item.getStringValue();
//					System.out.println(add1);
                        xmlMap.put("add1",add1);
                    }
                    //得到add2的值(lbx单号)
                    if (item.getName().equals("add2")){
                        //得到指定节点的值
                        String add2=item.getStringValue();
//					System.out.println(add2);
                        xmlMap.put("add2",add2);
                    }
                    //得到add3的值(备用字段)
                    if (item.getName().equals("add3")){
                        //得到指定节点的值
                        String add3=item.getStringValue();
//					System.out.println(add3);
                        xmlMap.put("add3",add3);
                    }
                }

                xmlMap.put("receiveData",XMLString);

                //插入到HP拒收表
                operationAreaService.insertWwwHpRecords(xmlMap);
                result.add("<processResponse><receiveFlag>S</receiveFlag></processResponse>");
            }catch (Exception e){
                e.printStackTrace();
                result.add("<processResponse><receiveFlag>F</receiveFlag></processResponse>");
            }

        }

        return result;

	}


	//关闭网单
	@ResponseBody
	@RequestMapping(value="closeOrder")
	public JSONObject closeOrder(HttpServletRequest request,HttpServletResponse response,String cOrderSn,String mark){
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		Map<String,Object> logMap=new HashMap<>();
		JSONObject jsonObject=new JSONObject();
		logMap=operationAreaService.selectData(cOrderSn);
		logMap.put("operator",userName);
		logMap.put("changeLog","关闭网单,关闭理由:"+mark);
		/*
		* 2018-07-26  添加shippingmode  为B2C  vom_shipping_status status 为TD的不允许关闭订单
		* */
		if(logMap.containsKey("shippingMode") && logMap.get("shippingMode").toString().equals("B2C")){
			int count = operationAreaService.queryCountByOrderNo(cOrderSn);
			if(count>0){
				jsonObject.put("msg","订单关闭失败，已经下发顺丰，请线下联系进行拦截。");
				logMap.put("remark","订单关闭失败，已经下发顺丰。");
				logMap.put("logTime",new Date().getTime()/1000);
				operationAreaService.insertLog(logMap);
				return jsonObject;
			}

		}
		//查询出出库凭证号
		jsonObject=queryOutPing(cOrderSn);
		if (Integer.parseInt(jsonObject.get("flag").toString())==1){
			String outping=jsonObject.get("outping").toString();
			jsonObject.put("msg","网单号:"+cOrderSn+"已经出库,出库凭证号为:"+outping+",走正常退换货流程");
			logger.info("网单号:"+cOrderSn+"已经出库,出库凭证号为:"+outping+",走正常退换货流程");
			return jsonObject;
		}
		//没有出库凭证号,查询有没有开提单号
		Map<String,Object> map=operationAreaService.selectlessOrderSn(cOrderSn);
		//存在开提单
		if (!StringUtil.isEmpty(map.get("lessOrderSn").toString())) {
			//通知VOM发起取消下单
			JSONObject object=cancelOpenBill(cOrderSn);
			String flag1=object.get("flag")+"";
			Boolean isOpenBill1=false;
			if (flag1.equals("true")){
				isOpenBill1=true;
			}
			if (flag1.equals("false")){
				isOpenBill1=false;
			}
			String msg=object.get("msg")+"";
			//通知VOM取消开提单成功
			if (isOpenBill1){
				logger.info("网单号:"+cOrderSn+"通知VOM取消提单成功");
				//通知VOM取消派工
				JSONObject jsonObject1=checkWorkers(cOrderSn);
				if (jsonObject1.get("flag").toString().equals("F")||jsonObject1.get("msg").toString().equals("单号已存在")){
					jsonObject.put("msg","取消提单成功,取消派工失败,原因:"+jsonObject1.get("msg"));
					logMap.put("remark","取消提单成功,取消派工失败,未释放库存,关闭网单失败");
					logMap.put("logTime",new Date().getTime()/1000);
					operationAreaService.insertLog(logMap);
					return jsonObject;
				}
				//释放库存,修改网单和订单状态
				jsonObject=ReleaseStock(cOrderSn);
				logMap.put("remark","取消提单成功,取消派工成功,释放库存成功,"+jsonObject.get("msg"));
				logMap.put("logTime",new Date().getTime()/1000);
				operationAreaService.insertLog(logMap);
				return jsonObject;
			}
			if (isOpenBill1==false){
				jsonObject.put("msg","取消下单失败,原因:"+msg);
				logger.info("取消下单失败,原因:"+msg);
				logMap.put("remark","取消下单失败,原因:"+msg);
				logMap.put("logTime",new Date().getTime()/1000);
				operationAreaService.insertLog(logMap);
				return jsonObject;
			}
		}
		//没有开提单
		if (StringUtil.isEmpty(map.get("lessOrderSn").toString())){
			//查询LesQueues下提单队列里面有没有带开提单的数据
			Map<String,Object> isStopMap=operationAreaService.queryisStop(Integer.parseInt(map.get("id").toString()));
			if (isStopMap.size()!=0){
				//开提单队列待执行的,这时要把待执行的数据isStop=0改为=3
				if(Integer.parseInt(isStopMap.get("isStop").toString())==0){
					operationAreaService.updateLesQueuesIsStop(Integer.parseInt(isStopMap.get("id").toString()));
					//取消派工
					JSONObject jsonObject1=checkWorkers(cOrderSn);
					if (jsonObject1.get("flag").toString().equals("F")||jsonObject1.get("msg").toString().equals("单号已存在")){
						jsonObject.put("msg","取消派工失败,原因:"+jsonObject1.get("msg"));
						logMap.put("remark","取消派工失败,原因:"+jsonObject1.get("msg"));
						logMap.put("logTime",new Date().getTime()/1000);
						operationAreaService.insertLog(logMap);
						return jsonObject;
					}
					jsonObject=ReleaseStock(cOrderSn);
					logMap.put("remark","取消派工成功,释放库存成功"+jsonObject.get("msg"));
					logMap.put("logTime",new Date().getTime()/1000);
					operationAreaService.insertLog(logMap);
					return jsonObject;
				}
				if(Integer.parseInt(isStopMap.get("isStop").toString())==1){
					String flag1=cancelOpenBill(cOrderSn).get("flag")+"";
					Boolean isOpenBill1=false;
					if (flag1.equals("true")){
						isOpenBill1=true;
					}
					if (flag1.equals("false")){
						isOpenBill1=false;
					}
					if (isOpenBill1){
						JSONObject jsonObject1=checkWorkers(cOrderSn);
						String flag2=jsonObject1.get("flag")+"";
						Boolean isOpenBill2=false;
						if (flag2.equals("true")){
							isOpenBill2=true;
						}
						if (flag2.equals("false")){
							isOpenBill2=false;
						}
						if (isOpenBill2){
							jsonObject=ReleaseStock(cOrderSn);
							logMap.put("remark","取消派工成功,"+jsonObject.get("msg")+"");
							logMap.put("logTime",new Date().getTime()/1000);
							operationAreaService.insertLog(logMap);
							return jsonObject;
						}
						if (isOpenBill2==false){
							jsonObject.put("msg","取消派工失败");
							logMap.put("remark","取消派工失败");
							logMap.put("logTime",new Date().getTime()/1000);
							operationAreaService.insertLog(logMap);
							return jsonObject;
						}
					}
					if (isOpenBill1==false){
						jsonObject.put("msg","取消下单失败");
						logMap.put("remark","取消下单失败");
						logMap.put("logTime",new Date().getTime()/1000);
						operationAreaService.insertLog(logMap);
						return jsonObject;
					}
				}
			}
			if (isStopMap.size()==0){
				//取消派工
				JSONObject jsonObject1=checkWorkers(cOrderSn);
				if (jsonObject1.get("flag").toString().equals("F")){
					jsonObject.put("msg","取消派工失败,原因:"+jsonObject1.get("msg"));
					logMap.put("remark","取消派工失败,原因:"+jsonObject1.get("msg"));
					logMap.put("logTime",new Date().getTime()/1000);
					operationAreaService.insertLog(logMap);
					return jsonObject;
				}
				//释放库存,关闭网单和订单
				jsonObject=ReleaseStock(cOrderSn);
				logMap.put("remark","取消派工成功,释放库存成功"+jsonObject.get("msg")+"");
				logMap.put("logTime",new Date().getTime()/1000);
				operationAreaService.insertLog(logMap);
				return jsonObject;
			}
		}
		return jsonObject;
	}

	 /**
     * 关闭退货单更改退货单状态
     * @param id
     * @param handleRemark
     * @return
     */
	@ResponseBody
	@RequestMapping(value="RepairsTermination")
	public Json RepairsTermination(String id,String handleRemark,HttpServletRequest request){
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		Json json = new Json();
		return operationAreaService.RepairsTermination(id, handleRemark,userName);
	}
	/**
	 * 终止逆向单
	 * @param id
	 * @param handleRemark
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="Terminatereverse")
	public Json Terminatereverse(String id,String handleRemark,String handleStatus,String terminationReason,HttpServletRequest request,String pd){
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		Json json = new Json();
		return operationAreaService.Rminatereverse(id, handleRemark,terminationReason,userName,handleStatus,pd);
	}
	/**
	 * 售后操作 22转41库
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="StockTransfer")
	public Json StockTransfer(String id,String handleRemark,HttpServletRequest request){
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		Json json =new Json();
		return operationAreaService.StockTransfer(id, handleRemark,userName);
	}

	/**
	 *接受HP返回过来的鉴定信息 http接口
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 * @throws DocumentException
	 */
	@RequestMapping(value="ReturnInformation",method=RequestMethod.POST, consumes="text/xml")
	@ResponseBody
	public String  ReturnInformation(HttpServletResponse response,HttpServletRequest request){
        StringBuffer wholeStr = new StringBuffer("");
		String returnXML = "";
		try {
            BufferedReader br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                wholeStr.append(str);
            }

			returnXML = operationAreaService.insertHPrecords(wholeStr.toString());
		} catch (Exception e) {
			logger.error("vomstatus/insert.html接口读取数据异常", e);
		}
	    return returnXML;
	}

	private String getSourceName(String source){
		if(source == null){
			return "";
		}
		String sourceName = "";
		if("TBSC".equals(source)){
			sourceName="海尔官方淘宝旗舰店";
		}
		if("TOPBOILER".equals(source)){
			sourceName="海尔热水器专卖店";
		}
		if("TONGSHUAI".equals(source)){
			sourceName="淘宝网统帅日日顺乐家专卖店";
		}
		if("TONGSHUAIFX".equals(source)){
			sourceName="统帅品牌商";
		}
		if("TOPXB".equals(source)){
			sourceName="海尔新宝旗舰店";
		}
		if("MOOKA".equals(source)){
			sourceName="mooka模卡官方旗舰店";
		}
		if("WASHER".equals(source)){
			sourceName="海尔洗衣机旗舰店";
		}
		if("FRIDGE".equals(source)){
			sourceName="海尔冰冷旗舰店";
		}
		if("AIR".equals(source)){
			sourceName="海尔空调旗舰店";
		}
		if("TBCT".equals(source)){
			sourceName="村淘海尔商家";
		}
		if("GQGYS".equals(source)){
			sourceName="生态授权店";
		}
		if("TMKSD".equals(source)){
			sourceName="天猫卡萨帝旗舰店";
		}
		if("TMTV".equals(source)){
			sourceName="海尔电视旗舰店";
		}
		if("TBCFDD".equals(source)){
			sourceName="海尔厨房大电旗舰店";
		}
		if("TBXCR".equals(source)){
			sourceName="天猫小超人旗舰店";
		}
		if("TOPSHJD".equals(source)){
			sourceName="海尔生活电器专卖店";
		}
		if("TOPDHSC".equals(source)){
			sourceName="海尔生活家电旗舰店";
		}
		if("GMZX".equals(source)){
			sourceName="国美海尔官方旗舰店";
		}
		if("GMZXTS".equals(source)){
			sourceName="国美统帅官方旗舰店";
		}
		if("SNYG".equals(source)){
			sourceName="苏宁统帅官方旗舰店";
		}
		if("SNHEGQ".equals(source)){
			sourceName="苏宁海尔官方旗舰店";
		}
		if("SNQDZX".equals(source)){
			sourceName="苏宁渠道中心";
		}
		if("DDW".equals(source)){
			sourceName="当当";
		}
		if("JDHEGQ".equals(source)){
			sourceName="京东海尔官方旗舰店";
		}
		if("JDHEBXGQ".equals(source)){
			sourceName="京东海尔集团冰箱官方旗舰店";
		}
		return sourceName;
	}
	//释放库存,取消网单和订单
	public JSONObject ReleaseStock(String cOrderSn){
		JSONObject jsonObject=new JSONObject();
		//释放库存
		Map<String,Object> LockedNumberMap=operationAreaService.selecctLockedNumber(cOrderSn);
		//如果未占有库存
		if(StringUtil.isEmpty(LockedNumberMap.get("lockedNumber").toString())){
			logger.info("网单号:"+cOrderSn+"没用锁定库存,不进行释放");
			//关闭网单和更新曾经释放的数量
			operationAreaService.updateOPStatus(0,cOrderSn);
			logger.info("关闭网单成功");
			//查询该订单是否只有一个网单(查询订单ID)
			List<Map<String,Object>> countList=operationAreaService.selectOPCount(Integer.parseInt(LockedNumberMap.get("orderId").toString()));
			if (countList.size()==0){
				logger.info("数据出错请联系管理员");
				jsonObject.put("msg","数据出错请联系管理员");
				return jsonObject;
			}
			int count=0;
			for (Map map1:countList){
				if (Integer.parseInt(map1.get("status").toString())!=110){
					count++;
				}
			}
			//PS:该订单下的所有网单已经关闭,取消订单
			if (count==0){
				logger.info("该订单下的所有网单已经关闭,执行更改订单状态");
				operationAreaService.updataOrderStatus(Integer.parseInt(LockedNumberMap.get("orderId").toString()));
				logger.info("关闭订单成功");
			}
			jsonObject.put("msg","关闭订单和网单成功");
			return jsonObject;
		}
		ServiceResult<Boolean> result=stockCenterHopStockService.releaseFrozenStockQty(LockedNumberMap.get("sku").toString(),
				Integer.parseInt(LockedNumberMap.get("lockedNumber").toString()),cOrderSn,RELEASE_BY_ZBCC	);
			//关闭网单和更新曾经释放的数量
			operationAreaService.updateOPStatus(Integer.parseInt(LockedNumberMap.get("lockedNumber").toString()),cOrderSn);
			logger.info("关闭网单和更新曾经释放的数量成功");
			//查询该订单是否只有一个网单(查询订单ID)
			List<Map<String,Object>> countList=operationAreaService.selectOPCount(Integer.parseInt(LockedNumberMap.get("orderId").toString()));
			if (countList.size()==0){
				logger.info("数据出错请联系管理员");
				jsonObject.put("msg","数据出错请联系管理员");
				return jsonObject;
			}
			int count=0;
			for (Map map1:countList){
				if (Integer.parseInt(map1.get("status").toString())!=110){
					count++;
				}
			}
			//PS:该订单下的所有网单已经关闭,取消订单
			if (count==0){
				logger.info("该订单下的所有网单已经关闭,执行更改订单状态");
				operationAreaService.updataOrderStatus(Integer.parseInt(LockedNumberMap.get("orderId").toString()));
				logger.info("关闭订单成功");
			}
			if (count==0){
				jsonObject.put("msg","关闭订单和网单成功");
			}
			if (count!=0){
				jsonObject.put("msg","关闭网单成功");
			}
			return jsonObject;
	}
	//查询出出库凭证号
	public JSONObject queryOutPing(String cOrderSn){
		JSONObject jsonObject=new JSONObject();
		String outping=operationAreaService.selectOutping(cOrderSn);
		if (StringUtil.isEmpty(outping)){
			jsonObject.put("flag",0);
			return jsonObject;
		}
		jsonObject.put("flag",1);
		jsonObject.put("outping",outping);
		return jsonObject;
	}
	//取消开提单
	public JSONObject cancelOpenBill(String cOrderSn){
		VOMCancelOrderRequire vomCancelOrderRequire = new VOMCancelOrderRequire();
		vomCancelOrderRequire.setOrderNo(cOrderSn);
		vomCancelOrderRequire.setCancelType("1");
		ServiceResult<VOMOrderResponse> serviceResult = vomOrderService.cancelVomOrderInfo(vomCancelOrderRequire);
		JSONObject jsonObject =new JSONObject();
		if (serviceResult.getResult().getFlag().equals("T")){
			jsonObject.put("flag",true);
			return jsonObject;
		}
		jsonObject.put("flag",false);
		jsonObject.put("msg",serviceResult.getResult().getMsg());
		return jsonObject;
	}
	//取消派工
	public JSONObject checkWorkers(String cOrderSn){
		JSONObject jsonObject=new JSONObject();
		Map<String,Object> map1=operationAreaService.selectlessOrderSn(cOrderSn);
		//取消派工
		 jsonObject=operationAreaService.cancelDispatchedWorkers(cOrderSn);
		//将HPQueues的issuccess状态改为3
		operationAreaService.updateHPQueuessuccess(Integer.parseInt(map1.get("id").toString()));
		return jsonObject;
	}
	//取消22入库单
	@RequestMapping(value="cancelInStock")
	@ResponseBody
	public Json cancelInStock(String repairId,String operate,String storageType,HttpServletRequest request){
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		Json json = new Json();
		if("出库".equals(operate)) {
			operate="1";
		}else if("入库".equals(operate)){
			operate="2";
		}
		List<OrderRepairLESRecords> orderRepairLESRecords =operationAreaService.queryRecordSn(operate, storageType, repairId);
		if(orderRepairLESRecords.size()<0) {
			json.setMsg("取消失败");
            json.setSuccess(false);
			return json;
		}
        OrderRepairLESRecords lesRecords = orderRepairLESRecords.get(0);
        String msg = "取消" + lesRecords.getStorageType() + (operate.equals("1") ? "出库" : "入库");

		if (lesRecords.getSuccess() != null && lesRecords.getSuccess().intValue() == 0){
            json.setMsg("已经" + msg + ",请勿重复操作!");
            json.setSuccess(false);
            return json;
        }

		VOMCancelOrderRequire vomCancelOrderRequire = new VOMCancelOrderRequire();
		vomCancelOrderRequire.setOrderNo(lesRecords.getRecordSn());
		vomCancelOrderRequire.setCancelType("1");
		vomCancelOrderRequire.setCancelExplain(msg);
		ServiceResult<VOMOrderResponse>serviceResult = vomOrderService.cancelVomOrderInfo(vomCancelOrderRequire);
		if (serviceResult.getResult().getMsg().equals("成功")){
		    //更新取消入库标识
            operationAreaService.updateRepairLesRecordcnSuccess(lesRecords.getId().toString(), 0);
			//添加操作日志
			operationAreaService.ProcessLog(Integer.parseInt(repairId),userName,"操作", msg);

            List<OrderRepairLESRecords> orderRepairLESRecordsList = operationAreaService.queryLesreCodrdsId(repairId);

            boolean flag = true;
            for (OrderRepairLESRecords lESRecords : orderRepairLESRecordsList){
                Integer success = lESRecords.getSuccess();
                if (success == null || success != 0){
                    flag = false;
                    break;
                }

            }
            if (flag){
                operationAreaService.RepairsTermination(repairId, "取消入库,关闭逆向单",userName);
            }

				json.setMsg(msg + "成功！");
			json.setSuccess(true);
			return json;

		}else{
			json.setMsg("取消失败:"+serviceResult.getResult().getMsg());
            json.setSuccess(true);
			return json;
		}
	}

    @RequestMapping(value="cancelLesOrderONline")
    @ResponseBody
	public Json cancelLesOrderOnline(String repairId,String operate,String storageType){
        Json json = new Json();
        if("出库".equals(operate)) {
            operate="1";
        }else if("入库".equals(operate)){
            operate="2";
        }
        List<OrderRepairLESRecords> orderRepairLESRecords =operationAreaService.queryRecordSn(operate, storageType, repairId);
        if(orderRepairLESRecords.size()<0) {
            json.setMsg("取消失败");
            return json;
        }
        VOMCancelOrderRequire vomCancelOrderRequire = new VOMCancelOrderRequire();
        vomCancelOrderRequire.setOrderNo(orderRepairLESRecords.get(0).getRecordSn());
        vomCancelOrderRequire.setCancelType("1");
        vomCancelOrderRequire.setCancelExplain("取消22入库单");
        ServiceResult<VOMOrderResponse>serviceResult = vomOrderService.cancelVomOrderInfo(vomCancelOrderRequire);
        if (serviceResult.getResult().getMsg().equals("成功")){
            json.setMsg("取消成功！");
            json.setSuccess(true);
            return json;

        }else{
            json.setMsg("取消失败:"+serviceResult.getResult().getMsg());
            return json;
        }

    }

    @RequestMapping(value="cancelLesOrderAndSendONline")
    @ResponseBody
    public Json cancelLesOrderOnlineAndSend(String repairId,String operate,String storageType){
        Json json = new Json();
        if("出库".equals(operate)) {
            operate="1";
        }else if("入库".equals(operate)){
            operate="2";
        }
        List<OrderRepairLESRecords> orderRepairLESRecords =operationAreaService.queryRecordSn(operate, storageType, repairId);
        if(orderRepairLESRecords.size()<0) {
            json.setMsg("取消失败");
            return json;
        }
        VOMCancelOrderRequire vomCancelOrderRequire = new VOMCancelOrderRequire();
        String recordSn = orderRepairLESRecords.get(0).getRecordSn();
        vomCancelOrderRequire.setOrderNo(recordSn);
        vomCancelOrderRequire.setCancelType("1");
        vomCancelOrderRequire.setCancelExplain("取消22入库单");
        ServiceResult<VOMOrderResponse>serviceResult = vomOrderService.cancelVomOrderInfo(vomCancelOrderRequire);
        if (serviceResult.getResult().getMsg().equals("成功")){

            String recordx = recordSn.substring(recordSn.length() - 3);
            Integer modifyResult = null;
            if (recordx.indexOf("CX") != -1){
                String cxNum = recordx.substring(recordx.indexOf("CX") + 2);

                recordSn = recordSn.substring(0, recordSn.indexOf("CX") + 2) + (Integer.valueOf(cxNum) + 2);

            }else{
                recordSn = recordSn + "2";

            }
            modifyResult = vomPropellingService.modifyLesOrder(orderRepairLESRecords.get(0).getId().toString(), recordSn);

            if (modifyResult != null && modifyResult > 0){
                vomPropellingService.callVOMProprllingONline(repairId);

            }else{
                json.setMsg("更新les失败");
                return json;
            }

            json.setMsg("取消并发送成功！");
            json.setSuccess(true);
            return json;

        }else{
            json.setMsg("取消失败:"+serviceResult.getResult().getMsg());
            return json;
        }

    }

    @RequestMapping(value="sendLesOrderOnline")
    @ResponseBody
    public String sendLesOrderOnline(String id){

        vomPropellingService.callVOMProprllingONline(id);

        return "success";
    }


	//根据tb单号查询网单表
	/*public List<Map<String,Object>> queryOrderProductByTB(String tbSn){
		List<Map<String,Object>> map=operationAreaService.queryOrderProductByTB(tbSn);
		return map;
	}*/

    @RequestMapping(value="putVomOrder")
    @ResponseBody
    public Json putVomOrder(String id){
        Json result = operationAreaService.establishTenLibraryOnLine(id, "");
        return result;
    }

	//生成退货单号拼装信息
	public JSONObject generateTHSn(Map<String,Object> map,String userName){
		JSONObject jsonObject=new JSONObject();
		String cOrderSn=map.get("cOrderSn").toString();
		Map<String,Object>  maps=shopOperationAreaService.selectPhoneAndName(cOrderSn);
		OrderRepairsVo orderRepairsVo=new OrderRepairsVo();
		orderRepairsVo.setcOrderSnId(cOrderSn);
		orderRepairsVo.setReason("其他");
		orderRepairsVo.setDescription("拒收商品");
		orderRepairsVo.setContactMobile(maps.get("mobile").toString());
		orderRepairsVo.setContactName((maps.get("consignee").toString()));
		orderRepairsVo.setType("1");
		Json json=operationAreaService.SaveRepairs(orderRepairsVo,userName);
		if (json.getSuccess()){
			String x=json.getObj().toString();
			//退货表的主键
			jsonObject.put("th_id",x);
			List<OrderRepairsVo> voList=new ArrayList<>();
			OrderRepairsVo orderRepairsVo1=new OrderRepairsVo();
			orderRepairsVo1.setId(Integer.parseInt(x));
			orderRepairsVo1.setMenuflag("JS");
			voList.add(orderRepairsVo1);
			return jsonObject;
		}
		jsonObject.put("th_id","");
		return jsonObject;
	}
	//发起鉴定请求
	public Json ModifyPushHPDate(String th_id)throws Exception{
		List<OrderRepairsVo> voList=new ArrayList<>();
		OrderRepairsVo vo=new OrderRepairsVo();
		vo.setId(Integer.parseInt(th_id));
		vo.setMenuflag("JS");
		voList.add(vo);
		Json json=operationAreaService.ModifyPushHP(voList,"定时任务");
		return json;
	}
	//添加退换货日志表
	public void addOrderRepairLogs(String userName,Integer orderRepairId,String operate,String remark){
		long addTime=new Date().getTime()/1000;
		Map<String,Object> map=new HashMap<>();
		map.put("userName",userName);
		map.put("addTime",addTime);
		map.put("orderRepairId",orderRepairId);
		map.put("remark",remark);
		map.put("operate",operate);
		operationAreaService.insertOrderRepairLog(map);
	}
	public final static boolean isNumeric(String s) {
		if (s != null && !"".equals(s.trim()))
			return s.matches("^[0-9]*$");
		else
			return false;
	}
	//创建入10单子  22转10 （无箱可换）
	@RequestMapping(value="establishTenLibrary")
	@ResponseBody
	public Json establishTenLibrary(String id,HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		return operationAreaService.establishTenLibrary(id,userName);
	}

    @RequestMapping(value="b2cestablishTenLibrary")
    @ResponseBody
    public Json b2cestablishTenLibrary(String id,HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userName = Ustring.getString(session.getAttribute("userName"));
        return operationAreaService.b2cestablishTenLibrary(id,userName);
    }
	
	/*
	* 修改来源订单号
	* @author gengdi
	* */
	@ResponseBody
	@RequestMapping("updateSourceOrderNumber")
	public Json updateSourceOrderNumber(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		String inputSourceOrderNumber = request.getParameter("inputSourceOrderNumber");
		String sourceOrderNumber = request.getParameter("sourceOrderNumber");
		String orderNumber = request.getParameter("orderNumber");
		Integer id = Integer.valueOf(request.getParameter("orderId"));
		
		ServiceResult<Boolean> updateSourceOrderNumber = orderService.updateSourceOrderNumber(inputSourceOrderNumber,sourceOrderNumber,orderNumber,userName,id);
		boolean success = updateSourceOrderNumber.getSuccess();
		String message = updateSourceOrderNumber.getMessage();
		Json json = new Json();
		json.setSuccess(success);
		json.setMsg(message);
		return json;
	}
	
	/*
	* 修改支付状态
	* @author gengdi
	* */
	@ResponseBody
	@RequestMapping("updatePayState")
	public Json updatePayState(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		String spanState = request.getParameter("spanState");
		String selectState = request.getParameter("selectState");
		String orderNumber = request.getParameter("orderNumber");
		Integer id = Integer.valueOf(request.getParameter("orderId"));
		
		ServiceResult<Boolean> updateSourceOrderNumber = orderService.updatePayState(spanState,selectState,orderNumber,userName,id);
		boolean success = updateSourceOrderNumber.getSuccess();
		String message = updateSourceOrderNumber.getMessage();
		Json json = new Json();
		json.setSuccess(success);
		json.setMsg(message);
		return json;
	}
	
	/*
	* 修改备注信息
	* @author gengdi
	* */
	@ResponseBody
	@RequestMapping("updateNotes")
	public Json updateNotes(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		String notes = request.getParameter("notes");
		String textNotes = request.getParameter("textNotes");
		String orderNumber = request.getParameter("orderNumber");
		Integer id = Integer.valueOf(request.getParameter("orderId"));
		
		ServiceResult<Boolean> updateSourceOrderNumber = orderService.updateNotes(notes,textNotes,orderNumber,userName,id);
		boolean success = updateSourceOrderNumber.getSuccess();
		String message = updateSourceOrderNumber.getMessage();
		Json json = new Json();
		json.setSuccess(success);
		json.setMsg(message);
		return json;
	}
	
	/*
	* 修改发票邮寄地址
	* @author gengdi
	* */
	@ResponseBody
	@RequestMapping("updateInvoiceAddress")
	public Json updateInvoiceAddress(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		String orderNumber = request.getParameter("orderNumber");
		Integer id = Integer.valueOf(request.getParameter("orderId"));
		String addressee = request.getParameter("addressee");
		String invoiceZipCode = request.getParameter("invoice_zip_code");
		String invoiceNumber = request.getParameter("invoice_number");
		String province = request.getParameter("province");
		String citys = request.getParameter("citys");
		String county = request.getParameter("county");
		String newAddress = request.getParameter("new_address");
		
		Orders orders = new Orders();
		orders.setReceiptConsignee(addressee);
		orders.setReceiptZipcode(invoiceZipCode);
		orders.setReceiptMobile(invoiceNumber);
		orders.setId(id);
		orders.setOrderSn(orderNumber);
		
		ServiceResult<Boolean> updateSourceOrderNumber = orderService.updateInvoiceAddress(userName,province,citys,county,newAddress,orders);
		boolean success = updateSourceOrderNumber.getSuccess();
		String message = updateSourceOrderNumber.getMessage();
		Json json = new Json();
		json.setSuccess(success);
		json.setMsg(message);
		return json;
	}
	
	/*
	* 修改发票状态
	* @author gengdi
	* */
	@ResponseBody
	@RequestMapping("updateInvoiceState")
	public Json updateInvoiceState(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		String orderNumber = request.getParameter("orderNumber");
		Integer id = Integer.valueOf(request.getParameter("orderId"));
		String isLockI = request.getParameter("is_lock_i");
		ServiceResult<Boolean> updateSourceOrderNumber = orderService.updateInvoiceState(userName,orderNumber,id,isLockI);
		boolean success = updateSourceOrderNumber.getSuccess();
		String message = updateSourceOrderNumber.getMessage();
		Json json = new Json();
		json.setSuccess(success);
		json.setMsg(message);
		return json;
	}
	
	/*
	* 修改发票信息
	* @author gengdi
	* */
	@ResponseBody
	@RequestMapping("updateInvoiceInfo")
	public Json updateInvoiceInfo(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		String orderNumber = request.getParameter("orderNumber");
		Integer id = Integer.valueOf(request.getParameter("orderId"));

		MemberInvoices memberInvoices = new MemberInvoices();
		String invoiceRemark = request.getParameter("invoice_remark_i");
		if(!StringUtil.isEmpty(invoiceRemark)) {
			invoiceRemark = invoiceRemark.trim();
		}
		String bankCardNumber = request.getParameter("bank_card_number_i");
		if(!StringUtil.isEmpty(bankCardNumber)) {
			bankCardNumber = bankCardNumber.trim();
		}
		String bankName = request.getParameter("bank_name_i");
		if(!StringUtil.isEmpty(bankName)) {
			bankName = bankName.trim();
		}
		String registerPhone = request.getParameter("register_phone_i");
		if(!StringUtil.isEmpty(registerPhone)) {
			registerPhone = registerPhone.trim();
		}
		String registerAddress = request.getParameter("register_address_i");
		if(!StringUtil.isEmpty(registerAddress)) {
			registerAddress = registerAddress.trim();
		}
		String taxPayerNumber = request.getParameter("tax_payer_number_i");
		if(!StringUtil.isEmpty(taxPayerNumber)) {
			taxPayerNumber = taxPayerNumber.trim();
		}
		String invoicesRaised = request.getParameter("invoices_raised_i");
		if(!StringUtil.isEmpty(invoicesRaised)) {
			invoicesRaised = invoicesRaised.trim();
		}
		String invoiceType = request.getParameter("invoice_type");
		String invoiceTypeI = request.getParameter("invoice_type_i");
		Integer invoiceTypeInt = Integer.valueOf(invoiceType);
		
		memberInvoices.setRemark(invoiceRemark);
		memberInvoices.setBankCardNumber(bankCardNumber);
		memberInvoices.setBankName(bankName);
		memberInvoices.setRegisterPhone(registerPhone);
		memberInvoices.setRegisterAddress(registerAddress);
		memberInvoices.setTaxPayerNumber(taxPayerNumber);
		memberInvoices.setInvoiceTitle(invoicesRaised);
		memberInvoices.setInvoiceType(invoiceTypeInt);
		if(invoiceTypeInt == 1) {
			memberInvoices.setElectricFlag(0);
			memberInvoices.setState(0);
		}else {
			memberInvoices.setElectricFlag(1);
		}
		
		ServiceResult<Boolean> updateSourceOrderNumber = orderService.updateInvoiceInfo(userName,orderNumber,id,memberInvoices,invoiceTypeI);
		boolean success = updateSourceOrderNumber.getSuccess();
		String message = updateSourceOrderNumber.getMessage();
		Json json = new Json();
		json.setSuccess(success);
		json.setMsg(message);
		return json;
	}
	
	/*
	* 修改收货人信息
	* @author gengdi
	* */
	@ResponseBody
	@RequestMapping("updateAddress")
	public Json updateAddress(HttpServletRequest request)throws Exception{
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		String orderNumber = request.getParameter("orderNumber");
		Integer id = Integer.valueOf(request.getParameter("orderId"));
		
		Orders orders = new Orders();
		orders.setConsignee(request.getParameter("address_consignee"));
		orders.setMobile(request.getParameter("address_mobile"));
		orders.setPhone(request.getParameter("address_phone"));
		orders.setZipcode(request.getParameter("address_zipcode"));
		orders.setAddress(request.getParameter("address_address"));
		
		ServiceResult<Boolean> updateSourceOrderNumber = orderService.updateAddress(userName,orderNumber,id,orders);
		boolean success = updateSourceOrderNumber.getSuccess();
		String message = updateSourceOrderNumber.getMessage();
		Json json = new Json();
		json.setSuccess(success);
		json.setMsg(message);
		return json;
	}


	    /**
	     * 导出网单查询列表
	     *
	     * @param vo
	     * @param request
	     * @param response
	     */
	    @RequestMapping(value = {"exportOrderList"},method = RequestMethod.POST)
	    void exportOrderList(OrderProductsVo vo,  HttpServletRequest request, HttpServletResponse response) {

			if(!"".equals(vo.getRegionAssign().trim())) {
				//区域查询由用省名查询改为用 o.province 查询
				if("east".equals(vo.getRegionAssign().trim())) {//东区
					//12 13 15 10 11 16
					//vo.setRegionAssign("浙江,安徽,江西,江苏,上海,山东");
					vo.setRegionAssign("east");
				}else
				if("south".equals(vo.getRegionAssign().trim())) {//南区
					//18 19 20 21 14 22
					//vo.setRegionAssign("湖南,福建,广东,海南,广西,湖北");
					vo.setRegionAssign("south");
				}else
				if("west".equals(vo.getRegionAssign().trim())) {//西区
					// 5 26 25 29 27 24 30 32 31 23 28
					//vo.setRegionAssign("山西,云南,贵州,甘肃,西藏,四川,青海,新疆,宁夏,重庆，陕西");
					vo.setRegionAssign("west");
				}else
				if("north".equals(vo.getRegionAssign().trim())) {//北区
					// 2 3 4 5 6 7 8 9 17
					//vo.setRegionAssign("北京,河北,吉林,黑龙江,内蒙古,山西,天津,河南,辽宁");
					vo.setRegionAssign("north");
				}

			}

			//以下过程用于去source 中的 "" 和 %2C 以下代码 用来多来源查询 留下备用
//			String[] sourceList = null;
//			if (vo.getSource() != null && !"".equals(vo.getSource())) {
//				sourceList = vo.getSource().split("%2C");
//				StringBuffer sb = new StringBuffer();
//				for (int i = 0; i < sourceList.length; i++) {
//					if ("".equals(sourceList[i])) {
//						continue;
//					}
//					sb.append(sourceList[i]);
//					if (i != sourceList.length - 1) {
//						sb.append(",");
//					}
//				}
//				vo.setSource(sb.toString());
//			}
			if (vo.getAddTimeMin() != null && !"".equals(vo.getAddTimeMin())){
				vo.setAddTimeMin(vo.getAddTimeMin().replace("+" ," ").replace("%3A",":"));
			}
			if (vo.getAddTimeMax() != null && !"".equals(vo.getAddTimeMax())){
				vo.setAddTimeMax(vo.getAddTimeMax().replace("+" ," ").replace("%3A",":"));
			}
			List<Map<String,Object>> list= operationAreaService.queryNetSheetExportDate(vo);

	        String fileName = "网单查询列表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	        String sheetName = "数据导出";

	        String[] sheetHead = new String[]{
	        		"序号",
	        		"订单号",
	        		"来源订单号",
	        		"关联订单号",
	        		"网单编号",
	        		"网单库存类型",
					"网单状态",
					"网单付款状态",
					"开票状态",
					"付款方式",
					"下单时间",
					"同步到商城时间",
					"付款时间",
					"订单来源",
					"品牌",
					"商品分类",
					"产品型号",
					"物料编码",
					"价格",
					"数量",
					"金额",
					"优惠券抵用金额",
					"订单使用积分",
					"是否为预定网单",
					"是否后台下单",
	        		"订单模式",
	        		"所属库房",
	        		"转运库房",
	        		"转运时效",
	        		"省",
	        		"市",
	        		"区县",
	        		"街道",
	        		"转人工时间",
	        		"转人工原因",
	        		"确认时间",
	        		"分配时间",
	        		"分配网点",
	        		"Les开单时间",
	        		"Les出库时间",
	        		"Les出库号",
	        		"网点签收时间",
	        		"网点出库时间",
	        		"用户签收时间",
	        		"开票时间",
	        		"取消时间",
	        		"配送时效(天)",
	        		"订单备注",
	        		"首次确认时间",
	        		"首次确认人",
	        		"预约(最晚)送货时间",
	        		"是否超时免单",
	        		"HP回传预约时间",
	        		"订单类型",
	        		"收货人",
	        		"转运出库时间",
	        		"转运入库时间",
	        		"物流到网点时间",
	        		"终端号",
	        		"交易类型",
	        		"pos付款时间",
	        		"交易金额",
	        		"交易参考号",
	        		"尾款付款时间",
	        		"销售代表",
					"用户地址",
					"库位编码",
					"是否是赠品"

	        };
	        try {
	            ExcelExportUtil.exportEntity(null, request, response, fileName, sheetName, sheetHead,
	                    new ExcelCallbackInterfaceUtil() {

	                        @Override
	                        public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp) throws Exception {
	                            setExportOrderList(sheet, temp, list);
	                        }
	                    });
	        } catch (Exception e) {
	        	LOGGER.error("订单查询列表导出失败", e);
	            e.printStackTrace();
	        }
	    }
	    /**
	     * 导出具体数据，实现回调函数
	     *
	     * @param sheet
	     * @param temp  行号
	     * @param list  传入需要导出的 list
	     * @throws WriteException
	     */
	    private void setExportOrderList(WritableSheet sheet, int temp,
	                                    List<Map<String,Object>> list) throws Exception {
	        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
	                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
	        WritableCellFormat textFormat = new WritableCellFormat(font);
	        textFormat.setAlignment(Alignment.CENTRE);
	        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

	        DisplayFormat displayFormat = NumberFormats.INTEGER;
	        WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
	        numberFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			//数字类型 addCell new Number 
			NumberFormat nf = new NumberFormat("#0.00");
			WritableCellFormat numOutFormat = new WritableCellFormat(font, nf);
			numOutFormat.setAlignment(Alignment.CENTRE);
			numOutFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
	        int index = 0;
	        for (Map<String,Object> map: list) {

	            index++;
	            int i = 0;
	            //序号
	            sheet.setColumnView(0, 10);
	            sheet.addCell(new Label(0, temp, getStringValue(index), textFormat));
	            //订单号
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("orderSn")), textFormat));
	            //来源订单号
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("sourceOrderSn")), textFormat));
	            //关联订单号
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("relationOrderSn")), textFormat));
	            //网单编号
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("cOrderSn")), textFormat));
	            //网单库存类型
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("stockType")), textFormat));//类型需判断
				//网单状态
				sheet.setColumnView(++i, 25);
				sheet.addCell(new Label(i, temp, getStatus(map.get("status")), textFormat));//类型需要判断
				//网单付款状态
				sheet.setColumnView(++i, 25);
				sheet.addCell(new Label(i, temp, getPaymentStatus(map.get("cPaymentStatus")), textFormat));
				//开票状态
				sheet.setColumnView(++i, 25);
				sheet.addCell(new Label(i, temp, getfp(map.get("isMakeReceipt")), textFormat));//类型需要判断
				//付款方式
				sheet.setColumnView(++i, 25);
				sheet.addCell(new Label(i, temp, getStringValue(map.get("paymentName")), textFormat));
				//下单时间
				sheet.setColumnView(++i, 25);
				sheet.addCell(new Label(i, temp, getTimeFormatObj(map.get("addTime")), textFormat));
				//同步到商城时间
				sheet.setColumnView(++i, 25);
				sheet.addCell(new Label(i, temp, getStringValue(map.get("syncTime")), textFormat));
				//付款时间
				sheet.setColumnView(++i, 25);
				sheet.addCell(new Label(i, temp, getTimeFormatObj(map.get("payTime")), textFormat));
				//订单来源
				sheet.setColumnView(++i, 25);
				sheet.addCell(new Label(i, temp, getShopEnum(map.get("source")), textFormat));
				//品牌
				sheet.setColumnView(++i, 25);
				sheet.addCell(new Label(i, temp, getStringValue(map.get("brandName")), textFormat));
				//商品分类
				sheet.setColumnView(++i, 25);
				sheet.addCell(new Label(i, temp, getStringValue(map.get("typeName")), textFormat));
				//产品型号
				sheet.setColumnView(++i, 25);
				sheet.addCell(new Label(i, temp, getStringValue(map.get("productName")), textFormat));
				// 物料编码
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("sku")), textFormat));
	            //价格
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Number(i, temp, getDoubleValue(map.get("price")), numOutFormat));
	            //数量
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Number(i, temp, getDoubleValue(map.get("number")), numOutFormat));
	            //金额
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Number(i, temp, getDoubleValue(map.get("productAmount")), numOutFormat));
	            //优惠券抵用金额
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Number(i, temp, getDoubleValue(map.get("couponAmount")), numOutFormat));
	            //订单使用积分
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("points")), textFormat));
	            //是否为预定网单
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getIsBook(map.get("isBook")), textFormat));
	            //是否后台下单
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getIsBackEnd(map.get("isBackend")), textFormat));//类型需要判断
	            //订单模式
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("shippingMode")), textFormat));
	            //所属库房
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("sCode")), textFormat));
	            //转运库房
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("tsCode")), textFormat));
	            //转运时效
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("tsShippingTime")), textFormat));
	            //省
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("province")), textFormat));//需要转换
	            //市
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("city")), textFormat));//需要转换
	            //区/县
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("region")), textFormat));//需要转换
	            //街道
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("street")), textFormat));//需要转换



	            //转人工时间
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getTimeFormatObj(map.get("smManualTime")), textFormat));
	            //转人工原因
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("smManualRemark")), textFormat));
	            //确认时间
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getTimeFormatObj(map.get("confirmTime")), textFormat));
	            //分配时间
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getTimeFormatObj(map.get("hpAllotNetPointTime")), textFormat));
	            //分配网点
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("netPointName")), textFormat));
	            //Les开单时间
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getTimeFormatObj(map.get("lessShipTime")), textFormat));
	            //Les出库时间
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getTimeFormatObj(map.get("lessShipTime")), textFormat));
	            //Les出库号
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("lessOrderSn")), textFormat));
	            //网点签收时间
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getTimeFormatObj(map.get("netPointAcceptTime")), textFormat));
	            //网点出库时间
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getTimeFormatObj(map.get("netPointShipTime")), textFormat));
	            //用户签收时间
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getTimeFormatObj(map.get("userAcceptTime")), textFormat));
	            //开票时间
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("receiptAddTime")), textFormat));
	            //取消时间
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getTimeFormatObj(map.get("cancelTime")), textFormat));
	            //配送时效(天 )
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("shippingTime")), textFormat));
	            //订单备注
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("remark")), textFormat));
	            //首次确认时间
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getTimeFormatObj(map.get("firstConfirmTime")), textFormat));
	            //首次确认人
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("firstConfirmPerson")), textFormat));
	            //预约最晚送货时间
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, setShippingTime(String.valueOf(map.get("lastTime")),String.valueOf(map.get("addhours"))
						,String.valueOf(map.get("payTimes")),String.valueOf(map.get("tsCode")))
						, textFormat));
	            //是否超时免单
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getIsTimeoutFree(map.get("isTimeoutFree")), textFormat));//类型需要判断
	            //HP回传预约时间
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getTimeFormatObj(map.get("hpReservationDate")), textFormat));
	            //订单类型
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getOrderType(map.get("orderType")), textFormat));//类型需要判断
	            //收货人
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("consignee")), textFormat));
	            //转运出库时间
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getTimeFormatObj(map.get("lessShipTOutTime")), textFormat));
	            //转运入库时间
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getTimeFormatObj(map.get("lessShipTInTime")), textFormat));
	            //物流到网点时间
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getTimeFormatObj(map.get("netPointArriveTime")), textFormat));
	            //终端号
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("暂无数据(终端号)")), textFormat));
	            //交易类型
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getTradeType(map.get("tradeType")), textFormat));//类型需要判断
	            //POS付款时间
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("暂无数据(POS付款时间)")), textFormat));
	            //交易金额
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Number(i, temp, getDoubleValue(map.get("paidAmount")), numOutFormat));
	            //交易参考号
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("暂无数据(交易参考号)")), textFormat));
	            //尾款付款时间
	            sheet.setColumnView(++i, 25);
//	            sheet.addCell(new Label(i, temp, String.valueOf(map.get("tailPayTime")), textFormat));
	            sheet.addCell(new Label(i, temp, getTimeFormatObj(map.get("tailPayTime")), textFormat));
	            //销售代表
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("sellpeople")), textFormat));
				//用户地址
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("address")), textFormat));
				//库位编码
	            sheet.setColumnView(++i, 25);
	            sheet.addCell(new Label(i, temp, getStringValue(map.get("sCode")), textFormat));
				//是否是赠品
				sheet.setColumnView(++i, 25);
				sheet.addCell(new Label(i, temp, getIdGiftValue(map.get("idGift")), textFormat));

	            temp++;
	        }
	    }

		public static String getIdGiftValue(Object obj) {
			if (obj == null) {
				return "";
			}
			String v = String.valueOf(obj);
			if (v.equals("0")){
				return "否";
			}else if (v.equals("1")){
				return "是";
			}else {
				return "";
			}

		}

		public static Double getDoubleValue(Object obj) {
			if (obj == null)
				return 0.00;

			return Double.parseDouble(String.valueOf(obj));

		}


		public static String getIsTimeoutFree(Object obj) {
			if (obj == null)
				return "";
			String v = String.valueOf(obj);
			if (v.equals("0")){
				return "未设置";
			}else if (v.equals("1")){
				return "是";
			}else if (v.equals("2")){
				return "否";
			}else {
				return "";
			}

		}




	    /**
	     * 把object转为String null时转为""
	     *
	     * @param obj
	     * @return
	     */
	    public static String getStringValue(Object obj) {
	        if (obj == null)
	            return "";
	        return String.valueOf(obj);
	    }
	    /**
	     * 空时间转换
	     *
	     * @param time
	     * @return
	     */
	    public static String getTimeFormat(String time) {
	        if (time == null || time.equals("1970-01-01 08:00:00")
	                || time.equals("0000-00-00 00:00:00"))
	            return "";
	        return String.valueOf(time);
	    }

		public static String getTimeFormatObj(Object obj) {
	    	String time = "";
			if (obj == null){
				return "";
			}else {
				time = String.valueOf(obj);
			}

			return getTimeFormat(time);
		}

		public static String getShopEnum(Object obj) {
			String shop = "";
			if (obj == null){
				return "";
			}else {
				shop = String.valueOf(obj);
				if ("TBSC".equals(shop)){
					return "海尔官方淘宝旗舰店";
				}else if ("TOPBOILER".equals(shop)){
					return "海尔热水器专卖店";
				}else if ("TONGSHUAI".equals(shop)){
					return "淘宝网统帅日日顺乐家专卖店";
				}else if ("TOPFENXIAO".equals(shop)){
					return "海尔官方旗舰店:营销02(分销平台)";
				}else if ("TOPXB".equals(shop)){
					return "海尔新宝旗舰店";
				}else if ("TOPFENXIAOXB".equals(shop)){
					return "海尔新宝旗舰店分销平台";
				}else if ("TONGSHUAIFX".equals(shop)){
					return "统帅日日顺分销平台";
				}else if ("MOOKA".equals(shop)){
					return "淘宝模卡旗舰店";
				}else if ("TMMK".equals(shop)){
					return "mooka模卡官方旗舰店";
				}else if ("TMMKFX".equals(shop)){
					return "mooka模卡分销";
				}else if ("WASHER".equals(shop)){
					return "海尔洗衣机旗舰店";
				}else if ("FRIDGE".equals(shop)){
					return "海尔冰冷旗舰店";
				}else if ("AIR".equals(shop)){
					return "海尔空调旗舰店";
				}else if ("TBCT".equals(shop)){
					return "村淘海尔商家";
				}else if ("GQGYS".equals(shop)){
					return "生态授权店";
				}else if ("TMKSD".equals(shop)){
					return "天猫卡萨帝旗舰店";
				}else if ("TMTV".equals(shop)){
					return "海尔电视旗舰店";
				}else if ("TBCFDD".equals(shop)){
					return "海尔厨房大电旗舰店";
				}else if ("TBXCR".equals(shop)){
					return "天猫小超人旗舰店";
				}else if ("TBZYKT".equals(shop)){
					return "淘宝中央空调";
				}else if ("SNYG".equals(shop)){
					return "苏宁统帅官方旗舰店";
				}else if ("SNHEGQ".equals(shop)){
					return "苏宁海尔官方旗舰店";
				}else if ("SNQDZX".equals(shop)){
					return "苏宁渠道中心";
				}else if ("DDW".equals(shop)){
					return "海尔当当旗舰店";
				}else if ("GMZX".equals(shop)){
					return "国美海尔官方旗舰店";
				}else if ("GMZXTS".equals(shop)){
					return "国美统帅官方旗舰店";
				}else if ("GMTSZYYY".equals(shop)){
					return "国美自营店(电视家庭影音)";
				}else if ("JDHEGQ".equals(shop)){
					return "京东海尔官方旗舰店";
				}else if ("JDHEBXGQ".equals(shop)){
					return "京东海尔集团冰箱官方旗舰店";
				}else if ("1".equals(shop)){
					return "商城PC";
				}else if ("XSST".equals(shop)){
					return "淘宝WA线上生态授权店";
				}else {
					return shop;
				}
			}
		}

		public static String getStatus(Object obj) {
			String status = "";
			if (obj == null){
				return "";
			}else {
				int v = Integer.parseInt(String.valueOf(obj));
				if (v == 0 ){
					status = "处理中";
				}else if (v == 1){
					status = "已占用库存";
				}else if (v == 2 ){
					status = "同步到HP";
				}else if (v == 3){
					status = "同步到EC";
				}else if (v == 4){
					status = "已分配到网点";
				}else if (v == 8){
					status = "待出库";
				}else if (v == 10 ){
					status = "待审核";
				}else if (v == 11){
					status = "待转运入库";
				}else if (v == 12){
					status = "待转运出库";
				}else if (v == 40){
					status = "待发货";
				}else if (v == 150){
					status = "网点拒单";
				}else if (v == 70 ){
					status = "待交付";
				}else if (v == 130 ){
					status = "完成关闭";
				}else if (v == 140){
					status = "用户签收";
				}else if (v == 160){
					status = "用户拒收";
				}else if (v == 110){
					status = "取消关闭";
				}else if (v == -100){
					status = "未定义";
				}else {
					status = "";
				}
				return status;

			}
		}

		public static String getPaymentStatus(Object obj){
			String pstatus = "";
			if (obj == null){
				return "";
			}else {
				int v = Integer.parseInt(String.valueOf(obj));
				if (v == 200 ){
					pstatus = "未付款";
				}else if (v == 201 || v == 101){
					pstatus = "买家已付款";
				}else if (v == 206){
					pstatus = "待退款";
				}else if (v == 207){
					pstatus = "已退款";
				}else {
					return "未定义:"+String.valueOf(obj);
				}
				return pstatus;
			}
		}
		public static String getfp(Object obj){
			String fp = "";
			if (obj == null){
				return "";
			}else {
				int v = Integer.parseInt(String.valueOf(obj));
				if (v == 1 ){
					fp = "未开票";
				}else if (v == 2){
					fp = "已开票";
				}else if (v == 10){
					fp = "取消开票";
				}else if (v == 4){
					fp = "跨月冲红";
				}else if (v == 3){
					fp = "作废发票";
				}else if (v == 9){
					fp = "待开票";
				}else if (v == 5 ){
					fp = "开票中";
				}else if (v == 6){
					fp = "开票失败";
				}else if (v == 20){
					fp = "期初数据封存";
				}else {

				}

				return fp;
			}
		}

		public static String getOrderType(Object obj){
			if (obj == null){
				return "";
			}else {
				int v = Integer.parseInt(String.valueOf(obj));
				if (v == 0 ){
					return "普通订单";
				}else if (v == 1){
					return "团购预付款订单";
				}else if (v == 2){
					return "团购尾款订单";
				}else if (v == 3){
					return "团购订单";
				}else if (v == 4){
					return "单订单模式的定金-尾款订单";
				}else {
					return "";
				}
			}

		}
		public static String getTradeType(Object obj){
			if (obj == null){
				return "";
			}else {
				String v = String.valueOf(obj);
				if (v.equals("fixed") ){
					return "一口价";
				}else if (v.equals("fx")){
					return "分销";
				}else if (v.equals("step")){
					return "万人团";
				}else {
					return "";
				}
			}
		}


		public static String getIsBook(Object obj){
			if (obj == null){
				return "";
			}else {
				String v = String.valueOf(obj);
				if (v.equals("false") ){
					return "否";
				}else if (v.equals("true")){
					return "是";
				}else {
					return "";
				}
			}
		}
		public static String getIsBackEnd(Object obj){
			if (obj == null){
				return "";
			}else {
				String v = String.valueOf(obj);
				if (v.equals("0") ){
					return "否";
				}else if (v.equals("1")){
					return "是";
				}else {
					return "";
				}
			}
		}


	public String setShippingTime(String shippingTime,String addhours,String payTimes,String tsCode) throws java.text.ParseException {
	    	if (addhours.equals("null")||addhours == null){
				addhours = "0";
			}
			if (payTimes.equals("null")||payTimes == null){
				payTimes = "0";
			}
			if (shippingTime.equals("null")||shippingTime == null){
				shippingTime = "";
			}if (tsCode.equals("null")||tsCode == null){
	    		tsCode = "";
			}
		int addhour = addhours == null ? 0 : Integer.valueOf(addhours);
		int payTime = payTimes == null ? 0 : Integer.valueOf(payTimes);
		String shippingTimeString = shippingTime == null ? "" : shippingTime;
		long shippingTimes = this.calDay(payTime, shippingTimeString, addhour, tsCode);
		if (shippingTimes == 0){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(Long.valueOf(shippingTimes)));
	}
	private Long calDay(Integer beginTimeOld, String dayString, Integer addhours,
						String tsCode) throws java.text.ParseException {
		Long beginTime = beginTimeOld * 1000L;
		if (beginTime == 0 || dayString == null || dayString.equals("")
				|| Integer.valueOf(dayString) == 0) {
			return 0L;
		}
		int daymax = 0;
		if (dayString != null && !dayString.trim().equals("")) {
			dayString = dayString.trim();
			int hour = 0;
			String day = "";
			if (dayString.indexOf("小时") > -1) {
				hour = Integer.valueOf(dayString.trim().substring(0, dayString.indexOf("小时")));
				daymax = hour / 24;
			} else if (dayString.indexOf("天") > -1) {
				day = dayString.substring(0, dayString.indexOf("天"));
				String[] days = day.split("-");
				daymax = Integer.valueOf(days[1]);
			} else {
				int dayInt = Integer.valueOf(dayString);
				if (dayInt > 0) {
					daymax = dayInt;
				}
			}
		}
		if (daymax == 0) {
			return 0L;
		}
		//付款日期+配送时效计算出日期，取当天20点；如果是17:30以后付款，则加1天；如果有使用转运库，则加上转运时间
		Long endTime = beginTime + daymax * 86400 * 1000L;
		if (addhours > 0 && tsCode != null && !"".equals(tsCode)) {//如果有使用转运库，则加上转运时间
			endTime += addhours * 3600 * 1000L;
		}
		// 添加12小时订单处理期时间(以前的注释错了，实际增加了24小时，单位也错了，应该* 1000L)
		if (new Date(endTime).getHours() >= 18
				|| (new Date(endTime).getHours() >= 17 && new Date(endTime).getMinutes() >= 30)) {
			//endTime += 86400;
			endTime += 86400 * 1000L; //24小时（张允冷）
		}
		if (null == timeFormat.get()) {
			timeFormat.set(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));
		}
		if (null == dayFormat.get()) {
			dayFormat.set(new SimpleDateFormat("yyyy/MM/dd"));
		}

		SimpleDateFormat dayformat = dayFormat.get();
		SimpleDateFormat dateformat = timeFormat.get();
		String dateString = dayformat.format(new Date(endTime));

		return dateformat.parse(dateString + " 20:00:00").getTime();
	}
	ThreadLocal<SimpleDateFormat> timeFormat = new ThreadLocal<SimpleDateFormat>();

	ThreadLocal<SimpleDateFormat> dayFormat = new ThreadLocal<SimpleDateFormat>();

	ThreadLocal<SimpleDateFormat> rsFormat = new ThreadLocal<SimpleDateFormat>();


	/**
	   //TODO 批量订单确认付款
	   * @param request
	   * @param response
	   * @param modelMap
	   * @author ysh
	   * @return
	   */
	  @ResponseBody
	  @RequestMapping("batchConfirmationReceipts")
	  public ModelAndView batchConfirmationReceipts() {
		  ModelAndView mv  = new ModelAndView();
		  mv.setViewName("order/batchConfirmationPayment");
		  return mv;
	  }
	  
	  /**
	   //TODO 批量订单确认付款
	   * @param cOrderSns
	   * @param modelMap
	   * @param request
	   * @param response
	   * @return
	   */
	  @RequestMapping(value = { "doBatchConfirmationPayment" }, method = { RequestMethod.POST })
	  @ResponseBody
	  public ServiceResult<Map<String, Object>> doBatchConfirmationPayment(@RequestParam(required = true) String cOrderSns,
	                                                                  Map<String, Object> modelMap,
	                                                                  HttpServletRequest request,
	                                                                  HttpServletResponse response) {
	      response.setCharacterEncoding("UTF-8");
	      ServiceResult<Map<String, Object>> result = new ServiceResult<Map<String, Object>>();
	      HttpSession session = request.getSession();
		  String userName = Ustring.getString(session.getAttribute("userName"));
	      if (StringUtil.isEmpty(cOrderSns, true)) {
	          result.setMessage("请提交有效的订单编号！");
	          return result;
	      }
	      try {
	          result = this.orderService.doBatchConfirmationPayment(cOrderSns, modelMap,userName);
	      } catch (Exception e) {
	          logger.error("[order][doBatchConfirmationPayment]批量订单确认付款时发生未知错误", e);
	      }
	      return result;
	  }

	/**
	 * 差评信息导出跳转
	 */
	@ResponseBody
	@RequestMapping("exportBadComments")
	public ModelAndView exportBadComments() {
		ModelAndView mv  = new ModelAndView();
		mv.setViewName("order/exportBadComments");
		return mv;
	}
	@PostMapping("exportBadCommentsList")
	public void  exportBadCommentsList(String sourceOrderSns,HttpServletRequest request, HttpServletResponse response){
		String[] split = sourceOrderSns.replace(" ", "").split("\r\n");
		List<String> lists = java.util.Arrays.asList(split);
		List<Map<String, Object>> maps = shopOperationAreaService.exportBadCommentsList(lists);
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.DATE, -1);
		date = calendar.getTime();
		String fileName = "差评信息导出" + new SimpleDateFormat("yyyy-MM-dd").format(date);
		String sheetName = "差评信息导出";

		String[] sheetHead = new String[]{
				"序号",
				"订单号",
				"姓名",
				"地址",
				"电话",
		};
		try {
			ExcelExportUtil.exportEntity(null, request, response, fileName, sheetName, sheetHead,
					new ExcelCallbackInterfaceUtil() {

						@Override
						public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp) throws Exception {
							setExportBadCommentsList(sheet, temp, maps);
						}
					});
		} catch (Exception e) {
			LOGGER.error("订单查询列表导出失败", e);
			e.printStackTrace();
		}
	}

	private void setExportBadCommentsList(WritableSheet sheet, int temp,
									List<Map<String,Object>> list) throws Exception {
		WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
				UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
		WritableCellFormat textFormat = new WritableCellFormat(font);
		textFormat.setAlignment(Alignment.CENTRE);
		textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

		DisplayFormat displayFormat = NumberFormats.INTEGER;
		WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
		numberFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		//数字类型 addCell new Number
		NumberFormat nf = new NumberFormat("#0.00");
		WritableCellFormat numOutFormat = new WritableCellFormat(font, nf);
		numOutFormat.setAlignment(Alignment.CENTRE);
		numOutFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		int index = 0;
		for (Map<String,Object> map: list) {

			index++;
			int i = 0;
			//序号
			sheet.setColumnView(0, 10);
			sheet.addCell(new Label(0, temp, getStringValue(index), textFormat));
			//订单号
			sheet.setColumnView(++i, 25);
			sheet.addCell(new Label(i, temp, getStringValue(map.get("sourceOrderSn")), textFormat));
			//来源订单号
			sheet.setColumnView(++i, 25);
			sheet.addCell(new Label(i, temp, getStringValue(map.get("consignee")), textFormat));
			//关联订单号
			sheet.setColumnView(++i, 25);
			sheet.addCell(new Label(i, temp, getStringValue(map.get("regionName")), textFormat));
			//网单编号
			sheet.setColumnView(++i, 25);
			sheet.addCell(new Label(i, temp, getStringValue(map.get("mobile")), textFormat));

			temp++;
		}
	}

}
