package com.haier.svc.api.controller.purchase;

import com.alibaba.fastjson.JSONObject;
import com.haier.afterSale.service.ItemServiceNew;
import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.ConvertUtil;
import com.haier.common.util.DateUtil;
import com.haier.common.util.StringUtil;
import com.haier.purchase.data.model.ProduceDailyPurchaseItem;
import com.haier.purchase.data.model.PurchaseItem;
import com.haier.purchase.data.model.PurchaseOrder;
import com.haier.purchase.data.model.PurchaseOrderQueueWrapper;
import com.haier.purchase.data.service.PurchaseItemService;
import com.haier.purchase.data.service.PurchaseOrderService;
import com.haier.shop.model.ItemBase;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.service.StockCommonService;
import com.haier.svc.api.controller.util.ExcelExportUtil;
import com.haier.svc.api.controller.util.ExcelReader;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.controller.util.VelocityUtil;
import com.haier.svc.api.form.PurchaseOrderForm;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.PaperSize;
import jxl.format.UnderlineStyle;
import jxl.write.DateFormat;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 采购订单管理功能
 */
@Controller
@RequestMapping(value = "/purchaseOrder")
public class PurchaseOrderController {

  @Autowired
  private StockCommonService stockCommonService;
  @Autowired
  private ItemServiceNew itemService;
  @Autowired
  private PurchaseOrderService purchaseOrderService;
  @Autowired
  private PurchaseItemService purchaseItemService;

  private static org.apache.log4j.Logger logger = org.apache.log4j.LogManager
      .getLogger(PurchaseOrderController.class);

  private static final List<String> ITEM_PROPERTIES = Arrays
      .asList(new String[]{"10", "21", "22", "40", "30", "90"});

  private static final String checkStr = "订单来源号,来源渠道,供应商,售达方,送达方,物料号,物料描述,商品品牌,订单数量,单价,总价,库存地点,批次,收货人姓名,收货人移动电话,收货人固定电话,收货人所在省,收货人所在市,收货人所在县/区,收货地址";

  @RequestMapping(value = {"/importPurchaseOrder"}, method = {RequestMethod.GET})
  public String importPurchaseOrder() {
    return "purchase/importPurchaseOrder";
  }

  /**
   * 下载模板
   */
  @RequestMapping(value = {"/downloadInOutTemplate"})
  void downloadTemplate(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    String fileName = "采购订单模板";
    String sheetName = "采购订单模板";
    ExcelExportUtil.downloadDataTemplate(null, request, response,
        fileName, sheetName, checkStr);
  }

  @RequestMapping(value = {"/importPurchaseOrderData"})
  @ResponseBody
  public HttpJsonResult<Map<String, Object>> importPurchaseData(HttpServletRequest request,
      HttpServletResponse response, Map<String, Object> modelMap) {
    HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
    // 转型为MultipartHttpRequest
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

    // 获得文件
    MultipartFile file = multipartRequest.getFile("file");
    if (file == null) {
      result.setMessage("没有选择导入文件，请选择导入文件后再点击导入操作！");
      return result;
    }

    String fileName = file.getOriginalFilename();
    int index = fileName.lastIndexOf(".");
    String fileExtName = fileName.substring(index + 1);
    if (!fileExtName.equalsIgnoreCase("xls")) {
      result.setMessage("选择导入文件扩展名必须为xls!");
      return result;
    }
    int count = 0;
    int nullRow = 0;
    try {
      List<String[]> list = ExcelReader.readExcel(file.getInputStream(), 1);
      if (list.size() <= 1) {
        result.setMessage("很抱歉！你导入的Excel没有数据记录，请查看重新整理导入！");
        return result;
      }
      //验证模板表头信息
      String[] firstLineData = list.get(0);
      boolean flag = this.checkDataTemplate(firstLineData, checkStr);
      if (!flag) {
        result.setMessage("很抱歉！你导入的Excel数据格式与系统模板格式存在差异，请下载模板后重新导入！");
        return result;
      }

      Map<String, PurchaseOrderForm> map = new LinkedHashMap<String, PurchaseOrderForm>();
      String infoMsg = "";

      Set<String> channels = getChannels();

      Date now = new Date();
      //读取数据
      for (int i = 1; i < list.size(); i++) {
        String[] str = list.get(i);
        String sourceSn = StringUtil.nullSafeString(str[0]).trim();
        if (sourceSn == null) {
          sourceSn = "";
        }
        String type = "ZBCR";
        String channel = StringUtil.nullSafeString(str[1].trim().toUpperCase());
        String suplier = StringUtil.nullSafeString(str[2]).trim();
        String soldTo = StringUtil.nullSafeString(str[3]).trim();
        String deliveryTo = StringUtil.nullSafeString(str[4]).trim();
        String sku = StringUtil.nullSafeString(str[5]).trim().toUpperCase();
        String productType = StringUtil.nullSafeString(str[6].trim());
        String brand = StringUtil.nullSafeString(str[7]).trim();
        int poQty = ConvertUtil.toInt(StringUtil.nullSafeString(str[8]).trim(), 0);
        String priceStr = StringUtil.nullSafeString(str[9]).trim();
        String amountStr = StringUtil.nullSafeString(str[10]).trim();
        BigDecimal price = ConvertUtil.toDecimal(priceStr, new BigDecimal(0));
        BigDecimal amount = ConvertUtil.toDecimal(amountStr, new BigDecimal(0));
        String unit = "TAI";
        String secCode = StringUtil.nullSafeString(str[11]).trim();
        String itemType = StringUtil.nullSafeString(str[12]).trim();
        String sign = "1";
        BigDecimal collect = new BigDecimal(0);
        String receiver = StringUtil.nullSafeString(str[13]).trim();
        String mobile = StringUtil.nullSafeString(str[14]).trim();
        String telephone = StringUtil.nullSafeString(str[15]).trim();
        String province = StringUtil.nullSafeString(str[16]).trim();
        String city = StringUtil.nullSafeString(str[17]).trim();
        String county = StringUtil.nullSafeString(str[18]).trim();
        String address = StringUtil.nullSafeString(str[19]).trim();
        String payStatus = "P1";

        boolean isAllNull = StringUtil.isEmpty(sourceSn, true)
            && StringUtil.isEmpty(productType, true)
            && StringUtil.isEmpty(channel, true)
            && StringUtil.isEmpty(suplier, true)
            && StringUtil.isEmpty(soldTo, true)
            && StringUtil.isEmpty(deliveryTo, true)
            && StringUtil.isEmpty(sku, true)
            && StringUtil.isEmpty(brand, true)
            && StringUtil.isEmpty(secCode, true)
            && StringUtil.isEmpty(itemType, true)
            && StringUtil.isEmpty(receiver, true)
            && StringUtil.isEmpty(mobile, true)
            && StringUtil.isEmpty(telephone, true)
            && StringUtil.isEmpty(province, true)
            && StringUtil.isEmpty(city, true)
            && StringUtil.isEmpty(county, true)
            && StringUtil.isEmpty(address, true);
        if (isAllNull) {
          nullRow++;
          continue;
        }
        if (StringUtil.isEmpty(productType, true)) {
          result.setMessage("很抱歉！你导入的Excel数据,物料描述不能为空! 请核查后重新导入！");
          return result;
        }
        if (StringUtil.isEmpty(type, true)) {
          result.setMessage("很抱歉！你导入的Excel数据,订单类型不能为空! 请核查后重新导入！");
          return result;
        }
        if (StringUtil.isEmpty(channel, true)) {
          result.setMessage("很抱歉！你导入的Excel数据,来源渠道不能为空! 请核查后重新导入！");
          return result;
        } else if (!channels.contains(channel)) {
          result.setMessage("很抱歉！你导入的Excel数据,来源渠道不正确! 请核查后重新导入！");
          return result;
        }
        if (StringUtil.isEmpty(suplier, true)) {
          result.setMessage("很抱歉！你导入的Excel数据,供应商不能为空! 请核查后重新导入！");
          return result;
        }
        if (StringUtil.isEmpty(soldTo, true)) {
          result.setMessage("很抱歉！你导入的Excel数据,售达方不能为空! 请核查后重新导入！");
          return result;
        }
        if (StringUtil.isEmpty(deliveryTo, true)) {
          result.setMessage("很抱歉！你导入的Excel数据,送达方不能为空! 请核查后重新导入！");
          return result;
        }
        if (StringUtil.isEmpty(sku, true)) {
          result.setMessage("很抱歉！你导入的Excel数据,物料号不能为空! 请核查后重新导入！");
          return result;
        } else if (!checkSKU(sku)) {
          result.setMessage("很抱歉！你导入的Excel数据，物料号【" + sku + "】无法识别！请检查后重新导入！");
          return result;
        }
        if (StringUtil.isEmpty(brand, true)) {
          result.setMessage("很抱歉！你导入的Excel数据,商品品牌不能为空! 请核查后重新导入！");
          return result;
        }
        if (StringUtil.isEmpty(secCode, true)) {
          result.setMessage("很抱歉！你导入的Excel数据,库存地点不能为空! 请核查后重新导入！");
          return result;
        }
        if (StringUtil.isEmpty(itemType, true)) {
          result.setMessage("很抱歉！你导入的Excel数据,批次不能为空! 请核查后重新导入！");
          return result;
        } else if (!ITEM_PROPERTIES.contains(itemType)) {
          result.setMessage("很抱歉！你导入的Excel数据，不支持的批次！请检查后重新导入！");
          return result;
        }
        if (StringUtil.isEmpty(sign, true)) {
          result.setMessage("很抱歉！你导入的Excel数据,特殊标记不能为空! 请核查后重新导入！");
          return result;
        }
        if (StringUtil.isEmpty(receiver, true)) {
          result.setMessage("很抱歉！你导入的Excel数据,收货人姓名不能为空! 请核查后重新导入！");
          return result;
        }
        if (StringUtil.isEmpty(mobile, true)) {
          result.setMessage("很抱歉！你导入的Excel数据,收货人移动电话不能为空! 请核查后重新导入！");
          return result;
        }
        if (StringUtil.isEmpty(telephone, true)) {
          result.setMessage("很抱歉！你导入的Excel数据,收货人固定电话不能为空! 请核查后重新导入！");
          return result;
        }
        if (StringUtil.isEmpty(province, true)) {
          result.setMessage("很抱歉！你导入的Excel数据,收货人所在省不能为空! 请核查后重新导入！");
          return result;
        }

        if (StringUtil.isEmpty(city, true)) {
          result.setMessage("很抱歉！你导入的Excel数据,收货人所在市不能为空! 请核查后重新导入！");
          return result;
        }
        if (StringUtil.isEmpty(county, true)) {
          result.setMessage("很抱歉！你导入的Excel数据,收货人所在县/区不能为空! 请核查后重新导入！");
          return result;
        }
        if (StringUtil.isEmpty(address, true)) {
          result.setMessage("很抱歉！你导入的Excel数据,收货地址不能为空! 请核查后重新导入！");
          return result;
        }

        if (StringUtil.isEmpty(unit, true)) {
          unit = "TAI";
        }

        //验证记录是否已经创建，如果已经存在则不作处理，这样防止重复导入
        if (!StringUtil.isEmpty(sourceSn)) {
          ServiceResult<Boolean> isExistResult = purchaseOrderService
              .isExistPurchaseItem(sourceSn, sku, secCode);
          if (isExistResult.getSuccess() && isExistResult.getResult()) {
            count++;
            continue;
          }
        }

        Date poTime = now;
        Date theLatestArrivalTime = calTheLatestArrivalTime(poTime);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setSoldTo(soldTo);
        purchaseOrder.setChannelCode(channel);
        purchaseOrder.setSuplier(suplier);
        purchaseOrder.setDeliveryTo(deliveryTo);
        purchaseOrder.setAmount(amount);
        purchaseOrder.setType(type);
        purchaseOrder.setMobile(mobile);
        purchaseOrder.setTelephone(telephone);
        purchaseOrder.setReceiver(receiver);
        purchaseOrder.setProvince(province);
        purchaseOrder.setCity(city);
        purchaseOrder.setCounty(county);
        purchaseOrder.setAddress(address);
        purchaseOrder.setPoType("3PL");

        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setSourceSn(sourceSn);
        purchaseItem.setSku(sku);
        purchaseItem.setBrand(brand);
        purchaseItem.setPoQty(poQty);
        purchaseItem.setUnit(unit);
        purchaseItem.setPrice(price);
        purchaseItem.setPoItemAmount(amount);
        purchaseItem.setSecCode(secCode);
        purchaseItem.setCollect(collect);
        purchaseItem.setItemType(itemType);
        purchaseItem.setSign(sign);
        purchaseItem.setPayStatus(payStatus);
        purchaseItem.setPoTime(poTime);
        purchaseItem.setLatestRevDate(theLatestArrivalTime);
        purchaseItem.setProductTypeName(productType);

        String key = secCode + "#" + channel;
        if (map.containsKey(key)) {
          PurchaseOrderForm purchaseOrderForm = map.get(key);
          PurchaseOrder purchaseOrder_ = purchaseOrderForm.getPurchaseOrder();
          BigDecimal amount_ = purchaseOrder_.getAmount();
          BigDecimal amount_new = amount_.add(amount);
          purchaseOrder_.setAmount(amount_new);
          purchaseOrderForm.setPurchaseOrder(purchaseOrder_);

          List<PurchaseItem> pItemList = purchaseOrderForm.getPurchaseItemList();
          pItemList.add(purchaseItem);
          purchaseOrderForm.setPurchaseItemList(pItemList);
        } else {
          PurchaseOrderForm purchaseOrderForm = new PurchaseOrderForm();
          purchaseOrderForm.setPurchaseOrder(purchaseOrder);
          List<PurchaseItem> newPurchaseItemList = new ArrayList<PurchaseItem>();
          newPurchaseItemList.add(purchaseItem);
          purchaseOrderForm.setPurchaseItemList(newPurchaseItemList);
          map.put(key, purchaseOrderForm);
        }
      }

      if (!StringUtil.isEmpty(infoMsg, true)) {
        result.setMessage(infoMsg);
        return result;
      }

      for (Entry<String, PurchaseOrderForm> entry : map.entrySet()) {
        PurchaseOrderForm purchaseOrderForm = entry.getValue();
        purchaseOrderService.createPurchaseOrder(purchaseOrderForm.getPurchaseOrder(),
            purchaseOrderForm.getPurchaseItemList());
      }
      modelMap.put("total", list.size() - 1 - nullRow);//总计
      modelMap.put("success", list.size() - count - 1 - nullRow);
      modelMap.put("ignore", count);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("文件导入数据库失败", e);
      result.setMessage("很抱歉！你导入的Excel数据导入失败，请联系技术负责人！");
      return result;
    }
    result.setData(modelMap);
    return result;
  }

  public boolean checkDataTemplate(String[] firstLineData, String checkStr) {
    boolean flag = false;
    StringBuffer sb = new StringBuffer();
    for (String str : firstLineData) {
      if (sb.length() > 0) {
        sb.append(",");
      }
      sb.append(str.trim());
    }
    if (sb.toString().equals(checkStr)) {
      flag = true;
    }
    return flag;
  }

  private Set<String> getChannels() {
    ServiceResult<List<InvStockChannel>> rs = stockCommonService.getChannels();
    if (!rs.getSuccess()) {
      throw new BusinessException("库存服务返回错误：" + rs.getMessage());
    }
    Set<String> channels = new HashSet<String>();
    for (InvStockChannel channel : rs.getResult()) {
      channels.add(channel.getChannelCode());
    }
    return channels;
  }

  private boolean checkSKU(String sku) {
    ServiceResult<ItemBase> result = itemService.getItemBaseBySku(sku);
    if (!result.getSuccess()) {
      return false;
    }
    return result.getResult() != null;
  }

  /**
   * 计算最晚到货时间：当{@code poTime}早于T周周三，则最晚到货时间为T+2周周日，否则为T+3周周日
   */
  private Date calTheLatestArrivalTime(Date poTime) {
    Calendar calendar = Calendar.getInstance(Locale.CHINA);
    //设置每周的开始为周一
    calendar.setFirstDayOfWeek(Calendar.MONDAY);
    calendar.setTime(poTime);
    //T周周三
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
    //早于周三
    if (poTime.before(calendar.getTime())) {
      calendar.add(Calendar.WEEK_OF_YEAR, 2);
    } else {//晚于周二
      calendar.add(Calendar.WEEK_OF_YEAR, 3);
    }
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    return calendar.getTime();
  }

  /**
   * 3PL网单队列管理
   * @return
   */
  @RequestMapping(value = {"/purchaseOrderQueue"}, method = {RequestMethod.GET})
  public String purchaseOrderQueue() {
    return "purchase/purchaseOrderQueue";
  }

  @RequestMapping(value = {"/getPurchaseOrderQueue"})
  @ResponseBody
  public JSONObject getPurchaseOrderQueue(
      @RequestParam(required = true) String startDate,
      @RequestParam(required = true) String endDate,
      @RequestParam(required = false) String poItemNo,
      @RequestParam(required = true) String status,
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer rows) {
    PagerInfo pagerInfo = new PagerInfo(rows, page);
    JSONObject json = new JSONObject();
    try {
      Date startTime = DateUtil.parse(startDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
      Date endTime = DateUtil.parse(endDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
      if (!StringUtil.isEmpty(poItemNo, true)) {
        poItemNo = StringUtil.trim(poItemNo);
      }
      int status_ = ConvertUtil.toInt(status, -2);

      //根据条件查询采购订单明细列表
      ServiceResult<List<PurchaseOrderQueueWrapper>> result = purchaseOrderService
          .findPurchaseOrderQueueByCondition(startTime, endTime, poItemNo, status_, pagerInfo);
      if (result.getSuccess() && result.getResult() != null) {
        json.put("rows", result.getResult());
        json.put("total", Long.valueOf(result.getPager().getRowsCount()));
      }

    } catch (Exception e) {
      json.put("rows", "");
      json.put("total", "");
      logger.error("查询【3PL网单队列】失败", e);
    }
    return json;
  }

  /**
   * 3PL采购订单管理
   * @return
   */
  @RequestMapping(value = { "/purchaseOrder" }, method = { RequestMethod.GET })
  public String purchaseOrderList() {
    return "purchase/purchaseOrder";
  }

  @RequestMapping(value = {"/findPurchaseOrderData"})
  @ResponseBody
  public JSONObject findPurchaseOrderData(
      @RequestParam(required = true) String startDate,
      @RequestParam(required = true) String endDate,
      @RequestParam(required = false) String poNo,
      @RequestParam(required = true) String status,
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer rows) {
    PagerInfo pagerInfo = new PagerInfo(rows, page);
    JSONObject json = new JSONObject();
    try {
      Date startTime = DateUtil.parse(startDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
      Date endTime = DateUtil.parse(endDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");

      if (!StringUtil.isEmpty(poNo, true)) {
        poNo = StringUtil.trim(poNo);
      }
      int status_ = ConvertUtil.toInt(status, 0);
      //根据条件查询采购订单列表
      ServiceResult<List<PurchaseOrder>> result = purchaseOrderService
          .findPurchaseOrder(startTime, endTime, poNo, status_, pagerInfo);
      if (result.getSuccess() && result.getResult() != null) {
        json.put("rows", result.getResult());
        json.put("total", Long.valueOf(result.getPager().getRowsCount()));
      }

    } catch (Exception e) {
      json.put("rows", "");
      json.put("total", "");
      logger.error("查询【3PL采购订单】失败", e);
    }
    return json;
  }

  @RequestMapping(value = { "/confirmPurchaseByPoId" })
  @ResponseBody
  public HttpJsonResult<Boolean> confirmPurchaseByPoId(@RequestParam(required = true)String poId) {
    HttpJsonResult<Boolean> result = new HttpJsonResult<Boolean>();
    ServiceResult<Boolean> results = purchaseOrderService.confirmPurchaseOrder(Integer
        .parseInt(poId));
    if (!results.getSuccess() || !results.getResult()) {
      result.setMessage("确认失败:" + results.getMessage());
    }
    return result;
  }

  @RequestMapping(value = { "/cancelPurchaseByPoId" })
  @ResponseBody
  public HttpJsonResult<Boolean> cancelPurchaseByPoId(@RequestParam(required = true)String poId) {
    HttpJsonResult<Boolean> result = new HttpJsonResult<Boolean>();
    ServiceResult<Boolean> results = purchaseOrderService.cancelPurchaseOrder(Integer
        .parseInt(poId));
    if (!results.getSuccess() || !results.getResult()) {
      result.setMessage("取消失败:" + results.getMessage());
    }
    return result;
  }

  @RequestMapping(value = {"/findPurchaseItemByPoId"})
  @ResponseBody
  public JSONObject findPurchaseItemByPoId(
      @RequestParam(required = true)String poIdStr) {
    JSONObject json = new JSONObject();
    try {
      int poId = ConvertUtil.toInt(poIdStr, 0);
      ServiceResult<List<PurchaseItem>> result = purchaseOrderService
          .findPurchaseItemByPoId(poId);
      if (result.getSuccess() && result.getResult() != null) {
        json.put("rows", result.getResult());
      }
    } catch (Exception e) {
      json.put("rows", "");
      logger.error("查询【采购网单】失败", e);
    }
    return json;
  }


  /**
   * 获取PurchaseOrderStatus
   * @param status
   * @return
   */
  @RequestMapping(value = {"/purchaseOrderStatus"})
  @ResponseBody
  public String purchaseOrderStatus(@RequestParam(required = true)Integer status) {
    VelocityUtil velocityUtil = new VelocityUtil();
    return velocityUtil.getPurchaseOrderStatus(status);
  }

  /**
   * 获取PurchaseItemStatus
   * @param status
   * @return
   */
  @RequestMapping(value = {"/purchaseItemStatus"})
  @ResponseBody
  public String purchaseItemStatus(@RequestParam(required = true)Integer status) {
    VelocityUtil velocityUtil = new VelocityUtil();
    return velocityUtil.getPurchaseItemStatus(status);
  }

  /**
   * 获取PurchaseOrderQueueStatus
   * @param status
   * @return
   */
  @RequestMapping(value = {"/purchaseOrderQueueStatus"})
  @ResponseBody
  public String purchaseOrderQueueStatus(@RequestParam(required = true)Integer status) {
    VelocityUtil velocityUtil = new VelocityUtil();
    return velocityUtil.getPurchaseOrderQueueStatus(status);
  }

  @RequestMapping(value = { "/exportPurchaseOrderData" }, method = { RequestMethod.GET })
  public @ResponseBody
  void exportPurchaseOrderData(@RequestParam(required = true) String startDate,
      @RequestParam(required = true) String endDate,
      @RequestParam(required = true) String status,
      @RequestParam(required = false) String poNo,
      HttpServletResponse response)
      throws Exception {
    Date start_date = DateUtil.parse(startDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
    Date end_date = DateUtil.parse(endDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
    if (!StringUtil.isEmpty(poNo, true)) {
      poNo = StringUtil.trim(poNo);
    }
    int status_ = ConvertUtil.toInt(status, 0);

    String title = "小家电采购订单入库差异报表_" + DateUtil.format(new Date(), "yyyyMMddHHmmss");

    response.reset();
    response.setContentType("application/octet-stream; charset=utf-8");
    response.setHeader("Content-Disposition",
        "attachment; filename=" + URLEncoder.encode(title, "utf-8") + ".xls");
    OutputStream os = response.getOutputStream();
    try {
      //根据条件查询采购订单列表
      PagerInfo pager = new PagerInfo(20000, 1);
      ServiceResult<List<PurchaseOrder>> result = purchaseOrderService.findPurchaseOrder(
          start_date, end_date, poNo, status_, pager);

      List<PurchaseOrderForm> purchaseOrderFormList = new ArrayList<PurchaseOrderForm>();

      if (result.getSuccess() && result.getResult() != null) {
        List<PurchaseOrder> purchaseOrderList = result.getResult();
        if (purchaseOrderList != null && !purchaseOrderList.isEmpty()) {
          for (PurchaseOrder purchaseOrder : purchaseOrderList) {
            PurchaseOrderForm purchaseOrderForm = new PurchaseOrderForm();
            purchaseOrderForm.setPurchaseOrder(purchaseOrder);
            //根据采购订单ID查询采购订单明细列表
            ServiceResult<List<PurchaseItem>> piResult = purchaseOrderService
                .findPurchaseItemByPoId(purchaseOrder.getPoId());
            List<PurchaseItem> piList = piResult.getResult();
            if (piResult.getSuccess() && piList != null) {
              purchaseOrderForm.setPurchaseItemList(piList);
            }
            purchaseOrderFormList.add(purchaseOrderForm);
          }
        }
      }
      if (result.getResult().size() == 0) {
        response.reset();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print("根据当前条件获取数据为空，不导出");
        response.getWriter().flush();
      } else {
        this.exportPurchaseOrder(purchaseOrderFormList, os);
      }

    } catch (Exception e) {
      response.reset();
      response.setContentType("text/html;charset=UTF-8");
      response.getWriter().print("导出excel失败，" + e);
      response.getWriter().flush();
    } finally {
      if (os != null) {
        os.flush();
        os.close();
      }
    }
  }

  public void exportPurchaseOrder(List<PurchaseOrderForm> list, OutputStream os) throws Exception {
    WritableWorkbook book = Workbook.createWorkbook(os);// 创建可以写的book文件对象
    WritableSheet sheet = book.createSheet("差异确认报表", 0);// 在book3.xls中创建一个sheet,名称为'unionName',从第一列开始插入
    sheet.getSettings().setPaperSize(PaperSize.A4);//设置纸张大小
    sheet.getSettings().setFitHeight(297);
    sheet.getSettings().setFitWidth(21);
    //        sheet.getSettings().setHorizontalCentre(true);
    int temp = 0;
    String[] totalHeaders = new String[] { "采购网单号", "订单来源号", "订单日期", "订单时间", "物料号", "型号编码",
        "库位", "批次", "来源渠道", "订单数量", "实际入库", "入库时间", "单价", "金额", "差异", "付款状态" };
    setExcelHeader(sheet, temp, totalHeaders);
    temp++;
    setExcelBodyTotalForOrder(sheet, temp, list);

    // 写入到服务器
    book.write();
    // 一定要关闭，否则不写入
    book.close();
  }

  private void setExcelHeader(WritableSheet sheet, int temp, String[] headers) throws Exception {
    WritableFont font = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD, false,
        UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
    WritableCellFormat format = new WritableCellFormat(font);
    format.setAlignment(Alignment.CENTRE);
    format.setBorder(Border.ALL, BorderLineStyle.THIN);
    for (int col = 0; col < headers.length; col++) {
      Label label = new Label(col, temp, headers[col], format);
      sheet.setColumnView(col, 25);
      sheet.addCell(label);
    }
  }

  private void setExcelBodyTotalForOrder(WritableSheet sheet, int temp,
      List<PurchaseOrderForm> list) throws Exception {

    WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
        UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
    WritableCellFormat textFormat = new WritableCellFormat(font);
    textFormat.setAlignment(Alignment.CENTRE);
    textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

    WritableCellFormat doubleFormat = new WritableCellFormat(font, NumberFormats.FLOAT);
    doubleFormat.setAlignment(Alignment.CENTRE);
    doubleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

    WritableCellFormat intFormat = new WritableCellFormat(font, NumberFormats.INTEGER);
    intFormat.setAlignment(Alignment.CENTRE);
    intFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

    WritableCellFormat dateFormat = new WritableCellFormat(font, new DateFormat("yyyy-MM-dd"));
    dateFormat.setAlignment(Alignment.CENTRE);
    dateFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

    WritableCellFormat timeFormat = new WritableCellFormat(font, new DateFormat("HH:mm:ss"));
    timeFormat.setAlignment(Alignment.CENTRE);
    timeFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

    WritableCellFormat dateTimeFormat = new WritableCellFormat(font, new DateFormat(
        "yyyy-MM-dd HH:mm:ss"));
    dateTimeFormat.setAlignment(Alignment.CENTRE);
    dateTimeFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

    for (PurchaseOrderForm purchaseOrderForm : list) {
      PurchaseOrder purchaseOrder = purchaseOrderForm.getPurchaseOrder();
      List<PurchaseItem> piList = purchaseOrderForm.getPurchaseItemList();
      for (PurchaseItem purchaseItem : piList) {
        //jxl.write.Number(列号,行号 ,内容 )
        // "采购网单号", "订单来源号", "订单日期", "订单时间", "物料号", "型号编码",
        //"库位", "批次", ,"来源渠道", "订单数量", "实际入库", "入库时间", "单价", "金额", "差异", "付款状态"
        sheet.setColumnView(0, 15);
        sheet.addCell(new Label(0, temp, purchaseItem.getPoItemNo(), textFormat));
        sheet.setColumnView(1, 25);
        sheet.addCell(new Label(1, temp, purchaseItem.getSourceSn(), textFormat));
        sheet.setColumnView(2, 25);
        sheet
            .addCell(new jxl.write.DateTime(2, temp, purchaseItem.getPoTime(), dateFormat));
        sheet.setColumnView(3, 15);
        sheet
            .addCell(new jxl.write.DateTime(3, temp, purchaseItem.getPoTime(), timeFormat));
        sheet.setColumnView(4, 15);
        sheet.addCell(new Label(4, temp, purchaseItem.getSku(), textFormat));
        sheet.setColumnView(5, 15);
        sheet.addCell(new Label(5, temp, purchaseItem.getProductTypeName(), textFormat));//型号编码
        sheet.setColumnView(6, 15);
        sheet.addCell(new Label(6, temp, purchaseItem.getSecCode(), textFormat));
        sheet.setColumnView(7, 15);
        sheet.addCell(new Label(7, temp, purchaseItem.getItemType(), textFormat));
        sheet.setColumnView(8, 15);
        sheet.addCell(new Label(8, temp, purchaseOrder.getChannelCode(), textFormat));
        sheet.setColumnView(9, 15);
        sheet.addCell(new jxl.write.Number(9, temp, purchaseItem.getPoQty(), intFormat));
        sheet.setColumnView(10, 15);
        sheet
            .addCell(new jxl.write.Number(10, temp, purchaseItem.getInputQty(), intFormat));
        sheet.setColumnView(11, 15);
        if (purchaseItem.getInputTime() != null) {
          sheet.addCell(new jxl.write.DateTime(11, temp, purchaseItem.getInputTime(),
              dateTimeFormat));
        } else {
          sheet.addCell(new Label(11, temp, "", textFormat));
        }
        sheet.setColumnView(12, 15);
        sheet.addCell(new jxl.write.Number(12, temp, purchaseItem.getPrice().doubleValue(),
            doubleFormat));
        sheet.setColumnView(13, 15);
        sheet.addCell(new jxl.write.Number(13, temp, purchaseItem.getPoItemAmount()
            .doubleValue(), doubleFormat));
        sheet.setColumnView(14, 20);
        sheet.addCell(new Label(14, temp, purchaseItem.getPoQty() == purchaseItem
            .getInputQty() ? "无差异：" : "有差异:采购数量为:" + purchaseItem.getPoQty() + "实际入库数量为:"
            + purchaseItem.getInputQty(), textFormat));
        sheet.setColumnView(15, 20);
        sheet.addCell(new Label(15, temp, purchaseItem.getPayStatus(), textFormat));
        temp++;
      }
    }
  }

  /**
   * 3PL采购网单管理
   * @return
   */
  @RequestMapping(value = { "/purchaseItem" }, method = { RequestMethod.GET })
  public String purchaseItem() {
    return "purchase/purchaseItem";
  }

  @RequestMapping(value = {"/findPurchaseItemData"})
  @ResponseBody
  public JSONObject findPurchaseItemData(
      @RequestParam(required = true) String startDate,
      @RequestParam(required = true) String endDate,
      @RequestParam(required = true) String status,
      @RequestParam(required = false) String poItemNo,
      @RequestParam(required = false) String sourceSn,
      @RequestParam(required = false) String channel,
      @RequestParam(required = false) String supplier,
      @RequestParam(required = false) String sku,
      @RequestParam(required = false) Integer page,
      @RequestParam(required = false) Integer rows) {

    JSONObject json = new JSONObject();
    try {
      Date start_date = DateUtil.parse(startDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
      Date end_date = DateUtil.parse(endDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");

      Date tempDate = DateUtil.add(start_date, Calendar.MONTH, 6);
      if (tempDate.before(end_date)) {
        end_date = tempDate;
      } else {
        end_date = DateUtil.add(end_date, Calendar.DATE, 1);
      }

      if (!StringUtil.isEmpty(poItemNo, true)) {
        poItemNo = StringUtil.trim(poItemNo);
      }
      if (!StringUtil.isEmpty(sourceSn, true)) {
        sourceSn = StringUtil.trim(sourceSn);
      }
      if (!StringUtil.isEmpty(channel)) {
        channel = StringUtil.trim(channel);
      }

      if ("ALL".equals(channel)) {
        channel = null;
      }

      if (!StringUtil.isEmpty(supplier)) {
        supplier = StringUtil.trim(supplier);
      }
      if (!StringUtil.isEmpty(sku)) {
        sku = StringUtil.trim(sku);
      }
      int status_ = ConvertUtil.toInt(status, 0);

      int pageIndex = ConvertUtil.toInt(page, 1);
      int pageSize = ConvertUtil.toInt(rows, 10);
      PagerInfo pager = new PagerInfo(pageSize, pageIndex);
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("startTime", start_date);
      params.put("endTime", end_date);
      params.put("status", status_);
      params.put("poItemNo", poItemNo);
      params.put("sourceSn", sourceSn);
      params.put("channel", channel);
      params.put("supplier", supplier);
      params.put("sku", sku);
      params.put("start", pager.getStart());
      params.put("size", pager.getPageSize());
      //根据条件查询采购订单明细列表
      List<ProduceDailyPurchaseItem> items = purchaseItemService.findPurchaseItems(params);
      pager.setRowsCount(purchaseItemService.getRowCnt());
      json.put("rows", items);
      json.put("total", pager.getRowsCount());

    } catch (Exception e) {
      json.put("rows", "");
      json.put("total", "");
      logger.error("查询【3PL采购网单管理】失败", e);
    }
    return json;
  }

  @RequestMapping(value = {"/getPurchaseOrderData"})
  @ResponseBody
  public JSONObject findPurchaseData(
      @RequestParam(required = true)String poIdStr) {
    JSONObject json = new JSONObject();
    try {
      int poId = ConvertUtil.toInt(poIdStr, 0);
      ServiceResult<PurchaseOrder> result = purchaseOrderService.getPurchaseOrder(poId);
      if (result.getSuccess() && result.getResult() != null) {
        List<PurchaseOrder> list = new ArrayList<>();
        list.add(result.getResult());
        json.put("rows", list);
      }
    } catch (Exception e) {
      json.put("rows", "");
      logger.error("查询【采购订单】失败", e);
    }
    return json;
  }

  @RequestMapping(value = { "/confirmPurchaseItemByPoItemId" })
  @ResponseBody
  public HttpJsonResult<Boolean> confirmPurchaseItemByPoItemId(@RequestParam(required = true)String poItemId) {
    HttpJsonResult<Boolean> result = new HttpJsonResult<Boolean>();
    ServiceResult<Boolean> results = purchaseOrderService.confirmPurchaseItem(Integer
        .parseInt(poItemId));
    if (!results.getSuccess() || !results.getResult()) {
      result.setMessage("确认失败:" + results.getMessage());
    }
    return result;
  }

  @RequestMapping(value = { "/cancelPurchaseItemByPoItemId" })
  @ResponseBody
  public HttpJsonResult<Boolean> cancelPurchaseItemByPoItemId(@RequestParam(required = true)String poItemId) {
    HttpJsonResult<Boolean> result = new HttpJsonResult<Boolean>();
    ServiceResult<Boolean> results = purchaseOrderService.cancelPurchaseItem(Integer
        .parseInt(poItemId));
    if (!results.getSuccess() || !results.getResult()) {
      result.setMessage("取消失败:" + results.getMessage());
    }
    return result;
  }

  @RequestMapping(value = { "/exportPurchaseItemData" }, method = { RequestMethod.GET })
  public @ResponseBody
  void exportPurchaseItemData(@RequestParam(required = true) String startDate,
      @RequestParam(required = true) String endDate,
      @RequestParam(required = true) String status,
      @RequestParam(required = false) String poItemNo,
      @RequestParam(required = false) String sourceSn,
      @RequestParam(required = false) String channel,
      @RequestParam(required = false) String supplier,
      @RequestParam(required = false) String sku,
      HttpServletResponse response)
      throws Exception {
    Date start_date = DateUtil.parse(startDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
    Date end_date = DateUtil.parse(endDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");

    Date tempDate = DateUtil.add(start_date, Calendar.MONTH, 6);
    if (tempDate.before(end_date)) {
      end_date = tempDate;
    } else {
      end_date = DateUtil.add(end_date, Calendar.DATE, 1);
    }

    if (!StringUtil.isEmpty(poItemNo, true)) {
      poItemNo = StringUtil.trim(poItemNo);
    }
    if (!StringUtil.isEmpty(sourceSn, true)) {
      sourceSn = StringUtil.trim(sourceSn);
    }
    if (!StringUtil.isEmpty(channel)) {
      channel = StringUtil.trim(channel);
    }

    if ("ALL".equals(channel)) {
      channel = null;
    }

    if (!StringUtil.isEmpty(supplier)) {
      supplier = StringUtil.trim(supplier);
    }
    if (!StringUtil.isEmpty(sku)) {
      sku = StringUtil.trim(sku);
    }
    int status_ = ConvertUtil.toInt(status, 0);

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("startTime", start_date);
    params.put("endTime", end_date);
    params.put("status", status_);
    params.put("poItemNo", poItemNo);
    params.put("sourceSn", sourceSn);
    params.put("channel", channel);
    params.put("supplier", supplier);
    params.put("sku", sku);
    params.put("start", 0);
    params.put("size", 20000);

    String title = "小家电采购订单入库差异报表_" + DateUtil.format(new Date(), "yyyyMMddHHmmss");

    response.reset();
    response.setContentType("application/octet-stream; charset=utf-8");
    response.setHeader("Content-Disposition",
        "attachment; filename=" + URLEncoder.encode(title, "utf-8") + ".xls");
    OutputStream os = response.getOutputStream();
    try {
      List<ProduceDailyPurchaseItem> items = purchaseItemService.findPurchaseItems(params);
      if (items.size() == 0) {
        response.reset();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().print("根据当前条件获取数据为空，不导出");
        response.getWriter().flush();
      } else {
        this.exportPurchaseItem(items, os);
      }

    } catch (Exception e) {
      response.reset();
      response.setContentType("text/html;charset=UTF-8");
      response.getWriter().print("导出excel失败，" + e);
      response.getWriter().flush();
    } finally {
      if (os != null) {
        os.flush();
        os.close();
      }
    }
  }

  private void exportPurchaseItem(List<ProduceDailyPurchaseItem> list, OutputStream os)
      throws Exception {
    WritableWorkbook book = Workbook.createWorkbook(os);// 创建可以写的book文件对象
    WritableSheet sheet = book.createSheet("差异确认报表", 0);// 在book3.xls中创建一个sheet,名称为'unionName',从第一列开始插入
    sheet.getSettings().setPaperSize(PaperSize.A4);//设置纸张大小
    sheet.getSettings().setFitHeight(297);
    sheet.getSettings().setFitWidth(21);
    //        sheet.getSettings().setHorizontalCentre(true);
    int temp = 0;
    String[] totalHeaders = new String[] { "采购网单号", "订单来源号", "订单日期", "订单时间", "物料号", "型号编码",
        "库位", "批次", "来源渠道", "订单数量", "实际入库", "入库时间", "单价", "金额", "差异", "付款状态" };
    setExcelHeader(sheet, temp, totalHeaders);
    temp++;
    setExcelBodyTotalForItem(sheet, temp, list);
    // 写入到服务器
    book.write();
    // 一定要关闭，否则不写入
    book.close();
  }

  private void setExcelBodyTotalForItem(WritableSheet sheet, int temp,
      List<ProduceDailyPurchaseItem> list) throws Exception {
    WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
        UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
    WritableCellFormat textFormat = new WritableCellFormat(font);
    textFormat.setAlignment(Alignment.CENTRE);
    textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

    WritableCellFormat doubleFormat = new WritableCellFormat(font, NumberFormats.FLOAT);
    doubleFormat.setAlignment(Alignment.CENTRE);
    doubleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

    WritableCellFormat intFormat = new WritableCellFormat(font, NumberFormats.INTEGER);
    intFormat.setAlignment(Alignment.CENTRE);
    intFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

    WritableCellFormat dateFormat = new WritableCellFormat(font, new DateFormat("yyyy-MM-dd"));
    dateFormat.setAlignment(Alignment.CENTRE);
    dateFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

    WritableCellFormat timeFormat = new WritableCellFormat(font, new DateFormat("HH:mm:ss"));
    timeFormat.setAlignment(Alignment.CENTRE);
    timeFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

    WritableCellFormat dateTimeFormat = new WritableCellFormat(font, new DateFormat(
        "yyyy-MM-dd HH:mm:ss"));
    dateTimeFormat.setAlignment(Alignment.CENTRE);
    dateTimeFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

    for (ProduceDailyPurchaseItem purchaseItem : list) {
      int poId = purchaseItem.getPoId();
      ServiceResult<PurchaseOrder> result = purchaseOrderService.getPurchaseOrder(poId);
      PurchaseOrder purchaseOrder = result.getResult();
      //jxl.write.Number(列号,行号 ,内容 )
      // "采购网单号", "订单来源号", "订单日期", "订单时间", "物料号", "型号编码",
      //"库位", "批次", "来源渠道", "订单数量", "实际入库", "入库时间", "单价", "金额", "差异", "付款状态"
      sheet.setColumnView(0, 15);
      sheet.addCell(new Label(0, temp, purchaseItem.getPoItemNo(), textFormat));
      sheet.setColumnView(1, 25);
      sheet.addCell(new Label(1, temp, purchaseItem.getSourceSn(), textFormat));
      sheet.setColumnView(2, 25);
      sheet.addCell(new jxl.write.DateTime(2, temp, purchaseItem.getPoTime(), dateFormat));
      sheet.setColumnView(3, 15);
      sheet.addCell(new jxl.write.DateTime(3, temp, purchaseItem.getPoTime(), timeFormat));
      sheet.setColumnView(4, 15);
      sheet.addCell(new Label(4, temp, purchaseItem.getSku(), textFormat));
      sheet.setColumnView(5, 15);
      sheet.addCell(new Label(5, temp, purchaseItem.getProductTypeName(), textFormat));
      sheet.setColumnView(6, 15);
      sheet.addCell(new Label(6, temp, purchaseItem.getSecCode(), textFormat));
      sheet.setColumnView(7, 15);
      sheet.addCell(new Label(7, temp, purchaseItem.getItemType(), textFormat));
      sheet.setColumnView(8, 15);
      sheet.addCell(new Label(8, temp, purchaseOrder.getChannelCode(), textFormat));
      sheet.setColumnView(9, 15);
      sheet.addCell(new jxl.write.Number(9, temp, purchaseItem.getPoQty(), intFormat));
      sheet.setColumnView(10, 15);
      sheet.addCell(new jxl.write.Number(10, temp, purchaseItem.getInputQty(), intFormat));
      sheet.setColumnView(11, 15);
      if (purchaseItem.getInputTime() != null) {
        sheet.addCell(new jxl.write.DateTime(11, temp, purchaseItem.getInputTime(),
            dateTimeFormat));
      } else {
        sheet.addCell(new Label(11, temp, "", textFormat));
      }
      sheet.setColumnView(12, 15);
      sheet.addCell(new jxl.write.Number(12, temp, purchaseItem.getPrice().doubleValue(),
          doubleFormat));
      sheet.setColumnView(13, 15);
      sheet.addCell(new jxl.write.Number(13, temp, purchaseItem.getPoItemAmount()
          .doubleValue(), doubleFormat));
      sheet.setColumnView(14, 20);
      sheet.addCell(new Label(14, temp,
          purchaseItem.getPoQty() == purchaseItem.getInputQty() ? "无差异" : "有差异:采购数量为:"
              + purchaseItem
              .getPoQty()
              + "实际入库数量为:"
              + purchaseItem
              .getInputQty(),
          textFormat));
      sheet.setColumnView(15, 20);
      sheet.addCell(new Label(15, temp, purchaseItem.getPayStatus(), textFormat));
      temp++;
    }
  }
}
