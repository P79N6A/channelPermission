package com.haier.svc.api.controller.settleCenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.invoice.service.DistributionDetailsService;
import com.haier.invoice.service.OdsTMFXPointMaintainService;
import com.haier.shop.model.DistributionDetails;
import com.haier.shop.model.OdsTMFXPointMaintain;
import com.haier.svc.api.controller.excel.ExcelImport;
import com.haier.svc.api.controller.excel.ExcelInfo;
import com.haier.svc.api.controller.settleCenter.excel.ExportExcelPointMaintainTemplate;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value = "/distributionDetails")
public class DistributionDetailsController {
    private static Logger     log = LogManager.getLogger(DistributionDetailsController.class);

    @Resource
    private DistributionDetailsService distributionDetailsService;

    @GetMapping("page")
    public String query(){
        return "settleCenter/distributionDetails";
    }


    @RequestMapping(value ="getList")
    @ResponseBody
    public String getList(DistributionDetails param, Integer page, Integer rows) {
        if (page == null) {
            page = 1;
        }
        rows = rows == null ? 50 : rows;
        PagerInfo pager = new PagerInfo(rows, page);
        JSONObject jsonObject = distributionDetailsService.paging(param, pager);
        return jsonObject.toString();
    }


    @ResponseBody
    @RequestMapping(value="/export",method=RequestMethod.GET)
    public void exportLPTN(OdsTMFXPointMaintain odsTMFXPointMaintain, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String fileName ="返利政策点位.xlsx";

        fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        ExportExcelPointMaintainTemplate exportExcelPointMaintainTemplate = new ExportExcelPointMaintainTemplate();
        exportExcelPointMaintainTemplate.doExport(response,fileName,odsTMFXPointMaintain);

    }


}
