package com.haier.traderate.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.shop.dto.ReviewContacts;
import com.haier.shop.dto.ReviewContactsDto;
import com.haier.shop.service.WoReviewContactsDataService;
import com.haier.traderate.service.WoReviewContactsService;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.traderate.services
 * @Date: Created in 2018/4/26 10:12
 * @Modified By:
 */
@Service
public class WoReviewContactsServiceImpl implements WoReviewContactsService {

  private static Logger log = LogManager.getLogger(WoReviewContactsServiceImpl.class);
  @Resource
  private WoReviewContactsDataService woReviewContactsDataService;

  /**
   * 分页查询
   */
  @Override
  public JSONObject page(ReviewContactsDto reviewContacts, PagerInfo pager) {
    return woReviewContactsDataService.page(reviewContacts, pager);
  }

  @Override
  public List<ReviewContactsDto> findListLG(ReviewContactsDto reviewContactsDto) {
    return woReviewContactsDataService.findListLG(reviewContactsDto);
  }

  /**
   * 添加
   */
  @Override
  public Boolean addReviewContacts(ReviewContacts record) {
    return woReviewContactsDataService.addReviewContacts(record);
  }

  /**
   * 修改
   */
  @Override
  public Boolean updateReviewContacts(ReviewContacts record) {
    return woReviewContactsDataService.updateReviewContacts(record);
  }

  /**
   * 删除
   */
  @Override
  public Boolean deleteReviewContacts(List<ReviewContacts> record, String qf) {
    return woReviewContactsDataService.deleteReviewContacts(record, qf);
  }

  @Override
  public Boolean findinsert(ReviewContactsDto record) {
    return woReviewContactsDataService.findinsert(record);
  }

  @Override
  public Boolean updatedel(ReviewContacts record) {
    return woReviewContactsDataService.updatedel(record);

  }

  /**
   * 保存按钮
   */
  @Override
  public Integer updatemanagerUserId(ReviewContactsDto record) {
    return woReviewContactsDataService.updatemanagerUserId(record);
  }

  /**
   * 通过ID更新责任人
   */
  @Override
  public Integer updateById(ReviewContactsDto reviewContacts) {
    return woReviewContactsDataService.updateById(reviewContacts);
  }

  @Override
  public List<ReviewContactsDto> findListBySearch(ReviewContactsDto rcd) {
    return woReviewContactsDataService.findListBySearch(rcd);
  }
}
