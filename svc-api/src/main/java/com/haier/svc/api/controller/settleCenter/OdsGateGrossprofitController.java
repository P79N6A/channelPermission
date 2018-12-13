package com.haier.svc.api.controller.settleCenter;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.invoice.service.OdsGateGrossprofitService;
import com.haier.shop.dto.OdsGateGrossprofitDto;
import com.haier.shop.model.OdsGateGrossprofit;
import com.haier.svc.api.controller.excel.ExcelImport;
import com.haier.svc.api.controller.excel.ExcelInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/6/26.
 */
@Controller
@RequestMapping("/GateGrossprofit")
public class OdsGateGrossprofitController {
    private static Logger log = LogManager.getLogger(OdsGateGrossprofitController.class);

    @Autowired
    private OdsGateGrossprofitService odsGateGrossprofitService;

    @GetMapping("page")
    public String query(){
        return "settleCenter/GateGrossprofit";
    }

    @RequestMapping(value ="getList")
    @ResponseBody
    public String getList(OdsGateGrossprofitDto param, Integer page, Integer rows) {
        if (page == null) {
            page = 1;
        }
        rows = rows == null ? 50 : rows;
        PagerInfo pager = new PagerInfo(rows, page);
        JSONObject jsonObject = odsGateGrossprofitService.paging(param, pager);
        return jsonObject.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/getBrands")
    public Object getBrands() {
        return odsGateGrossprofitService.getBrands();
    }

    @ResponseBody
    @RequestMapping(value = "/getCateName")
    public Object getCateName() {
        return odsGateGrossprofitService.selectCateName();
    }


    @PostMapping("insertOrUpdate")
    @ResponseBody
    public Object insert(HttpServletRequest request, OdsGateGrossprofit odsGateGrossprofit) {
        ServiceResult<JSONObject> result = new ServiceResult<>();
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");

        InetAddress ia=null;
        try {
            ia=ia.getLocalHost();
            String localip=ia.getHostAddress();

            if(odsGateGrossprofit.getId()!=null && !"".equals(odsGateGrossprofit.getId())){
                odsGateGrossprofit.setUpdateBy(userName);
                odsGateGrossprofit.setIp(localip);
                result = odsGateGrossprofitService.update(odsGateGrossprofit);
            }else {
                odsGateGrossprofit.setCreateBy(userName);
                odsGateGrossprofit.setIp(localip);
                result = odsGateGrossprofitService.insert(odsGateGrossprofit);
            }

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("操作失败");
        }

        return JsonUtil.toJson(result);
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
            serviceResult = odsGateGrossprofitService.checkInfo(excelInfo.getBobyInfo());
            if(serviceResult.getSuccess()){
                serviceResult = odsGateGrossprofitService.execExcel(excelInfo.getBobyInfo(),userName);
            }else {
                serviceResult.setSuccess(false);
                serviceResult.setMessage(result.getMessage());
            }

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





}
