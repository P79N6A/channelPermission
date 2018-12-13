package com.haier.traderate.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.dto.ThirdPartyLiabilityCondition;
import com.haier.shop.model.ReviewDataDictionaryEntity;
import com.haier.shop.service.WoDictionaryDataService;
import com.haier.traderate.service.WoDictionaryService;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Filename: WorkOrderDutyServiceImpl.java
 * @Version: 1.0
 * @Author: liuqi  刘祺
 * @Email: qi.liu@dhc.com.cn
 */
@Service
public class WoDictionaryServiceImpl implements WoDictionaryService {

  @Resource
  private WoDictionaryDataService woDictionaryDataService;

  /**
   * @param value_set_id
   * @return
   */
  @Override
  public List<ReviewDataDictionaryEntity> selectBySetId(String value_set_id) {
    return woDictionaryDataService.selectBySetId(value_set_id);

  }

  @Override
  public JSONObject selectBySetIds(String value_set_id,
      PagerInfo pager) {
    return woDictionaryDataService.selectBySetIds(value_set_id, pager);
  }

  /**
   * 通过parentValue获取数据字典二级菜单
   */
  @Override
  public List<ReviewDataDictionaryEntity> selectByParentValue(String value_set_id,
      String parentValue) {
    return woDictionaryDataService.selectByParentValue(value_set_id, parentValue);
  }

  /**
   * 分页查询订单来源
   */
  @Override
  public JSONObject findPageList(
      ReviewDataDictionaryEntity dic,
      PagerInfo pager) {
    return woDictionaryDataService.findPageList(dic, pager);
  }

  /**
   * 添加订单来源
   */
  @Override
  public ServiceResult<Boolean> addOrderSource(ReviewDataDictionaryEntity dic, String user) {
    return woDictionaryDataService.addOrderSource(dic, user);
  }

  /**
   * 修改订单来源
   */
  @Override
  public ServiceResult<Boolean> updateOrderSource(ReviewDataDictionaryEntity dic, String user) {
    return woDictionaryDataService.updateOrderSource(dic, user);
  }

  /**
   * 忽略是否启用
   */
  @Override
  public List<ReviewDataDictionaryEntity> selectBySetIdIgnoreFlg(
      String value_set_id) {
    return woDictionaryDataService.selectBySetIdIgnoreFlg(value_set_id);
  }

  /**
   * 分页查询区县与工贸对应关系
   */
  @Override
  public JSONObject findPageRegionCompanyList(
      Map<String, Object> map,
      PagerInfo pager) {
    return woDictionaryDataService.findPageRegionCompanyList(map, pager);

  }

  /**
   * 添加区县
   */
  @Override
  public ServiceResult<Boolean> addRegion(ReviewDataDictionaryEntity dic,
      Map<String, Object> map, String user) {
    return woDictionaryDataService.addRegion(dic, map, user);
  }

  /**
   * 在工贸中 追加/删除 区县ID
   *
   * @param map (newCompanyId,oldCompanyId,regionId)
   */
  @Override
  public ServiceResult<Boolean> addCompanyOfRegion(Map<String, Object> map) {
    return woDictionaryDataService.addCompanyOfRegion(map);
  }

  /**
   * 查询当前责任位在第三方责任表中是否存在
   */
  @Override
  public Integer findThirdPartyCount(ThirdPartyLiabilityCondition condition) {
    return woDictionaryDataService.findThirdPartyCount(condition);
  }

  @Override
  public ReviewDataDictionaryEntity selectByValueMeaning(String value_set_id,
      String value_meaning, String parent_value) {
    return woDictionaryDataService.selectByValueMeaning(value_set_id, value_meaning, parent_value);

  }

  @Override
  public ThirdPartyLiabilityCondition findInfoFromThird(
      ThirdPartyLiabilityCondition third) {
    return woDictionaryDataService.findInfoFromThird(third);
  }
}
