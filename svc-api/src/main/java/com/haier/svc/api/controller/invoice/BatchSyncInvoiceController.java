package com.haier.svc.api.controller.invoice;

import com.haier.common.util.StringUtil;
import com.haier.invoice.service.BatchUpdateEInvoiceService;
import com.haier.invoice.service.BatchUpdateTaxInfoService;
import com.haier.invoice.util.HttpJsonResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 批量更新电子、金税发票
 * @author lichunsheng
 * @create 2018-01-10
 **/
@Controller
@RequestMapping(value = "invoice/")
public class BatchSyncInvoiceController {

    private static final Logger logger = LogManager.getLogger(BatchSyncInvoiceController.class);

    @Autowired
    private BatchUpdateEInvoiceService batchUpdateEInvoiceService;
    @Autowired
    private BatchUpdateTaxInfoService batchUpdateTaxInfoService;

    /**
     * 批量同步金税发票跳转界面
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "batchNewPage" }, method = { RequestMethod.GET })
    String batchNewPage(HttpServletRequest request, HttpServletResponse response,
                        Map<String, Object> modelMap) {
        return "invoice/batchStatusInfo";
    }

    /**
     * 批量同步电子发票跳转界面
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "batchEInvoiceNewPage" }, method = { RequestMethod.GET })
    String batchEInvoiceNewPage(HttpServletRequest request, HttpServletResponse response,
                                Map<String, Object> modelMap) {
        modelMap.put("nodeType", "1");
        return "invoice/batchStatusInfo";
    }

    /**
     * 批量同步电子发票
     * @param cOrderSns
     * @param modelMap
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "doBatchUpdateEInvoiceInfo" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Map<String, Object>> doBatchUpdateEInvoiceInfo(@RequestParam(required = true) String cOrderSns,
                                                                         Map<String, Object> modelMap,
                                                                         HttpServletRequest request,
                                                                         HttpServletResponse response) {
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult();
        if (StringUtil.isEmpty(cOrderSns, true)) {
            result.setMessage("请提交有效的订单编号！");
            return result;
        }
        try {
            result = batchUpdateEInvoiceService.doBatchUpdateEInvoiceInfo(cOrderSns, modelMap);


        } catch (Exception e) {
            logger.error("[invoice][doBatchUpdateEInvoiceInfo]批量同步电子发票状态时发生未知错误", e);
            result.setMessage("批量同步电子发票状态失败！");
        }
        return result;
    }

    /**
     * 批量同步金税发票
     * @param cOrderSns
     * @param modelMap
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "doBatchUpdateTaxInfo" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Map<String, Object>> doBatchUpdateTaxInfo(@RequestParam(required = true) String cOrderSns,
                                                                    Map<String, Object> modelMap,
                                                                    HttpServletRequest request,
                                                                    HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult();
        if (StringUtil.isEmpty(cOrderSns, true)) {
            result.setMessage("请提交有效的订单编号！");
            return result;
        }
        try {
            result = this.batchUpdateTaxInfoService.doBatchUpdateTaxInfo(cOrderSns, modelMap);
        } catch (Exception e) {
            logger.error("[invoice][doBatchUpdateTaxInfo]批量同步金税发票状态时发生未知错误", e);
            result.setMessage("批量同步金税发票状态失败！");
        }
        return result;
    }
}
