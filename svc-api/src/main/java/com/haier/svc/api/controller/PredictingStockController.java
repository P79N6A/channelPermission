package com.haier.svc.api.controller;

import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.haier.svc.api.service.T2OrderModel;

/**
 * Created by 黄俊 on 2014/7/7.
 */
@Controller
public class PredictingStockController {
	private final static org.apache.log4j.Logger logger			 = LogManager
		.getLogger(PredictingStockController.class);
	
	//导入模板表头信息
	private static final String					 checkStr		 = "序号,提报周,渠道,产品组,物料编号,品牌,型号,T+3周,T+4周,T+5周,T+6周,T+7周,T+8周,T+9周,T+10周,T+11周,T+12周,T+13周,状态,失败信息";
	//T+3订单状态
	private static final String					 T3_ORDER_STATUS = "predict_stock_flow_flag";

	 @Autowired private T2OrderModel t2OrderService;
	
	@RequestMapping(value = { "predictingStockHistoryList.html" }, method = { RequestMethod.GET })
	String predictingStockHistoryList() {
		return "/purchase/predictingStockHistoryList";
	}

	

	/*@RequestMapping(value = { "/getChannelsByAuth" }, method = { RequestMethod.GET })
	@ResponseBody
	HttpJsonResult<List<InvStockChannel>> getChennelsByAuth(HttpServletRequest request) {
		HttpJsonResult<List<InvStockChannel>> result = new HttpJsonResult<List<InvStockChannel>>();
		ServiceResult<List<InvStockChannel>> queryResult = t2OrderService.getChannels();

		// 权限定义产品组取得
		ServiceResult<PrivilegeItem> privilegeData = purchaseCommonService
			.getPrivilege(String.valueOf(WebUtil.currentUserId(request)));
		// 删除没有权限的产品组
		List<InvStockChannel> chennelsData = new ArrayList<InvStockChannel>();
		if (privilegeData.getSuccess() == true && privilegeData.getResult() != null) {
			for (InvStockChannel invStockChannel : queryResult.getResult()) {
				if (privilegeData.getResult().getEd_channel_id()
					.contains(invStockChannel.getChannelCode())) {
					chennelsData.add((invStockChannel));
				}
			}
			result.setData(chennelsData);
		} else {
			result.setData(queryResult.getResult());
		}
		//690电商渠道处理
		if (privilegeData.getResult().getEd_channel_id().contains("DS")) {
			InvStockChannel invStockChannel = new InvStockChannel();
			invStockChannel.setChannelCode("DS");
			invStockChannel.setName("690电商渠道");
			result.getData().add(invStockChannel);
		}
		return result;
	}*/

	

}
