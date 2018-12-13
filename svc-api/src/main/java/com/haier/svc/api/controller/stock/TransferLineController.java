package com.haier.svc.api.controller.stock;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haier.common.util.JsonUtil;
import com.haier.svc.api.controller.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;
import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.StringUtil;
import com.haier.purchase.data.model.vehcile.AreaCenterInfoDTO;
import com.haier.shop.model.ItemBase;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.OrderProductsNew;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.model.InvTransferLog;
import com.haier.stock.service.LESService;
import com.haier.stock.service.LesStockSyncService;
import com.haier.stock.service.OrderService;
import com.haier.stock.service.TransferLineService;
import com.haier.svc.api.controller.stock.mode.StockModel;
import com.haier.svc.service.ItemService;
import com.haier.vehicle.service.AreaCenterInfoService;

import jxl.Cell;
import jxl.CellFeatures;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.PaperSize;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 货物调拨管理
 *                       
 * @Filename: TransferController.java
 * @Version: 1.0
 * @Author: maqiang 马强
 * @Email: mqianger@163.com
 *
 */
@Controller
@RequestMapping("/stock")
public class TransferLineController {
    private static Logger logger = LogManager.getLogger(TransferLineController.class);

//    @Resource
    @Autowired
    private TransferLineService transferLineService;
    @Resource
    private StockModel          stockModel;
    @Resource(name="OrderService")
    private OrderService        orderService;

    @Resource
    private ItemService         itemService;
//    @Resource
//    private EISStockService     eisStockService;
    @Resource
    private LesStockSyncService lesStockSyncService;
    @Resource
    private LESService          lesService;
    @Resource
    private AreaCenterInfoService areaCenterInfoService;
    // 导入模板表头信息
    private static final String    CHECKSTR    = "LBX,入库数量";

    private static final String BLANK_STR = "";

    /**
     * 进入货物调拨管理页面
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "/transferManageIndex" }, method = { RequestMethod.GET })
    public String transferManageIndex(HttpServletRequest request, HttpServletResponse response,
                                      Map<String, Object> modelMap) {
//    	transferLineService.updateStatusFromLES();
        modelMap.put("endTime", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

        modelMap.put("startTime", DateUtil
            .format(DateUtil.add(new Date(), Calendar.DAY_OF_YEAR, -14), "yyyy-MM-dd HH:mm:ss"));
        
        return "stock/transferManageIndexPage";
    }

    /**
     * 导入页面跳转
     *
     * @param request
     * @param response
     * @param modelMap
     * @return
     */

    @RequestMapping(value = { "/gotoYpCheckDataImport" }, method = { RequestMethod.GET })
    public String importCrmReturnInfo(HttpServletRequest request, HttpServletResponse response,
                                      Map<String, Object> modelMap) {
        String url = request.getHeader("referer");
        modelMap.put("url", url);
        return "stock/ypCheckImport";
    }

    @RequestMapping(value = { "/importYpCheckData" })
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

        try{
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

            // 读取数据
            for (int i = 1; i < list.size(); i++) {

                String[] str = list.get(i);
                // LBX
                String lbx = StringUtil.nullSafeString(str[0]).trim();
                // 入库数量
                String qty = StringUtil.nullSafeString(str[1]).trim();
                boolean isAllNull = StringUtil.isEmpty(lbx, true)
                        && StringUtil.isEmpty(qty, true);

                if (isAllNull) {
                    nullRow++;
                    continue;
                }

                // lbx
                if (StringUtil.isEmpty(lbx, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【lbx】不能为空! 请核查后重新导入！";

                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                ServiceResult<InvTransferLine> tranresult=transferLineService.getYpInvTransferLineBySoLineNum(lbx);
                if(tranresult.getResult()==null){
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【LBX】无法查询到该优品LBX码! 请核查后重新导入！";

                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if(!qty.equals(String.valueOf(tranresult.getResult().getTransferQty()))){
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的入库数量与LBX入库数量不匹配! 请核查后重新导入！";

                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                if(tranresult.getResult().getLineStatus()!=50){
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的状态不是待入库! 请核查后重新导入！";

                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }
                // 入库数量
                if (StringUtil.isEmpty(qty, true)) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【入库数量】不能为空! 请核查后重新导入！";

                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                // 数量正确性判断（int型，大于0的整数）
                if (StringUtil.isEmpty(qty, true) || !CommUtil.canToInt(qty)
                        || Integer.parseInt(qty) <= 0) {
                    MsgList = "很抱歉！你导入的Excel数据,第" + i + "行数据的【入库数量】应该是大于0的整数! 请核查后重新导入！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    continue;
                }

                ServiceResult<Boolean> insResult = transferLineService
                        .insertInvSals(lbx);
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
     * 取得货物调拨信息列表
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = { "/findTransferData" }, method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> findTransferData(HttpServletRequest request, HttpServletResponse response,
                                   Map<String, Object> modelMap, Integer page, Integer rows, InvTransferLine filter, String startTime, String endTime, String lineStatusStr) throws Exception {
    	Map<String, Object> map = new HashMap<>();
    	
//        if (!StringUtil.isEmpty(tempLineNum, true) && tempLineNum.split(",").length > 30) {
////            response.getWriter()
////                .write("<script type=\"text/javascript\">alert(\"调拨单号不能超过30个\");</script>");
////            return "/stock/transferLineList";
//        }
//        if (!StringUtil.isEmpty(tempLbx, true) && tempLbx.split(",").length > 30) {
////            response.getWriter()
////                .write("<script type=\"text/javascript\">alert(\"LBX号不能超过30个\");</script>");
////            return "/stock/transferLineList";
//        }
        Map<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(filter.getLineNum())){
        	params.put("lineNum", filter.getLineNum().split(","));
        }
        if(StringUtils.isNotBlank(filter.getSoLineNum())){
        	params.put("lbx", filter.getSoLineNum().split(","));
        }
        if(StringUtils.isNotBlank(startTime)){
        	params.put("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
        }
        if(StringUtils.isNotBlank(endTime)){
        	params.put("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
        }
        if(StringUtils.isNotBlank(filter.getChannelFrom())){
        	params.put("channelFrom", filter.getChannelFrom());
        }
        if(StringUtils.isNotBlank(filter.getChannelTo())){
        	params.put("channelTo", filter.getChannelTo());
        }
        if(StringUtils.isNotBlank(filter.getItemCode())){
        	params.put("itemCode", filter.getItemCode());
        }
        if(StringUtils.isNotBlank(filter.getSecFrom())){
        	params.put("secFrom", filter.getSecFrom());
        }
        if(StringUtils.isNotBlank(filter.getSecTo())){
        	params.put("secTo", filter.getSecTo());
        }
        if(StringUtils.isNotBlank(lineStatusStr)){
        	List<Integer> lineStatusList = new ArrayList<>();
        	for(String status : lineStatusStr.split(",")){
        		if("ALL".equalsIgnoreCase(status)){
        			lineStatusList = null;
        			break;
        		}
        		lineStatusList.add(Integer.valueOf(status));
        	}
        	params.put("lineStatus", lineStatusList);
        }
        if(filter.getTransferReason() != null){
        	params.put("transferReason", filter.getTransferReason());
        }

//        int pageIndex = ConvertUtil.toInt(request.getParameter("pageIndex"), 1);
//        int pageSize = ConvertUtil.toInt(request.getParameter("pageSize"), 10);
        PagerInfo pager = new PagerInfo(rows, page);

        ServiceResult<List<InvTransferLine>> result = transferLineService.getTransferLines(params,
            pager);
        pager = result.getPager();

        if (result.getSuccess() && result.getResult() != null) {
        	map.put("total", result.getPager().getRowsCount());
        	map.put("rows", result.getResult());
        }
        return map;
    }

    /**
     * 取得货物调拨历史操作列表
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "/findTransferOperationHistory" }, method = { RequestMethod.POST })
    public void findTransferOperationHistory(@RequestParam(required = true) String lineId, HttpServletRequest request,
                                               HttpServletResponse response,
                                               Map<String, Object> modelMap) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        if (StringUtil.isEmpty(lineId)) {
            logger.error("参数lineId不能为空");
            throw new RuntimeException("参数lineId不能为空");
        }
        ServiceResult<List<InvTransferLog>> ret = transferLineService
            .getTransferOperationHistory(Integer.parseInt(lineId));
        try {

            response.getWriter().write(JsonUtil.toJson(ret.getResult()));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 调拨管理-提交
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "/submitTransfer" }, method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<String> submitTransfer(HttpServletRequest request,
                                                               HttpServletResponse response,
                                                               Map<String, Object> modelMap, String lineIds, Boolean isFirst) {
        HttpJsonResult<String> ret = new HttpJsonResult<String>();
        if (StringUtil.isEmpty(lineIds, true)) {
            ret.setMessage("请选择要提交的调拨网单");
            return ret;
        }
        ServiceResult<Boolean> sret = transferLineService.submitTransfer(lineIds,
            WebUtil.currentUserName(request), isFirst);

        if (sret.getMessage().contains("@")) {
            String[] temp = sret.getMessage().split("@");
            ret.setData(temp[1]);
            ret.setMessage(temp[0]);
        } else {
            ret.setMessage(sret.getMessage());
        }
        return ret;
    }

    /**
     * 调拨管理-删除
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "/removeTransfer" }, method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> removeTransfer(HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                Map<String, Object> modelMap, String lineIds) {
        HttpJsonResult<Boolean> ret = new HttpJsonResult<Boolean>();
        if (StringUtil.isEmpty(lineIds, true)) {
            ret.setMessage("请选择要删除的调拨网单");
            return ret;
        }
        ServiceResult<Boolean> sret = transferLineService.removeTransfer(lineIds,
            WebUtil.currentUserName(request));
        ret.setMessage(sret.getMessage());
        return ret;
    }

    /**
     * 调拨管理-取消
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "/cancelTransfer" }, method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> cancelTransfer(HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                Map<String, Object> modelMap) {
        HttpJsonResult<Boolean> ret = new HttpJsonResult<Boolean>();
        String lineIds = request.getParameter("lineIds");
        if (StringUtil.isEmpty(lineIds, true)) {
            ret.setMessage("请选择要取消的调拨网单");
            return ret;
        }
        String[] lineIdStrArr = lineIds.split(",");
        ServiceResult<InvTransferLine> result = null;
        String lineNum = null;
        String lesNum = null;
        StringBuilder sb = new StringBuilder();
        ServiceResult<Boolean> lesResult = null;

        StringBuilder lessErrorMsg = new StringBuilder();
        for (String lineId : lineIdStrArr) {
            result = transferLineService.getInvTransferLineByLineID(Integer.parseInt(lineId));
            if (null != result && result.getSuccess() && null != result.getResult()) {
                if (result.getResult().getTransferReason()
                    .intValue() == InvTransferLine.TRANSFER_REASON_3W.intValue()) {
                    lineNum = result.getResult().getLineNum();
                    lessErrorMsg.append(lineNum).append(":").append("3W单子请用3W取消功能").append("|");
                } else
                    if (result.getResult().getTransferReason()
                        .intValue() == InvTransferLine.TRANSFER_REASON_PP.intValue()
                        || result.getResult().getTransferReason()
                            .intValue() == InvTransferLine.TRANSFER_REASON_QH.intValue()) {
                    //平铺和缺货取消LES
                    lineNum = result.getResult().getLineNum();
                    lesNum = result.getResult().getLesNum();
                    if (result.getResult().getLineStatus().equals(
                        InvTransferLine.LINE_STATUS_STORE_OUT) && !StringUtils.isBlank(lesNum)) {
                        //调用EIS服务，取消LES调拨单
                        lesResult = lesStockSyncService.cancelTransferLine2Les(lineNum, lesNum);
                        //LES取消成功，加入取消id
                        if (lesResult.getSuccess() && lesResult.getResult()) {
                            sb.append(lineId);
                            sb.append(",");
                        } else {
                            lessErrorMsg.append(lineNum).append(":").append(lesResult.getMessage())
                                .append("|");
                        }
                    } else {
                        sb.append(lineId);
                        sb.append(",");
                    }
                }
            }
        }

        lineIds = sb.toString();
        if (lineIds.endsWith(",")) {
            lineIds = lineIds.substring(0, lineIds.length() - 1);
        }
        if (StringUtils.isBlank(lineIds)) {
            ret.setMessage("Les取消调拨失败," + lessErrorMsg.toString());
            return ret;
        }
        //释放库存和更新取消状态
        ServiceResult<Boolean> sret = transferLineService.cancelTransfer(lineIds,
            WebUtil.currentUserName(request));
        ret.setMessage(sret.getMessage());
        if (lessErrorMsg.length() > 0) {
            ret.setMessage(ret.getMessage() + "/n部分调拨单LES取消失败，" + lessErrorMsg.toString());
        }
        return ret;
    }

    /**
     * 调拨管理-3W取消
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "/cancel3WTransfer" }, method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<String> cancel3WTransfer(HttpServletRequest request,
                                                                 HttpServletResponse response,
                                                                 Map<String, Object> modelMap) {
        HttpJsonResult<String> ret = new HttpJsonResult<String>();
        String lineIds = request.getParameter("lineIds");
        if (StringUtil.isEmpty(lineIds, true) || ",".equals(lineIds)) {
            ret.setMessage("请选择要取消的3W调拨单！");
            return ret;
        }
        ServiceResult<InvTransferLine> result = null;
        ServiceResult<Boolean> lesResult = null;
        String lineNum = null;
        StringBuilder lessErrorMsg = new StringBuilder();
        //成功拼接,id
        StringBuilder sbSucId = new StringBuilder();
        //成功拼接,lineNum
        StringBuilder sbSucSn = new StringBuilder();
        //失败拼接,id
        StringBuilder sbErrId = new StringBuilder();
        //失败拼接,lineNum
        StringBuilder sbErrSn = new StringBuilder();

        String[] lineIdStrArr = lineIds.split(",");
        for (String lineId : lineIdStrArr) {
            result = transferLineService.getInvTransferLineByLineID(Integer.parseInt(lineId));
            if (null != result && result.getSuccess() && null != result.getResult()) {
                lineNum = result.getResult().getLineNum();
                if (result.getResult().getTransferReason()
                    .intValue() == InvTransferLine.TRANSFER_REASON_3W.intValue()||
                        result.getResult().getTransferReason().intValue() == InvTransferLine.TRANSFER_REASON_YP.intValue()) {
                    //3W取消LES
                    if (result.getResult().getLineStatus()
                        .equals(InvTransferLine.LINE_STATUS_STORE_OUT)) {
                        //取消LES调拨单
                        lesResult = transferLineService.cancelTransferLineToLesOnTime(lineNum);
                        if (lesResult.getSuccess() && lesResult.getResult()) {
                            sbSucSn.append(lineNum);
                            sbSucSn.append(",");
                            sbSucId.append(lineId);
                            sbSucId.append(",");
                        } else {
                            sbErrSn.append(lineNum);
                            sbErrSn.append(",");
                            sbErrId.append(lineId);
                            sbErrId.append(",");
                            lessErrorMsg.append(lineNum).append(":").append(lesResult.getMessage())
                                .append("\n");
                        }
                    } 
//                    else {
//                        sbSucId.append(lineId);
//                        sbSucId.append(",");
//                    }
                } else
                    if (result.getResult().getTransferReason()
                        .intValue() == InvTransferLine.TRANSFER_REASON_PP.intValue()
                        || result.getResult().getTransferReason()
                            .intValue() == InvTransferLine.TRANSFER_REASON_QH.intValue()) {
                    //平铺和缺货取消LES
                    lessErrorMsg.append(lineNum).append(":").append("非3W单子请用非3W取消功能").append("\n");
                }
            }
        }
        //成功拼接,id
        String sucLineIds = sbSucId.toString();
        if (sucLineIds.endsWith(",")) {
            sucLineIds = sucLineIds.substring(0, sucLineIds.length() - 1);
        }
        //失败拼接,lineNum
        String errSns = sbErrSn.toString();
        if (errSns.endsWith(",")) {
            errSns = errSns.substring(0, errSns.length() - 1);
        }
        //失败拼接,id
        String errIds = sbErrId.toString();
        if (errIds.endsWith(",")) {
            errIds = errIds.substring(0, errIds.length() - 1);
        }
        if (StringUtils.isBlank(sucLineIds) && StringUtils.isBlank(errSns)) {
            ret.setMessage("Les取消调拨失败\n" + lessErrorMsg.toString());
            return ret;
        }
        if (!StringUtils.isBlank(sucLineIds)) {
            //释放库存和更新取消状态
            ServiceResult<Boolean> sret = transferLineService.cancelTransfer(sucLineIds,
                WebUtil.currentUserName(request));
            if (sret != null) {
                ret.setMessage(
                    sret.getSuccess() ? ("成功信息：\n" + sbSucSn.toString() + sret.getMessage() + "\n")
                        : (sret.getMessage() + "\n"));
            }
        }
        if (lessErrorMsg.length() > 0) {
            if (ret.getMessage() == null)
                ret.setMessage("");
            ret.setMessage(ret.getMessage() + "\n失败信息：\n" + lessErrorMsg.toString());
        }

        if (!StringUtils.isBlank(errSns)) {
            ret.setData(errSns + "@" + errIds);
        }

        return ret;
    }

    @RequestMapping(value = { "/addCancelQueue" }, method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> addCancelQueue(HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                Map<String, Object> modelMap) {
        HttpJsonResult<Boolean> ret = new HttpJsonResult<Boolean>();
        String lineIds = request.getParameter("lineIds");
        if (StringUtil.isEmpty(lineIds, true)) {
            ret.setMessage("调拨单号不能为空!");
            return ret;
        }
        ServiceResult<Boolean> sret = transferLineService.addCancelQueue(lineIds,
            WebUtil.currentUserName(request));
        if (sret != null && sret.getSuccess() && sret.getResult()) {
            ret.setMessage("添加3W自动推送物流取消队列成功");
        } else {
            ret.setMessage("添加3W自动推送物流取消队列失败,请联系管理员");
        }

        return ret;
    }

    /**
     * 进入平铺调货/缺货调货导入页面
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "/excelUpload" }, method = { RequestMethod.GET })
    public String ppTransfer(HttpServletRequest request, HttpServletResponse response,
                             Map<String, Object> modelMap) {
        modelMap.put("reason", request.getParameter("reason"));
        return "stock/transferExcelUpload";
    }

    /**
     * 下载平铺调货/缺货调货模板文件
     * @param request
     * @param response
     * @throws IOException
     */
//    @RequestMapping(value = { "/downloadTransferTemplate" }, method = { RequestMethod.GET })
//    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response,
//                                 @RequestHeader(value = "user-agent") String userAgent) throws IOException {
//        OutputStream os = response.getOutputStream();
//        try {
//            String reason = request.getParameter("reason");
//            String fileName = null;
//            String innerFileName = null;
//            if (InvTransferLine.TRANSFER_REASON_PP.toString().equals(reason)) {
//                fileName = "平铺调货模板.xls";
//                innerFileName = "pp_inv_transfer_template.xls";
//            } else if (InvTransferLine.TRANSFER_REASON_XN.toString().equals(reason)) {
//                fileName = "虚拟调拨模板.xls";
//                innerFileName = "xn_inv_transfer_template.xls";
//            } else if (InvTransferLine.TRANSFER_REASON_QH.toString().equals(reason)) {
//                fileName = "缺货调货模板.xls";
//                innerFileName = "qh_inv_transfer_template.xls";
//            } else if (InvTransferLine.TRANSFER_REASON_3W.toString().equals(reason)) {
//                fileName = "3W调拨模板.xls";
//                innerFileName = "3w_inv_transfer_template.xls";
//            } else {
//                //类型不对
//                return;
//            }
//            response.reset();
//            response.setHeader("Content-Disposition",
//                "attachment; filename=" + this.formatFileName(fileName, userAgent));
//            response.setContentType("application/octet-stream; charset=utf-8");
//            String path = request.getSession().getServletContext()
//                .getRealPath("/resources/stock/" + innerFileName);
//            os.write(FileUtils.readFileToByteArray(new File(path)));
//            os.flush();
//        } finally {
//            if (os != null) {
//                os.close();
//            }
//        }
//    }

    /**
     * 导入平铺调货/缺货调货记录
     * @param request
     * @param response
     * @param file
     * @return
     */
    @RequestMapping(value = { "/uploadTransferRecored" }, method = { RequestMethod.POST })
    public void uploadTransferRecored(HttpServletRequest request, HttpServletResponse response, String reason, MultipartHttpServletRequest req) {
        Map<String, Object> params = new HashMap<String, Object>();
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        Workbook workbook = null;
        InputStream is = null;
        MultipartFile file = req.getFile("file");
        try {
            is = file.getInputStream();
            workbook = Workbook.getWorkbook(is);
            List<String> parseErrs = new ArrayList<String>();
            if (StringUtil.isEmpty(reason, true)) {
                throw new BusinessException("导入类型不正确！");
            }
            int iReason = Integer.parseInt(reason.trim());
            if (InvTransferLine.TRANSFER_REASON_PP.intValue() != iReason
                && InvTransferLine.TRANSFER_REASON_QH.intValue() != iReason
                && InvTransferLine.TRANSFER_REASON_XN.intValue() != iReason
                && InvTransferLine.TRANSFER_REASON_3W.intValue() != iReason
                && InvTransferLine.TRANSFER_REASON_YP.intValue() != iReason) {
                throw new BusinessException("导入类型不正确！");
            }
            if (workbook.getSheet(0).getRows() > 501) {
                throw new BusinessException("一次最多导入500条！");
            }
            String user = request.getSession().getAttribute("userName").toString();
            List<InvTransferLine> lines = this.parseWorkbook(workbook, parseErrs, reason, user);
            if (parseErrs.size() > 0) {
                result.setMessage(this.getParseErrMsg(parseErrs));
            } else if (lines == null || lines.size() == 0) {
                result.setMessage("无记录,请核对excle文件");
            } else {
                ServiceResult<Integer> rs;
                if (InvTransferLine.TRANSFER_REASON_XN.toString().equals(reason)) {
                    rs = transferLineService.saveInnerTransfers(lines);
                } else {
                    rs = transferLineService.uploadTransferRecored(lines);
                }
                if (!rs.getSuccess()) {
                    result.setMessage(rs.getMessage());
                    logger.error("导入调货记录时返回错误：" + rs.getMessage());
                } else {
                    params.put("total", rs.getResult());
                    if (rs.getMessage() != null && !rs.getMessage().equals("")) {
                        params.put("msg", rs.getMessage());
                    } else {
                        params.put("msg", "");
                    }
                }
            }
        } catch (BiffException e) {
            logger.error("无法解析Excel文件！", e);
            result.setMessage("无法解析Excel文件！");
        } catch (BusinessException e) {
            logger.error(e.getMessage());
            result.setMessage(e.getMessage());
        } catch (Throwable e) {
            logger.error("导入调货记录文件" + file.getOriginalFilename() + "失败：", e);
            result.setMessage("处理失败，请稍后重试，如果仍无法处理请联系管理员");
        } finally {
            if (workbook != null)
                workbook.close();
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                }
        }
        result.setData(params);

        response.setContentType("text/plain;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        String json = new Gson().toJson(result);
        try {
            response.getOutputStream().write(json.getBytes("utf-8"));
        } catch (Exception e) {
            logger.error("返回调货记录导入结果时发生异常：", e);
        }
    }

    @RequestMapping(value = { "/exportTransferLines" }, method = { RequestMethod.GET })
    void export(HttpServletResponse response, HttpServletRequest request, InvTransferLine filter, String startTime, String endTime, String lineStatusStr) {
    	Map<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(filter.getLineNum())){
        	params.put("lineNum", filter.getLineNum().split(","));
        }
        if(StringUtils.isNotBlank(filter.getSoLineNum())){
        	params.put("lbx", filter.getSoLineNum().split(","));
        }
        if(StringUtils.isNotBlank(startTime)){
        	try {
				params.put("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
        if(StringUtils.isNotBlank(endTime)){
        	try {
				params.put("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime));
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
        if(StringUtils.isNotBlank(filter.getChannelFrom())){
        	params.put("channelFrom", filter.getChannelFrom());
        }
        if(StringUtils.isNotBlank(filter.getChannelTo())){
        	params.put("channelTo", filter.getChannelTo());
        }
        if(StringUtils.isNotBlank(filter.getItemCode())){
        	params.put("itemCode", filter.getItemCode());
        }
        if(StringUtils.isNotBlank(filter.getSecFrom())){
        	params.put("secFrom", filter.getSecFrom());
        }
        if(StringUtils.isNotBlank(filter.getSecTo())){
        	params.put("secTo", filter.getSecTo());
        }
        if(StringUtils.isNotBlank(lineStatusStr)){
        	List<Integer> lineStatusList = new ArrayList<>();
        	for(String status : lineStatusStr.split(",")){
        		if("ALL".equalsIgnoreCase(status)){
        			lineStatusList = null;
        			break;
        		}
        		lineStatusList.add(Integer.valueOf(status));
        	}
        	params.put("lineStatus", lineStatusList);
        }
        if(filter.getTransferReason() != null){
        	params.put("transferReason", filter.getTransferReason());
        }

        int pageIndex = 1;
        int pageSize = 5000;
        PagerInfo pager = new PagerInfo(pageSize, pageIndex);

        ServletOutputStream os = null;

        try {
            try {
                ServiceResult<List<InvTransferLine>> result = transferLineService
                    .getTransferLines(params, pager);
                if (!result.getSuccess())
                    throw new BusinessException("导出失败，获取待导出调拨数据出错：" + result.getMessage());
                List<InvTransferLine> transferLines = result.getResult();

                os = response.getOutputStream();
                response.reset();

                String fileName = "调拨管理(" + DateUtil.format(new Date(), "yyyy-MM-dd") + ")";
                response.setHeader("Content-Disposition",
                    "attachment; filename=" + new String(fileName.getBytes(), "iso-8859-1") + ".xls");
                response.setContentType("application/octet-stream; charset=utf-8");
                exportTransferLines(os, params, transferLines);
            } catch (Exception e) {
                response.reset();
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().print("导出excel失败，" + e);
                response.getWriter().flush();
                logger.error("导出excel失败:", e);
            } finally {
                if (os != null)
                    os.close();
            }
        } catch (IOException e) {
            logger.error("导出excel失败:", e);
        }
    }

    /**
     * @param os
     * @param params
     * @param transferLines
     * @throws Exception
     */
    /**
     * @param os
     * @param params
     * @param transferLines
     * @throws Exception
     */
    private void exportTransferLines(OutputStream os, Map<String, Object> params,
                                     List<InvTransferLine> transferLines) throws Exception {

        VelocityUtil util = new VelocityUtil();

        WritableWorkbook workbook = Workbook.createWorkbook(os);
        WritableSheet sheet = workbook.createSheet("调拨管理", 0);
        sheet.getSettings().setPaperSize(PaperSize.A2);//设置纸张大小
        sheet.getSettings().setFitHeight(297);
        sheet.getSettings().setFitWidth(21);
        sheet.getSettings().setHorizontalCentre(true);

        //标题
        WritableFont font = new WritableFont(WritableFont.ARIAL, 13, WritableFont.BOLD, false,
            UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);
        WritableCellFormat format = new WritableCellFormat(font);
        format.setAlignment(Alignment.LEFT);
        Label l_title = new Label(0, 0, "调拨管理", format);
        sheet.setColumnView(0, 25);
        sheet.addCell(l_title);

        WritableFont font2 = new WritableFont(WritableFont.ARIAL, 8, WritableFont.NO_BOLD, true,
            UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat _format = new WritableCellFormat(font2);
        _format.setAlignment(Alignment.LEFT);
        Label l_title2 = new Label(0, 2,
            "报表日期" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), _format);
        sheet.setColumnView(0, 25);
        sheet.addCell(l_title2);

        //参数
        WritableFont fontParam = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
            UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);
        WritableCellFormat formatParam = new WritableCellFormat(fontParam);

        StringBuilder sb = new StringBuilder();

        String[] lineNum = (String[]) params.get("lineNum");
        if (lineNum != null && lineNum.length > 0)
            sb.append("单据号：" + Arrays.toString(lineNum));
        String[] lbx = (String[]) params.get("lbx");
        if (lbx != null && lbx.length > 0)
            sb.append("LBX：" + Arrays.toString(lbx));

        sb.append(" 时间范围：" + (params.get("startTime") == null ? "未指定" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(params.get("startTime"))) + "--" + (params.get("endTime") == null ? "未指定" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(params.get("endTime"))));

        Object channelFrom = params.get("channelFrom");
        if (channelFrom == null || "ALL".equals(channelFrom))
            sb.append(" 调出渠道：" + "全部");
        else
            sb.append(" 调出渠道：" + util.getChannelName(channelFrom.toString()));

        Object channelTo = params.get("channelTo");
        if (channelTo == null || "ALL".equals(channelTo))
            sb.append(" 调入渠道：" + "全部");
        else
            sb.append(" 调入渠道：" + util.getChannelName(channelTo.toString()));

        //② 调货中发起部门直接删掉吧，原因筛选加上"虚拟" -- 2014-05-04
        /* Object dep = params.get("reqDep");
         if (dep != null && !StringUtil.isEmpty(dep.toString()))
             sb.append(" 发起部门：" + util.getDepName(dep.toString()));*/

        Object transferReason = params.get("transferReason");
        if (transferReason == null || StringUtil.isEmpty(transferReason.toString()))
            sb.append(" 原因：" + "全部");
        else
            sb.append(" 原因：" + util.getTransferReason((Integer) transferReason));

        Object sku = params.get("itemCode");
        if (sku != null && !StringUtil.isEmpty(sku.toString()))
            sb.append(" 物料编码：" + sku);

        Object secFrom = params.get("secFrom");
        if (secFrom != null && !StringUtil.isEmpty(secFrom.toString()))
            sb.append(" 调出库位：" + secFrom);

        Object secTo = params.get("secTo");
        if (secTo != null && !StringUtil.isEmpty(secTo.toString()))
            sb.append(" 调入库位：" + secTo);

        Object lineStatus = params.get("lineStatus");
        if (lineStatus == null)
            sb.append(" 状态：全部");
        else {
            sb.append(" 状态：");
            List<Integer> status = (List<Integer>) lineStatus;
            for (Integer s : status) {
                sb.append(util.getTransferLineStatus(s) + ",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }

        Label l_params = new Label(0, 4, sb.toString(), formatParam);
        sheet.setColumnView(0, 25);
        sheet.addCell(l_params);

        //表头
        int temp = 6;
        String[] headers = new String[] { "单据号", "LBX", "状态", "调出渠道", "调入渠道", "原因", "物料编码", "物料名称",
                                          "调出库位", "调入库位", "期望调拨数量", "实际调拨数量", "是否免费调货", "费用",
                                          "运输周期", "创建时间", "创建人", "开提单时间", "出库时间", "入库时间", "备注" };
        WritableFont fontHeader = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false,
            UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);
        WritableCellFormat formatHeader = new WritableCellFormat(fontHeader);
        formatHeader.setAlignment(Alignment.CENTRE);
        formatHeader.setVerticalAlignment(VerticalAlignment.CENTRE);
        formatHeader.setBorder(Border.ALL, BorderLineStyle.THIN);
        int count = 0;

        for (int col = 0; col < headers.length; col++) {
            Label _label = new Label(count, temp, headers[col], formatHeader);
            sheet.setColumnView(count, 16);
            sheet.addCell(_label);
            count++;
        }

        //数据
        temp = 7;
        WritableFont font1 = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
            UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        DateFormat dateFormat = new DateFormat("yyyy-MM-dd HH:mm:ss");

        WritableCellFormat format1 = new WritableCellFormat(font1, dateFormat);
        format1.setAlignment(Alignment.CENTRE);
        format1.setBorder(Border.ALL, BorderLineStyle.THIN);

        WritableCellFormat format2 = new WritableCellFormat(font1);
        format2.setAlignment(Alignment.CENTRE);
        format2.setBorder(Border.ALL, BorderLineStyle.THIN);

        WritableCellFormat formatInt = new WritableCellFormat(NumberFormats.INTEGER);
        formatInt.setAlignment(Alignment.RIGHT);
        formatInt.setBorder(Border.ALL, BorderLineStyle.THIN);

        WritableCellFormat formatFloat = new WritableCellFormat(NumberFormats.FLOAT);
        formatFloat.setAlignment(Alignment.RIGHT);
        formatFloat.setBorder(Border.ALL, BorderLineStyle.THIN);

        for (InvTransferLine transferLine : transferLines) {
            int j = 0;

            sheet.setColumnView(j, 19);
            sheet.addCell(new Label(j++, temp, transferLine.getLineNum(), format2));

            sheet.setColumnView(j, 19);
            sheet.addCell(new Label(j++, temp, transferLine.getSoLineNum(), format2));

            sheet.setColumnView(j, 12);
            sheet.addCell(new Label(j++, temp,
                util.getTransferLineStatus(transferLine.getLineStatus()), format2));

            sheet.setColumnView(j, 10);
            sheet.addCell(
                new Label(j++, temp, util.getChannelName(transferLine.getChannelFrom()), format2));

            sheet.setColumnView(j, 10);
            sheet.addCell(
                new Label(j++, temp, util.getChannelName(transferLine.getChannelTo()), format2));

            //            sheet.setColumnView(j, 10);
            //            sheet.addCell(new Label(j++, temp, util.getDepName(transferLine.getReqDep()), format2));

            sheet.setColumnView(j, 8);
            sheet.addCell(new Label(j++, temp,
                util.getTransferReason(transferLine.getTransferReason()), format2));

            sheet.setColumnView(j, 10);
            sheet.addCell(new Label(j++, temp, transferLine.getItemCode(), format2));

            sheet.setColumnView(j, 18);
            sheet.addCell(new Label(j++, temp, transferLine.getItemName(), format2));

            sheet.setColumnView(j, 8);
            sheet.addCell(new Label(j++, temp, transferLine.getSecFrom(), format2));

            sheet.setColumnView(j, 8);
            sheet.addCell(new Label(j++, temp, transferLine.getSecTo(), format2));

            sheet.setColumnView(j, 10);
            sheet.addCell(new jxl.write.Number(j++, temp, transferLine.getQty(), formatInt));

            sheet.setColumnView(j, 10);
            sheet
                .addCell(new jxl.write.Number(j++, temp, transferLine.getTransferQty(), formatInt));

            sheet.setColumnView(j, 18);
            sheet.addCell(new Label(j++, temp, transferLine.getFreeTransfer(), format2));

            sheet.setColumnView(j, 10);
            sheet.addCell(new jxl.write.Number(j++, temp,
                transferLine.getTransferFee().doubleValue(), formatFloat));

            sheet.setColumnView(j, 10);
            sheet.addCell(new jxl.write.Number(j++, temp, transferLine.getHaulCycle(), formatInt));

            sheet.setColumnView(j, 18);
            sheet.addCell(new DateTime(j++, temp, transferLine.getCreateTime(), format1));

            sheet.setColumnView(j, 10);
            sheet.addCell(new Label(j++, temp, transferLine.getCreateUser(), format2));

            ServiceResult<List<InvTransferLog>> ret = transferLineService
                .getTransferOperationHistory(transferLine.getLineId());
            if (!ret.getSuccess())
                throw new BusinessException("通过库存服务获取调拨单处理操作失败：" + ret.getMessage());
            List<InvTransferLog> oplogs = ret.getResult();

            Date syncToLesTime = null;
            Date billOut = null;
            Date billIn = null;
            for (InvTransferLog log : oplogs) {
                switch (log.getLogType()) {
                    case 8:
                        syncToLesTime = log.getOptTime();
                        break;
                    case 9:
                        billOut = log.getOptTime();
                        break;
                    case 10:
                        billIn = log.getOptTime();
                        break;
                    default:
                        break;
                }
            }

            sheet.setColumnView(j, 18);
            if (syncToLesTime != null)
                sheet.addCell(new DateTime(j++, temp, syncToLesTime, format1));
            else
                sheet.addCell(new Label(j++, temp, "", format1));
            sheet.setColumnView(j, 18);
            if (billOut != null)
                sheet.addCell(new DateTime(j++, temp, billOut, format1));
            else
                sheet.addCell(new Label(j++, temp, "", format1));
            sheet.setColumnView(j, 18);
            if (billIn != null)
                sheet.addCell(new DateTime(j++, temp, billIn, format1));
            else
                sheet.addCell(new Label(j++, temp, "", format1));

            //新添加备注 wyj 2014-09-29
            sheet.setColumnView(j, 18);
            sheet.addCell(new Label(j++, temp, transferLine.getRemark(), format2));

            temp++;
        }

        workbook.write();
        workbook.close();

    }

    private String getParseErrMsg(List<String> parseErrs) {
        StringBuffer sb = new StringBuffer("<ul>");
        for (String err : parseErrs) {
            sb.append("<li>").append(err).append("</li>");
        }
        sb.append("</ul>");
        return sb.toString();
    }

    private List<InvTransferLine> parseWorkbook(Workbook workbook, List<String> parseErrs,
                                                String reason, String user) {
        List<InvTransferLine> lines = new ArrayList<InvTransferLine>();
        Sheet sheet = workbook.getSheet(0);//这里只取得第一个sheet的值，默认从0开始
        // 开始循环，取得 cell 里的内容，这里都是按String来取的 为了省事，具体你自己可以按实际类型来取。或者都按
        // String来取。然后根据你需要强制转换一下。
        List<String> channelCodes = this.getChannelCodes();
        List<String> soLineNums = new ArrayList<String>();
        for (int i = 1; i < sheet.getRows(); i++) {
            //跳过空行
            if (sheet.getRow(i).length == 0)
                continue;
            else {
                boolean allNvl = true;
                for (Cell cell : sheet.getRow(i)) {
                    if (!StringUtil.isEmpty(cell.getContents(), true))
                        allNvl = false;
                }
                if (allNvl)
                    continue;
            }
            InvTransferLine line = this.getUploadDefaultTransferLine(reason, user);
            this.parseWorkbook(line, parseErrs, sheet.getRow(i), i + 1, channelCodes, soLineNums);
            lines.add(line);
        }
        return lines;

    }

    private InvTransferLine getUploadDefaultTransferLine(String reason, String user) {
        InvTransferLine line = new InvTransferLine();

        //??
        //        if (InvTransferLine.TRANSFER_REASON_PP.toString().equals(reason)) {//平铺调货
        //            line.setTransferReason(InvTransferLine.TRANSFER_REASON_PP);
        //        } else if (InvTransferLine.TRANSFER_REASON_XN.toString().equals(reason)) {
        //            line.setTransferReason(InvTransferLine.TRANSFER_REASON_XN);
        //        } else {
        //            line.setTransferReason(InvTransferLine.TRANSFER_REASON_QH);
        //        }
        line.setTransferReason(Integer.parseInt(reason));
        line.setSoLineNum(BLANK_STR);//如果是缺货调货会在解析excel时覆盖该值
        line.setLineStatus(InvTransferLine.LINE_STATUS_INIT);
        line.setTransferFee(new BigDecimal(0));
        line.setTransferFeeUser(BLANK_STR);
        line.setHaulCycle(0);
        Date initDate = null;
        try {
            initDate = new SimpleDateFormat("yyyy-MM-dd").parse("1900-01-01");
        } catch (ParseException e) {
        }
        line.setTransferFeeTime(initDate);
        line.setApproveStatus(InvTransferLine.APPROVE_STATUS_INIT);
        line.setApproveTime(initDate);
        line.setApproveUser(BLANK_STR);
        line.setApproveRemark(BLANK_STR);
        line.setLesNum(BLANK_STR);
        line.setCreateTime(new Date());
        line.setCreateUser(user);
        line.setRemark(BLANK_STR);
        return line;
    }

    private List<String> getChannelCodes() {
        List<InvStockChannel> channels = stockModel.getChannels();
        List<String> channelCodes = new ArrayList<String>();
        for (InvStockChannel channel : channels) {
            channelCodes.add(channel.getChannelCode());
        }
        //1.渠道编码可输入共享渠道WA
        channelCodes.add(InvSection.CHANNEL_CODE_WA);
        return channelCodes;
    }

    private void parseWorkbook(InvTransferLine line, List<String> parseErrs, Cell[] cells,
                               int rowNum, List<String> channelCodes, List<String> soLineNums) {
        //是为了什么？ 为空cell的判断？
        //        int colNum = InvTransferLine.TRANSFER_REASON_PP.equals(line.getTransferReason()) ? 7
        //            : (InvTransferLine.TRANSFER_REASON_XN.equals(line.getTransferReason()) ? 7 : 8);
        //实物调货里模板增加一列是否免费调货，必填项目，识别是、否，不允许为空
        //        int colNum = InvTransferLine.TRANSFER_REASON_PP.equals(line.getTransferReason()) ? 8
        //            : (InvTransferLine.TRANSFER_REASON_XN.equals(line.getTransferReason()) ? 7 : 9);
        int colNum = 0;
        if (InvTransferLine.TRANSFER_REASON_PP.equals(line.getTransferReason())) {
            colNum = 8;
        } else if (InvTransferLine.TRANSFER_REASON_XN.equals(line.getTransferReason())) {
            colNum = 7;
        } else if (InvTransferLine.TRANSFER_REASON_QH.equals(line.getTransferReason())) {
            colNum = 9;
        } else if (InvTransferLine.TRANSFER_REASON_3W.equals(line.getTransferReason())) {
            colNum = 9;
        } else if (InvTransferLine.TRANSFER_REASON_YP.equals(line.getTransferReason())) {
            colNum = 10;
        }

        if (cells.length < colNum) {
            Cell[] newCells = new Cell[colNum];
            for (int i = 0; i < colNum; i++) {
                Cell cell = null;
                if (i < cells.length) {
                    cell = cells[i];
                } else {
                    cell = this.newNullCell();
                }
                newCells[i] = cell;
            }
            cells = newCells;
        }
        /* 3W调拨数据检查*/
        if (InvTransferLine.TRANSFER_REASON_3W.equals(line.getTransferReason())) {
            check3WTransferCells(line, parseErrs, cells, rowNum, channelCodes, soLineNums);
            return;
        }
         /* 优品调拨数据检查*/
        if (InvTransferLine.TRANSFER_REASON_YP.equals(line.getTransferReason())) {
            checkYPTransferCells(line, parseErrs, cells, rowNum, channelCodes, soLineNums);
            return;
        }
        /* 内部虚拟调拨数据检查*/
        if (InvTransferLine.TRANSFER_REASON_XN.equals(line.getTransferReason())) {
            checkInnerTransferCells(line, parseErrs, cells, rowNum, channelCodes);
            return;
        }
        /*平铺调拨和缺货调拨数据检查*/
        int excelColNum = 0;
        /* //检查渠道
         if (cells.length > excelColNum)
             this.checkChannelId(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                 excelColNum, channelCodes);*/
        //检查调出渠道
        String channelFrom = "";
        if (cells.length > excelColNum) {
            channelFrom = cells[excelColNum++].getContents();
            this.checkChannelId(line, parseErrs, channelFrom, rowNum, excelColNum, channelCodes);
            line.setChannelFrom(channelFrom.trim().toUpperCase());
        }
        //检查调入渠道:渠道编码统一处理，不截取
        String channelTo = "";
        if (cells.length > excelColNum) {
            channelTo = cells[excelColNum++].getContents();
            this.checkChannelId(line, parseErrs, channelTo, rowNum, excelColNum, channelCodes);
            line.setChannelTo(channelTo.trim().toUpperCase());
        }
        //② 调货中发起部门直接删掉吧，原因筛选加上"虚拟" --2014-05-04
        //检查发起部门
        /* if (cells.length > excelColNum)
             this.checkReqdep(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                 excelColNum);*/
        //检查网单号
        if (cells.length > excelColNum) {
            if (InvTransferLine.TRANSFER_REASON_QH.equals(line.getTransferReason())) {
                this.checkSoLineNum(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                    excelColNum, soLineNums);
            }
        }
        //检查物料编码（sku）
        if (cells.length > excelColNum)
            this.checkItemCode(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                excelColNum);
        //检查调出库位
        String secFrom = "";
        int indexFromColNum = 0;
        if (cells.length > excelColNum) {
            indexFromColNum = excelColNum++;
            secFrom = cells[indexFromColNum].getContents();
            //this.checkSecFrom(line, parseErrs, secFrom, rowNum, excelColNum, false);
        }
        //检查调入库位
        String secTo = "";
        int indexToColNum = 0;
        if (cells.length > excelColNum) {
            indexToColNum = excelColNum++;
            secTo = cells[indexToColNum].getContents();
            //this.checkSecTo(line, parseErrs, secTo, rowNum, excelColNum, false);
        }
        //检查渠道与虚拟库位之间的关系， 新检查方式
        String[] secCodes = checkChannelSecCode(secFrom, channelFrom, secTo, channelTo,
            line.getTransferReason(), rowNum, parseErrs);
        if (secCodes != null && secCodes.length == 2) {
            this.checkSecFrom(line, parseErrs, secCodes[0], rowNum, indexFromColNum, true);
            this.checkSecTo(line, parseErrs, secCodes[1], rowNum, indexToColNum, true);
        }
        //平铺、缺货
        if (!StringUtil.isEmpty(secFrom) && !StringUtil.isEmpty(secTo)) {
            checkSameWhHouse(parseErrs, secFrom, secTo);
        }
        //检查调拨数量
        if (cells.length > excelColNum)
            this.checkTransferQty(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                excelColNum);
        //检查是否免费调货
        if (cells.length > excelColNum)
            this.checkFreeTransfer(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                excelColNum);
        //备注
        if (cells.length > excelColNum)
            this.checkRemark(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                excelColNum);
    }

    /**
     * 检查虚拟调拨导入字段
     * @param line
     * @param parseErrs
     * @param cells
     * @param rowNum
     * @param channelCodes
     */
    private void checkInnerTransferCells(InvTransferLine line, List<String> parseErrs, Cell[] cells,
                                         int rowNum, List<String> channelCodes) {
        int excelColNum = 0;
        //检查物料编码（sku）
        if (cells.length > excelColNum)
            this.checkItemCode(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                excelColNum);
        //检查调出渠道是否存在
        String channelFrom = "";
        if (cells.length > excelColNum) {
            channelFrom = cells[excelColNum++].getContents();
            this.checkChannelId(line, parseErrs, channelFrom, rowNum, excelColNum, channelCodes);
            line.setChannelFrom(channelFrom.trim().toUpperCase());

        }
        //检查调入渠道是否存在
        String channelTo = "";
        if (cells.length > excelColNum) {
            channelTo = cells[excelColNum++].getContents();
            this.checkChannelId(line, parseErrs, channelTo, rowNum, excelColNum, channelCodes);
            line.setChannelTo(channelTo.trim().toUpperCase());
        }
        String secFrom = "";
        String secTo = "";
        int indexFromColNum = 0;
        //检查调出库位是否存在
        //1. 虚拟调拨， 调出库位可以是虚拟库位
        if (cells.length > excelColNum) {
            indexFromColNum = excelColNum++;
            secFrom = cells[indexFromColNum].getContents();

        }
        //检查调入库位是否存在
        //1.虚拟调拨，调入库位可以是虚拟库位
        int indexToColNum = 0;
        if (cells.length > excelColNum) {
            indexToColNum = excelColNum++;
            secTo = cells[indexToColNum].getContents();

        }
        //检查渠道与虚拟库位之间的关系, 新检查方式
        String[] secCodes = checkChannelSecCode(secFrom, channelFrom, secTo, channelTo,
            line.getTransferReason(), rowNum, parseErrs);
        if (secCodes != null && secCodes.length == 2) {
            this.checkSecFrom(line, parseErrs, secCodes[0], rowNum, indexFromColNum, true);
            this.checkSecTo(line, parseErrs, secCodes[1], rowNum, indexToColNum, true);
        }
        //1.同库位不能互相调拨；2.同一仓库下才能虚拟调拨
        if (!StringUtil.isEmpty(secTo) && !StringUtil.isEmpty(secFrom)) {
            boolean sameSec = secFrom.toUpperCase().equals(secTo.toUpperCase());
            if (sameSec)
                parseErrs.add("第" + rowNum + "行，同一库位不能虚拟调拨");
            InvSection tempSecTo = stockModel.getSectionByCode(secTo);
            InvSection tempSecFrom = stockModel.getSectionByCode(secFrom);
            if (tempSecTo != null && tempSecFrom != null) {
                boolean sameWh = tempSecTo.getWhCode().equals(tempSecFrom.getWhCode());
                if (!sameWh) {
                    parseErrs.add("第" + rowNum + "行，不同仓库下不能虚拟调拨");
                }
            }

        }
        //检查调拨数量
        if (cells.length > excelColNum)
            this.checkTransferQty(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                excelColNum);
        //备注
        if (cells.length > excelColNum)
            this.checkRemark(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                excelColNum);
    }

    /**
     * 检查3W调拨导入字段
     * @param line
     * @param parseErrs
     * @param cells
     * @param rowNum
     * @param channelCodes
     */
    private void check3WTransferCells(InvTransferLine line, List<String> parseErrs, Cell[] cells,
                                      int rowNum, List<String> channelCodes,
                                      List<String> soLineNums) {
        int excelColNum = 0;
        //检查调出渠道
        String channelFrom = "";
        if (cells.length > excelColNum) {
            channelFrom = cells[excelColNum++].getContents();
            this.checkChannelId(line, parseErrs, channelFrom, rowNum, excelColNum, channelCodes);
            line.setChannelFrom(channelFrom.trim().toUpperCase());
        }
        //检查调入渠道
        String channelTo = "";
        if (cells.length > excelColNum) {
            channelTo = cells[excelColNum++].getContents();
            this.checkChannelId(line, parseErrs, channelTo, rowNum, excelColNum, channelCodes);
            line.setChannelTo(channelTo.trim().toUpperCase());
        }
        //检查物料编码（sku）
        if (cells.length > excelColNum)
            this.checkItemCode(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                excelColNum);
        //检查调出库位
        String secFrom = "";
        int indexFromColNum = 0;
        if (cells.length > excelColNum) {
            indexFromColNum = excelColNum++;
            secFrom = cells[indexFromColNum].getContents();
            this.check3WSecFrom(line, parseErrs, secFrom, rowNum, excelColNum);
        }
        //检查调入库位
        String secTo = "";
        int indexToColNum = 0;
        if (cells.length > excelColNum) {
            indexToColNum = excelColNum++;
            secTo = cells[indexToColNum].getContents();
            this.check3WSecTo(line, parseErrs, secTo, rowNum, excelColNum);
        }
        //检查渠道与虚拟库位之间的关系， 新检查方式
        String[] secCodes = check3WChannelSecCode(secFrom, secTo, rowNum, parseErrs);
        if (secCodes != null && secCodes.length == 2) {
            line.setSecFrom(secCodes[0]);
            line.setSecTo(secCodes[1]);
        }
        //检查菜鸟预约号
        if (cells.length > excelColNum)
            this.checkCaiNiaoBookNum(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                excelColNum, soLineNums);
        //检查调拨数量
        if (cells.length > excelColNum)
            this.checkTransferQty(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                excelColNum);
        //检查是否免费调货
        if (cells.length > excelColNum)
            this.checkFreeTransfer(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                excelColNum);
        //备注
        if (cells.length > excelColNum)
            this.checkRemark(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                excelColNum);

    }

    /**
     * 检查3W调拨导入字段
     * @param line
     * @param parseErrs
     * @param cells
     * @param rowNum
     * @param channelCodes
     */
    private void checkYPTransferCells(InvTransferLine line, List<String> parseErrs, Cell[] cells,
                                      int rowNum, List<String> channelCodes,
                                      List<String> soLineNums) {
        int excelColNum = 0;
        //检查调出渠道
        String channelFrom = "";
        if (cells.length > excelColNum) {
            channelFrom = cells[excelColNum++].getContents();
            this.checkChannelId(line, parseErrs, channelFrom, rowNum, excelColNum, channelCodes);
            line.setChannelFrom(channelFrom.trim().toUpperCase());
        }
        //检查调入渠道
        String channelTo = "";
        if (cells.length > excelColNum) {
            channelTo = cells[excelColNum++].getContents();
            this.checkChannelId(line, parseErrs, channelTo, rowNum, excelColNum, channelCodes);
            line.setChannelTo(channelTo.trim().toUpperCase());
        }
        //检查物料编码（sku）
        if (cells.length > excelColNum)
            this.checkItemCode(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                    excelColNum);
        //检查调出库位
        String secFrom = "";
        int indexFromColNum = 0;
        if (cells.length > excelColNum) {
            indexFromColNum = excelColNum++;
            secFrom = cells[indexFromColNum].getContents();
            this.check3WSecFrom(line, parseErrs, secFrom, rowNum, excelColNum);
        }
        //检查调入库位
        String secTo = "";
        int indexToColNum = 0;
        if (cells.length > excelColNum) {
            indexToColNum = excelColNum++;
            secTo = cells[indexToColNum].getContents();
            this.check3WSecTo(line, parseErrs, secTo, rowNum, excelColNum);
        }
        //检查渠道与虚拟库位之间的关系， 新检查方式
        String[] secCodes = check3WChannelSecCode(secFrom, secTo, rowNum, parseErrs);
        if (secCodes != null && secCodes.length == 2) {
            line.setSecFrom(secCodes[0]);
            line.setSecTo(secCodes[1]);
        }
        //检查菜鸟预约号
        if (cells.length > excelColNum)
            this.checkCaiNiaoBookNum(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                    excelColNum, soLineNums);
        //检查调拨数量
        if (cells.length > excelColNum)
            this.checkTransferQty(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                    excelColNum);
        //检查是否免费调货
        if (cells.length > excelColNum)
            this.checkFreeTransfer(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                    excelColNum);
        //备注
        if (cells.length > excelColNum)
            this.checkRemark(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                    excelColNum);
        //销售额
        if (cells.length > excelColNum)
            this.checkSalseAmount(line, parseErrs, cells[excelColNum++].getContents(), rowNum,
                    excelColNum);
    }

    private String[] checkChannelSecCode(String secFrom, String channelFrom, String secTo,
                                         String channelTo, int transferType, int rowIndex,
                                         List<String> errorMsg) {
        InvSection fromSecEntry = stockModel.getSectionByCode(secFrom);
        if (fromSecEntry == null) {
            errorMsg.add("第" + rowIndex + "行，找不到调出库位");
            return null;
        }
        InvSection toSecEntry = stockModel.getSectionByCode(secTo);
        if (toSecEntry == null) {
            errorMsg.add("第" + rowIndex + "行，找不到调入库位");
            return null;
        }
        if (fromSecEntry.isChannelRRS()) {
            errorMsg.add("第" + rowIndex + "行，调出库位不支持日日顺库位");
            return null;
        }
        if (toSecEntry.isChannelRRS()) {
            errorMsg.add("第" + rowIndex + "行，调入库位不支持日日顺库位");
            return null;
        }
        //第0个是调出库位，第1个为调入库位
        String[] secCodes = new String[2];
        secFrom = secFrom.toUpperCase();
        secTo = secTo.toUpperCase();
        channelFrom = channelFrom.toUpperCase();
        channelTo = channelTo.toUpperCase();
        if (transferType == InvTransferLine.TRANSFER_REASON_XN) {
            //1.如果调出库位不是WA， 则调出渠道和调出库位对应的渠道需要一致
            //2.如果调出库位是WA，则调出渠道是任意的， 从共享中调出渠道释放的库存到调入库位（为计算库龄， 实际不准，实际需要调JD的，但JD在共享中无库存，则调其他渠道释放的）；
            if (!fromSecEntry.isChannelWA()) {
                if (!fromSecEntry.getChannelCode().equals(channelFrom)) {
                    errorMsg.add("第" + rowIndex + "行，调出库位和调出渠道不匹配");
                    return null;
                }
            } else {
                //说明向同一个库位调货
                if (fromSecEntry.getChannelCode().equals(channelTo)) {
                    errorMsg.add("第" + rowIndex + "行，调出渠道和调入渠道填写不能一致");
                    return null;
                }
            }
            if (!toSecEntry.getChannelCode().equals(channelTo)) {
                errorMsg.add("第" + rowIndex + "行，调入库位和调入渠道不匹配");
                return null;
            }
            secCodes[0] = secFrom;
            secCodes[1] = secTo;
        } else {
            /*  if (!channelFrom.equals(InvSection.CHANNEL_CODE_WA)
                  && !channelTo.equals(InvSection.CHANNEL_CODE_WA) && !channelFrom.equals(channelTo)) {
                  errorMsg.add("第" + rowIndex + "行，不同渠道间不能进行实物调拨");
                  return null;
              }*/
            if (channelTo.equals(InvSection.CHANNEL_CODE_WA)) {
                errorMsg.add("第" + rowIndex + "行，调入渠道不能为WA");
                return null;
            }
            /* if (!fromSecEntry.getChannelCode().equals(InvSection.CHANNEL_CODE_WA)
                 && !toSecEntry.getChannelCode().equals(InvSection.CHANNEL_CODE_WA)
                 && !fromSecEntry.getChannelCode().equals(toSecEntry.getChannelCode())) {
                 errorMsg.add("第" + rowIndex + "行，库位填写有误");
                 return null;
             }*/
            if (!channelFrom.equals(InvSection.CHANNEL_CODE_WA)
                && !fromSecEntry.getChannelCode().equals(InvSection.CHANNEL_CODE_WA)
                && !fromSecEntry.getChannelCode().equals(channelFrom)) {
                errorMsg.add("第" + rowIndex + "行，调出库位和调出渠道不匹配");
                return null;
            }
            if (!channelTo.equals(InvSection.CHANNEL_CODE_WA)
                && !toSecEntry.getChannelCode().equals(InvSection.CHANNEL_CODE_WA)
                && !toSecEntry.getChannelCode().equals(channelTo)) {
                errorMsg.add("第" + rowIndex + "行，调入库位和调入渠道不匹配");
                return null;
            }
            if (fromSecEntry.isChannelWA()) {
                InvSection section = stockModel.getSectionByCode(fromSecEntry.getWhCode(),
                    channelFrom);
                secCodes[0] = section.getSecCode();
            } else {
                secCodes[0] = secFrom;
            }
            if (toSecEntry.isChannelWA()) {
                InvSection section = stockModel.getSectionByCode(toSecEntry.getWhCode(), channelTo);
                secCodes[1] = section.getSecCode();
            } else {
                secCodes[1] = secTo;
            }
        }

        return secCodes;
    }

    private String[] check3WChannelSecCode(String secFrom, String secTo, int rowIndex,
                                           List<String> errorMsg) {
        //第0个是调出库位，第1个为调入库位
        String[] secCodes = new String[2];
        secFrom = secFrom.toUpperCase();
        secTo = secTo.toUpperCase();
        secCodes[0] = secFrom;
        secCodes[1] = secTo;

        return secCodes;
    }

    private void checkSameWhHouse(List<String> parseErrs, String secFrom, String secTo) {
        secFrom = secFrom.trim();
        secTo = secTo.trim();
        InvSection fromSecEntry = stockModel.getSectionByCode(secFrom);
        if (fromSecEntry == null)
            return;
        InvSection toSecEntry = stockModel.getSectionByCode(secTo);
        if (toSecEntry == null)
            return;

        if (fromSecEntry.getWhCode() != null
            && fromSecEntry.getWhCode().equals(toSecEntry.getWhCode())) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("调出库位[").append(secFrom).append("]与调入库位[").append(secTo)
                .append("]不能位于同一仓库");
            parseErrs.add(buffer.toString());
        }
    }

    private Cell newNullCell() {
        return new Cell() {

            @Override
            public boolean isHidden() {
                return false;
            }

            @Override
            public CellType getType() {
                return null;
            }

            @Override
            public int getRow() {
                return 0;
            }

            @Override
            public String getContents() {
                return "";
            }

            @Override
            public int getColumn() {
                return 0;
            }

            @Override
            public CellFormat getCellFormat() {
                return null;
            }

            @Override
            public CellFeatures getCellFeatures() {
                return null;
            }
        };
    }

    private void checkSalseAmount(InvTransferLine line, List<String> parseErrs, String salesAmount,
                                  int rowNum, int colNum){
        BigDecimal bAmount= BigDecimal.ZERO;
        if("0".equals(salesAmount)){
            salesAmount=null;
        }
        try{
             bAmount=new BigDecimal(salesAmount);
        }catch (Exception e){
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("销售额不能为空且只能是数字");
            parseErrs.add(sb.toString());
            return;
        }
        line.setSalesAmount(new BigDecimal(salesAmount));
    }

    private void checkRemark(InvTransferLine line, List<String> parseErrs, String remark,
                             int rowNum, int colNum) {
        if (!StringUtil.isEmpty(remark, true)) {
            remark = remark.trim();
            if (remark.length() > 200) {
                StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
                sb.append("备注不能多于200个字");
                parseErrs.add(sb.toString());
                return;
            }
            line.setRemark(remark);
        }
    }

    private void checkFreeTransfer(InvTransferLine line, List<String> parseErrs,
                                   String freeTransfer, int rowNum, int colNum) {
        if (StringUtil.isEmpty(freeTransfer, true)) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("是否免费调货不允许为空");
            parseErrs.add(sb.toString());
            return;
        } else {
            freeTransfer = freeTransfer.trim();
            if (!freeTransfer.equals("是") && !freeTransfer.equals("否")) {
                StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
                sb.append("是否免费调货只识别是、否");
                parseErrs.add(sb.toString());
                return;
            }
            line.setFreeTransfer(freeTransfer);
        }
    }

    private void checkTransferQty(InvTransferLine line, List<String> parseErrs, String transferQty,
                                  int rowNum, int colNum) {
        if (StringUtil.isEmpty(transferQty, true)) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("调拨数量不能为空");
            parseErrs.add(sb.toString());
            return;
        }
        transferQty = transferQty.trim();
        int qty = 0;
        try {
            qty = Integer.parseInt(transferQty);
        } catch (Exception e) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("调拨数量必须为数字,并且不能大于2147483647");
            parseErrs.add(sb.toString());
            return;
        }
        if (qty <= 0) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("调拨数量必须为正整数");
            parseErrs.add(sb.toString());
            return;
        }

        if (!StringUtil.isEmpty(line.getSecFrom()) && !StringUtil.isEmpty(line.getItemCode())) {
            //修改实物调拨不检查StorageProducts表
            String error = "";
            /*
            if (line.getTransferReason() == InvTransferLine.TRANSFER_REASON_XN) {
                //1.虚拟调拨可用数返回为0，则不能上传
                //2.返回数量大于0，但是不够，允许提交
                ServiceResult<Integer> result = transferLineService.checkStorageForBaseStock(
                    line.getSecFrom(), line.getItemCode(), qty);
                //error = result.getMessage();
                if (result.getResult() <= 0) {
                    error = result.getMessage();
                } else {
                    line.setQty(qty);
                    line.setTransferQty(result.getResult());
                }
            
            } else {
                ServiceResult<String> result = transferLineService
                    .checkStorageForUploadTransferRecord(line.getSecFrom(), line.getItemCode(), qty);
                error = result.getMessage();
                line.setQty(qty);
                line.setTransferQty(qty);
            }
            */
            //期望
            line.setQty(qty);
            //实际
            line.setTransferQty(0);

            /* if (!ret.getSuccess()) {
                 throw new BusinessException(ret.getMessage());
             }*/
            if (!StringUtil.isEmpty(error)) {
                StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
                sb.append(error);
                parseErrs.add(sb.toString());
                return;
            }

        }

    }

    private void checkSecTo(InvTransferLine line, List<String> parseErrs, String secTo, int rowNum,
                            int colNum, boolean isXnTransfer) {
        if (StringUtil.isEmpty(secTo, true)) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("调入库位不能为空");
            parseErrs.add(sb.toString());
            return;
        }
        secTo = secTo.trim();
        InvSection section = stockModel.getSectionByCode(secTo);
        if (section == null) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("找不到库位编码[").append(secTo).append("]对应的库位");
            parseErrs.add(sb.toString());
            return;
        }
        if (!section.isChannelWA() && !isXnTransfer) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("库位编码[").append(secTo).append("]对应的库位不可进行实物调拨");
            parseErrs.add(sb.toString());
            return;
        }
        line.setSecTo(secTo);
    }

    private void check3WSecTo(InvTransferLine line, List<String> parseErrs, String secTo,
                              int rowNum, int colNum) {
        if (StringUtil.isEmpty(secTo, true)) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("调入库位不能为空");
            parseErrs.add(sb.toString());
            return;
        }
        secTo = secTo.trim();
        AreaCenterInfoDTO condition = new AreaCenterInfoDTO();
        condition.setWhCode(secTo);
        AreaCenterInfoDTO dto = areaCenterInfoService.getOneByCondition(condition);
        if (dto == null) {
//        if (!sCode3WSet.contains(secTo)) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("找不到库位编码[").append(secTo).append("]对应的库位");
            parseErrs.add(sb.toString());
            return;
        }
        line.setSecTo(secTo);
    }

    private void checkSecFrom(InvTransferLine line, List<String> parseErrs, String secFrom,
                              int rowNum, int colNum, boolean isXnTransfer) {
        if (StringUtil.isEmpty(secFrom, true)) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("调出库位不能为空");
            parseErrs.add(sb.toString());
            return;
        }
        secFrom = secFrom.trim();
        InvSection section = stockModel.getSectionByCode(secFrom);
        if (section == null) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("找不到库位编码[").append(secFrom).append("]对应的库位");
            parseErrs.add(sb.toString());
            return;
        }
        if (!section.isChannelWA() && !isXnTransfer) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("库位编码[").append(secFrom).append("]对应的库位不可进行实物调拨");
            parseErrs.add(sb.toString());
            return;
        }
        line.setSecFrom(secFrom);
    }

    private void check3WSecFrom(InvTransferLine line, List<String> parseErrs, String secFrom,
                                int rowNum, int colNum) {
        if (StringUtil.isEmpty(secFrom, true)) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("调出库位不能为空");
            parseErrs.add(sb.toString());
            return;
        }
        secFrom = secFrom.trim();
        InvSection section = stockModel.getSectionByCode(secFrom);
        if (section == null) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("找不到库位编码[").append(secFrom).append("]对应的库位");
            parseErrs.add(sb.toString());
            return;
        }
        line.setSecFrom(secFrom);
    }

    private void checkItemCode(InvTransferLine line, List<String> parseErrs, String itemCode,
                               int rowNum, int colNum) {
        if (StringUtil.isEmpty(itemCode, true)) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("物料编码不能为空");
            parseErrs.add(sb.toString());
            return;
        }
        itemCode = itemCode.trim().toUpperCase();
        ItemBase base = stockModel.getItemBaseBySku(itemCode);
        if (base == null) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("找不到物料编码[").append(itemCode).append("]对应的产品");
            parseErrs.add(sb.toString());
            return;
        }
        line.setItemCode(itemCode);
        line.setItemId(base.getId());
        line.setItemName(base.getMaterialDescription());
    }

    private void checkSoLineNum(InvTransferLine line, List<String> parseErrs, String contents,
                                int rowNum, int colNum, List<String> soLineNums) {
        if (StringUtil.isEmpty(contents, true)) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("网单号不能为空");
            parseErrs.add(sb.toString());
            return;
        }
        contents = contents.trim();
        if (contents.length() > 18) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("网单号[").append(contents).append("]过长");
            parseErrs.add(sb.toString());
            return;
        }
        if (soLineNums.contains(contents)) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("网单号[").append(contents).append("]重复");
            parseErrs.add(sb.toString());
            return;
        } else {
            soLineNums.add(contents);
        }

        String lineNum = contents + "DH";
        ServiceResult<Boolean> checkRet = transferLineService
            .checkQHLineNumForUploadTransferRecord(lineNum);
        if (!checkRet.getSuccess())
            throw new BusinessException("检查销售网单号码时发生异常");
        if (!checkRet.getResult()) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("网单号[").append(contents).append("]已入库");
            parseErrs.add(sb.toString());
            return;
        }

        ServiceResult<OrderProductsNew> orderRet = orderService.getOrderProductByCOrderSn(contents);
        if (!orderRet.getSuccess())
            throw new BusinessException("根据网单号取得网单时发生异常");
        OrderProductsNew product = orderRet.getResult();
        if (product == null) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("网单号[").append(contents).append("]找不到对应网单");
            parseErrs.add(sb.toString());
            return;
        }
        if (product.getStatus().intValue() >= OrderProducts.STATUS_LES_SHIPPING.intValue()) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("网单号[").append(contents).append("]对应的网单状态不正确");
            parseErrs.add(sb.toString());
            return;
        }

        line.setLineNum(lineNum);
        line.setSoLineNum(contents);
    }

    private void checkCaiNiaoBookNum(InvTransferLine line, List<String> parseErrs,
                                     String caiNiaoBookNum, int rowNum, int colNum,
                                     List<String> caiNiaoBookNumList) {
        if (StringUtil.isEmpty(caiNiaoBookNum, true)) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("菜鸟预约号不允许为空");
            parseErrs.add(sb.toString());
            return;
        }

        if (caiNiaoBookNum.length() > 20) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("菜鸟预约号最大长度20位");
            parseErrs.add(sb.toString());
            return;
        }
        if (caiNiaoBookNumList.contains(caiNiaoBookNum)) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("菜鸟预约号[").append(caiNiaoBookNum).append("]重复");
            parseErrs.add(sb.toString());
            return;
        } else {
            caiNiaoBookNumList.add(caiNiaoBookNum);
        }
        ServiceResult<InvTransferLine> rs = transferLineService
            .getInvTransferLineBySoLineNum(caiNiaoBookNum.trim());
        if (!rs.getSuccess())
            throw new BusinessException("通过服务获取菜鸟预约号信息失败");
        InvTransferLine tempLine = rs.getResult();
        if (tempLine != null) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("菜鸟预约号已存在，不允许重复导入");
            parseErrs.add(sb.toString());
            return;
        }
        //        line.setLineNum(caiNiaoBookNum.trim());
        line.setSoLineNum(caiNiaoBookNum.trim());
    }

    private void checkReqdep(InvTransferLine line, List<String> parseErrs, String reqdep,
                             int rowNum, int colNum) {
        if (StringUtil.isEmpty(reqdep, true)) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("发起部门不能为空");
            parseErrs.add(sb.toString());
            return;
        }
        reqdep = reqdep.trim();
        if (reqdep.length() > 2) {
            reqdep = reqdep.substring(0, 2);
        }
        reqdep = reqdep.toUpperCase();
        if (!InvTransferLine.REQ_DEP_DK.equals(reqdep) && !InvTransferLine.REQ_DEP_SC.equals(reqdep)
            && !InvTransferLine.REQ_DEP_TB.equals(reqdep)
            && !InvTransferLine.REQ_DEP_YY.equals(reqdep)
            && !InvTransferLine.REQ_DEP_CX.equals(reqdep)) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("部门编码[").append(reqdep).append("]错误，正确的部门编码为[SC,TB,DK,YY,CX]");
            parseErrs.add(sb.toString());
            return;
        }
        line.setReqDep(reqdep);
    }

    private void checkChannelId(InvTransferLine line, List<String> parseErrs, String channelId,
                                int rowNum, int colNum, List<String> channelCodes) {
        if (StringUtil.isEmpty(channelId, true)) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("渠道不能为空");
            parseErrs.add(sb.toString());
            return;
        }
        channelId = channelId.trim().toUpperCase();
        /*if (channelId.length() > 2) {
            channelId = channelId.substring(0, 2);
        }*/
        if (!checkChannelId(channelCodes, channelId)) {
            StringBuffer sb = this.getErrMsgForRowNumAndColNum(rowNum, colNum);
            sb.append("渠道编码[").append(channelId).append("]错误");
            parseErrs.add(sb.toString());
            return;
        }

    }

    /**
     * 检查渠道编码是否可用， 渠道中包含共享WA， 并将渠道统一化，不截取DKH渠道，以免出错
     * @param channelCodes
     * @param channelId
     * @return
     */
    private boolean checkChannelId(List<String> channelCodes, String channelId) {
        for (String code : channelCodes) {
            /*if (code.length() > 2) {
                code = code.substring(0, 2);
            }*/
            if (code.equalsIgnoreCase(channelId)) {
                return true;
            }
        }
        return false;
    }

    private StringBuffer getErrMsgForRowNumAndColNum(int rowNum, int colNum) {
        StringBuffer sb = new StringBuffer();
        sb.append(rowNum).append("行").append(colNum).append("列:");
        return sb;
    }

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

    private Map<String, Object> getParams(HttpServletRequest request) {
        Map<String, Object> ret = new HashMap<String, Object>();
        String tempLineNum = request.getParameter("lineNum");
        if (StringUtil.isEmpty(tempLineNum, true)) {
            ret.put("lineNum", null);
        } else {
            ret.put("lineNum", tempLineNum.trim().split(","));
        }
        String tempLbx = request.getParameter("lbx");
        if (StringUtil.isEmpty(tempLbx, true)) {
            ret.put("lbx", null);
        } else {
            ret.put("lbx", tempLbx.trim().split(","));
        }
        ret.put("startTime", request.getParameter("startTime"));
        ret.put("endTime", request.getParameter("endTime"));
        //        if (!StringUtil.isEmpty(request.getParameter("endTime"))) {
        //            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //            Date date = null;
        //            try {
        //                date = sdf.parse(request.getParameter("endTime"));
        //            } catch (ParseException e) {
        //                logger.error("参数结束时间转型时发生异常", e);
        //                throw new RuntimeException("参数结束时间转型时发生异常");
        //            }
        //            Calendar cald = Calendar.getInstance();
        //            cald.setTime(date);
        //            cald.add(Calendar.DAY_OF_MONTH, 1);
        //            ret.put("endTime", sdf.format(cald.getTime()));
        //        }
        //调出渠道
        String channelFrom = request.getParameter("channelFrom");

        ret.put("channelFrom", channelFrom);
        String channelTo = request.getParameter("channelTo");

        ret.put("channelTo", channelTo);

        ret.put("reqDep", request.getParameter("reqDep"));
        if (!StringUtil.isEmpty(request.getParameter("transferReason"))) {
            ret.put("transferReason", Integer.parseInt(request.getParameter("transferReason")));
        }

        ret.put("itemCode", request.getParameter("itemCode"));
        ret.put("secFrom", request.getParameter("secFrom"));

        ret.put("secTo", request.getParameter("secTo"));
        String[] lineStatus = request.getParameterValues("lineStatus");
        if (lineStatus != null && lineStatus.length > 0) {
            boolean allExists = false;
            for (String status : lineStatus) {
                if ("ALL".equals(status)) {
                    allExists = true;
                    break;
                }
            }
            if (!allExists)
                ret.put("lineStatus", lineStatus);
        }
        ret.put("itemCode", request.getParameter("itemCode"));
        ret.put("secFrom", request.getParameter("secFrom"));
        return ret;
    }

    private void addPagerParam(Map<String, Object> modelMap, PagerInfo pager) {
        Integer totalPage = pager.getRowsCount() % pager.getPageSize() == 0
            ? pager.getRowsCount() / pager.getPageSize()
            : pager.getRowsCount() / pager.getPageSize() + 1;
        modelMap.put("totalCount", pager.getRowsCount());
        if (pager.getPageIndex() > 1)
            modelMap.put("hasFirst", true);
        else
            modelMap.put("hasFirst", false);
        if (pager.getPageIndex() - 1 > 0)
            modelMap.put("hasPrevious", true);
        else
            modelMap.put("hasPrevious", false);
        if (pager.getPageIndex() + 1 <= totalPage)
            modelMap.put("hasNext", true);
        else
            modelMap.put("hasNext", false);
        if (totalPage > 1 && pager.getPageIndex() != totalPage)
            modelMap.put("hasLast", true);
        else
            modelMap.put("hasLast", false);
        modelMap.put("curPage", totalPage > 0 ? pager.getPageIndex() : 0);
        modelMap.put("totalPage", totalPage);
        modelMap.put("startNum", pager.getStart() + 1);
        modelMap.put("endNum",
            (pager.getStart() + pager.getPageSize()) > pager.getRowsCount()
                ? (pager.getStart() + pager.getRowsCount() % pager.getPageSize())
                : pager.getStart() + pager.getPageSize());
    }

    /**
     * 进入调拨费用审核页面
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "/transferFeeAuditIndex" }, method = { RequestMethod.GET })
    public String transferFeeAuditIndex(HttpServletRequest request, HttpServletResponse response,
                                        Map<String, Object> modelMap) {
        modelMap.put("endTime", DateUtil.format(new Date(), "yyyy-MM-dd"));
        modelMap.put("startTime",
            DateUtil.format(DateUtil.add(new Date(), Calendar.DAY_OF_YEAR, -7), "yyyy-MM-dd"));
        return "stock/transferFeeAuditIndexPage";
    }

    /**
     * 取得调拨费用审核列表
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = { "/transferFeeAuditList" }, method = { RequestMethod.GET })
    @ResponseBody
    public Map<String, Object> transferFeeAuditList(HttpServletRequest request, HttpServletResponse response,
                                       Map<String, Object> modelMap, Integer page, Integer rows, String lineNum, 
                                       String lineStatus, String startTime, String endTime) throws Exception {
    	Map<String, Object> map = new HashMap<>();
    	
        Map<String, Object> params = new HashMap<>();
        if(StringUtils.isNotBlank(lineNum)){
        	params.put("lineNum", lineNum.split(","));
        }
        if(StringUtils.isNotBlank(startTime)){
        	params.put("startTime", new SimpleDateFormat("yyyy-MM-dd").parse(startTime));
        }
        if(StringUtils.isNotBlank(endTime)){
        	params.put("endTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime + " 23:59:59"));
        }
        if(StringUtils.isNotBlank(lineStatus)){
        	params.put("lineStatus", lineStatus.split(","));
        }

        PagerInfo pager = new PagerInfo(rows, page);

        ServiceResult<List<InvTransferLine>> result = transferLineService.getTransferLines(params,
            pager);
        pager = result.getPager();

        if (result.getSuccess() && result.getResult() != null) {
            map.put("total", result.getPager().getRowsCount());
            map.put("rows", result.getResult());
        }
        return map;
    }

    /**
     * 调拨费用审核
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping(value = { "/transferFeeAudit" }, method = { RequestMethod.POST })
    public @ResponseBody HttpJsonResult<Boolean> transferFeeAudit(HttpServletRequest request,
                                                                  HttpServletResponse response,
                                                                  Map<String, Object> modelMap) {
        HttpJsonResult<Boolean> ret = new HttpJsonResult<Boolean>();
        String lineIdStr = request.getParameter("lineId");
        String operation = request.getParameter("operation");
        String reason = request.getParameter("reason");
        this.checkTransferFeeAuditParams(lineIdStr, operation, reason);

        ServiceResult<Boolean> sret = transferLineService.transferFeeAudit(
            Integer.parseInt(lineIdStr), operation, reason, WebUtil.currentUserName(request));
        ret.setMessage(sret.getMessage());
        return ret;
    }

    private void checkTransferFeeAuditParams(String lineIdStr, String operation, String reason) {
        Integer lineId = null;
        try {
            lineId = Integer.parseInt(lineIdStr);
        } catch (Exception e) {
            logger.error("参数lineId不正确-->lineId=" + lineIdStr);
            throw new BusinessException("参数lineId不正确");
        }
        if (lineId <= 0) {
            logger.error("参数lineId不正确-->lineId=" + lineIdStr);
            throw new BusinessException("参数lineId不正确");
        }
        if (StringUtil.isEmpty(operation)) {
            logger.error("参数operation不正确-->operation=" + operation);
            throw new BusinessException("参数operation不正确");
        }
    }

    private static final Set<String> sCode3WSet = new HashSet<String>();

    static {
        sCode3WSet.add("AKO1");
        sCode3WSet.add("ASO1");
        sCode3WSet.add("BBO1");
        sCode3WSet.add("BDO1");
        sCode3WSet.add("BJO2");
        sCode3WSet.add("BSO1");
        sCode3WSet.add("BTO1");
        sCode3WSet.add("CCO1");
        sCode3WSet.add("CDO1");
        sCode3WSet.add("CNO1");
        sCode3WSet.add("CQO1");
        sCode3WSet.add("CSO1");
        sCode3WSet.add("CYO2");
        sCode3WSet.add("CZO1");
        sCode3WSet.add("DBO1");
        sCode3WSet.add("DCO1");
        sCode3WSet.add("DGO1");
        sCode3WSet.add("DLO1");
        sCode3WSet.add("DQO1");
        sCode3WSet.add("DTO1");
        sCode3WSet.add("DWO1");
        sCode3WSet.add("FSO1");
        sCode3WSet.add("FYO1");
        sCode3WSet.add("FZO1");
        sCode3WSet.add("GAO1");
        sCode3WSet.add("GSO1");
        sCode3WSet.add("GXO1");
        sCode3WSet.add("GYO1");
        sCode3WSet.add("HAO1");
        sCode3WSet.add("HBO1");
        sCode3WSet.add("HDO1");
        sCode3WSet.add("HFO1");
        sCode3WSet.add("HKO1");
        sCode3WSet.add("HMO1");
        sCode3WSet.add("HNO2");
        sCode3WSet.add("HSO1");
        sCode3WSet.add("HXO1");
        sCode3WSet.add("HYO1");
        sCode3WSet.add("HZO1");
        sCode3WSet.add("JHO1");
        sCode3WSet.add("JMO1");
        sCode3WSet.add("JNO2");
        sCode3WSet.add("JOO2");
        sCode3WSet.add("JOWA");
        sCode3WSet.add("JOWB");
        sCode3WSet.add("JXO1");
        sCode3WSet.add("JZO1");
        sCode3WSet.add("KMO1");
        sCode3WSet.add("LAO1");
        sCode3WSet.add("LDO1");
        sCode3WSet.add("LGO1");
        sCode3WSet.add("LHO2");
        sCode3WSet.add("LMO2");
        sCode3WSet.add("LXO1");
        sCode3WSet.add("LYO2");
        sCode3WSet.add("LZO1");
        sCode3WSet.add("MMO1");
        sCode3WSet.add("MYO1");
        sCode3WSet.add("NBO2");
        sCode3WSet.add("NCO1");
        sCode3WSet.add("NJO1");
        sCode3WSet.add("NNO1");
        sCode3WSet.add("NPO1");
        sCode3WSet.add("NWO2");
        sCode3WSet.add("NYO2");
        sCode3WSet.add("QZO1");
        sCode3WSet.add("SCO1");
        sCode3WSet.add("SDO2");
        sCode3WSet.add("SHO2");
        sCode3WSet.add("SOO1");
        sCode3WSet.add("SOWA");
        sCode3WSet.add("SQO2");
        sCode3WSet.add("SRO1");
        sCode3WSet.add("STO1");
        sCode3WSet.add("SYO1");
        sCode3WSet.add("TJO1");
        sCode3WSet.add("TSO1");
        sCode3WSet.add("TYO1");
        sCode3WSet.add("TZO1");
        sCode3WSet.add("WFO2");
        sCode3WSet.add("WHO2");
        sCode3WSet.add("WNO1");
        sCode3WSet.add("WXO1");
        sCode3WSet.add("WYO1");
        sCode3WSet.add("XAO1");
        sCode3WSet.add("XFO2");
        sCode3WSet.add("XJO1");
        sCode3WSet.add("XMO1");
        sCode3WSet.add("XNO1");
        sCode3WSet.add("XXO1");
        sCode3WSet.add("XYO2");
        sCode3WSet.add("XZO1");
        sCode3WSet.add("YBO1");
        sCode3WSet.add("YCO1");
        sCode3WSet.add("YLO1");
        sCode3WSet.add("YTO2");
        sCode3WSet.add("ZBO2");
        sCode3WSet.add("ZJO1");
        sCode3WSet.add("ZYO1");
        sCode3WSet.add("ZZO2");
        sCode3WSet.add("SXO1");
    }

    public static void main(String[] args) {
        String str = "LBX0227541000716528";
        System.out.println(str.length());
    }
}
