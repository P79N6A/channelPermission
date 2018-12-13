package com.haier.traderate.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.dto.ReviewUserDto;
import com.haier.shop.model.WOUser;
import com.haier.shop.service.WOUserDataService;
import com.haier.traderate.service.WorkOrderWOUserService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.workorder.services
 * @Date: Created in 2018/4/23 10:59
 * @Modified By:
 */
@Service
public class WorkOrderWOUserServiceImpl implements WorkOrderWOUserService {

  @Resource
  private WOUserDataService woUserDataService;

  @Override
  public JSONObject getPageByParam(WOUser param,PagerInfo page) {
    return woUserDataService.getPagerByParam(param,page);
  }

  @Override
  public List<WOUser> getListByParam(WOUser param) {
    return woUserDataService.getListByParam(param);
  }

  @Override
  public Object addWOUser(WOUser param) {
    JSONObject jo = new JSONObject();
    if (woUserDataService.addWOUser(param) == 1) {
      jo.put("success", true);
    } else {
      jo.put("success", false);
    }
    return jo;
  }

  @Override
  public Object updateById(WOUser param) {
    JSONObject jo = new JSONObject();
    if (woUserDataService.updateById(param) == 1) {
      jo.put("success", true);
    } else {
      jo.put("success", false);
    }
    return jo;
  }

  @Override
  public WOUser getById(String userId) {
    return woUserDataService.getById(userId);
  }

  @Override
  public Object delBatch(List<String> userIds) {
    JSONObject jo = new JSONObject();
    if (woUserDataService.delBatch(userIds) != 0) {
      jo.put("success", true);
    } else {
      jo.put("success", false);
    }
    return jo;
  }

  @Override
  public List<ReviewUserDto> selectBySetId(String value_set_id, String type) {
    return woUserDataService.selectBySetId(value_set_id,type);
  }


  @Override
  public List<WOUser> selectNotuserId() {
    return woUserDataService.selectNotuserId();
  }
}
