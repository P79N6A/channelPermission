package com.haier.svc.api.controller.purchase;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.ParseException;
import com.google.gson.Gson;
import com.haier.common.ServiceResult;
import com.haier.common.util.ConvertUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.BusinessException;
import com.haier.purchase.data.model.CrmGenuineRejectItem;
import com.haier.purchase.data.model.DataDictionary;
import com.haier.purchase.data.model.OrderOperationLog;
import com.haier.purchase.data.model.WAAddress;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvWarehouse;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.service.StockCenterHopStockService;
import com.haier.stock.service.StockCommonService;
import com.haier.svc.api.controller.util.*;
import com.haier.svc.api.controller.util.date.DateCal;
import com.haier.svc.api.service.CommPurchase;
import com.haier.svc.service.*;
import org.apache.log4j.LogManager;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping(value = "/crmgenuinereject")
@Controller
public class CrmGenuineRejectController {

    // 正品退货状态
    private static final String                  REJECT_ORDER_STATUS = "genuine_reject_flow_flag";
    @Autowired
    private DataDictionaryService dataDictionaryService;
    @Autowired
    CommPurchase commPurchase;
    @Autowired
    private CrmGenuineRejectService crmGenuineRejectService;
    @Autowired
    private PurchaseCommonService purchaseCommonService;
    @Autowired
    private PurchaseBaseCommonService purchaseBaseCommonService;
    @Autowired
    private StockCenterHopStockService stockCenterHopStockService;

    // 导入模板表头信息
    private static final String                  CHECKSTR            = "No.,SO单号,DN单号,渠道,物料号,型号,库位,数量,含税价格,地址,联系人,联系电话";
    @Autowired
    private StockCommonService stockCommonService;
    private final static org.apache.log4j.Logger logger = LogManager
            .getLogger(CrmGenuineRejectController.class);



    /**
     * CRM正品退货跳转
     *
     * @param request
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "crmGenuineRejectList" }, method = { RequestMethod.GET })
    String crmGenuineRejectList(HttpServletRequest request, Map<String, Object> modelMap) {
        Map<String, Object> authMap = new HashMap<>();
        commPurchase.getAuthMap(request, "", "", "", authMap);
        try {
            modelMap.put("authMap", JSON.json(authMap));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "purchase/crmGenuineRejectList";
    }

    /**
     * CRM正品退货点击部分导出、导出Excel
     *
     * @param exportData
     *            导出数据
     * @param response
     * @param request
     * @param modelMap
     *            状态
     * @return 方法执行完毕调用的画面
     */
    @RequestMapping(value = { "/exportCrmGenuineReject.export" })
    void exportCrmGenuineReject(@RequestParam(required = false) String exportData,
                                  HttpServletResponse response, HttpServletRequest request,
                                  Map<String, Object> modelMap) {
        Map<String, Object> params = new HashMap<String, Object>();
        JSONArray exportJson = new JSONArray();
        String[] exportArray = null;
        try {
            if (exportData != null && !exportData.equals("")) {
                exportJson = (JSONArray) JSON.parse(exportData);
                exportArray = new String[exportJson.length()];
                // JSONArray转化为list
                for (int i = 0; i < exportJson.length(); i++) {
                    exportArray[i] = (String) exportJson.get(i);
                }
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
            logger.error("JSON转换失败！ 错误：" + e1.getMessage());
        }
        params.put("wp_order_id_list", exportArray);
        List<CrmGenuineRejectItem> getCrmGenuineRejectData = getCrmGenuineRejectData(params);
        HSSFWorkbook wb= getDetailsData(getCrmGenuineRejectData);
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date=new java.util.Date();
        String str=sdf.format(date);
        String fileName = "CRM正品退货订单导出"+str;
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
     * CRM正品退货全部导出
     *
     * @param wp_order_id_save
     *            退货单号
     * @param request_user_save
     *            申请人
     * @param flow_flag_save
     *            状态
     * @param commit_time_start_save
     *            提交日开始
     * @param commit_time_end_save
     *            提交日结束
     * @param brand_save
     *            品牌
     * @param cbsCategory_save
     *            品类
     * @param product_group_save
     *            产品组
     * @param materials_id_save
     *            物料号
     * @param model_id_save
     *            型号
     * @param channel_save
     *            渠道
     * @param storage_id_save
     *            库位
     * @param response
     */
    @RequestMapping(value = { "exportAllCrmGenuineReject.export" })
    void exportAllCrmGenuineReject(@RequestParam(required = false) String wp_order_id_save,
                                     @RequestParam(required = false) String request_user_save,
                                     @RequestParam(required = false) String flow_flag_save,
                                     @RequestParam(required = false) String commit_time_start_save,
                                     @RequestParam(required = false) String commit_time_end_save,
                                     @RequestParam(required = false) String brand_save,
                                     @RequestParam(required = false) String cbsCategory_save,
                                     @RequestParam(required = false) String product_group_save,
                                     @RequestParam(required = false) String materials_id_save,
                                     @RequestParam(required = false) String model_id_save,
                                     @RequestParam(required = false) String channel_save,
                                     @RequestParam(required = false) String storage_id_save,
                                     Map<String, Object> modelMap, HttpServletRequest request,
                                     HttpServletResponse response) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("wp_order_id", wp_order_id_save);
        params.put("request_user", request_user_save);
        // flow_flag转化为数组
        String[] flow_flag_list = null;
        if (flow_flag_save != null && !"".equals(flow_flag_save)) {
            flow_flag_list = flow_flag_save.split(",");
        }
        params.put("flow_flag", flow_flag_list);
        params.put("commit_time_start", commit_time_start_save);
        if (commit_time_end_save != null && !"".equals(commit_time_end_save)) {
            DateCal dateCal_billstart = new DateCal(commit_time_end_save);
            commit_time_end_save = dateCal_billstart.decDay(-1);
        }
        params.put("commit_time_end", commit_time_end_save);
        params.put("brand", brand_save);
        // 权限Map
        Map<String, Object> authMap = new HashMap<String, Object>();
        // 取得产品组权限List,渠道权限List和品类List
        commPurchase.getAuthMap(request, product_group_save, channel_save,
                cbsCategory_save, authMap);
        params.put("cbsCategory", authMap.get("cbsCatgory"));
        params.put("product_group_id", authMap.get("productGroup"));
        params.put("materials_id", materials_id_save);
        params.put("model_id", model_id_save);
        params.put("channel", authMap.get("channel"));
        params.put("storage_id", storage_id_save);
        // 渠道和产品组，品类数据存入HashMap
        // 产品组
        Map<String, String> productgroupmap = new HashMap<String, String>();
        // 渠道
        Map<String, String> invstockchannelmap = new HashMap<String, String>();
        // 品牌
        Map<String, String> brandMap = new HashMap<String, String>();
        // 取得产品组
        commPurchase.getProductMap(productgroupmap);
        // 取得渠道
        commPurchase.getChannelMap(invstockchannelmap);
        // 取得品牌
        commPurchase.getBrandMap(brandMap);
        List<CrmGenuineRejectItem> getCrmGenuineRejectData = getCrmGenuineRejectData(params);
        HSSFWorkbook wb= getDetailsData(getCrmGenuineRejectData);
        SimpleDateFormat sdf =  new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date=new java.util.Date();
        String str=sdf.format(date);
        String fileName = "CRM正品退货订单导出"+str;
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

    public HSSFWorkbook getDetailsData(List<CrmGenuineRejectItem> list) {


        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("CRM正品退货订单导出");
        int length = CrmRejectExportData.CrmRejectTitle.length;
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
            cell.setCellValue(CrmRejectExportData.CrmRejectTitle[i]);
            cell.setCellStyle(style);
        }


        //向单元格里添加数据
        for(short i=0;i<list.size();i++){
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(list.get(i).getWp_order_id());
            row.createCell(1).setCellValue(list.get(i).getFlow_flag_name());
            row.createCell(2).setCellValue(list.get(i).getSo_id());
            row.createCell(3).setCellValue(list.get(i).getDn_id());
            row.createCell(4).setCellValue(list.get(i).getVom_reverse_in_wa_no());
            row.createCell(5).setCellValue(list.get(i).getCommit_time_display());
            row.createCell(6).setCellValue(list.get(i).getDelivery_time_display());
            row.createCell(7).setCellValue(list.get(i).getWarehouse_out_time_display());
            row.createCell(8).setCellValue(list.get(i).getWarehouse_in_time_display());
            row.createCell(9).setCellValue(list.get(i).getReverse_syn_vom_time_display());
            row.createCell(10).setCellValue(list.get(i).getWa_in_time_displays());
            row.createCell(11).setCellValue(list.get(i).getRequest_user());
            row.createCell(12).setCellValue(list.get(i).getEd_channel_name());
            row.createCell(13).setCellValue(list.get(i).getCategory_id());
            row.createCell(14).setCellValue(list.get(i).getProduct_group_name());
            row.createCell(15).setCellValue(list.get(i).getBrand_name());
            row.createCell(16).setCellValue(list.get(i).getMaterials_id());
            row.createCell(17).setCellValue(list.get(i).getModel_id());
            row.createCell(18).setCellValue(list.get(i).getStorage_id());
            row.createCell(19).setCellValue(list.get(i).getQuantity());
            row.createCell(20).setCellValue(list.get(i).getTax_in_price());
            row.createCell(21).setCellValue(list.get(i).getAddress());
            row.createCell(22).setCellValue(list.get(i).getConcat_person());
            row.createCell(23).setCellValue(list.get(i).getConcat_phone());
            row.createCell(24).setCellValue(list.get(i).getError_msg());

        }
        return wb;

    }
    /**
     * 正品退货导出所需数据处理
     *
     *            状态
     * @return 方法执行完毕调用的画面
     */
    List<CrmGenuineRejectItem> getCrmGenuineRejectData(Map<String, Object> params) {
        ServiceResult<List<CrmGenuineRejectItem>> result = crmGenuineRejectService
                .getCrmGenuineRejectList(params);
        // 产品组
        Map<String, String> productgroupmap = new HashMap<String, String>();
        // 渠道
        Map<String, String> invstockchannelmap = new HashMap<String, String>();
        // 品牌
        Map<String, String> brandMap = new HashMap<String, String>();
        // 取得产品组
        commPurchase.getProductMap(productgroupmap);
        // 取得渠道
        commPurchase.getChannelMap(invstockchannelmap);
        // 取得品牌
        commPurchase.getBrandMap(brandMap);
        // 获取正品退货状态map
        Map<String, String> rejectOrderTypeMap = CommPurchase.getValueMeaningMap(
                dataDictionaryService, REJECT_ORDER_STATUS);
        List<CrmGenuineRejectItem> crmGenuineRejectList = null;
        if (result.getSuccess() && result.getResult() != null) {
            crmGenuineRejectList = result.getResult();

            for (CrmGenuineRejectItem item : crmGenuineRejectList) {
                // 根据渠道id取得渠道名
                item.setEd_channel_name(invstockchannelmap.get(item.getEd_channel_id()));
                // 根据产品组id取得产品组名
                item.setProduct_group_name(productgroupmap.get(item.getProduct_group_id()));
                // 品牌
                item.setBrand_name(brandMap.get(item.getBrand_id()));
                // 设置正品退货状态
                item.setFlow_flag_name(rejectOrderTypeMap.get(String.valueOf(item.getFlow_flag())));
            }
        }
        return crmGenuineRejectList;
    }


    /**
     * CRM正品退货订单履历
     * @param order_id 订单号
     * @param request
     * @param response
     */
    @RequestMapping(value = { "crmOrderOperation" }, method = { RequestMethod.POST })
    public void crmOrderOperation(@RequestParam(required = true) String order_id,
                                  HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> valueSetId = new HashMap<String, Object>();
        params.put("order_id", order_id);
        valueSetId.put("valueSetId", "genuine_reject_flow_flag");
        List<OrderOperationLog> orderoperation = crmGenuineRejectService.getOrderOperationLogInfo(
                params).getResult();
        List<DataDictionary> dataDictionary = dataDictionaryService.getByValueSetId(valueSetId)
                .getResult();
        for (OrderOperationLog item : orderoperation) {
            for (DataDictionary data : dataDictionary) {
                if (String.valueOf(item.getType()).equals(data.getValue())) {
                    item.setType_name(data.getValue_meaning());
                }
            }
        }
        try {

            response.getWriter().write(JsonUtil.toJson(orderoperation));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * CRM正品退货查询
     *
     * @param wp_order_id
     *            退货单号
     * @param request_user
     *            申请人
     * @param flow_flag
     *            状态
     * @param commit_time_start
     *            提交日开始
     * @param commit_time_end
     *            提交日结束
     * @param brand
     *            品牌
     * @param cbsCategory
     *            品类
     * @param product_group
     *            产品组
     * @param materials_id
     *            物料号
     * @param model_id
     *            型号
     * @param channel
     *            渠道
     * @param storage_id
     *            库位
     * @param rows
     * @param page
     * @param response
     */
    @RequestMapping(value = { "findCrmGenuineRejectList" }, method = { RequestMethod.POST })
    void findCrmGenuineRejectList(@RequestParam(required = false) String wp_order_id,
                                  @RequestParam(required = false) String request_user,
                                  @RequestParam(required = false) String flow_flag,
                                  @RequestParam(required = false) String commit_time_start,
                                  @RequestParam(required = false) String commit_time_end,
                                  @RequestParam(required = false) String brand,
                                  @RequestParam(required = false) String cbsCategory,
                                  @RequestParam(required = false) String product_group,
                                  @RequestParam(required = false) String materials_id,
                                  @RequestParam(required = false) String model_id,
                                  @RequestParam(required = false) String channel,
                                  @RequestParam(required = false) String storage_id,
                                  @RequestParam(required = false) String so_id,
                                  @RequestParam(required = false) Integer rows,
                                  @RequestParam(required = false) Integer page,
                                  HttpServletRequest request, HttpServletResponse response) {
        try {
            if (rows == null)
                rows = 20;
            if (page == null)
                page = 1;
            // 权限Map
            Map<String, Object> authMap = new HashMap<String, Object>();
            // 取得产品组权限List,渠道权限List和品类List

            commPurchase.getAuthMap(request, product_group, channel,
                    cbsCategory, authMap);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("wp_order_id", wp_order_id);
            params.put("request_user", request_user);
            // flow_flag转化为数组
            String[] flow_flag_list = null;
            if (flow_flag != null && !"".equals(flow_flag)) {
                flow_flag_list = flow_flag.split(",");
            }
            params.put("flow_flag", flow_flag_list);
            params.put("commit_time_start", commit_time_start);
            if (commit_time_end != null && !"".equals(commit_time_end)) {
                DateCal dateCal_billstart = new DateCal(commit_time_end);
                commit_time_end = dateCal_billstart.decDay(-1);
            }
            params.put("commit_time_end", commit_time_end);
            params.put("brand", brand);
            params.put("cbsCategory", authMap.get("cbsCatgory"));
            params.put("product_group_id", authMap.get("productGroup"));
            params.put("materials_id", materials_id);
            params.put("model_id", model_id);
            params.put("channel", authMap.get("channel"));
            params.put("storage_id", storage_id);
            params.put("so_id", so_id);
            params.put("m", (page - 1) * rows);
            params.put("n", rows);

            // 渠道和产品组，品类数据存入HashMap
            // 产品组
            Map<String, String> productgroupmap = new HashMap<String, String>();
            // 渠道
            Map<String, String> invstockchannelmap = new HashMap<String, String>();
            // 品牌
            Map<String, String> brandMap = new HashMap<String, String>();
            // 取得产品组
            commPurchase.getProductMap(productgroupmap);
            // 取得渠道
            commPurchase.getzChannelMap(invstockchannelmap);
            // 取得品牌
            commPurchase.getBrandMap(brandMap);
            // 获取正品退货状态map
            Map<String, String> rejectOrderTypeMap = CommPurchase.getValueMeaningMap(
                    dataDictionaryService, REJECT_ORDER_STATUS);
            // 调用业务Service
            ServiceResult<List<CrmGenuineRejectItem>> result = crmGenuineRejectService
                    .getCrmGenuineRejectList(params);
            /*
             * //获得条数 ServiceResult<Integer> resultcount =
             * crmGenuineRejectService.getRowCnts();
             */
            if (result.getSuccess() && result.getResult() != null) {
                List<CrmGenuineRejectItem> predictstocklist = result.getResult();

                for (CrmGenuineRejectItem item : predictstocklist) {
                    // 根据渠道id取得渠道名
                    item.setEd_channel_name(invstockchannelmap.get(item.getEd_channel_id()));
                    // 根据产品组id取得产品组名
                    item.setProduct_group_name(productgroupmap.get(item.getProduct_group_id()));
                    // 品牌
                    item.setBrand_name(brandMap.get(item.getBrand_id()));
                    // 设置正品退货状态
                    item.setFlow_flag_name(rejectOrderTypeMap.get(String.valueOf(item
                            .getFlow_flag())));
                }
                Gson gson = new Gson();
                Map<String, Object> retMap = new HashMap<String, Object>();
                retMap.put("total", result.getPager().getRowsCount());
                retMap.put("rows", predictstocklist);
                response.addHeader("Content-type", "text/html;charset=utf-8");
                response.getWriter().write(gson.toJson(retMap));
                response.getWriter().flush();
                response.getWriter().close();
            }
        } catch (IOException e) {
            logger.error("", e);
            throw new BusinessException("失败" + e.getMessage());
        }
    }


    /**
     * 下载Excel模板
     *
     * @param response
     * @return
     */
    @RequestMapping(value = { "/downloadCRMGenuineRejectTemplate" })
    public void exportModel(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "CRM退货单导入模板";
        String sheetName = "CRM退货单导入模板";
        String excelHead = CHECKSTR;
        ExcelExportUtil.downloadDataTemplate(logger, request, response, fileName, sheetName,
                excelHead);
    }

    /**
     * 导入页面跳转
     *
     * @param request
     * @param response
     * @param modelMap
     * @return
     */

    @RequestMapping(value = { "/gotoImportCrmReturnInfo" }, method = { RequestMethod.GET })
    public String importCrmReturnInfo(HttpServletRequest request, HttpServletResponse response,
                                      Map<String, Object> modelMap) {
        String url = request.getHeader("referer");
        modelMap.put("url", url);
        return "purchase/importCrmReturnInfo";
    }


    /**
     * 删除订单
     *
     * @param request
     * @param deleteData
     *            要删除的行
     * @return
     */
    @RequestMapping(value = { "deleteCrmGenuineRejectList" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<String> deleteOrderList(HttpServletRequest request,
                                                  @RequestParam(required = true) String deleteData) {
        HttpJsonResult<String> result = new HttpJsonResult<String>();
        if (deleteData != null) {
            try {
                // 取得提交数据
                JSONArray deletejson = (JSONArray) JSON.parse(deleteData);
                List<String> deleteList = new ArrayList<String>();
                // JSONArray转化为list
                for (int i = 0; i < deletejson.length(); i++) {
                    deleteList.add(deletejson.getString(i));
                }
                // 取得提交者
                String commituser = getUserName();
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("deleteList", deleteList);
                params.put("commituser", commituser);
                // 订单删除
                // TODO 2QILOG 提交日志
                crmGenuineRejectService.deleteCrmGenuineRejectList(params);
            } catch (ParseException e) {
                logger.error("", e);
                throw new BusinessException("JSON转化失败" + e.getMessage());
            }

        }
        result.setMessage("删除成功");
        return result;
    }


    @RequestMapping(value = { "/importCrmReturnInfo" })
    public @ResponseBody
    HttpJsonResult<Map<String, Object>> importCrmReturnInfoData(HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                @RequestParam(required = false) String request_user,
                                                                Map<String, Object> modelMap) {

        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        // 转型为MultipartHttpRequest
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        // 获得文件
        // List<MultipartFile> files = multipartRequest.getFiles("file");
        MultipartFile file = multipartRequest.getFile("file");
        if (file == null) {
            result.setMessage("没有选择导入文件，请选择导入文件后再点击导入操作！");
            return result;
        }
        // 警告信息集合
        String MsgList = "";
        StringBuffer sb = new StringBuffer();

        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        String fileExtName = fileName.substring(index + 1);
        if (!fileExtName.equalsIgnoreCase("xls")) {
            result.setMessage("选择导入文件扩展名必须为xls!");
            return result;
        }
        int count = 0;
        int nullRow = 0;

        // 判断用 产品组code
        String department = "";
        // 判断用 产品组名称
        String departmentName = "";
        // 判断用 渠道code
        String channelCode = "";
        // 品牌
        String brandId = "";
        // 品类
        String categoryName = "";
        // 判断用型号
        String materialDescription = "";
        try {
            List<String[]> list = ExcelReader.readExcel(file.getInputStream(), 1);
            if (list.size() <= 1) {
                result.setMessage("很抱歉！你导入的Excel没有数据记录，请查看重新整理导入！");
                return result;
            }
            if (list.size() > 2001) {
                result.setMessage("很抱歉！一次最多导入2000条数据，请查看重新整理导入！");
                return result;
            }
            // 验证模板表头信息
            String[] firstLineData = list.get(0);
            boolean flag = this.checkDataTemplate(firstLineData, CHECKSTR);
            if (!flag) {
                result.setMessage("很抱歉！你导入的Excel数据格式与系统模板格式存在差异，请下载模板后重新导入！");
                return result;
            }
            // 创建人 create_user
            String createUser = String.valueOf(getUserName());

            // 品类数据存入HashMap
            Map<String, String> categoryMap = new HashMap<String, String>();

            // 渠道数据存入HashMap
            Map<String, String> invstockchannelmap = new HashMap<String, String>();

            // 调用stockCommonService，取得渠道数据
            ServiceResult<List<InvStockChannel>> resultChannel = stockCommonService.getChannels();
            if (resultChannel.getSuccess() && resultChannel.getResult() != null) {
                List<InvStockChannel> invStockChannel = resultChannel.getResult();
                for (InvStockChannel invstockchannel : invStockChannel) {
                    invstockchannelmap.put(invstockchannel.getName(),
                            invstockchannel.getChannelCode());// 将name作为key，channelCode作为value存入map中
                }
            }

            // 读取数据
            for (int i = 1; i < list.size(); i++) {
                String[] str = list.get(i);
                // SO单号
                String so_id = StringUtil.nullSafeString(str[1]).trim();
                // DN单号
                String dn_id = StringUtil.nullSafeString(str[2]).trim();
                // 渠道
                String eb_channel_name = StringUtil.nullSafeString(str[3]).trim();
                // 物料号
                String materials_id = StringUtil.nullSafeString(str[4]).trim();
                // 型号
                String model_id = StringUtil.nullSafeString(str[5]).trim();
                // 库位
                String storage_id = StringUtil.nullSafeString(str[6]).trim();
                // 数量
                String quantity = StringUtil.nullSafeString(str[7]).trim();
                // 含税价格 float
                String tax_in_price = StringUtil.nullSafeString(str[8]).trim();
                // 地址
                String address = StringUtil.nullSafeString(str[9]).trim();
                // 联系人
                String concat_person = StringUtil.nullSafeString(str[10]).trim();
                // 联系电话
                String concat_phone = StringUtil.nullSafeString(str[11]).trim();

                boolean isAllNull = StringUtil.isEmpty(so_id, true)
                        && StringUtil.isEmpty(dn_id, true)
                        && StringUtil.isEmpty(eb_channel_name, true)
                        && StringUtil.isEmpty(materials_id, true)
                        && StringUtil.isEmpty(model_id, true)
                        && StringUtil.isEmpty(storage_id, true)
                        && StringUtil.isEmpty(quantity, true)
                        && StringUtil.isEmpty(tax_in_price, true)
                        && StringUtil.isEmpty(address, true)
                        && StringUtil.isEmpty(concat_person, true)
                        && StringUtil.isEmpty(concat_phone, true);

                if (isAllNull) {
                    nullRow++;
                    continue;
                }
                // 导入模板内容的非空判断****************START*******************************
                // SO单号check
                if (StringUtil.isEmpty(so_id, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【SO单号】不能为空! 请核查后重新导入！";

                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                // 渠道check
                if (StringUtil.isEmpty(eb_channel_name, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【渠道】不能为空! 请核查后重新导入！";

                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                // 物料号check
                if (StringUtil.isEmpty(materials_id, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【物料号】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                // 库位check
                if (StringUtil.isEmpty(storage_id, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【库位】不能为空! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                // 含税价格check（是否非空，待定）
                // if (StringUtil.isEmpty(tax_in_price, true)) {
                // MsgList = "很抱歉！你导入的Excel数据,第" + i +
                // "行数据的【含税价格】不能为空! 请核查后重新导入！";
                // if (StringUtil.isEmpty(MsgList, true)) {
                // sb.append(MsgList);
                // } else {
                // MsgList = MsgList + "<br>";
                // sb.append(MsgList);
                // }
                // continue;
                // }
                // 导入模板内容的非空判断****************END*******************************

                // 导入模板内容的合理性判断及权限判断**************START*******************
                // 渠道（用渠道名称取得渠道code）
                channelCode = invstockchannelmap.get(eb_channel_name);
                // 渠道的正确性判断
                if ("".equals(channelCode) || channelCode == null) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的渠道【" + eb_channel_name
                            + "】无法识别！请检查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                // SO单号的唯一性 （保证SO单号唯一 ，特殊情况状态为已取消的除外）so_id
                ServiceResult<Boolean> checkSoIdResult = crmGenuineRejectService
                        .checkSoidSame(so_id);
                if (!checkSoIdResult.getSuccess() || checkSoIdResult.getResult() == null
                        || !checkSoIdResult.getResult()) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的SO单号【" + so_id + "】已存在！请检查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                // 产品组code取得
                department = commPurchase.getDepartmentByMaterialCode(materials_id);
                if ("".equals(department) || department == null) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的物料号【" + materials_id
                            + "】无法识别！请检查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                // 从品牌型号Map（根据物料号 ），取得型号
                Map<String, String> itemBaseMap = commPurchase.getItemBaseMap(materials_id);
                materialDescription = itemBaseMap.get("material_description");

                // 型号判断，为空则用根据物料号取得的materialDescription，否则要与取得的materialDescription相同
                if (!StringUtil.isEmpty(model_id, true) && !model_id.equals(materialDescription)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的型号【" + model_id + "】不正确! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                // 库位的合理性判断**************START**********************************************
                // 通过电商库位码获取仓库
                ServiceResult<InvWarehouse> invWarehouseResult = purchaseBaseCommonService
                        .getWhByEstorgeId(storage_id);
                if (!invWarehouseResult.getSuccess() || invWarehouseResult.getResult() == null) {

                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【库位】无法识别! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                // 库位的合理性判断**************END**********************************************

                // 导入模板内容的数值形式判断**************START**********************************
                // 数量正确性判断（int型，大于0的整数）
                if (StringUtil.isEmpty(quantity, true) || !CommUtil.canToInt(quantity)
                        || Integer.parseInt(quantity) <= 0) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【数量】应该是大于0的整数! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                // 含税价格 float型判断(非空，则需要正确)
                if (!StringUtil.isEmpty(tax_in_price, true) && !CommUtil.canToFloat(tax_in_price)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【含税价格】数据不合法! 请核查后重新导入！";

                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                // 导入模板内容的数值形式判断**************END*******************************
                // 权限判断
                Map<String, Object> authMap = new HashMap<String, Object>();
                // 取得产品组权限List和渠道权限List
                commPurchase.getAuthMap(request, null, null,
                        null, authMap);

                String[] authChannel = (String[]) authMap.get("channel");
                String[] authProductGroup = (String[]) authMap.get("productGroup");
                String[] authCbsCatgory = (String[]) authMap.get("cbsCategory");
                List<String> channelList = Arrays.asList(authChannel);
                List<String> productGroupList = Arrays.asList(authProductGroup);
                List<String> cbsCatgoryList = Arrays.asList(authCbsCatgory);
                // 判断渠道id是否有权限
                if (!channelList.contains(channelCode)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的渠道【" + eb_channel_name
                            + "】，你没有权限导入！请检查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                // 渠道和产品组数据存入HashMap
                Map<String, String> productgroupmap = new HashMap<String, String>();
                // 取得产品组
                commPurchase.getProductMap(productgroupmap);
                // 判断用 产品组名称
                departmentName = productgroupmap.get(department);

                // 判断产品组id是否有权限
                if (!productGroupList.contains(department)) {

                    if (departmentName != null && !"".equals(departmentName)) {
                        MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的物料号【" + materials_id + "】对应的产品组【"
                                + departmentName + "】，你没有权限导入! 请核查后重新导入！";
                    } else {
                        MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的物料号【" + materials_id
                                + "】对应的产品组ID【" + department + "】，你没有权限导入! 请核查后重新导入！";
                    }

                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                // 品类取得 根据产品code
                commPurchase.getCategoryMap(categoryMap);
                categoryName = categoryMap.get(department);

                // 判断品类是否有权限
                if (!cbsCatgoryList.contains(categoryName)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的产品组ID【" + department + "】对应的品类【"
                            + categoryName + "】，你没有权限导入! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                // 导入模板内容的合理性判断**************END*******************************

                // 导入模板内容不足，其他项取得**************START*****************************

                // 从品牌型号Map（根据物料号 ），取得品牌code
                brandId = itemBaseMap.get("brand_code");

                // 导入模板内容不足，其他项取得**************END*******************************
                // 地址,联系人,联系电话补足
                ServiceResult<List<WAAddress>> invAddressResult = purchaseBaseCommonService
                        .getWAAddressInfo(storage_id);
                // 地址
                if (concat_phone == null || "".equals(address)) {
                    address = invAddressResult.getResult().get(0).getAddress();
                }
                // 联系人
                if (concat_person == null || "".equals(concat_person)) {
                    concat_person = invAddressResult.getResult().get(0).getContact_crm();
                }
                // 电话
                if (concat_phone == null || "".equals(concat_phone)) {
                    concat_phone = invAddressResult.getResult().get(0).getMobilePhone();
                }
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("wp_order_id", commPurchase.getRejectWPOrderId(purchaseCommonService, "T01"));
                // 模板提供项
                params.put("so_id", so_id);
                if (!StringUtil.isEmpty(dn_id, true)) {
                    params.put("dn_id", dn_id);
                }
                params.put("dn_id", dn_id);
                params.put("ed_channel_id", channelCode);
                params.put("materials_id", materials_id);
                params.put("model_id", materialDescription);
                params.put("storage_id", storage_id);
                params.put("quantity", ConvertUtil.toInt(quantity, null));
                if (!StringUtil.isEmpty(tax_in_price, true)) {
                    BigDecimal maxlng1 = new BigDecimal(tax_in_price);
                    params.put("tax_in_price", maxlng1.doubleValue());
                }
                params.put("address", address);
                params.put("concat_person", concat_person);
                params.put("concat_phone", concat_phone);
                // 模板之外插入项
                params.put("product_group_id", department);
                params.put("category_id", categoryName);
                params.put("brand_id", brandId);
                // 申请人
                params.put("request_user", createUser);
                params.put("create_user", createUser);

                // 创建T+2订单信息表单
                ServiceResult<Boolean> insResult = crmGenuineRejectService
                        .insertCrmReturnInfoList(params);
                if (insResult.getSuccess() && insResult.getResult()) {
                    count++;
                } else {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据插入DB时失败！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
            }

            modelMap.put("total", list.size() - 1 - nullRow);
            modelMap.put("ignore", list.size() - count - 1 - nullRow);
            modelMap.put("success", count);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("文件导入数据库失败", e);
            result.setMessage("很抱歉！你导入的Excel数据导入失败，请联系技术负责人！");
            return result;
        }

        modelMap.put("warn", sb.toString());
        result.setData(modelMap);
        return result;
    }

    /**
     * 取消退货单
     *
     * @param request
     * @param
     *
     */
    @RequestMapping(value = { "stopCrmGenuineRejectList" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<String> stopCrmGenuineRejectList(HttpServletRequest request,
                                                           @RequestParam(required = true) String stopData) {
        HttpJsonResult<String> result = new HttpJsonResult<String>();
        if (stopData != null && !stopData.equals("")) {
            List<String> stopList = new ArrayList<String>();
            String stopUser = getUserName();
            List<Integer> flow_flag = new ArrayList<Integer>();

            try {
                // 取得取消数据
                JSONArray stopjson = (JSONArray) JSON.parse(stopData);
                // JSONArray转化为list
                for (int i = 0; i < stopjson.length(); i++) {
                    stopList.add(stopjson.getString(i));

                    Map<String, Object> orderMap = new HashMap<String, Object>();
                    orderMap.put("wp_order_id", stopjson.getString(i));
                    ServiceResult<List<CrmGenuineRejectItem>> crmresult = crmGenuineRejectService
                            .getCrmGenuineRejectList(orderMap);
                    CrmGenuineRejectItem crmGenuineRejectItem = crmresult.getResult().get(0);
                    flow_flag.add(crmGenuineRejectItem.getFlow_flag());
                }
                // 取消退货单

                ServiceResult<Boolean> serviceResult = crmGenuineRejectService
                        .stopCrmGenuineRejectList(stopList);
                if (serviceResult.getResult()) {
                    result.setMessage(serviceResult.getMessage());
                    result.setSuccess(true);
                } else {
                    result.setMessage(serviceResult.getMessage());
                }
            } catch (ParseException e) {
                logger.error("", e);
                result.setMessage("终止退货失败");
                throw new BusinessException("JSON转化失败" + e.getMessage());
            }
            // TODO 2QILOG 提交日志
            Map<String, Object> logMap = new HashMap<String, Object>();
            logMap.put("orderidList", stopList);
            logMap.put("operateuser", stopUser);
            logMap.put("flow_flag", flow_flag);
            logMap.put("content", "CRM终止退货,状态变更为逆向同步到VOM");
            logMap.put("result", result);
            CrmLog(logMap);
        }
        result
                .setMessage(result.getMessage().substring(0, result.getMessage().indexOf(" VOM提单号：")));
        return result;
    }

    /**
     * 正品退货取消入WA提单
     *
     * @param request
     * @param stopData
     *            要取消入WA提单的行
     * @return
     */
    @RequestMapping(value = { "cancelInWaCrmGenuineRejectList" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<String> cancelInWaCrmGenuineRejectList(HttpServletRequest request,
                                                                 @RequestParam(required = true) String stopData) {
        HttpJsonResult<String> result = new HttpJsonResult<String>();
        if (stopData != null) {
            List<String> stopList = new ArrayList<String>();
            // 操作者
            String stopUser = getUserName();
            List<Integer> flow_flag = new ArrayList<Integer>();
            try {
                // 取得提交数据
                JSONArray stopjson = (JSONArray) JSON.parse(stopData);

                // JSONArray转化为list
                for (int i = 0; i < stopjson.length(); i++) {
                    stopList.add(stopjson.getString(i));

                    Map<String, Object> orderMap = new HashMap<String, Object>();
                    orderMap.put("wp_order_id", stopjson.getString(i));
                    ServiceResult<List<CrmGenuineRejectItem>> crmresult = crmGenuineRejectService
                            .getCrmGenuineRejectList(orderMap);
                    CrmGenuineRejectItem crmGenuineRejectItem = crmresult.getResult().get(0);
                    flow_flag.add(crmGenuineRejectItem.getFlow_flag());
                }

                Map<String, Object> params = new HashMap<String, Object>();
                params.put("stopList", stopList);
                params.put("stopUser", stopUser);
                // 取消入WA提单
                ServiceResult<Boolean> serviceResult = crmGenuineRejectService
                        .cancelInWaCrmGenuineRejectList(stopList);
                if (serviceResult.getResult()) {
                    result.setMessage("取消入WA提单成功" + serviceResult.getMessage());
                    result.setSuccess(true);
                } else {
                    result.setMessage(serviceResult.getMessage());
                }
            } catch (ParseException e) {
                logger.error("", e);
                result.setMessage("取消入WA提单失败");
                throw new BusinessException("JSON转化失败" + e.getMessage());
            }
            // TODO 2QILOG 提交日志
            Map<String, Object> logMap = new HashMap<String, Object>();
            logMap.put("orderidList", stopList);
            logMap.put("operateuser", stopUser);
            logMap.put("flow_flag", flow_flag);
            logMap.put("content", "CRM取消入WA提单,状态变更为已出库");
            logMap.put("result", result);
            CrmLog(logMap);
        }
        result
                .setMessage(result.getMessage().substring(0, result.getMessage().indexOf(" VOM提单号：")));
        return result;
    }

    /**
     * 取消退货单
     *
     * @param request
     * @param cancelData
     *            取消数据
     */
    @RequestMapping(value = { "cancelCrmGenuineRejectList" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<String> revokeOrderList(HttpServletRequest request,
                                                  @RequestParam(required = true) String cancelData) {
        HttpJsonResult<String> result = new HttpJsonResult<String>();
        if (cancelData != null && !cancelData.equals("")) {
            List<String> cancelList = new ArrayList<String>();
            String cancelUser = getUserName();
            List<Integer> flow_flag = new ArrayList<Integer>();

            try {
                // 取得取消数据
                JSONArray canceljson = (JSONArray) JSON.parse(cancelData);
                // JSONArray转化为list
                for (int i = 0; i < canceljson.length(); i++) {
                    cancelList.add(canceljson.getString(i));

                    Map<String, Object> orderMap = new HashMap<String, Object>();
                    orderMap.put("wp_order_id", canceljson.getString(i));
                    ServiceResult<List<CrmGenuineRejectItem>> crmresult = crmGenuineRejectService
                            .getCrmGenuineRejectList(orderMap);
                    CrmGenuineRejectItem crmGenuineRejectItem = crmresult.getResult().get(0);
                    flow_flag.add(crmGenuineRejectItem.getFlow_flag());
                }
                // 取消退货单
                result.setMessage(crmGenuineRejectService.updateFlowFlagCancel(cancelList));
                if (result.getMessage().length() == 10) {
                    result.setSuccess(true);
                }
            } catch (ParseException e) {
                logger.error("", e);
                result.setMessage("正品退货取消订单失败");
                throw new BusinessException("JSON转化失败" + e.getMessage());
            }
            // TODO 2QILOG 提交日志
            Map<String, Object> logMap = new HashMap<String, Object>();
            logMap.put("orderidList", cancelList);
            logMap.put("operateuser", cancelUser);
            logMap.put("flow_flag", flow_flag);
            logMap.put("content", "CRM取消退货单,状态变更为已取消");
            logMap.put("result", result);
            CrmLog(logMap);
        }
        return result;
    }


    /**
     * 正品退货订单状态更新日志
     *
     * @param logMap
     */
    private void CrmLog(Map<String, Object> logMap) {
        List<String> orderidList = new ArrayList<String>();
        orderidList = (List<String>) logMap.get("orderidList");
        String operateuser = (String) logMap.get("operateuser");
        String content = (String) logMap.get("content");
        HttpJsonResult<String> result = (HttpJsonResult<String>) logMap.get("result");
        List<OrderOperationLog> loglist = new ArrayList<OrderOperationLog>();
        List<Integer> flow_flag = (List<Integer>) logMap.get("flow_flag");
        for (int i = 0; i < orderidList.size(); i++) {

            OrderOperationLog orderOperationLog = new OrderOperationLog();
            if (result.getSuccess()) {
                orderOperationLog.setIs_sucess("1");
            } else {
                orderOperationLog.setIs_sucess("0");
                content = content.substring(0, content.indexOf(","));
            }
            orderOperationLog.setOrder_id(orderidList.get(i));
            orderOperationLog.setContent(content);
            orderOperationLog.setOperate_user(operateuser);
            orderOperationLog.setRemark(result.getMessage());
            orderOperationLog.setType(flow_flag.get(i));
            orderOperationLog.setSystem("采购平台");
            loglist.add(orderOperationLog);
            try {
                crmGenuineRejectService.insertOrderOperationLog(loglist);
            } catch (Exception e) {
                // TODO: handle exception
                logger.error("操作日志写入失败：", e);
            }
        }
    }

    /**
     * 判断导入文档表头是否正确
     *
     * @param firstLineData
     * @param CHECKSTR
     * @return
     */
    public boolean checkDataTemplate(String[] firstLineData, String CHECKSTR) {
        boolean flag = false;
        StringBuffer sb = new StringBuffer();
        for (String str : firstLineData) {
            if (sb.length() > 0)
                sb.append(",");
            sb.append(str.trim());
        }
        String nullStr = sb.toString().replace(CHECKSTR, "").replace(",", "");
        if (nullStr == null || "".equals(nullStr)) {
            flag = true;
        }
        return flag;
    }


    /**
     * 订单提交
     *
     * @param request
     * @param commitData
     *            提交数据
     */
    @RequestMapping(value = { "commitCrmGenuineRejectList" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<String> commitCrmGenuineRejectList(HttpServletRequest request,
                                                             @RequestParam(required = true) String commitData) {
        HttpJsonResult<String> result = new HttpJsonResult<String>();
        if (commitData != null) {
            try {
                // 取得提交数据
                JSONArray commitjson = (JSONArray) JSON.parse(commitData);
                List<String> commitList = new ArrayList<String>();
                // JSONArray转化为list
                for (int i = 0; i < commitjson.length(); i++) {
                    commitList.add(commitjson.getString(i));
                }
                // 取得提交者
                String commituser = getUserName();
                Map<String, Object> params = new HashMap<String, Object>();
                // xuelin.zhao start 获得crm退货单 并进行占货处理
                params.put("wp_order_id_list", commitList);
                ServiceResult<List<CrmGenuineRejectItem>> orderInfos = crmGenuineRejectService
                        .getCrmGenuineRejectList(params);
                List<OrderOperationLog> loglist = new ArrayList<OrderOperationLog>();
                // 占货成功list
                List<String> commitSuccessList = new ArrayList<String>();
                if (orderInfos.getSuccess() && orderInfos.getResult() != null) {
                    for (CrmGenuineRejectItem item : orderInfos.getResult()) {
                        // 占库
                        ServiceResult<String> resu = this.stockCenterHopStockService.frozeStockQty(
                                item.getMaterials_id(), item.getStorage_id(), item.getQuantity(),
                                item.getWp_order_id(), item.getEd_channel_id(),
                                InventoryBusinessTypes.FROZEN_BY_ZBCC);

                        if (resu.getResult() != null && resu.getSuccess()) {
                            commitSuccessList.add(item.getWp_order_id());
                            item.setError_msg("");
                        } else {
                            CrmGenuineRejectItem crmGenuineRejectItem = new CrmGenuineRejectItem();
                            crmGenuineRejectItem.setWp_order_id(item.getWp_order_id());
                            crmGenuineRejectItem.setError_msg(resu.getMessage());
                            crmGenuineRejectService.updateRemark(crmGenuineRejectItem);
                            // 占货失败信息
                            item.setError_msg(resu.getMessage());
                        }
                        // TODO 2QILOG 提交日志
                        OrderOperationLog orderOperationLog = new OrderOperationLog();
                        orderOperationLog.setOrder_id(item.getWp_order_id());
                        orderOperationLog.setType(item.getFlow_flag());
                        orderOperationLog.setContent("CRM提交订单,占货");
                        orderOperationLog.setSystem("采购平台");
                        orderOperationLog.setOperate_user(commituser);
                        if (resu.getSuccess()) {
                            orderOperationLog.setIs_sucess("1");
                            orderOperationLog.setRemark(resu.getMessage());
                        } else {
                            orderOperationLog.setIs_sucess("0");
                            orderOperationLog.setRemark("冻结库存失败");
                        }
                        loglist.add(orderOperationLog);
                    }
                    try {
                        crmGenuineRejectService.insertOrderOperationLog(loglist);
                    } catch (Exception e) {
                        logger.info("操作日志写入失败：", e);
                    }
                }
                params.put("commitList", commitSuccessList);
                // params.put("orderidList", commitList);
                // xuelin.zhao end
                // params.put("commitList", commitList);
                params.put("commituser", commituser);
                // 订单提交更新
                // TODO 2QILOG 提交日志
                ServiceResult<Boolean> result1=crmGenuineRejectService.commitCrmGenuineRejectList(params);
                result.setMessage(result1.getMessage());
                result.setSuccess(result1.getSuccess());
            } catch (ParseException e) {
                logger.error("", e);
                throw new BusinessException("JSON转化失败" + e.getMessage());
            }

        }
//        result.setMessage("更新成功");
        return result;
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
