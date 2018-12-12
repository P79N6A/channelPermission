package com.haier.svc.services;

import com.haier.purchase.data.model.CrmOrderManualDetailItem;
import com.haier.purchase.data.model.T2OrderItem;
import com.haier.purchase.data.service.PurchaseCrmOrderManualService;
import com.haier.purchase.data.service.PurchaseT2OrderService;
import com.haier.svc.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.PrivilegeItem;
import com.haier.svc.service.PurchaseCommonService;

import java.util.*;

@Service
public class PurchaseCommonServiceImpl implements PurchaseCommonService {
	private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
			.getLogger(PurchaseCommonServiceImpl.class);

	@Autowired
	private com.haier.purchase.data.service.PurchaseCommonService purchaseCommonService;

	@Autowired
    PurchaseT2OrderService purchaseT2OrderService;

	@Autowired
    PurchaseCrmOrderManualService purchaseCrmOrderManualService;

	@Autowired
    SequenceService sequenceService;

	/**
	 * 登录者权限情报取得
	 * 
	 * @param userId
	 *            ----登录者ID
	 * @return
	 */
	@Override
	public ServiceResult<PrivilegeItem> getPrivilege(String userId) {
		ServiceResult<PrivilegeItem> result = new ServiceResult<PrivilegeItem>();
		try {
			result.setResult(purchaseCommonService.findPrivilege(userId));
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("获取登录者权限情报失败：", e);
		}
		return result;
	}

	/**
	 * 获得订单流水号
	 * 
	 * @return
	 * @see com.haier.cbs.purchase.service.PurchaseCommonService#getNextVal()
	 */
	@Override
	public ServiceResult<Integer> getNextVal() {
		ServiceResult<Integer> result = new ServiceResult<Integer>();
		try {
			result.setResult(purchaseCommonService.getNextVal());
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			log.error("获取订单流水号：", e);
		}
		return result;
	}

	/**
	 * 取得CRM拆分子单号
	 * @param order_id 总单单号
	 * @return 拆分子单单号
	 */
	public String getCrmSubOrderId(String order_id, int num) {
//		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
//		definition.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
//		TransactionStatus status = transactionManager.getTransaction(definition);

		String new_order_id = null;
		try {
			if (num <= 0) {
				log.error("开单数量不能小于等于0,单号：" + order_id);
			} else {
				Map params = new HashMap();
				List<String> order_ids = new ArrayList<String>();
				order_ids.add(order_id);
				params.put("order_idList", order_ids);
				List<T2OrderItem> l = purchaseT2OrderService.findT2Orders(params);
				//判断是否需要拆单
				if (l.size() == 0) {
					Map order_map = new HashMap();
					order_map.put("wp_order_id", order_id);
					List<CrmOrderManualDetailItem> l_crm = purchaseCrmOrderManualService
							.findCrmOrderManuals(order_map);
					if (l_crm.size() == 0) {
						log.error("未找到对应WP主单号：" + order_id);
					} else {
						new_order_id = order_id;
					}
				} else {
					if (l.get(0).getT2_delivery_prediction().intValue() == num) {
						new_order_id = order_id;
					} else {
						Map param_seq = new HashMap();
						int sub_order_seq = 1;
						param_seq.put("order_id", order_id);
                        sequenceService.insertOrUpdateSequence(param_seq);
						Random r = new Random(System.currentTimeMillis());
						Thread.sleep(r.nextInt(3));

						Integer id = sequenceService.selectSequence(param_seq);
						if (id == null) {
							throw new Exception("无法获得子单序号");
						} else {
							param_seq.put("seq", id);
                            sequenceService.updateSequence(param_seq);
						}
						sub_order_seq = id;
						if (sub_order_seq == 0) {//如果在haier_in_rrs_t表中没有查到数据，则说明是旧单，不进行拆单
							new_order_id = order_id;
						} else {
							new_order_id = order_id + "-" + sub_order_seq;
						}
					}
				}
			}
//			transactionManager.commit(status);
			return new_order_id;
		} catch (Exception ex) {
			log.error("生成子单失败:" + order_id + "," + num, ex);
			ex.printStackTrace();
//			transactionManager.rollback(status);
			return null;
		}
	}
}
