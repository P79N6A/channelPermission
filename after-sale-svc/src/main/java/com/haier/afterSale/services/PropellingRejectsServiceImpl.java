package com.haier.afterSale.services;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.haier.afterSale.service.PropellingRejectsService;
import com.haier.afterSale.util.Ustring;
import com.haier.shop.model.OrderRepairsVo;
import com.haier.shop.model.OrderrepairHPrecordsVO;
import com.haier.shop.service.ShopOrderrepairHPrecordsService;

/**
 * 定时推送不良品信息 或 无箱可换
 * @author wukunyang
 *
 */


//@Configuration
//@EnableScheduling
@Service
public class PropellingRejectsServiceImpl implements PropellingRejectsService{
	private static Logger log = LoggerFactory.getLogger(PropellingRejectsServiceImpl.class);
	@Autowired
	private ShopOrderrepairHPrecordsService hPrecordsService;
	@Autowired
	private OperationAreaServiceImpl operationAreaServiceImpl;
	/*
	 * 定时任务 定是推送不良品鉴定工单到HP
	 */
//	@Scheduled(cron="0/5 * *  * * ?")
	public void  queryRejectsPropelling() throws MalformedURLException, ParseException{
		List<OrderRepairsVo> listVo = new ArrayList<>();
		List<OrderrepairHPrecordsVO> precordsVOs=hPrecordsService.queryRejectsPropelling();
			for(int i=0;i<precordsVOs.size();i++){
				OrderRepairsVo vo = new OrderRepairsVo();
				vo.setId(Integer.parseInt(Ustring.getString0(precordsVOs.get(i).getOrderRepairId())));
				vo.setMenuflag("blp");//不良品
				vo.setRepairHPrecordsId(precordsVOs.get(i).getId().toString());//用来接收HP返回鉴定表主键
				listVo.add(vo);
			}
			if(listVo.size()>0){
				operationAreaServiceImpl.ModifyPushHP(listVo,""); //推送HP
			}	
	}
	/**
	 * 发起三次鉴定
	 * @throws ParseException 
	 * @throws MalformedURLException 
	 */
//	@Scheduled(cron="0/5 * *  * * ?")
	public void threeAppraisal() throws MalformedURLException, ParseException{
		List<OrderRepairsVo> listVo = new ArrayList<>();
		List<OrderrepairHPrecordsVO> precordsVOs=hPrecordsService.queryThreeAppraisal();
		  if (precordsVOs == null || precordsVOs.size() == 0) {
			  log.info("没有需要三次鉴定数据");
				return;
			}
			for (OrderrepairHPrecordsVO precordsVO : precordsVOs) {
				try {
					OrderRepairsVo vo = new OrderRepairsVo();
					vo.setId(Integer.parseInt(Ustring.getString(precordsVO.getOrderRepairId())));
					vo.setMenuflag("blp3");//三次鉴定
					vo.setRepairHPrecordsId(precordsVO.getId().toString());//用来接收HP返回鉴定表主键
					listVo.add(vo);
				} catch (Exception e) {
					log.error("New[To Sap]:发起三次坚定发生异常InvThTransactionID：["
							+ precordsVO.getId() + "]," + e.getMessage());
				}
			}
			if(listVo.size()>0){
				operationAreaServiceImpl.ModifyPushHP(listVo,""); //推送HP
			}
	}


    

}
