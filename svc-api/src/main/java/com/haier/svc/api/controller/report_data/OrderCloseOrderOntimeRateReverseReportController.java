package com.haier.svc.api.controller.report_data;

import com.haier.common.PagerInfo;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.OrderWorkflowRegion;
import com.haier.shop.model.PersonTradeCfg;
import com.haier.shop.service.OrderWorkflowRegionService;
import com.haier.shop.service.ShopOrderWorkflowsRunTimeService;
import com.haier.shop.service.ShopOrderWorkflowsService;
import com.haier.shop.service.ShopPersonTradeCfgService;
import com.haier.stock.service.StockInvChannel2OrderSourceService;
import com.haier.svc.api.controller.util.ExchangeUtil;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.controller.util.WebUtil;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.PaperSize;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "orderClose")
public class OrderCloseOrderOntimeRateReverseReportController {

    /**
     * 列表查询每次最大条数
     */
    private int                            topNum           = 20000;
    //缓存过期时间-单位：分钟
    private int                            cacheTimeOut     = 10;
    @Autowired
    private ShopOrderWorkflowsRunTimeService shopOrderWorkflowsRunTimeService;
    @Autowired
    private OrderWorkflowRegionService orderWorkflowRegionService;
    @Autowired
    private ShopPersonTradeCfgService shopPersonTradeCfgService;
    @Autowired
    private StockInvChannel2OrderSourceService stockInvChannel2OrderSourceService;
    private Map<String, Identity>          countResultCache = new HashMap<String, Identity>();
    private Map<String, Identity>          repairIdCache    = new HashMap<String, Identity>();
    //天猫渠道code-订单来源代码
    //天猫渠道（淘宝海尔官方旗舰店、海尔生活家电旗舰店、淘宝海尔官方旗舰店分销平台、淘宝海尔生活家电旗舰店分销平台、淘宝海尔买帮专卖店、淘宝海尔热水器专卖店、淘宝海尔生活电器专卖店、统帅日日顺乐家专卖店、海尔新宝旗舰店、淘宝海尔新宝旗舰店分销平台）
    private String[]                       sources          = new String[] { "TBSC", "TOPDHSC",
            "TOPFENXIAO",
            "TOPFENXIAODHSC",
            "TOPBUYBANG",
            "TOPBOILER", "TOPSHJD",
            "TONGSHUAI", "TOPXB",
            "TOPFENXIAOXB" };
    @Autowired
    private ShopOrderWorkflowsService orderWorkFlowModel;
    ThreadLocal<SimpleDateFormat> formatAll        = new ThreadLocal<SimpleDateFormat>();
    ThreadLocal<DecimalFormat>    decimalFormatAll = new ThreadLocal<DecimalFormat>();

    private static org.apache.log4j.Logger log    = org.apache.log4j.LogManager
            .getLogger(BlpOrderOntimeRateReverseReportController.class);

    @GetMapping("orderCloseOrderOntimeRateReverseReport")
    public String mytest(HttpServletRequest request, HttpServletResponse response,
                         Map<String, Object> modelMap){

        String userName = getUserName();

        List<Map<String, String>> loginList = shopPersonTradeCfgService.getLoginPersonInfo(userName);

        if (loginList == null || loginList.size() == 0) {
            modelMap.put("userName", "");
            modelMap.put("userTrade", "");
            modelMap.put("userType", "0");
        } else {
            StringBuffer sb = new StringBuffer();

            Map<String, String> tradeExistMap = new HashMap<String, String>();

            for (Map<String, String> map : loginList) {

                if ("0".equals(map.get("type"))) {
                    if (tradeExistMap.containsKey(map.get("trade"))) {
                        continue;
                    }
                    modelMap.put("userName", map.get("commissioner"));
                    modelMap.put("userType", "0");
                    sb.append(map.get("trade")).append(",");
                    tradeExistMap.put(map.get("trade"), map.get("trade"));
                } else if ("1".equals(map.get("type"))) {
                    if (tradeExistMap.containsKey(map.get("trade"))) {
                        continue;
                    }
                    modelMap.put("userType", "1");
                    if (map.get("commissioner").contains("@")) {
                        String[] arr = map.get("commissioner").split("@");
                        for (String str : arr) {
                            if (str.equals(userName)) {
                                if (tradeExistMap.containsKey(map.get("trade"))) {
                                    continue;
                                }
                                modelMap.put("userName", userName);
                                sb.append(map.get("trade")).append(",");
                                tradeExistMap.put(map.get("trade"), map.get("trade"));
                            }
                        }
                    } else if (userName.equals(map.get("commissioner"))) {
                        modelMap.put("userName", userName);
                        sb.append(map.get("trade")).append(",");
                        tradeExistMap.put(map.get("trade"), map.get("trade"));
                    }
                }
            }
            modelMap.put("userTrade", sb.deleteCharAt(sb.toString().length() - 1).toString());
        }

        String channel = orderWorkflowRegionService.getLoginPersonChannel(userName);
        channel = channel == null ? "" : channel.trim();
        modelMap.put("channel", channel);

        return "report_data/orderCloseOrderOntimeRateReverseReport";
    }


    @RequestMapping(value = { "/getOrderOntimeRateReverseReportList" }, method = { RequestMethod.GET })
    String getOrderOntimeRateReverseList(HttpServletRequest request, HttpServletResponse response,
                                         Map<String, Object> modelMap,
                                         @RequestParam(required = true) String startDate,
                                         @RequestParam(required = true) String endDate,
                                         @RequestParam(required = false) String sourceCode,
                                         @RequestParam(required = false) String cateCode,
                                         @RequestParam(required = false) String queryGroup,
                                         @RequestParam(required = false) String province,
                                         @RequestParam(required = false) String city,
                                         @RequestParam(required = false) String region,
                                         @RequestParam(required = false) String area,
                                         @RequestParam(required = false) String areaCommissioner,
                                         @RequestParam(required = false) String areaTrade,
                                         @RequestParam(required = false) String areaSCode,
                                         @RequestParam(required = false) String fdArea,
                                         @RequestParam(required = false) String fdAreaTrade,
                                         @RequestParam(required = false) String fdAreaSCode,
                                         @RequestParam(required = false) String orderSn,
                                         @RequestParam(required = false) String nodeType,
                                         @RequestParam(required = false) String channel,
                                         @RequestParam(required = false) String stockType,
                                         @RequestParam(required = false) String tradeComissorType) {
        startDate = startDate.replace("-","");
        endDate = endDate.replace("-","");
        try {
            //增加网单号筛选
            boolean orderSnFlag = false;
            if (orderSn != null && !"".equals(orderSn.trim())) {
                orderSnFlag = true;
            }

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("startDate", startDate);
            paramMap.put("endDate", endDate);
            //增加网单号筛选
            paramMap.put("orderSn", orderSn == null ? "" : orderSn.trim().split(","));
            if (sourceCode != null && !sourceCode.trim().equals("")
                    && sourceCode.split(";").length > 0) {
                paramMap.put("sourceCode", Arrays.asList(sourceCode.split(";")));//订单来源多选
            }
            if (cateCode != null && !cateCode.trim().equals("") && cateCode.split(";").length > 0) {
                paramMap.put("cateCode", Arrays.asList(cateCode.split(";")));//品类多选
            }

            if ("PCR".equals(queryGroup)) {
                paramMap.put("province", province);
                paramMap.put("city", city);
                paramMap.put("region", region);
            }
            paramMap.put("nodeType", nodeType);
            paramMap.put("channel", channel);
            paramMap.put("stockType", stockType);
            //limit参数
            paramMap.put("topNum", topNum);
            CountEntity totalCountEntity = new CountEntity();//每次查询结果合计
            Map<String, CountEntity> countMap = new HashMap<String, CountEntity>();
            List<OrderWorkflowRegion> regionList = new ArrayList<OrderWorkflowRegion>();
            List<PersonTradeCfg> ptc = new ArrayList<PersonTradeCfg>();
            List<Map<String, String>> storageList = new ArrayList<Map<String, String>>();
            List<Map<String, String>> ordWfRegionList = new ArrayList<Map<String, String>>();
            Map<String, String> sCodeTradeMap = new HashMap<String, String>();
            Map<String, String> sCodeWlMap = new HashMap<String, String>();
            Map<String, Object> distanceMap = new HashMap<String, Object>();
            this.getDistanceInfo(distanceMap);

            //增加网单号筛选
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            if (orderSnFlag) {
                list = orderWorkFlowModel.getOntimeRateReverseListByOrderSn(paramMap);
                addDistance(list, distanceMap);
                if (list != null && list.size() > 0) {
                    ordWfRegionList = orderWorkflowRegionService.getSCodeTradeWl();
                }
                //计算及时率
                countBatch(list, storageList, countMap, paramMap, orderSnFlag, regionList,
                        startDate, endDate, sourceCode, queryGroup, province, city, region, area,
                        areaCommissioner, areaTrade, areaSCode, fdArea, fdAreaTrade, fdAreaSCode,
                        orderSn, nodeType, totalCountEntity, ptc, tradeComissorType, areaCommissioner,
                        channel, ordWfRegionList, sCodeTradeMap, sCodeWlMap);
            } else {
                //获取网单库传入sql查询
                if ("A".equals(queryGroup)) {
                    String[] arrayScode = null;
                    arrayScode = getScodesForLittleQuery(areaCommissioner, areaTrade, areaSCode,
                            storageList);
                    paramMap.put("sCodes", arrayScode);
                }
                if ("FDA".equals(queryGroup)) {
                    String[] arrayScode = null;
                    String strTemp = getScodesForLesLittleQuery(fdArea, fdAreaTrade, fdAreaSCode,
                            storageList);
                    if (!StringUtil.isEmpty(strTemp)) {
                        arrayScode = strTemp.split(",");
                    }
                    paramMap.put("sCodes", arrayScode);
                }
                list = orderWorkFlowModel.getOntimeRateReverseListNew(paramMap, true);
                if (list != null && list.size() > 0) {
                    ordWfRegionList = orderWorkflowRegionService.getSCodeTradeWl();
                }
                addDistance(list, distanceMap);
                long orsId = 0;
                while (null != list && list.size() > 0) {
                    //计算及时率
                    countBatch(list, storageList, countMap, paramMap, orderSnFlag, regionList,
                            startDate, endDate, sourceCode, queryGroup, province, city, region, area,
                            areaCommissioner, areaTrade, areaSCode, fdArea, fdAreaTrade, fdAreaSCode,
                            orderSn, nodeType, totalCountEntity, ptc, tradeComissorType,
                            areaCommissioner, channel, ordWfRegionList, sCodeTradeMap, sCodeWlMap);
                    if (list.size() < topNum) {
                        break;
                    }
                    orsId = typeJudge(list.get(list.size() - 1).get("id"));

                    paramMap.put("orsId", orsId);
                    list = orderWorkFlowModel.getOntimeRateReverseListNew(paramMap, false);
                    addDistance(list, distanceMap);
                }
            }

            modelMap.put("totalCountEntity", totalCountEntity);
            modelMap.put("countMap", countMap);

            this.countRate(totalCountEntity);
            if (null != countMap && !countMap.isEmpty()) {
                for (Map.Entry<String, CountEntity> entry : countMap.entrySet()) {
                    this.countRate(entry.getValue());
                }
            }

            //超过5分钟失效
            Object[] keySet = countResultCache.keySet().toArray();

            for (Object key : keySet) {
                if (System.currentTimeMillis()
                        - countResultCache.get(key).getCurTime() > cacheTimeOut * 60 * 1000)
                    countResultCache.remove(key);
            }
            //将每次明细查询结果的map缓存起来
            Identity identity = new Identity();
            //由于返回页面的带有网单号,缓存不需要,所以需要把modelMap拷贝一份放到缓存里,节省内存
            identity.setCountResultMap(copyModelMap(totalCountEntity, countMap));
            identity.setCurTime(System.currentTimeMillis());
            countResultCache.put(WebUtil.currentUserId(request) + "-" + nodeType, identity);
        } catch (Exception e) {
            log.error("[order][orderworkflow]获取逆向及时率网单列表时发生未知错误", e);
        }
        return "report_data/orderCloseOrderOntimeRateReverseReportList";
    }

    @RequestMapping(value = { "/getOrderOntimeRateReverseReportDetailList" }, method = { RequestMethod.POST })
    String getOrderOntimeRateReverseReportDetailList(HttpServletRequest request,
                                                     HttpServletResponse response,
                                                     Map<String, Object> modelMap,
                                                     @RequestParam(required = true) String repairId,
                                                     @RequestParam(required = false) String cOrderSn,
                                                     @RequestParam(required = true) String nodeType,
                                                     @RequestParam(required = false) Integer pageIndex,
                                                     @RequestParam(required = false) Integer pageSize,
                                                     @RequestParam(required = false) String sign) {

        try {
            //超过5分钟失效
            Object[] keySet = repairIdCache.keySet().toArray();
            for (Object key : keySet) {
                if (System.currentTimeMillis() - repairIdCache.get(key).getCurTime() > cacheTimeOut
                        * 60 * 1000)
                    repairIdCache.remove(key);
            }
            //将每次明细查询结果的网单号缓存起来
            Identity identity = new Identity();
            identity.setRepairId(repairId);
            identity.setCurTime(System.currentTimeMillis());
            repairIdCache.put(WebUtil.currentUserId(request) + "-" + nodeType, identity);
            PagerInfo pager = null;
            if (pageIndex != null && pageIndex > 0 && pageSize != null && pageSize > 0)
                pager = new PagerInfo(pageSize, pageIndex);
            List<Map<String, Object>> list = this.getOntimeRateReverseDetailList(repairId, cOrderSn, nodeType, pager);
            Map<String, Object> distanceMap = new HashMap<String, Object>();
            this.getDistanceInfo(distanceMap);
            addDistance(list, distanceMap);
            this.addOutTime(list, nodeType);
            addPagerParam(modelMap, pager);
            modelMap.put("repairId", repairId);
            modelMap.put("sign",sign);
            modelMap.put("orderOntimeRateReportDetailList", list);
        } catch (Exception e) {
            log.error("[order][orderworkflow]获取及时率网单明细列表时发生未知错误", e);
        }
        return "report_data/orderCloseOrderOntimeRateReverseReportDetailList";
    }


    @RequestMapping(value = { "/exportOrderOntimeRateReverseReportDetail.html" }, method = { RequestMethod.GET })
    void exportOrderOntimeRateReverseReportDetail(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  @RequestParam(required = true) String nodeType) throws Exception {
        OutputStream os = response.getOutputStream();
        try {
            response.reset();
            response.setHeader("Content-Disposition",
                    "attachment; filename=OrderWorkFlow-OrderOntimeReverseDetail("
                            + dateToStr(new Date()) + ").xls");
            response.setContentType("application/octet-stream; charset=utf-8");
            WritableWorkbook book = Workbook.createWorkbook(os);// 创建可以写的book文件对象
            WritableSheet sheet = book.createSheet("订单全流程-及时率报表", 0);// 在book3.xls中创建一个sheet,名称为'qy',从第一列开始插入
            sheet.getSettings().setPaperSize(PaperSize.A4);//设置纸张大小
            sheet.getSettings().setFitHeight(297);
            sheet.getSettings().setFitWidth(21);
            sheet.getSettings().setHorizontalCentre(true);
            if (repairIdCache.get(WebUtil.currentUserId(request) + "-" + nodeType) == null)
                throw new Exception("查询结果已失效，请关闭该页面后重新尝试");

            List<Map<String, Object>> list = this
                    .getOntimeRateReverseDetailList(repairIdCache
                                    .get(WebUtil.currentUserId(request) + "-" + nodeType).getRepairId(), null,
                            nodeType, null);
            Map<String, Object> distanceMap = new HashMap<String, Object>();
            this.getDistanceInfo(distanceMap);
            addDistance(list, distanceMap);
            this.addOutTime(list, nodeType);
            setExcelDetail(sheet, list, nodeType);
            // 写入到服务器
            book.write();
            // 一定要关闭，否则不写入
            book.close();
        } catch (Exception e) {
            response.reset();
            response.setContentType("text/html;charset=UTF-8");
            if ("查询结果已失效，请关闭该页面后重新尝试".equals(e.getMessage()))
                response.getWriter().print("查询结果已失效，请关闭该页面后重新尝试");
            else {
                response.getWriter().print("导出excel失败，请联系系统管理员");
                log.error("及时率逆向报表导出明细excel失败", e);
            }
            response.getWriter().flush();
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    private void setExcelDetail(WritableSheet sheet, List<Map<String, Object>> list,
                                String nodeType) throws Exception {
        WritableFont headerFont = new WritableFont(WritableFont.ARIAL, 15, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);
        WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
        headerFormat.setAlignment(Alignment.CENTRE);
        WritableFont font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat format1 = new WritableCellFormat(font, NumberFormats.INTEGER);
        format1.setAlignment(Alignment.CENTRE);
        format1.setVerticalAlignment(VerticalAlignment.CENTRE);
        format1.setBorder(Border.ALL, BorderLineStyle.THIN);
        WritableCellFormat format2 = new WritableCellFormat(font, NumberFormats.INTEGER);
        format2.setAlignment(Alignment.LEFT);
        format2.setBorder(Border.ALL, BorderLineStyle.THIN);
        int temp = 0;
        int columnWidth = 26;
        sheet.setColumnView(0, 20);
        sheet.addCell(new Label(0, 0, "订单全流程-逆向及时率报表明细", headerFormat));
        if ("22storehouse".equals(nodeType) || "recheckquality".equals(nodeType)
                || "transmitbox".equals(nodeType) || "transmitstock".equals(nodeType)) {
            sheet.mergeCells(0, 0, 42, 0);
        } else {
            sheet.mergeCells(0, 0, 38, 0);
        }
        temp++;
        String[] headers = null;
        if ("22storehouse".equals(nodeType) || "recheckquality".equals(nodeType)
                || "transmitbox".equals(nodeType) || "transmitstock".equals(nodeType)) {
            headers = new String[] { "WD单号", "订单号", "区域", "中心", "工贸", "转运库位", "网单状态", "商品分类",
                    "顶级品类", "型号", "数量", "金额", "网单金额", "退款申请金额", "是否金额差异", "收货人",
                    "退换货处理状态", "退换货类型", "退款状态", "货物状态", "发票状态", "检验结果", "质检结果",
                    "申请退款时间", "已退款时间", "审核完成时间", "终止完成时间", "推送HP时间", "HP回传时间",
                    "LES开提单时间", "LES入库时间", "发票作废时间", "入22库回传时间", "HP鉴定回传时间",
                    "HP换箱回传时间", "存性变更入10或41库开提单时间", "存性变更入10或41库回传时间", "取消关闭时间",
                    "超时天数", //"应完成时间",
                    "订单来源", "网点", "网单类型", "TB单号" };
        } else {
            headers = new String[] { "WD单号", "订单号", "区域", "中心", "工贸", "转运库位", "网单状态", "商品分类",
                    "顶级品类", "型号", "数量", "金额", "网单金额", "退款申请金额", "是否金额差异", "收货人",
                    "退换货处理状态", "退换货类型", "退款状态", "货物状态", "发票状态", "检验结果", "质检结果",
                    "申请退款时间", "已退款时间", "审核完成时间", "终止完成时间", "推送HP时间", "HP回传时间",
                    "LES开提单时间", "LES入库时间", "发票作废时间", "入日日顺21库时间", "取消关闭时间", "超时天数", //"应完成时间",
                    "订单来源", "网点", "网单类型", "TB单号" };
        }

        for (int col = 0; col < headers.length; col++) {
            sheet.setColumnView(col, columnWidth);
            sheet.addCell(new Label(col, temp, headers[col], headerFormat));
        }
        temp++;
        String[] columnNames = null;
        if ("22storehouse".equals(nodeType) || "recheckquality".equals(nodeType)
                || "transmitbox".equals(nodeType) || "transmitstock".equals(nodeType)) {
            columnNames = new String[] { "cOrderSn", "orderSn", "area", "sCode", "trade", "tsCode",
                    "status", "cateId", "superCateId", "productName", "number",
                    "price", "opMoney", "refMoney", "isDiffMoney", "consignee",
                    "handleStatus", "typeActual", "paymentStatus",
                    "storageStatus", "state", "checkResult", "quality",
                    "applyTime", "paymentTime", "handleTime", "endTime",
                    "hpFirstAddTime", "hpTime", "lesTime", "lesOutPingTime",
                    "invalidTime", "in22StoreTime", "jdTime", "hxTime",
                    "lesOrderSnTime13", "in1041StoreTime", "finishTime",
                    "outTime", //"mustDate",
                    "channelName", "netPointName", "stockType",
                    "tb_order_sn" };
        } else {
            columnNames = new String[] { "cOrderSn", "orderSn", "area", "sCode", "trade", "tsCode",
                    "status", "cateId", "superCateId", "productName", "number",
                    "price", "opMoney", "refMoney", "isDiffMoney", "consignee",
                    "handleStatus", "typeActual", "paymentStatus",
                    "storageStatus", "state", "checkResult", "quality",
                    "applyTime", "paymentTime", "handleTime", "endTime",
                    "hpFirstAddTime", "hpTime", "lesTime", "lesOutPingTime",
                    "invalidTime", "storageTime", "finishTime", "outTime", //"mustDate",
                    "channelName", "netPointName", "stockType",
                    "tb_order_sn" };
        }

        for (Map<String, Object> map : list) {
            int colNum = 0;
            for (String columnName : columnNames) {
                sheet.setColumnView(colNum, columnWidth);
                String thisValue = "";
                if (map.containsKey(columnName) && map.get(columnName) != null) {
                    thisValue = map.get(columnName).toString();
                }
                sheet.addCell(new Label(colNum, temp, thisValue, format1));
                colNum++;
            }
            temp++;
        }
    }

    /**
     * 获取及时率报表逆向明细数据列表
     */
    public List<Map<String, Object>> getOntimeRateReverseDetailList(String repairId,
                                                                    String cOrderSn,
                                                                    String nodeType,
                                                                    PagerInfo pager) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("repairIds", repairId.split(","));
        //增加网单查询
        paramMap.put("cOrderSn", "%" + (cOrderSn == null ? "" : cOrderSn) + "%");
        paramMap.put("nodeType", nodeType);
        if (pager != null) {
            paramMap.put("start", pager.getStart());
            paramMap.put("size", pager.getPageSize());
            pager.setRowsCount(orderWorkFlowModel.getOntimeRateReverseDetailCountNew(paramMap));
        } else {
            paramMap.put("size", 0);
        }
        List<Map<String, Object>> list = orderWorkFlowModel
                .getOntimeRateReverseDetailNew(paramMap);
        if (list == null || list.isEmpty()) {
            return list;
        }
        List<Map<String, String>> storageList = new ArrayList<Map<String, String>>();
        List<OrderWorkflowRegion> tradeList = new ArrayList<OrderWorkflowRegion>();
        Map<String, String> basStoreMap = null;

        List<Map<String, Object>> channels = stockInvChannel2OrderSourceService.getChannelNames(); // 渠道名称 add by zhaozj on 2013-12-25
        Map<String, Map<String, Object>> productCateMap = getProductCate();

        List<Map<String, String>> ordWfRegionList = orderWorkflowRegionService.getSCodeTradeWl();
        Map<String, String> sCodeTradeMap = new HashMap<String, String>();

        for (Map<String, Object> resultMap : list) {
            resultMap.put("status_Int", resultMap.get("status"));
            resultMap.put("applyTime_Long", resultMap.get("applyTime"));
            resultMap.put("finishTime_Long", resultMap.get("finishTime"));
            resultMap.put("cancelTime_Long", resultMap.get("cancelTime"));

            resultMap.put("handleStatus_int", resultMap.get("handleStatus"));
            resultMap.put("handleTime_Long", resultMap.get("handleTime"));
            resultMap.put("paymentTime_Long", resultMap.get("paymentTime"));//------新加
            resultMap.put("endTime_Long", resultMap.get("endTime"));

            //            Long finishTime = (Integer) resultMap.get("status") == 110 ? (Long) resultMap
            //                .get("cancelTime") : (Long) resultMap.get("finishTime");
            Long finishTime = (Long) resultMap.get("cancelTime");
            //翻译地区
            resultMap.put("area", getAreaTrade((Long) resultMap.get("region"), tradeList, "1"));
            String sCode = StringUtil.isEmpty((String) resultMap.get("tsCode"))
                    ? (String) resultMap.get("sCode") : (String) resultMap.get("tsCode");
            if ("3W".equals(resultMap.get("stockType").toString())) {
                //翻译工贸
                if (BlpOrderOntimeRateReverseReportController.SCODE_VALUE_MAP
                        .containsKey(sCode)) {
                    sCode = BlpOrderOntimeRateReverseReportController.SCODE_VALUE_MAP
                            .get(sCode);
                }
                String t = getTradeByScode(sCode, sCodeTradeMap,
                        ordWfRegionList);
                if (StringUtil.isEmpty(t)) {
                    resultMap.put("trade",
                            getAreaTrade((Long) resultMap.get("region"), tradeList, "2"));
                } else {
                    resultMap.put("trade", t);
                }
            } else {
                if ("WA".equalsIgnoreCase(resultMap.get("stockType").toString())
                        && "B2C".equalsIgnoreCase(resultMap.get("shippingMode").toString())) {
                    //翻译工贸
                    resultMap.put("trade",
                            getAreaTrade((Long) resultMap.get("region"), tradeList, "2"));
                } else {
                    String t = getTradeByScode(sCode, sCodeTradeMap,
                            ordWfRegionList);
                    if (StringUtil.isEmpty(t)) {
                        resultMap.put("trade",
                                getAreaTrade((Long) resultMap.get("region"), tradeList, "2"));
                    } else {
                        resultMap.put("trade", t);
                    }
                }
                //翻译库位
                resultMap.put("sCode",
                        getStorageName((String) resultMap.get("sCode"), storageList));
                //翻译转运库位
                resultMap.put("tsCode",
                        getStorageName((String) resultMap.get("tsCode"), storageList));
            }

            //翻译退换货处理状态
            resultMap.put("handleStatus", formatHandleStatus((Long) resultMap.get("handleStatus")));
            //翻译退换货类型
            resultMap.put("typeActual", formatTypeActual((Integer) resultMap.get("typeActual")));
            //翻译网单状态
            resultMap.put("status", formatStatus((Integer) resultMap.get("status")));
            //翻译退款状态
            resultMap.put("paymentStatus",
                    formatPaymentStatus((Integer) resultMap.get("paymentStatus")));
            //翻译货物状态
            resultMap.put("storageStatus",
                    formatStorageStatus((Integer) resultMap.get("storageStatus")));
            //翻译发票状态
            resultMap.put("state", formatInvoiceStatus((Integer) resultMap.get("state")));
            //翻译检验结果
            resultMap.put("checkResult", formatCheckResult((Integer) resultMap.get("checkResult")));
            //翻译质检结果
            resultMap.put("oldQuality", resultMap.get("quality"));
            resultMap.put("quality", formatQuality((Integer) resultMap.get("quality")));

            //格式化时间
            resultMap.put("applyTime", formatTime(resultMap.get("applyTime")));
            resultMap.put("handleTime", formatTime(resultMap.get("handleTime")));
            resultMap.put("paymentTime", formatTime(resultMap.get("paymentTime")));//------新加
            resultMap.put("endTime", formatTime(resultMap.get("endTime")));
            resultMap.put("hpFirstAddTime", formatTime(resultMap.get("hpFirstAddTime")));
            resultMap.put("calHpTime", resultMap.get("hpTime"));
            resultMap.put("hpTime", formatTime(resultMap.get("hpTime")));
            resultMap.put("calStorageTime", resultMap.get("storageTime"));
            if ("blp".equalsIgnoreCase(nodeType)) {
                resultMap.put("storageTime", formatTime(resultMap.get("storageTime")));
            } else {
                resultMap.put("storageTime", "0");
            }
            resultMap.put("lesTime", formatTime(resultMap.get("lesTime")));
            resultMap.put("lesOutPingTime", formatTime(resultMap.get("lesOutPingTime")));
            resultMap.put("invalidTime", formatTime(resultMap.get("invalidTime")));
            resultMap.put("finishTime", formatTime(finishTime));
            // 转换渠道名字 add by zhaozj on 2013-12-25 begin
            String source = (String) resultMap.get("source");
            Object channelName = ExchangeUtil.getSourceName(source, channels);
            resultMap.put("channelName", channelName);
            // 转换渠道名字 add by zhaozj on 2013-12-25 end
            resultMap.put("source", resultMap.get("source"));//------新加

            //是否金额差异
//            resultMap.put("opMoney", new BigDecimal(resultMap.get("productAmount").toString())
//                    .subtract(new BigDecimal(resultMap.get("balanceAmount").toString())));

            resultMap.put("refMoney", new BigDecimal(resultMap.get("refundAmount").toString()));
//            resultMap.put("isDiffMoney",
//                    new BigDecimal(resultMap.get("opMoney").toString())
//                            .compareTo(new BigDecimal(resultMap.get("refMoney").toString())) == 0 ? "否"
//                            : "是");

            if ("22storehouse".equals(nodeType) || "recheckquality".equals(nodeType)
                    || "transmitbox".equals(nodeType) || "transmitstock".equals(nodeType)) {
                //22库
                resultMap.put("in22StoreTime", formatTime(resultMap.get("in22StoreTime")));
                resultMap.put("in1041StoreTime", formatTime(resultMap.get("in1041StoreTime")));
                resultMap.put("lesOrderSnTime13", formatTime(resultMap.get("lesOrderSnTime13")));
                resultMap.put("jdTime", formatTime(resultMap.get("jdTime")));
                resultMap.put("hxTime", formatTime(resultMap.get("hxTime")));
            }
            //店铺
            if ("refund".equals(nodeType)
                    && "STORE".equalsIgnoreCase((String) resultMap.get("stockType"))) {
                resultMap.put("sCode",
                        getStoreName(resultMap.get("storeId").toString(), basStoreMap));
                resultMap.put("netPointName", resultMap.get("sCode"));
            }
            //顶级品类
            resultMap.put("superCateId",
                    getSuperCateId(resultMap.get("cateId").toString(), productCateMap));
            //品类
            resultMap.put("cateId",
                    productCateMap.get(String.valueOf(resultMap.get("cateId"))) == null ? ""
                            : productCateMap.get(String.valueOf(resultMap.get("cateId"))).get("cateName"));

        }
        return list;
    }

    /**
     * 获取顶级品类
     */
    public String getSuperCateId(String id, Map<String, Map<String, Object>> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        Map<String, Object> parentMap = map.get(id);
        if (parentMap == null) {
            return "";
        }
        if (Integer.parseInt(parentMap.get("parentId").toString()) == 0) {
            return parentMap.get("cateName").toString();
        } else {
            return getSuperCateId(parentMap.get("parentId").toString(), map);
        }
    }

    /**
     * 获取店铺名称
     */
    public String getStoreName(String id, Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        return StringUtil.isEmpty(map.get(id)) ? "" : map.get(id);
    }

    /**
     * 获取商品类型
     */
    public Map<String, Map<String, Object>> getProductCate() {
        Map<String, Map<String, Object>> productMap = new HashMap<String, Map<String, Object>>();
        List<Map<String, Object>> list = shopOrderWorkflowsRunTimeService.getProductCate();
        if (list == null || list.isEmpty()) {
            return productMap;
        }
        for (Map<String, Object> map : list) {
            productMap.put(String.valueOf(map.get("id")), map);
        }
        return productMap;
    }

    private String formatQuality(Integer value) {
        if (value == null || value == 0 || value == 4)
            return "";
        else if (value == 1)
            return "未开箱正品";
        else if (value == 2)
            return "已开箱正品";
        else if (value == 3)
            return "不良品";
        else if (value == 5)
            return "不良品换机";
        else if (value == 6)
            return "不良品退机";
        return value.toString();
    }

    private String formatCheckResult(Integer value) {
        if (value == null)
            return "";
        else if (value == 0)
            return "咨询结单";
        else if (value == 1)
            return "符合";
        else if (value == 2)
            return "不符合";
        return value.toString();
    }

    /**
     * 翻译发票状态
     */
    private String formatInvoiceStatus(Integer value) {
        if (value == null)
            return "";
        else if (value == 1)
            return "已开票";
        else if (value == 2)
            return "未开票";
        else if (value == 3)
            return "已召回";
        else if (value == 4)
            return "已冲票";
        else if (value == 10)
            return "待召回";
        return value.toString();
    }

    /**
     * 翻译货物状态
     */
    private String formatStorageStatus(Integer value) {
        if (value == 1)
            return "已出库";
        else if (value == 2)
            return "未出库";
        else if (value == 3)
            return "已召回";
        else if (value == 4)
            return "已入库";
        else if (value == 10)
            return "待召回";
        else if (value == 122)
            return "已入22库";
        else if (value == 121)
            return "已入21库";
        else if (value == 110)
            return "已入10库";
        else if (value == 221)
            return "已入日日顺21库";
        return value.toString();
    }

    /**
     * 翻译退款状态
     */
    private String formatPaymentStatus(Integer value) {
        if (value == 1)
            return "已付款";
        else if (value == 2)
            return "待退款";
        else if (value == 3)
            return "已退款";
        else if (value == 4)
            return "线下已退款";
        else if (value == 5)
            return "无需退款";
        return value.toString();
    }

    /**
     * 翻译网单状态
     */
    private String formatStatus(Integer value) {
        if (value == 0)
            return "初始状态";
        else if (value == 2)
            return "同步到HP";
        else if (value == 4)
            return "已分配网点";
        else if (value == 8)
            return "LES 开提单, 待出库";
        else if (value == 10)
            return "待审核";
        else if (value == 11)
            return "待转运入库";
        else if (value == 12)
            return "待转运出库";
        else if (value == 40)
            return "待发货";
        else if (value == 70)
            return "待交付";
        else if (value == 110)
            return "取消关闭";
        else if (value == 130)
            return "完成关闭 ";
        else if (value == 140)
            return "用户签收";
        else if (value == 150)
            return "网点拒单";
        else if (value == 160)
            return "用户拒收";
        else if (value == 170)
            return "用户取消";
        else if (value == 180)
            return "无法执行";
        return value.toString();
    }

    /**
     * 格式化时间
     */
    private String formatTime(Object time) {
        Long tempTime = 0L;
        if (time instanceof Integer)
            tempTime = ((Integer) time).longValue();
        else if (time instanceof Long)
            tempTime = ((Long) time);
        if (tempTime == 0)
            return tempTime.toString();
        Date date = new Date(tempTime * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }
    /**
     * 翻译退换货类型
     */
    private String formatTypeActual(Integer value) {
        if (value == null)
            return "";
        if (value == 1)
            return "退货退款";
        else if (value == 2)
            return "退货不退款";
        return value.toString();
    }

    private String formatHandleStatus(Long value) {
        if (value == null)
            return "";
        else if (value == 1)
            return "审核中";
        else if (value == 2)
            return "进行中";
        else if (value == 3)
            return "受理完成";
        else if (value == 4)
            return "已完结";
        else if (value == 5)
            return "已驳回";
        else if (value == 6)
            return "已终止";
        else if (value == 7)
            return "线下已退款";
        return value.toString();
    }

    /**
     * 翻译区域和工贸
     */
    private String getAreaTrade(Long region, List<OrderWorkflowRegion> tradeList, String flag) {
        if (tradeList.size() == 0) {
            List<OrderWorkflowRegion> tempTradeList = orderWorkFlowModel.getOwfRegion();
            Iterator<OrderWorkflowRegion> it = tempTradeList.iterator();
            while (it.hasNext()) {
                tradeList.add(it.next());
            }
        }
        for (OrderWorkflowRegion owfRegion : tradeList) {
            if (region.equals(owfRegion.getRegionId().longValue()))
                if ("1".equals(flag)) {
                    return owfRegion.getQyName();
                } else if ("2".equals(flag)) {
                    return owfRegion.getGmName();
                }
        }
        return "";
    }

    private void addPagerParam(Map<String, Object> modelMap, PagerInfo pager) {
        Integer totalPage = pager.getRowsCount() % pager.getPageSize() == 0
                ? pager.getRowsCount() / pager.getPageSize()
                : pager.getRowsCount() / pager.getPageSize() + 1;
        modelMap.put("totalCount", pager.getRowsCount());
        if (pager.getPageIndex() > 1)
            modelMap.put("hasFirst", true);
        else
            modelMap.put("hasFirst", false);
        if (pager.getPageIndex() - 1 > 0)
            modelMap.put("hasPrevious", true);
        else
            modelMap.put("hasPrevious", false);
        if (pager.getPageIndex() + 1 <= totalPage)
            modelMap.put("hasNext", true);
        else
            modelMap.put("hasNext", false);
        if (totalPage > 1 && pager.getPageIndex() != totalPage)
            modelMap.put("hasLast", true);
        else
            modelMap.put("hasLast", false);
        modelMap.put("curPage", totalPage > 0 ? pager.getPageIndex() : 0);
        modelMap.put("totalPage", totalPage);
        modelMap.put("startNum", pager.getStart() + 1);
        modelMap.put("endNum",
                (pager.getStart() + pager.getPageSize()) > pager.getRowsCount()
                        ? (pager.getStart() + pager.getRowsCount() % pager.getPageSize())
                        : pager.getStart() + pager.getPageSize());
    }
    /**
     *
     * 增加超期天数，应完成时间
     * @author chunlong.song@rogrand.com
     * @since 2013-12-18
     * **/
    private void addOutTime(List<Map<String, Object>> list, String nodeType) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Map<String, Object> map : list) {
            if (nodeType != null) {
                Calendar mustDate = null;
                Calendar realDate = null;
                /********************************************************/
                if ("audit".equals(nodeType)) {//退换货审核
                    Long applyTime = "0".equals(map.get("applyTime").toString()) ? 0
                            : format.parse(map.get("applyTime").toString()).getTime() / 1000;
                    Long auditTime = "0".equals(map.get("handleTime").toString()) ? 0
                            : format.parse(map.get("handleTime").toString()).getTime() / 1000;

                    Calendar auditRealCalendar = Calendar.getInstance();
                    Calendar auditMustCalendar = Calendar.getInstance();
                    auditRealCalendar
                            .setTime(auditTime == 0 ? new Date() : new Date(auditTime * 1000L));
                    auditMustCalendar.setTime(new Date(applyTime * 1000L));
                    //退换货审核在申请后24H内完成
                    auditMustCalendar.add(Calendar.DATE, 1);
                    //0:未超期；1：已完成超期；2：未完成超期；3未完成未超期

                    mustDate = auditMustCalendar;
                    realDate = auditRealCalendar;

                } else if ("hp".equals(nodeType)) {//HP质检

                    Long hpFirstAddTime = "0".equals(map.get("hpFirstAddTime").toString()) ? 0
                            : format.parse(map.get("hpFirstAddTime").toString()).getTime() / 1000;
                    Long hpTime = "0".equals(map.get("hpTime").toString()) ? 0
                            : format.parse(map.get("hpTime").toString()).getTime() / 1000;

                    Calendar hpRealCalendar = Calendar.getInstance();
                    Calendar hpMustCalendar = Calendar.getInstance();
                    hpRealCalendar.setTime(
                            (hpTime == null || hpTime == 0) ? new Date() : new Date(hpTime * 1000L));
                    hpMustCalendar.setTime(new Date(hpFirstAddTime * 1000L));
                    //HP一次质检在向hp推送成功后48H内完成
                    hpMustCalendar.add(Calendar.DATE, 2);
                    mustDate = hpMustCalendar;
                    realDate = hpRealCalendar;

                } else if ("les".equals(nodeType)) {
                    Long lesTime = "0".equals(map.get("lesTime").toString()) ? 0
                            : format.parse(map.get("lesTime").toString()).getTime() / 1000;
                    Long lesOutPingTime = "0".equals(map.get("lesOutPingTime").toString()) ? 0
                            : format.parse(map.get("lesOutPingTime").toString()).getTime() / 1000;
                    Calendar lesRealCalendar = Calendar.getInstance();
                    Calendar lesMustCalendar = Calendar.getInstance();
                    lesRealCalendar.setTime(lesOutPingTime == null || lesOutPingTime == 0
                            ? new Date() : new Date(lesOutPingTime * 1000L));
                    lesMustCalendar.setTime(new Date(lesTime == null ? 0 : lesTime * 1000L));
                    //LES入库在LES开单后7天内完成
                    lesMustCalendar.add(Calendar.DATE, 7);

                    mustDate = lesMustCalendar;
                    realDate = lesRealCalendar;

                } else if ("invoice".equals(nodeType)) {//开发票
                    Long lesOutPingTime = "0".equals(map.get("lesOutPingTime").toString()) ? 0
                            : format.parse(map.get("lesOutPingTime").toString()).getTime() / 1000;
                    Long invalidTime = "0".equals(map.get("invalidTime").toString()) ? 0
                            : format.parse(map.get("invalidTime").toString()).getTime() / 1000;

                    Calendar invoiceRealCalendar = Calendar.getInstance();
                    Calendar invoiceMustCalendar = Calendar.getInstance();
                    invoiceRealCalendar.setTime(invalidTime == null || invalidTime == 0 ? new Date()
                            : new Date(invalidTime * 1000L));
                    invoiceMustCalendar.setTime(new Date(lesOutPingTime * 1000L));
                    //开发票需在les入库后24H内完成
                    invoiceMustCalendar.add(Calendar.DATE, 1);

                    mustDate = invoiceMustCalendar;
                    realDate = invoiceRealCalendar;
                } else if ("orderclose".equals(nodeType)) {//订单关闭

                    //                    Integer status = (Integer) map.get("status_Int");
                    //退换货订单关闭及时率，开始时间为推送HP时间
                    //                    Long applyTime = (Long) map.get("applyTime_Long");
                    Long applyTime = "0".equals(map.get("hpFirstAddTime").toString()) ? 0
                            : format.parse(map.get("hpFirstAddTime").toString()).getTime() / 1000;
                    //                    Long finishTime = (Long) map.get("finishTime_Long");
                    Long cancelTime = (Long) map.get("cancelTime_Long");
                    //                    Long closeTime = status == 110 ? cancelTime : finishTime;
                    Long closeTime = cancelTime;
                    //************优化增加开始**********************
                    Long handleStatus = typeJudge(map.get("handleStatus_int"));
                    Long rejectEndTime = 0l;
                    if (handleStatus == 5 && typeJudge(map.get("handleTime_Long")) > 0) {
                        rejectEndTime = typeJudge(map.get("handleTime_Long"));
                    } else if ((handleStatus == 3 || handleStatus == 6)
                            && typeJudge(map.get("endTime_Long")) > 0) {
                        rejectEndTime = typeJudge(map.get("endTime_Long"));
                    }

                    if ((handleStatus == 5 && rejectEndTime > 0)
                            || ((handleStatus == 3 || handleStatus == 6) && rejectEndTime > 0)) {
                        closeTime = rejectEndTime;
                    }
                    //************优化增加结束**********************
                    Calendar ordercloseRealCalendar = Calendar.getInstance();
                    Calendar ordercloseMustCalendar = Calendar.getInstance();
                    ordercloseRealCalendar
                            .setTime(closeTime == 0 ? new Date() : new Date(closeTime * 1000L));
                    ordercloseMustCalendar.setTime(new Date(applyTime * 1000L));
                    //开发票需在les入库后24H内完成
                    ordercloseMustCalendar.add(Calendar.DATE, 9);

                    mustDate = ordercloseMustCalendar;
                    //线下已退款直接入实 //不良品直接入实 //超500公里直接入实
                    if (handleStatus == 7 || typeJudge(map.get("oldQuality")) == 3
                            || Double.parseDouble(map.get("shippingDistance").toString()) > 500) {
                        realDate = ordercloseMustCalendar;
                    } else {
                        realDate = ordercloseRealCalendar;
                    }
                } else if ("refund".equals(nodeType)) {//订单退款---------
                    String handleStatus = ((Long) map.get("handleStatus_int")) == null ? null
                            : ((Long) map.get("handleStatus_int")).toString();//handleStatus=5 已驳回
                    String handleTime = (handleStatus != null && handleStatus.equals("5"))
                            ? (String) map.get("handleTime") : "0";//审核驳回时间    默认0
                    String endTime = (String) map.get("endTime");//终止时间 ors.finishTime as endTime
                    String paymentTime = map.get("paymentTime") == null ? "0"
                            : (String) map.get("paymentTime");//付款时间    默认null
                    String source = (String) map.get("source");//订单来源-渠道
                    String realTime = paymentTime;//实际处理时间，默认已退款时间，实际取【已退款时间】、【审核驳回时间】、【终止时间】最早时间
                    /**
                     * 应：应完成时间=【申请退款时间】+3天（剔除【退换货类型】为“退货不退款”的情况）；
                     * 实：【已退款时间】、【审核驳回时间】、【终止时间】任意字段有值最早时间且小于应完成时间为实，否则为差；
                     * ①非天猫渠道的直接控制中心取数，②天猫渠道的目前未对接数据获取不到，如果【审核驳回时间】、【终止时间】全为空，先按实计算
                     */
                    if (paymentTime == null || paymentTime.trim().equals("")
                            || paymentTime.trim().equals("0") || paymentTime.length() < 18) {
                        realTime = endTime;
                    } else {
                        if (endTime == null || endTime.trim().equals("")
                                || endTime.trim().equals("0") || endTime.length() < 18) {
                            realTime = paymentTime;
                        } else {
                            if (format.parse(paymentTime).getTime()
                                    - format.parse(endTime).getTime() < 0) {
                                realTime = paymentTime;
                            } else {
                                realTime = endTime;
                            }
                        }
                    }
                    if (realTime == null || realTime.trim().equals("")
                            || realTime.trim().equals("0") || realTime.length() < 18) {
                        realTime = handleTime;
                    } else {
                        if (!(handleTime == null || handleTime.trim().equals("")
                                || handleTime.trim().equals("0") || handleTime.length() < 18)) {
                            if (format.parse(handleTime).getTime()
                                    - format.parse(realTime).getTime() < 0) {
                                realTime = handleTime;
                            }
                        }
                    }

                    Long applyTime = "0".equals((String) map.get("applyTime")) ? 0 : format.parse( //addTime as applyTime
                            (String) map.get("applyTime")).getTime() / 1000;
                    Long refundTime = "0".equals(realTime) ? 0
                            : format.parse(realTime).getTime() / 1000;

                    Calendar auditRealCalendar = Calendar.getInstance();
                    Calendar auditMustCalendar = Calendar.getInstance();

                    if (java.util.Arrays.toString(sources).contains(source)) {//判断是否天猫渠道
                        auditRealCalendar.setTime(refundTime == 0 ? new Date(applyTime * 1000L)
                                : new Date(refundTime * 1000L));//如果是天猫渠道来源订单，则设置实际完成时间为申请时间，需求规定这部分已完成，即应、实、差、里的“实”，
                    } else {
                        auditRealCalendar
                                .setTime(refundTime == 0 ? new Date() : new Date(refundTime * 1000L));
                    }

                    auditMustCalendar.setTime(new Date(applyTime * 1000L));
                    //退换货审核在申请后3天内完成
                    auditMustCalendar.add(Calendar.DATE, 3);
                    //0:未超期；1：已完成超期；2：未完成超期；3未完成未超期

                    mustDate = auditMustCalendar;
                    realDate = auditRealCalendar;
                } else if ("22storehouse".equals(nodeType)) {//22库
                    Long lesOutPingTimeStart = "0".equals(map.get("in22StoreTime").toString()) ? 0
                            : format.parse(map.get("in22StoreTime").toString()).getTime() / 1000;
                    Long lesOutPingTimeEnd = "0".equals(map.get("in1041StoreTime").toString()) ? 0
                            : format.parse(map.get("in1041StoreTime").toString()).getTime() / 1000;

                    Calendar storeHouseRealCalendar = Calendar.getInstance();
                    Calendar storeHouseMustCalendar = Calendar.getInstance();
                    storeHouseRealCalendar.setTime(
                            lesOutPingTimeEnd == 0 ? new Date() : new Date(lesOutPingTimeEnd * 1000L));
                    storeHouseMustCalendar.setTime(new Date(lesOutPingTimeStart * 1000L));
                    //入22库回传时间，21天内，入10或者41库
                    storeHouseMustCalendar.add(Calendar.DATE, 22);

                    mustDate = storeHouseMustCalendar;
                    realDate = storeHouseRealCalendar;
                } else if ("recheckquality".equals(nodeType)) {//二次质检
                    Long lesOutPingTime = "0".equals(map.get("in22StoreTime").toString()) ? 0
                            : format.parse(map.get("in22StoreTime").toString()).getTime() / 1000;
                    Long hpTime = "0".equals(map.get("jdTime").toString()) ? 0
                            : format.parse(map.get("jdTime").toString()).getTime() / 1000;

                    if (hpTime == 0l) {
                        Map<String, Object> storeMap = shopOrderWorkflowsRunTimeService
                                .get1041StoreExistInfo(Integer.parseInt(map.get("id").toString()));
                        if (storeMap != null && storeMap.size() > 0) {
                            hpTime = typeJudge(storeMap.get("lesOrderSnTime")) > 0
                                    ? typeJudge(storeMap.get("lesOrderSnTime"))
                                    : typeJudge(storeMap.get("addTime"));
                        }
                    }

                    Calendar reCheckQualityRealCalendar = Calendar.getInstance();
                    Calendar reCheckQualityMustCalendar = Calendar.getInstance();
                    reCheckQualityRealCalendar
                            .setTime(hpTime == 0 ? new Date() : new Date(hpTime * 1000L));
                    reCheckQualityMustCalendar.setTime(new Date(lesOutPingTime * 1000L));
                    //入22库回传时间，5天内，hp回传鉴定时间(checkType=2)
                    reCheckQualityMustCalendar.add(Calendar.DATE, 5);

                    mustDate = reCheckQualityMustCalendar;
                    realDate = reCheckQualityRealCalendar;
                } else if ("transmitbox".equals(nodeType)) {//转箱
                    Long hpTimeStart = "0".equals(map.get("jdTime").toString()) ? 0
                            : format.parse(map.get("jdTime").toString()).getTime() / 1000;
                    Long hpTimeEnd = "0".equals(map.get("hxTime").toString()) ? 0
                            : format.parse(map.get("hxTime").toString()).getTime() / 1000;

                    if (hpTimeEnd == 0l) {
                        Map<String, Object> storeMap = shopOrderWorkflowsRunTimeService
                                .get1041StoreExistInfo(Integer.parseInt(map.get("id").toString()));
                        if (storeMap != null && storeMap.size() > 0) {
                            hpTimeEnd = typeJudge(storeMap.get("lesOrderSnTime")) > 0
                                    ? typeJudge(storeMap.get("lesOrderSnTime"))
                                    : typeJudge(storeMap.get("addTime"));
                        }
                    }

                    Calendar transmitBoxRealCalendar = Calendar.getInstance();
                    Calendar transmitBoxMustCalendar = Calendar.getInstance();
                    transmitBoxRealCalendar
                            .setTime(hpTimeEnd == 0 ? new Date() : new Date(hpTimeEnd * 1000L));
                    transmitBoxMustCalendar.setTime(new Date(hpTimeStart * 1000L));
                    //hp回传鉴定时间(checkType=2)，15天内，hp回传换箱时间(checkType=3)
                    transmitBoxMustCalendar.add(Calendar.DATE, 15);

                    mustDate = transmitBoxMustCalendar;
                    realDate = transmitBoxRealCalendar;
                } else if ("transmitstock".equals(nodeType)) {//转库
                    Long lesOrderSnTime = "0".equals(map.get("lesOrderSnTime13").toString()) ? 0
                            : format.parse(map.get("lesOrderSnTime13").toString()).getTime() / 1000;
                    Long lesOutPingTime = "0".equals(map.get("in1041StoreTime").toString()) ? 0
                            : format.parse(map.get("in1041StoreTime").toString()).getTime() / 1000;

                    Calendar transmitStockRealCalendar = Calendar.getInstance();
                    Calendar transmitStockMustCalendar = Calendar.getInstance();
                    transmitStockRealCalendar.setTime(
                            lesOutPingTime == 0 ? new Date() : new Date(lesOutPingTime * 1000L));
                    transmitStockMustCalendar.setTime(new Date(lesOrderSnTime * 1000L));
                    //存性变更入10或者41库开单时间，1天内，转库完成
                    transmitStockMustCalendar.add(Calendar.DATE, 2);

                    mustDate = transmitStockMustCalendar;
                    realDate = transmitStockRealCalendar;
                } else if ("blp".equals(nodeType)) {//不良品
                    //开始时间：退换货列表退换货订单鉴定符合不良品
                    Long hpTime = typeJudge(map.get("calHpTime"));
                    //结束时间：入日日顺21库
                    Long storageTime = typeJudge(map.get("calStorageTime"));

                    Calendar blpRealCalendar = Calendar.getInstance();
                    Calendar blpMustCalendar = Calendar.getInstance();
                    blpRealCalendar.setTime(storageTime == null || storageTime == 0 ? new Date()
                            : new Date(storageTime * 1000L));
                    blpMustCalendar.setTime(new Date(hpTime == null ? 0 : hpTime * 1000L));
                    //退换货列表退换货订单鉴定符合不良品48小时入日日顺21库
                    blpMustCalendar.add(Calendar.DATE, 15);

                    mustDate = blpMustCalendar;
                    realDate = blpRealCalendar;
                }

                /*******************************************************/
                if (mustDate.after(realDate)) {
                    map.put("outTime", 0);
                } else {
                    map.put("outTime", (realDate.getTime().getTime() - mustDate.getTime().getTime())
                            / (24 * 3600 * 1000));
                }
                map.put("mustDate", format.format(mustDate.getTime()));
                map.put("realDate", format.format(realDate.getTime()));
            }
        }
    }

    private Map<String, Object> copyModelMap(CountEntity totalCountEntity,
                                             Map<String, CountEntity> countMap) {
        Map<String, Object> cacheMap = new HashMap<String, Object>();
        //拷贝清除stringBuffer,导出列表时候不用网单号
        cacheMap.put("totalCountEntity", totalCountEntity.clone());
        Map<String, CountEntity> cacheCountmap = new HashMap<String, CountEntity>();
        if (!countMap.isEmpty()) {
            for (Map.Entry<String, CountEntity> entry : countMap.entrySet()) {
                //拷贝清除stringBuffer,导出列表时候不用网单号
                cacheCountmap.put(entry.getKey(), entry.getValue().clone());
            }
        }
        cacheMap.put("countMap", cacheCountmap);
        return cacheMap;
    }
    private void countRate(CountEntity countEntity) {
        if (null == decimalFormatAll.get()) {
            decimalFormatAll.set(new DecimalFormat("#0.00"));
        }
        if (countEntity.total == 0) {
            countEntity.ontimeRate = "100%";
        } else {
            countEntity.ontimeRate = decimalFormatAll.get()
                    .format(countEntity.onTime.floatValue() / countEntity.total * 100) + '%';
        }
        if (countEntity.total == 0) {
            countEntity.finishRate = "100%";
        } else {
            countEntity.finishRate = decimalFormatAll.get()
                    .format((countEntity.onTime.floatValue() + countEntity.cOutTime.floatValue())
                            / countEntity.total * 100) + '%';
        }
        if (countEntity.realAudit == 0) {
            countEntity.avgRate = "0H";
        } else {
            countEntity.avgRate = decimalFormatAll.get().format(
                    countEntity.auditTotalTime.floatValue() / countEntity.realAudit / 60 / 60) + "H";
        }
    }


    @RequestMapping(value = { "/getCateInfo.html" }, method = { RequestMethod.POST })
    void getCateInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer = null;
        try {
            String json = "";
            List<Map<String, Object>> tempList = orderWorkflowRegionService.getCateInfo();
            if (tempList != null && tempList.size() > 0) {
                Map<Integer, Map<String, Object>> queryMap = new HashMap<Integer, Map<String, Object>>();
                for (Map<String, Object> map : tempList) {
                    queryMap.put(Integer.parseInt(map.get("id").toString()), map);
                }
                Map<Integer, ProductCates> productCateMap = new HashMap<Integer, ProductCates>();
                Set<Integer> set = new HashSet<Integer>();
                for (Map<String, Object> tempMap : tempList) {
                    if (Integer.parseInt(tempMap.get("parentId").toString()) == 0) {
                        if (!productCateMap
                                .containsKey(Integer.parseInt(tempMap.get("id").toString()))) {
                            ProductCates p = new ProductCates();
                            p.setId(Integer.parseInt(tempMap.get("id").toString()));
                            p.setText(tempMap.get("cateName").toString());
                            p.setIconCls("icon-blank");
                            p.setState("closed");
                            productCateMap.put(Integer.parseInt(tempMap.get("id").toString()), p);
                            set.add(Integer.parseInt(tempMap.get("id").toString()));
                        }
                    } else {
                        if (!productCateMap
                                .containsKey(Integer.parseInt(tempMap.get("id").toString()))) {
                            ProductCates child = new ProductCates();
                            child.setId(Integer.parseInt(tempMap.get("id").toString()));
                            child.setText(tempMap.get("cateName").toString());
                            child.setIconCls("icon-blank");
                            productCateMap.put(Integer.parseInt(tempMap.get("id").toString()),
                                    child);
                            Map<String, Object> parentMap = queryMap
                                    .get(Integer.parseInt(tempMap.get("parentId").toString()));
                            if (!productCateMap
                                    .containsKey(Integer.parseInt(parentMap.get("id").toString()))) {
                                ProductCates parent = new ProductCates();
                                parent.setId(Integer.parseInt(parentMap.get("id").toString()));
                                parent.setText(parentMap.get("cateName").toString());
                                parent.setIconCls("icon-blank");
                                parent.setState("closed");
                                parent.addChildren(child);
                                productCateMap.put(Integer.parseInt(parentMap.get("id").toString()),
                                        parent);
                            } else {
                                ProductCates parent = productCateMap
                                        .get(Integer.parseInt(parentMap.get("id").toString()));
                                parent.setState("closed");
                                parent.addChildren(child);
                            }
                        }
                    }
                }
                for (Iterator<Integer> it = productCateMap.keySet().iterator(); it.hasNext();) {
                    Integer key = it.next();
                    if (!set.contains(key)) {
                        it.remove();
                        continue;
                    }
                    if (productCateMap.get(key).getChildren() == null) {
                        productCateMap.get(key).setState(null);
                    }
                }
                json = JsonUtil.toJson(Arrays.asList(productCateMap.values()));
                if (!StringUtil.isEmpty(json)) {
                    json = json.substring(1, json.length() - 1);
                }
            }
            writer = response.getWriter();
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            log.error("报表获取品类树状图时异常：", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 获取区域配送距离数据
     */
    public void getDistanceInfo(Map<String, Object> distanceMap) {
        try {
            if (distanceMap.isEmpty()) {
                List<Map<String, Object>> tempAreaList = shopOrderWorkflowsRunTimeService.getDistances();
                if (tempAreaList == null || tempAreaList.isEmpty()) {
                    log.error("Regions表为空！");
                    return;
                }
                for (Map<String, Object> map : tempAreaList) {
                    distanceMap.put(map.get("id").toString(), map.get("shippingDistance"));
                }
            }
        } catch (Exception e) {
            log.error("[OrderWorkFlowRunTimeModel_getDistanceInfo]获取区域配送距离数据时发生未知异常:", e);
        }
    }

    /**
     * 获取网单库传入sql查询
     * @param les
     * @param trade
     * @param sCode
     * @return
     */
    private String getScodesForLesLittleQuery(String les, String trade, String sCode,
                                              List<Map<String, String>> storageList) {
        //物流选择为"请选择"或者"全部"时,即为全部订单
        if (StringUtil.isEmpty(les) || "-1".equals(les)) {
            return null;
        }

        StringBuffer sb = new StringBuffer();

        if (!StringUtil.isEmpty(trade) && !"-1".equals(trade)) {
            if (StringUtil.isEmpty(sCode) || "-1".equals(sCode)) {
                //根据工贸获取网单库
                List<Map<String, String>> sCodeList = orderWorkflowRegionService.getSCode(trade);
                if (sCodeList == null || sCodeList.size() == 0) {
                    return null;
                }
                //将网单库名称转化为编码
                for (Map<String, String> sCodeMap : sCodeList) {
                    sb.append(
                            getStorageCode(sCodeMap.get("value"), storageList))
                            .append(",");
                }
            } else {
                sb.append(getStorageCode(sCode, storageList)).append(",");
            }
            if (StringUtil.isEmpty(sb.toString())) {
                return null;
            }
            for (String tempStr : sb.toString().split(",")) {
                if (SCODE_MAP.containsKey(tempStr)) {
                    sb.append(SCODE_MAP.get(tempStr)).append(",");
                }
            }
            return sb.deleteCharAt(sb.toString().length() - 1).toString();
        } else {
            //根据工贸获取网单库
            List<Map<String, String>> sCodeList = orderWorkflowRegionService.getSCodeByLes(les);
            if (sCodeList == null || sCodeList.size() == 0) {
                return null;
            }
            //将网单库名称转化为编码
            for (Map<String, String> sCodeMap : sCodeList) {
                sb.append(
                        getStorageCode(sCodeMap.get("value"), storageList))
                        .append(",");
            }
            if (StringUtil.isEmpty(sb.toString())) {
                return null;
            }
            for (String tempStr : sb.toString().split(",")) {
                if (SCODE_MAP.containsKey(tempStr)) {
                    sb.append(SCODE_MAP.get(tempStr)).append(",");
                }
            }
            return sb.deleteCharAt(sb.toString().length() - 1).toString();
        }
    }


    /**
     * 获取网单库传入sql查询
     * @param person
     * @param trade
     * @param sCode
     * @return
     */
    private String[] getScodesForLittleQuery(String person, String trade, String sCode,
                                             List<Map<String, String>> storageList) {
        //人员选择为"请选择"或者"全部"时,即为全部订单
        if ("".equals(person) || "-1".equals(person)) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        Map<String, String> tradeExistMap = new HashMap<String, String>();

        if (!"".equals(person) && !"".equals(trade) && !"-1".equals(trade)) {
            if ("".equals(sCode) || "-1".equals(sCode)) {
                //根据工贸获取网单库
                List<Map<String, String>> sCodeList = orderWorkflowRegionService.getSCode(trade);
                //将网单库名称转化为编码
                for (Map<String, String> sCodeMap : sCodeList) {
                    sb.append(getStorageCode(sCodeMap.get("value"),
                            storageList)).append(",");
                }
            } else {
                sb.append(getStorageCode(sCode, storageList)).append(",");
            }
            for (String tempStr : sb.toString().split(",")) {
                if (SCODE_MAP.containsKey(tempStr)) {
                    sb.append(SCODE_MAP.get(tempStr)).append(",");
                }
            }
            return sb.deleteCharAt(sb.toString().length() - 1).toString().split(",");

        }

        List<Map<String, String>> loginList = shopPersonTradeCfgService.getLoginPersonInfo(person);

        if (loginList == null || loginList.size() == 0) {
            return null;
        } else {
            for (Map<String, String> map : loginList) {
                if (tradeExistMap.containsKey(map.get("trade"))) {
                    continue;
                }
                if (map.get("commissioner").contains("@")) {
                    String[] arr = map.get("commissioner").split("@");
                    for (String str : arr) {
                        if (str.equals(person)) {
                            tradeExistMap.put(map.get("trade"), map.get("trade"));
                            break;
                        }
                    }
                } else if (person.equals(map.get("commissioner"))) {
                    tradeExistMap.put(map.get("trade"), map.get("trade"));
                }
            }
        }

        for (String key : tradeExistMap.keySet()) {
            //根据工贸获取网单库
            List<Map<String, String>> sCodeList = orderWorkflowRegionService.getSCode(key);
            //将网单库名称转化为编码
            for (Map<String, String> sCodeMap : sCodeList) {
                sb.append(
                        getStorageCode(sCodeMap.get("value"), storageList))
                        .append(",");
            }
        }
        for (String tempStr : sb.toString().split(",")) {
            if (SCODE_MAP.containsKey(tempStr)) {
                sb.append(SCODE_MAP.get(tempStr)).append(",");
            }
        }
        return sb.deleteCharAt(sb.toString().length() - 1).toString().split(",");
    }

    private void countBatch(List<Map<String, Object>> list, List<Map<String, String>> storageList,
                            Map<String, CountEntity> countMap, Map<String, Object> paramMap,
                            boolean orderSnFlag, List<OrderWorkflowRegion> regionList,
                            String startDate, String endDate, String sourceCode, String queryGroup,
                            String province, String city, String region, String area,
                            String areaCommissioner, String areaTrade, String areaSCode,
                            String fdArea, String fdAreaTrade, String fdAreaSCode, String orderSn,
                            String nodeType, CountEntity totalCountEntity, List<PersonTradeCfg> ptc,
                            String userType, String userName, String channel,
                            List<Map<String, String>> ordWfRegionList,
                            Map<String, String> sCodeTradeMap,
                            Map<String, String> sCodeWlMap) throws Exception {
        for (Map<String, Object> map : list) {
            //增加网单号筛选
            if (orderSnFlag) {
                countOntime(paramMap, map, totalCountEntity);
                continue;
            }

            Long orderRegion = (Long) map.get("region");
            String orderSecCode = (String) map.get("sCode");
            //按不同维度展示
            if ("PCR".equals(queryGroup)) {
                countOntime(paramMap, map, totalCountEntity);
                String key = null;
                if (!"".equals(province) && "".equals(city)) {
                    key = getKey(orderRegion, "province", regionList);
                } else if (!"".equals(city) && "".equals(region)) {
                    key = getKey(orderRegion, "city", regionList);
                } else if (!"".equals(region)) {
                    key = getKey(orderRegion, "region", regionList);
                } else {
                    continue;
                }
                CountEntity itemCountEntiry = countMap.get(key) == null ? new CountEntity()
                        : countMap.get(key);//每一项统计
                countOntime(paramMap, map, itemCountEntiry);
                countMap.put(key, itemCountEntiry);
            } else if ("A".equals(queryGroup)) {
                String key = null;
                //                if (!"".equals(area) && "".equals(areaCommissioner) && "".equals(areaTrade)
                //                    && "".equals(areaSCode)) {
                //                    key = "area";
                //                } else
                if (!"".equals(areaCommissioner) && "".equals(areaTrade) && "".equals(areaSCode)) {
                    key = "areaCommissioner";
                } else if (!"".equals(areaTrade) && "".equals(areaSCode)) {
                    key = "areaTrade";
                } else if (!"".equals(areaSCode)) {
                    key = "areaSCode";
                } else {
                    countOntime(paramMap, map, totalCountEntity);
                    continue;
                }
                String sCode = StringUtil.isEmpty((String) map.get("tsCode"))
                        ? (String) map.get("sCode") : (String) map.get("tsCode");
                if (SCODE_VALUE_MAP.containsKey(sCode)) {
                    sCode = SCODE_VALUE_MAP.get(sCode);
                }

                if (!"".equals(areaCommissioner) && !"-1".equals(areaCommissioner)) {
                    if ("WA".equalsIgnoreCase(map.get("stockType").toString())
                            && "B2C".equalsIgnoreCase(map.get("shippingMode").toString())) {
                        if (!areaCommissioner.equals(
                                getKeyCommissioner(orderRegion, regionList, ptc, userName, channel))) {
                            continue;
                        }
                    } else {
                        if (!areaCommissioner
                                .equals(getCommissionerByScode(sCode,
                                        sCodeTradeMap, ptc, ordWfRegionList, areaCommissioner, channel))) {
                            continue;
                        }
                    }
                }

                if (!"".equals(areaTrade) && !"-1".equals(areaTrade)) {
                    if ("WA".equalsIgnoreCase(map.get("stockType").toString())
                            && "B2C".equalsIgnoreCase(map.get("shippingMode").toString())) {
                        if (!areaTrade.equals(getKey(orderRegion, "areaTrade", regionList))) {
                            continue;
                        }
                    } else {
                        if (!areaTrade.equals(getTradeByScode(sCode,
                                sCodeTradeMap, ordWfRegionList))) {
                            continue;
                        }
                    }
                }
                if (!"".equals(areaSCode) && !"-1".equals(areaSCode)) {
                    if ("WA".equalsIgnoreCase(map.get("stockType").toString())
                            && "B2C".equalsIgnoreCase(map.get("shippingMode").toString())) {
                        if (!areaSCode.equals(getKeyStorages(orderSecCode, storageList))) {
                            continue;
                        }
                    } else {
                        if (!areaSCode
                                .equals(getStorageName(sCode, storageList))) {
                            continue;
                        }
                    }
                }

                countOntime(paramMap, map, totalCountEntity);

                if (key.equals("areaCommissioner")) {
                    //                    itemCountEntiry = countMap.get(
                    //                        getKeyCommissioner(orderRegion, regionList, ptc, userName, channel)) == null
                    //                            ? new CountEntity()
                    //                            : countMap.get(getKeyCommissioner(orderRegion, regionList, ptc,
                    //                                userName, channel));//每一项统计
                    String mapKey = null;
                    if ("WA".equalsIgnoreCase(map.get("stockType").toString())
                            && "B2C".equalsIgnoreCase(map.get("shippingMode").toString())) {
                        mapKey = getKeyCommissioner(orderRegion, regionList, ptc, userName,
                                channel);
                    } else {
                        mapKey = getCommissionerByScode(sCode,
                                sCodeTradeMap, ptc, ordWfRegionList, areaCommissioner, channel);
                    }
                    CountEntity itemCountEntiry = countMap.get(mapKey) == null ? new CountEntity()
                            : countMap.get(mapKey);
                    countOntime(paramMap, map, itemCountEntiry);
                    countMap.put(mapKey, itemCountEntiry);
                } else if (key.equals("areaSCode")) {
                    //                    itemCountEntiry = countMap
                    //                        .get(getKeyStorages(orderSecCode, storageList)) == null ? new CountEntity()
                    //                            : countMap.get(getKeyStorages(orderSecCode, storageList));//每一项统计
                    String mapKey = null;
                    if ("WA".equalsIgnoreCase(map.get("stockType").toString())
                            && "B2C".equalsIgnoreCase(map.get("shippingMode").toString())) {
                        mapKey = getKeyStorages(orderSecCode, storageList);
                    } else {
                        mapKey = getStorageName(sCode, storageList);
                    }
                    CountEntity itemCountEntiry = countMap.get(mapKey) == null ? new CountEntity()
                            : countMap.get(mapKey);
                    countOntime(paramMap, map, itemCountEntiry);
                    countMap.put(mapKey, itemCountEntiry);
                } else {
                    //                    itemCountEntiry = countMap.get(getKey(orderRegion, key, regionList)) == null
                    //                        ? new CountEntity() : countMap.get(getKey(orderRegion, key, regionList));//每一项统计
                    String mapKey = null;
                    if ("WA".equalsIgnoreCase(map.get("stockType").toString())
                            && "B2C".equalsIgnoreCase(map.get("shippingMode").toString())) {
                        mapKey = getKey(orderRegion, key, regionList);
                    } else {
                        mapKey = getTradeByScode(sCode, sCodeTradeMap,
                                ordWfRegionList);
                    }
                    CountEntity itemCountEntiry = countMap.get(mapKey) == null ? new CountEntity()
                            : countMap.get(mapKey);
                    countOntime(paramMap, map, itemCountEntiry);
                    countMap.put(mapKey, itemCountEntiry);
                }
            } else {
                String key = null;
                if (!"".equals(fdArea) && "".equals(fdAreaTrade)) {
                    key = "fdArea";
                } else if (!"".equals(fdAreaTrade) && "".equals(fdAreaSCode)) {
                    key = "fdAreaTrade";
                } else if (!"".equals(fdAreaSCode)) {
                    key = "fdAreaSCode";
                } else {
                    countOntime(paramMap, map, totalCountEntity);
                    continue;
                }
                //应用中过滤筛选项
                String sCode = StringUtil.isEmpty((String) map.get("tsCode"))
                        ? (String) map.get("sCode") : (String) map.get("tsCode");
                if (SCODE_VALUE_MAP.containsKey(sCode)) {
                    sCode = SCODE_VALUE_MAP.get(sCode);
                }
                if (!"".equals(fdArea) && !"-1".equals(fdArea)
                        && !fdArea.equals(
                        getWlByScode(sCode, sCodeWlMap, ordWfRegionList)))
                    continue;
                if (!"".equals(fdAreaTrade) && !"-1".equals(fdAreaTrade)
                        && !fdAreaTrade.equals(
                        getTradeByScode(sCode, sCodeTradeMap,
                                ordWfRegionList)))
                    continue;
                if (!"".equals(fdAreaSCode) && !"-1".equals(fdAreaSCode)
                        && !fdAreaSCode.equals(
                        getStorageName(sCode, storageList)))
                    continue;
                countOntime(paramMap, map, totalCountEntity);
                if (key.equals("fdAreaSCode")) {

                    String mapKey = getStorageName(sCode, storageList);
                    CountEntity itemCountEntiry = countMap.get(mapKey) == null ? new CountEntity()
                            : countMap.get(mapKey);
                    countOntime(paramMap, map, itemCountEntiry);
                    countMap.put(mapKey, itemCountEntiry);
                } else if (key.equals("fdAreaTrade")) {
                    String mapKey = getTradeByScode(sCode, sCodeTradeMap,
                            ordWfRegionList);
                    CountEntity itemCountEntiry = countMap.get(mapKey) == null ? new CountEntity()
                            : countMap.get(mapKey);
                    countOntime(paramMap, map, itemCountEntiry);
                    countMap.put(mapKey, itemCountEntiry);
                } else {

                    String mapKey = getWlByScode(sCode, sCodeWlMap,
                            ordWfRegionList);
                    CountEntity itemCountEntiry = countMap.get(mapKey) == null ? new CountEntity()
                            : countMap.get(mapKey);
                    countOntime(paramMap, map, itemCountEntiry);
                    countMap.put(mapKey, itemCountEntiry);
                }

            }
        }
    }

    @RequestMapping(value = { "/getTradeAllSmallChannelPeople" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<Map<String, String>>> getTradeAllSmallChannelPeople() {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
        Assert.notNull(shopPersonTradeCfgService, "Property 'orderSelectListModel' is required.");
        try {
            result.setData(shopPersonTradeCfgService.getTradeAllSmallChannelPeople());
        } catch (Exception e) {
            log.error("[order][getTradeAllSmallChannelPeople]获取小渠道人员时发生未知错误", e);
            result.setMessage("获取小渠道人员时发生未知错误");
        }
        return result;
    }


    @RequestMapping(value = { "/exportOrderOntimeRateReverseReport.html" }, method = { RequestMethod.GET })
    void exportOrderOntimeRateReverseReport(HttpServletRequest request,
                                            HttpServletResponse response,
                                            @RequestParam(required = true) String nodeType) throws Exception {
        OutputStream os = response.getOutputStream();
        try {
            response.reset();
            response.setHeader("Content-Disposition",
                    "attachment; filename=OrderWorkFlow-OrderOntimeReverse(" + dateToStr(new Date())
                            + ").xls");
            response.setContentType("application/octet-stream; charset=utf-8");
            WritableWorkbook book = Workbook.createWorkbook(os);// 创建可以写的book文件对象
            WritableSheet sheet = book.createSheet("订单全流程-及时率报表", 0);// 在book3.xls中创建一个sheet,名称为'qy',从第一列开始插入
            sheet.getSettings().setPaperSize(PaperSize.A4);//设置纸张大小
            sheet.getSettings().setFitHeight(297);
            sheet.getSettings().setFitWidth(21);
            sheet.getSettings().setHorizontalCentre(true);
            int temp = 0;
            setExcelTitle(sheet);
            temp++;
            if (countResultCache.get(WebUtil.currentUserId(request) + "-" + nodeType) == null)
                throw new Exception("没有查询或者查询结果已失效，请关闭该页面后重新尝试");
            setExcelCount(sheet, temp, nodeType, countResultCache
                    .get(WebUtil.currentUserId(request) + "-" + nodeType).getCountResultMap());
            // 写入到服务器
            book.write();
            // 一定要关闭，否则不写入
            book.close();
        } catch (Exception e) {
            response.reset();
            response.setContentType("text/html;charset=UTF-8");
            if ("没有查询或者查询结果已失效，请关闭该页面后重新尝试".equals(e.getMessage()))
                response.getWriter().print("没有查询或者查询结果已失效，请关闭该页面后重新尝试");
            else {
                response.getWriter().print("导出excel失败，请联系系统管理员");
                log.error("及时率逆向报表导出统计excel失败", e);
            }
            response.getWriter().flush();
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }


    private void setExcelCount(WritableSheet sheet, int temp, String nodeType,
                               Map<String, Object> modelMap) throws Exception {
        WritableFont font = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat format1 = new WritableCellFormat(font, NumberFormats.INTEGER);
        format1.setAlignment(Alignment.CENTRE);
        format1.setVerticalAlignment(VerticalAlignment.CENTRE);
        format1.setBorder(Border.ALL, BorderLineStyle.THIN);
        WritableCellFormat format2 = new WritableCellFormat(font, NumberFormats.INTEGER);
        format2.setAlignment(Alignment.LEFT);
        format2.setBorder(Border.ALL, BorderLineStyle.THIN);
        int columnWidth = 12, firstColumnWidth = 20;
        CountEntity totalCountEntity = (CountEntity) modelMap.get("totalCountEntity");
        Map<String, CountEntity> countMap = (Map<String, CountEntity>) modelMap.get("countMap");
        String ontimeTitle = null;
        if ("audit".equals(nodeType)) {
            ontimeTitle = "退换货审核及时率";
        } else if ("hp".equals(nodeType)) {
            ontimeTitle = "退换货HP质检及时率";
        } else if ("les".equals(nodeType)) {
            ontimeTitle = "退换货LES入库及时率";
        } else if ("invoice".equals(nodeType)) {
            ontimeTitle = "退换货发票冲红及时率";
        } else if ("orderclose".equals(nodeType)) {
            ontimeTitle = "退换货订单关闭及时率";
        } else if ("refund".equals(nodeType)) {
            ontimeTitle = "退换货退款及时率";
        } else if ("22storehouse".equals(nodeType)) {
            ontimeTitle = "退换货22转库及时率";
        } else if ("recheckquality".equals(nodeType)) {
            ontimeTitle = "退换货二次质检及时率";
        } else if ("transmitbox".equals(nodeType)) {
            ontimeTitle = "退换货转箱及时率";
        } else if ("transmitstock".equals(nodeType)) {
            ontimeTitle = "退换货转库及时率";
        } else if ("blp".equals(nodeType)) {
            ontimeTitle = "不良品入库及时率";
        }
        sheet.setColumnView(0, columnWidth);
        sheet.setColumnView(1, firstColumnWidth);
        sheet.addCell(new Label(1, temp, "及时率", format1));
        sheet.mergeCells(1, temp, 1, temp + 1);
        sheet.setColumnView(2, columnWidth);
        sheet.addCell(new Label(2, temp, ontimeTitle, format1));
        if ("audit".equals(nodeType)) {
            sheet.mergeCells(2, temp, 6, temp);
        } else if ("refund".equals(nodeType)) {
            sheet.mergeCells(2, temp, 4, temp);
        } else {
            sheet.mergeCells(2, temp, 5, temp);
        }
        temp++;
        sheet.setColumnView(2, columnWidth);
        sheet.addCell(new Label(2, temp, "应", format1));
        sheet.setColumnView(3, columnWidth);
        sheet.addCell(new Label(3, temp, "实", format1));
        sheet.setColumnView(4, columnWidth);
        sheet.addCell(new Label(4, temp, "差", format1));
        sheet.setColumnView(5, columnWidth);
        sheet.addCell(new Label(5, temp, "及时率", format1));
        if (!"refund".equals(nodeType)) {
            sheet.setColumnView(6, columnWidth);
            sheet.addCell(new Label(6, temp, "完成率", format1));
        }
        if ("audit".equals(nodeType)) {
            sheet.setColumnView(7, columnWidth);
            sheet.addCell(new Label(7, temp, "平均效率", format1));
        }
        temp++;
        sheet.setColumnView(1, firstColumnWidth);
        sheet.addCell(new Label(1, temp, "合计", format1));
        sheet.setColumnView(2, columnWidth);
        sheet.addCell(new jxl.write.Number(2, temp, totalCountEntity.total, format1));
        sheet.setColumnView(3, columnWidth);
        sheet.addCell(new jxl.write.Number(3, temp, totalCountEntity.onTime, format1));
        sheet.setColumnView(4, columnWidth);
        sheet.addCell(new jxl.write.Number(4, temp, totalCountEntity.outTime, format1));
        sheet.setColumnView(5, columnWidth);
        sheet.addCell(new Label(5, temp, totalCountEntity.ontimeRate, format1));
        if (!"refund".equals(nodeType)) {
            sheet.setColumnView(6, columnWidth);
            sheet.addCell(new Label(6, temp, totalCountEntity.finishRate, format1));
        }
        if ("audit".equals(nodeType)) {
            sheet.setColumnView(7, columnWidth);
            sheet.addCell(new Label(7, temp, totalCountEntity.avgRate, format1));
        }
        temp++;
        for (String key : countMap.keySet()) {
            sheet.setColumnView(1, columnWidth);
            sheet.addCell(new Label(1, temp, key, format1));
            sheet.setColumnView(2, columnWidth);
            sheet.addCell(new jxl.write.Number(2, temp, countMap.get(key).total, format1));
            sheet.setColumnView(3, columnWidth);
            sheet.addCell(new jxl.write.Number(3, temp, countMap.get(key).onTime, format1));
            sheet.setColumnView(4, columnWidth);
            sheet.addCell(new jxl.write.Number(4, temp, countMap.get(key).outTime, format1));
            sheet.setColumnView(5, columnWidth);
            sheet.addCell(new Label(5, temp, countMap.get(key).ontimeRate, format1));
            if (!"refund".equals(nodeType)) {
                sheet.setColumnView(6, columnWidth);
                sheet.addCell(new Label(6, temp, countMap.get(key).finishRate, format1));
            }
            if ("audit".equals(nodeType)) {
                sheet.setColumnView(7, columnWidth);
                sheet.addCell(new Label(7, temp, countMap.get(key).avgRate, format1));
            }
            temp++;
        }
        temp++;
        String[] title = null;
        if ("audit".equals(nodeType)) {
            title = new String[] { "审核超期", "合计", "审核", "待申请", "用户申请", "人工导入", "审核确认" };
        } else if ("hp".equals(nodeType)) {
            title = new String[] { "HP质检超期", "合计", "派单", "商城审核", "确认", "派工", "质检" };
        } else if ("les".equals(nodeType)) {
            title = new String[] { "入库超期", "合计", "按单取货入库", "质检", "确认配车取货", "拉回", "入库" };
        } else if ("invoice".equals(nodeType)) {
            title = new String[] { "冲红超期", "合计", "发票冲红", "拉回", "入库", "冲红", "订单关闭" };
        } else if ("orderclose".equals(nodeType)) {
            title = new String[] { "订单关闭超期", "合计", "订单关闭", "审核", "质检", "入库", "冲红" };
        } else if ("refund".equals(nodeType)) {
            title = new String[] { "退款超期", "合计", "退款", "审核", "质检", "入库", "冲红" };
        } else if ("22storehouse".equals(nodeType)) {
            title = new String[] { "22转库超期", "合计", "22转库", "22转库", "二次质检", "转箱", "转库" };
        } else if ("recheckquality".equals(nodeType)) {
            title = new String[] { "二次质检超期", "合计", "二次质检", "22转库", "二次质检", "转箱", "转库" };
        } else if ("transmitbox".equals(nodeType)) {
            title = new String[] { "转箱超期", "合计", "转箱", "22转库", "二次质检", "转箱", "转库" };
        } else if ("transmitstock".equals(nodeType)) {
            title = new String[] { "转库超期", "合计", "转库", "22转库", "二次质检", "转箱", "转库" };
        } else if ("blp".equals(nodeType)) {
            title = new String[] { "不良品入库超期", "合计", "按单取货入库", "质检", "确认配车取货", "拉回", "入库" };
        }
        sheet.setColumnView(0, columnWidth);
        sheet.setColumnView(1, firstColumnWidth);
        sheet.addCell(new Label(1, temp, title[0], format1));
        sheet.mergeCells(1, temp, 1, temp + 1);
        sheet.setColumnView(2, firstColumnWidth);
        sheet.addCell(new Label(2, temp, title[1], format1));
        sheet.mergeCells(2, temp, 2, temp + 1);
        sheet.setColumnView(3, firstColumnWidth);
        sheet.addCell(new Label(3, temp, title[2], format1));
        sheet.mergeCells(3, temp, 6, temp);
        temp++;
        sheet.setColumnView(3, columnWidth);
        sheet.addCell(new Label(3, temp, title[3], format1));
        sheet.setColumnView(4, columnWidth);
        sheet.addCell(new Label(4, temp, title[4], format1));
        sheet.setColumnView(5, columnWidth);
        sheet.addCell(new Label(5, temp, title[5], format1));
        sheet.setColumnView(6, columnWidth);
        sheet.addCell(new Label(6, temp, title[6], format1));
        temp++;
        sheet.setColumnView(1, columnWidth);
        sheet.addCell(new Label(1, temp, "已完成已超期", format1));
        sheet.setColumnView(2, columnWidth);
        sheet.addCell(new Label(2, temp, totalCountEntity.cOutTime + "", format1));
        sheet.setColumnView(3, columnWidth);
        sheet.addCell(new Label(3, temp, "/", format1));
        sheet.setColumnView(4, columnWidth);
        sheet.addCell(new Label(4, temp, "/", format1));
        sheet.setColumnView(5, columnWidth);
        sheet.addCell(new Label(5, temp, "/", format1));
        sheet.setColumnView(6, columnWidth);
        sheet.addCell(new Label(6, temp, "/", format1));
        temp++;
        sheet.setColumnView(1, columnWidth);
        sheet.addCell(new Label(1, temp, "未完成已超期", format1));
        sheet.setColumnView(2, columnWidth);
        sheet.addCell(new Label(2, temp, totalCountEntity.uOutTime + "", format1));
        sheet.setColumnView(3, columnWidth);
        sheet.addCell(new Label(3, temp, "/", format1));
        sheet.setColumnView(4, columnWidth);
        sheet.addCell(new Label(4, temp, "/", format1));
        sheet.setColumnView(5, columnWidth);
        sheet.addCell(new Label(5, temp, "/", format1));
        sheet.setColumnView(6, columnWidth);
        sheet.addCell(new Label(6, temp, "/", format1));
        temp++;
        sheet.setColumnView(1, columnWidth);
        sheet.addCell(new Label(1, temp, "未完成未超期", format1));
        sheet.setColumnView(2, columnWidth);
        sheet.addCell(new Label(2, temp, totalCountEntity.uOnTime + "", format1));
        sheet.setColumnView(3, columnWidth);
        sheet.addCell(new Label(3, temp, "/", format1));
        sheet.setColumnView(4, columnWidth);
        sheet.addCell(new Label(4, temp, "/", format1));
        sheet.setColumnView(5, columnWidth);
        sheet.addCell(new Label(5, temp, "/", format1));
        sheet.setColumnView(6, columnWidth);
        sheet.addCell(new Label(6, temp, "/", format1));
    }


    private void setExcelTitle(WritableSheet sheet) throws Exception {
        WritableFont font = new WritableFont(WritableFont.ARIAL, 15, WritableFont.BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);
        WritableCellFormat format = new WritableCellFormat(font);
        format.setAlignment(Alignment.CENTRE);
        Label l_title = new Label(0, 0, "订单全流程-逆向及时率报表", format);
        sheet.setColumnView(0, 25);
        sheet.addCell(l_title);
        sheet.mergeCells(0, 0, 7, 0);
    }


    /**
     * 日期转换成字符串
     * @param date
     * @return str
     */
    private String dateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 根据网单库判断物流
     * @param sCode
     * @param sCodeWlMap
     * @param ordWfRegionList
     * @return
     */
    public String getWlByScode(String sCode, Map<String, String> sCodeWlMap,
                               List<Map<String, String>> ordWfRegionList) {
        if (sCodeWlMap == null || sCodeWlMap.isEmpty()) {
            for (Map<String, String> temp : ordWfRegionList) {
                sCodeWlMap.put(temp.get("sec_code").toString(), temp.get("wlqy_name").toString());
            }
        }
        String wl = sCodeWlMap.get(sCode);
        if (StringUtil.isEmpty(wl)) {
            return "";
        }

        return wl;
    }

    /**
     * 翻译库位
     */
    public String getStorageName(String id, List<Map<String, String>> storageList) {
        if (storageList.size() == 0) {
            List<Map<String, String>> tempStorageList = orderWorkFlowModel.getStorages();
            Iterator<Map<String, String>> it = tempStorageList.iterator();
            while (it.hasNext()) {
                storageList.add(it.next());
            }
        }
        for (Map<String, String> map : storageList) {
            if (map.get("id").equals(id.toUpperCase()))
                return map.get("storageName");
        }
        return "";
    }

    /**
     * 翻译库位(字母)
     */
    public String getStorageCode(String name, List<Map<String, String>> storageList) {
        if (storageList.size() == 0) {
            List<Map<String, String>> tempStorageList = orderWorkFlowModel.getStorages();
            Iterator<Map<String, String>> it = tempStorageList.iterator();
            while (it.hasNext()) {
                storageList.add(it.next());
            }
        }
        for (Map<String, String> map : storageList) {
            if (map.get("storageName").equals(name))
                return map.get("id");
        }
        return "";
    }
    /**
     * 根据行政区id获得行政区名称-判断中心-库存
     * @param secCode
     * @param storageList
     * @return
     * @throws Exception
     */
    private String getKeyStorages(String secCode, List<Map<String, String>> storageList) {
        if (!SCODE_MAP.containsKey(secCode)) {
            for (Map.Entry<String, String> entry : SCODE_MAP.entrySet()) {
                if (secCode.equals(entry.getValue())) {
                    secCode = entry.getKey();
                    break;
                }
            }
        }

        if (storageList.size() == 0) {//防止每次都去查询列表
            List<Map<String, String>> tempList = orderWorkFlowModel.getStorages();
            Iterator<Map<String, String>> it = tempList.iterator();
            while (it.hasNext()) {
                storageList.add(it.next());
            }
        }
        for (Map<String, String> map : storageList) {
            if (secCode.equals(map.get("id")))
                return map.get("storageName");
        }
        return "";
    }


    /**
     * 根据网单库判断工贸
     * @param sCode
     * @param sCodeTradeMap
     * @param ordWfRegionList
     * @return
     */
    public String getTradeByScode(String sCode, Map<String, String> sCodeTradeMap,
                                  List<Map<String, String>> ordWfRegionList) {
        if (sCodeTradeMap == null || sCodeTradeMap.isEmpty()) {
            for (Map<String, String> temp : ordWfRegionList) {
                sCodeTradeMap.put(temp.get("sec_code").toString(), temp.get("gm_name").toString());
            }
        }
        String trade = sCodeTradeMap.get(sCode);
        if (StringUtil.isEmpty(trade)) {
            return "";
        }

        return trade;
    }
    /**
     * 根据网单库判断人员
     * @param sCode
     * @param sCodeTradeMap
     * @param ptclist
     * @param ordWfRegionList
     * @param loginUserName
     * @return
     */
    public String getCommissionerByScode(String sCode, Map<String, String> sCodeTradeMap,
                                         List<PersonTradeCfg> ptclist,
                                         List<Map<String, String>> ordWfRegionList,
                                         String loginUserName, String channel) {
        if (sCodeTradeMap == null || sCodeTradeMap.isEmpty()) {
            for (Map<String, String> temp : ordWfRegionList) {
                sCodeTradeMap.put(temp.get("sec_code").toString(), temp.get("gm_name").toString());
            }
        }

        if (ptclist.size() == 0) {//防止每次都去查询列表
            if (loginUserName.equals("-1")) {
                List<PersonTradeCfg> tempptcList = shopPersonTradeCfgService.getAll();
                Iterator<PersonTradeCfg> it = tempptcList.iterator();
                while (it.hasNext()) {
                    ptclist.add(it.next());
                }
            } else {
                List<Map<String, String>> loginList = shopPersonTradeCfgService
                        .getLoginPersonInfo(loginUserName);

                if (loginList == null || loginList.size() == 0) {
                    return "";
                } else {
                    Map<String, String> tradeExistMap = new HashMap<String, String>();
                    PersonTradeCfg ptc = null;
                    for (Map<String, String> map : loginList) {

                        if (tradeExistMap.containsKey(map.get("trade"))) {
                            continue;
                        }

                        if (map.get("commissioner").contains("@")) {
                            String[] arr = map.get("commissioner").split("@");
                            for (String str : arr) {
                                if (str.equals(loginUserName)) {
                                    ptc = new PersonTradeCfg();
                                    ptc.setCommissioner(loginUserName);
                                    ptc.setTrade(map.get("trade"));
                                    tradeExistMap.put(map.get("trade"), map.get("trade"));
                                    ptclist.add(ptc);
                                    break;
                                }
                            }
                        } else if (loginUserName.equals(map.get("commissioner"))) {
                            ptc = new PersonTradeCfg();
                            ptc.setCommissioner(loginUserName);
                            ptc.setTrade(map.get("trade"));
                            tradeExistMap.put(map.get("trade"), map.get("trade"));
                            ptclist.add(ptc);
                        }
                    }
                }
            }
        }

        String trade = sCodeTradeMap.get(sCode);
        if (StringUtil.isEmpty(trade)) {
            return "";
        }
        for (PersonTradeCfg ptc : ptclist) {
            if (loginUserName.equals("-1")) {
                if (trade.equals(ptc.getTrade())) {
                    if ("1".equals(channel)) {
                        return ptc.getSmallChannelPeople();
                    } else {
                        return ptc.getCommissioner();
                    }
                }
            } else {
                if (trade.equals(ptc.getTrade()) && loginUserName.equals(ptc.getCommissioner())) {
                    return ptc.getCommissioner();
                }
            }
        }

        return "";
    }

    @RequestMapping(value = { "/getCommissioner" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<Map<String, String>>> getCommissioner(@RequestParam(required = true) String area) {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
        Assert.notNull(orderWorkFlowModel, "Property 'orderWorkFlowModel' is required.");
        try {
            result.setData(orderWorkflowRegionService.getCommissioner(area));
        } catch (Exception e) {
            log.error("[order][orderworkflow]获取人员时发生未知错误", e);
            result.setMessage("获取人员时发生未知错误");
        }
        return result;
    }


    @RequestMapping(value = { "/getSmallChannelPeopleTrade" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<Map<String, String>>> getSmallChannelPeopleTrade(@RequestParam(required = true) String areaCommissioner) {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
        Assert.notNull(shopPersonTradeCfgService, "Property 'orderSelectListModel' is required.");
        try {
            result.setData(orderWorkflowRegionService.getSmallChannelPeopleTrade(areaCommissioner));
        } catch (Exception e) {
            log.error("[order][getSmallChannelPeopleTrade]获取小渠道人员工贸时发生未知错误", e);
            result.setMessage("获取小渠道人员工贸时发生未知错误");
        }
        return result;
    }

    @RequestMapping(value = { "/getCommissionerTrade" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<Map<String, String>>> getCommissionerTrade(@RequestParam(required = true) String areaCommissioner) {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
        Assert.notNull(orderWorkFlowModel, "Property 'orderWorkFlowModel' is required.");
        try {
            result.setData(orderWorkflowRegionService.getCommissionerTrade(areaCommissioner));
        } catch (Exception e) {
            log.error("[order][orderworkflow]获取人员工贸时发生未知错误", e);
            result.setMessage("获取人员工贸时发生未知错误");
        }
        return result;
    }

    @RequestMapping(value = { "/getSCode" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<Map<String, String>>> getSCode(@RequestParam(required = true) String trade) {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
        Assert.notNull(orderWorkFlowModel, "Property 'orderWorkFlowModel' is required.");
        try {
            result.setData(orderWorkflowRegionService.getSCode(trade));
        } catch (Exception e) {
            log.error("[order][orderworkflow]获取中心时发生未知错误", e);
            result.setMessage("获取中心时发生未知错误");
        }
        return result;
    }

    @RequestMapping(value = { "/getTrade" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<Map<String, String>>> getTrade(@RequestParam(required = true) String area) {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
        Assert.notNull(orderWorkFlowModel, "Property 'orderWorkFlowModel' is required.");
        try {
            result.setData(orderWorkflowRegionService.getTrade(area));
        } catch (Exception e) {
            log.error("[order][orderworkflow]获取工贸时发生未知错误", e);
            result.setMessage("获取工贸时发生未知错误");
        }
        return result;
    }

    /**
     * 根据行政区id获得行政区名称-判断人员
     * @param region
     * @param loginUserName
     * @param regionList
     * @return
     * @throws Exception
     */
    private String getKeyCommissioner(Long region, List<OrderWorkflowRegion> regionList,
                                      List<PersonTradeCfg> ptclist, String loginUserName,
                                      String channel) {
        if (regionList.size() == 0) {//防止每次都去查询行政区列表
            List<OrderWorkflowRegion> tempRegionList = orderWorkFlowModel.getOwfRegion();
            Iterator<OrderWorkflowRegion> it = tempRegionList.iterator();
            while (it.hasNext()) {
                regionList.add(it.next());
            }
        }

        if (ptclist.size() == 0) {//防止每次都去查询列表
            if (loginUserName.equals("-1")) {
                List<PersonTradeCfg> tempptcList = shopPersonTradeCfgService.getAll();
                Iterator<PersonTradeCfg> it = tempptcList.iterator();
                while (it.hasNext()) {
                    ptclist.add(it.next());
                }
            } else {
                List<Map<String, String>> loginList = shopPersonTradeCfgService
                        .getLoginPersonInfo(loginUserName);

                if (loginList == null || loginList.size() == 0) {
                    return "";
                } else {
                    Map<String, String> tradeExistMap = new HashMap<String, String>();
                    PersonTradeCfg ptc = null;
                    for (Map<String, String> map : loginList) {

                        if (tradeExistMap.containsKey(map.get("trade"))) {
                            continue;
                        }

                        if (map.get("commissioner").contains("@")) {
                            String[] arr = map.get("commissioner").split("@");
                            for (String str : arr) {
                                if (str.equals(loginUserName)) {
                                    ptc = new PersonTradeCfg();
                                    ptc.setCommissioner(loginUserName);
                                    ptc.setTrade(map.get("trade"));
                                    tradeExistMap.put(map.get("trade"), map.get("trade"));
                                    ptclist.add(ptc);
                                    break;
                                }
                            }
                        } else if (loginUserName.equals(map.get("commissioner"))) {
                            ptc = new PersonTradeCfg();
                            ptc.setCommissioner(loginUserName);
                            ptc.setTrade(map.get("trade"));
                            tradeExistMap.put(map.get("trade"), map.get("trade"));
                            ptclist.add(ptc);
                        }
                    }
                }
            }
        }

        for (OrderWorkflowRegion owfRegion : regionList) {
            if (region.intValue() == owfRegion.getRegionId()) {
                for (PersonTradeCfg ptc : ptclist) {

                    if (loginUserName.equals("-1")) {
                        if (owfRegion.getGmName().equals(ptc.getTrade())) {
                            if ("1".equals(channel)) {
                                return ptc.getSmallChannelPeople();
                            } else {
                                return ptc.getCommissioner();
                            }
                        }
                    } else {
                        if (owfRegion.getGmName().equals(ptc.getTrade())
                                && loginUserName.equals(ptc.getCommissioner())) {
                            return ptc.getCommissioner();
                        }
                    }
                }
            }
        }
        return "";
    }

    @RequestMapping(value = { "/getChannelNames" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<Map<String, Object>>> getTrade() {
        HttpJsonResult<List<Map<String, Object>>> result = new HttpJsonResult<List<Map<String, Object>>>();
        Assert.notNull(orderWorkFlowModel, "Property 'orderWorkFlowModel' is required.");
        try {
            result.setData(stockInvChannel2OrderSourceService.getChannelNames());
        } catch (Exception e) {
            log.error("[order][orderworkflow]获取订单来源时发生未知错误", e);
            result.setMessage("获取订单来源时发生未知错误");
        }
        return result;
    }

    /**
     * 获得省市区
     * @param parentId
     * @return
     */
    @RequestMapping(value = { "/getRegions" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<Map<String, String>>> getProductType(@RequestParam(required = true) Integer parentId) {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
        Assert.notNull(orderWorkFlowModel, "Property 'orderWorkFlowModel' is required.");
        try {
            result.setData(orderWorkFlowModel.getRegions(parentId));
        } catch (Exception e) {
            log.error("[order][orderworkflow]获取区县时发生未知错误", e);
            result.setMessage("获取区县时发生未知错误");
        }
        return result;
    }

    @RequestMapping(value = { "/getTradeAllCommissioner" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<Map<String, String>>> getTradeAllCommissioner() {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
        Assert.notNull(shopPersonTradeCfgService, "Property 'orderSelectListModel' is required.");
        try {
            result.setData(shopPersonTradeCfgService.getTradeAllCommissioner());
        } catch (Exception e) {
            log.error("[order][getTradeAllCommissioner]获取大渠道人员时发生未知错误", e);
            result.setMessage("获取大渠道人员时发生未知错误");
        }
        return result;
    }

    /**
     * 根据行政区id获得行政区名称
     * @param region
     * @param name
     * @param regionList
     * @return
     * @throws Exception
     */
    private String getKey(Long region, String name,
                          List<OrderWorkflowRegion> regionList) throws Exception {
        if (regionList.size() == 0) {//防止每次都去查询行政区列表
            List<OrderWorkflowRegion> tempRegionList = orderWorkFlowModel.getOwfRegion();
            Iterator<OrderWorkflowRegion> it = tempRegionList.iterator();
            while (it.hasNext()) {
                regionList.add(it.next());
            }
        }
        for (OrderWorkflowRegion owfRegion : regionList) {
            if ("area".equals(name)) {
                if (region.equals(owfRegion.getRegionId().longValue()))
                    return owfRegion.getQyName();
            } else if ("fdArea".equals(name)) {
                if (region.equals(owfRegion.getRegionId().longValue()))
                    return owfRegion.getWlqyName();
            } else if ("areaTrade".equals(name) || "fdAreaTrade".equals(name)) {
                if (region.intValue() == owfRegion.getRegionId())
                    return owfRegion.getGmName();
            } else if ("areaSCode".equals(name) || "fdAreaSCode".equals(name)) {
                if (region.intValue() == owfRegion.getRegionId())
                    return owfRegion.getSecName();
            } else if ("province".equals(name)) {
                if (region.intValue() == owfRegion.getRegionId())
                    return owfRegion.getProvinceName();
            } else if ("city".equals(name)) {
                if (region.intValue() == owfRegion.getRegionId())
                    return owfRegion.getCityName();
            } else if ("region".equals(name)) {
                if (region.intValue() == owfRegion.getRegionId())
                    return owfRegion.getRegionName();
            }
        }
        return "";
    }

    public String judgeTimeout(Map<String, Object> map, Map<String, Object> paramMap,
                               String nodeType) throws Exception {
        nodeType = nodeType == null ? (String) paramMap.get("nodeType") : nodeType;
        if ("audit".equals(nodeType)) {//退换货审核
            Long applyTime = (Long) map.get("addTime");
            Long auditTime = (map.get("handleTime") == null) ? 0L : ((Long) map.get("handleTime"));
            Calendar auditRealCalendar = Calendar.getInstance();
            Calendar auditMustCalendar = Calendar.getInstance();
            auditRealCalendar.setTime(auditTime == 0 ? new Date() : new Date(auditTime * 1000L));
            auditMustCalendar.setTime(new Date(applyTime * 1000L));
            //退换货审核在申请后24H内完成
            auditMustCalendar.add(Calendar.DATE, 1);
            //0:未超期；1：已完成超期；2：未完成超期；3未完成未超期
            paramMap.put("mustDate", calendarToStr(auditMustCalendar));
            return auditRealCalendar.after(auditMustCalendar) ? (auditTime > 0 ? "1" : "2")
                    : (auditTime > 0 ? "0" : "3");
        } else if ("hp".equals(nodeType)) {//HP质检
            Long hpFirstAddTime = (Long) map.get("hpFirstAddTime");
            Integer hpTime = (Integer) map.get("hpTime");
            Calendar hpRealCalendar = Calendar.getInstance();
            Calendar hpMustCalendar = Calendar.getInstance();
            hpRealCalendar
                    .setTime((hpTime == null || hpTime == 0) ? new Date() : new Date(hpTime * 1000L));
            hpMustCalendar.setTime(new Date(hpFirstAddTime * 1000L));
            //HP一次质检在向hp推送成功后48H内完成
            hpMustCalendar.add(Calendar.DATE, 2);
            paramMap.put("mustDate", calendarToStr(hpMustCalendar));
            return hpRealCalendar.after(hpMustCalendar)
                    ? ((hpTime == null || hpTime == 0) ? "2" : "1")
                    : ((hpTime == null || hpTime == 0) ? "3" : "0");
        } else if ("les".equals(nodeType)) {//LES入库
            Long lesTime = (Long) map.get("lesTime");
            Long lesOutPingTime = (Long) map.get("lesOutPingTime");
            Calendar lesRealCalendar = Calendar.getInstance();
            Calendar lesMustCalendar = Calendar.getInstance();
            lesRealCalendar.setTime(lesOutPingTime == null || lesOutPingTime == 0 ? new Date()
                    : new Date(lesOutPingTime * 1000L));
            lesMustCalendar.setTime(new Date(lesTime == null ? 0 : lesTime * 1000L));
            //LES入库在LES开单后7天内完成
            lesMustCalendar.add(Calendar.DATE, 7);
            paramMap.put("mustDate", calendarToStr(lesMustCalendar));
            return lesRealCalendar.after(lesMustCalendar)
                    ? (lesOutPingTime == null || lesOutPingTime == 0 ? "2" : "1")
                    : (lesOutPingTime == null || lesOutPingTime == 0 ? "3" : "0");
        } else if ("invoice".equals(nodeType)) {//开发票
            Long lesOutPingTime = (Long) map.get("lesOutPingTime");
            Long invalidTime = (Long) map.get("invalidTime");
            if (lesOutPingTime == null || lesOutPingTime == 0)
                return invalidTime == null || invalidTime == 0 ? "2" : "0";
            Calendar invoiceRealCalendar = Calendar.getInstance();
            Calendar invoiceMustCalendar = Calendar.getInstance();
            invoiceRealCalendar.setTime(invalidTime == null || invalidTime == 0 ? new Date()
                    : new Date(invalidTime * 1000L));
            invoiceMustCalendar.setTime(new Date(lesOutPingTime * 1000L));
            //开发票需在les入库后24H内完成
            invoiceMustCalendar.add(Calendar.DATE, 1);
            paramMap.put("mustDate", calendarToStr(invoiceMustCalendar));
            return invoiceRealCalendar.after(invoiceMustCalendar)
                    ? (invalidTime == null || invalidTime == 0 ? "2" : "1")
                    : (invalidTime == null || invalidTime == 0 ? "3" : "0");
        } else if ("orderclose".equals(nodeType)) {//订单关闭
            //开始时间：推送HP一次质检时间
            //结束时间：退换货状态为“已驳回”并且驳回时间有值，取驳回时间
            //                退换货状态为“受理完成”并且受理完成时间有值，取受理完成时间
            //                退换货状态为“已终止”并且终止时间有值，取终止时间
            //                否则用取消关闭时间
            //判断逻辑：
            //1、线下已退款直接入实、不良品直接入实、超500公里直接入实
            //2、非1的情况，开始时间＋9天为应完成时间
            //     应完成时间在实际完成时间之后，如果结束时间有值，则已完成已超期，否则，未完成已超期
            //     应完成时间在实际完成时间之前，如果结束时间有值，则已完成未超期，否则，未完成未超期
            //     在此基础上，如果发票［未冲红］并且退换货状态［不是］已驳回和已终止两种情况，实要变未完成未超期，已完成已超期要变未完成已超期

            //            Integer status = (Integer) map.get("status");
            //退换货订单关闭及时率，开始时间为推送HP时间
            //            Long applyTime = (Long) map.get("addTime");
            Long applyTime = (Long) map.get("hpFirstAddTime");
            //            Long finishTime = (Long) map.get("finishTime");
            Long cancelTime = (Long) map.get("cancelTime");
            //            Long closeTime = status == 110 ? cancelTime : finishTime;
            Long closeTime = cancelTime;
            //************优化增加开始**********************
            Long state = typeJudge(map.get("state"));
            Long handleStatus = typeJudge(map.get("handleStatus"));
            Long rejectEndTime = 0l;
            if (handleStatus == 5 && typeJudge(map.get("handleTime")) > 0) {
                rejectEndTime = typeJudge(map.get("handleTime"));
            } else
            if ((handleStatus == 3 || handleStatus == 6) && typeJudge(map.get("endTime")) > 0) {
                rejectEndTime = typeJudge(map.get("endTime"));
            }

            if ((handleStatus == 5 && rejectEndTime > 0)
                    || ((handleStatus == 3 || handleStatus == 6) && rejectEndTime > 0)) {
                closeTime = rejectEndTime;
            }
            //************优化增加结束**********************
            Calendar ordercloseRealCalendar = Calendar.getInstance();
            Calendar ordercloseMustCalendar = Calendar.getInstance();
            ordercloseRealCalendar
                    .setTime(closeTime == 0 ? new Date() : new Date(closeTime * 1000L));
            ordercloseMustCalendar.setTime(new Date(applyTime * 1000L));
            //开发票需在les入库后24H内完成
            ordercloseMustCalendar.add(Calendar.DATE, 9);
            paramMap.put("mustDate", calendarToStr(ordercloseMustCalendar));
            //线下已退款直接入实 //不良品直接入实 //超500公里直接入实
            if (handleStatus == 7 || typeJudge(map.get("quality")) == 3
                    || Double.parseDouble(map.get("shippingDistance").toString()) > 500) {
                return "0";
            }

            String strReturn = ordercloseRealCalendar.after(ordercloseMustCalendar)
                    ? (closeTime == 0 ? "2" : "1") : (closeTime == 0 ? "3" : "0");
            //************优化增加开始**********************
            //发票未冲红情况
            if (state != 4 && state != 2 && !(handleStatus == 5 && rejectEndTime > 0)
                    && !(handleStatus == 6 && rejectEndTime > 0)) {
                if (strReturn.equals("0")) {
                    strReturn = "3";
                } else if (strReturn.equals("1")) {
                    strReturn = "2";
                }
            }
            //************优化增加结束**********************
            return strReturn;
        } else if ("refund".equals(nodeType)) {//订单退款--------
            String handleStatus = map.get("handleStatus") == null ? null
                    : map.get("handleStatus").toString();//handleStatus=5 已驳回
            Long handleTime = (handleStatus != null && handleStatus.equals("5"))
                    ? (Long) map.get("handleTime") : 0L;//审核驳回时间
            Object objendtime = map.get("endTime");
            Long tempTime = 0L;
            if (objendtime instanceof Integer)
                tempTime = ((Integer) objendtime).longValue();
            else if (objendtime instanceof Long)
                tempTime = ((Long) objendtime);
            //(Long) Long.parseLong(map.get("endTime") == null ? "0" : map.get(
            //"endTime").toString());//终止时间 ors.finishTime as endTime
            Long endTime = tempTime;
            Long paymentTime = (Long) map.get("paymentTime");//付款时间
            String source = (String) map.get("source");//订单来源-渠道
            Long realTime = paymentTime;//实际处理时间，默认已退款时间，实际取【已退款时间】、【审核驳回时间】、【终止时间】最早时间
            /**
             * 应：应完成时间=【申请退款时间】+3天（剔除【退换货类型】为“退货不退款”的情况）；
             * 实：【已退款时间】、【审核驳回时间】、【终止时间】任意字段有值最早时间且小于应完成时间为实，否则为差；
             * ①非天猫渠道的直接控制中心取数，②天猫渠道的目前未对接数据获取不到，如果【审核驳回时间】、【终止时间】全为空，先按实计算
             */
            if (paymentTime == null || paymentTime == 0) {
                realTime = endTime;
            } else {
                if (endTime == null || endTime == 0) {
                    realTime = paymentTime;
                } else {
                    if (paymentTime - endTime < 0) {
                        realTime = paymentTime;
                    } else {
                        realTime = endTime;
                    }
                }
            }
            if (realTime == null || realTime == 0) {
                realTime = handleTime;
            } else {
                if (!(handleTime == null || handleTime == 0)) {
                    if (handleTime - realTime < 0) {
                        realTime = handleTime;
                    }
                }
            }

            Long addtime = (Long) map.get("addTime");
            Long refundTime = realTime;
            Calendar refundRealCalendar = Calendar.getInstance();
            Calendar refundMustCalendar = Calendar.getInstance();
            if (java.util.Arrays.toString(sources).contains(source)) {//判断是否天猫渠道
                //如果是天猫渠道来源订单，则设置实际完成时间为申请时间，需求规定这部分已完成，即应、实、差、里的“实”，
                refundRealCalendar.setTime((refundTime == null || refundTime == 0)
                        ? new Date(addtime * 1000L) : new Date(refundTime * 1000L));
            } else {
                refundRealCalendar.setTime((refundTime == null || refundTime == 0) ? new Date()
                        : new Date(refundTime * 1000L));
            }

            refundMustCalendar.setTime(new Date(addtime * 1000L));
            //申请退货成功后3天内完成
            refundMustCalendar.add(Calendar.DATE, 3);
            paramMap.put("mustDate", calendarToStr(refundMustCalendar));
            //0:已完成未超期；1：已完成已超期；2：未完成已超期；3未完成未超期
            if (java.util.Arrays.toString(sources).contains(source)) {//判断是否天猫渠道
                //如果是天猫渠道来源订单，则设置实际完成时间为申请时间，需求规定这部分已完成未超期，即应、实、差、里的“实”，设置为0
                return refundRealCalendar.after(refundMustCalendar)
                        ? ((refundTime == null || refundTime == 0) ? "2" : "1") : "0";
            } else {
                return refundRealCalendar.after(refundMustCalendar)
                        ? ((refundTime == null || refundTime == 0) ? "2" : "1")
                        : ((refundTime == null || refundTime == 0) ? "3" : "0");
            }

        } else if ("22storehouse".equals(nodeType)) {//22库
            Long lesOutPingTimeStart = typeJudge(map.get("in22StoreTime"));
            Long lesOutPingTimeEnd = typeJudge(map.get("in1041StoreTime"));

            Calendar storeHouseRealCalendar = Calendar.getInstance();
            Calendar storeHouseMustCalendar = Calendar.getInstance();
            storeHouseRealCalendar
                    .setTime(lesOutPingTimeEnd == 0 ? new Date() : new Date(lesOutPingTimeEnd * 1000L));
            storeHouseMustCalendar.setTime(new Date(lesOutPingTimeStart * 1000L));
            //入22库回传时间，21天内，入10或者41库
            storeHouseMustCalendar.add(Calendar.DATE, 22);
            paramMap.put("mustDate", calendarToStr(storeHouseMustCalendar));
            return storeHouseRealCalendar.after(storeHouseMustCalendar)
                    ? (lesOutPingTimeEnd == 0 ? "2" : "1") : (lesOutPingTimeEnd == 0 ? "3" : "0");
        } else if ("recheckquality".equals(nodeType)) {//二次质检
            Long lesOutPingTime = typeJudge(map.get("in22StoreTime"));
            Long hpTime = typeJudge(map.get("jdTime"));

            if (hpTime == 0l) {
                Map<String, Object> storeMap = shopOrderWorkflowsRunTimeService
                        .get1041StoreExistInfo(Integer.parseInt(map.get("id").toString()));
                if (storeMap != null && storeMap.size() > 0) {
                    hpTime = typeJudge(storeMap.get("lesOrderSnTime")) > 0
                            ? typeJudge(storeMap.get("lesOrderSnTime"))
                            : typeJudge(storeMap.get("addTime"));
                }
            }

            Calendar reCheckQualityRealCalendar = Calendar.getInstance();
            Calendar reCheckQualityMustCalendar = Calendar.getInstance();
            reCheckQualityRealCalendar.setTime(hpTime == 0 ? new Date() : new Date(hpTime * 1000L));
            reCheckQualityMustCalendar.setTime(new Date(lesOutPingTime * 1000L));
            //入22库回传时间，5天内，hp回传鉴定时间(checkType=2)
            reCheckQualityMustCalendar.add(Calendar.DATE, 5);
            paramMap.put("mustDate", calendarToStr(reCheckQualityMustCalendar));
            return reCheckQualityRealCalendar.after(reCheckQualityMustCalendar)
                    ? (hpTime == 0 ? "2" : "1") : (hpTime == 0 ? "3" : "0");
        } else if ("transmitbox".equals(nodeType)) {//转箱
            Long hpTimeStart = typeJudge(map.get("jdTime"));
            Long hpTimeEnd = typeJudge(map.get("hxTime"));

            if (hpTimeEnd == 0l) {
                Map<String, Object> storeMap = shopOrderWorkflowsRunTimeService
                        .get1041StoreExistInfo(Integer.parseInt(map.get("id").toString()));
                if (storeMap != null && storeMap.size() > 0) {
                    hpTimeEnd = typeJudge(storeMap.get("lesOrderSnTime")) > 0
                            ? typeJudge(storeMap.get("lesOrderSnTime"))
                            : typeJudge(storeMap.get("addTime"));
                }
            }

            Calendar transmitBoxRealCalendar = Calendar.getInstance();
            Calendar transmitBoxMustCalendar = Calendar.getInstance();
            transmitBoxRealCalendar
                    .setTime(hpTimeEnd == 0 ? new Date() : new Date(hpTimeEnd * 1000L));
            transmitBoxMustCalendar.setTime(new Date(hpTimeStart * 1000L));
            //hp回传鉴定时间(checkType=2)，15天内，hp回传换箱时间(checkType=3)
            transmitBoxMustCalendar.add(Calendar.DATE, 15);
            paramMap.put("mustDate", calendarToStr(transmitBoxMustCalendar));
            return transmitBoxRealCalendar.after(transmitBoxMustCalendar)
                    ? (hpTimeEnd == 0 ? "2" : "1") : (hpTimeEnd == 0 ? "3" : "0");
        } else if ("transmitstock".equals(nodeType)) {//转库
            Long lesOrderSnTime = typeJudge(map.get("lesOrderSnTime13"));
            Long lesOutPingTime = typeJudge(map.get("in1041StoreTime"));

            Calendar transmitStockRealCalendar = Calendar.getInstance();
            Calendar transmitStockMustCalendar = Calendar.getInstance();
            transmitStockRealCalendar
                    .setTime(lesOutPingTime == 0 ? new Date() : new Date(lesOutPingTime * 1000L));
            transmitStockMustCalendar.setTime(new Date(lesOrderSnTime * 1000L));
            //存性变更入10或者41库开单时间，1天内，转库完成
            transmitStockMustCalendar.add(Calendar.DATE, 2);
            paramMap.put("mustDate", calendarToStr(transmitStockMustCalendar));
            return transmitStockRealCalendar.after(transmitStockMustCalendar)
                    ? (lesOutPingTime == 0 ? "2" : "1") : (lesOutPingTime == 0 ? "3" : "0");
        } else if ("blp".equals(nodeType)) {//不良品
            //开始时间：退换货列表退换货订单鉴定符合不良品
            Long hpTime = typeJudge(map.get("hpTime"));
            //结束时间：入日日顺21库
            Long storageTime = typeJudge(map.get("storageTime"));

            Calendar blpRealCalendar = Calendar.getInstance();
            Calendar blpMustCalendar = Calendar.getInstance();
            blpRealCalendar.setTime(storageTime == null || storageTime == 0 ? new Date()
                    : new Date(storageTime * 1000L));
            blpMustCalendar.setTime(new Date(hpTime == null ? 0 : hpTime * 1000L));
            //退换货列表退换货订单鉴定符合不良品48小时入日日顺21库
            blpMustCalendar.add(Calendar.DATE, 15);
            paramMap.put("mustDate", calendarToStr(blpMustCalendar));
            return blpRealCalendar.after(blpMustCalendar)
                    ? (storageTime == null || storageTime == 0 ? "2" : "1")
                    : (storageTime == null || storageTime == 0 ? "3" : "0");
        }
        return null;
    }

    private Long typeJudge(Object o) {
        Long tempReturn = 0L;
        if (o == null)
            return tempReturn;
        if (o instanceof Integer)
            tempReturn = ((Integer) o).longValue();
        else if (o instanceof Long)
            tempReturn = ((Long) o);
        else
            tempReturn = Long.parseLong(o.toString());
        return tempReturn;
    }

    /**
     * 统计及时率
     */
    private void countOntime(Map<String, Object> paramMap, Map<String, Object> map,
                             CountEntity countEntity) throws Exception {
        Integer num = (Integer) map.get("count");//申请退货台数
        countEntity.total = countEntity.total + num;//应
        String timeoutType = judgeTimeout(map, paramMap, null);
        countEntity.totalRepairId = countEntity.totalRepairId.append((Long) map.get("id"))
                .append(",");
        countEntity.onTime = "0".equals(timeoutType) ? countEntity.onTime + num
                : countEntity.onTime;//实
        countEntity.onTimeRepairId = "0".equals(timeoutType)
                ? countEntity.onTimeRepairId.append((Long) map.get("id")).append(",")
                : countEntity.onTimeRepairId;
        countEntity.outTime = countEntity.total - countEntity.onTime;//差
        countEntity.outTimeRepairId = !"0".equals(timeoutType)
                ? countEntity.outTimeRepairId.append((Long) map.get("id")).append(",")
                : countEntity.outTimeRepairId;
        countEntity.cOutTime = "1".equals(timeoutType) ? countEntity.cOutTime + num
                : countEntity.cOutTime;
        countEntity.cOutTimeRepairId = "1".equals(timeoutType)
                ? countEntity.cOutTimeRepairId.append((Long) map.get("id")).append(",")
                : countEntity.cOutTimeRepairId;
        countEntity.uOutTime = "2".equals(timeoutType) ? countEntity.uOutTime + num
                : countEntity.uOutTime;
        countEntity.uOutTimeRepairId = "2".equals(timeoutType)
                ? countEntity.uOutTimeRepairId.append((Long) map.get("id")).append(",")
                : countEntity.uOutTimeRepairId;
        countEntity.uOnTime = "3".equals(timeoutType) ? countEntity.uOnTime + num
                : countEntity.uOnTime;
        countEntity.uOnTimeRepairId = "3".equals(timeoutType)
                ? countEntity.uOnTimeRepairId.append((Long) map.get("id")).append(",")
                : countEntity.uOnTimeRepairId;
        //        countEntity.ontimeRate = new DecimalFormat("#0.00").format(countEntity.onTime.floatValue()
        //                                                                   / countEntity.total * 100) + '%';
        //        countEntity.finishRate = new DecimalFormat("#0.00")
        //            .format((countEntity.onTime.floatValue() + countEntity.cOutTime.floatValue())
        //                    / countEntity.total * 100) + '%';
        //审核平均效率计算
        if ("audit".equals((String) paramMap.get("nodeType")) && (Long) map.get("handleStatus") > 2
                && (map.get("handleTime") == null ? 0L : (Long) map.get("handleTime")) > 0) {
            countEntity.realAudit += num;
            countEntity.auditTotalTime += (Long) map.get("handleTime") - (Long) map.get("addTime");
            //            countEntity.avgRate = new DecimalFormat("#0.00").format(countEntity.auditTotalTime
            //                .floatValue() / countEntity.realAudit / 60 / 60)
            //                                  + "H";
        }
    }

    /**
     * 日期转换成字符串
     * @param calendar
     * @return str
     */
    private String calendarToStr(Calendar calendar) {
        if (null == formatAll.get()) {
            formatAll.set(new SimpleDateFormat("yyyyMMdd"));
        }
        return formatAll.get().format(calendar.getTime());
    }

    private void addDistance(List<Map<String, Object>> list, Map<String, Object> distanceMap) {
        for (Map<String, Object> map : list) {
            map.put("shippingDistance", getDistance(map.get("region").toString(), distanceMap));
        }
    }

    /**
     * 获取配送距离
     * @param region
     * @param distanceMap
     * @return
     */
    private double getDistance(String region, Map<String, Object> distanceMap) {
        try {
            return Double.parseDouble(distanceMap.get(region).toString());
        } catch (Exception e) {
            return 0.0;
        }
    }


    public static Map<String, String> SCODE_MAP       = new HashMap<String, String>();
    public static Map<String, String> SCODE_VALUE_MAP = new HashMap<String, String>();

    static {
        SCODE_MAP.put("ASWA", "ASO1");
        SCODE_MAP.put("AKWA", "AKO1");
        SCODE_MAP.put("YBWA", "YBO1");
        SCODE_MAP.put("BSWA", "BSO1");
        SCODE_MAP.put("BBWA", "BBO1");
        SCODE_MAP.put("BTWA", "BTO1");
        SCODE_MAP.put("BDWA", "BDO1");
        SCODE_MAP.put("BJWA", "BJO2");
        SCODE_MAP.put("CZWA", "CZO1");
        SCODE_MAP.put("DCWA", "DCO1");
        SCODE_MAP.put("CDWA", "CDO1");
        SCODE_MAP.put("LDWA", "LDO1");
        SCODE_MAP.put("DLWA", "DLO1");
        SCODE_MAP.put("DQWA", "DQO1");
        SCODE_MAP.put("DTWA", "DTO1");
        SCODE_MAP.put("DGWA", "DGO1");
        SCODE_MAP.put("FSWA", "FSO1");
        SCODE_MAP.put("FZWA", "FZO1");
        SCODE_MAP.put("FYWA", "FYO1");
        SCODE_MAP.put("JXWA", "JXO1");
        SCODE_MAP.put("GAWA", "GAO1");
        SCODE_MAP.put("LGWA", "LGO1");
        SCODE_MAP.put("GYWA", "GYO1");
        SCODE_MAP.put("DBWA", "DBO1");
        SCODE_MAP.put("HKWA", "HKO1");
        SCODE_MAP.put("HDWA", "HDO1");
        SCODE_MAP.put("HZWA", "HZO1");
        SCODE_MAP.put("HFWA", "HFO1");
        SCODE_MAP.put("HNWA", "HNO2");
        SCODE_MAP.put("WYWA", "WYO1");
        SCODE_MAP.put("HYWA", "HYO1");
        SCODE_MAP.put("HMWA", "HMO1");
        SCODE_MAP.put("HSWA", "HSO1");
        SCODE_MAP.put("HAWA", "HAO1");
        SCODE_MAP.put("JOWA", "JOO2");
        SCODE_MAP.put("SDWA", "SDO2");
        SCODE_MAP.put("JNWA", "JNO2");
        SCODE_MAP.put("JMWA", "JMO1");
        SCODE_MAP.put("JHWA", "JHO1");
        SCODE_MAP.put("JZWA", "JZO1");
        SCODE_MAP.put("KMWA", "KMO1");
        SCODE_MAP.put("LZWA", "LZO1");
        SCODE_MAP.put("LYWA", "LYO2");
        SCODE_MAP.put("GXWA", "GXO1");
        SCODE_MAP.put("DWWA", "DWO1");
        SCODE_MAP.put("LXWA", "LXO1");
        SCODE_MAP.put("LMWA", "LMO2");
        SCODE_MAP.put("LHWA", "LHO2");
        SCODE_MAP.put("MMWA", "MMO1");
        SCODE_MAP.put("MYWA", "MYO1");
        SCODE_MAP.put("NCWA", "NCO1");
        SCODE_MAP.put("SCWA", "SCO1");
        SCODE_MAP.put("NJWA", "NJO1");
        SCODE_MAP.put("NNWA", "NNO1");
        SCODE_MAP.put("NPWA", "NPO1");
        SCODE_MAP.put("NYWA", "NYO2");
        SCODE_MAP.put("NBWA", "NBO2");
        SCODE_MAP.put("QZWA", "QZO1");
        SCODE_MAP.put("XMWA", "XMO1");
        SCODE_MAP.put("STWA", "STO1");
        SCODE_MAP.put("SQWA", "SQO2");
        SCODE_MAP.put("SHWA", "SHO2");
        SCODE_MAP.put("SRWA", "SRO1");
        SCODE_MAP.put("SYWA", "SYO1");
        SCODE_MAP.put("HBWA", "HBO1");
        SCODE_MAP.put("XXWA", "XXO1");
        SCODE_MAP.put("SOWA", "SOO1");
        SCODE_MAP.put("TZWA", "TZO1");
        SCODE_MAP.put("TYWA", "TYO1");
        SCODE_MAP.put("TSWA", "TSO1");
        SCODE_MAP.put("TJWA", "TJO1");
        SCODE_MAP.put("GSWA", "GSO1");
        SCODE_MAP.put("WFWA", "WFO2");
        SCODE_MAP.put("NWWA", "NWO2");
        SCODE_MAP.put("WXWA", "WXO1");
        SCODE_MAP.put("WNWA", "WNO1");
        SCODE_MAP.put("WHWA", "WHO2");
        SCODE_MAP.put("XAWA", "XAO1");
        SCODE_MAP.put("XNWA", "XNO1");
        SCODE_MAP.put("XFWA", "XFO2");
        SCODE_MAP.put("XJWA", "XJO1");
        SCODE_MAP.put("XYWA", "XYO2");
        SCODE_MAP.put("XZWA", "XZO1");
        SCODE_MAP.put("YTWA", "YTO2");
        SCODE_MAP.put("CYWA", "CYO2");
        SCODE_MAP.put("YCWA", "YCO1");
        SCODE_MAP.put("YLWA", "YLO1");
        SCODE_MAP.put("ZJWA", "ZJO1");
        SCODE_MAP.put("CCWA", "CCO1");
        SCODE_MAP.put("CSWA", "CSO1");
        SCODE_MAP.put("ZZWA", "ZZO2");
        SCODE_MAP.put("CQWA", "CQO1");
        SCODE_MAP.put("ZBWA", "ZBO2");
        SCODE_MAP.put("ZYWA", "ZYO1");

        for (Map.Entry<String, String> entry : SCODE_MAP.entrySet()) {
            SCODE_VALUE_MAP.put(entry.getValue(), entry.getKey());
        }
    }
    /**
     *  及时率统计实体
     */
    public class CountEntity {
        /**应完成数*/
        Integer      total            = 0;                 //应完成数
        StringBuffer totalRepairId    = new StringBuffer();
        /**按时完成数，即实*/
        Integer      onTime           = 0;                 //按时完成数，即实
        StringBuffer onTimeRepairId   = new StringBuffer();
        /**超期完成数、未完成未超期、未完成超期数，即差*/
        Integer      outTime          = 0;                 //超期完成数、未完成未超期、未完成超期数，即差
        StringBuffer outTimeRepairId  = new StringBuffer();
        /**超期完成数*/
        Integer      cOutTime         = 0;                 //超期完成数
        StringBuffer cOutTimeRepairId = new StringBuffer();
        /**未完成未超期数*/
        Integer      uOnTime          = 0;                 //未完成未超期数
        StringBuffer uOnTimeRepairId  = new StringBuffer();
        /**未完成超期数*/
        Integer      uOutTime         = 0;                 //未完成超期数
        StringBuffer uOutTimeRepairId = new StringBuffer();
        /**及时率*/
        String       ontimeRate       = "0.00%";           //及时率
        /**完成率  */
        String       finishRate       = "0.00%";           //完成率
        /**平均效率*/
        String       avgRate          = "0H";              //平均效率
        /**实际审核数量*/
        Integer      realAudit        = 0;                 //实际审核数量
        Long         auditTotalTime   = 0L;                //审核总时间

        public CountEntity clone() {
            CountEntity countEntity = new CountEntity();
            /**应完成数*/
            countEntity.total = total; //应完成数
            countEntity.totalRepairId = null;
            /**按时完成数，即实*/
            countEntity.onTime = onTime; //按时完成数，即实
            countEntity.onTimeRepairId = null;
            /**超期完成数、未完成未超期、未完成超期数，即差*/
            countEntity.outTime = outTime; //超期完成数、未完成未超期、未完成超期数，即差
            countEntity.outTimeRepairId = null;
            /**超期完成数*/
            countEntity.cOutTime = cOutTime; //超期完成数
            countEntity.cOutTimeRepairId = null;
            /**未完成未超期数*/
            countEntity.uOnTime = uOnTime; //未完成未超期数
            countEntity.uOnTimeRepairId = null;
            /**未完成超期数*/
            countEntity.uOutTime = uOutTime; //未完成超期数
            countEntity.uOutTimeRepairId = null;
            /**及时率*/
            countEntity.ontimeRate = ontimeRate; //及时率
            /**完成率  */
            countEntity.finishRate = finishRate; //完成率
            /**平均效率*/
            countEntity.avgRate = avgRate; //平均效率
            /**实际审核数量*/
            countEntity.realAudit = realAudit; //实际审核数量
            countEntity.auditTotalTime = auditTotalTime; //审核总时间
            return countEntity;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public StringBuffer getTotalRepairId() {
            return totalRepairId;
        }

        public void setTotalRepairId(StringBuffer totalRepairId) {
            this.totalRepairId = totalRepairId;
        }

        public Integer getOnTime() {
            return onTime;
        }

        public void setOnTime(Integer onTime) {
            this.onTime = onTime;
        }

        public StringBuffer getOnTimeRepairId() {
            return onTimeRepairId;
        }

        public void setOnTimeRepairId(StringBuffer onTimeRepairId) {
            this.onTimeRepairId = onTimeRepairId;
        }

        public Integer getOutTime() {
            return outTime;
        }

        public void setOutTime(Integer outTime) {
            this.outTime = outTime;
        }

        public StringBuffer getOutTimeRepairId() {
            return outTimeRepairId;
        }

        public void setOutTimeRepairId(StringBuffer outTimeRepairId) {
            this.outTimeRepairId = outTimeRepairId;
        }

        public Integer getcOutTime() {
            return cOutTime;
        }

        public void setcOutTime(Integer cOutTime) {
            this.cOutTime = cOutTime;
        }

        public StringBuffer getcOutTimeRepairId() {
            return cOutTimeRepairId;
        }

        public void setcOutTimeRepairId(StringBuffer cOutTimeRepairId) {
            this.cOutTimeRepairId = cOutTimeRepairId;
        }

        public Integer getuOnTime() {
            return uOnTime;
        }

        public void setuOnTime(Integer uOnTime) {
            this.uOnTime = uOnTime;
        }

        public StringBuffer getuOnTimeRepairId() {
            return uOnTimeRepairId;
        }

        public void setuOnTimeRepairId(StringBuffer uOnTimeRepairId) {
            this.uOnTimeRepairId = uOnTimeRepairId;
        }

        public Integer getuOutTime() {
            return uOutTime;
        }

        public void setuOutTime(Integer uOutTime) {
            this.uOutTime = uOutTime;
        }

        public StringBuffer getuOutTimeRepairId() {
            return uOutTimeRepairId;
        }

        public void setuOutTimeRepairId(StringBuffer uOutTimeRepairId) {
            this.uOutTimeRepairId = uOutTimeRepairId;
        }

        public String getOntimeRate() {
            return ontimeRate;
        }

        public void setOntimeRate(String ontimeRate) {
            this.ontimeRate = ontimeRate;
        }

        public String getFinishRate() {
            return finishRate;
        }

        public void setFinishRate(String finishRate) {
            this.finishRate = finishRate;
        }

        public String getAvgRate() {
            return avgRate;
        }

        public void setAvgRate(String avgRate) {
            this.avgRate = avgRate;
        }

        public Integer getRealAudit() {
            return realAudit;
        }

        public void setRealAudit(Integer realAudit) {
            this.realAudit = realAudit;
        }

        public Long getAuditTotalTime() {
            return auditTotalTime;
        }

        public void setAuditTotalTime(Long auditTotalTime) {
            this.auditTotalTime = auditTotalTime;
        }

    }

    /*
     *  标识类，用于查询明细
     */
    public class Identity {
        private String              repairId;      //以逗号拼接起来的退换货id字符串
        private Long                curTime;       //当前时间，用于计算超期
        private Map<String, Object> countResultMap; //统计结果

        public String getRepairId() {
            return repairId;
        }

        public void setRepairId(String repairId) {
            this.repairId = repairId;
        }

        public Long getCurTime() {
            return curTime;
        }

        public void setCurTime(Long curTime) {
            this.curTime = curTime;
        }

        public Map<String, Object> getCountResultMap() {
            return countResultMap;
        }

        public void setCountResultMap(Map<String, Object> countResultMap) {
            this.countResultMap = countResultMap;
        }

    }

    private static class ProductCates {
        private Integer            id;
        private String             text;
        private String             iconCls;
        private String             state;
        private List<ProductCates> children;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getIconCls() {
            return iconCls;
        }

        public void setIconCls(String iconCls) {
            this.iconCls = iconCls;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public List<ProductCates> getChildren() {
            return children;
        }

        public void addChildren(ProductCates p) {
            if (children == null) {
                children = new ArrayList<ProductCates>();
            }
            for (ProductCates child : children) {
                if (child.id.intValue() == p.getId().intValue()) {
                    return;
                }
            }
            children.add(p);
        }

    }

    /**
     * 获取当前登录的用户
     */
    private String getUserName() {
        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        return (String) attr.getRequest().getSession().getAttribute("userName");
    }
}
