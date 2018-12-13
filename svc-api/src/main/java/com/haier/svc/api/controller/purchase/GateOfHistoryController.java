package com.haier.svc.api.controller.purchase;

import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.model.GateOfHistoryLimitItem;
import com.haier.purchase.data.model.GateOfLimitItem;
import com.haier.svc.api.controller.util.CommUtil;
import com.haier.svc.api.controller.util.ExportData;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.controller.util.date.DateCal;
import com.haier.svc.api.service.CommPurchase;
import com.haier.svc.service.GateService;
import org.apache.log4j.LogManager;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "gatehistory")
public class GateOfHistoryController {
    @Autowired
    CommPurchase commPurchase;
    @Resource(name = "gateService")
    private GateService gateService;
    private final static org.apache.log4j.Logger logger = LogManager.getLogger(GateOfHistoryController.class);

    /**
     * 额度闸口历史查询页面调转
     *
     * @param request
     * @param modelMap
     *            调转页面数据存放map
     * @return
     */
    @RequestMapping(value = { "gotoGateOfHistoryLimit" }, method = { RequestMethod.GET })
    String gotoGateOfHistoryLimit(HttpServletRequest request, Map<String, Object> modelMap) {
        // 当前日期取得
        String currentDate = CommUtil.getCurrentDate("yyyy-MM-dd");
        // 转化成周
        String currentWeek = CommUtil.getWeekOfYear_Sunday(currentDate, null, "1");
        modelMap.put("currentweek", currentWeek);
        return "purchase/gateOfHistoryLimit";
    }

    /**
     * 额度闸口历史查询页面中为datagrid加载值
     *
     * @param request
     * @param response
     * @param modelMap
     * @throws IOException
     */
    @RequestMapping(value = { "findGateOfHistoryLimit" }, method = { RequestMethod.POST })
    public void findGateOfHistoryLimit(HttpServletRequest request, HttpServletResponse response,
                                       @RequestParam(required = false) String report_year_week,
                                       Map<String, Object> modelMap) throws IOException {
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html; charset=utf-8");
            // 设置检索参数
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("report_year_week", report_year_week);
            // 在DB中检索详细信息
            Map<String, Object> retMap = new HashMap<String, Object>();
            retMap.put("rows", getGateOfHistoryLimitData(params));
            // 检索结果向前台传递
            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("错误代码：", e);

        }
    }

    /**
     * 额度闸口历史查询数据处理（将不同DB中检索出的数据组织在一起）
     *
     * @param Map<String,Object> params 检索条件中的参数
     * @return result 需要显示或导出数据结果
     */
    public List<GateOfHistoryLimitItem> getGateOfHistoryLimitData(Map<String, Object> params) {
        // 渠道数据存入HashMap
        Map<String, String> invStockChannelMap = new HashMap<String, String>();
        // 取得渠道
        commPurchase.getChannelMap(invStockChannelMap);
        // 调用业务Service
        ServiceResult<List<GateOfHistoryLimitItem>> result = gateService
                .findGateOfHistoryLimit(params);
        // 获得条数
        List<GateOfHistoryLimitItem> limitHistoryList = new ArrayList<GateOfHistoryLimitItem>();
        // 要显示的结果集
        List<GateOfHistoryLimitItem> limitHistoryResult = new ArrayList<GateOfHistoryLimitItem>();
        // 重新组织DB中检索的数据
        if (result.getSuccess() && result.getResult() != null) {
            limitHistoryList = result.getResult();
            String tempCategery = "";
            // 指标合计
            BigDecimal target_sum = new BigDecimal(0);
            // 额度合计
            BigDecimal limit_sum = new BigDecimal(0);
            // 拆借合计
            BigDecimal loan_sum = new BigDecimal(0);
            // 总库存合计
            BigDecimal total_sum = new BigDecimal(0);
            // 在途合计
            BigDecimal on_way_sum = new BigDecimal(0);
            // 本周已用合计
            BigDecimal used_sum = new BigDecimal(0);
            // 可用额度合计
            BigDecimal available_sum = new BigDecimal(0);
            GateOfHistoryLimitItem gateOfHistoryLimitItem = null;
            for (Iterator<GateOfHistoryLimitItem> gateOfHistoryLimitList = limitHistoryList
                    .iterator(); gateOfHistoryLimitList.hasNext();) {
                gateOfHistoryLimitItem = gateOfHistoryLimitList.next();
                // 为渠道名称赋值
                gateOfHistoryLimitItem.setEd_channel_name(invStockChannelMap
                        .get(gateOfHistoryLimitItem.getEd_channel_id()));
                if (tempCategery.equals(gateOfHistoryLimitItem.getCategory_id())
                        || tempCategery.equals("")) {
                    // 同品类中计算以上数据的合计值
                    target_sum = target_sum.add(gateOfHistoryLimitItem.getTarget_num());
                    limit_sum = limit_sum.add(gateOfHistoryLimitItem.getLimit_num());
                    loan_sum = loan_sum.add(gateOfHistoryLimitItem.getLoan_num());
                    total_sum = total_sum.add(gateOfHistoryLimitItem.getTotal_num());
                    on_way_sum = on_way_sum.add(gateOfHistoryLimitItem.getOn_way_num());
                    used_sum = used_sum.add(gateOfHistoryLimitItem.getUsed_num());
                    available_sum = available_sum.add(gateOfHistoryLimitItem.getAvailable_num());
                    tempCategery = gateOfHistoryLimitItem.getCategory_id();
                    // 将节点存入结果集list
                    limitHistoryResult.add(gateOfHistoryLimitItem);
                } else {
                    // 品类改变，为新增合计节点赋值
                    GateOfHistoryLimitItem tempItem = new GateOfHistoryLimitItem();
                    tempItem.setCategory_id("");
                    tempItem.setEd_channel_name("合计");
                    tempItem.setTarget_num(target_sum);
                    tempItem.setLimit_num(limit_sum);
                    tempItem.setLoan_num(loan_sum);
                    tempItem.setTotal_num(total_sum);
                    tempItem.setOn_way_num(on_way_sum);
                    tempItem.setUsed_num(used_sum);
                    tempItem.setAvailable_num(available_sum);
                    // 将处理完毕的合计节点存入list
                    limitHistoryResult.add(tempItem);

                    // 将节点存入结果集list
                    limitHistoryResult.add(gateOfHistoryLimitItem);
                    // 合计清零
                    tempCategery = gateOfHistoryLimitItem.getCategory_id();
                    target_sum = gateOfHistoryLimitItem.getTarget_num();
                    limit_sum = gateOfHistoryLimitItem.getLimit_num();
                    loan_sum = gateOfHistoryLimitItem.getLoan_num();
                    total_sum = gateOfHistoryLimitItem.getTotal_num();
                    on_way_sum = gateOfHistoryLimitItem.getOn_way_num();
                    used_sum = gateOfHistoryLimitItem.getUsed_num();
                    available_sum = gateOfHistoryLimitItem.getAvailable_num();
                }
                if (!gateOfHistoryLimitList.hasNext()) {
                    // 为新增合计节点赋值
                    GateOfHistoryLimitItem tempItem = new GateOfHistoryLimitItem();
                    tempItem.setCategory_id("");
                    tempItem.setEd_channel_name("合计");
                    tempItem.setTarget_num(target_sum);
                    tempItem.setLimit_num(limit_sum);
                    tempItem.setLoan_num(loan_sum);
                    tempItem.setTotal_num(total_sum);
                    tempItem.setOn_way_num(on_way_sum);
                    tempItem.setUsed_num(used_sum);
                    tempItem.setAvailable_num(available_sum);
                    // 将处理完毕的合计节点存入list
                    limitHistoryResult.add(tempItem);
                }
            }
        } else {
            logger.error("额度闸口历史查询失败！");
        }
        return limitHistoryResult;
    }


    /**
     * 预测备料通过日期获取当前周
     * @param  （YYYY-MM-DD）
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

    /**
     * 额度闸口历史查询全部导出
     *
     * @param
     *
     * @param response
     * @return 方法执行完毕调用的画面
     */
    @RequestMapping(value = { "/exportGateOfHistoryLimit" })
    void exportGateOfHistoryLimit(@RequestParam(required = false) String report_year_week_txt,
                                    Map<String, Object> modelMap, HttpServletRequest request,
                                    HttpServletResponse response) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("report_year_week", report_year_week_txt.replace("年", "").replace("周", ""));

        HSSFWorkbook wb= getDetailsData(params,report_year_week_txt);
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date=new java.util.Date();
        String str=sdf.format(date);
        String fileName = "历史额度查看列表"+str;
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


    public HSSFWorkbook getDetailsData(Map<String, Object> params,String report_year_week_txt) {

        List<GateOfHistoryLimitItem> list = getGateOfHistoryLimitData(params);

        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
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
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("历史额度查看列表");
        int length = ExportData.gateOfHistoryTitle.length;
        for (int i = 0; i <length; i++) {
            sheet.setColumnWidth(i, (int)(21.57*256));
        }
        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
        HSSFRow row0 = sheet.createRow((int) 0);
        row0.createCell(0).setCellValue("额度时间");
        row0.createCell(1).setCellValue(report_year_week_txt);
        HSSFRow row1 = sheet.createRow((int) 1);
        // 设置表头
        for(int i=0;length-1>=i;i++){
            HSSFCell cell = row1.createCell(i);
            cell.setCellValue(ExportData.gateOfHistoryTitle[i]);
            cell.setCellStyle(style);
        }

        //向单元格里添加数据
        for(short i=0;i<list.size();i++){
            HSSFRow row = sheet.createRow(i+2);
            row.createCell(0).setCellValue(i+1);
            row.createCell(1).setCellValue(list.get(i).getCategory_id());
            row.createCell(2).setCellValue(list.get(i).getEd_channel_name());
            row.createCell(3).setCellValue(String.valueOf(list.get(i).getTarget_num()));
            row.createCell(4).setCellValue(String.valueOf(list.get(i).getLimit_num()));
            row.createCell(5).setCellValue(String.valueOf(list.get(i).getTotal_num()));
            row.createCell(6).setCellValue(String.valueOf(list.get(i).getOn_way_num()));
            row.createCell(7).setCellValue(String.valueOf(list.get(i).getLoan_num()));
            row.createCell(8).setCellValue(String.valueOf(list.get(i).getUsed_num()));
            row.createCell(9).setCellValue(String.valueOf(list.get(i).getAvailable_num()));
        }
        return wb;

    }
}
