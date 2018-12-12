package com.haier.afterSale.model;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.dubbo.remoting.exchange.Response;
import com.haier.shop.model.GroupOrders;
import com.haier.shop.model.InvoiceApiLogs;
import com.haier.shop.model.InvoiceChangeLogs;
import com.haier.shop.model.InvoiceConst;
import com.haier.shop.model.InvoiceElectric2Out;
import com.haier.shop.model.InvoiceElectricLogs;
import com.haier.shop.model.InvoiceElectricSyncLogs;
import com.haier.shop.model.InvoiceEntity;
import com.haier.shop.model.InvoiceOutType;
import com.haier.shop.model.InvoiceQueue;
import com.haier.shop.model.InvoiceSAPLogs;

import com.haier.shop.model.Invoices;
import com.haier.shop.model.MemberInvoices;
import com.haier.shop.model.Order2ths;
import com.haier.shop.model.Order4Invoices;
import com.haier.shop.model.OrderOperateLogs;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrderRepairLogsNew;
import com.haier.shop.model.OrderRepairsNew;
import com.haier.shop.model.OrderSource;
import com.haier.shop.model.OrderType;
import com.haier.shop.model.OrdersNew;
import com.haier.shop.model.ProductActivities;
import com.haier.shop.model.ProductTypesNew;
import com.haier.shop.model.ProductsNew;
import com.haier.shop.model.SmsLogs;
import com.haier.shop.service.CustomerCodesService;
import com.haier.shop.service.GroupOrdersService;
import com.haier.shop.service.InvoiceChangeLogsService;
import com.haier.shop.service.InvoiceElectric2OutService;
import com.haier.shop.service.InvoiceElectricLogsService;
import com.haier.shop.service.InvoiceElectricSyncLogsService;
import com.haier.shop.service.InvoiceQueueService;
import com.haier.shop.service.InvoicesService;
import com.haier.shop.service.MemberInvoicesService;
import com.haier.shop.service.Order2thsService;
import com.haier.shop.service.Order4InvoicesService;
import com.haier.shop.service.OrderProductsNewService;
import com.haier.shop.service.OrderRepairLogsNewService;
import com.haier.shop.service.OrderRepairsNewService;
import com.haier.shop.service.OrdersNewService;
import com.haier.shop.service.ProductActivitiesService;
import com.haier.shop.service.RegionsService;
import com.haier.shop.service.ShopInvoiceApiLogsService;
import com.haier.shop.service.ShopInvoiceSAPLogsService;
import com.haier.shop.service.ShopOrderOperateLogsService;
import com.haier.shop.service.ShopTaoBaoGroupsService;
import com.haier.shop.service.SmsLogsWriteService;
import com.haier.stock.model.OrderProductStatus;
import com.haier.stock.model.OrderStatus;


import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.afterSale.helper.MultiThreadTool;
import com.haier.afterSale.helper.OrderBizHelper;
import com.haier.afterSale.helper.ThreadHelper;
import com.haier.afterSale.service.StockCenterEInvoiceService;
import com.haier.afterSale.service.StockCenterInvoiceToTaxService;
import com.haier.afterSale.service.StockCenterItemService;
import com.haier.afterSale.services.StockCenterEInvoiceV2ServiceImpl;
import com.haier.afterSale.util.DateFormatUtil;
import com.haier.afterSale.util.IExcute;
import com.haier.afterSale.util.ReadWriteRoutingDataSourceHolder;
import com.haier.afterSale.util.SerializedPhpParser;
import com.haier.afterSale.util.SmsTemplateConst;
import com.haier.afterSale.util.StrTools;
import com.haier.afterSale.util.UnmarshalXmlUtil;
import com.haier.afterSale.util.Ustring;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;



/**
 * 发票相关逻辑
 *                       
 * @Filename InvoiceModel.java
 *
 * @Description 
 *
 * @Version 1.0
 *
 * @Author Benio
 *
 * @Email zhouzhy@haier.com
 *       
 * @History
 *<li>Author:  Benio</li>
 *<li>Date: 2014-8-6</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
@Service
public class InvoiceModel {
    private static org.apache.log4j.Logger log          = org.apache.log4j.LogManager
                                                            .getLogger(InvoiceModel.class);
    @Autowired
    private ThreadHelper                   threadHelper;
    private DataSourceTransactionManager   transactionManager;
    @Autowired
    private OrderProductsNewService orderProductsDao;
    @Autowired
    private OrdersNewService ordersDao;
    @Autowired
    private MemberInvoicesService memberInvoicesDao;
    @Autowired
    private InvoiceQueueService invoiceQueueDao;
    @Autowired
    private InvoicesService invoicesDao;
    @Autowired
    private ShopOrderOperateLogsService orderOperateLogsDao;
    @Autowired
    private ProductActivitiesService productActivitiesDao;
    @Autowired
    private GroupOrdersService groupOrdersDao;
//    @Autowired
//    private ShopTaoBaoGroupsService taoBaoGroupsDao;
    @Autowired
    private OrderRepairsNewService orderRepairsDao;
    @Autowired
    private OrderRepairLogsNewService orderRepairLogsDao;
    @Autowired
    private StockCenterItemService                    itemService;
    @Autowired
    private CustomerCodesService customerCodeDao;
    @Autowired
    private Order2thsService order2thsDao;
    @Autowired
    private Order4InvoicesService order4InvoicesDao;
    @Autowired
    private RegionsService regionsDao;
    @Autowired
    private InvoiceElectricLogsService invoiceElectricLogsDao;
    @Autowired
    private ShopInvoiceApiLogsService invoiceApiLogsDao;
    @Autowired
    private StockCenterInvoiceToTaxService            invoiceToTaxService;
    @Autowired
    private StockCenterEInvoiceService                eInvoiceService;
    @Autowired
    private StockCenterEInvoiceV2ServiceImpl              eInvoiceV2ServiceImpl;
    @Autowired
    private InvoiceElectricSyncLogsService invoiceElectricSyncLogsDao;
    @Autowired
    private InvoiceElectric2OutService invoiceElectric2OutDao;
    @Autowired
    private SmsLogsWriteService smsLogsWriteDao;
    @Autowired
    private ShopInvoiceSAPLogsService invoiceSAPLogsDao;
    @Autowired
    private InvoiceChangeLogsService invoiceChangeLogsDao;
//    @Autowired
//    private DataSourceTransactionManager   transactionManagerShop;
    //电子发票配置信息  平台编码
    private String                         platformcode = "PT000002";
    //平台纳税人身份标识
    private String                         shopcode     = "ehaier";
    private static BigDecimal              amount       = new BigDecimal(100000);
    private String logPrefix(String id) {
        return "[inv-to-kpsys] [" + id + "]";
    }

    /**
     * 发送电子发票相关请求(走java开票队列，不在实时调用接口)
     * @param
     * @param
     */
    private void eInvoiceSendPhp(Invoices invoices, InvoiceElectricLogs eInvoiceLog) {
        switch (eInvoiceLog.getType()) {
            case 1:
                invoices.setTryNum(1);
                invoicesDao.update(invoices);
                break;
            case 2:
                invoices.setStatusType(4);
                invoices.setTryNum(0);
                invoices.setSuccess(0);
                invoicesDao.update(invoices);
                break;
            default:
                break;
        }
    }

    /**
     * 发票强制作废接口
     * @param
     * @throws Exception
     */
    public void invalidInvoice(Invoices invoices) throws Exception {
        if (invoices.getState() != 4) {
            throw new Exception("已开票发票才能作废");
        }
        if (invoices.getElectricFlag() == 1) {
            OrderProductsNew orderProducts = orderProductsDao.get(invoices.getOrderProductId());
            InvoiceElectricLogs eInvoiceLog = new InvoiceElectricLogs();
            eInvoiceLog.setAddTime((int) (new Date().getTime() / 1000));
            eInvoiceLog.setOrderProductId(invoices.getOrderProductId());
            eInvoiceLog.setCOrderSn(orderProducts.getCOrderSn());
            eInvoiceLog.setInvoiceId(invoices.getId());
            eInvoiceLog.setType(2);
            eInvoiceLog.setPushData("");
            eInvoiceLog.setLastMessage("写入作废队列成功");
            eInvoiceLog.setSuccess(1);
            invoiceElectricLogsDao.insertLog(eInvoiceLog);
            this.eInvoiceSendPhp(invoices, eInvoiceLog);
            return;
        }
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        invoiceEntity.setWdh(invoices.getCOrderSn());
        invoiceEntity.setRrrq(new Date());
        ServiceResult<InvoiceOutType> invalidInvoiceToTaxSys = null;
        try {
            invalidInvoiceToTaxSys = invoiceToTaxService.invalidInvoiceToTaxSys(invoiceEntity);
        } catch (Exception e) {
            this.insertApiLogs(invoices, JsonUtil.toJson(invoiceEntity), 0, "推送参数异常");
            throw new Exception("推送参数异常");
        }
        if (invalidInvoiceToTaxSys != null && invalidInvoiceToTaxSys.getResult() != null
            && "S".equals(invalidInvoiceToTaxSys.getResult().getFlag())) {
            this.insertApiLogs(invoices, JsonUtil.toJson(invoiceEntity), 1, "");
        } else {
            String msg = "";
            if (invalidInvoiceToTaxSys != null && invalidInvoiceToTaxSys.getResult() != null) {
                msg = invalidInvoiceToTaxSys.getResult().getMsg();
            }
            this.insertApiLogs(invoices, JsonUtil.toJson(invoiceEntity), 0, msg);
        }
    }

    private void insertApiLogs(Invoices invoices, String data, Integer isSuccess, String returnData) {
        InvoiceApiLogs apilogs = new InvoiceApiLogs();
        apilogs.setType(4);
        apilogs.setOrderProductId(invoices.getOrderProductId());
        apilogs.setCOrderSn(invoices.getCOrderSn());
        apilogs.setInvoiceId(invoices.getId());
        apilogs.setPushData(data);
        apilogs.setCountNum(1);
        apilogs.setReturnData(returnData);
        apilogs.setIsSuccess(isSuccess);
        invoiceApiLogsDao.insert(apilogs);
    }

    /**
     * 金税开票信息修改/取消开票接口
     * @param invoices
     * @throws Exception
     */
    public void editInvoice(Invoices invoices, Integer status, Integer orderId) throws Exception {
        if (invoices.getElectricFlag() == 1) {
            throw new Exception("电子发票不能修改发票信息，请作废后重新开票！");
        }
        if (status == 5 && invoices.getState() == 4) {
            throw new Exception("已开票状态不能正常取消发票");
        }

        OrdersNew orders = ordersDao.get(orderId);
        String[] allowSourceArray = new String[] { "TBSC", "TOPFENXIAO", "TOPBUYBANG", "TOPBOILER",
                "TOPSHJD", "TOPMOBILE", "TOPPAD" };
        String suff = "";
        if (orders != null && Arrays.asList(allowSourceArray).contains(orders.getSource())) {
            suff = "T";
        }
        InvoiceEntity inputType = new InvoiceEntity();
        inputType.setWdh(invoices.getCOrderSn());
        inputType.setKhbm(invoices.getCustomerCode());
        inputType.setKhmc(invoices.getInvoiceTitle());
        inputType.setKhsh(invoices.getTaxPayerNumber());
        inputType.setKhdz(invoices.getRegisterAddressAndPhone());
        inputType.setKhkhyhzh(invoices.getBankNameAndAccount());
        inputType.setBz(invoices.getCOrderSn() + suff);
        inputType.setWdrq(new Date());
        inputType.setCpdm(invoices.getSku());
        inputType.setCpmc(invoices.getPaymentName());
        inputType.setCpxh(invoices.getProductCateName());
        inputType.setCpdw(invoices.getUnit());
        inputType.setCpsl(new BigDecimal(invoices.getNumber()));
        inputType.setHsdj(invoices.getPrice());
        inputType.setHsje(invoices.getAmount());
        inputType.setBhsdj(invoices.getNonTaxPrice());
        inputType.setBhsje(invoices.getNonTaxAmount());
        inputType.setJsje(invoices.getTaxAmount());
        inputType.setJssl(invoices.getTaxRate());
        inputType.setJfje(invoices.getIntegralAmount());
        inputType.setJlje(invoices.getEnergySavingAmount());
        inputType.setJlbz(invoices.getEnergySavingRemark());
        inputType.setFplx(invoices.getType() == 1 ? 1 : 2);//发票类型(1=增税());2=普税)
        inputType.setFpzt(status);
        inputType.setSkfs(invoices.getPaymentName());
        inputType.setLbjsdh(invoices.getInternalSettlement());
        inputType.setKwbm(invoices.getSCode());
        inputType.setHptx(Integer.parseInt(invoices.getIsTogether().toString()));
        inputType.setDdlx(invoices.getOrderType());
        inputType.setFgsno(invoices.getBranchOfficeCode());
        inputType.setDdhno(invoices.getOrderLineNumber());
        inputType.setWlno(invoices.getLessOrderSn());
        inputType.setAdd1(invoices.getBackupFieldA());
        inputType.setAdd2(invoices.getBackupFieldB());
        inputType.setRrrq(new Date());
        inputType.setGxrq(new Date());
        inputType.setFphm("");
        inputType.setKpman("");
        ServiceResult<InvoiceOutType> modifyInvoiceToTaxSys = null;
        try {
            modifyInvoiceToTaxSys = invoiceToTaxService.modifyInvoiceToTaxSys(inputType);
        } catch (Exception e) {
            this.insertApiLogs(invoices, JsonUtil.toJson(inputType), 0, "推送参数异常");
            throw new Exception("推送参数异常");
        }
        if (modifyInvoiceToTaxSys != null && modifyInvoiceToTaxSys.getResult() != null
            && "S".equals(modifyInvoiceToTaxSys.getResult().getFlag())) {
            this.insertApiLogs(invoices, JsonUtil.toJson(inputType), 1, "");
        } else {
            String msg = "";
            if (modifyInvoiceToTaxSys != null && modifyInvoiceToTaxSys.getResult() != null) {
                msg = modifyInvoiceToTaxSys.getResult().getMsg();
            }
            this.insertApiLogs(invoices, JsonUtil.toJson(inputType), 0, msg);
        }
    }

    /**
     * 查询invoice_queue表代开发票数据，创建多线程
     */
    public void createInvoice() {
        //1、获取待开发票队列
        List<InvoiceQueue> list = invoiceQueueDao.getBySuccess(InvoiceQueue.SUCCESS_NO);
        if (list == null || list.size() == 0) {
            log.info("创建发票队列：没有需要处理的记录。");
            return;
        }
        //加入多线程执行
        ExecuteCreateInvoice executeCreateInvoice = new ExecuteCreateInvoice();
        //分组大小100条(如果大于10小于等于100,分两组),加入多线程失败重试3次,如果失败则本线程自己执行
        int splitSize = 100;
        int size = list.size();
        if (size > 10 && size <= splitSize) {
            splitSize = size / 2 + 1;
        }
        new MultiThreadTool<InvoiceQueue>().processJobs((IExcute) executeCreateInvoice, threadHelper, log,
            list, splitSize, 3);
    }

    /**
     * 创建发票信息，将来直接推送给开票系统
     */
    public void createInvoiceThread(List<InvoiceQueue> list) {
        try {
            //强制启用写库
            ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
            //1、获取待开发票队列
            //        List<InvoiceQueue> list = invoiceQueueDao.getBySuccess(InvoiceQueue.SUCCESS_NO);
            if (list == null || list.size() == 0) {
                log.info("创建发票队列：没有需要处理的记录。");
                return;
            }
            //2、逐个生成发票信息，等待推送
            for (InvoiceQueue item : list) {
                Integer opId = item.getOrderProductId();//网单ID
                if (opId.equals(0)) {
                    log.error("创建发票队列：orderProductId不正确，行ID(" + item.getId() + ")");
                    continue;
                }
                //获取网单信息
                OrderProductsNew op = orderProductsDao.get(opId);
                if (op == null) {
                    log.error("创建发票队列：网单不存在，网单ID(" + opId + ")");
                    continue;
                }
                //获取订单信息
                Integer orderId = op.getOrderId();
                OrdersNew order = ordersDao.get(orderId);
                if (order == null) {
                    log.error("创建发票队列：订单不存在，订单ID(" + orderId + ")");
                    continue;
                }
                //订单取消不需要开票
                if (order.getOrderStatus().equals(OrderStatus.OS_CANCEL.getCode())) {
                    OrderOperateLogs log = this.AssemblyOrderOperateLog(order, op, "发票创建通知",
                        "订单取消不开票，所以不往金税传递");
                    orderOperateLogsDao.insert(log);
                    //更新开票队列状态
                    item.setSuccess(1);
                    item.setProcessTime(new Date());
                    item.setLastMessage("订单取消不开票");
                    invoiceQueueDao.updateAfterProccess(item);
                    continue;
                }
                //网单取消不需要开票
                if (op.getStatus().equals(OrderProductStatus.CANCEL_CLOSE.getCode())) {
                    OrderOperateLogs log = this.AssemblyOrderOperateLog(order, op, "发票创建通知",
                        "网单取消不开票，所以不往金税传递");
                    orderOperateLogsDao.insert(log);
                    //更新开票队列状态
                    item.setSuccess(1);
                    item.setProcessTime(new Date());
                    item.setLastMessage("网单取消不开票");
                    invoiceQueueDao.updateAfterProccess(item);
                    continue;
                }
                //获取订单发票信息
                MemberInvoices mi = memberInvoicesDao.getByOrderId(orderId);
                if (mi == null) {
                    log.error("创建发票队列：订单发票信息不存在，订单ID(" + orderId + ")");
                    OrderOperateLogs log = this.AssemblyOrderOperateLog(order, op, "发票创建通知",
                        "根据订单未找到发票信息，不开票");
                    orderOperateLogsDao.insert(log);
                    //更新开票队列状态
                    item.setSuccess(1);
                    item.setProcessTime(new Date());
                    item.setLastMessage("根据订单未找到发票信息，不开票");
                    invoiceQueueDao.updateAfterProccess(item);
                    continue;
                }
                //网单不需要开票
                if (!op.getIsReceipt().equals(1)) {
                    OrderOperateLogs log = this.AssemblyOrderOperateLog(order, op, "发票创建通知",
                        "网单不需要发票，所以不往金税传递");
                    orderOperateLogsDao.insert(log);
                    //更新开票队列状态
                    item.setSuccess(1);
                    item.setProcessTime(new Date());
                    item.setLastMessage("不需要开票");
                    invoiceQueueDao.updateAfterProccess(item);
                    continue;
                }
                //如果是共享开票并且不是电子发票，不开票
                if (op.getMakeReceiptType().equals(2) && !mi.getElectricFlag().equals(1)) {
                    //记录开票操作日志
                    OrderOperateLogs log = this.AssemblyOrderOperateLog(order, op, "发票创建通知",
                        "共享开票并且不是电子发票，不开票");
                    orderOperateLogsDao.insert(log);

                    //更新开票队列状态
                    item.setSuccess(1);
                    item.setProcessTime(new Date());
                    item.setLastMessage("共享开票并且不是电子发票，不开票");
                    invoiceQueueDao.updateAfterProccess(item);
                    continue;
                }
                //开票类型是初始值0的不开票
                if (op.getMakeReceiptType().equals(0)) {
                    //记录开票操作日志
                    OrderOperateLogs log = this.AssemblyOrderOperateLog(order, op, "发票创建通知",
                        "开票类型是初始值0的不开票");
                    orderOperateLogsDao.insert(log);

                    //更新开票队列状态
                    item.setSuccess(1);
                    item.setProcessTime(new Date());
                    item.setLastMessage("开票类型是初始值0的不开票");
                    invoiceQueueDao.updateAfterProccess(item);
                    continue;
                }

                //库存类型为STORE、O2O的不开票
                if (op.getStockType() != null
                    && (op.getStockType().equalsIgnoreCase("STORE") || op.getStockType()
                        .equalsIgnoreCase("O2O"))) {
                    //记录开票操作日志
                    OrderOperateLogs log = this.AssemblyOrderOperateLog(order, op, "发票创建通知",
                        "库存类型是STORE、O2O的不开票");
                    orderOperateLogsDao.insert(log);

                    //更新开票队列状态
                    item.setSuccess(1);
                    item.setProcessTime(new Date());
                    item.setLastMessage("库存类型是STORE、O2O的不开票");
                    invoiceQueueDao.updateAfterProccess(item);
                    continue;
                }

                //组织发票信息
                Invoices invoice = this.AssemblyInvoiceInfo(order, op, mi);
                if (invoice == null) {
                    //更新开票队列状态
                    item.setSuccess(1);
                    item.setProcessTime(new Date());
                    item.setLastMessage("已开票或组织发票信息为空");
                    invoiceQueueDao.updateAfterProccess(item);
                    continue;
                }

                //事务处理创建发票信息等逻辑
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                TransactionStatus status = transactionManager.getTransaction(def);
                try {
                    //创建发票信息
                    invoicesDao.insertInvoice(invoice);
                    //更新网单信息
                    op.setIsMakeReceipt(5);//开票中
                    orderProductsDao.updateAfterCreateInvoice(op);
                    //记录开票操作日志
                    OrderOperateLogs log = this.AssemblyOrderOperateLog(order, op, "发票创建通知",
                        "已经插入到推送金税表，等待一分钟后推送金税。");
                    orderOperateLogsDao.insert(log);
                    //更新开票队列状态
                    item.setSuccess(1);
                    item.setProcessTime(new Date());
                    item.setLastMessage("开票成功");
                    invoiceQueueDao.updateAfterProccess(item);
                    //提交事务
                    transactionManager.commit(status);
                } catch (Exception ex) {
                    //回滚事务
                    transactionManager.rollback(status);
                    //更新开票队列状态
                    item.setProcessTime(new Date());
                    String message = ex.getMessage().length() > 200 ? ex.getMessage().substring(0,
                        200) : ex.getMessage();
                    item.setLastMessage(message);
                    invoiceQueueDao.updateAfterProccess(item);
                    //记录日志
                    log.error("创建发票队列：(opId:" + opId + ",orderId:" + orderId + ")，出现未知异常:", ex);
                }
            }
        } catch (Exception ex) {
            //记录日志
            log.error("批量开发票时，出现未知异常:", ex);
        } finally {
            ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
        }
    }

    /**
     * 组织发票信息
     * @param order
     * @param op
     * @param mi
     * @return
     */
    private Invoices AssemblyInvoiceInfo(OrdersNew order, OrderProductsNew op, MemberInvoices mi) {
        if (order == null || op == null || mi == null) {
            log.error("组装发票信息：订单、网单、订单发票对象都不能为null");
            return null;
        }
        //已开过票，不需要开票
        List<Invoices> invList = invoicesDao.getByOrderProductId(op.getId());
        if (invList != null && invList.size() > 0) {
            for (Invoices inv : invList) {
                if (inv.getState().equals(0)//待开票
                    || inv.getState().equals(1)//开票中,商城初次开票推送给EAI的值也为此值
                    || (inv.getState().equals(4) && StringUtil.isEmpty(inv.getEaiWriteState()))) {//已开票,金税开完税后，回写EAI,EAI回写商城这边的值也为此值
                    log.info("此网单不能开票，原因：该网单已在开票中或已开票，网单号：" + op.getCOrderSn());
                    return null;
                }
            }
        }
        //获取产品对象
        ProductsNew product = itemService.getProductBySku(op.getSku()).getResult();
        if (product == null) {
            log.error("组装发票信息：产品不存在，sku（" + op.getSku() + "）");
            return null;
        }
        //获取产品类型
        ProductTypesNew productType = itemService.getProductType(product.getProductTypeId())
            .getResult();

        //计算开票金额小计
        BigDecimal amount = calInvoiceAmount(order, op);

        //        //计算开票金额小计
        //        BigDecimal amount = op.getProductAmount();
        //
        //        //商城万人团订单的金额计算
        //        if (OrderType.TYPE_GROUP_ADVANCE.getValue().equals(order.getOrderType())
        //            && op.getActivityId() > 0) {
        //            //从促销活动表中获取价格
        //            ProductActivities pa = productActivitiesDao.get(op.getActivityId());
        //            BigDecimal paPrice = this.getPriceInActivities(pa);
        //            if (paPrice != null) {
        //                amount = paPrice.multiply(new BigDecimal(op.getNumber()));
        //            }
        //            //如果尾款订单使用了礼券，还得减去礼券的金额
        //            GroupOrders go = groupOrdersDao.getByDepositOrderId(order.getId());//订单尾款订单匹配表
        //            if (go != null) {
        //                //尾款网单
        //                OrderProducts top = orderProductsDao.getByCOrderSn(go.getTailCOrderSn());
        //                if (top != null && top.getCouponAmount().compareTo(BigDecimal.ZERO) > 0) {
        //                    //尾款网单使用了礼券，从总计中扣除
        //                    amount = amount.subtract(top.getCouponAmount());
        //                }
        //            }
        //        }
        //
        //        //天猫定金尾款订单的金额计算 （注：天猫定金尾款订单，不能使用礼券，否则开票金额就错误了！！！！）
        //        if (OrderType.TYPE_GROUP_ADVANCE_TAIL.getValue().equals(order.getOrderType())
        //            && order.getTaobaoGroupId() > 0) {
        //            //从促销活动中获取价格
        //            TaoBaoGroups group = taoBaoGroupsDao.get(order.getTaobaoGroupId());
        //            if (group != null) {
        //                //实现天猫定金尾款订单支持套装
        //                String productSpecs = group.getProductSpecs();
        //                if (productSpecs != null && !productSpecs.equals("")) {
        //                    List<Map<String, Object>> list = JsonUtil.fromJson(productSpecs);
        //                    if (list != null && list.size() > 0) {
        //                        BigDecimal price = new BigDecimal(0);
        //                        BigDecimal depositAmount = new BigDecimal(0);
        //                        BigDecimal tailAmount = new BigDecimal(0);
        //                        boolean flagsku = false;
        //                        for (int i = 0; i < list.size(); i++) {
        //                            if (list.get(i).get("sku") != null
        //                                && op.getSku().equals(list.get(i).get("sku").toString())) {
        //                                depositAmount = new BigDecimal(list.get(i).get("depositAmount")
        //                                    .toString());
        //                                tailAmount = new BigDecimal(list.get(i).get("tailAmount")
        //                                    .toString());
        //                                flagsku = true;
        //                                break;
        //                            }
        //                        }
        //                        //如果group不为空，必须有sku对应网单sku存在，否则报错
        //                        if (!flagsku) {
        //                            log.error("组装发票信息：查找定金尾款金额时，未找到对应网单sku，TaobaoGroupId（"
        //                                      + order.getTaobaoGroupId() + "），sku:" + op.getSku());
        //                            return null;
        //                        }
        //                        price = depositAmount.add(tailAmount);
        //                        amount = price.multiply(new BigDecimal(op.getNumber()));
        //                    } else {
        //                        log.error("组装发票信息：定金尾款金额未找到，TaobaoGroupId（" + order.getTaobaoGroupId()
        //                                  + "）");
        //                        return null;
        //                    }
        //                } else {
        //                    log.error("组装发票信息：定金尾款金额不存在，TaobaoGroupId（" + order.getTaobaoGroupId() + "）");
        //                    return null;
        //                }
        //            }
        //        }
        //
        //        //电子钱包支付订单的金额计算(注：电子钱包支付方式只能单独存在)
        //        Boolean isUsedEWallet = false;
        //        if ("1".equalsIgnoreCase(order.getSource())
        //            && "lejia".equalsIgnoreCase(order.getPaymentCode())) {
        //            //使用了电子钱包支付
        //            isUsedEWallet = true;
        //            BigDecimal price = new BigDecimal("0.01");
        //            amount = price.multiply(new BigDecimal(op.getNumber()));
        //        }
        //        //余额支付订单的金额计算，开票金额扣掉余额支付金额
        //        if (op.getBalanceAmount().compareTo(BigDecimal.ZERO) > 0) {
        //            amount = op.getProductAmount().subtract(op.getBalanceAmount());
        //        }
        //        //节能补贴逻辑的金额计算
        //        BigDecimal energySubsidyAmount = BigDecimal.ZERO;//节能补贴金额
        //        String esRremark = "";//节能补贴描述
        //        if (op.getEsAmount().compareTo(BigDecimal.ZERO) > 0) {
        //            energySubsidyAmount = op.getEsAmount();
        //            amount = amount.add(energySubsidyAmount);
        //            esRremark = String.format("实收%s已补%s", amount, energySubsidyAmount);//实收等于小计-节能补贴
        //        }
        //        //2014-6-4 金额为零的所有网单，改为开1分钱发票 Benio
        //        if (amount.compareTo(BigDecimal.ZERO) == 0) {
        //            BigDecimal price = new BigDecimal("0.01");
        //            amount = price.multiply(new BigDecimal(op.getNumber()));
        //        }

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

        //使用了礼品卡支付
        Boolean isUsedGiftCard = false;
        if (op.getUsedGiftCardAmount().compareTo(BigDecimal.ZERO) > 0) {
            isUsedGiftCard = true;
        }

        //由于使用了电子钱包和礼品卡，修改备注  - 2012-12-20 Benio
        String remark = "";//发票备注
        if (isUsedEWallet) {
            if (isUsedGiftCard) {
                remark = sn + "(DL)";
            } else {
                remark = sn + "(D)";
            }
        } else {
            if (isUsedGiftCard) {
                remark = sn + "(L)";
            } else {
                remark = sn;
            }
        }

        //计算是否货票同行
        Integer isTogether = 1;//货票同行
        if (isReInvoice || op.getMakeReceiptType().equals(2)) {//重开发票，或者中心开票
            isTogether = 2;//非货票同行
        }

        //计算开票库位
        String sCode = op.getSCode();
        if (!StringUtil.isEmpty(op.getTsCode()) && !op.getSCode().equals(op.getTsCode())) {
            //如果是转运网单，则将发票开在转运库
            sCode = op.getTsCode();
        }

        //组织发票信息
        Invoices invoice = new Invoices();
        invoice.setIsReInvoice(Integer.parseInt(Ustring.getString(isReInvoice ? 1 : 0)));
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
        invoice.setNumber(op.getNumber());
        invoice.setTaxRate(new BigDecimal(0.17));
        invoice.setNonTaxPrice(invoice.getPrice().divide(new BigDecimal(1.17), 2,
            RoundingMode.HALF_UP));
        invoice.setNonTaxAmount(invoice.getAmount().divide(new BigDecimal(1.17), 2,
            RoundingMode.HALF_UP));
        invoice.setTaxAmount(invoice.getAmount().subtract(invoice.getNonTaxAmount()));
        invoice.setType(mi.getInvoiceType().intValue());//1增票，2电子，3普票（普税纸质）2015-09-28
        invoice.setIsTogether(isTogether.intValue());
        invoice.setState(0);
        invoice.setLessOrderSn(op.getLessOrderSn());
        invoice.setPaymentName(order.getPaymentName());
        invoice.setSCode(sCode);
        invoice.setOrderType("ZBCC");
        invoice.setInvoiceNumber("");
        invoice.setBillingTime(0L);//0l
        invoice.setEaiWriteTime(0L);//0l
        invoice.setDrawer("");
        invoice.setEaiWriteState("");
        invoice.setInvalidTime(0L);//0l
        invoice.setFirstPushTime(0);
        invoice.setLastModifyTime(0);
        invoice.setInternalSettlement("");
        //2016-7-15 XinM 添加发票码
        invoice.setBranchOfficeCode(product.getConTaxCode());
        invoice.setOrderLineNumber("");
        invoice.setBackupFieldA("");
        invoice.setBackupFieldB(OrderSource.getInstance().getSourceName(order.getSource()));
        invoice.setIntegralAmount(BigDecimal.ZERO);
        invoice.setEnergySavingRemark(esRremark);
        invoice.setStatusType(1);//首次推送
        invoice.setSuccess(0);
        invoice.setAddTime(Long.parseLong(Ustring.getString(new Date().getTime() / 1000)));
        invoice.setTryNum(0);
        invoice.setInvalidReason("");

        /* 如果含税单价乘以数量不等于含税金额，则分摊含税单价，同时更新价格金额其它信息 */
        if (invoice.getPrice().multiply(new BigDecimal(invoice.getNumber()))
            .compareTo(invoice.getAmount()) != 0) {
            invoice.setPrice(invoice.getAmount().divide(new BigDecimal(invoice.getNumber()), 2,
                RoundingMode.HALF_UP));
            invoice.setNonTaxPrice(invoice.getPrice().divide(new BigDecimal(1.17), 2,
                RoundingMode.HALF_UP));
            invoice.setNonTaxAmount(invoice.getAmount().divide(new BigDecimal(1.17), 2,
                RoundingMode.HALF_UP));
            invoice.setTaxAmount(invoice.getAmount().subtract(invoice.getNonTaxAmount()));
        }

        //电子发票，2013-6-30   2015-09-28纸质普票转成电子发票（原来开票时会转）
        invoice.setElectricFlag((mi.getElectricFlag().toString() != null && mi.getElectricFlag().equals(1) ? 1
            : (mi.getInvoiceType() != null && mi.getInvoiceType().equals(2) ? 1 : 0)));

        return invoice;
    }

    /**
     * 获取发票金额(原有计算方式)
     * @param cOrderSn 网单号
     * @return
     */
    public BigDecimal getInvoiceAmount(String cOrderSn) {
        return getInvoiceAmount(cOrderSn, true);
    }

    /**
     * 获取发票金额(新计算方式)
     * @param cOrderSn 网单号
     * @param flag true代表最后如果为0元改为0.01元,false代表不更改,实际价
     * @return
     */
    public BigDecimal getInvoiceAmount(String cOrderSn, boolean flag) {
        if (cOrderSn == null || cOrderSn.equals("")) {
            log.error("获取发票金额：网单号为空，不正确");
            return null;
        }
        //获取网单信息
        OrderProductsNew op = orderProductsDao.getByCOrderSn(cOrderSn);
        if (op == null) {
            log.error("获取发票金额：网单不存在，网单号(" + cOrderSn + ")");
            return null;
        }
        //获取订单信息
        Integer orderId = op.getOrderId();
        OrdersNew order = ordersDao.get(orderId);
        if (order == null) {
            log.error("获取发票金额：订单不存在，订单ID(" + orderId + ")");
            return null;
        }
        return calInvoiceAmount(order, op, flag);
    }

    /**
     * 计算发票金额(原有计算方式)
     * @param order
     * @param op
     * @return
     */
    public BigDecimal calInvoiceAmount(OrdersNew order, OrderProductsNew op) {
        return calInvoiceAmount(order, op, true);
    }

    /**
     * 计算发票金额(新计算方式)
     * @param order
     * @param op
     * @param flag true代表最后如果为0元改为0.01元,false代表不更改,实际价
     * @return
     */
    public BigDecimal calInvoiceAmount(OrdersNew order, OrderProductsNew op, boolean flag) {
        if (order == null || op == null) {
            log.error("计算发票金额：订单、网单对象都不能为null，网单号：" + op.getCOrderSn());
            return null;
        }
        //计算开票金额小计
        BigDecimal amount = op.getProductAmount();

        //商城万人团订单的金额计算
        if (OrderType.TYPE_GROUP_ADVANCE.getValue().equals(order.getOrderType())
            && op.getActivityId() > 0) {
            //从促销活动表中获取价格
            ProductActivities pa = productActivitiesDao.get(op.getActivityId());
            BigDecimal paPrice = this.getPriceInActivities(pa);
            if (paPrice != null) {
                amount = paPrice.multiply(new BigDecimal(op.getNumber()));
            }
            //如果尾款订单使用了礼券，还得减去礼券的金额
            GroupOrders go = groupOrdersDao.getByDepositOrderId(Integer.parseInt(order.getId()));//订单尾款订单匹配表
            if (go != null) {
                //尾款网单
                OrderProductsNew top = orderProductsDao.getByCOrderSn(go.getTailCOrderSn());
                if (top != null && top.getCouponAmount().compareTo(BigDecimal.ZERO) > 0) {
                    //尾款网单使用了优惠券，从总计中扣除
                    amount = amount.subtract(top.getCouponAmount());
                }
                if (top != null && top.getCouponCodeValue().compareTo(BigDecimal.ZERO) > 0) {
                    //尾款网单使用了H码，从总计中扣除
                    amount = amount.subtract(top.getCouponCodeValue());
                }
            }
        }

        //天猫定金尾款订单的金额计算 （注：天猫定金尾款订单，不能使用礼券，否则开票金额就错误了！！！！）,如果取网单金额，定金发货的网单开票时会只开用户付定金那部分
        //2015-8-4 XinM 天猫定金尾款订单的金额重新计算，按照用户付款金额开票（op.getProductAmount），订金发货的网单等尾款支付以后才开票
        //        if (OrderType.TYPE_GROUP_ADVANCE_TAIL.getValue().equals(order.getOrderType())
        //            && order.getTaobaoGroupId() > 0) {
        //            //从促销活动中获取价格
        //            TaoBaoGroups group = taoBaoGroupsDao.get(order.getTaobaoGroupId());
        //            if (group != null) {
        //                //实现天猫定金尾款订单支持套装
        //                String productSpecs = group.getProductSpecs();
        //                if (productSpecs != null && !productSpecs.equals("")
        //                    && !productSpecs.trim().equals("[]")) {
        //                    List<Map<String, Object>> list = JsonUtil.fromJson(productSpecs);
        //                    if (list != null && list.size() > 1) {//字段不为空，并且长度大于1才认为是套装，否则识别为非套装
        //                        BigDecimal price = new BigDecimal(0);
        //                        BigDecimal depositAmount = new BigDecimal(0);
        //                        BigDecimal tailAmount = new BigDecimal(0);
        //                        boolean flagsku = false;
        //                        for (int i = 0; i < list.size(); i++) {
        //                            if (list.get(i).get("sku") != null
        //                                && op.getSku().equals(list.get(i).get("sku").toString())) {
        //                                depositAmount = new BigDecimal(list.get(i).get("depositAmount")
        //                                    .toString());
        //                                tailAmount = new BigDecimal(list.get(i).get("tailAmount")
        //                                    .toString());
        //                                flagsku = true;
        //                                break;
        //                            }
        //                        }
        //                        //如果group不为空，必须有sku对应网单sku存在，否则报错
        //                        if (!flagsku) {
        //                            log.error("组装发票信息：查找定金尾款金额时，未找到对应网单sku，TaobaoGroupId（"
        //                                      + order.getTaobaoGroupId() + "），sku:" + op.getSku());
        //                            return null;
        //                        }
        //                        price = depositAmount.add(tailAmount);
        //                        amount = price.multiply(new BigDecimal(op.getNumber()));
        //                    } else {
        //                        BigDecimal price = group.getDepositAmount().add(group.getBalanceAmount());
        //                        amount = price.multiply(new BigDecimal(op.getNumber()));
        //                    }
        //                } else {
        //                    BigDecimal price = group.getDepositAmount().add(group.getBalanceAmount());
        //                    amount = price.multiply(new BigDecimal(op.getNumber()));
        //                }
        //            }
        //        }

        //电子钱包支付订单的金额计算(注：电子钱包支付方式只能单独存在)
        if ("1".equalsIgnoreCase(order.getSource())
            && "lejia".equalsIgnoreCase(order.getPaymentCode())) {
            BigDecimal price = new BigDecimal("0.01");
            amount = price.multiply(new BigDecimal(op.getNumber()));
        }
        //余额支付订单的金额计算，开票金额扣掉余额支付金额
        if(op.getBalanceAmount()==null){
        	op.setBalanceAmount(new BigDecimal(0));
        }
        if (op.getBalanceAmount().compareTo(BigDecimal.ZERO) > 0) {
            amount = op.getProductAmount().subtract(op.getBalanceAmount());
        }
        //节能补贴逻辑的金额计算
        if (op.getEsAmount().compareTo(BigDecimal.ZERO) > 0) {
            amount = amount.add(op.getEsAmount());
        }
        if (flag) {
            //2014-6-4 金额为零的所有网单，改为开1分钱发票 Benio
            if (amount.compareTo(BigDecimal.ZERO) == 0
                || amount.divide(new BigDecimal(op.getNumber()), 2, RoundingMode.HALF_UP)
                    .compareTo(BigDecimal.ZERO) == 0) {
                BigDecimal price = new BigDecimal("0.01");
                amount = price.multiply(new BigDecimal(op.getNumber()));
            }
        }

        return amount;
    }

    /**
     * 从商城万人团活动中，计算出活动金额
     * @param pa
     * @return
     */
    private BigDecimal getPriceInActivities(ProductActivities pa) {
        if (pa != null && pa.getActivityType().equals(1)) {//商城万人团
            String extensions = pa.getExtensions();
            @SuppressWarnings("rawtypes")
            Map results = (Map) new SerializedPhpParser(extensions).parse();
            @SuppressWarnings("rawtypes")
            Map priceList = (Map) results.get("priceList");
            if (priceList != null) {
                BigDecimal minPrice = BigDecimal.ZERO;
                for (int i = 0; i < priceList.size(); i++) {
                    @SuppressWarnings("rawtypes")
                    BigDecimal p = new BigDecimal(((Map) priceList.get(i)).get("price").toString());
                    if (i == 0) {
                        minPrice = p;
                    } else {
                        if (minPrice.compareTo(p) > 0) {
                            minPrice = p;
                        }
                    }
                }
                if (minPrice.compareTo(BigDecimal.ZERO) > 0) {
                    return minPrice;
                }
            }
        }
        return null;
    }

    /**
     * 同步发票信息到电子发票系统或金穗系统(Invoices表同步到开票系统)-创建多线程
     */
    public void syncToInvoiceSystem() {
        try {
            //1、获取待开发票队列
            List<Invoices> list = invoicesDao.getSyncInvoiceList(500);//php原值300   开票不用太快 平时500，双十一改成1000
            if (list == null || list.size() == 0) {
                log.info("获取待同步开票系统发票信息队列：没有需要处理的记录。");
                return;
            }
            //加入多线程执行
            ExecuteSyncToInvoiceSystem execute = new ExecuteSyncToInvoiceSystem();
            //分组大小100条(如果大于10小于等于100,分两组),加入多线程失败重试3次,如果失败则本线程自己执行
            int splitSize = 100;
            int size = list.size();
            if (size > 10 && size <= splitSize) {
                splitSize = size / 2 + 1;
            }
            new MultiThreadTool<Invoices>().processJobs(execute, threadHelper, log, list,
                splitSize, 3);
        } catch (Exception e) {
            log.error("获取待同步开票系统发票信息队列出现异常：", e);
        }
    }

    /**
     * 同步发票信息到电子发票系统或金穗系统(Invoices表同步到开票系统)
     * 发票推送条件：success=0 and tryNum < 20
     */
    public void syncToInvoiceSystemThread(List<Invoices> list) {
        if (list != null && list.size() > 0) {
            //            log.info("inv-to-tax/einv:list=" + list.size());
            for (Invoices invoices : list) {
                if (invoices == null) {
                    continue;
                }
                try {
                    ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
                    Byte statusType = invoices.getStatusType().byteValue();
                    if (statusType == null) {
                        continue;
                    }
                    switch (statusType) {
                        case 1: //创建发票
                            if ((int) (new Date().getTime() / 1000) < 1451392200) {
                                //升级之前的1.1版本的开票，不再使用
                                syncToInvoiceSystemCreateOld(invoices);
                            } else {
                                syncToInvoiceSystemCreate(invoices);
                            }
                            break;
                        case 2: //修改发票
                            syncToInvoiceSystemUpdate(invoices);
                            break;
                        case 3: //取消发票
                            syncToInvoiceSystemCancel(invoices);
                            break;
                        case 4: //作废发票
                            if ((int) (new Date().getTime() / 1000) < 1451392200) {
                                //升级之前的1.1版本的作废，不再使用
                                syncToInvoiceSystemInvalidOld(invoices);
                            } else {
                                syncToInvoiceSystemInvalid(invoices);
                            }
                            break;
                        default:
                            log.error("获取待同步开票系统发票信息队列：发票状态类型不在处理范围。网单号：" + invoices.getCOrderSn()
                                      + "，发票状态类型：" + statusType);
                            break;
                    }
                } catch (Exception e) {
                    log.error(this.logPrefix(invoices.getOrderProductId().toString())
                              + "发票同步到开票系统异常字符串:" + StrTools.printExceptionStackInfo(e));
                } finally {
                    ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
                }
            }
        } else {
            log.info("获取待同步开票系统发票信息队列：接收同步到开票系统的发票列表数据为空。");
        }
    }

    /**
     * 同步发票信息到电子发票系统或金穗系统(Invoices表同步到开票系统)-开票
     */
    public void syncToInvoiceSystemCreate(Invoices invoices) {
        if (!invoices.getCOrderSn().equals(invoices.getCOrderSn().trim())) {
            invoices.setCOrderSn(invoices.getCOrderSn().trim());
            invoicesDao.update(invoices);
        }
        if (invoices.getFirstPushTime() == null || invoices.getFirstPushTime().equals(0)) {
            invoices.setFirstPushTime((int) (new Date().getTime() / 1000));
        }
        //验证发票数据
        if (!checkInvoiceData(invoices)) {
            log.error("获取待同步开票系统发票信息队列：发票数据验证没有通过，网单id：" + invoices.getOrderProductId());
            //异常发票数据格式问题，记录日志，写次数
            invoices.setTryNum(20);
            invoicesDao.update(invoices);
            return;
        }
        try {

            MemberInvoices memberInvoices = null;
            String mobile = "";
            String consignee = "";
            String industryName = "";
            Integer orderId = 0;
            if (InvoiceConst.COMMON_CORDER_TYPE.equals(invoices.getCOrderType())) {//普通网单
                OrderProductsNew orderProducts = orderProductsDao.get(invoices.getOrderProductId());
                if (orderProducts == null) {
                    log.error("获取待同步开票系统发票信息队列：网单不存在，发票id：" + invoices.getId());
                    invoices
                        .setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                    invoicesDao.update(invoices);
                    return;
                }
                //3W网单特殊处理，数量大于1不开票 xinm 2016-11-18
                if (orderProducts.getStockType() != null
                    && orderProducts.getStockType().equalsIgnoreCase("3W")
                    && invoices.getNumber() != null && invoices.getNumber().intValue() > 1) {
                    log.error("同步开票系统：3W网单商品数据大于1，不开票，网单id：" + invoices.getOrderProductId());
                    //异常发票数据格式问题，记录日志，写次数
                    invoices.setTryNum(20);
                    invoicesDao.update(invoices);
                    return;
                }
                orderId = orderProducts.getOrderId();
                memberInvoices = memberInvoicesDao.getByOrderId(orderProducts.getOrderId());
            } else if (InvoiceConst.DIFF_CORDER_TYPE.equals(invoices.getCOrderType())) {//差异网单
                Order2ths order2ths = order2thsDao.get(invoices.getDiffId());
                memberInvoices = memberInvoicesDao.get(order2ths.getMemberInvoiceId());
                mobile = order2ths.getMobile1();
                consignee = order2ths.getConsignee();
                industryName = order2ths.getIndustryName();
            } else { //专项开票
                Order4Invoices order4Invoices = order4InvoicesDao.get(invoices.getDiffId());
                memberInvoices = memberInvoicesDao.get(order4Invoices.getMemberInvoiceId());
                mobile = order4Invoices.getMobile1();
                consignee = order4Invoices.getConsignee();
                industryName = order4Invoices.getIndustryName();
            }
            //如果发票类型是普通发票，并且电子发票切换标志(ELECTRIC_SWITCH)为true时，所有普票强制改电子发票，要更新数据库电子发票标志
            if (InvoiceConst.ELECTRIC_SWITCH
                && InvoiceConst.GENERAL_INVOICE.equals(memberInvoices.getInvoiceType())) {
                memberInvoices.setElectricFlag(InvoiceConst.ELECTRIC_INVOICE);
                memberInvoicesDao.update(memberInvoices);
            }
            //并发时，过滤同步操作数据库
            if (invoices.getSuccess().equals(1)) {
                log.error("获取待同步开票系统发票信息-开票：发票已经被其他进程执行完成，发票id：" + invoices.getId());
                return;
            }
            //自营转单需求，所有票都给客户开增值税发票，用户发票需要客户开，memberInvoices表中，存储的是用户开票信息，与给客户开增值税发票信息冲突。
            if (invoices.getElectricFlag().equals(1)) {
                //            if (memberInvoices.getElectricFlag().equals(1) || invoices.getElectricFlag().equals(1)) {
                //                // 电子发票
                //                // 生成发票推送日志
                //                if (!(invoices.getElectricFlag() != null && invoices.getElectricFlag().equals(1))) {
                //                    invoices.setElectricFlag(InvoiceConst.ELECTRIC_INVOICE);
                //                    invoicesDao.update(invoices);//后面还会执行更新
                //                }
                OrdersNew order = null;
                if (InvoiceConst.COMMON_CORDER_TYPE.equals(invoices.getCOrderType())) {
                    order = ordersDao.get(orderId);
                } else {
                    order = new OrdersNew();
                    order.setMemberEmail("");
                    order.setMobile(mobile);
                    order.setConsignee(consignee);
                    order.setProvince(0);
                    order.setCity(0);
                    order.setRegion(0);
                    order.setAddress(industryName);
                }
                // 生成报文 -插入或更新电子发票日志
                InvoiceElectricLogs invoiceElectricLogs = eInvoiceGenerate(invoices, order);
                //                log.info("invoiceElectricLogs:" + invoiceElectricLogs.getCOrderSn());
                if (invoiceElectricLogs != null) {
                    // 发送请求
                    boolean result = eInvoiceSend(invoices, invoiceElectricLogs);
                    if (!result) {
                        log.error("创建发票推送失败，请检查网单及发票信息是否完整！发票id：" + invoices.getId());
                        //                        invoices
                        //                            .setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                        invoicesDao.update(invoices);
                    }
                } else {
                    log.error("创建发票信息不完整，请检查网单及发票信息是否完整！发票id：" + invoices.getId());
                    invoices
                        .setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                    invoicesDao.update(invoices);
                }
            } else {//金穗系统开票
                //设置参数
                InvoiceEntity invoiceEntity = AssemblyInvoicesEntityParam(invoices);
                String data = JsonUtil.toJson(invoiceEntity);//生成日志数据
                ServiceResult<InvoiceOutType> result = null;
                String message = "";
                InvoiceApiLogs apilogs = saveInvoiceApiLogs(invoices, data, InvoiceConst.ADD_KIND,
                    0, message);
                try {
                    //调用金穗开票接口   返回结果数据，需要解析
                    result = invoiceToTaxService.createInvoiceToTaxSys(invoiceEntity);
                } catch (Exception e) {
                    log.error("推送金穗开票系统-创建发票-接口异常：", e);
                    message = "推送金穗开票系统-创建发票-接口异常" + e.getMessage();
                    result = null;
                }
                //事务处理创建发票信息等逻辑
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                TransactionStatus status = transactionManager.getTransaction(def);
                try {
                    if (result == null) {
                        apilogs.setLastMessage(message);
                        log.error(message);
                    } else if (!result.getSuccess() || !result.getResult().getFlag().equals("S")) {
                        //失败
                        message = result.getMessage() == null || result.getMessage().equals("") ? result
                            .getResult().getMsg() == null || result.getResult().getMsg().equals("") ? "返回结果超时"
                            : result.getResult().getMsg()
                            : result.getMessage();//原php值："返回结果超时"
                        apilogs.setLastMessage(message);
                        apilogs.setReturnData(JsonUtil.toJson(result.getResult()));
                        log.error(message);
                    } else {
                        //成功

                        // 更改来源单开票中
                        if (InvoiceConst.COMMON_CORDER_TYPE.equals(invoices.getCOrderType())) {
                            // 普通网单
                            OrderProductsNew op = orderProductsDao.get(invoices.getOrderProductId());
                            if (op != null) {
                                op.setIsMakeReceipt(InvoiceConst.MR_STATE_MAKING);
                                orderProductsDao.update(op);
                            }
                        } else if (InvoiceConst.DIFF_CORDER_TYPE.equals(invoices.getCOrderType())) {
                            // 差异订单
                            Order2ths order2ths = order2thsDao.get(invoices.getDiffId());
                            if (order2ths != null) {
                                order2ths.setIsReceipted(InvoiceConst.R_RECEIPTING);
                                order2thsDao.update(order2ths);
                            }
                        } else if (InvoiceConst.SPECIAL_CORDER_TYPE
                            .equals(invoices.getCOrderType())) {
                            // 专项开票
                            Order4Invoices order4Invoices = order4InvoicesDao.get(invoices
                                .getDiffId());
                            if (order4Invoices != null) {
                                order4Invoices.setIsReceipted(InvoiceConst.R_RECEIPTING);
                                order4InvoicesDao.update(order4Invoices);
                            }
                        }
                        apilogs.setLastMessage("");
                        apilogs.setIsSuccess(1);
                        apilogs.setReturnData(JsonUtil.toJson(result.getResult()));

                        invoices.setState(1);
                        invoices.setSuccess(1);
                    }

                    invoiceApiLogsDao.updateByInvoiceIdAndType(apilogs);

                    invoices
                        .setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                    invoicesDao.update(invoices);

                    //提交事务
                    transactionManager.commit(status);
                } catch (Exception ex) {
                    //回滚事务
                    transactionManager.rollback(status);
                    message = "金穗开发票时异常";
                    log.error(message, ex);
                    //记录日志
                    apilogs.setLastMessage(message);
                    invoiceApiLogsDao.updateByInvoiceIdAndType(apilogs);

                    invoices
                        .setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                    invoicesDao.update(invoices);
                }
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.toString()).append("\r\n");
            for (StackTraceElement key : e.getStackTrace()) {
                sb.append(key.toString()).append("\r\n");
            }
            log.error(this.logPrefix(invoices.getOrderProductId().toString()) + "创建发票开票时异常字符串:"
                      + sb.toString());
            invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
            invoicesDao.update(invoices);
        }
    }

    /**
     * 同步发票信息到电子发票系统或金穗系统(Invoices表同步到开票系统)-开票
     */
    @Deprecated
    public void syncToInvoiceSystemCreateOld(Invoices invoices) {
        if (!invoices.getCOrderSn().equals(invoices.getCOrderSn().trim())) {
            invoices.setCOrderSn(invoices.getCOrderSn().trim());
            invoicesDao.update(invoices);
        }
        if (invoices.getFirstPushTime() == null || invoices.getFirstPushTime().equals(0)) {
            invoices.setFirstPushTime((int) (new Date().getTime() / 1000));
        }
        //验证发票数据
        if (!checkInvoiceData(invoices)) {
            log.error("获取待同步开票系统发票信息队列：发票数据验证没有通过，网单id：" + invoices.getOrderProductId());
            //异常发票数据格式问题，记录日志，写次数
            invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
            invoicesDao.update(invoices);
            return;
        }
        try {

            MemberInvoices memberInvoices = null;
            String mobile = "";
            String consignee = "";
            String industryName = "";
            Integer orderId = 0;
            if (InvoiceConst.COMMON_CORDER_TYPE.equals(invoices.getCOrderType())) {//普通网单
                OrderProductsNew orderProducts = orderProductsDao.get(invoices.getOrderProductId());
                if (orderProducts == null) {
                    log.error("获取待同步开票系统发票信息队列：网单不存在，发票id：" + invoices.getId());
                    invoices
                        .setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                    invoicesDao.update(invoices);
                    return;
                }
                orderId = orderProducts.getOrderId();
                memberInvoices = memberInvoicesDao.getByOrderId(orderProducts.getOrderId());
            } else if (InvoiceConst.DIFF_CORDER_TYPE.equals(invoices.getCOrderType())) {//差异网单
                Order2ths order2ths = order2thsDao.get(invoices.getDiffId());
                memberInvoices = memberInvoicesDao.get(order2ths.getMemberInvoiceId());
                mobile = order2ths.getMobile1();
                consignee = order2ths.getConsignee();
                industryName = order2ths.getIndustryName();
            } else { //专项开票
                Order4Invoices order4Invoices = order4InvoicesDao.get(invoices.getDiffId());
                memberInvoices = memberInvoicesDao.get(order4Invoices.getMemberInvoiceId());
                mobile = order4Invoices.getMobile1();
                consignee = order4Invoices.getConsignee();
                industryName = order4Invoices.getIndustryName();
            }
            //如果发票类型是普通发票，并且电子发票切换标志(ELECTRIC_SWITCH)为true时，所有普票强制改电子发票，要更新数据库电子发票标志
            if (InvoiceConst.ELECTRIC_SWITCH
                && InvoiceConst.GENERAL_INVOICE.equals(memberInvoices.getInvoiceType())) {
                memberInvoices.setElectricFlag(InvoiceConst.ELECTRIC_INVOICE);
                memberInvoicesDao.update(memberInvoices);
            }
            //并发时，过滤同步操作数据库
            if (invoices.getSuccess().equals(1)) {
                log.error("获取待同步开票系统发票信息-开票：发票已经被其他进程执行完成，发票id：" + invoices.getId());
                return;
            }
            if (memberInvoices.getElectricFlag().equals(1) || invoices.getElectricFlag().equals(1)) {
                // 电子发票
                // 生成发票推送日志
                if (!(invoices.getElectricFlag() != null && invoices.getElectricFlag().equals(1))) {
                    invoices.setElectricFlag(InvoiceConst.ELECTRIC_INVOICE.intValue());
                    invoicesDao.update(invoices);//后面还会执行更新
                }
                OrdersNew order = null;
                if (InvoiceConst.COMMON_CORDER_TYPE.equals(invoices.getCOrderType())) {
                    order = ordersDao.get(orderId);
                } else {
                    order = new OrdersNew();
                    order.setMemberEmail("");
                    order.setMobile(mobile);
                    order.setConsignee(consignee);
                    order.setProvince(0);
                    order.setCity(0);
                    order.setRegion(0);
                    order.setAddress(industryName);
                }
                // 生成报文 -插入或更新电子发票日志
                InvoiceElectricLogs invoiceElectricLogs = eInvoiceGenerateOld(invoices, order);
                //                log.info("invoiceElectricLogs:" + invoiceElectricLogs.getCOrderSn());
                if (invoiceElectricLogs != null) {
                    // 发送请求
                    boolean result = eInvoiceSendOld(invoices, invoiceElectricLogs);
                    if (!result) {
                        log.error("创建发票推送失败，请检查网单及发票信息是否完整！发票id：" + invoices.getId());
                        //                        invoices
                        //                            .setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                        invoicesDao.update(invoices);
                    }
                } else {
                    log.error("创建发票信息不完整，请检查网单及发票信息是否完整！发票id：" + invoices.getId());
                    invoices
                        .setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                    invoicesDao.update(invoices);
                }
            } else {//金穗系统开票
                //设置参数
                InvoiceEntity invoiceEntity = AssemblyInvoicesEntityParam(invoices);
                String data = JsonUtil.toJson(invoiceEntity);//生成日志数据
                ServiceResult<InvoiceOutType> result = null;
                String message = "";
                InvoiceApiLogs apilogs = saveInvoiceApiLogs(invoices, data, InvoiceConst.ADD_KIND,
                    0, message);
                try {
                    //调用金穗开票接口   返回结果数据，需要解析
                    result = invoiceToTaxService.createInvoiceToTaxSys(invoiceEntity);
                } catch (Exception e) {
                    log.error("推送金穗开票系统-创建发票-接口异常：", e);
                    message = "推送金穗开票系统-创建发票-接口异常" + e.getMessage();
                    result = null;
                }
                //事务处理创建发票信息等逻辑
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                TransactionStatus status = transactionManager.getTransaction(def);
                try {
                    if (result == null) {
                        apilogs.setLastMessage(message);
                        log.error(message);
                    } else if (!result.getSuccess() || !result.getResult().getFlag().equals("S")) {
                        //失败
                        message = result.getMessage() == null || result.getMessage().equals("") ? result
                            .getResult().getMsg() == null || result.getResult().getMsg().equals("") ? "返回结果超时"
                            : result.getResult().getMsg()
                            : result.getMessage();//原php值："返回结果超时"
                        apilogs.setLastMessage(message);
                        apilogs.setReturnData(JsonUtil.toJson(result.getResult()));
                        log.error(message);
                    } else {
                        //成功

                        // 更改来源单开票中
                        if (InvoiceConst.COMMON_CORDER_TYPE.equals(invoices.getCOrderType())) {
                            // 普通网单
                            OrderProductsNew op = orderProductsDao.get(invoices.getOrderProductId());
                            if (op != null) {
                                op.setIsMakeReceipt(InvoiceConst.MR_STATE_MAKING);
                                orderProductsDao.update(op);
                            }
                        } else if (InvoiceConst.DIFF_CORDER_TYPE.equals(invoices.getCOrderType())) {
                            // 差异订单
                            Order2ths order2ths = order2thsDao.get(invoices.getDiffId());
                            if (order2ths != null) {
                                order2ths.setIsReceipted(InvoiceConst.R_RECEIPTING);
                                order2thsDao.update(order2ths);
                            }
                        } else if (InvoiceConst.SPECIAL_CORDER_TYPE
                            .equals(invoices.getCOrderType())) {
                            // 专项开票
                            Order4Invoices order4Invoices = order4InvoicesDao.get(invoices
                                .getDiffId());
                            if (order4Invoices != null) {
                                order4Invoices.setIsReceipted(InvoiceConst.R_RECEIPTING);
                                order4InvoicesDao.update(order4Invoices);
                            }
                        }
                        apilogs.setLastMessage("");
                        apilogs.setIsSuccess(1);
                        apilogs.setReturnData(JsonUtil.toJson(result.getResult()));

                        invoices.setState(1);
                        invoices.setSuccess(1);
                    }

                    invoiceApiLogsDao.updateByInvoiceIdAndType(apilogs);

                    invoices
                        .setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                    invoicesDao.update(invoices);

                    //提交事务
                    transactionManager.commit(status);
                } catch (Exception ex) {
                    //回滚事务
                    transactionManager.rollback(status);
                    message = "金穗开发票时异常";
                    log.error(message, ex);
                    //记录日志
                    apilogs.setLastMessage(message);
                    invoiceApiLogsDao.updateByInvoiceIdAndType(apilogs);

                    invoices
                        .setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                    invoicesDao.update(invoices);
                }
            }
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.toString()).append("\r\n");
            for (StackTraceElement key : e.getStackTrace()) {
                sb.append(key.toString()).append("\r\n");
            }
            log.error(this.logPrefix(invoices.getOrderProductId().toString()) + "创建发票开票时异常字符串:"
                      + sb.toString());
            invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
            invoicesDao.update(invoices);
        }
    }

    /**
     * 同步发票信息到电子发票系统或金穗系统(Invoices表同步到开票系统)-修改
     */
    public void syncToInvoiceSystemUpdate(Invoices invoice) {
        //验证发票数据
        if (!checkInvoiceData(invoice)) {
            log.error("更新发票信息队列：发票数据验证没有通过，网单id：" + invoice.getCOrderSn());
            invoice.setTryNum((invoice.getTryNum() == null ? 1 : invoice.getTryNum()) + 1);
            invoicesDao.update(invoice);
            throw new BusinessException("校验数据失败!");
        }
        if (!invoice.getState().equals(1)) {
            invoice.setTryNum((invoice.getTryNum() == null ? 1 : invoice.getTryNum()) + 1);
            invoicesDao.update(invoice);
            throw new BusinessException("发票状态异常!");
        }
        if (invoice.getElectricFlag().equals(1)) {
            invoice.setTryNum((invoice.getTryNum() == null ? 1 : invoice.getTryNum()) + 1);
            invoicesDao.update(invoice);
            throw new BusinessException("电子发票不能修改发票信息，请作废后重新开票！");
        }
        //并发时，过滤同步操作数据库
        if (invoice.getSuccess().equals(1)) {
            log.error("电子发票发票信息-修改：发票已经被其他进程执行完成，发票id：" + invoice.getId());
            return;
        }
        InvoiceEntity entity = new InvoiceEntity();
        //表示更新
        entity.setFpzt(1);//(1正常 or 5取消)
        entity.setWdh(invoice.getCOrderSn());
        entity.setKhbm(invoice.getCustomerCode());
        entity.setKhmc(invoice.getInvoiceTitle());
        entity.setKhsh(invoice.getTaxPayerNumber() == null ? invoice.getTaxPayerNumber() : invoice
            .getTaxPayerNumber().replace(" ", ""));
        entity.setKhdz(invoice.getRegisterAddressAndPhone());
        entity.setKhkhyhzh(invoice.getBankNameAndAccount() == null ? invoice
            .getBankNameAndAccount() : invoice.getBankNameAndAccount().replace(" ", ""));
        entity.setBz(invoice.getCOrderSn());//?
        entity.setWdrq(new Date(invoice.getAddTime()));
        entity.setCpdm(invoice.getSku());
        entity.setCpmc(invoice.getProductName());
        entity.setCpxh(invoice.getProductCateName());
        entity.setCpdw(invoice.getUnit());
        entity.setCpsl(new BigDecimal(invoice.getNumber()));
        entity.setHsdj(invoice.getPrice());
        entity.setHsje(invoice.getAmount());
        entity.setBhsdj(invoice.getNonTaxPrice());
        entity.setBhsje(invoice.getNonTaxAmount());
        entity.setJsje(invoice.getTaxAmount());
        entity.setJssl(invoice.getTaxRate());
        entity.setJfje(invoice.getIntegralAmount());
        entity.setJlje(invoice.getEnergySavingAmount());
        entity.setJlbz(invoice.getEnergySavingRemark());
        entity.setFplx(invoice.getType().intValue());//发票类型：1，增税；2=普税
        entity.setSkfs(invoice.getPaymentName());
        entity.setLbjsdh(invoice.getInternalSettlement());
        entity.setKwbm(invoice.getSCode());
        entity.setHptx(invoice.getIsTogether().intValue());
        entity.setDdlx(invoice.getOrderType());
        entity.setFgsno(invoice.getBranchOfficeCode());
        entity.setDdhno(invoice.getOrderLineNumber());
        entity.setWlno(invoice.getLessOrderSn());
        entity.setAdd1(invoice.getBackupFieldA());
        entity.setAdd2(invoice.getBackupFieldB());
        entity.setRrrq(new Date(invoice.getAddTime()));
        entity.setGxrq(new Date(invoice.getLastModifyTime()));
        entity.setFphm(null);
        entity.setKprq(null);
        entity.setSkrq(null);
        entity.setKpman(null);
        entity.setKpzt(null);
        entity.setZfrq(null);
        String pushData = JsonUtil.toJson(entity);
        String receiveData = "";
        Integer isSucess = InvoiceConst.SUCCESS;

        try {
            //请求金税更新
            ServiceResult<InvoiceOutType> result = invoiceToTaxService
                .modifyInvoiceToTaxSys(entity);
            if (result.getSuccess()) {
                InvoiceOutType outType = result.getResult();
                String flag = outType.getFlag();
                isSucess = "S".equals(flag) ? InvoiceConst.SUCCESS : InvoiceConst.FAILED;
                receiveData = JsonUtil.toJson(outType);
            }
            this.writeInvoiceApiLogs(invoice.getId(), InvoiceConst.EDIT_KIND,
                invoice.getOrderProductId(), invoice.getCOrderSn(), isSucess, "更新金税", receiveData,
                pushData, null);
            //更新Invoice
            invoice.setSuccess(isSucess.intValue());
            invoice.setTryNum((invoice.getTryNum() == null ? 1 : invoice.getTryNum()) + 1);
            invoicesDao.update(invoice);
        } catch (Exception e) {
            this.writeInvoiceApiLogs(invoice.getId(), InvoiceConst.EDIT_KIND,
                invoice.getOrderProductId(), invoice.getCOrderSn(), isSucess, "更新金税", "发生异常",
                pushData, null);
            log.error("更新发票队列：更新发票发生异常，网单id：" + invoice.getCOrderSn());
            invoice.setTryNum((invoice.getTryNum() == null ? 1 : invoice.getTryNum()) + 1);
            invoicesDao.update(invoice);
            throw new BusinessException("更新发票发生异常");
        }
    }

    /**
     * 同步发票信息到电子发票系统或金穗系统(Invoices表同步到开票系统)-取消
     */
    public void syncToInvoiceSystemCancel(Invoices invoice) {
        //验证发票数据
        if (!checkInvoiceData(invoice)) {
            invoice.setTryNum((invoice.getTryNum() == null ? 1 : invoice.getTryNum()) + 1);
            invoicesDao.update(invoice);
            log.error("取消发票信息队列：发票数据验证没有通过，网单id：" + invoice.getCOrderSn());
            throw new BusinessException("校验数据失败!");
        }
        if (invoice.getElectricFlag().equals(1)) {
            invoice.setTryNum((invoice.getTryNum() == null ? 1 : invoice.getTryNum()) + 1);
            invoicesDao.update(invoice);
            throw new BusinessException("电子发票不能取消发票，请直接作废发票！");
        }
        if (invoice.getState().equals(4)) {
            invoice.setTryNum((invoice.getTryNum() == null ? 1 : invoice.getTryNum()) + 1);
            invoicesDao.update(invoice);
            log.error("取消发票信息队列：已开票状态不能正常取消发票，网单id：" + invoice.getCOrderSn());
            throw new BusinessException("已开票状态不能正常取消发票");
        }
        //并发时，过滤同步操作数据库
        if (invoice.getSuccess().equals(1)) {
            log.error("电子发票发票信息-取消：发票已经被其他进程执行完成，发票id：" + invoice.getId());
            return;
        }
        InvoiceEntity entity = new InvoiceEntity();
        //取消
        entity.setFpzt(5);//(1正常 or 5取消)
        entity.setWdh(invoice.getCOrderSn());
        entity.setKhbm(invoice.getCustomerCode());
        entity.setKhmc(invoice.getInvoiceTitle());
        entity.setKhsh(invoice.getTaxPayerNumber() == null ? invoice.getTaxPayerNumber() : invoice
            .getTaxPayerNumber().replace(" ", ""));
        entity.setKhdz(invoice.getRegisterAddressAndPhone());
        entity.setKhkhyhzh(invoice.getBankNameAndAccount() == null ? invoice
            .getBankNameAndAccount() : invoice.getBankNameAndAccount().replace(" ", ""));
        entity.setBz(invoice.getCOrderSn());//?
        entity.setWdrq(new Date(invoice.getAddTime()));
        entity.setCpdm(invoice.getSku());
        entity.setCpmc(invoice.getProductName());
        entity.setCpxh(invoice.getProductCateName());
        entity.setCpdw(invoice.getUnit());
        entity.setCpsl(new BigDecimal(invoice.getNumber()));
        entity.setHsdj(invoice.getPrice());
        entity.setHsje(invoice.getAmount());
        entity.setBhsdj(invoice.getNonTaxPrice());
        entity.setBhsje(invoice.getNonTaxAmount());
        entity.setJsje(invoice.getTaxAmount());
        entity.setJssl(invoice.getTaxRate());
        entity.setJfje(invoice.getIntegralAmount());
        entity.setJlje(invoice.getEnergySavingAmount());
        entity.setJlbz(invoice.getEnergySavingRemark());
        entity.setFplx(invoice.getType().intValue());//发票类型：1，增税；2=普税
        entity.setSkfs(invoice.getPaymentName());
        entity.setLbjsdh(invoice.getInternalSettlement());
        entity.setKwbm(invoice.getSCode());
        entity.setHptx(invoice.getIsTogether().intValue());
        entity.setDdlx(invoice.getOrderType());
        entity.setFgsno(invoice.getBranchOfficeCode());
        entity.setDdhno(invoice.getOrderLineNumber());
        entity.setWlno(invoice.getLessOrderSn());
        entity.setAdd1(invoice.getBackupFieldA());
        entity.setAdd2(invoice.getBackupFieldB());
        entity.setRrrq(new Date(invoice.getAddTime()));
        entity.setGxrq(new Date(invoice.getLastModifyTime()));
        entity.setFphm(null);
        entity.setKprq(null);
        entity.setSkrq(null);
        entity.setKpman(null);
        entity.setKpzt(null);
        entity.setZfrq(null);
        String pushData = JsonUtil.toJson(entity);
        String receiveData = "";
        Integer isSucess = InvoiceConst.SUCCESS;
        try {
            //请求金税更新
            ServiceResult<InvoiceOutType> result = invoiceToTaxService
                .modifyInvoiceToTaxSys(entity);
            if (result.getSuccess()) {
                InvoiceOutType outType = result.getResult();
                String flag = outType.getFlag();
                isSucess = "S".equals(flag) ? InvoiceConst.SUCCESS : InvoiceConst.FAILED;
                receiveData = JsonUtil.toJson(outType);
            }
            this.writeInvoiceApiLogs(invoice.getId(), InvoiceConst.CANCEL_KIND,
                invoice.getOrderProductId(), invoice.getCOrderSn(), isSucess, "取消金税", receiveData,
                pushData, null);
            //更新发票状态
            invoice.setState(InvoiceConst.CANCEL_STATE.intValue());//已取消开票
            invoice.setSuccess(isSucess.intValue());
            invoice.setTryNum((invoice.getTryNum() == null ? 1 : invoice.getTryNum()) + 1);
            invoicesDao.update(invoice);
        } catch (Exception e) {
            this.writeInvoiceApiLogs(invoice.getId(), InvoiceConst.CANCEL_KIND,
                invoice.getOrderProductId(), invoice.getCOrderSn(), isSucess, "取消金税", "发生异常",
                pushData, null);
            log.error("取消发票队列：取消发票发生异常，网单id：" + invoice.getCOrderSn());
            invoice.setTryNum((invoice.getTryNum() == null ? 1 : invoice.getTryNum()) + 1);
            invoicesDao.update(invoice);
            throw new BusinessException("取消发票发生异常:" + e.getMessage());
        }
    }

    private void writeInvoiceApiLogs(Integer invoiceId, Integer type, Integer orderProductId,
                                     String cOrderSn, Integer isSucess, String operate,
                                     String receiveData, String pushData, String lastMessage) {
        InvoiceApiLogs apiLogs = new InvoiceApiLogs();
        apiLogs.setInvoiceId(invoiceId);
        apiLogs.setType(type);
        apiLogs.setOrderProductId(orderProductId);
        apiLogs.setCountNum(0);
        apiLogs.setCOrderSn(cOrderSn);
        apiLogs.setIsSuccess(isSucess);
        apiLogs.setReturnData(operate);
        apiLogs.setReceiveData(receiveData);
        apiLogs.setPushData(pushData);
        apiLogs.setLastMessage(StringUtil.isEmpty(lastMessage) ? "" : lastMessage);
        invoiceApiLogsDao.insert(apiLogs);
    }

    private void writeInvoiceElectricLogs(Integer invoiceId, Integer type, Integer orderProductId,
                                          String cOrderSn, Integer isSucess, String receiveData,
                                          String pushData, String lastMessage) {
        InvoiceElectricLogs log = new InvoiceElectricLogs();
        log.setAddTime((int) (new Date().getTime() / 1000));
        log.setOrderProductId(orderProductId);
        log.setCOrderSn(cOrderSn);
        log.setInvoiceId(invoiceId);
        log.setType(type);
        log.setPushData(pushData);
        log.setReturnData(receiveData);
        log.setAnalysisResult(0);
        log.setVerifyResult(0);
        log.setCount(1);
        log.setSuccess(isSucess);
        log.setLastTime((int) (new Date().getTime() / 1000));
        log.setLastMessage(StringUtil.isEmpty(lastMessage) ? "" : lastMessage);
        log.setSmsFlag(0);
        invoiceElectricLogsDao.insertLog(log);

    }

    /**
     * 同步发票信息到电子发票系统或金穗系统(Invoices表同步到开票系统)-作废
     */
    @Deprecated
    public void syncToInvoiceSystemInvalidOld(Invoices invoices) {
        //已开票发票才能作废
        if (invoices.getState() != 4) {
            log.error(this.logPrefix("网单号：" + invoices.getCOrderSn()) + "已开票发票才能作废。");
            invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
            invoicesDao.update(invoices);
            return;
        }
        //网单id不正确
        if ((invoices.getOrderProductId() == null || invoices.getOrderProductId().equals(0))
            && (invoices.getDiffId() == null || invoices.getDiffId().equals(0))) {
            log.error(this.logPrefix("行ID：" + invoices.getId())
                      + "orderProductId和diffId不正确，不能同时为0或null");
            invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
            invoicesDao.update(invoices);
            return;
        }

        String memberEmail = "";
        String memberMobile = "";
        if (InvoiceConst.COMMON_CORDER_TYPE.equals(invoices.getCOrderType())) {//普通网单
            OrderProductsNew orderProducts = orderProductsDao.get(invoices.getOrderProductId());
            if (orderProducts == null) {
                log.error(this.logPrefix("网单ID：" + invoices.getOrderProductId())
                          + "获取待同步开票系统作废发票信息队列：网单不存在，发票id：" + invoices.getId());
                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                invoicesDao.update(invoices);
                return;
            }
            //获取订单信息
            OrdersNew order = ordersDao.get(orderProducts.getOrderId());
            if (order == null) {
                log.error(this.logPrefix("订单ID：" + orderProducts.getOrderId()) + "订单不存在，");
                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                invoicesDao.update(invoices);
                return;
            }
            memberMobile = order.getMobile();
            memberEmail = order.getMemberEmail();
        } else if (InvoiceConst.DIFF_CORDER_TYPE.equals(invoices.getCOrderType())) {//差异网单
            Order2ths order2ths = order2thsDao.get(invoices.getDiffId());
            memberMobile = order2ths.getMobile1();
        } else { //专项开票
            Order4Invoices order4Invoices = order4InvoicesDao.get(invoices.getDiffId());
            memberMobile = order4Invoices.getMobile1();
        }
        //并发时，过滤同步操作数据库
        if (invoices.getSuccess().equals(1)) {
            log.error("获取待同步开票系统发票信息-作废：发票已经被其他进程执行完成，发票id：" + invoices.getId());
            return;
        }
        //调用开票接口
        if (invoices.getElectricFlag().equals(1)) {// 电子发票系统
            //生成电子发票日志
            InvoiceElectricLogs invoiceElectricLogs = this.eInvoiceInvalidOld(memberEmail,
                memberMobile, invoices);
            //调用电子发票接口
            if (invoiceElectricLogs != null) {
                // 发送请求
                boolean result = eInvoiceSendOld(invoices, invoiceElectricLogs);
                if (!result) {
                    log.error("电子发票推送失败，请检查网单及发票信息是否完整！发票id：" + invoices.getId());
                    //                    invoices
                    //                        .setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                    invoicesDao.update(invoices);
                }
            } else {
                log.error("电子发票信息不完整，请检查网单及发票信息是否完整！发票id：" + invoices.getId());
                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                invoicesDao.update(invoices);
            }
        } else {//金穗系统开票
            //设置参数    调用base服务的作废发票，需要两个参数，网单号和invoice->addTime
            InvoiceEntity invoiceEntity = new InvoiceEntity();
            invoiceEntity.setWdh(invoices.getCOrderSn());
            invoiceEntity.setRrrq(DateFormatUtil.parse(DateFormatUtil.formatTime(invoices
                .getAddTime())));

            String data = JsonUtil.toJson(invoiceEntity);//生成日志数据
            ServiceResult<InvoiceOutType> result = null;
            String message = "";
            InvoiceApiLogs apilogs = saveInvoiceApiLogs(invoices, data, InvoiceConst.INVALID_KIND,
                0, message);
            try {
                //调用金穗开票作废接口   返回结果数据，需要解析
                result = invoiceToTaxService.invalidInvoiceToTaxSys(invoiceEntity);
            } catch (Exception e) {
                log.error("推送金穗开票系统-作废发票-接口异常：", e);
                message = "推送金穗开票系统-作废发票-接口异常" + e.getMessage();
                result = null;
            }
            //事务处理创建发票信息等逻辑
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus status = transactionManager.getTransaction(def);
            try {
                if (result == null) {
                    //                    String message = "返回结果超时";//原php值："返回结果超时"
                    apilogs.setLastMessage(message);
                    log.error(message);
                } else if (result == null || !result.getSuccess()
                           || !result.getResult().getFlag().equals("S")) {
                    //失败
                    message = result.getMessage() == null || result.getMessage().equals("") ? result
                        .getResult().getMsg() == null || result.getResult().getMsg().equals("") ? "返回结果超时"
                        : result.getResult().getMsg()
                        : result.getMessage();//原php值："返回结果超时"
                    apilogs.setLastMessage(message);
                    log.error(message);
                } else {
                    //成功
                    invoices.setSuccess(1);

                    apilogs.setLastMessage("");
                    apilogs.setIsSuccess(1);
                    apilogs.setReturnData(JsonUtil.toJson(result.getResult()));
                    //                    apilogs = saveInvoiceApiLogs(invoices, data, InvoiceConst.INVALID_KIND, 1, "");

                }
                invoiceApiLogsDao.updateByInvoiceIdAndType(apilogs);

                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                invoicesDao.update(invoices);
                //提交事务
                transactionManager.commit(status);
            } catch (Exception ex) {
                //回滚事务
                transactionManager.rollback(status);
                message = "金穗作废发票时异常";
                //记录日志
                //                apilogs = insertInvoiceApiLogs(invoices, data, InvoiceConst.INVALID_KIND, 0,
                //                    message);
                //                invoiceApiLogsDao.insert(apilogs);
                apilogs.setLastMessage(message + ":" + ex.getMessage());
                invoiceApiLogsDao.updateByInvoiceIdAndType(apilogs);
                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                invoicesDao.update(invoices);
                log.error(message, ex);
            }
        }
    }

    /**
     * 同步发票信息到电子发票系统或金穗系统(Invoices表同步到开票系统)-作废
     */
    public void syncToInvoiceSystemInvalid(Invoices invoices) {
        //已开票发票才能作废
        if (invoices.getState() != 4) {
            log.error(this.logPrefix("网单号：" + invoices.getCOrderSn()) + "已开票发票才能作废。");
            invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
            invoicesDao.update(invoices);
            return;
        }
        //网单id不正确
        if ((invoices.getOrderProductId() == null || invoices.getOrderProductId().equals(0))
            && (invoices.getDiffId() == null || invoices.getDiffId().equals(0))) {
            log.error(this.logPrefix("行ID：" + invoices.getId())
                      + "orderProductId和diffId不正确，不能同时为0或null");
            invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
            invoicesDao.update(invoices);
            return;
        }

        String memberEmail = "";
        String memberMobile = "";
        if (InvoiceConst.COMMON_CORDER_TYPE.equals(invoices.getCOrderType())) {//普通网单
            OrderProductsNew orderProducts = orderProductsDao.get(invoices.getOrderProductId());
            if (orderProducts == null) {
                log.error(this.logPrefix("网单ID：" + invoices.getOrderProductId())
                          + "获取待同步开票系统作废发票信息队列：网单不存在，发票id：" + invoices.getId());
                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                invoicesDao.update(invoices);
                return;
            }
            //获取订单信息
            OrdersNew order = ordersDao.get(orderProducts.getOrderId());
            if (order == null) {
                log.error(this.logPrefix("订单ID：" + orderProducts.getOrderId()) + "订单不存在，");
                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                invoicesDao.update(invoices);
                return;
            }
            memberMobile = order.getMobile();
            memberEmail = order.getMemberEmail();
        } else if (InvoiceConst.DIFF_CORDER_TYPE.equals(invoices.getCOrderType())) {//差异网单
            Order2ths order2ths = order2thsDao.get(invoices.getDiffId());
            memberMobile = order2ths.getMobile1();
        } else { //专项开票
            Order4Invoices order4Invoices = order4InvoicesDao.get(invoices.getDiffId());
            memberMobile = order4Invoices.getMobile1();
        }
        //并发时，过滤同步操作数据库
        if (invoices.getSuccess().equals(1)) {
            log.error("获取待同步开票系统发票信息-作废：发票已经被其他进程执行完成，发票id：" + invoices.getId());
            return;
        }
        //调用开票接口
        if (invoices.getElectricFlag().equals(1)) {// 电子发票系统
            //生成电子发票日志
            InvoiceElectricLogs invoiceElectricLogs = this.eInvoiceInvalid(memberEmail,
                memberMobile, invoices);
            //调用电子发票接口
            if (invoiceElectricLogs != null) {
                // 发送请求
                boolean result = eInvoiceSend(invoices, invoiceElectricLogs);
                if (!result) {
                    log.error("电子发票推送失败，请检查网单及发票信息是否完整！发票id：" + invoices.getId());
                    //                    invoices
                    //                        .setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                    invoicesDao.update(invoices);
                }
            } else {
                log.error("电子发票信息不完整，请检查网单及发票信息是否完整！发票id：" + invoices.getId());
                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                invoicesDao.update(invoices);
            }
        } else {//金穗系统开票
            //设置参数    调用base服务的作废发票，需要两个参数，网单号和invoice->addTime
            InvoiceEntity invoiceEntity = new InvoiceEntity();
            invoiceEntity.setWdh(invoices.getCOrderSn());
            invoiceEntity.setRrrq(DateFormatUtil.parse(DateFormatUtil.formatTime(invoices
                .getAddTime())));

            String data = JsonUtil.toJson(invoiceEntity);//生成日志数据
            ServiceResult<InvoiceOutType> result = null;
            String message = "";
            InvoiceApiLogs apilogs = saveInvoiceApiLogs(invoices, data, InvoiceConst.INVALID_KIND,
                0, message);
            try {
                //调用金穗开票作废接口   返回结果数据，需要解析
                result = invoiceToTaxService.invalidInvoiceToTaxSys(invoiceEntity);
            } catch (Exception e) {
                log.error("推送金穗开票系统-作废发票-接口异常：", e);
                message = "推送金穗开票系统-作废发票-接口异常" + e.getMessage();
                result = null;
            }
            //事务处理创建发票信息等逻辑
            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus status = transactionManager.getTransaction(def);
            try {
                if (result == null) {
                    //                    String message = "返回结果超时";//原php值："返回结果超时"
                    apilogs.setLastMessage(message);
                    log.error(message);
                } else if (result == null || !result.getSuccess()
                           || !result.getResult().getFlag().equals("S")) {
                    //失败
                    message = result.getMessage() == null || result.getMessage().equals("") ? result
                        .getResult().getMsg() == null || result.getResult().getMsg().equals("") ? "返回结果超时"
                        : result.getResult().getMsg()
                        : result.getMessage();//原php值："返回结果超时"
                    apilogs.setLastMessage(message);
                    log.error(message);
                } else {
                    //成功
                    invoices.setSuccess(1);

                    apilogs.setLastMessage("");
                    apilogs.setIsSuccess(1);
                    apilogs.setReturnData(JsonUtil.toJson(result.getResult()));
                    //                    apilogs = saveInvoiceApiLogs(invoices, data, InvoiceConst.INVALID_KIND, 1, "");

                }
                invoiceApiLogsDao.updateByInvoiceIdAndType(apilogs);

                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                invoicesDao.update(invoices);
                //提交事务
                transactionManager.commit(status);
            } catch (Exception ex) {
                //回滚事务
                transactionManager.rollback(status);
                message = "金穗作废发票时异常";
                //记录日志
                //                apilogs = insertInvoiceApiLogs(invoices, data, InvoiceConst.INVALID_KIND, 0,
                //                    message);
                //                invoiceApiLogsDao.insert(apilogs);
                apilogs.setLastMessage(message + ":" + ex.getMessage());
                invoiceApiLogsDao.updateByInvoiceIdAndType(apilogs);
                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                invoicesDao.update(invoices);
                log.error(message, ex);
            }
        }
    }

    /**
     * 检验推送数据
     *
     * @param
     *
     * @return
     */
    private boolean checkInvoiceData(Invoices invoices) {
        if (invoices == null) {
            log.error("推送开票信息：发票信息为空");
            return false;
        }

        int electricFlag = invoices.getElectricFlag().intValue();
        String lastMessage = "";

        if (StringUtil.isEmpty(invoices.getInvoiceTitle(), true)) {
            lastMessage = "发票抬头不能为空";
            if (electricFlag == 0) {
                this.writeInvoiceApiLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                    invoices.getCOrderSn(), 0, "", "", "", lastMessage);
            } else {
                this.writeInvoiceElectricLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                    invoices.getCOrderSn(), 0, "", "", lastMessage);
            }
            log.error(lastMessage);
            return false;
        }
        if (StringUtil.isEmpty(invoices.getProductName(), true)) {
            lastMessage = "商品名称不能为空";
            if (electricFlag == 0) {
                this.writeInvoiceApiLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                    invoices.getCOrderSn(), 0, "", "", "", lastMessage);
            } else {
                this.writeInvoiceElectricLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                    invoices.getCOrderSn(), 0, "", "", lastMessage);
            }
            log.error(lastMessage);
            return false;
        }
        try {
            if (invoices.getElectricFlag().equals(1)) {
                if (invoices.getInvoiceTitle().getBytes("GBK").length > 100) {
                    lastMessage = "电子发票抬头长度太长";
                    this.writeInvoiceElectricLogs(invoices.getId(), 0,
                        invoices.getOrderProductId(), invoices.getCOrderSn(), 0, "", "",
                        lastMessage);
                    log.error(lastMessage);
                    return false;
                }
                if (invoices.getProductName().getBytes("GBK").length > 90) {
                    lastMessage = "电子发票商品名称长度太长";
                    this.writeInvoiceElectricLogs(invoices.getId(), 0,
                        invoices.getOrderProductId(), invoices.getCOrderSn(), 0, "", "",
                        lastMessage);
                    log.error(lastMessage);
                    return false;
                }
                if (invoices.getAmount() == null || invoices.getAmount().compareTo(amount) >= 0) {
                    lastMessage = "电子发票开票金额不能超过100000";
                    this.writeInvoiceElectricLogs(invoices.getId(), 0,
                        invoices.getOrderProductId(), invoices.getCOrderSn(), 0, "", "",
                        lastMessage);
                    log.error(lastMessage);
                    return false;
                }
            } else if (invoices.getElectricFlag().equals(0)) {
                if (invoices.getInvoiceTitle().length() > 50) {
                    lastMessage = "金穗发票抬头:" + invoices.getInvoiceTitle() + "长度:"
                                  + invoices.getInvoiceTitle().length() + "，长度不能超过50";
                    this.writeInvoiceApiLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                        invoices.getCOrderSn(), 0, "", "", "", lastMessage);
                    log.error(lastMessage);
                    return false;
                }
                if (invoices.getProductName().length() > 52) {
                    lastMessage = "金穗发票商品名称:" + invoices.getProductName() + "长度:"
                                  + invoices.getProductName().length() + "，长度不能超过52";
                    this.writeInvoiceApiLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                        invoices.getCOrderSn(), 0, "", "", "", lastMessage);
                    log.error(lastMessage);
                    return false;
                }
            }
        } catch (UnsupportedEncodingException e) {
            log.error("[创建发票新增开票]异常：", e);
        }
        if (StringUtil.isEmpty(invoices.getSku(), true)) {
            lastMessage = "物料编码不能为空";
            if (electricFlag == 0) {
                this.writeInvoiceApiLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                    invoices.getCOrderSn(), 0, "", "", "", lastMessage);
            } else {
                this.writeInvoiceElectricLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                    invoices.getCOrderSn(), 0, "", "", lastMessage);
            }
            log.error(lastMessage);
            return false;
        }
        if (invoices.getNonTaxPrice() == null
            || invoices.getNonTaxPrice().compareTo(BigDecimal.ZERO) == 0) {
            lastMessage = "不含税单价必须为正整数";
            if (electricFlag == 0) {
                this.writeInvoiceApiLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                    invoices.getCOrderSn(), 0, "", "", "", lastMessage);
            } else {
                this.writeInvoiceElectricLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                    invoices.getCOrderSn(), 0, "", "", lastMessage);
            }
            log.error(lastMessage);
            return false;
        }
        if (invoices.getNonTaxAmount() == null
            || invoices.getNonTaxAmount().compareTo(BigDecimal.ZERO) == 0) {
            lastMessage = "不含税总金额必须为正整数";
            if (electricFlag == 0) {
                this.writeInvoiceApiLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                    invoices.getCOrderSn(), 0, "", "", "", lastMessage);
            } else {
                this.writeInvoiceElectricLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                    invoices.getCOrderSn(), 0, "", "", lastMessage);
            }
            log.error(lastMessage);
            return false;
        }
        if (invoices.getNumber() == null || invoices.getNumber().equals(0)) {
            lastMessage = "数量必须为正整数";
            if (electricFlag == 0) {
                this.writeInvoiceApiLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                    invoices.getCOrderSn(), 0, "", "", "", lastMessage);
            } else {
                this.writeInvoiceElectricLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                    invoices.getCOrderSn(), 0, "", "", lastMessage);
            }
            log.error(lastMessage);
            return false;
        }
        if (invoices.getPrice() == null || invoices.getPrice().compareTo(BigDecimal.ZERO) == 0) {
            lastMessage = "单价必须为正整数";
            if (electricFlag == 0) {
                this.writeInvoiceApiLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                    invoices.getCOrderSn(), 0, "", "", "", lastMessage);
            } else {
                this.writeInvoiceElectricLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                    invoices.getCOrderSn(), 0, "", "", lastMessage);
            }
            log.error(lastMessage);
            return false;
        }
        if (invoices.getAmount() == null || invoices.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            lastMessage = "总金额必须为正整数";
            if (electricFlag == 0) {
                this.writeInvoiceApiLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                    invoices.getCOrderSn(), 0, "", "", "", lastMessage);
            } else {
                this.writeInvoiceElectricLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                    invoices.getCOrderSn(), 0, "", "", lastMessage);
            }
            log.error(lastMessage);
            return false;
        }

        BigDecimal balance = invoices.getAmount()
            .subtract(invoices.getPrice().multiply(new BigDecimal(invoices.getNumber()))).abs();
        if (balance.compareTo(new BigDecimal(0.5)) > 0) {
            lastMessage = "单价*数量与总金额误差大于0.5";
            if (electricFlag == 0) {
                this.writeInvoiceApiLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                    invoices.getCOrderSn(), 0, "", "", "", lastMessage);
            } else {
                this.writeInvoiceElectricLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                    invoices.getCOrderSn(), 0, "", "", lastMessage);
            }
            log.error(lastMessage);
            return false;
        }

        //增值税增加判断
        if (invoices.getType().equals(1)) {
            if (StringUtil.isEmpty(invoices.getTaxPayerNumber(), true)) {
                lastMessage = "税票号不能为空";
                if (electricFlag == 0) {
                    this.writeInvoiceApiLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                        invoices.getCOrderSn(), 0, "", "", "", lastMessage);
                } else {
                    this.writeInvoiceElectricLogs(invoices.getId(), 0,
                        invoices.getOrderProductId(), invoices.getCOrderSn(), 0, "", "",
                        lastMessage);
                }
                log.error(lastMessage);
                return false;
            }
            if (!(invoices.getTaxPayerNumber().length() == 15
                  || invoices.getTaxPayerNumber().length() == 17
                  || invoices.getTaxPayerNumber().length() == 18 || invoices.getTaxPayerNumber()
                .length() == 20)) {
                lastMessage = "税票号长度不正确";
                if (electricFlag == 0) {
                    this.writeInvoiceApiLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                        invoices.getCOrderSn(), 0, "", "", "", lastMessage);
                } else {
                    this.writeInvoiceElectricLogs(invoices.getId(), 0,
                        invoices.getOrderProductId(), invoices.getCOrderSn(), 0, "", "",
                        lastMessage);
                }
                log.error(lastMessage);
                return false;
            }
            if (StringUtil.isEmpty(invoices.getRegisterAddressAndPhone(), true)) {
                lastMessage = "注册地址和注册电话不能为空";
                if (electricFlag == 0) {
                    this.writeInvoiceApiLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                        invoices.getCOrderSn(), 0, "", "", "", lastMessage);
                } else {
                    this.writeInvoiceElectricLogs(invoices.getId(), 0,
                        invoices.getOrderProductId(), invoices.getCOrderSn(), 0, "", "",
                        lastMessage);
                }
                log.error(lastMessage);
                return false;
            }
            if (StringUtil.isEmpty(invoices.getBankNameAndAccount(), true)) {
                lastMessage = "开户银行名称和开户银行帐号不能为空";
                if (electricFlag == 0) {
                    this.writeInvoiceApiLogs(invoices.getId(), 0, invoices.getOrderProductId(),
                        invoices.getCOrderSn(), 0, "", "", "", lastMessage);
                } else {
                    this.writeInvoiceElectricLogs(invoices.getId(), 0,
                        invoices.getOrderProductId(), invoices.getCOrderSn(), 0, "", "",
                        lastMessage);
                }
                log.error(lastMessage);
                return false;
            }
        }
        return true;
    }

    /**
     * 生成电子发票开票数据
     *
     * @param
     *
     * @return
     */
    @Deprecated
    private InvoiceElectricLogs eInvoiceGenerateOld(Invoices invoice, OrdersNew order) {
        if (null == invoice || null == order)
            return null;

        String email = "";
        String noticeEmail = "";

        if (StrTools.isEmail(order.getMemberEmail())) {
            email = order.getMemberEmail();
            noticeEmail = email;
        }

        String mobile = "";
        String noticeMobile = "";

        if (StrTools.isMobile(order.getMobile())) {
            mobile = order.getMobile();
            noticeMobile = mobile;
        }
        String tel = "";
        if (StrTools.isTelephone(order.getPhone())) {
            tel = order.getPhone();
        }
        if (tel.equals("") && StrTools.isMobile(order.getMobile())) {
            tel = order.getMobile();
        }

        String provincename = "";
        String cityname = "";
        String regionname = "";
        if (order.getProvince() != null && !order.getProvince().equals(0)) {
            provincename = regionsDao.get(order.getProvince()).getRegionName();
        }
        if (order.getCity() != null && !order.getCity().equals(0)) {
            cityname = regionsDao.get(order.getCity()).getRegionName();
        }
        if (order.getRegion() != null && !order.getRegion().equals(0)) {
            regionname = regionsDao.get(order.getRegion()).getRegionName();
        }

        String content = "商城订单";
        // 样品机订单发票备注
        if ("COS".equalsIgnoreCase(order.getSource())) {
            content = content + "，样品机，不退不换";
        }
        if ("DBJ".equalsIgnoreCase(order.getSource())) {
            content = content + "，夺宝机，不退不换";
        }

        //创建XML
        Element root = new Element("request");

        Document doc = new Document(root);

        Element elementsheader = new Element("header");
        root.addContent(elementsheader);
        elementsheader.setAttribute("platformCode", platformcode);//php原值PT000002  测试PT0000014
        elementsheader.setAttribute("postTime",
            DateFormatUtil.formatByType("yyyy-MM-dd HH:mm:ss", new Date()));
        elementsheader.setAttribute("sessionID", InvoiceConst.SESSIONID);
        //        elementsheader.setAttribute("shopCode", "ehaier");//php有此值，java版本没有这个值
        elementsheader.setAttribute("version", "V1.1");//测试V1.1

        Element elementsorder = new Element("order");
        root.addContent(elementsorder);
        elementsorder.setAttribute("account", email.equals("") ? order.getConsignee() : email);
        elementsorder.setAttribute("address", provincename
                                              + cityname
                                              + regionname
                                              + order.getAddress().replaceAll("[\\x00-\\x1f]+", "")
                                                  .replaceAll("[\\x7f-\\xff]+", ""));
        if (!email.equals("")) {
            elementsorder.setAttribute("email", email);
        }
        if (!tel.equals("")) {
            elementsorder.setAttribute("tel", tel);//php原值"mobile"=mobile
        }
        elementsorder.setAttribute("orderNo", invoice.getCOrderSn());
        elementsorder.setAttribute("totalAmount", invoice.getAmount().toString());

        Element elementsinvoice = new Element("invoice");
        root.addContent(elementsinvoice);
        elementsinvoice.setAttribute(
            "customer",
            invoice.getInvoiceTitle().replaceAll("[\\x00-\\x1f]+", "")
                .replaceAll("[\\x7f-\\xff]+", ""));
        elementsinvoice.setAttribute("drawer", InvoiceConst.DRAWER);//开票员
        //        elementsinvoice.setAttribute("totalAmount", invoice.getAmount().toString());//php有此值，order节点中有这个值
        elementsinvoice.setAttribute("shopCode", shopcode);//php在header中    php值ehaier  修改后测试值testcn
        elementsinvoice.setAttribute("customerCode", invoice.getCustomerCode());//php无此值，不是必填项

        Element elementsitems = new Element("items");
        elementsinvoice.addContent(elementsitems);
        Element elementsitem = new Element("item");
        elementsitems.addContent(elementsitem);
        elementsitem.setAttribute("code", invoice.getSku());
        elementsitem.setAttribute("name", invoice.getProductName());
        elementsitem.setAttribute("price", invoice.getPrice().toString());
        elementsitem.setAttribute("quantity", invoice.getNumber().toString());
        elementsitem.setAttribute("uom", invoice.getUnit());
        elementsitem.setAttribute("taxRate", invoice.getTaxRate().toString());
        elementsitem.setAttribute("amount", invoice.getAmount().toString());
        elementsitem.setAttribute("remark", "");

        Element elementsremarks = new Element("remarks");
        elementsinvoice.addContent(elementsremarks);
        Element elementsremark = new Element("remark");
        elementsremarks.addContent(elementsremark);
        elementsremark.setAttribute("content", content);

        Element elementsnotices = new Element("notices");
        root.addContent(elementsnotices);
        /* 现在设置为商城发短信，此逻辑暂时不执行，需要电子发票系统发短信时可把SMSFLAG设置为true */
        if (noticeMobile != null && !noticeMobile.equals("") && InvoiceConst.SMSFLAG) {
            Element elementsnotice = new Element("notice");
            elementsnotice.setAttribute("type", "sms");
            elementsnotice.setAttribute("value", noticeMobile);
            elementsnotices.addContent(elementsnotice);
        }
        if (email != null && !email.equals("")) {
            Element elementsnotice = new Element("notice");
            elementsnotice.setAttribute("type", "email");
            elementsnotice.setAttribute("value", noticeEmail);
            elementsnotices.addContent(elementsnotice);
        }

        XMLOutputter XMLOut = new XMLOutputter();//StrTools.formatXML()   可以格式化xml
        String pushData = "";
        try {
            pushData = XMLOut.outputString(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pushData == null || pushData.equals("")) {
            return null;
        }
        InvoiceElectricLogs ielog = saveInvoiceElectricLogs(invoice, pushData,
            InvoiceConst.TYPE_GENERATE);
        return ielog;
    }

    /**
     * 生成电子发票开票数据
     *
     * @param
     *
     * @return
     */
    private InvoiceElectricLogs eInvoiceGenerate(Invoices invoice, OrdersNew order) {
        if (null == invoice || null == order)
            return null;

        String email = "";
        String noticeEmail = "";

        if (StrTools.isEmail(order.getMemberEmail())) {
            email = order.getMemberEmail();
            noticeEmail = email;
        }

        String mobile = "";
        String noticeMobile = "";

        if (StrTools.isMobile(order.getMobile())) {
            mobile = order.getMobile();
            noticeMobile = mobile;
        }
        String tel = "";
        if (StrTools.isTelephone(order.getPhone())) {
            tel = order.getPhone();
        }
        if (tel.equals("") && StrTools.isMobile(order.getMobile())) {
            tel = order.getMobile();
        }

        String provincename = "";
        String cityname = "";
        String regionname = "";
        if (order.getProvince() != null && !order.getProvince().equals(0)) {
            provincename = regionsDao.get(order.getProvince()).getRegionName();
        }
        if (order.getCity() != null && !order.getCity().equals(0)) {
            cityname = regionsDao.get(order.getCity()).getRegionName();
        }
        if (order.getRegion() != null && !order.getRegion().equals(0)) {
            regionname = regionsDao.get(order.getRegion()).getRegionName();
        }

        String content = "商城订单";
        // 样品机订单发票备注
        if ("COS".equalsIgnoreCase(order.getSource())) {
            content = content + "，样品机，不退不换";
        }
        if ("DBJ".equalsIgnoreCase(order.getSource())) {
            content = content + "，夺宝机，不退不换";
        }

        //创建XML
        Element root = new Element("request");

        Document doc = new Document(root);

        Element elementsheader = new Element("header");
        root.addContent(elementsheader);
        elementsheader.setAttribute("platformCode", platformcode);//php原值PT000002  测试PT0000014
        elementsheader.setAttribute("postTime",
            DateFormatUtil.formatByType("yyyy-MM-dd HH:mm:ss", new Date()));
        elementsheader.setAttribute("sessionID", invoice.getCOrderSn() + "_KP");
        //        elementsheader.setAttribute("shopCode", "ehaier");//php有此值，java版本没有这个值
        elementsheader.setAttribute("version", InvoiceConst.VERSION);//测试V1.1，升级为V2.0

        Element elementsorder = new Element("order");
        root.addContent(elementsorder);
        elementsorder.setAttribute("account", email.equals("") ? order.getConsignee() : email);
        elementsorder.setAttribute("address", provincename
                                              + cityname
                                              + regionname
                                              + order.getAddress().replaceAll("[\\x00-\\x1f]+", "")
                                                  .replaceAll("[\\x7f-\\xff]+", ""));
        if (!email.equals("")) {
            elementsorder.setAttribute("email", email);
        }
        if (!tel.equals("")) {
            elementsorder.setAttribute("tel", tel);//php原值"mobile"=mobile
        }
        elementsorder.setAttribute("orderNo", invoice.getCOrderSn());
        //        elementsorder.setAttribute("totalAmount", invoice.getAmount().toString());//V2.0版本去掉

        Element elementsinvoice = new Element("invoice");
        root.addContent(elementsinvoice);
        elementsinvoice.setAttribute(
            "customerName",
            invoice.getInvoiceTitle().replaceAll("[\\x00-\\x1f]+", "")
                .replaceAll("[\\x7f-\\xff]+", ""));//购货方名称，即发票抬头
        elementsinvoice.setAttribute("drawer", InvoiceConst.DRAWER);//开票员
        //        elementsinvoice.setAttribute("totalAmount", invoice.getAmount().toString());//php有此值，order节点中有这个值
        //        elementsinvoice.setAttribute("shopCode", shopcode);//php在header中    php值ehaier  修改后测试值testcn//V2.0版本去掉
        //2016-12-13 XinM 电子发票增加：纳锐人识别号，地址电话，开户行账号推送
        //【备注】由于发票表地址电话、开户行账号合并保存在了一个字段里
        //所以地址电话不拆封放在地址字段，电话还是传""不变；开户行账号放在开户行字段，账号还是传""不变；
        elementsinvoice
            .setAttribute(
                "customerCode",
                invoice.getTaxPayerNumber() != null
                        && !invoice.getTaxPayerNumber().trim().equals("") ? invoice
                    .getTaxPayerNumber().trim() : "");//php无此值，不是必填项//V2.0版本，传空[购货方纳税人识别号]
        //V2.0新增字段
        //XinM 2017-2-21 商城做三证合一，使用新税号：913702127180446942。旧号370212718044694
        elementsinvoice.setAttribute("taxpayerCode", "913702127180446942");//销货方纳税人识别号
        elementsinvoice.setAttribute("customerAddress",
            invoice.getRegisterAddressAndPhone() != null
                    && !invoice.getRegisterAddressAndPhone().trim().equals("") ? invoice
                .getRegisterAddressAndPhone().trim() : "");//购货方地址
        elementsinvoice.setAttribute("customerTel", "");//购货方电话
        elementsinvoice.setAttribute("customerBankName", invoice.getBankNameAndAccount() != null
                                                         && !invoice.getBankNameAndAccount().trim()
                                                             .equals("") ? invoice
            .getBankNameAndAccount().trim() : "");//购货方开户银行
        elementsinvoice.setAttribute("customerBankAccount", "");//购货方银行账号
        elementsinvoice.setAttribute("payee", "");//收款人
        elementsinvoice.setAttribute("reviewer", "");//复核人
        elementsinvoice.setAttribute("totalAmount", invoice.getAmount().toString());//税价合计金额
        elementsinvoice.setAttribute("remark", "");//发票备注

        Element elementsitems = new Element("items");
        elementsinvoice.addContent(elementsitems);
        Element elementsitem = new Element("item");
        elementsitems.addContent(elementsitem);
        elementsitem.setAttribute("code", invoice.getSku());
        elementsitem
            .setAttribute(
                "name",
                invoice.getProductCateName() != null
                        && !invoice.getProductCateName().trim().equals("") ? invoice
                    .getProductCateName() : invoice.getProductName());
        elementsitem.setAttribute("price",
            invoice.getAmount()
                .divide(new BigDecimal(invoice.getNumber()), 6, RoundingMode.HALF_UP).toString());
        elementsitem.setAttribute("quantity", invoice.getNumber().toString());
        elementsitem.setAttribute("uom", invoice.getUnit());
        elementsitem.setAttribute("taxRate", invoice.getTaxRate().toString());
        elementsitem.setAttribute("amount", invoice.getAmount().toString());
        //        elementsitem.setAttribute("remark", "");//V2.0版本去掉
        elementsitem.setAttribute("type", "0");//0 正常行、1 折扣行、2 被 折扣行 （新增字段）
        String spec = invoice.getProductName();
        if (spec != null) {
            try {
                if (spec.getBytes("GBK").length > 40) {
                    spec = spec.substring(0, 20);
                }
            } catch (UnsupportedEncodingException e) {
                log.error("网单号:" + invoice.getCOrderSn() + "转GBK异常，商品名称："
                          + invoice.getProductName());
                spec = "";
            }
        } else {
            spec = "";
        }
        elementsitem.setAttribute("spec", spec);//商品规格型号（新增字段，如果没有则为“无”）
        elementsitem.setAttribute("imei", "");//商品IMIE号

        //V2.0版本已去掉
        //        Element elementsremarks = new Element("remarks");
        //        elementsinvoice.addContent(elementsremarks);
        //        Element elementsremark = new Element("remark");
        //        elementsremarks.addContent(elementsremark);
        //        elementsremark.setAttribute("content", content);

        Element elementsnotices = new Element("notices");
        root.addContent(elementsnotices);
        /* 现在设置为商城发短信，此逻辑暂时不执行，需要电子发票系统发短信时可把SMSFLAG设置为true */
        if (noticeMobile != null && !noticeMobile.equals("") && InvoiceConst.SMSFLAG) {
            Element elementsnotice = new Element("notice");
            elementsnotice.setAttribute("type", "sms");
            elementsnotice.setAttribute("value", noticeMobile);
            elementsnotices.addContent(elementsnotice);
        }
        if (email != null && !email.equals("")) {
            Element elementsnotice = new Element("notice");
            elementsnotice.setAttribute("type", "email");
            elementsnotice.setAttribute("value", noticeEmail);
            elementsnotices.addContent(elementsnotice);
        }

        XMLOutputter XMLOut = new XMLOutputter();//StrTools.formatXML()   可以格式化xml
        String pushData = "";
        try {
            pushData = XMLOut.outputString(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pushData == null || pushData.equals("")) {
            return null;
        }
        InvoiceElectricLogs ielog = saveInvoiceElectricLogs(invoice, pushData,
            InvoiceConst.TYPE_GENERATE);
        return ielog;
    }

    /**
     * 生成电子发票作废/冲红数据
     *
     * @param
     *
     * @return
     */
    @Deprecated
    private InvoiceElectricLogs eInvoiceInvalidOld(String memberEmail, String memberMobile,
                                                   Invoices invoices) {
        //验证邮箱地址
        String email = "";
        if (StrTools.isEmail(memberEmail)) {
            email = memberEmail;
        }
        //验证手机号
        String mobile = "无";
        String noticeMobile = "";
        if (StrTools.isMobile(memberMobile)) {
            mobile = memberMobile;
            noticeMobile = mobile;
        }
        //创建XML
        Element root = new Element("request");

        Document doc = new Document(root);

        Element elementsheader = new Element("header");
        elementsheader.setAttribute("platformCode", platformcode);//PT000002
        elementsheader.setAttribute("postTime",
            DateFormatUtil.formatByType("yyyy-MM-dd HH:mm:ss", new Date()));
        elementsheader.setAttribute("sessionID", InvoiceConst.SESSIONID);
        //        elementsheader.setAttribute("shopCode", "ehaier");//php有
        elementsheader.setAttribute("version", "V1.1");
        root.addContent(elementsheader);

        Element elementsinvoice = new Element("invoice");
        elementsinvoice.setAttribute("shopCode", shopcode);
        elementsinvoice.setAttribute("originalCode", invoices.getInvoiceNumber());
        String reason = invoices.getInvalidReason();
        elementsinvoice.setAttribute("reason", reason == null || reason.equals("") ? "作废" : reason);
        root.addContent(elementsinvoice);

        Element elementsnotices = new Element("notices");
        /* 现在设置为商城发短信，此逻辑暂时不执行，需要电子发票系统发短信时可把SMSFLAG设置为true */
        if (noticeMobile != null && !noticeMobile.equals("") && InvoiceConst.SMSFLAG) {
            Element elementsnotice = new Element("notice");
            elementsnotice.setAttribute("type", "sms");
            elementsnotice.setAttribute("value", noticeMobile);
            elementsnotices.addContent(elementsnotice);
        }
        if (email != null && !email.equals("")) {
            Element elementsnotice = new Element("notice");
            elementsnotice.setAttribute("type", "email");
            elementsnotice.setAttribute("value", email);
            elementsnotices.addContent(elementsnotice);
        }
        root.addContent(elementsnotices);

        XMLOutputter XMLOut = new XMLOutputter();//StrTools.formatXML()   可以格式化xml
        String pushData = "";
        try {
            pushData = XMLOut.outputString(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pushData == null || pushData.equals("")) {
            return null;
        }
        //插入电子发票日志
        InvoiceElectricLogs ielog = saveInvoiceElectricLogs(invoices, pushData,
            InvoiceConst.TYPE_INVALID);

        return ielog;
    }

    /**
     * 生成电子发票作废/冲红数据
     *
     * @param
     *
     * @return
     */
    private InvoiceElectricLogs eInvoiceInvalid(String memberEmail, String memberMobile,
                                                Invoices invoices) {
        //验证邮箱地址
        String email = "";
        if (StrTools.isEmail(memberEmail)) {
            email = memberEmail;
        }
        //验证手机号
        String mobile = "无";
        String noticeMobile = "";
        if (StrTools.isMobile(memberMobile)) {
            mobile = memberMobile;
            noticeMobile = mobile;
        }
        //创建XML
        Element root = new Element("request");

        Document doc = new Document(root);

        Element elementsheader = new Element("header");
        elementsheader.setAttribute("platformCode", platformcode);//PT000002
        elementsheader.setAttribute("postTime",
            DateFormatUtil.formatByType("yyyy-MM-dd HH:mm:ss", new Date()));
        elementsheader.setAttribute("sessionID", invoices.getCOrderSn() + "_CH");
        //        elementsheader.setAttribute("shopCode", "ehaier");//php有
        elementsheader.setAttribute("version", InvoiceConst.VERSION);//升级为V2.0
        root.addContent(elementsheader);

        Element elementsinvoice = new Element("invoice");
        //        elementsinvoice.setAttribute("shopCode", shopcode);//V2.0版本已去掉
        elementsinvoice.setAttribute("originalCode", invoices.getInvoiceNumber());
        String reason = invoices.getInvalidReason();
        elementsinvoice.setAttribute("reason", reason == null || reason.equals("") ? "作废" : reason);
        root.addContent(elementsinvoice);

        Element elementsnotices = new Element("notices");
        /* 现在设置为商城发短信，此逻辑暂时不执行，需要电子发票系统发短信时可把SMSFLAG设置为true */
        if (noticeMobile != null && !noticeMobile.equals("") && InvoiceConst.SMSFLAG) {
            Element elementsnotice = new Element("notice");
            elementsnotice.setAttribute("type", "sms");
            elementsnotice.setAttribute("value", noticeMobile);
            elementsnotices.addContent(elementsnotice);
        }
        if (email != null && !email.equals("")) {
            Element elementsnotice = new Element("notice");
            elementsnotice.setAttribute("type", "email");
            elementsnotice.setAttribute("value", email);
            elementsnotices.addContent(elementsnotice);
        }
        root.addContent(elementsnotices);

        XMLOutputter XMLOut = new XMLOutputter();//StrTools.formatXML()   可以格式化xml
        String pushData = "";
        try {
            pushData = XMLOut.outputString(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pushData == null || pushData.equals("")) {
            return null;
        }
        //插入电子发票日志
        InvoiceElectricLogs ielog = saveInvoiceElectricLogs(invoices, pushData,
            InvoiceConst.TYPE_INVALID);

        return ielog;
    }

    /**
     * 插入电子发票日志
     * param type 开票类型
     * @return
     */
    private InvoiceElectricLogs saveInvoiceElectricLogs(Invoices invoice, String data, Integer type) {
        if (!InvoiceConst.TYPE_GENERATE.equals(type) && !InvoiceConst.TYPE_INVALID.equals(type)
            && !InvoiceConst.TYPE_GET.equals(type)) {
            log.error("组装电子发票日志信息：开票类型未定义，网单id（" + invoice.getOrderProductId() + "）");
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
        n = invoiceElectricLogsDao.insertLog(log);
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
     * 组织发票日志信息
     * param type 开票类型
     * param flag 1开票成功，0开票失败
     * param message 返回信息
     * @return
     */
    private InvoiceApiLogs assemblyInvoiceApiLogs(Invoices invoice, String data, Integer type,
                                                  Integer flag, String message) {
        if (!InvoiceConst.ADD_KIND.equals(type) && !InvoiceConst.EDIT_KIND.equals(type)
            && !InvoiceConst.CANCEL_KIND.equals(type) && !InvoiceConst.INVALID_KIND.equals(type)
            && !InvoiceConst.RECEIVE_KIND.equals(type)) {
            log.error("组装发票日志信息：开票类型未定义，网单id（" + invoice.getOrderProductId() + "）");
            return null;
        }
        InvoiceApiLogs log = new InvoiceApiLogs();
        log.setOrderProductId(invoice.getOrderProductId());
        log.setCOrderSn(invoice.getCOrderSn());
        log.setInvoiceId(invoice.getId());
        log.setType(type);
        log.setPushData(data);
        log.setCountNum(1);
        log.setLastMessage(message);
        log.setIsSuccess(flag);
        return log;
    }

    /**
     * 插入发票日志信息
     * param type 开票类型
     * param flag 1开票成功，0开票失败
     * param message 返回信息
     * @return
     */
    private InvoiceApiLogs saveInvoiceApiLogs(Invoices invoice, String data, Integer type,
                                              Integer flag, String message) {
        if (!InvoiceConst.ADD_KIND.equals(type) && !InvoiceConst.EDIT_KIND.equals(type)
            && !InvoiceConst.CANCEL_KIND.equals(type) && !InvoiceConst.INVALID_KIND.equals(type)
            && !InvoiceConst.RECEIVE_KIND.equals(type)) {
            log.error("组装发票日志信息：开票类型未定义，网单id（" + invoice.getOrderProductId() + "）");
            return null;
        }
        InvoiceApiLogs log = invoiceApiLogsDao.getByInvoiceIdAndType(invoice.getId(), type);
        int n = 0;
        if (log == null) {
            log = new InvoiceApiLogs();
            log.setOrderProductId(invoice.getOrderProductId());
            log.setCOrderSn(invoice.getCOrderSn());
            log.setInvoiceId(invoice.getId());
            log.setType(type);
            log.setPushData(data);
            log.setReturnData("");
            log.setReceiveData("");
            log.setAddTime((int) (new Date().getTime() / 1000) + "");
            log.setLastTime((int) (new Date().getTime() / 1000));
            log.setCountNum(1);
            log.setLastMessage(message);
            log.setIsSuccess(flag);
            n = invoiceApiLogsDao.insert(log);
        } else {
            log.setOrderProductId(invoice.getOrderProductId());
            log.setCOrderSn(invoice.getCOrderSn());
            //            log.setInvoiceId(invoice.getId());
            //            log.setType(type);
            log.setPushData(data);
            log.setCountNum(log.getCountNum() == null ? 1 : log.getCountNum() + 1);
            log.setLastMessage(message);
            log.setIsSuccess(flag);
            n = invoiceApiLogsDao.updateByInvoiceIdAndType(log);
        }
        if (n < 1) {
            return null;
        }
        return log;
    }

    /**
     * 根据cOrderSn查询发票信息
     * @param cOrderSn
     * @return
     */
    public Invoices getInvoicesByCorderSn(String cOrderSn) {
        return invoicesDao.getByCorderSn(cOrderSn);
    }

    /**
     * 根据网单号cOrderSn查询发票部分信息
     * @param cOrderSn
     * @return
     */
    public Map<String, Object> searchInvoicesInfoByCOrderSn(String cOrderSn) {
        return invoicesDao.searchInvoicesInfoByCOrderSn(cOrderSn);
    }

    /**
     * 根据网单号cOrderSn查询订单信息
     * @param cOrderSn
     * @return
     */
    public OrdersNew searchOrdersInfoByCOrderSn(String cOrderSn) {
        OrderProductsNew op = orderProductsDao.getByCOrderSn(cOrderSn);
        if (op == null)
            return null;
        return ordersDao.get(op.getOrderId());
    }

    /**
     * 发送电子发票相关请求
     *
     * @param
     *
     * @return
     */
    @Deprecated
    public boolean eInvoiceSendOld(Invoices invoice, InvoiceElectricLogs invoiceElectricLogs) {
        Integer type = invoiceElectricLogs.getType();
        String[] message = new String[1];
        switch (type) {
            case 1://开票
                invoiceElectricLogs.setLastTime((int) (new Date().getTime() / 1000));
                invoice.setTryNum((invoice.getTryNum() == null ? 1 : invoice.getTryNum()) + 1);

                ServiceResult<String> result1 = null;
                try {

                    //调用电子开票接口
                    result1 = eInvoiceService.createInvoice(invoiceElectricLogs.getPushData(),
                        invoiceElectricLogs.getOrderProductId().toString());
                    //                    log.info("向电子发票系统开电子发票:" + result1.getResult());
                } catch (Exception e) {
                    log.error("推送电子开票系统创建发票接口异常：", e);
                    message[0] = "推送电子开票系统创建发票接口异常" + e.getMessage();
                    invoiceElectricLogs.setLastMessage(message[0]);
                    result1 = null;
                }
                String xml1 = null;
                org.dom4j.Document doc1 = null;
                if (result1 != null && result1.getSuccess()) {
                    xml1 = result1.getResult();
                    doc1 = parseXml(xml1, message);
                }

                if (doc1 == null) {
                    log.error("推送开票系统电子发票解析返回结果为空：" + message[0]);
                    invoiceElectricLogs.setLastMessage(message[0]);
                    invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                    invoicesDao.update(invoice);
                    return false;
                }

                //开启事务
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                TransactionStatus transtatus = transactionManager.getTransaction(def);
                try {
                    boolean updateFlag = false;
                    String oldReturnData = invoiceElectricLogs.getReturnData();
                    //请求返回后插入日志
                    if (result1 == null) {//失败
                        log.error("推送开票系统电子发票返回结果为空：" + message[0]);
                        invoiceElectricLogs.setLastMessage(message[0]);
                        invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                        invoicesDao.update(invoice);
                    } else if (!result1.getSuccess()) {//失败
                        message[0] = result1.getMessage();
                        log.error("推送开票系统电子发票返回结果信息出错：" + message[0]);
                        invoiceElectricLogs.setLastMessage(message[0]);
                        invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                        invoicesDao.update(invoice);
                    } else {
                        invoiceElectricLogs.setReturnData(xml1);

                        org.dom4j.Element resultElement = doc1.getRootElement().element("result");
                        String code = resultElement.attribute("code").getValue();
                        message[0] = resultElement.attribute("message").getValue();
                        invoiceElectricLogs.setLastMessage(message[0]);

                        if (code != null && code.equals("1")) {//成功
                            org.dom4j.Element invoiceElement = doc1.getRootElement().element(
                                "invoice");

                            invoiceElectricLogs.setSuccess(1);
                            invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);

                            if (invoiceElement != null) {
                                @SuppressWarnings("unchecked")
                                List<Attribute> listInvoice = invoiceElement.attributes();
                                Map<String, String> attMap = new HashMap<String, String>();
                                if (listInvoice != null && listInvoice.size() > 0) {
                                    for (int i = 0; i < listInvoice.size(); i++) {
                                        if (listInvoice.get(i).getName()
                                            .equalsIgnoreCase("generateTime")) {
                                            attMap.put(listInvoice.get(i).getName(),
                                                (timeStringToDate(listInvoice.get(i).getValue())
                                                    .getTime() / 1000) + "");
                                        } else {
                                            attMap.put(listInvoice.get(i).getName(), listInvoice
                                                .get(i).getValue());
                                        }
                                    }

                                    updateFlag = eInvoiceUpdateOld(invoice,
                                        invoiceElectricLogs.getType(), attMap);
                                    if (!updateFlag) {
                                        log.error("推送开票系统开票：更新电子发票信息返回false");
                                    }
                                } else {
                                    invoiceElectricLogsDao
                                        .updateByInvoiceIdAndType(invoiceElectricLogs);
                                    invoicesDao.update(invoice);
                                }
                            } else {
                                log.error(message);
                                invoiceElectricLogsDao
                                    .updateByInvoiceIdAndType(invoiceElectricLogs);
                                invoicesDao.update(invoice);
                            }
                        } else if (code != null && code.equals("4") && message.equals("该订单已开过发票")) {
                            org.dom4j.Element invoiceElement = doc1.getRootElement().element(
                                "invoice");

                            doc1 = parseXml(oldReturnData, message);
                            if (doc1 == null) {
                                log.error("推送开票系统开票：更新电子发票信息返回false");
                                log.error("电子发票返回的xml:" + xml1);
                                invoiceElectricLogs.setReturnData(xml1);
                                invoiceElectricLogs.setLastMessage(message[0]);
                                invoiceElectricLogsDao
                                    .updateByInvoiceIdAndType(invoiceElectricLogs);
                                invoice.setTryNum(20);//返回code=4，并且message=该订单已开过发票    设置重试次数20次，不在执行，未开票成功，待人工核对
                                invoicesDao.update(invoice);
                            } else {
                                resultElement = doc1.getRootElement().element("result");
                                invoiceElement = doc1.getRootElement().element("invoice");
                                code = resultElement.attribute("code").getValue();
                                message[0] = resultElement.attribute("message").getValue();
                                invoiceElectricLogs.setLastMessage(message[0]);
                                if (code != null && code.equals("1")) {//成功
                                    invoiceElectricLogs.setReturnData(oldReturnData);
                                    invoiceElectricLogs.setSuccess(1);
                                    invoiceElectricLogsDao
                                        .updateByInvoiceIdAndType(invoiceElectricLogs);

                                    if (invoiceElement != null) {
                                        @SuppressWarnings("unchecked")
                                        List<Attribute> listInvoice = invoiceElement.attributes();
                                        Map<String, String> attMap = new HashMap<String, String>();
                                        if (listInvoice != null && listInvoice.size() > 0) {
                                            for (int i = 0; i < listInvoice.size(); i++) {
                                                if (listInvoice.get(i).getName()
                                                    .equalsIgnoreCase("generateTime")) {
                                                    attMap.put(
                                                        listInvoice.get(i).getName(),
                                                        (timeStringToDate(
                                                            listInvoice.get(i).getValue())
                                                            .getTime() / 1000) + "");
                                                } else {
                                                    attMap.put(listInvoice.get(i).getName(),
                                                        listInvoice.get(i).getValue());
                                                }
                                            }

                                            updateFlag = eInvoiceUpdateOld(invoice,
                                                invoiceElectricLogs.getType(), attMap);
                                            if (!updateFlag) {
                                                log.error("推送开票系统开票：更新电子发票信息返回false");
                                            }
                                        } else {
                                            invoiceElectricLogsDao
                                                .updateByInvoiceIdAndType(invoiceElectricLogs);
                                            invoicesDao.update(invoice);
                                        }
                                    } else {
                                        log.error(message);
                                        invoiceElectricLogsDao
                                            .updateByInvoiceIdAndType(invoiceElectricLogs);
                                        invoicesDao.update(invoice);
                                    }
                                } else {
                                    log.error(message);
                                    invoiceElectricLogsDao
                                        .updateByInvoiceIdAndType(invoiceElectricLogs);
                                    invoice.setTryNum(20);//返回code=4，并且message=该订单已开过发票    设置重试次数20次，不在执行，未开票成功，待人工核对
                                    invoicesDao.update(invoice);
                                }
                            }
                        } else {
                            log.error(message);
                            invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                            invoicesDao.update(invoice);
                        }
                    }
                    // 提交事务
                    transactionManager.commit(transtatus);
                    return updateFlag;
                } catch (Exception e) {
                    // 回滚事务
                    transactionManager.rollback(transtatus);
                    log.error("推送电子开票系统-创建发票-更新处理结果异常：", e);
                    invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                    invoicesDao.update(invoice);
                    return false;
                }
                //                break;
            case 2://作废
                invoiceElectricLogs.setLastTime((int) (new Date().getTime() / 1000));
                invoice.setTryNum((invoice.getTryNum() == null ? 1 : invoice.getTryNum()) + 1);

                ServiceResult<String> result2 = null;
                try {
                    //调用电子开票作废接口
                    result2 = eInvoiceService.invalidInvoice(invoiceElectricLogs.getPushData(),
                        invoiceElectricLogs.getOrderProductId().toString());
                } catch (Exception e) {
                    log.error("推送电子开票系统作废发票接口异常：", e);
                    message[0] = "推送电子开票系统作废发票接口异常:" + e.getMessage();
                    invoiceElectricLogs.setLastMessage(message[0]);
                    result2 = null;
                }

                String xml2 = null;
                org.dom4j.Document doc2 = null;
                if (result2 != null && result2.getSuccess()) {
                    xml2 = result2.getResult();
                    doc2 = parseXml(xml2, message);
                }
                if (doc2 == null) {
                    log.error(message);
                    log.error("电子发票返回的xml:" + xml2);
                    invoiceElectricLogs.setLastMessage(message[0]);
                    invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                    invoicesDao.update(invoice);
                    return false;
                }
                //开启事务
                DefaultTransactionDefinition def2 = new DefaultTransactionDefinition();
                def2.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                TransactionStatus transtatus2 = transactionManager.getTransaction(def2);
                try {
                    boolean updateFlag = false;
                    //请求返回后插入日志
                    if (result2 == null) {//失败
                        message[0] = "推送电子开票系统作废发票接口异常";//返回结果超时
                        log.error(message);
                        invoiceElectricLogs.setLastMessage(message[0]);
                        invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                        invoicesDao.update(invoice);
                    } else if (!result2.getSuccess()) {//失败
                        message[0] = result2.getMessage();
                        log.error(message[0]);
                        invoiceElectricLogs.setLastMessage(message[0]);
                        invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                        invoicesDao.update(invoice);
                    } else {
                        invoiceElectricLogs.setReturnData(xml2);
                        org.dom4j.Element resultElement = doc2.getRootElement().element("result");
                        String code = resultElement.attribute("code").getValue();
                        message[0] = resultElement.attribute("message").getValue();
                        invoiceElectricLogs.setLastMessage(message[0]);

                        if (code != null && code.equals("1")) {//成功
                            org.dom4j.Element invoiceElement = doc2.getRootElement().element(
                                "invoice");

                            @SuppressWarnings("unchecked")
                            List<Attribute> listInvoice = invoiceElement.attributes();
                            Map<String, String> attMap = new HashMap<String, String>();
                            if (listInvoice != null && listInvoice.size() > 0) {
                                for (int i = 0; i < listInvoice.size(); i++) {
                                    if (listInvoice.get(i).getName().equalsIgnoreCase("validTime")) {
                                        attMap.put(listInvoice.get(i).getName(),
                                            (timeStringToDate(listInvoice.get(i).getValue())
                                                .getTime() / 1000) + "");
                                    } else {
                                        attMap.put(listInvoice.get(i).getName(), listInvoice.get(i)
                                            .getValue());
                                    }
                                }
                            }

                            boolean eiu = eInvoiceUpdateOld(invoice, invoiceElectricLogs.getType(),
                                attMap);
                            if (!eiu) {
                                log.error("作废发票时，更新电子发票失败");
                                invoiceElectricLogsDao
                                    .updateByInvoiceIdAndType(invoiceElectricLogs);
                                invoicesDao.update(invoice);
                            } else {
                                invoiceElectricLogs.setSuccess(1);
                                invoiceElectricLogsDao
                                    .updateByInvoiceIdAndType(invoiceElectricLogs);
                                // 同步修改退换货申请的发票状态
                                updateFlag = orderRepairsCancelInvoice(invoice.getOrderProductId());
                                if (!updateFlag) {
                                    log.error("作废发票时，修改退换货申请的发票状态失败，网单id:"
                                              + invoice.getOrderProductId());
                                }
                            }
                        } else {
                            log.error(message[0] + " 发票id:" + invoice.getId());
                            invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                            invoicesDao.update(invoice);
                        }
                    }
                    // 提交事务
                    transactionManager.commit(transtatus2);
                    return updateFlag;
                } catch (Exception e) {
                    // 回滚事务
                    transactionManager.rollback(transtatus2);
                    log.error("推送电子开票系统-作废发票-更新处理结果异常：", e);
                    invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                    invoicesDao.update(invoice);
                    return false;
                }
                //                break;
            default:
                log.error("发送开票系统时，开票类型不匹配");
                return false;
        }
    }

    /**
     * 发送电子发票相关请求
     *
     * @param
     *
     * @return
     */
    public boolean eInvoiceSend(Invoices invoice, InvoiceElectricLogs invoiceElectricLogs) {
        Integer type = invoiceElectricLogs.getType();
        String[] message = new String[1];
        switch (type) {
            case 1://开票
                invoiceElectricLogs.setLastTime((int) (new Date().getTime() / 1000));
                invoice.setTryNum((invoice.getTryNum() == null ? 1 : invoice.getTryNum()) + 1);

                ServiceResult<String> result1 = null;
                try {

                    //调用电子开票接口
                    //                    result1 = eInvoiceService.createInvoice(invoiceElectricLogs.getPushData(),
                    //                        invoiceElectricLogs.getOrderProductId().toString());
                    result1 = eInvoiceV2ServiceImpl.createInvoice(invoiceElectricLogs
                        .getOrderProductId().toString(), invoiceElectricLogs.getPushData());
                    //                    log.info("向电子发票系统开电子发票:" + result1.getResult());
                } catch (Exception e) {
                    log.error("推送电子开票系统创建发票接口异常：", e);
                    message[0] = "推送电子开票系统创建发票接口异常" + e.getMessage();
                    invoiceElectricLogs.setLastMessage(message[0]);
                    result1 = null;
                }
                String xml1 = null;
                org.dom4j.Document doc1 = null;
                if (result1 != null && result1.getSuccess()) {
                    xml1 = result1.getResult();
                    doc1 = parseXml(xml1, message);
                }
                if (doc1 == null) {
                    log.error("推送开票系统电子发票解析返回结果为空：" + message[0]);
                    invoiceElectricLogs.setLastMessage(message[0]);
                    invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                    invoicesDao.update(invoice);
                    return false;
                }

                //开启事务
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                TransactionStatus transtatus = transactionManager.getTransaction(def);
                try {
                    boolean updateFlag = false;
                    String oldReturnData = invoiceElectricLogs.getReturnData();
                    //请求返回后插入日志
                    if (result1 == null) {//失败
                        log.error("推送开票系统电子发票返回结果为空：" + message[0]);
                        invoiceElectricLogs.setLastMessage(message[0]);
                        invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                        invoicesDao.update(invoice);
                    } else if (!result1.getSuccess()) {//失败
                        message[0] = result1.getMessage();
                        log.error("推送开票系统电子发票返回结果信息出错：" + message[0]);
                        invoiceElectricLogs.setLastMessage(message[0]);
                        invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                        invoicesDao.update(invoice);
                    } else {
                        invoiceElectricLogs.setReturnData(xml1);

                        org.dom4j.Element resultElement = doc1.getRootElement().element("result");
                        String code = resultElement.attribute("code").getValue();
                        message[0] = resultElement.attribute("message").getValue();
                        invoiceElectricLogs.setLastMessage(message[0]);

                        if (code != null && code.equals("0")) {//成功
                            org.dom4j.Element invoiceElement = doc1.getRootElement()
                                .element("invoices").element("invoice");

                            invoiceElectricLogs.setSuccess(1);
                            invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);

                            if (invoiceElement != null) {
                                @SuppressWarnings("unchecked")
                                List<Attribute> listInvoice = invoiceElement.attributes();
                                Map<String, String> attMap = new HashMap<String, String>();
                                if (listInvoice != null && listInvoice.size() > 0) {
                                    for (int i = 0; i < listInvoice.size(); i++) {
                                        if (listInvoice.get(i).getName()
                                            .equalsIgnoreCase("generateTime")) {
                                            attMap.put(listInvoice.get(i).getName(),
                                                (timeStringToDate(listInvoice.get(i).getValue())
                                                    .getTime() / 1000) + "");
                                        } else {
                                            attMap.put(listInvoice.get(i).getName(), listInvoice
                                                .get(i).getValue());
                                        }
                                    }

                                    updateFlag = eInvoiceUpdate(invoice,
                                        invoiceElectricLogs.getType(), attMap);
                                    if (!updateFlag) {
                                        log.error("推送开票系统开票：更新电子发票信息返回false");
                                    }
                                } else {
                                    invoiceElectricLogsDao
                                        .updateByInvoiceIdAndType(invoiceElectricLogs);
                                    invoicesDao.update(invoice);
                                }
                            } else {
                                log.error(message);
                                invoiceElectricLogsDao
                                    .updateByInvoiceIdAndType(invoiceElectricLogs);
                                invoicesDao.update(invoice);
                            }
                        } else if (code != null && code.equals("5") && message.equals("该订单已开过发票")) {
                            org.dom4j.Element invoiceElement = doc1.getRootElement()
                                .element("invoices").element("invoice");

                            doc1 = parseXml(oldReturnData, message);
                            if (doc1 == null) {
                                log.error("推送开票系统开票：更新电子发票信息返回false");
                                log.error("电子发票返回的xml:" + xml1);
                                invoiceElectricLogs.setReturnData(xml1);
                                invoiceElectricLogs.setLastMessage(message[0]);
                                invoiceElectricLogsDao
                                    .updateByInvoiceIdAndType(invoiceElectricLogs);
                                invoice.setTryNum(20);//返回code=4，并且message=该订单已开过发票    设置重试次数20次，不在执行，未开票成功，待人工核对
                                invoicesDao.update(invoice);
                            } else {
                                resultElement = doc1.getRootElement().element("result");
                                invoiceElement = doc1.getRootElement().element("invoices")
                                    .element("invoice");
                                code = resultElement.attribute("code").getValue();
                                message[0] = resultElement.attribute("message").getValue();
                                invoiceElectricLogs.setLastMessage(message[0]);
                                if (code != null && code.equals("0")) {//成功
                                    invoiceElectricLogs.setReturnData(oldReturnData);
                                    invoiceElectricLogs.setSuccess(1);
                                    invoiceElectricLogsDao
                                        .updateByInvoiceIdAndType(invoiceElectricLogs);

                                    if (invoiceElement != null) {
                                        @SuppressWarnings("unchecked")
                                        List<Attribute> listInvoice = invoiceElement.attributes();
                                        Map<String, String> attMap = new HashMap<String, String>();
                                        if (listInvoice != null && listInvoice.size() > 0) {
                                            for (int i = 0; i < listInvoice.size(); i++) {
                                                if (listInvoice.get(i).getName()
                                                    .equalsIgnoreCase("generateTime")) {
                                                    attMap.put(
                                                        listInvoice.get(i).getName(),
                                                        (timeStringToDate(
                                                            listInvoice.get(i).getValue())
                                                            .getTime() / 1000) + "");
                                                } else {
                                                    attMap.put(listInvoice.get(i).getName(),
                                                        listInvoice.get(i).getValue());
                                                }
                                            }

                                            updateFlag = eInvoiceUpdate(invoice,
                                                invoiceElectricLogs.getType(), attMap);
                                            if (!updateFlag) {
                                                log.error("推送开票系统开票：更新电子发票信息返回false");
                                            }
                                        } else {
                                            invoiceElectricLogsDao
                                                .updateByInvoiceIdAndType(invoiceElectricLogs);
                                            invoicesDao.update(invoice);
                                        }
                                    } else {
                                        log.error(message);
                                        invoiceElectricLogsDao
                                            .updateByInvoiceIdAndType(invoiceElectricLogs);
                                        invoicesDao.update(invoice);
                                    }
                                } else {
                                    log.error(message);
                                    invoiceElectricLogsDao
                                        .updateByInvoiceIdAndType(invoiceElectricLogs);
                                    invoice.setTryNum(20);//返回code=4，并且message=该订单已开过发票    设置重试次数20次，不在执行，未开票成功，待人工核对
                                    invoicesDao.update(invoice);
                                }
                            }
                        } else {
                            log.error(message);
                            invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                            invoicesDao.update(invoice);
                        }
                    }
                    // 提交事务
                    transactionManager.commit(transtatus);
                    return updateFlag;
                } catch (Exception e) {
                    // 回滚事务
                    transactionManager.rollback(transtatus);
                    log.error("推送电子开票系统-创建发票-更新处理结果异常：", e);
                    invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                    invoicesDao.update(invoice);
                    return false;
                }
                //                break;
            case 2://作废
                invoiceElectricLogs.setLastTime((int) (new Date().getTime() / 1000));
                invoice.setTryNum((invoice.getTryNum() == null ? 1 : invoice.getTryNum()) + 1);

                ServiceResult<String> result2 = null;
                try {
                    //调用电子开票作废接口
                    //                    result2 = eInvoiceService.invalidInvoice(invoiceElectricLogs.getPushData(),
                    //                        invoiceElectricLogs.getOrderProductId().toString());
                    result2 = eInvoiceV2ServiceImpl.invalidInvoice(invoiceElectricLogs
                        .getOrderProductId().toString(), invoiceElectricLogs.getPushData());
                } catch (Exception e) {
                    log.error("推送电子开票系统作废发票接口异常：", e);
                    message[0] = "推送电子开票系统作废发票接口异常:" + e.getMessage();
                    invoiceElectricLogs.setLastMessage(message[0]);
                    result2 = null;
                }

                String xml2 = null;
                org.dom4j.Document doc2 = null;
                if (result2 != null && result2.getSuccess()) {
                    xml2 = result2.getResult();
                    doc2 = parseXml(xml2, message);
                }
                if (doc2 == null) {
                    log.error(message);
                    log.error("电子发票返回的xml:" + xml2);
                    invoiceElectricLogs.setLastMessage(message[0]);
                    invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                    invoicesDao.update(invoice);
                    return false;
                }

                //开启事务
                DefaultTransactionDefinition def2 = new DefaultTransactionDefinition();
                def2.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                TransactionStatus transtatus2 = transactionManager.getTransaction(def2);
                try {
                    boolean updateFlag = false;
                    //请求返回后插入日志
                    if (result2 == null) {//失败
                        message[0] = "推送电子开票系统作废发票接口异常";//返回结果超时
                        log.error(message);
                        invoiceElectricLogs.setLastMessage(message[0]);
                        invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                        invoicesDao.update(invoice);
                    } else if (!result2.getSuccess()) {//失败
                        message[0] = result2.getMessage();
                        log.error(message[0]);
                        invoiceElectricLogs.setLastMessage(message[0]);
                        invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                        invoicesDao.update(invoice);
                    } else {
                        invoiceElectricLogs.setReturnData(xml2);
                        org.dom4j.Element resultElement = doc2.getRootElement().element("result");
                        String code = resultElement.attribute("code").getValue();
                        message[0] = resultElement.attribute("message").getValue();
                        invoiceElectricLogs.setLastMessage(message[0]);

                        if (code != null && code.equals("0")) {//成功
                            org.dom4j.Element invoiceElement = doc2.getRootElement()
                                .element("invoices").element("invoice");

                            @SuppressWarnings("unchecked")
                            List<Attribute> listInvoice = invoiceElement.attributes();
                            Map<String, String> attMap = new HashMap<String, String>();
                            if (listInvoice != null && listInvoice.size() > 0) {
                                for (int i = 0; i < listInvoice.size(); i++) {
                                    if (listInvoice.get(i).getName().equalsIgnoreCase("validTime")) {
                                        attMap.put(listInvoice.get(i).getName(),
                                            (timeStringToDate(listInvoice.get(i).getValue())
                                                .getTime() / 1000) + "");
                                    } else {
                                        attMap.put(listInvoice.get(i).getName(), listInvoice.get(i)
                                            .getValue());
                                    }
                                }
                            }

                            boolean eiu = eInvoiceUpdate(invoice, invoiceElectricLogs.getType(),
                                attMap);
                            if (!eiu) {
                                log.error("作废发票时，更新电子发票失败");
                                invoiceElectricLogsDao
                                    .updateByInvoiceIdAndType(invoiceElectricLogs);
                                invoicesDao.update(invoice);
                            } else {
                                invoiceElectricLogs.setSuccess(1);
                                invoiceElectricLogsDao
                                    .updateByInvoiceIdAndType(invoiceElectricLogs);
                                // 同步修改退换货申请的发票状态
                                updateFlag = orderRepairsCancelInvoice(invoice.getOrderProductId());
                                if (!updateFlag) {
                                    log.error("作废发票时，修改退换货申请的发票状态失败，网单id:"
                                              + invoice.getOrderProductId());
                                }
                            }
                        } else {
                            log.error(message[0] + " 发票id:" + invoice.getId());
                            invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                            invoicesDao.update(invoice);
                        }
                    }
                    // 提交事务
                    transactionManager.commit(transtatus2);
                    return updateFlag;
                } catch (Exception e) {
                    // 回滚事务
                    transactionManager.rollback(transtatus2);
                    log.error("推送电子开票系统-作废发票-更新处理结果异常：", e);
                    invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);
                    invoicesDao.update(invoice);
                    return false;
                }
                //                break;
            default:
                log.error("发送开票系统时，开票类型不匹配");
                return false;
        }
    }

    private org.dom4j.Document parseXml(String xml, String[] message) {
        org.dom4j.Document doc = null;
        if (xml != null && !xml.equals("")) {
            try {
                doc = DocumentHelper.parseText(xml);
            } catch (DocumentException e) {
                message[0] = "解析返回XML结果时异常:" + e.getMessage();
                log.error(message + ":" + xml, e);
            }
        } else {
            message[0] = "返回XML结果为空";
            log.error(message);
        }
        return doc;
    }

    /**
     * 更新电子发票开票信息
     *
     * @return
     */
    @Deprecated
    private boolean eInvoiceUpdateOld(Invoices invoice, Integer type, Map<String, String> attMap) {
        boolean sourceSMSFlag = true;//根据订单来源判断是否发短信
        String noSmsSourceCode = "BLPHH";//不发短信订单来源
        if (type == null) {
            invoicesDao.update(invoice);
            return false;
        }

        if (attMap == null) {
            invoicesDao.update(invoice);
            return false;
        }
        //并发时，过滤同步操作数据库
        if (invoice.getSuccess().equals(1)) {
            log.error("更新电子发票开票信息时：发票已经被其他进程执行完成，发票id：" + invoice.getId());
            return true;
        }
        String userMobile = "";
        Integer orderId = 0;
        int statusType = 1;//发票操作类型，1开票
        String orderSource = "";//订单来源，微店订单发生短信特殊处理
        switch (type) {
            case 1://开票
                invoice.setState(InvoiceConst.COMPLETE_STATE.intValue());
                invoice.setSuccess(1);
                invoice.setEaiWriteState(InvoiceConst.NORMAL_KP_STATE);
                invoice.setDrawer("CBS");
                invoice.setBillingTime(Long.parseLong(attMap.get("generateTime")));
                invoice.setInvoiceNumber(attMap.get("code"));
                invoice.setEInvViewUrl(attMap.get("viewUrl"));

                String fiscalCode = "";
                if (attMap.get("fiscalCode") != null && !attMap.get("fiscalCode").equals("")) {
                    fiscalCode = attMap.get("fiscalCode");
                } else {
                    fiscalCode = attMap.get("viewUrl").substring(
                        attMap.get("viewUrl").indexOf("=") + 1);
                }
                invoice.setFiscalCode(fiscalCode);

                statusType = 1;//开票
                if (invoice.getCOrderType().equals(InvoiceConst.COMMON_CORDER_TYPE)) {//普通网单
                    OrderProductsNew op = orderProductsDao.get(invoice.getOrderProductId());
                    if (op != null) {
                        op.setReceiptNum(invoice.getInvoiceNumber());
                        op.setIsMakeReceipt(InvoiceConst.MR_STATE_MAKED);//// 已开票
                        op.setReceiptAddTime(DateFormatUtil.formatTime("yyyyMMdd",
                            invoice.getBillingTime()));
                        op.setSystemRemark(op.getSystemRemark() + "发票状态变更为已开票,isMakeReceipt:{"
                                           + op.getIsMakeReceipt() + "},receiptNum:{"
                                           + op.getReceiptNum() + "},receiptAddTime:{"
                                           + op.getReceiptAddTime() + "};");
                        orderProductsDao.update(op);

                        OrdersNew order = ordersDao.get(op.getOrderId());
                        orderSource = order.getSource();
                        if (noSmsSourceCode.equalsIgnoreCase(order.getSource())) {
                            sourceSMSFlag = false;
                        }
                        orderId =Integer.parseInt(order.getId());
                        //记录开票操作日志
                        OrderOperateLogs log = this.AssemblyOrderOperateLog(order, op,
                            "发票状态变更为:“已开票”。税控码：" + fiscalCode, "发票状态更改", "电子发票系统");
                        orderOperateLogsDao.insert(log);

                        InvoiceElectricSyncLogs invoiceElectricSyncLogs = AssemblyInvoiceElectricSyncLogs(
                            order, op, invoice, attMap);

                        List<InvoiceElectricSyncLogs> iesllist = invoiceElectricSyncLogsDao
                            .getByCOrderSnAngStatusType(invoice.getCOrderSn(), statusType);
                        if (iesllist == null || iesllist.size() < 1) {
                            invoiceElectricSyncLogsDao.insert(invoiceElectricSyncLogs);
                        }

                        //创建发票成功后同步HP队列表插入数据，单独运行，表`invoice_electric_2_out`,另外写job读取该表数据执行上面的电子发票信息传HP功能
                        insertInvoiceElectric2Out(invoice.getId(), "HP");

                        userMobile = order.getMobile() == null || order.getMobile().equals("") ? order
                            .getPhone() : order.getMobile();
                    }
                } else if (invoice.getCOrderType().equals(InvoiceConst.DIFF_CORDER_TYPE)) {//差异网单
                    Order2ths order2ths = order2thsDao.get(invoice.getDiffId());
                    if (order2ths != null) {
                        orderId = order2ths.getId();
                        order2ths.setReceiptNumber(invoice.getInvoiceNumber());
                        order2ths.setJSDate(DateFormatUtil.formatTime("yyyyMMdd",
                            invoice.getBillingTime()));
                        order2ths.setIsReceipted(InvoiceConst.R_RECEIPTED);
                        order2thsDao.update(order2ths);
                    }
                    // 同步向SAP传递开票信息
                    // 差异订单开票前需要先将订单信息传SAP
                    //                    Order2ths::sendSAP($invoice->diffId);
                    //向SAP传递开票信息先插入表`invoice_electric_2_out`,sendType=SAP，另外写job读取该表数据执行传SAP功能
                    insertInvoiceElectric2Out(invoice.getId(), "SAP");

                    userMobile = order2ths.getMobile1();
                } else {
                    Order4Invoices order4Invoice = order4InvoicesDao.get(invoice.getDiffId());
                    if (order4Invoice != null) {
                        orderId = order4Invoice.getId();
                        order4Invoice.setReceiptNumber(invoice.getInvoiceNumber());
                        order4Invoice.setJSDate(DateFormatUtil.formatTime("yyyyMMdd",
                            invoice.getBillingTime()));
                        order4Invoice.setIsReceipted(InvoiceConst.R_RECEIPTED);
                        order4InvoicesDao.update(order4Invoice);
                    }
                    userMobile = order4Invoice.getMobile1();
                }

                invoicesDao.update(invoice);

                MemberInvoices mInvoices = memberInvoicesDao.getByOrderId(orderId);
                if (mInvoices != null) {
                    mInvoices.setIsLock(1);//开票成功后设置锁，不让修改信息
                    memberInvoicesDao.update(mInvoices);
                }

                InvoiceSAPLogs invoiceSAPLogs = OrderBizHelper.sapGenerate(invoiceSAPLogsDao,
                    orderProductsDao, invoice, 0, this.getPathByName("/upfiles/sapblacklist.txt"));
                boolean sendSap = false;
                if (invoiceSAPLogs != null) {
                    int n = invoiceSAPLogsDao.insert(invoiceSAPLogs);
                    if (n < 1) {
                        log.error("插入invoiceSAPLogs信息失败，发票id:" + invoice.getId());
                    } else {
                        sendSap = true;
                    }
                }
                if (!sendSap) {
                    log.error("开票时，sapGenerate更新电子发票，未推送Sap开票信息，发票id:" + invoice.getId());
                }
                break;
            case 2://作废
                statusType = 2;//红冲
                if (attMap.get("status").equals("3") || attMap.get("status").equals("4")) {//原php invalidateType=1   新java  status=3冲红     status=2作废   status=1开票   status=4被红冲
                    // 红冲
                    invoice.setInvalidTime(Long.parseLong(attMap.get("validTime")));
                    invoice.setEaiWriteState(InvoiceConst.MONTH_INVALID_KP_STATE);
                    if (invoice.getCOrderType().equals(InvoiceConst.COMMON_CORDER_TYPE)) {
                        OrderProductsNew op = orderProductsDao.get(invoice.getOrderProductId());
                        if (op != null) {
                            op.setIsMakeReceipt(InvoiceConst.MR_STATE_RED);
                            orderProductsDao.update(op);

                            OrdersNew order = ordersDao.get(op.getOrderId());
                            orderSource = order.getSource();
                            if (noSmsSourceCode.equalsIgnoreCase(order.getSource())) {
                                sourceSMSFlag = false;
                            }
                            orderId = Integer.parseInt(order.getId());
                            //记录开票操作日志
                            OrderOperateLogs log = this.AssemblyOrderOperateLog(order, op,
                                "发票状态变更为“跨月冲红”", "发票状态更改", "电子发票系统");
                            orderOperateLogsDao.insert(log);

                            //红冲时也写入队列  2015-06-29添加 wyj
                            InvoiceElectricSyncLogs invoiceElectricSyncLogs = AssemblyInvoiceElectricSyncLogs(
                                order, op, invoice, attMap);

                            List<InvoiceElectricSyncLogs> iesllist = invoiceElectricSyncLogsDao
                                .getByCOrderSnAngStatusType(invoice.getCOrderSn(), statusType);
                            if (iesllist == null || iesllist.size() < 1) {
                                invoiceElectricSyncLogsDao.insert(invoiceElectricSyncLogs);
                            }

                            userMobile = order.getMobile();
                        }
                    } else if (invoice.getCOrderType().equals(InvoiceConst.DIFF_CORDER_TYPE)) {
                        Order2ths order2th = order2thsDao.get(invoice.getDiffId());
                        orderId = order2th.getId();
                        userMobile = order2th.getMobile1();
                    } else {
                        Order4Invoices order4Invoice = order4InvoicesDao.get(invoice.getDiffId());
                        orderId = order4Invoice.getId();
                        userMobile = order4Invoice.getMobile1();
                    }
                } else if (attMap.get("status").equals("2")) {
                    // 作废情况已经不存在了，都是红冲，所以没有2的情况
                    invoice.setInvalidTime(Long.parseLong(attMap.get("validTime")));
                    invoice.setEaiWriteState(InvoiceConst.INVALID_KP_STATE);
                    if (invoice.getCOrderType().equals(InvoiceConst.COMMON_CORDER_TYPE)) {
                        OrderProductsNew op = orderProductsDao.get(invoice.getOrderProductId());
                        if (op != null) {
                            op.setIsMakeReceipt(InvoiceConst.MR_STATE_NULLIFY);
                            orderProductsDao.update(op);

                            OrdersNew order = ordersDao.get(op.getOrderId());
                            orderSource = order.getSource();
                            if (noSmsSourceCode.equalsIgnoreCase(order.getSource())) {
                                sourceSMSFlag = false;
                            }
                            orderId = Integer.parseInt(order.getId());
                            //记录开票操作日志
                            OrderOperateLogs log = this.AssemblyOrderOperateLog(order, op,
                                "发票状态变更为“当月作废”", "发票状态更改", "电子发票系统");
                            orderOperateLogsDao.insert(log);

                            //红冲时也写入队列  2015-06-29添加 wyj
                            InvoiceElectricSyncLogs invoiceElectricSyncLogs = AssemblyInvoiceElectricSyncLogs(
                                order, op, invoice, attMap);

                            List<InvoiceElectricSyncLogs> iesllist = invoiceElectricSyncLogsDao
                                .getByCOrderSnAngStatusType(invoice.getCOrderSn(), statusType);
                            if (iesllist == null || iesllist.size() < 1) {
                                invoiceElectricSyncLogsDao.insert(invoiceElectricSyncLogs);
                            }

                            userMobile = order.getMobile();
                        }
                    } else if (invoice.getCOrderType().equals(InvoiceConst.DIFF_CORDER_TYPE)) {
                        Order2ths order2th = order2thsDao.get(invoice.getDiffId());
                        orderId = order2th.getId();
                        userMobile = order2th.getMobile1();
                    } else {
                        Order4Invoices order4Invoice = order4InvoicesDao.get(invoice.getDiffId());
                        orderId = order4Invoice.getId();
                        userMobile = order4Invoice.getMobile1();
                    }
                }
                invoice.setSuccess(1);
                invoicesDao.update(invoice);

                invoiceSAPLogs = OrderBizHelper.sapGenerate(invoiceSAPLogsDao, orderProductsDao,
                    invoice, 0, this.getPathByName("/upfiles/sapblacklist.txt"));
                sendSap = false;
                if (invoiceSAPLogs != null) {
                    int n = invoiceSAPLogsDao.insert(invoiceSAPLogs);
                    if (n < 1) {
                        log.error("插入invoiceSAPLogs信息失败，发票id:" + invoice.getId());
                    } else {
                        sendSap = true;
                    }
                }
                if (!sendSap) {
                    log.error("开票时，sapGenerate更新电子发票，未推送Sap开票信息，发票id:" + invoice.getId());
                }

                /*boolean flag2 = OrderBizHelper.sapGenerate(invoiceSAPLogsDao, orderProductsDao,
                    invoice, 0, this.getPathByName("/upfiles/sapblacklist.txt"));
                if (!flag2) {
                    log.error("发票作废时，sapGenerate更新电子发票，未推送Sap作废信息，发票id:" + invoice.getId());
                }*/
                break;
            default:
                break;
        }
        // 电子发票发短信-商城发短信
        if (!InvoiceConst.SMSFLAG && sourceSMSFlag) {
            String content1 = "";
            String content2 = "";
            if (InvoiceConst.TYPE_INVALID.equals(type)) {
                if (orderSource != null && orderSource.equalsIgnoreCase("MSTORE")) {
                    //                    content1 = "【顺逛微店】尊敬的顺逛微店用户,您购买的" + invoice.getProductCateName()
                    //                               + "的发.票已作废,原发.票税控码:" + invoice.getFiscalCode();
                    //                    content2 = "【顺逛微店】发.票详情:" + invoice.getEInvViewUrl();

                    content1 = "【顺逛微店】尊敬的顺逛微店用户,您购买的#productCateName#的发.票已作废,原发.票税控码:#fiscalCode#"
                        .replaceAll("#productCateName#", invoice.getProductCateName()).replaceAll(
                            "#fiscalCode#", invoice.getFiscalCode());
                    content2 = "【顺逛微店】发.票详情:#eInvViewUrl#".replaceAll("#eInvViewUrl#",
                        invoice.getEInvViewUrl());
                } else {
                    //                    content1 = "【海尔商城】尊敬的海尔商城用户,您购买的" + invoice.getProductCateName()
                    //                               + "的发.票已作废,原发.票税控码:" + invoice.getFiscalCode();
                    //                    content2 = "【海尔商城】发.票详情:" + invoice.getEInvViewUrl();

                    content1 = "【海尔商城】尊敬的海尔商城用户,您购买的#productCateName#的发.票已作废,原发.票税控码:#fiscalCode#"
                        .replaceAll("#productCateName#", invoice.getProductCateName()).replaceAll(
                            "#fiscalCode#", invoice.getFiscalCode());
                    content2 = "【海尔商城】发.票详情:#eInvViewUrl#".replaceAll("#eInvViewUrl#",
                        invoice.getEInvViewUrl());
                }
            } else {
                //                content1 = "尊敬的海尔商城用户,您购买的" + invoice.getProductCateName() + "的发.票已生效,税控码:"
                //                           + invoice.getFiscalCode();
                if (orderSource != null && orderSource.equalsIgnoreCase("MSTORE")) {
                    //                    content1 = "【顺逛微店】尊敬的顺逛微店用户,您购买的海尔产品电子发.票已开出，税控码："
                    //                               + invoice.getFiscalCode()
                    //                               + "，发.票抬头："
                    //                               + invoice.getInvoiceTitle()
                    //                               + "，详询网站客服或登录chinaeinv.com查询打印。电子发.票是国家税务机关认可的可作为保修和报销的凭证，请勿泄露发.票信息，以免权益受到损害。【海尔电子发.票平台】";
                    //                    content2 = "【顺逛微店】发.票详情:" + invoice.getEInvViewUrl();

                    content1 = "【顺逛微店】尊敬的顺逛微店用户,您购买的海尔产品电子发.票已开出，税控码：#fiscalCode#，发.票抬头：#invoiceTitle#，详询网站客服或登录chinaeinv.com查询打印。电子发.票是国家税务机关认可的可作为保修和报销的凭证，请勿泄露发.票信息，以免权益受到损害。【海尔电子发.票平台】"
                        .replaceAll("#fiscalCode#", invoice.getFiscalCode()).replaceAll(
                            "#invoiceTitle#", invoice.getInvoiceTitle());
                    content2 = "【顺逛微店】发.票详情:#eInvViewUrl#".replaceAll("#eInvViewUrl#",
                        invoice.getEInvViewUrl());
                } else {
                    //                    content1 = "【海尔商城】尊敬的海尔商城用户,您购买的海尔产品电子发.票已开出，税控码："
                    //                               + invoice.getFiscalCode()
                    //                               + "，发.票抬头："
                    //                               + invoice.getInvoiceTitle()
                    //                               + "，详询网站客服或登录chinaeinv.com查询打印。电子发.票是国家税务机关认可的可作为保修和报销的凭证，请勿泄露发.票信息，以免权益受到损害。【海尔电子发.票平台】";
                    //                    content2 = "【海尔商城】发.票详情:" + invoice.getEInvViewUrl();

                    content1 = "【海尔商城】尊敬的海尔商城用户,您购买的海尔产品电子发.票已开出，税控码：#fiscalCode#，发.票抬头：#invoiceTitle#，详询网站客服或登录chinaeinv.com查询打印。电子发.票是国家税务机关认可的可作为保修和报销的凭证，请勿泄露发.票信息，以免权益受到损害。【海尔电子发.票平台】"
                        .replaceAll("#fiscalCode#", invoice.getFiscalCode()).replaceAll(
                            "#invoiceTitle#", invoice.getInvoiceTitle());
                    content2 = "【海尔商城】发.票详情:#eInvViewUrl#".replaceAll("#eInvViewUrl#",
                        invoice.getEInvViewUrl());
                }
            }
            InvoiceElectricLogs ielog = invoiceElectricLogsDao.getByInvoiceIdAndType(
                invoice.getId(), type);
            if (ielog != null) {
                if (ielog.getSmsFlag().equals(0) && userMobile.length() == 11
                    && StrTools.isMobile(userMobile)) {
                    sendSms(userMobile, content1, "电子发票通知", invoice, orderId);
                    sendSms(userMobile, content2, "电子发票通知", invoice, orderId);
                    ielog.setSmsFlag(1);
                    invoiceElectricLogsDao.update(ielog);
                }
            }
        }
        return true;
    }

    /**
     * 更新电子发票开票信息
     *
     * @return
     */
    private boolean eInvoiceUpdate(Invoices invoice, Integer type, Map<String, String> attMap) {
        boolean sourceSMSFlag = true;//根据订单来源判断是否发短信
        String noSmsSourceCode = "BLPHH";//不发短信订单来源
        if (type == null) {
            invoicesDao.update(invoice);
            return false;
        }

        if (attMap == null) {
            invoicesDao.update(invoice);
            return false;
        }
        //并发时，过滤同步操作数据库
        if (invoice.getSuccess().equals(1)) {
            log.error("更新电子发票开票信息时：发票已经被其他进程执行完成，发票id：" + invoice.getId());
            return true;
        }
        String userMobile = "";
        Integer orderId = 0;
        int statusType = 1;//发票操作类型，1开票
        String orderSource = "";//订单来源，微店订单发生短信特殊处理
        switch (type) {
            case 1://开票
                invoice.setState(InvoiceConst.COMPLETE_STATE.intValue());
                invoice.setSuccess(1);
                invoice.setEaiWriteState(InvoiceConst.NORMAL_KP_STATE);
                invoice.setDrawer("CBS");
                invoice.setBillingTime(Long.parseLong(attMap.get("generateTime")));
                invoice.setInvoiceNumber(attMap.get("code"));
                invoice.setEInvViewUrl(attMap.get("viewUrl"));

                String fiscalCode = "";
                if (attMap.get("fiscalCode") != null && !attMap.get("fiscalCode").equals("")) {
                    fiscalCode = attMap.get("fiscalCode");
                } else {
                    fiscalCode = attMap.get("viewUrl").substring(
                        attMap.get("viewUrl").indexOf("=") + 1);
                }
                invoice.setFiscalCode(fiscalCode);

                invoice.setCheckCode(attMap.get("checkCode"));//校验码（新增返回字段信息，为20位）

                statusType = 1;//开票
                if (invoice.getCOrderType().equals(InvoiceConst.COMMON_CORDER_TYPE)) {//普通网单
                    OrderProductsNew op = orderProductsDao.get(invoice.getOrderProductId());
                    if (op != null) {
                        op.setReceiptNum(invoice.getInvoiceNumber());
                        op.setIsMakeReceipt(InvoiceConst.MR_STATE_MAKED);//// 已开票
                        op.setReceiptAddTime(DateFormatUtil.formatTime("yyyyMMdd",
                            invoice.getBillingTime()));
                        op.setSystemRemark(op.getSystemRemark() + "发票状态变更为已开票,isMakeReceipt:{"
                                           + op.getIsMakeReceipt() + "},receiptNum:{"
                                           + op.getReceiptNum() + "},receiptAddTime:{"
                                           + op.getReceiptAddTime() + "};");
                        orderProductsDao.update(op);

                        OrdersNew order = ordersDao.get(op.getOrderId());
                        orderSource = order.getSource();
                        if (noSmsSourceCode.equalsIgnoreCase(order.getSource())) {
                            sourceSMSFlag = false;
                        }
                        orderId = Integer.parseInt(order.getId());
                        //记录开票操作日志
                        OrderOperateLogs log = this.AssemblyOrderOperateLog(order, op,
                            "发票状态变更为:“已开票”。税控码：" + fiscalCode, "发票状态更改", "电子发票系统");
                        orderOperateLogsDao.insert(log);

                        InvoiceElectricSyncLogs invoiceElectricSyncLogs = AssemblyInvoiceElectricSyncLogs(
                            order, op, invoice, attMap);

                        List<InvoiceElectricSyncLogs> iesllist = invoiceElectricSyncLogsDao
                            .getByCOrderSnAngStatusType(invoice.getCOrderSn(), statusType);
                        if (iesllist == null || iesllist.size() < 1) {
                            invoiceElectricSyncLogsDao.insert(invoiceElectricSyncLogs);
                        }

                        //创建发票成功后同步HP队列表插入数据，单独运行，表`invoice_electric_2_out`,另外写job读取该表数据执行上面的电子发票信息传HP功能
                        insertInvoiceElectric2Out(invoice.getId(), "HP");

                        userMobile = order.getMobile() == null || order.getMobile().equals("") ? order
                            .getPhone() : order.getMobile();
                    }
                } else if (invoice.getCOrderType().equals(InvoiceConst.DIFF_CORDER_TYPE)) {//差异网单
                    Order2ths order2ths = order2thsDao.get(invoice.getDiffId());
                    if (order2ths != null) {
                        orderId = order2ths.getId();
                        order2ths.setReceiptNumber(invoice.getInvoiceNumber());
                        order2ths.setJSDate(DateFormatUtil.formatTime("yyyyMMdd",
                            invoice.getBillingTime()));
                        order2ths.setIsReceipted(InvoiceConst.R_RECEIPTED);
                        order2thsDao.update(order2ths);
                    }
                    // 同步向SAP传递开票信息
                    // 差异订单开票前需要先将订单信息传SAP
                    //                    Order2ths::sendSAP($invoice->diffId);
                    //向SAP传递开票信息先插入表`invoice_electric_2_out`,sendType=SAP，另外写job读取该表数据执行传SAP功能
                    insertInvoiceElectric2Out(invoice.getId(), "SAP");

                    userMobile = order2ths.getMobile1();
                } else {
                    Order4Invoices order4Invoice = order4InvoicesDao.get(invoice.getDiffId());
                    if (order4Invoice != null) {
                        orderId = order4Invoice.getId();
                        order4Invoice.setReceiptNumber(invoice.getInvoiceNumber());
                        order4Invoice.setJSDate(DateFormatUtil.formatTime("yyyyMMdd",
                            invoice.getBillingTime()));
                        order4Invoice.setIsReceipted(InvoiceConst.R_RECEIPTED);
                        order4InvoicesDao.update(order4Invoice);
                    }
                    userMobile = order4Invoice.getMobile1();
                }

                invoicesDao.update(invoice);

                MemberInvoices mInvoices = memberInvoicesDao.getByOrderId(orderId);
                if (mInvoices != null) {
                    mInvoices.setIsLock(1);//开票成功后设置锁，不让修改信息
                    memberInvoicesDao.update(mInvoices);
                }

                InvoiceSAPLogs invoiceSAPLogs = OrderBizHelper.sapGenerate(invoiceSAPLogsDao,
                    orderProductsDao, invoice, 0, this.getPathByName("/upfiles/sapblacklist.txt"));
                boolean sendSap = false;
                if (invoiceSAPLogs != null) {
                    int n = invoiceSAPLogsDao.insert(invoiceSAPLogs);
                    if (n < 1) {
                        log.error("插入invoiceSAPLogs信息失败，发票id:" + invoice.getId());
                    } else {
                        sendSap = true;
                    }
                }
                if (!sendSap) {
                    log.error("开票时，sapGenerate更新电子发票，未推送Sap开票信息，发票id:" + invoice.getId());
                }
                break;
            case 2://作废
                statusType = 2;//红冲
                if (attMap.get("status").equals("3") || attMap.get("status").equals("4")) {//原php invalidateType=1   新java  status=3冲红     status=2作废   status=1开票   status=4被红冲
                    // 红冲
                    invoice.setInvalidTime(Long.parseLong(attMap.get("validTime")));
                    invoice.setEaiWriteState(InvoiceConst.MONTH_INVALID_KP_STATE);
                    if (invoice.getCOrderType().equals(InvoiceConst.COMMON_CORDER_TYPE)) {
                        OrderProductsNew op = orderProductsDao.get(invoice.getOrderProductId());
                        if (op != null) {
                            op.setIsMakeReceipt(InvoiceConst.MR_STATE_RED);
                            orderProductsDao.update(op);

                            OrdersNew order = ordersDao.get(op.getOrderId());
                            orderSource = order.getSource();
                            if (noSmsSourceCode.equalsIgnoreCase(order.getSource())) {
                                sourceSMSFlag = false;
                            }
                            orderId = Integer.parseInt(order.getId());
                            //记录开票操作日志
                            OrderOperateLogs log = this.AssemblyOrderOperateLog(order, op,
                                "发票状态变更为“跨月冲红”", "发票状态更改", "电子发票系统");
                            orderOperateLogsDao.insert(log);

                            //红冲时也写入队列  2015-06-29添加 wyj
                            InvoiceElectricSyncLogs invoiceElectricSyncLogs = AssemblyInvoiceElectricSyncLogs(
                                order, op, invoice, attMap);

                            List<InvoiceElectricSyncLogs> iesllist = invoiceElectricSyncLogsDao
                                .getByCOrderSnAngStatusType(invoice.getCOrderSn(), statusType);
                            if (iesllist == null || iesllist.size() < 1) {
                                invoiceElectricSyncLogsDao.insert(invoiceElectricSyncLogs);
                            }

                            userMobile = order.getMobile();
                        }
                    } else if (invoice.getCOrderType().equals(InvoiceConst.DIFF_CORDER_TYPE)) {
                        Order2ths order2th = order2thsDao.get(invoice.getDiffId());
                        orderId = order2th.getId();
                        userMobile = order2th.getMobile1();
                    } else {
                        Order4Invoices order4Invoice = order4InvoicesDao.get(invoice.getDiffId());
                        orderId = order4Invoice.getId();
                        userMobile = order4Invoice.getMobile1();
                    }
                } else if (attMap.get("status").equals("2")) {
                    // 作废情况已经不存在了，都是红冲，所以没有2的情况
                    invoice.setInvalidTime(Long.parseLong(attMap.get("validTime")));
                    invoice.setEaiWriteState(InvoiceConst.INVALID_KP_STATE);
                    if (invoice.getCOrderType().equals(InvoiceConst.COMMON_CORDER_TYPE)) {
                        OrderProductsNew op = orderProductsDao.get(invoice.getOrderProductId());
                        if (op != null) {
                            op.setIsMakeReceipt(InvoiceConst.MR_STATE_NULLIFY);
                            orderProductsDao.update(op);

                            OrdersNew order = ordersDao.get(op.getOrderId());
                            orderSource = order.getSource();
                            if (noSmsSourceCode.equalsIgnoreCase(order.getSource())) {
                                sourceSMSFlag = false;
                            }
                            orderId = Integer.parseInt(order.getId());
                            //记录开票操作日志
                            OrderOperateLogs log = this.AssemblyOrderOperateLog(order, op,
                                "发票状态变更为“当月作废”", "发票状态更改", "电子发票系统");
                            orderOperateLogsDao.insert(log);

                            //红冲时也写入队列  2015-06-29添加 wyj
                            InvoiceElectricSyncLogs invoiceElectricSyncLogs = AssemblyInvoiceElectricSyncLogs(
                                order, op, invoice, attMap);

                            List<InvoiceElectricSyncLogs> iesllist = invoiceElectricSyncLogsDao
                                .getByCOrderSnAngStatusType(invoice.getCOrderSn(), statusType);
                            if (iesllist == null || iesllist.size() < 1) {
                                invoiceElectricSyncLogsDao.insert(invoiceElectricSyncLogs);
                            }

                            userMobile = order.getMobile();
                        }
                    } else if (invoice.getCOrderType().equals(InvoiceConst.DIFF_CORDER_TYPE)) {
                        Order2ths order2th = order2thsDao.get(invoice.getDiffId());
                        orderId = order2th.getId();
                        userMobile = order2th.getMobile1();
                    } else {
                        Order4Invoices order4Invoice = order4InvoicesDao.get(invoice.getDiffId());
                        orderId = order4Invoice.getId();
                        userMobile = order4Invoice.getMobile1();
                    }
                }
                //作废发票,保留原发票信息
                //invoice.setInvoiceNumber(attMap.get("code"));
                //invoice.setEInvViewUrl(attMap.get("viewUrl"));

                //fiscalCode = "";
                //if (attMap.get("fiscalCode") != null && !attMap.get("fiscalCode").equals("")) {
                //    fiscalCode = attMap.get("fiscalCode");
                //} else {
                //    fiscalCode = attMap.get("viewUrl").substring(
                //        attMap.get("viewUrl").indexOf("=") + 1);
                //}
                //invoice.setFiscalCode(fiscalCode);
                //invoice.setCheckCode(attMap.get("checkCode"));//校验码（新增返回字段信息，为20位）
                invoice.setSuccess(1);
                invoicesDao.update(invoice);

                invoiceSAPLogs = OrderBizHelper.sapGenerate(invoiceSAPLogsDao, orderProductsDao,
                    invoice, 0, this.getPathByName("/upfiles/sapblacklist.txt"));
                sendSap = false;
                if (invoiceSAPLogs != null) {
                    int n = invoiceSAPLogsDao.insert(invoiceSAPLogs);
                    if (n < 1) {
                        log.error("插入invoiceSAPLogs信息失败，发票id:" + invoice.getId());
                    } else {
                        sendSap = true;
                    }
                }
                if (!sendSap) {
                    log.error("开票时，sapGenerate更新电子发票，未推送Sap开票信息，发票id:" + invoice.getId());
                }

                /*boolean flag2 = OrderBizHelper.sapGenerate(invoiceSAPLogsDao, orderProductsDao,
                    invoice, 0, this.getPathByName("/upfiles/sapblacklist.txt"));
                if (!flag2) {
                    log.error("发票作废时，sapGenerate更新电子发票，未推送Sap作废信息，发票id:" + invoice.getId());
                }*/
                break;
            default:
                break;
        }
        // 电子发票发短信-商城发短信
        if (!InvoiceConst.SMSFLAG && sourceSMSFlag) {
            String content1 = "";
            String content2 = "";
            if (InvoiceConst.TYPE_INVALID.equals(type)) {
                if (orderSource != null && orderSource.equalsIgnoreCase("MSTORE")) {
                    //                    content1 = "【顺逛微店】尊敬的顺逛微店用户,您购买的" + invoice.getProductCateName()
                    //                               + "的发.票已作废,原发.票税控码:" + invoice.getFiscalCode();
                    //                    content2 = "【顺逛微店】发.票详情:" + invoice.getEInvViewUrl();

                    content1 = SmsTemplateConst.Invoice_MSTORE_Invalid_1_Notice
                        .replaceAll("#productCateName#", invoice.getProductCateName())
                        .replaceAll("#invoiceNumberF12#",
                            invoice.getInvoiceNumber().trim().substring(0, 12))
                        .replaceAll("#invoiceNumberB8#",
                            invoice.getInvoiceNumber().trim().substring(12, 20))
                        .replaceAll("#checkCode#", invoice.getCheckCode());
                    content2 = SmsTemplateConst.Invoice_MSTORE_Invalid_1_Detail.replaceAll(
                        "#eInvViewUrl#", invoice.getEInvViewUrl());
                } else {
                    //                    content1 = "【海尔商城】尊敬的海尔商城用户,您购买的" + invoice.getProductCateName()
                    //                               + "的发.票已作废,原发.票税控码:" + invoice.getFiscalCode();
                    //                    content2 = "【海尔商城】发.票详情:" + invoice.getEInvViewUrl();

                    content1 = SmsTemplateConst.Invoice_HESC_Invalid_1_Notice
                        .replaceAll("#productCateName#", invoice.getProductCateName())
                        .replaceAll("#invoiceNumberF12#",
                            invoice.getInvoiceNumber().trim().substring(0, 12))
                        .replaceAll("#invoiceNumberB8#",
                            invoice.getInvoiceNumber().trim().substring(12, 20))
                        .replaceAll("#checkCode#", invoice.getCheckCode());
                    content2 = SmsTemplateConst.Invoice_HESC_Invalid_1_Detail.replaceAll(
                        "#eInvViewUrl#", invoice.getEInvViewUrl());
                }
            } else {
                //                content1 = "尊敬的海尔商城用户,您购买的" + invoice.getProductCateName() + "的发.票已生效,税控码:"
                //                           + invoice.getFiscalCode();
                if (orderSource != null && orderSource.equalsIgnoreCase("MSTORE")) {
                    //                    content1 = "【顺逛微店】尊敬的顺逛微店用户,您购买的海尔产品电子发.票已开出，税控码："
                    //                               + invoice.getFiscalCode()
                    //                               + "，发.票抬头："
                    //                               + invoice.getInvoiceTitle()
                    //                               + "，详询网站客服或登录chinaeinv.com查询打印。电子发.票是国家税务机关认可的可作为保修和报销的凭证，请勿泄露发.票信息，以免权益受到损害。【海尔电子发.票平台】";
                    //                    content2 = "【顺逛微店】发.票详情:" + invoice.getEInvViewUrl();

                    content1 = SmsTemplateConst.Invoice_MSTORE_Generate_1_Notice.replaceAll(
                        "#invoiceTitle#", invoice.getInvoiceTitle());
                    content2 = SmsTemplateConst.Invoice_MSTORE_Generate_1_Detail
                        .replaceAll("#eInvViewUrl#", invoice.getEInvViewUrl())
                        .replaceAll("#invoiceNumberF12#",
                            invoice.getInvoiceNumber().trim().substring(0, 12))
                        .replaceAll("#invoiceNumberB8#",
                            invoice.getInvoiceNumber().trim().substring(12, 20))
                        .replaceAll("#checkCode#", invoice.getCheckCode());
                } else {
                    //                    content1 = "【海尔商城】尊敬的海尔商城用户,您购买的海尔产品电子发.票已开出，税控码："
                    //                               + invoice.getFiscalCode()
                    //                               + "，发.票抬头："
                    //                               + invoice.getInvoiceTitle()
                    //                               + "，详询网站客服或登录chinaeinv.com查询打印。电子发.票是国家税务机关认可的可作为保修和报销的凭证，请勿泄露发.票信息，以免权益受到损害。【海尔电子发.票平台】";
                    //                    content2 = "【海尔商城】发.票详情:" + invoice.getEInvViewUrl();

                    content1 = SmsTemplateConst.Invoice_HESC_Generate_1_Notice.replaceAll(
                        "#invoiceTitle#", invoice.getInvoiceTitle());
                    content2 = SmsTemplateConst.Invoice_HESC_Generate_1_Detail
                        .replaceAll("#eInvViewUrl#", invoice.getEInvViewUrl())
                        .replaceAll("#invoiceNumberF12#",
                            invoice.getInvoiceNumber().trim().substring(0, 12))
                        .replaceAll("#invoiceNumberB8#",
                            invoice.getInvoiceNumber().trim().substring(12, 20))
                        .replaceAll("#checkCode#", invoice.getCheckCode());
                }
            }
            InvoiceElectricLogs ielog = invoiceElectricLogsDao.getByInvoiceIdAndType(
                invoice.getId(), type);
            if (ielog != null) {
                if (ielog.getSmsFlag().equals(0) && userMobile.length() == 11
                    && StrTools.isMobile(userMobile)) {
                    sendSms(userMobile, content1, "电子发票通知", invoice, orderId);
                    sendSms(userMobile, content2, "电子发票通知", invoice, orderId);
                    ielog.setSmsFlag(1);
                    invoiceElectricLogsDao.update(ielog);
                }
            }
        }
        return true;
    }

    private String getPathByName(String name) {
        return this.getClass().getResource(name).getPath();
    }

    /**
     * 修改退换货申请的发票状态
     * param orderProductId 网单id
     * @return
     */
    private boolean orderRepairsCancelInvoice(Integer orderProductId) {
        if (orderProductId == null || orderProductId.equals(0)) {
            log.error("修改退换货申请的发票状态：网单id为空或为0");
            return false;
        }
        OrderProductsNew orderProduct = orderProductsDao.get(orderProductId);
        if (orderProduct == null) {
            log.error("修改退换货申请的发票状态：网单不存在");
            return false;
        }
        List<OrderRepairsNew> orderRepairsList = orderRepairsDao.getByOrderProductId(orderProductId);
        if (orderRepairsList == null || orderRepairsList.size() == 0) {
            log.error("修改退换货申请的发票状态：没有找到对应的退换货列表");
            return false;
        }
        for (int i = 0; i < orderRepairsList.size(); i++) {
            if (InvoiceConst.RS_CANCELED.equals(orderRepairsList.get(i).getReceiptStatus())) {
                return false;
            }
            orderRepairsList.get(i).setReceiptStatus(InvoiceConst.RS_CANCELED);
            orderRepairsList.get(i).setReceiptTime((int) (new Date().getTime() / 1000));
            orderRepairsDao.update(orderRepairsList.get(i));

            //插入退换货日志
            OrderRepairLogsNew orderRepairLogs = new OrderRepairLogsNew();
            orderRepairLogs.setSiteId(1);
            orderRepairLogs.setAddTime((int) (new Date().getTime() / 1000));
            orderRepairLogs.setOrderRepairId(orderRepairsList.get(i).getId());
            orderRepairLogs.setOperator("系统");
            orderRepairLogs.setOperate("rs_cancel");
            orderRepairLogs.setRemark("同步发票状态到"
                                      + InvoiceConst.RECEIPT_STATUS.get(InvoiceConst.RS_CANCELED));
            orderRepairLogsDao.insert(orderRepairLogs);
        }
        return true;
    }

    /**
     * 组装 发票参数
     * @param invoice
     * @return
     */
    private InvoiceEntity AssemblyInvoicesEntityParam(Invoices invoice) {
        InvoiceEntity ie = new InvoiceEntity();
        ie.setWdh(invoice.getCOrderSn());
        ie.setKhbm(invoice.getCustomerCode());
        ie.setKhmc(invoice.getInvoiceTitle());
        ie.setKhsh(invoice.getTaxPayerNumber() == null ? invoice.getTaxPayerNumber() : invoice
            .getTaxPayerNumber().replace(" ", ""));
        ie.setKhdz(invoice.getRegisterAddressAndPhone());
        ie.setKhkhyhzh(invoice.getBankNameAndAccount() == null ? invoice.getBankNameAndAccount()
            : invoice.getBankNameAndAccount().replace(" ", ""));
        ie.setBz(invoice.getRemark() + returnSuffix(invoice.getOrderProductId()));
        ie.setWdrq(DateFormatUtil.parse(DateFormatUtil.formatTime(invoice.getCOrderAddTime())));
        ie.setCpdm(invoice.getSku());
        ie.setCpmc(invoice.getProductName());
        ie.setCpxh(invoice.getProductCateName());
        ie.setCpdw(invoice.getUnit());
        ie.setCpsl(new BigDecimal(invoice.getNumber()));
        ie.setHsdj(invoice.getPrice());
        ie.setHsje(invoice.getAmount());
        ie.setBhsdj(invoice.getNonTaxPrice());
        ie.setBhsje(invoice.getNonTaxAmount());
        ie.setJsje(invoice.getTaxAmount());
        ie.setJssl(invoice.getTaxRate());
        ie.setJfje(invoice.getIntegralAmount());
        ie.setJlje(invoice.getEnergySavingAmount());
        ie.setJlbz(invoice.getEnergySavingRemark());
        ie.setFplx((int) (invoice.getType() != null && invoice.getType().equals(3) ? 2 : invoice.getType()));
        ie.setFpzt(1);
        ie.setSkfs(invoice.getPaymentName());
        ie.setLbjsdh(invoice.getInternalSettlement());
        ie.setKwbm(invoice.getSCode());
        ie.setHptx(invoice.getIsTogether().intValue());
        ie.setDdlx(invoice.getOrderType());
        ie.setFgsno(invoice.getBranchOfficeCode());
        ie.setDdhno(invoice.getOrderLineNumber());
        ie.setWlno(invoice.getLessOrderSn());
        ie.setAdd1(invoice.getBackupFieldA());
        ie.setAdd2(invoice.getBackupFieldB());
        ie.setRrrq(DateFormatUtil.parse(DateFormatUtil.formatTime(invoice.getAddTime())));
        ie.setGxrq(DateFormatUtil.parse(DateFormatUtil.formatTime(invoice.getLastModifyTime() == null
                                                                  || invoice.getLastModifyTime()
                                                                      .equals(0) ? (int) (new Date()
            .getTime() / 1000) : invoice.getLastModifyTime())));
        ie.setFphm("");
        ie.setKprq(null);
        ie.setSkrq(null);
        ie.setKpman("");
        ie.setKpzt(null);
        ie.setZfrq(null);
        return ie;
    }

    /**
     * 组装 电子发票同步日志
     * @return
     */
    private InvoiceElectricSyncLogs AssemblyInvoiceElectricSyncLogs(OrdersNew order, OrderProductsNew op,
                                                                    Invoices invoice,
                                                                    Map<String, String> attMap) {
        InvoiceElectricSyncLogs log = new InvoiceElectricSyncLogs();
        log.setSiteId(1);
        log.setAddTime((int) (new Date().getTime() / 1000));
        log.setOrderId(op.getOrderId());
        log.setOrderProductId(op.getId());
        log.setCOrderSn(op.getCOrderSn());
        log.setOrderSource(order.getSource());
        log.setPushData("");
        log.setReturnData("");
        log.setCount(0);
        log.setSuccess(0);
        log.setSuccessTime(0);
        log.setLastMessage("");

        String invoiceNumber = "";
        String fiscalCode = "";
        String viewUrl = "";
        Integer statusType = 1;
        String pdfUrl = "";
        String downloadUrl = "";
        Long operateTime = 0l;
        BigDecimal amount = BigDecimal.ZERO;

        if (attMap.get("code") != null && !attMap.get("code").equals("")) {
            invoiceNumber = attMap.get("code");
        } else {
            invoiceNumber = "";
        }
        if (attMap.get("fiscalCode") != null && !attMap.get("fiscalCode").equals("")) {
            fiscalCode = attMap.get("fiscalCode");
        } else {
            fiscalCode = "";
        }
        if (attMap.get("viewUrl") != null && !attMap.get("viewUrl").equals("")) {
            viewUrl = attMap.get("viewUrl");
        } else {
            viewUrl = "";
        }
        if (attMap.get("status").equals("1")) {
            statusType = 1;
        } else {
            statusType = 2;
        }
        if (attMap.get("pdfUnsignedUrl") != null && !attMap.get("pdfUnsignedUrl").equals("")) {
            pdfUrl = attMap.get("pdfUnsignedUrl");
        } else {
            pdfUrl = "";
        }
        if (attMap.get("downloadUrl") != null && !attMap.get("downloadUrl").equals("")) {
            downloadUrl = attMap.get("downloadUrl");
        } else {
            downloadUrl = "";
        }
        if (statusType.equals(1)) {
            if (attMap.get("generateTime") != null && !attMap.get("generateTime").equals("")) {
                operateTime = Long.parseLong(attMap.get("generateTime"));
            } else {
                operateTime = 0l;
            }
        } else {
            if (attMap.get("validTime") != null && !attMap.get("validTime").equals("")) {
                operateTime = Long.parseLong(attMap.get("validTime"));
            } else {
                operateTime = 0l;
            }
        }
        if (attMap.get("totalAmount") != null && !attMap.get("totalAmount").equals("")) {
            amount = BigDecimal.valueOf(Double.parseDouble(attMap.get("totalAmount")));
        } else {
            amount = BigDecimal.ZERO;
        }
        log.setInvoiceNumber(invoiceNumber);
        log.setFiscalCode(fiscalCode);
        log.setViewUrl(viewUrl);
        log.setStatusType(statusType);
        log.setPdfUrl(pdfUrl);
        log.setDownloadUrl(downloadUrl);
        log.setOperateTime(operateTime);
        log.setAmount(amount);
        return log;
    }

    /**
     * 返回备注中网单号的后缀
     *
     * @param
     *
     * @return string 'T' OR ''
     */
    public String returnSuffix(Integer opid) {
        if (opid == null || opid.equals(0)) {
            return "";
        }

        Map<String, String> allowSource = new HashMap<String, String>();
        allowSource.put("TBSC", "淘宝海尔官方旗舰店");//淘宝海尔官方旗舰店
        allowSource.put("TOPFENXIAO", "淘宝海尔官方旗舰店分销平台");//淘宝海尔官方旗舰店分销平台
        allowSource.put("TOPBUYBANG", "淘宝海尔官方旗舰店");//淘宝海尔官方旗舰店
        allowSource.put("TOPBOILER", "淘宝海尔热水器专卖店");//淘宝海尔热水器专卖店
        allowSource.put("TOPSHJD", "淘宝海尔生活电器专卖店");//淘宝海尔生活电器专卖店
        allowSource.put("TOPMOBILE", "淘宝海尔手机专卖店");//淘宝海尔手机专卖店
        allowSource.put("TOPPAD", "淘宝海尔PAD专卖店");//淘宝海尔PAD专卖店

        OrderProductsNew orderProducts = orderProductsDao.get(opid);
        if (orderProducts != null) {
            OrdersNew order = ordersDao.get(orderProducts.getOrderId());
            if (order != null && allowSource.containsKey(order.getSource())) {
                return "T";
            }
        }
        return "";
    }

    /**
     * 组装 操作日志
     * @param order
     * @param op
     * @param remark
     * @param changeLog
     * @return
     */
    private OrderOperateLogs AssemblyOrderOperateLog(OrdersNew order, OrderProductsNew op, String remark,
                                                     String changeLog) {
        OrderOperateLogs log = new OrderOperateLogs();
        log.setChangeLog(StringUtil.isEmpty(changeLog) ? "" : changeLog);
        log.setLogTime((int) (new Date().getTime() / 1000));
        log.setNetPointId(op.getNetPointId());
        log.setOperator("系统");
        log.setOrderId(op.getOrderId());
        log.setOrderProductId(op.getId());
        log.setPaymentStatus(order.getPaymentStatus());
        log.setRemark(StringUtil.isEmpty(remark) ? "" : remark);
        log.setSiteId(1);
        log.setStatus(op.getStatus());
        return log;
    }
    private OrderOperateLogs AssemblyOrderOperateLog(OrdersNew order, OrderProductsNew op, String remark,
                                                     String changeLog, String operator) {
        OrderOperateLogs log = new OrderOperateLogs();
        log.setChangeLog(StringUtil.isEmpty(changeLog) ? "" : changeLog);
        log.setLogTime((int) (new Date().getTime() / 1000));
        log.setNetPointId(op.getNetPointId());
        log.setOperator(StringUtil.isEmpty(operator) ? "系统" : operator);
        log.setOrderId(op.getOrderId());
        log.setOrderProductId(op.getId());
        log.setPaymentStatus(order.getPaymentStatus());
        log.setRemark(StringUtil.isEmpty(remark) ? "" : remark);
        log.setSiteId(1);
        log.setStatus(op.getStatus());
        return log;
    }

    private void insertOrderOperateLog(OrdersNew order, Invoices invoice, String remark,
                                       String changeLog, String operator) {
        OrderOperateLogs log = new OrderOperateLogs();
        log.setChangeLog(changeLog);//操作内容
        log.setLogTime((int) (new Date().getTime() / 1000));
        log.setNetPointId(0);
        log.setOperator(operator);//操作人
        log.setOrderId(Integer.parseInt(order.getId()));
        log.setOrderProductId(invoice.getOrderProductId());
        log.setPaymentStatus(0);
        log.setRemark(remark);//结果描述
        log.setSiteId(1);
        log.setStatus(0);
        orderOperateLogsDao.insert(log);
    }

    private void sendSms(String userMobile, String smsMsg, String name, Invoices invoice,
                         Integer orderId) {
        if (userMobile == null || userMobile.equals("") || userMobile.length() != 11
            || !StrTools.isMobile(userMobile)) {
            return;
        }
        userMobile = userMobile.replace(" ", "").replace("-", "");
        if (StringUtils.isBlank(smsMsg)) {
            return;
        }
        if (StringUtils.isBlank(name)) {
            name = userMobile;
        }
        SmsLogs smsLogs = new SmsLogs();
        Integer nowTime = Long.valueOf(System.currentTimeMillis() / 1000L).intValue();
        smsLogs.setMobile(userMobile);
        smsLogs.setName(name);
        smsLogs.setMessage(smsMsg);
        smsLogs.setAddTime(nowTime);
        smsLogs.setIsSuccess(0);//0-成功 1-失败
        smsLogs.setPriority(255);
        smsLogs.setLastTime((int) (new Date().getTime() / 1000));
        smsLogs.setTryNum(0);
        smsLogs.setSiteId(1);

        try {
            smsLogsWriteDao.insertSmsLogs(smsLogs);
        } catch (Exception e) {
            log.error("开票发短信失败，发票id:" + invoice.getId() + ",异常信息:", e);
            //记日志
            OrderOperateLogs log = new OrderOperateLogs();
            log.setChangeLog("开票发送短信通知");
            log.setLogTime((int) (new Date().getTime() / 1000));
            log.setNetPointId(0);
            log.setOperator("CBS开票系统");
            log.setOrderId(orderId);
            log.setOrderProductId(invoice.getOrderProductId());
            log.setPaymentStatus(0);
            log.setRemark("开发票发送短信通知用户失败");
            log.setSiteId(1);
            log.setStatus(0);
            orderOperateLogsDao.insert(log);
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
        List<InvoiceElectric2Out> ie2olist = invoiceElectric2OutDao
            .getByInvoiceIdAndSendType(invoiceElectric2Out);
        if (ie2olist == null || ie2olist.size() < 1) {
            invoiceElectric2OutDao.insert(invoiceElectric2Out);
        }
    }

    private class ExecuteCreateInvoice implements IExcute {
        public void execute(Object obj) {
            List<InvoiceQueue> list = (List<InvoiceQueue>) obj;
            try {
                createInvoiceThread(list);
            } catch (Exception e) {
                log.error("创建开发票队列,发生异常：", e);
            }
        }

    }

    private class ExecuteSyncToInvoiceSystem implements IExcute {

        @SuppressWarnings("unchecked")
        @Override
        public void execute(Object obj) {
            List<Invoices> list = (List<Invoices>) obj;
            try {
                syncToInvoiceSystemThread(list);
            } catch (Exception e) {
                log.error("同步发票到开票系统,发生异常：", e);
            }
        }

    }
    public String getCustomerCode(int productID) {
        List<Invoices> invoives = invoicesDao.getByOrderProductId(productID);
        if (invoives == null || invoives.isEmpty())
            return "A69";
        List<String> codes = customerCodeDao.getCustomerCode(invoives.get(0).getInvoiceTitle());
        if (codes == null || codes.isEmpty())
            return "A69";
        return codes.get(0);
    }

    public String saveMemberInvoices(Integer id, Integer invoiceType, String invoiceTitle,
                                     String taxPayerNumber, String registerAddress,
                                     String registerPhone, String bankName, String bankCardNumber,
                                     Integer state, String remark, String auditor) {
        try {
            // 根据Id获得发票信息
            MemberInvoices memberInvoices = this.memberInvoicesDao.get(id);
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
            List<OrderProductsNew> orderProductsList = this.orderProductsDao
                .getByOrderId(memberInvoices.getOrderId());
            // 获得所有的Id信息
            List<Integer> idsList = new ArrayList<Integer>();
            if (orderProductsList != null && orderProductsList.size() > 0) {
                for (OrderProductsNew eachOrderProducts : orderProductsList) {
                    idsList.add(eachOrderProducts.getId());
                }
            }
            // 根据ID信息取得OrderProducts信息
            if (idsList.size() > 0) {
                orderProductsList.clear();
                orderProductsList = this.orderProductsDao.getByIds(idsList);
                if (orderProductsList != null && orderProductsList.size() > 0) {
                    for (OrderProductsNew eachOrderProducts : orderProductsList) {
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

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus transtatus = transactionManager.getTransaction(def);
            try {
                // 增值税发票的场合
                if (paramMemberInvoices.getInvoiceType().equals(1)) {
                    // 将开票类型修改为“共享开票”
                    if (orderProductsList != null && orderProductsList.size() > 0) {
                        for (OrderProductsNew eachOrderProducts : orderProductsList) {
                            eachOrderProducts.setMakeReceiptType(2);
                            this.orderProductsDao.updateMakeReceiptType(eachOrderProducts);
                        }
                    }

                    // 增票不能是电子发票
                    paramMemberInvoices.setElectricFlag(0);

                    // 已存在的增票信息自动审核通过
                    Integer state_t = paramMemberInvoices.getState();
                    paramMemberInvoices.setState(1);
                    MemberInvoices queryMemberInvoices = this.memberInvoicesDao
                        .checkPassedValuedInvoice(paramMemberInvoices);

                    if (queryMemberInvoices != null) {
                        paramMemberInvoices.setState(1); // 审核通过
                        paramMemberInvoices.setAuditTime(new Date().getTime() / 1000);
                        paramMemberInvoices.setAuditor("系统");
                        paramMemberInvoices.setRemark("该增票信息先前已存在并审核通过,本订单增票信息自动审核通过");
                        paramMemberInvoices.setParentId(queryMemberInvoices.getId());
                        this.memberInvoicesDao.update(paramMemberInvoices);
                    } else {
                        paramMemberInvoices.setState(state_t);
                        this.memberInvoicesDao.update(paramMemberInvoices);
                    }
                } else {
                    this.memberInvoicesDao.update(paramMemberInvoices);
                }
                //插入日志
                InvoiceChangeLogs invoiceChangeLogs = new InvoiceChangeLogs();
                invoiceChangeLogs.setAddTime(new Date().getTime() / 1000);
                invoiceChangeLogs.setInvoiceId(memberInvoices.getId());
                invoiceChangeLogs.setOperator(auditor);
                if (!oldmemberInvoices.getInvoiceTitle().equals(
                    paramMemberInvoices.getInvoiceTitle())) {
                    invoiceChangeLogs.setRemark("发票抬头：由“" + oldmemberInvoices.getInvoiceTitle()
                                                + "”变更为“" + paramMemberInvoices.getInvoiceTitle()
                                                + "”");
                    invoiceChangeLogsDao.insert(invoiceChangeLogs);
                }
                if (!oldmemberInvoices.getInvoiceType()
                    .equals(paramMemberInvoices.getInvoiceType())) {
                    invoiceChangeLogs
                        .setRemark("发票类型：由“"
                                   + (oldmemberInvoices.getInvoiceType().equals(1) ? "增值税发票"
                                       : "普通发票")
                                   + "”变更为“"
                                   + (paramMemberInvoices.getInvoiceType().equals(1) ? "增值税发票"
                                       : "普通发票") + "”");
                    invoiceChangeLogsDao.insert(invoiceChangeLogs);
                }
                if (!oldmemberInvoices.getTaxPayerNumber().equals(
                    paramMemberInvoices.getTaxPayerNumber())) {
                    invoiceChangeLogs.setRemark("纳税人识别号：由“" + oldmemberInvoices.getTaxPayerNumber()
                                                + "”变更为“" + paramMemberInvoices.getTaxPayerNumber()
                                                + "”");
                    invoiceChangeLogsDao.insert(invoiceChangeLogs);
                }
                if (!oldmemberInvoices.getRegisterAddress().equals(
                    paramMemberInvoices.getRegisterAddress())) {
                    invoiceChangeLogs.setRemark("注册地址：由“" + oldmemberInvoices.getRegisterAddress()
                                                + "”变更为“"
                                                + paramMemberInvoices.getRegisterAddress() + "”");
                    invoiceChangeLogsDao.insert(invoiceChangeLogs);
                }
                if (!oldmemberInvoices.getRegisterPhone().equals(
                    paramMemberInvoices.getRegisterPhone())) {
                    invoiceChangeLogs.setRemark("注册电话：由“" + oldmemberInvoices.getRegisterPhone()
                                                + "”变更为“" + paramMemberInvoices.getRegisterPhone()
                                                + "”");
                    invoiceChangeLogsDao.insert(invoiceChangeLogs);
                }
                if (!oldmemberInvoices.getBankName().equals(paramMemberInvoices.getBankName())) {
                    invoiceChangeLogs
                        .setRemark("开户银行：由“" + oldmemberInvoices.getBankName() + "”变更为“"
                                   + paramMemberInvoices.getBankName() + "”");
                    invoiceChangeLogsDao.insert(invoiceChangeLogs);
                }
                if (!oldmemberInvoices.getBankCardNumber().equals(
                    paramMemberInvoices.getBankCardNumber())) {
                    invoiceChangeLogs.setRemark("开户卡号：由“" + oldmemberInvoices.getBankCardNumber()
                                                + "”变更为“" + paramMemberInvoices.getBankCardNumber()
                                                + "”");
                    invoiceChangeLogsDao.insert(invoiceChangeLogs);
                }
                if (!oldmemberInvoices.getState().equals(paramMemberInvoices.getState())) {
                    invoiceChangeLogs.setRemark("审核状态：由“" + oldmemberInvoices.getState() + "”变更为“"
                                                + paramMemberInvoices.getState() + "”");
                    invoiceChangeLogs.setRemark(invoiceChangeLogs.getRemark().replace("0", "待审核")
                        .replace("1", "审核通过").replace("2", "拒绝"));
                    invoiceChangeLogsDao.insert(invoiceChangeLogs);
                }
                if (!oldmemberInvoices.getRemark().equals(paramMemberInvoices.getRemark())) {
                    invoiceChangeLogs.setRemark("备注信息：由“" + oldmemberInvoices.getRemark() + "”变更为“"
                                                + paramMemberInvoices.getRemark() + "”");
                    invoiceChangeLogsDao.insert(invoiceChangeLogs);
                }
                // 修改发票表信息
                //
                // 提交事务
                transactionManager.commit(transtatus);
                return "";
            } catch (Exception e) {// 回滚事务
                transactionManager.rollback(transtatus);
                log.error("保存发票信息发生异常，回滚事务：", e);
                return "保存发票信息发生异常:" + e.getMessage();
            }
        } catch (Exception e) {
            log.error("保存发票信息发生异常：", e);
            return "保存发票信息发生异常:" + e.getMessage();
        }
    }

    public String unlockMemberInvoices(Integer id, String userName) {
        try {
            // 根据Id获得发票信息
            MemberInvoices memberInvoices = this.memberInvoicesDao.get(id);
            if (memberInvoices == null) {
                return "对不起，该发票信息不存在!";
            }

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus transtatus = transactionManager.getTransaction(def);
            try {
                memberInvoices.setId(id);
                memberInvoices.setIsLock(0);//待锁定
                memberInvoicesDao.update(memberInvoices);

                InvoiceChangeLogs invoiceChangeLogs = new InvoiceChangeLogs();
                invoiceChangeLogs.setAddTime(new Date().getTime() / 1000);
                invoiceChangeLogs.setInvoiceId(memberInvoices.getId());
                invoiceChangeLogs.setOperator(userName);
                invoiceChangeLogs.setRemark("手动解除发票信息锁定");
                invoiceChangeLogsDao.insert(invoiceChangeLogs);

                // 提交事务
                transactionManager.commit(transtatus);
                return "";
            } catch (Exception e) {
                // 回滚事务
                transactionManager.rollback(transtatus);
                log.error("保存发票信息发生异常，回滚事务：", e);
                return "保存发票信息发生异常：" + e.getMessage();
            }
        } catch (Exception e) {
            log.error("保存发票信息发生异常：", e);
            return "保存发票信息发生异常：" + e.getMessage();
        }
    }

    /**
     * 电子发票实时开票信息查询
     * @param corderSn
     * @return
     */
    public ServiceResult<Response> queryInvoice(String corderSn) {//此方法已不再使用
        String queryXml = getQueryInvoiceXml(corderSn);
        //log.info("====================发送的xml：====================\n" + queryXml);
        //        ServiceResult<String> result = eInvoiceService.queryInvoice(queryXml, corderSn);
        ServiceResult<String> result = eInvoiceV2ServiceImpl.queryInvoice(corderSn, queryXml);
        ServiceResult<Response> einvoiceResult = new ServiceResult<Response>();
        //log.info("====================返回的success：" + result.getSuccess());
        if (result.getSuccess()) {
            try {
                String responXML = result.getResult();
                //log.info("====================返回的xml：====================\n" + responXML);
                Response response = UnmarshalXmlUtil.analyzeEInvoiceResponse(responXML);
                einvoiceResult.setResult(response);
            } catch (Exception e) {
                log.error("解析响应xml异常", e);
                e.printStackTrace();
            }
        } else {
            einvoiceResult.setSuccess(result.getSuccess());
            einvoiceResult.setMessage(result.getMessage());
        }
        return einvoiceResult;
    }

    /**
     * 电子发票开票信息查询
     * @param corderSn
     * @return
     */
    @Deprecated
    public Map<String, String> queryInvoiceResultMapOld(String corderSn) {
        String queryXml = getQueryInvoiceXmlOld(corderSn);
        //log.info("====================发送的xml：====================\n" + queryXml);
        ServiceResult<String> result = eInvoiceService.queryInvoice(queryXml, corderSn);
        //log.info("====================返回的success：" + result.getSuccess());
        org.dom4j.Document doc = null;
        String[] strmes = new String[1];
        if (result.getSuccess()) {
            try {
                String responXML = result.getResult();
                //log.info("====================返回的xml：====================\n" + responXML);
                doc = parseXml(responXML, strmes);
                return invoiceResultDocToMapOld(doc);
            } catch (Exception e) {
                log.error("解析响应xml异常", e);
                e.printStackTrace();
            }
        } else {
            log.error("====================失败返回的mes：====================\n" + result.getMessage());
        }
        return null;
    }

    /**
     * 电子发票开票信息查询
     * @param corderSn
     * @return
     */
    public Map<String, String> queryInvoiceResultMap(String corderSn) {
        String queryXml = getQueryInvoiceXml(corderSn);
        //log.info("====================发送的xml：====================\n" + queryXml);
        //        ServiceResult<String> result = eInvoiceService.queryInvoice(queryXml, corderSn);
        ServiceResult<String> result = eInvoiceV2ServiceImpl.queryInvoice(corderSn, queryXml);
        //log.info("====================返回的success：" + result.getSuccess());
        org.dom4j.Document doc = null;
        String[] strmes = new String[1];
        if (result.getSuccess()) {
            try {
                String responXML = result.getResult();
                //log.info("====================返回的xml：====================\n" + responXML);
                doc = parseXml(responXML, strmes);
                return invoiceResultDocToMap(doc);
            } catch (Exception e) {
                log.error("解析响应xml异常", e);
                e.printStackTrace();
            }
        } else {
            log.error("====================失败返回的mes：====================\n" + result.getMessage());
        }
        return null;
    }

    /**
     * 根据网单号生成xml
     * @param corderSn
     * @return
     */
    @Deprecated
    public String getQueryInvoiceXmlOld(String corderSn) {
        Element root = new Element("request");
        Document doc = new Document(root);
        Element header = new Element("header");
        header.setAttribute("platformCode", platformcode);//InvoiceConst.PLATFORMCODE
        header.setAttribute("postTime",
            DateFormatUtil.formatByType("yyyy-MM-dd HH:mm:ss", new Date()));
        header.setAttribute("sessionID", InvoiceConst.SESSIONID);
        header.setAttribute("version", "V1.1");

        root.addContent(header);

        Element criteria = new Element("criteria");
        Element criterion = new Element("criterion");
        criterion.setAttribute("name", "orderNo");
        criterion.setAttribute("value", corderSn);
        criteria.addContent(criterion);

        root.addContent(criteria);

        XMLOutputter XMLOut = new XMLOutputter();
        return XMLOut.outputString(doc);
    }

    /**
     * 根据网单号生成xml
     * @param corderSn
     * @return
     */
    public String getQueryInvoiceXml(String corderSn) {
        Element root = new Element("request");
        Document doc = new Document(root);
        Element header = new Element("header");
        header.setAttribute("platformCode", platformcode);//InvoiceConst.PLATFORMCODE
        header.setAttribute("postTime",
            DateFormatUtil.formatByType("yyyy-MM-dd HH:mm:ss", new Date()));
        header.setAttribute("sessionID", corderSn + "_CX");
        header.setAttribute("version", InvoiceConst.VERSION);

        root.addContent(header);

        Element criteria = new Element("criteria");
        Element criterion = new Element("criterion");
        criterion.setAttribute("name", "orderNo");
        criterion.setAttribute("value", corderSn);
        criteria.addContent(criterion);

        root.addContent(criteria);

        XMLOutputter XMLOut = new XMLOutputter();
        return XMLOut.outputString(doc);
    }

    /**
     * 发票返回结果数据转换到Map
     * @param
     * @return
     * @throws ParseException
     */
    @Deprecated
    public Map<String, String> invoiceResultDocToMapOld(org.dom4j.Document doc) {
        if (doc == null) {
            return null;
        }
        org.dom4j.Element resultElement = doc.getRootElement().element("result");
        String code = resultElement.attribute("code").getValue();

        if (code != null && code.equals("1")) {//成功
            org.dom4j.Element invoiceElement = doc.getRootElement().element("invoice");

            @SuppressWarnings("unchecked")
            List<Attribute> listInvoice = invoiceElement.attributes();
            if (listInvoice != null && listInvoice.size() > 0) {
                Map<String, String> attMap = new HashMap<String, String>();
                for (int i = 0; i < listInvoice.size(); i++) {
                    if (listInvoice.get(i).getName().equalsIgnoreCase("generateTime")) {
                        attMap
                            .put(listInvoice.get(i).getName(),
                                (timeStringToDate(listInvoice.get(i).getValue()).getTime() / 1000)
                                        + "");
                    } else if (listInvoice.get(i).getName().equalsIgnoreCase("validTime")) {
                        attMap
                            .put(listInvoice.get(i).getName(),
                                (timeStringToDate(listInvoice.get(i).getValue()).getTime() / 1000)
                                        + "");
                    } else {
                        attMap.put(listInvoice.get(i).getName(), listInvoice.get(i).getValue());
                    }
                }
                return attMap;
            }
        }
        return null;
    }

    /**
     * 发票返回结果数据转换到Map
     * @param
     * @return
     * @throws ParseException
     */
    public Map<String, String> invoiceResultDocToMap(org.dom4j.Document doc) {
        if (doc == null) {
            return null;
        }
        org.dom4j.Element resultElement = doc.getRootElement().element("result");
        String code = resultElement.attribute("code").getValue();

        if (code != null && code.equals("0")) {//成功
            org.dom4j.Element invoiceElement = doc.getRootElement().element("invoices")
                .element("invoice");

            @SuppressWarnings("unchecked")
            List<Attribute> listInvoice = invoiceElement.attributes();
            if (listInvoice != null && listInvoice.size() > 0) {
                Map<String, String> attMap = new HashMap<String, String>();
                for (int i = 0; i < listInvoice.size(); i++) {
                    if (listInvoice.get(i).getName().equalsIgnoreCase("generateTime")) {
                        attMap
                            .put(listInvoice.get(i).getName(),
                                (timeStringToDate(listInvoice.get(i).getValue()).getTime() / 1000)
                                        + "");
                    } else if (listInvoice.get(i).getName().equalsIgnoreCase("validTime")) {
                        attMap
                            .put(listInvoice.get(i).getName(),
                                (timeStringToDate(listInvoice.get(i).getValue()).getTime() / 1000)
                                        + "");
                    } else {
                        attMap.put(listInvoice.get(i).getName(), listInvoice.get(i).getValue());
                    }
                }
                return attMap;
            }
        }
        return null;
    }

    /**
     * 处理发票返回时间
     * @param
     * @return
     * @throws
     */
    public Date timeStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (StringUtil.isEmpty(time)) {
            return null;
        }
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 同步作废发票信息从电子发票系统(电子发票系统同步到Invoices表)
     * 获取同步条件：invalidTime=0 and statusType=4 and success=1 and electricFlag=1 and type=2 and tryNum < 30 and addTime >= 1427817600
     * 要比较作废时间和系统日期，某个时间以后的才行，否则会把以前无效的也写入队列推到sap系统
     * 检查sap队列是否存在，不存在要写入
     * 如果存在未成功要重置次数
     */
    @Deprecated
    public void syncInvoiceInvalidInfoFromEInvoiceSystemOld() {
        try {
            //1、获取待开发票队列
            List<Invoices> list = invoicesDao.getInvoiceInvalidNotEndList(100);
            if (list == null || list.size() == 0) {
                log.info("[syn_invoiceinvalidinfo_from_einvoicesys]获取待同步作废发票信息队列：没有需要处理的记录。");
                return;
            }
            if (list != null && list.size() > 0) {
                //                log.info("[syn_invoiceinvalidinfo_from_einvoicesys]:list=" + list.size());
                for (Invoices invoices : list) {
                    if (invoices == null) {
                        continue;
                    }
                    if (StringUtil.isEmpty(invoices.getCOrderSn(), true)) {
                        invoices
                            .setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                        invoicesDao.update(invoices);
                        continue;
                    }
                    try {
                        ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
                        //同步作废发票信息
                        //                        ServiceResult<Response> electricResult = queryInvoice(invoices
                        //                            .getCOrderSn());
                        Map<String, String> paramMap = queryInvoiceResultMapOld(invoices
                            .getCOrderSn());
                        //                        boolean success = electricResult.getSuccess();
                        if (paramMap != null) {
                            //                            Response response = electricResult.getResult();
                            //                            Invoice invalidInvoice = response.getInvoice();
                            if (!OrderBizHelper.eInvoiceEntityToInvalidInvoicesOld(paramMap,
                                invoices)) {
                                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                    .getTryNum()) + 1);
                                invoicesDao.update(invoices);
                                continue;
                            }

                            //事务处理创建发票信息等逻辑
                            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                            TransactionStatus status = transactionManager.getTransaction(def);
                            try {
                                if (OrderBizHelper.updateInvoiceInfo(invoices, 2, invoicesDao,
                                    orderProductsDao, invoiceSAPLogsDao,
                                    this.getPathByName("/upfiles/sapblacklist.txt"))) {
                                    handelOrderAfterUpdateInvalidInvoice(invoices, paramMap);
                                } else {
                                    invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                        .getTryNum()) + 1);
                                    invoicesDao.update(invoices);
                                }

                                //提交事务
                                transactionManager.commit(status);
                            } catch (Exception ex) {
                                //回滚事务
                                transactionManager.rollback(status);
                                log.error("定时同步作废发票信息时异常", ex);
                                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                    .getTryNum()) + 1);
                                invoicesDao.update(invoices);
                            }
                        } else {
                            log.error("[syn_invoiceinvalidinfo_from_einvoicesys][invoiceId："
                                      + invoices.getId() + "]同步作废发票信息时异常");
                            invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                .getTryNum()) + 1);
                            invoicesDao.update(invoices);
                        }
                    } catch (Exception e) {
                        log.error("[syn_invoiceinvalidinfo_from_einvoicesys][网单id:"
                                  + invoices.getOrderProductId().toString() + "]同步作废发票信息异常字符串:"
                                  + StrTools.printExceptionStackInfo(e));
                    } finally {
                        ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取同步作废发票信息队列出现异常：", e);
        }
    }

    /**
     * 同步作废发票信息从电子发票系统(电子发票系统同步到Invoices表)
     * 获取同步条件：invalidTime=0 and statusType=4 and success=1 and electricFlag=1 and type=2 and tryNum < 30 and addTime >= 1427817600
     * 要比较作废时间和系统日期，某个时间以后的才行，否则会把以前无效的也写入队列推到sap系统
     * 检查sap队列是否存在，不存在要写入
     * 如果存在未成功要重置次数
     */
    public void syncInvoiceInvalidInfoFromEInvoiceSystem() {
        try {
            //1、获取待开发票队列
            List<Invoices> list = invoicesDao.getInvoiceInvalidNotEndList(100);
            if (list == null || list.size() == 0) {
                log.info("[syn_invoiceinvalidinfo_from_einvoicesys]获取待同步作废发票信息队列：没有需要处理的记录。");
                return;
            }
            if (list != null && list.size() > 0) {
                //                log.info("[syn_invoiceinvalidinfo_from_einvoicesys]:list=" + list.size());
                for (Invoices invoices : list) {
                    if (invoices == null) {
                        continue;
                    }
                    if (StringUtil.isEmpty(invoices.getCOrderSn(), true)) {
                        invoices
                            .setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                        invoicesDao.update(invoices);
                        continue;
                    }
                    try {
                        ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
                        //同步作废发票信息
                        //                        ServiceResult<Response> electricResult = queryInvoice(invoices
                        //                            .getCOrderSn());
                        Map<String, String> paramMap = queryInvoiceResultMap(invoices.getCOrderSn());
                        //                        boolean success = electricResult.getSuccess();
                        if (paramMap != null) {
                            //                            Response response = electricResult.getResult();
                            //                            Invoice invalidInvoice = response.getInvoice();
                            if (!OrderBizHelper.eInvoiceEntityToInvalidInvoices(paramMap, invoices)) {
                                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                    .getTryNum()) + 1);
                                invoicesDao.update(invoices);
                                continue;
                            }

                            //事务处理创建发票信息等逻辑
                            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                            TransactionStatus status = transactionManager.getTransaction(def);
                            try {
                                if (OrderBizHelper.updateInvoiceInfo(invoices, 2, invoicesDao,
                                    orderProductsDao, invoiceSAPLogsDao,
                                    this.getPathByName("/upfiles/sapblacklist.txt"))) {
                                    handelOrderAfterUpdateInvalidInvoice(invoices, paramMap);
                                } else {
                                    invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                        .getTryNum()) + 1);
                                    invoicesDao.update(invoices);
                                }

                                //提交事务
                                transactionManager.commit(status);
                            } catch (Exception ex) {
                                //回滚事务
                                transactionManager.rollback(status);
                                log.error("定时同步作废发票信息时异常", ex);
                                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                    .getTryNum()) + 1);
                                invoicesDao.update(invoices);
                            }
                        } else {
                            log.error("[syn_invoiceinvalidinfo_from_einvoicesys][invoiceId："
                                      + invoices.getId() + "]同步作废发票信息时异常");
                            invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                .getTryNum()) + 1);
                            invoicesDao.update(invoices);
                        }
                    } catch (Exception e) {
                        log.error("[syn_invoiceinvalidinfo_from_einvoicesys][网单id:"
                                  + invoices.getOrderProductId().toString() + "]同步作废发票信息异常字符串:"
                                  + StrTools.printExceptionStackInfo(e));
                    } finally {
                        ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取同步作废发票信息队列出现异常：", e);
        }
    }

    /**
     * 同步发票信息从电子发票系统(电子发票系统同步到Invoices表)
     * 如果开票成功，检查invoices表success字段是否为1，不为1要置为1
     * 检查sap队列是否存在，不存在，写入sap队列
     * 如果存在未成功要重置次数
     */
    @Deprecated
    public int syncInvoiceInfoFromEInvoiceSystemOld(List<Invoices> list) {
        int count = 0;
        try {
            if (list == null || list.size() == 0) {
                log.info("[syn_invoiceinfo_from_einvoicesys]获取待同步发票信息队列：没有需要处理的记录。");
                return 0;
            }
            if (list != null && list.size() > 0) {
                //                log.info("[syn_invoiceinfo_from_einvoicesys]:list=" + list.size());
                for (Invoices invoices : list) {
                    if (invoices == null) {
                        continue;
                    }
                    if (StringUtil.isEmpty(invoices.getCOrderSn(), true)) {
                        invoices
                            .setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                        invoicesDao.update(invoices);
                        continue;
                    }
                    try {
                        ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
                        //同步发票信息
                        //                        ServiceResult<Response> electricResult = queryInvoice(invoices
                        //                            .getCOrderSn());
                        Map<String, String> paramMap = queryInvoiceResultMapOld(invoices
                            .getCOrderSn());
                        //                        boolean success = electricResult.getSuccess();
                        if (paramMap != null) {
                            //                            Response response = electricResult.getResult();
                            //                            Invoice eInvoice = response.getInvoice();
                            if (!OrderBizHelper.eInvoiceEntityToInvoicesOld(paramMap, invoices)) {
                                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                    .getTryNum()) + 1);
                                invoicesDao.update(invoices);
                                continue;
                            }
                            Integer pushType = OrderBizHelper.getInvoiceSAPLogsOfPushType(invoices,
                                orderProductsDao);

                            //事务处理创建发票信息等逻辑
                            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                            TransactionStatus status = transactionManager.getTransaction(def);
                            try {
                                if (OrderBizHelper.updateInvoiceInfo(invoices, pushType,
                                    invoicesDao, orderProductsDao, invoiceSAPLogsDao,
                                    this.getPathByName("/upfiles/sapblacklist.txt"))) {
                                    if (invoices.getStatusType().equals(1)
                                        && invoices.getSuccess().equals(1)) {//开票
                                        handelOrderAfterUpdateCreateInvoice(invoices, paramMap);
                                    } else if (invoices.getStatusType().equals(4)
                                               && invoices.getSuccess().equals(1)) {//作废
                                        handelOrderAfterUpdateInvalidInvoice(invoices, paramMap);
                                    }
                                    count++;
                                } else {
                                    invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                        .getTryNum()) + 1);
                                    invoicesDao.update(invoices);
                                }

                                //提交事务
                                transactionManager.commit(status);
                            } catch (Exception ex) {
                                //回滚事务
                                transactionManager.rollback(status);
                                log.error("定时同步发票信息时异常", ex);
                                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                    .getTryNum()) + 1);
                                invoicesDao.update(invoices);
                            }
                        } else {
                            log.error("[syn_invoiceinfo_from_einvoicesys][invoiceId："
                                      + invoices.getId() + "]同步发票信息时异常");
                            invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                .getTryNum()) + 1);
                            invoicesDao.update(invoices);
                            continue;
                        }

                    } catch (Exception e) {
                        log.error("[syn_invoiceinfo_from_einvoicesys][网单id:"
                                  + invoices.getOrderProductId().toString() + "]同步发票信息异常字符串:"
                                  + StrTools.printExceptionStackInfo(e));
                    } finally {
                        ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取同步发票信息队列出现异常：", e);
        }
        return count;
    }

    /**
     * 同步发票信息从电子发票系统(电子发票系统同步到Invoices表)
     * 如果开票成功，检查invoices表success字段是否为1，不为1要置为1
     * 检查sap队列是否存在，不存在，写入sap队列
     * 如果存在未成功要重置次数
     */
    public int syncInvoiceInfoFromEInvoiceSystem(List<Invoices> list) {
        int count = 0;
        try {
            if (list == null || list.size() == 0) {
                log.info("[syn_invoiceinfo_from_einvoicesys]获取待同步发票信息队列：没有需要处理的记录。");
                return 0;
            }
            if (list != null && list.size() > 0) {
                //                log.info("[syn_invoiceinfo_from_einvoicesys]:list=" + list.size());
                for (Invoices invoices : list) {
                    if (invoices == null) {
                        continue;
                    }
                    if (StringUtil.isEmpty(invoices.getCOrderSn(), true)) {
                        invoices
                            .setTryNum((invoices.getTryNum() == null ? 1 : invoices.getTryNum()) + 1);
                        invoicesDao.update(invoices);
                        continue;
                    }
                    try {
                        ReadWriteRoutingDataSourceHolder.setIsAlwaysMaster(Boolean.TRUE);
                        //同步发票信息
                        //                        ServiceResult<Response> electricResult = queryInvoice(invoices
                        //                            .getCOrderSn());
                        Map<String, String> paramMap = queryInvoiceResultMap(invoices.getCOrderSn());
                        //                        boolean success = electricResult.getSuccess();
                        if (paramMap != null) {
                            //                            Response response = electricResult.getResult();
                            //                            Invoice eInvoice = response.getInvoice();
                            if (!OrderBizHelper.eInvoiceEntityToInvoices(paramMap, invoices)) {
                                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                    .getTryNum()) + 1);
                                invoicesDao.update(invoices);
                                continue;
                            }
                            Integer pushType = OrderBizHelper.getInvoiceSAPLogsOfPushType(invoices,
                                orderProductsDao);

                            //事务处理创建发票信息等逻辑
                            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                            TransactionStatus status = transactionManager.getTransaction(def);
                            try {
                                if (OrderBizHelper.updateInvoiceInfo(invoices, pushType,
                                    invoicesDao, orderProductsDao, invoiceSAPLogsDao,
                                    this.getPathByName("/upfiles/sapblacklist.txt"))) {
                                    if (invoices.getStatusType().equals(1)
                                        && invoices.getSuccess().equals(1)) {//开票
                                        handelOrderAfterUpdateCreateInvoice(invoices, paramMap);
                                    } else if (invoices.getStatusType().equals(4)
                                               && invoices.getSuccess().equals(1)) {//作废
                                        handelOrderAfterUpdateInvalidInvoice(invoices, paramMap);
                                    }
                                    count++;
                                } else {
                                    invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                        .getTryNum()) + 1);
                                    invoicesDao.update(invoices);
                                }

                                //提交事务
                                transactionManager.commit(status);
                            } catch (Exception ex) {
                                //回滚事务
                                transactionManager.rollback(status);
                                log.error("定时同步发票信息时异常", ex);
                                invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                    .getTryNum()) + 1);
                                invoicesDao.update(invoices);
                            }
                        } else {
                            log.error("[syn_invoiceinfo_from_einvoicesys][invoiceId："
                                      + invoices.getId() + "]同步发票信息时异常");
                            invoices.setTryNum((invoices.getTryNum() == null ? 1 : invoices
                                .getTryNum()) + 1);
                            invoicesDao.update(invoices);
                            continue;
                        }

                    } catch (Exception e) {
                        log.error("[syn_invoiceinfo_from_einvoicesys][网单id:"
                                  + invoices.getOrderProductId().toString() + "]同步发票信息异常字符串:"
                                  + StrTools.printExceptionStackInfo(e));
                    } finally {
                        ReadWriteRoutingDataSourceHolder.clearIsAlwaysMaster();
                    }
                }
            }
        } catch (Exception e) {
            log.error("获取同步发票信息队列出现异常：", e);
        }
        return count;
    }

    /**
     * 更新作废发票信息后处理订单操作
     */
    public void handelOrderAfterUpdateInvalidInvoice(Invoices invoices, Map<String, String> attMap) {
        if (invoices.getCOrderType().equals(InvoiceConst.COMMON_CORDER_TYPE)) {
            OrderProductsNew op = orderProductsDao.get(invoices.getOrderProductId());
            if (op != null) {
                op.setIsMakeReceipt(InvoiceConst.MR_STATE_RED);
                orderProductsDao.update(op);

                OrdersNew order = ordersDao.get(op.getOrderId());
                //记录开票操作日志
                OrderOperateLogs log = this.AssemblyOrderOperateLog(order, op, "发票状态变更为“红冲”",
                    "发票状态更改", "电子发票同步系统");
                orderOperateLogsDao.insert(log);

                int statusType = 2;//红冲
                InvoiceElectricSyncLogs invoiceElectricSyncLogs = AssemblyInvoiceElectricSyncLogs(
                    order, op, invoices, attMap);

                List<InvoiceElectricSyncLogs> iesllist = invoiceElectricSyncLogsDao
                    .getByCOrderSnAngStatusType(invoices.getCOrderSn(), statusType);
                if (iesllist == null || iesllist.size() < 1) {
                    invoiceElectricSyncLogsDao.insert(invoiceElectricSyncLogs);
                }
            }

            //插入或更新电子发票日志    ---需要设置推送和返回数据信息
            InvoiceElectricLogs invoiceElectricLogs = saveInvoiceElectricLogs(invoices, "",
                InvoiceConst.TYPE_INVALID);
            invoiceElectricLogs.setLastMessage("同步发票作废信息成功");
            invoiceElectricLogs.setLastTime((int) (new Date().getTime() / 1000));
            invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);

            boolean orderRepairs = orderRepairsCancelInvoice(invoices.getOrderProductId());
            if (orderRepairs) {
                //                log.info("更新退货单成功！orderProductId=" + invoices.getOrderProductId());
            } else {
                log.error("无退货单，或者退货单更新失败, orderProductId=" + invoices.getOrderProductId());
            }
        }
    }

    /**
     * 更新发票信息后处理订单操作
     */
    public void handelOrderAfterUpdateCreateInvoice(Invoices invoice, Map<String, String> attMap) {
        Integer orderId = 0;
        if (invoice.getCOrderType().equals(InvoiceConst.COMMON_CORDER_TYPE)) {//普通网单
            OrderProductsNew op = orderProductsDao.get(invoice.getOrderProductId());
            if (op != null) {
                op.setReceiptNum(invoice.getInvoiceNumber());
                op.setIsMakeReceipt(InvoiceConst.MR_STATE_MAKED);//// 已开票
                op.setReceiptAddTime(DateFormatUtil.formatByType("yyyyMMdd",
                    new Date(invoice.getBillingTime() * 1000)));
                op.setSystemRemark(op.getSystemRemark() + "发票状态变更为已开票,isMakeReceipt:{"
                                   + op.getIsMakeReceipt() + "},receiptNum:{" + op.getReceiptNum()
                                   + "},receiptAddTime:{" + op.getReceiptAddTime() + "};");
                orderProductsDao.update(op);

                OrdersNew order = ordersDao.get(op.getOrderId());
                orderId = Integer.parseInt(order.getId());
                //记录开票操作日志
                OrderOperateLogs log = this.AssemblyOrderOperateLog(order, op,
                    "发票状态变更为:“已开票”。税控码：" + invoice.getFiscalCode(), "发票状态更改", "电子发票同步系统");
                orderOperateLogsDao.insert(log);

                int statusType = 1;//开票
                InvoiceElectricSyncLogs invoiceElectricSyncLogs = AssemblyInvoiceElectricSyncLogs(
                    order, op, invoice, attMap);

                List<InvoiceElectricSyncLogs> iesllist = invoiceElectricSyncLogsDao
                    .getByCOrderSnAngStatusType(invoice.getCOrderSn(), statusType);
                if (iesllist == null || iesllist.size() < 1) {
                    invoiceElectricSyncLogsDao.insert(invoiceElectricSyncLogs);
                }

                //创建发票成功后同步HP队列表插入数据，单独运行，表`invoice_electric_2_out`,另外写job读取该表数据执行上面的电子发票信息传HP功能
                insertInvoiceElectric2Out(invoice.getId(), "HP");
            }
        } else if (invoice.getCOrderType().equals(InvoiceConst.DIFF_CORDER_TYPE)) {//差异网单
            Order2ths order2ths = order2thsDao.get(invoice.getDiffId());
            if (order2ths != null) {
                orderId = order2ths.getId();
                order2ths.setReceiptNumber(invoice.getInvoiceNumber());
                order2ths
                    .setJSDate(DateFormatUtil.formatTime("yyyyMMdd", invoice.getBillingTime()));
                order2ths.setIsReceipted(InvoiceConst.R_RECEIPTED);
                order2thsDao.update(order2ths);
            }
            insertInvoiceElectric2Out(invoice.getId(), "SAP");

        } else {
            Order4Invoices order4Invoice = order4InvoicesDao.get(invoice.getDiffId());
            if (order4Invoice != null) {
                orderId = order4Invoice.getId();
                order4Invoice.setReceiptNumber(invoice.getInvoiceNumber());
                order4Invoice.setJSDate(DateFormatUtil.formatTime("yyyyMMdd",
                    invoice.getBillingTime()));
                order4Invoice.setIsReceipted(InvoiceConst.R_RECEIPTED);
                order4InvoicesDao.update(order4Invoice);
            }
        }

        //插入或更新电子发票日志    ---需要设置推送和返回数据信息
        InvoiceElectricLogs invoiceElectricLogs = saveInvoiceElectricLogs(invoice, "",
            InvoiceConst.TYPE_GENERATE);
        invoiceElectricLogs.setLastMessage("同步发票开票信息成功");
        invoiceElectricLogs.setLastTime((int) (new Date().getTime() / 1000));
        invoiceElectricLogsDao.updateByInvoiceIdAndType(invoiceElectricLogs);

        MemberInvoices mInvoices = memberInvoicesDao.getByOrderId(orderId);
        if (mInvoices != null) {
            mInvoices.setIsLock(1);//开票成功后设置锁，不让修改信息
            memberInvoicesDao.update(mInvoices);
        }
    }
}
