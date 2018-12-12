package com.haier.shop.services;

import com.haier.common.BusinessException;
import com.haier.shop.dao.shopread.*;
import com.haier.shop.dao.shopwrite.*;
import com.haier.shop.model.*;
import com.haier.shop.service.ShopInvoiceService;
import com.haier.shop.util.DateFormatUtil;
import com.haier.shop.util.InvoiceServiceUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发票业务操作实现类（业务专用）
 */
@Service
public class ShopInvoicesServiceImpl implements ShopInvoiceService {

    private static final Logger logger = LogManager.getLogger(ShopInvoicesServiceImpl.class);
    @Autowired
    private InvoicesReadDao invoicesReadDao;
    @Autowired
    private InvoicesWriteDao invoicesWriteDao;
    @Autowired
    private OrdersReadDao ordersReadDao;
    @Autowired
    private OrderProductsReadDao orderProductsReadDao;
    @Autowired
    private OrderProductsWriteDao orderProductsWriteDao;
    @Autowired
    private OrderOperateLogsWriteDao orderOperateLogsWriteDao;
    @Autowired
    private InvoiceApiLogsWriteDao invoiceApiLogsWriteDao;
    @Autowired
    private OrderRepairsReadDao orderRepairsReadDao;
    @Autowired
    private OrderRepairsWriteDao orderRepairsWriteDao;
    @Autowired
    private OrderRepairLogsWriteDao orderRepairLogsWriteDao;
    @Autowired
    private MemberInvoicesWriteDao memberInvoicesWriteDao;
    @Autowired
    private MemberInvoicesReadDao memberInvoicesReadDao;
    @Autowired
    private InvoiceElectricLogsWriteDao invoiceElectricLogsWriteDao;
    @Autowired
    private InvoiceElectricSyncLogsReadDao invoiceElectricSyncLogsReadDao;
    @Autowired
    private InvoiceElectricSyncLogsWriteDao invoiceElectricSyncLogsWriteDao;
    @Autowired
    private InvoiceElectric2OutReadDao invoiceElectric2OutReadDao;
    @Autowired
    private InvoiceElectric2OutWriteDao invoiceElectric2OutWriteDao;
    @Autowired
    private InvoiceSAPLogsReadDao invoiceSAPLogsReadDao;
    @Autowired
    private InvoiceSAPLogsWriteDao invoiceSAPLogsWriteDao ;

    @Override
    public Invoices getInvoicesByCOrderSn(String cOrderSn) {
        return invoicesReadDao.getInvoicesByCOrderSn(cOrderSn);
    }

    @Override
    public Integer updateInvoices(Invoices invoices) {
        return invoicesWriteDao.updateInvoices(invoices);
    }

    @Override
    public Invoices getById(Integer id) {
        return invoicesReadDao.getById(id);
    }

    @Override
    public Map<String, Object> getInvoiceMakeOutListByPage(Map<String, Object> paramMap) {
        //获取开单列表List
        List<InvoicesDispItem> result = invoicesReadDao.getInvoiceMakeOutList(paramMap);
        //获得条数
        int resultcount = invoicesReadDao.getRowCnts();
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("total", resultcount);
        retMap.put("rows", result);
        return retMap;
    }

    @Override
    public int getRowCnts() {
        return invoicesReadDao.getRowCnts();
    }

    @Override
    public List<Map<String, Object>> getExportInvoiceMakeOutList(Map<String, Object> paramMap) {
        return invoicesReadDao.getExportInvoiceMakeOutList(paramMap);
    }

    @Override
    public Invoices getUsableByOrderProductId(Integer id) {
        return invoicesReadDao.getUsableByOrderProductId(id);
    }

    @Override
    public int eInvoiceBatchInvalid(Map<String, Object> params) {
        return invoicesWriteDao.eInvoiceBatchInvalid(params);
    }

    @Override
    public int eInvoiceBatchReBilling(Map<String, Object> params) {
        return invoicesWriteDao.eInvoiceBatchReBilling(params);
    }

    /**
     * 更新发票信息 业务操作
     * @param invoice
     */
    @Override
    @Transactional(value = "shopTransactionManagerWrite", isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void updateInvoiceOperate(Invoices invoice) {
        invoicesWriteDao.updateInvoices(invoice);
        if (!InvoiceConst.COMMON_CORDER_TYPE.equals(invoice.getCOrderType())) {
            logger.error("发票对应网单不是普通网单，发票信息：" + invoice.getId());
            return;
        }
        OrderProducts orderProduct = orderProductsReadDao.get(invoice.getOrderProductId());

        OrderOperateLogs operateLogs = new OrderOperateLogs();
        operateLogs.setSiteId(1);
        operateLogs.setOrderId(orderProduct.getOrderId());
        operateLogs.setOrderProductId(invoice.getOrderProductId());
        operateLogs.setNetPointId(orderProduct.getNetPointId());
        operateLogs.setStatus(orderProduct.getStatus());
        //付款状态
        operateLogs.setPaymentStatus(orderProduct.getCPaymentStatus());

        operateLogs.setOperator("系统");
        operateLogs.setChangeLog("变更发票状态");
        operateLogs.setRemark("变更发票状态，等待同步");
        orderOperateLogsWriteDao.insert(operateLogs);
    }

    @Override
    @Transactional(value = "shopTransactionManagerWrite", isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public void updateInvoiceStatus4Tax(Invoices edit, boolean hasPushData) throws BusinessException {
        if (edit == null) {
            throw new BusinessException("没有可验证的发票");
        }
        Invoices innerInvoice = invoicesReadDao.getById(edit.getId());
        if (innerInvoice == null) {
            innerInvoice = invoicesReadDao.getInvoicesByCOrderSn(edit.getCOrderSn());
        }
        if (innerInvoice == null) {
            throw new BusinessException("没有找到可更新的发票");
        }
        edit.setId(innerInvoice.getId());
        edit.setOrderProductId(innerInvoice.getOrderProductId());
        edit.setDiffId(innerInvoice.getDiffId());
        edit.setMemberInvoiceId(innerInvoice.getMemberInvoiceId());
        edit.setCOrderType(innerInvoice.getCOrderType());
        edit.setCOrderSn(innerInvoice.getCOrderSn());
        String pushData = hasPushData ? innerInvoice.getCOrderSn() : "";
        String receiveData = "array ('state' => '" + edit.getState() + "'," + "'eaiWriteState' => '"
                + edit.getEaiWriteState() + "'," + "'drawer' => '" + edit.getDrawer()
                + "'," + "'invoiceNumber' => '" + edit.getInvoiceNumber() + "',"
                + "'backupFieldA' => '" + edit.getBackupFieldA() + "',"
                + "'billingTime' => " + edit.getBillingTime() + ","
                + "'eaiWriteTime' => " + edit.getEaiWriteTime() + ","
                + "'invalidTime' => '" + edit.getInvalidTime() + "'," + "'success' => "
                + (innerInvoice.getSuccess() == 1 ? "true" : "false") + "," + ")";
        InvoiceApiLogs apiLogs = new InvoiceApiLogs();
        apiLogs.setInvoiceId(innerInvoice.getId());
        apiLogs.setType(InvoiceConst.RECEIVE_KIND);
        apiLogs.setOrderProductId(innerInvoice.getOrderProductId());
        apiLogs.setCountNum(1);
        apiLogs.setCOrderSn(innerInvoice.getCOrderSn());
        apiLogs.setIsSuccess(edit == null ? InvoiceConst.FAILED
                : (edit.getSuccess() == null ? 1 : edit.getSuccess()));
        apiLogs.setReturnData("同步数据");
        apiLogs.setReceiveData(receiveData);
        apiLogs.setPushData(pushData);
        apiLogs.setLastMessage("EAI同步状态");

        try {
            invoicesWriteDao.updateInvoices(edit);
            invoiceApiLogsWriteDao.insert(apiLogs);
            String eaiWriteState = edit.getEaiWriteState();
            if (InvoiceConst.INVALID_KP_STATE.equals(eaiWriteState)) {
                ///*如果是返回的是当月作废*/
                this.updateOrderProduct(edit, InvoiceConst.MR_STATE_NULLIFY, "'当月作废'");
            } else if (InvoiceConst.MONTH_INVALID_KP_STATE.equals(eaiWriteState)) {
                /*如果是返回的是跨月冲红*/
                this.updateOrderProduct(edit, InvoiceConst.MR_STATE_RED, "'跨月冲红'");
            } else if (InvoiceConst.COMPLETE_STATE.equals(edit.getState())) {
                /*如果是已开票则更新网单表或差异网单表中的开票信息*/
                this.updateMemberInvoice(edit);
                this.updateOrderProduct(edit, InvoiceConst.MR_STATE_MAKED, "'已开票'");
                if (InvoiceConst.DIFF_CORDER_TYPE.equals(edit.getCOrderType())) {
                    //向SAP传递开票信息先插入表`invoice_electric_2_out`,sendType=SAP，另外写job读取该表数据执行传SAP功能
                    this.insertInvoiceElectric2Out(edit.getId(), "SAP");
                }
            }

            Integer pushType = this.getInvoiceSAPLogsOfPushType(edit);
            boolean sendSap = this.handelInvoiceSAPLogs(edit, pushType);
            if (!sendSap) {
                logger.error("开票时，sapGenerate更新电子发票，未推送Sap开票信息，发票id:" + edit.getId());
            }
        } catch (Exception e) {
            logger.error("更新发票发生异常:", e);
            throw new BusinessException("更新发票发生异常");
        }
    }

    @Override
    @Transactional(value = "shopTransactionManagerWrite", isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public boolean updateElecInvoice4ElecSys(Invoices invoices,Map<String, String> paramMap) throws BusinessException{

        Integer pushType = this.getInvoiceSAPLogsOfPushType(invoices);
        try {
            if (this.updateInvoiceInfo(invoices, pushType)) {
                if (invoices.getStatusType().equals(1)
                        && invoices.getSuccess().equals(1)) {//开票
                    //更新发票信息后处理订单操作
                    handelOrderAfterUpdateCreateInvoice(invoices, paramMap);
                } else if (invoices.getStatusType().equals(4)
                        && invoices.getSuccess().equals(1)) {//作废
                    //更新发票信息后处理订单操作
                    handelOrderAfterUpdateInvalidInvoice(invoices, paramMap);
                }
                return true;
            } else {
                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                        .getTryNum()) + 1);
                invoicesWriteDao.updateInvoices(invoices);
                return false;
            }
        }catch(Exception e){
            logger.error("更新发票发生异常:" + e.getMessage());
            throw new BusinessException("更新发票发生异常");
        }
    }

    @Override
    public List<InvoicesDispItem> showInvoiceInfo(Integer id) {
        return invoicesReadDao.showInvoiceInfo(id);
    }

    @Override
    public int invoiceBatchModify(Map<String, Object> params) {
        return invoicesWriteDao.invoiceBatchModify(params);
    }

    /**
     * 更新网单发票信息
     * @param invoice
     * @param makeReceipt
     * @param remark
     * @throws BusinessException
     */
    private void updateOrderProduct(Invoices invoice, Integer makeReceipt,
                                    String remark) throws BusinessException {
        if (Invoices.CORDERTYPE_COMMON_CORDER_TYPE != invoice.getCOrderType()) {
            return;
        }
        Integer orderProductId = invoice.getOrderProductId();
        OrderProducts orderProduct = orderProductsReadDao.get(orderProductId);
        //判断网单是否存在
        if (orderProductId != null && orderProductId > 0 && orderProduct != null) {
            OrderProducts tempOrderProduct = new OrderProducts();
            //商城用
            tempOrderProduct.setIsMakeReceipt(makeReceipt);//开票状态修改为3-当月作废
            tempOrderProduct
                    .setReceiptAddTime(DateFormatUtil.formatTime("yyyyMMdd", invoice.getBillingTime()));
            tempOrderProduct.setReceiptNum(invoice.getInvoiceNumber());
            tempOrderProduct
                    .setSystemRemark(
                            tempOrderProduct.getSystemRemark() + "发票状态变更为" + remark + ",isMakeReceipt:{"
                                    + tempOrderProduct.getIsMakeReceipt() + "},receiptNum:{"
                                    + tempOrderProduct.getReceiptNum() + "},receiptAddTime:{"
                                    + tempOrderProduct.getReceiptAddTime() + "};");
            tempOrderProduct.setId(orderProductId);
            orderProductsWriteDao.updateForsyncInvoice(tempOrderProduct);

            OrderOperateLogs operateLogs = new OrderOperateLogs();
            operateLogs.setSiteId(1);
            operateLogs.setOrderId(orderProduct.getOrderId());
            operateLogs.setOrderProductId(orderProductId);
            operateLogs.setNetPointId(orderProduct.getNetPointId());
            operateLogs.setStatus(orderProduct.getStatus());
            //付款状态
            operateLogs.setPaymentStatus(orderProduct.getCPaymentStatus());
            operateLogs.setOperator("金税系统");
            operateLogs.setChangeLog("发票状态更改");
            operateLogs.setRemark("发票状态变更为'" + remark + "'");

            orderOperateLogsWriteDao.insert(operateLogs);

            //修改退货申请的发票信息
            List<OrderRepairs> orderRepairsList = orderRepairsReadDao.getByOrderProductId(orderProductId);
            if (orderRepairsList == null || orderRepairsList.size() <= 0) {
                return;
            }
            for (OrderRepairs orderRepairs : orderRepairsList) {
                if (orderRepairs.getReceiptStatus() == InvoiceConst.RS_CANCELED) {
                    return;
                }
                orderRepairs.setReceiptStatus(InvoiceConst.RS_CANCELED);
                orderRepairs.setReceiptTime((int) new Date().getTime() / 1000);
                orderRepairsWriteDao.updateReceiptInfo(orderRepairs);
                OrderRepairLogs orderRepairsLog = new OrderRepairLogs();
                orderRepairsLog.setSiteId(1);
                orderRepairsLog.setAddTime((int) new Date().getTime() / 1000);
                orderRepairsLog.setOrderRepairId(orderRepairs.getId());
                orderRepairsLog.setOperate("rs_cancel");
                orderRepairsLog.setOperator("系统");
                orderRepairsLog.setRemark(
                        "'同步发票状态到 ' " + InvoiceConst.RECEIPT_STATUS.get(InvoiceConst.RS_CANCELED));
                orderRepairLogsWriteDao.insert(orderRepairsLog);
            }
        }
    }

    /**
     * 更新会员发票信息
     * @param invoice
     * @throws BusinessException
     */
    private void updateMemberInvoice(Invoices invoice) throws BusinessException {
        Integer memberInvoiceId = invoice.getMemberInvoiceId();
        MemberInvoices memberInvoice = memberInvoicesReadDao.getById(memberInvoiceId);
        if (memberInvoice == null) {
            return;//throw new BusinessException("没有要更新的发票, memberInvoiceId=" + invoice.getMemberInvoiceId());
        }
        //开票后锁定发票信息
        MemberInvoices tempMemberInvoice = new MemberInvoices();
        tempMemberInvoice.setIsLock(1);//锁定member invoice
        tempMemberInvoice.setId(memberInvoiceId);
        memberInvoicesWriteDao.updateLockStatus(tempMemberInvoice);
    }

    /**
     * 保存推SAP的发票信息
     * @param invoiceId
     * @param sendType
     */
    private void insertInvoiceElectric2Out(Integer invoiceId, String sendType) {
        //向SAP传递开票信息先插入表`invoice_electric_2_out`,sendType=SAP，另外写job读取该表数据执行传SAP功能
        InvoiceElectric2Out invoiceElectric2Out = new InvoiceElectric2Out();
        invoiceElectric2Out.setAddTime(new Date());
        invoiceElectric2Out.setInvoiceId(invoiceId);
        invoiceElectric2Out.setLastMessage("");
        invoiceElectric2Out.setSendType(sendType);//SAP & HP
        invoiceElectric2Out.setProcessTime(new Date());
        invoiceElectric2Out.setSuccess(0);
        invoiceElectric2Out.setCount(0);
        List<InvoiceElectric2Out> ie2olist = invoiceElectric2OutReadDao.getByInvoiceIdAndSendType(invoiceElectric2Out);
        if (ie2olist == null || ie2olist.size() < 1) {
            invoiceElectric2OutWriteDao.insert(invoiceElectric2Out);
        }
    }

    /**
     * 组装 发票SAP日志的PushType的值
     * @param invoice
     * @return
     */
    private Integer getInvoiceSAPLogsOfPushType(Invoices invoice) {
        Integer pushType = 0;
        if (InvoiceConst.COMPLETE_STATE.equals(invoice.getState())) {
            if (InvoiceConst.COMMON_CORDER_TYPE.equals(invoice.getCOrderType())) {
                OrderProducts orderProduct = orderProductsReadDao.get(invoice.getOrderProductId());
                if (orderProduct != null) {
                    // 同步向SAP传递开票信息
                    if (!InvoiceConst.SPLIT_NEW.equals(orderProduct.getSplitFlag())) {
                        if (!orderProduct.getCOrderSn().equals(invoice.getCOrderSn())) {
                            // 网单与发票的网单号不相等，代表是 作废/红冲 后重新开票
                            pushType = InvoiceConst.SAP_TYPE_VOUCHER;
                        } else {
                            pushType = InvoiceConst.SAP_TYPE_NORMAL;
                        }
                    } else {
                        // 拆单后开票，需要按二次开票推送
                        pushType = InvoiceConst.SAP_TYPE_VOUCHER;
                    }
                }
            }else{
                logger.error("查询金税并更新发票信息时发生错误，非普通发票，调用参数：" + invoice.getCOrderType());
                throw new BusinessException("查询金税并更新发票信息时发生错误，非普通发票");
            }
        }

        if (InvoiceConst.INVALID_KP_STATE.equals(invoice.getEaiWriteState())
                || InvoiceConst.MONTH_INVALID_KP_STATE.equals(invoice.getEaiWriteState())) {
            // 同步向SAP传递发票作废/红冲信息
            pushType = InvoiceConst.SAP_TYPE_INVALID;
        }
        return pushType;
    }

    /**
     * 处理开票或作废后同步sap发票信息
     */
    private boolean handelInvoiceSAPLogs(Invoices invoices, Integer pushType) {
        InvoiceSAPLogs isap = new InvoiceSAPLogs();
        isap.setInvoiceId(invoices.getId());
        isap.setPushType(pushType);
        List<InvoiceSAPLogs> isaplist = invoiceSAPLogsReadDao.getByInvoiceIdAndPushType(isap);
        if (isaplist == null || isaplist.size() == 0) {
            isap.setAddTime((int) (new Date().getTime() / 1000));
            isap.setcOrderType(invoices.getCOrderType());
            isap.setOrderProductId(invoices.getOrderProductId());
            isap.setDiffId(invoices.getDiffId());
            isap.setPushData("");
            isap.setReturnData("");
            isap.setSuccess(0);
            isap.setCount(0);
            isap.setLastTime(0);
            isap.setLastMessage("");
            invoiceSAPLogsWriteDao.insert(isap);
        } else {
            for (InvoiceSAPLogs sapLog : isaplist){
                if (sapLog.getSuccess().equals(0) && sapLog.getCount().intValue() >= 15){
                    sapLog.setCount(0);
                    sapLog.setLastMessage("[" + DateFormatUtil.format(new Date()) + "]同步程序自动重置count值");
                    invoiceSAPLogsWriteDao.updateInvoiceSAPLogs(sapLog);
                }
            }
        }
        return true;
    }

    /**
     * 更新发票信息并写sap
     */
    private boolean updateInvoiceInfo(Invoices invoices, Integer pushType) {
        if (pushType == null
                || (!pushType.equals(1) && !pushType.equals(2) && !pushType.equals(3))) {
            return false;
        }
        try {
            if (invoicesWriteDao.updateInvoices(invoices) > 0) {
                return handelInvoiceSAPLogs(invoices, pushType);
            } else {
                return false;
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.toString()).append("\r\n");
            for (StackTraceElement key : e.getStackTrace()) {
                sb.append(key.toString()).append("\r\n");
            }
            logger.error("同步发票信息-作废时异常字符串:" + sb.toString());
            invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
            invoicesWriteDao.updateInvoices(invoices);
            return false;
        }
    }

    /**
     * 更新发票信息后处理订单操作
     */
    private void handelOrderAfterUpdateCreateInvoice(Invoices invoice, Map<String, String> attMap) {
        Integer orderId = 0;
        if (invoice.getCOrderType().equals(InvoiceConst.COMMON_CORDER_TYPE)) {//普通网单
            OrderProducts op = orderProductsReadDao.get(invoice.getOrderProductId());
            if (op != null) {
                op.setReceiptNum(invoice.getInvoiceNumber());
                op.setIsMakeReceipt(InvoiceConst.MR_STATE_MAKED);//// 已开票
                op.setReceiptAddTime(DateFormatUtil.formatByType("yyyyMMdd",
                        new Date(invoice.getBillingTime() * 1000)));
                op.setSystemRemark(op.getSystemRemark() + "发票状态变更为已开票,isMakeReceipt:{"
                        + op.getIsMakeReceipt() + "},receiptNum:{" + op.getReceiptNum()
                        + "},receiptAddTime:{" + op.getReceiptAddTime() + "};");
                orderProductsWriteDao.updateForsyncInvoice(op);

                Orders order = ordersReadDao.get(op.getOrderId());
                orderId = order.getId();
                //记录开票操作日志
                OrderOperateLogs log = InvoiceServiceUtil.assemblyOrderOperateLog(order, op,
                        "发票状态变更为:“已开票”。税控码：" + invoice.getFiscalCode(), "发票状态更改", "电子发票同步系统");
                orderOperateLogsWriteDao.insert(log);

                int statusType = 1;//开票
                InvoiceElectricSyncLogs invoiceElectricSyncLogs = InvoiceServiceUtil.assemblyInvoiceElectricSyncLogs(
                        order, op, invoice, attMap);

                List<InvoiceElectricSyncLogs> iesllist = invoiceElectricSyncLogsReadDao
                        .getByCOrderSnAngStatusType(invoice.getCOrderSn(), statusType);
                if (iesllist == null || iesllist.size() < 1) {
                    invoiceElectricSyncLogsWriteDao.insert(invoiceElectricSyncLogs);
                }

                //创建发票成功后同步HP队列表插入数据，单独运行，表`invoice_electric_2_out`,另外写job读取该表数据执行上面的电子发票信息传HP功能
                this.insertInvoiceElectric2Out(invoice.getId(), "HP");
            }
        }

        //插入或更新电子发票日志    ---需要设置推送和返回数据信息
        InvoiceElectricLogs invoiceElectricLogs = saveInvoiceElectricLogs(invoice, "",
                InvoiceConst.TYPE_GENERATE);
        invoiceElectricLogs.setLastMessage("同步发票开票信息成功");
        invoiceElectricLogs.setLastTime((int) (new Date().getTime() / 1000));
        invoiceElectricLogsWriteDao.updateByInvoiceIdAndType(invoiceElectricLogs);

        MemberInvoices mInvoices = memberInvoicesReadDao.getByOrderId(orderId);
        if (mInvoices != null) {
            mInvoices.setIsLock(1);//开票成功后设置锁，不让修改信息
            memberInvoicesWriteDao.updateLockStatus(mInvoices);
        }
    }

    /**
     * 保存电子发票日志
     * @param invoice
     * @param data
     * @param type
     * @return
     */
    private InvoiceElectricLogs saveInvoiceElectricLogs(Invoices invoice, String data, Integer type) {
        if (!InvoiceConst.TYPE_GENERATE.equals(type) && !InvoiceConst.TYPE_INVALID.equals(type)
                && !InvoiceConst.TYPE_GET.equals(type)) {
            logger.error("组装电子发票日志信息：开票类型未定义，网单id（" + invoice.getOrderProductId() + "）");
            return null;
        }
        InvoiceElectricLogs log = null;
        //插入电子发票日志，将每次推送电子发票系统的日志都保存下来。不在原有发票日志上进行修改，而是重新插入一条
        //        List<InvoiceElectricLogs> logList = invoiceElectricLogsDao.getByInvoiceIdAndTypeList(
        //            invoice.getId(), type);
        //        if (logList != null && logList.size() > 0) {
        //            log = logList.get(0);
        //        }
        int n = 0;
        //        if (log == null) {
        log = new InvoiceElectricLogs();
        log.setAddTime((int) (new Date().getTime() / 1000));
        log.setOrderProductId(invoice.getOrderProductId());
        log.setCOrderSn(invoice.getCOrderSn());
        log.setInvoiceId(invoice.getId());
        log.setType(type);
        log.setPushData(data);//eInvoiceSign(data),接口中会签名，这里不加签名
        log.setReturnData("");
        log.setAnalysisResult(0);
        log.setVerifyResult(0);
        log.setCount(1);
        log.setSuccess(0);
        log.setLastTime((int) (new Date().getTime() / 1000));
        log.setLastMessage("");
        log.setSmsFlag(0);
        n = invoiceElectricLogsWriteDao.insertLog(log);
        //        } else {
        //            //            log.setAddTime((int) (new Date().getTime() / 1000));
        //            log.setOrderProductId(invoice.getOrderProductId());
        //            log.setCOrderSn(invoice.getCOrderSn());
        //            //            log.setInvoiceId(invoice.getId());
        //            //            log.setType(type);
        //            log.setPushData(data);//eInvoiceSign(data),接口中会签名，这里不加签名
        //            log.setCount(log.getCount() == null ? 1 : log.getCount() + 1);
        //            n = invoiceElectricLogsDao.updateByInvoiceIdAndType(log);
        //        }
        if (n < 1) {
            return null;
        }
        return log;
    }

    /**
     * 更新作废发票信息后处理订单操作
     */
    private void handelOrderAfterUpdateInvalidInvoice(Invoices invoices, Map<String, String> attMap) {
        if (invoices.getCOrderType().equals(InvoiceConst.COMMON_CORDER_TYPE)) {
            OrderProducts op = orderProductsReadDao.get(invoices.getOrderProductId());
            if (op != null) {
                op.setIsMakeReceipt(InvoiceConst.MR_STATE_RED);
                orderProductsWriteDao.updateMakeReceiptType(op);

                Orders order = ordersReadDao.get(op.getOrderId());
                //记录开票操作日志
                OrderOperateLogs log = InvoiceServiceUtil.assemblyOrderOperateLog(order, op, "发票状态变更为“红冲”",
                        "发票状态更改", "电子发票同步系统");
                orderOperateLogsWriteDao.insert(log);

                int statusType = 2;//红冲
                InvoiceElectricSyncLogs invoiceElectricSyncLogs = InvoiceServiceUtil.assemblyInvoiceElectricSyncLogs(
                        order, op, invoices, attMap);

                List<InvoiceElectricSyncLogs> iesllist = invoiceElectricSyncLogsReadDao
                        .getByCOrderSnAngStatusType(invoices.getCOrderSn(), statusType);
                if (iesllist == null || iesllist.size() < 1) {
                    invoiceElectricSyncLogsWriteDao.insert(invoiceElectricSyncLogs);
                }
            }

            //插入或更新电子发票日志    ---需要设置推送和返回数据信息
            InvoiceElectricLogs invoiceElectricLogs = saveInvoiceElectricLogs(invoices, "",
                    InvoiceConst.TYPE_INVALID);
            invoiceElectricLogs.setLastMessage("同步发票作废信息成功");
            invoiceElectricLogs.setLastTime((int) (new Date().getTime() / 1000));
            invoiceElectricLogsWriteDao.updateByInvoiceIdAndType(invoiceElectricLogs);
            //修改退换货申请的发票状态
            boolean orderRepairs = orderRepairsCancelInvoice(invoices.getOrderProductId());
            if (orderRepairs) {
                //                log.info("更新退货单成功！orderProductId=" + invoices.getOrderProductId());
            } else {
                logger.error("无退货单，或者退货单更新失败, orderProductId=" + invoices.getOrderProductId());
            }
        }
    }

    /**
     * 修改退换货申请的发票状态
     * param orderProductId 网单id
     * @return
     */
    private boolean orderRepairsCancelInvoice(Integer orderProductId) {
        if (orderProductId == null || orderProductId.equals(0)) {
            logger.error("修改退换货申请的发票状态：网单id为空或为0");
            return false;
        }
        OrderProducts orderProduct = orderProductsReadDao.get(orderProductId);
        if (orderProduct == null) {
            logger.error("修改退换货申请的发票状态：网单不存在");
            return false;
        }
        List<OrderRepairs> orderRepairsList = orderRepairsReadDao.getByOrderProductId(orderProductId);
        if (orderRepairsList == null || orderRepairsList.size() == 0) {
            logger.error("修改退换货申请的发票状态：没有找到对应的退换货列表");
            return false;
        }
        for (int i = 0; i < orderRepairsList.size(); i++) {
            if (InvoiceConst.RS_CANCELED.equals(orderRepairsList.get(i).getReceiptStatus())) {
                return false;
            }
            orderRepairsList.get(i).setReceiptStatus(InvoiceConst.RS_CANCELED);
            orderRepairsList.get(i).setReceiptTime((int) (new Date().getTime() / 1000));
            orderRepairsWriteDao.updateReceiptInfo(orderRepairsList.get(i));

            //插入退换货日志
            OrderRepairLogs orderRepairLogs = new OrderRepairLogs();
            orderRepairLogs.setSiteId(1);
            orderRepairLogs.setAddTime((int) (new Date().getTime() / 1000));
            orderRepairLogs.setOrderRepairId(orderRepairsList.get(i).getId());
            orderRepairLogs.setOperator("系统");
            orderRepairLogs.setOperate("rs_cancel");
            orderRepairLogs.setRemark("同步发票状态到"
                    + InvoiceConst.RECEIPT_STATUS.get(InvoiceConst.RS_CANCELED));
            orderRepairLogsWriteDao.insert(orderRepairLogs);
        }
        return true;
    }

}
