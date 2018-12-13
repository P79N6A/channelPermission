package com.haier.svc.api.controller.pop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.haier.common.PagerInfo;
import com.haier.distribute.data.model.Regions;
import com.haier.distribute.data.model.TChannelsInfo;
import com.haier.distribute.data.model.TOrdersManual;
import com.haier.distribute.data.model.TOrdersManualProducts;
import com.haier.distribute.service.DistributeCenterPopOrderService;
import com.haier.distribute.service.DistributeCenterWareHouseService;
import com.haier.distribute.service.ManualInputOrderProductsService;
import com.haier.distribute.service.ManualInputsOrderService;
import com.haier.order.model.Ustring;

@Controller
@RequestMapping("manualInputOrder")
public class ManualInputOrderController {
	
	private org.apache.log4j.Logger logger       = org.apache.log4j.LogManager.getLogger(this
            .getClass());
	
	@Autowired
	ManualInputsOrderService manualInputsOrderService;
	
	@Autowired
	ManualInputOrderProductsService manualInputOrderProductsService;
	@Autowired
	DistributeCenterPopOrderService distributeCenterPopOrderService;
	@Autowired 
	DistributeCenterWareHouseService distributeCenterWareHouseService;
	
	@RequestMapping(value = { "/getmanualInputOrder" }, method = { RequestMethod.GET })
	public ModelAndView getManualInputOrder() {
		
		ModelAndView mode=new ModelAndView();
		
		mode.setViewName("pop/distribute/manualInputOrder");
		
		return mode;
	}
	
	@RequestMapping(value = { "/getRegions" }, method = { RequestMethod.GET })
	@ResponseBody
	public String getRegions() {
		
		List<Regions> list = distributeCenterWareHouseService.getRegionsAll();
		JSONObject json=new JSONObject();
		json.put("rows", list);
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(list);
		return json.toString();
	}
	@RequestMapping(value = { "/getChannel" }, method = { RequestMethod.GET })
	@ResponseBody
	public JSONObject getChannel() {
		
		TChannelsInfo tChanneclsInfo=new TChannelsInfo();
		int page = 1;
		int rows=50;
		
        PagerInfo pager = new PagerInfo(rows, page);
		JSONObject jsonobject=distributeCenterPopOrderService.channelList(pager,tChanneclsInfo);
		return jsonobject;
	}
	@RequestMapping(value = { "/getManualInputOrderAll" }, method = { RequestMethod.POST })
	@ResponseBody
    public JSONObject getManualInputOrderAll(@RequestParam(required = false) Integer pageIndex,
							          @RequestParam(required = false) String startDate,
	                                  @RequestParam(required = false) String endDate,
	                                  @RequestParam(required = false) String channelCode,
	                                  @RequestParam(required = false) String orderSn,
	                                  @RequestParam(required = false) String orderStatus,
	                                  @RequestParam(required = false) String invoiceType,
	                                  
							          Map<String, Object> modelMap,HttpServletRequest request,
							          HttpServletResponse response){
		JSONObject json = new JSONObject();
		  int size = Integer.parseInt(Ustring.getString0(request.getParameter("size")));
	      int start =Integer.parseInt(Ustring.getString0(request.getParameter("start")));
		try{
			Assert.notNull(manualInputsOrderService, "Property 'baseInventoryModel' is required.");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if(orderStatus.equals("-1") ) {
				orderStatus = "";
			}
			paramMap=handleParameters(paramMap,startDate,endDate,channelCode,orderSn,orderStatus, 
					invoiceType);
			
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
			List<Map<String, Object>> lists=manualInputsOrderService.getManualInputOrderAll(paramMap);
			pager.setRowsCount(manualInputsOrderService.getManualInputOrderAllCount(paramMap));
		json.put("total", pager.getRowsCount());
		json.put("rows", lists);
		}catch(Exception e){
			logger.error("[ManualInputOrderController_getManualInputOrderAll]查询库存时发生未知错误", e);
		}

	    return json;
	}
	

	public Map<String, Object> handleParameters(Map<String, Object> modelMap, String startDate,String endDate, 
			String channelCode,String orderSn,String orderStatus, String invoiceType ){
		if(StringUtils.isNotEmpty(channelCode)){
			modelMap.put("channelCode", channelCode);
		}
		if(StringUtils.isNotEmpty(orderSn)){
			modelMap.put("orderSn", orderSn);
		}
		if(StringUtils.isNotEmpty(startDate)){
			modelMap.put("startDate", startDate);
		}
		if(StringUtils.isNotEmpty(endDate)){
			modelMap.put("endDate", endDate);
		}
		if(StringUtils.isNotEmpty(orderStatus)){
			modelMap.put("orderStatus", orderStatus);
		}
		if(StringUtils.isNotEmpty(invoiceType)){
			modelMap.put("invoiceType", invoiceType);
		}
        
        return modelMap;
		
	}
	
	@RequestMapping(value = { "/saveManualInputOrder" }, method = { RequestMethod.POST })
	@ResponseBody
	public Integer saveManualInputOrder(
			  @RequestParam(required = false) String id,
			  @RequestParam(required = false) String orderFee,
	          @RequestParam(required = false) String notes,
              @RequestParam(required = false) String payTime,
              @RequestParam(required = false) String name,
              @RequestParam(required = false) String mobile,
              @RequestParam(required = false) String phone,
              @RequestParam(required = false) String zip,
              @RequestParam(required = false) String province,
              @RequestParam(required = false) String city,
              @RequestParam(required = false) String county,
              @RequestParam(required = false) String address,
              @RequestParam(required = false) String invoiceType,
              @RequestParam(required = false) String invoiceTitle,
              @RequestParam(required = false) String districtCode,
              Map<String, Object> modelMap,HttpServletRequest request,
	          HttpServletResponse response) {
		
		TOrdersManual tOrdersManual=new TOrdersManual();
		if(StringUtils.isNotEmpty(districtCode)){
			tOrdersManual.setDistrictCode(districtCode);
		}
		if(StringUtils.isNotEmpty(id)){
			Integer ids=Integer.valueOf(id);
			tOrdersManual.setId(ids);
		}
		if(StringUtils.isNotEmpty(orderFee)){
			int orderFees=Integer.valueOf(orderFee);
			tOrdersManual.setOrderFee(orderFees);
		}
		if(StringUtils.isNotEmpty(payTime)){
			tOrdersManual.setPayTime(payTime);
		}
		if(StringUtils.isNotEmpty(notes)){
			tOrdersManual.setNotes(notes);
		}
		if(StringUtils.isNotEmpty(name)){
			tOrdersManual.setName(name);
		}
		if(StringUtils.isNotEmpty(mobile)){
			tOrdersManual.setMobile(mobile);
		}
		if(StringUtils.isNotEmpty(phone)){
			tOrdersManual.setPhone(phone);
		}
		if(StringUtils.isNotEmpty(zip)){
			tOrdersManual.setZip(zip);
		}
		if(StringUtils.isNotEmpty(province)){
			tOrdersManual.setProvince(province);
		}
		if(StringUtils.isNotEmpty(city)){
			tOrdersManual.setCity(city);
		}
		if(StringUtils.isNotEmpty(county)){
			tOrdersManual.setDistrict(county);
		}
		
		if(StringUtils.isNotEmpty(address)){
			tOrdersManual.setAddress(address);
		}
		if(StringUtils.isNotEmpty(invoiceType)){
			int invoiceTypes=Integer.valueOf(invoiceType);
			tOrdersManual.setInvoiceType(invoiceTypes);
		}
		if(StringUtils.isNotEmpty(invoiceTitle)){
			tOrdersManual.setInvoiceTitle(invoiceTitle);
		}
		HttpSession session = request.getSession();
        String createBy=(String)session.getAttribute("userName");
		tOrdersManual.setUpdateBy(createBy);
		if(tOrdersManual !=null) {
			int ids =Integer.valueOf(id);
			List<Map<String, Object>> lists=manualInputOrderProductsService.getManualInputOrderProductsbyOrderId(ids);
			net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(lists);
			tOrdersManual.setProductsInfo( jsonArray.toString());
		int a=manualInputsOrderService.updateManualInputOrder(tOrdersManual);
		return a;
		}
		
		return 0;
	}
	@RequestMapping(value = { "/insertManualInputOrder" }, method = { RequestMethod.POST })
	@ResponseBody
	public Integer insertManualInputOrder(
			  @RequestParam(required = false) String id,
			  @RequestParam(required =false)  String orderSn,
			  @RequestParam(required =false)  String channelCode,
			  @RequestParam(required = false) String orderFee,
	          @RequestParam(required = false) String notes,
              @RequestParam(required = false) String payTime,
              @RequestParam(required = false) String name,
              @RequestParam(required = false) String mobile,
              @RequestParam(required = false) String phone,
              @RequestParam(required = false) String zip,
              @RequestParam(required = false) String province,
              @RequestParam(required = false) String city,
              @RequestParam(required = false) String county,
              @RequestParam(required = false) String address,
              @RequestParam(required = false) String invoiceType,
              @RequestParam(required = false) String invoiceTitle,
              @RequestParam(required = false) String districtCode,
              Map<String, Object> modelMap,HttpServletRequest request,
	          HttpServletResponse response) {
		
		TOrdersManual tOrdersManual=new TOrdersManual();
		if(StringUtils.isNotEmpty(districtCode)){
			tOrdersManual.setDistrictCode(districtCode);
		}
		if(StringUtils.isNotEmpty(orderSn)){
			tOrdersManual.setOrderSn(orderSn);
		}
		if(StringUtils.isNotEmpty(channelCode)){
			tOrdersManual.setChannelCode(channelCode);
		}
		if(StringUtils.isNotEmpty(orderFee)){
			int orderFees=Integer.valueOf(orderFee);
			tOrdersManual.setOrderFee(orderFees);
			tOrdersManual.setPaidFee(orderFees);
		}
		if(StringUtils.isNotEmpty(payTime)){
			tOrdersManual.setPayTime(payTime);
		}
		if(StringUtils.isNotEmpty(notes)){
			tOrdersManual.setNotes(notes);
		}
		if(StringUtils.isNotEmpty(name)){
			tOrdersManual.setName(name);
		}
		if(StringUtils.isNotEmpty(mobile)){
			tOrdersManual.setMobile(mobile);
		}
		if(StringUtils.isNotEmpty(phone)){
			tOrdersManual.setPhone(phone);
		}
		if(StringUtils.isNotEmpty(zip)){
			tOrdersManual.setZip(zip);
		}
		if(StringUtils.isNotEmpty(province)){
			tOrdersManual.setProvince(province);
		}
		if(StringUtils.isNotEmpty(city)){
			tOrdersManual.setCity(city);
		}
		if(StringUtils.isNotEmpty(county)){
			tOrdersManual.setDistrict(county);
		}
		
		if(StringUtils.isNotEmpty(address)){
			tOrdersManual.setAddress(address);
		}
		if(StringUtils.isNotEmpty(invoiceType)){
			int invoiceTypes=Integer.valueOf(invoiceType);
			tOrdersManual.setInvoiceType(invoiceTypes);
		}
		if(StringUtils.isNotEmpty(invoiceTitle)){
			tOrdersManual.setInvoiceTitle(invoiceTitle);
		}
		HttpSession session = request.getSession();
        String createBy=(String)session.getAttribute("userName");
        
        tOrdersManual.setSyncStatus(0);
        tOrdersManual.setPaymentStatus(1);
		tOrdersManual.setCreateBy(createBy);
		if(tOrdersManual !=null) {
			
			//int ids =Integer.valueOf(id);
			List<Map<String, Object>> lists=manualInputOrderProductsService.getManualInputOrderProductsbyOrderId(0);
			
			net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(lists);
			tOrdersManual.setProductsInfo( jsonArray.toString());
		int a=manualInputsOrderService.insertManualInputOrder(tOrdersManual);
		TOrdersManualProducts tOrdersManualProducts=new TOrdersManualProducts();
		tOrdersManualProducts.setOrderId(a);
		manualInputOrderProductsService.updeteManualInputOrderProducts(tOrdersManualProducts);
		return a;
		}
		
	return 0;
	}
	
	@RequestMapping("/deleteManualInputOrderProducts" )
	@ResponseBody
	public int deleteManualInputOrderProducts(@RequestParam Integer id) {
		if(id !=null) {
			int a=manualInputOrderProductsService.deleteManualInputOrderProducts(id);
			return a;
		}else {
			
		}
		
		return 0;
	}
	@RequestMapping(value = { "/insertManualInputOrderProducts" }, method = { RequestMethod.POST } )
	public @ResponseBody int insertManualInputOrderProducts(@RequestParam String sku,
			@RequestParam String productName,
			@RequestParam String price,
			@RequestParam String num,
			@RequestParam String orderId,HttpServletRequest request,
	          HttpServletResponse response) {
		
		TOrdersManualProducts tOrdersManualProducts=new TOrdersManualProducts();
		if(StringUtils.isNotEmpty(sku)){
			tOrdersManualProducts.setSku(sku);
		}
		if(StringUtils.isNotEmpty(productName)){
			tOrdersManualProducts.setProductName(productName);
		}
		if(StringUtils.isNotEmpty(price)){
			int prices=Integer.valueOf(price);
			tOrdersManualProducts.setPrice(prices);
		}
		if(StringUtils.isNotEmpty(num)){
			int nums=Integer.valueOf(num);
			tOrdersManualProducts.setNum(nums);
		}
		if(StringUtils.isNotEmpty(orderId)){
			int orderIds=Integer.valueOf(orderId);
			tOrdersManualProducts.setOrderId(orderIds);;
		}
		HttpSession session = request.getSession();
        String createBy=(String)session.getAttribute("userName");
        tOrdersManualProducts.setCreateBy(createBy);
		if(tOrdersManualProducts!=null) {
			int results=manualInputOrderProductsService.inserManualInputOrderProducts(tOrdersManualProducts);
			return results;
		}
		
		return 0;
		
		
		
	}
	@RequestMapping(value = {"/updateManualInputOrderProducts"}, method = { RequestMethod.POST } )
	public @ResponseBody int updateManualInputOrderProducts(@RequestParam String sku,
			@RequestParam String id,
			@RequestParam String productName,
			@RequestParam String price,
			@RequestParam String num,HttpServletRequest request,
	          HttpServletResponse response) {
		
		TOrdersManualProducts tOrdersManualProducts=new TOrdersManualProducts();
		if(StringUtils.isNotEmpty(sku)){
			tOrdersManualProducts.setSku(sku);
		}
		if(StringUtils.isNotEmpty(id)){
			tOrdersManualProducts.setId(Integer.parseInt(id));
		}
		if(StringUtils.isNotEmpty(productName)){
			tOrdersManualProducts.setProductName(productName);
		}
		if(StringUtils.isNotEmpty(price)){
			Integer prices=Integer.parseInt(price);
			tOrdersManualProducts.setPrice(prices);
		}
		if(StringUtils.isNotEmpty(num)){
			Integer nums=Integer.parseInt(num);
			tOrdersManualProducts.setNum(nums);
		}
		HttpSession session = request.getSession();
        String updateBy=(String)session.getAttribute("userName");
        tOrdersManualProducts.setUpdateBy(updateBy);
       Integer results = null;
		if(tOrdersManualProducts!=null) {
			results=manualInputOrderProductsService.updeteManualInputOrderProducts(tOrdersManualProducts);
		}
		return results;
		
		
		
		
	}
	@RequestMapping(value = {"/getManualInputOrderProductsbyOrderId"}, method = { RequestMethod.POST } )
	@ResponseBody
	public  JSONObject getManualInputOrderProductsbyOrderId(@RequestParam String orderId,
HttpServletRequest request,HttpServletResponse response) {

		JSONObject json = new JSONObject();
		 int size = Integer.parseInt(Ustring.getString0(request.getParameter("sizes")));
	      int start =Integer.parseInt(Ustring.getString0(request.getParameter("starts")));
		try{
			Assert.notNull(manualInputsOrderService, "Property 'baseInventoryModel' is required.");
			Map<String, Object> paramMap = new HashMap<String, Object>();
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
	        	 paramMap.put("starts",0);
	        }else {
	        	 paramMap.put("starts", pager.getPageIndex());
	        }
	        paramMap.put("sizes", pager.getPageSize());
			
	        //paramMap.put("size", pager.getPageSize());
	        if(StringUtils.isNotEmpty(orderId)){
				int orderIds=Integer.valueOf(orderId);
				List<Map<String, Object>> lists=manualInputOrderProductsService.getManualInputOrderProductsbyOrderId(orderIds);
				pager.setRowsCount(manualInputOrderProductsService.getManualInputOrderProductsbyOrderIdCount(orderIds));
				json.put("total", pager.getRowsCount());
				json.put("rows", lists);
	        }
		}catch(Exception e){
			logger.error("[ManualInputOrderController_getManualInputOrderAll]查询库存时发生未知错误", e);
		}
		return json;
		
	}
	
	
	@RequestMapping(value = { "/findProduct" }, method = { RequestMethod.POST })
	@ResponseBody
	public JSONObject findProduct(@RequestParam String id) {
		JSONObject jsonobject=manualInputOrderProductsService.findProduct(Integer.parseInt(id));
		return jsonobject;
	}
	
	

}

