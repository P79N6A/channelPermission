package com.haier.stock.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.haier.stock.model.InvStockInOut;
import com.haier.stock.model.StockAgeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.eis.model.BusinessException;
import com.haier.shop.model.ItemBase;
import com.haier.stock.model.InvStockAge;
import com.haier.stock.model.InvStockAge.StockAgeData;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.StockAgeWapped;
import com.haier.stock.service.StockAgeService;
import com.haier.stock.service.StockInvStockAgeService;
import com.haier.stock.service.StockInvStockChannelService;


@Service
public class StockCenterStockAgeServiceImpl implements StockAgeService {

	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
			.getLogger(StockCenterStockAgeServiceImpl.class);

	@Autowired
	private StockInvStockAgeService stockInvStockAgeService;
	@Autowired
	private StockInvStockChannelService stockInvStockChannelService;
	@Autowired
	private StockAgeModel stockAgeModel;

	public ServiceResult<List<StockAgeWapped>> getStockAgeList(
			PagerInfo pagerInfo, Map<String, Object> params) {
		ServiceResult<List<StockAgeWapped>> rt = new ServiceResult<List<StockAgeWapped>>();
		ServiceResult<List<InvStockAge>> result = getStockAgeList1(pagerInfo,
				params);
		if (!result.getSuccess()) {
			log.error("stockService Error:" + result.getMessage());
			rt.setSuccess(false);
			rt.setMessage("获取库龄信息失败,库存服务器返回错误:" + result.getMessage());
		} else {
			List<InvStockAge> stockAges = result.getResult();
			List<StockAgeWapped> wappedAges = wappedStockAges(stockAges);
			for (StockAgeWapped ageWapped : wappedAges) {

				StockAgeData totalAgeData = ageWapped.getStockAge().new StockAgeData();
				int totalQuantity = 0;
				BigDecimal totalValue = new BigDecimal("0.0");
				totalAgeData.setAge(-1005);

				for (StockAgeData ageData : ageWapped.getAgeDatas()) {
					BigDecimal value = ageData.getValue();
					value.setScale(2);
					value = value.divide(new BigDecimal(10000),
							BigDecimal.ROUND_HALF_UP);// 金额万元
					ageData.setValue(value);
					totalQuantity += ageData.getStockQuantity();
					totalValue = totalValue.add(ageData.getValue());
				}
				totalAgeData.setStockQuantity(totalQuantity);
				totalAgeData.setValue(totalValue);
				ageWapped.getAgeDatas().add(0, totalAgeData);
			}
			rt.setResult(wappedAges);
		}
		rt.setPager(result.getPager());
		return rt;
	}

	public ServiceResult<List<InvStockAge>> getStockAgeList1(
			PagerInfo pagerInfo, Map<String, Object> params) {
		ServiceResult<List<InvStockAge>> result = new ServiceResult<List<InvStockAge>>();
		try {
			params.put("start",pagerInfo.getStart());
			params.put("size",pagerInfo.getPageSize());
			int count = stockInvStockAgeService.getCount(params);
			result.setResult(stockInvStockAgeService.getStockAgeList(params));
			pagerInfo.setRowsCount(count);
			result.setPager(pagerInfo);
		} catch (BusinessException e) {
			result.setSuccess(false);
			result.setMessage("无法获取库龄记录:" + e.getMessage());
		} catch (Exception e) {
			log.error("获取库龄时发生未知异常：" + e);
			result.setSuccess(false);
			result.setMessage("获取库龄信时发生未知异常" + e.getMessage());
		}
		return result;
	}

	/**
	 * 按报表要求包装数据
	 * 
	 * @param stockAges
	 * @return
	 */
	private List<StockAgeWapped> wappedStockAges(List<InvStockAge> stockAges) {
		List<StockAgeWapped> stockAgeWappeds = new ArrayList<StockAgeWapped>();
		for (InvStockAge invStockAge : stockAges) {
			StockAgeWapped ageWapped = new StockAgeWapped(invStockAge);
			ageWapped.wappenAgeDatas();
			stockAgeWappeds.add(ageWapped);
		}
		return stockAgeWappeds;
	}

	@Override
	public ServiceResult<List<InvStockChannel>> getChannels() {
		ServiceResult<List<InvStockChannel>> result = new ServiceResult<List<InvStockChannel>>();
		try {
			result.setResult(stockInvStockChannelService.getAll());
		} catch (Exception e) {
			log.error("获取渠道出现未知异常：", e);
			result.setSuccess(false);
			result.setError("获取渠道出现未知异常:", e.getMessage());
		}
		return result;
	}
	public ServiceResult<Integer> stockInOutRecord(List<InvStockInOut> invStockInOuts){
		ServiceResult<Integer> result = new ServiceResult<Integer>();
		if (invStockInOuts == null) {
			result.setSuccess(true);
			result.setResult(0);
			return result;
		}

		try {
			result.setResult(stockAgeModel.stockInOutsRecord(invStockInOuts));
		} catch (Exception e) {
			log.error("记录出入库记录发生未知异常：", e);
			result.setSuccess(false);
			result.setMessage("记录出入库记录发生未知异常:" + e.getMessage());
		}
		return result;
	}
	

    public ServiceResult<Integer> stockInOutRecord(InvStockInOut invStockInOut) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        if (invStockInOut == null) {
            result.setSuccess(true);
            result.setResult(0);
            return result;
        }

        try {
            result.setResult(stockAgeModel.stockInOutRecord(invStockInOut));
        } catch (Exception e) {
            log.error("记录出入库记录发生未知异常：", e);
            result.setSuccess(false);
            result.setMessage("记录出入库记录发生未知异常:" + e.getMessage());
        }
        return result;
    }

    @Override
    public ServiceResult<Integer> updateMtlInfoForStockAge(ItemBase base) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            int cnt = stockAgeModel.updateMtlInfoForStockAge(base);
            result.setResult(cnt);
        } catch (Throwable e) {
            result.setSuccess(false);
            result.setMessage("更新库齡表的物料相关信息(商品名称，品类，产品组，品牌)时出现未知异常");
            log.error("更新库齡表的物料相关信息(商品名称，品类，产品组，品牌)时出现未知异常：", e);
        }
        return result;
    }

}
