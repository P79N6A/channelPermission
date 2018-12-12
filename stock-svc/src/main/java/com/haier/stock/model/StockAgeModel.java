package com.haier.stock.model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.ItemAttribute;
import com.haier.shop.model.ItemBase;
import com.haier.stock.service.InvStockAgeLogService;
import com.haier.stock.service.InvStockAgeService;
import com.haier.stock.service.InvStockChannelService;
import com.haier.stock.service.InvStockInOutService;
import com.haier.stock.service.InvStockLockDesService;
import com.haier.stock.service.StockInvSectionService;
import com.haier.stock.service.StockInvStockAgeService;
import com.haier.stock.service.StockInvStockLockService;

@Service
public class StockAgeModel {
	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager.getLogger(StockAgeModel.class);
	@Autowired
	private InvStockInOutService invStockInOutService;
	@Autowired
	private InvStockChannelService invStockChannelService;
	@Autowired
	private StockInvStockAgeService stockInvStockAgeService;
	@Autowired
	private InvStockLockDesService invStockLockDesService;
	@Autowired
	private ItemServiceImpl itemService;
	@Autowired
	private StockInvStockLockService stockInvStockLockService;
	@Autowired
	private StockInvSectionService stockInvSectionService;
	@Autowired
	private InvStockAgeService invStockAgeService;
	@Autowired
	private InvStockAgeLogService invStockAgeLogService;
	@Autowired
	
	
	public static final String LOGGER_MARK = "[Stock.StockAgeModel]";

	public Integer stockInOutsRecord(List<InvStockInOut> invStockInOuts) {
		int count = 0;
		for (InvStockInOut invStockInOut : invStockInOuts) {
			count += stockInOutRecord(invStockInOut);
		}
		return count;
	}

	public Integer stockInOutRecord(InvStockInOut stockInOut) {
		int c = 0;
		String channelCode = stockInOut.getChannelCode();
		try {
			int recordCount = invStockInOutService.getCountByBillNo(stockInOut.getBillNo());
			if (recordCount <= 0) {
				c = invStockInOutService.insert(stockInOut);
				if (c > 0) {
					stockAgeRecord(stockInOut);// 如果对应的库龄信息不存在，则新增库龄信息
				}
			}
			return c;
		} catch (Exception e) {
			log.warn("处理出入库记录时发生未知错误（" + stockInOut.getSku() + "," + stockInOut.getSecCode() + "," + channelCode + "）：",
					e);
			throw new BusinessException("处理出入库记录时发生未知异常：" + e.getMessage());
		}
	}

	private void stockAgeRecord(InvStockInOut invStockInOut) {

		String channelCode = invStockInOut.getChannelCode();
		if (StringUtil.isEmpty(channelCode))
			throw new BusinessException("channel_code：不能为空");

		List<InvStockAge> stockAges = stockInvStockAgeService.getBySkuAndSCode(invStockInOut.getSecCode(),
				invStockInOut.getSku());

		if (stockAges.size() == 0) {// 库龄记录不存在
			// WA不添加库龄记录
			if ("WA".equalsIgnoreCase(channelCode))
				return;
			// 全渠道新增记录
			if (InvStockChannel.ALL.equalsIgnoreCase(channelCode) || channelCode.contains(","))
				addAllChannelStockAges(invStockInOut);
			else
				addStockAge(invStockInOut, DateUtil.truncateTime(invStockInOut.getBillTime()));
		} else {
			Date earliest = stockAges.get(0).getDate();
			for (InvStockAge stockAge : stockAges) {
				if (stockAge.getDate().before(earliest))
					earliest = stockAge.getDate();
			}
			// 如果stockAges中的清算日期不同则需要进行修正
			boolean needUpdate = earliest.before(stockAges.get(0).getDate());
			if (invStockInOut.getBillTime().before(earliest)) {
				// 判断出库时间和最早的清算日期
				earliest = DateUtil.truncateTime(invStockInOut.getBillTime());
				needUpdate = true;
			}

			if (!InvStockChannel.ALL.equalsIgnoreCase(channelCode) && !"WA".equalsIgnoreCase(channelCode)) {
				String[] channelCodes = channelCode.split(",");
				Set<String> channelCodesList = new HashSet<String>();
				Collections.addAll(channelCodesList, channelCodes);
				for (String code : channelCodesList) {
					code = code.toUpperCase();
					if ("WA".equalsIgnoreCase(code))
						continue;
					boolean isExist = false;
					for (InvStockAge stockAge : stockAges) {
						if (code.equalsIgnoreCase(stockAge.getChannelCode())) {
							isExist = true;
							break;
						}
					}

					if (!isExist) {// 对应渠道的记录不存在，新增记录
						invStockInOut.setChannelCode(code);
						addStockAge(invStockInOut, earliest);
					}
				}
			}
			if (needUpdate)
				// 修正清算日期
				updateStockAgeClearDate(stockAges, earliest);
		}

	}

	private void addAllChannelStockAges(InvStockInOut invStockInOut) {
		InvStockAge stockAge = new InvStockAge();
		stockAge.setSecCode(invStockInOut.getSecCode());
		stockAge.setItemId(invStockInOut.getItemId());
		stockAge.setDate(DateUtil.truncateTime(invStockInOut.getBillTime()));
		stockAge.setSku(invStockInOut.getSku());
		stockAge.setPrice(new BigDecimal(0.00));
		stockAge.setWaStockQty(0);
		wrappedStockAge(stockAge);
		try {
			List<InvStockChannel> channels = invStockChannelService.getAll();
			for (InvStockChannel channel : channels) {
				stockAge.setChannelCode(channel.getChannelCode());
				stockAge.setChannelName(channel.getName());
				stockInvStockAgeService.insert(stockAge);
			}
		} catch (Exception e) {
			throw new BusinessException("新增库龄失败，" + e.getMessage());
		}
	}

	private void wrappedStockAge(InvStockAge stockAge) {
		this.setDefaultMtlInfoForStockAge(stockAge);
		if (!StringUtil.isEmpty(stockAge.getSku(), true)) {
			ItemBase mtl = this.getViewMtlBaseAllByMaterialCode(stockAge.getSku());
			if (mtl != null) {
				this.setMtlInfoForStockAge(stockAge, mtl);
			} else {
				ItemBase newMtl = this.getEmptyViewMtlBaseAll(stockAge.getSku());
				this.saveViewMtlBaseAll(newMtl);
			}
		}
		stockAge.setAgeData(null);
		stockAge.setSecName(getSecName(stockAge.getSecCode()));

		Date now = DateUtil.currentDateTime();
		stockAge.setCreateTime(now);
		stockAge.setUpdateTime(now);
	}

	private void setDefaultMtlInfoForStockAge(InvStockAge stockAge) {
		stockAge.setProductName("");
		stockAge.setProductTypeName("");
		stockAge.setBrand("");
		stockAge.setProductGroupName("");
	}

	private ItemBase getViewMtlBaseAllByMaterialCode(String sku) {
		ServiceResult<ItemBase> rs = itemService.getItemBaseBySku(sku);
		if (!rs.getSuccess())
			throw new BusinessException("向ItemService请求ViewMtlBaseAll数据时发生错误：" + rs.getMessage());
		return rs.getResult();
	}

	private void saveViewMtlBaseAll(ItemBase newMtl) {
		ServiceResult<Boolean> insertRet = itemService.saveItemBase(newMtl);
		if (!insertRet.getSuccess()) {
			throw new BusinessException("向ItemService插入ViewMtlBaseAll数据时发生错误：" + insertRet.getMessage());
		}
	}

	private InvStockAge addStockAge(InvStockInOut invStockInOut, Date clearDate) {
		InvStockAge stockAge = new InvStockAge();
		stockAge.setSecCode(invStockInOut.getSecCode());
		stockAge.setItemId(invStockInOut.getItemId());
		stockAge.setDate(clearDate);
		stockAge.setSku(invStockInOut.getSku());
		stockAge.setPrice(new BigDecimal(0.00));
		stockAge.setChannelCode(invStockInOut.getChannelCode());
		stockAge.setWaStockQty(0);
		stockAge.setChannelName(getChannelName(stockAge.getChannelCode()));
		wrappedStockAge(stockAge);
		stockInvStockAgeService.insert(stockAge);
		return stockAge;
	}

	private String getChannelName(String channel_code) {
		if ("SHH".equalsIgnoreCase(channel_code))
			return "社会化渠道";
		InvStockChannel channel = invStockChannelService.getByCode(channel_code);
		if (channel == null)
			return null;
		else
			return channel.getName();
	}

	private void updateStockAgeClearDate(List<InvStockAge> stockAges, Date clearDate) {
		for (InvStockAge stockAge : stockAges) {
			updateStockAgeClearDate(stockAge, clearDate);
		}
	}

	private void updateStockAgeClearDate(InvStockAge stockAge, Date clearDate) {
		stockAge.setUpdateTime(new Date());
		stockAge.setDate(DateUtil.truncateTime(clearDate));
		stockInvStockAgeService.updateDate(stockAge);
	}

	private void setMtlInfoForStockAge(InvStockAge stockAge, ItemBase mtl) {
		stockAge.setProductName(mtl.getMaterialDescription());
		if (!StringUtil.isEmpty(mtl.getDepartment(), true)) {
			ItemAttribute department = this.getHmValueSetByValueSetIdAndValue("ProductGroup", mtl.getDepartment());
			if (department != null) {
				stockAge.setProductTypeName(department.getCbsCategory());
				stockAge.setProductGroupName(department.getValueMeaning());
			}
		}
		if (!StringUtil.isEmpty(mtl.getProBand(), true)) {
			ItemAttribute brand = this.getHmValueSetByValueSetIdAndValue("Brand", mtl.getProBand());
			if (brand != null) {
				stockAge.setBrand(brand.getValueMeaning());
			}
		}
	}

	private ItemAttribute getHmValueSetByValueSetIdAndValue(String valueSetId, String value) {
		ServiceResult<ItemAttribute> rs = itemService.getItemAttributeByValueSetIdAndValue(valueSetId, value);
		if (!rs.getSuccess())
			throw new BusinessException("向ItemService请求HmValueSet数据时发生错误：" + rs.getMessage());
		return rs.getResult();
	}

	private ItemBase getEmptyViewMtlBaseAll(String sku) {
		Date initDate;
		try {
			initDate = new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01");
		} catch (ParseException e) {
			initDate = new Date();
		}
		ItemBase newMtl = new ItemBase();
		newMtl.setCreated(initDate);
		newMtl.setLastUpd(initDate);
		newMtl.setMaterialCode(sku);
		return newMtl;
	}

	private String getSecName(String secCode) {
		InvSection invSection = stockInvSectionService.getBySecCode(secCode);
		return invSection == null ? "" : invSection.getSecName();
	}

	public void calculateStockAgeTimely() {
		List<InvStockInOut> stockInOuts = invStockInOutService.getByAgeStatus(0);
		Date today = DateUtil.truncateTime(stockInvStockAgeService.getNow());
		Map<String, List<InvStockInOut>> inOutMap = new HashMap<String, List<InvStockInOut>>();
		for (InvStockInOut stockInOut : stockInOuts) {
			Date billTime = stockInOut.getBillTime();
			if (billTime.before(today)) {// 交易时间早于今天，不统计
				invStockInOutService.updateAgeStatus(stockInOut.getId(), InvStockInOut.AGE_STATUS_DOWN,
						InvStockInOut.AGE_STATUS_WAIT);
				log.info("时时计算(stockInOut-id=" + stockInOut.getId() + ")完成:交易时间早于今天,不进行计算");
				continue;
			} else if (billTime.after(DateUtil.add(today, Calendar.DAY_OF_YEAR, 1))) { // 交易日期晚于今天，不统计
				log.info("时时计算(stockInOut-id=" + stockInOut.getId() + ")完成:交易时间晚于今天,不进行计算");
				continue;
			}
			String sku = stockInOut.getSku();
			String secCode = stockInOut.getSecCode();
			if (StringUtil.isEmpty(sku) || StringUtil.isEmpty(secCode) || secCode.length() < 2) {
				invStockInOutService.updateAgeStatus(stockInOut.getId(), stockInOut.getAgeStatus(), 1);
				log.info("时时计算库龄(stockInOut-id=" + stockInOut.getId() + ")完成:数据错误,不进行计算");
				continue;
			}
			String key = sku + "#" + secCode;
			List<InvStockInOut> list = inOutMap.get(key);
			if (list == null) {
				list = new ArrayList<InvStockInOut>();
				inOutMap.put(key, list);
			}
			list.add(stockInOut);
		}

		for (Map.Entry<String, List<InvStockInOut>> stringListEntry : inOutMap.entrySet()) {
			String key = stringListEntry.getKey();
			String[] strs = key.split("#");
			String sku = strs[0];
			String secCode = strs[1];
			List<InvStockAge> stockAges = stockInvStockAgeService.getBySkuAndSCode(secCode, sku);
			if (stockAges.size() <= 0) {
				log.error("库龄数据不存在，无法统计。sku=" + sku + "，secCode=" + secCode);
				continue;
			}
			Date clearDate = stockAges.get(0).getDate();
			// 如果库龄数据的清算日期早于今天，重新清算库龄
			if (clearDate.before(today)) {
				log.info("库龄（" + sku + "," + secCode + "）没有完成清算，当前清算日期为“" + DateUtil.format(clearDate, "yyyy-MM-dd")
						+ "”，不支持实时计算");
				continue;
			}
			StockAgeHandler ageHandler = new StockAgeHandler(sku, secCode);
			ageHandler.buildUpAgeDataListByStockAge(stockAges);
			for (InvStockInOut stockInOut : stringListEntry.getValue()) {
				calculateStockAge(ageHandler, stockInOut, today, clearDate);
				ageHandler.buildInvStockAges(stockAges);
				int effectNum = invStockInOutService.updateAgeStatus(stockInOut.getId(), InvStockInOut.AGE_STATUS_DOWN,
						InvStockInOut.AGE_STATUS_WAIT);
				if (effectNum < 1) {
					log.error("实时统计库龄出现并发情况（stock_in_out id=" + stockInOut.getId() + "）");
					continue;
				}

				// Map<String, InvStockAge> channelStockAges =
				// convertToChannelAgeData(stockAges);
				for (InvStockAge stockAge : stockAges) {
					// assertCurrentStockFrozenOutOrder(stockAge, channelStockAges,
					// ageHandler.getDataList());
					stockInvStockAgeService.update(stockAge);
				}
			}
		}
		syncAssertFrozenQty();
	}

	private void syncAssertFrozenQty() {
		invStockLockDesService.delete();
		List<InvStockLock> stockLockList = stockInvStockLockService.getProcessStockLock();
		for (InvStockLock stockLock : stockLockList) {
			String secCode = stockLock.getSecCode();
			String sku = stockLock.getSku();
			List<InvStockAge> stockAges = stockInvStockAgeService
					.getBySkuAndSCode(secCode.substring(0, 2) + InvSection.CHANNEL_CODE_WA, sku);
			if (stockAges.size() <= 0) {
				log.error("占用数据计算不存在，无法统计。sku=" + sku + "，secCode=" + secCode);
				continue;
			}
			StockAgeHandler ageHandler = new StockAgeHandler(sku, secCode);
			// 区分渠道库存和共享库存
			ageHandler.buildUpAgeDataListByStockAge(stockAges);
			Map<String, InvStockAge> channelStockAges = convertToChannelAgeData(stockAges);
			// for (InvStockAge stockAge : stockAges) {
			assertQty(stockLock, channelStockAges, ageHandler.getDataList());
			// }
		}
	}

	private boolean assertQty(InvStockLock stockLock, Map<String, InvStockAge> channelStockAges,
			List<StockAgeHandler.Data> dataList) {
		InvStockLockDes lockDes = new InvStockLockDes();
		// 虚拟渠道库存,冻结的数量,wa库位,渠道
		lockDes.setSku(stockLock.getSku());
		lockDes.setRefno(stockLock.getRefno());
		lockDes.setSecCode(stockLock.getSecCode().substring(0, 2) + InvSection.CHANNEL_CODE_WA);
		lockDes.setChannel(stockLock.getChannel());
		lockDes.setLockQty(stockLock.getLockQty());
		lockDes.setLockId(stockLock.getId());
		invStockLockDesService.update(lockDes);
		boolean isWa = stockLock.getSecCode().endsWith(InvSection.CHANNEL_CODE_WA);
		if (!isWa) {
			invStockLockDesService.insert(lockDes);
		} else {
			List<InvStockLockDes> lockDesList = invStockLockDesService.queryWaStockQtyByChannel(stockLock.getSku(),
					stockLock.getSecCode(), stockLock.getChannel());
			Map<String, Integer> lockQtyMap = this.convertToChannelLockQty(lockDesList);
			pullOutLockedData(lockQtyMap, dataList);
			Integer lockQty = lockQtyMap.get(stockLock.getChannel());
			// 取出当前渠道的库龄信息
			InvStockAge channelAge = channelStockAges.get(stockLock.getChannel());
			// 分配共享库的冻结渠道， 3分钟运行试图将库存和库龄数量一致
			Integer waQty = channelAge == null ? 0 : channelAge.getWaStockQty();
			if (lockQty != null) {
				waQty = waQty >= lockQty ? waQty - lockQty : 0;
			}

			int restQty = 0;
			lockDes.setLockQty(waQty >= stockLock.getLockQty() ? stockLock.getLockQty() : waQty);
			if (waQty < stockLock.getLockQty()) {
				restQty = stockLock.getLockQty() - waQty;
			}
			if (waQty > 0) {
				invStockLockDesService.insert(lockDes);
			}
			// 找其他渠道库存最老的占用
			List<StockAgeHandler.Data> datas = assertOlderWaQty(restQty, stockLock.getChannel(), dataList);
			for (StockAgeHandler.Data data : datas) {
				lockDes.setChannel(data.channel);
				lockDes.setLockQty(data.waQty);
				invStockLockDesService.insert(lockDes);
			}

		}
		return true;
	}

	private Map<String, Integer> convertToChannelLockQty(List<InvStockLockDes> lockDesList) {
		Map<String, Integer> stockLockQtyMap = new HashMap<String, Integer>();
		for (InvStockLockDes lockDes : lockDesList) {
			if (!stockLockQtyMap.containsKey(lockDes.getChannel())) {
				stockLockQtyMap.put(lockDes.getChannel(), lockDes.getLockQty());
			}
		}
		return stockLockQtyMap;
	}

	private void pullOutLockedData(Map<String, Integer> waLockQtyMap, List<StockAgeHandler.Data> dataList) {
		for (StockAgeHandler.Data data : dataList) {
			if (waLockQtyMap.get(data.channel) == null || waLockQtyMap.get(data.channel) == 0) {
				continue;
			}
			if (data.waQty <= 0) {
				continue;
			}
			int rest = data.waQty - waLockQtyMap.get(data.channel);
			if (rest >= 0) {
				// 渠道共享的多，没有被渠道占完，供以后的渠道占用
				// 渠道wa中赋值为剩下的
				data.waQty = data.waQty - waLockQtyMap.get(data.channel);
				// 分配完成
				waLockQtyMap.put(data.channel, 0);

			} else {
				// 渠道wa共享中数据不足，没有分配完成，继续
				waLockQtyMap.put(data.channel, rest > 0 ? rest : 0);
				// 当前渠道对应的此库存清空，继续分配
				data.waQty = 0;
			}

		}
	}

	private Map<String, InvStockAge> convertToChannelAgeData(List<InvStockAge> stockAges) {

		Map<String, InvStockAge> ageDataMap = new HashMap<String, InvStockAge>();
		for (InvStockAge age : stockAges) {
			if (!ageDataMap.containsKey(age.getChannelCode())) {
				ageDataMap.put(age.getChannelCode(), age);
			}
		}
		return ageDataMap;
	}

	private List<StockAgeHandler.Data> assertOlderWaQty(int restQty, String channel,
			List<StockAgeHandler.Data> dataList) {
		Collections.sort(dataList, new Comparator<StockAgeHandler.Data>() {

			@Override
			public int compare(StockAgeHandler.Data entry1, StockAgeHandler.Data entry2) {
				if (entry1 == null) {
					return 1;
				}
				if (entry2 == null) {
					return -1;
				}
				return entry1.age > entry2.age ? -1 : (entry1.age.equals(entry2.age) ? 0 : 1);
			}

		});
		List<StockAgeHandler.Data> assertChannelList = new ArrayList<StockAgeHandler.Data>();
		int qty = restQty;
		for (StockAgeHandler.Data entry : dataList) {
			if (qty <= 0) {
				break;
			}
			if (entry.waQty <= 0) {
				continue;
			}
			if (entry.channel.equals(channel)) {
				continue;
			}
			if (entry.waQty >= qty) {
				entry.waQty = qty;
			}
			qty -= entry.waQty;
			assertChannelList.add(entry);

		}
		return assertChannelList;
	}

	private int getDateDiff(Date beginDate, Date endDate) {
		int day = 0;
		while (beginDate.before(endDate)) {
			beginDate = DateUtil.add(beginDate, Calendar.DAY_OF_YEAR, 1);
			day++;
		}
		return day;
	}

	/**
	 * 根据出库记录增量计算库龄.按照先进先出原则分渠道进行计算.<br>
	 * 普通计算:<br>
	 * <ul>
	 * <li>mark = S(入库),增加库存,age = (clearDate - lastClearDate) Days
	 * <ul>
	 * <li>channel=WA,ALL;计入社会化(SSH)渠道</li>
	 * <li>channel!=WA,计入所属渠道</li>
	 * </ul>
	 * </li>
	 * <li>mark = H(出库),扣减库存.
	 * <ul>
	 * <li>使用共享(WA)库存,只扣减渠道共享库存(WA)</li>
	 * <li>使用锁定(!WA,如BJSC)库存,先扣减渠道内库存,再扣减其他渠道库存,会刷新渠道共享(WA)库存数量</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * 库龄平移:<br>
	 * 释放到共享:<br>
	 *
	 * @param ageHandler
	 *            handler of stockAge
	 * @param stockInOut
	 *            InvStockInOut
	 * @param clearDate
	 *            Current clear date
	 * @param lastClearDate
	 *            Last clear date
	 */
	private void calculateStockAge(StockAgeHandler ageHandler, InvStockInOut stockInOut, Date clearDate,
			Date lastClearDate) {
		String channel = stockInOut.getChannelCode().toUpperCase();
		String vSecCode = stockInOut.getVirtualSecCode().toUpperCase();
		String mark = stockInOut.getMark();
		int qty = stockInOut.getQuantity();
		boolean isWa = vSecCode.endsWith("WA");

		switch (stockInOut.getAgeType()) {
		case InvStockInOut.AGE_TYPE_SAMPLE:// 简单计算，扣减库存和增加库存
			if (!isWa) {
				// 根据虚拟库位确定渠道,存在关联的渠道和使用库存的库位不符的情况
				channel = vSecCode.substring(2, 4);
			}
			if ("DK".equalsIgnoreCase(channel)) {
				// DKH渠道对应的虚拟库位为{WH}Dk
				channel = "DKH";
			}
			if ("H".equalsIgnoreCase(mark)) {
				if (!InvStockChannel.ALL.equalsIgnoreCase(channel)) {
					// 渠道内扣减
					qty = ageHandler.deducted(channel, stockInOut.getQuantity(), isWa);
				}
				if (qty > 0) {
					// 渠道内扣减后仍然有剩余,再扣减其他渠道库存
					qty = ageHandler.deducted(qty, isWa);
				}
				if (qty > 0) {
					if (log.isDebugEnabled()) {
						log.debug(LOGGER_MARK + "[" + stockInOut.getSku() + "," + stockInOut.getSecCode() + ","
								+ channel + "," + stockInOut.getQuantity() + "],扣减后仍然有结余:" + qty);
					}
				}
			} else if ("S".equalsIgnoreCase(stockInOut.getMark())) {
				// 少数情况下,会存在关联的入库记录为共享渠道,这部分库存计入社会化渠道
				if ("WA".equals(channel)) {
					channel = "SHH";
					isWa = true;
				}
				ageHandler.increased(channel, getDateDiff(lastClearDate, clearDate), qty,
						isWa ? stockInOut.getQuantity() : 0);
			}
			break;
		case InvStockInOut.AGE_TYPE_PAN_OF_AGE:
			String[] strings = channel.split(",");
			if (strings.length < 2) {
				log.error("库龄平移,渠道配置错误");
				break;
			}
			String channelFrom = strings[1];
			String channelTo = strings[0];

			if (channelFrom.equals(channelTo) || "WA".equals(channelFrom)) {// 优先使用本渠道的库存
				ageHandler.panOfAgeSelf(channelTo, stockInOut.getQuantity());
			} else {
				int _qty = ageHandler.panOfAge(channelFrom, channelTo, stockInOut.getQuantity(), isWa);
				if (_qty > 0 && isWa)
					_qty = ageHandler.panOfAge(channelTo, _qty, true);
				if (_qty > 0)
					log.error("平移数据剩余");
			}
			break;
		case InvStockInOut.AGE_TYPE_ADD_WA_QTY:
			ageHandler.increasedWaQty(channel, stockInOut.getQuantity());
			break;
		default:
			log.error(LOGGER_MARK + "库龄出入库记录（id=" + stockInOut.getId() + "）的统计类型（" + stockInOut.getAgeType() + "）不正确");
			break;

		}

	}
	
	 /**
     * 根据出入库记录计算库龄数据,每天计算一次.<br/>
     * 增量计算,每条库龄数据记录上次计算的日期,下次计算时,只统计上次计算日期当今天的数据
     */
    public void calculateStockAgeDaily() {
        //按照secCode和sku分组获取库龄数据
        long stepTime = System.currentTimeMillis();
        List<InvStockAge> stockAgesGroupBySkuSecCode = invStockAgeService.getGroupBySecAndSku();
        log.info(LOGGER_MARK + "-[calculateStockAgeDaily]:按照sku和secCode获取库龄信息完成，共"
                 + stockAgesGroupBySkuSecCode.size() + "组数据需要处理，用时 "
                 + (System.currentTimeMillis() - stepTime) + "ms");

        Date today = DateUtil.truncateTime(invStockAgeService.getNow());//当前日期
        //每组库龄数据统一计算
        for (InvStockAge invStockAge : stockAgesGroupBySkuSecCode) {
            Date lastClearDate = invStockAge.getDate();
            if (!lastClearDate.before(today)) {
                //已经计算到今天,不再计算
                continue;
            }
            calculateStockAgeDaily(invStockAge.getSku(), invStockAge.getSecCode(), today,
                lastClearDate);
        }
    }
    
    /**
     * 库龄计算
     *
     * @param sku           物料
     * @param secCode       库位
     * @param today         当前日期
     * @param lastClearDate 上次清算的日期.
     * @return 清算到的日期, 一般情况等于当前日期
     */
    private Date calculateStockAgeDaily(String sku, String secCode, Date today,
                                        Date lastClearDate) {
        //获取库龄数据并构造库龄计算Handler
        List<InvStockAge> stockAges = invStockAgeService.getBySkuAndSCode(secCode, sku);
        List<InvStockAgeLog> ageLogs = invStockAgeLogService.getGroupByDate(sku, secCode,
            lastClearDate);
        StockAgeHandler handler = new StockAgeHandler(sku, secCode, ageLogs);

        //根据清算日期和当前日期获取出入库记录,并按照日期分组
        List<InvStockInOut> stockInOuts = invStockInOutService.getReference(sku, secCode, lastClearDate,
            today);
        Map<Date, Queue<InvStockInOut>> stockInOutPieces = cutUpStockInOutsByDate(stockInOuts);

        //从清算日期开始计算
        while (lastClearDate.before(today)) {
            Date clearDate = DateUtil.add(lastClearDate, Calendar.DAY_OF_YEAR, 1);
            //库龄加一
            handler.riseTheAge(1);

            //获取当天产生的出入库记录并逐条计算
            Queue<InvStockInOut> stockInOutPiece = stockInOutPieces.get(lastClearDate);
            if (stockInOutPiece != null) {
                while (!stockInOutPiece.isEmpty()) {
                    calculateStockAge(handler, stockInOutPiece.poll(), clearDate, lastClearDate);
                }
            }

            lastClearDate = clearDate;

            //更新库龄计算结构数据inv_stock_age_log
            List<InvStockAgeLog> stockAgeLogs = handler.buildInvStockAgeLogs(lastClearDate);
            invStockAgeLogService.deleteBySkuSecCode(secCode, sku, lastClearDate);
            if (stockAgeLogs.size() > 0) {
                invStockAgeLogService.insertList(stockAgeLogs);
            }

            if (log.isDebugEnabled()) {
                log.debug("(sku:" + sku + ",sec_code:" + secCode + ") 计算完成，日期："
                          + DateUtil.format(clearDate, "yyyy-MM-dd"));
            }
        }

        //更新库龄展示数据inv_stock_age
        handler.buildInvStockAges(stockAges);
        for (InvStockAge stockAge : stockAges) {
            stockAge.setDate(today);
            stockAge.refreshAgeDatas();
            invStockAgeService.update(stockAge);
        }
        log.info("(sku:" + sku + ",sec_code:" + secCode + ") 计算完成并更新结果，日期："
                 + DateUtil.format(lastClearDate, "yyyy-MM-dd"));
        return lastClearDate;
        
    }
    /**
     * 按照日期分组出入库记录
     *
     * @param stockInOuts list of stockInOuts
     * @return 分组后的出入库记录
     */
    private Map<Date, Queue<InvStockInOut>> cutUpStockInOutsByDate(List<InvStockInOut> stockInOuts) {
        Map<Date, Queue<InvStockInOut>> retMap = new HashMap<Date, Queue<InvStockInOut>>();
        if (stockInOuts.size() <= 0) {
            return retMap;
        }

        Date temp = DateUtil.truncateTime(stockInOuts.get(0).getBillTime());
        temp = DateUtil.add(temp, Calendar.DAY_OF_YEAR, 1);

        Queue<InvStockInOut> queue = new LinkedBlockingDeque<InvStockInOut>();
        for (InvStockInOut stockInOut : stockInOuts) {
            Date billTime = stockInOut.getBillTime();
            if (!billTime.before(temp)) {
                retMap.put(DateUtil.add(temp, Calendar.DAY_OF_YEAR, -1), queue);
                queue = new LinkedBlockingDeque<InvStockInOut>();
                temp = DateUtil.add(DateUtil.truncateTime(billTime), Calendar.DAY_OF_YEAR, 1);
            }
            queue.offer(stockInOut);
        }
        retMap.put(DateUtil.add(temp, Calendar.DAY_OF_YEAR, -1), queue);
        return retMap;
    }
}
