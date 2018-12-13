package com.haier.vehicle.services;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.haier.common.ServiceResult;
import com.haier.purchase.data.model.vehcile.Cn3wPurchaseStock;
import com.haier.purchase.data.model.vehcile.Entry3wOrder;
import com.haier.purchase.data.model.vehcile.VehicleOrderZqDetailsDTO;
import com.haier.purchase.data.service.vechile.PurchaseVehicleInterfaceLogService;
import com.haier.vehicle.model.VehicleOrderDetailModel;
import com.haier.vehicle.service.TMallCAMachineService;
import com.haier.vehicle.service.VehicleInterfaceLogService;
import com.haier.vehicle.service.VehicleJobService;
import com.haier.vehicle.wsdl.les.ZHIFAADDRES;
import com.haier.vehicle.wsdl.les.ZHIFACUSTOMER;
import com.haier.vehicle.wsdl.les.ZHIFACUSTOMER.INPUT;
import com.haier.vehicle.wsdl.les.ZHIFACUSTOMERPT;
import com.haier.vehicle.wsdl.les.ZHIFACUSTOMERPTBindingQSService;
import com.haier.vehicle.wsdl.les.ZHIFACUSTOMERRESPONSE;
import com.haier.vehicle.wsdl.mdm.HAIERMDMFIELDSVALUETABLE;
import com.haier.vehicle.wsdl.mdm.HAIERMDMFIELDSVALUETYPE;
import com.haier.vehicle.wsdl.mdm.TransCustomerInfoXDToMDM;
import com.haier.vehicle.wsdl.mdm.TransCustomerInfoXDToMDM_Service;
import com.haier.vehicle.wsdl.querystatus.InputType;
import com.haier.vehicle.wsdl.querystatus.OutputType;
import com.haier.vehicle.wsdl.querystatus.QueryOrderStaToEstore;
import com.haier.vehicle.wsdl.querystatus.QueryOrderStaToEstore_Service;

/**
 * 定时任务
 *
 * @author zzb
 * @create 2017-09-07 9:48
 **/
@Service
public class VehicleJobServiceImpl implements VehicleJobService {

	@Autowired
	private VehicleOrderDetailModel vehicleOrderDetailModel;
	@Autowired
	private PurchaseVehicleInterfaceLogService vehicleInterfaceLogService;
	@Autowired
	private TMallCAMachineService tmallCAMachineSerivce;

	@Value("${wsdlPath}")
	private String wsdlLocation;

	@Override
	public void queryStatusFromGvs(String itemNo) {

		// String path = "file:"
		// + this.getClass()
		// .getResource(
		// wsdlLocation + "/QueryOrderStatusToEstore.wsdl")
		// .getPath();
		// URL url = new URL(path);
		URL url = this.getClass().getResource(
				wsdlLocation + "/QueryOrderStatusToEstore.wsdl");
		QueryOrderStaToEstore_Service service = new QueryOrderStaToEstore_Service(
				url);
		QueryOrderStaToEstore soap = service.getQueryOrderStaToEstoreSOAP();
		InputType in = new InputType();
		in.setBSTNK(itemNo);
		List<InputType> listIn = new ArrayList<InputType>();
		listIn.add(in);
		Holder<java.util.List<com.haier.vehicle.wsdl.querystatus.OutputType>> out = new Holder<java.util.List<com.haier.vehicle.wsdl.querystatus.OutputType>>();
		Holder<String> flag = new Holder<String>();
		Holder<String> message = new Holder<String>();
		Holder<String> errorDetail = new Holder<String>();
		soap.queryOrderStaToEstoreWsdl(listIn, out, flag, message, errorDetail);
		for (OutputType outType : out.value) {
			if (outType.getSTATUS() != null && !"".equals(outType.getSTATUS())) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("itemNo", itemNo);
				map.put("vbeln", outType.getVBELN());
				map.put("vbelnDn1", outType.getVBELNDN1());
				vehicleOrderDetailModel.updateByItemNo(map);
				// 更新状态
				vehicleOrderDetailModel.updateDetailStatus(outType.getBSTNK(),
						outType.getSTATUS());

				// 同一个主单下的子单是否都审核
				int count = vehicleOrderDetailModel.selectCount(
						outType.getBSTNK(), outType.getSTATUS());
				// 全部审核更新订单主表及扣款主表的订单状态
				if (count == 0) {
					vehicleOrderDetailModel.updateStatus(outType.getBSTNK(),
							outType.getSTATUS());
					vehicleOrderDetailModel.updateZqStatus(outType.getBSTNK(),
							outType.getSTATUS());
				}
			}
		}

	}

	@Override
	public void queryStatusFromGvs(List<String> itemNoList, String type) {

		URL url = this.getClass().getResource(
				wsdlLocation + "/QueryOrderStatusToEstore.wsdl");
		QueryOrderStaToEstore_Service service = new QueryOrderStaToEstore_Service(
				url);
		QueryOrderStaToEstore soap = service.getQueryOrderStaToEstoreSOAP();
		InputType in = null;
		List<InputType> listIn = null;
		Holder<java.util.List<com.haier.vehicle.wsdl.querystatus.OutputType>> out = null;
		Holder<String> flag = null;
		Holder<String> message = null;
		Holder<String> errorDetail = null;
		if ("ZQ".equals(type)) {
			for (String itemNo : itemNoList) {
				/** ZQ begin */
				in = new InputType();
				in.setBSTNK(itemNo);
				listIn = new ArrayList<InputType>();
				listIn.add(in);
				out = new Holder<java.util.List<com.haier.vehicle.wsdl.querystatus.OutputType>>();
				flag = new Holder<String>();
				message = new Holder<String>();
				errorDetail = new Holder<String>();
				soap.queryOrderStaToEstoreWsdl(listIn, out, flag, message,
						errorDetail);
				vehicleInterfaceLogService.insertLog("更新85单号接口" + type + "入参",
						JSONObject.toJSON(listIn).toString());
				vehicleInterfaceLogService.insertLog("更新85单号口" + type + "出参",
						JSONObject.toJSON(out).toString());
				for (OutputType outType : out.value) {
					if (outType.getVBELNDN1() != null
							&& !"".equals(outType.getVBELNDN1())) {
						VehicleOrderZqDetailsDTO zqDetail = new VehicleOrderZqDetailsDTO();
						zqDetail.setZqItemNo(itemNo);
						zqDetail.setVbelnDn5(outType.getVBELNDN1());
						// 修改zq子表二次85单号
						vehicleOrderDetailModel.updateByZqItemNo(zqDetail);
						if (outType.getSTATUS() != null
								&& !"".equals(outType.getSTATUS())) {
							vehicleOrderDetailModel.updateZqDetailStatus(
									outType.getBSTNK(), outType.getSTATUS());
						}
						// 同一个主单下的子单是否都审核
						int count = vehicleOrderDetailModel.selectZqCount(
								outType.getBSTNK(), outType.getSTATUS());
						// 全部审核更新订单主表及扣款主表的订单状态
						if (count == 0) {
							vehicleOrderDetailModel.updateZqStatus(
									outType.getBSTNK(), outType.getSTATUS());
						}
					}
				}
			}
		} else if ("ZK".equals(type)) {
			for (String itemNo : itemNoList) {
				in = new InputType();
				in.setBSTNK(itemNo);
				listIn = new ArrayList<InputType>();
				listIn.add(in);
				out = new Holder<java.util.List<com.haier.vehicle.wsdl.querystatus.OutputType>>();
				flag = new Holder<String>();
				message = new Holder<String>();
				errorDetail = new Holder<String>();
				soap.queryOrderStaToEstoreWsdl(listIn, out, flag, message,
						errorDetail);
				vehicleInterfaceLogService.insertLog("更新85单号接口" + type + "入参",
						JSONObject.toJSON(listIn).toString());
				vehicleInterfaceLogService.insertLog("更新85单号口" + type + "出参",
						JSONObject.toJSON(out).toString());
				for (OutputType outType : out.value) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("itemNo", outType.getBSTNK());
					map.put("vbeln", outType.getVBELN());
					map.put("vbelnDn1", outType.getVBELNDN1());
					// 修改zk子表85单号
					vehicleOrderDetailModel.updateByItemNo(map);
					if (outType.getSTATUS() != null
							&& !"".equals(outType.getSTATUS())) {
						// 更新zk子表状态
						vehicleOrderDetailModel.updateDetailStatus(
								outType.getBSTNK(), outType.getSTATUS());
						// 同一个主单下的子单是否都审核
						int count = vehicleOrderDetailModel.selectCount(
								outType.getBSTNK(), outType.getSTATUS());
						// 全部审核更新订单主表的订单状态
						if (count == 0) {
							// 更新ZK主表状态
							vehicleOrderDetailModel.updateStatus(
									outType.getBSTNK(), outType.getSTATUS());
							// 当状态是待发货时更新Zq子表的状态也为06
							if ("06".equals(outType.getSTATUS())) {
								// 全部审核更新订单主表及扣款主表的订单状态
								vehicleOrderDetailModel.updateZqStatus(outType
										.getBSTNK().replaceAll("ZK", "ZQ"),
										outType.getSTATUS());
							}
						}
					}
				}
			}
		}
	}

	@Override
	public String queryStatusFromMateriel(String itemNo) {

		// String path = "file:"
		// + this.getClass()
		// .getResource(
		// wsdlLocation + "/TransCustomerInfoXDToMDM.wsdl")
		// .getPath();
		URL url = this.getClass().getResource(
				wsdlLocation + "/TransCustomerInfoXDToMDM.wsdl");
		TransCustomerInfoXDToMDM_Service service = new TransCustomerInfoXDToMDM_Service(
				url);

		TransCustomerInfoXDToMDM soap = service
				.getTransCustomerInfoXDToMDMSOAP();
		// InputType in = new InputType();
		// in.setBSTNK(itemNo);
		// List<InputType> listIn = new ArrayList<InputType>();
		// listIn.add(in);
		// Holder<java.util.List<com.haier.vehicle.wsdl.querystatus.OutputType>>
		// out = new
		// Holder<java.util.List<com.haier.vehicle.wsdl.querystatus.OutputType>>();
		// Holder<String> flag = new Holder<String>();
		// Holder<String> message = new Holder<String>();
		Holder<String> errorDetail = new Holder<String>();
		// TransCustomerInfoXDToMDM port = service
		// .getTransCustomerInfoXDToMDMSOAP();

		java.lang.String _transCustomerInfoXDToMDM_inSYSNAME = "S00713";
		java.lang.String _transCustomerInfoXDToMDM_inMASTERTYPE = "HAIERMDM";
		java.lang.String _transCustomerInfoXDToMDM_inTABLENAME = "HRCBS_MTL_GENERAL_LES";

		java.util.List<com.haier.vehicle.wsdl.mdm.HAIERMDMFIELDSVALUETYPE> infieldsvaluetableitem = new ArrayList<HAIERMDMFIELDSVALUETYPE>();

		HAIERMDMFIELDSVALUETYPE type = new HAIERMDMFIELDSVALUETYPE();
		type.setFIELDNAME("MATERIAL_CODE");
		type.setFIELDVALUE(itemNo);
		type.setFIELDQUERYTYPE("in");

		infieldsvaluetableitem.add(type);

		HAIERMDMFIELDSVALUETABLE inFIELDSVALUETABLE = new HAIERMDMFIELDSVALUETABLE();

		inFIELDSVALUETABLE.setInfieldsvaluetableitem(infieldsvaluetableitem);

		List<HAIERMDMFIELDSVALUETABLE> inFIELDSVALUETABLEList = new ArrayList<HAIERMDMFIELDSVALUETABLE>();

		inFIELDSVALUETABLEList.add(inFIELDSVALUETABLE);

		javax.xml.ws.Holder<java.lang.String> _transCustomerInfoXDToMDM_outRESULT = new javax.xml.ws.Holder<java.lang.String>();
		javax.xml.ws.Holder<java.lang.String> _transCustomerInfoXDToMDM_outRETMSG = new javax.xml.ws.Holder<java.lang.String>();
		javax.xml.ws.Holder<java.lang.String> _transCustomerInfoXDToMDM_outRETCODE = new javax.xml.ws.Holder<java.lang.String>();
		soap.transCustomerInfoXDToMDM(_transCustomerInfoXDToMDM_inSYSNAME,
				_transCustomerInfoXDToMDM_inMASTERTYPE,
				_transCustomerInfoXDToMDM_inTABLENAME, inFIELDSVALUETABLEList,
				_transCustomerInfoXDToMDM_outRESULT,
				_transCustomerInfoXDToMDM_outRETMSG,
				_transCustomerInfoXDToMDM_outRETCODE);
		// JSONObject object = (JSONObject) JSONObject
		// .toJSON(inFIELDSVALUETABLEList);

		vehicleInterfaceLogService.insertLog("获取体积接口入参",
				JSONObject.toJSON(inFIELDSVALUETABLEList).toString());
		// object = (JSONObject) JSONObject
		// .toJSON(_transCustomerInfoXDToMDM_outRETCODE);

		vehicleInterfaceLogService.insertLog("获取体积接口出参",
				JSONObject.toJSON(_transCustomerInfoXDToMDM_outRETCODE)
						.toString());
		return _transCustomerInfoXDToMDM_outRESULT.value + ","
				+ _transCustomerInfoXDToMDM_outRETCODE.value;

		// port.transCustomerInfoXDToMDM(_transCustomerInfoXDToMDM_inSYSNAME,
		// _transCustomerInfoXDToMDM_inMASTERTYPE,
		// _transCustomerInfoXDToMDM_inTABLENAME, inFIELDSVALUETABLEList,
		// _transCustomerInfoXDToMDM_outRESULT,
		// _transCustomerInfoXDToMDM_outRETMSG,
		// _transCustomerInfoXDToMDM_outRETCODE);

		// System.out.println("transCustomerInfoXDToMDM._transCustomerInfoXDToMDM_outRESULT="
		// + _transCustomerInfoXDToMDM_outRESULT.value);
		// System.out.println("transCustomerInfoXDToMDM._transCustomerInfoXDToMDM_outRETMSG="
		// + _transCustomerInfoXDToMDM_outRETMSG.value);
		// System.out.println("transCustomerInfoXDToMDM._transCustomerInfoXDToMDM_outRETCODE="
		// + _transCustomerInfoXDToMDM_outRETCODE.value);

	}

	@Override
	public String saveDNS85(String dn1, String shopCode, String manDt) {
		// URL url = new URL(path);
		URL url = this.getClass().getResource(
				wsdlLocation + "/TransWuLiuInfoFromLESToCBSXIN.WSDL");
		ZHIFACUSTOMERPTBindingQSService ss = new ZHIFACUSTOMERPTBindingQSService(
				url);

		ZHIFACUSTOMERPT port = ss.getZHIFACUSTOMERPTBindingQSPort();

		System.out.println("Invoking zhifaCUSTOMER...");

		ZHIFAADDRES zhifaaddres = new ZHIFAADDRES();

		zhifaaddres.setBSTKD(dn1);
		zhifaaddres.setKUNNR(shopCode);
		zhifaaddres.setMANDT(manDt);

		List<ZHIFAADDRES> getItem = new ArrayList<ZHIFAADDRES>();

		getItem.add(zhifaaddres);

		INPUT input = new INPUT();

		input.setItem(getItem);

		ZHIFACUSTOMER _zhifaCUSTOMER_parameters = new ZHIFACUSTOMER();

		_zhifaCUSTOMER_parameters.setINPUT(input);

		ZHIFACUSTOMERRESPONSE _zhifaCUSTOMER__return = port
				.zhifaCUSTOMER(_zhifaCUSTOMER_parameters);
		// System.out.println("zhifaCUSTOMER.result=" +
		// _zhifaCUSTOMER__return.getMESSAGE());

		JSONObject object = (JSONObject) JSONObject
				.toJSON(_zhifaCUSTOMER_parameters);
		vehicleInterfaceLogService.insertLog("物流判断直发单接口入参",
				object.toJSONString());
		object = (JSONObject) JSONObject.toJSON(_zhifaCUSTOMER__return);
		vehicleInterfaceLogService.insertLog("物流判断直发单接口出参",
				object.toJSONString());
		return _zhifaCUSTOMER__return.getFLAG();
	}
	
	@Override
	public ServiceResult<String> getTmallCaMachine(){
		ServiceResult<String> result = tmallCAMachineSerivce.getTmallCaMachine();
		return result;
	}

	@Override
	public void addPurchaseStock(Cn3wPurchaseStock cn3wPurchaseStock) {
		vehicleOrderDetailModel.addPurchaseStock(cn3wPurchaseStock);
	}

	@Override
	public void updateCn3wPurchaseStock(Cn3wPurchaseStock cn3wPurchaseStock) {
		vehicleOrderDetailModel.updateCn3wPurchaseStock(cn3wPurchaseStock);
	}

	@Override
	public List<Cn3wPurchaseStock> queryCn3wPurchaseStock(
			Map<String, Object> map) {
		return vehicleOrderDetailModel.queryCn3wPurchaseStock(map);
	}

	public void addEntry3wOrder(Entry3wOrder entry3wOrder) {
		vehicleOrderDetailModel.addEntry3wOrder(entry3wOrder);
	}

	public void updateEntry3wOrder(Entry3wOrder entry3wOrder) {
		vehicleOrderDetailModel.updateEntry3wOrder(entry3wOrder);
	}

	public List<Entry3wOrder> queryEntry3wOrder(Map<String, Object> map) {
		return vehicleOrderDetailModel.queryEntry3wOrder(map);
	}

	public void updateCn3wPurchaseStockById(Cn3wPurchaseStock cn3wPurchaseStock) {
		vehicleOrderDetailModel.updateEntry3wOrderById(cn3wPurchaseStock);
	}

}
