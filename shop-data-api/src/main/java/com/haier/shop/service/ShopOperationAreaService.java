package com.haier.shop.service;



import java.util.List;
import java.util.Map;

import com.haier.shop.model.*;

/**
 * 网单
 * 吴坤洋 2017-10-25
 * @author wukunyang
 *
 */
public interface ShopOperationAreaService {

	
	public List<OrderProductsVo>  queryOderProductList(OrderProductsVo params); //分页查询
	public int  findOrderProductCNT(OrderProductsVo params);//查询总数
	
	public OrderProductsVo queryOrdeProduct(String cOrderSn);//根据网单号查询明细数据
	public OrderProductsVo queryCommodity(String cOrderSn);//根据网单号查询商品
	public Map<String,String> selectMemberInvoicesByorderSn(String orderSn);
	public List<Map<String,String>> selectPrudevtDatail(String orderSn);/*订单商品详情*/
	public List<Map<String,String>> selectOrderOperateLogs(String orderSn);/*订单操作日志*/
	public List<Map<String,String>> selectCoupon(String orderSn);/*优惠券信息查询*/
	public Map<String,String> orderNumberSelect(String orderSn);//根据订单标号查询明细数据
	public List<Map<String,String>> datagrid_WwwHpRecords(Map<String, Object> map);//HP拒收表格显示
	public List<Map<String,String>> datagrid_WwwHpRecords1(Map<String, Object> map);//获取总记录数
	public List<Map<String,Object>> check_cOrderSn_isExist(List<Map<String, Object>> list);////查询在网单表已经存在的网单号并返回存在的网单号
	public void update_WwwHpRecordsCount(List<String> list);////更新WwwHpRecords表中的匹配次数
	public void updateFlagBatch(List<String> list);//批量更新hp拒收表的flag字段
	public List<Map<String,Object>> select_export_ExcelData(Map<String, Object> map);//查询导出报表的数据
	public void insertHPlogs(Map<String, Object> map);//hp推送信息插入到hp拒收日志表
	public List<String> selectHPlogsRowid(String rowid);//查询hp拒收日志表里面的rowid ,判断主键是否重复
	public List<Map<String, Object>> selectAllHPlogs();
	public void insertWwwHpRecords(Map<String, Object> map);

	public String queryTBorderSn(String orderSn); //根据网单号查询TB单号

	public int queryWwwHpTbSn(String tbSn);//根据网单扩展表TB单号查询HP拒收表是否有这条数据

	public List<OrderProductsVo> queryTmallTiming(String sku, String source,String tid);//根据sku 和来源号匹配网单数据

    /**
     * 根据tid oid匹配网单数据
     * @param oid
     * @param tid
     * @return
     */
	public List<OrderProductsVo> queryTmallTimingoid(String oid, String tid);

	public void update_WwwHpRecordsInfo(List<Map<String, Object>> list);//更新HP拒收信息
	public Map<String,Object> select_ThInfo(String cOrderSn);
	public OrderRepairs selectOrderSn(String cOrderSn);
	public OrderRepairs Checkstate(String cOrderSn);
	/*
	* 退换货列表显示
	* */
	public List<Map<String,String>> datagrid_orderForecastGoal(Map<String, Object> map);
	public List<Map<String,String>> datagrid_orderForecastGoalcount(Map<String, Object> map);//退货总记录数
	public List<Map<String,Object>> export_ExcelOrderRepairs(Map<String, Object> map);
	public Map<String,Object> selectPhoneAndName(String cOrderSn);
	public String selectOrderProductId(int th_id);
	
	public int updateStatus(String id,String Status);
	public List<OrderProductsVo> searchcod(OrderProductsVo vo);
	public int findCodConfirmCNT(OrderProductsVo vo);
	OrderProducts queryGetId(String id);//根据id查询数据
	Map<String,Object> selectOrderProductView (String id);//根据网单id查询数据
	public List<OrderProducts> findOrderProduct(String sourceOrderSn, String sku);
	public OrderProducts findOrderProductByOid(String oid);
	int insertOrderProducts(OrderProducts products);//插入网单
	public List<OrderProducts> queryOrderProductStatus(String orderId);//根据订单号查询 为关闭的网单信息
	public String selectOutping(String cOrderSn);//查询出入库凭证号
	public  Map<String,Object> selectlessOrderSn(String cOrderSn);
	public Map<String,Object> queryisStop(Integer id);
	public Map<String,Object> selecctLockedNumber(String cOrderSn);
	public void updateOPStatus(Integer lockedNumber,String cOrderSn);
	public List<Map<String,Object>> selectOPCount(Integer orderId);
	public void updataOrderStatus(Integer id);
	public void updateLesQueuesIsStop(Integer id);
	public Integer selectStatus(String cOrderSn);
	public void updateHPQueuessuccess(Integer id);
	public List<Map<String,Object>> queryOrderProductByTB(String tbSn);
	public List<Map<String,Object>> queryRepairsOrderProductByTB(String tbSn, String sku);
	public List<Map<String,Object>> queryRepairsOrderProductByTBSKU(String tbSn, String sku);
	public void updateHPjushouCount(Map<String,Object> map);
	public void updateOrderRepairsStatus(Map<String,Object> map);
	public void insertOrderRepairLog(Map<String,Object> map);
	public List<Regions> getRegion(int id);
	public List<Regions> getRegionB2C(int id);
	public Regions getOneRegion(int id);
	public Orders getRegionName(String orderSn);
	public void updateRegion(String orderSn,int province,int city,int region,int street,String regionName);
	public List<Map<String,Object>> queryNetSheetExportDate(OrderProductsVo vo);
	public Map<String,Object> selectData(String cOrderSn);
	public List<Map<String,Object>> selectOrderProductViewTwo (String id);

	List<OrderProducts> findByTradeSn(String tradeSn, String sku);

	public List<Map<String,String>> getShippingModeAndStockType(String orderSn);

	public List<Map<String,Object>> exportBadCommentsList(List<String> sourceOrderSn);

	public OrderProductsVo selectIdAndIsMakeReceiptBycOrderSn(String cOrderSn);

	public Integer Rejectionsinglereset(String id);


}
