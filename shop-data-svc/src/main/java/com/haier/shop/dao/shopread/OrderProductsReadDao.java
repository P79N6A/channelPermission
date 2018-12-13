package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderProducts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Mapper
public interface OrderProductsReadDao {

    /**
     * 根据ID获取网单信息
     * @param id
     * @return
     */
    OrderProducts get(@Param("id") Integer id);

    /**
     * 根据订单编号，获取订单id
     * @param cOrderSn
     * @return
     */
    Integer getOrderIdByCOrderSn(String cOrderSn);

    /**
     * 根据订单编号，获取网单信息
     * @param cOrderSn
     * @return
     */
    OrderProducts getByCOrderSn(String cOrderSn);

    /**
     * 根据OrderId获得OrderProducts信息
     * @param orderId
     * @return OrderProducts信息
     */
    List<OrderProducts> getOrderProductsByOrderId(Integer orderId);

    /**
     * 根据网单id列表，获取网单列表
     * @param ids
     * @return
     */
    List<OrderProducts> getByIds(@Param("ids") List<Integer> ids);

    /**
     * 根据cOrderSn批量获取网单ID
     * @param params
     * @return
     */
    List<Integer> getOrderProductIdsInfo(Map<String, Object> params);

    /**
     * 根据网单id获取订单id
     * @param id 网单id
     * @return
     */
    Integer getOrderIdById(@Param("id") Integer id);

    /**
     * 批量查询网单数量，界面展示
     * @param paramMap
     * @return
     */
    Integer getOpListByCOrderSnCount(@Param("params") Map<String, Object> paramMap);

    /**
     *  获取网单信息列表List[普通网单]
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> getOpListByCOrderSnSearch(@Param("params") Map<String, Object> paramMap);

    /**
     *  获取[导出网单信息]列表List[普通网单]
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> getOpListByCOrderSn(@Param("params") Map<String, Object> paramMap);

	List<OrderProducts> findsCode(Integer attrCateId);

	List<OrderProducts> findSortCount();

	List<OrderProducts> findIndustryCount();

    /**
     * 根据TB单号获取网单信息
     * @param tbOrderSn
     * @return
     */
    OrderProducts getOrderProductsByTbNo(@Param("tbOrderSn") String tbOrderSn);

    OrderProducts findOPBycOrderSnAndSku(@Param("cOrderSn") String cOrderSn, @Param("sku")String sku);

    /**
     * 导出功能根据sourceOrderSn获取tbOrderSn
     * @param paramMap
     * @return
     */
    List<Map<String,Object>> getTBOrderSnBySourceOrderSn(@Param("params")Map<String,Object> paramMap);

    /**
     * 获取物流模式
     * @return
     */
    List<Map<String,Object>> getshippingMode();
}
