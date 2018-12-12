package com.haier.svc.util;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.OmsOrderVO;
import com.haier.purchase.data.service.PurchaseOmsSyncService;
import com.haier.svc.purchase.omsquery.QueryOrderFromEHaierToOMS;
import com.haier.svc.purchase.omsquery.QueryOrderFromEHaierToOMS_Service;
import com.haier.svc.purchase.omsquery.RequestData;
import com.haier.svc.purchase.omsquery.ResponseData;

/**
 * 
 * OMS数据抓取线程
 * 
 * @Version: 1.0
 * @Author: zhanghan 张晗
 * @Email:
 * 
 */
public class OmsDataFetcher extends Thread {
	private static org.apache.log4j.Logger log				  = org.apache.log4j.LogManager
		.getLogger(OmsDataFetcher.class);
	private static int					   FETCH_MODE		  = 1;							// 0:一次查询一个OMS订单 1:批量查询OMS订单
	private List<T2DataQueueWritter>	   l_writter		  = null;
	private List<String>				   orderIds;
	private boolean						   isCalcMD5		  = false;
	private CountDownLatch				   countDownLatch	  = null;
	private IFetchCallback				   cb				  = null;

	// 性能统计参数
	private long						   syncTotalTime	  = 0;							// 与外围系统同步时间(总时间)
	private long						   queueWaitTotalTime = 0;							// 插入队列等待时间(总时间)
	private String						   wsdlLocation;

	public OmsDataFetcher(List<T2DataQueueWritter> l_writter, PurchaseOmsSyncService purchaseOmsSyncService,
						  List<String> orderIds, boolean isCalcMD5, int threadCount,
						  CountDownLatch countDownLatch, IFetchCallback cb,String wsdlLocation) {
		this.isCalcMD5 = isCalcMD5;
		this.l_writter = l_writter;
		this.orderIds = orderIds;
		this.countDownLatch = countDownLatch;
		this.cb = cb;
		this.wsdlLocation = wsdlLocation;
	}

	private String getStatInfo() {
		return "syncTotalTime:" + syncTotalTime + "\tqueueWaitTotalTime:" + queueWaitTotalTime;
	}

	private List<OmsOrderVO> getDataFromOMS(String orderId) throws Exception {
		try {
			List<OmsOrderVO> l_vo = new ArrayList<OmsOrderVO>();

//			String path = "file:" + this.getClass()
//				.getResource(wsdlLocation + "/QueryOrderFromEHaierToOMS.wsdl").getPath();
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

//			URL url = new URL(path);
			URL url = this.getClass().getResource(
        			wsdlLocation + "/QueryOrderFromEHaierToOMS.wsdl");
			QueryOrderFromEHaierToOMS_Service service = new QueryOrderFromEHaierToOMS_Service(url);
			QueryOrderFromEHaierToOMS soap = service.getQueryOrderFromEHaierToOMSSOAP();
			RequestData requestData = new RequestData();
			requestData.setSysName("EHAIER");
			requestData.setOrderSoCode(orderId);
			List<ResponseData> outputs = soap.queryOrderFromEHaierToOMS(requestData);
			for (ResponseData output : outputs) {
				try {
					if (!StringUtils.isNotEmpty(output.getFlag().trim())
						&& !output.getFlag().equalsIgnoreCase("F")) {
						OmsOrderVO oov = new OmsOrderVO();
						oov.setActualShipDate(
							WSUtils.xmlCalendarToString(output.getActualShipDate()));
						oov.setCancelReason(output.getCancelreason());
						oov.setConfirmDate(WSUtils.xmlCalendarToString(output.getConfirmDate()));
						oov.setCustOrderCode(output.getCustOrderCode());
						oov.setCustRevQty(String.valueOf((output.getCustrevqty())));
						output.getFaultDetail();
						output.getFlag();
						oov.setGvsDn(output.getGvsDN());
						oov.setGvsOrderCode(output.getGvsOrderCode());
						oov.setLatestArrivalTime(
							WSUtils.xmlCalendarToString(output.getLatestArrivalTime()));

						if (StringUtils.isNotEmpty(output.getLatestLeaveBaseDate())) {
							String normalDate = output.getLatestLeaveBaseDate();
							try {
								Date date = dateTimeFormat.parse(output.getLatestLeaveBaseDate());
								normalDate = dateFormat.format(date);
							} catch (Exception ex) {
								try {
									dateFormat.parse(output.getLatestLeaveBaseDate());
								} catch (Exception ex2) {
									normalDate = null;
									log.warn("不能识别的最晚离基地日期:" + output.getLatestLeaveBaseDate()
											 + "[orderId:" + output.getCustOrderCode() + "]");
								}
							}
							if (normalDate != null) {
								oov.setLatest_leave_base_date(normalDate);
							}
						} else {
							oov.setLatest_leave_base_date(null);
						}

						oov.setMade_fectory_code(output.getMadeFectoryCode());
						oov.setMade_fectory_name(output.getMadeFectoryName());
						output.getMessage();
						oov.setOesPredictRevDate(
							WSUtils.xmlCalendarToString(output.getOesPredictrevDate()));
						oov.setOrderSoCode(output.getOrderSoCode());
						oov.setOrderState(output.getOrderState());
						oov.setOrder_type_name(output.getOrderTypeName());
						oov.setPlannedShipDate(
							WSUtils.xmlCalendarToString(output.getPlannedShipDate()));
						oov.setPodDate(WSUtils.normalDateString(output.getPodDate()));
						oov.setPromisedArrivalDate(
							WSUtils.xmlCalendarToString(output.getPromisedArrivalDate()));
						oov.setReqArrivalDate(
							WSUtils.xmlCalendarToString(output.getReqArrivalDate()));
						oov.setSignDate(WSUtils.xmlCalendarToString(output.getSignDate()));
						oov.setSubmitDate(WSUtils.xmlCalendarToString(output.getSubmitDate()));
						oov.setTradeSendDate(
							WSUtils.xmlCalendarToString(output.getTradeSendDate()));
						oov.setTransitArrivalDate(
							WSUtils.xmlCalendarToString(output.getTransitArrivalDate()));
						oov.setTransit_code(output.getTransitCode());
						oov.setWp_order_id(output.getWpOrderId());
						oov.setProdseriescode(output.getProdSeriesCode());
						oov.setCustpodetailcode(output.getCustPoDetailCode());
						if (isCalcMD5)
							oov.setMd5(MD5Utils.md5(oov.toString()));

						l_vo.add(oov);
					} else {
						log.warn("从OMS获取订单时[订单号:" + orderId + "]，发生异常1:" + output.getFaultDetail());
					}
				} catch (Exception ex) {
					log.error("转换数据出错[订单号:" + orderId + "]，发生异常2", ex);
					ex.printStackTrace();
				}
			}
			return l_vo;
		} catch (Exception e) {
			log.error("从OMS获取订单时[订单号:" + orderId + "]，发生异常3", e);
			throw e;
		}
	}

	@Override
	public void run() {
		log.debug("OmsDataFetcher Thread Id = " + this.getId() + " start.");

		List<OmsOrderVO> omsOrders = new ArrayList<OmsOrderVO>();
		ServiceResult<Boolean> result = new ServiceResult<Boolean>();

		int count = 0;
		if (orderIds != null) {
			if (FETCH_MODE == 0) {
				for (int i = 0; i < orderIds.size(); i++) {
					try {
						log.debug("OmsDataFetcher Thread Id = " + this.getId() + " has left "
								  + (orderIds.size() - i - 1) + " orders.");

						String orderId = orderIds.get(i);

						syncTotalTime -= System.currentTimeMillis();
						List<OmsOrderVO> l_vo = getDataFromOMS(orderId);
						syncTotalTime += System.currentTimeMillis();
						omsOrders.addAll(l_vo);

						while (l_writter.size() == 0) {
							Thread.sleep(100);
						}

						for (OmsOrderVO oov : l_vo) {
							queueWaitTotalTime -= System.currentTimeMillis();
							while (true) {
								T2DataQueueWritter writter = l_writter.get(count);
								if (writter.addTaskItem(oov)) {
									break;
								} else {
									count++;
									if (count == l_writter.size()) {
										count = 0;
									}
								}
							}
							queueWaitTotalTime += System.currentTimeMillis();
						}
					} catch (Exception e) {
						log.error("", e);
						result.setSuccess(false);
						result.setMessage(e.getMessage());
						e.printStackTrace();
					}
				}
			} else if (FETCH_MODE == 1) {
				log.debug("OmsDataFetcher Thread Id = " + this.getId() + " fetch " + orderIds.size()
						  + " orders.");

				String orderIdStr = "";
				for (int i = 0; i < orderIds.size(); i++) {
					orderIdStr += orderIds.get(i) + ",";
				}
				if (orderIdStr.length() > 0) {
					orderIdStr = orderIdStr.substring(0, orderIdStr.length() - 1);
				}

				try {

					syncTotalTime -= System.currentTimeMillis();
					List<OmsOrderVO> l_vo = getDataFromOMS(orderIdStr);
					syncTotalTime += System.currentTimeMillis();
					omsOrders.addAll(l_vo);

					if (l_vo.size() > 0) {
						log.debug("Fetch order success.size:" + l_vo.size());
					}

					while (l_writter.size() == 0) {
						Thread.sleep(100);
					}

					for (OmsOrderVO oov : l_vo) {
						queueWaitTotalTime -= System.currentTimeMillis();
						while (true) {
							T2DataQueueWritter writter = l_writter.get(count);
							count++;
							if (count == l_writter.size()) {
								count = 0;
							}
							if (writter.addTaskItem(oov)) {
								break;
							}
						}
						queueWaitTotalTime += System.currentTimeMillis();
					}
				} catch (Exception e) {
					log.error("", e);
					result.setSuccess(false);
					result.setMessage(e.getMessage());
					e.printStackTrace();
				}

			}
		}

		log.debug("OmsDataFetcher Thread Id = " + this.getId() + " end\r\nStat:" + getStatInfo());

		if (countDownLatch != null) {
			countDownLatch.countDown();
			log.debug("Count Down Latch:" + countDownLatch.getCount());
		}
		if (countDownLatch.getCount() == 0) {
			log.debug("All Thread Fetcher Over.");

			// 为了防止线程没有全部启动完毕就发出结束信号的情况,需要等待所有线程都启动后再发出信号
			synchronized (l_writter) {
				cb.threadFinish(-1);
			}

		}
	}
}