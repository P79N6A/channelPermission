package com.haier.svc.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.shop.model.QueryOrderParameter;
import com.haier.shop.service.ShopOrdersService;
import com.haier.shop.service.ShopTaoBaoGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.svc.service.OrderOperationService;

@Service
public class OrderOperationServiceImpl implements OrderOperationService{

	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
			.getLogger(OrderOperationServiceImpl.class);
	
	@Autowired
	private ShopOrdersService shopOrdersService;

	@Autowired
	private ShopTaoBaoGroupsService shopTaoBaoGroupsService;
	
	@Override
	public ServiceResult<List<QueryOrderParameter>> getFindQueryOrderList(QueryOrderParameter queryOrder) {
        ServiceResult<List<QueryOrderParameter>> result = new ServiceResult<List<QueryOrderParameter>>();
        try {
        	if(queryOrder==null){
                result.setSuccess(false);
                result.setMessage("【getFindQueryOrderList】获取订单信息服务参数并为null");
                log.error("【getFindQueryOrderList】获取订单信息服务参数并为null");
                return result;
        	}
        	List<QueryOrderParameter> findQueryOrderList = shopOrdersService.getFindQueryOrderList(queryOrder);
        	Integer count = shopOrdersService.getFindQueryOrderListCount(queryOrder);
            result.setResult(findQueryOrderList);
            PagerInfo pi = new PagerInfo();
            pi.setRowsCount(count !=null ? count : 0 );
            result.setPager(pi);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("获取订单信息list失败：", e);
        }
        return result;
	}
	/**
	 * 导出订单列表
	 */
	@Override
	public ServiceResult<List<QueryOrderParameter>> getExportOrderList(QueryOrderParameter queryOrder) {
		ServiceResult<List<QueryOrderParameter>> result = new ServiceResult<List<QueryOrderParameter>>();
		try {
			if(queryOrder==null){
				result.setSuccess(false);
				result.setMessage("【getExportOrderList】获取导出订单信息服务参数并为null");
				log.error("【getExportOrderList】获取导出订单信息服务参数并为null");
				return result;
			}
			List<QueryOrderParameter> findQueryOrderList = shopOrdersService.getFindQueryOrderList(queryOrder);
			result.setResult(findQueryOrderList);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("获取导出订单信息list失败：", e);
		}
		return result;
	}
	
	/**
	 * 根据sku查询定金尾款
	 */
	@Override
	public ServiceResult<List<Map<String,Object>>> getTaoBaoGroupsListBySku(String sku,Integer page, Integer rows) {
		ServiceResult<List<Map<String,Object>>> result = new ServiceResult<List<Map<String,Object>>>();
		try {
			if(sku==null){
				sku="";
			}
			List<Map<String,Object>> taoBaoGroupsListBySku = shopTaoBaoGroupsService.getTaoBaoGroupsListBySku(sku,page,rows);
			timeFormatList(taoBaoGroupsListBySku);
        	Integer count = shopTaoBaoGroupsService.getTaoBaoGroupsListBySkuCount(sku);
            result.setResult(taoBaoGroupsListBySku);
            PagerInfo pi = new PagerInfo();
            pi.setRowsCount(count !=null ? count : 0 );
            result.setPager(pi);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("获取导出订单信息list失败：", e);
		}
		return result;
	}
	
	@Override
	public ServiceResult<Boolean> addTaoBaoGroups(Map<String, Object> map) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try{
			if(map==null){
				log.error("新增团购商品信息到数据库，参数为空");
				result.setMessage("新增团购商品信息到数据库，参数为空");
				result.setSuccess(false);
				return result;
			}
			Integer addTaoBaoGroups = shopTaoBaoGroupsService.addTaoBaoGroups(map);
			if(addTaoBaoGroups!=null && addTaoBaoGroups >0 ){
				result.setSuccess(true);
				return result;
			}else{
				log.error("新增团购商品信息到数据库失败");
				result.setMessage("新增新增团购商品信息到数据库失败");
				result.setSuccess(false);
				return result;
			}
		}catch(Exception e) {
			log.error("新增团购商品信息失败：", e);
			result.setMessage(e.getMessage());
			result.setSuccess(false);
			return result;
		}
	}
	@Override
	public ServiceResult<Boolean> updateTaoBaoGroups(Map<String, Object> map) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try{
			if(map==null){
				log.error("更新团购商品信息到数据库，参数为空");
				result.setMessage("更新团购商品信息到数据库，参数为空");
				result.setSuccess(false);
				return result;
			}
			Integer updateTaoBaoGroups = shopTaoBaoGroupsService.updateTaoBaoGroups(map);
			if(updateTaoBaoGroups!=null && updateTaoBaoGroups >0 ){
				result.setSuccess(true);
				return result;
			}else{
				log.error("更新团购商品信息到数据库失败");
				result.setMessage("更新新增团购商品信息到数据库失败");
				result.setSuccess(false);
				return result;
			}
		}catch(Exception e) {
			log.error("更新团购商品信息失败：", e);
			result.setMessage(e.getMessage());
			result.setSuccess(false);
			return result;
		}
	}

	void timeFormatList(List<Map<String,Object>> taoBaoGroupsList){
		if(taoBaoGroupsList==null){
			return;
		}
		
		for (Map<String, Object> map : taoBaoGroupsList) {
			Integer depositStartTime = (Integer) map.get("depositStartTime");
			Integer depositEndTime = (Integer) map.get("depositEndTime");
			Integer balanceStartTime = (Integer) map.get("balanceStartTime");
			Integer balanceEndTime = (Integer) map.get("balanceEndTime");
			
			map.put("depositStartTime", depositStartTime > 0?timeFormat(depositStartTime * 1000L):"");
			map.put("depositEndTime",  depositEndTime > 0 ?timeFormat(depositEndTime * 1000L):"");
			map.put("balanceStartTime", balanceStartTime > 0?timeFormat(balanceStartTime* 1000L):"");
			map.put("balanceEndTime",  balanceEndTime > 0 ?timeFormat(balanceEndTime* 1000L):"");
		}
	}
	String timeFormat(Long time){
		try {
			if(time==null){
				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(time);
			return sdf.format(date);
		} catch (Exception e) {
			log.error("long时间转换String错误，long时间："+time+"e:"+e.getMessage());
			return null;
		}
	}
	
	@Override
	public ServiceResult<Boolean> delTaoBaoGroups(Integer[] ids) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		try {
			if(ids==null){
				result.setSuccess(false);
				result.setMessage("淘宝万人团定金尾款删除id为空");
				return result;
			}
			Integer delTaoBaoGroups = shopTaoBaoGroupsService.delTaoBaoGroups(ids);
			if(delTaoBaoGroups!=null && delTaoBaoGroups>0){
				result.setSuccess(true);
			}else{
				result.setSuccess(false);
				result.setMessage("淘宝万人团定金尾款删除数据失败，id:"+ids);
			}
			return result;
		} catch (Exception e) {
			log.error("long时间转换String错误，long时间：");
			return result;
		}
	}
	@Override
	public ServiceResult<Map<String, Object>> getTaoBaoGroupsBySkuAndName(String sku, String groupName) {
		ServiceResult<Map<String, Object>> result = new ServiceResult<Map<String, Object>>();
		try {
			if(sku==null||sku.equals("") || groupName==null || groupName.equals("")){
				result.setSuccess(false);
				result.setMessage("淘宝万人团定金尾款sku与团购名称参数为空");
				return null;
			}
			 Map<String, Object> taoBaoGroups = shopTaoBaoGroupsService.getTaoBaoGroupsBySkuAndName(sku, groupName);
			if(taoBaoGroups!=null){
				result.setSuccess(true);
				result.setResult(taoBaoGroups);
			}else{
				result.setSuccess(false);
				result.setMessage("淘宝万人团定金尾款查询数据失败，sku:"+sku+"groupName:"+groupName);
			}
			return result;
		} catch (Exception e) {
			log.error("淘宝万人团定金尾款查询数据异常，e:"+e.getMessage());
			result.setSuccess(false);
			result.setMessage("淘宝万人团定金尾款查询数据异常，e:"+e.getMessage());
			return result;
		}
	}
}
