package com.haier.svc.api.controller.report_data;


import com.haier.common.PagerInfo;
import com.haier.shop.model.OrderWorkflowRegion;
import com.haier.shop.model.PersonTradeCfg;
import com.haier.shop.service.*;
import com.haier.stock.service.StockInvChannel2OrderSourceService;
import com.haier.svc.api.controller.util.ExchangeUtil;
import com.haier.svc.api.controller.util.WebUtil;
import com.haier.svc.api.controller.util.date.DateFormatUtil;
import jxl.Workbook;
import jxl.format.PaperSize;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.Boolean;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "orderclosehistory")
public class OrderCloseHistoryOrderOntimeRateReverseReportController {

    @Autowired
    private ShopOrderWorkflowsService orderWorkFlowModel;
    @Autowired
    private ReverseReportService reverseReportService;
    @Autowired
    private ShopPersonTradeCfgService shopPersonTradeCfgService;
    @Autowired
    private ShopOrderWorkflowsRunTimeService shopOrderWorkflowsRunTimeService;
    @Autowired
    private StockInvChannel2OrderSourceService stockInvChannel2OrderSourceService;
    private Map<String, Identity>          repairIdCache    = new HashMap<String, Identity>();
    ThreadLocal<SimpleDateFormat> formatAll        = new ThreadLocal<SimpleDateFormat>();
    ThreadLocal<DecimalFormat>    decimalFormatAll = new ThreadLocal<DecimalFormat>();
    private Map<String, Identity>          countResultCache = new HashMap<String, Identity>();
    //缓存过期时间-单位：分钟
    private int                            cacheTimeOut     = 5;

    private static org.apache.log4j.Logger log    = org.apache.log4j.LogManager
            .getLogger(OrderCloseHistoryOrderOntimeRateReverseReportController.class);


    @RequestMapping(value = { "/getKpiOrderOntimeRateReverseReportList.html" }, method = { RequestMethod.GET })
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
                                         @RequestParam(required = false) String timeoutType,
                                         @RequestParam(required = false) String nodeType,
                                         @RequestParam(required = false) String channel,
                                         @RequestParam(required = false) String tradeComissorType) {

        try {
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");
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
            paramMap.put("nodeTypeQuery", nodeType_Query(nodeType));
            //TODO ---------------------修复区域不显示-------------------------------------
            List<OrderWorkflowRegion> regionList = new ArrayList<OrderWorkflowRegion>();
            List<PersonTradeCfg> ptc = new ArrayList<PersonTradeCfg>();
            //----------------------------------------------------------------
            CountEntity totalCountEntity = new CountEntity();//每次查询结果合计
            Map<String, CountEntity> countMap = new HashMap<String, CountEntity>();

            //增加网单号筛选
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            if (orderSnFlag) {
                list = reverseReportService.getOntimeRateReverseByOrderSn(paramMap);
            } else {
                list = reverseReportService.getOntimeRateReverse(paramMap);
            }
            for (Map<String, Object> map : list) {
                //增加网单号筛选
                if (orderSnFlag) {
                    countOntime(map, totalCountEntity);
                    continue;
                }
                Long orderRegion = (Long) map.get("region");
                String storageName = (String) map.get("center_name");
                //按不同维度展示
                if ("PCR".equals(queryGroup)) {
                    countOntime(map, totalCountEntity);
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
                    countOntime(map, itemCountEntiry);
                    countMap.put(key, itemCountEntiry);
                } else if ("A".equals(queryGroup)) {
                    String key = null;

                    if (!"".equals(areaCommissioner) && "".equals(areaTrade)
                            && "".equals(areaSCode)) {
                        key = "areaCommissioner";
                    } else if (!"".equals(areaTrade) && "".equals(areaSCode)) {
                        key = "areaTrade";
                    } else if (!"".equals(areaSCode)) {
                        key = "areaSCode";
                    } else {
                        countOntime(map, totalCountEntity);
                        continue;
                    }

                    if (!"".equals(areaCommissioner) && !"-1".equals(areaCommissioner)
                            && !areaCommissioner.equals(getKeyCommissioner(orderRegion, regionList, ptc,
                            areaCommissioner, channel)))
                        continue;
                    if (!"".equals(areaTrade) && !"-1".equals(areaTrade)
                            && !areaTrade.equals(getKey(orderRegion, "areaTrade", regionList)))
                        continue;
                    if (!"".equals(areaSCode) && !"-1".equals(areaSCode)
                            && !areaSCode.equals(storageName))
                        continue;
                    countOntime(map, totalCountEntity);
                    CountEntity itemCountEntiry = null;
                    if (key.equals("areaCommissioner")) {
                        itemCountEntiry = countMap.get(getKeyCommissioner(orderRegion, regionList,
                                ptc, areaCommissioner, channel)) == null ? new CountEntity()
                                : countMap.get(getKeyCommissioner(orderRegion, regionList, ptc,
                                areaCommissioner, channel));//每一项统计
                    } else if (key.equals("areaSCode")) {
                        itemCountEntiry = countMap.get(storageName) == null ? new CountEntity()
                                : countMap.get(storageName);//每一项统计
                    } else {
                        itemCountEntiry = countMap.get(getKey(orderRegion, key, regionList)) == null
                                ? new CountEntity()
                                : countMap.get(getKey(orderRegion, key, regionList));//每一项统计
                    }
                    countOntime(map, itemCountEntiry);
                    if (key.equals("areaCommissioner")) {
                        countMap.put(getKeyCommissioner(orderRegion, regionList, ptc,
                                areaCommissioner, channel), itemCountEntiry);
                    } else if (key.equals("areaSCode")) {
                        countMap.put(storageName, itemCountEntiry);
                    } else {
                        countMap.put(getKey(orderRegion, key, regionList), itemCountEntiry);
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
                        countOntime(map, totalCountEntity);
                        continue;
                    }
                    //应用中过滤筛选项
                    if (!"".equals(fdArea) && !"-1".equals(fdArea)
                            && !fdArea.equals(getKey(orderRegion, "fdArea", regionList)))
                        continue;
                    if (!"".equals(fdAreaTrade) && !"-1".equals(fdAreaTrade)
                            && !fdAreaTrade.equals(getKey(orderRegion, "fdAreaTrade", regionList)))
                        continue;
                    if (!"".equals(fdAreaSCode) && !"-1".equals(fdAreaSCode)
                            && !fdAreaSCode.equals(storageName))
                        continue;
                    countOntime(map, totalCountEntity);
                    CountEntity itemCountEntiry = null;
                    if (key.equals("fdAreaSCode")) {
                        itemCountEntiry = countMap.get(storageName) == null ? new CountEntity()
                                : countMap.get(storageName);
                    } else {
                        itemCountEntiry = countMap.get(getKey(orderRegion, key, regionList)) == null
                                ? new CountEntity()
                                : countMap.get(getKey(orderRegion, key, regionList));
                    }
                    countOntime(map, itemCountEntiry);
                    if (key.equals("fdAreaSCode")) {
                        countMap.put(storageName, itemCountEntiry);
                    } else {
                        countMap.put(getKey(orderRegion, key, regionList), itemCountEntiry);
                    }
                }
            }
            modelMap.put("totalCountEntity", totalCountEntity);
            modelMap.put("countMap", countMap);
            //历史数据
            modelMap.put("queryHistory", "true");
            //是否是网单号查询
            modelMap.put("orderSnFlag", orderSnFlag);

            this.countRate(totalCountEntity);
            if (null != countMap && !countMap.isEmpty()) {
                for (Map.Entry<String, CountEntity> entry : countMap.entrySet()) {
                    this.countRate(entry.getValue());
                }
            }

            //超过30分钟失效
            Object[] keySet = countResultCache.keySet().toArray();
            for (Object key : keySet) {
                if (System.currentTimeMillis()
                        - countResultCache.get(key).getCurTime() > cacheTimeOut * 60 * 1000)
                    countResultCache.remove(key);
            }
            //将每次明细查询结果的map缓存起来
            Identity identity = new Identity();
            identity.setCountResultMap(modelMap);
            identity.setCurTime(System.currentTimeMillis());
            countResultCache.put(WebUtil.currentUserId(request) + "-" + nodeType, identity);
        } catch (Exception e) {
            log.error("[order][orderworkflow]获取历史逆向及时率网单列表时发生未知错误", e);
        }
        return "report_data/orderCloseOrderOntimeRateReverseReportList";
    }


    @RequestMapping(value = { "/exportKpiOrderOntimeRateReverseReport.html" }, method = { RequestMethod.GET })
    void exportOrderOntimeRateReverseReport(HttpServletRequest request,
                                            HttpServletResponse response,
                                            @RequestParam(required = true) String nodeType) throws Exception {
        OutputStream os = response.getOutputStream();
        try {
            response.reset();
            response.setHeader("Content-Disposition",
                    "attachment; filename=OrderWorkFlow-HistoryOrderOntimeReverse("
                            + dateToStr(new Date()) + ").xls");
            response.setContentType("application/octet-stream; charset=utf-8");
            WritableWorkbook book = Workbook.createWorkbook(os);// 创建可以写的book文件对象
            WritableSheet sheet = book.createSheet("订单全流程-历史及时率报表", 0);// 在book3.xls中创建一个sheet,名称为'qy',从第一列开始插入
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

    @RequestMapping(value = { "/getKpiOrderOntimeRateReverseReportDetailList.html" }, method = { RequestMethod.POST })
    String getOrderOntimeRateReverseReportDetailList(HttpServletRequest request,
                                                     HttpServletResponse response,
                                                     Map<String, Object> modelMap,
                                                     @RequestParam(required = true) String repairId,
                                                     @RequestParam(required = false) String cOrderSn,
                                                     @RequestParam(required = true) String nodeType,
                                                     @RequestParam(required = false) Integer timeoutType,
                                                     @RequestParam(required = false) Boolean orderSnFlag,
                                                     @RequestParam(required = true) String startDate,
                                                     @RequestParam(required = true) String endDate,
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
            startDate = startDate.replace("-","");
            endDate = endDate.replace("-","");
            //将每次明细查询结果的网单号缓存起来
            Identity identity = new Identity();
            identity.setRepairId(repairId);
            identity.setCurTime(System.currentTimeMillis());
            repairIdCache.put(WebUtil.currentUserId(request) + "-" + nodeType, identity);
            PagerInfo pager = null;
            if (pageIndex != null && pageIndex > 0 && pageSize != null && pageSize > 0)
                pager = new PagerInfo(pageSize, pageIndex);
            List<Map<String, Object>> list = this.getOntimeRateReverseDetailList(
                    repairId, cOrderSn, nodeType, nodeType_Query(nodeType), timeoutType, orderSnFlag,
                    startDate, endDate, pager);
            addPagerParam(modelMap, pager);
            modelMap.put("orderOntimeRateReportDetailList", list);
            //历史查询
            modelMap.put("queryHistory", "true");
            //是否是网单号查询
            modelMap.put("orderSnFlag", orderSnFlag);
            //超时类型
            modelMap.put("timeoutType", timeoutType);
            modelMap.put("startDate", startDate);
            modelMap.put("endDate", endDate);
            modelMap.put("sign",sign);
            modelMap.put("repairId", repairId);
        } catch (Exception e) {
            log.error("[history][orderworkflow]获取及时率网单明细列表时发生未知错误", e);
        }
        return "report_data/orderCloseOrderOntimeRateReverseReportDetailList";
    }

    @RequestMapping(value = { "/exportKpiOrderOntimeRateReverseReportDetail.html" }, method = { RequestMethod.GET })
    void exportOrderOntimeRateReverseReportDetail(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  @RequestParam(required = false) String startDate,
                                                  @RequestParam(required = false) String endDate,
                                                  @RequestParam(required = false) Integer timeoutType,
                                                  @RequestParam(required = false) Boolean orderSnFlag,
                                                  @RequestParam(required = true) String nodeType) throws Exception {
        OutputStream os = response.getOutputStream();
        try {
            response.reset();
            response.setHeader("Content-Disposition",
                    "attachment; filename=OrderWorkFlow-HistoryOrderOntimeReverseDetail("
                            + dateToStr(new Date()) + ").xls");
            response.setContentType("application/octet-stream; charset=utf-8");
            WritableWorkbook book = Workbook.createWorkbook(os);// 创建可以写的book文件对象
            WritableSheet sheet = book.createSheet("订单全流程-历史及时率报表", 0);// 在book3.xls中创建一个sheet,名称为'qy',从第一列开始插入
            sheet.getSettings().setPaperSize(PaperSize.A4);//设置纸张大小
            sheet.getSettings().setFitHeight(297);
            sheet.getSettings().setFitWidth(21);
            sheet.getSettings().setHorizontalCentre(true);
            if (repairIdCache.get(WebUtil.currentUserId(request) + "-" + nodeType) == null)
                throw new Exception("查询结果已失效，请关闭该页面后重新尝试");

            List<Map<String, Object>> list = this.getOntimeRateReverseDetailList(
                    repairIdCache.get(WebUtil.currentUserId(request) + "-" + nodeType).getRepairId(),
                    null, nodeType, nodeType_Query(nodeType), timeoutType, orderSnFlag, startDate,
                    endDate, null);
            setExcelDetail(sheet, list);
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

    private void setExcelDetail(WritableSheet sheet,
                                List<Map<String, Object>> list) throws Exception {
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
        sheet.addCell(new Label(0, 0, "订单全流程-历史逆向及时率报表明细", headerFormat));
        sheet.mergeCells(0, 0, 28, 0);
        temp++;
        String[] headers = new String[] { "WD单号", "区域", "中心", "工贸", "转运库位", "网单状态", "商品分类", "顶级品类",
                "型号", "数量", "金额", "网单金额", "退款申请金额", "是否金额差异", "退换货处理状态",
                "退换货类型", "退款状态", "货物状态", "发票状态", "检验结果", "质检结果", "申请退款时间",
                "已退款时间", "审核完成时间", "终止完成时间", "推送HP时间", "HP回传时间",
                "LES开提单时间", "LES入库时间", "发票作废时间", "订单来源", "网点", "网单类型",
                "取消关闭时间", "超时天数", "应完成时间", "实际完成时间", "统计时间", "TB单号" };
        for (int col = 0; col < headers.length; col++) {
            sheet.setColumnView(col, columnWidth);
            sheet.addCell(new Label(col, temp, headers[col], headerFormat));
        }
        temp++;
        for (Map<String, Object> map : list) {
            String[] columnNames = new String[] { "cOrderSn", "area", "sCode", "trade", "tsCode",
                    "status", "cateId", "superCateId", "productName",
                    "number", "price", "opMoney", "refMoney",
                    "isDiffMoney", "handleStatus", "typeActual",
                    "paymentStatus", "storageStatus", "state",
                    "checkResult", "quality", "applyTime",
                    "paymentTime", "handleTime", "endTime",
                    "hpFirstAddTime", "hpTime", "lesTime",
                    "lesOutPingTime", "invalidTime", "channelName",
                    "netPointName", "stockType", "finishTime",
                    "outTime", "mustFinishTime", "realFinishTime",
                    "date", "tb_order_sn" };
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
     * 获取及时率报表逆向明细数据列表
     */
    public List<Map<String, Object>> getOntimeRateReverseDetailList(String repairId,
                                                                    String cOrderSn,
                                                                    String nodeType,
                                                                    String nodeTypeQuery,
                                                                    Integer timeoutType,
                                                                    Boolean orderSnFlag,
                                                                    String startDate,
                                                                    String endDate,
                                                                    PagerInfo pager) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        List<OrderWorkflowRegion> tradeList = new ArrayList<OrderWorkflowRegion>();
        List<Map<String, String>> storageList = new ArrayList<Map<String, String>>();

        paramMap.put("repairIds", repairId.split(","));
        //增加网单查询
        paramMap.put("cOrderSn", "%" + (cOrderSn == null ? "" : cOrderSn) + "%");
        paramMap.put("nodeType", nodeType);
        paramMap.put("nodeTypeQuery", nodeTypeQuery);
        //合计条件过滤
        //timeoutType合计时不作为检索条件 传入为-1 不作为检索条件 -2表示超时类型
        paramMap.put("timeoutType", timeoutType);
        paramMap.put("orderSnFlag", orderSnFlag != null && orderSnFlag == true ? "true" : "");
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        if (pager != null) {
            paramMap.put("start", pager.getStart());
            paramMap.put("size", pager.getPageSize());
            pager.setRowsCount(reverseReportService.getOntimeRateReverseDetailCount(paramMap));
        } else {
            paramMap.put("size", 0);
        }
        List<Map<String, Object>> list = reverseReportService.getOntimeRateReverseDetail(paramMap);
        if (list == null || list.isEmpty()) {
            return list;
        }
        List<Map<String, Object>> channels = stockInvChannel2OrderSourceService.getChannelNames(); // 渠道名称 add by zhaozj on 2013-12-25
        Map<String, Map<String, Object>> productCateMap = getProductCate();
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
            resultMap.put("area", getAreaTrade((Long) resultMap.get("region"),tradeList, "1"));
            //翻译库位
            resultMap.put("sCode", getStorageName((String) resultMap.get("sCode"),storageList));
            //翻译转运库位
            resultMap.put("tsCode", getStorageName((String) resultMap.get("tsCode"),storageList));
            //翻译工贸
            resultMap.put("trade", getAreaTrade((Long) resultMap.get("region"), tradeList,"2"));
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
            //翻译hp返回结果
            //resultMap.put("checkResult", formatCheckResult((Integer) resultMap.get("checkResult")));
            //翻译检验结果
            resultMap.put("checkResult", formatCheckResult((Integer) resultMap.get("checkResult")));
            //翻译质检结果
            resultMap.put("quality", formatQuality((Integer) resultMap.get("quality")));

            //格式化时间
            resultMap.put("applyTime", formatTime(resultMap.get("applyTime")));
            resultMap.put("handleTime", formatTime(resultMap.get("handleTime")));
            resultMap.put("paymentTime", formatTime(resultMap.get("paymentTime")));//------新加
            resultMap.put("endTime", formatTime(resultMap.get("endTime")));
            resultMap.put("hpFirstAddTime", formatTime(resultMap.get("hpFirstAddTime")));
            resultMap.put("hpTime", formatTime(resultMap.get("hpTime")));
            resultMap.put("lesTime", formatTime(resultMap.get("lesTime")));
            resultMap.put("lesOutPingTime", formatTime(resultMap.get("lesOutPingTime")));
            resultMap.put("invalidTime", formatTime(resultMap.get("invalidTime")));
            resultMap.put("finishTime", formatTime(finishTime));
            resultMap.put("mustFinishTime",
                    DateFormatUtil.format(((Timestamp) resultMap.get("mustFinishTime"))));
            resultMap.put("realFinishTime",
                    DateFormatUtil.format(((Timestamp) resultMap.get("realFinishTime"))));
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
            //顶级品类
            resultMap.put("superCateId", getSuperCateId(resultMap.get("cateId").toString(), productCateMap));
            //品类
            resultMap.put("cateId",
                    productCateMap.get(String.valueOf(resultMap.get("cateId"))) == null ? ""
                            : productCateMap.get(String.valueOf(resultMap.get("cateId"))).get("cateName"));
        }
        return list;
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

    private String formatQuality(Integer value) {
        if (value == null || value == 0 || value == 4)
            return "";
        else if (value == 1)
            return "未开箱正品";
        else if (value == 2)
            return "已开箱正品";
        else if (value == 3)
            return "不良品";
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
        return DateFormatUtil.format(date);
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
            ontimeTitle = "历史退换货审核及时率";
        } else if ("hp".equals(nodeType)) {
            ontimeTitle = "历史退换货HP质检及时率";
        } else if ("les".equals(nodeType)) {
            ontimeTitle = "历史退换货LES入库及时率";
        } else if ("invoice".equals(nodeType)) {
            ontimeTitle = "历史退换货发票冲红及时率";
        } else if ("orderclose".equals(nodeType)) {
            ontimeTitle = "历史退换货订单关闭及时率";
        } else if ("refund".equals(nodeType)) {
            ontimeTitle = "历史退换货退款及时率";
        } else if ("22storehouse".equals(nodeType)) {
            ontimeTitle = "退换货22转库及时率";
        }
        sheet.setColumnView(0, columnWidth);
        sheet.setColumnView(1, firstColumnWidth);
        sheet.addCell(new Label(1, temp, "历史及时率", format1));
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
        Label l_title = new Label(0, 0, "订单全流程-历史逆向及时率报表", format);
        sheet.setColumnView(0, 25);
        sheet.addCell(l_title);
        sheet.mergeCells(0, 0, 7, 0);
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
     * 根据行政区id获得行政区名称-判断人员
     * @param region
     * @param ptclist
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

    /**
     * 检查是否已经存入网单号
     * @param repairId 拼接的退货单号
     * @param map 查询记录
     * @return true 不存在 false 存在
     */
    private Boolean notExists(StringBuffer repairId, Map<String, Object> map) {
        if (repairId.indexOf(map.get("ors_id").toString() + ",") < 0)
            return true;
        return false;
    }
    /**
     * 统计及时率
     */
    private void countOntime(Map<String, Object> map, CountEntity countEntity) throws Exception {
        Integer num = (Integer) map.get("count");//申请退货台数
        countEntity.total = countEntity.total + num;//应
        String timeoutType = map.get("timeout_type").toString();
        countEntity.totalRepairId = notExists(countEntity.totalRepairId, map)
                ? countEntity.totalRepairId.append(map.get("ors_id").toString()).append(",")
                : countEntity.totalRepairId;
        countEntity.onTime = "0".equals(timeoutType) ? countEntity.onTime + num
                : countEntity.onTime;//实
        countEntity.onTimeRepairId = notExists(countEntity.onTimeRepairId, map)
                && "0".equals(timeoutType)
                ? countEntity.onTimeRepairId
                .append(map.get("ors_id").toString()).append(",")
                : countEntity.onTimeRepairId;
        countEntity.outTime = countEntity.total - countEntity.onTime;//差
        countEntity.outTimeRepairId = notExists(countEntity.outTimeRepairId, map)
                && !"0".equals(timeoutType)
                ? countEntity.outTimeRepairId
                .append(map.get("ors_id").toString()).append(",")
                : countEntity.outTimeRepairId;
        countEntity.cOutTime = "1".equals(timeoutType) ? countEntity.cOutTime + num
                : countEntity.cOutTime;
        countEntity.cOutTimeRepairId = notExists(countEntity.cOutTimeRepairId, map)
                && "1".equals(timeoutType)
                ? countEntity.cOutTimeRepairId
                .append(map.get("ors_id").toString()).append(",")
                : countEntity.cOutTimeRepairId;
        countEntity.uOutTime = "2".equals(timeoutType) ? countEntity.uOutTime + num
                : countEntity.uOutTime;
        countEntity.uOutTimeRepairId = notExists(countEntity.uOutTimeRepairId, map)
                && "2".equals(timeoutType)
                ? countEntity.uOutTimeRepairId
                .append(map.get("ors_id").toString()).append(",")
                : countEntity.uOutTimeRepairId;
        countEntity.uOnTime = "3".equals(timeoutType) ? countEntity.uOnTime + num
                : countEntity.uOnTime;
        countEntity.uOnTimeRepairId = notExists(countEntity.uOnTimeRepairId, map)
                && "3".equals(timeoutType)
                ? countEntity.uOnTimeRepairId
                .append(map.get("ors_id").toString()).append(",")
                : countEntity.uOnTimeRepairId;
    }


    /*
    *  标识类，用于查询明细
    */
    public class Identity {
        private String              repairId;       //以逗号拼接起来的退换货id字符串
        private Long                curTime;        //当前时间，用于计算超期
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

    /**
     * 转化nodeType  nodeTypeQuery
     * @param nodeType
     * @return str
     */
    private String nodeType_Query(String nodeType) {
        if (nodeType.equals("audit")) {
            //退换货订单审核
            nodeType = "audit";
        } else if (nodeType.equals("hp")) {
            // 退换货HP质检(一次质检)
            nodeType = "checkquality";
        } else if (nodeType.equals("les")) {
            //退换货LES入库
            nodeType = "instock";
        } else if (nodeType.equals("invoice")) {
            //退换货发票冲红
            nodeType = "invoice";
        } else if (nodeType.equals("orderclose")) {
            //退换货订单闭环
            nodeType = "orderclose";
        } else if (nodeType.equals("refund")) {
            //退换货订单闭环
            nodeType = "refund";
        } else if (nodeType.equals("22storehouse")) {
            //退换货22库
            nodeType = "store22";
        }
        return nodeType;
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

}
