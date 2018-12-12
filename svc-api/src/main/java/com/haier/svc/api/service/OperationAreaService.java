/*
package com.haier.svc.api.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.haier.shop.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;

*/
/**
 * 网单
 * 吴坤洋 2017-10-25
 * @author wukunyang
 *
 *//*

@Service
public class OperationAreaService{
	@Autowired OperationAreaService operationAreaService;
	*/
/**
	 * 网单分页查询
	 * @param params
	 * @return
	 *//*

	public ServiceResult<List<OrderProductsVo> > searchList(OrderProductsVo params) {
		// TODO Auto-generated method stub searchList
		return operationAreaService.searchList(params);
	}
	
	*/
/**
	 * 根据网单编号查询表单 与 商品明细
	 *//*

	public OrderProductsVo PrudectView(String cOrderSn){
		return operationAreaService.PrudectView(cOrderSn);
	}
	*/
/**
	 * 查询明细 网单
	 *//*

	public OrderProductsVo PrudectDetailed(String cOrderSn){
		return operationAreaService.PrudectDetailed(cOrderSn);
	}
	 // 保存退货信息
	public Json SaveRepairs(OrderRepairsVo orderRepairs){
		return operationAreaService.SaveRepairs(orderRepairs);
	}
	//订单操作日志
	public List<Map<String,String>> selectOrderOperateLogs(String orderSn){
		return operationAreaService.selectOrderOperateLogs(orderSn);
	}

	//优惠券信息
	public List<Map<String,String>> selectCoupon(String orderSn){
		return operationAreaService.selectCoupon(orderSn);
	}
	//根据订单编号查询表单 与 商品明细
	public Map<String, String> orderNumberSelect(String orderSn){
		return  operationAreaService.orderNumberSelect(orderSn);
	}
	//订单详情里面的商品详情
	public List<Map<String,String>> selectPrudevtDatail(String orderSn){
		return  operationAreaService.selectPrudevtDatail(orderSn);
	}
	//根据退货id查询退货信息
	public Map<String ,Object> ReturnEdit(String id){
		return operationAreaService.ReturnEdit(id);
	}
	//退货审核
	public Json Toexamine(String id,String status,String handleRemark){
		return operationAreaService.Toexamine(id,status,handleRemark);
	}
	*/
/*
	* @author zhangbo
	* hp拒收
	* *//*

	public List<Map<String,String>> datagrid_WwwHpRecords(Map<String,Object> map){
		return operationAreaService.datagrid_WwwHpRecords(map);
	}
	//查询总记录数
	public List<Map<String,String>> datagrid_WwwHpRecords1(Map<String,Object> map){
		return operationAreaService.datagrid_WwwHpRecords1(map);
	}
	////查询在网单表已经存在的网单号并返回存在的网单号
	public List<Map<String,Object>> check_cOrderSn_isExist(List<Map<String,Object>> list){
		return operationAreaService.check_cOrderSn_isExist(list);
	}
	//更新WwwHpRecords表中的匹配次数
	public void update_WwwHpRecordsCount(List<String> list){
		 operationAreaService.update_WwwHpRecordsCount(list);
	}
	//批量修改hp拒收表flag属性
	public void updateFlagBatch(List<String> list){
		operationAreaService.updateFlagBatch(list);
	}
	//查询导出报表的数据
	public List<Map<String,Object>> select_export_ExcelData(Map<String,Object> map){
		return operationAreaService.select_export_ExcelData(map);
	}
	*/
/**
	 * 推送HP修改信息
	 * @param orderRepairsVo
	 * @return
	 * @throws ParseException 
	 * @throws MalformedURLException 
	 *//*

	public Json ModifyPushHP(List<OrderRepairsVo> orderRepairsVo) throws MalformedURLException, ParseException{
		return operationAreaService.ModifyPushHP(orderRepairsVo);
	}

	*/
/**
	 * 保存HP返回来的不良品信息
	 *//*

//	public int SaveHPUnhealthy(OrderhpRejectionLogs orderhpRejectionLogs){
//		return operationAreaService.SaveHPUnhealthy(orderhpRejectionLogs);
//	}
	public String analysisXml(String xmlString){
		return operationAreaService.analysisXml(xmlString);
	}
	*/
/**
	 * 用来接收天猫返回过来的数据 插入数据库
	 * @param orderTmallReturnLogs
	 * @return
	 *//*

	public Json TMReturnData(List<OrderTmallReturnLogs> orderTmallReturnLogs){
		return operationAreaService.TMReturnData(orderTmallReturnLogs);
	}
	*/
/*
	* 检查inv_machine_set表里面的主sku
	* *//*

	public List<Map<String,Object>> select_sku(List<Map<String,Object>> list){
		return operationAreaService.select_sku(list);
	}
	*/
/*
	* 更新HP拒收表信息
	* *//*

	public void update_WwwHpRecordsInfo(List<Map<String,Object>> list){
		operationAreaService.update_WwwHpRecordsInfo(list);
	}

	*/
/**
	 * 插入出入库信息
	 *//*

	public int insert(OrderRepairLESRecords cords){
		return operationAreaService.insert(cords);
	};
	*/
/**
	 * 查询退货id 网单ID
	 *//*

	public OrderRepairsVo queryOrderProductId(String id){
		return operationAreaService.queryOrderProductId(id);
	}

	public Json CallHttpVOM(OrderRepairLESRecords  cords) throws IOException{
		return operationAreaService.CallHttpVOM(cords);
	}
	*/
/**
	 * 插入保存HP回传回来的鉴定结果
	 * @param orderrepairHPrecords
	 * @return
	 *//*

	public int insertHPrecords(OrderRepairshpLogs bean){
		return operationAreaService.insertHPrecords(bean);
	}
	public Map<String,Object> select_ThInfo(String cOrderSn){
		return operationAreaService.select_ThInfo(cOrderSn);
	}
	public String selectOrderSn(String cOrderSn){
		return operationAreaService.selectOrderSn(cOrderSn);
	}

	*/
/*
	* 退换货列表显示数据
	* *//*

	public List<Map<String,String>> datagrid_orderForecastGoal(Map<String,Object> map){
		return operationAreaService.datagrid_orderForecastGoal(map);
	}
	//退货总记录数
	public List<Map<String,String>> datagrid_orderForecastGoalcount(Map<String,Object> map){
		return operationAreaService.datagrid_orderForecastGoalcount(map);
	}
	public List<Map<String,Object>> export_ExcelOrderRepairs(Map<String,Object> map){
		return operationAreaService.export_ExcelOrderRepairs(map);
	}
	*/
/**
	 * 处理vom回传的信息
	 * @param vomReturnInforMation
	 * @return
	 *//*

	public String insertAnalyLogs(OrederVOMReturnLogs vomReturnLogs){
		return operationAreaService.insertAnalyLogs(vomReturnLogs);

	}
	*/
/**
	 * 接收不良品返回过来的信息   http接口
	 * @return
	 * @throws ParseException 
	 *//*

	public String HPReturnUnhealthyImpl(String obejecXml) throws ParseException{
		return operationAreaService.HPReturnUnhealthyImpl(obejecXml);
	}
}
*/
