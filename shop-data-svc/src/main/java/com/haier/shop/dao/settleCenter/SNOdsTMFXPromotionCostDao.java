package com.haier.shop.dao.settleCenter;

import com.haier.shop.model.SNOdsTMFXPromotionCost;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SNOdsTMFXPromotionCostDao {
    /**
     * 通过主键删除苏宁分销-营销费用维护<br>
     * 此方法为自动生成.<br>
     * 此方法对应的数据表为： ODS_TMFX_PROMOTION_COST<br>
     *
     * DATE: 2017-10-23 13:33
     */
    int deleteByPrimaryKey(String id);

    /**
     * 添加数据到苏宁分销-营销费用维护<br>
     * 此方法为自动生成.<br>
     * 此方法对应的数据表为： ODS_TMFX_PROMOTION_COST<br>
     *
     * DATE: 2017-10-23 13:33
     */
    int insertSelective(SNOdsTMFXPromotionCost record);

    /**
     * 通过主键查询苏宁分销-营销费用维护<br>
     * 此方法为自动生成.<br>
     * 此方法对应的数据表为： ODS_TMFX_PROMOTION_COST<br>
     *
     * DATE: 2017-10-23 13:33
     */
    SNOdsTMFXPromotionCost selectByPrimaryKey(String id);

    /**
     * 通过主键更新苏宁分销-营销费用维护<br>
     * 此方法为自动生成.<br>
     * 此方法对应的数据表为： ODS_TMFX_PROMOTION_COST<br>
     *
     * DATE: 2017-10-23 13:33
     */
    int updateByPrimaryKeySelective(SNOdsTMFXPromotionCost record);

    /**
     * 分页查询
     * @param param
     */
    List<SNOdsTMFXPromotionCost> queryPromotionCostList(SNOdsTMFXPromotionCost param);

    /**
     * 获取总数
     * @param param
     * @return
     */
    int queryPromotionCostCount(SNOdsTMFXPromotionCost param);
    /**
     * 查询导出
     * @param map
     * @return
     */
    List<SNOdsTMFXPromotionCost> queryPromotionCostExcel(SNOdsTMFXPromotionCost map);
    /**
     * 重复校验
     * @param odsTMFXPromotionCost
     * @return
     */
    List<SNOdsTMFXPromotionCost> queryRepetition(SNOdsTMFXPromotionCost odsTMFXPromotionCost);

    /**
     * 批量插入
     * @param list
     */
    void bulkImport(List<SNOdsTMFXPromotionCost> list);

    /**
     * 查询费用金额
     * @param odsTMFXPromotionCost
     * @return
     */
    List<SNOdsTMFXPromotionCost> findPromotionCost(SNOdsTMFXPromotionCost odsTMFXPromotionCost);

    /**
     * 查询营销费用金额 按生态店汇总时候
     * @param odsTMFXPromotionCost
     * @return
     */
    SNOdsTMFXPromotionCost findPromotionCostByShop(SNOdsTMFXPromotionCost odsTMFXPromotionCost);

    /**
     * 单品牌所有的费用
     * @param year
     * @param month
     * @param brand
     * @return
     */
    BigDecimal queryYXCostFromBrand(String year,String month,String brand,String ecologyShop);

    /**
     * 查询其他费用
     * @param odsTMFXPromotionCost
     * @return
     */
    BigDecimal queryQTCostAmount(SNOdsTMFXPromotionCost odsTMFXPromotionCost);
}