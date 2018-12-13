package com.haier.stock.services;

import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.BusinessException;
import com.haier.eis.model.EisExternalSku;
import com.haier.eis.model.EisInterfaceFinance;
import com.haier.eis.model.EisInterfaceStatus;
import com.haier.eis.model.EisStockBusinessQueue;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.service.EisExternalSkuService;
import com.haier.eis.service.EisInterfaceFinanceService;
import com.haier.eis.service.EisInterfaceStatusService;
import com.haier.eis.service.EisLesStockTransQueueService;
import com.haier.eis.service.EisStockBusinessQueueService;
import com.haier.eis.service.LesStockTransQueueService;
import com.haier.purchase.data.model.GoodsBackInfoResponse;
import com.haier.purchase.data.model.PurchaseItem;
import com.haier.purchase.data.model.PurchaseOrder;
import com.haier.purchase.data.model.PurchaseOrderInfoItem;
import com.haier.shop.model.ItemBase;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.OrdersNew;
import com.haier.stock.model.InvReservedConfig;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvStockBatch;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvStockInOut;
import com.haier.stock.model.InvStockTransaction;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.service.StockReservedService;
import com.haier.stock.service.StockService;
import com.haier.stock.services.Helper.EisBuzHelper;
import com.haier.stock.services.finance.PushReturnInfoToGVSHandler;
import com.haier.stock.services.finance.SFHandler;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


@Service("eISStockModel")
public class EISStockModel {
	   private Logger              logger   = LoggerFactory.getLogger(EISStockModel.class);
     private static Pattern stockCheckpattern = Pattern.compile("^3.{2}4$");
	   private LesStockTransQueue transQueue;
	   @Autowired
	   private StockCommonServiceImpl      stockCommonServiceImpl;
	   @Autowired
	   private TransferLineServiceImpl  transferLineServiceImpl;
	   @Autowired
	   private OrderServiceImpl         orderServiceImpl;
	   @Autowired
	   private DataSourceTransactionManager transactionManagerEis;
	   @Autowired
	   private PurchaseOrderServiceImpl purchaseOrderServiceImpl;
	   @Autowired
	   private EisStockBusinessQueueService eisStockBusinessQueueService;
	   @Autowired
	   private VomOrderServiceImpl                vomOrderServiceImpl;
	   @Autowired
	   private EisExternalSkuService eisExternalSkuService;
	   @Autowired
	   private StockTransactionServiceImpl stockTransactionServiceImpl;
	   @Autowired
	   private EisLesStockTransQueueService          eisLesStockTransQueueService;
	   @Autowired
	   private StockCenterItemServiceImplNew          itemServiceImplNew;
	   @Autowired
     private EisInterfaceFinanceService eisInterfaceFinanceDao;
	   @Autowired
	   private PushReturnInfoToGVSHandler sfHandler;
	   @Autowired
     private LesStockTransQueueService lesStockTransQueueDao;

  @Autowired
  private EisInterfaceStatusService eisInterfaceStatusService;
  @Autowired
  private StockService stockService;
  @Autowired
  private StockReservedService stockReservedService;


	   private String channel; //关联渠道
	   private boolean isRelease = false; //是否需要释放库存，默认为否

       public boolean isRelease() {
		return isRelease;
		}

		public void setRelease(boolean isRelease) {
			this.isRelease = isRelease;
		}

	public String getChannel() {
           return channel;
       }

       public void setChannel(String channel) {
           this.channel = channel;
       }

	   public LesStockTransQueue getTrans() {
           return transQueue;
       }
	   private int businessProcessStatus = 2; //关联业务处理状态，默认为不处理
    /**
     * 处理LES出入库记录JOB：根据LES出入库记录生成库存交易记录。LES出入库记录是实际库存交易，需要根据业务规则生成虚拟库存交易（分渠道）。无法确定渠道的滞后处理，直到确定渠道或者认为指定渠道后。
     * 非10批次的交易记录渠道指向WA。
     * 采购：3pl根据采购单关联渠道；其他采购根据SI或者DI单号关联渠道。
     * 调拨：关联调拨单的渠道。
     * 销售出库：根据订单来源确定渠道
     * 退货入库：根据正向销售单确定渠道
     * 正品退货：正品退货关联的渠道（暂时不做）。
     * 拒收入库：根据单据规则确定渠道（京东）
     * 其他：渠道指向WA。
     */
    public void processLessStockTransJob() {
        //滞后处理的记录，每次只处理500条

        int COUNT = 0;
        List<LesStockTransQueue> transQueues = eisLesStockTransQueueService.getByStatus(LesStockTransQueue.STATUS_DELAY, 500);
        do {
            for (LesStockTransQueue transQueue : transQueues) {
                LessTransProcess transProcess = getTransProcess(transQueue);
                transProcess.process();
                COUNT++;
            }
            transQueues = eisLesStockTransQueueService.getByStatus(LesStockTransQueue.STATUS_UNDONE, 1000);
        } while (transQueues != null && !transQueues.isEmpty());
        logger.info("[EisStockModel][processLessStockTransJob]-处理LESS出入库记录完成,共:" + COUNT + " ,条记录!");
    }
    /**
     * 根据 bill_type 获取 TransProcess
     *
     * @param transQueue
     * @return
     */
    private LessTransProcess getTransProcess(LesStockTransQueue transQueue) {
        InventoryBusinessTypes businessTypes = InventoryBusinessTypes
            .getByCode(transQueue.getBillType());
        LessTransProcess transProcess;
        switch (businessTypes) {
            case IN_PURCHASE://采购入库
                transProcess = new LessZBCRTransProcess(transQueue);
                break;
            case IN_CHANGE://存性变更入库
            case OUT_CHANGE://存性变更出库
                transProcess = new LessChangeTransProcess(transQueue);
                break;
            case IN_TRANSFER://调拨入库
            case OUT_TRANSFER://调拨出库
                transProcess = new LessTransferTransProcess(transQueue);
                break;
            case OUT_SALE://销售出库
                transProcess = new LessZBCCTransProcess(transQueue);
                break;
            case IN_REFUSE://拒收入库
                transProcess = new LessZBCJTransProcess(transQueue);
                break;
            case IN_RETURNED://退货入库
                transProcess = new LessZBCTTransProcess(transQueue);
                break;
            default://不可识别的交易类型，不处理
                transProcess = new LessUnidentifiedTransProcess(transQueue);
                break;
        }
        return transProcess;
    }


    /**
     * 拒收入库
     */
    private class LessZBCJTransProcess extends LessTransProcess {

        LessZBCJTransProcess(LesStockTransQueue transQueue) {
            super(transQueue);
        }

        @Override
        boolean doWith() throws BusinessException {
            String corderSn = getTrans().getCorderSn();
            if (corderSn.matches(".+(JS.*)")) {
                setChannel(InvStockChannel.JD);
                return true;
            }
            if (corderSn.matches(".+(YS.*)")) {
                setChannel(InvStockChannel.YX);
                return true;
            }
            throw new BusinessException("非JD的拒收入库记录，无法分渠道");
        }
    }


    /**
     * 调拨
     */
    private class LessTransferTransProcess extends LessTransProcess {

        LessTransferTransProcess(LesStockTransQueue transQueue) {
            super(transQueue);
        }

        @Override
        boolean doWith() throws BusinessException {
            String channel;
            String corderSn = getTrans().getCorderSn();

            InvTransferLine transferLine = EisBuzHelper.getTransferLine(transferLineServiceImpl,
                corderSn);
            if (transferLine == null) {
                throw new BusinessException("调拨网单已不存在，网单号：" + corderSn);
            }
            //需要更新调拨单状态
            setBusinessProcessStatus(0);
            String billType = getTrans().getBillType();
            if (InventoryBusinessTypes.OUT_TRANSFER.getCode().equalsIgnoreCase(billType)) {
                channel = transferLine.getChannelFrom();
                setIsRelease(true);
            } else {
                channel = transferLine.getChannelTo();
            }
            if (StringUtil.isEmpty(channel)) {
                if (transferLine.getTransferReason().equals(InvTransferLine.TRANSFER_REASON_XN)) {
                    InvSection section;
                    if (InventoryBusinessTypes.OUT_TRANSFER.getCode().equalsIgnoreCase(billType)) {
                        section = EisBuzHelper.getInvSection(stockCommonServiceImpl,
                            transferLine.getSecFrom());
                    } else {
                        section = EisBuzHelper.getInvSection(stockCommonServiceImpl,
                            transferLine.getSecTo());
                    }
                    if (section != null) {
                        channel = section.getChannelCode();
                    }
                } else {
                    channel = transferLine.getChannelId();
                }
            }
            if ("DK".equalsIgnoreCase(channel)) {
                channel = "DKH";
            }
            if (StringUtil.isEmpty(channel)) {
                throw new BusinessException("无法确定调拨单的渠道");
            }
            setChannel(channel);
            return true;
        }
    }
    void setBusinessProcessStatus(int businessProcessStatus) {
        this.businessProcessStatus = businessProcessStatus;
    }

    /**
     * 存性变更
     */
    private class LessChangeTransProcess extends LessTransProcess {

        LessChangeTransProcess(LesStockTransQueue transQueue) {
            super(transQueue);
        }

        @Override
        boolean doWith() throws BusinessException {
            //如果是其他批次，指定渠道为WA
            if (!InvSection.W10.equals(getTrans().getCharg())) {
                setChannel(InvSection.CHANNEL_CODE_WA);
                return true;
            }
            //根据关联的网单确认渠道
            String orderSn = getTrans().getCorderSn().substring(0, 14);
            OrderProductsNew orderProduct = EisBuzHelper.getOrderProducts(orderServiceImpl, orderSn);
            if (orderProduct == null) {
                throw new BusinessException("关联的网单不存在");
            }
            OrdersNew orders = getOrderById(orderProduct.getOrderId());
            if (orders == null) {
                throw new BusinessException("关联的订单不存在");
            }
            String source = orders.getSource();
            String channel = getInvStockChannelCodeBySource(source);
            if (StringUtil.isEmpty(channel)) {
                throw new BusinessException("\"订单来源【\" + source + \"】关联的渠道不存在\"");
            }
            setChannel(channel);
            return true;
        }
    }

    /**
     * 采购入库：
     * <ul>
     * <li>日日单采购入库</li>
     * <li>3pl采购入库</li>
     * <li>CBS采购入库</li>
     * <li>样品机采购入库</li>
     * </ul>
     */
    private class LessZBCRTransProcess extends LessTransProcess {

        LessZBCRTransProcess(LesStockTransQueue transQueue) {
            super(transQueue);
        }

        //获取日日单信息
        private OrderProductsNew getProduceOrderProduct() {
            String pdOrderSn = getTrans().getBstkd();
            if (StringUtil.isEmpty(pdOrderSn) || !pdOrderSn.startsWith("WD")) {
                return null;
            }
            OrderProductsNew orderProducts = EisBuzHelper.getOrderProducts(orderServiceImpl, pdOrderSn);
            if (orderProducts != null && orderProducts.getPdOrderStatus() > 0) {
                return orderProducts;
            }
            return null;
        }

        @Override
        boolean doWith() throws BusinessException {

            //4.样品机采购入库
            if (InvSection.W40.equals(getTrans().getCharg())) {
                setChannel(InvSection.CHANNEL_CODE_WA);
                return true;
            } else if (!InvSection.W10.equals(getTrans().getCharg())) {
                throw new BusinessException("不支持的批次：" + getTrans().getCharg());
            }

            //1.日日单，根据订单来源确定渠道
            OrderProductsNew pdOrderProduct = getProduceOrderProduct();
            if (pdOrderProduct != null) {
                //日日单状态没有完成关闭和取消关闭时才会占用库存
                setIsFrozen(
                    pdOrderProduct.getStatus() < OrderProductsNew.STATUS_CANCEL_CLOSE && pdOrderProduct
                        .getPdOrderStatus() < OrderProductsNew.RRSSTATUS_FINISH_CLOSE);
                setBusinessProcessStatus(getIsFrozen() ? 0 : 2);
                String channel = ascertainChannelOfOutSale(pdOrderProduct);
                if (StringUtil.isEmpty(channel)) {
                    throw new BusinessException("确定日日单所属渠道失败，关联关系不存在");
                }
                //修改单据号为日日单网单号
                getTrans().setCorderSn(pdOrderProduct.getCOrderSn());
                setChannel(channel);
                return true;
            }

            String corderSn = getTrans().getCorderSn();

            //2.CBS采购，根据SI单或者85DN单号获取关联的采购PO单获取渠道
            //85D单号去掉D
            //PC6000190000008,统帅金立PC单
            String pCOrderSn = corderSn.matches("8.+(D.*)?")
                ? corderSn.substring(0, corderSn.length() - 1)
                : corderSn.matches("(SI).+") ? corderSn
                    : corderSn.matches("(PC).+") ? corderSn : null;
            if (pCOrderSn != null) {
                ServiceResult<List<PurchaseOrderInfoItem>> pResult = purchaseOrderServiceImpl
                    .getOrderInfoByDnSi(Collections.singletonList(pCOrderSn));
                if (!pResult.getSuccess()) {
                    throw new BusinessException("根据85DN号或SI号获取WP采购单信息失败：" + pResult.getMessage());
                }

                //使用采购单中的渠道
                if (pResult.getResult().size() > 0) {
                    PurchaseOrderInfoItem purchaseOrderInfoItem = pResult.getResult().get(0);
                    getTrans().setCorderSn(pCOrderSn);
                    if (!StringUtil.isEmpty(purchaseOrderInfoItem.getEd_channel_id())) {
                        setChannel(purchaseOrderInfoItem.getEd_channel_id());
                        return true;
                    }
                }

                //使用手工指定的渠道
                if (getTrans().getIsManualSetChannel().equals(1)) {
                    String channelRevised = getTrans().getChannelRevised();
                    if (!StringUtil.isEmpty(channelRevised)) {
                        setChannel(channelRevised);
                        return true;
                    }
                    return false;
                }

                //设置为可以手工指定渠道
                updateTransIsManualSetChannel("未能关联上WP采购单，请手工指定渠道", true);

                return false;
            }

            //3.3PL采购单据
            PurchaseItem pItem = EisBuzHelper.getPurchaseItem(purchaseOrderServiceImpl, corderSn);
            if (pItem != null) {
                PurchaseOrder pOrder = EisBuzHelper.getPurchaseOrder(purchaseOrderServiceImpl,
                    pItem.getPoId());
                if (pOrder == null) {
                    throw new BusinessException("3pl采购订单数据异常，采购网单关联的采购订单不存在");
                }
                setBusinessProcessStatus(0);
                String channel = pOrder.getChannelCode();
                if (StringUtil.isEmpty(channel)) {
                    throw new BusinessException(pItem.getPoItemNo() + " 3pl采购单信息错误，没有渠道信息");
                }
                setChannel(channel);
                return true;
            }

            //其他
            throw new BusinessException("无法识别的采购入库单据");
        }
    }

    /**
     * 销售出库
     */
    private class LessZBCCTransProcess extends LessTransProcess {

        LessZBCCTransProcess(LesStockTransQueue transQueue) {
            super(transQueue);
        }

        @Override
        boolean doWith() throws BusinessException {
            setIsRelease(true);
            String channel;
            String corderSn = getTrans().getCorderSn();
            if (corderSn.endsWith("J") || corderSn.endsWith("j")) { //京东的网单
                channel = InvStockChannel.JD;
            } else if (corderSn.endsWith("Y") || corderSn.endsWith("y")) {//易迅的网单
                channel = InvStockChannel.YX;
            } else if (corderSn.matches("WDDTD.+") || corderSn.matches("WDD.+")) {//DTD的网单
                channel = "RS";
            } else {//内部网单根据订单来源区分渠道
                //获取正品退货关联的渠道
                OrderProductsNew orderProducts = EisBuzHelper.getOrderProducts(orderServiceImpl, corderSn);
                if (orderProducts != null) {
                    OrdersNew orders = getOrderById(orderProducts.getOrderId());
                    if (orders == null) {
                        throw new BusinessException("网单[" + getTrans().getCorderSn() + "]关联的订单不存在");
                    }
                    String source = orders.getSource();
                    channel = getInvStockChannelCodeBySource(source);

                } else {//获取正品退货关联渠道
                    channel = getGoodsBackInfo(corderSn);
                    if (!StringUtil.isEmpty(channel)) {
                        setBusinessProcessStatus(0);
                    }
                }
            }

            if (StringUtil.isEmpty(channel)) {
                throw new BusinessException("订单号[" + getTrans().getCorderSn() + "]没有关联的渠道");
            }

            setChannel(channel);
            return true;
        }
    }
    /**
     * LESS出入库记录处理抽象类，根据不同的业务类型实现确定渠道的方法，可以指定是否占用和关联业务处理状态
     */
    private abstract class LessTransProcess {
        private LesStockTransQueue transQueue;

        private boolean isFrozen = false; //是否需要占用库存，默认为否

        private boolean isRelease = false; //是否需要释放库存，默认为否

        private int businessProcessStatus = 2; //关联业务处理状态，默认为不处理

        private String channel; //关联渠道

        LessTransProcess(LesStockTransQueue transQueue) {
            this.transQueue = transQueue;
        }

        public LesStockTransQueue getTrans() {
            return transQueue;
        }

        boolean getIsFrozen() {
            return isFrozen;
        }

        void setIsFrozen(boolean isFrozen) {
            this.isFrozen = isFrozen;
        }

        int getBusinessProcessStatus() {
            return businessProcessStatus;
        }

        void setBusinessProcessStatus(int businessProcessStatus) {
            this.businessProcessStatus = businessProcessStatus;
        }

        boolean getIsRelease() {
            return isRelease;
        }

        void setIsRelease(boolean isRelease) {
            this.isRelease = isRelease;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        /**
         * 设置交易是否占用库存、是否释放库存、关联业务处理状态、关联渠道
         *
         * @throws BusinessException 业务异常
         */
        abstract boolean doWith() throws BusinessException;

        private boolean updateTransStatus(Integer status, String errorMessage) {
            if (!StringUtil.isEmpty(errorMessage) && errorMessage.length() > 255) {
                errorMessage = errorMessage.substring(0, 255);
            }
            return eisLesStockTransQueueService.updateStatus(getTrans().getId(), status,
                getTrans().getStatus(), errorMessage) > 0;
        }

        private boolean updateTransDone() {
            return updateTransStatus(LesStockTransQueue.STATUS_DONE, "");
        }

        protected boolean updateTransFailed(String errorMessage) {
            return updateTransStatus(LesStockTransQueue.STATUS_DELAY, errorMessage);
        }

        protected boolean updateTransUnidentified(String errorMessage) {
            return updateTransStatus(LesStockTransQueue.STATUS_UNIDENTIFIED, errorMessage);
        }

        protected boolean updateTransIsManualSetChannel(String errorMessage,
                                                        boolean isManualSetChannel) {
            if (!StringUtil.isEmpty(errorMessage) && errorMessage.length() > 255) {
                errorMessage = errorMessage.substring(0, 255);
            }
            return eisLesStockTransQueueService.updateIsManualSetChannel(getTrans().getId(),
                LesStockTransQueue.STATUS_DELAY, getTrans().getStatus(), errorMessage,
                isManualSetChannel ? 1 : 0) > 0;
        }

        /**
         * 添加到业务处理队列
         *
         * @param stockTransQueue
         */
        private void newStockBusinessQueue(LesStockTransQueue stockTransQueue) {
            EisStockBusinessQueue stockBusinessQueue = new EisStockBusinessQueue();
            stockBusinessQueue.setStockTransQueueId(stockTransQueue.getId());
            stockBusinessQueue.setAddTime(new Date());
            eisStockBusinessQueueService.insert(stockBusinessQueue);
        }

        private void saveInvTrans(InvStockTransaction invTrans) throws BusinessException {
            if (StringUtil.isEmpty(getChannel())) {
                throw new BusinessException("未设置关联渠道");
            }

            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//            TransactionStatus status = transactionManagerEis.getTransaction(def);
            try {
                //先更新les_stock_trans_queue防止并发，并保持事物的一致性
                if (updateTransDone()) {
                    //写入eis_stock_business_queue
                    if (invTrans.getBusinessProcessStatus() == 0) {
                        newStockBusinessQueue(transQueue);
                    }

                    //调用 StockService 生成库存交易记录
                    ServiceResult<Boolean> rs = stockTransactionServiceImpl.addStockTransactionWithCheck(invTrans, transQueue.getId());
                    if (!rs.getSuccess() || !rs.getResult()) {
                        throw new BusinessException(rs.getMessage());
                    }
                    getTrans().setStatus(LesStockTransQueue.STATUS_DONE);
                } else {
                    logger.info("[StockTrans][saveInvTrans]:出现并发情况(" + getTrans().getId() + ","
                                + getTrans().getCorderSn() + ")");
                }

//                transactionManagerEis.commit(status);
            } catch (Exception ex) {
//                transactionManagerEis.rollback(status);
                throw new BusinessException(ex.getMessage());
            }
        }

        public void process() {
            try {
                //3%4库位数据不处理
                if (getTrans().getSecCode() != null) {

                    Matcher matcher = stockCheckpattern.matcher(getTrans().getSecCode());
                    while (matcher.find()) {
                        updateTransUnidentified("库位问题");
                        return;
                    }
                }

                InventoryBusinessTypes businessTypes = InventoryBusinessTypes
                    .getByCode(getTrans().getBillType());
                //不再处理冲销业务
                if (!businessTypes.getMark().equals(getTrans().getMark())) {
                    updateTransUnidentified("交易类型和借贷标识不一致");
                    return;
                }
                //转换为内部编码，主要针对于统帅和新宝产品，需要根据R码转换为6码
                EisExternalSku byR = eisExternalSkuService.getByExternalSku(getTrans().getSku());
                if (byR != null) {
                    getTrans().setSku(byR.getSku());
                    eisLesStockTransQueueService.updateSKU(getTrans().getId(), getTrans().getSku());
                }
                //检查物料是否存在，不存在需要业务人员手工维护关系后处理
                ServiceResult<ItemBase> rs = itemServiceImplNew.getItemBaseBySku(getTrans().getSku());
                if (!rs.getSuccess()) {
                    throw new BusinessException("获取itemBase出现未知错误：" + rs.getMessage());
                }
                ItemBase itemBase = rs.getResult();
                if (itemBase == null) {
                    throw new BusinessException("无法识别的物料编码 " + getTrans().getSku());
                }

                //业务处理
                if (doWith()) {
                    saveInvTrans(generateInvTrans());
                    logger.info("处理LESS交易（" + getTrans().getCorderSn() + "，" + getTrans().getMark()
                                + "，" + getTrans().getBillType() + "）完成，：" + channel + "|"
                                + getBusinessProcessStatus() + "|" + getIsFrozen());
                } else {
                    if (!LesStockTransQueue.STATUS_DONE.equals(getTrans().getStatus())) {
                        updateTransFailed("处理失败");
                    }
                    logger.info("处理LESS交易（" + getTrans().getCorderSn() + "，" + getTrans().getMark()
                                + "，" + getTrans().getBillType() + "）失败，：" + channel + "|"
                                + getBusinessProcessStatus() + "|" + getIsFrozen());
                }

            } catch (Exception e) {
                if (e instanceof BusinessException) {
                    updateTransFailed(e.getMessage());
                    logger.error("处理less（" + getTrans().getCorderSn() + "，" + getTrans().getMark()
                                + "，" + getTrans().getBillType() + "）交易失败:" + e.getMessage());
                } else {
                    updateTransFailed("未知错误：" + e.getMessage());
                    logger.error("处理less（" + getTrans().getCorderSn() + "，" + getTrans().getMark()
                                 + "，" + getTrans().getBillType() + "）交易出现未知错误:",
                        e);
                }
            }
        }

        private InvStockTransaction generateInvTrans() {
            InvStockTransaction stockTransaction = new InvStockTransaction();
            stockTransaction.setSku(transQueue.getSku());
            stockTransaction.setExternalSecCode(transQueue.getSecCode());
            stockTransaction.setChannelCode(channel);
            stockTransaction.setCorderSn(transQueue.getCorderSn());
            stockTransaction.setQuantity(transQueue.getQuantity());
            stockTransaction.setMark(transQueue.getMark());
            stockTransaction.setItemProperty(transQueue.getCharg());
            stockTransaction.setBillType(StringUtil.isEmpty(transQueue.getBillType())
                ? InventoryBusinessTypes.UNDEFINED.getCode() : transQueue.getBillType());
            stockTransaction.setBillTime(transQueue.getBillTime());
            stockTransaction.setIsFrozen(getIsFrozen() ? 1 : 0);
            stockTransaction.setIsRelease(getIsRelease() ? 1 : 0);
            stockTransaction.setProcessStatus(0);
            stockTransaction.setBusinessProcessStatus(businessProcessStatus);
            stockTransaction.setAddTime(new Date());
            stockTransaction.setIsDelay(0);
            return stockTransaction;
        }
    }
    /**
     * 退货入库
     */
    private class LessZBCTTransProcess extends LessTransProcess {

        LessZBCTTransProcess(LesStockTransQueue transQueue) {
            super(transQueue);
        }

        @Override
        boolean doWith() throws BusinessException {

            if (getTrans().getCorderSn().matches("WDDTD.+")
                || getTrans().getCorderSn().matches("WDD.+")) {//DTD业务
                setChannel("RS");
                return true;
            }

            setBusinessProcessStatus(0);
            String channel;
            String _corderSn = getTrans().getCorderSn().replaceAll("TH.*", "");//网单号=退货单号去掉TH*
            OrderProductsNew orderProducts = EisBuzHelper.getOrderProducts(orderServiceImpl, _corderSn);
            if (orderProducts != null) {
                OrdersNew order = getOrderById(orderProducts.getOrderId());
                if (order == null) {
                    throw new BusinessException("网单[" + getTrans().getCorderSn() + "]关联的订单不存在");
                }
                String source = order.getSource();
                //海朋转TC订单和海尔生活家电旗舰店订单渠道指定到商城
                if ("H2B2B".equalsIgnoreCase(source) || "TOPDHSC".equalsIgnoreCase(source)) {
                    channel = InvStockChannel.CHANNEL_SHANGCHENG;
                } else {
                    channel = getInvStockChannelCodeBySource(source);
                }

            } else {//正品退货逆向
                channel = getGoodsBackInfo(getTrans().getCorderSn());
            }

            //使用手工指定的渠道（此方法只有手动在数据库添加渠道才能执行）
            if (getTrans().getIsManualSetChannel().equals(1)) {
                String channelRevised = getTrans().getChannelRevised();
                if (!StringUtil.isEmpty(channelRevised)) {
                    setChannel(channelRevised);
                    return true;
                }
            }

            if (StringUtil.isEmpty(channel)) {
                throw new BusinessException("订单号[" + getTrans().getCorderSn() + "]没有关联的渠道");
            }

            setChannel(channel);
            return true;
        }
    }

    /**
     * 不合法的交易类型，直接修改交易的处理状态为不处理
     */
    private class LessUnidentifiedTransProcess extends LessTransProcess {

        LessUnidentifiedTransProcess(LesStockTransQueue transQueue) {
            super(transQueue);
        }

        @Override
        boolean doWith() throws BusinessException {
            updateTransUnidentified("未定义的交易类型，不处理");
            return false;
        }

        @Override
        public void process() {
            try {
                updateTransUnidentified("未定义的交易类型，不处理");
                logger.info("less（" + getTrans().getCorderSn() + "，" + getTrans().getMark() + "，"
                            + getTrans().getBillType() + "）交易：不可识别的交易类型，不处理");
            } catch (Exception ex) {
                logger.error("LessUnidentifiedTransProcess process faild:", ex);
            }
        }
    }

    private OrdersNew getOrderById(Integer orderId) {
        ServiceResult<OrdersNew> rs2 = orderServiceImpl.getOrderById(orderId);
        if (!rs2.getSuccess()){
            throw new BusinessException("向订单服务请求订单信息出现错误:" + rs2.getMessage());
        }
        return rs2.getResult();
    }

    private String getInvStockChannelCodeBySource(String source) {
        ServiceResult<String> rs3 = stockCommonServiceImpl.getChannelCodeByOrderSource(source);
        if (!rs3.getSuccess()){
            throw new BusinessException("通过订单来源向库存服务请求渠道编码发生错误:" + rs3.getMessage());
        }
        return rs3.getResult();
    }

    /**
     * 确定出入库记录的渠道
     *
     * @param orderProducts 网单
     * @return 渠道编码
     */
    private String ascertainChannelOfOutSale(OrderProductsNew orderProducts) {
        OrdersNew order = getOrderById(orderProducts.getOrderId());
        if (order == null) {
            logger.error("确定销售出库记录的渠道错误：网单关联的订单不存在：" + orderProducts.getOrderId());
            return null;
        }
        String source = order.getSource();
        return getInvStockChannelCodeBySource(source);
    }
    /**
     * 获取正品退货关联渠道
     *
     * @return
     */
    private String getGoodsBackInfo(String orderNo) {

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("orderNo", orderNo);
        ServiceResult<GoodsBackInfoResponse> result = vomOrderServiceImpl.getGoodsBackInfo(paramMap);
        if (!result.getSuccess()) {
            throw new BusinessException("处理正品退货记录[" + orderNo + "]失败：" + result.getMessage());
        }
        GoodsBackInfoResponse goodsBackInfoResponse = result.getResult();
        if (goodsBackInfoResponse == null) {
            return null;
        }
        return goodsBackInfoResponse.getChannel();
    }

    public void reviseLessStockTransChannel(int transId, String channel, String user) {
        LesStockTransQueue transQueue = eisLesStockTransQueueService.getById(transId);
        if (transQueue == null) {
            throw new BusinessException("Less交易不存在，无法修正渠道");
        }
        if (LesStockTransQueue.STATUS_DELAY.equals(transQueue.getStatus())) {
            int n = eisLesStockTransQueueService.updateChannel(transId, channel, user);
            if (n < 1) {
                throw new BusinessException("系统繁忙，请稍后再试");
            }
        } else {
            throw new BusinessException("此交易已经被处理");
        }
    }

    /**
     * Job：LES出入库后调用财务接口
     */
    public void processLesTransForFinance() {

        List<EisInterfaceFinance> failedInterfaceFinances;
        //        List<EisInterfaceFinance> unKnownInterfaceFinances = null;
        List<LesStockTransQueue> transQueues;
        try {
            Map<String,Object> failParams = new HashMap<String,Object>(3);
            failParams.put("status",EisInterfaceFinance.STATUS_FAILED);
            failParams.put("isNotDN",true);
            failParams.put("isNotTrans",true);
            // 处理调用失败的
            failedInterfaceFinances = eisInterfaceFinanceDao
                .getByParams(failParams);
            for (EisInterfaceFinance interfaceFinance : failedInterfaceFinances) {
                try {
                    sfHandler.handleRequest(interfaceFinance);
                } catch (Exception e) {
                    logger.error("Redo[To Sap]：重新发财务出现异常lesTransId["
                            + interfaceFinance.getLesStockTransQueueId() + "]",
                        e);
                }
            }

            //处理新数据
            /****去掉原来按最后id号取的逻辑改成按状态***/
            Map<String,Object> initParams = new HashMap<String,Object>(2);
            initParams.put("isNotDN",true);
            initParams.put("isNotTrans",true);
            transQueues = lesStockTransQueueDao.getForFinanceByParams(initParams);
            for (LesStockTransQueue transQueue : transQueues) {
                try {
                    sfHandler.handleRequest(transQueue);
                    lesStockTransQueueDao.updateAfterDoFinance(transQueue.getId());
                } catch (Exception e) {
                    logger.error("New[To Sap]:处理出入库记录发生异常lesTransId[" + transQueue.getId() + "]",
                        e);
                }
            }

            //处理状态未知
            //            unKnownInterfaceFinances = eisInterfaceFinanceDao
            //                .getByStatus(EisInterfaceFinance.STATUS_UNKNOWN);
            //
            //            for (EisInterfaceFinance interfaceFinance : unKnownInterfaceFinances)
            //                sfHandler.handleRequest(interfaceFinance);

            SFHandler.clear();//清楚网单号缓存

        } catch (Exception e) {
            SFHandler.clear();//清楚网单号缓存
            logger.error("Les出入库后调用财务接口出现错误：", e);
        }

    }

  public boolean syncStockReserved() {

    //查询接口编码为sync_reserved_stock的数据
    EisInterfaceStatus interfaceStatus = eisInterfaceStatusService
        .getByInterfaceCode(EisInterfaceStatus.INTERFACE_CODE_SYNC_RESERVED_STOCK);
    if (interfaceStatus == null) {
      logger.error("无" + EisInterfaceStatus.INTERFACE_CODE_SYNC_RESERVED_STOCK + "接口状态记录");
      return false;
    }

    //获取最后更新id
    Integer lastId = interfaceStatus.getLastId();
    InvReservedConfig config = new InvReservedConfig();
    config.setStatus(InvReservedConfig.STATUS_ON);
    //查询未已释放的数据
    ServiceResult<List<InvReservedConfig>> reservedConfigResult = this.stockReservedService
        .queryInvReservedConfigs(config);
    //无预留配置信息
    if (!reservedConfigResult.getSuccess() || reservedConfigResult.getResult() == null
        || reservedConfigResult.getResult().size() <= 0) {
      return true;
    }
    int pageSize = 1000;
    int pageIndex = 1;
    int startIndex;
    DefaultTransactionDefinition def = new DefaultTransactionDefinition();
    def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
    ServiceResult<List<InvStockBatch>> stockBatchResult;
    List<InvStockBatch> batchList;

    while (true) {
      startIndex = (pageIndex - 1) * pageSize;
      stockBatchResult = stockService.queryInvStockBatchList(lastId, startIndex, pageSize);
      batchList = stockBatchResult.getResult();
      if (batchList.size() <= 0) {
        logger
            .info("syncStockReserved: 总共" + batchList.size() + "条, pageIndex:" + pageIndex);
        break;
      }
      moveInvReservedRecord(batchList, def, interfaceStatus, reservedConfigResult.getResult());
      pageIndex++;
    }

    return true;
  }

  private void moveInvReservedRecord(List<InvStockBatch> batchList,
      DefaultTransactionDefinition def,
      EisInterfaceStatus interfaceStatus, List<InvReservedConfig> configs) {
    TransactionStatus status;
    int error = 0;
    for (InvStockBatch entry : batchList) {
      status = transactionManagerEis.getTransaction(def);
      try {
        ServiceResult<Boolean> result = null;
        if (entry.getBilltype() != null
            //1.采购入库； 2.调拨入库
            && (entry.getBilltype().equals(InvStockInOut.IN_PURCHASE_TYPE)
            || entry.getBilltype().equals(InvStockInOut.IN_TRANSFER_TYPE))
            && entry.getStockQty() > 0) {
          //暂时只处理采购入库
          result = this.stockReservedService.stockReservedToRelease(entry, configs);
          logger.debug(entry.getRefno() + "," + result.getMessage());
        }
        if (result != null && !result.getSuccess()) {
          error++;
        }
        updateLastBatchId(interfaceStatus, entry);

        transactionManagerEis.commit(status);
      } catch (Exception e) {
        e.printStackTrace();
        transactionManagerEis.rollback(status);
      }
    }
    logger.info("moveInvReservedRecord: 预留总共" + batchList.size() + "条， 出错" + error + "条");

  }

  private void updateLastBatchId(EisInterfaceStatus interfaceStatus, InvStockBatch entry) {
    interfaceStatus.setLastId(entry.getId());
    interfaceStatus.setUpdateTime(new Date());
    if (interfaceStatus.getLastId() > 0) {
      eisInterfaceStatusService.update(interfaceStatus);
    }
  }
}
