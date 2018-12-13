package com.haier.svc.api.controller.report_data;





import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.haier.afterSale.model.BadRate;
import com.haier.afterSale.model.ProductBadCount;
import com.haier.afterSale.model.ProductIndustry;
import com.haier.afterSale.service.ReportDataCenterService;
import com.haier.eis.model.VomInOutStoreOrder;
import com.haier.shop.model.BadProductInStorageAnaly;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.ProductCates;
import com.haier.shop.model.ProductToIndustry;
import com.haier.shop.model.PutAway;
import com.haier.stock.model.InvChannel2OrderSource;
import com.haier.stock.model.InvWarehouse;
import com.haier.stock.service.StockCenterInvWareHouseService;


/***
 *报表
 * @author 付振兴
 *
 */
@Controller
@RequestMapping(value ="report")
public class ReportDataController {

	@Autowired
	    private ReportDataCenterService reportDataCenterService;
	@Autowired
    private StockCenterInvWareHouseService stockCenterInvWareHouseService;

	/**
	 * 跳转不良品走势图
	 * 
	 * @param date
	 * @return
	 */
  @RequestMapping("/BadProductTrend")
  public String badProductTrend(Date date) {
	 
      return "report_data/badProductTrend";
  }
  /**
   * 处理数据返回不良品率折线图
   * 
   * @param date
   * @param response
   * @throws IOException
   */
  @RequestMapping(value = { "/findBadProductCount" }, method = { RequestMethod.POST })
  @ResponseBody
  public void findBadProductCount(Date date,HttpServletResponse response) throws IOException {
      response.setContentType("text/html;charset=utf-8");
      Calendar c = Calendar.getInstance();
      int year = c.get(Calendar.YEAR);
      int month = c.get(Calendar.MONTH)+1;
     
      
      String badrate = "";
      List<BadRate> list = new ArrayList<BadRate>();
     
      for(int i=1;i<=month;i++) {
    	  String begintime = year+"-"+i+"-"+"01";
          String endtime = year+"-"+i+"-"+"31";
        //根据数据库查询
      int count = reportDataCenterService.findBadProductCount(begintime,endtime);//当月不良品数量
      int ordernum = reportDataCenterService.findOrderNum(begintime, endtime);//当月订单数量
      double num = count;
	  if(ordernum != 0) {
	      double onum = ordernum;
	      DecimalFormat df = new DecimalFormat("0.00");//格式化小数   
	      double rate = (num/onum)*100; 
	       badrate = df.format(rate);//返回的是String类型
	  }
	  else{
	  	badrate = "0.0";
	  }
	      BadRate badRate = new BadRate(i+"月", badrate);
	      list.add(badRate);

      }
      ObjectMapper mapper = new ObjectMapper();    //提供java-json相互转换功能的类
      
      String json = mapper.writeValueAsString(list);    //将list中的对象转换为Json格式的数组
      response.getWriter().write(json);
      
  }
  /**
   * 跳转到产业的不良率
   * 
   * @return
   */
  @RequestMapping("/ToIndustryBadRate")
  public String toIndustryBadRate() {
      return "report_data/toIndustryBadRate";
  }
  /**
   * 查询每个产业的不良率
   * 
   * @param response
   * @throws IOException
   */
  @RequestMapping(value = { "/findBadRate" }, method = { RequestMethod.POST })
  @ResponseBody
  public void findBadRate(HttpServletResponse response) throws IOException {
	  		
      response.setContentType("text/html;charset=utf-8");
      
    //根据数据库查询
      List<ProductBadCount> list_product = new ArrayList<ProductBadCount>();
      List<ProductCates> list_industry = reportDataCenterService.findindustry();//查询产业
      List<ProductToIndustry> list_sign = reportDataCenterService.findsign();//揽收的不良品量
      List<ProductToIndustry> list_reject = reportDataCenterService.findreject();//拒收的不良品量

	  List<ProductCates> list_SevenProduct = new ArrayList<ProductCates>(); // 创建一个list 存七大产业的数据
	  for(ProductCates product :list_industry) {
		 if (product.getRootId() == 2723) {
			 list_SevenProduct.add(product);
		 }
		 if (product.getRootId() == 2725) {
			 list_SevenProduct.add(product);
		 }
		 if (product.getRootId() == 2726) {
			 list_SevenProduct.add(product);
		 }
		 if (product.getRootId() == 2729) {
			 list_SevenProduct.add(product);
		 }
		 if (product.getRootId() == 2741) {
			 list_SevenProduct.add(product);
		 }
		 if (product.getRootId() == 2742) {
			 list_SevenProduct.add(product);
		 }
		 if (product.getRootId() == 2743) {
			 list_SevenProduct.add(product);
		 }
	 }
	 for(ProductCates sevenProduct:list_SevenProduct) {
         int signNum = 0;
         int rejectNum = 0;
		 ProductBadCount productBadCount = new ProductBadCount();
		 for (ProductToIndustry sign : list_sign) {
			 if (sign.getRootId() ==sevenProduct.getId()) {
				 signNum = sign.getSign();
			 }
		 }
		 for (ProductToIndustry reject : list_reject) {
				 if (reject.getRootId() ==sevenProduct.getId()) {
					 rejectNum = reject.getReject();
				 }
         }
			 productBadCount.setIndustry(sevenProduct.getCateName());
			 productBadCount.setReject(rejectNum);
			 productBadCount.setSign(signNum);
			 productBadCount.setTotal(signNum + rejectNum);
			 list_product.add(productBadCount);

	 }

      ObjectMapper mapper = new ObjectMapper();    //提供java-json相互转换功能的类
      
      String json = mapper.writeValueAsString(list_product);    //将list中的对象转换为Json格式的数组
      
      response.getWriter().write(json);
      
      
  }

  /**
   * 跳转到中心的不良品占比
   * 
   * @return
   */
  @RequestMapping("/ToCenterIndustryBadRate")
  public String toCenterIndustryBadRate() {
      return "report_data/toCenterIndustryBadRate";
  }
  
  /**
   *查询数据显示表格
   * 
   * @param response
   * @throws IOException
   */
  @RequestMapping(value = { "/findIndustry" }, method = { RequestMethod.POST })
  @ResponseBody
  public void findIndustry(HttpServletResponse response) throws IOException {
      response.setContentType("text/html;charset=utf-8");
      NumberFormat num = NumberFormat.getPercentInstance(); 

    //根据数据库查询
      List<ProductCates> list_sortcount = reportDataCenterService.findSortCount();//按类别分组的总数量
      List<OrderProducts> list_industrycount = reportDataCenterService.findIndustryCount();//按中心分组的数量
      List<ProductIndustry> list = new ArrayList<ProductIndustry>();
      List<InvWarehouse> list_center = stockCenterInvWareHouseService.findCenter();//查找42个工贸中心
      for(int k=0; k<list_center.size();k++) {
    	  ProductIndustry productIndustry = new ProductIndustry();
    	  //循环比对中心是否一致，然后判断产业
      for(int i=0; i<list_sortcount.size();i++) {
		      for(int j=0; j<list_industrycount.size();j++) {
                  double industrycount = 0.0;
                  double sortcount = 0.0;
                  double rate = 0.0;
				    		  if(k==0) {//第一次循环先确定不良品量 每个产业的
				    			  if(productIndustry.getCenterName()== null) {
				    			  productIndustry.setCenterName("不良品量");
				    			  }
				    			    if(list_sortcount.get(i).getRootId()== 2723) {
				        	        	   productIndustry.setFridge(String.valueOf(list_sortcount.get(i).getCount()));
				        	           }
				        	           if(list_sortcount.get(i).getRootId()== 2725) {
				        	        	   productIndustry.setWashingMach(String.valueOf(list_sortcount.get(i).getCount()));
				        	           }
				        	           if(list_sortcount.get(i).getRootId()== 2726) {
				        	        	   productIndustry.setFreezer(String.valueOf(list_sortcount.get(i).getCount()));
				        	           }
				        	           if(list_sortcount.get(i).getRootId()== 2729) {
				        	        	   productIndustry.setAirCondition(String.valueOf(list_sortcount.get(i).getCount()));
				        	           }
				        	           if(list_sortcount.get(i).getRootId()== 2741) {
				        	        	   productIndustry.setWaterHeater(String.valueOf(list_sortcount.get(i).getCount()));
				        	           }
				        	           if(list_sortcount.get(i).getRootId()== 2742) {
				        	        	   productIndustry.setKichenMach(String.valueOf(list_sortcount.get(i).getCount()));
				        	           }
				        	           if(list_sortcount.get(i).getRootId()== 2743) {
				        	        	   productIndustry.setColorTV(String.valueOf(list_sortcount.get(i).getCount()));
				        	           }
				        	            
				    		  }
				    		  else {//其他词循环 算到中心的不良率
				    	  if(list_industrycount.get(j).getSCode().equals(list_center.get(k).getEstorge_id())) {//判断中心名字
							  if (productIndustry.getCenterName() == null || !productIndustry.getCenterName().equals(list_center.get(k).getIndustry_trade_desc())) {

								  productIndustry.setCenterName(list_center.get(k).getIndustry_trade_desc());

							  }

		    			  	//找到对应的产业赋值给自定义对象productIndustry
		    	           if(list_industrycount.get(j).getRootId()== 2723 &&list_sortcount.get(i).getRootId()== 2723) {
				    			   industrycount = list_industrycount.get(j).getCount();
				    			   sortcount = list_sortcount.get(i).getCount();
				    		       rate = (industrycount/sortcount);
		    	        	   productIndustry.setFridge(num.format(rate)); //返回的是String类型
		    	           }
		    	           if(list_industrycount.get(j).getRootId()== 2725&&list_sortcount.get(i).getRootId()==2725) {
		    	        	   industrycount = list_industrycount.get(j).getCount();
			    			   sortcount = list_sortcount.get(i).getCount();
			    		       rate = (industrycount/sortcount);
	    	        	   productIndustry.setWashingMach(num.format(rate)); //返回的是String类型    
		    	           }
		    	           if(list_industrycount.get(j).getRootId()== 2726&&list_sortcount.get(i).getRootId()==2726) {
		    	        	   industrycount = list_industrycount.get(j).getCount();
			    			   sortcount = list_sortcount.get(i).getCount();
			    		       rate = (industrycount/sortcount);
	    	        	   productIndustry.setFreezer(num.format(rate)); //返回的是String类型    
		    	           }
		    	           if(list_industrycount.get(j).getRootId()== 2729&&list_sortcount.get(i).getRootId()==2729) {
		    	        	   industrycount = list_industrycount.get(j).getCount();
			    			   sortcount = list_sortcount.get(i).getCount();
			    		       rate = (industrycount/sortcount);
	    	        	   productIndustry.setAirCondition(num.format(rate)); //返回的是String类型     
		    	           }
		    	           if(list_industrycount.get(j).getRootId()== 2741&&list_sortcount.get(i).getRootId()==2741) {
		    	        	   industrycount = list_industrycount.get(j).getCount();
			    			   sortcount = list_sortcount.get(i).getCount();
			    		       rate = (industrycount/sortcount);
	    	        	   productIndustry.setWaterHeater(num.format(rate)); //返回的是String类型    
		    	           }
		    	           if(list_industrycount.get(j).getRootId()== 2742&&list_sortcount.get(i).getRootId()==2742) {
		    	        	   industrycount = list_industrycount.get(j).getCount();
			    			   sortcount = list_sortcount.get(i).getCount();
			    		       rate = (industrycount/sortcount);
	    	        	   productIndustry.setKichenMach(num.format(rate)); //返回的是String类型     
		    	           }
		    	           if(list_industrycount.get(j).getRootId()== 2743&&list_sortcount.get(i).getRootId()==2743) {
		    	        	   industrycount = list_industrycount.get(j).getCount();
			    			   sortcount = list_sortcount.get(i).getCount();
			    		       rate = (industrycount/sortcount);
	    	        	   productIndustry.setColorTV(num.format(rate)); //返回的是String类型   
		    	           }
		    	            
		    		  }
				    		      
		    	  }
		    	  }
		      
		      }
      //没有中心不加入list中
      	if(productIndustry.getCenterName() != null) {
      		list.add(productIndustry);
      	}
    	  
      }
    	  
      //ObjectMapper mapper = new ObjectMapper();    //提供java-json相互转换功能的类
  Map<String,Object> map = new HashMap<String,Object>();
 	Gson gson=new Gson();
	response.addHeader("Content-type","text/html;charset=utf-8");
	map.put("total", list.size());
	map.put("rows", list);
	response.getWriter().write(gson.toJson(map));
	response.getWriter().flush();
	response.getWriter().close();
      
      
      
  }  
  
  /**
   * 跳转机损入库界面
   * 
   * @return
   */
  @RequestMapping("/MachDamageMonitor")
  public String machDamageMonitor() {
	  		//根据数据库查询
      return "report_data/machDamageMonitor";
  }
  /**
   * 机损对应的数据查询
   * 
   * @param response
   * @throws IOException
   */
  @RequestMapping(value = { "/findmach" }, method = { RequestMethod.POST })
  @ResponseBody
  public void findmach(HttpServletResponse response) throws IOException {
	  		//根据数据库查询
      response.setContentType("text/html;charset=utf-8");
      List<PutAway> list_mach = reportDataCenterService.findmach();
      List<InvWarehouse> list_center = stockCenterInvWareHouseService.findCenter();
      List<VomInOutStoreOrder> list_eis = reportDataCenterService.findInTime();
      
      //在InvWarehouse匹配中心和库位
      for(PutAway put :list_mach) {
    	  for(InvWarehouse inv:list_center) {
    		  if(put.getSecCode().equals(inv.getEstorge_id())){
    			  put.setCenterName(inv.getIndustry_trade_desc());
    			  list_mach.add(put);
    		  }
    	  }
      }  
      // 在VomInOutStoreOrder表中根据网单号匹配入库完成时间
      for(PutAway put :list_mach) {
    	  for(VomInOutStoreOrder vom:list_eis) {
    		  if(put.getNetListNum().equals(vom.getOrderNo())) {
    			  put.setPushCompleteDate(vom.getAddTime());
    		  }
    	  }
    	  if(put.getStorageType() != 22) {//入库产生日期是入10库或者21库的时间所以加入判断
    		  put.setPushWarehouseProduceDate(put.getAddtimeTs());
    	  }
    	  if(put.getStorageDate() != null) {//不为空才有周期
    	  long day=(new Date().getTime()-put.getStorageDate().getTime())/(24*60*60*1000);
    	  
    	  put.setPeriod(day);
    	  }
      }
    Map<String,Object> map = new HashMap<String,Object>();
 	Gson gson=new Gson();
	response.addHeader("Content-type","text/html;charset=utf-8");
	map.put("total", list_mach.size());
	map.put("rows", list_mach);
	response.getWriter().write(gson.toJson(map));
	response.getWriter().flush();
	response.getWriter().close();
  
  }  
  
  //箱损功能暂时不要了 先注释上
 /* @RequestMapping("/BoxDamageMonitor")
  public String boxDamageMonitor() {
	  		//根据数据库查询
      return "report_data/boxDamageMonitor";
  }
  
  @RequestMapping(value = { "/findbox" }, method = { RequestMethod.POST })
  @ResponseBody
  public void findbox(HttpServletResponse response) throws IOException {
	  		//根据数据库查询
      response.setContentType("text/html;charset=utf-8");
      
     
      List<PutAway> list_box = reportDataCenterService.findbox();
      List<InvWarehouse> list_center = stockCenterInvWareHouseService.findCenter();
      List<VomInOutStoreOrder> list_eis = reportDataCenterService.findInTime();
      for(PutAway put :list_box) {
    	  for(InvWarehouse inv:list_center) {
    		  if(put.getSecCode().equals(inv.getEstorge_id())){
    			  put.setCenterName(inv.getIndustry_trade_desc()); 
    			  put.setWarehouseLocation(inv.getEstorge_name());
    		  }
    	  }
      } 
      for(PutAway put :list_box) {
    	  for(VomInOutStoreOrder vom:list_eis) {
    		  if(put.getNetListNum().equals(vom.getOrderNo())) {
    			  put.setPushCompleteDate(vom.getAddTime());
    		  }
    	  }
    	  long day=(new Date().getTime()-put.getPushWarehouseProduceDate().getTime())/(24*60*60*1000);
    	  
    	  put.setPeriod(day);
      }
    Map<String,Object> map = new HashMap<String,Object>();
 	Gson gson=new Gson();
	response.addHeader("Content-type","text/html;charset=utf-8");
	map.put("total", "50");
	map.put("rows", list_box);
	response.getWriter().write(gson.toJson(map));
	response.getWriter().flush();
	response.getWriter().close();
   
  }  
  */

  /**
   * 跳转wa箱损库存监控页面(换箱处理过程监控)
   * 
   * @return
   */
  @RequestMapping("/WABoxDamageDealMonitor")
  public String WABoxDamageDealMonitor() {
	  		//根据数据库查询
      return "report_data/WABoxDamageDealMonitor";
  }
  
  /**
   * 
   * 
   * @param response
   * @throws IOException
   */
  @RequestMapping(value = { "/findwabox" }, method = { RequestMethod.POST })
  @ResponseBody
  public void findwabox(HttpServletResponse response) throws IOException {
	  		//根据数据库查询
      response.setContentType("text/html;charset=utf-8");
      
     
      List<PutAway> list_box = reportDataCenterService.findbox();//查询箱损
      List<InvWarehouse> list_center = stockCenterInvWareHouseService.findCenter();
      List<VomInOutStoreOrder> list_eis = reportDataCenterService.findInTime();//查询出库时间
      for(PutAway put :list_box) {
    	  for(InvWarehouse inv:list_center) {
    		  if(put.getSecCode().equals(inv.getEstorge_id())){
    			  put.setCenterName(inv.getIndustry_trade_desc()); 
    			  put.setWarehouseLocation(inv.getEstorge_name());
    		  }
    	  }
      } 
      for(PutAway put :list_box) {
    	  for(VomInOutStoreOrder vom:list_eis) {
    		  if(put.getNetListNum().equals(vom.getOrderNo())) {
    			  put.setPushCompleteDate(vom.getAddTime());
    		  }   		  
    	  }
    	  if(put.getStorageType() == 10) {//入10库时间也就是入库单产生日期
    		  put.setPushWarehouseProduceDate(put.getAddtimeTs());
    	  }
    	  if(put.getStorageType() == 21) {//入21库时间也就是入库单产生日期
    		  put.setPushWarehouseProduceDate(put.getAddtimeTs());
    	  }
    	  if(put.getStorageType() == 22) {//22库时间是入库时间
    		  put.setStorageDate(put.getAddtimeTs());
    	  }
    	  if(put.getStorageDate() != null) {//周期设置
    	  long day=(new Date().getTime()-put.getStorageDate().getTime())/(24*60*60*1000);
    	  
    	  put.setPeriod(day);
    	  }
      }
    Map<String,Object> map = new HashMap<String,Object>();
 	Gson gson=new Gson();
	response.addHeader("Content-type","text/html;charset=utf-8");
	map.put("total", list_box.size());
	map.put("rows", list_box);
	response.getWriter().write(gson.toJson(map));
	response.getWriter().flush();
	response.getWriter().close();
   
  }  
 
  
  //换箱未入库监控
  @RequestMapping("/WABoxDamageNotInStock")
  public String WABoxDamageNotInStock() {
	  		//根据数据库查询
      return "report_data/WABoxDamageNotInStock";
  }
  
  /**
   * 
   * 
   * @param response
   * @throws IOException
   */
  @RequestMapping(value = { "/findNotInStock" }, method = { RequestMethod.POST })
  @ResponseBody
  public void findNotInStock(HttpServletResponse response) throws IOException {
	  		//根据数据库查询
      response.setContentType("text/html;charset=utf-8");
      
      List<ProductCates> list_industry = reportDataCenterService.findindustry();//7大产业
      List<PutAway> list_notInStock = reportDataCenterService.findnotinstock();//查询未入库
      List<InvWarehouse> list_center = stockCenterInvWareHouseService.findCenter();
      String outnumber = reportDataCenterService.findoutnum();
      for(PutAway put :list_notInStock) { 
    	  for(InvWarehouse inv:list_center) {
    		  if(put.getSecCode().equals(inv.getEstorge_id())){
    			  put.setCenterName(inv.getIndustry_trade_desc()); 
    			  put.setWarehouseLocation(inv.getEstorge_name());
    		  }
    	  }
    	  


    		  for(ProductCates product :list_industry) {
    		  if(product.getId()== put.getRootId()) {//判断产业是否相等
    			  put.setIndustry(product.getCateName());
    		  }
    	  }


    	  if(put.getStorageType() == 10) {//入10库时间也就是入库单产生日期
    		  put.setPushWarehouseProduceDate(put.getAddtimeTs());
    		  put.setGenuineWarehouseNum(put.getPushWarehouseNum());
    	  }
    	  if(put.getStorageType() == 21) {//入21库时间也就是入库单产生日期
    		  put.setPushWarehouseProduceDate(put.getAddtimeTs());
    		  put.setUndesirableWarehouseNum(put.getPushWarehouseNum());
    	  }
    	  if(put.getStorageType() == 22) {//22库时间是入库时间
    		  put.setStorageDate(put.getAddtimeTs());
    	  }
    	  if(put.getPushWarehouseProduceDate() != null) {//周期设置
    	  long day=(new Date().getTime()-put.getPushWarehouseProduceDate().getTime())/(24*60*60*1000);
    	  
    	  put.setPeriod(day);
    	  put.setOutNum(outnumber);
    	  }
      }
    Map<String,Object> map = new HashMap<String,Object>();
 	Gson gson=new Gson();
	response.addHeader("Content-type","text/html;charset=utf-8");
	map.put("total", list_notInStock.size());
	map.put("rows", list_notInStock);
	response.getWriter().write(gson.toJson(map));
	response.getWriter().flush();
	response.getWriter().close();
   
  }  
  
  @RequestMapping("/rejectNotFitWarning")
  public String rejectNotFitWarning() {
	  		//根据数据库查询
      return "report_data/rejectNotFitWarning";
  }
  
  /**
   * 
   * 
   * @param response
   * @throws IOException
   */
  @RequestMapping(value = { "/findNotFit" }, method = { RequestMethod.POST })
  @ResponseBody
  public void findNotFit(HttpServletResponse response) throws IOException {
	  		//根据数据库查询
      response.setContentType("text/html;charset=utf-8");
      
      List<PutAway> list_notfit = reportDataCenterService.findNotFit();
      List<InvWarehouse> list_center = stockCenterInvWareHouseService.findCenter();
      for(PutAway put :list_notfit) { 
    	  for(InvWarehouse inv:list_center) {
    		  if(put.getSecCode().equals(inv.getEstorge_id())){
    			  put.setCenterName(inv.getIndustry_trade_desc()); 
    			  put.setWarehouseLocation(inv.getEstorge_name());
    		  }
    	  }
    	  if(put.getHandleStatus() == 6) {
    		  put.setReversePush("是");
    	  }
    	  else {
    		  put.setReversePush("否");
    	  }
    	  if(put.getCheckType() == 1) {//第一次质检
    		  put.setNetPointTime(put.getPushTime());
    		  put.setOneInspectorNum(put.getWoId());
    		  //判断质检结果
    		  if(put.getQuality() == 1) {
    			  put.setRejectOneResult("未开箱");	
    		  }
			  if(put.getQuality() == 2) {
			     put.setRejectOneResult("已开箱正品");			  
			    		  }
			  if(put.getQuality() == 5) {
				  put.setRejectOneResult("不良品换机");	
			  }
			  if(put.getQuality() == 6) {
				  put.setRejectOneResult("不良品退机");	
			  }
    	  }
      }
    Map<String,Object> map = new HashMap<String,Object>();
 	Gson gson=new Gson();
	response.addHeader("Content-type","text/html;charset=utf-8");
	int count = reportDataCenterService.notfitcount();
	map.put("total", count);
	map.put("rows", list_notfit);
	response.getWriter().write(gson.toJson(map));
	response.getWriter().flush();
	response.getWriter().close();
   
  }  
  
  @RequestMapping("/WABoxNoChangeWarning")
  public String WABoxNoChangeWarning() {
	  		//根据数据库查询
      return "report_data/WABoxNoChangeWarning";
  }
  
  /**
   * 
   * 
   * @param response
   * @throws IOException
   */
  @RequestMapping(value = { "/findNoChangeWarning" }, method = { RequestMethod.POST })
  @ResponseBody
  public void findNoChangeWarning(HttpServletResponse response) throws IOException {
	  		//根据数据库查询
      response.setContentType("text/html;charset=utf-8");
      
      List<PutAway> list_notchange = reportDataCenterService.findNoChangeWarning();
      List<InvWarehouse> list_center = stockCenterInvWareHouseService.findCenter();
      for(PutAway put :list_notchange) { 
    	  for(InvWarehouse inv:list_center) {
    		  if(put.getSecCode().equals(inv.getEstorge_id())){
    			  put.setCenterName(inv.getIndustry_trade_desc()); 
    			  put.setWarehouseLocation(inv.getEstorge_name());
    		  }
    	  }
    	  if(put.getStorageType() == 22) {//22库时间是入库时间
    		  put.setStorageDate(put.getAddtimeTs());
    	  }
    	  if(put.getCheckType() == 1) {//第一次质检
    		  put.setNetPointTime(put.getPushTime());
    		  put.setOneInspectorNum(put.getWoId());
    		  //判断质检结果
    		  if(put.getQuality() == 1) {
    			  put.setRejectOneResult("未开箱");	
    		  }
			  if(put.getQuality() == 2) {
			     put.setRejectOneResult("已开箱正品");			  
			    		  }
			  if(put.getQuality() == 5) {
				  put.setRejectOneResult("不良品换机");	
			  }
			  if(put.getQuality() == 6) {
				  put.setRejectOneResult("不良品退机");	
			  }
    	  }
    	  if(put.getCheckType() == 2) {//第二次质检
    		  
    		  //判断质检结果
    		  if(put.getQuality() == 1) {
    			  put.setTwoInspectorResult("未开箱");	
    		  }
			  if(put.getQuality() == 2) {
			     put.setTwoInspectorResult("已开箱正品");			  
			    		  }
			  if(put.getQuality() == 5) {
				  put.setTwoInspectorResult("不良品换机");	
			  }
			  if(put.getQuality() == 6) {
				  put.setTwoInspectorResult("不良品退机");	
			  }
    	  }
    	
      }
    Map<String,Object> map = new HashMap<String,Object>();
 	Gson gson=new Gson();
	response.addHeader("Content-type","text/html;charset=utf-8");
	map.put("total", list_notchange.size());
	map.put("rows", list_notchange);
	response.getWriter().write(gson.toJson(map));
	response.getWriter().flush();
	response.getWriter().close();
   
  }  
  
  /**
   * 跳转未入库预警
   * @return
   */
  @RequestMapping("/NotInStockWarning")
  public String NotInStockWarning() {
      return "report_data/NotInStockWarning";
  }
  
  /**
   * 
   * 
   * @param response
   * @throws IOException
   */
  @RequestMapping(value = { "/findNotInStockWarning" }, method = { RequestMethod.POST })
  @ResponseBody
  public void findNotInStockWarning(HttpServletResponse response) throws IOException {
	  		//根据数据库查询
      response.setContentType("text/html;charset=utf-8");
      
      List<PutAway> list_notstock = reportDataCenterService.findNotInStockWarning();
      List<InvWarehouse> list_center = stockCenterInvWareHouseService.findCenter();
      for(PutAway put :list_notstock) { 
    	  for(InvWarehouse inv:list_center) {
    		  if(put.getSecCode().equals(inv.getEstorge_id())){
    			  put.setCenterName(inv.getIndustry_trade_desc()); 
    			  put.setWarehouseLocation(inv.getEstorge_name());
    		  }
    	  }
    	  if(put.getCheckType() == 1) {//第一次质检
    		  put.setOnePushTime(put.getPushTime());
    	  }
      }
    Map<String,Object> map = new HashMap<String,Object>();
 	Gson gson=new Gson();
	response.addHeader("Content-type","text/html;charset=utf-8");
	map.put("total", list_notstock.size());
	map.put("rows", list_notstock);
	response.getWriter().write(gson.toJson(map));
	response.getWriter().flush();
	response.getWriter().close();
   
  }  
  
  @RequestMapping("/3WBadProductInStorageAnaly")
  public String BadProductInStorageAnaly() {
      return "report_data/3WBadProductInStorageAnaly";
  }

  @RequestMapping(value = { "/findBadProductAnaly" }, method = { RequestMethod.POST })
  @ResponseBody
  public void findBadProductAnaly(HttpServletResponse response) throws IOException {
	  		
      response.setContentType("text/html;charset=utf-8");
      ProductBadCount productBadCount = new ProductBadCount();
    //根据数据库查询
      List<ProductCates> list_industry = reportDataCenterService.findindustry();//查询不良品量按产业分组
      List<BadProductInStorageAnaly> list_in = reportDataCenterService.HasInStock();
      List<BadProductInStorageAnaly> list_out = reportDataCenterService.NotInStock();
      int signNum = 0;
      int rejectNum = 0;
   // 查询source来源放入map中
   			Map <String,String> map = new HashMap<String,String>();
   			List<InvChannel2OrderSource> list_source = reportDataCenterService.getAllOrder2ChannelSource();
   			for(InvChannel2OrderSource source :list_source) {
   				map.put(source.getOrderSource(), source.getNote());
   			}
     for(ProductCates product :list_industry) {
    	
    	 	 productBadCount.setReject(rejectNum);
    	 	 productBadCount.setSign(signNum);
    	 	 productBadCount.setTotal(signNum+rejectNum);
     }

      ObjectMapper mapper = new ObjectMapper();    //提供java-json相互转换功能的类
      
      String json = mapper.writeValueAsString(list_industry);    //将list中的对象转换为Json格式的数组
      
      response.getWriter().write(json);
      
      
  }
}