package com.haier.shop.services;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.dao.workorder.WoDictionaryDao;
import com.haier.shop.dao.workorder.WoReviewContactsDao;
import com.haier.shop.dao.workorder.WoReviewLogDao;
import com.haier.shop.dto.LogBean;
import com.haier.shop.dto.ReviewContactsDto;
import com.haier.shop.dto.ThirdPartyLiabilityCondition;
import com.haier.shop.model.ReviewDataDictionaryEntity;
import com.haier.shop.service.WoDictionaryDataService;
import com.haier.shop.util.ReviewConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: wsh
 * @Description:
 * @ProjectName: svc
 * @PackageName: com.haier.workorder.data.services
 * @Date: Created in 2018/4/24 15:26
 * @Modified By:
 */

@Service
public class WoDictionaryDataServiceImpl implements WoDictionaryDataService {

  @Resource
  private WoDictionaryDao woDictionaryDao;
  @Resource
  private WoReviewLogDao woReviewLogDao;
  @Resource
  private WoReviewContactsDao reviewContactsDao;

  /**
   * @param value_set_id
   * @return
   */
  @Override
  public List<ReviewDataDictionaryEntity> selectBySetId(String value_set_id) {
    ReviewDataDictionaryEntity record = new ReviewDataDictionaryEntity();
    record.setValueSetId(value_set_id);
    return woDictionaryDao.selectBySetId(record);
  }

  @Override
  public JSONObject selectBySetIds(String value_set_id, PagerInfo pager) {
    JSONObject result = new JSONObject();
    ReviewDataDictionaryEntity record = new ReviewDataDictionaryEntity();
    record.setValueSetId(value_set_id);
    result.put("rows", woDictionaryDao.selectBySetIds(record, pager));
    result.put("total", woDictionaryDao.selectBySetIdsCount(record));
    return result;
  }

  @Override
  public List<ReviewDataDictionaryEntity> selectByParentValue(String value_set_id,
      String parentValue) {
    ReviewDataDictionaryEntity record = new ReviewDataDictionaryEntity();
    record.setValueSetId(value_set_id);
    record.setParentValue(parentValue);
    return woDictionaryDao.selectBySetId(record);
  }

  /**
   * 分页查询
   */
  @Override
  public JSONObject findPageList(ReviewDataDictionaryEntity dic, PagerInfo pager) {
    JSONObject result = new JSONObject();
    result.put("rows", woDictionaryDao.findPageList(dic, pager));
    result.put("total", woDictionaryDao.findPageCount(dic));
    return result;
  }

  /**
   * 添加订单来源
   */
  @Override
  public ServiceResult<Boolean> addOrderSource(ReviewDataDictionaryEntity dic, String user) {
    ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
    //校验是否有重复订单ID
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("id", null);
    map.put("value", dic.getValue());
    map.put("valueMeaning", dic.getValueMeaning());
    int num = woDictionaryDao.checkoutRepeat(map);
    //有则提示有重复
    if (num > 0) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage("添加失败");
      return serviceResult;
    }
    // TODO 插入订单来源管理添加log
    Short s = dic.getDeleteFlag();
    String isFlg = "";
    if (s == ReviewConstants.IS_FLG.OPEN) {
      isFlg = ReviewConstants.IS_FLG_STR.OPEN;
    } else if (s == ReviewConstants.IS_FLG.CLOSE) {
      isFlg = ReviewConstants.IS_FLG_STR.CLOSE;
    }
    LogBean addContextlog = new LogBean(user, ReviewConstants.MKNAME.MKNAME_4,
        ReviewConstants.LOG.LOG_21, "订单来源:" + dic.getValueMeaning() + " " + "编号:"
        + dic.getValue() + " 是否启用:" + isFlg, "");
    woReviewLogDao.insertSelective(addContextlog);
    woDictionaryDao.addOrderSource(dic);
    return serviceResult;

  }

  /**
   * 修改订单来源
   */
  @Override
  public ServiceResult<Boolean> updateOrderSource(ReviewDataDictionaryEntity dic, String user) {
    //校验是否有重复订单ID
    ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("id", dic.getId());
    map.put("value", dic.getValue());
    map.put("valueMeaning", dic.getValueMeaning());
    int num = woDictionaryDao.checkoutRepeat(map);
    //有则提示有重复
    if (num > 0) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage("已存在，更新失败");
      return serviceResult;
    }
    //判断是否存在已经配置责任位的订单来源
    ReviewContactsDto reviewContacts = new ReviewContactsDto();
    reviewContacts.setOrdercome(dic.getValueMeaning());
    int total = reviewContactsDao.findPageCount(reviewContacts);
    if (total > 0) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage("已经配置责任位,更新失败");
      return serviceResult;
    }
    // TODO 插入订单来源管理添加log
    Short s = dic.getDeleteFlag();
    String isFlg = "";
    if (s == ReviewConstants.IS_FLG.OPEN) {
      isFlg = ReviewConstants.IS_FLG_STR.OPEN;
    } else if (s == ReviewConstants.IS_FLG.CLOSE) {
      isFlg = ReviewConstants.IS_FLG_STR.CLOSE;
    }
    LogBean addContextlog = new LogBean(user, ReviewConstants.MKNAME.MKNAME_4,
        ReviewConstants.LOG.LOG_22, "订单来源:" + dic.getValueMeaning() + " " + "编号:"
        + dic.getValue() + " 是否启用:" + isFlg, "");
    woReviewLogDao.insertSelective(addContextlog);
    int res = woDictionaryDao.updateOrderSource(dic);
    if (res == 1) {
      return serviceResult;
    } else {
      serviceResult.setSuccess(false);
      serviceResult.setMessage("更新失败");
      return serviceResult;
    }


  }

  /**
   * @param value_set_id 忽略是否启用
   */
  @Override
  public List<ReviewDataDictionaryEntity> selectBySetIdIgnoreFlg(
      String value_set_id) {
    ReviewDataDictionaryEntity record = new ReviewDataDictionaryEntity();
    record.setValueSetId(value_set_id);
    return woDictionaryDao.selectBySetIdIgnoreFlg(record);
  }

  /**
   * 分页查询区县与工贸对应关系
   */
  @Override
  public JSONObject findPageRegionCompanyList(Map<String, Object> map, PagerInfo pager) {
    JSONObject result = new JSONObject();
    result.put("rows", woDictionaryDao.findPageRegionCompanyList(map, pager));
    result.put("total", woDictionaryDao.findPageRegionCompanyNum(map));
    return result;

  }

  /**
   * 添加区县
   */
  @Override
  @Transactional
  public ServiceResult<Boolean> addRegion(ReviewDataDictionaryEntity dic,
      Map<String, Object> mapData, String user) {
    ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
    int num = woDictionaryDao.findRegionNum(dic);
    if (num > 0) {
      serviceResult.setSuccess(false);
      serviceResult.setMessage("添加失败");
      return serviceResult;
    }
    //不存在则创建
    woDictionaryDao.addRegion(dic);
    //在新工贸中追加区县ID
    String companyId = (String) mapData.get("companyId");
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("newCompanyId", companyId);
    map.put("regionId", dic.getValue());
    int flg = woDictionaryDao.addCompanyOfRegion(map);
    if (flg > 0) {
      String companyName = (String) mapData.get("companyName");//工贸名称
      String cityName = (String) mapData.get("cityName");//市名称
      String provinceName = (String) mapData.get("provinceName");//省名称
      LogBean addContextlog = new LogBean(user, ReviewConstants.MKNAME.MKNAME_5,
          ReviewConstants.LOG.LOG_23, "省:" + provinceName + " 市:" + cityName + "下追加新区县:"
          + dic.getValueMeaning() + " " + " 对应工贸:" + companyName,
          "");
      woReviewLogDao.insertSelective(addContextlog);
      return serviceResult;
    } else {
      serviceResult.setSuccess(false);
      serviceResult.setMessage("添加失败");
      return serviceResult;
    }
  }

  /**
   * 在工贸中 追加/删除 区县ID
   *
   * @param map (newCompanyId,oldCompanyId,regionId)
   */
  @Override
  public ServiceResult<Boolean> addCompanyOfRegion(Map<String, Object> map) {
    ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
    String lodCompanyId = (String) map.get("oldCompanyId");
    if (lodCompanyId != null && !"".equals(lodCompanyId)) {
      //删除原工贸中的区县ID
      woDictionaryDao.delOldCompanyOfRegion(map);
    }
    //在新工贸中追加区县ID
    int flg = woDictionaryDao.addCompanyOfRegion(map);
    if (flg > 0) {
      String user = (String) map.get("user");
      String oldCompanyName = (String) map.get("oldCompanyName");
      String regionName = (String) map.get("regionName");
      String newCompanyName = (String) map.get("newCompanyName");
      LogBean addContextlog = new LogBean(user, ReviewConstants.MKNAME.MKNAME_5,
          ReviewConstants.LOG.LOG_24, "区县:" + regionName + " " + "工贸由:" + oldCompanyName
          + " 改为:" + newCompanyName, "");
      woReviewLogDao.insertSelective(addContextlog);
      return serviceResult;
    } else {
      serviceResult.setMessage("追加区县失败");
      serviceResult.setSuccess(false);
      return serviceResult;
    }
  }

  /**
   * 查询当前责任位在第三方责任表中是否存在
   */
  @Override
  public Integer findThirdPartyCount(ThirdPartyLiabilityCondition condition) {
    return woDictionaryDao.findThirdPartyCount(condition.getQuestion1Level1(),
        condition.getQuestion1Level2(),
        condition.getQuestion1Level3()
    );
  }

  @Override
  public ReviewDataDictionaryEntity selectByValueMeaning(String value_set_id,
      String value_meaning, String parent_value) {
    ReviewDataDictionaryEntity rd = new ReviewDataDictionaryEntity();
    rd.setValueMeaning(value_meaning);
    rd.setParentValue(parent_value);
    rd.setValueSetId(value_set_id);
    return woDictionaryDao.selectByValueMeaning(rd);
  }

  @Override
  public ThirdPartyLiabilityCondition findInfoFromThird(
      ThirdPartyLiabilityCondition third) {
    return woDictionaryDao.findInfoFromThird(third.getQuestion1Level1(),
        third.getQuestion1Level2(),
        third.getQuestion1Level3()
    );
  }

  @Override
  public ReviewDataDictionaryEntity getChannelCode(String valueMeaning) {
    return woDictionaryDao.getChannelCode(valueMeaning);
  }

  @Override
  public List<ReviewDataDictionaryEntity> selectByValue(String setId, String value) {
    ReviewDataDictionaryEntity record = new ReviewDataDictionaryEntity();
    record.setValueSetId(setId);
    record.setValue(value);
    return woDictionaryDao.selectBySetId(record);
  }

  @Override
  public ReviewDataDictionaryEntity getCompany(String region) {
    return woDictionaryDao.getCompany(region);
  }
}
