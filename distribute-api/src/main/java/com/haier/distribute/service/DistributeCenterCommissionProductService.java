package com.haier.distribute.service;




import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.distribute.data.model.CommissionProduct;
import com.haier.distribute.data.model.Products;


public interface  DistributeCenterCommissionProductService {

	JSONObject CommissionListf(PagerInfo pager, CommissionProduct condition);

	int addCommission(CommissionProduct commission);

	int updateCommission(CommissionProduct commission);

	int deleteCommission(int id);

	Boolean jiaoyanCommission(CommissionProduct commission);

	List<CommissionProduct> selectexportCommission_productListf(CommissionProduct commission_product);

	String Distribution_infoList(String channelsid);

	int skuAll(String sku, int chantype, int intValue);

	int addCommission_product(CommissionProduct commission_product);

	List<Products> importCommission_productList(String sku, String productName);

	CommissionProduct AllName(Integer brandId, Integer productTypeId);

	int updateCommission_product(CommissionProduct commission_product);
	
	List<CommissionProduct> selectCommission_productListf(CommissionProduct entity,int start,int rows);
}
