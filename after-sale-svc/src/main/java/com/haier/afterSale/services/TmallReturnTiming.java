package com.haier.afterSale.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.haier.afterSale.model.Ustring;
import com.haier.shop.model.OrderProductsVo;
import com.haier.shop.model.OrderRepairLogs;
import com.haier.shop.model.OrderRepairsVo;
import com.haier.shop.model.OrderTmallReturnLogs;
import com.haier.shop.model.RefundBillSyncRecord;
import com.haier.shop.service.RefundBillSyncRecordService;
import com.haier.shop.service.ShopOperationAreaService;
import com.haier.shop.service.ShopOrderRepairLogsService;
import com.haier.shop.service.ShopOrderRepairsService;
import com.haier.shop.service.ShopOrderTmallReturnLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时匹配天猫返回信息表 进行匹配信息 生成退货单
 * @author wukunyang
 *吴坤洋 2017-11-25
 */
@Configuration
@EnableScheduling
public class TmallReturnTiming {

		@Autowired
		private ShopOrderTmallReturnLogsService shopOrderTmallReturnLogsService;

		@Autowired
		private ShopOperationAreaService shopOperationAreaService;

		@Autowired
		private ShopOrderRepairLogsService shopOrderRepairLogsService;

		@Autowired
		private ShopOrderRepairsService shopOrderRepairsService;
		@Autowired
		private RefundBillSyncRecordService syncRecordService;

//		 @Scheduled(cron="0/5 * *  * * ?")
	    public void executeUploadTask() {
	        // 间隔1分钟,执行工单上传任务    
	    	List<RefundBillSyncRecord> list=syncRecordService.selectByPrimaryKey(); //查询天猫数据日志表 所有未匹配成功的数据
	    	OrderRepairsVo orderRepairs = new OrderRepairsVo();
	    	for(int i=0;i<list.size();i++){
	    		RefundBillSyncRecord returnLogs = list.get(i);
	    		Map map = JSON.parseObject(returnLogs.getJdpResponse());
	    		map = (Map) map.get("refund_get_response");
	    		map = (Map) map.get("refund");
	    		//根据交易订单号，来源单号 和物料号 用来和网单表匹配 如果匹配到 就自动生成一个退货单号  如果生成啦退货定单 退货单状态为：审核中 
	    		OrderProductsVo productVo =shopOperationAreaService.queryTmallTiming(returnLogs.getSku(), returnLogs.getSource(),returnLogs.getTid().toString());
	    		
	    		String repairsn= shopOrderRepairsService.queryRepaiSn(productVo.getId());//查询此网单是否第一次退货
	    		if(productVo!=null&&repairsn!=null && !"".equals(repairsn)){
	    			orderRepairs.setId(shopOrderRepairLogsService.getNextValId());
	    			orderRepairs.setOrderProductId(productVo.getId());//网单主键
	    			orderRepairs.setOrderId(productVo.getOrowId());//订单主键
	    			orderRepairs.setPaymentName(productVo.getPaymentName());//支付方式
	    			orderRepairs.setOfflineAmount(new BigDecimal(Ustring.getString(map.get("refund_fee"))));//退款金额 		PS:不确定退款金额 是什么计算方式 先用退款金额
	    			orderRepairs.setReason(Ustring.getString(map.get("reason")));//退款原因
	    			orderRepairs.setHandleRemark(" ");
	    			orderRepairs.setRequestServiceRemark(" ");
	    			orderRepairs.setRequestServiceDate(0L);
	    			orderRepairs.setOfflineFlag(0);
	    			orderRepairs.setContactMobile(productVo.getMobile());//退货人手机号
	    			orderRepairs.setContactName(productVo.getConsignee());//退货人姓名
	    			orderRepairs.setOfflineReason("  ");
	    			orderRepairs.setOfflineRemark(" ");
	    			orderRepairs.setHpFirstAddTime(0);
	    			orderRepairs.setHpSecondAddTime(0);
	    			orderRepairs.setHandleStatus(1);//1审核中2进行中3受理完成
	    			String signTim = shopOrderRepairsService.queryIsRejectionSign(Ustring.getString(productVo.getId()));
	    			if(signTim!=null && Integer.parseInt(signTim)>0){
	    				orderRepairs.setTypeFlag(5); //5表式揽收
	    			}else {
	    				orderRepairs.setTypeFlag(4);//4表示拒收
	    			}
					shopOrderRepairsService.insertSelective(orderRepairs); //插入退货信息
					OrderRepairLogs log = new OrderRepairLogs(); //new出来一个退货日志
					log.setId(shopOrderRepairLogsService.getNextValId());//主键
					log.setOrderRepairId(orderRepairs.getId());//退货id
					log.setOperator("系统");
					log.setOperate("登记");
					log.setRemark("淘宝海尔官方旗舰店同步退换货申请");
					i=i+shopOrderRepairLogsService.insert(log); //记录退货操作流程 日志
	    		}
	    	}
	        
	    }
}
