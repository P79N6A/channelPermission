package com.haier.svc.api.controller.stock;

import com.haier.svc.api.controller.excel.InvStockAgeExport;
import com.haier.svc.api.controller.util.excel.MultiHeadExcelHandler;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.model.ItemBase;
import com.haier.stock.model.InvReservedConfig;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvStockAge.StockAgeData;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.service.TransferLineService;
import com.haier.svc.api.controller.stock.mode.StockModel;
import com.haier.svc.api.controller.stock.mode.StockReservedModel;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.controller.util.WebUtil;

import net.sf.json.JSONObject;

//import net.sf.json.JSONObject;

@Controller
@RequestMapping("stockReserved")
public class StockReservedController {

    @Autowired
    private TransferLineService transferLineService;
    @Autowired
    private StockReservedModel  stockReservedModel;
    @Autowired
    private StockModel          stockModel;

    /**
     * 跳转路径
     * @return
     */
    @RequestMapping(value = { "/stockReservedIndexPage" }, method = { RequestMethod.GET })
    public String loadStockReservedPage() {
        return "stock/stockReservedIndexPage";
    }

    @RequestMapping(value = { "/stockIndexPage" }, method = { RequestMethod.GET })
    public ModelAndView loadStockPage(Map<String, Object> modelMap) {
    	ModelAndView mv = new ModelAndView();
        mv.addObject("canTransferStock","0");
        mv.setViewName("/stock/stockReservedIndexPage");
        return mv;
    }

    /**
     * 查询库存列表
     */
    @RequestMapping(value = { "/queryStockAgeList" })
    @ResponseBody
    public JSONObject queryStockAgeList(HttpServletRequest request, Map<String, Object> modelMap
                                    ) {
    	JSONObject json = new JSONObject();
        Map<String, Object> params = new HashMap<String, Object>();
        String sku = request.getParameter("ssku");
        String secCode = request.getParameter("ssecCode");
        String productTypeName = request.getParameter("product_type_name");
        String channelCode = request.getParameter("channelCode");
        int page = Integer.parseInt(request.getParameter("page"));
        int rows =Integer.parseInt(request.getParameter("rows"));
        params.put("sku", sku);
        params.put("sec_code", secCode);
        params.put("product_type_name", productTypeName);
        params.put("channel_code", channelCode);
        if (rows <= 0) {
        	rows=20;
		    }
        if(page<0){
          page =1;
        }
        PagerInfo pager = new PagerInfo(rows,page);
        List<Map<String, Object>> stockAgeList = stockReservedModel.queryStockReservedList(pager,
            params);
        modelMap.put("rowList", stockAgeList);
        modelMap.put("pager", pager);

        json.put("total", pager.getRowsCount());
        json.put("rows", stockAgeList);
        return json;
    }

    @RequestMapping(value = { "/queryStockAgeListExport" }, method = { RequestMethod.GET })
    public void queryStockAgeListExport(HttpServletRequest request, HttpServletResponse response,
                                          Map<String, Object> modelMap,
                                          @RequestParam(required = false) Integer pageIndex) {
        Map<String, Object> params = new HashMap<String, Object>();
        String sku = request.getParameter("ssku");
        String secCode = request.getParameter("ssecCode");
        String productTypeName = request.getParameter("product_type_name");
        String channelCode = request.getParameter("channelCode");
        params.put("sku", sku);
        params.put("sec_code", secCode);
        params.put("product_type_name", productTypeName);
        params.put("channel_code", channelCode);
        pageIndex=1;
        PagerInfo pager = new PagerInfo(999999, pageIndex);
        List<Map<String, Object>> stockAgeList = stockReservedModel.queryStockReservedList(pager,
            params);
        processData(stockAgeList);
        /*modelMap.put("rowList", stockAgeList);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = "库存统计";
        try {
            fileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".xls\"");
        return "stock/stockReservedRowExport";*/
        List<InvStockAgeExport> myDatas = new LinkedList<InvStockAgeExport>();
        for (Map<String, Object> demo : stockAgeList) {
            InvStockAgeExport invStockAgeExport = new InvStockAgeExport();
            invStockAgeExport.setSku((String) demo.get("sku"));
            invStockAgeExport.setSecCode((String) demo.get("sec_code"));
            invStockAgeExport.setChannelName((String) demo.get("channel_name"));
            invStockAgeExport.setProductTypeName((String) demo.get("product_type_name"));
            invStockAgeExport.setProductName((String) demo.get("product_name"));
            invStockAgeExport.setDayLock1((Integer)demo.get("age1_7"));
            invStockAgeExport.setDayLock2((Integer)demo.get("age7_15"));
            invStockAgeExport.setDayLock3((Integer)demo.get("age15_30"));
            invStockAgeExport.setDayLock4((Integer)demo.get("age30"));
            invStockAgeExport.setLockOccupy(Integer.parseInt(demo.get("channelLockQty").toString()));
            invStockAgeExport.setDayShare1((Integer)demo.get("waage1_7"));
            invStockAgeExport.setDayShare2((Integer)demo.get("waage7_15"));
            invStockAgeExport.setDayShare3((Integer)demo.get("waage15_30"));
            invStockAgeExport.setDayShare4((Integer)demo.get("waage30"));
            invStockAgeExport.setChannelShareOccupy(Integer.parseInt(demo.get("waLockQty").toString()));
            myDatas.add(invStockAgeExport);
        }
        MultiHeadExcelHandler excelHandler = new MultiHeadExcelHandler(InvStockAgeExport.class);
        excelHandler.setData(myDatas);
        excelHandler.exportExcel(response);
    }

    private void processData(List<Map<String, Object>> stockAgeList) {
        List<StockAgeData> stockAgeDatas;
        for (Map<String, Object> mapEntry : stockAgeList) {
            String ageData = this.toString(mapEntry.get("age_data"));
            String waData = this.toString(mapEntry.get("wa_stock_qty"));
            Gson gson = new Gson();
            Type type = new TypeToken<List<StockAgeData>>() {
            }.getType();
            stockAgeDatas = gson.fromJson(ageData, type);

            Integer waQty = waData == null || waData.equals("") ? 0 : Integer.parseInt(waData);
            boolean isWa = waQty > 0;
            Collections.sort(stockAgeDatas, new Comparator<StockAgeData>() {

                @Override
                public int compare(StockAgeData entry1, StockAgeData entry2) {
                    if (entry1 == null) {
                        return 1;
                    }
                    if (entry2 == null) {
                        return -1;
                    }
                    return entry1.getAge() > entry2.getAge() ? -1 : (entry1.getAge() == entry2
                        .getAge() ? 0 : 1);
                }

            });
            int waage1_7 = 0;
            int waage7_15 = 0;
            int waage15_30 = 0;
            int waage30 = 0;
            if (isWa) {
                Map<Integer, Integer> stockAgeData = new HashMap<Integer, Integer>();
                for (StockAgeData entry : stockAgeDatas) {

                    if (waQty <= 0) {
                        break;
                    }
                    int qty = entry.getStockQuantity() - waQty;
                    stockAgeData.put(entry.getAge(), qty >= 0 ? waQty : entry.getStockQuantity());
                    if (qty >= 0) {
                        entry.setStockQuantity(qty);
                        waQty = 0;
                    } else {
                        waQty = waQty - entry.getStockQuantity();
                        entry.setStockQuantity(0);

                    }

                }

                for (Map.Entry<Integer, Integer> itEntry : stockAgeData.entrySet()) {

                    int age = itEntry.getKey();
                    int qty = itEntry.getValue();

                    if (age >= 0 && age <= 7) {
                        waage1_7 += qty;
                    } else if (age > 7 && age <= 15) {
                        waage7_15 += qty;
                    } else if (age > 15 && age <= 30) {
                        waage15_30 += qty;
                    } else {
                        waage30 += qty;
                    }
                }
            }
            mapEntry.put("waage1_7", waage1_7);
            mapEntry.put("waage7_15", waage7_15);
            mapEntry.put("waage15_30", waage15_30);
            mapEntry.put("waage30", waage30);

            int age1_7 = 0;
            int age7_15 = 0;
            int age15_30 = 0;
            int age30 = 0;
            for (StockAgeData entry : stockAgeDatas) {
                int age = entry.getAge();
                int qty = entry.getStockQuantity();
                if (age >= 0 && age <= 7) {
                    age1_7 += qty;
                } else if (age > 7 && age <= 15) {
                    age7_15 += qty;
                } else if (age > 15 && age <= 30) {
                    age15_30 += qty;
                } else {
                    age30 += qty;
                }
            }
            mapEntry.put("age1_7", age1_7);
            mapEntry.put("age7_15", age7_15);
            mapEntry.put("age15_30", age15_30);
            mapEntry.put("age30", age30);
        }

    }

    private String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    @RequestMapping(value = { "/getLockDetails" }, method = { RequestMethod.GET })
    public String getLockDetails(HttpServletRequest request, Map<String, Object> modelMap) {
        String secCode = request.getParameter("secCode");
        String sku = request.getParameter("sku");
        String channel = request.getParameter("channel");
        String isWa = request.getParameter("iswa");
        List<Map<String, Object>> lockDetailList = stockReservedModel.queryLockDetails(secCode,
            sku, channel, isWa);
        modelMap.put("rowList", lockDetailList);
        return "stock/stockLockDetails";
    }

    /**
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "/getStockReserved" }, method = { RequestMethod.GET })
    public String getStockReserved(HttpServletRequest request, Map<String, Object> modelMap) {
        String isReserved = request.getParameter("isReserved");
        modelMap.put("isReserved", Boolean.parseBoolean(isReserved));
        return "stock/saveStockReservedRow";
    }

    /**
     * 通过库位查询调拨轨迹
     */
    public void queryTransferTraceBySecCodeAndSku() {

    }

    /**
     * 检查调拨数量
     */
    @RequestMapping(value = { "/checkTransferCntBySecCodeAndSku" }, method = { RequestMethod.GET })
    public @ResponseBody
    HttpJsonResult<Integer> checkTransferCntBySecCodeAndSku(HttpServletRequest request,
                                                            Map<String, Object> modelMap) {
    	HttpJsonResult<Integer> json = new HttpJsonResult<Integer>();
        String secCode = request.getParameter("secCode");
        String channelFrom = request.getParameter("channelFrom");
        InvSection tempSecFrom = stockModel.getSectionByCode(secCode);
        if(tempSecFrom==null){
        	json.setMessage("通过库存服务获库位信息失败");
        	return json;
        }
        //如果调出库位输入的是共享库位，则和调出渠道做拼接生成新的调出库位
        if (tempSecFrom.isChannelWA()) {
            InvSection secFrom = stockModel.getSectionByCode(tempSecFrom.getWhCode(), channelFrom);
            secCode = secFrom.getSecCode();
        }
        String sku = request.getParameter("sku");
        //InvSection tempSecTo = stockModel.getSectionByCode(secCode);
        String transferCnt = request.getParameter("qty");
        //这里的库位是虚拟库位，直接检查输入库位的数量
        ServiceResult<Integer> result = transferLineService.checkStorageForBaseStock(secCode, sku,
            Integer.parseInt(transferCnt));

        json.setData(result.getResult());
        json.setMessage(result.getMessage());
        return json;
    }

    /**
     * 保存库存锁定
     */
    @RequestMapping(value = { "/saveStockReservedByChannel" }, method = { RequestMethod.GET })
    public @ResponseBody
    HttpJsonResult<Boolean> saveStockReservedByChannel(HttpServletRequest request,
                                                       Map<String, Object> modelMap) {
        HttpJsonResult<Boolean> json = new HttpJsonResult<Boolean>();
        InvTransferLine line = new InvTransferLine();
        line.setTransferReason(InvTransferLine.TRANSFER_REASON_XN);
        Date initDate = null;
        try {
            initDate = new SimpleDateFormat("yyyy-MM-dd").parse("1900-01-01");
        } catch (ParseException e) {
        }
        line.setTransferFeeTime(initDate);
        line.setApproveTime(initDate);
        line.setCreateTime(new Date());
        line.setCreateUser(WebUtil.currentUserName(request));
        line.setRemark("锁定");

        //可以是虚拟库位
        String secCode = request.getParameter("secCode");
        //调出渠道
        String channelFrom = request.getParameter("channelFrom");
        //调入渠道
        String channelTo = request.getParameter("channelTo");
        //物料编号
        String sku = request.getParameter("sku");
        //数量
        String transferCnt = request.getParameter("qty");
        //锁定时间
        Integer lockHours = Integer.parseInt(request.getParameter("lockHours"));

        //查询调拨仓库编码
        InvSection tempSecFrom = stockModel.getSectionByCode(secCode);

        if (tempSecFrom.isChannelWA()) {
            InvSection secFrom = stockModel.getSectionByCode(tempSecFrom.getWhCode(), channelFrom);
            //设置调出库位
            line.setSecFrom(secFrom.getSecCode());
        } else {
            line.setSecFrom(secCode);
        }
        //确认调入库位
        InvSection tempSecTo = stockModel.getSectionByCode(tempSecFrom.getWhCode(), channelTo);
        //设置调入库位
        line.setSecTo(tempSecTo.getSecCode());
        //设置调入渠道
        line.setChannelTo(channelTo);
        //设置调出渠道
        line.setChannelFrom(channelFrom.equals("WA") ? channelTo : channelFrom);

        //1.判断调出库位不能与调入库位相同
        if (secCode.equals(tempSecTo.getSecCode())) {
            json.setMessage("不能对同一库位下物料做操作");
            json.setData(false);
            return json;
        }
        //
        if (tempSecFrom.isChannelRRS()) {
            json.setMessage("不支持的调出渠道");
            json.setData(false);
            return json;
        }

        if (!tempSecFrom.isChannelWA() && !channelFrom.equals(tempSecFrom.getChannelCode())) {
            json.setMessage("调出库位和调出渠道不匹配");
            json.setData(false);
            return json;
        }

        //1.不能是RRS渠道产品；2.预留：调入渠道不能是WA
        if (tempSecTo.isChannelRRS() || tempSecTo.isChannelWA()) {
            json.setMessage("不支持的调入渠道");
            json.setData(false);
            return json;
        }

        //1.设置产品信息
        ItemBase base = stockModel.getItemBaseBySku(sku);
        if (base == null) {
            json.setMessage("找不到物料编码[" + sku + "]对应的产品");
            return json;
        }
        line.setItemCode(sku);
        line.setItemId(base.getId());
        line.setItemName(base.getMaterialDescription());

        line.setTransferQty(Integer.parseInt(transferCnt));
        line.setQty(Integer.parseInt(transferCnt));
        //1.保存调拨记录， 2. 增加单号预留配置; 并启动立即提交
        ServiceResult<String> rs = transferLineService.saveInnerTransfer(line, true);
        String message = rs.getMessage();
        if (rs.getSuccess()) {
            InvReservedConfig reservedConfig = new InvReservedConfig();
            reservedConfig.setAllowUpdate(1);
            reservedConfig.setChannelCode(tempSecTo.getChannelCode());
            reservedConfig.setCreateUser(WebUtil.currentUserName(request));
            reservedConfig.setLockHours(lockHours);
            reservedConfig.setStatus(InvReservedConfig.STATUS_ON);
            reservedConfig.setRef(rs.getResult());
            reservedConfig.setUpdateUser(WebUtil.currentUserName(request));
            ServiceResult<Boolean> reservedResult = stockReservedModel
                .insertReservedConfig(reservedConfig);
            message = reservedResult.getSuccess() ? "锁定成功" : "锁定失败";
        }

        json.setData(rs.getSuccess());
        json.setMessage(message);
        return json;
    }

    /**
     * 解锁
     */
    @RequestMapping(value = { "/releaseStockReservedFromChannel" }, method = { RequestMethod.GET })
    public @ResponseBody
    HttpJsonResult<Boolean> releaseStockReservedFromChannel(HttpServletRequest request,
                                                            Map<String, Object> modelMap) {
        HttpJsonResult<Boolean> json = new HttpJsonResult<Boolean>();
        InvTransferLine line = new InvTransferLine();
        line.setTransferReason(InvTransferLine.TRANSFER_REASON_XN);
        Date initDate = null;
        try {
            initDate = new SimpleDateFormat("yyyy-MM-dd").parse("1900-01-01");
        } catch (ParseException e) {
        }
        line.setTransferFeeTime(initDate);
        line.setApproveTime(initDate);
        line.setCreateTime(new Date());
        line.setCreateUser(WebUtil.currentUserName(request));
        line.setRemark("解锁");

        String secCode = request.getParameter("secCode");
        String channelFrom = request.getParameter("channelFrom");
        String channelTo = request.getParameter("channelTo");
        String sku = request.getParameter("sku");
        String transferCnt = request.getParameter("qty");
        line.setTransferQty(Integer.parseInt(transferCnt));
        line.setQty(Integer.parseInt(transferCnt));
        InvSection tempSecFrom = stockModel.getSectionByCode(secCode);
        if (tempSecFrom.isChannelWA()) {
            InvSection secFrom = stockModel.getSectionByCode(tempSecFrom.getWhCode(), channelFrom);
            line.setSecFrom(secFrom.getSecCode());
        } else {
            line.setSecFrom(secCode);
        }
        InvSection tempSecTo = stockModel.getSectionByCode(tempSecFrom.getWhCode(), channelTo);
        line.setSecTo(tempSecTo.getSecCode());
        line.setChannelTo(channelTo);
        line.setChannelFrom(channelFrom);

        //1.设置产品信息
        ItemBase base = stockModel.getItemBaseBySku(sku);
        if (base == null) {
            json.setMessage("找不到物料编码[" + sku + "]对应的产品");
            return json;
        }
        line.setItemCode(sku);
        line.setItemId(base.getId());
        line.setItemName(base.getMaterialDescription());

        if (line.getSecFrom().equals(tempSecTo.getSecCode())) {
            json.setMessage("不能对同一库位下物料做操作");
            return json;
        }

        if (!tempSecTo.isChannelWA()) {
            json.setMessage("调入库位必须是共享库");
            return json;
        }
        if (!tempSecFrom.isChannelWA() && !channelFrom.equals(tempSecFrom.getChannelCode())) {
            json.setMessage("调出库位和调出渠道不匹配");
            json.setData(false);
            return json;
        }
        if (tempSecFrom.isChannelRRS()) {
            json.setMessage("不支持的调出渠道");
            return json;
        }
        //1.保存调拨记录，并立即提交调拨
        ServiceResult<String> rs = transferLineService.saveInnerTransfer(line, true);

        json.setData(rs.getSuccess());
        json.setMessage(rs.getSuccess() ? "解锁成功" : "解锁失败");
        return json;
    }
}
