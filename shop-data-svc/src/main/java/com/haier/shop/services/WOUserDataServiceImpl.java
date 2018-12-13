package com.haier.shop.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.dao.workorder.WOUserDao;
import com.haier.shop.dto.ReviewUserDto;
import com.haier.shop.model.WOUser;
import com.haier.shop.service.WOUserDataService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.workorder.data.services
 * @Date: Created in 2018/4/23 10:38
 * @Modified By:
 */
@Service
public class WOUserDataServiceImpl implements WOUserDataService {

  @Resource
  private WOUserDao woUserDao;

  @Override
  public JSONObject getPagerByParam(WOUser param,PagerInfo page) {
    JSONObject result = new JSONObject();
    result.put("rows", woUserDao.getListByParam(param,page));
    result.put("total", woUserDao.countByParam(param));
    return result;
  }

  @Override
  public List<WOUser> getListByParam(WOUser param) {
    return woUserDao.getListByParam(param,null);
  }

  @Override
  public int addWOUser(WOUser param) {
    return woUserDao.addWOUser(param);
  }

  @Override
  public int updateById(WOUser param) {
    return woUserDao.updateById(param);
  }

  @Override
  public WOUser getById(String userId) {
    return woUserDao.getById(userId);
  }

  @Override
  public int delBatch(List<String> userIds) {
    return woUserDao.delBatch(userIds);
  }

  @Override
  public List<ReviewUserDto> selectBySetId(String value_set_id, String type) {
    return woUserDao.selectBySetId(value_set_id,type);
  }

  @Override
  public List<WOUser> selectNotuserId() {
    return woUserDao.selectNotuserId();
  }
}
