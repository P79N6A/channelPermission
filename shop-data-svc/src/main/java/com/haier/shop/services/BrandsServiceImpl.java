package com.haier.shop.services;

import java.util.List;
import java.util.Map;

import com.haier.shop.dao.shopread.BrandsReadDao;
import com.haier.shop.dao.shopwrite.BrandsWriteDao;
import com.haier.shop.dto.Merchandise;
import com.haier.shop.model.*;
import com.haier.shop.service.*;
import com.haier.shop.util.OrderSnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BrandsServiceImpl implements BrandsService {
    @Autowired
    BrandsReadDao brandsReadDao;
    @Autowired
    BrandsWriteDao brandsWriteDao;
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

    @Override
    public List<Brands> selectBrandsList() {
        return brandsReadDao.selectBrandsList();
    }

    @Override
    public List<Brands> selectBrandsIdList(int id) {
        return brandsReadDao.selectBrandsIdList(id);
    }

    @Override
    public List<Brands> getAllBrands() {
        return brandsReadDao.getAllBrands();
    }

    @Override
    public Brands get(Integer id) {
        return brandsReadDao.get(id);
    }

	@Override
	public List<Map<String, Object>> getBrands() {
		return brandsReadDao.getBrands();
	}
    @Override
    public List<Map<String,Object>> getProducts(){
        return brandsReadDao.getProducts();
    }
    @Override
    public List<Map<String,Object>> getProductBy(Map<String,Object> map){
        return brandsReadDao.getProductBy(map);
    }
    @Override
    public List<Map<String,Object>> getProductInfo(List<String> list){
        return brandsReadDao.getProductInfo(list);
    }
    @Override
    public int addProduct(Map<String,Object> map) {
        return brandsWriteDao.addProduct(map);
    }
    @Override
    public int addProduct1(Map<String,Object> map) {
        return brandsWriteDao.addProduct1(map);
    }
    @Override
    public int addProduct2( Merchandise mer) {
        return brandsWriteDao.addProduct2(mer);
    }
    @Override
    public int insertSelective( Merchandise mer) {
        return brandsWriteDao.insertSelective(mer);
    }
    @Override
    public int insertSelective1( Merchandise mer) {
        return brandsWriteDao.insertSelective1(mer);
    }
    @Override
    public int insertSelective2( Merchandise mer) {
        return brandsWriteDao.insertSelective2(mer);
    }
    @Override
    public Integer province(String province ,String str) {
        return brandsReadDao.province(province,str);
    }

    @Override
    public Integer city( String city ,String str) {
        return brandsReadDao.city(city,str);
    }

    @Override
    public Integer region( String region ,String str) {
        return brandsReadDao.region(region,str);
    }

    @Override
    public String getRegionName(Integer province) {
        return brandsReadDao.getRegionName(province);
    }

    @Override
    public void addOrdersAndOrderProducts(Merchandise inv, CostPools ncp, GatePrice gatePrice,
                                          Boolean idGift,String userName,String source) {
        brandsWriteDao.insertSelective(inv);
        Orders order = shopOrdersService.getIdAndOhterByOrderSn(inv.getOrderSn());
        inv.setOrderId(order.getId());
        brandsWriteDao.insertSelective1(inv);
        brandsWriteDao.insertSelective2(inv);
        OrderProducts orderproducts = shopOrderProductsService.findOPBycOrderSnAndSku(inv.getcOrderSn(),inv.getSku());
        String cOrderSn = OrderSnUtil.getCOrderSn(orderproducts.getId());
        shopOrderProductsService.updateCorderSnById(orderproducts.getId(),cOrderSn);
        Integer memberinvoicesId = memberInvoicesService.getIdByOrderId(order.getId());
        shopOrdersService.updateMemberinvoicesId(order.getId(),memberinvoicesId);
        if(idGift){
            costPoolsService.updateBanlacnAmount(ncp.getId(), gatePrice.getPurPrice());
        }

        //添加orderWorkflows
        OrderWorkflows flow = OrderSnUtil.getDefaultOrderWorkflows();
        flow.setAddTime(order.getAddTime());
        flow.setOrderId(order.getId());
        flow.setOrderProductId(orderproducts.getId());
        flow.setPayTime(order.getPayTime() == null ? 0 : order.getPayTime());
        shopOrderWorkflowsService.insert(flow);

        //添加日志
        OrderOperateLogs operateLogs = new OrderOperateLogs();
        operateLogs.setSiteId(1);
        operateLogs.setOrderId(order.getId());
        operateLogs.setOrderProductId(orderproducts.getId());
        operateLogs.setNetPointId(0);
        operateLogs.setStatus(orderproducts.getStatus());
        //付款状态
        operateLogs.setPaymentStatus(orderproducts.getcPaymentStatus());
        operateLogs.setOperator(userName);
        operateLogs.setChangeLog("添加订单");
        operateLogs.setRemark("后台批量导入订单");
        shopOrderOperateLogsService.insert(operateLogs);

        if(idGift) {
            CostPoolsUsedLogs costPoolsUsedLogs = new CostPoolsUsedLogs();
            costPoolsUsedLogs.setSiteId(1);
            costPoolsUsedLogs.setType(ncp.getType());
            costPoolsUsedLogs.setChannel(ncp.getChannel());
            costPoolsUsedLogs.setChanye(ncp.getChanYe());
            costPoolsUsedLogs.setYearMonth(ncp.getYearMonth());
            costPoolsUsedLogs.setOrderSn(inv.getOrderSn());
            costPoolsUsedLogs.setcOrderSn(cOrderSn);
            costPoolsUsedLogs.setOrderId(order.getId());
            costPoolsUsedLogs.setCorderId(orderproducts.getId());
            costPoolsUsedLogs.setRelationOrderSn(order.getRelationOrderSn());
            costPoolsUsedLogs.setSource(source);
            costPoolsUsedLogs.setUsedType(1);
            costPoolsUsedLogs.setAmount(gatePrice.getPurPrice());
            costPoolsUsedLogs.setRemark(" ");
            costPoolsUsedLogs.setNumber(inv.getNumber());
            costPoolsUsedLogsService.insert(costPoolsUsedLogs);

            OrderOperateLogs operateLogs1 = new OrderOperateLogs();
            operateLogs1.setSiteId(1);
            operateLogs1.setOrderId(order.getId());
            operateLogs1.setOrderProductId(orderproducts.getId());
            operateLogs1.setNetPointId(0);
            operateLogs1.setStatus(orderproducts.getStatus());
            //付款状态
            operateLogs1.setPaymentStatus(orderproducts.getcPaymentStatus());
            operateLogs1.setOperator(userName);
            operateLogs1.setChangeLog("赠品费用扣减");
            operateLogs1.setRemark("扣减金额 " + gatePrice.getPurPrice());
            shopOrderOperateLogsService.insert(operateLogs1);
        }

    }


}