package com.haier.shop.dao.workorder;

import com.haier.common.PagerInfo;
import com.haier.shop.dto.ReviewUserDto;
import com.haier.shop.model.WOUser;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.workorder.data.dao.workorder
 * @Date: Created in 2018/4/23 10:38
 * @Modified By:
 */
@Mapper
public interface WOUserDao {

  List<WOUser> getListByParam(@Param("param") WOUser param, @Param("page") PagerInfo pagerInfo);

  int countByParam(@Param("param") WOUser param);

  int addWOUser(WOUser param);

  int updateById(WOUser param);

  WOUser getById(String userId);

  int delBatch(List<String> userIds);

  List<ReviewUserDto> selectBySetId(@Param("userId")String value_set_id, @Param("type") String type);

  /**
   * 获取没有责任位的人员的下拉列表
   */
  List<WOUser> selectNotuserId();


}
