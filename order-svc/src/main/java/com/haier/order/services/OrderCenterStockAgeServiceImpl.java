package com.haier.order.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.haier.stock.model.InvStockAge;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.StockAgeWapped;
import com.haier.stock.service.StockInvStockAgeService;
import com.haier.stock.service.StockInvStockChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;

@Service
public class OrderCenterStockAgeServiceImpl {

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(OrderCenterStockAgeServiceImpl.class);

/*	@Autowired
    private InvStockAgeDao invStockAgeDao;*/

    @Autowired
    private StockInvStockAgeService stockInvStockAgeService;

    @Autowired
    private StockInvStockChannelService stockInvStockChannelService;

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

                InvStockAge.StockAgeData totalAgeData = ageWapped.getStockAge().new StockAgeData();
                int totalQuantity = 0;
                BigDecimal totalValue = new BigDecimal("0.0");
                totalAgeData.setAge(-1005);

                for (InvStockAge.StockAgeData ageData : ageWapped.getAgeDatas()) {
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

}
