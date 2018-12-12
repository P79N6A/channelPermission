package com.haier.stock.service;

import java.util.List;
import java.util.Map;

import com.haier.stock.model.InvStockTransaction;

public interface StockInvStockTransactionService {
	public Integer insert(InvStockTransaction stockTransaction);

	public  Integer insertAll(List<InvStockTransaction> transactions);

	public  List<InvStockTransaction> getNotProcessBusiness();

	public  Integer updateBusinessProcessStatus(Integer id,
	                                        Integer businessProcessStatus);

	public  Integer updateProcessStatus(Integer id,
	                                 Integer processStatus,
	                                Integer oldProcessStatus,
	                                Integer isDelay,
	                                String message);

	public   Integer updateToDelay(Integer id, Integer isDelay,
	                         String message);

	public  List<InvStockTransaction> getByProcessStatus(Integer status);

	public  List<InvStockTransaction> getByIsDelay( Integer isDelay);

	public  List<InvStockTransaction> getByRefNo(String refNo);

	public   List<InvStockTransaction> queryData(Integer id, Integer num);
	
	   /**
     * 保存对象非空数据
     * @param record
     * @return
     */
    int insertSelective(InvStockTransaction record);
    
    /**
     * 查询交易 按时间降序
     * @param params
     * @return
     */
    List<Map<String, Object>> queryList(Map params);
    /**
     * 查询结果
     * @param params
     * @return
     */
    int getListRowCnt(Map params);
    /**
     * 查询交易 按时间降序
     * @param params
     * @return
     */
    List<Map<String, Object>> queryStockTransList(Map params);
    
    /**
     * 查询结果
     * @param params
     * @return
     */
    int getStockTransListRowCnt(Map params);
    /**
     * 查询交易
     * @param params
     * @return
     */
    List<Map<String, Object>> query(Map params);
    

    int getRowCnt();
}
