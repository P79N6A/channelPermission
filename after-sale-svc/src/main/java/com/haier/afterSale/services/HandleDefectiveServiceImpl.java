package com.haier.afterSale.services;

import com.haier.afterSale.eai.TransferGoodsInfoToEhaierSAP.*;
import com.haier.afterSale.model.PushReturnInfoToGVSHandler;
import com.haier.afterSale.model.Ustring;
import com.haier.afterSale.service.HandleDefectiveService;
import com.haier.afterSale.util.OrderSnUtil;
import com.haier.afterSale.webService.pushHP.InsertDataToHP;
import com.haier.afterSale.webService.pushHP.InsertDataToHP_Service;
import com.haier.afterSale.webService.pushHP.InsertDataToHP_Type;
import com.haier.common.ServiceResult;
import com.haier.common.util.DateUtil;
import com.haier.common.util.JsonUtil;
import com.haier.common.util.StringUtil;
import com.haier.dbconfig.model.Result;
import com.haier.eis.model.EisInterfaceDataLog;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.model.VomInOutStoreOrder;
import com.haier.eis.service.*;
import com.haier.eop.data.model.StoreCodeMapping;
import com.haier.eop.data.service.StoreCodeService;
import com.haier.shop.model.*;
import com.haier.shop.service.AllocateDefectLogsService;
import com.haier.shop.service.OmsInStoreRecordService;
import com.haier.shop.service.ShopOrderRepairLesreCordsService;
import com.haier.shop.service.StoragesService;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.model.InvTransferLine;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.service.StockInvMachineSetService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.taobao.pac.sdk.cp.PacClient;
import com.taobao.pac.sdk.cp.SendSysParams;
import com.taobao.pac.sdk.cp.dataobject.request.ERP_ORDER_CANCEL_NOTIFY.ErpOrderCancelNotifyRequest;
import com.taobao.pac.sdk.cp.dataobject.request.ERP_STOCK_OUT_ORDER_NOTIFY.ErpStockOutOrderNotifyRequest;
import com.taobao.pac.sdk.cp.dataobject.request.ERP_STOCK_OUT_ORDER_NOTIFY.ReceiverInfo;
import com.taobao.pac.sdk.cp.dataobject.request.ERP_STOCK_OUT_ORDER_NOTIFY.SenderInfo;
import com.taobao.pac.sdk.cp.dataobject.request.ERP_STOCK_OUT_ORDER_NOTIFY.StockOutOrderItem;
import com.taobao.pac.sdk.cp.dataobject.response.ERP_ORDER_CANCEL_NOTIFY.ErpOrderCancelNotifyResponse;
import com.taobao.pac.sdk.cp.dataobject.response.ERP_STOCK_OUT_ORDER_NOTIFY.ErpStockOutOrderNotifyResponse;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.jws.WebParam;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//@Configuration
//@EnableScheduling
@Service
public class HandleDefectiveServiceImpl implements HandleDefectiveService {

    private static final QName SERVICE_NAME = new QName("http://www.example.org/InsertDataToHP/", "InsertDataToHP");
    private static Logger log = LoggerFactory.getLogger(HandleDefectiveServiceImpl.class);
    @Autowired
    private OmsReceivedQueueDataService omsReceivedQueueDataService;
    @Autowired
    private OmsInStoreRecordService omsInStoreRecordService;
    @Autowired
    private AllocateDefectLogsService allocateDefectLogsService;

    @Autowired
    private StoreCodeService storeCodeService;
    @Autowired
    private ShopOrderRepairLesreCordsService shopOrderRepairLesreCordsService;
    @Autowired
    private StockInvMachineSetService stockInvMachineSetService;
    @Autowired
    private VOM3WOrderModel vOM3WOrderModel;
    @Autowired
    private EisVomInOutStoreOrderService eisVomInOutStoreOrderService;
    @Autowired
    private PushReturnInfoToGVSHandler toGVSHandler;
    @Autowired
    private LesStockTransQueueService lesStockTransQueueService;
    @Autowired
    protected EisInterfaceDataLogService eisInterfaceDataLogDao;

    @Autowired
    private StoragesService storagesService;
    @Value("${wsdlPath}")
    private String wsdlLocation;

    protected String                          interfaceCode = "";
    private Integer                           eaiDataLogId;

    /*
     * 发起二次质检
     * */
//    @Scheduled(cron = "*/10 * * * * ?")
    public void InitiateQualityInspection() throws ParseException, MalformedURLException {
        java.util.List<InsertDataToHP_Type.Inputs> _insertDataToHP_inputs = new ArrayList<>();
        javax.xml.ws.Holder<java.lang.String> _insertDataToHP_flag = new javax.xml.ws.Holder<java.lang.String>();
        javax.xml.ws.Holder<java.lang.String> _insertDataToHP_msg = new javax.xml.ws.Holder<java.lang.String>();
        URL url =  this.getClass().getResource(wsdlLocation+"/InsertDataToHP.wsdl");
//        URL url = new URL(path);
        InsertDataToHP_Service ss = new InsertDataToHP_Service(url);
        InsertDataToHP port = ss.getInsertDataToHPSOAP();
        List<OmsInStoreRecords> omsInStoreRecords = omsInStoreRecordService.selectByStatus();
        if (omsInStoreRecords.size() == 0) {
            log.info("没有需要推送HP发起质检的数据");
        }
        for (OmsInStoreRecords omsInStoreRecords1 : omsInStoreRecords) {
            //物流单号校验，只有物流传输TBD（调拨）和THJ（转运）才能流转处理，其余不予处理
            if(omsInStoreRecords1.getPurchaseOrderCode().contains("TBD") || omsInStoreRecords1.getPurchaseOrderCode().contains("THJ")) {
                if (!omsInStoreRecords1.getGoodsCode().substring(0, 2).equals("AA")&&!omsInStoreRecords1.getGoodsCode().substring(0, 2).equals("AB")) {
                    if (Ustring.isNotEmpty(omsInStoreRecords1.getDb())) {
                        InsertDataToHP_Type.Inputs input = new InsertDataToHP_Type.Inputs();
                        input.setOrderNo(omsInStoreRecords1.getDb()+"TC");
                        input.setCounts(new BigDecimal(1));
                        input.setCreatedDate(dateToXmlDate(new Date()));
                        input.setProdtypeId(omsInStoreRecords1.getGoodsCode());
                        input.setRequestServiceRemark("");
                        input.setRequestServiceDate(dateToXmlDate(getDate()));
                        input.setOldOrder(omsInStoreRecords1.getPurchaseOrderCode());
                        input.setIfEcjd("2");
                        input.setTypes("D1");
                        //用来标示新老系统
                        input.setAttribute6("NSC");
                        input.setProductNo(omsInStoreRecords1.getSnCode());
                        input.setTcCode(omsInStoreRecords1.getStorageLocation());
                        _insertDataToHP_inputs.add(input);
                        allocateDefectLogsService.insertLog(omsInStoreRecords1.getId(), "推送HP二次质检");//推送hp插入操作日志
                    }
                }
            }
}
        try {
            port.insertDataToHP(_insertDataToHP_inputs, _insertDataToHP_flag, _insertDataToHP_msg);
            List<String> dblist = new ArrayList<>();
            List<String> notdblist = new ArrayList<>();
            if (_insertDataToHP_flag.value.equals("S")) {
//                log.info("发送HP质检完成");
                for (OmsInStoreRecords omsInStoreRecords1 : omsInStoreRecords) {
                    if(omsInStoreRecords1.getPurchaseOrderCode().contains("TBD") || omsInStoreRecords1.getPurchaseOrderCode().contains("THJ")) {
                        if (!omsInStoreRecords1.getGoodsCode().substring(0, 2).equals("AA")&&!omsInStoreRecords1.getGoodsCode().substring(0, 2).equals("AB")) {
                            if (Ustring.isNotEmpty(omsInStoreRecords1.getDb())) {
                                dblist.add(omsInStoreRecords1.getDb());
                            allocateDefectLogsService.insertLog(omsInStoreRecords1.getId(), "推送HP二次质检成功");//推送hp插入操作日志
                            }
                        }    else{//空调暂不处理
                            notdblist.add(omsInStoreRecords1.getDb());
                        }

                    }
                    else{//不符合tbd或tbz加入list
                        notdblist.add(omsInStoreRecords1.getDb());
                    }
                }
            }
            if(dblist.size() !=0) {
                omsInStoreRecordService.updateByDb(dblist);
            }
            if(notdblist.size() != 0) {
                omsInStoreRecordService.updateNotDb(notdblist);//不符合status改成2
            }

        } catch (Exception e) {
            log.error("发送质检失败");
        }
    }

    /*
     *
     * 定时处理调拨残次队列表里面的数据拆分成具体内容
     * */
//     @Scheduled(cron = "*/10 * * * * ?")
    public void treatmentforDefective() {
        Document doc = null;
        try {
            //查询调拨残次品队列里面状态为0的数据
            List<Map<String, Object>> list = omsReceivedQueueDataService.select();
            if (list.size() == 0) {
                log.info("暂时没有待处理的数据");
                return;
            }
            List<OmsInStoreRecords> allList = new ArrayList<>();
            //更新oms_received_queue表的状态和处理时间
            List<Map<String, Object>> updateList = new ArrayList<>();
            //遍历待处理的队列;
            for (Map map : list) {
                Map<String, Object> updateMap = new HashMap<>();
                updateList.add(updateMap);
                Integer update_id = Integer.parseInt(map.get("id").toString());//为了更新队列的状态值
                updateMap.put("id", update_id);
                updateMap.put("handleTime", getTime());
                String xmlString = (String) map.get("content");
                try {
                    doc = DocumentHelper.parseText(xmlString);
                    List<OmsInStoreRecords> omsInStoreRecords = parseDoc(doc, update_id);
                    allList.addAll(omsInStoreRecords);
                } catch (Exception w) {
                    log.error("xml转换Document对象失败 :" + w);
                }
            }
            try {
                //更新oms_received_queue表中已经被处理的数据
                omsReceivedQueueDataService.update(updateList);

            } catch (Exception e) {
                log.error("更新oms_received_queue表的状态和处理时间失败 " + e);
            }
            try {
                List<String> rowidList = new ArrayList<>();
                for (OmsInStoreRecords omsInStoreRecords : allList) {
                    rowidList.add(omsInStoreRecords.getRowId());
                }
                //查询
                List<String> rowIdBatchList = omsInStoreRecordService.selectRowId(rowidList);
                //全部的减去数据库已经存在的等于需要插入的
                rowidList.removeAll(rowIdBatchList);
                List<OmsInStoreRecords> insertList = new ArrayList<>();
                for (OmsInStoreRecords omsInStoreRecords : allList) {
                    String rowid = omsInStoreRecords.getRowId();
                    for (String string : rowidList) {//这些是需要的
                        if (string.equals(rowid)) {
                            insertList.add(omsInStoreRecords);
                        }
                    }
                }
                if (insertList.size() == 0) {
                    log.info("没有需要插入到oms_in_store_records表的数据");
                    return;
                }
                //插入前再次判断有没有重复的主键
                Set<String> row_idlist = new HashSet<>();
                for (OmsInStoreRecords omsInStoreRecords : insertList) {
                    row_idlist.add(omsInStoreRecords.getRowId());
                }
                List<OmsInStoreRecords> insert_List = new ArrayList<>();
                ok:
                for (String string : row_idlist) {
                    for (OmsInStoreRecords omsInStoreRecords : insertList) {
                        if (omsInStoreRecords.getRowId().equals(string)) {
                            insert_List.add(omsInStoreRecords);
                            continue ok;
                        }
                    }
                }
                //更新oms_received_queue表中已经被处理的数据
                List<OmsInStoreRecords> list_db = omsInStoreRecordService.insert(insert_List);
                for (OmsInStoreRecords oms:list_db){
                    String DB = OrderSnUtil.getDbSn(oms.getId(),new Date());
                    oms.setDb(DB);
                }
                omsInStoreRecordService.updateDb(list_db);//批量更新db单号
                log.info("执行完毕");
                return;
            } catch (Exception e) {
                log.error("插入oms_in_store_records表失败 ", e);
            }
        } catch (Exception e) {
            log.error("查询队列数据时发生异常 :", e);
        }
    }

    //解析xml
    public static List<OmsInStoreRecords> parseDoc(Document doc, Integer update_id) {
        List<OmsInStoreRecords> list = new ArrayList<>();
        Element rootElt = doc.getRootElement();
        List<org.dom4j.Element> firstChild = rootElt.elements();
        for (org.dom4j.Element element : firstChild) {
            List<org.dom4j.Element> rowChild = element.elements();
            OmsInStoreRecords omsInStoreRecords = new OmsInStoreRecords();
            for (Element elem : rowChild) {
                if (elem.getName().equals("materialCertification")) {
                    omsInStoreRecords.setMaterialCertification(elem.getStringValue());
                }
                if (elem.getName().equals("certificationItem")) {
                    omsInStoreRecords.setCertificationItem(elem.getStringValue());
                }
                if (elem.getName().equals("snCode")) {
                    omsInStoreRecords.setSnCode(elem.getStringValue());
                }
                if (elem.getName().equals("purchaseOrderCode")) {
                    omsInStoreRecords.setPurchaseOrderCode(elem.getStringValue());
                }
                if (elem.getName().equals("sourceSn")) {
                    omsInStoreRecords.setSourceSn(elem.getStringValue());
                }
                if (elem.getName().equals("storageLocation")) {
                    omsInStoreRecords.setStorageLocation(elem.getStringValue());
                }
                if (elem.getName().equals("batch")) {
                    omsInStoreRecords.setBatch(elem.getStringValue());
                }
                if (elem.getName().equals("goodsCode")) {
                    omsInStoreRecords.setGoodsCode(elem.getStringValue());
                }
                if (elem.getName().equals("billCnt")) {
                    omsInStoreRecords.setBillCnt(elem.getStringValue());
                }
                if (elem.getName().equals("orderType")) {
                    omsInStoreRecords.setOrderType(elem.getStringValue());
                }
                if (elem.getName().equals("returnSourceOrder")) {
                    omsInStoreRecords.setReturnSourceOrder(elem.getStringValue());
                }
                if (elem.getName().equals("returnSourceSn")) {
                    omsInStoreRecords.setReturnSourceSn(elem.getStringValue());
                }
                if (elem.getName().equals("orderSourceCode")) {
                    omsInStoreRecords.setOrderSourceCode(elem.getStringValue());
                }
                if (elem.getName().equals("orderNo")) {
                    omsInStoreRecords.setOrderNo(elem.getStringValue());
                }
                if (elem.getName().equals("id")) {
                    omsInStoreRecords.setRowId(elem.getStringValue());
                }
                if (elem.getName().equals("itemId")) {
                    omsInStoreRecords.setItemId(elem.getStringValue());
                }
                omsInStoreRecords.setAddTime(getTime());
                omsInStoreRecords.setStatus(0);
            }
            //DB号生成规则是DB加13位时间戳
//            omsInStoreRecords.setDb("DB" + new Date().getTime() + getMath());
            list.add(omsInStoreRecords);
        }
        return list;
    }

    /*
     * 定时下退仓
     * */
//    @Scheduled(cron="0/5 * *  * * ?")
    public void returnWarehouse() throws ParseException {
        List<OmsInStoreRecords> list = omsInStoreRecordService.selectForStatus();//查询是否换箱完成的数据
        for (OmsInStoreRecords oms : list) {
            if (!oms.getGoodsCode().substring(0, 2).equals("AA")&&!oms.getGoodsCode().substring(0, 2).equals("AB")) {
            if (("1").equals(oms.getADD2()) && ("3").equals(oms.getADD1())) {//换箱完成
                ErpStockOutOrderNotifyRequest request = new ErpStockOutOrderNotifyRequest();
                //商品信息列表
                List<StockOutOrderItem> stockOutOrderItems = new ArrayList<StockOutOrderItem>();
                StockOutOrderItem stockOutOrderItem = new StockOutOrderItem();
                stockOutOrderItem.setOwnerUserId("2998123754");
                stockOutOrderItem.setItemCode(oms.getGoodsCode());
                // stockOutOrderItem.setItemCode("BB0A4008V");
                //stockOutOrderItem.setItemName("婉扬测试专用商品请勿动");
                stockOutOrderItem.setInventoryType(105);
                stockOutOrderItem.setItemId(oms.getItemId());
                stockOutOrderItem.setItemQuantity(1);
                //stockOutOrderItem.setItemQuantity(1);
                stockOutOrderItems.add(stockOutOrderItem);

                request.setOrderItemList(stockOutOrderItems);
                //收货信息
                ReceiverInfo receiverInfo = new ReceiverInfo();
                receiverInfo.setReceiverProvince("山东省");
                receiverInfo.setReceiverCity("青岛市");
                receiverInfo.setReceiverArea("崂山区");
                receiverInfo.setReceiverAddress("海尔路1号");
                receiverInfo.setReceiverName("海尔网点");
                request.setRemark(oms.getDb() + "CX2;机编：" + oms.getSnCode());
//	      request.setRemark("DB1523259702050821664CX2;机编：TBZ17122305481243TB008211290");
                request.setReceiverInfo(receiverInfo);
                //发货信息
                SenderInfo senderInfo = new SenderInfo();
                senderInfo.setSenderProvince("浙江省");
                senderInfo.setSenderCity("杭州市");
                senderInfo.setSenderArea("西湖区");
                senderInfo.setSenderAddress("发件人地址");
                senderInfo.setSenderName("发件人");
                request.setSenderInfo(senderInfo);

                request.setOwnerUserId("2998123754");
                //根据海尔仓库编码找到菜鸟仓库编码
                StoragesWwwRelas storagesWwwRelas = storagesService.findForGbCode(oms.getStorageLocation());
                request.setStoreCode(storagesWwwRelas.getCainiaocode());

                request.setOrderType(901); //普通出库
                request.setOrderSource(13);
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date createTime = new Date();

                request.setOrderCreateTime(createTime);
//	      request.setOrderCode("DB1523259702050821666");
                request.setOrderCode(oms.getDb()+"TC");
//	      Map<String, String> extendFields = new HashMap<String, String>();
	    /*  extendFields.put("biz_order_code", "8558492274");
	      request.setExtendFields(extendFields);*/


                //LINK 方式接入 生产环境
                String appKey = "009658";
                String fromCode = "2998123754";
                String secretKey = "Vi3z88ZEy5j9W28s4JA0O3X29r159S06";
                String pacUrl = "http://link.cainiao.com/gateway/link.do";
                PacClient pacClient = new PacClient(appKey, secretKey, pacUrl);
                SendSysParams sysParams = new SendSysParams();
                sysParams.setFromCode(fromCode);
//	      Object o = new Object();

                ErpStockOutOrderNotifyResponse linkResponse = pacClient.send(request, sysParams);
//            System.out.println(linkResponse);
                String orderCode = linkResponse.getOrderCode();//取得菜鸟返回的lbx号
                if (orderCode != null) {
                    oms.setOrderCode(orderCode);
                    omsInStoreRecordService.updateForOrderCode(oms);
                }
            }
        }
        }
    }

    /**
     * 定时推送vom
     */
//        @Scheduled(cron="0/5 * *  * * ?")
    public void pushVom() {
        List<OmsInStoreRecords> list = omsInStoreRecordService.selectByVomStatus();//查询没有推vom的数据
        for (OmsInStoreRecords oms : list) {
            //生成入库信息
            if (!oms.getGoodsCode().substring(0, 2).equals("AA")&&!oms.getGoodsCode().substring(0, 2).equals("AB")) {
            if (oms.getOrderCode() != null && !"".equals(oms.getOrderCode())) {//下退仓成功才能继续下出库单
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = new Date();
                VOMSynOrderRequire synOrderRequire = new VOMSynOrderRequire();
                String vomSn = oms.getDb() + "TC";
                synOrderRequire.setOrderNo(vomSn);
                synOrderRequire.setSourceSn(oms.getSourceSn());
                synOrderRequire.setOrderDate(dateFormat.format(date1));
                String code = storagesService.getBywwwCode(oms.getStorageLocation());//根据3w码查找c码
                synOrderRequire.setStoreCode(code);
                //根据3w码查找国标码和详细地址
                StoragesWwwRelas storagesWwwRelas = storagesService.findForGbCode(oms.getStorageLocation());
                synOrderRequire.setGbCode(storagesWwwRelas.getGcode());//国标码
                synOrderRequire.setAddr(storagesWwwRelas.getAddress());//详细地址
                synOrderRequire.setName("3W海尔正品退仓");
                synOrderRequire.setMobile("13373011505");
                synOrderRequire.setReorder(oms.getOrderCode());
                synOrderRequire.setOrderType("1");
                synOrderRequire.setBusType("1");//业务类型
                synOrderRequire.setBusFlag("2");

            /*synOrderRequire.setTel(vo.getPhone());
            synOrderRequire.setPostCode(vo.getZipcode());
            synOrderRequire.setPayState(vo.getPaymentStatus().toString().equals("1")?"P1":"P2");
            synOrderRequire.setPayTime(vo.getPayTimeStr());
            synOrderRequire.setPayType(vo.getPaymentCode());
            synOrderRequire.setInvRise(vo.getInvoiceTitle());
            synOrderRequire.setIsInv(vo.getIsReceipt());
            synOrderRequire.setInvType(InvoiceConst.INVOICE_TYPE.get(vo.getType()));
            synOrderRequire.setTaxBearer(vo.getTaxPayerNumber());
            synOrderRequire.setRecAddr(vo.getRegisterAddressAndPhone());
            synOrderRequire.setRemark(repairLESRecords.get(i).getRepairSn());*/

        /*    StringBuffer sb = new StringBuffer(repairLESRecords.get(i).getRecordSn());
            sb.deleteCharAt(sb.length()-3);
            String Reorder =repairLESRecords.get(i).getRecordSn().substring(repairLESRecords.get(i).getRecordSn().length()-3, repairLESRecords.get(i).getRecordSn().length());
            String lesId =shopOrderhpRejectionLogsService.quereHpLesId(repairLESRecords.get(i).getRepairSn()+"TC");//根据退货单号查询38单
            if("CX1".equals(Reorder)){
                if(Ustring.isNotEmpty(lesId)) {
                    Reorder=lesId;
                    if("".equals(repairLESRecords.get(i).getStorageType())){
                        synOrderRequire.setOrderType("13");
                        synOrderRequire.setBusType("1");//业务类型
                    }
                }else {
                    Reorder=sb+"CX2";
                    if("".equals(repairLESRecords.get(i).getStorageType())){
                        synOrderRequire.setOrderType("13");
                        synOrderRequire.setBusType("1");//业务类型
                    }
                }
            }else if("CX2".equals(Reorder)){
                Reorder=sb+"CX1";
                if("".equals(repairLESRecords.get(i).getStorageType())){
                    synOrderRequire.setOrderType("14");
                    synOrderRequire.setBusType("1");//业务类型
                }
            }else {
                Reorder= repairLESRecords.get(i).getRecordSn().substring(0,repairLESRecords.get(i).getRecordSn().length()-3);
                synOrderRequire.setOrderType("10");
                synOrderRequire.setBusType("2");//业务类型
            }
            synOrderRequire.setReorder(Reorder);
            synOrderRequire.setRecBank(vo.getBankNameAndAccount());
            synOrderRequire.setFreight(vo.getShippingAmount().doubleValue());
            synOrderRequire.setBillSum(vo.getOrderAmount().doubleValue());*/
                VOMSynSubOrderRequire synSubOrderRequire = null;
                //判断是不是套机
//            List<InvMachineSet> machineSets=stockInvMachineSetService.getByMainSku(repairLESRecords.get(i).getSku());
                List<VOMSynSubOrderRequire> subOrderList = new ArrayList<VOMSynSubOrderRequire>();
            /*if(machineSets.size()>0){//如果是套机
                for(int j=0;j<machineSets.size();j++){
                    synSubOrderRequire = new VOMSynSubOrderRequire();
                    synSubOrderRequire.setProductCode(machineSets.get(i).getSubSku());//子sku
                    synSubOrderRequire.setItemNo(Ustring.getString(j));//行号
                    synSubOrderRequire.setStorageType(Ustring.getString(repairLESRecords.get(i).getStorageType()));
                    synSubOrderRequire.setHrCode(machineSets.get(i).getSubSku());
                    synSubOrderRequire.setProdes(machineSets.get(i).getMaktx2());
                    synSubOrderRequire.setNumber(repairLESRecords.get(i).getNumber());
                    synSubOrderRequire.setUnprice(repairLESRecords.get(i).getPrice());
                    synSubOrderRequire.setReitem(Ustring.getString(machineSets.size()));
                    subOrderList.add(synSubOrderRequire);
                }
            }else {*/

                synSubOrderRequire = new VOMSynSubOrderRequire();
                //套机列表查询
//                String sku= helpUtils.getProductCode(repairLESRecords.get(i).getId(),repairLESRecords.get(i).getSku(),repairLESRecords.get(i).getPushFailNumber());
                synSubOrderRequire.setProductCode(oms.getGoodsCode());//物料号
                synSubOrderRequire.setHrCode(oms.getGoodsCode());//物料号
                //处理行号0001 0002 为1 和 2
                String itemNo = oms.getCertificationItem().substring(3);
                synSubOrderRequire.setItemNo(itemNo);
                synSubOrderRequire.setStorageType("10");
                synSubOrderRequire.setProdes(oms.getSnCode());
                synSubOrderRequire.setNumber(1);
//                synSubOrderRequire.setUnprice(repairLESRecords.get(i).getPrice());
//                synSubOrderRequire.setReitem(Ustring.getString(repairLESRecords.size()));
                subOrderList.add(synSubOrderRequire);
//            }
                synOrderRequire.setSubOrderList(subOrderList);
                //推送VOM
                allocateDefectLogsService.insertLog(oms.getId(), "推送VOM信息");

                VOMOrderResponse vomOrderResponse = vOM3WOrderModel.synOrderInfo(synOrderRequire);
                log.info("推送VOM返回信息:Msg:" + vomOrderResponse.getMsg() + "Flag:" + vomOrderResponse.getFlag());
                if ("成功".equals(vomOrderResponse.getMsg())) {
                    allocateDefectLogsService.insertLog(oms.getId(), "推送VOM成功");

                    oms.setVomSn(vomSn);
                    omsInStoreRecordService.updataVomStatus(oms);
                } else {
                    allocateDefectLogsService.insertLog(oms.getId(), "推送VOM失败");

                }

            }
        }
        }
    }

    /**
     * 取消调拨单
     */
//    @Scheduled(cron="0/5 * *  * * ?")
    public void cannelVom() {
        VOMCancelOrderRequire vomCancelOrderRequire = new VOMCancelOrderRequire();
        vomCancelOrderRequire.setOrderNo("DB18053000000303TC");
        vomCancelOrderRequire.setCancelType("1");
        VOMOrderResponse vomOrderResponse  = vOM3WOrderModel.cancelOrderInfo(vomCancelOrderRequire);
        log.info("推送VOM返回信息:Msg:" + vomOrderResponse.getMsg() + "Flag:" + vomOrderResponse.getFlag());

    }

    /**
     * 推送出库sqp
     * @throws MalformedURLException
     */
//    		@Scheduled(cron="0/5 * *  * * ?")
    public void pushOutSAP() throws MalformedURLException {
        log.info(" 推送出库SAP");
        //查询
         List<OmsInStoreRecords> list = omsInStoreRecordService.findForVomStatus();//查询推送vom成功,没有推送出库sap的
        if(list.size()>0) {
            for (OmsInStoreRecords oms : list) {
                LesStockTransQueue lesStockTransQueue = lesStockTransQueueService.findInStockCode(oms.getVomSn());
                if (lesStockTransQueue != null) {
                    Result rs = new Result();
                    ObjectFactory objectFactory = new ObjectFactory();
                    ZMMS0010 request = objectFactory.createZMMS0010();
                    String sku = oms.getGoodsCode();
                    request.setZCBSN(oms.getDb()+"TC");
                    request.setMATNR(sku);
                    request.setZLGORTI(lesStockTransQueue.getSecCode());//入库位编码
                    request.setZLGORTO(oms.getStorageLocation());// 出库位编码
                    //截取lbx单号为出库单号
                    String lbx = oms.getOrderCode();
                    lbx = lbx.substring(lbx.length() - 15, lbx.length());
                    request.setZLSOT(lbx);// les出库单号
                String zeile = oms.getCertificationItem();
                String seqNum= "";
                if (StringUtil.isEmpty(zeile) || zeile.length() <= 3)
                    seqNum = zeile;
                else
                    seqNum = zeile.substring(zeile.length() - 3, zeile.length());
                    request.setZLSOI(seqNum);//行项目号
                    String addtime = oms.getAddTime().substring(0, 10);
                    request.setBLDAT(addtime);//出库确认时间
                    String date = DateUtil.format(new Date(), "yyyy-MM-dd");

                    request.setBUDAT(date);//当前时间


                    request.setMENGE(new BigDecimal(1));


                    TransferGoodsInfoToEhaierSAP soap = new TransferGoodsInfoToEhaierSAP_Service(
                            this.getClass().getResource(wsdlLocation + "/TransferGoodsInfoToEhaierSAP.wsdl")).getTransferGoodsInfoToEhaierSAPSOAP();

                    List<ZMMS0010> tZMMS0010 = new ArrayList<ZMMS0010>();
                    tZMMS0010.add(request);
                    // 要记录接口数据日志
                    EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
                    dataLog.setForeignKey(oms.getDb()+"TC");
                    dataLog.setInterfaceCode("TransferGoodsInfoToEhaierSAP");
                    dataLog.setRequestTime(DateUtil.currentDateTime());
                    dataLog.setRequestData(JsonUtil.toJson(tZMMS0010));
                    Long startTime = System.currentTimeMillis();
                    try {
                        Holder<List<ZSDS0002>> tMSG = new Holder<List<ZSDS0002>>();
                        Holder<Integer> exSUBRC = new Holder<Integer>();
                        allocateDefectLogsService.insertLog(oms.getId(),"推送出库SAP信息");
                        soap.transferGoodsInfoToEhaierSAP(tZMMS0010, exSUBRC, tMSG);
                        String msg = JsonUtil.toJson(tMSG.value);
                        dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
                        dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
                        dataLog.setResponseTime(DateUtil.currentDateTime());
                        dataLog.setResponseData(msg);
                        if (tMSG.value == null || tMSG.value.size() <= 0) {
                            rs.setStatus(false);
                            rs.setMessage("EAI 返回空");
                            dataLog.setResponseData("");
                        } else {
                            boolean flag = true;
                            for (ZSDS0002 zsds0002 : tMSG.value) {
                                if (!flag)
                                    break;
                                flag = !"E".equalsIgnoreCase(zsds0002.getTYPE());
                            }
                     /*       if (flag) {
                                // 更新状态已推送SAP
                                omsInStoreRecordService.updateOutSapStatus(oms);
                            }*/
                            rs.setStatus(flag);
                            rs.setMessage(msg);
                        }
                    } catch (Exception e) {
                        dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
                        dataLog.setResponseTime(DateUtil.currentDateTime());
                        dataLog.setResponseData("");
                        dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
                        dataLog.setErrorMessage(e.getMessage());
                        rs.setStatus(false);
                        rs.setMessage("调用EAI接口失败");
                        log.error("调用EAI接口 TransferGoodsInfoToEhaierSAP 失败：", e);
                    }
                    dataLog.setCreateTime(DateUtil.currentDateTime());
                    recordEisInterfaceDataLog(dataLog);
                    if(rs.getStatus()){
                        allocateDefectLogsService.insertLog(oms.getId(),"推送出库SAP成功");
                        omsInStoreRecordService.updateOutSapStatus(oms);//推送出库sap成功修改状态
                    }
                    else{
                        allocateDefectLogsService.insertLog(oms.getId(),"推送出库SAP失败");
                    }

                }
            }
        }
    }

    protected void recordEisInterfaceDataLog(EisInterfaceDataLog datalog) {
        try {
            if (StringUtil.isEmpty(datalog.getInterfaceCode()))
                datalog.setInterfaceCode(interfaceCode);
            eisInterfaceDataLogDao.insert(datalog);
            eaiDataLogId = datalog.getId();
        } catch (Exception e) {
            log.error("记录EisInterfaceDataLog出错：", e);
            log.error("EisInterfaceDataLog:" + JsonUtil.toJson(datalog));
            eaiDataLogId = 0;
        }
    }

    /**
     * 推送入库sap
     * @throws MalformedURLException
     */
//    		@Scheduled(cron="0/5 * *  * * ?")
    public void pushInSAP() throws MalformedURLException {
        log.info(" 推送SAP");
        //查询
        List<OmsInStoreRecords> list = omsInStoreRecordService.findForSapStatus();//查询推送vom成功,推送出库sap成功的,推送入库不成功的
        for (OmsInStoreRecords oms : list) {
//            VomInOutStoreOrder storeOrder = null;
            //查询出VOM入库流水是否已经返回到本系统
//            storeOrder = eisVomInOutStoreOrderService.queryVomInTenlibrary(oms.getVomSn());
            //如果VOM出入库流水已经到本系统  推送SAP
//            if (storeOrder != null) {
                List<LesStockTransQueue> list_transQueue = lesStockTransQueueService
                        .getLesStockTransQueueByCorderSn(oms.getVomSn());
                if (list_transQueue != null && list_transQueue.size() > 0) {
                    for (LesStockTransQueue lstq : list_transQueue) {
                        Result result = null;
                        allocateDefectLogsService.insertLog(oms.getId(),"推送入库SAP信息");
                        result = pushToSap(oms, lstq);
                        if(result.getStatus()){
                            allocateDefectLogsService.insertLog(oms.getId(),"推送入库SAP成功");
                            omsInStoreRecordService.updateInSapStatus(oms);//推送入库sap个更改状态
                        }
                        else{
                            allocateDefectLogsService.insertLog(oms.getId(),"推送入库SAP失败");
                        }
                    }
                }

//            }

        }
    }


    //推送sap接口
        private Result pushToSap(OmsInStoreRecords oms, LesStockTransQueue lesStockTransQueue
         ) {
            Result rs = new Result();
                ObjectFactory objectFactory = new ObjectFactory();
                ZMMS0010 request = objectFactory.createZMMS0010();
                request.setZCBSN(oms.getDb()+"TC");
                String outping = lesStockTransQueue.getOutping();

                String zeile = lesStockTransQueue.getZeile();
                String seqNum = "";
                if (StringUtil.isEmpty(zeile) || zeile.length() <= 3)
                    seqNum = zeile;
                else
                    seqNum = zeile.substring(zeile.length() - 3, zeile.length());

                request.setZLGORTO("");// 发货库位
                request.setZLSOT("");// les出库单号
                request.setZLSIN(outping);// les入库单号
                request.setZLSII(seqNum);
                request.setZLSOI("");
                String budat = DateUtil.format(new Date(), "yyyy-MM-dd");

                String bldat = DateUtil.format(lesStockTransQueue.getBillTime(), "yyyy-MM-dd");
                request.setBLDAT(bldat);// 入库确认时间
                request.setBUDAT(budat);// 当前时间

                String sku = lesStockTransQueue.getSku();

                request.setMATNR(sku);
                request.setMENGE(new BigDecimal(lesStockTransQueue.getQuantity()));

                request.setZLGORTI(lesStockTransQueue.getSecCode());// 接收库位

                TransferGoodsInfoToEhaierSAP soap = new TransferGoodsInfoToEhaierSAP_Service(
                        this.getClass().getResource(wsdlLocation + "/TransferGoodsInfoToEhaierSAP.wsdl")).getTransferGoodsInfoToEhaierSAPSOAP();

                List<ZMMS0010> tZMMS0010 = new ArrayList<ZMMS0010>();
                tZMMS0010.add(request);
                // 要记录接口数据日志
                EisInterfaceDataLog dataLog = new EisInterfaceDataLog();
                dataLog.setForeignKey(lesStockTransQueue.getCorderSn());
                dataLog.setRequestTime(DateUtil.currentDateTime());
                dataLog.setRequestData(JsonUtil.toJson(tZMMS0010));
                Long startTime = System.currentTimeMillis();
                try {
                    Holder<List<ZSDS0002>> tMSG = new Holder<List<ZSDS0002>>();
                    Holder<Integer> exSUBRC = new Holder<Integer>();
                    soap.transferGoodsInfoToEhaierSAP(tZMMS0010, exSUBRC, tMSG);
                    String msg = JsonUtil.toJson(tMSG.value);
                    dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_SUCCESS);
                    dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
                    dataLog.setResponseTime(DateUtil.currentDateTime());
                    dataLog.setResponseData(msg);
                    if (tMSG.value == null || tMSG.value.size() <= 0) {
                        rs.setStatus(false);
                        rs.setMessage("EAI 返回空");
                        dataLog.setResponseData("");
                    } else {
                        boolean flag = true;
                        for (ZSDS0002 zsds0002 : tMSG.value) {
                            if (!flag)
                                break;
                            flag = !"E".equalsIgnoreCase(zsds0002.getTYPE());
                        }
                        if (flag) {
                            // 更新状态为WA已推送SAP
//                        updateLineStatusAndQtyByLineNum(invTransferLine.getLineNum(), InvTransferLine.LINE_STATUS_WA_TO_SAP, lesStockTransQueue.getQuantity());
                        }
                        rs.setStatus(flag);
                        rs.setMessage(msg);
                    }
                } catch (Exception e) {
                    dataLog.setResponseStatus(EisInterfaceDataLog.RESPONSE_STATUS_ERROR);
                    dataLog.setResponseTime(DateUtil.currentDateTime());
                    dataLog.setResponseData("");
                    dataLog.setElapsedTime(System.currentTimeMillis() - startTime);
                    dataLog.setErrorMessage(e.getMessage());
                    rs.setStatus(false);
                    rs.setMessage("调用EAI接口失败");
                    log.error("调用EAI接口 TransferGoodsInfoToEhaierSAP 失败：", e);
                }
                dataLog.setCreateTime(DateUtil.currentDateTime());
//		recordEisInterfaceDataLog(dataLog);


            return rs;
        }





    /*
     * 定时取消退仓
     * */
//    @Scheduled(cron="0/5 * *  * * ?")
    public void cannelReturnWarehouse() {
        ErpOrderCancelNotifyRequest request = new ErpOrderCancelNotifyRequest();
        List<OmsInStoreRecords> list = omsInStoreRecordService.selectForStatus();
        for (OmsInStoreRecords oms : list) {
            request.setOwnerUserId("2998123754");
            request.setOrderCode(oms.getOrderCode());
            request.setOrderType("901");
            request.setOutOrderCode(oms.getDb()+"TC");
            //LINK 方式接入 生产环境
            String appKey = "009658";
            String fromCode = "2998123754";
            String secretKey = "Vi3z88ZEy5j9W28s4JA0O3X29r159S06";
            String pacUrl = "http://link.cainiao.com/gateway/link.do";

            PacClient pacClient = new PacClient(appKey, secretKey, pacUrl);
            SendSysParams sysParams = new SendSysParams();
            sysParams.setFromCode(fromCode);

            ErpOrderCancelNotifyResponse linkResponse = pacClient.send(request, sysParams);
        }
    }


    //获取当前时间
    private static String getTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 将Date类转换为XMLGregorianCalendar
     *
     * @param date
     * @return
     */
    public static XMLGregorianCalendar dateToXmlDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DatatypeFactory dtf = null;
        try {
            dtf = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
        }
        XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();
        dateType.setYear(cal.get(Calendar.YEAR));
        //由于Calendar.MONTH取值范围为0~11,需要加1
        dateType.setMonth(cal.get(Calendar.MONTH) + 1);
        dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));
        dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));
        dateType.setMinute(cal.get(Calendar.MINUTE));
        dateType.setSecond(cal.get(Calendar.SECOND));
        return dateType;
    }

    //获取当前时间+3天后的时间
    public static Date getDate() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.DAY_OF_MONTH, 3);// 今天+1天
        Date tomorrow = c.getTime();
        Long lt = new Long(tomorrow.getTime());
        Date date = new Date(lt);
        return date;
    }

    //6位随机数
    public static String getMath() {
        return (int) ((Math.random() * 9 + 1) * 100000) + "";
    }

}