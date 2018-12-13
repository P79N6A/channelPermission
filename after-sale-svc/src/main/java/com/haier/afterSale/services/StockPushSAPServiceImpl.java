package com.haier.afterSale.services;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.haier.afterSale.model.PushReturnInfoToGVSHandler;
import com.haier.afterSale.model.PushReturnInfoToGVSHandler.Result;
import com.haier.afterSale.model.TransferDefectiveProductsInToGVSModel;
import com.haier.afterSale.model.TransferDefectiveProductsOutToGVSModel;
import com.haier.afterSale.service.StockPushSAPService;
import com.haier.afterSale.util.Ustring;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.service.LesStockTransQueueService;
import com.haier.shop.model.OrderhpRejectionLogs;
import com.haier.shop.model.OrderhpRejectionLogsVO;
import com.haier.shop.model.OrderrepairHPrecordsVO;
import com.haier.shop.service.InvoicesService;
import com.haier.shop.service.ShopOrderRepairsService;
import com.haier.shop.service.ShopOrderhpRejectionLogsService;
import com.haier.shop.service.ShopOrderrepairHPrecordsService;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.service.StockInvMachineSetService;

/**
 * 推送库存信息到SAP
 * @author wukunyang
 *
 */
//@Configuration
//@EnableScheduling
@Service
public class StockPushSAPServiceImpl implements StockPushSAPService{
	private static Logger log = LoggerFactory.getLogger(StockPushSAPServiceImpl.class);
	@Autowired
	private ShopOrderrepairHPrecordsService shopOrderrepairHPrecordsService;
	@Autowired
	private ShopOrderRepairsService shopOrderRepairsService;
	@Autowired
	private ShopOrderhpRejectionLogsService shopOrderhpRejectionLogsService;
	@Autowired
	private TransferDefectiveProductsOutToGVSModel transferDefectiveProductsOutToGVSModel;
	@Autowired
	private OperationAreaServiceImpl operationAreaServiceImpl;
	@Autowired
	private TransferDefectiveProductsInToGVSModel transferDefectiveProductsInToGVSModel;
	@Autowired
	private LesStockTransQueueService lesStockTransQueueService;
	@Autowired
	private PushReturnInfoToGVSHandler toGVSHandler;
	@Autowired
	private InvoicesService invoicesService;
	@Autowired
	private StockInvMachineSetService stockInvMachineSetService;
	//定时推送数据到 SAP（实入）
//	@Scheduled(cron="0/5 * *  * * ?")
	public void stockPishSapxTim() throws MalformedURLException{
		List<OrderrepairHPrecordsVO> hPrecordsVOs= shopOrderrepairHPrecordsService.queryTbOrederSn(); 
		String mark ="";
		String charg ="";
		for (OrderrepairHPrecordsVO precordsVO : hPrecordsVOs) {
			//判断发票信息是否已经被作废 
//		if(invoicesService.selectCountView(precordsVO.getOrderProductId().toString())>1){
//			if("3".equals(precordsVO.getCheckType())){
//				mark ="H";
//				charg="22";
//			}else {
			//查询入22库的数据
				mark ="S";
				charg="22";
//			}
			try {
				LesStockTransQueue transQueue= lesStockTransQueueService.queryCorderSn(precordsVO.getRepairSn(),mark,charg);
				Result result=null;
					if(transQueue!=null){
						 result=toGVSHandler.process(transQueue);
						 
					}
					if(result ==null){
						log.info("物流未返回出入库流水 流水号："+precordsVO.getRepairSn());
						continue;
					}
					List<Map<String,Object>> mapListJson = (List)net.sf.json.JSONArray.fromObject(result.getMessage());
				if("S".equals(mapListJson.get(0).get("type"))){
					//推送成功之后修改退货单 推送SAP状态
					shopOrderRepairsService. updataPushSap(Ustring.getString(precordsVO.getOrderRepairId()),"1");
					//操作记录
					operationAreaServiceImpl.ProcessLog(precordsVO.getOrderRepairId(), "系统","操作", "推送SAP：成功");
					//逆向单状态改成“受理完成”
//					shopOrderRepairsService.updataStatus(precordsVO.getOrderRepairId().toString(), "3", "");
				}
			} catch (Exception e) {
				log.error("New[To Sap]:发送信息到SAP发生异常InvThTransactionID：["
						+ precordsVO.getId() + "]," + e.getMessage());
			}
//		}
		}
	}



	//逐条发送数据到SAP 推送SAP信息虚入 （对SAP进行虚入虚出的操作）
	//	 @Scheduled(cron="0/5 * *  * * ?")
	public void emptyEnterSAP() throws MalformedURLException{
		List<OrderhpRejectionLogsVO> rejectionLogsList = shopOrderhpRejectionLogsService.queryTheVirtualInto(); 
		for (OrderhpRejectionLogsVO  rejectionLogs: rejectionLogsList) {
			int virtualEntryFlag =0;
			//判断发票信息是否已经作废
//			if(invoicesService.selectCountView(rejectionLogs.getProductId().toString())>1){
			List<InvMachineSet>  invMachineSets=stockInvMachineSetService.getBySubSku(rejectionLogs.getProductTypeId());//根据sku查询是否是套机
			//如果是套机的话HP返回两条数据虚入只推一次 循环判断是否已经虚入成功 如果虚入成功的话则不需要再推
			if(invMachineSets.size()>0) {
				List<OrderhpRejectionLogs> orderhpRejectionLogs=shopOrderhpRejectionLogsService.queryVirtualEntryState(rejectionLogs.getChannelOrderSn());
				for(OrderhpRejectionLogs rejectionLogs2:orderhpRejectionLogs) {
					if("enterSuccess".equals(rejectionLogs2.getVirtualEntryState())) { 
						virtualEntryFlag=1;
					}
				}
			}
			if(virtualEntryFlag==1) {
				//如果已经推送过一次啦 则把第二条数据改成虚入成功
				shopOrderrepairHPrecordsService.UpdaVirtualEntryState(Ustring.getString(rejectionLogs.getId()), "enterSuccess");
				continue;
			}
			try {
				 String success =transferDefectiveProductsInToGVSModel.transfer(rejectionLogs);
				if("S".equals(success)){
					//推送成功之后修改HP回传详细信息 enterSuccess代表虚入成功
					shopOrderrepairHPrecordsService.UpdaVirtualEntryState(Ustring.getString(rejectionLogs.getId()), "enterSuccess");
					//操作记录
					operationAreaServiceImpl.ProcessLog(Integer.parseInt(rejectionLogs.getRepairId()),"系统", "操作", "推送SAP(虚入)：成功");
//					//逆向单状态改成“受理完成”
//					shopOrderRepairsService.updataStatus(rejectionLogs.getRepairId().toString(), "3", "");
				}
			} catch (Exception e) {
				log.error("New[To Sap]:发送信息到SAP发生异常InvThTransactionID：["
						+ rejectionLogs.getId() + "]," + e.getMessage());
			}
//			}
		}
	}

	//逐条发送数据到SAP 推送SAP信息虚出 （对SAP进行虚入虚出的操作）
	//	 @Scheduled(cron="0/5 * *  * * ?")
	public void emptyOutSAP(){
		List<OrderhpRejectionLogsVO> rejectionLogs = shopOrderhpRejectionLogsService.quereEmptyOutSAP();
		if (rejectionLogs == null || rejectionLogs.size() == 0) {
			log.info("不良品虚出信息不存在");
			return;
		}
		for (OrderhpRejectionLogsVO transaction : rejectionLogs) {
			//判断发票信息是否已经作废
//		if(invoicesService.selectCountView(transaction.getProductId().toString())>1){
			try {
				String success=	transferDefectiveProductsOutToGVSModel.transfer(transaction);

				//推送成功之后修改HP回传详细信息 outSuccess代表虚出成功
				if("S".equals(success)){
					shopOrderrepairHPrecordsService.UpdaVirtualEntryState(Ustring.getString(transaction.getId()), "outSuccess");
					//操作记录
					operationAreaServiceImpl.ProcessLog(Integer.parseInt(transaction.getRepairId()),"系统", "操作", "推送SAP(虚出)：成功");
					//逆向单状态改成“受理完成”
					shopOrderRepairsService.updataStatus(transaction.getRepairId().toString(), "3", "");
				}else {
					log.error("New[To Sap]:发送不良品虚出信息到SAP失败InvThTransactionID：["+transaction.getId()+"]");
				}
			} catch (Exception e) {
				log.error("New[To Sap]:发送不良品虚出信息到SAP发生异常InvThTransactionID：["
						+ transaction.getId() + "]," + e.getMessage());
			}
//			}
		}

	}



	
	//逐条发送数据到SAP 推送实出数据
//			 @Scheduled(cron="0/5 * *  * * ?")
		public void realOutofSAP(){
			List<OrderhpRejectionLogsVO> rejectionLogs = shopOrderhpRejectionLogsService.queryRealOutofData();
			if (rejectionLogs == null || rejectionLogs.size() == 0) {
				log.info("不良品实出信息不存在");
				return;
			}
			for (OrderhpRejectionLogsVO transaction : rejectionLogs) {
				//判断发票信息是否已经作废
//			if(invoicesService.selectCountView(transaction.getProductId().toString())>1){
				try {
					String success=	transferDefectiveProductsOutToGVSModel.transfer(transaction);

					//推送成功之后修改HP回传详细信息 outSuccess代表虚出成功
					if("S".equals(success)){
					//	pusSap 1表示实入成功 2表示实出成功
						shopOrderRepairsService. updataPushSap(Ustring.getString(transaction.getRepairId()),"2");
						//操作记录
						operationAreaServiceImpl.ProcessLog(Integer.parseInt(transaction.getRepairId()),"系统", "操作", "推送SAP(实出)：成功");
						//逆向单状态改成“受理完成”
						shopOrderRepairsService.updataStatus(transaction.getRepairId().toString(), "3", "");
					}else {
						log.error("New[To Sap]:发送不良品实出信息到SAP失败InvThTransactionID：["+transaction.getId()+"]");
					}
				} catch (Exception e) {
					log.error("New[To Sap]:发送不良品实出信息到SAP发生异常InvThTransactionID：["
							+ transaction.getId() + "]," + e.getMessage());
				}
//				}
			}

		}

}