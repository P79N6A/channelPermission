package com.haier.vehicle.services;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.service.vechile.PurchaseVehicleInterfaceLogService;
import com.haier.vehicle.model.SaleBIllDetail;
import com.haier.vehicle.model.SaleBillMaster;
import com.haier.vehicle.service.VehicleInsertSaleBillToCRMService;
import com.haier.vehicle.util.ConversionClassUtil;
import com.haier.vehicle.util.WSUtils;
import com.haier.vehicle.wsdl.saleorder.BPELProcess;
import com.haier.vehicle.wsdl.saleorder.BpelprocessClientEp;
import com.haier.vehicle.wsdl.saleorder.DetailType;
import com.haier.vehicle.wsdl.saleorder.InsertSaleBillGVS2ProcessRequest;
import com.haier.vehicle.wsdl.saleorder.InsertSaleBillGVS2ProcessResponse;
import com.haier.vehicle.wsdl.saleorder.MasterType;
import com.haier.vehicle.wsdl.saleorder.RemoteExceptionMessage;

/**
 * 扣款
 *
 * @author
 * @create 2017-09-08 14:47
 **/
@Service
public class VehicleInsertSaleBillToCRMServiceImpl implements
		VehicleInsertSaleBillToCRMService {

	@Value("${wsdlPath}")
	private String wsdlLocation;

	@Autowired
	private PurchaseVehicleInterfaceLogService vehicleInterfaceLogService;

	@Override
	public ServiceResult<String> sendSaleBillToCRM(SaleBillMaster master,
			SaleBIllDetail detail) {
		ServiceResult<String> result = new ServiceResult<String>();
		InsertSaleBillGVS2ProcessResponse out = null;
		JSONObject json = null;
		// String path = "file:"
		// + this.getClass()
		// .getResource(wsdlLocation + "/InsertSaleBill_ZKToCRM.wsdl")
		// .getPath();
		// URL url = new URL(path);
		URL url = this.getClass().getResource(
				wsdlLocation + "/InsertSaleBill_ZKToCRM.wsdl");
//		URL url = this.getClass().getResource("/wsdl_test/InsertSaleBill_ZKToCRM.wsdl");
		BpelprocessClientEp service = new BpelprocessClientEp(url);
		BPELProcess soap = service.getBPELProcessPt();
		InsertSaleBillGVS2ProcessRequest insertSaleBillGVS2ProcessRequest = new InsertSaleBillGVS2ProcessRequest();
		MasterType masterNew = new MasterType();
		DetailType dtNew = new DetailType();
		ConversionClassUtil.conversion(master, masterNew);
		ConversionClassUtil.conversion(detail, dtNew);
		dtNew.setADD1(detail.getADD1());
		dtNew.setADD2(detail.getADD2());
		dtNew.setADD3(detail.getADD3());
		masterNew.setADD1(master.getADD1());
		masterNew.setADD2(master.getADD2());
		masterNew.setADD3(master.getADD3());
		masterNew.setADD4(master.getADD4());
		masterNew.setADD5(master.getADD5());
		masterNew.setBName(master.getBName());
		masterNew.setATPPlanInDate(WSUtils.stringToXmlCalendar(
				master.getAtpPlanInDateprivate(), "yyyy-MM-dd"));
		insertSaleBillGVS2ProcessRequest.getDETAIL().add(dtNew);
		insertSaleBillGVS2ProcessRequest.setMASTER(masterNew);
		try {
			JSONObject object = (JSONObject) JSONObject
					.toJSON(insertSaleBillGVS2ProcessRequest);
			vehicleInterfaceLogService.insertLog("整车直发CRM扣款入参",
					object.toJSONString());
			out = soap.process(insertSaleBillGVS2ProcessRequest);
			json = (JSONObject) JSONObject.toJSON(out);
			vehicleInterfaceLogService.insertLog("整车直发CRM扣款出参",
					"单号:"+master.getBillCode()+json.toJSONString());
			JSONObject jsResult = new JSONObject();
			if ("W".equals(out.getCODE()) || "F".equals(out.getCODE())) {
				result.setMessage(out.getMSG());
				result.setSuccess(false);
			} else {
				if ("S".equals(out.getResult().getFlag())) {
					jsResult.put("billCode", out.getResult().getBillCode());
					jsResult.put("vbeln", out.getResult().getVbeln());
					jsResult.put("vbelnDn", out.getResult().getVBELNDN());
					result.setResult(jsResult.toJSONString());
					result.setSuccess(true);
					result.setMessage(out.getResult().getFlag());
				} else {
					result.setResult("");
					result.setSuccess(false);
					result.setMessage(out.getResult().getFlag());
				}

			}

		} catch (RemoteExceptionMessage remoteExceptionMessage) {
			remoteExceptionMessage.printStackTrace();
			result.setMessage("调用下单接口异常");
			result.setSuccess(false);
		}
		return result;
	}
}
