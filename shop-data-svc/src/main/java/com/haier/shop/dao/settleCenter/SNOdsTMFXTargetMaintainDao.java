package com.haier.shop.dao.settleCenter;

import com.haier.shop.model.SNOdsTMFXTargetMaintain;
import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.shop.dao.settleCenter
 * @Date: Created in 2018/5/28 14:07
 * @Modified By:
 */
@Mapper
public interface SNOdsTMFXTargetMaintainDao {

  /**
   * 通过主键删除苏宁分销-月度年度季度目标维护<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_TARGETMAINTAIN<br>
   *
   * DATE: 2017-10-10 16:55
   */
  int deleteByPrimaryKey(String id);

  /**
   * 添加数据到苏宁分销-月度年度季度目标维护<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_TARGETMAINTAIN<br>
   *
   * DATE: 2017-10-10 16:55
   */
  int insertSelective(SNOdsTMFXTargetMaintain record);

  /**
   * 通过主键查询苏宁分销-月度年度季度目标维护<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_TARGETMAINTAIN<br>
   *
   * DATE: 2017-10-10 16:55
   */
  SNOdsTMFXTargetMaintain selectByPrimaryKey(String id);

  /**
   * 通过主键更新苏宁分销-月度年度季度目标维护<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_TARGETMAINTAIN<br>
   *
   * DATE: 2017-10-10 16:55
   */
  int updateByPrimaryKeySelective(SNOdsTMFXTargetMaintain record);

  /**
   * 分页查询
   */
  List<SNOdsTMFXTargetMaintain> queryTMFXTargetMaintainList(SNOdsTMFXTargetMaintain paging);

  /**
   * 分页总数
   */
  int queryTMFXTargetMaintainCount(SNOdsTMFXTargetMaintain param);

  /**
   * 查询导出
   */
  List<SNOdsTMFXTargetMaintain> queryTMFXTargetMaintainExcel(SNOdsTMFXTargetMaintain map);

  /**
   * 校验重复
   */
  List<SNOdsTMFXTargetMaintain> queryRepetition(SNOdsTMFXTargetMaintain odsTMFXTargetMaintain);

  /**
   * 批量导入
   */
  void bulkImport(List<SNOdsTMFXTargetMaintain> list);

  /**
   * 查询要生成数据的目标
   */
  List<SNOdsTMFXTargetMaintain> queryAllTargetMaintain(String year, String month, String type);

  /**
   * 查询目标(按产业 品牌 汇总的时候)
   */
  SNOdsTMFXTargetMaintain findTargetMaintain(SNOdsTMFXTargetMaintain odsTMFXTargetMaintain);

  /**
   * 查询季度下各月目标(按产业 品牌 汇总的时候)
   */
  List<SNOdsTMFXTargetMaintain> findTargetMaintain2(SNOdsTMFXTargetMaintain odsTMFXTargetMaintain);

  /**
   * 查询目标(按生态店汇总的时候)
   */
  SNOdsTMFXTargetMaintain findByShop(SNOdsTMFXTargetMaintain odsTMFXTargetMaintain);

  /**
   * 查询生态店的所有目标
   */
  BigDecimal findTargetSummary(String year, String month, String ecologyShop, String brand,
      String industry, String type);

  /**
   * 查询生态店的年度签约目标
   */
  BigDecimal findYearTarget(String year, String ecologyShop);
}
