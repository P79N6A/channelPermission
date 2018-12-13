package com.haier.vehicle.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.vehcile.Cn3wPurchaseStock;
import com.haier.purchase.data.model.vehcile.Entry3wOrder;

/**
 * <p>
 * Description: []
 * </p>
 * Created on 2017年09月07日}
 *
 * @author <a href="mailto: Zhangzengbao32@camelotchina.com">zzb</a>
 * @version 1.0 Copyright (c) 2016 北京柯莱特科技有限公司 交付部
 */
public interface VehicleJobService {
	public void queryStatusFromGvs(String itemNo);

	public void queryStatusFromGvs(List<String> itemNoList, String type);

	public String queryStatusFromMateriel(String itemNo);

	public String saveDNS85(String dn1, String shopCode, String manDt);

	public void addPurchaseStock(Cn3wPurchaseStock cn3wPurchaseStock);

	public void updateCn3wPurchaseStock(Cn3wPurchaseStock cn3wPurchaseStock);

	public List<Cn3wPurchaseStock> queryCn3wPurchaseStock(
			Map<String, Object> map);

	public void addEntry3wOrder(Entry3wOrder entry3wOrder);

	public void updateEntry3wOrder(Entry3wOrder entry3wOrder);

	public List<Entry3wOrder> queryEntry3wOrder(Map<String, Object> map);
	
	/**
	 * 天猫CA套机查询接口
	 */
	public ServiceResult<String> getTmallCaMachine();
}
