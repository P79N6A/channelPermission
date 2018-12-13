package com.haier.svc.api.controller.invoice;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.haier.afterSale.model.Ustring;
import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.invoice.model.eai.InvoiceEntity;
import com.haier.invoice.model.eai.QueryInvoiceInputType;
import com.haier.invoice.model.einvoice.Invoice;
import com.haier.invoice.service.*;
import com.haier.invoice.util.*;
import com.haier.shop.model.*;
import com.haier.shop.util.InvoiceServiceUtil;
import com.haier.svc.api.controller.util.WebUtil;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 发票操作类
 * @author lichunsheng
 * @create 2018-01-10
 **/
@Controller
@RequestMapping(value = "invoice/")
public class InvoiceController {

    private static final Logger logger = LogManager.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceCommonService invoiceCommonService;
    @Autowired
    private ResendInvoicesService resendInvoicesService;
    @Autowired
    private CancelInvoicesService cancelInvoicesService;
    @Autowired
    private InvalidInvoicesService invalidInvoicesService;
    @Autowired
    private SyncStatusInvoicesService syncStatusInvoicesService;
    @Autowired
    private GetCustomerCodeService getCustomerCodeService;


    /**
     * 开票列表页面跳转
     * @param request
     * @return
     */
    @RequestMapping(value = { "invoiceMakeOutList" }, method = { RequestMethod.GET })
    String invoiceMakeOutList(HttpServletRequest request) {
        return "invoice/invoiceMakeOutList";
    }


    /**
     * 开票列表查询
     * @param corder_sn  网单编号
     * @param invoice_title 发票抬头
     * @param tax_payer_number 纳税人识别号
     * @param register_address_and_phone 注册地址和电话
     * @param bank_name_and_account 开户行和卡号
     * @param energy_saving 节能补贴
     * @param is_reinvoice 重开票
     * @param type 发票类型
     * @param state 发票状态
     * @param electric_flag 是否电子发票
     * @param try_num 推送次数
     * @param billing_time_start 开票时间
     * @param billing_time_end 开票时间
     * @param invalid_time_start 发票作废时间
     * @param invalid_time_end 发票作废时间
     * @param status_type 推送状态
     * @param success 是否成功
     * @param corder_add_time_start 下单时间
     * @param corder_add_time_end 下单时间
     * @param timeA 未推送时间
     * @param is_together 是否货票同行
     * @param timeB 未开票时间
     * @param corder_type 网单类型
     * @param invoice_number 发票号
     * @param rows
     * @param page
     * @param request
     * @param response
     */
    @RequestMapping(value = { "findInvoiceMakeOutList" }, method = { RequestMethod.POST })
    void findInvoiceMakeOutList(@RequestParam(required = false) String corder_sn,
                                @RequestParam(required = false) String sourceorder_sn,
                                @RequestParam(required = false) String invoice_title,
                                @RequestParam(required = false) String tax_payer_number,
                                @RequestParam(required = false) String register_address_and_phone,
                                @RequestParam(required = false) String bank_name_and_account,
                                @RequestParam(required = false) String energy_saving,
                                @RequestParam(required = false) String is_reinvoice,
                                @RequestParam(required = false) String type,
                                @RequestParam(required = false) String state,
                                @RequestParam(required = false) String electric_flag,
                                @RequestParam(required = false) String try_num,
                                @RequestParam(required = false) String billing_time_start,
                                @RequestParam(required = false) String billing_time_end,
                                @RequestParam(required = false) String invalid_time_start,
                                @RequestParam(required = false) String invalid_time_end,
                                @RequestParam(required = false) String status_type,
                                @RequestParam(required = false) String success,
                                @RequestParam(required = false) String corder_add_time_start,
                                @RequestParam(required = false) String corder_add_time_end,
                                @RequestParam(required = false) String eai_write_state,
                                @RequestParam(required = false) String is_together,
                                @RequestParam(required = false) String timeB,
                                @RequestParam(required = false) String corder_type,
                                @RequestParam(required = false) String invoice_number,
                                @RequestParam(required = false) Integer rows,
                                @RequestParam(required = false) Integer page,
                                HttpServletRequest request, HttpServletResponse response) {
             response.setCharacterEncoding("UTF-8");
        try {
            if (rows == null)
                rows = 50;
            if (page == null)
                page = 1;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("m", (page - 1) * rows);
            params.put("n", rows);
            //参数加入params里
            params.put("corder_sn", corder_sn.trim());
            params.put("sourceorder_sn", sourceorder_sn.trim());
            params.put("invoice_title", invoice_title.trim());
            params.put("tax_payer_number", tax_payer_number.trim());
            params.put("register_address_and_phone", register_address_and_phone.trim());
            params.put("bank_name_and_account", bank_name_and_account.trim());
            params.put("energy_saving", energy_saving);
            params.put("is_reinvoice", is_reinvoice);
            params.put("type", type);
            params.put("state", state);
            params.put("electric_flag", electric_flag);
            params.put("try_num", try_num);
            params.put("billing_time_start", billing_time_start);
            params.put("billing_time_end", billing_time_end);
            params.put("invalid_time_start", invalid_time_start);
            params.put("invalid_time_end", invalid_time_end);
            params.put("status_type", status_type);
            params.put("success", success);
            params.put("corder_add_time_start", corder_add_time_start);
            params.put("corder_add_time_end", corder_add_time_end);
            params.put("eai_write_state", eai_write_state);
            params.put("is_together", is_together);
            params.put("timeB", timeB);
            params.put("corder_type", corder_type);
            params.put("invoice_number", invoice_number.trim());

            Map<String, Object> retMap = invoiceCommonService.findInvoiceMakeOutList(params);
            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    /**
     * 开单列表导出Excel
     * @param exportData  导出数据检索条件
     * @param response
     * @return 方法执行完毕调用的画面
     *
     */
    @RequestMapping(value = { "exportInvoiceMakeOutList" })
    void exportInvoiceMakeOutList(@RequestParam(required = false) String exportData,
                                  HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<String, Object>();
        response.setCharacterEncoding("UTF-8");
        params.put("m", 0);
        params.put("n", 5000);
        Map<String, String> map = JsonUtil.fromJson(exportData);

        //参数加入params里
        params.put("corder_sn", map.get("corder_sn").trim());
        params.put("invoice_title", map.get("invoice_title").trim());
        params.put("tax_payer_number", map.get("tax_payer_number").trim());
        params.put("register_address_and_phone", map.get("register_address_and_phone").trim());
        params.put("bank_name_and_account", map.get("bank_name_and_account").trim());
        params.put("energy_saving", map.get("energy_saving"));
        params.put("is_reinvoice", map.get("is_reinvoice"));
        params.put("type", map.get("type"));
        params.put("state", map.get("state"));
        params.put("electric_flag", map.get("electric_flag"));
        params.put("tryNum", map.get("tryNum"));
        params.put("billing_time_start", map.get("billing_time_start"));
        params.put("billing_time_end", map.get("billing_time_end"));
        params.put("invalid_time_start", map.get("invalid_time_start"));
        params.put("invalid_time_end", map.get("invalid_time_end"));
        params.put("status_type", map.get("status_type"));
        params.put("success", map.get("success"));
        params.put("corder_add_time_start", map.get("corder_add_time_start"));
        params.put("corder_add_time_end", map.get("corder_add_time_end"));
        params.put("timeA", map.get("timeA"));
        params.put("is_together", map.get("is_together"));
        params.put("timeB", map.get("timeB"));
        params.put("corder_type", map.get("corder_type"));
        params.put("invoice_number", map.get("invoice_number").trim());

        String ids = request.getParameter("ids");
        if (StringUtils.isNotBlank(ids)) {
            ids = ids.replace("[", "").replace("]", "");
            params.put("ids", ids);
        }

        //获取开单列表List
        final List<Map<String, Object>> result = invoiceCommonService.getExportInvoiceMakeOutList(params);
        String fileName = "开票报表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "数据导出";
        String[] sheetHead = new String[] { "序号", "网单号", "客户编码", "发票抬头", "纳税人识别号", "注册地址和电话",
                "开户银行", "发票备注", "发票收件人", "发票收件地址", "邮政编码", "收件人电话", "网单生成时间", "物料编码", "商品名称",
                "商品分类", "计量单位", "数量", "含税价", "含税金额", "不含税单价", "不含税金额", "税额", "税率", "补贴金额", "发票类型",
                "是否货票同行", "发票状态", "付款方式", "开票时间", "开票人", "开票状态", "发票作废时间", "首次推送开票时间",
                "电商最后更新开票信息时间", "收货人", "地区名称", "收货地址", "手机号", "固定电话", "订单备注", "税控号码" };
        try {
            ExcelExportUtil.exportEntity(request, response, fileName, sheetName, sheetHead,
                    new ExcelCallbackInterfaceUtil() {

                        @Override
                        public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp)
                                throws Exception {
                            setExcelBodyTotalForInvoicesMakeOut(sheet, temp, result);
                        }

                    });
        } catch (Exception e) {
            logger.error("开票列表导出失败", e);
            e.printStackTrace();
        }
    }

    /**
     * 3W特殊网单发票列表 页面跳转
     * @param request
     * @return
     */
    @RequestMapping(value = { "invoiceWwwLogs" }, method = { RequestMethod.GET })
    public String invoiceWwwLogsList(HttpServletRequest request) {
        return "invoice/invoiceWwwLogs";
    }

    /**
     * 3W特殊网单发票列表
     * @param request
     * @return
     */
    @RequestMapping(value = { "findInvoicesWwwLogs" }, method = { RequestMethod.POST })
    public void findInvoicesWwwLogs(@RequestParam(required = false) String sourceSn,
                                    @RequestParam(required = false) String cOrderSn,
                                   @RequestParam(required = false) String success,
                                   @RequestParam(required = false) String source,
                                   @RequestParam(required = false) String addTimeMax,
                                   @RequestParam(required = false) String addTimeMin,
                                   @RequestParam(required = false) Integer rows,
                                   @RequestParam(required = false) Integer page,
                                   HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        try {
            if (rows == null)
                rows = 50;
            if (page == null)
                page = 1;
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("m", (page - 1) * rows);
            paramMap.put("n", rows);
            paramMap.put("sourceSn", sourceSn.trim());
            paramMap.put("cOrderSn", cOrderSn.trim());
            paramMap.put("success", success);
            paramMap.put("source", source);
            paramMap.put("addTimeMin", addTimeMin);
            paramMap.put("addTimeMax", addTimeMax);

            Map<String, Object> retMap = invoiceCommonService.findInvoiceWwwLogPage(paramMap);

            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.error("", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    /**
     * 订单发票列表导出
     * @param request
     * @return
     */
    @RequestMapping(value = { "exportInvoicesWwwLogs" })
    void exportInvoicesWwwLogs(@RequestParam(required = false) String exportData,
                                    HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("m", 0);
        params.put("n", 5000);
        //orderStatus
        Map<String, String> map = JsonUtil.fromJson(exportData);
        params.put("sourceSn", map.get("sourceSn").trim());
        params.put("success", map.get("success"));
        params.put("source", map.get("source"));
        params.put("addTimeMin", map.get("addTimeMin").trim());
        params.put("addTimeMax", map.get("addTimeMax").trim());
        //获取开单列表List
        final List<Map<String, Object>> resultList = invoiceCommonService.findInvoiceWwwLogList(params);

        String fileName = "3W发票待人工处理列表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "数据导出";

        String[] sheetHead = new String[] { "序号", "订单号", "处理状态", "订单来源", "添加时间", "处理时间"};

        final String[] dataKey = new String[] { "orderSn", "success", "source", "addTime", "processTime" };

        try {
            ExcelExportUtil.exportEntity(request, response, fileName, sheetName, sheetHead,
                    new ExcelCallbackInterfaceUtil() {
                        @Override
                        public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp)
                                throws Exception {
                            ExcelExportUtil.setExcelBody(sheet, temp, dataKey, resultList);
                        }

                    });
        } catch (Exception e) {
            logger.error("3W发票待人工处理列表导出失败", e);
            e.printStackTrace();
        }
    }

    /**
     * 3W发票待人工处理-开票
     * @param flag
     * @param orderId
     * @param orderProductId
     * @param request
     * @return
     */
    @RequestMapping(value = { "invoicesWwwLogsOperate" }, method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> invoicesWwwLogsOperate(@RequestParam(required = true) String flag,
                                                      @RequestParam(required = true) String orderId,
                                                      @RequestParam(required = true) String orderProductId,
                                                      HttpServletRequest request) {

        String message = "";
        Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            String auditor = WebUtil.currentUserName(request);
            message = invoiceCommonService.invoiceWwwLogsOperate(flag,orderId,orderProductId,auditor);
            if (message.trim().equals("")) {
                message = "您已成功更新发票信息!";
            }
            retMap.put("message", message);
            return retMap;
        } catch (Exception e) {
            retMap.put("message", "操作发票失败");
            logger.error("操作发票失败", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    /**
     * 获得金税实时开票数据
     * @param corderSn 网单号
     * @param request
     * @return
     */
    @RequestMapping(value = { "getTaxInvoicesInfo" }, method = { RequestMethod.GET })
    @ResponseBody
    public HttpJsonResult<InvoiceEntity> getTaxInvoicesInfo(@RequestParam(required = false) String corderSn,
                                                            HttpServletRequest request) {
        HttpJsonResult<InvoiceEntity> result = new HttpJsonResult<InvoiceEntity>();
        QueryInvoiceInputType inputType = new QueryInvoiceInputType();
        inputType.setWdh(corderSn);
        try {
            ServiceResult<InvoiceEntity> invoiceResult = invoiceCommonService.getTaxInvoicesInfo(inputType);
            boolean success = invoiceResult.getSuccess();
            if (success) {
                InvoiceEntity invoiceEntity = invoiceResult.getResult();
                result.setData(invoiceEntity);
            } else {
                result.setMessage("实时开票信息查询失败!");
            }
        } catch (Exception e) {
            result.setMessage("实时开票信息查询失败!");
            logger.error("实时开票信息查询失败!", e);
        }
        return result;
    }

    /**
     * 获得电子发票实时开票数据
     * @param corderSn 网单号
     * @param request
     * @return
     */
    @RequestMapping(value = { "getElectricInvoiceInfo" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Invoice> getElectricInvoiceInfo(@RequestParam(required = false) String corderSn,
                                                          HttpServletRequest request) {
        HttpJsonResult<Invoice> result = new HttpJsonResult<Invoice>();
        try {
            Map<String, String> resultMap = invoiceCommonService.queryElecInvoice(corderSn);

            if (resultMap != null) {
                Invoice invoice = new Invoice();
                invoice.setCode(resultMap.get("code"));//发票号
                invoice.setStatus(resultMap.get("status"));//发票状态
                invoice.setTotalAmount(resultMap.get("totalAmount"));//含税金额
                invoice.setNoTaxAmount(resultMap.get("noTaxAmount"));//不含税金额
                invoice.setTaxAmount(resultMap.get("taxAmount"));//税额
                invoice.setGenerateTime(resultMap.get("generateTime"));//开票时间
                invoice.setValidTime(resultMap.get("validTime") != null ? resultMap
                        .get("validTime") : "");//作废时间
                invoice.setViewUrl(resultMap.get("viewUrl"));//查看地址
                invoice.setFiscalCode(resultMap.get("fiscalCode"));//税控码

                result.setData(invoice);
            } else {
                result.setMessage("实时开票信息返回为空!");
            }
        } catch (Exception e) {
            result.setMessage("实时开票信息查询失败!");
            logger.error("实时开票信息查询失败!", e);
        }
        return result;
    }

    /**
     * 纸质发票日志页面调转
     * @param request
     * @return
     */
    @RequestMapping(value = { "makeInvoiceApiLogs" }, method = { RequestMethod.GET })
    String makeInvoiceApiLogs(HttpServletRequest request) {
        return "invoice/makeInvoiceApiLogs";
    }

    /**
     * 开票日志查询
     */
    @RequestMapping(value = { "findMakeInvoiceApiLogs" }, method = { RequestMethod.POST })
    void makeInvoiceLogList(@RequestParam(required = false) String cOrderSn,
                            @RequestParam(required = false) Integer type,
                            @RequestParam(required = false) String addTimeMin,
                            @RequestParam(required = false) String addTimeMax,
                            @RequestParam(required = false) Integer isSuccess,
                            @RequestParam(required = false) Integer rows,
                            @RequestParam(required = false) Integer page,
                            HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        try {
            if (rows == null)
                rows = 50;
            if (page == null)
                page = 1;
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("m", (page - 1) * rows);
            paramMap.put("n", rows);
            paramMap.put("cOrderSn", cOrderSn);
            paramMap.put("type", type);
            paramMap.put("addTimeMin", addTimeMin);
            paramMap.put("addTimeMax", addTimeMax);
            paramMap.put("isSuccess", isSuccess);

            Map<String, Object> retMap = invoiceCommonService.findMakeInvoiceApiLogs(paramMap);
            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    /**
     * 电子发票日志跳转
     * @param request
     * @return
     */
    @RequestMapping(value = { "electronicInvoiceLog" }, method = { RequestMethod.GET })
    String electronicInvoiceLog(HttpServletRequest request) {
        return "invoice/electronicInvoiceLog";
    }

    /**
     * 电子发票日志查询
     * @param cOrderSn
     * @param type
     * @param addTimeMin
     * @param addTimeMax
     * @param success
     * @param rows
     * @param page
     * @param request
     * @param response
     */
    @RequestMapping(value = { "findelectronicInvoiceLogs" }, method = { RequestMethod.POST })
    void findelectronIcnvoiceLogs(@RequestParam(required = false) String cOrderSn,
                                  @RequestParam(required = false) Integer type,
                                  @RequestParam(required = false) String addTimeMin,
                                  @RequestParam(required = false) String addTimeMax,
                                  @RequestParam(required = false) Integer success,
                                  @RequestParam(required = false) Integer rows,
                                  @RequestParam(required = false) Integer page,
                                  HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        try {
            if (rows == null)
                rows = 50;
            if (page == null)
                page = 1;
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("m", (page - 1) * rows);
            paramMap.put("n", rows);
            paramMap.put("cOrderSn", cOrderSn);
            paramMap.put("type", type);
            paramMap.put("addTimeMin", addTimeMin);
            paramMap.put("addTimeMax", addTimeMax);
            paramMap.put("success", success);

            Map<String, Object> retMap = invoiceCommonService.findElecInvoiceLogsService(paramMap);
            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    /**
     * 订单发票跳转
     * @param request
     * @return
     */
    @RequestMapping(value = { "memberInvoiceList" }, method = { RequestMethod.GET })
    String orderInvoiceList(HttpServletRequest request) {
        return "invoice/memberInvoiceList";
    }

    /**
     * 订单发票列表
     */
    @RequestMapping(value = { "findMemberInvoiceList" }, method = { RequestMethod.POST })
    void findMemberInvoiceList(@RequestParam(required = false) Integer orderStatus,
                               @RequestParam(required = false) Integer paymentStatus,
                               @RequestParam(required = false) String source,
                               @RequestParam(required = false) Integer state,
                               @RequestParam(required = false) Integer invoiceType,
                               @RequestParam(required = false) Integer electricFlag,
                               @RequestParam(required = false) String memberName,
                               @RequestParam(required = false) String invoiceTitle,
                               @RequestParam(required = false) String taxPayerNumber,
                               @RequestParam(required = false) String registerAddress,
                               @RequestParam(required = false) String registerPhone,
                               @RequestParam(required = false) String bankName,
                               @RequestParam(required = false) String bankCardNumber,
                               @RequestParam(required = false) String addTimeMin,
                               @RequestParam(required = false) String addTimeMax,
                               @RequestParam(required = false) String auditTimeMin,
                               @RequestParam(required = false) String auditTimeMax,
                               @RequestParam(required = false) String orderSn,
                               @RequestParam(required = false) Integer rows,
                               @RequestParam(required = false) Integer page,
                               HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        try {
            if (rows == null)
                rows = 50;
            if (page == null)
                page = 1;
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("m", (page - 1) * rows);
            paramMap.put("n", rows);
            paramMap.put("orderSn", orderSn);
            paramMap.put("orderStatus", orderStatus);
            paramMap.put("paymentStatus", paymentStatus);
            paramMap.put("source", source);
            paramMap.put("state", state);
            paramMap.put("invoiceType", invoiceType);
            paramMap.put("electricFlag", electricFlag);
            paramMap.put("memberName", memberName);
            paramMap.put("invoiceTitle", invoiceTitle);
            paramMap.put("taxPayerNumber", taxPayerNumber);
            paramMap.put("registerAddress", registerAddress);
            paramMap.put("registerPhone", registerPhone);
            paramMap.put("bankName", bankName);
            paramMap.put("bankCardNumber", bankCardNumber);
            paramMap.put("addTimeMin", addTimeMin);
            paramMap.put("addTimeMax", addTimeMax);
            paramMap.put("auditTimeMin", auditTimeMin);
            paramMap.put("auditTimeMax", auditTimeMax);

            Map<String, Object> retMap = invoiceCommonService.findMemberInvoiceList(paramMap);
            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.error("", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    /**
     * 订单发票列表导出
     * @param reques
     * @return
     */
    @RequestMapping(value = { "exportAllMemberInvoiceList" })
    void exportAllMemberInvoiceList(@RequestParam(required = false) String exportData,
                                    HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("m", 0);
        params.put("n", 5000);
        //orderStatus
        Map<String, String> map = JsonUtil.fromJson(exportData);
        params.put("orderSn", map.get("orderSn").trim());
        params.put("orderStatus", map.get("orderStatus"));
        params.put("paymentStatus", map.get("paymentStatus"));
        params.put("source", map.get("source"));
        params.put("state", map.get("state"));
        params.put("invoiceType", map.get("invoiceType"));
        params.put("electricFlag", map.get("electricFlag"));
        params.put("memberName", map.get("memberName"));
        params.put("invoiceTitle", map.get("invoiceTitle").trim());
        params.put("taxPayerNumber", map.get("taxPayerNumber").trim());
        params.put("registerAddress", map.get("registerAddress").trim());
        params.put("registerPhone", map.get("registerPhone").trim());
        params.put("bankName", map.get("bankName").trim());
        params.put("bankCardNumber", map.get("bankCardNumber").trim());
        params.put("addTimeMin", map.get("addTimeMin").trim());
        params.put("addTimeMax", map.get("addTimeMax").trim());
        params.put("auditTimeMin", map.get("auditTimeMin").trim());
        params.put("auditTimeMax", map.get("auditTimeMax").trim());

        String ids = request.getParameter("ids");
        if (StringUtils.isNotBlank(ids)) {
            ids = ids.replace("[", "").replace("]", "");
            params.put("ids", ids);
        }

        //获取开单列表List
        final List<MemberInvoicesDispItem> resultList = invoiceCommonService.getExportMemberInvoicesList(params);
        String fileName = "订单发票列表报表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "数据导出";

        String[] sheetHead = new String[] { "序号", "订单号", "会员名称", "发票类型", "发票抬头", "纳税人识别号", "注册地址",
                "注册电话", "开户行", "银行卡号", "添加时间", "审核状态", "审核时间", "审核人", "是否锁定", "提示信息" };
        try {
            ExcelExportUtil.exportEntity(request, response, fileName, sheetName, sheetHead,
                    new ExcelCallbackInterfaceUtil() {

                        @Override
                        public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp)
                                throws Exception {
                            setExportMemberInvoiceList(sheet, temp, resultList);
                        }

                    });
        } catch (Exception e) {
            logger.error("订单开票列表导出失败", e);
            e.printStackTrace();
        }
    }


    /**
     * SAP接口监控页面调转
     * @param request
     * @return
     */
    @RequestMapping(value = { "invoiceSapLogList" }, method = { RequestMethod.GET })
    String invoiceSapLogList(HttpServletRequest request) {
        return "invoice/invoiceSapLogList";
    }

    /**
     * SAP接口监控查询
     * @param count 推送次数
     * @param orderProductId  网单ID号
     * @param pushType 开票类型
     * @param success 是否成功
     * @param addTimeMin 记录起始时间
     * @param addTimeMax 记录截止时间
     * @param rows
     * @param page
     * @param request
     * @param response
     */
    @RequestMapping(value = { "findInvoiceSapLogList" }, method = { RequestMethod.POST })
    void findInvoiceSapLogList(@RequestParam(required = false) String cOrderSn,
                               @RequestParam(required = false) String count,
                               @RequestParam(required = false) String orderProductId,
                               @RequestParam(required = false) String pushType,
                               @RequestParam(required = false) String success,
                               @RequestParam(required = false) String addTimeMin,
                               @RequestParam(required = false) String addTimeMax,
                               @RequestParam(required = false) String lastMessage,
                               @RequestParam(required = false) Integer rows,
                               @RequestParam(required = false) Integer page,
                               HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        try {
            if (rows == null)
                rows = 100;
            if (page == null)
                page = 1;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("m", (page - 1) * rows);
            params.put("n", rows);
            params.put("count", count);
            params.put("orderProductId", orderProductId.trim());
            params.put("pushType", pushType);
            params.put("success", success);
            params.put("addTimeMin", addTimeMin);
            params.put("addTimeMax", addTimeMax);
            params.put("lastMessage", lastMessage);

            Map<String, Object> retMap = invoiceCommonService.findInvoiceSapLogList(cOrderSn,params);
            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    /**
     * 更具ID查询，显示网单开票信息
     * @param invoiceId
     * @param modelMap
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "showInvoiceInfo" }, method = { RequestMethod.GET })
    @ResponseBody
    public HttpJsonResult<Map<String, Object>> showInvoiceInfo(@RequestParam(required = false) Integer invoiceId,
                                                               Map<String, Object> modelMap,
                                                               HttpServletRequest request,
                                                               HttpServletResponse response) {

        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        try {
            //添加查询条件
            modelMap = invoiceCommonService.showInvoiceInfo(invoiceId);
            result.setData(modelMap);

        } catch (Exception e) {
            logger.error("[Invoice][InvoiceController]获取网单发票信息时发生错误", e);
        }
        return result;
    }

    /**
     * 对推送SAP接口失败数据，将推送次数置为0
     * @param request
     */
    @RequestMapping(value = { "pushAgainInvoiceSap" }, method = { RequestMethod.GET })
    @ResponseBody
    public HttpJsonResult<Map<String, Object>> pushAgainInvoiceSap(@RequestParam(required = false) Integer id,
                                                                   HttpServletRequest request,
                                                                   HttpServletResponse response) {
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        try {

            invoiceCommonService.updatePushAgainInvoiceSapCount(id);
        } catch (Exception e) {
            logger.error("重新推送SAP接口异常", e);
            result.setMessage("[异常原因]：" + e.getMessage());
        }
        return result;
    }

    /**
     * 编辑/审核发票信息
     * @param id  用户发票id
     * @param modifyFlg
     * @param orderSn
     * @param modelMap
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "modifyMemberInvoices" }, method = { RequestMethod.GET })
    ModelAndView modifyMemberInvoices(@RequestParam(required = false) Integer id,
                                @RequestParam(required = false) String modifyFlg,
                                @RequestParam(required = false) String orderSn,
                                Map<String, Object> modelMap, HttpServletRequest request,
                                HttpServletResponse response) {
        ModelAndView mv  = new ModelAndView();
        try {
            Map<String,Object> retMap = invoiceCommonService.modifyMemberInvoices(id,modifyFlg,orderSn);
            modelMap.putAll(retMap);
            mv.addObject("map", retMap);
            mv.setViewName("invoice/memberInvoiceDetail");


        } catch (Exception e) {
            logger.error("编辑/审核发票信息失败", e);
            throw new BusinessException("异常信息:" + e.getMessage());
        }
//        return "invoice/memberInvoiceDetail";

        return mv;
    }

    /**
     * 编辑发票信息，3w发票待人工处理界面使用
     * @param id  订单id
     * @param modifyFlg
     * @param orderSn
     * @param modelMap
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "modifyMemberInvoicesBy3wInvoiceLog" }, method = { RequestMethod.GET })
    String modifyMemberInvoicesBy3wInvoiceLog(@RequestParam(required = false) Integer id,
                                @RequestParam(required = false) String modifyFlg,
                                @RequestParam(required = false) String orderSn,
                                Map<String, Object> modelMap, HttpServletRequest request,
                                HttpServletResponse response) {
        try {
            Map<String,Object> retMap = invoiceCommonService.modifyMemberInvoicesBy3wInvoiceLogs(id,modifyFlg,orderSn);
            modelMap.putAll(retMap);
        } catch (Exception e) {
            logger.error("编辑/审核发票信息失败", e);
            throw new BusinessException("异常信息:" + e.getMessage());
        }
        return "invoice/memberInvoiceDetail";
    }

    /**
     * 根据发票台头获取发票信息
     * @param invoiceTitle
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "getMemberInvoiceByInvoiceTitle" }, method = { RequestMethod.GET })
    @ResponseBody
    Map<String, Object> getMemberInvoiceByInvoiceTitle(@RequestParam(required = false) String invoiceTitle,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response) {
        try {
            // 根据发票抬头获取发票信息
            MemberInvoices memberInvoices = invoiceCommonService.getMemberInvoiceByInvoiceTitle(invoiceTitle);
            Map<String, Object> retMap = new HashMap<String, Object>();
            retMap.put("memberInvoices", memberInvoices);
            return retMap;

        } catch (Exception e) {
            logger.error("根据发票抬头获取发票信息失败", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    /**
     * 修改或审核发票信息
     * @param request
     * @return
     */
    @RequestMapping(value = { "saveMemberInvoices" }, method = { RequestMethod.GET })
    @ResponseBody
    Map<String, Object> saveMemberInvoices(@RequestParam(required = false) Integer id,
                                           @RequestParam(required = false) Integer invoiceType,
                                           @RequestParam(required = false) String invoiceTitle,
                                           @RequestParam(required = false) String taxPayerNumber,
                                           @RequestParam(required = false) String registerAddress,
                                           @RequestParam(required = false) String registerPhone,
                                           @RequestParam(required = false) String bankName,
                                           @RequestParam(required = false) String bankCardNumber,
                                           @RequestParam(required = false) Integer state,
                                           @RequestParam(required = false) String remark,
                                           @RequestParam(required = false) String vatremark,
                                           HttpServletRequest request, HttpServletResponse response) {
        String errorFlg = "1";
        String message = "";
        Map<String, Object> retMap = new HashMap<String, Object>();
        try {
        	HttpSession session = request.getSession();
      	    String userName = Ustring.getString(session.getAttribute("userName"));
            String auditor = WebUtil.currentUserName(request);
            message = invoiceCommonService.saveMemberInvoices(id, invoiceType, invoiceTitle,
                    taxPayerNumber, registerAddress, registerPhone, bankName, bankCardNumber, state,
                    remark, auditor,userName,vatremark);
            if (message.trim().equals("")) {
                message = "您已成功更新发票信息!<br/>如果是增票需发票管理员审核后方可生效";
                errorFlg = "0";
            }
            retMap.put("message", message);
            retMap.put("errorFlg", errorFlg);
            return retMap;
        } catch (Exception e) {
            retMap.put("message", "根据发票抬头获取发票信息失败");
            retMap.put("errorFlg", errorFlg);
            logger.error("根据发票抬头获取发票信息失败", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    /**
     * 解锁发票信息
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "unlockMemberInvoices" }, method = { RequestMethod.GET })
    @ResponseBody
    Map<String, Object> unlockMemberInvoices(@RequestParam(required = false) Integer id,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {
        String errorFlg = "1";
        String message = "";
        Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            String auditor = WebUtil.currentUserName(request);
            message = invoiceCommonService.unlockMemberInvoices(id, auditor);
            if (message.trim().equals("")) {
                message = "您已成功解锁发票信息!";
                errorFlg = "0";
            }
            retMap.put("message", message);
            retMap.put("errorFlg", errorFlg);
            return retMap;
        } catch (Exception e) {
            retMap.put("message", message);
            retMap.put("errorFlg", errorFlg);
            logger.error("根据发票id解锁发票信息失败", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    /**
     * 重推发票
     * @param reSendData
     * @param request
     * @return
     */
    @RequestMapping(value = { "reSendInvoices" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Integer> reSendInvoices(@RequestParam(required = false) String reSendData,
                                                  HttpServletRequest request) {
        HttpJsonResult<Integer> result = new HttpJsonResult<Integer>();
        try {
            Integer successCount = resendInvoicesService.resendInvoices(reSendData);
            if (successCount > 0){
                result.setData(successCount);//成功条数
                JSONArray cacelData = (JSONArray) JSON.parse(reSendData);
                result.setTotalCount(cacelData.length());//总条数
            }else{
                result.setMessage("重推失败!");
            }
        } catch (Exception e) {
            result.setMessage("重推失败!");
            logger.error("重推失败!", e);
        }
        return result;
    }

    /**
     * 取消发票
     * @param cancelData
     * @param request
     * @return
     */
    @RequestMapping(value = { "cancelInvoices" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Integer> cancelInvoices(@RequestParam(required = false) String cancelData,
                                                  HttpServletRequest request) {
        HttpJsonResult<Integer> result = new HttpJsonResult<Integer>();
        try {
            Integer successCount = cancelInvoicesService.cancelInvoices(cancelData);
            if (successCount > 0) {
                result.setData(successCount);//成功条数
                JSONArray cacelData = (JSONArray) JSON.parse(cancelData);
                result.setTotalCount(cacelData.length());//总条数
            } else {
                result.setMessage("取消失败!");
            }
        } catch (Exception e) {
            result.setMessage("取消失败!");
            logger.error("取消失败!", e);
        }
        return result;
    }

    /**
     * 作废发票
     * @param forceCancelData
     * @param invalidReason
     * @param request
     * @return
     */
    @RequestMapping(value = { "invalidInvoice" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Integer> invalidInvoice(@RequestParam(required = false) String forceCancelData,
                                                  @RequestParam(required = false) String invalidReason,
                                                  HttpServletRequest request) {
        HttpJsonResult<Integer> result = new HttpJsonResult<Integer>();
        try {
            Integer successCount = invalidInvoicesService.InvalidInvoices(forceCancelData,invalidReason);
            if (successCount > 0) {
                result.setData(successCount);//成功条数
                JSONArray cacelData = (JSONArray) JSON.parse(forceCancelData);
                result.setTotalCount(cacelData.length());//总条数
            } else {
                result.setMessage("作废失败!");
            }
        } catch (Exception e) {
            result.setMessage("作废失败!");
            logger.error("作废失败!", e);
        }
        return result;
    }

    /**
     * 同步金税和电子发票状态
     * @param syncData
     * @param request
     * @return
     */
    @RequestMapping(value = { "syncStatusInvoices" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Integer> syncStatusInvoices(@RequestParam(required = false) String syncData,
                                                      HttpServletRequest request) {
        HttpJsonResult<Integer> result = new HttpJsonResult<Integer>();
        try {
            ServiceResult<Integer> invoiceResult = syncStatusInvoicesService.syncStatusInvoices(syncData);
            boolean success = invoiceResult.getSuccess();
            if (success) {
                result.setData(invoiceResult.getResult());//成功条数
                JSONArray cacelData = (JSONArray) JSON.parse(syncData);
                result.setTotalCount(cacelData.length());//总条数
            } else {
                result.setMessage("同步失败!" + result.getMessage());
            }
        } catch (Exception e) {
            result.setMessage("同步出错!" + e.getMessage());
            logger.error("[InvoiceController]同步出错!", e);
        }
        return result;
    }

    /**
     * 获取sap客户码页面跳转
     * @param request
     * @return
     */
    @RequestMapping(value = { "getCustomerCodePage" }, method = { RequestMethod.GET })
    String getCustomerCodePage(HttpServletRequest request) {
        return "invoice/getCustomerCodePage";
    }

    /**
     * 获取sap客户码
     * @param cOrderSn
     * @param request
     * @return
     */
    @RequestMapping(value = { "getCustomerCode" }, method = { RequestMethod.POST })
    public void getCustomerCode(@RequestParam(required = true) String cOrderSn,
                                  Map<String, Object> modelMap, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            if (cOrderSn == null || cOrderSn.equals("")) {
                resultMap.put("Message", "网单号不能为空！");
                logger.error("网单号不能为空!");
            }else{
                resultMap = this.getCustomerCodeService.getCustomerCode(cOrderSn);
            }
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JsonUtil.toJson(resultMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e){
            logger.error("查询客户编码发生异常!", e);
        }
    }

    /**
     * 查询网单信息跳转界面
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "inCOrderSnListPage" })
    public String inCOrderSnListPage(HttpServletRequest request, HttpServletResponse response,
                              Map<String, Object> modelMap) {
        return "invoice/inCOrderSnListPage";
    }

    /**
     * 查询网单信息
     * @param cOrderSns
     * @param pageIndex
     * @param orderProductsType
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = { "queryOrderProductsInfo" })
    public String queryOrderProductsInfo(@RequestParam(required = true) String cOrderSns,
                                         @RequestParam(required = false) Integer pageIndex,
                                         @RequestParam(required = true) String orderProductsType,
                                         Map<String, Object> modelMap, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        int pageSize = 10;
        pageIndex = pageIndex == null || pageIndex <= 0 ? 1 : pageIndex;
        PagerInfo page = new PagerInfo(pageSize, pageIndex);

        String[] strTemp = cOrderSns.split("\r\n");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("cOrderSns", Arrays.asList(strTemp));
        paramMap.put("start", page.getStart());
        paramMap.put("size", page.getPageSize());

        if (!InvoiceConst.COMMON_CORDER_TYPE.toString().equals(orderProductsType)){
            response.reset();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print("只能查询普通网单信息！");
            response.getWriter().flush();
            return null;
        }

        int listcount = invoiceCommonService.getOpListByCOrderSnCount(paramMap);

        if (listcount > 0) {
            //获取网单列表List
            final List<Map<String, Object>> result = invoiceCommonService.getOpListByCOrderSnSearch(
                    paramMap);
            //转订单来源到渠道
            List<Map<String, Object>> channels = invoiceCommonService.getChannelNames();

            if (result != null && result.size() > 0 ) {
                for (int i = 0; i < result.size(); i++) {
                    String source = (String) result.get(i).get("source");
                    Object channelName = InvoiceServiceUtil.getSourceName(source, channels);
                    result.get(i).put("channelName", channelName);
                }
            }
            //添加渠道和客户编码
            addChannelAndCustomerCode(result);
            page.setRowsCount(listcount);
            modelMap.put("pager", page);
            modelMap.put("rowList", result);
        } else {
            response.reset();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print("没有查询到网单信息，请核对网单号是否正确！");
            response.getWriter().flush();
            return null;
        }

        return "invoice/inCOrderSnListPageGrid";
    }

    @RequestMapping(value = { "exportOrderProductsInfo" })
    public void exportOrderProductsInfo(@RequestParam(required = true) String cOrderSns,
                                        @RequestParam(required = true) String orderProductsType,
                                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        if (StringUtil.isEmpty(cOrderSns.replace("\r\n", ""), true)) {
            response.reset();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print("请提交有效的网单编号！");
            response.getWriter().flush();
            return;
        }

        String[] strTemp = cOrderSns.split("\r\n");
        //        Set<String> set = new HashSet<String>(Arrays.asList(strTemp));
        //        String[] cOrderSnsArray = set.toArray(new String[0]);
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("cOrderSns", Arrays.asList(strTemp));
        //获取网单列表List
        final List<Map<String, Object>> result = invoiceCommonService.getOpListByCOrderSn(paramMap);
        //转订单来源到渠道
        List<Map<String, Object>> channels = invoiceCommonService.getChannelNames();
        if (result != null && result.size() > 0) {
            if (!orderProductsType.equals("2")) {
                for (int i = 0; i < result.size(); i++) {
                    String source = (String) result.get(i).get("source");
                    Object channelName = InvoiceServiceUtil.getSourceName(source, channels);
                    result.get(i).put("channelName", channelName);
                }
            }
        } else {
            response.reset();
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print("没有查询到网单信息，请核对网单号是否正确！");
            response.getWriter().flush();
            return;
        }
        //添加渠道和客户编码
        addChannelAndCustomerCode(result);

        String fileName = "网单数据" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "网单数据导出";
        String[] sheetHead = new String[] { "序号", "网单号", "物料编码", "渠道", "总价", "数量", "售达方(客户码)",
                "渠道(SAP渠道编码)" };
        final String[] dataKey = new String[] { "cOrderSn", "sku", "channelName", "productAmount",
                "number", "customerCode", "sapChannelCode" };
        try {
            ExcelExportUtil.exportEntity(request, response, fileName, sheetName, sheetHead,
                    new ExcelCallbackInterfaceUtil() {
                        @Override
                        public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp)
                                throws Exception {
                            ExcelExportUtil.setExcelBody(sheet, temp, dataKey, result);
                        }

                    });
        } catch (Exception e) {
            logger.error("网单数据导出失败", e);
            e.printStackTrace();
        }
    }

    /**
     * 发票批量修改跳转界面
     */
    @RequestMapping(value = { "invoiceBatchModifyLoad" })
    String invoiceBatchModifyLoad(@RequestParam(required = false) Integer level,
                                  Map<String, Object> modelMap) {
        return "invoice/invoiceBatchModify";
    }

    /**
     * 发票批量修改
     * @param cOrderSns_invoices
     * @param invoice_title
     * @param product_name
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping(value = { "invoiceBatchModify" }, method = { RequestMethod.POST })
    @ResponseBody
    HttpJsonResult<Map<String, Object>> invoiceBatchModify(@RequestParam(required = false) String cOrderSns_invoices,
                                                           String invoice_title,
                                                           String product_name,
                                                           String productCateName,
                                                           String taxPayerNumber,
                                                           HttpServletRequest request,
                                                           HttpServletResponse response)
            throws IOException,
            ParseException {
        response.setCharacterEncoding("UTF-8");
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        try {
            cOrderSns_invoices = cOrderSns_invoices == null || "".equals(cOrderSns_invoices.trim()) ? null
                    : cOrderSns_invoices.trim();
            if (cOrderSns_invoices == null) {
                result.setMessage("网单号不允许为空！");
                return result;
            }
            invoice_title = invoice_title == null || "".equals(invoice_title.trim()) ? null
                    : invoice_title.trim();

            if (invoice_title == null) {
                result.setMessage("发票抬头不允许为空！");
                return result;
            }

            product_name = product_name == null || "".equals(product_name.trim()) ? null
                    : product_name.trim();

            if (product_name == null) {
                result.setMessage("商品名称不允许为空！");
                return result;
            }
            productCateName = productCateName == null || "".equals(productCateName.trim()) ? null
                    : productCateName.trim();

            if (productCateName == null) {
                result.setMessage("商品品类不允许为空！");
                return result;
            }
            taxPayerNumber = taxPayerNumber == null || "".equals(taxPayerNumber.trim()) ? null
                    : taxPayerNumber.trim();

            if (taxPayerNumber == null) {
                result.setMessage("纳税人识别号不允许为空！");
                return result;
            }

            String[] totalArray = cOrderSns_invoices.replace("\r", ",").replace("\n", ",")
                    .replaceAll("(,)+", ",").replace(" ", "").split(",");
            Set<String> set = new HashSet<String>(Arrays.asList(totalArray));
            totalArray = set.toArray(new String[0]);

            List<String[]> list = new ArraySplitUtil<String>().splistArray(totalArray, 50);
            int totalCount = 0;
            for (String[] subArray : list) {
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("cOrderSns", subArray);
                paramMap.put("invoiceTitle", invoice_title);
                paramMap.put("productName", product_name);
                paramMap.put("productCateName", productCateName);
                paramMap.put("taxPayerNumber", taxPayerNumber);
                int updateCount = invoiceCommonService.invoiceBatchModify(paramMap);
                totalCount += updateCount;
            }
            if (totalCount == 0) {
                result.setMessage("更新0条记录！请检查网单号是否正确！");
            } else {
                result.setMessage("更新成功！共更新" + totalCount + "条记录！");
            }
        } catch (Exception e) {
            logger.error("[invoiceBatchModify]更新Invoices[发票批量修改]时发生未知错误", e);
            result.setMessage("更新失败！");
            return result;
        }
        return result;
    }

    /**
     * 导出具体数据，实现回调函数
     * @param sheet
     * @param temp 行号
     * @param list 传入需要导出的 list
     */
    private void setExcelBodyTotalForInvoicesMakeOut(WritableSheet sheet, int temp,
                                                     List<Map<String, Object>> list)
            throws Exception {

        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat textFormat = new WritableCellFormat(font);
        textFormat.setAlignment(Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        DisplayFormat displayFormat = NumberFormats.INTEGER;
        WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
        numberFormat.setAlignment(jxl.format.Alignment.RIGHT);
        numberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        int index = 0;
        for (Map<String, Object> map : list) {
            index++;
            //jxl.write.Number(列号,行号 ,内容 )
            /* "网单号", "客户编码", "发票抬头", "纳税人识别号", "注册地址和电话", "开户银行",
             "发票备注", "发票收件人", "发票收件地址", "邮政编码", "收件人电话", "网单生成时间", "物料编码", "商品名称", "商品分类",
             "计量单位", "数量", "含税价", "含税金额", "不含税单价", "不含税金额", "税额", "税率", "补贴金额", "发票类型",
             "是否货票同行", "发票状态", "付款方式", "开票时间", "开票人", "开票状态", "发票作废时间", "首次推送开票时间",
             "电商最后更新开票信息时间", "收货人", "地区名称", "收货地址", "手机号", "固定电话", "订单备注", "税控号码"*/
            sheet.setColumnView(0, 10);
            sheet.addCell(new Label(0, temp, CommUtil.getStringValue(index), textFormat));
            sheet.setColumnView(1, 30);
            sheet.addCell(new Label(1, temp, CommUtil.getStringValue(map.get("cOrderSn")),
                    textFormat));
            sheet.setColumnView(2, 15);
            sheet.addCell(new Label(2, temp, CommUtil.getStringValue(map.get("customerCode")),
                    textFormat));
            sheet.setColumnView(3, 15);
            sheet.addCell(new Label(3, temp, CommUtil.getStringValue(map.get("invoiceTitle")),
                    textFormat));
            sheet.setColumnView(4, 15);
            sheet.addCell(new Label(4, temp, CommUtil.getStringValue(map.get("taxPayerNumber")),
                    textFormat));
            sheet.setColumnView(5, 15);
            sheet.addCell(new Label(5, temp, CommUtil.getStringValue(map
                    .get("registerAddressAndPhone")), textFormat));
            sheet.setColumnView(6, 15);
            sheet.addCell(new Label(6, temp,
                    CommUtil.getStringValue(map.get("bankNameAndAccount")), textFormat));
            sheet.setColumnView(7, 15);
            sheet
                    .addCell(new Label(7, temp, CommUtil.getStringValue(map.get("remark")), textFormat));
            sheet.setColumnView(8, 15);
            sheet.addCell(new Label(8, temp, CommUtil.getStringValue(map.get("receiptConsignee")),
                    textFormat));
            sheet.setColumnView(9, 15);
            sheet.addCell(new Label(9, temp, CommUtil.getStringValue(map.get("receiptAddress")),
                    textFormat));
            sheet.setColumnView(10, 15);
            sheet.addCell(new Label(10, temp, CommUtil.getStringValue(map.get("receiptZipcode")),
                    textFormat));
            sheet.setColumnView(11, 15);
            sheet.addCell(new Label(11, temp, CommUtil.getStringValue(map.get("receiptMobile")),
                    textFormat));
            sheet.setColumnView(12, 15);
            sheet.addCell(new Label(12, temp, CommUtil.getStringValue(map.get("cOrderAddTime")),
                    textFormat));
            sheet.setColumnView(13, 15);
            sheet.addCell(new Label(13, temp, CommUtil.getStringValue(map.get("sku")), textFormat));
            sheet.setColumnView(14, 15);
            sheet.addCell(new Label(14, temp, CommUtil.getStringValue(map.get("productName")),
                    textFormat));
            sheet.setColumnView(15, 15);
            sheet.addCell(new Label(15, temp, CommUtil.getStringValue(map.get("productCateName")),
                    textFormat));
            sheet.setColumnView(16, 15);
            sheet
                    .addCell(new Label(16, temp, CommUtil.getStringValue(map.get("unit")), textFormat));
            sheet.setColumnView(17, 15);
            sheet.addCell(new Label(17, temp, CommUtil.getStringValue(map.get("number")),
                    textFormat));
            sheet.setColumnView(18, 15);
            sheet
                    .addCell(new Label(18, temp, CommUtil.getStringValue(map.get("price")), textFormat));
            sheet.setColumnView(19, 15);
            sheet.addCell(new Label(19, temp, CommUtil.getStringValue(map.get("amount")),
                    textFormat));
            sheet.setColumnView(20, 15);
            sheet.addCell(new Label(20, temp, CommUtil.getStringValue(map.get("nonTaxPrice")),
                    textFormat));
            sheet.setColumnView(21, 15);
            sheet.addCell(new Label(21, temp, CommUtil.getStringValue(map.get("nonTaxAmount")),
                    textFormat));
            sheet.setColumnView(22, 15);
            sheet.addCell(new Label(22, temp, CommUtil.getStringValue(map.get("taxAmount")),
                    textFormat));
            sheet.setColumnView(23, 15);
            sheet.addCell(new Label(23, temp, CommUtil.getStringValue(map.get("taxRate")),
                    textFormat));
            sheet.setColumnView(24, 15);
            sheet.addCell(new Label(24, temp,
                    CommUtil.getStringValue(map.get("energySavingAmount")), textFormat));
            //发票类型转化为名称('1': '增值税发票';'2':'普通发票';)
            String type = CommUtil.getStringValue(map.get("type"));
            if ("1".equals(type)) {
                type = "增值税发票";
            } else if ("2".equals(type)) {
                type = "普通发票";
            }
            sheet.setColumnView(25, 15);
            sheet.addCell(new Label(25, temp, type, textFormat));
            //isTogether转化为名称（"是"，"否"）
            String isTogether = "1".equals(CommUtil.getStringValue(map.get("isTogether"))) ? "是"
                    : "否";
            sheet.setColumnView(26, 15);
            sheet.addCell(new Label(26, temp, isTogether, textFormat));
            //发票状态转化为名称（"0":'待开票',"1"'开票中',"4":'已开票',"5":'已取消开票'）
            String state = CommUtil.getStringValue(map.get("state"));
            if ("0".equals(state)) {
                state = "待开票";
            } else if ("1".equals(state)) {
                state = "开票中";
            } else if ("4".equals(state)) {
                state = "已开票";
            } else if ("5".equals(state)) {
                state = "已取消开票";
            }
            sheet.setColumnView(27, 15);
            sheet.addCell(new Label(27, temp, state, textFormat));
            sheet.setColumnView(28, 15);
            sheet.addCell(new Label(28, temp, CommUtil.getStringValue(map.get("paymentName")),
                    textFormat));
            sheet.setColumnView(29, 15);
            sheet.addCell(new Label(29, temp, CommUtil.getStringValue(map.get("billingTime")),
                    textFormat));
            sheet.setColumnView(30, 15);
            sheet.addCell(new Label(30, temp, CommUtil.getStringValue(map.get("drawer")),
                    textFormat));
            //开票状态转化为名称 '': '正常',  "3": '当月作废', "4":'跨月冲红'
            String eaiWriteState = CommUtil.getStringValue(map.get("eaiWriteState"));
            if ("".equals(eaiWriteState)) {
                eaiWriteState = "正常";
            } else if ("3".equals(eaiWriteState)) {
                eaiWriteState = "当月作废";
            } else if ("4".equals(eaiWriteState)) {
                eaiWriteState = "跨月冲红";
            }
            sheet.setColumnView(31, 15);
            sheet.addCell(new Label(31, temp, eaiWriteState, textFormat));
            sheet.setColumnView(32, 15);
            sheet.addCell(new Label(32, temp, CommUtil.getStringValue(map.get("invalidTime")),
                    textFormat));
            sheet.setColumnView(33, 15);
            sheet.addCell(new Label(33, temp, CommUtil.getStringValue(map.get("firstPushTime")),
                    textFormat));
            sheet.setColumnView(34, 15);
            sheet.addCell(new Label(34, temp, CommUtil.getStringValue(map.get("lastModifyTime")),
                    textFormat));
            sheet.setColumnView(35, 15);
            sheet.addCell(new Label(35, temp, CommUtil.getStringValue(map.get("consignee")),
                    textFormat));
            sheet.setColumnView(36, 15);
            sheet.addCell(new Label(36, temp, CommUtil.getStringValue(map.get("regionName")),
                    textFormat));
            sheet.setColumnView(37, 15);
            sheet.addCell(new Label(37, temp, CommUtil.getStringValue(map.get("address")),
                    textFormat));
            sheet.setColumnView(38, 15);
            sheet.addCell(new Label(38, temp, CommUtil.getStringValue(map.get("mobile")),
                    textFormat));
            sheet.setColumnView(39, 15);
            sheet
                    .addCell(new Label(39, temp, CommUtil.getStringValue(map.get("phone")), textFormat));
            sheet.setColumnView(40, 15);
            //订单备注处理
            String orderRemark = CommUtil.getStringValue(map.get("orderRemark")).replace("\n", "")
                    .replace("\t", "").replace("\r", "").replace("\r\n", "");
            sheet.addCell(new Label(40, temp, orderRemark, textFormat));
            //税控号码处理
            String[] invoiceNumbers = CommUtil.getStringValue(map.get("invoiceNumber")).split(" ");
            String invoiceNumber = "";
            int k=0;
            for (String str : invoiceNumbers) {
                if(k==0){
                    invoiceNumber=str;
                }else {
                    invoiceNumber += "'" + str + '\t';
                }
                k++;
            }
            sheet.setColumnView(41, 15);
            sheet.addCell(new Label(41, temp, invoiceNumber, textFormat));
            temp++;
        }
    }

    /**
     * 导出具体数据，实现回调函数
     * @param sheet
     * @param temp 行号
     * @param list 传入需要导出的 list
     * @throws WriteException
     */
    private void setExportMemberInvoiceList(WritableSheet sheet, int temp,
                                            List<MemberInvoicesDispItem> list) throws Exception {
        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat textFormat = new WritableCellFormat(font);
        textFormat.setAlignment(Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        DisplayFormat displayFormat = NumberFormats.INTEGER;
        WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
        numberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        int index = 0;
        for (MemberInvoicesDispItem memberInvoices : list) {
            index++;
            //jxl.write.Number(列号,行号 ,内容 )
            /*
             *  订单号,"会员名称","发票类型","发票抬头","纳税人识别号","注册地址","注册电话","开户行",
             *  "银行卡号","添加时间","审核状态","审核时间","审核人","是否锁定"
             */
            sheet.setColumnView(0, 10);
            sheet.addCell(new Label(0, temp, CommUtil.getStringValue(index), textFormat));
            sheet.setColumnView(1, 25);
            sheet.addCell(new Label(1, temp, CommUtil.getStringValue(memberInvoices.getOrderSn()),
                    textFormat));
            sheet.setColumnView(2, 25);
            sheet.addCell(new Label(2, temp,
                    CommUtil.getStringValue(memberInvoices.getMemberName()), textFormat));
            //发票类型转化为名称('1': '增值税发票';'2':'普通发票';'3':'增值税发票（普）';)
            String invoiceType = CommUtil.getStringValue(memberInvoices.getInvoiceType());
            if ("1".equals(invoiceType)) {
                invoiceType = "增值税发票";
            } else if ("2".equals(invoiceType)) {
                invoiceType = "普通发票";
            } else if ("3".equals(invoiceType)) {
                invoiceType = "增值税发票（普）";
            }
            sheet.setColumnView(3, 25);
            sheet.addCell(new Label(3, temp, invoiceType, textFormat));
            sheet.setColumnView(4, 25);
            sheet.addCell(new Label(4, temp, CommUtil.getStringValue(memberInvoices
                    .getInvoiceTitle()), textFormat));
            sheet.setColumnView(5, 25);
            sheet.addCell(new Label(5, temp, CommUtil.getStringValue(memberInvoices
                    .getTaxPayerNumber()), textFormat));
            sheet.setColumnView(6, 25);
            sheet.addCell(new Label(6, temp, CommUtil.getStringValue(memberInvoices
                    .getRegisterAddress()), textFormat));
            sheet.setColumnView(7, 25);
            sheet.addCell(new Label(7, temp, CommUtil.getStringValue(memberInvoices
                    .getRegisterPhone()), textFormat));
            sheet.setColumnView(8, 25);
            sheet.addCell(new Label(8, temp, CommUtil.getStringValue(memberInvoices.getBankName()),
                    textFormat));
            sheet.setColumnView(9, 25);
            sheet.addCell(new Label(9, temp, CommUtil.getStringValue(memberInvoices
                    .getBankCardNumber()), textFormat));
            sheet.setColumnView(10, 25);
            sheet.addCell(new Label(10, temp, CommUtil.getStringValue(memberInvoices.getAddTime()),
                    textFormat));
            //是否锁定 0未锁定 ；1锁定
            String state = CommUtil.getStringValue(memberInvoices.getState());
            if ("0".equals(state)) {
                state = "待审核";
            } else if ("1".equals(state)) {
                state = "审核通过";
            } else if ("2".equals(state)) {
                state = "拒绝";
            }
            sheet.setColumnView(11, 25);
            sheet.addCell(new Label(11, temp, state, textFormat));
            sheet.setColumnView(12, 25);
            sheet.addCell(new Label(12, temp,
                    CommUtil.getStringValue(memberInvoices.getAuditTime()), textFormat));
            sheet.setColumnView(13, 25);
            sheet.addCell(new Label(13, temp, CommUtil.getStringValue(memberInvoices.getAuditor()),
                    textFormat));
            String isLock = CommUtil.getStringValue(memberInvoices.getIsLock());
            //是否锁定 0未锁定 ；1锁定
            if ("1".equals(isLock)) {
                isLock = "锁定";
            } else if ("0".equals(isLock)) {
                isLock = "未锁定";
            }
            sheet.setColumnView(14, 25);
            sheet.addCell(new Label(14, temp, isLock, textFormat));
            sheet.setColumnView(15, 25);
            sheet.addCell(new Label(15, temp, CommUtil.getStringValue(memberInvoices.getMessage()), textFormat));
            temp++;
        }
    }

    /**
     * 网单添加渠道编码和客户码
     * @param list
     */
    private void addChannelAndCustomerCode(List<Map<String, Object>> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        String cOrderSn;
        String source;//订单来源
        for (Map<String, Object> map : list) {
            try {
                cOrderSn = (String) map.get("cOrderSn");
                source = (String) map.get("source");
                Map<String, String> customerMap = getCustomerCodeService.getCustomerCode(cOrderSn);
                map.put("sapChannelCode", invoiceCommonService.getSapChannelCode(cOrderSn,source));
                String message = customerMap.get("Message");
                if (null == message) {
                    map.put("customerCode", customerMap.get("code"));
                } else {
                    map.put("customerCode", message);
                }

            } catch (Exception e) {
                map.put("customerCode", "Exception:获取客户码失败！");
                map.put("sapChannelCode", "Exception:获取SAP渠道编码失败！" );
                logger.error("获取客户码、SAP渠道编码失败!异常：", e);
            }
        }
    }
    
    /**
    //TODO 批量共享开票
    * @param request
    * @param response
    * @param modelMap
    * @author ysh
    * @return
    */
   @RequestMapping(value = { "batchShareInvoicePage" }, method = { RequestMethod.GET })
   String batchShareInvoicePage(HttpServletRequest request, HttpServletResponse response,
                               Map<String, Object> modelMap) {
       return "invoice/batchShareInvoiceInfo";
   }
   /**
   //TODO 批量审核订单发票
   * @param request
   * @param response
   * @param modelMap
   * @author ysh
   * @return
   */
  @RequestMapping(value = { "batchAuditingOrderInvoicePage" }, method = { RequestMethod.GET })
  String batchAuditingOrderInvoicePage(HttpServletRequest request, HttpServletResponse response,
                              Map<String, Object> modelMap) {
      return "invoice/batchAuditingOrderInvoiceInfo";
  }
   /**
    //TODO 批量共享开票
    * @param cOrderSns
    * @param modelMap
    * @param request
    * @param response
    * @return
    */
   @RequestMapping(value = { "doBatchShareInvoice" }, method = { RequestMethod.POST })
   @ResponseBody
   public HttpJsonResult<Map<String, Object>> doBatchShareInvoice(@RequestParam(required = true) String cOrderSns,
                                                                   Map<String, Object> modelMap,
                                                                   HttpServletRequest request,
                                                                   HttpServletResponse response) {
       response.setCharacterEncoding("UTF-8");
       HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
       if (StringUtil.isEmpty(cOrderSns, true)) {
           result.setMessage("请提交有效的网单编号！");
           return result;
       }
       System.out.println(cOrderSns);
       try {
           result = this.invoiceCommonService.doBatchShareInvoice(cOrderSns, modelMap);
       } catch (Exception e) {
           logger.error("[invoice][doBatchShareInvoice]批量共享开票时发生未知错误", e);
           result.setMessage("批量共享开票失败！");
       }
       return result;
   }
   /**
   //TODO 批量审核订单发票
   * @param cOrderSns
   * @param modelMap
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = { "doBatchAuditingOrderInvoice" }, method = { RequestMethod.POST })
  @ResponseBody
  public HttpJsonResult<Map<String, Object>> doBatchAuditingOrderInvoice(@RequestParam(required = true) String cOrderSns,
                                                                  Map<String, Object> modelMap,
                                                                  HttpServletRequest request,
                                                                  HttpServletResponse response) {
	  HttpSession session = request.getSession();
	  String userName = Ustring.getString(session.getAttribute("userName"));
      response.setCharacterEncoding("UTF-8");
      HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
      if (StringUtil.isEmpty(cOrderSns, true)) {
          result.setMessage("请提交有效的订单编号！");
          return result;
      }
      try {
          result = this.invoiceCommonService.doBatchAuditingOrderInvoice(cOrderSns, modelMap,userName);
      } catch (Exception e) {
          logger.error("[invoice][doBatchUpdateTaxInfo]批量审核订单发票时发生未知错误", e);
      }
      return result;
  }
}
