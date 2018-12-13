package com.haier.svc.api.controller.purchase;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.ParseException;
import com.google.gson.Gson;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.model.vehcile.TmypAreaCenterInfo;
import com.haier.svc.api.controller.util.ExportData;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import com.haier.svc.service.TmypAreaCenterInfoService;
import org.apache.log4j.LogManager;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping(value = "/tmypareacenterinfo")
@Controller
public class TmypAreaCenterInfoController {
    private final static org.apache.log4j.Logger logger = LogManager.getLogger(TmypAreaCenterInfoController.class);
    @GetMapping("toPage")
    public String toPage() {
        return "purchase/tmypAreaCenterInfo";
    }

    @Autowired
    private TmypAreaCenterInfoService tmypAreaCenterInfoService;

    /**
     * @param tmypAreaCenterInfo
     * @param rows
     * @param page
     * @param request
     * @param response
     */
    @RequestMapping(value = { "searchAreaCenterInfo" }, method = { RequestMethod.POST })
    public void searchAreaCenterInfo(TmypAreaCenterInfo tmypAreaCenterInfo, @RequestParam(required = false) Integer rows,
                                     @RequestParam(required = false) Integer page,
                                     HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        Map<String, Object> result = new HashMap<String, Object>();
        Map<String, Object> params = new HashMap<String, Object>();

        if (rows == null) {
            rows = 1;
        }
        if (page == null) {
            page = 1;
        }
        params.put("areaCenterInfo", tmypAreaCenterInfo);
        params.put("rows", rows);
        params.put("index", rows * (page - 1));
        List<TmypAreaCenterInfo> userlist = tmypAreaCenterInfoService.getTmypAreaCenterInfo(params);
        for (TmypAreaCenterInfo a : userlist) {
            if ("0".equals(a.getActiveFlag())) {
                a.setActiveFlagName("禁用");
            }
            if ("1".equals(a.getActiveFlag())) {
                a.setActiveFlagName("启用");
            }
            a.setCreateTimeShow(checkData(a.getCreateTime()));
            a.setLastUpdateTimeShow(checkData(a.getLastUpdateTime()));
        }
        result.put("total", tmypAreaCenterInfoService.getTmypAreaCenterInfoCount(params));
        result.put("rows", userlist);
        try {
            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(result));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Title: createWaAddress
     * @Description: 增加数据
     * @param request
     * @param areaCenterInfoDTO
     * @return void
     * @throws
     */
    @RequestMapping(value = { "createAreaCenterInfo" }, method = { RequestMethod.POST })
    public void createAreaCenterInfoDTO(HttpServletRequest request, HttpServletResponse response,
                                        TmypAreaCenterInfo areaCenterInfoDTO) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            Map<String, Object> keyCountMap = new HashMap<String, Object>();
            Map<String, Object> resultMap = new HashMap<String, Object>();

            areaCenterInfoDTO.setCreateBy(getUserName());
            areaCenterInfoDTO.setLastUpdateBy(getUserName());
            areaCenterInfoDTO.setCreateTime(new Date());
            // 创建时主键不能重复，往数据库校验的数据

            TmypAreaCenterInfo waAddressKeyCount = tmypAreaCenterInfoService.getOneByDeliveryToCode(areaCenterInfoDTO.getDeliveryToCode());
            if (waAddressKeyCount == null) {
                tmypAreaCenterInfoService.insertSelective(areaCenterInfoDTO);
                resultMap.put("resultStatus", 1);
            } else {
                resultMap.put("resultStatus", 0);
                logger.error("创建失败，库位码 重复");
            }
            // resultMap是往前台写的校验标志位，1 代表成功创建，0 代表主键重复，创建失败
            response.getWriter().write(JsonUtil.toJson(resultMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @Title: openStatusWaAddress
     * @Description: 数据状态启用
     * @param request
     * @param response
     * @param openData
     * @return void
     * @throws
     */
    @RequestMapping(value = { "openStatusAreaCenterInfo" }, method = { RequestMethod.POST })
    public void openStatusAreaCenterInfo(HttpServletRequest request, HttpServletResponse response,
                                         @RequestParam(required = true) String openData) {
        if (openData != null) {
            try {
                JSONArray openjson = (JSONArray) JSON.parse(openData);
                List<String> openList = new ArrayList<String>();
                Map<String, Object> params = new HashMap<String, Object>();
                for (int i = 0; i < openjson.length(); i++) {
                    openList.add(openjson.getString(i));
                }
                params.put("openList", openList);
                String open_user = this.getUserName();
                params.put("open_user", open_user);
                tmypAreaCenterInfoService.openStatusAreaCenterInfo(params);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * @Title: closeStatusWaAddress
     * @Description: 数据状态禁用
     * @param closeData
     * @param request
     * @param response
     * @return void
     * @throws
     */
    @RequestMapping(value = { "closeStatusAreaCenterInfo" }, method = { RequestMethod.POST })
    public void closeStatusAreaCenterInfo(@RequestParam(required = true) String closeData,
                                          HttpServletRequest request, HttpServletResponse response) {
        if (closeData != null) {
            try {
                JSONArray closejson = (JSONArray) JSON.parse(closeData);
                List<String> closeList = new ArrayList<String>();
                Map<String, Object> params = new HashMap<String, Object>();
                for (int i = 0; i < closejson.length(); i++) {
                    closeList.add(closejson.getString(i));
                }
                params.put("closeList", closeList);
                String close_user = this.getUserName();
                params.put("close_user", close_user);
                tmypAreaCenterInfoService.closeStatusAreaCenterInfo(params);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }
    /**
     * @Title: updateWaAddress
     * @Description: 更新数据
     * @param waAddress
     * @return void
     * @throws
     */
    @RequestMapping(value = { "updateAreaCenterInfo" }, method = { RequestMethod.POST })
    public void updateWaAddress(HttpServletRequest request, HttpServletResponse response,
                                TmypAreaCenterInfo areaCenterInfoDTO) {
        try {
            areaCenterInfoDTO.setLastUpdateBy(getUserName());
            areaCenterInfoDTO.setLastUpdateTime(new Date());
            tmypAreaCenterInfoService.updateSelectiveByDeliveryToCode(areaCenterInfoDTO);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 获取当前登录的用户
     */
    private String getUserName() {
        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        return (String) attr.getRequest().getSession().getAttribute("userName");
    }


    /**
     * @Title: exportAllWaterAppList
     * @Description: 导出全部数据
     * @param response
     * @param request
     * @param modelMap
     * @return String
     * @throws
     */
    @RequestMapping(value = { "/AreaCenterInfoAllExport" })
    void waAddressAllExport(HttpServletResponse response, HttpServletRequest request,
                            Map<String, Object> modelMap) {
        Map<String, Object> params = new HashMap<String, Object>();
        List<TmypAreaCenterInfo> OrderList = areaCenterInfoExport(params);
        HSSFWorkbook wb= getDetailsData(OrderList);
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date=new java.util.Date();
        String str=sdf.format(date);
        String fileName = "优品3W送达方对照"+str;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            wb.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);

            ExportExcelUtil.exportCommon(is,fileName,response);
        } catch (IOException e) {
            logger.error("错误", e);
        }
    }


    /**
     *
     * @Title: waAddressExport
     * @Description:选择数据导出
     * @param exportData
     * @param response
     * @param request
     * @param modelMap
     * @param @return
     * @return String
     * @throws
     */
    @RequestMapping(value = { "/AreaCenterInfoExport" })
    void waAddressExport(@RequestParam(required = false) String exportData,
                         HttpServletResponse response, HttpServletRequest request,
                         Map<String, Object> modelMap) {
        Map<String, Object> params = new HashMap<String, Object>();
        JSONArray exportJson = new JSONArray();
        String[] exportArray = null;
        try {
            if (exportData != null && !exportData.equals("")) {
                exportJson = (JSONArray) JSON.parse(exportData);
                exportArray = new String[exportJson.length()];
                for (int i = 0; i < exportJson.length(); i++) {
                    exportArray[i] = (String) exportJson.get(i);
                }
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        params.put("deliveryToCode", exportArray);
        List<TmypAreaCenterInfo> orderList = areaCenterInfoExport(params);
        HSSFWorkbook wb= getDetailsData(orderList);
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date=new java.util.Date();
        String str=sdf.format(date);
        String fileName = "优品3W送达方对照"+str;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            wb.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);

            ExportExcelUtil.exportCommon(is,fileName,response);
        } catch (IOException e) {
            logger.error("错误", e);
        }
    }

    private HSSFWorkbook getDetailsData(List<TmypAreaCenterInfo> list) {


        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("优品3W送达方对照");
        int length = ExportData.tmypCenterInfoTitle.length;
        for (int i = 0; i <length; i++) {
            sheet.setColumnWidth(i, (int)(21.57*256));
        }

        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 4.创建单元格，设置值表头，设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边
        // 设置表头
        for(int i=0;length-1>=i;i++){
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(ExportData.tmypCenterInfoTitle[i]);
            cell.setCellStyle(style);
        }


        //向单元格里添加数据
        for(short i=0;i<list.size();i++){
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(list.get(i).getDeliveryToCode());
            row.createCell(1).setCellValue(list.get(i).getDistributionCentreCode());
            row.createCell(2).setCellValue(list.get(i).getDistributionCentreName());
            row.createCell(3).setCellValue(list.get(i).getWarehouseCode());
            row.createCell(4).setCellValue(list.get(i).getWarehouseName());
            row.createCell(5).setCellValue(list.get(i).getWhCode());
            row.createCell(6).setCellValue(list.get(i).getAreaCode());
            row.createCell(7).setCellValue(list.get(i).getAreaName());
            row.createCell(8).setCellValue(list.get(i).getRrsCenterCode());
            row.createCell(9).setCellValue(list.get(i).getRrsCenterName());
            row.createCell(10).setCellValue(list.get(i).getActiveFlag());
            row.createCell(11).setCellValue(list.get(i).getCreateBy());
            row.createCell(12).setCellValue(list.get(i).getLastUpdateBy());
            row.createCell(13).setCellValue(checkData(list.get(i).getCreateTime()));
            row.createCell(14).setCellValue(checkData(list.get(i).getLastUpdateTime()));
        }
        return wb;

    }

    /**
     *
     * @Title: waAddressExport
     * @Description: 导出数据处理
     * @param params
     * @param @return
     * @return List<WAAddress>
     * @throws
     */
    List<TmypAreaCenterInfo> areaCenterInfoExport(Map<String, Object> params) {
        List<TmypAreaCenterInfo> result = tmypAreaCenterInfoService.getAreaCenterInfoExport(params);
        for (TmypAreaCenterInfo a : result) {
            if ("0".equals(a.getActiveFlag())) {
                a.setActiveFlag("禁用");
            }
            if ("1".equals(a.getActiveFlag())) {
                a.setActiveFlag("启用");
            }
        }
        return result;
    }
    private String checkData(Date data){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if(data==null){
            return "";
        }
        return formatter.format(data);
    }
}
