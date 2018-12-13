package com.haier.svc.api.controller.netpointstorage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.haier.common.ServiceResult;
import com.haier.invoice.util.CommUtil;
import com.haier.invoice.util.ExcelCallbackInterfaceUtil;
import com.haier.invoice.util.ExcelExportUtil;
import com.haier.shop.model.StorageCities;
import com.haier.shop.model.Storages;
import com.haier.shop.service.StorageCitiesService;
import com.haier.shop.service.StoragesService;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @Author:JinXueqian
 * @Date: 2018/7/16 16:01
 */
@Controller
@RequestMapping("storage/")
public class StoragesController {

    private static final Logger logger = LogManager.getLogger(StoragesController.class);

    @Autowired
    private StoragesService storagesService;

    @Autowired
    private StorageCitiesService storageCitiesService;

    /**
     * 库位列表页面跳转
     *
     * @return
     */
    @RequestMapping(value = {"loadStorageListPage.html"}, method = {RequestMethod.GET})
    public String loadNetPointListPage() {
        return "netpointstorage/storageList";
    }

    /**
     * 分页查询
     * @param response
     * @param rows
     * @param page
     */
    @RequestMapping(value = {"getStorageList.html"}, method = {RequestMethod.POST})
    public void getNetPointsList(HttpServletResponse response,
                                 @RequestParam(required = false) String storageName,
                                 @RequestParam(required = false) String storageCode,
                                 @RequestParam(required = false) String storageType,
                                 @RequestParam(required = false) String isTogether,
                                 @RequestParam(required = false) String isSupportCod,
                                 @RequestParam(required = false) Integer rows,
                                 @RequestParam(required = false) Integer page) {

        try {
            if (rows == null){
                rows = 50;
            }
            if (page == null){
                page = 1;
            }
            Map<String, Object> params = new HashMap();
            params.put("m", (page - 1) * rows);
            params.put("n", rows);
            params.put("storageName", storageName);
            params.put("storageCode", storageCode);
            params.put("storageType", storageType);
            params.put("isTogether", isTogether);
            params.put("isSupportCod", isSupportCod);

            Map<String, Object> retMap = storagesService.getStoragesList(params);
            response.setCharacterEncoding("UTF-8");
            String json = JSON.toJSONString(retMap);
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("网点列表失败", e);
        }
    }


    /**
     * 导出库位列表
     *
     * @param
     * @param request
     * @param response
     */
    @RequestMapping(value = {"exportStoragesList"})
    public void exportInvoiceOrderProductsList( @RequestParam(required = false) String storageName,
                                                @RequestParam(required = false) String storageCode,
                                                @RequestParam(required = false) String storageType,
                                                @RequestParam(required = false) String isTogether,
                                                @RequestParam(required = false) String isSupportCod,
            HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> params = new HashMap();

        params.put("storageName", storageName);
        params.put("storageCode", storageCode);
        params.put("storageType", storageType);
        params.put("isTogether", isTogether);
        params.put("isSupportCod", isSupportCod);

        final List<Map<String, Object>> resultList = storagesService.getExportStoragesListByParams(params);

        String fileName = "库位报表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "数据导出";

        String[] sheetHead = new String[]{"序号", "库位名称", "库位码","是否货票同行", "库位类型","所属中心","中心代码", "所属区域"};
        try {
            ExcelExportUtil.exportEntity(request, response, fileName, sheetName, sheetHead, new ExcelCallbackInterfaceUtil() {
                @Override
                public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp) throws Exception {
                    setExcelBodyTotalForStoragesList(sheet, temp, resultList);
                }
            });
        } catch (Exception e) {
            logger.error("库位列表导出失败", e);
            e.printStackTrace();
        }
    }


    /**
     * 添加库位页面跳转
     * @return
     */
    @RequestMapping(value = {"storagesAdd"})
    public String storagesAdd() {
        return "netpointstorage/storagesAdd";
    }

    /**
     * 保存库位
     * @param request
     * @return
     */
    @RequestMapping(value = {"saveStorageInfo"},method = {RequestMethod.POST})
    @ResponseBody
    public String saveStorageInfo(HttpServletRequest request, HttpServletResponse response, Storages storages,String cityName){

        JSONObject json = new JSONObject();

        try {
            //添加时间
            storages.setAddTime(new Date());
            //www库位
            storages.setWwwCode("");
            //是否开启货票同行,由于页面没有输入 ,默认为0:否
            storages.setIsFreightInvoice(0);
            //淘宝仓库编码
            storages.setTaobaoStoreCode("");

            //判断页面提交数据是否非空
            //是否支持货到付款,如果没有输入,默认为0:否
            if(storages.getIsSupportCod() == null){
                storages.setIsSupportCod(0);
            }
            //所属中心
            if(storages.getCenterCity() == null){
                storages.setCenterCity(0);
            }
            //网单中心代码
            if(storages.getCenterCode() == null){
                storages.setCenterCode("");
            }
            //工贸代码
            if(storages.getIndustryCode() == null){
                storages.setIndustryCode("");
            }
            //工贸名称
            if(storages.getIndustryName() == null){
                storages.setIndustryName("");
            }
            //所属区域,如果没有设置为0
            if(storages.getArea() == null){
                storages.setArea(0);
            }
            //预计送达时间
            if(storages.getLimitTime() == null){
                storages.setLimitTime("");
            }
            //备注
            if(storages.getRemark() == null){
                storages.setRemark("");
            }

            int  result = storagesService.insert(storages,cityName);
            if (result > 0 ){
                json.put("flag",1);
                return json.toString();
            }else {
                json.put("flag",2);
                return json.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("添加失败");
        }
        json.put("flag",3);
        return json.toString();
    }


    /**
     * 查询库位覆盖城市
     * @param provinceId
     * @param modal
     * @return
     */
    @RequestMapping("getRegionOptions")
    @ResponseBody
    public String getRegionOptions(@RequestParam(required = true)Integer provinceId, Model modal){
        Map<String, Object> retMap = new HashMap<String,Object>();
        ServiceResult<List<StorageCities>> result = new ServiceResult<List<StorageCities>>();
        try {
            List<StorageCities> cityIdByProvinceId = storageCitiesService.getAllCityByProvId(provinceId);

            if(cityIdByProvinceId!=null && cityIdByProvinceId.size()>0){
                result.setSuccess(true);
                result.setResult(cityIdByProvinceId);

            }else{
                result.setSuccess(false);
                result.setMessage("不存在的省id");
            }


            if(result!=null && result.getSuccess()){
                retMap.put("data", result.getResult());
                retMap.put("flag", true);
            }else{
                retMap.put("flag", false);
                retMap.put("message", result.getMessage());
            }
            Gson gson=new Gson();
            return gson.toJson(retMap);
        } catch (Exception e) {
            logger.error("[StoragesController][getRegionOptions]异常，e:"+e.getMessage());
            return null;
        }
    }
    /**
     * 查询库位覆盖城市
     * @param cityId
     * @param modal
     * @return
     */
    @RequestMapping("getRegionByCiyId")
    @ResponseBody
    public String getRegionByCiyId(@RequestParam(required = true)Integer cityId, Model modal){
        Map<String, Object> retMap = new HashMap<String,Object>();
        ServiceResult<List<StorageCities>> result = new ServiceResult<List<StorageCities>>();
        try {
            List<StorageCities> cityIdByProvinceId = storageCitiesService.getAllRegionByCityId(cityId);

            if(cityIdByProvinceId!=null && cityIdByProvinceId.size()>0){
                result.setSuccess(true);
                result.setResult(cityIdByProvinceId);

            }else{
                result.setSuccess(false);
                result.setMessage("不存在的市id");
            }

            if(result!=null && result.getSuccess()){
                retMap.put("data", result.getResult());
                retMap.put("flag", true);
            }else{
                retMap.put("flag", false);
                retMap.put("message", result.getMessage());
            }
            Gson gson=new Gson();
            return gson.toJson(retMap);
        } catch (Exception e) {
            logger.error("[StoragesController][getRegionByCiyId]异常，e:"+e.getMessage());
            return null;
        }
    }

    /**
     * 删除库位
     * @param
     * @return
     */
    @RequestMapping(value = "deleteStorage" ,method = {RequestMethod.GET})
    @ResponseBody
    public String deleteStorage (@RequestParam(required = false)Integer id){
        JSONObject json = new JSONObject();
        try {
            int rows = storagesService.deleteByPrimaryKey(id);
            if(rows > 0){
                json.put("flag",1);
                return json.toString();
            }else {
                json.put("flag",2);
                return json.toString();
            }

        } catch (Exception e) {
            logger.error("删除失败:物流宝库存同步库位id"+id+e.getMessage());
        }
        json.put("flag",3);
        return json.toString();
    }







    /**
     * 导出库位网单具体数据，实现回调函数
     *
     * @param sheet
     * @param temp
     * @param list
     * @throws Exception
     */
    private void setExcelBodyTotalForStoragesList(WritableSheet sheet, int temp,List<Map<String, Object>> list) throws Exception {
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
            //库位名称
            sheet.setColumnView(1, 20);
            sheet.addCell(new Label(1, temp, CommUtil.getStringValue(map.get("name")), textFormat));
            //库位码
            sheet.setColumnView(2, 20);
            sheet.addCell(new Label(2, temp, CommUtil.getStringValue(map.get("code")), textFormat));
            //是否货票同行
            String isFreightInvoice = CommUtil.getStringValue(map.get("isFreightInvoice"));
            if (isFreightInvoice.equals("0")) {
                isFreightInvoice = "非货票同行";
            } else if (isFreightInvoice.equals("1")) {
                isFreightInvoice = "货票同行";
            } else {
                isFreightInvoice = "";
            }
                sheet.setColumnView(3, 20);
                sheet.addCell(new Label(3, temp, isFreightInvoice, textFormat));
                //库位类型
                String type = CommUtil.getStringValue(map.get("type"));
                if (type.equals("1")) {
                    type = "大库";
                } else if (type.equals("2")) {
                    type = "小库";
                } else  {
                    type = "";
                }
                sheet.setColumnView(4, 15);
                sheet.addCell(new Label(4, temp, type, textFormat));
                //所属中心
                String cityName = CommUtil.getStringValue(map.get("cityName"));

                sheet.setColumnView(5, 15);
                sheet.addCell(new Label(5, temp, cityName, textFormat));
                //中心代码
                sheet.setColumnView(6, 20);
                sheet.addCell(new Label(6, temp, CommUtil.getStringValue(map.get("centerCode")), textFormat));

                //所属区域
                String area = CommUtil.getStringValue(map.get("area"));
                if (area.equals("1")) {
                    area = "东区";
                } else if (area.equals("2")) {
                    area = "西区";
                }  else if (area.equals("3")) {
                    area = "南区";
                }  else if (area.equals("4")) {
                    area = "北区";
                } else  {
                    area = "";
                }
                sheet.setColumnView(7, 15);
                sheet.addCell(new Label(7, temp, area, textFormat));
                temp++;
            }

        }
    }

