package com.haier.afterSale.model;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.haier.afterSale.service.VomOrderService;
import com.haier.afterSale.util.HelpUtils;
import com.haier.afterSale.webService.pushSAP.InType;
import com.haier.afterSale.webService.pushSAP.OutType;
import com.haier.afterSale.webService.pushSAP.TMSGType;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.EisInterfaceFinance;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.purchase.data.model.GoodsBackInfoResponse;
import com.haier.shop.model.Invoices;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrderRepairsNew;
import com.haier.shop.model.OrderType;
import com.haier.shop.model.OrdersNew;
import com.haier.stock.model.InventoryBusinessTypes;
@Service
public class PushReturnInfoToGVSHandler extends SFHandler{
	 private Logger                     logger = LogManager
             .getLogger(PushReturnInfoToGVSHandler.class);
	 @Autowired
	 private VomOrderService            vomOrderService;
	 @Value("${wsdlPath}")
	    private String wsdlPath;
	 private static Map<String, String> REASONS;
	    protected static HashSet<String> cOrderSns = new HashSet<String>();
	    public static void clear() {
	        cOrderSns.clear();
	    }
	  //退货入库
	  @SuppressWarnings("static-access")
	public Result process(LesStockTransQueue transQueue) throws MalformedURLException {

	        Result result = new Result();

	        String corderSn = transQueue.getCorderSn();
	        if (StringUtil.isEmpty(corderSn)) {
	            result.status = EisInterfaceFinance.STATUS_ERROR;
	            result.message = "处理退货入库失败：退货单号不正确，" + corderSn;
	            return result;
	        }

	        //处理正品退货
	        String soou = this.isGenuineGoods(corderSn);
	        if (!StringUtil.isEmpty(soou)
	            && InventoryBusinessTypes.IN_RETURNED.getCode().equals(transQueue.getBillType())) {
	            //正品退货发送SAP
//	            return this.reverseGenuineGoodsToGVS(transQueue, soou);
	        }

//	        if (cOrderSns.contains(corderSn)) {
//	            result.status = EisInterfaceFinance.STATUS_WARN;
//	            result.message = "网单号：" + corderSn + "已经调用退货入库接口";
//	            return result;
//	        }
//	        cOrderSns.add(corderSn);

	        //组装数据
	        com.haier.afterSale.webService.pushSAP.ObjectFactory objectFactory = new com.haier.afterSale.webService.pushSAP.ObjectFactory();
	        InType request = objectFactory.createInType();
	        request.setZSYST("EHAI");//系统编码

	        request.setZWBDR(corderSn);//退货网单号
//	        request.setZWBDR("WD182919376957");//退货网单号

	        OrderProductsNew orderProducts = getOrderProducts(corderSn.replaceAll("TH.*", ""));
	        OrdersNew order = getOrderByWD(orderProducts);

	        if (order == null) {
	            result.message = "处理退货入库失败：未获取到退货单关联的订单,退货单号：" + corderSn;
	            result.status = EisInterfaceFinance.STATUS_ERROR;
	            return result;
	        }
	        // 原始网单号
	        if (orderProducts.getSplitFlag() == OrderProductsNew.SPLITFLAG_SPLIT_NEW) {
	       	 request.setZWBDRO(orderProducts.getSplitRelateCOrderSn());// 原始网单号，拆单后新单需要原网单单号
	        } else {
	       	 request.setZWBDRO(orderProducts.getCOrderSn());//原始网单号
	        }
	        //定金尾款订单，如销售出库推送了SAP，退货入库也推送SAP，如未推送，则都不推送
	        if (OrderType.TYPE_GROUP_ADVANCE_TAIL.getValue().equals(order.getOrderType())
	            && order.getTaobaoGroupId() > 0 && orderProducts.getShippingOpporunity().equals(0)) {

	            //3W的单子特殊处理:因为3W的单子，出库走的单独的逻辑
	            if (orderProducts.getStockType() != null
	                && orderProducts.getStockType().equalsIgnoreCase("3W")) {
	                if (orderProducts.getLessShipTime().equals(0L)
	                    || orderProducts.getLessShipTime().intValue() == 0) {
	                    result.status = EisInterfaceFinance.STATUS_ERROR;
	                    result.message = "3W网单[" + transQueue.getCorderSn()
	                                     + "]属于定金尾款退货，CBS未收到原单，退货单不推送SAP";
	                    logger
	                        .info("3W网单[" + transQueue.getCorderSn() + "]属于定金尾款退货，CBS未收到原单，退货单不推送SAP");
	                    return result;
	                }
	            } else {

	                List<LesStockTransQueue> depositList = getLesStockTransQueueDao()
	                    .getLesStockTransQueueByCorderSn(corderSn.replaceAll("TH.*", ""));
	                if ((depositList == null || depositList.isEmpty())
	                    && orderProducts.getSplitFlag().intValue() == 2
	                    && !StringUtil.isEmpty(orderProducts.getSplitRelateCOrderSn())) {//拆单的情况
	                    depositList = getLesStockTransQueueDao().getLesStockTransQueueByCorderSn(
	                        orderProducts.getSplitRelateCOrderSn());
	                }
	                if (depositList == null || depositList.isEmpty()) {
	                    result.status = EisInterfaceFinance.STATUS_ERROR;
	                    result.message = "网单[" + transQueue.getCorderSn()
	                                     + "]属于定金尾款退货，CBS未收到原单，退货单不推送SAP";
	                    logger.info("网单[" + transQueue.getCorderSn() + "]属于定金尾款退货，CBS未收到原单，退货单不推送SAP");
	                    return result;
	                }
	                Set<Integer> status = new HashSet<Integer>();
	                for (LesStockTransQueue deposit : depositList) {
	                    Integer transQueueId = deposit.getId();
	                    EisInterfaceFinance eisInterfaceFinance = getInterfaceFinance(transQueueId);
	                    if (eisInterfaceFinance == null) {
	                        continue;
	                    }
	                    status.add(eisInterfaceFinance.getStatus());
	                }
	                if (status.isEmpty()) {
	                    result.status = EisInterfaceFinance.STATUS_ERROR;
	                    result.message = "网单[" + transQueue.getCorderSn()
	                                     + "]属于定金尾款退货，原单未推送SAP，退货单不推送SAP";
	                    logger.info("网单[" + transQueue.getCorderSn() + "]属于定金尾款退货，原单未推送SAP，退货单不推送SAP");
	                    return result;
	                }
	                if (status.contains(EisInterfaceFinance.STATUS_ERROR)) {
	                    result.status = EisInterfaceFinance.STATUS_ERROR;
	                    result.message = "网单[" + transQueue.getCorderSn()
	                                     + "]属于定金尾款退货，原单未成功推送SAP，退货单不推送SAP";
	                    logger
	                        .info("网单[" + transQueue.getCorderSn() + "]属于定金尾款退货，原单未成功推送SAP，退货单不推送SAP");
	                    return result;
	                }
	            }
	        }

	        /*if (new Date((long) order.getAddTime() * 1000).before(DateUtil.parse("2013-08-01 00:00:00",
	            "yyyy-MM-dd HH:mm:ss"))) {
	            result.status = EisInterfaceFinance.STATUS_SUCCESS;
	            result.message = "此退货的网单早于 2013-08-01 00:00:00 ，" + corderSn;
	            return result;
	        }*/
	        //判断下单日期和推送日期是否在同一个月 如果是的话则推送下单日期 如果不再同一个月则推送服务器日期
	        if(help.equals(transQueue.getBillTime(),new Date())){
	        	  request.setZWBDT(DateUtil.format(transQueue.getBillTime(), "yyyy-MM-dd"));//网单创建日期
	        }else {
	        	 request.setZWBDT(DateUtil.format(new Date(), "yyyy-MM-dd"));//网单创建日期
	        }
	      

	        OrderRepairsNew orderRepairs = getOrderRepairs(orderProducts);
	        if (orderRepairs == null) {
	            result.status = EisInterfaceFinance.STATUS_ERROR;
	            result.message = "处理退货入库失败：网单关联的有效退货单已不存在，退货单号：" + corderSn;
	            return result;
	        }
	        String reason = help.getReasonCode(orderRepairs.getReason());
	        if (StringUtil.isEmpty(reason))
	            reason = help.getReasonCode("其他");
	        request.setAUGRU(reason);//退货原因

	        request.setMATNR(orderProducts.getSku());//物料编码
	        request.setKWMENG(new BigDecimal(orderProducts.getNumber()));//数量

	        BigDecimal price = null;
	        List<Invoices> invoices = invoicesDao.getByOrderProductId(orderProducts.getId());
	        boolean isMakeReceipt = false;

	        for (Invoices invoice : invoices) {
	            if (invoice.getState() == 4) {
	                isMakeReceipt = true;
	                price = invoice.getPrice();
	                break;
	            }
	        }

	        if (!isMakeReceipt)
	            price = computeInvoicePrice(order, orderProducts);

	        request.setKBETR(price);//单价
	        request.setKUNNRAG(getCustormCode(orderProducts, order, price));// 客户编码
	        request.setKUNNRRG(HelpUtils.getKunnrRgNull(order.getSource()));//付款方
	        request.setLGORT(transQueue.getSecCode());//库位
	        String orderSn = order.getOrderSn();
	        String relationOrderSn = order.getRelationOrderSn();
	        if (!StringUtil.isEmpty(relationOrderSn) && !"新单".equalsIgnoreCase(relationOrderSn)) {
	            orderSn = relationOrderSn;
	        } else if (!StringUtil.isEmpty(order.getSourceOrderSn())
	                   && !"新单".equalsIgnoreCase(order.getSourceOrderSn())) {
	            orderSn = order.getSourceOrderSn();
	        } else {
	            orderSn = order.getOrderSn();
	        }
	        // 如果是定金尾款订单，则取本身单号
	        if (order.getOrderType().equals(OrderType.TYPE_GROUP_ADVANCE.getIntValue())) {
	            orderSn = order.getOrderSn();
	        }

	        if ("BLPHH".equals(order.getSource())) {//不良品换货业务，订单号不能截取
	            if (orderSn != null && orderSn.contains("_")) {//网单号不含 “_” 的无需进行截取
	                orderSn = orderSn.substring(0, orderSn.indexOf("_"));
	            }
	            request.setZORDR(orderSn);//订单号
	        } else {//其他业务需要截掉订单号的前缀。如TOP_11111111 要截取为 11111111
	            request.setZORDR(orderSn == null ? "" : orderSn.replaceAll(".*_", ""));//订单号
	        }

	        request.setZCHNL(getChannel(corderSn, order.getSource()));//渠道
	        request.setZFGBL(isMakeReceipt ? "0" : "1");// 是否已开票

	        List<InType> in = new ArrayList<InType>();
	        in.add(request);
	        URL url = this.getClass().getResource(wsdlPath + "/PushReturnInfoToGVS.wsdl");
//	        String path = "file:"+ this.getClass().getResource("/wsdl_test/PushReturnInfoToGVS.wsdl").getPath();
	        com.haier.afterSale.webService.pushSAP.PushReturnInfoToGVS soap = new com.haier.afterSale.webService.pushSAP.PushReturnInfoToGVS_Service(
	        		url).getPushReturnInfoToGVSSOAP();
//	        com.haier.afterSale.webService.pushSAP.PushReturnInfoToGVS soap = new com.haier.afterSale.webService.pushSAP.PushReturnInfoToGVS_Service(getWSDLURL()).getPushReturnInfoToGVSSOAP();

	        // 要记录接口数据日志
	        EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
	        dataLog.setForeignKey(transQueue.getCorderSn());
	        dataLog.setRequestTime(DateUtil.currentDateTime());
	        dataLog.setRequestData(JsonUtil.toJson(in));
	        Long startTime = System.currentTimeMillis();

	        try {
	        	OutType pushReturnInfoToGVS = soap.pushReturnInfoToGVS(in);
				List<TMSGType> results = pushReturnInfoToGVS.getTMSG();
	            String msg = JsonUtil.toJson(results);

	            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
	            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
	            dataLog.setResponseTime(DateUtil.currentDateTime());

	            dataLog.setResponseData(msg);
	            if (results == null || results.size() <= 0) {
	                result.status = EisInterfaceFinance.STATUS_FAILED;
	                result.message = "调用EAI接口返回数据不合法";
	            } else {
	                boolean flag = true;
	                for (TMSGType rs : results) {
	                    if (!flag)
	                        break;
	                    if ("E".equals(rs.getTYPE()))
	                        flag = false;
	                }
	                result.status = flag ? EisInterfaceFinance.STATUS_UNKNOWN
	                    : EisInterfaceFinance.STATUS_FAILED;
	                result.message = msg;
	            }
	        } catch (Exception e) {
	            dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
	            dataLog.setResponseTime(DateUtil.currentDateTime());
	            dataLog.setResponseData("");
	            dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
	            dataLog.setErrorMessage(e.getMessage());
	            result.status = EisInterfaceFinance.STATUS_FAILED;
	            result.message = "调用EAI接口失败";
	            logger.error("调用EAI接口 pushReturnInfoToGVS 失败：", e);
	        }

	        dataLog.setCreateTime(DateUtil.currentDateTime());
	        recordEisInterfaceDataLog(dataLog);

	        return result;
	    }
	  /**
	     * 判断单据号是否为正品退货<br/>
	     * 是正品退货则返回so|ou号，不是正品退货则返回null
	     * @return
	     */
	    private String isGenuineGoods(String orderNo) {

	        if (StringUtil.isEmpty(orderNo)) {
	            return null;
	        }

	        if (!orderNo.startsWith("WD")) {
	            return null;
	        }

	        Map<String, Object> paramMap = new HashMap<String, Object>();
	        paramMap.put("orderNo", orderNo);
	        ServiceResult<GoodsBackInfoResponse> result = vomOrderService.getGoodsBackInfo(paramMap);
	        if (!result.getSuccess()) {
	            return null;
	            //            throw new BusinessException("处理正品退货记录[" + orderNo + "]失败：" + result.getMessage());
	        }
	        GoodsBackInfoResponse goodsBackInfoResponse = result.getResult();
	        if (goodsBackInfoResponse == null) {
	            return null;
	        }
	        //返回so|ou号
	        return goodsBackInfoResponse.getSiOuSlipNo();
	    }
	    
	    private OrderRepairsNew getOrderRepairs(OrderProductsNew op) {
	        ServiceResult<OrderRepairsNew> result = orderService.getValidByOrderProductId(op.getId());
	        if (!result.getSuccess())
	            throw new RuntimeException("orderService返回错误:" + result.getMessage());
	        if (result.getResult() == null) {
	            ServiceResult<List<OrderRepairsNew>> rs = orderService.getOrderRepairsByOrderProductId(op
	                .getId());
	            if (rs==null || !rs.getSuccess())
	                throw new RuntimeException("orderService-getOrderRepairsByOrderProductId 错误:"
	                                           + rs.getMessage());
	            if (rs.getResult()!=null && rs.getResult().size() > 0)
	                return rs.getResult().get(0);
	        }
	        return result.getResult();
	    }
	    /**
	     * webService 返回bean
	     */
	    public class Result {
	    	public int    status = EisInterfaceFinance.STATUS_ERROR;
	      public   String eiaType;
	      public  String message;
	      public String system;
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getEiaType() {
			return eiaType;
		}
		public void setEiaType(String eiaType) {
			this.eiaType = eiaType;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getSystem() {
			return system;
		}
		public void setSystem(String system) {
			this.system = system;
		}
	      
	    }
}
