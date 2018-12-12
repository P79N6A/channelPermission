package com.haier.svc.api.service.pop;

import com.haier.logistics.service.EisNewVOMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.VomReceivedQueue;
import com.haier.logistics.service.EisInterfaceDataLogApiService;
@Service
public class VOMInformationReceiverServiceImpl {
	@Autowired
	private EisInterfaceDataLogApiService eisInterfaceDataLogApiService;
	@Autowired
	private EisNewVOMService eisVOMService;
	
	  /**
     * 记录一条访问log
     * @param log
     * @return
     */
	 public   ServiceResult<Boolean> record(EisInterfaceDataLog log){
		 return eisInterfaceDataLogApiService.record(log);
	 }
	 
	 
	 
	 /**
	     * 添加接收的消息
	     * @param receivedQueue
	     * @return
	     */
	 public ServiceResult<Boolean> addReceivedInformation(VomReceivedQueue receivedQueue){
		 return eisVOMService.addReceivedInformation(receivedQueue);
	 	}

	    /**
	     * 预处理接收的信息内容
	     * @return
	     */
	   public  ServiceResult<Boolean> reprocessingContent(){
		   return eisVOMService.reprocessingContent();
	   }

	    /**
	     * 处理VOM出入库记录:生成库存交易
	     * @return
	     */
	   public ServiceResult<Boolean> processOutInStoreForGenerateStockTransaction(){
		   return eisVOMService.processOutInStoreForGenerateStockTransaction();
	   }

	    /**
	     * 处理VOM回传的订单状态：出入库处理、
	     * @return
	     */
	  public   ServiceResult<Boolean> processVomShippingStatus(){
		  return eisVOMService.processVomShippingStatus();
	  }

	    /**
	     * 预处理VOM信息失败后再处理
	     * @return
	     */
	  public  ServiceResult<Boolean> processVomReceivedQueueFailStatus(){
		  return eisVOMService.processVomReceivedQueueFailStatus();
	  }
	    
	    /**
	     * 处理VOM出入库记录:生成库存交易（3W正品退仓）
	     * @return
	     */
	  public   ServiceResult<Boolean> processOutInStoreForGenerateStockTransaction3W(){
		  return eisVOMService.processOutInStoreForGenerateStockTransaction3W();
	  }
	   
	    /**
	     * 处理VOM回传的订单状态：出入库处理(3W正品退仓)、
	     * @return
	     */
	  public  ServiceResult<Boolean> processVomShippingStatus3W(){
		  return eisVOMService.processVomShippingStatus3W();
	  }
	  
	  public   String getProperties(String name){
		  return eisInterfaceDataLogApiService.getProperties(name);
	  }

}
