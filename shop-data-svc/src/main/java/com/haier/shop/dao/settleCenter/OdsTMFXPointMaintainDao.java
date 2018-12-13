package com.haier.shop.dao.settleCenter;

import com.haier.common.PagerInfo;
import com.haier.shop.model.OdsTMFXPointMaintain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OdsTMFXPointMaintainDao {

    List<OdsTMFXPointMaintain> paging(@Param("param") OdsTMFXPointMaintain param, @Param("page") PagerInfo pagerInfo);
    List<Map<String,Object>> export(@Param("param") OdsTMFXPointMaintain param);
    long count(@Param("param") OdsTMFXPointMaintain param);
    int insert( OdsTMFXPointMaintain param);
    int update( OdsTMFXPointMaintain param);
    int delBatch(List<String> ids);

    /**
     * 查询是否重复
     * @param odsTMFXPointMaintain
     * @return
     */
    List<OdsTMFXPointMaintain> queryRepetition(OdsTMFXPointMaintain odsTMFXPointMaintain);

    void bulkImport(List<OdsTMFXPointMaintain> list);

    /**
     *根据分销明细查询点位(月度)
     * 查的是单条数据
     * @param odsTMFXPointMaintain
     * @return
     */
    List<OdsTMFXPointMaintain> queryPointByDistribution(OdsTMFXPointMaintain odsTMFXPointMaintain);

    /**
     * 根据分销明细查询点位(季度年度)
     * @param odsTMFXPointMaintain
     * @return
     */
    public OdsTMFXPointMaintain queryPointByDistribution2(OdsTMFXPointMaintain odsTMFXPointMaintain);

    /**
     * 根据分销明细查询点位(月度)
     * 查的是台阶点位(多条)
     * @param odsTMFXPointMaintain
     * @return
     */
    List<OdsTMFXPointMaintain> queryPointByDistribution3(OdsTMFXPointMaintain odsTMFXPointMaintain);

    /**
     * 查询季度下三个月的达标返利点位
     * @param odsTMFXPointMaintain
     * @return
     */
    List<OdsTMFXPointMaintain> queryPointByDistribution4(OdsTMFXPointMaintain odsTMFXPointMaintain);

    /**
     * 查询月度下的日期计算期间
     * @param odsTMFXPointMaintain
     * @return
     */
    List<OdsTMFXPointMaintain> queryPointByDistribution5(OdsTMFXPointMaintain odsTMFXPointMaintain);

    /**
     * 按日期查询返利点位
     * @param odsTMFXPointMaintain
     * @return
     */
    List<OdsTMFXPointMaintain> queryPointByDistribution6(OdsTMFXPointMaintain odsTMFXPointMaintain);

}