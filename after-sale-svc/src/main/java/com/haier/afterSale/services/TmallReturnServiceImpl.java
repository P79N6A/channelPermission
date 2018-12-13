package com.haier.afterSale.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.haier.afterSale.model.Ustring;
import com.haier.afterSale.service.OperationAreaService;
import com.haier.afterSale.service.TmallReturnService;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.shop.model.*;
import com.haier.shop.service.*;


import com.haier.stock.service.VomOrderService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 定时匹配天猫返回信息表 进行匹配信息 生成退货单
 * @author wukunyang
 *吴坤洋 2017-11-25
 */
/*@Configuration
@EnableScheduling*/
@Service
public class TmallReturnServiceImpl implements TmallReturnService{
	private static Logger logs = LoggerFactory.getLogger(TmallReturnServiceImpl.class);
	@Autowired
	private ShopOperationAreaService shopOperationAreaService;
	@Autowired
	private OperationAreaService operationAreaService;
	@Autowired
	private ShopOrderRepairLogsService shopOrderRepairLogsService;
	@Autowired
	private ShopOrderRepairsService shopOrderRepairsService;
	@Autowired
	private RefundBillSyncRecordService syncRecordService;
	@Autowired
	private ShopOrdersService shopOrdersService;
    @Autowired
    private VomOrderService vomOrderService;
    @Autowired
    private LesQueuesService lesQueuesService;
//	@Scheduled(cron="0/5 * *  * * ?")
	public void executeUploadTask() {
		logs.info("定时匹配天猫返回信息表 进行匹配信息 生成退货单");
		// 间隔1分钟,执行工单上传任务
		List<RefundBillSyncRecord> list=syncRecordService.selectByPrimaryKey(1); //查询天猫数据日志表 所有未匹配成功的数据
		OrderRepairsVo orderRepairs = new OrderRepairsVo();
		if(list.size()<=0) {
			logs.info("定时匹配天猫返回信息表 进行匹配信息   没有数据");
		}
		for(int i=0;i<list.size();i++){
			RefundBillSyncRecord returnLogs = list.get(i);
			
			JSONObject obj = JSONObject.parseObject(JSONObject.parseObject(JSONObject.parseObject(returnLogs.getJdpResponse()).getString("refund_get_response")).getString("refund"));
			String status = obj.getString("status");
			
			Map map = JSON.parseObject(returnLogs.getJdpResponse());
			map = (Map) map.get("refund_get_response");
			map = (Map) map.get("refund");
			//根据交易订单号，来源单号 和物料号 用来和网单表匹配 如果匹配到 就自动生成一个退货单号  如果生成啦退货定单 退货单状态为：审核中 
			List<OrderProductsVo> productVo = null;

			if (StringUtils.isBlank(returnLogs.getSku())){
                productVo = shopOperationAreaService.queryTmallTimingoid(String.valueOf(returnLogs.getOid()), returnLogs.getTid().toString());
            }else{
                productVo = shopOperationAreaService.queryTmallTiming(returnLogs.getSku(), returnLogs.getSource(),returnLogs.getTid().toString());
            }
			if(productVo==null) {
				logs.info("定时匹配天猫返回信息表: 进行匹配信息 为匹配到对应的网单");
				continue;
			} 
			for(OrderProductsVo vo :productVo) {
				
				
				
				//查询是否已经有退货单 如果有的话则不继续生成退货单
				OrderRepairsVo orderRepairsVo=	shopOrderRepairsService.queryWhetherRepaiSn(vo.getId());
				if(orderRepairsVo==null){
					String repairsn= shopOrderRepairsService.queryRepaiSn(vo.getId());//查询此网单是否第一次退货
					//	    			orderRepairs.setId(shopOrderRepairLogsService.getNextValId());
					orderRepairs.setOrderProductId(vo.getId());//网单主键
					orderRepairs.setOrderId(vo.getOrowId());//订单主键
					orderRepairs.setPaymentName(vo.getPaymentName());//支付方式
					orderRepairs.setOfflineAmount(new BigDecimal(Ustring.getString(map.get("refund_fee"))));//退款金额 		PS:不确定退款金额 是什么计算方式 先用退款金额
					orderRepairs.setReason(Ustring.getString(map.get("reason")));//退款原因
					orderRepairs.setDescription(Ustring.getString(map.get("title")));//描述标题
					orderRepairs.setCount(1);//退货数量
					if("1".equals(vo.getIsMakeReceipt().toString())||"9".equals(vo.getIsMakeReceipt().toString())||"5".equals(vo.getIsMakeReceipt().toString())||"6".equals(vo.getIsMakeReceipt().toString())) {
						orderRepairs.setReceiptStatus(2); 
					}else if("2".equals(vo.getIsMakeReceipt().toString())) {
						orderRepairs.setReceiptStatus(1); 
					}
					orderRepairs.setPaymentStatus(5);
					//判断是否已出库
					if(Ustring.isNotEmpty(vo.getOutping())) {
						orderRepairs.setStorageStatus(1);
					}else {
						orderRepairs.setStorageStatus(2);
					}
					//退货订单处理
					if(repairsn!=null && !"".equals(repairsn)){
						StringBuffer sb = new StringBuffer();
						sb.append(repairsn);
						String s = sb.substring(sb.length()-1,sb.length());
						int thbh= +Integer.parseInt(s)+1;
						orderRepairs.setRepairSn(vo.getCOrderSn()+"TH"+thbh);
					}else {
						orderRepairs.setRepairSn(vo.getCOrderSn()+"TH2");//退货号
					}
					orderRepairs.setHandleRemark(" ");
					orderRepairs.setRequestServiceRemark(" ");
					orderRepairs.setRequestServiceDate(0L);
					orderRepairs.setOfflineFlag(0);
					orderRepairs.setCOrderSnStatus(vo.getStatus());
					orderRepairs.setContactMobile(vo.getMobile());//退货人手机号
					orderRepairs.setContactName(vo.getConsignee());//退货人姓名
					orderRepairs.setOfflineReason("  ");
					orderRepairs.setOfflineRemark(" ");
					orderRepairs.setHpFirstAddTime(0);
					orderRepairs.setHpSecondAddTime(0);
//					orderRepairs.setHandleStatus(1);//1审核中2进行中3受理完成
                    // 小家电退货单状态为"进行中"
					if ("B2C".equals(vo.getShippingMode()) || "CT01".equals(vo.getsCode()) || "JS01".equals(vo.getsCode())){
                        orderRepairs.setHandleStatus(2);//1审核中2进行中3受理完成
                    }else{
                        orderRepairs.setHandleStatus(1);//1审核中2进行中3受理完成
                    }
                    //海鹏退货单
                    if ("CT01".equals(vo.getsCode())){

                        orderRepairs.setTypeFlag(8);

                    }else if("JS01".equals(vo.getsCode())){
						orderRepairs.setTypeFlag(7);

					}else{

                        String signTim = shopOrderRepairsService.queryIsRejectionSign(Ustring.getString(vo.getId()));
                        if(signTim!=null && Integer.parseInt(signTim)>0){
                            orderRepairs.setTypeFlag(5); //5表式揽收
                        }else {
                            orderRepairs.setTypeFlag(4);//4表示拒收
//						orderRepairs.setHandleStatus(9);//如果是拒收的话
                        }

                    }

//					if(Ustring.isEmpty(vo.getOutping())){
//						orderRepairs.setHandleStatus(5);
//					}
					int RepairsId=shopOrderRepairsService.insertSelective(orderRepairs); //插入退货信息
					if(Ustring.isEmpty(Ustring.getString(RepairsId))) {
						logs.info("已生成退货单失败！");
						continue;
					}
					operationAreaService.ProcessLog(RepairsId, "系统", "登记", "淘宝海尔官方旗舰店同步退换货申请");

                    if ("B2C".equals(vo.getShippingMode())){
                        operationAreaService.ProcessLog(RepairsId, "系统", "审核通过", "小家电，系统自动审核通过");

                    }else if("CT01".equals(vo.getsCode())){
                        operationAreaService.ProcessLog(RepairsId, "系统", "审核通过", "海鹏，系统自动审核通过");
                    }else if("JS01".equals(vo.getsCode())){
                        operationAreaService.ProcessLog(RepairsId, "系统", "审核通过", "净水，系统自动审核通过");
                    }


                    if ("B2C".equals(vo.getShippingMode())){

                        Integer productStatus = vo.getStatus();
                        //判断是否已占用库存
                        if (productStatus >= 1 && productStatus != 110){

                            String productOutping = vo.getOutping();

                            //已出库
                            if (StringUtils.isNotBlank(productOutping)){
                                operationAreaService.ProcessLog(RepairsId, "系统", "提示信息", "小家电已出库 请联系顺丰拦截和库房拦截");

                            }else{//未出库
								Map<String,Object> lessOrderSn=operationAreaService.selectlessOrderSn(vo.getCOrderSn());

                                List<Map<String, Object>> lesQueuesMapList = lesQueuesService.checkOrderLessSuccess(vo.getId());

                                //已开提单
                                if ((lesQueuesMapList != null && lesQueuesMapList.size() > 0) || (productStatus >= 8 && productStatus != 110) || (lessOrderSn != null && !StringUtil.isEmpty(lessOrderSn.get("lessOrderSn").toString()))){

                                    VOMCancelOrderRequire vomCancelOrderRequire = new VOMCancelOrderRequire();
                                    vomCancelOrderRequire.setOrderNo(vo.getcOrderSn());
                                    vomCancelOrderRequire.setCancelType("1");
                                    ServiceResult<VOMOrderResponse> serviceResult = vomOrderService.cancelVomOrderInfo(vomCancelOrderRequire);

                                    JSONObject jsonObject =new JSONObject();
                                    if (serviceResult.getResult().getFlag().equals("T")){
                                        operationAreaService.ProcessLog(RepairsId, "系统", "提示信息", "小家电取消开提单成功 " + vo.getcOrderSn() + "返回结果：" + serviceResult.getResult().getFlag() + serviceResult.getResult().getMsg());
                                        JSONObject releaseResult = operationAreaService.releaseStock(vo.getCOrderSn());
                                        if (releaseResult != null && (boolean)releaseResult.get("flag")){
                                            operationAreaService.ProcessLog(RepairsId, "系统", "操作", "小家电取消开提单成功 释放库存成功");
                                        }else{
                                            operationAreaService.ProcessLog(RepairsId, "系统", "操作", "小家电取消开提单成功 释放库存失败！");
                                        }

                                        String msg = "小家电取消开提单成功";
                                        closeOrder(vo, RepairsId, msg);

                                    }else{
                                        operationAreaService.ProcessLog(RepairsId, "系统", "提示信息", "小家电取消开提单失败 请联系顺丰拦截和库房拦截");

                                    }

                                }else{//未开提单

                                    JSONObject releaseResult = operationAreaService.releaseStock(vo.getCOrderSn());
                                    if (releaseResult != null && (boolean)releaseResult.get("flag")){
                                        operationAreaService.ProcessLog(RepairsId, "系统", "操作", "小家电未出库未开提单 释放库存成功");
                                    }else{
                                        operationAreaService.ProcessLog(RepairsId, "系统", "操作", "小家电未出库未开提单 释放库存失败！");
                                    }
                                    String msg = "小家电未出库未开提单";
                                    closeOrder(vo, RepairsId, msg);

                                }

                            }

                        }else{//没有占用库存，逆向单受理完成；关闭正向单
                            String msg = "小家电未占用库存";
                            closeOrder(vo, RepairsId, msg);

                        }
                    }


					//判断 是否入库 如果未入库 退货单状态改成已驳回  网单状态改成取消关闭
//					if(Ustring.isEmpty(vo.getOutping())){
//						 shopOrderRepairsService.RepairsTermination(Ustring.getString(RepairsId), "5", "天猫匹配 未入库 退货单状态改成已驳回");
//						 operationAreaService.ProcessLog(RepairsId, "系统", "更改退货单状态", "天猫匹配 未入库 退货单状态改成已驳回");
//						List<OrderProducts> orderProducts=shopOperationAreaService.queryOrderProductStatus(vo.getOrowId().toString());
//						shopOperationAreaService.updateStatus(vo.getId().toString(),"110"); //发起二次鉴定的同时要把正向单关闭
//						 operationAreaService.ProcessLog(RepairsId, "系统", "系统联动取消网单的操作（先记录日志,后做取消网单的操作）", "淘宝海尔官方旗舰店同步退换货申请,未出库网单自动取消关闭");
//						 operationAreaService.ProcessLog(RepairsId, "系统", "状态由“待出库”变成“取消关闭”（联动取消）", "淘宝海尔官方旗舰店同步退换货申请,未出库网单自动取消关闭");
//						if(orderProducts.size()==1){
//							for(OrderProducts products: orderProducts) {
//								if(products.getcOrderSn().equals(vo.getcOrderSn())) {
//									shopOrdersService.updataOrdersStatus(vo.getOrowId().toString());//关闭订单
//									operationAreaService.ProcessLog(RepairsId, "系统", "系统联动取消订单的操作", "淘宝海尔官方旗舰店同步退换货申请,取消订单");
//								}
//							}
//						}
//					}
					syncRecordService.updataSynState(list.get(i).getId().toString());
//					logs.info("定时匹配天猫返回信息表 进行匹配信息 生成退货单  成功 退货单号"+orderRepairs.getRepairSn());
					//判断是否0秒退
					if("ZERO_SEC_REFUND_SUCCESS".equals(obj.getString("status"))) {
						//修改退货单状态为受理完成
						 shopOrderRepairsService.RepairsTermination(String.valueOf(RepairsId), "3", "0秒退 退货单状态修改为受理完成");
						//加入退货单受理完成日志
						operationAreaService.ProcessLog(RepairsId, "系统", "操作", "0秒退 退货单状态修改为受理完成");
						//根据订单号查询网单信息
						List<OrderProducts> orderProducts=shopOperationAreaService.queryOrderProductStatus(vo.getOrowId().toString());
						//正向单关闭(网单改为取消关闭)
						shopOperationAreaService.updateStatus(vo.getId().toString(),"110"); 
						//加入网单操作日志
						operationAreaService.ProcessLog(RepairsId, "系统", "系统联动取消网单的操作", "0秒退订单自动关闭网单");
						//如果对应的订单只有一条网单,则订单状态改为“已取消”。
						if(orderProducts.size()==1){
							for(OrderProducts products: orderProducts) {
								if(products.getcOrderSn().equals(vo.getcOrderSn())) {
									//关闭订单
									shopOrdersService.updataOrdersStatus(vo.getOrowId().toString());
									//添加订单操作日志
									operationAreaService.ProcessLog(RepairsId, "系统", "系统联动取消订单的操作", "0秒退订单自动关闭");
								}
							}
						}
					}
				}else {
					syncRecordService.updataSynState(list.get(i).getId().toString());
					//判断是否0秒退
					if("ZERO_SEC_REFUND_SUCCESS".equals(obj.getString("status"))) {
						//修改退货单状态为受理完成
						 shopOrderRepairsService.RepairsTermination(orderRepairsVo.getId().toString(), "3", "0秒退 未发货 退货单状态修改为受理完成");
						//加入退货单受理完成日志
						operationAreaService.ProcessLog(orderRepairsVo.getId(), "系统", "操作", "0秒退 退货单状态修改为受理完成");
						//根据订单号查询网单信息
						List<OrderProducts> orderProducts=shopOperationAreaService.queryOrderProductStatus(vo.getOrowId().toString());
						//正向单关闭(网单改为取消关闭)
						shopOperationAreaService.updateStatus(vo.getId().toString(),"110"); 
						//加入退货单操作日志
						operationAreaService.ProcessLog(orderRepairsVo.getId(), "系统", "系统联动取消网单的操作", "0秒退订单自动关闭网单");
						//如果对应的订单只有一条网单,则订单状态改为“已取消”。
						if(orderProducts.size()==1){
							for(OrderProducts products: orderProducts) {
								if(products.getcOrderSn().equals(vo.getcOrderSn())) {
									shopOrdersService.updataOrdersStatus(vo.getOrowId().toString());//关闭订单
									operationAreaService.ProcessLog(orderRepairsVo.getId(), "系统", "系统联动取消订单的操作", "0秒退订单自动关闭");
								}
							}
						}
					}
					//判断 是否入库 如果未入库 退货单状态改成已驳回  网单状态改成取消关闭
//					if(Ustring.isEmpty(vo.getOutping())){
//						 shopOrderRepairsService.RepairsTermination(orderRepairsVo.getId().toString(), "5", "天猫匹配 未入库 退货单状态改成已驳回");
//						 operationAreaService.ProcessLog(orderRepairsVo.getId(), "系统", "更改退货单状态", "天猫匹配 未入库 退货单状态改成已驳回");
//						List<OrderProducts> orderProducts=shopOperationAreaService.queryOrderProductStatus(vo.getOrowId().toString());
//						shopOperationAreaService.updateStatus(vo.getId().toString(),"110"); //发起二次鉴定的同时要把正向单关闭
//						 operationAreaService.ProcessLog(orderRepairsVo.getId(), "系统", "系统联动取消网单的操作（先记录日志,后做取消网单的操作）", "淘宝海尔官方旗舰店同步退换货申请,未出库网单自动取消关闭");
//						 operationAreaService.ProcessLog(orderRepairsVo.getId(), "系统", "状态由“待出库”变成“取消关闭”（联动取消）", "淘宝海尔官方旗舰店同步退换货申请,未出库网单自动取消关闭");
//						if(orderProducts.size()==1){
//							for(OrderProducts products: orderProducts) {
//								if(products.getcOrderSn().equals(vo.getcOrderSn())) {
//									shopOrdersService.updataOrdersStatus(vo.getOrowId().toString());//关闭订单
//									operationAreaService.ProcessLog(orderRepairsVo.getId(), "系统", "系统联动取消订单的操作", "淘宝海尔官方旗舰店同步退换货申请,取消订单");
//								}
//							}
//						}
//					}
				}
			}
		}
		//查询无包含关系的数据，并进行修改
		List<RefundBillSyncRecord> othersList=syncRecordService.selectByPrimaryKey(2); //查询天猫数据日志表 所有未匹配成功的数据
		for(int i = 0;i<othersList.size();i++) {
			syncRecordService.updataSynState(othersList.get(i).getId().toString());
		}

	}

    /**
     * 关闭逆向单 关闭正向单
     * @param vo
     * @param repairsId
     * @param msg
     */
    private void closeOrder(OrderProductsVo vo, int repairsId, String msg) {
        //修改退货单状态为受理完成
        shopOrderRepairsService.RepairsTermination(String.valueOf(repairsId), "3", msg + " 退货单状态修改为受理完成");
        //加入退货单受理完成日志
        operationAreaService.ProcessLog(repairsId, "系统", "操作", msg + " 退货单状态修改为受理完成");
        //正向单关闭(网单改为取消关闭)
        shopOperationAreaService.updateStatus(vo.getId().toString(),"110");
        operationAreaService.ProcessLog(repairsId, "系统", "系统联动取消网单的操作", msg + "订单自动关闭网单");

        Map<String,Object> logMap=operationAreaService.selectData(vo.getCOrderSn());
        logMap.put("operator","系统");
        logMap.put("changeLog","关闭网单,关闭理由:" + msg);
        logMap.put("remark",msg + ",系统联动关闭网单");
        logMap.put("logTime",new Date().getTime()/1000);
        operationAreaService.insertLog(logMap);
        //根据订单号查询网单信息
        List<OrderProducts> orderProducts=shopOperationAreaService.queryOrderProductStatus(vo.getOrowId().toString());
        //如果对应的订单只有一条网单,则订单状态改为“已取消”。
        if(orderProducts.size() == 0){
            //关闭订单
            shopOrdersService.updataOrdersStatus(vo.getOrowId().toString());
            //添加订单操作日志
            operationAreaService.ProcessLog(repairsId, "系统", "系统联动取消订单的操作", msg + "订单自动关闭");
            logMap.put("remark",msg + ",系统联动关闭订单");
            logMap.put("logTime",new Date().getTime()/1000);
            operationAreaService.insertLog(logMap);
        }
    }


}
