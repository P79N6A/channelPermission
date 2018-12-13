package com.haier.svc.services;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.InvBudgetOrg;
import com.haier.purchase.data.model.T2OrderItem;
import com.haier.purchase.data.model.WAAddress;
import com.haier.purchase.data.service.PurchaseInvBudgetOrgService;
import com.haier.purchase.data.service.PurchaseT2OrderService;
import com.haier.purchase.data.service.PurchaseWAAddressService;
import com.haier.stock.model.InvRrsWarehouse;
import com.haier.stock.model.InvWarehouse;
import com.haier.stock.model.JdStorage;
import com.haier.stock.service.StockInvRrsWarehouseService;
import com.haier.stock.service.StockInvWarehouseService;
import com.haier.stock.service.StockJdStorageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.svc.service.PurchaseBaseCommonService;

@Service
public class PurchaseBaseCommonServiceImpl implements PurchaseBaseCommonService {
	private static Logger log = LoggerFactory
			.getLogger(PurchaseBaseCommonServiceImpl.class);
	@Autowired
	private StockInvWarehouseService stockInvWarehouseService;
	@Autowired
	private StockInvRrsWarehouseService stockInvRrsWarehouseService;
	@Autowired
	private StockJdStorageService stockJdStorageService;
	@Autowired
	private PurchaseInvBudgetOrgService purchaseInvBudgetOrgService;
	@Autowired
	private PurchaseWAAddressService purchaseWAAddressService;

	@Autowired
	private PurchaseT2OrderService purchaseT2OrderService;

	/**
	 * @Description:通过estorge_id索引
	 */
	@Override
	public ServiceResult<InvWarehouse> getWhByEstorgeId(String estorge_id) {
		ServiceResult<InvWarehouse> result = new ServiceResult<InvWarehouse>();
		try {
			InvWarehouse invWarehouse = stockInvWarehouseService
					.getWhByEstorgeId(estorge_id);
			result.setResult(invWarehouse);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResult(null);
			result.setMessage("通过电商库位码获取仓库：" + e.getMessage());
			log.error("通过电商库位码获取仓库：", e);
		}
		return result;
	}

	/**
	 * @Title: getRrsWhByEstorgeId
	 * @Description:默认通过estorge_id字段查询，可选查询字段有t2_default， 
	 *                                                    rrs_wh_code，查询表：inv_rrs_warehouse
	 */
	@Override
	public ServiceResult<List<InvRrsWarehouse>> getRrsWhByEstorgeId(
			Map<String, Object> params) {
		ServiceResult<List<InvRrsWarehouse>> result = new ServiceResult<List<InvRrsWarehouse>>();
		try {
			List<InvRrsWarehouse> list = stockInvRrsWarehouseService
					.getRrsWhByEstorgeOriginal(params);
			result.setResult(list);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResult(null);
			result.setMessage("通过电商库位码获取日日顺仓库List：" + e.getMessage());
			log.error("通过电商库位码获取日日顺仓库List：", e);
		}
		return result;
	}
	
	/**
	 * 查询所有预算体
	 * 
	 * @Description:查询表inv_budgetorg
	 */
	@Override
	public ServiceResult<InvBudgetOrg> getAllBudgetOrg(Map params) {
		ServiceResult<InvBudgetOrg> result = new ServiceResult<InvBudgetOrg>();
		try {
			result.setResult(purchaseInvBudgetOrgService.getAllBudgetOrg(params));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResult(null);
			result.setMessage("预算体取得：" + e.getMessage());
			log.error("预算体取得：", e);
		}
		return result;
	}
	
	/**
	 * 获取所有数据
	 * 
	 * @Description:通过estorge_id索引
	 */
	@Override
	public ServiceResult<InvWarehouse> getAllWhByEstorgeId(String estorge_id) {
		ServiceResult<InvWarehouse> result = new ServiceResult<InvWarehouse>();
		try {
			result.setResult(stockInvWarehouseService.getAllWhByEstorgeId(estorge_id));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResult(null);
			result.setMessage("getAllWhByEstorgeId：" + e.getMessage());
			log.error("getAllWhByEstorgeId：", e);
		}
		return result;
	}
	
	/**
	 * @Title: getRrsWhByEstorgeId
	 * @Description:默认通过estorge_id字段查询，可选查询字段有t2_default， 
	 *                                                    rrs_wh_code，查询表：inv_rrs_warehouse
	 */
	@Override
	public ServiceResult<List<InvRrsWarehouse>> getAllRrsWhByEstorgeId(
			Map<String, Object> params) {
		ServiceResult<List<InvRrsWarehouse>> result = new ServiceResult<List<InvRrsWarehouse>>();
		try {
			result.setResult(stockInvRrsWarehouseService.getAllRrsWhByEstorgeOriginal(params));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResult(null);
			result.setMessage("通过电商库位码获取日日顺仓库List：" + e.getMessage());
			log.error("通过电商库位码获取日日顺仓库List：", e);
		}
		return result;
	}
	
	/**
	 * @Title: getAllRrsWhByEstorgeIdJd
	 * @Description:默认通过estorge_id字段查询，适用jd
	 * @author zhangming
	 */
	@Override
	public ServiceResult<List<JdStorage>> getAllRrsWhByEstorgeIdJd(
			Map<String, Object> params) {
		ServiceResult<List<JdStorage>> result = new ServiceResult<List<JdStorage>>();
		try {
			result.setResult(stockJdStorageService.getAllRrsWhByEstorgeOriginal(params));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResult(null);
			result.setMessage("通过JD电商库位码获取日日顺仓库List：" + e.getMessage());
			log.error("通过JD电商库位码获取日日顺仓库List：", e);
		}
		return result;
	}
	
	/**
	 * 查询所有数据
	 * 
	 * @Description:通过waCode查询表inv_warehouse
	 */
	@Override
	public ServiceResult<List<WAAddress>> getAllWAAddressInfo(String waCode) {
		ServiceResult<List<WAAddress>> result = new ServiceResult<List<WAAddress>>();
		try {
			result.setResult(purchaseWAAddressService.getALlWAAddressInfo(waCode));
		} catch (Exception e) {
			log.error("getALlWAAddressInfo：", e);
			result.setMessage("getALlWAAddressInfo：" + e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 通过来源单号查询
	 *
	 * @param source_order_id
	 *
	 */
	@Override
	public ServiceResult<T2OrderItem> getDataBySourceOrderId(
			String source_order_id) {
		ServiceResult<T2OrderItem> result = new ServiceResult<T2OrderItem>();
		try {
			result.setResult(purchaseT2OrderService
					.getDataBySourceOrderId(source_order_id));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setResult(null);
			result.setMessage("getDataBySourceOrderId：" + e.getMessage());
			log.error("getDataBySourceOrderId：", e);
		}
		return result;
	}

	/**
	 * @Description:通过waCode查询表inv_warehouse
	 */
	@Override
	public ServiceResult<List<WAAddress>> getWAAddressInfo(String waCode) {
		ServiceResult<List<WAAddress>> result = new ServiceResult<List<WAAddress>>();
		try {
			result.setResult(purchaseWAAddressService.getWAAddressInfo(waCode));
		} catch (Exception e) {
			log.error("getWAAddressInfo：", e);
			result.setMessage("getWAAddressInfo：" + e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}
}
