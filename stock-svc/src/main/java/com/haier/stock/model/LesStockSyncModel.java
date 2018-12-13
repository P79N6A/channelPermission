package com.haier.stock.model;
import com.haier.purchase.data.service.PurchaseItemService;
import com.haier.purchase.data.service.PurchaseT2OrderQueryService;
import com.haier.stock.eai.getbominfofromlestoehaier.ZLESDCMS24;
import com.haier.stock.service.InvTransferLineService;
import com.haier.stock.service.StockService;
import com.haier.stock.services.StockCommonServiceImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.ws.Holder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.eis.model.EisInterfaceStatus;
import com.haier.eis.model.LesStockItem;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.service.EisInterfaceStatusService;
import com.haier.eis.service.EisStockTrans2ExternalService;
import com.haier.eis.service.LesStockItemService;
import com.haier.eis.service.LesStockTransQueueService;
import com.haier.stock.canceltidan.JdeAndLesLoseValidityResponse;
import com.haier.stock.createscordertoles.OutputType;
import com.haier.stock.eai.transaccountcheckingfromcbstoles.GetKUCUNInfoFromLESToEHAIERResponseStockQty;
import com.haier.stock.eai.transaccountcheckingfromcbstoles.GetKUCUNInfoFromLESToEHAIERResponseStockTrans;
import com.haier.stock.service.StockCommonService;
import com.haier.stock.service.TransferLineService;
import com.haier.stock.services.Helper.EAIHandler;

@Service
public class LesStockSyncModel {
	@Autowired
	private EAIHandler eaiHandler;
	@Autowired
	private EisInterfaceStatusService eisInterfaceStatusService;
	@Autowired
	private DataSourceTransactionManager dataSourceTransactionManager;
	@Autowired
	private EisStockTrans2ExternalService    eisStockTrans2ExternalService;
	@Autowired
	private LesStockTransQueueService        LesStockTransQueueService;
	@Autowired
	private StockCommonServiceImpl stockCommonService;
	@Autowired
	private LesStockItemService              LesStockItemService;
	@Autowired
	private TransferLineService transferLineService;
	@Autowired
	private LesStockTransQueueService        lesStockTransQueueDao;
	@Autowired
	private StockService stockService;

	@Autowired
	private InvTransferLineService invTransferLineService;
	@Autowired
	private PurchaseItemService purchaseItemService;
	@Autowired
	private PurchaseT2OrderQueryService purchaseT2OrderQueryService;

	
	 private Logger              logger   = LoggerFactory.getLogger(LesStockSyncModel.class);
	 
	 public Boolean doInventoryTransFromLesNew() {
		 long begin = System.currentTimeMillis();

	        EisInterfaceStatus eisInterfaceStatus = getInterfaceStatus(EisInterfaceStatus.INTERFACE_CODE_GET_STOCK_TRANS_FROM_LES);
	        if (eisInterfaceStatus == null) {
	            logger.error("无" + EisInterfaceStatus.INTERFACE_CODE_GET_STOCK_TRANS_FROM_LES
	                         + "接口状态记录");
	            throw new BusinessException(
	                "无" + EisInterfaceStatus.INTERFACE_CODE_GET_STOCK_TRANS_FROM_LES + "接口状态记录");
	        }

	        Date startTime = eisInterfaceStatus.getLastTime();//上次同步记录的结束时间
	        Date endTime = DateUtil.add(eisInterfaceStatus.getNow(), Calendar.MINUTE, -5);//现在时间

	        //        logger.info("startTime："
	        //                    + DateUtil.format(eisInterfaceStatus.getLastTime(), "yyyy-MM-dd HH:mm:ss")
	        //                    + "，数据库时间："
	        //                    + DateUtil.format(eisInterfaceStatus.getNow(), "yyyy-MM-dd HH:mm:ss")
	        //                    + "，endTime:"
	        //                    + DateUtil.format(
	        //                        DateUtil.add(eisInterfaceStatus.getNow(), Calendar.MINUTE, -5),
	        //                        "yyyy-MM-dd HH:mm:ss"));

	        if (startTime.after(endTime)) {
	            logger.info("开始时间晚于结束时间，不需要处理");
	            return true;
	        }

	        //        Date maxDate = DateUtil.add(startTime, Calendar.HOUR_OF_DAY, 6);
	        //LES限制不可以抓取超过10分钟数据，CBS逻辑修改
	        Date maxDate = DateUtil.add(startTime, Calendar.MINUTE, 9);
	        if (maxDate.before(endTime)) {
	            //            logger.info("超过6个小时，结束时间修订为" + DateUtil.format(maxDate, "yyyy-MM-dd HH:mm:ss"));
	            logger.info("超过10分钟，结束时间修订为" + DateUtil.format(maxDate, "yyyy-MM-dd HH:mm:ss"));
	            endTime = maxDate;
	        }

	        String dateBegin = DateUtil.format(startTime, "yyyy-MM-dd");
	        String dateEnd = DateUtil.format(endTime, "yyyy-MM-dd");
	        String timeBegin = DateUtil.format(startTime, "HH:mm:ss");
	        String timeEnd = DateUtil.format(endTime, "HH:mm:ss");

	        String secType = "WD";//商城

	        Holder<String> flagHolder = new Holder<String>();
	        flagHolder.value = "-1";
	        Holder<String> messageHolder = new Holder<String>();
	        Holder<List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans>> responseStockTransHolder = new Holder<List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans>>();
	        Holder<List<GetKUCUNInfoFromLESToEHAIERResponseStockQty>> responseStockQtysHolder = new Holder<List<GetKUCUNInfoFromLESToEHAIERResponseStockQty>>();

	        long les_time_begin = System.currentTimeMillis();
	        logger.info("开始向LES请求数据，时间范围" + dateBegin + " " + timeBegin + "^" + dateEnd + " " + timeEnd);
	       
	        Integer batchId = eaiHandler.getInventoryTranFromLes(dateBegin, dateEnd, timeBegin,
	            timeEnd, secType, flagHolder, messageHolder, responseStockTransHolder,
	            responseStockQtysHolder);
	        if (!"0".equals(flagHolder.value)) {
	            logger.info("向LES请求WA库存记录失败");
	            eisInterfaceStatus.setUpdateTime(DateUtil.currentDateTime());
	            updateEisInterfaceStatus(eisInterfaceStatus);
	            return false;
	        }
	        try {
	            List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans> responseStockTrans = responseStockTransHolder.value == null ? new ArrayList<GetKUCUNInfoFromLESToEHAIERResponseStockTrans>()
	                : responseStockTransHolder.value;
	            List<GetKUCUNInfoFromLESToEHAIERResponseStockQty> responseStockQtys = responseStockQtysHolder.value == null ? new ArrayList<GetKUCUNInfoFromLESToEHAIERResponseStockQty>()
	                : responseStockQtysHolder.value;
	            logger.info("向LES请求WA库存记录完成，共 " + responseStockTrans.size() + " 条出入库记录,"
	                        + responseStockQtys.size() + " 条库存变化记录需要处理。用时："
	                        + (System.currentTimeMillis() - les_time_begin) + "ms");

	            long les_parse_time = System.currentTimeMillis();
	            List<LesStockItem> stockItems = parseLesInvStockQtys(batchId, responseStockQtys);
	            List<LesStockTransQueue> stockTransQueues = parseLesInvTrans(batchId,
	                responseStockTrans);
	            logger.info("LES库存记录解析完成，用时：" + (System.currentTimeMillis() - les_parse_time) + "ms");

	            sortStockTransQueuesByBillTime(stockTransQueues);//按交易时间排序

	            DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	            def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	            TransactionStatus status = dataSourceTransactionManager.getTransaction(def);

	            try {
	                saveStockItems(stockItems);
	                saveStockTransQueues(stockTransQueues);
	                //更新最后时间
	                eisInterfaceStatus.setLastTime(endTime);
	                updateEisInterfaceStatus(eisInterfaceStatus);
	                dataSourceTransactionManager.commit(status);
	            } catch (Exception e) {
	                dataSourceTransactionManager.rollback(status);
	                logger.error("保存LES自有库存失败：", e);
	                throw new BusinessException("保存LES自有库存信息失败");
	            }

	            logger.info("处理LES自有库存信息完成，批次标识 " + batchId + ",共处理 " + responseStockTrans.size()
	                        + " 条出入库记录," + responseStockQtys.size() + " 条库存变化记录，用时："
	                        + (System.currentTimeMillis() - begin) + "ms");
	            return true;
	        } catch (Exception e) {
	            logger.error("处理LES出入库信息错误：", e);
	            return false;
	        }
	        
	 }

	 private EisInterfaceStatus getInterfaceStatus(String interfaceCode) {
	        return eisInterfaceStatusService.getByInterfaceCode(interfaceCode);
	    }
	 private void updateEisInterfaceStatus(EisInterfaceStatus eisInterfaceStatus) {
	        try {
	            eisInterfaceStatusService.update(eisInterfaceStatus);
	        } catch (Exception e) {
	            logger.error("更新接口状态信息错误：", e);
	        }
	    }
	 private void saveStockItems(List<LesStockItem> stockItems) {
	        int m = 0;
	        for (LesStockItem stockItem : stockItems) {
	            String scode = stockItem.getSCode();
	            InvSection section = stockCommonService.getSectionByCode(scode).getResult();
	            if (section != null
	                && InvSection.SECTION_PROPERTY_XT.equalsIgnoreCase(section.getSectionProperty())) {
	                //库位为协同仓，则不处理
	                continue;
	            }

	            LesStockItemService.insertItem(stockItem);
	            m++;
	            if (m % 100 == 0 || m == stockItems.size())
	                logger.info("正在保存LES库存变化数据，已完成" + m + "条");
	        }
	    }
	 
	 /**
	    * 解析LES库存变化数据
	    * @param batchId
	    * @param responseStockQtys
	    * @return
	    */
	    private List<LesStockItem> parseLesInvStockQtys(Integer batchId,
	                                                    List<GetKUCUNInfoFromLESToEHAIERResponseStockQty> responseStockQtys) {
	        List<LesStockItem> stockItems = new ArrayList<LesStockItem>();

	        for (GetKUCUNInfoFromLESToEHAIERResponseStockQty responseStockQty : responseStockQtys) {

	            LesStockItem stockItem = new LesStockItem();
	            stockItem.setData(JsonUtil.toJson(responseStockQty));
	            stockItem.setLesStockSyncsId(batchId);
	            Date now = DateUtil.currentDateTime();
	            stockItem.setVersionCode(UUID.randomUUID().toString());
	            stockItem.setSCode(responseStockQty.getLGORT());
	            stockItem.setSku(responseStockQty.getMATNR());
	            stockItem.setStock(responseStockQty.getLFIMG() == null ? null : responseStockQty
	                .getLFIMG().intValue());
	            stockItem.setEaitime(now.getTime() / 1000);
	            stockItem.setAddTime((int) (now.getTime() / 1000));
	            stockItem.setStatus(LesStockItem.STATUS_RECEIVED);

	            int type;
	            type = 0;
	            String charg = responseStockQty.getCHARG();
	            try {
	                type = Integer.parseInt(charg);
	            } catch (Exception e) {
	                type = -1;
	            }
	            stockItem.setType(type);

	            String msg = validLesStockItem(stockItem);
	            if (msg != null) {
	                stockItem.setStatus(LesStockItem.STATUS_ERROR);
	                stockItem.setErrorMessage(msg);
	            }
	            stockItem.setChannelCode("WA");
	            stockItems.add(stockItem);
	        }
	        return stockItems;
	    }
	    
	    private String validLesStockItem(LesStockItem stockItem) {
	        if (StringUtil.isEmpty(stockItem.getSCode()))
	            return "库位为空";
	        if (StringUtil.isEmpty(stockItem.getSku()))
	            return "sku为空";
	        if (stockItem.getType() == null || stockItem.getType() == -1)
	            return "库存类型错误";
	        if (stockItem.getStock() == null || stockItem.getStock() < 0)
	            return "库存数量错误";
	        return null;
	    }
	    
	    private List<LesStockTransQueue> parseLesInvTrans(Integer batchId,
                List<GetKUCUNInfoFromLESToEHAIERResponseStockTrans> responseStockTrans) {
		List<LesStockTransQueue> stockTransQueues = new ArrayList<LesStockTransQueue>();

		for (GetKUCUNInfoFromLESToEHAIERResponseStockTrans responseStockTran : responseStockTrans) {
			LesStockTransQueue stockTransQueue = new LesStockTransQueue();
			stockTransQueue.setLesBatchId(batchId);
			stockTransQueue.setChannelCode(LesStockTransQueue.CHANNEL_WA);
			stockTransQueue.setAddTime(DateUtil.currentDateTime());
			stockTransQueue.setVersionCode(UUID.randomUUID().toString());
			try {
				stockTransQueue.setData(JsonUtil.toJson(responseStockTran));

				String sku = responseStockTran.getMATNR();
				stockTransQueue.setSku(sku);

				String sec_code = responseStockTran.getLGORT();
				stockTransQueue.setSecCode(sec_code);

				String corderSn = responseStockTran.getBSTNK();
				stockTransQueue.setCorderSn(corderSn);

				String outping = responseStockTran.getMBLNR();
				stockTransQueue.setOutping(outping);

				String d = responseStockTran.getCPUDT();// 输入日期
				String t = responseStockTran.getCPUTM();// 输入时间
				Date billTime = DateUtil.parse(d + " " + t, "yyyy-MM-dd HH:mm:ss");
				stockTransQueue.setBillTime(billTime);

				String billType = responseStockTran.getAUART();
				stockTransQueue.setBillType(billType);

				// 数量
				BigDecimal quantity = responseStockTran.getLFIMG();
				if (quantity != null)
					stockTransQueue.setQuantity(quantity.intValue());

				String partNo = responseStockTran.getMBLNR();// 物料凭证号
				String seqNo = responseStockTran.getZEILE();// 序号
				if (StringUtil.isEmpty(partNo) || StringUtil.isEmpty(seqNo)) {
					throw new BusinessException("LES物料凭证号或序号为空");
				}
				String billNo = partNo + seqNo;
				stockTransQueue.setZeile(seqNo);
				stockTransQueue.setLesBillNo(billNo);

				String mark = responseStockTran.getSHKZG();
				stockTransQueue.setMark(mark);

				stockTransQueue.setMatkl(responseStockTran.getMATKL());

				stockTransQueue.setKunnrSaleTo(responseStockTran.getKUNNRSALETO());

				stockTransQueue.setKunnrSendTo(responseStockTran.getKUNNRSENDTO());

				stockTransQueue.setTknum(responseStockTran.getTKNUM());

				stockTransQueue.setBwart(responseStockTran.getBWART());

				stockTransQueue.setCharg(responseStockTran.getCHARG());

				stockTransQueue.setReserve1(responseStockTran.getRESERVE1());
				stockTransQueue.setReserve2(responseStockTran.getRESERVE2());
				stockTransQueue.setBstkd(responseStockTran.getBSTKD());

				String msg = validLesStockTransQueue(stockTransQueue);
				if (msg != null)
					throw new BusinessException(msg);
				//2018-9-5校验订单是否是采购或者调拨
				//如果是采购和调拨则校验是否存在于3W-CBS系统 如果不存在则存入表中不处理
				Integer status = null;
				//如果是采购
				if (InventoryBusinessTypes.IN_PURCHASE.getCode().equals(billType)) {
					//普通采购 VOM回传单号为85开头D结尾
					//2018-09-10 按照旧系统 可能有SI单号或者PC单号
					//2.CBS采购，根据SI单或者85DN单号获取关联的采购PO单获取渠道
					//85D单号去掉D
					//PC6000190000008,统帅金立PC单
					Integer purchaseT2 = null;
					String pCOrderSn = corderSn.matches("8.+(D.*)?")
							? corderSn.substring(0, corderSn.length() - 1)
							: corderSn.matches("(SI).+") ? corderSn
							: corderSn.matches("(PC).+") ? corderSn : null;
					if (pCOrderSn != null) {
						purchaseT2 = purchaseT2OrderQueryService.getByOrderId(pCOrderSn);
					}
					//3PL采购
					int purchaseItem = purchaseItemService.getByPoItemNo(corderSn);
					//如果存在
					if (purchaseT2 > 0 || purchaseItem > 0) {
						status = LesStockTransQueue.STATUS_UNDONE;
						//不存在
					} else {
						status = LesStockTransQueue.STATUS_NO;
					}
					//如果是调拨
				} else if (InventoryBusinessTypes.OUT_TRANSFER.getCode().equals(billType)
						|| InventoryBusinessTypes.IN_TRANSFER.getCode().equals(billType)){
					//查询是否存在
					int invTransferLine = invTransferLineService.getByLineNum(corderSn);
					//如果存在
					if (invTransferLine > 0) {
						status = LesStockTransQueue.STATUS_UNDONE;
						//不存在
					} else {
						status = LesStockTransQueue.STATUS_NO;
					}
					//其他情况
				} else {
					status = LesStockTransQueue.STATUS_UNDONE;
				}
				stockTransQueue.setStatus(status);
			} catch (Exception e) {
				stockTransQueue.setStatus(LesStockTransQueue.STATUS_ERROR);
				stockTransQueue.setErrorMessage(e.getMessage());
			}
			stockTransQueues.add(stockTransQueue);
		}

		return stockTransQueues;
	}
	    
	    private void saveStockTransQueues(List<LesStockTransQueue> stockTransQueues) {
	        int m = 0;
	        for (LesStockTransQueue stockTransQueue : stockTransQueues) {
	            String scode = stockTransQueue.getSecCode();
	            InvSection section = stockCommonService.getSectionByCode(scode).getResult();
	            if (section != null
	                && InvSection.SECTION_PROPERTY_XT.equalsIgnoreCase(section.getSectionProperty())) {
	                //库位为协同仓，则插入eis_stock_trans_2_external中
	                if ("JDXT".equalsIgnoreCase(section.getChannelCode())) {
	                    stockTransQueue.setMerchantCode("JINGDONG");//京东协同仓
	                } else {
	                    stockTransQueue.setMerchantCode("YIXUN");//易迅协同仓
	                }
	                saveStockTrans2External(stockTransQueue);
	                continue;
	            }

	            saveStockTransQueue(stockTransQueue);
	            m++;
	            if (m % 100 == 0 || m == stockTransQueues.size())
	                logger.info("正在保存LES WA出入库记录，已完成" + m + "条");
	        }
	        
	    }
	    
	    private void saveStockTrans2External(LesStockTransQueue stockTransQueue) {
	        String lesBillNo = stockTransQueue.getLesBillNo();
	        if (!StringUtil.isEmpty(lesBillNo)) {
	            LesStockTransQueue transQueue2 = eisStockTrans2ExternalService.getByLesBillNo(lesBillNo);
	            if (transQueue2 != null) {
	                logger.info("eis_stock_trans_2_external中记录已经存在，不再插入，lesBillNo：" + lesBillNo);
	                return;
	            }
	        }
	        eisStockTrans2ExternalService.insert(stockTransQueue);
	    }
	    /**
	     * 判断LES库存记录是否已经存在
	     * @param stockTransQueue
	     * @return TRUE：存在；FALSE：不存在
	     */
	    private boolean lesStockTransQueueExists(LesStockTransQueue stockTransQueue) {
	        String lesBillNo = stockTransQueue.getLesBillNo();
	        if (!StringUtil.isEmpty(lesBillNo)) {
	        	LesStockTransQueue stockTransQueue2 = LesStockTransQueueService.getByLesBillNo(lesBillNo);
	            if (stockTransQueue2 != null) {
	                logger.info("LES库存记录已经存在，不再插入，lesBillNo：" + lesBillNo);
	                return true;
	            }
	        }
	        return false;
	    }
	    
	    private void sortStockTransQueuesByBillTime(List<LesStockTransQueue> stockTransQueues) {
	        Collections.sort(stockTransQueues, new Comparator<LesStockTransQueue>() {

	            @Override
	            public int compare(LesStockTransQueue o1, LesStockTransQueue o2) {
	                return o1.getStatus() == LesStockTransQueue.STATUS_ERROR ? -1
	                    : (o2.getStatus() == LesStockTransQueue.STATUS_ERROR ? 1 : o1.getBillTime()
	                        .compareTo(o2.getBillTime()));
	            }
	        });
	    }
	    private String validLesStockTransQueue(LesStockTransQueue stockTransQueue) {
	        if (StringUtil.isEmpty(stockTransQueue.getSku()))
	            return "物料编号为空";
	        if (StringUtil.isEmpty(stockTransQueue.getSecCode()))
	            return "库位为空";
	        //        if (StringUtil.isEmpty(stockTransQueue.getCorderSn()))
	        //            return "单据号为空";
	        if (StringUtil.isEmpty(stockTransQueue.getLesBillNo()))
	            return "LES单据号为空";
	        if (stockTransQueue.getAddTime() == null)
	            return "交易时间错误";
	        //        if (StringUtil.isEmpty(stockTransQueue.getBillType()))
	        //            return "单据类型为空";
	        if (stockTransQueue.getQuantity() == null || stockTransQueue.getQuantity() < 0)
	            return "数量不正确";
	        return null;
	    }
	    private void saveStockTransQueue(LesStockTransQueue stockTransQueue) {
	        if (lesStockTransQueueExists(stockTransQueue))
	            //LES库存记录已经存在,不插入
	            return;
	        LesStockTransQueueService.insert(stockTransQueue);
	    }
	    
	    public void seteisStockTrans2ExternalService(EisStockTrans2ExternalService eisStockTrans2ExternalService) {
	        this.eisStockTrans2ExternalService = eisStockTrans2ExternalService;
	    }
	    
	    /**
	     * 取消调拨单到LES
	     * @param lineNum
	     * @param lesNum
	     * @return
	     */
	    public boolean cancelTransferLine2Les(String lineNum, String lesNum) {
	    	//TODO 把这里补充完整
	        JdeAndLesLoseValidityResponse.Out out = eaiHandler.cancelTransferLineToLes(lineNum, lesNum);
	        if (out == null) {
	            throw new BusinessException("LES返回空");
	        }
	        if (!"S".equalsIgnoreCase(out.getFLAG())) {
	            throw new BusinessException(out.getMESSAGE());
	        }
	        return true;
	    }
	    
	    /**
	     * Job：读取状态为30待传LES的调拨网单同步到LES
	     */
	    public void syncTransferLinesToLes() {
	        ServiceResult<List<InvTransferLine>> rs = transferLineService
	            .getTransferLineByLineStatus(InvTransferLine.LINE_STATUS_LES);
	        if (!rs.getSuccess()) {
	            logger.error("获取待同步LES调拨单列表失败：" + rs.getMessage());
	            return;
	        }
	        List<InvTransferLine> transferLinesWaitForSyncToLes = rs.getResult();

	        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
	        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
	        TransactionStatus status;

	        for (InvTransferLine transferLine : transferLinesWaitForSyncToLes) {
	            String secFrom = transferLine.getSecFrom();
	            String secTo = transferLine.getSecTo();
	            InvWarehouse warehouse1 = getWarehouse(secFrom);
	            if (warehouse1 == null) {
	                logger.error("同步调拨单到LES时出现异常（" + transferLine.getLineNum() + "），未获取到库位对应的仓库信息,库位："
	                             + secFrom);
	                continue;
	            }
	            transferLine.setSecFrom(warehouse1.getWhCode() + InvSection.CHANNEL_CODE_WA);
	            String transferFrom = warehouse1.getCenterCode();
	            InvWarehouse warehouse2 = getWarehouse(secTo);
	            if (warehouse2 == null) {
	                logger.error("同步调拨单到LES时出现异常（" + transferLine.getLineNum() + "），未获取到库位对应的仓库信息,库位："
	                             + secTo);
	                continue;
	            }
	            transferLine.setSecTo(warehouse2.getWhCode() + InvSection.CHANNEL_CODE_WA);
	            if (transferLine.getTransferReason() != null
	                && transferLine.getTransferReason().equals(InvTransferLine.TRANSFER_REASON_XN)) {
	                try {
	                    boolean isSuc = innerTransferSyncLesStockTrans(transferLine);
	                } catch (Exception e) {
	                    logger.info("记录虚拟调拨入库/出库记录出现错误" + e.getMessage());
	                }
	                continue;
	            }

	            String transferTo = warehouse2.getLesAccepter();
	            OutputType outPut = eaiHandler.createTransferLineToLes(transferLine, transferFrom,
	                transferTo);

	            if ("1".equalsIgnoreCase(outPut.getFLAG())) {//成功
	                String lessOrdersn = outPut.getVBELN();
	                transferLine.setLesNum(lessOrdersn);
	                transferLine.setLineStatus(InvTransferLine.LINE_STATUS_STORE_OUT);
	                ServiceResult<Integer> result = transferLineService
	                    .updateTransferLineAfterSyncToLes(transferLine, DateUtil.currentDateTime(),
	                        "开单完成，LES单号:" + lessOrdersn);
	                if (!result.getSuccess())
	                    logger.error("更新调拨单（" + transferLine.getLineNum() + "）失败："
	                                 + result.getMessage());
	            } else {
	                logger.error("同步调拨单到LES时出现异常（" + transferLine.getLineNum() + "），LES接口返回失败:"
	                             + outPut.getFLAG() + " " + outPut.getMESSAGE());
	            }
	        }

	        logger.info("SyncTransferLinesToLes Done! Count " + transferLinesWaitForSyncToLes.size());
	    }
	    
	    
	    private InvWarehouse getWarehouse(String secCode) {
	        if (StringUtil.isEmpty(secCode))
	            return null;
	        String wh_code = secCode.substring(0, 2);
	        ServiceResult<InvWarehouse> rs = stockCommonService.getWarehouse(wh_code);
	        if (!rs.getSuccess()) {
	            logger.error("通过库存服务获取仓库信息失败:" + rs.getMessage());
	            throw new BusinessException("通过库存服务获取仓库信息失败！");
	        }
	        return rs.getResult();
	    }
	    
	    private boolean innerTransferSyncLesStockTrans(InvTransferLine transferLine) {
	        this.lesStockTransQueueDao.insert(createLesStockTransQueue(transferLine,
	            InvStockInOut.OUT_TRANSFER_TYPE, InventoryBusinessTypes.OUT_TRANSFER.getMark()));
	        logger.info("虚拟调拨[出库记录]: 调拨单号：" + transferLine.getLineNum());
	        this.lesStockTransQueueDao.insert(createLesStockTransQueue(transferLine,
	            InvStockInOut.IN_TRANSFER_TYPE, InventoryBusinessTypes.IN_TRANSFER.getMark()));
	        logger.info("虚拟调拨[入库记录]： 调拨单号：" + transferLine.getLineNum());
	        //更新调拨单状态 
	        ServiceResult<Boolean> result = transferLineService.updateLineStatusByLineId(
	            transferLine.getLineId(), InvTransferLine.LINE_STATUS_STORE_OUT);
	        logger.info("同步虚拟调拨单,更新调拨单状态为待出库lineId[" + transferLine.getLineId() + "]"
	                    + (result.getSuccess() ? "成功" : "失败"));
	        return true;
	    }
	    
	    /**
	     * 虚拟调拨标识
	     */
	    private static final String XN_MARK = "XN";
	    private LesStockTransQueue createLesStockTransQueue(InvTransferLine transferLine,
                String billType, String mark) {
			LesStockTransQueue lesStockTransQueue = new LesStockTransQueue();
			lesStockTransQueue.setSku(transferLine.getItemCode());
			lesStockTransQueue
			.setSecCode(InvStockInOut.IN_TRANSFER_TYPE.equals(billType) ? transferLine.getSecTo()
			: transferLine.getSecFrom());
			lesStockTransQueue.setCorderSn(transferLine.getLineNum());
			lesStockTransQueue.setChannelCode(LesStockTransQueue.CHANNEL_WA);
			lesStockTransQueue.setOutping("");
			lesStockTransQueue.setBillTime(new Date());
			lesStockTransQueue.setBillType(billType);
			lesStockTransQueue.setQuantity(transferLine.getTransferQty());
			lesStockTransQueue.setLesBillNo(transferLine.getLineNum());
			lesStockTransQueue.setMark(mark);
			lesStockTransQueue.setCharg(InvSection.W10);//良品
			lesStockTransQueue.setStatus(0);
			lesStockTransQueue.setVersionCode(UUID.randomUUID().toString());
			lesStockTransQueue.setAddTime(new Date());
			lesStockTransQueue.setLesBatchId(0);
			lesStockTransQueue.setData(XN_MARK);
			return lesStockTransQueue;
		}
	    
	    
	    /**
	     * Job：传递用于录入费用的调拨单信息到LES
	     */
	    public void transferLineToLesForFeeInput() {
	        ServiceResult<List<InvTransferLine>> rs = transferLineService
	            .getTransferLineByLineStatus(InvTransferLine.LINE_STATUS_CONFIRM);
	        if (!rs.getSuccess()) {
	            logger.error("用transferLineService获取待同步LES调拨单列表失败：" + rs.getMessage());
	            return;
	        }
	        List<InvTransferLine> lines = rs.getResult();
	        if (lines == null || lines.size() == 0) {
	            if (logger.isInfoEnabled()) {
	                logger.info("没有用于录入费用的调拨单需要传递到LES,Job退出");
	            }
	            return;
	        }
	        for (InvTransferLine line : lines) {
	            String secFrom = line.getSecFrom();
	            String secTo = line.getSecTo();
	            line.setSecFrom(secFrom.substring(0, 2) + InvSection.CHANNEL_CODE_WA);
	            line.setSecTo(secTo.substring(0, 2) + InvSection.CHANNEL_CODE_WA);
	            InvWarehouse warehouseFrom = this.getWarehouse(line.getSecFrom());
	            if (warehouseFrom == null) {
	                logger.error("未获取到库位对应的仓库信息,库位：" + line.getSecFrom());
	                continue;
	            }
	            String kunag = warehouseFrom.getCenterCode();//调出中心代码

	            InvWarehouse warehouseTo = this.getWarehouse(line.getSecTo());
	            if (warehouseTo == null) {
	                logger.error("未获取到库位对应的仓库信息,库位：" + line.getSecTo());
	                continue;
	            }
	            String kunnr = warehouseTo.getCenterCode();//调入中心代码
	            boolean eaiRet = eaiHandler.transferLineToLesForFeeInput(line, kunag, kunnr);
	            if (!eaiRet) {
	                logger
	                    .error("单号为"
	                           + line.getLineNum()
	                           + "的调拨单在进行传递用于录入费用的调拨单信息到LES时执行失败，请查看eaiHandler的transferLineToLesForFeeInput方法日志，下次Job会重新执行该操作");
	                continue;
	            }
	            ServiceResult<Integer> ret = transferLineService
	                .updateTransferLineAfterToLesForFeeInput(line);
	            if (!ret.getSuccess()) {
	                logger.error("传递调拨单号为" + line.getLineNum() + "的用于录入费用的调拨单信息到LES后更新调拨单状态失败："
	                             + ret.getMessage());
	            }
	        }
	    }

	public Boolean doMachineSetsSyncFromLes() {
		Date now = DateUtil.currentDate();
		String inputDate = DateUtil.format(now, "yyyy-MM-dd");
		List<InvMachineSet> machineSets = getBomInfoFromLes(inputDate);
		int errCount = 0;
		for (InvMachineSet machineSet : machineSets) {
			ServiceResult<Boolean> rs = stockService.machineSetSync(machineSet);
			if (!rs.getSuccess()) {
				logger.error("库存服务同步套机记录错误，" + rs.getMessage());
				errCount++;
			}
		}
		logger.info("同步套机数据完成，可处理记录 " + machineSets.size() + ",错误记录 " + errCount);
		return true;
	}

	private List<InvMachineSet> getBomInfoFromLes(String inputDate) {
		List<InvMachineSet> retList = new ArrayList<InvMachineSet>();

		List<ZLESDCMS24> bomInfos = eaiHandler.getBomInfoFromLes(inputDate);
		if (bomInfos == null) {
			logger.warn("从Les后去套机数据null，输入参数：" + inputDate);
			return retList;
		}

		Map<ZLESDCMS24, String> errMap = new HashMap<ZLESDCMS24, String>();

		for (ZLESDCMS24 bomInfo : bomInfos) {

			String mainSku = bomInfo.getMATNR();//父sku
			if (StringUtil.isEmpty(mainSku)) {
				errMap.put(bomInfo, "main_sku为空");
				continue;
			}

			String subSku = bomInfo.getIDNRK();//子sku
			if (StringUtil.isEmpty(subSku)) {
				errMap.put(bomInfo, "sub_sku为空");
				continue;
			}

			String factoryCode = bomInfo.getWERKS();//工厂

			String bomNum = bomInfo.getPOSNR();//bom 项目号
			if (StringUtil.isEmpty(bomNum)) {
				errMap.put(bomInfo, "bom项目号为空");
				continue;
			}

			BigDecimal menge = bomInfo.getMENGE();//组件数量

			String maktx1 = bomInfo.getMAKTX1();
			String maktx2 = bomInfo.getMAKTX2();

			String stlnr = bomInfo.getSTLNR();

			InvMachineSet machineSet = new InvMachineSet();
			machineSet.setMainSku(mainSku);
			machineSet.setSubSku(subSku);
			machineSet.setFactoryCode(factoryCode);
			machineSet.setStlnr(stlnr);
			machineSet.setPosnr(bomNum);
			machineSet.setMenge(menge);
			machineSet.setMaktx1(maktx1);
			machineSet.setMaktx2(maktx2);
			retList.add(machineSet);
		}

		if (errMap.size() > 0) {
			for (Map.Entry<ZLESDCMS24, String> entry : errMap.entrySet()) {
				ZLESDCMS24 zlesdcms24 = entry.getKey();
				String msg = entry.getValue();
				logger.warn("套机数据错误，" + msg + "。" + JsonUtil.toJson(zlesdcms24));
			}
		}

		return retList;
	}
}

	
