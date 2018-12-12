package com.haier.stock.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.eis.model.BusinessException;
import com.haier.shop.model.VOMSynOrderRequire;
import com.haier.shop.model.VOMSynSubOrderRequire;
import com.haier.stock.service.StockCenterEISStockService;
@Configuration
@EnableScheduling
@Service
public class EISStockServiceImpl implements StockCenterEISStockService  {
	 private Logger                                 logger = LoggerFactory
		        .getLogger(EISStockServiceImpl.class);
	 @Autowired
		    private EISStockModel eisStockModel;
	 @Autowired
		private VOMOrderModel vomOrderModel;
	
	
	 @Override
	    public ServiceResult<Boolean> reviseLessStockTransChannel(Integer transId, String channel,
	                                                              String user) {
	        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
	        try {
	            //            eisStockModel.processLessStockTransQueue();
	            eisStockModel.reviseLessStockTransChannel(transId, channel, user);
	            result.setResult(true);
	        } catch (Exception e) {
	            result.setSuccess(false);
	            result.setResult(false);
	            if (e instanceof BusinessException) {
	                result.setMessage(e.getMessage());
	            } else {
	                result.setMessage("系统错误");
	                logger.error("修正LES交易的渠道出现未知错误：", e);
	            }
	        }
	        return result;
	    }
	 
	 
//	 	//@Scheduled(cron="0/5 * *  * * ?")
//		public void man() throws Exception{
//		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date date1 = new Date();
//			VOMSynOrderRequire synOrderRequire = new VOMSynOrderRequire();
//			 synOrderRequire.setOrderNo("WDDTDB41221998896xxxx");
//			 synOrderRequire.setSourceSn("DTDA141211133429804547");
//			 synOrderRequire.setOrderType("2");
//			 synOrderRequire.setBusType("70");
//			 synOrderRequire.setOrderDate(dateFormat.format(date1));
//			 synOrderRequire.setStoreCode("C12502");
//			 synOrderRequire.setProvince("吉林");
//			 synOrderRequire.setCity("长春");
//			 synOrderRequire.setCounty("南关区");
//			 synOrderRequire.setAddr("长春市亚泰大街星河湾");
//			 synOrderRequire.setGbCode("220102");
//			 synOrderRequire.setName("光复路昕源电器行");
//			 synOrderRequire.setMobile("0431-88727171");
//			 synOrderRequire.setPayState("P1");
//			 synOrderRequire.setPayTime("2014-12-21 12:16:12");
//			 synOrderRequire.setPayType("alipay");
//			 synOrderRequire.setIsInv("1");
//			 synOrderRequire.setInvType("1");
//			 synOrderRequire.setTaxBearer("22010219680109400000");
//			 synOrderRequire.setRecAddr("11");
//			 synOrderRequire.setRecAcc("181901040011603");
//			 synOrderRequire.setRecBank("中国农业银行吉林长春市曙光支行");
//			 synOrderRequire.setSname("海尔电商");
//			 synOrderRequire.setSprovince("山东省");
//			 synOrderRequire.setScity("青岛市");
//			 synOrderRequire.setScounty("崂山区");
//			 synOrderRequire.setSaddr("山东省青岛市崂山区海尔路1号海尔工业园");
//			 synOrderRequire.setBusFlag("2");
//			 synOrderRequire.setFreight(0.0);
//			 synOrderRequire.setBillSum(899.0);
//			 synOrderRequire.setBillOwe(0.0);
//			 
//			 VOMSynSubOrderRequire synSubOrderRequire = new VOMSynSubOrderRequire();
//			 synSubOrderRequire.setItemNo("1");
//			 synSubOrderRequire.setStorageType("10");
//			 synSubOrderRequire.setProductCode("CB0MD9B0M");
//			 synSubOrderRequire.setHrCode("CB0MD9B0M");
//			 synSubOrderRequire.setProdes("Haier海尔6KG全自动波轮洗衣机");
//			 synSubOrderRequire.setNumber(1);
//			 synSubOrderRequire.setUnprice(0.0);
//			 
//			 List<VOMSynSubOrderRequire> subOrderList = new ArrayList<VOMSynSubOrderRequire>();
//			 subOrderList.add(synSubOrderRequire);
//			 synOrderRequire.setSubOrderList(subOrderList);
//			vomOrderModel.synOrderInfo(synOrderRequire);
//		}
		
}
