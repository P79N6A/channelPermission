package com.haier.svc.api.controller.settleCenter;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.invoice.service.OdsGatePriceService;
import com.haier.invoice.util.CommUtil;
import com.haier.invoice.util.ExcelCallbackInterfaceUtil;
import com.haier.invoice.util.ExcelExportUtil;
import com.haier.shop.model.GatePrice;
import com.haier.shop.model.ItemAttribute;
import com.haier.svc.api.controller.excel.ExcelReadHandler;
import com.haier.svc.api.controller.util.HttpJsonResult;
import com.haier.svc.api.controller.util.WebUtil;
import com.haier.svc.api.controller.util.excel.ExcelHandler;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: zsx
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.svc.api.controller.settleCenter
 * @Date: Created in 2018/6/21 11:11
 * @Modified By:
 */
@Controller
@RequestMapping(value = "/gatePrice")
public class OdsGatePriceController {


    @Resource
    private OdsGatePriceService odsGatePriceService;

    @GetMapping("toPage")
    public String toPage() {
        return "settleCenter/gatePrice";
    }



    /***
     * 查询品牌
     * @return
     */
    @RequestMapping(value = { "/queryBrands" }, method = { RequestMethod.GET })
    @ResponseBody
        HttpJsonResult<List<String>> queryBrands(HttpServletRequest request) {
        HttpJsonResult<List<String>> result = new HttpJsonResult<List<String>>();
        ServiceResult<List<String>> queryResult = odsGatePriceService
                .selectBrandsList();
        result.setData(queryResult.getResult());
        return result;
    }

    /***
     * 查询品类
     * @return
     */
    @RequestMapping(value = { "/queryCategory" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<String>> queryCategory(HttpServletRequest request) {
        HttpJsonResult<List<String>> result = new HttpJsonResult<List<String>>();
        ServiceResult<List<String>> queryResult = odsGatePriceService
                .selectCategoryList();
        result.setData(queryResult.getResult());
        return result;
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = { "queryPage" }, method = { RequestMethod.POST })
    void queryPage(@RequestParam(required = false) String brand,
                   @RequestParam(required = false) String cateGory,
                   @RequestParam(required = false) String sku,
                   @RequestParam(required = false) String bigChannel,
                   @RequestParam(required = false) String isValid,
                   @RequestParam(required = false) String beginTime,
                   @RequestParam(required = false) String endTime,
                   @RequestParam(required = false) String frozenFlag,
                   @RequestParam(required = false) String auditStatus,
                   @RequestParam(required = false) String isBigBarePrice,
                   @RequestParam(required = false) String isSmallNormal,
                   @RequestParam(required = false) String isLower,
                   @RequestParam(required = false) String execDaysFrom,
                   @RequestParam(required = false) String execDaysTo,
                   @RequestParam(required = false) Integer rows,
                   @RequestParam(required = false) Integer page,
                   HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        try {
            if (rows == null)
                rows = 50;
            if (page == null)
                page = 1;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("m", (page - 1) * rows);
            params.put("n", rows);
            params.put("userId",getUserName());
            //参数加入params里
            params.put("brand", brand.trim());
            params.put("cateGory", cateGory.trim());
            params.put("sku", sku.trim());
            params.put("bigChannel", bigChannel.trim());
            params.put("isValid", isValid.trim());
            params.put("beginTime", beginTime);
            params.put("endTime", endTime);
            params.put("frozenFlag", frozenFlag);
            params.put("auditStatus", auditStatus);
            params.put("isBigBarePrice", isBigBarePrice);
            params.put("isSmallNormal", isSmallNormal);
            params.put("isLower", isLower);
            params.put("execDaysFrom", execDaysFrom);
            params.put("execDaysTo", execDaysTo);
            Map<String, Object> retMap = odsGatePriceService.queryGatePrice(params);

            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            throw new BusinessException("失败" + e.getMessage());
        }
    }

    /**
     * 修改或者下市
     */
    @PostMapping("updateGatePrice")
    @ResponseBody
    public Object audit(HttpServletRequest request,String id, String lowerStatus,String gatePrice,String tempGatePrice) {
        ServiceResult<String> result = new ServiceResult<>();
        GatePrice gatePricel = new GatePrice();
        gatePricel.setId(id);
        gatePricel.setLowerStatus(lowerStatus);
        gatePricel.setGatePrice(new BigDecimal(gatePrice));
        gatePricel.setTempGatePrice(new BigDecimal(tempGatePrice));
        gatePricel.setIp(getIpAddress(request));
        gatePricel.setCreateBy(getUserName());
        gatePricel.setUpdateBy(getUserName());
        try{
            result= odsGatePriceService.updateGatePrice(gatePricel);
    }catch (Exception e){
        result.setSuccess(false);
        result.setMessage("保存失败"+e.getMessage());
    }
    return result;
    }

    @RequestMapping(value = { "exportGatePriceList" })
    public void export(@RequestParam(required = false) String exportData,
                                 HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, String> map = JsonUtil.fromJson(exportData);
        params.put("userId",getUserName());
                params.put("brand", map.get("brand").trim());
        params.put("cateGory", map.get("cateGory").trim());
        params.put("sku", map.get("sku").trim());
        params.put("bigChannel", map.get("bigChannel").trim());
        params.put("isValid", map.get("isValid").trim());
        params.put("beginTime", map.get("beginTime").trim());
        params.put("endTime", map.get("endTime").trim());
        params.put("frozenFlag", map.get("frozenFlag").trim());
        params.put("auditStatus", map.get("auditStatus").trim());
        params.put("isBigBarePrice", map.get("isBigBarePrice").trim());
        params.put("isSmallNormal", map.get("isSmallNormal").trim());
        params.put("isLower", map.get("isLower").trim());
        params.put("execDaysFrom", map.get("execDaysFrom").trim());
        params.put("execDaysTo", map.get("execDaysTo").trim());
        String ids = request.getParameter("ids");
        if (StringUtils.isNotBlank(ids)) {
            ids = ids.replace("[", "").replace("]", "");
            params.put("ids", ids);
        }
        List<GatePrice> data = odsGatePriceService.getExportGatePriceList(params);
        ExcelHandler eh = new ExcelHandler(GatePrice.class);
        eh.setData(data);
        eh.exportxls(response);
    }
    /**
     * 导入
     */
    @PostMapping("import")
    @ResponseBody
    public Object importExcel(@RequestParam MultipartFile file,HttpServletRequest request) {
        ServiceResult<String> result = new ServiceResult<>();
        ExcelReadHandler reader = new ExcelReadHandler();
        List<GatePrice> data = new ArrayList<>();
        ExcelHandler eh = new ExcelHandler(GatePrice.class);
        try {
            eh.convertToEntity(reader.readExcel(file), data);
        } catch (IOException e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        String userName = getUserName();
        for (GatePrice o : data) {
            o.setCreateBy(userName);
            o.setIp(getIpAddress(request));
        }
        return odsGatePriceService.execExcel(data);
    }

    /**
     * 获取当前登录的用户
     */
    private String getUserName() {
        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        return (String) attr.getRequest().getSession().getAttribute("userName");
    }

    public static String getIpAddress(HttpServletRequest request) {
                 String ip = request.getHeader("x-forwarded-for");
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                        ip = request.getHeader("Proxy-Client-IP");
                     }
                 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                         ip = request.getHeader("WL-Proxy-Client-IP");
                  }
                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                       ip = request.getHeader("HTTP_CLIENT_IP");
                     }
                 if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                         ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                    }
               if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                     ip = request.getRemoteAddr();
        }
                return ip;
             }
}
