package com.haier.traderate.service;

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
 * @PackageName: com.haier.workorder.service
 * @Date: Created in 2018/4/24 15:24
 * @Modified By:
 */
public interface WoDictionaryService {

  List<ReviewDataDictionaryEntity> selectBySetId(String value_set_id);

  JSONObject selectBySetIds(String value_set_id, PagerInfo pager);

  List<ReviewDataDictionaryEntity> selectByParentValue(String value_set_id, String parentValue);

  JSONObject findPageList(ReviewDataDictionaryEntity dic, PagerInfo pager);

  ServiceResult<Boolean> addOrderSource(ReviewDataDictionaryEntity dic, String user);

  ServiceResult<Boolean> updateOrderSource(ReviewDataDictionaryEntity dic, String user);

  List<ReviewDataDictionaryEntity> selectBySetIdIgnoreFlg(String value_set_id);

  JSONObject findPageRegionCompanyList(Map<String, Object> map, PagerInfo pager);

  ServiceResult<Boolean> addRegion(ReviewDataDictionaryEntity dic, Map<String, Object> map, String user);

  ServiceResult<Boolean> addCompanyOfRegion(Map<String, Object> map);

  Integer findThirdPartyCount(ThirdPartyLiabilityCondition condition);

  ReviewDataDictionaryEntity selectByValueMeaning(String value_set_id,
      String value_meaning, String parent_value);

  ThirdPartyLiabilityCondition findInfoFromThird(
      ThirdPartyLiabilityCondition third);
}
