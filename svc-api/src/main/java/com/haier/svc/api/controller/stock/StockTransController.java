package com.haier.svc.api.controller.stock;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.EncryptUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvStockTransaction;
import com.haier.svc.api.controller.stock.mode.StockTransModel;
import com.haier.svc.api.controller.util.PagerInfo;
import com.haier.svc.api.controller.util.WebUtil;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * 库存交易
 * Created by 钊 on 2014/12/23.
 */
@Controller
@RequestMapping("/stockTrans")
public class StockTransController {
    private static org.apache.log4j.Logger logger       = org.apache.log4j.LogManager
            .getLogger(StockTransController.class);
    /*当前页数*/
    private int                  pageIndex  = 1;
    /*分页尺寸*/
    private int                  pageSize   = 10;
    @Autowired
    private StockTransModel transModel;

    @RequestMapping(value = { "/lessStockTransDelay" }, method = { RequestMethod.GET })
    String getLessStockTransDelay() {
        return "stock/lesStockTransDelayList";
    }

    @RequestMapping(value = { "/invStockTrans.html" }, method = { RequestMethod.GET })
    String geInvStockTransList() {
        return "stock/invStockTrans";
    }

    @RequestMapping(value = { "lessStockTransChannels.html" }, method = { RequestMethod.POST })
    public void getChannels(HttpServletResponse response) {
        try {
        	response.setCharacterEncoding("utf-8");
            List<InvStockChannel> channels = transModel.getChannels();
            response.getWriter().write(JsonUtil.toJson(channels));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @RequestMapping(value = { "lesStockTransDelayList.html" }, method = { RequestMethod.POST })
    public void loadDelayLessTrans(@RequestParam(required = false) int page,
                                   @RequestParam(required = false) int rows,
                                   HttpServletResponse response) {
        try {
            if (page <= 0) {
                page = 1;
            }
            if (rows <= 0) {
                rows = 1;
            }
            List<LesStockTransQueue> delayLessStockTrans = transModel.getDelayLessStockTrans(page,
                rows);
            response.setCharacterEncoding("UTF-8");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("total", transModel.getTotalOfLessDelayTrans());
            map.put("rows", delayLessStockTrans);
            Gson gson = new Gson();
            response.getWriter().write(gson.toJson(map));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @RequestMapping(value = { "saveLessTransChannel.html" }, method = { RequestMethod.POST })
    public void saveChannel(@RequestParam(required = true) int id,
                            @RequestParam(required = true) String channel,
                            HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        try {
        	response.setCharacterEncoding("utf-8");
            String userName = WebUtil.readCookie(request, WebUtil.CBS_USER_NAME);

            if (!StringUtil.isEmpty(userName)) {
                userName = EncryptUtil.fromBASE64(userName);
            }

            if (StringUtil.isEmpty(userName)) {
                retMap.put("flag", "F");
                retMap.put("errMsg", "请登录！");
            } else {
                ServiceResult<Boolean> result = transModel.reviseLessStockTransChannel(id, channel,
                    userName);
                if (result.getSuccess() && result.getResult()) {
                    retMap.put("flag", "S");
                } else {
                    retMap.put("flag", "F");
                    retMap.put("errMsg", result.getMessage());
                }
            }
            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @RequestMapping(value = { "queryInvStockTrans.html" }, method = { RequestMethod.POST })
    public void queryInvStockTransactions(@RequestParam(required = false) String corder_sn,
                                          @RequestParam(required = false) String sku,
                                          @RequestParam(required = false) String sec_code,
                                          @RequestParam(required = false) String bill_type,
                                          @RequestParam(required = false) String mark,
                                          @RequestParam(required = false) String item_property,
                                          @RequestParam(required = false) String process_status,
                                          @RequestParam(required = false) String bill_time_s,
                                          @RequestParam(required = false) String bill_time_e,
                                          @RequestParam(required = false) int page,
                                          @RequestParam(required = false) int rows,
                                          HttpServletResponse response) {
        try {
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            parameterMap.put("corder_sn", corder_sn);
            parameterMap.put("sku", sku);
            parameterMap.put("sec_code", sec_code);
            parameterMap.put("item_property", item_property);
            parameterMap.put("process_status", process_status);
            parameterMap.put("bill_type", bill_type);
            parameterMap.put("mark", mark);
            parameterMap.put("bill_time_s", bill_time_s);
            parameterMap.put("bill_time_e", bill_time_e);

            if (page <= 0) {
                page = 1;
            }
            if (rows <= 0) {
                rows = 10;
            }
            PagerInfo pagerInfo = new PagerInfo(rows, page);
            parameterMap.put("start", pagerInfo.getStart());
            parameterMap.put("size", pagerInfo.getPageSize());

            List<Map<String, Object>> result = transModel.getInvStockTransList(parameterMap);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("total", transModel.getTotalOfInvTrans());
            map.put("rows", result);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(JsonUtil.toJson(map));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
    }

    @RequestMapping(value = { "/exportStockTrans.html" })
    String exportBaseInventoryList(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(required = false) String corder_sn,
                                   @RequestParam(required = false) String sku,
                                   @RequestParam(required = false) String sec_code,
                                   @RequestParam(required = false) String bill_type,
                                   @RequestParam(required = false) String mark,
                                   @RequestParam(required = false) String item_property,
                                   @RequestParam(required = false) String process_status,
                                   @RequestParam(required = false) String bill_time_s,
                                   @RequestParam(required = false) String bill_time_e,
                                   Map<String, Object> modelMap) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("corder_sn", corder_sn);
        parameterMap.put("sku", sku);
        parameterMap.put("sec_code", sec_code);
        parameterMap.put("item_property", item_property);
        parameterMap.put("process_status", process_status);
        parameterMap.put("bill_type", bill_type);
        parameterMap.put("mark", mark);
        parameterMap.put("bill_time_s", bill_time_s);
        parameterMap.put("bill_time_e", bill_time_e);
        parameterMap.put("start", 0);
        parameterMap.put("size", 5000);

        List<Map<String, Object>> items = transModel.getInvStockTransList(parameterMap);
        modelMap.put("rowList", items);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String fileName = "库存交易列表";
        try {
            fileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".xls\"");
        return "stock/invStockTransExport";

        /*return "stock/invStockTrans";*/
    }
    
    @RequestMapping(value = { "/invStockTransImport.html" }, method = { RequestMethod.GET })
    String geInvStockTransImportList() {
        return "stock/invStockTransImport";
    }
    
    /**
     * 手动导入库存交易页面多条件查询功能
     * @param corder_sn
     * @param sku
     * @param sec_code
     * @param bill_type
     * @param mark
     * @param item_property
     * @param process_status
     * @param bill_time_s
     * @param bill_time_e
     * @param pageIndex
     * @param modelMap
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "/queryInvStockTransImport.html" } ,method = { RequestMethod.GET })
    public String queryInvStockTransactionsImport(@RequestParam(required = false) String corder_sn,
                                          @RequestParam(required = false) String sku,
                                          @RequestParam(required = false) String sec_code,
                                          @RequestParam(required = false) String bill_type,
                                          @RequestParam(required = false) String mark,
                                          @RequestParam(required = false) String item_property,
                                          @RequestParam(required = false) String process_status,
                                          @RequestParam(required = false) String bill_time_s,
                                          @RequestParam(required = false) String bill_time_e,
                                          @RequestParam(required = false) Integer pageIndex,
                                          Map<String, Object> modelMap,HttpServletRequest request,
                                          HttpServletResponse response) {
        this.pageIndex = pageIndex == null || pageIndex <= 0 ? 1 : pageIndex;
        PagerInfo page = new PagerInfo(this.pageSize, this.pageIndex);
        try {
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            parameterMap.put("corder_sn", corder_sn);
            parameterMap.put("sku", sku);
            parameterMap.put("sec_code", sec_code);
            parameterMap.put("item_property", item_property);
            parameterMap.put("process_status", process_status);
            parameterMap.put("bill_type", bill_type);
            parameterMap.put("mark", mark);
            parameterMap.put("bill_time_s", bill_time_s);
            parameterMap.put("bill_time_e", bill_time_e);
          
            parameterMap.put("start", page.getStart());
            parameterMap.put("size", page.getPageSize());
            

            int listcount =  transModel.findInvStockTransCount(parameterMap);
            if (listcount > 0) {
                List<Map<String, Object>> list = transModel.findInvStockTransList(parameterMap);
                page.setRowsCount(listcount);
                modelMap.put("rowList", list);
            }
            modelMap.put("pager", page);
        } catch (Exception e) {
            modelMap.put("pager", page);
        }
        return "stock/invStockTransImportRow";
    }
    /**
     * 导出手动导入库存交易页面多条件查询功能
     * @param corder_sn
     * @param sku
     * @param sec_code
     * @param bill_type
     * @param mark
     * @param item_property
     * @param process_status
     * @param bill_time_s
     * @param bill_time_e
     * @param pageIndex
     * @param modelMap
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "/exportInvStockTransImport.html" } ,method = { RequestMethod.GET })
    public String exportInvStockTransImport(@RequestParam(required = false) String corder_sn,
                                          @RequestParam(required = false) String sku,
                                          @RequestParam(required = false) String sec_code,
                                          @RequestParam(required = false) String bill_type,
                                          @RequestParam(required = false) String mark,
                                          @RequestParam(required = false) String item_property,
                                          @RequestParam(required = false) String process_status,
                                          @RequestParam(required = false) String bill_time_s,
                                          @RequestParam(required = false) String bill_time_e,
                                          Map<String, Object> modelMap,HttpServletRequest request,
                                          HttpServletResponse response) {
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            parameterMap.put("corder_sn", corder_sn);
            parameterMap.put("sku", sku);
            parameterMap.put("sec_code", sec_code);
            parameterMap.put("item_property", item_property);
            parameterMap.put("process_status", process_status);
            parameterMap.put("bill_type", bill_type);
            parameterMap.put("mark", mark);
            parameterMap.put("bill_time_s", bill_time_s);
            parameterMap.put("bill_time_e", bill_time_e);
            parameterMap.put("size", -1);
            List<Map<String, Object>> list = transModel.findInvStockTransList(parameterMap);
            modelMap.put("rowList", list);
            //生成excel
            String charset = "UTF-8";
          String  fileName = "库存交易";
            try {
                Enumeration<?> eunm = request.getHeaders("User-Agent");
                String browertype = "";
                while (eunm.hasMoreElements()) {
                    browertype = eunm.nextElement().toString();
                }
                if (browertype != null && browertype.contains("Firefox")) { //判断浏览器类型
                    fileName = new String(fileName.getBytes(charset), "ISO-8859-1");
                } else {
                    fileName = URLEncoder.encode(fileName, charset);
                }

                response.setContentType("application/vnd.ms-excel;charset=" + charset);
                response.setHeader("Content-disposition", "attachment; filename=\"" + fileName
                                                          + ".xls\"");
            } catch (Exception e) {
                logger.error("[StockTransController][exportInvStockTransImport]:" + fileName + "文件下载失败!", e);
            }
        return "stock/invStockTransExportRow";
    }
    
    @RequestMapping(value = { "/importInvStockTransData.html" })
 public    String importInvStockTransData(HttpServletRequest request,
                                                                 HttpServletResponse response,
                                                                 Map<String, Object> modelMap) {

        // 转型为MultipartHttpRequest   
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        // 获得文件
        //        List<MultipartFile> files = multipartRequest.getFiles("file");
        MultipartFile file = multipartRequest.getFile("file");
        Map<String, Object> error = new HashMap<String, Object>();
        if (file == null) {
            error.put("error","没有选择导入文件，请选择导入文件后再点击导入操作！");
            modelMap.put("result", error);
            return "stock/eisInterfaceDataText";
        }

        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        String fileExtName = fileName.substring(index + 1);
        if (!fileExtName.equalsIgnoreCase("xls")) {
            error.put("error","选择导入文件扩展名必须为xls");
            modelMap.put("result", error);
            return "stock/eisInterfaceDataText";
        }
        Workbook workbook = null;
        InputStream is = null;
        Sheet sheet =null;
        try {
                is = file.getInputStream();
                workbook = Workbook.getWorkbook(is);
                sheet = workbook.getSheet(0);
            if (sheet.getRows() <= 1) {
                error.put("error","很抱歉！你导入的Excel没有数据记录，请查看重新整理导入！");
                modelMap.put("result", error);
                return "stock/eisInterfaceDataText";
            }
            List<InvStockTransaction> listData = new ArrayList<InvStockTransaction>();
            // 行数(表头的目录不需要，从1开始)
            for (int i = 1; i < sheet.getRows(); i++) {
                String sku = StringUtil.nullSafeString(sheet.getCell(0, i).getContents()).trim();
                if (sku.equals("")) {
                    continue;
                }
                String externalSecCode = StringUtil.nullSafeString(sheet.getCell(1, i).getContents()).trim();
                String channelCode = StringUtil.nullSafeString(sheet.getCell(2, i).getContents()).trim();
                long l=System.currentTimeMillis();
                try {
                    //防止l值相同
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String corderSn ="WD"+l+"XF";
                String quantity = StringUtil.nullSafeString(sheet.getCell(3, i).getContents()).trim();
                String mark = StringUtil.nullSafeString(sheet.getCell(4, i).getContents()).trim();
                String billType=StringUtil.nullSafeString(sheet.getCell(5, i).getContents().trim());
                String itemProperty=StringUtil.nullSafeString(sheet.getCell(6, i).getContents().trim());
                String secCode=transModel.getSectionByWhCodeAndChannelCode(externalSecCode, channelCode,itemProperty);
                if (StringUtil.isEmpty(sku, true)) {
                    error.put("error","很抱歉！你导入的Excel数据," + (i + 1) + "行物料号不能为空! 请核查后重新导入！");
                    modelMap.put("result", error);
                    return "stock/eisInterfaceDataText";
                }
                if (StringUtil.isEmpty(externalSecCode, true)) {
                    error.put("error","很抱歉！你导入的Excel数据," + (i + 1) + "行共享库位不能为空! 请核查后重新导入！");
                    modelMap.put("result", error);
                    return "stock/eisInterfaceDataText";
                }
                if (StringUtil.isEmpty(channelCode, true)) {
                    error.put("error","很抱歉！你导入的Excel数据," + (i + 1)+ "行渠道不能为空! 请核查后重新导入！");
                    modelMap.put("result", error);
                    return "stock/eisInterfaceDataText";
                }
                if (StringUtil.isEmpty(corderSn, true)) {
                    error.put("error","很抱歉！你导入的Excel数据," + (i + 1) + "行网单号不能为空! 请核查后重新导入！");
                    modelMap.put("result", error);
                    return "stock/eisInterfaceDataText";
                }
                if (StringUtil.isEmpty(quantity, true)) {
                    error.put("error","很抱歉！你导入的Excel数据," + (i + 1) + "行数量不能为空! 请核查后重新导入！");
                    modelMap.put("result", error);
                    return "stock/eisInterfaceDataText";
                }
                if (StringUtil.isEmpty(mark, true)) {
                    error.put("error","很抱歉！你导入的Excel数据," + (i + 1) + "行借贷标志不能为空! 请核查后重新导入！");
                    modelMap.put("result", error);
                    return "stock/eisInterfaceDataText";
                }
                if (!(mark.equalsIgnoreCase("H")||mark.equalsIgnoreCase("S"))) {
                    error.put("error","很抱歉！你导入的Excel数据," + (i + 1) + "行借贷标志值不符合! 请核查后重新导入！");
                    modelMap.put("result", error);
                    return "stock/eisInterfaceDataText";
                }
                if (StringUtil.isEmpty(itemProperty, true)) {
                    error.put("error","很抱歉！你导入的Excel数据," + (i + 1) + "行批次不能为空! 请核查后重新导入！");
                    modelMap.put("result", error);
                    return "stock/eisInterfaceDataText";
                }
                Cell cell = sheet.getCell(7, i);
                Date billTime=null;
                if (cell.getType() == CellType.DATE){
                    try {
                        billTime = ((DateCell) cell).getDate();
                        TimeZone zone = TimeZone.getTimeZone("GMT");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        sdf.setTimeZone(zone);
                        String cellDate = sdf.format(billTime);
                        billTime = DateUtil.parse(cellDate, "yyyy-MM-dd HH:mm:ss");
                    } catch (Exception e) {
                        error.put("error","很抱歉！你导入的Excel数据,第" + (i + 1) + "行交易时间格式不正确! 请核查后重新导入！");
                        return "stock/eisInterfaceDataText";
                    }
                }
                
                InvStockTransaction invStockTransaction= new InvStockTransaction();
                invStockTransaction.setAddTime(new Date());
                invStockTransaction.setBillTime(billTime);
                invStockTransaction.setBillType(billType);
                invStockTransaction.setBusinessProcessStatus(2);
                invStockTransaction.setExternalSecCode(externalSecCode.toUpperCase());
                invStockTransaction.setChannelCode(channelCode.toUpperCase());
                invStockTransaction.setCorderSn(corderSn);
                invStockTransaction.setIsDelay(0);
                invStockTransaction.setIsFrozen(0);
                invStockTransaction.setIsRelease(0);
                invStockTransaction.setItemProperty(itemProperty);
                invStockTransaction.setMark(mark.toUpperCase());
                invStockTransaction.setProcessStatus(InvStockTransaction.PROCESS_STATUS_INIT);
                invStockTransaction.setQuantity(Integer.valueOf(quantity));
                invStockTransaction.setSecCode(secCode);
                invStockTransaction.setSku(sku);
                listData.add(invStockTransaction);
            }
          
            if(listData.size()>0){
                transModel.insertInvStockTransaction(listData);
            }
         } catch (BiffException e) {
            error.put("error","无法解析Excel文件！");
            modelMap.put("result", error);
            return "stock/eisInterfaceDataText";
        } catch (BusinessException e) {
            error.put("error","无法解析Excel文件！");
            modelMap.put("result", error);
            return "stock/eisInterfaceDataText";
        } catch (Exception e) {
            logger.error("处理上传的出入库记录文件" + file.getOriginalFilename() + "失败：", e);
            error.put("error","处理失败，请稍后重试，如果仍无法处理请联系管理员");
            modelMap.put("result", error);
            return "stock/eisInterfaceDataText";
        } finally {
            if (workbook != null)
                workbook.close();
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                }
        }
        error.put("error","导入成功！");
        modelMap.put("result", error);
        return "stock/eisInterfaceDataText";
    }
    /**
     * 下载
     * @param request
     * @param response
     * @param userAgent
     * @throws IOException
     */
    @RequestMapping(value = { "/downloadImportIvnTransferTemplate.html" }, method = { RequestMethod.GET })
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response,
                                 @RequestHeader(value = "user-agent") String userAgent)
                                                                                       throws IOException {
        OutputStream os = response.getOutputStream();
        try {

            String fileName = null;
            String innerFileName = null;

            fileName = "库存交易模板.xls";
            innerFileName = "dr_inv_transfer_template.xls";

            response.reset();
            response.setHeader("Content-Disposition",
                "attachment; filename=" + this.formatFileName(fileName, userAgent));
            response.setContentType("application/octet-stream; charset=utf-8");
            String path = request.getSession().getServletContext()
                .getRealPath("/resources/stock/" + innerFileName);
            os.write(FileUtils.readFileToByteArray(new File(path)));
            os.flush();
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }
    /**
     * 格式文档名称
     * @param fileName
     * @param userAgent
     * @return
     */
    private String formatFileName(String fileName, String userAgent) {
        try {
            if (userAgent.toLowerCase().indexOf("firefox") > 0) {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            } else if (userAgent.toUpperCase().indexOf("MSIE") > 0) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else if (userAgent.toUpperCase().indexOf("CHROME") > 0
                       || userAgent.toUpperCase().indexOf("SAFARI") > 0) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }
        } catch (Exception e) {
        }
        return fileName;
    }
    
    
    /** 
     * 接口状态更新(重推:2 禁止重推: 3)
     * @param eaiDataLogId 每行数据的唯一id
     * @param status 接口状态
     * */
    @RequestMapping(value = { "/updateTransactionStatus.html" }, method = { RequestMethod.GET })
    String updateTransactionStatus(Map<String, Object> modelMap,
                                 @RequestParam(required = true) String eaiDataLogId,
                                 @RequestParam(required = true) String rowStatus,
                                 @RequestParam(required = true) Integer status) {
        Map<String, Object> params = new HashMap<String, Object>();
        String[] idArray = eaiDataLogId.split(",");
        //String[] strArray = rowStatus.split(",");
        
        ArrayList<Object> list = new ArrayList<Object>();
        for(int i=0;i<idArray.length;i++){
        	if(status == -1){
        		list.add(idArray[i]);
        	}
        }
        final int size = list.size();
        Map<String, Object> error = new HashMap<String, Object>();
        
        try {
            if (size != 0) {
                String[] arr = (String[]) list.toArray(new String[size]);

                params.put("financeId", arr);
                params.put("status", status);

                // 更新该数据接口状态
                transModel.updateStockTransaction(params);
            }
            error.put("error", "更新成功" + size + "条!更新失败" + (idArray.length - size) + "条");

        } catch (Exception e) {
            error.put("error", "更新接口状态失败！");
        }
        modelMap.put("result", error);

        return "stock/eisInterfaceDataText";
    }
    


}
