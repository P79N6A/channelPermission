package com.haier.stock.dao.stock;

import com.haier.stock.model.InvReservedConfig;
import com.haier.stock.model.InvReservedToRelease;
import com.haier.stock.model.QueryTotalData;
import org.apache.ibatis.annotations.Param;


import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InvReservedToReleaseDao {
    /**
     * 根据编号更新预留库存配置
     * @param config
     * @return
     */
    Integer updateReservedConfigById(InvReservedConfig config);

    /**
     * 添加配置记录
     * @param config
     * @return
     */
    Integer insertReservedConfig(InvReservedConfig config);

    /**
     * 根据条件查询配置
     * @param config
     * @return
     */
    List<InvReservedConfig> getReservedConfig(InvReservedConfig config);

    /**
     * 添加预留数据
     * @param batchId
     * @param date
     * @return
     */
    Integer insertReserved(@Param("batchId") Integer batchId, @Param("releaseTime") Date date);

//    /**
//     * 根据batchId更新预留表数据状态
//     * @param releases
//     * @return
//     */
//    Integer updateReserved(InvReservedToRelease releases);

    /**
     * 根据batchId更新预留表数据状态
     * @param releases
     * @return
     */
    Integer updateReserved(InvReservedToRelease releases);
    public List<Map<String,String>> queryTotalPage(Map<String,Object> map);
    public List<QueryTotalData> queryTotalData(@Param("map")Map<String,Object> map, @Param("start")int start, @Param("rows")int rows);
}
