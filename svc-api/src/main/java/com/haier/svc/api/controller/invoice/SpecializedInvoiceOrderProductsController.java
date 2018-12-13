package com.haier.svc.api.controller.invoice;

import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.invoice.util.CommUtil;
import com.haier.invoice.util.ExcelCallbackInterfaceUtil;
import com.haier.invoice.util.ExcelExportUtil;
import com.haier.shop.model.OrderProductsVo;
import com.haier.shop.service.SpecializedInvoiceOrderProductsService;
import com.haier.stock.model.OrderProductStatus;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网单列表-专用
 *
 * @Author:JinXueqian
 * @Date: 2018/6/4 15:52
 */
@Controller
@RequestMapping("invoice/")
public class SpecializedInvoiceOrderProductsController {

    private static final Logger logger = LogManager.getLogger(SpecializedInvoiceOrderProductsController.class);

    @Autowired
    private SpecializedInvoiceOrderProductsService specializedInvoiceOrderProductsService;

    /**
     * 页面跳转
     *
     * @return
     */
    @RequestMapping(value = {"loadSpecializedInvoiceOrderProductsListPage.html"}, method = {RequestMethod.GET})
    public String loadInvoiceOrderProductsListPage() {
        return "invoice/specializedInvoiceOrderProductsList";
    }

    /**
     * 分页查询
     *
     * @param vo
     * @param request
     * @param response
     */
    @RequestMapping(value = "getSpecializedInvoiceOrderProductsList.html", method = {RequestMethod.POST})
    @ResponseBody
    public void getSpecializedInvoiceOrderProductsList(OrderProductsVo vo, HttpServletRequest request, HttpServletResponse response) {
        if (vo.getRows() == null) {
            vo.setRows(50);
        }
        if (vo.getPage() == null) {
            vo.setPage(1);
        }
        try {
            if (vo.getPage() > 0) {
                vo.setPage((vo.getPage() - 1) * vo.getRows());
            } else {
                vo.setPage(0);
            }
            ServiceResult<List<OrderProductsVo>> result = specializedInvoiceOrderProductsService.getSpecializedInvoiceOrderProductsList(vo);
            if (result.getSuccess() && result.getResult() != null) {
                List<OrderProductsVo> predictstocklist = result.getResult();
                Map<String, Object> retMap = new HashMap<>(100);
                retMap.put("total", result.getPager().getRowsCount());
                retMap.put("rows", predictstocklist);
                response.setCharacterEncoding("UTF-8");
                String json = JSON.toJSONString(retMap);
                response.getWriter().write(json);
                response.getWriter().flush();
                response.getWriter().close();
            }
        } catch (Exception e) {
            logger.error("[SpecializedInvoiceOrderProductsController]关联查询时发生未知错误", e);

        }

    }

    /**
     * 导出网单列表
     *
     * @param vo
     * @param request
     * @param response
     */
    @RequestMapping(value = {"exportSpecializedInvoiceOrderProductsList.html"})
    public void exportInvoiceOrderProductsList(OrderProductsVo vo, HttpServletRequest request, HttpServletResponse response) {
        final List<Map<String, Object>> resultList = specializedInvoiceOrderProductsService.getListByParams(vo);

        String fileName = "网单列表-专用报表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "数据导出";

            String[] sheetHead = new String[]{"序号", "网单编号", "订单编号","网单状态", "订单状态","订单支付状态","开票状态", "商品名称", "价格", "数量", "金额", "库位", "下单时间", "备注", "首次确认时间", "已确认次数", "转人工时间"};
            try {
                ExcelExportUtil.exportEntity(request, response, fileName, sheetName, sheetHead, new ExcelCallbackInterfaceUtil() {
                    @Override
                    public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp) throws Exception {
                        setExcelBodyTotalForInvoiceOrderProductsList(sheet, temp, resultList);
                    }
                });
            } catch (Exception e) {
                logger.error("网单列表导出失败", e);
                e.printStackTrace();
            }
    }

    /**
     * 前台easyui请求查询 明细 （网单）
     * @throws ParseException
     * @throws
     */
    @RequestMapping("getOrderOroductsDetail.html")
    public String getOrderOroductsDetail(String cOrderSn, ModelMap model){
        OrderProductsVo vodata= specializedInvoiceOrderProductsService.getOrderOroductsDetail(cOrderSn);
        model.put("orderProduct",vodata);
        return "invoice/specializedInvoiceOrderProductsDetail";
    }

    /**
     * 修改网单单价和金额
     * @param id
     * @param price
     * @param money
     * @return
     * updateOrderProductsPriceAndProductAmount.html
     */
    @PostMapping("updateOrderProductsPriceAndProductAmount.html")
    @ResponseBody
    public String updateOrderProductsPriceAndProductAmount(
                                        @RequestParam(required = false) String id,
                                        @RequestParam(required = false) String price,
                                        @RequestParam(required = false) String money){
        JSONObject json = new JSONObject();

        try {
        Map<String, Object> params = new HashMap();
        params.put("id", id);
        params.put("price", price);
        params.put("money", money);
        Integer result = specializedInvoiceOrderProductsService.updateOrderProductsPriceAndProductAmount(params);
        if (result > 0 ){
            json.put("flag",1);
            return json.toString();
        }else {
            json.put("flag",2);
            return json.toString();
        }
    }catch (Exception e){
        e.printStackTrace();
        logger.error("修改网单单价和金额失败");
    }
        json.put("flag",3);
        return json.toString();
    }

    /**
     * 导出专用网单具体数据，实现回调函数
     *
     * @param sheet
     * @param temp
     * @param list
     * @throws Exception
     */
    private void setExcelBodyTotalForInvoiceOrderProductsList(WritableSheet sheet, int temp,List<Map<String, Object>> list) throws Exception {
        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat textFormat = new WritableCellFormat(font);
        textFormat.setAlignment(Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        DisplayFormat displayFormat = NumberFormats.INTEGER;
        WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
        numberFormat.setAlignment(jxl.format.Alignment.RIGHT);
        numberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        int index = 0;

        for (Map<String, Object> map : list) {
            index++;
            //序号
            sheet.setColumnView(0, 10);
            sheet.addCell(new Label(0, temp, CommUtil.getStringValue(index), textFormat));
            //网单编号
            sheet.setColumnView(1, 25);
            sheet.addCell(new Label(1, temp, CommUtil.getStringValue(map.get("cOrderSn")), textFormat));
            //订单编号
            sheet.setColumnView(2, 30);
            sheet.addCell(new Label(2, temp, CommUtil.getStringValue(map.get("orderSn")), textFormat));
            //网单状态
            String status = CommUtil.getStringValue(map.get("status"));
            String orderProdcutStatus = "";
            orderProdcutStatus = OrderProductStatus.getByCode(Integer.parseInt(status)).getName();
            /*if (status.equals("0")){
                status= "处理中";
            }
            if (status.equals("1")){
                status= "已占用库存";
            }
            if (status.equals("2")) {
                status= "同步到HP";
            }
            if (status.equals("3")) {
                status="同步到EC";
            }
            if (status.equals("4")) {
                status= "分配网点";
            }
            if (status.equals("8")) {
                status= "待出库";
            }
            if (status.equals("10")) {
                status= "待审核";
            }
            if (status.equals("11")) {
                status= "待转运入库";
            }
            if (status.equals("12")) {
                status= "待转运出库";
            }
            if (status.equals("40")) {
                status= "待发货";
            }
            if (status.equals("150")) {

                status= "网点拒绝";
            }
            if (status.equals("70")) {
                status= "待交付";
            }
            if (status.equals("140")) {
                status= "用户签收";
            }
            if (status.equals("130")) {
                status= "完成关闭";
            }
            if (status.equals("160")) {
                status= "用户拒收";
            }
            if (status.equals("110")) {
                status= "取消关闭";
            }*/
            sheet.setColumnView(3, 20);
            sheet.addCell(new Label(3, temp, orderProdcutStatus, textFormat));
            //订单状态
            String orderStatus = CommUtil.getStringValue(map.get("orderStatus"));
            if (orderStatus.equals("200")) {
                orderStatus = "未确认";
            } else if (orderStatus.equals("201")){
                orderStatus = "已确认";
            }else if (orderStatus.equals("202")){
                orderStatus = "已取消";
            }else if (orderStatus.equals("203")){
                orderStatus = "已完成";
            }
            sheet.setColumnView(4, 20);
            sheet.addCell(new Label(4, temp, orderStatus, textFormat));
            //订单支付状态
            String cPaymentStatus = CommUtil.getStringValue(map.get("cPaymentStatus"));
            if (cPaymentStatus.equals("200")) {
                cPaymentStatus = "未付款";
            } else if (cPaymentStatus.equals("201")){
                cPaymentStatus = "已付款";
            }else if (cPaymentStatus.equals("206")){
                cPaymentStatus = "待退款";
            }else if (cPaymentStatus.equals("207")){
                cPaymentStatus = "已退款";
            }else if (cPaymentStatus.equals("0")){
                cPaymentStatus = "";
            }
            sheet.setColumnView(5, 15);
            sheet.addCell(new Label(5, temp, cPaymentStatus, textFormat));
            //订单支付状态
            String isMakeReceipt = CommUtil.getStringValue(map.get("isMakeReceipt"));
            if (isMakeReceipt.equals("1")) {
                isMakeReceipt = "未开票";
            } else if (isMakeReceipt.equals("9")){
                isMakeReceipt = "待开票";
            }else if (isMakeReceipt.equals("5")){
                isMakeReceipt = "开票中";
            }else if (isMakeReceipt.equals("6")){
                isMakeReceipt = "开票失败";
            }else if (isMakeReceipt.equals("2")){
                isMakeReceipt = "已开票";
            }else if (isMakeReceipt.equals("3")){
                isMakeReceipt = "作废发票";
            }else if (isMakeReceipt.equals("4")){
                isMakeReceipt = "冲红发票";
            }else if (isMakeReceipt.equals("10")){
                isMakeReceipt = "取消开票";
            }else if (isMakeReceipt.equals("20")){
                isMakeReceipt = "期初数据封存";
            }
            sheet.setColumnView(6, 15);
            sheet.addCell(new Label(6, temp, isMakeReceipt, textFormat));
            //商品名称
            sheet.setColumnView(7, 30);
            sheet.addCell(new Label(7, temp, CommUtil.getStringValue(map.get("productName")), textFormat));
            //价格
            sheet.setColumnView(8, 15);
            sheet.addCell(new Label(8, temp, CommUtil.getStringValue(map.get("price")), textFormat));
            //数量
            sheet.setColumnView(9, 15);
            sheet.addCell(new Label(9, temp, CommUtil.getStringValue(map.get("number")), textFormat));
            //金额
            sheet.setColumnView(10, 15);
            sheet.addCell(new Label(10, temp, CommUtil.getStringValue(map.get("money")), textFormat));
            //库位
            sheet.setColumnView(11, 15);
            sheet.addCell(new Label(11, temp,  CommUtil.getStringValue(map.get("sCode")), textFormat));
            //下单时间
            sheet.setColumnView(12, 25);
            sheet.addCell(new Label(12, temp, CommUtil.getStringValue(map.get("addTimeMin")), textFormat));
            //备注
            sheet.setColumnView(13, 20);
            sheet.addCell(new Label(13, temp,CommUtil.getStringValue(map.get("remark")) , textFormat));
            //首次确认时间
            sheet.setColumnView(14, 25);
            sheet.addCell(new Label(14, temp, CommUtil.getStringValue(map.get("firstConfirmTime")), textFormat));
            //已确认次数
            sheet.setColumnView(15, 20);
            sheet.addCell(new Label(15, temp,CommUtil.getStringValue(map.get("autoConfirmNum")), textFormat));
            //转人工时间
            sheet.setColumnView(16, 20);
            sheet.addCell(new Label(16, temp, CommUtil.getStringValue(map.get("smManualTime")), textFormat));
            temp++;
        }

    }
}
