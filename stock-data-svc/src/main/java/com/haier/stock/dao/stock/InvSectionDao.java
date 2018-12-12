package com.haier.stock.dao.stock;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.stock.model.InvSection;

public interface InvSectionDao extends BaseDao<InvSection> {

    int deleteByPrimaryKey(String secCode);

    int insert(InvSection record);

    int insertSelective(InvSection record);

    InvSection selectByPrimaryKey(String secCode);

    int updateByPrimaryKeySelective(InvSection record);

    int updateByPrimaryKey(InvSection record);
    
    long checkSame(String secCode);

    List<InvSection> exportSection(@Param("entity") InvSection entity);
    
    InvSection getBySecCode(String secCode);
    
    /**
     * 通过渠道编码获取在用的库存列表
     * @param whCode
     * @param channelCode
     * @return
     */
    List<InvSection> getByChannelCode(@Param("whCode") String whCode,
                                      @Param("channelCode") String channelCode);
    
    /**
     * 根据状态获取库位列表
     * @param status 状态，可为空
     * @return
     */
    List<InvSection> getByStatus(@Param("status") Integer status);

    /**
     * 根据条件获取响应库位
     * @param secCode
     * @return
     */
    InvSection getInvSection(@Param("lesSecCode") String secCode,
                             @Param("channelCode") String channelCode,
                             @Param("itemProperty") String itemProperty);
    


    InvSection getByLesSecCodeAndChannelCode(@Param("les_sec_code") String lesSecCode,
                                             @Param("channelCode") String channelCode);

}
