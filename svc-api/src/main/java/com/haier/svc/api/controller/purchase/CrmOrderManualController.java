package com.haier.svc.api.controller.purchase;

import com.google.gson.Gson;
import com.haier.purchase.data.model.*;
import com.haier.shop.model.ItemBase;
import com.haier.stock.model.InvRrsWarehouse;
import com.haier.stock.model.InvWarehouse;
import com.haier.stock.service.StockCommonService;
import com.haier.svc.api.controller.util.*;
import com.haier.svc.api.controller.util.date.DateCal;
import com.haier.svc.api.service.CommPurchase;
import com.haier.svc.bean.GVSOrderPriceRequire;
import com.haier.svc.bean.GVSOrderPriceResponse;
import com.haier.svc.service.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by 李超 on 2018/3/12.
 */

@Controller
@RequestMapping(value = "CrmOrderManual")
public class CrmOrderManualController {

        private final static Logger       logger                  = LoggerFactory
                .getLogger(CrmOrderManualController.class);

        @Autowired
        private CrmOrderManualService crmOrderManualService;

        // CRM手工采购订单录入
        private final String              ADD_CRM_ORDER_MANUAL    = "add";
        // CRM手工采购订单修改
        private final String              MODIFY_CRM_ORDER_MANUAL = "modify";
        // 订单类型
        private static final String       BILL_TYPE               = "bill_type";
        // 配送方式
        private static final String       SAP_FLOW_NUM_NAME       = "sap_flow_num";
        // 是否区分
        private static final String       TRUE_FALSE_DISTINCT     = "true_or_false";
        // 订单状态
        private static final String       MANUAL_ORDER_FLOW_FLAG  = "manual_order_flow_flag";

        @Autowired
        private ItemService itemService;

        @Autowired
        private StockCommonService stockCommonService;

        @Autowired
        private PurchaseBaseCommonService purchaseBaseCommonService;

        @Autowired
        CommPurchase commPurchase;

        @Autowired
        private PurchaseCommonService     purchaseCommonService;

//        @Resource(name = "gvsOrderService")
//        private GVSOrderService           gvsOrderService;

        @Autowired
        private DataDictionaryService dataDictionaryService;

        @Autowired
        private T2OrderService t2OrderService;

        @Autowired
        private HaierInRrsItemService     haierInRrsItemService;

        @Autowired
        private ProductPaymentService     productPaymentService;

        @RequestMapping(value = "/toCrmOrderManualQueryList")
        public String CrmOrderManual(){
            return "purchase/crmOrderManualQueryList";
        }


        /**
         * 跳转至CRM手工采购订单录入页面
         *
         * @param request
         */
        @RequestMapping(value = { "/gotoAddCRMOrderManualDetail" }, method = { RequestMethod.GET })
        String gotoAddCRMOrderManualDetail(@RequestParam(required = false) String modifyData,
                                           HttpServletRequest request, Map<String, Object> modelMap) {
            logger.debug("CrmOrderManualControl:gotoAddCRMOrderManualDetail");

            // 通过共通方法获取产品组和品牌map待用
            Map<String, String> productgroupMap = new HashMap<String, String>();
            Map<String, String> brandMap = new HashMap<String, String>();
            commPurchase.getProductMap(productgroupMap);
            commPurchase.getBrandMap(brandMap);

            // 获取是否区分map
            Map<String, String> trueOrFalseMap = commPurchase.getValueMeaningMap(dataDictionaryService,
                    TRUE_FALSE_DISTINCT);
            CrmOrderManualDetailItem crmOrderManualDetailItem = new CrmOrderManualDetailItem();

            // 价格接口呼出,价格情报取得

            // 通过前台的隐藏变量modifyData判断操作为新增还是修改
            if (modifyData == null || modifyData.equals("")) {
                // 隐藏变量未传值——新增，向前台传递操作类型
                modelMap.put("operatorType", "");
                crmOrderManualDetailItem.setCustMgr("00022923");
                crmOrderManualDetailItem.setPorMgr("00022923");
                crmOrderManualDetailItem.setProDputy("00022923");
                crmOrderManualDetailItem.setMaker(WebUtil.currentUserName(request));
                // 设置默认值
                crmOrderManualDetailItem.setBillType("ZGOR");
                crmOrderManualDetailItem.setSap_flow_num(2);
                modelMap.put("crmOrderManualDetailItem", crmOrderManualDetailItem);

            } else if ("add".equals(modifyData)) {
                // 隐藏变量未传值——新增，向前台传递操作类型
                modelMap.put("operatorType", ADD_CRM_ORDER_MANUAL);
                crmOrderManualDetailItem.setCustMgr("00022923");
                crmOrderManualDetailItem.setPorMgr("00022923");
                crmOrderManualDetailItem.setProDputy("00022923");
                crmOrderManualDetailItem.setMaker(WebUtil.currentUserName(request));
                // 设置默认值
                crmOrderManualDetailItem.setBillType("ZGOR");
                crmOrderManualDetailItem.setSap_flow_num(2);
                modelMap.put("crmOrderManualDetailItem", crmOrderManualDetailItem);

            } else {
                // 隐藏变量已传值——修改，向前台传递操作类型
                modelMap.put("operatorType", MODIFY_CRM_ORDER_MANUAL);

                // 在画面中获取WP订单号，放入map，待检索
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("order_id", modifyData);

                // 根据map检索数据
                ServiceResult<List<CrmOrderManualDetailItem>> result = crmOrderManualService
                        .getCrmOrderManualList(params);
                crmOrderManualDetailItem = result.getResult().get(0);

                // 根据检索结果在map中取名称
                String product_group_name = productgroupMap
                        .get(crmOrderManualDetailItem.getProduct_group_id());
                String brand_name = brandMap.get(crmOrderManualDetailItem.getBrand_id());
                // 将取得的名称set入entity
                crmOrderManualDetailItem.setProduct_group_name(product_group_name);
                crmOrderManualDetailItem.setBrand_name(brand_name);
                crmOrderManualDetailItem.setIsFLName(
                        trueOrFalseMap.get(String.valueOf(crmOrderManualDetailItem.getIsFL())));
                crmOrderManualDetailItem.setIsKPOName(
                        trueOrFalseMap.get(String.valueOf(crmOrderManualDetailItem.getIsKPO())));
                modelMap.put("crmOrderManualDetailItem", crmOrderManualDetailItem);
                modelMap.put("rrsList",
                        JsonUtil.toJson(getRrsList(crmOrderManualDetailItem.getEstorge_id())));
            }

            String url = request.getHeader("referer");
            modelMap.put("url", url);
            return "purchase/crmOrderManualDetail";
        }

        /**
         * CRM手工采购订单录入
         *
         * @param request
         */
        @RequestMapping(value = { "/addCRMOrderManualDetail" }, method = { RequestMethod.POST })
        @ResponseBody
        HttpJsonResult<String> addCRMOrderManualDetail(@ModelAttribute("filterForm") CrmManualOrder crmManualOrder,
                                                       @RequestParam(required = false) String operatorType,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response) {
            HttpJsonResult<String> result = new HttpJsonResult<String>();
            logger.debug("CrmOrderManualControl:addCRMOrderManualDetail");

            Map<String, Object> params = new HashMap<String, Object>();

            // 取得提交者
            String user = WebUtil.currentUserName(request);

            // 从画面的form中获取两个entity
            CrmOrderManualItem crmOrderManualItem = crmManualOrder.getCrmOrderManualItem();
            CrmOrderManualDetailItem crmOrderManualDetailItem = crmManualOrder
                    .getCrmOrderManualDetailItem();
            // 验证来源单号和数量 star
            String sourceOrderId = crmOrderManualItem.getSource_order_id();
            if (StringUtils.isNotEmpty(sourceOrderId)) {
                Map<String, Object> condition = new HashMap<String, Object>();
                List<String> orderList = new ArrayList<String>();

                orderList.add(sourceOrderId);
                condition.put("order_list", orderList);
                condition.put("source_order_id", sourceOrderId);
                ServiceResult<List<T2OrderItem>> t2Items = t2OrderService.getT2OrderList(condition);
                List<T2OrderItem> t2OrderItem = t2Items.getResult();
                // 通过T2订单验证来源单号
                if (t2Items.getPager().getRowsCount() < 1) {
                    result.setTotalCount(0);
                    // 来源单号验证失败
                    result.setMessage("来源单号不存在");
                    return result;
                }
                // 验证数量
                Integer Count = haierInRrsItemService.getOrderNum(condition); // crm订单数量
                if (crmOrderManualDetailItem.getQty() > Count) {
                    result.setTotalCount(0);
                    // 数量验证失败
                    result.setMessage("数量不能超过" + Count);
                    return result;
                }
                int Qty = crmOrderManualDetailItem.getQty();
                if (Qty > 0) {
                    String sourceOrderIdByPurchase = purchaseCommonService
                            .getCrmSubOrderId(sourceOrderId, Qty);
                    crmOrderManualItem.setSource_order_id(sourceOrderId);//来源单号
                    crmOrderManualItem.setReleBillCode(sourceOrderIdByPurchase);//关联单号 （子单号）
                } else {
                    crmOrderManualItem.setSource_order_id(sourceOrderId);//来源单号
                    crmOrderManualItem.setReleBillCode(sourceOrderId);//关联单号 （子单号）
                }
            }
            // end
            // 为两个entity设置创建用户
            if (ADD_CRM_ORDER_MANUAL.equals(operatorType)) {
                // WP订单号
                crmOrderManualItem
                        .setWp_order_id(commPurchase.getWPOrderId(purchaseCommonService, "C02"));
                //来源单号为空  置关联单号为手工采购单号
                if (StringUtils.isEmpty(sourceOrderId)) {
                    crmOrderManualItem.setReleBillCode(crmOrderManualItem.getWp_order_id());//关联单号 （子单号）
                }
                // 设置创建用户
                crmOrderManualItem.setCreate_user(user);
                crmOrderManualDetailItem.setCreate_user(user);
            } else if (MODIFY_CRM_ORDER_MANUAL.equals(operatorType)) {
                // 设置修改用户
                crmOrderManualItem.setModify_user(user);
                crmOrderManualDetailItem.setModify_user(user);
            } else if ("".equals(operatorType)) {
                // WP订单号
                crmOrderManualItem
                        .setWp_order_id(commPurchase.getWPOrderId(purchaseCommonService, "C02"));
                operatorType = "add";
                crmOrderManualItem.setCreate_user(user);
                crmOrderManualDetailItem.setCreate_user(user);
            }
            // 将订单号同时赋CRM手工采购单详情entity
            crmOrderManualDetailItem.setWp_order_id(crmOrderManualItem.getWp_order_id());

            // 价格情报取得
            GVSOrderPriceRequire order = new GVSOrderPriceRequire();
            order.setCorpCode(crmOrderManualItem.getSaleOrgCode());
            order.setCustCode(crmOrderManualItem.getCustCode());
            order.setInvCode(crmOrderManualDetailItem.getMaterials_id());
            order.setRegionCode(crmOrderManualItem.getRegionId());
            GVSOrderPriceResponse priceResponse = crmOrderManualService.quirePrice(order);

            // 价格情报设定
            crmOrderManualDetailItem.setActPrice(new Float(priceResponse.getReActPrice()));
            crmOrderManualDetailItem.setBateMoney(new Float(priceResponse.getReBateMoney()));
            crmOrderManualDetailItem.setBateRate(new Float(priceResponse.getReBateRate()));
            crmOrderManualDetailItem.setIsFL(
                    StringUtils.isNotEmpty(priceResponse.getReIsFL()) ? "0" : priceResponse.getReIsFL());
            crmOrderManualDetailItem.setIsKPO(
                    StringUtils.isNotEmpty(priceResponse.getReIsKPO()) ? "0" : priceResponse.getReIsKPO());
            crmOrderManualDetailItem.setLossRate(new Float(priceResponse.getReLossRate()));
            crmOrderManualDetailItem.setRetailPrice(new Float(priceResponse.getReRetailPrice()));
            crmOrderManualDetailItem.setStockPrice(new Float(priceResponse.getReStockPrice()));
            crmOrderManualDetailItem.setUnitPrice(new Float(priceResponse.getReUnitPrice()));
            // 设置系统标识码
            crmOrderManualItem.setSysFlag(301);
            // 库存类型
            if ("ZGOR".equals(crmOrderManualItem.getBillType())) {
                crmOrderManualItem.setStock_type(6);
            }
            // 当前日期取得
            String currentDate = CommUtil.getCurrentDate("yyyy-MM-dd");
            // 上周日期取得
            DateCal dateCal = new DateCal(currentDate);
            // 运输时效取得
            Map<String, Object> invRrsWarehouseParams = new HashMap<String, Object>();
            invRrsWarehouseParams.put("estorge_id", crmOrderManualItem.getEstorge_id());
            invRrsWarehouseParams.put("rrs_wh_code", crmOrderManualItem.getWhCode());
            ServiceResult<List<InvRrsWarehouse>> invRrsWarehouseResult = purchaseBaseCommonService
                    .getAllRrsWhByEstorgeId(invRrsWarehouseParams);

            String planInDate = dateCal.decDay(
                    -invRrsWarehouseResult.getResult().get(0).getTransport_prescription()) + " 00:00:00";
            // crmOrderManualItem.setPlanInDateStr(planInDate);
            // crmOrderManualItem.setPlanInDate(CommUtil.getStringToDate(planInDate,
            // CommUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
            // 根据产品组code取得品类
            Map<String, String> categoryMap = new HashMap<String, String>();
            commPurchase.getCategoryMap(categoryMap);
            String categoryId = categoryMap.get(crmOrderManualDetailItem.getProduct_group_id());
            crmOrderManualDetailItem.setCategory_id(categoryId);
            crmOrderManualDetailItem.setInvState(10);

            // 处理类型放入map
            params.put("operatorType", operatorType);

            //海华要求如果渠道是商城和顺逛custCode设置为C200130028  修改位置提前至add保存前  lupeng  2017/03/01
            if (StringUtils.isNotEmpty(crmOrderManualDetailItem.getEd_channel_id())
                    && (crmOrderManualDetailItem.getEd_channel_id().equalsIgnoreCase("SC")
                    || crmOrderManualDetailItem.getEd_channel_id().equalsIgnoreCase("RS"))) {
                crmOrderManualItem.setCustCode("C200130028");
                crmOrderManualDetailItem.setCustCode("C200130028");
            }
            // 处理完毕的参数放入map
            params.put("CrmOrderManualItem", crmOrderManualItem);
            params.put("CrmOrderManualDetailItem", crmOrderManualDetailItem);

            // 执行插入service
            ServiceResult<Boolean> insertResult = crmOrderManualService.editCRMOrderManual(params);

            if (!insertResult.getSuccess() || insertResult.getResult() == false) {
                result.setTotalCount(0);
                // 保存失败
                result.setMessage("保存失败！");
                return result;
            }

            // CRM系统提交
            crmOrderManualItem.setPlanInDateStr(planInDate);
            List<CrmOrderManualDetailItem> crmOrderManualDetailItemList = new ArrayList<CrmOrderManualDetailItem>();
            crmOrderManualDetailItemList.add(crmOrderManualDetailItem);

            ServiceResult<Boolean> commitCRMResult = crmOrderManualService
                    .insertWAOrderBillToCRM(crmOrderManualItem, crmOrderManualDetailItemList);
            if (commitCRMResult.getSuccess() && commitCRMResult.getResult() != null
                    && commitCRMResult.getResult() == true) {

                result.setTotalCount(1);
                // CRM系统提交成功
                result.setMessage("保存成功,CRM系统提交成功！");
            } else {
                result.setTotalCount(1);
                // CRM系统提交失败
                result.setMessage("保存成功,CRM系统提交失败！"+commitCRMResult.getMessage());
            }

            return result;
        }

        /**
         * 订单提交
         *
         * @param request
         * @param commitData
         *            提交数据
         */
        @RequestMapping(value = { "/commitOrderManual" }, method = { RequestMethod.POST })
        @ResponseBody
        public HttpJsonResult<String> commitOrderManual(HttpServletRequest request,
                                                        @RequestParam(required = true) String commitData) {
            HttpJsonResult<String> result = new HttpJsonResult<String>();
            if (commitData != null) {
                try {
                    // 取得提交数据
                    JSONArray commitjson = (JSONArray) JSON.parse(commitData);
                    List<String> commitList = new ArrayList<String>();
                    // JSONArray转化为list
                    for (int i = 0; i < commitjson.length(); i++) {
                        commitList.add(commitjson.getString(i));
                    }
                    // 取得提交者
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("order_id_list", commitList);

                    ServiceResult<List<CrmOrderManualDetailItem>> crmList = crmOrderManualService
                            .getCrmOrderManualList(params);
                    // 订单提交更新
                    ServiceResult<Boolean> commitResult = crmOrderManualService
                            .commitOrderManual(params);
                    if (commitResult.getSuccess() && commitResult.getResult() != null
                            && commitResult.getResult() == true) {
                        // DB操作成功
                        result.setMessage("提交成功！");
                    } else {
                        // DB操作失败
                        result.setMessage("提交失败！");
                    }
                } catch (ParseException e) {
                    logger.error("", e);
                    throw new BusinessException("JSON转化失败" + e.getMessage());
                }
            }
            return result;
        }

        /**
         * 删除订单
         *
         * @param request
         * @param deleteData
         *            要删除的行
         * @return
         */
        @RequestMapping(value = { "/deleteOrderManual" }, method = { RequestMethod.POST })
        @ResponseBody
        public HttpJsonResult<String> deleteOrderManual(HttpServletRequest request,
                                                        @RequestParam(required = true) String deleteData) {
            HttpJsonResult<String> result = new HttpJsonResult<String>();
            if (deleteData != null) {
                try {
                    // 取得删除数据
                    JSONArray deletejson = (JSONArray) JSON.parse(deleteData);
                    List<String> deleteList = new ArrayList<String>();
                    // JSONArray转化为list
                    for (int i = 0; i < deletejson.length(); i++) {
                        deleteList.add(deletejson.getString(i));
                    }
                    // 取得登录用户
                    String user = WebUtil.currentUserName(request);
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("deleteList", deleteList);
                    params.put("delete_user", user);
                    // 订单删除

                    ServiceResult<Boolean> deleteResult = crmOrderManualService
                            .deleteOrderManual(params);
                    if (deleteResult.getSuccess() && deleteResult.getResult() != null
                            && deleteResult.getResult() == true) {
                        // DB操作成功
                        result.setMessage("删除成功！");
                    } else {
                        // DB操作失败
                        result.setMessage("删除失败！");
                    }
                } catch (ParseException e) {
                    logger.error("", e);
                    throw new BusinessException("JSON转化失败" + e.getMessage());
                }
            }
            return result;
        }

        /**
         * 根据物料号获取数据（型号，品牌名称，品牌code，产品组名称，产品组code）
         *
         * @param request
         * @param materials_id
         *            物料号
         */
        @RequestMapping(value = { "/getDataByMaterialsId" }, method = { RequestMethod.POST })
        @ResponseBody
        public HttpJsonResult<String> getDataByMaterialsId(HttpServletRequest request,
                                                           @RequestParam(required = true) String materials_id,
                                                           @RequestParam(required = true) String area_id,
                                                           @RequestParam(required = true) String region_id,
                                                           @RequestParam(required = true) String custCode,
                                                           @RequestParam(required = true) String corpCode,
                                                           @RequestParam(required = true) String qty) {
            HttpJsonResult<String> result = new HttpJsonResult<String>();

            // 通过共通方法获取产品组和品牌map待用
            Map<String, String> productgroupMap = new HashMap<String, String>();
            Map<String, String> brandMap = new HashMap<String, String>();
            commPurchase.getProductMap(productgroupMap);
            commPurchase.getBrandMap(brandMap);

            // 权限Map
            Map<String, String> authMap = new HashMap<String, String>();
            // 取得产品组权限List,渠道权限List和品类List
//            commPurchase.getAuthMap(purchaseCommonService, request, null, null, null, authMap);

            if (materials_id != null && !materials_id.equals("")) {
                // 调用service执行检索
                ServiceResult<List<ItemBase>> itemResult = itemService
                        .findItemBaseByMaterialId(materials_id);
                if (itemResult.getSuccess() && itemResult.getResult() != null
                        && itemResult.getResult().size() > 0) {
                    // DB操作成功
                    ItemBase item = itemResult.getResult().get(0);
                    // 型号
                    String materials_desc = item.getMaterialDescription();
                    // 品牌code
                    String brand_id = item.getProBand();
                    // 品牌名称
                    String brand_name = brandMap.get(brand_id);
                    // 产品组code
                    String product_group_id = item.getDepartment();
                    // 产品组名称
                    String product_group_name = productgroupMap.get(product_group_id);
                    //付款方
                    String payment_name = "";
                    //付款方代码
                    custCode = "";

                    if (product_group_name != null && !product_group_name.equals("")) {
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("productName", product_group_name);
                        List<ProductPayment> ppList = productPaymentService
                                .findPaymentNameByCode(params);
                        if (ppList.size() > 0) {
                            payment_name = ppList.get(0).getPayMentName();
                            custCode = ppList.get(0).getPayMentCode();
                        }

                    }

                    authMap = t2OrderService.getProductMap(authMap);
                    List<String> productGroupList = new ArrayList<>();
                    Iterator<Map.Entry<String, String>> it = authMap.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, String> entry = it.next();
                        productGroupList.add(entry.getKey());
                    }

//                    String[] authProductGroup = (String[]) authMap.get("productGroup");
//                    List<String> productGroupList = Arrays.asList(authProductGroup);
                    // 判断产品组id是否合法
                    if (productGroupList.contains(product_group_id)) {
                        // 产品组id合法，所需要的数据汇总并存入map
                        Map<String, Object> Data = new HashMap<String, Object>();
                        Data.put("payment_name", payment_name);//付款方
                        Data.put("custCode", custCode);//付款方代码
                        Data.put("materials_desc", materials_desc);
                        Data.put("brand_id", brand_id);
                        Data.put("brand_name", brand_name);
                        Data.put("product_group_id", product_group_id);
                        Data.put("product_group_name", product_group_name);

                        if (area_id != null && !area_id.equals("")) {
                            // 预算体取得
                            Map<String, Object> params = new HashMap<String, Object>();
                            params.put("product_group_id", product_group_id);
                            params.put("area_id", area_id);
                            ServiceResult<InvBudgetOrg> invBudgetOrg = purchaseBaseCommonService
                                    .getAllBudgetOrg(params);

                            if (invBudgetOrg.getSuccess() && invBudgetOrg.getResult() != null) {
                                Data.put("budgetOrg", invBudgetOrg.getResult().getBudgetorg_id());
                                Data.put("budgetOrgName", invBudgetOrg.getResult().getBudgetorg_name());
                            }
                        }

                        if (region_id != null && !region_id.equals("")) {
                            // 价格情报取得
                            GVSOrderPriceRequire order = new GVSOrderPriceRequire();
                            order.setCorpCode(corpCode);
                            order.setCustCode(custCode);
                            order.setInvCode(materials_id);
                            order.setRegionCode(region_id);
                            GVSOrderPriceResponse priceResponse = crmOrderManualService.quirePrice(order);
                            // 价格情报设定
                            if (qty != null && !qty.equals("") && !qty.equals("0")) {
                                Data.put("sumMoney", (new BigDecimal(priceResponse.getReUnitPrice()))
                                        .multiply(new BigDecimal(qty)));
                            }
                            Data.put("unitPrice", priceResponse.getReUnitPrice());
                            Data.put("stockPrice", priceResponse.getReStockPrice());
                            Data.put("retailPrice", priceResponse.getReRetailPrice());
                            Data.put("actPrice", priceResponse.getReActPrice());
                            Data.put("bateRate", priceResponse.getReBateRate());
                            Data.put("bateMoney", priceResponse.getReBateMoney());
                            Data.put("lossRate", priceResponse.getReLossRate());
                            Data.put("isFL", StringUtils.isNotEmpty(priceResponse.getReIsFL()) ? "0"
                                    : priceResponse.getReIsFL());
                            Data.put("isKPO", StringUtils.isNotEmpty(priceResponse.getReIsKPO()) ? "0"
                                    : priceResponse.getReIsKPO());
                            // 获取是否区分map
                            Map<String, String> trueOrFalseMap = commPurchase
                                    .getValueMeaningMap(dataDictionaryService, TRUE_FALSE_DISTINCT);
                            Data.put("isFLName", trueOrFalseMap.get(Data.get("isFL")));
                            Data.put("isKPOName", trueOrFalseMap.get(Data.get("isKPO")));
                        }

                        // 数据集转化为json
                        String jsonData = JsonUtil.toJson(Data);
                        // 设置返回值
                        result.setData(jsonData);
                        result.setTotalCount(1);
                    } else {
                        // 产品组id非法，
                        result.setMessage("物料号非法！无权限操作此物料号！");
                        result.setTotalCount(0);
                    }
                } else {
                    // DB操作失败
                    result.setMessage("物料号非法！不存在此物料号！");
                    result.setTotalCount(0);
                }
            }
            return result;
        }

        /**
         * 根据WA库位码获取数据（分公司，工贸，付款方，送达方，WA库位名，配送中心）
         *
         * @param request
         * @param whCode
         *            库位码
         */
        @RequestMapping(value = { "/getDataByEstorgeId" }, method = { RequestMethod.POST })
        @ResponseBody
        public HttpJsonResult<String> getDataByEstorgeId(HttpServletRequest request,
                                                         @RequestParam(required = true) String estorge_id,
                                                         @RequestParam(required = true) String product_group_id,
                                                         @RequestParam(required = true) String materials_id,
                                                         @RequestParam(required = true) String custCode,
                                                         @RequestParam(required = true) String qty) {
            HttpJsonResult<String> result = new HttpJsonResult<String>();
            if (estorge_id != null && !estorge_id.equals("")) {

                // 通过电商库位码获取仓库
                ServiceResult<InvWarehouse> invWarehouseResult = purchaseBaseCommonService
                        .getAllWhByEstorgeId(estorge_id);
                if (invWarehouseResult.getSuccess() && invWarehouseResult.getResult() != null) {
                    Map<String, Object> Data = new HashMap<String, Object>();
                    // WA库位名
                    String estorge_name = invWarehouseResult.getResult().getEstorge_name();
                    // 工贸编码
                    String industry_trade_id = invWarehouseResult.getResult().getIndustry_trade_id();
                    // 工贸描述
                    String industry_trade_desc = invWarehouseResult.getResult()
                            .getIndustry_trade_desc();
                    // 管理客户编码
                    // String custom_id =
                    // invWarehouseResult.getResult().getCustom_id();
                    // 管理客户描述
                    // String custom_desc =
                    // invWarehouseResult.getResult().getCustom_desc();
                    // 付款方
                    // String payment_id = invWarehouseResult.getResult().getPayment_id();
                    //String payment_name = invWarehouseResult.getResult().getPayment_name();
                    // 送达方编码
                    String transmit_id = invWarehouseResult.getResult().getTransmit_id();
                    // 送达方描述
                    String transmit_desc = invWarehouseResult.getResult().getTransmit_desc();
                    // 配送中心编码
                    String distribution_center_id = invWarehouseResult.getResult().getCenterCode();
                    // 配送中心描述
                    String distribution_center_desc = invWarehouseResult.getResult()
                            .getRrs_distribution_name();

                    Data.put("estorge_name", estorge_name);
                    Data.put("corpCode", invWarehouseResult.getResult().getSale_org_id());
                    Data.put("corpName", invWarehouseResult.getResult().getBranch());
                    Data.put("regionId", industry_trade_id);
                    Data.put("industry_trade_desc", industry_trade_desc);
                    // Data.put("custCode", payment_id);
                    //Data.put("payment_name", payment_name);
                    Data.put("destCode", transmit_id);
                    Data.put("esale_name", transmit_desc);
                    Data.put("destCenter", distribution_center_id);
                    Data.put("rrs_distribution_name", distribution_center_desc);
                    Data.put("rrsList", getRrsList(estorge_id));
                    Data.put("areaId", invWarehouseResult.getResult().getArea_id());
                    Data.put("sale_org_id", invWarehouseResult.getResult().getSale_org_id());
                    Data.put("branch", invWarehouseResult.getResult().getBranch());

                    // 预算体取得
                    if (product_group_id != null && !product_group_id.equals("")) {
                        // 预算体取得
                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("product_group_id", product_group_id);
                        params.put("area_id", invWarehouseResult.getResult().getArea_id());
                        ServiceResult<InvBudgetOrg> invBudgetOrg = purchaseBaseCommonService
                                .getAllBudgetOrg(params);
                        if (invBudgetOrg.getSuccess() && invBudgetOrg.getResult() != null) {
                            Data.put("budgetOrg", invBudgetOrg.getResult().getBudgetorg_id());
                            Data.put("budgetOrgName", invBudgetOrg.getResult().getBudgetorg_name());
                        }

                        // 价格情报取得
                        GVSOrderPriceRequire order = new GVSOrderPriceRequire();
                        order.setCorpCode(invWarehouseResult.getResult().getSale_org_id());
                        order.setCustCode(custCode);
                        order.setInvCode(materials_id);
                    /*
                     * 101bug修改 START CRM手工采购调用价格接口用做ZZ001类似的
                     */
                        // order.setRegionCode(industry_trade_id);
                        order.setRegionCode(invWarehouseResult.getResult().getArea_id());
                        // 101bug修改 END
                        GVSOrderPriceResponse priceResponse = crmOrderManualService.quirePrice(order);
                        // 价格情报设定
                        if (qty != null && !qty.equals("") && !qty.equals("0")) {
                            Data.put("sumMoney", (new BigDecimal(priceResponse.getReUnitPrice()))
                                    .multiply(new BigDecimal(qty)));
                        }
                        Data.put("unitPrice", priceResponse.getReUnitPrice());
                        Data.put("stockPrice", priceResponse.getReStockPrice());
                        Data.put("retailPrice", priceResponse.getReRetailPrice());
                        Data.put("actPrice", priceResponse.getReActPrice());
                        Data.put("bateRate", priceResponse.getReBateRate());
                        Data.put("bateMoney", priceResponse.getReBateMoney());
                        Data.put("lossRate", priceResponse.getReLossRate());
                        Data.put("isFL", StringUtils.isNotEmpty(priceResponse.getReIsFL()) ? "0"
                                : priceResponse.getReIsFL());
                        Data.put("isKPO", StringUtils.isNotEmpty(priceResponse.getReIsKPO()) ? "0"
                                : priceResponse.getReIsKPO());
                        // 获取是否区分map
                        Map<String, String> trueOrFalseMap = commPurchase
                                .getValueMeaningMap(dataDictionaryService, TRUE_FALSE_DISTINCT);
                        Data.put("isFLName", trueOrFalseMap.get(Data.get("isFL")));
                        Data.put("isKPOName", trueOrFalseMap.get(Data.get("isKPO")));
                    }
                    // 数据集转化为json
                    String jsonData = JsonUtil.toJson(Data);
                    // 设置返回值
                    result.setData(jsonData);
                    result.setTotalCount(1);
                } else {
                    // DB操作失败
                    result.setMessage("WA库位码非法！不存在此WA库位码！");
                    result.setTotalCount(0);
                }

            }
            return result;
        }

        /**
         * 根据来源单号获取数据（分公司，工贸，付款方，送达方，WA库位名，配送中心）
         *
         * @param request
         * @param whCode
         *
         */
        @RequestMapping(value = { "/getDataBySourceOrderId" }, method = { RequestMethod.POST })
        @ResponseBody
        public HttpJsonResult<String> getDataBySourceOrderId(HttpServletRequest request,
                                                             @RequestParam(required = true) String source_order_id) {
            HttpJsonResult<String> result = new HttpJsonResult<String>();

            if (source_order_id != null && !source_order_id.equals("")) {
                // 通过共通方法获取产品组和品牌map待用
                Map<String, String> productgroupMap = new HashMap<String, String>();
                Map<String, String> brandMap = new HashMap<String, String>();
//                commPurchase.getProductMap(productgroupMap);
//                commPurchase.getBrandMap(brandMap);

                // 通过电商库位码获取仓库
                ServiceResult<T2OrderItem> t2OrderItemResult = purchaseBaseCommonService
                        .getDataBySourceOrderId(source_order_id);

                //logger.info(JsonUtil.toJson(t2OrderItemResult));
                if (t2OrderItemResult.getSuccess() && t2OrderItemResult.getResult() != null) {
                    try {
                        Map<String, Object> Data = new HashMap<String, Object>();
                        // 物料号
                        String materials_id = t2OrderItemResult.getResult().getMaterials_id();
                        // 型号
                        String materials_desc = t2OrderItemResult.getResult().getMaterials_desc();
                        // WA库位名
                        String storage_id = t2OrderItemResult.getResult().getStorage_id();
                        // WA库位名称
                        String storage_name = t2OrderItemResult.getResult().getStorage_name();
                        // 渠道
                        String ed_channel_id = t2OrderItemResult.getResult().getEd_channel_id();
                        // 数量
                        int num, count, waQty, rrsNum;
                        if (t2OrderItemResult.getResult().getT2_delivery_prediction() == null) {
                            count = 0;
                        } else {
                            count = t2OrderItemResult.getResult().getT2_delivery_prediction()
                                    .intValue();
                        }

                    /*// 通过电商库位码获取仓库
                    ServiceResult<InvWarehouse> invWarehouseResult = purchaseBaseCommonService
                            .getAllWhByEstorgeId(storage_id);
                    if (invWarehouseResult.getSuccess()
                            && invWarehouseResult.getResult() != null) {

                    }
                    */

                        //logger.info(JsonUtil.toJson(count));
                        //查询数量
                        //ServiceResult<HaierInRrsItem> haierInRrsResult = purchaseBaseCommonService.getQtyBySourceOrderId(source_order_id);
                        Map<String, Object> condition = new HashMap<String, Object>();
                        condition.put("source_order_id", source_order_id);
                        Integer Count = haierInRrsItemService.getOrderNum(condition); // crm订单数量

                        num = Count;

                        //logger.info(JsonUtil.toJson(num));
                        // 付款方
                        String payment_id = t2OrderItemResult.getResult().getPayment_id();
                        String payment_name = t2OrderItemResult.getResult().getPayment_name();
                        // 工贸编码
                        String industry_trade_id = t2OrderItemResult.getResult().getIndustry_trade_id();
                        // 工贸描述
                        String industry_trade_desc = t2OrderItemResult.getResult()
                                .getIndustry_trade_desc();
                        // 通过电商库位码获取仓库
                        ServiceResult<InvWarehouse> invWarehouseResult = purchaseBaseCommonService
                                .getAllWhByEstorgeId(storage_id);

                        if (invWarehouseResult.getSuccess() && invWarehouseResult.getResult() != null) {

                            String branch = invWarehouseResult.getResult().getBranch();
                            // 送达方编码
                            String transmit_id = t2OrderItemResult.getResult().getTransmit_id();
                            // 送达方描述
                            String transmit_desc = t2OrderItemResult.getResult().getTransmit_desc();
                            // 预算体
                            String product_group_id = t2OrderItemResult.getResult()
                                    .getProduct_group_id();

                            String brand_id = t2OrderItemResult.getResult().getBrand_id();
                            // 品牌名称
                            String brand_name = brandMap.get(brand_id);
                            // 产品组名称
                            String product_group_name = productgroupMap.get(product_group_id);

                            Map<String, Object> params = new HashMap<String, Object>();
                            params.put("product_group_id", product_group_id);
                            params.put("area_id", invWarehouseResult.getResult().getArea_id());
                            //获取预算体
                            ServiceResult<InvBudgetOrg> invBudgetOrg = purchaseBaseCommonService
                                    .getAllBudgetOrg(params);

                            if (invBudgetOrg.getSuccess() && invBudgetOrg.getResult() != null) {
                                Data.put("budgetOrg", invBudgetOrg.getResult().getBudgetorg_id()); //预算体
                                Data.put("getBudgetorg_name", invBudgetOrg.getResult() //预算体名称
                                        .getBudgetorg_name());
                            }

                            // 价格情报取得
                            GVSOrderPriceRequire order = new GVSOrderPriceRequire();
                            order.setCorpCode(invWarehouseResult.getResult().getSale_org_id());
                            order.setCustCode(payment_id);
                            order.setInvCode(materials_id);
                        /*
                         * 101bug修改 START CRM手工采购调用价格接口用做ZZ001类似的
                         */
                            // order.setRegionCode(industry_trade_id);
                            order.setRegionCode(invWarehouseResult.getResult().getArea_id());
                            // 101bug修改 END
                            GVSOrderPriceResponse priceResponse = crmOrderManualService.quirePrice(order);
                            // 价格情报设定
                            if (num > 0) {
                                Data.put("sumMoney", (new BigDecimal(priceResponse.getReUnitPrice()))
                                        .multiply(new BigDecimal(num)));
                            }
                            Data.put("unitPrice", priceResponse.getReUnitPrice());
                            Data.put("stockPrice", priceResponse.getReStockPrice());
                            Data.put("retailPrice", priceResponse.getReRetailPrice());
                            Data.put("actPrice", priceResponse.getReActPrice());
                            Data.put("bateRate", priceResponse.getReBateRate());
                            Data.put("bateMoney", priceResponse.getReBateMoney());
                            Data.put("lossRate", priceResponse.getReLossRate());
                            Data.put("isFL", StringUtils.isNotEmpty(priceResponse.getReIsFL()) ? "0"
                                    : priceResponse.getReIsFL());
                            Data.put("isKPO", StringUtils.isNotEmpty(priceResponse.getReIsKPO()) ? "0"
                                    : priceResponse.getReIsKPO());
                            // 获取是否区分map
                            Map<String, String> trueOrFalseMap = commPurchase
                                    .getValueMeaningMap(dataDictionaryService, TRUE_FALSE_DISTINCT);
                            Data.put("isFLName", trueOrFalseMap.get(Data.get("isFL")));
                            Data.put("isKPOName", trueOrFalseMap.get(Data.get("isKPO")));

                            // 配送中心编码
                            String distribution_center_id = invWarehouseResult.getResult()
                                    .getCenterCode();
                            // 配送中心描述
                            String distribution_center_desc = invWarehouseResult.getResult()
                                    .getRrs_distribution_name();
                            //品牌
                            Data.put("brand_id", brand_id);
                            Data.put("brand_name", brand_name);
                            Data.put("product_group_name", product_group_name);
                            Data.put("product_group_id", product_group_id);
                            Data.put("materials_id", materials_id);
                            Data.put("materials_desc", materials_desc);
                            Data.put("storage_id", storage_id);
                            Data.put("storage_name", storage_name);
                            Data.put("ed_channel_id", ed_channel_id);
                            Data.put("payment_id", payment_id);
                            Data.put("payment_name", payment_name);
                            Data.put("industry_trade_id", industry_trade_id);
                            Data.put("industry_trade_desc", industry_trade_desc);
                            Data.put("branch", branch);
                            Data.put("transmit_id", transmit_id);
                            Data.put("transmit_desc", transmit_desc);
                            Data.put("destCenter", distribution_center_id);
                            Data.put("sale_org_id", invWarehouseResult.getResult().getSale_org_id());
                            Data.put("qty", num);
                            Data.put("corpCode", invWarehouseResult.getResult().getSale_org_id());
                            Data.put("destCode", transmit_id);
                            Data.put("rrs_distribution_name", distribution_center_desc);
                            Data.put("rrsList", getRrsList(storage_id));
                            Data.put("areaId", invWarehouseResult.getResult().getArea_id());
                        }
                        // 数据集转化为json
                        String jsonData = JsonUtil.toJson(Data);
                        // 设置返回值
                        result.setData(jsonData);
                        result.setTotalCount(1);
                    } catch (Exception e) {
                        logger.error("来源单号不匹配或数据错误", e);
                        result.setMessage("来源单号不匹配！");
                    }
                } else {
                    // DB操作失败
                    result.setMessage("来源单号非法！不存在此来源单号！");
                    result.setTotalCount(0);
                }

            }
            return result;
        }

        /**
         * 通过电商库位码获取日日顺仓库List
         *
         * @param estorge_id
         *            WA库位码
         * @return List<InvRrsWarehouse>
         */
        List<InvRrsWarehouse> getRrsList(String estorge_id) {
            //
            Map<String, Object> invRrsWarehouseParams = new HashMap<String, Object>();
            invRrsWarehouseParams.put("estorge_id", estorge_id);
            ServiceResult<List<InvRrsWarehouse>> invRrsResult = purchaseBaseCommonService
                    .getAllRrsWhByEstorgeId(invRrsWarehouseParams);

            if (invRrsResult.getSuccess() && invRrsResult.getResult() != null
                    && invRrsResult.getResult().size() > 0) {
                return invRrsResult.getResult();
            } else {
                return null;
            }
        }

        /**
         * CRM手工采购订单查询
         *
         * @param modelMap
         * @return
         */
        @RequestMapping(value = { "/crmOrderManualQueryList" }, method = { RequestMethod.GET })
        String crmOrderManualQueryList(HttpServletRequest request, Map<String, Object> modelMap) {
            return "purchase/crmOrderManualQueryList";
        }

        /**
         * 获取CRM手工采购订单信息
         *
         * @param report_year_week
         *            提报周
         * @param channel
         *            渠道
         * @param product_group
         *            产品组
         * @param order_id
         *            订单号
         * @param materials_id
         *            物料号
         * @param storage_id
         *            库位号
         * @param flow_flag
         *            状态
         * @param rows
         *            行数
         * @param page
         *            页数
         * @return
         */
        @RequestMapping(value = { "/findCrmOrderManualList" }, method = { RequestMethod.POST })
        void find(@RequestParam(required = false) String order_id,
                  @RequestParam(required = false) String corpCode,
                  @RequestParam(required = false) String whCode,
                  @RequestParam(required = false) String materials_id,
                  @RequestParam(required = false) String materials_desc,
                  @RequestParam(required = false) String brand_id,
                  @RequestParam(required = false) String category_id,
                  @RequestParam(required = false) String bill_type,
                  @RequestParam(required = false) String channel,
                  @RequestParam(required = false) String product_group,
                  @RequestParam(required = false) String flow_flag,
                  @RequestParam(required = false) String planInDate_start,
                  @RequestParam(required = false) String planInDate_end,
                  @RequestParam(required = false) String so_id,
                  @RequestParam(required = false) String dn_id,
                  @RequestParam(required = false) String source_order_id,
                  @RequestParam(required = false) Integer rows,
                  @RequestParam(required = false) Integer page, HttpServletRequest request,
                  HttpServletResponse response) throws java.text.ParseException {
            try {
                if (rows == null)
                    rows = 20;
                if (page == null)
                    page = 1;

                // 权限Map
                Map<String, Object> authMap = new HashMap<String, Object>();
                // 取得产品组权限List,渠道权限List和品类List
//                commPurchase.getAuthMap(purchaseCommonService, request, product_group, channel,
//                        category_id, authMap);
                Map<String, Object> params = new HashMap<String, Object>();
                if (planInDate_end != null && !"".equals(planInDate_end)) {
                    DateCal dateCal_end = new DateCal(planInDate_end);
                    planInDate_end = dateCal_end.decDay(-1);
                }
                params.put("order_id", order_id);
                params.put("corpCode", corpCode);
                params.put("whCode", whCode);
                params.put("materials_id", materials_id);
                params.put("materials_desc", materials_desc);
                params.put("brand_id", brand_id);
                params.put("category_id", authMap.get("cbsCatgory"));
                params.put("bill_type", bill_type);
                params.put("channel", authMap.get("channel"));
                params.put("product_group_id", authMap.get("productGroup"));
                params.put("planInDate_start", planInDate_start);
                params.put("planInDate_end", planInDate_end);
                params.put("so_id", so_id);
                params.put("dn_id", dn_id);
                params.put("source_order_id", source_order_id);
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
                // 品牌
                Map<String, String> brandMap = new HashMap<String, String>();
                // 取得产品组
                commPurchase.getProductMap(productgroupmap);
                // 取得渠道
                commPurchase.getChannelMap(invstockchannelmap);
                // 取得品牌
                commPurchase.getBrandMap(brandMap);
                // 取得订单类型map
                Map<String, String> billTypeMap = commPurchase.getValueMeaningMap(dataDictionaryService,
                        BILL_TYPE);
                // 取得配送方式
                Map<String, String> sapFlowNumNameMap = commPurchase
                        .getValueMeaningMap(dataDictionaryService, SAP_FLOW_NUM_NAME);
                // 获取是否区分map
                Map<String, String> trueOrFalseMap = commPurchase
                        .getValueMeaningMap(dataDictionaryService, TRUE_FALSE_DISTINCT);
                // 获取订单状态map
                Map<String, String> orderFlowFlagMap = commPurchase
                        .getValueMeaningMap(dataDictionaryService, MANUAL_ORDER_FLOW_FLAG);
                // 调用业务Service
                ServiceResult<List<CrmOrderManualDetailItem>> result = crmOrderManualService
                        .getCrmOrderManualList(params);
            /*
             * //获得条数 ServiceResult<Integer> resultcount =
             * crmOrderManualService.getRowCnts();
             */
                if (result.getSuccess() && result.getResult() != null) {
                    List<CrmOrderManualDetailItem> orderManuallist = result.getResult();
                    for (CrmOrderManualDetailItem item : orderManuallist) {
                        // 根据渠道id取得渠道名
                        item.setEd_channel_name(invstockchannelmap.get(item.getEd_channel_id()));
                        // 根据工作组id取得工作组名
                        item.setProduct_group_name(productgroupmap.get(item.getProduct_group_id()));
                        // 根据品牌id品牌名
                        item.setBrand_name(brandMap.get(item.getBrand_id()));
                        // 订单类型名
                        item.setBillTypeName(billTypeMap.get(item.getBillType()));
                        // 配送方式名
                        item.setSap_flow_num_name(
                                sapFlowNumNameMap.get(String.valueOf(item.getSap_flow_num())));
                        // 是否商用空调
                        item.setIsKPOName(trueOrFalseMap.get(item.getIsKPO()));
                        // 是否返利
                        item.setIsFLName(trueOrFalseMap.get(item.getIsFL()));
                        // 状态名称
                        item.setFlow_flag_name(
                                orderFlowFlagMap.get(String.valueOf(item.getFlow_flag())));
                    }
                    Map<String, Object> retMap = new HashMap<String, Object>();
                    Gson gson = new Gson();
                    retMap.put("total", result.getPager().getRowsCount());
                    retMap.put("rows", orderManuallist);
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
         * 获取CRM手工采购订单信息页面点击部分导出、导出Excel
         *
         * @param exportData
         *            导出数据
         * @param response
         * @param request
         * @param modelMap
         *            状态
         * @return 方法执行完毕调用的画面
         */
        @RequestMapping(value = { "/exportCrmList.export" })
        void exportCrmList(@RequestParam(required = false) String exportData,
                            HttpServletResponse response, HttpServletRequest request,
                            Map<String, Object> modelMap) {

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
            params.put("order_id_list", exportArray);
            HSSFWorkbook wb= getDetailsData(params);

            SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
            java.util.Date date=new java.util.Date();
            String str=sdf.format(date);
            String fileName = "CRM手工采购订单列表"+str;

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
         * Crm导出所需数据处理
         *
         * @param exportData
         *            导出数据
         * @param response
         * @param request
         * @param modelMap
         *            状态
         * @return 方法执行完毕调用的画面
         */
        List<CrmOrderManualDetailItem> getCrmData(Map<String, Object> params) {
            ServiceResult<List<CrmOrderManualDetailItem>> result = crmOrderManualService
                    .getCrmOrderManualList(params);

            // 渠道和产品组数据存入HashMap
            Map<String, String> productgroupmap = new HashMap<String, String>();
            Map<String, String> invstockchannelmap = new HashMap<String, String>();
            // 品牌
            Map<String, String> brandMap = new HashMap<String, String>();
            // 取得产品组
            commPurchase.getProductMap(productgroupmap);
            // 取得渠道
            commPurchase.getChannelMap(invstockchannelmap);
            // 取得品牌
            commPurchase.getBrandMap(brandMap);
            // 取得订单类型map
            Map<String, String> billTypeMap = commPurchase.getValueMeaningMap(dataDictionaryService,
                    BILL_TYPE);
            // 取得配送方式
            Map<String, String> sapFlowNumNameMap = commPurchase
                    .getValueMeaningMap(dataDictionaryService, SAP_FLOW_NUM_NAME);
            // 获取是否区分map
            Map<String, String> trueOrFalseMap = commPurchase.getValueMeaningMap(dataDictionaryService,
                    TRUE_FALSE_DISTINCT);
            // 获取订单状态map
            Map<String, String> orderFlowFlagMap = commPurchase
                    .getValueMeaningMap(dataDictionaryService, MANUAL_ORDER_FLOW_FLAG);
            if (result.getSuccess() && result.getResult() != null) {
                List<CrmOrderManualDetailItem> crmList = result.getResult();
                for (CrmOrderManualDetailItem item : crmList) {
                    // 根据渠道id取得渠道名
                    item.setEd_channel_name(invstockchannelmap.get(item.getEd_channel_id()));
                    // 根据工作组id取得工作组名
                    item.setProduct_group_name(productgroupmap.get(item.getProduct_group_id()));
                    // 根据品牌id品牌名
                    item.setBrand_name(brandMap.get(item.getBrand_id()));
                    // 订单类型名
                    item.setBillTypeName(billTypeMap.get(item.getBillType()));
                    // 配送方式名
                    item.setSap_flow_num_name(
                            sapFlowNumNameMap.get(String.valueOf(item.getSap_flow_num())));
                    // 是否商用空调
                    item.setIsKPOName(trueOrFalseMap.get(item.getIsKPO()));
                    // 是否返利
                    item.setIsFLName(trueOrFalseMap.get(item.getIsFL()));
                    // 状态名称
                    item.setFlow_flag_name(orderFlowFlagMap.get(String.valueOf(item.getFlow_flag())));
                }
            }
            List<CrmOrderManualDetailItem> crmOrderList = result.getResult();
            return crmOrderList;
        }

        /**
         * 点击全部导出、导出Excel
         *
         * @param exportData
         *            导出数据
         * @param report_start_week
         * @param report_end_week
         * @param channel
         * @param product_group
         * @param wp_order_id
         * @param storage_id
         * @param flow_flag
         * @param materials_id
         * @param bill_order_id
         * @param so_id
         * @param dn_id
         * @param bill_time_start_hidden
         * @param bill_time_end
         * @param datestorge_start
         * @param datestorge_end
         * @param brand_id
         * @param category_id
         * @param materials_desc
         * @param rows
         * @param response
         * @param modelMap
         *            状态
         * @return 方法执行完毕调用的画面
         */
        @RequestMapping(value = { "/exportAllCrmList.export" })
        void exportAllCrmList(@RequestParam(required = false) String order_id_hidden,
                                @RequestParam(required = false) String CorpCode_hidden,
                                @RequestParam(required = false) String WhCode_hidden,
                                @RequestParam(required = false) String materials_desc_hidden,
                                @RequestParam(required = false) String materials_id_hidden,
                                @RequestParam(required = false) String product_group_id_hidden,
                                @RequestParam(required = false) String ed_channel_id_hidden,
                                @RequestParam(required = false) String brand_hidden,
                                @RequestParam(required = false) String BillType_hidden,
                                @RequestParam(required = false) String flow_flag_hidden,
                                @RequestParam(required = false) String PlanInDate_start_hidden,
                                @RequestParam(required = false) String PlanInDate_end_hidden,
                                @RequestParam(required = false) String so_id_hidden,
                                @RequestParam(required = false) String dn_id_hidden,
                                @RequestParam(required = false) String cbsCategory_hidden,
                                HttpServletResponse response, HttpServletRequest request,
                                Map<String, Object> modelMap) {
            Map<String, Object> params = new HashMap<String, Object>();
            if (PlanInDate_end_hidden != null && !"".equals(PlanInDate_end_hidden)) {
                DateCal dateCal_end = new DateCal(PlanInDate_end_hidden);
                PlanInDate_end_hidden = dateCal_end.decDay(-1);
            }
            // 权限Map
            Map<String, Object> authMap = new HashMap<String, Object>();
            // 取得产品组权限List,渠道权限List和品类List
//            commPurchase.getAuthMap(purchaseCommonService, request, product_group_id_hidden,
//                    ed_channel_id_hidden, cbsCategory_hidden, authMap);
            params.put("order_id", order_id_hidden);
            params.put("corpCode", CorpCode_hidden);
            params.put("whCode", WhCode_hidden);
            params.put("materials_id", materials_id_hidden);
            params.put("materials_desc", materials_desc_hidden);
            params.put("brand_id", brand_hidden);
            params.put("category_id", authMap.get("cbsCatgory"));
            params.put("bill_type", BillType_hidden);
            params.put("channel", authMap.get("channel"));
            params.put("product_group_id", authMap.get("productGroup"));
            params.put("planInDate_start", PlanInDate_start_hidden);
            params.put("planInDate_end", PlanInDate_end_hidden);
            params.put("so_id", so_id_hidden);
            params.put("dn_id", dn_id_hidden);

            // flow_flag转化为数组
            String[] flow_flag_list = null;
            if (flow_flag_hidden != null && !"".equals(flow_flag_hidden)) {
                if (flow_flag_hidden.contains("ALL")) {
                    flow_flag_list = null;
                } else {
                    flow_flag_list = flow_flag_hidden.split(",");
                }
            }
            params.put("flow_flag", flow_flag_list);

            HSSFWorkbook wb = getDetailsData(params);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            java.util.Date date = new java.util.Date();
            String str = sdf.format(date);
            String fileName = "CRM手工采购订单列表" + str;

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            try {
                wb.write(os);
                byte[] content = os.toByteArray();
                InputStream is = new ByteArrayInputStream(content);

                ExportExcelUtil.exportCommon(is, fileName, response);
            } catch (IOException e) {
                logger.error("错误", e);
            }
        }

    public HSSFWorkbook getDetailsData(Map<String, Object> params) {
        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("CRM手工采购订单列表");
        int length = CrmOrderManualExportData.CrmOrderManualListQuery.length;
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
            cell.setCellValue(CrmOrderManualExportData.CrmOrderManualListQuery[i]);
            cell.setCellStyle(style);
        }
        List<CrmOrderManualDetailItem> gc = getCrmData(params);
        //向单元格里添加数据
        for(short i=0;i<gc.size();i++){
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(gc.get(i).getWp_order_id());
            row.createCell(1).setCellValue(gc.get(i).getSource_order_id());
            row.createCell(2).setCellValue(gc.get(i).getPo_id());
            row.createCell(3).setCellValue(gc.get(i).getSo_id());
            row.createCell(4).setCellValue(gc.get(i).getDn_id());
            row.createCell(5).setCellValue(gc.get(i).getEd_channel_name());
            row.createCell(6).setCellValue(gc.get(i).getFlow_flag_name());
            row.createCell(7).setCellValue(gc.get(i).getCorpName());
            row.createCell(8).setCellValue(gc.get(i).getIndustry_trade_desc());
//            row.createCell(9).setCellValue(gc.get(i).getBillType());
            row.createCell(9).setCellValue(gc.get(i).getBillTypeName());
//            row.createCell(11).setCellValue(gc.get(i).getSap_flow_num());
            row.createCell(10).setCellValue(gc.get(i).getSap_flow_num_name());

            row.createCell(11).setCellValue(gc.get(i).getPayment_name());
            row.createCell(12).setCellValue(gc.get(i).getEsale_name());
            row.createCell(13).setCellValue(gc.get(i).getEstorge_id());
            row.createCell(14).setCellValue(gc.get(i).getEstorge_name());
            row.createCell(15).setCellValue(gc.get(i).getWhCode());
            row.createCell(16).setCellValue(gc.get(i).getCustMgr());
            row.createCell(17).setCellValue(gc.get(i).getPorMgr());
            row.createCell(18).setCellValue(gc.get(i).getProDputy());
            row.createCell(19).setCellValue(gc.get(i).getIsKPOName());
            row.createCell(20).setCellValue(gc.get(i).getPlanindate_display());
            row.createCell(21).setCellValue(gc.get(i).getBudgetOrgName());
            row.createCell(22).setCellValue("月度直扣(占PL市场费用)");
            row.createCell(23).setCellValue(gc.get(i).getCorpName());
            row.createCell(24).setCellValue(gc.get(i).getMaker());
            row.createCell(25).setCellValue(gc.get(i).getReleBillCode());
            row.createCell(26).setCellValue(gc.get(i).getRrs_distribution_name());
            row.createCell(27).setCellValue(gc.get(i).getMaterials_id());
            row.createCell(28).setCellValue(gc.get(i).getMaterials_desc());
            row.createCell(29).setCellValue(gc.get(i).getBrand_name());
            row.createCell(30).setCellValue(gc.get(i).getProduct_group_name());
            row.createCell(31).setCellValue(gc.get(i).getInvState());
            row.createCell(32).setCellValue(gc.get(i).getSumMoney());
            row.createCell(33).setCellValue(gc.get(i).getQty());
            row.createCell(34).setCellValue(gc.get(i).getUnitPrice());
            row.createCell(35).setCellValue(gc.get(i).getRetailPrice());
            row.createCell(36).setCellValue(gc.get(i).getActPrice());
            row.createCell(37).setCellValue(gc.get(i).getBateRate());
            row.createCell(38).setCellValue(gc.get(i).getBateMoney());
            row.createCell(39).setCellValue(gc.get(i).getLossRate());
            row.createCell(40).setCellValue(gc.get(i).getIsFLName());
            row.createCell(41).setCellValue(gc.get(i).getBill_time_display());
            row.createCell(42).setCellValue(gc.get(i).getRrs_out_time_display());
            row.createCell(43).setCellValue(gc.get(i).getWa_in_time_display());
            row.createCell(44).setCellValue(gc.get(i).getError_msg());
        }
        return wb;

    }

}
