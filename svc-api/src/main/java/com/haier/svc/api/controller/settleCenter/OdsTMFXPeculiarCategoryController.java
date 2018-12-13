package com.haier.svc.api.controller.settleCenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.invoice.service.OdsTMFXPeculiarCategoryService;
import com.haier.shop.model.OdsTMFXPeculiarCategory;
import com.haier.shop.model.OdsTMFXPointMaintain;
import com.haier.svc.api.controller.settleCenter.excel.ExportExcelPeculiarCategoryTemplate;
import com.haier.svc.api.controller.settleCenter.excel.ExportExcelSettlementInvoiceTemplate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value = "/peculiarCategory")
public class OdsTMFXPeculiarCategoryController {
    private static Logger     log = LogManager.getLogger(OdsTMFXPeculiarCategoryController.class);

    @Resource
    private OdsTMFXPeculiarCategoryService odsTMFXPeculiarCategorySerivce;

    @GetMapping("page")
    public String query(){
        return "settleCenter/pecularCategory";
    }


    @RequestMapping(value ="getList")
    @ResponseBody
    public String getList(OdsTMFXPeculiarCategory odsTMFXPeculiarCategory, Integer page, Integer rows) {
        if (page == null) {
            page = 1;
        }
        rows = rows == null ? 50 : rows;
        PagerInfo pager = new PagerInfo(rows, page);

        JSONObject jsonObject = odsTMFXPeculiarCategorySerivce.paging(odsTMFXPeculiarCategory, pager);
        return jsonObject.toString();
    }





    @ResponseBody
    @RequestMapping(value="/export",method=RequestMethod.GET)
    public void exportLPTN(OdsTMFXPeculiarCategory odsTMFXPeculiarCategory, HttpServletRequest request, HttpServletResponse response) throws IOException {


        String fileName ="peculiarCategory.xlsx";

        fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        ExportExcelPeculiarCategoryTemplate exportExcelPeculiarCategoryTemplate = new ExportExcelPeculiarCategoryTemplate();
        exportExcelPeculiarCategoryTemplate.doExport(response,fileName,odsTMFXPeculiarCategory);

    }



    @PostMapping("insert")
    @ResponseBody
    public Object insert(HttpServletRequest request, OdsTMFXPeculiarCategory odsTMFXPeculiarCategory) {
        ServiceResult<JSONObject> result = new ServiceResult<>();
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");

        try {

            odsTMFXPeculiarCategory.setCreateBy(userName);
            odsTMFXPeculiarCategory.setCreateTime(new Date());
            result = odsTMFXPeculiarCategorySerivce.insert(odsTMFXPeculiarCategory);


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
            result = odsTMFXPeculiarCategorySerivce.delBatch(list);

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("操作失败");
        }

        return JsonUtil.toJson(result);
    }

}
