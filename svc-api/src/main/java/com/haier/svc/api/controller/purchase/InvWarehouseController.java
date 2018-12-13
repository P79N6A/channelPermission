package com.haier.svc.api.controller.purchase;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.ParseException;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.model.InvBaseCityCode;
import com.haier.stock.model.InvWarehouse;
import com.haier.svc.api.controller.util.ExportData;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import com.haier.svc.service.InvWarehouseService;
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
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhenshuoxin
 * @date 2018年7月16日 下午1:41:16
 */
@RequestMapping(value = "/invWarehouse")
@Controller
public class InvWarehouseController {

    private final static org.apache.log4j.Logger logger = LogManager.getLogger(InvWarehouseController.class);


    @Autowired
    private InvWarehouseService invWarehouseService;
    @GetMapping("toPage")
    public String toPage() {
        return "purchase/invWarehouse";
    }


    /**
     *
     * @title searchInvWarehouse
     * @description 查询数据操作
     */
    @RequestMapping(value = { "searchInvWarehouse" }, method = { RequestMethod.POST })
    public void searchInvWarehouse(@RequestParam(required = false) String wh_code,
                                   @RequestParam(required = false) String estorge_name,
                                   @RequestParam(required = false) String transmit_id,
                                   @RequestParam(required = false) String transmit_desc,
                                   @RequestParam(required = false) String custom_id,
                                   @RequestParam(required = false) String rrs_distribution_id,
                                   @RequestParam(required = false) String rrs_distribution_name,
                                   @RequestParam(required = false) Integer rows,
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
        params.put("wh_code", wh_code);
        params.put("estorge_name", estorge_name);
        params.put("transmit_id", transmit_id);
        params.put("transmit_desc", transmit_desc);
        params.put("custom_id", custom_id);
        params.put("rrs_distribution_id", rrs_distribution_id);
        params.put("rrs_distribution_name", rrs_distribution_name);
        params.put("rows", rows);
        params.put("index", rows * (page - 1));
        ServiceResult<List<InvWarehouse>> userlist = invWarehouseService
                .getInvWarehouseInfo(params);
        List<InvWarehouse> statusList = userlist.getResult();
        List<InvWarehouse> newList = new ArrayList<InvWarehouse>();
        for (InvWarehouse a : statusList) {
            if (a.getIs_enabled() == 0) {
                a.setIs_enabled_name("启用");
                newList.add(a);
            }
            if (a.getIs_enabled() == 1) {
                a.setIs_enabled_name("禁用");
                newList.add(a);
            }
        }
        result.put("total", invWarehouseService.getInvWareHouseCount(params));
        result.put("rows", newList);
        try {
            response.getWriter().write(JsonUtil.toJson(result));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @title createInvWarehouse
     * @description 添加新数据
     */
    @RequestMapping(value = { "createInvWarehouse" }, method = { RequestMethod.POST })
    public void createInvWarehouse(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(required = true) String createData) {
        if (createData != null) {
            try {
                JSONArray createjson = (JSONArray) JSON.parse(createData);
                List<String> createList = new ArrayList<String>();
                Map<String, Object> params = new HashMap<String, Object>();
                Map<String, Object> keyCountMap = new HashMap<String, Object>();
                Map<String, Object> resultMap = new HashMap<String, Object>();
                for (int i = 0; i < createjson.length(); i++) {
                    createList.add(createjson.getString(i));
                }
                keyCountMap.put("wh_code", createList.get(0));
                int keyCount = invWarehouseService.checkMainKey(keyCountMap);
                if (keyCount == 0) {
                    String create_user = getUserName();
                    String update_user = getUserName();
                    params.put("wh_code", createList.get(0));
                    params.put("wh_name", createList.get(1));
                    params.put("status", createList.get(2));
                    params.put("center_code", createList.get(3));
                    params.put("support_cod", createList.get(4));
                    params.put("les_accepter", createList.get(5));
                    params.put("estorge_id", createList.get(6));
                    params.put("estorge_name", createList.get(7));
                    params.put("transmit_id", createList.get(8));
                    params.put("transmit_desc", createList.get(9));
                    params.put("les_OE_id", createList.get(10));
                    params.put("custom_id", createList.get(11));
                    params.put("custom_desc", createList.get(12));
                    params.put("industry_trade_id", createList.get(13));
                    params.put("industry_trade_desc", createList.get(14));
                    params.put("graininess_id", createList.get(15));
                    params.put("net_management_id", createList.get(16));
                    params.put("esale_id", createList.get(17));
                    params.put("esale_name", createList.get(18));
                    params.put("sale_org_id", createList.get(19));
                    params.put("branch", createList.get(20));
                    params.put("payment_id", createList.get(21));
                    params.put("payment_name", createList.get(22));
                    params.put("area_id", createList.get(23));
                    params.put("rrs_distribution_id", createList.get(24));
                    params.put("rrs_distribution_name", createList.get(25));
                    params.put("rrs_sale_id", createList.get(26));
                    params.put("rrs_sale_name", createList.get(27));
                    params.put("oms_rrs_payment_id", createList.get(28));
                    params.put("oms_rrs_payment_name", createList.get(29));
                    params.put("city_desc", createList.get(30));
                    params.put("city_code", createList.get(31));
                    params.put("create_user", create_user);
                    params.put("update_user", update_user);
                    invWarehouseService.createInvWareHouse(params);
                    resultMap.put("resultStatus", -1);
                } else {
                    resultMap.put("resultStatus", 0);
                    logger.error("创建失败，仓库（TC）代码重复");
                }
                response.getWriter().write(JsonUtil.toJson(resultMap));
                response.getWriter().flush();
                response.getWriter().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @title updateInvWarehouse
     * @description 更新数据
     */
    @RequestMapping(value = { "updateInvWarehouse" }, method = { RequestMethod.POST })
    public void updateInvWarehouse(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(required = true) String updateData) {
        if (updateData != null) {
            try {
                JSONArray updatejson = (JSONArray) JSON.parse(updateData);
                List<String> updateList = new ArrayList<String>();
                Map<String, Object> params = new HashMap<String, Object>();
                for (int i = 0; i < updatejson.length(); i++) {
                    updateList.add(updatejson.getString(i));
                }
                String update_user = getUserName();
                params.put("wh_code", updateList.get(0));
                params.put("wh_name", updateList.get(1));
                params.put("status", updateList.get(2));
                params.put("center_code", updateList.get(3));
                params.put("support_cod", updateList.get(4));
                params.put("les_accepter", updateList.get(5));
                params.put("estorge_id", updateList.get(6));
                params.put("estorge_name", updateList.get(7));
                params.put("transmit_id", updateList.get(8));
                params.put("transmit_desc", updateList.get(9));
                params.put("les_OE_id", updateList.get(10));
                params.put("custom_id", updateList.get(11));
                params.put("custom_desc", updateList.get(12));
                params.put("industry_trade_id", updateList.get(13));
                params.put("industry_trade_desc", updateList.get(14));
                params.put("graininess_id", updateList.get(15));
                params.put("net_management_id", updateList.get(16));
                params.put("esale_id", updateList.get(17));
                params.put("esale_name", updateList.get(18));
                params.put("sale_org_id", updateList.get(19));
                params.put("branch", updateList.get(20));
                params.put("payment_id", updateList.get(21));
                params.put("payment_name", updateList.get(22));
                params.put("area_id", updateList.get(23));
                params.put("rrs_distribution_id", updateList.get(24));
                params.put("rrs_distribution_name", updateList.get(25));
                params.put("rrs_sale_id", updateList.get(26));
                params.put("rrs_sale_name", updateList.get(27));
                params.put("oms_rrs_payment_id", updateList.get(28));
                params.put("oms_rrs_payment_name", updateList.get(29));
                params.put("city_desc", updateList.get(30));
                params.put("city_code", updateList.get(31));
                params.put("update_user", update_user);
                invWarehouseService.updateInvWareHouse(params);

            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    /**
     *
     * @title deleteInvWarehouse
     * @description 删除数据
     */

    @RequestMapping(value = { "deleteInvWarehouse" }, method = { RequestMethod.POST })
    public void deleteInvWarehouse(@RequestParam(required = true) String deleteData,
                                   HttpServletRequest request, HttpServletResponse response) {
        if (deleteData != null) {
            try {
                JSONArray deletejson = (JSONArray) JSON.parse(deleteData);
                List<String> deleteList = new ArrayList<String>();
                Map<String, Object> params = new HashMap<String, Object>();
                for (int i = 0; i < deletejson.length(); i++) {
                    deleteList.add(deletejson.getString(i));
                }
                params.put("deleteList", deleteList);
                String delete_user = getUserName();
                params.put("delete_user", delete_user);
                invWarehouseService.deleteInvWareHouse(params);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     *
     * @title openStatusInvWarehouse
     * @description 数据状态设为启用
     */
    @RequestMapping(value = { "openStatusInvWarehouse" }, method = { RequestMethod.POST })
    public void openStatusInvWarehouse(HttpServletRequest request, HttpServletResponse response,
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
                String open_user = getUserName();
                params.put("open_user", open_user);
                invWarehouseService.openStatusInvWarehouse(params);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 城市下拉列表
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = { "getinvBaseCityCodeCombobox" }, method = { RequestMethod.GET })
    public void getInvBaseCityCode1(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        Map<String, Object> params = new HashMap<String, Object>();
        ServiceResult<List<InvBaseCityCode>> result = invWarehouseService
                .getInvBaseCityCode(params);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<InvBaseCityCode> rowslist = new ArrayList<InvBaseCityCode>();
        rowslist = result.getResult();
        resultMap.put("rows", rowslist);
        try {
            response.getWriter().write(JsonUtil.toJson(resultMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("getInvBaseCityCode:", e);
        }
    }

    /**
     *
     * @title closeStatusInvWarehouse
     * @description 数据状态设为禁用
     */
    @RequestMapping(value = { "closeStatusInvWarehouse" }, method = { RequestMethod.POST })
    public void closeStatusInvWarehouse(@RequestParam(required = true) String closeData,
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
                String close_user = getUserName();
                params.put("close_user", close_user);
                invWarehouseService.closeStatusInvWarehouse(params);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     *
     * @Title: InvWarehouseExport
     * @Description: 选择数据导出
     * @param exportData
     * @param response
     * @param request
     * @param modelMap
     * @return String
     * @throws
     */
    @RequestMapping(value = { "/invWarehouseExport.export" })
    void InvWarehouseExport(@RequestParam(required = false) String exportData,
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
        params.put("wh_code", exportArray);
        List<InvWarehouse> orderList = invWarehouseExport(params);
        HSSFWorkbook wb= getDetailsData(orderList);
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date=new java.util.Date();
        String str=sdf.format(date);
        String fileName = "电商OMS送达方对照"+str;
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


    public HSSFWorkbook getDetailsData(List<InvWarehouse> list) {


        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("电商OMS送达方对照");
        int length = ExportData.invWarehouseTitle.length;
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
            cell.setCellValue(ExportData.invWarehouseTitle[i]);
            cell.setCellStyle(style);
        }


        //向单元格里添加数据
        for(short i=0;i<list.size();i++){
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(list.get(i).getWhCode());
            row.createCell(1).setCellValue(list.get(i).getWhName());
            row.createCell(2).setCellValue(list.get(i).getCenterCode());
            row.createCell(3).setCellValue(list.get(i).getCreateUser());
            row.createCell(4).setCellValue(list.get(i).getCreateTime());
            row.createCell(5).setCellValue(list.get(i).getUpdateUser());
            row.createCell(6).setCellValue(list.get(i).getUpdateTime());
            row.createCell(7).setCellValue(list.get(i).getEstorge_id());
            row.createCell(8).setCellValue(list.get(i).getEstorge_name());
            row.createCell(9).setCellValue(list.get(i).getTransmit_id());
            row.createCell(10).setCellValue(list.get(i).getTransmit_desc());
            row.createCell(11).setCellValue(list.get(i).getLes_OE_id());
            row.createCell(12).setCellValue(list.get(i).getCustom_id());
            row.createCell(13).setCellValue(list.get(i).getCustom_desc());
            row.createCell(14).setCellValue(list.get(i).getIndustry_trade_id());
            row.createCell(15).setCellValue(list.get(i).getIndustry_trade_desc());
            row.createCell(16).setCellValue(list.get(i).getGraininess_id());
            row.createCell(17).setCellValue(list.get(i).getNet_management_id());
            row.createCell(18).setCellValue(list.get(i).getEsale_id());
            row.createCell(19).setCellValue(list.get(i).getEsale_name());
            row.createCell(20).setCellValue(list.get(i).getSale_org_id());
            row.createCell(21).setCellValue(list.get(i).getBranch());
            row.createCell(22).setCellValue(list.get(i).getPayment_id());
            row.createCell(23).setCellValue(list.get(i).getPayment_name());
            row.createCell(24).setCellValue(list.get(i).getArea_id());
            row.createCell(25).setCellValue(list.get(i).getRrs_distribution_id());
            row.createCell(26).setCellValue(list.get(i).getRrs_distribution_name());
            row.createCell(27).setCellValue(list.get(i).getRrs_sale_id());
            row.createCell(28).setCellValue(list.get(i).getRrs_sale_name());
            row.createCell(29).setCellValue(list.get(i).getOms_rrs_payment_id());
            row.createCell(30).setCellValue(list.get(i).getOms_rrs_payment_name());
            row.createCell(31).setCellValue(list.get(i).getIs_enabled_name());

        }
        return wb;

    }
    /**
     *
     * @Title: invWarehouseExport
     * @Description: 导出数据处理
     * @param params
     * @return List<InvWarehouse>
     * @throws
     */
    List<InvWarehouse> invWarehouseExport(Map<String, Object> params) {
        ServiceResult<List<InvWarehouse>> result = invWarehouseService
                .getInvWarehouseExport(params);
        List<InvWarehouse> invWarehouseExportResult = result.getResult();
        List<InvWarehouse> newList = new ArrayList<InvWarehouse>();
        for (InvWarehouse a : invWarehouseExportResult) {
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
    @RequestMapping(value = { "/invWarehouseAllExport.export" })
    void invWarehouseAllExport(HttpServletResponse response, HttpServletRequest request,
                                 Map<String, Object> modelMap) {
        Map<String, Object> params = new HashMap<String, Object>();
        List<InvWarehouse> OrderList = invWarehouseExport(params);
        HSSFWorkbook wb= getDetailsData(OrderList);
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date=new java.util.Date();
        String str=sdf.format(date);
        String fileName = "电商OMS送达方对照"+str;
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
