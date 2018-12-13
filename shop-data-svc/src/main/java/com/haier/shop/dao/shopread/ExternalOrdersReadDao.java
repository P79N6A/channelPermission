package com.haier.shop.dao.shopread;

import com.haier.shop.model.ExternalOrders;
import com.haier.shop.model.ExternalOrdersVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/*
*
* 作者:张波
* 2017/12/20
* */
@Mapper
public interface ExternalOrdersReadDao {
  /**
   * 根据源订单号获取外部订单对象
   * @param sourceOrderSn
   * @return
   */
  ExternalOrders getBySourceOrderSn(String sourceOrderSn);

  /**
   * 查询总条数
   * @return
   */
  int getRowCnts();

  /**
   * 根据条件查询
   * @param params
   * @return
   */
  List<ExternalOrdersVo> getExternalOrdersList(Map<String,Object> params);
  /**
   * 查询总条数
   * @return
   */
  Integer findExternalOrdersCNT(Map<String,Object> params);
}
