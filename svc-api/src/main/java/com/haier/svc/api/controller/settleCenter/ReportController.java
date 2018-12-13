package com.haier.svc.api.controller.settleCenter;

import com.haier.afterSale.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2018/8/29.
 */
@RequestMapping("reportc")
@Controller
public class ReportController {
    @Autowired
    private IReportService ireportService;

    @ResponseBody
    @RequestMapping("port")
    public void report(){
        ireportService.catchKpiReportDetail();
    }
}
