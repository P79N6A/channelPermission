package com.haier.order.services;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.haier.order.service.OrderCenterOrderOperationService;
import com.haier.order.util.PHPSerializer;
import com.haier.order.util.SerializedPhpParser;
import com.haier.shop.model.OrderProductsVo;
import com.haier.shop.model.QueryOrderParameter;
import com.haier.shop.service.ShopOperationAreaService;
import com.haier.shop.service.ShopOrdersService;
import com.haier.shop.service.ShopTaoBaoGroupsService;
import com.haier.stock.model.InvChannel2OrderSource;
import com.haier.stock.model.OrderProductStatus;
import com.haier.stock.service.StockInvChannel2OrderSourceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;

@Service
public class OrderCenterOrderOperationServiceImpl implements OrderCenterOrderOperationService {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(OrderCenterOrderOperationServiceImpl.class);

    @Autowired
    private ShopOrdersService shopOrdersService;
    @Autowired
    ShopOperationAreaService  shopOperationAreaService;
    @Autowired
    private ShopTaoBaoGroupsService shopTaoBaoGroupsService;
    @Autowired
    private StockInvChannel2OrderSourceService stockInvChannel2OrderSourceService;

    public ServiceResult<List<QueryOrderParameter>> getFindQueryOrderList(QueryOrderParameter queryOrder) {
        ServiceResult<List<QueryOrderParameter>> result = new ServiceResult<List<QueryOrderParameter>>();
        try {
            if (queryOrder == null) {
                result.setSuccess(false);
                result.setMessage("【getFindQueryOrderList】获取订单信息服务参数并为null");
                log.error("【getFindQueryOrderList】获取订单信息服务参数并为null");
                return result;
            }
            List<QueryOrderParameter> findQueryOrderList = shopOrdersService.getFindQueryOrderList(queryOrder);
            Integer count = shopOrdersService.getFindQueryOrderListCount(queryOrder);
            result.setResult(findQueryOrderList);
            PagerInfo pi = new PagerInfo();
            pi.setRowsCount(count != null ? count : 0);
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

    public ServiceResult<List<QueryOrderParameter>> getExportOrderList(QueryOrderParameter queryOrder) {
        ServiceResult<List<QueryOrderParameter>> result = new ServiceResult<List<QueryOrderParameter>>();
        try {
            if (queryOrder == null) {
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

    public ServiceResult<List<Map<String, Object>>> getTaoBaoGroupsListBySku(String sku, Integer page, Integer rows) {
        ServiceResult<List<Map<String, Object>>> result = new ServiceResult<List<Map<String, Object>>>();
        try {
            if (sku == null) {
                sku = "";
            }
            List<Map<String, Object>> taoBaoGroupsListBySku = shopTaoBaoGroupsService.getTaoBaoGroupsListBySku(sku, page, rows);
            timeFormatList(taoBaoGroupsListBySku);
            Integer count = shopTaoBaoGroupsService.getTaoBaoGroupsListBySkuCount(sku);
            result.setResult(taoBaoGroupsListBySku);
            PagerInfo pi = new PagerInfo();
            pi.setRowsCount(count != null ? count : 0);
            result.setPager(pi);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("获取导出订单信息list失败：", e);
        }
        return result;
    }

    public ServiceResult<Boolean> addTaoBaoGroups(Map<String, Object> map) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            if (map == null) {
                log.error("新增团购商品信息到数据库，参数为空");
                result.setMessage("新增团购商品信息到数据库，参数为空");
                result.setSuccess(false);
                return result;
            }
			String specIds = (String) map.get("specIds");
			String prices = (String) map.get("prices");
			String[] specIdsSplit = specIds.substring(1, specIds.length()).split(",");
			String[] pricesSplit = prices.substring(1, prices.length()).split(",");
			Map<String,String> specIdsMap = new HashMap<String,String>();
			for (int i = 0; i < specIdsSplit.length; i++) {
				specIdsMap.put(specIdsSplit[i], pricesSplit[i]);			
			}
			String orderExtention = getOrderExtention(specIdsMap);
			map.put("productSpecs", orderExtention);
			Integer addTaoBaoGroups = shopTaoBaoGroupsService.addTaoBaoGroups(map);
            if (addTaoBaoGroups != null && addTaoBaoGroups > 0) {
                result.setSuccess(true);
                return result;
            } else {
                log.error("新增团购商品信息到数据库失败");
                result.setMessage("新增新增团购商品信息到数据库失败");
                result.setSuccess(false);
                return result;
            }
        } catch (Exception e) {
            log.error("新增团购商品信息失败：", e);
            result.setMessage(e.getMessage());
            result.setSuccess(false);
            return result;
        }
    }

    public ServiceResult<Boolean> updateTaoBaoGroups(Map<String, Object> map) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            if (map == null) {
                log.error("更新团购商品信息到数据库，参数为空");
                result.setMessage("更新团购商品信息到数据库，参数为空");
                result.setSuccess(false);
                return result;
            }
			String specIds = (String) map.get("specIds");
			String prices = (String) map.get("prices");
			String[] specIdsSplit = specIds.substring(1, specIds.length()).split(",");
			String[] pricesSplit = prices.substring(1, prices.length()).split(",");
			Map<String,String> specIdsMap = new HashMap<String,String>();
			for (int i = 0; i < specIdsSplit.length; i++) {
				specIdsMap.put(specIdsSplit[i], pricesSplit[i]);			
			}
			String orderExtention = getOrderExtention(specIdsMap);
			map.put("productSpecs", orderExtention);
			Integer updateTaoBaoGroups = shopTaoBaoGroupsService.updateTaoBaoGroups(map);
            if (updateTaoBaoGroups != null && updateTaoBaoGroups > 0) {
                result.setSuccess(true);
                return result;
            } else {
                log.error("更新团购商品信息到数据库失败");
                result.setMessage("更新新增团购商品信息到数据库失败");
                result.setSuccess(false);
                return result;
            }
        } catch (Exception e) {
            log.error("更新团购商品信息失败：", e);
            result.setMessage(e.getMessage());
            result.setSuccess(false);
            return result;
        }
    }

    void timeFormatList(List<Map<String, Object>> taoBaoGroupsList) {
        if (taoBaoGroupsList == null) {
            return;
        }

        for (Map<String, Object> map : taoBaoGroupsList) {
            Integer depositStartTime = (Integer) map.get("depositStartTime");
            Integer depositEndTime = (Integer) map.get("depositEndTime");
            Integer balanceStartTime = (Integer) map.get("balanceStartTime");
            Integer balanceEndTime = (Integer) map.get("balanceEndTime");

            map.put("depositStartTime", depositStartTime > 0 ? timeFormat(depositStartTime * 1000L) : "");
            map.put("depositEndTime", depositEndTime > 0 ? timeFormat(depositEndTime * 1000L) : "");
            map.put("balanceStartTime", balanceStartTime > 0 ? timeFormat(balanceStartTime * 1000L) : "");
            map.put("balanceEndTime", balanceEndTime > 0 ? timeFormat(balanceEndTime * 1000L) : "");
        }
    }

    String timeFormat(Long time) {
        try {
            if (time == null) {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(time);
            return sdf.format(date);
        } catch (Exception e) {
            log.error("long时间转换String错误，long时间：" + time + "e:" + e.getMessage());
            return null;
        }
    }

    public ServiceResult<Boolean> delTaoBaoGroups(Integer[] ids) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            if (ids == null) {
                result.setSuccess(false);
                result.setMessage("淘宝万人团定金尾款删除id为空");
                return result;
            }
            Integer delTaoBaoGroups = shopTaoBaoGroupsService.delTaoBaoGroups(ids);
            if (delTaoBaoGroups != null && delTaoBaoGroups > 0) {
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
                result.setMessage("淘宝万人团定金尾款删除数据失败，id:" + ids);
            }
            return result;
        } catch (Exception e) {
            log.error("long时间转换String错误，long时间：");
            return result;
        }
    }

    public ServiceResult<Map<String, Object>> getTaoBaoGroupsBySkuAndName(String sku, String groupName) {
        ServiceResult<Map<String, Object>> result = new ServiceResult<Map<String, Object>>();
        try {
            if (sku == null || sku.equals("") || groupName == null || groupName.equals("")) {
                result.setSuccess(false);
                result.setMessage("淘宝万人团定金尾款sku与团购名称参数为空");
                return null;
            }
            Map<String, Object> taoBaoGroups = shopTaoBaoGroupsService.getTaoBaoGroupsBySkuAndName(sku, groupName);
            if (taoBaoGroups != null) {
                result.setSuccess(true);
                result.setResult(taoBaoGroups);
            } else {
                result.setSuccess(false);
                result.setMessage("淘宝万人团定金尾款查询数据失败，sku:" + sku + "groupName:" + groupName);
            }
            return result;
        } catch (Exception e) {
            log.error("淘宝万人团定金尾款查询数据异常，e:" + e.getMessage());
            result.setSuccess(false);
            result.setMessage("淘宝万人团定金尾款查询数据异常，e:" + e.getMessage());
            return result;
        }
    }
	@Override
	public ServiceResult<Map<String, Object>> getTaoBaoGroupsById(Integer id) {
		ServiceResult<Map<String, Object>> result = new ServiceResult<Map<String, Object>>();
		try {
			if(id==null||id <= 0){
				result.setSuccess(false);
				result.setMessage("淘宝万人团id查询数据id参数为空");
				return null;
			}
			 Map<String, Object> taoBaoGroups = shopTaoBaoGroupsService.getTaoBaoGroupsById(id);
			if(taoBaoGroups!=null){
				result.setSuccess(true);
				result.setResult(taoBaoGroups);
			}else{
				result.setSuccess(false);
				result.setMessage("淘宝万人团通过id查询数据失败，id:"+id);
			}
			return result;
		} catch (Exception e) {
			log.error("淘淘宝万人团通过id查询数据异常，e:"+e.getMessage());
			result.setSuccess(false);
			result.setMessage("淘宝万人团通过id查询数据异常，e:"+e.getMessage());
			return result;
		}
	}

	/**
	 * 根据设置信息中的productSpecs字段获取套装对应的产品列表
	 *
	 * @param productSpecs
	 * @return
	 */
	public List<Map<String,Object>> getProductList(String productSpecs) throws Exception {
	    List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	    SerializedPhpParser serializedPhpParser = new SerializedPhpParser(productSpecs);
	    Object result = serializedPhpParser.parse(); //通过php序列化后的字符串，获取java对象
	    if (result != null) {
	        @SuppressWarnings("rawtypes")
	        Map myMap = (HashMap) result;
	        @SuppressWarnings("rawtypes")
	        Set keys = myMap.keySet();
	        for (@SuppressWarnings("rawtypes")
	             Iterator i = keys.iterator(); i.hasNext(); ) {
	            String key = (String) i.next(); //获取sku编号
	            if (null == myMap.get(key) || "".equals(myMap.get(key))) {
	                throw new Exception("淘宝套装设置异常:套装为设置名称或为设置金额");
	            }
	            BigDecimal price = new BigDecimal(myMap.get(key).toString());//外部促销价格
	            String skutrim = key.trim();
	            Map<String,Object> map = new HashMap<String,Object>();
	            map.put("price", price);
	            map.put("sku", skutrim);
	            list.add(map);
	        }
	    }
	    return list;
	}
	
	@Override
	public ServiceResult<List<Map<String,Object>>> productSpecsFormat(Integer id) {
		ServiceResult<List<Map<String,Object>>> result = new ServiceResult<List<Map<String,Object>>>();
		try {
			if(id==null||id.equals("")){
				result.setSuccess(false);
				result.setMessage("淘宝万人团查询套装参数为空");
				return null;
			}
			 Map<String, Object> taoBaoGroups = shopTaoBaoGroupsService.getTaoBaoGroupsById(id);
			if(taoBaoGroups!=null && taoBaoGroups.get("productSpecs")!=null){
				List<Map<String,Object>> productList = getProductList(taoBaoGroups.get("productSpecs").toString());
				result.setSuccess(true);
				result.setResult(productList);
			}else{
				result.setSuccess(false);
				result.setMessage("淘宝万人团查询套装失败，id:"+id);
			}
			return result;
		} catch (Exception e) {
			log.error("淘宝万人团查询套装异常，e:"+e.getMessage());
			result.setSuccess(false);
			result.setMessage("淘宝万人团查询套装异常，e:"+e.getMessage());
			return result;
		}
	}

    public String getOrderExtention(Map<String,String> li) {
        String s = "";
        try {
            byte[] a = PHPSerializer.serialize(li);
            s = new String(a, 0, a.length);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return s;
    }

	@Override
	public ServiceResult<List<OrderProductsVo>> searchList(OrderProductsVo vo) {
			// TODO Auto-generated method stub
		// 查询source来源放入map中
			Map <String,String> map = new HashMap<String,String>();
			List<InvChannel2OrderSource> list_source = stockInvChannel2OrderSourceService.getAllOrder2ChannelSource();
			for(InvChannel2OrderSource source :list_source) {
				map.put(source.getOrderSource(), source.getNote());
			}
			ServiceResult<List<OrderProductsVo>> result = new ServiceResult<List<OrderProductsVo>>();
			List<OrderProductsVo>  list =shopOperationAreaService.searchcod(vo);
			for(OrderProductsVo ovo:list) {
				ovo.setSource(map.get(ovo.getSource()));
			}
			result.setResult(list);
			PagerInfo pi = new PagerInfo();
			pi.setRowsCount(shopOperationAreaService.findCodConfirmCNT(vo));
			result.setPager(pi);
			return result;
		}

	@Override
	public ServiceResult<List<InvChannel2OrderSource>> getSource() {
		// TODO Auto-generated method stub
		ServiceResult<List<InvChannel2OrderSource>> result = new ServiceResult<List<InvChannel2OrderSource>>();
		List<InvChannel2OrderSource> list_source = stockInvChannel2OrderSourceService.getAllOrder2ChannelSource();
		result.setResult(list_source);
		return result;
	}
	}

