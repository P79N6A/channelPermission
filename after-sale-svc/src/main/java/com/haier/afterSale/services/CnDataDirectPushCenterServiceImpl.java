package com.haier.afterSale.services;

import com.haier.afterSale.model.Ustring;
import com.haier.afterSale.service.CnDataDirectPushCenterService;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.eis.model.VomwwwOutinstockAnalysis;
import com.haier.purchase.data.model.CnDataDirectPush;
import com.haier.purchase.data.model.ExchangeGoods;
import com.haier.purchase.data.model.ReturnTable;
import com.haier.shop.model.OmsInStoreRecords;
import com.haier.shop.model.OrderrepairHPrecordsVO;
import com.haier.shop.model.StoragesWwwRelas;
import com.haier.shop.service.*;
import com.haier.stock.service.StockInvMachineSetService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.haier.purchase.data.service.CnDataDirectPushService;
import com.haier.afterSale.model.CnPushReturnInfoToGVSModel;
import com.haier.afterSale.model.PushReturnInfoToGVSHandler;
import com.haier.afterSale.model.Ustring;
import com.haier.afterSale.service.CustomeAfterService;
import com.haier.afterSale.util.SequenceModel;
import com.haier.eis.model.LesStockTransQueue;
import com.haier.eis.model.VomInOutStoreOrder;
import com.haier.eis.model.VomwwwOutinstockAnalysis;
import com.haier.eis.service.EisVomInOutStoreOrderService;
import com.haier.eis.service.EisVomwwwOutinstockAnalysisService;
import com.haier.eis.service.LesStockTransQueueService;
import com.haier.purchase.data.model.*;
import com.haier.purchase.data.service.CnDataDirectPushService;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CnDataDirectPushCenterServiceImpl implements CnDataDirectPushCenterService {
    @Autowired
    private CnDataDirectPushService CnDataDirectPushService;
    private static Logger log = LoggerFactory.getLogger(CustomeAfterServiceImpl.class);
    @Autowired
    private ShopOrderrepairHPrecordsService shopOrderrepairHPrecordsService;
    @Autowired
    private OperationAreaServiceImpl operationAreaServiceImpl;
    @Autowired
    private ShopOrderRepairsService shopOrderRepairsService;//退货
    @Autowired
    private OrderRepairLESRecordsService repairLESRecordsService;//出入库信息
    @Autowired
    private ShopOrdersService shopOrdersService;//订单

    @Autowired
    private EisVomInOutStoreOrderService eisVomInOutStoreOrderService;
    @Autowired
    private CnPushReturnInfoToGVSModel returnInfoToGVSModel;
    @Autowired
    private EisVomwwwOutinstockAnalysisService      eisVomwwwOutinstockAnalysisService;
    @Autowired
    private PushReturnInfoToGVSHandler toGVSHandler;
    @Autowired
    private LesStockTransQueueService lesStockTransQueueService;
    @Autowired
    private ShopOperationAreaService shopOperationAreaService;
    @Autowired
    private InvoicesService invoicesService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderProductsNewService orderProductNewService;
    @Autowired
    private SequenceModel sequenceModel;
    @Autowired
    private OrderRepairsNewService orderRepairsNewService;
    @Autowired
    private ShopOrderRepairLesreCordsService shopOrderRepairLesreCordsService;
    @Autowired
    private MemberInvoicesService memberInvoicesService;
    @Autowired
    private ShopOrderWorkflowsService shopOrderWorkflowsService;
    @Autowired
    private StoragesService storagesService;

    @Autowired
    private StockInvMachineSetService stockInvMachineSetService;

    @Override
    public ServiceResult<List<ExchangeGoods>> searchList(ExchangeGoods params) {
        ServiceResult<List<ExchangeGoods>> result = new ServiceResult<List<ExchangeGoods>>();
        List<ExchangeGoods>  list =CnDataDirectPushService.findAll(params);
        result.setResult(list);
        PagerInfo pi = new PagerInfo();
        int count = CnDataDirectPushService.findAllCount(params);
        pi.setRowsCount(count);
        result.setPager(pi);
        return result;
    }

    @Override
    public ServiceResult<List<ReturnTable>> findReturnGoods(ReturnTable params) {
        ServiceResult<List<ReturnTable>> result = new ServiceResult<List<ReturnTable>>();
        List<ReturnTable>  list =CnDataDirectPushService.findReturnGoods(params);
        result.setResult(list);
        PagerInfo pi = new PagerInfo();
        int count = CnDataDirectPushService.findReturnGoodsCount(params);
        pi.setRowsCount(count);
        result.setPager(pi);
        return result;
    }

    @Override
    public String returnGoodsPushSAP(String id) throws MalformedURLException, ParseException {
        try {
            ReturnTable rt = CnDataDirectPushService.findOneReturn(id);//查找一条退货单
                if(rt != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    JSONObject jsonobject = JSONObject.fromObject(rt.getData());
                    JSONArray getJsonArray = JSONArray.fromObject(jsonobject.get("orderItems"));
                    for (int i = 0; i < getJsonArray.size(); i++) {
                        JSONObject getJsonObj = getJsonArray.getJSONObject(i);//获取json数组中的第i项
                        //根据3w入库信息去匹配网单和订单是否存在
                        List<OrderrepairHPrecordsVO> list_vo = shopOrderrepairHPrecordsService.findByOid((String) getJsonObj.get("subSourceOrderCode"));
                        if (list_vo.size() != 0) {
                            for (OrderrepairHPrecordsVO vo : list_vo) {
                                if (Ustring.isNotEmpty(vo.getVomRepairSn()) && vo.getVomRepairSn().contains("TH")) {//判断是否是未签收
                                    //根据网单id查找最大的发票id(这个发票才是有效的)
                                    OrderrepairHPrecordsVO invoice = shopOrderrepairHPrecordsService.findInvoice(vo.getOrderProductId());
                                    if (invoice != null) {
                                        if (invoice.getInvoiceSuccess() == 1 && invoice.getStatusType() == 1) {//判断是否开发票
                                            operationAreaServiceImpl.InvalidInvoices(String.valueOf(invoice.getInvoIceId()), "退货或拦截");
                                            operationAreaServiceImpl.ProcessLog(vo.getOrderRepairId(), "系统", "作废发票", "发起作废发票请求");
                                        }
                                    }
                                    List<VomwwwOutinstockAnalysis> list_eis = eisVomwwwOutinstockAnalysisService.outStockSap(vo.getTbOrderSn());
                                     if (list_eis.size() > 0) {//判读是否出库并且推送sap成功
                                        //根据菜鸟仓库编码找到海尔仓库编码
//									StoreCodeMapping storeCodeMapping = storeCodeService.findByCainiaoStoreCode((String) jsonobject.get("warehouseCode"));
                                        StoragesWwwRelas storagesWwwRelas = storagesService.findByCainiaoStoreCode((String) jsonobject.get("warehouseCode"));

                                        com.haier.afterSale.model.CnPushReturnInfoToGVSModel.Result result = null;
                                        VomwwwOutinstockAnalysis vomwwwOutinstockAnalysis = new VomwwwOutinstockAnalysis();
                                        vomwwwOutinstockAnalysis.setTradeNo((String) getJsonObj.get("sourceOrderCode"));
                                        vomwwwOutinstockAnalysis.setSubTradeNo((String) getJsonObj.get("subSourceOrderCode"));
                                        vomwwwOutinstockAnalysis.setScOrderNo(vo.getcOrderSn());
                                        //vomwwwOutinstockAnalysis.setOrderStatus(0);
                                        vomwwwOutinstockAnalysis.setItemNo((String) getJsonObj.get("itemId"));
                                        //vomwwwOutinstockAnalysis.setCertification("");
                                        vomwwwOutinstockAnalysis.setLBXNo((String) jsonobject.get("returnOrderId"));
                                        //查询子物料,如果有子物料传子物料,没有传网单中的sku
                                        String subSku = stockInvMachineSetService.querySubsku(vo.getSku());
                                        if (Ustring.isEmpty(subSku)) {
                                            vomwwwOutinstockAnalysis.setSku(vo.getSku());
                                        } else {
                                            vomwwwOutinstockAnalysis.setSku(subSku);
                                        }
                                        //vomwwwOutinstockAnalysis.setType(1);
                                        vomwwwOutinstockAnalysis.setNum(Integer.parseInt((String) getJsonObj.get("actualQty")));
                                        //vomwwwOutinstockAnalysis.setReceiptVoucher("");
                                        vomwwwOutinstockAnalysis.setOutInDate(sdf.parse((String) jsonobject.get("orderConfirmTime")));
                                        //vomwwwOutinstockAnalysis.setTBNo("");
//                            vomwwwOutinstockAnalysis.setWWWStock((String) jsonobject.get("warehouseCode"));
                                        //vomwwwOutinstockAnalysis.setHpInfo("");
                                        vomwwwOutinstockAnalysis.setBackNo(vo.getVomRepairSn());
                                        //vomwwwOutinstockAnalysis.setBatch("");
                                        vomwwwOutinstockAnalysis.setWWWStock(storagesWwwRelas.getWwwstorcode());
                                        vomwwwOutinstockAnalysis.setExpressNum((String) jsonobject.get("expressCode"));
                                        result = returnInfoToGVSModel.send(vomwwwOutinstockAnalysis); //推送SAP
                                        if (result.getStatus() == 1) {
                                            List<Map<String, Object>> mapListJson = (List) net.sf.json.JSONArray.fromObject(result.getMessage());
                                            if ("S".equals(mapListJson.get(0).get("type"))) {
                                                shopOperationAreaService.updateStatus(vo.getOrderProductId().toString(), "110"); //发起二次鉴定的同时要把正向单关闭
                                                operationAreaServiceImpl.ProcessLog(vo.getOrderRepairId(), "系统", "关闭", "关闭正向单");
                                                //推送成功之后修改退货单 推送SAP状态
                                                shopOrderRepairsService.updataPushSap(Ustring.getString(vo.getOrderRepairId()), "1");
                                                //操作记录
                                                operationAreaServiceImpl.ProcessLog(vo.getOrderRepairId(), "系统", "操作", "推送SAP：成功");
                                                //逆向单状态改成“受理完成”
                                                shopOrderRepairsService.updataStatus(vo.getOrderRepairId().toString(), "3", "");
                                                CnDataDirectPush cnData = new CnDataDirectPush();
                                                cnData.setId(rt.getId());
                                                CnDataDirectPushService.update(cnData);//匹配成功修改cndata表
                                                CnDataDirectPushService.updateReturn(rt);//匹配成功修改状态returTable表
                                                return "匹配成功";
                                            } else {
                                                //操作记录
//                                                operationAreaServiceImpl.ProcessLog(vo.getOrderRepairId(), "系统", "操作", "推送SAP：失败,原因:Msg:" + result.getMessage() + ",status:" + result.getStatus() + ".system:" + result.getSystem() + ",eiaType:" + result.getEiaType());
                                                log.info("推送SAP：失败,原因:Msg:" + result.getMessage() + ",status:" + result.getStatus() + ".system:" + result.getSystem() + ",eiaType:" + result.getEiaType());
                                                return "推送sap失败";
                                            }
                                        } else {
                                            //操作记录
//                                            operationAreaServiceImpl.ProcessLog(vo.getOrderRepairId(), "系统", "操作", "推送SAP：失败,原因:Msg:" + result.getMessage() + ",status:" + result.getStatus() + ".system:" + result.getSystem() + ",eiaType:" + result.getEiaType());
                                            log.info("推送SAP：失败,原因:Msg:" + result.getMessage() + ",status:" + result.getStatus() + ".system:" + result.getSystem() + ",eiaType:" + result.getEiaType());
                                            return "推送sap失败";
                                        }

                                    }
                                    else {//没有推送sap的 直接修改状态
                                        orderProductNewService.interceptCancelClose(vo.getOrderProductId(), (new Date().getTime()) / 1000);//取消原网单并更改拦截状态
                                        CnDataDirectPush cnData = new CnDataDirectPush();
                                        cnData.setId(rt.getId());
                                        CnDataDirectPushService.update(cnData);//匹配成功修改cndata表
                                        CnDataDirectPushService.updateReturn(rt);//匹配成功修改状态returTable表
                                        return "匹配成功";

                                    }

                                }
                            }
                        } else {
                            CnDataDirectPushService.updateRtExpection(rt);
                            return "匹配失败,没有单据信息";
                        }

                    }
                }

        }catch (Exception e){
            log.error("returnGoodsPushSAP error", e);
            e.printStackTrace();
            throw e;
        }

              return "";
    }
}
