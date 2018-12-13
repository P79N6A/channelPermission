package com.haier.svc.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.haier.afterSale.model.Ustring;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.order.service.OrderCenterOrderOperationService;
import com.haier.shop.model.*;
import com.haier.stock.model.InvChannel2OrderSource;
import com.haier.svc.api.controller.util.ExcelCallbackInterfaceUtil;
import com.haier.svc.api.controller.util.ExcelExportUtil;
import com.haier.system.model.SyncOrderConfigs;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.apache.log4j.LogManager;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.Boolean;


/**
 * 订单
 *
 * @author Lockie
 */

@Controller
@RequestMapping(value = "orderOperation/")
public class OrderOperationController {

    private final static org.apache.log4j.Logger log = LogManager
            .getLogger(OrderOperationController.class);

    @Autowired
    OrderCenterOrderOperationService orderCenterOrderOperationService;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
    public static final Integer SUCCESS = 1;//成功
    public static final Integer FAIL = 0;//失败
    public static final Integer REPEAT = 2;//重复数据
	private final static org.apache.log4j.Logger logger = LogManager
			.getLogger(OperationAreaController.class);
    @RequestMapping(value = {"orderList"}, method = {RequestMethod.GET})
    String jumpOrderList(HttpServletRequest request, ModelMap map) {
    	Calendar cal=Calendar.getInstance();
    	cal.setTime(new Date());
    	cal.add(Calendar.MONTH, -1);// 月份减1  
    	Date resultDate = cal.getTime(); // 结果
    	map.put("startDate", sdf.format(resultDate));
        return "order/orderList";
    }
    

    /**
     * 订单查询
     *
     * @param queryOrder
     * @param request
     * @param response
     */
    @ApiMethod(description = "订单查询", summary = "dingdan")
    @ApiResponseObject
    @RequestMapping(value = {"findQueryOrderList"}, method = {RequestMethod.POST})
    void findQueryOrderList(QueryOrderParameter queryOrder,@RequestParam(required = false) Integer rows,
            @RequestParam(required = false) Integer page,
                            HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        try {
        	System.out.println("aaaaaaa :"+page +"------"+rows);
            if (queryOrder == null) {
                return;
            }
            //参数验证
         
                queryOrder.setPage((page - 1) *rows);
            
                queryOrder.setRows(rows);
            

            if (queryOrder.getAddTimeEnd() != null && queryOrder.getAddTimeEnd().length() > 0) {
                queryOrder.setAddTimeEnd(queryOrder.getAddTimeEnd() + " 23:59:59");
            }
            if (queryOrder.getModifiedEnd() != null && queryOrder.getModifiedEnd().length() > 0) {
                queryOrder.setModifiedEnd(queryOrder.getModifiedEnd() + " 23:59:59");
            }

            ServiceResult<List<QueryOrderParameter>> findQueryOrderList = orderCenterOrderOperationService.getFindQueryOrderList(queryOrder);
            Map<String, Object> retMap = new HashMap<String, Object>();
            if (findQueryOrderList != null && findQueryOrderList.getSuccess() == true) {
                List<QueryOrderParameter> result = findQueryOrderList.getResult();
                PagerInfo pager = findQueryOrderList.getPager();
                retMap.put("total", pager.getRowsCount());
                retMap.put("rows", result);
                System.out.println(pager.getRowsCount()+"---"+result.size());
            } else {
                retMap.put("total", 0);
                retMap.put("rows", new ArrayList<>());
            }
            response.getWriter().write(JsonUtil.toJson(retMap));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 导出订单列表
     *
     * @param queryOrder
     * @param request
     * @param response
     */
    @RequestMapping(value = {"exportOrderList"})
    void exportOrderList(QueryOrderParameter queryOrder,
                         HttpServletRequest request, HttpServletResponse response) {

        if (queryOrder.getAddTimeEnd() != null && queryOrder.getAddTimeEnd().length() > 0) {
            queryOrder.setAddTimeEnd(queryOrder.getAddTimeEnd() + " 23:59:59");
        }
        if (queryOrder.getModifiedEnd() != null && queryOrder.getModifiedEnd().length() > 0) {
            queryOrder.setModifiedEnd(queryOrder.getModifiedEnd() + " 23:59:59");
        }
        ServiceResult<List<QueryOrderParameter>> findQueryOrderList = null;
        try{
             findQueryOrderList = orderCenterOrderOperationService.getExportOrderList(queryOrder);

        }catch (Exception e){
            log.error("失败:订单查询列表导出的条数过多", e);
            e.printStackTrace();
        }
        List<QueryOrderParameter> list = findQueryOrderList.getResult();

        String fileName = "订单查询列表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "数据导出";

        String[] sheetHead = new String[]{"序号", "订单号", "货到付款", "订单来源", "来源订单号", "收货人", "手机号码",
                "发票类型", "下单时间", "更新时间", "同步到商城时间", "付款时间", "首次确认时间", "订单金额", "订单类型", "订单状态", "支付方式",
                "支付状态", "已确认次数", "省份", "是否日日单"};
        try {
            ExcelExportUtil.exportEntity(log, request, response, fileName, sheetName, sheetHead,
                    new ExcelCallbackInterfaceUtil() {

                        @Override
                        public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp) throws Exception {
                            setExportOrderList(sheet, temp, list);
                        }
                    });
        } catch (Exception e) {
            log.error("订单查询列表导出失败", e);
            e.printStackTrace();
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
    private void setExportOrderList(WritableSheet sheet, int temp,
                                    List<QueryOrderParameter> list) throws Exception {
        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat textFormat = new WritableCellFormat(font);
        textFormat.setAlignment(Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        DisplayFormat displayFormat = NumberFormats.INTEGER;
        WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
        numberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        int index = 0;
        for (QueryOrderParameter lt : list) {

            index++;
            int i = 0;
            //"序号"
            sheet.setColumnView(0, 10);
            sheet.addCell(new Label(0, temp, getStringValue(index), textFormat));
            //订单号
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getOrderSn()), textFormat));
            //货到付款
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getIsCod() == 0 ? "否" : "是"), textFormat));
            //订单来源
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(QueryOrderParameter.SOURCE_MAP.get(lt.getSource())), textFormat));
            //来源订单号
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getSourceOrderNo()), textFormat));
            //暂时不显示
            //sheet.setColumnView(++i, 25);
            //sheet.addCell(new Label(i, temp, getStringValue(lt.getProductName()), textFormat));
            //收货人
            sheet.setColumnView(++i, 35);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getConsignee()), textFormat));
            //手机号码
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getMobile()), textFormat));
            //发票类型
            String invoiceType = "";
            if (lt.getInvoiceType() != null) {
                if (lt.getInvoiceType() == 1) {
                    invoiceType = "增值税发票";
                }
                if (lt.getInvoiceType() == 2) {
                    invoiceType = "普通发票";
                }
            }
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(invoiceType), textFormat));
            //下单时间
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(getTimeFormat(lt.getOrderAddTime())), textFormat));
            //更新时间
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(getTimeFormat(lt.getOrderModified())), textFormat));
            //同步到商城时间
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(getTimeFormat(lt.getSyncTime())), textFormat));
            //付款时间
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(getTimeFormat(lt.getPayTime())), textFormat));
            //首次确认时间
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(getTimeFormat(lt.getFirstConfirmTime())), textFormat));
            //订单金额
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getOrderMoney()), textFormat));
            //订单类型
            String orderType = "";
            if (lt.getOrderType() != null) {
                if (lt.getOrderType() == 0) {
                    orderType = "普通订单";
                }
                if (lt.getOrderType() == 2) {
                    orderType = "定金-尾款订单";
                }
            }
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(orderType),
                    textFormat));
            //订单状态
            String orderStatus = "";
            if (lt.getOrderStatus() != null) {
                if (lt.getOrderStatus() == 200) {
                    orderStatus = "未确认";
                }
                if (lt.getOrderStatus() == 201) {
                    orderStatus = "已确认";
                }
                if (lt.getOrderStatus() == 203) {
                    orderStatus = "已完成";
                }
                if (lt.getOrderStatus() == 202) {
                    orderStatus = "已取消";
                }
            }
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(orderStatus), textFormat));
            //支付方式
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getPaymentName()), textFormat));
            //支付状态
            String paymentStatus = "";
            if (lt.getPaymentStatus() != null) {
                if (lt.getPaymentStatus() == 100) {
                    paymentStatus = "未付款";
                }
                if (lt.getPaymentStatus() == 101) {
                    paymentStatus = "已付款";
                }
                if (lt.getPaymentStatus() == 102) {
                    paymentStatus = "待退款";
                }
                if (lt.getPaymentStatus() == 103) {
                    paymentStatus = "已退款";
                }
            }
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(paymentStatus), textFormat));
            //已确认次数
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(lt.getAutoConfirmNum()), textFormat));
            //省份
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(QueryOrderParameter.PROVINCE_MAP.get(lt.getProvinceId())), textFormat));
            //是否日日单
            String isProduceDaily = "";
            if (lt.getIsProduceDaily() != null) {
                if (lt.getIsProduceDaily() == 1) {
                    isProduceDaily = "是";
                } else {
                    isProduceDaily = "否";
                }
            }
            sheet.setColumnView(++i, 25);
            sheet.addCell(new Label(i, temp, getStringValue(isProduceDaily), textFormat));
            temp++;
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

    
    @RequestMapping(value = { "getProductSpecs"})
    @ResponseBody
    String getProductSpecs(Integer id,HttpServletRequest request, HttpServletResponse response) {
		try {
			if(id==null){
				log.error("[OrderOperationController][getProductSpecs]淘宝万人团通过id查询套装id为空");
				return null;
			}
			ServiceResult<List<Map<String,Object>>> productSpecsFormat = orderCenterOrderOperationService.productSpecsFormat(id);
            Map<String, Object> retMap = new HashMap<String, Object>();
            if(productSpecsFormat!=null && productSpecsFormat.getSuccess()==true){
            	List<Map<String,Object>> result = productSpecsFormat.getResult();
                retMap.put("start", 1);
                retMap.put("result", result);
            }else{
                retMap.put("start", 0);
            }
	        Gson gson=new Gson();
	        String json = gson.toJson(retMap);
	        return json;
		} catch (Exception e) {
			log.error("[OrderOperationController][getProductSpecs]淘宝万人团通过id查询套装发生未知异常，e:"+e.getMessage());
			return null;		
		}
    }
    
    /**
     * 淘宝万人团跳转界面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = {"tbGroupBuy"}, method = {RequestMethod.GET})
    String jumpTbGroupBuy(HttpServletRequest request) {
        return "order/tbGroupBuy";
    }

    /**
     * 淘宝万人团查询
     *
     * @param sku
     * @param page
     * @param rows
     */
    @RequestMapping(value = {"findTbOrderGroupBuy"}, method = {RequestMethod.POST})
    void tbOrderGroupBuy(String sku, Integer page, Integer rows,
                         HttpServletRequest request, HttpServletResponse response) {
        try {
            //参数验证
            if (page != null && page != 0) {
                page = (page - 1) * rows;
            }
            if (rows == null || rows == 0) {
                rows = 50;
            }
            ServiceResult<List<Map<String, Object>>> findQueryOrderList = orderCenterOrderOperationService.getTaoBaoGroupsListBySku(sku, page, rows);
            Map<String, Object> retMap = new HashMap<String, Object>();
            if (findQueryOrderList != null && findQueryOrderList.getSuccess() == true) {
                List<Map<String, Object>> result = findQueryOrderList.getResult();
                PagerInfo pager = findQueryOrderList.getPager();
                retMap.put("total", pager.getRowsCount());
                retMap.put("rows", result);
            } else {
                retMap.put("total", 0);
                retMap.put("rows", new ArrayList<>());
            }
            Gson gson = new Gson();
            response.addHeader("Content-type", "text/html;charset=utf-8");
            response.getWriter().write(gson.toJson(retMap));
            response.getWriter().flush();
        } catch (Exception e) {
            log.error("淘宝万人团定金尾款查询出现异常。e:" + e.getMessage());
        } finally {
            try {
                response.getWriter().close();
            } catch (IOException e) {
                log.error("淘宝万人团定金尾款查询关闭流出现异常，e:" + e.getMessage());
            }
        }
    }

    @RequestMapping(value = {"addTbOrderGroupBuy"}, method = {RequestMethod.POST})
    @ResponseBody
    Integer addTbOrderGroupBuy(Integer siteId, String groupName, String sku, String depositAmount, String balanceAmount,
                               String depositStartTime, String depositEndTime, String balanceStartTime, String balanceEndTime,
			String status,String shippingOpporunity,Integer id, String SpecIds, String Prices,
			HttpServletRequest request,HttpServletResponse response) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("siteId", siteId != null ? siteId : 1);
            map.put("groupName", groupName != null ? groupName : "");
            map.put("sku", sku != null ? sku : "");
            map.put("depositAmount", depositAmount != null ? new BigDecimal(depositAmount) : 0.00);
            map.put("balanceAmount", balanceAmount != null ? new BigDecimal(balanceAmount) : 0.00);
            map.put("depositStartTime", depositStartTime != null ? timeFormat(depositStartTime) : 0);
            map.put("depositEndTime", depositEndTime != null ? timeFormat(depositEndTime) : 0);
            map.put("balanceStartTime", balanceStartTime != null ? timeFormat(balanceStartTime) : 0);
            map.put("balanceEndTime", balanceEndTime != null ? timeFormat(balanceEndTime) : 0);
            if (status != null && (status.equals("true") || status.equals("false"))) {
                map.put("status", Boolean.valueOf(status));
            } else {
                map.put("status", status != null ? Integer.parseInt(status) : "");
            }
            map.put("shippingOpporunity", shippingOpporunity != null ? Integer.parseInt(shippingOpporunity) : "");
			map.put("specIds", SpecIds!=null ? SpecIds :"");
			map.put("prices", Prices!=null ? Prices :"");
            if (id == null || id == 0) {
                ServiceResult<Map<String, Object>> taoBaoGroupsBySkuAndName = orderCenterOrderOperationService.getTaoBaoGroupsBySkuAndName(sku, groupName);
                if (taoBaoGroupsBySkuAndName != null && taoBaoGroupsBySkuAndName.getSuccess()) {
                    log.error("淘宝万人团定金尾款添加查询有该条数据不允许添加，sku:" + sku + ",groupName:" + groupName);
                    return REPEAT;
                }
                ServiceResult<Boolean> addTaoBaoGroups = orderCenterOrderOperationService.addTaoBaoGroups(map);
                if (addTaoBaoGroups != null && addTaoBaoGroups.getSuccess()) {
                    return SUCCESS;
                } else {
                    log.error("淘宝万人团定金尾款添加失败，" + addTaoBaoGroups.getMessage());
                    return FAIL;
                }
            } else {
                map.put("id", id);
                ServiceResult<Boolean> updateTaoBaoGroups = orderCenterOrderOperationService.updateTaoBaoGroups(map);
                if (updateTaoBaoGroups != null && updateTaoBaoGroups.getSuccess()) {
                    return SUCCESS;
                } else {
                    log.error("淘宝万人团定金尾款修改失败，" + updateTaoBaoGroups.getMessage());
                    return FAIL;
                }
            }
        } catch (Exception e) {
            if (id == null) {
                log.error("淘宝万人团定金尾款添加出现异常,e:" + e.getMessage());
            } else {
                log.error("淘宝万人团定金尾款修改出现异常,e:" + e.getMessage());
            }
            return FAIL;
        }

    }

    /**
     * 时间String转Long(已除1000)
     *
     * @param timeString
     */
    private Integer timeFormat(String timeString) {
        try {
            if (timeString == null) {
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = sdf.parse(timeString);
            Integer time = (int) (parse.getTime() / 1000);
            return time;
        } catch (Exception e) {
            log.error("[OrderOperationController][timeFormat]时间格式化，String转Long发生异常，e:" + e.getMessage());
            return null;
        }
    }

    @RequestMapping(value = {"delTbOrderGroupBuy"}, method = {RequestMethod.GET})
    @ResponseBody
    Boolean delTbOrderGroupBuy(HttpServletRequest request,
                               HttpServletResponse response) {
        try {
            String[] ids = request.getParameterValues("ids[]");
            if (ids == null) {
                log.error("[OrderOperationController][delTbOrderGroupBuy]淘宝万人团定金尾款删除数据id为空");
                return false;
            }

            Integer[] ids2 = getIds(ids);
            if (ids2 == null || ids2.length == 0) {
                log.error("[OrderOperationController][delTbOrderGroupBuy]转换Integer为空，ids:" + ids);
                return false;
            }
            ServiceResult<Boolean> delTaoBaoGroups = orderCenterOrderOperationService.delTaoBaoGroups(ids2);
            if (delTaoBaoGroups != null && delTaoBaoGroups.getSuccess()) {
                return true;
            } else {
                log.error("[OrderOperationController][delTbOrderGroupBuy]淘宝万人团定金尾款删除数据失败，" + delTaoBaoGroups.getMessage());
                return false;
            }
        } catch (Exception e) {
            log.error("[OrderOperationController][delTbOrderGroupBuy]淘宝万人团定金尾款删除数据发生未知异常，e:" + e.getMessage());
            return false;
        }
    }

    Integer[] getIds(String[] ids) {
        try {
            Integer[] intarray = new Integer[ids.length];
            for (int i = 0; i < ids.length; i++) {
                intarray[i] = Integer.parseInt(ids[i]);
            }
            return intarray;
        } catch (Exception e) {
            log.error("ids:" + ids + "String[]转Integer[]异常：" + e.getMessage());
            return null;
        }
    }
    
    
    /**
     * 跳转待确认订单页
     * @return
     */
    @RequestMapping("/toConfirmOrder")
    public String toConfirmOrder() {
        return "order/toConfirmOrder";
    }
    
    /**
     * 查询订单来源下拉列表
     * @param response
     * @throws IOException
     */
	 @RequestMapping(value = { "/getSource" }, method = { RequestMethod.GET })
		@ResponseBody
		 public void getSource(HttpServletResponse response) throws IOException {

		 ServiceResult<List<InvChannel2OrderSource>> result =orderCenterOrderOperationService.getSource();
		      response.setContentType("text/html;charset=utf-8");
		
		      ObjectMapper mapper = new ObjectMapper();    //提供java-json相互转换功能的类
		      
		      String json = mapper.writeValueAsString(result.getResult());    //将list中的对象转换为Json格式的数组
		      response.getWriter().write(json);
		      
		  }
    /**
	 * 查询待确认列表
	 * @param
	 * @return
	 */
	@RequestMapping("search")
	@ResponseBody
	public void search(OrderProductsVo vo,HttpServletRequest request, HttpServletResponse response){
		if (vo.getRows() == null){
			vo.setRows(20);
		}
		if (vo.getPage() == null)
		{
			vo.setPage(1);
		}
		if(vo.getSource().equals("全部")) {
			vo.setSource("");
		}
		try {
			if(vo.getPage()>0){
				vo.setPage((vo.getPage() - 1) * vo.getRows());
			}else {
				vo.setPage(0);
			}
			if(vo.getAddTimeMin()!= null && !vo.getAddTimeMin().equals("")) {
			vo.setAddTimeMin(vo.getAddTimeMin()+" 00:00:00");
			}
			if(vo.getAddTimeMax()!= null && !vo.getAddTimeMax().equals("")) {
			vo.setAddTimeMax(vo.getAddTimeMax()+" 23:59:59");
			}
			ServiceResult<List<OrderProductsVo>> result =orderCenterOrderOperationService.searchList(vo);
			if (result.getSuccess() && result.getResult() != null) {
				List<OrderProductsVo> predictstocklist = result.getResult();
				Map<String, Object> retMap = new HashMap<String, Object>();
				retMap.put("total", result.getPager().getRowsCount());
				retMap.put("rows", predictstocklist);
				Gson gson=new Gson();
				response.addHeader("Content-type","text/html;charset=utf-8");
				response.getWriter().write(gson.toJson(retMap));
				response.getWriter().flush();
				response.getWriter().close();
			}
		} catch (IOException e) {
			logger.error("错误", e);
		}
	}
	//根据网单id查询复制网单详细信息
	@RequestMapping("copyProductView")
	@ResponseBody
	private ModelAndView  copyProductView(String productId,String orderSn){
		ModelAndView mv = new ModelAndView();
		Map<String,Object> map = orderCenterOrderOperationService.copyProductView(productId, orderSn);
		mv.addObject("order",map.get("order")); 
		//mv.addObject("productsVo",map.get("productsVo"));
		mv.addObject("invoices",map.get("invoices"));
		mv.addObject("orderSn",orderSn);
		//mv.addObject("productsVoSize",map.get("productsVoSize"));
		mv.setViewName("order/addOrder");
		return mv;
	}
    //根据网单id查询复制网单详细信息
    @RequestMapping("copyProductViewData")
    @ResponseBody
    private String  copyProductViewData(String orderSn){
        ModelAndView mv = new ModelAndView();
        Map<String,Object> map = orderCenterOrderOperationService.copyProductView(null, orderSn);
        return String.valueOf(map.get("productsVo"));
    }
	//查询出所有所属产业
	@RequestMapping("getProductGroupIndustryList")
	@ResponseBody
	private List<OrderPriceProductGroupIndustry> getProductGroupIndustryList(){
		return orderCenterOrderOperationService.getProductGroupIndustryList();
	}
	//拿到订单来源
    @RequestMapping("selectSyncOrderonfigs")
    @ResponseBody
    private List<SyncOrderConfigs> selectSyncOrderonfigs(){
        return orderCenterOrderOperationService.selectSyncOrderonfigs();
    }

    //拿到Shop库订单来源
    @RequestMapping("selectOrderPriceSourceChannel")
    @ResponseBody
    private List<OrderPriceSourceChannel> selectOrderPriceSourceChannel(){
        return orderCenterOrderOperationService.selectOrderPriceSourceChannel();
    }

	//保存要复制的网单信息
	@RequestMapping(value="copyProductSave",method=RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public Json copyProductSave(@RequestBody JSONObject params,HttpServletRequest request){
		Json json = new Json();
        HttpSession session = request.getSession();
        String userName = Ustring.getString(session.getAttribute("userName"));
		json = orderCenterOrderOperationService.copyProductSave(params,userName);
		return json;
	}
}
