package com.haier.shop.dao.workorder;

import com.haier.common.PagerInfo;

import com.haier.shop.dto.LogBean;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface WoReviewLogDao {
    int deleteByPrimaryKey(Long id);

    int insert(LogBean record);

    int insertSelective(LogBean record);

    LogBean selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LogBean record);

    int updateByPrimaryKey(LogBean record);

    /**
     * 获取分页查询list数据
     * @param endTime 
     * @param startTime 
     * @return
     */
    List<LogBean> findPageList(@Param("record") LogBean record, @Param("pager") PagerInfo pager,
        @Param("startTime") String startTime,
        @Param("endTime") String endTime);

    /**
     * 获取总条数
     * @return
     */
    int findPageCount(@Param("record") LogBean record, @Param("startTime") String startTime,
        @Param("endTime") String endTime);
}