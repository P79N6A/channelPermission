package com.haier.svc.api.controller.settleCenter;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.invoice.service.SettlementInvoiceService;
import com.haier.shop.model.SettlementInvoiceData;
import com.haier.svc.api.controller.excel.ExcelImport;
import com.haier.svc.api.controller.excel.ExcelInfo;
import com.haier.svc.api.controller.settleCenter.excel.ExportExcelSettlementInvoiceTemplate;


@Controller
@RequestMapping(value = "/settlementInvoice")
public class SettlementInvoiceController {
    private static Logger     log = LogManager.getLogger(SettlementInvoiceController.class);

    @Resource
    private SettlementInvoiceService settlementInvoiceService;

    @GetMapping("page")
    public String query(){
        return "settleCenter/settlementInvoice";
    }


    @RequestMapping(value ="getList")
    @ResponseBody
    public String getList(@RequestParam(required = true) String yearmonth,
                          @RequestParam(required = false) String sellPeople,
                          @RequestParam(required = false) String settlementType,
                          @RequestParam(required = false) String state,
                          @RequestParam(required = false) String statusType,
                          @RequestParam(required = false) String payTimeMin,
                          @RequestParam(required = false) String payTimeMax,
                          @RequestParam(required = false) String invoiceTimeMin,
                          @RequestParam(required = false) String invoiceTimeMax,
                          @RequestParam(required = false) String source, Integer page, Integer rows) {
        if (page == null) {
            page = 1;
        }
        rows = rows == null ? 50 : rows;
        PagerInfo pager = new PagerInfo(rows, page);

        if (payTimeMin != null && !payTimeMin.equals("")) {
            payTimeMin = (timeStringToDate(payTimeMin).getTime() / 1000) + "";
        }
        if (payTimeMax != null && !payTimeMax.equals("")) {
            payTimeMax = (timeStringToDate(payTimeMax).getTime() / 1000) + "";
        }
        if (invoiceTimeMin != null && !invoiceTimeMin.equals("")) {
            invoiceTimeMin = (timeStringToDate(invoiceTimeMin).getTime() / 1000) + "";
        }
        if (invoiceTimeMax != null && !invoiceTimeMax.equals("")) {
            invoiceTimeMax = (timeStringToDate(invoiceTimeMax).getTime() / 1000) + "";
        }

        String year = "";
        String month = "";
        try {
            if (yearmonth != null && !yearmonth.equals("")) {
                year = yearmonth.substring(0, 4);
                month = Integer.parseInt(yearmonth.substring(5, 7)) + "";
            }
        } catch (Exception e) {
            log.error("时间解析异常：",e );
        }

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("year", year);
        paramMap.put("month", month);
        paramMap.put("sellPeople", sellPeople);
        paramMap.put("settlementType", settlementType);
        paramMap.put("state", state);
        paramMap.put("statusType", statusType);
        paramMap.put("payTimeMin", payTimeMin);
        paramMap.put("payTimeMax", payTimeMax);
        paramMap.put("invoiceTimeMin", invoiceTimeMin);
        paramMap.put("invoiceTimeMax", invoiceTimeMax);
        paramMap.put("source", source);


        JSONObject jsonObject = settlementInvoiceService.paging(paramMap, pager);
        return jsonObject.toString();
    }





    @ResponseBody
    @RequestMapping(value="/export",method=RequestMethod.GET)
    public void exportLPTN(@RequestParam(required = false) String yearmonth,
                           @RequestParam(required = false) String sellPeople,
                           @RequestParam(required = false) String settlementType,
                           @RequestParam(required = false) String state,
                           @RequestParam(required = false) String statusType,
                           @RequestParam(required = false) String payTimeMin,
                           @RequestParam(required = false) String payTimeMax,
                           @RequestParam(required = false) String invoiceTimeMin,
                           @RequestParam(required = false) String invoiceTimeMax, HttpServletRequest request, HttpServletResponse response) throws IOException {


        if (payTimeMin != null && !payTimeMin.equals("")) {
            payTimeMin = (timeStringToDate(payTimeMin).getTime() / 1000) + "";
        }
        if (payTimeMax != null && !payTimeMax.equals("")) {
            payTimeMax = (timeStringToDate(payTimeMax).getTime() / 1000) + "";
        }
        if (invoiceTimeMin != null && !invoiceTimeMin.equals("")) {
            invoiceTimeMin = (timeStringToDate(invoiceTimeMin).getTime() / 1000) + "";
        }
        if (invoiceTimeMax != null && !invoiceTimeMax.equals("")) {
            invoiceTimeMax = (timeStringToDate(invoiceTimeMax).getTime() / 1000) + "";
        }

        String year = "";
        String month = "";
        try {
            if (yearmonth != null && !yearmonth.equals("")) {
                year = yearmonth.substring(0, 4);
                month = Integer.parseInt(yearmonth.substring(5, 7)) + "";
            }
        } catch (Exception e) {
            log.error("时间解析异常：",e );
        }

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("year", year);
        paramMap.put("month", month);
        paramMap.put("sellPeople", sellPeople);
        paramMap.put("settlementType", settlementType);
        paramMap.put("state", state);
        paramMap.put("statusType", statusType);
        paramMap.put("payTimeMin", payTimeMin);
        paramMap.put("payTimeMax", payTimeMax);
        paramMap.put("invoiceTimeMin", invoiceTimeMin);
        paramMap.put("invoiceTimeMax", invoiceTimeMax);

        String fileName ="结算发票报表.xlsx";

        fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        ExportExcelSettlementInvoiceTemplate exportExcelSettlementInvoiceTemplate = new ExportExcelSettlementInvoiceTemplate();
        exportExcelSettlementInvoiceTemplate.doExport(response,fileName,paramMap);

    }


    @ResponseBody
    @RequestMapping(value = "/importTMFXPointMaintainExcel", method = { RequestMethod.POST })
    public String ImprotExcel(HttpServletRequest request,@RequestParam(value="file")MultipartFile file) {
        ServiceResult<ExcelInfo> result = new ServiceResult<>();
        ServiceResult<String> serviceResult = new ServiceResult<>();
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");

        try {

            result = ExcelImport.transferStreamToExcel(file.getInputStream());
            ExcelInfo excelInfo = result.getResult();
            List<SettlementInvoiceData> settlementInvoiceDataList = new ArrayList<SettlementInvoiceData>();
            serviceResult = settlementInvoiceService.checkAndImport(excelInfo.getBobyInfo(),userName,settlementInvoiceDataList);


            log.debug(result);
        } catch (Exception e) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage("importTMFXPointMaintainExcel导入异常");
            log.error("importTMFXPointMaintainExcel导入异常",e);
        }

        //批量插入数据库

        String ret = null;

        return JsonUtil.toJson(serviceResult);
    }

    @RequestMapping(value = { "/downloadTemplate" })
    void downloadTemplate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        OutputStream os = response.getOutputStream();
        try {
            response.reset();
            response.setHeader("Content-Disposition",
                    "attachment; filename=settlement_invoice_template.xls");
            response.setContentType("application/octet-stream; charset=utf-8");
            String path = request.getSession().getServletContext()
                    .getRealPath("/xls/settlement_invoice_template.xls");
            File file = ResourceUtils.getFile("classpath:xls/settlement_invoice_template.xls");
            os.write(FileUtils.readFileToByteArray(file));
            os.flush();
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }


    @PostMapping("audit")
    @ResponseBody
    public Object audit(HttpServletRequest request, String ids, String audit,String state) {
        ServiceResult<JSONObject> result = new ServiceResult<>();
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");

        if (audit==null){
            result.setError("error","审核类型不能为空");
            return result;
        }
        String []idArray = ids.split(",");

        try {
            if ("B".equals(audit)&&("-1".equals(state)||"2".equals(state))){
                //业务审核
                for (String id:idArray){
                    SettlementInvoiceData settlementInvoiceData = new SettlementInvoiceData();
                    settlementInvoiceData.setId(Long.parseLong(id));
                    settlementInvoiceData.setState(Integer.parseInt(state));
                    settlementInvoiceData.setBusauditorpeople(userName);
                    settlementInvoiceData.setBusauditortime(new Date());
                    settlementInvoiceService.update(settlementInvoiceData);
                }
            }
            if ("F".equals(audit)&&("-2".equals(state)||"3".equals(state))){
                //财务审核
                for (String id:idArray){
                    SettlementInvoiceData settlementInvoiceData = new SettlementInvoiceData();
                    settlementInvoiceData.setId(Long.parseLong(id));
                    settlementInvoiceData.setState(Integer.parseInt(state));
                    settlementInvoiceData.setFinauditorpeople(userName);
                    settlementInvoiceData.setFinauditortime(new Date());
                    settlementInvoiceService.update(settlementInvoiceData);
                }
            }
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("操作失败");
        }

        return JsonUtil.toJson(result);
    }


    /**
     * 处理时间变成时间戳
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date timeStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (StringUtils.isEmpty(time)) {
            return null;
        }
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            return null;
        }
    }
    
    @PostMapping("getSelecttion")
    @ResponseBody
    public Object getSelecttion() {
    	 List<String> list = settlementInvoiceService.getSelections();
    	 JSONArray json = new JSONArray();
         for(String s : list){
             JSONObject jo = new JSONObject();
             jo.put("source",s);
             json.add(jo);
         }
    	 return json.toString();
    }
}
