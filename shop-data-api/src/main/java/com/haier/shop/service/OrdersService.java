package com.haier.shop.service;

import java.util.List;
import java.util.Map;

import com.haier.common.ServiceResult;
import com.haier.shop.model.InvoicesWwwLogs;
import com.haier.shop.model.MemberInvoices;
import com.haier.shop.model.OrderOperateLogs;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.Orders;

public interface OrdersService {
	
	
	 List<Orders> getCodNotConfirmOrders();
	 int updateCodConfirmState(Integer id);
	 int insertOrders (Orders orders);
	 Orders get(int id);
	 List<Orders> getOrderList(String sourceOrderNumber);
	 void updateSourceOrderNumber(String sourceOrderNumber,String orderNumber,OrderOperateLogs orderOperateLogs);
	 void updatePayState(Integer id,String selectState,OrderOperateLogs orderOperateLogs,String spanState);
	 void updateNotes(Integer id,String textNotes,OrderOperateLogs orderOperateLogs);
	 void updateInvoiceAddress(Integer id,Orders orders,OrderOperateLogs orderOperateLogs);
	 void updateInvoiceState(Integer id,Integer isLock,OrderOperateLogs orderOperateLogs);
	 void updateInvoiceInfo(Integer id,MemberInvoices memberInvoices,OrderOperateLogs orderOperateLogs,List<InvoicesWwwLogs> invoicesWwwLogsList,List<OrderProducts> orderProductsList);
	 void updateAddress(Integer id,Orders orders,OrderOperateLogs orderOperateLogs);

	 int updateNotAutoConfirm(Integer id, Integer whereValue, Integer setValue);
	 void insertAddressAndMarkBuildingLog(OrderOperateLogs orderOperateLogs);
	 ServiceResult<Map<String, Object>> doBatchConfirmationPayment(String cOrderSns, Map<String, Object> modelMap,String userName);

    int updateMemberInvoicesId(int memberInvoicesId, int orderId);
}
