package com.haier.svc.api.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.order.service.RejectsdetailService;
import com.haier.shop.model.Outstoragelist;
import com.haier.shop.model.Rejectsdetail;
import com.haier.svc.api.controller.excel.ExcelReadHandler;
import com.haier.svc.api.controller.util.CommUtil;
import com.haier.svc.api.controller.util.ExcelCallbackInterfaceUtil;
import com.haier.svc.api.controller.util.ExcelExportUtil;
import com.haier.svc.api.controller.util.excel.ExcelHandler;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.apache.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/8/14.
 */
@RequestMapping(value = "/Rejectsdetail")
@Controller
public class RejectsdetailController {
    private final static org.apache.log4j.Logger LOGGER = LogManager
            .getLogger(RejectsdetailController.class);

    @Resource
    private RejectsdetailService rejectsdetailService;


    /**
     * 获取当前登录的用户
     */
    private String getUserName() {
        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        return (String) attr.getRequest().getSession().getAttribute("userName");
    }

    @RequestMapping("toPage")
    public String toPage() {
        return "afterSaleReport/badProductRateJD";
    }

   /* @RequestMapping("toPage")
    public String toPage() {
        return "afterSaleReport/newsabc";
    }
*/
    @RequestMapping(value ="getList")
    @ResponseBody
    public String getList( String month,String year) {
        Rejectsdetail rejectsdetail=new Rejectsdetail();
        rejectsdetail.setMonth(month);
        rejectsdetail.setYear(Integer.valueOf(year));
        JSONObject jsonObject = rejectsdetailService.paging(rejectsdetail);

        return jsonObject.toString();
    }

    /**
     * 责任占比
     * @param month
     * @param year
     * @return
     */
    @RequestMapping(value ="responsibility")
    @ResponseBody
    public String responsibility( String month,String year) {
        Rejectsdetail rejectsdetail=new Rejectsdetail();
        List<Object> obj=new ArrayList<>();
        if(month==null || "全年".equals(month)){
            rejectsdetail.setYear(Integer.valueOf(year));
            obj = rejectsdetailService.responsibility(rejectsdetail);
        }else{
            rejectsdetail.setMonth(month);
            rejectsdetail.setYear(Integer.valueOf(year));
            obj = rejectsdetailService.responsibility(rejectsdetail);
        }
        return obj.toString();
    }

    /**
     * 处理方式占比
     * @param month
     * @param year
     * @return
     */
    @RequestMapping(value ="Processing")
    @ResponseBody
    public String Processing( String month,String year) {
        Rejectsdetail rejectsdetail=new Rejectsdetail();
        List<Object> obj=new ArrayList<>();
        if(month==null || "全年".equals(month)){
            rejectsdetail.setYear(Integer.valueOf(year));
             obj = rejectsdetailService.Processing(rejectsdetail);
        }else{
            rejectsdetail.setMonth(month);
            rejectsdetail.setYear(Integer.valueOf(year));
            obj = rejectsdetailService.Processing(rejectsdetail);
        }
        return obj.toString();
    }


    /**
     * 导入
     */
    @PostMapping("import")
    @ResponseBody
    public Object importExcel(@RequestParam MultipartFile file, HttpServletRequest request) {
        ServiceResult<String> result = new ServiceResult<>();
        ExcelReadHandler reader = new ExcelReadHandler();
        List<Rejectsdetail> data = new ArrayList<>();
        ExcelHandler eh = new ExcelHandler(Rejectsdetail.class);
        try {
            eh.convertToEntity(reader.readExcel(file), data);
            for(Rejectsdetail rej:data){
                if(!rej.getGuaranteeDays().matches("-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?")){
                    result.setSuccess(false);
                    result.setMessage("数据导入异常");
                    return result;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("数据导入异常");
            return result;
        }
        String userName = getUserName();
        for (Rejectsdetail o : data) {
            o.setUploadPerson(userName);
        }
        return rejectsdetailService.execExcel(data);
    }

    /**
     * 出库产品导入
     * @param file
     * @param request
     * @return
     */
    @PostMapping("outimport")
    @ResponseBody
    public Object outimportExcel(@RequestParam MultipartFile file, HttpServletRequest request) {
        ServiceResult<String> result = new ServiceResult<>();
        ExcelReadHandler reader = new ExcelReadHandler();
        List<Outstoragelist> data = new ArrayList<>();
        ExcelHandler eh = new ExcelHandler(Outstoragelist.class);
        try {
            eh.convertToEntity(reader.readExcel(file), data);
            for(Outstoragelist out:data){
               if( !out.getMonths().matches("[0-9]{1,}" )&& !out.getYears().matches(("[0-9]{1,}"))){
                   result.setSuccess(false);
                   result.setMessage("数据导入异常");
                   return result;
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("数据导入异常");
            return result;
        }
        String userName = getUserName();
        for (Outstoragelist o : data) {
            o.setUploadPerson(userName);
        }
        return rejectsdetailService.outexecExcel(data);
    }

    /**
     * 导出不良品信息
     * @param beginbad
     * @param endbad
     * @param year
     */
    @RequestMapping("export")
    @ResponseBody
    public void export(String beginbad,String endbad,String year,HttpServletRequest request, HttpServletResponse response) {
        Rejectsdetail param=new Rejectsdetail();
        param.setMonth(beginbad);
        param.setMonth2(endbad);
        param.setYear(Integer.valueOf(year));
        //获取开单列表List
        final   List<Rejectsdetail> resultList = rejectsdetailService.importbad(param);
        String fileName = "不良品商品信息表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "数据导出";

        String[] sheetHead = new String[] { "序号","中心", "入库单号", "签收时间", "服务单申请时间", "保修天数", "拆包时间"
                , "申请时间", "上架时间", "备件条码", "一级品类", "二级品类", "三级品类"
                , "品牌", "商品编号", "商品名称", "入库类型"};
        try {
            ExcelExportUtil.exportEntity(LOGGER, request, response, fileName, sheetName, sheetHead,
                    new ExcelCallbackInterfaceUtil() {

                        @Override
                        public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp)
                                throws Exception {
                            setExportMemberInvoiceListbad(sheet, temp, resultList);
                        }

                    });
        } catch (Exception e) {
            LOGGER.error("不良品商品信息表导出失败", e);
            e.printStackTrace();
        }

    }

    /**
     * 导出出库商品信息
     * @param beginout
     * @param endout
     * @param years
     */
    @RequestMapping("exportout")
    public void exportout(String beginout,String endout,String years,HttpServletRequest request, HttpServletResponse response) {
        Outstoragelist param=new Outstoragelist();
        param.setMonths(beginout);
        param.setMonths2(endout);
        param.setYears(years);
        //获取开单列表List
        final  List<Outstoragelist> resultList = rejectsdetailService.importout(param);
        String fileName = "出库商品信息表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "数据导出";

        String[] sheetHead = new String[] { "序号", "年份", "月份", "三级品类", "数量"};
        try {
            ExcelExportUtil.exportEntity(LOGGER, request, response, fileName, sheetName, sheetHead,
                    new ExcelCallbackInterfaceUtil() {

                        @Override
                        public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp)
                                throws Exception {
                            setExportMemberInvoiceList(sheet, temp, resultList);
                        }

                    });
        } catch (Exception e) {
            LOGGER.error("出库商品信息表导出失败", e);
            e.printStackTrace();
        }
    }

    /**
     * 导出不良品具体数据，实现回调函数
     * @param sheet
     * @param temp 行号
     * @param list 传入需要导出的 list
     * @throws WriteException
     */
    private void setExportMemberInvoiceListbad(WritableSheet sheet, int temp, List<Rejectsdetail> list)
            throws Exception {
        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat textFormat = new WritableCellFormat(font);
        textFormat.setAlignment(Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        DisplayFormat displayFormat = NumberFormats.INTEGER;
        WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
        numberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        int index = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Rejectsdetail rejectsdetail : list) {
            index++;
            int i = 0;
            sheet.setColumnView(0, 10);
            sheet.addCell(new Label(0, temp, CommUtil.getStringValue(index), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(rejectsdetail
                    .getCore()), textFormat));
            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(rejectsdetail
                    .getWarehouseNumber()), textFormat));
            sheet.setColumnView(++i, 15);
            if(rejectsdetail.getSigninTime()==null){
                sheet.addCell(new Label(i, temp, "", textFormat));
            }else{
                sheet.addCell(new Label(i, temp, simpleDateFormat.format(rejectsdetail
                        .getSigninTime()), textFormat));
            }
            sheet.setColumnView(++i, 15);
            if(rejectsdetail.getServicelistApplyTime()==null){
                sheet.addCell(new Label(i, temp, "", textFormat));
            }else{
                sheet.addCell(new Label(i, temp, simpleDateFormat.format(rejectsdetail
                        .getServicelistApplyTime()), textFormat));
            }
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(rejectsdetail
                    .getGuaranteeDays()), textFormat));
            sheet.setColumnView(++i, 25);
            if(rejectsdetail.getDismantlingTime()==null){
                sheet.addCell(new Label(i, temp, "", textFormat));
            }else{
                sheet.addCell(new Label(i, temp, simpleDateFormat.format(rejectsdetail
                        .getDismantlingTime()), textFormat));
            }
            sheet.setColumnView(++i, 15);
            if(rejectsdetail.getApplyTime()==null){
                sheet.addCell(new Label(i, temp, "", textFormat));
            }else{
                sheet.addCell(new Label(i, temp, simpleDateFormat.format(rejectsdetail
                        .getApplyTime()), textFormat));
            }
            sheet.setColumnView(++i, 15);
            if(rejectsdetail.getGroundingTime()==null){
                sheet.addCell(new Label(i, temp, "", textFormat));
            }else{
                sheet.addCell(new Label(i, temp, simpleDateFormat.format(rejectsdetail
                        .getGroundingTime()), textFormat));
            }
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(rejectsdetail
                    .getAttachmentBarCode()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(rejectsdetail
                    .getOneCategory()), textFormat));
            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(rejectsdetail
                    .getTwoCategory()), textFormat));
            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(rejectsdetail
                    .getThreeCategory()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(rejectsdetail
                    .getBrand()), textFormat));
            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(rejectsdetail
                    .getCommodityNumber()), textFormat));
            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(rejectsdetail
                    .getCommodityName()), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(rejectsdetail
                    .getReservoirType()), textFormat));
            temp++;
        }
    }

    /**
     * 导出出库商品具体数据，实现回调函数
     * @param sheet
     * @param temp 行号
     * @param list 传入需要导出的 list
     * @throws WriteException
     */
    private void setExportMemberInvoiceList(WritableSheet sheet, int temp, List<Outstoragelist> list)
            throws Exception {
        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat textFormat = new WritableCellFormat(font);
        textFormat.setAlignment(Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        DisplayFormat displayFormat = NumberFormats.INTEGER;
        WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
        numberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        int index = 0;
        for (Outstoragelist orderPriceGate : list) {
            index++;
            int i = 0;
            sheet.setColumnView(0, 10);
            sheet.addCell(new Label(0, temp, CommUtil.getStringValue(index), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                    .getYears()), textFormat));
            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                    .getMonths()), textFormat));
            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                    .getThreeCategory()), textFormat));
            sheet.setColumnView(++i, 15);
            sheet.addCell(new Label(i, temp, CommUtil.getStringValue(orderPriceGate
                    .getOutqty()), textFormat));
            temp++;
        }
    }





}


