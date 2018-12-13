package com.haier.svc.api.controller.settleCenter;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.invoice.service.SNOdsTMFXPointMaintainService;
import com.haier.shop.model.SNOdsTMFXPointMaintain;
import com.haier.svc.api.controller.excel.ExcelImport;
import com.haier.svc.api.controller.excel.ExcelInfo;
import com.haier.svc.api.controller.settleCenter.excel.ExportExcelPointSNMaintainTemplate;


@Controller
@RequestMapping(value = "/SNpointMaintain")
public class SNOdsTMFXPointMaintainController {
    private static Logger     log = LogManager.getLogger(SNOdsTMFXPointMaintainController.class);

    @Resource
    private SNOdsTMFXPointMaintainService sNOdsTMFXPointMaintainService;

    @GetMapping("page")
    public String query(){
        return "settleCenter/SNpointMaintain";
    }


    @RequestMapping(value ="getList")
    @ResponseBody
    public String getList(SNOdsTMFXPointMaintain param, Integer page, Integer rows) {
        if (page == null) {
            page = 1;
        }
        rows = rows == null ? 50 : rows;
        PagerInfo pager = new PagerInfo(rows, page);
        JSONObject jsonObject = sNOdsTMFXPointMaintainService.paging(param, pager);
        return jsonObject.toString();
    }

    /**
     * 获取最后一次执行计算日志
     * @param request
     * @return
     */
    @RequestMapping(value = { "/getLogInfo" })
    @ResponseBody
    public String getLogInfo( HttpServletRequest request){
        String logInfo = sNOdsTMFXPointMaintainService.getLogInfo();
        return logInfo;
    }


    @PostMapping("insertOrUpdate")
    @ResponseBody
    public Object insert(HttpServletRequest request, SNOdsTMFXPointMaintain odsTMFXPointMaintain) {
        ServiceResult<JSONObject> result = new ServiceResult<>();
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");

        try {
            if(odsTMFXPointMaintain.getId()==0){
                odsTMFXPointMaintain.setCreateBy(userName);
                odsTMFXPointMaintain.setDeleteTab("N");
                odsTMFXPointMaintain.setAuditState("B");
                result = sNOdsTMFXPointMaintainService.insert(odsTMFXPointMaintain);
            }else {
                result = sNOdsTMFXPointMaintainService.update(odsTMFXPointMaintain);
            }

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("操作失败");
        }

        return JsonUtil.toJson(result);
    }


    @PostMapping("delBatch")
    @ResponseBody
    public Object delBatch(HttpServletRequest request,String ids) {
        ServiceResult<JSONObject> result = new ServiceResult<>();
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");

        try {
            List<String> list = JSON.parseArray(ids, String.class);
            result = sNOdsTMFXPointMaintainService.delBatch(list);

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("操作失败");
        }

        return JsonUtil.toJson(result);
    }

    @ResponseBody
    @RequestMapping(value="/export",method=RequestMethod.GET)
    public void exportLPTN(SNOdsTMFXPointMaintain odsTMFXPointMaintain, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String fileName ="苏宁返利政策点位.xlsx";

        fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        ExportExcelPointSNMaintainTemplate exportExcelPointMaintainTemplate = new ExportExcelPointSNMaintainTemplate();
        exportExcelPointMaintainTemplate.doExport(response,fileName,odsTMFXPointMaintain);

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
            serviceResult = sNOdsTMFXPointMaintainService.checkInfo(excelInfo.getBobyInfo());
            if(serviceResult.getSuccess()){
                serviceResult = sNOdsTMFXPointMaintainService.execExcel(excelInfo.getBobyInfo(),userName);
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

    @RequestMapping(value = { "/downloadInOutTemplate" })
    void downloadTemplate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        OutputStream os = response.getOutputStream();
        try {
            response.reset();
            response.setHeader("Content-Disposition",
                    "attachment; filename=odsTMFXPromotionCost.xlsx");
            response.setContentType("application/octet-stream; charset=utf-8");
            String path = request.getSession().getServletContext()
                    .getRealPath("/xls/odsTMFXPromotionCost.xlsx");
            File file = ResourceUtils.getFile("classpath:xls/odsTMFXPromotionCost.xlsx");
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
    public Object audit(HttpServletRequest request, String ids, String audit) {
        ServiceResult<JSONObject> result = new ServiceResult<>();
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");

        if (audit==null){
            result.setError("error","审核类型不能为空");
            return result;
        }
        String []idArray = ids.split(",");

        try {
            if ("B".equals(audit)){
                //业务审核
                for (String id:idArray){
                    SNOdsTMFXPointMaintain opm = new SNOdsTMFXPointMaintain();
                    opm.setId(Integer.parseInt(id));
                    opm.setAuditState("F");
                    opm.setAuditBy(userName);
                    opm.setAuditTime(new Date());
                    sNOdsTMFXPointMaintainService.update(opm);
                }
            }
            if ("F".equals(audit)){
                //财务审核
                for (String id:idArray){
                    SNOdsTMFXPointMaintain opm = new SNOdsTMFXPointMaintain();
                    opm.setId(Integer.parseInt(id));
                    opm.setAuditState("A");
                    opm.setAuditBy(userName);
                    opm.setAuditTime(new Date());
                    sNOdsTMFXPointMaintainService.update(opm);
                }
            }
            result.setSuccess(true);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("操作失败");
        }

        return JsonUtil.toJson(result);
    }
}
