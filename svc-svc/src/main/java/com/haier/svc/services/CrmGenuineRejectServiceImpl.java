package com.haier.svc.services;

import com.haier.purchase.data.model.*;
import com.haier.shop.model.VOMCancelOrderRequire;
import com.haier.shop.model.VOMOrderResponse;
import com.haier.stock.model.InventoryBusinessTypes;
import com.haier.stock.service.StockCenterHopStockService;
import com.haier.stock.service.VomOrderService;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Document;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.purchase.data.service.CrmGenuineRejectDataService;
import com.haier.purchase.data.service.OrderOperationLogService;
import com.haier.shop.model.VOMSynOrderRequire;
import com.haier.shop.model.VOMSynSubOrderRequire;
import com.haier.stock.model.InvMachineSet;
import com.haier.stock.service.StockCommonService;
import com.haier.svc.purchase.createscordertoles.CreateSCOrderToLESResponse;
import com.haier.svc.service.CrmGenuineRejectService;
import com.haier.svc.service.ItemService;
import com.haier.svc.service.PurchaseBaseCommonService;
import com.haier.svc.util.*;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.*;

@Service
public class CrmGenuineRejectServiceImpl implements CrmGenuineRejectService{

    private static Logger log = LoggerFactory
            .getLogger(CrmGenuineRejectServiceImpl.class);

    @Autowired
    private CrmGenuineRejectDataService crmGenuineRejectDataService;
    @Autowired
    private OrderOperationLogService orderOperationLogService;
    @Autowired
    private PurchaseBaseCommonService purchaseBaseCommonService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private StockCommonService stockCommonService;
    @Autowired
    private StockCenterHopStockService stockCenterHopStockService;
    @Autowired
    private VomOrderService vomOrderService;

    //获取价格地址
    @Value("${vomSynOrderUrl}")
    private String vomSynOrderUrl;

    @Override
    public ServiceResult<List<CrmGenuineRejectItem>> getCrmGenuineRejectList(Map<String, Object> params) {
        ServiceResult<List<CrmGenuineRejectItem>> result = new ServiceResult<List<CrmGenuineRejectItem>>();
        try {
            result.setResult(crmGenuineRejectDataService.getCrmGenuineRejectList(params));
            PagerInfo pi = new PagerInfo();
            pi.setRowsCount(crmGenuineRejectDataService.getCrmGenuineRejectListCNT(params));
            result.setPager(pi);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("获取CRM正品退货list失败：", e);
        }
        return result;
    }

    @Override
    public ServiceResult<Boolean> checkSoidSame(String so_id) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        if (crmGenuineRejectDataService.checkSoidSame(so_id) > 0) {
            result.setResult(false);
        } else {
            result.setResult(true);
        }
        return result;
    }

    /**
     * 创建退货单
     *
     * @param params
     * @return
     * @see
     */
    @Override
    public ServiceResult<Boolean> insertCrmReturnInfoList(Map<String, Object> params) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            // 创建退货单
            result.setResult(crmGenuineRejectDataService.insertCrmReturnInfoList(params));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("CRM退货单创建失败：", e);
        }
        return result;
    }

    @Override
    public void insertOrderOperationLog(List<OrderOperationLog> loglist) {
        orderOperationLogService.insertOrderOperationLog(loglist);
    }

    /**
     * 提交订单
     *
     * @param params
     * @return
     */
    @Override
    public ServiceResult<Boolean> commitCrmGenuineRejectList(Map<String, Object> params) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            List<String> commitList = new ArrayList<String>();
            commitList = (List<String>) params.get("commitList");
            List<String> orderidList = commitList;
            int type = 0;
            String operateuser = (String) params.get("commituser");
            String content = "CRM提交订单,设置订单状态为已提交";
            // 设置订单状态为已提交
            if (commitList.size() > 0) {
                result.setResult(crmGenuineRejectDataService.commitCrmGenuineRejectList(params));
                if (result.getResult()) {
                    result.setMessage("设置订单状态成功");
                    log.info("设置订单状态成功：" + JsonUtil.toJson(params));
                } else {
                    result.setSuccess(false);
                    result.setMessage("设置订单状态失败");
                    log.error("设置订单状态失败：" + JsonUtil.toJson(params));
                }
                // TODO 正品退货 提交订单写入日志
                orderOperationLog(result, orderidList, type, content, operateuser);
            } else {
                result.setSuccess(false);
                result.setMessage("正品退货提交退货订单,占货处理失败");
            }
            if (commitList.size() > 0) {
                // 发送开出WA提单
                List<CreateSCOrderToLESResponse> out = new ArrayList<CreateSCOrderToLESResponse>();
                for (String wp_order_id : commitList) {
                    Map<String, Object> require = new HashMap<String, Object>();
                    require.put("wp_order_id", wp_order_id);
                    List<CrmGenuineRejectItem> rejectList = crmGenuineRejectDataService
                            .getCrmGenuineRejectList(require);

					/*
					 * * for (CrmGenuineRejectItem item : rejectList) {
					 * List<CrmGenuineRejectItem> message = new
					 * ArrayList<CrmGenuineRejectItem>(); message.add(item);
					 * List<CreateSCOrderToLESResponse> r = lesOrderModel
					 * .CreateSCOrderToLESMethod(message); out.addAll(r); }
					 */

                    log.info("获得正品退货列表：" + JsonUtil.toJson(rejectList));
                    for (CrmGenuineRejectItem item : rejectList) {
                        VOMSynOrderRequire synOrderRequire = this.getVOMSynOrderInfo(item);
                        String contentXml = getVomContentXml(synOrderRequire);
                        log.info("-------------------调用VOM正向下发接口contentXml：" + contentXml);
                        StringBuffer sb = new StringBuffer();
                        sb.append("CBS")
                                .append(DateFormatUtil.getCurrentDateWithFormat("yyyyMMddHHmmss"))
                                .append(CommUtil.getRandomString(8));
                        String interXml = getVomInterXml(contentXml, sb.toString());
                        log.info("-------------------调用VOM正向下发接口interXml：" + interXml);
                        String responseStr = Httpclient.send(vomSynOrderUrl, interXml,
                                "text/html; charset=utf-8");
                        log.info("-------------------调用VOM正向下发接口返回参数responseStr：" + responseStr);
                        Map<String, String> resultMap = getVomRequestResult(responseStr);
                        String flag = resultMap.get("flag");
                        String orderId = item.getWp_order_id();
                        type = 10;
                        if ("T".equals(flag)) {
                            result.setMessage("更新成功！");
                            // 更新leader_nb_return_order_t状态
                            Map<String, Object> paramMap = new HashMap<String, Object>();
                            paramMap.put("flow_flag", 15);
                            paramMap.put("source_order_id", item.getWp_order_id());
                            crmGenuineRejectDataService.updateCrmReturnInfo(paramMap);
                        } else {
                            String msg = resultMap.get("msg");
                            result.setSuccess(false);
                            result.setMessage("更新失败！");
                            CrmGenuineRejectItem crmGenuineRejectItem = new CrmGenuineRejectItem();
                            crmGenuineRejectItem.setError_msg(msg);
                            crmGenuineRejectItem.setWp_order_id(item.getWp_order_id());
                            crmGenuineRejectDataService.updateRemark(crmGenuineRejectItem);
                            log.error("推送VOM失败:" + JsonUtil.toJson(item));

                        }
                    }
                }

                // TODO 正品退货 提交订单写入日志
                content = "CRM提交订单,发送开出WA提单";
                orderOperationLog(result, orderidList, type, content, operateuser);

            } else {
                result.setMessage("正品退货提交退货订单，发送开出WA提单失败");
                result.setSuccess(false);
                log.error("正品退货提交退货订单，发送开出WA提单失败：" + JsonUtil.toJson(params));
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("提交订单失败：" + JsonUtil.toJson(params), e);
        }
        return result;
    }

    /**
     * 删除订单
     *
     * @param params
     */
    @Override
    public void deleteCrmGenuineRejectList(Map<String, Object> params) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        try {
            // 删除订单
            result.setResult(crmGenuineRejectDataService.deleteCrmGenuineRejectList(params));
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("删除订单失败：", e);
        }
    }
    private Map<String, String> getVomRequestResult(String xmlStr) throws DocumentException {
        Document doc = DocumentHelper.parseText(xmlStr);
        Element rootElt = doc.getRootElement();
        String flag = rootElt.element("flag").getStringValue();
        String msg = rootElt.element("msg").getStringValue();
        Map<String, String> map = new HashMap<String, String>();
        map.put("flag", flag);
        map.put("msg", msg);
        return map;
    }
    /**
     * 封装请求参数(vom开提单)
     *
     * @param vomSynOrderRequire
     * @return
     */
    public static String getVomContentXml(VOMSynOrderRequire vomSynOrderRequire) {

        StringBuffer sb = new StringBuffer();
        sb.append("<Order>");
        if (vomSynOrderRequire == null) {
            sb.append("</Order>");
            return sb.toString();
        }

        sb.append("<orderno>" + vomSynOrderRequire.getOrderNo() + "</orderno>");// 网单号
        sb.append("<sourcesn>" + vomSynOrderRequire.getSourceSn() + "</sourcesn>");// 订单号
        sb.append("<ordertype>" + vomSynOrderRequire.getOrderType() + "</ordertype>");// 订单类型
        sb.append("<bustype>" + vomSynOrderRequire.getBusType() + "</bustype>");// 业务类型
        sb.append("<expno></expno>");// 快递单号
        sb.append("<orderdate>" + vomSynOrderRequire.getOrderDate() + "</orderdate>");// 订单时间（格式：YYYY-MM-DD HH:MM:SS）
        sb.append("<storecode>" + vomSynOrderRequire.getStoreCode() + "</storecode>");// scode转
        // 中心代码
        // 仓库编码：按日日顺C码
        sb.append("<province>" + vomSynOrderRequire.getProvince() + "</province>");// 收货人所在省
        sb.append("<city>" + vomSynOrderRequire.getCity() + "</city>");// 收货人所在市
        sb.append("<county>" + vomSynOrderRequire.getCounty() + "</county>");// 收货人所在县/区
        sb.append("<addr><![CDATA[" + vomSynOrderRequire.getAddr() + "]]></addr>");// 详细地址
        sb.append("<gbcode>" + vomSynOrderRequire.getGbCode() + "</gbcode>");// 国标码
        sb.append("<name><![CDATA[" + vomSynOrderRequire.getName() + "]]></name>");// 送达方姓名：收货人姓名
        sb.append("<mobile>" + vomSynOrderRequire.getMobile() + "</mobile>");// 联系电话
        sb.append("<tel>" + vomSynOrderRequire.getTel() + "</tel>");// 固定电话
        sb.append("<postcode>" + vomSynOrderRequire.getPostCode() + "</postcode>");// 邮政编码
        sb.append("<deldate>" + vomSynOrderRequire.getDelDate() + "</deldate>");// 预约时间：预约送货时间
        sb.append("<insdate>" + vomSynOrderRequire.getInsDate() + "</insdate>");// 预约时间：预约安装时间
        sb.append("<reorder>" + vomSynOrderRequire.getReorder() + "</reorder>");// 前续订单号：关联单号
        sb.append("<freight>" + vomSynOrderRequire.getFreight() + "</freight>");// 运费
        sb.append("<billsum>" + vomSynOrderRequire.getBillSum() + "</billsum>");// 订单总金额
        sb.append("<billowe>" + vomSynOrderRequire.getBillOwe() + "</billowe>");// 代收金额：应收欠款
        sb.append("<paystate>" + vomSynOrderRequire.getPayState() + "</paystate>");// 付款状态：P1-已付款，P2-代收货款
        sb.append("<paytime>" + vomSynOrderRequire.getPayTime() + "</paytime>");// 付款时间//
        // ---订单为cod时付款时间为空
        sb.append("<paytype>" + vomSynOrderRequire.getPayType() + "</paytype>");// 支付类别（如支付宝、现金、银联等)
        sb.append("<isinv>" + vomSynOrderRequire.getIsInv() + "</isinv>");// 是否需要开具发票：1.是
        // 2.否
        sb.append("<invtype>" + vomSynOrderRequire.getInvType() + "</invtype>");// 发票类型
        if (!"".equals(vomSynOrderRequire.getInvRise())
                && vomSynOrderRequire.getInvRise() != null) {
            sb.append("<invrise><![CDATA[" + vomSynOrderRequire.getInvRise() + "]]></invrise>");// 发票抬头
        } else {
            sb.append("<invrise></invrise>");
        }
        sb.append("<taxbearer>" + vomSynOrderRequire.getTaxBearer() + "</taxbearer>");// 纳税人登记号
        if (!"".equals(vomSynOrderRequire.getRecAddr())
                && vomSynOrderRequire.getRecAddr() != null) {
            sb.append("<recaddr><![CDATA[" + vomSynOrderRequire.getRecAddr() + "]]></recaddr>");// 发票地址
        } else {
            sb.append("<recaddr></recaddr>");
        }
        sb.append("<recacc>" + vomSynOrderRequire.getRecAcc() + "</recacc>");// 发票开户行帐号
        if (!"".equals(vomSynOrderRequire.getRecBank())
                && vomSynOrderRequire.getRecBank() != null) {
            sb.append("<recbank><![CDATA[" + vomSynOrderRequire.getRecBank() + "]]></recbank>");// 发票开户行
        } else {
            sb.append("<recbank></recbank>");
        }
        sb.append("<sname>" + vomSynOrderRequire.getSname() + "</sname>");// 发货人姓名
        sb.append("<sprovince>" + vomSynOrderRequire.getSprovince() + "</sprovince>");// 发货人所在省
        sb.append("<scity>" + vomSynOrderRequire.getScity() + "</scity>");// 发货人所在市
        sb.append("<scounty>" + vomSynOrderRequire.getScounty() + "</scounty>");// 发货人所在县/区
        sb.append("<saddr>" + vomSynOrderRequire.getSaddr() + "</saddr>");// 发货人详细地址
        sb.append("<smobile>" + vomSynOrderRequire.getSmobile() + "</smobile>");// 发货人联系电话
        sb.append("<stel>" + vomSynOrderRequire.getStel() + "</stel>");// 发货人固定电话
        sb.append("<busflag>" + vomSynOrderRequire.getBusFlag() + "</busflag>");// 订单标记
        // 1送装一体
        // 2.只配送
        // 3.开箱验货

        if (!"".equals(vomSynOrderRequire.getRemark()) && vomSynOrderRequire.getRemark() != null) {
            sb.append("<remark><![CDATA[" + vomSynOrderRequire.getRemark() + "]]></remark>");// 备注
        } else {
            sb.append("<remark></remark>");
        }
        sb.append("<attributes></attributes>");// 属性备注
        sb.append("<remark1>WATC</remark1>");// 备用字段 --不填
        sb.append("<remark2></remark2>");// 备用字段 --不填
        sb.append("<remark3></remark3>");// 备用字段 --不填
        sb.append("<remark4></remark4>");// 备用字段 --不填
        sb.append("<remark5></remark5>");// 备用字段 --不填
        sb.append("<remark6></remark6>");// 备用字段 --不填
        sb.append("<remark7></remark7>");// 备用字段 --不填
        sb.append("<remark8>1</remark8>");// 备用字段 --不填

        // 子订单
        sb.append("<items>");
        List<VOMSynSubOrderRequire> subOrderList = vomSynOrderRequire.getSubOrderList();
        if (subOrderList.size() > 0) {
            for (VOMSynSubOrderRequire synSubOrderRequire : subOrderList) {
                sb.append("<Item>");
                sb.append("<itemno>" + synSubOrderRequire.getItemNo() + "</itemno>");
                sb.append("<storagetype>" + synSubOrderRequire.getStorageType() + "</storagetype>");// 批次 产品状态:10 正品 21 不良 22 破箱 40 样品
                // L0礼品
                sb.append("<productcode>" + synSubOrderRequire.getProductCode() + "</productcode>");// 客户产品编码 -- sku 套机填sub_sku
                sb.append("<hrcode>" + synSubOrderRequire.getHrCode() + "</hrcode>");// 海尔产品编码// 日日顺物流生成
                sb.append("<prodes>" + synSubOrderRequire.getProdes() + "</prodes>");// 产品描述
                sb.append("<volume>" + synSubOrderRequire.getVolume() + "</volume>");// 体积
                sb.append("<weight>" + synSubOrderRequire.getWeight() + "</weight>");// 重量
                sb.append("<number>" + synSubOrderRequire.getNumber() + "</number>");// 网单数量乘以组件数量// 不是套机填网单数量
                sb.append("<unprice>" + synSubOrderRequire.getUnprice() + "</unprice>");// 单价
                sb.append("<reitem>" + synSubOrderRequire.getReitem() + "</reitem>");// 前续订单行号(关联行号)
                sb.append("<attributes></attributes>");// 属性备注
                sb.append("<remark1></remark1>");// 备用
                sb.append("<remark2></remark2>");// 备用
                sb.append("</Item>");
            }
        } else {
            sb.append("<Item>");
            sb.append("<itemno></itemno>");
            sb.append("<storagetype></storagetype>");
            sb.append("<productcode></productcode>");
            sb.append("<hrcode></hrcode>");
            sb.append("<prodes></prodes>");
            sb.append("<volume></volume>");
            sb.append("<weight></weight>");
            sb.append("<number></number>");
            sb.append("<unprice></unprice>");
            sb.append("<reitem></reitem>");
            sb.append("<attributes></attributes>");
            sb.append("<remark1></remark1>");
            sb.append("<remark2></remark2>");
            sb.append("</Item>");
        }
        sb.append("</items>");
        sb.append("</Order>");

        return sb.toString();
    }


    public static String getVomInterXml(String content, String notifyId) throws Exception {
        VomInterData vomInterData = new VomInterData();
        List<VomInterData> list = new ArrayList<VomInterData>();
        // notifyId
        vomInterData.setNotifyid(notifyId);
        // notifyTime
        String dateStr = DateFormatUtil.getCurrentDateWithFormat("yyyy-MM-dd HH:mm:ss");
        vomInterData.setNotifytime(dateStr);
        // butype
        vomInterData.setButype("rrs_order");
        // content
        String aesContent = AESUtil.Encrypt(content, "KeLy8g7qjmnbgWP1").replaceAll("\n", "");
        String encodeContent = URLEncoder.encode(aesContent, "utf-8");
        vomInterData.setContent(encodeContent);
        // sign
        String md5Str = MD5util.encrypt(content + "RRS,123");// 测试秘钥：Haier,123
        // 生产环境秘钥：RRS,123
        String base64Str = Base64Util.encode(md5Str.getBytes()).replaceAll("\r\n", "");
        vomInterData.setSign(base64Str);
        // 接口方法名
        vomInterData.setButype("rrs_order");
        // 根据系统区分来源
        vomInterData.setSource("HAIERSC");
        // 报文格式
        vomInterData.setType("xml");

        list.add(vomInterData);

        return ObjectUtil.getObjectToURLString(list);

    }

    public VOMSynOrderRequire getVOMSynOrderInfo(CrmGenuineRejectItem crmGenuineRejectItem) {
        VOMSynOrderRequire synOrderRequire = new VOMSynOrderRequire();
        String sourceOrderId = crmGenuineRejectItem.getWp_order_id();
        synOrderRequire.setOrderNo(sourceOrderId);// 订单号
        synOrderRequire.setSourceSn(crmGenuineRejectItem.getSo_id());// 来源订单号
        synOrderRequire.setOrderType("5");
        if(crmGenuineRejectItem.getStorage_id()==null){
            return null;
        }else if("ZZWA".equals(crmGenuineRejectItem.getStorage_id())){
            synOrderRequire.setBusType("70");
        }else {
            synOrderRequire.setBusType("1");
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("wp_order_id", sourceOrderId);

        List<WAAddressInfo> waAddressList = crmGenuineRejectDataService.findWAAddressInfo(params);
        if (waAddressList.size() > 0) {
            WAAddressInfo waAddressInfo = waAddressList.get(0);
            String sCode = waAddressInfo.getsCode();
            ServiceResult<List<WAAddress>> serviceResult = purchaseBaseCommonService
                    .getAllWAAddressInfo(sCode);
            if (serviceResult.getSuccess() && serviceResult.getResult() != null
                    && serviceResult.getResult().size() > 0) {
                WAAddress waAddress = serviceResult.getResult()
                        .get(0);
                synOrderRequire.setOrderDate(waAddressInfo.getCreateTime());// 订单时间
                synOrderRequire.setStoreCode(waAddress.getcCode());// 日日顺C码
                synOrderRequire.setProvince(waAddress.getProvince());// 省
                synOrderRequire.setCity(waAddress.getCity());// 市
                synOrderRequire.setCounty(waAddress.getCounty());// 区

                // 根据省市区获得国标码
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("provinceName", waAddress.getProvince());
                param.put("cityName", waAddress.getCity());
                param.put("areaName", waAddress.getCounty());
                ServiceResult<String> gbcode = this.itemService.getRegionsCode(param);
                if (gbcode.getSuccess() && gbcode.getResult() != null) {
                    synOrderRequire.setGbCode(gbcode.getResult());
                }

                synOrderRequire.setAddr(waAddress.getAddress());// 详细地址
                synOrderRequire.setName(waAddress.getContact_crm());// 收货人姓名
                synOrderRequire.setMobile(waAddress.getMobilePhone());// 联系电话
                synOrderRequire.setPostCode(waAddress.getZipCode());// 邮政编码
            }
        }

        List<VOMSynSubOrderRequire> subOrderList = new ArrayList<VOMSynSubOrderRequire>();
        List<Map<String, Object>> orderInfoList = crmGenuineRejectDataService
                .findCrmReturnOrderInfo(params);
        if (orderInfoList.size() > 0) {
            Map<String, Object> map = orderInfoList.get(0);
            // 根据主SKU取得子SKU
            InvMachineSet tempSet = new InvMachineSet();
            tempSet.setMainSku((String) map.get("materials_id"));
            ServiceResult<List<InvMachineSet>> resultSet =stockCommonService.getSubMachinesByMainSku(tempSet);
            if (resultSet.getSuccess() && resultSet.getResult() != null) {
                if (resultSet.getResult().size() > 0) {
                    // 子SKU存在场合
                    Integer itemNo = 0;
                    for (InvMachineSet invMachineSet : resultSet.getResult()) {
                        VOMSynSubOrderRequire synSubOrderRequire = new VOMSynSubOrderRequire();
                        itemNo++;
                        synSubOrderRequire.setItemNo(itemNo.toString());
                        synSubOrderRequire.setStorageType("10");// 正品
                        // 子SKU
                        String materialsId = invMachineSet.getSubSku();
                        String categoryId = (String) map.get("category_id");
                        Integer quantity = (Integer) map.get("quantity");
                        synSubOrderRequire.setProductCode(materialsId);
                        synSubOrderRequire.setHrCode(materialsId);
                        synSubOrderRequire.setProdes(categoryId);
                        synSubOrderRequire.setNumber(quantity);

                        subOrderList.add(synSubOrderRequire);
                    }
                } else {
                    // 子SKU不存在场合
                    VOMSynSubOrderRequire synSubOrderRequire = new VOMSynSubOrderRequire();
                    synSubOrderRequire.setItemNo("1");
                    synSubOrderRequire.setStorageType("10");// 正品
                    // 子SKU
                    String materialsId = (String) map.get("materials_id");
                    String categoryId = (String) map.get("category_id");
                    Integer quantity = (Integer) map.get("quantity");
                    synSubOrderRequire.setProductCode(materialsId);
                    synSubOrderRequire.setHrCode(materialsId);
                    synSubOrderRequire.setProdes(categoryId);
                    synSubOrderRequire.setNumber(quantity);

                    subOrderList.add(synSubOrderRequire);
                }
            }
        }

        synOrderRequire.setSubOrderList(subOrderList);

        return synOrderRequire;
    }
    // 写入日志
    private void orderOperationLog(ServiceResult<Boolean> result, List<String> orderidList,
                                   int type, String content, String operateuser) {
        List<OrderOperationLog> loglist = new ArrayList<OrderOperationLog>();
        for (String order_id : orderidList) {
            OrderOperationLog orderOperationLog = new OrderOperationLog();
            orderOperationLog.setOrder_id(order_id);
            orderOperationLog.setType(type);
            orderOperationLog.setOperate_user(operateuser);
            orderOperationLog.setSystem("采购平台");
            if (result.getSuccess()) {
                orderOperationLog.setContent(content + ",状态变更为已提交");
                orderOperationLog.setIs_sucess("1");
                orderOperationLog.setRemark(result.getMessage());
            } else {
                orderOperationLog.setContent(content);
                orderOperationLog.setIs_sucess("0");
                orderOperationLog.setRemark(result.getMessage());
            }
            loglist.add(orderOperationLog);
        }
        try {
            orderOperationLogService.insertOrderOperationLog(loglist);
        } catch (Exception e) {
            log.error("操作日志写入失败：", e);
        }

    }
    /**
     * 取消退货单
     *
     * @param
     * @return
     */
    @Override
    public String updateFlowFlagCancel(List<String> cancelList) {
        Map<String, String> failed = new HashMap<String, String>();
        List<String> successList = new ArrayList<String>();
        String result = "";
        try {
            for (String wp_order_id : cancelList) {
                Map<String, Object> require = new HashMap<String, Object>();
                require.put("wp_order_id", wp_order_id);
                List<CrmGenuineRejectItem> orders = crmGenuineRejectDataService
                        .getCrmGenuineRejectList(require);
                if (orders == null || orders.isEmpty() == true)
                    return "CRM退货单取消失败,找不到对应订单号";
                for (CrmGenuineRejectItem item : orders) {
					/*
					 * ServiceResult<Boolean> s =
					 * lesOrderModel.CancelSCOrderToLESMethod(item.getVbeln());
					 * if (s.getResult() == false) {
					 * failed.put(item.getWp_order_id(), s.getMessage()); } else
					 * { successList.add(item.getWp_order_id()); }
					 */

                    // VOM取消订单
                    boolean isVOMSuccess = true;
                    String orderId = item.getWp_order_id();
                    Integer flowFlag = item.getFlow_flag();
                    if (flowFlag == 10) {// 已提交
                        List<String> list = new ArrayList<String>();
                        list.add(orderId);
                        crmGenuineRejectDataService.updateFlowFlagCancel(list);
                        result = "正品退货取消订单成功";
                    } else if (flowFlag == 15) {// 同步到VOM
                        VOMCancelOrderRequire cancelOrderRequire = new VOMCancelOrderRequire();
                        cancelOrderRequire.setOrderNo(orderId);
                        cancelOrderRequire.setCancelType("2");// 2：人工拦截订单
                        ServiceResult<VOMOrderResponse> vomOrderResponseResule = vomOrderService
                                .cancelVomOrderInfo(cancelOrderRequire);
                        VOMOrderResponse vomOrderResponse=vomOrderResponseResule.getResult();
                        String flag = vomOrderResponse.getFlag();
                        if ("T".equals(flag)) {
                            List<String> list = new ArrayList<String>();
                            list.add(orderId);
                            crmGenuineRejectDataService.updateFlowFlagCancel(list);
                            result = "正品退货取消订单成功";
                            isVOMSuccess = true;
                        } else {
                            failed.put(item.getWp_order_id(), vomOrderResponse.getMsg());
                            crmGenuineRejectDataService.updateAfterCancelFailed(failed);
                            result = vomOrderResponse.getMsg();
                            isVOMSuccess = false;
                        }
                    } else if (flowFlag == 20) {// 待出库
                        VOMCancelOrderRequire cancelOrderRequire = new VOMCancelOrderRequire();
                        cancelOrderRequire.setOrderNo(orderId);
                        cancelOrderRequire.setCancelType("2");// 2：人工拦截订单
                        ServiceResult<VOMOrderResponse> vomOrderResponseResule = vomOrderService
                                .cancelVomOrderInfo(cancelOrderRequire);
                        VOMOrderResponse vomOrderResponse=vomOrderResponseResule.getResult();
                        String flag = vomOrderResponse.getFlag();
                        if ("T".equals(flag)) {
                            List<String> list = new ArrayList<String>();
                            list.add(orderId);
                            crmGenuineRejectDataService.updateFlowFlagCancel(list);
                            result = "正品退货取消订单成功";
                            isVOMSuccess = true;
                        } else {
                            failed.put(item.getWp_order_id(), vomOrderResponse.getMsg());
                            crmGenuineRejectDataService.updateAfterCancelFailed(failed);
                            result = vomOrderResponse.getMsg();
                            isVOMSuccess = false;
                        }
                    }

                    // VOM取消订单

                    // 取消占货
                    if (isVOMSuccess) {
                        ServiceResult<Boolean> serviceResult = this.stockCenterHopStockService
                                .releaseFrozenStockQty(item.getMaterials_id(), item.getQuantity(),
                                        item.getWp_order_id(), InventoryBusinessTypes.RELEASE_BY_ZBCC);
                        if (serviceResult.getSuccess() && serviceResult.getResult()) {
                            log.info("释放库存成功:" + JsonUtil.toJson(item));
                        }else{
                            log.info("释放库存失败:" + JsonUtil.toJson(item));
                        }
                    }
                }
            }
        } catch (Exception e) {
            result = "系统异常，正品退货取消订单失败";
        }

        return result;
    }

    /**
     *
     * @title getOrderOperationLogInfo
     * @description 通过字段order_id和sub_order_id查询数据库表order_operation_log_t中数据
     */
    @Override
    public ServiceResult<List<OrderOperationLog>> getOrderOperationLogInfo(Map<String, Object> params) {
        ServiceResult<List<OrderOperationLog>> result = new ServiceResult<List<OrderOperationLog>>();
        try {
            result.setResult(orderOperationLogService.getOrderOperationLogInfo(params));
        } catch (Exception e) {
            log.error("获取库信息时，发生未知异常：", e);
            result.setMessage("服务器发生未知异常：" + e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public List<CrmGenuineRejectItem> getRejectWdOrderId(String wpOrderId) {
        return crmGenuineRejectDataService.getRejectWdOrderId(wpOrderId);
    }

    @Override
    public void updateRemark(CrmGenuineRejectItem crmGenuineRejectItem) {
        crmGenuineRejectDataService.updateRemark(crmGenuineRejectItem);
    }

    public ServiceResult<Boolean> cancelInWaCrmGenuineRejectList(List<String> stopList) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        result.setResult(false);
        if (stopList != null && stopList.size() != 0) {
            Map<String, Object> orderRejectMap = new HashMap<String, Object>();
            for (String wpOrderId : stopList) {
                orderRejectMap.put("wpOrderId", wpOrderId);
                List<CrmOrderRejectItem> crmOrderRejectList = crmGenuineRejectDataService
                        .findCrmOrderRejectList(orderRejectMap);
                if (crmOrderRejectList != null && crmOrderRejectList.size() > 0) {
                    CrmOrderRejectItem orderRejectItem = crmOrderRejectList.get(0);
                    String vomReverseInWaNo = orderRejectItem.getVomReverseInWaNo();
                    VOMCancelOrderRequire cancelOrderRequire = new VOMCancelOrderRequire();
                    cancelOrderRequire.setOrderNo(vomReverseInWaNo);
                    cancelOrderRequire.setCancelType("2");// 2：人工拦截订单
                    ServiceResult<VOMOrderResponse> vomOrderResponse = vomOrderService
                            .cancelVomOrderInfo(cancelOrderRequire);

                    if (vomOrderResponse.getSuccess() && vomOrderResponse.getResult() != null) {
                        String flag = vomOrderResponse.getResult().getFlag();
                        if ("T".equals(flag)) {
                            orderRejectMap.put("flow_flag", 30);
                            orderRejectMap.put("source_order_id", orderRejectItem.getOrderId());
                            crmGenuineRejectDataService.updateFlowFlagCancelInWa(orderRejectMap);
                            result.setResult(true);
                            result.setMessage(" VOM提单号：" + vomReverseInWaNo);
                        } else {
                            result.setResult(false);
                            result.setMessage("取消入WA提单失败" + vomOrderResponse.getResult().getMsg()
                                    + " VOM提单号：" + vomReverseInWaNo);
                        }
                    } else {
                        result.setResult(false);
                        result.setMessage("调用VOM接口失败,取消入WA提单失败" + " VOM提单号：" + vomReverseInWaNo);
                    }

                }
            }
        }
        return result;
    }

    public ServiceResult<Boolean> stopCrmGenuineRejectList(List<String> stopList) {
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        result.setResult(false);
        try {
            List<Map<String, Object>> successMapList = new ArrayList<Map<String, Object>>();
            Map<String, Object> paramMap = new HashMap<String, Object>();
            String vomReverseInWaNo = "";
            if (stopList != null && !stopList.isEmpty()) {

                List<String> sucessList = new ArrayList<String>();
                for (String wpOrderId : stopList) {
                    Map<String, Object> orderRejectMap = new HashMap<String, Object>();
                    VOMSynOrderRequire synOrderRequire = new VOMSynOrderRequire();
                    orderRejectMap.put("wpOrderId", wpOrderId);
                    List<CrmOrderRejectItem> cgoOrderRejectList = crmGenuineRejectDataService
                            .findCrmOrderRejectList(orderRejectMap);
                    Date now = new Date();
                    if (cgoOrderRejectList.size() > 0) {
                        CrmOrderRejectItem orderRejectItem = cgoOrderRejectList.get(0);
                        vomReverseInWaNo = orderRejectItem.getVomReverseInWaNo();
                        String orderId = orderRejectItem.getOrderId();
                        if ("".equals(vomReverseInWaNo) || vomReverseInWaNo == null) {
                            vomReverseInWaNo = orderId + "T1";
                        } else {
                            int index = vomReverseInWaNo.lastIndexOf("T");
                            int num = Integer.parseInt(vomReverseInWaNo.substring(index + 1)) + 1;
                            vomReverseInWaNo = vomReverseInWaNo.substring(0, index + 1) + num;
                        }

                        synOrderRequire.setOrderNo(vomReverseInWaNo);
                        // String sourceOrderId =
                        // orderRejectItem.getSourceOrderId();
                        synOrderRequire.setSourceSn(orderId);
                        synOrderRequire.setOrderType("10");
                        synOrderRequire.setBusType("2");

                        String orderDate = DateFormatUtil.getCurrentDateWithFormat(now,
                                "yyyy-MM-dd HH:mm:ss");
                        synOrderRequire.setOrderDate(orderDate);
                        String sCode = orderRejectItem.getStorageId();
                        ServiceResult<List<WAAddress>> serviceResult = purchaseBaseCommonService
                                .getAllWAAddressInfo(sCode);
                        if (serviceResult.getSuccess() && serviceResult.getResult() != null
                                && serviceResult.getResult().size() > 0) {
                            WAAddress waAddress = serviceResult
                                    .getResult().get(0);
                            synOrderRequire.setStoreCode(waAddress.getcCode());// 日日顺C码
                            synOrderRequire.setProvince(waAddress.getProvince());// 省
                            synOrderRequire.setCity(waAddress.getCity());// 市
                            synOrderRequire.setCounty(waAddress.getCounty());// 区

                            // 根据省市区获得国标码
                            Map<String, Object> param = new HashMap<String, Object>();
                            param.put("provinceName", waAddress.getProvince());
                            param.put("cityName", waAddress.getCity());
                            param.put("areaName", waAddress.getCounty());
                            ServiceResult<String> gbcode = this.itemService.getRegionsCode(param);
                            if (gbcode.getSuccess() && gbcode.getResult() != null) {
                                synOrderRequire.setGbCode(gbcode.getResult());
                            }

                            synOrderRequire.setAddr(waAddress.getAddress());// 详细地址
                            synOrderRequire.setName(waAddress.getContact_crm());// 收货人姓名
                            synOrderRequire.setMobile(waAddress.getMobilePhone());// 联系电话
                            synOrderRequire.setPostCode(waAddress.getZipCode());// 邮政编码
                        }

                        List<VOMSynSubOrderRequire> subOrderList = new ArrayList<VOMSynSubOrderRequire>();
                        VOMSynSubOrderRequire synSubOrderRequire = new VOMSynSubOrderRequire();
                        synSubOrderRequire.setItemNo("1");
                        synSubOrderRequire.setStorageType("10");
                        Map<String, Object> infoMap = new HashMap<String, Object>();
                        infoMap.put("wp_order_id", wpOrderId);
                        List<Map<String, Object>> orderInfoList = crmGenuineRejectDataService
                                .findCrmReturnOrderInfo(infoMap);
                        if (orderInfoList.size() > 0) {
                            Map<String, Object> orderInfoMap = orderInfoList.get(0);
                            String materialsId = (String) orderInfoMap.get("materials_id");
                            String categoryId = (String) orderInfoMap.get("category_id");
                            Integer quantity = (Integer) orderInfoMap.get("quantity");
                            synSubOrderRequire.setProductCode(materialsId);
                            synSubOrderRequire.setHrCode(materialsId);
                            synSubOrderRequire.setProdes(categoryId);
                            synSubOrderRequire.setNumber(quantity);
                        }
                        subOrderList.add(synSubOrderRequire);
                        synOrderRequire.setSubOrderList(subOrderList);
                    } else {
                        continue;
                    }

                    ServiceResult<VOMOrderResponse> vomOrderResponseSR = vomOrderService
                            .synOrderInfo(synOrderRequire);
                    if (vomOrderResponseSR.getSuccess() && vomOrderResponseSR.getResult() != null) {
                        VOMOrderResponse vomOrderResponse = vomOrderResponseSR.getResult();
                        if ("T".equals(vomOrderResponse.getFlag())) {
                            Map<String, Object> successMap = new HashMap<String, Object>();
                            successMap.put("waOrderId", wpOrderId);
                            successMap.put("vomTime", now);
                            successMap.put("vomReverseInWaNo", vomReverseInWaNo);
                            successMapList.add(successMap);
                        }
                    }
                }
                paramMap.put("successMap", successMapList);
                ServiceResult<Boolean> vomResult = vomOrderService
                        .stopCrmGenuineRejectList(paramMap);
                if (vomResult.getSuccess()) {
                    result.setResult(true);
                    result.setMessage("终止退货订单成功" + " VOM提单号：" + vomReverseInWaNo);
                } else {
                    result.setResult(false);
                    result.setMessage(vomResult.getMessage() + " VOM提单号：" + vomReverseInWaNo);
                }
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            log.error("终止退货订单失败：", e);
        }
        return result;
    }
}
