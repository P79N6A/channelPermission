package com.haier.svc.api.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.invoice.util.HttpJsonResult;
import com.haier.order.service.GuaranteePriceUnLockService;
import com.haier.order.service.OrderService;
import com.haier.shop.model.OrderPriceGate;
import com.haier.shop.model.OrderPriceProductGroupIndustry;
import com.haier.shop.model.OrderPriceSourceChannel;
import com.haier.shop.model.OrderPriceSourceIndustry;
import com.haier.svc.api.controller.util.WebUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "guaranteePriceUnLockController")
public class GuaranteePriceUnLockController {
    private static org.apache.log4j.Logger logger = org.apache.log4j.LogManager
        .getLogger(GuaranteePriceUnLockController.class);

    @Autowired
    private GuaranteePriceUnLockService guaranteePriceUnLockService;
    @Autowired
    private OrderService orderService;
    /*当前页数*/
    private int pageIndex = 1;
    /*分页尺寸*/
    private int pageSize  = 10;

    ThreadLocal<SimpleDateFormat> tParse = new ThreadLocal<SimpleDateFormat>();

    //保本价渠道配置，和库存的渠道不一致，新的配置关系
    private final static List<Map<String, String>> CHANNELLIST = new ArrayList<Map<String, String>>();

    static {
        CHANNELLIST.add(getSource("SC", "商城"));
        CHANNELLIST.add(getSource("TB", "天猫"));
        CHANNELLIST.add(getSource("DSPT", "电商平台"));
        CHANNELLIST.add(getSource("SCFX", "商城分销"));
        CHANNELLIST.add(getSource("TMFX", "天猫分销"));
        CHANNELLIST.add(getSource("SG", "顺逛"));
    }

    @RequestMapping(value = { "/loadGuaranteePriceUnLock" })
    public String loadGuaranteePriceUnLock() {
        return "order/orderPriceOpenGateIndex";
    }

    /**
     * 查询保本价锁定列表
     * @return
     */
    @RequestMapping(value = { "/getGuaranteePriceList" })
    @ResponseBody
    public JSONObject getGuaranteePriceList(@RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate,
        @RequestParam(required = false) String channel,
        @RequestParam(required = false) String source,
        @RequestParam(required = false) String industry,
        @RequestParam(required = false) String productGroup,
        @RequestParam(required = false) String orderSn,
        @RequestParam(required = false) String cOrderSn,
        @RequestParam(required = false) String gateStatus,
        @RequestParam(required = false) String gateType,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer rows) {
        Assert.notNull(guaranteePriceUnLockService, "Property 'guaranteePriceUnLockService' is required.");
        PagerInfo pagerInfo = new PagerInfo(page, rows);
        JSONObject json = new JSONObject();
        try {
            if (pagerInfo.getPageIndex() > 0) {
                page = (page - 1) * rows;
            }
            Map<String, Object> paramMap = new HashMap<String, Object>();
            if (!StringUtil.isEmpty(startDate)) {
                paramMap.put("startDate", dateToInteger(startDate));
            }
            if (!StringUtil.isEmpty(endDate)) {
                paramMap.put("endDate", dateToInteger(endDate) + 23 * 60 * 60 + 59 * 60 + 59);
            }

            String[] cOrderSnArray = null;
            cOrderSn = cOrderSn.replace("\r", ",").replace("\n", ",").replaceAll("(,)+", ",")
                .replace(" ", "");
            if (!StringUtil.isEmpty(cOrderSn)) {
                cOrderSnArray = cOrderSn.split(",");
                Set<String> set = new HashSet<String>(Arrays.asList(cOrderSnArray));
                cOrderSnArray = set.toArray(new String[0]);
            }

            String[] orderSnArray = null;
            orderSn = orderSn.replace("\r", ",").replace("\n", ",").replaceAll("(,)+", ",")
                .replace(" ", "");
            if (!StringUtil.isEmpty(orderSn)) {
                orderSnArray = orderSn.split(",");
                Set<String> set = new HashSet<String>(Arrays.asList(orderSnArray));
                orderSnArray = set.toArray(new String[0]);
            }

            paramMap.put("channel", channel.trim());
            paramMap.put("source", source.trim());
            paramMap.put("industry", industry.trim());
            paramMap.put("productGroup", productGroup.trim());
            paramMap.put("orderSns", orderSnArray);
            paramMap.put("cOrderSns", cOrderSnArray);
            paramMap.put("gateStatus", gateStatus.trim());
            paramMap.put("gateType", gateType.trim());
            paramMap.put("start", page);
            paramMap.put("size", rows);

            int count = guaranteePriceUnLockService.getGuaranteePriceListCount(paramMap);
            List<OrderPriceGate> list = guaranteePriceUnLockService.getGuaranteePriceList(paramMap);

            json.put("rows", list);
            json.put("total", Long.valueOf(count));
        } catch (Exception e) {
            logger.error("[GuaranteePriceUnLockController_getGuaranteePriceList]查询保本价锁定列表时发生未知错误",
                e);
        }
        return json;
    }

    @RequestMapping(value = { "/getGuaranteePriceChannel" }, method = { RequestMethod.GET })
    @ResponseBody
    public HttpJsonResult<List<Map<String, String>>> getGuaranteePriceChannel() {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
        Assert.notNull(guaranteePriceUnLockService,
            "Property 'guaranteePriceUnLockService' is required.");
        try {
            result.setData(guaranteePriceUnLockService.getGuaranteePriceChannel());
        } catch (Exception e) {
            logger.error("[GuaranteePriceUnLockController_getChannel]获取渠道下拉框时发生未知错误", e);
            result.setMessage("获取渠道下拉框时发生未知错误");
        }
        return result;
    }

    @RequestMapping(value = { "/getGuaranteePriceSource" }, method = { RequestMethod.GET })
    @ResponseBody
    public HttpJsonResult<List<Map<String, String>>> getGuaranteePriceSource(@RequestParam(required = true) String channel) {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
        Assert.notNull(guaranteePriceUnLockService,
            "Property 'guaranteePriceUnLockService' is required.");
        try {
            result.setData(guaranteePriceUnLockService.getGuaranteePriceSource(channel));
        } catch (Exception e) {
            logger.error("[GuaranteePriceUnLockController_getSource]获取订单来源下拉框时发生未知错误", e);
            result.setMessage("获取订单来源下拉框时发生未知错误");
        }
        return result;
    }

    @RequestMapping(value = { "/unLockGuaranteePrice" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<String> unLockGuaranteePrice(@RequestParam(required = true) String responsiblePerson,
                                                @RequestParam(required = true) String reason,
                                                @RequestParam(required = true) String cOrderSns,
                                                HttpServletRequest request) {
        HttpJsonResult<String> result = new HttpJsonResult<String>();
        try {
            //非空之类验证
            String strErr = checkBasicError(responsiblePerson, reason, cOrderSns);
            if (!StringUtil.isEmpty(strErr)) {
                result.setMessage(strErr);
                return result;
            }

            //key=订单号,value=网单号Set
            Map<String, Set<String>> osAndOpCheckMap = setOsAndOpCheckMap(cOrderSns);
            //前端传入的订单号Set
            Set<String> orderSnSet = osAndOpCheckMap.keySet();
            //数据库中订单和网单信息
            Map<String, Set<String>> osAndOpDBMap = guaranteePriceUnLockService
                .getGateOrderSnList(new ArrayList<String>(orderSnSet));
            if (osAndOpDBMap == null) {
                result.setMessage("订单号不存在！");
                return result;
            }

            //待解锁的订单号Set
            Set<String> orderSet = new HashSet<String>();
            strErr = checkBussinessError(orderSet, osAndOpCheckMap, osAndOpDBMap);
            if (orderSet.isEmpty()) {
                result.setMessage(strErr);
                return result;
            }

            ServiceResult<String> orderResult = orderService.unLockOrderGuaranteePricByOrderSns(
                new ArrayList<String>(orderSet), request.getSession().getAttribute("loginId").toString(),
                responsiblePerson.trim(), reason.trim());
            if (orderResult != null) {
                if (!orderResult.getSuccess()) {
                    result.setMessage(orderResult.getMessage());
                    return result;
                }
                String orderServiceMessage = StringUtil.isEmpty(orderResult.getResult()) ? ""
                    : orderResult.getResult();
                if (!StringUtil.isEmpty(orderServiceMessage)) {
                    result.setMessage(orderServiceMessage + strErr + "其它订单未解锁");
                    return result;
                }
                if (!StringUtil.isEmpty(strErr)) {
                    result.setMessage(strErr + "其它订单未解锁");
                    return result;
                }
            } else {
                result.setMessage("解锁失败！");
                return result;
            }
        } catch (Exception e) {
            logger.error("[GuaranteePriceUnLockController_unLockGuaranteePrice]放行订单保本价闸口时发生未知错误",
                e);
            result.setMessage("放行订单保本价闸口时发生未知错误");
        }

        return result;
    }

    private Integer dateToInteger(String date) throws ParseException {
        if (null == tParse.get()) {
            tParse.set(new SimpleDateFormat("yyyy-MM-dd"));
        }
        return ((Long) (tParse.get().parse(date).getTime() / 1000)).intValue();
    }

    private String checkBasicError(String responsiblePerson, String reason, String cOrderSns) {
        String error = null;
        if (StringUtil.isEmpty(responsiblePerson, true)) {
            error = "请输入责任人姓名！";
        }
        if (StringUtil.isEmpty(reason, true)) {
            error = "请输入放行原因！";
        }
        if (StringUtil.isEmpty(cOrderSns, true)
            || StringUtil.isEmpty(cOrderSns.replace(",", "").replace("@", ""), true)) {
            error = "请选择要解锁的订单！";
        }
        if (responsiblePerson.trim().length() > 10) {
            error = "责任人姓名不能超过10个汉字！";
        }
        if (reason.trim().length() > 200) {
            error = "放行原因不能超过200个汉字！";
        }
        return error;
    }

    private String checkBussinessError(Set<String> orderSet,
        Map<String, Set<String>> osAndOpCheckMap,
        Map<String, Set<String>> osAndOpDBMap) {

        Set<String> checkSet = null;
        Set<String> dbSet = null;
        boolean doubleFlag;
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Set<String>> entry : osAndOpDBMap.entrySet()) {
            if (osAndOpCheckMap.containsKey(entry.getKey())) {
                checkSet = osAndOpCheckMap.get(entry.getKey());
                dbSet = entry.getValue();
                doubleFlag = false;
                for (String db : dbSet) {
                    if (!checkSet.contains(db)) {
                        sb.append("订单号：").append(entry.getKey())
                            .append("下有多个网单，请将此订单下全部网单选择后再解锁此订单！").append("\r\n");
                        doubleFlag = true;
                        break;
                    }
                }
                if (!doubleFlag) {
                    orderSet.add(entry.getKey());
                }
            } else {
                sb.append("订单号：").append(entry.getKey()).append("不存在！").append("\r\n");
            }
        }
        return sb.toString();
    }

    private Map<String, Set<String>> setOsAndOpCheckMap(String cOrderSns) {
        //key=订单号,value=网单号Set
        Map<String, Set<String>> osAndOpCheckMap = new HashMap<String, Set<String>>();
        String[] osAndOpSn = cOrderSns.split(",");
        //网单号Set
        Set<String> cOrderSnSet = new HashSet<String>();
        for (String sn : osAndOpSn) {
            String[] temp = sn.split("@");
            if (osAndOpCheckMap.containsKey(temp[0])) {
                cOrderSnSet = osAndOpCheckMap.get(temp[0]);
            } else {
                cOrderSnSet = new HashSet<String>();
                osAndOpCheckMap.put(temp[0], cOrderSnSet);
            }
            cOrderSnSet.add(temp[1]);
        }
        return osAndOpCheckMap;
    }

    @RequestMapping(value = { "/loadOrderPriceSourceChannel"}, method = { RequestMethod.GET })
    public String loadOrderPriceSourceChannel() {
        return "order/orderPriceSourceChannelIndex";
    }

    @RequestMapping(value = { "/getGuaranteePriceInvStockChannel" }, method = { RequestMethod.GET })
    @ResponseBody
    public HttpJsonResult<List<Map<String, String>>> getInvStockChannel() {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
        Assert.notNull(guaranteePriceUnLockService,
            "Property 'guaranteePriceUnLockService' is required.");
        try {
            //            result.setData(guaranteePriceUnLockService.getInvStockChannel());
            result.setData(CHANNELLIST);
        } catch (Exception e) {
            logger.error("[GuaranteePriceUnLockController_getInvStockChannel]获取stock库渠道下拉框时发生未知错误",
                e);
            result.setMessage("获取渠道列表时发生未知错误");
        }
        return result;
    }

    @RequestMapping(value = { "/getGuaranteePriceInvChannel2OrderSource" }, method = { RequestMethod.GET })
    @ResponseBody
    public HttpJsonResult<List<Map<String, String>>> getInvChannel2OrderSource(@RequestParam(required = true) String channelCode) {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
        Assert.notNull(guaranteePriceUnLockService,
            "Property 'guaranteePriceUnLockService' is required.");
        try {
            result.setData(guaranteePriceUnLockService.getInvChannel2OrderSource(channelCode));
        } catch (Exception e) {
            logger.error(
                "[GuaranteePriceUnLockController_getInvChannel2OrderSource]根据渠道获取stock库订单来源下拉框时发生未知错误",
                e);
            result.setMessage("获取订单来源列表时发生未知错误");
        }
        return result;
    }

    /**
     * 查询保本价渠道订单来源配置列表
     * @return
     */
    @RequestMapping("/getOrderPriceSourceChannelList")
    @ResponseBody
    public JSONObject getOrderPriceSourceChannelList(
        @RequestParam(required = false) String channel,
        @RequestParam(required = false) String source,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String gateType,
        @RequestParam(required = false) String sendType,
        @RequestParam(required = false) String person,
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer rows) {
        Assert.notNull(guaranteePriceUnLockService,
            "Property 'guaranteePriceUnLockService' is required.");

        PagerInfo pagerInfo = new PagerInfo(page, rows);
        JSONObject json = new JSONObject();
        try {
            if (pagerInfo.getPageIndex() > 0) {
                page = (page - 1) * rows;
            }
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("source", source.trim());
            paramMap.put("channel", channel.trim());
            paramMap.put("person", person.trim());
            paramMap.put("status", status.trim());
            paramMap.put("gateType", gateType.trim());
            paramMap.put("sendType", sendType.trim());
            paramMap.put("start", page);
            paramMap.put("size", rows);

            int count = guaranteePriceUnLockService.getOrderPriceSourceChannelListCount(paramMap);
            List<OrderPriceSourceChannel> list = guaranteePriceUnLockService.getOrderPriceSourceChannelList(paramMap);
            json.put("rows", list);
            json.put("total", Long.valueOf(count));
        } catch (Exception e) {
            logger.error(
                "[GuaranteePriceUnLockController_getOrderPriceSourceChannelList]查询保本价渠道订单来源配置列表时发生未知错误",
                e);
        }
        return json;
    }

    /**
     * 根据id修改保本价渠道订单来源配置列表
     * @return
     */
    @RequestMapping(value = { "/saveOrderPriceSourceChannel" }, method = { RequestMethod.POST})
    @ResponseBody
    public HttpJsonResult<Map<String, Object>> saveOrderPriceSourceChannel(@RequestParam(required = false) Integer id,
        @RequestParam(required = true) String channelCode,
        @RequestParam(required = true) String channelName,
        @RequestParam(required = true) String sourceCode,
        @RequestParam(required = true) String sourceName,
        @RequestParam(required = true) String status,
        @RequestParam(required = true) String gateType,
        @RequestParam(required = true) String person,
        @RequestParam(required = true) String mobile,
        @RequestParam(required = true) String email,
        @RequestParam(required = true) String sendType) {
        Assert.notNull(guaranteePriceUnLockService,
            "Property 'guaranteePriceUnLockService' is required.");
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        try {
            checkLogic(channelCode, channelName, sourceCode, sourceName, status, gateType, sendType,
                person, mobile, email, result);
            if (!result.getSuccess()) {
                return result;
            }

            OrderPriceSourceChannel orderPriceSourceChannel = new OrderPriceSourceChannel();
            orderPriceSourceChannel.setChannelCode(channelCode);
            orderPriceSourceChannel.setChannelName(channelName);
            orderPriceSourceChannel.setOrderSource(sourceCode);
            orderPriceSourceChannel.setOrderSourceName(sourceName);
            orderPriceSourceChannel.setStatus(Integer.parseInt(status));
            orderPriceSourceChannel.setGateType(Integer.parseInt(gateType));
            orderPriceSourceChannel.setPerson(person);
            orderPriceSourceChannel.setMobile(mobile);
            orderPriceSourceChannel.setEmail(email);
            orderPriceSourceChannel.setSendType(Integer.parseInt(sendType));

            if (id == null) {
                int count = guaranteePriceUnLockService
                    .createOrderPriceSourceChannel(orderPriceSourceChannel);
                if (count == 0) {
                    result.setMessage("已存在，请核对！");
                }
            } else {
                orderPriceSourceChannel.setId(id);
                int count = guaranteePriceUnLockService
                    .updateOrderPriceSourceChannel(orderPriceSourceChannel);
                if (count == 0) {
                    result.setMessage("更新失败！");
                }
            }
        } catch (Exception e) {
            logger.error(
                "[GuaranteePriceUnLockController_saveOrderPriceSourceChannel]更新保本价渠道订单来源配置列表时发生未知错误",
                e);
            result.setMessage("维护信息时异常！");
        }
        return result;
    }

    /**
     * 根据id删除保本价渠道订单来源配置列表
     * @param id
     * @return
     */
    @RequestMapping(value = { "/deleteOrderPriceSourceChannelById" }, method = { RequestMethod.POST })
    @ResponseBody
    public String deleteOrderPriceSourceChannelById(@RequestParam(required = true) Integer id) {
        Assert.notNull(guaranteePriceUnLockService, "Property 'guaranteePriceUnLockService' is required.");
        JSONObject json = new JSONObject();
        try {
            int count = guaranteePriceUnLockService.deleteOrderPriceSourceChannelById(id);
            if (count <= 0) {
                json.put("text", "error");
            }
        } catch (Exception e) {
            logger.error(
                "[GuaranteePriceUnLockController_deleteOrderPriceSourceChannelById]删除保本价渠道订单来源配置列表时发生未知错误",
                e);
            json.put("text", "error");
        }
        json.put("text", "success");
        return json.toString();
    }

    @RequestMapping(value = { "/loadProductGroupIndustry.html" })
    String loadProductGroupIndustry() {
        return "/order/orderPriceProductGroupIndustryFilter";
    }

    @RequestMapping(value = { "/getOrderPriceIndustry" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<Map<String, String>>> getOrderPriceIndustry() {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
        Assert.notNull(guaranteePriceUnLockService,
            "Property 'guaranteePriceUnLockService' is required.");
        try {
            result.setData(guaranteePriceUnLockService.getOrderPriceIndustry());
        } catch (Exception e) {
            logger.error("[GuaranteePriceUnLockController_getOrderPriceIndustry]获取产业下拉框时发生未知错误", e);
            result.setMessage("获取产业列表时发生未知错误");
        }
        return result;
    }

    @RequestMapping(value = { "/getOrderPriceProductGroup" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<Map<String, String>>> getOrderPriceProductGroup(@RequestParam(required = true) String industry) {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
        Assert.notNull(guaranteePriceUnLockService,
            "Property 'guaranteePriceUnLockService' is required.");
        try {
            result.setData(guaranteePriceUnLockService.getOrderPriceProductGroup(industry));
        } catch (Exception e) {
            logger.error(
                "[GuaranteePriceUnLockController_getOrderPriceProductGroup]获取产品组下拉框时发生未知错误", e);
            result.setMessage("获取产品组列表时发生未知错误");
        }
        return result;
    }

    /**
     * 查询订单价格管控产品组产业配置列表
     * @return
     */
    @RequestMapping(value = { "/getOrderPriceProductGroupIndustryList.html" }, method = { RequestMethod.GET })
    public String getOrderPriceProductGroupIndustryList(@RequestParam(required = false) String industry,
        @RequestParam(required = false) String productGroup,
        @RequestParam(required = false) Integer pageIndex,
        Map<String, Object> modelMap,
        HttpServletRequest request,
        HttpServletResponse response) {
        Assert.notNull(guaranteePriceUnLockService,
            "Property 'guaranteePriceUnLockService' is required.");
        this.pageIndex = pageIndex == null || pageIndex <= 0 ? 1 : pageIndex;
        PagerInfo page = new PagerInfo(this.pageSize, this.pageIndex);
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("industry", industry.trim());
            paramMap.put("productGroup", productGroup.trim());
            paramMap.put("start", page.getStart());
            paramMap.put("size", page.getPageSize());

            int listcount = guaranteePriceUnLockService
                .getOrderPriceProductGroupIndustryListCount(paramMap);
            if (listcount > 0) {
                List<OrderPriceProductGroupIndustry> list = guaranteePriceUnLockService
                    .getOrderPriceProductGroupIndustryList(paramMap);
                page.setRowsCount(listcount);
                modelMap.put("rowList", list);
            }
            modelMap.put("pager", page);
        } catch (Exception e) {
            logger.error(
                "[GuaranteePriceUnLockController_getOrderPriceProductGroupIndustryList]查询订单价格管控产品组产业配置列表时发生未知错误",
                e);
        }
        return "/order/orderPriceProductGroupIndustryGrid";
    }

    /**
     * 根据id修改保本价渠道订单来源配置列表
     * @return
     */
    @RequestMapping(value = { "/saveOrderPriceProductGroupIndustry.html" }, method = { RequestMethod.GET })
    @ResponseBody
    public HttpJsonResult<Map<String, Object>> saveOrderPriceProductGroupIndustry(@RequestParam(required = false) Integer id,
        @RequestParam(required = true) String industryCode,
        @RequestParam(required = true) String industryName,
        @RequestParam(required = true) String productGroupCode,
        @RequestParam(required = true) String productGroupName,
        Map<String, Object> modelMap,
        HttpServletRequest request,
        HttpServletResponse response) {
        Assert.notNull(guaranteePriceUnLockService,
            "Property 'guaranteePriceUnLockService' is required.");
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        try {
            checkGroupIndustryLogic(industryCode, industryName, productGroupCode, productGroupName,
                result);
            if (!result.getSuccess()) {
                return result;
            }

            OrderPriceProductGroupIndustry orderPriceProductGroupIndustry = new OrderPriceProductGroupIndustry();
            orderPriceProductGroupIndustry.setIndustryCode(industryCode);
            orderPriceProductGroupIndustry.setIndustryName(industryName);
            orderPriceProductGroupIndustry.setProductGroup(productGroupCode);
            orderPriceProductGroupIndustry.setProductGroupName(productGroupName);

            if (id == null) {
                int count = guaranteePriceUnLockService
                    .createOrderPriceProductGroupIndustry(orderPriceProductGroupIndustry);
                if (count == 0) {
                    result.setMessage("已存在，请核对！");
                }
            } else {
                orderPriceProductGroupIndustry.setId(id);
                int count = guaranteePriceUnLockService
                    .updateOrderPriceProductGroupIndustry(orderPriceProductGroupIndustry);
                if (count == 0) {
                    result.setMessage("更新失败！");
                }
            }
        } catch (Exception e) {
            logger.error(
                "[GuaranteePriceUnLockController_saveOrderPriceProductGroupIndustry]更新订单价格管控产品组产业配置列表时发生未知错误",
                e);
            result.setMessage("维护信息时异常！");
        }
        return result;
    }

    /**
     * 根据id删除订单价格管控产品组产业配置列表
     * @param id
     * @param modelMap
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "/deleteOrderPriceProductGroupIndustryById.html" }, method = { RequestMethod.GET })
    @ResponseBody
    public HttpJsonResult<Map<String, Object>> deleteOrderPriceProductGroupIndustryById(@RequestParam(required = true) Integer id,
        Map<String, Object> modelMap,
        HttpServletRequest request,
        HttpServletResponse response) {
        Assert.notNull(guaranteePriceUnLockService,
            "Property 'guaranteePriceUnLockService' is required.");
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        try {
            int count = guaranteePriceUnLockService.deleteOrderPriceProductGroupIndustryById(id);
            if (count <= 0) {
                result.setMessage("删除失败！");
            }
            result.setTotalCount(count);
        } catch (Exception e) {
            logger.error(
                "[GuaranteePriceUnLockController_deleteOrderPriceSourceChannelById]删除订单价格管控产品组产业配置列表时发生未知错误",
                e);
            result.setMessage("删除时异常！");
        }
        return result;
    }

    @RequestMapping(value = { "/loadSourceIndustry.html" })
    String loadSourceIndustry() {
        return "/order/orderPriceSourceIndustryFilter";
    }

    @RequestMapping(value = { "/getOrderPriceIndustryBySource" }, method = { RequestMethod.GET })
    @ResponseBody
    HttpJsonResult<List<Map<String, String>>> getOrderPriceIndustryBySource(@RequestParam(required = true) String source) {
        HttpJsonResult<List<Map<String, String>>> result = new HttpJsonResult<List<Map<String, String>>>();
        Assert.notNull(guaranteePriceUnLockService,
            "Property 'guaranteePriceUnLockService' is required.");
        try {
            result.setData(guaranteePriceUnLockService.getOrderPriceIndustryBySource(source));
        } catch (Exception e) {
            logger.error(
                "[GuaranteePriceUnLockController_getOrderPriceIndustryBySource]获取产业下拉框时发生未知错误", e);
            result.setMessage("获取产业列表时发生未知错误");
        }
        return result;
    }

    /**
     * 查询订单价格管控来源产业配置列表
     * @return
     */
    @RequestMapping(value = { "/getOrderPriceSourceIndustryList.html" }, method = { RequestMethod.GET })
    public String getOrderPriceSourceIndustryList(@RequestParam(required = false) String source,
        @RequestParam(required = false) String industry,
        @RequestParam(required = false) String status,
        @RequestParam(required = false) Integer pageIndex,
        Map<String, Object> modelMap,
        HttpServletRequest request,
        HttpServletResponse response) {
        Assert.notNull(guaranteePriceUnLockService,
            "Property 'guaranteePriceUnLockService' is required.");
        this.pageIndex = pageIndex == null || pageIndex <= 0 ? 1 : pageIndex;
        PagerInfo page = new PagerInfo(this.pageSize, this.pageIndex);
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("industry", industry.trim());
            paramMap.put("source", source.trim());
            paramMap.put("status", status.trim());
            paramMap.put("start", page.getStart());
            paramMap.put("size", page.getPageSize());

            int listcount = guaranteePriceUnLockService
                .getOrderPriceSourceIndustryListCount(paramMap);
            if (listcount > 0) {
                List<OrderPriceSourceIndustry> list = guaranteePriceUnLockService
                    .getOrderPriceSourceIndustryList(paramMap);
                page.setRowsCount(listcount);
                modelMap.put("rowList", list);
            }
            modelMap.put("pager", page);
        } catch (Exception e) {
            logger.error(
                "[GuaranteePriceUnLockController_getOrderPriceSourceIndustryList]查询订单价格管控来源产业配置列表时发生未知错误",
                e);
        }
        return "/order/orderPriceSourceIndustryGrid";
    }

    /**
     * 根据id修改订单价格管控来源产业配置列表
     * @return
     */
    @RequestMapping(value = { "/saveOrderPriceSourceIndustry.html" }, method = { RequestMethod.GET })
    @ResponseBody
    public HttpJsonResult<Map<String, Object>> saveOrderPriceSourceIndustry(@RequestParam(required = false) Integer id,
        @RequestParam(required = true) String industryCode,
        @RequestParam(required = true) String industryName,
        @RequestParam(required = true) String sourceCode,
        @RequestParam(required = true) String sourceName,
        @RequestParam(required = true) Integer statusCode,
        Map<String, Object> modelMap,
        HttpServletRequest request,
        HttpServletResponse response) {
        Assert.notNull(guaranteePriceUnLockService,
            "Property 'guaranteePriceUnLockService' is required.");
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        try {
            checkSourceIndustryLogic(industryCode, industryName, sourceCode, sourceName, result);
            if (!result.getSuccess()) {
                return result;
            }

            OrderPriceSourceIndustry orderPriceSourceIndustry = new OrderPriceSourceIndustry();
            orderPriceSourceIndustry.setIndustryCode(industryCode);
            orderPriceSourceIndustry.setIndustryName(industryName);
            orderPriceSourceIndustry.setOrderSource(sourceCode);
            orderPriceSourceIndustry.setOrderSourceName(sourceName);
            orderPriceSourceIndustry.setStatus(statusCode);

            if (id == null) {
                int count = guaranteePriceUnLockService
                    .createOrderPriceSourceIndustry(orderPriceSourceIndustry);
                if (count == 0) {
                    result.setMessage("已存在，请核对！");
                }
            } else {
                orderPriceSourceIndustry.setId(id);
                int count = guaranteePriceUnLockService
                    .updateOrderPriceSourceIndustry(orderPriceSourceIndustry);
                if (count == 0) {
                    result.setMessage("更新失败！");
                }
            }
        } catch (Exception e) {
            logger.error(
                "[GuaranteePriceUnLockController_saveOrderPriceSourceIndustry]更新订单价格管控来源产业配置列表时发生未知错误",
                e);
            result.setMessage("维护信息时异常！");
        }
        return result;
    }

    /**
     * 根据id删除订单价格管控来源产业配置列表
     * @param id
     * @param modelMap
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "/deleteOrderPriceSourceIndustryById.html" }, method = { RequestMethod.GET })
    @ResponseBody
    public HttpJsonResult<Map<String, Object>> deleteOrderPriceSourceIndustryById(@RequestParam(required = true) Integer id,
        Map<String, Object> modelMap,
        HttpServletRequest request,
        HttpServletResponse response) {
        Assert.notNull(guaranteePriceUnLockService,
            "Property 'guaranteePriceUnLockService' is required.");
        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        try {
            int count = guaranteePriceUnLockService.deleteOrderPriceSourceIndustryById(id);
            if (count <= 0) {
                result.setMessage("删除失败！");
            }
            result.setTotalCount(count);
        } catch (Exception e) {
            logger.error(
                "[GuaranteePriceUnLockController_deleteOrderPriceSourceIndustryById]删除订单价格管控来源产业配置列表时发生未知错误",
                e);
            result.setMessage("删除时异常！");
        }
        return result;
    }

    private void checkLogic(String channelCode, String channelName, String sourceCode,
        String sourceName, String status, String gateType, String sendType,
        String person, String mobile, String email,
        HttpJsonResult<Map<String, Object>> result) {
        if (StringUtil.isEmpty(channelCode, true)) {
            result.setMessage("请输入渠道！");
        }
        if (StringUtil.isEmpty(channelName, true)) {
            result.setMessage("请输入渠道名称！");
        }
        if (StringUtil.isEmpty(sourceCode, true)) {
            result.setMessage("请输入订单来源！");
        }
        if (StringUtil.isEmpty(sourceName, true)) {
            result.setMessage("请输入订单来源名称！");
        }
        if (StringUtil.isEmpty(sourceName, true)) {
            result.setMessage("请选择闸口类型！");
        }
        if ("1".equals(status)) {
            if ("1".equals(sendType)) {
                if (StringUtil.isEmpty(person, true)) {
                    result.setMessage("请输入负责人姓名！");
                }
                if (StringUtil.isEmpty(mobile, true)) {
                    result.setMessage("请输入手机号码！");
                }
                String[] mm = mobile.split(",");
                if (mm == null || mm.length == 0) {
                    result.setMessage("手机号码格式有误！");
                    return;
                }
                for (String m : mm) {
                    if (!isMobile(m.trim())) {
                        result.setMessage("手机号码" + m + "格式有误！");
                        break;
                    }
                }
            } else if ("2".equals(sendType)) {
                if (StringUtil.isEmpty(person, true)) {
                    result.setMessage("请输入负责人姓名！");
                }
                if (StringUtil.isEmpty(email, true)) {
                    result.setMessage("请输入邮件！");
                }
                String[] ee = email.split(",");
                if (ee == null || ee.length == 0) {
                    result.setMessage("邮件格式有误！");
                    return;
                }
                for (String e : ee) {
                    if (!isEmail(e.trim())) {
                        result.setMessage("邮件" + e + "格式有误！");
                        break;
                    }
                }
            } else if ("3".equals(sendType)) {
                if (StringUtil.isEmpty(person, true)) {
                    result.setMessage("请输入负责人姓名！");
                }
                if (StringUtil.isEmpty(mobile, true)) {
                    result.setMessage("请输入手机号码！");
                }
                String[] mm = mobile.split(",");
                if (mm == null || mm.length == 0) {
                    result.setMessage("手机号码格式有误！");
                    return;
                }
                for (String m : mm) {
                    if (!isMobile(m.trim())) {
                        result.setMessage("手机号码" + m + "格式有误！");
                        break;
                    }
                }
                if (StringUtil.isEmpty(email, true)) {
                    result.setMessage("请输入邮件！");
                }
                String[] ee = email.split(",");
                if (ee == null || ee.length == 0) {
                    result.setMessage("邮件格式有误！");
                    return;
                }
                for (String e : ee) {
                    if (!isEmail(e.trim())) {
                        result.setMessage("邮件" + e + "格式有误！");
                        break;
                    }
                }
            }
        }
    }

    private void checkGroupIndustryLogic(String industryCode, String industryName,
        String productGroupCode, String productGroupName,
        HttpJsonResult<Map<String, Object>> result) {
        if (StringUtil.isEmpty(industryCode, true)) {
            result.setMessage("请输入产业！");
        }
        if (StringUtil.isEmpty(industryName, true)) {
            result.setMessage("请输入产业名称！");
        }
        if (StringUtil.isEmpty(productGroupCode, true)) {
            result.setMessage("请输入产品组！");
        }
        if (StringUtil.isEmpty(productGroupName, true)) {
            result.setMessage("请输入产品组名称！");
        }
    }

    private void checkSourceIndustryLogic(String industryCode, String industryName,
        String sourceCode, String sourceName,
        HttpJsonResult<Map<String, Object>> result) {
        if (StringUtil.isEmpty(industryCode, true)) {
            result.setMessage("请输入产业！");
        }
        if (StringUtil.isEmpty(industryName, true)) {
            result.setMessage("请输入产业名称！");
        }
        if (StringUtil.isEmpty(sourceCode, true)) {
            result.setMessage("请输入订单来源！");
        }
        if (StringUtil.isEmpty(sourceName, true)) {
            result.setMessage("请输入订单来源名称！");
        }
    }

    private boolean isMobile(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[1][3-8]\\d{9}");
        return pattern.matcher(str).matches();
    }

    private boolean isEmail(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("^([A-Za-z0-9_\\-\\.])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,4})$");
        return pattern.matcher(str).matches();
    }

    private static Map<String, String> getSource(String k, String v) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(k, v);
        return map;
    }
}
