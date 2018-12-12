package com.haier.afterSale.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.afterSale.model.InvoiceModel;
import com.haier.afterSale.model.MakeOutInvoiceModel;
import com.haier.afterSale.service.InvoiceNewService;
import com.haier.afterSale.util.DateFormatUtil;
import com.haier.common.ServiceResult;
import com.haier.shop.model.Invoices;
import com.haier.shop.model.OrdersNew;
@Service
public class InvoiceNewServiceImpl implements InvoiceNewService {
    private static Logger         log    = LoggerFactory.getLogger(InvoiceNewServiceImpl.class);
    @Autowired
    private InvoiceModel          invoiceModel;
    @Autowired
    private MakeOutInvoiceModel   makeOutInvoiceModel;
    private static final String[] SOURCE = { "", "TBSC", "a" };

    @Override
    public ServiceResult<Boolean> createInvoice() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            invoiceModel.createInvoice();

            result.setMessage("");
            result.setSuccess(true);
            result.setResult(true);
        } catch (Exception e) {
            log.error("生成发票时, 发生未知异常：", e);
            result.setMessage("服务器发生异常：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(false);
        }
        return result;
    }

    public void setMakeOutInvoiceModel(MakeOutInvoiceModel makeOutInvoiceModel) {
        this.makeOutInvoiceModel = makeOutInvoiceModel;
    }

    public void setInvoiceModel(InvoiceModel invoiceModel) {
        this.invoiceModel = invoiceModel;
    }

    @Override
    public String getCustomerCode(int productID) {
        return invoiceModel.getCustomerCode(productID);
    }

    @Override
    public ServiceResult<BigDecimal> getInvoiceAmount(String cOrderSn) {
        ServiceResult<BigDecimal> result = new ServiceResult<BigDecimal>();
        try {
            BigDecimal count = invoiceModel.getInvoiceAmount(cOrderSn);
            if (count != null && count.compareTo(BigDecimal.ZERO) >= 0) {
                result.setMessage("");
                result.setSuccess(true);
                result.setResult(count);
            } else {
                result.setMessage("计算发票金额出错，请检查日志");
                result.setSuccess(false);
                result.setResult(count);
            }
        } catch (Exception e) {
            log.error("生成发票时, 发生未知异常：", e);
            result.setMessage("服务器发生异常：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(null);
        }
        return result;
    }

    @Override
    public ServiceResult<BigDecimal> getInvoiceAmount(String cOrderSn, boolean flag) {
        ServiceResult<BigDecimal> result = new ServiceResult<BigDecimal>();
        try {
            BigDecimal count = invoiceModel.getInvoiceAmount(cOrderSn, flag);
            if (count != null && count.compareTo(BigDecimal.ZERO) >= 0) {
                result.setMessage("");
                result.setSuccess(true);
                result.setResult(count);
            } else {
                result.setMessage("计算发票金额出错，请检查日志");
                result.setSuccess(false);
                result.setResult(count);
            }
        } catch (Exception e) {
            log.error("生成发票时, 发生未知异常：", e);
            result.setMessage("服务器发生异常：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(null);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> syncToInvoiceSystem() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            invoiceModel.syncToInvoiceSystem();

            result.setMessage("");
            result.setSuccess(true);
            result.setResult(true);
        } catch (Exception e) {
            log.error("同步发票信息到开票系统时, 发生未知异常：", e);
            result.setMessage("服务器发生异常：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(false);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> syncInvoiceInvalidInfoFromEInvoiceSystem() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            if ((int) (new Date().getTime() / 1000) < 1451392200) {
                //升级之前的1.1版本的[同步作废发票信息从电子发票系统]，不再使用
                invoiceModel.syncInvoiceInvalidInfoFromEInvoiceSystemOld();
            } else {
                invoiceModel.syncInvoiceInvalidInfoFromEInvoiceSystem();
            }

            result.setMessage("");
            result.setSuccess(true);
            result.setResult(true);
        } catch (Exception e) {
            log.error("同步作废发票信息从电子发票系统时, 发生未知异常：", e);
            result.setMessage("服务器发生异常：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(false);
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> syncInvoiceInfoFromEInvoiceSystem(List<Invoices> list) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            if (list == null || list.size() <= 0) {
                result.setMessage("发票列表为空!");
                result.setSuccess(false);
                result.setResult(0);
            } else {
                Integer count;
                if ((int) (new Date().getTime() / 1000) < 1451392200) {
                    //升级之前的1.1版本的[同步发票信息从电子发票系统]，不再使用
                    count = invoiceModel.syncInvoiceInfoFromEInvoiceSystemOld(list);
                } else {
                    count = invoiceModel.syncInvoiceInfoFromEInvoiceSystem(list);
                }
                result.setMessage("");
                result.setSuccess(true);
                result.setResult(count);
            }
        } catch (Exception e) {
            log.error("同步作废发票信息从电子发票系统时, 发生未知异常：", e);
            result.setMessage("服务器发生异常：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(0);
        }
        return result;
    }

    /**
     * 保存发票信息
     * @param id ：发票ID(MemberInvoice id)
     * @param invoiceType
     * @param invoiceTitle
     * @param taxPayerNumber
     * @param registerAddress
     * @param registerPhone
     * @param bankName
     * @param bankCardNumber
     * @param state
     * @param remark
     * @param auditor
     * @return
     */
    public String saveMemberInvoices(Integer id, Integer invoiceType, String invoiceTitle,
                                     String taxPayerNumber, String registerAddress,
                                     String registerPhone, String bankName, String bankCardNumber,
                                     Integer state, String remark, String auditor) {

        return invoiceModel.saveMemberInvoices(id, invoiceType, invoiceTitle, taxPayerNumber,
            registerAddress, registerPhone, bankName, bankCardNumber, state, remark, auditor);
    }

    public String unlockMemberInvoices(Integer id, String userName) {

        return invoiceModel.unlockMemberInvoices(id, userName);
    }

    @Override
    public ServiceResult<Map<String, String>> queryInvoice(String corderSn) {
        //        return invoiceModel.queryInvoice(corderSn);
        ServiceResult<Map<String, String>> einvoiceResult = new ServiceResult<Map<String, String>>();
        Map<String, String> resultMap = null;
        if ((int) (new Date().getTime() / 1000) < 1451392200) {
            //升级之前的1.1版本的[电子发票开票信息查询]，不再使用
            resultMap = invoiceModel.queryInvoiceResultMapOld(corderSn);
        } else {
            resultMap = invoiceModel.queryInvoiceResultMap(corderSn);
        }
        einvoiceResult.setResult(resultMap);
        einvoiceResult.setSuccess(resultMap != null ? true : false);

        return einvoiceResult;
    }

    @Override
    public ServiceResult<Integer> updateInvoice(List<Invoices> invoicesList, boolean reSend) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        if (invoicesList == null || invoicesList.size() <= 0) {
            result.setMessage("发票不存在");
            result.setSuccess(false);
            result.setResult(0);
        }
        int successCount = 0;
        for (Invoices invoice : invoicesList) {
            try {
                makeOutInvoiceModel.updateInvoice(invoice, reSend);
                successCount++;
            } catch (Exception e) {
                log.error("更新发票发生异常，发票编号：" + invoice.getCOrderSn(), e);
            }
        }
        result.setSuccess(true);
        result.setResult(successCount);

        return result;
    }

    @Override
    public ServiceResult<Boolean> syncElectricInvoiceToHp() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            makeOutInvoiceModel.syncElectricInvoiceToHp();

            result.setMessage("");
            result.setSuccess(true);
            result.setResult(true);
        } catch (Exception e) {
            log.error("同步电子发票信息到HP系统时, 发生未知异常：", e);
            result.setMessage("服务器发生异常：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(false);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> syncElectricInvoiceOrder2thsToSap() {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            makeOutInvoiceModel.syncElectricInvoiceOrder2thsToSap();

            result.setMessage("");
            result.setSuccess(true);
            result.setResult(true);
        } catch (Exception e) {
            log.error("同步电子发票差异订单到SAP系统时, 发生未知异常：", e);
            result.setMessage("服务器发生异常：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(false);
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> syncStatusInvoices(List<Invoices> invoicesList) {

        ServiceResult<Integer> result = new ServiceResult<Integer>();
        int successCount = 0;
        if (invoicesList == null || invoicesList.size() <= 0) {
            result.setMessage("发票不存在!");
            result.setSuccess(false);
            result.setResult(0);
        } else {
            for (Invoices invoice : invoicesList) {
                try {
                    makeOutInvoiceModel.syncInvoiceFromTax(invoice);
                    successCount++;
                } catch (Exception e) {
                    log.error("同步发票发生异常，发票编号：" + invoice.getCOrderSn(), e);
                }
            }
            result.setSuccess(true);
            result.setResult(successCount);
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> syncTaxStatus(List<Invoices> invoicesList) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        if (invoicesList == null || invoicesList.size() <= 0) {
            result.setMessage("发票不存在!");
            result.setSuccess(false);
            result.setResult(0);
        }
        int successCount = 0;
        for (Invoices invoice : invoicesList) {
            try {
                makeOutInvoiceModel.updateInvoiceStatus(invoice);
                successCount++;
            } catch (Exception e) {
                log.error(
                    "同步发票发生异常，cOrderSn[" + invoice.getCOrderSn() + "]," + "发票号["
                            + invoice.getInvoiceNumber() + "]", e);
            }
        }
        result.setSuccess(true);
        result.setResult(successCount);

        return result;
    }

    @Override
    public ServiceResult<Map<String, Object>> searchInvoicesInfoByCOrderSn(String cOrderSn) {
        ServiceResult<Map<String, Object>> result = new ServiceResult<Map<String, Object>>();
        Map<String, Object> invoicesMap;
        OrdersNew orders;
        try {
            orders = invoiceModel.searchOrdersInfoByCOrderSn(cOrderSn);
            if (orders == null) {
                log.error("网单号cOrderSn[" + cOrderSn + "]没有查询到订单或网单信息");
                result.setMessage("网单号cOrderSn[" + cOrderSn + "]没有查询到订单或网单信息");
                result.setSuccess(false);
                result.setResult(null);
                return result;
            }
            if (orders.getSource() == null || orders.getSource().equals("")) {
                log.error("网单号cOrderSn[" + cOrderSn + "]查询到的订单来源为空");
                result.setMessage("网单号cOrderSn[" + cOrderSn + "]查询到的订单来源为空");
                result.setSuccess(false);
                result.setResult(null);
                return result;
            }
            /*if (!checkSource(orders.getSource())) {
                log.error("网单号cOrderSn[" + cOrderSn + "]查询到的订单来源有误");
                result.setMessage("网单号cOrderSn[" + cOrderSn + "]查询到的订单来源有误");
                result.setSuccess(false);
                result.setResult(null);
                return result;
            }*/

            invoicesMap = invoiceModel.searchInvoicesInfoByCOrderSn(cOrderSn);

            //将时间戳改成时间类型
            if (invoicesMap != null) {
                String billingTime = DateFormatUtil.formatTime(invoicesMap.get("billingTime"));
                invoicesMap.put("billingTime", billingTime);
                String invalidTime = DateFormatUtil.formatTime(invoicesMap.get("invalidTime"));
                invoicesMap.put("invalidTime", invalidTime);
            }

            result.setResult(invoicesMap);
        } catch (Exception e) {
            log.error("根据网单号cOrderSn查询发票部分信息时, 发生未知异常：", e);
            result.setMessage("服务器发生异常：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(null);
        }
        return result;
    }

    private boolean checkSource(String source) {
        for (int i = 0; i < SOURCE.length; i++) {
            if (SOURCE[i].equalsIgnoreCase(source)) {
                return true;
            }
        }
        return false;
    }
}
