package com.haier.vehicle.services;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.haier.vehicle.service.VehicleGetPriceFromCRMService;
import com.haier.vehicle.util.HttpUtils;

/**
 * crm价格
 * <p>
 * map.put("custCode客户编码","8800044838"); map.put("regionCode地区编码","QD001");
 * map.put("invCode产品编码","CA0JN0B06"); map.put("corpCode日日顺公司编码","2130");
 *
 * @author
 * @create 2017-09-12 16:13
 **/
@Service
public class VehicleGetPriceFromCRMServiceImpl implements
		VehicleGetPriceFromCRMService {
	@Value("${crmPriceUrl}")
	private String crmPriceUrl;

//	@Autowired
//	private PurchaseVehicleInterfaceLogService vehicleInterfaceLogService;

	@Override
	public String getPriceFromCrm(Map<String, String> map) {

		// String url =
		// "http://58.56.128.84:9001/EAI/RoutingProxyService/EAI_REST_POST_ServiceRoot?INT_CODE=EAI_INT_1020";
		String url = crmPriceUrl;
				//"http://58.56.128.10:19001/EAI/service/IHS/GetInvRebateInfoFromIHSForCycle/GetInvRebateInfoFromIHSForCycle?INT_CODE=EAI_INT_1020";
		String data = "<INPUT><INPUTROW><CustCode>" + map.get("custCode")
				+ "</CustCode><RegionCode>" + map.get("regionCode")
				+ "</RegionCode><InvCode>" + map.get("invCode")
				+ "</InvCode><CorpCode>" + map.get("corpCode")
				+ "</CorpCode></INPUTROW></INPUT>";
		String response = HttpUtils.sendRequest(url, null, data,
				HttpUtils.HTTP_METHOD_POST, false, null);
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(response);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element element = (Element) doc.getRootElement().elements().get(0);
		List<Element> list = element.elements();
		JSONObject json = new JSONObject();
		for (Element el : list) {
			if (el.getName().equals("ReInvCode")) {
				json.put("reInvCode", el.getText());
			} else if (el.getName().equals("ReUnitPrice")) {
				json.put("reUnitPrice", el.getText());
			} else if (el.getName().equals("ReStockPrice")) {
				json.put("reStockPrice", el.getText());
			} else if (el.getName().equals("ReRetailPrice")) {
				json.put("reRetailPrice", el.getText());
			} else if (el.getName().equals("ReActPrice")) {
				json.put("reActPrice", el.getText());
			} else if (el.getName().equals("ReBateRate")) {
				json.put("reBateRate", el.getText());
			} else if (el.getName().equals("ReBateMoney")) {
				json.put("reBateMoney", el.getText());
			} else if (el.getName().equals("ReLossRate")) {
				json.put("reLossRate", el.getText());
			} else if (el.getName().equals("ReIsFL")) {
				json.put("reIsFL", el.getText());
			} else if (el.getName().equals("ReIsKPO")) {
				json.put("reIsKPO", el.getText());
			}
		}
		return json.toJSONString();
	}
}
