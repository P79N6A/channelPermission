package com.haier.svc.api.controller.stock;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.ConvertUtil;
import com.haier.common.util.DateUtil;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.Products;
import com.haier.shop.model.ProductsNew;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvStockInOut;
import com.haier.svc.api.controller.stock.mode.StockModel;
import com.haier.svc.api.controller.util.ExcelReader;
import com.haier.svc.api.controller.util.HttpJsonResult;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Controller
@RequestMapping(value = { "/upload" })
public class UploadStockInOutsController {
    private static org.apache.log4j.Logger log = LogManager.getLogger(UploadStockInOutsController.class);
    @Resource
    private StockModel stockModel;
    //点击导航条返回的页面(首页)
    @RequestMapping(value = { "/uploadInOuts" }, method = { RequestMethod.GET })
    String upload() {
        return "stock/uploadSkuInOut";
    }
    @RequestMapping(value = { "/downloadInOutTemplate" })
    void downloadTemplate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        OutputStream os = response.getOutputStream();
        try {
            response.reset();
            response.setHeader("Content-Disposition",
                    "attachment; filename=stock_in_out_template.xls");
            response.setContentType("application/octet-stream; charset=utf-8");
            String path = request.getSession().getServletContext()
                    .getRealPath("/xls/stock_in_out_template.xls");
            File file = ResourceUtils.getFile("classpath:xls/stock_in_out_template.xls");
            os.write(FileUtils.readFileToByteArray(file));
            os.flush();
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }
    private static final String checkStr = "物料编号,库位,渠道（商城:SC；大客户:DKH；天猫：TB；京东：JD；易迅：YX）,数量,入库时间,入库单号,类型（ZBCR  采购入库订单,ZGI6  调拨出库订单, ZGR6  调拨入库订单）";
    @RequestMapping(value = { "/doUpload" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<List<Map<String, Object>>> upload1(HttpServletRequest request,
                                                            HttpServletResponse response
                                                            ) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        HttpJsonResult<List<Map<String, Object>>> result = new HttpJsonResult<List<Map<String, Object>>>();
        InputStream is = file.getInputStream();
        String fileName = file.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        String fileExtName = fileName.substring(index + 1);
        if (!fileExtName.equalsIgnoreCase("xls")) {
            result.setMessage("选择导入文件扩展名必须为xls!");
            return result;
        }

        try {
            // 解析Excle文件
            List<String[]> excelList = ExcelReader.readExcel(is, 1);
            if (excelList.size() <= 1) {
                result.setMessage("很抱歉！你导入的Excel没有数据记录，请查看重新整理导入！");
                return result;
            }
            // 验证模板表头信息
            String[] firstLineData = excelList.get(0);
            boolean flag = this.checkDataTemplate(firstLineData, checkStr);
            if (!flag) {
                result.setMessage("很抱歉！你导入的Excel数据格式与系统模板格式存在差异，请下载模板后重新导入！");
                return result;
            }
            // 组装成List<Map<String, Object>>
            List<Map<String, Object>> infros = new ArrayList<Map<String, Object>>();
            if( null != excelList && !excelList.isEmpty()){
                // 读取数据
                for (int i = 1; i < excelList.size(); i++) {
                    Map<String, Object> rowInfo = new HashMap<String, Object>();
                    String[] str = (String[]) excelList.get(i);
                    String pl = StringUtil.nullSafeString(str[0]).trim(); // 品类
                    if (StringUtil.isEmpty(pl, true)) {
                        result.setMessage("很抱歉！你导入的Excel数据中第" + i + "行, 品类不能为空! 请核查后重新导入！");
                        return result;
                    }
                    String xh = StringUtil.nullSafeString(str[1]).trim(); // 型号
                    if (StringUtil.isEmpty(xh, true)) {
                        result.setMessage("很抱歉！你导入的Excel数据中第" + i + "行, 型号不能为空! 请核查后重新导入！");
                        return result;
                    }
                    String sku = StringUtil.nullSafeString(str[2]).trim(); // 物料编码
                    if (StringUtil.isEmpty(sku, true)) {
                        result.setMessage("很抱歉！你导入的Excel数据中第" + i + "行, 物料编码不能为空! 请核查后重新导入！");
                        return result;
                    }
                    String hdjg = StringUtil.nullSafeString(str[3]).trim(); // 活动价格
                    if (StringUtil.isEmpty(hdjg, true)) {
                        result.setMessage("很抱歉！你导入的Excel数据中第" + i + "行, 活动价格不能为空! 请核查后重新导入！");
                        return result;
                    }
                    BigDecimal price = ConvertUtil.toDecimal(hdjg, new BigDecimal(0));

                    String kc = StringUtil.nullSafeString(str[4]).trim(); // 库存
                    if (StringUtil.isEmpty(kc, true)) {
                        result.setMessage("很抱歉！你导入的Excel数据中第" + i + "行, 库存不能为空! 请核查后重新导入！");
                        return result;
                    }
                    String yjxl = StringUtil.nullSafeString(str[5]).trim(); // 预计销量
                    if (StringUtil.isEmpty(yjxl, true)) {
                        result.setMessage("很抱歉！你导入的Excel数据中第" + i + "行, 预计销量不能为空! 请核查后重新导入！");
                        return result;
                    }
                    String url = StringUtil.nullSafeString(str[6]).trim(); // 活动地址URL
                    if (StringUtil.isEmpty(url, true)) {
                        result.setMessage("很抱歉！你导入的Excel数据中第" + i + "行, 活动地址URL不能为空! 请核查后重新导入！");
                        return result;
                    }

                    rowInfo.put("pl", pl);
                    rowInfo.put("xh", xh);
                    rowInfo.put("sku", sku);
                    rowInfo.put("hdjg", price);
                    rowInfo.put("kc", kc);
                    rowInfo.put("yjxl", yjxl);
                    rowInfo.put("url", url);

                    infros.add(rowInfo);
                }
            }

            result.setData(infros);
        } catch (BiffException e) {
            result.setMessage("无法解析Excel文件！");
        } catch (BusinessException bex) {
            log.error(bex.getMessage());
            result.setMessage(bex.getMessage());
        } catch (Exception ex) {
            StringBuffer sbStr = new StringBuffer();
            StackTraceElement[]  item = ex.getStackTrace();
            for(StackTraceElement se : item){
                sbStr.append(se.toString()).append("\n");
            }
            result.setMessage("处理失败，请稍后重试，如果仍无法处理请联系管理员");
        } finally {
            if (null != is)
                try {
                    is.close();
                } catch (IOException e) {
                }
        }

        return result;
    }
    private boolean checkDataTemplate(String[] firstLineData, String checkStr) {
        boolean flag = false;
        StringBuffer sb = new StringBuffer();
        for (String str : firstLineData) {
            if (sb.length() > 0)
                sb.append(",");
            sb.append(str.trim());
        }
        if (sb.toString().equals(checkStr)){
            flag = true;
        }

        return flag;
    }
    @RequestMapping(value = { "/doUploadInOuts" }, method = { RequestMethod.POST })
    @ResponseBody
    HttpJsonResult<Map<String, Object>> upload(HttpServletRequest request,
                                               HttpServletResponse response,
                                              MultipartFile file) {
        Map<String, Object> params = new HashMap<String, Object>();
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        Workbook workbook = null;
        InputStream is = null;
        try {
            is = file.getInputStream();
            workbook = Workbook.getWorkbook(is);
            List<InvStockInOut> invStockInOuts = parseWorkbook(workbook);

            ServiceResult<Integer> rs = stockModel.doInventoryFromUpload(invStockInOuts);
            if (!rs.getSuccess()) {
                result.setMessage("保存出入库记录时，库存服务返回错误！");
                log.error("保存出入库记录时，库存服务返回错误：" + rs.getMessage());
            } else {
                params.put("total", invStockInOuts.size());//总计
                params.put("success", rs.getResult());
                params.put("ignore", invStockInOuts.size() - rs.getResult());
            }
        } catch (BiffException e) {
            result.setMessage("无法解析Excel文件！");
        } catch (BusinessException e) {
            log.error(e.getMessage());
            result.setMessage(e.getMessage());
        } catch (Exception e) {
            log.error("处理上传的出入库记录文件" + file.getOriginalFilename() + "失败：", e);
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
        return result;
    }

    private List<InvStockInOut> parseWorkbook(Workbook workbook) {
        List<InvStockInOut> stockInOuts = new ArrayList<InvStockInOut>();
        Sheet sheet = workbook.getSheet(0);//这里只取得第一个sheet的值，默认从0开始
        // 开始循环，取得 cell 里的内容，这里都是按String来取的 为了省事，具体你自己可以按实际类型来取。或者都按
        // String来取。然后根据你需要强制转换一下。
        String errMsg = "解析Excel数据失败：";
        List<InvStockChannel> channels = stockModel.getChannels();
        List<String> channelCodes = new ArrayList<String>();
        channelCodes.add("SHH");
        Map<String, ProductsNew> products = new HashMap<String, ProductsNew>();
        for (InvStockChannel channel : channels)
            channelCodes.add(channel.getChannelCode());
        List<String> sections = new ArrayList<String>();
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

            if (sheet.getRow(i).length != 7)
                throw new BusinessException(errMsg + "第" + (i + 1) + "行数据错误，列数不对");

            String sku = sheet.getCell(0, i).getContents().trim().toUpperCase();
            ProductsNew product = products.get(sku);
            if (product == null) {
                product = stockModel.getProdutsBySku(sku);
                if (product == null) {
                    product = new ProductsNew();
                    product.setId(0);
                }
                products.put(sku, product);
            }

            String sCode = sheet.getCell(1, i).getContents().trim().toUpperCase();
            if (StringUtil.isEmpty(sCode))
                throw new BusinessException(errMsg + "第" + (i + 1) + "行数据错误,库位不能为空");
            if (!sections.contains(sCode)) {
                InvSection section = stockModel.getSectionByCode(sCode);
                if (section != null && section.isChannelWA())
                    sections.add(sCode);
                else
                    throw new BusinessException(errMsg + "第" + (i + 1) + "行数据错误，无法识别的库位");
            }

            String channel_code = sheet.getCell(2, i).getContents().trim().toUpperCase();
            if (!channelCodes.contains(channel_code))
                throw new BusinessException(errMsg + "第" + (i + 1) + "行数据错误，渠道编码不存在");

            Integer quantity;
            try {
                quantity = Integer.parseInt(sheet.getCell(3, i).getContents());
            } catch (NumberFormatException e) {
                throw new BusinessException(errMsg + "第" + (i + 1) + "行数据错误，出入库数量不正确");
            }
            if (quantity < 0)
                throw new BusinessException(errMsg + "第" + (i + 1) + "行数据错误，出入库数量不能为负数");

            Cell cell = sheet.getCell(4, i);
            Date stockDate;
            if (cell.getType() == CellType.DATE) {
                stockDate = ((DateCell) cell).getDate();
                TimeZone zone = TimeZone.getTimeZone("GMT");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf.setTimeZone(zone);
                String cellDate = sdf.format(stockDate);
                stockDate = DateUtil.parse(cellDate, "yyyy-MM-dd HH:mm:ss");
            } else {
                String cellDate = sheet.getCell(4, i).getContents();
                if (cellDate != null && cellDate.indexOf("/") > 0) {
                    cellDate = cellDate.replace("/", "-");
                }
                stockDate = DateUtil
                        .parse(sheet.getCell(4, i).getContents(), "yyyy-MM-DD HH:mm:ss");
            }
            if (stockDate == null)
                throw new BusinessException(errMsg + "第" + (i + 1) + "行数据错误，入库日期不正确");
            //            else if (DateUtil.add(stockDate, Calendar.DAY_OF_YEAR, 30)
            //                .before(DateUtil.currentDate()))
            //                throw new BusinessException(errMsg + "第" + (i + 1) + "行数据错误，入库日期不能早于7天");

            String billNo = sheet.getCell(5, i).getContents();
            if (StringUtil.isEmpty(billNo))
                throw new BusinessException(errMsg + "第" + (i + 1) + "行数据错误，入库单号不能为空");

            String billType = sheet.getCell(6, i).getContents();
            billType = billType.trim().toUpperCase();
            if (!(InvStockInOut.IN_PURCHASE_TYPE.equals(billType)
                    || InvStockInOut.IN_TRANSFER_TYPE.equals(billType) || (InvStockInOut.OUT_TRANSFER_TYPE
                    .equals(billType)))) {
                throw new BusinessException(errMsg + "第" + (i + 1) + "行数据错误，不正确的类型");
            }
            billNo = billType + billNo;
            InvStockInOut invStockInOut = new InvStockInOut();
            invStockInOut.setBillNo(billNo);
            invStockInOut.setBillTime(stockDate);
            invStockInOut.setChannelCode(channel_code);
            invStockInOut.setCreateTime(new Date());
            invStockInOut.setItemId(product.getId());
            invStockInOut.setQuantity(quantity);
            invStockInOut.setSecCode(sCode);
            invStockInOut.setSku(sku);
            invStockInOut.setType(billType);
            invStockInOut.setAgeType(InvStockInOut.AGE_TYPE_SAMPLE);
            invStockInOut.setMark(billType.equals(InvStockInOut.OUT_TRANSFER_TYPE) ? "H" : "S");
            invStockInOut.setAgeStatus(1);
            //使用锁定库存，会刷新共享库存数量
            invStockInOut.setVirtualSecCode(sCode.substring(0, 2)
                    + ("DKH".equals(channel_code) ? "DK" : channel_code));
            stockInOuts.add(invStockInOut);
        }
        return stockInOuts;

    }
}
