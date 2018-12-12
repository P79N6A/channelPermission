package com.haier.stock.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.ItemBase;
import com.haier.shop.model.StockHolder;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvStockInOut;
import com.haier.stock.model.InvStockLock;
import com.haier.stock.model.InvStockTransaction;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.model.StockTransactionExist;
import com.haier.stock.service.StockAgeService;
import com.haier.stock.service.StockInvMachineSetService;
import com.haier.stock.service.StockInvSectionService;
import com.haier.stock.service.StockInvStockLockService;
import com.haier.stock.service.StockInvStockTransactionService;
import com.haier.stock.service.StockStockTransactionExistService;

/**
 * 库存交易model
 * Created by 钊 on 2014/4/1.
 * @param <T>
 */
@Service
public class StockTransactionModel<T> {
    private final static Logger logger   = LoggerFactory.getLogger(StockTransactionModel.class);
    private static final String LOG_MARK = "[Stock][StockTransactionModel] ";
    @Autowired
    private StockCenterItemServiceImplNew         itemService;
    @Autowired
    private StockServiceImpl        stockService;
    @Autowired
    private TransferLineServiceImpl transferLineService;
    @Autowired
    private StockInvStockTransactionService       stockInvStockTransactionService;
//    @Autowired
//    private DataSourceTransactionManager transactionManagerStock;
    @Autowired
    private StockInvMachineSetService             stockInvMachineSetService;
    @Autowired
    private StockInvStockLockService              stockInvStockLockService;
    @Autowired
    private StockInvSectionService<T>                stockInvSectionService;
    @Autowired
    private StockStockTransactionExistService     stockStockTransactionExistService;
    @Autowired
   private StockCenterStockAgeServiceImpl stockAgeService;

    /**
     * 新增库存交易记录
     * @param stockTransaction 库存交易
     * @return 是否成功
     */
//    @Transactional
    public boolean addStockTransaction(InvStockTransaction stockTransaction,
                                       Integer lesStockTransQueueId) {
        if (lesStockTransQueueId != null) {
            StockTransactionExist exist = stockStockTransactionExistService.getByLesStockTransQueueId(lesStockTransQueueId);
            if (exist != null) {
                logger.error("[addStockTransaction]les_stock_trans_queue表已执行过,lesStockTransQueueId="
                             + lesStockTransQueueId);
                return true;
            }
        }

        long startTime = System.currentTimeMillis();

        StringBuilder msg = new StringBuilder();
        //如果是套机，需要拆成子件计算库存
        List<StockHolder> parts = getPartsOfBOM(stockTransaction.getSku(),stockTransaction.getQuantity());
        //确定虚拟库位
        List<StockHolder> holders = new ArrayList<StockHolder>();
        //关联的库位信息
        InvSection section;
        String channel = stockTransaction.getChannelCode();
        String itemProperty = stockTransaction.getItemProperty();
        if (!InvSection.W10.equalsIgnoreCase(itemProperty)) {
            section = stockInvSectionService.getInvSection(stockTransaction.getExternalSecCode(),
                InvSection.CHANNEL_CODE_WA, itemProperty);
        } else {
            String stockChannel = "ALL".equalsIgnoreCase(channel) ? InvSection.CHANNEL_CODE_WA
                : channel;
            section = stockInvSectionService.getInvSection(stockTransaction.getExternalSecCode(),
                stockChannel, InvSection.W10);
        }
        if (section == null) {
            throw new BusinessException("根据LES库位和销售渠道获取的库位信息不存在");
        }

        Integer isRelease = stockTransaction.getIsRelease() == null ? 0
            : stockTransaction.getIsRelease();

        //入库和不需要释放占用库存的出库记录，根据渠道关联库位
        if ("S".equalsIgnoreCase(stockTransaction.getMark()) || !isRelease.equals(1)) {
            msg.append("|入库或不需要释放占用库存的出库").append("|根据渠道关联仓库信息（").append(section.getSecCode())
                .append("，").append(section.getChannelCode()).append("）确定CBS库位");
            for (StockHolder part : parts) {
                part.setSecCode(section.getSecCode());
                holders.add(part);
            }
        } else {
            msg.append("|出库");
            //如果需要释放库存，代表此出库单据曾经占用过库存，必须能关联上占用记录
            List<InvStockLock> locks = stockInvStockLockService.getNotReleasedByRefNo(stockTransaction.getCorderSn());
            if (locks.size() <= 0) {
                throw new BusinessException("占用记录不存在（" + stockTransaction.getSku() + "，"
                                            + stockTransaction.getCorderSn() + "）");
            }
            for (StockHolder part : parts) {
                int qty = part.getChangeQty();
                List<InvStockLock> locksBySkuWhCode = getLocksBySkuWhCode(locks, part.getSku(), section.getWhCode());
                for (InvStockLock lock : locksBySkuWhCode) {
                    if (qty <= 0) {
                        break;
                    }
                    int lockedQty = lock.getLockQty() - lock.getRealeaseQty();

                    if (qty >= lockedQty) {
                        holders
                            .add(StockHolder.newInstance(part.getSku(), lock.getSecCode(), lockedQty));
                        qty -= lockedQty;
                    } else {
                        holders.add(StockHolder.newInstance(part.getSku(), lock.getSecCode(), qty));
                        qty = 0;
                    }
                    msg.append("|根据占用记录（").append(lock.getSku()).append("，")
                        .append(lock.getSecCode()).append("，").append(lockedQty)
                        .append("）确定CBS库位，剩余数量-").append(qty);
                }
                if (qty > 0) {
                    throw new BusinessException("占用数量和出库数量不一致： " + qty);
                }
            }
        }

        List<InvStockTransaction> stockTransactions = new ArrayList<InvStockTransaction>();
        for (StockHolder holder : holders) {
            InvStockTransaction _stockTransaction = (InvStockTransaction) stockTransaction.clone();
            assert _stockTransaction != null;
            _stockTransaction.setSku(holder.getSku());
            _stockTransaction.setSecCode(holder.getSecCode());
            _stockTransaction.setQuantity(holder.getChangeQty());
            stockTransactions.add(_stockTransaction);
        }

        int qty = 0;
        if (stockTransactions.size() > 0) {
            qty = stockInvStockTransactionService.insertAll(stockTransactions);
            if (qty > 0 && lesStockTransQueueId != null) {
                try {
                    StockTransactionExist stockTransactionExist = new StockTransactionExist();
                    stockTransactionExist.setLesStockTransQueueId(lesStockTransQueueId);
                    stockStockTransactionExistService.insert(stockTransactionExist);
                } catch (Exception e) {
                    logger.error("[addStockTransaction]插入库存重复控制表失败,lesStockTransQueueId="
                                 + lesStockTransQueueId,
                        e);
                }
            }
            logger.info(LOG_MARK + "生成库存交易记录完成。ref_no = " + stockTransaction.getCorderSn()
                        + ",新增交易 = " + qty + ", message :" + msg.toString() + ",耗时 = "
                        + (System.currentTimeMillis() - startTime));
        } else {
            logger.error("不能生成库存交易实体,ref_no = " + stockTransaction.getCorderSn());
        }

        return qty > 0;
    }

    /**
     * 根据仓库编码和物料筛选占用记录
     * @param locks 占用记录列表
     * @param sku 物料
     * @param whCode 仓库编码
     * @return 筛选后的占用记录列表
     */
//    @Transactional
    private List<InvStockLock> getLocksBySkuWhCode(List<InvStockLock> locks, String sku,
                                                   String whCode) {
        List<InvStockLock> _locks = new ArrayList<InvStockLock>();
        for (InvStockLock lock : locks) {
            if (lock.getSku().equals(sku) && lock.getSecCode().substring(0, 2).equals(whCode)) {
                _locks.add(lock);
            }
        }
        return _locks;
    }

    /**
     * 获取套机的BOM关系
     * @param sku 物料
     * @param quantity 数量
     * @return BOM关系
     */
    @Transactional
    private List<StockHolder> getPartsOfBOM(String sku, int quantity) {
        List<StockHolder> holders = new ArrayList<StockHolder>();
        //拆分套机
        List<InvMachineSet> machineSets = stockInvMachineSetService.getByMainSku(sku);
        if (machineSets.size() <= 0) {
            holders.add(StockHolder.newInstance(sku, null, quantity));
        }
        for (InvMachineSet machineSet : machineSets) {
            holders.add(StockHolder.newInstance(machineSet.getSubSku(), null,
                machineSet.getMenge().intValue() * quantity));
        }
        return holders;
    }

    /**
     * 获取待处理关联业务的库存交易记录
     * @return 待处理关联业务的交易记录
     */
    public List<InvStockTransaction> getNotProcessBusiness() {
        return stockInvStockTransactionService.getNotProcessBusiness();
    }

    /**
     * 更新库存交易的关联业务处理状态
     * @param id 交易id
     * @param businessProcessStatus 处理状态
     * @return 更新条数
     */
    public boolean updateBusinessStatus(Integer id, Integer businessProcessStatus) {
        return stockInvStockTransactionService.updateBusinessProcessStatus(id, businessProcessStatus) > 0;
    }


    /**
     * 处理库存交易记录，生成库岭计算所需的出入库记录
     */
    public void processForGenerateStockAgeInOut() {
        while (true) {
            List<InvStockTransaction> stockTransactions = stockInvStockTransactionService.getByProcessStatus(InvStockTransaction.PROCESS_STATUS_UPDATE_STOCK_DOWN);
            if (stockTransactions.size() <= 0)
                break;
            for (InvStockTransaction stockTransaction : stockTransactions) {
                long startTime = System.currentTimeMillis();
                processForGenerateStockAgeInOut(stockTransaction);
                logger.info(LOG_MARK + "处理库存交易(id=" + stockTransaction.getId()
                            + "),生成库龄inv_stock_in_out,用时: "
                            + (System.currentTimeMillis() - startTime) + " ms");
            }
        }
    }

    private void processForGenerateStockAgeInOut(InvStockTransaction stockTransaction) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            int isDelay = stockTransaction.getIsDelay();
            if (isDelay == 1)
                isDelay = 2;
            int effectNum = stockInvStockTransactionService.updateProcessStatus(stockTransaction.getId(),
                InvStockTransaction.PROCESS_STATUS_BUILD_STOCK_AGE_DOWN,
                InvStockTransaction.PROCESS_STATUS_UPDATE_STOCK_DOWN, isDelay, "生成库龄计算出入库记录完成");
            if (effectNum < 1) {
                logger.info(
                    LOG_MARK + "生成 inv_stock_in_out 出现并发，不再处理，id=" + stockTransaction.getId());
//                transactionManagerStock.commit(status);
                return;
            }

            String itemProperties = stockTransaction.getItemProperty();
            if (!InvSection.W10.equalsIgnoreCase(itemProperties)) {//只有批次10的商品需要计算库龄
//                transactionManagerStock.commit(status);
                return;
            }

            //只有WA需要统计库龄
            if (!stockTransaction.getExternalSecCode().endsWith("WA")) {
//                transactionManagerStock.commit(status);
                return;
            }

            InventoryBusinessTypes businessTypes = InventoryBusinessTypes
                .getByCode(stockTransaction.getBillType());
            String channel = assertChannelForStockAge(stockTransaction);
            InvStockInOut stockInOut = buildStockAgeInOut(stockTransaction);
            stockInOut.setChannelCode(channel);
            stockInOut.setAgeStatus(0);
            stockInOut.setAgeType(InvStockInOut.AGE_TYPE_SAMPLE);
            stockInOut.setType(stockTransaction.getBillType());

            if (businessTypes == InventoryBusinessTypes.IN_TRANSFER
                || businessTypes == InventoryBusinessTypes.OUT_TRANSFER) {
                InvTransferLine transferLine = getTransferLine(stockTransaction.getCorderSn());
                if (transferLine == null) {
                    logger.error(LOG_MARK + "生成库龄inv_stock_in_out，调拨单："
                                 + stockTransaction.getCorderSn() + "不存在,不处理");
//                    transactionManagerStock.commit(status);
                    return;
                } else if (InvTransferLine.TRANSFER_REASON_XN
                    .equals(transferLine.getTransferReason())) {
                    if (InventoryBusinessTypes.OUT_TRANSFER == businessTypes) {
                        //调出库位
                        String channelFrom = transferLine.getChannelFrom();
                        String channelTo = transferLine.getChannelTo();
                        if (StringUtil.isEmpty(channelFrom) || StringUtil.isEmpty(channelTo)) {
                            channelFrom = transferLine.getSecFrom().substring(2, 4).toUpperCase();
                            channelTo = transferLine.getSecTo().substring(2, 4).toUpperCase();
                            if ("DK".equals(channelFrom))
                                channelFrom = "DKH";
                            if ("DK".equals(channelTo))
                                channelTo = "DKH";
                        }
                        stockInOut.setVirtualSecCode(transferLine.getSecFrom());
                        String channelCode;
                        if (InvSection.CHANNEL_CODE_WA.equalsIgnoreCase(channelTo)) {//锁定释放到共享
                            channelCode = channelFrom;
                            stockInOut.setMark("H");
                            stockInOut.setAgeType(InvStockInOut.AGE_TYPE_ADD_WA_QTY);
                        } else {
                            channelCode = channelTo + "," + channelFrom;
                            stockInOut.setMark("S");
                            stockInOut.setAgeType(InvStockInOut.AGE_TYPE_PAN_OF_AGE);
                        }
                        stockInOut.setChannelCode(channelCode);
                    } else {
//                        transactionManagerStock.commit(status);
                        return;
                    }
                }
            }

            ServiceResult<Integer> result = stockAgeService.stockInOutRecord(stockInOut);
            if (!result.getSuccess()){
            	 throw new BusinessException(result.getMessage());
            }
               
//            else
//                transactionManagerStock.commit(status);
        } catch (Exception e) {
//            transactionManagerStock.rollback(status);
            logger.error(
                LOG_MARK + "处理库存交易（id=" + stockTransaction.getId() + "），生成库龄inv_stock_in_out记录失败:",
                e);
            updateToDelay(stockTransaction.getId(), 1, "生成库龄出入库记录失败：" + e.getMessage());
        }
    }

    private void updateToDelay(Integer stockTransactionId, Integer isDelay, String message) {
        try {
        	stockInvStockTransactionService.updateToDelay(stockTransactionId, isDelay, message);
        } catch (Exception e) {
            logger.error(LOG_MARK + "更新库存交易为延后处理失败：", e);
        }
    }

    private final static String BRAND_LEADER = "089"; //统帅
    private final static String BRAND_SAMPO  = "019"; //新宝
    private final static String CHANNEL_SHH  = "SHH";

    private String assertChannelForStockAge(InvStockTransaction stockTransaction) {
        String channel = stockTransaction.getChannelCode();
        if (!StringUtil.isEmpty(channel) && !"WA".equals(channel.toUpperCase()))
            return channel;
        else if (InventoryBusinessTypes.OUT_TRANSFER == InventoryBusinessTypes
            .getByCode(stockTransaction.getBillType()))
            return channel;
        else if (InventoryBusinessTypes.OUT_SALE == InventoryBusinessTypes
            .getByCode(stockTransaction.getBillType()))
            return InvStockChannel.ALL;
        //如果是统帅和新宝品牌的物料，则指定到社会化渠道
        String sku = stockTransaction.getSku();
        ServiceResult<ItemBase> result = itemService.getItemBaseBySku(sku);
        ItemBase itemBase = result.getResult();
        if (!result.getSuccess())
            throw new BusinessException("获取item_base失败（sku=" + sku + "）：" + result.getMessage());
        else if (itemBase == null)
            throw new BusinessException("获取item_base失败（sku=" + sku + "）：item_base不存在");
        if (BRAND_LEADER.equalsIgnoreCase(itemBase.getProBand())
            || BRAND_SAMPO.equalsIgnoreCase(itemBase.getProBand()))
            return CHANNEL_SHH;
        return stockTransaction.getChannelCode();
    }

    private InvTransferLine getTransferLine(String lineNum) {
        ServiceResult<InvTransferLine> result = transferLineService
            .getInvTransferLineByLineNum(lineNum);
        if (!result.getSuccess())
            throw new BusinessException("获取调拨单失败：" + result.getMessage());
        return result.getResult();
    }

    /**
     * 生成库岭计算用的出入库记录
     * @param stockTransaction 库存交易记录
     * @return 库龄出入库记录
     */
    private InvStockInOut buildStockAgeInOut(InvStockTransaction stockTransaction) {
        InvStockInOut invStockInOut = new InvStockInOut();
        invStockInOut.setBillNo("CBS" + stockTransaction.getId());
        invStockInOut.setMark(stockTransaction.getMark());
        //sku and itemId
        invStockInOut.setSku(stockTransaction.getSku());
        invStockInOut.setItemId(0);
        //库位
        String sec_code = stockTransaction.getSecCode();
        invStockInOut.setVirtualSecCode(sec_code);
        InvSection section = stockInvSectionService.getBySecCode(sec_code);
        invStockInOut.setSecCode(section.getLesSecCode());
        //数量
        int quantity = stockTransaction.getQuantity();
        invStockInOut.setQuantity(quantity);
        //修改库龄交易时间为库存更新时间
        invStockInOut.setBillTime(stockTransaction.getLastProcessTime());
        String cbsBillNo = stockTransaction.getCorderSn();
        invStockInOut.setCbsBillNo(cbsBillNo);
        invStockInOut.setNote("");
        return invStockInOut;
    }
    /**
     * 处理库存交易记录，更新库存信息：计算库存，释放库存，占用库存等。
     */
    public void processForUpdateStock() {
        while (true) {
            List<InvStockTransaction> stockTransactions = stockInvStockTransactionService.getByProcessStatus(InvStockTransaction.PROCESS_STATUS_INIT);
            if (stockTransactions.size() <= 0) {
                break;
            }
            for (InvStockTransaction stockTransaction : stockTransactions) {
                processForUpdateStock(stockTransaction);
            }
        }
    }
    /**
     * 更新库存
     * @param stockTransaction 待处理的库存交易
     */
//    @Transactional
    private void processForUpdateStock(InvStockTransaction stockTransaction) {

        long startTime = System.currentTimeMillis();

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManagerStock.getTransaction(def);
        try {
            int isDelay = stockTransaction.getIsDelay();
            if (isDelay == 1)
                isDelay = 0;
            int effectNum = stockInvStockTransactionService.updateProcessStatus(stockTransaction.getId(),
                InvStockTransaction.PROCESS_STATUS_UPDATE_STOCK_DOWN,
                InvStockTransaction.PROCESS_STATUS_INIT, isDelay, "更新库存完成");
            if (effectNum < 1) {
//                transactionManagerStock.commit(status);
                return;
            }
            ServiceResult<Boolean> result = stockService.updateCbsStockByStockTransaction(stockTransaction);
            if (!result.getSuccess() || !result.getResult()) {
                throw new BusinessException(result.getMessage());
            } else {
//                transactionManagerStock.commit(status);
            }
            logger.info(LOG_MARK + "处理库存交易（id=" + stockTransaction.getId() + "），更新库存成功"
                        + (StringUtil.isEmpty(result.getMessage()) ? "。"
                            : "，remark " + result.getMessage())
                        + "|耗时:" + (System.currentTimeMillis() - startTime));
        } catch (Exception e) {
//            transactionManagerStock.rollback(status);
            if (e instanceof BusinessException) {
                logger.info(LOG_MARK + "处理库存交易（id=" + stockTransaction.getId() + "），更新库存失败:"
                            + e.getMessage());
            } else {
                logger.error(LOG_MARK + "处理库存交易（id=" + stockTransaction.getId() + "），更新库存失败:", e);
            }
            updateToDelay(stockTransaction.getId(), 1, "更新库存失败：" + e.getMessage());
        }

    }
    /**
     * 处理需要延后处理的库存出入库记录
     */
    public void processForDelay() {
        List<InvStockTransaction> stockTransactions = stockInvStockTransactionService.getByIsDelay(1);
        for (InvStockTransaction stockTransaction : stockTransactions) {
            Integer processStatus = stockTransaction.getProcessStatus();
            switch (processStatus) {
                case InvStockTransaction.PROCESS_STATUS_INIT:
                    processForUpdateStock(stockTransaction);
                    break;
                case InvStockTransaction.PROCESS_STATUS_UPDATE_STOCK_DOWN:
                    processForGenerateStockAgeInOut(stockTransaction);
                    break;
                default:
                    updateToDelay(stockTransaction.getId(), 2, "不需要处理");
                    break;
            }
        }
    }

}
