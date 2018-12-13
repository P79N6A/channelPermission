package com.haier.svc.api.controller.stock.mode;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.service.EisLesStockTransQueueService;
import com.haier.stock.model.InvSection;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvStockTransaction;
import com.haier.stock.service.StockCenterEISStockService;
import com.haier.stock.service.StockCommonService;
import com.haier.stock.service.StockInvStockTransactionService;
import com.haier.stock.service.StockTransactionWriteService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 钊 on 2014/12/23.
 */
@Service
public class StockTransModel {
	@Autowired
    private EisLesStockTransQueueService eisLesStockTransQueueService;
	@Autowired
    private StockCenterEISStockService eisStockService;
	@Autowired
    private StockCommonService stockCommonService;
	@Autowired
    private StockInvStockTransactionService stockInvStockTransactionService;
	@Autowired
    private StockTransactionWriteService stockTransactionWriteService;
     /**
      * 批量保存库存交易记录
      * @param listData
      */
    public void insertInvStockTransaction(List<InvStockTransaction> listData) {
         for (InvStockTransaction invStockTransaction : listData) {
             stockInvStockTransactionService.insertSelective(invStockTransaction);
        }
        
    }
    
    public void updateStockTransaction(Map<String, Object> params) {
    	stockTransactionWriteService.updateStockTransaction(params);
    }
    
    /**
     * 根据查询条件获取导入库存交易列表
     *
     * @param params
     * @return
     */
    public List<Map<String, Object>> findInvStockTransImportList(Map<String, Object> params) {
        return stockInvStockTransactionService.queryList(params);
    }

    /**
     * 获取延后处理的LESS出入库记录列表总数
     *
     * @return
     */
    public int findInvStockTransImportCount(Map<String, Object> params) {
        return stockInvStockTransactionService.getListRowCnt(params);
    }
    
    
    
    /**
     * 根据查询条件获取导入库存交易列表
     *
     * @param params
     * @return
     */
    public List<Map<String, Object>> findInvStockTransList(Map<String, Object> params) {
        return stockInvStockTransactionService.queryStockTransList(params);
    }

    /**
     * 获取延后处理的LESS出入库记录列表总数
     *
     * @return
     */
    public int findInvStockTransCount(Map<String, Object> params) {
        return stockInvStockTransactionService.getStockTransListRowCnt(params);
    }
    /**
     * 生成单据号
     * @param whCode
     * @param channelCode
     * @return
     */
    public String  getSectionByWhCodeAndChannelCode(String whCode, String channelCode, String itemProperty){
        ServiceResult<InvSection> result = stockCommonService.getByLesSecCodeAndChannelCode(whCode, channelCode, itemProperty);
        if (result.getSuccess()) {
            InvSection invSection=    result.getResult();
          return   invSection.getSecCode()  ;
        }
        
        return "";
    }
    /**
     * 获取延后处理的LESS出入库记录列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    public List<LesStockTransQueue> getDelayLessStockTrans(int page, int pageSize) {
        PagerInfo pagerInfo = new PagerInfo(pageSize, page);
        return eisLesStockTransQueueService.getDelayTrans(pagerInfo.getStart(), pageSize);
    }

    /**
     * 获取延后处理的LESS出入库记录列表总数
     *
     * @return
     */
    public int getTotalOfLessDelayTrans() {
        return eisLesStockTransQueueService.getDelayTransCount();
    }


    /**
     * 修正LESS出入库记录的所属渠道
     *
     * @param transId
     * @param channel
     * @param user
     * @return
     */
    public ServiceResult<Boolean> reviseLessStockTransChannel(Integer transId, String channel,
                                                              String user) {
        return eisStockService.reviseLessStockTransChannel(transId, channel, user);
    }

    /**
     * 根据查询条件获取CBS库存交易列表
     *
     * @param params
     * @return
     */
    public List<Map<String, Object>> getInvStockTransList(Map<String, Object> params) {
        return stockInvStockTransactionService.query(params);
    }

    /**
     * 获取延后处理的LESS出入库记录列表总数
     *
     * @return
     */
    public int getTotalOfInvTrans(Map params) {
        return stockInvStockTransactionService.getRowCnt(params);
    }


    public List<InvStockChannel> getChannels() {
        ServiceResult<List<InvStockChannel>> result = stockCommonService.getChannels();
        if (result.getSuccess()) {
            return result.getResult();
        }
        return new ArrayList<InvStockChannel>();
    }




  
}
