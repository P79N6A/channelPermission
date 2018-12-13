package com.haier.shop.dao.shopwrite;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.haier.shop.model.OrderProducts;

import java.util.List;
import java.util.Map;

/**
 * 网单
 * 吴坤洋 2017-10-25
 * @author wukunyang
 *
 */
@Mapper
public interface OperationAreaWriteDao {
	/**
	 * 更新网单状态
	 * @param id
	 * @param Status
	 * @return
	 */
	public int updateStatus(@Param("id") String id,@Param("Status")String Status);
	
	public void update_WwwHpRecordsCount(List<String> list);////更新WwwHpRecords表中的匹配次数
	public void updateFlagBatch(List<String> list);//批量更新hp拒收表的flag字段
	public void insertHPlogs(Map<String, Object> map);//hp推送信息插入到hp拒收日志表
	public void insertWwwHpRecords(Map<String, Object> map);

	public void update_WwwHpRecordsInfo(List<Map<String, Object>> list);//更新HP拒收信息
	
	int insertOrderProducts(OrderProducts products);//插入网单
	public void updataOrderStatus(@Param("id") Integer id);
	public void updateHPjushouCount(@Param("map") Map<String,Object> map);
	public void updateOrderRepairsStatus(@Param("map") Map<String,Object> map);
	public void updateHPQueuessuccess(@Param("id") Integer id);
	public void updateOPStatus(@Param("lockedNumber") Integer lockedNumber,@Param("cOrderSn") String cOrderSn);
	public void updateLesQueuesIsStop(@Param("id") Integer id);
	public void insertOrderRepairLog(@Param("map") Map<String,Object> map);

	public void updateRegion(@Param("orderSn")String orderSn,@Param("province") int province,@Param("city") int city,@Param("region") int region,@Param("street")int street,@Param("regionName") String regionName);
}
