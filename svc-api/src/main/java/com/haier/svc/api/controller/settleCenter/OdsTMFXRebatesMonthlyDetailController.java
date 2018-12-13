package com.haier.svc.api.controller.settleCenter;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.invoice.service.OdsTMFXRebatesMonthlyDetailService;
import com.haier.shop.model.OdsTMFXPointMaintain;
import com.haier.shop.model.OdsTMFXRebatesMonthlyDetail;
import com.haier.svc.api.controller.settleCenter.excel.ExportExcelRebatesMonthlyDetailTemplate;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@RequestMapping(value = "/rebatesMonthlyDetail")
public class OdsTMFXRebatesMonthlyDetailController {
    private static Logger     log = LogManager.getLogger(OdsTMFXRebatesMonthlyDetailController.class);

    @Resource
    private OdsTMFXRebatesMonthlyDetailService odsTMFXRebatesMonthlyDetailService;

    @GetMapping("page")
    public String query(){
        return "settleCenter/rebatesMonthlyDetail";
    }


    @RequestMapping(value ="getList")
    @ResponseBody
    public String getList(OdsTMFXRebatesMonthlyDetail param, Integer page, Integer rows) {
        if (page == null) {
            page = 1;
        }
        rows = rows == null ? 50 : rows;
        PagerInfo pager = new PagerInfo(rows, page);
        JSONObject jsonObject = odsTMFXRebatesMonthlyDetailService.paging(param, pager);
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

        JSONObject resultJson = odsTMFXRebatesMonthlyDetailService.actionToCreateData(year, month, type, flag);
        return resultJson.toString();

    }


    @ResponseBody
    @RequestMapping(value="/export",method=RequestMethod.GET)
    public void exportLPTN(OdsTMFXRebatesMonthlyDetail odsTMFXRebatesMonthlyDetail, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String fileName ="返利月度明细.xlsx";

        fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        ExportExcelRebatesMonthlyDetailTemplate exportExcelRebatesMonthlyDetailTemplate = new ExportExcelRebatesMonthlyDetailTemplate();
        exportExcelRebatesMonthlyDetailTemplate.doExport(response,fileName,odsTMFXRebatesMonthlyDetail);

    }


}
