package com.haier.svc.api.controller.purchase;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.ParseException;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.model.WAAddress;
import com.haier.svc.api.controller.util.ExportData;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import com.haier.svc.service.WaAddressService;
import org.apache.log4j.LogManager;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping(value = "/waaddress")
@Controller
public class WaAddressController {

    private final static org.apache.log4j.Logger logger = LogManager.getLogger(WaAddressController.class);

    @Resource
    private WaAddressService waAddressService;
    @GetMapping("toPage")
    public String toPage() {
        return "purchase/waAddress";
    }


    /**
     * @param waAddress
     * @param rows
     * @param page
     * @param request
     * @param response
     */
    @RequestMapping(value = { "searchWaAddress" }, method = { RequestMethod.POST })
    public void findWaAddress(WAAddress waAddress, @RequestParam(required = false) Integer rows,
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
        params.put("waAddress", waAddress);
        params.put("rows", rows);
        params.put("index", rows * (page - 1));
        List<WAAddress> userlist = waAddressService.getWAAddress(params);
        List<WAAddress> newList = new ArrayList<WAAddress>();
        for (WAAddress a : userlist) {
            if (a.getIs_enabled() == 0) {
                a.setIs_enabled_name("启用");
                newList.add(a);
            }
            if (a.getIs_enabled() == 1) {
                a.setIs_enabled_name("禁用");
                newList.add(a);
            }
        }
        result.put("total", waAddressService.getwaAddressCount(params));
        result.put("rows", newList);
        try {
            response.getWriter().write(JsonUtil.toJson(result));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Title: updateWaAddress
     * @Description: 更新数据
     * @param waAddress
     * @return void
     * @throws
     */
    @RequestMapping(value = { "updateWaAddress" }, method = { RequestMethod.POST })
    public void updateWaAddress(HttpServletRequest request, HttpServletResponse response,
                                WAAddress waAddress) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            waAddress.setUpdateUser(getUserName());
            params.put("waAddress", waAddress);
            //update_user 修改操作人
            String update_user = this.getUserName();
            params.put("update_user", update_user);
            waAddressService.updateWaAddress(params);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @Title: createWaAddress
     * @Description: 增加数据
     * @param request
     * @param waAddress
     * @return void
     * @throws
     */
    @RequestMapping(value = { "createWaAddress" }, method = { RequestMethod.POST })
    public void createWaAddress(HttpServletRequest request, HttpServletResponse response,
                                WAAddress waAddress) {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            Map<String, Object> keyCountMap = new HashMap<String, Object>();
            Map<String, Object> resultMap = new HashMap<String, Object>();
            waAddress.setCreateUser(getUserName());
            waAddress.setUpdateUser(getUserName());
            // 往数据库请求创建的map数据
            params.put("waAddress", waAddress);
            // 创建时主键不能重复，往数据库校验的数据
            WAAddress waAddressKeyCount = new WAAddress();
            waAddressKeyCount.setsCode(waAddress.getsCode());
            keyCountMap.put("waAddress", waAddressKeyCount);
            // KeyCount是要创建的数据的主键在数据库中的已经存在的条数
            int KeyCount = waAddressService.checkMainKey(keyCountMap);
            if (KeyCount == 0) {
                waAddressService.createWaAddress(params);
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
     * @Title: deletePurchaseUser
     * @Description: 删除数据
     * @param request
     * @param deleteData
     * @return void
     * @throws
     */
    @RequestMapping(value = { "deleteWaAddress.html" }, method = { RequestMethod.POST })
    public void deletePurchaseUser(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(required = true) String deleteData) {
        if (deleteData != null) {
            try {
                JSONArray deletejson = (JSONArray) JSON.parse(deleteData);
                List<String> deleteList = new ArrayList<String>();
                Map<String, Object> params = new HashMap<String, Object>();
                for (int i = 0; i < deletejson.length(); i++) {
                    deleteList.add(deletejson.getString(i));
                }
                params.put("deleteList", deleteList);
                String delete_user = this.getUserName();
                params.put("delete_user", delete_user);
                waAddressService.deleteWaAddress(params);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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
    @RequestMapping(value = { "openStatusWaAddress" }, method = { RequestMethod.POST })
    public void openStatusWaAddress(HttpServletRequest request, HttpServletResponse response,
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
                waAddressService.openStatusWaAddress(params);
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
    @RequestMapping(value = { "closeStatusWaAddress.html" }, method = { RequestMethod.POST })
    public void closeStatusWaAddress(@RequestParam(required = true) String closeData,
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
                waAddressService.closeStatusWaAddress(params);
            } catch (ParseException e) {
                e.printStackTrace();
            }

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
    @RequestMapping(value = { "/waAddressExport.export" })
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
        params.put("sCode", exportArray);
        List<WAAddress> orderList = waAddressExport(params);
        HSSFWorkbook wb= getDetailsData(orderList);
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date=new java.util.Date();
        String str=sdf.format(date);
        String fileName = "仓库地址联系方式"+str;
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


    public HSSFWorkbook getDetailsData(List<WAAddress> list) {


        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("仓库地址联系方式");
        int length = ExportData.waAddressTitle.length;
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
            cell.setCellValue(ExportData.waAddressTitle[i]);
            cell.setCellStyle(style);
        }


        //向单元格里添加数据
        for(short i=0;i<list.size();i++){
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(list.get(i).getsCode());
            row.createCell(1).setCellValue(list.get(i).getCenterName());
            row.createCell(2).setCellValue(list.get(i).getcCode());
            row.createCell(3).setCellValue(list.get(i).getProvince());
            row.createCell(4).setCellValue(list.get(i).getCity());
            row.createCell(5).setCellValue(list.get(i).getCounty());
            row.createCell(6).setCellValue(list.get(i).getLesCode());
            row.createCell(7).setCellValue(list.get(i).getAddress());
            row.createCell(8).setCellValue(list.get(i).getZipCode());
            row.createCell(9).setCellValue(list.get(i).getContact_cgodbm());
            row.createCell(10).setCellValue(list.get(i).getContact_crm());
            row.createCell(11).setCellValue(list.get(i).getMobilePhone());
            row.createCell(12).setCellValue(list.get(i).getPhone());
            row.createCell(13).setCellValue(list.get(i).getIs_enabled_name());

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
    List<WAAddress> waAddressExport(Map<String, Object> params) {
        ServiceResult<List<WAAddress>> result = waAddressService.getWAAddressExport(params);
        List<WAAddress> waAddressExportResult = result.getResult();
        List<WAAddress> newList = new ArrayList<WAAddress>();
        for (WAAddress a : waAddressExportResult) {
            if (a.getIs_enabled() == 0) {
                a.setIs_enabled_name("启用");
                newList.add(a);
            }
            if (a.getIs_enabled() == 1) {
                a.setIs_enabled_name("禁用");
                newList.add(a);
            }
        }
        return newList;
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
    @RequestMapping(value = { "/waAddressAllExport.export" })
    void waAddressAllExport(HttpServletResponse response, HttpServletRequest request,
                              Map<String, Object> modelMap) {
        Map<String, Object> params = new HashMap<String, Object>();
        List<WAAddress> OrderList = waAddressExport(params);
        HSSFWorkbook wb= getDetailsData(OrderList);
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date=new java.util.Date();
        String str=sdf.format(date);
        String fileName = "仓库地址联系方式"+str;
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
     * 获取当前登录的用户
     */
    private String getUserName() {
        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        return (String) attr.getRequest().getSession().getAttribute("userName");
    }
}
