package com.haier.afterSale.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.haier.afterSale.model.CargoStatus;
import com.haier.afterSale.model.Json;
import com.haier.afterSale.model.MemberInvoicesStatus;
import com.haier.afterSale.model.OrderRepairsStatus;
import com.haier.afterSale.model.PaymentStatus;
import com.haier.afterSale.model.Ustring;
import com.haier.afterSale.model.VOMOrderModel;
import com.haier.afterSale.service.OperationAreaService;
import com.haier.afterSale.util.Base64Util;
import com.haier.afterSale.util.MD5util;
import com.haier.afterSale.webService.pushHP.InsertDataToHP;
import com.haier.afterSale.webService.pushHP.InsertDataToHP_Service;
import com.haier.afterSale.webService.pushHP.InsertDataToHP_Type;
import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.eis.model.EisExternalSku;
import com.haier.eis.service.EisExternalSkuService;
import com.haier.shop.model.InvoiceConst;
import com.haier.shop.model.Invoices;
import com.haier.shop.model.OrderProductsVo;
import com.haier.shop.model.OrderRepairLESRecords;
import com.haier.shop.model.OrderRepairLogs;
import com.haier.shop.model.OrderRepairsVo;
import com.haier.shop.model.OrderRepairshpLogs;
import com.haier.shop.model.OrderTmallReturnLogs;
import com.haier.shop.model.OrderhpRejectionLogs;
import com.haier.shop.model.OrderrepairHPrecords;
import com.haier.shop.model.OrdersVo;
import com.haier.shop.model.VOMSynOrderRequire;
import com.haier.shop.model.VOMSynSubOrderRequire;
import com.haier.shop.service.ShopDepartMentCodeSkuService;
import com.haier.shop.service.ShopInvoiceService;
import com.haier.shop.service.ShopOperationAreaService;
import com.haier.shop.service.ShopOrderRepairLesreCordsService;
import com.haier.shop.service.ShopOrderRepairLogsService;
import com.haier.shop.service.ShopOrderRepairsService;
import com.haier.shop.service.ShopOrderRepairshpLogsService;
import com.haier.shop.service.ShopOrderTmallReturnLogsService;
import com.haier.shop.service.ShopOrderVOMReturnAnalysisDetailedService;
import com.haier.shop.service.ShopOrderVOMReturnAnalysisService;
import com.haier.shop.service.ShopOrderhpRejectionLogsService;
import com.haier.shop.service.ShopOrderrepairHPrecordsService;
import com.haier.shop.service.ShopOrdersService;
import com.haier.shop.service.ShopOrederVOMReturnLogsService;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.model.OrderProductStatus;
import com.haier.stock.service.StockInvMachineSetService;

import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;
/**
 * 网单
 * 吴坤洋 2017-10-25
 * @author wukunyang
 *
 */
@Service
public class OperationAreaServiceImpl implements OperationAreaService{
	private static org.apache.log4j.Logger log            = org.apache.log4j.LogManager.getLogger(OperationAreaServiceImpl.class);
	   private static final Logger logger = LogManager.getLogger(OperationAreaServiceImpl.class);
    private static final QName SERVICE_NAME = new QName("http://www.example.org/InsertDataToHP/", "InsertDataToHP");
    @Autowired
    private ShopInvoiceService shopInvoiceService;
	@Autowired
	private ShopOperationAreaService shopOperationAreaService;

	@Autowired
	private ShopOrderRepairsService shopOrderRepairsService;//退货

	@Autowired
	private ShopOrderRepairLogsService shopOrderRepairLogsService;//退货日志

	@Autowired
	private ShopOrderrepairHPrecordsService shopOrderrepairHPrecordsService;//hp回传数据

	@Autowired
	private ShopOrderRepairLesreCordsService shopOrderRepairLesreCordsService;//出入库状态

	@Autowired
	private ShopOrderhpRejectionLogsService shopOrderhpRejectionLogsService;

	@Autowired
	private ShopOrderTmallReturnLogsService shopOrderTmallReturnLogsService;

	@Autowired
	private ShopDepartMentCodeSkuService departMentCodeSkuService;

	@Autowired
	private StockInvMachineSetService stockInvMachineSetService;

	@Autowired
	private ShopOrdersService shopOrdersService;//订单

	@Autowired
	private ShopOrderRepairshpLogsService shopOrderRepairshpLogsService;

	@Autowired
	private ShopOrederVOMReturnLogsService shopOrederVOMReturnLogsService;

	@Autowired
	private ShopOrderVOMReturnAnalysisService shopOrderVOMReturnAnalysisService;

	@Autowired
	private ShopOrderVOMReturnAnalysisDetailedService shopOrderVOMReturnAnalysisDetailedService;
	@Autowired
    private StockInvMachineSetService         invMachineSetDao;
	@Autowired
	private VOMOrderModel vomOrderModel;
	@Autowired
	private EisExternalSkuService eisExternalSkuService;
    private String               wsdlLocation;

    
	@Override
	public ServiceResult<List<OrderProductsVo> > searchList(OrderProductsVo params) {
		// TODO Auto-generated method stub
		ServiceResult<List<OrderProductsVo>> result = new ServiceResult<List<OrderProductsVo>>();
		List<OrderProductsVo>  list =shopOperationAreaService.queryOderProductList(params);
		for(int i=0;i<list.size();i++){
			String ss ="";
			if(null!=list.get(i).getStatus()&&!"".equals(list.get(i).getStatus())){
				ss= OrderProductStatus.getByCode(Integer.parseInt(list.get(i).getStatus().toString())).getName();
			}
			list.get(i).setStatusTs(ss);
        }
		result.setResult(list);
		PagerInfo pi = new PagerInfo();
		pi.setRowsCount(shopOperationAreaService.findOrderProductCNT(params));
		result.setPager(pi);
		return result;
	}

	/**
	 * 根据网单编号 查询表单
	 */
	@Override
	public OrderProductsVo PrudectView(String cOrderSn){
		OrderProductsVo productVo=shopOperationAreaService.queryOrdeProduct(cOrderSn);//查询页面表单
		if(null !=productVo){
			if(productVo.getIstate()!=null && !"".equals(productVo.getIstate())){
				productVo.setIstate(MemberInvoicesStatus.getByCode(Integer.parseInt(productVo.getIstate())).getName()); //发票状态转中文
			} 
		}
		return productVo;
	}
	/**
	 * 根据网单编号查询明细
	 */
	@Override
	public OrderProductsVo PrudectDetailed(String cOrderSn){
		OrderProductsVo productCommodity =shopOperationAreaService.queryCommodity(cOrderSn);//查询商品明细`
		return productCommodity;
	}



	/**
	 * 保存退货信息
	 */
	@Override
//	@Transactional(rollbackFor = Exception.class)
	public Json SaveRepairs(OrderRepairsVo orderRepairs){
		Json json = new Json();
		int i=0;
		
		OrderProductsVo productVo=shopOperationAreaService.queryOrdeProduct(orderRepairs.getcOrderSnId()); //查询网单详细信息
		orderRepairs.setId(shopOrderRepairLogsService.getNextValId());//
		orderRepairs.setOrderProductId(productVo.getId());//网单主键
		orderRepairs.setOrderId(productVo.getOrowId());//订单主键
		orderRepairs.setPaymentName(productVo.getPaymentName());//支付方式
		orderRepairs.setOfflineAmount(productVo.getPrice());//退款金额 		PS:不确定退款金额 是什么计算方式 先用退款金额
		orderRepairs.setHandleRemark(" ");
		orderRepairs.setRequestServiceRemark(" ");
		orderRepairs.setRequestServiceDate(0L);
		orderRepairs.setOfflineFlag(0);
		orderRepairs.setOfflineReason("  ");
		orderRepairs.setOfflineRemark(" ");
		orderRepairs.setHpFirstAddTime(0);
		orderRepairs.setHpSecondAddTime(0);
		if ("1".equals(orderRepairs.getType())){
			orderRepairs.setHandleStatus(3);//1审核中2进行中3受理完成
		}else{
			orderRepairs.setHandleStatus(1);//1审核中2进行中3受理完成
		}
		String repairsn= shopOrderRepairsService.queryRepaiSn(productVo.getId());//查询此网单是否第一次退货
		//退货订单处理
		if(repairsn!=null && !"".equals(repairsn)){
			StringBuffer sb = new StringBuffer();
			sb.append(repairsn);
			String s = sb.substring(sb.length()-1,sb.length());
			int thbh= +Integer.parseInt(s)+1;
			orderRepairs.setRepairSn(productVo.getCOrderSn()+"TH"+thbh);
		}else {
			orderRepairs.setRepairSn(productVo.getCOrderSn()+"TH2");//退货号
		}
		String signTim = shopOrderRepairsService.queryIsRejectionSign(Ustring.getString(productVo.getId()));
		if(signTim!=null && Integer.parseInt(signTim)>0){
			orderRepairs.setTypeFlag(5); //5表式揽收
		}else {
			orderRepairs.setTypeFlag(4);//4表示拒收
		}
		i=shopOrderRepairsService.insertSelective(orderRepairs);
		orderRepairs.getRepairSn();//插入退货信息
//		log.setSiteId();
		OrderRepairLogs log = new OrderRepairLogs(); //new出来一个退货日志
		log.setId(shopOrderRepairLogsService.getNextValId());//主键
		log.setOrderRepairId(orderRepairs.getId());//退货id
		log.setOperator("系统");
		log.setOperate("登记");
		log.setRemark("淘宝海尔官方旗舰店同步退换货申请");
		i=i+shopOrderRepairLogsService.insert(log); //记录退货操作流程 日志
		if(i>1){
			json.setMsg("保存退货信息成功！");
			json.setObj(orderRepairs.getId()); //传到前台 退货主键id
			json.setSuccess(true);
		}else {
			json.setMsg("保存退货信息失败！");
			json.setSuccess(false);
		}
		return json;
	}
	
	/**
	 * 查询 退货 数据
	 */
	@Override
	public Map<String,Object> ReturnEdit(String id){
		Gson gson = new Gson();
		Map<String,Object> map = new HashMap<String, Object>();
		OrderRepairsVo vo=shopOrderRepairsService.queryPairsId(id);//查询退货详细信息
		vo.setPaymentStatusTS(PaymentStatus.getByCode(vo.getPaymentStatus().intValue()).getName());
		vo.setReceiptStatusTS(MemberInvoicesStatus.getByCode(vo.getReceiptStatus().intValue()).getName());
		vo.setStorageStatusTS(CargoStatus.getByCode(vo.getStorageStatus().intValue()).getName());
		vo.setStatus(OrderRepairsStatus.getByCode(vo.getHandleStatus()).getName());
		List<OrderRepairLogs> list = new ArrayList<OrderRepairLogs>();
		if(vo!=null){
			list=shopOrderRepairLogsService.queryLogs(vo.getRepairSn());//查询退货操作日志
		}
		List<OrderRepairLESRecords> repairLesreCords=shopOrderRepairLesreCordsService.queryLesreCodrdsId(id);
		OrderrepairHPrecords hpRecords=shopOrderrepairHPrecordsService.selectByHpreCordsId(id); //HP退货工单状态
		map.put("vo", vo);
		map.put("list", gson.toJson(list));
		map.put("hpRecords", hpRecords);
		map.put("repairLesreCords", gson.toJson(repairLesreCords));
		return map;
	}
	/*
*
* 订单操作日志
* */
	@Override
	public List<Map<String,String>> selectOrderOperateLogs(String orderSn){
		return shopOperationAreaService.selectOrderOperateLogs(orderSn);
	}
	/*
	* 优惠券信息查询
	* */
	@Override
	public List<Map<String,String>> selectCoupon(String orderSn){
		return shopOperationAreaService.selectCoupon(orderSn);
	}
	/*
	* 根据订单表号查询明细
	* */
	@Override
	public Map<String,String> orderNumberSelect(String orderSn){
		Map<String,String> map=shopOperationAreaService.orderNumberSelect(orderSn);
		Map<String,String> map1=shopOperationAreaService.selectMemberInvoicesByorderSn(orderSn);
		if (map1!=null){
			map.putAll(map1);
		}
		return  map;
	}
	/*
	* 订单详情里面的商品详情
	*
	* */
	@Override
	public List<Map<String,String>> selectPrudevtDatail(String orderSn){
		List<Map<String,String>> stringMap=shopOperationAreaService.selectPrudevtDatail(orderSn);
		return stringMap;
	}
	/**
	 * 退货审核
	 * @return
	 */
	@Override
	public Json Toexamine(String id,String status,String handleRemark){
		Json json = new Json();
		int i =shopOrderRepairsService.updataStatus(id, status,handleRemark);
		//添加退货操作日志
        OrderRepairLogs log = new OrderRepairLogs(); //new出来一个退货日志
        log.setId(shopOrderRepairLogsService.getNextValId());//主键
		log.setOrderRepairId(Integer.parseInt(id));//退货id
		log.setOperator("系统");
		if("2".equals(status)){
			log.setOperate("审核通过");
			log.setRemark("修改申请状态从 审核中 到 进行中，备注：");
		}else if("5".equals(status)){
			log.setOperate("审核不通过");
			log.setRemark("修改申请状态从 审核中 到 已驳回，备注：");
		}
		i=i+shopOrderRepairLogsService.insert(log); //记录退货操作流程 日志
		if(i>1){
			json.setMsg("操作成功！");
			json.setSuccess(true);
		}else {
			json.setMsg("操作失败！");
			json.setSuccess(false);
		}
		return json;
	}
	/*
	* @author zhangbo
	* hp拒收表格显示
	* */
	@Override
	public List<Map<String,String>> datagrid_WwwHpRecords(Map<String,Object> map){
		return shopOperationAreaService.datagrid_WwwHpRecords(map);
	}
	@Override
	public List<Map<String,String>> datagrid_WwwHpRecords1(Map<String,Object> map){
		return shopOperationAreaService.datagrid_WwwHpRecords1(map);
	}
	//查询在网单表已经存在的网单号并返回存在的网单号
	@Override
	public List<Map<String,Object>> check_cOrderSn_isExist(List<Map<String,Object>> list){
		return shopOperationAreaService.check_cOrderSn_isExist(list);
	}
	//更新WwwHpRecords表中的匹配次数
	@Override
	public void update_WwwHpRecordsCount(List<String> list){
		shopOperationAreaService.update_WwwHpRecordsCount(list);
	}
	//批量更新hp拒收表中flag字段
	@Override
	public void updateFlagBatch(List<String> list){
		shopOperationAreaService.updateFlagBatch(list);
	}
	//查询Excel导出的数据
	@Override
	public List<Map<String,Object>> select_export_ExcelData(Map<String,Object> map){
		return shopOperationAreaService.select_export_ExcelData(map);
	}
/**
 * 推送修改信息给HP
 * @throws ParseException 
 * @throws MalformedURLException 
 */
	@Override
	public Json ModifyPushHP(List<OrderRepairsVo> orderRepairs) throws ParseException, MalformedURLException {
		// TODO Auto-generated method stub
		Json json = new Json();
		String TbSn = ""; //接收TB单号
		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  InsertDataToHP_Type.Inputs input = new InsertDataToHP_Type.Inputs();
			 java.util.List<InsertDataToHP_Type.Inputs> _insertDataToHP_inputs = new ArrayList<>();
		     javax.xml.ws.Holder<java.lang.String> _insertDataToHP_flag = new javax.xml.ws.Holder<java.lang.String>();
		     javax.xml.ws.Holder<java.lang.String> _insertDataToHP_msg = new javax.xml.ws.Holder<java.lang.String>();
		    String path = "file:"+ this.getClass().getResource("/wsdl_test/InsertDataToHP.wsdl").getPath();
		    URL url = new URL(path);
		    InsertDataToHP_Service ss = new InsertDataToHP_Service(url, SERVICE_NAME);
	        InsertDataToHP port = ss.getInsertDataToHPSOAP();
		  for(int i =0;i<orderRepairs.size();i++){
			  OrderRepairsVo  orderRepairsVo = orderRepairs.get(i);
			  String menuflag =orderRepairsVo.getMenuflag(); 
			  
			if("SD".equals(orderRepairsVo.getMenuflag())){ //手动推送HP的时候要判断 HP异常匹配表是否有这个信息 如果有才能继续推送没有就提示等待
				TbSn =shopOperationAreaService.queryTBorderSn(orderRepairsVo.getcOrderSnId());
				if(shopOperationAreaService.queryWwwHpTbSn(TbSn)<=0){ //根据网单扩展表的TB单号查询拒收表有没有这条数据
					json.setMsg("请等待HP拒收信息！");
					json.setSuccess(false);
					return json;
				}
			 }
			
		  //如果发起二次鉴定
		  if("2".equals(menuflag)||"JS".equals(menuflag)){ //menuflag 等于JS也就是表示是拒收调用接口
			  orderRepairsVo =shopOrderRepairsService.queryTwoIdentification(Ustring.getString(orderRepairsVo.getId()));
		  }
		  if(orderRepairsVo==null){
			 continue;
		  }
		  OrderRepairsVo vo = shopOrderRepairsService.selectPairs(Ustring.getString(orderRepairsVo.getId()));
        //给HP传值
	        input.setOrderNo(Ustring.getString(orderRepairsVo.getId()));//退货订单号
	        input.setCounts(new BigDecimal(Ustring.getString0(orderRepairsVo.getCount())));//退货数量
	        input.setCreatedDate(dateToXmlDate(new Date()));//接口传输时间
	        //传PRODTYPE_ID物料编码 要先查departmentcode_sku 
	        //判断这个网单是不是空调如果是空调的话则根据网单表的SKU去查inv_machine_set表的sub_sku这个字段然后把值传给HP
	        if(Integer.parseInt(Ustring.getString0(departMentCodeSkuService.selectKt(Ustring.getString(vo.getSku()))))>=1){
	        	input.setProdtypeId(departMentCodeSkuService.querySubsku(vo.getSku()));
	        }else {
	        	   input.setProdtypeId(vo.getSku());//物料编码
	        }
	        input.setRequestServiceRemark(orderRepairsVo.getRequestServiceRemark());//要求描述
	        if("2".equals(menuflag)|| "JS".equals(menuflag)){
				Long timestamp = (long) (Integer.parseInt(Ustring.getString0(orderRepairsVo.getRequeStservieDateTS()))*1000);
		        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));
				orderRepairsVo.setRequeStservieDateTS(date);
		        input.setRequestServiceDate(dateToXmlDate(sdf.parse(date)));//要求服务时间
	        }else{
				input.setRequestServiceDate(dateToXmlDate(sdf.parse(orderRepairsVo.getRequeStservieDateTS())));//要求服务时间
			}
	      //  原始网单号 (3W传TB单号)
	        if("3W".equals(vo.getStocktype())){
	        	 input.setOldOrder(vo.getTbOrderSn());
	        }else {
	        	  input.setOldOrder(orderRepairsVo.getcOrderSnId());
	        }
	        input.setIfTk(orderRepairsVo.getOfflineFlag().toString());//是否线下退款
	        input.setTkReason(orderRepairsVo.getOfflineReason());//退款原因
	        input.setTkje(orderRepairsVo.getOfflineAmount());//退款金额
	        input.setTkRemark(orderRepairsVo.getOfflineRemark());//退款备注
	        OrderrepairHPrecords orderrepairHPrecords= new OrderrepairHPrecords();
	        if("2".equals(menuflag)){
	        	input.setIfEcjd("2");//是否二次鉴定
	        	orderrepairHPrecords.setTwoAppraisal("1");
	        	orderrepairHPrecords.setCheckType(new Byte((byte) 2));
	        	shopOrderrepairHPrecordsService.updataRepaiHp(orderrepairHPrecords);//更新推送二次鉴定推送时间和状态
	        	  
	        	orderRepairsVo.setHpSecondAddTime(22);
	            shopOperationAreaService.updateStatus(vo.getOrderProductId().toString(),"130"); //发起二次鉴定的同时要把正向单关闭 
	    		//记录退货操作流程 日志
	    		ProcessLog(vo.getId(), "发起二次鉴定", "向HP推送发起二次鉴定请求信息");
	    		ProcessLog(vo.getId(), "关闭", "关闭正向单");
	        }else if("blp3".equals(menuflag)){
	        	input.setIfEcjd("3");//是否三次鉴定
	        	 orderrepairHPrecords.setCheckType(new Byte((byte) 3));
	        	orderrepairHPrecords.setThreeAppraisal("1");
	        	shopOrderrepairHPrecordsService.updataRepaiHp(orderrepairHPrecords); //更新推送三次鉴定推送时间和状态
	        	ProcessLog(vo.getId(), "发起三次鉴定", "向HP推送发起三次鉴定请求信息");
	        	//发起三次鉴定时需要向VOM发起22出库单
	        	OrderRepairLESRecords cords= new OrderRepairLESRecords();
				cords.setOrderProductId(vo.getOrderProductId());//网单id
				cords.setOrderRepairId(vo.getId());//退货单主键
	        	cords.setRecordSn(vo.getcOrderSnId()+"CX1");//入库单号
				cords.setOperate(1);//操作，1=出库；2=入库
				cords.setStorageType(22);//批次，22；21；10
				insert(cords); //插入 入库单
				ProcessLog(vo.getId(), "推送OMS", "向OMS发起出22库请求");
	        }else {
	        	orderRepairsVo.setHpFirstAddTime(1);
	        	input.setIfEcjd("1");//是否一次鉴定
	        }
	        
        //判断库存类型是否是菜鸟库发货的
        if("3W".equals(vo.getStocktype())){
        	input.setTypes("W8");//类型 1，网单。2京东 。3 富士康
        }else if("blp".equals(menuflag) || "blp3".equals(menuflag)){
        	input.setTypes("W2");//类型 1，网单。2京东 。3 富士康
        } else {
        	input.setTypes("");//类型 1，网单。2京东 。3 富士康
        }
        
        
        //如果是手动推送的话就要修改HP异常表的数据
        if("SD".equals(orderRepairsVo.getMenuflag())){
        	List<Map<String,Object>> list = new ArrayList<>();
        	Map<String,Object>  map = new HashMap<>();
        	map.put("orderProductId", vo.getOrderProductId());
        	map.put("th_id", vo.getId());
        	map.put("cOrderSn", vo.getcOrderSnId());
        	map.put("hpTbSn", TbSn);
        	list.add(map);
			shopOperationAreaService.update_WwwHpRecordsInfo(list);
			//记录操作日志
			this.ProcessLog(vo.getId(), "修改信息", "修改推送HP信息");
        }
        
       
        
//        if("2".equals(orderRepairsVo.getMenuflag())){ //表示是二次鉴定
//        
//        }else if("blp3".equals(orderRepairsVo.getMenuflag())){
//        	orderRepairsVo.setHpFirstAddTime(1); //一次鉴定
//        }
        
      //添加推送hp信息(修改退货信息）（receiptStatus）发票状态   （ storageStatus） 货物状态改成 带召回
        	  orderRepairsVo.setStorageStatus(10);
	          orderRepairsVo.setReceiptStatus(10);
			  shopOrderRepairsService.updatePushHp(orderRepairsVo); 
//        shopOrderRepairsService.updateStatus("10", "10", vo.getId().toString()); // 改一下退货表的 （receiptStatus）发票状态   （ storageStatus） 货物状态改成 带召回
        //添加退货操作日志
        //	调用推送HP接口
        _insertDataToHP_inputs.add(input);
		}
		port.insertDataToHP(_insertDataToHP_inputs, _insertDataToHP_flag, _insertDataToHP_msg);
        json.setMsg(_insertDataToHP_msg.toString()+"-------"+_insertDataToHP_flag);
		return json;
	}
	
	   /** 
     * 将Date类转换为XMLGregorianCalendar 
     * @param date 
     * @return  
     */  
    public static XMLGregorianCalendar dateToXmlDate(Date date){
            Calendar cal = Calendar.getInstance();  
            cal.setTime(date);  
            DatatypeFactory dtf = null;  
             try {  
                dtf = DatatypeFactory.newInstance();  
            } catch (DatatypeConfigurationException e) {  
            }  
            XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();  
            dateType.setYear(cal.get(Calendar.YEAR));  
            //由于Calendar.MONTH取值范围为0~11,需要加1  
            dateType.setMonth(cal.get(Calendar.MONTH)+1);  
            dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));  
            dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));  
            dateType.setMinute(cal.get(Calendar.MINUTE));  
            dateType.setSecond(cal.get(Calendar.SECOND));  
            return dateType;  
        }
	
	/**
	 * 接收天猫返回过来的数据存到数据库
	 * @param orderTmallReturnLogs
	 * @return
	 */
	public Json TMReturnData(List<OrderTmallReturnLogs> orderTmallReturnLogs){
		Json json = new Json();
		for(int i=0;i<orderTmallReturnLogs.size();i++){
			OrderTmallReturnLogs logs = orderTmallReturnLogs.get(i);
			shopOrderTmallReturnLogsService.insert(logs);
		}
		return json;
	}
	/*
	* 检查inv_machine_set表里面的主sku
	* */
	public List<Map<String,Object>> select_sku(List<Map<String,Object>> list){
		return stockInvMachineSetService.select_sku(list);
	}
	/*
	* 更新HP拒收信息
	* */
	public void update_WwwHpRecordsInfo(List<Map<String,Object>> list){
		shopOperationAreaService.update_WwwHpRecordsInfo(list);
	}

	/**
	 * 插入出入库信息
	 */
	public int insert(OrderRepairLESRecords cords){
		cords.setId(shopOrderRepairLogsService.getNextValId()); //生成主键
		return shopOrderRepairLesreCordsService.insert(cords);
	}

    /**
	 * 查询退货id 网单ID
	 */
	public OrderRepairsVo queryOrderProductId(String id){
		return shopOrderRepairsService.queryOrderProductId(id);
	}
	
	

	//调用VOM http接口
	  public Json  CallHttpVOM(OrderRepairLESRecords  cords) throws Exception
	    {
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Json json = new Json();
		  Map<String,Object> map  = new HashMap<>();
		  XMLSerializer xmlSerializer = new XMLSerializer();
		  OrdersVo vo= shopOrdersService.queryVOMTransMission(Ustring.getString(cords.getOrderProductId()));
		  String responseMsg = "";
		           //1.构造HttpClient的实例
		           HttpClient httpClient=new HttpClient();
		           httpClient.getParams().setContentCharset("UTF-8");
	            PostMethod postMethod=new PostMethod("http://58.56.128.84:9001/EAI/service/VOM/CommonGetWayToVOM/CommonGetWayToVOM");
	            
	            postMethod.addParameter("notifytime", formatter.format(new Date()));
	            postMethod.addParameter("butype", "rrs_order");
	            postMethod.addParameter("source", "HBDM3W");
	            postMethod.addParameter("type", "XML");
	         Gson gson = new Gson();
	            String md5Str= MD5util.encrypt(xmlSerializer.write(JSONSerializer.toJSON(gson.toJson(vo)))+ "Haier,123");
	            postMethod.addParameter("sign",Base64Util.encode(md5Str.getBytes()).replaceAll("\r\n", ""));
	            postMethod.addParameter("content",xmlSerializer.write(JSONSerializer.toJSON(JSON.toJSONString(vo))));
	            try {
	            	             // 4.执行postMethod,调用http接口
	            	             httpClient.executeMethod(postMethod);//200
	            	            
	            	 //5.读取内容tt
	            	             responseMsg = postMethod.getResponseBodyAsString().trim();
	            	            log.info(responseMsg);
	            	            
	            	             //6.处理返回的内容
	            	
	            	         } catch (HttpException e) {
	            	             e.printStackTrace();
	            	         } catch (IOException e) {
	            	            e.printStackTrace();
	            	         } finally {
	            	            //7.释放连接
	            	             postMethod.releaseConnection();
	            	         }
	        return json;

	    }
	  /**
	   * 插入保存 HP返回来的鉴定结果
	   * @param
	   * @return
	   */
	  public int insertHPrecords(OrderRepairshpLogs bean){
		  int i=0;
		  OrderrepairHPrecords orderrepairHPrecords= new OrderrepairHPrecords();
		  OrderRepairsVo vo= shopOrderRepairsService.selectOrederProductId(bean.getOrderRejectSn());//查询网单id和退货单号
		  bean.setId(shopOrderRepairLogsService.getNextValId());
		  orderrepairHPrecords.setId(shopOrderRepairLogsService.getNextValId());//生成解析之后数据的主键
		  orderrepairHPrecords.setOrderProductId(Integer.parseInt(vo.getcOrderSnId()));//网单id
		  orderrepairHPrecords.setOrderRepairId(vo.getId());//退货id
		  orderrepairHPrecords.setNetPointCode(bean.getNetPointCode());//网点
		  orderrepairHPrecords.setCheckResult(new Byte(bean.getCheckResult()));///鉴定结果 1:符合退机条件 2:不符合退机条件
		  orderrepairHPrecords.setQuality(new Byte(bean.getQuality()));///产品状态 1: 未开箱。2:已开箱正品。3:不良品  4：已使用正品 5:不良品换机 6:不良品退机（现电商只保有1256四个状态）
		  orderrepairHPrecords.setMachineNum(bean.getMacHineNum());//机编代码
		  orderrepairHPrecords.setInspector(bean.getInSpector());//质检员
		  orderrepairHPrecords.setInspectTime(bean.getInSpectTime());//质检时间
		  orderrepairHPrecords.setSuccess(new Byte((byte) 1));//是否成功
		  orderrepairHPrecords.setCheckType(new Byte(bean.getAdd1()));//质检类型，一次/二次
		  orderrepairHPrecords.setPackResult(new Byte(bean.getAdd2()));//包装结果
		  orderrepairHPrecords.setResponse(new Byte(bean.getAdd3()));//非正品买单方
//		  orderrepairHPrecords.setHpOrderFail("");//生成工单是否失败，无值代表成功
		  orderrepairHPrecords.setHpOrderRemark(bean.getAdd5());//备注
		  orderrepairHPrecords.setRepairsHPLogsId(bean.getId());//OrderRepairsHPLogs原始数据关联ID
		  orderrepairHPrecords.setSource("");
		  orderrepairHPrecords.setWoId(bean.getWoId());//工单号
		  i=+shopOrderRepairshpLogsService.insert(bean);//插入HP返回的原始数据
		  i=+shopOrderrepairHPrecordsService.insert(orderrepairHPrecords);//插入HP返回结果解析之后的数据
		  //记录操作日志
		  OrderRepairLogs log = new OrderRepairLogs(); //new出来一个退货日志
			log.setId(shopOrderRepairLogsService.getNextValId());//主键
			log.setOrderRepairId(vo.getId());//退货id
			log.setOperator("系统");
			log.setOperate("HP回传");
			if("1".equals(bean.getAdd1())){
				log.setRemark("HP返回一次鉴定结果");
			}else if("2".equals(bean.getAdd1())){
				log.setRemark("HP返回二次鉴定结果");
			}
			if("2".equals(bean.getQuality())){
				log.setRemark("HP返回鉴定结果：不良品");
			}
			i=i+shopOrderRepairLogsService.insert(log); //记录退货操作流程 日志
		  return  i;
	  }
	public Map<String,Object> select_ThInfo(String cOrderSn){
		return shopOperationAreaService.select_ThInfo(cOrderSn);
	}
	//根据网单号查询订单号
	public String selectOrderSn(String cOrderSn){
		return shopOperationAreaService.selectOrderSn(cOrderSn);
	}
	/*
	* 退换货列表显示
	* */
	public List<Map<String,String>> datagrid_orderForecastGoal(Map<String,Object> map){
		return shopOperationAreaService.datagrid_orderForecastGoal(map);
	}
	/*
	* 退货总记录数
	* */
	public List<Map<String,String>> datagrid_orderForecastGoalcount(Map<String,Object> map){
		return shopOperationAreaService.datagrid_orderForecastGoalcount(map);
	}
	/*
	* 退换货报表导出
	* */
	public List<Map<String,Object>> export_ExcelOrderRepairs(Map<String,Object> map){
		return shopOperationAreaService.export_ExcelOrderRepairs(map);
	}
	
	
	
	
	/**
	 * 处理vom回传的信息
	 * @param vomReturnInforMation
	 * @return
	 */
//	@Transactional(rollbackFor = Exception.class)
//	public String insertAnalyLogs(OrederVOMReturnLogs vomReturnLogs){
//		Json jsons = new Json();
//		int index = 0;
//		String id="";
//		// TODO Auto-generated method stub
//		net.sf.json.JSON json=getJSONFromXml(vomReturnLogs.getContent());//	把数据转成JSON
//		OrderVOMReturnAnalysis 	analysis =JSON.parseObject(JSON.toJSONString(json), OrderVOMReturnAnalysis.class);
//		net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(json);
//		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(jsonObject.getString("InOutItems"));
//		net.sf.json.JSONObject jsonObjectss = null;
//		 List<OrderVOMReturnAnalysisDetailed> list2 = new ArrayList<>();
//		 vomReturnLogs.setId(shopOrderRepairLogsService.getNextValId());
//		 index += shopOrederVOMReturnLogsService.insert(vomReturnLogs);//插入VOM返回原始数据
//		 analysis.setId(shopOrderRepairLogsService.getNextValId());//生成 主表单主键
//		 index += shopOrderVOMReturnAnalysisService.insert(analysis);//插入VOM回传信息 解析之后的数据 （主表单）
//		 for (int i = 0; i < jsonArray.size(); i++) {
//			 jsonObjectss = jsonArray.getJSONObject(i);
//			 OrderVOMReturnAnalysisDetailed detailed = JSON.parseObject(JSON.toJSONString(jsonObjectss), OrderVOMReturnAnalysisDetailed.class);
//			 detailed.setId(shopOrderRepairLogsService.getNextValId());//生成子表单的主键
//			 detailed.setAnalysisId(analysis.getId());//关联主表单id
//			 index += shopOrderVOMReturnAnalysisDetailedService.insert(detailed); //插入vom返回的子表单数据（解析之后的数据）
//			 
//	      }
//		 if((2+jsonArray.size())==index){
//			 jsons.setMsg("成功");
//			 jsons.setSuccess(true);
//			 id = analysis.getOrderNo();
//		 }else {
//			 jsons.setMsg("失败！");
//			 jsons.setSuccess(false); 
//		 }
//		return id;
//	}
	/**
	 * 把XML数据转换成JSON
	 * @param xmlString
	 * @return
	 */
	public static net.sf.json.JSON getJSONFromXml(String xmlString) {  
        XMLSerializer xmlSerializer = new XMLSerializer();  
        net.sf.json.JSON json = xmlSerializer.read(xmlString);  
        return json;  
    }  
	
	/**
	 * 接收不良品返回过来的信息   http接口
	 * @return
	 * @throws ParseException 
	 */
	 public String HPReturnUnhealthyImpl(String obejecXml) throws ParseException{
		 OrderhpRejectionLogs logs = new OrderhpRejectionLogs();
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 net.sf.json.JSON json=getJSONFromXml(obejecXml);//	把数据转成JSON
		 net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(json);
		 net.sf.json.JSONObject jsonObjects = net.sf.json.JSONObject.fromObject(object.getString("item"));
		 logs.setId(shopOrderRepairLogsService.getNextValId());
		 logs.setSoNum(jsonObjects.getString("so_num"));
		 logs.setVbelnso(jsonObjects.getString("vbelnso"));
		 logs.setProductTypeId(jsonObjects.getString("product_type_id"));
		 logs.setProductNo(jsonObjects.getString("product_no"));
		 logs.setHpLesId(jsonObjects.getString("hp_les_id"));
		 logs.setCountNum(Integer.parseInt(jsonObjects.getString("count_num")));
		 logs.setKw(Ustring.getString(jsonObjects.getString("kw")));
		 logs.setInwhId(Ustring.getString(jsonObjects.getString("inwh_id")));
		 logs.setInwhDate(Ustring.getString(jsonObjects.getString("inwh_date")));
		 logs.setKeyProductNo(Ustring.getString(jsonObjects.getString("key_product_no")));
		 logs.setMainSku(Ustring.getString(jsonObjects.getString("main_sku")));
		 logs.setSubCount(Integer.parseInt(Ustring.getString0(jsonObjects.getString("sub_count"))));
		 logs.setChannelOrderSn(jsonObjects.getString("channel_order_sn"));
		 logs.setChannel(jsonObjects.getString("channel"));
		 logs.setSecCode(Ustring.getString(jsonObjects.getString("sec_code")));
		 logs.setDepCharge(Ustring.getString(jsonObjects.getString("dep_charge")));
		 logs.setHpLesDate(formatter.parse(jsonObjects.getString("hp_les_date")));
 		 logs.setRespDivision(Ustring.getString(jsonObjects.getString("resp_division")));
 		 //根据退货网单号 查询是否已经回传过 如果是的话就更改数据库信息 不是则插入
 		String id= shopOrderhpRejectionLogsService.quereHPRejection(jsonObjects.getString("channel_order_sn"));
 		 if(Ustring.isEmpty(id)){ 
		 shopOrderhpRejectionLogsService.insertSelective(logs);
		//根据退货单号 查询数据用来关闭正向单 作废发票
		 OrderRepairsVo repairsVo= shopOrderRepairsService.queryRepairsInvoiceId(jsonObjects.getString("channel_order_sn"));
		 shopOperationAreaService.updateStatus(Ustring.getString(repairsVo.getOrderProductId()),"130"); //发起二次鉴定的同时要把正向单关闭
		 ProcessLog(repairsVo.getId(), "操作", "关闭正向单");
		 //同时作废发票
			if(Ustring.isNotEmpty(repairsVo.getInvoiceId())){
				this.InvalidInvoices(repairsVo.getInvoiceId(), "退换货");
				 ProcessLog(repairsVo.getId(), "操作", "作废发票");
			}
		 }else {
			 shopOrderhpRejectionLogsService.updateHpRejectionLogs(logs); 
		 }
		return "成功～";
		}
	//查询hp拒收日志表里面的rowid ,判断主键是否重复
	public List<String> selectHPlogsRowid(String rowid){
		return shopOperationAreaService.selectHPlogsRowid(rowid);
	}
	//hp推送信息插入到hp拒收日志表
	public void insertHPlogs(Map<String,Object> map){
		 shopOperationAreaService.insertHPlogs(map);
	}
	public void insertWwwHpRecords(Map<String,Object> map){
		shopOperationAreaService.insertWwwHpRecords(map);
	}
	 

	/**
	 * 作废电子发票
	 **/
	 public Integer InvalidInvoices(String invalidData, String invalidReason) throws BusinessException {
	            String id = String.valueOf(invalidData);
	            Invoices invoices = shopInvoiceService.getById(Integer.parseInt(id));
	            if (invoices == null) {//发票不存在
	                return 0;
	            }
	            if (invoices.getState() != InvoiceConst.COMPLETE_STATE) {//已开票的发票才可以作废
	            	return 0;
	            }
	            if (invoices.getStatusType() == InvoiceConst.INVALID_KIND
	                    && invoices.getSuccess() == InvoiceConst.SUCCESS) {//已经作废成功
	            	return 0;
	            }
	            invoices.setStatusType(InvoiceConst.INVALID_KIND);//推送作废
	            invoices.setSuccess(0);//待推送
	            invoices.setTryNum(0);
	            invoices.setInvalidReason(invalidReason);
	            Integer t=0;
	            try {
	              shopInvoiceService.updateInvoiceOperate(invoices);
	              t=1;
	            } catch (Exception e) {
	                logger.error("更新发票发生异常，发票编号：" + invoices.getCOrderSn(), e);
	            }
	        return t;
	    }
	 
	   /**
	     * 关闭退货单更改退货单状态
	     * @param id
	     * @param handleRemark
	     * @return
	     */
	 public int RepairsTermination(String id,String handleRemark){
		 int index =0;
		 index = shopOrderRepairsService.RepairsTermination(id, handleRemark);
		 OrderRepairLogs log = new OrderRepairLogs(); //new出来一个退货日志
			log.setId(shopOrderRepairLogsService.getNextValId());//主键
			log.setOrderRepairId(Integer.parseInt(id));//退货id
			log.setOperator("系统");
			log.setOperate("关闭逆向单");
			log.setRemark(handleRemark);
			index +=shopOrderRepairLogsService.insert(log); //记录退货操作流程 日志
		 return index;
	 }
	 
	 /**
	 *  22转41库 实时推送给VOM
	 * @return
      */
	 public int StockTransfer(String id,String handleRemark){
		 OrderRepairsVo vo= shopOrderRepairsService.queryReturnEdit(id);
		 List<OrderRepairLESRecords> repairLESRecords = new ArrayList<>();
		 OrderRepairLESRecords cords= new OrderRepairLESRecords();
			cords.setOrderProductId(vo.getOrderProductId());//网单id
			cords.setOrderRepairId(vo.getId());//退货单主键
			cords.setRecordSn(vo.getcOrderSnId()+"CX1");//出库单号
			cords.setOperate(1);//操作，1=出库；2=入库
			cords.setStorageType(22);//批次，22；21；10
			int index = insert(cords); //插入出库单
			repairLESRecords.add(cords);
			if(index>0){
				cords.setRecordSn(vo.getcOrderSnId()+"CX2");//入库单号
				cords.setOperate(2);//操作，1=出库；2=入库
				cords.setStorageType(41);//批次，22；21；10
				index+= insert(cords); //插入出库单
				repairLESRecords.add(cords);
			}
			 OrderRepairLogs log = new OrderRepairLogs(); //new出来一个退货日志
				log.setId(shopOrderRepairLogsService.getNextValId());//主键
				log.setOrderRepairId(Integer.parseInt(id));//退货id
				log.setOperator("系统");
				log.setOperate("出22转41库");
				log.setRemark(handleRemark);
				index +=shopOrderRepairLogsService.insert(log); //记录退货操作流程 日志
				
				for(int i=0;i<repairLESRecords.size();i++){
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date1 = new Date();
					OrdersVo ordersVo= shopOrdersService.queryVOMTransMission(repairLESRecords.get(i).getOrderProductId().toString());
					VOMSynOrderRequire synOrderRequire = new VOMSynOrderRequire();
					 synOrderRequire.setOrderNo(repairLESRecords.get(i).getRecordSn());
					 synOrderRequire.setSourceSn(repairLESRecords.get(i).getRecordSn());
					 synOrderRequire.setOrderType(Ustring.getString(ordersVo.getOrderType()));
//					 synOrderRequire.setBusType("70");
					 synOrderRequire.setOrderDate(dateFormat.format(date1));
					 synOrderRequire.setStoreCode(ordersVo.getsCode());
					 synOrderRequire.setProvince(ordersVo.getProvince().toString());
					 synOrderRequire.setCity(ordersVo.getCity().toString());
					 synOrderRequire.setCounty(ordersVo.getRegion().toString());
					 synOrderRequire.setAddr(ordersVo.getAddress());
//					 synOrderRequire.setGbCode("220102");
					 synOrderRequire.setName(ordersVo.getConsignee());
					 synOrderRequire.setMobile(ordersVo.getMobile());
					 synOrderRequire.setTel(ordersVo.getPhone());
					 synOrderRequire.setPostCode(ordersVo.getZipcode()); 
					 synOrderRequire.setPayState(ordersVo.getPaymentStatus().toString());
					 synOrderRequire.setPayTime(ordersVo.getPayTimeStr());
					 synOrderRequire.setPayType(ordersVo.getPaymentCode());
					 synOrderRequire.setIsInv(ordersVo.getIsReceipt());
					 synOrderRequire.setInvType(ordersVo.getType());
					 synOrderRequire.setTaxBearer(ordersVo.getTaxPayerNumber());
					 synOrderRequire.setRecAddr(ordersVo.getRegisterAddressAndPhone());
					 synOrderRequire.setRemark(repairLESRecords.get(i).getRepairSn());
					 
					 StringBuffer sb = new StringBuffer(repairLESRecords.get(i).getRecordSn());  
					 sb.deleteCharAt(sb.length()-3);
					String Reorder =repairLESRecords.get(i).getRecordSn().substring(repairLESRecords.get(i).getRecordSn().length()-3, repairLESRecords.get(i).getRecordSn().length());
					 if("CX1".equals(Reorder)){
						 Reorder=sb+"CX2";
					 }else if("CX2".equals(Reorder)){
						 Reorder=sb+"CX1";
					 }else {
						 Reorder=repairLESRecords.get(i).getRecordSn();
					 }
					 synOrderRequire.setReorder(Reorder);
					 synOrderRequire.setRecBank(ordersVo.getBankNameAndAccount());
					 synOrderRequire.setFreight(ordersVo.getShippingAmount().doubleValue());
					 synOrderRequire.setBillSum(ordersVo.getOrderAmount().doubleValue());
					 VOMSynSubOrderRequire synSubOrderRequire = new VOMSynSubOrderRequire();
					 //套机列表查询
					 String sku= getProductCode(repairLESRecords.get(i).getId(),repairLESRecords.get(i).getSku(),repairLESRecords.get(i).getPushFailNumber()); 
					 
					 synSubOrderRequire.setProductCode(sku);
					 synSubOrderRequire.setItemNo("1");
					 synSubOrderRequire.setStorageType(Ustring.getString(repairLESRecords.get(i).getStorageType()));
					 synSubOrderRequire.setHrCode(sku);
					 synSubOrderRequire.setProdes(repairLESRecords.get(i).getProductName());
					 synSubOrderRequire.setNumber(repairLESRecords.get(i).getNumber());
					 synSubOrderRequire.setUnprice(repairLESRecords.get(i).getPrice());
					 List<VOMSynSubOrderRequire> subOrderList = new ArrayList<VOMSynSubOrderRequire>();
					 subOrderList.add(synSubOrderRequire);
					 synOrderRequire.setSubOrderList(subOrderList);
					 vomOrderModel.synOrderInfo(synOrderRequire);
					
					OrderRepairLESRecords records = new OrderRepairLESRecords();
		        	records.setId(repairLESRecords.get(i).getId());
		        	records.setFalg("1"); //修改推送状态
		        	shopOrderRepairLesreCordsService.updataRecords(records);
				}
				
		 return index;
	 }
	 
	 public String getProductCode(int id,String sku,Integer pushFailNumber){
		 //套机列表查询
	      List<InvMachineSet> imsList = null;
		 InvMachineSet machineSet = new InvMachineSet();
          machineSet.setMainSku(sku);
          ServiceResult<List<InvMachineSet>> stockcommresult = getSubMachinesByMainSku(machineSet);
          if (stockcommresult != null && stockcommresult.getSuccess()) {
              imsList = stockcommresult.getResult();
              if (imsList == null || imsList.size() == 0) {//判断如果不是套机就做一个常规处理
                  imsList = new ArrayList<InvMachineSet>();
                  InvMachineSet ims = new InvMachineSet();
                  ims.setPosnr("10");
                  ims.setSubSku(convertToExternalSku(sku));//转换为R码
                  if (ims.getSubSku() == null || ims.getSubSku().equals("")) {
                  	OrderRepairLESRecords records = new OrderRepairLESRecords();
                  	records.setId(id);
                  	records.setFalg("2"); //修改推送状态
                  	records.setFailReason("套机产品编码不能为空");
                  	records.setPushFailNumber(pushFailNumber+1); //修改失败推送次数
                  	shopOrderRepairLesreCordsService.updataRecords(records);
                  }
                  ims.setMenge(new BigDecimal(1));
                  imsList.add(ims);
                  
                  return ims.getSubSku();
              }
          }
          	return sku;
	}
		
		 public ServiceResult<List<InvMachineSet>> getSubMachinesByMainSku(InvMachineSet machineSet) {
		        ServiceResult<List<InvMachineSet>> result = new ServiceResult<List<InvMachineSet>>();
		        try {
		            result.setResult(invMachineSetDao.getByMainSku(machineSet.getMainSku()));
		        } catch (Exception e) {
		            log.error("根据mainSku(" + machineSet.getMainSku() + ")获取仓库信息时发生异常:", e);
		            result.setSuccess(false);
		            result.setMessage(e.getMessage());
		        }

		        return result;
		    }
		 
		 /**
		     * 转换为外部系统使用的物料编码
		     *
		     * @param sku 内部的物料编码
		     * @return 外部的物料编码
		     */
		    //12
		    private String convertToExternalSku(String sku) {
		        if (sku == null || sku.equals("")) {
		            return null;
		        }
		        ServiceResult<EisExternalSku> result = getBySkuType(sku, EisExternalSku.TYPE_R);
		        EisExternalSku es = result.getResult();

		        if (!result.getSuccess()) {
		            log.error("通过itemService转换物料编码发生未知异常：" + result.getMessage());
		            //throw new BusinessException("通过itemService转换物料编码发生未知异常：" + result.getMessage());
		            return sku;
		        } else {
		            if (es != null && es.getExternalSku() != null && !es.getExternalSku().equals("")) {
		                return es.getExternalSku();
		            } else {
		                return sku;
		            }
		        }
		    }
		    public ServiceResult<EisExternalSku> getBySkuType(String sku, String type) {
		        ServiceResult<EisExternalSku> result = new ServiceResult<EisExternalSku>();
		        try {
		            result.setResult(eisExternalSkuService.getBySkuType(sku, type));
		        } catch (Exception e) {
		            log.error("根据sku和type查询物料对照信息时，发生未知异常：", e);
		            result.setMessage("根据sku和type查询物料对照信息发生未知异常：" + e.getMessage());
		            result.setSuccess(false);
		        }
		        return result;
		    }
		    /**
		     * 记录处理日志
		     * @param orderReparsId
		     * @param operate
		     * @param remark
		     * @return
		     */
		    public int ProcessLog(Integer orderReparsId,String operate,String remark){
		    	OrderRepairLogs log = new OrderRepairLogs(); //new出来一个退货日志
				log.setId(shopOrderRepairLogsService.getNextValId());//主键
				log.setOrderRepairId(orderReparsId);//退货id
				log.setOperator("系统");
				log.setOperate(operate);
				log.setRemark(remark);
				return shopOrderRepairLogsService.insert(log);
		    }
}
