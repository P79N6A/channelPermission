package com.haier.shop.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.shop.dao.shopread.MemberInvoicesReadDao;
import com.haier.shop.dao.shopread.OrderProductsReadDao;
import com.haier.shop.dao.shopread.OrdersReadDao;
import com.haier.shop.dao.shopwrite.InvoicesWwwLogsWriteDao;
import com.haier.shop.dao.shopwrite.OrderOperateLogsWriteDao;
import com.haier.shop.dao.shopwrite.OrderProductsNewDao;
import com.haier.shop.dao.shopwrite.OrderProductsWriteDao;
import com.haier.shop.dao.shopwrite.OrdersDao;
import com.haier.shop.model.InvoicesWwwLogs;
import com.haier.shop.model.MemberInvoices;
import com.haier.shop.model.OrderOperateLogs;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.Orders;
import com.haier.shop.service.OrdersService;
import com.mysql.fabric.xmlrpc.base.Data;
@Service
public class OrdersServiceImpl implements OrdersService{
	
	private static final Logger logger = LogManager.getLogger(OrdersServiceImpl.class);
	@Autowired
	OrdersDao ordersDao;
	@Autowired
	OrdersReadDao ordersReadDao;
	@Autowired
	OrderOperateLogsWriteDao orderOperateLogsWriteDao;
	@Autowired
	InvoicesWwwLogsWriteDao invoicesWwwLogsWriteDaoDao;
	@Autowired
	MemberInvoicesReadDao memberInvoicesReadDao;
	@Autowired
	OrderProductsWriteDao orderProductsWriteDao;
	@Autowired
	OrderProductsReadDao orderProductsReadDao;
	@Autowired
	OrderProductsNewDao orderProductsNewDao;
	@Override
	public List<Orders> getCodNotConfirmOrders() {
		// TODO Auto-generated method stub
		return ordersDao.getCodNotConfirmOrders();
	}

	@Override
	public int updateCodConfirmState(Integer id) {
		// TODO Auto-generated method stub
		return ordersDao.updateCodConfirmState(id);
	}

	@Override
	public int insertOrders(Orders orders) {
		// TODO Auto-generated method stub
		ordersDao.insertOrders(orders);
		return orders.getId();
	}

	@Override
	public Orders get(int id) {
		// TODO Auto-generated method stub
		return ordersReadDao.get(id);
	}
	
	@Override
	public List<Orders> getOrderList(String sourceOrderNumber) {
		// TODO Auto-generated method stub
		return ordersDao.getOrderList(sourceOrderNumber);
	}
	
	@Override
    @Transactional(value = "shopTransactionManagerWrite", isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void updateSourceOrderNumber(String sourceOrderNumber,String orderNumber,OrderOperateLogs orderOperateLogs) {
		ordersDao.updateSourceOrderNumber(sourceOrderNumber,orderNumber);
		orderOperateLogsWriteDao.insert(orderOperateLogs);
	}
	
	@Override
    @Transactional(value = "shopTransactionManagerWrite", isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void updatePayState(Integer id,String selectState,OrderOperateLogs orderOperateLogs,String spanState) {
		if(spanState.equals("100") && selectState.equals("101")) {
			ordersDao.updateSmConfirmStatusByIdState(id,1);
		}
		Integer selectStateInteger = Integer.valueOf(selectState);
		ordersDao.updatePayState(id,selectStateInteger);
		orderOperateLogsWriteDao.insert(orderOperateLogs);
	}
	
	@Override
    @Transactional(value = "shopTransactionManagerWrite", isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void updateNotes(Integer id,String textNotes,OrderOperateLogs orderOperateLogs) {
		ordersDao.updateNotes(id,textNotes);
		orderOperateLogsWriteDao.insert(orderOperateLogs);
	}
	
	@Override
    @Transactional(value = "shopTransactionManagerWrite", isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void updateInvoiceAddress(Integer id,Orders orders,OrderOperateLogs orderOperateLogs) {
		ordersDao.updateInvoiceAddress(orders);
		orderOperateLogsWriteDao.insert(orderOperateLogs);
	}
	
	@Override
    @Transactional(value = "shopTransactionManagerWrite", isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void updateInvoiceState(Integer id,Integer isLock,OrderOperateLogs orderOperateLogs) {
		ordersDao.updateInvoiceState(id,isLock);
		orderOperateLogsWriteDao.insert(orderOperateLogs);
	}
	
	@Override
    @Transactional(value = "shopTransactionManagerWrite", isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void updateInvoiceInfo(Integer id,MemberInvoices memberInvoices,OrderOperateLogs orderOperateLogs,List<InvoicesWwwLogs> invoicesWwwLogsList,List<OrderProducts> orderProductsList) {
		if(invoicesWwwLogsList!=null && invoicesWwwLogsList.size()>0) {
			for (InvoicesWwwLogs invoicesWwwLogs : invoicesWwwLogsList) {
				 invoicesWwwLogsWriteDaoDao.updateInvoiceWwwLogs(invoicesWwwLogs);
			}
		}
		String invoiceTitle = memberInvoices.getInvoiceTitle();
		String taxPayerNumber = memberInvoices.getTaxPayerNumber();
		Integer invoiceType = memberInvoices.getInvoiceType();
		String bankName = memberInvoices.getBankName();
		String bankCardNumber = memberInvoices.getBankCardNumber();
		String registerAddress = memberInvoices.getRegisterAddress();
		String registerPhone = memberInvoices.getRegisterPhone();
		if(!StringUtil.isEmpty(invoiceTitle) && !StringUtil.isEmpty(taxPayerNumber) && invoiceType!=null && !StringUtil.isEmpty(bankName) && !StringUtil.isEmpty(bankCardNumber) &&
				!StringUtil.isEmpty(registerAddress) && !StringUtil.isEmpty(registerPhone)) {
			MemberInvoices memberInvoiceByInvoiceTitle = memberInvoicesReadDao.getMemberInvoiceByInvoiceTitle(memberInvoices.getInvoiceTitle());
			if(memberInvoiceByInvoiceTitle!=null) {
				if(taxPayerNumber.equals(memberInvoiceByInvoiceTitle.getTaxPayerNumber()) &&
						invoiceType==memberInvoiceByInvoiceTitle.getInvoiceType() && bankName.equals(memberInvoiceByInvoiceTitle.getBankName()) &&
						bankCardNumber.equals(memberInvoiceByInvoiceTitle.getBankCardNumber()) && registerAddress.equals(memberInvoiceByInvoiceTitle.getRegisterAddress()) &&
						registerPhone.equals(memberInvoiceByInvoiceTitle.getRegisterPhone())) {
					if(invoiceType==1) {
						memberInvoices.setState(1);
						if(StringUtil.isEmpty(memberInvoices.getRemark())) {
							memberInvoices.setRemark("该增票信息先前已存在并审核通过,本订单增票信息自动审核通过");
						}
					}
				}
			}
		}
		if(invoiceType!=null) {
			if(invoiceType==1) {
				if (orderProductsList != null && orderProductsList.size() > 0) {
	                for (OrderProducts eachOrderProducts : orderProductsList) {
	                    eachOrderProducts.setMakeReceiptType(2);
	                    orderProductsWriteDao.updateMakeReceiptType(eachOrderProducts);
	                }
	            }
			}
		}
		memberInvoices.setOrderId(id);
		ordersDao.updateInvoiceInfo(memberInvoices);
		orderOperateLogsWriteDao.insert(orderOperateLogs);
	}
	
	@Override
    @Transactional(value = "shopTransactionManagerWrite", isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public void updateAddress(Integer id,Orders orders,OrderOperateLogs orderOperateLogs) {
		orders.setId(id);
		ordersDao.updateAddress(orders);
		orderOperateLogsWriteDao.insert(orderOperateLogs);
	}

	@Override
	public int updateNotAutoConfirm(Integer id, Integer whereValue, Integer setValue) {
		return ordersDao.updateNotAutoConfirm(id, whereValue, setValue);
	}

	@Override
	public void insertAddressAndMarkBuildingLog(OrderOperateLogs orderOperateLogs) {
		orderOperateLogsWriteDao.insert(orderOperateLogs);
	}

	@Override
	public ServiceResult<Map<String, Object>> doBatchConfirmationPayment(String cOrderSns,
			Map<String, Object> modelMap,String userName) {
		ServiceResult<Map<String, Object>> result = new ServiceResult<Map<String, Object>>();
		try {
			String[] strTemp = cOrderSns.split("\r\n");
			Set<String> set = new HashSet<String>(Arrays.asList(strTemp));
			String[] cOrderSnsArray = set.toArray(new String[0]);

			StringBuffer sbUF = new StringBuffer();
			StringBuffer sbError = new StringBuffer();
			StringBuffer sbHidden = new StringBuffer();
			StringBuffer sb = new StringBuffer();
			StringBuffer sbSuccess = new StringBuffer();
			List<Orders> ordersList = null;
			Integer total = 0;
			Integer unFindCount = 0;
			Integer errorCount = 0;
			for (String cOrderSn : cOrderSnsArray) {
				Orders orders = ordersDao.getOrderNo(cOrderSn.trim());
				if (orders == null) {
					unFindCount += 1;
					sbUF.append("对不起，订单").append(cOrderSn).append("未查询到Orders 订单表信息！").append("<br>");
					continue;
				}
				ordersList = new ArrayList<Orders>();
				ordersList.add(orders);

				if (orders.getPaymentStatus() != null) {
					if (orders.getPaymentStatus() != 100) {
						unFindCount += 1;
						sbUF.append("对不起，订单").append(cOrderSn).append("已经付款！").append("<br>");
						continue;
					}
				}
				if(orders.getOrderStatus()!=null) {
					if (orders.getOrderStatus() == 202) {
						unFindCount += 1;
						sbUF.append("对不起，订单").append(cOrderSn).append("已经取消！").append("<br>");
						continue;
					}
				}
				if (orders.getIsCod() != null) {
					if (orders.getIsCod() == 1) {
						unFindCount += 1;
						sbUF.append("对不起，订单").append(cOrderSn).append("是货到付款订单不能确认收款！").append("<br>");
						continue;
					}
				}
				List<OrderProducts> orderProductsByOrderId = orderProductsReadDao.getOrderProductsByOrderId(orders.getId());
				Boolean orderProductsCon = true;
				for (OrderProducts orderProducts : orderProductsByOrderId) {
					if(orderProducts.getStatus()!=null) {
						if(orderProducts.getStatus()==110) {
							unFindCount += 1;
							sbUF.append("对不起，订单").append(cOrderSn).append("中存在取消关闭的网单，请与业务核实订单执行后再确认收款！").append("<br>");
							orderProductsCon = false;
							break;
						}
					}
				}
				if(orderProductsCon==false) {
					continue;
				}
				try {
					ordersDao.updatePayState(orders.getId(),101);
					ordersDao.updateSmConfirmStatusByIdState(orders.getId(),1);
					orderProductsNewDao.updatePaymentStatusByOrderId(orders.getId(),201);
					List<OrderProducts> orderProductsByOrderIds = orderProductsReadDao.getOrderProductsByOrderId(orders.getId());
					for (OrderProducts orderProducts : orderProductsByOrderIds) {
						OrderOperateLogs orderOperateLogs = new OrderOperateLogs();
						orderOperateLogs.setSiteId(0);
						orderOperateLogs.setOrderId(orders.getId());
						orderOperateLogs.setOrderProductId(orderProducts.getId());
						orderOperateLogs.setNetPointId(orderProducts.getNetPointId());
						orderOperateLogs.setOperator(userName);
						orderOperateLogs.setChangeLog("订单未付款手动变成已付款");
						orderOperateLogs.setRemark("批量手动确认收款");
						long time = new Date().getTime();
						Integer date = (int)time;
						orderOperateLogs.setLogTime(date);
						orderOperateLogs.setStatus(orderProducts.getStatus());
						orderOperateLogs.setPaymentStatus(101);
						orderOperateLogsWriteDao.insert(orderOperateLogs);
					}
					
					 total += 1;
                     sbSuccess.append("订单号").append(cOrderSn).append("确认收款成功,").append("订单金额为:").append(orders.getProductAmount())
                             .append("<br>");
				} catch (Exception e) {
					errorCount += 1;
                    sbError.append(errorCount).append("订单号").append(cOrderSn).append("确认收款异常")
                            .append("<br>");
                    continue;
				}
				
			}
			sb.append("总计").append(cOrderSnsArray.length).append("条数据！<br>");
            if (total > 0) {
                sb.append(total+"条确认收款成功！信息如下：<br>").append(sbSuccess);
            }
            if (unFindCount > 0) {
                sb.append(unFindCount).append("条确认收款失败！信息如下：<br>").append(sbUF);
            }
            if (errorCount > 0) {
                sb.append(errorCount).append("条确认收款异常！信息如下：<br>").append(sbError);
            }
            result.setMessage(sb.toString());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("error", sbHidden.toString());
            result.setResult(map);
		} catch (Exception e) {
			e.printStackTrace();
            logger.error("[order][doBatchConfirmationPayment]批量确认收款时发生未知错误", e);
            result.setMessage("批量确认收款失败！");
		}
		return result;
	}

	@Override
	public int updateMemberInvoicesId(int memberInvoicesId, int orderId) {
		return ordersDao.updateMemberInvoicesId(memberInvoicesId,orderId);
	}

}
