package com.haier.stock.services;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.haier.common.util.StringUtil;
import com.haier.shop.service.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.haier.eis.model.VomInOutStoreOrder;
import com.haier.eis.service.EisVomInOutStoreOrderService;
import com.haier.shop.model.InvoiceConst;
import com.haier.shop.model.OrderRepairLESRecords;
import com.haier.shop.model.OrderRepairLogs;
import com.haier.shop.model.OrderrepairHPrecordsVO;
import com.haier.shop.model.OrdersVo;
import com.haier.shop.model.VOMOrderResponse;
import com.haier.shop.model.VOMSynOrderRequire;
import com.haier.shop.model.VOMSynSubOrderRequire;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.service.StockInvMachineSetService;
import com.haier.stock.service.VOMPropellingService;
import com.haier.stock.util.HelpUtils;
import com.haier.stock.util.Ustring;


/**
 * 定时推送VOM出入库信息
 * @author wukunyang
 *
 */
//@Configuration
//@EnableScheduling
@Service
public class VOMPropellingServiceImpl implements VOMPropellingService{
	@Autowired
	private ShopOrderRepairLesreCordsService shopOrderRepairLesreCordsService;
	@Autowired
	private VOMOrderModel vomOrderModel;
	@Autowired
	private ShopOrdersService shopOrdersService;//订单
	@Autowired
	private RegionsService regionsService;
	@Autowired
	private StoragesService storagesService;
	@Autowired
	private StockInvMachineSetService stockInvMachineSetService;
	@Autowired
	private HelpUtils helpUtils;
	@Autowired
	private ShopOrderhpRejectionLogsService shopOrderhpRejectionLogsService;
	@Autowired
	private ShopOrderRepairLogsService shopOrderRepairLogsService;//退货日志
	@Autowired
	private EisVomInOutStoreOrderService eisVomInOutStoreOrderService;
	@Autowired
	private ShopOrderRepairsService shopOrderRepairsService;//退货
	@Autowired
	private ShopOrderrepairHPrecordsService hPrecordsService;
    @Autowired
    private OrderRepairLESRecordsService orderRepairLESRecordsService;
	private static org.slf4j.Logger log = LoggerFactory.getLogger(VOMPropellingServiceImpl.class);
	/**
	 * 推送到vom
	 */
//	 @Scheduled(cron="0/5 * *  * * ?")
	public void callVOMProprlling(){
		log.info("推送VOM 定时任务");
		List<OrderRepairLESRecords> repairLESRecords=shopOrderRepairLesreCordsService.queryOutofStorage();
		for(int i=0;i<repairLESRecords.size();i++){
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = new Date();
            OrderRepairLESRecords orderRepairLESRecords = repairLESRecords.get(i);

            List<OrderRepairLESRecords> orderRepairLESRecordsListByRepairId =
                    shopOrderRepairLesreCordsService.queryLesreCodrdsId(orderRepairLESRecords.getOrderRepairId().toString());
            String storeCode = null;
            Map hpRecordIdMap = shopOrdersService.queryMinHpRecordId(orderRepairLESRecords.getOrderRepairId().toString());

            if (hpRecordIdMap == null || hpRecordIdMap.get("id") == null){
                log.info(" 退货单不存在不存在 " + orderRepairLESRecords.getId().toString());
                continue;
            }

            OrdersVo vo = shopOrdersService.queryVOMTransMission(hpRecordIdMap.get("id").toString());
            //第一次推送vom
            if (orderRepairLESRecordsListByRepairId.size() == 1){

                storeCode = storagesService.queryCenterCode(vo.getNetPointCode());

            }else{
                OrderRepairLESRecords orderRepairLESRecordsMinId = orderRepairLESRecordsListByRepairId.get(0);
                VomInOutStoreOrder vomInOutStoreOrder = eisVomInOutStoreOrderService.queryGetStoreCode(
                        orderRepairLESRecordsMinId.getStorageType().toString(),
                        orderRepairLESRecordsMinId.getOperate().toString(),
                        orderRepairLESRecordsMinId.getRecordSn());

                if (vomInOutStoreOrder != null && vomInOutStoreOrder.getStoreCode() != null){
                    storeCode = vomInOutStoreOrder.getStoreCode();

                }

            }

            if(Ustring.isEmpty(storeCode)){
                log.info("库位不存在 " + orderRepairLESRecords.getOrderRepairId());
                continue;
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
                //根据退货单号查询是否是无箱可换的数据
                OrderrepairHPrecordsVO hPrecordsVO=hPrecordsService.queryChangeTheboxUnbox(Ustring.getString(orderRepairLESRecords.getOrderRepairId()));
                 lesId =shopOrderhpRejectionLogsService.quereHpLesId(orderRepairLESRecords.getRepairSn().replace("TH","TC"));//根据退货单号查询38单
                if(hPrecordsVO!=null) {
                    if(Ustring.isEmpty(lesId)) {
                        log.error("未开箱 无箱可换38单号未回传");
                        continue;
                    }
                }
                if (getFiveYard(orderRepairLESRecords, storeCode, synOrderRequire)){
			        continue;
                }
                synOrderRequire.setNetPointCode(null);
            }
			if("CX1".equals(Reorder) || "CX3".equals(Reorder) || "CX5".equals(Reorder)){
				if(Ustring.isNotEmpty(lesId)) {
					 Reorder=lesId;
					 if("22".equals(Ustring.getString(orderRepairLESRecords.getStorageType()))){
						 synOrderRequire.setOrderType("13");
						 synOrderRequire.setBusType("1");//业务类型
					 }
				}else {
					 Reorder=sb+"2";
					 if("22".equals(Ustring.getString(orderRepairLESRecords.getStorageType()))){
						 synOrderRequire.setOrderType("13");
						 synOrderRequire.setBusType("1");//业务类型
					 }
				}
                synOrderRequire.setReorder(Reorder);
			 }else if("CX2".equals(Reorder)|| "CX4".equals(Reorder) || "CX6".equals(Reorder)){
				 Reorder=sb+"1";
				 	if("10".equals(Ustring.getString(orderRepairLESRecords.getStorageType()))){
				 		 synOrderRequire.setOrderType("14");
						 synOrderRequire.setBusType("1");//业务类型
				 	}
                if (getFiveYard(orderRepairLESRecords, storeCode, synOrderRequire)){
                    continue;
                }
                synOrderRequire.setNetPointCode(null);
                synOrderRequire.setReorder(Reorder);
			 }else {
				 Reorder= orderRepairLESRecords.getRecordSn().substring(0, orderRepairLESRecords.getRecordSn().length()-3);
				 synOrderRequire.setOrderType("10");
				 synOrderRequire.setBusType("2");//业务类型
				 synOrderRequire.setRemark("");
                if ("3W".equals(vo.getStockType())){

                    synOrderRequire.setReorder(vo.getTbOrderSn());

                }else{

                    synOrderRequire.setReorder(Reorder);

                }
			 }

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
			 synSubOrderRequire.setReitem(Ustring.getString(repairLESRecords.size()));
			 subOrderList.add(synSubOrderRequire);
			 }
			 synOrderRequire.setSubOrderList(subOrderList);
			 //推送VOM
			 VOMOrderResponse vomOrderResponse =vomOrderModel.synOrderInfo(synOrderRequire); 
			 if("成功".equals(vomOrderResponse.getMsg())){
				 OrderRepairLESRecords records = new OrderRepairLESRecords();
		        	records.setId(orderRepairLESRecords.getId());
		        	records.setFalg("1"); //修改推送状态
		        	shopOrderRepairLesreCordsService.updataRecords(records);
			    	OrderRepairLogs log = new OrderRepairLogs(); //new出来一个退货日志
			    	log.setSiteId(0);
					log.setOrderRepairId(orderRepairLESRecords.getOrderRepairId());//退货id
					log.setOperator("系统");
					log.setOperate("操作");
					log.setRemark("推送VOM物流成功");
					shopOrderRepairLogsService.insert(log);
			 }
		}
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

    public Integer modifyLesOrder(String id, String recordCn){
        Integer result = orderRepairLESRecordsService.updateRepairLesRecordcn(id, recordCn);

        return result;
    }

    public void callVOMProprllingONline(String id){
        log.info("实时推送VOM start");
        List<OrderRepairLESRecords> repairLESRecords=shopOrderRepairLesreCordsService.queryOutofStorageByRepairid(id);
        for(int i=0;i<repairLESRecords.size();i++){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1 = new Date();
            OrderRepairLESRecords orderRepairLESRecords = repairLESRecords.get(i);

            List<OrderRepairLESRecords> orderRepairLESRecordsListByRepairId =
                    shopOrderRepairLesreCordsService.queryLesreCodrdsId(orderRepairLESRecords.getOrderRepairId().toString());
            String storeCode = null;
            Map hpRecordIdMap = shopOrdersService.queryMinHpRecordId(orderRepairLESRecords.getOrderRepairId().toString());

            if (hpRecordIdMap == null || hpRecordIdMap.get("id") == null){
                log.info(" 退货单不存在不存在 " + orderRepairLESRecords.getId().toString());
                continue;
            }

            OrdersVo vo = shopOrdersService.queryVOMTransMission(hpRecordIdMap.get("id").toString());
            //第一次推送vom
            if (orderRepairLESRecordsListByRepairId.size() == 1){

                storeCode = storagesService.queryCenterCode(vo.getNetPointCode());

            }else{
                OrderRepairLESRecords orderRepairLESRecordsMinId = orderRepairLESRecordsListByRepairId.get(0);
                VomInOutStoreOrder vomInOutStoreOrder = eisVomInOutStoreOrderService.queryGetStoreCode(
                        orderRepairLESRecordsMinId.getStorageType().toString(),
                        orderRepairLESRecordsMinId.getOperate().toString(),
                        orderRepairLESRecordsMinId.getRecordSn());

                if (vomInOutStoreOrder != null && vomInOutStoreOrder.getStoreCode() != null){
                    storeCode = vomInOutStoreOrder.getStoreCode();

                }

            }

            if(Ustring.isEmpty(storeCode)){
                log.info("库位不存在 " + orderRepairLESRecords.getOrderRepairId());
                continue;
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
                //根据退货单号查询是否是无箱可换的数据
                OrderrepairHPrecordsVO hPrecordsVO=hPrecordsService.queryChangeTheboxUnbox(Ustring.getString(orderRepairLESRecords.getOrderRepairId()));
                lesId =shopOrderhpRejectionLogsService.quereHpLesId(orderRepairLESRecords.getRepairSn().replace("TH","TC"));//根据退货单号查询38单
                if(hPrecordsVO!=null) {
                    if(Ustring.isEmpty(lesId)) {
                        log.error("未开箱 无箱可换38单号未回传");
                        continue;
                    }
                }
                if (getFiveYard(orderRepairLESRecords, storeCode, synOrderRequire)){
                    continue;
                }
                synOrderRequire.setNetPointCode(null);
            }
            if("CX1".equals(Reorder) || "CX3".equals(Reorder) || "CX5".equals(Reorder)){
                if(Ustring.isNotEmpty(lesId)) {
                    Reorder=lesId;
                    if("22".equals(Ustring.getString(orderRepairLESRecords.getStorageType()))){
                        synOrderRequire.setOrderType("13");
                        synOrderRequire.setBusType("1");//业务类型
                    }
                }else {
                    Reorder=sb+"2";
                    if("22".equals(Ustring.getString(orderRepairLESRecords.getStorageType()))){
                        synOrderRequire.setOrderType("13");
                        synOrderRequire.setBusType("1");//业务类型
                    }
                }
                synOrderRequire.setReorder(Reorder);
            }else if("CX2".equals(Reorder) || "CX4".equals(Reorder) || "CX6".equals(Reorder)){
                Reorder=sb+"1";
                if("10".equals(Ustring.getString(orderRepairLESRecords.getStorageType()))){
                    synOrderRequire.setOrderType("14");
                    synOrderRequire.setBusType("1");//业务类型
                }
                if (getFiveYard(orderRepairLESRecords, storeCode, synOrderRequire)){
                    continue;
                }
                synOrderRequire.setNetPointCode(null);
                synOrderRequire.setReorder(Reorder);
            }else {
                Reorder= orderRepairLESRecords.getRecordSn().substring(0, orderRepairLESRecords.getRecordSn().length()-3);
                synOrderRequire.setOrderType("10");
                synOrderRequire.setBusType("2");//业务类型
                synOrderRequire.setRemark("");
                if ("3W".equals(vo.getStockType())){
                    synOrderRequire.setReorder(vo.getTbOrderSn());

                }else{
                    synOrderRequire.setReorder(Reorder);
                }
            }

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
                synSubOrderRequire.setReitem(Ustring.getString(repairLESRecords.size()));
                subOrderList.add(synSubOrderRequire);
            }
            synOrderRequire.setSubOrderList(subOrderList);
            //推送VOM
            VOMOrderResponse vomOrderResponse =vomOrderModel.synOrderInfo(synOrderRequire);
            if("成功".equals(vomOrderResponse.getMsg())){
                OrderRepairLESRecords records = new OrderRepairLESRecords();
                records.setId(orderRepairLESRecords.getId());
                records.setFalg("1"); //修改推送状态
                shopOrderRepairLesreCordsService.updataRecords(records);
                OrderRepairLogs log = new OrderRepairLogs(); //new出来一个退货日志
                log.setSiteId(0);
                log.setOrderRepairId(orderRepairLESRecords.getOrderRepairId());//退货id
                log.setOperator("系统");
                log.setOperate("操作");
                log.setRemark("推送VOM物流成功");
                shopOrderRepairLogsService.insert(log);
            }
        }
    }
	
	//查询入10和入41的出入库信息  判断vom是否已经返回入库流水  如果已经回传 就更改货物状态
//	 @Scheduled(cron="0/5 * *  * * ?") 
	public void stateOfgoods() {
        log.info("已开箱正品物流状态更新 定时任务 start");
		List<OrderRepairLESRecords> repairLESRecords=shopOrderRepairLesreCordsService.queryStorageType();
		VomInOutStoreOrder vomInOutStoreOrder = null;
		for(OrderRepairLESRecords records : repairLESRecords) {
			//根据出入库单号查询出入库明细表来判断VOM是否已经把出入库流水返回到CBS
			vomInOutStoreOrder = eisVomInOutStoreOrderService.queryGetStoreCode(records.getStorageType().toString(),records.getOperate().toString(),records.getRecordSn());//根据退货单好查询 vom返回的C码
//			vomInOutStoreOrder = eisVomInOutStoreOrderService.queryVomInOut(records.getRecordSn());
			//如果VOM已经把流水返回到CBS 则更改退货表的货物状态
			if(vomInOutStoreOrder!=null) {
				if("10".equals(Ustring.getString(records.getStorageType()))) {
					//更改货物状态改成入10
					shopOrderRepairsService.updataOrderRepairsStatus("", "110", Ustring.getString(records.getOrderRepairId()));
                    shopOrderRepairsService.updataStatus(Ustring.getString(records.getOrderRepairId()), "3", "已入库10，退货单关闭");

                    OrderRepairLogs log = new OrderRepairLogs(); //new出来一个退货日志
                    log.setSiteId(0);
                    log.setOrderRepairId(records.getOrderRepairId());//退货id
                    log.setOperator("系统");
                    log.setOperate("操作");
                    log.setRemark("已入库10，退货单关闭");
                    shopOrderRepairLogsService.insert(log);
				} else if("41".equals(Ustring.getString(records.getStorageType()))) {
					 //更改发票和货物状态改成入41
					 shopOrderRepairsService.updataOrderRepairsStatus("", "141", Ustring.getString(records.getOrderRepairId()));
                    shopOrderRepairsService.updataStatus(Ustring.getString(records.getOrderRepairId()), "3", "已入库41，退货单关闭");
                    OrderRepairLogs log = new OrderRepairLogs(); //new出来一个退货日志
                    log.setSiteId(0);
                    log.setOrderRepairId(records.getOrderRepairId());//退货id
                    log.setOperator("系统");
                    log.setOperate("操作");
                    log.setRemark("已入库41，退货单关闭");
                    shopOrderRepairLogsService.insert(log);
				}
				
			}
		}
		
		
	}

	public void updateRepairOrderLesAndOutPing(){
	    log.warn("updateRepairOrderLesAndOutPing start");
        List<OrderRepairLESRecords> orderRepairNotLESRecordList = shopOrderRepairLesreCordsService.queryNotLesOrder();

        if (orderRepairNotLESRecordList != null){
            for (int i = 0; i < orderRepairNotLESRecordList.size(); i++){
                OrderRepairLESRecords orderRepairLESRecords = orderRepairNotLESRecordList.get(i);
                VomInOutStoreOrder vomInOutStoreOrder = eisVomInOutStoreOrderService.getByStockInfoByOrderNo(orderRepairLESRecords.getRecordSn());
                if (vomInOutStoreOrder != null && StringUtils.isNotBlank(vomInOutStoreOrder.getExpNo())){
                    OrderRepairLESRecords lesRecords = new OrderRepairLESRecords();
                    lesRecords.setId(orderRepairLESRecords.getId());
                    lesRecords.setLesOrderSn(vomInOutStoreOrder.getExpNo());
                    lesRecords.setLesOrderSnTime(vomInOutStoreOrder.getOutInDate().getTime()/1000);
                    lesRecords.setLesOutPing(vomInOutStoreOrder.getCertification());
                    lesRecords.setLesOutPingTime(vomInOutStoreOrder.getAddTime().getTime()/1000);

                    orderRepairLESRecordsService.updateAfterVomAccepted(lesRecords);
                    orderRepairLESRecordsService.updateLesRecordAfterJLIN(lesRecords);

                }

            }

        }
        log.warn("updateRepairOrderLesAndOutPing end");

    }

}