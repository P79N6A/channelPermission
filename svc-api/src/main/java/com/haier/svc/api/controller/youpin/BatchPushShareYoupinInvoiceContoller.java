package com.haier.svc.api.controller.youpin;

import com.haier.common.util.StringUtil;
import com.haier.invoice.service.InvoiceCommonService;
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
 * 批量推送共享优品发票
 * @Author:JinXueqian
 * @Date: 2018/8/21 11:09
 */
@Controller
@RequestMapping("youpin")
public class BatchPushShareYoupinInvoiceContoller {

    private static final Logger               logger = LogManager.getLogger(BatchPushShareYoupinInvoiceContoller.class);
    @Autowired
    private              InvoiceCommonService invoiceCommonService;

    /**
     * 页面跳转
     * @return
     */
    @RequestMapping("/loadBatchPushShareYoupinInvoicePage.html")
    public String loadBatchPushShareYoupinInvoicePage() {
        return "youpin/batchPushShareYoupinInvoice";
    }

    /**
     * 批量推送共享优品发票
     * @param
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = { "doBatchPushShareYoupinInvoice" }, method = {RequestMethod.POST})
    @ResponseBody
    public HttpJsonResult<Map<String, Object>> doBatchPushShareYoupinInvoice(@RequestParam(required = true) String line_nums,
                                                                             HttpServletRequest request, HttpServletResponse response) {

        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        try {
           if (StringUtil.isEmpty(line_nums.replace("\r\n", ""), true)) {
               response.setContentType("text/html;charset=UTF-8");
               result.setMessage("请提交有效的调拨网单号码！");
               return result;
           }
           result = this.invoiceCommonService.doBatchPushShareYoupinInvoice(line_nums);
       }catch (Exception e){
           e.printStackTrace();
           logger.error("批量推送共享优品发票失败",e);
           result.setMessage("批量推送共享优品发票失败！");
       }
       return result;
    }
}
