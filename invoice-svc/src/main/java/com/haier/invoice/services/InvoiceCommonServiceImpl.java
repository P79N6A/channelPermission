package com.haier.invoice.services;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.invoice.model.SyncElecInvoiceModel;
import com.haier.invoice.model.eai.InvoiceEntity;
import com.haier.invoice.model.eai.QueryInvoiceInputType;
import com.haier.invoice.service.InvoiceCommonService;
import com.haier.invoice.service.InvoiceToTaxService;
import com.haier.invoice.util.DateFormatUtil;
import com.haier.shop.model.*;
import com.haier.shop.service.*;
import com.haier.shop.util.InvoiceServiceUtil;
import com.haier.stock.model.InvChannel2OrderSource;
import com.haier.stock.service.StockInvChannel2OrderSourceService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 发票处理公共类
 **/
@Service
public class InvoiceCommonServiceImpl implements InvoiceCommonService {

    private static final Logger logger = LogManager.getLogger(InvoiceCommonServiceImpl.class);

    @Autowired
    private ShopInvoiceService shopInvoiceService;
    @Autowired
    private ShopInvoiceElecLogsService shopInvoiceElecLogsService;
    @Autowired
    private InvoiceToTaxService invoiceToTaxService;
    @Autowired
    private ShopInvoiceSAPLogsService shopInvoiceSAPLogsService;
    @Autowired
    private SyncElecInvoiceModel syncElecInvoiceModel;
    @Autowired
    private ShopInvoiceApiLogsService shopInvoiceApiLogsService;
    @Autowired
    private ShopMemberInvoicesService shopMemberInvoicesService;
    @Autowired
    private ShopOrderProductsService shopOrderProductsService;
    @Autowired
    private ShopInvoiceChangeLogService shopInvoiceChangeLogService;
    @Autowired
    private StockInvChannel2OrderSourceService stockInvChannel2OrderSourceService;
    @Autowired
    private InvoicesWwwLogsService invoicesWwwLogsService;
    @Autowired
    private ShopOrdersService shopOrdersService;

    @Override
    public List<Map<String, Object>> getExportInvoiceMakeOutList(Map<String, Object> param) {
        return shopInvoiceService.getExportInvoiceMakeOutList(param);
    }

    @Override
    public Map<String, Object> findElecInvoiceLogsService(Map<String, Object> queryMap) {
        Map<String, Object> result = shopInvoiceElecLogsService.getElectronicInvoiceLogsByPage(queryMap);
        return result;
    }

    @Override
    public Map<String, Object> findInvoiceMakeOutList(Map<String, Object> params) {
        //获取开单列表List
        Map<String, Object> retMap = shopInvoiceService.getInvoiceMakeOutListByPage(params);
        return retMap;
    }

    @Override
    public ServiceResult<InvoiceEntity> getTaxInvoicesInfo(QueryInvoiceInputType inputType) {
        return invoiceToTaxService.queryInvoiceToTaxSys(inputType);
    }

    @Override
    public Map<String, String> queryElecInvoice(String corderSn) {
        return syncElecInvoiceModel.queryInvoiceResultMap(corderSn);
    }

    @Override
    public Map<String, Object> findInvoiceSapLogList(String cOrderSn, Map<String, Object> params) {
        String invoiceId = "";
        String orderProductId = (String) params.get("orderProductId");
        if (cOrderSn != null && !cOrderSn.trim().equals("")
                && (orderProductId == null || orderProductId.trim().equals(""))) {
            Invoices invoice = shopInvoiceService.getInvoicesByCOrderSn(cOrderSn);
            if (invoice != null) {
                invoiceId = invoice.getId() + "";
            } else {
                Map<String, Object> retMap = new HashMap<String, Object>();
                retMap.put("total", 0);
                retMap.put("rows", new ArrayList<Map<String, Object>>());
                return retMap;
            }
        }
        //参数加入params里
        params.put("invoiceId", invoiceId);

        //获取SAP接口监控列表List
        Map<String, Object> retMap = shopInvoiceSAPLogsService.getInvoiceSapLogListByPage(params);
        return retMap;
    }

    @Override
    public void updatePushAgainInvoiceSapCount(Integer id) {
        shopInvoiceSAPLogsService.resetCount(id);
    }

    @Override
    public Map<String, Object> findMakeInvoiceApiLogs(Map<String, Object> paramMap) {
        Map<String, Object> retMap = shopInvoiceApiLogsService.getInvoiceApiLogsByPage(paramMap);
        return retMap;
    }

    @Override
    public Map<String, Object> findMemberInvoiceList(Map<String, Object> paramMap) {
        Map<String, Object> retMap = shopMemberInvoicesService.findMemberInvoiceListByPage(paramMap);
        return retMap;
    }

    @Override
    public Map<String, Object> findInvoiceWwwLogPage(Map<String, Object> paramMap) {
        Map<String, Object> retMap = invoicesWwwLogsService.findInvoiceWwwLogPage(paramMap);
        return retMap;
    }

    @Override
    public List<Map<String, Object>> findInvoiceWwwLogList(Map<String, Object> paramMap) {
        List<InvoicesWwwLogDispItem> retList = invoicesWwwLogsService.findInvoiceWwwLogList(paramMap);
        List<Map<String, Object>> list = new ArrayList<>();
        for (InvoicesWwwLogDispItem iw : retList) {
            Map<String, Object> one = new HashMap<>();
            one.put("orderSn", iw.getOrderSn());
            one.put("success", InvoiceServiceUtil.parseInvoiceWwwLogsSuccess(iw.getSuccess()));
            one.put("source", InvoiceServiceUtil.parseInvoiceWwwLogsSource(iw.getSource()));
            one.put("addTime", iw.getAddTime());
            one.put("processTime", iw.getProcessTime());
            list.add(one);
        }
        return list;
    }

    @Override
    public List<MemberInvoicesDispItem> getExportMemberInvoicesList(Map<String, Object> paramMap) {
        List<MemberInvoicesDispItem> resultList = shopMemberInvoicesService.getExportMemberInvoicesList(paramMap);
        return resultList;
    }

    @Override
    public Map<String, Object> modifyMemberInvoices(Integer memberInvoiceId, String modifyFlg, String orderSn) {

        Map<String, Object> retMap = new HashMap<String, Object>();
        String displayName = "";
        boolean typeEditAble = true;
        String[] inputAuditOptions = {"待审核", "审核通过", "审核拒绝"};
        String[] invoiceTypeOptions = {"增值税发票", "普通发票", "增值税发票（普）"};

        //获得MemberInvoices信息
        MemberInvoices memberInvoices = shopMemberInvoicesService.getById(memberInvoiceId);
        if (memberInvoices != null) {
            // 获得OrderProducts信息
            List<OrderProducts> orderProductsList = shopOrderProductsService
                    .getOrderProductsByOrderId(memberInvoices.getOrderId());
            if (orderProductsList != null && orderProductsList.size() > 0) {
                for (OrderProducts eachOrderProducts : orderProductsList) {
                    // 获得Invoices信息
                    Invoices invoices = shopInvoiceService
                            .getUsableByOrderProductId(eachOrderProducts.getId());
                    if (invoices != null && invoices.getIsTogether() != null
                            && invoices.getIsTogether().equals(1)) {
                        typeEditAble = false;
                    }
                }
            }

            // 获得InvoiceChangeLogs信息   InvoiceChangeLogs表中的invoiceId对应MemberInvoices表中的id
            List<InvoiceChangeLogs> invoiceChangeLogsList = shopInvoiceChangeLogService
                    .getInvoiceChangeLogs(memberInvoiceId);
            if (invoiceChangeLogsList != null && invoiceChangeLogsList.size() > 0) {
                for (InvoiceChangeLogs eachInvoiceChangeLogs : invoiceChangeLogsList) {
                    Date newAddTime = new Date();
                    newAddTime.setTime(eachInvoiceChangeLogs.getAddTime() * 1000);
                    String addTimeStr = new SimpleDateFormat("yyyyMMdd").format(newAddTime);
                    eachInvoiceChangeLogs.setAddTime(Long.valueOf(addTimeStr));
                }
            }
            displayName = "";
            if (memberInvoices.getElectricFlag() == 1) {
                displayName = "电子发票";
            } else if (memberInvoices.getElectricFlag() == 0) {
                displayName = "纸质发票";
            }
            retMap.put("invoiceChangeLogsList", invoiceChangeLogsList);
        } else {
            displayName = "";
        }

        retMap.put("memberInvoices", memberInvoices);
        retMap.put("inputAuditOptions", inputAuditOptions);
        retMap.put("invoiceTypeOptions", invoiceTypeOptions);
        retMap.put("typeEditAble", typeEditAble);
        retMap.put("vatInvoice", "增值税发票");
        retMap.put("modifyFlg", modifyFlg);
        retMap.put("electricFlag", displayName);
        retMap.put("orderSn", orderSn);
        return retMap;
    }

    @Override
    public Map<String, Object> modifyMemberInvoicesBy3wInvoiceLogs(Integer orderId, String modifyFlg, String orderSn) {
        MemberInvoices memberInvoices = shopMemberInvoicesService.getByOrderId(orderId);
        if (null == memberInvoices) {
            logger.error("3w发票待人工处理，开增票时查询不到用户发票信息");
            throw new BusinessException("查询不到订单发票信息");
        }
        Map<String, Object> retMap = this.modifyMemberInvoices(memberInvoices.getId(), modifyFlg, orderSn);
        return retMap;
    }

    @Override
    public MemberInvoices getMemberInvoiceByInvoiceTitle(String invoiceTitle) {
        return shopMemberInvoicesService.getMemberInvoiceByInvoiceTitle(invoiceTitle);
    }

    @Override
    public String saveMemberInvoices(Integer id, Integer invoiceType, String invoiceTitle, String taxPayerNumber, String registerAddress, String registerPhone, String bankName, String bankCardNumber, Integer state, String remark, String auditor) {
        try {
            // 根据Id获得发票信息
            MemberInvoices memberInvoices = shopMemberInvoicesService.getById(id);
            if (memberInvoices == null) {
                return "对不起，该发票信息不存在!";
            }
            // 如果发票已经被锁定，则不能被编辑
            if (memberInvoices.getIsLock() == 1) {
                return "对不起，该订单的发票信息已被锁定，无法编辑!";
            }
            MemberInvoices oldmemberInvoices = new MemberInvoices();
            BeanUtils.copyProperties(memberInvoices, oldmemberInvoices);
            // 获得参数信息
            MemberInvoices paramMemberInvoices = memberInvoices;
            paramMemberInvoices.setInvoiceTitle(invoiceTitle);
            paramMemberInvoices.setRemark(remark);
            paramMemberInvoices.setAuditor(auditor);
            paramMemberInvoices.setAuditTime(new Date().getTime() / 1000);

            // 增值税发票的场合
            if (invoiceType != null && invoiceType.equals(1)) {
                paramMemberInvoices.setTaxPayerNumber(taxPayerNumber);
                paramMemberInvoices.setRegisterAddress(registerAddress);
                paramMemberInvoices.setRegisterPhone(registerPhone);
                paramMemberInvoices.setBankName(bankName);
                paramMemberInvoices.setBankCardNumber(bankCardNumber);
                paramMemberInvoices.setState(state);
            } else {
                paramMemberInvoices.setTaxPayerNumber("");
                paramMemberInvoices.setRegisterAddress("");
                paramMemberInvoices.setRegisterPhone("");
                paramMemberInvoices.setBankName("");
                paramMemberInvoices.setBankCardNumber("");
                paramMemberInvoices.setState(1);// 审核通过
            }
            if (invoiceType != null && invoiceType.intValue() > 0) {
                paramMemberInvoices.setInvoiceType(invoiceType);
            } else {
                paramMemberInvoices.setInvoiceType(0);
            }
            // 检查参数信息
            if (paramMemberInvoices.getInvoiceTitle() == null
                    || paramMemberInvoices.getInvoiceTitle().toString().equals("")) {
                return "对不起，发票抬头不能为空。";
            }
            // 增值税发票的场合
            if (paramMemberInvoices.getInvoiceType() != null
                    && paramMemberInvoices.getInvoiceType().equals(1)) {
                if (paramMemberInvoices.getTaxPayerNumber() == null
                        || paramMemberInvoices.getTaxPayerNumber().toString().equals("")) {
                    return "对不起，纳税人识别号不能为空。";
                }
                if (paramMemberInvoices.getTaxPayerNumber() == null
                        || !Arrays.asList(15, 17, 18, 20).contains(
                        paramMemberInvoices.getTaxPayerNumber().length())) {
                    return "对不起，税票号长度不正确。";
                }
                if (paramMemberInvoices.getRegisterAddress() == null
                        || paramMemberInvoices.getRegisterAddress().toString().equals("")) {
                    return "对不起，注册地址不能为空。";
                }
                if (paramMemberInvoices.getRegisterPhone() == null
                        || paramMemberInvoices.getRegisterPhone().toString().equals("")) {
                    return "对不起，注册电话不能为空。";
                }
                if (paramMemberInvoices.getBankName() == null
                        || paramMemberInvoices.getBankName().toString().equals("")) {
                    return "对不起，开户银行不能为空。";
                }
                if (paramMemberInvoices.getBankCardNumber() == null
                        || paramMemberInvoices.getBankCardNumber().toString().equals("")) {
                    return "对不起，银行卡号不能为空。";
                }
            }

            // 根据OrderId获得OrderProducts信息
            List<OrderProducts> orderProductsList = shopOrderProductsService
                    .getOrderProductsByOrderId(memberInvoices.getOrderId());
            // 获得所有的Id信息
            List<Integer> idsList = new ArrayList<Integer>();
            if (orderProductsList != null && orderProductsList.size() > 0) {
                for (OrderProducts eachOrderProducts : orderProductsList) {
                    idsList.add(eachOrderProducts.getId());
                }
            }
            // 根据ID信息取得OrderProducts信息
            if (idsList.size() > 0) {
                orderProductsList.clear();
                orderProductsList = shopOrderProductsService.getByIds(idsList);
                if (orderProductsList != null && orderProductsList.size() > 0) {
                    for (OrderProducts eachOrderProducts : orderProductsList) {
                        // 已开票的场合
                        if (eachOrderProducts.getIsMakeReceipt() != null
                                && eachOrderProducts.getIsMakeReceipt().equals(2)) {
                            return "对不起，该订单已有网单开票成功，无法编辑!";
                        }
                        // 开票中的场合
                        if (eachOrderProducts.getIsMakeReceipt() != null
                                && eachOrderProducts.getIsMakeReceipt().equals(3)
                                && !oldmemberInvoices.getInvoiceType().equals(
                                paramMemberInvoices.getInvoiceType())) {
                            return "对不起，该订单有网单开票中，不能修改发票类型!";
                        }
                    }
                }
            }

            try {
                //更新相关发票、日志业务操作
                shopMemberInvoicesService.saveInvoiceOperate(orderProductsList, paramMemberInvoices, oldmemberInvoices, auditor);
                return "";
            } catch (Exception e) {// 回滚事务
                logger.error("保存发票信息发生异常，回滚事务：", e);
                return "保存发票信息发生异常:" + e.getMessage();
            }
        } catch (Exception e) {
            logger.error("保存发票信息发生异常：", e);
            return "保存发票信息发生异常:" + e.getMessage();
        }
    }

    @Override
    public String unlockMemberInvoices(Integer id, String userName) {
        try {
            return shopMemberInvoicesService.unlockMemberInvoices(id, userName);
        } catch (Exception e) {
            logger.error("保存发票信息发生异常：", e);
            return "保存发票信息发生异常：" + e.getMessage();
        }
    }

    @Override
    public Map<String, Object> showInvoiceInfo(Integer id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<InvoicesDispItem> list = shopInvoiceService.showInvoiceInfo(id);
        if (list != null && list.size() > 0) {
            InvoicesDispItem tInvoicesDispItem = list.get(0);

            resultMap.put("id", tInvoicesDispItem.getId());
            int state = tInvoicesDispItem.getState();
            switch (state) {
                case 0:
                    resultMap.put("state", "待开票");
                    break;
                case 1:
                    resultMap.put("state", "开票中");
                    break;
                case 4:
                    resultMap.put("state", "已开票");
                    break;
                case 5:
                    resultMap.put("state", "已取消开票");
                    break;
                default:
                    resultMap.put("state", state);
                    break;
            }

            state = tInvoicesDispItem.getType();
            switch (state) {
                case 1:
                    resultMap.put("type", "增值税发票");
                    break;
                case 2:
                    resultMap.put("type", "普通发票");
                    break;
                case 3:
                    resultMap.put("type", "增值税发票（普）");
                    break;
                default:
                    resultMap.put("type", state);
                    break;
            }

            resultMap.put("number", tInvoicesDispItem.getNumber());
            state = tInvoicesDispItem.getIsReInvoice();
            switch (state) {
                case 0:
                    resultMap.put("isReInvoice", "否");
                    break;
                case 1:
                    resultMap.put("isReInvoice", "是");
                    break;
                default:
                    resultMap.put("isReInvoice", state);
                    break;
            }

            resultMap.put("orderProductId", tInvoicesDispItem.getOrderProductId());
            state = tInvoicesDispItem.getCorderType();
            switch (state) {
                case 1:
                    resultMap.put("corderType", "普通网单");
                    break;
                case 2:
                    resultMap.put("corderType", "差异网单");
                    break;
                case 3:
                    resultMap.put("corderType", "专项开票");
                    break;
                default:
                    resultMap.put("corderType", state);
                    break;
            }

            resultMap.put("diffId", tInvoicesDispItem.getDiffId());
            resultMap.put("corderSn", tInvoicesDispItem.getCorderSn());
            resultMap.put("invoiceTitle", tInvoicesDispItem.getInvoiceTitle());
            resultMap.put("price", "￥" + tInvoicesDispItem.getPrice() + "元");
            resultMap.put("amount", "￥" + tInvoicesDispItem.getAmount() + "元");
            resultMap.put("invoiceNumber", tInvoicesDispItem.getInvoiceNumber());

            String time = tInvoicesDispItem.getBillingTime();
            String dateTime = DateFormatUtil.getCurrentDateWithFormat(
                    new Date(Long.parseLong(time) * 1000), "yyyy-MM-dd hh:mm:ss");
            resultMap.put("billingTime", dateTime);
            time = tInvoicesDispItem.getEaiWriteState();
            if (time == null) {
                resultMap.put("eaiWriteState", "正常");
            } else {
                if (time.equals("")) {
                    resultMap.put("eaiWriteState", "正常");
                } else if (time.equals("3")) {
                    resultMap.put("eaiWriteState", "当月作废");
                } else if (time.equals("4")) {
                    resultMap.put("eaiWriteState", "跨月冲红");
                } else {
                    resultMap.put("eaiWriteState", time);
                }
            }
            time = tInvoicesDispItem.getInvalidTime();
            dateTime = DateFormatUtil.getCurrentDateWithFormat(
                    new Date(Long.parseLong(time) * 1000), "yyyy-MM-dd hh:mm:ss");
            resultMap.put("invalidTime", dateTime);
            time = tInvoicesDispItem.getFirstPushTime();
            dateTime = DateFormatUtil.getCurrentDateWithFormat(
                    new Date(Long.parseLong(time) * 1000), "yyyy-MM-dd hh:mm:ss");
            resultMap.put("firstPushTime", dateTime);
            time = tInvoicesDispItem.getLastModifyTime();
            dateTime = DateFormatUtil.getCurrentDateWithFormat(
                    new Date(Long.parseLong(time) * 1000), "yyyy-MM-dd hh:mm:ss");
            resultMap.put("lastModifyTime", dateTime);
            state = tInvoicesDispItem.getStatusType();
            switch (state) {
                case 1:
                    resultMap.put("statusType", "首次推送");
                    break;
                case 2:
                    resultMap.put("statusType", "推送修改");
                    break;
                case 3:
                    resultMap.put("statusType", "推送取消");
                    break;
                case 4:
                    resultMap.put("statusType", "推送作废");
                    break;
                default:
                    resultMap.put("statusType", state);
                    break;
            }

            state = tInvoicesDispItem.getSuccess();
            switch (state) {
                case 0:
                    resultMap.put("success", "待推送");
                    break;
                case 1:
                    resultMap.put("success", "成功");
                    break;
                default:
                    resultMap.put("success", state);
                    break;
            }

            time = tInvoicesDispItem.getAddTime();
            dateTime = DateFormatUtil.getCurrentDateWithFormat(
                    new Date(Long.parseLong(time) * 1000), "yyyy-MM-dd hh:mm:ss");
            resultMap.put("addTime", dateTime);
            resultMap.put("tryNum", tInvoicesDispItem.getTryNum());
            state = tInvoicesDispItem.getElectricFlag();
            switch (state) {
                case 0:
                    resultMap.put("electricFlag", "纸质发票");
                    break;
                case 1:
                    resultMap.put("electricFlag", "电子发票");
                    break;
                default:
                    resultMap.put("electricFlag", state);
                    break;
            }
        }
        return resultMap;
    }

    @Override
    public Integer getOpListByCOrderSnCount(Map<String, Object> paramMap) {
        return shopOrderProductsService.getOpListByCOrderSnCount(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOpListByCOrderSnSearch(Map<String, Object> paramMap) {
        return shopOrderProductsService.getOpListByCOrderSnSearch(paramMap);
    }

    @Override
    public List<Map<String, Object>> getOpListByCOrderSn(Map<String, Object> paramMap) {
        return shopOrderProductsService.getOpListByCOrderSn(paramMap);
    }

    @Override
    public List<Map<String, Object>> getChannelNames() {
        return stockInvChannel2OrderSourceService.getChannelNames();
    }

    @Override
    public String getSapChannelCode(String orderSource) {
        String ChannelCode = "";
        if (orderSource == null || orderSource.trim().equals("")) {
            logger.error("获取sap渠道码：参数orderSource为空！调用参数：" + orderSource);
            return "获取sap渠道码：参数orderSource为空!";
        }
        try {
            InvChannel2OrderSource invc2os = stockInvChannel2OrderSourceService
                    .getSapChannelCodeAndCustomerCode(orderSource);
            if (invc2os != null && invc2os.getSapChannelCode() != null
                    && !invc2os.getSapChannelCode().equals("")) {
                ChannelCode = invc2os.getSapChannelCode();
            } else {
                logger.error("获取sap渠道码：调用sap渠道码接口返回渠道码结果为空！调用参数：" + orderSource);
                return "调用sap渠道码接口返回渠道码结果为空!";
            }
        } catch (Exception e) {
            logger.error("获取sap渠道码异常，错误信息：" + e.getMessage());
            return "调用sap渠道码接口时发送异常";
        }
        return ChannelCode;
    }

    @Override
    public int invoiceBatchModify(Map<String, Object> params) {
        return shopInvoiceService.invoiceBatchModify(params);
    }

    @Override
    public String invoiceWwwLogsOperate(String flag, String orderId, String orderProductId, String auditor) {

        //查询是否有订单
        Orders orders = shopOrdersService.get(Integer.parseInt(orderId));
        if (null == orders) {
            logger.warn("3w发票待人工处理界面请求操作发票错误，订单不存在，请求参数：" + orderId);
            return "对不起，您请求的数据不存在";
        }

        String message = "";
        if ("2".equals(flag)) { //开电子发票
            message = billElecInvoice(orders, Integer.parseInt(orderProductId), auditor);
        } else { //不开票
            message = noNeedBillInvoice(Integer.parseInt(orderId));
        }
        return message;
    }

    /**
     * 开电子发票
     *
     * @param orders
     * @param orderProductId
     * @return 成功返回空
     */
    private String billElecInvoice(Orders orders, Integer orderProductId, String auditor) {
        MemberInvoices memberInvoices = shopMemberInvoicesService.getByOrderId(orders.getId());
        if (null == memberInvoices) {
            logger.warn("3w发票待人工处理界面请求操作发票错误，用户发票信息不存在，请求参数：" + orders.getId());
            return "对不起，您请求的数据不存在！";
        }
        InvoicesWwwLogs invoicesWwwLogs = invoicesWwwLogsService.get(orderProductId);
        if (memberInvoices.getInvoiceType() == 1) {
            invoicesWwwLogs.setSuccess(1);
            invoicesWwwLogs.setFlag(1);
            invoicesWwwLogs.setLastMessage("");
            invoicesWwwLogs.setProcessTime((int) (System.currentTimeMillis() / 1000));
            invoicesWwwLogsService.updateInvoiceWwwLogs(invoicesWwwLogs);
            return "对不起，发票类型已设置为增票不能开电子票!";
        }
        if (orders.getOrderType() == OrderType.TYPE_GROUP_ADVANCE_TAIL.getIntValue() && orders.getTailPayTime() == 0) {
            return "对不起，订金尾款订单未付尾款不能开票";
        }
        orders.setSmConfirmStatus(4); //更新订单标建状态为“待自动处理”
        shopOrdersService.updateSmConfirmStatus(orders);

        List<InvoicesWwwLogs> invoicesWwwLogList = invoicesWwwLogsService.getByOrderId(orders.getId());
        try {
            for (InvoicesWwwLogs tempLog : invoicesWwwLogList) {
                String message = invoicesWwwLogsService.billElecInvoice(orders, tempLog, orderProductId, auditor);
                if (!"".equals(message)) {
                    return message;
                }
            }
        } catch (BusinessException e) {
            return "开电子发票发生异常";
        }
        return "";
    }

    /**
     * 不需要开票操作
     *
     * @param orderId
     * @return
     */
    private String noNeedBillInvoice(Integer orderId) {
        List<InvoicesWwwLogs> invoicesWwwLogsList = invoicesWwwLogsService.getByOrderId(orderId);
        for (InvoicesWwwLogs temp : invoicesWwwLogsList) {
            temp.setSuccess(2); //无需处理
            temp.setProcessTime((int) (System.currentTimeMillis() / 1000));
            invoicesWwwLogsService.updateInvoiceWwwLogs(temp);
        }
        return "";
    }
}
