package com.haier.afterSale.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.afterSale.service.InvoiceService;
import com.haier.afterSale.service.OrderService;
import com.haier.afterSale.service.StockCommonService;
import com.haier.afterSale.util.HelpUtils;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.BusinessException;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.EisInterfaceFinance;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.service.EisInterfaceDataLogService;
import com.haier.eis.service.EisInterfaceFinanceService;
import com.haier.eis.service.LesStockTransQueueService;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrdersNew;
import com.haier.shop.service.InvoicesService;
import com.haier.stock.model.InvChannel2OrderSource;
import com.haier.stock.model.InvStockChannel;
@Service
public class SFHandler {
    private final static Logger               logger        = LogManager.getLogger(SFHandler.class);
    @Autowired
	protected InvoiceService                  invoiceService;
    @Autowired
	protected EisInterfaceFinanceService         eisInterfaceFinanceDao;
    @Autowired
	 protected StockCommonService              stockCommonService;
    @Autowired
	 protected LesStockTransQueueService           lesStockTransQueueService;
    @Autowired
	 protected InvoicesService                     invoicesDao;
    @Autowired
	 protected OrderService                    orderService;
    @Autowired
	 protected EisInterfaceDataLogService          eisInterfaceDataLogDao;
    @Autowired
	  protected HelpUtils                       help;
	  protected String                          interfaceCode = "";
	  private Integer                           eaiDataLogId;
	 protected OrdersNew getOrderByWD(OrderProductsNew orderProducts) {
	        if (orderProducts == null)
	            return null;
	        ServiceResult<OrdersNew> rs2 = orderService.getOrderById(orderProducts.getOrderId());
	        if (!rs2.getSuccess())
	            throw new BusinessException("向订单服务请求订单信息出现错误:" + rs2.getMessage());
	        return rs2.getResult();
	    }
	    protected OrderProductsNew getOrderProducts(String cOrderSn) {
	        ServiceResult<OrderProductsNew> rs = orderService.getOrderProductByCOrderSn(cOrderSn);
	        if (!rs.getSuccess()) {
	            throw new RuntimeException("向订单服务请求网单信息出现错误：" + rs.getMessage());
	        }
	        return rs.getResult();
	    }
	    public LesStockTransQueueService getLesStockTransQueueDao() {
	        return lesStockTransQueueService;
	    }

	    /**
	     * 是否处理过
	     *
	     * @param transQueue
	     * @return
	     */
	    private boolean isHasProcess(LesStockTransQueue transQueue) {
	        return getInterfaceFinance(transQueue.getId()) != null;
	    }
	    /**
	     * 获取记录
	     *
	     * @return
	     */
	    protected EisInterfaceFinance getInterfaceFinance(int transQueueId) {
	        return eisInterfaceFinanceDao.getByTransQueueId(transQueueId);
	    }
	    
	    /**
	     *  计算开票单价
	     *
	     * @param order
	     * @param orderProduct
	     */
	    public BigDecimal computeInvoicePrice(OrdersNew order, OrderProductsNew orderProduct) {

	        if (orderProduct == null || orderProduct.getCOrderSn() == null
	            || orderProduct.getCOrderSn().equals("")) {
	            throw new BusinessException("获取开票单价：网单为空，或网单号为空");
	        }
	        try {
	            //获得开票含税总金额
	            ServiceResult<BigDecimal> result = invoiceService.getInvoiceAmount(orderProduct
	                .getCOrderSn());
	            if (result == null || !result.getSuccess()) {
	                String message = result != null ? result.getMessage() : "返回结果为null";
	                throw new BusinessException("获取开票单价：调用发票价格接口时出错！错误信息：" + message);
	            } else {
	                //含税总金额
	                BigDecimal amount = result.getResult();
	                if (amount == null) {
	                    throw new BusinessException("获取开票单价：调用发票价格接口返回金额为null");
	                }
	                if (orderProduct.getNumber() != null && orderProduct.getNumber().compareTo(0) > 0) {
	                    //含税单价          =含税总金额除以网单数量
	                    BigDecimal price = amount.divide(new BigDecimal(orderProduct.getNumber()), 2,
	                        RoundingMode.HALF_UP);
	                    return price;
	                } else {
	                    throw new BusinessException("获取开票单价：网单数量为0或为null");
	                }
	            }
	        } catch (Exception e) {
	            logger.error("获取开票单价异常," + e.getMessage() + "\n错误信息:", e);
	            throw new BusinessException(e.getMessage());
	        }
	    }
	    protected String getCustormCode(OrderProductsNew orderProducts, OrdersNew order, BigDecimal price) {
	        return help.getCustormCode(orderProducts, order, price);
	    }
	    
	    protected String getChannel(String cOrderSn, String source) {
	        if (cOrderSn.endsWith("J")) {//京东
	            return "DS03";
	        }
	        if ("HPB2B".equalsIgnoreCase(source)) {//海朋归属大客户渠道
	            return "DS03";
	        }
	        if ("TOPXB".equalsIgnoreCase(source) || "TOPFENXIAOXB".equalsIgnoreCase(source)
	            || "TOPDHSC".equalsIgnoreCase(source)) {//新宝归属淘宝渠道
	            return "DS02";
	        }
	        if ("RRS".equalsIgnoreCase(source) || "DXS".equalsIgnoreCase(source)) {//日日顺订单
	            return "DS05";
	        }
	        if ("TONGSHUAI".equals(source) || "TONGSHUAIFX".equals(source)) {//统帅
	            return "DS06";
	        }
	        if ("YIHAODIAN".equalsIgnoreCase(source) || "YHD".equalsIgnoreCase(source)) {//一号店订单
	            return "DS11";
	        }
	        if ("CCBSC".equals(source) || "CCBSR".equals(source) || "95533".equals(source)) {//建行订单
	            return "DS12";
	        }
	        if ("GMZX".equals(source)) {//国美在线订单
	            return "DS10";
	        }
	        if ("YMX".equals(source)) {//亚马逊
	            return "DS09";
	        }
	        if ("YHDZY".equals(source)) {//电商平台-1号店自营
	            return "DS15";
	        }
	        if ("ICBC".equals(source)) {//平台大客户-工商银行
	            return "DS13";
	        }

	        if ("SNYG".equals(source)) {//电商平台-苏宁易购旗舰店
	            return "DS16";
	        }
	        if ("DALOU".equalsIgnoreCase(source)) {//企业订单_大楼订单,2016-08-30新增
	            return "DS01";
	        }
	        //新加的sap渠道码走下面的流程    ---wyj-2015-04-10
	        try {
	            ServiceResult<InvChannel2OrderSource> result = stockCommonService
	                .getSapChannelCodeAndCustomerCode(source);
	            if (result == null || !result.getSuccess()) {
	                String message = result != null ? result.getMessage() : "result返回结果为null";
	                throw new BusinessException("获取sap渠道码：调用sap渠道码接口时出错！错误信息：" + message);
	            } else {
	                InvChannel2OrderSource invc2os = result.getResult();
	                if (invc2os != null && invc2os.getSapChannelCode() != null
	                    && !invc2os.getSapChannelCode().equals("")) {
	                    return invc2os.getSapChannelCode();
	                }
	            }
	        } catch (Exception e) {
	            logger.error("获取sap渠道码异常," + e.getMessage() + "\n错误信息:", e);
	            throw new BusinessException(e.getMessage());
	        }

	        String channel = getInvStockChannelCodeBySource(source);
	        if (StringUtil.isEmpty(channel)) {
	            throw new BusinessException("订单来源对应的渠道不存在：" + source);
	        }
	        if (InvStockChannel.CHANNEL_DAKEHU.equals(channel)) {
	            return "DS03";
	        }
	        if (InvStockChannel.CHANNEL_SHANGCHENG.equals(channel)) {
	            return "DS01";
	        }
	        if (InvStockChannel.CHANNEL_TAOBAO.equals(channel)) {
	            return "DS02";
	        }
	        if (InvStockChannel.JD.equals(channel)) {
	            return "DS04";
	        }
	        if ("MK".equalsIgnoreCase(channel)) {
	            return "DS07";
	        }
	        return "";
	    }
	    private String getInvStockChannelCodeBySource(String source) {
	        ServiceResult<String> rs3 = stockCommonService.getChannelCodeByOrderSource(source);
	        if (!rs3.getSuccess())
	            throw new RuntimeException("通过订单来源向库存服务请求渠道编码发生错误:" + rs3.getMessage());
	        return rs3.getResult();
	    }
	    
	    protected void recordEisInterfaceDataLog(EisInterfaceDataLog log) {
	        try {
	            if (StringUtil.isEmpty(log.getInterfaceCode()))
	                log.setInterfaceCode(interfaceCode);
	            eisInterfaceDataLogDao.insert(log);
	            eaiDataLogId = log.getId();
	        } catch (Exception e) {
	            logger.error("记录EisInterfaceDataLog出错：", e);
	            logger.error("EisInterfaceDataLog:" + JsonUtil.toJson(log));
	            eaiDataLogId = 0;
	        }
	    }
}
