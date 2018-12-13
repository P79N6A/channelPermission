package com.haier.svc.api.controller.settleCenter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.invoice.service.SNOdsTMFXRebatesMonthlyDetailService;
import com.haier.shop.model.SNOdsTMFXRebatesMonthlyDetail;
import com.haier.svc.api.controller.settleCenter.excel.ExportExcelSNRebatesMonthlyDetailTemplate;


@Controller
@RequestMapping(value = "/SNrebatesMonthlyDetail")
public class SNOdsTMFXRebatesMonthlyDetailController {
    private static Logger     log = LogManager.getLogger(SNOdsTMFXRebatesMonthlyDetailController.class);

    @Resource
    private SNOdsTMFXRebatesMonthlyDetailService sNOdsTMFXRebatesMonthlyDetailService;

    @GetMapping("page")
    public String query(){
        return "settleCenter/SNrebatesMonthlyDetail";
    }


    @RequestMapping(value ="getList")
    @ResponseBody
    public String getList(SNOdsTMFXRebatesMonthlyDetail param, Integer page, Integer rows) {
        if (page == null) {
            page = 1;
        }
        rows = rows == null ? 50 : rows;
        PagerInfo pager = new PagerInfo(rows, page);
        JSONObject jsonObject = sNOdsTMFXRebatesMonthlyDetailService.paging(param, pager);
        return jsonObject.toString();
    }

    /**
     * 手动执行计算
     * @param year
     * @param month
     * @param type
     * @param flag
     * @param request
     * @return
     */
    @RequestMapping(value = { "/actionToCreateData" }, method = { RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String selectComment(@RequestParam(value = "year")String year,
                                @RequestParam(value = "month")String month,
                                @RequestParam(value = "type")String type,
                                @RequestParam(value = "flag")String flag,
                                HttpServletRequest request){

        JSONObject resultJson = sNOdsTMFXRebatesMonthlyDetailService.actionToCreateData(year, month, type, flag);
        return resultJson.toString();

    }


    @ResponseBody
    @RequestMapping(value="/export",method=RequestMethod.GET)
    public void exportLPTN(SNOdsTMFXRebatesMonthlyDetail odsTMFXRebatesMonthlyDetail, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String fileName ="苏宁返利月度明细.xlsx";

        fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        ExportExcelSNRebatesMonthlyDetailTemplate exportSNExcelRebatesMonthlyDetailTemplate = new ExportExcelSNRebatesMonthlyDetailTemplate();
        exportSNExcelRebatesMonthlyDetailTemplate.doExport(response,fileName,odsTMFXRebatesMonthlyDetail);

    }


}
