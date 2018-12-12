package com.haier.svc.api.controller.invoice;

import com.haier.invoice.service.BatchBillEInvoiceService;
import com.haier.invoice.service.BatchInvalidEInvoiceService;
import com.haier.invoice.service.BatchRebillEInvoiceService;
import com.haier.invoice.util.HttpJsonResult;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 批量操作电子发票（开票、作废、重开）
 * @author lichunsheng
 * @create 2018-01-10
 **/
@Controller
@RequestMapping(value = "invoice/")
public class BatchOperateEInvoiceController {

    private static final Logger logger = LogManager.getLogger(BatchOperateEInvoiceController.class);

    @Autowired
    private BatchBillEInvoiceService batchBillEInvoiceService;
    @Autowired
    private BatchInvalidEInvoiceService batchInvalidEInvoiceService;
    @Autowired
    private BatchRebillEInvoiceService batchRebillEInvoiceService;

    /**
     * 电子发票批量开票跳转界面
     * @param level
     * @param modelMap
     * @return
     */
    @RequestMapping({"eInvoiceBatchBillingLoad"})
    String eInvoiceBatchBillingLoad(@RequestParam(required=false) Integer level, Map<String, Object> modelMap)
    {
        return "invoice/eInvoiceBatchBilling";
    }

    @RequestMapping(value={"eInvoiceBatchBilling"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    HttpJsonResult<Map<String, Object>> eInvoiceBatchBilling(@RequestParam(required=false) String cOrderSns_invoiceQueue, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ParseException
    {
        response.setCharacterEncoding("UTF-8");
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult();

        try
        {
            cOrderSns_invoiceQueue = (cOrderSns_invoiceQueue == null) || ("".equals(cOrderSns_invoiceQueue.trim())) ? null : cOrderSns_invoiceQueue.trim();

            if (cOrderSns_invoiceQueue == null) {
                result.setMessage("网单号不允许为空！");
                return result;
            }

            String[] totalArray = cOrderSns_invoiceQueue.replace("\r", ",").replace("\n", ",").replaceAll("(,)+", ",").replace(" ", "").split(",");
            Set<String> set = new HashSet(Arrays.asList(totalArray));
            totalArray = (String[])set.toArray(new String[0]);

            String serviceResult = this.batchBillEInvoiceService.batchBillEInvoice(totalArray);
            result.setMessage(serviceResult);
        } catch (Exception e) {
            logger.error("[eInvoiceBatchBilling]更新Invoices[电子发票批量开票]时发生未知错误", e);
            result.setMessage("更新失败！");
            return result;
        }
        return result;
    }

    /**
     * 电子发票批量作废跳转界面
     * @param level
     * @param modelMap
     * @return
     */
    @RequestMapping({"eInvoiceBatchInvalidLoad"})
    String eInvoiceBatchInvalidLoad(@RequestParam(required=false) Integer level, Map<String, Object> modelMap)
    {
        return "invoice/eInvoiceBatchInvalid";
    }

    /**
     * 电子发票批量作废
     * @param cOrderSns_invoices
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping(value={"eInvoiceBatchInvalid"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    HttpJsonResult<Map<String, Object>> eInvoiceBatchInvalid(@RequestParam(required=false) String cOrderSns_invoices, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ParseException
    {
        response.setCharacterEncoding("UTF-8");
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult();
        try
        {
            cOrderSns_invoices = (cOrderSns_invoices == null) || ("".equals(cOrderSns_invoices.trim())) ? null : cOrderSns_invoices.trim();
            if (cOrderSns_invoices == null) {
                result.setMessage("网单号不允许为空！");
                return result;
            }
            String[] totalArray = cOrderSns_invoices.replace("\r", ",").replace("\n", ",").replaceAll("(,)+", ",").replace(" ", "").split(",");
            Set<String> set = new HashSet(Arrays.asList(totalArray));
            totalArray = (String[])set.toArray(new String[0]);

            String serviceResult = this.batchInvalidEInvoiceService.batchInvalidEInvoice(totalArray);
            result.setMessage(serviceResult);
        } catch (Exception e) {
            logger.error("[eInvoiceBatchInvalid]更新Invoices[电子发票批量作废]时发生未知错误", e);
            result.setMessage("更新失败！");
            return result;
        }
        return result;
    }

    /**
     * 电子发票批量重开跳转界面
     * @param level
     * @param modelMap
     * @return
     */
    @RequestMapping({"eInvoiceBatchReBillingLoad"})
    String eInvoiceBatchReBillingLoad(@RequestParam(required=false) Integer level, Map<String, Object> modelMap)
    {
        return "invoice/eInvoiceBatchReBilling";
    }

    @RequestMapping(value={"eInvoiceBatchReBilling"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
    @ResponseBody
    HttpJsonResult<Map<String, Object>> eInvoiceBatchReBilling(@RequestParam(required=false) String cOrderSns_invoices, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ParseException
    {
        response.setCharacterEncoding("UTF-8");
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult();
        try
        {
            cOrderSns_invoices = (cOrderSns_invoices == null) || ("".equals(cOrderSns_invoices.trim())) ? null : cOrderSns_invoices.trim();
            if (cOrderSns_invoices == null) {
                result.setMessage("网单号不允许为空！");
                return result;
            }
            String[] totalArray = cOrderSns_invoices.replace("\r", ",").replace("\n", ",").replaceAll("(,)+", ",").replace(" ", "").split(",");
            Set<String> set = new HashSet(Arrays.asList(totalArray));
            totalArray = (String[])set.toArray(new String[0]);

            String serviceResult = this.batchRebillEInvoiceService.batchRebillEInvoice(totalArray);
            result.setMessage(serviceResult);
        } catch (Exception e) {
            logger.error("[eInvoiceBatchReBilling]更新Invoices[电子发票批量重推开票]时发生未知错误", e);
            result.setMessage("更新失败！");
            return result;
        }
        return result;
    }
}
