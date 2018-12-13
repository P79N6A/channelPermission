package com.haier.svc.api.controller.stock;

import com.alibaba.dubbo.common.utils.Assert;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;

import com.haier.shop.model.CBSKCType;
import com.haier.shop.model.QueryLesStockOutType;
import com.haier.shop.model.QueryLesStockToCbs;
import com.haier.stock.model.InvBaseStockExcel;
import com.haier.stock.service.LESService;
import com.haier.svc.api.controller.excel.LesBaseStockExport;
import com.haier.svc.api.controller.excel.LesStockQueryExport;
import com.haier.svc.api.controller.stock.mode.BaseInventoryModel;
import com.haier.svc.api.controller.stock.mode.ItemAttributeModel;
import com.haier.svc.api.controller.util.excel.MultiHeadExcelHandler;
import java.util.LinkedList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = { "/selectStock" })
public class LesStockQueryController {
    @Autowired
    private LESService lesService;
    private org.apache.log4j.Logger logger       = org.apache.log4j.LogManager.getLogger(this
            .getClass());

    @Autowired
    private ItemAttributeModel itemAttributeModel;
    @Autowired
    private BaseInventoryModel baseInventoryModel;
    @RequestMapping(value = { "/getLesBaseStockPage.html" }, method = { RequestMethod.GET })
    public String LesloadBaseStockPage() {
        return "stock/lesBaseStockList";
    }
    @RequestMapping(value = { "/getCbsBaseStockPage" }, method = { RequestMethod.GET })
    public String CbsloadBaseStockPage() {
        return "stock/cbsBaseStockList";
    }
    @ResponseBody
    @RequestMapping(value = {"/getLesBaseStockList"})
    public JSONObject queryBaseStockList(String startDate,String endDate,String secCode,String sku,String stockQty,String avaiableQty,String itemProperty,
                                     String cbsCategory,String productName,Integer page, Integer rows)throws Exception {
        Map<String,String> conditionMap=new HashMap<>();
        if (StringUtil.isEmpty(startDate)){
            conditionMap.put("startDate",null);
        }else {
            conditionMap.put("startDate",startDate);
        }
        if (StringUtil.isEmpty(endDate)){
            conditionMap.put("endDate",null);
        }else {
            conditionMap.put("endDate",endDate);
        }
        if (StringUtil.isEmpty(secCode)){
            conditionMap.put("secCode",null);
        }else {
            conditionMap.put("secCode",secCode);
        }
        if (StringUtil.isEmpty(sku)){
            conditionMap.put("sku",null);
        }else {
            conditionMap.put("sku",sku);
        }
        if (StringUtil.isEmpty(stockQty)){
            conditionMap.put("stockQty",null);
        }else {
            conditionMap.put("stockQty",stockQty);
        }
        if (StringUtil.isEmpty(avaiableQty)){
            conditionMap.put("avaiableQty",null);
        }else {
            conditionMap.put("avaiableQty",avaiableQty);
        }
        if (StringUtil.isEmpty(itemProperty)||itemProperty.equals("-1")){
            conditionMap.put("itemProperty",null);
        }else {
            conditionMap.put("itemProperty",itemProperty);
        }
        if (StringUtil.isEmpty(cbsCategory)){
            conditionMap.put("cbsCategory",null);
        }else {
            conditionMap.put("cbsCategory",cbsCategory);
        }
        if (StringUtil.isEmpty(productName)){
            conditionMap.put("productName",null);
        }else {
            conditionMap.put("productName",productName);
        }
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);
        JSONObject jsonObject= baseInventoryModel.queryBaseStockRows(pager,conditionMap);
        return jsonObject;
    }
    /**
     * LES库存数查询---CBS使用
     * @param request
     * @param modelMap
     * @param sku
     * @param lesSecCode
     * @return
     */
    @RequestMapping(value = { "/getLesStockList.html" })
    public String getLesStockList(HttpServletRequest request, Map<String, Object> modelMap,
                                  @RequestParam(required = false) String sku,
                                  @RequestParam(required = false) String lesSecCode) {
        try{
            QueryLesStockToCbs stock=new QueryLesStockToCbs();
            stock.setSecCode(lesSecCode);
            stock.setSku(sku);
            stock.setFlag("4");//CBS库存对账使用
            ServiceResult<QueryLesStockOutType> res=lesService.queryLesStock(stock);
            if(res.getSuccess()){
                List<CBSKCType> lists=res.getResult().getCbskc();
                modelMap.put("rowList", lists);
            }
        }catch(Exception e){
            logger.error("[LesStockQueryController_getLesStockList]查询les库存数量时发生未知错误", e);
        }
        return "stock/lesStockList";
    }
    @RequestMapping(value = { "/exportLesBaseStockList.html" }, method = { RequestMethod.GET })
    public void exportLesBaseStockList(HttpServletRequest request, Map<String, Object> modelMap,
                                         @RequestParam(required = false) String itemProperty,
                                         HttpServletResponse response) {
        try{
            Assert.notNull(baseInventoryModel, "Property 'baseInventoryModel' is required.");
            PagerInfo page = new PagerInfo(999999,1);
            InvBaseStockExcel invBaseStock= handleParameters(itemProperty,request);
            List<InvBaseStockExcel> rowList = baseInventoryModel.queryBaseStockRows1(invBaseStock, page);
            /*modelMap.put("rowList", rowList);
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date=sdf.format(new Date());
            String fileName = "CBS库存数据报表";
            fileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
            System.out.println(fileName);
           // fileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".xls\"");*/
            List<LesBaseStockExport> myDatas = new LinkedList<LesBaseStockExport>();
            for (InvBaseStockExcel excel : rowList) {
                LesBaseStockExport export = new LesBaseStockExport();
                export = JSON.parseObject(JSON.toJSONString(excel), LesBaseStockExport.class);
//            BeanUtils.copyProperties(invStockTransExport,demo);
                myDatas.add(export);
            }
            MultiHeadExcelHandler excelHandler = new MultiHeadExcelHandler(LesBaseStockExport.class);
            excelHandler.setData(myDatas);
            excelHandler.exportExcel(response);
        }catch(Exception e){
            logger.error("[LesStockQueryController_queryBaseStockList]导出库存时发生未知错误", e);
        }
//        return "stock/exportLesBaseStockRowList";
    }
    public InvBaseStockExcel handleParameters(String itemProperty, HttpServletRequest request){
        InvBaseStockExcel invBaseStock = new InvBaseStockExcel();
        String secCode = request.getParameter("secCode");
        String sku = request.getParameter("sku");
        invBaseStock.setSecCode(secCode == null ? "" : secCode.trim());
        invBaseStock.setSku(sku == null ? "" : sku.trim());
        if (itemProperty.equals("-1")){
            invBaseStock.setItemProperty(null);
        }else{
            invBaseStock.setItemProperty(itemProperty);
        }

        String stockQty = request.getParameter("stockQty");
        String avaiableQty = request.getParameter("avaiableQty");
        String product_type_name = request.getParameter("product_type_name");
        invBaseStock.setCbsCategory(product_type_name);
        invBaseStock.setStockQty(StringUtil.isEmpty(stockQty) ? null : Integer.parseInt(stockQty
                .trim()));
        invBaseStock.setAvaiableQty(StringUtil.isEmpty(avaiableQty) ? null : Integer.parseInt(avaiableQty
                .trim()));
        String productName = request.getParameter("productName");
        invBaseStock.setProductName(StringUtil.isEmpty(productName) ? null : productName.trim());

        String startDate = request.getParameter("startDate");
        if(StringUtils.isNotEmpty(startDate)){
            invBaseStock.setStartDate(startDate);
        }
        String endDate = request.getParameter("endDate");
        if(StringUtils.isNotEmpty(endDate)){
            invBaseStock.setEndDate(endDate);
        }
        return invBaseStock;
    }
    @RequestMapping(value = { "/getLesStockList1" })
    @ResponseBody
    public JSONObject getLesStockList1(HttpServletRequest request, Map<String, Object> modelMap,
                                  @RequestParam(required = false) String sku,
                                  @RequestParam(required = false) String lesSecCode) {
        try{
            QueryLesStockToCbs stock=new QueryLesStockToCbs();
            stock.setSecCode(lesSecCode);
            stock.setSku(sku);
            stock.setFlag("4");//CBS库存对账使用
            ServiceResult<QueryLesStockOutType> res=lesService.queryLesStock(stock);
            if(res.getSuccess()){
                List<CBSKCType> lists=res.getResult().getCbskc();
                if (lists==null){
                    JSONObject jsonObject=jsonResult(lists,0);
                    return jsonObject;
                }
                modelMap.put("rowList", lists);
                JSONObject jsonObject=jsonResult(lists,lists.size());
                return jsonObject;
            }
        }catch(Exception e){
            logger.error("[LesStockQueryController_getLesStockList]查询les库存数量时发生未知错误", e);
        }
        JSONObject jsonObject=new JSONObject();
        return jsonObject;
    }
    private <T> JSONObject jsonResult(List<T> list, long total) {
        JSONObject json = new JSONObject();
        json.put("total", total);
        if (list == null || list.isEmpty()) {
            json.put("rows", new ArrayList<T>());
        } else {
            json.put("rows", list);
        }
        return json;
    }
    @RequestMapping(value = { "/exportLesStockList.html" }, method = { RequestMethod.GET })
    public void exportLesStock(HttpServletRequest request, Map<String, Object> modelMap,
                                 @RequestParam(required = false) String sku,
                                 @RequestParam(required = false) String lesSecCode,
                                 HttpServletResponse response){
        try{
            QueryLesStockToCbs stock=new QueryLesStockToCbs();
            stock.setSecCode(lesSecCode);
            stock.setSku(sku);
            stock.setFlag("4");//CBS库存对账使用
            ServiceResult<QueryLesStockOutType> res=lesService.queryLesStock(stock);
            List<CBSKCType> lists = new ArrayList<CBSKCType>();
            if(res.getSuccess()){
                lists=res.getResult().getCbskc();
                modelMap.put("rowList", lists);
            }

            List<LesStockQueryExport> myDatas = new LinkedList<LesStockQueryExport>();
            if (null != lists){
                for (CBSKCType excel : lists) {
                    LesStockQueryExport export = new LesStockQueryExport();
                    BeanUtils.copyProperties(excel,export);
                    myDatas.add(export);
                }
            }
            MultiHeadExcelHandler excelHandler = new MultiHeadExcelHandler(LesStockQueryExport.class);
            excelHandler.setData(myDatas);
            excelHandler.exportExcel(response);

            /*response.setContentType("application/vnd.ms-excel;charset=utf-8");
            String fileName = "LES库存数据报表";
            fileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".xls\"");*/
        }catch(Exception e){
            logger.error("[LesStockQueryController_getLesStockList]导出les库存时发生未知错误", e);
        }
//        return "stock/exportLesStockList";
    }
}
