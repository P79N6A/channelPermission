package com.haier.svc.api.controller.invoice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haier.common.BusinessException;
import com.haier.common.util.JsonUtil;
import com.haier.shop.model.MemberInvoices;
import com.haier.shop.model.OrderOperateLogs;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.Ustring;
import com.haier.shop.service.InvoicesService;
import com.haier.shop.service.MemberInvoicesService;
import com.haier.shop.service.ShopOrderOperateLogsService;
import com.haier.svc.api.controller.util.HttpJsonResult;

import com.haier.svc.api.controller.util.StringUtil;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "invoice/")
public class memberInvoicesUpdateImportController {

    private static final Logger logger = LogManager.getLogger(memberInvoicesUpdateImportController.class);

    @Autowired
    private InvoicesService invoicesService;
    @Autowired
    private MemberInvoicesService memberInvoicesService;

    @Autowired
    private ShopOrderOperateLogsService shopOrderOperateLogsService;

    /**
     * 导入页面
     */
    @RequestMapping(value = { "memberInvoicesUpdateImport" })
    public String loadMemberInvoicesUpdateImport() {
        return "invoice/memberInvoicesUpdateImport";
    }

    /**
     * 读取EXCEL表格，校验数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "/checkMemberInvoicesUpdate" }, method = {RequestMethod.POST})
    @ResponseBody
    public HttpJsonResult<String> checkMemberInvoicesUpdate(HttpServletRequest request,
                                                            HttpServletResponse response) {
        HttpJsonResult<String> result = new HttpJsonResult<String>();
        MemberInvoices memberInvoices = null;
        List<MemberInvoices> list = new ArrayList<MemberInvoices>();
        Workbook workbook = null;
        InputStream is = null;
        Sheet sheet = null;
        MultipartFile file = null;
        try {
            // 转型为MultipartHttpRequest
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            file = multipartRequest.getFile("file");
            if (file == null) {
                result.setMessage("没有选择导入文件，请选择导入文件后再点击导入操作！");
                return result;
            }
            String fileName = file.getOriginalFilename();
            int index = fileName.lastIndexOf(".");
            String fileExtName = fileName.substring(index + 1);
            if (!fileExtName.equalsIgnoreCase("xls") && !fileExtName.equalsIgnoreCase("xlsx")) {
                result.setMessage("选择导入文件扩展名必须为xls或xlsx格式");
                return result;
            }
            is = file.getInputStream();
            if (fileExtName.equalsIgnoreCase("xls")) {
                workbook = new HSSFWorkbook(is);

            } else if (fileExtName.equalsIgnoreCase("xlsx")) {
                workbook = new XSSFWorkbook(is);
            }
            sheet = workbook.getSheetAt(0);
            int rowNum = sheet.getLastRowNum() + 1;
            if (rowNum <= 1) {
                result.setMessage("很抱歉！你导入的Excel没有数据记录，请查看重新整理导入！");
                return result;
            }

            if (rowNum > 201) {
                result.setMessage("很抱歉！每次只能修改200条记录，请查看重新整理导入！");
                return result;
            }

            for (int i = 1; i < rowNum; i++) {
                int j = 0;
                Row row = sheet.getRow(i);

                String cOrderSn = getRowCell(row, j++);
                if (StringUtil.isEmpty(cOrderSn, true)) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[网单号]不能为空! 请核查后重新导入！");
                    return result;
                }

                String invoiceTitle = getRowCell(row, j++);
                String taxPayerNumber = getRowCell(row, j++);
                if (StringUtil.isEmpty(invoiceTitle, true)
                        && StringUtil.isEmpty(taxPayerNumber, true)) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[网单号:" + cOrderSn + "][发票抬头、纳税人识别号]不能同时为空! 请核查后重新导入！");
                    return result;
                }

                OrderProductsNew op = invoicesService.getOrderProductsByCOrderSn(cOrderSn);
                if (op == null) {
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[网单号:" + cOrderSn+ "]查不到网单信息! 请核查后重新导入！");
                    return result;
                }

                if (op.getIsMakeReceipt() == 2){
                    result.setMessage("数据有误：Excel第" + (i + 1) + "行[网单号:" + op.getCOrderSn()+ "]的开票状态为已开票! 已开票不能修改！");
                    return result;
                }

                String address = getRowCell(row, j++);
                String tel = getRowCell(row, j++);
                String bank = getRowCell(row, j++);
                String banknum = getRowCell(row, j++);

                memberInvoices = new MemberInvoices();
                if (invoiceTitle == null){
                    invoiceTitle = "";
                }
                if (taxPayerNumber == null){
                    taxPayerNumber = "";
                }
                if (address == null){
                    address = "";
                }
                if (tel == null){
                    tel = "";
                }
                if (bank == null){
                    bank = "";
                }
                if (banknum == null){
                    banknum = "";
                }
                memberInvoices.setInvoiceTitle(invoiceTitle);//发票抬头
                memberInvoices.setTaxPayerNumber(taxPayerNumber);//纳税人识别号

                //根据订单id修改发票，只修改 普通发票或者电子发票
                memberInvoices.setOrderId(op.getOrderId());//订单id
                memberInvoices.setInvoiceType(2);//普通发票
                memberInvoices.setElectricFlag(1);//改电子发票

                memberInvoices.setRegisterAddress(address);//地址
                memberInvoices.setRegisterPhone(tel);//电话
                memberInvoices.setBankName(bank);//银行
                memberInvoices.setBankCardNumber(banknum);//银行卡号

                memberInvoices.setRemark(cOrderSn);//不修改，只备注信息，后续更新失败时会用

                list.add(memberInvoices);
                try {
                    //防止l值相同
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (BusinessException e) {
            result.setMessage("无法解析Excel文件！");
            return result;
        } catch (Exception e) {
            result.setMessage("处理失败，请稍后重试，如果仍无法处理请联系管理员");
            logger.error("处理上传的出入库记录文件" + file.getOriginalFilename() + "失败：", e);
            return result;
        } finally {
            if (workbook != null) {
                //workbook.close();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("关闭流失败：", e);
                }
            }
        }
        String json = JsonUtil.toJson(list);
        result.setData(json);
        result.setMessage("数据校验正常：");
        result.setTotalCount(1);
        return result;
    }

    /**
     * 获取excel某行的j列内容
     * @param row
     * @param j
     * @return
     */
    private String getRowCell(Row row, int j) {
        if (row.getCell(j) != null) {
            row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
            return StringUtil.nullSafeString(row.getCell(j).getStringCellValue()).trim();
        }
        return null;
    }

    /**
     * 导入EXCEL数据
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "/importMemberInvoicesUpdate" })
    @ResponseBody
    public HttpJsonResult<Boolean> importMemberInvoicesUpdate(HttpServletRequest request,
                                                              HttpServletResponse response) {
        HttpSession session = request.getSession();
        String userName = Ustring.getString(session.getAttribute("userName"));
        HttpJsonResult<Boolean> result = new HttpJsonResult<Boolean>();
        int totalCount = 0;
        StringBuilder content = new StringBuilder();

        try {
            String json = request.getParameter("json");
            Gson gson = new Gson();
            Type type = new TypeToken<List<MemberInvoices>>() {
            }.getType();
            List<MemberInvoices> lists = (List<MemberInvoices>) gson.fromJson(json, type);

            for (MemberInvoices memberInvoices : lists) {
                try {
                    MemberInvoices byOrderId = memberInvoicesService.getByOrderId(memberInvoices.getOrderId());
                    int t = memberInvoicesService.updateByTitleAndNumber(memberInvoices);
                    if (t > 0) {
                        totalCount++;
                        StringBuffer log = new StringBuffer("发票信息修改:");
                        OrderOperateLogs orderOperateLogs = new OrderOperateLogs();
                        OrderProductsNew byCOrderSn = invoicesService.getOrderProductsByCOrderSn(memberInvoices.getRemark());
                        orderOperateLogs.setSiteId(1);
                        orderOperateLogs.setOrderId(byCOrderSn.getOrderId());
                        orderOperateLogs.setOrderProductId(byCOrderSn.getId());
                        orderOperateLogs.setNetPointId(byCOrderSn.getNetPointId());
                        orderOperateLogs.setOperator(userName);
                            log.append("发票抬头由 "+byOrderId.getInvoiceTitle() + "改为 " + memberInvoices.getInvoiceTitle() + " ");
                            log.append("纳税人识别号由 "+byOrderId.getTaxPayerNumber() + "改为 " + memberInvoices.getTaxPayerNumber() + " ");
                            log.append("注册地址由 "+byOrderId.getRegisterAddress() + "改为 " + memberInvoices.getRegisterAddress() + " ");
                            log.append("注册电话由 "+byOrderId.getRegisterPhone() + "改为 " + memberInvoices.getRegisterPhone() + " ");
                            log.append("开户银行由 "+byOrderId.getBankName() + "改为 " + memberInvoices.getBankName() + " ");
                            log.append("开户卡号由 "+byOrderId.getBankCardNumber() + "改为 " + memberInvoices.getBankCardNumber() + " ");
                        orderOperateLogs.setChangeLog(log.toString());
                        orderOperateLogs.setRemark(log.toString());
                        orderOperateLogs.setLogTime((int) (new Date().getTime() / 1000));
                        orderOperateLogs.setStatus(byCOrderSn.getStatus());
                        orderOperateLogs.setPaymentStatus(byCOrderSn.getCPaymentStatus());
                        shopOrderOperateLogsService.insert(orderOperateLogs);
                    } else {
                        content.append("网单号【" + memberInvoices.getRemark() + "】修改失败;</br>");
                    }
                } catch (Exception e) {
                    logger.error("网单号【" + memberInvoices.getRemark() + "】修改异常:", e);
                    content.append("网单号【" + memberInvoices.getRemark() + "】修改异常;</br>");
                }

            }
        } catch (Exception e) {
            logger.error("[importMemberInvoicesUpdate]失败", e);
            result.setData(false);
            result.setMessage("处理失败，请稍后重试，如果仍无法处理请联系管理员");
            return result;
        }
        result.setData(true);
        result.setMessage("修改成功" + totalCount + "条！"
                + (!content.toString().equals("") ? "</br>" + content.toString() : ""));
        return result;
    }


    /**
     * 下载
     * @param request
     * @param response
     * @param userAgent
     * @throws IOException
     */
    @RequestMapping(value = { "/downloadMemberInvoicesUpdateTemplate" }, method = { RequestMethod.GET })
    public void downloadMemberInvoicesUpdateTemplate(HttpServletRequest request,
                                                     HttpServletResponse response,
                                                     @RequestHeader(value = "user-agent") String userAgent)
            throws IOException {
        OutputStream os = response.getOutputStream();
        try {

            String fileName = null;

            fileName = "会员发票信息修改模板.xlsx";

            System.out.println("======");
            response.reset();
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + this.formatFileName(fileName, userAgent));
            response.setContentType("application/octet-stream; charset=utf-8");

            File file = ResourceUtils.getFile("classpath:static/static/excel/memberinvoices_update_template.xlsx");

            os.write(FileUtils.readFileToByteArray(file));
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
}
