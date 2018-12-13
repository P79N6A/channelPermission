package com.haier.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.shop.model.SNOdsTMFXTargetMaintain;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.shop.service
 * @Date: Created in 2018/5/28 14:29
 * @Modified By:
 */
public interface SNOdsTMFXTargetMaintainDataService {

  /**
   * 通过主键删除天猫分销-月度年度季度目标维护<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_TARGETMAINTAIN<br>
   *
   * DATE: 2017-10-10 16:55
   */
  int deleteByPrimaryKey(String id);

  /**
   * 添加数据到天猫分销-月度年度季度目标维护<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_TARGETMAINTAIN<br>
   *
   * DATE: 2017-10-10 16:55
   */
  int insertSelective(SNOdsTMFXTargetMaintain record);

  /**
   * 通过主键查询天猫分销-月度年度季度目标维护<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_TARGETMAINTAIN<br>
   *
   * DATE: 2017-10-10 16:55
   */
  SNOdsTMFXTargetMaintain selectByPrimaryKey(String id);

  /**
   * 通过主键更新天猫分销-月度年度季度目标维护<br> 此方法为自动生成.<br> 此方法对应的数据表为： ODS_TMFX_TARGETMAINTAIN<br>
   *
   * DATE: 2017-10-10 16:55
   */
  int updateByPrimaryKeySelective(SNOdsTMFXTargetMaintain record);

  /**
   * 分页查询
   */
  JSONObject queryTMFXTargetMaintain(SNOdsTMFXTargetMaintain paging);

  /**
   * 查询导出
   */
  List<SNOdsTMFXTargetMaintain> queryTMFXTargetMaintainExcel(SNOdsTMFXTargetMaintain map);

  /**
   * 校验重复
   */
  List<SNOdsTMFXTargetMaintain> queryRepetition(SNOdsTMFXTargetMaintain odsTMFXTargetMaintain);

  /**
   *
   * @param odsTMFXTargetMaintain
   * @return
   */
  ServiceResult<String> addRecord(SNOdsTMFXTargetMaintain odsTMFXTargetMaintain);

  /**
   * 批量导入
   */
  ServiceResult<String> bulkImport(List<SNOdsTMFXTargetMaintain> list);

  /**
   * 查询要生成数据的目标
   */
  List<SNOdsTMFXTargetMaintain> queryAllTargetMaintain(String year, String month, String type);

  /**
   * 查询目标(按产业 品牌 汇总的时候)
   */
  SNOdsTMFXTargetMaintain findTargetMaintain(SNOdsTMFXTargetMaintain odsTMFXTargetMaintain);

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
