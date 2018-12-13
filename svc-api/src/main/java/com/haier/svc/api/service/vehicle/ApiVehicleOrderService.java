package com.haier.svc.api.service.vehicle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.DataDictionary;
import com.haier.purchase.data.model.vehcile.AreaCenterInfoDTO;
import com.haier.purchase.data.model.vehcile.Cn3wPurchaseStock;
import com.haier.purchase.data.model.vehcile.DepartmentInfoDTO;
import com.haier.purchase.data.model.vehcile.Entry3wOrder;
import com.haier.purchase.data.model.vehcile.ExportVehicleDTO;
import com.haier.purchase.data.model.vehcile.MaterielInfoDTO;
import com.haier.purchase.data.model.vehcile.PurchaseProductPaymentDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDetailsDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderHistoryDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderZqDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderZqDetailsDTO;
import com.haier.purchase.data.model.vehcile.VehicleProductPaymentDTO;
import com.haier.purchase.data.model.vehcile.VehiclePushToSAP;
import com.haier.purchase.data.service.vechile.PurchaseVehicleOrderDetailService;
import com.haier.svc.api.controller.util.WebUtil;
import com.haier.svc.api.controller.util.date.DateUtil;
import com.haier.system.model.PlanInDate;
import com.haier.system.model.PlanInDateEnum;
import com.haier.system.service.SystemCenterService;
import com.haier.vehicle.model.InsertOrderToOMSItem;
import com.haier.vehicle.service.AreaCenterInfoService;
import com.haier.vehicle.service.DepartmentInfoService;
import com.haier.vehicle.service.MaterielInfoService;
import com.haier.vehicle.service.VehicleAppService;
import com.haier.vehicle.service.VehicleDataDictionaryService;
import com.haier.vehicle.service.VehicleGetPriceFromCRMService;
import com.haier.vehicle.service.VehicleInsertOrderToOMSService;
import com.haier.vehicle.service.VehicleJobService;
import com.haier.vehicle.service.VehicleOrderDetailService;
import com.haier.vehicle.service.VehicleOrderService;
import com.haier.vehicle.service.VehicleOrderZqDetailsService;
import com.haier.vehicle.service.VehicleOrderZqService;
import com.haier.vehicle.service.VehicleProductPaymentService;

@Service
public class ApiVehicleOrderService {
	@Autowired
	private VehicleOrderService vehicleOrderDao;
	@Autowired
	private VehicleOrderDetailService vehicleOrderDetailDao;
	@Autowired
	private VehicleProductPaymentService vehicleProductPaymentDao;
	@Autowired
	private VehicleAppService vehicleAppService;
	@Autowired
	private MaterielInfoService materielInfoDao;
	@Autowired
	private AreaCenterInfoService areaCenterInfoDao;
	@Autowired
	private DepartmentInfoService departmentInfoDao;
	@Autowired
	private VehicleGetPriceFromCRMService vehicleGetPriceFromCRMService;
	@Autowired
	private VehicleInsertOrderToOMSService vehicleInsertOrderToOMSService;
	@Autowired
	private VehicleOrderZqService vehicleOrderZqDao;
	@Autowired
	private VehicleOrderZqDetailsService vehicleOrderZqDetailsDao;
	@Autowired
	private VehicleDataDictionaryService dataDictionaryDao;
	@Autowired
	private SystemCenterService systemCenterService;
	@Autowired
	private VehicleJobService vehicleJobService;
	@Autowired
	private PurchaseVehicleOrderDetailService purchaseVehicleOrderDetailService;

	private Map<String, String> sendMap;

	private String getSoldToCode() {
		return "8800017840";
	}

	private String getSoldToName() {
		return "海尔集团电子商务有限公司";
	}

	private String getSendMapValue(String key) {
		if (MapUtils.isEmpty(sendMap)) {
			getSendTo();
		}
		return sendMap.get(key);
	}

	public String getUserId() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		int userId = WebUtil.currentUserId(request);
		return String.valueOf(userId);
	}

	private String getUserName() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String userName = WebUtil.currentUserName(request);
		return userName;
	}

	/**
	 * 查询单条.
	 *
	 * @param id
	 * @return ExecuteResult<VehicleOrderDTO>
	 */
	public ServiceResult<VehicleOrderDTO> getOneById(Long id) {
		ServiceResult<VehicleOrderDTO> result = new ServiceResult<VehicleOrderDTO>();
		VehicleOrderDTO dto = vehicleOrderDao.getOneById(id);
		if (dto == null) {
			result.setSuccess(false);
			return result;
		}
		result.setResult(dto);
		return result;
	}

	/**
	 * 根据条件查询单条.
	 *
	 * @param condition
	 * @return List<VehicleOrderDTO>
	 */
	public JSONObject historyOrder(VehicleOrderDTO condition, PagerInfo pager) {
		List<VehicleOrderDTO> list = vehicleOrderDao.getPageByCondition(condition, pager.getStart(),
				pager.getPageSize());
		// 遍历查询ZQ子表的remark,用于在页面中判断是否有取消按钮
		for (VehicleOrderDTO dto : list) {
			boolean flag = DateUtil.getCancelOrder(dto.getOrderTime());
			if (flag) {
				// 可取消
				dto.setRemark("1");
			} else {
				// 不可取消
				dto.setRemark("0");
			}
		}
		long count = vehicleOrderDao.getPagerCount(condition);
		return jsonResult(list, count);
	}

	/**
	 * 根据条件查询单条.
	 *
	 * @param condition
	 * @return List<VehicleOrderDTO>
	 */
	public JSONObject getPagerByCondition(VehicleOrderDTO condition, PagerInfo pager) {
		List<VehicleOrderDTO> list = vehicleOrderDao.getPageByCondition(condition, pager.getStart(),
				pager.getPageSize());
		long count = vehicleOrderDao.getPagerCount(condition);
		return jsonResult(list, count);
	}

	/**
	 * productGroup
	 *
	 * @return
	 */
	public JSONObject productGroup() {
		List<PurchaseProductPaymentDTO> list = vehicleProductPaymentDao.getList();
		JSONObject temp = new JSONObject();
		JSONObject res = new JSONObject();
		JSONArray array = new JSONArray();
		for (PurchaseProductPaymentDTO o : list) {
			JSONObject json = new JSONObject();
			JSONObject inner = new JSONObject();
			inner.put("paymentCode", o.getPaymentCode());
			inner.put("paymentName", o.getPaymentName());
			json.put("text", o.getProductName());
			json.put("value", o.getProductCode());
			temp.put(o.getProductCode(), inner);
			array.add(json);
		}
		res.put("array", array);
		res.put("obj", temp);
		return res;
	}

	/**
	 * 获取送达方
	 *
	 * @return
	 */

	public JSONArray getSendTo() {
		ServiceResult<String> res = vehicleAppService.getSendTo();
		JSONArray result = new JSONArray();
		if (res.getSuccess()) {
			JSONArray array = JSONArray.parseArray(res.getResult());
			if (MapUtils.isEmpty(sendMap)) {
				sendMap = new HashMap<String, String>();
				for (Object o : array) {
					Map<String, String> map = (Map<String, String>) o;
					sendMap.put(map.get("TMCS2S_SENDTO"), map.get("TMCSE_YJMFID"));
				}
			}
			for (Object o : array) {
				JSONObject temp = (JSONObject) o;
				JSONObject json = new JSONObject();
				json.put("text", temp.get("TMCS2S_SENDTO") + ":" + temp.get("TMCSE_NAME"));
				json.put("value", temp.get("TMCS2S_SENDTO"));
				result.add(json);
			}
		}
		return result;
	}

	/**
	 * 获取基地
	 *
	 * @return
	 */
	public JSONArray baseCode() {
		ServiceResult<String> res = vehicleAppService.getBaseCode();
		JSONArray result = new JSONArray();
		if (res.getSuccess()) {
			result = toComboboxJson(res.getResult(), "ICC_JDNAME", "ICC_JDCODE");
		}
		return result;
	}

	private JSONArray toComboboxJson(String str, String text, String value) {
		JSONArray array = JSONObject.parseArray(str);
		return toComboboxJson(array, text, value);
	}

	private JSONArray toComboboxJson(JSONArray array, String text, String value) {
		JSONArray res = new JSONArray();
		for (Object o : array) {
			JSONObject temp = (JSONObject) o;
			JSONObject json = new JSONObject();
			json.put("text", temp.get(text));
			json.put("value", temp.get(value));
			res.add(json);
		}
		return res;
	}

	/**
	 * 获取车型
	 *
	 * @param base
	 * @param sendTo
	 * @return
	 */

	public JSONObject carCode(String base, String sendTo) {
		ServiceResult<String> result = vehicleAppService.getCarCode(base, sendTo);
		JSONObject temp = new JSONObject();
		if (result.getSuccess()) {
			temp.put("array", toComboboxJson(result.getResult(), "LIBTITLE", "LIBKEY"));
			temp.put("data", result.getResult());
			temp.put("success", true);
		}
		return temp;
	}

	public JSONObject center(AreaCenterInfoDTO condition) {
		AreaCenterInfoDTO dto = areaCenterInfoDao.getOneByCondition(condition);
		JSONObject obj = new JSONObject();
		obj.put("data", dto);
		return obj;
	}

	public JSONObject itemCheck(String materielCode, String deliveryCode, String baseCode, int count) {
		ServiceResult<String> resStr = vehicleAppService.getproList(this.getSoldToCode(), deliveryCode, materielCode,
				baseCode);
		JSONObject result = new JSONObject();
		boolean res = true;
		if (resStr.getSuccess()) {
			JSONObject obj = JSONObject.parseObject(resStr.getResult());
			int max = Integer.valueOf(obj.get("MAXQTY").toString());
			int min = Integer.valueOf(obj.get("MINQTY").toString());
			if (count < min) {
				res = false;
				result.put("msg", "超过最小装车量，请重新选择数量");
			}
			if (count > max) {
				res = false;
				result.put("msg", "超过最大装车量，请重新选择数量");
			}
		} else {
			res = false;
			result.put("msg", resStr.getMessage());
		}
		result.put("success", res);
		return result;
	}

	public JSONArray orderStatus() {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("valueSetId", "order_status");
		List<DataDictionary> list = dataDictionaryDao.getByValueSetId(params);
		JSONArray array = new JSONArray();
		for (DataDictionary e : list) {
			if (!"00".equals(e.getValue()) && !"01".equals(e.getValue())) {
				JSONObject obj = new JSONObject();
				obj.put("text", e.getValue_meaning());
				obj.put("value", e.getValue());
				array.add(obj);
			}
		}

		return array;
	}

	public JSONObject tempSave(VehicleOrderDTO entity, String materielCodes) {
		JSONObject json = new JSONObject();
		json.put("success", true);
		try {
			AreaCenterInfoDTO condition = new AreaCenterInfoDTO();
			condition.setDeliveryToCode(entity.getDeliveryCode());
			condition = areaCenterInfoDao.getOneByCondition(condition);
			if (condition != null) {
				entity.setDistributionCentre(condition.getRrsCenterCode());
				// 暂存订单信息
				entity.setStatus("01");
				saveOrder(entity);
				// 暂存订单明细
				saveOrderDetail(entity, materielCodes);
			} else {
				json.put("success", false);
				json.put("msg", "未获取到配送中心编码");
			}
		} catch (BusinessException e) {
			json.put("success", false);
			json.put("msg", e.getMessage());
		}
		return json;
	}

	public JSONObject tempLoad(String orderId) {
		VehicleOrderDTO orderCon = new VehicleOrderDTO();
		orderCon.setOrderId(Long.valueOf(orderId));
		VehicleOrderDTO order = vehicleOrderDao.getOneByCondition(orderCon);
		VehicleOrderDetailsDTO detailCon = new VehicleOrderDetailsDTO();
		detailCon.setOrderNo(order.getOrderNo());
		List<VehicleOrderDetailsDTO> details = vehicleOrderDetailDao.getListByCondition(detailCon);
		JSONObject json = new JSONObject();
		json.put("details", details);
		json.put("order", order);
		return json;
	}

	public JSONObject normalSave(VehicleOrderDTO entity, String materielCodes) {
		JSONObject json = new JSONObject();
		entity.setStatus("00");
		// 暂存订单信息
		try {
			AreaCenterInfoDTO condition = new AreaCenterInfoDTO();
			condition.setDeliveryToCode(entity.getDeliveryCode());
			condition = areaCenterInfoDao.getOneByCondition(condition);
			if (condition != null) {
				entity.setDistributionCentre(condition.getRrsCenterCode());
			} else {
				json.put("success", false);
				json.put("msg", "未获取到配送中心编码");
			}
			saveOrder(entity);
		} catch (BusinessException e) {
			JSONObject data = new JSONObject();
			data.put("success", false);
			data.put("msg", e.getMessage());
			return data;
		}
		if (null != entity.getOrderId()) {
			entity = vehicleOrderDao.getOneById(entity.getOrderId());
		}
		// 暂存订单明细
		saveOrderDetail(entity, materielCodes);
		return carCheck(entity, materielCodes);
	}

	public JSONObject carCheck(VehicleOrderDTO entity, String materielCodes) {
		// 调用装车检查接口
		Map<String, String> map = new HashMap<String, String>();
		map.put("carCode", entity.getCarType());
		map.put("orderNo", entity.getOrderNo());
		map.put("custCode", entity.getDeliveryCode());
		map.put("items", materielCodes);
		VehicleOrderDTO condition = new VehicleOrderDTO();
		condition.setOrderNo(entity.getOrderNo());
		VehicleOrderDTO dto = vehicleOrderDao.getOneByCondition(condition);
		ServiceResult<String> res = vehicleAppService.checkCar(map);
		JSONObject data = new JSONObject();
		List<String> list = JSONArray.parseArray(res.getResult(), String.class);
		if (res.getSuccess()) {
			// List<String> list = JSONArray.parseArray(res.getResult(),
			// String.class);
			data.put("orderId", dto.getOrderId().toString());
			data.put("imgUrl", list.get(0));
			data.put("success", true);
			data.put("orderNo", entity.getOrderNo());
		} else {
			data.put("success", false);
			data.put("imgUrl", list);
			data.put("msg", res.getMessage());
		}
		return data;
	}

	private void saveOrder(VehicleOrderDTO entity) throws BusinessException {
		PlanInDate planInDate = systemCenterService.getPlanInDateByTypeId(PlanInDateEnum.ZCDH.getCode());
		if (planInDate == null || planInDate.getValue() == null || "".equals(planInDate.getValue())) {
			throw new BusinessException("整车直发到货周未设置,请设置到货周!");
		}
		if (null == entity.getOrderId()) {
			String orderNo = vehicleOrderDao.getVehicleOrderNo("ZKTM");
			DepartmentInfoDTO dep = departmentInfoDao.getOneByDeliveryToCode(entity.getDeliveryCode());
			AreaCenterInfoDTO area = areaCenterInfoDao.getOneByDeliveryToCode(entity.getDeliveryCode());
			entity.setOrderNo(orderNo);
			entity.setCorpCode(dep.getOrganizationCode());
			entity.setCorpName(dep.getOrganizationName());
			entity.setAreaCode(area.getAreaCode());
			entity.setAreaName(area.getAreaName());
			// entity.setStatus("00");
			entity.setType("ZGOZ");
			entity.setSoldToCode(getSoldToCode());
			entity.setSoldToName(getSoldToName());
			entity.setDateOfArrival(DateUtil.getArrivalDate(Integer.parseInt(planInDate.getValue())));
			entity.setOrderTime(new Date());
			entity.setCreateBy(getUserId());
			entity.setCreateTime(new Date());
			entity.setLastUpdateTime(new Date());
			entity.setLastUpdateBy(getUserId());
			vehicleOrderDao.insertSelective(entity);
		} else {
			entity.setDateOfArrival(DateUtil.getArrivalDate(Integer.parseInt(planInDate.getValue())));
			DepartmentInfoDTO dep = departmentInfoDao.getOneByDeliveryToCode(entity.getDeliveryCode());
			AreaCenterInfoDTO area = areaCenterInfoDao.getOneByDeliveryToCode(entity.getDeliveryCode());
			entity.setCorpCode(dep.getOrganizationCode());
			entity.setCorpName(dep.getOrganizationName());
			entity.setAreaCode(area.getAreaCode());
			entity.setAreaName(area.getAreaName());
			vehicleOrderDao.updateSelectiveById(entity);
		}
	}

	private void saveOrderDetail(VehicleOrderDTO entity, String materielCodes) {
		JSONArray arr = JSONObject.parseArray(materielCodes);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (Object e : arr) {
			JSONObject obj = (JSONObject) e;
			map.put(String.valueOf(obj.get("materielId")), Integer.valueOf((String) obj.get("quantity")));
		}
		List<MaterielInfoDTO> list = materielInfoDao.selectByKeys(new ArrayList<String>(map.keySet()));
		if (entity.getOrderId() != null) {
			vehicleOrderDetailDao.deleteByOrderId(entity.getOrderId());
			entity = vehicleOrderDao.getOneById(entity.getOrderId());
		}

		int row = 1;
		VehicleProductPaymentDTO paymentDTO = new VehicleProductPaymentDTO();
		if (list != null && !list.isEmpty()) {
			// 获取金额
			setItemPrice(entity.getDeliveryCode(), list);
			double loadingVolume = 0.0;
			for (MaterielInfoDTO e : list) {
				VehicleOrderDetailsDTO dto = new VehicleOrderDetailsDTO();
				paymentDTO.setProductGroup(e.getProductGroupCode());
				e.setCount(map.get(e.getMaterielId().toString()));
				PurchaseProductPaymentDTO tempDto = vehicleProductPaymentDao
						.getPurchasePaymentOneByCondition(paymentDTO);
				dto.setOrderNo(entity.getOrderNo());
				dto.setAmount(e.getSumPrice());
				dto.setMaterielId(e.getMaterielId().toString());
				dto.setBateRate(e.getBateRate());// 直采扣点
				dto.setStockPrice(e.getStockPrice()); // 采购价
				dto.setRetailPrice(e.getRetailPrice()); // 供价,零售价
				dto.setActPrice(e.getActPrice()); // 执行价
				dto.setBateMoney(e.getBateMoney()); // 单台台返金额
				dto.setLossRate(e.getLossRate()); // 折扣扣率
				dto.setIsfl(e.getIsfl()); // 返利类型
				dto.setIskpo(e.getIskpo()); // 商用空调标志
				dto.setQty(e.getCount());
				dto.setRows(row++);
				dto.setUnitPrice(e.getPrice());
				dto.setProductGroup(e.getProductGroupCode());
				dto.setProductGroupName(e.getProductGroupName());
				dto.setMaterielCode(e.getMaterielCode());
				dto.setMaterielName(e.getMaterielName());
				dto.setVolume(e.getVolume());
				dto.setTotalVolume(e.getTotalVolume());
				dto.setBrand(e.getBrandCode());
				dto.setStatus(entity.getStatus());
				if (tempDto != null) {
					dto.setPaymentCode(tempDto.getPaymentCode());
					dto.setPaymentName(tempDto.getPaymentName());
				}
				dto.setCreateBy(getUserId());
				dto.setLastUpdateBy(getUserId());
				dto.setCreateTime(new Date());
				dto.setLastUpdateTime(new Date());
				loadingVolume += e.getTotalVolume();
				vehicleOrderDetailDao.insertSelective(dto);
			}
			entity.setRows(list.size());
			DecimalFormat df = new DecimalFormat("#.00");
			entity.setLoadingVolume(Double.parseDouble(df.format(loadingVolume)));
			vehicleOrderDao.updateSelectiveByOrderNo(entity);
		}
	}

	public JSONObject updateStatus(String orderNo, String status) {
		JSONObject res = new JSONObject();
		VehicleOrderDTO order = new VehicleOrderDTO();
		order.setOrderNo(orderNo);
		order = vehicleOrderDao.getOneByCondition(order);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if ("18".equals(status)) {
			boolean flag = DateUtil.getCancelOrder(order.getCreateTime());
			if (!flag) {
				res.put("success", false);
				res.put("msg", "当前时间不可取消订单！");
			}
			resultMap = vehicleOrderDao.cancelOrder(order);
		}
		order.setStatus(status);
		/** 撤销begin */
		// ZK主表
		int c = vehicleOrderDao.updateSelectiveByOrderNo(order);

		if (c != 1) {
			res.put("success", false);
			res.put("msg", "数据处理失败！");
		} else {
			// ZK子表
			VehicleOrderDetailsDTO orderDetail = new VehicleOrderDetailsDTO();
			orderDetail.setOrderNo(order.getOrderNo());
			orderDetail.setStatus(order.getStatus());
			vehicleOrderDetailDao.updateByOrderNo(orderDetail);
			res.put("success", true);
			res.put("msg", "操作成功");
		}
		if ("18".equals(status)) {
			/** 撤销end */
			if ("S".equals(resultMap.get("code"))) {
				/** 取消需要在撤销的基础上再对ZQ、ZQ子表进行处理 */
				VehicleOrderZqDTO entity = new VehicleOrderZqDTO();
				entity.setOrderNo(orderNo);
				VehicleOrderZqDTO zqOrder = vehicleOrderZqDao.getOneByCondition(entity);
				if (zqOrder != null) {
					zqOrder.setStatus(status);
					int a = vehicleOrderZqDao.updateSelectiveById(zqOrder);
					if (a != 1) {
						res.put("success", false);
						res.put("msg", "数据处理失败！");
					} else {
						VehicleOrderZqDetailsDTO zqOrderDetail = new VehicleOrderZqDetailsDTO();
						zqOrderDetail.setZqOrderNo(zqOrder.getZqOrderNo());
						zqOrderDetail.setStatus(status);
						vehicleOrderZqDetailsDao.updateSelectiveByZqOrderNo(zqOrderDetail);
						res.put("success", true);
						res.put("msg", resultMap.get("message"));
					}
				}
			} else {
				res.put("success", false);
				res.put("msg", resultMap.get("message"));
			}
		}
		return res;
	}

	public JSONObject checkOrder(String orderNo) {
		VehicleOrderDTO condition = new VehicleOrderDTO();
		VehicleOrderDetailsDTO detailsCondition = new VehicleOrderDetailsDTO();
		JSONObject json = new JSONObject();
		condition.setOrderNo(orderNo);
		detailsCondition.setOrderNo(orderNo);
		VehicleOrderDTO orderDTO = vehicleOrderDao.getOneByCondition(condition);
		List<VehicleOrderDetailsDTO> details = vehicleOrderDetailDao.getListByCondition(detailsCondition);
		List<VehicleOrderHistoryDTO> histotryList = new ArrayList<VehicleOrderHistoryDTO>();
		json.put("order", orderDTO);
		if (details != null && details.size() > 0) {
			for (VehicleOrderDetailsDTO detail : details) {
				VehicleOrderHistoryDTO historyDto = new VehicleOrderHistoryDTO();
				BeanUtils.copyProperties(detail, historyDto);
				VehicleOrderZqDetailsDTO entity = new VehicleOrderZqDetailsDTO();
				entity.setZqItemNo(detail.getItemNo().replaceAll("ZK", "ZQ"));
				List<VehicleOrderZqDetailsDTO> zqDetailsList = vehicleOrderZqDetailsDao.listByCondition(entity);
				if (zqDetailsList != null && zqDetailsList.size() > 0) {
					historyDto.setZqItemNo(zqDetailsList.get(0).getZqItemNo());
					historyDto.setZqStatus(zqDetailsList.get(0).getStatus());
					historyDto.setRemark(zqDetailsList.get(0).getRemark());
					historyDto.setVbelnDn5(zqDetailsList.get(0).getVbelnDn5());
				}
				if (!"".equals(detail.getVbelnDn1())) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("cnStockSyncsId", detail.getItemNo());
					List<Cn3wPurchaseStock> cn3wPurchaseStockList = vehicleJobService.queryCn3wPurchaseStock(map);
					if (cn3wPurchaseStockList != null && cn3wPurchaseStockList.size() > 0) {
						historyDto.setSapStatus(cn3wPurchaseStockList.get(0).getStatus() + "");
					}
				}

				Map<String, Object> map = new HashMap<>();
				map.put("entryOrderId", detail.getLbx());
				List<Entry3wOrder> entryList = purchaseVehicleOrderDetailService.queryEntry3wOrder(map);
				if (entryList != null && entryList.size() > 0) {
					historyDto.setLbxStatus(entryList.get(0).getStatus());
					historyDto.setLbxActualQty(entryList.get(0).getActualQty());
				}
				histotryList.add(historyDto);
			}
		}

		json.put("details", histotryList);
		// 扣款单号 VehicleOrderZqDetailsDTO.zqItemNo
		// 85单 VehicleOrderZqDetailsDTO.vbelnDn1
		// LBX单号VehicleOrderZqDetailsDTO.lbx
		// LBX入库时间VehicleOrderZqDetailsDTO.zspdt
		// 闸口信息VehicleOrderZqDetailsDTO.remark
		// 总金额 VehicleOrderZqDetailsDTO.amount
		// 扣款状态 VehicleOrderZqDetailsDTO.status
		return json;
	}

	public JSONObject submitOrder(String orderNo) {
		JSONObject json = new JSONObject();
		VehicleOrderDTO orderEntity = new VehicleOrderDTO();
		orderEntity.setOrderNo(orderNo);
		VehicleOrderDTO orderDTO = vehicleOrderDao.getOneByCondition(orderEntity);
		VehicleOrderDetailsDTO detailEntity = new VehicleOrderDetailsDTO();
		detailEntity.setOrderNo(orderNo);
		List<VehicleOrderDetailsDTO> details = vehicleOrderDetailDao.getListByCondition(detailEntity);
		List<InsertOrderToOMSItem> list = null;
		try {
			list = getInsertOrderToOMSItem(orderDTO, details);
		} catch (BusinessException e1) {
			e1.printStackTrace();
			json.put("msg", e1.getMessage());
			return json;
		}
		boolean res = true;
		if (list != null) {
			for (InsertOrderToOMSItem e : list) {
				ServiceResult<String> resStr = vehicleInsertOrderToOMSService.sendOrderToOms(e);
				if (resStr.getSuccess()) {
					Map<String, String> obj = (Map<String, String>) JSONObject.parse(resStr.getResult());
					obj.get("billCode");
					VehicleOrderDetailsDTO temp = new VehicleOrderDetailsDTO();
					temp.setItemNo(e.getBillCode());
					temp.setStatus("80");
					temp.setRelevantOrderNo1(obj.get("vbeln"));
					vehicleOrderDetailDao.updateSelectiveByItemNo(temp);
				} else {
					res = false;
					VehicleOrderDetailsDTO temp = new VehicleOrderDetailsDTO();
					temp.setItemNo(e.getBillCode());
					temp.setStatus("18");
					json.put("msg", resStr.getMessage());
					temp.setRemark(resStr.getMessage());
					vehicleOrderDetailDao.updateSelectiveByItemNo(temp);
					break;
				}
			}
		}
		VehicleOrderDTO dto = new VehicleOrderDTO();
		dto.setOrderNo(orderNo);
		json.put("success", res);
		if (res) {
			saveZQ(orderDTO, details);
			dto.setStatus("80");
			vehicleOrderDao.updateSelectiveByOrderNo(dto);
		} else {
			dto.setStatus("18");
			dto.setRemark((String) json.get("msg"));
			vehicleOrderDao.updateSelectiveByOrderNo(dto);
		}
		return json;

	}

	private List<InsertOrderToOMSItem> getInsertOrderToOMSItem(VehicleOrderDTO orderDTO,
			List<VehicleOrderDetailsDTO> details) throws BusinessException {
		String custMgr = "01417887";
		if (MapUtils.isEmpty(sendMap)) {
			getSendTo();
		}
		AreaCenterInfoDTO areaDto = areaCenterInfoDao.getOneByDeliveryToCode(orderDTO.getDeliveryCode());
		List<InsertOrderToOMSItem> list = new ArrayList<InsertOrderToOMSItem>();
		PlanInDate planInDate = systemCenterService.getPlanInDateByTypeId(PlanInDateEnum.ZCDH.getCode());
		if (planInDate == null || planInDate.getValue() == null || "".equals(planInDate.getValue())) {
			throw new BusinessException("整车直发到货周未设置,请设置到货周!");
		}
		for (VehicleOrderDetailsDTO e : details) {
			InsertOrderToOMSItem item = new InsertOrderToOMSItem();
			item.setBillCode(e.getItemNo());
			item.setCorpCode(orderDTO.getCorpCode());
			item.setRegionID(orderDTO.getAreaCode());
			item.setBillType("ZGOZ");
			item.setPickType("3");
			item.setCorpDest(orderDTO.getDistributionCentre());
			item.setWhCode(areaDto.getWarehouseCode() + "81");
			item.setCustMgr(custMgr);
			item.setProMgr(custMgr);
			item.setProDeputy(custMgr);
			item.setPlanInDate(DateUtil.getT2Date(Integer.parseInt(planInDate.getValue())));
			item.setOrderCode("");
			item.setCustCode(this.getSoldToCode());
			item.setCustDest(orderDTO.getDeliveryCode());
			item.setMGCustCode(getSendMapValue(orderDTO.getDeliveryCode()));
			item.setMaker(getUserId() + getUserName());
			item.setLockDay("0");
			item.setInvCode(e.getMaterielCode());
			item.setInvSort(e.getProductGroup());
			item.setQty(String.valueOf(e.getQty()));
			item.setReleBillCode("");
			item.setFlag("4");
			item.setADD1(e.getPaymentCode());
			item.setADD2(orderDTO.getOrderNo());
			item.setADD3(String.valueOf(orderDTO.getRows()));
			item.setADD6("TB");
			item.setBaseCode(orderDTO.getJdCode());
			list.add(item);
		}
		return list;
	}

	private void saveZQ(VehicleOrderDTO order, List<VehicleOrderDetailsDTO> details) {
		VehicleOrderZqDTO zqOrder = new VehicleOrderZqDTO();
		BeanUtils.copyProperties(order, zqOrder);
		zqOrder.setType("ZGOZ");
		zqOrder.setSoldToCode(getSoldToCode());
		zqOrder.setSoldToName(getSoldToName());
		zqOrder.setMgCustCode(getSendMapValue(order.getDeliveryCode()));
		String zqOrderNo = StringUtils.replace(order.getOrderNo(), "ZK", "ZQ");
		zqOrder.setZqOrderNo(zqOrderNo);
		vehicleOrderZqDao.insertSelective(zqOrder);
		for (VehicleOrderDetailsDTO e : details) {
			VehicleOrderZqDetailsDTO temp = new VehicleOrderZqDetailsDTO();
			BeanUtils.copyProperties(e, temp);
			temp.setZqOrderNo(zqOrderNo);
			temp.setZqItemNo(StringUtils.replace(e.getItemNo(), "ZK", "ZQ"));
			vehicleOrderZqDetailsDao.insertSelective(temp);
		}

	}

	/**
	 * 获取物料信息
	 *
	 * @param pager
	 * @param condition
	 * @return
	 */
	public JSONObject productList(PagerInfo pager, MaterielInfoDTO condition) {
		if (StringUtils.isNotBlank(condition.getDeliveryToCode())) {
			List<MaterielInfoDTO> list = materielInfoDao.getPageByCondition(condition, pager.getStart(),
					pager.getPageSize());
			long total = materielInfoDao.getPagerCount(condition);
			if (list != null && !list.isEmpty()) {
				setItemPrice(condition.getDeliveryToCode(), list);
			}
			return jsonResult(list, total);
		}
		return jsonResult(null, 0);
	}

	/**
	 * 获取价格
	 *
	 * @param deliveryToCode
	 * @param items
	 */
	private void setItemPrice(String deliveryToCode, List<MaterielInfoDTO> items) {
		Map<String, String> map = new HashMap<String, String>();
		if (null != items && !items.isEmpty()) {
			for (MaterielInfoDTO e : items) {
				DepartmentInfoDTO dep = departmentInfoDao.getOneByDeliveryToCode(deliveryToCode);
				AreaCenterInfoDTO area = areaCenterInfoDao.getOneByDeliveryToCode(deliveryToCode);
				VehicleProductPaymentDTO paymentDTO = new VehicleProductPaymentDTO();
				paymentDTO.setProductGroup(e.getProductGroupCode());
				PurchaseProductPaymentDTO tempDto = vehicleProductPaymentDao
						.getPurchasePaymentOneByCondition(paymentDTO);
				map.put("custCode", tempDto.getPaymentCode());
				map.put("regionCode", area.getAreaCode());
				map.put("invCode", e.getMaterielCode());
				map.put("corpCode", dep.getOrganizationCode());
				// 获取物料相关金额
				String str = vehicleGetPriceFromCRMService.getPriceFromCrm(map);
				Map<String, String> json = (Map<String, String>) JSONObject.parse(str);
				e.setPrice(Double
						.parseDouble(StringUtils.isBlank(json.get("reUnitPrice")) ? "0" : json.get("reUnitPrice")));// 开票价
				e.setBateRate(
						Double.parseDouble(StringUtils.isBlank(json.get("reBateRate")) ? "0" : json.get("reBateRate")));// 直采扣点
				e.setStockPrice(Double
						.parseDouble(StringUtils.isBlank(json.get("reStockPrice")) ? "0" : json.get("reStockPrice"))); // 采购价
				e.setRetailPrice(Double
						.parseDouble(StringUtils.isBlank(json.get("reRetailPrice")) ? "0" : json.get("reRetailPrice"))); // 供价,零售价
				e.setActPrice(
						Double.parseDouble(StringUtils.isBlank(json.get("reActPrice")) ? "0" : json.get("reActPrice"))); // 执行价
				e.setBateMoney(Double
						.parseDouble(StringUtils.isBlank(json.get("reBateMoney")) ? "0" : json.get("reBateMoney"))); // 单台台返金额
				e.setLossRate(
						Double.parseDouble(StringUtils.isBlank(json.get("reLossRate")) ? "0" : json.get("reLossRate"))); // 折扣扣率
				e.setIsfl(Integer.parseInt(StringUtils.isBlank(json.get("reIsFL")) ? "0" : json.get("reIsFL"))); // 返利类型
				e.setIskpo(Integer.parseInt(StringUtils.isBlank(json.get("reIsKPO")) ? "0" : json.get("reIsKPO"))); // 商用空调标志
			}
		}
	}

	private <T> JSONObject jsonResult(List<T> list, long total) {
		JSONObject json = new JSONObject();
		json.put("total", total);
		if (list == null || list.isEmpty()) {
			json.put("rows", new ArrayList<T>());
		} else {
			json.put("rows", list);
		}
		return json;
	}

	public List<ExportVehicleDTO> selectVehicleExport(Map<String, Object> params) {
		List<ExportVehicleDTO> result = vehicleOrderDao.selectVehicleExport(params);
		for (ExportVehicleDTO e : result) {
			if (e.getWhCode() == null) {
				e.setWhCode(vehicleOrderDao.getWhCode(e.getDeliveryCode()));
			}
		}

		return result;
	}

	public JSONArray whCode() {
		AreaCenterInfoDTO entity = new AreaCenterInfoDTO();
		List<AreaCenterInfoDTO> dtoList = areaCenterInfoDao.getListByCondition(entity);
		JSONArray array = new JSONArray();
		for (AreaCenterInfoDTO o : dtoList) {
			JSONObject json = new JSONObject();
			json.put("text", o.getWhCode() + ":" + o.getWarehouseName());
			json.put("value", o.getDeliveryToCode());
			array.add(json);
		}
		return array;
	}

	public int updateVblenSpare(String itemNo, String vbelnSpare) {
		return vehicleOrderDetailDao.updateVbelnSpareByItemNo(itemNo, vbelnSpare);
	}

	public ServiceResult<List<Cn3wPurchaseStock>> findPushToSAPList(Map<String, Object> params) {

		ServiceResult<List<Cn3wPurchaseStock>> result = new ServiceResult<List<Cn3wPurchaseStock>>();
		// Integer start = null, size = null;
		// PagerInfo pager = (PagerInfo) params.get("pager");
		// if (pager != null) {
		// start = pager.getStart();
		// size = pager.getPageSize();
		// }
		// params.put("start",start);
		// params.put("size",size);

		// if (pager != null) {
		// pager.setRowsCount(vehicleOrderDao.findPushToSAPListCount(params));
		// }
		int pagecount = vehicleOrderDao.findPushToSAPListCount(params);
		PagerInfo pager = new PagerInfo();
		pager.setRowsCount(pagecount);
		// result.setPager(pi);
		// return result;

		result.setPager(pager);
		result.setResult(vehicleOrderDao.findPushToSAPList(params));
		return result;
	}

	public JSONObject checkOrder(String orderNo, String vbelnDn1, Date sDate, Date eDate, int page, int rows) {
		JSONObject json = new JSONObject();
		List<String> orderNos = new ArrayList<>();
		if (StringUtils.isNotBlank(orderNo)) {
			for (String no : orderNo.replaceAll("，", ",").split(",")) {
				if (StringUtils.isNotBlank(no)) {
					orderNos.add(no);
				}
			}
		}
		List<String> vbelnDn1s = new ArrayList<>();
		if (StringUtils.isNotBlank(vbelnDn1)) {
			for (String no : vbelnDn1.replaceAll("，", ",").split(",")) {
				if (StringUtils.isNotBlank(no)) {
					vbelnDn1s.add(no);
				}
			}
		}
		VehicleOrderDTO condition = new VehicleOrderDTO();
		condition.setOrderNos(orderNos.size() == 0 ? null : orderNos);
		condition.setVbelnDn1s(vbelnDn1s.size() == 0 ? null : vbelnDn1s);
		condition.setStartOrderTime(sDate);
		condition.setEndOrderTime(eDate);
		List<VehicleOrderHistoryDTO> list = vehicleOrderDao.getChangeDNPageByCondition(condition, page, rows);
		Long count = vehicleOrderDao.getChangeDNPagerCount(condition);
		json.put("rows", list);
		json.put("total", count);

		return json;

		// VehicleOrderDTO condition = new VehicleOrderDTO();
		// VehicleOrderDetailsDTO detailsCondition = new
		// VehicleOrderDetailsDTO();
		// JSONObject json = new JSONObject();
		// condition.setOrderNo(orderNo);
		//
		//
		// detailsCondition.setOrderNo(orderNo);
		// detailsCondition.setStartOrderTime(sDate);
		// detailsCondition.setEndOrderTime(eDate);
		// VehicleOrderDTO orderDTO =
		// vehicleOrderDao.getOneByCondition(condition);
		//// List<VehicleOrderDetailsDTO> details = vehicleOrderDetailDao
		//// .getListByCondition(detailsCondition);
		// List<VehicleOrderDetailsDTO> details2 =
		// vehicleOrderDetailDao.getPageByCondition(detailsCondition, page,
		// rows);
		// List<String> orderList = new ArrayList<>();
		// for(VehicleOrderDetailsDTO dto : details2){
		// orderList.add(dto.getOrderNo());
		// }
		// List<VehicleOrderHistoryDTO> histotryList = new
		// ArrayList<VehicleOrderHistoryDTO>();
		//// json.put("order", orderDTO);
		// if (details2 != null && details2.size() > 0) {
		// for (VehicleOrderDetailsDTO detail : details2) {
		// VehicleOrderHistoryDTO historyDto = new VehicleOrderHistoryDTO();
		// BeanUtils.copyProperties(detail, historyDto);
		// VehicleOrderZqDetailsDTO entity = new VehicleOrderZqDetailsDTO();
		// entity.setZqItemNo(detail.getItemNo().replaceAll("ZK", "ZQ"));
		// List<VehicleOrderZqDetailsDTO> zqDetailsList =
		// vehicleOrderZqDetailsDao
		// .listByCondition(entity);
		// if (zqDetailsList != null && zqDetailsList.size() > 0) {
		// historyDto.setZqItemNo(zqDetailsList.get(0).getZqItemNo());
		// historyDto.setZqStatus(zqDetailsList.get(0).getStatus());
		// historyDto.setRemark(zqDetailsList.get(0).getRemark());
		// historyDto.setVbelnDn5(zqDetailsList.get(0).getVbelnDn5());
		// }
		// if (!"".equals(detail.getVbelnDn1())) {
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("cnStockSyncsId", detail.getItemNo());
		// List<Cn3wPurchaseStock> cn3wPurchaseStockList = vehicleJobService
		// .queryCn3wPurchaseStock(map);
		// if (cn3wPurchaseStockList != null
		// && cn3wPurchaseStockList.size() > 0) {
		// historyDto.setSapStatus(cn3wPurchaseStockList.get(0)
		// .getStatus() + "");
		// }
		// }
		//
		// Map<String, Object> map = new HashMap<>();
		// map.put("entryOrderId", detail.getLbx());
		// List<Entry3wOrder> entryList = purchaseVehicleOrderDetailService
		// .queryEntry3wOrder(map);
		// if (entryList != null && entryList.size() > 0) {
		// historyDto.setLbxStatus(entryList.get(0).getStatus());
		// historyDto.setLbxActualQty(entryList.get(0).getActualQty());
		// }
		// histotryList.add(historyDto);
		// }
		// }
		//
		// json.put("details", histotryList);
		// 扣款单号 VehicleOrderZqDetailsDTO.zqItemNo
		// 85单 VehicleOrderZqDetailsDTO.vbelnDn1
		// LBX单号VehicleOrderZqDetailsDTO.lbx
		// LBX入库时间VehicleOrderZqDetailsDTO.zspdt
		// 闸口信息VehicleOrderZqDetailsDTO.remark
		// 总金额 VehicleOrderZqDetailsDTO.amount
		// 扣款状态 VehicleOrderZqDetailsDTO.status
		// return json;
	}

	public Long getChangeDNTotal(String orderNo, String vbelnDn1, Date sDate, Date eDate) {
		List<String> orderNos = new ArrayList<>();
		if (StringUtils.isNotBlank(orderNo)) {
			for (String no : orderNo.replaceAll("，", ",").split(",")) {
				if (StringUtils.isNotBlank(no)) {
					orderNos.add(no);
				}
			}
		}
		List<String> vbelnDn1s = new ArrayList<>();
		if (StringUtils.isNotBlank(vbelnDn1)) {
			for (String no : vbelnDn1.replaceAll("，", ",").split(",")) {
				if (StringUtils.isNotBlank(no)) {
					vbelnDn1s.add(no);
				}
			}
		}
		VehicleOrderDTO condition = new VehicleOrderDTO();
		condition.setOrderNos(orderNos.size() == 0 ? null : orderNos);
		condition.setVbelnDn1s(vbelnDn1s.size() == 0 ? null : vbelnDn1s);
		condition.setStartOrderTime(sDate);
		condition.setEndOrderTime(eDate);
		Long count = vehicleOrderDao.getChangeDNPagerCount(condition);
		return count;
	}

	public List<VehicleOrderHistoryDTO> getChangeDNList(String orderNo, String vbelnDn1, Date sDate, Date eDate,
			int page, int rows) {
		List<String> orderNos = new ArrayList<>();
		if (StringUtils.isNotBlank(orderNo)) {
			for (String no : orderNo.replaceAll("，", ",").split(",")) {
				if (StringUtils.isNotBlank(no)) {
					orderNos.add(no);
				}
			}
		}
		List<String> vbelnDn1s = new ArrayList<>();
		if (StringUtils.isNotBlank(vbelnDn1)) {
			for (String no : vbelnDn1.replaceAll("，", ",").split(",")) {
				if (StringUtils.isNotBlank(no)) {
					vbelnDn1s.add(no);
				}
			}
		}
		VehicleOrderDTO condition = new VehicleOrderDTO();
		condition.setOrderNos(orderNos.size() == 0 ? null : orderNos);
		condition.setVbelnDn1s(vbelnDn1s.size() == 0 ? null : vbelnDn1s);
		condition.setStartOrderTime(sDate);
		condition.setEndOrderTime(eDate);
		return vehicleOrderDao.getChangeDNPageByCondition(condition, page, rows);
	}

	public int updateVbelnSpareByItemNo(String itemNo, String vbelnSpare) {
		return vehicleOrderDetailDao.updateVbelnSpareByItemNo(itemNo, vbelnSpare);
	}

	public boolean vbelnExists(String itemNo, String vbelnSpare) {
		return vehicleOrderDetailDao.vbelnExists(itemNo, vbelnSpare);
	}

	public ServiceResult<List<VehiclePushToSAP>> findPushToSAPList2(Map<String, Object> params) {
		ServiceResult<List<VehiclePushToSAP>> result = new ServiceResult<List<VehiclePushToSAP>>();
		int pagecount = vehicleOrderDao.findPushToSAPListCount2(params);
		PagerInfo pager = new PagerInfo();
		pager.setRowsCount(pagecount);

		result.setPager(pager);
		result.setResult(vehicleOrderDao.findPushToSAPList2(params));
		return result;
	}

}
