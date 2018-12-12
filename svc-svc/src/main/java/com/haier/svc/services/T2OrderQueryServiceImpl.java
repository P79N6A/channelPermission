package com.haier.svc.services;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.purchase.data.service.CnT2PurchaseStockService;
import com.haier.purchase.data.service.PurchaseT2OrderQueryService;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.service.StockInvStockChannelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.model.CnT2PurchaseStock;
import com.haier.purchase.data.model.CrmOrderItem;
import com.haier.purchase.data.model.T2OrderItem;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.service.ShopItemAttributeService;
import com.haier.svc.bean.queryfrostorderfromehaiertooms.InType;
import com.haier.svc.bean.queryfrostorderfromehaiertooms.OutType;
import com.haier.svc.purchase.queryfrostorderfromehaiertooms.QueryFrostOrderFromEHAIERToOMS;
import com.haier.svc.purchase.queryfrostorderfromehaiertooms.QueryFrostOrderFromEHAIERToOMS_Service;
import com.haier.svc.service.T2OrderQueryService;
import com.haier.svc.util.DateUtil;

/**
 * @author 李超
 */
@Service
//@Transactional
public class T2OrderQueryServiceImpl implements T2OrderQueryService {
	
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(T2OrderQueryServiceImpl.class);
	
	@Autowired
	private PurchaseT2OrderQueryService purchaseT2OrderQueryService;
	@Autowired
	private ShopItemAttributeService shopItemAttributeService;
	
	@Autowired
    private StockInvStockChannelService stockInvStockChannelService;
	@Autowired
	private CnT2PurchaseStockService cnT2PurchaseStockService;
	@Value("${t2OrderResponse.location}")
    private String wsdlLocation;
	
    
    /**
     * 综合查询获取T+2订单信息
     * 
     * @param Map
     *            <String, Object> params
     * @return
     */
    // huangjun
    @Override
    /**
     * 综合查询获取T+2订单信息
     * 
     * @param Map
     *            <String, Object> params
     * @return
     */
    public ServiceResult<List<T2OrderItem>> findT2OrderMultipleList(Map<String, Object> params) {
        return getT2OrderList(params);

    }
    
	/**
	 * 获取T+2订单信息list
	 * (non-Javadoc)
	 * @see com.haier.svc.service.T2OrderQueryService#findT2OrderMultipleList(java.util.Map)
	 */
	@Override
	public ServiceResult<List<T2OrderItem>> getT2OrderList(
			Map<String, Object> params) {
	        ServiceResult<List<T2OrderItem>> result = new ServiceResult<List<T2OrderItem>>();
	        try {
	        	List<T2OrderItem> list = purchaseT2OrderQueryService.findT2OrderMultipleList(params);
	        	for(T2OrderItem item : list){
	        		Map<String, Object> map = new HashMap<String, Object>();
	        		map.put("cnStockSyncsId", item.getOrder_id());
	        		List<CnT2PurchaseStock>  ctList = cnT2PurchaseStockService.queryCnT2PurchaseStock(map);
	        		if (ctList != null && ctList.size() > 0) {
	        			item.setSapMessage(ctList.get(0).getMessage());
	        			if(ctList.get(0).getProcessTime() != null){
	        				item.setSapProcessTime(DateUtil.getFormatDateTime(ctList.get(0).getProcessTime()));
	        			}
	        			item.setSapStatus(ctList.get(0).getStatus());
	        		}
	        	}
	            result.setResult(list);
	            int pagecount = purchaseT2OrderQueryService.getRowCnts();
	            PagerInfo pi = new PagerInfo();
	            pi.setRowsCount(pagecount);
	            result.setPager(pi);
	            return result;
	        } catch (Exception e) {
	            result.setSuccess(false);
	            result.setMessage(e.getMessage());
	            log.error("获取T+2订单信息失败：", e);
	        }
	        return result;
	}

	/**
	 * 手工关单
	 * (non-Javadoc)
	 * @see com.haier.svc.service.T2OrderQueryService#manualCloseOrderList(java.util.Map)
	 */
	@Override
	public void manualCloseOrderList(Map<String, Object> params) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // 情报更新
            purchaseT2OrderQueryService.manualCloseOrder(params);
            // 提交事务
            //transactionManager.commit(status);
            //return false;
            // 执行成功 提交订单
            result.setResult(true);
        } catch (Exception e) {
        	// 回滚事务
        	//transactionManager.rollback(status);
            // 记录日志
            log.error("[T2OrderModel][manualCloseOrderList]:手工关单失败:", e);
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("手工关单失败：", e);
        }
	}

	/**
	 * 撤销手工关单 
	 * (non-Javadoc)
	 * @see com.haier.svc.service.T2OrderQueryService#cancelCloseOrderList(java.util.Map)
	 */
	@Override
	public void cancelCloseOrderList(Map<String, Object> params) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        //TransactionStatus status = transactionManager.getTransaction(def);
        try {
            // 情报更新
            purchaseT2OrderQueryService.cancelCloseOrder(params);
            // 提交事务
            //transactionManager.commit(status);
            // 执行成功 提交订单
            result.setResult(true);
        } catch (Exception e) {
        	//回滚事务
        	//transactionManager.rollback(status);
        	//日志信息
        	log.error("[T2OrderModel][cancelCloseOrderList]:撤消手工关单失败:", e);
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("撤消手工关单失败：", e);
        }
	}

	/**
	 * 已冻结推送  
	 * (non-Javadoc)
	 * @see com.haier.svc.service.T2OrderQueryService#commitAgainOrderMultiple(java.util.Map)
	 */
	@Override
	public int commitAgainOrderMultiple(Map<String, Object> params) {
        try {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            String omsOrderId = (String) params.get("oms_order_id");
            String passReason = (String) params.get("pass_reason");
            OutType outPut = new OutType();
            outPut = ReleaseFrostT2Order(omsOrderId, passReason);
            /* outPut.setMessage("推送失败");
             outPut.setStatus("E");*/
            int count;
            if (outPut != null) {
                resultMap.put("oms_order_id", params.get("oms_order_id"));
                resultMap.put("pass_reason", params.get("pass_reason"));
                resultMap.put("no_pass_reason", outPut.getMessage());
                resultMap.put("status", outPut.getStatus());
                count = purchaseT2OrderQueryService.commitAgainOrderMultiple(resultMap);
            } else {
                log.info("T+2订单解除冻结时,调用接口失败,oms_order_id:" + omsOrderId);
                count = 0;
            }
            return count;
        } catch (Exception ex) {
            log.error("T+2订单解除冻结时,发生未知异常");
            return 0;
        }
	}
	
    public OutType ReleaseFrostT2Order(String omsOrderId, String reason) throws Exception {
        try {
//            String path = "file:"
//                          + this.getClass()
//                              .getResource(wsdlLocation + "/QueryFrostOrderFromEHAIERToOMS.wsdl")
//                              .getPath();
//            URL url = new URL(path);
        	URL url = this.getClass().getResource(
        			wsdlLocation + "/QueryFrostOrderFromEHAIERToOMS.wsdl");
            QueryFrostOrderFromEHAIERToOMS_Service service = new QueryFrostOrderFromEHAIERToOMS_Service(
                url);

            QueryFrostOrderFromEHAIERToOMS soap = service.getQueryFrostOrderFromEHAIERToOMSSOAP();
            InType in = new InType();
            log.info("T+2订单解除冻结, " + " id : " + omsOrderId);
            in.setOrderCode(omsOrderId);
            in.setDetailCode("10");
            in.setFromSystem("EHAIER");
            in.setOperationFlage("S");
            if (!"".equals(reason)) {
                in.setCancelReason(reason);
            }
            OutType output = soap.queryFrostOrderFromEHAIERToOMS(in);
            //System.out.println("T+2订单撤销" + " id : " + order.getOms_id() + " ResponseData:"
            //                   + JsonUtil.toJson(output));
            log.info("T+2订单解除冻结, " + " oms_order_id: " + omsOrderId + " output:" + JsonUtil.toJson(output));
            return output;
        } catch (Exception e) {
            log.error("T+2订单解除冻结时,发生未知异常,oms_order_id:" + omsOrderId + ",reason:" + reason, e);
            return null;
        }
    }
    
    @Override
    public ServiceResult<List<InvStockChannel>> getChannels() {
        ServiceResult<List<InvStockChannel>> result = new ServiceResult<List<InvStockChannel>>();
        try {
            result.setResult(stockInvStockChannelService.getAll());
        } catch (Exception e) {
            log.error("获取渠道出现未知异常：", e);
            result.setSuccess(false);
            result.setError("获取渠道出现未知异常:", e.getMessage());
        }
        return result;
    }
    
    @Override
    public ServiceResult<List<ItemAttribute>> queryProductGroupByCbsCategory(String cbsCategory) {
        ServiceResult<List<ItemAttribute>> result = new ServiceResult<List<ItemAttribute>>();
        try {
            result.setResult(shopItemAttributeService.queryProductGroupByCbsCategory(cbsCategory));
        } catch (Exception e) {
            log.error("根据CBS品类查询产品组时，发生未知异常：", e);
            result.setMessage("根据CBS品类查询产品组发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 通过ValueSetId检索ItemAttribute List
     * @param valueSetId 属性标识
     * @return list of ItemAttribute
     */
    @Override
    public ServiceResult<List<ItemAttribute>> getByValueSetId(String valueSetId) {
        ServiceResult<List<ItemAttribute>> result = new ServiceResult<List<ItemAttribute>>();
        try {
            result.setResult(shopItemAttributeService.getByValueSetId(valueSetId));
        } catch (Exception e) {
            log.error("检索ItemAttribute List，发生未知异常：", e);
            result.setMessage("检索ItemAttribute List，发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
    
    /**
     * 获取PO查询信息
     * 
     * @param Map
     *            <String, Object> params
     * @return
     */
    @Override
    public ServiceResult<List<CrmOrderItem>> getPOList(Map<String, Object> params) {
        ServiceResult<List<CrmOrderItem>> result = new ServiceResult<List<CrmOrderItem>>();
        try {
        	List<CrmOrderItem> list = purchaseT2OrderQueryService.findPOList(params);
        	for(CrmOrderItem crm : list){
        		Map<String, Object> map = new HashMap<String, Object>();
        		map.put("cnStockSyncsId", crm.getOrder_id());
        		List<CnT2PurchaseStock>  ctList = cnT2PurchaseStockService.queryCnT2PurchaseStock(map);
        		if(ctList != null && ctList.size()>0){
        			crm.setSapMessage(ctList.get(0).getMessage());
        			if(ctList.get(0).getProcessTime() != null){
        				crm.setSapProcessTime(DateUtil.getFormatDateTime(ctList.get(0).getProcessTime()));
        			}
        			crm.setSapStatus(ctList.get(0).getStatus());
        		}
        	}
            result.setResult(list);
            PagerInfo pi = new PagerInfo();
            pi.setRowsCount(purchaseT2OrderQueryService.findPOListCNT(params));
            result.setPager(pi);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("获取T+2 PO订单信息list失败：", e);
        }
        return result;
    }
    
}
