package com.haier.svc.api.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.ParseException;
import com.google.gson.Gson;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.model.CrmOrderItem;
import com.haier.purchase.data.model.DataDictionary;
import com.haier.purchase.data.model.T2OrderItem;
import com.haier.shop.model.ItemAttribute;
import com.haier.svc.api.controller.util.CommUtil;
import com.haier.svc.api.controller.util.CrmExportData;
import com.haier.svc.api.controller.util.ExportData;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.controller.util.WebUtil;
import com.haier.svc.api.controller.util.date.DateCal;
import com.haier.svc.api.service.CommPurchase;
import com.haier.svc.service.DataDictionaryService;
import com.haier.svc.service.T2OrderQueryService;
import com.haier.svc.service.T2OrderService;

/**
 * @author 李超
 *
 */
@Controller
@RequestMapping(value="T2OrderQuery")
public class T2OrderQueryController {
	
    private final static org.apache.log4j.Logger logger = LogManager
            .getLogger(T2OrderQueryController.class);
	
    @Autowired
	private T2OrderQueryService t2OrderQueryService;
    @Autowired
	T2OrderService t2OrderService;
/*    @Autowired
	private CommPurchase commPurchase;*/
	
//	@Reference(version="1.0.0")
//    @Autowired
//    private PurchaseCommonService purchaseCommonService;
    
    //@Resource(name = "dataDictionaryService")
	@Autowired
    private DataDictionaryService dataDictionaryService;
    
    // T+2订单状态
    private static final String T2_ORDER_STATUS = "flow_flag";
    
    // 订单状态类别
    private static final String ORDER_CATEGORY = "order_category";
    
    // 是否区分
    private static final String TRUE_FALSE_DISTINCT = "true_or_false";
    
    // 订单类型
    private static final String ORDER_TYPE = "order_type";
    
    // 库存满足方式（1：在库 2：补货）
    private static String SATISFY_TYPE = "satisfy_type ";
    

	@Autowired
	CommPurchase commPurchase;
    
	 @RequestMapping("/PageJump")
	 public String PageJump(HttpServletRequest request, Map<String, Object> modelMap){
		 Map<String, Object> authMap = new HashMap<>();
			commPurchase.getAuthMap(request, "", "", "", authMap);
			try {
				modelMap.put("authMap", JSON.json(authMap));
			} catch (IOException e) {
				e.printStackTrace();
			}
		 return  "purchase/t2OrderQuery";
	 }

    /**
     * T+2订单综合查询页面
     * @param request
     * @param modelMap
     * @return
     */
/*    @RequestMapping(value = {"/t2OrderQuery"}, method = {RequestMethod.GET})
    String t2OrderQuery(HttpServletRequest request, Map<String, Object> modelMap) {
        // 当前日期取得
        String currentDate = CommUtil.getCurrentDate("yyyy-MM-dd");
        // 上周日期取得
        DateCal dateCal = new DateCal(currentDate);
        String lastDate = dateCal.decDay(7);
        modelMap.put("currentweek", currentDate);
        modelMap.put("lastweek", lastDate);
        return "T2OrderQuery/t2OrderQuery";
    }*/
    
    /**
     * T+2订单查询  李超
     * @param arrival_start_week
     * @param arrival_end_week
     * @param report_start_week
     * @param report_end_week
     * @param ed_channel_id
     * @param product_group_id
     * @param order_id
     * @param oms_order_id
     * @param brand_id
     * @param materials_id
     * @param materials_desc
     * @param storage_id
     * @param flow_flag
     * @param shipment_combination_id
     * @param gvs_order_id
     * @param custom_order_id
     * @param customization
     * @param order_type
     * @param category_id
     * @param order_category
     * @param rows
     * @param page
     * @param request
     * @param response
     */
    @RequestMapping(value = {"/findMultipleOrder"}, method = {RequestMethod.POST})
    void findMultipleOrder(@RequestParam(required = false) String arrival_start_week,
                           @RequestParam(required = false) String arrival_end_week,
                           @RequestParam(required = false) String report_start_week,
                           @RequestParam(required = false) String report_end_week,
                           @RequestParam(required = false) String ed_channel_id,
                           @RequestParam(required = false) String product_group_id,
                           @RequestParam(required = false) String order_id,
                           @RequestParam(required = false) String oms_order_id,
                           @RequestParam(required = false) String brand_id,
                           @RequestParam(required = false) String materials_id,
                           @RequestParam(required = false) String materials_desc,
                           @RequestParam(required = false) String storage_id,
                           @RequestParam(required = false) String flow_flag,
                           @RequestParam(required = false) String shipment_combination_id,
                           @RequestParam(required = false) String gvs_order_id,
                           @RequestParam(required = false) String custom_order_id,
                           @RequestParam(required = false) String customization,
                           @RequestParam(required = false) String order_type,
                           @RequestParam(required = false) String category_id,
                           @RequestParam(required = false) String order_category,
                           @RequestParam(required = false) Integer rows,
                           @RequestParam(required = false) Integer page,
                           HttpServletRequest request, HttpServletResponse response) {
        try {
            String startDay = "";
            String endDay = "";
            if (rows == null)
                rows = 20;
            if (page == null)
                page = 1;
            Map<String, Object> params = new HashMap<String, Object>();
            // Boolean startweekblag = false;
            // Boolean endweekblag = false;
            if (arrival_start_week != null && !"".equals(arrival_start_week)) {
                String startWeek = CommUtil.getWeekOfYear_Sunday_Normal(arrival_start_week, null,
                        "1");
                arrival_start_week = startWeek.replace("年", "").replace("周", "");
                startDay = CommUtil.weekToStartDateDay(arrival_start_week);
                // System.out.println("startDay:" + startDay);
                // startweekblag = true;
            }
            if (arrival_end_week != null && !"".equals(arrival_end_week)) {
                String endtWeek = CommUtil.getWeekOfYear_Sunday_Normal(arrival_end_week, null, "1");
                arrival_end_week = endtWeek.replace("年", "").replace("周", "");
                endDay = CommUtil.weekToEndDateDay(arrival_end_week);
                // System.out.println("endDay:" + endDay);
                // endweekblag = true;
            }
            // if (startweekblag && endweekblag) {
            //
            // } else {
            // if (startweekblag) {
            // arrival_end_week = arrival_start_week;
            // } else {
            // arrival_start_week = arrival_end_week;
            // }
            // }
            // 权限Map
            Map<String, Object> authMap = new HashMap<String, Object>();
            // 取得产品组权限List和渠道权限List
            commPurchase.getAuthMap(request,product_group_id,
                    ed_channel_id, category_id, authMap);
            params.put("arrival_start_week", startDay);
            params.put("arrival_end_week", endDay);
            if (report_start_week != null && !"".equals(report_start_week)) {
                report_start_week = report_start_week.replace("年", "").replace("周", "");
            }
            if (report_end_week != null && !"".equals(report_end_week)) {
                report_end_week = report_end_week.replace("年", "").replace("周", "");
            }
            params.put("report_year_week_start", report_start_week);
            params.put("report_year_week_end", report_end_week);
            if(authMap.get("channel") != null){
            	String[] channels = (String[]) authMap.get("channel");
            	Set<String> channelSet = new HashSet(Arrays.asList(channels));
            }
            params.put("ed_channel_id", authMap.get("channel"));
//            params.put("ed_channel_id", ed_channel_id);
            params.put("product_group_id", authMap.get("productGroup"));
//            params.put("product_group_id", product_group_id);
            params.put("order_id", order_id);
            params.put("oms_order_id", oms_order_id);
            params.put("brand_id", brand_id);
            params.put("materials_id", materials_id);
            params.put("materials_desc", materials_desc);
            params.put("storage_id", storage_id);
            // 订单类别
            params.put("order_category", order_category);
            String[] flow_flag_list = null;
            if (flow_flag != null && !"".equals(flow_flag)) {
                // flow_flag转化为数组
                flow_flag_list = flow_flag.split(",");
            }
            params.put("flow_flag", flow_flag_list);

            params.put("shipment_combination_id", shipment_combination_id);
            params.put("gvs_order_id", gvs_order_id);
            params.put("custom_order_id", custom_order_id);
            params.put("customization", customization);
            params.put("order_type", order_type);
            params.put("category_id", authMap.get("cbsCategory"));
//            params.put("category_id", category_id);
            params.put("m", (page - 1) * rows);
            params.put("n", rows);
            
            ServiceResult<List<T2OrderItem>> result = t2OrderQueryService
                    .getT2OrderList(params);
            
            if(result == null)
            	return ;
            
            //result.setResult(new ArrayList<T2OrderItem>());
            
            // 获取流程状态map
            Map<String, String> flowFlagMap = commPurchase.getValueMeaningMap(
                    dataDictionaryService, T2_ORDER_STATUS);
            // 获取真假map
            Map<String, String> trueOrFalseMap = commPurchase.getValueMeaningMap(
                    dataDictionaryService, TRUE_FALSE_DISTINCT);
            // 获取订单类型map
            Map<String, String> orderTypeMap = commPurchase.getValueMeaningMap(
                    dataDictionaryService, ORDER_TYPE);
            // 获取库存满足方式map
            Map<String, String> satisfyTypeMap = commPurchase.getValueMeaningMap(
                    dataDictionaryService, SATISFY_TYPE);
            // 获取订单类别
            Map<String, String> orderCategoryMap = commPurchase.getValueMeaningMap(
                    dataDictionaryService, ORDER_CATEGORY);
            // 渠道和产品组数据存入HashMap
            Map<String, String> productgroupmap = new HashMap<String, String>();// 产品组
            Map<String, String> invstockchannelmap = new HashMap<String, String>();// 渠道
            // 品牌数据存入HashMap
            Map<String, String> brandMap = new HashMap<String, String>();
            // 取得产品组
            commPurchase.getProductMap(productgroupmap);
            productgroupmap = t2OrderService.getProductMap(productgroupmap);
            // 取得渠道
//            commPurchase.getChannelMap(invstockchannelmap);
            invstockchannelmap = t2OrderService
					.getChannelMapByCode(invstockchannelmap);
            // 取得品牌
            commPurchase.getBrandMap(brandMap);
            if (result.getSuccess() && result.getResult() != null) {
                List<T2OrderItem> t2OrderItem = result.getResult();
                for (T2OrderItem item : t2OrderItem) {
                    // 提报周对应结束日取得

                    String arrivalWeek = CommUtil.getWeekOfYear_Sunday_Normal(
                            item.getLatest_arrive_date_display(), null, "1");
                    // System.out.println(item.getLatest_arrive_date_display());
                    // System.out.println(arrivalWeek);
                    // 编辑到货年
                    item.setArrive_year_week(arrivalWeek);
                    // 编辑到货周
                    item.setArrive_year_week_display(arrivalWeek);
                    // 提报周
                    String report_year_week = item.getReport_year_week();
                    if(!"".equals(report_year_week)){
                    	// 编辑提报年
                    	item.setReport_year_week(report_year_week.substring(0, 4) + "年");
                    	// 编辑提报周
                        item.setReport_year_week_display(report_year_week.substring(4) + "周");
                    }
                    // 根据工作组id取得工作组名
                    item.setProduct_group_name(productgroupmap.get(item.getProduct_group_id()));
                    // 根据渠道id取得渠道名
                    item.setEd_channel_name(invstockchannelmap.get(item.getEd_channel_id()));
                    // 根据品牌id取得品牌名
                    item.setBrand_id(brandMap.get(item.getBrand_id()));
                    // 编辑状态
                    item.setFlow_flag_name(flowFlagMap.get(String.valueOf(item.getFlow_flag())));
                    // 编辑订单类型
                    item.setOrder_type_name(orderTypeMap.get(String.valueOf(item.getOrder_type_id())));
                    // 编辑订单类别
                    item.setOrder_category_name(orderCategoryMap.get(String.valueOf(item
                            .getOrder_category())));
                    // 编辑OMS状态
                    item.setStatus_name(flowFlagMap.get(String.valueOf(item.getStatus())));
                    // 取得库存满足方式
                    item.setSatisfy_type_name(satisfyTypeMap.get(String.valueOf(item
                            .getSatisfy_type_id())));
                    // 编辑是否为日日顺订单
                    item.setRrs_order_flag_name(trueOrFalseMap.get(String.valueOf(item
                            .getRrs_order_flag())));
                    // 编辑是否定制
                    item.setCustomization_name(trueOrFalseMap.get(String.valueOf(item
                            .getCustomization())));
                    
                    //return item;
                }
                Map<String, Object> retMap = new HashMap<String, Object>();
                Gson gson = new Gson();
                retMap.put("rows", result.getResult());
                retMap.put("total", result.getPager().getRowsCount());
                response.addHeader("Content-type", "text/html;charset=utf-8");
				response.getWriter().write(gson.toJson(retMap));
                response.getWriter().flush();
                response.getWriter().close();
            }

        } catch (IOException e) {
            logger.error("", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }
    
    /**
     * 查看PO PO查询页面
     * @param modelMap
     * @return
     */
    @RequestMapping(value = {"/purchaseOrderQueryList"}, method = {RequestMethod.GET})
    String purchaseOrderQueryList(HttpServletRequest request, Map<String, Object> modelMap) {
//        String currentDate = CommUtil.getCurrentDate("yyyy-MM-dd");
//        DateCal dateCal = new DateCal(currentDate);
//        String lastDate = dateCal.decDay(7);
//
//        modelMap.put("currentweek", currentDate);
//        modelMap.put("lastweek", lastDate);
        
        String poDetailData = request.getParameter("poDetailData");
        modelMap.put("poDetailData", poDetailData);
        String url = request.getHeader("referer");
        modelMap.put("url", url);
        Map<String, Object> authMap = new HashMap<>();
		commPurchase.getAuthMap(request, "", "", "", authMap);
		try {
			modelMap.put("authMap", JSON.json(authMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
        return "purchase/t2OrderQueryPoList";
    }
    
    /**
     * 获取PO订单信息
     *
     * @return
     */
    @RequestMapping(value = {"/findPOList"}, method = {RequestMethod.POST})
    void find(@RequestParam(required = false) String report_start_week,
              @RequestParam(required = false) String report_end_week,
              @RequestParam(required = false) String channel,
              @RequestParam(required = false) String product_group,
              @RequestParam(required = false) String wp_order_id,
              @RequestParam(required = false) String storage_id,
              @RequestParam(required = false) String flow_flag,
              @RequestParam(required = false) String materials_id,
              @RequestParam(required = false) String bill_order_id,
              @RequestParam(required = false) String so_id,
              @RequestParam(required = false) String dn_id,
              @RequestParam(required = false) String bill_time_start,
              @RequestParam(required = false) String bill_time_end,
              @RequestParam(required = false) String datestorge_start,
              @RequestParam(required = false) String datestorge_end,
              @RequestParam(required = false) String brand_id,
              @RequestParam(required = false) String category_id,
              @RequestParam(required = false) String materials_desc,
              @RequestParam(required = false) Integer rows,
              @RequestParam(required = false) Integer page,
              @RequestParam(required = false) String order_id,
              @RequestParam(required = false) String source_order_id,
              HttpServletRequest request,
              HttpServletResponse response) throws java.text.ParseException {
        try {
            if (rows == null)
                rows = 20;
            if (page == null)
                page = 1;
            Map<String, Object> params = new HashMap<String, Object>();
            String startDay = "";
            String endDay = "";
            // Boolean startweekblag = false;
            // Boolean endweekblag = false;
            if (report_start_week != null && !"".equals(report_start_week)) {
                String startWeek = CommUtil.getWeekOfYear_Sunday_Normal(report_start_week, null,
                        "1");
                report_start_week = startWeek.replace("年", "").replace("周", "");
                startDay = CommUtil.weekToStartDateDay(report_start_week);
                // startweekblag = true;
            }
            if (report_end_week != null && !"".equals(report_end_week)) {
                String endtWeek = CommUtil.getWeekOfYear_Sunday_Normal(report_end_week, null, "1");
                report_end_week = endtWeek.replace("年", "").replace("周", "");
                endDay = CommUtil.weekToEndDateDay(report_end_week);
                // endweekblag = true;
            }
            // if (startweekblag && endweekblag) {
            //
            // } else {
            // if (startweekblag) {
            // report_end_week = report_start_week;
            // } else {
            // report_start_week = report_end_week;
            // }
            // }

            if (bill_time_end != null && !"".equals(bill_time_end)) {
                DateCal dateCal_billstart = new DateCal(bill_time_end);
                bill_time_end = dateCal_billstart.decDay(-1);
            }
            if (datestorge_end != null && !"".equals(datestorge_end)) {
                DateCal dateCal_datestorgeend = new DateCal(datestorge_end);
                datestorge_end = dateCal_datestorgeend.decDay(-1);
            }

            // 权限Map
            Map<String, Object> authMap = new HashMap<String, Object>();
            // 取得产品组权限List和渠道权限List
            commPurchase.getAuthMap(request, product_group, channel,
                    category_id, authMap);
            params.put("report_start_week", startDay);
            params.put("report_end_week", endDay);
            params.put("product_group_id", authMap.get("productGroup"));
            params.put("ed_channel_id", authMap.get("channel"));
            params.put("wp_order_id", wp_order_id);
            params.put("storage_id", storage_id);
            params.put("materials_id", materials_id);
            params.put("bill_time_start", bill_time_start);
            params.put("bill_time_end", bill_time_end);
            params.put("bill_order_id", bill_order_id);
            params.put("so_id", so_id);
            params.put("dn_id", dn_id);
            params.put("datestorge_start", datestorge_start);
            params.put("datestorge_end", datestorge_end);
            params.put("brand_id", brand_id);
            params.put("category_id", authMap.get("cbsCategory"));
            params.put("materials_desc", materials_desc);
            params.put("source_order_id", source_order_id);
            params.put("order_id", order_id);
            // flow_flag转化为数组
            String[] flow_flag_list = null;
            if (flow_flag != null && !"".equals(flow_flag)) {
                if (flow_flag.contains("ALL")) {
                    flow_flag_list = null;
                } else {
                    flow_flag_list = flow_flag.split(",");
                }
            }
            params.put("flow_flag", flow_flag_list);
            params.put("m", (page - 1) * rows);
            params.put("n", rows);

            // 渠道和产品组数据存入HashMap
            Map<String, String> productgroupmap = new HashMap<String, String>();
            Map<String, String> invstockchannelmap = new HashMap<String, String>();
            // 取得产品组
            commPurchase.getProductMap(productgroupmap);
            // 取得渠道
//            commPurchase.getChannelMap(invstockchannelmap);
            invstockchannelmap = t2OrderService
					.getChannelMapByCode(invstockchannelmap);
            // 获取流程状态map
            Map<String, String> flowFlagMap = commPurchase.getValueMeaningMap(
                    dataDictionaryService, T2_ORDER_STATUS);
            // 调用业务Service
            ServiceResult<List<CrmOrderItem>> result = t2OrderQueryService.getPOList(params);
            if (result.getSuccess() && result.getResult() != null) {
                List<CrmOrderItem> predictstocklist = result.getResult();
                for (CrmOrderItem item : predictstocklist) {
                    // 根据渠道id取得渠道名
                    item.setEd_channel_name(invstockchannelmap.get(item.getEd_channel_id()));
                    // 根据工作组id取得工作组名
                    item.setProduct_group_name(productgroupmap.get(item.getProduct_group_id()));
                    // 取得流程状态名称
                    item.setFlow_flag_name(flowFlagMap.get(String.valueOf(item.getFlow_flag())));
                }
/*                Map<String, Object> retMap = new HashMap<String, Object>();
                retMap.put("total", result.getPager().getRowsCount());
                retMap.put("rows", predictstocklist);
                response.getWriter().write(JsonUtil.toJson(retMap));
                response.getWriter().flush();
                response.getWriter().close();*/
                Map<String, Object> retMap = new HashMap<String, Object>();
                Gson gson = new Gson();
                retMap.put("rows", predictstocklist);
                retMap.put("total", result.getPager().getRowsCount());
                response.addHeader("Content-type", "text/html;charset=utf-8");
				response.getWriter().write(gson.toJson(retMap));
                response.getWriter().flush();
                response.getWriter().close();
            }
        } catch (IOException e) {
            logger.error("", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }
    
    /**
     * 手工关单 李超
     *
     * @param request
     * @param manualCloseData 提交数据
     */
    @RequestMapping(value = {"/manualCloseOrderList"}, method = {RequestMethod.POST})
    @ResponseBody
    public HttpJsonResult<String> manualCloseOrderList(HttpServletRequest request,
                                                       @RequestParam(required = true) String manualCloseData) {
        HttpJsonResult<String> result = new HttpJsonResult<String>();
        if (manualCloseData != null) {
            try {
                // 取得提交数据
                JSONArray manualclosejson = (JSONArray) JSON.parse(manualCloseData);
                List<String> manualcloselist = new ArrayList<String>();
                // JSONArray转化为list
                for (int i = 0; i < manualclosejson.length(); i++) {
                    manualcloselist.add(manualclosejson.getString(i));
                }
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("manualCloseList", manualcloselist);

                // 操作人 start xuelin.zhao///////
                String closeUser = WebUtil.currentUserName(request);
                params.put("order_close_user", closeUser);
                // end/////////////////////////
                // 订单提交更新
                t2OrderQueryService.manualCloseOrderList(params);
            } catch (ParseException e) {
                logger.error("", e);
                throw new BusinessException("JSON转化失败" + e.getMessage());
            }
        }
        result.setMessage("更新成功");
        return result;
    }

    /**
     * 批量手工关单 李超
     * @param request
     * @param manualCloseData
     * @return
     */
    @RequestMapping(value = {"/batchManualCloseOrderList"}, method = {RequestMethod.POST})
    @ResponseBody
    public HttpJsonResult<String> batchManualCloseOrderList(HttpServletRequest request,
                                                            @RequestParam(required = true) String manualCloseData) {
        HttpJsonResult<String> result = new HttpJsonResult<String>();
        if (manualCloseData != null) {
            try {
                // 取得提交数据
                String[] manualclosejson = manualCloseData.split(",");
                List<String> manualcloselist = new ArrayList<String>();
                for (int i = 0; i < manualclosejson.length; i++) {
                    manualcloselist.add(manualclosejson[i]);
                }
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("manualCloseList", manualcloselist);

                String closeUser = WebUtil.currentUserName(request);
                params.put("order_close_user", closeUser);
                // 订单提交更新
                t2OrderQueryService.manualCloseOrderList(params);
            } catch (Exception e) {
                logger.error("", e);
                throw new BusinessException("JSON转化失败" + e.getMessage());
            }

        }
        result.setMessage("更新成功");
        return result;
    }

    
    /**
     * 撤销手工关单 李超
     * @param request
     * @param cancelCloseData
     * @return
     */
    @RequestMapping(value = {"/cancelCloseOrderList"}, method = {RequestMethod.POST})
    @ResponseBody
    public HttpJsonResult<String> cancelCloseOrderList(HttpServletRequest request,
                                                       @RequestParam(required = true) String cancelCloseData) {
        HttpJsonResult<String> result = new HttpJsonResult<String>();
        if (cancelCloseData != null) {
            try {
                // 取得提交数据
                JSONArray cancelclosejson = (JSONArray) JSON.parse(cancelCloseData);
                List<String> cancelcloselist = new ArrayList<String>();
                // JSONArray转化为list
                for (int i = 0; i < cancelclosejson.length(); i++) {
                    cancelcloselist.add(cancelclosejson.getString(i));
                }
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("cancelCloseList", cancelcloselist);

                // 操作人 start xuelin.zhao///////
                String closeUser = WebUtil.currentUserName(request);
                params.put("order_close_user", closeUser);
                // end/////////////////////////
                // 订单提交更新
                t2OrderQueryService.cancelCloseOrderList(params);
            } catch (ParseException e) {
                logger.error("", e);
                throw new BusinessException("JSON转化失败" + e.getMessage());
            }

        }
        result.setMessage("更新成功");
        return result;
    }
    /**
     * 已冻结推送 李超
     *
     * @param request
     * @param order_id
     * @param response
     */
    @RequestMapping(value = {"/commitAgainOrderMultipleList"}, method = {RequestMethod.POST})
    public void commitAgainOrder(HttpServletRequest request,
                                 @RequestParam(required = true) String oms_order_id,
                                 String pass_reason, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("pass_reason", pass_reason);
        params.put("oms_order_id", oms_order_id);
        int u = t2OrderQueryService.commitAgainOrderMultiple(params);
        Boolean result = (u > 0) ? true : false;
        try {
            response.getWriter().write(JsonUtil.toJson(result));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("", e);
            e.printStackTrace();
        }
    }
    
  
   
    
    
    /**
     * 获取品类权限数据  李超
     *
     * @param request
     * @return
     */
/*    @RequestMapping(value = {"/getCbsCategoryByAuth"}, method = {RequestMethod.GET})
    @ResponseBody
    HttpJsonResult<List<String>> getCbsCategoryByAuth(HttpServletRequest request) {
        HttpJsonResult<List<String>> result = new HttpJsonResult<List<String>>();
        ServiceResult<List<String>> queryResult = itemService.getAllCbsCategory();

        // 权限定义产品组取得
        ServiceResult<PrivilegeItem> privilegeData = purchaseCommonService.getPrivilege(String
                .valueOf(WebUtil.currentUserId(request)));
        String[] cbsCategoryDataList = null;
        cbsCategoryDataList = privilegeData.getResult().getCbs_catgory().split(",");
        // 删除没有权限的产品组
        List<String> cbsCategoryData = new ArrayList<String>();
        if (privilegeData.getSuccess() == true && privilegeData.getResult() != null) {
            for (String category : queryResult.getResult()) {
                //category='空调'  privilegeData.getResult().getCbs_catgory() = '商用空调'
                //此种情况就会将 空调加到cbsCategoryData里  zzb  2017-03-28
                if (privilegeData.getResult().getCbs_catgory().contains(category)) {
                    cbsCategoryData.add(category);
                }
                for(int i = 0;i<cbsCategoryDataList.length;i++){
                    if(category.equals(cbsCategoryDataList[i])){
                        cbsCategoryData.add(category);
                    }
                }
            }
            result.setData(cbsCategoryData);
        } else {
            result.setData(queryResult.getResult());
        }
        return result;
    }*/
    
	/**
	 * 预测备料通过日期获取当前周
	 * @param  日期（YYYY-MM-DD）
	 * @return 例子： 2014年01周
	 */
	@RequestMapping(value = { "/findateWeek" }, method = { RequestMethod.GET })
	@ResponseBody
	public HttpJsonResult<String> findateWeek(HttpServletRequest request,
											  HttpServletResponse response) {
		HttpJsonResult<String> result = new HttpJsonResult<String>();
		String date = request.getParameter("date");
		String type = request.getParameter("type");
		String dataweekString = "";
		if ("0".equals(type)) {
			//周四为一周第一天
			dataweekString = CommUtil.getWeekOfYear_Sunday(date, null, "1");
		} else {
			//周日为一周第一天
			dataweekString = CommUtil.getWeekOfYear_Sunday_Normal(date, null, "1");
		}
		result.setData(dataweekString);
		return result;
	}
	
/*	@RequestMapping(value = { "/getProductGroupByAuth" }, method = { RequestMethod.GET })
	@ResponseBody
	HttpJsonResult<List<ItemAttribute>> getProductGroup(HttpServletRequest request) {
		HttpJsonResult<List<ItemAttribute>> result = new HttpJsonResult<List<ItemAttribute>>();
		ServiceResult<List<ItemAttribute>> queryResult = t2OrderQueryService
			.queryProductGroupByCbsCategory(null);
		//        queryResult
		//        groups.add(0, "全部");

		// 权限定义产品组取得
		ServiceResult<PrivilegeItem> privilegeData = purchaseCommonService
			.getPrivilege(String.valueOf(WebUtil.currentUserId(request)));
		// 删除没有权限的产品组
		List<ItemAttribute> productGroupData = new ArrayList<ItemAttribute>();
		if (privilegeData.getSuccess() == true && privilegeData.getResult() != null) {
			for (ItemAttribute itemAttribute : queryResult.getResult()) {
				if (privilegeData.getResult().getProduct_group_id()
					.contains(itemAttribute.getValue())) {
					productGroupData.add((itemAttribute));
				}
			}
			result.setData(productGroupData);
		} else {
			result.setData(queryResult.getResult());
		}
		return result;
	}*/
	
/*	@RequestMapping(value = { "/getChannelsByAuth" }, method = { RequestMethod.GET })
	@ResponseBody
	HttpJsonResult<List<InvStockChannel>> getChennelsByAuth(HttpServletRequest request) {
		HttpJsonResult<List<InvStockChannel>> result = new HttpJsonResult<List<InvStockChannel>>();
		ServiceResult<List<InvStockChannel>> queryResult = t2OrderQueryService.getChannels();

		// 权限定义产品组取得
		ServiceResult<PrivilegeItem> privilegeData = purchaseCommonService
			.getPrivilege(String.valueOf(WebUtil.currentUserId(request)));
		// 删除没有权限的产品组
		List<InvStockChannel> chennelsData = new ArrayList<InvStockChannel>();
		if (privilegeData.getSuccess() == true && privilegeData.getResult() != null) {
			for (InvStockChannel invStockChannel : queryResult.getResult()) {
				if (privilegeData.getResult().getEd_channel_id()
					.contains(invStockChannel.getChannelCode())) {
					chennelsData.add((invStockChannel));
				}
			}
			result.setData(chennelsData);
		} else {
			result.setData(queryResult.getResult());
		}
		//690电商渠道处理
		if (privilegeData.getResult().getEd_channel_id().contains("DS")) {
			InvStockChannel invStockChannel = new InvStockChannel();
			invStockChannel.setChannelCode("DS");
			invStockChannel.setName("690电商渠道");
			result.getData().add(invStockChannel);
		}
		return result;
	}*/
	
	/**
	 * 通过数据分类查询流程标识list
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/getByValueSetId" }, method = { RequestMethod.GET })
	@ResponseBody
    HttpJsonResult<List<DataDictionary>> getByValueSetId(HttpServletRequest request) {

        HttpJsonResult<List<DataDictionary>> result = new HttpJsonResult<List<DataDictionary>>();
        result = commPurchase.getValueSetId(dataDictionaryService,
                request.getParameter("valueSetId"));
        return result;
    }
	
    /**
     * PO详情页面调转
     *
     * @param request
     * @param modelMap 调转页面数据存放map
     * @return
     */
    @RequestMapping(value = {"/t2OrderQueryPoList"}, method = {RequestMethod.GET})
    String T2OrderQueryPoList(HttpServletRequest request, Map<String, Object> modelMap) {
        String poDetailData = request.getParameter("poDetailData");
        modelMap.put("poDetailData", poDetailData);
        String url = request.getHeader("referer");
        modelMap.put("url", url);
        return "purchase/t2OrderQueryPoList";
    }
    
    /**
     * 获取品牌
     * @param
     * @author DHC 黄俊
     */
    @RequestMapping(value = {"/getProductBrand"}, method = {RequestMethod.GET})
    @ResponseBody
    HttpJsonResult<List<ItemAttribute>> getProductBrand() {
        HttpJsonResult<List<ItemAttribute>> result = new HttpJsonResult<List<ItemAttribute>>();
        ServiceResult<List<ItemAttribute>> queryResult = t2OrderService.getByValueSetItemId("Brand");
        result.setData(queryResult.getResult());
        return result;
    }
    
    /**
     * T+2点击部分导出、导出Excel 李超
     *
     * @param exportData 导出数据
     * @param response
     * @param request
     * @param modelMap   状态
     * @return 方法执行完毕调用的画面
     */
    @RequestMapping(value = {"/exportT2OrderList.export"})
    void exportT2OrderList(@RequestParam(required = false) String exportData,HttpServletResponse res) {

        Map<String, Object> params = new HashMap<String, Object>();
        JSONArray exportJson = new JSONArray();
        String[] exportArray = null;
        try {
            if (exportData != null && !exportData.equals("")) {
                exportJson = (JSONArray) JSON.parse(exportData);
                exportArray = new String[exportJson.length()];
                // JSONArray转化为list
                for (int i = 0; i < exportJson.length(); i++) {
                    exportArray[i] = (String) exportJson.get(i);
                }
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
            logger.error("JSON转换失败！ 错误：" + e1.getMessage());
        }
        params.put("order_list", exportArray);
        HSSFWorkbook  wb= getDetailsData(params);
   
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");  
          java.util.Date date=new java.util.Date();  
          String str=sdf.format(date); 
          String fileName = "订单列表"+str;
          
          ByteArrayOutputStream os = new ByteArrayOutputStream();
          try {
          wb.write(os);
          byte[] content = os.toByteArray();
          InputStream is = new ByteArrayInputStream(content);

        ExportExcelUtil.exportCommon(is,fileName,res);
      } catch (IOException e) {
        logger.error("错误", e);
      }

      }

    @RequestMapping(value = {"/exportAllT2OrderQueryList.export"})
    void exportAllT2QueryOrderList(@RequestParam(required = false) String arrival_year_week_start_save,
                              @RequestParam(required = false) String arrival_year_week_end_save,
                              @RequestParam(required = false) String datestart_save,
                              @RequestParam(required = false) String dateend_save,
                              @RequestParam(required = false) String ed_channel_id_save,
                              @RequestParam(required = false) String product_group_id_save,
                              @RequestParam(required = false) String wp_order_id,
                              @RequestParam(required = false) String oms_order_id_save,
                              @RequestParam(required = false) String brand_save,
                              @RequestParam(required = false) String materials_id,
                              @RequestParam(required = false) String materials_description,
                              @RequestParam(required = false) String storage_id,
                              @RequestParam(required = false) String flow_flag_save,
                              @RequestParam(required = false) String shipment_combination_id,
                              @RequestParam(required = false) String gvs_order_id,
                              @RequestParam(required = false) String custom_order_id,
                              @RequestParam(required = false) String customization_save,
                              @RequestParam(required = false) String order_type_save,
                              @RequestParam(required = false) String cbs_catgory_save,
                              @RequestParam(required = false) String order_category_save,
                              HttpServletRequest request, HttpServletResponse response) throws IOException {

        String startDay = "";
        String endDay = "";
        Map<String, Object> params = new HashMap<String, Object>();
        // Boolean startweekblag = false;
        // Boolean endweekblag = false;
        if (arrival_year_week_start_save != null && !"".equals(arrival_year_week_start_save)) {
            String startWeek = CommUtil.getWeekOfYear_Sunday_Normal(arrival_year_week_start_save, null,
                    "1");
            arrival_year_week_start_save = startWeek.replace("年", "").replace("周", "");
            startDay = CommUtil.weekToStartDateDay(arrival_year_week_start_save);
            // System.out.println("startDay:" + startDay);
            // startweekblag = true;
        }
        if (arrival_year_week_end_save != null && !"".equals(arrival_year_week_end_save)) {
            String endtWeek = CommUtil.getWeekOfYear_Sunday_Normal(arrival_year_week_end_save, null, "1");
            arrival_year_week_end_save = endtWeek.replace("年", "").replace("周", "");
            endDay = CommUtil.weekToEndDateDay(arrival_year_week_end_save);
            // System.out.println("endDay:" + endDay);
            // endweekblag = true;
        }
        // if (startweekblag && endweekblag) {
        //
        // } else {
        // if (startweekblag) {
        // arrival_end_week = arrival_start_week;
        // } else {
        // arrival_start_week = arrival_end_week;
        // }
        // }
        // 权限Map
        Map<String, Object> authMap = new HashMap<String, Object>();
        // 取得产品组权限List和渠道权限List
        commPurchase.getAuthMap(request,product_group_id_save,
                ed_channel_id_save, cbs_catgory_save, authMap);
        params.put("arrival_start_week", startDay);
        params.put("arrival_end_week", endDay);
        if (datestart_save != null && !"".equals(datestart_save)) {
            datestart_save = datestart_save.replace("年", "").replace("周", "");
        }
        if (dateend_save != null && !"".equals(dateend_save)) {
            dateend_save = dateend_save.replace("年", "").replace("周", "");
        }
        params.put("report_year_week_start", datestart_save);
        params.put("report_year_week_end", dateend_save);
        params.put("ed_channel_id", authMap.get("channel"));
//        params.put("ed_channel_id", ed_channel_id_save);
        params.put("product_group_id", authMap.get("productGroup"));
//        params.put("product_group_id", product_group_id_save);
        params.put("order_id", wp_order_id);
        params.put("oms_order_id", oms_order_id_save);
        params.put("brand_id", brand_save);
        params.put("materials_id", materials_id);
        params.put("materials_desc", materials_description);
        params.put("storage_id", storage_id);
        // 订单类别
        params.put("order_category", order_category_save);
        String[] flow_flag_list = null;
        if (flow_flag_save != null && !"".equals(flow_flag_save)) {
            // flow_flag转化为数组
            flow_flag_list = flow_flag_save.split(",");
        }
        params.put("flow_flag", flow_flag_list);

        params.put("shipment_combination_id", shipment_combination_id);
        params.put("gvs_order_id", gvs_order_id);
        params.put("custom_order_id", custom_order_id);
        params.put("customization", customization_save);
        params.put("order_type", order_type_save);
        params.put("category_id", authMap.get("cbsCategory"));
//        params.put("category_id", cbs_catgory_save);

        HSSFWorkbook  wb= getDetailsQueryData(params);

        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date=new java.util.Date();
        String str=sdf.format(date);
        String fileName = "订单列表"+str;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            wb.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);

            ExportExcelUtil.exportCommon(is,fileName,response);
        } catch (IOException e) {
            logger.error("错误", e);
        }
    }

    /**
     * T+2审核提交画面导出数据处理（将不同DB中检索出的数据组织在一起） 李超
     *
     * @param  <String, Object> params 检索条件中的参数
     * @return result 需要显示或导出数据结果
     */
    public HSSFWorkbook getDetailsQueryData(Map<String, Object> params) {
        Integer pageIndex = 0;
        List<T2OrderItem> t2OrderItemsList= new ArrayList<>();

        while (true){
            params.put("m",1000*pageIndex);
            params.put("n",1000);
            pageIndex++;
            List<T2OrderItem> list = t2OrderQueryService.getT2OrderListExportData(params);
            if(list.isEmpty()||list.size()==0){
                break;
            }
            t2OrderItemsList.addAll(list);
        }

        // 获得条数
        List<T2OrderItem> orderList = new ArrayList<T2OrderItem>();
        if (t2OrderItemsList!=null&&t2OrderItemsList.size()!=0) {
            orderList = t2OrderItemsList;
        }
        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("订单列表");
        int length = ExportData.orderListQuery.length;
        for (int i = 0; i <length; i++) {
            sheet.setColumnWidth(i, (int)(21.57*256));
        }

        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 4.创建单元格，设置值表头，设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边
        // 设置表头
        for(int i=0;length-1>=i;i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(ExportData.orderListQuery[i]);
            cell.setCellStyle(style);
        }
        Map<String, String> orderCategoryMap = commPurchase.getValueMeaningMap(
                dataDictionaryService, ORDER_CATEGORY);

        Map<String, String> invstockchannelmap = new HashMap<String, String>();// 渠道

//         commPurchase.getChannelMap(invstockchannelmap);
        invstockchannelmap = t2OrderService
                .getChannelMapByCode(invstockchannelmap);
        invstockchannelmap = t2OrderService.getChannelMapByCode(invstockchannelmap);
        // 品牌数据存入HashMap
        Map<String, String> brandMap = new HashMap<String, String>();
        // 取得品牌
        commPurchase.getBrandMap(brandMap);

        Map<String, String> trueOrFalseMap = commPurchase.getValueMeaningMap(
                dataDictionaryService, TRUE_FALSE_DISTINCT);

        Map<String, String> flowFlagMap = commPurchase.getValueMeaningMap(
                dataDictionaryService, T2_ORDER_STATUS);

        Map<String, String> productgroupmap = new HashMap<String, String>();// 产品组
        commPurchase.getProductMap(productgroupmap);

        //向单元格里添加数据
        for(short i=0;i<orderList.size();i++){
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(orderCategoryMap.get(String.valueOf(orderList.get(i).getOrder_category())));
            row.createCell(1).setCellValue(flowFlagMap.get(String.valueOf(orderList.get(i).getFlow_flag())));
            row.createCell(2).setCellValue(orderList.get(i).getOrder_id());
            row.createCell(3).setCellValue(orderList.get(i).getOrder_num_73());
            row.createCell(4).setCellValue(orderList.get(i).getSend_flag());
            row.createCell(5).setCellValue(orderList.get(i).getChannel_commit_time_display());
            row.createCell(6).setCellValue(orderList.get(i).getChannel_commit_user());
            row.createCell(7).setCellValue(orderList.get(i).getAudit_time_display());
            row.createCell(8).setCellValue(orderList.get(i).getAudit_user());
            row.createCell(9).setCellValue(orderList.get(i).getOrder_close_time_display());
            row.createCell(10).setCellValue(orderList.get(i).getOrder_close_user());
            row.createCell(11).setCellValue(orderList.get(i).getOms_order_id());
            row.createCell(12).setCellValue(orderList.get(i).getCustom_desc());
            row.createCell(13).setCellValue("重庆新日日顺家电销售有限公司");
            row.createCell(14).setCellValue(orderList.get(i).getTransmit_desc());
            // 提报周
            String report_year_week = orderList.get(i).getReport_year_week();
            if(!"".equals(report_year_week)){
                // 编辑提报年
                row.createCell(15).setCellValue(report_year_week.substring(0, 4) + "年");
                row.createCell(16).setCellValue(report_year_week.substring(4) + "周");
            }

            String arrivalWeek = CommUtil.getWeekOfYear_Sunday_Normal(
                    orderList.get(i).getLatest_arrive_date_display(), null, "1");

            row.createCell(17).setCellValue(arrivalWeek);
            row.createCell(18).setCellValue(arrivalWeek);
            row.createCell(19).setCellValue(orderList.get(i).getIndustry_trade_desc());
            row.createCell(20).setCellValue(invstockchannelmap.get(orderList.get(i).getEd_channel_id()));
            // row.createCell(18).setCellValue(orderList.get(i).getEd_channel_name());
            row.createCell(21).setCellValue(orderList.get(i).getCategory_id());
            row.createCell(22).setCellValue(productgroupmap.get(orderList.get(i).getProduct_group_id()));
            //row.createCell(20).setCellValue(orderList.get(i).getProduct_group_name());
            row.createCell(23).setCellValue(brandMap.get(orderList.get(i).getBrand_id()));
            //row.createCell(21).setCellValue(orderList.get(i).getBrand_id());
            row.createCell(24).setCellValue(orderList.get(i).getMaterials_id());
            row.createCell(25).setCellValue(orderList.get(i).getMaterials_desc());
            row.createCell(26).setCellValue(orderList.get(i).getOrder_type_name());
            row.createCell(27).setCellValue(String.valueOf(orderList.get(i).getT2_delivery_prediction()));
            row.createCell(28).setCellValue(String.valueOf(orderList.get(i).getPrice()));
            row.createCell(29).setCellValue(String.valueOf(orderList.get(i).getAmount()));
            row.createCell(30).setCellValue(orderList.get(i).getStorage_id());
            row.createCell(31).setCellValue(orderList.get(i).getStorage_name());
            row.createCell(32).setCellValue(orderList.get(i).getArrival_storage_desc());
            row.createCell(33).setCellValue(orderList.get(i).getSeries_id());
            row.createCell(34).setCellValue(orderList.get(i).getStatus());
            row.createCell(35).setCellValue(orderList.get(i).getLatest_arrive_date_display());
            row.createCell(36).setCellValue(orderList.get(i).getPlan_deliver_date_display());
            row.createCell(37).setCellValue(orderList.get(i).getPromise_arrive_date_display());
            row.createCell(38).setCellValue(orderList.get(i).getActual_deliver_date_display());
            row.createCell(39).setCellValue(orderList.get(i).getPredict_arrive_date_display());
            row.createCell(40).setCellValue(orderList.get(i).getIndustry_trade_take_date_display());
            row.createCell(41).setCellValue(orderList.get(i).getCustom_sign_date_display());
            row.createCell(42).setCellValue(orderList.get(i).getReturn_order_date_display());
            row.createCell(43).setCellValue(orderList.get(i).getLatest_arrive_date_display());
            row.createCell(44).setCellValue(orderList.get(i).getFactory_id());
            row.createCell(45).setCellValue(orderList.get(i).getFactory_name());
            row.createCell(46).setCellValue(orderList.get(i).getShipment_combination_id());
            if(orderList.get(i).getSign_num() != null){
                row.createCell(47).setCellValue(String.valueOf(orderList.get(i).getSign_num()));
            }else {
                row.createCell(47).setCellValue("");
            }
//             row.createCell(45).setCellValue(String.valueOf(orderList.get(i).getSign_num()));
            row.createCell(48).setCellValue(orderList.get(i).getNo_pass_reason());
            row.createCell(49).setCellValue(orderList.get(i).getGvs_order_id());
            row.createCell(50).setCellValue(orderList.get(i).getDn_id());
            row.createCell(51).setCellValue(orderList.get(i).getCustpodetailcode());
            row.createCell(52).setCellValue(orderList.get(i).getCommit_time_display());
            row.createCell(53).setCellValue(trueOrFalseMap.get(String.valueOf(orderList.get(i) .getCustomization())));
            //  row.createCell(51).setCellValue(orderList.get(i).getCustomization_name());
            row.createCell(54).setCellValue(orderList.get(i).getSatisfy_type_name());
            if (orderList.get(i).getWAqty() != null){
                row.createCell(55).setCellValue(String.valueOf(orderList.get(i).getWAqty()));
            }else {
                row.createCell(55).setCellValue("");
            }
//             row.createCell(53).setCellValue(String.valueOf(orderList.get(i).getWAqty()));
            row.createCell(56).setCellValue(orderList.get(i).getError_msg());
            row.createCell(57).setCellValue(orderList.get(i).getPass_reason());

        }
        return wb;

    }

    @RequestMapping(value = {"/exportAllT2OrderList.export"})
    void exportAllT2OrderList(@RequestParam(required = false) String arrival_year_week_start_save,
            @RequestParam(required = false) String arrival_year_week_end_save,
            @RequestParam(required = false) String datestart_save,
            @RequestParam(required = false) String dateend_save,
            @RequestParam(required = false) String ed_channel_id_save,
            @RequestParam(required = false) String product_group_id_save,
            @RequestParam(required = false) String wp_order_id,
            @RequestParam(required = false) String oms_order_id_save,
            @RequestParam(required = false) String brand_save,
            @RequestParam(required = false) String materials_id,
            @RequestParam(required = false) String materials_description,
            @RequestParam(required = false) String storage_id,
            @RequestParam(required = false) String flow_flag_save,
            @RequestParam(required = false) String shipment_combination_id,
            @RequestParam(required = false) String gvs_order_id,
            @RequestParam(required = false) String custom_order_id,
            @RequestParam(required = false) String customization_save,
            @RequestParam(required = false) String order_type_save,
            @RequestParam(required = false) String cbs_catgory_save,
            @RequestParam(required = false) String order_category_save,
            HttpServletRequest request, HttpServletResponse response) throws IOException {

    	String startDay = "";
        String endDay = "";
        Map<String, Object> params = new HashMap<String, Object>();
        // Boolean startweekblag = false;
        // Boolean endweekblag = false;
        if (arrival_year_week_start_save != null && !"".equals(arrival_year_week_start_save)) {
            String startWeek = CommUtil.getWeekOfYear_Sunday_Normal(arrival_year_week_start_save, null,
                    "1");
            arrival_year_week_start_save = startWeek.replace("年", "").replace("周", "");
            startDay = CommUtil.weekToStartDateDay(arrival_year_week_start_save);
            // System.out.println("startDay:" + startDay);
            // startweekblag = true;
        }
        if (arrival_year_week_end_save != null && !"".equals(arrival_year_week_end_save)) {
            String endtWeek = CommUtil.getWeekOfYear_Sunday_Normal(arrival_year_week_end_save, null, "1");
            arrival_year_week_end_save = endtWeek.replace("年", "").replace("周", "");
            endDay = CommUtil.weekToEndDateDay(arrival_year_week_end_save);
            // System.out.println("endDay:" + endDay);
            // endweekblag = true;
        }
        // if (startweekblag && endweekblag) {
        //
        // } else {
        // if (startweekblag) {
        // arrival_end_week = arrival_start_week;
        // } else {
        // arrival_start_week = arrival_end_week;
        // }
        // }
        // 权限Map
        Map<String, Object> authMap = new HashMap<String, Object>();
        // 取得产品组权限List和渠道权限List
        commPurchase.getAuthMap(request,product_group_id_save,
        		ed_channel_id_save, cbs_catgory_save, authMap);
        params.put("arrival_start_week", startDay);
        params.put("arrival_end_week", endDay);
        if (datestart_save != null && !"".equals(datestart_save)) {
            datestart_save = datestart_save.replace("年", "").replace("周", "");
        }
        if (dateend_save != null && !"".equals(dateend_save)) {
            dateend_save = dateend_save.replace("年", "").replace("周", "");
        }
        params.put("report_year_week_start", datestart_save);
        params.put("report_year_week_end", dateend_save);
        params.put("ed_channel_id", authMap.get("channel"));
//        params.put("ed_channel_id", ed_channel_id_save);
        params.put("product_group_id", authMap.get("productGroup"));
//        params.put("product_group_id", product_group_id_save);
        params.put("order_id", wp_order_id);
        params.put("oms_order_id", oms_order_id_save);
        params.put("brand_id", brand_save);
        params.put("materials_id", materials_id);
        params.put("materials_desc", materials_description);
        params.put("storage_id", storage_id);
        // 订单类别
        params.put("order_category", order_category_save);
        String[] flow_flag_list = null;
        if (flow_flag_save != null && !"".equals(flow_flag_save)) {
            // flow_flag转化为数组
            flow_flag_list = flow_flag_save.split(",");
        }
        params.put("flow_flag", flow_flag_list);

        params.put("shipment_combination_id", shipment_combination_id);
        params.put("gvs_order_id", gvs_order_id);
        params.put("custom_order_id", custom_order_id);
        params.put("customization", customization_save);
        params.put("order_type", order_type_save);
        params.put("category_id", authMap.get("cbsCategory"));
//        params.put("category_id", cbs_catgory_save);
        
        HSSFWorkbook  wb= getDetailsData(params);
        
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");  
        java.util.Date date=new java.util.Date();  
        String str=sdf.format(date); 
        String fileName = "订单列表"+str;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            wb.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
          
            ExportExcelUtil.exportCommon(is,fileName,response);
        } catch (IOException e) {
            logger.error("错误", e);
        }
    }
    
    /**
     * T+2审核提交画面导出数据处理（将不同DB中检索出的数据组织在一起） 李超
     *
     * @param Map <String, Object> params 检索条件中的参数
     * @return result 需要显示或导出数据结果
     */
    public HSSFWorkbook getDetailsData(Map<String, Object> params) {
        ServiceResult<List<T2OrderItem>> result = t2OrderQueryService.getT2OrderList(params);

       // 获得条数
       List<T2OrderItem> orderList = new ArrayList<T2OrderItem>();
       if (result.getSuccess() && result.getResult() != null) {
           orderList = result.getResult();
       }
      // 1.创建一个workbook，对应一个Excel文件
         HSSFWorkbook wb = new HSSFWorkbook();
         // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
         HSSFSheet sheet = wb.createSheet("订单列表");
         int length = ExportData.orderListQuery.length;
         for (int i = 0; i <length; i++) {
        	 sheet.setColumnWidth(i, (int)(21.57*256));
		}
         
         // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
         HSSFRow row = sheet.createRow((int) 0);
         // 4.创建单元格，设置值表头，设置表头居中
         HSSFCellStyle style = wb.createCellStyle();
         // 居中格式
         style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
         style.setFillForegroundColor(HSSFColor.SKY_BLUE.index); 
         style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
         style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
         style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
         style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边
         // 设置表头
         for(int i=0;length-1>=i;i++){
           HSSFCell cell = row.createCell(i);
           cell.setCellValue(ExportData.orderListQuery[i]);
           cell.setCellStyle(style);          
         }
         Map<String, String> orderCategoryMap = commPurchase.getValueMeaningMap(
                 dataDictionaryService, ORDER_CATEGORY);
         
         Map<String, String> invstockchannelmap = new HashMap<String, String>();// 渠道
         
//         commPurchase.getChannelMap(invstockchannelmap);
         invstockchannelmap = t2OrderService
					.getChannelMapByCode(invstockchannelmap);
         invstockchannelmap = t2OrderService.getChannelMapByCode(invstockchannelmap);
         // 品牌数据存入HashMap
         Map<String, String> brandMap = new HashMap<String, String>();
         // 取得品牌
         commPurchase.getBrandMap(brandMap);
         
         Map<String, String> trueOrFalseMap = commPurchase.getValueMeaningMap(
                 dataDictionaryService, TRUE_FALSE_DISTINCT);
         
         Map<String, String> flowFlagMap = commPurchase.getValueMeaningMap(
                 dataDictionaryService, T2_ORDER_STATUS);
         
         Map<String, String> productgroupmap = new HashMap<String, String>();// 产品组
		  commPurchase.getProductMap(productgroupmap);

         //向单元格里添加数据  
         for(short i=0;i<orderList.size();i++){              
             row = sheet.createRow(i+1);  
             row.createCell(0).setCellValue(orderCategoryMap.get(String.valueOf(orderList.get(i).getOrder_category())));  
             row.createCell(1).setCellValue(flowFlagMap.get(String.valueOf(orderList.get(i).getFlow_flag())));  
             row.createCell(2).setCellValue(orderList.get(i).getOrder_id());  
             row.createCell(3).setCellValue(orderList.get(i).getOrder_num_73());  
             row.createCell(4).setCellValue(orderList.get(i).getSend_flag());  
             row.createCell(5).setCellValue(orderList.get(i).getChannel_commit_time_display());  
             row.createCell(6).setCellValue(orderList.get(i).getChannel_commit_user());  
             row.createCell(7).setCellValue(orderList.get(i).getAudit_time_display());  
             row.createCell(8).setCellValue(orderList.get(i).getAudit_user());  
             row.createCell(9).setCellValue(orderList.get(i).getOrder_close_time_display());  
             row.createCell(10).setCellValue(orderList.get(i).getOrder_close_user()); 
             row.createCell(11).setCellValue(orderList.get(i).getOms_order_id());  
             row.createCell(12).setCellValue(orderList.get(i).getCustom_desc());  
             row.createCell(13).setCellValue("重庆新日日顺家电销售有限公司");
             row.createCell(14).setCellValue(orderList.get(i).getTransmit_desc());             
             // 提报周
             String report_year_week = orderList.get(i).getReport_year_week();
             if(!"".equals(report_year_week)){
             	// 编辑提报年
                 row.createCell(15).setCellValue(report_year_week.substring(0, 4) + "年");
                 row.createCell(16).setCellValue(report_year_week.substring(4) + "周");
             }
             
             String arrivalWeek = CommUtil.getWeekOfYear_Sunday_Normal(
           		  orderList.get(i).getLatest_arrive_date_display(), null, "1");

             row.createCell(17).setCellValue(arrivalWeek);
             row.createCell(18).setCellValue(arrivalWeek);
             row.createCell(19).setCellValue(orderList.get(i).getIndustry_trade_desc());
             row.createCell(20).setCellValue(invstockchannelmap.get(orderList.get(i).getEd_channel_id()));  
            // row.createCell(18).setCellValue(orderList.get(i).getEd_channel_name());
             row.createCell(21).setCellValue(orderList.get(i).getCategory_id());  
             row.createCell(22).setCellValue(productgroupmap.get(orderList.get(i).getProduct_group_id()));  
             //row.createCell(20).setCellValue(orderList.get(i).getProduct_group_name());  
             row.createCell(23).setCellValue(brandMap.get(orderList.get(i).getBrand_id()));  
             //row.createCell(21).setCellValue(orderList.get(i).getBrand_id());  
             row.createCell(24).setCellValue(orderList.get(i).getMaterials_id());  
             row.createCell(25).setCellValue(orderList.get(i).getMaterials_desc());  
             row.createCell(26).setCellValue(orderList.get(i).getOrder_type_name());  
             row.createCell(27).setCellValue(String.valueOf(orderList.get(i).getT2_delivery_prediction()));  
             row.createCell(28).setCellValue(String.valueOf(orderList.get(i).getPrice())); 
             row.createCell(29).setCellValue(String.valueOf(orderList.get(i).getAmount()));  
             row.createCell(30).setCellValue(orderList.get(i).getStorage_id());  
             row.createCell(31).setCellValue(orderList.get(i).getStorage_name());
             row.createCell(32).setCellValue(orderList.get(i).getArrival_storage_desc());
             row.createCell(33).setCellValue(orderList.get(i).getSeries_id());
             row.createCell(34).setCellValue(orderList.get(i).getStatus());
             row.createCell(35).setCellValue(orderList.get(i).getLatest_arrive_date_display());
             row.createCell(36).setCellValue(orderList.get(i).getPlan_deliver_date_display());
             row.createCell(37).setCellValue(orderList.get(i).getPromise_arrive_date_display());
             row.createCell(38).setCellValue(orderList.get(i).getActual_deliver_date_display());
             row.createCell(39).setCellValue(orderList.get(i).getPredict_arrive_date_display());  
             row.createCell(40).setCellValue(orderList.get(i).getIndustry_trade_take_date_display());  
             row.createCell(41).setCellValue(orderList.get(i).getCustom_sign_date_display());  
             row.createCell(42).setCellValue(orderList.get(i).getReturn_order_date_display());  
             row.createCell(43).setCellValue(orderList.get(i).getLatest_arrive_date_display());  
             row.createCell(44).setCellValue(orderList.get(i).getFactory_id());  
             row.createCell(45).setCellValue(orderList.get(i).getFactory_name());  
             row.createCell(46).setCellValue(orderList.get(i).getShipment_combination_id());
             if(orderList.get(i).getSign_num() != null){
                 row.createCell(47).setCellValue(String.valueOf(orderList.get(i).getSign_num()));
             }else {
                 row.createCell(47).setCellValue("");
             }
//             row.createCell(45).setCellValue(String.valueOf(orderList.get(i).getSign_num()));
             row.createCell(48).setCellValue(orderList.get(i).getNo_pass_reason());  
             row.createCell(49).setCellValue(orderList.get(i).getGvs_order_id());
             row.createCell(50).setCellValue(orderList.get(i).getDn_id());
             row.createCell(51).setCellValue(orderList.get(i).getCustpodetailcode());
             row.createCell(52).setCellValue(orderList.get(i).getCommit_time_display());
             row.createCell(53).setCellValue(trueOrFalseMap.get(String.valueOf(orderList.get(i) .getCustomization())));
           //  row.createCell(51).setCellValue(orderList.get(i).getCustomization_name());
             row.createCell(54).setCellValue(orderList.get(i).getSatisfy_type_name());
             if (orderList.get(i).getWAqty() != null){
                 row.createCell(55).setCellValue(String.valueOf(orderList.get(i).getWAqty()));
             }else {
                 row.createCell(55).setCellValue("");
             }
//             row.createCell(53).setCellValue(String.valueOf(orderList.get(i).getWAqty()));
             row.createCell(56).setCellValue(orderList.get(i).getError_msg());
             row.createCell(57).setCellValue(orderList.get(i).getPass_reason());
             
           }
		return wb;

   }
    /**
     * T+2点击全部导出、导出Excel
     *
     * @param report_year_week 填报周
     * @param channel          渠道
     * @param product_group    产品组
     * @param order_id         订单号
     * @param materials_id     物料号
     * @param storage_id       库位码
     * @param flow_flag        状态
     * @param response
     * @return 方法执行完毕调用的画面
     */
    
    @RequestMapping(value = {"/exportAllPoList.export"})
    void exportAllPOList(@RequestParam(required = false) String report_start_week,
            @RequestParam(required = false) String report_end_week,
            @RequestParam(required = false) String channel,
            @RequestParam(required = false) String product_group,
            @RequestParam(required = false) String order_id,
            @RequestParam(required = false) String storage_id,
            @RequestParam(required = false) String flow_flag,
            @RequestParam(required = false) String materials_id,
            @RequestParam(required = false) String bill_order_id,
            @RequestParam(required = false) String so_id,
            @RequestParam(required = false) String dn_id,
            @RequestParam(required = false) String bill_time_start,
            @RequestParam(required = false) String bill_time_end,
            @RequestParam(required = false) String datestorge_start,
            @RequestParam(required = false) String datestorge_end,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String cbsCategory,
            @RequestParam(required = false) String materials_desc,
            @RequestParam(required = false) Integer rows,
            @RequestParam(required = false) Integer page,
            HttpServletRequest request,
            HttpServletResponse response) {

        // 权限Map
        Map<String, Object> authMap = new HashMap<String, Object>();
        // 取得产品组权限List,渠道权限List和品类List
        commPurchase.getAuthMap(request, product_group,channel, cbsCategory, authMap);
        Map<String, Object> params = new HashMap<String, Object>();
        
        String startDay = "";
        String endDay = "";
        // Boolean startweekblag = false;
        // Boolean endweekblag = false;
        if (report_start_week != null && !"".equals(report_start_week)) {
            String startWeek = CommUtil.getWeekOfYear_Sunday_Normal(report_start_week, null,
                    "1");
            report_start_week = startWeek.replace("年", "").replace("周", "");
            startDay = CommUtil.weekToStartDateDay(report_start_week);
            // startweekblag = true;
        }
        if (report_end_week != null && !"".equals(report_end_week)) {
            String endtWeek = CommUtil.getWeekOfYear_Sunday_Normal(report_end_week, null, "1");
            report_end_week = endtWeek.replace("年", "").replace("周", "");
            endDay = CommUtil.weekToEndDateDay(report_end_week);
            // endweekblag = true;
        }


        if (bill_time_end != null && !"".equals(bill_time_end)) {
            DateCal dateCal_billstart = new DateCal(bill_time_end);
            bill_time_end = dateCal_billstart.decDay(-1);
        }
        if (datestorge_end != null && !"".equals(datestorge_end)) {
            DateCal dateCal_datestorgeend = new DateCal(datestorge_end);
            datestorge_end = dateCal_datestorgeend.decDay(-1);
        }
        String[] flow_flag_list = null;
        if (flow_flag != null && !"".equals(flow_flag)) {
            if (flow_flag.contains("ALL")) {
                flow_flag_list = null;
            } else {
                flow_flag_list = flow_flag.split(",");
            }
        }
        // 权限Map
//        Map<String, Object> authMap = new HashMap<String, Object>();
        
        params.put("flow_flag", flow_flag_list);
        params.put("report_start_week", startDay);
        params.put("report_end_week", endDay);
        params.put("product_group_id", authMap.get("productGroup"));
        params.put("ed_channel_id", authMap.get("channel"));
        params.put("wp_order_id", AllToNull(order_id));
        params.put("storage_id", storage_id);
        params.put("materials_id", materials_id);
        params.put("bill_time_start", bill_time_start);
        params.put("bill_time_end", bill_time_end);
        params.put("bill_order_id", bill_order_id);
        params.put("so_id", so_id);
        params.put("dn_id", dn_id);
        params.put("datestorge_start", datestorge_start);
        params.put("datestorge_end", datestorge_end);
        params.put("brand_id", AllToNull(brand));
        params.put("category_id", authMap.get("cbsCategory"));
        params.put("materials_desc", materials_desc);    
        HSSFWorkbook  wb=getPODetailsExportData(params);
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");  
          java.util.Date date=new java.util.Date();  
          String str=sdf.format(date); 
          String fileName = "订单列表"+str;
          
          ByteArrayOutputStream os = new ByteArrayOutputStream();
          try {
          wb.write(os);
          byte[] content = os.toByteArray();
          InputStream is = new ByteArrayInputStream(content);

        ExportExcelUtil.exportCommon(is,fileName,response);
      } catch (IOException e) {
        logger.error("错误", e);
      }       
        
    }
    
    
    @RequestMapping(value = {"/exportPoList.export"})
    void exportPOList(@RequestParam(required = false) String exportData,HttpServletResponse res) {
    	
        Map<String, Object> params = new HashMap<String, Object>();
        JSONArray exportJson = new JSONArray();
        String[] exportArray = null;
        try {
            if (exportData != null && !exportData.equals("")) {
                exportJson = (JSONArray) JSON.parse(exportData);
                exportArray = new String[exportJson.length()];
                // JSONArray转化为list
                for (int i = 0; i < exportJson.length(); i++) {
                    exportArray[i] = (String) exportJson.get(i);
                }
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
            logger.error("JSON转换失败！ 错误：" + e1.getMessage());
        }
        params.put("bill_order_list", exportArray);       
        HSSFWorkbook  wb=getPODetailsData(params);
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");  
          java.util.Date date=new java.util.Date();  
          String str=sdf.format(date); 
          String fileName = "订单列表"+str;
          
          ByteArrayOutputStream os = new ByteArrayOutputStream();
          try {
          wb.write(os);
          byte[] content = os.toByteArray();
          InputStream is = new ByteArrayInputStream(content);

        ExportExcelUtil.exportCommon(is,fileName,res);
      } catch (IOException e) {
        logger.error("错误", e);
      }
 		
    }
    HSSFWorkbook getPODetailsExportData(Map<String, Object> params) {

        Integer pageIndex = 0;
        List<CrmOrderItem> t2CrmOrderItemList= new ArrayList<>();

        while (true){
            params.put("m",1000*pageIndex);
            params.put("n",1000);
            pageIndex++;
            List<CrmOrderItem> list = t2OrderQueryService.getPOExportList(params);
            if(list.isEmpty()||list.size()==0){
                break;
            }
            t2CrmOrderItemList.addAll(list);
        }
        List<CrmOrderItem> predictstocklist =new ArrayList<CrmOrderItem>();
        if (t2CrmOrderItemList.size()!=0 && t2CrmOrderItemList != null) {
            predictstocklist = t2CrmOrderItemList;
        }
        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("订单列表");
        int length = CrmExportData.poListExportQuery.length;
        for (int i = 0; i <length; i++) {

            sheet.setColumnWidth(i, (int)(21.57*256));

        }
        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 4.创建单元格，设置值表头，设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边
        // 设置表头
        for(int i=0;length-1>=i;i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(CrmExportData.poListExportQuery[i]);
            cell.setCellStyle(style);
        }
        // 渠道和产品组数据存入HashMap
        Map<String, String> productgroupmap = new HashMap<String, String>();
        Map<String, String> invstockchannelmap = new HashMap<String, String>();
        // 取得产品组
        commPurchase.getProductMap(productgroupmap);
        // 取得渠道
//          commPurchase.getChannelMap(invstockchannelmap);
        invstockchannelmap = t2OrderService
                .getChannelMapByCode(invstockchannelmap);

        Map<String, String> flowFlagMap = commPurchase.getValueMeaningMap(
                dataDictionaryService, T2_ORDER_STATUS);
        //向单元格里添加数据
        for(short i=0;i<predictstocklist.size();i++){
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(predictstocklist.get(i).getOrder_id());
            row.createCell(1).setCellValue(predictstocklist.get(i).getPo_id());
            row.createCell(2).setCellValue(predictstocklist.get(i).getBill_order_id());
            row.createCell(3).setCellValue(predictstocklist.get(i).getBill_time_display());
            row.createCell(4).setCellValue(predictstocklist.get(i).getSo_id());
            row.createCell(5).setCellValue(predictstocklist.get(i).getDn_id());
            row.createCell(6).setCellValue(invstockchannelmap.get(predictstocklist.get(i).getEd_channel_id()));
            row.createCell(7).setCellValue(predictstocklist.get(i).getCategory_id());
            row.createCell(8).setCellValue(productgroupmap.get(predictstocklist.get(i).getProduct_group_id()));
            row.createCell(9).setCellValue(predictstocklist.get(i).getMaterials_id());
            row.createCell(10).setCellValue(predictstocklist.get(i).getMaterials_desc());
            row.createCell(11).setCellValue(predictstocklist.get(i).getQty());
            row.createCell(12).setCellValue(String.valueOf(predictstocklist.get(i).getPrice()));
            row.createCell(13).setCellValue(String.valueOf(predictstocklist.get(i).getT2_amount()));
            row.createCell(14).setCellValue(String.valueOf(predictstocklist.get(i).getAmount()));
            row.createCell(15).setCellValue(String.valueOf(predictstocklist.get(i).getTotal()));
            row.createCell(16).setCellValue(predictstocklist.get(i).getStorage_id());
            row.createCell(17).setCellValue(predictstocklist.get(i).getStorage_name());
            row.createCell(18).setCellValue(flowFlagMap.get(String.valueOf(predictstocklist.get(i).getFlow_flag())));
            //row.createCell(18).setCellValue(predictstocklist.get(i).getFlow_flag_name());
            row.createCell(19).setCellValue(predictstocklist.get(i).getRrs_out_time_display());
            row.createCell(20).setCellValue(predictstocklist.get(i).getWa_in_time_display());
            row.createCell(21).setCellValue(check(predictstocklist.get(i).getWAqty()));
//            row.createCell(22).setCellValue(SapStatus(predictstocklist.get(i).getSapStatus()));
//            row.createCell(23).setCellValue(predictstocklist.get(i).getSapMessage());
//            row.createCell(24).setCellValue(predictstocklist.get(i).getSapProcessTime());


        }
        return wb;

    }
    HSSFWorkbook getPODetailsData(Map<String, Object> params) {
       ServiceResult<List<CrmOrderItem>> result = t2OrderQueryService.getPOList(params);
        
        List<CrmOrderItem> predictstocklist =new ArrayList<CrmOrderItem>();
        if (result.getSuccess() && result.getResult() != null) {
            predictstocklist = result.getResult();
        }
       // 1.创建一个workbook，对应一个Excel文件
          HSSFWorkbook wb = new HSSFWorkbook();
          // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
          HSSFSheet sheet = wb.createSheet("订单列表");
          int length = CrmExportData.poListQuery.length;
          for (int i = 0; i <length; i++) {
        	  
        	  sheet.setColumnWidth(i, (int)(21.57*256));
			
		}                        
          // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
          HSSFRow row = sheet.createRow((int) 0);
          // 4.创建单元格，设置值表头，设置表头居中
          HSSFCellStyle style = wb.createCellStyle();
          // 居中格式
          style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
          style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
          style.setFillForegroundColor(HSSFColor.SKY_BLUE.index); 
          style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
          style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
          style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
          style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边
          // 设置表头
          for(int i=0;length-1>=i;i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(CrmExportData.poListQuery[i]);
            cell.setCellStyle(style);          
          }
          // 渠道和产品组数据存入HashMap
          Map<String, String> productgroupmap = new HashMap<String, String>();
          Map<String, String> invstockchannelmap = new HashMap<String, String>();
          // 取得产品组
          commPurchase.getProductMap(productgroupmap);
          // 取得渠道
//          commPurchase.getChannelMap(invstockchannelmap);
          invstockchannelmap = t2OrderService
					.getChannelMapByCode(invstockchannelmap);
          
          Map<String, String> flowFlagMap = commPurchase.getValueMeaningMap(
                  dataDictionaryService, T2_ORDER_STATUS);
          //向单元格里添加数据  
          for(short i=0;i<predictstocklist.size();i++){              
              row = sheet.createRow(i+1);  
              row.createCell(0).setCellValue(predictstocklist.get(i).getOrder_id());  
              row.createCell(1).setCellValue(predictstocklist.get(i).getPo_id());  
              row.createCell(2).setCellValue(predictstocklist.get(i).getBill_order_id());  
              row.createCell(3).setCellValue(predictstocklist.get(i).getBill_time_display());  
              row.createCell(4).setCellValue(predictstocklist.get(i).getSo_id());  
              row.createCell(5).setCellValue(predictstocklist.get(i).getDn_id());  
              row.createCell(6).setCellValue(invstockchannelmap.get(predictstocklist.get(i).getEd_channel_id()));  
              row.createCell(7).setCellValue(predictstocklist.get(i).getCategory_id());  
              row.createCell(8).setCellValue(productgroupmap.get(predictstocklist.get(i).getProduct_group_id())); 
              row.createCell(9).setCellValue(predictstocklist.get(i).getMaterials_id());  
              row.createCell(10).setCellValue(predictstocklist.get(i).getMaterials_desc());  
              row.createCell(11).setCellValue(predictstocklist.get(i).getQty());
              row.createCell(12).setCellValue(String.valueOf(predictstocklist.get(i).getPrice()));             
	          row.createCell(13).setCellValue(String.valueOf(predictstocklist.get(i).getT2_amount()));
               row.createCell(14).setCellValue(String.valueOf(predictstocklist.get(i).getAmount()));
               row.createCell(15).setCellValue(String.valueOf(predictstocklist.get(i).getTotal()));
              row.createCell(16).setCellValue(predictstocklist.get(i).getStorage_id());
              row.createCell(17).setCellValue(predictstocklist.get(i).getStorage_name());
              row.createCell(18).setCellValue(flowFlagMap.get(String.valueOf(predictstocklist.get(i).getFlow_flag()))); 
              //row.createCell(18).setCellValue(predictstocklist.get(i).getFlow_flag_name());
              row.createCell(19).setCellValue(predictstocklist.get(i).getRrs_out_time_display());  
              row.createCell(20).setCellValue(predictstocklist.get(i).getWa_in_time_display());
              row.createCell(21).setCellValue(check(predictstocklist.get(i).getWAqty()));
              row.createCell(22).setCellValue(SapStatus(predictstocklist.get(i).getSapStatus()));
              row.createCell(23).setCellValue(predictstocklist.get(i).getSapMessage());
              row.createCell(24).setCellValue(predictstocklist.get(i).getSapProcessTime());


          }
		return wb;
   
    }

    public String check(Integer param){
        if(param==null){
            return "";
        }else {
            return String.valueOf(param);
        }
    }
    public String SapStatus(int param){
        if(param==1){
            return "成功";
        }else if(param==0){
            return "未推送";
        }else if(param==2){
            return "推送失败";
        }
        return "";
    }
	 public String AllToNull(String param){
			
	    	if("ALL".equals(param)||"全部".equals(param)){
	    		
	    		param=null;
	    		
	    	}
	    	return param;

	    }
    
	    /**
	     * PO导出所需数据处理
	     *
	     * @param exportData 导出数据
	     * @param response
	     * @param request
	     * @param modelMap   状态
	     * @return 方法执行完毕调用的画面
	     * @throws IOException 
	     */
/*	    public void getPoData(HSSFWorkbook wb) throws IOException {
        	
            SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");  
            java.util.Date date=new java.util.Date();  
            String str=sdf.format(date); 
            String fileName = "PO详情列表"+str;
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
	            wb.write(os);
	            byte[] content = os.toByteArray();
	            InputStream is = new ByteArrayInputStream(content);
		        ExportExcelUtil.exportCommon(is,fileName,res);
	        } catch (IOException e) {
	        	logger.error("错误", e);
	        }finally{
	        	if(os != null)
	        		os.close();
	        }
	    }*/
}
