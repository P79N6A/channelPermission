package com.haier.vehicle.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.model.vehcile.Cn3wPurchaseStock;
import com.haier.purchase.data.model.vehcile.ExportVehicleDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDetailsDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderHistoryDTO;
import com.haier.purchase.data.model.vehcile.VehiclePushToSAP;
import com.haier.purchase.data.service.vechile.PurchaseVehicleInterfaceLogService;
import com.haier.purchase.data.service.vechile.PurchaseVehicleOrderDetailService;
import com.haier.purchase.data.service.vechile.PurchaseVehicleOrderService;
import com.haier.vehicle.service.VehicleOrderService;
import com.haier.vehicle.util.DateUtil;
import com.haier.vehicle.util.HttpUtils;

@Service
public class VehicleOrderServiceImpl implements VehicleOrderService{
	
	@Autowired
	PurchaseVehicleOrderService pourchaseVehicleOrderService; 
	@Autowired
	PurchaseVehicleOrderDetailService purchaseVehicleOrderDetailService;
	@Autowired
	private PurchaseVehicleInterfaceLogService vehicleInterfaceLogService;
	
	@Value("${cancelOMSUrl}")
	private String cancelOMSUrl;
	@Value("${wsdlPath}")
	private String wsdlPath;
	private static final QName SERVICE_NAME = new QName("http://www.example.org/AtpForeBackFromATPToOMS/", "AtpForeBackFromATPToOMS");
	
	@Override
	public int insertSelective(VehicleOrderDTO entity) {
		return pourchaseVehicleOrderService.insertSelective(entity);
	}

	@Override
	public int updateSelectiveById(VehicleOrderDTO entity) {
		return pourchaseVehicleOrderService.updateSelectiveById(entity);
	}

	@Override
	public VehicleOrderDTO getOneById(long id) {
		return pourchaseVehicleOrderService.getOneById(id);
	}

	@Override
	public VehicleOrderDTO getOneByCondition(VehicleOrderDTO entity) {
		return pourchaseVehicleOrderService.getOneByCondition(entity);
	}

	@Override
	public List<VehicleOrderDTO> getListByCondition(VehicleOrderDTO entity) {
		return pourchaseVehicleOrderService.getListByCondition(entity);
	}

	@Override
	public List<VehicleOrderDTO> getPageByCondition(VehicleOrderDTO entity,
			int start, int rows) {
		return pourchaseVehicleOrderService.getPageByCondition(entity, start, rows);
	}

	@Override
	public long getPagerCount(VehicleOrderDTO entity) {
		return pourchaseVehicleOrderService.getPagerCount(entity);
	}

	@Override
	public VehicleOrderDTO getOneByDeliveryToCode(String deliveryToCode) {
		return pourchaseVehicleOrderService.getOneByDeliveryToCode(deliveryToCode);
	}

	@Override
	public String getVehicleOrderNo(String begin) {
		return pourchaseVehicleOrderService.getVehicleOrderNo(begin);
	}

	@Override
	public int updateSelectiveByOrderNo(VehicleOrderDTO entity) {
		return pourchaseVehicleOrderService.updateSelectiveByOrderNo(entity);
	}
	
	@Override
	public List<ExportVehicleDTO> selectVehicleExport(Map<String, Object> params) {
		return pourchaseVehicleOrderService.selectVehicleExport(params);
	}

	@Override
	public String getWhCode(String code) {
		return pourchaseVehicleOrderService.getWhCode(code);
	}
	
	@Override
	public Map<String,Object> cancelOrder(VehicleOrderDTO order) {
		Map<String,Object> resultMap = new HashMap<>();
		String result = "";
		VehicleOrderDetailsDTO entity = new VehicleOrderDetailsDTO();
		entity.setOrderNo(order.getOrderNo());
//		List<VehicleOrderDetailsDTO> detailDTOList = purchaseVehicleOrderDetailService.getListByCondition(entity);
		String path = cancelOMSUrl;
//			for(VehicleOrderDetailsDTO dto : detailDTOList){.replaceAll("ZK", "")
				
				String data = "<PARAMETER>" + "<item>" + "<custOrderCode>"
						+ order.getOrderNo() + "</custOrderCode>" + "<flag>1</flag>"
						+ "<orderType>1</orderType>" + "<attr1>"
						+ DateUtil.getFormatDate2(order.getDateOfArrival()) + "</attr1>" + "<attr2>ZK</attr2>"
						+ "<attr3></attr3><attr4></attr4><attr5></attr5>"
						+ "</item>" + "</PARAMETER>";
				vehicleInterfaceLogService.insertLog("整车直发取消入参",data);
				String resultMsg = HttpUtils.sendRequest(path, null, data,
						HttpUtils.HTTP_METHOD_POST, false, null);
				vehicleInterfaceLogService.insertLog("整车直发取消出参",resultMsg);
				// GVSOrderPriceResponse result = new GVSOrderPriceResponse();
				Document doc;
				try {
					doc = DocumentHelper.parseText(resultMsg);
					Element element = (Element) doc.getRootElement().elements().get(0);
					List<Element> list = element.elements();
					String messageCode = "";
					String messageDetail = "";
					 for (Element el : list) {
						 if("messageCode".equals(el.getName())){
							 messageCode = el.getText();
						 }
						 if("messageDetail".equals(el.getName())){
							 messageDetail = el.getText();
						 }
					 }
					 resultMap.put("code", messageCode);
					 resultMap.put("message", messageDetail);

				} catch (DocumentException e) {
					e.printStackTrace();
				}
		return resultMap;
	}
	
	@Override
	public List<Cn3wPurchaseStock> findPushToSAPList(Map<String, Object> params) {
		return pourchaseVehicleOrderService.findPushToSAPList(params);
	}

	@Override
	public Integer findPushToSAPListCount(Map<String, Object> params) {
		return pourchaseVehicleOrderService.findPushToSAPListCount(params);
	}

	@Override
	public List<VehicleOrderHistoryDTO> getChangeDNPageByCondition(VehicleOrderDTO condition, int page, int rows) {
		return pourchaseVehicleOrderService.getChangeDNPageByCondition(condition, page, rows);
	}

	@Override
	public Long getChangeDNPagerCount(VehicleOrderDTO condition) {
		return pourchaseVehicleOrderService.getChangeDNPagerCount(condition);
	}

	@Override
	public List<VehiclePushToSAP> findPushToSAPList2(Map<String, Object> params) {
		return pourchaseVehicleOrderService.findPushToSAPList2(params);
	}

	@Override
	public int findPushToSAPListCount2(Map<String, Object> params) {
		return pourchaseVehicleOrderService.findPushToSAPListCount2(params);
	}

}
