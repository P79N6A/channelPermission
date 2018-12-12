
package com.haier.svc.api.webService.WwwHpRecords.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.haier.shop.model.OrderRepairLESRecords;
import com.haier.shop.model.OrderRepairsVo;
import com.haier.shop.model.OrderRepairshpLogs;
import com.haier.shop.model.OrderhpRejectionLogs;
import com.haier.svc.api.controller.util.SpringContextUtil;
import com.haier.svc.api.webService.WwwHpRecords.Service.HPReturnIdentificationResults;
/**
 * HP回传鉴定结果信息（已开箱正品 为开箱正品）
 * 吴坤洋 2017-11-20
 * @author wukunyang
 *
 */

@WebService
@Component
public class HPReturnIdentificationResultsImpl implements HPReturnIdentificationResults {
	private static Logger log = LoggerFactory.getLogger(HPReturnIdentificationResultsImpl.class);
	/**
	 * HP回传鉴定结果信息
	 * @throws Exception 
	 */
	public String ReturnInformation(List<OrderRepairshpLogs> bean) throws Exception{
		HPReturn_transfer hpRetun = (HPReturn_transfer) SpringContextUtil.getBean("hpReturnTransfer");
			for (OrderRepairshpLogs rejectionLogs : bean) {
				try {
					hpRetun.insertHPrecords(rejectionLogs);//插入HP返回的鉴定结果到数据库
				} catch (Exception e) {
					log.error("New[To Sap]:接受HP返回鉴定信息发生异常InvThTransactionID：["
							+ rejectionLogs.getId() + "]," + e.getMessage());
					return  e.getMessage();
				}
			}
		return "成功";
	}




}

