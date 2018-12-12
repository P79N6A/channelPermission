
package com.haier.svc.api.webService.WwwHpRecords.ServiceImpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

import com.haier.afterSale.model.Json;
import com.haier.afterSale.service.OperationAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.model.OrderRepairLESRecords;
import com.haier.shop.model.OrderRepairsVo;
import com.haier.shop.model.OrderRepairshpLogs;
import com.haier.shop.model.OrderhpRejectionLogs;
import com.haier.shop.model.OrederVOMReturnLogs;


/**
 * 用来中转注入调用方法
 * @author wukunyang
 * 吴坤洋 2017-11-22
 *
 */

@Service("hpReturnTransfer")
public class HPReturn_transfer {
	@Autowired
	private OperationAreaService operationAreaServiceImpl;
	
/**
	 * 保存HP返回的信息
	 * @param orderhpRejectionLogs
	 * @return
	 */
//
//	public int  SaveHPUnhealthy(OrderhpRejectionLogs orderhpRejectionLogs){
//		return operationAreaServiceImpl.SaveHPUnhealthy(orderhpRejectionLogs);
//	}
//	
	
/**
	 * 插入出入库信息
	 */

	public int insert(OrderRepairLESRecords cords){
		return operationAreaServiceImpl.insert(cords);
	};
	
/**
	 * 查询退货id 网单ID
	 */

	public OrderRepairsVo  queryOrderProductId(String id){
		return  operationAreaServiceImpl.queryOrderProductId(id);
	}
	
	
/**
	 * 调用VOM http接口
 * @throws Exception 
	 */

	  public Json  CallHttpVOM(OrderRepairLESRecords  cords) throws Exception{
		  return  operationAreaServiceImpl.CallHttpVOM(cords);
	  }
	  
	  
/**
	   * 推送HP鉴定结果
	 * @throws ParseException 
	 * @throws MalformedURLException 
	   */

	  public Json ModifyPushHP(List<OrderRepairsVo> orderRepairsVo) throws MalformedURLException, ParseException{
		  return  operationAreaServiceImpl.ModifyPushHP(orderRepairsVo);
	  }
	  
	  
/**
		 * 插入保存HP回传回来的鉴定结果
		 * @param orderrepairHPrecords
		 * @return
		 */

	  public int insertHPrecords(OrderRepairshpLogs bean){
			return operationAreaServiceImpl.insertHPrecords(bean);
		}
	  
	  
/***
	   * 处理vom回传的信息
	   * @param vomReturnLogs
	   * @return
	   */

//	  public String insertAnalyLogs(OrederVOMReturnLogs vomReturnLogs){
//		  return operationAreaServiceImpl.insertAnalyLogs(vomReturnLogs);
//	  }
}

