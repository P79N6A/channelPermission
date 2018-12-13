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
import com.haier.common.util.StringUtil;
import com.haier.shop.model.*;
import com.haier.shop.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.order.model.Ustring;
import com.haier.order.service.OrderCenterOrderOperationService;
import com.haier.order.util.HelpUtils;
import com.haier.order.util.OrderSnUtil;
import com.haier.order.util.PHPSerializer;
import com.haier.order.util.SerializedPhpParser;
import com.haier.stock.model.InvChannel2OrderSource;
import com.haier.stock.service.StockInvChannel2OrderSourceService;
import com.haier.system.model.SyncOrderConfigs;
import com.haier.system.service.SyncOrderConfigsService;

import net.sf.json.JSONArray;

@Service
public class OrderCenterOrderOperationServiceImpl implements OrderCenterOrderOperationService {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(OrderCenterOrderOperationServiceImpl.class);
    @Autowired
    private OrderPriceProductGroupIndustryService orderPriceProductGroupIndustryService;
    @Autowired
    private SyncOrderConfigsService syncOrderConfigsService;
    @Autowired
    private ShopOrdersService shopOrdersService;
    @Autowired
    ShopOperationAreaService   shopOperationAreaService;
    @Autowired
    private ShopTaoBaoGroupsService shopTaoBaoGroupsService;
    @Autowired
    private StockInvChannel2OrderSourceService stockInvChannel2OrderSourceService;
    @Autowired
    private InvoicesService invoicesService;
    @Autowired
    private ShopOrderWorkflowsService shopOrderWorkflowsService;
    @Autowired
    private MemberInvoicesService memberInvoicesService;
    @Autowired
    private OrderProductsNewService orderProductsNewService;
    @Autowired
    private ShopOrderOperateLogsService  shopOrderOperateLogsService;
    @Autowired
    private HelpUtils helpUtils;
    @Autowired
    private OrderPriceSourceChannelService orderPriceSourceChannelService;


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
            //Integer count = shopOrdersService.getRowCnts();
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
            Integer count = shopOrdersService.getRowCnts();
            //导出条数大于1000则只导出前1000条
            if(count > 1000){
                if(queryOrder.getPage() == null){
                    queryOrder.setPage(1);
                }
                if (queryOrder.getRows() == null) {
                    queryOrder.setRows(1000);
                }
                if (queryOrder.getPage() != null && queryOrder.getPage() != 0) {
                    queryOrder.setPage((queryOrder.getPage() - 1) * queryOrder.getRows());
                }

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
			if(!StringUtil.isEmpty(specIds) && !StringUtil.isEmpty(prices)) {
                String[] specIdsSplit = specIds.substring(1, specIds.length()).split(",");
                String[] pricesSplit = prices.substring(1, prices.length()).split(",");
                Map<String,String> specIdsMap = new HashMap<String,String>();
                for (int i = 0; i < specIdsSplit.length; i++) {
                    specIdsMap.put(specIdsSplit[i], pricesSplit[i]);
                }
                String orderExtention = getOrderExtention(specIdsMap);
                map.put("productSpecs", orderExtention);
            } else{
                map.put("productSpecs", "");
            }
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
			if(!StringUtil.isEmpty(specIds) && !StringUtil.isEmpty(prices)) {
                String[] specIdsSplit = specIds.substring(1, specIds.length()).split(",");
                String[] pricesSplit = prices.substring(1, prices.length()).split(",");
                Map<String, String> specIdsMap = new HashMap<String, String>();
                for (int i = 0; i < specIdsSplit.length; i++) {
                    specIdsMap.put(specIdsSplit[i], pricesSplit[i]);
                }
                String orderExtention = getOrderExtention(specIdsMap);
                map.put("productSpecs", orderExtention);
            }else{
                map.put("productSpecs", "");
            }
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
			    String productSpecsStr = taoBaoGroups.get("productSpecs").toString();
                List<Map<String, Object>> productList = new ArrayList<>();
			    if(!StringUtil.isEmpty(productSpecsStr)) {
                    productList = getProductList(productSpecsStr);
                }
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
	/**
	 * 查询复制网单的信息
	 * @param productId
	 * @param orderSn
	 * @return
	 */
	public Map<String,Object> copyProductView(String productId,String orderSn){
		Gson gson = new Gson();
		Map<String,Object> map = new HashMap<>();
		Orders order = shopOrdersService.selectOrderView(orderSn);//查询订单信息
        List<Map<String,Object>> productsVo=shopOperationAreaService.selectOrderProductViewTwo(orderSn);//查询网单数据
		Invoices invoices =invoicesService.selectInvoiceView(orderSn);
		map.put("order", order);
		map.put("productsVo", gson.toJson(productsVo));
		map.put("productsVoSize",productsVo);
        //map.put("productsVo", productsVo);
		map.put("invoices", invoices);	
		return map;
	}
	
	public List<OrderPriceProductGroupIndustry> getProductGroupIndustryList(){
		return orderPriceProductGroupIndustryService.getProductGroupIndustryList();
	}
	
	public List<SyncOrderConfigs> selectSyncOrderonfigs(){
		return syncOrderConfigsService.selectSyncOrderConfigs();
	}
	/**
	 * 保存复制网单信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Json copyProductSave(JSONObject params,String username) {
		Json json = new Json();
		// TODO Auto-generated method stub
		Orders  orders= shopOrdersService.get(Integer.parseInt(params.get("id").toString()));//根据订单id查询订单信息
		//把前台修改的数据放到数据库
		orders.setRelationOrderSn(Ustring.getString(orders.getOrderSn()));//关联订单编号
		orders.setIsProduceDaily(Integer.parseInt(Ustring.getString0(params.get("isProduceDaily"))));//是否日日单

        if (params.get("source") != null && StringUtils.isNotBlank(params.get("source").toString())){
            orders.setSource(Ustring.getString(params.get("source")));//订单来源
        }
//		orders.setSourceOrderSn(Ustring.getString(params.get("sourceOrderSn")));//来源订单号
		orders.setConsignee(Ustring.getString(params.get("consignee")));//收获人
		orders.setProvince(Integer.parseInt(Ustring.getString0(params.get("province"))));//省
		orders.setAddress(Ustring.getString(params.get("address")));//详细地址
		orders.setZipcode(Ustring.getString(params.get("zipcode")));//邮遍
		orders.setMobile(Ustring.getString(params.get("mobile")));//电话
		orders.setPhone(Ustring.getString(params.get("phone")));//手机
		orders.setSyncTime(new Date().getTime()/1000);
		String oldOrderSn = orders.getOrderSn();
		orders.setOrderSn(helpUtils.getOrderNo());
		orders.setAgent("系统");

		/*orders.setConfirmTime(Long.valueOf(new Date().getTime()).intValue());
		orders.setFirstConfirmTime(Long.valueOf(new Date().getTime()).intValue());*/
		orders.setFirstConfirmPerson("系统");
		orders.setFinishTime(null);
		orders.setSmConfirmStatus(1);

		net.sf.json.JSONArray jsonArray = JSONArray.fromObject(params.get("productsVo"));
		List<OrderProductsVo> productsVo = (List<OrderProductsVo>) net.sf.json.JSONArray.toCollection(jsonArray,OrderProductsVo.class);
		OrderProducts products = new OrderProducts();

        //Map mapType = com.alibaba.fastjson.JSON.parseObject(params.get("invocie").toString(),Map.class);
        orders.setOrderStatus(200);
        orders =helpUtils.isPanOrder(orders);
        orders.setPaymentStatus(100);
        orders.setPoints(0L);
        //手动复制订单标志
        orders.setIsCopy(1);
        int orderId=shopOrdersService.insertOrders(orders);//插入订单信息
        //网单List
        List<String>  NetSingleNumberList = new ArrayList<>();
        for(OrderProductsVo vo:productsVo){
            products=shopOperationAreaService.queryGetId(vo.getId().toString());//根据id查询网单信息


                products.setOrderId(orderId);//关联网单id
                products =helpUtils.isPanOrderProduct(products);
                products.setStatus(0);
                products.setInvoiceNumber("");
                products.setLessOrderSn("");
                products.setOutping("");
                products.setLessShipTime(0);
                products.setCloseTime(0);
                products.setReceiptNum("");
                products.setReceiptAddTime("");
                products.setTbOrderSn("");
                products.setcPaymentStatus(200);
                products.setLockedNumber(0);
                products.setUnlockedNumber(0);
                products.setcPayTime(0L);
                products.setExpressName("");
                products.setWaitGetLesShippingInfo(0);
                products.setIsMakeReceipt(9);
                products.setSystemRemark("");
                products.setHpRegisterDate(0);
                products.setHpReservationDate(0);
                products.setShippingOpporunity(0);
                products.setNetPointId(0);
                products.setScode("");
                products.setTsCode("");

                int orderProducrId=shopOperationAreaService.insertOrderProducts(products);//插入网单信息
                //插入原订单操作日志
                shopOrderOperateLogsService.insert(
                        constructOperateLog(orders,products,username,"复制订单（系统自动）生成新的换机单","原订单号为:"+oldOrderSn + "新订单号为:"+orders.getOrderSn(),null));

                OrderWorkflows orderWorkflows= shopOrderWorkflowsService.getByOrderProductId(products.getId());
                if(orderWorkflows != null) {
                    OrderWorkflows newOrderWorkflows = new OrderWorkflows();
                    newOrderWorkflows.setOrderId(orderId);
                    newOrderWorkflows.setOrderProductId(orderProducrId);
                    newOrderWorkflows.setAddTime(orderWorkflows.getAddTime());
                    newOrderWorkflows.setPayTime(0L);
                    shopOrderWorkflowsService.insert(newOrderWorkflows);//插入订单全流程监控表
                }

                MemberInvoices memberInvoices= memberInvoicesService.getByOrderId(orders.getId()); //查询 用户发票信息
                if (memberInvoices != null) {
                    memberInvoices.setId(null);
                    memberInvoices.setOrderId(orderId);
                    memberInvoicesService.insert(memberInvoices);//插入 用户发票表
                }

            orders.setId(orderId);
            products.setId(orderProducrId);
            //插入新订单操作日志
            shopOrderOperateLogsService.insert(
                    constructOperateLog(orders,products,username,"复制订单（系统自动）生成新的换机单","原订单号为:"+oldOrderSn + "新订单号为:"+orders.getOrderSn(),null));

                String cOrderSn = OrderSnUtil.getCOrderSn(orderProducrId);
                products.setCOrderSn(cOrderSn);//生成网单号
                products.setId(orderProducrId);
                orderProductsNewService.updateCOrderSn(products);//更改网单号
                NetSingleNumberList.add(products.getCOrderSn());
            }
        json.setMsg("保存成功！ 复制订单号为："+orders.getOrderSn()+"   网单号为："+String.valueOf(NetSingleNumberList));
        json.setSuccess(true);
        return json;
    }



    @Override
    public List<OrderPriceSourceChannel> selectOrderPriceSourceChannel() {
        return orderPriceSourceChannelService.getOrderPriceSourceChannelList();
    }

    /**
     * 构造订单操作日志对象
     * @param orders 订单对象
     * @param orderProducts  网单对象
     * @param operator 操作人
     * @param changeLog 变更记录
     * @param remark 备注
     * @param log OrderOperateLogs
     * @return
     */
    public static OrderOperateLogs constructOperateLog(Orders orders, OrderProducts orderProducts,
                                                       String operator, String changeLog,
                                                       String remark, OrderOperateLogs log) {
        log = new OrderOperateLogs();
        log.setChangeLog(StringUtil.isEmpty(changeLog) ? "" : changeLog);
        log.setOperator(StringUtil.isEmpty(operator) ? "系统" : operator);
        log.setPaymentStatus(
                null == orders || orders.getPaymentStatus() == null ? 0 : orders.getPaymentStatus());
        log.setRemark(StringUtil.isEmpty(remark) ? ""
                : (remark.length() > 255 ? remark.substring(0, 255) : remark));
        log.setSiteId(1);
        log.setOrderId(Integer.valueOf(orders.getId()));
        if (orderProducts != null) {
            log.setNetPointId(orderProducts.getNetPointId());
            log.setOrderProductId(orderProducts.getId());
            log.setStatus(orderProducts.getStatus());
        } else {
            log.setNetPointId(0);
            log.setOrderProductId(0);
            log.setStatus(0);
        }
        return log;
    }
}

