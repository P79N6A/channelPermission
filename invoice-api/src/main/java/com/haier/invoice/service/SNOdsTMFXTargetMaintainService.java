package com.haier.invoice.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.shop.model.SNOdsTMFXTargetMaintain;
import java.util.List;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.invoice.service
 * @Date: Created in 2018/5/28 14:32
 * @Modified By:
 */
public interface SNOdsTMFXTargetMaintainService {

  /**
   * 苏宁分销-目标维护分页查询
   */
  JSONObject queryTargetMaintain(SNOdsTMFXTargetMaintain odsTMFXTargetMaintain);

  /**
   * 苏宁分销-目标维护查询导出
   */
  List<SNOdsTMFXTargetMaintain> queryTMFXTargetMaintainExcel(SNOdsTMFXTargetMaintain map);

  /**
   * 苏宁分销-目标维护新增修改
   */
  ServiceResult<String> editTargetMaintain(SNOdsTMFXTargetMaintain odsTMFXTargetMaintain);

  /*
   */
/**
 * 导入校验
 * @param list
 * @param user
 * @return
 *//*

   ServiceResult<String> checkInfo(List<SNOdsTMFXTargetMaintainExcel> list,BaseUser user);

  */
/**
 * 导入处理
 * @param list
 * @param user
 * @return
 *//*

   ServiceResult<String> execExcel(List<SNOdsTMFXTargetMaintainExcel> list,BaseUser user);
*/

  /**
   * 删除
   */
  ServiceResult<String> deleteTargetMaintain(String ids, String userName);

  /**
   * 审核
   */
  ServiceResult<String> auditTargetMaintain(String ids, String audit, String userName);

  /**
   * 导入处理
   */
  ServiceResult<String> execExcel(List<SNOdsTMFXTargetMaintain> list);

}

