package com.haier.svc.api.controller.pop;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.afterSale.model.Json;
import com.haier.common.PagerInfo;
import com.haier.distribute.data.model.*;
import com.haier.distribute.service.DistributeCenterPopProductService;
import com.haier.order.model.Ustring;
import com.haier.svc.api.controller.util.ExportExcelUtil;
import com.haier.svc.api.controller.util.PopExportData;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.*;
import org.jsondoc.core.annotation.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@Api(name = "", description = "PopController")
@RequestMapping(value = "pop/product")
public class PopProductController {

    @Autowired
    private DistributeCenterPopProductService distributeCenterPopProductService;

    //菜单配置显示productList页面
    @RequestMapping("/productList")
    public String showProduct() {

        return "pop/product/productList";
    }

    //List页面跳转详细页
    @RequestMapping("/productListEdit")
    public String showProduct_bak(String gloid, Model mode) {
        mode.addAttribute("gloid", gloid);
        return "pop/product/productListEdit";
    }

    //主表根据条件查询
    @RequestMapping(value = "/productListid", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray productList(Product condition) {

        return distributeCenterPopProductService.productList(condition);
    }

    /**
     * 主表分页查询
     *
     * @param page
     * @param rows
     * @param condition
     * @return
     */
    @RequestMapping(value = "/productListF", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ProductList(int page, int rows, Product condition) {
        page = page == 0 ? 1 : page;
        PagerInfo pager = new PagerInfo(rows, page);
        return distributeCenterPopProductService.findProductList(pager, condition);
    }

    //查询子表
    @RequestMapping(value = "/productDetailList", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray productDetailList(int saleid) {
        return distributeCenterPopProductService.productDetailList(saleid);
    }

    //校验时间
    @RequestMapping(value = "/pricTime", method = RequestMethod.POST)
    @ResponseBody
    public int pricTime(int saleid, int regionId, String starttime, String endtime, int id) throws ParseException {
        return distributeCenterPopProductService.pricTime(saleid, regionId, starttime, endtime, id);
    }

    //根据区域查询子表数据
    @RequestMapping(value = "/productDetailcountList", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray productDetailcountList(String startDateTime, String endDateTime, int saleid, String county) {
        return distributeCenterPopProductService.productDetailcountyList(startDateTime, endDateTime, saleid, county);
    }

    //添加
    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    @ResponseBody

    public int addProduct(Product Product) throws Exception {

        return distributeCenterPopProductService.addProduct(Product);
    }

    //子表添加
    @RequestMapping(value = "/addProductDetail", method = RequestMethod.POST)
    @ResponseBody
    public int addProductDetail(ProductDetail productDetail) {

        return distributeCenterPopProductService.addProductDetail(productDetail);
    }

    //修改
    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    @ResponseBody
    public int updateProduct(Product Product) {

        return distributeCenterPopProductService.updateProduct(Product);
    }

    //子表修改
    @RequestMapping(value = "/updateProductDetail", method = RequestMethod.POST)
    @ResponseBody
    public int updateProductDetail(ProductDetail ProductDetail) {

        return distributeCenterPopProductService.updateProductDetail(ProductDetail);
    }

    //删除
    @RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
    @ResponseBody
    public int deleteProduct(int id) {

        return distributeCenterPopProductService.deleteProduct(id);
    }

    //子表删除
    @RequestMapping(value = "/deleteProductDetail", method = RequestMethod.POST)
    @ResponseBody
    public int deleteProductDetail(int id) {

        return distributeCenterPopProductService.deleteProductDetail(id);
    }

    //子表全部删除
    @RequestMapping(value = "/deleteProductDetailAtuo", method = RequestMethod.POST)
    @ResponseBody
    public int deleteProductDetailAuto(int saleid) {

        return distributeCenterPopProductService.deleteProductDetailAuto(saleid);
    }

    //tree查询地区
    @RequestMapping(value = "/regionsList", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray regionsList(int parentid, String attributes) {
        return distributeCenterPopProductService.regionsList(parentid, attributes);
    }

    //根据id查询地区
    @RequestMapping(value = "/regionsListId", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray regionsList(int id) {
        return distributeCenterPopProductService.regionsPatchId(id);
    }

    //根据id查询多个县
    @RequestMapping(value = "/regionsListIds", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray regionsLists(int id) {
        return distributeCenterPopProductService.regionsPatchIds(id);
    }

    //查询品类
    @RequestMapping(value = "/producttypesList", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray producttypesList() {
        return distributeCenterPopProductService.proucttypeesList();
    }

    //根据id查询品类
    @RequestMapping(value = "/producttypesListsku", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray producttypesListsku(int id) {
        return distributeCenterPopProductService.proucttypeesListsku(id);
    }

    //查询渠道
    @RequestMapping(value = "/channelsList", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray channelsList() {
        return distributeCenterPopProductService.channelsList();
    }

    //查询分销商
    @RequestMapping(value = "/channelsInfoList", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray channelsInfoList() {
        return distributeCenterPopProductService.channelsInfoList();
    }

    //查询物料
    @RequestMapping(value = "/productsList", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray skuList(Products products) {
        return distributeCenterPopProductService.skuList(products);
    }

    //根据物料编码查询品类 产品组
    @RequestMapping(value = "/getSelectSkuValue", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray GetSelectSkuValue(String sku, Integer productTypeId) {
        return distributeCenterPopProductService.GetSelectSkuValue(sku, productTypeId);
    }

    //查询产品组品类
    @RequestMapping(value = "/departmentproducttypeList", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray departmentproducttypeList() {
        return distributeCenterPopProductService.departmentproducttypeList();
    }

    //查询渠道编码相同情况下商品编码是否相同
    @RequestMapping(value = "/productCodeList", method = RequestMethod.POST)
    @ResponseBody
    public int productCodeList(int channelid, String productcode, int id) {
        return distributeCenterPopProductService.productCodeList(channelid, productcode, id);
    }

    //查询品牌
    @RequestMapping(value = "/brandList", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray brandsList() {
        return distributeCenterPopProductService.brandsList();
    }

    //根据id查询品牌
    @RequestMapping(value = "/brandidList", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray brandsIdList(int id) {
        return distributeCenterPopProductService.brandsIdList(id);
    }

    /***************-------------------@Author hwl-----------------------************/

    /**
     * 跳转可售商品价格页面
     *
     * @return
     */
    @RequestMapping("/saleProductPrice")
    public String priceToProduct() {

        return "pop/product/saleProductPrice";
    }

    //查询子表
    @RequestMapping(value = "/priceDetailList", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray priceDetailList(int saleid) {
        return distributeCenterPopProductService.priceDetailList(saleid);
    }

    //List页面跳转详细页
    @RequestMapping("/priceListEdit")
    public String priceListEdit(String gloid, Model mode) {
        mode.addAttribute("gloid", gloid);
        return "pop/product/saleProductPriceEdit";
    }

    //子表修改
    @RequestMapping(value = "/updatePriceDetail", method = RequestMethod.POST)
    @ResponseBody
    public int updatePriceDetail(TSaleProductPrice tSaleProductPrice) {

        return distributeCenterPopProductService.updatePriceDetail(tSaleProductPrice);
    }

    //子表添加
    @RequestMapping(value = "/addPriceDetail", method = RequestMethod.POST)
    @ResponseBody
    public int addPriceDetail(TSaleProductPrice tSaleProductPrice) {

        return distributeCenterPopProductService.addPriceDetail(tSaleProductPrice);
    }

    //子表删除
    @RequestMapping(value = "/deletePriceDetail", method = RequestMethod.POST)
    @ResponseBody
    public int deletePriceDetail(int id) {

        return distributeCenterPopProductService.deleteProductDetail(id);
    }

    //查询时间范围
    @RequestMapping(value = "/priceDetailCountList", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray priceDetailCountList(String startDateTime, String endDateTime, int saleid) {
        return distributeCenterPopProductService.priceGetChanls(startDateTime, endDateTime, saleid);
    }

    //子表全部删除
    @RequestMapping(value = "/deletePriceDetailAtuo", method = RequestMethod.POST)
    @ResponseBody
    public int deletePriceDetailAtuo(int saleid) {

        return distributeCenterPopProductService.deleteProductDetailAuto(saleid);
    }

//    checkPriceTime
    //校验时间

    @RequestMapping(value = "/checkPriceTime", method = RequestMethod.POST)
    @ResponseBody
    public long checkPriceTime(int id, int  saleid, String startTime, String endTime) throws ParseException {
        return distributeCenterPopProductService.checkPriceTime(id,saleid, startTime, endTime);
    }

    /**
     * 导入可售商品价格信息
     *
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/importSaleProductsPrice", method = RequestMethod.POST)
    @ResponseBody
    public Json importSaleProductsPrice(@RequestParam MultipartFile file) throws Exception {
        Json json = new Json();
        Products productsNDTO = new Products();
        Product productDTO = new Product();
        DepartmentProductType departmentProductTypeDTO = new DepartmentProductType();
        TSaleProductPrice tSaleProductPrice = new TSaleProductPrice();
        Producttypes producttypesDTO = new Producttypes();
        TChannelsInfo tChannelsInfo = new TChannelsInfo();
        int rowsCount = 0;
        // 接收校验字段
        StringBuffer sc = new StringBuffer(2000);
        // 导入文件名
        String filePath = file.getOriginalFilename();
        //得到 Excel 工作表对象
        Sheet sheet = null;
        Row row = null;
        Map<Object, String> map = new HashMap();
        if (filePath.endsWith(".xlsx")) {//判断是否是以  .xls 结尾     91-03版Excel
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            sheet = workbook.getSheetAt(0);

        } else {
            sc.append("请使用2007或以上版本excel文档导入<br/>");
            json.setMsg("导入失败！<br/>" + sc.toString());
            return json;

        }
        //返回 Excel 工作表中有多少行
        rowsCount = sheet.getPhysicalNumberOfRows();

        if (rowsCount <= 1) {
            sc.append("报表内容不能为空!<br/>");
            json.setSuccess(false);
            json.setMsg("导入失败！<br/>" + sc.toString());
            return json;

        } else {
            boolean flage = true;
            // 先检查

            for (int rowindex = 1; rowindex < rowsCount; rowindex++) {
                row = sheet.getRow(rowindex);
                int j = rowindex + 1;
                if (row == null) {
                    break;
                }
                String channelCode = Ustring.getString(row.getCell(1)).trim();//渠道编码
                String sku = Ustring.getString(row.getCell(2)).trim();//物料编码

                String supplyprice = Ustring.getString(row.getCell(3)).trim();//结算价格
                String saleprice = Ustring.getString(row.getCell(4)).trim();//销售价格
                String limitprice = Ustring.getString(row.getCell(5)).trim();//限制价格
                short format6 = row.getCell(6).getCellStyle().getDataFormat();
                short format7 = row.getCell(7).getCellStyle().getDataFormat();
                if (format6 != 22) {
                    sc.append("第" + j + "行价格适用开始时间格式有误，请检查！");
                    json.setMsg("导入失败！<br/>" + sc.toString());
                    return json;
                }
                if (format7 != 22) {
                    sc.append("第" + j + "行价格适用结束时间格式有误，请检查！");
                    json.setMsg("导入失败！<br/>" + sc.toString());
                    return json;
                }

                /*---------------------------POI时间格式转换-------------------------*/
                String pricestarttime = "";
                String priceendtime = "";
                if (row.getCell(6).getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                    short format = row.getCell(6).getCellStyle().getDataFormat();
                    SimpleDateFormat sdf = null;
                    if (format == 22) {
                        //日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    }
                    double value = row.getCell(6).getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                    pricestarttime = sdf.format(date);
                }
                if (row.getCell(7).getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                    short format = row.getCell(7).getCellStyle().getDataFormat();
                    SimpleDateFormat sdf = null;
                    if (format == 22) {
                        //日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    }
                    double value = row.getCell(7).getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                    priceendtime = sdf.format(date);
                }
                if (isValidDate(pricestarttime, priceendtime)) {
                    sc.append("第" + j + "行开始时间大于结束时间，请检查！");
                    json.setMsg("导入失败！<br/>" + sc.toString());
                    return json;
                }
                //防止数据为空或者不符合条件的数据，进行验证
                if (channelCode == "") {
                    sc.append("第" + j + "行渠道编码为空，请检查！");
                    json.setMsg("导入失败！<br/>" + sc.toString());
                    return json;
                }
                if (sku == "") {
                    sc.append("第" + j + "行物料编码为空，请检查！");
                    json.setMsg("导入失败！<br/>" + sc.toString());
                    return json;
                }
                if (supplyprice == "") {
                    sc.append("第" + j + "行结算价格为空，请检查！");
                    json.setMsg("导入失败！<br/>" + sc.toString());
                    return json;
                }
                if (saleprice == "") {
                    sc.append("第" + j + "行结算价格为空，请检查！");
                    json.setMsg("导入失败！<br/>" + sc.toString());
                    return json;
                }
                if (limitprice == "") {
                    sc.append("第" + j + "行销售价格为空，请检查！");
                    json.setMsg("导入失败！<br/>" + sc.toString());
                    return json;
                }
                if (pricestarttime == "") {
                    sc.append("第" + j + "行价格适用开始时间为空，请检查！");
                    json.setMsg("导入失败！<br/>" + sc.toString());
                    return json;
                }
                if (priceendtime == "") {
                    sc.append("第" + j + "行价格适用结束时间为空，请检查！");
                    json.setMsg("导入失败！<br/>" + sc.toString());
                    return json;
                }
                /********-------------数字格式-------------------****/
                if (validDouble(supplyprice)) {
                    sc.append("第" + j + "行结算价格格式不符，请检查！");
                    json.setMsg("导入失败！<br/>" + sc.toString());
                    return json;
                }
                if (validDouble(saleprice)) {
                    sc.append("第" + j + "行销售价格格式不符，请检查！");
                    json.setMsg("导入失败！<br/>" + sc.toString());
                    return json;
                }
                if (validDouble(limitprice)) {
                    sc.append("第" + j + "行限制价格格式不符，请检查！");
                    json.setMsg("导入失败！<br/>" + sc.toString());
                    return json;
                }
                /*---------------------字符长度--------------------------------*/
                if (supplyprice.length() > 10) {
                    sc.append("第" + j + "行结算价格字符长度不得大于10，请检查！");
                    json.setMsg("导入失败！<br/>" + sc.toString());
                    return json;
                }
                if (saleprice.length() > 10) {
                    sc.append("第" + j + "行销售价格字符长度不得大于10，请检查！");
                    json.setMsg("导入失败！<br/>" + sc.toString());
                    return json;
                }
                if (limitprice.length() > 10) {
                    sc.append("第" + j + "行限制价格字符长度不得大于10，请检查！");
                    json.setMsg("导入失败！<br/>" + sc.toString());
                    return json;
                }

                if (Ustring.isEmpty(map.get(channelCode + sku))) {
                    map.put(channelCode + sku, channelCode + sku);
                } else {
                    sc.append("第" + j + "行物料编码相同");
                    json.setMsg("导入失败！<br/>" + sc.toString());
                    return json;
                }

                //对应Products表
                Products validSku = distributeCenterPopProductService.checkSkuFromProducts(sku);
                if (validSku == null) {
                    sc.append("第" + j + "行物料编码不存在，请检查！");
                    json.setMsg("导入失败！<br/>" + sc.toString());
                    return json;
                }

                //对应t_sale_product表
                Product validSkuAndChannel = distributeCenterPopProductService.checkSkuAndChannel(sku, channelCode);
                if (validSkuAndChannel != null) {
                    long checkTime = checkPriceTime(0, validSkuAndChannel.getId(), pricestarttime, priceendtime);
                    if (checkTime > 0) {
                        sc.append("第" + j + "行价格适用时间重复，请检查！");
                        json.setMsg("导入失败！<br/>" + sc.toString());
                        return json;
                    }
                }
                /***对应渠道表*/
                tChannelsInfo = distributeCenterPopProductService.getChannelInfo(channelCode);
                if (validSkuAndChannel != null) {
                    tSaleProductPrice.setSaleId(validSkuAndChannel.getId());
                    tSaleProductPrice.setSupplyPrice(new BigDecimal(supplyprice));
                    tSaleProductPrice.setSalePrice(new BigDecimal(saleprice));
                    tSaleProductPrice.setLimitPrice(new BigDecimal(limitprice));
                    tSaleProductPrice.setPriceStartTime(pricestarttime);
                    tSaleProductPrice.setPriceEndTime(priceendtime);
                    distributeCenterPopProductService.addTSaleProuctPrice(tSaleProductPrice);
                } else {
                    //对应productTypes表
                    producttypesDTO = distributeCenterPopProductService.getProductsTypeBySKU(sku);
                    departmentProductTypeDTO = distributeCenterPopProductService.getDepartment(validSku.getProductTypeId());

                    //插入主表数据
                    productDTO.setChannelId(tChannelsInfo.getId().intValue());
                    productDTO.setSku(sku);
                    productDTO.setSkuName(validSku.getProductName());
                    productDTO.setProductTypeId(validSku.getProductTypeId());
                    productDTO.setProductCode(sku);
                    productDTO.setOnSale("1");
                    productDTO.setChannelName(tChannelsInfo.getChannelName());
                    productDTO.setProductTypeName(producttypesDTO.getTypeName());
                    productDTO.setProductName(validSku.getProductName());
                    productDTO.setDepartmentName(departmentProductTypeDTO.getDepartmentName());
                    int id = distributeCenterPopProductService.addProductFromImport(productDTO);

                    //插入子表数据
                    int saleId = id;
                    tSaleProductPrice.setSaleId(saleId);
                    tSaleProductPrice.setSupplyPrice(new BigDecimal(supplyprice));
                    tSaleProductPrice.setSalePrice(new BigDecimal(saleprice));
                    tSaleProductPrice.setLimitPrice(new BigDecimal(limitprice));
                    tSaleProductPrice.setPriceStartTime(pricestarttime);
                    tSaleProductPrice.setPriceEndTime(priceendtime);
                    distributeCenterPopProductService.addTSaleProuctPrice(tSaleProductPrice);
                }
            }
        }
        json.setSuccess(true);
        json.setMsg("导入成功!");
        return json;
    }

    public boolean isValidDate(String startTime, String endTime) {
        //String str = "2007-01-02";
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean flag = false;
        try {
            Date startDate = formatter.parse(startTime);
            Date endDate = formatter.parse(endTime);
            if (startDate.after(endDate)) {
                flag = true;
            }
            return flag;
        } catch (Exception e) {
            return true;
        }
    }

    public boolean validDouble(String str) {
        try {
            BigDecimal v = new BigDecimal(str);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 导出可售商品价格模板
     *
     * @param session
     * @param res
     * @throws IOException
     */
    @RequestMapping(value = "/exportSaleProductPriceDemo", method = RequestMethod.GET)
    public void exportOrderList(HttpSession session, HttpServletResponse res) throws IOException {
        // 1.创建一个workbook，对应一个Excel文件
        XSSFWorkbook wb = new XSSFWorkbook();
        // 2.在workbook中添加一个sheet，对应Excel中的一个sheet
        XSSFSheet sheet = wb.createSheet("可售商品价格信息");
        sheet.setColumnWidth(0, (int) (21.57 * 256));
        sheet.setColumnWidth(1, (int) 21.57 * 256);
        sheet.setColumnWidth(3, (int) 21.57 * 256);
        sheet.setColumnWidth(4, (int) 21.57 * 256);
        sheet.setColumnWidth(5, (int) 21.57 * 256);
        sheet.setColumnWidth(6, (int) 16.14 * 256);
        sheet.setColumnWidth(7, (int) 16.57 * 256);

        // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
        XSSFRow row = sheet.createRow(0);
        // 4.创建单元格，设置值表头，设置表头居中
        XSSFCellStyle style = wb.createCellStyle();
        // 居中格式
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        int length = PopExportData.saleProductPriceDemoTitle.length;
        // 设置表头
        for (int i = 0; length - 1 >= i; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(PopExportData.saleProductPriceDemoTitle[i]);
            cell.setCellStyle(style);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date = new java.util.Date();
        String str = sdf.format(date);
        String fileName = "可售商品价格" + str;

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        ExportExcelUtil.exportCommon(is, fileName, res);
    }
}