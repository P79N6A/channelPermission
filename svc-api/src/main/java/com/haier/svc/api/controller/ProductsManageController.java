package com.haier.svc.api.controller;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.haier.order.service.*;
import com.haier.shop.model.*;
import com.haier.shop.service.MdmDataService;
import com.haier.svc.api.controller.util.StringUtil;
import com.haier.svc.service.GetMtlInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.svc.api.controller.util.ExcelCallbackInterfaceUtil;
import com.haier.svc.api.controller.util.ExcelExportUtil;

import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import org.springframework.web.servlet.ModelAndView;

/**
 * 产品
 * @author Lockie
 */
@Controller
@RequestMapping(value = "productsManage/")
public class ProductsManageController {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(ProductsManageController.class);
	
    @Autowired
	ProductsManageService productsManageService;
    @Autowired
	private ShopProductPictureService shopProductPictureService;
    @Autowired
	private ShopProductFeatureService shopProductFeatureService;
    @Autowired
	private ShopProductGatefoldService shopProductGatefoldService;
    @Autowired
	private ShopProductOuterPackingBoxeService shopProductOuterPackingBoxeService;
    @Autowired
	private ShopProductSpecificationService shopProductSpecificationService;

    @Autowired
    StorageStreetsService storageStreetsService;

    @Autowired
	MdmDataService mdmDataService;

    @Autowired
	private GetMtlInfoService getMtlInfoService;

    @Autowired
	private ShopProductInfoService shopProductInfoService;
	/**
	 //TODO 页面跳转
	 * @return
	 */
	@RequestMapping(value = { "products" }, method = { RequestMethod.GET })
	public String SinglePageJump(Model modal){
		ServiceResult<List<Map<String,Object>>> brands = productsManageService.getBrands();
		if(brands.getSuccess()){
			modal.addAttribute("brands", brands.getResult());
		}else{
			modal.addAttribute("brands", null);
		}
		ServiceResult<List<Map<String, Object>>> productType = productsManageService.getProductType();
		if(productType.getSuccess()){
			modal.addAttribute("productType", productType.getResult());
		}else{
			modal.addAttribute("productType", null);
		}
//		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
//		productsManageService.getProductCate(returnList, 0);
//		modal.addAttribute("productCate", returnList);
		return  "order/productManage";
	}
	
	//TODO 商品查询列表
	@RequestMapping(value = { "queryProductsManageList" }, method = {RequestMethod.POST})
	void queryProductsManageList(
			@RequestParam(required = false) String productName,
			@RequestParam(required = false) String sku,
			@RequestParam(required = false) Integer productCateId,
			@RequestParam(required = false) Integer brandId,
			@RequestParam(required = false) Integer isVirtual,
			@RequestParam(required = false) Integer onSale,
			@RequestParam(required = false) Integer audit,//state审核状态
			@RequestParam(required = false) Integer productTypeId,
			@RequestParam(required = false) Integer energySubsidy,//energySubsidyAmount大于0是节能补贴
			@RequestParam(required = false) Integer storageType,
			@RequestParam(required = false) Integer isNoLimitStockProduct,
			@RequestParam(required = false) Integer isNotConfirm,
			@RequestParam(required = false) Integer multiStorage,
			@RequestParam(required = false) String taobaoId,
			@RequestParam(required = false) Integer rows,
			@RequestParam(required = false) Integer page,
			HttpServletRequest request, HttpServletResponse response){
		try {
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("productName", productName);
			map.put("sku", sku);
			map.put("productCateId", productCateId);
			map.put("brandId", brandId);
			map.put("isVirtual", isVirtual);
			map.put("onSale", onSale);
			map.put("state", audit);
			map.put("productTypeId", productTypeId);
			map.put("energySubsidy", energySubsidy);
			map.put("storageType", storageType);
			map.put("isNoLimitStockProduct", isNoLimitStockProduct);
			map.put("isNotConfirm", isNotConfirm);
			map.put("multiStorage", multiStorage);
			map.put("taobaoId", taobaoId);
    		if (page != null && page!=0){
    			map.put("page", (page-1)*rows);
            }else{
            	map.put("page", page);
            }
            if (rows == null){
            	map.put("rows", 50);
            }else{
            	map.put("rows", rows);
            }
            ServiceResult<List<Map<String,Object>>> queryProductsManageList = productsManageService.queryProductsManageList(map);
            Map<String, Object> retMap = new HashMap<String, Object>();
            if(queryProductsManageList!=null && queryProductsManageList.getSuccess()==true){
            	List<Map<String,Object>> result = queryProductsManageList.getResult();
            	PagerInfo pager = queryProductsManageList.getPager();
				if (pager != null){
					retMap.put("total", pager.getRowsCount());
					retMap.put("rows", result);
				}else {
					retMap.put("total", 0);
					retMap.put("rows", new ArrayList<>());
				}
            }else{
                retMap.put("total", 0);
                retMap.put("rows", new ArrayList<>());
            }
	        Gson gson=new Gson();
	        response.addHeader("Content-type","text/html;charset=utf-8");
        	response.getWriter().write(gson.toJson(retMap));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			log.error("[queryProductsManageList]出现异常，e:"+e.getMessage());
		}
	}


	@RequestMapping(value = { "/commodityCode" }, method = { RequestMethod.GET })
	public String selectBuyCode(Model model1,HttpServletRequest request, HttpServletResponse response,  @RequestParam("id") Integer id)throws Exception{
		ServiceResult<Products> productCenterDTO=productsManageService.findProductsById(id);
		ModelAndView mav = new ModelAndView();
		//根据商品Id查询产品图
		List<ProductPicturesDTO> productPicturesDTOList=shopProductPictureService.selectByProductId(id);
		//根据商品Id查询特征图
		List<ProductFeaturesDTO> productFeaturesDTOList=shopProductFeatureService.selectByProductId(id);
		//根据商品Id查询拉页图
		List<ProductGatefoldsDTO> productGatefoldsDTOList=shopProductGatefoldService.selectByProductId(id);
		//根据商品Id查询外包箱图
		List<ProductOuterPackingBoxesDTO> productOuterPackingBoxesDTOList=shopProductOuterPackingBoxeService.selectByProductId(id);
		//根据商品Id查询规格参数
		List<ProductSpecificationsDTO> productSpecificationsDTOList=shopProductSpecificationService.selectByProductId(id);
		model1.addAttribute("senateDTO",productCenterDTO.getResult());
		model1.addAttribute("productPicturesDTOList",productPicturesDTOList);
		model1.addAttribute("productFeaturesDTOList",productFeaturesDTOList);
		model1.addAttribute("productGatefoldsDTOList",productGatefoldsDTOList);
		model1.addAttribute("productOuterPackingBoxesDTOList",productOuterPackingBoxesDTOList);
		model1.addAttribute("productSpecificationsDTOList",productSpecificationsDTOList);
		response.setCharacterEncoding("UTF-8");
		return "order/commoditycode";
	}

	/**
	 //TODO  商品管理--增加 跳转页面
	 * @param id
	 * @param modal
	 * @return
	 */
	@RequestMapping("productEdit")
	public String productEdit(@RequestParam(required = false)Integer id, Model modal){
		ServiceResult<List<Map<String,Object>>> brands = productsManageService.getBrands();
		if(brands.getSuccess()){
			modal.addAttribute("brands", brands.getResult());
		}
		ServiceResult<List<Map<String, Object>>> productType = productsManageService.getProductType();
		if(productType.getSuccess()){
			modal.addAttribute("productType", productType.getResult());
		}
		ServiceResult<List<Map<String, Object>>> onSaleProductIds = productsManageService.getOnSaleProductIds();
		if(onSaleProductIds.getSuccess()){
			modal.addAttribute("onSaleProductIds", onSaleProductIds.getResult());
		}
//		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
//		productsManageService.getProductCate(returnList, 0);
//		modal.addAttribute("productCate", returnList);
		if(id!=null && id >= 0){
			ServiceResult<Products> findProductsById = productsManageService.findProductsById(id);
			if(findProductsById.getSuccess()){
				Products result = findProductsById.getResult();
				modal.addAttribute("product", result);
				
				String limitCityIds = result.getLimitCityIds();
				if(limitCityIds!=null && limitCityIds.length() > 0){
					String[] split = limitCityIds.substring(1,limitCityIds.length()).split(",");
					ServiceResult<List<Map<String,Object>>> cityByCityIds = storageStreetsService.getCityByCityIds(split);
					if(cityByCityIds !=null && cityByCityIds.getSuccess()){
						modal.addAttribute("cityIds", cityByCityIds.getResult());
					}
				}
			}
			return  "order/productEdit";
		}else{
			return  "order/productAdd";
		}
	}
	
	@RequestMapping("getRegionOptions")
	@ResponseBody
	public String getRegionOptions(@RequestParam(required = true)Integer provinceId, Model modal){
		Map<String, Object> retMap = new HashMap<String,Object>();
		try {
			ServiceResult<List<Map<String,Object>>> cityIdByProvinceId = storageStreetsService.getCityIdByProvinceId(provinceId);
			if(cityIdByProvinceId!=null && cityIdByProvinceId.getSuccess()){
				retMap.put("data", cityIdByProvinceId.getResult());
				retMap.put("flag", true);
			}else{
				retMap.put("flag", false);
				retMap.put("message", cityIdByProvinceId.getMessage());
			}
			Gson gson=new Gson();
			return gson.toJson(retMap);
		} catch (Exception e) {
			log.error("[ProductsManageController][getRegionOptions]异常，e:"+e.getMessage());
			return null;
		}
	}
	
	/**
	 //TODO 商品的编辑和添加方法 
	 * @param product
	 * @param request
	 * @param response
	 * @param modal
	 * @return
	 */
	@RequestMapping(value="productEditOrAdd",method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public Integer productEditOrAdd(@RequestBody Products product, HttpServletRequest request, HttpServletResponse response, Model modal){
		try {
			if(product==null){
				log.error("[ProductsManageController][productEditOrAdd]参数product为空。");
				return 0;
			}

			if(StringUtil.isEmpty(product.getSku())){
				return 5;
			}
			product.setSku(product.getSku().trim());
			ServiceResult<Map<String,Object>> map = productsManageService.findProductBySku(product.getSku());
			if(map!=null && map.getSuccess()){
				if(product.getId()!=null && product.getId() > 0 ){
					Map<String, Object> result = map.getResult();
					Long oldid = (Long)result.get("id");
					if(oldid.intValue() != product.getId()){
						return 3;
					}
				}else{
					return 3;
				}
			}

			Integer a = (int)(new Date().getTime()/1000);
			product.setLastModify(a);
			
			if(product.getTaobaoId()==null){
				product.setTaobaoId("");
			}
			if(product.getProductTitle()==null){
				product.setProductTitle("");
			}
			if(product.getWlbItemId()==null){
				product.setWlbItemId("");
			}
			if(product.getRejectReason()==null){
				product.setRejectReason("");
			}
			if(product.getCommentNum()==null){
				product.setCommentNum(0);
			}
			if(product.getGradeAvg()==null){
				product.setGradeAvg(BigDecimal.ZERO);
			}
			if(product.getEnergySubsidyAmount()==null){
				product.setEnergySubsidyAmount(BigDecimal.ZERO);
			}
			if(product.getProductActivityInfo()==null){
				product.setProductActivityInfo("");
			}
			if(product.getEnergySubsidyProductName()==null){
				product.setEnergySubsidyProductName("");
			}
			if(product.getLimitCityIds()==null){
				product.setLimitCityIds("");
			}
			if(product.getStockToTaotao()!=null && !product.getStockToTaotao().equals("")){
				product.setLastSyncTime(a);
			}else{
				product.setLastSyncTime(0);
			}
			if(product.getPackagePrice()==null){
				product.setPackagePrice(BigDecimal.ZERO);
			}
			if(product.getSpecailPrice()==null){
				product.setSpecailPrice(BigDecimal.ZERO);
			}
			if(product.getInternalPrice()==null){
				product.setInternalPrice(BigDecimal.ZERO);
			}
			if(product.getSaleGuidePrice()==null){
				product.setSaleGuidePrice(BigDecimal.ZERO);
			}
			if(product.getProductTags()==null){
				product.setProductTags("");
			}
			if(product.getSellingPoint()==null){
				product.setSellingPoint("");
			}
			if (product.getProductDetail() == null){
				product.setProductDetail("");
			}
			if (product.getIsStar() == null){
				product.setIsStar(false);
			}
			if (product.getsCode() == null){
				product.setsCode("");
			}
			ServiceResult<Map<String,Object>> mapByName = productsManageService.findProductByName(product.getProductName());
			if(mapByName!=null && mapByName.getSuccess()){
				if(product.getId()!=null && product.getId() > 0 ){
					Map<String, Object> result = mapByName.getResult();
					Long oldid = (Long)result.get("id");
					if(oldid.intValue() != product.getId()){
						return 4;
					}
				}else{
					return 4;
				}
			}

			if(product.getId()!=null && product.getId() > 0){//修改
				if(product.getAddTime()==null){
					product.setAddTime(a);
				}
				Boolean productUpdate = productsManageService.productUpdate(product);
				if(productUpdate){
					return 1;
				}
			}else{//添加
				product.setAddTime(a);
				Boolean productAdd = productsManageService.productAdd(product);
				if(productAdd){
					return 1;
				}
			}
			return 0;
		} catch (Exception e) {
			log.error("[ProductsManageController][productEditOrAdd]异常，e:"+e.getMessage());
			return 2;
		}
	}
	
	@RequestMapping("delProduct")
	@ResponseBody
	public Integer delProduct(Integer id, HttpServletRequest request, HttpServletResponse response, Model modal){
		try {
			if(id==null || id <= 0){
				log.error("[ProductsManageController][delProduct]参数id为空。");
				return 0;
			}
			Boolean del = productsManageService.delProduct(id)	;			
				if(del){
					return 1;
				}else{
					return 0;
				}
		} catch (Exception e) {
			log.error("[ProductsManageController][delProduct]异常，e:"+e.getMessage());
			return 2;
		}
	}
	/**
	 //TODO 导出商品列表 
	 * @param productName
	 * @param sku
	 * @param productCateId
	 * @param brandId
	 * @param isVirtual
	 * @param onSale
	 * @param audit
	 * @param productTypeId
	 * @param energySubsidy
	 * @param storageType
	 * @param isNoLimitStockProduct
	 * @param isNotConfirm
	 * @param multiStorage
	 * @param taobaoId
	 * @param rows
	 * @param page
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = { "exportProductList" })
	void exportProductList(
			@RequestParam(required = false) String productName,
			@RequestParam(required = false) String sku,
			@RequestParam(required = false) Integer productCateId,
			@RequestParam(required = false) Integer brandId,
			@RequestParam(required = false) Integer isVirtual,
			@RequestParam(required = false) Integer onSale,
			@RequestParam(required = false) Integer audit,//state审核状态
			@RequestParam(required = false) Integer productTypeId,
			@RequestParam(required = false) Integer energySubsidy,//energySubsidyAmount大于0是节能补贴
			@RequestParam(required = false) Integer storageType,
			@RequestParam(required = false) Integer isNoLimitStockProduct,
			@RequestParam(required = false) Integer isNotConfirm,
			@RequestParam(required = false) Integer multiStorage,
			@RequestParam(required = false) String taobaoId,
			@RequestParam(required = false) Integer rows,
			@RequestParam(required = false) Integer page,
			HttpServletRequest request, HttpServletResponse response){
		
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("productName", productName);
			map.put("sku", sku);
			map.put("productCateId", productCateId);
			map.put("brandId", brandId);
			map.put("isVirtual", isVirtual);
			map.put("onSale", onSale);
			map.put("state", audit);
			map.put("productTypeId", productTypeId);
			map.put("energySubsidy", energySubsidy);
			map.put("storageType", storageType);
			map.put("isNoLimitStockProduct", isNoLimitStockProduct);
			map.put("isNotConfirm", isNotConfirm);
			map.put("multiStorage", multiStorage);
			map.put("taobaoId", taobaoId);
			
			//参数验证
	        if (page != null && page != 0) {
	        	map.put("page", (page-1)*rows);
	        }
    		
            ServiceResult<List<Map<String,Object>>> queryProductsManageList = productsManageService.exportProductsManageList(map);
            List<Map<String, Object>> maps= queryProductsManageList.getResult();
            String fileName = "商品查询列表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String sheetName = "数据导出";
            String[] sheetHead = new String[]{"序号", "商品名称", "商品类型", "商品分类","品牌","物料编码","上架","商品标题","商品活动信息",
            		"是否特殊商品","是否虚拟商品","库位类型","商品所属库位编码","开票码","供货价","销售价","内部价","限价","节能补贴商品名称",
            		"节能补贴金额","更新时间","审核状态","无需确认订单商品"};
        try {
        	ExcelExportUtil.exportEntity(log, request, response, fileName, sheetName, sheetHead,
                    new ExcelCallbackInterfaceUtil() {

                        @Override
                        public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp) throws Exception {
                            setExportProductsList(sheet, temp, maps);
                        }
                    });
		} catch (Exception e) {
			log.error("[exportProductsManageList]出现异常，e:"+e.getMessage());
		}
	}
	/**
     * 导出具体数据，实现回调函数
     *
     * @param sheet
     * @param temp  行号
     * @param list  传入需要导出的 list
     * @throws WriteException
     */
    private void setExportProductsList(WritableSheet sheet, int temp,
    		List<Map<String, Object>> list) throws Exception {
        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat textFormat = new WritableCellFormat(font);
        textFormat.setAlignment(Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        DisplayFormat displayFormat = NumberFormats.INTEGER;
        WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
        numberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        int index = 0;
        for (Map<String, Object> lt : list) {

            index++;
            int i = 0;
            sheet.setColumnView(0, 10);
            sheet.addCell(new Label(0, temp, getStringValue(index), textFormat));
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.get("productName")), textFormat));//商品名称
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.get("typeName")), textFormat));//商品类型
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.get("cateName")), textFormat));//商品分类
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.get("brandName")), textFormat));//品牌
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.get("sku")), textFormat));//物料编码
            String onsale="";
            if(lt.get("onSale")!=null && lt.get("onSale").toString().equals("1"))
            	onsale="是";
            else
            	onsale="否";
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(onsale), textFormat));//上架
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.get("productTitle")), textFormat));//商品标题
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.get("productActivityInfo")), textFormat));//商品活动信息
            String isSpecial="";
            if(lt.get("isSpecial")!=null && lt.get("isSpecial").toString().equals("1"))
            	isSpecial="特殊商品";
            else
            	isSpecial="普通商品";
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(isSpecial), textFormat));//是否特殊商品
            String isVirtual="";
            if(lt.get("isVirtual")!=null && lt.get("isVirtual").toString().equals("1"))
            	isVirtual="是";
            else
            	isVirtual="否";
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(isVirtual), textFormat));//是否虚拟商品
            String storageType="";
            if(lt.get("storageType")!=null && lt.get("storageType").toString().equals("1"))
				storageType="大库";
            else
				storageType="小库";
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(storageType), textFormat));//库位类型
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.get("sCode")), textFormat));//商品所属库位编码
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.get("conTaxCode")), textFormat));//开票码
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.get("supplyPrice")), textFormat));//供货价
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.get("saleGuidePrice")), textFormat));//销售价
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.get("internalPrice")), textFormat));//内部价
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.get("limitedPrice")), textFormat));//限价
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.get("energySubsidyProductName")), textFormat));//节能补贴商品名称
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.get("energySubsidyAmount")), textFormat));//节能补贴金额
            sheet.setColumnView(++i, 25);
            String timeStamp = getStringValue(lt.get("lastModify"));
            String lastModify = timeStamp2Date(timeStamp,null);
            sheet.addCell(new Label(i, temp, lastModify, textFormat));//更新时间
			sheet.setColumnView(++i, 25);
			String state = "";
			if(lt.get("state")!=null && lt.get("state").toString().equals("0")) {
				state = "正常状态";
			}else if(lt.get("state")!=null && lt.get("state").toString().equals("1")){
				state = "新纪录";
			}else if(lt.get("state")!=null && lt.get("state").toString().equals("2")){
				state = "修改过";
			}else if(lt.get("state")!=null && lt.get("state").toString().equals("3")){
				state = "新记录被拒绝";
			}else if(lt.get("state")!=null && lt.get("state").toString().equals("4")){
				state = "修改过被拒绝";
			}
			sheet.addCell(new Label(i, temp, getStringValue(state), textFormat));//审核状态
			sheet.setColumnView(++i, 25);
			String isNotConfirm = "";
			if(lt.get("isNotConfirm")!=null && lt.get("isNotConfirm").toString().equals("0")) {
				isNotConfirm = "否";
			}else{
				isNotConfirm = "是";
			}
			sheet.addCell(new Label(i, temp, getStringValue(isNotConfirm), textFormat));//无需确认订单商品
            temp++;
        }
    }

	@RequestMapping("validSku")
	@ResponseBody
	public String validSku(@RequestParam(required = true)String sku, Model modal){
		Map<String, Object> retMap = new HashMap<String,Object>();
		try {
			SysMdmVO sysMdmVO = mdmDataService.queryBySku(sku);
			if(sysMdmVO == null){
				retMap.put("flag", false);
				retMap.put("message", "未查询到该sku对应商品信息");
			}else{
				String conTaxCode = getMtlInfoService.getConTaxCode(sku);
				if("error".equals(conTaxCode)){
					retMap.put("flag", false);
					retMap.put("productName","");
					retMap.put("conTaxCode", "");
					retMap.put("message", "未查询到该sku对应商品开票码信息");
				}else {
					retMap.put("flag", true);
					retMap.put("productName", sysMdmVO.getMaterialDescrition());
					retMap.put("conTaxCode", conTaxCode);
					retMap.put("message", "");
				}
			}
			Gson gson=new Gson();
			return gson.toJson(retMap);
		} catch (Exception e) {
			log.error("[ProductsManageController][validSku]异常，e:"+e.getMessage());
			return null;
		}
	}

    /**
     * 把object转为String null时转为""
     *
     * @param obj
     * @return
     */
    public static String getStringValue(Object obj) {
        if (obj == null)
            return "";
        return String.valueOf(obj);
    }
    /**
     * 空时间转换
     *
     * @param time
     * @return
     */
    public static String getTimeFormat(String time) {
        if (time == null || time.equals("1970-01-01 08:00:00")
                || time.equals("0000-00-00 00:00:00"))
            return "";
        return String.valueOf(time);
    }
	String formatArray(String[] str){
		try {
			if(str!=null){
				String res = "";
				for (int i = 0; i < str.length; i++) {
					res += str[i]+",";
				}
				return res.substring(0,res.length()-1);
			}
			return null;
		} catch (Exception e) {
			log.error("[formatArray] String数组格式化字符串出现异常，e:"+e.getMessage());
			return null;
		}
	}

	public static String timeStamp2Date(String seconds,String format) {
		if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
			return "";
		}
		if(format == null || format.isEmpty()){
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds+"000")));
	}
}
