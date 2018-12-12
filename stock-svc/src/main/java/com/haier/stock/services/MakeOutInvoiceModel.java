package com.haier.stock.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.InvoiceApiLogs;
import com.haier.shop.model.InvoiceConst;
import com.haier.shop.model.InvoiceData2HP;
import com.haier.shop.model.InvoiceElectric2Out;
import com.haier.shop.model.InvoiceEntity;
import com.haier.shop.model.Invoices;
import com.haier.shop.model.MemberInvoices;
import com.haier.shop.model.Order2ths;
import com.haier.shop.model.Order4Invoices;
import com.haier.shop.model.OrderOperateLogs;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrderRepairLogsNew;
import com.haier.shop.model.OrderRepairsNew;
import com.haier.shop.model.Orders2thsToSAPzsds0001;
import com.haier.shop.model.QueryInvoiceInputType;
import com.haier.shop.service.CustomerCodesService;
import com.haier.shop.service.InvoiceElectric2OutService;
import com.haier.shop.service.InvoicesService;
import com.haier.shop.service.MemberInvoicesService;
import com.haier.shop.service.Order2thsService;
import com.haier.shop.service.Order4InvoicesService;
import com.haier.shop.service.OrderProductsNewService;
import com.haier.shop.service.OrderRepairLogsNewService;
import com.haier.shop.service.OrderRepairsNewService;
import com.haier.shop.service.ShopInvoiceApiLogsService;
import com.haier.shop.service.ShopInvoiceSAPLogsService;
import com.haier.shop.service.ShopOrderOperateLogsService;
import com.haier.stock.datasource.ReadWriteRoutingDataSourceHolder;
import com.haier.stock.services.Helper.ThreadHelper;
import com.haier.stock.util.DateFormatUtil;
import com.haier.stock.util.Ustring;
@Service
public class MakeOutInvoiceModel {
    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
        .getLogger(MakeOutInvoiceModel.class);
    @Autowired
    private ShopInvoiceApiLogsService shopInvoiceApiLogsService;
    @Autowired
    private InvoicesService invoicesService;
    @Autowired
    private OrderProductsNewService orderProductsNewService;
    @Autowired
    private Order2thsService order2thsService;
    @Autowired
    private Order4InvoicesService order4InvoicesService;
    @Autowired
    private InvoiceElectric2OutService invoiceElectric2OutService;
    @Autowired
    private ShopOrderOperateLogsService shopOrderOperateLogsService;
    @Autowired
    private MemberInvoicesService memberInvoicesService;
    @Autowired
    private StockCenterEInvoiceServiceImpl        eInvoiceServiceImpl;
//    private InvoiceToSAPService    invoiceToSAPService;
    @Autowired
    private ThreadHelper           threadHelper;
    @Autowired
    private StockCenterInvoiceToTaxServiceImpl    invoiceToTaxService;
//    private CustomerCodeDao        customerCodeDao;
    @Autowired
    private OrderRepairsNewService orderRepairsNewService;
    @Autowired
    private OrderRepairLogsNewService orderRepairLogsNewService;
    @Autowired
    private CustomerCodesService customerCodesService;
    @Autowired
    private ShopInvoiceSAPLogsService shopInvoiceSAPLogsService;
    private DataSourceTransactionManager transactionManager;

    public void updateInvoice(Invoices edit, boolean reSend) throws Exception {
        if (edit == null) {
            throw new BusinessException("没有可验证的发票");
        }
//        Invoices innerInvoice = invoicesDao.get(edit.getId());
//        if (innerInvoice == null) {
//            throw new BusinessException("没有找到可更新的发票");
//        }
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
        	invoicesService.update(edit);

            Integer orderId = 0;
            Integer orderProductId = 0;
            Integer netPointId = 0;
            Integer opStatus = 0;
            Integer paymentStatus = 0;
            orderProductId = edit.getOrderProductId();
            if (InvoiceConst.COMMON_CORDER_TYPE.equals(edit.getCOrderType())) {//普通网单
                OrderProductsNew orderProduct = orderProductsNewService.get(edit.getOrderProductId());
                orderId = orderProduct.getOrderId();
                netPointId = orderProduct.getNetPointId();
                opStatus = orderProduct.getStatus();
                paymentStatus = orderProduct.getCPaymentStatus();
            } else if (InvoiceConst.DIFF_CORDER_TYPE.equals(edit.getCOrderType())) {//差异网单
                Order2ths order2ths = order2thsService.get(edit.getDiffId());
                orderId = order2ths.getId();
                netPointId = 0;
                opStatus = -1;
                paymentStatus = -1;
            } else { //专项开票
                Order4Invoices order4Invoices = order4InvoicesService.get(edit.getDiffId());
                orderId = order4Invoices.getId();
                netPointId = 0;
                opStatus = -1;
                paymentStatus = -1;
            }

            OrderOperateLogs operateLogs = new OrderOperateLogs();
            operateLogs.setSiteId(1);
            operateLogs.setOrderId(orderId);
            operateLogs.setOrderProductId(orderProductId);
            operateLogs.setNetPointId(netPointId);
            operateLogs.setStatus(opStatus);
            //付款状态
            operateLogs.setPaymentStatus(paymentStatus);

            operateLogs.setOperator("系统");
            operateLogs.setChangeLog("变更发票状态");
            operateLogs.setRemark("变更发票状态，等待同步");
            shopOrderOperateLogsService.insert(operateLogs);
            transactionManager.commit(status);
        } catch (Exception e) {
            log.error("更新发票发生异常:", e);
            transactionManager.rollback(status);
            throw new BusinessException("更新发票发生异常");
        }

    }

    public void updateInvoiceStatus(Invoices edit) {
        updateInvoiceStatus(edit, false);
    }

    public void updateInvoiceStatus(Invoices edit, boolean hasPushData) {
        if (edit == null) {
            throw new BusinessException("没有可验证的发票");
        }
        Invoices innerInvoice = invoicesService.get(edit.getId());
        if (innerInvoice == null) {
            innerInvoice = invoicesService.getByCorderSn(edit.getCOrderSn());
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

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            //edit.setState(InvoiceConst.COMPLETE_STATE);
        	invoicesService.update(edit);
            shopInvoiceApiLogsService.insert(apiLogs);
            String eaiWriteState = edit.getEaiWriteState();
            //order2Ths:差异网单
            //order4Invoices：专项开票
            //orderProduct：普通网单
            if (InvoiceConst.INVALID_KP_STATE.equals(eaiWriteState)) {
                ///*如果是返回的是当月作废*/
                //_invalidStateSync(edit);
                orderProduct(edit, InvoiceConst.MR_STATE_NULLIFY, "'当月作废'");
                order2Ths(edit, InvoiceConst.R_NULLIFY);
                order4Invoices(edit, InvoiceConst.R_NULLIFY);
            } else if (InvoiceConst.MONTH_INVALID_KP_STATE.equals(eaiWriteState)) {
                /*如果是返回的是跨月冲红*/
                //_monthInvalidStateSync(edit);
                orderProduct(edit, InvoiceConst.MR_STATE_RED, "'跨月冲红'");
                order2Ths(edit, InvoiceConst.R_RED);
                order4Invoices(edit, InvoiceConst.R_RED);
            } else if (InvoiceConst.COMPLETE_STATE.equals(edit.getState())) {
                /*如果是已开票则更新网单表或差异网单表中的开票信息*/
                // _completeStateSync(edit);
                memberInvoice(edit);
                orderProduct(edit, InvoiceConst.MR_STATE_MAKED, "'已开票'");
                order2Ths(edit, InvoiceConst.R_RECEIPTED);
                order4Invoices(edit, InvoiceConst.R_RECEIPTED);
                if (InvoiceConst.DIFF_CORDER_TYPE.equals(edit.getCOrderType())) {
                    // 差异订单开票前需要先将订单信息传SAP
                    // Order2ths::sendSAP($invoice->diffId);   //只在开票时传sap，作废时不需要再传
                    //向SAP传递开票信息先插入表`invoice_electric_2_out`,sendType=SAP，另外写job读取该表数据执行传SAP功能
                    insertInvoiceElectric2Out(edit.getId(), "SAP");
                }
            }

            Integer pushType = OrderBizHelper.getInvoiceSAPLogsOfPushType(edit, orderProductsNewService);
            boolean sendSap = OrderBizHelper.handelInvoiceSAPLogs(edit, pushType, orderProductsNewService,
            		shopInvoiceSAPLogsService, this.getPathByName("/upfiles/sapblacklist.txt"));

            //            InvoiceSAPLogs invoiceSAPLogs = OrderBizHelper.sapGenerate(invoiceSAPLogsDao,
            //                orderProductsDao, edit, 0, this.getPathByName("/upfiles/sapblacklist.txt"));
            //            boolean sendSap = false;
            //            if (invoiceSAPLogs != null) {
            //                int n = invoiceSAPLogsDao.insert(invoiceSAPLogs);
            //                if (n < 1) {
            //                    log.error("插入invoiceSAPLogs信息失败，发票id:" + edit.getId());
            //                } else {
            //                    sendSap = true;
            //                }
            //            }
            if (!sendSap) {
                log.error("开票时，sapGenerate更新电子发票，未推送Sap开票信息，发票id:" + edit.getId());
            }
            transactionManager.commit(status);
        } catch (Exception e) {
            log.error("更新发票发生异常:", e);
            transactionManager.rollback(status);
            throw new BusinessException("更新发票发生异常");
        }
    }

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
        invoiceElectric2OutService.insert(invoiceElectric2Out);
    }

    private String getPathByName(String name) {
        return this.getClass().getResource(name).getPath();
    }

    public void syncInvoiceFromTax(Invoices queryInvoice) throws BusinessException {
        Invoices invoices = this.invoicesService.get(queryInvoice.getId());
        if (invoices == null) {
            throw new BusinessException("同步的发票信息不存在");
        }
        String receiveData = null;
        Invoices entity = null;

        QueryInvoiceInputType inputType = new QueryInvoiceInputType();
        inputType.setWdh(invoices.getCOrderSn());
        ServiceResult<InvoiceEntity> queryResult = this.invoiceToTaxService
            .queryInvoiceToTaxSys(inputType);

        if (queryResult.getSuccess()) {
            InvoiceEntity resultInvoice = queryResult.getResult();
            entity = new Invoices();
            entity.setId(queryInvoice.getId());
            entity.setState(resultInvoice.getFpzt().intValue());
            entity.setEaiWriteState(
                resultInvoice.getKpzt() == null || resultInvoice.getKpzt().equals(0) ? ""
                    : resultInvoice.getKpzt().toString());
            entity.setDrawer(resultInvoice.getKpman());
            entity.setInvoiceNumber(resultInvoice.getFphm());
            entity.setBackupFieldA(resultInvoice.getAdd1());
            entity.setBillingTime(Long.parseLong(Ustring.getString(resultInvoice.getKprq() == null ? null : resultInvoice.getKprq().getTime() / 1000)));
            entity.setEaiWriteTime(Long.parseLong(Ustring.getString(resultInvoice.getSkrq() == null ? null : resultInvoice.getSkrq().getTime() / 1000)));
            entity.setInvalidTime(Long.parseLong(Ustring.getString(
                resultInvoice.getZfrq() == null ? null : resultInvoice.getZfrq().getTime() / 1000)));
            entity.setSuccess(InvoiceConst.SUCCESS.intValue());
        }
        receiveData = queryResult.getSuccess() ? "" : "响应失败";
        receiveData = StringUtil.isEmpty(receiveData) ? ((entity == null) ? "响应结果为空" : "") : "";
        if (!StringUtil.isEmpty(receiveData)) {
            throw new BusinessException("同步EAI状态失败");
        }

        this.updateInvoiceStatus(entity, true);

    }

    private void memberInvoice(Invoices invoice) throws BusinessException {
        Integer memberInvoiceId = invoice.getMemberInvoiceId();
        MemberInvoices memberInvoice = memberInvoicesService.get(memberInvoiceId);
        if (memberInvoice == null) {
            return;//throw new BusinessException("没有要更新的发票, memberInvoiceId=" + invoice.getMemberInvoiceId());
        }
        //开票后锁定发票信息
        MemberInvoices tempMemberInvoice = new MemberInvoices();
        tempMemberInvoice.setIsLock(1);//锁定member invoice
        tempMemberInvoice.setId(memberInvoiceId);
        memberInvoicesService.updateForsynInvoices(tempMemberInvoice);

    }

    private void orderProduct(Invoices invoice, Integer makeReceipt,
                              String remark) throws BusinessException {
        if (Invoices.CORDERTYPE_COMMON_CORDER_TYPE != invoice.getCOrderType().intValue()) {
            return;
        }
        Integer orderProductId = invoice.getOrderProductId();
        OrderProductsNew orderProduct = orderProductsNewService.get(orderProductId);
        //判断网单是否存在
        if (orderProductId != null && orderProductId > 0 && orderProduct != null) {
            OrderProductsNew tempOrderProduct = new OrderProductsNew();
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
            orderProductsNewService.updateForsyncInvoice(tempOrderProduct);

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

            shopOrderOperateLogsService.insert(operateLogs);

            //修改退货申请的发票信息
            List<OrderRepairsNew> orderRepairsList = orderRepairsNewService
                .getByOrderProductId(orderProductId);
            if (orderRepairsList == null || orderRepairsList.size() <= 0) {
                return;
            }
            for (OrderRepairsNew orderRepairs : orderRepairsList) {
                if (orderRepairs.getReceiptStatus() == InvoiceConst.RS_CANCELED) {
                    return;
                }
                orderRepairs.setReceiptStatus(InvoiceConst.RS_CANCELED);
                orderRepairs.setReceiptTime((int) new Date().getTime() / 1000);
                orderRepairsNewService.updateForStatus(orderRepairs);
                OrderRepairLogsNew orderRepairsLog = new OrderRepairLogsNew();
                orderRepairsLog.setSiteId(1);
                orderRepairsLog.setAddTime((int) new Date().getTime() / 1000);
                orderRepairsLog.setOrderRepairId(orderRepairs.getId());
                orderRepairsLog.setOperate("rs_cancel");
                orderRepairsLog.setOperator("系统");
                orderRepairsLog.setRemark(
                    "'同步发票状态到 ' " + InvoiceConst.RECEIPT_STATUS.get(InvoiceConst.RS_CANCELED));
                orderRepairLogsNewService.insert(orderRepairsLog);
            }

        }
    }

    private void order2Ths(Invoices invoice, Integer isReceipted) throws BusinessException {
        if (Invoices.CORDERTYPE_DIFF_CORDER_TYPE != invoice.getCOrderType().byteValue()) {
            return;
        }
        Integer diffId = invoice.getDiffId();
        Order2ths order2ths = order2thsService.get(diffId);
        if (order2ths != null && diffId != null && diffId > 0) {
            Order2ths order2Ths = new Order2ths();
            order2Ths.setIsReceipted(isReceipted);//R_NULLIFY
            order2Ths.setId(diffId);
            order2thsService.updateForsynInvoices(order2Ths);
        }

    }

    private void order4Invoices(Invoices invoice, Integer isReceipted) throws BusinessException {
        if (Invoices.CORDERTYPE_SPECIAL_CORDER_TYPE != invoice.getCOrderType().byteValue()) {
            return;
        }
        Integer diffId = invoice.getDiffId();
        Order4Invoices order4invoices = order4InvoicesService.get(diffId);
        if (order4invoices != null && diffId != null && diffId > 0) {
            Order4Invoices order4Invoices = new Order4Invoices();
            order4Invoices.setIsReceipted(isReceipted);//R_NULLIFY
            order4Invoices.setId(diffId);
            Integer effect = order4InvoicesService.updateForsynInvoices(order4Invoices);
            if (effect < 0) {
                throw new BusinessException("没有专项开票可更新, diffId=" + invoice.getDiffId());
            }
        }

    }

    /**
     * 查询invoice_electric_2_out表发票数据推送HP系统，创建多线程
     */
    public void syncElectricInvoiceToHp() {
        //1、获取开票信息待传HP队列
        List<InvoiceElectric2Out> list = invoiceElectric2OutService.getSendToHpList(1000);
        if (list == null || list.size() == 0) {
            log.info("创建发票发送HP队列：没有需要处理的记录。");
            return;
        }
        //加入多线程执行
        ExecuteSendToHp execute = new ExecuteSendToHp();
        //分组大小100条(如果大于10小于等于100,分两组),加入多线程失败重试3次,如果失败则本线程自己执行
        int splitSize = 100;
        int size = list.size();
        if (size > 10 && size <= splitSize) {
            splitSize = size / 2 + 1;
        }
        new MultiThreadTool<InvoiceElectric2Out>().processJobs((IExcute) execute, threadHelper, log, list,
            splitSize, 3);
    }

    /**
     * 查询invoice_electric_2_out表差异订单数据推送SAP系统，创建多线程
     */
    public void syncElectricInvoiceOrder2thsToSap() {
        //1、获取开票信息待传HP队列
        List<InvoiceElectric2Out> list = invoiceElectric2OutService.getSendToSapList(1000);
        if (list == null || list.size() == 0) {
            log.info("创建差异订单发送SAP队列：没有需要处理的记录。");
            return;
        }
        //加入多线程执行
        ExecuteSendToSap execute = new ExecuteSendToSap();
        //分组大小100条(如果大于10小于等于100,分两组),加入多线程失败重试3次,如果失败则本线程自己执行
        int splitSize = 100;
        int size = list.size();
        if (size > 10 && size <= splitSize) {
            splitSize = size / 2 + 1;
        }
        new MultiThreadTool<InvoiceElectric2Out>().processJobs(execute, threadHelper, log, list,
            splitSize, 3);
    }

    private String logPrefixHp(String id) {
        return "[inv-to-hp] [" + id + "]";
    }

    private String logPrefixSap(String id) {
        return "[order2ths-to-sap] [" + id + "]";
    }

    public void syncElectricInvoiceToHpThread(List<InvoiceElectric2Out> invoiceElectric2OutList) {
        if (invoiceElectric2OutList != null && invoiceElectric2OutList.size() > 0) {
            for (InvoiceElectric2Out invoiceElectric2Out : invoiceElectric2OutList) {
                if (invoiceElectric2Out == null) {
                    continue;
                }
                try {
                    ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
                    createElectricInvoiceToHp(invoiceElectric2Out);
                } catch (Exception e) {
                    log.error(this.logPrefixHp(invoiceElectric2Out.getInvoiceId().toString())
                              + "发票同步到HP出错",
                        e);
                } finally {
                    ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
                }
            }
        } else {
            log.info("[inv-to-hp] 接收同步到HP的发票列表数据为空。");
        }
    }

    public void syncElectricInvoiceOrder2thsToSapThread(List<InvoiceElectric2Out> invoiceElectric2OutList) {
        if (invoiceElectric2OutList != null && invoiceElectric2OutList.size() > 0) {
            for (InvoiceElectric2Out invoiceElectric2Out : invoiceElectric2OutList) {
                if (invoiceElectric2Out == null) {
                    continue;
                }
                try {
                    ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
                    createElectricInvoiceOrder2thsToSap(invoiceElectric2Out);
                } catch (Exception e) {
                    log.error(this.logPrefixHp(invoiceElectric2Out.getInvoiceId().toString())
                              + "差异订单同步到SAP出错",
                        e);
                } finally {
                    ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
                }
            }
        } else {
            log.info("[order2ths-to-sap] 接收同步到SAP的差异订单列表数据为空。");
        }
    }

    /**
     * 往Hp创建电子发票
     * @param invoiceElectric2Out
     */
    private boolean createElectricInvoiceToHp(InvoiceElectric2Out invoiceElectric2Out) {
        Integer invoiceId = invoiceElectric2Out.getInvoiceId();//发票id
        Invoices invoice = invoicesService.get(invoiceId);
        if (invoice == null) {
            invoiceElectric2Out.setLastMessage("发票不存在");
            invoiceElectric2Out.setCount(invoiceElectric2Out.getCount() + 1);
            invoiceElectric2Out.setProcessTime(new Date());
            invoiceElectric2OutService.updateAfterSync(invoiceElectric2Out);
            log.error(this.logPrefixHp("发票ID:" + invoiceId) + "发票不存在");
            return false;//发票不存在
        }

        //生成调用参数
        InvoiceData2HP invoiceData2HP = new InvoiceData2HP();
        invoiceData2HP.setOrderNo(invoice.getCOrderSn());
        invoiceData2HP.setFpSkh(invoice.getFiscalCode());
        invoiceData2HP.setFpNo(invoice.getInvoiceNumber());
        invoiceData2HP.setFpPrice(invoice.getPrice());
        invoiceData2HP.setFpDate(new Date(invoice.getBillingTime() * 1000l));
        invoiceData2HP.setFpCount(new BigDecimal(invoice.getNumber()));
        invoiceData2HP.setFpType(invoice.getElectricFlag() + "");
        invoiceData2HP
            .setFpStatus(invoice.getEaiWriteState() == null || invoice.getEaiWriteState().equals("")
                ? "1" : invoice.getEaiWriteState());
        invoiceData2HP.setProcFlag("");
        invoiceData2HP.setProcRemark("");
        invoiceData2HP.setCreatedDate(new Date());

        ServiceResult<Boolean> result = null;
        try {
            //调用接口
            result = eInvoiceServiceImpl.invoiceInfoToHpSys(invoiceData2HP);
        } catch (Exception e) {
            log.error("开票调用内部HP接口异常：", e);
            result = null;
        }
        boolean flag = false;
        if (result == null) {//失败
            log.error("开票调用内部HP接口错误，网单号：" + invoiceData2HP.getOrderNo());
            invoiceElectric2Out.setLastMessage("开票调用内部HP接口错误");
            invoiceElectric2Out.setCount(invoiceElectric2Out.getCount() + 1);
            invoiceElectric2Out.setProcessTime(new Date());
        } else if (!result.getSuccess()) {//失败
            log.error(
                "开票调用HP接口失败，网单号：" + invoiceData2HP.getOrderNo() + "，返回信息:" + result.getMessage());
            invoiceElectric2Out
                .setLastMessage(result.getMessage() == null || result.getMessage().equals("")
                    ? "开票调用HP接口失败" : result.getMessage());
            invoiceElectric2Out.setCount(invoiceElectric2Out.getCount() + 1);
            invoiceElectric2Out.setProcessTime(new Date());
        } else {//成功
            invoiceElectric2Out.setSuccess(1);
            invoiceElectric2Out.setLastMessage(result.getMessage());
            invoiceElectric2Out.setCount(invoiceElectric2Out.getCount() + 1);
            invoiceElectric2Out.setProcessTime(new Date());
            flag = true;
        }
        invoiceElectric2OutService.updateAfterSync(invoiceElectric2Out);
        return flag;
    }

    /**
     * 往Sap创建差异订单
     * @param invoiceElectric2Out
     */
    private boolean createElectricInvoiceOrder2thsToSap(InvoiceElectric2Out invoiceElectric2Out) {
        Integer invoiceId = invoiceElectric2Out.getInvoiceId();//发票id
        Invoices invoice = invoicesService.get(invoiceId);
        if (invoice == null) {
            invoiceElectric2Out.setLastMessage("发票不存在");
            invoiceElectric2Out.setCount(invoiceElectric2Out.getCount() + 1);
            invoiceElectric2Out.setProcessTime(new Date());
            invoiceElectric2OutService.updateAfterSync(invoiceElectric2Out);
            log.error(this.logPrefixSap("发票ID:" + invoiceId) + "发票不存在");
            return false;//发票不存在
        }
        boolean flag = false;
        //查询差异订单信息
        Order2ths order2ths = order2thsService.get(invoice.getDiffId());
        //生成调用参数
        Orders2thsToSAPzsds0001 pushData = new Orders2thsToSAPzsds0001();
        if (order2ths != null) {
            pushData.setZSYST("EHAI");

            MemberInvoices memberInvoices = memberInvoicesService.get(order2ths.getMemberInvoiceId());
            String invoiceTitle = "";
            if (memberInvoices != null) {
                invoiceTitle = memberInvoices.getInvoiceTitle();
            }
            String paymentCode = "";
            paymentCode = order2ths.getPaymentCode();

            pushData.setKUNNRAG(getCustomerCode(paymentCode, invoiceTitle));
            pushData.setKUNNRRG("");
            pushData.setZWBDR(order2ths.getOrderSn());
            String zwbdtdate = order2ths.getAddTime();
            zwbdtdate = zwbdtdate == null ? DateFormatUtil.formatByType("yyyy-MM-dd", new Date())
                : zwbdtdate;
            zwbdtdate = zwbdtdate.replaceAll("/", "-");
            String datetype = "yyyy-MM-dd";
            datetype = !zwbdtdate.contains("-") ? "yyyyMMdd" : datetype;

            pushData.setZWBDT(DateFormatUtil.formatByType("yyyy-MM-dd",
                DateFormatUtil.parseByType(datetype, zwbdtdate)));
            pushData.setAUGRU("");
            pushData.setMATNR(order2ths.getSku());
            pushData.setKWMENG(new BigDecimal(order2ths.getNumber()));
            pushData.setKBETR(order2ths.getPrice());
            pushData.setLGORT(order2ths.getsCode());
            pushData.setZORDR(order2ths.getOrderSn());
            pushData.setZCHNL("DS03");

            Map<String, Orders2thsToSAPzsds0001> pushDataMap = new HashMap<String, Orders2thsToSAPzsds0001>();
            ServiceResult<String> result = null;
            try {
                order2ths
                    .setSapCount(order2ths.getSapCount() == null ? 0 : order2ths.getSapCount() + 1);
                order2ths.setSapLastTime((int) (new Date().getTime() / 1000));
                pushDataMap.put("T_ZSDS0001", pushData);
                order2ths.setSapPushData(JsonUtil.toJson(pushDataMap));
                //调用接口
//                result = invoiceToSAPService.sendOrder2thsToSap(invoice.getcOrderSn(), pushData);
            } catch (Exception e) {
                order2ths.setSapReturnData(e.getMessage());
                order2ths.setSapSuccess(0);
                log.error("差异订单调用内部base-SAP接口异常：", e);
                result = null;
                flag = false;
            }
            //            if (result.    rs->T_MSG)) {
            //                $order2th->sapSuccess = 1;
            //                $order2th->sapReturnData = serialize($rs);
            //                $return = true;
            //             }

            if (result == null) {//失败
                log.error("差异订单调用内部SAP接口错误，网单号：" + order2ths.getOrderSn());
                invoiceElectric2Out.setLastMessage("差异订单调用内部SAP接口错误");
                invoiceElectric2Out.setCount(invoiceElectric2Out.getCount() + 1);
                invoiceElectric2Out.setProcessTime(new Date());
            } else if (!result.getSuccess()) {//失败
                log.error(
                    "差异订单调用SAP接口失败，网单号：" + order2ths.getOrderSn() + "，返回信息:" + result.getMessage());
                invoiceElectric2Out
                    .setLastMessage(result.getMessage() == null || result.getMessage().equals("")
                        ? "差异订单调用SAP接口失败" : result.getMessage());
                invoiceElectric2Out.setCount(invoiceElectric2Out.getCount() + 1);
                invoiceElectric2Out.setProcessTime(new Date());
            } else {
                if (result.getResult() == null) {//失败    Success=1,Result=null:接口对象返回信息异常
                    log.error("差异订单调用SAP接口返回信息异常，网单号：" + order2ths.getOrderSn());
                    invoiceElectric2Out.setLastMessage(
                        result.getMessage() == null || result.getMessage().equals("")
                            ? "差异订单调用SAP接口返回信息异常" : result.getMessage());
                    invoiceElectric2Out.setCount(invoiceElectric2Out.getCount() + 1);
                    invoiceElectric2Out.setProcessTime(new Date());
                } else {//成功接收接口返回信息
                    Map<String, Object> returnDataMap = (Map) JsonUtil.fromJson(result.getResult());
                    List<Map<String, Object>> tmsg2 = (List) returnDataMap.get("T_MSG");
                    String msg = JsonUtil.toJson(returnDataMap.get("T_MSG"));

                    if (tmsg2 == null || tmsg2.size() <= 0) {
                        log.error("差异订单调用SAP接口EAI返回为空，网单号：" + order2ths.getOrderSn());
                        invoiceElectric2Out.setLastMessage("差异订单调用SAP接口EAI返回为空");
                        invoiceElectric2Out.setCount(invoiceElectric2Out.getCount() + 1);
                        invoiceElectric2Out.setProcessTime(new Date());
                    } else {
                        for (int i = 0; i < tmsg2.size(); i++) {
                            if (tmsg2.get(i) instanceof Map) {
                                Map<String, Object> map2 = tmsg2.get(i);
                                flag = "E".equalsIgnoreCase(map2.get("type") + "") ? false : true;
                                result.setMessage(result.getMessage() + "|" + map2.get("message"));
                            } else {
                                log.error("差异订单推送Sap系统，更新处理结果异常：Sap系统返回类型不正确！ 发票ID:" + invoiceId);
                            }
                        }
                        if (flag) {
                            invoiceElectric2Out.setSuccess(1);
                        }
                        invoiceElectric2Out.setLastMessage(result.getMessage());
                        invoiceElectric2Out.setCount(invoiceElectric2Out.getCount() + 1);
                        invoiceElectric2Out.setProcessTime(new Date());
                        if (flag) {//返回成功
                            order2ths.setSapSuccess(1);
                            order2ths.setSapReturnData(msg);
                        }
                        flag = true;
                    }
                }
            }

            //开启事务
            DefaultTransactionDefinition def2 = new DefaultTransactionDefinition();
            def2.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus transtatus2 = transactionManager.getTransaction(def2);
            try {
            	invoiceElectric2OutService.updateAfterSync(invoiceElectric2Out);
                if (flag) {
                	order2thsService.update(order2ths);
                }
                // 提交事务
                transactionManager.commit(transtatus2);
                flag = true;
            } catch (Exception e) {
                // 回滚事务
                transactionManager.rollback(transtatus2);
                log.error("推送Sap系统-差异订单-更新处理结果异常：", e);
                flag = false;
            }
        } else {
            log.error(this.logPrefixSap("发票ID:" + invoiceId) + "差异订单信息不存在");
            flag = false;
        }

        return flag;
    }

//    public String getCustomerCode(String paymentCode, String invoiceTitle) {
//        String code = "";
//        if ("insidestatement".equals(paymentCode) || "prepaid".equals(paymentCode)) {
//            List<String> strlist = customerCodeDao.getCustomerCode(invoiceTitle);
//            if (strlist != null && strlist.size() > 0) {
//                String customerCode = (String) strlist.get(0);
//                if (customerCode == null) {
//                    code = "A83";
//                } else {
//                    code = customerCode;
//                }
//            }
//        } else {
//            code = "A83";
//        }
//        return code;
//    }

    private class ExecuteSendToHp implements IExcute {

        @SuppressWarnings("unchecked")
        @Override
        public void execute(Object obj) {
            List<InvoiceElectric2Out> list = (List<InvoiceElectric2Out>) obj;
            try {
                syncElectricInvoiceToHpThread(list);
            } catch (Exception e) {
                log.error("创建发票发送HP队列,发生异常：", e);
            }
        }

    }

    private class ExecuteSendToSap implements IExcute {

        @SuppressWarnings("unchecked")
        @Override
        public void execute(Object obj) {
            List<InvoiceElectric2Out> list = (List<InvoiceElectric2Out>) obj;
            try {
                syncElectricInvoiceOrder2thsToSapThread(list);
            } catch (Exception e) {
                log.error("创建差异订单发送Sap队列,发生异常：", e);
            }
        }

    }
    public String getCustomerCode(String paymentCode, String invoiceTitle) {
        String code = "";
        if ("insidestatement".equals(paymentCode) || "prepaid".equals(paymentCode)) {
            List<String> strlist = customerCodesService.getCustomerCode(invoiceTitle);
            if (strlist != null && strlist.size() > 0) {
                String customerCode = (String) strlist.get(0);
                if (customerCode == null) {
                    code = "A83";
                } else {
                    code = customerCode;
                }
            }
        } else {
            code = "A83";
        }
        return code;
    }

}
