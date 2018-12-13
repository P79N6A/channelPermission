package com.haier.shop.dao.shopread;



import java.util.List;
import java.util.Map;

import com.haier.shop.model.*;
import org.apache.ibatis.annotations.Param;

/**
 * 网单
 * 吴坤洋 2017-10-25
 * @author wukunyang
 *
 */
public interface OperationAreaReadDao {

	
	public List<OrderProductsVo>  queryOderProductList(OrderProductsVo params); //分页查询
	public int  findOrderProductCNT(OrderProductsVo params);//查询总数
	public List<OrderProducts> findOrderProduct(@Param("sourceOrderSn")String sourceOrderSn, @Param("sku")String sku);//查询所有网单
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
	public List<Map<String,Object>> select_export_ExcelData(Map<String, Object> map);//查询导出报表的数据
	public List<String> selectHPlogsRowid(String rowid);//查询hp拒收日志表里面的rowid ,判断主键是否重复

	public List<Map<String, Object>> selectAllHPlogs();

	public String queryTBorderSn(String orderSn); //根据网单号查询TB单号

	public int queryWwwHpTbSn(String tbSn);//根据网单扩展表TB单号查询HP拒收表是否有这条数据

	public List<OrderProductsVo> queryTmallTiming(@Param("sku")String sku,@Param("source") String source,@Param("tid")String tid);//根据sku 和来源号匹配网单数据
	public List<OrderProductsVo> queryTmallTimingoid(@Param("oid")String oid, @Param("tid")String tid);//根据oid 和 tid匹配网单数据

	public Map<String,Object> select_ThInfo(String cOrderSn);
	public OrderRepairs selectOrderSn(String cOrderSn);
	public OrderRepairs Checkstate(String cOrderSn);
	/*
	* 退换货列表显示
	* */
	public List<Map<String,String>> datagrid_orderForecastGoal(Map<String, Object> map);
	public List<Map<String,String>> datagrid_orderForecastGoalcount(Map<String, Object> map);//退货总记录数
	public List<Map<String,Object>> export_ExcelOrderRepairs(@Param("map") Map<String, Object> map);
	public Map<String,Object> selectPhoneAndName(String cOrderSn);
	public String selectOrderProductId(@Param("th_id") int th_id);
	
	public List<OrderProductsVo> searchcod(OrderProductsVo vo);
	public int findCodConfirmCNT(OrderProductsVo vo);

	Map<String,Object> selectOrderProductView (String id);//根据网单id查询数据
	List<Map<String,Object>> selectOrderProductViewTwo (String id);//根据订单号查询数据
	public OrderProducts findOrderProductByOid(String oid);//根据子订单去匹配网单 退货单 发票
	OrderProducts queryGetId(String id);//根据id查询数据
	/**
	 * orders 里面的运费，和ordersproducts 里面的优惠信息等
	 * @param oIds
	 */
	public List<Map<String,Object>> queryDataSumByOrderIds(@Param("list")List<Integer> list);

	public List<OrderProducts> queryOrderProductStatus(String orderId);//根据订单号查询 为关闭的网单信息
	public String selectOutping(@Param("cOrderSn") String cOrderSn);
	public Map<String,Object> selectlessOrderSn(@Param("cOrderSn") String cOrderSn);
	public LesQueues queryisStop(@Param("id") Integer id);
	public Map<String,Object> selecctLockedNumber(@Param("cOrderSn") String cOrderSn);
	public List<Map<String,Object>> selectOPCount(@Param("orderId") Integer orderId);
	public Integer selectStatus(@Param("cOrderSn") String cOrderSn);
	public List<Map<String,Object>> queryOrderProductByTB(@Param("hpTbSn") String tbSn);
	public List<Map<String,Object>> queryRepairsOrderProductByTB(@Param("hpTbSn") String tbSn, @Param("sku") String sku);
	public List<Map<String,Object>> queryRepairsOrderProductByTBSKU(@Param("hpTbSn") String tbSn, @Param("sku") String sku);
	public List<Regions> getRegion(@Param("parentId")Integer parentId);
	public List<Regions> getRegionB2C(@Param("parentId")Integer parentId);
	public Regions getOneRegion(@Param("id")Integer id);
	public Orders getRegionName(@Param("orderSn") String orderSn);
	public List<Map<String,Object>> queryNetSheetExportDate(OrderProductsVo vo);
	public Map<String,Object> selectData(@Param("cOrderSn") String cOrderSn);

	List<OrderProducts> findByTradeSn(@Param("tradeSn")String tradeSn, @Param("sku")String sku);

	public List<Map<String,String>> getShippingModeAndStockType(@Param("orderSn")String orderSn);

	public List<Map<String,Object>> exportBadCommentsList(@Param("sourceOrderSn")List<String> sourceOrderSn);

	public OrderProductsVo selectIdAndIsMakeReceiptBycOrderSn(String cOrderSn);

}
