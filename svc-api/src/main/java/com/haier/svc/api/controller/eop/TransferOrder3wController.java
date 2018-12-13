package com.haier.svc.api.controller.eop;

import com.google.gson.Gson;
import com.haier.common.BusinessException;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.eop.data.model.StoreCodeMapping;
import com.haier.eop.data.model.TransferOrderDisplayItem;
import com.haier.eop.data.service.StoreCodeService;
import com.haier.eop.service.EopStoreCodeService;
import com.haier.eop.service.EopTransferOrder3wService;
import com.haier.invoice.util.CommUtil;
import com.haier.invoice.util.ExcelCallbackInterfaceUtil;
import com.haier.invoice.util.ExcelExportUtil;
import com.haier.invoice.util.HttpJsonResult;
import com.haier.system.model.SysUser;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("transferorder3w/")
public class TransferOrder3wController {

    private static final Logger logger = LogManager.getLogger(TransferOrder3wController.class);

    @Autowired
    private EopTransferOrder3wService transferOrder3wService;

    @Autowired
    private EopStoreCodeService eopStoreCodeService;

    @RequestMapping(value = {"transferOrderList3w"}, method = {RequestMethod.GET})
    String transferOrderList3w(HttpServletRequest request) {
        return "eop/transferorder/transferOrderList3w";
    }

    @RequestMapping(value = {"transfer_order_query"}, method = {RequestMethod.POST, RequestMethod.GET})
    public void transferOrderQuery(
            @RequestParam(required = false) String transferOrderCode,
            @RequestParam(required = false) String transferOutOrderCode,
            @RequestParam(required = false) String transferInOrderCode,
            @RequestParam(required = false) String scItemCode,
            @RequestParam(required = false) String orderStatus,
            @RequestParam(required = false) String difference,
            @RequestParam(required = false) String fromStoreCode,
            @RequestParam(required = false) String toStoreCode,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
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
            //参数加入params里
            params.put("transferOrderCode", transferOrderCode.trim());
            params.put("transferOutOrderCode", transferOutOrderCode.trim());
            params.put("transferInOrderCode", transferInOrderCode.trim());
            params.put("scItemCode", scItemCode.trim());
            params.put("orderStatus", orderStatus);
            params.put("difference", difference);
            params.put("fromStoreCode", fromStoreCode.trim());
            params.put("toStoreCode", toStoreCode.trim());
            params.put("startTime", startTime);
            params.put("endTime", endTime);


            Map<String, Object> retMap = transferOrder3wService.findOrderList(params);
            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (ParseException e) {
            logger.error("", e);
            throw new BusinessException("时间转换类型失败" + e.getMessage());
        } catch (IOException e) {
            logger.error("", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }


    @RequestMapping(value = {"/transfer_order_sync"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String transferOrderSync(@RequestParam int id, @RequestParam String transferOrderCode,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            transferOrder3wService.syncOrderFromCn(transferOrderCode);
            return "eop/transferorder/transferOrderList3w";
        } catch (Exception e) {
            return "eop/transferorder/transferOrderList3w";
        }
    }

    /**
     * 批量补录界面跳转
     * @param request
     * @return
     */
    @RequestMapping(value = {"transferOrderReadd"}, method = {RequestMethod.GET})
    String transferOrderReadd(HttpServletRequest request) {
        return "eop/transferorder/transferOrderReadd";
    }

    /**
     * 补录调拨单
     * @param transferOrderCodeList
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/transfer_order_batchreadd"}, method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public HttpJsonResult<Map<String, Object>> transferOrderBatchReadd(@RequestParam(required = false) String transferOrderCodeList,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("UTF-8");
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult();

        try
        {
            transferOrderCodeList = (transferOrderCodeList == null) || ("".equals(transferOrderCodeList.trim())) ? null : transferOrderCodeList.trim();

            if (transferOrderCodeList == null) {
                result.setMessage("IBC补货单号不允许为空！");
                return result;
            }

            String[] totalArray = transferOrderCodeList.replace("\r", ",").replace("\n", ",").replaceAll("(,)+", ",").replace(" ", "").split(",");
            Set<String> set = new HashSet(Arrays.asList(totalArray));
            totalArray = (String[])set.toArray(new String[0]);

            String serviceResult = this.transferOrder3wService.readdRecords(totalArray);
            result.setMessage(serviceResult);
        } catch (Exception e) {
            logger.error("批量补录3W调拨单出现异常：", e);
            result.setMessage("更新失败！");
            return result;
        }
        return result;
    }

    @RequestMapping(value = {"/transfer_order_syncall"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String transferOrderSyncAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            transferOrder3wService.syncOrdersFromCn();
            return "eop/transferorder/transferOrderList3w";
        } catch (Exception e) {
            return "eop/transferorder/transferOrderList3w";
        }
    }

    /**
     * 仓库编码对照信息列表
     * @param request
     * @return
     */
    @RequestMapping(value = {"/StoreCodeList3w"},method = {RequestMethod.GET})
    public String invoiceMakeStoreCodeList(HttpServletRequest request){ return "eop/storecode/StoreCodeList3w"; }


    /**
     * 分页查询仓库编码
     * @param storeName 仓库名称
     * @param haierStoreCode 海尔仓库编码
     * @param cainiaoStoreCode 菜鸟仓库编码
     * @param page
     * @param rows
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"/StoreCode_query"},method = {RequestMethod.POST,RequestMethod.GET})
    public void transferStoreCodeQuery(
            @RequestParam(required = false) String storeName,
            @RequestParam(required = false) String haierStoreCode,
            @RequestParam(required = false) String cainiaoStoreCode,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer rows,
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
            //参数加入params里
            params.put("storeName", storeName.trim());
            params.put("haierStoreCode", haierStoreCode.trim());
            params.put("cainiaoStoreCode", cainiaoStoreCode.trim());

            Map<String, Object> retMap = eopStoreCodeService.findStoreCodeList(params);
            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("", e);
            throw new BusinessException("失败" + e.getMessage());
        }

    }


    /**
     *
     * 导出调拨单
     * @param
     * @param request
     * @param response
     */
    @RequestMapping(value = {"export_transfer_order"}, method = {RequestMethod.POST, RequestMethod.GET})
    public void exportTransferOrder(
            @RequestParam(required = false) String transferOrderCode,
            @RequestParam(required = false) String transferOutOrderCode,
            @RequestParam(required = false) String transferInOrderCode,
            @RequestParam(required = false) String scItemCode,
            @RequestParam(required = false) String orderStatus,
            @RequestParam(required = false) String difference,
            @RequestParam(required = false) String fromStoreCode,
            @RequestParam(required = false) String toStoreCode,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime,
            HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        response.setCharacterEncoding("UTF-8");
        paramMap.put("m", 0);
        paramMap.put("n", 5000);

        //参数加入params里
        paramMap.put("transferOrderCode", transferOrderCode.trim());
        paramMap.put("transferOutOrderCode", transferOutOrderCode.trim());
        paramMap.put("transferInOrderCode", transferInOrderCode.trim());
        paramMap.put("scItemCode", scItemCode.trim());
        paramMap.put("orderStatus", orderStatus);
        paramMap.put("difference", difference);
        paramMap.put("fromStoreCode", fromStoreCode.trim());
        paramMap.put("toStoreCode", toStoreCode.trim());
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);

        //获取开单列表List
        final List<Map<String, Object>> result = transferOrder3wService.getExportTransferOrderOutList(paramMap);
        String fileName = "调拨单列表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "数据导出";
        String[] sheetHead = new String[] { "序号", "单据号", "物料编码", "出仓库编码", "入仓库编码", "实际出库数量",
                "入库正品数量", "入库残品数量", "状态", "出库单LBX号", "入库单LBX号", "出库确认时间", "入库确认时间",
                "创建人", "创建时间", "SAP错误信息" };
        try {
            ExcelExportUtil.exportEntity(request, response, fileName, sheetName, sheetHead,
                    new ExcelCallbackInterfaceUtil() {

                        @Override
                        public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp)
                                throws Exception {
                            setExcelBodyTotalForTransferOrderOut(sheet, temp, result);
                        }

                    });
        } catch (Exception e) {
            logger.error("调拨单导出失败", e);
            e.printStackTrace();
        }
    }

    /**
     * 导出具体数据，实现回调函数
     * @param sheet
     * @param temp 行号
     * @param list 传入需要导出的 list
     */
    private void setExcelBodyTotalForTransferOrderOut(WritableSheet sheet, int temp,
                                                     List<Map<String, Object>> list)
            throws Exception {

        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat textFormat = new WritableCellFormat(font);
        textFormat.setAlignment(Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        DisplayFormat displayFormat = NumberFormats.INTEGER;
        WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
        numberFormat.setAlignment(jxl.format.Alignment.RIGHT);
        numberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int index = 0;
        for (Map<String, Object> map : list) {
            index++;
            //jxl.write.Number(列号,行号 ,内容 )
            /* "单据号", "物料编码", "出仓库编码", "入仓库编码", "实际出库数量",
                "入库正品数量", "入库残品数量", "状态", "出库单LBX号", "入库单LBX号", "出库确认时间", "入库确认时间",
                "创建人", "创建时间", "SAP错误信息"*/
            sheet.setColumnView(0, 10);
            sheet.addCell(new Label(0, temp, CommUtil.getStringValue(index), textFormat));
            sheet.setColumnView(1, 30);
            sheet.addCell(new Label(1, temp, CommUtil.getStringValue(map.get("transferOrderCode")), textFormat));
            sheet.setColumnView(2, 15);
            sheet.addCell(new Label(2, temp, CommUtil.getStringValue(map.get("scItemCode")), textFormat));
            sheet.setColumnView(3, 15);
            sheet.addCell(new Label(3, temp, CommUtil.getStringValue(map.get("fromStoreCode")), textFormat));
            sheet.setColumnView(4, 15);
            sheet.addCell(new Label(4, temp, CommUtil.getStringValue(map.get("toStoreCode")), textFormat));
            sheet.setColumnView(5, 15);
            sheet.addCell(new Label(5, temp, CommUtil.getStringValue(map.get("outCount")), textFormat));
            sheet.setColumnView(6, 15);
            sheet.addCell(new Label(6, temp, CommUtil.getStringValue(map.get("inCount")), textFormat));
            sheet.setColumnView(7, 15);
            sheet.addCell(new Label(7, temp, CommUtil.getStringValue(map.get("remnantNum")), textFormat));
            String status = CommUtil.getStringValue(map.get("orderStatus"));
            if ("0".equals(status)) {
                status = "调拨单申请中";
            } else if ("30".equals(status)) {
                status = "全部出库";
            } else if ("140".equals(status)) {
                status = "全部入库";
            } else if ("150".equals(status)) {
                status = "调拨完成,出库推送SAP";
            } else if ("100".equals(status)) {
                status = "已推送SAP,关闭";
            } else if ("101".equals(status)) {
                status = "同仓调拨,关闭";
            } else if ("110".equals(status)){
                status = "需人工介入";
            } else if ("120".equals(status)){
                status = "创建未请求菜鸟接口";
            } else if ("-100".equals(status)){
                status = "已取消";
            } else{
                status = "";
            }
            sheet.setColumnView(8, 15);
            sheet.addCell(new Label(8, temp, CommUtil.getStringValue(status), textFormat));
            sheet.setColumnView(9, 15);
            sheet.addCell(new Label(9, temp, CommUtil.getStringValue(map.get("transferOutOrderCode")), textFormat));
            sheet.setColumnView(10, 15);
            sheet.addCell(new Label(10, temp, CommUtil.getStringValue(map.get("transferInOrderCode")), textFormat));
            sheet.setColumnView(11, 15);
            if(map.get("confirmOutTime")!=null){
                String outTime = fmt.format(fmt.parse(map.get("confirmOutTime").toString()));
                sheet.addCell(new Label(11, temp, CommUtil.getStringValue(outTime), textFormat));
            }else{
                sheet.addCell(new Label(11, temp, CommUtil.getStringValue(""), textFormat));
            }
            sheet.setColumnView(12, 15);
            if(map.get("confirmInTime")!=null){
                String inTime = fmt.format(fmt.parse(map.get("confirmInTime").toString()));
                sheet.addCell(new Label(12, temp, CommUtil.getStringValue(inTime), textFormat));
            }else{
                sheet.addCell(new Label(12, temp, CommUtil.getStringValue(""), textFormat));
            }
            sheet.setColumnView(13, 15);
            sheet.addCell(new Label(13, temp, CommUtil.getStringValue(map.get("creater")), textFormat));
            sheet.setColumnView(14, 15);
            if(map.get("createTime")!=null){
                String inTime = fmt.format(fmt.parse(map.get("createTime").toString()));
                sheet.addCell(new Label(14, temp, CommUtil.getStringValue(inTime), textFormat));
            }else{
                sheet.addCell(new Label(14, temp, CommUtil.getStringValue(""), textFormat));
            }
            sheet.setColumnView(15, 15);
            sheet.addCell(new Label(15, temp, CommUtil.getStringValue(map.get("sapErrorMessage")), textFormat));
            temp++;
        }
    }

    /**
     * 创建仓库编码对照信息
     * @param  cainiaoStoreCode
     * @return
     */
    @RequestMapping(value = { "/create_Store_Code" }, method = { RequestMethod.POST })
    public void createStore(
            @RequestParam(required = false) String StoreName,//用户名
            @RequestParam(required = false) String haierStoreCode,//状态
            @RequestParam(required = false) String cainiaoStoreCode,//登录账号
            HttpServletRequest request,HttpServletResponse response
    ) {
        response.addHeader("Content-type", "text/html;charset=utf-8");
        try {
            StoreCodeMapping StoreCode = new StoreCodeMapping();
            StoreCode.setStoreName(StoreName);
            StoreCode.setHaierStoreCode(haierStoreCode);
            StoreCode.setCainiaoStoreCode(cainiaoStoreCode);

            Map<String, Object> retMap = new HashMap<String, Object>();
            Gson gson = new Gson();

            int row = eopStoreCodeService.createStoreCode(StoreCode);
            if(row==0){
                retMap.put("message", "该菜鸟仓库编码已存在！不允许添加重复菜鸟仓库编码！");
            }
            response.getWriter().write(gson.toJson(retMap.get("message")));
            response.getWriter().flush();
            response.getWriter().close();

        } catch (Exception e) {
            logger.error("创建用户异常", e);
            e.printStackTrace();
        }
    }

    /**
     * 根据ID删除仓库编码对照信息
     * @param id
     * @return
     */
    @RequestMapping(value = { "/delete_Store_Code" }, method = { RequestMethod.POST })
    public void deleteStore(
            @RequestParam(required = false) String id,//id
            HttpServletRequest request,HttpServletResponse response)
    {
        response.addHeader("Content-type", "text/html;charset=utf-8");
        Map<String, Object> retMap = new HashMap<String, Object>();
        Gson gson = new Gson();
        try {
            int row = eopStoreCodeService.deleteStoreCodeById(id);
            if(row<0){
                retMap.put("message", "删除失败！！数据不存在");
            }
            if(row==0){
                retMap.put("message", "删除失败");
            }
            response.getWriter().write(gson.toJson(retMap.get("message")));
            response.getWriter().flush();
            response.getWriter().close();

        } catch (Exception e) {
            logger.error("创建用户异常", e);
            e.printStackTrace();
        }
    }


    @RequestMapping(value = { "/update_Store_Code" }, method = { RequestMethod.POST, RequestMethod.GET })
    public void updateStoreCode(@RequestParam("id") String id,
                                  @RequestParam("storeName") String storeName,
                                  @RequestParam("haierStoreCode") String haierStoreCode,
                                  @RequestParam("cainiaoStoreCode") String cainiaoStoreCode,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.addHeader("Content-type", "text/html;charset=utf-8");
        Map<String, Object> retMap = new HashMap<String, Object>();
        Gson gson = new Gson();
        StoreCodeMapping storeCode = new StoreCodeMapping();
        storeCode.setId(Integer.parseInt(id));
        storeCode.setStoreName(storeName.trim());
        storeCode.setHaierStoreCode(haierStoreCode.trim());
        storeCode.setCainiaoStoreCode(cainiaoStoreCode.trim());
        try {
            int row = eopStoreCodeService.updateStoreCode(storeCode);
            if(row==-1) {
                retMap.put("message", "更新仓库编码对照信息失败！要修改的数据不存在");
            }else if(row==-2){
                retMap.put("message", "菜鸟仓库编码不允许重复添加");
            }else if (row==0){
                retMap.put("message", "更新仓库编码对照信息失败！修改异常");
            }
            response.getWriter().write(gson.toJson(retMap.get("message")));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            logger.error("修改用户异常", e);
            e.printStackTrace();
        }
    }



}
