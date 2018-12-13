package com.haier.afterSale.services;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.afterSale.util.OrderSnUtil;
import com.haier.common.util.StringUtil;
import com.haier.eop.data.model.StoreCodeMapping;
import com.haier.eop.data.service.StoreCodeService;
import com.haier.shop.model.*;
import com.haier.shop.service.*;
import com.haier.stock.service.StockInvMachineSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.haier.afterSale.model.CnPushReturnInfoToGVSModel;
import com.haier.afterSale.model.PushReturnInfoToGVSHandler;
import com.haier.afterSale.model.Ustring;
import com.haier.afterSale.service.CustomeAfterService;
import com.haier.afterSale.util.SequenceModel;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.model.VomInOutStoreOrder;
import com.haier.eis.model.VomwwwOutinstockAnalysis;
import com.haier.eis.service.EisVomInOutStoreOrderService;
import com.haier.eis.service.EisVomwwwOutinstockAnalysisService;
import com.haier.eis.service.LesStockTransQueueService;
import com.haier.purchase.data.model.*;
import com.haier.purchase.data.service.CnDataDirectPushService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 	售后定时任务
 * @author wukunyang
 *
 */

//@Configuration
//@EnableScheduling
@Service
public class CustomeAfterServiceImpl implements CustomeAfterService{
	private static Logger log = LoggerFactory.getLogger(CustomeAfterServiceImpl.class);
	@Autowired
	private ShopOrderrepairHPrecordsService shopOrderrepairHPrecordsService;
	@Autowired
	private OperationAreaServiceImpl operationAreaServiceImpl;
	@Autowired
	private ShopOrderRepairsService shopOrderRepairsService;//退货
	@Autowired
	private OrderRepairLESRecordsService repairLESRecordsService;//出入库信息
	@Autowired
	private ShopOrdersService shopOrdersService;//订单

	@Autowired
	private EisVomInOutStoreOrderService eisVomInOutStoreOrderService;
	@Autowired
	private CnPushReturnInfoToGVSModel returnInfoToGVSModel;
	@Autowired
    private EisVomwwwOutinstockAnalysisService      eisVomwwwOutinstockAnalysisService;
	@Autowired
	private PushReturnInfoToGVSHandler toGVSHandler;
	@Autowired
	private LesStockTransQueueService lesStockTransQueueService;
	@Autowired
	private ShopOperationAreaService shopOperationAreaService;
	@Autowired
	private InvoicesService invoicesService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderProductsNewService orderProductNewService;
    @Autowired
    private CnDataDirectPushService CnDataDirectPushService;
    @Autowired
    private SequenceModel sequenceModel;
	@Autowired
	private OrderRepairsNewService orderRepairsNewService;
    @Autowired
	private ShopOrderRepairLesreCordsService shopOrderRepairLesreCordsService;
	@Autowired
	private MemberInvoicesService memberInvoicesService;
	@Autowired
	private ShopOrderWorkflowsService shopOrderWorkflowsService;
	@Autowired
	private ShopOrderOperateLogsService shopOrderOperateLogsService;

	@Autowired
	private StoragesService storagesService;

	@Autowired
	private StockInvMachineSetService stockInvMachineSetService;
    @Autowired
	private StoreCodeService storeCodeService;

	/**
	 * 查询需要生成出入库记录的信息 定时生成
	 */
	//	@Scheduled(cron="0/5 * *  * * ?")
	public void generateOutOfStorage(){
		//查询需要生成出入库记录的信息
		List<OrderrepairHPrecords> orderrepairHPrecordsList=	shopOrderrepairHPrecordsService.queryGenerateOutOfStorage();
		//循环判断
		for(OrderrepairHPrecords precords :orderrepairHPrecordsList){
			//根据退货单id查询退货信息
			OrderRepairsVo vo=	operationAreaServiceImpl.queryOrderProductId(precords.getOrderRepairId().toString());
			if(vo==null) {
				log.info("定时生成出入库记录 err:未找到退货单");
				continue;
			}
			OrderRepairLESRecords cords= new OrderRepairLESRecords();
			cords.setOrderProductId(vo.getOrderProductId());//网单id
			cords.setOrderRepairId(vo.getId());//退货单主键
			String hpOrderFail=	Ustring.getString0(precords.getHpOrderFail());
			//判断是否是1次鉴定回传的信息
			if("1".equals(Ustring.getString(precords.getCheckType()))&&"1".equals(Ustring.getString(precords.getCheckResult()))){
				cords.setRecordSn(vo.getRepairSn());//入库单号 (单纯入22的时候用th号来做入库单号)
				cords.setOperate(2);//操作，1=出库；2=入库
				cords.setStorageType(22);//批次，22；21；10
				int s = operationAreaServiceImpl.insert(cords); //插入出库单
				//记录退货操作流程 日志
				operationAreaServiceImpl.ProcessLog(precords.getOrderRepairId(),"系统", "生成入库单", "生成22入库单:"+cords.getRecordSn());
//				//更改货物状态改成入22
//				shopOrderRepairsService.updataOrderRepairsStatus("", "122", Ustring.getString(vo.getId()));
			}else if("3".equals(Ustring.getString(precords.getCheckType()))){ //判断是否是二次鉴定回传
				//判断是否有箱可换  有箱的话 CBS给VOM发送命令 生成22出库单  和生成 10入库单号 然后 VOM再给CBS返回入库结果 
				if("1".equals(Ustring.getString(precords.getPackResult()))){
					cords.setRecordSn(vo.getcOrderSnId()+"CX1");//出库单号
					cords.setOperate(1);//操作，1=出库；2=入库
					cords.setStorageType(22);//批次，22；21；10
					int s = operationAreaServiceImpl.insert(cords); //插入出库单
					//记录退货操作流程 日志
					operationAreaServiceImpl.ProcessLog(precords.getOrderRepairId(),"系统", "生成出库单", "生成22出库单："+cords.getRecordSn());
					log.info("插入22出库单");
					if(s>0){
						cords.setRecordSn(vo.getcOrderSnId()+"CX2");//入库单号
						cords.setOperate(2);//操作，1=出库；2=入库
						cords.setStorageType(10);//批次，22；21；10
						operationAreaServiceImpl.insert(cords); //插入 入库单
						operationAreaServiceImpl.ProcessLog(precords.getOrderRepairId(),"系统", "生成入库单", "生成10入库单："+cords.getRecordSn());
						log.info("插入10入库单");
					}
				}else{
					//如果不符合条件则跳出循环
					continue;
				}
			}else {
				continue;
			}
			//更改出入库单生成状态
			shopOrderrepairHPrecordsService.updataOutOfStorage("1", precords.getId().toString());
		}
	}
	/**
	 * 发起三次鉴定时生成出22库单
	 */
//	@Scheduled(cron="0/5 * *  * * ?")
	public void notOpenBoxOutOfStorage(){
		//查询需要生成出入库记录的信息
		List<OrderrepairHPrecordsVO> orderrepairHPrecordsList=shopOrderrepairHPrecordsService.queryThreeOutOfStorage();
		if(orderrepairHPrecordsList.size()<=0) {
			log.info("未找到需要三次鉴定时生成出22库单");
			return ;
		}
		for(OrderrepairHPrecordsVO precordsVO :orderrepairHPrecordsList){
				OrderRepairLESRecords cords= new OrderRepairLESRecords();
				cords.setOrderProductId(precordsVO.getOrderProductId());//网单id
				cords.setOrderRepairId(precordsVO.getOrderRepairId());//退货单主键
				cords.setRecordSn(precordsVO.getcOrderSn()+"CX1");//出库单号
				cords.setOperate(1);//操作，1=出库；2=入库
				cords.setStorageType(22);//批次，22；21；10
				int s = operationAreaServiceImpl.insert(cords); //插入出库单
				//记录退货操作流程 日志
				operationAreaServiceImpl.ProcessLog(precordsVO.getOrderRepairId(),"系统", "生成出库单", "生成22出库单："+cords.getRecordSn());
				log.info("生成22出库单");
				//更改出入库单生成状态 改成3
				shopOrderrepairHPrecordsService.updataOutOfStorage("3", precordsVO.getId().toString());
				log.info("更改出入库单生成状态");
				//更改货物状态改成入22
				shopOrderRepairsService.updataOrderRepairsStatus("", "122", Ustring.getString(precordsVO.getOrderRepairId()));
		}

	}


	/**
	 * 未开箱正品
	 * @throws MalformedURLException 
	 */
		 //@Scheduled(cron="0/5 * *  * * ?")
	public void notOutBoxQuality() throws MalformedURLException{
		log.info("未开箱正品 定时任务开始"); 
		List<OrderrepairHPrecordsVO> precordsVOs =shopOrderrepairHPrecordsService.queryNotOutBoxQuality();
		for(OrderrepairHPrecordsVO vo :precordsVOs){
			//查询是否已经签收
			String signTim = shopOrderRepairsService.queryIsRejectionSign(Ustring.getString(vo.getOrderProductId()));
			if("3W".equals(vo.getStockType())){
				if(signTim == null || !Ustring.isNotEmpty(signTim) || Integer.parseInt(signTim)<= 0){
					continue;
				}
			}
			//if(Ustring.isNotEmpty(signTim)&& Integer.parseInt(signTim)>0){//如果签收时间不为null的话
				OrderRepairLESRecords cords= new OrderRepairLESRecords();
				cords.setOrderProductId(vo.getOrderProductId());//网单id
				cords.setOrderRepairId(vo.getOrderRepairId());//退货单主键
				cords.setRecordSn(vo.getRepairSn());//入库单号
				cords.setOperate(2);//操作，1=出库；2=入库
				cords.setStorageType(10);//批次，22；21；10
				//插入10入库单
				if(operationAreaServiceImpl.insert(cords)>0){
					//更改出入库信息生成状态
					shopOrderrepairHPrecordsService.updataOutOfStorage("1", vo.getId().toString());

					//记录日志
					operationAreaServiceImpl.ProcessLog(vo.getOrderRepairId(),"系统", "生成入库单", "生成10入库单："+cords.getRecordSn());
					log.info("插入10入库单");
				}
				log.info("已经签收流程  成功");
			//}
//			else {//如果未签收的话
//                //查询3w入库信息
//                List<ReturnTable> list_3w = CnDataDirectPushService.find3WInStock();
//                for(ReturnTable rt:list_3w) {
//                    JSONObject jsonobject = JSONObject.fromObject(rt.getData());
//                    JSONArray getJsonArray=JSONArray.fromObject(jsonobject.get("orderItems"));
//                    for(int i=0;i<getJsonArray.size();i++) {
//                        JSONObject getJsonObj = getJsonArray.getJSONObject(i);//获取json数组中的第i项 
//                        //根据3w入库信息去匹配网单和订单是否存在
//                        if(vo.getOid().equals(getJsonObj.get("subSourceOrderCode")) ) {
//                        	CnDataDirectPush cnData = new CnDataDirectPush();
//                        	cnData.setId(rt.getId());
//                        	CnDataDirectPushService.update(cnData);
//							//作废发票
//							if(Ustring.isNotEmpty(vo.getInvoIceId())){
//								operationAreaServiceImpl.InvalidInvoices(vo.getInvoIceId(), "退换货");
//								operationAreaServiceImpl.ProcessLog(vo.getOrderRepairId(),"系统", "作废发票", "发起作废发票请求");
//							}
//                        }
//                        else {
//                        	CnDataDirectPushService.updateRtExpection(rt);
//                        }
//                        
//				    }
//                }
//			}
		}
	}
	/**
	 * 未开箱正品 需要作废发票的信息（已签收）
	 */
	public void signinInvalidatedInvoice() {
		log.info("未开箱正品 需要作废发票的信息 定时任务"); 
		List<OrderrepairHPrecordsVO> precordsVOs =shopOrderrepairHPrecordsService.SigninInvalidatedInvoiceView();
		for(OrderrepairHPrecordsVO vo :precordsVOs){
			//查询是否已经签收
			String signTim = shopOrderRepairsService.queryIsRejectionSign(Ustring.getString(vo.getOrderProductId()));
			if(Ustring.isNotEmpty(signTim)&& Integer.parseInt(signTim)>0){//如果签收时间不为null的话
				//根据退货单号主键查询是否VOM已经把入库信息回传到CBS修改
				List<OrderRepairLESRecords> repairLESRecords=shopOrderRepairLesreCordsService.queryRecordSn("2","10",vo.getOrderRepairId().toString()); 
				VomInOutStoreOrder vomInOutStoreOrder = new VomInOutStoreOrder();
				for(OrderRepairLESRecords orderRepairLESRecords :repairLESRecords){
					//根据出入库单号查询出入库明细表来判断VOM是否已经把出入库流水返回到CBS
					vomInOutStoreOrder= eisVomInOutStoreOrderService.queryVomInTenlibrary(orderRepairLESRecords.getRecordSn());
				}
				//判断是否有VOM出入库信息
				if(vomInOutStoreOrder!=null){
					operationAreaServiceImpl.InvalidInvoices(vo.getInvoIceId(), "退换货");
					operationAreaServiceImpl.ProcessLog(vo.getOrderRepairId(),"系统", "作废发票成功", "发起作废发票请求");
				}
			}
		}
		
		
	}


	/**
	 * 查询未开箱正品需要作废发票和推送SAP的信息(已签收)
	 * @throws
	 */
		/*@Scheduled(cron="0/5 * *  * * ?")*/

	@Override
    public void notOutBoxStockPishSAP() {
		log.info("已经签收流程  推送SAP"); 
		//查询
		List<OrderrepairHPrecordsVO> precordsVOs =shopOrderrepairHPrecordsService.quertNotOutBoxStockPishSAP();
		for(OrderrepairHPrecordsVO hPrecordsVO :precordsVOs){
			//判断发票信息是否已经被作废 
//		if(invoicesService.selectCountView(hPrecordsVO.getOrderProductId().toString())>1){
				//查询入10库的出入库信息
				List<OrderRepairLESRecords> repairLESRecords=repairLESRecordsService.queryRecordSn("2","10",hPrecordsVO.getOrderRepairId().toString());
				VomInOutStoreOrder storeOrder= null;
				//查询出VOM入库流水是否已经返回到本系统
				for(int i = 0;i<repairLESRecords.size();i++){
					storeOrder=eisVomInOutStoreOrderService.queryVomInTenlibrary(repairLESRecords.get(i).getRecordSn());
				}
				//如果VOM出入库流水已经到本系统 则作废发票 推送SAP
				if(storeOrder!=null){
					LesStockTransQueue transQueue= lesStockTransQueueService.queryCorderSn(hPrecordsVO.getRepairSn(),"S","10");
					com.haier.afterSale.model.PushReturnInfoToGVSHandler.Result result=null;
					if(transQueue!=null){
						try {
							result=toGVSHandler.process(transQueue);
						} catch (MalformedURLException e) {
							log.error("已经签收流程  推送SAP error", e);
                            continue;
						}
					}
					if(result==null) {
						continue;
					}
					List<Map<String,Object>> mapListJson = (List)net.sf.json.JSONArray.fromObject(result.getMessage());
					log.info(mapListJson.toString()); 
					if("S".equals(mapListJson.get(0).get("type"))){
						//推送成功之后修改退货单 推送SAP状态
						shopOrderRepairsService. updataPushSap(Ustring.getString(hPrecordsVO.getOrderRepairId()),"1");
						operationAreaServiceImpl.ProcessLog(hPrecordsVO.getOrderRepairId(),"系统", "VOM返回入库流水", "VOM返回入库流水成功！");
						//操作记录
						operationAreaServiceImpl.ProcessLog(hPrecordsVO.getOrderRepairId(), "系统","操作", "推送SAP：成功");
						//				//作废发票

						OrderrepairHPrecordsVO invoice = shopOrderrepairHPrecordsService.findInvoice(hPrecordsVO.getOrderProductId());
						if (invoice != null && invoice.getInvoIceId() != null && invoice.getStatusType() == 1 && invoice.getInvoiceSuccess() == 1){
                            operationAreaServiceImpl.InvalidInvoices(invoice.getInvoIceId(), "退换货");
                            operationAreaServiceImpl.ProcessLog(hPrecordsVO.getOrderRepairId(), "系统","作废发票", "发起作废发票请求");

                        }

						//更改发票和货物状
						shopOrderRepairsService.updataOrderRepairsStatus("4", "", Ustring.getString(hPrecordsVO.getOrderRepairId()));
						//逆向单状态改成“受理完成”
						shopOrderRepairsService.updataStatus(hPrecordsVO.getOrderRepairId().toString(), "3", "");
						
						shopOperationAreaService.updateStatus(hPrecordsVO.getOrderProductId().toString(),"110"); //正向单关闭 
						operationAreaServiceImpl.ProcessLog(hPrecordsVO.getOrderRepairId(),"系统", "系统联动取消网单的操作", "系统同步退换货申请,取消网单");

						//更改货物状态改成入10
						shopOrderRepairsService.updataOrderRepairsStatus("", "110", Ustring.getString(hPrecordsVO.getOrderRepairId()));

						//				}
						List<OrderProducts> orderProducts=shopOperationAreaService.queryOrderProductStatus(hPrecordsVO.getOrderProductId().toString());
			    		if(orderProducts.size() <= 1){
							for(OrderProducts products: orderProducts) {
								if(products.getcOrderSn().equals(hPrecordsVO.getcOrderSn())) {
									shopOrdersService.updataOrdersStatus(hPrecordsVO.getOrderId());//关闭订单
									operationAreaServiceImpl.ProcessLog(hPrecordsVO.getOrderRepairId(), "系统", "系统联动取消订单的操作", "系统同步退换货申请,取消订单");
								}
							}
						}
					}
				}
//			}

		}
	}

    /**
     * 小家电物流状态更新
     */
    public void b2cstateOfgoods() {

        log.info("小家电物流状态更新 定时任务 start");
        List<OrderRepairLESRecords> repairLESRecords=shopOrderRepairLesreCordsService.b2cqueryStorageType();
        VomInOutStoreOrder vomInOutStoreOrder = null;
        for(OrderRepairLESRecords records : repairLESRecords) {
            //根据出入库单号查询出入库明细表来判断VOM是否已经把出入库流水返回到CBS
            String recordSn = records.getRecordSn();
            String ordercn = recordSn.substring(0, recordSn.length() - 3);
            vomInOutStoreOrder =eisVomInOutStoreOrderService.queryGetStoreCode(records.getStorageType().toString(),records.getOperate().toString(), recordSn);//根据退货单好查询 vom返回的C码
//			vomInOutStoreOrder = eisVomInOutStoreOrderService.queryVomInOut(records.getRecordSn());
            //如果VOM已经把流水返回到CBS 则更改退货表的货物状态
            if(vomInOutStoreOrder != null) {
                //更改货物状态改成入10
                shopOrderRepairsService.updataOrderRepairsStatus("", "110", Ustring.getString(records.getOrderRepairId()));
                shopOrderRepairsService.updataStatus(Ustring.getString(records.getOrderRepairId()), "3", "已入库10，退货单关闭");

				operationAreaServiceImpl.ProcessLog(records.getOrderRepairId(), "系统", "操作", "已入库10，退货单关闭");

                //释放库存
                com.alibaba.fastjson.JSONObject releaseResult = operationAreaServiceImpl.releaseStock(ordercn);

                //推sap
                LesStockTransQueue transQueue= lesStockTransQueueService.queryCorderSn(records.getRecordSn(),"S","10");
                com.haier.afterSale.model.PushReturnInfoToGVSHandler.Result result=null;

                try {

                    if(transQueue!=null){

                        result=toGVSHandler.process(transQueue);

                    }
                } catch (Exception e) {
                    log.error("小家电物流信息入10后推送sap异常", e);
                    operationAreaServiceImpl.ProcessLog(records.getOrderRepairId(), "系统","推sap", "系统推送sap异常！");
                }

                if(result==null) {
                    operationAreaServiceImpl.ProcessLog(records.getOrderRepairId(), "系统","推sap", "系统推送sap失败！");

                }
                List<Map<String,Object>> mapListJson = (List)net.sf.json.JSONArray.fromObject(result.getMessage());
                log.info("小家电入10库后推送sap返回：" + mapListJson.toString());
                if("S".equals(mapListJson.get(0).get("type"))){
                    operationAreaServiceImpl.ProcessLog(records.getOrderRepairId(), "系统","推sap", "系统推送sap成功！");
                    //作废发票
                    OrderrepairHPrecordsVO invoice = shopOrderrepairHPrecordsService.findInvoice(records.getOrderProductId());
                    if (invoice != null && invoice.getInvoIceId() != null && invoice.getStatusType() == 1 && invoice.getInvoiceSuccess() == 1){
                        operationAreaServiceImpl.InvalidInvoices(invoice.getInvoIceId(), "退换货");
                        operationAreaServiceImpl.ProcessLog(records.getOrderRepairId(), "系统","作废发票", "发起作废发票请求");

                    }
                    //更改发票和货物状
                    shopOrderRepairsService.updataOrderRepairsStatus("4", "", Ustring.getString(records.getOrderRepairId()));
                    //逆向单状态改成“受理完成”
                    shopOrderRepairsService.updataStatus(records.getOrderRepairId().toString(), "3", "");

                    shopOperationAreaService.updateStatus(records.getOrderProductId().toString(),"110"); //关闭网单
                    operationAreaServiceImpl.ProcessLog(records.getOrderRepairId(),"系统", "系统联动取消网单的操作", "系统同步退换货申请,取消网单");
                    //更改货物状态改成入10
                    shopOrderRepairsService.updataOrderRepairsStatus("", "110", Ustring.getString(records.getOrderRepairId()));

                    List<OrderProducts> orderProducts=shopOperationAreaService.queryOrderProductStatus(records.getOrderProductId().toString());
                    if(orderProducts.size() == 0){
                        shopOrdersService.updataOrdersStatus(String.valueOf(orderProducts.get(0).getOrderId()));//关闭订单
                        operationAreaServiceImpl.ProcessLog(records.getOrderRepairId(), "系统", "系统联动取消订单的操作", "系统同步退换货申请,取消订单");

                    }
                }

            }
        }

    }
	
	/**
	 * 3w换货换表操作
	 * @throws ParseException 
	 */
//	@Scheduled(cron="0/5 * *  * * ?")
    public void exchangeTable() throws ParseException {
// 		log.info("3w换货换表操作 定时任务开始");
        // TODO Auto-generated method stub
    	//查询3w入库信息要大于新表的最大id
    	String max = CnDataDirectPushService.findMax();
    	if(max==null || "".equals(max)) {
    		max = "0";
    	}
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            List<CnDataDirectPush> list_3wExchange = CnDataDirectPushService.find3WExchange(Integer.parseInt(max));
            List<ExchangeGoods> list_ex = new ArrayList<ExchangeGoods>();
            for (CnDataDirectPush cnData : list_3wExchange) {
                JSONObject jsonobject = JSONObject.fromObject(cnData.getData());
                if (jsonobject.get("orderType").equals("503")) {
                    JSONArray getJsonArray = JSONArray.fromObject(jsonobject.get("orderItems"));
					JSONArray tmsJsonArray = JSONArray.fromObject(jsonobject.get("tmsOrders"));
                    ExchangeGoods ex = new ExchangeGoods();
                    if (getJsonArray.size() == 1) {
                        ex.setItemcode((String) getJsonArray.getJSONObject(0).get("scItemCode"));
                    }
                    if (getJsonArray.size() == 2) {//证明是空调 需要去匹配主物料号
                    	try{
						String subSku1 = ((String) getJsonArray.getJSONObject(0).get("scItemCode"));
						String subSku2 = ((String) getJsonArray.getJSONObject(1).get("scItemCode"));
						String mainSku = operationAreaServiceImpl.findBySubSku(subSku1, subSku2);//根据两个子物料号匹配到主物料号
						ex.setItemcode(mainSku);
                    }catch (Exception e){
							log.error("查询主物料异常"+ e);
							e.printStackTrace();
							throw e;
						}
                    }
					ex.setData(cnData.getData());
					ex.setId(cnData.getId());
					ex.setTradeitemid((String) getJsonArray.getJSONObject(0).get("tradeItemId"));
					ex.setConsigntime(sdf.parse((String) jsonobject.get("consignTime")));
					ex.setItemamount(Long.parseLong((String) getJsonArray.getJSONObject(0).get("itemAmount")));
					ex.setOrdercode((String) jsonobject.get("orderCode"));
					ex.setStorecode((String) jsonobject.get("storeCode"));
					ex.setItemquantity(Integer.parseInt((String) getJsonArray.getJSONObject(0).get("itemQuantity")));
					ex.setTradeid((String) getJsonArray.getJSONObject(0).get("tradeId"));
					ex.setStoreordercode((String) jsonobject.get("storeOrderCode"));
					ex.setPackageCode((String) tmsJsonArray.getJSONObject(0).get("packageCode"));
					ex.setTmsOrderCode((String) tmsJsonArray.getJSONObject(0).get("tmsOrderCode"));
					list_ex.add(ex);
                }
                  max=String.valueOf(cnData.getId());
            }

		CnDataDirectPushService.update3WDataMaxId(max);
            if (list_ex.size() != 0) {
                CnDataDirectPushService.insertEx(list_ex);

//                log.info("3w换货换表操作完成");
            }

        }


    
	/**
	 * 3w退货换表操作
	 */
//	@Scheduled(cron="0/5 * *  * * ?")
    public void returnTable() throws ParseException {
        // TODO Auto-generated method stub
//		log.info("3w退货换表操作 定时任务开始");
    	//查询3w入库信息要大于新表的最大id
    	String max = CnDataDirectPushService.findReturnMax();
		if(max==null || "".equals(max)) {
    		max = "0";
    	}

        List<CnDataDirectPush> list_3wReturn = CnDataDirectPushService.find3WReturn(Integer.parseInt(max));
        List<ReturnTable> list_rt = new ArrayList<ReturnTable>();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for(CnDataDirectPush cnData:list_3wReturn) {
            JSONObject jsonobject = JSONObject.fromObject(cnData.getData());
         //   if(jsonobject.get("orderType").equals("503")) {
            	JSONArray getJsonArray=JSONArray.fromObject(jsonobject.get("orderItems"));
			JSONArray sendinfo =JSONArray.fromObject(jsonobject.get("senderInfo"));

			ReturnTable rt = new ReturnTable();
            	rt.setData(cnData.getData());
            	rt.setId(cnData.getId());
            	rt.setSubSourceOrderCode((String)getJsonArray.getJSONObject(0).get("subSourceOrderCode"));
            	rt.setActualQty(Integer.parseInt((String)getJsonArray.getJSONObject(0).get("actualQty")));
            	rt.setDetailAddress((String)sendinfo.getJSONObject(0).get("detailAddress"));
            	rt.setItemCode((String)getJsonArray.getJSONObject(0).get("itemCode"));
            	rt.setMobile((String)sendinfo.getJSONObject(0).get("mobile"));
            	rt.setName((String)sendinfo.getJSONObject(0).get("name"));
            	rt.setReturnOrderId((String) jsonobject.get("returnOrderId"));
            	rt.setWarehouseCode((String) jsonobject.get("warehouseCode"));
            	rt.setOrderConfirmTime(sdf.parse((String) jsonobject.get("orderConfirmTime")));
            	list_rt.add(rt);
           // }
          }
		if(list_rt.size()!=0) {
			CnDataDirectPushService.insertRt(list_rt);
			log.info("3w退货换表操作完成");
		}
    }
    
    
    /**
     * 定时处理3w换货
     */
//	@Scheduled(cron="0/5 * *  * * ?")
    public void exchangeGoods_3W() {
        // TODO Auto-generated method stub
//		log.info("3w换货操作 定时任务开始");
    	//查询3w入库信息
        List<ExchangeGoods> list_ex = CnDataDirectPushService.findExchangeTable();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if(list_ex.size()!=0) {
        for(ExchangeGoods ex:list_ex) {
           this.matching(ex);

          }
        }
    }
    
    /**
     * 3w未签收的退货推送sap
     * @throws ParseException 
     */
//	@Scheduled(cron="0/5 * *  * * ?")
	public void returnGoodsPushSAP() throws MalformedURLException, ParseException {
		// TODO Auto-generated method stub
		log.info("3w未签收的退货推送sap操作 定时任务开始");
                //查询3w入库信息查询的是换货新表
		try {
            List<ReturnTable> list_3w = CnDataDirectPushService.find3WInStock();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (ReturnTable rt : list_3w) {
                JSONObject jsonobject = JSONObject.fromObject(rt.getData());
                JSONArray getJsonArray = JSONArray.fromObject(jsonobject.get("orderItems"));
                for (int i = 0; i < getJsonArray.size(); i++) {
                    JSONObject getJsonObj = getJsonArray.getJSONObject(i);//获取json数组中的第i项
                    //根据3w入库信息去匹配网单和订单是否存在
                    List<OrderrepairHPrecordsVO> list_vo = shopOrderrepairHPrecordsService.findByOid((String) getJsonObj.get("subSourceOrderCode"));
                    if (list_vo.size() != 0) {
						for (OrderrepairHPrecordsVO vo : list_vo){
							if (Ustring.isNotEmpty(vo.getVomRepairSn()) && vo.getVomRepairSn().contains("TH")) {//判断是否是未签收

								//根据网单id查找最大的发票id(这个发票才是有效的)
								OrderrepairHPrecordsVO invoice = shopOrderrepairHPrecordsService.findInvoice(vo.getOrderProductId());
								if (invoice != null) {
									if (invoice.getInvoiceSuccess() == 1 && invoice.getStatusType() == 1) {//判断是否开发票
										operationAreaServiceImpl.InvalidInvoices(String.valueOf(invoice.getInvoIceId()), "退货或拦截");
										operationAreaServiceImpl.ProcessLog(vo.getOrderRepairId(), "系统", "作废发票", "发起作废发票请求");
									}
								}
								List<VomwwwOutinstockAnalysis> list_eis = eisVomwwwOutinstockAnalysisService.outStockSap(vo.getTbOrderSn());
								if (list_eis.size() > 0) {//判读是否出库并且推送sap成功
									//根据菜鸟仓库编码找到海尔仓库编码
//									StoreCodeMapping storeCodeMapping = storeCodeService.findByCainiaoStoreCode((String) jsonobject.get("warehouseCode"));
//									StoragesWwwRelas storagesWwwRelas = storagesService.findByCainiaoStoreCode((String) jsonobject.get("warehouseCode"));

                                    StoreCodeMapping warehouseCode = storeCodeService.findByCainiaoStoreCode((String) jsonobject.get("warehouseCode"));

                                    com.haier.afterSale.model.CnPushReturnInfoToGVSModel.Result result = null;
									VomwwwOutinstockAnalysis vomwwwOutinstockAnalysis = new VomwwwOutinstockAnalysis();
									vomwwwOutinstockAnalysis.setTradeNo((String) getJsonObj.get("sourceOrderCode"));
									vomwwwOutinstockAnalysis.setSubTradeNo((String) getJsonObj.get("subSourceOrderCode"));
									vomwwwOutinstockAnalysis.setScOrderNo(vo.getcOrderSn());
									//vomwwwOutinstockAnalysis.setOrderStatus(0);
									vomwwwOutinstockAnalysis.setItemNo((String) getJsonObj.get("itemId"));
									//vomwwwOutinstockAnalysis.setCertification("");
									vomwwwOutinstockAnalysis.setLBXNo((String) jsonobject.get("returnOrderId"));
									//查询子物料,如果有子物料传子物料,没有传网单中的sku
									String subSku = stockInvMachineSetService.querySubsku(vo.getSku());
									if (Ustring.isEmpty(subSku)) {
										vomwwwOutinstockAnalysis.setSku(vo.getSku());
									} else {
										vomwwwOutinstockAnalysis.setSku(subSku);
									}
									//vomwwwOutinstockAnalysis.setType(1);
									vomwwwOutinstockAnalysis.setNum(Integer.parseInt((String) getJsonObj.get("actualQty")));
									//vomwwwOutinstockAnalysis.setReceiptVoucher("");
									vomwwwOutinstockAnalysis.setOutInDate(sdf.parse((String) jsonobject.get("orderConfirmTime")));
									//vomwwwOutinstockAnalysis.setTBNo("");
//                            vomwwwOutinstockAnalysis.setWWWStock((String) jsonobject.get("warehouseCode"));
									//vomwwwOutinstockAnalysis.setHpInfo("");
									vomwwwOutinstockAnalysis.setBackNo(vo.getVomRepairSn());
									//vomwwwOutinstockAnalysis.setBatch("");

//									vomwwwOutinstockAnalysis.setWWWStock(storagesWwwRelas.getWwwstorcode());
                                    vomwwwOutinstockAnalysis.setWWWStock(warehouseCode.getHaierStoreCode());

									vomwwwOutinstockAnalysis.setExpressNum((String) jsonobject.get("expressCode"));
									result = returnInfoToGVSModel.send(vomwwwOutinstockAnalysis); //推送SAP
									if (result.getStatus() == 1) {
										List<Map<String, Object>> mapListJson = (List) net.sf.json.JSONArray.fromObject(result.getMessage());
										if ("S".equals(mapListJson.get(0).get("type"))) {
											shopOperationAreaService.updateStatus(vo.getOrderProductId().toString(), "110"); //发起二次鉴定的同时要把正向单关闭
											operationAreaServiceImpl.ProcessLog(vo.getOrderRepairId(), "系统", "关闭", "关闭正向单");
											//推送成功之后修改退货单 推送SAP状态
											shopOrderRepairsService.updataPushSap(Ustring.getString(vo.getOrderRepairId()), "1");
											//操作记录
											operationAreaServiceImpl.ProcessLog(vo.getOrderRepairId(), "系统", "操作", "推送SAP：成功");
											//逆向单状态改成“受理完成”
											shopOrderRepairsService.updataStatus(vo.getOrderRepairId().toString(), "3", "");
											CnDataDirectPush cnData = new CnDataDirectPush();
											cnData.setId(rt.getId());
											CnDataDirectPushService.update(cnData);//匹配成功修改cndata表
											CnDataDirectPushService.updateReturn(rt);//匹配成功修改状态returTable表
										}
									}

								} else {//没有推送sap的 直接修改状态
									orderProductNewService.interceptCancelClose(vo.getOrderProductId(), (new Date().getTime()) / 1000);//取消原网单并更改拦截状态
									CnDataDirectPush cnData = new CnDataDirectPush();
									cnData.setId(rt.getId());
									CnDataDirectPushService.update(cnData);//匹配成功修改cndata表
									CnDataDirectPushService.updateReturn(rt);//匹配成功修改状态returTable表
								}

							}
					}
                    } else {
                        CnDataDirectPushService.updateRtExpection(rt);
                    }

                }
            }
			log.info("3w未签收的退货推送sap操作 定时任务完成");
        }catch (Exception e){
		    log.error("returnGoodsPushSAP error", e);
		    e.printStackTrace();
		    throw e;
        }

	}



	/**
	 * 3w拦截
	 * @throws MalformedURLException 
	 * @throws ParseException 
	 */
	//@Scheduled(cron="0/5 * *  * * ?")
	/*public void intercept_3w() throws MalformedURLException, ParseException {
		// TODO Auto-generated method stub
		log.info("3w拦截操作 定时任务开始");
    	//查询3w入库信息
        List<ReturnTable> list_3w = CnDataDirectPushService.find3WInStock();

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for(ReturnTable rt:list_3w) {
            JSONObject jsonobject = JSONObject.fromObject(rt.getData());
            JSONArray getJsonArray=JSONArray.fromObject(jsonobject.get("orderItems"));
            for(int i=0;i<getJsonArray.size();i++) {
                JSONObject getJsonObj = getJsonArray.getJSONObject(i);//获取json数组中的第i项 
                //根据3w入库信息去匹配网单
                OrderProducts vo = shopOperationAreaService.findOrderProductByOid((String)getJsonObj.get("subSourceOrderCode"));
                if(vo!=null ) {
                	 List<VomwwwOutinstockAnalysis> list_eis = eisVomwwwOutinstockAnalysisService.outStockSap();
                	 CnDataDirectPush cnData = new CnDataDirectPush();
                 	cnData.setId(rt.getId());
                 	CnDataDirectPushService.update(cnData);//匹配成功修改cndata表
					CnDataDirectPushService.updateReturn(rt);//匹配成功修改状态returTable表
			        orderProductNewService.interceptCancelClose(vo.getId(),(new Date().getTime())/1000);//取消原网单并更改拦截状态

			        if(vo.getSuccess()==1&&vo.getStatusType()==1) {//判断是否开发票
                            operationAreaServiceImpl.InvalidInvoices(String.valueOf(vo.getInvoIceId()), "拦截");
			        }
                }
                else {
                	CnDataDirectPushService.updateRtExpection(rt);
                }
            }
          }
	   
	}*/

	/**
	 * 匹配网单并处理异常
	 * @param ex
	 */
	public void matching(ExchangeGoods ex) {
        //根据3w入库信息去匹配网单和订单是否存在,条件是来源订单号sourceOrderSn和sku
       List<OrderProducts>  list_op = shopOperationAreaService.findOrderProduct(ex.getTradeid(),ex.getItemcode());
        if(list_op.size()>0) {
				this.handleOrderProduct(list_op.get(0),ex);
        }
        else {//如果没有匹配到 ,根据在线交易流水号tradeSn和sku去匹配
			List<OrderProducts>  list_op1 = shopOperationAreaService.findByTradeSn(ex.getTradeid(),ex.getItemcode());
			if(list_op1.size()>0) {
				this.handleOrderProduct(list_op1.get(0),ex);
			}
			else{//异常处理
				CnDataDirectPushService.updateExpection(ex);
			}
        }
    
	}

	/**
	 * 构造订单操作日志对象
	 * @param orders 订单对象
	 * @param orderProducts  网单对象
	 * @param operator 操作人
	 * @param changeLog 变更记录
	 * @param remark 备注
	 * @param log OrderOperateLogs
	 * @return
	 */
	public static OrderOperateLogs constructOperateLog(Orders orders, OrderProducts orderProducts,
													   String operator, String changeLog,
													   String remark, OrderOperateLogs log) {
		log = new OrderOperateLogs();
		log.setChangeLog(StringUtil.isEmpty(changeLog) ? "" : changeLog);
		log.setOperator(StringUtil.isEmpty(operator) ? "系统" : operator);
		log.setPaymentStatus(
				null == orders || orders.getPaymentStatus() == null ? 0 : orders.getPaymentStatus());
		log.setRemark(StringUtil.isEmpty(remark) ? ""
				: (remark.length() > 255 ? remark.substring(0, 255) : remark));
		log.setSiteId(1);
		log.setOrderId(Integer.valueOf(orders.getId()));
		if (orderProducts != null) {
			log.setNetPointId(orderProducts.getNetPointId());
			log.setOrderProductId(orderProducts.getId());
			log.setStatus(orderProducts.getStatus());
		} else {
			log.setNetPointId(0);
			log.setOrderProductId(0);
			log.setStatus(0);
		}
		return log;
	}

	/**
	 * 匹配到网单后,处理网单数据
	 * @param orderProducts 网单数据
	 * @param ex 换货数据
	 */
	public void handleOrderProduct(OrderProducts  orderProducts,ExchangeGoods ex){
		//复制新网单isBackend isBook outping lessShipTime 这些字段都是待确认的
		String OrderSn = String.valueOf(sequenceModel.getSnowFlake());
		CnDataDirectPush cnData = new CnDataDirectPush();
		cnData.setId(ex.getId());

		Orders order = ordersService.get(orderProducts.getOrderId());
		//根据旧的网单id查询订单全流程监控表
		OrderWorkflows orderWorkflows = shopOrderWorkflowsService.getByOrderProductId(orderProducts.getId());
		MemberInvoices memberInvoices = memberInvoicesService.getByOrderId(order.getId());//根据旧的订单id查找用户发票信息
		//插入操作日志旧的订单id和网单id
		shopOrderOperateLogsService.insert(constructOperateLog(order, orderProducts, "系统", "生成新的换机单",
				"原订单号为:" + order.getOrderSn()+",原网单号为:"+orderProducts.getCOrderSn(), null));
		if(orderWorkflows!=null&&memberInvoices!=null) {
			Long time = (new Date().getTime()) / 1000; //统一系统时间
			order.setId(null);
			order.setRelationOrderSn(order.getOrderSn());
			order.setOrderSn(OrderSn);
			order.setOrderStatus(201);
			order.setAddTime(time);
			order.setSyncTime(time);
			order.setConfirmTime(Integer.parseInt(time + ""));
			order.setFirstConfirmTime(Integer.parseInt(time + ""));
			order.setFinishTime(null);
			order.setSmConfirmStatus(1);
			order.setPoints((long) 0);
			order.setLbxSn(ex.getStoreordercode());
			int OrderId = ordersService.insertOrders(order);
			Orders orderNew = ordersService.get(OrderId);
			memberInvoices.setOrderId(OrderId);//复制用户发票表更改订单id
			int memberInvoicesId = memberInvoicesService.insert(memberInvoices);
			//回填用户发票id
			ordersService.updateMemberInvoicesId(memberInvoicesId,OrderId);
			ex.setOrderProductsCn(orderProducts.getcOrderSn());

			orderProducts.setId(null);
			orderProducts.setOrderId(OrderId);
			orderProducts.setTbOrderSn("");
			orderProducts.setLockedNumber(0);
			orderProducts.setUnlockedNumber(0);
//
			orderProducts.setStatus(8);
			orderProducts.setInvoiceNumber("");
			orderProducts.setExpressName("");
			orderProducts.setLessOrderSn("");
			orderProducts.setWaitGetLesShippingInfo(0);
			orderProducts.setOutping("");
			orderProducts.setLessShipTime(0);
//
			orderProducts.setCloseTime(0);
			orderProducts.setIsMakeReceipt(9);
			orderProducts.setReceiptNum("");
			orderProducts.setReceiptAddTime("");
			orderProducts.setSystemRemark("");
			orderProducts.setHpRegisterDate(0);
			orderProducts.setHpReservationDate(0);
			orderProducts.setOid(ex.getTradeitemid());
			orderProducts.setPoints((long) 0);
			orderProducts.setTbOrderSn("");
            orderProducts.setNetPointId(0);
            orderProducts.setScode("");
            orderProducts.setTsCode("");

			int orderProductId = orderProductNewService.insert(orderProducts);
			String cOrderSn = OrderSnUtil.getCOrderSn(orderProductId);
			orderProducts.setId(orderProductId);
			orderProducts.setCOrderSn(cOrderSn);
			orderProducts.setLessOrderSn(cOrderSn);
			orderProductNewService.updateCOrderSn(orderProducts);
			shopOrderOperateLogsService.insert(constructOperateLog(orderNew, orderProducts, "系统", "生成新的换机单",
					"新订单号为:" + orderNew.getOrderSn()+",新网单号为:"+cOrderSn, null));
            OrderWorkflows newOrderWorkflows = new OrderWorkflows();
            newOrderWorkflows.setOrderId(OrderId);
            newOrderWorkflows.setOrderProductId(orderProductId);
            newOrderWorkflows.setAddTime(orderWorkflows.getAddTime());
            newOrderWorkflows.setPayTime(0L);
			
			shopOrderWorkflowsService.insert(newOrderWorkflows);//复制订单全流程监控表更改订单网单id
			ex.setOrderProductsCnNew(cOrderSn);
			CnDataDirectPushService.updateWD(ex);
			CnDataDirectPushService.update(cnData);//匹配成功修改cndata表

			orderRepairsNewService.updateExchange(orderProducts.getRepairId());
		}
	}


}
