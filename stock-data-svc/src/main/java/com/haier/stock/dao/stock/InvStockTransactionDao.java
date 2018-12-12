package com.haier.stock.dao.stock;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.haier.stock.model.InvStockTransaction;


/**
 * Created by 钊 on 2014/4/1.
 */
public interface InvStockTransactionDao {

    Integer insert(InvStockTransaction stockTransaction);

    Integer insertAll(List<InvStockTransaction> transactions);

    List<InvStockTransaction> getNotProcessBusiness();

    Integer updateBusinessProcessStatus(@Param("id") Integer id,
                                        @Param("businessProcessStatus") Integer businessProcessStatus);

    Integer updateProcessStatus(@Param("id") Integer id,
                                @Param("processStatus") Integer processStatus,
                                @Param("oldProcessStatus") Integer oldProcessStatus,
                                @Param("isDelay") Integer isDelay,
                                @Param("message") String message);

    Integer updateToDelay(@Param("id") Integer id, @Param("isDelay") Integer isDelay,
                          @Param("message") String message);

    List<InvStockTransaction> getByProcessStatus(@Param("processStatus") Integer status);

    List<InvStockTransaction> getByIsDelay(@Param("isDelay") Integer isDelay);

    List<InvStockTransaction> getByRefNo(@Param("corderSn") String refNo);

    List<InvStockTransaction> queryData(@Param("id") Integer id, @Param("num") Integer num);
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
