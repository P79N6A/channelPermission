package com.haier.svc.api.controller.pop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.haier.distribute.service.DistributeCenterPopOrderService;
import org.apache.log4j.LogManager;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.jsondoc.core.annotation.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.distribute.data.model.PopOrderProducts;
import com.haier.distribute.data.model.PopOrders;
import com.haier.distribute.data.model.TChannelsInfo;
import com.haier.svc.api.controller.util.CommUtil;
import com.haier.svc.api.controller.util.ConstantsType;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import com.haier.svc.api.controller.util.PopExportData;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/6 0006
 * \* Time: 15:49
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Controller
@Api(name = "分销订单", description = "DistributeController")
@RequestMapping(value = "pop/")
public class DistributeController {

    @Autowired
    DistributeCenterPopOrderService distributeCenterPopOrderService;

    private final static org.apache.log4j.Logger logger = LogManager
            .getLogger(DistributeController.class);

    /**
     * 时间格式转换
     *
     * @param seconds
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 跳转订单列表页
     *
     * @return
     */
    @RequestMapping(value = {"orderList"}, method = {RequestMethod.GET})
    String orderListIndex() {
        logger.debug("跳转订单列表页");
        return "pop/distribute/orderList";
    }

    /**
     * 订单列表一览
     *
     * @param page
     * @param rows
     * @param condition
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/findDistributeOrderList", method = RequestMethod.POST)
    public JSONObject findDistributeOrderList(int page, int rows, PopOrders condition) {
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);
        if (StringUtils.isNotEmpty(condition.getAddTimeStart())) {
            Long date = convertStringDateToInteger(condition.getAddTimeStart() + " 00:00:00");
            if (null != date) {
                condition.setAddTimeStartS(date);
            }
        }
        if (StringUtils.isNotEmpty(condition.getAddTimeEnd())) {
            Long date = convertStringDateToInteger(condition.getAddTimeEnd() + " 23:59:59");
            if (null != date) {
                condition.setAddTimeEndS(date);
            }
        }
        return distributeCenterPopOrderService.productList(pager, condition);
    }

    private Long convertStringDateToInteger(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = formatter.parse(strDate);
            return date.getTime() / 1000;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/getSource")
    @ResponseBody
    public JSONArray getSource() {
        return distributeCenterPopOrderService.getSource();
    }

    @RequestMapping("/getOrderStatus")
    @ResponseBody
    public JSONArray getOrderStatus() {
        return distributeCenterPopOrderService.getSysDictionaryByType(ConstantsType.SYS_TYPE.POP_ORDER_TYPE.toString());
    }

    @RequestMapping("/getOrderPayStatus")
    @ResponseBody
    public JSONArray getOrderPayStatus() {
        return distributeCenterPopOrderService.getSysDictionaryByType(ConstantsType.SYS_TYPE.POP_ORDER_PAY_TYPE.toString());
    }

    @RequestMapping("/getInvoiceType")
    @ResponseBody
    public JSONArray getInvoiceType() {
        return distributeCenterPopOrderService.getSysDictionaryByType(ConstantsType.SYS_TYPE.INVOICE_TYPE.toString());
    }

    /**
     * 跳转订单详细页
     *
     * @param orderSn
     * @param model
     * @param session
     * @return
     * @throws ParseException
     */
    @RequestMapping("/orderDetail")
    public String showOrderDetail(String orderSn, String where, PagerInfo page, Model model, HttpSession session) throws ParseException {
        PopOrders condition = new PopOrders();
        condition.setOrderSn(orderSn);
        PopOrders od = this.distributeCenterPopOrderService.getOneByCondition(condition);
        od.setSource(od.getChannelName());
        if (0 != od.getFirstConfirmTime()) {
            od.setFirstTureTime(CommUtil.convertLongDateToString(new Long(od.getFirstConfirmTime()) * 1000L));
        }
        od.setReceiptInfo(od.getReceiptInfo());
        session.setAttribute("orderId", od.getId());
        session.setAttribute("orderSn", orderSn);
        String payTime = od.getPayTime();
        String addTime = Integer.toString(od.getAddTime());
        od.setAddTimeStart(timeStamp2Date(addTime, null));
        od.setPayTime(timeStamp2Date(payTime, null));
        model.addAttribute("order", od);
        model.addAttribute("orderSn", orderSn);
        model.addAttribute("page", page);
        return "pop/distribute/orderDetail";

    }

    /**
     * 当前订单下的商品列表
     *
     * @param session
     * @return
     */
    @RequestMapping("/findCommodityList")
    @ResponseBody
    public JSONObject findCommodityList(HttpSession session) {
        Long orderId = (Long) session.getAttribute("orderId");
        return distributeCenterPopOrderService.commodityList(orderId);

    }

    /**
     * 订单操作日志列表
     *
     * @param session
     * @return
     */
    @RequestMapping("/findOperateHistory")
    @ResponseBody
    public JSONObject findOperateHistory(HttpSession session) {
        String orderSn = (String) session.getAttribute("orderSn");
        return distributeCenterPopOrderService.historyList(orderSn);

    }

    /**
     * 修改订单状态
     *
     * @param orderId
     * @param but
     * @param model
     * @param session
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/orderOperation", method = RequestMethod.GET)
    @ResponseBody
    public String orderOpertion(String orderId, String but, String oId, BigDecimal price, String expressNo, String thisStatus,
                                Model model, HttpSession session) throws ParseException {
        if (("sure").equals(but)) {
            but = "201";
            return this.distributeCenterPopOrderService.orderOpertion(but, orderId, oId, price, expressNo, thisStatus);
        } else {
            if (("finish").equals(but)) {
                but = "203";
            } else if (("cancel").equals(but)) {
                but = "202";
            } else if (("stockout").equals(but)) {
                but = "204";
            } else if (("send").equals(but)) {
                but = "205";
            }
            return this.distributeCenterPopOrderService.orderOpertion(but, orderId, oId, price, expressNo, thisStatus);
        }
    }

    /**
     * 订单取消状态操作
     *
     * @param orderId
     * @param but
     * @param model
     * @param session
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/cancelOpertion", method = RequestMethod.GET)
    public String cancelOpertion(String orderId, String but, BigDecimal price,
                                 Model model, HttpSession session) throws ParseException {
//    	String thisOrderId = (String) session.getAttribute("orderId");
        String thisOrderId = "";
        if (("sure").equals(but)) {
            but = "207";
        } else if (("refuse").equals(but)) {
            but = "208";
        }
        this.distributeCenterPopOrderService.cancelOpertion(but, orderId, price);
        return orderListIndex();
    }

    /**
     * 修改订单备注信息
     *
     * @param orderSn
     * @param codConfirmRemark
     * @return
     */
    @RequestMapping(value = "/updateRemark", method = RequestMethod.POST)
    @ResponseBody
    public String updateRemark(String orderSn, String codConfirmRemark) {
        int flag = distributeCenterPopOrderService.editRemark(orderSn, codConfirmRemark);
        if (1 == flag) {
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 修改关联订单号
     *
     * @param orderSn
     * @param oid
     * @return
     */
    @RequestMapping(value = "/updateOid", method = RequestMethod.POST)
    @ResponseBody
    public String updateOid(String orderSn, String oid) {
        return distributeCenterPopOrderService.editOid(orderSn, oid);
    }

    /**
     * 修改物流编号
     *
     * @param orderSn
     * @param expressNo
     * @return
     */
    @RequestMapping(value = "/updateExpressNo", method = RequestMethod.POST)
    @ResponseBody
    public String updateExpressNo(String orderSn, String expressNo) {
        return distributeCenterPopOrderService.editExpressNo(orderSn, expressNo);
    }

    /**
     * 修改收货人信息
     *
     * @param orderSn
     * @param originRegionName
     * @param originAddress
     * @return
     */
    @RequestMapping(value = "/updateOrigin", method = RequestMethod.POST)
    @ResponseBody
    public String updateOrigin(String orderSn, String consignee, String mobile, String originRegionName, String originAddress) {
        int flag = distributeCenterPopOrderService.editOrigin(orderSn, consignee, mobile, originRegionName, originAddress);
        if (1 == flag) {
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 闸口开启时，将订单状态从已完成改为已取消
     *
     * @param orderSn
     * @return
     */
    @RequestMapping(value = "/finishToCancel", method = RequestMethod.POST)
    @ResponseBody
    public String finishToCancel(String orderSn) {
        int flag = distributeCenterPopOrderService.finishToCancel(orderSn);
        if (1 == flag) {
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 加载网单列表
     *
     * @return
     */
    @RequestMapping("/orderProductList")
    public String deliveryOrdeProducts() {

        return "pop/distribute/orderProductList";
    }

    /**
     * 网单列表页，以及查询
     *
     * @param page
     * @param rows
     * @param orderProducts
     * @return
     */
    @RequestMapping(value = "/findOrderProductList", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject findOrderProductList(int page, int rows, PopOrderProducts orderProducts) {
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);
        if (StringUtils.isNotEmpty(orderProducts.getcPayTime())) {
            Long date = convertStringDateToInteger(orderProducts.getcPayTime() + " 00:00:00");
            if (null != date) {
                orderProducts.setcPayTimeAdd(date);
            }
        }
        if (StringUtils.isNotEmpty(orderProducts.getPayTimeEnd())) {
            Long date = convertStringDateToInteger(orderProducts.getPayTimeEnd() + " 23:59:59");
            if (null != date) {
                orderProducts.setcPayTimeEnd(date);
            }
        }
        return distributeCenterPopOrderService.orderProductList(pager, orderProducts);
    }

    /**
     * 网单详细页面
     *
     * @param orderSn
     * @param model
     * @param session
     * @return
     * @throws ParseException
     */
    @RequestMapping("/orderProductsDetail")
    public String showOrderProductsDetail(String cOrderSn, String orderSn, Model model, HttpSession session) throws ParseException {
        PopOrders condition = new PopOrders();
        condition.setOrderSn(orderSn);
        PopOrders od = this.distributeCenterPopOrderService.getOneByCondition(condition);
        PopOrderProducts orderProducts = new PopOrderProducts();
        orderProducts.setcOrderSn(cOrderSn);
        PopOrderProducts op = distributeCenterPopOrderService.getOneByOrderProducts(orderProducts);
        if (0 != op.getShippingTime()) {
            op.setShipTime(CommUtil.convertLongDateToString(new Long(op.getShippingTime()) * 1000L));
        } else {
            op.setShipTime("");
        }
        session.setAttribute("orderId", od.getId());
        session.setAttribute("orderSn", orderSn);
        String payTime = od.getPayTime();
        String addTime = Integer.toString(od.getAddTime());
        od.setAddTimeStart(timeStamp2Date(addTime, null));
        od.setPayTime(timeStamp2Date(payTime, null));
        model.addAttribute("order", od);
        model.addAttribute("orderProduct", op);
        return "pop/distribute/orderProductDetail";
    }

    /**
     * 从网单列表中跳转订单详细页面
     *
     * @param orderSn
     * @param where
     * @param page
     * @param model
     * @param session
     * @return
     * @throws ParseException
     */
    @RequestMapping("/orderDetailFromP")
    public String orderDetailFromP(String orderSn, String where, PagerInfo page, Model model, HttpSession session) throws ParseException {
        PopOrders condition = new PopOrders();
        condition.setOrderSn(orderSn);
        PopOrders od = this.distributeCenterPopOrderService.getOneByCondition(condition);
        od.setSource(od.getChannelName());
        if (0 != od.getFirstConfirmTime()) {
            od.setFirstTureTime(CommUtil.convertLongDateToString(new Long(od.getFirstConfirmTime()) * 1000L));
        }
        od.setReceiptInfo(od.getReceiptInfo());
        session.setAttribute("orderId", od.getId());
        session.setAttribute("orderSn", orderSn);
        String payTime = od.getPayTime();
        String addTime = Integer.toString(od.getAddTime());
        od.setAddTimeStart(timeStamp2Date(addTime, null));
        od.setPayTime(timeStamp2Date(payTime, null));
        model.addAttribute("order", od);
        model.addAttribute("orderSn", orderSn);
        model.addAttribute("page", page);
        return "pop/distribute/orderDetailFromP";

    }

    /**
     * 跳转渠道列表
     *
     * @return
     */
    @RequestMapping("/channelList")
    public String deliverychannelList() {

        return "pop/distribute/channelList";
    }

    /**
     * 渠道列表
     *
     * @param page
     * @param rows
     * @param tChanneclsInfo
     * @return
     */
    @RequestMapping("/findChannelList")
    @ResponseBody
    public JSONObject findChannelList(int page, int rows, TChannelsInfo tChanneclsInfo) {
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);
        return distributeCenterPopOrderService.channelList(pager, tChanneclsInfo);
    }

    /**
     * 添加和修改分销渠道
     *
     * @param tChanneclsInfo
     * @return
     */
    @RequestMapping("/addChannel")
    @ResponseBody
    public String addChannel(TChannelsInfo tChanneclsInfo) {
        return distributeCenterPopOrderService.addChannel(tChanneclsInfo);
    }

    /**
     * 删除渠道信息
     */
    @RequestMapping(value = "/removeChannel", method = RequestMethod.POST)
    @ResponseBody
    public String removeChannel(Long id) {
        JSONObject json = new JSONObject();
        int flag = distributeCenterPopOrderService.removeChannel(id);
        if (1 == flag) {
            json.put("text", "success");
        } else {
            json.put("text", "fail");
        }
        return json.toString();
    }

    /**
     * 导出订单列表
     *
     * @param res
     * @throws IOException
     */
    @RequestMapping(value = "/exportOrderList", method = RequestMethod.GET)
    public void exportOrderList(String order_sn, String source_order_sn, String order_Status, String source_e,
                                String o_id, String express_no, String addTime_start, String addTime_end, HttpServletResponse res, String code) throws IOException {
        PopOrders condition = new PopOrders();
        if (StringUtils.isNotEmpty(addTime_start)) {
            Long date = convertStringDateToInteger(addTime_start + " 00:00:00");
            if (null != date) {
                condition.setAddTimeStartS(date);
            }
        }
        if (StringUtils.isNotEmpty(addTime_end)) {
            Long date = convertStringDateToInteger(addTime_end + " 23:59:59");
            if (null != date) {
                condition.setAddTimeEndS(date);
            }
        }
        condition.setOrderSn(order_sn);
        condition.setSourceOrderSn(source_order_sn);
        condition.setOrderStatus(order_Status);
        condition.setSource(source_e);
        condition.setOid(o_id);
        condition.setExpressNo(express_no);
        List<PopOrders> orderList = null;
        if ("yes".equals(code)) {
            orderList = distributeCenterPopOrderService.exportOrderList(condition);
        }

        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("订单列表");
        sheet.setColumnWidth(0, (int) (21.57 * 256));
        sheet.setColumnWidth(1, (int) 21.57 * 256);
        sheet.setColumnWidth(2, (int) 21.57 * 256);
        sheet.setColumnWidth(3, (int) 21.57 * 256);
        sheet.setColumnWidth(4, (int) 21.57 * 256);
        sheet.setColumnWidth(5, (int) 11.14 * 256);
        sheet.setColumnWidth(6, (int) 8.57 * 256);
        sheet.setColumnWidth(7, (int) 21.57 * 256);
        sheet.setColumnWidth(8, (int) 21.57 * 256);
        sheet.setColumnWidth(9, (int) 14.43 * 256);
        sheet.setColumnWidth(10, (int) 14.43 * 256);

        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        // 4.创建单元格，设置值表头，设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        int length = PopExportData.orderListTitle.length;
        // 设置表头
        for (int i = 0; length - 1 >= i; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(PopExportData.orderListTitle[i]);
            cell.setCellStyle(style);
        }
        String orederStatus = null;
        String cancelStatus = null;
        //向单元格里添加数据
        if (orderList != null) {

            for (short i = 0; i < orderList.size(); i++) {
                if ("200".equals(orderList.get(i).getOrderStatus()) || "0".equals(orderList.get(i).getOrderStatus())) {
                    orederStatus = "未确认";
                } else if ("204".equals(orderList.get(i).getOrderStatus())) {
                    orederStatus = "缺货";
                } else if ("201".equals(orderList.get(i).getOrderStatus())) {
                    orederStatus = "已确认";
                } else if ("203".equals(orderList.get(i).getOrderStatus())) {
                    orederStatus = "已完成";
                } else if ("202".equals(orderList.get(i).getOrderStatus())) {
                    orederStatus = "已取消";
                } else if ("205".equals(orderList.get(i).getOrderStatus())) {
                    orederStatus = "配送中";
                }

                if (orderList.get(i).getCancelStatus() == 206) {
                    cancelStatus = "订单取消申请中";
                } else if (orderList.get(i).getCancelStatus() == 207) {
                    cancelStatus = "订单取消成功";
                } else if (orderList.get(i).getCancelStatus() == 208) {
                    cancelStatus = "订单取消失败";
                } else {
                    cancelStatus = "";
                }

                orderList.get(i).setAddTimeStart(CommUtil.convertLongDateToString(orderList.get(i).getAddTime() * 1000L));
                orderList.get(i).setFirstTureTime(CommUtil.convertLongDateToString(orderList.get(i).getFirstConfirmTime() * 1000L));
                orderList.get(i).setSureTime(CommUtil.convertLongDateToString(orderList.get(i).getConfirmTime() * 1000L));
                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(orderList.get(i).getOrderSn());
                row.createCell(1).setCellValue(orderList.get(i).getChannelName());
                row.createCell(2).setCellValue(orderList.get(i).getSourceOrderSn());
                row.createCell(3).setCellValue(orderList.get(i).getAddTimeStart());
                row.createCell(4).setCellValue(orderList.get(i).getFirstTureTime());
                row.createCell(5).setCellValue(orderList.get(i).getSureTime());
                row.createCell(6).setCellValue(orderList.get(i).getOrderAmount().doubleValue());
                row.createCell(7).setCellValue(orederStatus);
                row.createCell(8).setCellValue(orderList.get(i).getOid());
                row.createCell(9).setCellValue(orderList.get(i).getExpressNo());
                row.createCell(10).setCellValue(cancelStatus);
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date = new java.util.Date();
        String str = sdf.format(date);
        String fileName = "订单列表" + str;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        ExportExcelUtil.exportCommon(is, fileName, res);
    }

    /**
     * 导出网单
     *
     * @param res
     * @throws IOException
     */
    @RequestMapping(value = "/exportProductList", method = RequestMethod.GET)
    public void exportProductList(String c_order_sn, String order_sn, String source_order_sn, String product_name,
                                  String sku_p, String c_pay_time, String pay_time_end, String order_status, String code, HttpServletResponse res) throws IOException {
        PopOrderProducts orderProducts = new PopOrderProducts();
        if (StringUtils.isNotEmpty(c_pay_time)) {
            Long date = convertStringDateToInteger(c_pay_time + " 00:00:00");
            if (null != date) {
                orderProducts.setcPayTimeAdd(date);
            }
        }
        if (StringUtils.isNotEmpty(pay_time_end)) {
            Long date = convertStringDateToInteger(pay_time_end + " 23:59:59");
            if (null != date) {
                orderProducts.setcPayTimeEnd(date);
            }
        }
        orderProducts.setcOrderSn(c_order_sn);
        orderProducts.setOrderSn(order_sn);
        orderProducts.setSourceOrderSn(source_order_sn);
        orderProducts.setProductName(product_name);
        orderProducts.setSku(sku_p);
        orderProducts.setOrderStatus(order_status);
        List<PopOrderProducts> productList = null;
        if ("yes".equals(code)) {
            productList = distributeCenterPopOrderService.exportOrderProductsList(orderProducts);
        }

        // 1.创建一个workbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        HSSFSheet sheet = wb.createSheet("网单列表");
        sheet.setColumnWidth(0, (int) (21.57 * 256));
        sheet.setColumnWidth(1, (int) 21.57 * 256);
        sheet.setColumnWidth(2, (int) 13.43 * 256);
        sheet.setColumnWidth(3, (int) 21.57 * 256);
        sheet.setColumnWidth(4, (int) 21.57 * 256);
        sheet.setColumnWidth(5, (int) 11.14 * 256);
        sheet.setColumnWidth(6, (int) 8.57 * 256);
        sheet.setColumnWidth(7, (int) 8.57 * 256);
        sheet.setColumnWidth(8, (int) 21.57 * 256);
        sheet.setColumnWidth(9, (int) 21.57 * 256);
        sheet.setColumnWidth(10, (int) 21.57 * 256);
        sheet.setColumnWidth(11, (int) 21.57 * 256);
        sheet.setColumnWidth(12, (int) 21.57 * 256);
        sheet.setColumnWidth(13, (int) 21.57 * 256);
        sheet.setColumnWidth(14, (int) 21.57 * 256);
        sheet.setColumnWidth(15, (int) 21.57 * 256);

        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        // 4.创建单元格，设置值表头，设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        // 居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        int length = PopExportData.productListTitle.length;
        // 设置表头
        for (int i = 0; length - 1 >= i; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(PopExportData.productListTitle[i]);
            cell.setCellStyle(style);
        }
        String orederStatus = null;
        //向单元格里添加数据
        if (productList != null) {
            for (short i = 0; i < productList.size(); i++) {
                String status = productList.get(i).getOrderStatus();
                if ("200".equals(productList.get(i).getOrderStatus()) || "0".equals(productList.get(i).getOrderStatus())) {
                    orederStatus = "未确认";
                } else if ("204".equals(productList.get(i).getOrderStatus())) {
                    orederStatus = "缺货";
                } else if ("201".equals(productList.get(i).getOrderStatus())) {
                    orederStatus = "已确认";
                } else if ("203".equals(productList.get(i).getOrderStatus())) {
                    orederStatus = "已完成";
                } else if ("202".equals(productList.get(i).getOrderStatus())) {
                    orederStatus = "已取消";
                } else if ("205".equals(productList.get(i).getOrderStatus())) {
                    orederStatus = "配送中";
                }
                row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(productList.get(i).getcOrderSn());
                row.createCell(1).setCellValue(productList.get(i).getOrderSn());
                row.createCell(2).setCellValue(productList.get(i).getProductName());
                row.createCell(3).setCellValue(productList.get(i).getSku());
                row.createCell(4).setCellValue(productList.get(i).getTypeName());
                row.createCell(5).setCellValue(productList.get(i).getPrice().doubleValue());
                row.createCell(6).setCellValue(productList.get(i).getNumber());
                row.createCell(7).setCellValue((productList.get(i).getPrice().multiply(new BigDecimal(productList.get(i).getNumber()))).doubleValue());
                row.createCell(8).setCellValue(productList.get(i).getChannelName());
                row.createCell(9).setCellValue(productList.get(i).getSourceOrderSn());
                row.createCell(10).setCellValue(productList.get(i).getcPayTime());
                row.createCell(11).setCellValue(orederStatus);
                row.createCell(12).setCellValue(productList.get(i).getConsignee());
                row.createCell(13).setCellValue(productList.get(i).getMobile());
                row.createCell(14).setCellValue(productList.get(i).getOriginRegionName());
                row.createCell(15).setCellValue(productList.get(i).getOriginAddress());
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date = new java.util.Date();
        String str = sdf.format(date);
        String fileName = "网单列表" + str;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        ExportExcelUtil.exportCommon(is, fileName, res);
    }

}