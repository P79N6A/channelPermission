package com.haier.distribute.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.distribute.data.model.*;
import com.haier.distribute.data.service.*;
import com.haier.distribute.service.DistributeCenterPopOrderService;
import com.haier.distribute.util.CommUtil;
import com.haier.distribute.util.OrderLogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.haier.distribute.util.OrderLogUtil.CHANNELMODULE;
import static com.haier.distribute.util.OrderLogUtil.CHANNELREMOVE;
import static com.haier.distribute.util.OrderLogUtil.CHANNELUPDATE;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2017/11/7 0007
 * \* Time: 9:28
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class DistributeCenterPopOrderServiceImpl implements DistributeCenterPopOrderService {
   @Autowired
    private PopOrderService popOrderService;
   @Autowired
    private TChanneclsInfoService tChanneclsInfoService;
   @Autowired
    private SysDictionaryService sysDictionaryService;
   @Autowired
    private OrderProductsService orderProductsService;
   @Autowired
    private TOrderOperatorLogService tOrderOperatorLogService;
   @Autowired
    private TOrderLogisticsService tOrderLogisticsService;
   @Autowired
    private TOperatorLogService tOperatorLogService;

    @Override
    public JSONObject productList(PagerInfo pager, PopOrders condition) {
        List<PopOrders> list = popOrderService.getPageByCondition(condition,
                pager.getStart(), pager.getPageSize());
        long total = popOrderService.getPagerCount(condition);

        for (PopOrders order : list) {
            order.setExportOrderSn(order.getOrderSn());
            // 订单号
            order.setOrderSn("<a href=\"../pop/orderDetail?orderSn=" + order.getOrderSn() + "\">" + order.getOrderSn() + "</a>");
            // 是否货到付款
            order.setIsCodDisplay((order.getIsCod() != null && order.getIsCod() == 1) ? "是" : "否");
            // 下单时间
            order.setAddTimeStart(CommUtil.convertLongDateToString(order.getAddTime() * 1000L));
            // 付款时间
            if (0 != order.getConfirmTime()) {
                order.setSureTime(CommUtil.convertLongDateToString(new Long(order.getConfirmTime()) * 1000L));
            }
            if (0 != order.getFirstConfirmTime()) {
                order.setFirstTureTime(CommUtil.convertLongDateToString(new Long(order.getFirstConfirmTime()) * 1000L));
            }
            // 订单状态
            //order.setOrderStatus(OrderStatus.getByCode(new Integer(order.getOrderStatus())).getName());
            // 订单类型
            order.setOrderType(OrderTypeDisplay.getByCode(new Integer(order.getOrderType())).getValue());
            order.setSource(order.getChannelName());
            //订单取消状态
            int cancelStatus = order.getCancelStatus();
            //当前订单状态
            String orderStatus = order.getOrderStatus();

            if (206 == cancelStatus) {
                order.setCancelOperation("<a href=\"../pop/cancelOpertion?orderId=" + order.getId() + "&but=sure&price=" + order.getOrderAmount() + "\">" + "确认&nbsp;" + "</a>" +
                        "<a href=\"../pop/cancelOpertion?orderId=" + order.getId() + "&but=refuse" + "\">" + "拒绝" + "</a>");
                order.setOperation("");
            } else {
                if (("200").equals(orderStatus)) {
                    order.setOperation("<a onclick=\"show('" + order.getId() + order.getOrderStatus() + order.getOrderAmount() + "')\" >确认&nbsp;</a>" +
                            "<a onclick=\"stockout('" + order.getId() + order.getOrderStatus() + "')\" >缺货&nbsp;</a>" +
                            "<a onclick=\"cancel('" + order.getId() + order.getOrderStatus() + "')\" >取消</a>");
                } else if (("204").equals(orderStatus)) {
                    order.setOperation("<a onclick=\"show('" + order.getId() + order.getOrderStatus() + order.getOrderAmount() + "')\" >确认&nbsp;</a>" +
                            "<a onclick=\"cancel('" + order.getId() + order.getOrderStatus() + "')\" >取消</a>");
                } else if (("201").equals(orderStatus)) {
                    order.setOperation("<a onclick=\"send('" + order.getId() + order.getOrderStatus() + "')\" >配送&nbsp;</a>" +
                            "<a onclick=\"finish('" + order.getId() + order.getOrderStatus() + "')\" >完成&nbsp;</a>" +
                            "<a onclick=\"cancel('" + order.getId() + order.getOrderStatus() + order.getOrderAmount() + "')\" >取消</a>");
                } else if (("203").equals(orderStatus)) {
                    order.setOperation("");
                } else if (("202").equals(orderStatus)) {
                    order.setOperation("");
                } else if (("205").equals(orderStatus)) {
                    order.setOperation("<a onclick=\"finish('" + order.getId() + order.getOrderStatus() + "')\" >完成&nbsp;</a>" +
                            "<a onclick=\"cancel('" + order.getId() + order.getOrderStatus() + order.getOrderAmount() + "')\" >取消</a>");
                }
            }
        }
        return jsonResult(list, total);

    }

    @Override
    public JSONArray getSource() {
        List<TChannelsInfo> tChanneclsInfo = tChanneclsInfoService.getList();

        JSONArray res = new JSONArray();

        for (TChannelsInfo tci : tChanneclsInfo) {
            JSONObject json = new JSONObject();
            json.put("text", tci.getChannelName());
            json.put("value", tci.getChannelCode());
            res.add(json);
        }
        return res;
    }

    @Override
    public JSONArray getSysDictionaryByType(String type) {
        SysDictionary condition = new SysDictionary();
        condition.setType(type);
        List<SysDictionary> list = this.sysDictionaryService.getListByCondition(condition);
        JSONArray res = new JSONArray();
        for (Object o : list) {
            SysDictionary dto = (SysDictionary) o;
            JSONObject json = new JSONObject();
            json.put("text", dto.getValue());
            json.put("value", dto.getCode());
            res.add(json);
        }

        return res;
    }

    @Override
    public PopOrders getOneByCondition(PopOrders condition) {
        ServiceResult<PopOrders> result = new ServiceResult<PopOrders>();
        List<PopOrders> list = popOrderService.listByCondition(condition);
        if (list == null && list.size() == 0) {
            return new PopOrders();
        } else {
            return list.get(0);
        }
    }

    @Override
    public JSONObject commodityList(Long orderId) {
        List<PopOrderProducts> list = orderProductsService.commodityList(orderId);

        return jsonCom(list);
    }

    @Override
    public JSONObject historyList(String orderSn) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<TOrderOperatorLog> list = tOrderOperatorLogService.historyList(orderSn);
        for (TOrderOperatorLog tool : list) {
            tool.setOperateTime(sdf.format(tool.getLogtime()));
        }
        return jsonCom(list);
    }

    @Override
    public String orderOpertion(String orderStatus, String Id, String oId, BigDecimal price, String expressNo, String thisStatus) {

        String moneyStatus = null;
        String check = null;

        TChannelsInfo TChannelsInfo = tChanneclsInfoService.selectPriceByOrderId(Id);

        TOrderOperatorLog tOrderOperatorLog = new TOrderOperatorLog();
        tOrderOperatorLog.setLogtime(new Date());
//        tOrderOperatorLog.setOperator(getUserName());
        tOrderOperatorLog.setOperator("no one");
        tOrderOperatorLog.setOrdersn(Id);
        //物流编号实体
        TOrderLogistics tOrderLogistics = new TOrderLogistics();
        tOrderLogistics.setOrdersn(Id);
        tOrderLogistics.setExpressno(expressNo);

        PopOrders PopOrders = new PopOrders();
        PopOrders.setId(Long.parseLong(Id));
        PopOrders order = popOrderService.getOneByCondition(PopOrders);
        String status = order.getOrderStatus();
//    	int cancelStatus = order.getCancelStatus();
        if (!thisStatus.equals(status)) {
            return "changed";
        } else {
            if ("201".equals(orderStatus)) {
                //校验关联订单号
                List<PopOrders> od = popOrderService.checkOid(oId, null);
                if (od.size() > 0) {
                    moneyStatus = "oidIsSame";
                } else {

                    if (TChannelsInfo != null) {

                    }
                    int flag = (TChannelsInfo.getMoneyLimit().subtract(price)).compareTo(TChannelsInfo.getMoneyLock());
                    int res = (TChannelsInfo.getMoneyLimit().subtract(price)).compareTo(TChannelsInfo.getMoneyAlert());
                    if (flag < 0) {
                        moneyStatus = "lock";
                    } else {
                        if (res < 0) {
                            moneyStatus = "alert";
                        }
                        int a = tChanneclsInfoService.updatePrice(Id, price);
                        tOrderOperatorLog.setChangeitem(OrderLogUtil.UPDATEPRICE);
                        BigDecimal re = TChannelsInfo.getMoneyLimit().subtract(price);

                        int b = popOrderService.orderOpertionToSure("no one", orderStatus, oId, Id);
                        int confirmTime = (int) (System.currentTimeMillis() / 1000);
                        int c = popOrderService.addConfirmTime(confirmTime, Id);
                        if ("204".equals(status)) {
                            tOrderOperatorLog.setChangeitem(OrderLogUtil.ORDER_ITEM_STATUS);
                            tOrderOperatorLog.setChangecontent(OrderLogUtil.STOCKOUT_TO_SURE + ",【关联订单号】：" + oId + ",【扣款】金额额度：" + TChannelsInfo.getMoneyLimit() + "-->" + re);
                            tOrderOperatorLogService.insertSelective(tOrderOperatorLog);
                        } else {
                            tOrderOperatorLog.setChangeitem(OrderLogUtil.ORDER_ITEM_STATUS);
                            tOrderOperatorLog.setChangecontent(OrderLogUtil.UNCONFIRMED_TO_SURE + ",【关联订单号】：" + oId + ",【扣款】金额额度：" + TChannelsInfo.getMoneyLimit() + "-->" + re);
                            tOrderOperatorLogService.insertSelective(tOrderOperatorLog);
                        }
                    }
                }

                return moneyStatus;
            } else {
                if (("201".equals(status) || "205".equals(status)) && "202".equals(orderStatus)) {
                    tChanneclsInfoService.addPrice(Id, price);
                }
                if ("205".equals(orderStatus)) {
                    List<TOrderLogistics> tol = tOrderLogisticsService.checkExpressNo(expressNo, null);
                    if (tol != null && !tol.isEmpty()) {
                        check = "isSame";
                    } else {
                        tOrderLogisticsService.insertSelective(tOrderLogistics);
                        popOrderService.updateStatus(orderStatus, Id);
                        int confirmTime = (int) (System.currentTimeMillis() / 1000);
                        int c = popOrderService.confirmTime(confirmTime, Id);
                    }
                } else {
                    popOrderService.updateStatus(orderStatus, Id);
                    int confirmTime = (int) (System.currentTimeMillis() / 1000);
                    int c = popOrderService.confirmTime(confirmTime, Id);
                }

                //订单状态变更log
                if ("200".equals(status) && "202".equals(orderStatus)) {
                    tOrderOperatorLog.setChangeitem(OrderLogUtil.ORDER_ITEM_STATUS);
                    tOrderOperatorLog.setChangecontent(OrderLogUtil.UNCONFIRMED_TO_CANCEL);
                    tOrderOperatorLogService.insertSelective(tOrderOperatorLog);
                }
                if ("200".equals(status) && "204".equals(orderStatus)) {
                    tOrderOperatorLog.setChangeitem(OrderLogUtil.ORDER_ITEM_STATUS);
                    tOrderOperatorLog.setChangecontent(OrderLogUtil.UNCONFIRMED_TO_STOCKOUT);
                    tOrderOperatorLogService.insertSelective(tOrderOperatorLog);
                }
                if ("201".equals(status) && "202".equals(orderStatus)) {
                    BigDecimal re = TChannelsInfo.getMoneyLimit().add(price);
                    tOrderOperatorLog.setChangeitem(OrderLogUtil.ORDER_ITEM_STATUS);
                    tOrderOperatorLog.setChangecontent(OrderLogUtil.SURE_TO_CANCEL + ",【补款】金额额度：" + TChannelsInfo.getMoneyLimit() + "-->" + re);
                    tOrderOperatorLogService.insertSelective(tOrderOperatorLog);
                }
                if ("201".equals(status) && "203".equals(orderStatus)) {
                    tOrderOperatorLog.setChangeitem(OrderLogUtil.ORDER_ITEM_STATUS);
                    tOrderOperatorLog.setChangecontent(OrderLogUtil.SURE_TO_FINISH);
                    tOrderOperatorLogService.insertSelective(tOrderOperatorLog);
                }
                if ("201".equals(status) && "205".equals(orderStatus)) {
                    tOrderOperatorLog.setChangeitem(OrderLogUtil.ORDER_ITEM_STATUS);
                    tOrderOperatorLog.setChangecontent(OrderLogUtil.SURE_TO_SEND + ",【运单编号】：" + expressNo);
                    tOrderOperatorLogService.insertSelective(tOrderOperatorLog);
                }
                if ("204".equals(status) && "202".equals(orderStatus)) {
                    tOrderOperatorLog.setChangeitem(OrderLogUtil.ORDER_ITEM_STATUS);
                    tOrderOperatorLog.setChangecontent(OrderLogUtil.STOCKOUT_TO_CANCEL);
                    tOrderOperatorLogService.insertSelective(tOrderOperatorLog);
                }
                if ("205".equals(status) && "202".equals(orderStatus)) {
                    BigDecimal re = TChannelsInfo.getMoneyLimit().add(price);
                    tOrderOperatorLog.setChangeitem(OrderLogUtil.ORDER_ITEM_STATUS);
                    tOrderOperatorLog.setChangecontent(OrderLogUtil.SEND_TO_CANCEL + ",【补款】金额额度：" + TChannelsInfo.getMoneyLimit() + "-->" + re);
                    tOrderOperatorLogService.insertSelective(tOrderOperatorLog);
                }
                if ("205".equals(status) && "203".equals(orderStatus)) {
                    tOrderOperatorLog.setChangeitem(OrderLogUtil.ORDER_ITEM_STATUS);
                    tOrderOperatorLog.setChangecontent(OrderLogUtil.SEND_TO_FINISH);
                    tOrderOperatorLogService.insertSelective(tOrderOperatorLog);
                }
            }
            return check;
        }
    }

    @Override
    public int cancelOpertion(String cancelStatus, String orderId, BigDecimal price) {

        TOrderOperatorLog tOrderOperatorLog = new TOrderOperatorLog();
        tOrderOperatorLog.setLogtime(new Date());
//        tOrderOperatorLog.setOperator(getUserName());
        tOrderOperatorLog.setOperator("no one");
        tOrderOperatorLog.setOrdersn(orderId);
        tOrderOperatorLog.setChangeitem(OrderLogUtil.CANCEL_STATUS_CHANGE);

        PopOrders popOrders = new PopOrders();
        popOrders.setId(Long.parseLong(orderId));
        PopOrders order = popOrderService.getOneByCondition(popOrders);
        String status = order.getOrderStatus();

        if ("207".equals(cancelStatus)) {
            popOrderService.updateStatus("202", orderId);
            tChanneclsInfoService.addPrice(orderId, price);
            popOrderService.updateCancelStatus(cancelStatus, orderId);

            //订单状态变更log
            if ("205".equals(status)) {
                tOrderOperatorLog.setChangecontent(OrderLogUtil.APPLY_TO_SUCCESS + "," + OrderLogUtil.SEND_TO_CANCEL);
                tOrderOperatorLogService.insertSelective(tOrderOperatorLog);
            }
            if ("204".equals(status)) {
                tOrderOperatorLog.setChangecontent(OrderLogUtil.APPLY_TO_SUCCESS + "," + OrderLogUtil.STOCKOUT_TO_CANCEL);
                tOrderOperatorLogService.insertSelective(tOrderOperatorLog);
            }
            if ("201".equals(status)) {
                tOrderOperatorLog.setChangecontent(OrderLogUtil.APPLY_TO_SUCCESS + "," + OrderLogUtil.SURE_TO_CANCEL);
                tOrderOperatorLogService.insertSelective(tOrderOperatorLog);
            }
            if ("200".equals(status)) {
                tOrderOperatorLog.setChangecontent(OrderLogUtil.APPLY_TO_SUCCESS + "," + OrderLogUtil.UNCONFIRMED_TO_CANCEL);
                tOrderOperatorLogService.insertSelective(tOrderOperatorLog);
            }
            //订单取消状态变更log
//    		tOrderOperatorLog.setChangecontent(OrderLogUtil.APPLY_TO_SUCCESS);
//    		tOrderOperatorLogService.insertSelective(tOrderOperatorLog);
            return 1;
        } else {
            popOrderService.updateCancelStatus(cancelStatus, orderId);
            //订单取消状态变更log
            tOrderOperatorLog.setChangecontent(OrderLogUtil.APPLY_TO_SUCCESS);
            tOrderOperatorLogService.insertSelective(tOrderOperatorLog);
            return 1;
        }
    }

    @Override
    public int editRemark(String orderSn, String codConfirmRemark) {
        PopOrders PopOrders = new PopOrders();
        PopOrders.setOrderSn(orderSn);
        PopOrders order = popOrderService.getOneByCondition(PopOrders);
        String remark = order.getCodConfirmRemark();
        if (!codConfirmRemark.equals(remark)) {
            TOrderOperatorLog tOrderOperatorLog = new TOrderOperatorLog();
            tOrderOperatorLog.setLogtime(new Date());
            tOrderOperatorLog.setOperator("no one");
            tOrderOperatorLog.setOrdersn(orderSn);
            tOrderOperatorLog.setChangeitem(OrderLogUtil.EDITREMARK);
            tOrderOperatorLog.setChangecontent("【备注】：" + remark + "-->" + codConfirmRemark);
            tOrderOperatorLogService.insert(tOrderOperatorLog);
        }
        return popOrderService.editRemark(codConfirmRemark, orderSn);
    }

    @Override
    public String editOid(String orderSn, String oid) {

        List<PopOrders> orders = popOrderService.checkOid(oid, orderSn);
        if (orders.size() > 0) {
            return "oidIsSame";
        } else {
            PopOrders PopOrders = new PopOrders();
            PopOrders.setOrderSn(orderSn);
            PopOrders order = popOrderService.getOneByCondition(PopOrders);
            String dataOid = order.getOid();
            if (!oid.equals(dataOid)) {
                TOrderOperatorLog tOrderOperatorLog = new TOrderOperatorLog();
                tOrderOperatorLog.setLogtime(new Date());
                tOrderOperatorLog.setOperator("no one");
                tOrderOperatorLog.setOrdersn(orderSn);
                tOrderOperatorLog.setChangeitem(OrderLogUtil.EDITOID);
                tOrderOperatorLog.setChangecontent("【关联订单号】：" + dataOid + "-->" + oid);
                tOrderOperatorLogService.insert(tOrderOperatorLog);
            }
            popOrderService.editOid(oid, orderSn);
            return "success";
        }
    }

    @Override
    public String editExpressNo(String orderSn, String expressNo) {

        List<TOrderLogistics> tol = tOrderLogisticsService.checkExpressNo(expressNo, orderSn);
        if (tol.size() > 0) {
            return "expressNoIsSame";
        } else {
            PopOrders PopOrders = new PopOrders();
            PopOrders.setOrderSn(orderSn);
            PopOrders order = popOrderService.getOneByCondition(PopOrders);
            String dataExpressNo = order.getExpressNo();
            if (!expressNo.equals(dataExpressNo)) {
                TOrderOperatorLog tOrderOperatorLog = new TOrderOperatorLog();
                tOrderOperatorLog.setLogtime(new Date());
                tOrderOperatorLog.setOperator("no one");
                tOrderOperatorLog.setOrdersn(orderSn);
                tOrderOperatorLog.setChangeitem(OrderLogUtil.EDITEXPRESSNO);
                tOrderOperatorLog.setChangecontent("【物流编号】：" + dataExpressNo + "-->" + expressNo);
                tOrderOperatorLogService.insert(tOrderOperatorLog);
            }
            tOrderLogisticsService.editExpressNo(expressNo, orderSn);
            return "success";
        }
    }

    @Override
    public int editOrigin(String orderSn, String consignee, String mobile, String originRegionName, String originAddress) {

        PopOrders PopOrders = new PopOrders();
        PopOrders.setOrderSn(orderSn);
        PopOrders order = popOrderService.getOneByCondition(PopOrders);
        String exportConsignee = "";
        String exportMobile = "";
        String exportOriginRegionName = "";
        String exportOriginAddress = "";
        if (!order.getConsignee().equals(consignee)) {
            exportConsignee = "【收件人】:" + order.getConsignee() + "-->" + consignee;
        }
        if (!order.getMobile().equals(mobile)) {
            exportMobile = "【联系电话】:" + order.getMobile() + "-->" + mobile;
        }
        if (!order.getOriginRegionName().equals(originRegionName)) {
            exportOriginRegionName = "【所在地】:" + order.getOriginRegionName() + "-->" + originRegionName;
        }
        if (!order.getOriginAddress().equals(originAddress)) {
            exportOriginAddress = "【详细信息】:" + order.getOriginAddress() + "-->" + originAddress;
        }
        TOrderOperatorLog tOrderOperatorLog = new TOrderOperatorLog();
        tOrderOperatorLog.setLogtime(new Date());
        tOrderOperatorLog.setOperator("no one");
        tOrderOperatorLog.setOrdersn(orderSn);
        tOrderOperatorLog.setChangeitem(OrderLogUtil.EDITORIGIN);
        if (!("" == exportConsignee && "" == exportMobile && "" == exportOriginRegionName && "" == exportOriginAddress)) {
            tOrderOperatorLog.setChangecontent(exportConsignee + exportMobile + exportOriginRegionName + exportOriginAddress);
            tOrderOperatorLogService.insert(tOrderOperatorLog);
        }
        return popOrderService.editOrigin(consignee, mobile, originRegionName, originAddress, orderSn);
    }

    @Override
    public JSONObject orderProductList(PagerInfo pager, PopOrderProducts orderProducts) {

        List<PopOrderProducts> list = orderProductsService.getPageByCondition(orderProducts,
                pager.getStart(), pager.getPageSize());
        List<PopOrderProducts> exportlist = orderProductsService.getPageByCondition(orderProducts,
                0, Integer.MAX_VALUE);
        long total = orderProductsService.getPagerCount(orderProducts);
        BigDecimal totalAmount = new BigDecimal(0);
        for (PopOrderProducts products : list) {

            products.setExportOrderSn(products.getOrderSn());
            products.setExportCorderSn(products.getcOrderSn());
            // 网单号
            products.setcOrderSn("<a href=\"../pop/orderProductsDetail?cOrderSn=" + products.getcOrderSn() + "&orderSn=" + products.getOrderSn() + "\">" + products.getcOrderSn() + "</a>");
            // 订单号
            products.setOrderSn("<a href=\"../pop/orderDetailFromP?orderSn=" + products.getOrderSn() + "\">" + products.getOrderSn() + "</a>");
            // 是否货到付款
            products.setIscoddisplay((products.getIscod() != null && products.getIscod() == 1) ? "是" : "否");
            // 下单时间
            products.setcPayTime(CommUtil.convertLongDateToString(new Long(products.getcPayTime()) * 1000L));
            products.setSource(products.getChannelName());
            products.setpAmount(products.getPrice().multiply(new BigDecimal(products.getNumber())));
        }
        for (PopOrderProducts products : exportlist) {
            products.setpAmount(products.getPrice().multiply(new BigDecimal(products.getNumber())));
            totalAmount = totalAmount.add(products.getPrice().multiply(new BigDecimal(products.getNumber())));
        }

        return jsonResultForProduct(list, total, totalAmount);
    }

    @Override
    public PopOrderProducts getOneByOrderProducts(PopOrderProducts op) {
        ServiceResult<PopOrderProducts> result = new ServiceResult<PopOrderProducts>();
        List<PopOrderProducts> list = orderProductsService.listByCondition(op);
        if (list == null && list.size() == 0) {
            return new PopOrderProducts();
        } else {
            return list.get(0);
        }
    }

    @Override
    public JSONObject channelList(PagerInfo pager, TChannelsInfo tChanneclsInfo) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<TChannelsInfo> list = tChanneclsInfoService.getPageByCondition(tChanneclsInfo,
                pager.getStart(), pager.getPageSize());
        long total = tChanneclsInfoService.getPagerCount(tChanneclsInfo);
        for (TChannelsInfo tci : list) {
            if (null != tci.getCreateTime()) {
                tci.setcTime(sdf.format(tci.getCreateTime()));
            }
            if (null != tci.getUpdateTime()) {
                tci.setuTime(sdf.format(tci.getUpdateTime()));
            }
        }
        return jsonResult(list, total);
    }

    @Override
    public String addChannel(TChannelsInfo tChanneclsInfo) {

        String userName = "admin";
        TChannelsInfo checkCode = tChanneclsInfoService.selectOneByCode(tChanneclsInfo.getChannelCode());
        TChannelsInfo checkName = tChanneclsInfoService.selectOneByName(tChanneclsInfo.getChannelName());
        TChannelsInfo checkEditName = tChanneclsInfoService.checkNameForEdit(tChanneclsInfo.getChannelName(), tChanneclsInfo.getId());
        /**
         * log实体类
         */
        TOperatorLog tol = new TOperatorLog();

        if (null == tChanneclsInfo.getId()) {
            if (checkCode != null) {
                return "codeIsSame";
            } else {
                if (checkName != null) {
                    return "nameIsSame";
                } else {
                    tChanneclsInfo.setCreateBy(userName);
                    tChanneclsInfo.setCreateTime(new Date());
                    tChanneclsInfoService.insertOne(tChanneclsInfo);
                    return "success";
                }
            }
        } else {
            if (checkEditName != null) {
                return "nameIsSame";
            } else {
                tChanneclsInfo.setUpdateBy(userName);
                tChanneclsInfo.setUpdateTime(new Date());
                TChannelsInfo tci = tChanneclsInfoService.selectOneByThisId(tChanneclsInfo.getId().toString());
                tChanneclsInfoService.updateByPrimaryKeySelective(tChanneclsInfo);
                //渠道名称
                String cnc = "";
                String mac = "";
                String mlic = "";
                String mloc = "";
                String rc = "";
                if (!tci.getChannelName().equals(tChanneclsInfo.getChannelName())) {
                    cnc = "【渠道名称】：" + tci.getChannelName() + "-->" + tChanneclsInfo.getChannelName();
                }
                //报警金额
                if (tci.getMoneyAlert().compareTo(tChanneclsInfo.getMoneyAlert()) != 0) {
                    mac = "【报警金额】：" + tci.getMoneyAlert() + "-->" + tChanneclsInfo.getMoneyAlert();
                }
                //金额额度
                if (tci.getMoneyLimit().compareTo(tChanneclsInfo.getMoneyLimit()) != 0) {
                    mlic = "【金额额度】：" + tci.getMoneyLimit() + "-->" + tChanneclsInfo.getMoneyLimit();
                }
                //锁定金额
                if (tci.getMoneyLock().compareTo(tChanneclsInfo.getMoneyLock()) != 0) {
                    mloc = "【锁定金额】：" + tci.getMoneyLock() + "-->" + tChanneclsInfo.getMoneyLock();
                }
                if (!tci.getRemark().equals(tChanneclsInfo.getRemark())) {
                    rc = "【备注】：" + tci.getRemark() + "-->" + tChanneclsInfo.getRemark();
                }
                tol.setModule(CHANNELMODULE);
                tol.setChangeitem(CHANNELUPDATE);
                tol.setChangecontent(cnc + mlic + mac + mloc + rc);
                tol.setOperator(userName);
                tol.setLogtime(new Date());
                tol.setRecordid(tChanneclsInfo.getId().intValue());
                if (!("" == cnc && "" == mlic && "" == mac && "" == mloc && "" == rc)) {
                    tOperatorLogService.insertSelective(tol);
                }
                return "success";
            }
        }
    }

    @Override
    public int removeChannel(Long id) {

        TChannelsInfo tci = tChanneclsInfoService.selectOneByThisId(id.toString());
        /**
         * log实体类
         */
        TOperatorLog tol = new TOperatorLog();
        tol.setLogtime(new Date());
        tol.setOperator("no one");
        tol.setModule(CHANNELMODULE);
        tol.setChangeitem(CHANNELREMOVE);
        tol.setChangecontent("【渠道名称】：" + tci.getChannelName());
        tOperatorLogService.insertSelective(tol);
        return tChanneclsInfoService.deleteByPrimaryKey(id);
    }

    private <T> JSONObject jsonResult(List<T> list, long total) {
        JSONObject json = new JSONObject();
        json.put("total", total);
        if (list == null || list.isEmpty()) {
            json.put("rows", new ArrayList<T>());
        } else {
            json.put("rows", list);
        }
        return json;
    }

    private <T> JSONObject jsonCom(List<T> list) {
        JSONObject json = new JSONObject();
        if (list == null || list.isEmpty()) {
            json.put("rows", new ArrayList<T>());
        } else {
            json.put("rows", list);
        }
        return json;
    }

//    private String getUserName() {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String userName = WebUtil.currentUserName(request);
//        return userName;
//    }

    private <T> JSONObject jsonResultForProduct(List<T> list, long total, BigDecimal a) {
        JSONObject json = new JSONObject();
        json.put("total", total);
        List<Map<String, Object>> mapList1 = new ArrayList<Map<String, Object>>();
        Map<String, Object> tmap1 = new HashMap<String, Object>();
        tmap1.put("cOrderSn", "总金额");
        tmap1.put("orderSn", a);
        mapList1.add(tmap1);

        if (list == null || list.isEmpty()) {
            json.put("rows", new ArrayList<T>());
            json.put("footer", mapList1);
        } else {
            json.put("rows", list);
            json.put("footer", mapList1);
        }
        return json;
    }

    @Override
    public List<PopOrders> exportOrderList(PopOrders po) {
        return popOrderService.exportlist(po);
    }

    @Override
    public List<PopOrderProducts> exportOrderProductsList(PopOrderProducts pop) {
        return orderProductsService.exportOrderProductsList(pop);
    }

    @Override
    public int finishToCancel(String orderSn) {

        TOrderOperatorLog tOrderOperatorLog = new TOrderOperatorLog();
        tOrderOperatorLog.setLogtime(new Date());
        tOrderOperatorLog.setOperator("");
        tOrderOperatorLog.setOrdersn(orderSn);
        tOrderOperatorLog.setChangeitem(OrderLogUtil.ORDER_ITEM_STATUS);
        tOrderOperatorLog.setChangecontent(OrderLogUtil.FINISH_TO_CANCEL);
        tOrderOperatorLogService.insert(tOrderOperatorLog);
        return popOrderService.finishToCancel(orderSn);
    }

}