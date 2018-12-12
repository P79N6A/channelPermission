package com.haier.distribute.service;




import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.distribute.data.model.CommissionTarget;


public interface DistributeCenterCommissionTargetService {

	JSONObject findCommission_targetList(PagerInfo pager, CommissionTarget condition);


	int addCommission(CommissionTarget commission);


	int updateCommission(CommissionTarget commission);


	int deleteCommission(int id);


	Boolean jiaoyanCommission(CommissionTarget commission);


	JSONObject commission_product_invoice(PagerInfo pager, String categoryId, String policyYear, String endTime, String policyYear2, int channelId, int channelType,
			int brandId);


	JSONObject commission_product_invoice1(PagerInfo pager, String categoryId, String policyYear, String endTime, String policyYear2, int channelId, int channelType,
			int brandId);


	JSONArray commission_product_invoice(String categoryId, String policyYear, int channelId, int channelType,
			int brandId);


	JSONArray commission_product_invoice1(String categoryId, String policyYear, int channelId, int channelType,
			int brandId);

}
