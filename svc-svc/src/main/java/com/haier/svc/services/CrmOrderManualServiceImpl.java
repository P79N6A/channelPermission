package com.haier.svc.services;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.ws.Holder;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.model.CrmManualOrder;
import com.haier.purchase.data.model.CrmOrderManualDetailItem;
import com.haier.purchase.data.model.CrmOrderManualItem;
import com.haier.purchase.data.model.InvBudgetOrg;
import com.haier.purchase.data.model.ProductPayment;
import com.haier.shop.model.ItemBase;
import com.haier.stock.model.InvRrsWarehouse;
import com.haier.stock.model.InvStockChannel;
import com.haier.stock.model.InvWarehouse;
import com.haier.stock.model.JdStorage;
import com.haier.stock.service.StockAgeService;
import com.haier.stock.service.StockJdStorageService;
import com.haier.svc.bean.GVSOrderPriceRequire;
import com.haier.svc.bean.GVSOrderPriceResponse;
import com.haier.svc.model.CrmOrderManualModel;
import com.haier.svc.model.InterfaceLogModel;
import com.haier.svc.purchase.crmmanual.DetailType;
import com.haier.svc.purchase.crmmanual.InsertWAOrderBillFromEhaierToCRM;
import com.haier.svc.purchase.crmmanual.InsertWAOrderBillFromEhaierToCRM_Service;
import com.haier.svc.purchase.crmmanual.MasterType;
import com.haier.svc.service.CrmOrderManualService;
import com.haier.svc.service.ItemService;
import com.haier.svc.service.ProductPaymentService;
import com.haier.svc.service.PurchaseBaseCommonService;
import com.haier.svc.service.PurchaseCommonService;
import com.haier.svc.service.T2OrderService;
import com.haier.svc.util.CommUtil;
import com.haier.svc.util.DateCal;
import com.haier.svc.util.HttpUtils;
import com.haier.svc.util.WSUtils;

/**
 * Created by 李超 on 2018/3/13.
 */
@Service
public class CrmOrderManualServiceImpl implements CrmOrderManualService {
    private static org.apache.log4j.Logger log          = org.apache.log4j.LogManager
            .getLogger(CrmOrderManualServiceImpl.class);

    @Autowired
    private CrmOrderManualModel crmOrderManualModel;
//    private GVSOrderModel                  gvsOrderModel;
    
    @Value("${t2OrderResponse.location}")
    private String                         wsdlLocation;

    //获取价格地址
    @Value("${t2Order.kxPath.priceUrl}")
    private String priceUrl;

    @Autowired
    private PurchaseBaseCommonService purchaseBaseCommonService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private PurchaseCommonService purchaseCommonService;

    @Autowired
    private InterfaceLogModel interfaceLogModel;
    
    @Autowired
    private ProductPaymentService     productPaymentService;
    
    @Autowired
    private T2OrderService t2OrderService;
    
    @Autowired
	StockAgeService stockAgeService;
    @Autowired
    private StockJdStorageService jdStorageService;
    
    public void setWsdlLocation(String wsdlLocation) {
        this.wsdlLocation = wsdlLocation;
    }

    public PurchaseBaseCommonService getPurchaseBaseCommonService() {
        return purchaseBaseCommonService;
    }

    public void setPurchaseBaseCommonService(PurchaseBaseCommonService purchaseBaseCommonService) {
        this.purchaseBaseCommonService = purchaseBaseCommonService;
    }

    public void setCrmOrderManualModel(CrmOrderManualModel crmOrderManualModel) {
        this.crmOrderManualModel = crmOrderManualModel;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public void setPurchaseCommonService(PurchaseCommonService purchaseCommonService) {
        this.purchaseCommonService = purchaseCommonService;
    }

//    public GVSOrderModel getGvsOrderModel() {
//        return gvsOrderModel;
//    }
//
//    public void setGvsOrderModel(GVSOrderModel gvsOrderModel) {
//        this.gvsOrderModel = gvsOrderModel;
//    }

    /**提交订单
     * @param params
     * @return
     */
    @Override
    public ServiceResult<Boolean> commitOrderManual(Map<String, Object> params) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            // 提交订单
            result.setResult(crmOrderManualModel.commitOrderManual(params));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("提交订单失败：", e);
        }
        return result;
    }

    /**
     * 删除订单
     * @param params
     */
    @Override
    public ServiceResult<Boolean> deleteOrderManual(Map<String, Object> params) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            // 删除订单
            result.setResult(crmOrderManualModel.deleteOrderManual(params));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("删除订单失败：", e);
        }
        return result;
    }

    /**
     * CRM手工采购订单编辑
     * @param
     */
    @Override
    public ServiceResult<Boolean> editCRMOrderManual(Map<String, Object> params) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            // 创建退货单
            result.setResult(crmOrderManualModel.editCRMOrderManual(params));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("[CrmOrderManualService][editCRMOrderManual]:CRM手工采购订单编辑失败:", e);
        }
        return result;
    }

    /**
     * 获取CRM手工采购订单信息
     * @return
     */
    @Override
    public ServiceResult<List<CrmOrderManualDetailItem>> getCrmOrderManualList(Map<String, Object> params) {
        ServiceResult<List<CrmOrderManualDetailItem>> result = new ServiceResult<List<CrmOrderManualDetailItem>>();
        try {
            result.setResult(crmOrderManualModel.getCrmOrderManualList(params));
            PagerInfo pi = new PagerInfo();
            pi.setRowsCount(crmOrderManualModel.getCrmOrderManualListCNT(params));
            result.setPager(pi);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("获取T+2订单信息list失败：", e);
        }
        return result;
    }

    /**
     * 获得明细条数
     * @return
     */
    @Override
    public ServiceResult<Integer> getRowCnts() {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        try {
            result.setResult(crmOrderManualModel.getRowCnts());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("获取明细条数失败：", e);
        }
        return result;
    }

    private String splitReleBillCode(String sourceOrderId) {
        String orderId = "";
        if (StringUtils.isEmpty(sourceOrderId)) {
            return sourceOrderId;
        } else {
            orderId = sourceOrderId;
            if (orderId.indexOf("-") > 0) {

                if (orderId.substring(orderId.indexOf("-"), orderId.length()).length() == 1) {
                    orderId = orderId.replace("-", "0");
                } else {
                    orderId = orderId.replace("-", "");
                }
                return orderId;
            } else {
                return orderId;
            }
        }

    }

    /**
     * 来源单号掉接口重建单号
     * @return
     */
    private String editSourceOrderId(String sourceOrderId) {
        String orderId = "";
        if (StringUtils.isEmpty(sourceOrderId)) {
            return sourceOrderId;
        }
        orderId = sourceOrderId.replace("WP01", "WM");//由于CRM单号最长不能超过15位
        if (orderId.contains("-")) {

            if ('-' == orderId.charAt(orderId.length() - 2)) {
                orderId = orderId.replace('-', '0');
            } else {
                orderId = orderId.replace("-", "");
            }
        }
        return orderId;
    }

    public ServiceResult<Boolean> insertWAOrderBillToCRM(CrmOrderManualItem comi,
                                                         List<CrmOrderManualDetailItem> l_comdi) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        List<DetailType> l_detail = new ArrayList<DetailType>();
        Holder<String> billCode = new Holder<String>();
        Holder<String> flag = new Holder<String>();
        Holder<String> message = new Holder<String>();
        Holder<String> vbeln = new Holder<String>();
        Holder<String> vbelnDN = new Holder<String>();
//        String path = "file:"
//                + this.getClass()
//                .getResource(wsdlLocation + "/InsertWAOrderBillFromEhaierToCRM.wsdl")
//                .getPath();
        try {
            URL url = this.getClass().getResource(
                    wsdlLocation + "/InsertWAOrderBillFromEhaierToCRM.wsdl");
            InsertWAOrderBillFromEhaierToCRM_Service service = new InsertWAOrderBillFromEhaierToCRM_Service(
                    url);
            InsertWAOrderBillFromEhaierToCRM soap = service
                    .getInsertWAOrderBillFromEhaierToCRMSOAP();
            MasterType masterType = new MasterType();
            masterType.setAddress("");
            masterType.setAudiFlag(0);
            masterType.setAudiMan("");
            masterType.setBaseCode("");
            //TODO 关联 单号 变成0
            masterType.setBillCode(editSourceOrderId(comi.getReleBillCode()));//
            masterType.setBillType(comi.getBillType());
            masterType.setBName("");
            masterType.setBrandCode(l_comdi.get(0).getBrand_id());
            masterType.setBudgetOrg(comi.getBudgetOrg());
            masterType.setBudgetSort(comi.getBudgetSort());
            masterType.setCorpCode(comi.getCorpCode());//FIXME 固定值 柱爷确认
            masterType.setCustCode(comi.getCustCode());
            masterType.setCustMgr("00022923");//FIXME 固定值 柱爷确认
            masterType.setDestCode(comi.getDestCode());
            masterType.setFlag("");
            masterType.setInvSort(l_comdi.get(0).getProduct_group_id());//
            masterType.setMaker("CBS[" + comi.getMaker() + "]");
            masterType.setOrderCode("");
            masterType.setPickType(String.valueOf(comi.getSap_flow_num()));

            masterType.setPlanInDate(WSUtils.stringToXmlCalendar(comi.getPlanInDateStr(),
                    "yyyy-MM-dd HH:mm:ss"));

            masterType.setProDeputy("00022923");
            masterType.setProjectCode("");
            masterType.setProMgr("00022923");
            masterType.setReGetmoney("");
            masterType.setRegionID(comi.getRegionId());
            masterType.setSaleOrgCode(comi.getSaleOrgCode());
            masterType.setTel("");
            masterType.setUpAccount("");
            masterType.setUserMemo("");
            masterType.setWhCode(comi.getWhCode());
            masterType.setZipCode("");

            for (CrmOrderManualDetailItem comdi : l_comdi) {
                try {
                    DetailType dt = new DetailType();
                    dt.setActPrice(new BigDecimal(String.valueOf(comdi.getActPrice())));
                    dt.setBateMoney(new BigDecimal(String.valueOf(comdi.getBateMoney())));
                    dt.setBateRate(new BigDecimal(String.valueOf(comdi.getBateRate())));
                    dt.setBillCode(editSourceOrderId(comi.getReleBillCode()));
                    dt.setInvBrand(comdi.getBrand_id());
                    dt.setInvCode(comdi.getMaterials_id());
                    dt.setInvMemo("");
                    dt.setInvSort(comdi.getProduct_group_id());
                    dt.setInvState("10");
                    dt.setIsFL(StringUtils.isNotEmpty(comdi.getIsFL()) ? 0 : Integer.parseInt(comdi
                            .getIsFL()));
                    dt.setIsKPO(StringUtils.isNotEmpty(comdi.getIsKPO()) ? 0 : Integer
                            .parseInt(comdi.getIsKPO()));
                    dt.setLossRate(new BigDecimal(String.valueOf(comdi.getLossRate())));
                    dt.setProLossMoney(new BigDecimal(String.valueOf(comdi.getProLossMoney())));
                    dt.setQty(comdi.getQty());
                    dt.setRetailPrice(new BigDecimal(String.valueOf(comdi.getRetailPrice())));
                    dt.setStockPrice(new BigDecimal(String.valueOf(comdi.getStockPrice())));
                    dt.setUnitPrice(new BigDecimal(String.valueOf(comdi.getUnitPrice())));
                    dt.setSumMoney(dt.getUnitPrice().multiply(new BigDecimal(dt.getQty())));
                    dt.setVerCode("");
                    dt.setVerMoney(new BigDecimal(0));
                    l_detail.add(dt);
                } catch (Exception ex) {
                    log.error("", ex);
                }
            }
            String masterTypeJ=JsonUtil.toJson(masterType);
            String l_detailJ=JsonUtil.toJson(l_detail);
            log.info("*****调用接口入参masterType："+ JsonUtil.toJson(masterType));
            log.info("*****调用接口入参l_detail："+JsonUtil.toJson(l_detail));
            interfaceLogModel.insertInterfaceLog("CRM手工采购订单录入接口入参masterType","CRM手工采购订单",JsonUtil.toJson(masterType));
            interfaceLogModel.insertInterfaceLog("CRM手工采购订单录入接口入参l_detail","CRM手工采购订单",JsonUtil.toJson(l_detail));
            soap.insertWAOrderBillFromEhaierToCRM("EHAIER", masterType, l_detail, billCode, flag,
                    message, vbeln, vbelnDN);
            interfaceLogModel.insertInterfaceLog("CRM手工采购订单录入接口出参","CRM手工采购订单",message.value);

            if (flag.value.equalsIgnoreCase("Y")) {
                for (CrmOrderManualDetailItem comdi : l_comdi) {
                    comdi.setSo_id(vbeln.value);
                    comdi.setDn_id(vbelnDN.value);
                    comdi.setPo_id("PO" + vbeln.value);
                    crmOrderManualModel.updateCRMOrderManualDetailForCRM(comdi);
                }
                comi.setFlow_flag(70);
                result.setResult(true);
            } else {
                comi.setError_msg(message.value);
                comi.setFlow_flag(0);
                result.setResult(false);
                result.setMessage(result.getMessage()+message.value);
                log.warn("CRM手工采购调用接口失败：" + JsonUtil.toJson(comi) + ",<br>"
                        + JsonUtil.toJson(l_comdi));
            }
            crmOrderManualModel.commitOrderManualForCRM(comi);

        } catch (Exception ex) {
            log.error("", ex);
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 自动手工开单
     * @param sku 物料编码
     * @param edChannelId 渠道
     * @param qty 数量
     * @param estorgeId WA库位码
     * @param billType 订单类型
     * @param sapFlow 配送方式
     * @return
     */
    @Override
    public ServiceResult<CrmOrderManualDetailItem> createAutoOrderManual(String sku,
                                                                         String edChannelId,
                                                                         Integer qty,
                                                                         String estorgeId,
                                                                         String billType,
                                                                         Integer sapFlow) {
        ServiceResult<CrmOrderManualDetailItem> result = new ServiceResult<CrmOrderManualDetailItem>();
        CrmOrderManualDetailItem comi = new CrmOrderManualDetailItem();
        CrmOrderManualItem com = new CrmOrderManualItem();
        try {
            //基本设定
            //WA库位
            com.setEstorge_id(estorgeId);
            //物料编码
            comi.setMaterials_id(sku);
            //渠道
            comi.setEd_channel_id(edChannelId);
            //数量
            comi.setQty(qty);
            //批次
            comi.setInvState(10);
            //订单类型
            com.setBillType(billType);
            //配送方式
            com.setSap_flow_num(sapFlow);
            //库存类型
            if ("ZGOR".equals(com.getBillType())) {
                com.setStock_type(6);
            }
            //客户经理
            com.setCustMgr("00022923");
            //产品经理
            com.setPorMgr("00022923");
            //产品代表
            com.setProDputy("00022923");
            //开单人
            com.setMaker("sysAuto");
            //预算种类
            com.setBudgetSort("30");
            //创建人
            com.setCreate_user("sysAuto");
            //创建人
            comi.setCreate_user("sysAuto");
            //RRS库位码
            com.setWhCode("");
            //设置系统标识码
            com.setSysFlag(301);
            //1，根据WA库位码获取数据（分公司，工贸，付款方，送达方，WA库位名，配送中心，地区编码，销售组织）
            if (estorgeId != null && !estorgeId.equals("")) {

                //通过电商库位码获取仓库
                ServiceResult<InvWarehouse> invWarehouseResult = purchaseBaseCommonService
                        .getAllWhByEstorgeId(estorgeId);
                if (invWarehouseResult.getSuccess() && invWarehouseResult.getResult() != null) {
                    //WA库位名
                    com.setEstorge_name(invWarehouseResult.getResult().getEstorge_name());
                    //工贸编码
                    com.setRegionId(invWarehouseResult.getResult().getArea_id());
                    //工贸描述
                    com.setIndustry_trade_desc(invWarehouseResult.getResult()
                            .getIndustry_trade_desc());
                    //付款方
                    com.setCustCode(invWarehouseResult.getResult().getPayment_id());
                    //付款方名称
                    com.setPayment_name(invWarehouseResult.getResult().getPayment_name());
                    //送达方编码
                    com.setDestCode(invWarehouseResult.getResult().getTransmit_id());
                    //送达方描述
                    com.setEsale_name(invWarehouseResult.getResult().getTransmit_desc());
                    //配送中心编码
                    com.setDestCenter(invWarehouseResult.getResult().getCenterCode());
                    //配送中心描述
                    com.setRrs_distribution_name(invWarehouseResult.getResult()
                            .getRrs_distribution_name());
                    //分公司编码
                    com.setCorpCode(invWarehouseResult.getResult().getSale_org_id());
                    //分公司名称
                    com.setCorpName(invWarehouseResult.getResult().getBranch());
                    //销售组织
                    com.setSaleOrgCode(invWarehouseResult.getResult().getSale_org_id());
                } else {
                    result.setMessage("指定WA库位码不存在");
                    result.setSuccess(false);
                    return result;
                }

            }
            //2，根据物料号获取数据（型号，品牌名称，品牌code，产品组名称，产品组code）
            if (sku != null && !sku.equals("")) {
                //调用service执行检索
                ServiceResult<List<ItemBase>> itemResult = itemService
                        .findItemBaseByMaterialId(sku);
                if (itemResult.getSuccess() && itemResult.getResult() != null
                        && itemResult.getResult().size() > 0) {
                    //DB操作成功
                    ItemBase item = itemResult.getResult().get(0);
                    //型号
                    comi.setMaterials_desc(item.getMaterialDescription());
                    //品牌code
                    comi.setBrand_id(item.getProBand());
                    //产品组code
                    comi.setProduct_group_id(item.getDepartment());
                    //根据产品组取得品类
                    List<String> productGroupList = new ArrayList<String>();
                    productGroupList.add(comi.getProduct_group_id());
                    Map<String, Object> categoryParams = new HashMap<String, Object>();
                    categoryParams.put("productGroup", productGroupList);
                    ServiceResult<List<String>> resultProductGroup = itemService
                            .getCbsCategoryByProductGroup(categoryParams);
                    if (resultProductGroup.getSuccess() && resultProductGroup.getResult() != null
                            && resultProductGroup.getResult().size() > 0) {
                        comi.setCategory_id(resultProductGroup.getResult().get(0));
                    }
                } else {
                    result.setMessage("指定SKU不存在");
                    result.setSuccess(false);
                    return result;
                }
            }
            //3，根据地区编码和产品组取得预算体信息
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("product_group_id", comi.getProduct_group_id());
            params.put("area_id", com.getRegionId());
            ServiceResult<InvBudgetOrg> invBudgetOrg = purchaseBaseCommonService
                    .getAllBudgetOrg(params);

            if (invBudgetOrg.getSuccess() && invBudgetOrg.getResult() != null) {
                com.setBudgetOrg(invBudgetOrg.getResult().getBudgetorg_id());
                com.setBudgetOrgName(invBudgetOrg.getResult().getBudgetorg_name());
            }

            //4，根据销售组织，付款方，物料编码，地区编码取得价格信息
            GVSOrderPriceRequire order = new GVSOrderPriceRequire();
            order.setCorpCode(com.getSaleOrgCode());
            order.setCustCode(com.getCustCode());
            order.setInvCode(sku);

            //order.setRegionCode(industry_trade_id);
            order.setRegionCode(com.getRegionId());
            GVSOrderPriceResponse priceResponse = quirePrice(order);
//            log.info("价格取得结果：" + JsonUtil.toJson(priceResponse));
            //价格情报设定
            if (priceResponse.getReUnitPrice() != null
                    && !"".equals(priceResponse.getReUnitPrice())) {
                //总价
                comi.setSumMoney((new BigDecimal(priceResponse.getReUnitPrice())).multiply(
                        new BigDecimal(qty)).floatValue());
                //开票单价
                comi.setUnitPrice(new Float(priceResponse.getReUnitPrice()));
            } else {
                result.setSuccess(false);
                result.setMessage("指定物料价格情报在GVS不存在");
                return result;
            }

            //采购价
            if (priceResponse.getReStockPrice() != null
                    && !"".equals(priceResponse.getReStockPrice())) {
                comi.setStockPrice(new Float(priceResponse.getReStockPrice()));
            } else {
                comi.setStockPrice(new Float(0));
            }

            //供价
            if (priceResponse.getReRetailPrice() != null
                    && !"".equals(priceResponse.getReRetailPrice())) {
                comi.setRetailPrice(new Float(priceResponse.getReRetailPrice()));
            } else {
                comi.setRetailPrice(new Float(0));
            }
            //执行价
            if (priceResponse.getReActPrice() != null && !"".equals(priceResponse.getReActPrice())) {
                comi.setActPrice(new Float(priceResponse.getReActPrice()));
            } else {
                comi.setActPrice(new Float(0));
            }
            //直扣
            if (priceResponse.getReBateRate() != null && !"".equals(priceResponse.getReBateRate())) {
                comi.setBateRate(new Float(priceResponse.getReBateRate()));
            } else {
                comi.setBateRate(new Float(0));
            }
            //台返金额
            if (priceResponse.getReBateMoney() != null
                    && !"".equals(priceResponse.getReBateMoney())) {
                comi.setBateMoney(new Float(priceResponse.getReBateMoney()));
            } else {
                comi.setBateMoney(new Float(0));
            }
            //折价损失
            if (priceResponse.getReLossRate() != null && !"".equals(priceResponse.getReLossRate())) {
                comi.setLossRate(new Float(priceResponse.getReLossRate()));
            } else {
                comi.setLossRate(new Float(0));
            }
            //是否返利
            comi.setIsFL(StringUtils.isNotEmpty(priceResponse.getReIsFL()) ? "0" : priceResponse
                    .getReIsFL());
            //是否商用空调
            comi.setIsKPO(StringUtils.isNotEmpty(priceResponse.getReIsKPO()) ? "0" : priceResponse
                    .getReIsKPO());

            //5，生成采购单
            //采购订单号生成
            String wpOderId = getWPOrderId();
            com.setWp_order_id(wpOderId);
            comi.setWp_order_id(wpOderId);
            log.info("采购单号：" + wpOderId);
            //关联订单号
            com.setReleBillCode(wpOderId);
            //采购单登陆
            params = new HashMap<String, Object>();
            params.put("operatorType", "add");
            params.put("CrmOrderManualItem", com);
            params.put("CrmOrderManualDetailItem", comi);
            Boolean resultEdit = crmOrderManualModel.editCRMOrderManual(params);
            log.info("采购单生成结果：" + resultEdit);
            String errorMsg = "";
            if (resultEdit) {
                //6，CRm开单接口调用，如果成功，更新采购单状态，并返回
                errorMsg = sendWAOrderBillToCRM(com, comi, getRrsList(estorgeId));
                if (errorMsg == null) {
                    CrmOrderManualDetailItem resultManual = new CrmOrderManualDetailItem();
                    resultManual.setWp_order_id(com.getWp_order_id());
                    resultManual.setSo_id(comi.getSo_id());
                    result.setResult(resultManual);
                } else {
                    result.setSuccess(false);
                    result.setMessage(errorMsg);
                }
            }

            //result.setResult("WP0215060539519");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("自动手工采购失败：", e);
        }
        return result;
    }

    /**
     * 通过电商库位码获取日日顺仓库List
     *
     * @param estorge_id WA库位码
     * @return List<InvRrsWarehouse>
     */
    private List<InvRrsWarehouse> getRrsList(String estorge_id) {
        //
        Map<String, Object> invRrsWarehouseParams = new HashMap<String, Object>();
        invRrsWarehouseParams.put("estorge_id", estorge_id);
        ServiceResult<List<InvRrsWarehouse>> invRrsResult = purchaseBaseCommonService
                .getAllRrsWhByEstorgeId(invRrsWarehouseParams);

        if (invRrsResult.getSuccess() && invRrsResult.getResult() != null
                && invRrsResult.getResult().size() > 0) {
            return invRrsResult.getResult();
        } else {
            return null;
        }
    }

    /**
     * 订单号取得
     * @return
     */
    private String getWPOrderId() {
        String wpOrderId = "";
        // 正向流程,WP开头
        wpOrderId = "WP02";
        // 年月加入
        Calendar cal = Calendar.getInstance();
        // 当前年
        String currentYear = String.valueOf(cal.get(Calendar.YEAR));
        // 当前月
        String currentMonth = String.valueOf(cal.get(Calendar.MONTH) + 1);
        if (currentMonth.length() == 1) {
            currentMonth = "0" + currentMonth;
        }

        wpOrderId = wpOrderId + currentYear.substring(2) + currentMonth;

        // 订单流水号取得
        Integer nextVal = purchaseCommonService.getNextVal().getResult();
        String newVal = String.valueOf(nextVal);
        while (newVal.length() < 7) {
            newVal = "0" + newVal;
        }

        // 订单流水号加入
        wpOrderId = wpOrderId + newVal;

        return wpOrderId;
    }

    public String sendWAOrderBillToCRM(CrmOrderManualItem com, CrmOrderManualDetailItem comi,
                                       List<InvRrsWarehouse> rrsCodeList) {
        List<DetailType> l_detail = new ArrayList<DetailType>();
        Holder<String> billCode = new Holder<String>();
        Holder<String> flag = new Holder<String>();
        Holder<String> message = new Holder<String>();
        Holder<String> vbeln = new Holder<String>();
        Holder<String> vbelnDN = new Holder<String>();
//        String path = "file:"
//                + this.getClass()
//                .getResource("/wsdl" + "/InsertWAOrderBillFromEhaierToCRM.wsdl")
//                .getPath();
        try {
            URL url = this.getClass().getResource(
                    wsdlLocation + "/InsertWAOrderBillFromEhaierToCRM.wsdl");
            InsertWAOrderBillFromEhaierToCRM_Service service = new InsertWAOrderBillFromEhaierToCRM_Service(
                    url);
            InsertWAOrderBillFromEhaierToCRM soap = service
                    .getInsertWAOrderBillFromEhaierToCRMSOAP();
            MasterType masterType = new MasterType();
            masterType.setAddress("");
            masterType.setAudiFlag(0);
            masterType.setAudiMan("");
            masterType.setBaseCode("");

            masterType.setBillCode(com.getWp_order_id());
            masterType.setBillType(com.getBillType());
            masterType.setBName("");
            masterType.setBrandCode(comi.getBrand_id());
            masterType.setBudgetOrg(com.getBudgetOrg());
            masterType.setBudgetSort(com.getBudgetSort());
            masterType.setCorpCode(com.getCorpCode());//FIXME 固定值 柱爷确认
            masterType.setCustCode(com.getCustCode());
            masterType.setCustMgr("00022923");//FIXME 固定值 柱爷确认
            masterType.setDestCode(com.getDestCode());
            masterType.setFlag("");
            masterType.setInvSort(comi.getProduct_group_id());//
            masterType.setMaker("CBS[" + com.getMaker() + "]");
            masterType.setOrderCode("");
            masterType.setPickType(String.valueOf(com.getSap_flow_num()));

            masterType.setPlanInDate(WSUtils.stringToXmlCalendar(com.getPlanInDateStr(),
                    "yyyy-MM-dd HH:mm:ss"));

            masterType.setProDeputy("00022923");
            masterType.setProjectCode("");
            masterType.setProMgr("00022923");
            masterType.setReGetmoney("");
            masterType.setRegionID(com.getRegionId());
            masterType.setSaleOrgCode(com.getSaleOrgCode());
            masterType.setTel("");
            masterType.setUpAccount("");
            masterType.setUserMemo("");
            //masterType.setWhCode(com.getWhCode());
            masterType.setZipCode("");

            try {
                DetailType dt = new DetailType();
                dt.setActPrice(new BigDecimal(String.valueOf(comi.getActPrice())));
                dt.setBateMoney(new BigDecimal(String.valueOf(comi.getBateMoney())));
                dt.setBateRate(new BigDecimal(String.valueOf(comi.getBateRate())));
                dt.setBillCode(com.getWp_order_id());
                dt.setInvBrand(comi.getBrand_id());
                dt.setInvCode(comi.getMaterials_id());
                dt.setInvMemo("");
                dt.setInvSort(comi.getProduct_group_id());
                dt.setInvState("10");
                dt.setIsFL(StringUtils.isNotEmpty(comi.getIsFL()) ? 0 : Integer.parseInt(comi
                        .getIsFL()));
                dt.setIsKPO(StringUtils.isNotEmpty(comi.getIsKPO()) ? 0 : Integer.parseInt(comi
                        .getIsKPO()));
                dt.setLossRate(new BigDecimal(String.valueOf(comi.getLossRate())));
                dt.setProLossMoney(new BigDecimal(String.valueOf(comi.getProLossMoney())));
                dt.setQty(comi.getQty());
                dt.setRetailPrice(new BigDecimal(String.valueOf(comi.getRetailPrice())));
                dt.setStockPrice(new BigDecimal(String.valueOf(comi.getStockPrice())));
                dt.setUnitPrice(new BigDecimal(String.valueOf(comi.getUnitPrice())));
                dt.setSumMoney(dt.getUnitPrice().multiply(new BigDecimal(dt.getQty())));
                dt.setVerCode("");
                dt.setVerMoney(new BigDecimal(0));
                l_detail.add(dt);
            } catch (Exception ex) {
                log.error("", ex);
                return ex.getMessage();
            }
            Boolean successFlag = false;
            for (InvRrsWarehouse rrsCode : rrsCodeList) {
                //日日顺库位编码
                masterType.setWhCode(rrsCode.getRrs_wh_code());
                interfaceLogModel.insertInterfaceLog("CRM开单接口入参masterType","CRM手工采购订单",JsonUtil.toJson(masterType));
                interfaceLogModel.insertInterfaceLog("CRM开单接口入参l_detail","CRM手工采购订单",JsonUtil.toJson(l_detail));
                soap.insertWAOrderBillFromEhaierToCRM("EHAIER", masterType, l_detail, billCode,
                        flag, message, vbeln, vbelnDN);
                log.info("CRM开单结果：" + flag.value);
                //CRM开单成功
                if (flag.value.equalsIgnoreCase("Y")) {
                    log.info("CRM开单库位：" + rrsCode.getRrs_wh_code());
                    comi.setSo_id(vbeln.value);
                    comi.setDn_id(vbelnDN.value);
                    comi.setPo_id("PO" + vbeln.value);
                    crmOrderManualModel.updateCRMOrderManualDetailForCRM(comi);
                    com.setFlow_flag(70);
                    com.setWhCode(rrsCode.getRrs_wh_code());
                    //根据日日顺库位,计算计划到货日期
                    com.setPlanInDateStr(getPlanInDate(com));
                    crmOrderManualModel.commitOrderManualForCRM(com);
                    successFlag = true;
                    break;
                } else {
                    //库存原因，使用下一个库位
                    if (message.value.indexOf("库存") > 0) {
                        continue;
                    } else {
                        break;
                    }
                }
            }
            //未采购成功，将生成的采购单删除
            if (!successFlag) {
                Map<String, Object> params = new HashMap<String, Object>();
                List<String> deleteList = new ArrayList<String>();
                deleteList.add(com.getWp_order_id());
                params.put("deleteList", deleteList);
                params.put("delete_user", "sysAuto");
                crmOrderManualModel.deleteOrderManual(params);
                return message.value;
            }
        } catch (Exception ex) {
            log.error("", ex);
            return ex.getMessage();
        }
        return null;
    }

    private String getPlanInDate(CrmOrderManualItem com) {
        //当前日期取得
        String currentDate = CommUtil.getCurrentDate("yyyy-MM-dd");
        //上周日期取得
        DateCal dateCal = new DateCal(currentDate);
        //运输时效取得
        Map<String, Object> invRrsWarehouseParams = new HashMap<String, Object>();
        invRrsWarehouseParams.put("estorge_id", com.getEstorge_id());
        invRrsWarehouseParams.put("rrs_wh_code", com.getWhCode());
        ServiceResult<List<InvRrsWarehouse>> invRrsWarehouseResult = purchaseBaseCommonService
                .getAllRrsWhByEstorgeId(invRrsWarehouseParams);

        String planInDate = dateCal.decDay(-invRrsWarehouseResult.getResult().get(0)
                .getTransport_prescription())
                + " 00:00:00";
        return planInDate;
    }

    @Override
    public GVSOrderPriceResponse quirePrice(GVSOrderPriceRequire order){
        String url = priceUrl;
        String data = "<INPUT><INPUTROW><CustCode>" + order.getCustCode()
                + "</CustCode><RegionCode>" + order.getRegionCode()
                + "</RegionCode><InvCode>" + order.getInvCode()
                + "</InvCode><CorpCode>" + order.getCorpCode()
                + "</CorpCode></INPUTROW></INPUT>";

        interfaceLogModel.insertInterfaceLog("CRM手工采购获取价格信息入参","CRM手工采购订单",data);

        String resultMsg = HttpUtils.sendRequest(url, null, data,
                HttpUtils.HTTP_METHOD_POST, false, null);
        GVSOrderPriceResponse result = new GVSOrderPriceResponse();
                Document doc = null;
                try {
                    doc = DocumentHelper.parseText(resultMsg);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                Element element = (Element) doc.getRootElement().elements().get(0);
        List<Element> list = element.elements();
        for (Element el : list) {
            if (el.getName().equals("ReInvCode")) {
                result.setReInvCode(el.getText());
            } else if (el.getName().equals("ReUnitPrice")) {
                result.setReUnitPrice(el.getText());
            } else if (el.getName().equals("ReStockPrice")) {
                result.setReStockPrice(el.getText());
            } else if (el.getName().equals("ReRetailPrice")) {
                result.setReRetailPrice(el.getText());
            } else if (el.getName().equals("ReActPrice")) {
                result.setReActPrice(el.getText());
            } else if (el.getName().equals("ReBateRate")) {
                result.setReBateRate(el.getText());
            } else if (el.getName().equals("ReBateMoney")) {
                result.setReBateMoney(el.getText());
            } else if (el.getName().equals("ReLossRate")) {
                result.setReLossRate(el.getText());
            } else if (el.getName().equals("ReIsFL")) {
                result.setReIsFL(el.getText());
            } else if (el.getName().equals("ReIsKPO")) {
                result.setReIsKPO(el.getText());
            }
        }
        interfaceLogModel.insertInterfaceLog("CRM手工采购获取价格信息出参","CRM手工采购订单",resultMsg);
        return result;
    }

	@Override
	public Map<String, Object> importCrmOrderManualJD(List<String[]> list, Map<String, String> productgroupMap, Map<String, String> brandMap) {
		Map<String, Object> resultMap = new HashMap<>();
		List<CrmManualOrder> result = new ArrayList<>();
		ServiceResult<List<InvStockChannel>> channels = stockAgeService.getChannels();
        // 渠道数据存入HashMap
        Map<String, String> invstockchannelmap = new HashMap<String, String>();

        // 调用stockCommonService，取得渠道数据
        invstockchannelmap = t2OrderService
                .getChannelMapByName(invstockchannelmap);
		boolean access = true;
		String errorMsg = "";
		
		
		for(int i = 1; i < list.size(); i++){
			String[] s = list.get(i);
			
			String materials_id = s[1];
			String qty = s[2];
			String whCode = s[4];
			String estorge_id = s[5];
			String ed_channel_id = s[6];


			if(StringUtils.isBlank(materials_id)){
				continue;
			}
			if(StringUtils.isBlank(ed_channel_id)){
				access = false;
				errorMsg += "<br>请将第" + (i+1) + "行数据渠道补充完整。";
			}else{
				boolean exists = false;
				for(InvStockChannel isc : channels.getResult()){
					if(isc.getName().equals(ed_channel_id)){
						exists = true;
						ed_channel_id = isc.getChannelCode();
						break;
					}
				}
				if(exists == false){
					access = false;
					errorMsg += "<br>第" + (i+1) + "行数据渠道【" + ed_channel_id + "】填写错误。";
				}
			}

            ed_channel_id=invstockchannelmap.get(ed_channel_id);

			if(StringUtils.isBlank(qty) || !isInteger(qty) || Integer.valueOf(qty).intValue() <= 0){
				access = false;
				errorMsg += "<br>请将第" + (i+1) + "行可用库存数填写为正确的正整数。";
			}
			if(StringUtils.isBlank(estorge_id)){
				access = false;
				errorMsg += "<br>请将第" + (i+1) + "行WA库位码补充完整。";
			}else{
				Map params = new HashMap();
				params.put("estorageId", estorge_id);
				List<JdStorage> storageList = jdStorageService.getAllRrsWhByEstorgeOriginal(params);
				if(storageList == null || storageList.isEmpty()){
					access = false;
					errorMsg += "<br>第" + (i+1) + "行WA库位码【" + estorge_id + "】未在数据库中检索到相关信息，请检查是否填写正确。";
				}else{
					boolean exists = false;
					for(JdStorage js : storageList){
						if(js.getWhCode().equals(whCode)){
							exists = true;
							break;
						}
					}
					if(!exists){
						access = false;
						errorMsg += "<br>第" + (i+1) + "行WA库位码【" + estorge_id + "】未在数据库中检索到对应的京东库位码【" + whCode + "】，请检查是否填写正确。";
					}
				}
			}
			
		}
		
		if(access == false){
			resultMap.put("result", false);
			resultMap.put("msg", errorMsg);
			return resultMap;
		}
		
		for(int i = 1; i < list.size(); i++){
			String[] s = list.get(i);
			CrmOrderManualItem orderItem = new CrmOrderManualItem();
			CrmOrderManualDetailItem orderDetail = new CrmOrderManualDetailItem();
			
			String product_group_id = "";
			//付款方代码
            String custCode = "";
            String regionId = "";
//            String ed_channel_name = "";
			
			String materials_id = s[1];
			String qty = s[2];
			String whCode = s[4];
			String estorge_id = s[5];
			String ed_channel_id = s[6];
            ed_channel_id=invstockchannelmap.get(ed_channel_id);

            if(StringUtils.isBlank(materials_id)){
				continue;
			}
			
			Map<String, Object> params = new HashMap<>();
			params.put("estorageId", estorge_id);
			List<JdStorage> storageList = jdStorageService.getAllRrsWhByEstorgeOriginal(params);
			for(JdStorage js : storageList){
				if(js.getWhCode().equals(whCode)){
					regionId = js.getRegionId();
					break;
				}
			}
			
			
			orderItem.setMaker("");
			orderDetail.setMaker("");
			
			orderItem.setSource_order_id("");
			orderItem.setCustMgr("00022923");
//			orderDetail.setCustMgr("00022923");
			orderItem.setPorMgr("00022923");
//			orderDetail.setPorMgr("00022923");
			orderItem.setProDputy("00022923");
//			orderDetail.setProDputy("00022923");
//            orderDetail.setMaker(WebUtil.currentUserName(request));
            // 设置默认值
			orderItem.setBillType("ZGOR");
//            orderDetail.setBillType("ZGOR");
            orderItem.setSap_flow_num(2);
//            orderDetail.setSap_flow_num(2);
            orderItem.setWhCode(whCode);
//            orderDetail.setWhCode(whCode);
            
            orderDetail.setEd_channel_id(ed_channel_id);
            orderDetail.setMaterials_id(materials_id);
            
            orderDetail.setDn_id("");
            orderDetail.setSo_id("");
            
//            for(InvStockChannel isc : channels.getResult()){
//            	
//            }
            
            
            orderItem.setBudgetSort("30");
            
            
            if(StringUtils.isBlank(qty)){
            	
            }else{
            	orderDetail.setQty(Integer.valueOf(qty));
            }
            
            
            
			
            // 通过共通方法获取产品组和品牌map待用
//            Map<String, String> productgroupMap = new HashMap<String, String>();
//            Map<String, String> brandMap = new HashMap<String, String>();
//            commPurchase.getProductMap(productgroupMap);
//            commPurchase.getBrandMap(brandMap);

            // 权限Map
            Map<String, String> authMap = new HashMap<String, String>();
            // 取得产品组权限List,渠道权限List和品类List
//            commPurchase.getAuthMap(purchaseCommonService, request, null, null, null, authMap);
            
            //根据物料号补充内容
            if (materials_id != null && !materials_id.equals("")) {
                // 调用service执行检索
                ServiceResult<List<ItemBase>> itemResult = itemService
                        .findItemBaseByMaterialId(materials_id);
                if (itemResult.getSuccess() && itemResult.getResult() != null
                        && itemResult.getResult().size() > 0) {
                    // DB操作成功
                    ItemBase item = itemResult.getResult().get(0);
                    // 型号
                    String materials_desc = item.getMaterialDescription();
                    // 品牌code
                    String brand_id = item.getProBand();
                    // 品牌名称
                    String brand_name = brandMap.get(brand_id);
                    // 产品组code
                    product_group_id = item.getDepartment();
                    // 产品组名称
                    String product_group_name = productgroupMap.get(product_group_id);
                    //付款方
                    String payment_name = "";
                    

                    if("SC".equals(ed_channel_id) || "SC".equals(ed_channel_id)){
                    	custCode = "C200130028";
                    	payment_name = "海尔集团电子商务有限公司(顺逛全产业)";
                    }else if (org.apache.commons.lang.StringUtils.isNotBlank(product_group_name)) {
                		params = new HashMap<String, Object>();
                		params.put("productName", product_group_name);
                		List<ProductPayment> ppList = productPaymentService.findPaymentNameByCode(params);
                		if (ppList.size() > 0) {
                			payment_name = ppList.get(0).getPayMentName();
                			custCode = ppList.get(0).getPayMentCode();
	                	}
	                }

                    authMap = t2OrderService.getProductMap(authMap);
                    List<String> productGroupList = new ArrayList<>();
                    Iterator<Map.Entry<String, String>> it = authMap.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, String> entry = it.next();
                        productGroupList.add(entry.getKey());
                    }

//                    String[] authProductGroup = (String[]) authMap.get("productGroup");
//                    List<String> productGroupList = Arrays.asList(authProductGroup);
                    // 判断产品组id是否合法
                    if (productGroupList.contains(product_group_id)) {
                        // 产品组id合法，所需要的数据汇总并存入map
//                        Map<String, Object> Data = new HashMap<String, Object>();
//                        Data.put("payment_name", payment_name);//付款方
                    	orderItem.setPayment_name(payment_name);
//                        orderDetail.setPayment_name(payment_name);
//                        Data.put("custCode", custCode);//付款方代码
                        orderItem.setCustCode(custCode);
//                        orderDetail.setCustCode(custCode);
//                        Data.put("materials_desc", materials_desc);
                        orderDetail.setMaterials_desc(materials_desc);
//                        Data.put("brand_id", brand_id);
                        orderDetail.setBrand_id(brand_id);
//                        Data.put("brand_name", brand_name);
                        orderDetail.setBrand_name(brand_name);
//                        Data.put("product_group_id", product_group_id);
                        orderDetail.setProduct_group_id(product_group_id);
//                        Data.put("product_group_name", product_group_name);
                        orderDetail.setProduct_group_name(product_group_name);
                        
                    } else {
                        // 产品组id非法，
                    }
                } else {
                    // DB操作失败
                	access = false;
                	errorMsg += "<br>第" + (i+1) + "行物料号码【" + materials_id + "】未检索到相关信息，请检查是否填写正确。";
                }
            }
            
            
            
            
            //根据WA库位码补充内容
            if (estorge_id != null && !estorge_id.equals("")) {
            	
            	orderItem.setEstorge_id(estorge_id);
//            	orderDetail.setEstorge_id(estorge_id);

                // 通过电商库位码获取仓库
                ServiceResult<InvWarehouse> invWarehouseResult = purchaseBaseCommonService
                        .getAllWhByEstorgeId(estorge_id);
                if (invWarehouseResult.getSuccess() && invWarehouseResult.getResult() != null) {
                    Map<String, Object> Data = new HashMap<String, Object>();
                    // WA库位名
                    String estorge_name = invWarehouseResult.getResult().getEstorge_name();
                    // 工贸编码
                    String industry_trade_id = invWarehouseResult.getResult().getIndustry_trade_id();
                    // 工贸描述
                    String industry_trade_desc = invWarehouseResult.getResult()
                            .getIndustry_trade_desc();
                    // 管理客户编码
                    // String custom_id =
                    // invWarehouseResult.getResult().getCustom_id();
                    // 管理客户描述
                    // String custom_desc =
                    // invWarehouseResult.getResult().getCustom_desc();
                    // 付款方
                    // String payment_id = invWarehouseResult.getResult().getPayment_id();
                    //String payment_name = invWarehouseResult.getResult().getPayment_name();
                    // 送达方编码
                    String transmit_id = invWarehouseResult.getResult().getTransmit_id();
                    // 送达方描述
                    String transmit_desc = invWarehouseResult.getResult().getTransmit_desc();
                    // 配送中心编码
                    String distribution_center_id = invWarehouseResult.getResult().getCenterCode();
                    // 配送中心描述
                    String distribution_center_desc = invWarehouseResult.getResult()
                            .getRrs_distribution_name();

//                    Data.put("estorge_name", estorge_name);
                    orderItem.setEstorge_name(estorge_name);
//                    orderDetail.setEstorge_name(estorge_name);
//                    Data.put("corpCode", invWarehouseResult.getResult().getSale_org_id());
                    orderItem.setCorpCode(invWarehouseResult.getResult().getSale_org_id());
//                    orderDetail.setCorpCode(invWarehouseResult.getResult().getSale_org_id());
                    orderItem.setSaleOrgCode(invWarehouseResult.getResult().getSale_org_id());
//                    Data.put("corpName", invWarehouseResult.getResult().getBranch());
                    orderItem.setCorpName(invWarehouseResult.getResult().getBranch());
//                    orderDetail.setCorpName(invWarehouseResult.getResult().getBranch());
//                    Data.put("regionId", industry_trade_id);
                    orderItem.setRegionId(regionId);
//                    orderDetail.setRegionId(industry_trade_id);
//                    Data.put("industry_trade_desc", industry_trade_desc);
                    orderItem.setIndustry_trade_desc(industry_trade_desc);
//                    orderDetail.setIndustry_trade_desc(industry_trade_desc);
                    // Data.put("custCode", payment_id);
                    //Data.put("payment_name", payment_name);
//                    Data.put("destCode", transmit_id);
                    orderItem.setDestCode(transmit_id);
//                    orderDetail.setDestCode(transmit_id);
//                    Data.put("esale_name", transmit_desc);
                    orderItem.setEsale_name(transmit_desc);
//                    orderDetail.setEsale_name(transmit_desc);
//                    Data.put("destCenter", distribution_center_id);
                    orderItem.setDestCenter(distribution_center_id);
//                    orderDetail.setDestCenter(distribution_center_id);
//                    Data.put("rrs_distribution_name", distribution_center_desc);
                    orderItem.setRrs_distribution_name(distribution_center_desc);
//                    orderDetail.setRrs_distribution_name(distribution_center_desc);
                    Data.put("areaId", invWarehouseResult.getResult().getArea_id());
                    Data.put("sale_org_id", invWarehouseResult.getResult().getSale_org_id());
                    Data.put("branch", invWarehouseResult.getResult().getBranch());

                    // 预算体取得
                    if (product_group_id != null && !product_group_id.equals("")) {
                        // 预算体取得
                        params = new HashMap<String, Object>();
                        params.put("product_group_id", product_group_id);
                        params.put("area_id", invWarehouseResult.getResult().getArea_id());
                        ServiceResult<InvBudgetOrg> invBudgetOrg = purchaseBaseCommonService
                                .getAllBudgetOrg(params);
                        if (invBudgetOrg.getSuccess() && invBudgetOrg.getResult() != null) {
//                            Data.put("budgetOrg", invBudgetOrg.getResult().getBudgetorg_id());
                        	orderItem.setBudgetOrg(invBudgetOrg.getResult().getBudgetorg_id());
//                            orderDetail.setBudgetOrg(invBudgetOrg.getResult().getBudgetorg_id());
//                            Data.put("budgetOrgName", invBudgetOrg.getResult().getBudgetorg_name());
                            orderItem.setBudgetOrgName(invBudgetOrg.getResult().getBudgetorg_name());
//                            orderDetail.setBudgetOrgName(invBudgetOrg.getResult().getBudgetorg_name());
                        }

                    }
                    // 设置返回值
                } else {
                    // DB操作失败
                }

            }
           CrmManualOrder cmo = new CrmManualOrder();
           cmo.setCrmOrderManualItem(orderItem);
           cmo.setCrmOrderManualDetailItem(orderDetail);
           result.add(cmo);
		}
		
		if(access == true){
			resultMap.put("result", true);
			resultMap.put("list", result);
		}else{
			resultMap.put("result", false);
			resultMap.put("msg", errorMsg);
		}
		return resultMap;
	}

    @Override
    public CrmOrderManualItem getCrmOrderManualItem(String s) {
        return crmOrderManualModel.getCrmOrderManualItem(s);
    }

    @Override
    public List<CrmOrderManualDetailItem> getcrmOrderManualDetailItem(String s) {
        return crmOrderManualModel.getcrmOrderManualDetailItem(s);

    }

    static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
        return pattern.matcher(str).matches();  
  }
}
