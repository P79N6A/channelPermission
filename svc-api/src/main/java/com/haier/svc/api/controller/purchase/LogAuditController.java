package com.haier.svc.api.controller.purchase;

import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.model.LogAuditInfo;
import com.haier.svc.api.controller.util.ExportData;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import com.haier.svc.service.LogAuditService;
import org.apache.log4j.LogManager;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/logaudit")
@Controller
public class LogAuditController {
    private final static org.apache.log4j.Logger logger = LogManager.getLogger(LogAuditController.class);
    private static final String                  TYPESTR = "导入,修改,新增,启用,禁用,删除";

    @Resource
    private LogAuditService logAuditService;
    @GetMapping("toPage")
    public String toPage() {
        return "purchase/logQueryList";
    }


    /**
     *  操作日志数据查询
     * @param oper_user_name 操作人
     * @param jude_way_channel 判断方式-渠道
     * @param channel 预测备料准确率-渠道
     * @param gate_way_channel 关闸方式-渠道
     * @param category 品类
     * @param type 操作类型
     * @param log_time_start 操作日开始日
     * @param log_time_end 操作日结束日
     * @param content 操作内容
     * @param rows
     * @param page
     * @param response
     */
    @RequestMapping(value = { "findLogQueryList" }, method = { RequestMethod.POST })
    void findLogQueryList(@RequestParam(required = false) String oper_user_name,
                          @RequestParam(required = false) String jude_way_channel,
                          @RequestParam(required = false) String channel,
                          @RequestParam(required = false) String gate_way_channel,
                          @RequestParam(required = false) String category,
                          @RequestParam(required = false) String type,
                          @RequestParam(required = false) String log_time_start,
                          @RequestParam(required = false) String log_time_end,
                          @RequestParam(required = false) String order_id,
                          @RequestParam(required = false) String content,
                          @RequestParam(required = false) Integer rows,
                          @RequestParam(required = false) Integer page, HttpServletRequest request,
                          HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        try {
            if (rows == null)
                rows = 100;
            if (page == null)
                page = 1;

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("oper_user_name", oper_user_name);
            params.put("jude_way_channel", jude_way_channel);
            params.put("channel", channel);
            params.put("gate_way_channel", gate_way_channel);
            params.put("category", category);
            params.put("type", type);
            params.put("log_time_start", log_time_start);
            params.put("log_time_end", log_time_end);
            params.put("order_id", order_id);
            params.put("content", content);
            params.put("m", (page - 1) * rows);
            params.put("n", rows);

            //调用业务Service
            Map<String, Object> result = logAuditService.queryLogAudit(params);
            response.getWriter().write(JsonUtil.toJson(result));
            response.getWriter().flush();
            response.getWriter().close();

        } catch (IOException e) {
            logger.error("错误代码：", e);
        }
    }

    /**
     * 操作日志-点击全部导出、导出Excel
     * @param oper_user_name 操作人
     * @param jude_way_channel 判断方式-渠道
     * @param channel 预测备料准确率-渠道
     * @param gate_way_channel 关闸方式-渠道
     * @param category 品类
     * @param type 操作类型
     * @param log_time_start 操作日开始日
     * @param log_time_end 操作日结束日
     * @return 方法执行完毕调用的画面
     */
    @RequestMapping(value = { "/exportLogAuditList" })
    void cgoExportAllOrderList(@RequestParam(required = false) String oper_user_name,
                                 @RequestParam(required = false) String jude_way_channel,
                                 @RequestParam(required = false) String channel,
                                 @RequestParam(required = false) String gate_way_channel,
                                 @RequestParam(required = false) String category,
                                 @RequestParam(required = false) String type,
                                 @RequestParam(required = false) String log_time_start,
                                 @RequestParam(required = false) String log_time_end,
                                 @RequestParam(required = false) String order_id,
                                 Map<String, Object> modelMap, HttpServletRequest request,
                                 HttpServletResponse response) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("oper_user_name", oper_user_name.equals("ALL") ? "" : oper_user_name);
        params.put("jude_way_channel", jude_way_channel.equals("ALL") ? "" : jude_way_channel);
        params.put("channel", channel.equals("ALL") ? "" : channel);
        params.put("gate_way_channel", gate_way_channel.equals("ALL") ? "" : gate_way_channel);
        params.put("category", category.equals("全部") ? "" : category);
        params.put("type", type.equals("ALL") ? "" : type);
        params.put("log_time_start", log_time_start);
        params.put("log_time_end", log_time_end);
        params.put("order_id", order_id);

        List<LogAuditInfo> logAuditInfoList = logAuditService.queryLogAuditExcle(params);
        for (LogAuditInfo logInfo : logAuditInfoList) {
            int typeData = logInfo.getType();
            logInfo.setTypeName(TYPESTR.split(",")[typeData]);
        }
        HSSFWorkbook wb= getDetailsData(logAuditInfoList);
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date=new java.util.Date();
        String str=sdf.format(date);
        String fileName = "日志操作-导出"+str;
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


    public HSSFWorkbook getDetailsData(List<LogAuditInfo> list) {

        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("日志操作-导出");
        int length = ExportData.logAuditInfoTitle.length;
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
            cell.setCellValue(ExportData.logAuditInfoTitle[i]);
            cell.setCellStyle(style);
        }

        //向单元格里添加数据
        for(short i=0;i<list.size();i++){
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(list.get(i).getOper_user_name());
            row.createCell(1).setCellValue(list.get(i).getLog_time());
            row.createCell(2).setCellValue(list.get(i).getTypeName());
            row.createCell(3).setCellValue(list.get(i).getContent());
        }
        return wb;

    }
}
