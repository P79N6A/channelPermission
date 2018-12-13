package com.haier.shop.dao.workorder;

import com.haier.common.PagerInfo;
import com.haier.shop.dto.ReviewContacts;
import com.haier.shop.dto.ReviewContactsDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 通讯录表
 *
 * @Filename: ReviewcontactsReadDao.java
 * @Version: 1.0
 * @Author: tianshuai.zhang 张天帅
 * @Email: tianshuai.zhang@dhc.com.cn
 */
@Mapper
public interface WoReviewContactsDao {

  int deleteByPrimaryKey(Long id);

  int insert(ReviewContacts record);

  int insertSelective(ReviewContacts record);

  ReviewContacts selectByPrimaryKey(String id);

  int updateByPrimaryKeySelective(ReviewContacts record);

  int updateByPrimaryKey(ReviewContacts record);

  /**
   * 删除
   * @return
   */
  int deleteReviewContacts(List<ReviewContacts> list);

  /**
   *
   * @return
   */
  int updatemanagerUserId(ReviewContacts contacts);

  int updatedel(ReviewContacts record);

  /**
   * 通过主键Id更改责任人
   * @return
   */
  int updateById(ReviewContacts contacts);

  int deleteByPrimaryKey(String id);

  /**
   * 模糊查找条件
   * 获取分页查询list数据
   * @return
   */
  List<ReviewContactsDto> findPageList(@Param("record") ReviewContactsDto record,
      @Param("pager") PagerInfo pager);

  /**
   *
   * @return
   */
  int findPageCount(@Param("record") ReviewContactsDto record);

  /**
   * 精确查找条件
   * @param record
   * @return
   */
  List<ReviewContactsDto> findList(@Param("record") ReviewContactsDto record);
  /**
   * 通过list验证重复
   * 根据责任位1 和责任位2  查询相对应的人员
   * @param contactList
   * @return
   */
  List<ReviewContactsDto> validateByList(List<ReviewContactsDto> contactList);

}