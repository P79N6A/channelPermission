package com.haier.afterSale.services;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.haier.afterSale.model.Ustring;
import com.haier.eis.service.EisVomInOutStoreOrderService;
import com.haier.shop.model.OrderRepairLESRecords;
import com.haier.shop.model.OrderRepairLogs;
import com.haier.shop.model.OrderRepairsVo;
import com.haier.shop.model.OrderrepairHPrecords;
import com.haier.shop.service.ShopOrderRepairLesreCordsService;
import com.haier.shop.service.ShopOrderRepairLogsService;
import com.haier.shop.service.ShopOrderrepairHPrecordsService;
/**
 * 定时去HP返回信息表中 关联查询VOM返回入库信息表 匹配信息然后发起二次鉴定
 * @author wukunyang
 *
 */
@Configuration
@EnableScheduling
public class InitiatingTwoAppraisalHP {
	@Autowired
	private ShopOrderrepairHPrecordsService  shopOrderrepairHPrecordsService;
	@Autowired
	private OperationAreaServiceImpl operationAreaServiceImpl;
	@Autowired
	private EisVomInOutStoreOrderService eisVomInOutStoreOrderService;
	@Autowired
	private ShopOrderRepairLesreCordsService shopOrderRepairLesreCordsService;
	@Autowired
	private ShopOrderRepairLogsService shopOrderRepairLogsService;//退货日志
//	@Scheduled(cron="0/5 * *  * * ?")
	public void TwoAppraisalHP() throws MalformedURLException, ParseException{
		//查询未发起二次鉴定的退单号 
		List<OrderrepairHPrecords> precords=	shopOrderrepairHPrecordsService.queryHPRecords();
		List<OrderRepairsVo> listVo = new ArrayList<>();
		for(int i=0;i<precords.size();i++){
	    //根据退货单号主键查询是否VOM已经把入库信息回传到CBS修改
		List<OrderRepairLESRecords> repairLESRecords=shopOrderRepairLesreCordsService.queryRecordSn(precords.get(i).getOrderRepairId().toString()); 
		int vomInder =0;
		for(OrderRepairLESRecords orderRepairLESRecords :repairLESRecords){
			//根据出入库单号查询出入库明细表来判断VOM是否已经把出入库流水返回到CBS
			vomInder +=eisVomInOutStoreOrderService.queryVomInOut(orderRepairLESRecords.getRecordSn());
		}
		//判断是否有VOM出入库信息
		if(vomInder>0){
			OrderRepairsVo vo = new OrderRepairsVo();
			vo.setMenuflag("2");
			vo.setcOrderSnId(Ustring.getString(precords.get(i).getOrderRepairId()));
			vo.setId(precords.get(i).getOrderRepairId());
			listVo.add(vo);
			//同时作废发票
			if(Ustring.isNotEmpty(precords.get(i).getInvoIceId())){
				operationAreaServiceImpl.InvalidInvoices(precords.get(i).getInvoIceId(), "退换货");
			}
			//记录操作日志
			operationAreaServiceImpl.ProcessLog(precords.get(i).getOrderRepairId(), "作废发票", "发起作废发票请求");
		}	

	}
		if(listVo!=null){
			operationAreaServiceImpl.ModifyPushHP(listVo);
			
		}
	}
	
	
}
