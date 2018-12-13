package com.haier.svc.api.controller.purchase;


import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.ParseException;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.model.WAAddress;
import com.haier.stock.model.InvRrsWarehouse;
import com.haier.svc.api.controller.util.ExportData;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import com.haier.svc.service.InvRrsWarehouseService;
import org.apache.log4j.LogManager;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * @author zhenshuoxin
 * @date 2018年7月16日 下午1:41:16
 */
@RequestMapping(value = "/invRrsWarehouse")
@Controller
public class InvRrsWarehouseController {
    private final static org.apache.log4j.Logger logger = LogManager.getLogger(InvRrsWarehouseController.class);

    @GetMapping("toPage")
    public String toPage() {
        return "purchase/InvRrsWarehouseView";
    }

    @Resource
    private InvRrsWarehouseService invRrsWarehouseService;

    @RequestMapping("getRrsWhByEstorgeId")
    private void getRrsWhByEstorgeIdController(HttpServletRequest request,
                                               HttpServletResponse response,
                                               InvRrsWarehouse invRrsWarehouse, Integer page,
                                               Integer rows) {
        if (rows == null) {
            rows = 1;
        }
        if (page == null) {
            page = 1;
        }
        Map daoMap = new HashMap();

        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("rows", rows);
        userMap.put("index", rows * (page - 1));

        userMap.put("rrs_wh_code", invRrsWarehouse.getRrs_wh_code());
        String rrs_wh_name = invRrsWarehouse.getRrs_wh_name();
        if (rrs_wh_name != null && !"".equals(rrs_wh_name)) {
            rrs_wh_name = "%" + rrs_wh_name + "%";
            userMap.put("rrs_wh_name", rrs_wh_name);

        }

        userMap.put("estorge_id", invRrsWarehouse.getEstorge_id());
        userMap.put("t2_default", invRrsWarehouse.getT2_default());
        userMap.put("transport_prescription", invRrsWarehouse.getTransport_prescription());

        ServiceResult<List<InvRrsWarehouse>> result = invRrsWarehouseService
                .getPurRrsWhByEstorgeId(userMap);

        List<InvRrsWarehouse> list = result.getResult();
        List<InvRrsWarehouse> newList = new ArrayList<InvRrsWarehouse>();
        for (InvRrsWarehouse inv : list) {
            if (inv.getIs_enabled() == 0) {
                inv.setIs_enabled_name("启用");
            } else {
                inv.setIs_enabled_name("禁用");
            }
            Integer tempi = 1;
            if (inv.getT2_default() == tempi) {
                inv.setT2_default(1);

            } else {
                inv.setT2_default(0);
            }
            newList.add(inv);
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("rows", newList);
        resultMap.put("total", invRrsWarehouseService.countTotalService(userMap));
        try {
            response.setHeader("content-type", "text/html;charset=UTF-8");
            response.getWriter().write(JsonUtil.toJson(resultMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     *
     * @throws Exception
     * @Title: insertInvRrsWarehouseController
     * @Description:接收新建数据的json数组，把json数组变成map供数据库使用，对应插入按钮
     */
    @RequestMapping(value = "insertInvRrsWarehouse", method = { RequestMethod.POST })
    public void insertInvRrsWarehouseController(HttpServletRequest request,
                                                HttpServletResponse response,
                                                InvRrsWarehouse invRrsWarehouse) throws Exception {
        String create_user = this.getUserName();
        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("rrs_wh_code", invRrsWarehouse.getRrs_wh_code());
        userMap.put("rrs_wh_name", invRrsWarehouse.getRrs_wh_name());
        userMap.put("estorge_id", invRrsWarehouse.getEstorge_id());

        userMap.put("transport_prescription", invRrsWarehouse.getTransport_prescription());
        userMap.put("create_user", create_user);
        userMap.put("update_user", create_user);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("estorge_id", invRrsWarehouse.getEstorge_id());
        queryMap.put("rrs_wh_code", invRrsWarehouse.getRrs_wh_code());

        Integer T2default = invRrsWarehouse.getT2_default();
        Map<String, Object> statusMap = new HashMap<String, Object>();
        statusMap.put("rrs_wh_code", invRrsWarehouse.getRrs_wh_code());
        Integer rrsCodeStatus = invRrsWarehouseService.checkMainKey(statusMap);
        if (rrsCodeStatus == 0) {
            if (T2default == 0) {
                invRrsWarehouse.setT2_default(null);
                userMap.put("t2_default", invRrsWarehouse.getT2_default());
                invRrsWarehouseService.insertInvRrsWarehouseService(userMap);
                resultMap.put("resultStatus", "yes");
            } else {
                Integer resultCount = invRrsWarehouseService.countT2StatusService(queryMap);
                if (resultCount == 0) {
                    userMap.put("t2_default", invRrsWarehouse.getT2_default());
                    invRrsWarehouseService.insertInvRrsWarehouseService(userMap);
                    resultMap.put("resultStatus", "yes");
                } else {
                    resultMap.put("resultStatus", "no");
                    logger.error("创建失败，同一个电商库位码只能有一个上单默认");
                }

            }
        } else {
            resultMap.put("resultStatus", "primary");
            logger.error("创建失败，日日顺库位码重复");
        }

        response.getWriter().write(JsonUtil.toJson(resultMap));
        response.getWriter().flush();
        response.getWriter().close();
    }


    /**
     *
     * @throws Exception
     * @Title: updateInvRrsWarehouseController
     * @Description:更新数据，前台传过来更新数据，包括所有字段的数据，转成Map供数据库更新使用，对应更新按钮
     */
    @RequestMapping(value = "updateInvRrsWarehouse", method = { RequestMethod.POST })
    public void updateInvRrsWarehouseController(HttpServletRequest request,
                                                HttpServletResponse response,
                                                InvRrsWarehouse invRrsWarehouse) throws Exception {
        Map<String, Object> userMap = new HashMap<String, Object>();
        String update_user = this.getUserName();
        userMap.put("rrs_wh_code", invRrsWarehouse.getRrs_wh_code());
        userMap.put("rrs_wh_name", invRrsWarehouse.getRrs_wh_name());
        userMap.put("estorge_id", invRrsWarehouse.getEstorge_id());
        userMap.put("transport_prescription", invRrsWarehouse.getTransport_prescription());
        userMap.put("update_user", update_user);
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("rrs_wh_code", invRrsWarehouse.getRrs_wh_code());
        queryMap.put("estorge_id", invRrsWarehouse.getEstorge_id());
        Map<String, Object> statusMap = new HashMap<String, Object>();

        if (invRrsWarehouse.getT2_default() == 0) {
            invRrsWarehouse.setT2_default(null);
            userMap.put("t2_default", invRrsWarehouse.getT2_default());
            invRrsWarehouseService.updateInvRrsWarehouseService(userMap);
            statusMap.put("resultStatus", "yes");
        } else {
            Integer resultCount = invRrsWarehouseService.countT2StatusService(queryMap);
            if (resultCount == 1) {
                statusMap.put("resultStatus", "no");
            } else {
                userMap.put("t2_default", invRrsWarehouse.getT2_default());
                invRrsWarehouseService.updateInvRrsWarehouseService(userMap);
                statusMap.put("resultStatus", "yes");
            }
        }

        response.getWriter().write(JsonUtil.toJson(statusMap));
        response.getWriter().flush();
        response.getWriter().close();
    }

    /**
     *
     * @Title: deleteInvRrsWarehouseController
     * @Description:删除操作，传过来要删除的条目的主键json串，把json传转成List，遍历List实现删除操作，对应删除按钮
     */
    @RequestMapping(value = "deleteInvRrsWarehouse", method = { RequestMethod.POST })
    public void deleteInvRrsWarehouseController(HttpServletRequest request,
                                                HttpServletResponse response, String deleteData)
            throws Exception {

        JSONArray jsonArray = (JSONArray) JSON.parse(deleteData);

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {
            list.add(jsonArray.getString(i));

        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("deleteList", list);
        map.put("is_enabled", "4");
        map.put("openUser", this.getUserName());

        invRrsWarehouseService.deleteInvRrsWarehouseService(map);

    }

    /**
     *
     * @Title: statusOpenInvRrsWarehouseController
     * @Description:状态打开操作，与update共用一个Dao层方法，前台传来要打开状态的主键，将要打开状态的条目的status字段设置为0，对应开启按钮
     */
    @RequestMapping(value = "statusOpenInvRrsWarehouse", method = { RequestMethod.POST })
    public void statusOpenInvRrsWarehouseController(HttpServletRequest request,
                                                    HttpServletResponse response, String deleteData)
            throws Exception {
        try {
            JSONArray jsonArray = (JSONArray) JSON.parse(deleteData);
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("deleteList", list);
            map.put("openUser", getUserName());
            map.put("is_enabled", "0");

            invRrsWarehouseService.deleteInvRrsWarehouseService(map);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @Title: statusCloseInvRrsWarehouseController
     * @Description:状态关闭操作，与update共用一个Dao层方法，前台传来要打开状态的主键，将要打开状态的条目的status字段设置为1，对应关闭按钮
     */
    @RequestMapping(value = "statusCloseInvRrsWarehouse", method = { RequestMethod.POST })
    public void statusCloseInvRrsWarehouseController(HttpServletRequest request,
                                                     HttpServletResponse response, String deleteData)
            throws ParseException {

        try {
            JSONArray jsonArray = (JSONArray) JSON.parse(deleteData);
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getString(i));
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("deleteList", list);
            map.put("openUser", getUserName());
            map.put("is_enabled", "1");

            invRrsWarehouseService.deleteInvRrsWarehouseService(map);
        } catch (ParseException e) {

            e.printStackTrace();
        }
    }

    /**
     *
     * title:exportInvRrsWarehouse description:部分导出报表
     */
    @RequestMapping(value = "exportInvRrsWarehouse.export", method = { RequestMethod.POST,
            RequestMethod.GET })
    public void exportInvRrsWarehouse(HttpServletRequest request, HttpServletResponse response,
                                        Map userMap, String exportData) throws Exception {

        JSONArray jsonArray = (JSONArray) JSON.parse(exportData);

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < jsonArray.length(); i++) {

            list.add(jsonArray.getString(i));
        }
        Map queryMap = new HashMap();
        queryMap.put("exportList", list);
        List<InvRrsWarehouse> resultList = invRrsWarehouseService
                .selectInvRrsWarehouseExportService(queryMap);
        List<InvRrsWarehouse> tempList = new ArrayList<InvRrsWarehouse>();

        for (InvRrsWarehouse inv : resultList) {
            Date tempDate = inv.getCreate_time();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String CreateDateString = sdf.format(tempDate);
            inv.setCreate_time_web(CreateDateString);
            String updateDateString = sdf.format(inv.getUpdate_time());
            inv.setUpdate_time_web(updateDateString);
            if (inv.getIs_enabled() == 0) {
                inv.setIs_enabled_name("启用");
                tempList.add(inv);
            } else {
                inv.setIs_enabled_name("禁用");
                tempList.add(inv);
            }
        }
        HSSFWorkbook wb= getDetailsData(tempList);
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date=new java.util.Date();
        String str=sdf.format(date);
        String fileName = "电商库位与日日顺库位部分导出"+str;
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

    public HSSFWorkbook getDetailsData(List<InvRrsWarehouse> list) {


        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("电商库位与日日顺库位部分导出");
        int length = ExportData.invRrsWarehouseTitle.length;
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
            cell.setCellValue(ExportData.invRrsWarehouseTitle[i]);
            cell.setCellStyle(style);
        }


        //向单元格里添加数据
        for(short i=0;i<list.size();i++){
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(list.get(i).getRrs_wh_code());
            row.createCell(1).setCellValue(list.get(i).getRrs_wh_name());
            row.createCell(2).setCellValue(list.get(i).getEstorge_id());
            row.createCell(3).setCellValue(check(list.get(i).getT2_default()));
            row.createCell(4).setCellValue(list.get(i).getTransport_prescription());
            row.createCell(5).setCellValue(list.get(i).getCreate_user());
            row.createCell(6).setCellValue(list.get(i).getCreate_time_web());
            row.createCell(7).setCellValue(list.get(i).getUpdate_user());
            row.createCell(8).setCellValue(list.get(i).getUpdate_time_web());
            row.createCell(9).setCellValue(list.get(i).getIs_enabled_name());;

        }
        return wb;
    }
    private String check(Integer t2){
        if(t2==null){
            return "";
        }
        return String.valueOf(t2);
    }

    @RequestMapping(value = "exportInvRrsWarehouseAll.export", method = { RequestMethod.POST,
            RequestMethod.GET })
    public void exportInvRrsWarehouseAll(Map userMap, HttpServletRequest request,
                                           HttpServletResponse response) {

        Map map = new HashMap();
        List<InvRrsWarehouse> userList = invRrsWarehouseService
                .selectInvRrsWarehouseExportService(map);

        List<InvRrsWarehouse> tempList = new ArrayList<InvRrsWarehouse>();
        for (InvRrsWarehouse inv : userList) {
            Date tempDate = inv.getCreate_time();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String CreateDateString = sdf.format(tempDate);
            inv.setCreate_time_web(CreateDateString);
            String updateDateString = sdf.format(inv.getUpdate_time());
            inv.setUpdate_time_web(updateDateString);
            if (inv.getIs_enabled() == 0) {
                inv.setIs_enabled_name("启用");
                tempList.add(inv);
            } else {
                inv.setIs_enabled_name("禁用");
                tempList.add(inv);
            }

        }

        HSSFWorkbook wb= getDetailsData(tempList);
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date=new java.util.Date();
        String str=sdf.format(date);
        String fileName = "电商库位与日日顺库位对照"+str;
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
