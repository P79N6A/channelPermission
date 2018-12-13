package com.haier.afterSale.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import com.alibaba.fastjson.JSONArray;
import com.haier.common.util.StringUtil;
import com.haier.eis.service.EisVomShippingStatusService;
import com.haier.shop.model.*;
import com.haier.shop.service.*;
import com.haier.stock.service.StockCenterHopStockService;
import com.haier.stock.service.VomOrderService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.json.JSONException;
import org.json.XML;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.haier.afterSale.model.CargoStatus;
import com.haier.afterSale.model.Json;
import com.haier.afterSale.model.MemberInvoicesStatus;
import com.haier.afterSale.model.OrderRepairsStatus;
import com.haier.afterSale.model.PaymentStatus;
import com.haier.afterSale.model.Ustring;
import com.haier.afterSale.model.VOMOrderModel;
import com.haier.afterSale.service.OperationAreaService;
import com.haier.afterSale.util.Base64Util;
import com.haier.afterSale.util.HelpUtils;
import com.haier.afterSale.util.MD5util;
import com.haier.afterSale.util.XmlUtils;
import com.haier.afterSale.webService.cancelDispatchedWorkers.Inputs;
import com.haier.afterSale.webService.cancelDispatchedWorkers.Outputs;
import com.haier.afterSale.webService.cancelDispatchedWorkers.TransOrderCancelFromEHAIERToHP;
import com.haier.afterSale.webService.cancelDispatchedWorkers.TransordercancelfromehaiertohpClientEp;
import com.haier.afterSale.webService.pushHP.InsertDataToHP;
import com.haier.afterSale.webService.pushHP.InsertDataToHP_Service;
import com.haier.afterSale.webService.pushHP.InsertDataToHP_Type;
import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.eis.model.EisExternalSku;
import com.haier.eis.model.VomInOutStoreOrder;
import com.haier.eis.service.EisExternalSkuService;
import com.haier.eis.service.EisVomInOutStoreOrderService;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.model.OrderProductStatus;
import com.haier.stock.service.StockInvMachineSetService;

import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

import static com.haier.stock.model.InventoryBusinessTypes.RELEASE_BY_ZBCC;

/**
 * 网单 吴坤洋 2017-10-25
 * 
 * @author wukunyang
 *
 */
@Service
public class OperationAreaServiceImpl implements OperationAreaService {
	private static org.slf4j.Logger log = LoggerFactory.getLogger(OperationAreaServiceImpl.class);
	// private static org.apache.log4j.Logger log =
	// org.apache.log4j.LogManager.getLogger(OperationAreaServiceImpl.class);
	private static final Logger logger = LogManager.getLogger(OperationAreaServiceImpl.class);
	private static final QName SERVICE_NAME = new QName("http://www.example.org/InsertDataToHP/", "InsertDataToHP");
	public final static QName SERVICE1 = new QName(
			"http://xmlns.oracle.com/MyFirstSOA/TransOrderCancelFromEHAIERToHP/TransOrderCancelFromEHAIERToHP",
			"transordercancelfromehaiertohp_client_ep");
	// @Autowired
	// HttpServletRequest request;
	@Autowired
	private ShopInvoiceService shopInvoiceService;
	@Autowired
	private ShopOperationAreaService shopOperationAreaService;

    @Autowired
    private StockCenterHopStockService stockCenterHopStockService;

	@Autowired
	private ShopOrderRepairsService shopOrderRepairsService;// 退货

	@Autowired
	private ShopOrderRepairLogsService shopOrderRepairLogsService;// 退货日志
	@Autowired
	private RegionsService regionsService;
	@Autowired
	private ShopOrderrepairHPrecordsService shopOrderrepairHPrecordsService;// hp回传数据

	@Autowired
	private ShopOrderRepairLesreCordsService shopOrderRepairLesreCordsService;// 出入库状态

	@Autowired
	private HelpUtils helpUtils;
	@Autowired
	private ShopOrderhpRejectionLogsService shopOrderhpRejectionLogsService;

	@Autowired
	private ShopOrderTmallReturnLogsService shopOrderTmallReturnLogsService;

	@Autowired
	private ShopDepartMentCodeSkuService departMentCodeSkuService;

	@Autowired
	private StockInvMachineSetService stockInvMachineSetService;
	@Autowired
	private ShopOrdersService shopOrdersService;// 订单

	@Autowired
	private ShopOrderRepairshpLogsService shopOrderRepairshpLogsService;

	@Autowired
	private ShopOrederVOMReturnLogsService shopOrederVOMReturnLogsService;

	@Autowired
	private ShopOrderVOMReturnAnalysisService shopOrderVOMReturnAnalysisService;

	@Autowired
	private ShopOrderVOMReturnAnalysisDetailedService shopOrderVOMReturnAnalysisDetailedService;
	@Autowired
	private StockInvMachineSetService invMachineSetDao;
	@Autowired
	private VOMOrderModel vomOrderModel;
	@Autowired
	private EisExternalSkuService eisExternalSkuService;
	@Autowired
	private HPIdentificationResultService hpIdentificationResultService;
	@Autowired
	private StoragesService storagesService;
	@Autowired
	private ShopOrderOperateLogsService shopOrderOperateLogsService;
	@Autowired
	private EisVomInOutStoreOrderService eisVomInOutStoreOrderService;
	@Autowired
	private InterfaceLogService interfaceLogService;
	@Autowired
    private ShopOrderProductsService shopOrderProductsService;
	@Autowired
    private ShopMemberInvoicesService shopMemberInvoicesService;
	@Autowired
	private OrderProductsNewService orderProductsNewService;
	@Autowired
	private EisVomShippingStatusService eisVomShippingStatusService;
	@Autowired
	private OrdersService ordersService;
	@Value("${wsdlPath}")
	private String wsdlPath;
    @Autowired
    private VomOrderService vomOrderService;
	@Autowired
	private ExpressInfosService expressInfosService;
	@Override
	public ServiceResult<List<OrderProductsVo>> searchList(OrderProductsVo params) {
		// TODO Auto-generated method stub
		ServiceResult<List<OrderProductsVo>> result = new ServiceResult<List<OrderProductsVo>>();
		List<OrderProductsVo> list = shopOperationAreaService.queryOderProductList(params);
		for (int i = 0; i < list.size(); i++) {
			String ss = "";
			if (null != list.get(i).getStatus() && !"".equals(list.get(i).getStatus())) {
				ss = OrderProductStatus.getByCode(Integer.parseInt(list.get(i).getStatus().toString())).getName();
			}
			list.get(i).setStatusTs(ss);
		}
		result.setResult(list);
		PagerInfo pi = new PagerInfo();
		pi.setRowsCount(shopOperationAreaService.findOrderProductCNT(params));
		result.setPager(pi);
		return result;
	}

	/**
	 * 根据网单编号 查询表单
	 */
	@Override
	public OrderProductsVo PrudectView(String cOrderSn) {
		OrderProductsVo productVo = shopOperationAreaService.queryOrdeProduct(cOrderSn);// 查询页面表单
		if (null != productVo) {
			if (productVo.getIstate() != null && !"".equals(productVo.getIstate())) {
				productVo.setIstate(MemberInvoicesStatus.getByCode(Integer.parseInt(productVo.getIstate())).getName()); // 发票状态转中文
			}
			productVo.setType(InvoiceConst.INVOICE_TYPE.get(Ustring.getString(productVo.getType())));
			productVo.setEaiWriteState(InvoiceConst.EAI_WRITE_STATE.get(productVo.getEaiWriteState()));
			if ("1".equals(productVo.getElectricFlag())) {
				productVo.setElectricFlag("是");
			} else if ("0".equals(productVo.getElectricFlag())) {
				productVo.setElectricFlag("否");
			}
			productVo
					.setMakeReceiptTypeStatus(InvoiceConst.MAKE_RECEIPT_TYPE_NAMES.get(productVo.getMakeReceiptType()));
			productVo.setIsMakeReceiptStatus(
					InvoiceConst.MAKE_RECEIPT_STATE_OPTIONS.get(Ustring.getString(productVo.getIsMakeReceipt())));
		}
		return productVo;
	}

	@Override
	public ExpressInfos findBycOrderSn(String cOrderSn) {
		return expressInfosService.findBycOrderSn(cOrderSn);
	}

	@Override
	public Map<String, String> selectMemberInvoicesByorderSn(String orderSn) {
		Map<String, String> map = shopOperationAreaService.selectMemberInvoicesByorderSn(orderSn);
		return map;
	}

	/**
	 * 根据网单编号查询明细
	 */
	@Override
	public OrderProductsVo PrudectDetailed(String cOrderSn) {
		OrderProductsVo productCommodity = shopOperationAreaService.queryCommodity(cOrderSn);// 查询商品明细
		productCommodity.setIsMakeReceiptStatus(
				InvoiceConst.MAKE_RECEIPT_STATE_OPTIONS.get(Ustring.getString(productCommodity.getIsMakeReceipt())));
		return productCommodity;
	}

	/**
	 * 查询网单操作日志
	 * 
	 * @param productId
	 * @return
	 */
	@Override
	public List<OrderOperateLogs> getProductIdVdiew(String productId) {
		return shopOrderOperateLogsService.getProductIdVdiew(productId);
	}

	/**
	 * 保存退货信息
	 */
	@Override
	// @Transactional(rollbackFor = Exception.class)
	public Json SaveRepairs(OrderRepairsVo orderRepairs, String userName) {
		// HttpSession session = request.getSession();
		Json json = new Json();
		int i = 0;
		OrderProductsVo productVo = shopOperationAreaService.queryOrdeProduct(orderRepairs.getcOrderSnId()); // 查询网单详细信息
		// orderRepairs.setId(shopOrderRepairLogsService.getNextValId());//

        List<OrderRepairsVo> orderRepairsVoList = shopOrderRepairsService.selectOrderRepairsNOFinish(String.valueOf(productVo.getId()));
        if (orderRepairsVoList != null && orderRepairsVoList.size() > 0){
            json.setMsg("已有退货单，退货单号：" + orderRepairsVoList.get(0).getRepairSn() + ",请勿重复创建。");
            json.setSuccess(false);
            return json;
        }

        orderRepairs.setOrderProductId(productVo.getId());// 网单主键
		orderRepairs.setOrderId(productVo.getOrowId());// 订单主键
		orderRepairs.setPaymentName(productVo.getPaymentName());// 支付方式
		orderRepairs.setOfflineAmount(productVo.getPrice());// 退款金额 PS:不确定退款金额 是什么计算方式 先用退款金额
		orderRepairs.setHandleRemark(" ");
		orderRepairs.setRequestServiceRemark(" ");
		if ("1".equals(productVo.getIsMakeReceipt().toString()) || "9".equals(productVo.getIsMakeReceipt().toString())
				|| "5".equals(productVo.getIsMakeReceipt().toString())
				|| "6".equals(productVo.getIsMakeReceipt().toString())) {
			orderRepairs.setReceiptStatus(2);
		} else if ("2".equals(productVo.getIsMakeReceipt().toString())) {
			orderRepairs.setReceiptStatus(1);
		}

		orderRepairs.setPaymentStatus(5);
		// 判断是否已出库
		if (Ustring.isNotEmpty(productVo.getOutping())) {
			orderRepairs.setStorageStatus(1);
		} else {
			orderRepairs.setStorageStatus(2);
		}
		orderRepairs.setRequestServiceDate(0L);
		orderRepairs.setOfflineFlag(0);
		orderRepairs.setOfflineReason("  ");
		orderRepairs.setOfflineRemark("  ");
		orderRepairs.setHpFirstAddTime(0);
		orderRepairs.setHpSecondAddTime(0);
		orderRepairs.setCOrderSnStatus(productVo.getStatus());
		if ("1".equals(orderRepairs.getType())) {
			orderRepairs.setHandleStatus(3);// 1审核中2进行中3受理完成
		} else {
            // 小家电退货单状态为"进行中"
            if ("B2C".equals(productVo.getShippingMode()) || "CT01".equals(productVo.getsCode()) || "JS01".equals(productVo.getsCode())){
                orderRepairs.setHandleStatus(2);//1审核中2进行中3受理完成
            }else{
                orderRepairs.setHandleStatus(1);//1审核中2进行中3受理完成
            }
		}
		String repairsn = shopOrderRepairsService.queryRepaiSn(productVo.getId());// 查询此网单是否第一次退货
		// 退货订单处理
		if (repairsn != null && !"".equals(repairsn)) {
            String repairNum = repairsn.substring(repairsn.indexOf("TH") + 2);
			int thbh = +Integer.parseInt(repairNum) + 1;
			orderRepairs.setRepairSn(productVo.getCOrderSn() + "TH" + thbh);
			// json.setMsg("此网单已经有关联退货单号！");
			// json.setSuccess(true);
			// return json;
		} else {
			orderRepairs.setRepairSn(productVo.getCOrderSn() + "TH2");// 退货号
		}

        //海鹏退货单
        if ("CT01".equals(productVo.getsCode())){
            orderRepairs.setTypeFlag(8);
        }else if("JS01".equals(productVo.getsCode())){
            orderRepairs.setTypeFlag(7);
		}else{
            String signTim = shopOrderRepairsService.queryIsRejectionSign(Ustring.getString(productVo.getId()));
            if (signTim != null && Integer.parseInt(signTim) > 0) {
                orderRepairs.setTypeFlag(5); // 5表式揽收
            } else {
                orderRepairs.setTypeFlag(4);// 4表示拒收
            }
        }

		orderRepairs.setCount(1);// 退货数量
		int orderRepairsId = shopOrderRepairsService.insertSelective(orderRepairs);
		orderRepairs.getRepairSn();// 插入退货信息
		OrderRepairLogs log = new OrderRepairLogs(); // new出来一个退货日志
		log.setOrderRepairId(orderRepairsId);// 退货id
		log.setSiteId(0);
		log.setOperator(userName);
		log.setOperate("登记");
		log.setRemark("淘宝海尔官方旗舰店同步退换货申请");
		i = i + shopOrderRepairLogsService.insert(log); // 记录退货操作流程 日志

        if ("B2C".equals(productVo.getShippingMode())){

            Integer productStatus = productVo.getStatus();
            //判断是否已占用库存
            if (productStatus >= 1 && productStatus != 110){

                String productOutping = productVo.getOutping();

                //已出库
                if (StringUtils.isNotBlank(productOutping)){
                    ProcessLog(orderRepairsId, "系统", "提示信息", "小家电已出库 请联系顺丰拦截和库房拦截");

                }else{//未出库

                    //已开提单
                    if (productStatus >= 8 && productStatus != 110){

                        VOMCancelOrderRequire vomCancelOrderRequire = new VOMCancelOrderRequire();
                        vomCancelOrderRequire.setOrderNo(productVo.getcOrderSn());
                        vomCancelOrderRequire.setCancelType("1");
                        ServiceResult<VOMOrderResponse> serviceResult = vomOrderService.cancelVomOrderInfo(vomCancelOrderRequire);

                        JSONObject jsonObject =new JSONObject();
                        if (serviceResult.getResult().getFlag().equals("T")){
							JSONObject releaseResult = releaseStock(productVo.getcOrderSn());
                            if (releaseResult != null && (boolean)releaseResult.get("flag")){
                                ProcessLog(orderRepairsId, "系统", "操作", "小家电取消开提单成功 释放库存成功");
                            }else{
                                ProcessLog(orderRepairsId, "系统", "操作", "小家电取消开提单成功 释放库存失败！");
                            }
                            String msg = "小家电取消开提单成功";
                            closeOrder(productVo, orderRepairsId, msg);

                        }else{
                            ProcessLog(orderRepairsId, "系统", "提示信息", "小家电取消开提单失败 请联系顺丰拦截和库房拦截");

                        }

                    }else{//未开提单

                        JSONObject releaseResult = releaseStock(productVo.getcOrderSn());
                        if (releaseResult != null && (boolean)releaseResult.get("flag")){
                            ProcessLog(orderRepairsId, "系统", "操作", "小家电未出库未开提单 释放库存成功");
                        }else{
                            ProcessLog(orderRepairsId, "系统", "操作", "小家电未出库未开提单 释放库存失败！");
                        }
                        String msg = "小家电未出库未开提单";
                        closeOrder(productVo, orderRepairsId, msg);

                    }

                }

            }else{//没有占用库存，逆向单受理完成；关闭正向单
                String msg = "小家电未占用库存";
                closeOrder(productVo, orderRepairsId, msg);

            }
        }


		if (orderRepairsId > 1) {
			json.setMsg("保存退货信息成功！");
			json.setObj(orderRepairsId); // 传到前台 退货主键id
			json.setSuccess(true);
		} else {
			json.setMsg("保存退货信息失败！");
			json.setSuccess(false);
		}
		return json;
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
        ProcessLog(repairsId, "系统", "操作", msg + " 退货单状态修改为受理完成");
        //正向单关闭(网单改为取消关闭)
        shopOperationAreaService.updateStatus(vo.getId().toString(),"110");
        ProcessLog(repairsId, "系统", "系统联动取消网单的操作", msg + "订单自动关闭网单");
        Map<String,Object> logMap = selectData(vo.getCOrderSn());
        logMap.put("operator","系统");
        logMap.put("changeLog","关闭网单,关闭理由:" + msg);
        logMap.put("remark",msg + ",系统联动关闭网单");
        logMap.put("logTime",new Date().getTime()/1000);
        insertLog(logMap);

        //根据订单号查询网单信息
        List<OrderProducts> orderProducts=shopOperationAreaService.queryOrderProductStatus(vo.getOrowId().toString());
        //如果对应的订单只有一条网单,则订单状态改为“已取消”。
        if(orderProducts.size() == 0){
			//关闭订单
			shopOrdersService.updataOrdersStatus(vo.getOrowId().toString());
			//添加订单操作日志
			ProcessLog(repairsId, "系统", "系统联动取消订单的操作", msg + "订单自动关闭");
            logMap.put("remark",msg + ",系统联动关闭订单");
            logMap.put("logTime",new Date().getTime()/1000);
            insertLog(logMap);
        }
    }

	/**
	 * 查询 退货 数据
	 */
	@Override
	public Map<String, Object> ReturnEdit(String id) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		OrderRepairsVo vo = shopOrderRepairsService.queryPairsId(id);// 查询退货详细信息
		vo.setPaymentStatusTS(PaymentStatus.getByCode(vo.getPaymentStatus().intValue()).getName());
		vo.setReceiptStatusTS(MemberInvoicesStatus.getByCode(vo.getReceiptStatus().intValue()).getName());
		vo.setStorageStatusTS(CargoStatus.getByCode(vo.getStorageStatus().intValue()).getName());
		vo.setStatus(OrderRepairsStatus.getByCode(vo.getHandleStatus()).getName());
		List<OrderRepairLogs> list = new ArrayList<OrderRepairLogs>();
		if (vo != null) {
			list = shopOrderRepairLogsService.queryLogs(vo.getRepairSn());// 查询退货操作日志
		}
		List<OrderRepairLESRecords> repairLesreCords = shopOrderRepairLesreCordsService.queryLesreCodrdsId(id);
		for (int i = 0; i < repairLesreCords.size(); i++) {
			if ("1".equals(repairLesreCords.get(i).getOperate().toString())) {
				repairLesreCords.get(i).setOperateStatus("出库");
			} else if ("2".equals(repairLesreCords.get(i).getOperate().toString())) {
				repairLesreCords.get(i).setOperateStatus("入库");
			}
			repairLesreCords.get(i)
					.setSuccessStatus(OrderRepairsConst.SUCCESSTIDANMAP.get(repairLesreCords.get(i).getSuccess()));
		}
		List<OrderrepairHPrecordsVO> hpRecordsList = shopOrderrepairHPrecordsService.selectByHpreCordsId(id); // HP退货工单状态
		for (int i = 0; i < hpRecordsList.size(); i++) {
			hpRecordsList.get(i).setCheckResultStatus(Ustring.getString(OrderRepairsConst.CHECKRESULTMAP
					.get(Integer.parseInt(Ustring.getString0(hpRecordsList.get(i).getCheckResult())))));
			hpRecordsList.get(i).setQualityStatus(Ustring.getString(OrderRepairsConst.QUALITYMAP
					.get(Integer.parseInt(Ustring.getString0(hpRecordsList.get(i).getQuality())))));
			hpRecordsList.get(i).setCheckTypeStatus(Ustring.getString(OrderRepairsConst.CHECKTYPEMAP
					.get(Integer.parseInt(Ustring.getString0(hpRecordsList.get(i).getCheckType())))));
			hpRecordsList.get(i).setHpOrderFailStatus(Ustring.getString(OrderRepairsConst.HPORDERMAP
					.get(Integer.parseInt(Ustring.getString0(hpRecordsList.get(i).getHpOrderFail())))));
			if ("3".equals(hpRecordsList.get(i).getCheckType().toString())) {
				hpRecordsList.get(i).setSecondaryTypeStatus(Ustring.getString(
						OrderRepairsConst.PACKRESULT_3MAP.get(hpRecordsList.get(i).getPackResult().intValue())));
			} else if ("2".equals(hpRecordsList.get(i).getCheckType().toString())) {
				hpRecordsList.get(i).setSecondaryTypeStatus(Ustring.getString(
						OrderRepairsConst.PACKRESULTMAP_2MAP.get(hpRecordsList.get(i).getPackResult().intValue())));
			}
		}
		map.put("vo", vo);
		map.put("list", gson.toJson(list));
		map.put("hpRecords", gson.toJson(hpRecordsList));
		map.put("repairLesreCords", gson.toJson(repairLesreCords));
		return map;
	}

	/*
	 *
	 * 订单操作日志
	 */
	@Override
	public List<Map<String, String>> selectOrderOperateLogs(String orderSn) {
		return shopOperationAreaService.selectOrderOperateLogs(orderSn);
	}

	/*
	 * 优惠券信息查询
	 */
	@Override
	public List<Map<String, String>> selectCoupon(String orderSn) {
		return shopOperationAreaService.selectCoupon(orderSn);
	}

	/*
	 * 根据订单表号查询明细
	 */
	@Override
	public Map<String, String> orderNumberSelect(String orderSn) {
		Map<String, String> map = shopOperationAreaService.orderNumberSelect(orderSn);
		List<Map<String, String>> getMode = shopOperationAreaService.getShippingModeAndStockType(orderSn);
		String shippingMode = "";
		String stockType = "";
		boolean flag = false;
		boolean flagStockType = false;
		if (getMode != null) {
			for ( Map<String, String> m : getMode) {
				if ( "B2B2C".equals(m.get("shippingMode"))) {
					shippingMode = "B2B2C";
				}
				if ("B2C".equals(m.get("shippingMode"))) {
					shippingMode = "B2C";
					flag = true;
				}
				if ("WA".equals(m.get("stockType"))){
					stockType = "WA";
				}
				if ("3W".equals(m.get("stockType"))){
					stockType = "3W";
					flagStockType = true;
				}
			}
		}
		if (flag){
			shippingMode = "B2C";
		}
		if (flagStockType){
			stockType = "3W";
		}
		Map<String, String> setMode = new HashMap<String, String>();
		setMode.put("shippingMode",shippingMode);
		setMode.put("stockType",stockType);
		Map<String, String> map1 = shopOperationAreaService.selectMemberInvoicesByorderSn(orderSn);
		String a = String.valueOf(map.get("id"));
		Integer valueOf = Integer.valueOf(a);
		MemberInvoices memberInvoices = shopMemberInvoicesService.getByOrderId(valueOf);
		if(memberInvoices!=null) {
			if(memberInvoices.getInvoiceType()!=null) {
				map.put("invoiceOperationType", memberInvoices.getInvoiceType().toString());
			}
		}
		// 根据OrderId获得OrderProducts信息
        List<OrderProducts> orderProductsList = shopOrderProductsService
                .getOrderProductsByOrderId(valueOf);
        // 获得所有的Id信息
        List<Integer> idsList = new ArrayList<Integer>();
        if (orderProductsList != null && orderProductsList.size() > 0) {
            for (OrderProducts eachOrderProducts : orderProductsList) {
                idsList.add(eachOrderProducts.getId());
            }
        }
        // 根据ID信息取得OrderProducts信息
        if (idsList.size() > 0) {
            orderProductsList.clear();
            orderProductsList = shopOrderProductsService.getByIds(idsList);
            if (orderProductsList != null && orderProductsList.size() > 0) {
                for (OrderProducts eachOrderProducts : orderProductsList) {
                    // 已开票的场合
                    if (eachOrderProducts.getIsMakeReceipt() != null
                            && eachOrderProducts.getIsMakeReceipt().equals(2)) {
                        map.put("invoiceOperation", "1");
                        break;
                    }
                    if (eachOrderProducts.getIsMakeReceipt() != null
                            && eachOrderProducts.getIsMakeReceipt().equals(3)) {
                    	map.put("invoiceOperation", "2");
                    }
                }
            }
        }
		
		if (map1 != null) {
			map.putAll(map1);
		}
		if (getMode != null){
			map.putAll(setMode);
		}
		return map;
	}

	@Override
	public JSONArray getRegion(int id) {
		List<Regions> regions = shopOperationAreaService.getRegion(id);
		Regions r = new Regions();
		r.setRegionName("请选择");
		r.setId(0);
		List<Regions> regions1 = new ArrayList<>();
		regions1.add(r);
		regions1.addAll(regions);
		return JSONArray.parseArray(JSON.toJSONString(regions1));
	}

	@Override
	public JSONArray getRegionB2C(int id) {
		List<Regions> regions = shopOperationAreaService.getRegionB2C(id);
		Regions r = new Regions();
		r.setRegionName("请选择");
		r.setId(0);
		List<Regions> regions1 = new ArrayList<>();
		regions1.add(r);
		regions1.addAll(regions);
		return JSONArray.parseArray(JSON.toJSONString(regions1));
	}

	@Override
	public String getRegionName(String orderSn) {
		return shopOperationAreaService.getRegionName(orderSn).getRegionName();
	}

	@Override
	public ServiceResult<Boolean> updateRegion(String orderSn, int province, int city, int region, int street,int id,String userName,String oldRegionName) {
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();
		String province1 = shopOperationAreaService.getOneRegion(province).getRegionName();
		String city1 = shopOperationAreaService.getOneRegion(city).getRegionName();
		String region1 = shopOperationAreaService.getOneRegion(region).getRegionName();
		String regionName = province1 + " " + city1 + " " + region1;
		if (street != 0) {
			String street1 = shopOperationAreaService.getOneRegion(street).getRegionName();
			regionName = regionName + " " + street1;
		}

		OrderOperateLogs orderOperateLogs = new OrderOperateLogs();
		OrderProductsNew orderProductsNew = orderProductsNewService.getOrderId(id);
		if(orderProductsNew!=null) {
			Integer netPointId = orderProductsNew.getNetPointId();
			orderOperateLogs.setNetPointId(netPointId);
			Integer status = orderProductsNew.getStatus();
			orderOperateLogs.setStatus(status);
			Integer id2 = orderProductsNew.getId();
			orderOperateLogs.setOrderProductId(id2);
		}else {
			result.setSuccess(false);
			result.setMessage("订单地址信息修改失败，请重新修改");
			return result;
		}
		Orders orders = ordersService.get(id);
		if(orders!=null) {
			Integer paymentStatus = orders.getPaymentStatus();
			orderOperateLogs.setPaymentStatus(paymentStatus);
		}else {
			result.setSuccess(false);
			result.setMessage("订单地址信息修改失败，请重新修改");
			return result;
		}
		long time = new Date().getTime();
		Integer date = (int)time;
		orderOperateLogs.setLogTime(date);
		orderOperateLogs.setSiteId(0);
		orderOperateLogs.setOperator(userName);

		orderOperateLogs.setChangeLog("订单地址由"+oldRegionName+"更改为"+regionName + ",标建成功");
		orderOperateLogs.setRemark("订单地址由"+oldRegionName+"更改为"+regionName + ",标建成功");
		orderOperateLogs.setOrderId(id);
		ordersService.insertAddressAndMarkBuildingLog(orderOperateLogs);
		shopOperationAreaService.updateRegion(orderSn, province, city, region, street, regionName);
		return result;
	}

	/*
	 * 订单详情里面的商品详情
	 *
	 */
	@Override
	public List<Map<String, String>> selectPrudevtDatail(String orderSn) {
		List<Map<String, String>> stringMap = shopOperationAreaService.selectPrudevtDatail(orderSn);
		List<Map<String, String>> stringMap2 = new ArrayList<>();
		for ( Map<String, String> m : stringMap ) {
			OrderRepairs orderRepairs = selectOrderSn(m.get("cOrderSn"));
			if (orderRepairs != null){
				m.put("handleStatus",orderRepairs.getHandleStatus().toString());
				m.put("repairsId",orderRepairs.getId().toString());
			}else {
				m.put("handleStatus","");
				m.put("repairsId","");
			}
			stringMap2.add(m);
		}
		return stringMap2;
	}

	/**
	 * 退货审核
	 * 
	 * @return
	 */
	@Override
	public Json Toexamine(String id, String status, String handleRemark, String userName, String cOrderSn) {
		Json json = new Json();
		if ("2".equals(status)) {
			OrderProductsVo productVo = shopOperationAreaService.queryOrdeProduct(cOrderSn); // 查询网单详细信息
            //小家电审核通过后，退货单状态为"进行中"
            if (!"B2C".equals(productVo.getShippingMode()) && !"CT01".equals(productVo.getsCode())){
                String signTim = shopOrderRepairsService.queryIsRejectionSign(Ustring.getString(productVo.getId()));
                if ((signTim != null && Integer.parseInt(signTim) > 0) || "WA".equalsIgnoreCase(productVo.getStockType())) {
                } else {
                    status = "9";
                    handleRemark = "审核通过 判断是拒收信息 状态则改成“等待HP拒收信息”";
                }
            }

		}
		int i = shopOrderRepairsService.updataStatus(id, status, handleRemark);
		// 添加退货操作日志
		OrderRepairLogs log = new OrderRepairLogs(); // new出来一个退货日志
		// log.setId(shopOrderRepairLogsService.getNextValId());//主键
		log.setSiteId(0);
		log.setOrderRepairId(Integer.parseInt(id));// 退货id
		log.setOperator(userName);
		if ("2".equals(status)) {
			log.setOperate("审核通过");
			log.setRemark("修改申请状态从 审核中 到 进行中，备注：" + handleRemark);
		} else if ("5".equals(status)) {
			log.setOperate("审核不通过");
			log.setRemark("修改申请状态从 审核中 到 已驳回，备注：" + handleRemark);
		} else if ("9".equals(status)) {
			log.setOperate("审核通过");
			log.setRemark("修改申请状态从 审核中 到 （判断是否为拒收信息 状态则改成“等待HP拒收信息”），备注：" + handleRemark);
		}
		i = i + shopOrderRepairLogsService.insert(log); // 记录退货操作流程 日志
		if (i > 1) {
			json.setMsg("操作成功！");
			json.setSuccess(true);
		} else {
			json.setMsg("操作失败！");
			json.setSuccess(false);
		}
		return json;
	}

	/*
	 * @author zhangbo hp拒收表格显示
	 */
	@Override
	public List<Map<String, String>> datagrid_WwwHpRecords(Map<String, Object> map) {
		return shopOperationAreaService.datagrid_WwwHpRecords(map);
	}

	@Override
	public List<Map<String, String>> datagrid_WwwHpRecords1(Map<String, Object> map) {
		return shopOperationAreaService.datagrid_WwwHpRecords1(map);
	}

	// 查询在网单表已经存在的网单号并返回存在的网单号
	@Override
	public List<Map<String, Object>> check_cOrderSn_isExist(List<Map<String, Object>> list) {
		return shopOperationAreaService.check_cOrderSn_isExist(list);
	}

	// 更新WwwHpRecords表中的匹配次数
	@Override
	public void update_WwwHpRecordsCount(List<String> list) {
		shopOperationAreaService.update_WwwHpRecordsCount(list);
	}

	// 批量更新hp拒收表中flag字段
	@Override
	public void updateFlagBatch(List<String> list) {
		shopOperationAreaService.updateFlagBatch(list);
	}

	// 查询Excel导出的数据
	@Override
	public List<Map<String, Object>> select_export_ExcelData(Map<String, Object> map) {
		return shopOperationAreaService.select_export_ExcelData(map);
	}

	/**
	 * 推送修改信息给HP
	 * 
	 * @throws ParseException
	 * @throws MalformedURLException
	 */
	@Override
	public Json ModifyPushHP(List<OrderRepairsVo> orderRepairs, String userName)
			throws ParseException, MalformedURLException {
		// TODO Auto-generated method stub
		Json json = new Json();
		// String TbSn = ""; //接收TB单号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		URL url = this.getClass().getResource(wsdlPath + "/InsertDataToHP.wsdl");
		InsertDataToHP_Service ss = new InsertDataToHP_Service(url, SERVICE_NAME);
		InsertDataToHP port = ss.getInsertDataToHPSOAP();
		// String path = "file:"+
		// this.getClass().getResource("/wsdl_test/InsertDataToHP.wsdl").getPath();
		List<String> JsonMeg = new ArrayList<>();
		List<Boolean> JsonFlag = new ArrayList<>();
		for (int i = 0; i < orderRepairs.size(); i++) {
			InterfaceLog interfaceLog = new InterfaceLog();
			interfaceLog.setSubordinateSystem("HP");// 所属系统
			// 添加接口操作系统
			interfaceLog.setInterfaceCallAddress(
					"http://10.135.1.185:7001/EAI/RoutingProxyService/EAI_SOAP_ServiceRoot?INT_CODE=EAI_INT_0145");
			InsertDataToHP_Type.Inputs input = new InsertDataToHP_Type.Inputs();
			List<InsertDataToHP_Type.Inputs> _insertDataToHP_inputs = new ArrayList<>();
			javax.xml.ws.Holder<String> _insertDataToHP_flag = new javax.xml.ws.Holder<String>();
			javax.xml.ws.Holder<String> _insertDataToHP_msg = new javax.xml.ws.Holder<String>();
			OrderRepairsVo orderRepairsVo = orderRepairs.get(i);
			String menuflag = orderRepairsVo.getMenuflag();

			// if("SD".equals(orderRepairsVo.getMenuflag())){ //手动推送HP的时候要判断 HP异常匹配表是否有这个信息
			// 如果有才能继续推送没有就提示等待
			// TbSn
			// =shopOperationAreaService.queryTBorderSn(orderRepairsVo.getcOrderSnId());
			// int tbCount =shopOperationAreaService.queryWwwHpTbSn(TbSn);
			// if(tbCount<=0){ //根据网单扩展表的TB单号查询拒收表有没有这条数据
			// json.setMsg("请等待HP拒收信息！");
			// json.setSuccess(false);
			// return json;
			// }
			// }

			// 如果发起二次鉴定
			if ("2".equals(menuflag) || "JS".equals(menuflag) || "blp".equals(menuflag) || "blp3".equals(menuflag)) { // menuflag
																														// 等于JS也就是表示是拒收调用接口
				orderRepairsVo = shopOrderRepairsService
						.queryTwoIdentification(Ustring.getString(orderRepairsVo.getId()));
                orderRepairsVo.setRequestServiceDate(0L);
			}
			OrderRepairsVo vo = shopOrderRepairsService.selectPairs(Ustring.getString(orderRepairsVo.getId()));
			// 添加业务主键,退货单号
			interfaceLog.setKeyword(vo.getRepairSn());
			// 给HP传值
			if ("2".equals(menuflag)) { // 判断是否是二次鉴定如果是的话 退货订单号后缀需要加上_2
				input.setOrderNo(Ustring.getString(vo.getRepairSn() + "_2"));// 退货订单号
				input.setOldOrder(vo.getRepairSn());
			} else if ("blp3".equals(menuflag)) {// 判断是否是三次鉴定或者是不良品 如果是的话退货单号后缀需要加上_3
				input.setOrderNo(vo.getRepairSn().replace("TH", "TC"));// 退货订单号
				input.setOldOrder(Ustring.getString(vo.getRepairSn() + "_2"));
			} else if ("blp".equals(menuflag)) {
				input.setOrderNo(vo.getRepairSn().replace("TH", "TC"));// 退货订单号
				input.setOldOrder(vo.getRepairSn());
			} else {
				input.setOrderNo(Ustring.getString(vo.getRepairSn()));// 退货订单号
				if ("3W".equals(vo.getStocktype())) {
					if (Ustring.isEmpty(vo.getTbOrderSn())) {
						json.setMsg("TB单号为空！推送失败");
						json.setSuccess(true);
						return json;
					}
					input.setOldOrder(vo.getTbOrderSn());
				} else {
					input.setOldOrder(orderRepairsVo.getcOrderSnId());
				}
				if(vo.getIsCd() != null && vo.getIsCd().intValue() == 1){
                    List<Map<String, Object>> orderProductMapList = shopOperationAreaService.selectOPCount(vo.getOrderId());
                    if (orderProductMapList != null && orderProductMapList.size() > 0){
                        String cOrderSn = orderProductMapList.get(0).get("cOrderSn").toString();
                        input.setOldOrder(cOrderSn);
                    }

                }

			}

			input.setCounts(new BigDecimal(Ustring.getString0(vo.getCount())));// 退货数量
			input.setCreatedDate(dateToXmlDate(new Date()));// 接口传输时间
			// 传PRODTYPE_ID物料编码 要先查departmentcode_sku
			// 判断这个网单是不是空调如果是空调的话则根据网单表的SKU去查inv_machine_set表的sub_sku这个字段然后把值传给HP
			String sku = stockInvMachineSetService.querySubsku(vo.getSku());
			if (Ustring.isNotEmpty(sku)) {
				input.setProdtypeId(sku);
			} else {
				input.setProdtypeId(vo.getSku());// 物料编码
			}
			input.setRequestServiceRemark(orderRepairsVo.getRequestServiceRemark());// 要求描述
			if ("2".equals(menuflag) || "JS".equals(menuflag) || "blp".equals(menuflag) || "blp3".equals(menuflag)) {
				if (orderRepairsVo.getRequestServiceDate() <= 0) {
					orderRepairsVo.setRequestServiceDate(new Date().getTime());
					orderRepairsVo
							.setRequeStservieDateTS(Ustring.timeStamp3Date(Ustring.timeStamp(), "yyyy-MM-dd HH:mm:ss"));
				} else {
					orderRepairsVo.setRequeStservieDateTS(Ustring.timeStamp2Date(
							Ustring.getString(orderRepairsVo.getRequestServiceDate()), "yyyy-MM-dd HH:mm:ss"));
				}
				input.setRequestServiceDate(dateToXmlDate(sdf.parse(orderRepairsVo.getRequeStservieDateTS())));// 要求服务时间
			} else {
				input.setRequestServiceDate(dateToXmlDate(sdf.parse(orderRepairsVo.getRequeStservieDateTS())));// 要求服务时间
			}
			// // 原始网单号 (3W传TB单号)
			// if("3W".equals(vo.getStocktype())){
			// if(Ustring.isEmpty(vo.getTbOrderSn())) {
			// json.setMsg("TB单号为空！推送失败");
			// json.setSuccess(true);
			// return json;
			// }
			// input.setOldOrder(vo.getTbOrderSn());
			// }else {
			// input.setOldOrder(orderRepairsVo.getcOrderSnId());
			// }
			input.setIfTk(orderRepairsVo.getOfflineFlag().toString());// 是否线下退款
			input.setTkReason(orderRepairsVo.getOfflineReason());// 退款原因
			input.setTkje(orderRepairsVo.getOfflineAmount());// 退款金额
			input.setTkRemark(orderRepairsVo.getOfflineRemark());// 退款备注

			OrderrepairHPrecords orderrepairHPrecords = new OrderrepairHPrecords();
			// 给HP回传数据表赋值主键
			orderrepairHPrecords
					.setId(Integer.parseInt(Ustring.getString0(orderRepairs.get(i).getRepairHPrecordsId())));
			if ("2".equals(menuflag)) {
				VomInOutStoreOrder vomInOutStoreOrder = eisVomInOutStoreOrderService.queryGetStoreCode("22", "2",
						vo.getRepairSn());// 根据退货单好查询 vom返回的C码
				if (null == vomInOutStoreOrder || null == vomInOutStoreOrder.getStoreCode()) {
					log.info("发起二次鉴定 未找到VOM返回的C码 退货单号为：" + vo.getRepairSn());
					continue;
				}
				input.setTcCode(storagesService.queryCode(vomInOutStoreOrder.getStoreCode())); // 根据c码查询库位推送到HP
				input.setIfEcjd("2");// 是否二次鉴定
				orderrepairHPrecords.setTwoAppraisal("1");

				orderRepairsVo.setHpSecondAddTime(22);
				interfaceLog.setUseTheScene("发起二次鉴定");// 使用场景
			} else if ("blp3".equals(menuflag)) {
				VomInOutStoreOrder vomInOutStoreOrder = eisVomInOutStoreOrderService.queryGetStoreCode("22", "2",
						vo.getRepairSn());// 根据退货单好查询 vom返回的C码
				if (null == vomInOutStoreOrder || null == vomInOutStoreOrder.getStoreCode()) {
					log.info("发起二次鉴定 未找到VOM返回的C码 退货单号为：" + vo.getRepairSn());
					continue;
				}
                String tcCode = storagesService.queryCode(vomInOutStoreOrder.getStoreCode());
				if (StringUtils.isBlank(tcCode)){
                    log.info("发起二次鉴定 库位编码查询为空：" + vo.getRepairSn());
				    continue;
                }
                input.setTcCode(tcCode); // 根据c码查询库位推送到HP
				input.setIfEcjd("3");// 是否三次鉴定
				orderrepairHPrecords.setThreeAppraisal("1");
				interfaceLog.setUseTheScene("发起三次鉴定");// 使用场景
			} else if ("blp".equals(menuflag)) {
				// input.setTcCode(storagesService.queryCode(orderRepairs.get(i).getStoreCode()));
				// //根据c码查询库位推送到HP
				input.setIfEcjd("2");// 是否二次鉴定

				// if(Ustring.isNotEmpty(vo.getInvoiceId())){
				// this.InvalidInvoices(vo.getInvoiceId(), "退换货");
				// ProcessLog(vo.getId(), "作废发票", "发起作废发票请求");
				// shopOrderRepairsService.updataOrderRepairsStatus("4", "",
				// Ustring.getString(vo.getId()));
				// }
				interfaceLog.setUseTheScene("发起不良品鉴定工单");// 使用场景
			} else {
				orderRepairsVo.setHpFirstAddTime(1);
				input.setIfEcjd("1");// 是否一次鉴定
				interfaceLog.setUseTheScene("发起一次鉴定");// 使用场景
			}

			// 判断库存类型是否是菜鸟库发货的
			if ("3W".equals(vo.getStocktype()) && !"blp".equals(menuflag) && !"blp3".equals(menuflag)) {
				input.setTypes("W8");// 类型 1，网单。2京东 。3 富士康
			} else if ("blp".equals(menuflag) || "blp3".equals(menuflag)) {
				input.setTypes("W2");// 类型 1，网单。2京东 。3 富士康
			} else {
				input.setTypes("");// 类型 1，网单。2京东 。3 富士康
			}

			// 用来标示新老系统
			input.setAttribute6("NSC");

			// 如果是手动推送的话就要修改HP异常表的数据
			// if("SD".equals(orderRepairsVo.getMenuflag())){
			// List<Map<String,Object>> list = new ArrayList<>();
			// Map<String,Object> map = new HashMap<>();
			// map.put("orderProductId", vo.getOrderProductId());
			// map.put("th_id", vo.getId());
			// map.put("cOrderSn", vo.getcOrderSnId());
			// map.put("hpTbSn", TbSn);
			// list.add(map);
			// shopOperationAreaService.update_WwwHpRecordsInfo(list);
			//
			//
			// }

			interfaceLog.setCreateTime(new Date());// 创建时间
			interfaceLog.setCreateBy(userName);// 创建人
			// 添加退货操作日志
			// 调用推送HP接口
			_insertDataToHP_inputs.add(input);
			interfaceLog.setSendMessage(JSONObject.toJSONString(_insertDataToHP_inputs));// 发送报文

			try {
				port.insertDataToHP(_insertDataToHP_inputs, _insertDataToHP_flag, _insertDataToHP_msg);
				// 判断HP接口是否返回成功
				if ("S".equals(_insertDataToHP_flag.value.trim())
						&& "Success".equals(_insertDataToHP_msg.value.trim())) {
					JsonMeg.add("推送成功");
					JsonFlag.add(true);

					// 接口推送成功再进行数据库操作--start
					if ("2".equals(menuflag)) {

						shopOrderrepairHPrecordsService.updataRepaiHp(orderrepairHPrecords);// 更新推送二次鉴定推送时间和状态

						shopOperationAreaService.updateStatus(vo.getOrderProductId().toString(), "110"); // 发起二次鉴定的同时要把正向单关闭
						// 记录退货操作流程 日志
						ProcessLog(vo.getId(), "系统", "发起二次鉴定", "向HP推送发起二次鉴定请求信息");
						ProcessLog(vo.getId(), "系统", "关闭", "关闭正向单");

						List<OrderProducts> orderProducts = shopOperationAreaService
								.queryOrderProductStatus(vo.getOrderProductId().toString());
						if (orderProducts.size() == 1) {
							for (OrderProducts products : orderProducts) {
								if (products.getcOrderSn().equals(vo.getcOrderSnId())) {
									shopOrdersService.updataOrdersStatus(vo.getOrderId().toString());// 关闭订单
									ProcessLog(vo.getId(), "系统", "系统联动取消订单的操作", "淘宝海尔官方旗舰店同步退换货申请,取消订单");
								}
							}
						}
					} else if ("blp3".equals(menuflag)) {

						shopOrderrepairHPrecordsService.updataRepaiHp(orderrepairHPrecords); // 更新推送三次鉴定推送时间和状态
						ProcessLog(vo.getId(), "系统", "发起三次鉴定", "向HP推送发起三次鉴定请求信息");
					} else if ("blp".equals(menuflag)) {

						ProcessLog(vo.getId(), "系统", "操作", "发起不良品鉴定工单");
						// 修改推送不良品状态
						shopOrderrepairHPrecordsService
								.updataPushRejects(Ustring.getString(orderRepairs.get(i).getRepairHPrecordsId()));

					} else {

						shopOrderRepairsService.updateStatus("10", "10", vo.getId().toString()); // 改一下退货表的
																									// （receiptStatus）发票状态
																									// （ storageStatus）
																									// 货物状态改成 带召回
						// 记录操作日志
						this.ProcessLog(vo.getId(), userName, "修改信息", "修改推送HP信息");
					}
					// 接口推送成功再进行数据库操作--end

					this.ProcessLog(vo.getId(), userName, "推送结果", "推送HP信息成功");
					// 添加推送hp信息(修改退货信息）
					shopOrderRepairsService.updatePushHp(orderRepairsVo);
				} else {
					JsonMeg.add("失败" + _insertDataToHP_msg.value.trim());
					JsonFlag.add(false);
					// 打印推送日志
					processLog(userName, menuflag, vo);

					this.ProcessLog(vo.getId(), userName, "推送结果", "推送HP信息失败" + _insertDataToHP_msg.value.trim());
				}

                interfaceLog.setReturnMessage(
                        "flag=" + _insertDataToHP_flag.value + "&msg=" + _insertDataToHP_msg.value);// 接收报文

			} catch (Exception e) {
				e.printStackTrace();
				JsonMeg.add("系统异常,请稍后重试");
				JsonFlag.add(false);
				// 打印推送日志
				processLog(userName, menuflag, vo);
				interfaceLog.setExptionInformation(String.valueOf(e));
				this.ProcessLog(vo.getId(), userName, "推送结果", "推送HP信息失败(异常)");
			}

			/*
			 * json.setMsg("推送成功！"); json.setSuccess(true);
			 */
			interfaceLogService.insert(interfaceLog);
		}
		json.setMsg(String.valueOf(JsonMeg));
		json.setSuccess(!JsonFlag.contains(false));
		return json;
	}

	private void processLog(String userName, String menuflag, OrderRepairsVo vo) {
		if ("2".equals(menuflag)) {
			ProcessLog(vo.getId(), "系统", "发起二次鉴定", "向HP推送发起二次鉴定请求信息");
		} else if ("blp3".equals(menuflag)) {
			ProcessLog(vo.getId(), "系统", "发起三次鉴定", "向HP推送发起三次鉴定请求信息");
		} else if ("blp".equals(menuflag)) {
			ProcessLog(vo.getId(), "系统", "操作", "发起不良品鉴定工单");
		} else {
			this.ProcessLog(vo.getId(), userName, "修改信息", "修改推送HP信息");
		}
	}

	/**
	 * 将Date类转换为XMLGregorianCalendar
	 * 
	 * @param date
	 * @return
	 */
	public static XMLGregorianCalendar dateToXmlDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		DatatypeFactory dtf = null;
		try {
			dtf = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
		}
		XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();
		dateType.setYear(cal.get(Calendar.YEAR));
		// 由于Calendar.MONTH取值范围为0~11,需要加1
		dateType.setMonth(cal.get(Calendar.MONTH) + 1);
		dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));
		dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));
		dateType.setMinute(cal.get(Calendar.MINUTE));
		dateType.setSecond(cal.get(Calendar.SECOND));
		return dateType;
	}

	/**
	 * 接收天猫返回过来的数据存到数据库
	 * 
	 * @param orderTmallReturnLogs
	 * @return
	 */
	public Json TMReturnData(List<OrderTmallReturnLogs> orderTmallReturnLogs) {
		Json json = new Json();
		for (int i = 0; i < orderTmallReturnLogs.size(); i++) {
			OrderTmallReturnLogs logs = orderTmallReturnLogs.get(i);
			shopOrderTmallReturnLogsService.insert(logs);
		}
		return json;
	}

	/*
	 * 检查inv_machine_set表里面的主sku
	 */
	public List<Map<String, Object>> select_sku(List<Map<String, Object>> list) {
		return stockInvMachineSetService.select_sku(list);
	}

	/*
	 * 更新HP拒收信息
	 */
	public void update_WwwHpRecordsInfo(List<Map<String, Object>> list) {
		shopOperationAreaService.update_WwwHpRecordsInfo(list);
	}

	/**
	 * 插入出入库信息
	 */
	public int insert(OrderRepairLESRecords cords) {
		// cords.setId(shopOrderRepairLogsService.getNextValId()); //生成主键
		return shopOrderRepairLesreCordsService.insert(cords);
	}

	/**
	 * 查询退货id 网单ID
	 */
	public OrderRepairsVo queryOrderProductId(String id) {
		return shopOrderRepairsService.queryOrderProductId(id);
	}

	// 调用VOM http接口
	public Json CallHttpVOM(OrderRepairLESRecords cords) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Json json = new Json();
		Map<String, Object> map = new HashMap<>();
		XMLSerializer xmlSerializer = new XMLSerializer();
		OrdersVo vo = shopOrdersService.queryVOMTransMission(Ustring.getString(cords.getOrderProductId()));
		String responseMsg = "";
		// 1.构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		PostMethod postMethod = new PostMethod(
				"http://58.56.128.84:9001/EAI/service/VOM/CommonGetWayToVOM/CommonGetWayToVOM");

		postMethod.addParameter("notifytime", formatter.format(new Date()));
		postMethod.addParameter("butype", "rrs_order");
		postMethod.addParameter("source", "HBDM3W");
		postMethod.addParameter("type", "XML");
		Gson gson = new Gson();
		String md5Str = MD5util.encrypt(xmlSerializer.write(JSONSerializer.toJSON(gson.toJson(vo))) + "Haier,123");
		postMethod.addParameter("sign", Base64Util.encode(md5Str.getBytes()).replaceAll("\r\n", ""));
		postMethod.addParameter("content", xmlSerializer.write(JSONSerializer.toJSON(JSON.toJSONString(vo))));
		try {
			// 4.执行postMethod,调用http接口
			httpClient.executeMethod(postMethod);// 200

			// 5.读取内容tt
			responseMsg = postMethod.getResponseBodyAsString().trim();
			log.info(responseMsg);

			// 6.处理返回的内容

		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 7.释放连接
			postMethod.releaseConnection();
		}
		return json;

	}

	/**
	 * 插入保存 HP返回来的鉴定结果
	 * 
	 * @param
	 * @return
	 * @throws JSONException
	 * @throws DocumentException
	 */
	public String insertHPrecords(String obejecJson)
            throws JSONException, DocumentException, UnsupportedEncodingException {
	    log.info("insertHPrecords start!");
		Json json = new Json();
		List<OrderRepairshpLogs> items = new ArrayList<>();
		if (obejecJson.equals("")) {
			json.setMsg("失败:没有数据!!!");
			json.setSuccess(false);
			return json2xml(json);
		}

		try {
//            log.info("insertHPrecords recevie json " + obejecJson);
            net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(XmlUtils.xml2Json(obejecJson).get("item"));
            // net.sf.json.JSONArray jsonArray
            // =net.sf.json.JSONArray.fromObject(JSONObject.parseObject(JSONObject.parseObject(JSONObject.parseObject((XmlUtils.xml2Json(obejecJson).get("InsertCBS_InputVariable").toString())).get("part").toString()).get("items").toString()).get("item"));
            List<OrderRepairshpLogs> list = (List<OrderRepairshpLogs>) net.sf.json.JSONArray.toCollection(jsonArray,
                    OrderRepairshpLogs.class);
            log.info("insertHPrecords list size " + list.size());
            int i = 0;
            for (OrderRepairshpLogs bean : list) {
                String orderRejectSn = bean.getOrderRejectSn();
                if (orderRejectSn.substring(0, 2).equals("DB")) {
                    hpIdentificationResultService.insert(bean);
                    continue;
                }
                OrderrepairHPrecords orderrepairHPrecords = new OrderrepairHPrecords();
                i = +shopOrderRepairshpLogsService.insert(bean);// 插入HP返回的原始数据
                // 判断是否二次质检回传 如果是的话 则去掉最后两位字符串_2
                if ("2".equals(bean.getAdd1())) {
                    bean.setOrderRejectSn(bean.getOrderRejectSn().substring(0, bean.getOrderRejectSn().length() - 2));
                } else if ("3".equals(bean.getAdd1()) && "3".equals(bean.getInspectTime())) { // 判断是否返回二次鉴定结果
                    bean.setOrderRejectSn(bean.getOrderRejectSn().substring(0, bean.getOrderRejectSn().length() - 4));
                } else if ("3".equals(bean.getAdd1()) && "1".equals(bean.getAdd2())
                        || "3".equals(bean.getAdd1()) && "2".equals(bean.getAdd2())) {
                    bean.setOrderRejectSn(bean.getOrderRejectSn().substring(0, bean.getOrderRejectSn().length() - 2));
                }
                // bean.setId(shopOrderRepairLogsService.getNextValId());
                OrderRepairsVo vo = shopOrderRepairsService.selectOrederProductId(bean.getOrderRejectSn());// 查询网单id和退货单号
                if (null == vo) {
                    json.setMsg("失败 未找到退货单号");
                    json.setSuccess(false);
                    return json2xml(json);
                }
                if ("1".equals(bean.getAdd1())) {
                    int countRepairs = shopOrderrepairHPrecordsService.queryOrderHAdd1(Ustring.getString(vo.getId()));
                    if (countRepairs > 0) {
//                        json.setMsg("失败 此条一次鉴定结果 已存在！单号：" + bean.getOrderRejectSn());
//                        json.setSuccess(false);
//                        return json2xml(json);
						log.info("hp鉴定结果 已存在！单号：" + bean.getOrderRejectSn());
						continue;
                    }
                }
                // orderrepairHPrecords.setId(shopOrderRepairLogsService.getNextValId());//生成解析之后数据的主键
                orderrepairHPrecords.setOrderProductId(Integer.parseInt(vo.getcOrderSnId()));// 网单id
                orderrepairHPrecords.setOrderRepairId(vo.getId());// 退货id
                orderrepairHPrecords.setNetPointCode(bean.getNetPointCode());// 网点
                orderrepairHPrecords.setCheckResult(new Byte(Ustring.getString0(bean.getCheckResult())));/// 鉴定结果 1:符合退机条件
                /// 2:不符合退机条件
                orderrepairHPrecords.setQuality(new Byte(Ustring.getString0(bean.getQuality())));/// 产品状态 1:
                /// 未开箱。2:已开箱正品。3:不良品
                /// 4：已使用正品 5:不良品换机
                /// 6:不良品退机（现电商只保有1256四个状态）
                orderrepairHPrecords.setMachineNum(bean.getMachineNum());// 机编代码

                orderrepairHPrecords.setInspector(bean.getInspector());// 质检员
                orderrepairHPrecords.setInspectTime(bean.getInspectTime());// 质检时间
                orderrepairHPrecords.setSuccess(new Byte((byte) 1));// 是否成功
                orderrepairHPrecords.setCheckType(new Byte(bean.getAdd1()));// 质检类型，一次/二次
                orderrepairHPrecords.setPackResult(new Byte(Ustring.getString0(bean.getAdd2())));// 包装结果
                orderrepairHPrecords.setResponse(new Byte(Ustring.getString0(bean.getAdd3())));// 非正品买单方
                orderrepairHPrecords.setHpOrderFail(new Byte(Ustring.getString0(bean.getAdd4())));// 生成工单是否失败，无值代表成功
                orderrepairHPrecords.setHpOrderRemark(bean.getAdd5());// 备注
                orderrepairHPrecords.setRepairsHPLogsId(bean.getId());// OrderRepairsHPLogs原始数据关联ID
                orderrepairHPrecords.setSource("");
                orderrepairHPrecords.setWoId(bean.getWoId());// 工单号

                shopOrderrepairHPrecordsService.insert(orderrepairHPrecords);// 插入HP返回结果解析之后的数据
                // 更改发票和货物状态改成已召回
                if (!"2".equals(Ustring.getString0(bean.getCheckResult()))) {
                	shopOrderRepairsService.updataOrderRepairsStatus("3", "3", Ustring.getString(vo.getId()));
                }
                // 记录操作日志
                OrderRepairLogs log = new OrderRepairLogs(); // new出来一个退货日志
                // log.setId(shopOrderRepairLogsService.getNextValId());//主键
                log.setOrderRepairId(vo.getId());// 退货id
                log.setSiteId(0);
                log.setOperator("系统");

                log.setOperate("HP回传:" + Ustring.getString(OrderRepairsConst.HPORDERMAP
                        .get(Integer.parseInt(Ustring.getString0(orderrepairHPrecords.getHpOrderFail())))));
                if ("1".equals(bean.getAdd1())) {
                    log.setRemark("HP返回一次鉴定结果");
                } else if ("2".equals(bean.getAdd1()) || "3".equals(bean.getAdd1())) {
                    log.setRemark("HP返回二次鉴定结果");
                    if ("3".equals(orderrepairHPrecords.getCheckType().toString())) {
                        log.setRemark("HP返回换箱信息:" + Ustring.getString(
                                OrderRepairsConst.PACKRESULT_3MAP.get(orderrepairHPrecords.getPackResult().intValue())));
                    } else if ("2".equals(orderrepairHPrecords.getCheckType().toString())) {
                        log.setRemark("HP返回二次鉴定结果:" + Ustring.getString(
                                OrderRepairsConst.PACKRESULTMAP_2MAP.get(orderrepairHPrecords.getPackResult().intValue())));
                    }
                }

                // else
                // if("3".equals(bean.getAdd1())&&bean.getAdd2()!=null&&bean.getAdd2()!=""){
                // log.setRemark("HP返回鉴定结果"+Ustring.getString(OrderRepairsConst.PACKRESULT_3MAP.get(Integer.parseInt(bean.getAdd2()))));
                // }

                if ("5".equals(bean.getQuality())) {
                    log.setRemark("HP返回鉴定结果：不良品换机");
                } else if ("6".equals(bean.getQuality())) {
                    log.setRemark("HP返回鉴定结果：不良品退机");
                } else if ("2".equals(bean.getQuality())) {
                    log.setRemark("HP返回鉴定结果：已开箱正品");
                }

                if ("2".equals(Ustring.getString0(bean.getCheckResult()))) {
                    log.setRemark("HP返回鉴定结果：不符合");
                    shopOrderRepairsService.RepairsTermination(Ustring.getString(vo.getId()), "5", "HP返回鉴定结果：不符合");
                }
                shopOrderRepairLogsService.insert(log); // 记录退货操作流程 日志

            }
            json.setMsg("成功");
            json.setSuccess(true);
            return json2xml(json);
        }catch (Exception e){
		    e.printStackTrace();
            log.error("insertHPrecords error", e);
            json.setMsg("失败:出现异常!!!");
            json.setSuccess(false);
            return json2xml(json);
        }
	}

	/**
	 * json to xml
	 * 
	 * @param json
	 * @return
	 */
	public static String json2xml(Json json) {
		org.json.JSONObject jsonObj = new org.json.JSONObject(json);
		return "<xml>" + XML.toString(jsonObj) + "</xml>";
	}

	public Map<String, Object> select_ThInfo(String cOrderSn) {
		return shopOperationAreaService.select_ThInfo(cOrderSn);
	}

	// 根据网单号查询订单号
	public OrderRepairs selectOrderSn(String cOrderSn) {
		if(null!=shopOperationAreaService.Checkstate(cOrderSn)){
			return shopOperationAreaService.Checkstate(cOrderSn);
		}else{
			return shopOperationAreaService.selectOrderSn(cOrderSn);
		}
	}

	/*
	 * 退换货列表显示
	 */
	public List<Map<String, String>> datagrid_orderForecastGoal(Map<String, Object> map) {
		return shopOperationAreaService.datagrid_orderForecastGoal(map);
	}

	/*
	 * 退货总记录数
	 */
	public List<Map<String, String>> datagrid_orderForecastGoalcount(Map<String, Object> map) {
		return shopOperationAreaService.datagrid_orderForecastGoalcount(map);
	}

	/*
	 * 退换货报表导出
	 */
	public List<Map<String, Object>> export_ExcelOrderRepairs(Map<String, Object> map) {
		return shopOperationAreaService.export_ExcelOrderRepairs(map);
	}

	/**
	 * 处理vom回传的信息
	 * 
	 * @param vomReturnInforMation
	 * @return
	 */
	// @Transactional(rollbackFor = Exception.class)
	// public String insertAnalyLogs(OrederVOMReturnLogs vomReturnLogs){
	// Json jsons = new Json();
	// int index = 0;
	// String id="";
	// // TODO Auto-generated method stub
	// net.sf.json.JSON json=getJSONFromXml(vomReturnLogs.getContent());// 把数据转成JSON
	// OrderVOMReturnAnalysis analysis =JSON.parseObject(JSON.toJSONString(json),
	// OrderVOMReturnAnalysis.class);
	// net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(json);
	// net.sf.json.JSONArray jsonArray =
	// net.sf.json.JSONArray.fromObject(jsonObject.getString("InOutItems"));
	// net.sf.json.JSONObject jsonObjectss = null;
	// List<OrderVOMReturnAnalysisDetailed> list2 = new ArrayList<>();
	// vomReturnLogs.setId(shopOrderRepairLogsService.getNextValId());
	// index += shopOrederVOMReturnLogsService.insert(vomReturnLogs);//插入VOM返回原始数据
	// analysis.setId(shopOrderRepairLogsService.getNextValId());//生成 主表单主键
	// index += shopOrderVOMReturnAnalysisService.insert(analysis);//插入VOM回传信息
	// 解析之后的数据 （主表单）
	// for (int i = 0; i < jsonArray.size(); i++) {
	// jsonObjectss = jsonArray.getJSONObject(i);
	// OrderVOMReturnAnalysisDetailed detailed =
	// JSON.parseObject(JSON.toJSONString(jsonObjectss),
	// OrderVOMReturnAnalysisDetailed.class);
	// detailed.setId(shopOrderRepairLogsService.getNextValId());//生成子表单的主键
	// detailed.setAnalysisId(analysis.getId());//关联主表单id
	// index += shopOrderVOMReturnAnalysisDetailedService.insert(detailed);
	// //插入vom返回的子表单数据（解析之后的数据）
	//
	// }
	// if((2+jsonArray.size())==index){
	// jsons.setMsg("成功");
	// jsons.setSuccess(true);
	// id = analysis.getOrderNo();
	// }else {
	// jsons.setMsg("失败！");
	// jsons.setSuccess(false);
	// }
	// return id;
	// }
	/**
	 * 把XML数据转换成JSON
	 * 
	 * @param xmlString
	 * @return
	 */
	public static net.sf.json.JSON getJSONFromXml(String xmlString) {
		XMLSerializer xmlSerializer = new XMLSerializer();
		net.sf.json.JSON json = xmlSerializer.read(xmlString);
		return json;
	}

	/**
	 * 接收不良品返回过来的信息 http接口
	 * 
	 * @return
	 * @throws ParseException
	 */
	public String HPReturnUnhealthyImpl(String obejecXml) throws ParseException {
		OrderhpRejectionLogs logs = new OrderhpRejectionLogs();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		net.sf.json.JSON json = getJSONFromXml(obejecXml);// 把数据转成JSON
		net.sf.json.JSONObject object = net.sf.json.JSONObject.fromObject(json);
		net.sf.json.JSONObject jsonObjects = net.sf.json.JSONObject.fromObject(object.getString("item"));
		// logs.setId(shopOrderRepairLogsService.getNextValId());
		logs.setSoNum(jsonObjects.getString("so_num"));
		logs.setVbelnso(jsonObjects.getString("vbelnso"));
		logs.setProductTypeId(jsonObjects.getString("product_type_id"));
		logs.setProductNo(jsonObjects.getString("product_no"));
		logs.setHpLesId(jsonObjects.getString("hp_les_id"));
		logs.setCountNum(Integer.parseInt(jsonObjects.getString("count_num")));
		logs.setKw(Ustring.getString(jsonObjects.getString("kw")));
		logs.setInwhId(Ustring.getString(jsonObjects.getString("inwh_id")));
		logs.setInwhDate(Ustring.getString(jsonObjects.getString("inwh_date")));
		logs.setKeyProductNo(Ustring.getString(jsonObjects.getString("key_product_no")));
		logs.setMainSku(Ustring.getString(jsonObjects.getString("main_sku")));
		logs.setSubCount(Integer.parseInt(Ustring.getString0(jsonObjects.getString("sub_count"))));
		logs.setChannelOrderSn(jsonObjects.getString("channel_order_sn"));
		logs.setChannel(jsonObjects.getString("channel"));
		logs.setSecCode(Ustring.getString(jsonObjects.getString("sec_code")));
		logs.setDepCharge(Ustring.getString(jsonObjects.getString("dep_charge")));
		logs.setHpLesDate(formatter.parse(jsonObjects.getString("hp_les_date")));
		logs.setRespDivision(Ustring.getString(jsonObjects.getString("resp_division")));
		// 根据退货网单号 查询是否已经回传过 如果是的话就更改数据库信息 不是则插入
		String id = shopOrderhpRejectionLogsService.quereHPRejection(
				jsonObjects.getString("channel_order_sn").replace("TC", "TH"), jsonObjects.getString("hp_les_id"));// 退货订单号);
		// 根据退货单号 查询数据用来关闭正向单 作废发票
		OrderRepairsVo repairsVo = shopOrderRepairsService
				.queryRepairsInvoiceId(jsonObjects.getString("channel_order_sn").replace("TC", "TH"));
		if (repairsVo == null) {
			return "<?xml version='1.0' encoding='UTF-8'?><response><flag>F</flag><message>未找到"
					+ jsonObjects.getString("channel_order_sn") + "此单</message></response>";
		}
		if (Ustring.isEmpty(id)) {
			shopOrderhpRejectionLogsService.insertSelective(logs);
			// //更改发票和货物状态改成已召回
			// shopOrderRepairsService.updataOrderRepairsStatus("3", "3",
			// Ustring.getString(repairsVo.getId()));
			// 同时作废发票
			int countRepairs = shopOrderrepairHPrecordsService.queryjudgeRejects(Ustring.getString(repairsVo.getId()));// 判断是否是不良品
			//根据网单id查找最大的发票id(这个发票才是有效的)
			OrderrepairHPrecordsVO invoice = shopOrderrepairHPrecordsService.findInvoice(repairsVo.getOrderProductId());
			if (invoice != null) {
				if (invoice.getInvoiceSuccess() == 1 && invoice.getStatusType() == 1&& countRepairs > 0) {//判断是否开发票
				// 如果是不良品的话则判断是否有发票号 如果有的话则作废发票
				this.InvalidInvoices(String.valueOf(invoice.getInvoIceId()), "退换货");
				ProcessLog(repairsVo.getId(), "系统", "操作", "作废发票");

				shopOperationAreaService.updateStatus(repairsVo.getOrderProductId().toString(), "110"); // 发起不良品鉴定工单的同时要把正向单关闭
				ProcessLog(repairsVo.getId(), "系统", "操作", "关闭正向单");
				List<OrderProducts> orderProducts = shopOperationAreaService
						.queryOrderProductStatus(repairsVo.getOrderProductId().toString());
				if (orderProducts.size() == 1) {
					for (OrderProducts products : orderProducts) {
						if (products.getcOrderSn().equals(repairsVo.getcOrderSnId())) {
							shopOrdersService.updataOrdersStatus(repairsVo.getOrderId().toString());// 关闭订单
							ProcessLog(repairsVo.getId(), "系统", "系统联动取消订单的操作", "淘宝海尔官方旗舰店同步退换货申请,取消订单");
						}
					}
				}
			}
			}
			ProcessLog(repairsVo.getId(), "系统", "操作", "HP返回不良品结果");
		} else {
			shopOrderhpRejectionLogsService.updateHpRejectionLogs(logs);
			// 同时作废发票
			int countRepairs = shopOrderrepairHPrecordsService.queryjudgeRejects(Ustring.getString(repairsVo.getId()));// 判断是否是不良品
			OrderrepairHPrecordsVO invoice = shopOrderrepairHPrecordsService.findInvoice(repairsVo.getOrderProductId());
			if (invoice != null) {
				if (invoice.getInvoiceSuccess() == 1 && invoice.getStatusType() == 1&& countRepairs > 0) {//判断是否开发票
					this.InvalidInvoices(String.valueOf(invoice.getInvoIceId()), "退换货");
				ProcessLog(repairsVo.getId(), "系统", "操作", "作废发票");
				// 如果是不良品的话则判断是否有发票号 如果有的话则作废发票
				shopOperationAreaService.updateStatus(repairsVo.getOrderProductId().toString(), "110"); // 发起不良品鉴定工单的同时要把正向单关闭
				ProcessLog(repairsVo.getId(), "系统", "操作", "关闭正向单");
				// 判断是否很只有一个网单 如果是的话 则关闭订单
				List<OrderProducts> orderProducts = shopOperationAreaService
						.queryOrderProductStatus(repairsVo.getOrderProductId().toString());
				if (orderProducts.size() == 1) {
					for (OrderProducts products : orderProducts) {
						if (products.getcOrderSn().equals(repairsVo.getcOrderSnId())) {
							shopOrdersService.updataOrdersStatus(repairsVo.getOrderId().toString());// 关闭订单
							ProcessLog(repairsVo.getId(), "系统", "系统联动取消订单的操作", "淘宝海尔官方旗舰店同步退换货申请,取消订单");
						}
					}
				}
			}
		}
		}

		return "<?xml version='1.0' encoding='UTF-8'?><response><flag>S</flag><message></message></response>";
	}

	// 查询hp拒收日志表里面的rowid ,判断主键是否重复
	public List<String> selectHPlogsRowid(String rowid) {
		return shopOperationAreaService.selectHPlogsRowid(rowid);
	}

	public List<Map<String, Object>> selectAllHPlogs(){
	    return shopOperationAreaService.selectAllHPlogs();
    }

	// hp推送信息插入到hp拒收日志表
	public void insertHPlogs(Map<String, Object> map) {
		shopOperationAreaService.insertHPlogs(map);
	}

	public void insertWwwHpRecords(Map<String, Object> map) {
		shopOperationAreaService.insertWwwHpRecords(map);
	}

	/**
	 * 作废电子发票
	 **/
	public Integer InvalidInvoices(String invalidData, String invalidReason) throws BusinessException {
		String id = String.valueOf(invalidData);
		Invoices invoices = shopInvoiceService.getById(Integer.parseInt(id));
		if (invoices == null) {// 发票不存在
			return 0;
		}
		if (invoices.getState() != InvoiceConst.COMPLETE_STATE) {// 已开票的发票才可以作废
			return 0;
		}
		if (invoices.getStatusType() == InvoiceConst.INVALID_KIND && invoices.getSuccess() == InvoiceConst.SUCCESS) {// 已经作废成功
			return 0;
		}
		invoices.setStatusType(InvoiceConst.INVALID_KIND);// 推送作废
		invoices.setSuccess(0);// 待推送
		invoices.setTryNum(0);
		invoices.setInvalidReason(invalidReason);
		Integer t = 0;
		try {
			shopInvoiceService.updateInvoiceOperate(invoices);
			t = 1;
		} catch (Exception e) {
			logger.error("更新发票发生异常，发票编号：" + invoices.getCOrderSn(), e);
		}
		return t;
	}

	/**
	 * 关闭退货单更改退货单状态
	 * 
	 * @param id
	 * @param handleRemark
	 * @return
	 */
	public Json RepairsTermination(String id, String handleRemark, String userName) {
		Json json = new Json();
		int index = 0;
		// 判断此逆向单是否是非正品需买单
		// if(shopOrderRepairsService.queryRepairsStats(id)>0) {
		index = shopOrderRepairsService.RepairsTermination(id, "6", handleRemark);
		OrderRepairLogs log = new OrderRepairLogs(); // new出来一个退货日志
		// log.setId(shopOrderRepairLogsService.getNextValId());//主键
		log.setSiteId(0);
		log.setOrderRepairId(Integer.parseInt(id));// 退货id
		log.setOperator(userName);
		log.setOperate("关闭逆向单");
		log.setRemark(handleRemark);
		index += shopOrderRepairLogsService.insert(log); // 记录退货操作流程 日志
		if (index > 1) {
			json.setSuccess(true);
			json.setMsg("终止成功！");
		} else {
			json.setSuccess(false);
			json.setMsg("终止失败！");
		}
		// }else {
		// json.setSuccess(true);
		// json.setMsg("此退货单不是“非正品需买单”不能终止！");
		// }
		return json;
	}
	//关闭逆向单
	public Json Rminatereverse(String id, String handleRemark,String terminationReason, String userName,String handleStatus,String pd) {
		Json json = new Json();
		int index = 0;
		// 判断此逆向单是否是非正品需买单
		// if(shopOrderRepairsService.queryRepairsStats(id)>0) {
		OrderRepairLogs log = new OrderRepairLogs(); // new出来一个退货日志
		if(handleStatus!=null && !handleStatus.equals("") ){
			index = shopOrderRepairsService.RepairsRminatereverse(id, handleStatus, handleRemark,terminationReason);
			log.setOperate("申请关闭逆向单");
			log.setRemark(terminationReason);
		}else{

			log.setOperate("关闭逆向单");
			if(("1").equals(pd)){
                index = shopOrderRepairsService.updateHandleStatus(id, "6");
				log.setRemark("确认关闭");
			}else{
                index = shopOrderRepairsService.RepairsRminatereverse(id, "6", handleRemark,terminationReason);
				log.setRemark(terminationReason);
			}
		}

		// log.setId(shopOrderRepairLogsService.getNextValId());//主键
		log.setSiteId(0);
		log.setOrderRepairId(Integer.parseInt(id));// 退货id
		log.setOperator(userName);


		index += shopOrderRepairLogsService.insert(log); // 记录退货操作流程 日志
		if (index > 1) {
			json.setSuccess(true);
			json.setMsg("终止成功！");
		} else {
			json.setSuccess(false);
			json.setMsg("终止失败！");
		}
		// }else {
		// json.setSuccess(true);
		// json.setMsg("此退货单不是“非正品需买单”不能终止！");
		// }
		return json;
	}
	/**
	 * 22转41库 实时推送给VOM
	 * 
	 * @return
	 */
	public Json StockTransfer(String id, String handleRemark, String userName) {
		Json json = new Json();
		if (shopOrderRepairsService.queryRepairsStats(id) <= 0) {
			json.setSuccess(true);
			json.setMsg("此退货单不是“非正品需买单”不能终止！");
			return json;
		}
        List<OrderRepairLESRecords> repairLESRecordList = new ArrayList<>();
        OrderRepairsVo orderRepairsVo = shopOrderRepairsService.queryReturnEdit(id);
        OrderRepairLESRecords cords = new OrderRepairLESRecords();
        cords.setOrderProductId(orderRepairsVo.getOrderProductId());// 网单id
        cords.setOrderRepairId(orderRepairsVo.getId());// 退货单主键
        cords.setRecordSn(orderRepairsVo.getcOrderSnId() + "CX1");// 出库单号
        cords.setOperate(1);// 操作，1=出库；2=入库
        cords.setStorageType(22);// 批次，22；21；10
        List<OrderRepairLESRecords> orderRepairLESRecordLess = shopOrderRepairLesreCordsService.queryRecordSn("1", "22", id);
        int lesorderid = 0;
        if (orderRepairLESRecordLess != null && orderRepairLESRecordLess.size() > 0){
            lesorderid = orderRepairLESRecordLess.get(0).getId();
        }else{
            lesorderid = insert(cords);// 插入出库单
            ProcessLog(Integer.parseInt(id), userName, "生成出库单", "生成22出库单 单号：" + cords.getRecordSn());
        }
        cords.setId(lesorderid);
        repairLESRecordList.add(cords);

        OrderRepairLESRecords cordscx2 = new OrderRepairLESRecords();
        cordscx2.setOrderProductId(orderRepairsVo.getOrderProductId());// 网单id
        cordscx2.setOrderRepairId(orderRepairsVo.getId());// 退货单主键
        cordscx2.setRecordSn(orderRepairsVo.getcOrderSnId() + "CX2");// 入库单号
        cordscx2.setOperate(2);// 操作，1=出库；2=入库
        cordscx2.setStorageType(41);// 批次，22；21；10
        orderRepairLESRecordLess = shopOrderRepairLesreCordsService.queryRecordSn("2", "41", id);
        if (orderRepairLESRecordLess != null && orderRepairLESRecordLess.size() > 0){
            lesorderid = orderRepairLESRecordLess.get(0).getId();
        }else{
            lesorderid = insert(cordscx2);// 插入出库单
            ProcessLog(Integer.parseInt(id), userName, "生成入库单", "生成41入库单 单号：" + cordscx2.getRecordSn());
        }
        cordscx2.setId(lesorderid);
        repairLESRecordList.add(cordscx2);

		for (int i = 0; i < repairLESRecordList.size(); i++) {
            OrderRepairLESRecords orderRepairLESRecords = repairLESRecordList.get(i);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = new Date();

            List<OrderRepairLESRecords> orderRepairLESRecordsListByRepairId =
                    shopOrderRepairLesreCordsService.queryLesreCodrdsId(id);
            String storeCode = null;
            Map hpRecordIdMap = shopOrdersService.queryMinHpRecordId(id);

            if (hpRecordIdMap == null || hpRecordIdMap.get("id") == null){
                log.info(" 22转41退货单不存在 " + id);
                json.setSuccess(true);
                json.setMsg("退货单不存在!");
                return json;
            }


			OrdersVo vo = shopOrdersService
					.queryVOMTransMission(hpRecordIdMap.get("id").toString());

            OrderRepairLESRecords orderRepairLESRecordsMinId = orderRepairLESRecordsListByRepairId.get(0);
            VomInOutStoreOrder vomInOutStoreOrder = eisVomInOutStoreOrderService.queryGetStoreCode(
                    orderRepairLESRecordsMinId.getStorageType().toString(),
                    orderRepairLESRecordsMinId.getOperate().toString(),
                    orderRepairLESRecordsMinId.getRecordSn());

            if (vomInOutStoreOrder != null && vomInOutStoreOrder.getStoreCode() != null){
                storeCode = vomInOutStoreOrder.getStoreCode();

            }

            if (StringUtil.isEmpty(storeCode)){
                log.info("22转41库位不存在 " + id);
                json.setSuccess(true);
                json.setMsg("库位查找失败！");
                return json;
            }

            VOMSynOrderRequire synOrderRequire = new VOMSynOrderRequire();
            synOrderRequire.setOrderNo(orderRepairLESRecords.getRecordSn());
            synOrderRequire.setSourceSn(orderRepairLESRecords.getRecordSn());
            synOrderRequire.setOrderDate(dateFormat.format(date1));

            //根据网点86码查询库位
//			 String StoreCode=storagesService.queryCenterCode(vo.getNetPointCode());

            synOrderRequire.setNetPointCode(vo.getNetPointCode());
            synOrderRequire.setStoreCode(storeCode);
            synOrderRequire.setProvince(regionsService.selectregionName(vo.getProvince()));//收货人所在省
            synOrderRequire.setCity(regionsService.selectregionName(vo.getCity()));//收货人所在市
            synOrderRequire.setCounty(regionsService.selectregionName(vo.getRegion()));//收货人所在县/区
            synOrderRequire.setAddr(vo.getAddress());//详细地址
            String Region=regionsService.selectCode(Ustring.getString(vo.getRegion()));
//			if(Ustring.isEmpty(Region)){
//				log.info("收货人所在县/区 不存在");
//				continue;
//			}
            synOrderRequire.setGbCode(Region); //国标
            synOrderRequire.setName(vo.getConsignee());
            if (StringUtil.isEmpty(vo.getMobile())){
                synOrderRequire.setMobile(vo.getPhone());
                synOrderRequire.setTel(vo.getPhone());
            }else{
                synOrderRequire.setMobile(vo.getMobile());
                synOrderRequire.setTel(vo.getPhone());
            }

            synOrderRequire.setPostCode(vo.getZipcode());
            synOrderRequire.setPayState(vo.getPaymentStatus().toString().equals("1")?"P1":"P2");
            synOrderRequire.setPayTime(vo.getPayTimeStr());
            synOrderRequire.setPayType(vo.getPaymentCode());
            synOrderRequire.setInvRise(vo.getInvoiceTitle());
            synOrderRequire.setIsInv(vo.getIsReceipt());
            synOrderRequire.setInvType(InvoiceConst.INVOICE_TYPE.get(vo.getType()));
            synOrderRequire.setTaxBearer(vo.getTaxPayerNumber());
            synOrderRequire.setRecAddr(vo.getRegisterAddressAndPhone());
            synOrderRequire.setRemark(orderRepairLESRecords.getRepairSn());

            StringBuffer sb = new StringBuffer(orderRepairLESRecords.getRecordSn());
            sb.deleteCharAt(sb.length()-1);
            String Reorder = orderRepairLESRecords.getRecordSn().substring(orderRepairLESRecords.getRecordSn().length()-3, orderRepairLESRecords.getRecordSn().length());
            String lesId =null;

            if("CX1".equals(Reorder) || "CX3".equals(Reorder) || "CX5".equals(Reorder)){
                if (getFiveYard(orderRepairLESRecords, storeCode, synOrderRequire)){
                    json.setSuccess(true);
                    json.setMsg("CX1库位查找失败！");
                    return json;
                }
                synOrderRequire.setNetPointCode(null);

                Reorder=sb+"2";
                synOrderRequire.setOrderType("13");
                synOrderRequire.setBusType("1");//业务类型

            }else if("CX2".equals(Reorder) || "CX4".equals(Reorder) || "CX6".equals(Reorder)){
                Reorder=sb+"1";
                synOrderRequire.setOrderType("14");
                synOrderRequire.setBusType("1");//业务类型

                if (getFiveYard(orderRepairLESRecords, storeCode, synOrderRequire)){
                    json.setSuccess(true);
                    json.setMsg("CX2库位查找失败！");
                    return json;
                }
                synOrderRequire.setNetPointCode(null);

            }
            synOrderRequire.setReorder(Reorder);
            synOrderRequire.setRecBank(vo.getBankNameAndAccount());
            synOrderRequire.setFreight(vo.getShippingAmount().doubleValue());
            synOrderRequire.setBillSum(vo.getOrderAmount().doubleValue());
            VOMSynSubOrderRequire synSubOrderRequire = null;
            //判断是不是套机
            List<InvMachineSet> machineSets=stockInvMachineSetService.getByMainSku(orderRepairLESRecords.getSku());
            List<VOMSynSubOrderRequire> subOrderList = new ArrayList<VOMSynSubOrderRequire>();
            if(machineSets.size()>0){//如果是套机
                for(int j=0;j<machineSets.size();j++){
                    synSubOrderRequire = new VOMSynSubOrderRequire();
                    synSubOrderRequire.setProductCode(machineSets.get(j).getSubSku());//子sku
                    synSubOrderRequire.setItemNo(Ustring.getString(j+1));//行号
                    synSubOrderRequire.setStorageType(Ustring.getString(orderRepairLESRecords.getStorageType()));
                    synSubOrderRequire.setHrCode(machineSets.get(j).getSubSku());
                    synSubOrderRequire.setProdes(machineSets.get(j).getMaktx2());
                    synSubOrderRequire.setNumber(orderRepairLESRecords.getNumber());
                    synSubOrderRequire.setUnprice(orderRepairLESRecords.getPrice());
                    synSubOrderRequire.setReitem(Ustring.getString(machineSets.size()));
                    subOrderList.add(synSubOrderRequire);
                }
            }else {
                synSubOrderRequire = new VOMSynSubOrderRequire();
                //套机列表查询
                String sku= helpUtils.getProductCode(orderRepairLESRecords.getId(), orderRepairLESRecords.getSku(), orderRepairLESRecords.getPushFailNumber());
                synSubOrderRequire.setProductCode(sku);
                synSubOrderRequire.setItemNo("1");
                synSubOrderRequire.setStorageType(Ustring.getString(orderRepairLESRecords.getStorageType()));
                synSubOrderRequire.setHrCode(sku);
                synSubOrderRequire.setProdes(orderRepairLESRecords.getProductName());
                synSubOrderRequire.setNumber(orderRepairLESRecords.getNumber());
                synSubOrderRequire.setUnprice(orderRepairLESRecords.getPrice());
                synSubOrderRequire.setReitem(Ustring.getString(2));
                subOrderList.add(synSubOrderRequire);
            }
            synOrderRequire.setSubOrderList(subOrderList);

            VOMOrderResponse vomOrderResponse = vomOrderModel.synOrderInfo(synOrderRequire);
            if("成功".equals(vomOrderResponse.getMsg())){
                OrderRepairLESRecords records = new OrderRepairLESRecords();
                records.setId(orderRepairLESRecords.getId());
                records.setFalg("1"); // 修改推送状态
                shopOrderRepairLesreCordsService.updataRecords(records);
                ProcessLog(Integer.parseInt(id), userName, "操作", "推送vom成功 单号：" + orderRepairLESRecords.getRecordSn());
            }else{
                json.setSuccess(true);
                json.setMsg("推送vom失败！");
                return json;
            }

		}
		json.setSuccess(true);
		json.setMsg("操作成功！");

		return json;
	}

	public String getProductCode(int id, String sku, Integer pushFailNumber) {
		// 套机列表查询
		List<InvMachineSet> imsList = null;
		InvMachineSet machineSet = new InvMachineSet();
		machineSet.setMainSku(sku);
		ServiceResult<List<InvMachineSet>> stockcommresult = getSubMachinesByMainSku(machineSet);
		if (stockcommresult != null && stockcommresult.getSuccess()) {
			imsList = stockcommresult.getResult();
			if (imsList == null || imsList.size() == 0) {// 判断如果不是套机就做一个常规处理
				imsList = new ArrayList<InvMachineSet>();
				InvMachineSet ims = new InvMachineSet();
				ims.setPosnr("10");
				ims.setSubSku(convertToExternalSku(sku));// 转换为R码
				if (ims.getSubSku() == null || ims.getSubSku().equals("")) {
					OrderRepairLESRecords records = new OrderRepairLESRecords();
					records.setId(id);
					records.setFalg("2"); // 修改推送状态
					records.setFailReason("套机产品编码不能为空");
					records.setPushFailNumber(pushFailNumber + 1); // 修改失败推送次数
					shopOrderRepairLesreCordsService.updataRecords(records);
				}
				ims.setMenge(new BigDecimal(1));
				imsList.add(ims);

				return ims.getSubSku();
			}
		}
		return sku;
	}

	public ServiceResult<List<InvMachineSet>> getSubMachinesByMainSku(InvMachineSet machineSet) {
		ServiceResult<List<InvMachineSet>> result = new ServiceResult<List<InvMachineSet>>();
		try {
			result.setResult(invMachineSetDao.getByMainSku(machineSet.getMainSku()));
		} catch (Exception e) {
			log.error("根据mainSku(" + machineSet.getMainSku() + ")获取仓库信息时发生异常:", e);
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}

		return result;
	}

	/**
	 * 转换为外部系统使用的物料编码
	 *
	 * @param sku
	 *            内部的物料编码
	 * @return 外部的物料编码
	 */
	// 12
	private String convertToExternalSku(String sku) {
		if (sku == null || sku.equals("")) {
			return null;
		}
		ServiceResult<EisExternalSku> result = getBySkuType(sku, EisExternalSku.TYPE_R);
		EisExternalSku es = result.getResult();

		if (!result.getSuccess()) {
			log.error("通过itemService转换物料编码发生未知异常：" + result.getMessage());
			// throw new BusinessException("通过itemService转换物料编码发生未知异常：" +
			// result.getMessage());
			return sku;
		} else {
			if (es != null && es.getExternalSku() != null && !es.getExternalSku().equals("")) {
				return es.getExternalSku();
			} else {
				return sku;
			}
		}
	}

	public ServiceResult<EisExternalSku> getBySkuType(String sku, String type) {
		ServiceResult<EisExternalSku> result = new ServiceResult<EisExternalSku>();
		try {
			result.setResult(eisExternalSkuService.getBySkuType(sku, type));
		} catch (Exception e) {
			log.error("根据sku和type查询物料对照信息时，发生未知异常：", e);
			result.setMessage("根据sku和type查询物料对照信息发生未知异常：" + e.getMessage());
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 记录处理日志
	 * 
	 * @param orderReparsId
	 * @param operate
	 * @param remark
	 * @return
	 */
	public int ProcessLog(Integer orderReparsId, String userName, String operate, String remark) {
		// HttpSession session = request.getSession();
		OrderRepairLogs log = new OrderRepairLogs(); // new出来一个退货日志
		// log.setId(shopOrderRepairLogsService.getNextValId());//主键
		log.setSiteId(0);
		log.setOrderRepairId(orderReparsId);// 退货id
		log.setOperator(userName);
		// log.setOperator(Ustring.getString(session.getAttribute("userName")));
		log.setOperate(operate);
		log.setRemark(remark);
		return shopOrderRepairLogsService.insert(log);
	}

	public String findBySubSku(String subSku1, String subSku2) {
		return invMachineSetDao.findBySubSku(subSku1, subSku2);
	}

	public String selectOutping(String cOrderSn) {
		String outping = shopOperationAreaService.selectOutping(cOrderSn);
		return outping;
	}

	// 查询开提单
	public Map<String, Object> selectlessOrderSn(String cOrderSn) {
		return shopOperationAreaService.selectlessOrderSn(cOrderSn);
	}

	public Map<String, Object> queryisStop(Integer id) {
		return shopOperationAreaService.queryisStop(id);
	}

	public Map<String, Object> selecctLockedNumber(String cOrderSn) {
		return shopOperationAreaService.selecctLockedNumber(cOrderSn);
	}

	// 关闭网单和更新曾经释放的数量
	public void updateOPStatus(Integer lockedNumber, String cOrderSn) {
		shopOperationAreaService.updateOPStatus(lockedNumber, cOrderSn);
	}

	public List<Map<String, Object>> selectOPCount(Integer orderId) {
		return shopOperationAreaService.selectOPCount(orderId);
	}

	public void updataOrderStatus(Integer id) {
		shopOperationAreaService.updataOrderStatus(id);
	}

	public void updateLesQueuesIsStop(Integer id) {
		shopOperationAreaService.updateLesQueuesIsStop(id);
	}

	public Integer selectStatus(String cOrderSn) {
		return shopOperationAreaService.selectStatus(cOrderSn);
	}

	public void updateHPQueuessuccess(Integer id) {
		shopOperationAreaService.updateHPQueuessuccess(id);
	}

	public List<OrderRepairLESRecords> queryRecordSn(String operate, String storageType, String orderRepairId) {
		return shopOrderRepairLesreCordsService.queryRecordSn(operate, storageType, orderRepairId);
	}

	// 取消派工
	public JSONObject cancelDispatchedWorkers(String cOrderSn) {
		URL url = this.getClass().getResource(wsdlPath + "/TransOrderCancelFromEHAIERToHP.wsdl");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = simpleDateFormat.format(new Date());
		String[] array = date.split(" ");
		String time = array[0] + "T" + array[1];
		Inputs inputs = new Inputs();
		inputs.setORDERNO(cOrderSn);
		inputs.setCANCELDATE(time);
		TransordercancelfromehaiertohpClientEp ss = new TransordercancelfromehaiertohpClientEp(url, SERVICE1);
		TransOrderCancelFromEHAIERToHP port = ss.getTransOrderCancelFromEHAIERToHPPt();
		List<Inputs> list = new ArrayList<>();
		list.add(inputs);
		Outputs _process__return = port.process(list);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag", _process__return.getFLAG());
		jsonObject.put("msg", _process__return.getMSG());
		return jsonObject;
	}

	public List<Map<String, Object>> queryOrderProductByTB(String tbSn) {
		return shopOperationAreaService.queryOrderProductByTB(tbSn);
	}

	public void insertOrderRepairLog(Map<String, Object> map) {
		shopOperationAreaService.insertOrderRepairLog(map);
	}

	public Json establishTenLibrary(String id, String userName) {
		Json json = new Json();
		OrderrepairHPrecordsVO hPrecordsVO = shopOrderrepairHPrecordsService.queryTenLibrary(id);
		if (hPrecordsVO != null) {
			OrderRepairsVo voOrderRepairsVo = shopOrderRepairsService.queryReturnEdit(id);
			OrderRepairLESRecords cords = new OrderRepairLESRecords();
			cords.setOrderProductId(voOrderRepairsVo.getOrderProductId());// 网单id
			cords.setOrderRepairId(voOrderRepairsVo.getId());// 退货单主键
			cords.setRecordSn(voOrderRepairsVo.getcOrderSnId() + "CX2");// 出库单号
			cords.setOperate(2);// 操作，1=出库；2=入库
			cords.setStorageType(10);// 批次，22；21；10
			insert(cords); // 插入出库单
			ProcessLog(Integer.parseInt(id), userName, "生成入库单", "生成入10单 单号：" + cords.getRecordSn());
			List<OrderRepairLESRecords> repairLESRecords = shopOrderRepairLesreCordsService
					.queryTransferBatch(voOrderRepairsVo.getId().toString());
			for (int i = 0; i < repairLESRecords.size(); i++) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date1 = new Date();
                Map hpRecordIdMap = shopOrdersService.queryMinHpRecordId(repairLESRecords.get(i).getOrderRepairId().toString());

                if (hpRecordIdMap == null || hpRecordIdMap.get("id") == null){
                    log.info(" 退货单不存在不存在 " + repairLESRecords.get(i).getId().toString());
                    continue;
                }
				OrdersVo vo = shopOrdersService.queryVOMTransMission(hpRecordIdMap.get("id").toString());
				VOMSynOrderRequire synOrderRequire = new VOMSynOrderRequire();
				synOrderRequire.setOrderNo(repairLESRecords.get(i).getRecordSn());
				synOrderRequire.setSourceSn(repairLESRecords.get(i).getRecordSn());
				synOrderRequire.setOrderDate(dateFormat.format(date1));
				// 根据网点86码查询库位
//				String StoreCode = storagesService.queryCenterCode(vo.getNetPointCode());
				List<OrderRepairLESRecords> orderRepairLESRecordsListByRepairId =
						shopOrderRepairLesreCordsService.queryLesreCodrdsId(voOrderRepairsVo.getId().toString());
                String storeCode = null;
                if (orderRepairLESRecordsListByRepairId != null && orderRepairLESRecordsListByRepairId.size() >= 2){
                    OrderRepairLESRecords orderRepairLESRecordsMinId = orderRepairLESRecordsListByRepairId.get(0);
                    VomInOutStoreOrder vomInOutStoreOrder = eisVomInOutStoreOrderService.queryGetStoreCode(
                            orderRepairLESRecordsMinId.getStorageType().toString(),
                            orderRepairLESRecordsMinId.getOperate().toString(),
                            orderRepairLESRecordsMinId.getRecordSn());

                    if (vomInOutStoreOrder != null && vomInOutStoreOrder.getStoreCode() != null){
                        storeCode = vomInOutStoreOrder.getStoreCode();

                    }

                }

				if (Ustring.isEmpty(storeCode)) {
					log.info("库位不存在");
					continue;
				}
				synOrderRequire.setNetPointCode(vo.getNetPointCode());
				synOrderRequire.setStoreCode(storeCode);
				synOrderRequire.setProvince(regionsService.selectregionName(vo.getProvince()));// 收货人所在省
				synOrderRequire.setCity(regionsService.selectregionName(vo.getCity()));// 收货人所在市
				synOrderRequire.setCounty(regionsService.selectregionName(vo.getRegion()));// 收货人所在县/区
				synOrderRequire.setAddr(vo.getAddress());// 详细地址
				String Region = regionsService.selectCode(Ustring.getString(vo.getRegion()));
				synOrderRequire.setGbCode(Region); // 国标
				synOrderRequire.setName(vo.getConsignee());
				if (StringUtils.isBlank(vo.getMobile())){
                    synOrderRequire.setMobile(vo.getPhone());
                    synOrderRequire.setTel(vo.getPhone());
                }else{
                    synOrderRequire.setMobile(vo.getMobile());
                    synOrderRequire.setTel(vo.getPhone());
                }
//				synOrderRequire.setMobile(vo.getMobile());
//				synOrderRequire.setTel(vo.getPhone());
				synOrderRequire.setPostCode(vo.getZipcode());
				synOrderRequire.setPayState(vo.getPaymentStatus().toString().equals("1") ? "P1" : "P2");
				synOrderRequire.setPayTime(vo.getPayTimeStr());
				synOrderRequire.setPayType(vo.getPaymentCode());
				synOrderRequire.setInvRise(vo.getInvoiceTitle());
				synOrderRequire.setIsInv(vo.getIsReceipt());
				synOrderRequire.setInvType(InvoiceConst.INVOICE_TYPE.get(vo.getType()));
				synOrderRequire.setTaxBearer(vo.getTaxPayerNumber());
				synOrderRequire.setRecAddr(vo.getRegisterAddressAndPhone());
				synOrderRequire.setRemark(repairLESRecords.get(i).getRepairSn());
				StringBuffer sb = new StringBuffer(repairLESRecords.get(i).getRecordSn());
				sb.deleteCharAt(sb.length() - 1);
				String Reorder = repairLESRecords.get(i).getRecordSn().substring(
						repairLESRecords.get(i).getRecordSn().length() - 3,
						repairLESRecords.get(i).getRecordSn().length());
				String lesId = null;
				if ("CX1".equals(Reorder)) {
					Reorder = sb + "2";
					if ("22".equals(Ustring.getString(repairLESRecords.get(i).getStorageType()))) {
						synOrderRequire.setOrderType("13");
						synOrderRequire.setBusType("1");// 业务类型
					}
				} else if ("CX2".equals(Reorder)) {
					Reorder = sb + "1";
					if ("10".equals(Ustring.getString(repairLESRecords.get(i).getStorageType()))) {
						synOrderRequire.setOrderType("14");
						synOrderRequire.setBusType("1");// 业务类型
					}
				} else {
					Reorder = repairLESRecords.get(i).getRecordSn().substring(0,
							repairLESRecords.get(i).getRecordSn().length() - 3);
					synOrderRequire.setOrderType("10");
					synOrderRequire.setBusType("2");// 业务类型
					synOrderRequire.setRemark("");
				}
				synOrderRequire.setReorder(Reorder);
				synOrderRequire.setRecBank(vo.getBankNameAndAccount());
				synOrderRequire.setFreight(vo.getShippingAmount().doubleValue());
				synOrderRequire.setBillSum(vo.getOrderAmount().doubleValue());
				VOMSynSubOrderRequire synSubOrderRequire = null;
				// 判断是不是套机
				List<InvMachineSet> machineSets = stockInvMachineSetService
						.getByMainSku(repairLESRecords.get(i).getSku());
				List<VOMSynSubOrderRequire> subOrderList = new ArrayList<VOMSynSubOrderRequire>();
				if (machineSets.size() > 0) {// 如果是套机
					for (int j = 0; j < machineSets.size(); j++) {
						synSubOrderRequire = new VOMSynSubOrderRequire();
						synSubOrderRequire.setProductCode(machineSets.get(j).getSubSku());// 子sku
						synSubOrderRequire.setItemNo(Ustring.getString(j));// 行号
						synSubOrderRequire.setStorageType(Ustring.getString(repairLESRecords.get(i).getStorageType()));
						synSubOrderRequire.setHrCode(machineSets.get(j).getSubSku());
						synSubOrderRequire.setProdes(machineSets.get(j).getMaktx2());
						synSubOrderRequire.setNumber(repairLESRecords.get(i).getNumber());
						synSubOrderRequire.setUnprice(repairLESRecords.get(i).getPrice());
						synSubOrderRequire.setReitem(Ustring.getString(machineSets.size()));
						subOrderList.add(synSubOrderRequire);
					}
				} else {
					synSubOrderRequire = new VOMSynSubOrderRequire();
					// 套机列表查询
					String sku = helpUtils.getProductCode(repairLESRecords.get(i).getId(),
							repairLESRecords.get(i).getSku(), repairLESRecords.get(i).getPushFailNumber());
					synSubOrderRequire.setProductCode(sku);
					synSubOrderRequire.setItemNo("1");
					synSubOrderRequire.setStorageType(Ustring.getString(repairLESRecords.get(i).getStorageType()));
					synSubOrderRequire.setHrCode(sku);
					synSubOrderRequire.setProdes(repairLESRecords.get(i).getProductName());
					synSubOrderRequire.setNumber(repairLESRecords.get(i).getNumber());
					synSubOrderRequire.setUnprice(repairLESRecords.get(i).getPrice());
					synSubOrderRequire.setReitem(Ustring.getString(repairLESRecords.size()));
					subOrderList.add(synSubOrderRequire);
				}
				synOrderRequire.setSubOrderList(subOrderList);
				// 推送VOM
				VOMOrderResponse vomOrderResponse = vomOrderModel.synOrderInfo(synOrderRequire);
				if ("成功".equals(vomOrderResponse.getMsg())) {
					OrderRepairLESRecords records = new OrderRepairLESRecords();
					records.setId(repairLESRecords.get(i).getId());
					records.setFalg("1"); // 修改推送状态
					shopOrderRepairLesreCordsService.updataRecords(records);
					ProcessLog(repairLESRecords.get(i).getOrderRepairId(), "系统", "操作",
							"推送VOM物流成功 单号:" + repairLESRecords.get(i).getRecordSn());
				}
				shopOrderRepairsService.RepairsTermination(Ustring.getString(hPrecordsVO.getId()), "3",
						"手动 22转10 流程结束");
				// 更改出入库单生成状态 改成3
				shopOrderrepairHPrecordsService.updataOutOfStorage("4", hPrecordsVO.getId().toString());
				json.setMsg("22转10操作成功 10单号：" + cords.getRecordSn());
                    json.setSuccess(true);
			}
		} else {
			json.setMsg("未生成22出库单 或者HP未返回二次鉴定结果");
			json.setSuccess(false);
		}
		return json;
	}


    public Json b2cestablishTenLibrary(String id, String userName) {
        Json json = new Json();
            OrderRepairsVo voOrderRepairsVo = shopOrderRepairsService.queryReturnEdit(id);
            OrderRepairLESRecords cords = new OrderRepairLESRecords();
            cords.setOrderProductId(voOrderRepairsVo.getOrderProductId());// 网单id
            cords.setOrderRepairId(voOrderRepairsVo.getId());// 退货单主键
            cords.setRecordSn(voOrderRepairsVo.getRepairSn());// 出库单号
            cords.setOperate(2);// 操作，1=出库；2=入库
            cords.setStorageType(10);// 批次，22；21；10
        List<OrderRepairLESRecords> orderRepairLESRecords = shopOrderRepairLesreCordsService.queryRecordSn("2", "10", id);
        int lesorderid = 0;
        if (orderRepairLESRecords != null && orderRepairLESRecords.size() > 0){
            lesorderid = orderRepairLESRecords.get(0).getId();
        }else{
            lesorderid = insert(cords);// 插入出库单
            ProcessLog(Integer.parseInt(id), userName, "生成入库单", "生成入10单 单号：" + cords.getRecordSn());
        }

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = new Date();
                OrdersVo vo = shopOrdersService.queryb2cVOM(String.valueOf(lesorderid));
                VOMSynOrderRequire synOrderRequire = new VOMSynOrderRequire();
                synOrderRequire.setOrderNo(voOrderRepairsVo.getRepairSn());
                synOrderRequire.setSourceSn(voOrderRepairsVo.getRepairSn());
                synOrderRequire.setOrderDate(dateFormat.format(date1));

                Map resultMap = shopOrdersService.queryFiveYard(vo.getsCode());
		Storages storages = storagesService.getByCode(vo.getsCode());

		if (resultMap == null || resultMap.get("fiveYard") == null || storages == null){
                    json.setMsg("入10库失败， 库位信息获取失败。");
                    json.setSuccess(false);
                    return json;
        }

                synOrderRequire.setNetPointCode(resultMap.get("fiveYard").toString());
                synOrderRequire.setStoreCode(storages.getCenterCode());
                synOrderRequire.setProvince(regionsService.selectregionName(vo.getProvince()));// 收货人所在省
                synOrderRequire.setCity(regionsService.selectregionName(vo.getCity()));// 收货人所在市
                synOrderRequire.setCounty(regionsService.selectregionName(vo.getRegion()));// 收货人所在县/区
                synOrderRequire.setAddr(vo.getAddress());// 详细地址
                String Region = regionsService.selectCode(Ustring.getString(vo.getRegion()));
                synOrderRequire.setGbCode(Region); // 国标
                synOrderRequire.setName(vo.getConsignee());
                if (StringUtils.isBlank(vo.getMobile())){
                    synOrderRequire.setMobile(vo.getPhone());
                    synOrderRequire.setTel(vo.getPhone());
                }else{
                    synOrderRequire.setMobile(vo.getMobile());
                    synOrderRequire.setTel(vo.getPhone());
                }
//                synOrderRequire.setMobile(vo.getMobile());
//                synOrderRequire.setTel(vo.getPhone());
                synOrderRequire.setPostCode(vo.getZipcode());
                synOrderRequire.setPayState(vo.getPaymentStatus().toString().equals("1") ? "P1" : "P2");
                synOrderRequire.setPayTime(vo.getPayTimeStr());
                synOrderRequire.setPayType(vo.getPaymentCode());
                synOrderRequire.setInvRise(vo.getInvoiceTitle());
                synOrderRequire.setIsInv(vo.getIsReceipt());
                synOrderRequire.setInvType(InvoiceConst.INVOICE_TYPE.get(vo.getType()));
                synOrderRequire.setTaxBearer(vo.getTaxPayerNumber());
                synOrderRequire.setRecAddr(vo.getRegisterAddressAndPhone());
                synOrderRequire.setRemark(voOrderRepairsVo.getRepairSn());
                StringBuffer sb = new StringBuffer(voOrderRepairsVo.getRepairSn());
                sb.deleteCharAt(sb.length() - 1);
                String lesId = null;
                String Reorder = voOrderRepairsVo.getRepairSn().substring(0,
                                            voOrderRepairsVo.getRepairSn().length() - 3);
                synOrderRequire.setOrderType("10");
                synOrderRequire.setBusType("70");// 业务类型
                synOrderRequire.setRemark("");

                synOrderRequire.setReorder(Reorder);
                synOrderRequire.setRecBank(vo.getBankNameAndAccount());
                synOrderRequire.setFreight(vo.getShippingAmount().doubleValue());
                synOrderRequire.setBillSum(vo.getOrderAmount().doubleValue());
                VOMSynSubOrderRequire synSubOrderRequire = null;

                List<VOMSynSubOrderRequire> subOrderList = new ArrayList<VOMSynSubOrderRequire>();

                    synSubOrderRequire = new VOMSynSubOrderRequire();
                    // 套机列表查询
                    synSubOrderRequire.setProductCode(vo.getSku());
                    synSubOrderRequire.setItemNo("1");
                    synSubOrderRequire.setStorageType("10");
                    synSubOrderRequire.setHrCode(vo.getSku());
                    synSubOrderRequire.setProdes(vo.getProductName());
                    synSubOrderRequire.setNumber(Integer.valueOf(vo.getNumber()));
                    synSubOrderRequire.setUnprice(Double.valueOf(vo.getPrice()));
                    synSubOrderRequire.setReitem("1");
                    subOrderList.add(synSubOrderRequire);
                synOrderRequire.setSubOrderList(subOrderList);
        Object synjson = JSONObject.toJSON(synOrderRequire);
        log.info("发送入10库请求");
        log.info(synjson.toString());
        // 推送VOM
                VOMOrderResponse vomOrderResponse = vomOrderModel.synOrderInfo(synOrderRequire);
                if ("成功".equals(vomOrderResponse.getMsg())) {
                    OrderRepairLESRecords records = new OrderRepairLESRecords();
                    records.setId(lesorderid);
                    records.setFalg("1"); // 修改推送状态
                    shopOrderRepairLesreCordsService.updataRecords(records);
                    ProcessLog(Integer.valueOf(id), "系统", "操作",
                                        "推送VOM物流成功 单号:" + voOrderRepairsVo.getRepairSn());
                    json.setMsg("入10库成功，单号：" + cords.getRecordSn());
                    json.setSuccess(true);
                }else{
                    json.setMsg("入10库失败，请稍候重试。");
                    json.setSuccess(false);
                }

        return json;
    }

    public Json establishTenLibraryOnLine(String id, String userName) {
        Json json = new Json();
        OrderrepairHPrecordsVO hPrecordsVO = shopOrderrepairHPrecordsService.queryRepairOrderInfo(id);
        if (hPrecordsVO != null) {
            OrderRepairsVo voOrderRepairsVo = shopOrderRepairsService.queryReturnEdit(id);
            OrderRepairLESRecords cords = new OrderRepairLESRecords();
            cords.setOrderProductId(voOrderRepairsVo.getOrderProductId());// 网单id
            cords.setOrderRepairId(voOrderRepairsVo.getId());// 退货单主键
            cords.setRecordSn(voOrderRepairsVo.getcOrderSnId() + "CX2");// 出库单号
            cords.setOperate(2);// 操作，1=出库；2=入库
            cords.setStorageType(10);// 批次，22；21；10
//            insert(cords); // 插入出库单
//            ProcessLog(Integer.parseInt(id), userName, "生成入库单", "生成入10单 单号：" + cords.getRecordSn());
            List<OrderRepairLESRecords> repairLESRecords = shopOrderRepairLesreCordsService
                                                            .queryRepairLesOrder(voOrderRepairsVo.getId().toString());
            for (int i = 0; i < repairLESRecords.size(); i++) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = new Date();
                OrdersVo vo = shopOrdersService.queryRepairVOMInfo(repairLESRecords.get(i).getId().toString());
                VOMSynOrderRequire synOrderRequire = new VOMSynOrderRequire();
                synOrderRequire.setOrderNo(repairLESRecords.get(i).getRecordSn());
                synOrderRequire.setSourceSn(repairLESRecords.get(i).getRecordSn());
                synOrderRequire.setOrderDate(dateFormat.format(date1));
                // 根据网点86码查询库位
                String StoreCode = storagesService.queryCenterCode(vo.getNetPointCode());
                if (Ustring.isEmpty(StoreCode)) {
                    log.info("库位不存在");
                    continue;
                }
                synOrderRequire.setNetPointCode(vo.getNetPointCode());
                synOrderRequire.setStoreCode(StoreCode);
                synOrderRequire.setProvince(regionsService.selectregionName(vo.getProvince()));// 收货人所在省
                synOrderRequire.setCity(regionsService.selectregionName(vo.getCity()));// 收货人所在市
                synOrderRequire.setCounty(regionsService.selectregionName(vo.getRegion()));// 收货人所在县/区
                synOrderRequire.setAddr(vo.getAddress());// 详细地址
                String Region = regionsService.selectCode(Ustring.getString(vo.getRegion()));
                synOrderRequire.setGbCode(Region); // 国标
                synOrderRequire.setName(vo.getConsignee());
                if (StringUtils.isBlank(vo.getMobile())){
                    synOrderRequire.setMobile(vo.getPhone());
                    synOrderRequire.setTel(vo.getPhone());
                }else{
                    synOrderRequire.setMobile(vo.getMobile());
                    synOrderRequire.setTel(vo.getPhone());
                }
//				synOrderRequire.setMobile(vo.getMobile());
//				synOrderRequire.setTel(vo.getPhone());
                synOrderRequire.setPostCode(vo.getZipcode());
                synOrderRequire.setPayState(vo.getPaymentStatus().toString().equals("1") ? "P1" : "P2");
                synOrderRequire.setPayTime(vo.getPayTimeStr());
                synOrderRequire.setPayType(vo.getPaymentCode());
                synOrderRequire.setInvRise(vo.getInvoiceTitle());
                synOrderRequire.setIsInv(vo.getIsReceipt());
                synOrderRequire.setInvType(InvoiceConst.INVOICE_TYPE.get(vo.getType()));
                synOrderRequire.setTaxBearer(vo.getTaxPayerNumber());
                synOrderRequire.setRecAddr(vo.getRegisterAddressAndPhone());
                synOrderRequire.setRemark(repairLESRecords.get(i).getRepairSn());
                StringBuffer sb = new StringBuffer(repairLESRecords.get(i).getRecordSn());
                sb.deleteCharAt(sb.length() - 1);
                String Reorder = repairLESRecords.get(i).getRecordSn().substring(
                        repairLESRecords.get(i).getRecordSn().length() - 5,
                        repairLESRecords.get(i).getRecordSn().length());
                String lesId = null;
                if ("CX1".equals(Reorder)) {
                    Reorder = sb + "2";
                    if ("22".equals(Ustring.getString(repairLESRecords.get(i).getStorageType()))) {
                        synOrderRequire.setOrderType("13");
                        synOrderRequire.setBusType("1");// 业务类型
                    }
                } else if ("CX2".equals(Reorder)) {
                    Reorder = sb + "1";
                    if ("10".equals(Ustring.getString(repairLESRecords.get(i).getStorageType()))) {
                        synOrderRequire.setOrderType("14");
                        synOrderRequire.setBusType("1");// 业务类型
                    }
                } else {
                    Reorder = repairLESRecords.get(i).getRecordSn().substring(0,
                            repairLESRecords.get(i).getRecordSn().length() - 5);
                    synOrderRequire.setOrderType("10");
                    synOrderRequire.setBusType("2");// 业务类型
                    synOrderRequire.setRemark("");
                }
                synOrderRequire.setReorder(Reorder);
                synOrderRequire.setRecBank(vo.getBankNameAndAccount());
                synOrderRequire.setFreight(vo.getShippingAmount().doubleValue());
                synOrderRequire.setBillSum(vo.getOrderAmount().doubleValue());
                VOMSynSubOrderRequire synSubOrderRequire = null;
                // 判断是不是套机
                List<InvMachineSet> machineSets = stockInvMachineSetService
                        .getByMainSku(repairLESRecords.get(i).getSku());
                List<VOMSynSubOrderRequire> subOrderList = new ArrayList<VOMSynSubOrderRequire>();
                if (machineSets.size() > 0) {// 如果是套机
                    for (int j = 0; j < machineSets.size(); j++) {
                        synSubOrderRequire = new VOMSynSubOrderRequire();
                        synSubOrderRequire.setProductCode(machineSets.get(j).getSubSku());// 子sku
                        synSubOrderRequire.setItemNo(Ustring.getString(j));// 行号
                        synSubOrderRequire.setStorageType(Ustring.getString(repairLESRecords.get(i).getStorageType()));
                        synSubOrderRequire.setHrCode(machineSets.get(j).getSubSku());
                        synSubOrderRequire.setProdes(machineSets.get(j).getMaktx2());
                        synSubOrderRequire.setNumber(repairLESRecords.get(i).getNumber());
                        synSubOrderRequire.setUnprice(repairLESRecords.get(i).getPrice());
                        synSubOrderRequire.setReitem(Ustring.getString(machineSets.size()));
                        subOrderList.add(synSubOrderRequire);
                    }
                } else {
                    synSubOrderRequire = new VOMSynSubOrderRequire();
                    // 套机列表查询
                    String sku = helpUtils.getProductCode(repairLESRecords.get(i).getId(),
                            repairLESRecords.get(i).getSku(), repairLESRecords.get(i).getPushFailNumber());
                    synSubOrderRequire.setProductCode(sku);
                    synSubOrderRequire.setItemNo("1");
                    synSubOrderRequire.setStorageType(Ustring.getString(repairLESRecords.get(i).getStorageType()));
                    synSubOrderRequire.setHrCode(sku);
                    synSubOrderRequire.setProdes(repairLESRecords.get(i).getProductName());
                    synSubOrderRequire.setNumber(repairLESRecords.get(i).getNumber());
                    synSubOrderRequire.setUnprice(repairLESRecords.get(i).getPrice());
                    synSubOrderRequire.setReitem(Ustring.getString(repairLESRecords.size()));
                    subOrderList.add(synSubOrderRequire);
                }
                synOrderRequire.setSubOrderList(subOrderList);
                log.info("push vom online param:" + JSONObject.toJSON(synOrderRequire).toString());
                // 推送VOM
                VOMOrderResponse vomOrderResponse = vomOrderModel.synOrderInfo(synOrderRequire);
                log.info("push vom online result:" + vomOrderResponse.getMsg());
                if ("成功".equals(vomOrderResponse.getMsg())) {
                    OrderRepairLESRecords records = new OrderRepairLESRecords();
                    records.setId(repairLESRecords.get(i).getId());
                    records.setFalg("1"); // 修改推送状态
//					shopOrderRepairLesreCordsService.updataRecords(records);
//					ProcessLog(repairLESRecords.get(i).getOrderRepairId(), "系统", "操作",
//							"推送VOM物流成功 单号:" + repairLESRecords.get(i).getRecordSn());
                    json.setMsg("10操作成功 10单号：" + cords.getRecordSn());
                    json.setSuccess(true);
                }else{
                    json.setMsg("10操作失败");
                    json.setSuccess(false);

                }
                return json;
//				shopOrderRepairsService.RepairsTermination(Ustring.getString(hPrecordsVO.getId()), "3",
//						"手动 22转10 流程结束");
                // 更改出入库单生成状态 改成3
//				shopOrderrepairHPrecordsService.updataOutOfStorage("4", hPrecordsVO.getId().toString());
//				json.setMsg("22转10操作成功 10单号：" + cords.getRecordSn());
//				json.setSuccess(true);
            }
        } else {
            json.setMsg("未生成22出库单 或者HP未返回二次鉴定结果");
            json.setSuccess(false);
        }
        return json;
    }

    //释放库存,取消网单和订单
    public JSONObject releaseStock(String cOrderSn){
        JSONObject jsonObject=new JSONObject();
        //释放库存
        Map<String,Object> LockedNumberMap = selecctLockedNumber(cOrderSn);
        //如果未占有库存
        if(StringUtil.isEmpty(LockedNumberMap.get("lockedNumber").toString())){
            logger.info("网单号:"+cOrderSn+"没用锁定库存,不进行释放");
            //关闭网单和更新曾经释放的数量
            updateOPStatus(0,cOrderSn);
            jsonObject.put("flag", true);
            return jsonObject;
        }
        ServiceResult<Boolean> result=stockCenterHopStockService.releaseFrozenStockQty(LockedNumberMap.get("sku").toString(),
                Integer.parseInt(LockedNumberMap.get("lockedNumber").toString()),cOrderSn,RELEASE_BY_ZBCC);
        if (result.getSuccess()){
            //关闭网单和更新曾经释放的数量
            updateOPStatus(Integer.parseInt(LockedNumberMap.get("lockedNumber").toString()),cOrderSn);
            jsonObject.put("flag", true);
            return jsonObject;
        }else{
            jsonObject.put("flag", false);
            return jsonObject;
        }
    }


	@Override
	public List<Map<String, Object>> queryNetSheetExportDate(OrderProductsVo vo) {
		return shopOperationAreaService.queryNetSheetExportDate(vo);
	}
	@Override
	public Map<String,Object> selectData(String cOrderSn){
		return shopOperationAreaService.selectData(cOrderSn);
	}
	public void insertLog(Map<String,Object> map){
		shopOrderOperateLogsService.insertLog(map);
	}

	@Override
	public int queryCountByOrderNo(String orderNo) {
		return eisVomShippingStatusService.queryCountByOrderNo(orderNo);
	}

    private boolean getFiveYard(OrderRepairLESRecords orderRepairLESRecords, String storeCode, VOMSynOrderRequire synOrderRequire) {
        String storagesCode = storagesService.queryCode(storeCode);
        if (storagesCode == null){
            log.error("库位信息获取失败。" + orderRepairLESRecords.getRecordSn());
            return true;
        }
        Map map = shopOrdersService.queryFiveYard(storagesCode);
        if (map == null || map.get("fiveYard") == null){
            log.error("库位信息获取失败。" + orderRepairLESRecords.getRecordSn());
            return true;
        }
        synOrderRequire.setRemark5(map.get("fiveYard").toString());

        List<Map<String, Object>> storageRegionMapList = shopOrderRepairLesreCordsService.queryStorageRegion(storagesCode);
        if (storageRegionMapList != null && storageRegionMapList.size() > 0){
            Map<String, Object> stringObjectMap = storageRegionMapList.get(0);

            synOrderRequire.setName("网单存性变更");
            synOrderRequire.setProvince(stringObjectMap.get("province").toString());
            synOrderRequire.setCity(stringObjectMap.get("city").toString());
            synOrderRequire.setCounty(stringObjectMap.get("region").toString());
            synOrderRequire.setAddr(stringObjectMap.get("adress").toString());
            synOrderRequire.setPostCode(stringObjectMap.get("zip_code").toString());
            synOrderRequire.setMobile(stringObjectMap.get("tel").toString());
            synOrderRequire.setTel(stringObjectMap.get("tel").toString());
        }else{
            log.error("库位区域信息获取失败=" + orderRepairLESRecords.getRecordSn() + " " + storagesCode);
            return true;
        }

        return false;
    }

    /**
     *
     * @param id
     * @param success
     * @return
     */
    public int updateRepairLesRecordcnSuccess(String id, Integer success){
        return shopOrderRepairLesreCordsService.updateRepairLesRecordcnSuccess(id, success);
    }

    public List<OrderRepairLESRecords> queryLesreCodrdsId(String id){

        List<OrderRepairLESRecords> orderRepairLESRecords = shopOrderRepairLesreCordsService.queryLesreCodrdsId(id);

        return orderRepairLESRecords;
    }

	@Override
	public ServiceResult<JSONObject> Rejectionsinglereset(String id) {
		ServiceResult<JSONObject> serviceResult = new ServiceResult<>();
		try {
			int count = shopOperationAreaService.Rejectionsinglereset(id);
			if(count == 1){
				serviceResult.setSuccess(true);
				serviceResult.setMessage("重置成功");
			}else {
				serviceResult.setSuccess(false);
				serviceResult.setMessage("重置失败");
			}
		}catch (Exception e){
			serviceResult.setSuccess(false);
			serviceResult.setMessage(e.getMessage());
			log.error("重置异常",e);
		}
		return serviceResult;
	}
}
