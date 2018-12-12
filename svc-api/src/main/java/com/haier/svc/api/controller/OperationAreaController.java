package com.haier.svc.api.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haier.afterSale.model.Json;
import com.haier.order.service.MdmService;
import com.haier.shop.model.OrderProductsVo;
import com.haier.shop.model.OrderRepairsVo;
import com.haier.shop.model.OrderTmallReturnLogs;
import com.haier.shop.model.OrderrepairHPrecords;
import com.haier.shop.service.ShopOperationAreaService;
import org.apache.log4j.LogManager;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.haier.afterSale.model.Ustring;
import com.haier.afterSale.service.OperationAreaService;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;

/**
 * 网单
 * @author
 * Created by 吴坤洋 on 2017/10/24.
 *
 */
@Controller
@RequestMapping(value="operationArea/")
public class OperationAreaController {
	@Autowired
	private ShopOperationAreaService shopOperationAreaService;
	@Autowired
	private MdmService mdmService;
	private final static org.apache.log4j.Logger logger = LogManager
			.getLogger(OperationAreaController.class);
	@Autowired
	private OperationAreaService operationAreaService;
	/**
	 * 查询
	 * @param
	 * @return
	 */
	@RequestMapping("search")
	@ResponseBody
	public void search(OrderProductsVo vo,HttpServletRequest request, HttpServletResponse response){
		if (vo.getRows() == null){
			vo.setRows(20);
		}
		if (vo.getPage() == null)
		{
			vo.setPage(1);
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
	 *  根据网单编号 查询明细 表单
	 * @param cOrderSn
	 * @return
	 */
	@RequestMapping("ProductView")
	public ModelAndView ProductView(String cOrderSn){
		ModelAndView mv  = new ModelAndView();
		OrderProductsVo vo =operationAreaService.PrudectView(cOrderSn);
		String orderSn=operationAreaService.selectOrderSn(cOrderSn);//根据网单
		if (Ustring.isEmpty(orderSn)){
			orderSn="";
		}
		mv.addObject("vo", vo);
		mv.addObject("orderSn", orderSn);
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
	public Json PrudevtDatail(String cOrderSn) throws ParseException {
		Json josn = new Json();
		OrderProductsVo vodata= operationAreaService.PrudectDetailed(cOrderSn);
		josn.setObj(vodata);
		return josn;
	}

	/**
	 * 保存退货信息
	 */
	@RequestMapping(value="SaveRepairs",method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public Json SaveRepairs(@RequestBody OrderRepairsVo orderRepairs){
		Json josn = new Json();
		josn = operationAreaService.SaveRepairs(orderRepairs);
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
		OrderrepairHPrecords hpRecords =(OrderrepairHPrecords) map.get("hpRecords");
		mv.addObject("vo",vo);
		mv.addObject("list",list);
		mv.addObject("hpRecords",hpRecords);
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
	 * 退货审核
	 * @param id
	 * @return
	 */
	@RequestMapping("Toexamine")
	@ResponseBody
	public Json Toexamine(String id,String status,String handleRemark){
		return operationAreaService.Toexamine(id, status, handleRemark);
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
	* hp拒收表格显示
	* @author zhangbo
	* */
	@ResponseBody
	@RequestMapping("exceptional_point")
	public Json exceptional_point(@RequestBody List<Map<String,Object>> hpRecordsList)throws Exception{
		Json json=new Json();
		if (hpRecordsList.size()!=0){
			//根据tb单号匹配网单号并返回(tb单号,sku,网单号)
			List<Map<String,Object>> exist_cOrderSnList=operationAreaService.check_cOrderSn_isExist(hpRecordsList);
			//需要重置的网单号在网单表都不存在_
			if (exist_cOrderSnList.size()==0){
				List<String> list=new ArrayList<>();
				for (Map map:hpRecordsList){
					list.add(map.get("hpTbSn").toString());
				}
				//更新WwwHpRecords表中的匹配次数
				operationAreaService.update_WwwHpRecordsCount(list);
				json.setMsg("单据匹配失败,出库信息未获取。待人工处理");
				return json;
			}
			//这一步是把hpRecordsList(前台勾选的sku)添加到exist_cOrderSnList里面
			for (Map map:hpRecordsList){
				String hpTbSn=map.get("hpTbSn").toString();
				for (Map map1:exist_cOrderSnList){
					String hpTbSn1=map1.get("hpTbSn").toString();
					if (hpTbSn.equals(hpTbSn1)){
						map1.put("input_sku",map.get("sku"));
					}
				}
			}
			List<Map<String,Object>> difference_sku=new ArrayList<>();//sku不相同,需要去空调关联表里里面去根据子健号查询主物料号
			List<Map<String,Object>> identical_sku=new ArrayList<>();//sku相同
			//匹配到网单号之后去比较sku
			for (Map map:exist_cOrderSnList){
				String sku=map.get("sku").toString();
				String input_sku=map.get("input_sku").toString();
				if (!(sku.equals(input_sku))){
					difference_sku.add(map);
				}else{
					identical_sku.add(map);
				}
			}
			//更新HP拒收表的详细信息
			if (identical_sku.size()!=0){
				for (Map map:identical_sku){
					//没有
					if (map.containsKey("th_id")==false){
					if (map.get("th_id")==null||map.get("th_id")=="") {
						String cOrderSn=map.get("cOrderSn").toString();
						Map<String,Object>  maps=shopOperationAreaService.selectPhoneAndName(cOrderSn);
						OrderRepairsVo orderRepairsVo=new OrderRepairsVo();
						orderRepairsVo.setcOrderSnId(cOrderSn);
						orderRepairsVo.setReason("其他");
						orderRepairsVo.setDescription("拒收商品");
						orderRepairsVo.setContactMobile(maps.get("mobile").toString());
						orderRepairsVo.setContactName((maps.get("consignee").toString()));
						orderRepairsVo.setType("1");
						Json json1=operationAreaService.SaveRepairs(orderRepairsVo);
						try {
							if (json1.getSuccess()){
								String x=json1.getObj().toString();
								map.put("th_id",x);
								String orderProductId1=shopOperationAreaService.selectOrderProductId(Integer.parseInt(x));
								map.put("orderProductId",orderProductId1);
								List<OrderRepairsVo> voList=new ArrayList<>();
								OrderRepairsVo orderRepairsVo1=new OrderRepairsVo();
								orderRepairsVo1.setId(Integer.parseInt(json1.getObj().toString()));
								orderRepairsVo1.setMenuflag("JS");
								voList.add(orderRepairsVo1);
								try{
									Json json2=operationAreaService.ModifyPushHP(voList);
									System.out.println("=====推送一次质检完成======");
								}catch (Exception e){
									logger.info("推送一次质检失败");
									json.setMsg("推送一次质检失败");
								}
							}
						}catch (Exception e){
							logger.info("生成退货单失败");
							json.setMsg("生成退货单失败");
							}
						}
					}
				}
				try{
					System.out.println(identical_sku.size());
					operationAreaService.update_WwwHpRecordsInfo(identical_sku);
					json.setMsg("匹配成功");
				}catch (Exception e){
					logger.info("更新HP拒收表失败");
					json.setMsg("更新HP拒收表失败");
				}
				return json;
			}
			//SKU不相同的去查询inv_machine_set表
			List<Map<String,Object>> update_WwwHpRecordsList=new ArrayList<>();//
			List<Map<String,Object>> update_count=new ArrayList<>();
			if (difference_sku.size()!=0){
				List<Map<String,Object>> mainAndSub_sku=operationAreaService.select_sku(difference_sku);
				for (Map map:difference_sku){
					String sku=map.get("sku").toString();
					String input_sku=map.get("input_sku").toString();
					for (Map map1:mainAndSub_sku){
						String sub_sku=map1.get("sub_sku").toString();
						String main_sku=map1.get("main_sku").toString();
						if (input_sku.equals(sub_sku)&&sku.equals(main_sku)){
							//根据子健号可以匹配上 SKu的
							update_WwwHpRecordsList.add(map);
						}/*else{
							//sku匹配不上的 直接更新匹配次数和匹配内容
							update_count.add(map);
						}*/
					}
				}
			}
			for (Map map:update_WwwHpRecordsList){
				String hpTbSn= map.get("hpTbSn").toString();
				/*for (Map map1:hpRecordsList){
					if (map1.containsValue(hpTbSn)){
						hpRecordsList.remove(map1);
					}
				}*/
				Iterator<Map<String,Object>> iter = hpRecordsList.iterator() ;  // 为Iterator接口实例化
				while(iter.hasNext()){  // 判断是否有内容
					Map<String,Object> stringObjectMap=iter.next();
					if (stringObjectMap.get("hpTbSn").equals(hpTbSn)){
						iter.remove();
					}
				}
			}
			//需要更新次数的
			List<String> hpTbSnList=new ArrayList<>();
			//此时hpRecordsList只剩下未匹配到网单号的了
			for (Map map:hpRecordsList){
				hpTbSnList.add(map.get("hpTbSn").toString());
			}
			//这个是通过子健号可以匹配上SKU的,需要更新匹配信息
			if (update_WwwHpRecordsList.size()!=0){
				for (Map map:update_WwwHpRecordsList){
					//这个是没有ID的需要去生成退货单号
					if (map.get("orderProductId")==null||map.get("orderProductId")==""){
						OrderRepairsVo vo=new OrderRepairsVo();
						Map<String,Object> map1=operationAreaService.select_ThInfo(map.get("cOrderSn").toString());
						vo.setcOrderSnId(map.get("cOrderSn").toString());
						vo.setReason("用户拒收");
						vo.setCount(Integer.parseInt(map1.get("number").toString()));
						vo.setContactMobile(map1.get("mobile").toString());
						vo.setContactName(map1.get("consignee").toString());
						vo.setDescription(" ");
						Json json1=operationAreaService.SaveRepairs(vo);
						if (json1.getMsg().equals("保存退货信息成功！")){
							map.put("orderProductId",map.get("id").toString());
							map.put("th_id",json1.getObj().toString());
						}
					}
				}
				operationAreaService.update_WwwHpRecordsInfo(update_WwwHpRecordsList);
				List<OrderRepairsVo> voList=new ArrayList<>();
				for (Map map:update_WwwHpRecordsList){
					OrderRepairsVo vo=new OrderRepairsVo();
					vo.setId(Integer.parseInt(map.get("th_id").toString()));
					vo.setMenuflag("JS");
					voList.add(vo);
				}
				Json json1=operationAreaService.ModifyPushHP(voList);
				System.out.println(json1.getMsg());
				json.setMsg("操作成功");
			}
			if (hpTbSnList.size()!=0){
				operationAreaService.update_WwwHpRecordsCount(hpTbSnList);
				json.setMsg("匹配未成功");
			}
			return json;
		}
		return json;
	}
	/*
	* hp拒收报表导出
	* */
	@ResponseBody
	@RequestMapping("export_Excel")
	public Json export_Excel(@RequestBody Map<String,Object> map, HttpServletResponse response)throws Exception{
		Json json=new Json();
		if (Ustring.isEmpty(map.get("success").toString())){
			map.put("success",4);
		}
		//查询需要导出的数据
		List<Map<String,Object>> export_ExcelData=operationAreaService.select_export_ExcelData(map);
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
		}
		File file=new File("hp拒收报表.xls");
		OutputStream os = new FileOutputStream(file);
		wb.write(os);
		os.close();
		json.setMsg("导出报表成功");
		return json;
	}
	
	/**
	 * 推送HP信息
	 * @throws java.text.ParseException 
	 * @throws MalformedURLException 
	 */
	@ResponseBody
	@RequestMapping(value="ModifyPushHP" ,method=RequestMethod.POST, consumes="application/json")
	public Json ModifyPushHP(@RequestBody OrderRepairsVo orderRepairsVo) throws MalformedURLException, java.text.ParseException{
		List<OrderRepairsVo> vo  = new ArrayList<>();
		orderRepairsVo.setMenuflag("SD");//标示是手动来推送的
		vo.add(orderRepairsVo);
		return operationAreaService.ModifyPushHP(vo);
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
			String province=map1.get("province").toString();
			if (!addTime.equals("0")){
			Long timestamp = Long.parseLong(addTime)*1000;
			String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));
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
	public Json export_ExcelOrderRepairs(@RequestBody Map<String,Object> map,HttpServletResponse response)throws Exception{
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
			if (!addTime.equals("0")){
				Long timestamp = Long.parseLong(addTime)*1000;
				String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));
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
			}else{
				map1.put("storageStatus","");
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
		}
		File file=new File("退换货报表.xls");
		OutputStream os = new FileOutputStream(file);
		wb.write(os);
		os.close();
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
		try {
			while ((jsonStr = reader.readLine()) != null) {
				result1.append(jsonStr);
			}
		} catch (IOException e) {
			logger.info("读取报文失败");
			e.printStackTrace();
		}

		String XMLString =result1.toString();
		Document doc = null;
		reader.close();// 关闭输入流
		try{
			// 将字符串转为XML
			doc = DocumentHelper.parseText(XMLString);
			// 获取根节点
			Element rootElt = doc.getRootElement();
			System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
			//判断根节点的名字是不是root
			if (!(rootElt.getName().equals("root"))){
				result="F";
				return result;
			}
			//通过根元素得到一级子元素
			List<Element>firstChild=rootElt.elements();
			if (!(firstChild.get(0).getName().equals("orderJs"))||firstChild.size()!=1){
				result="F";
				return result;
			}
			//通过一级子元素得到二级子元素
			List<Element> secondChild=firstChild.get(0).elements();
			if (!(secondChild.get(0).getName().equals("item"))||secondChild.size()!=1){
				result="F";
				return result;
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
						return "主键不能为空";
					}
					xmlMap.put("rowid",rowid);
					System.out.println(rowid);
				}
				//得到orderno的值(Tb单号) Y
				if (item.getName().equals("orderno")){
					//得到指定节点的值
					String orderno=item.getStringValue();
					if (Ustring.isEmpty(orderno)){
						return "订单号不能为空";
					}
					xmlMap.put("orderno",orderno);
					System.out.println(orderno);
				}
				//得到sourceno的值(来源编号) N
				if (item.getName().equals("sourceno")){
					//得到指定节点的值
					String sourceno=item.getStringValue();
					System.out.println(sourceno);
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
					System.out.println(prodtype);
				}
				//得到remark的值(备注) N
				if (item.getName().equals("remark")){
					//得到指定节点的值
					String remark=item.getStringValue();
					System.out.println(remark);
					xmlMap.put("remark",remark);
				}
				//得到netcode的值(网单86码) N
				if (item.getName().equals("netcode")){
					//得到指定节点的值
					String netcode=item.getStringValue();
					System.out.println(netcode);
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
					System.out.println(status);
				}
				//得到types的值 N
				if (item.getName().equals("types")){
					//得到指定节点的值
					String types=item.getStringValue();
					System.out.println(types);
					xmlMap.put("types",types);
				}
				//得到add10的值(备用字段)
				if (item.getName().equals("add10")){
					//得到指定节点的值
					String add10=item.getStringValue();
					System.out.println(add10);
					xmlMap.put("add10",add10);
				}
				//得到add1的值(备用字段)
				if (item.getName().equals("add1")){
					//得到指定节点的值
					String add1=item.getStringValue();
					System.out.println(add1);
					xmlMap.put("add1",add1);
				}
				//得到add2的值(lbx单号)
				if (item.getName().equals("add2")){
					//得到指定节点的值
					String add2=item.getStringValue();
					System.out.println(add2);
					xmlMap.put("add2",add2);
				}
				//得到add3的值(备用字段)
				if (item.getName().equals("add3")){
					//得到指定节点的值
					String add3=item.getStringValue();
					System.out.println(add3);
					xmlMap.put("add3",add3);
				}
			}
			List<String> list=operationAreaService.selectHPlogsRowid(xmlMap.get("rowid").toString());
			if (list.size()!=0){
				return "F";
			}
			xmlMap.put("receiveData",XMLString);
			//插入到拒收日志表
			operationAreaService.insertHPlogs(xmlMap);
			//插入到HP拒收表
			operationAreaService.insertWwwHpRecords(xmlMap);
			System.out.println("插入成功");
			return "S";
		}catch (Exception e){
			e.printStackTrace();
		}
		return "S";
	}
	@ResponseBody
	@RequestMapping(value="zhangbo")
	public void zhangbo(){
		System.out.println("开始~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		mdmService.insert();
		System.out.println("结束~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
	 /**
     * 关闭退货单更改退货单状态
     * @param id
     * @param handleRemark
     * @return
     */
	@ResponseBody
	@RequestMapping(value="RepairsTermination")
	public Json RepairsTermination(String id,String handleRemark){
		Json json = new Json();
		int i=operationAreaService.RepairsTermination(id, handleRemark);
		if(i>1){
			json.setSuccess(true);
			json.setMsg("终止成功！");
		}else {
			json.setSuccess(false);
			json.setMsg("终止失败！");
		}
		return json;
	}
	/**
	 * 售后操作 22转41库
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="StockTransfer")
	public Json StockTransfer(String id,String handleRemark){
		Json json =new Json();
		int i=operationAreaService.StockTransfer(id, handleRemark);
		if(i>2){
			json.setSuccess(true);
			json.setMsg("操作成功！");
		}else {
			json.setSuccess(false);
			json.setMsg("操作失败！");
		}
		return json;
	}


}
