package com.haier.invoice.services;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.invoice.model.SyncElecInvoiceModel;
import com.haier.invoice.model.eai.InvoiceEntity;
import com.haier.invoice.model.eai.QueryInvoiceInputType;
import com.haier.invoice.service.InvoiceCommonService;
import com.haier.invoice.service.InvoiceToTaxService;
import com.haier.invoice.util.DataTypeUtil;
import com.haier.invoice.util.DateFormatUtil;
import com.haier.invoice.util.HttpJsonResult;
import com.haier.invoice.util.OrderSourceName;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    
    @Autowired
    private ProductsService productsService;
    @Autowired
    private ProductTypesService productTypesService;
    @Autowired
    private ShopOrderOperateLogsService shopOrderOperateLogsService;

    @Autowired
    private InvTransferLineSalesService invTransferLineSalesService;
    @Autowired
    private TmypProductDiscountService tmypProductDiscountService;
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
        String[] inputAuditOptions = {"待审核", "审核通过", "审核拒绝","需人工审核"};
        String[] invoiceTypeOptions = {"增值税发票", "普通发票", "增值税发票（普）"};

        //获得MemberInvoices信息
        MemberInvoices memberInvoices = shopMemberInvoicesService.getById(memberInvoiceId);
        if (memberInvoices != null) {
            //去除remark中的转译符号
            String remark = memberInvoices.getRemark();
            String replaceRemark = remark.replace("\n", "");
            memberInvoices.setRemark(replaceRemark);

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
                    //去掉转译符号
                    String eachInvoiceChangeLogsRemark = eachInvoiceChangeLogs.getRemark();
                    String replaceInvoiceChangeLogsRemark  = eachInvoiceChangeLogsRemark.replace("\n", "");
                    eachInvoiceChangeLogs.setRemark(replaceInvoiceChangeLogsRemark);
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
    public String saveMemberInvoices(Integer id, Integer invoiceType, String invoiceTitle, String taxPayerNumber, String registerAddress, String registerPhone, String bankName, String bankCardNumber, Integer state, String remark, String auditor, String userName, String vatremark) {

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
            paramMemberInvoices.setVatremark(vatremark);
            paramMemberInvoices.setAuditor(auditor);
            paramMemberInvoices.setAuditTime(new Date().getTime() / 1000);

            paramMemberInvoices.setTaxPayerNumber(taxPayerNumber);
            paramMemberInvoices.setRegisterAddress(registerAddress);
            paramMemberInvoices.setRegisterPhone(registerPhone);
            paramMemberInvoices.setBankName(bankName);
            paramMemberInvoices.setBankCardNumber(bankCardNumber);
            // 增值税发票的场合
            if (invoiceType != null && invoiceType.equals(1)) {
                paramMemberInvoices.setState(state);
            } else {
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
                                && eachOrderProducts.getIsMakeReceipt().equals(5)
                                && !oldmemberInvoices.getInvoiceType().equals(
                                paramMemberInvoices.getInvoiceType())) {
                            return "对不起，该订单有网单开票中，不能修改发票类型!";
                        }
                    }
                }
            }

            try {
                //更新相关发票、日志业务操作
                shopMemberInvoicesService.saveInvoiceOperate(orderProductsList, paramMemberInvoices, oldmemberInvoices, auditor,userName);
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
    public String getSapChannelCode(String cOrderSn,String orderSource) {
        String ChannelCode = "";
        if (orderSource == null || orderSource.trim().equals("")) {
            logger.error("获取sap渠道码：参数orderSource为空！调用参数：" + orderSource);
            return "获取sap渠道码：参数orderSource为空!";
        }
        try {
            ChannelCode=getChannel( cOrderSn,  orderSource);

//            InvChannel2OrderSource invc2os = stockInvChannel2OrderSourceService
//                    .getSapChannelCodeAndCustomerCode(orderSource);
//            if (invc2os != null && invc2os.getSapChannelCode() != null
//                    && !invc2os.getSapChannelCode().equals("")) {
//                ChannelCode = invc2os.getSapChannelCode();
//            } else {
//                logger.error("获取sap渠道码：调用sap渠道码接口返回渠道码结果为空！调用参数：" + orderSource);
//                return "调用sap渠道码接口返回渠道码结果为空!";
//            }

        } catch (Exception e) {
            logger.error("获取sap渠道码异常，错误信息：" + e.getMessage());
            return "调用sap渠道码接口时发送异常";
        }
        return ChannelCode;
    }


    public String getChannel(String cOrderSn, String source) {
        if (cOrderSn.endsWith("J")) {//京东
            return "DS03";
        }
        if ("HPB2B".equalsIgnoreCase(source)) {//海朋归属大客户渠道
            return "DS03";
        }
        if ("TOPXB".equalsIgnoreCase(source) || "TOPFENXIAOXB".equalsIgnoreCase(source)
                || "TOPDHSC".equalsIgnoreCase(source)) {//新宝归属淘宝渠道
            return "DS02";
        }
        if ("RRS".equalsIgnoreCase(source) || "DXS".equalsIgnoreCase(source)) {//日日顺订单
            return "DS05";
        }
        if ("TONGSHUAI".equals(source) || "TONGSHUAIFX".equals(source)) {//统帅
            return "DS06";
        }
        if ("YIHAODIAN".equalsIgnoreCase(source) || "YHD".equalsIgnoreCase(source)) {//一号店订单
            return "DS11";
        }
        if ("CCBSC".equals(source) || "CCBSR".equals(source) || "95533".equals(source)) {//建行订单
            return "DS12";
        }
        if ("GMZX".equals(source)) {//国美在线订单
            return "DS10";
        }
        if ("YMX".equals(source)) {//亚马逊
            return "DS09";
        }
        if ("YHDZY".equals(source)) {//电商平台-1号店自营
            return "DS15";
        }
        if ("ICBC".equals(source)) {//平台大客户-工商银行
            return "DS13";
        }

        if ("SNYG".equals(source)) {//电商平台-苏宁易购旗舰店
            return "DS16";
        }
        if ("DALOU".equalsIgnoreCase(source)) {//企业订单_大楼订单,2016-08-30新增
            return "DS01";
        }
        //新加的sap渠道码走下面的流程
        InvChannel2OrderSource  result= null;
        try {
            result = stockInvChannel2OrderSourceService
                    .getSapChannelCodeAndCustomerCode(source);
            if (result == null) {
                throw new BusinessException("获取sap渠道码：调用sap渠道码接口时出错！错误信息：result返回结果为null");
            } else {
                if (result.getSapChannelCode() != null
                        && !result.getSapChannelCode().equals("")) {
                    return result.getSapChannelCode();
                }
            }
        } catch (Exception e) {
            logger.error("获取sap渠道码异常," + e.getMessage() + "\n错误信息:", e);
            throw new BusinessException(e.getMessage());
        }

        String channel = result.getChannelCode();


        if (StringUtil.isEmpty(channel)) {
            throw new BusinessException("订单来源对应的渠道不存在：" + source);
        }
        if ("DKH".equals(channel)) {
            return "DS03";
        }
        if ("SC".equals(channel)) {
            return "DS01";
        }
        if ("TB".equals(channel)) {
            return "DS02";
        }
        if ("JD".equals(channel)) {
            return "DS04";
        }
        if ("MK".equalsIgnoreCase(channel)) {
            return "DS07";
        }
        return "";
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

    /**
     //TODO 批量审核订单发票  
     */
	@Override
	public HttpJsonResult<Map<String, Object>> doBatchAuditingOrderInvoice(String cOrderSns,
			Map<String, Object> modelMap,String userName) {

        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        try {
            String[] strTemp = cOrderSns.split("\r\n");
            Set<String> set = new HashSet<String>(Arrays.asList(strTemp));
            String[] cOrderSnsArray = set.toArray(new String[0]);

            StringBuffer sbUF = new StringBuffer();
            StringBuffer sbError = new StringBuffer();
            StringBuffer sbHidden = new StringBuffer();
            StringBuffer sb = new StringBuffer();
            StringBuffer sbSuccess = new StringBuffer();
            List<Orders> ordersList = null;
            Integer total = 0;
            Integer unFindCount = 0;
            Integer errorCount = 0;
            for (String cOrderSn : cOrderSnsArray) {
                //获取开单列表List
            	Orders orders=shopOrdersService.getByOrderSn(cOrderSn.trim());
                if (orders == null) {
                    unFindCount += 1;
                    sbUF.append("订单号").append(cOrderSn).append("未查询到Orders 订单表信息！")
                            .append("<br>");
                    continue;
                }

                ordersList = new ArrayList<Orders>();
                ordersList.add(orders);
                try {
                	MemberInvoices memberInvoices=shopMemberInvoicesService.getById(orders.getMemberInvoiceId());
                    if (memberInvoices == null) {
                    	unFindCount += 1;
                    	sbUF.append(errorCount).append("订单号").append(cOrderSn)
                                .append("同步失败，MemberInvoices表中没有该数据！").append("<br>");
                        continue;
                    }
                    
                    memberInvoices.setState(1);
                    MemberInvoices memberInvoices2=shopMemberInvoicesService.checkPassedValuedInvoice(memberInvoices);
                    if (memberInvoices2 != null) {
                    	List<OrderProducts> orderProductsByOrderId = shopOrderProductsService.getOrderProductsByOrderId(orders.getId());
                    	for (OrderProducts orderProducts : orderProductsByOrderId) {
                    		OrderOperateLogs orderOperateLogs = new OrderOperateLogs();
    						orderOperateLogs.setSiteId(0);
    						orderOperateLogs.setOrderId(orders.getId());
    						orderOperateLogs.setOrderProductId(orderProducts.getId());
    						orderOperateLogs.setNetPointId(orderProducts.getNetPointId());
    						orderOperateLogs.setOperator(userName);
    						orderOperateLogs.setChangeLog("订单手动变成发票审核通过");
    						orderOperateLogs.setRemark("批量手动发票审核通过");
    						long time = new Date().getTime();
    						Integer date = (int)time;
    						orderOperateLogs.setLogTime(date);
    						orderOperateLogs.setStatus(orderProducts.getStatus());
    						orderOperateLogs.setPaymentStatus(orders.getPaymentStatus());
    						shopOrderOperateLogsService.insert(orderOperateLogs);

                            if (memberInvoices.getInvoiceType().equals(1)) {
                                // 将开票类型修改为“共享开票”
                                orderProducts.setMakeReceiptType(2);
                                shopOrderProductsService.updateMakeReceiptType(orderProducts);
                            }

						}


                        memberInvoices.setState(1); // 审核通过
                        memberInvoices.setAuditTime(new Date().getTime() / 1000);
                        memberInvoices.setAuditor("系统");
                        memberInvoices.setRemark("该增票信息先前已存在并审核通过,本订单增票信息自动审核通过");
                        memberInvoices.setParentId(memberInvoices2.getId());
                        this.shopMemberInvoicesService.update(memberInvoices);
                        total += 1;
                        sbSuccess.append("订单号").append(cOrderSn).append("审核成功！")
                                .append("<br>");
                    }else {
                    	unFindCount += 1;
                    	sbUF.append(errorCount).append("订单号").append(cOrderSn)
                                .append("审核失败，该增票信息没有审核通过的单据！").append("<br>");
                        continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("批量审核订单发票时发生未知错误", e);
                    errorCount += 1;
                    sbError.append(errorCount).append("订单号").append(cOrderSn).append("审核异常")
                            .append("<br>");
                    continue;
                }

            }
            sb.append("总计").append(cOrderSnsArray.length).append("条数据！<br>");
            if (total > 0) {
                sb.append(total+"条审核成功！信息如下：<br>").append(sbSuccess);
            }
            if (unFindCount > 0) {
                sb.append(unFindCount).append("条审核失败！信息如下：<br>").append(sbUF);
            }
            if (errorCount > 0) {
                sb.append(errorCount).append("条审核异常！信息如下：<br>").append(sbError);
            }
            result.setMessage(sb.toString());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("error", sbHidden.toString());
            result.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[invoice][doBatchAuditingOrderInvoice]批量审核订单发票时发生未知错误", e);
            result.setMessage("批量审核订单发票失败！");
        }
        return result;
    }

	/**
	 //TODO 批量共享开票
	 */
	@Override
	public HttpJsonResult<Map<String, Object>> doBatchShareInvoice(String cOrderSns, Map<String, Object> modelMap) {

        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        try {
            String[] strTemp = cOrderSns.split("\r\n");
            Set<String> set = new HashSet<String>(Arrays.asList(strTemp));
            String[] cOrderSnsArray = set.toArray(new String[0]);

            StringBuffer sbUF = new StringBuffer();
            StringBuffer sbError = new StringBuffer();
            StringBuffer sbHidden = new StringBuffer();
            StringBuffer sbSuccess = new StringBuffer();
            StringBuffer sb = new StringBuffer();
            List<Invoices> invoicesList = null;
            Integer total = 0;
            Integer unFindCount = 0;
            Integer errorCount = 0;
            for (String cOrderSn : cOrderSnsArray) {
            	try {
                //获取开单列表List
            	OrderProducts orderProducts=shopOrderProductsService.getByCOrderSn(cOrderSn.trim());
                if (orderProducts == null) {
                    unFindCount += 1;
                    sbUF.append("网单号").append(cOrderSn).append("未查询到网单信息！")
                            .append("<br>");
                    continue;
                }
                Orders o= shopOrdersService.get(orderProducts.getOrderId());
                if(o==null) {
                	unFindCount += 1;
                    sbUF.append("网单号").append(cOrderSn).append("未查询到对应的订单信息{Order表}！")
                            .append("<br>");
                    continue;
                }
                if (o.getMemberInvoiceId() == 0) {
                    unFindCount += 1;
                    sbUF.append("网单号").append(cOrderSn).append("网单.").append(orderProducts.getCOrderSn()).append("原发票信息没转换！")
                            .append("<br>");
                    continue;
                }
                if (orderProducts.getIsReceipt() == 0) {
                	unFindCount += 1;
                    sbUF.append("网单号").append(cOrderSn).append("isReceipt字段=0！")
                            .append("<br>");
                    continue;
                }
                if(orderProducts.getStatus() != 130){
                	unFindCount += 1;
                    sbUF.append("网单号").append(cOrderSn).append("网单不是完成关闭，不能开票！")
                            .append("<br>");
                    continue;
                }
//                if(orderProducts.getId() < 826615){
//                	unFindCount += 1;
//                    sbUF.append("网单号").append(cOrderSn).append("2013年8月1号之前网单不能开票！")
//                            .append("<br>");
//                    continue;
//                }

                if(orderProducts.getIsMakeReceipt()==InvoiceConst.MR_STATE_CLOSE.intValue()){
                    unFindCount += 1;
                    sbUF.append("网单号").append(cOrderSn).append("已封存网单网单不能开票！")
                            .append("<br>");
                    continue;
                }
                if("OJO".equals(orderProducts.getStockType()) || "O2O".equals(orderProducts.getStockType()) || "STORE".equals(orderProducts.getStockType())){
	                    unFindCount += 1;
	                    sbUF.append("网单号").append(cOrderSn).append("类型为").append(orderProducts.getStockType()).append("网单不能开票！")
	                            .append("<br>");
	                    continue;
                }
            	Invoices i=shopInvoiceService.getInvoicesByCOrderSn(orderProducts.getcOrderSn());
            	if(i!=null&&!"3".equals(i.getEaiWriteState())&&!"4".equals(i.getEaiWriteState())) {
                //if(i!=null &&  i.getState()!=3 && i.getState()!=4) {
                	unFindCount += 1;
                    sbUF.append("网单号").append(cOrderSn).append("网单为").append(orderProducts.getcOrderSn()).append("在金税已存在！")
                            .append("<br>");
                    continue;
                }
                List<OrderRepairs> repairsList=shopInvoiceService.getOrderRepairsByOrderProductId(orderProducts.getId());
                if(repairsList!=null && repairsList.size()>0) {
                    boolean isRepair=false;
                    for (OrderRepairs orderRepair:repairsList) {
                        if(orderRepair.getHandleStatus()!=5&&orderRepair.getHandleStatus()!=6){ //排除 已终止和已驳回的
                            isRepair=true;
                            break;
                        }

                    }
                    if(isRepair) {
                        unFindCount += 1;
                        sbUF.append("网单号").append(cOrderSn).append("网单为").append(orderProducts.getcOrderSn()).append("已产生逆向单不能开增票！")
                                .append("<br>");
                        continue;
                    }

                }
              //-------------------------------------------------------------------
                if(OrderType.TYPE_GROUP_TAIL.getIntValue()==o.getOrderType()) {
                	unFindCount += 1;
                    sbUF.append("网单号").append(cOrderSn).append("网单为").append(orderProducts.getcOrderSn()).append("为尾款订单不能开票！")
                            .append("<br>");
                    continue;
                }
                MemberInvoices m=shopMemberInvoicesService.getById(o.getMemberInvoiceId());
                if(m!=null) {
                	if(m.getInvoiceType()==1) {

                        if("".equals(m.getTaxPayerNumber())){
                            unFindCount += 1;
                            sbUF.append("网单号").append(cOrderSn).append("网单为").append(orderProducts.getcOrderSn()).append("税号为空！")
                                    .append("<br>");
                            continue;
                        }
                        if(m.getTaxPayerNumber().length() !=15 && m.getTaxPayerNumber().length() !=18 && m.getTaxPayerNumber().length() !=17 && m.getTaxPayerNumber().length() !=20) {
                        	unFindCount += 1;
                            sbUF.append("网单号").append(cOrderSn).append("发票信息不完整，税票号长度不对，请核对！")
                                    .append("<br>");
                            continue;
                        }
                        if("".equals(m.getRegisterAddress())) {
                        	unFindCount += 1;
                            sbUF.append("网单号").append(cOrderSn).append("注册地址为空！")
                                    .append("<br>");
                            continue;
                        }
                        if("".equals(m.getRegisterPhone())) {
                        	unFindCount += 1;
                            sbUF.append("网单号").append(cOrderSn).append("注册电话为空！")
                                    .append("<br>");
                            continue;
                        }
                        if("".equals(m.getBankName())) {
                        	unFindCount += 1;
                            sbUF.append("网单号").append(cOrderSn).append("注册银行为空！")
                                    .append("<br>");
                            continue;
                        }
                        if("".equals(m.getBankCardNumber())) {
                        	unFindCount += 1;
                            sbUF.append("网单号").append(cOrderSn).append("银行帐号为空！")
                                    .append("<br>");
                            continue;
                        }
                        if(m.getState()==0) {
                        	unFindCount += 1;
                            sbUF.append("网单号").append(cOrderSn).append("增值税信息尚未审核！")
                                    .append("<br>");
                            continue;
                        }else if(m.getState()==2) {
                        	unFindCount += 1;
                            sbUF.append("网单号").append(cOrderSn).append("增值税信息审核拒绝通过！")
                                    .append("<br>");
                            continue;
                        }
                	}
                	if(m.getInvoiceType()==3) {
                		if(m.getState()==0) {
                			unFindCount += 1;
                            sbUF.append("网单号").append(cOrderSn).append("增普信息尚未审核！")
                                    .append("<br>");
                            continue;
                		}else if(m.getState()==2) {
                			unFindCount += 1;
                            sbUF.append("网单号").append(cOrderSn).append("增普信息审核拒绝通过！")
                                    .append("<br>");
                            continue;
                		}
                	}
                	
                	// 组织开票方法 
                	Invoices saveInvoices=AssemblyInvoiceInfo(o, orderProducts, m);
                	//如果增值税发票备注不为空 附上此备注
                	if(!StringUtil.isEmpty(m.getVatremark(),true)){
                        saveInvoices.setRemark(m.getVatremark());
                    }
                	shopInvoiceService.insertInvoices(saveInvoices);

                	total+=1;
                    sbSuccess.append("网单号").append(cOrderSn).append("推送成功！")
                            .append("<br>");
                	
                }else {
                	unFindCount += 1;
                    sbUF.append("网单号").append(cOrderSn).append("MemberInvoices 表中没有数据！")
                            .append("<br>");
                    continue;
                }
                
            	}catch (Exception e) {
            	    e.printStackTrace();
            	    logger.error("批量共享开票异常，"+cOrderSn,e);
            		errorCount+=1;
            		sbError.append(errorCount).append("网单号").append(cOrderSn).append("异常：").append(e.toString()).append("<br>");
            		 continue;
				}
	        }

         
           
            sb.append("总计").append(cOrderSnsArray.length).append("条数据！<br>");
            if (total > 0) {
                sb.append(total+"条同步成功！信息如下：<br>").append(sbSuccess);
            }
            if (unFindCount > 0) {
                sb.append(unFindCount+"条推送失败!信息如下：<br>").append(sbUF);
            }
            if (errorCount > 0) {
                sb.append(errorCount).append("条推送异常!信息如下：<br>").append(sbError);
            }
            result.setMessage(sb.toString());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("error", sbHidden.toString());
            result.setData(map);
        } catch (Exception e) {
            logger.error("[invoice][doBatchShareInvoice]批量共享开票时发生未知错误", e);
            result.setMessage("批量共享开票失败！");
        }
        return result;
	}



    /**
     * 组织发票信息
     *
     * @param order
     * @param op
     * @param mi
     * @return
     */
    private Invoices AssemblyInvoiceInfo(Orders order, OrderProducts op, MemberInvoices mi) {
        if (order == null || op == null || mi == null) {
            logger.error("组装发票信息：订单、网单、订单发票对象都不能为null");
            return null;
        }
        //已开过票，不需要开票
        List<Invoices> invList =shopInvoiceService.getByOrderProductId(op.getId());
        if (invList != null && invList.size() > 0) {
            for (Invoices inv : invList) {
                if (inv.getState().equals(0)//待开票
                        || inv.getState().equals(1)//开票中,商城初次开票推送给EAI的值也为此值
                        || (inv.getState().equals(4) && StringUtil.isEmpty(inv.getEaiWriteState()))) {//已开票,金税开完税后，回写EAI,EAI回写商城这边的值也为此值
                	logger.info("此网单不能开票，原因：该网单已在开票中或已开票，网单号：" + op.getCOrderSn());
                    return null;
                }
            }
        }
        //获取产品对象
        Products product = productsService.getBySku(op.getSku());
        if (product == null) {
            logger.error("组装发票信息：产品不存在，sku（" + op.getSku() + "）");
            return null;
        }
        //获取产品类型
        ProductTypesNew productType = productTypesService.getById(product.getProductTypeId());
        		

        //计算开票金额小计
        BigDecimal amount = op.getProductAmount();

        //2014-6-4 金额为零的所有网单，改为开1分钱发票 Benio
        //处理赠品，1分钱
        if (amount.compareTo(BigDecimal.ZERO) == 0
                || amount.divide(new BigDecimal(op.getNumber()), 2, RoundingMode.HALF_UP)
                .compareTo(BigDecimal.ZERO) == 0) {
            BigDecimal price = new BigDecimal("0.01");
            amount = price.multiply(new BigDecimal(op.getNumber()));
        }

        //节能补贴逻辑的金额计算，节能补贴描述
        BigDecimal energySubsidyAmount = BigDecimal.ZERO;//节能补贴金额
        String esRremark = "";//节能补贴描述
        if (op.getEsAmount().compareTo(BigDecimal.ZERO) > 0) {
            energySubsidyAmount = op.getEsAmount();
            esRremark = String.format("实收%s已补%s", amount, energySubsidyAmount);//实收等于小计-节能补贴
        }

        //计算新网单号
        String sn = op.getCOrderSn();
        Boolean isReInvoice = false;

        //重开发票的情况，重新处理网单号
        if (invList != null && invList.size() > 0) {
            //序列号,不足2位补0,超过2位取末尾2位
            String seq = String.format("%02d", invList.size());
            int len = seq.length();
            if (len > 2) {
                seq = seq.substring(len - 2);
            }
            if (mi.getElectricFlag().equals(1)) {
                sn = op.getCOrderSn() + "R" + seq;
            } else {
                sn = op.getCOrderSn() + "_" + seq;
            }
            isReInvoice = true;
        }

        //电子钱包支付标志
        Boolean isUsedEWallet = false;
        if ("1".equalsIgnoreCase(order.getSource())
                && "lejia".equalsIgnoreCase(order.getPaymentCode())) {
            //使用了电子钱包支付
            isUsedEWallet = true;
        }

        //由于使用了电子钱包和礼品卡，修改备注  - 2012-12-20 Benio
        String remark = "";//发票备注
        if (isUsedEWallet) {
            remark = sn + "(D)";
        } else {
            remark = sn;
        }

        //计算是否货票同行
        Integer isTogether = 2;//货票同行
//        if (isReInvoice || op.getMakeReceiptType().equals(2)) {//重开发票，或者中心开票
//            isTogether = 2;//非货票同行
//        }

        //计算开票库位
        String sCode = op.getSCode();
        if (!StringUtil.isEmpty(op.getTsCode()) && !op.getSCode().equals(op.getTsCode())) {
            //如果是转运网单，则将发票开在转运库
            sCode = op.getTsCode();
        }

        //组织发票信息
        Invoices invoice = new Invoices();
        invoice.setIsOld(0);
        invoice.setIsReInvoice(isReInvoice ? 1 : 0);
        invoice.setCOrderSn(sn);
        invoice.setRemark(remark);
        invoice.setProductName(op.getEsAmount().compareTo(BigDecimal.ZERO) > 0 ? product
                .getEnergySubsidyProductName() : op.getProductName());
        invoice.setProductCateName(productType == null ? "" : productType.getTypeName());
        invoice.setPrice(amount.divide(new BigDecimal(op.getNumber()), 2, RoundingMode.HALF_UP));
        invoice.setAmount(amount);
        invoice.setEnergySavingAmount(energySubsidyAmount);
        invoice.setOrderProductId(op.getId());
        invoice.setCOrderType(1);// 普通正常网单
        invoice.setDiffId(0);//
        invoice.setCustomerCode("00002001");
        invoice.setMemberInvoiceId(mi.getId());
        invoice.setInvoiceTitle(mi.getInvoiceTitle());
        invoice.setTaxPayerNumber(mi.getTaxPayerNumber());
        invoice.setRegisterAddressAndPhone(mi.getRegisterAddress() + mi.getRegisterPhone());
        invoice.setBankNameAndAccount(mi.getBankName() + mi.getBankCardNumber());
        invoice.setSku(op.getSku());
        invoice.setUnit("台");
        invoice.setCOrderAddTime(order.getAddTime().toString());
        invoice.setNumber(DataTypeUtil.longToInteger(op.getNumber()));
//        invoice.setNumber(op.getNumber());
        //modify by mengchong修改税率0.16
        invoice.setTaxRate(new BigDecimal(0.16));
        invoice.setNonTaxPrice(invoice.getPrice().divide(new BigDecimal(1.16), 2,
                RoundingMode.HALF_UP));
        invoice.setNonTaxAmount(invoice.getAmount().divide(new BigDecimal(1.16), 2,
                RoundingMode.HALF_UP));
        invoice.setTaxAmount(invoice.getAmount().subtract(invoice.getNonTaxAmount()));
        invoice.setType(mi.getInvoiceType());//1增票，2电子，3普票（普税纸质）2015-09-28
        invoice.setIsTogether(isTogether);
        invoice.setState(0);
        invoice.setLessOrderSn(op.getLessOrderSn());
        invoice.setPaymentName(order.getPaymentName());
        invoice.setSCode(sCode);
        invoice.setOrderType("ZBCC");
        invoice.setInvoiceNumber("");
        invoice.setBillingTime(0L);
        invoice.setEaiWriteTime(0L);
        invoice.setDrawer("");
        invoice.setEaiWriteState("");
        invoice.setInvalidTime(0L);
        invoice.setFirstPushTime(0);
        invoice.setLastModifyTime(0);
        invoice.setInternalSettlement("");
        //2016-7-15 添加发票码
        invoice.setBranchOfficeCode(product.getConTaxCode());
        invoice.setOrderLineNumber("");
        invoice.setBackupFieldA("");
        if(OrderSourceName.getByValue(order.getSource())==null){
            invoice.setBackupFieldB("ORIGIN_"+order.getSource());
        }else{
            invoice.setBackupFieldB(OrderSourceName.getByValue(order.getSource()).toString());
        }
        invoice.setIntegralAmount(BigDecimal.ZERO);
        invoice.setEnergySavingRemark(esRremark);
        invoice.setStatusType(InvoiceConst.ADD_KIND);//首次推送
        invoice.setSuccess(0);
        invoice.setAddTime(new Date().getTime() / 1000);
        invoice.setTryNum(0);
        invoice.setInvalidReason("");

        /* 如果含税单价乘以数量不等于含税金额，则分摊含税单价，同时更新价格金额其它信息 */
        if (invoice.getPrice().multiply(new BigDecimal(invoice.getNumber()))
                .compareTo(invoice.getAmount()) != 0) {
            invoice.setPrice(invoice.getAmount().divide(new BigDecimal(invoice.getNumber()), 2,
                    RoundingMode.HALF_UP));
            invoice.setNonTaxPrice(invoice.getPrice().divide(new BigDecimal(1.16), 2,
                    RoundingMode.HALF_UP));
            invoice.setNonTaxAmount(invoice.getAmount().divide(new BigDecimal(1.16), 2,
                    RoundingMode.HALF_UP));
            invoice.setTaxAmount(invoice.getAmount().subtract(invoice.getNonTaxAmount()));
        }

        //电子发票，2013-6-30   2015-09-28纸质普票转成电子发票（原来开票时会转）
        invoice.setElectricFlag(mi.getElectricFlag() != null && mi.getElectricFlag().equals(1) ? 1
                : (mi.getInvoiceType() != null && mi.getInvoiceType().equals(2) ? 1 : 0));

        return invoice;
    }

    /**
     * 天猫税控码查询(开票列表)
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> getTianMaoFiscalCodeListByPage(Map<String, Object> params) {
        //获取开单列表List
        Map<String, Object> retMap = shopInvoiceService.getTianMaoFiscalCodeListByPage(params);
        return retMap;
    }

    /**
     * 查询导出发票信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> getExportTianMaoFiscalCodeList(Map<String, Object> params) {
        return shopInvoiceService.getExportTianMaoFiscalCodeList(params);

    }

    /**
     * 税控码查询
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> getFiscalCodeListByPage(Map<String, Object> params) {
        //获取开单列表List
        Map<String, Object> retMap = shopInvoiceService.getFiscalCodeListByPage(params);
        return retMap;
    }

    /**
     * 批量推送共享优品发票
     * @param
     * @return
     */
    @Override
    public HttpJsonResult<Map<String, Object>> doBatchPushShareYoupinInvoice(String line_nums) {

        HttpJsonResult<Map<String, Object>> result = new HttpJsonResult<Map<String, Object>>();
        try {

            String[] strTemp = line_nums.split("\r\n");
            Set<String> set = new HashSet<String>(Arrays.asList(strTemp));
            String[] line_numsArray = set.toArray(new String[0]);

            StringBuffer sbUF = new StringBuffer();
            StringBuffer sbError = new StringBuffer();
            StringBuffer sbHidden = new StringBuffer();
            StringBuffer sbSuccess = new StringBuffer();
            StringBuffer sb = new StringBuffer();
            Integer total = 0;
            Integer unFindCount = 0;
            Integer errorCount = 0;

            for (String line_num : line_numsArray) {
                try {
                    InvTransferLineSales invTransferLineSales = invTransferLineSalesService.getByLine__num(line_num.trim());
                    if (invTransferLineSales == null) {
                        unFindCount += 1;
                        sbUF.append("调拨网单号码:").append(line_num).append("未查询到！").append("<br>");
                        continue;
                    }

                    if(invTransferLineSales.getInvoiceState() == 1){
                        unFindCount += 1;
                        sbUF.append("优品单号").append(line_num).append("正在开票中优品单不能开票！")
                                .append("<br>");
                        continue;
                    }
                    if(invTransferLineSales.getInvoiceState()==InvoiceConst.MR_STATE_CLOSE.intValue()){
                        unFindCount += 1;
                        sbUF.append("优品单号").append(line_num).append("已封存网单网单不能开票！")
                                .append("<br>");
                        continue;
                    }
                    if(invTransferLineSales.getInvoiceState() == InvoiceConst.MR_STATE_MAKED.intValue()){
                        unFindCount += 1;
                        sbUF.append("优品单号").append(line_num).append("已开票优品单不能开票！")
                                .append("<br>");
                        continue;
                    }
                    Invoices i = shopInvoiceService.getInvoicesByCOrderSn(invTransferLineSales.getLineNum());
                    if (i != null && !"3".equals(i.getEaiWriteState()) && !"4".equals(i.getEaiWriteState())) {
                        //if(i!=null &&  i.getState()!=3 && i.getState()!=4) {
                        unFindCount += 1;
                        sbUF.append("调拨网单号码:").append(line_num).append("天猫优品单号为").append(invTransferLineSales.getLineNum()).append("在金税已存在！").append("<br>");
                        continue;
                    }
                    //TODO修改: 固定的 memberinvoices测试库 id = 2579061 ,生产上2579061
                    String invoiceTitle = "天津天猫电子商务有限公司";
                    MemberInvoices m = shopMemberInvoicesService.getMemberInvoiceByInvoiceTitleForYoupin(invoiceTitle);
                    if (m != null) {
                        if (m.getInvoiceType() == 1) {
                            if ("".equals(m.getTaxPayerNumber())) {
                                unFindCount += 1;
                                sbUF.append("调拨网单号码").append(line_num).append("天猫优品单号为").append(invTransferLineSales.getLineNum()).append("税号为空！").append("<br>");
                                continue;
                            }
                            if (m.getTaxPayerNumber().length() != 15 && m.getTaxPayerNumber().length() != 18 && m.getTaxPayerNumber().length() != 17 && m.getTaxPayerNumber().length() != 20) {
                                unFindCount += 1;
                                sbUF.append("调拨网单号码:").append(line_num).append("发票信息不完整，税票号长度不对，请核对！").append("<br>");
                                continue;
                            }
                            if ("".equals(m.getRegisterAddress())) {
                                unFindCount += 1;
                                sbUF.append("调拨网单号码:").append(line_num).append("注册地址为空！").append("<br>");
                                continue;
                            }
                            if ("".equals(m.getRegisterPhone())) {
                                unFindCount += 1;
                                sbUF.append("调拨网单号码").append(line_num).append("注册电话为空！").append("<br>");
                                continue;
                            }
                            if ("".equals(m.getBankName())) {
                                unFindCount += 1;
                                sbUF.append("调拨网单号码").append(line_num).append("注册银行为空！").append("<br>");
                                continue;
                            }
                            if ("".equals(m.getBankCardNumber())) {
                                unFindCount += 1;
                                sbUF.append("调拨网单号码").append(line_num).append("银行帐号为空！").append("<br>");
                                continue;
                            }
                            if (m.getState() == 0) {
                                unFindCount += 1;
                                sbUF.append("调拨网单号码").append(line_num).append("增值税信息尚未审核！").append("<br>");
                                continue;
                            } else if (m.getState() == 2) {
                                unFindCount += 1;
                                sbUF.append("调拨网单号码").append(line_num).append("增值税信息审核拒绝通过！").append("<br>");
                                continue;
                            }
                        }
                        //查询折扣率
                        TmypProductDiscount tmypProductDiscount = tmypProductDiscountService.getTmyp_ProductDiscountBySKU(invTransferLineSales.getItemCode());
                        if(tmypProductDiscount == null){
                            unFindCount += 1;
                            sbUF.append("调拨网单号码:").append(line_num).append("未查询对应的折扣率！").append("<br>");
                            continue;
                        }
                        // 组织开票方法
                        Invoices saveInvoices = this.AssemblyYoupinInvoiceInfo(invTransferLineSales, m ,tmypProductDiscount);
                        //如果增值税发票备注不为空 附上此备注
                        if (!StringUtil.isEmpty(m.getVatremark(), true)) {
                            saveInvoices.setRemark(m.getVatremark());
                        }
                        shopInvoiceService.insertInvoices(saveInvoices);
                        total += 1;
                        sbSuccess.append("调拨网单号码").append(line_num).append("推送成功！").append("<br>");
                    } else {
                        unFindCount += 1;
                        sbUF.append("调拨网单号码").append(line_num).append("MemberInvoices 表中没有数据！").append("<br>");
                        continue;
                    }

            }catch(Exception e){
                    e.printStackTrace();
                    logger.error("批量推送共享优品发票异常，"+line_num,e);
                    errorCount+=1;
                    sbError.append(errorCount).append("调拨网单号码").append(line_num).append("异常：").append(e.toString()).append("<br>");
                    continue;
            }
        }
        sb.append("总计").append(line_numsArray.length).append("条数据！<br>");
        if (total > 0) {
            sb.append(total+"条同步成功！信息如下：<br>").append(sbSuccess);
        }
        if (unFindCount > 0) {
            sb.append(unFindCount+"条推送失败!信息如下：<br>").append(sbUF);
        }
        if (errorCount > 0) {
            sb.append(errorCount).append("条推送异常!信息如下：<br>").append(sbError);
        }
        result.setMessage(sb.toString());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("error", sbHidden.toString());
        result.setData(map);
    } catch (Exception e) {
        logger.error("[invoice][doBatchShareInvoice]批量推送共享优品发票时发生未知错误", e);
        result.setMessage("批量推送共享优品发票失败！");
    }

        return result;
    }

    /**
     * 组织优品发票信息
     * @param invTransferLineSales
     * @param mi
     * @return
     */
    private Invoices AssemblyYoupinInvoiceInfo(InvTransferLineSales invTransferLineSales, MemberInvoices mi ,TmypProductDiscount tmypProductDiscount) {
        if (invTransferLineSales == null || mi == null || tmypProductDiscount == null) {
            logger.error("组装发票信息：天猫优品对象、订单发票对象,天猫优品折扣率对象 都不能为null");
            return null;
        }
        List<Invoices> invList =shopInvoiceService.getByDiffId(invTransferLineSales.getId());

        if (invList != null && invList.size() > 0) {
            for (Invoices inv : invList) {
                if (inv.getState().equals(0)//待开票
                        || inv.getState().equals(1)//开票中,商城初次开票推送给EAI的值也为此值
                        || (inv.getState().equals(4) && StringUtil.isEmpty(inv.getEaiWriteState()))) {//已开票,金税开完税后，回写EAI,EAI回写商城这边的值也为此值
                    logger.info("不能开票，原因：已在开票中或已开票，天猫优品单号：" + invTransferLineSales.getLineNum());
                    return null;
                }
            }
        }
        BigDecimal amount = invTransferLineSales.getSalesAmounts();
        //2014-6-4 金额为零的所有网单，改为开1分钱发票 Benio
        //处理赠品，1分钱
        if (amount.compareTo(BigDecimal.ZERO) == 0
                || amount.divide(new BigDecimal(invTransferLineSales.getTransferQty()), 2, RoundingMode.HALF_UP)
                .compareTo(BigDecimal.ZERO) == 0) {
            BigDecimal price = new BigDecimal("0.01");
            amount = price.multiply(new BigDecimal(invTransferLineSales.getTransferQty()));
        }
        //计算新网单号
        String sn = invTransferLineSales.getLineNum();
        Boolean isReInvoice = false;

        //重开发票的情况，重新处理网单号
        if (invList != null && invList.size() > 0) {
            //序列号,不足2位补0,超过2位取末尾2位
            String seq = String.format("%02d", invList.size());
            int len = seq.length();
            if (len > 2) {
                seq = seq.substring(len - 2);
            }
            if (mi.getElectricFlag().equals(1)) {
                sn = invTransferLineSales.getLineNum() + "R" + seq;
            } else {
                sn = invTransferLineSales.getLineNum() + "_" + seq;
            }
            isReInvoice = true;
        }

        //组织发票信息
        Invoices invoice = new Invoices();
        //是否是从原开票中间表导过来的数据，如果是导过来的此值为1，但不可再传金税开票
        invoice.setIsOld(0);
        //是否是重新开票
        invoice.setIsReInvoice(isReInvoice ? 1 : 0);
        //网单编号cOrderSn存放line_num
        invoice.setCOrderSn(sn);
        //备注
        invoice.setRemark("");
        //商品名称productName
        invoice.setProductName(tmypProductDiscount.getProductName());
        //商品分类
        invoice.setProductCateName(tmypProductDiscount.getProductType());
        // '含税价(HSDJ)取自网单表中的price',
        invoice.setPrice(new BigDecimal(0));
        /*
          原价Amount:计算方法：开票额/(1-折扣率)
           */
        //开票额
       // BigDecimal salesAmounts = invTransferLineSales.getSalesAmounts();
        //折扣率discount
        BigDecimal  one = new BigDecimal("1.00");
        BigDecimal discount = tmypProductDiscount.getDiscount();
        BigDecimal a = one.setScale(2,BigDecimal.ROUND_HALF_DOWN);

        BigDecimal b = discount.setScale(2,BigDecimal.ROUND_HALF_DOWN);
        if(a.equals(b)){
            invoice.setAmount(amount);
        }else {
            BigDecimal subtract = one.subtract(discount);
             amount = amount.divide(subtract,BigDecimal.ROUND_CEILING);
            invoice.setAmount(amount);
        }
        invoice.setEnergySavingAmount(BigDecimal.ZERO);
        //网单ID
        invoice.setOrderProductId(0);
        //cOrderType字段增加枚举（ 4：优品开票）
        invoice.setCOrderType(Invoices.CORDERTYPE_YOUPIN_CORDER_TYPE);
        //差异网单ID
        invoice.setDiffId(invTransferLineSales.getId());
        //客户编码(对应KHBM)目前不管是普票还是增票都默认传00002001'
        invoice.setCustomerCode("00002001");
        //会员发票信息ID,MemberInvoices表的主键
        invoice.setMemberInvoiceId(mi.getId());
        //发票抬头
        invoice.setInvoiceTitle(mi.getInvoiceTitle());
        //纳税人识别号
        invoice.setTaxPayerNumber(mi.getTaxPayerNumber());
        //注册地址和电话
        invoice.setRegisterAddressAndPhone(mi.getRegisterAddress() + mi.getRegisterPhone());
        //开户银行
        invoice.setBankNameAndAccount(mi.getBankName() + mi.getBankCardNumber());
        //sku物料编码
        invoice.setSku(invTransferLineSales.getItemCode());
        //计量单位
        invoice.setUnit("台");
        //网单生成时间
        long cOrderAddTime = invTransferLineSales.getCreateTime().getTime() / 1000;
        invoice.setCOrderAddTime(String.valueOf(cOrderAddTime));
        //数量(CPSL)
        invoice.setNumber(invTransferLineSales.getTransferQty());
        invoice.setTaxRate(new BigDecimal(0.16));
        invoice.setNonTaxPrice(invoice.getPrice().divide(new BigDecimal(1.16), 2,
                RoundingMode.HALF_UP));
        invoice.setNonTaxAmount(invoice.getAmount().divide(new BigDecimal(1.16), 2,
                RoundingMode.HALF_UP));
        //税额
        invoice.setTaxAmount(invoice.getAmount().subtract(invoice.getNonTaxAmount()));
        //发票类型:1增票，2电子，3普票（普税纸质）2015-09-28
        invoice.setType(mi.getInvoiceType());
        //是否货票同行(HPTX)1-是,2-否'
        invoice.setIsTogether(2);
        //发票状态
        invoice.setState(0);
        //less订单号(WLNO)LES返回的以10和79开票的单号  ??
        invoice.setLessOrderSn("");
        //付款方式??
        invoice.setPaymentName("");
        //库位编码 ??
        invoice.setSCode(invTransferLineSales.getSecTo());

        invoice.setOrderType("ZBCC");
        invoice.setInvoiceNumber("");
        invoice.setBillingTime(0L);
        invoice.setEaiWriteTime(0L);
        invoice.setDrawer("");
        invoice.setEaiWriteState("");
        invoice.setInvalidTime(0L);
        invoice.setFirstPushTime(0);
        invoice.setLastModifyTime(0);
        invoice.setInternalSettlement("");
        //2016-7-15 添加发票码
        invoice.setBranchOfficeCode("");
        invoice.setOrderLineNumber("");
        invoice.setBackupFieldA("");

        //BackupFieldB 备用字段1(ADD2)默认为空，目前该字段专门当作“订单来源”传给金税??
        invoice.setBackupFieldB("TMYP");

        //integralAmount: 开票额/(1-折扣率)*discount
        if(a.equals(b)){
            invoice.setIntegralAmount(BigDecimal.ZERO);
        }else {
            BigDecimal number1 = one.subtract(discount);
            BigDecimal divide1 = amount.divide(number1,BigDecimal.ROUND_CEILING);
            BigDecimal internalAmount = divide1.multiply(discount);
            invoice.setIntegralAmount(internalAmount);
        }


        //节能补贴备注
        invoice.setEnergySavingRemark("");
        invoice.setStatusType(InvoiceConst.ADD_KIND);//首次推送
        invoice.setSuccess(0);
        invoice.setAddTime(System.currentTimeMillis() / 1000);
        invoice.setTryNum(0);
        invoice.setInvalidReason("");

        /* 如果含税单价乘以数量不等于含税金额，则分摊含税单价，同时更新价格金额其它信息 */
        if (invoice.getPrice().multiply(new BigDecimal(invoice.getNumber()))
                .compareTo(invoice.getAmount()) != 0) {
            invoice.setPrice(invoice.getAmount().divide(new BigDecimal(invoice.getNumber()), 2,
                    RoundingMode.HALF_UP));
            invoice.setNonTaxPrice(invoice.getPrice().divide(new BigDecimal(1.16), 2,
                    RoundingMode.HALF_UP));
            invoice.setNonTaxAmount(invoice.getAmount().divide(new BigDecimal(1.16), 2,
                    RoundingMode.HALF_UP));
            invoice.setTaxAmount(invoice.getAmount().subtract(invoice.getNonTaxAmount()));
        }
        //电子发票，2013-6-30   2015-09-28纸质普票转成电子发票（原来开票时会转）
        invoice.setElectricFlag(mi.getElectricFlag() != null && mi.getElectricFlag().equals(1) ? 1
                : (mi.getInvoiceType() != null && mi.getInvoiceType().equals(2) ? 1 : 0));

        return invoice;
    }
}
