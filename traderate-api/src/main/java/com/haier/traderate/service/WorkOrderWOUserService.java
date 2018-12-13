package com.haier.traderate.service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.dto.ReviewUserDto;
import com.haier.shop.model.WOUser;
import java.util.List;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.workorder.service
 * @Date: Created in 2018/4/23 10:58
 * @Modified By:
 */
public interface WorkOrderWOUserService {

  JSONObject getPageByParam(WOUser param, PagerInfo page);

  List<WOUser> getListByParam(WOUser param);

  Object addWOUser(WOUser param);

  Object updateById(WOUser param);

  WOUser getById(String userId);

  Object delBatch(List<String> userIds);

  /**
   * 通过setId获取对应数据表数据
   * @param value_set_id
   * @return
   */
  List<ReviewUserDto> selectBySetId(String value_set_id, String type);

  /**
   * 获取没有责任位的人员的下拉列表
   */
  List<WOUser> selectNotuserId();
}
