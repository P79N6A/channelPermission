package com.haier.shop.dao.settleCenter;

import com.haier.common.PagerInfo;
import com.haier.shop.model.SNOdsTMFXPointMaintain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SNOdsTMFXPointMaintainDao {

    List<SNOdsTMFXPointMaintain> paging(@Param("param") SNOdsTMFXPointMaintain param, @Param("page") PagerInfo pagerInfo);
    List<Map<String,Object>> export(@Param("param") SNOdsTMFXPointMaintain param);
    long count(@Param("param") SNOdsTMFXPointMaintain param);
    int insert( SNOdsTMFXPointMaintain param);
    int update( SNOdsTMFXPointMaintain param);
    int delBatch(List<String> ids);

    /**
     * 查询是否重复
     * @param odsTMFXPointMaintain
     * @return
     */
    List<SNOdsTMFXPointMaintain> queryRepetition(SNOdsTMFXPointMaintain odsTMFXPointMaintain);

    void bulkImport(List<SNOdsTMFXPointMaintain> list);

    /**
     *根据分销明细查询点位(月度)
     * 查的是单条数据
     * @param odsTMFXPointMaintain
     * @return
     */
    List<SNOdsTMFXPointMaintain> queryPointByDistribution(SNOdsTMFXPointMaintain odsTMFXPointMaintain);

    /**
     * 根据分销明细查询点位(季度年度)
     * @param odsTMFXPointMaintain
     * @return
     */
    public SNOdsTMFXPointMaintain queryPointByDistribution2(SNOdsTMFXPointMaintain odsTMFXPointMaintain);

    /**
     * 根据分销明细查询点位(月度)
     * 查的是台阶点位(多条)
     * @param odsTMFXPointMaintain
     * @return
     */
    List<SNOdsTMFXPointMaintain> queryPointByDistribution3(SNOdsTMFXPointMaintain odsTMFXPointMaintain);

    /**
     * 查询季度下三个月的达标返利点位
     * @param odsTMFXPointMaintain
     * @return
     */
    List<SNOdsTMFXPointMaintain> queryPointByDistribution4(SNOdsTMFXPointMaintain odsTMFXPointMaintain);

    /**
     * 查询月度下的日期计算期间
     * @param odsTMFXPointMaintain
     * @return
     */
    List<SNOdsTMFXPointMaintain> queryPointByDistribution5(SNOdsTMFXPointMaintain odsTMFXPointMaintain);

    /**
     * 按日期查询返利点位
     * @param odsTMFXPointMaintain
     * @return
     */
    List<SNOdsTMFXPointMaintain> queryPointByDistribution6(SNOdsTMFXPointMaintain odsTMFXPointMaintain);

}