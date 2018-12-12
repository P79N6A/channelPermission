package com.haier.afterSale.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.haier.afterSale.model.Json;
import com.haier.common.ServiceResult;
import com.haier.shop.model.OrderProductsVo;
import com.haier.shop.model.OrderRepairLESRecords;
import com.haier.shop.model.OrderRepairsVo;
import com.haier.shop.model.OrderRepairshpLogs;
import com.haier.shop.model.OrderTmallReturnLogs;

/**
 * 网单列表
 * @author 吴坤洋 ～2017-10-24
 *
 */
public interface OperationAreaService {
	
	/**
	 * 网单列表(分页查询)
	 * @param bean
	 * @return
	 */
	ServiceResult<List<OrderProductsVo> >  searchList(OrderProductsVo params);
	
	/**
	 * 根据网单编号查询表单 与 商品明细
	 */
	OrderProductsVo PrudectView(String cOrderSn);
	
	/**
	 * 根据网单编号查询  商品明细
	 */
	OrderProductsVo PrudectDetailed(String cOrderSn);
	
	/**
	 * 保存退货信息
	 */
	Json SaveRepairs(OrderRepairsVo orderRepairs);
	/*
	* 订单操作日志
	* */
	List<Map<String,String>> selectOrderOperateLogs(String orderSn);
	/*
	* 优惠券信息查询
	* */
	List<Map<String,String>> selectCoupon(String orderSn);
	/*
	*根据订单编号查询表单 与 商品明细
	* */
	Map<String,String> orderNumberSelect(String orderSn);
	/*
	*
	* */
	List<Map<String,String>> selectPrudevtDatail(String orderSn);
	
	/**
	 * 查看退货信息
	 */
	Map<String,Object> ReturnEdit(String id);

	/**
	 * 退货审核
	 */
	Json Toexamine(String id,String status,String handleRemark);
	/*
	* @author zhangbo
	* hp拒收表格显示
	* */
	List<Map<String,String>> datagrid_WwwHpRecords(Map<String,Object> map);
	List<Map<String,String>> datagrid_WwwHpRecords1(Map<String,Object> map);
	//////查询在网单表已经存在的网单号并返回存在的网单号
	List<Map<String,Object>> check_cOrderSn_isExist(List<Map<String,Object>> list);
	//更新WwwHpRecords表中的匹配次数
	void update_WwwHpRecordsCount(List<String> list);
	//批量更新hp拒收flag字段
	void updateFlagBatch(List<String> list);
	//查询导出的数据
	List<Map<String,Object>> select_export_ExcelData(Map<String,Object> map);
	/**
	 * 推送修改信息到HP
	 * @param orderRepairsVo
	 * @return
	 * @throws ParseException 
	 * @throws MalformedURLException 
	 */
	Json ModifyPushHP(List<OrderRepairsVo> orderRepairsVo) throws ParseException, MalformedURLException;
	/*
	*解析hp推送结果  解析xml
	* */

	/*
	 * 接收天猫返回来的数据 插入到数据库
	 */
	Json TMReturnData(List<OrderTmallReturnLogs> orderTmallReturnLogs);
	/**
	 * 插入出入库信息
	 */
	int insert(OrderRepairLESRecords cords);

	/**
	 * 查询退货id 网单ID
	 */
    OrderRepairsVo queryOrderProductId(String id);
	//调用VOM http接口
    Json  CallHttpVOM(OrderRepairLESRecords cords) throws IOException, Exception;
	/**
	 * 插入保存 HP返沪的鉴定结果
	 * @param orderrepairHPrecords
	 * @return
	 */
    int insertHPrecords(OrderRepairshpLogs bean);
	List<Map<String,Object>> select_sku(List<Map<String,Object>> list);
	void update_WwwHpRecordsInfo(List<Map<String,Object>> list);
	Map<String,Object> select_ThInfo(String cOrderSn);
	String selectOrderSn(String cOrderSn);//根据网单号查询订单号

	List<Map<String,String>> datagrid_orderForecastGoal(Map<String, Object> map);//退换货列表显示
	List<Map<String,String>> datagrid_orderForecastGoalcount(Map<String, Object> map);//退货总记录数
	List<Map<String,Object>> export_ExcelOrderRepairs(Map<String, Object> map);//退换货列表导出数据查询

	
	/**
	 * 处理vom回传的信息
	 * @param vomReturnInforMation
	 * @return
	 */
//    String insertAnalyLogs(OrederVOMReturnLogs vomReturnLogs);
	
	/**
	 * 接收不良品返回过来的信息   http接口
	 * @return
	 * @throws ParseException 
	 */
    String HPReturnUnhealthyImpl(String obejecXml) throws ParseException;
	public List<String> selectHPlogsRowid(String rowid);//查询hp拒收日志表里面的rowid ,判断主键是否重复
	public void insertHPlogs(Map<String,Object> map);//hp推送信息插入到hp拒收日志表
	public void insertWwwHpRecords(Map<String,Object> map);
	
	  /**
     * 关闭退货单更改退货单状态
     * @param id
     * @param handleRemark
     * @return
     */
    int   RepairsTermination(String id,String handleRemark);

	int StockTransfer(String id, String handleRemark);
}
