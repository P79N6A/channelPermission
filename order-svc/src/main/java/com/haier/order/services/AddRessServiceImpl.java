package com.haier.order.services;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.order.service.AddRessService;
import com.haier.order.util.HelpUtils;
import com.haier.order.util.OrderSnUtil;
import com.haier.shop.dto.Merchandise;
import com.haier.shop.dto.RegionsDTO;
import com.haier.shop.model.*;
import com.haier.shop.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AddRessServiceImpl implements AddRessService {
    private final static Logger log = LoggerFactory.getLogger(AddRessServiceImpl.class);
    @Autowired
    private AddRessDataService addRessDataService;
    @Autowired
    private BrandsService brandsService;
    @Autowired
    private HelpUtils helpUtils;
    @Autowired
    private ProductsService productsService;
    @Autowired
    private OrderPriceSourceChannelService orderPriceSourceChannelService;
    @Autowired
    private CostPoolsService costPoolsService;
    @Autowired
    private CostPoolsUsedLogsService costPoolsUsedLogsService;
    @Autowired
    private ShopOrderProductsService shopOrderProductsService;
    @Autowired
    private ShopOrdersService shopOrdersService;
    @Autowired
    private OdsGatePriceDataService odsGatePriceDataService;
    @Autowired
    private ShopOrderOperateLogsService shopOrderOperateLogsService;
    @Autowired
    private RegionsService regionsService;
    @Autowired
    private MemberInvoicesService memberInvoicesService;
    @Autowired
    private ShopOrderWorkflowsService shopOrderWorkflowsService;


    public List<RegionsDTO> getRegionsAll() {
        return addRessDataService.getRegionsAll();
    }
    public List<Map<String,Object>> getProductCates(){
        List<Map<String,Object>> list=addRessDataService.getProductCates();
        return list;
    }
    public List<Map<String,Object>> getBrands(){
        List<Map<String,Object>> list=brandsService.getBrands();
        return list;
    }
    public List<Map<String,Object>> getProducts(){
        List<Map<String,Object>> list=brandsService.getProducts();
        return list;
    }
    public List<Map<String,Object>> getProductBy(Map<String,Object> map){
        List<Map<String,Object>> list=brandsService.getProductBy(map);
        return list;
    }
    public List<Map<String,Object>> getProductInfo(List<String> list){
        List<Map<String,Object>> list1=brandsService.getProductInfo(list);
        return list1;
    }

    @Override
    public String addProduct(Map<String,Object> map ,List<Merchandise> list1) {
        JSONObject json = new JSONObject();
        GatePrice gatePrice = null;
        Boolean idGift = false;
        CostPools ncp = null;
        //根据订单来源获得渠道
        String source = String.valueOf(map.get("source"));
        String industrys = String.valueOf(map.get("industrys"));
        String userName = String.valueOf(map.get("userName"));
        if(StringUtils.isEmpty(source)){
            json.put("text", "订单来源为空");
            return json.toString();
        }
        if(map.get("idGift")!=null&&"1".equals(String.valueOf(map.get("idGift")))){
            idGift=true;
        }
        OrderPriceSourceChannel channel = orderPriceSourceChannelService.getChannelByOrderSource(source);
        if(channel==null){
            channel = new OrderPriceSourceChannel();
            if("SN".equalsIgnoreCase(source.substring(0,2))||
                    "DD".equalsIgnoreCase(source.substring(0,2))||
                    "JD".equalsIgnoreCase(source.substring(0,2))){
                channel.setChannelCode("DSPT");
            }
            if("T".equalsIgnoreCase(source.substring(0,1))||
                    "G".equalsIgnoreCase(source.substring(0,1))||
                    "X".equalsIgnoreCase(source.substring(0,1))){
                channel.setChannelCode("TB");
            }
        }

        if(idGift){
            //如果是订单 就
            //根据渠道时间费用类型查询费用维护池
            Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH) + 1;
            int year = cal.get(Calendar.YEAR);
            List<CostPools> cpList = costPoolsService.findCostPoolsByChannel(industrys,CostPools.getChannelValue(channel.getChannelCode()),"0",String.valueOf(year)+(month<10 ? "0"+month : ""+month));
            //按照批次从小到大排序 desc 从小批次开始占用
            if(cpList==null||cpList.size()<1){
                json.put("text", "没有相应的营销费用池数据");
                log.error("没有相应的营销费用池数据");
                return json.toString();
            }

            for(Merchandise list :list1){
                gatePrice = odsGatePriceDataService.getOdsGatePriceBySku(list.getSku());
                if(gatePrice==null){
                    json.put("text", "改SKU"+list.getSku()+"的闸口价信息没有找到");
                    log.error("闸口价信息没有找到%");
                    return json.toString();
                }
                //价值链比率不能小于百分之5
                if(gatePrice.getChannelRate().compareTo(new BigDecimal("0.050"))<0){
                    json.put("text", "价值链比率低于5");
                    log.error("价值链比率低于5%");
                    return json.toString();
                }
                //临时闸口价需=0
                if(gatePrice.getTempGatePrice().compareTo(new BigDecimal("0"))!=0){
                    json.put("text", "临时闸口价不等于0");
                    log.error("临时闸口价不等于0");
                    return json.toString();
                }
            }


            for (CostPools cp:cpList) {
                BigDecimal rest = cp.getAmount().subtract(cp.getBalanceAmount());
                if(rest.compareTo(gatePrice.getPurPrice())==1||rest.compareTo(gatePrice.getPurPrice())==0){
                    ncp=cp;
                    continue;
                }
            }
            if(ncp==null){
                json.put("text", "useup");
                log.error("月度费用已占用完，不允许继续录入赠品机");
                return json.toString();
            }
        }

        String provinceName = brandsService.getRegionName(Integer.valueOf(map.get("province").toString()));
        String citysName = brandsService.getRegionName(Integer.valueOf(map.get("citys").toString()));
        String countyName = brandsService.getRegionName(Integer.valueOf(map.get("county").toString()));
        map.put("regionName",provinceName+" "+citysName+" "+ countyName);
        if(StringUtil.isEmpty(String.valueOf(map.get("originRegionName")),true)){
            map.put("originRegionName",provinceName+" "+citysName+" "+ countyName);
        }
        map.put("agent",userName);
        int flas = brandsService.addProduct(map);
        Orders order = shopOrdersService.getByOrderSn(String.valueOf(map.get("orderSn")));
        map.put("orderId",order.getId());
        int flas1 = brandsService.addProduct1(map);
        int index = 0;
        int num = 0;

        for(Merchandise list :list1){
            Merchandise mer = new Merchandise();
            mer.setOrderId(order.getId());
            mer.setOrderSn(order.getOrderSn());
            mer.setEsAmount(list.getEsAmount());
            mer.setAddTime(list.getAddTime());
            mer.setFreight(list.getFreight());
            mer.setId(list.getId());
            mer.setSku(list.getSku());
            mer.setLimitedPrice(list.getLimitedPrice());
            mer.setNumber(list.getNumber());
            mer.setProductName(list.getProductName());
            mer.setUnitPrice(list.getUnitPrice());
            mer.setSaleGuidePrice(list.getSaleGuidePrice());
            mer.setIsTest(list.getIsTest());
            mer.setSupportOneDayLimit(list.getSupportOneDayLimit());
            mer.setcOrderSn(list.getcOrderSn());
            mer.setcPaymentStatus(list.getcPaymentStatus());
            mer.setcPayTime(list.getcPayTime());
            mer.setProductType(list.getProductType());
            mer.setLockedNumber(list.getLockedNumber());
            mer.setUnlockedNumber(list.getUnlockedNumber());
            if(idGift){
                mer.setProductAmount(new BigDecimal(0.01));
            }else{
                mer.setProductAmount(list.getProductAmount());
            }

            mer.setCouponAmount(list.getCouponAmount());
            mer.setCateId(list.getCateId());
            mer.setBrandId(list.getBrandId());
            mer.setNetPointId(list.getNetPointId());
            mer.setSettlementStatus(list.getSettlementStatus());
            mer.setReceiptOrRejectTime(list.getReceiptOrRejectTime());
            mer.setIsWmsSku(list.getIsWmsSku());
            mer.setsCode(list.getsCode());
            mer.setStatus(list.getStatus());
            mer.setInvoiceNumber(list.getInvoiceNumber());
            mer.setExpressName(list.getExpressName());
            mer.setInvoiceExpressNumber(list.getInvoiceExpressNumber());
            mer.setShippingTime(list.getShippingTime());
            mer.setLessOrderSn(list.getLessOrderSn());
            mer.setWaitGetLesShippingInfo(list.getWaitGetLesShippingInfo());
            mer.setGetLesShippingCount(list.getGetLesShippingCount());
            mer.setOutping(list.getOutping());
            mer.setLessShipTime(list.getLessShipTime());
            mer.setCloseTime(list.getCloseTime());
            mer.setReceiptNum(list.getReceiptNum());
            mer.setReceiptAddTime(list.getReceiptAddTime());
            mer.setMakeReceiptType(list.getMakeReceiptType());
            mer.setShippingMode(list.getShippingMode());
            mer.setLastTimeForShippingMode(list.getLastTimeForShippingMode());
            mer.setLastEditorForShippingMode(list.getLastEditorForShippingMode());
            mer.setSystemRemark(list.getSystemRemark());
            mer.setExternalSaleSettingId(list.getExternalSaleSettingId());
            mer.setIsNoLimitStockProduct(list.getIsNoLimitStockProduct());
            mer.setSplitFlag(list.getSplitFlag());
            mer.setSplitRelateCOrderSn(list.getSplitRelateCOrderSn());


            int OPid =  brandsService.addProduct2(mer);
            OrderProducts op = shopOrderProductsService.findOPBycOrderSnAndSku(list.getcOrderSn(), list.getSku());
            String cOrderSn = OrderSnUtil.getCOrderSn(op.getId());
            int flas2 = shopOrderProductsService.updateCorderSnById(op.getId(), cOrderSn);
            MemberInvoices memberinvoicesId = memberInvoicesService.getByOrderId(order.getId());
            shopOrdersService.updateMemberinvoicesId(order.getId(),memberinvoicesId.getId());


            //添加orderWorkflows
            OrderWorkflows flow = OrderSnUtil.getDefaultOrderWorkflows();
            flow.setAddTime(order.getAddTime());
            flow.setOrderId(order.getId());
            flow.setOrderProductId(op.getId());
            flow.setPayTime(order.getPayTime() == null ? 0 : order.getPayTime());
            shopOrderWorkflowsService.insert(flow);
            //添加日志
            OrderOperateLogs operateLogs = new OrderOperateLogs();
            operateLogs.setSiteId(1);
            operateLogs.setOrderId(order.getId());
            operateLogs.setOrderProductId(op.getId());
            operateLogs.setNetPointId(0);
            operateLogs.setStatus(mer.getStatus());
            //付款状态
            operateLogs.setPaymentStatus(mer.getcPaymentStatus());
            operateLogs.setOperator(userName);
            operateLogs.setChangeLog("添加订单");
            operateLogs.setRemark("后台添加订单");
            shopOrderOperateLogsService.insert(operateLogs);

            if(idGift) {
                //修改营销池费用
                int rows = costPoolsService.updateBanlacnAmount(ncp.getId(), gatePrice.getPurPrice());

                if (rows > 0) {
                    CostPoolsUsedLogs costPoolsUsedLogs = new CostPoolsUsedLogs();
                    costPoolsUsedLogs.setSiteId(1);
                    costPoolsUsedLogs.setType(ncp.getType());
                    costPoolsUsedLogs.setChannel(ncp.getChannel());
                    costPoolsUsedLogs.setChanye(ncp.getChanYe());
                    costPoolsUsedLogs.setYearMonth(ncp.getYearMonth());
                    costPoolsUsedLogs.setOrderSn(order.getOrderSn());
                    costPoolsUsedLogs.setcOrderSn(cOrderSn);
                    costPoolsUsedLogs.setOrderId(order.getId());
                    costPoolsUsedLogs.setCorderId(op.getId());
                    costPoolsUsedLogs.setRelationOrderSn(order.getRelationOrderSn());
                    costPoolsUsedLogs.setSource(source);
                    costPoolsUsedLogs.setUsedType(1);
                    costPoolsUsedLogs.setAmount(gatePrice.getPurPrice());
                    costPoolsUsedLogs.setRemark(" ");
                    costPoolsUsedLogs.setNumber(mer.getNumber());
                    costPoolsUsedLogsService.insert(costPoolsUsedLogs);
                }
                //添加日志
                OrderOperateLogs operateLogs1 = new OrderOperateLogs();
                operateLogs1.setSiteId(1);
                operateLogs1.setOrderId(order.getId());
                operateLogs1.setOrderProductId(op.getId());
                operateLogs1.setNetPointId(0);
                operateLogs1.setStatus(mer.getStatus());
                //付款状态
                operateLogs1.setPaymentStatus(mer.getcPaymentStatus());
                operateLogs1.setOperator(userName);
                operateLogs1.setChangeLog("赠品费用扣减");
                operateLogs1.setRemark("扣减金额 "+gatePrice.getPurPrice());
                shopOrderOperateLogsService.insert(operateLogs1);
            }
            if(0 != OPid ){
                index ++;
            }else{
                num++;
            }
        }
        //结束
        if (1 == flas && 1 == flas1 && index >0 && num <=0) {

            json.put("text", "success");
        } else {
            json.put("text", "fail");
        }

        return json.toString();
    }




    @Override
    public ServiceResult<Map<String, Integer>> insertInvWarehouses(List<Merchandise> invWarehouses,HashMap<String,String> param) {
        ServiceResult<Map<String, Integer>> result = new ServiceResult<Map<String, Integer>>();
        try {
            Boolean idGift =false;
            String source=null;
            String industrys=null;
            String userName=null;
            String yearMonth=null;
            GatePrice gatePrice=null;
            CostPools ncp=null;
            int success = 0;
            int failure = 0;
            int index = 1;
            StringBuffer sb = new StringBuffer();
            String MsgList = "";
            sb.append("<br>");
            Map<String, Integer> map = new HashMap<String, Integer>();

            if(param.get("idGift")!=null&&"1".equals(String.valueOf(param.get("idGift")))){
                idGift=true;
            }
            if(param.get("source")!=null&&String.valueOf(param.get("source"))!=""){
                source=param.get("source");
            }
            if(param.get("industrys")!=null&&String.valueOf(param.get("industrys"))!=""){
                industrys=param.get("industrys");
            }
            if(param.get("userName")!=null&&String.valueOf(param.get("userName"))!=""){
                userName=param.get("userName");
            }
            //根据订单来源获得渠道
            OrderPriceSourceChannel channel = orderPriceSourceChannelService.getChannelByOrderSource(source);


            if(channel==null){
                channel = new OrderPriceSourceChannel();
                if("SN".equalsIgnoreCase(source.substring(0,2))||
                        "DD".equalsIgnoreCase(source.substring(0,2))||
                        "JD".equalsIgnoreCase(source.substring(0,2))){
                    channel.setChannelCode("DSPT");
                }
                if("T".equalsIgnoreCase(source.substring(0,1))||
                        "G".equalsIgnoreCase(source.substring(0,1))||
                        "X".equalsIgnoreCase(source.substring(0,1))){
                    channel.setChannelCode("TB");
                }
            }

            if(idGift){
                //根据渠道时间费用类型查询费用维护池
                Calendar cal = Calendar.getInstance();
                int month = cal.get(Calendar.MONTH) + 1;
                int year = cal.get(Calendar.YEAR);
                //根据渠道和产业查询营销池
                yearMonth = String.valueOf(year)+(month<10 ? "0"+month : ""+month);
                Integer costPoolsCount = costPoolsService.findCostPoolsCount(industrys,CostPools.getChannelValue(channel.getChannelCode()),"0",yearMonth);
                if(costPoolsCount==null||costPoolsCount<1){
                    map.put("success", success);
                    map.put("failure", failure);
                    result.setResult(map);
                    result.setMessage("月度费用未维护");
                    log.error("月度费用未维护");
                    return result;
                }
                //结束
            }

            if (invWarehouses == null)
                throw new BusinessException("[Warehouse_service][T2OrderItem]:insertInvWarehouses对象不能为空");

            for (Merchandise invWarehouse : invWarehouses) {
                try {
                    Orders orders = null;
                    if(!StringUtils.isEmpty(invWarehouse.getRelationOrderSn().trim())
                            &&!"新单".equalsIgnoreCase(invWarehouse.getRelationOrderSn().trim())){
                        orders = shopOrdersService.getOrderByRelationOrderSn(invWarehouse.getRelationOrderSn());
                        if(orders==null){
                           MsgList="第"+index+"条数据关联订单号不存在!";
                           if (StringUtil.isEmpty(MsgList, true)) {
                               sb.append(MsgList);
                           } else {
                               MsgList = MsgList + "<br></br>";
                               sb.append(MsgList);
                           }
                           log.info("第"+index+"条数据关联订单号不存在!");
                           break;
                       }
                    }

                        //根据SKU判断商品是否存在
                    Products pro = this.getProductIsBySku(invWarehouse.getSku());
                    if (pro == null) {
                        MsgList = "第" + index + "条数据的sku" + invWarehouse.getSku() + "商品不存在，不允许录入!";
                        if (StringUtil.isEmpty(MsgList, true)) {
                            sb.append(MsgList);
                        } else {
                            MsgList = MsgList + "<br>";
                            sb.append(MsgList);
                        }
                        log.info("第" + index + "条数据的sku" + invWarehouse.getSku() + "商品不存在，不允许录入!");
                        break;
                    }
                    if(idGift){
                        //根据SKU判断商品是否支持赠品
                        if (!pro.getIsGift()) {
                            MsgList="第"+index+"条数据的sku" + invWarehouse.getSku() + "未维护赠品机标识，不允许录入!";
                            if (StringUtil.isEmpty(MsgList, true)) {
                                sb.append(MsgList);
                            } else {
                                MsgList = MsgList + "<br>";
                                sb.append(MsgList);
                            }
                            log.info("第"+index+"条数据的sku" + invWarehouse.getSku() + "未维护赠品机标识，不允许录入!");
                            break;
                        }
                        gatePrice =odsGatePriceDataService.getOdsGatePriceBySku(invWarehouse.getSku());
                        if(gatePrice==null){
                            MsgList="第"+index+"条数据的sku" + invWarehouse.getSku() + "闸口价信息没找到";
                            if (StringUtil.isEmpty(MsgList, true)) {
                                sb.append(MsgList);
                            } else {
                                MsgList = MsgList + "<br>";
                                sb.append(MsgList);
                            }
                            log.info("第"+index+"条数据的sku" + invWarehouse.getSku() + "闸口价信息没找到");
                            break;
                        }
                        log.info("gatePrice"+gatePrice);
                        //价值链比率不能小于百分之5
                        if(gatePrice.getChannelRate()==null||gatePrice.getChannelRate().compareTo(new BigDecimal("0.050"))<0){
                            MsgList="第"+index+"条数据的sku" + invWarehouse.getSku() + "价值链比率低于5%";
                            if (StringUtil.isEmpty(MsgList, true)) {
                                sb.append(MsgList);
                            } else {
                                MsgList = MsgList + "<br>";
                                sb.append(MsgList);
                            }
                            log.info("第"+index+"条数据的sku" + invWarehouse.getSku() + "价值链比率低于5%");
                            break;
                        }
                        //临时闸口价需=0
                        if(gatePrice.getTempGatePrice()==null||gatePrice.getTempGatePrice().compareTo(new BigDecimal("0"))!=0){
                            MsgList="第"+index+"条数据的sku" + invWarehouse.getSku() + "临时闸口价不等于0";
                            if (StringUtil.isEmpty(MsgList, true)) {
                                sb.append(MsgList);
                            } else {
                                MsgList = MsgList + "<br>";
                                sb.append(MsgList);
                            }
                            log.info("第"+index+"条数据的sku" + invWarehouse.getSku() + "临时闸口价不等于0");
                            break;
                        }

                        //判断营销池是否够扣除费用
                        ncp = costPoolsService.findCostPoolsByChannelAndBatchAsc(industrys,CostPools.getChannelValue(channel.getChannelCode()),"0",yearMonth,gatePrice.getPurPrice());
                        if(ncp==null){
                            MsgList="第"+index+"条数据添加失败！月度费用已占用完，不允许继续录入赠品机！成功录入"+success+"条数据";
                            if (StringUtil.isEmpty(MsgList, true)) {
                                sb.append(MsgList);
                            } else {
                                MsgList = MsgList + "<br>";
                                sb.append(MsgList);
                            }
                            log.info("第"+index+"条数据添加失败！月度费用已占用完，不允许继续录入赠品机！成功录入"+success+"条数据");
                            break;
                        }
                    }

                    Date date = new Date();
                    long dat = date.getTime()/1000;
                    String ordersn = helpUtils.getOrderSn();
                    Merchandise inv = new Merchandise();
                    inv.setId(pro.getId());
                    inv.setProductName(pro.getProductName());
                    inv.setEsAmount(pro.getEnergySubsidyAmount());
                    inv.setAddTime(dat);//添加时间
                    inv.setNumber(invWarehouse.getNumber());//数量
                    if(idGift&&gatePrice!=null){
                        inv.setShippingMode(gatePrice.getLogisticsModel());//物流模式值为B2B2C或B2C
                    }else{
                        inv.setShippingMode(invWarehouse.getShippingMode());//物流模式值为B2B2C或B2C
                    }
                    inv.setSku(invWarehouse.getSku());//物料
                    inv.setCouponCodeValue(invWarehouse.getCouponCodeValue());//优惠总额
                    inv.setEsAmount(invWarehouse.getEsAmount());//节能补贴
                    inv.setSaleGuidePrice(invWarehouse.getSaleGuidePrice());//价格
                    /*orderproduct默认值*/
                    inv.setFreight(new BigDecimal(0));//运费
                    inv.setIsTest(0);//是否是测试网点
                    inv.setSupportOneDayLimit(0);//是否支持24小时时限达
                    inv.setcOrderSn(ordersn);//child order sn 子订单编码 C0919293(网单号)
                    inv.setcPaymentStatus(200);//子订单付款状态
                    inv.setcPayTime(0);//子订单付款时间
                    inv.setProductType(pro.getProductTypeId());//商品类型
                    inv.setLockedNumber(0);//曾经锁定的库存数量
                    inv.setUnlockedNumber(0);//曾经解锁的库存数量
                    inv.setProductAmount(invWarehouse.getSaleGuidePrice().multiply(new BigDecimal(invWarehouse.getNumber())));//此字段专为同步外部订单而加
                    inv.setCouponAmount(new BigDecimal(0));//优惠券抵扣金额
                    inv.setOrderAmount(inv.getProductAmount());
                    inv.setCateId(pro.getProductCateId());//分类ID
                    inv.setBrandId(pro.getBrandId());//品牌id
                    inv.setNetPointId(0);//网点id
                    inv.setSettlementStatus(0);//结算状态0 未结算 1已结算 、
                    inv.setReceiptOrRejectTime(0);//确认收货时间或拒绝收货时间、
                    inv.setIsWmsSku(0);//是否淘宝小家电、
                    inv.setsCode("");//库位编码、
                    inv.setStatus(0);//状态、
                    inv.setInvoiceNumber("");//运单号、
                    inv.setExpressName("");//快递公司、
                    inv.setInvoiceExpressNumber("");//发票快递单号、
                    inv.setShippingTime(0);//发货时间、
                    inv.setLessOrderSn("");//less 订单号、
                    inv.setWaitGetLesShippingInfo(0);//是否等待获取LES物流配送节点信息
                    inv.setGetLesShippingCount(0);//已获取LES配送节点信息的次数
                    inv.setOutping("");//出库凭证
                    inv.setLessShipTime(0);//less出库时间
                    inv.setCloseTime(0);//网单完成关闭或取消关闭时间
                    inv.setReceiptNum("");//发票号
                    inv.setReceiptAddTime("");//开票时间
                    inv.setMakeReceiptType(0);//开票类型
                    inv.setLastTimeForShippingMode(0);//最后修改物流模式的时间
                    inv.setLastEditorForShippingMode("");//最后修改物流模式的管理员
                    inv.setSystemRemark("");//系统备注
                    inv.setExternalSaleSettingId(0);//淘宝套装id
                    inv.setIsNoLimitStockProduct(0);//是否是无限制库存量的商品
                    inv.setSplitFlag(0);//拆单标志
                    inv.setSplitRelateCOrderSn("");//拆单关联单号
                    /*order*/
                    inv.setOrderSn(ordersn);
                    inv.setIsProduceDaily(invWarehouse.getIsProduceDaily());//是否是日日单
                    inv.setSource(invWarehouse.getSource());//订单来源
                    inv.setSourceOrderSn(invWarehouse.getSourceOrderSn());//来源订单号
                    String ss = "/";
                    Integer province = null;
                    //数据库中不是全名 为了匹配自治区全名做匹配
                    if(invWarehouse.getProvince().length()>1){
                        if("内蒙".equals(invWarehouse.getProvince().substring(0,2))){
                            province = brandsService.province("内蒙古",ss);
                        }else if("新疆".equals(invWarehouse.getProvince().substring(0,2))){
                            province = brandsService.province("新疆",ss);
                        }else if("广西".equals(invWarehouse.getProvince().substring(0,2))){
                            province = brandsService.province("广西",ss);
                        }else if("宁夏".equals(invWarehouse.getProvince().substring(0,2))){
                            province = brandsService.province("宁夏",ss);
                        }else if("西藏".equals(invWarehouse.getProvince().substring(0,2))){
                            province = brandsService.province("西藏",ss);
                        }else if(invWarehouse.getProvince().substring(invWarehouse.getProvince().length()-1).equals("省")){
                            province = brandsService.province(invWarehouse.getProvince().substring(0,invWarehouse.getProvince().length()-1),ss);
                        }else{
                            province = brandsService.province(invWarehouse.getProvince(),ss);
                        }
                    }else{
                        province = brandsService.province(invWarehouse.getProvince(),ss);
                    }
//                    if (province==null) {
//                        MsgList = "第" + index + "条数据的省份不存在!";
//                        if (StringUtil.isEmpty(MsgList, true)) {
//                            sb.append(MsgList);
//                        } else {
//                            MsgList = MsgList + "<br>";
//                            sb.append(MsgList);
//                        }
//                        log.info("第" + index + "条数据的省份不存在!");
//                        break;
//                    }
                    if(province==null){
                        inv.setProvinceid(0);//市
                        province=0;
                    }else{
                        inv.setProvinceid(province);//市
                    }
                    inv.setProvinceid(province);//省
                    String str = "/"+province+"/";
                    Integer city = null;
                    if(invWarehouse.getCity().length()>1&&invWarehouse.getCity().substring(invWarehouse.getCity().length()-1).equals("市")){
                        city = brandsService.city(invWarehouse.getCity().substring(0,invWarehouse.getCity().length()-1),str);
                        if(city==null){
                            city = brandsService.city(invWarehouse.getCity(),str);
                        }
                    }else{
                        city = brandsService.city(invWarehouse.getCity(),str);
                    }
//                    if (city==null) {
//                        MsgList = "第" + index + "条数据的市不存在!";
//                        if (StringUtil.isEmpty(MsgList, true)) {
//                            sb.append(MsgList);
//                        } else {
//                            MsgList = MsgList + "<br>";
//                            sb.append(MsgList);
//                        }
//                        log.info("第" + index + "条数据的市不存在!");
//                        break;
//                    }
                    if(city==null){
                        inv.setCityid(0);//市
                        city=0;
                    }else{
                        inv.setCityid(city);//市
                    }
                    String strs = str + city +"/";
                    Integer region = brandsService.region(invWarehouse.getRegion(),strs);
//                    if (region==null) {
//                        MsgList = "第" + index + "条数据的区县不存在!";
//                        if (StringUtil.isEmpty(MsgList, true)) {
//                            sb.append(MsgList);
//                        } else {
//                            MsgList = MsgList + "<br>";
//                            sb.append(MsgList);
//                        }
//                        log.info("第" + index + "条数据的区县不存在!");
//                        break;
//                    }
                    if(region==null){
                        inv.setRegionid(0);//区
                    }else{
                        inv.setRegionid(region);//区
                    }
                    inv.setAddress(invWarehouse.getAddress());//详细地址
                    if(orders==null){
                        inv.setOriginAddress(invWarehouse.getAddress());
                        inv.setOriginRegionName(invWarehouse.getProvince()+" "+invWarehouse.getCity()+" "+invWarehouse.getRegion());
                    }else{
                        inv.setOriginAddress(orders.getOriginAddress());
                        inv.setOriginRegionName(orders.getOriginRegionName());
                    }
                    inv.setConsignee(invWarehouse.getConsignee());//收货人
                    inv.setZipcode(invWarehouse.getZipcode());//邮编
                    inv.setMobile(invWarehouse.getMobile());//手机
                    inv.setPhone(invWarehouse.getPhone());//固话
                    inv.setIsTogether(invWarehouse.getIsTogether());//货票同行
                    inv.setRemark1(invWarehouse.getRemark1());//备注
                    inv.setRelationOrderSn(invWarehouse.getRelationOrderSn());//关联订单号
                    inv.setSyncTime(dat);//同步到此表的时间
                    /*orders 默认值*/
                    if(idGift){
                        inv.setIdGift(1);
                        inv.setPaidAmount(new BigDecimal(0.01));//已付金额
                    }else{
                        inv.setIdGift(0);
                        inv.setPaidAmount(new BigDecimal(0));//已付金额
                    }
                    inv.setPhone(invWarehouse.getPhone());
                    inv.setIsTest1(0);//是否是测试订单
                    inv.setIsCod(0);//是否货到付款
                    inv.setNotAutoConfirm(0);//是否是非自动订单
                    inv.setMemberId(0);//是否是非自动订单
                    inv.setMemberEmail("");//会员邮件
                    inv.setOrderStatus(200);//订单状态
                    inv.setPayTime(0);//在线付款时间
                    inv.setPaymentStatus(100);//付款状态
                    inv.setReceiptConsignee("");//发票收件人
                    inv.setReceiptAddress("");//发票地址
                    inv.setReceiptZipcode("");//发票邮编
                    inv.setReceiptMobile("");//发票电话
//                    inv.setProductAmount1(invWarehouse.getSaleGuidePrice());//商品金额
                    inv.setOrderAmount(inv.getProductAmount());//订单总金额
                    inv.setAgent(userName);//处理人
                    inv.setShippingAmount(new BigDecimal(0));//淘宝运费
                    inv.setTotalEsAmount(invWarehouse.getEsAmount());//网单中节能补贴之和
                    inv.setPaymentCode("alipay");//支付方式
                    inv.setPayBankCode("");//网银代码
                    inv.setPaymentName(invWarehouse.getPaymentName());//支付方式名称
                    inv.setStreet(0);//街道ID
                    inv.setMarkBuilding(0);//标建标志
                    inv.setPoiId("");//标建id
                    inv.setPoiName("");//标建名字
                    inv.setRegionName(invWarehouse.getProvince()+" "+invWarehouse.getCity()+" "+invWarehouse.getRegion());//地区名称
                    inv.setReceiptInfo("a:0:{}");//发票信息
                    inv.setDelayShipTime(0);//延迟发货日期
                    inv.setFirstConfirmTime(0);//首次确定时间
                    inv.setFirstConfirmPerson("");//首次确定人
                    inv.setSignCode("");//收货确定码
                    inv.setOrderType(0);//订单类型
                    inv.setMemberInvoiceId(0);//订单发票ID
                    inv.setTaobaoGroupId(0);//淘宝万人团活动ID
                    inv.setTradeType("fixed");//交易类型,值参考淘宝
                    inv.setStepTradeStatus("");//分阶段付款的订单状态,值参考淘宝
                    inv.setStepPaidFee(new BigDecimal(0));//分阶段付款的已付金额
                    inv.setDepositAmount(new BigDecimal(0));//定金应付金额
                    inv.setBalanceAmount(new BigDecimal(0));//尾款应付金额
                    inv.setAutoCancelDays(invWarehouse.getAutoCancelDays());//未付款过期的天数
                    inv.setIsNoLimitStockOrder(0);//不是无库存限制订单
                    inv.setAutoConfirmNum(0);//自动确认次数
                    inv.setSmConfirmTime(0);//请求发送HP时间

                    /*MemberInvoices*/
                    inv.setInvoiceTitle(invWarehouse.getInvoiceTitle());//发票抬头
                    inv.setElectricFlag(0);//发票标志
                    inv.setInvoiceType(invWarehouse.getInvoiceType());//发票类型
                    inv.setMemberName("");//会员名称
                    inv.setTaxPayerNumber(invWarehouse.getTaxPayerNumber());//会员名称
                    inv.setRegisterAddress(invWarehouse.getRegisterAddress());//注册地址
                    inv.setRegisterPhone(invWarehouse.getRegisterPhone());//注册电话
                    inv.setBankName(invWarehouse.getBankName());//开户银行
                    inv.setBankCardNumber(invWarehouse.getBankCardNumber());//银行账户
                    inv.setState(0);//审核状态
                    inv.setAuditor("系统");//审核人
                    inv.setRemark("");//备注
                    inv.setIsLock(0);//是否锁定
                    inv.setParentId(0);//已通过增票审核
                    inv.setAddTime1(dat);//添加时间

                    brandsService.addOrdersAndOrderProducts(inv,ncp,gatePrice,idGift,userName,source);
                    success++;


                }catch (Exception ex) {
                    MsgList="第"+index+"条数据添加失败！请联系后台技术人员！";
                    if (StringUtil.isEmpty(MsgList, true)) {
                        sb.append(MsgList);
                    } else {
                        MsgList = MsgList + "<br>";
                        sb.append(MsgList);
                    }
                    log.info("第"+index+"条数据添加失败！请联系后台技术人员！");
                    log.error(
                            "[Warehouse_service][insertInvWarehouses]:创建订单发生未知异常:",
                            ex);
                    break;
                }
                index++;
            }
            // 提交事务
            // transactionManager.commit(status);

            map.put("success", success);
            map.put("failure", failure);
            result.setResult(map);
            result.setMessage(sb.toString());
        } catch (BusinessException be) {
            log.info("BusinessException异常捕获========================");
            result.setSuccess(false);
            result.setMessage(be.getMessage());
        } catch (Exception e) {
            log.info("Exception异常捕获========================");
            result.setSuccess(false);
            result.setMessage("创建产品基础信息失败，" + e.getMessage());
            log.error("[Warehouse_service][insertInvWarehouses]:产品基础信息创建失败", e);
        }
        return result;
    }

    @Override
    public Products getProductIsBySku(String sku) {
        return productsService.getBySku2(sku);
    }

    @Override
    public Orders getOrderByOrderSn(String sourceOrderSn) {
        return shopOrdersService.getBySourceOrderSn(sourceOrderSn);
    }

    @Override
    public List<RegionsDTO> getRegionsParentId(String parentId) {
        return addRessDataService.getRegionsParentId(parentId);
    }

    @Override
    public Orders getOrderByRelationOrderSn(String connectOrderNum) {
        return shopOrdersService.getOrderByRelationOrderSn(connectOrderNum);
    }


}
