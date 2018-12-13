package com.haier.stock.services.Helper;

import com.haier.common.util.ConvertUtil;
import com.haier.purchase.data.model.PurchaseItem;
import com.haier.purchase.data.model.PurchaseOrder;
import com.haier.purchase.data.model.PurchaseOrderQueue;
import com.haier.purchase.data.model.PurchaseOrderQueueStatus;
import com.haier.purchase.data.model.PurchaseOrderQueueWrapper;
import com.haier.purchase.data.service.PurchaseOrderService;
import com.haier.stock.eai.getTidan.GetTidanZWDFromLESToEHAIER;
import com.haier.stock.eai.getTidan.GetTidanZWDFromLESToEHAIER_Service;
import com.haier.stock.eai.getTidan.GetTidanZWDFromLESToEHAIER_Type;
import com.haier.stock.eai.getTidan.ZWDTABLE2;
import com.haier.stock.eai.getbominfofromlestoehaier.GetBomInfoFromLESToEHAIER;
import com.haier.stock.eai.getbominfofromlestoehaier.GetBomInfoFromLESToEHAIER_Service;
import com.haier.stock.eai.getbominfofromlestoehaier.ZLESDCMS24;
import com.haier.stock.eai.getinvrebateinfofromihs.GetInvRebateInfoFromIHS;
import com.haier.stock.eai.getinvrebateinfofromihs.GetInvRebateInfoFromIHS_Service;
import com.haier.stock.eai.queryProdDateFromEDW.OutType;
import com.haier.stock.eai.queryProdDateFromEDW.QueryProOrderAndSaleOrderFromEDW;
import com.haier.stock.eai.queryProdDateFromEDW.QueryProOrderAndSaleOrderFromEDW_Service;
import com.haier.stock.eai.queryProdDateFromEDW.SelectITFMAKEPLANXIAXIANVOutput;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.ws.Holder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.haier.common.BusinessException;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.eis.model.EisExternalSku;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.service.EisExternalSkuService;
import com.haier.eis.service.EisInterfaceDataLogService;
import com.haier.shop.model.LesStockSyncs;
import com.haier.shop.service.LesStockSyncsService;
import com.haier.stock.canceltidan.JdeAndLesLoseValidity;
import com.haier.stock.canceltidan.JdeAndLesLoseValidityResponse;
import com.haier.stock.canceltidan.JdeAndLesLoseValidity_Service;
import com.haier.stock.canceltidan.JdeAndLesLoseValidity_Type;
import com.haier.stock.createscordertoles.CreateSCOrderToLES;
import com.haier.stock.createscordertoles.CreateSCOrderToLES_Service;
import com.haier.stock.createscordertoles.InputType;
import com.haier.stock.createscordertoles.OutputType;
import com.haier.stock.eai.transaccountcheckingfromcbstoles.GetKUCUNInfoFromLESToEHAIER;
import com.haier.stock.eai.transaccountcheckingfromcbstoles.GetKUCUNInfoFromLESToEHAIERResponseStockQty;
import com.haier.stock.eai.transaccountcheckingfromcbstoles.GetKUCUNInfoFromLESToEHAIERResponseStockTrans;
import com.haier.stock.eai.transaccountcheckingfromcbstoles.GetKUCUNInfoFromLESToEHAIER_Service;
import com.haier.stock.exchangegoodstoles.ExchangeGoodsToLES;
import com.haier.stock.exchangegoodstoles.ExchangeGoodsToLESResponse.OUTPUT;
import com.haier.stock.exchangegoodstoles.ExchangeGoodsToLES_Service;
import com.haier.stock.exchangegoodstoles.ExchangeGoodsToLES_Type.INPUT;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.service.OrderService;
import com.haier.stock.services.OrderServiceImpl;
import com.haier.stock.util.HelpUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


@Service
public class EAIHandler {

  private Logger log = LogManager.getLogger(EAIHandler.class);

  @Autowired
  private EisInterfaceDataLogService EisInterfaceDataLogService;
  @Autowired
  private LesStockSyncsService LesStockSyncsService;
  @Autowired
  private EisInterfaceDataLogService interfaceDataLogService;
  @Autowired
  private EisExternalSkuService eisExternalSkuService;
  @Autowired
  private OrderService orderService;
  @Autowired
  private com.haier.stock.service.StockTransferLineService transferLineDao;
  @Resource
  private EisInterfaceDataLogService eisInterfaceDataLogService;
  @Autowired
  private HelpUtils help;
  @Value("${wsdlPath}")
  private String wsdllocation;

  @Resource
  private EisInterfaceDataLogService interfaceDataLogDao;

  public void setHelp(HelpUtils help) {
    this.help = help;
  }

  public OrderService getOrderService() {
    return orderService;
  }

  public void setOrderService(OrderServiceImpl orderService) {
    this.orderService = orderService;
  }

  /**
   * 往Les创建调提单
   */
  public OutputType createTransferLineToLes(InvTransferLine transferLine,
      String transferFrom,
      String transferTo) {
    //组装参数
    String source = "1";//订单来源码
    String sourceName = "商城调拨单";//来源名称
    String sourceSn = "";//来源订单编号
    String corderSn = transferLine.getLineNum();//网单编号
    Integer posex = 10;//采购订单行项目

    Date addDate = transferLine.getCreateTime();//订单创建时间
    String auart = "ZGI6";//订单类型
    String kunnr = transferFrom;//售达方
    String accepter = transferTo;//送达方
    String sku = transferLine.getItemCode();//sku
    sku = convertToExternalSku(sku, EisExternalSku.TYPE_R);
    String brandName = "";//品牌名称
    Long quantity = transferLine.getTransferQty().longValue();//数量
    String meins = "TAI";//基本单位
    Integer charg = 10;//批次
    BigDecimal unitPrice = new BigDecimal(0);//单价

    BigDecimal subtotal = new BigDecimal(0);//小计
    BigDecimal shipFee = transferLine.getTransferFee();//运费

    BigDecimal total = new BigDecimal(0);//总计
    String consignee = "";//订货人
    String sdabw = "2";//特殊处理标记
    String mobile = "";//订货人手机
    String phone = "";//订货人电话

    String provinceName = "";//省
    String cityName = "";//市
    String regionName = "";//区
    String address = "";//地址
    String payStatus = "";//付款状态
    String sCode = transferLine.getSecFrom();//库位

    String augru = "";//定单原因
    String bstkd_e = corderSn;//LES需要根据出库订单，自行生成入库订单 而BSTKD_E字段是标注前续单号用的
    String posnr_e = ""; //优先采购订单的项目数
    String zip = "";//邮编
    String payType = "";// 付款方式
    String remark = "";//客户备注
    String kunem = "";//最终客户
    String kunz1 = "";//产品基地代码
    String sdaem = "";//基地直发客户标记
    String urlab = "";//超期免单标识
    String urmemo = "";//加急配送标释
    String isNeedInvoice = ""; //发票信息
    String invoiceType = "";
    String invoiceAcc = "";
    String invoiceAddress = "";
    String invoiceBank = "";
    String taxSpotNum = "";

    Date reqDate = new Date();//调用les接口时间
    String add1 = "";
    String add2 = "";//是否货票同行标识
    String add3 = "";

    // 要记录接口数据日志
    EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
    dataLog.setForeignKey(corderSn);
    dataLog.setInterfaceCode(EisInterfaceDataLog.INTERFACE_CODE_GET_TRANSFER_LINE_TO_LES);
    dataLog.setRequestTime(reqDate);
    Long startTime = System.currentTimeMillis();

    //调用接口
    try {

      List<InputType> inputList = new ArrayList<InputType>();
      InputType input = new InputType();
      //1.商城订单来源代码 - 30
      if (source.length() > 30) {
        source = source.substring(0, 30);
      }
      input.setSOURCE(source);

      //2.商城订单来源文字描述 - 30
      if (sourceName.length() > 30) {
        sourceName = sourceName.substring(0, 30);
      }
      input.setSOURCEEXT(sourceName);

      //3.商城来源订单号 - 30
      if (sourceSn.length() > 30) {
        sourceSn = sourceSn.substring(0, 30);
      }
      input.setSOURCESN(sourceSn);

      //4.商城网单编号 - 35
      if (corderSn.length() > 35) {
        corderSn = corderSn.substring(0, 35);
      }
      input.setBSTKD(corderSn);

      //5.商城订单行项目 - 6
      input.setPOSEX(posex.toString());

      //订单创建日期和时间
      Calendar cl = Calendar.getInstance();
      cl.setTime(addDate);
      XMLGregorianCalendar xgcDate = DatatypeFactory.newInstance()
          .newXMLGregorianCalendarDate(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH) + 1,
              cl.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
      XMLGregorianCalendar xgcTime = DatatypeFactory.newInstance()
          .newXMLGregorianCalendarTime(cl.get(Calendar.HOUR_OF_DAY), cl.get(Calendar.MINUTE),
              cl.get(Calendar.SECOND), DatatypeConstants.FIELD_UNDEFINED);
      //6.订单创建日期
      input.setAUDAT(xgcDate);

      //7.订单创建时间
      input.setAUTIM(xgcTime);

      //8.单据类型 - 4
      if (auart.length() > 4) {
        auart = auart.substring(0, 4);
      }
      input.setAUART(auart);

      //9.售达方 - 10
      if (kunnr.length() > 10) {
        kunnr = kunnr.substring(0, 10);
      }
      input.setKUNNR(kunnr);

      //10.送达方 - 10
      if (accepter.length() > 10) {
        accepter = accepter.substring(0, 10);
      }
      input.setKUNWE(accepter);

      //11.物料号 - 18
      if (sku.length() > 18) {
        sku = sku.substring(0, 18);
      }
      input.setMATNR(sku);

      //12.商品品牌 - 20，可为空
      if (brandName.length() > 20) {
        brandName = brandName.substring(0, 20);
      }
      input.setCOMTYP(brandName);

      //13.订单数量 -15
      input.setKWMENG(new BigDecimal(quantity));

      //14.基本单位 - 3, 非空， EA或TAI，商城不能空，
      if (meins.length() > 3) {
        meins = meins.substring(0, 3);
      }
      input.setMEINS(meins);

      //15.库存地点 - 4
      if (sCode.length() > 4) {
        sCode = sCode.substring(0, 4);
      }
      input.setLGORT(sCode);

      //16.批次 - 10, 非空（商城默认为 10）
      input.setCHARG(charg.toString());

      //17.特殊处理标记 - 4,(1：自提，2：配送,70:直发客户)
      input.setSDABW(sdabw);

      //18.定单原因 - 3,可为空
      if (augru.length() > 3) {
        augru = augru.substring(0, 3);
      }
      input.setAUGRU(augru);

      //19.运达方的采购订单编号 - 35
      if (bstkd_e.length() > 35) {
        bstkd_e = bstkd_e.substring(0, 35);
      }
      input.setBSTKDE(bstkd_e);

      //20.优先采购订单的项目数 - 6
      if (posnr_e.length() > 6) {
        posnr_e = posnr_e.substring(0, 6);
      }
      input.setPOSNRE(posnr_e);

      //21.单价 - 12
      input.setKBETR(unitPrice);

      //22.商品金额小计
      input.setKWERT(subtotal);

      //23.运费 - 12
      input.setSHIPCO(shipFee);

      //24.订单总金额
      input.setKWERZ(total);

      //25.订货人姓名 - 30
      if (consignee.length() > 30) {
        consignee = consignee.substring(0, 30);
      }
      input.setDHRXM(consignee);

      //26.订货人联系电话 - 30
      if (mobile.length() > 30) {
        mobile = mobile.substring(0, 30);
      }
      input.setDHRPH(mobile);

      //27.收货人移动电话 - 30
      input.setSHRMOB(mobile);

      //28.收货人姓名 - 30
      input.setSHRXM(consignee);

      //29.收货人固定电话 - 30 ，可为空
      if (phone.length() > 30) {
        phone = phone.substring(0, 30);
      }
      input.setSHRTEL(phone);

      //30.收货人所在省 - 30
      if (provinceName.length() > 30) {
        provinceName = provinceName.substring(0, 30);
      }
      input.setPROV(provinceName);

      //31.收货人所在市 - 30
      if (cityName.length() > 30) {
        cityName = cityName.substring(0, 30);
      }
      input.setCITY(cityName);

      //32.收货人所在县/区 - 30
      if (regionName.length() > 30) {
        regionName = regionName.substring(0, 30);
      }
      input.setCOUNTY(regionName);

      //33.收货地址 - 100
      if (address.length() > 100) {
        address = address.substring(0, 100);
      }
      input.setADDRESS(address);

      //34.邮编 - 6
      if (zip.length() > 6) {
        zip = zip.substring(0, 6);
      }
      input.setPSTLZ(zip);

      //35.付款状态 - 20
      if (payStatus.length() > 20) {
        payStatus = payStatus.substring(0, 20);
      }
      input.setPAYSTE(payStatus);

      //36.支付类别 - 20
      if (payType.length() > 20) {
        payType = payType.substring(0, 20);
      }
      input.setPAYTYP(payType);

      //37.预约发货时间
      //客户要求送货日期，可为空
      input.setYDDAT(null);

      //38.客户要求送货时间，可为空
      input.setYDTIME(null);

      //39客户备注 - 200，可为空
      if (remark.length() > 200) {
        remark = remark.substring(0, 200);
      }
      input.setSDMEMO(remark);

      //40.最终客户 - 10，可为空
      if (kunem.length() > 10) {
        kunem = kunem.substring(0, 10);
      }
      input.setKUNEM(kunem);

      //41.产品基地代码  - 10，可为空
      if (kunz1.length() > 10) {
        kunz1 = kunz1.substring(0, 10);
      }
      input.setKUNZ1(kunz1);

      //42.基地直发客户标记 - 10，可为空
      if (sdaem.length() > 10) {
        sdaem = sdaem.substring(0, 10);
      }
      input.setSDAEM(sdaem);

      //43.超期免单标识 - 1，可为空
      if (urlab.length() > 1) {
        urlab = urlab.substring(0, 1);
      }
      input.setURLAB(urlab);

      //44.加急配送标释 - 10，可为空
      if (urmemo.length() > 10) {
        urmemo = urmemo.substring(0, 10);
      }
      input.setURMEMO(urmemo);

      //45.是否需要发票 - 10，可为空
      if (isNeedInvoice.length() > 10) {
        isNeedInvoice = isNeedInvoice.substring(0, 10);
      }
      input.setINVO(isNeedInvoice);

      //46.发票类型 - 30，可为空
      if (invoiceType.length() > 30) {
        invoiceType = invoiceType.substring(0, 30);
      }
      input.setINVTYP(invoiceType);

      //47.发票抬头 - 100，可为空
      if (invoiceAcc.length() > 100) {
        invoiceAcc = invoiceAcc.substring(0, 100);
      }
      input.setINVACC(invoiceAcc);

      //48.发票地址 - 100，可为空
      if (invoiceAddress.length() > 100) {
        invoiceAddress = invoiceAddress.substring(0, 100);
      }
      input.setINVADD(invoiceAddress);

      //49.发票开户行 - 100，可为空
      if (invoiceBank.length() > 100) {
        invoiceBank = invoiceBank.substring(0, 100);
      }
      input.setINVBANK(invoiceBank);

      //50.纳税人登记号 - 100，可为空
      if (taxSpotNum.length() > 100) {
        taxSpotNum = taxSpotNum.substring(0, 100);
      }
      input.setINVNUM(taxSpotNum);

      //请求时间
      cl.setTime(reqDate);
      xgcDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
          cl.get(Calendar.YEAR), cl.get(Calendar.MONTH) + 1, cl.get(Calendar.DAY_OF_MONTH),
          DatatypeConstants.FIELD_UNDEFINED);
      xgcTime = DatatypeFactory.newInstance().newXMLGregorianCalendarTime(
          cl.get(Calendar.HOUR_OF_DAY), cl.get(Calendar.MINUTE), cl.get(Calendar.SECOND),
          DatatypeConstants.FIELD_UNDEFINED);
      //51.商城订单创建日期
      input.setERDAT(xgcDate);

      //52.商城订单创建时间
      input.setERZET(xgcTime);

      //53.备用字段1 - 20，可为空
      if (add1.length() > 20) {
        add1 = add1.substring(0, 20);
      }
      input.setADD1(add1);

      //54.是否货票同行 - 30，可为空
      if (add2.length() > 30) {
        add2 = add2.substring(0, 30);
      }
      input.setADD2(add2);

      //55.备用字段3 - 50，可为空
      if (add3.length() > 50) {
        add3 = add3.substring(0, 50);
      }
      input.setADD3(add3);

      inputList.add(input);

      //请求参数记录到日志中
      dataLog.setRequestData(new Gson().toJson(input));

      //调用les接口
      CreateSCOrderToLES soap = new CreateSCOrderToLES_Service(
          this.getClass().getResource(wsdllocation + "/CreateSCOrderToLES.wsdl"))
          .getCreateSCOrderToLESSOAP();

      List<OutputType> outPutList = soap.createSCOrderToLES(inputList);

      dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
      dataLog.setResponseTime(new Date());
      dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
      dataLog.setCreateTime(new Date());
      OutputType output = outPutList == null ? null
          : outPutList.get(0);
      dataLog.setResponseData(new Gson().toJson(output));
      try {
        interfaceDataLogService.insert(dataLog);
      } catch (Exception e) {
        log.error("向LES开调拨提单记录日志数据出错：", e);
      }

      return output;
    } catch (Exception e) {
      log.error("[transferLine_id" + transferLine.getLineId() + "]: les调用异常", e);
      dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
      dataLog.setResponseTime(new Date());
      dataLog.setResponseData("");
      dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
      dataLog.setCreateTime(new Date());
//            interfaceDataLogDao.insert(dataLog);
      throw new BusinessException("调用LES开提单接口出现未知错误：" + e);
    }
  }

  /**
   * 转换内部SKU为R码
   *
   * @param type 外部sku类型
   */
  public String convertToExternalSku(String sku, String type) {
    EisExternalSku by6 = eisExternalSkuService.getBySkuType(sku, EisExternalSku.TYPE_R);
    if (by6 == null) {
      return sku;
    }
    return by6.getExternalSku();
  }

  /**
   * 从LES获取交易数据和库存数据
   */
  public Integer getInventoryTranFromLes(String dateBegin, String dateEnd, String timeBegin,
      String timeEnd,
      String secType, Holder<String> flag, Holder<String> message,
      Holder<List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans>> responseStockTrans,
      Holder<List<GetKUCUNInfoFromLESToEHAIERResponseStockQty>> responseStockQtys) {
    LesStockSyncs lesStockSyncs = new LesStockSyncs();
    lesStockSyncs.setIsDone(1);
    String now = DateUtil.format(DateUtil.currentDateTime(), "yyyy-MM-dd HH:mm:ss");
    lesStockSyncs.setAddTime(now);
    lesStockSyncs.setDoneTime(now);
    lesStockSyncs.setIsRedone(1);
    // 要记录接口数据日志
    Map<String, Object> requestData = new HashMap<String, Object>();

    com.haier.eis.model.EisInterfaceDataLog dataLog = new com.haier.eis.model.EisInterfaceDataLog();
    dataLog.setInterfaceCode(
        com.haier.eis.model.EisInterfaceDataLog.INTERFACE_CODE_GET_STOCK_TRANS_FROM_LES);
    Long startTime = System.currentTimeMillis();

    try {
      String crk = "";

      requestData.put("CRK", crk);
      requestData.put("DATE_BEGIN", dateBegin);
      requestData.put("DATE_END", dateEnd);
      requestData.put("KUWEI", secType);
      requestData.put("TIME_BEGIN", timeBegin);
      requestData.put("TIME_END", timeEnd);

      dataLog.setRequestData(new Gson().toJson(requestData));
      dataLog.setRequestTime(new Date());

      // 调用接口
      // URL url = new
      // URL(this.getWsdlPath("GetKUCUNInfoFromLESToEHAIER.wsdl"));
      URL url = this.getClass().getResource(wsdllocation + "/GetKUCUNInfoFromLESToEHAIER.wsdl");
      GetKUCUNInfoFromLESToEHAIER_Service service = new GetKUCUNInfoFromLESToEHAIER_Service(url);
      GetKUCUNInfoFromLESToEHAIER soap = service.getGetKUCUNInfoFromLESToEHAIERSOAP();

      soap.getKUCUNInfoFromLESToEHAIER(crk, dateBegin, dateEnd, secType, timeBegin, timeEnd, flag,
          message,
          responseStockTrans, responseStockQtys);

      dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
      dataLog.setResponseTime(new Date());
      dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);

      try {
        Map<String, Object> responseData = new HashMap<String, Object>();
        responseData.put("REQUEST", requestData);
        responseData.put("FLAG", flag.value);
        responseData.put("MESSAGE", message.value);
        responseData.put("OUTPUT", responseStockTrans.value);
        responseData.put("OUTPUT1", responseStockQtys.value);

        String json = JsonUtil.toJson(responseData);
        lesStockSyncs.setReceivedData(json);
        lesStockSyncs.setErrorMsg("");
        lesStockSyncs.setReDoneSns("");
        LesStockSyncsService.insert(lesStockSyncs);
        int id = LesStockSyncsService.getIdbyDonetime(lesStockSyncs.getDoneTime());
        dataLog.setResponseData(json);
        dataLog.setForeignKey(id + "");
        EisInterfaceDataLogService.insert(dataLog);

        return id;
      } catch (Exception ex) {
        log.error("记录接口日志时，发生未知异常：" + ex.getMessage(), ex);
        return -1;
      }
    } catch (Exception e) {
      dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
      dataLog.setErrorMessage(e.getMessage());
      dataLog.setForeignKey("");
      dataLog.setResponseData(e.getMessage());
      dataLog.setResponseTime(new Date());
      log.error("调用les接口时发生未知异常", e);
      flag.value = "-1";
      lesStockSyncs.setErrorMsg("未知异常，请查看日志");
      try {
        EisInterfaceDataLogService.insert(dataLog);
      } catch (Exception e1) {
        log.error(e1);
      }
      return -1;
    }

  }

  private String getWsdlPath(String wsdlFile) {
    return help.getWSDLPATH(wsdlFile);
  }

  public JdeAndLesLoseValidityResponse.Out cancelTransferLineToLes(
      String lineNum, String lesNum) {
    // 要记录接口数据日志
    EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
    dataLog.setForeignKey(lineNum);
    dataLog.setInterfaceCode(EisInterfaceDataLog.INTERFACE_CODE_CANCEL_TRANSFERLINE_TO_LES);

    Long startTime = System.currentTimeMillis();
    JdeAndLesLoseValidity_Type.In input = new JdeAndLesLoseValidity_Type.In();
    input.setABGRU("95");
    input.setVBELN(lesNum);
    dataLog.setRequestData(new Gson().toJson(input));
    dataLog.setRequestTime(new Date());

    try {
      // 调用les接口
      JdeAndLesLoseValidity soap = new JdeAndLesLoseValidity_Service(
          this.getClass().getResource(wsdllocation + "/JdeAndLesLoseValidity.wsdl"))
          .getJdeAndLesLoseValiditySOAP();
      JdeAndLesLoseValidityResponse.Out output = soap
          .jdeAndLesLoseValidity(input);
      // 记录日志
      dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
      dataLog.setResponseData(new Gson().toJson(output));
      dataLog.setResponseTime(new Date());
      transferLineDao.updateRemarkByLineNum(lineNum, output.getMESSAGE());
      return output;
    } catch (Exception e) {
      log.error("取消调拨单到LES时，发生未知异常", e);
      dataLog.setResponseTime(new Date());
      dataLog.setResponseData("can not connect");
      dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
      dataLog.setCreateTime(new Date());
      dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
      dataLog.setErrorMessage("未知异常：" + e.getMessage());
      return null;
    } finally {
      try {
        EisInterfaceDataLogService.insert(dataLog);
      } catch (Exception ex) {
        log.error("记录接口日志时，发生未知异常：" + ex.getMessage(), ex);
      }
    }
  }


  /**
   * 传递用于录入费用的调拨单信息到LES
   *
   * @param line 调拨单
   * @param kunag 调出中心
   * @param kunnr 调入中心
   */
  public boolean transferLineToLesForFeeInput(InvTransferLine line, String kunag, String kunnr) {
    INPUT intype = this.getTransferLineToLesForFeeInput(line, kunag, kunnr);
    EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
    dataLog.setForeignKey(line.getLineNum());
    dataLog.setInterfaceCode(EisInterfaceDataLog.INTERFACE_CODE_EXCHANGE_GOODS_TO_LES);
    dataLog.setRequestData(new Gson().toJson(intype));
    dataLog.setRequestTime(new Date());
    Long startTime = System.currentTimeMillis();

    OUTPUT output = null;
    try {
      output = this.getTransferLineToLesForFeeOutput(intype);
    } catch (Exception e) {
      log.error("传递用于录入费用的调拨单信息到LES时发生未知异常", e);
      dataLog.setErrorMessage("未知异常：" + e.getMessage());
    }

    try {
      Gson gson = new Gson();
      String outputStr = gson.toJson(output);
      dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
      dataLog.setResponseData(outputStr);
      dataLog.setResponseTime(new Date());
      if (output == null) {
        dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
        throw new RuntimeException("传递用于录入费用的调拨单信息到LES时发生未知异常");
      } else {
        if (!"S".equalsIgnoreCase(output.getFLAG())) {
          dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
          dataLog.setErrorMessage(output.getMESSAGE());
          throw new RuntimeException(output.getMESSAGE());
        } else {
          dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
        }
      }
      return true;
    } catch (Exception e) {
      log.error("传递用于录入费用的调拨单信息到LES失败", e);
      return false;
    } finally {
      try {
        eisInterfaceDataLogService.insert(dataLog);
      } catch (Exception ex) {
        log.error("记录接口日志时，发生未知异常：" + ex.getMessage(), ex);
      }
    }
  }

  private OUTPUT getTransferLineToLesForFeeOutput(INPUT input) throws MalformedURLException {
//        URL url = new URL(this.getWsdlPath("ExchangeGoodsToLES.wsdl"));
    ExchangeGoodsToLES_Service service = new ExchangeGoodsToLES_Service(
        this.getClass().getResource(wsdllocation + "/ExchangeGoodsToLES.wsdl"));

    ExchangeGoodsToLES soap = service.getExchangeGoodsToLESSOAP();
    return soap.exchangeGoodsToLES(input);
  }

  private INPUT getTransferLineToLesForFeeInput(InvTransferLine line, String kunag, String kunnr) {
    INPUT input = new INPUT();
    input.setADD1("");
    input.setADD2("");
    input.setARKTX(line.getItemName());
    Date createTime = line.getCreateTime();
    if (createTime == null) {
      createTime = new Date();
    }
    input.setAUDAT(new SimpleDateFormat("yyyy-MM-dd").format(createTime));
    input.setAUTIM(new SimpleDateFormat("HH:mm:ss").format(createTime));
    input.setBSTNK(line.getLineNum());
    input.setKUNAG(kunag);
    input.setKUNNR(kunnr);
    input.setKWMENG(new BigDecimal(line.getTransferQty()));
    input.setLGORT(line.getSecFrom());
    input.setMATNR(line.getItemCode());
    input.setMEINS("TAI");
    input.setPOSNR("10");
    return input;
  }

	/*
	 * public List<ZLESDCMS24> getBomInfoFromLes(String inputDate) { //
	 * 要记录接口数据日志 EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
	 * dataLog.setForeignKey(inputDate);
	 * dataLog.setInterfaceCode(EisInterfaceDataLog.
	 * INTERFACE_CODE_GET_BOM_INFO_FROM_LES); dataLog.setRequestData(new
	 * Gson().toJson(inputDate));
	 * 
	 * Long startTime = System.currentTimeMillis(); dataLog.setRequestTime(new
	 * Date());
	 * 
	 * //访问接口 Holder<String> flag = new Holder<String>(); Holder<String> message
	 * = new Holder<String>(); Holder<List<ZLESDCMS24>> outputList = new
	 * Holder<List<ZLESDCMS24>>(); try { URL url = new
	 * URL(this.getWsdlPath("GetBomInfoFromLESToEHAIER.wsdl"));
	 * GetBomInfoFromLESToEHAIER_Service service = new
	 * GetBomInfoFromLESToEHAIER_Service(url); GetBomInfoFromLESToEHAIER soap =
	 * service.getGetBomInfoFromLESToEHAIERSOAP();
	 * soap.getBomInfoFromLESToEHAIER(inputDate, flag, message, outputList); }
	 * catch (Exception e) { flag.value = "E"; log.error("从CRM获取采购价格时，发生未知异常",
	 * e); dataLog.setErrorMessage("未知异常：" + e.getMessage()); }
	 * 
	 * //记录日志 dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
	 * Map<String, Object> responseData = new HashMap<String, Object>();
	 * responseData.put("FLAG", flag.value); responseData.put("MESSAGE",
	 * message.value); responseData.put("Z_OUTPUT_PARA", outputList.value);
	 * dataLog.setResponseData(new Gson().toJson(responseData));
	 * dataLog.setResponseTime(new Date()); if
	 * ("S".equalsIgnoreCase(flag.value)) {
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS); }
	 * else {
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR); }
	 * try { EisInterfaceDataLogService.insert(dataLog); } catch (Exception ex)
	 * { log.error("记录接口日志时，发生未知异常：" + ex.getMessage(), ex); }
	 * 
	 * return outputList == null ? null : outputList.value; }
	 * 
	 *//**
   * 往Les创建采购订单
   *
   * @param purchaseOrderQueueWrapper
   * @return
   */
  public Boolean createPurchaseOrderToLes(PurchaseOrderQueueWrapper purchaseOrderQueueWrapper,
      PurchaseOrderService purchaseOrderService) {

    PurchaseOrderQueue purchaseOrderQueue = purchaseOrderQueueWrapper.getPurchaseOrderQueue();
    PurchaseOrder purchaseOrder = purchaseOrderQueueWrapper.getPurchaseOrder();
    PurchaseItem purchaseItem = purchaseOrderQueueWrapper.getPurchaseItem();
    //组装参数
    String source = "CGRK";//订单来源码
    String sourceName = this.getSourceName(source);//来源名称
    String sourceSn = purchaseItem.getSourceSn();//来源订单编号
    String corderSn = purchaseItem.getPoItemNo();//网单编号
    Integer posex = 10;//采购订单行项目

    Date addDate = purchaseItem.getPoTime();//订单创建时间
    String auart = purchaseOrder.getType();//订单类型
    String kunnr = purchaseOrder.getSoldTo();//售达方
    String accepter = purchaseOrder.getDeliveryTo();//送达方
    String sku = purchaseItem.getSku();//sku
    sku = convertToExternalSku(sku, EisExternalSku.TYPE_R);

    String brandName = purchaseItem.getBrand();//品牌名称
    Long quantity = (long) purchaseItem.getPoQty();//数量
    String meins = "TAI";//基本单位
    Integer charg = ConvertUtil.toInt(purchaseItem.getItemType(), 10);//批次
    BigDecimal unitPrice = purchaseItem.getPrice();//单价

    BigDecimal subtotal = unitPrice.multiply(new BigDecimal(quantity));//小计
    BigDecimal shipFee = new BigDecimal(0);//运费

    BigDecimal total = purchaseOrder.getAmount();//总计
    String consignee = purchaseOrder.getReceiver();//订货人
    String sdabw = purchaseItem.getSign();//特殊处理标记
    String mobile = purchaseOrder.getMobile();//订货人手机
    String phone = purchaseOrder.getTelephone();//订货人电话

    String provinceName = purchaseOrder.getProvince();//省
    String cityName = purchaseOrder.getCity();//市
    String regionName = purchaseOrder.getCounty();//区
    String address = purchaseOrder.getAddress();//地址
    String payStatus = purchaseItem.getPayStatus();//付款状态
    String sCode = purchaseItem.getSecCode();//库位

    String augru = "";//定单原因
    String bstkd_e = "";//运达方的采购订单编号
    String posnr_e = ""; //优先采购订单的项目数
    String zip = "";//邮编
    String payType = "";// 付款方式
    String remark = "";//客户备注
    String kunem = "";//最终客户
    String kunz1 = "";//产品基地代码
    String sdaem = "";//基地直发客户标记
    String urlab = "";//超期免单标识
    String urmemo = "";//加急配送标释
    String isNeedInvoice = ""; //发票信息
    String invoiceType = "";
    String invoiceAcc = "";
    String invoiceAddress = "";
    String invoiceBank = "";
    String taxSpotNum = "";

    Date reqDate = new Date();//调用les接口时间
    String add1 = "";
    String add2 = "";//是否货票同行标识
    String add3 = "";

    // 要记录接口数据日志
    EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
    dataLog.setForeignKey(corderSn);
    dataLog.setInterfaceCode(EisInterfaceDataLog.INTERFACE_CODE_GET_PURCHASEORDER_TRANS_TO_LES);
    dataLog.setRequestTime(reqDate);
    Long startTime = System.currentTimeMillis();

    //调用接口
    try {
      if (log.isDebugEnabled()) {
        log.debug("[purchase_queue_id_" + purchaseOrderQueue.getId() + "]: 采购订单明细编号:"
            + purchaseItem.getPoItemNo() + "; 采购订单编号:" + purchaseOrder.getPoNo());
      }

      List<com.haier.stock.eai.createpurchaseordertoles.InputType> inputList = new ArrayList<com.haier.stock.eai.createpurchaseordertoles.InputType>();
      com.haier.stock.eai.createpurchaseordertoles.InputType input = new com.haier.stock.eai.createpurchaseordertoles.InputType();
      //1.商城订单来源代码 - 30
      if (source.length() > 30) {
        source = source.substring(0, 30);
      }
      input.setSOURCE(source);

      //2.商城订单来源文字描述 - 30
      if (sourceName.length() > 30) {
        sourceName = sourceName.substring(0, 30);
      }
      input.setSOURCEEXT(sourceName);

      //3.商城来源订单号 - 30
      if (sourceSn.length() > 30) {
        sourceSn = sourceSn.substring(0, 30);
      }
      input.setSOURCESN(sourceSn);

      //4.商城网单编号 - 35
      if (corderSn.length() > 35) {
        corderSn = corderSn.substring(0, 35);
      }
      input.setBSTKD(corderSn);

      //5.商城订单行项目 - 6
      input.setPOSEX(posex.toString());

      //订单创建日期和时间
      Calendar cl = Calendar.getInstance();
      cl.setTime(addDate);
      XMLGregorianCalendar xgcDate = DatatypeFactory.newInstance()
          .newXMLGregorianCalendarDate(cl.get(Calendar.YEAR), cl.get(Calendar.MONTH) + 1,
              cl.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
      XMLGregorianCalendar xgcTime = DatatypeFactory.newInstance()
          .newXMLGregorianCalendarTime(cl.get(Calendar.HOUR_OF_DAY), cl.get(Calendar.MINUTE),
              cl.get(Calendar.SECOND), DatatypeConstants.FIELD_UNDEFINED);
      //6.订单创建日期
      input.setAUDAT(xgcDate);

      //7.订单创建时间
      input.setAUTIM(xgcTime);

      //8.单据类型 - 4
      if (auart.length() > 4) {
        auart = auart.substring(0, 4);
      }
      input.setAUART(auart);

      //9.售达方 - 10
      if (kunnr.length() > 10) {
        kunnr = kunnr.substring(0, 10);
      }
      input.setKUNNR(kunnr);

      //10.送达方 - 10
      if (accepter.length() > 10) {
        accepter = accepter.substring(0, 10);
      }
      input.setKUNWE(accepter);

      //11.物料号 - 18
      if (sku.length() > 18) {
        sku = sku.substring(0, 18);
      }
      input.setMATNR(sku);

      //12.商品品牌 - 20，可为空
      if (brandName.length() > 20) {
        brandName = brandName.substring(0, 20);
      }
      input.setCOMTYP(brandName);

      //13.订单数量 -15
      input.setKWMENG(new BigDecimal(quantity));

      //14.基本单位 - 3, 非空， EA或TAI，商城不能空，
      if (meins.length() > 3) {
        meins = meins.substring(0, 3);
      }
      input.setMEINS(meins);

      //15.库存地点 - 4
      if (sCode.length() > 4) {
        sCode = sCode.substring(0, 4);
      }
      input.setLGORT(sCode);

      //16.批次 - 10, 非空（商城默认为 10）
      input.setCHARG(charg.toString());

      //17.特殊处理标记 - 4,(1：自提，2：配送,70:直发客户)
      input.setSDABW(sdabw);

      //18.定单原因 - 3,可为空
      if (augru.length() > 3) {
        augru = augru.substring(0, 3);
      }
      input.setAUGRU(augru);

      //19.运达方的采购订单编号 - 35
      if (bstkd_e.length() > 35) {
        bstkd_e = bstkd_e.substring(0, 35);
      }
      input.setBSTKDE(bstkd_e);

      //20.优先采购订单的项目数 - 6
      if (posnr_e.length() > 6) {
        posnr_e = posnr_e.substring(0, 6);
      }
      input.setPOSNRE(posnr_e);

      //21.单价 - 12
      input.setKBETR(unitPrice);

      //22.商品金额小计
      input.setKWERT(subtotal);

      //23.运费 - 12
      input.setSHIPCO(shipFee);

      //24.订单总金额
      input.setKWERZ(total);

      //25.订货人姓名 - 30
      if (consignee.length() > 30) {
        consignee = consignee.substring(0, 30);
      }
      input.setDHRXM(consignee);

      //26.订货人联系电话 - 30
      if (mobile.length() > 30) {
        mobile = mobile.substring(0, 30);
      }
      input.setDHRPH(mobile);

      //27.收货人移动电话 - 30
      input.setSHRMOB(mobile);

      //28.收货人姓名 - 30
      input.setSHRXM(consignee);

      //29.收货人固定电话 - 30 ，可为空
      if (phone.length() > 30) {
        phone = phone.substring(0, 30);
      }
      input.setSHRTEL(phone);

      //30.收货人所在省 - 30
      if (provinceName.length() > 30) {
        provinceName = provinceName.substring(0, 30);
      }
      input.setPROV(provinceName);

      //31.收货人所在市 - 30
      if (cityName.length() > 30) {
        cityName = cityName.substring(0, 30);
      }
      input.setCITY(cityName);

      //32.收货人所在县/区 - 30
      if (regionName.length() > 30) {
        regionName = regionName.substring(0, 30);
      }
      input.setCOUNTY(regionName);

      //33.收货地址 - 100
      if (address.length() > 100) {
        address = address.substring(0, 100);
      }
      input.setADDRESS(address);

      //34.邮编 - 6
      if (zip.length() > 6) {
        zip = zip.substring(0, 6);
      }
      input.setPSTLZ(zip);

      //35.付款状态 - 20
      if (payStatus.length() > 20) {
        payStatus = payStatus.substring(0, 20);
      }
      input.setPAYSTE(payStatus);

      //36.支付类别 - 20
      if (payType.length() > 20) {
        payType = payType.substring(0, 20);
      }
      input.setPAYTYP(payType);

      //37.预约发货时间
      //客户要求送货日期，可为空
      input.setYDDAT(null);

      //38.客户要求送货时间，可为空
      input.setYDTIME(null);

      //39客户备注 - 200，可为空
      if (remark.length() > 200) {
        remark = remark.substring(0, 200);
      }
      input.setSDMEMO(remark);

      //40.最终客户 - 10，可为空
      if (kunem.length() > 10) {
        kunem = kunem.substring(0, 10);
      }
      input.setKUNEM(kunem);

      //41.产品基地代码  - 10，可为空
      if (kunz1.length() > 10) {
        kunz1 = kunz1.substring(0, 10);
      }
      input.setKUNZ1(kunz1);

      //42.基地直发客户标记 - 10，可为空
      if (sdaem.length() > 10) {
        sdaem = sdaem.substring(0, 10);
      }
      input.setSDAEM(sdaem);

      //43.超期免单标识 - 1，可为空
      if (urlab.length() > 1) {
        urlab = urlab.substring(0, 1);
      }
      input.setURLAB(urlab);

      //44.加急配送标释 - 10，可为空
      if (urmemo.length() > 10) {
        urmemo = urmemo.substring(0, 10);
      }
      input.setURMEMO(urmemo);

      //45.是否需要发票 - 10，可为空
      if (isNeedInvoice.length() > 10) {
        isNeedInvoice = isNeedInvoice.substring(0, 10);
      }
      input.setINVO(isNeedInvoice);

      //46.发票类型 - 30，可为空
      if (invoiceType.length() > 30) {
        invoiceType = invoiceType.substring(0, 30);
      }
      input.setINVTYP(invoiceType);

      //47.发票抬头 - 100，可为空
      if (invoiceAcc.length() > 100) {
        invoiceAcc = invoiceAcc.substring(0, 100);
      }
      input.setINVACC(invoiceAcc);

      //48.发票地址 - 100，可为空
      if (invoiceAddress.length() > 100) {
        invoiceAddress = invoiceAddress.substring(0, 100);
      }
      input.setINVADD(invoiceAddress);

      //49.发票开户行 - 100，可为空
      if (invoiceBank.length() > 100) {
        invoiceBank = invoiceBank.substring(0, 100);
      }
      input.setINVBANK(invoiceBank);

      //50.纳税人登记号 - 100，可为空
      if (taxSpotNum.length() > 100) {
        taxSpotNum = taxSpotNum.substring(0, 100);
      }
      input.setINVNUM(taxSpotNum);

      //请求时间
      cl.setTime(reqDate);
      xgcDate = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
          cl.get(Calendar.YEAR), cl.get(Calendar.MONTH) + 1, cl.get(Calendar.DAY_OF_MONTH),
          DatatypeConstants.FIELD_UNDEFINED);
      xgcTime = DatatypeFactory.newInstance().newXMLGregorianCalendarTime(
          cl.get(Calendar.HOUR_OF_DAY), cl.get(Calendar.MINUTE), cl.get(Calendar.SECOND),
          DatatypeConstants.FIELD_UNDEFINED);
      //51.商城订单创建日期
      input.setERDAT(xgcDate);

      //52.商城订单创建时间
      input.setERZET(xgcTime);

      //53.备用字段1 - 20，可为空
      if (add1.length() > 20) {
        add1 = add1.substring(0, 20);
      }
      input.setADD1(add1);

      //54.是否货票同行 - 30，可为空
      if (add2.length() > 30) {
        add2 = add2.substring(0, 30);
      }
      input.setADD2(add2);

      //55.备用字段3 - 50，可为空
      if (add3.length() > 50) {
        add3 = add3.substring(0, 50);
      }
      input.setADD3(add3);

      inputList.add(input);

      //请求参数记录到日志中
      dataLog.setRequestData(new Gson().toJson(input));

      //调用les接口
      com.haier.stock.eai.createpurchaseordertoles.CreateSCOrderToLES soap = new com.haier.stock.eai.createpurchaseordertoles.CreateSCOrderToLES_Service(
          this.getClass().getResource(wsdllocation + "/CreateSCOrderToLES.wsdl")).getCreateSCOrderToLESSOAP();

      List<com.haier.stock.eai.createpurchaseordertoles.OutputType> outPutList = soap
          .createSCOrderToLES(inputList);

      dataLog.setResponseTime(new Date());
      dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
      dataLog.setCreateTime(new Date());

      //返回结果
      if (outPutList == null || outPutList.size() == 0 || outPutList.get(0) == null) {
        log.warn("[purchase_queue_id_" + purchaseOrderQueue.getId()
            + "]: les调用异常, 采购订单队列id：" + purchaseOrderQueue.getId()
            + ", outputList is null");

        dataLog.setResponseData("");
        dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
        interfaceDataLogDao.insert(dataLog);
        return false;
      }

      com.haier.stock.eai.createpurchaseordertoles.OutputType output = outPutList.get(0);

      dataLog.setResponseData(new Gson().toJson(output));

      String result = output.getFLAG().toLowerCase();

      // 1:失败
      if (!result.equals("1")) {
        purchaseOrderQueue.setLesNo("");
        purchaseOrderQueue.setLesMsg("les调用结果：" + output.getMESSAGE());
        purchaseOrderQueue.setStatus(PurchaseOrderQueueStatus.POQ_ERROR.getValue());
        //更新采购订单队列表
        purchaseOrderService.updatePurchaseOrderQueueAfterSynced(purchaseOrderQueue);

        dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
        interfaceDataLogDao.insert(dataLog);
        return false;
      } else {// 2：成功
        String lessOrdersn = output.getVBELN();//提单号

        purchaseOrderQueue.setLesNo(lessOrdersn);
        purchaseOrderQueue.setLesMsg("");
        purchaseOrderQueue.setStatus(PurchaseOrderQueueStatus.POQ_FINISH.getValue());
        //更新采购订单队列表
        purchaseOrderService.updatePurchaseOrderQueueAfterSynced(purchaseOrderQueue);

        //记录接口日志
        dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
        interfaceDataLogDao.insert(dataLog);
        return true;
      }
    } catch (Exception e) {
      log.error("[purchase_queue_id_" + purchaseOrderQueue.getId() + "]: les调用异常, 采购订单队列id："
          + purchaseOrderQueue.getId(), e);
      dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
      dataLog.setResponseTime(new Date());
      dataLog.setResponseData("");
      dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
      dataLog.setCreateTime(new Date());
      interfaceDataLogDao.insert(dataLog);
      return false;
    }
  }


  /**
   *
   * 往Les创建调提单
   *
   * @return
   */
	/*
	 * public com.haier.cbs.eai.createpurchaseordertoles.OutputType
	 * createTransferLineToLes(InvTransferLine transferLine, String
	 * transferFrom, String transferTo) { //组装参数 String source = "1";//订单来源码
	 * String sourceName = "商城调拨单";//来源名称 String sourceSn = "";//来源订单编号 String
	 * corderSn = transferLine.getLineNum();//网单编号 Integer posex = 10;//采购订单行项目
	 * 
	 * Date addDate = transferLine.getCreateTime();//订单创建时间 String auart =
	 * "ZGI6";//订单类型 String kunnr = transferFrom;//售达方 String accepter =
	 * transferTo;//送达方 String sku = transferLine.getItemCode();//sku sku =
	 * convertToExternalSku(sku, EisExternalSku.TYPE_R); String brandName =
	 * "";//品牌名称 Long quantity = transferLine.getTransferQty().longValue();//数量
	 * String meins = "TAI";//基本单位 Integer charg = 10;//批次 BigDecimal unitPrice
	 * = new BigDecimal(0);//单价
	 * 
	 * BigDecimal subtotal = new BigDecimal(0);//小计 BigDecimal shipFee =
	 * transferLine.getTransferFee();//运费
	 * 
	 * BigDecimal total = new BigDecimal(0);//总计 String consignee = "";//订货人
	 * String sdabw = "2";//特殊处理标记 String mobile = "";//订货人手机 String phone =
	 * "";//订货人电话
	 * 
	 * String provinceName = "";//省 String cityName = "";//市 String regionName =
	 * "";//区 String address = "";//地址 String payStatus = "";//付款状态 String sCode
	 * = transferLine.getSecFrom();//库位
	 * 
	 * String augru = "";//定单原因 String bstkd_e = corderSn;//LES需要根据出库订单，自行生成入库订单
	 * 而BSTKD_E字段是标注前续单号用的 String posnr_e = ""; //优先采购订单的项目数 String zip =
	 * "";//邮编 String payType = "";// 付款方式 String remark = "";//客户备注 String
	 * kunem = "";//最终客户 String kunz1 = "";//产品基地代码 String sdaem = "";//基地直发客户标记
	 * String urlab = "";//超期免单标识 String urmemo = "";//加急配送标释 String
	 * isNeedInvoice = ""; //发票信息 String invoiceType = ""; String invoiceAcc =
	 * ""; String invoiceAddress = ""; String invoiceBank = ""; String
	 * taxSpotNum = "";
	 * 
	 * Date reqDate = new Date();//调用les接口时间 String add1 = ""; String add2 =
	 * "";//是否货票同行标识 String add3 = "";
	 * 
	 * // 要记录接口数据日志 EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
	 * dataLog.setForeignKey(corderSn);
	 * dataLog.setInterfaceCode(EisInterfaceDataLog.
	 * INTERFACE_CODE_GET_TRANSFER_LINE_TO_LES);
	 * dataLog.setRequestTime(reqDate); Long startTime =
	 * System.currentTimeMillis();
	 * 
	 * //调用接口 try {
	 * 
	 * List<com.haier.cbs.eai.createpurchaseordertoles.InputType> inputList =
	 * new ArrayList<com.haier.cbs.eai.createpurchaseordertoles.InputType>();
	 * com.haier.cbs.eai.createpurchaseordertoles.InputType input = new
	 * com.haier.cbs.eai.createpurchaseordertoles.InputType(); //1.商城订单来源代码 - 30
	 * if (source.length() > 30) { source = source.substring(0, 30); }
	 * input.setSOURCE(source);
	 * 
	 * //2.商城订单来源文字描述 - 30 if (sourceName.length() > 30) { sourceName =
	 * sourceName.substring(0, 30); } input.setSOURCEEXT(sourceName);
	 * 
	 * //3.商城来源订单号 - 30 if (sourceSn.length() > 30) { sourceSn =
	 * sourceSn.substring(0, 30); } input.setSOURCESN(sourceSn);
	 * 
	 * //4.商城网单编号 - 35 if (corderSn.length() > 35) { corderSn =
	 * corderSn.substring(0, 35); } input.setBSTKD(corderSn);
	 * 
	 * //5.商城订单行项目 - 6 input.setPOSEX(posex.toString());
	 * 
	 * //订单创建日期和时间 Calendar cl = Calendar.getInstance(); cl.setTime(addDate);
	 * XMLGregorianCalendar xgcDate = DatatypeFactory.newInstance()
	 * .newXMLGregorianCalendarDate(cl.get(Calendar.YEAR),
	 * cl.get(Calendar.MONTH) + 1, cl.get(Calendar.DAY_OF_MONTH),
	 * DatatypeConstants.FIELD_UNDEFINED); XMLGregorianCalendar xgcTime =
	 * DatatypeFactory.newInstance()
	 * .newXMLGregorianCalendarTime(cl.get(Calendar.HOUR_OF_DAY),
	 * cl.get(Calendar.MINUTE), cl.get(Calendar.SECOND),
	 * DatatypeConstants.FIELD_UNDEFINED); //6.订单创建日期 input.setAUDAT(xgcDate);
	 * 
	 * //7.订单创建时间 input.setAUTIM(xgcTime);
	 * 
	 * //8.单据类型 - 4 if (auart.length() > 4) { auart = auart.substring(0, 4); }
	 * input.setAUART(auart);
	 * 
	 * //9.售达方 - 10 if (kunnr.length() > 10) { kunnr = kunnr.substring(0, 10); }
	 * input.setKUNNR(kunnr);
	 * 
	 * //10.送达方 - 10 if (accepter.length() > 10) { accepter =
	 * accepter.substring(0, 10); } input.setKUNWE(accepter);
	 * 
	 * //11.物料号 - 18 if (sku.length() > 18) { sku = sku.substring(0, 18); }
	 * input.setMATNR(sku);
	 * 
	 * //12.商品品牌 - 20，可为空 if (brandName.length() > 20) { brandName =
	 * brandName.substring(0, 20); } input.setCOMTYP(brandName);
	 * 
	 * //13.订单数量 -15 input.setKWMENG(new BigDecimal(quantity));
	 * 
	 * //14.基本单位 - 3, 非空， EA或TAI，商城不能空， if (meins.length() > 3) { meins =
	 * meins.substring(0, 3); } input.setMEINS(meins);
	 * 
	 * //15.库存地点 - 4 if (sCode.length() > 4) { sCode = sCode.substring(0, 4); }
	 * input.setLGORT(sCode);
	 * 
	 * //16.批次 - 10, 非空（商城默认为 10） input.setCHARG(charg.toString());
	 * 
	 * //17.特殊处理标记 - 4,(1：自提，2：配送,70:直发客户) input.setSDABW(sdabw);
	 * 
	 * //18.定单原因 - 3,可为空 if (augru.length() > 3) { augru = augru.substring(0,
	 * 3); } input.setAUGRU(augru);
	 * 
	 * //19.运达方的采购订单编号 - 35 if (bstkd_e.length() > 35) { bstkd_e =
	 * bstkd_e.substring(0, 35); } input.setBSTKDE(bstkd_e);
	 * 
	 * //20.优先采购订单的项目数 - 6 if (posnr_e.length() > 6) { posnr_e =
	 * posnr_e.substring(0, 6); } input.setPOSNRE(posnr_e);
	 * 
	 * //21.单价 - 12 input.setKBETR(unitPrice);
	 * 
	 * //22.商品金额小计 input.setKWERT(subtotal);
	 * 
	 * //23.运费 - 12 input.setSHIPCO(shipFee);
	 * 
	 * //24.订单总金额 input.setKWERZ(total);
	 * 
	 * //25.订货人姓名 - 30 if (consignee.length() > 30) { consignee =
	 * consignee.substring(0, 30); } input.setDHRXM(consignee);
	 * 
	 * //26.订货人联系电话 - 30 if (mobile.length() > 30) { mobile =
	 * mobile.substring(0, 30); } input.setDHRPH(mobile);
	 * 
	 * //27.收货人移动电话 - 30 input.setSHRMOB(mobile);
	 * 
	 * //28.收货人姓名 - 30 input.setSHRXM(consignee);
	 * 
	 * //29.收货人固定电话 - 30 ，可为空 if (phone.length() > 30) { phone =
	 * phone.substring(0, 30); } input.setSHRTEL(phone);
	 * 
	 * //30.收货人所在省 - 30 if (provinceName.length() > 30) { provinceName =
	 * provinceName.substring(0, 30); } input.setPROV(provinceName);
	 * 
	 * //31.收货人所在市 - 30 if (cityName.length() > 30) { cityName =
	 * cityName.substring(0, 30); } input.setCITY(cityName);
	 * 
	 * //32.收货人所在县/区 - 30 if (regionName.length() > 30) { regionName =
	 * regionName.substring(0, 30); } input.setCOUNTY(regionName);
	 * 
	 * //33.收货地址 - 100 if (address.length() > 100) { address =
	 * address.substring(0, 100); } input.setADDRESS(address);
	 * 
	 * //34.邮编 - 6 if (zip.length() > 6) { zip = zip.substring(0, 6); }
	 * input.setPSTLZ(zip);
	 * 
	 * //35.付款状态 - 20 if (payStatus.length() > 20) { payStatus =
	 * payStatus.substring(0, 20); } input.setPAYSTE(payStatus);
	 * 
	 * //36.支付类别 - 20 if (payType.length() > 20) { payType =
	 * payType.substring(0, 20); } input.setPAYTYP(payType);
	 * 
	 * //37.预约发货时间 //客户要求送货日期，可为空 input.setYDDAT(null);
	 * 
	 * //38.客户要求送货时间，可为空 input.setYDTIME(null);
	 * 
	 * //39客户备注 - 200，可为空 if (remark.length() > 200) { remark =
	 * remark.substring(0, 200); } input.setSDMEMO(remark);
	 * 
	 * //40.最终客户 - 10，可为空 if (kunem.length() > 10) { kunem = kunem.substring(0,
	 * 10); } input.setKUNEM(kunem);
	 * 
	 * //41.产品基地代码 - 10，可为空 if (kunz1.length() > 10) { kunz1 =
	 * kunz1.substring(0, 10); } input.setKUNZ1(kunz1);
	 * 
	 * //42.基地直发客户标记 - 10，可为空 if (sdaem.length() > 10) { sdaem =
	 * sdaem.substring(0, 10); } input.setSDAEM(sdaem);
	 * 
	 * //43.超期免单标识 - 1，可为空 if (urlab.length() > 1) { urlab = urlab.substring(0,
	 * 1); } input.setURLAB(urlab);
	 * 
	 * //44.加急配送标释 - 10，可为空 if (urmemo.length() > 10) { urmemo =
	 * urmemo.substring(0, 10); } input.setURMEMO(urmemo);
	 * 
	 * //45.是否需要发票 - 10，可为空 if (isNeedInvoice.length() > 10) { isNeedInvoice =
	 * isNeedInvoice.substring(0, 10); } input.setINVO(isNeedInvoice);
	 * 
	 * //46.发票类型 - 30，可为空 if (invoiceType.length() > 30) { invoiceType =
	 * invoiceType.substring(0, 30); } input.setINVTYP(invoiceType);
	 * 
	 * //47.发票抬头 - 100，可为空 if (invoiceAcc.length() > 100) { invoiceAcc =
	 * invoiceAcc.substring(0, 100); } input.setINVACC(invoiceAcc);
	 * 
	 * //48.发票地址 - 100，可为空 if (invoiceAddress.length() > 100) { invoiceAddress =
	 * invoiceAddress.substring(0, 100); } input.setINVADD(invoiceAddress);
	 * 
	 * //49.发票开户行 - 100，可为空 if (invoiceBank.length() > 100) { invoiceBank =
	 * invoiceBank.substring(0, 100); } input.setINVBANK(invoiceBank);
	 * 
	 * //50.纳税人登记号 - 100，可为空 if (taxSpotNum.length() > 100) { taxSpotNum =
	 * taxSpotNum.substring(0, 100); } input.setINVNUM(taxSpotNum);
	 * 
	 * //请求时间 cl.setTime(reqDate); xgcDate =
	 * DatatypeFactory.newInstance().newXMLGregorianCalendarDate(
	 * cl.get(Calendar.YEAR), cl.get(Calendar.MONTH) + 1,
	 * cl.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED);
	 * xgcTime = DatatypeFactory.newInstance().newXMLGregorianCalendarTime(
	 * cl.get(Calendar.HOUR_OF_DAY), cl.get(Calendar.MINUTE),
	 * cl.get(Calendar.SECOND), DatatypeConstants.FIELD_UNDEFINED);
	 * //51.商城订单创建日期 input.setERDAT(xgcDate);
	 * 
	 * //52.商城订单创建时间 input.setERZET(xgcTime);
	 * 
	 * //53.备用字段1 - 20，可为空 if (add1.length() > 20) { add1 = add1.substring(0,
	 * 20); } input.setADD1(add1);
	 * 
	 * //54.是否货票同行 - 30，可为空 if (add2.length() > 30) { add2 = add2.substring(0,
	 * 30); } input.setADD2(add2);
	 * 
	 * //55.备用字段3 - 50，可为空 if (add3.length() > 50) { add3 = add3.substring(0,
	 * 50); } input.setADD3(add3);
	 * 
	 * inputList.add(input);
	 * 
	 * //请求参数记录到日志中 dataLog.setRequestData(new Gson().toJson(input));
	 * 
	 * //调用les接口 CreateSCOrderToLES soap = new CreateSCOrderToLES_Service(new
	 * URL(
	 * this.getWsdlPath("CreateSCOrderToLES.wsdl"))).getCreateSCOrderToLESSOAP()
	 * ;
	 * 
	 * List<com.haier.cbs.eai.createpurchaseordertoles.OutputType> outPutList =
	 * soap .createSCOrderToLES(inputList);
	 * 
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
	 * dataLog.setResponseTime(new Date());
	 * dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
	 * dataLog.setCreateTime(new Date());
	 * com.haier.cbs.eai.createpurchaseordertoles.OutputType output = outPutList
	 * == null ? null : outPutList.get(0); dataLog.setResponseData(new
	 * Gson().toJson(output)); try { EisInterfaceDataLogService.insert(dataLog);
	 * } catch (Exception e) { log.error("向LES开调拨提单记录日志数据出错：", e); }
	 * 
	 * return output; } catch (Exception e) { log.error("[transferLine_id" +
	 * transferLine.getLineId() + "]: les调用异常", e);
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
	 * dataLog.setResponseTime(new Date()); dataLog.setResponseData("");
	 * dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
	 * dataLog.setCreateTime(new Date());
	 * EisInterfaceDataLogService.insert(dataLog); throw new
	 * BusinessException("调用LES开提单接口出现未知错误：" + e); } }
	 * 
	 * public
	 * com.haier.cbs.eai.selectinfofrommdm.SelectInfoFromMDMOPResponse.Output
	 * getSkuInfoFromMdm(Integer pageIndex, Date beginTime, Date endTime) { //
	 * 要记录接口数据日志 EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
	 * dataLog.setForeignKey(pageIndex.toString());
	 * dataLog.setInterfaceCode(EisInterfaceDataLog.
	 * INTERFACE_CODE_GET_SKU_INFO_FROM_MDM);
	 * 
	 * Long startTime = System.currentTimeMillis();
	 * 
	 * InType input = new InType(); input.setCurrentPage(pageIndex.toString());
	 * input.setDepartment("table_hm_value_set"); SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd"); input.setEndTime(sdf.format(endTime));
	 * input.setStartTime(sdf.format(beginTime));
	 * input.setTableName("haiermdm.hm_value_set"); dataLog.setRequestData(new
	 * Gson().toJson(input)); dataLog.setRequestTime(new Date());
	 * 
	 * //访问接口
	 * com.haier.cbs.eai.selectinfofrommdm.SelectInfoFromMDMOPResponse.Output
	 * output = null; try { URL url = new
	 * URL(this.getWsdlPath("SelectInfoFromMDM.wsdl"));
	 * SelectInfoFromMDM_Service service = new SelectInfoFromMDM_Service(url);
	 * SelectInfoFromMDM soap = service.getSelectInfoFromMDMSOAP(); output =
	 * soap.selectInfoFromMDMOP(input); } catch (Exception e) {
	 * log.error("从MDM获取sku信息时，发生未知异常", e); dataLog.setErrorMessage("未知异常：" +
	 * e.getMessage()); }
	 * 
	 * //记录日志 dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
	 * dataLog.setResponseData(new Gson().toJson(output));
	 * dataLog.setResponseTime(new Date()); if (output != null) {
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS); }
	 * else {
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR); }
	 * try { EisInterfaceDataLogService.insert(dataLog); } catch (Exception ex)
	 * { log.error("记录接口日志时，发生未知异常：" + ex.getMessage(), ex); }
	 * 
	 * return output; }
	 */
  public List<Map<String, String>> queryProduceInformationFromOms(String refNo, String url) {
    List<Map<String, String>> paramList = null;
    EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
    dataLog.setForeignKey(refNo);
    dataLog.setInterfaceCode(EisInterfaceDataLog.QUERY_ORDER_INFO_FROM_OMS);
    dataLog.setRequestData(refNo);
    dataLog.setRequestTime(new Date());
    Long startTime = System.currentTimeMillis();
    PostMethod postMethod = new PostMethod(url);
    InputStream inputStream = null;
    try {
      String requestData = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
          + "xmlns:cbs=\"http://service.oms.haier.com/services/CbsOrderInfo\">\n"
          + "<soapenv:Header/>"
          + "<soapenv:Body>"
          + "<cbs:requestData>"
          + "<cbs:add1>"
          + refNo
          + "</cbs:add1>"
          + "</cbs:requestData>"
          + "</soapenv:Body></soapenv:Envelope>";
      byte[] b = requestData.getBytes();
      inputStream = new ByteArrayInputStream(b, 0, b.length);

      RequestEntity requestEntity = new InputStreamRequestEntity(inputStream, b.length,
          "application/soap+xml; charset=utf-8");
      postMethod.setRequestEntity(requestEntity);
      HttpClient httpClient = new HttpClient();
      httpClient.executeMethod(postMethod);

      dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
      dataLog.setResponseData(postMethod.getResponseBodyAsString());
      dataLog.setResponseTime(new Date());
      dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);

      DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
      org.w3c.dom.Document document = documentBuilder.parse(postMethod
          .getResponseBodyAsStream());
      NodeList list = document.getElementsByTagName("item");
      paramList = new ArrayList<Map<String, String>>();
      for (int i = 0; i < list.getLength(); i++) {
        org.w3c.dom.Element element = (org.w3c.dom.Element) list.item(i);
        NodeList _list = element.getChildNodes();
        Map<String, String> params = new HashMap<String, String>();
        paramList.add(params);
        for (int j = 0; j < _list.getLength(); j++) {
          Node item = _list.item(j);
          if (item instanceof org.w3c.dom.Element) {
            org.w3c.dom.Element _element = (org.w3c.dom.Element) _list.item(j);
            params.put(_element.getTagName(), _element.getTextContent());
          }
        }
      }

    } catch (Exception e) {
      dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
      dataLog.setRequestData("");
      dataLog.setErrorMessage(e.getMessage());
      log.error("从OMS获取日日单生产信息失败：", e);
    } finally {
      try {
        if(inputStream!=null){
          inputStream.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      postMethod.releaseConnection();
    }

    try {
      interfaceDataLogDao.insert(dataLog);
    } catch (Exception ex) {
      log.error("记录接口日志时，发生未知异常：" + ex.getMessage(), ex);
    }

    if (paramList == null) {
      paramList = new ArrayList<Map<String, String>>();
    }
    return paramList;
  }
  /**
   * 获取日日单的生产/下线日期
   *
   * @param gvsOrderCode
   *            GVS订单号
   * @return
   */
  public Date queryProdDateFromEDW(String gvsOrderCode) {

    Date prodDate = null;

    EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
    dataLog.setInterfaceCode(EisInterfaceDataLog.QUERY_PROD_DATE_FROM_EDW);
    Long startTime = System.currentTimeMillis();

    dataLog.setRequestData(gvsOrderCode);
    dataLog.setForeignKey(gvsOrderCode);
    dataLog.setRequestTime(new Date());

    try {
      //URL url = new URL(this.getWsdlPath("QueryProOrderAndSaleOrderFromEDW.wsdl"));
      QueryProOrderAndSaleOrderFromEDW_Service service = new QueryProOrderAndSaleOrderFromEDW_Service(
          this.getClass().getResource(wsdllocation + "QueryProOrderAndSaleOrderFromEDW.wsdl"));
      QueryProOrderAndSaleOrderFromEDW soap = service
          .getQueryProOrderAndSaleOrderFromEDWSOAP();
      OutType outType = soap.queryProOrderAndSaleOrderFromEDW(gvsOrderCode);

      dataLog.setResponseData(JsonUtil.toJson(outType));
      dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
      dataLog.setResponseTime(new Date());
      dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);

      if (outType != null) {
        List<SelectITFMAKEPLANXIAXIANVOutput> output = outType.getOutput();
        if (output != null && output.size() > 0) {
          XMLGregorianCalendar completeDate = output.get(output.size() - 1)
              .getCOMPLETEDATE();
          if (completeDate != null) {
            prodDate = output.get(0).getCOMPLETEDATE().toGregorianCalendar().getTime();
          }
        }
      }

    } catch (Exception e) {
      dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
      dataLog.setErrorMessage(e.getMessage());
      dataLog.setForeignKey("");
      dataLog.setResponseData(e.getMessage());
      dataLog.setResponseTime(new Date());

      log.error("从EDW获取生产/下线日期失败：", e);

    } finally {
      try {
        interfaceDataLogDao.insert(dataLog);
      } catch (Exception ex) {
        log.error("记录接口日志时，发生未知异常：" + ex.getMessage(), ex);
      }
    }

    return prodDate;

  }
  public List<ZWDTABLE2> queryProduceCRMTidanInformationFromLess(Date date) {
    GetTidanZWDFromLESToEHAIER_Type request = new GetTidanZWDFromLESToEHAIER_Type();
    request.setERDAT(DateUtil.format(date, "yyyy-MM-dd"));

    EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
    dataLog.setInterfaceCode(EisInterfaceDataLog.QUERY_CRM_TIDAN_INFO_FROM_LESS);
    Long startTime = System.currentTimeMillis();

    dataLog.setRequestData(new Gson().toJson(request));
    dataLog.setRequestTime(new Date());

    try {
      //URL url = new URL(this.getWsdlPath("GetTidanZWDFromLESToEHAIER.wsdl"));
      GetTidanZWDFromLESToEHAIER_Service service = new GetTidanZWDFromLESToEHAIER_Service(this.getClass().getResource(wsdllocation + "GetTidanZWDFromLESToEHAIER.wsdl"));
      GetTidanZWDFromLESToEHAIER soap = service.getGetTidanZWDFromLESToEHAIERSOAP();

      String erdat = DateUtil.format(date, "yyyy-MM-dd");
      Holder<String> flag = new Holder<String>();
      Holder<String> msg = new Holder<String>();
      Holder<List<ZWDTABLE2>> outPut = new Holder<List<ZWDTABLE2>>();
      soap.getTidanZWDFromLESToEHAIER(erdat, flag, msg, outPut);

      dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
      dataLog.setResponseTime(new Date());
      dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("flag", flag.value);
      map.put("msg", msg.value);
      map.put("output", outPut.value);
      dataLog.setResponseData(JsonUtil.toJson(map));
      dataLog.setForeignKey("");
      try {
        interfaceDataLogDao.insert(dataLog);
      } catch (Exception ex) {
        log.error("记录接口日志时，发生未知异常：" + ex.getMessage(), ex);
      }

      if ("E".equals(flag.value))
        return new ArrayList<ZWDTABLE2>();
      else if (outPut.value == null)
        return new ArrayList<ZWDTABLE2>();
      return outPut.value;

    } catch (Exception e) {
      dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
      dataLog.setErrorMessage(e.getMessage());
      dataLog.setForeignKey("");
      dataLog.setResponseData(e.getMessage());
      dataLog.setResponseTime(new Date());
      try {
        interfaceDataLogDao.insert(dataLog);
      } catch (Exception e1) {
        log.error(e1);
      }
      log.error("从OMS获取生产数据发生错误：", e);
      throw new BusinessException("从OMS获取生产数据失败,Log Id:" + dataLog.getId());
    }

  }

  /**
   * 从CRM获取采购价格
   * @param corpCode 公司编码
   * @param custCode 付款方编码
   * @param sku 物料编码
   * @param regionCode 地区编码
   * @return
   */
  public com.haier.stock.eai.getinvrebateinfofromihs.OutputType getPurchasePriceFromCrm(String corpCode, String custCode, String sku,
      String regionCode) {
    // 要记录接口数据日志
    EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
    dataLog.setForeignKey(sku + "_" + corpCode + "_" + custCode);
    dataLog.setInterfaceCode(EisInterfaceDataLog.INTERFACE_CODE_GET_PURCHASE_PRICE_FROM_CRM);

    Long startTime = System.currentTimeMillis();

    com.haier.stock.eai.getinvrebateinfofromihs.InputType input =
        new com.haier.stock.eai.getinvrebateinfofromihs.InputType();
    input.setCorpCode(corpCode);
    input.setCustCode(custCode);
    input.setInvCode(sku);
    input.setRegionCode(regionCode);
    dataLog.setRequestData(new Gson().toJson(input));
    dataLog.setRequestTime(new Date());

    //访问接口
    com.haier.stock.eai.getinvrebateinfofromihs.OutputType output = null;
    try {
//      URL url = new URL(this.getWsdlPath("GetInvRebateInfoFromIHS.wsdl"));
      GetInvRebateInfoFromIHS_Service service = new
          GetInvRebateInfoFromIHS_Service(this.getClass().getResource(wsdllocation + "/GetInvRebateInfoFromIHS.wsdl"));
      GetInvRebateInfoFromIHS soap = service.getGetInvRebateInfoFromIHSSOAP();
      output = soap.getInvRebateInfoFromIHS(input);
    } catch (Exception e) {
      log.error("从CRM获取采购价格时，发生未知异常", e);
      dataLog.setErrorMessage("未知异常：" + e.getMessage());
    }

    //记录日志
    dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
    dataLog.setResponseData(new Gson().toJson(output));
    dataLog.setResponseTime(new Date());
    if (output != null) {
      dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
    } else {
      dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
    }
    try {
      interfaceDataLogDao.insert(dataLog);
    } catch (Exception ex) {
      log.error("记录接口日志时，发生未知异常：" + ex.getMessage(), ex);
    }

    return output;
  }

  /**
   * 从LES获取获取套机信息
   * @param inputDate
   * @return
   */
  public List<ZLESDCMS24> getBomInfoFromLes(String inputDate) {
    // 要记录接口数据日志
    EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
    dataLog.setForeignKey(inputDate);
    dataLog.setInterfaceCode(EisInterfaceDataLog.INTERFACE_CODE_GET_BOM_INFO_FROM_LES);
    dataLog.setRequestData(new Gson().toJson(inputDate));

    Long startTime = System.currentTimeMillis();
    dataLog.setRequestTime(new Date());

    //访问接口
    Holder<String> flag = new Holder<String>();
    Holder<String> message = new Holder<String>();
    Holder<List<ZLESDCMS24>> outputList = new Holder<List<ZLESDCMS24>>();
    try {
//      URL url = new URL(this.getWsdlPath("GetBomInfoFromLESToEHAIER.wsdl"));
      GetBomInfoFromLESToEHAIER_Service service =
          new GetBomInfoFromLESToEHAIER_Service(this.getClass().getResource(wsdllocation + "/GetBomInfoFromLESToEHAIER.wsdl"));
      GetBomInfoFromLESToEHAIER soap = service.getGetBomInfoFromLESToEHAIERSOAP();
      soap.getBomInfoFromLESToEHAIER(inputDate, flag, message, outputList);
    } catch (Exception e) {
      flag.value = "E";
      log.error("从CRM获取采购价格时，发生未知异常", e);
      dataLog.setErrorMessage("未知异常：" + e.getMessage());
    }

    //记录日志
    dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
    Map<String, Object> responseData = new HashMap<String, Object>();
    responseData.put("FLAG", flag.value);
    responseData.put("MESSAGE", message.value);
    responseData.put("Z_OUTPUT_PARA", outputList.value);
    dataLog.setResponseData(new Gson().toJson(responseData));
    dataLog.setResponseTime(new Date());
    if ("S".equalsIgnoreCase(flag.value)) {
      dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
    } else {
      dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
    }
    try {
      interfaceDataLogDao.insert(dataLog);
    } catch (Exception ex) {
      log.error("记录接口日志时，发生未知异常：" + ex.getMessage(), ex);
    }

    return outputList == null ? null : outputList.value;
  }

  /**
   * 从MDM获取所有符合条件的产品属性/物料基本信息
   *
   * @param beginTime
   * @param endTime
   * @return list of HmValueSet
   */
	/*
	 * public <E> List<E> getTargetListFromMdm(Date beginTime, Date endTime,
	 * Class<E> clazz) { List<E> ret = new ArrayList<E>(); log.info("从MDM获取" +
	 * clazz.getName() + "（beginTime:" + beginTime.toString() + ",endTime:" +
	 * endTime.toString() + "）"); this.getTargetListFromMdm(1, beginTime,
	 * endTime, ret, clazz); return ret; }
	 *
	 *//**
   * 从MDM获取产品属性/物料基本信息
   *
   * @param pageIndex
   * @param beginTime
   * @param endTime
   * @param ret
   */
	/*
	 * private <E> void getTargetListFromMdm(Integer pageIndex, Date beginTime,
	 * Date endTime, List<E> ret, Class<E> clazz) {
	 *
	 * InType intype = this.getSelectInfoFromMDMIntype(pageIndex, beginTime,
	 * endTime, clazz);
	 *
	 * EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
	 * dataLog.setForeignKey(pageIndex.toString());
	 * dataLog.setInterfaceCode(EisInterfaceDataLog.
	 * INTERFACE_CODE_GET_SKU_INFO_FROM_MDM); dataLog.setRequestData(new
	 * Gson().toJson(intype)); dataLog.setRequestTime(new Date()); Long
	 * startTime = System.currentTimeMillis();
	 *
	 * com.haier.cbs.eai.selectinfofrommdm.SelectInfoFromMDMOPResponse.Output
	 * output = null; try { output = this.getMdmOutput(intype); } catch
	 * (Exception e) { log.error("从MDM获取" + clazz.getSimpleName() + "信息时发生未知异常",
	 * e); dataLog.setErrorMessage("未知异常：" + e.getMessage()); }
	 *
	 * try { Gson gson = new Gson(); String outputStr = gson.toJson(output);
	 * dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
	 * dataLog.setResponseData(outputStr); dataLog.setResponseTime(new Date());
	 * if (output == null) {
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
	 * throw new RuntimeException("从MDM获取" + clazz.getSimpleName() +
	 * "信息时发生未知异常"); } else {
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS); }
	 *
	 * try { EisInterfaceDataLogService.insert(dataLog); } catch (Exception ex)
	 * { log.error("记录接口日志时，发生未知异常：" + ex.getMessage(), ex); }
	 *
	 * APIResult apiRet = gson.fromJson(outputStr, APIResult.class); List<E>
	 * targetList = this.getTargetList(apiRet.getOut(), clazz); if (targetList
	 * != null && targetList.size() > 0) { ret.addAll(targetList); }
	 *
	 * if (apiRet.getCurrentPage().intValue() < apiRet.getPageCount()) {
	 * this.getTargetListFromMdm(apiRet.getCurrentPage() + 1, beginTime,
	 * endTime, ret, clazz); } } catch (Exception e) { log.error("从MDM获取" +
	 * clazz.getSimpleName() + "信息时发生未知异常", e); throw new
	 * RuntimeException("从MDM获取" + clazz.getSimpleName() + "信息时发生未知异常"); } }
	 *
	 * private <E> InType getSelectInfoFromMDMIntype(Integer pageIndex, Date
	 * beginTime, Date endTime, Class<E> clazz) { String department = null;
	 * String tableName = null; if (ItemAttribute.class.equals(clazz)) {
	 * department = Constants.EAI_PARAM_DEP_FOR_ITEM_ATTRIBUTE; tableName =
	 * Constants.EAI_PARAM_TABLE_FOR_ITEM_ATTRIBUTE; } else if
	 * (ItemBase.class.equals(clazz)) { department =
	 * Constants.EAI_PARAM_DEP_FOR_ITEM_BASE; tableName =
	 * Constants.EAI_PARAM_TABLE_FOR_ITEM_BASE; } InType input = new InType();
	 * input.setCurrentPage(pageIndex.toString());
	 * input.setDepartment(department); SimpleDateFormat sdf = new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 * input.setEndTime(sdf.format(endTime));
	 * input.setStartTime(sdf.format(beginTime)); input.setTableName(tableName);
	 * return input; }
	 *
	 *//**
   * 访问接口
   *
   * @param input
   * @return
   * @throws MalformedURLException
   */
	/*
	 * private <E>
	 * com.haier.cbs.eai.selectinfofrommdm.SelectInfoFromMDMOPResponse.Output
	 * getMdmOutput(InType input) throws MalformedURLException { URL url = new
	 * URL(this.getWsdlPath("SelectInfoFromMDM.wsdl"));
	 * SelectInfoFromMDM_Service service = new SelectInfoFromMDM_Service(url);
	 * SelectInfoFromMDM soap = service.getSelectInfoFromMDMSOAP(); return
	 * soap.selectInfoFromMDMOP(input); }
	 *
	 * @SuppressWarnings("unchecked") private <E> List<E> getTargetList(String
	 * xml, Class<E> clazz) throws Exception { StringReader sr = null; try { if
	 * (StringUtil.isEmpty(xml, true)) return null; JAXBContext jc = null; if
	 * (ItemAttribute.class.equals(clazz)) { jc =
	 * JAXBContext.newInstance(Output.class); } else if
	 * (ItemBase.class.equals(clazz)) { jc =
	 * JAXBContext.newInstance(com.haier.cbs.eai.itembase.Output.class); } if
	 * (jc==null){ return null; } Unmarshaller unmarshaller =
	 * jc.createUnmarshaller(); xml = xml.replaceAll("\u003c",
	 * "<").replaceAll("\u003e", ">"); sr = new StringReader(xml);
	 *
	 * XMLInputFactory xif = XMLInputFactory.newInstance(); XMLStreamReader xsr
	 * = xif.createXMLStreamReader(sr); xsr = new ICStreamReaderDelegate(xsr);
	 *
	 * if (ItemAttribute.class.equals(clazz)) { Output out = (Output)
	 * unmarshaller.unmarshal(xsr); if (out != null && out.getRowset() != null)
	 * { return (List<E>) out.getRowset().getItemAttributes(); } } else if
	 * (ItemBase.class.equals(clazz)) { com.haier.cbs.eai.itembase.Output out =
	 * (com.haier.cbs.eai.itembase.Output) unmarshaller .unmarshal(xsr); if (out
	 * != null && out.getRowset() != null) { return (List<E>)
	 * out.getRowset().getItemBases(); } } } catch (Exception e) {
	 * log.error("将从MDM获取的" + clazz.getSimpleName() + "信息结构化时发生未知异常", e); throw
	 * e; }finally { if(sr!=null){ sr.close(); } } return null; }
	 *
	 *//**
   * 根据SKU从MDM获取物料基本信息
   *
   * @param skus
   * @return
   */
	/*
	 * public List<ItemBase> getMtlsBySkus(List<String> skus) { try { if (skus
	 * == null || skus.size() == 0) return null; List<ItemBase> ret = new
	 * ArrayList<ItemBase>(); for (String sku : skus) { EisInterfaceDataLog
	 * dataLog = new EisInterfaceDataLog(); try { InputsType input =
	 * this.constructMdmCondition( Constants.EAI_PARAM_DEP_FOR_ITEM_BASE,
	 * Constants.EAI_PARAM_TABLE_FOR_ITEM_BASE, sku);
	 * dataLog.setForeignKey(sku); dataLog
	 * .setInterfaceCode(EisInterfaceDataLog.
	 * INTERFACE_CODE_GET_SKU_INFO_FROM_MDM); dataLog.setRequestData(new
	 * Gson().toJson(input)); dataLog.setRequestTime(new Date()); Long startTime
	 * = System.currentTimeMillis();
	 *
	 * OutputsType output = null; try { output =
	 * this.selectMdmInfoFromMdmByConditonOutput(input); } catch (Exception e) {
	 * log.error("根据SKU从MDM获取物料基本信息时发生未知异常", e); dataLog.setErrorMessage("未知异常："
	 * + e.getMessage()); }
	 *
	 * Gson gson = new Gson(); String outputStr = gson.toJson(output);
	 * dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
	 * dataLog.setResponseData(outputStr); dataLog.setResponseTime(new Date());
	 *
	 * if (output == null) {
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
	 * throw new RuntimeException("根据SKU从MDM获取物料基本信息时发生未知异常"); } else {
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS); }
	 *
	 * APIResult apiRet = gson.fromJson(outputStr, APIResult.class);
	 * List<ItemBase> targetList = this.getTargetList(apiRet.getOut(),
	 * ItemBase.class); if (targetList != null && targetList.size() > 0) {
	 * ret.add(targetList.get(0)); } } finally { try {
	 * EisInterfaceDataLogService.insert(dataLog); } catch (Exception ex) {
	 * log.error("记录接口日志时，发生未知异常：" + ex.getMessage(), ex); } } } return ret; }
	 * catch (Exception e) { log.error("根据SKU从MDM获取物料基本信息时发生未知异常", e); throw new
	 * RuntimeException("根据SKU从MDM获取物料基本信息时发生未知异常"); } }
	 *
	 * private InputsType constructMdmCondition(String department, String
	 * tableName, String condition) { InputsType input = new InputsType();
	 * input.setDepartment(department); input.setTableName(tableName);
	 * input.setCondition("MATERIAL_CODE = '" + condition + "'"); return input;
	 * }
	 *
	 * private com.haier.cbs.eai.selectmdminfofrommdmbyconditon.OutputsType
	 * selectMdmInfoFromMdmByConditonOutput(InputsType input) throws
	 * MalformedURLException {
	 *
	 * URL url = new
	 * URL(this.getWsdlPath("SelectMDMInfoFromMDMByConditon.wsdl"));
	 * SelectMDMInfoFromMDMByConditon_Service service = new
	 * SelectMDMInfoFromMDMByConditon_Service( url);
	 *
	 * SelectMDMInfoFromMDMByConditon soap =
	 * service.getSelectMDMInfoFromMDMByConditonSOAP(); return
	 * soap.selectMDMInfoFromMDMByConditonOP(input); }
	 *
	 *//**
   * 根据订单来源编号，获取来源名称
   *
   * @param source
   * @return
   */
  private String getSourceName(String source) {
    if (source.equals("1")) {
      return "商城订单";
    } else if (source.equals("CGRK")) {
      return "商城采购订单";
    } else if (source.equals("TBSC")) {
      return "淘宝海尔官方旗舰店";
    } else if (source.equals("SHSC")) {
      return "淘宝海尔生活家电旗舰店";
    } else if (source.equals("TOPBUYBANG")) {
      return "淘宝海尔买帮专卖店";
    } else if (source.equals("TOPBOILER")) {
      return "淘宝海尔热水器专卖店";
    } else if (source.equals("TOPSHJD")) {
      return "淘宝海尔生活电器专卖店";
    } else if (source.equals("TOPMOBILE")) {
      return "淘宝海尔手机专卖店";
    } else if (source.equals("TONGSHUAI")) {
      return "统帅日日顺乐家专卖店";
    } else if (source.equals("CCBSC")) {
      return "企业订单_建行龙卡商城订单";
    } else if (source.equals("CCBSR")) {
      return "企业订单_建行善融商城订单";
    } else if (source.equals("95533")) {
      return "企业内购-建行";
    } else if (source.equals("CORPORATE")) {
      return "企业订单";
    } else if (source.equals("CORPORATE_SJMG")) {
      return "企业订单_四季沐歌";
    } else if (source.equals("112")) {
      return "商城订单_海尔地产";
    } else if (source.equals("YIHAODIAN")) {
      return "企业订单_1号店订单";
    } else if (source.equals("DALOU")) {
      return "企业订单_大楼订单";
    } else if (source.equals("113")) {
      return "企业订单_内购";
    } else if (source.equals("114")) {
      return "企业订单_集采";
    } else if (source.equals("115")) {
      return "企业订单_B2B";
    } else if (source.equals("19")) {
      return "充值订单";
    } else if (source.equals("TOPFENXIAO")) {
      return "淘宝海尔官方旗舰店分销平台";
    } else {
      return "";
    }
  }

  /**
   * 获取wsdl file路径
   *
   * @param wsdlFile
   * @return
   */
	/*
	 * private String getWsdlPath(String wsdlFile) { return
	 * help.getWSDLPATH(wsdlFile); // String path = "file:" +
	 * this.getClass().getResource("/wsdl/" + wsdlFile).getPath(); // if
	 * (log.isDebugEnabled()) // log.debug("wsdl path:" + path); // return path;
	 * }
	 *
	 * public void setEisInterfaceDataLogService(EisEisInterfaceDataLogService
	 * EisInterfaceDataLogService) { this.EisInterfaceDataLogService =
	 * EisInterfaceDataLogService; }
	 *
	 * public void setLesStockSyncsDao(LesStockSyncsDao lesStockSyncsDao) {
	 * this.lesStockSyncsDao = lesStockSyncsDao; }
	 *
	 * public EisExternalSkuDao getEisExternalSkuDao() { return
	 * eisExternalSkuDao; }
	 *
	 * public void setEisExternalSkuDao(EisExternalSkuDao eisExternalSkuDao) {
	 * this.eisExternalSkuDao = eisExternalSkuDao; }
	 *
	 *//**
   *
   * @param startDate
   *            订单订购开始时间(格式：YYYY-MM-DD)
   * @param endDate
   *            订单订购结束时间(格式：YYYY-MM-DD)
   * @param currentPage
   *            当前页。结果集每次返回最多2000条(格式：正整数)
   * @return
   */
	/*
	 * public EcommerceOrderResult getOmsOnorder(String startDate, String
	 * endDate, String currentPage) { // 要记录接口数据日志 EisInterfaceDataLog dataLog =
	 * new EisInterfaceDataLog(); dataLog.setForeignKey(startDate + "_" +
	 * endDate + "_" + currentPage);
	 * dataLog.setInterfaceCode(EisInterfaceDataLog.
	 * INTERFACE_CODE_GET_OMS_ON_ORDER);
	 *
	 * Long startTime = System.currentTimeMillis(); GetEcommerceOrder input =
	 * new GetEcommerceOrder(); input.setStartDate(startDate);
	 * input.setEndDate(endDate); ; input.setCurrentPage(currentPage);
	 *
	 * dataLog.setRequestData(new Gson().toJson(input));
	 * dataLog.setRequestTime(new Date());
	 *
	 * //访问接口 EcommerceOrderResult output = null; try { URL url = new
	 * URL(this.getWsdlPath("EcommerceOrder.wsdl"));
	 * EcommerceOrderServiceService service = new
	 * EcommerceOrderServiceService(url); EcommerceOrderService soap =
	 * service.getEcommerceOrderServicePort(); output =
	 * soap.getEcommerceOrder(startDate, endDate, currentPage); } catch
	 * (Exception e) { log.error("从OMS获取在途订单信息时，发生未知异常", e);
	 * dataLog.setErrorMessage("未知异常：" + e.getMessage()); }
	 *
	 * //记录日志 dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
	 * dataLog.setResponseData(new Gson().toJson(output));
	 * dataLog.setResponseTime(new Date()); if (output != null) {
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS); }
	 * else {
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR); }
	 * try { EisInterfaceDataLogService.insert(dataLog); } catch (Exception ex)
	 * { log.error("记录接口日志时，发生未知异常：" + ex.getMessage(), ex); } return output; }
	 *
	 *//**
   * 传递用于录入费用的调拨单信息到LES
   *
   * @param line
   *            调拨单
   * @param kunag
   *            调出中心
   * @param kunnr
   *            调入中心
   * @return
   */
	/*
	 * public boolean transferLineToLesForFeeInput(InvTransferLine line, String
	 * kunag, String kunnr) { INPUT intype =
	 * this.getTransferLineToLesForFeeInput(line, kunag, kunnr);
	 * EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
	 * dataLog.setForeignKey(line.getLineNum());
	 * dataLog.setInterfaceCode(EisInterfaceDataLog.
	 * INTERFACE_CODE_EXCHANGE_GOODS_TO_LES); dataLog.setRequestData(new
	 * Gson().toJson(intype)); dataLog.setRequestTime(new Date()); Long
	 * startTime = System.currentTimeMillis();
	 *
	 * OUTPUT output = null; try { output =
	 * this.getTransferLineToLesForFeeOutput(intype); } catch (Exception e) {
	 * log.error("传递用于录入费用的调拨单信息到LES时发生未知异常", e);
	 * dataLog.setErrorMessage("未知异常：" + e.getMessage()); }
	 *
	 * try { Gson gson = new Gson(); String outputStr = gson.toJson(output);
	 * dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
	 * dataLog.setResponseData(outputStr); dataLog.setResponseTime(new Date());
	 * if (output == null) {
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
	 * throw new RuntimeException("传递用于录入费用的调拨单信息到LES时发生未知异常"); } else { if
	 * (!"S".equalsIgnoreCase(output.getFLAG())) {
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
	 * dataLog.setErrorMessage(output.getMESSAGE()); throw new
	 * RuntimeException(output.getMESSAGE()); } else {
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS); }
	 * } return true; } catch (Exception e) { log.error("传递用于录入费用的调拨单信息到LES失败",
	 * e); return false; } finally { try {
	 * EisInterfaceDataLogService.insert(dataLog); } catch (Exception ex) {
	 * log.error("记录接口日志时，发生未知异常：" + ex.getMessage(), ex); } } }
	 *
	 *//**
   * 转换内部SKU为R码
   *
   * @param sku
   * @param type
   *            外部sku类型
   * @return
   */
	/*
	 * public String convertToExternalSku(String sku, String type) {
	 * EisExternalSku by6 = eisExternalSkuDao.getBySkuType(sku,
	 * EisExternalSku.TYPE_R); if (by6 == null) return sku; return
	 * by6.getExternalSku(); }
	 *
	 * private OUTPUT getTransferLineToLesForFeeOutput(INPUT input) throws
	 * MalformedURLException { URL url = new
	 * URL(this.getWsdlPath("ExchangeGoodsToLES.WSDL"));
	 * ExchangeGoodsToLES_Service service = new ExchangeGoodsToLES_Service(url);
	 *
	 * ExchangeGoodsToLES soap = service.getExchangeGoodsToLESSOAP(); return
	 * soap.exchangeGoodsToLES(input); }
	 *
	 * private INPUT getTransferLineToLesForFeeInput(InvTransferLine line,
	 * String kunag, String kunnr) { INPUT input = new INPUT();
	 * input.setADD1(""); input.setADD2(""); input.setARKTX(line.getItemName());
	 * Date createTime = line.getCreateTime(); if (createTime == null) {
	 * createTime = new Date(); } input.setAUDAT(new
	 * SimpleDateFormat("yyyy-MM-dd").format(createTime)); input.setAUTIM(new
	 * SimpleDateFormat("HH:mm:ss").format(createTime));
	 * input.setBSTNK(line.getLineNum()); input.setKUNAG(kunag);
	 * input.setKUNNR(kunnr); input.setKWMENG(new
	 * BigDecimal(line.getTransferQty())); input.setLGORT(line.getSecFrom());
	 * input.setMATNR(line.getItemCode()); input.setMEINS("TAI");
	 * input.setPOSNR("10"); return input; }
	 *
	 *//**
   * 调用OMS创建订单
   *
   * @param orderProduct
   *            网单
   * @param org
   *            销售组织
   * @param productList
   *            产品组
   * @param sendCode
   *            送达方编码
   * @param haierSendCode
   *            海尔送达方编码
   * @param lastSendDate
   *            要求最晚到货日期
   * @return
   */
	/*
	 * public boolean createOrderToOms(OrderProducts orderProduct, String org,
	 * String productList, String unit, String sendCode, String haierSendCode,
	 * String lastSendDate, String xmlStr, String urlStr) { String changeLog =
	 * "推送订单至OMS"; String remark = "";
	 *
	 * // 要记录接口数据日志 EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
	 * dataLog.setForeignKey(orderProduct.getCOrderSn());
	 * dataLog.setInterfaceCode(EisInterfaceDataLog.
	 * INTERFACE_CODE_CREATE_ORDER_TO_OMS);
	 *
	 * ServiceResult<Orders> orderResult =
	 * getOrderService().getOrder(orderProduct.getOrderId()); Orders order =
	 * null; //用户姓名 String consignee = ""; //联系电话 String mobile = ""; //地址
	 * String addressRegionName = ""; if (orderResult.getSuccess() && null !=
	 * orderResult.getResult()) { order = orderResult.getResult(); consignee =
	 * order.getConsignee(); if (isLength(consignee) > 45) { consignee =
	 * consignee.substring(0, 15); } mobile = order.getMobile(); if
	 * (isLength(mobile) > 20) { mobile = mobile.substring(0, 20); }
	 * addressRegionName = order.getRegionName() + " " + order.getAddress(); if
	 * (isLength(addressRegionName) > 120) { addressRegionName =
	 * addressRegionName.substring(0, 40); } }
	 *
	 * Long startTime = System.currentTimeMillis(); //format xml内容 //客户采购单号(网单号)
	 * String bstkd = orderProduct.getCOrderSn(); //创建时间 String creationDateStr
	 * = new SimpleDateFormat("yyyy-MM-dd").format(new Date()); String
	 * creationDate = creationDateStr + "T00:00:00"; //日日顺送达方 String kunnrSong =
	 * sendCode; //客户订单数 String kwmeng = orderProduct.getNumber().toString(); //
	 * //库存地点 // String lgort = orderProduct.getSCode().substring(0, 2) + "R2";
	 * //客户订单数 String matnr = orderProduct.getSku(); //海尔送达方 String regieSoog =
	 * haierSendCode; //要求最晚到货日期 String revDate = lastSendDate; String
	 * revDateStr = lastSendDate.substring(0, 10); //产品组 String spart =
	 * productList; //日日顺库存地点代码 String storage =
	 * orderProduct.getSCode().substring(0, 2) + "R2"; //销售组织 String vkorg =
	 * org; xmlStr = MessageFormat.format(xmlStr, bstkd, creationDate,
	 * creationDateStr, kunnrSong, kwmeng, storage, matnr, regieSoog, revDate,
	 * revDateStr, spart, vkorg, unit, consignee, mobile, addressRegionName);
	 * dataLog.setRequestData(xmlStr); dataLog.setRequestTime(new Date());
	 *
	 * try { //调用OMS接口 Map<String, String> resultMap =
	 * this.callAndGetResult(xmlStr, urlStr);
	 *
	 * //记录日志 dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
	 * dataLog.setResponseData(new Gson().toJson(resultMap));
	 * dataLog.setResponseTime(new Date()); if (!resultMap.isEmpty()) { if
	 * (!"S".equalsIgnoreCase(resultMap.get("messageCode"))) {
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
	 * dataLog.setErrorMessage(resultMap.get("messageDetail")); remark =
	 * dataLog.getErrorMessage(); return false; } else {
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
	 * remark = "推送OMS成功,单号:" + resultMap.get("FIELD2"); //更新网单日日单状态和OMS单号
	 * orderProduct.setPdOrderStatus(OrderProducts.RRSSTATUS_PRODUCTION);
	 * orderProduct.setOmsOrderSn(resultMap.get("FIELD2"));
	 * this.getOrderService().updateRRSById(orderProduct); return true; } } else
	 * { dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
	 * remark = "推送OMS失败"; return false; } } catch (Exception e) {
	 * log.error("创建订单到OMS时，发生未知异常", e); dataLog.setResponseTime(new Date());
	 * dataLog.setResponseData("can not connect");
	 * dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
	 * dataLog.setCreateTime(new Date());
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
	 * dataLog.setErrorMessage("未知异常：" + e.getMessage()); remark = "推送OMS失败";
	 * return false; } finally { try {
	 * EisInterfaceDataLogService.insert(dataLog); this.insertOperateLog(null,
	 * orderProduct, null, changeLog, remark); } catch (Exception ex) {
	 * log.error("记录接口日志时，发生未知异常：" + ex.getMessage(), ex); } } }
	 *
	 *//**
   * 请求基地库存
   *
   * @param sku
   * @param factory
   * @param secCode
   * @return
   */
	/*
	 * public Holder<List<ZSDINTSDSSTOCKPUT>> getJiDiKuCunFromGvs(String sku,
	 * String factory, String secCode) {
	 *
	 * Holder<List<ZSDINTSDSSTOCKPUT>> tOUTPUT = new
	 * Holder<List<ZSDINTSDSSTOCKPUT>>(); Holder<String> message = new
	 * Holder<String>(); Holder<String> flag = new Holder<String>();
	 * Holder<String> faultDetail = new Holder<String>(); //开启日志记录
	 * EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
	 * dataLog.setInterfaceCode(EisInterfaceDataLog.
	 * INTERFACE_CODE_GET_STOCK_JIDI_FROM_GVS); //启动日志记录时间 Long startTime =
	 * System.currentTimeMillis(); dataLog.setRequestTime(new Date()); try {
	 * //记录日志请求参数 Map<String, String> parameters = new HashMap<String,
	 * String>(); parameters.put("MATNR", sku); parameters.put("WERKS",
	 * factory); parameters.put("LGORT", secCode);
	 * dataLog.setRequestData(JsonUtil.toJson(parameters));
	 * dataLog.setForeignKey(sku + "&" + secCode);
	 *
	 * //设置请求参数 List<ZSDINTSDSSTOCKPUT> tINPUT = new
	 * ArrayList<ZSDINTSDSSTOCKPUT>(); ZSDINTSDSSTOCKPUT stockPut = new
	 * ZSDINTSDSSTOCKPUT(); stockPut.setMATNR(sku); stockPut.setWERKS(factory);
	 * stockPut.setLGORT(secCode); tINPUT.add(stockPut); //请求库存 URL url = new
	 * URL(this.getWsdlPath("QueryBaseGroundStockFromGVS.wsdl"));
	 * QueryBaseGroundStockFromGVS_Service service = new
	 * QueryBaseGroundStockFromGVS_Service( url); QueryBaseGroundStockFromGVS
	 * soap = service.getQueryBaseGroundStockFromGVSSOAP();
	 *
	 * soap.queryBaseGroundStockFromGVS("EHAIER", tINPUT, tOUTPUT, message,
	 * flag, faultDetail); dataLog.setResponseData(JsonUtil.toJson(tOUTPUT));
	 * dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
	 * dataLog.setResponseTime(new Date());
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS); }
	 * catch (Exception e) { log.error("getJiDiKuCunFromGvs:请求基地库存失败,sku=" + sku
	 * + ", secCode=" + secCode + ", factory=" + factory);
	 * dataLog.setResponseTime(new Date());
	 * dataLog.setResponseData(JsonUtil.toJson(tOUTPUT));
	 * dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
	 * dataLog.setCreateTime(new Date());
	 * dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
	 * dataLog.setErrorMessage("getJiDiKuCunFromGvs:请求基地库存失败,sku=" + sku +
	 * ", secCode=" + secCode + ", factory=" + factory + e.getMessage());
	 * e.printStackTrace(); } EisInterfaceDataLogService.insert(dataLog); return
	 * tOUTPUT;
	 *
	 * }
	 *
	 * private Map<String, String> callAndGetResult(String xmlStr, String
	 * urlStr) throws Exception { URL url = new URL(urlStr +
	 * "/EAI/RoutingProxyService/EAI_SOAP_ServiceRoot?INT_CODE=CRM_INT_OMS_3");
	 * // URL url = new URL(urlStr + "/EAI/RoutingProxyService/OMS"); byte[]
	 * data = xmlStr.getBytes(); HttpURLConnection httpConn =
	 * (HttpURLConnection) url.openConnection();
	 * httpConn.setRequestProperty("Content-Length",
	 * String.valueOf(data.length)); httpConn.setRequestProperty("Content-Type",
	 * "text/xml; charset=utf-8"); httpConn.setRequestMethod("POST");
	 * httpConn.setDoOutput(true); httpConn.setDoInput(true); OutputStream out =
	 * httpConn.getOutputStream(); out.write(data); out.close(); byte[] datas =
	 * this.readInputStream(httpConn.getInputStream()); String result = new
	 * String(datas); Document doc = DocumentHelper.parseText(result); Element
	 * el = (Element) doc.getRootElement().elements().get(1);//Body el =
	 * (Element) el.elements().get(0);//createRrsOrderResponse el = (Element)
	 * el.elements().get(0);//result15 el = (Element)
	 * el.elements().get(0);//Result15
	 *
	 * @SuppressWarnings("unchecked") List<Element> list = el.elements();
	 * Map<String, String> map = new HashMap<String, String>(); for (Element
	 * element : list) { map.put(element.getName(), element.getText()); } return
	 * map; }
	 *
	 * private byte[] readInputStream(InputStream inStream) throws Exception {
	 * ByteArrayOutputStream outStream = new ByteArrayOutputStream(); byte[]
	 * data = null; try { byte[] buffer = new byte[1024]; int len = 0; while
	 * ((len = inStream.read(buffer)) != -1) { outStream.write(buffer, 0, len);
	 * } data = outStream.toByteArray();//二进制数据 } finally { if(outStream!=null){
	 * outStream.close(); } if(inStream!=null){ inStream.close(); } } return
	 * data; }
	 *
	 * private void insertOperateLog(Orders order, OrderProducts orderProduct,
	 * String operator, String changeLog, String remark) { OrderOperateLogs log
	 * = new OrderOperateLogs(); log.setChangeLog(StringUtil.isEmpty(changeLog)
	 * ? "" : changeLog); log.setNetPointId(orderProduct.getNetPointId());
	 * log.setOperator(StringUtil.isEmpty(operator) ? "系统" : operator);
	 * log.setOrderId(orderProduct.getOrderId());
	 * log.setOrderProductId(orderProduct.getId()); log.setPaymentStatus(null ==
	 * order ? 0 : order.getPaymentStatus());
	 * log.setRemark(StringUtil.isEmpty(remark) ? "" : remark);
	 * log.setSiteId(1); log.setStatus(orderProduct.getStatus());
	 * this.getOrderService().insertOperateLog(log); }
	 *
	 *//**
   * 返回字符串长度
   *
   * @param o
   * @return
   *//*
		 * private int isLength(Object o) { try { if (null == o) { return 0; }
		 * else { return o.toString().trim().getBytes("utf-8").length; } } catch
		 * (UnsupportedEncodingException e) {
		 * log.error("[EAIHandler_isLength]汉字转化时发生未知异常:", e); return 0; } }
		 */
}