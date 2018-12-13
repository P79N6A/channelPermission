package com.haier.shop.dao.workorder;

import com.haier.common.PagerInfo;
import com.haier.shop.dto.OrderStock;
import com.haier.shop.dto.ReviewRegionCompanyDto;
import com.haier.shop.dto.ThirdPartyLiabilityCondition;
import com.haier.shop.model.ReviewDataDictionaryEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WoDictionaryDao {

  ReviewDataDictionaryEntity selectByPrimaryKey(Integer id);

  List<ReviewDataDictionaryEntity> selectBySetId(ReviewDataDictionaryEntity record);

  List<ReviewDataDictionaryEntity> selectBySetIds(
      @Param("reviewDataDictionaryEntity") ReviewDataDictionaryEntity record,
      @Param("pager") PagerInfo pager);

  int selectBySetIdsCount(@Param("reviewDataDictionaryEntity") ReviewDataDictionaryEntity record);

  /**
   * 通过订单来源的value_meaning查询有无重复数据
   */
  ReviewDataDictionaryEntity selectByMeaning(String valuemeaning);

  /**
   * 分页查询
   */
  List<ReviewDataDictionaryEntity> findPageList(@Param("dic") ReviewDataDictionaryEntity dic,
      @Param("pager") PagerInfo pager);


  /*查询订单来源编码  条件是  value_set_id='order_source'*/
  List<String> getOrderSource();


  /**
   * 查询总条数
   */
  int findPageCount(@Param("dic") ReviewDataDictionaryEntity dic);

  /**
   * 校验是否有重复的订单来源
   */
  int checkoutRepeat(@Param("map") Map<String, Object> map);

  /**
   * 忽略是否启用的查询
   */
  List<ReviewDataDictionaryEntity> selectBySetIdIgnoreFlg(ReviewDataDictionaryEntity record);

  /**
   * 分页查询区县与工贸对应关系
   */
  List<ReviewRegionCompanyDto> findPageRegionCompanyList(@Param("map") Map<String, Object> map,
      @Param("pager") PagerInfo pager);

  /**
   * 查询区县与工贸对应关系的总数量
   */
  int findPageRegionCompanyNum(@Param("map") Map<String, Object> map);

  /**
   * 查询指定条件区县数量
   */
  int findRegionNum(@Param("dic") ReviewDataDictionaryEntity dic);

  /**
   * 查询当前责任位在第三方责任表中是否存在
   */
  Integer findThirdPartyCount(@Param("question1Level1") String question1Level1,
      @Param("question1Level2") String question1Level2,
      @Param("question1Level3") String question1Level3
  );

  /**
   * 通过责任位名称获取责任位对应信息
   */
  ReviewDataDictionaryEntity selectByValueMeaning(ReviewDataDictionaryEntity record);

  ThirdPartyLiabilityCondition findTypeFromThirdPartyCount(
      @Param("question1Level1") String question1Level1,
      @Param("question1Level2") String question1Level2,
      @Param("question1Level3") String question1Level3

  );

  ThirdPartyLiabilityCondition findInfoFromThird(@Param("question1Level1") String question1Level1,
      @Param("question1Level2") String question1Level2,
      @Param("question1Level3") String question1Level3

  );

  ReviewDataDictionaryEntity getChannelCode(String valueMeaning);


  /**
   * 插入其他的到下拉列表里
   */
  int addReviewLY(ReviewDataDictionaryEntity record);

  ReviewDataDictionaryEntity selectorderFlag();

  /**
   * 添加订单来源
   */
  int addOrderSource(ReviewDataDictionaryEntity dic);

  /**
   * 修改订单来源
   */
  int updateOrderSource(ReviewDataDictionaryEntity dic);

  /**
   * 添加区县
   */
  int addRegion(ReviewDataDictionaryEntity dic);

  /**
   * 删除原工贸中的区县ID
   *
   * @param map (oldCompanyId,regionId)
   */
  int delOldCompanyOfRegion(@Param("map") Map<String, Object> map);

  /**
   * 在新工贸中追加区县ID
   *
   * @param map (newCompanyId,regionId)
   */
  int addCompanyOfRegion(@Param("map") Map<String, Object> map);

  void deleteOrderSource();

  void InsertOrderSource(@Param("orderStock") List<OrderStock> orderStock);

  ReviewDataDictionaryEntity getCompany(String region);
}