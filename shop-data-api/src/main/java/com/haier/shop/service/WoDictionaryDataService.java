package com.haier.shop.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.dto.ThirdPartyLiabilityCondition;
import com.haier.shop.model.ReviewDataDictionaryEntity;
import java.util.List;
import java.util.Map;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.workorder.data.service
 * @Date: Created in 2018/4/24 15:25
 * @Modified By:
 */
public interface WoDictionaryDataService {

  /**
   * 通过setId获取对应数据表数据
   */
  List<ReviewDataDictionaryEntity> selectBySetId(String value_set_id);

  /*五级人员权限,有分页查询*/
  JSONObject selectBySetIds(String value_set_id,
      PagerInfo pager);

  /**
   * 通过parentValue获取对应二级表数据
   */
  List<ReviewDataDictionaryEntity> selectByParentValue(String value_set_id,
      String parentValue);

  /**
   * 分页查询订单来源
   */
  JSONObject findPageList(ReviewDataDictionaryEntity dic,
      PagerInfo pager);

  /**
   * 添加订单来源
   */
  ServiceResult<Boolean> addOrderSource(ReviewDataDictionaryEntity dic, String user);

  /**
   * 修改订单来源
   */
  ServiceResult<Boolean> updateOrderSource(ReviewDataDictionaryEntity dic, String user);

  /**
   * 忽略是否启用
   */
  List<ReviewDataDictionaryEntity> selectBySetIdIgnoreFlg(String value_set_id);

  /**
   * 分页查询区县与工贸对应关系
   */
  JSONObject findPageRegionCompanyList(Map<String, Object> map,
      PagerInfo pager);

  /**
   * 添加区县
   */
  ServiceResult<Boolean> addRegion(ReviewDataDictionaryEntity dic, Map<String, Object> map,
      String user);

  /**
   *c
   *
   * @param map (newCompanyId,oldCompanyId,regionId)
   */
  ServiceResult<Boolean> addCompanyOfRegion(Map<String, Object> map);

  /**
   * 查询当前责任位在第三方责任表中是否存在
   */
  Integer findThirdPartyCount(ThirdPartyLiabilityCondition condition);

  /**
   * 通过责任位名称获取责任位对应信息
   */

  ReviewDataDictionaryEntity selectByValueMeaning(String value_set_id,
      String value_meaning, String parent_value);

  ThirdPartyLiabilityCondition findInfoFromThird(ThirdPartyLiabilityCondition third);

  ReviewDataDictionaryEntity getChannelCode(String valueMeaning);

  List<ReviewDataDictionaryEntity> selectByValue(String setId,String value);

  ReviewDataDictionaryEntity getCompany(String region);


}
