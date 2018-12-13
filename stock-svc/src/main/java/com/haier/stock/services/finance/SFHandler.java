package com.haier.stock.services.finance;


import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.EisInterfaceFinance;
import com.haier.eis.model.EisInterfaceFinanceQueryInfo;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.service.EisInterfaceDataLogService;
import com.haier.eis.service.EisInterfaceFinanceQueryInfoService;
import com.haier.eis.service.EisInterfaceFinanceService;
import com.haier.eis.service.LesStockTransQueueService;
import com.haier.shop.model.ChangeOrderConfig;
import com.haier.shop.model.OrderProductsAttributes;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrdersNew;
import com.haier.shop.service.ChangeOrderConfigService;
import com.haier.shop.service.CustomerCodesService;
import com.haier.shop.service.GroupOrdersService;
import com.haier.shop.service.InvoicesService;
import com.haier.shop.service.MemberInvoicesService;
import com.haier.shop.service.ProductActivitiesService;
import com.haier.shop.service.ShopTaoBaoGroupsService;
import com.haier.stock.model.InvChannel2OrderSource;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.ItemServiceImpl;
import com.haier.stock.service.OrderService;
import com.haier.stock.service.StockCommonService;
import com.haier.stock.services.StockCenterInvoiceNewServiceImpl;
import com.haier.stock.services.StockServiceImpl;
import com.haier.stock.util.HelpUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * 库存财务接口处理抽象类
 *
 * @Filename: SFHandler.java
 * @Version: 1.0
 * @Author: yanzhao
 * @Email: yan01250428@126.com
 */
public abstract class SFHandler {

  private final static Logger logger = LogManager.getLogger(SFHandler.class);
  public final static String SYSNAME = "EHAIER";
  @Value("${wsdlPath}")
  private String wsdlPath;
  protected String interfaceCode = "";
  protected String wsdlFile;
  protected String wsdlFileForQuery;

  protected SFHandler nextHandler;

  @Autowired
  protected EisInterfaceFinanceService eisInterfaceFinanceDao;
  @Autowired
  protected LesStockTransQueueService lesStockTransQueueDao;
  @Autowired
  protected EisInterfaceDataLogService eisInterfaceDataLogDao;
  @Autowired
  protected EisInterfaceFinanceQueryInfoService eisInterfaceFinanceQueryInfoDao;
  @Autowired
  protected CustomerCodesService customerCodesDao;
  @Autowired
  protected MemberInvoicesService memberInvoicesDao;
  @Autowired
  protected InvoicesService invoicesDao;
  @Autowired
  protected ShopTaoBaoGroupsService taoBaoGroupsDao;
  @Autowired
  protected GroupOrdersService groupOrdersDao;
  @Autowired
  protected ProductActivitiesService productActivitiesDao;
  @Autowired
  protected ChangeOrderConfigService changeOrderConfigDao;
  @Autowired
  protected OrderService orderService;
  @Autowired
  protected StockServiceImpl stockService;
  @Autowired
  protected StockCommonService stockCommonService;
  @Autowired
  protected ItemServiceImpl itemService;
  @Autowired
  protected StockCenterInvoiceNewServiceImpl invoiceService;
  @Autowired
  protected HelpUtils help;
  private Integer eaiDataLogId;


  protected static HashSet<String> cOrderSns = new HashSet<String>();

  public static void clear() {
    cOrderSns.clear();
  }

  /**
   * 处理请求
   */
  public void handleRequest(LesStockTransQueue stockTransQueue) {
    eaiDataLogId = 0;

    try {

      if (stockTransQueue.getStatus().equals(LesStockTransQueue.STATUS_ERROR)
          || stockTransQueue.getStatus().equals(LesStockTransQueue.STATUS_UNIDENTIFIED)) {
        Result rs = new Result();
        rs.status = EisInterfaceFinance.STATUS_ERROR;
        rs.message = stockTransQueue.getErrorMessage();
        recordInterfaceFinance(stockTransQueue, rs);
        return;
      }

      if (isJob(stockTransQueue)) {

        //处理过则不再处理
        if (isHasProcess(stockTransQueue)) {
          return;
        }
        if (!stockTransQueue.getStatus().equals(LesStockTransQueue.STATUS_DONE)) {
          Result rs = new Result();
          rs.status = EisInterfaceFinance.STATUS_FAILED;
          rs.message = "等待CBS处理完毕";
          recordInterfaceFinance(stockTransQueue, rs);
          return;
        }

        Result rs = process(stockTransQueue);

        if (rs.status == EisInterfaceFinance.STATUS_WARN) {
          logger.warn("[handleRequest->LesStockTransQueue][" + rs.message + "]");
        } else {
          recordInterfaceFinance(stockTransQueue, rs);
        }
      } else {
        //当前处理者无法处理，则调用下一处理者处理
        nextHandler.handleRequest(stockTransQueue);
      }
    } catch (BusinessException e) {
      Result rs = new Result();
      rs.message = e.getMessage();
      rs.status = EisInterfaceFinance.STATUS_ERROR;
      recordInterfaceFinance("UNKNOWN", stockTransQueue, rs);
      logger.info("调用SAP财务接口时出现错误：" + e.getMessage());
    }
  }

  /**
   * 处理请求：失败和未知（状态查询）情况处理
   */
  public void handleRequest(EisInterfaceFinance interfaceFinance) {
    eaiDataLogId = 0;

    if (interfaceFinance == null) {
      return;
    }
    if (!isJob(interfaceFinance)) {
      nextHandler.handleRequest(interfaceFinance);
    } else {
      LesStockTransQueue stockTransQueue = getTransQueue(interfaceFinance
          .getLesStockTransQueueId());

      if (stockTransQueue == null) {
        //将不存的记录更新状态
        interfaceFinance.setStatus(3);
        interfaceFinance.setUpdateTime(new Date());
        eisInterfaceFinanceDao.update(interfaceFinance);
        logger.error("未获取到账务接口关联的出入库记录，les_stock_tran_queue_id:"
            + interfaceFinance.getLesStockTransQueueId());
        return;
      } else if (stockTransQueue.getStatus().equals(LesStockTransQueue.STATUS_ERROR)
          || stockTransQueue.getStatus()
          .equals(LesStockTransQueue.STATUS_UNIDENTIFIED)) {
        Result rs = new Result();
        rs.status = EisInterfaceFinance.STATUS_ERROR;
        rs.message = stockTransQueue.getErrorMessage();
        updateInterfaceFinance(interfaceFinance, rs);
        return;
      } else if (!stockTransQueue.getStatus().equals(LesStockTransQueue.STATUS_DONE)) {
        return;
      }
      Result result;
      switch (interfaceFinance.getStatus()) {
        case EisInterfaceFinance.STATUS_FAILED: {
          //失败的则重新处理
          result = process(stockTransQueue);
          break;
        }
        case EisInterfaceFinance.STATUS_UNKNOWN: {
          //状态未知的调用状态接口更新状态
          result = getInterfaceStatus(stockTransQueue);
          recordInterfaceFinanceQueryInfo(stockTransQueue, result,
              interfaceFinance.getId());
          break;
        }
        default:
          throw new BusinessException("无法处理的请求，只能处理失败和未知的数据");
      }
      updateInterfaceFinance(interfaceFinance, result);
    }
  }

  /**
   * 是否可以处理
   */
  protected abstract boolean isJob(LesStockTransQueue transQueue);

  /**
   * 是否可以处理
   */
  protected boolean isJob(EisInterfaceFinance interfaceFinance) {
    return interfaceCode.equals(interfaceFinance.getInterfaceCode());
  }

  /**
   * 处理请求
   */
  protected abstract Result process(LesStockTransQueue transQueue);

  /**
   * 获取接口状态
   */
  protected abstract Result getInterfaceStatus(LesStockTransQueue transQueue);

  /**
   * 是否处理过
   */
  private boolean isHasProcess(LesStockTransQueue transQueue) {
    return getInterfaceFinance(transQueue.getId()) != null;
  }

  /**
   * 获取记录
   */
  protected EisInterfaceFinance getInterfaceFinance(int transQueueId) {
    return eisInterfaceFinanceDao.getByTransQueueId(transQueueId);
  }

  private LesStockTransQueue getTransQueue(Integer id) {
    return lesStockTransQueueDao.getById(id);
  }

  private void recordInterfaceFinanceQueryInfo(LesStockTransQueue transQueue, Result rs,
      int financeId) {
    EisInterfaceFinanceQueryInfo financeQueryInfo = new EisInterfaceFinanceQueryInfo();
    financeQueryInfo.setAddTime(DateUtil.currentDateTime());
    financeQueryInfo.setCordersn(transQueue.getCorderSn());
    financeQueryInfo.setInterfaceCode(interfaceCode);
    financeQueryInfo.setEaiType(rs.eiaType);
    financeQueryInfo.setMark(rs.mark);
    financeQueryInfo.setEaiMessage(rs.message);
    financeQueryInfo.setFinanceId(financeId);
    financeQueryInfo.setSystem(rs.system);
    eisInterfaceFinanceQueryInfoDao.insert(financeQueryInfo);
  }

  private void recordInterfaceFinance(LesStockTransQueue transQueue, Result rs) {
    recordInterfaceFinance(interfaceCode, transQueue, rs);
  }

  private void recordInterfaceFinance(String interfaceCode, LesStockTransQueue transQueue,
      Result rs) {
    try {
      EisInterfaceFinance interfaceFinance = new EisInterfaceFinance();
      interfaceFinance.setLesStockTransQueueId(transQueue.getId());
      interfaceFinance.setInterfaceCode(interfaceCode);
      interfaceFinance.setStatus(rs.status);
      interfaceFinance.setEaiType(rs.eiaType);
      interfaceFinance.setMessage(rs.message);
      Date now = DateUtil.currentDateTime();
      interfaceFinance.setAddTime(now);
      interfaceFinance.setUpdateTime(now);
      interfaceFinance.setEaiDataLogId(eaiDataLogId);
      eisInterfaceFinanceDao.insert(interfaceFinance);
    } catch (Exception e) {
      logger.error("[To Sap]写EisInterfaceFinance发生异常[" + transQueue.getId() + "]["
          + e.getMessage() + "]");
    }

  }

  /**
   * 更新接口状态
   */
  protected void updateInterfaceFinance(EisInterfaceFinance interfaceFinance, Result result) {
    if (EisInterfaceFinance.STATUS_UNKNOWN != interfaceFinance.getStatus()) {
      interfaceFinance.setEaiDataLogId(eaiDataLogId);
    }
    if (result.status == EisInterfaceFinance.STATUS_WARN) {
      logger.warn("[handleRequest->updateInterfaceFinance][" + result.message + "]");
      return;
    }
    interfaceFinance.setStatus(result.status);
    interfaceFinance.setEaiType(result.eiaType);
    interfaceFinance.setMessage(result.message);
    interfaceFinance.setUpdateTime(new Date());
    eisInterfaceFinanceDao.update(interfaceFinance);
  }

  protected OrdersNew getOrderByWD(OrderProductsNew orderProducts) {
    if (orderProducts == null) {
      return null;
    }
    ServiceResult<OrdersNew> rs2 = orderService.getOrderById(orderProducts.getOrderId());
    if (!rs2.getSuccess()) {
      throw new BusinessException("向订单服务请求订单信息出现错误:" + rs2.getMessage());
    }
    return rs2.getResult();
  }

  protected OrderProductsNew getOrderProducts(String cOrderSn) {
    ServiceResult<OrderProductsNew> rs = orderService.getOrderProductByCOrderSn(cOrderSn);
    if (!rs.getSuccess()) {
      throw new RuntimeException("向订单服务请求网单信息出现错误：" + rs.getMessage());
    }
    return rs.getResult();
  }

  protected OrderProductsAttributes getOrderProductsAttributes(Integer orderProductId) {
    ServiceResult<OrderProductsAttributes> rs = orderService
        .getByOrderProductId(orderProductId);
    if (!rs.getSuccess()) {
      throw new RuntimeException("向订单服务请求网单扩展属性信息出现错误：" + rs.getMessage());
    }
    return rs.getResult();
  }

  protected ChangeOrderConfig getChangeOrderConfig(int customerId) {
    return changeOrderConfigDao.get(customerId);
  }

  protected void recordEisInterfaceDataLog(EisInterfaceDataLog log) {
    try {
      if (StringUtil.isEmpty(log.getInterfaceCode())) {
        log.setInterfaceCode(interfaceCode);
      }
      eisInterfaceDataLogDao.insertAndReturnId(log);
      eaiDataLogId = log.getId();
    } catch (Exception e) {
      logger.error("记录EisInterfaceDataLog出错：", e);
      logger.error("EisInterfaceDataLog:" + JsonUtil.toJson(log));
      eaiDataLogId = 0;
    }
  }

  private URL getWSDLURL(String file) {
    try {
//              String path = "file:" + this.getClass().getResource("/wsdl/" + file).getPath();
//              return new URL(path);
      URL url = this.getClass().getResource(
          wsdlPath+ "/" +file);
      return url;
//      return help.getWSDLURL(file);
    } catch (Exception e) {
      logger.error("WSDL文件路径配置错误或WSDL文件不存在：" + wsdlFile);
      throw new BusinessException("解析WSDL文件失败，配置错误");
    }
  }

  protected URL getWSDLURL() {
    return getWSDLURL(wsdlFile);
  }

  protected URL getWSDLURLForQuery() {
    return getWSDLURL(wsdlFileForQuery);
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
        if (invc2os != null && !StringUtils.isBlank(invc2os.getSapChannelCode())) {
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

  protected String getCustormCode(OrderProductsNew orderProducts, OrdersNew order,
      BigDecimal price) {
    return help.getCustormCode(orderProducts, order, price);
  }

  //    protected String getCustormCode(OrderProducts orderProducts, Orders order, BigDecimal price) {
  //        //9月1号及以后新发生的订单，其推送出库、红冲及二次开票按照新规则执行：
  //        //OTO线上订单传送SAP出库、红冲及二次开票，其客户码推送至A0T，其优先级高于订单来源；后台录单和专项开票，推送SAP时，客户也推送A0T。
  //        if (orderProducts != null && orderProducts.getO2oType().equals(2)) {
  //            if (order.getAddTime().intValue() >= 1441036800) {
  //                if (new BigDecimal("0.01").compareTo(price) == 0) {
  //                    return "A89";
  //                }
  //                return "A0T";
  //            }
  //        }
  //
  //        //2014年3月12日1号店业务现将“上海传绩电子商务有限公司”所有的业务 切换至“纽海电子商务（上海）有限公司
  //
  //        String source = order.getSource();
  //
  //        if ("YIHAODIAN".equalsIgnoreCase(source) || "YHD".equalsIgnoreCase(source)) {//一号店订单
  //            return "8800046354";
  //        }
  //
  //        if ("YHDZY".equals(source)) {//电商平台-1号店自营
  //            return "8800049539";
  //        }
  //
  //        if ("TONGSHUAI".equals(source) || "TONGSHUAIFX".equals(source)) {//统帅订单
  //            return "A92";
  //        }
  //
  //        if ("GMZX".equals(source)) {//国美在线订单
  //            return "8800042421";
  //        }
  //
  //        if ("CCBSC".equals(source) || "CCBSR".equals(source) || "95533".equals(source)) {//建行订单
  //            return "8800040597";
  //        }
  //
  //        if ("YMX".equals(source)) {//亚马逊
  //            return "8800149760";
  //        }
  //
  //        if ("ICBC".equals(source)) {//平台大客户-工商银行
  //            return "8800049167";
  //        }
  //
  //        if ("SNYG".equals(source)) {//电商平台-苏宁易购旗舰店
  //            return "8800049541";
  //        }
  //
  //        //HP不良品换货的客户码获取方式，根据库位获得工贸代码作为客户码    ---wyj-2015-05-14
  //        if ("BLPHH".equals(source)) {//电商平台-HP不良品换货
  //            try {
  //                ServiceResult<InvSection> result = stockCommonService
  //                    .getSectionByCode(orderProducts.getSCode());
  //                if (result == null || !result.getSuccess()) {
  //                    String message = result != null ? result.getMessage()
  //                        : "获取section对象时，result返回结果为null";
  //                    throw new BusinessException("获取sap客户码：调用section服务接口时出错！错误信息：" + message);
  //                } else {
  //                    InvSection invsec = result.getResult();
  //                    if (invsec != null && invsec.getWhCode() != null
  //                        && !invsec.getWhCode().equals("")) {
  //                        try {
  //                            ServiceResult<InvWarehouse> result_wh = stockCommonService
  //                                .getWarehouse(invsec.getWhCode());
  //                            if (result_wh == null || !result_wh.getSuccess()) {
  //                                String message = result_wh != null ? result_wh.getMessage()
  //                                    : "获取section对象时，result返回结果为null";
  //                                throw new BusinessException("获取sap客户码：获取section对象接口时出错！错误信息："
  //                                                            + message);
  //                            } else {
  //                                InvWarehouse iwh = result_wh.getResult();
  //                                if (iwh != null && iwh.getSapCustomerCode() != null
  //                                    && !iwh.getSapCustomerCode().equals("")) {
  //                                    return iwh.getSapCustomerCode();
  //                                } else {
  //                                    String message = result_wh != null && !result_wh.getSuccess() ? result_wh
  //                                        .getMessage()
  //                                        : "获取InvWarehouse对象时，客户码（getSapCustomerCode）为空";
  //                                    throw new BusinessException(
  //                                        "获取sap客户码：获取InvWarehouse对象时接口时出错！错误信息：" + message);
  //                                }
  //                            }
  //                        } catch (Exception e) {
  //                            logger.error("获取sap客户码异常," + e.getMessage() + "\n错误信息:", e);
  //                            throw new BusinessException(e.getMessage());
  //                        }
  //                    } else {
  //                        String message = result != null && !result.getSuccess() ? result
  //                            .getMessage() : "获取section对象时，库位（sCode）为空";
  //                        throw new BusinessException("获取sap客户码：获取section对象时出错！错误信息：" + message);
  //                    }
  //                }
  //            } catch (Exception e) {
  //                logger.error("获取sap客户码异常," + e.getMessage() + "\n错误信息:", e);
  //                throw new BusinessException(e.getMessage());
  //            }
  //        }
  //
  //        MemberInvoices memberInvoices = memberInvoicesDao.getByOrderId(order.getId());
  //        if ("SCFX".equals(source) && memberInvoices != null
  //            && "青岛信创信息科技有限公司".equals(memberInvoices.getInvoiceTitle())) {//商城订单-分销渠道,且发票抬头为：青岛信创信息科技有限公司
  //            return "8800048176";
  //        }
  //        //新加的客户码通过这个服务获取，不在手动修改代码了    ---wyj-2015-04-10
  //        try {
  //            ServiceResult<InvChannel2OrderSource> result = stockCommonService
  //                .getSapChannelCodeAndCustomerCode(order.getSource());
  //            if (result == null || !result.getSuccess()) {
  //                String message = result != null ? result.getMessage() : "result返回结果为null";
  //                throw new BusinessException("获取sap客户码：调用sap客户码接口时出错！错误信息：" + message);
  //            } else {
  //                InvChannel2OrderSource invc2os = result.getResult();
  //                if (invc2os != null && invc2os.getCustomerCode() != null
  //                    && !invc2os.getCustomerCode().equals("")) {
  //                    return invc2os.getCustomerCode();
  //                }
  //            }
  //        } catch (Exception e) {
  //            logger.error("获取sap客户码异常," + e.getMessage() + "\n错误信息:", e);
  //            throw new BusinessException(e.getMessage());
  //        }
  //
  //        String paymentCode = order.getPaymentCode();
  //
  //        if (("RRS".equals(source))
  //            || ("DXS".equalsIgnoreCase(source))
  //            || (order.getIsBackend().compareTo(1) == 0 && ("prepaid".equalsIgnoreCase(paymentCode) || "insidestatement"
  //                .equalsIgnoreCase(paymentCode)))) {
  //            if (memberInvoices == null) {
  //                logger.info("订单（" + order.getId() + ")没开发票，或发票信息不存在");
  //                return null;
  //            }
  //            if ("北京京东世纪信息技术有限公司".equals(memberInvoices.getInvoiceTitle()))
  //                return "C200065928";
  //
  //            String title = memberInvoices.getInvoiceTitle();
  //            if (title == null)
  //                title = "";
  //            title = title.replaceAll("\\（", "(");
  //            title = title.replaceAll("\\）", ")");
  //
  //            CustomerCodes customerCodes = customerCodesDao.getByTitle(title);
  //
  //            if (customerCodes == null) {
  //                logger.info("CustomerCode 不存在，title:" + memberInvoices.getInvoiceTitle());
  //                return "A69";
  //            }
  //            return customerCodes.getCode();
  //        } else {
  //
  //            if (!"RRS".equals(source) && new BigDecimal("0.01").compareTo(price) == 0)
  //                return "A89";
  //            else
  //                return "A69";
  //        }
  //    }

  private String getInvStockChannelCodeBySource(String source) {
    ServiceResult<String> rs3 = stockCommonService.getChannelCodeByOrderSource(source);
    if (!rs3.getSuccess()) {
      throw new RuntimeException("通过订单来源向库存服务请求渠道编码发生错误:" + rs3.getMessage());
    }
    return rs3.getResult();
  }


  /**
   *  计算开票单价
   */
  public BigDecimal computeInvoicePrice(OrdersNew order, OrderProductsNew orderProduct) {

    if (orderProduct == null || StringUtils.isBlank(orderProduct.getCOrderSn())) {
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

  public class Result {

    int status = EisInterfaceFinance.STATUS_ERROR;
    String eiaType;
    String mark;
    String message;
    String system;

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

    public String getMark() {
      return mark;
    }

    public void setMark(String mark) {
      this.mark = mark;
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
