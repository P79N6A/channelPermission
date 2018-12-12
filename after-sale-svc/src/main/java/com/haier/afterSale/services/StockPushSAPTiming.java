package com.haier.afterSale.services;

import java.net.MalformedURLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.haier.afterSale.model.CnPushReturnInfoToGVSModel;
import com.haier.afterSale.model.TransferDefectiveProductsOutToGVSModel;
import com.haier.afterSale.service.OperationAreaService;
import com.haier.afterSale.util.Ustring;
import com.haier.eis.model.VomwwwOutinstockAnalysis;
import com.haier.eis.service.EisVomwwwOutinstockAnalysisService;
import com.haier.shop.model.OrderhpRejectionLogs;
import com.haier.shop.model.OrderrepairHPrecordsVO;
import com.haier.shop.service.ShopOrderRepairsService;
import com.haier.shop.service.ShopOrderhpRejectionLogsService;
import com.haier.shop.service.ShopOrderrepairHPrecordsService;

/**
 * 推送库存信息到SAP
 * @author wukunyang
 *
 */
@Configuration
@EnableScheduling
public class StockPushSAPTiming {
	 private static Logger log = LoggerFactory.getLogger(StockPushSAPTiming.class);
	@Autowired
	private ShopOrderrepairHPrecordsService shopOrderrepairHPrecordsService;
	@Autowired
	private CnPushReturnInfoToGVSModel returnInfoToGVSModel;
	@Autowired
	private EisVomwwwOutinstockAnalysisService vomwwwOutinstockAnalysisService ;
	@Autowired
	private ShopOrderRepairsService shopOrderRepairsService;
	@Autowired
	private ShopOrderhpRejectionLogsService shopOrderhpRejectionLogsService;
	@Autowired
	private TransferDefectiveProductsOutToGVSModel transferDefectiveProductsOutToGVSModel;
	
	private OperationAreaServiceImpl operationAreaServiceImpl;
//	 private static final QName SERVICE_NAME = new QName("http://www.example.org/PushReturnInfoToGVS/", "PushReturnInfoToGVS");
//	 //定时推送库存信息到SAP
//	public void StockPishSap() throws MalformedURLException, ParseException{
//		
//		InType input = new InType();
//	    java.util.List<InType> _insertDataToHP_inputs = new ArrayList<>();
//	    String path = "file:"+ this.getClass().getResource("/wsdl_test/PushReturnInfoToGVS.wsdl").getPath();
//	    URL url = new URL(path);
//	    PushReturnInfoToGVS_Service ss = new PushReturnInfoToGVS_Service(url, SERVICE_NAME);
//	    PushReturnInfoToGVS port = ss.getPushReturnInfoToGVSSOAP();
//	    SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ); 
//	    List<OrderrepairHPrecordsVO> precordsVOList=  shopOrderrepairHPrecordsService.queryPushSAP();
//	    for(int i=0;i<precordsVOList.size();i++){
//	    OrderrepairHPrecordsVO hPrecordsVO= precordsVOList.get(i);
//	    String  lgort=	transQueue3WService.querySecCode(hPrecordsVO.getcOrderSn(), hPrecordsVO.getSku()); //根据网单号和sku查询库位
//	    input.setZWBDR(hPrecordsVO.getRepairSn());//退货单号
//	    input.setZWBDRO(hPrecordsVO.getcOrderSn());//网单号
//	    //判断退货单时间和当前服务器时间是否跨月 如果是跨月的单子则用当前服务器时间来推送
//	    if(equals(sdf.parse(hPrecordsVO.getAddTimeTs()),new Date())){
//	    	input.setZWBDT(hPrecordsVO.getAddTimeTs());
//	    }else {
//	    	input.setZWBDT(sdf.format(new Date()));
//	    }
////	    input.setKUNNRAG(returnInfoToGVSModel.);
//	    input.setZORDR(hPrecordsVO.getSourceOrderSn());//用户下单的订单号
//	    input.setZCHNL(hPrecordsVO.getSource());//渠道
//	    input.setMATNR(hPrecordsVO.getSku());
//	    input.setKWMENG(new BigDecimal(hPrecordsVO.getNumber()));//数量
//	    input.setKBETR(new BigDecimal(hPrecordsVO.getPrice()));//单价
//	    input.setLGORT(lgort);//库位
//	    input.setZFGBL(hPrecordsVO.getIsReceipt());//是否需要发票
//	    _insertDataToHP_inputs.add(input);
//	    }
//	    port.pushReturnInfoToGVS(_insertDataToHP_inputs);
//	}
	
	
//	public static boolean equals(Date date1, Date date2) {
//        
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.setTime(date1);
//        Calendar calendar2 = Calendar.getInstance();
//        calendar2.setTime(date2);
//        int year1 = calendar1.get(Calendar.YEAR);
//        int year2 = calendar2.get(Calendar.YEAR);
//        int month1 = calendar1.get(Calendar.MONTH);
//        int month2 = calendar2.get(Calendar.MONTH);
//        System.out.println(year1 + "  " + month1);
//        System.out.println(year2 + "  " + month2);
//        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
//         
//    } 
	 
	 
	 //定时推送数据到 SAP
//	 @Scheduled(cron="0/5 * *  * * ?")
	 public void StockPishSapxTim() throws MalformedURLException{
		 List<OrderrepairHPrecordsVO> hPrecordsVOs= shopOrderrepairHPrecordsService.queryTbOrederSn(); 
		 
		 for (OrderrepairHPrecordsVO precordsVO : hPrecordsVOs) {
				try {
					 VomwwwOutinstockAnalysis vomwwwOutinstockAnalysis= vomwwwOutinstockAnalysisService.quereyVOMthNO(precordsVO.getTbOrderSn());
					 if(vomwwwOutinstockAnalysis!=null){
						 returnInfoToGVSModel.send(vomwwwOutinstockAnalysis);
					 }
					 //推送成功之后修改退货单 推送SAP状态
					 shopOrderRepairsService. updataPushSap(Ustring.getString(vomwwwOutinstockAnalysis.getId()));
					 //操作记录
					 operationAreaServiceImpl.ProcessLog(precordsVO.getOrderRepairId(), "操作", "推送SAP：成功");
				} catch (Exception e) {
					log.error("New[To Sap]:发送信息到SAP发生异常InvThTransactionID：["
							+ precordsVO.getId() + "]," + e.getMessage());
				}
			}
	 }
	 
	 
	 
	 //逐条发送数据到SAP 推送SAP信息虚入 （对SAP进行虚入虚出的操作）
//	 @Scheduled(cron="0/5 * *  * * ?")
	 public void EmptyEnterSAP() throws MalformedURLException{
		 List<OrderrepairHPrecordsVO> hPrecordsVOs= shopOrderrepairHPrecordsService.queryTbOrederSn(); 
		 
		 for (OrderrepairHPrecordsVO precordsVO : hPrecordsVOs) {
				try {
					 VomwwwOutinstockAnalysis vomwwwOutinstockAnalysis= vomwwwOutinstockAnalysisService.quereyVOMthNO(precordsVO.getTbOrderSn());
					 if(vomwwwOutinstockAnalysis!=null){
						 returnInfoToGVSModel.send(vomwwwOutinstockAnalysis);
					 }
					 //推送成功之后修改退货单 推送SAP状态
					 shopOrderRepairsService. updataPushSap(Ustring.getString(vomwwwOutinstockAnalysis.getId()));
					 //操作记录
					 operationAreaServiceImpl.ProcessLog(precordsVO.getOrderRepairId(), "操作", "推送SAP：成功");
				} catch (Exception e) {
					log.error("New[To Sap]:发送信息到SAP发生异常InvThTransactionID：["
							+ precordsVO.getId() + "]," + e.getMessage());
				}
			}
	 }
	 
	 //逐条发送数据到SAP 推送SAP信息虚出 （对SAP进行虚入虚出的操作）
//	 @Scheduled(cron="0/5 * *  * * ?")
	 public void EmptyOutSAP(){
		  List<OrderhpRejectionLogs> rejectionLogs = shopOrderhpRejectionLogsService.quereEmptyOutSAP();
		  if (rejectionLogs == null || rejectionLogs.size() == 0) {
			  log.info("不良品虚出信息不存在");
				return;
			}
			for (OrderhpRejectionLogs transaction : rejectionLogs) {
				try {
					transferDefectiveProductsOutToGVSModel.transfer(transaction);
					//更改不良品记录表中的虚出状态
					shopOrderhpRejectionLogsService.updataEmptyOut(transaction.getId().toString(), "1");
				} catch (Exception e) {
					log.error("New[To Sap]:发送不良品虚出信息到SAP发生异常InvThTransactionID：["
							+ transaction.getId() + "]," + e.getMessage());
				}
			}
				
	 }

		
	 
}