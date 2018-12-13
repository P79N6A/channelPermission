package com.haier.afterSale.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.shop.model.*;
import org.dom4j.DocumentException;
import org.json.JSONException;

import com.haier.afterSale.model.Json;
import com.haier.common.ServiceResult;

/**
 * 网单列表
 *
 * @author 吴坤洋 ～2017-10-24
 */
public interface OperationAreaService {

	/**
	 * 网单列表(分页查询)
	 *
	 * @param params
	 * @return
	 */
	ServiceResult<List<OrderProductsVo>> searchList(OrderProductsVo params);

	/**
	 * 根据网单编号查询表单 与 商品明细
	 */
	OrderProductsVo PrudectView(String cOrderSn);

	ExpressInfos findBycOrderSn(String cOrderSn);

	Map<String,String> selectMemberInvoicesByorderSn(String orderSn);
	/**
	 * 根据网单编号查询  商品明细
	 */
	OrderProductsVo PrudectDetailed(String cOrderSn);

	/**
	 * 保存退货信息
	 */
	Json SaveRepairs(OrderRepairsVo orderRepairs,String userName);

	/*
	* 订单操作日志
	* */
	List<Map<String, String>> selectOrderOperateLogs(String orderSn);

	/*
	* 优惠券信息查询
	* */
	List<Map<String, String>> selectCoupon(String orderSn);

	/*
	*根据订单编号查询表单 与 商品明细
	* */
	Map<String, String> orderNumberSelect(String orderSn);


	JSONArray getRegion(int id);

	JSONArray getRegionB2C(int id);

	String getRegionName(String orderSn);

	ServiceResult<Boolean> updateRegion(String orderSn,int province,int city,int region,int street,int id,String userName,String oldRegionName);
	/*
	*
	* */
	List<Map<String, String>> selectPrudevtDatail(String orderSn);

	/**
	 * 查看退货信息
	 */
	Map<String, Object> ReturnEdit(String id);

	/**
	 * 退货审核
	 */
	Json Toexamine(String id, String status, String handleRemark,String userName,String cOrderSn);

	/*
	* @author zhangbo
	* hp拒收表格显示
	* */
	List<Map<String, String>> datagrid_WwwHpRecords(Map<String, Object> map);

	List<Map<String, String>> datagrid_WwwHpRecords1(Map<String, Object> map);

	//////查询在网单表已经存在的网单号并返回存在的网单号
	List<Map<String, Object>> check_cOrderSn_isExist(List<Map<String, Object>> list);

	//更新WwwHpRecords表中的匹配次数
	void update_WwwHpRecordsCount(List<String> list);

	//批量更新hp拒收flag字段
	void updateFlagBatch(List<String> list);

	//查询导出的数据
	List<Map<String, Object>> select_export_ExcelData(Map<String, Object> map);

	/**
	 * 推送修改信息到HP
	 *
	 * @param orderRepairsVo
	 * @return
	 * @throws ParseException
	 * @throws MalformedURLException
	 */
	Json ModifyPushHP(List<OrderRepairsVo> orderRepairsVo,String userName) throws ParseException, MalformedURLException;
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
	Json CallHttpVOM(OrderRepairLESRecords cords) throws IOException, Exception;

	/**
	 * 插入保存 HP返沪的鉴定结果
	 *
	 * @return
	 * @throws JSONException
	 * @throws DocumentException
	 */
	String insertHPrecords(String obejecJson) throws JSONException, DocumentException, UnsupportedEncodingException;

	List<Map<String, Object>> select_sku(List<Map<String, Object>> list);

	void update_WwwHpRecordsInfo(List<Map<String, Object>> list);

	Map<String, Object> select_ThInfo(String cOrderSn);

	OrderRepairs selectOrderSn(String cOrderSn);//根据网单号查询订单号

	List<Map<String, String>> datagrid_orderForecastGoal(Map<String, Object> map);//退换货列表显示

	List<Map<String, String>> datagrid_orderForecastGoalcount(Map<String, Object> map);//退货总记录数

	List<Map<String, Object>> export_ExcelOrderRepairs(Map<String, Object> map);//退换货列表导出数据查询


	/**
	 * 处理vom回传的信息
	 * @param vomReturnInforMation
	 * @return
	 */
//    String insertAnalyLogs(OrederVOMReturnLogs vomReturnLogs);

	/**
	 * 接收不良品返回过来的信息   http接口
	 *
	 * @return
	 * @throws ParseException
	 */
	String HPReturnUnhealthyImpl(String obejecXml) throws ParseException;

	public List<String> selectHPlogsRowid(String rowid);//查询hp拒收日志表里面的rowid ,判断主键是否重复

	public List<Map<String, Object>> selectAllHPlogs();

	public void insertHPlogs(Map<String, Object> map);//hp推送信息插入到hp拒收日志表

	public void insertWwwHpRecords(Map<String, Object> map);

	/**
	 * 关闭退货单更改退货单状态
	 *
	 * @param id
	 * @param handleRemark
	 * @return
	 */
	Json RepairsTermination(String id, String handleRemark,String userName);

	Json Rminatereverse(String id, String handleRemark,String terminationReason,String userName,String handleStatus,String pd);

	Json StockTransfer(String id, String handleRemark,String userName);

	/**
	 * 操作网单日志
	 *
	 * @param productId
	 * @return
	 */
	List<OrderOperateLogs> getProductIdVdiew(String productId);
	//查询出库凭证号
	public String selectOutping(String cOrderSn);
	//查询开提单
	public Map<String,Object> selectlessOrderSn(String cOrderSn);
	//查询开提单队列
	public Map<String,Object> queryisStop(Integer id);
	//查询锁定库存的数量
	public Map<String,Object> selecctLockedNumber(String cOrderSn);
	//关闭网单和更新曾经释放的数量
	public void updateOPStatus(Integer lockedNumber,String cOrderSn);
	//在网单表中查询orderId一样的数据
	public List<Map<String,Object>> selectOPCount(Integer orderId);
	//更改订单状态为202(取消订单)
	public void updataOrderStatus(Integer id);
	//更改LesQueues的isStop=3
	public void updateLesQueuesIsStop(Integer id);
	//查询网单状态
	public Integer selectStatus(String cOrderSn);
	////将HPQueues的issuccess状态改为3
	public void updateHPQueuessuccess(Integer id);

	public List<OrderRepairLESRecords> queryRecordSn(String operate,String storageType,String orderRepairId);
	public int ProcessLog(Integer orderReparsId,String userName,String operate,String remark);

	public JSONObject cancelDispatchedWorkers(String cOrderSn);
	public  List<Map<String,Object>> queryOrderProductByTB(String tbSn);
	public void insertOrderRepairLog(Map<String,Object> map);
	
	public Json establishTenLibrary(String id,String userName);

	public Json b2cestablishTenLibrary(String id, String userName);
	public Json establishTenLibraryOnLine(String id, String userName);
	public List<Map<String,Object>> queryNetSheetExportDate(OrderProductsVo vo);

    public JSONObject releaseStock(String cOrderSn);

	public Map<String,Object> selectData(String cOrderSn);
	public void insertLog(Map<String,Object> map);
	/**
	 * 根据网单号查询是否已下发顺丰
	 * @param orderNo
	 * @return
	 */
	public int queryCountByOrderNo(String orderNo);

	/**
	 *
	 * @param id
	 * @param success
	 * @return
	 */
	public int updateRepairLesRecordcnSuccess(String id, Integer success);

    /**
     *
     * @param id
     * @return
     */
    public List<OrderRepairLESRecords> queryLesreCodrdsId(String id);


	public ServiceResult<JSONObject> Rejectionsinglereset(String id);
}
