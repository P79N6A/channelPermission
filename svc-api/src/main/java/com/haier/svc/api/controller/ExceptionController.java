package com.haier.svc.api.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.haier.afterSale.service.CnDataDirectPushCenterService;
import com.haier.afterSale.service.OmsInStoreRecordCenterService;
import com.haier.purchase.data.model.ExchangeGoods;
import com.haier.purchase.data.model.ReturnTable;
import com.haier.shop.model.OmsInStoreRecords;
import com.haier.shop.service.OmsInStoreRecordService;
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
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.json.ParseException;
import com.google.gson.Gson;
import com.haier.afterSale.model.Json;
import com.haier.afterSale.model.Ustring;
import com.haier.afterSale.service.OperationAreaService;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.order.service.MdmService;
import com.haier.shop.model.OrderProductsVo;
import com.haier.shop.model.OrderRepairsVo;
import com.haier.shop.model.OrderrepairHPrecords;
import com.haier.shop.service.ShopOperationAreaService;

/**
 * 处理异常
 * @author
 * Created by 付振兴 on 2018/5/18.
 *
 */
@Controller
@RequestMapping(value="exception/")
public class ExceptionController {
	@Autowired
	private ShopOperationAreaService shopOperationAreaService;
	@Autowired
	private MdmService mdmService;
	@Autowired
	private OmsInStoreRecordCenterService omsInStoreRecordCenterService;

	private final static org.apache.log4j.Logger logger = LogManager
			.getLogger(ExceptionController.class);
	@Autowired
	private OperationAreaService operationAreaService;
	@Autowired
	private CnDataDirectPushCenterService cnDataDirectPushCenterService;

	/**
	 * 查询换货列表
	 * @param
	 * @return
	 */
	@RequestMapping("search")
	@ResponseBody
	public void search(String storeordercode,ExchangeGoods vo, HttpServletRequest request, HttpServletResponse response){
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
			vo.setStorecode(storeordercode);
			ServiceResult<List<ExchangeGoods>> result =cnDataDirectPushCenterService.searchList(vo);
			if (result.getSuccess() && result.getResult() != null) {
				List<ExchangeGoods> predictstocklist = result.getResult();
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
	   @RequestMapping(value = {"/exchangeGoods"})
	    public String exchangeGoods(){
	        return "order/3wExchangeException";
	    }

	/**
	 * 跳转到调拨残次页面
	 * @return
	 */
	@RequestMapping(value = {"/allocationRemnant"})
	public String allocationRemnant(){
		return "order/allocationRemnant";
	}

	/**
	 * 跳转到调拨残次页面
	 * @return
	 */
	@RequestMapping(value = {"/returnGoods"})
	public String returnGoods(){
		return "order/3wReturnGoods";
	}
	/**
	 * 查询调拨残次
	 * @param
	 * @return
	 */
	@RequestMapping("findAllocationRemnant")
	@ResponseBody
	public void findAllocationRemnant(String db,String orderCode,OmsInStoreRecords vo, HttpServletRequest request, HttpServletResponse response){
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
			vo.setDb(db);
			vo.setOrderCode(orderCode);
			ServiceResult<List<OmsInStoreRecords>> result =omsInStoreRecordCenterService.searchList(vo);
			if (result.getSuccess() && result.getResult() != null) {
				List<OmsInStoreRecords> list_oms = result.getResult();
				Map<String, Object> retMap = new HashMap<String, Object>();
				retMap.put("total", result.getPager().getRowsCount());
				retMap.put("rows", list_oms);
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
	 * 查询退货拦截数据
	 * @param subSourceOrderCode
	 * @param vo
	 * @param request
	 * @param response
	 */
	@RequestMapping("findReturnGoods")
	@ResponseBody
	public void findReturnGoods(String subSourceOrderCode, ReturnTable vo, HttpServletRequest request, HttpServletResponse response){
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
			vo.setSubSourceOrderCode(subSourceOrderCode);
			ServiceResult<List<ReturnTable>> result =cnDataDirectPushCenterService.findReturnGoods(vo);
			if (result.getSuccess() && result.getResult() != null) {
				List<ReturnTable> predictstocklist = result.getResult();
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
	 * 手动匹配退货
	 * @param id
	 * @param response
	 * @throws IOException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("returnGoodsPushSAP")
	@ResponseBody
	public void returnGoodsPushSAP(String id,HttpServletResponse response) throws IOException, java.text.ParseException {
		String msg = cnDataDirectPushCenterService.returnGoodsPushSAP(id);
		response.addHeader("Content-type","text/html;charset=utf-8");
		response.getWriter().write(msg);
		response.getWriter().flush();
		response.getWriter().close();
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
	public Json SaveRepairs(@RequestBody OrderRepairsVo orderRepairs,HttpServletRequest request){
		Json josn = new Json();
		HttpSession session = request.getSession();
		String userName = Ustring.getString(session.getAttribute("userName"));
		josn = operationAreaService.SaveRepairs(orderRepairs,userName);
		return josn;
	}
	/**
	 * 跳转调拨残次操作日志页面
	 * @return
	 */
	@RequestMapping("allocateLog")
	public ModelAndView allocateLog(String id){
		ModelAndView mv = new ModelAndView();
		Map<String, Object> map=  omsInStoreRecordCenterService.operateLog(id); //查询退货页面详情

		Object list =map.get("list");

		mv.addObject("list",list);

		mv.setViewName("order/allocateDefectLogs");
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


}
